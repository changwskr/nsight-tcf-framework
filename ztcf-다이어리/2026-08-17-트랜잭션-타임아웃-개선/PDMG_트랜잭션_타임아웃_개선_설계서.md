# PDMG 트랜잭션 타임아웃 개선 설계서

---

document-status: PROPOSED
system-scope: PDMG
source-baseline: nsight-tcf-framework @ develop (2026-08-22)
verified-against-source: true
architecture-area: TCF / Transaction / Timeout / Runtime Evidence
change-type: Architecture Improvement
priority: P0
related-docs:
  - README.md
  - pdmg-tx-timeout-easy.md
  - pdmg-tx-processing.md
  - timeout-tx-mg-guide.md

---

## 0. 문서 세트

| 문서 | 역할 |
| --- | --- |
| [README.md](./README.md) | 읽는 순서·소스 위치·구현 스냅샷 |
| [pdmg-tx-timeout-easy.md](./pdmg-tx-timeout-easy.md) | 초보자용 3계층 Timeout 개념 |
| [pdmg-tx-processing.md](./pdmg-tx-processing.md) | `@Transactional(REQUIRED)` 참여 조건 |
| [timeout-tx-mg-guide.md](./timeout-tx-mg-guide.md) | pdmg-fw / pdmg-service 수정 가이드 |
| **본 문서** | AS-IS / GAP / TO-BE / Gate / 테스트 |

> **SSOT:** `pdmg-fw/docs/timeout/01.timeout.md` 는 구현 이전 초안이다. 타임아웃 설계의 공식 기준은 **본 `ztcf-다이어리` 세트**를 따른다.

---

## 0.1 문서 목적

본 문서는 현재 PDMG Framework의 온라인 거래 타임아웃 구조를 실제 `pdmg-fw`, `pdmg-service` 소스를 기준으로 분석하고, 단순한 `Future.get(timeout)` 기반 응답 타임아웃을 **실제 트랜잭션·SQL·외부호출·Runtime Evidence까지 연결되는 End-to-End Deadline 제어 구조**로 개선하기 위한 목표 아키텍처와 구현 기준을 정의한다.

이 문서의 핵심 목표는 다음과 같다.

```text
현재
Future.get(timeout)
        ↓
응답 TIMEOUT
        ↓
future.cancel(true)
        ↓
실제 DB/JDBC 종료는 별도

개선
ServiceId Deadline
        ↓
Queue Budget
        ↓
Worker Budget
        ↓
Transaction Timeout
        ↓
JDBC / MyBatis Statement Timeout
        ↓
External Call Timeout
        ↓
Rollback Completion
        ↓
Runtime Evidence
```

즉, **"사용자에게 TIMEOUT 응답을 반환했다"와 "실제 업무 트랜잭션이 종료되었다"를 동일한 것으로 간주하지 않는 것**이 본 개선의 출발점이다.

---

# 1. 적용 범위

## 1.1 대상 모듈

| 모듈           | 적용 내용                                                                       |
| -------------- | ------------------------------------------------------------------------------- |
| `pdmg-fw`      | TCF Timeout Executor, Deadline Context, Transaction Boundary, Runtime 상태관리  |
| `pdmg-service` | RDW TransactionManager, MyBatis, ServiceId별 Transaction Policy                 |
| `pdmg-ui`      | 직접 변경 없음. 응답 TIMEOUT 표준코드 연계 대상                                 |
| `pdmg-jwt`     | 직접 변경 없음                                                                  |
| OM/운영관리    | ServiceId별 Timeout/Transaction 정책과 Runtime Evidence 관리 대상으로 확장 가능 |

## 1.2 주요 실제 소스

현재 분석의 핵심 근거는 다음 파일이다.

```text
pdmg-fw/
 └─ src/main/java/nhnis/fw/tcf/timeout/
      ├─ DefaultOnlineTimeoutExecutor.java
      ├─ OnlineTimeoutConfiguration.java
      ├─ OnlineTimeoutExecutor.java
      ├─ OnlineTimeoutProperties.java
      ├─ OnlineTimeoutWorkerContext.java
      └─ SyncOnlineTimeoutExecutor.java

pdmg-fw/
 └─ src/main/java/nhnis/fw/tcf/core/facade/
      └─ TcfFacade.java

pdmg-service/
 └─ src/main/java/nhnis/mg/co/a/config/
      └─ RdwDataSourceConfig.java

pdmg-service/
 └─ src/main/java/nhnis/mg/co/a/application/
      ├─ facade/*Facade.java
      └─ service/*Service.java

pdmg-service/
 └─ src/main/resources/application.yml

pdmg-fw/
 └─ src/test/java/nhnis/fw/tcf/timeout/
      ├─ DefaultOnlineTimeoutExecutorTest.java
      └─ OnlineTimeoutPropertiesTest.java
```

## 1.3 Timeout과 거래통제(TxControl) 분리

PDMG 온라인 경로에서 **제한시간**과 **거래 허용/차단**은 별도 정책이다. `TcfFacade.process()` 실행 순서는 다음과 같다.

```text
MgActiveTransactionRegistry.begin()
        ↓
stf.preProcess()          ← nhnis.fw.txcontrol (MgTxControlService, TB_MG_TX_CONTROL)
        ↓
OnlineTimeoutExecutor     ← nhnis.fw.timeout (Worker + Future.get)
        ↓
dispatcher → Handler → Facade → Service → DAO
        ↓
finally: registry.end() + etf.postProcess()   ← elapsed vs timeout 재점검
```

| 정책 | 설정 키 | 런타임 | 관리 UI/프로그램 |
| --- | --- | --- | --- |
| Timeout | `nhnis.fw.timeout.*` | `DefaultOnlineTimeoutExecutor` | YAML (향후 OM) |
| TxControl | `nhnis.fw.txcontrol.enabled` | `stf` → `MgTxControlService` | `mgcoa9001` |

**`mgcoa9001`은 타임아웃 정책 프로그램이 아니다.** ServiceId별 timeout ms를 CRUD하는 대상이 아니며, 본 개선의 1차 구현 범위에도 포함하지 않는다.

현재 예외 코드(구현됨): `FW_TIMEOUT`(504), `FW_OVERLOADED`(503) — `GlobalExceptionHandler`.  
본 문서 §19의 `FW-TIMEOUT-00x` 세분 코드는 **TO-BE Runtime Evidence**용이다.

---

# 2. 현재 AS-IS 구조

## 2.1 현재 온라인 타임아웃 실행 흐름

현재 `nhnis.fw.timeout.enabled=true`이면 `TcfFacade`에서 Dispatcher 이하가 `DefaultOnlineTimeoutExecutor`를 통해 Worker Thread에서 실행된다.

```text
HTTP Request Thread
        │
        ▼
TcfFacade.process()
        │
        ├─ MgActiveTransactionRegistry.begin()
        │
        ├─ stf.preProcess()          ← 거래통제 (TxControl)
        │
        ▼
DefaultOnlineTimeoutExecutor.execute()
        │
        ├─ ServiceId별 timeoutMs 결정
        ├─ deadlineNanos 생성
        ├─ Worker Pool submit
        │
        ▼
Request Thread
Future.get(timeoutMs)
        │
   ┌────┴────────────┐
   │                 │
완료                 Timeout
   │                 │
   ▼                 ├─ future.cancel(true)
응답                 └─ OnlineTimeoutException

Worker Thread
        │
        ▼
TransactionTemplate
PROPAGATION_REQUIRED
        │
        ▼
Dispatcher
        │
        ▼
Handler
        │
        ▼
Facade
        │
        ▼
Service
        │
        ▼
DAO / MyBatis / DB
        │
        ▼
action.call() 복귀
        │
        ▼
deadline / interrupt 확인
        │
   ┌────┴────┐
   ▼         ▼
COMMIT     ROLLBACK
```

