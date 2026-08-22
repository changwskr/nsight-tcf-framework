# CHG-TMO-001 — Timeout Safety Harness / Policy Guard Specification

> 상태: **HARNESS SPEC READY / RUNTIME NOT EXECUTED**  
> P0 연계: `P0-TMO-003`, `RUN-TIMEOUT`

## 1. 목적

현재 `OnlineTransactionTimeoutExecutor`는 Timeout 시:

```java
future.cancel(true)
```

를 호출한다. 이것은 Worker Thread에 Interrupt를 요청할 뿐, 다음을 자동 보장하지 않는다.

- DB Transaction Rollback
- Late Commit 방지
- Hikari Connection 반환
- Thread Pool 정상 복귀
- Context/MDC 누수 방지

따라서 이를 실제 실행으로 증명하는 Integration Harness가 필요하다.

## 2. 현재 Policy Gap

현재 기본값은:

```text
DB Query Timeout = 3s
TX Timeout       = 5s
Online Timeout   = 5s
```

일부 `TimeoutPolicySeedData`의 조회성 정책도 `online=5`, `tx=5`가 될 수 있다.

안전 원칙은:

```text
DB Query Timeout < TX Timeout < Online Timeout < Client Timeout
```

이며 `TX == Online`은 Timeout Race Window를 만들 수 있으므로 **Target Validation에서 금지 후보**로 둔다.

실제 숫자 변경은 Runtime Test와 ADR 이후 승인한다. 이번 Change에서는 먼저 잘못된 순서를 탐지할 Guard와 Harness를 정의한다.

## 3. 권장 테스트 위치

`tcf-core`는 DB Integration Dependency가 제한적이다. `tcf-web`은 Spring JDBC/MyBatis를 포함하고 Test Runtime에 H2가 존재하므로 1차 Harness 위치로 적합하다.

권장 경로:

```text
tcf-web/src/test/java/com/nh/nsight/tcf/web/timeout/
├─ TimeoutTransactionSafetyIntegrationTest.java
├─ TimeoutTestService.java
└─ TimeoutTestRepository.java
```

실제 운영과 더 가까운 E2E 검증은 별도 `tcf-harness`/통합환경에서 RUN-TIMEOUT으로 수행한다.

## 4. Test Database

H2 Test Table:

```sql
CREATE TABLE TCF_TIMEOUT_TEST (
    ID VARCHAR(50) PRIMARY KEY,
    VALUE VARCHAR(100),
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

각 Test는 Before/After Row Count를 증적으로 남긴다.

## 5. 핵심 시나리오

### T1 — Normal Commit

```text
TX begin
→ INSERT row A
→ return before deadline
→ COMMIT
```

합격: row A = 1.

### T2 — Timeout Before Commit

```text
Online Worker
→ @Transactional TestService
→ INSERT row B
→ wait > online timeout
→ Caller receives TIMEOUT
```

합격:

- 최종 row B = 0
- timeout response 발생
- connection active count 원복

### T3 — Late Commit Hazard

Worker가 Interrupt를 받은 뒤 즉시 종료하지 않는 나쁜 업무코드를 의도적으로 시뮬레이션한다.

```text
INSERT B
→ sleep
→ InterruptedException catch
→ interrupt flag 무시/처리 후 추가 UPDATE/return 시도
```

Harness 목적은 "interrupt가 있으니 안전하다"고 가정하지 않고 **TX Timeout이 실제 Commit을 차단하는지** 확인하는 것이다.

합격:

- Client Timeout 후 `2 × onlineTimeout`까지 row 변경 0
- late commit 0

### T4 — Slow SQL

DB Query Timeout이 TX/Online보다 먼저 동작하는지 확인한다.

합격:

```text
Query timeout event
< Transaction timeout event
< Online timeout event
```

실제 H2에서 Oracle과 동일한 Slow SQL 동작을 재현하기 어려우면 이 Test는 Oracle/RDW 통합환경으로 승격한다.

### T5 — Pool Return

Timeout 직전/직후/안정화 후 Hikari:

```text
active
idle
pending
```

을 기록한다.

합격: 안정화 후 Connection Leak 0.

### T6 — Context Leak

같은 Worker Thread가 다음 요청을 처리하도록 유도한다.

Request 1:

```text
GUID=G1, ServiceId=S1
```

Request 2:

```text
GUID=G2, ServiceId=S2
```

합격: Request 2에서 G1/S1/MDC/RequestAttributes 잔존 0.

## 6. Policy Validator

신규 Validator 후보:

```text
TimeoutPolicyValidator
```

최소 검증:

```text
dbQueryTimeoutSec > 0
txTimeoutSec > 0
onlineTimeoutSec > 0

dbQueryTimeoutSec < txTimeoutSec
txTimeoutSec < onlineTimeoutSec
```

외부연계는 단순 고정 Read Timeout보다 Remaining Deadline 기반이 목표이므로 별도 G50/G60 Change로 관리한다.

### 현재 Seed와의 관계

현재 일부 Seed는 `online=5`, `tx=5`가 가능하므로 Validator를 바로 Production Fail-Fast로 적용하면 기존 데이터가 기동을 막을 수 있다.

도입 순서:

```text
1. Audit Mode: invalid policy 목록 출력
2. OM 정책 데이터 정비
3. Test
4. Enforce Mode 전환
```

## 7. Evidence 출력

`RUN-TIMEOUT/<RUN_ID>/`:

```text
run-manifest.json
config-snapshot/
transaction-log/
db-before.json
db-after.json
hikari-metrics.json
thread-before.txt
thread-after.txt
context-leak-result.json
result.md
```

## 8. 합격조건

```text
Timeout Response             PASS
TX Rollback                  PASS
Late Commit                  0
Connection Leak              0
Thread Pool Leak             0
Context/MDC Leak             0
Invalid Timeout Policy       0 (Enforce 전환 시)
```

## 9. 자동 Test vs 운영 Run 구분

| 검증 | CI Test | Production-like Run |
|---|---|---|
| Future timeout exception | O | O |
| Context capture/clear | O | O |
| H2 rollback | O | 보조 |
| Oracle/RDW query timeout | △ | **필수** |
| Real Hikari pool pressure | △ | **필수** |
| Late commit under actual JDBC | △ | **필수** |

CI Test 성공만으로 `P0-TMO-003`을 CLOSED 처리하지 않는다.

## 10. Acceptance Criteria

- [ ] Timeout Integration Harness 구현
- [ ] Policy Audit Validator 구현
- [ ] invalid policy 목록 0 또는 승인예외
- [ ] CI Timeout tests PASS
- [ ] `RUN-TIMEOUT` Production-like PASS
- [ ] `RUN-SLOWSQL` PASS
- [ ] G40/G60/G80 재평가
