# NSIGHT / PDMG 아키텍처 정의서
# 05. MECHANISM — Interface / Message / GUID / Framework / Transaction / Timeout / Security / Logging
## Visual-First / Top-down / Drill-down 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-VISUAL-MECHANISM-05`  
> Architecture Level: **MECHANISM / L4**  
> 문서 상태: **Draft / Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_04_PHYSICAL_VISUAL_FIRST_TOPDOWN_상세본.md`  
> 후속 장: **06. RUNTIME**

---

# 0. 이 문서를 읽는 방법

MECHANISM은 **“어디에 배치했는가?”가 아니라 “어떤 표준 계약과 실행규칙으로 동작하는가?”**를 정의한다.

```text
PHYSICAL
WEB / WAS / JVM / WAR / DB / Event / CDC / ETL
        │
        ▼
MECHANISM
Interface Selection
        │
        ▼
Standard Entry
        │
        ▼
Message / Header / GUID / ServiceId
        │
        ▼
Framework / Business Boundary
        │
        ▼
Transaction / Timeout / Retry
        │
        ▼
Event / CDC / ETL / File / Batch
        │
        ▼
Security / JWT / SSO
        │
        ▼
Error / Logging / Evidence
        │
        ▼
RUNTIME
Sequence / Thread / TX / Failure / Recovery
```

---

# 1. VISUAL ROUTE — MECHANISM 전체를 한 장으로 보기

## FIG-MEC-01. Mechanism Architecture Journey

```text
╔══════════════════════════════════════════════════════════════════════════════╗
║                           MECHANISM ARCHITECTURE                             ║
╚══════════════════════════════════════════════════════════════════════════════╝

 ① INTERFACE
    Online / Event / CDC / ETL / File / Batch
         │
         ▼
 ② ENTRY
    Gateway / MCA-MCI / Direct / Standard Controller
         │
         ▼
 ③ MESSAGE
    Header / DTO / Result / Charset
         │
         ▼
 ④ TRACE
    GUID / ServiceId / InterfaceId
         │
         ▼
 ⑤ FRAMEWORK
    Filter / Context / TCF / Dispatcher / Handler
         │
         ▼
 ⑥ EXECUTION RULE
    Transaction / Timeout / Retry / Idempotency
         │
         ▼
 ⑦ SECURITY
    Authentication / Authorization / JWT / SSO / Key
         │
         ▼
 ⑧ ERROR / LOGGING
    HTTP Status / Error Code / Log / ImageLog / Audit
         │
         ▼
 ⑨ RUNTIME
    실제 Sequence / Thread / Failure / Recovery / Evidence
```

---

# 2. PHYSICAL → MECHANISM Handoff

## FIG-MEC-02. Physical Boundary를 Contract로 변환

```text
WEB
 ↓
WAS
 ↓
JVM
 ↓
WAR
 ↓
DB
```

PHYSICAL이 답한 것:

```text
어디에 배치되는가?
```

MECHANISM이 답할 것:

```text
무슨 Interface인가?
무슨 Header인가?
무슨 Timeout인가?
무슨 Error Contract인가?
무슨 Security인가?
무슨 Trace를 남기는가?
```

---

# 3. MECHANISM 핵심 원칙

## FIG-MEC-03. Boundary → Purpose → Contract

```text
Boundary
   ↓
Purpose
   ↓
Mechanism Type
   ↓
Contract
   ↓
Security
   ↓
Timeout / Retry
   ↓
Recovery
   ↓
Trace / Evidence
```

> **경계를 먼저 정의하고, 그 경계를 통과하는 표준계약을 정의한다.**

---

# 4. 목적별 Interface Selection

## FIG-MEC-04. Interface Decision Tree

```text
업무 목적?
   │
   ├─ 즉시 Request / Response
   │      └─ API / MCA / MCI / Controlled Online
   │
   ├─ 비동기 Event
   │      └─ Kafka / Event Broker
   │
   ├─ DB 변경 전달
   │      └─ CDC
   │
   ├─ 대량 데이터 이동
   │      └─ ETL
   │
   ├─ 파일 전달
   │      └─ MFT / FOS
   │
   └─ 일정 기반 대량처리
          └─ Batch Framework
```

### 금지

```text
모든 연계 = REST             X
모든 연계 = DB Direct        X
Bulk Data = Online API       X
Event = Sync HTTP            X
```

---

# 5. 공통 Interface Contract

## FIG-MEC-05. Universal Contract

```text
Interface
│
├─ Interface ID
├─ Producer
├─ Consumer
├─ Purpose
├─ Sync / Async
├─ Schema
├─ Security
├─ Timeout
├─ Retry
├─ Idempotency
├─ Error
├─ Recovery
├─ Trace Key
├─ Owner
└─ Evidence
```

---

# 6. Online Interface

## FIG-MEC-06. Standard Online Path

```text
Channel / Client
      │
      ▼
Gateway / MCA / MCI / Direct
      │
      ▼
WEB / WAS
      │
      ▼
Standard Entry
      │
      ▼
ServiceId
      │
      ▼
Business Runtime
      │
      ▼
Owned / Approved Data
```

---

# 7. Direct도 표준을 유지한다

## FIG-MEC-07. Controlled Direct

```text
Information Terminal / Package UI
       │
       ▼
Direct HTTP/JSON
       │
       ▼
Standard Header
       │
       ▼
ServiceId
       │
       ▼
Business Runtime
```

```text
Direct
≠
표준 면제
```

필수:

```text
GUID
ServiceId
Authentication
Authorization
Timeout
Error Contract
Logging
```

---

# 8. Gateway / APIM 역할

## FIG-MEC-08. Gateway Responsibility

```text
External / Channel
       │
       ▼
