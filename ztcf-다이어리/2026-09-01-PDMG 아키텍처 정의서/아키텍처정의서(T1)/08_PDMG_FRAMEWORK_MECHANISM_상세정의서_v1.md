# PDMG 전체 아키텍처 정의서
# 08. PDMG FRAMEWORK / MECHANISM ARCHITECTURE
## Filter / Context / Security / TCF / Timeout / Transaction / Error / Logging
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-08-FRAMEWORK-MECHANISM`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-08-01. 이 장의 핵심 질문

```text
PDMG 거래를 공통으로 통제하는 Framework Mechanism은 무엇인가?
Request가 Business로 들어가기 전후 어떤 Control이 실행되는가?
TCF ON/OFF, Context, Error, Timeout의 Current GAP는 무엇인가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-08-02. Evidence Flow

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
| EV-08-01 | PDMG Runtime/TCF | Filter→TCF→Handler | [AS-IS] |
| EV-08-02 | Transaction/Timeout | Worker/TX | [AS-IS] |
| EV-08-03 | Message/Context/Error | Context/Error/Log | [AS-IS] |
| EV-08-04 | 03 Application | Framework/Business boundary | [WORKING BASELINE] |
| EV-08-05 | Security | SecurityFilterChain/JWT | [AS-IS + GAP] |

---

# 2. Figure Plan

## FIG-08-03. Top-down Drill-down

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

# 3. L0 — Framework Mechanism Master

## FIG-08-04. L0 — Framework Mechanism Master

```text
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
TransactionDispatcher
 ↓
Handler
 ↓
Business
 ↓
Response / Error / Log
```

---

# 4. Framework vs Business

## FIG-08-05. Framework vs Business

```text
Framework
= How to execute safely

Business
= What business work to execute

Framework → Handler Contract → Business
```

---

# 5. DefaultFilter

## FIG-08-06. DefaultFilter

```text
Request
 ↓
Body Cache
 ↓
Header / GUID
 ↓
ServiceContext
 ↓
MDC
 ↓
FilterChain
 ↓
finally / clear
```

---

# 6. Security Integration

## FIG-08-07. Security Integration

```text
DefaultFilter
 ↓
SecurityFilterChain
 ↓
Verified Principal?
 ↓
DispatcherServlet
```

---

# 7. MVC / Interceptor

## FIG-08-08. MVC / Interceptor

```text
DispatcherServlet
 ↓
HandlerMapping
 ↓
ServicePreventionInterceptor.preHandle
 ↓
Controller
 ↓
afterCompletion
```

---

# 8. TCF Facade

## FIG-08-09. TCF Facade

```text
OnlineTransactionController
 ↓
TcfFacade
 ├─ TimeoutExecutor
 └─ Dispatcher

STF / ETF
= classes may exist
≠ current executed path unless evidence
```

---

# 9. ServiceId Resolution

## FIG-08-10. ServiceId Resolution

```text
ServiceContext Header
   ↓ precedence
Request Header
   ↓
Path Variable
   ↓
ServiceId / null

Mismatch rejection
= [GAP / PROPOSED]
```

---

# 10. Dispatcher / Handler Registry

## FIG-08-11. Dispatcher / Handler Registry

```text
Spring Beans
TransactionHandler[]
 ↓
Registry
serviceId → Handler
 ↓
Dispatch
```

---

# 11. Timeout / Worker Mechanism

## FIG-08-12. Timeout / Worker Mechanism

```text
Request Thread
 ↓ submit
Worker Pool
 ↓
TransactionTemplate
 ↓
Business
 ↓
Deadline
```

---

# 12. Transaction Mechanism

## FIG-08-13. Transaction Mechanism

```text
Worker
 ↓
TransactionTemplate BEGIN
 ↓
Handler
 ↓
Facade REQUIRED
 ↓
Service / DAO
 ↓
Commit / Rollback
```

---

# 13. Context Propagation

## FIG-08-14. Context Propagation

```text
Request ServiceContext
 ↓ capture
Worker
 ↓ install
Business
 ↓ clear
```

---

# 14. Error / Response

## FIG-08-15. Error / Response

```text
Known Exception
 ↓
GlobalExceptionHandler
 ↓
Standard Error

Filter/Security early error
 ↓
MVC bypass possible
 [GAP]
```

---

# 15. Logging / Evidence

## FIG-08-16. Logging / Evidence

```text
GUID / ServiceId
 ↓
MDC
 ↓
ImageLog PRE/POST/EX
 ↓
Application Log
 ↓
Evidence
```

---

# 16. TCF OFF Mechanism

## FIG-08-17. TCF OFF Mechanism

```text
HTTP
 ↓
Filter / Security / MVC
 ↓
Business Controller
 ↓
Facade / Service
 ↓
DAO

TCF / TimeoutExecutor
= not applied by OFF automatically
```

---

# 17. Framework Anti-pattern

## FIG-08-18. Framework Anti-pattern

```text
Framework
 ──► Customer DAO
 X

Handler
 ──► Mapper
 X

Business
 ──► ThreadLocal lifecycle
 X
```

