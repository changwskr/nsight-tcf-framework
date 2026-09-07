# NSIGHT / PDMG 아키텍처 정의서 — V. Transaction / Timeout / Thread / DB Architecture

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 대상: PDMG Transaction / Timeout / Thread / DB AS-IS Reference + TO-BE Control Policy  
> 문서 상태: **Draft / Evidence-First**  
> 작성일: 2026-08-31  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_IV_PDMG_Online_Runtime_TCF_Flow.md`

---

# 0. Evidence Register

| ID | 근거 자료 | 본 장 사용 목적 | 상태 |
|---|---|---|---|
| EV-V-01 | `00.BigPicture Tx 처리-1.md` | Worker TX 시작, Dispatcher~Handler~Facade~Service~DAO Transaction 범위 | `[AS-IS EVIDENCE]` |
| EV-V-02 | `02장.전체_온라인_거래_빅픽처_ASCII_확장본.md` | Timeout 두 Thread 시간축, cancel/interruption, deadline late commit 방지 | `[AS-IS EVIDENCE]` |
| EV-V-03 | `15장.Business Facade.md` | `@Transactional`, `REQUIRED`, readOnly, rollbackFor, 외부 TX 참여 | `[AS-IS EVIDENCE]` |
| EV-V-04 | `16.Service Context-1.md` | WorkerContext capture/install/clear, 동일 mutable ServiceContext 공유 Risk | `[AS-IS EVIDENCE]` |
| EV-V-05 | `11장.ServiceContext와 GUID.md` | ThreadLocal 전파·정리, Timeout 후 Worker와 Request Thread 관계 | `[AS-IS EVIDENCE]` |
| EV-V-06 | `00.Big Picture Image.md` | Request Thread / Worker Thread / TX 경계 통합 그림 | `[AS-IS EVIDENCE]` |
| EV-V-07 | `NSIGHT_PDMG_아키텍처_정의서_IV_PDMG_Online_Runtime_TCF_Flow.md` | V장 Handoff, 현재 설정값, ON/OFF 차이 | `[CURRENT BASELINE DRAFT]` |
| EV-V-08 | `NSIGHT_PDMG_아키텍처_인포그래픽_이미지화_마스터_프롬프트.md` | V장 필수 Figure / Timeout 계층 검증 계약 | `[WORKING BASELINE]` |
| EV-V-09 | `23-타임아웃과-작업-취소-집필-프롬프트.md` | Timeout 응답≠JDBC 즉시종료, cancel/interruption 구분 | `[WRITING/VALIDATION RULE]` |

> **가장 중요한 Evidence 원칙**
>
> 이 장에서 Timeout을 설명할 때 다음 네 문장을 절대 같은 뜻으로 쓰지 않는다.
>
> ```text
> HTTP 요청 Timeout
> Worker Thread 종료
> JDBC Statement 취소
> DB Transaction Rollback
> ```
>
> 현재 Source는 이 네 시점이 서로 다를 수 있음을 보여 준다.

---

# 1. Current Source Snapshot

본 장의 AS-IS 주 경로는 다음 설정을 기준으로 한다.

```yaml
nhnis:
  fw:
    tcf:
      enabled: true
    timeout:
      enabled: true
      milliseconds: 5000
      pool-size: 20
      queue-capacity: 100
```

## 1.1 현재 값의 의미

| 설정 | 현재 Source 의미 | NSIGHT Target 값인가 |
|---|---|---|
| `milliseconds=5000` | Request Thread가 Future 결과를 기다리는 최대시간 | **아님** |
| `pool-size=20` | PDMG Online Worker 동시 실행수 | **아님** |
| `queue-capacity=100` | Worker 포화 시 대기 Queue 크기 | **아님** |

이 숫자는 **현재 PDMG Snapshot의 AS-IS Evidence**다.

다음으로 자동 승격하지 않는다.

```text
전사 온라인 SLA
p95 목표
Tomcat maxThreads
DB Pool Size
Client Timeout
Gateway Timeout
Oracle Query Timeout
```

---

# 2. Figure Plan

| FIG | 제목 | Level | 목적 | 필수 |
|---|---|---:|---|---|
| FIG-V-01 | Transaction / Timeout Big Picture | L0~L3 | Thread·TX·DB 전체 구조 | Y |
| FIG-V-02 | Request Thread vs Worker Thread | L2~L3 | 두 Thread 시간축 분리 | Y |
| FIG-V-03 | Executor Pool / Queue / Overload | L2~L4 | 20 Worker + 100 Queue | Y |
| FIG-V-04 | WorkerContext Capture / Install / Clear | L2~L4 | Context/MDC 전파 | Y |
| FIG-V-05 | DB Transaction Boundary | L2~L3 | Dispatcher부터 Deadline까지 TX | Y |
| FIG-V-06 | TransactionTemplate vs Facade @Transactional | L3 | REQUIRED 참여 관계 | Y |
| FIG-V-07 | ReadOnly vs Write Transaction | L3 | Facade Annotation 의미와 외부 TX 한계 | Y |
| FIG-V-08 | 정상 Commit Sequence | L3 | 정상 실행/Commit | Y |
| FIG-V-09 | Business Exception Rollback | L3~L4 | 예외전파/Rollback | Y |
| FIG-V-10 | Timeout Two-Thread Timeline | L3~L4 | HTTP 504 vs Worker 종료 차이 | Y |
| FIG-V-11 | Future.cancel / Interrupt / JDBC Limit | L3~L4 | 취소요청과 실제 DB 종료 분리 | Y |
| FIG-V-12 | Deadline Late Commit Prevention | L3~L4 | 늦은 SQL 성공 후 Rollback | Y |
| FIG-V-13 | DB Connection Lifecycle | L3~L4 | TX/Connection/Mapper 관계 | Y |
| FIG-V-14 | Business TX vs ImageLog TX | L3~L4 | 감사로그와 업무 TX 분리 | Y |
| FIG-V-15 | Timeout Budget Layers | L2~L5 | DB/Worker/Server/Client 예산 | Y |
| FIG-V-16 | TCF ON Timeout ON/OFF Matrix | L2~L4 | Mode별 TX 시작점 | Y |
| FIG-V-17 | Failure / Risk / Anti-Pattern | L4 | 현재 위험과 금지 패턴 | Y |
| FIG-V-18 | VI장 Handoff | L5 | Message/Error/Log로 연결 | Y |

---

# 3. 핵심 결론

V장의 가장 중요한 결론은 다음 열두 문장이다.

1. **현재 TCF ON + Timeout ON에서는 실제 업무가 HTTP Request Thread가 아니라 `pdmg-online-N` Worker Thread에서 실행된다.**
2. Request Thread는 Worker 결과를 `Future.get(5000ms)`로 기다린다.
3. Worker가 시작되면 `TransactionTemplate`이 `rdwTransactionManager` 기반 DB Transaction을 연다.
4. 현재 DB Transaction 범위는 **TransactionDispatcher → TransactionHandler → Business Facade → BizPre/Post → Service → DAO/Mapper/SQL → Deadline 검사**까지다.
5. 따라서 **Handler도 Transaction 안에 있다.** 다만 Handler가 직접 SQL을 실행하는 것은 정상 책임이 아니다.
6. Facade의 `@Transactional(REQUIRED)`는 이미 Worker 외부 Transaction이 존재하면 새 Transaction을 만들지 않고 같은 Transaction에 참여한다.
7. 조회 Facade의 `readOnly=true` 선언이 있더라도 외부 TransactionTemplate가 먼저 시작된 경우 내부 선언이 외부 Transaction 속성을 뒤늦게 다시 정의한다고 단정할 수 없다.
8. Business Exception이 Transaction 밖으로 전파되면 Rollback된다. 반대로 예외를 잡아 정상값으로 반환하면 Transaction Proxy는 정상종료로 인식할 수 있으므로 Rollback 계약이 깨질 수 있다.
9. Request Thread가 5초에 Timeout되어 HTTP 504를 반환해도 Worker/JDBC/DB가 정확히 그 시점에 종료된다고 보장할 수 없다.
10. `Future.cancel(true)`는 **Interrupt 요청**이지 DB 강제 Kill 명령이 아니다.
11. 현재 Worker는 업무 반환 뒤 Deadline을 다시 검사하여 제한시간을 넘긴 경우 `rollbackOnly`로 늦은 Commit을 차단한다.
12. Worker Context는 ThreadLocal 자동전파가 아니라 명시적 capture/install/clear로 전달되며, 현재 구현은 같은 mutable `ServiceContext` 객체 참조를 공유하기 때문에 Timeout 시점의 동시 접근 Risk가 존재한다.

---

# 4. 목적 / 범위 / 전제

## 4.1 목적

본 장은 다음 질문에 답한다.

1. Request Thread와 Worker Thread는 정확히 무엇이 다른가?
2. Worker Pool과 Queue는 어떻게 Overload를 통제하는가?
3. ServiceContext와 MDC는 Worker에 어떻게 전달되는가?
4. DB Transaction은 정확히 어디서 시작하는가?
5. Handler도 Transaction 안인가?
6. Facade `@Transactional(REQUIRED)`는 Worker Transaction과 어떤 관계인가?
7. 조회/쓰기 Facade의 Transaction Annotation은 무엇을 의미하는가?
8. Business Exception은 언제 Rollback되는가?
9. 5초 Timeout이 발생하면 Worker와 DB는 언제 끝나는가?
10. `cancel(true)`와 JDBC Statement Cancel은 같은가?
11. Deadline 검사는 어떻게 Late Commit을 막는가?
12. DB Connection은 어느 Thread/Transaction에 속하는가?
13. ImageLog는 업무 Transaction과 같은가?
14. DB Query / Transaction / Worker / Server / Client Timeout을 어떤 계층으로 관리해야 하는가?
15. TCF ON/OFF 및 Timeout ON/OFF에 따라 Transaction 시작점은 어떻게 달라지는가?

## 4.2 포함

```text
OnlineTimeoutExecutor
Executor Pool
Queue
Future.get
Future.cancel(true)
Request Thread
Worker Thread
OnlineTimeoutWorkerContext
ServiceContext
MDC
TransactionTemplate
rdwTransactionManager
TransactionDispatcher
TransactionHandler
Business Facade
@Transactional(REQUIRED)
readOnly
rollbackFor
BizPrePostAspect
Service
DAO
MyBatis
Mapper XML
JDBC / Connection
Deadline
setRollbackOnly
COMMIT / ROLLBACK
ImageLog Transaction 분리
TCF ON/OFF
Timeout ON/OFF
```

## 4.3 제외

```text
표준 Request/Response 전문 필드        → VI
Exception Code 체계                   → VI
JWT/SSO                               → VII
Hikari Pool 최종 Size                 → VIII
Tomcat Thread / JVM Capacity          → VIII
OM Queue/Pool Dashboard               → IX
ServiceId-SQL 자동추적                → X
```

---

# 5. FIG-V-01 — Transaction / Timeout Big Picture

```text
Browser
   │
   ▼