Gateway / APIM
       │
       ├─ Authentication
       ├─ Routing
       ├─ Rate Control
       ├─ Policy
       └─ Access Log
       │
       ▼
Business Service
```

금지:

```text
Gateway = Business Logic       X
Gateway → DAO                  X
Gateway → Internal Table DML   X
```

---

# 9. MCA / MCI 역할

## FIG-MEC-09. MCA / MCI Boundary

```text
Channel Message
      │
      ▼
MCA / MCI
      │
      ├─ Channel Protocol
      ├─ Routing
      ├─ Header Mapping
      └─ Format Conversion
      │
      ▼
NSIGHT Application
```

---

# 10. Standard Message

## FIG-MEC-10. PDMG Current Request Envelope

```text
Request
┌─────────────────────────────────┐
│ hdr_nhnis                       │
│   └─ sys_comm                   │
│      ├─ std_gbl_id              │
│      ├─ rms_svc_c               │
│      ├─ tr_sysid                │
│      ├─ tr_trm_ipadr            │
│      ├─ tr_brc                  │
│      ├─ scid                    │
│      └─ optr_eno                │
│                                 │
│ dto                             │
│   └─ business input             │
└─────────────────────────────────┘
```

Current PDMG:

```text
Request = { hdr_nhnis, dto }
```

---

# 11. Success / Error Envelope

## FIG-MEC-11. Response Contract

```text
[Success]

Business Result
   ↓
Framework Response
   ↓
{ hdr_nhnis, dto }


[Known Error]

Exception / Business Error
   ↓
Error Mapping
   ↓
{ hdr_nhnis, result }
```

Current PDMG known contract:

```text
Success = { hdr_nhnis, dto }
Error   = { hdr_nhnis, result }
```

---

# 12. Header vs DTO

## FIG-MEC-12. Message Responsibility

```text
Header
= System / Trace / Identity Context

DTO
= Business Input / Output
```

```text
Request
├─ hdr_nhnis
└─ dto
```

Business Service는 가능한 한 DTO를 명시적으로 사용하고,
공통 Trace/User/System 정보는 Framework Context로 관리한다.

---

# 13. Header Trust Boundary

## FIG-MEC-13. optr_eno는 자체 인증증거가 아니다

```text
Client Header
optr_eno
   │
   │ Untrusted by itself
   ▼
Authentication Evidence
   │
   ▼
Principal
   │
   ▼
Identity Binding
   │
   ▼
Authorization
```

---

# 14. GUID

## FIG-MEC-14. GUID End-to-End

```text
std_gbl_id
   │
   ▼
Filter
   │
   ▼
ServiceContext
   │
   ▼
MDC
   │
   ▼
TCF / Business
   │
   ▼
SQL / External
   │
   ▼
Response / ImageLog / Trace
```

### 역할

```text
GUID = 한 거래흐름의 Correlation Key
```

---

# 15. ServiceId

## FIG-MEC-15. ServiceId Runtime Routing

```text
Request
  │
  ▼
rms_svc_c / Path
  │
  ▼
ServiceId
  │
  ▼
TransactionDispatcher
  │
  ▼
Handler
  │
  ▼
Business
```

### 역할

```text
ServiceId = 무슨 업무거래인가?
```

---

# 16. GUID vs ServiceId

## FIG-MEC-16. Two Different Keys

```text
GUID
= 이 호출이 어느 흐름에 속하는가?

ServiceId
= 이 호출이 무슨 업무거래인가?
```

```text
GUID G1
   └─ ServiceId mgcoa9001S0
```

---

# 17. Header / Path ServiceId Conflict

## FIG-MEC-17. Multiple Sources

```text
ServiceContext.header.rms_svc_c
           │
           ▼
Request JSON rms_svc_c
           │
           ▼
Path Variable
```

Risk:

```text
Header ServiceId ≠ Path ServiceId
```

TO-BE 후보:

```text
Mismatch Reject
```

---

# 18. Charset

## FIG-MEC-18. Encoding Boundary

```text
Client
  │
  ▼
Entry Adapter
  │
  ├─ Validate Charset
  └─ Convert if needed
  │
  ▼
Application Internal String
  │
  ▼
External / File / DB
```

기본 신규 방향:

```text
UTF-8 중심
```

Legacy/File/대외는 별도 Contract로 관리한다.

---

# 19. Framework vs Business

## FIG-MEC-19. Responsibility Boundary

```text
┌──────────────── FRAMEWORK ────────────────┐
│ Filter                                    │
│ Context                                   │
│ TCF                                       │
│ Timeout                                   │
│ Transaction Control                      │
│ Error                                     │
│ Logging                                   │
└──────────────────┬────────────────────────┘
                   ▼
┌──────────────── BUSINESS ─────────────────┐
│ Handler                                   │
│ Facade                                    │
│ Service                                   │
│ DAO / Mapper                              │
│ Business Validation / Decision            │
└───────────────────────────────────────────┘
```

---

# 20. PDMG Current Mechanism Reference

## FIG-MEC-20. PDMG AS-IS Components

```text
PDMG
│
├─ DefaultFilter
├─ ServiceContext
├─ ServicePreventionInterceptor
├─ OnlineTransactionController
├─ TcfFacade
├─ OnlineTimeoutExecutor
├─ TransactionDispatcher
├─ TransactionHandler
├─ ResponseBodyAdvice
├─ GlobalExceptionHandler
└─ ImageLog
```

이 구조는 Reference이며 NSIGHT Target 전체로 자동 승격하지 않는다.

---

# 21. TCF ON / OFF

## FIG-MEC-21. Entry Difference

```text
[TCF ON]

Request
 ↓
OnlineTransactionController
 ↓
