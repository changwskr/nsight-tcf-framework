## PDMG Transaction + Timeout 아키텍처 구조

이번 업로드 소스를 기준으로 다시 확인하면, **현재 PDMG의 핵심 구조는 `Timeout Worker`와 `TransactionTemplate`을 결합해 하나의 온라인 거래 실행 경계를 만드는 방식**입니다. 연속성 기준에서도 Timeout ON 구조에서는 최외곽 DB Transaction이 `TimeoutExecutor + TransactionTemplate`에 의해 만들어질 수 있으므로 실제 구현을 기준으로 판단하도록 정의되어 있습니다.

### 1. 전체 구조

현재 `pdmg-service/application.yml`은 `tcf.enabled=true`, `timeout.enabled=true`이므로 실제 기본 실행 경로는 다음입니다.

```text
                         PDMG ONLINE TRANSACTION
┌─────────────────────────────────────────────────────────────┐
│ HTTP / Tomcat Request Thread                               │
│                                                             │
│  Filter / Interceptor                                      │
│          │                                                  │
│          ▼                                                  │
│  OnlineTransactionController                               │
│          │ serviceId                                       │
│          ▼                                                  │
│  TcfFacade.process()                                       │
│          │                                                  │
│          ├─ TransactionContext 생성                         │
│          ├─ Active Transaction 등록                         │
│          │                                                  │
│          ▼                                                  │
│  STF.preProcess()                                          │
│    └─ 거래통제                                             │
│          │                                                  │
│          ▼                                                  │
│  OnlineTimeoutExecutor                                     │
│          │                                                  │
│          │ Worker Pool submit                              │
│          ▼                                                  │
└──────────┼──────────────────────────────────────────────────┘
           │ Thread 전환
           ▼
┌─────────────────────────────────────────────────────────────┐
│ pdmg-online-* Worker Thread                                │
│                                                             │
│  TransactionTemplate                                       │
│  PROPAGATION_REQUIRED                                      │
│          │                                                  │
│          ├────────── DB TX BEGIN ◀──────────── TX OWNER     │
│          │                                                  │
│          ▼                                                  │
│  TransactionDispatcher                                     │
│          │ serviceId                                       │
│          ▼                                                  │
│  TransactionHandler                                        │
│          │                                                  │
│          ▼                                                  │
│  Business Facade                                           │
│    @Transactional(REQUIRED)                                │
│          │                                                  │
│          ▼                                                  │
│  Service                                                   │
│    @Transactional 일부 존재                               │
│          │                                                  │
│          ▼                                                  │
│  DAO                                                       │
│          │                                                  │
│          ▼                                                  │
│  Mapper / SQL                                              │
│          │                                                  │
│          ▼                                                  │
│  RDW DB                                                    │
│          │                                                  │
│     정상 ─┴──→ COMMIT                                      │
│     예외 ─────→ ROLLBACK                                   │
└─────────────────────────────────────────────────────────────┘
           │
           ▼
┌─────────────────────────────────────────────────────────────┐
│ Request Thread                                             │
│                                                             │
│ ETF.postProcess()                                          │
│    └─ 최종 Timeout Interval 확인                           │
│          │                                                  │
│          ▼                                                  │
│ Response / ExceptionHandler                                │
└─────────────────────────────────────────────────────────────┘
```

---

## 2. 가장 중요한 핵심: **TX는 Worker Thread에서 시작됩니다**

`pdmg-fw`의 핵심 소스는 다음입니다.

```text
pdmg-fw
└─ nhnis.fw.tcf.timeout
   ├─ OnlineTimeoutConfiguration
   ├─ OnlineTimeoutProperties
   ├─ OnlineTimeoutExecutor
   ├─ DefaultOnlineTimeoutExecutor   ★
   ├─ SyncOnlineTimeoutExecutor
   ├─ OnlineTimeoutWorkerContext
   └─ OnlineTimeoutException
```

`DefaultOnlineTimeoutExecutor` 생성 시 실제로 다음 구조가 만들어집니다.

```java
this.transactionTemplate =
        new TransactionTemplate(transactionManager);

this.transactionTemplate.setPropagationBehavior(
        TransactionDefinition.PROPAGATION_REQUIRED);
```

그리고 중요한 실행 순서는:

```text
Worker Thread
    ↓
transactionTemplate.execute(...)
    ↓
TX BEGIN
    ↓
action.call()
    ↓
dispatcher.dispatch(...)
```

입니다.

즉 **Dispatcher가 호출되기 전에 이미 Physical DB Transaction이 시작됩니다.**

---

## 3. 그래서 `@Transactional`과의 관계가 중요합니다

