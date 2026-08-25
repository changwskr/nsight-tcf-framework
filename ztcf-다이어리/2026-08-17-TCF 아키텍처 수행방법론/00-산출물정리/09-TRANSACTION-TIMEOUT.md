# 09. NSIGHT Transaction & Timeout Architecture — G40

> Gate: **G40 — Mechanism / Source Conformance**  
> 기준: `nsight-tcf-framework (2).zip`의 실제 Source Snapshot과 기존 NSIGHT Architecture Baseline을 대조한다.  
> 원칙: **PDMG AS-IS와 NSIGHT TCF TO-BE를 동일 구현으로 취급하지 않는다.**


## 1. 핵심 판정

PDMG AS-IS와 NSIGHT TCF TO-BE의 가장 중요한 차이는 **온라인 Timeout과 DB Transaction의 Owner를 분리했는가**이다.

- PDMG AS-IS: `DefaultOnlineTimeoutExecutor`가 Worker Thread를 만들면서 동시에 `TransactionTemplate`로 Dispatcher 이하 전체를 감싼다.
- NSIGHT TCF TO-BE: `OnlineTransactionTimeoutExecutor`는 Worker Deadline만 담당하고, 실제 DB Transaction은 Facade의 `@Transactional` 및 Policy-driven Transaction Timeout이 담당한다.

이 차이는 TO-BE의 핵심 개선방향으로 유지하는 것이 타당하다.

## 2. PDMG AS-IS

```text
Tomcat Request Thread
       ↓
TcfFacade
       ↓
Future submit
       ↓ Thread Switch
pdmg-online-* Worker
       ↓
TransactionTemplate(PROPAGATION_REQUIRED)
       ↓  ★ OUTER TX BEGIN
Dispatcher
 ↓
Handler
 ↓
Facade @Transactional(rdw)
 ↓
Service @Transactional(rdw)
 ↓
DAO / Mapper / DB
       ↓
Action Return
       ↓
Deadline/Interrupt Re-check
       ↓
Commit or Rollback
```

### 직접 확인된 설정

`pdmg-service/application.yml`:

- `nhnis.fw.tcf.enabled=true`
- `nhnis.fw.timeout.enabled=true`
- default timeout `5000ms`
- `mgcoa5530S0=10000ms`
- Worker pool size `20`
- Queue `100`

근거: `application.yml:45-69`

### Transaction Owner

`OnlineTimeoutConfiguration`은 `rdwTransactionManager`를 `DefaultOnlineTimeoutExecutor`에 주입한다. Executor는 `TransactionTemplate(PROPAGATION_REQUIRED)`를 생성한다.

근거:

- `OnlineTimeoutConfiguration.java:20-22,56-63`
- `DefaultOnlineTimeoutExecutor.java:31-40`

### 중첩 Annotation

PDMG sample에서는 Facade와 Service 모두 같은 `rdwTransactionManager`의 `@Transactional`을 선언한다. `PROPAGATION_REQUIRED`이므로 보통 Worker의 외곽 TX에 Join하지만, Transaction Owner가 3곳에 표현되어 설계 책임이 중복된다.

## 3. PDMG Timeout Cancel 의미

Request Thread는 `future.get(timeout)`으로 대기하며 Timeout 시 `future.cancel(true)`를 요청한다.

Worker 내부는 업무 action이 반환된 뒤:

```text
Deadline exceeded OR Thread interrupted
        ↓
status.setRollbackOnly()
        ↓
OnlineTimeoutException
```

을 수행한다.

### [RISK] Late Commit / Connection Hold

`future.cancel(true)`는 **interrupt 요청**이지 JDBC/DB 작업의 강제 중단을 보장하지 않는다.

특히 다음 Race를 Runtime Test로 검증해야 한다.

```text
Caller Timeout
   ↓
future.cancel(true)
   ↓
Worker가 JDBC/Commit 구간에서 Interrupt를 즉시 반영하지 못함
   ↓
DB Connection 계속 점유 또는 Commit 진행 가능성
```

PDMG 구현은 action 반환 후 interrupt/deadline을 재검사해 Rollback을 유도하므로 일반 경로의 Late Commit 위험을 줄이고 있다. 그러나 **DB Driver/SQL/Commit 중단 특성은 Source만으로 보장할 수 없다.**