TcfFacade
 ↓
Dispatcher
 ↓
Handler
 ↓
Business


[TCF OFF]

Request
 ↓
Business Controller
 ↓
Facade / Service
 ↓
DAO
```

### 핵심 질문

```text
ON/OFF에서
Timeout
Transaction
Error
Logging
Policy
가 동일한가?
```

---

# 22. Transaction 8-Step Responsibility Model

## FIG-MEC-22. 8 Steps

```text
① System Pre
      ↓
② Common Pre
      ↓
③ Business Pre
      ↓
④ Controller
      ↓
⑤ Business Service
      ↓
⑥ Business Post
      ↓
⑦ Common Post
      ↓
⑧ System Post
```

### 주의

Current PDMG 실제 TcfFacade가 STF/ETF를 호출하지 않는다면,
STF/ETF를 AS-IS 실행단계처럼 그리지 않는다.

---

# 23. Current 실제 선후처리

## FIG-MEC-23. PDMG Current Pre/Post

```text
DefaultFilter
    ↓
ServicePreventionInterceptor.preHandle
    ↓
Controller / TCF / Business
    ↓
ServicePreventionInterceptor.afterCompletion
    ↓
DefaultFilter finally
    ↓
Context Clear
```

---

# 24. Transaction Boundary

## FIG-MEC-24. Current Worker TX

```text
Request Thread
   │
   ▼
OnlineTimeoutExecutor
   │
   │ submit
   ▼
Worker Thread
   │
   ▼
TransactionTemplate BEGIN
   │
   ▼
Dispatcher
   ↓
Handler
   ↓
Facade @Transactional(REQUIRED)
   ↓
Service
   ↓
DAO / Mapper / SQL
   │
   ▼
COMMIT / ROLLBACK
```

### 핵심

```text
@Transactional annotation 위치
≠
실제 물리 TX BEGIN 위치
```

---

# 25. Transaction Owner

## FIG-MEC-25. TX Owner Determination

```text
Runtime Call Path
  ↓
Transaction Manager
  ↓
TransactionTemplate / AOP
  ↓
DB Connection
  ↓
Commit / Rollback
```

Transaction Owner는 소스 Annotation 하나로 판단하지 않는다.

---

# 26. Timeout Hierarchy

## FIG-MEC-26. Budget

```text
DB Query Timeout
       <
Transaction / Worker Deadline
       <
Server / Downstream Timeout
       <
Client Timeout
```

---

# 27. Current PDMG Timeout Snapshot

## FIG-MEC-27. AS-IS Snapshot

```text
tcf.enabled        = true
timeout.enabled    = true
milliseconds       = 5000
pool-size          = 20
queue-capacity     = 100
```

```text
[AS-IS SNAPSHOT]
```

NSIGHT Target NFR로 자동 승격하지 않는다.

---

# 28. Request Thread vs Worker

## FIG-MEC-28. Thread Split

```text
Request Thread
   │
   │ Future.get(5000ms)
   ▼
OnlineTimeoutExecutor
   │
   └─ submit
        │
        ▼
   Worker pdmg-online-N
        │
        ▼
     Business / DB
```

---

# 29. Timeout Timeline

## FIG-MEC-29. HTTP Timeout vs Worker

```text
T0
Request Thread        Worker Thread
    │                       │
    │ wait                  │ business
    │                       │ SQL
    │                       │
T+5s│ timeout               │ still running?
    │                       │
    ├─ cancel(true)         │
    └─ 504                  │
```

### 절대 동일시 금지

```text
HTTP 504
≠ Worker 종료
≠ JDBC Cancel
≠ DB Rollback 완료
```

---

# 30. cancel(true)의 한계

## FIG-MEC-30. Interrupt

```text
future.cancel(true)
      │
      ▼
Thread interrupt request
      │
      ├─ Java code 반응?       verify
      ├─ JDBC driver 반응?     verify
      ├─ SQL cancel?           verify
      └─ DB session kill?      자동 가정 금지
```

---

# 31. Late Commit Prevention

## FIG-MEC-31. Deadline Guard

```text
HTTP Timeout
   ↓
Worker continues
   ↓
SQL returns
   ↓
Deadline Check
   │
   ├─ expired → ROLLBACK
   └─ valid   → COMMIT
```

목적:

```text
사용자는 실패 응답을 받았는데
DB가 뒤늦게 성공 commit 되는 문제 방지
```

---

# 32. Retry

## FIG-MEC-32. Retry Decision

```text
Failure
  │
  ├─ Transient?
  │     ├─ YES
  │     │   ↓
  │     │ Backoff
  │     │   ↓
  │     │ Max Retry
  │     │   ↓
  │     │ DLQ / Recovery
  │     │
  │     └─ NO → No Retry
```

---

# 33. Retry 금지

```text
금융거래 DML
중복위험 거래
Validation Error
Authorization Error
Business Reject
Non-idempotent Command
```

→ Blind Retry 금지.

---

# 34. Idempotency

## FIG-MEC-33. Duplicate Prevention

```text
Retryable Command
      │
      ▼
Idempotency Key
      │
      ▼
Already Processed?
   ├─ YES → previous result / reject
   └─ NO  → execute
```

---

# 35. Timeout + Retry 중복위험

## FIG-MEC-34. Duplicate Execution Risk

```text
Original Request
   │
   ├─ Worker still executing
   │
   └─ Caller sees Timeout
            │
            ▼
          Retry
            │
            ▼
       Duplicate DML Risk
```

---

# 36. Error Taxonomy

## FIG-MEC-35. Error Classes

```text
Validation
Authentication
Authorization
Business Reject
Routing / Handler
Timeout
Overload
External
DB / SQL
System / Unknown
```

각 Error Class는 Retry/HTTP/Logging 정책이 달라야 한다.

---

# 37. HTTP Status + Application Error Code

## FIG-MEC-36. Dual Contract

```text
HTTP Status
= Transport / Runtime Result