## 2.2 현재 설정

`pdmg-service/src/main/resources/application.yml` 기준 현재 설정은 다음과 같다.

```yaml
nhnis:
  fw:
    timeout:
      enabled: true
      milliseconds: 5000
      overrides:
        mgcoa5530S0: 10000
      pool-size: 20
      queue-capacity: 100
```

즉 현재는 다음 기능이 존재한다.

| 기능                                 | 상태                 |
| ------------------------------------ | -------------------- |
| 공통 Timeout                         | 적용                 |
| ServiceId별 Timeout Override         | 적용                 |
| Worker Pool                          | 적용                 |
| Queue Capacity                       | 적용                 |
| `Future.get(timeout)`                | 적용                 |
| Timeout 시 `future.cancel(true)`     | 적용                 |
| Worker 외곽 `TransactionTemplate`    | 적용                 |
| Worker 복귀 후 Deadline 확인         | 적용                 |
| Deadline 초과 후 Late Commit 방지    | 적용                 |
| `TransactionTemplate.setTimeout()`   | **미적용**           |
| SQL Timeout 동적 연계                | **명시적 구성 없음** |
| Worker 시작 전 Remaining Budget 검사 | **미적용**           |
| ServiceId별 Transaction Mode         | **미적용**           |
| Timeout 응답과 Worker 종료상태 분리  | **미적용**           |
| ETF Handler 후 elapsed 재점검       | **적용** (`etf.checkTimeoutInterval`) |
| 단위 테스트 (Executor/Properties)   | **적용** (`DefaultOnlineTimeoutExecutorTest`) |

## 2.4 소스 검증 스냅샷 (2026-08-22)

아래는 AS-IS 설명의 근거가 되는 **현재 구현** 요약이다.

**`DefaultOnlineTimeoutExecutor` — TX timeout 미설정, deadline은 Worker 복귀 시점에만 검사**

```java
this.transactionTemplate = new TransactionTemplate(transactionManager);
this.transactionTemplate.setPropagationBehavior(PROPAGATION_REQUIRED);
// setTimeout() 없음

Future<T> future = pool.submit(() -> runInWorker(..., deadlineNanos, action));
return future.get(timeoutMs, MILLISECONDS);  // Request Thread 대기

// Worker 내부: action.call() 후 deadline 초과 시 rollbackOnly + OnlineTimeoutException
```

**`OnlineTimeoutConfiguration` — `rdwTransactionManager` 고정 주입**

```java
@Qualifier("rdwTransactionManager") PlatformTransactionManager transactionManager
```

**설정 예 (`pdmg-service/.../application.yml`)**

```yaml
nhnis:
  fw:
    timeout:
      enabled: true
      milliseconds: 5000
      overrides:
        mgcoa5530S0: 10000
    txcontrol:
      enabled: true   # STF 거래통제 — timeout과 독립
```

---

# 3. 현재 구조의 장점

현재 구조는 이전의 단순 동기 실행보다 분명히 개선된 구조다.

## 3.1 Worker Thread 내부에서 Transaction 시작

다음 형태로 동작한다.

```text
Request Thread
      │
      └─ Future 대기

Worker Thread
      │
      └─ TransactionTemplate
             ↓
          TX BEGIN
             ↓
         Dispatcher
             ↓
          Business
```

따라서 Request Thread의 Spring Transaction Context를 Worker로 잘못 전달하는 방식보다 명확하다.

## 3.2 ServiceId별 Timeout을 지원

`OnlineTimeoutProperties.resolveMilliseconds(serviceId)`를 통해 업무별 timeout 설정이 가능하다.

```text
기본 거래       5 sec
mgcoa5530S0    10 sec
...
```

이는 PDMG의 ServiceId 중심 통제 철학과 잘 맞는다.

## 3.3 Timeout 이후 Late Commit 방어

Worker가 업무 호출에서 복귀한 뒤 다음 조건을 검사한다.

```text
Deadline 초과
OR
Worker Interrupted
        ↓
status.setRollbackOnly()
        ↓
OnlineTimeoutException
```

따라서 업무 코드가 제한시간 이후 정상값을 반환하더라도 그대로 Commit되는 위험을 줄였다.

---

# 4. 핵심 GAP 및 위험

## GAP-TX-001. Future Timeout과 실제 Transaction Timeout이 분리되어 있다

현재 `DefaultOnlineTimeoutExecutor` 생성자는 다음 구조다.

```java
this.transactionTemplate = new TransactionTemplate(transactionManager);
this.transactionTemplate.setPropagationBehavior(
    TransactionDefinition.PROPAGATION_REQUIRED
);
```

현재 실제 구현에는 다음 설정이 없다.

```java
transactionTemplate.setTimeout(...);
```

따라서 현재의 `5000ms`는 우선적으로 다음 의미다.

```text
Request Thread가 Future 결과를 기다리는 최대시간
```

그러나 이것이 자동으로 다음을 의미하지는 않는다.

```text
DB Transaction이 반드시 5초 이내 종료
SQL이 반드시 5초 이내 종료
JDBC Driver가 반드시 5초 이내 중단
외부 HTTP가 반드시 5초 이내 종료
```

### 위험

```text
Request Thread
5초 → TIMEOUT 응답

Worker Thread
5초 이후에도 JDBC 호출 중

DB
SQL 계속 수행 가능

결과
사용자 관점 거래 종료
≠
실제 서버 거래 종료
```

### 판정

**P0 / HIGH**

---

## GAP-TX-002. `future.cancel(true)`는 실제 DB 작업 종료 보장이 아니다

현재 Timeout 시:

```java
future.cancel(true);
```

를 호출한다.

이 호출은 실행 Thread에 interrupt를 시도하지만, JDBC Driver 또는 업무 코드가 interrupt를 즉시 처리한다는 보장은 없다.

따라서 PDMG 타임아웃은 다음 두 상태를 구분해야 한다.

```text
TIMEOUT_RESPONSE_SENT

≠

WORKER_TERMINATED
```

### 판정

**P0 / HIGH**

---

## GAP-TX-003. Worker가 Queue에서 오래 대기한 뒤에도 업무 실행을 시작한다

Deadline은 Worker submit 전에 생성되므로 Queue 대기시간도 전체 제한시간에 포함되는 방향은 적절하다.

하지만 현재 Worker는 시작 직후 Remaining Budget을 검사하지 않고 바로 `TransactionTemplate.execute()`로 진입한다.

예:

```text
Service Timeout = 5000ms

Queue Wait       = 4700ms
Worker Start     = 4700ms
Remaining        = 300ms

현재
TX BEGIN
 → Dispatcher
 → SQL 실행 시도

권장
Remaining 300ms
 → Minimum Start Budget 미달
 → TX 시작하지 않음
 → 즉시 Timeout
```

### 판정

**P1 / HIGH**

---

## GAP-TX-004. 외곽 Transaction이 `rdwTransactionManager`로 고정된다

`OnlineTimeoutConfiguration`은 다음 Bean을 고정 주입한다.

```java
@Qualifier("rdwTransactionManager")
PlatformTransactionManager transactionManager
```

