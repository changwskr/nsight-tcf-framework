# PDMG 전체 아키텍처 정의서
# 09. PDMG ONLINE RUNTIME ARCHITECTURE
## Request Thread / Worker / ServiceId / Business / DB / Response / Failure
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-09-ONLINE-RUNTIME`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-09-01. 이 장의 핵심 질문

```text
온라인 거래 한 건이 실제 어떤 순서와 Thread/Transaction 경계로 실행되는가?
TCF ON/OFF의 Runtime 차이는 무엇인가?
Timeout/Overload/Error/Security가 시간축에서 어디에 위치하는가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-09-02. Evidence Flow

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
| EV-09-01 | IV Online Runtime | End-to-end sequence | [AS-IS] |
| EV-09-02 | V Transaction/Timeout | Thread/TX/timeout | [AS-IS] |
| EV-09-03 | VI Message/Error/Log | response/error/context | [AS-IS] |
| EV-09-04 | VII Security | JWT path | [AS-IS + GAP] |
| EV-09-05 | 03/08 chapters | business/framework boundaries | [WORKING BASELINE] |

---

# 2. Figure Plan

## FIG-09-03. Top-down Drill-down

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

# 3. L0 — End-to-End Online Runtime

## FIG-09-04. L0 — End-to-End Online Runtime

```text
Browser
 ↓
HTTP
 ↓
DefaultFilter
 ↓
SecurityFilterChain
 ↓
DispatcherServlet
 ↓
Interceptor
 ↓
OnlineTransactionController
 ↓
TcfFacade
 ↓
OnlineTimeoutExecutor
 ↓
Worker / TransactionTemplate
 ↓
Dispatcher / Handler
 ↓
Facade / Service / DAO / Mapper
 ↓
DB
 ↓
Response Advice / afterCompletion
 ↓
Filter finally
 ↓
HTTP Response
```

---

# 4. Request Thread Timeline

## FIG-09-05. Request Thread Timeline

```text
T0 Request
 ↓ Filter
 ↓ Security
 ↓ MVC
 ↓ Controller
 ↓ submit Worker
 ↓ Future.get(timeout)
 ↓ Response / Exception
 ↓ Cleanup
```

---

# 5. Worker Thread Timeline

## FIG-09-06. Worker Thread Timeline

```text
submit
 ↓
pdmg-online-N
 ↓
Context Install
 ↓
TX BEGIN
 ↓
Dispatch
 ↓
Business
 ↓
DB
 ↓
Deadline
 ↓
Commit/Rollback
 ↓
Context Clear
```

---

# 6. ServiceId Resolution / Routing

## FIG-09-07. ServiceId Resolution / Routing

```text
Header / Context / Path
 ↓
ServiceId
 ↓
Dispatcher Registry
 ↓
Handler
 ↓
handle branch
 ↓
Facade method
```

---

# 7. Business Execution

## FIG-09-08. Business Execution

```text
Handler
 ↓
Facade
 ↓
BizPrePostAspect
 ↓
Service
 ↓
DAO
 ↓
Mapper
 ↓
DB
```

---

# 8. Response Runtime

## FIG-09-09. Response Runtime

```text
Business Result
 ↓
Controller return
 ↓
ResponseBodyAdvice
 ↓
hdr_nhnis + dto
 ↓
afterCompletion
 ↓
Filter finally
```

---

# 9. Known Error Runtime

## FIG-09-10. Known Error Runtime

```text
Exception
 ↓
GlobalExceptionHandler
 ↓
ErrorCode / Type
 ↓
hdr_nhnis + result
 ↓
HTTP status
```

---

# 10. Timeout Runtime

## FIG-09-11. Timeout Runtime

```text
Future.get(5000ms)
 ├─ complete → response
 └─ timeout
      ↓
   cancel(true)
      ↓
   504

Worker may continue
```

---

# 11. Overload Runtime

## FIG-09-12. Overload Runtime

```text
Worker Active = 20
Queue up to 100
 ↓
saturation
 ↓
reject
 ↓
OnlineOverloadException
 ↓
503
```

---

# 12. TCF OFF Runtime

## FIG-09-13. TCF OFF Runtime

```text
HTTP
 ↓
Filter
 ↓
Security
 ↓
MVC
 ↓
Business Controller
 ↓
Facade / Service
 ↓
DAO
 ↓
DB
```

---

# 13. Context Runtime

## FIG-09-14. Context Runtime

```text
Request ThreadLocal
 ↓
Worker capture/install
 ↓
Business
 ↓
ResponseBody stored
 ↓
afterCompletion
 ↓
remove
```

---

# 14. Security Runtime

## FIG-09-15. Security Runtime

```text
Bearer
 ↓
SecurityFilterChain
 ↓
JwtProvider
 ↓
request.ssoId
 ↓
Business Context?
 [GAP]
```

---

# 15. DB Runtime

## FIG-09-16. DB Runtime

```text
Worker
 ↓
Transaction
 ↓
Hikari Connection
 ↓
JDBC Statement
 ↓
DB Session
 ↓
SQL wait / result
```

---

# 16. Runtime Evidence

## FIG-09-17. Runtime Evidence