Application Error Code
= Framework / Business Meaning
```

Known PDMG examples:

```text
503 + FW_OVERLOADED
504 + FW_TIMEOUT
500 + SERVICE / BIZ Error
```

---

# 38. Early Filter Error

## FIG-MEC-37. MVC 우회 오류

```text
Request
  ↓
DefaultFilter
  │
  ├─ 400
  └─ 401
  │
  ▼
sendError
```

이 경우:

```text
ResponseBodyAdvice
GlobalExceptionHandler
Standard Result Envelope
```

를 우회할 수 있다.

```text
[GAP]
Early Error Standardization
```

---

# 39. Generic Exception Gap

## FIG-MEC-38. Unknown Exception

```text
Unknown Runtime / SQL Exception
       │
       ▼
Explicit Handler?
  ├─ YES → Standard Error
  └─ NO  → Spring default /error 가능
```

필요:

```text
Stable Code
Safe Message
Trace ID
No sensitive stack exposure
```

---

# 40. Logging Mechanism

## FIG-MEC-39. Logging Channels

```text
Runtime
│
├─ MDC Correlation Log
├─ Application Transaction Log
├─ SQL Log
├─ ImageLog
├─ Security Audit
└─ Deployment / Operation Log
```

---

# 41. MDC

## FIG-MEC-40. MDC Correlation

```text
GUID
ServiceId
UserId
Client IP
SQL ID
   │
   ▼
MDC / ThreadContext
   │
   ▼
Application Log
```

Worker Thread 전환 시 명시적 Capture/Restore/Clear가 필요하다.

---

# 42. ImageLog

## FIG-MEC-41. ImageLog Lifecycle

```text
Request
  ↓
PRE INSERT
  ↓
Business Runtime
  │
  ├─ Normal
  │    ↓
  │ POST UPDATE
  │
  └─ Exception
       ↓
      EX UPDATE / INSERT
```

ImageLog는 Business Ledger가 아니라 운영/감사 Evidence다.

---

# 43. ImageLog Fail-open

## FIG-MEC-42. Audit Failure

```text
ImageLog Write Failure
      │
      ├─ Business Continue
      └─ Log Error
```

장점:

```text
Business Availability 보호
```

위험:

```text
Audit Gap
```

필요:

```text
Alert
Reconciliation
Recovery
```

---

# 44. Sensitive Logging

## FIG-MEC-43. Never Log Raw

```text
Authorization Bearer
Access Token
Refresh Token
Password
Private Key
HMAC Secret
DB Password
Sensitive Personal Data
```

---

# 45. SQL Evidence

## FIG-MEC-44. ServiceId → SQL

```text
ServiceId
  ↓
Handler
  ↓
Service
  ↓
DAO / Mapper
  ↓
SqlId
  ↓
Elapsed / Rows / Error
```

SQL Parameter는 Masking 정책을 적용한다.

---

# 46. Event Mechanism

## FIG-MEC-45. Event Contract

```text
Producer
  │
  ▼
Event
  ├─ Event Type
  ├─ Event Key
  ├─ Event Time
  ├─ Producer
  ├─ Payload
  └─ Correlation
  │
  ▼
Kafka / Broker
  │
  ▼
Consumer
```

---

# 47. Event State

## FIG-MEC-46. Event Processing State

```text
PRODUCED
  ↓
STORED
  ↓
CONSUMED
  ↓
PROCESSED
  ↓
ACK / OFFSET COMMIT

Failure
  ├─ Retry
  ├─ Replay
  ├─ DLQ
  └─ Manual Recovery
```

---

# 48. Event Idempotency

## FIG-MEC-47. Duplicate Event

```text
Same Event Key
    │
    ├─ first     → process
    └─ duplicate → skip / reconcile
```

---

# 49. CDC Mechanism

## FIG-MEC-48. CDC Flow

```text
Source DB
  │
  ▼
Capture
  │
  ▼
Transport / Relay
  │
  ▼
Apply
  │
  ▼
RDW
```

필수 운영지표:

```text
Capture Lag
Transport Lag
Apply Lag
Error
Restart
```

---

# 50. CDC 금지

```text
CDC = Business API 대체                  X
CDC = Strong Synchronous Consistency      X
Lag 측정 없이 "실시간" 주장             X
Source DB Impact 검증 없이 적용          X
```

---

# 51. ETL Mechanism

## FIG-MEC-49. ETL Flow

```text
Source
  ↓
Extract
  ↓
Transform
  ↓
Validate
  ↓
Load
  ↓
Reconcile
  ↓
ADW / Mart
```

---

# 52. ETL 금지

```text
Online Request 안에서 ETL 실행       X
Restart 없는 Script성 Batch          X
Online DB Pool과 Heavy ETL 공유      X
Reconciliation 없이 성공 선언        X
```

---

# 53. File Mechanism

## FIG-MEC-50. File Contract

```text
Producer
  ↓
Create File
  │
  ├─ Filename
  ├─ Charset
  ├─ Schema
  ├─ Record Count
  ├─ Hash
  └─ Encryption
  ↓
MFT / FOS
  ↓
Consumer
  ↓
Validate / Process / Archive
```

---

# 54. File State

## FIG-MEC-51. File Runtime State

```text
READY
  ↓
TRANSFER
  ↓
RECEIVED
  ↓
VALIDATED
  ↓
PROCESSED
  ↓
ARCHIVED

Failure
→ RETRY / QUARANTINE / MANUAL RECOVERY
```

---

# 55. File Recovery

## FIG-MEC-52. Recovery

```text
Transfer Failure
   ↓