따라서 Timeout ON 경로에서는 Dispatcher 아래 모든 업무를 기본적으로 RDW Transaction으로 감싸게 된다.

하지만 실제 거래는 다음과 같이 다양할 수 있다.

```text
조회 Only
변경 거래
Transaction 불필요
Cache Only
외부 API Only
ADW 조회
파일 처리
복합 연계
```

### 개선 원칙

```text
Timeout Policy
        ≠
Transaction Policy
```

두 정책은 반드시 분리한다.

### 판정

**P0 / HIGH**

---

## GAP-TX-005. Facade/Service의 `@Transactional` 속성이 외곽 TX와 충돌할 수 있다

현재 PDMG 업무 코드에는 다음과 같은 구조가 존재한다.

```text
DefaultOnlineTimeoutExecutor
 └─ TransactionTemplate(REQUIRED)
       ↓
    Facade
    @Transactional(readOnly=true)
       ↓
    Service
    @Transactional(readOnly=true)
```

Spring의 `PROPAGATION_REQUIRED` 참여 트랜잭션은 기본적으로 이미 생성된 외부 Physical Transaction에 참여한다.

따라서 내부 Facade/Service의 다음 속성이 기대한 대로 새로운 Physical Transaction 특성으로 반영되지 않을 수 있다.

```text
readOnly
isolation
local timeout
```

현재 외곽 `TransactionTemplate`에는 readOnly 구분도 없다.

### 결과 위험

```text
조회 Service
@Transactional(readOnly=true)

        ↑

외곽 TransactionTemplate
readOnly=false 기본값

        ↓

실제 Physical TX 특성은 외곽 기준
```

### 판정

**P1 / HIGH**

---

## GAP-TX-006. MyBatis 공통 Statement Timeout이 명시적으로 설정되어 있지 않다

현재 `RdwDataSourceConfig`는:

```java
Configuration mybatisConfig = new Configuration();
mybatisConfig.setJdbcTypeForNull(JdbcType.NULL);
mybatisConfig.setCallSettersOnNulls(true);
```

이며 다음 설정은 없다.

```java
mybatisConfig.setDefaultStatementTimeout(...);
```

다만 목표 구조에서는 단순히 고정 SQL Timeout 하나를 설정하는 것보다 **현재 Transaction의 Remaining Timeout을 MyBatis/JDBC까지 전달하는 구조**가 우선이다.

MyBatis는 Transaction Timeout과 Mapper/Default Statement Timeout을 조합할 수 있으므로, PDMG는 이를 활용하되 실제 Oracle Driver 환경에서 반드시 검증한다.

### 판정

**P0 / HIGH**

---

## GAP-TX-007. Timeout 응답 시 ActiveTransaction 상태가 실제 Worker보다 먼저 종료될 수 있다

`TcfFacade.process()`는 다음 형태다 (`MgActiveTransactionRegistry`).

```text
activeTransactionRegistry.begin(context)
        ↓
stf.preProcess(context)
        ↓
onlineTimeoutExecutor.execute(...)
        ↓
Timeout Exception (Request Thread)
        ↓
finally
        ↓
activeTransactionRegistry.end(context)
        ↓
etf.postProcess(context)    ← Handler 완료 후 elapsed 재점검 (Worker 지속과 별개)
```

그러나 `future.cancel(true)` 이후에도 Worker/JDBC가 계속 실행 중일 수 있다.

그러면 운영 관점에서 다음 불일치가 발생할 수 있다.

```text
TCF/OM
거래 END

하지만

Worker
RUNNING

DB TX
ACTIVE 또는 ROLLBACK PENDING
```

### 판정

**P0 / HIGH**

---

## GAP-TX-008. Timeout ON/OFF가 Transaction Architecture까지 변경한다

현재:

```text
Timeout ON
Worker Thread
 → TransactionTemplate
 → Dispatcher
```

반면:

```text
Timeout OFF
Request Thread
 → SyncOnlineTimeoutExecutor
 → Dispatcher
 → Facade @Transactional
```

즉 `timeout.enabled` 설정 변경이 단순 제한시간 기능뿐 아니라 다음까지 바꾼다.

```text
실행 Thread
Transaction Owner
TX BEGIN 위치
Transaction 속성 적용 방식
```

Timeout 기능과 Transaction Boundary는 분리하는 것이 바람직하다.

### 판정

**P1 / HIGH**

---

# 5. 목표 아키텍처 원칙

개선 구조는 다음 원칙을 따른다.

## RULE-TX-001. 하나의 Absolute Deadline을 사용한다

```text
Request 수신시점
      ↓
Deadline 생성
      ↓
Queue
      ↓
Worker
      ↓
Transaction
      ↓
SQL
      ↓
외부연계
```

각 계층이 독립적으로 `5초`, `3초`, `10초`를 다시 시작하지 않는다.

모든 하위 계층은 **남아 있는 시간(Remaining Budget)**을 사용한다.

---

## RULE-TX-002. Timeout과 Transaction Policy를 분리한다

```text
Execution Policy
 ├─ timeoutMs
 ├─ minStartBudgetMs
 ├─ transactionMode
 ├─ transactionManager
 ├─ readOnly
 ├─ isolation
 ├─ sqlSafetyTimeout
 └─ externalCallReserve
```

---

## RULE-TX-003. Timeout ON/OFF와 무관하게 Transaction Boundary는 동일해야 한다

목표:

```text
TCF
 ↓
ExecutionCoordinator
 ↓
TransactionBoundaryExecutor
 ↓
Dispatcher
```

Async Worker 사용 여부만 ExecutionCoordinator가 결정한다.

Transaction Boundary 자체는 별도 컴포넌트가 소유한다.

---

## RULE-TX-004. 거래 시작 가능 여부를 Remaining Budget으로 판정한다

```text
Worker Start
      ↓
remainingMillis()
      ↓
remaining < minStartBudget
      ├─ YES → 실행 금지 / 즉시 Timeout
      └─ NO  → TX BEGIN
```

---

## RULE-TX-005. Transaction Timeout은 Remaining Budget보다 길 수 없다

```text
Remaining Budget = 3400ms

Transaction Timeout
≤ 3sec

SQL Timeout
≤ Transaction Remaining
```

Spring Transaction Timeout이 초 단위이므로 PDMG에서는 보수적으로 계산한다.

예:

```text
remaining = 3400ms
transaction timeout = floor(3400 / 1000) = 3s
```

단 `remaining < 1000ms`이면 Transaction을 새로 시작하지 않는 정책을 기본으로 한다.

---

## RULE-TX-006. 응답 종료와 Worker 종료를 별도 상태로 관리한다

```text
RUNNING
   ↓
DEADLINE_EXCEEDED
   ↓
CANCEL_REQUESTED
   ↓
ROLLBACK_PENDING
   ↓
ROLLED_BACK
   ↓
WORKER_TERMINATED
```

그리고 별도의 클라이언트 상태를 둔다.

```text
CLIENT_WAITING
   ↓
TIMEOUT_RESPONSE_SENT
```

---

# 6. TO-BE 전체 구조

