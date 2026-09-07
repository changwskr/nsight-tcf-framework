# PDMG 전체 아키텍처 정의서
# 12. PDMG STANDARD MESSAGE / CONTEXT / ERROR / LOGGING ARCHITECTURE
## hdr_nhnis / GUID / ServiceContext / Error Taxonomy / ImageLog / Runtime Evidence
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-12-MESSAGE-CONTEXT-ERROR-LOGGING`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-12-01. 이 장의 핵심 질문

```text
거래 Header와 DTO는 어떤 책임을 가지는가?
GUID/ServiceId/ServiceContext는 Request-Worker-Response를 어떻게 연결하는가?
Error/Logging/ImageLog는 어떤 Contract와 Evidence를 제공하는가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-12-02. Evidence Flow

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
| EV-12-01 | VI Message/Context/Error/Logging | request/response/context/log | [AS-IS] |
| EV-12-02 | IV/V Runtime | ServiceId/worker/TX context | [AS-IS] |
| EV-12-03 | VII Security | identity/user context | [AS-IS + GAP] |
| EV-12-04 | X Traceability | GUID/ServiceId closed loop | [WORKING BASELINE] |
| EV-12-05 | Decision Register | error/context decisions | [DECISION] |

---

# 2. Figure Plan

## FIG-12-03. Top-down Drill-down

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

# 3. L0 — Message / Context / Evidence Master

## FIG-12-04. L0 — Message / Context / Evidence Master

```text
HTTP Request
 ↓
{ hdr_nhnis, dto }
 ↓
Header Enrichment
 ↓
ServiceContext
 ↓
MDC / GUID
 ↓
Business Runtime
 ↓
Response / Error
 ↓
ImageLog / Log / Trace
 ↓
Runtime Evidence
```

---

# 4. Standard Request Envelope

## FIG-12-05. Standard Request Envelope

```text
REQUEST
┌────────────────────────────┐
│ hdr_nhnis                  │
│  └─ sys_comm               │
│      ├─ std_gbl_id (GUID)  │
│      ├─ rms_svc_c          │
│      ├─ user/source fields │
│      └─ screen/ip context  │
├────────────────────────────┤
│ dto                        │
└────────────────────────────┘
```

---

# 5. Standard Success / Error

## FIG-12-06. Standard Success / Error

```text
SUCCESS
{ hdr_nhnis, dto }

KNOWN ERROR
{ hdr_nhnis, result }

Header
= processing-time context
≠ raw request echo only
```

---

# 6. Header Lifecycle

## FIG-12-07. Header Lifecycle

```text
Request Header
 ↓
DefaultFilter
 GUID / source context
 ↓
Interceptor
 enrichment
 ↓
Controller
 ServiceId finalization
 ↓
ServiceContext
 ↓
Response Header
```

---

# 7. GUID Architecture

## FIG-12-08. GUID Architecture

```text
std_gbl_id / GUID
 ↓
ServiceContext
 ↓
MDC
 ↓
TransactionContext
 ↓
Worker Context
 ↓
ImageLog
 ↓
Runtime Evidence
```

---

# 8. ServiceId in Message

## FIG-12-09. ServiceId in Message

```text
URL / Path ServiceId
      ↘
Header rms_svc_c
      ↘
ServiceContext
      ↓
TransactionDispatcher

Mismatch
= must reject or resolve explicitly
```

---

# 9. ServiceContext Structure

## FIG-12-10. ServiceContext Structure

```text
ServiceContext
├─ Header
├─ Request / Response ref
├─ User context
├─ GUID
├─ ServiceId
└─ Runtime data

ThreadLocal lifecycle
```

---

# 10. ThreadLocal Lifecycle

## FIG-12-11. ThreadLocal Lifecycle

```text
Filter
 ↓ create
ThreadLocal.set
 ↓ enrich
Worker capture/install
 ↓ execute
Worker clear
 ↓ response
Filter finally
 ↓ remove
```

---

# 11. TransactionContext

## FIG-12-12. TransactionContext

```text
TransactionContext
= ServiceId
+ ServiceContext reference
+ startedAt / elapsed

≠ DB Transaction
```

---

# 12. Worker Context Risk

## FIG-12-13. Worker Context Risk

```text
Request ServiceContext
   ↓ same mutable reference?
Worker
   ↓
Servlet request/response refs
   ↓
[RISK]

Target
→ immutable worker snapshot
```

---

# 13. Error Taxonomy

## FIG-12-14. Error Taxonomy

```text
ServiceHandlerNotFound
→ E9999 / SERVICE / 500

BizException
→ business code / BIZ / 500

OnlineTimeoutException
→ FW_TIMEOUT / COMMON / 504

OnlineOverloadException
→ FW_OVERLOADED / COMMON / 503
```

---

# 14. Early Error Boundary

## FIG-12-15. Early Error Boundary

```text
Filter / Security Error
 ↓
sendError / servlet response
 ↓
MVC Advice bypass possible
 ↓
Standard Envelope not guaranteed
 [GAP]
```

---

# 15. ImageLog

## FIG-12-16. ImageLog

```text
Transaction
 ↓
PRE
 ↓
Business
 ↓
POST / EX
 ↓
TB_FW_IMAGE_LOG
 keyed by GUID

fail-open logging behavior
```

---

# 16. Logging Security

## FIG-12-17. Logging Security

```text
Log
 ├─ GUID
 ├─ ServiceId
 ├─ ErrorCode
 └─ elapsed

Do NOT log
 ├─ token
 ├─ password
 ├─ secret
 └─ sensitive full DTO
```