Retry
   ↓
Max Retry
   ↓
Quarantine
   ↓
Operator Recovery
   ↓
Reconciliation
```

---

# 56. Batch Framework

## FIG-MEC-53. Batch Runtime Contract

```text
Scheduler / Control-M
      │
      ▼
Batch Entry
      │
      ▼
Job
      │
      ▼
Step
      │
      ▼
Reader / Processor / Writer
      │
      ▼
DB / File / External
```

---

# 57. Batch Dependency

## FIG-MEC-54. Batch Orchestration

```text
Job A
  ↓ success
Job B
  ↓ success
Job C
```

필수:

```text
Schedule
Dependency
Business Date
Job Parameter
Restart Point
Recovery
```

---

# 58. Batch Restart

## FIG-MEC-55. Restart Contract

```text
FAILED
  ↓
Failure Point
  ↓
Restartable?
  ├─ YES → restart/checkpoint
  └─ NO  → compensate / full rerun
```

---

# 59. Delivery Mechanism

## FIG-MEC-56. Source → Runtime

```text
Source
  ↓
Build
  ↓
Artifact
  ↓
Deploy
  ↓
Config
  ↓
Runtime
```

원칙:

```text
Build Once
Promote Artifact
Environment Config Separation
```

---

# 60. 수동 운영변경 금지

```text
운영서버 직접 Compile       X
운영 WAR 직접 덮어쓰기       X
운영 Config 무승인 수정      X
변경 Evidence 없음           X
```

---

# 61. Solution Boundary

## FIG-MEC-57. Commercial Solution Contract

```text
Business Solution
      │
      ▼
Standard Adapter / Interface
      │
      ▼
NSIGHT Service / Data / Framework
```

```text
Solution
≠ Standard 면제
```

---

# 62. SELF-BI

## FIG-MEC-58. Self-BI Contract

```text
Self-BI User
    ↓
BI Portal
    ↓
Approved Data Access
    ↓
RDW / ADW
```

금지:

```text
Self-BI → Core Unlimited SQL
```

---

# 63. EBM

## FIG-MEC-59. Event → Decision → Offering

```text
Customer Action
   ↓
Event
   ↓
Behavior Processing
   ↓
EBM Decision
   ↓
Offer / Contact
```

---

# 64. SSO / JWT

## FIG-MEC-60. Security Chain

```text
User
 ↓
SSO / Authentication
 ↓
Identity Evidence
 ↓
Token Issue
 ↓
Service Request
 ↓
JWT Verify
 ↓
Principal
 ↓
Authorization
```

---

# 65. PDMG Normal Login Reference

## FIG-MEC-61. mgjwa1000C0

```text
User
 ↓
pdmg-ui
 ↓
mgjwa1000C0
 ↓
Credential Check / BCrypt
 ↓
JwtTokenIssuer
 ↓
RS256 Access Token
+
Random Refresh Token
 ↓
Refresh Hash DB
```

---

# 66. PDMG SSO Reference

## FIG-MEC-62. mgjwa1000C1

```text
Trusted Caller
   ↓
mgjwa1000C1
   │
   ├─ allowed service
   ├─ timestamp
   ├─ HMAC
   └─ caller IP
   ↓
Trusted User Info
   ↓
PDMG Token Pair
```

이 흐름을 직접 OIDC Callback이라고 단정하지 않는다.

---

# 67. Key / Secret Taxonomy

## FIG-MEC-63. Secret Separation

```text
RS256 Private Key
= Token Signing

RS256 Public Key / JWKS
= Token Verification

HMAC Secret
= Trusted Caller Validation

Legacy jwt.secret
= 별도 Legacy Mechanism 가능
```

---

# 68. JWT Verify

## FIG-MEC-64. Verification Contract

```text
Bearer Token
  ↓
Signature
  ↓
Issuer / Audience / Expiration [Target]
  ↓
Principal
  ↓
Identity Binding
  ↓
Authorization
```

---

# 69. Identity Binding GAP

## FIG-MEC-65. ssoId vs optr_eno

```text
Validated JWT Subject
      │
      └─ ssoId
          │
          │ ?
          ▼
Header User
      └─ optr_eno
```

```text
[GAP]
Trusted Principal → Header/UserContext Binding
```

---

# 70. Authentication vs Authorization

## FIG-MEC-66. Two Security Decisions

```text
Authentication
= 누구인가?

        ↓

Authorization
= 무엇을 할 수 있는가?
```

SecurityFilterChain 존재만으로 업무인가가 완결되었다고 판단하지 않는다.

---

# 71. Security Revalidation

## FIG-MEC-67. Trust Boundary Revalidation

```text
Gateway Authentication
       ↓
Application JWT Verify
       ↓
Principal
       ↓
Business Authorization
       ↓
Data Authorization
```

---

# 72. Direct WAS Bypass

## FIG-MEC-68. Bypass Defense

```text
Normal
Client → WEB/Gateway → WAS

Risk
Internal Client ─────→ WAS Direct
```

필요:

```text
Network ACL
JWT Validation
Trusted Source Policy
```

---

# 73. Exception Mechanism

## FIG-MEC-69. Exception Flow

```text
Exception
  ↓
Classify
  ↓
Map
  ↓
HTTP Status + App Code
  ↓
Safe Message
  ↓
Trace / Log / Audit
```

---

# 74. External vs Internal Error Detail

## FIG-MEC-70. Safe Error Boundary

```text
Internal
├─ Stack
├─ Class
├─ Method
└─ Root Cause

External
├─ Stable Error Code
├─ Safe Message
└─ Trace ID
```

---

# 75. Traceability Keys

## FIG-MEC-71. Key Map

```text
Architecture / Business Transaction
→ ServiceId