```text
┌────────────────────────────────────────────┐
│                TcfFacade                   │
└─────────────────────┬──────────────────────┘
                      │ ServiceId
                      ▼
┌────────────────────────────────────────────┐
│       ServiceExecutionPolicyResolver       │
│                                            │
│ timeout                                    │
│ transaction mode                           │
│ readOnly                                   │
│ tx manager                                 │
│ min execution budget                       │
└─────────────────────┬──────────────────────┘
                      │
                      ▼
┌────────────────────────────────────────────┐
│          ExecutionDeadline                 │
│                                            │
│ startedAt                                  │
│ deadline                                   │
│ remainingMillis()                          │
│ expired()                                  │
└─────────────────────┬──────────────────────┘
                      │
                      ▼
┌────────────────────────────────────────────┐
│       OnlineExecutionCoordinator           │
│                                            │
│ Timeout ON  → Worker Pool                  │
│ Timeout OFF → Current Thread               │
└─────────────────────┬──────────────────────┘
                      │
                      ▼
              Remaining Budget Check
                      │
                      ▼
┌────────────────────────────────────────────┐
│      TransactionBoundaryExecutor           │
│                                            │
│ NONE                                       │
│ RDW_READ_ONLY                              │
│ RDW_READ_WRITE                             │
│ ADW_READ_ONLY                              │
│ ...                                        │
└─────────────────────┬──────────────────────┘
                      │
                      ▼
            Dynamic TransactionTemplate
                      │
                      ├─ propagation
                      ├─ timeout = remaining
                      ├─ readOnly
                      └─ isolation
                      │
                      ▼
                 Dispatcher
                      │
                      ▼
                   Handler
                      │
                      ▼
                   Facade
                      │
                      ▼
                   Service
                ┌─────┴──────┐
                ▼            ▼
             MyBatis      External Client
                │            │
                │            └─ timeout <= remaining
                ▼
              JDBC
                │
        Statement Timeout
        <= TX Remaining
                │
                ▼
               DB
                      │
                      ▼
              Deadline Recheck
                      │
                ┌─────┴─────┐
                ▼           ▼
              COMMIT      ROLLBACK
```

---

# 7. 신규/개선 컴포넌트 설계

## 7.1 `ExecutionDeadline`

### 역할

하나의 거래 전체 제한시간을 절대시각으로 관리한다.

### 권장 인터페이스

```java
public final class ExecutionDeadline {

    private final long startedAtNanos;
    private final long deadlineNanos;

    public static ExecutionDeadline start(long timeoutMs) {
        long now = System.nanoTime();
        return new ExecutionDeadline(
            now,
            now + TimeUnit.MILLISECONDS.toNanos(timeoutMs)
        );
    }

    public long remainingMillis() {
        long remaining = deadlineNanos - System.nanoTime();
        return Math.max(0L, TimeUnit.NANOSECONDS.toMillis(remaining));
    }

    public boolean expired() {
        return System.nanoTime() >= deadlineNanos;
    }

    public long elapsedMillis() {
        return TimeUnit.NANOSECONDS.toMillis(
            System.nanoTime() - startedAtNanos
        );
    }
}
```

### Architecture Rule

```text
각 계층은 timeout 시간을 새로 시작하지 않는다.
ExecutionDeadline.remainingMillis()만 사용한다.
```

---

## 7.2 `ServiceExecutionPolicy`

ServiceId의 실행정책을 하나의 객체로 통합한다.

```java
public record ServiceExecutionPolicy(
    String serviceId,
    long timeoutMs,
    long minStartBudgetMs,
    TransactionMode transactionMode,
    String transactionManager,
    boolean readOnly,
    Integer isolationLevel,
    int sqlSafetyTimeoutSeconds
) {}
```

### Transaction Mode

```java
public enum TransactionMode {
    NONE,
    RDW_READ_ONLY,
    RDW_READ_WRITE,
    ADW_READ_ONLY
}
```

추후 필요 시 확장한다.

```text
ADW_READ_WRITE
REQUIRES_NEW
JTA
NO_DB
```

---

## 7.3 `ServiceExecutionPolicyResolver`

### 역할

```text
ServiceId
   ↓
Execution Policy
```

를 결정한다.

1차 구현은 YAML 기반으로 가능하다.

```yaml
nhnis:
  fw:
    execution:
      defaults:
        timeout-ms: 5000
        min-start-budget-ms: 1000
        transaction-mode: RDW_READ_WRITE

      services:
        mgcoa5530S0:
          timeout-ms: 10000
          transaction-mode: RDW_READ_ONLY
          read-only: true

        mgcoa8888S0:
          timeout-ms: 5000
          transaction-mode: RDW_READ_ONLY
          read-only: true

        mgcoa8888D0:
          timeout-ms: 5000
          transaction-mode: RDW_READ_WRITE
          read-only: false
```

향후 OM Service Catalog로 승격한다.

```text
YAML
 ↓
OM DB
 ↓
Service Catalog
 ↓
Dynamic Policy Cache
```

---

# 8. Transaction Boundary 개선

## 8.1 현재 문제

현재 `DefaultOnlineTimeoutExecutor`가 다음 두 책임을 동시에 가진다.

```text
Timeout Execution
+
Transaction Boundary
```

이를 분리한다.

## 8.2 목표

```text
OnlineExecutionCoordinator
        │
        ▼
TransactionBoundaryExecutor
```

### 인터페이스

```java
public interface TransactionBoundaryExecutor {

    <T> T execute(
        ServiceExecutionPolicy policy,
        ExecutionDeadline deadline,
        Callable<T> action
    ) throws Exception;
}
```

## 8.3 권장 구현

```java
public class DefaultTransactionBoundaryExecutor
        implements TransactionBoundaryExecutor {

    private final Map<String, PlatformTransactionManager> txManagers;

    @Override
    public <T> T execute(
            ServiceExecutionPolicy policy,
            ExecutionDeadline deadline,
            Callable<T> action) throws Exception {

        if (policy.transactionMode() == TransactionMode.NONE) {
            return action.call();
        }

        long remainingMs = deadline.remainingMillis();

        if (remainingMs < policy.minStartBudgetMs()) {
            throw new OnlineTimeoutException(...);
        }

        PlatformTransactionManager txManager =
            txManagers.get(policy.transactionManager());

        TransactionTemplate template =
            new TransactionTemplate(txManager);

        template.setPropagationBehavior(
            TransactionDefinition.PROPAGATION_REQUIRED
        );

        template.setReadOnly(policy.readOnly());

        int timeoutSeconds = toConservativeSeconds(remainingMs);
        template.setTimeout(timeoutSeconds);

        return template.execute(status -> {
            try {
                T result = action.call();

                if (deadline.expired()
                        || Thread.currentThread().isInterrupted()) {
                    status.setRollbackOnly();
                    throw new OnlineTimeoutException(...);
                }

                return result;
            } catch (Exception e) {
                status.setRollbackOnly();
                throw wrap(e);
            }
        });
    }

    private int toConservativeSeconds(long remainingMs) {
        if (remainingMs < 1000L) {
            throw new IllegalStateException(
                "remaining budget is too small to start transaction"
            );
        }
        return Math.max(1, (int) (remainingMs / 1000L));
    }
}
```

### 중요

`TransactionTemplate`은 현재처럼 Singleton 필드 하나를 공유하면서 ServiceId별 timeout/readOnly 값을 변경하지 않는다.

거래마다 정책이 다르므로 **실행 시점에 TransactionTemplate을 생성하거나 정책별 불변 Template을 Cache**하는 방식을 사용한다.

---

# 9. Online Timeout Executor 개선

## 9.1 역할 재정의

기존:

```text
DefaultOnlineTimeoutExecutor
 ├─ Thread Pool
 ├─ Future Timeout
 └─ TransactionTemplate
```