---

# 17. Runtime Evidence Chain

## FIG-12-18. Runtime Evidence Chain

```text
GUID
+ ServiceId
+ ErrorCode
+ SqlId
+ DeploymentId [target]
+ Host/JVM [target]
 ↓
Evidence Index
```

---

# 18. Architecture Rule Catalog

## FIG-12-19. Rule Set

```text
R-MSG-01
hdr_nhnis는 Framework 공통정보, dto는 Business Payload다.

R-MSG-02
GUID는 trace key이며 auth/business PK/idempotency key가 아니다.

R-MSG-03
Response Header는 처리 후 ServiceContext를 사용한다.

R-MSG-04
ServiceId source mismatch를 통제한다.

R-MSG-05
ThreadLocal은 request/worker lifecycle마다 clear한다.

R-MSG-06
Worker Context는 immutable snapshot을 지향한다.

R-MSG-07
Known error는 stable code/envelope를 사용한다.

R-MSG-08
Filter/Security error도 표준 contract를 지향한다.

R-MSG-09
Sensitive data/token/secret logging을 금지한다.

R-MSG-10
ImageLog는 business transaction과 독립될 수 있음을 명시한다.
```

| Rule | 정의 |
|---|---|
| R-MSG-01 | hdr_nhnis는 Framework 공통정보, dto는 Business Payload다. |
| R-MSG-02 | GUID는 trace key이며 auth/business PK/idempotency key가 아니다. |
| R-MSG-03 | Response Header는 처리 후 ServiceContext를 사용한다. |
| R-MSG-04 | ServiceId source mismatch를 통제한다. |
| R-MSG-05 | ThreadLocal은 request/worker lifecycle마다 clear한다. |
| R-MSG-06 | Worker Context는 immutable snapshot을 지향한다. |
| R-MSG-07 | Known error는 stable code/envelope를 사용한다. |
| R-MSG-08 | Filter/Security error도 표준 contract를 지향한다. |
| R-MSG-09 | Sensitive data/token/secret logging을 금지한다. |
| R-MSG-10 | ImageLog는 business transaction과 독립될 수 있음을 명시한다. |

---

# 19. Verification / Test

## FIG-12-20. Verification Flow

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
| T-MSG-01 | request/response schema |
| T-MSG-02 | GUID propagation |
| T-MSG-03 | ServiceId mismatch |
| T-MSG-04 | ThreadLocal cleanup |
| T-MSG-05 | early filter/security error |
| T-MSG-06 | sensitive log scan |
| T-MSG-07 | ImageLog PRE/POST/EX consistency |

---

# 20. GAP Register

## FIG-12-21. GAP Lifecycle

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
| GAP-MSG-01 | Worker mutable context sharing | High | immutable snapshot |
| GAP-MSG-02 | Filter/Security standard error envelope | High | common error handler |
| GAP-MSG-03 | ServiceId mismatch rejection | High | validation rule |
| GAP-MSG-04 | Generic exception fallback/order | High | fault test |
| GAP-MSG-05 | ImageLog DDL/response field governance | Medium/High | DB/schema governance |
| GAP-MSG-06 | Deployment/Host evidence tags | High | observability integration |

---

# 21. Risk Register

## FIG-12-22. Risk Propagation

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
| RISK-MSG-01 | ThreadLocal leak | cross-request contamination |
| RISK-MSG-02 | full DTO/image log | sensitive data exposure |
| RISK-MSG-03 | GUID misuse as auth/idempotency | security/business error |
| RISK-MSG-04 | error envelope inconsistency | client handling complexity |
| RISK-MSG-05 | logging failure side effects | runtime disturbance |

---

# 22. Architecture Decision / ADR

## FIG-12-23. Decision Flow

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
| ADR-TASK-007 | Standard Message | versioned envelope |
| ADR-TASK-008 | Error Handling | central taxonomy |
| ADR-TASK-009 | Worker Context | immutable snapshot |
| ADR-TASK-035 | Observability | GUID/ServiceId correlation |

---

# 23. Architecture PASS / PDMG Conformance

## FIG-12-24. PASS Model

```text
Architecture Definition
 ↓
PASS

Current Message/Context Conformance
 ↓
PARTIAL / GAP

Strong: hdr_nhnis/GUID/Context/ImageLog
Gap: early error / mutable worker context
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Request envelope | PASS | current contract |
| GUID trace | PASS/PARTIAL | strong source |
| ServiceContext | PASS/PARTIAL | mutable worker risk |
| Error mapping | PASS/PARTIAL | known errors |
| Early errors | GAP | MVC bypass |
| Logging security | CONDITIONAL | masking/evidence needed |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PARTIAL / GAP`  
**Runtime Evidence Coverage:** `HIGH-MEDIUM`

---

# 24. Next Chapter Handoff

## FIG-12-25. 12 → 13

```text
12 MESSAGE / CONTEXT / ERROR / LOGGING
"거래정보와 증적은 어떻게 흐르는가?"
     ↓
13 EVENT / CDC / ETL / BATCH / FILE / CACHE
"온라인 이외 실행/데이터 이동 Mechanism은 PDMG와 어떤 관계를 가지는가?" 
```

---

# 25. PDMG STANDARD MESSAGE / CONTEXT / ERROR / LOGGING ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG STANDARD MESSAGE / CONTEXT / ERROR / LOGGING ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**12장 Architecture Definition 판정: `PASS`**