Runtime Request
→ GUID

System Interface
→ InterfaceId

Event
→ EventId / CorrelationId

File
→ FileId / InterfaceId

Batch
→ JobId / ExecutionId
```

---

# 76. Service Trace

## FIG-MEC-72. ServiceId Drill-down

```text
ServiceId
  ↓
Handler
  ↓
Facade
  ↓
Service
  ↓
DAO
  ↓
Mapper / SQL
  ↓
DB
```

---

# 77. Error Trace

## FIG-MEC-73. Error Drill-down

```text
HTTP Status
  ↓
Error Code
  ↓
GUID
  ↓
ServiceId
  ↓
Application / JVM
  ↓
Worker
  ↓
SQL / External
```

---

# 78. Retry / Security / Timeout Decision

## FIG-MEC-74. Failure Decision Matrix

```text
Failure
  │
  ├─ Validation      → No Retry
  ├─ Authentication  → No Retry
  ├─ Authorization   → No Retry
  ├─ Business Reject → No Retry
  ├─ Timeout         → Original status 확인
  └─ Transient Infra → Controlled Retry Candidate
```

---

# 79. MUST — Interface

```text
Interface ID
Producer / Consumer
Purpose
Schema
Security
Timeout
Error
Recovery
Trace
Owner
```

---

# 80. MUST — ServiceId

```text
Unique
Naming Standard
Registered Handler
Handler Branch
Header/Path Consistency
Trace
```

---

# 81. MUST — GUID

```text
Entry에서 생성/수용
임의 재생성 금지
Thread / Interface 간 전파
Error/Log/Evidence 포함
```

---

# 82. MUST — Framework

```text
공통 기능 중앙화
Context lifecycle 관리
Thread propagation 명시
Business Logic 침투 금지
Error/Logging 일관성
```

---

# 83. MUST — Retry

```text
Retryable Condition
Max Retry
Backoff
Idempotency
Final Recovery
```

---

# 84. MUST — Security

```text
Trusted Principal
Identity Binding
Authorization
Key Separation
No Raw Token Log
Direct Bypass Control
```

---

# 85. Standard Exception Governance

## FIG-MEC-75. Rule Exception

```text
Standard
  │
  ├─ Comply
  │
  └─ Exception
       ↓
      ADR
       │
       ├─ Reason
       ├─ Scope
       ├─ Owner
       ├─ Risk
       ├─ Control
       └─ Expiry
```

---

# 86. Anti-pattern Map

## FIG-MEC-76. Mechanism Anti-pattern

```text
[Interface]
All REST / Direct DB / Bulk Online

[Message]
UI별 Header / Error / Charset

[Trace]
GUID reset / ServiceId mismatch

[Framework]
업무별 Timeout / JWT parser / Handler→DAO

[Transaction]
Blind Retry / Long TX / Timeout Late Commit

[Security]
Header Identity Trust / Raw Token Log

[Data]
CDC=Strong Sync / ETL in Online

[Operations]
No Recovery / No Evidence / Manual Prod Edit
```

---

# 87. Inventory — Interface

```yaml
interface:
  interfaceId:
  type:
  producer:
  consumer:
  purpose:
  syncType:
  schema:
  security:
  timeout:
  retry:
  recovery:
  traceKey:
  owner:
  evidence:
  status:
```

---

# 88. Inventory — Service

```yaml
service:
  serviceId:
  endpoint:
  handler:
  facade:
  service:
  transactionPolicy:
  timeoutPolicy:
  securityPolicy:
  errorPolicy:
  evidence:
```

---

# 89. Inventory — Timeout

```yaml
timeoutPolicy:
  serviceId:
  client:
  gateway:
  web:
  worker:
  transaction:
  datasource:
  query:
  external:
  evidence:
```

---

# 90. Inventory — Retry

```yaml
retryPolicy:
  interfaceId:
  retryableErrors:
  maxRetry:
  backoff:
  idempotencyKey:
  finalRecovery:
  owner:
```

---

# 91. Inventory — Security

```yaml
security:
  mechanismId:
  authType:
  issuer:
  verifier:
  keyType:
  keyStore:
  identityBinding:
  authorization:
  revocation:
  audit:
```

---

# 92. NFR → MECHANISM

## FIG-MEC-77. NFR Projection

```text
Performance
→ Timeout / Sync-Async / Message Size

Availability
→ Retry / Recovery / Replay / Idempotency

Scalability
→ Event / Async / Stateless

Security
→ Authentication / Authorization / Key / Masking

Observability
→ GUID / ServiceId / Error / Logging
```

---

# 93. AS-IS vs TO-BE Alignment

## FIG-MEC-78. PDMG Reference Alignment

```text
NSIGHT Mechanism Target
       │
       ▼ compare
PDMG Current Mechanism
       │
       ├─ conform
       ├─ partial
       ├─ drift
       └─ gap
```

PDMG에서 비교할 대상:

```text
Message
GUID / ServiceId
Framework
TCF
Transaction
Timeout
Error
Logging
JWT / SSO
```

---

# 94. PDMG AS-IS로만 유지할 항목

```text
5000ms Timeout
Worker 20
Queue 100
Local Port
Current UI token storage
Current Error Handler implementation
Current ImageLog schema
```

Target Standard로 자동 승격하지 않는다.

---

# 95. CONFIRMED / WORKING BASELINE

```text
[CONFIRMED / WORKING BASELINE]

