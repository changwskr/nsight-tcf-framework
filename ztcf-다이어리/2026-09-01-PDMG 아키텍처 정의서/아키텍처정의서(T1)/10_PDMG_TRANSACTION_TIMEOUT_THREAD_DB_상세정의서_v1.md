# PDMG 전체 아키텍처 정의서
# 10. PDMG TRANSACTION / TIMEOUT / THREAD / DB ARCHITECTURE
## Worker / TransactionTemplate / Deadline / Hikari / JDBC / DB Session
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-10-TRANSACTION-TIMEOUT-THREAD-DB`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-10-01. 이 장의 핵심 질문

```text
Request Thread와 Worker Thread는 어떻게 분리되는가?
실제 Physical Transaction은 어디에서 시작/종료되는가?
Timeout/Overload/JDBC Cancel/Pool Capacity를 어떻게 구분하고 검증하는가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-10-02. Evidence Flow

```text
Source / Config
   ↓
Runtime / Deployment
   ↓
PDMG Current Analysis
   ↓
Architecture Decision
   ↓
NSIGHT Target / Working Baseline
   ↓
PASS / GAP / OPEN
```

| Evidence ID | 근거 | 사용목적 | 상태 |
|---|---|---|---|
| EV-10-01 | V Transaction/Timeout | core source snapshot | [AS-IS] |
| EV-10-02 | IV Runtime | request/worker sequence | [AS-IS] |
| EV-10-03 | VIII Capacity | Tomcat/Hikari/JVM candidates | [CANDIDATE] |
| EV-10-04 | VI Context | worker propagation | [AS-IS + RISK] |
| EV-10-05 | Decision Register | timeout/capacity | [DECISION] |

---

# 2. Figure Plan

## FIG-10-03. Top-down Drill-down

```text
L0  Overall
 ↓
L1  Boundary / Responsibility
 ↓
L2  Node / Platform / Component
 ↓
L3  Runtime / Control / Data
 ↓
L4  Sequence / Failure / Recovery
 ↓
L5  Source / Config / Evidence
```

---

# 3. L0 — Transaction/Timeout Master

## FIG-10-04. L0 — Transaction/Timeout Master

```text
Request Thread
 ↓ submit
Worker Pool
 ↓
TransactionTemplate BEGIN
 ↓
Handler / Facade / Service
 ↓
DAO / Hikari / JDBC
 ↓
DB
 ↓
Deadline
 ├─ commit
 └─ rollback
```

---

# 4. Current Snapshot

## FIG-10-05. Current Snapshot

```text
timeout.enabled = true
milliseconds = 5000
pool-size = 20
queue-capacity = 100

[AS-IS SNAPSHOT]
```

---

# 5. Request vs Worker

## FIG-10-06. Request vs Worker

```text
Request Thread
= HTTP/MVC/Future wait/response

Worker Thread
= Context/TX/Business/DB
```

---

# 6. Executor / Queue

## FIG-10-07. Executor / Queue

```text
20 active workers
   ↓
100 queue capacity
   ↓
reject
   ↓
503 overload
```

---

# 7. Overload vs Timeout

## FIG-10-08. Overload vs Timeout

```text
Overload
= cannot accept work
→ 503

Timeout
= accepted but did not finish in deadline
→ 504
```

---

# 8. Worker Context

## FIG-10-09. Worker Context

```text
Request Context
 ↓ capture
Worker install
 ↓ business
Worker clear

Current same mutable reference risk
```

---

# 9. Physical Transaction Boundary

## FIG-10-10. Physical Transaction Boundary

```text
Worker
 ↓
TransactionTemplate BEGIN
 ├─ Dispatcher
 ├─ Handler
 ├─ Facade REQUIRED joins
 ├─ Service
 ├─ DAO / SQL
 └─ Deadline
 ↓
COMMIT / ROLLBACK
```

---

# 10. Facade @Transactional

## FIG-10-11. Facade @Transactional