개선:

```text
OnlineExecutionCoordinator
 ├─ Deadline
 ├─ Thread/Queue
 ├─ Cancellation Request
 └─ TransactionBoundaryExecutor 호출
```

## 9.2 목표 흐름

```text
execute(serviceId, action)
        │
        ├─ Policy Resolve
        ├─ Deadline Start
        ├─ Evidence Start
        │
        ▼
Worker Submit
        │
        ▼
Worker Start
        │
        ├─ remaining budget check
        │
        ▼
TransactionBoundaryExecutor
        │
        ▼
Dispatcher
```

## 9.3 Worker 시작 전 체크

반드시 다음 체크를 넣는다.

```java
long remainingMs = deadline.remainingMillis();

if (remainingMs < policy.minStartBudgetMs()) {
    evidence.markDeadlineExceededBeforeExecution();
    throw new OnlineTimeoutException(...);
}
```

---

# 10. MyBatis / JDBC Timeout 연계

## 10.1 원칙

```text
전체 Deadline
   ↓
Transaction Remaining
   ↓
MyBatis Transaction Timeout
   ↓
JDBC Statement.queryTimeout
```

SQL이 전체 Service Deadline보다 오래 실행될 수 있게 두지 않는다.

## 10.2 현재 기술구조의 활용

현재 PDMG는 다음을 사용한다.

```text
Spring Boot 3.5.x
Java 21
DataSourceTransactionManager
MyBatis Spring Boot Starter 3.0.4
HikariCP
```

MyBatis의 Statement 처리에서는 Mapper/기본 Query Timeout과 Transaction Timeout을 함께 고려할 수 있는 구조가 있으므로, 먼저 **Spring Transaction Timeout이 MyBatis Statement까지 실제 전달되는지 통합시험으로 검증**한다.

## 10.3 기본 Safety Timeout

Transaction Timeout 전파와 별도로 비정상적으로 긴 SQL에 대한 Safety Ceiling을 둘 수 있다.

예:

```java
mybatisConfig.setDefaultStatementTimeout(10);
```

단 이 값은 최종 표준값이 아니라 예시다.

실제 적용에서는:

```text
min(
  SQL Safety Ceiling,
  Transaction Remaining Timeout,
  Mapper 개별 Timeout
)
```

의 개념을 유지한다.

## 10.4 특정 SQL 예외

장시간 실행이 정당한 SQL은 명시적으로 관리한다.

```xml
<select id="selectLargeData"
        timeout="8"
        resultType="...">
```

단 개별 Mapper Timeout이 Service 전체 Deadline보다 길어서는 안 된다.

---

# 11. Hikari Connection Pool Timeout 개선

Connection 획득 대기시간은 SQL Timeout과 다른 예산이다.

```text
Service Deadline 5s

 ├─ Queue Wait
 ├─ Connection Acquire
 ├─ SQL
 ├─ Business Logic
 └─ Response Processing
```

Connection Pool 대기가 전체 Timeout의 대부분을 소비하지 않도록 별도 상한을 둔다.

예시 구성:

```yaml
spring:
  datasource:
    rdw:
      connection-timeout: 1000
      validation-timeout: 1000
```

**주의:** 위 값은 설계 예시이며 실제 값은 부하시험과 Pool 크기 산정을 통해 확정한다.

Architecture Rule:

```text
Hikari connectionTimeout
< Service Timeout
```

그리고 Runtime Evidence에서 다음을 구분한다.

```text
POOL_WAIT_TIMEOUT
SQL_TIMEOUT
TX_TIMEOUT
SERVICE_DEADLINE_TIMEOUT
```

---

# 12. 외부 연계 Timeout 전파

TCF 내부 거래가 외부 API를 호출하는 경우에도 동일한 Remaining Budget을 사용해야 한다.

## 12.1 잘못된 예

```text
Service Timeout = 5s

내부 처리 4s 사용
        ↓
외부 API timeout 새로 30s 시작
```

결과:

```text
Client는 5초에 TIMEOUT
Worker는 최대 34초 이상 계속 실행 가능
```

## 12.2 권장

```text
Service Deadline = 5s

4s 사용
 ↓
Remaining = 1s
 ↓
External Read Timeout <= 1s
```

## 12.3 Reserve 적용

응답/정리/Rollback 시간을 위해 일부 시간을 보존한다.

```text
remaining = 1200ms
cleanupReserve = 200ms

externalBudget = 1000ms
```

---

# 13. Timeout 상태 모델

## 13.1 현재 단순 상태의 문제

현재 `TIMEOUT` 하나로 표시하면 다음을 알 수 없다.

```text
응답만 Timeout인가?
Worker도 종료되었는가?
Rollback은 완료됐는가?
SQL은 계속 실행 중인가?
```

## 13.2 목표 상태

```text
SUBMITTED
   ↓
QUEUED
   ↓
WORKER_STARTED
   ↓
TX_STARTED
   ↓
RUNNING
   │
   ├──────────── SUCCESS
   │                 ↓
   │              COMMITTED
   │                 ↓
   │             TERMINATED
   │
   └─ DEADLINE_EXCEEDED
          ↓
      CANCEL_REQUESTED
          ↓
      ROLLBACK_PENDING
          ↓
        ROLLED_BACK
          ↓
       TERMINATED
```

Client 상태는 별도로 관리한다.

```text
CLIENT_WAITING
     ↓
SUCCESS_RESPONSE_SENT

또는

CLIENT_WAITING
     ↓
TIMEOUT_RESPONSE_SENT
```

---

# 14. Runtime Evidence 설계

각 거래는 최소 다음 정보를 남긴다.

```yaml
evidenceId: EVT-...
serviceId: mgcoa8888S0
guid: ...
traceId: ...

configuredTimeoutMs: 5000
queueElapsedMs: 120
workerStartedAt: ...
remainingAtWorkerStartMs: 4870

txMode: RDW_READ_ONLY
txTimeoutSeconds: 4
txStartedAt: ...

sqlId: mgcoa8888S0_S0
sqlTimeoutSeconds: 4

clientResult: TIMEOUT
workerResult: ROLLED_BACK

cancelRequested: true
cancelRequestedAt: ...
rollbackStartedAt: ...
rollbackCompletedAt: ...
workerTerminatedAt: ...

totalWorkerElapsedMs: ...
```

## 14.1 운영 핵심 지표

| Metric                         | 의미                                       |
| ------------------------------ | ------------------------------------------ |
| `timeout.request.count`        | 사용자 응답 Timeout 수                     |
| `timeout.worker.overrun.count` | Client Timeout 이후에도 Worker가 실행된 수 |
| `timeout.rollback.pending`     | Rollback 미완료 거래                       |
| `timeout.rollback.elapsed`     | Timeout→Rollback 완료시간                  |
| `timeout.queue.elapsed`        | Worker Queue 대기시간                      |
| `timeout.tx.elapsed`           | Transaction 수행시간                       |
| `timeout.sql.count`            | JDBC/SQL Timeout 수                        |
| `timeout.pool.wait.count`      | Connection Pool 대기 Timeout               |
| `timeout.cancel.success`       | Cancel 요청 수신 여부                      |
| `timeout.worker.terminated`    | 최종 Worker 종료수                         |

---

# 15. `TcfFacade` 개선

## 15.1 현재

```java
activeTransactionRegistry.begin(context);
try {
    stf.preProcess(context);
    return onlineTimeoutExecutor.execute(
        () -> dispatcher.dispatch(...)
    );
} finally {
    activeTransactionRegistry.end(context);
    etf.postProcess(context);
}
```