업무 Facade에는 실제로 다음과 같은 코드가 있습니다.

```java
@Transactional(
    transactionManager = "rdwTransactionManager",
    readOnly = true
)
public mgcoa8888S0DTOout mgcoa8888S0(...) {
    return service.mgcoa8888S0(input);
}
```

그런데 외부 Worker에서 이미 동일한 `rdwTransactionManager`의 Transaction이 만들어져 있습니다.

따라서 Timeout ON일 때는 개념적으로:

```text
TransactionTemplate
PROPAGATION_REQUIRED
       │
       │ Physical TX 생성
       ▼
┌───────────────────────────────┐
│ Physical Transaction #1       │
│                               │
│ Dispatcher                    │
│    ↓                          │
│ Handler                       │
│    ↓                          │
│ Facade                        │
│ @Transactional(REQUIRED)      │
│    ↓                          │
│ Service                       │
│ @Transactional(REQUIRED)      │
│    ↓                          │
│ DAO → Mapper → DB             │
│                               │
└───────────────────────────────┘
```

가 됩니다.

**Facade와 Service의 `@Transactional`이 새로운 Physical Transaction을 만드는 것이 아닙니다.** 기본 propagation이 `REQUIRED`이고 같은 `rdwTransactionManager`를 사용한다면 이미 존재하는 Worker TX에 참여합니다.

즉 논리적인 Transaction 경계는 여러 개 보이지만:

```text
TransactionTemplate
      +
Facade @Transactional
      +
Service @Transactional
```

실제 DB 관점에서는 기본적으로:

```text
Physical TX = 1개
```

입니다.

---

## 4. Timeout과 Transaction의 실제 동작

현재 Timeout 설정도 소스에서 확인됩니다.

```yaml
nhnis:
  fw:
    tcf:
      enabled: true

    timeout:
      enabled: true
      milliseconds: 5000

      overrides:
        mgcoa5530S0: 10000

      pool-size: 20
      queue-capacity: 100
```

따라서 일반 거래는:

```text
5초
```

이고 `mgcoa5530S0`은:

```text
10초
```

입니다.

실행 메커니즘은 다음과 같습니다.

```text
Request Thread
     │
     ├─ timeout = ServiceId 정책 조회
     │
     ├─ Worker submit
     │
     ▼
future.get(timeoutMs)
     │
     ├──────────────── 정상시간 내 완료
     │                    │
     │                    ▼
     │                  COMMIT
     │                    │
     │                    ▼
     │                  Response
     │
     └──── timeout
              │
              ▼
       future.cancel(true)
              │
              ▼
       Worker Interrupt 요청
              │
              ▼
       OnlineTimeoutException
              │
              ▼
         Request 종료
```

Worker 내부에서도 추가 방어를 합니다.

```text
action.call()
     │
     ▼
현재 시각 >= deadline ?
또는
Thread interrupted ?
     │
 ┌───┴────┐
 NO       YES
 │         │
 ▼         ▼
return   setRollbackOnly()
 │         │
 ▼         ▼
COMMIT   OnlineTimeoutException
           │
           ▼
        ROLLBACK
```

테스트 코드에서도 정상 완료 시 commit, 업무 예외 시 rollback, deadline 초과 시 rollback 또는 rollback-only가 검증되어 있습니다.

---

## 5. 정상 거래

예를 들어 제한시간 5초이고 DB 작업이 800ms에 끝났다면:

```text
T = 0 ms
Request 시작

     ↓

STF

     ↓

Worker Thread

     ↓

TX BEGIN
     │
     ├─ Handler
     ├─ Facade
     ├─ Service
     ├─ DAO
     └─ SQL

     ↓ 800ms

정상 Return

     ↓

COMMIT

     ↓

Future 완료

     ↓

ETF

     ↓

Response
```

결과는:

```text
HTTP Request     성공
Worker           종료
DB Transaction   COMMIT
Connection       반환
```

입니다.

---

## 6. 업무 예외가 발생하면

예를 들어 Service에서 RuntimeException이 발생하면:

```text
TX BEGIN
   │
   ▼
Handler
   ↓
Facade
   ↓
Service
   │
   X Exception
   │
   ▼
TransactionTemplate
   │
   ├─ setRollbackOnly
   │
   ▼
ROLLBACK
   │
   ▼
ExecutionException
   │
   ▼
Request Thread
   │
   ▼
GlobalExceptionHandler
```

이 경우 업무 변경 내용은 rollback됩니다.

---

# 7. Timeout이 발생하면

여기가 가장 중요합니다.