```text
Existing TX exists?
 ├─ YES → REQUIRED joins
 └─ NO  → new TX

TCF ON timeout ON
→ outer TransactionTemplate exists
```

---

# 11. Deadline before Commit

## FIG-10-12. Deadline before Commit

```text
DB work finished
 ↓
Deadline check
 ├─ within → commit
 └─ exceeded → rollbackOnly
```

---

# 12. cancel(true) Limitation

## FIG-10-13. cancel(true) Limitation

```text
Request timeout
 ↓
Future.cancel(true)
 ↓
Thread interrupt request

≠ JDBC cancel guarantee
≠ DB session kill
```

---

# 13. Hikari / DB Session Boundary

## FIG-10-14. Hikari / DB Session Boundary

```text
Worker Thread
 ↓ borrow
Hikari Connection
 ↓
JDBC
 ↓
DB Session
 ↓
SQL

Thread count ≠ Pool size ≠ DB session count
```

---

# 14. Query Timeout Hierarchy

## FIG-10-15. Query Timeout Hierarchy

```text
DB Query Timeout
 <
Worker/TX Deadline
 <
Server/Downstream
 <
Client
```

---

# 15. Capacity Chain

## FIG-10-16. Capacity Chain

```text
Tomcat Request Threads
 ↓
PDMG Worker 20
 ↓
Hikari Pool [target OPEN]
 ↓
DB Sessions
 ↓
CPU / IO / Locks
```

---

# 16. Failure / Rollback Cases

## FIG-10-17. Failure / Rollback Cases

```text
Business Exception
DB Exception
Deadline Exceeded
Interrupt
 ↓
Rollback?
 ↓
Evidence / Error Mapping
```

---

# 17. Metrics

## FIG-10-18. Metrics

```text
Tomcat busy
Worker active / queue
Hikari active / pending
DB sessions / waits
SQL latency
Timeout / reject
 ↓
correlate by ServiceId/GUID
```

---

# 18. Architecture Rule Catalog

## FIG-10-19. Rule Set

```text
R-TX-01
Request Thread와 DB Transaction Thread를 구분한다.

R-TX-02
Worker TransactionTemplate이 Current outer TX boundary다.

R-TX-03
Facade REQUIRED는 기존 TX에 참여한다.

R-TX-04
HTTP 504를 DB cancel 완료로 해석하지 않는다.

R-TX-05
Deadline check는 commit 이전에 수행한다.

R-TX-06
Query timeout < transaction deadline < outer/client timeout.

R-TX-07
Worker/Queue/Hikari/DB pool size를 동일하게 맞추지 않는다.

R-TX-08
Overload와 Timeout을 별도 metric/error로 운영한다.

R-TX-09
Worker context는 안전하게 복제/정리한다.

R-TX-10
금융 DML Blind Retry를 금지한다.
```

| Rule | 정의 |
|---|---|
| R-TX-01 | Request Thread와 DB Transaction Thread를 구분한다. |
| R-TX-02 | Worker TransactionTemplate이 Current outer TX boundary다. |
| R-TX-03 | Facade REQUIRED는 기존 TX에 참여한다. |
| R-TX-04 | HTTP 504를 DB cancel 완료로 해석하지 않는다. |
| R-TX-05 | Deadline check는 commit 이전에 수행한다. |
| R-TX-06 | Query timeout < transaction deadline < outer/client timeout. |
| R-TX-07 | Worker/Queue/Hikari/DB pool size를 동일하게 맞추지 않는다. |
| R-TX-08 | Overload와 Timeout을 별도 metric/error로 운영한다. |
| R-TX-09 | Worker context는 안전하게 복제/정리한다. |
| R-TX-10 | 금융 DML Blind Retry를 금지한다. |

---

# 19. Verification / Test

## FIG-10-20. Verification Flow