---

# 18. Architecture Rule Catalog

## FIG-08-19. Rule Set

```text
R-FW-01
Framework는 실행통제를 소유하고 업무규칙을 소유하지 않는다.

R-FW-02
Handler는 Facade를 호출한다.

R-FW-03
Duplicate ServiceId는 startup fail.

R-FW-04
ServiceId source 간 mismatch를 검증한다.

R-FW-05
Context는 Thread lifecycle에 맞게 install/clear한다.

R-FW-06
Timeout은 HTTP response와 DB cancel을 동일시하지 않는다.

R-FW-07
Known error는 표준 envelope로 매핑한다.

R-FW-08
STF/ETF 존재만으로 executed path로 표시하지 않는다.

R-FW-09
TCF OFF에서도 공통 Business Core를 유지한다.

R-FW-10
Framework logging은 fail-safe/fail-open 영향도를 정의한다.
```

| Rule | 정의 |
|---|---|
| R-FW-01 | Framework는 실행통제를 소유하고 업무규칙을 소유하지 않는다. |
| R-FW-02 | Handler는 Facade를 호출한다. |
| R-FW-03 | Duplicate ServiceId는 startup fail. |
| R-FW-04 | ServiceId source 간 mismatch를 검증한다. |
| R-FW-05 | Context는 Thread lifecycle에 맞게 install/clear한다. |
| R-FW-06 | Timeout은 HTTP response와 DB cancel을 동일시하지 않는다. |
| R-FW-07 | Known error는 표준 envelope로 매핑한다. |
| R-FW-08 | STF/ETF 존재만으로 executed path로 표시하지 않는다. |
| R-FW-09 | TCF OFF에서도 공통 Business Core를 유지한다. |
| R-FW-10 | Framework logging은 fail-safe/fail-open 영향도를 정의한다. |

---

# 19. Verification / Test

## FIG-08-20. Verification Flow

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
| T-FW-01 | Filter/context lifecycle |
| T-FW-02 | Security chain order |
| T-FW-03 | ServiceId resolution/mismatch |
| T-FW-04 | Registry duplicate/branch |
| T-FW-05 | TCF ON/OFF parity |
| T-FW-06 | early error contract |
| T-FW-07 | context leak/concurrency |

---

# 20. GAP Register

## FIG-08-21. GAP Lifecycle

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
| GAP-FW-01 | ServiceId mismatch rejection 미확정 | High | Controller/Dispatcher validation |
| GAP-FW-02 | Mutable ServiceContext worker 공유 | High | Immutable snapshot |
| GAP-FW-03 | Filter/Security early error envelope | High | Common error filter |
| GAP-FW-04 | TCF OFF timeout/control 차이 | High | Policy/adapter alignment |
| GAP-FW-05 | STF/ETF current runtime 미연결 | Medium | Scope/ADR |
| GAP-FW-06 | Generic exception fallback/Advice order | High | Exception test |

---

# 21. Risk Register

## FIG-08-22. Risk Propagation

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
| RISK-FW-01 | Context leak | User/trace contamination |
| RISK-FW-02 | TCF ON/OFF policy drift | Different transaction/error behavior |
| RISK-FW-03 | Late worker after timeout | resource/late commit risk |
| RISK-FW-04 | Framework business coupling | reuse/change isolation loss |
| RISK-FW-05 | early sendError | contract inconsistency |

---

# 22. Architecture Decision / ADR

## FIG-08-23. Decision Flow

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
| ADR-TASK-004 | Common Business Core | Facade common boundary |
| ADR-TASK-005 | TCF policy | ON default, OFF exception |
| ADR-TASK-008 | Error Standard | Central taxonomy/envelope |
| ADR-TASK-009 | Worker Context | Immutable snapshot |
| ADR-TASK-017 | Timeout Budget | layered |

---

# 23. Architecture PASS / PDMG Conformance

## FIG-08-24. PASS Model

```text
Architecture Definition
 ↓
PASS

Current Framework Conformance
 ↓
PARTIAL / GAP

Strong: Filter/TCF/Worker/TX
Gap: context, error, OFF parity
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Filter/MVC | PASS | current path |
| TCF/Dispatcher | PASS | current path |
| Worker/TX | PASS/PARTIAL | current snapshot |
| Context | GAP | mutable sharing risk |
| Error | CONDITIONAL | early error bypass |
| TCF OFF | CONDITIONAL | control parity gap |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PARTIAL / GAP`  
**Runtime Evidence Coverage:** `HIGH-MEDIUM`

---

# 24. Next Chapter Handoff

## FIG-08-25. 08 → 09

```text
08 FRAMEWORK / MECHANISM
"어떤 공통 Mechanism이 거래를 통제하는가?"
     ↓
09 ONLINE RUNTIME
"그 Mechanism들이 한 거래에서 실제 어떤 시간순서로 실행되는가?" 
```

---

# 25. PDMG FRAMEWORK / MECHANISM ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG FRAMEWORK / MECHANISM ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**08장 Architecture Definition 판정: `PASS`**