════════════════════ Request Thread ════════════════════

DefaultFilter
   ↓
Interceptor
   ↓
OnlineTransactionController
   ↓
TcfFacade
   ↓
OnlineTimeoutExecutor
   │
   ├─ WorkerContext.capture()
   ├─ executor.submit(task)
   └─ Future.get(5000ms)
          │
          │ submit
          ▼
════════════════════ Worker Thread ═════════════════════

OnlineTimeoutWorkerContext.install()
   ↓
┌────────────────── DB Transaction ──────────────────┐
│ TransactionTemplate BEGIN                         │
│                                                   │
│ TransactionDispatcher                             │
│    ↓                                              │
│ TransactionHandler                                │
│    ↓                                              │
│ Business Facade @Transactional(REQUIRED)          │
│    ↓                                              │
│ BizPrePostAspect.before                           │
│    ↓                                              │
│ Service                                           │
│    ↓                                              │
│ DAO → SqlSessionTemplate → Mapper → JDBC → DB     │
│    ↓                                              │
│ BizPrePostAspect.afterReturning                   │
│    ↓                                              │
│ Deadline Check                                    │
│    ├─ OK → COMMIT                                 │
│    └─ EXCEEDED → rollbackOnly → ROLLBACK          │
└───────────────────────────────────────────────────┘
   ↓
workerContext.clear()

══════════════════ 결과 / 예외 반환 ═════════════════

Request Thread
   ↓
Response / Error
   ↓
afterCompletion
   ↓
Filter finally
```

## 5.1 핵심 경계

```text
Request Thread
≠ Worker Thread

HTTP Request
≠ DB Transaction

Timeout Response
≠ Worker Completion

SQL Success
≠ Transaction Commit
```

---

# 6. FIG-V-02 — Request Thread vs Worker Thread

## 6.1 Request Thread 역할

```text
Request Thread
├─ Filter
├─ Security / MVC
├─ Interceptor
├─ OnlineTransactionController
├─ TcfFacade
├─ Worker submit
├─ Future.get(timeout)
├─ Response Advice
├─ afterCompletion
└─ Filter cleanup
```

Request Thread가 하지 않는 주 업무:

```text
Handler 업무 실행
Facade 업무 실행
Service 업무 실행
DAO SQL
Worker DB Transaction
```

## 6.2 Worker Thread 역할

```text
pdmg-online-N
├─ WorkerContext install
├─ TransactionTemplate
├─ Dispatcher
├─ Handler
├─ Facade
├─ BizPrePostAspect
├─ Service
├─ DAO / Mapper / SQL
├─ Deadline Check
├─ Commit / Rollback
└─ WorkerContext clear
```

## 6.3 두 Thread의 시간축

```text
시간 ──────────────────────────────────────────────────────────────>

Request Thread
0ms     submit
│
│──────────────── Future.get(5000ms) ────────────────│
│                                                    │
│                                                    │
5000ms                                               │
│ Timeout                                            │
│ cancel(true)                                       │
│ 504                                                │
▼                                                    │

Worker Thread                                        │
  10~수십 ms                                         │
  TX BEGIN                                           │
  │                                                  │
  ├─ Handler / Business                              │
  └─ SQL --------------------------------------------┤
                                                     │
                                                     │
                                  실제 SQL 반환 시점 │
                                                     ▼
                                         Deadline Check
                                         Commit / Rollback
```

---

# 7. FIG-V-03 — Executor Pool / Queue / Overload

## 7.1 Current Snapshot

```text
pool-size       = 20
queue-capacity  = 100
timeout         = 5000ms
```

## 7.2 구조

```text
Incoming Online Requests
        │
        ▼
┌────────────────────────────┐
│ OnlineTimeoutExecutor      │
├────────────────────────────┤
│ Worker #01                 │
│ Worker #02                 │
│ ...                        │
│ Worker #20                 │
├────────────────────────────┤
│ Queue 001                  │
│ Queue 002                  │
│ ...                        │
│ Queue 100                  │
└─────────────┬──────────────┘
              │
              ├─ capacity available
              │      ↓
              │    execute
              │
              └─ full
                     ↓
              Task Rejected
                     ↓
              OnlineOverloadException
                     ↓
              HTTP 503
```

## 7.3 중요한 의미

`20 + 100`은 단순 설정이 아니라 **Failure Domain**을 만든다.

```text
DB Slow
   ↓
Worker 장기점유
   ↓
20 Worker 소진
   ↓
Queue 100 증가
   ↓
Queue Full
   ↓
503 Overload
```

즉 DB 지연은 Application Worker Queue 포화로 전파될 수 있다.

## 7.4 이 값으로 말할 수 없는 것

```text
초당 20 TPS
최대 120 동시거래
적정 Production Size
DB Connection Pool 최소 20
```

이런 결론은 별도 Capacity Model 없이 내리지 않는다.

---

# 8. Overload vs Timeout을 구분한다

```text
[Overload]
Worker submit 전에 또는 Queue 수용 단계에서 실패
→ 업무 TX 시작 전 가능
→ HTTP 503

[Timeout]
Worker 실행 또는 대기 중 Request Thread 제한시간 초과
→ HTTP 504
→ Worker는 계속 종료작업 중일 수 있음
```

## 8.1 Matrix

| 구분 | Overload | Timeout |
|---|---|---|
| 원인 | Worker/Queue 수용불가 | 제한시간 초과 |
| HTTP | 503 | 504 |
| Worker 시작 | 안 했을 수 있음 | 했을 수 있음 |
| DB TX | 미시작 가능 | 이미 시작했을 수 있음 |
| Rollback 필요 | 보통 없음 | 필요 가능 |
| Client Retry | 정책 필요 | 정책 필요 |
| 운영지표 | reject count | timeout count + late worker |

---

# 9. FIG-V-04 — WorkerContext Capture / Install / Clear

## 9.1 ThreadLocal 문제

```text
Request Thread
ServiceContextHolder = Context-A
MDC = {guid, serviceId, user...}

             │ new worker
             ▼

Worker Thread
ServiceContextHolder = null
MDC = empty
```

ThreadLocal은 자동 전파되지 않는다.

## 9.2 Current AS-IS

```text
Request Thread
   │
   ├─ OnlineTimeoutWorkerContext.capture()
   │    ├─ ServiceContext reference
   │    └─ MDC map
   │
   ▼
Worker Thread
   │
   ├─ workerContext.install()
   │    ├─ ServiceContextHolder.setInstance(...)
   │    └─ ThreadContext.putAll(...)
   │
   ├─ Handler / Facade / Service
   │
   └─ finally
        └─ workerContext.clear()
             ├─ ServiceContextHolder.removeInstance()
             └─ ThreadContext.clearAll()
```

## 9.3 전달되는 주요 정보

Source 분석에서 함께 전달되는 것으로 확인된 항목:

```text
ServiceContext
MDC 전체
GUID
serviceId
사용자 ID
Client IP
```

일부 값은 `ServiceContext` 또는 MDC 내부에 포함되어 있으므로 실제 필드 구조와 표시방식은 구현 수준에서 중복될 수 있다.

---

# 10. Worker Context의 현재 Risk

현재 구현은 안전한 immutable snapshot을 새로 만드는 것이 아니라 **동일 ServiceContext 객체 참조**를 Worker에 전달하는 것으로 분석된다.

ServiceContext에는 다음 참조가 존재한다.

```text
HttpServletRequest
HttpServletResponse
header
userContext
requestBody
responseBody
```

따라서 Timeout 시:

```text
Request Thread
   │
   ├─ 504 응답 조립
   └─ responseBody 변경 가능
             ▲
             │ 같은 ServiceContext
             ▼