Timeout 이후 Worker가 아직 살아 있는 경우 `end()`가 너무 일찍 호출될 수 있다.

## 15.2 개선 원칙

Registry를 두 종류로 분리한다.

```text
Client Transaction State
+
Worker Execution State
```

또는 하나의 Registry에서 상태를 세분화한다.

```java
registry.clientTimeout(context);
registry.cancelRequested(context);
registry.rollbackPending(context);
registry.rollbackCompleted(context);
registry.workerTerminated(context);
```

## 15.3 ETF 처리 기준

ETF도 다음을 구분한다.

```text
CLIENT ETF
- 응답 전문 마감
- 사용자 TIMEOUT 응답

WORKER FINALIZER
- 실제 TX 종료
- Rollback 완료
- Runtime Evidence 종료
```

둘을 동일한 이벤트로 처리하지 않는다.

---

# 16. `@Transactional` 정리 기준

현재 일부 업무는 Facade와 Service에 모두 `@Transactional`이 존재한다.

목표 구조에서는 Transaction Owner를 명확히 한다.

## 권장안 A — TCF 외곽 Transaction Owner

PDMG TCF를 사용하는 온라인 거래의 기본 권장안이다.

```text
TCF
 ↓
TransactionBoundaryExecutor   ← Physical TX Owner
 ↓
Dispatcher
 ↓
Handler
 ↓
Facade
 ↓
Service
```

업무 Facade/Service의 중복 `@Transactional(REQUIRED)`은 제거하거나 **독립 실행 호환성을 위해 남길지 ADR로 결정**한다.

### 장점

- Timeout과 TX Deadline 일치
- Transaction 정책을 ServiceId 기준 통제 가능
- Late Commit 방지 일관성
- Runtime Evidence 통합

### 주의

TCF OFF 직접 Controller 경로를 유지할 경우에도 동일 TransactionBoundaryExecutor를 사용해야 한다.

---

# 17. Timeout ON/OFF 구조 통일

## AS-IS

```text
Timeout ON
Worker + Outer TX

Timeout OFF
Current Thread + Facade TX
```

## TO-BE

```text
                       TCF
                        │
                        ▼
              ExecutionCoordinator
                 │             │
          timeout enabled   timeout disabled
                 │             │
                 ▼             ▼
              Worker       Current Thread
                 │             │
                 └──────┬──────┘
                        ▼
            TransactionBoundaryExecutor
                        │
                        ▼
                    Dispatcher
```

결과:

```text
Timeout ON/OFF
= Scheduling/Deadline 전략 차이

Transaction Boundary
= 동일
```

---

# 18. 권장 Configuration 모델

```yaml
nhnis:
  fw:
    execution:
      enabled: true

      defaults:
        timeout-ms: 5000
        min-start-budget-ms: 1000
        cleanup-reserve-ms: 200
        transaction-mode: RDW_READ_WRITE
        transaction-manager: rdwTransactionManager
        sql-safety-timeout-seconds: 10

      pool:
        size: 20
        queue-capacity: 100

      services:
        mgcoa5530S0:
          timeout-ms: 10000
          transaction-mode: RDW_READ_ONLY
          read-only: true

        mgcoa8888S0:
          timeout-ms: 5000
          transaction-mode: RDW_READ_ONLY
          read-only: true

        mgcoa8888D0:
          timeout-ms: 5000
          transaction-mode: RDW_READ_WRITE
          read-only: false
```

기존 `nhnis.fw.timeout` 설정은 단계적으로 Deprecated 처리한다.

---

# 19. 예외 코드 체계

## 19.1 AS-IS (현재 구현)

| 코드 | HTTP | 의미 |
| --- | --- | --- |
| `FW_TIMEOUT` | 504 | `OnlineTimeoutException` — Service deadline / ETF interval 초과 |
| `FW_OVERLOADED` | 503 | Worker Pool 또는 Queue 포화 |

## 19.2 TO-BE (Runtime Evidence 연계)

Timeout을 하나의 코드로 처리하지 않는다.

| 코드 예           | 의미                          |
| ----------------- | ----------------------------- |
| `FW-TIMEOUT-001`  | Service Deadline 초과         |
| `FW-TIMEOUT-002`  | Worker 시작 전 Budget 부족    |
| `FW-TIMEOUT-003`  | Transaction Timeout           |
| `FW-TIMEOUT-004`  | SQL Statement Timeout         |
| `FW-TIMEOUT-005`  | External Call Timeout         |
| `FW-TIMEOUT-006`  | Connection Pool Wait Timeout  |
| `FW-TIMEOUT-007`  | Cancel 요청 후 Worker Overrun |
| `FW-OVERLOAD-001` | Worker Pool Reject            |

클라이언트에는 보안상 단순화된 표준 코드로 반환할 수 있지만 Runtime Evidence에서는 원인을 구분한다.

---

# 20. 구현 대상 파일

## 20.1 신규 파일 권장

```text
pdmg-fw/src/main/java/nhnis/fw/tcf/execution/
 ├─ ExecutionDeadline.java
 ├─ ServiceExecutionPolicy.java
 ├─ ServiceExecutionPolicyResolver.java
 ├─ TransactionMode.java
 ├─ OnlineExecutionCoordinator.java
 ├─ TransactionBoundaryExecutor.java
 ├─ DefaultTransactionBoundaryExecutor.java
 └─ ExecutionEvidence.java
```

## 20.2 기존 파일 변경

```text
pdmg-fw/
 ├─ tcf/timeout/DefaultOnlineTimeoutExecutor.java
 ├─ tcf/timeout/OnlineTimeoutConfiguration.java
 ├─ tcf/timeout/OnlineTimeoutProperties.java
 ├─ tcf/timeout/OnlineTimeoutWorkerContext.java
 └─ tcf/core/facade/TcfFacade.java

pdmg-service/
 ├─ config/RdwDataSourceConfig.java
 ├─ application/facade/*Facade.java
 ├─ application/service/*Service.java
 └─ resources/application.yml
```

---

# 21. 단계별 구현 전략

## Phase 1 — Transaction Timeout 실제 적용

### 목표

현재 Worker 외곽 Transaction에 Remaining Timeout을 실제 적용한다.

### 작업

1. `ExecutionDeadline` 도입
2. Worker Start Remaining Budget Check
3. 실행별 `TransactionTemplate` 생성
4. `TransactionTemplate.setTimeout()` 적용
5. ReadOnly/TransactionMode 적용
6. Timeout 단위 테스트 추가

### 완료 기준

```text
Service Timeout 5초
        ↓
TX Timeout ≤ Remaining Budget
```

---

## Phase 2 — MyBatis/JDBC Timeout 검증 및 적용

### 작업

1. MyBatis Transaction Timeout 전달 여부 Integration Test
2. `Statement.getQueryTimeout()` 또는 Interceptor 기반 증적 확인
3. Oracle Driver 실제 Timeout 동작 검증
4. 기본 SQL Safety Timeout 정의
5. Mapper 개별 Timeout 예외정책 정의

### 완료 기준

```text
Service Deadline 초과 전에
JDBC Statement가 Timeout을 인지
```

---

## Phase 3 — Transaction Policy 분리

### 작업

1. `TransactionMode` 도입
2. `rdwTransactionManager` 고정 의존 제거
3. ServiceId별 ReadOnly/ReadWrite/None 관리
4. Facade/Service 중복 `@Transactional` 정리
5. TCF ON/OFF Boundary 통일