```text
                   5 sec
Request Thread ────────────────────────────────X
       │                                       │
       │                                       ▼
       │                              Future.get Timeout
       │                                       │
       │                              future.cancel(true)
       │                                       │
       │                              OnlineTimeoutException
       │
       │
       ▼
Worker Thread
       │
       ├─ TX BEGIN
       │
       ├─ Service
       │
       ├─ DAO
       │
       └─ DB SQL ...
```

여기서 반드시 알아야 할 점이 있습니다.

### `cancel(true)` = DB SQL을 무조건 즉시 죽인다는 뜻은 아닙니다.

`future.cancel(true)`는:

```text
Worker Thread에 interrupt 요청
```

입니다.

따라서 Java 코드가:

```text
Thread.sleep()
BlockingQueue
일부 interrupt-aware I/O
```

상태라면 잘 반응할 수 있습니다.

하지만 JDBC Driver/DB SQL이 interrupt를 즉시 처리하지 않는다면:

```text
Request Thread
     │
     └──── 5초 → 사용자에게 Timeout

Worker Thread
     │
     └────────── DB SQL 계속 실행 가능
                        │
                        │
                       8초
                        │
                        ▼
                  SQL 반환/예외
                        │
                        ▼
                  Rollback 처리
```

가 될 수 있습니다.

즉 **현재 구현은 “5초가 되면 반드시 DB SQL까지 물리적으로 5초에 종료”되는 구조는 아닙니다.**

---

# 8. 그래서 현재 Timeout에는 두 가지 의미가 있습니다

이 둘을 구분해야 합니다.

```text
① Application Execution Timeout
────────────────────────────────
Future.get(5000ms)

5초 이상 요청 Thread를 기다리지 않는다.

현재 구현됨
        ✅


② DB / Transaction Hard Timeout
────────────────────────────────
SQL/JDBC/DB 작업 자체가
5초를 넘지 못하도록 강제 종료

현재 TransactionTemplate에는
setTimeout()이 없음

        ⚠ 별도 보완 필요
```

현재 `TransactionTemplate`에는:

```java
transactionTemplate.setPropagationBehavior(REQUIRED);
```

는 있지만:

```java
transactionTemplate.setTimeout(...);
```

은 없습니다.

따라서 현재 `nhnis.fw.timeout.milliseconds=5000`은 **Spring Transaction timeout 자체는 아닙니다.**

이 차이는 매우 중요합니다.

---

# 9. ETF는 두 번째 Timeout 방어선입니다

`etf.postProcess()`에서도:

```java
long timeoutMs =
    timeoutProperties.resolveMilliseconds(serviceId);

long elapsedMs =
    context.elapsedMsSinceStart();

if (elapsedMs > timeoutMs) {
    throw new OnlineTimeoutException(...);
}
```

을 수행합니다.

따라서 Timeout 구조는 사실상:

```text
                 Timeout Defense

       ┌────────────────────────────┐
       │ 1차                        │
       │ Future.get(timeout)        │
       │                            │
       │ 실행 중 강제 제한          │
       └────────────┬───────────────┘
                    │
                    ▼
             Worker / Transaction
                    │
                    ▼
       ┌────────────────────────────┐
       │ 2차                        │
       │ ETF elapsed check          │
       │                            │
       │ 거래 총 경과시간 사후검사  │
       └────────────────────────────┘
```

라고 보는 것이 정확합니다.

---

# 10. Timeout ON/OFF에 따라 **TX Owner가 달라집니다**

이 부분이 PDMG Transaction 아키텍처를 이해하는 핵심입니다.

| 실행 모드                | Thread         | 최외곽 TX Owner                          | Facade `@Transactional` |
| ------------------------ | -------------- | ---------------------------------------- | ----------------------- |
| **TCF ON + Timeout ON**  | Worker Thread  | **TransactionTemplate**                  | 기존 TX 참여            |
| **TCF ON + Timeout OFF** | Request Thread | **Facade `@Transactional`**              | Physical TX 생성        |
| TCF OFF                  | Request Thread | 업무 Controller/Service 구조에 따라 결정 | 업무 Annotation 기준    |

현재 설정은:

```text
TCF ON
+
Timeout ON
```

이므로 **현재 기본 Architecture의 TX Owner는 `DefaultOnlineTimeoutExecutor`라고 보는 것이 맞습니다.**

---

# 11. 실제 소스 책임 위치