Worker Thread
   ├─ 아직 실행중
   └─ Context 조회/수정 가능
```

### `[RISK-V-01]`

```text
동일 mutable Context
+
두 Thread
+
Timeout 이후 겹치는 수명
=
경합 가능
```

## 10.1 TO-BE 후보

```text
WorkerContext Snapshot
├─ guid
├─ serviceId
├─ userId
├─ clientIp
├─ 필요한 Header 일부
└─ immutable MDC snapshot

제외:
HttpServletRequest
HttpServletResponse
responseBody mutable reference
```

---

# 11. FIG-V-05 — DB Transaction Boundary

현재 Source 분석에서 가장 중요한 정정사항이다.

## 11.1 실제 Boundary

```text
TransactionTemplate BEGIN
│
├─ TransactionDispatcher
│
├─ TransactionHandler
│
├─ Business Facade
│
├─ BizPrePostAspect.before
│
├─ Service
│
├─ DAO / Mapper / SQL
│
├─ BizPrePostAspect.afterReturning
│
├─ Deadline Check
│
└─ COMMIT / ROLLBACK
```

따라서:

```text
Handler = TX 안
```

이다.

## 11.2 잘못된 그림

```text
Dispatcher
  ↓
Handler
  ↓
[TX BEGIN]
Facade
  ↓
Service
  ↓
DAO
```

이 그림은 현재 Timeout ON Source와 일치하지 않는다.

## 11.3 왜 Handler도 TX 안인가

Worker code의 형태가 개념적으로 다음과 같기 때문이다.

```java
transactionTemplate.execute(status -> {
    return action.call();
});
```

여기서 `action.call()` 자체가:

```text
dispatcher.dispatch(...)
```

이므로 TransactionTemplate이 Dispatcher부터 감싼다.

## 11.4 실질 DB 작업 시점과 TX Boundary를 구분

```text
TX Boundary 시작
Dispatcher

실질 SQL 시작
Facade/Service 이후 DAO

```

즉 Handler가 TX 안에 있어도 Handler가 DB 책임을 가진다는 의미는 아니다.

---

# 12. Transaction Manager / DataSource Alignment

현재 분석자료에서 `RdwDataSourceConfig`가 다음 Bean을 생성하는 것으로 확인된다.

```text
rdwDataSource
rdwSqlSessionFactory
rdwSqlSessionTemplate
rdwTransactionManager
```

그리고:

```text
OnlineTimeoutExecutor TransactionTemplate
+
Facade @Transactional(transactionManager="rdwTransactionManager")
+
DAO / SqlSessionTemplate
```

이 동일 RDW Transaction Manager / DataSource 축에 정렬되어 있다.

## 12.1 Architecture Rule

```text
TransactionTemplate TM
=
Facade @Transactional TM
=
DAO SqlSession DataSource

```

이어야 같은 물리 Transaction 참여가 성립한다.

다른 TransactionManager를 쓰면:

```text
외부 TX
+
내부 새 TX
```

또는 예상치 못한 다중 Transaction이 될 수 있다.

---

# 13. FIG-V-06 — TransactionTemplate vs Facade @Transactional

## 13.1 TCF ON + Timeout ON

```text
Worker Thread

TransactionTemplate(rdw)
        │
        │ TX BEGIN
        ▼
TransactionDispatcher
        ▼
TransactionHandler
        ▼
Facade Proxy
@Transactional(
  transactionManager = "rdwTransactionManager",
  propagation = REQUIRED(default)
)
        │
        ├─ 기존 RDW TX 있음
        │
        └─ 같은 TX 참여
        ▼
Service
        ▼
DAO
        ▼
DB
        │
        ▼
TransactionTemplate가 최종 Commit/Rollback
```

## 13.2 핵심 식

```text
외부 TX 없음
+
Facade @Transactional(REQUIRED)
=
Facade Proxy가 TX 시작

외부 TX 있음
+
Facade @Transactional(REQUIRED)
=
기존 TX 참여
```

현재 Timeout ON에서는 두 번째 경우다.

---

# 14. Facade Annotation과 물리 BEGIN을 동일시하지 않는다

현행 Facade 예:

### 조회

```java
@Transactional(
    transactionManager = "rdwTransactionManager",
    readOnly = true
)
```

### 등록/수정/삭제

```java
@Transactional(
    transactionManager = "rdwTransactionManager",
    rollbackFor = Exception.class
)
```

하지만 현재 Timeout ON에서는 Worker TransactionTemplate가 더 바깥이다.

따라서:

```text
Annotation 위치
≠
항상 Physical BEGIN 위치
```

이다.

---

# 15. FIG-V-07 — ReadOnly vs Write Transaction

## 15.1 조회 Facade

```text
Worker TransactionTemplate
        │
        ▼
Facade @Transactional(readOnly=true)
        │
        ▼
Service
        ▼
DAO SELECT
```

여기서 주의:

> 외부 Transaction이 이미 시작된 경우 내부 참여자의 `readOnly=true`가 외부 Transaction의 속성을 뒤늦게 강제로 바꾼다고 단정하지 않는다.

현재 Source Evidence는 Facade Annotation과 외부 TransactionTemplate의 존재를 보여 주지만, TransactionTemplate가 조회/쓰기 ServiceId별로 readOnly를 동적으로 다르게 설정하는지는 본 장 자료에서 확인되지 않았다.

`[GAP-V-01]`

```text
Worker 외곽 Transaction이
조회 거래에 대해 readOnly 최적화를 실제 적용하는가?
```

## 15.2 Write Facade

```text
Facade
@Transactional(
  rollbackFor = Exception.class
)
```

checked Exception까지 Rollback 범위를 넓히려는 계약으로 분석된다.

단, 이미 외부 Transaction이 존재하면 최종 Rollback은 전체 Worker Transaction에 반영된다.

---

# 16. Exception을 삼키면 Rollback 계약이 깨질 수 있다

## 정상 실패 전파

```text
DAO Exception
   ↑
Service
   ↑
Facade
   ↑
Worker TransactionTemplate
   ↓
ROLLBACK
```

## Anti-pattern

```java
try {
    service.execute();
} catch (Exception ex) {
    log.error(...);
    return new Output();
}
```

위와 같이 정상값으로 반환하면:

```text
Transaction Proxy 관점
"정상 반환"
   ↓
Commit 가능
```

이다.

## Architecture Rule

```text
로그를 남겼다
≠
Rollback을 요청했다
```

유스케이스 실패는:

```text
예외 재던지기
또는
rollbackOnly 표시
```

로 Transaction 상태에 전달되어야 한다.

---

# 17. BizPrePostAspect와 Transaction

현재 Pointcut은 Service public method를 감싼다.

```text
Facade
   ↓
Service Proxy
   │
   ├─ @Before
   ▼
Service
   │
   └─ 정상 반환
       ↓
     @AfterReturning
```

TCF ON + Timeout ON에서는 이 전체가 Worker TransactionTemplate 안이다.

따라서 업무 선/후처리에서 같은 `rdwDataSource` DAO를 호출하고 예외를 삼키지 않는 경우:

```text
업무 SQL
+
업무 선후처리 SQL
=
같은 Transaction 참여 가능
```

## 17.1 중요한 Failure

`@AfterReturning`은 Service가 정상 반환할 때만 실행된다.

```text
Service Exception
   ↓
업무 후처리 없음
   ↓
Rollback
```

업무 후처리가 반드시 실행돼야 하는 보상/감사 로직이라면 `AfterReturning`만으로 충분한지 별도 설계가 필요하다.

---

# 18. FIG-V-08 — 정상 Commit Sequence

```text
Request Thread
   │
   └─ submit
        ↓
Worker Thread
   │
   ├─ Context install
   │
   ├─ TransactionTemplate BEGIN
   │
   ├─ Dispatcher
   │
   ├─ Handler
   │
   ├─ Facade REQUIRED 참여
   │
   ├─ BizPre
   │
   ├─ Service
   │
   ├─ DAO / SQL
   │
   ├─ BizPost
   │
   ├─ 업무 결과 반환
   │
   ├─ Deadline Check
   │     └─ 제한시간 내
   │
   ├─ COMMIT
   │
   └─ Context clear
        ↓
Request Thread
   ↓
Response
```

## 18.1 Commit 조건

단순화하면 현재 구조의 정상 Commit 조건은:

```text
업무 예외 없음
+
Transaction rollbackOnly 아님
+
Deadline 초과 아님
=
COMMIT 후보
```

---

# 19. FIG-V-09 — Business Exception Rollback

```text
Worker TX BEGIN
   ↓
Handler
   ↓
Facade
   ↓
Service
   ↓
DAO
   │
   └─ Exception
        ↑
      Service
        ↑
      Facade
        ↑
      Handler
        ↑
TransactionTemplate
   │
   └─ Exception 감지
        ↓
      ROLLBACK
        ↓
Worker returns exception
        ↓
Request Thread
        ↓