- 목적별 Interface 분리
- Request {hdr_nhnis,dto}
- Success {hdr_nhnis,dto}
- Known Error {hdr_nhnis,result}
- GUID = std_gbl_id
- ServiceId = rms_svc_c 중심 Runtime Key
- Framework / Business 책임 분리
- TCF ON / OFF 구조
- PDMG Timeout 5000 / Worker20 / Queue100 AS-IS
- CDC / ETL / File / Batch 별도 Mechanism
- SSO / JWT Security Mechanism 존재
```

---

# 96. OPEN

```text
[OPEN-MEC-01] HTTP/JSON 공식 적용범위
[OPEN-MEC-02] Header/Path ServiceId mismatch rejection
[OPEN-MEC-03] Spring TX Timeout 실제값
[OPEN-MEC-04] JDBC Query Timeout 실제값
[OPEN-MEC-05] Retryable Error Catalog
[OPEN-MEC-06] Idempotency Standard
[OPEN-MEC-07] Generic Exception Contract
[OPEN-MEC-08] JWT issuer/audience/JWKS 최종 정책
[OPEN-MEC-09] Refresh/Denylist 실제 Enforcement
[OPEN-MEC-10] Event/File/Batch 전사 Catalog
```

---

# 97. GAP Register

| ID | GAP | 영향 |
|---|---|---|
| GAP-MEC-01 | Interface Catalog 전수 미완료 | Integration |
| GAP-MEC-02 | HTTP/JSON Scope 미확정 | Standard |
| GAP-MEC-03 | ServiceId mismatch enforcement 미확정 | Routing |
| GAP-MEC-04 | Request Resolver / TCF Controller 계약 미확정 | MVC |
| GAP-MEC-05 | Timeout Budget 전수 미완료 | Runtime |
| GAP-MEC-06 | Query Timeout 미확정 | DB |
| GAP-MEC-07 | TX Timeout 미확정 | TX |
| GAP-MEC-08 | Retry / Idempotency 표준 미완료 | Recovery |
| GAP-MEC-09 | Early Error Envelope 미완료 | Error |
| GAP-MEC-10 | Generic Exception 표준 미완료 | Error |
| GAP-MEC-11 | JWT Identity Binding 미완료 | Security |
| GAP-MEC-12 | Denylist Enforcement 미확정 | Security |
| GAP-MEC-13 | Sensitive Log Masking 미완료 | Security |
| GAP-MEC-14 | Event/File/Batch Recovery Catalog 미완료 | Ops |
| GAP-MEC-15 | ImageLog Fail-open Recovery 미완료 | Audit |
| GAP-MEC-16 | Direct WAS Bypass 통제 미확정 | Security |

---

# 98. RISK Register

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-MEC-01 | Blind Retry 금융 DML 중복 | Critical |
| RISK-MEC-02 | Timeout Late Commit | Critical |
| RISK-MEC-03 | Header/Path ServiceId 불일치 | High |
| RISK-MEC-04 | Header 사용자정보 신뢰 | Critical |
| RISK-MEC-05 | Raw Token/Secret Log | Critical |
| RISK-MEC-06 | Event/Batch의 Online 자원 점유 | High |
| RISK-MEC-07 | CDC Strong Sync 오판 | High |
| RISK-MEC-08 | File 중복/덮어쓰기 | High |
| RISK-MEC-09 | Unknown Exception 비표준 응답 | High |
| RISK-MEC-10 | Retry without Idempotency | Critical |
| RISK-MEC-11 | ImageLog Fail-open 감사공백 | High |
| RISK-MEC-12 | Framework 기능 업무별 재구현 | High |

---

# 99. ADR 후보

```text
ADR-MEC-01 Interface Type Selection
ADR-MEC-02 HTTP/JSON Scope
ADR-MEC-03 Standard Message Envelope
ADR-MEC-04 ServiceId Consistency
ADR-MEC-05 GUID Propagation
ADR-MEC-06 Charset Standard
ADR-MEC-07 TCF ON/OFF Target Policy
ADR-MEC-08 Transaction Ownership
ADR-MEC-09 Timeout Budget
ADR-MEC-10 Retry / Idempotency
ADR-MEC-11 Error Contract
ADR-MEC-12 Generic Exception Policy
ADR-MEC-13 JWT Identity Binding
ADR-MEC-14 Revocation / Denylist
ADR-MEC-15 Sensitive Logging
ADR-MEC-16 File/Event/Batch Recovery
```

---

# 100. Verification Checklist — Interface

```text
[ ] Interface ID?
[ ] Producer/Consumer?
[ ] 목적에 맞는 Mechanism?
[ ] Schema?
[ ] Security?
[ ] Timeout?
[ ] Retry?
[ ] Recovery?
[ ] Trace Key?
```

---

# 101. Verification Checklist — Message / Trace

```text
[ ] Header/DTO 분리?
[ ] GUID 존재?
[ ] ServiceId 존재?
[ ] Charset 명확?
[ ] Success/Error Envelope?
[ ] Identity Binding?
```

---

# 102. Verification Checklist — Transaction / Timeout

```text
[ ] 실제 TX Owner 검증?
[ ] Query Timeout?
[ ] Worker Deadline?
[ ] Timeout Hierarchy?
[ ] Late Commit 방지?
[ ] cancel(true) 한계 검증?
```

---

# 103. Verification Checklist — Recovery

```text
[ ] Retryable Error?
[ ] Backoff?
[ ] Max Retry?
[ ] Idempotency?
[ ] DLQ / Quarantine?
[ ] Reconciliation?
```

---

# 104. Verification Checklist — Security

```text
[ ] Authentication / Authorization 분리?
[ ] Principal / Header Identity Binding?
[ ] Key / Secret 분리?
[ ] Raw Token Log 금지?
[ ] Direct Bypass 통제?
[ ] Revocation 실제 반영?
```

---

# 105. Verification Checklist — Error / Logging

```text
[ ] Error Taxonomy?
[ ] HTTP + App Code?
[ ] Early Error Standard?
[ ] Generic Exception?
[ ] Stack Trace External 차단?
[ ] GUID/ServiceId Log?
[ ] ImageLog Failure Alert?
```

---

# 106. Completion Gate

## FIG-MEC-79. Mechanism Completion Gate

```text
Interface Selected?
    ↓ YES