```text
pdmg-fw
│
├─ OnlineTransactionController
│     HTTP / ServiceId 진입
│
├─ TcfFacade
│     STF → Timeout → Dispatcher → ETF 조립
│
├─ stf
│     거래통제
│
├─ DefaultOnlineTimeoutExecutor
│     ★ Worker Thread
│     ★ Timeout
│     ★ Outer TransactionTemplate
│
├─ OnlineTimeoutProperties
│     기본/ServiceId별 Timeout 정책
│
├─ OnlineTimeoutConfiguration
│     Worker Pool
│     rdwTransactionManager 연결
│
├─ TransactionDispatcher
│     ServiceId → Handler
│
└─ etf
      거래 종료 Timeout 재확인


pdmg-service
│
├─ RdwDataSourceConfig
│     DataSourceTransactionManager
│     Bean = rdwTransactionManager
│
├─ xxxHandler
│     ServiceId → Facade
│
├─ xxxFacade
│     @Transactional
│
├─ xxxService
│     업무 로직
│     일부 @Transactional 존재
│
├─ DAO
│
└─ Mapper
```

따라서 **Transaction + Timeout 공통 구조 자체를 수정해야 한다면 중심 모듈은 `pdmg-fw`**입니다.

업무별 `@Transactional` 정책이나 DAO/SQL을 수정하는 문제라면 `pdmg-service`가 대상입니다.

---

# 12. 현재 구조에서 제가 보는 핵심 GAP

현재 구조의 방향은 좋지만 세 가지는 명확히 관리할 필요가 있습니다.

```text
[GAP-1]

Application Timeout
        ≠
DB Hard Timeout

Future.cancel(true)가
JDBC SQL 즉시 중단을 보장하지 않음
```

```text
[GAP-2]

Outer TransactionTemplate
        +
Facade @Transactional
        +
Service @Transactional

Transaction 경계가 중복 표현됨
```

특히 Timeout ON에서는 **TransactionTemplate이 이미 최외곽 TX Owner**이므로 Facade/Service의 `@Transactional` 역할을 명확하게 표준화할 필요가 있습니다.

그리고 하나 더 중요한 것이 있습니다.

```text
[GAP-3]

Outer TransactionTemplate
readOnly 설정 없음

       ↓

Facade
@Transactional(readOnly=true)
       ↓
이미 외부 REQUIRED TX 존재
```

이 경우 Facade의 `readOnly=true`가 **새 Physical Transaction을 만드는 경우와 동일한 방식으로 DB TX 속성을 설정한다고 기대하면 안 됩니다.**

따라서 조회/변경별 `readOnly`, isolation, timeout 등을 실제 물리 TX에 반영하려면 **최외곽 TransactionTemplate 정책 자체가 ServiceId별 Transaction Policy를 받아야 하는 구조**가 더 일관됩니다.

---

# 13. 제가 권장하는 최종 목표 구조

```text
ServiceId
   │
   ▼
Transaction / Timeout Policy
   │
   ├─ timeout       = 5 sec
   ├─ txTimeout     = 5 sec
   ├─ readOnly      = true/false
   ├─ isolation
   └─ propagation   = REQUIRED
   │
   ▼
TimeoutExecutor
   │
   ▼
Worker Thread
   │
   ▼
TransactionTemplate
   │
   ├─ setTimeout()
   ├─ setReadOnly()
   ├─ setIsolationLevel()
   └─ REQUIRED
   │
   ▼
TX BEGIN
   │
   ▼
Dispatcher
   ↓
Handler
   ↓
Facade
   ↓
Service
   ↓
DAO
   ↓
Mapper / JDBC
   ↓
DB
```

그러면 **Timeout Policy와 Transaction Policy가 하나의 ServiceId 정책으로 통합**됩니다.

---

## 최종 정리

현재 PDMG를 한 문장으로 정의하면 다음과 같습니다.

```text
TCF ON + Timeout ON

Request Thread
      ↓
STF
      ↓
TimeoutExecutor
      ↓
Worker Thread
      ↓
TransactionTemplate
      ↓
TX BEGIN
      ↓
Dispatcher
      ↓
Handler
      ↓
Facade @Transactional(REQUIRED)
      ↓
Service
      ↓
DAO → Mapper → DB
      ↓
COMMIT / ROLLBACK
```

그리고 가장 중요한 판단은 **`TransactionTemplate`이 최외곽 Physical TX Owner이고, Facade의 `@Transactional(REQUIRED)`는 그 Transaction에 참여한다**는 것입니다.

다만 현재 5초 Timeout은 **`Future.get(5초)` 중심의 Application Timeout이지 JDBC/DB까지 5초에 무조건 중단시키는 완전한 Hard Transaction Timeout은 아닙니다.** 다음 개선의 핵심은 `ServiceId별 Timeout + Spring TX Timeout + JDBC/Query Timeout`을 하나의 Deadline으로 연결하는 것입니다.