Error Mapping
```

## 19.1 `rollbackFor = Exception.class`

쓰기 Facade의 `rollbackFor=Exception.class`는 checked Exception까지 Rollback 대상으로 확대한다.

다만 외부 TransactionTemplate가 최종 Transaction Owner이므로, 내부 Facade의 예외가 외부까지 정상 전파되는 것이 중요하다.

---

# 20. Timeout은 네 개의 시점을 가진다

```text
T1. Request Thread timeout 인지
T2. cancel(true) 호출
T3. Worker 실제 종료
T4. DB Transaction Rollback 완료
```

이 네 시점은 같지 않다.

## 핵심 공식

```text
HTTP 504 시점
≠
Worker 종료 시점
≠
JDBC SQL 취소 완료 시점
≠
ROLLBACK 완료 시점
```

---

# 21. FIG-V-10 — Timeout Two-Thread Timeline

예시 Source Snapshot:

```text
timeout = 5000ms
```

```text
시간 ───────────────────────────────────────────────────────────────>

Request Thread
0ms
│ submit
│
│──────────────── Future.get(5000ms) ─────────────────────│
│                                                        │
5000ms                                                   │
│ Timeout                                                │
│ cancel(true)                                           │
│ OnlineTimeoutException                                 │
│ HTTP 504                                               │
▼                                                        │


Worker Thread                                            │
20ms                                                     │
│ TransactionTemplate BEGIN                              │
│                                                        │
100ms                                                    │
│ SQL 실행 ───────────────────────────────────────────────┤
│                                                        │
5300ms                                                   │
│ SQL 반환                                               │
│ Deadline 초과 확인                                     │
│ rollbackOnly                                           │
│ ROLLBACK                                               │
▼
```

위 예시는 현재 구조를 설명하기 위한 시간축으로, `20ms`, `100ms`, `5300ms`는 Source의 고정 운영값이 아니라 설명용 상대시점이다. 확정값은 `5000ms`뿐이다.

---

# 22. FIG-V-11 — Future.cancel / Interrupt / JDBC 한계

## 22.1 `cancel(true)`의 의미

```text
Request Thread
   │
   └─ Future.cancel(true)
          ↓
Worker Thread
   │
   └─ interrupt flag / interrupt request
```

이것은 다음과 같지 않다.

```text
DB Session Kill
Oracle SQL 즉시 중단
Connection 강제 close
Transaction 즉시 Rollback
```

## 22.2 실제 가능성

```text
Worker
  ├─ Java sleep/wait 등 Interrupt 반응
  ├─ 애플리케이션 코드가 interrupted 상태 확인
  ├─ JDBC Driver가 Statement 작업에 반응할 수도 있음
  └─ JDBC/DB가 즉시 반응하지 않아 SQL이 나중에 끝날 수도 있음
```

정확한 PDMG Oracle JDBC Driver/Statement Cancel 동작은 현재 업로드 Source Evidence만으로 확정할 수 없다.

`[UNKNOWN-V-01]`

---

# 23. Timeout 방어를 cancel 하나에 의존하지 않는다

현재 구조의 방어는 두 층이다.

```text
1차 방어
Request Thread
Future.get(timeout)
   ↓
cancel(true)
   ↓
사용자에게 빠른 Timeout 응답

2차 방어
Worker 복귀
   ↓
Deadline Check
   ↓
late Commit 차단
```

즉:

```text
Response Deadline
+
Commit Deadline
```

의 이중 방어로 이해한다.

---

# 24. FIG-V-12 — Deadline Late Commit Prevention

## 24.1 문제

```text
Request Thread
5000ms에 504 반환

Worker SQL
5300ms에 성공 반환
```

Deadline 검사가 없다면:

```text
사용자는 실패로 인지
BUT
DB는 Commit
```

될 수 있다.

금융/거래 시스템에서는 위험하다.

## 24.2 현재 방어

Source 분석:

```java
Object result = action.call();

if (deadlineExceeded) {
    status.setRollbackOnly();
    throw new OnlineTimeoutException(...);
}
```

구조:

```text
SQL 성공
   ↓
업무 결과 정상
   ↓
Deadline Check
   │
   ├─ 제한시간 내
   │    ↓
   │  COMMIT
   │
   └─ 제한시간 초과
        ↓
      rollbackOnly
        ↓
      OnlineTimeoutException
        ↓
      ROLLBACK
```

## 24.3 핵심 식

```text
SQL SUCCESS
+
DEADLINE EXCEEDED
=
ROLLBACK
```

즉:

```text
SQL 성공
≠
거래 성공
```

---

# 25. Deadline 검사의 한계

현재 Deadline Check는 **업무 호출이 Java 코드로 복귀한 뒤** 판단할 수 있다.

따라서 JDBC가 매우 오래 block되면:

```text
Worker
   ↓
SQL Blocking
   ↓
Deadline 넘김
   ↓
그 시점에는 아직 Deadline Check 코드 도달 불가
```

이다.

따라서 완전한 Timeout 체계는:

```text
Request Wait Timeout
+
Worker Deadline
+
Transaction Timeout
+
JDBC Statement / Query Timeout
+
DB Resource Control
```

의 계층화를 요구한다.

현재 Source에서 모든 계층의 실제 값이 확인되지는 않는다.

---

# 26. Transaction Timeout vs Online Timeout

현재 확인된 값:

```text
Online Request Wait Timeout = 5000ms
```

그러나 다음은 현재 증거에서 정확한 운영값이 확인되지 않았다.

```text
Spring Transaction Timeout
MyBatis Statement Timeout
JDBC Statement Query Timeout
Oracle Net Read Timeout
DB Resource Manager Timeout
Client Timeout
Apache/Tomcat/Gateway Timeout
```

따라서:

```text
"DB Query Timeout도 5초다"
```

라고 쓰지 않는다.

---

# 27. FIG-V-13 — DB Connection Lifecycle

이 절은 Source 확인사실과 Spring/JDBC Framework 의미를 구분한다.

## 27.1 `[AS-IS EVIDENCE]`

현재 PDMG는 다음 Bean 축을 사용한다.

```text
rdwDataSource
rdwSqlSessionFactory
rdwSqlSessionTemplate
rdwTransactionManager
```

DAO는:

```text
DAO Interface
   ↓
MyBatis SqlSessionTemplate
   ↓
Mapper XML
   ↓
JDBC / DB
```

을 사용한다.

## 27.2 `[FRAMEWORK SEMANTICS]` Connection의 개념적 위치

Spring `DataSourceTransactionManager` 계열의 일반 동작을 적용하면:

```text
Worker Thread
   ↓
TransactionTemplate BEGIN
   ↓
Transaction Synchronization
   ↓
DataSource Connection 확보/바인딩
   ↓
MyBatis SqlSessionTemplate
   ↓
같은 Transaction Resource 사용
   ↓
Commit / Rollback
   ↓
Connection 반환
```

단, **Connection이 물리적으로 정확히 어느 Method Line에서 처음 Pool에서 checkout되는지**는 Lazy Acquisition 여부와 DataSource/Proxy 구성에 따라 달라질 수 있다.

현재 Source Evidence만으로 이 세부 시점을 확정하지 않는다.

`[OPEN-V-01]`

## 27.3 중요한 소유 Thread

TCF ON + Timeout ON의 업무 DB Connection은 개념적으로:

```text
Request Thread
X

Worker Thread
O
```

의 Transaction에 귀속된다.

Request Thread에서 Worker Transaction Connection을 직접 사용하는 구조로 그리지 않는다.

---

# 28. Hikari / Pool에 대해 현재 확정할 수 있는 것과 없는 것

## 확정 가능

PDMG가 RDW DataSource와 MyBatis/Spring TransactionManager를 사용한다는 구조.

## 현재 자료만으로 확정하지 않는 값

```text
maximumPoolSize
minimumIdle
connectionTimeout
idleTimeout
maxLifetime
leakDetectionThreshold
validationTimeout
```

VIII장에서 현재 `application.yml`, Config, Runtime Metrics를 다시 검증한다.

## 금지

```text
Worker pool 20
→ Hikari pool도 20이어야 한다
```

라고 단정하지 않는다.

두 Pool은 목적이 다르다.

```text
Worker Pool
= 업무 동시실행 제어

DB Connection Pool
= DB 연결 자원 제어
```

다만 두 Pool의 비율은 Capacity에서 매우 중요하다.

---

# 29. Worker Pool과 DB Pool의 상호작용

개념적으로:

```text
20 Worker
   │
   ├─ DB 미사용 거래
   ├─ DB Connection 대기
   └─ DB Connection 사용
```

DB Pool이 Worker보다 작다면:

```text
Worker 실행
   ↓
Connection 대기
   ↓
Worker 점유
   ↓
Request Timeout
   ↓
Queue 증가
```

DB Pool이 과도하게 크다면:

```text
DB Connection 폭증
   ↓
Oracle Session / CPU / PGA 부담
```

이 관계는 VIII장의 Capacity Model에서 정량화한다.

---

# 30. FIG-V-14 — Business TX vs ImageLog TX

현재 PDMG 분석에서 ImageLog는 업무 DB Transaction과 별도 시간/Transaction으로 동작할 수 있다.

## 30.1 시간관계

```text
Request Thread
  ↓
Pre ImageLog INSERT
  │
  │ [업무 TX 밖]
  ▼

Worker
  ↓