```text
GUID
+ ServiceId
+ Thread
+ ErrorCode
+ SqlId
+ elapsed
 ↓
Log / ImageLog / Metric
```

---

# 17. Runtime Failure Matrix

## FIG-09-18. Runtime Failure Matrix

```text
Filter fail
Security fail
Routing fail
Worker reject
Timeout
Business reject
DB fail
Response fail
 ↓
Different owner / evidence
```

---

# 18. Architecture Rule Catalog

## FIG-09-19. Rule Set

```text
R-RT-01
Request Thread와 Worker Thread를 분리한다.

R-RT-02
Worker에서 Transaction을 시작한다.

R-RT-03
ServiceId Registry/Branch를 일치시킨다.

R-RT-04
Timeout Response와 Worker 종료를 동일시하지 않는다.

R-RT-05
Response 이후 Context cleanup을 보장한다.

R-RT-06
Known error는 표준 mapping을 적용한다.

R-RT-07
TCF OFF는 별도 runtime path로 명시한다.

R-RT-08
GUID/ServiceId를 end-to-end 유지한다.

R-RT-09
JDBC/DB waits를 request latency와 연계관측한다.

R-RT-10
Runtime path는 Source Evidence로 검증한다.
```

| Rule | 정의 |
|---|---|
| R-RT-01 | Request Thread와 Worker Thread를 분리한다. |
| R-RT-02 | Worker에서 Transaction을 시작한다. |
| R-RT-03 | ServiceId Registry/Branch를 일치시킨다. |
| R-RT-04 | Timeout Response와 Worker 종료를 동일시하지 않는다. |
| R-RT-05 | Response 이후 Context cleanup을 보장한다. |
| R-RT-06 | Known error는 표준 mapping을 적용한다. |
| R-RT-07 | TCF OFF는 별도 runtime path로 명시한다. |
| R-RT-08 | GUID/ServiceId를 end-to-end 유지한다. |
| R-RT-09 | JDBC/DB waits를 request latency와 연계관측한다. |
| R-RT-10 | Runtime path는 Source Evidence로 검증한다. |

---

# 19. Verification / Test

## FIG-09-20. Verification Flow

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
| T-RT-01 | End-to-end happy path trace |
| T-RT-02 | timeout with late worker |
| T-RT-03 | overload reject |
| T-RT-04 | routing unknown serviceId |
| T-RT-05 | business exception |
| T-RT-06 | DB exception/rollback |
| T-RT-07 | TCF ON/OFF parity |
| T-RT-08 | context cleanup |

---

# 20. GAP Register

## FIG-09-21. GAP Lifecycle

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
| GAP-RT-01 | Worker context mutable sharing | High | immutable snapshot |
| GAP-RT-02 | JWT identity binding | Critical | principal binding |
| GAP-RT-03 | TCF OFF facade parity | High | controller→facade |
| GAP-RT-04 | generic/early error coverage | High | fault tests |
| GAP-RT-05 | JDBC cancel/query timeout evidence | High | driver/query test |
| GAP-RT-06 | Runtime→deployment correlation | High | deploymentId/host/jvm tags |

---

# 21. Risk Register

## FIG-09-22. Risk Propagation

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
| RISK-RT-01 | late worker | late DB work after 504 |
| RISK-RT-02 | queue saturation | 503/timeout cascade |
| RISK-RT-03 | context leak | cross-request contamination |
| RISK-RT-04 | security/runtime mismatch | unauthorized/failed requests |
| RISK-RT-05 | DB slow | pool/worker saturation |

---

# 22. Architecture Decision / ADR

## FIG-09-23. Decision Flow

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
| ADR-TASK-004 | Business Core | common facade |
| ADR-TASK-005 | TCF policy | ON default |
| ADR-TASK-009 | Worker Context | immutable |
| ADR-TASK-017 | Timeout | layered |
| ADR-TASK-029 | Pool capacity | end-to-end budget |

---

# 23. Architecture PASS / PDMG Conformance

## FIG-09-24. PASS Model

```text
Architecture Definition
 ↓
PASS

Current Runtime Conformance
 ↓
PARTIAL / CONDITIONAL

Core path confirmed
Critical gaps: context / identity / JDBC timeout evidence
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| TCF ON path | PASS | strong source |
| Request/Worker | PASS | strong source |
| Transaction | PASS/PARTIAL | strong source |
| Timeout | CONDITIONAL | late worker/JDBC |
| Error | CONDITIONAL | early/generic gaps |
| TCF OFF | CONDITIONAL | facade parity |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PARTIAL / CONDITIONAL`  
**Runtime Evidence Coverage:** `HIGH-MEDIUM`

---

# 24. Next Chapter Handoff

## FIG-09-25. 09 → 10

```text
09 ONLINE RUNTIME
"실제로 어떤 순서로 실행되는가?"
     ↓
10 TRANSACTION / TIMEOUT / THREAD / DB
"그 실행의 Thread, Transaction, Timeout, Pool, DB 경계는 정확히 어디인가?" 
```

---

# 25. PDMG ONLINE RUNTIME ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG ONLINE RUNTIME ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**09장 Architecture Definition 판정: `PASS`**