```text
Architecture Model
   ↓
Static / Config Check
   ↓
Integration Test
   ↓
Failure / Security / Performance Test
   ↓
Runtime Evidence
   ↓
PASS / GAP
```

| Test ID | 검증내용 |
|---|---|
| T-TX-01 | physical TX begin/commit trace |
| T-TX-02 | deadline exceeded rollback |
| T-TX-03 | JDBC query timeout |
| T-TX-04 | future cancel behavior |
| T-TX-05 | overload rejection |
| T-TX-06 | pool saturation |
| T-TX-07 | TX manager/datasource alignment |
| T-TX-08 | late worker evidence |

---

# 20. GAP Register

## FIG-10-21. GAP Lifecycle

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

| GAP ID | GAP | 중요도 | 전환조건 |
|---|---|---|---|
| GAP-TX-01 | DB/JDBC Query Timeout exact value 미확정 | Critical | driver/config test |
| GAP-TX-02 | cancel(true) DB cancel guarantee 없음 | High | statement cancel test |
| GAP-TX-03 | mutable worker context | High | immutable snapshot |
| GAP-TX-04 | Hikari target size 미확정 | High | load/DB test |
| GAP-TX-05 | Tomcat/worker/pool capacity evidence 미완료 | High | load/soak |
| GAP-TX-06 | late worker runtime evidence 미완료 | High | timeout fault test |

---

# 21. Risk Register

## FIG-10-22. Risk Propagation

```text
Cause
  ↓
Technical Failure
  ↓
Service Impact
  ↓
Operational / Business Impact
```

| Risk ID | Risk | 영향 |
|---|---|---|
| RISK-TX-01 | late commit after client timeout | duplicate/reconciliation risk |
| RISK-TX-02 | oversized thread/pool | DB saturation |
| RISK-TX-03 | worker queue buildup | latency cascade |
| RISK-TX-04 | interrupt ignored by JDBC | resource leak/long DB work |
| RISK-TX-05 | wrong TX manager/datasource | transaction inconsistency |

---

# 22. Architecture Decision / ADR

## FIG-10-23. Decision Flow

```text
Decision Question
   ↓
주안 / 대안
   ↓
장점 / 단점
   ↓
Evidence / Test
   ↓
ADR
   ↓
Baseline
```

| ADR/Task | 의사결정 주제 | 현재 방향 |
|---|---|---|
| ADR-TASK-017 | Timeout Budget | layered |
| ADR-TASK-018 | Retry/Idempotency | conditional |
| ADR-TASK-029 | Thread/Worker/Hikari | end-to-end budget |
| ADR-TASK-026 | WAS sizing | load test based |

---

# 23. Architecture PASS / PDMG Conformance

## FIG-10-24. PASS Model

```text
Architecture Definition
 ↓
PASS

Current TX/Timeout Conformance
 ↓
PARTIAL / CONDITIONAL

Known: worker20/queue100/5000ms
Open: query timeout / target pool / cancel
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Worker/TX boundary | PASS | strong source |
| 5000ms snapshot | PASS AS-IS | not target SLA |
| Query timeout | OPEN | exact config needed |
| Cancel semantics | CONDITIONAL | JDBC test needed |
| Hikari target | OPEN | load/DB test |
| Metrics | CONDITIONAL | end-to-end correlation |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PARTIAL / CONDITIONAL`  
**Runtime Evidence Coverage:** `HIGH-MEDIUM`

---

# 24. Next Chapter Handoff

## FIG-10-25. 10 → 11

```text
10 TX / TIMEOUT / THREAD / DB
"실행자원과 DB 경계는 무엇인가?"
     ↓
11 SECURITY / SSO / JWT / SESSION
"그 Runtime의 Identity, Token, Key, Session, Authorization은 어떻게 보호되는가?" 
```

---

# 25. PDMG TRANSACTION / TIMEOUT / THREAD / DB ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG TRANSACTION / TIMEOUT / THREAD / DB ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**10장 Architecture Definition 판정: `PASS`**