---

## Phase 4 — Runtime Evidence

### 작업

1. Client Timeout 상태 분리
2. Worker 종료 상태 분리
3. Rollback Pending/Complete 추적
4. SQL Timeout / Pool Timeout 구분
5. OM Dashboard 연계

---

## Phase 5 — 외부연계 Deadline Propagation

### 작업

1. APIGW Client Remaining Budget 적용
2. FOS Timeout 단위/적용범위 정비
3. Connect/Read/Pool Timeout 구분
4. 외부호출 Reserve 적용

---

# 22. 테스트 설계

## TEST-TX-001 정상 거래

```text
Timeout = 5s
업무 = 300ms

Expected
SUCCESS
COMMIT
Worker TERMINATED
```

---

## TEST-TX-002 SQL이 Transaction Timeout 초과

```text
Timeout = 3s
SQL = 10s
```

Expected:

```text
SQL/Transaction Timeout 발생
ROLLBACK
Client TIMEOUT 또는 표준 Timeout Error
Worker 종료
Runtime Evidence 생성
```

---

## TEST-TX-003 Queue 대기로 Budget 소진

```text
Timeout = 5s
Queue Wait = 4.5s
minStartBudget = 1s
```

Expected:

```text
Worker가 Transaction 시작하지 않음
DB Connection 획득하지 않음
FW-TIMEOUT-002
```

---

## TEST-TX-004 Late Return 방지

업무 코드가 interrupt를 무시하고 제한시간 후 정상값을 반환하도록 만든다.

Expected:

```text
Deadline 검사
→ rollbackOnly
→ COMMIT 금지
```

---

## TEST-TX-005 ReadOnly 정책

```text
ServiceId = 조회 거래
TransactionMode = RDW_READ_ONLY
```

Expected:

```text
Outer Transaction readOnly=true
Facade/Service와 정책 충돌 없음
```

---

## TEST-TX-006 ReadWrite 거래

```text
UPDATE
 ↓
Timeout 발생
```

Expected:

```text
ROLLBACK
DB 변경 0건
```

---

## TEST-TX-007 Timeout 응답 후 Worker Overrun

JDBC/Mock Driver가 interrupt를 즉시 처리하지 않는 상황을 모사한다.

Expected:

```text
Client     TIMEOUT_RESPONSE_SENT
Worker     CANCEL_REQUESTED
Registry   ROLLBACK_PENDING

이후
Worker     TERMINATED
Registry   ROLLED_BACK
```

---

## TEST-TX-008 Worker Pool Reject

```text
Pool = full
Queue = full
```

Expected:

```text
OnlineOverloadException
DB Transaction 시작 없음
```

---

## TEST-TX-009 TCF Timeout OFF

Expected:

```text
Current Thread 실행
하지만 TransactionBoundaryExecutor는 동일 적용
```

즉 Timeout OFF라고 해서 Transaction Owner가 Facade로 바뀌지 않아야 한다.

---

## TEST-TX-010 ServiceId Override

```text
Default = 5s
mgcoa5530S0 = 10s
```

Expected:

```text
Deadline = 10s
Transaction Timeout = Worker 시작 시점 Remaining Budget 기준
```

---

# 23. Architecture Test Rule

다음 규칙은 자동검증 대상으로 등록한다.

```text
TX-RULE-001
TCF 온라인 거래의 Physical Transaction Owner는
TransactionBoundaryExecutor이다.

TX-RULE-002
DefaultOnlineTimeoutExecutor가 고정 TransactionTemplate을 직접 소유하지 않는다.

TX-RULE-003
ServiceId Transaction Policy가 없으면 명시적인 Default Policy를 사용한다.

TX-RULE-004
Transaction Timeout은 Remaining Deadline을 초과하지 않는다.

TX-RULE-005
Remaining Budget이 최소 실행예산보다 작으면 TX를 시작하지 않는다.

TX-RULE-006
Timeout 응답과 Worker 종료상태를 별도 관리한다.

TX-RULE-007
Runtime Evidence 없이 Timeout 처리를 완료 상태로 판정하지 않는다.

TX-RULE-008
조회거래의 Outer Transaction은 readOnly=true이다.

TX-RULE-009
Timeout ON/OFF가 Physical Transaction Boundary를 변경하지 않는다.

TX-RULE-010
SQL/외부연계 Timeout은 Service Deadline보다 길 수 없다.
```

---

# 24. 운영 Dashboard 권장 항목

```text
[PDMG Timeout Dashboard]

전체 거래                  100,000
Timeout 응답                   120
DB/SQL Timeout                  43
Pool Wait Timeout               12
Worker Overrun                  10
Rollback Pending                 2
Rollback Failed                  0
Overload Reject                 15

Top ServiceId
────────────────────────────
mgcoa5530S0   41
mgcoa8888S0   24
...
```

ServiceId 선택 시:

```text
ServiceId
  ↓
Configured Timeout
  ↓
Queue Time
  ↓
Worker Start
  ↓
TX Timeout
  ↓
SQL Timeout
  ↓
Client Response
  ↓
Rollback Complete
  ↓
Worker Terminated
```

까지 보여준다.

---

# 25. 성능 및 용량 고려

Timeout 개선은 단지 예외처리 기능이 아니다.

다음 관계를 함께 관리한다.

```text
Worker Pool Size
       │
       ▼
동시 실행 거래
       │
       ▼
DB Connection Pool
       │
       ▼
DB Session
```

권장 원칙:

```text
Worker Active가 DB Pool보다 지나치게 크면
DB Connection 대기시간 증가
   ↓
Timeout 증가
   ↓
Cancel 증가
   ↓
Worker Overrun 증가
```

따라서 다음 지표를 함께 부하시험한다.

```text
Worker Active
Worker Queue
Hikari Active
Hikari Pending
DB Session
Transaction Duration
SQL p95/p99
Timeout Rate
Rollback Duration
```

---

# 26. 변경 시 호환성

## 26.1 기존 설정 호환

초기 전환기간에는 기존 설정을 유지한다.

```yaml
nhnis.fw.timeout.*
```

새 설정이 없으면 기존 값을 New Execution Policy의 기본값으로 변환한다.

```text
Legacy TimeoutProperties
        ↓ Adapter
ServiceExecutionPolicy
```

## 26.2 Deprecated 계획

```text
1차
기존 설정 지원 + 경고 없음

2차
기존 설정 사용 시 Deprecated Warning

3차
OM/Execution Policy 기준으로 전환

4차
Legacy timeout 설정 제거
```

---

# 27. 금지 패턴

## 금지 1 — Future Timeout만으로 트랜잭션 종료 완료 처리

```text
future.get timeout
→ cancel(true)
→ 거래 종료 기록
```

**금지.**

---

## 금지 2 — 하위 계층이 독립적으로 새로운 Timeout 시작

```text
TCF = 5s
HTTP Client = 30s
SQL = 30s
```

**금지.**

Remaining Budget을 사용해야 한다.

---

## 금지 3 — 모든 거래를 무조건 RDW ReadWrite Transaction으로 감싸기

```text
ServiceId 관계없이
rdwTransactionManager
```

**금지.**

---

## 금지 4 — Timeout ON/OFF에 따라 Transaction Owner 변경

**금지.**

---

## 금지 5 — Client Timeout을 Worker 종료로 기록

**금지.**

---