┌──────── Business TX ────────┐
│ Handler                     │
│ Facade                      │
│ Service                     │
│ DAO                         │
│ COMMIT / ROLLBACK           │
└─────────────────────────────┘
  ↓

Request Thread
  ↓
Response
  ↓
Post / Exception ImageLog UPDATE
```

## 30.2 가능한 결과

```text
업무 COMMIT
+
ImageLog UPDATE 성공

업무 ROLLBACK
+
ImageLog 오류기록 성공

업무 COMMIT
+
ImageLog UPDATE 실패
```

즉:

```text
ImageLog 완료
≠
Business Transaction Commit
```

## 30.3 Architecture 의미

감사로그가 업무 Rollback과 같이 사라지면 장애/감사 추적이 어려우므로 분리는 장점이 있다.

반대로:

```text
업무 성공
로그 실패
```

가 가능하므로 운영관점에서는 별도의 Log Delivery/Retry/Alert 정책이 필요하다.

VI/IX에서 이어간다.

---

# 31. 업무 선후처리의 Transaction 포함

## Pre

```text
Facade TX 진행
  ↓
Service Proxy
  ↓
BizPrePostAspect.before
```

같은 RDW DAO를 호출하면 같은 TX 참여 가능.

## Service

```text
Service
  ↓
DAO
  ↓
SQL
```

같은 TX.

## Post

```text
Service 정상 반환
  ↓
BizPrePostAspect.afterReturning
```

같은 RDW DAO를 호출하고 예외를 삼키지 않는다면 같은 TX에 포함될 수 있다.

## 주의

AfterReturning에서 예외가 발생하면 전체 Transaction 결과에 영향을 줄 수 있다.

구체 Rollback 동작은 AOP와 Transaction Proxy 순서까지 Source/Test로 검증해야 한다.

`[OPEN-V-02]`

---

# 32. TCF ON + Timeout OFF

IV장에서 확인한 구조:

```text
Request Thread
   ↓
TcfFacade
   ↓
Dispatcher
   ↓
Handler
   ↓
Facade
   ↓
Service
   ↓
DAO
```

이 경우 Worker 외곽 `TransactionTemplate`이 없다.

따라서 Transaction 시작점은:

```text
Facade @Transactional Proxy
```

가 될 수 있다.

즉:

```text
TCF ON + Timeout ON
TransactionTemplate가 바깥 TX

TCF ON + Timeout OFF
Facade @Transactional이 TX 시작점 후보
```

이다.

---

# 33. TCF OFF

TCF OFF에서는:

```text
Business Controller
  ├─ → Facade → Service → DAO
  └─ → Service → DAO
```

현행 일부가 혼재한다.

## 33.1 Controller→Facade

```text
Controller
   ↓
Facade @Transactional
   ↓
TX BEGIN
   ↓
Service / DAO
```

## 33.2 Controller→Service

Service에 Transaction Annotation이 없다면:

```text
Controller
   ↓
Service
   ↓
DAO

[TX 경계가 Facade 기반 표준과 다름]
```

정확한 각 Service의 Annotation은 거래별 Source로 검증해야 한다.

따라서 TCF OFF의 Transaction 일관성은 현재 GAP다.

---

# 34. FIG-V-16 — Runtime Mode / Transaction Matrix

| Mode | Thread | 외곽 TX | Facade 역할 | Timeout |
|---|---|---|---|---|
| TCF ON + Timeout ON | Worker | `TransactionTemplate` | REQUIRED 참여 | 5000ms AS-IS |
| TCF ON + Timeout OFF | Request | 없음 | Facade가 TX 시작 후보 | TCF Worker Timeout 없음 |
| TCF OFF + Facade | Request | 없음 | Facade가 TX 시작 | TCF Worker Timeout 없음 |
| TCF OFF + Service 직행 | Request | 없음 | 우회 | Service 설정에 따라 다름 |

이 표는 **TCF OFF에서 Transaction이 항상 없는 것**을 의미하지 않는다.

의미는:

```text
TCF 외곽 TransactionTemplate가 없다
```

이다.

---

# 35. FIG-V-15 — Timeout Budget Layers

완성된 Target 구조에서는 Timeout을 한 숫자로 관리하면 안 된다.

## 35.1 계층

```text
[Client / UI Timeout]
          │
          ▼
[Gateway / WEB / Server Timeout]
          │
          ▼
[PDMG Request Wait Timeout]
          │
          ▼
[Transaction Deadline]
          │
          ▼
[DB Connection Wait]
          │
          ▼
[JDBC Statement / Query Timeout]
          │
          ▼
[DB Execution / Resource Control]
```

## 35.2 원칙 후보

아래는 **TO-BE 정책 원칙**이지 현재 PDMG 실설정 사실이 아니다.

```text
DB Query Timeout
      <
Transaction / Worker Deadline
      <
Server / Downstream Timeout
      <
Client Timeout
```

상위가 하위보다 먼저 Timeout되어 내부 작업만 살아남는 구조를 줄이는 방향이다.

## 35.3 현재 Gap

정확한 다음 값의 조합이 현재 Source Evidence에 모두 존재하지 않는다.

```text
Client
Gateway
Apache
Tomcat
PDMG Worker
Spring TX
Hikari Connection Wait
MyBatis/JDBC Statement
Oracle
```

따라서 Timeout Budget Matrix는 별도 Baseline 작업이 필요하다.

---

# 36. Timeout Budget 예시를 현재값처럼 쓰지 않는다

예를 들어:

```text
DB 3s
Server 4s
Client 5s
```

같은 값은 현재 Source의 사실이 아니다.

본 정의서에서 확정된 현재 PDMG 값은:

```text
OnlineTimeoutExecutor Request Wait = 5000ms
```

뿐이다.

---

# 37. Query Timeout / Statement Timeout

현재 업로드 Source Evidence에서 다음의 명시적인 현재 설정값은 확인되지 않았다.

```text
MyBatis defaultStatementTimeout
Statement.setQueryTimeout(...)
Oracle JDBC read timeout
Spring @Transactional(timeout=...)
TransactionTemplate.setTimeout(...)
```

따라서:

```text
PDMG DB Query도 자동 5초 Timeout
```

이라고 쓰지 않는다.

`[GAP-V-02]`

---

# 38. TransactionTemplate 자체 Timeout

현재 Source 분석은 다음을 명확히 보여 준다.

```text
Request Wait Timeout = 5000ms
Deadline Check = 존재
TransactionTemplate = 존재
```

하지만 현재 Evidence만으로:

```text
TransactionTemplate.setTimeout(5)
```

또는 동일한 Spring TX timeout이 직접 설정돼 있다고 확정하지 않는다.

따라서 Worker Deadline과 Spring Transaction timeout은 **동일개념으로 병합하지 않는다.**

---

# 39. Timeout 발생 후의 세 가지 방어영역

```text
1. 사용자 응답 방어
Future.get(timeout)
→ 빠른 504

2. Application Commit 방어
Deadline Check
→ rollbackOnly

3. DB 실행 방어
Statement/Driver/DB Timeout
→ 현재 설정 Evidence 미확정
```

현재 PDMG는 1과 2를 Source로 확인할 수 있고, 3은 추가 검증 대상이다.

---

# 40. Failure Scenario A — 정상조회

```text
Request Thread
  ↓ submit

Worker
  ↓ TX BEGIN
Dispatcher
  ↓
Handler
  ↓
Facade(readOnly)
  ↓
Service
  ↓
SELECT
  ↓
Return
  ↓
Deadline OK
  ↓
COMMIT/Complete
  ↓
Response 200
```

조회 Transaction의 Commit이라는 표현은 DB 변경 Commit보다 Transaction 정상 완료 의미로 읽는다.

---

# 41. Failure Scenario B — 업무예외

```text
Worker TX BEGIN
   ↓
Service
   ↓
BizException
   ↑
Facade
   ↑
TransactionTemplate
   ↓
ROLLBACK
   ↓
Request Thread
   ↓
Error Response
```

주의:

```text
BizException을 catch 후 정상 DTO로 반환
```

하면 Rollback 계약이 달라질 수 있다.

---

# 42. Failure Scenario C — DB Exception

```text
Worker TX BEGIN
   ↓
DAO
   ↓
Mapper
   ↓
DB Error
   ↓
Exception
   ↑
Service
   ↑
Facade
   ↑
Worker Transaction
   ↓
ROLLBACK
```

Error Response 표준화는 VI장으로 넘긴다.

---

# 43. Failure Scenario D — Request Timeout / Worker Late Return

```text
0ms    Worker TX BEGIN
        ↓
        SQL

5000ms Request Thread
        ↓
        timeout
        ↓
        cancel(true)
        ↓
        504

5300ms Worker
        ↓
        SQL Return
        ↓
        Deadline Exceeded
        ↓
        rollbackOnly
        ↓
        ROLLBACK
```

핵심:

```text
사용자 실패
+
DB Rollback
```

로 정합성을 맞추려는 구조다.

---

# 44. Failure Scenario E — Overload

```text
20 Worker Busy
+
100 Queue Full
   ↓
New Task Rejected
   ↓
TX 미시작
   ↓
503 FW_OVERLOADED
```

이 경우 DB Rollback할 Transaction 자체가 없을 수 있다.

---

# 45. Failure Scenario F — Context Leak

Worker Pool Thread는 재사용된다.

```text
Worker Thread
거래 A
   ↓