## 4. NSIGHT TCF TO-BE

```text
Tomcat Request Thread
       ↓
STF
  └─ TimeoutPolicy resolve
       ↓
OnlineTransactionTimeoutExecutor
       ↓ Worker Thread
Dispatcher
 ↓
Handler
 ↓
Facade @Transactional
       ↓ ★ TX BEGIN
Service
 ↓
Rule / DAO / Mapper / DB
       ↓
TX Commit/Rollback
       ↓
Future complete
```

`OnlineTransactionTimeoutExecutor`는 Future/Deadline을 담당하지만 TransactionTemplate을 소유하지 않는다.

근거: `tcf-core/.../OnlineTransactionTimeoutExecutor.java:30-60`

## 5. Policy-driven Timeout Chain

STF는 `TimeoutPolicyService.resolveAndApply`를 호출한다.

Policy에는 다음 값이 존재한다.

```text
onlineTimeoutSec
transactionTimeoutSec
dbQueryTimeoutSec
timeoutAction
```

근거: `TimeoutPolicyService.java:23-45`

Facade의 `@Transactional`은 `PolicyDrivenTransactionAttributeSource`에 의해 실행 시점의 `txTimeoutSec`으로 덮어쓸 수 있다.

```text
ServiceId
  ↓
TimeoutPolicyRepository
  ↓
TimeoutPolicyResolver
  ↓
TimeoutContextHolder
  ↓
Facade @Transactional
  ↓
PolicyDrivenTransactionAttributeSource
  ↓
TX timeout = policy.txTimeoutSec
```

프로그램적 트랜잭션은 `PolicyDrivenTransactionExecutor`가 동일 정책을 `TransactionTemplate.setTimeout()`에 적용한다.

## 6. 권장 Owner Model

| 책임 | Owner |
|---|---|
| HTTP Client Deadline | Client/API Gateway |
| Online End-to-End Deadline | TCF OnlineTimeoutExecutor |
| Transaction Timeout | Facade Transaction Boundary |
| SQL Query Timeout | MyBatis/JDBC Policy Interceptor |
| Retry/Compensation | Integration/Application Policy |
| Timeout Error Mapping | TCF |
| Timeout Metric/Log | TCF/OM |

## 7. Timeout 계층 Rule

기본 관계는 다음을 검증한다.

```text
DB Query Timeout
    <
Transaction Timeout
    <
Online TCF Timeout
    <
Upstream Client/Proxy Timeout
```

숫자는 ServiceId별 Policy에서 관리하고, 전역 하나의 숫자로 고정하지 않는다.

## 8. Context Propagation

### PDMG

Worker Context는 ServiceContext + MDC를 Snapshot하고 Servlet Request/Response는 보관하지 않는다.

### NSIGHT TCF

`TimeoutThreadContext`는 TimeoutPolicy + TransactionContext + MDC + RequestAttributes를 Worker로 전파한다.

**G40-C04로 Worker RequestAttributes 정책을 ADR 대상화한다.**

## 9. Conformance Gap

| ID | 내용 | Severity |
|---|---|---:|
| G40-TX-01 | PDMG Outer TransactionTemplate + Facade + Service TX 중복 | P0 |
| G40-TX-02 | NSIGHT Service 계층에도 `@Transactional` 4개 파일 존재 | P0 |
| G40-TX-03 | SQL Query Timeout 실제 Mapper/JDBC 전수 적용 미검증 | P0 |
| G40-TX-04 | Timeout 시 JDBC/Commit Interrupt 행동 Runtime 미검증 | P0 |
| G40-TX-05 | RequestAttributes Worker 전파 안전성 미검증 | P0 |
| G40-TX-06 | Upstream Apache/L4/Client Timeout과 Policy 관계 미매핑 | P1 |

## 10. Gate 판정

**CONDITIONAL PASS**

구조적 TO-BE는 PDMG보다 Transaction/Timeout 책임을 더 명확히 분리한다. 그러나 Runtime에서 Commit/Rollback/DB Connection 반환이 Deadline과 일치하는지 부하시험·장애주입 테스트가 필수다.