# 28. 최종 목표 처리 흐름

```text
HTTP Request
    │
    ▼
TcfFacade
    │
    ▼
STF
    │
    ▼
ServiceExecutionPolicyResolver
    │
    ├─ timeout
    ├─ tx mode
    ├─ readOnly
    └─ tx manager
    │
    ▼
ExecutionDeadline
    │
    ▼
OnlineExecutionCoordinator
    │
    ├─ Queue
    └─ Worker
    │
    ▼
Remaining Budget Check
    │
    ▼
TransactionBoundaryExecutor
    │
    ├─ NONE
    ├─ RDW_READ_ONLY
    └─ RDW_READ_WRITE
    │
    ▼
TransactionTemplate
    │ timeout = remaining
    │ readOnly = policy
    ▼
Dispatcher
    │
    ▼
Handler
    │
    ▼
Facade
    │
    ▼
Service
    │
    ├──────────────┐
    ▼              ▼
DAO/MyBatis    External Client
    │              │
    ▼              ▼
Statement       timeout <=
Timeout         remaining
    │
    ▼
DB
    │
    ▼
Deadline Check
    │
 ┌──┴───────────┐
 ▼              ▼
COMMIT        ROLLBACK
                 │
                 ▼
          Rollback Evidence
                 │
                 ▼
          Worker Terminated
                 │
                 ▼
             ETF/OM
```

---

# 29. Architecture Gate

## G-TX-10 — Source Gate

- [x] `DefaultOnlineTimeoutExecutor` AS-IS 재확인 (2026-08-22)
- [x] ServiceId Timeout 설정 확인 (`OnlineTimeoutProperties.resolveMilliseconds`)
- [x] RDW TransactionManager 확인 (`OnlineTimeoutConfiguration` `@Qualifier`)
- [x] Timeout vs TxControl 분리 확인 (`stf` / `MgTxControlService`)
- [x] 단위 테스트 존재 확인 (`DefaultOnlineTimeoutExecutorTest`)
- [ ] MyBatis Statement timeout 전달 Integration Test
- [ ] Oracle Driver 운영버전 Timeout 동작 검증

## G-TX-20 — Design Gate

- [ ] Deadline 모델 승인
- [ ] Transaction Policy 승인
- [ ] Transaction Owner 승인
- [ ] TCF ON/OFF Boundary 승인
- [ ] Runtime 상태모델 승인

## G-TX-30 — Code Gate

- [ ] Worker Start Budget 검사 구현
- [ ] Dynamic Transaction Timeout 구현
- [ ] readOnly 정책 구현
- [ ] TransactionManager Resolver 구현
- [ ] Client/Worker 상태 분리 구현

## G-TX-40 — Test Gate

- [ ] Unit Test PASS
- [ ] Transaction Rollback PASS
- [ ] SQL Timeout PASS
- [ ] Queue Deadline PASS
- [ ] Late Return Rollback PASS
- [ ] Overload PASS

## G-TX-50 — Runtime Gate

- [ ] Oracle 환경 Timeout Evidence 확보
- [ ] Timeout 후 SQL 종료 확인
- [ ] Rollback 완료시간 측정
- [ ] Worker Overrun 측정
- [ ] Hikari Pending 측정

## HG-TX-90 — Final Gate

다음 중 하나라도 충족하지 못하면 최종 PASS 금지.

```text
Transaction Timeout 미적용
SQL Timeout 검증 없음
Rollback Evidence 없음
Worker 종료 증적 없음
Critical Timeout Drift 존재
```

---

# 30. 구현 우선순위

| 우선순위 | 항목                                         | 효과                 |
| -------: | -------------------------------------------- | -------------------- |
| **P0-1** | `ExecutionDeadline` 도입                     | 전체 Deadline 단일화 |
| **P0-2** | Worker 시작 전 Remaining Budget Check        | 불필요한 TX/SQL 차단 |
| **P0-3** | TransactionTemplate에 Remaining Timeout 적용 | 실제 TX 제한         |
| **P0-4** | Transaction Mode/Manager ServiceId 정책화    | RDW 고정 제거        |
| **P0-5** | MyBatis/JDBC Timeout Integration Test        | DB 실효성 검증       |
| **P0-6** | Client Timeout / Worker 종료 상태 분리       | 운영 정합성          |
| **P1-1** | Facade/Service `@Transactional` 정리         | TX Owner 명확화      |
| **P1-2** | TCF ON/OFF Boundary 통일                     | 설정 Drift 제거      |
| **P1-3** | Hikari Pool Wait Timeout 관리                | Budget 소모 방지     |
| **P1-4** | 외부연계 Deadline 전파                       | E2E Deadline 완성    |
| **P1-5** | OM Runtime Evidence 연계                     | 운영 검증 가능       |

---

# 31. 최종 판정

현재 PDMG의 Worker + `TransactionTemplate` + `Future.get(timeout)` 구조는 **Timeout 응답과 Late Commit 방지의 기반을 이미 확보했다**는 점에서 방향은 적절하다.

그러나 현재는 다음 수준이다.

```text
Response Timeout                  PASS
ServiceId별 Timeout              PASS
Worker Pool                      PASS
Queue Capacity                   PASS
Cancel Request                   PASS
Late Commit 방어                 PASS

Actual Transaction Timeout       GAP
Dynamic SQL Timeout              GAP
Remaining Budget Propagation     GAP
ServiceId TX Policy              GAP
ReadOnly Outer TX                GAP
Worker Terminal Evidence         GAP
Timeout ON/OFF TX Consistency    GAP
```

따라서 목표는 단순히 `transactionTemplate.setTimeout(5)` 한 줄을 추가하는 것이 아니다.

PDMG의 최종 개선 방향은 다음이다.

```text
ServiceId
   ↓
Execution Policy
   ↓
Absolute Deadline
   ↓
Queue / Worker
   ↓
Remaining Budget
   ↓
Transaction Timeout
   ↓
MyBatis / JDBC Timeout
   ↓
External Timeout
   ↓
Rollback Completion
   ↓
Runtime Evidence
```

이 구조가 완성되어야 PDMG의 Timeout은 단순한 **응답 제한 기능**이 아니라 실제 **Enterprise Transaction Deadline Control Framework**로 볼 수 있다.

---

# 32. 공식 기술 참고자료

본 설계의 TO-BE 기술 방향은 다음 공식 문서를 참고해 검증한다.

- Spring Framework — Programmatic Transaction Management  
  https://docs.spring.io/spring-framework/reference/data-access/transaction/programmatic.html

- Spring Framework — Transaction Propagation  
  https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/tx-propagation.html

- Spring Framework — `DataSourceTransactionManager`  
  https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/datasource/DataSourceTransactionManager.html

- MyBatis — `BaseStatementHandler` Transaction/Query Timeout 처리  
  https://mybatis.org/mybatis-3/xref/org/apache/ibatis/executor/statement/BaseStatementHandler.html

- MyBatis-Spring — `SpringManagedTransaction`  
  https://mybatis.org/spring/apidocs/org/mybatis/spring/transaction/SpringManagedTransaction.html

- Java 21 — `FutureTask.cancel(boolean)`  
  https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/FutureTask.html

---

# 33. 변경관리 기록

| Version | Date       | Status   | 내용                                                  |
| ------- | ---------- | -------- | ----------------------------------------------------- |
| 0.1     | 2026-08-17 | PROPOSED | PDMG 실제 Timeout/Transaction Source 기준 최초 개선안 |