Context install
   ↓
업무
   ↓
clear 누락
   ↓
Thread 반환

거래 B
   ↓
같은 Thread 재사용
   ↓
A의 GUID/User 남음
```

현재 `workerContext.clear()`는 이 위험을 막는 핵심 정리 코드다.

---

# 46. Failure Scenario G — Mutable Context Race

```text
Request Thread
   │ 504 응답 처리
   │ responseBody 수정
   │
   │ 같은 object
   ▼
ServiceContext
   ▲
   │ Worker 아직 실행
   │ Header/User/Context 조회
Worker Thread
```

TO-BE에서는 Worker용 최소 immutable Context가 더 안전하다.

---

# 47. Failure Scenario H — TransactionManager 불일치

가상 예시:

```text
Worker TransactionTemplate
→ rdwTransactionManager

Facade
→ anotherTransactionManager
```

이면:

```text
외부 TX와 내부 TX가 분리
```

될 수 있다.

현재 주요 PDMG Facade는 `rdwTransactionManager`로 정렬되어 있다는 Evidence가 있다.

신규 업무축 추가 시 이 정렬을 Conformance Rule로 검증해야 한다.

---

# 48. Failure Scenario I — Exception Swallow

```text
TX BEGIN
  ↓
DAO update 성공
  ↓
다음 Service 오류
  ↓
catch(Exception)
  ↓
빈 Output 반환
  ↓
Transaction 정상 반환으로 판단
  ↓
COMMIT 가능
```

금지:

```text
"로그 찍었으니 실패 처리됨"
```

반드시 Transaction 상태와 연동되어야 한다.

---

# 49. Transaction과 Retry

현재 V장 Source는 Runtime Retry 정책을 확정하지 않는다.

따라서:

```text
Timeout → 자동 Retry
```

를 PDMG AS-IS로 쓰지 않는다.

금융/쓰기 거래에서 Retry는:

```text
Idempotency
Duplicate Prevention
Transaction Outcome
External Side Effect
```

을 함께 검토해야 한다.

VI 또는 별도 Interface/Control 장에서 다룬다.

---

# 50. Worker Queue와 Tomcat Thread 관계

개념적으로:

```text
Tomcat Request Thread
  ↓
Future.get
  ↓
대기

PDMG Worker Thread
  ↓
Business/DB
```

Timeout 구조는 Tomcat Request Thread가 Worker 결과를 기다리므로:

```text
Worker 분리
≠
Tomcat Thread 즉시 반환
```

이다.

즉 5초 동안 Request Thread도 대기한다.

VIII장에서 다음을 함께 산정해야 한다.

```text
Tomcat maxThreads
Online Worker pool
Worker Queue
Hikari Pool
DB Session
평균/최악 처리시간
```

---

# 51. Worker Pool을 늘리면 해결되는가

단순히:

```text
20 → 200
```

으로 늘리면 반드시 성능이 좋아지는 것이 아니다.

가능한 전파:

```text
Worker 증가
   ↓
동시 DB 요청 증가
   ↓
Hikari 대기 또는 DB Session 증가
   ↓
DB CPU/I/O 증가
   ↓
SQL 지연
   ↓
더 많은 Worker 장기점유
```

따라서 Pool은 **전체 Capacity Chain**으로 결정한다.

---

# 52. DB Connection Wait와 Online Timeout

정확한 Hikari `connectionTimeout` 현재값은 이 장 Source Evidence에서 확인하지 않는다.

하지만 구조적으로:

```text
Worker
  ↓
DAO
  ↓
Connection 필요
  ↓
Pool 대기
```

시간도 Online Deadline 안에 포함될 수 있다.

따라서 목표 정책에서는:

```text
Connection Wait
<
Online Deadline
```

가 되도록 설계하는 것이 합리적이다.

이 문장은 `[PROPOSED]` 정책이며 현재 설정 사실이 아니다.

---

# 53. External Call과 Transaction

현재 V장 Source는 주요 RDW DB Transaction을 중심으로 한다.

업무 Transaction 안에서 Remote API/MCA 호출이 존재하는 경우:

```text
DB TX BEGIN
   ↓
Remote Call
   ↓
상대 시스템 대기
   ↓
DB Connection 장기점유
```

가 될 수 있다.

따라서 향후 ServiceId별로:

```text
DB TX 내부 Remote Call 존재 여부
```

를 자동검증/리뷰해야 한다.

현재 PDMG 주요 샘플에 대한 전수 결과는 본 장 Evidence에서 확정하지 않는다.

`[OPEN-V-03]`

---

# 54. Transaction 안에서 하지 말아야 할 것

TO-BE 원칙 후보:

```text
대용량 파일 I/O
무제한 Remote Retry
긴 Sleep
사용자 입력 대기
대량 Event Publish 동기대기
불필요한 외부 API 호출
```

이런 작업은 DB Transaction 수명을 길게 만들 수 있다.

단, 실제 PDMG Source 위반 여부는 별도 Scan이 필요하다.

---

# 55. Thread / Transaction / Context Matrix

| 요소 | Request Thread | Worker Thread | DB TX |
|---|:---:|:---:|:---:|
| DefaultFilter | ● |  | 밖 |
| Interceptor.pre | ● |  | 밖 |
| Controller | ● |  | 밖 |
| TcfFacade | ● |  | 밖 |
| Future.get | ● |  | 밖 |
| WorkerContext install |  | ● | 직전 |
| TransactionTemplate |  | ● | 시작 |
| Dispatcher |  | ● | 안 |
| Handler |  | ● | 안 |
| Facade |  | ● | 안 |
| BizPre |  | ● | 안 |
| Service |  | ● | 안 |
| DAO/Mapper |  | ● | 안 |
| Deadline |  | ● | 안 |
| Commit/Rollback |  | ● | 종료 |
| WorkerContext clear |  | ● | 뒤 |
| Response Advice | ● |  | 밖 |
| afterCompletion | ● |  | 밖 |
| Filter cleanup | ● |  | 밖 |

---

# 56. Context / Transaction 분리 Matrix

| 객체 | 저장 방식 | 범위 | DB TX인가 |
|---|---|---|---|
| ServiceContext | ThreadLocal Holder | HTTP 요청 | No |
| TransactionContext | Method Argument | TCF 실행 | No |
| WorkerContext | 명시적 Snapshot/참조 | Worker 실행 | No |
| MDC | ThreadLocal Logging | Thread | No |
| TransactionStatus | Spring TX 내부 | Worker TX | Yes 관련 |
| JDBC Connection | Transaction Resource | DB TX | Yes 관련 |

`ServiceContext`와 `TransactionContext`라는 이름 때문에 DB Transaction으로 오해하지 않는다.

---

# 57. Transaction Outcome Matrix

| 상황 | Request 결과 | Worker | DB Outcome |
|---|---|---|---|
| 정상 | 200 | 정상 종료 | Commit/Complete |
| BizException | 오류 | 예외 | Rollback |
| Handler 없음 | 오류 | Dispatcher/Handler 전 실패 | Rollback 또는 DB 미사용 |
| Queue Full | 503 | 미실행 | TX 미시작 |
| Timeout + 늦은 SQL | 504 | 늦게 복귀 | Deadline으로 Rollback |
| Filter 401 | 401 | 미실행 | TX 미시작 |
| 일반 DB 오류 | 오류 | 예외 | Rollback |
| Exception swallow | 정상처럼 보일 수 있음 | 정상 반환 | Commit 위험 |

---

# 58. Timeout 계층 요구사항

TO-BE에서는 각 Timeout마다 Owner가 있어야 한다.

| Layer | Timeout 목적 | Owner 후보 | 현재 값 |
|---|---|---|---|
| UI/Client | 사용자 대기 상한 | UI | `[UNKNOWN]` |
| Gateway/WEB | 외부 요청 상한 | TA/IA | `[UNKNOWN]` |
| PDMG Worker | Application 거래 상한 | FW | `5000ms AS-IS` |
| Spring TX | Transaction 상한 | FW/DA | `[UNKNOWN]` |
| Connection Wait | Pool 획득 상한 | TA/FW | `[UNKNOWN]` |
| JDBC Query | SQL 실행 상한 | DA/FW | `[UNKNOWN]` |
| DB Resource | DB 실행/자원 통제 | DBA | `[UNKNOWN]` |

---

# 59. FIG-V-17 — Failure / Risk / Anti-Pattern Map

```text
[1] Timeout = DB Kill
Future timeout
  └─ "DB 즉시 종료"
       X

[2] cancel(true) = SQL Cancel
Interrupt request
  └─ "Oracle Session Kill"
       X

[3] Facade Annotation = 항상 TX BEGIN
Facade @Transactional
  └─ 외부 TX 존재 무시
       X

[4] Handler TX 밖
TransactionTemplate가 Dispatcher를 감싸는데
Handler 밖으로 그림
       X

[5] ThreadLocal 자동전파
Request Context
  └─ Worker 자동 전달
       X

[6] Exception swallow
catch
  └─ 정상 Output
       X

[7] Worker pool만 확대
20→200
  └─ DB Capacity 무시
       X

[8] 5000ms = 전사 SLA
AS-IS 설정
  └─ Target NFR로 승격
       X