Contract Defined?
    ↓ YES
Message Standardized?
    ↓ YES
Trace Defined?
    ↓ YES
Security Defined?
    ↓ YES
Transaction/Timeout Defined?
    ↓ YES
Retry/Recovery Defined?
    ↓ YES
Error/Logging Defined?
    ↓ YES
Runtime Testable?
    ↓ YES
MECHANISM PASS
```

하나라도 NO:

```text
CONDITIONAL / GAP / ADR
```

---

# 107. RUNTIME Handoff

## FIG-MEC-80. MECHANISM → RUNTIME

```text
MECHANISM Output
│
├─ Interface Type
├─ Entry Contract
├─ Message / Header / DTO
├─ GUID / ServiceId
├─ Framework Entry
├─ Transaction Policy
├─ Timeout Policy
├─ Retry / Idempotency
├─ Security
├─ Error Contract
├─ Event / CDC / ETL / File / Batch
└─ Logging / Evidence
       │
       ▼
RUNTIME Input
│
├─ 실제 Sequence
├─ 실제 Thread
├─ 실제 TX Begin/Commit/Rollback
├─ 실제 Timeout 시점
├─ 실제 Failure Branch
├─ 실제 Recovery
├─ 실제 Resource Usage
└─ 실제 Runtime Evidence
```

---

# 108. RUNTIME에서 반드시 답할 질문

```text
1. 실제 요청은 어떤 순서로 흐르는가?
2. 어느 Thread에서 실행되는가?
3. TX는 어디서 Begin/Commit/Rollback 되는가?
4. Timeout은 언제 발생하는가?
5. Timeout 후 Worker/DB는 어떻게 되는가?
6. Error는 어느 계층에서 변환되는가?
7. GUID/MDC/Context는 Thread를 넘어 어떻게 전달되는가?
8. JWT는 실제 어디서 검증되는가?
9. Event/CDC/ETL/File/Batch의 Failure State는 무엇인가?
10. Runtime Evidence는 무엇으로 남는가?
```

---

# 109. MECHANISM 최종 통합 지도

## FIG-MEC-81. Summary

```text
BOUNDARY
  ↓
PURPOSE
  ↓
INTERFACE
API / MCA / Event / CDC / ETL / File / Batch
  ↓
CONTRACT
Header / DTO / Schema / Charset
  ↓
TRACE
GUID / ServiceId / InterfaceId
  ↓
FRAMEWORK
Filter / Context / TCF / Dispatcher / Handler
  ↓
EXECUTION RULE
Transaction / Timeout / Retry / Idempotency
  ↓
SECURITY
SSO / JWT / Auth / AuthZ / Key
  ↓
ERROR / LOGGING
HTTP + Code / Log / ImageLog / Audit
  ↓
RUNTIME
Sequence / Thread / TX / Failure / Recovery / Evidence
```

---

# 110. Definition of Done

## Interface
- [x] Online/Event/CDC/ETL/File/Batch 목적별 분리
- [x] Standard Entry 정의
- [x] Direct도 표준 유지
- [x] Gateway/MCA/MCI 책임 구분

## Message / Trace
- [x] Header/DTO/Result 구조
- [x] Success/Error Envelope
- [x] GUID/ServiceId 구분
- [x] Header Identity Trust Boundary
- [x] Charset 책임

## Framework
- [x] Framework / Business 책임 분리
- [x] TCF ON/OFF
- [x] Current 선후처리와 8-Step 모델 구분

## Transaction / Timeout / Retry
- [x] Worker TX Boundary
- [x] Timeout Hierarchy
- [x] Current 5000/20/100 Snapshot
- [x] cancel(true) 한계
- [x] Late Commit 방지
- [x] Retry/Idempotency

## Data Movement
- [x] Event
- [x] CDC
- [x] ETL
- [x] File
- [x] Batch

## Security
- [x] SSO/JWT
- [x] Key/Secret 분리
- [x] Identity Binding GAP
- [x] Auth/AuthZ 분리
- [x] Direct WAS Bypass

## Error / Logging
- [x] Error Taxonomy
- [x] HTTP/App Code
- [x] Early/Generic Error GAP
- [x] MDC/ImageLog/SQL/Audit 분리
- [x] Sensitive Logging 금지

## Governance
- [x] Inventory Template
- [x] GAP/RISK/ADR
- [x] Completion Gate
- [x] RUNTIME Handoff

**MECHANISM 장 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. 전체 Interface Catalog 승인
2. HTTP/JSON Scope 확정
3. Header/Path ServiceId mismatch 정책 확정
4. Query/TX Timeout 실제값 확보
5. Retry/Idempotency 표준 승인
6. Generic/Early Error 표준 승인
7. JWT Identity Binding / Revocation 확정
8. Event/File/Batch Recovery Contract 승인
9. Sensitive Logging/Masking 표준 승인
10. Runtime Scenario별 실제 Evidence 확보

---

# 111. 다음 장

다음은 **06. RUNTIME — 실제 거래 Sequence / Thread / Transaction / Timeout / Failure / Recovery / Evidence**다.

```text
MECHANISM
"어떤 규칙으로 동작해야 하는가?"

        ↓

RUNTIME
"실제로 어느 순서 / 어느 Thread / 어느 TX에서
어떻게 실행되고 실패하며 복구되는가?"
```