[9] ImageLog = Business Commit
Audit 로그 성공
  └─ 업무 성공으로 판정
       X
```

---

# 60. 현재 GAP

| ID | GAP | 영향 |
|---|---|---|
| GAP-V-01 | Worker TransactionTemplate가 조회/쓰기별 readOnly 속성을 어떻게 다르게 적용하는지 미확정 | 조회 최적화 |
| GAP-V-02 | JDBC/MyBatis/DB Query Timeout 현재값 미확정 | 실제 Timeout 강제력 |
| GAP-V-03 | Spring Transaction timeout 현재값 미확정 | Worker Deadline과 중복/공백 |
| GAP-V-04 | Hikari Connection Pool/connectionTimeout 현재값 미확정 | Worker-DB Capacity |
| GAP-V-05 | JDBC Driver가 `cancel(true)` Interrupt에 어떻게 반응하는지 Runtime Evidence 없음 | Timeout Worker 종료 |
| GAP-V-06 | Worker Context가 동일 mutable ServiceContext 참조 사용 | Thread Safety |
| GAP-V-07 | TCF OFF Transaction 경계가 Controller마다 다름 | ON/OFF 일관성 |
| GAP-V-08 | Exception swallow 방지 Coding Rule/Static Rule 미완성 | Rollback 보장 |
| GAP-V-09 | Remote Call이 DB TX 내부에 존재하는지 전수 Scan 미완료 | TX 장기점유 |
| GAP-V-10 | Timeout Budget 전체 Layer 값 미확정 | 장애 전파 |
| GAP-V-11 | Worker Queue/Pool과 운영 Capacity의 근거 미확정 | Overload |
| GAP-V-12 | ImageLog 실패 재처리/보상 정책 미확정 | Audit 완전성 |

---

# 61. Current RISK

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-V-01 | Timeout 후 Request/Worker가 동일 mutable ServiceContext 공유 | High |
| RISK-V-02 | HTTP 504 후 JDBC가 계속 실행 | High |
| RISK-V-03 | Query Timeout 부재 시 Worker 장기점유 | High |
| RISK-V-04 | Connection Pool 대기까지 Deadline 소모 | High |
| RISK-V-05 | Exception swallow로 Commit | Critical |
| RISK-V-06 | TCF OFF Facade 우회로 TX 부재/변형 | High |
| RISK-V-07 | Worker pool 과대확장으로 DB 압박 | High |
| RISK-V-08 | Worker pool 과소설정으로 Queue/503 | Medium/High |
| RISK-V-09 | Remote Call inside TX | High |
| RISK-V-10 | ImageLog와 Business Result 불일치 | Medium |
| RISK-V-11 | Timeout 계층 역전 | High |
| RISK-V-12 | AS-IS 5000ms를 Target SLA로 오인 | Medium |

---

# 62. OPEN Issue

| ID | 질문 |
|---|---|
| OPEN-V-01 | Connection은 실제 현재 구성에서 Transaction BEGIN 즉시 checkout되는가 첫 SQL 시점인가 |
| OPEN-V-02 | BizPre/Post Aspect와 Transaction Advisor의 Proxy Order는 정확히 어떻게 구성되는가 |
| OPEN-V-03 | DB Transaction 안에서 Remote API/MCA 호출하는 ServiceId가 존재하는가 |
| OPEN-V-04 | Worker TransactionTemplate에 Spring TX Timeout도 설정할 것인가 |
| OPEN-V-05 | JDBC/MyBatis Query Timeout을 전 ServiceId 공통으로 할 것인가 업무별로 둘 것인가 |
| OPEN-V-06 | Timeout Worker가 Servlet 객체를 포함한 ServiceContext를 참조하지 않도록 바꿀 것인가 |
| OPEN-V-07 | TCF OFF를 운영에서 제거하거나 동일 Timeout/TX Wrapper를 적용할 것인가 |
| OPEN-V-08 | Overload 20/100을 어떤 부하시험 근거로 재산정할 것인가 |
| OPEN-V-09 | ImageLog를 별도 Transaction으로 유지하되 실패 보상은 어떻게 할 것인가 |
| OPEN-V-10 | readOnly 조회를 외부 Worker TX에서 어떻게 보장할 것인가 |

---

# 63. ADR 후보

| ADR | 결정 주제 |
|---|---|
| ADR-V-01 | PDMG Online Timeout Budget 계층 정의 |
| ADR-V-02 | JDBC Query Timeout 표준 적용 위치 |
| ADR-V-03 | Spring Transaction Timeout과 Worker Deadline 관계 |
| ADR-V-04 | Worker Context immutable snapshot 전환 |
| ADR-V-05 | TCF OFF Transaction/Timeout 통일 |
| ADR-V-06 | Worker Pool/Queue Capacity 산정 기준 |
| ADR-V-07 | readOnly Transaction 외곽정책 |
| ADR-V-08 | Exception Swallow 금지 자동검증 |
| ADR-V-09 | DB TX 내부 Remote Call 금지/예외기준 |
| ADR-V-10 | ImageLog Transaction/Retry 정책 |

---

# 64. TO-BE Transaction / Timeout Policy 후보

```text
Client
  │  상위 Budget
  ▼
Gateway / WEB
  │
  ▼
PDMG Online Deadline
  │
  ├─ Worker Queue Budget
  ├─ Transaction Budget
  ├─ Connection Wait Budget
  └─ SQL Query Budget
       │
       ▼
     DB
```

## 64.1 Policy 후보

1. 하위 Timeout이 상위 Timeout보다 충분히 짧아야 한다.
2. Worker Deadline을 넘긴 거래는 Commit하지 않는다.
3. `cancel(true)`만을 DB 취소 보장으로 사용하지 않는다.
4. JDBC/DB Timeout을 별도 계층으로 둔다.
5. Worker Context는 Servlet 객체를 제거한 최소 Snapshot으로 전달한다.
6. ON/OFF가 동일 Business Facade와 Transaction 계약을 공유한다.
7. 쓰기 Transaction 안에서 장시간 Remote Call을 제한한다.
8. Exception swallow를 Coding/Architecture Test로 차단한다.
9. Worker/Queue/Connection Pool을 하나의 Capacity Chain으로 산정한다.
10. Timeout/Overload/late rollback 지표를 OM에 노출한다.

`[PROPOSED]`이며 승인 전 Decision이 아니다.

---

# 65. 운영 모니터링 필수지표 후보

IX장으로 넘길 최소 지표:

```text
online worker active
online worker queue depth
online rejected count
online timeout count
worker late completion count
deadline rollback count
transaction rollback count
DB connection active
DB connection pending
SQL slow count
SQL timeout count
TCF ON/OFF request count
```

현재 `pdmg-om`에 실제로 존재한다고 단정하지 않는다.

---

# 66. 테스트 시나리오

## 66.1 정상 Commit

```text
Given
  TCF ON / Timeout ON
  정상 SQL < 5초

Expect
  HTTP 200
  Worker same GUID
  TX Commit
  Queue 정상
```

## 66.2 Business Exception

```text
Service throws BizException
Expect
  TX Rollback
  Error response
  Worker Context clear
```

## 66.3 Checked Exception

```text
Write Facade path
Checked Exception
Expect
  rollbackFor=Exception 계약 검증
```

## 66.4 Exception Swallow

```text
Service/Facade catch Exception and return normal
Expect
  Test가 위험을 검출
```

## 66.5 Timeout with late SQL

```text
SQL > 5초
Expect
  Request 504
  cancel requested
  Worker eventual return
  deadline exceeded
  Rollback
  no late commit
```

## 66.6 Queue Full

```text
20 worker busy
100 queue full
new request
Expect
  503
  Business TX not started
```

## 66.7 Context Propagation

```text
Request GUID=X
Worker
Expect
  GUID=X
  ServiceId same
  user same
```

## 66.8 Context Clear

```text
same worker thread reused
transaction A → B
Expect
  A context not visible in B
```

## 66.9 TCF OFF

```text
same business input
ON vs OFF
Compare
  DB outcome
  response
  timeout
  transaction
```

## 66.10 Path with remote call in TX

전수 Source Scan/Integration Test로:

```text
TX active
→ outbound HTTP/MCA
```

존재여부를 검출한다.

---

# 67. Architecture Conformance Rule 후보

```text
RULE-V-01
Handler must not call DAO directly

RULE-V-02
TCF OFF Controller should call Facade

RULE-V-03
Write Facade must declare rollback policy

RULE-V-04
Online business package must use rdwTransactionManager where RDW DAO is used

RULE-V-05
No ThreadLocal leak after worker completion

RULE-V-06
No generic catch-and-normal-return inside transactional use case

RULE-V-07
No Servlet Request/Response in immutable Worker Context target

RULE-V-08
Every online ServiceId must have timeout policy classification

RULE-V-09
Every Mapper/SQL must have query-timeout classification

RULE-V-10
DB TX internal remote call requires approved exception
```

일부는 정적분석만으로 충분하지 않고 Runtime/Integration Test가 필요하다.

---

# 68. Traceability

## 68.1 IV → V

```text
IV
OnlineTimeoutExecutor
      ↓
V
Executor Pool / Future / Worker / TX / DB

IV
ServiceContext
      ↓
V
Worker Context / Thread Safety

IV
Facade / Service / DAO
      ↓
V
Transaction Propagation / Commit / Rollback

IV
504 / 503
      ↓
V
Timeout / Overload Mechanism
```

## 68.2 V → VI

```text
Transaction Outcome
      ↓
Error Classification

Timeout / Overload
      ↓
Error Code / Response Envelope

GUID / Worker
      ↓
Log / Trace

Rollback / Commit
      ↓
Audit / ImageLog
```

---

# 69. FIG-V-18 — VI장 Handoff

```text
V. Transaction / Timeout / Thread / DB
      │
      ├─ Request Thread
      ├─ Worker Thread
      ├─ Worker Context
      ├─ TransactionTemplate
      ├─ @Transactional
      ├─ Commit / Rollback
      ├─ Timeout / Cancel
      ├─ Deadline
      ├─ DB Connection
      ├─ SQL
      └─ Transaction Outcome
              │
              ▼
VI. Standard Message / Context / Error / Logging
              │
              ├─ Request Envelope
              ├─ Common Header
              ├─ ServiceContext
              ├─ TransactionContext
              ├─ Validation
              ├─ Exception Taxonomy
              ├─ Error Mapping
              ├─ Response Envelope
              ├─ ImageLog
              ├─ Business Log
              ├─ Audit
              └─ End-to-End GUID Trace
```

## VI장에서 반드시 답할 질문

1. `hdr_nhnis`와 `dto`는 어떤 책임으로 분리되는가?
2. ServiceContext와 TransactionContext의 정확한 필드·수명은 무엇인가?
3. Timeout/Overload/Biz/DB 예외를 어떤 Error Taxonomy로 분류하는가?
4. Filter 조기 오류도 표준 Error Envelope로 만들 것인가?
5. GUID/ServiceId/User/IP는 어떤 Log에 들어가는가?
6. Request/Response ImageLog와 Business Log는 어떤 Transaction 관계인가?
7. 개인정보/Token/전문 원문을 어디까지 로그할 수 있는가?
8. Commit/Rollback 결과를 Audit와 어떻게 연결하는가?
9. Error Code와 HTTP Status 정책을 어떻게 통일하는가?
10. 한 거래를 GUID로 Browser→Worker→SQL→Response까지 추적할 수 있는가?

---

# 70. Architecture Rules

## 70.1 Must

1. HTTP Timeout과 DB Rollback을 같은 시점으로 표현하지 않는다.
2. `cancel(true)`를 DB 강제종료로 표현하지 않는다.
3. Handler를 Worker Transaction 밖으로 그리지 않는다.
4. Facade `@Transactional` Annotation 위치를 항상 물리 BEGIN 위치로 표현하지 않는다.
5. Worker ThreadLocal Context는 명시적으로 install/clear한다.
6. Transaction 실패를 catch 후 정상 DTO로 숨기지 않는다.
7. SQL 성공 후 Deadline 초과이면 Commit하지 않는다.
8. Worker Pool 값과 DB Pool 값을 동일한 값으로 가정하지 않는다.
9. 현재 `5000ms/20/100`을 NSIGHT Target NFR로 승격하지 않는다.
10. Source에 없는 JDBC Query Timeout 값을 만들지 않는다.
11. ImageLog 성공을 Business Commit 성공으로 판단하지 않는다.
12. TCF OFF에도 Worker TransactionTemplate가 있다고 설명하지 않는다.

## 70.2 Should

1. Worker Context를 immutable 최소 Snapshot으로 바꾼다.
2. Query Timeout을 Worker Deadline보다 짧게 설계한다.
3. Connection Wait Timeout도 Worker Deadline 예산 안에 둔다.
4. ON/OFF가 동일 Facade/TX 계약을 사용하도록 정렬한다.
5. Worker/Queue/DB Pool을 통합 Capacity Model로 관리한다.
6. late worker/late rollback을 운영 Metric으로 수집한다.
7. DB TX 안 Remote Call을 제한한다.
8. Timeout 계층을 SSOT Matrix로 관리한다.

---

# 71. 검증 체크리스트

## 71.1 Thread

- [x] Request/Worker가 분리되어 있는가
- [x] Worker 이름 `pdmg-online-N`이 표시되는가
- [x] ThreadLocal 자동전파라고 쓰지 않았는가
- [x] Worker clear가 존재하는가
- [x] 동일 mutable Context Risk를 표시했는가

## 71.2 Transaction

- [x] TransactionTemplate가 Dispatcher 전체를 감싸는가
- [x] Handler가 TX 안에 있는가
- [x] Facade REQUIRED 참여가 표현되는가
- [x] readOnly/rollbackFor를 현재 Source 기준으로 구분했는가
- [x] Exception swallow Risk가 있는가

## 71.3 Timeout

- [x] Future.get(5000ms)이 Current AS-IS로 표시되는가
- [x] cancel(true)가 interrupt request로 표현되는가
- [x] HTTP 504와 Worker 종료가 분리되는가
- [x] Deadline late commit 방지가 표현되는가
- [x] Query Timeout 미확정을 숨기지 않았는가

## 71.4 DB

- [x] rdwDataSource / SqlSession / TransactionManager 축이 표현되는가
- [x] Connection Pool 현재값을 창작하지 않았는가
- [x] Worker Pool과 DB Pool을 구분했는가
- [x] Connection lifecycle 세부는 Framework Semantics로 구분했는가

## 71.5 Mode

- [x] TCF ON+Timeout ON을 주 경로로 사용했는가
- [x] Timeout OFF에서 Facade가 TX 시작 후보임을 표시했는가
- [x] TCF OFF Service 직행 GAP가 표시되는가

---

# 72. Completion Gate

```text
Figure Plan                       18
실제 Text Figure                 18

Big Picture                      PASS
Request vs Worker                PASS
Pool / Queue                     PASS
Worker Context                   PASS
DB TX Boundary                   PASS
TransactionTemplate vs Facade    PASS
ReadOnly / Write                 PASS
Normal Commit                    PASS
Business Rollback                PASS
Timeout Timeline                 PASS
Cancel / Interrupt / JDBC        PASS
Deadline Late Commit             PASS
DB Connection                    CONDITIONAL
Business vs ImageLog TX          PASS
Timeout Budget                   CONDITIONAL
Mode Matrix                      PASS
Failure / Anti-pattern           PASS
VI Handoff                       PASS

확인되지 않은 Hikari 수치         0건
확인되지 않은 Query Timeout       0건
cancel=DB kill 오기재             0건
Handler TX 밖 오기재              0건
5000ms Target 승격                0건
```

**판정: CONDITIONAL PASS**

## PASS 전환 조건

```text
Condition-V-01
RdwDataSourceConfig / Hikari 현재 설정 전체 Source 재스캔

Condition-V-02
MyBatis/JDBC Query Timeout 현재 설정 재스캔

Condition-V-03
TransactionTemplate의 Spring Transaction Timeout 설정 여부 Source 재검증

Condition-V-04
Oracle JDBC Driver cancel/interrupt 동작 Integration Test

Condition-V-05
Worker mutable ServiceContext를 immutable snapshot으로 바꿀지 ADR

Condition-V-06
TCF OFF Transaction/Timeout 정책 확정

Condition-V-07
전체 Timeout Budget(Client~DB) Baseline 확정

Condition-V-08
Worker Pool/Queue 값의 부하시험 근거 확보
```

---

# 73. 장 최종 평가

V장은 PDMG의 온라인 거래를 **Thread, Transaction, Timeout, Connection, Commit/Rollback** 관점으로 다시 해석했다.

핵심은 다음과 같다.

> **현재 TCF ON + Timeout ON에서 업무는 Request Thread가 아니라 `pdmg-online-N` Worker에서 실행되고, Worker의 `TransactionTemplate`이 Dispatcher부터 Handler·Facade·Service·DAO와 Deadline 검사까지 하나의 RDW Transaction으로 감싼다.**

> **Facade `@Transactional(REQUIRED)`는 이 외부 Transaction에 참여하며, Annotation이 있는 위치와 실제 물리 Transaction BEGIN 위치는 동일하지 않을 수 있다.**

> **HTTP 504는 Worker/JDBC/DB Rollback 완료를 의미하지 않는다. `Future.cancel(true)`는 중단 요청이고 실제 JDBC 종료는 별도 문제다.**

> **PDMG는 Deadline 재검사를 통해 늦게 정상 반환한 SQL이 제한시간 이후 Commit되는 것을 막는 방어를 가지고 있다.**

> **그러나 DB Query Timeout, Spring Transaction Timeout, Hikari Connection Wait, Client/Gateway Timeout의 전체 예산은 현재 Source Evidence만으로 닫히지 않는다.**

> **또한 Worker가 동일 mutable ServiceContext 객체를 공유하는 현재 구조는 Timeout 이후 Request/Worker 동시 접근 Risk가 있어 TO-BE 보완 후보가 된다.**

다음 VI장에서는 이 Transaction 결과를 사용자와 운영시스템이 이해할 수 있는 **Message / Context / Error / Log Evidence**로 변환한다.

```text
V
Thread / Transaction / Timeout / DB Outcome
       ↓
VI
Standard Message / Context / Error / Logging / Audit
```
