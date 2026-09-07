# NSIGHT / PDMG 아키텍처 정의서
# 별첨 D. INTERFACE ARCHITECTURE DEFINITION
## Contract / Interaction / Sync-Async / API-Event-CDC-ETL-File / Runtime / Governance
## Visual-First / Purpose-Driven / Contract-First / Standalone Appendix

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-IF-APPENDIX-D`  
> 문서 유형: **별첨 / 독립 Interface Architecture 정의서**  
> 문서 상태: **Draft / Evidence-First / Visual-First / Contract-First**  
> 작성 기준일: **2026-08-31**  
> 연계 장: `02 BIG PICTURE`, `03 LOGICAL`, `05 MECHANISM`, `06 RUNTIME`, `09 OPERATIONS`, `10 BASELINE`, `11 DEVELOPMENT STANDARD`  
> 연계 별첨: `별첨 A Application Architecture`, `별첨 B Technical Architecture`, `별첨 C Infrastructure Architecture`

---

# 0. 이 별첨의 목적

## FIG-IF-01. Interface Architecture가 답해야 하는 질문

```text
Business / System Requirement
          ↓
무엇과 무엇을 연결하는가?
          ↓
왜 연결하는가?
          ↓
즉시 응답이 필요한가?
          ↓
어떤 Interface Type이 맞는가?
          ↓
어떤 Contract / Schema를 사용할 것인가?
          ↓
Timeout / Retry / Idempotency는?
          ↓
Security / Trace / Error는?
          ↓
장애 시 어떻게 복구하는가?
          ↓
변경 / Version / 예외를 어떻게 통제하는가?
```

> **Interface Architecture는 시스템·애플리케이션·데이터 사이의 연결을 목적에 따라 분류하고, Interface Type·Contract·SYNC/ASYNC·Message·Security·Failure/Recovery·Version·Operations 정책을 정의하여 모든 연계가 표준화되고 추적 가능하게 만드는 Architecture 영역이다.**

---

# 1. Interface Architecture 한 문장 정의

## FIG-IF-02. Definition

```text
Business Interaction Need
        ↓
Interface Classification
        ↓
Source / Target Boundary
        ↓
Interaction Contract
        ↓
Runtime Mechanism
        ↓
Failure / Recovery
        ↓
Operations / Evidence
```

---

# 2. Interface Architecture가 아닌 것

## FIG-IF-03. Not Interface Architecture

```text
Interface Architecture
≠ API 목록

Interface Architecture
≠ 전문 목록

Interface Architecture
≠ Endpoint 목록

Interface Architecture
≠ EAI 제품 설명

Interface Architecture
≠ 포트 목록
```

이 항목들은 Interface Architecture의 **Inventory 또는 구현상세**다.

---

# 3. Interface Architecture와 Integration Platform의 차이

## FIG-IF-04. Architecture vs Platform

```text
INTERFACE ARCHITECTURE
│
├─ Purpose
├─ Source / Target
├─ Type
├─ Contract
├─ Sync / Async
├─ Error / Timeout / Retry
├─ Security
├─ Recovery
└─ Version
       ↓ implemented by
INTEGRATION PLATFORM
│
├─ MCA / MCI
├─ API Gateway / APIM
├─ Event Broker
├─ CDC Platform
├─ ETL
├─ MFT / FOS
└─ GSE / Adapter
```

---

# 4. Interface Architecture와 Technical Architecture의 관계

## FIG-IF-05. IF → TA

```text
Interface Architecture
"어떤 계약과 방식으로 연결할 것인가?"
          ↓
Technical Architecture
"그 계약을 어떤 기술 Platform으로 실행할 것인가?"
          ↓
Infrastructure Architecture
"그 Platform을 어느 Host/Network에 배치할 것인가?"
```

---

# 5. Interface Architecture 12대 축

## FIG-IF-06. Twelve Axes

```text
1. Interface Principle
2. Interface Classification
3. Source / Target Boundary
4. Type Selection
5. SYNC / ASYNC
6. Contract / Message / Schema
7. Timeout / Retry / Idempotency
8. Security
9. Runtime / Recovery
10. Observability / SLA
11. Version / Lifecycle
12. Inventory / Governance
```

---

# 6. 최상위 Interface 원칙

## FIG-IF-07. Core Principles

```text
Purpose-Driven Selection
        +
P2P Minimize
        +
Service-to-Service
        +
Contract First
        +
SYNC only when required
        +
Online / Bulk Separation
        +
Failure Isolation
        +
Traceability
        +
Version Governance
        +
Exception by ADR
```

---

# 7. IF-01 — 목적에 따라 방식을 선택한다

## FIG-IF-08. Purpose First

```text
업무 목적
   ↓
Transaction?
Event?
Change Data?
Bulk?
File?
External?
   ↓
Appropriate Interface Type
```

### MUST NOT

```text
모든 연계 = REST
모든 연계 = DB Direct
모든 연계 = File
```

---

# 8. IF-02 — Point-to-Point를 최소화한다

## FIG-IF-09. Standard Integration

```text
System A
   ↓
Standard Integration Layer
   ↓
System B
```

---

# 9. P2P 제한

## FIG-IF-10. P2P Anti-pattern

```text
System A ─────► System B
Direct TCP

System A ─────► System B DB
Direct JDBC

System A DB ──► System B DB
DB-Link
```

### 원칙

P2P는 기본이 아니라 **예외**로 관리한다.

---

# 10. IF-03 — Service-to-Service 원칙

## FIG-IF-11. Service Boundary

```text
Application A
  ↓
Public Service Contract
  ↓
Application B
```

### MUST NOT

```text
Application A
  ↓
Application B DAO / Internal Table
```

---

# 11. IF-04 — SYNC/ASYNC는 업무 응답 필요성으로 결정한다

## FIG-IF-12. Sync Decision

```text
현재 업무 완료에
Target 결과가 반드시 필요한가?
       │
       ├─ YES → SYNC Candidate
       └─ NO  → ASYNC 우선
```

---

# 12. IF-05 — 온라인과 대량처리를 분리한다

## FIG-IF-13. Online vs Bulk

```text
Online
Request / Response
   ↓
API / MCA / MCI

Bulk
Large Data / Batch
   ↓
ETL / File / Batch
```

---

# 13. IF-06 — Contract First

## FIG-IF-14. Contract First

```text
Requirement
  ↓
Interface Contract
  ↓
Review
  ↓
Implementation
  ↓
Test
  ↓
Runtime
```

### MUST NOT

```text
코드 먼저 작성
→ 나중에 Interface Definition 작성
```

---

# 14. IF-07 — 장애전파를 통제한다

## FIG-IF-15. Failure Isolation

```text
Caller
  ↓
Timeout / Isolation
  ↓
Interface
  ↓
Target Failure
```

Target 장애가 Caller Resource를 무한 점유하지 않도록 한다.

---

# 15. IF-08 — 모든 Interface는 추적 가능해야 한다

## FIG-IF-16. Traceability

```text
InterfaceId
  +
GUID / CorrelationId
  +
ServiceId / EventId / JobId
      ↓
End-to-End Trace
```

---

# 16. IF-09 — 변경은 Version으로 관리한다

## FIG-IF-17. Version

```text
Contract v1
  ↓ compatible change
v1.x

Breaking Change
  ↓
Contract v2
```

---

# 17. IF-10 — 예외는 승인대상이다

## FIG-IF-18. Exception

```text
Standard 적용?
  ├─ YES → Standard
  └─ NO
       ↓
    Exception Reason
       ↓
    Impact / Risk
       ↓
    Architecture Review
       ↓
    ADR / Approval
```

---

# 18. Interface Classification

## FIG-IF-19. Classification

```text
Interface
│
├─ Online Transaction
├─ Service API
├─ Event
├─ CDC
├─ ETL / Bulk
├─ File
├─ Batch-triggered
├─ External / Institution
└─ Legacy / Adapter
```

---

# 19. Source / Target Classification

## FIG-IF-20. Source Target

```text
Channel
Core
Information System
Data Platform
BI
External Institution
Other Corporation
Package Solution
```

각 Interface는 Source와 Target을 명시적으로 가진다.

---

# 20. Direction

## FIG-IF-21. Direction

```text
A → B
One-way

A ↔ B
Bi-directional

A → Broker → B/C/D
Fan-out
```

---

# 21. Interface Type Decision Tree

## FIG-IF-22. Master Decision Tree

```text
Interface Requirement
        ↓
즉시 응답 필요?
 ├─ YES
 │    ↓
 │  Transaction / Service?
 │    ↓
 │  API / MCA / MCI / Controlled Online
 │
 └─ NO
      ↓
   Event인가?
 ├─ YES → Event / Kafka
 └─ NO
      ↓
   DB 변경분인가?
 ├─ YES → CDC
 └─ NO
      ↓
   대량 데이터인가?
 ├─ YES → ETL
 └─ NO
      ↓
   File 단위인가?
 ├─ YES → MFT / FOS
 └─ NO → Batch / Special Adapter
```

---

# 22. Online Transaction Interface

## FIG-IF-23. Online

```text
Client
  ↓
Gateway / MCA / MCI / Direct
  ↓
WEB / WAS
  ↓
Standard Entry
  ↓
ServiceId
  ↓
Business Runtime
```

---

# 23. MCA / MCI Interface

## FIG-IF-24. MCA / MCI

```text
Channel
  ↓
MCA / MCI
  │
  ├─ Channel Protocol
  ├─ Routing
  ├─ Header Mapping
  └─ Format Conversion
  ↓
NSIGHT Application
```

---

# 24. API / APIM Interface

## FIG-IF-25. API

```text
Caller
  ↓
API Gateway / APIM
  │
  ├─ Auth
  ├─ Routing
  ├─ Rate Control
  ├─ Policy
  └─ Access Log
  ↓
Business Service
```

---

# 25. Direct Online Interface

## FIG-IF-26. Controlled Direct

```text
Information Terminal / Package
        ↓
Direct HTTP / JSON
        ↓
Standard Header
        ↓
Authentication / Authorization
        ↓
ServiceId
        ↓
Business Runtime
```

### 핵심

```text
Direct
≠ 표준 면제
```

---

# 26. Direct Interface 필수조건

```text
GUID
ServiceId
Authentication
Authorization
Timeout
Error Contract
Logging
Monitoring
```

---

# 27. Event Interface

## FIG-IF-27. Event

```text
Producer
  ↓
Event Broker
  ↓
Topic / Stream
  ↓
Consumer Group
  ↓
Consumer
```

---

# 28. Event 사용조건

```text
즉시 결과 불필요
상태변경 전달
다수 Consumer
후처리
반응형 업무
```

---

# 29. Event Contract

## FIG-IF-28. Event Contract

```text
Event
│
├─ EventType
├─ EventId
├─ Producer
├─ Version
├─ OccurredAt
├─ CorrelationId
├─ Payload
└─ Schema
```

---

# 30. Event Runtime State

## FIG-IF-29. Event Lifecycle

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
```

Failure:

```text
Retry
Replay
DLQ
Manual Recovery
```

---

# 31. Event Idempotency

## FIG-IF-30. Duplicate Event

```text
Event Key
  ↓
Already Processed?
 ├─ YES → Skip / Reconcile
 └─ NO  → Process
```

---

# 32. Pub/Sub Pattern

## FIG-IF-31. Pub/Sub

```text
             ┌─► Consumer A
Producer ─► Broker ─► Consumer B
             └─► Consumer C
```

Producer는 Consumer 개수를 직접 알 필요가 없어야 한다.

---

# 33. Async Request / Reply

## FIG-IF-32. Async Request Reply

```text
Requester
  ↓
Request Queue
  ↓
Responder
  ↓
Reply Queue
  ↓
Requester
```

CorrelationId가 필수다.

---

# 34. CDC Interface

## FIG-IF-33. CDC

```text
Source DB
  ↓
Capture
  ↓
Transport / Relay
  ↓
Apply
  ↓
RDW / Target
```

---

# 35. CDC 사용목적

```text
DB Change Propagation
Near Real-time Data Supply
Operational Data Replication
```

### MUST NOT

```text
CDC = Business API
CDC = Strong Sync Transaction
```

---

# 36. CDC Contract

## FIG-IF-34. CDC Contract

```text
Source
  ↓
Table / Object
  ↓
Change Type
  ↓
Capture Position
  ↓
Target
  ↓
Apply Rule
```

---

# 37. CDC Observability

```text
Capture Lag
Transport Lag
Apply Lag
Error
Restart Position
```

---

# 38. CDC Freshness SLA

## FIG-IF-35. Freshness

```text
Source Commit
  ↓
Capture
  ↓
Transport
  ↓
Apply
  ↓
Consumer Visible
```

기존 `30s vs 3s` 충돌은 측정지점을 고정한 뒤 ADR로 확정한다.

---

# 39. ETL Interface

## FIG-IF-36. ETL

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
Target
```

---

# 40. ETL 사용조건

```text
대량 데이터
정기 적재
집계 / 변환
DW / Mart
```

---

# 41. ETL 금지

## FIG-IF-37. ETL Anti-pattern

```text
Online Request
  ↓
ETL 실행

X
```

```text
Online DB Pool
  ↓
Heavy ETL 공유

X
```

---

# 42. File Interface

## FIG-IF-38. File

```text
Producer
  ↓
Create File
  ↓
MFT / FOS
  ↓
Landing
  ↓
Validate
  ↓
Consumer
```

---

# 43. File Contract

```text
Filename
Charset
Schema
Record Count
Hash
Encryption
Compression
Business Date
```

---

# 44. File Runtime State

## FIG-IF-39. File State

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
```

Failure:

```text
RETRY
QUARANTINE
MANUAL RECOVERY
```

---

# 45. File Reconciliation

## FIG-IF-40. File Reconciliation

```text
Expected Count
  ↓ compare
Received Count
  ↓ compare
Processed Count
  ↓
PASS / GAP
```

---

# 46. GSE / Corporation Interface

## FIG-IF-41. GSE

```text
Other Corporation
  ↓
GSE
  │
  ├─ Corporation Code
  ├─ Authorization
  ├─ Message Version
  └─ Error Ownership
  ↓
NSIGHT
```

GSE는 일반 내부 API와 별도 경계로 관리한다.

---

# 47. External Institution Interface

## FIG-IF-42. External Boundary

```text
NSIGHT
  ↓
API Gateway / External MCA / Dedicated Network
  ↓
External Institution
```

외부기관이 지정한 Protocol은 예외 또는 별도 Standard로 관리한다.

---

# 48. Legacy Interface

## FIG-IF-43. Legacy Adapter

```text
Legacy Protocol
  ↓
Adapter
  ↓
Standard Internal Contract
  ↓
NSIGHT
```

Legacy 기술을 내부 Target Standard로 확산시키지 않는다.

---

# 49. Interface Contract 전체

## FIG-IF-44. Universal Contract

```text
Interface
│
├─ InterfaceId
├─ InterfaceName
├─ Producer
├─ Consumer
├─ Purpose
├─ Type
├─ SyncType
├─ Endpoint / Topic / File
├─ Schema
├─ Header
├─ Security
├─ Timeout
├─ Retry
├─ Idempotency
├─ Error
├─ Recovery
├─ TraceKey
├─ SLA
├─ Owner
└─ Version
```

---

# 50. Interface ID

## FIG-IF-45. Interface Identity

```text
InterfaceId
  ↓
Contract
  ↓
Implementation
  ↓
Monitoring
  ↓
Change History
```

InterfaceId Naming 규칙은 Enterprise Naming Standard에서 확정한다.

---

# 51. ServiceId vs InterfaceId

## FIG-IF-46. Identifier Roles

```text
ServiceId
= Business Transaction Identity

InterfaceId
= System-to-System Contract Identity

GUID
= Runtime Execution Identity
```

---

# 52. Contract와 Endpoint 구분

```text
Interface Contract
      ↓
Endpoint v1
Endpoint v2
```

Endpoint가 바뀌어도 Contract Identity/Version 관리가 가능해야 한다.

---

# 53. Standard Message

## FIG-IF-47. PDMG Online Envelope

```text
Request
{
  hdr_nhnis,
  dto
}

Success
{
  hdr_nhnis,
  dto
}

Known Error
{
  hdr_nhnis,
  result
}
```

### 태그

```text
[PDMG AS-IS REFERENCE]
```

---

# 54. Header Architecture

## FIG-IF-48. Header

```text
hdr_nhnis
  ↓
sys_comm
  ├─ std_gbl_id
  ├─ rms_svc_c
  ├─ tr_sysid
  ├─ tr_trm_ipadr
  ├─ tr_brc
  ├─ scid
  └─ optr_eno
```

---

# 55. Header의 역할

```text
Trace
Service Identity
Source System
Client Context
Operator Context
```

Header에 Business Payload를 무분별하게 넣지 않는다.

---

# 56. GUID

## FIG-IF-49. GUID Propagation

```text
Source
  ↓
std_gbl_id / GUID
  ↓
Integration Layer
  ↓
Target
  ↓
Log / Trace / Error
  ↓
Runtime Evidence
```

---

# 57. CorrelationId

Event/Async에서는:

```text
RequestId
CorrelationId
CausationId
```

를 목적에 따라 구분할 수 있다.

---

# 58. Message Schema

## FIG-IF-50. Schema Governance

```text
Schema
  ↓
Required / Optional
  ↓
Type / Length
  ↓
Semantic
  ↓
Validation
  ↓
Version
```

---

# 59. JSON 표준

신규 Online의 기본 후보:

```text
JSON
UTF-8
```

기존 Legacy Encoding/Format을 신규표준으로 자동 복제하지 않는다.

---

# 60. Charset

## FIG-IF-51. Charset Boundary

```text
Legacy EUC-KR
  ↓
Adapter / Conversion
  ↓
Target UTF-8
```

문자변환은 명시적 Boundary에서 수행한다.

---

# 61. Binary / Image

```text
Large Binary
  ↓
File / Object / Dedicated Transfer
```

대용량 Binary를 Online JSON 본문에 무분별하게 포함하지 않는다.

---

# 62. Message Size

## FIG-IF-52. Payload Decision

```text
Payload Size
  ↓
Small / Online?
  ├─ YES → API / Message
  └─ NO
       ↓
     Bulk?
       ├─ YES → ETL
       └─ File → MFT/FOS
```

---

# 63. Required / Optional

```text
Required
= 없으면 Contract Invalid

Optional
= 없어도 Contract Valid

Conditional
= 특정 조건에서 Required
```

---

# 64. Default Value

Default를 사용할 경우:

```text
Meaning
Version
Source of Default
```

를 명시한다.

---

# 65. Enumerated Code

## FIG-IF-53. Code Contract

```text
Code
  ↓
Allowed Values
  ↓
Meaning
  ↓
Version / Deprecation
```

---

# 66. Date / Time

```text
Timezone
Format
Precision
Business Date vs Timestamp
```

을 명확히 정의한다.

---

# 67. Versioning

## FIG-IF-54. Version Lifecycle

```text
v1
 ↓ compatible extension
v1.1
 ↓ breaking change
v2
```

---

# 68. Breaking Change

```text
Field Remove
Semantic Change
Type Change
Required Rule Change
Authentication Change
```

은 Major Version 후보.

---

# 69. Backward Compatibility

## FIG-IF-55. Compatibility

```text
Producer New
  ↓
Consumer Old
  ↓
Still works?
```

Compatibility Matrix를 관리한다.

---

# 70. Deprecation

```text
Active
  ↓
Deprecated
  ↓
Migration Window
  ↓
Retired
```

---

# 71. SYNC Architecture

## FIG-IF-56. Sync Runtime

```text
Caller
  ↓ Request
Target
  ↓ Process
Caller
  ↑ Response
```

Caller Thread/Resource가 Target 결과를 기다린다.

---

# 72. SYNC 사용조건

```text
Current Transaction Completion에
Target Result가 필수
```

---

# 73. SYNC 위험

## FIG-IF-57. Sync Failure Propagation

```text
Target Slow
  ↓
Caller Wait
  ↓
Thread Occupied
  ↓
Pool Saturation
  ↓
Cascade Failure
```

---

# 74. ASYNC Architecture

## FIG-IF-58. Async

```text
Producer
  ↓
Queue / Event
  ↓
Consumer
```

Producer와 Consumer Runtime을 분리한다.

---

# 75. ASYNC 장점

```text
Failure Isolation
Buffering
Replay
Independent Scaling
Fan-out
```

---

# 76. ASYNC 책임

비동기는 자동으로 안전하지 않다.

```text
Retry
DLQ
Replay
Idempotency
Ordering
Reconciliation
```

가 필요하다.

---

# 77. Timeout Architecture

## FIG-IF-59. Timeout Hierarchy

```text
DB / Target Operation
       <
Target Service Deadline
       <
Gateway / Integration Timeout
       <
Caller Timeout
```

---

# 78. Timeout Budget

## FIG-IF-60. Budget

```text
Caller SLA
  ↓
Network Budget
  ↓
Gateway Budget
  ↓
Target Processing Budget
  ↓
DB / External Budget
```

---

# 79. PDMG Timeout Reference

```text
[AS-IS]

Online Worker Deadline = 5000ms
Worker Pool            = 20
Queue                  = 100
```

이 값은 Interface Enterprise SLA로 자동 승격하지 않는다.

---

# 80. HTTP Timeout 의미

## FIG-IF-61. Timeout Semantics

```text
Caller Timeout
  ↓
Failure Response
```

하지만:

```text
Timeout Response
≠ Target Worker End
≠ JDBC Cancel
≠ DB Rollback Complete
```

---

# 81. Retry Architecture

## FIG-IF-62. Retry Decision

```text
Failure
  ↓
Transient?
 ├─ NO → No Retry
 └─ YES
      ↓
   Idempotent?
 ├─ NO → Compensation / Manual
 └─ YES
      ↓
   Backoff
      ↓
   Max Retry
      ↓
   Final Recovery
```

---

# 82. Retry 금지

## FIG-IF-63. No Retry

```text
Validation Error
Authorization Error
Business Reject
Non-idempotent DML
Duplicate-risk Command
```

Blind Retry 금지.

---

# 83. Idempotency

## FIG-IF-64. Idempotency

```text
Request
  ↓
Idempotency Key
  ↓
Already Processed?
 ├─ YES → Previous Result / Reject
 └─ NO  → Execute
```

---

# 84. Timeout + Retry 중복위험

## FIG-IF-65. Duplicate Risk

```text
Original Request
  ↓
Target still executing
  ↓
Caller Timeout
  ↓
Retry
  ↓
Duplicate Processing
```

---

# 85. Transaction Boundary

## FIG-IF-66. Distributed TX Avoidance

```text
System A TX
  ↓
Remote Interface
  ↓
System B TX
```

### 원칙

분산 XA/2PC에 과도하게 의존하지 않고,
서비스 경계에서 Local TX + Recovery/Compensation을 우선 검토한다.

---

# 86. Long Remote Call in TX

## FIG-IF-67. Long TX Risk

```text
TX BEGIN
  ↓
DB Work
  ↓
Remote Call
  ↓ slow
Lock / Resource Held
  ↓
Timeout
```

---

# 87. Compensation

## FIG-IF-68. Compensation

```text
Step A Commit
  ↓
Step B Fail
  ↓
Compensation A
  ↓
Reconcile
```

---

# 88. Error Taxonomy

## FIG-IF-69. Error Classes

```text
Validation
Authentication
Authorization
Business Reject
Routing
Timeout
Overload
External
DB
System / Unknown
```

---

# 89. HTTP + Application Error

## FIG-IF-70. Error Contract

```text
HTTP Status
= Transport / Runtime

Application Error Code
= Business / Framework Meaning
```

---

# 90. Error Ownership

```text
Source Error
Integration Error
Target Error
External Error
```

를 구분한다.

---

# 91. Retryability를 Error Code에 연결

## FIG-IF-71. Error Policy

```text
Error Code
  ↓
Retryable?
  ↓
Backoff?
  ↓
Recovery?
```

---

# 92. Security Architecture

## FIG-IF-72. Interface Security

```text
Caller Identity
  ↓
Authentication
  ↓
Authorization
  ↓
Transport Protection
  ↓
Message / Data Protection
  ↓
Audit
```

---

# 93. Authentication

```text
JWT / OAuth / API Credential / Certificate / Legacy Mechanism
```

실제 방식은 Interface Type/Trust Boundary별로 결정한다.

---

# 94. Authorization

## FIG-IF-73. Authorization

```text
Authenticated Caller
  ↓
Interface Permission
  ↓
Business Permission
  ↓
Data Permission
```

Authentication과 Authorization을 동일시하지 않는다.

---

# 95. Transport Security

```text
TLS
Mutual TLS [where required]
Dedicated Network
VPN
```

정확한 적용정책은 Security Architecture를 따른다.

---

# 96. Sensitive Data

## FIG-IF-74. Sensitive Data Control

```text
Payload
  ↓
Sensitive?
 ├─ NO → Standard
 └─ YES
      ↓
   Minimize
   Encrypt
   Mask Log
   Access Control
```

---

# 97. Credential / Secret

```text
Token
Password
Private Key
API Secret
```

를 Message/Log에 평문 노출하지 않는다.

---

# 98. Identity Binding

## FIG-IF-75. Identity

```text
Trusted Principal
   ↓
Header / Business User Context
   ↓
Authorization
```

Header User 값만으로 신뢰하지 않는다.

---

# 99. Interface Observability

## FIG-IF-76. Observability

```text
Interface
  ↓
Metric
  +
Log
  +
Trace
  ↓
Dashboard
  ↓
Alert
  ↓
Runbook
```

---

# 100. Interface Metric

```text
Request Count
Success Rate
Error Rate
Latency
Timeout
Retry
DLQ
Lag
Payload Size
```

---

# 101. Interface Trace

## FIG-IF-77. Trace Chain

```text
InterfaceId
  ↓
GUID / CorrelationId
  ↓
Source
  ↓
Integration Layer
  ↓
Target
  ↓
Result
```

---

# 102. Interface Log

```text
InterfaceId
GUID
Source
Target
Version
Status
ErrorCode
Elapsed
```

Sensitive Payload 전체를 기본 Log에 남기지 않는다.

---

# 103. Alert

## FIG-IF-78. Alert Lifecycle

```text
Metric / Log
  ↓
Alert Rule
  ↓
Severity
  ↓
Owner
  ↓
Runbook
  ↓
Recovery
```

---

# 104. Retry / DLQ 운영

```text
Failure
  ↓
Retry
  ↓
Max Retry
  ↓
DLQ / Quarantine
  ↓
Operator
  ↓
Replay
```

---

# 105. Replay

## FIG-IF-79. Replay

```text
Failed Message
  ↓
Root Cause Fixed
  ↓
Replay
  ↓
Idempotency Check
  ↓
Process
  ↓
Reconcile
```

---

# 106. Reconciliation

## FIG-IF-80. Reconciliation

```text
Source Count / State
       ↓ compare
Target Count / State
       ↓
PASS / Difference
       ↓
Recovery
```

---

# 107. SLA

## FIG-IF-81. SLA Dimensions

```text
Availability
Latency
Throughput
Freshness
Delivery Success
Recovery Time
```

---

# 108. Online SLA

```text
TPS
p95 / p99
Timeout Rate
Error Rate
```

---

# 109. Event SLA

```text
Produce Rate
Consumer Lag
Delivery Delay
DLQ Rate
```

---

# 110. CDC SLA

```text
Source-to-Target Freshness
Lag
Apply Error
Recovery Time
```

---

# 111. ETL SLA

```text
Start
End
Duration
Rows
Reject
Completion Deadline
```

---

# 112. File SLA

```text
Expected Time
Transfer Time
File Count
Record Count
Validation
Processing Deadline
```

---

# 113. Capacity Architecture

## FIG-IF-82. Capacity

```text
Business Volume
  ↓
Interface TPS / Message Rate
  ↓
Payload Size
  ↓
Bandwidth / Broker / Thread
  ↓
Target Capacity
```

---

# 114. Payload Capacity

```text
Average Size
Peak Size
Daily Volume
Compression
Retention
```

---

# 115. Event Capacity

```text
Events/sec
Partition
Consumer Count
Lag
Retention
```

---

# 116. File Capacity

```text
Files/day
File Size
Peak Transfer
Storage
Retention
```

---

# 117. Interface Inventory SSOT

## FIG-IF-83. Inventory

```text
Interface Catalog
      ↓
Architecture Model
      ↓
Implementation
      ↓
Monitoring
      ↓
Change / Version
```

---

# 118. Interface Inventory Template

```yaml
interface:
  interfaceId:
  name:
  producer:
  consumer:
  purpose:
  type:
  syncType:
  endpoint:
  schema:
  version:
  security:
  timeout:
  retry:
  idempotency:
  errorPolicy:
  recovery:
  traceKey:
  sla:
  owner:
  evidence:
  status:
```

---

# 119. Service / Interface Mapping

## FIG-IF-84. Service Mapping

```text
ServiceId
  ↓ uses
InterfaceId
  ↓
Target Service
```

---

# 120. Event Inventory

```yaml
event:
  eventType:
  version:
  producer:
  topic:
  consumers:
  schema:
  key:
  retry:
  dlq:
  retention:
  owner:
```

---

# 121. File Inventory

```yaml
fileInterface:
  interfaceId:
  filenamePattern:
  sender:
  receiver:
  charset:
  schema:
  schedule:
  maxSize:
  hash:
  encryption:
  retention:
  owner:
```

---

# 122. CDC Inventory

```yaml
cdc:
  interfaceId:
  sourceDb:
  sourceObject:
  capture:
  relay:
  targetDb:
  apply:
  freshnessSla:
  restartPolicy:
  owner:
```

---

# 123. Timeout Policy Inventory

```yaml
timeout:
  interfaceId:
  caller:
  gateway:
  target:
  db:
  external:
  evidence:
```

---

# 124. Retry Policy Inventory

```yaml
retry:
  interfaceId:
  retryableErrors:
  maxRetry:
  backoff:
  idempotencyKey:
  finalRecovery:
  owner:
```

---

# 125. Version Inventory

```yaml
version:
  interfaceId:
  current:
  previous:
  compatibility:
  deprecatedAt:
  retiredAt:
```

---

# 126. Interface Traceability

## FIG-IF-85. Full Trace

```text
Requirement
  ↓
Application
  ↓
ServiceId
  ↓
InterfaceId
  ↓
Contract Version
  ↓
Implementation
  ↓
Endpoint / Topic / File
  ↓
Runtime
  ↓
GUID / CorrelationId
  ↓
Evidence
```

---

# 127. Reverse Trace

## FIG-IF-86. Reverse

```text
Failed Interface
  ↑
InterfaceId
  ↑
ServiceId
  ↑
Application
  ↑
Requirement
```

---

# 128. Interface Architecture Model

## FIG-IF-87. Model Entities

```text
Interface
Producer
Consumer
Contract
Schema
Version
Endpoint
Topic
File
ServiceId
EventType
TimeoutPolicy
RetryPolicy
SecurityPolicy
RuntimeScenario
Evidence
```

---

# 129. Model Relations

```text
Application
  PRODUCES
Interface

Application
  CONSUMES
Interface

ServiceId
  USES
Interface

Interface
  HAS
Contract

Contract
  HAS_VERSION
Version

RuntimeScenario
  PROVES
Interface
```

---

# 130. Interface Rule Categories

## FIG-IF-88. Rule Set

```text
Type Selection
P2P
Service Boundary
Sync / Async
Contract
Schema
Version
Timeout
Retry
Idempotency
Security
Observability
Recovery
```

---

# 131. Type Selection Rule

```text
R-IF-PURPOSE-TYPE
```

업무 목적과 Interface Type이 일치해야 한다.

---

# 132. P2P Rule

```text
R-IF-P2P-EXCEPTION
```

Direct P2P는 ADR/예외승인 없이는 금지 후보.

---

# 133. Direct DB Rule

```text
R-IF-CROSS-SYSTEM-DML
R-IF-DBLINK-EXCEPTION
```

---

# 134. Sync / Async Rule

```text
R-IF-SYNC-NECESSITY
```

현재 거래 완료에 결과가 필수일 때만 SYNC.

---

# 135. Contract Rule

```text
R-IF-CONTRACT-REQUIRED
R-IF-SCHEMA-VERSION
R-IF-OWNER
```

---

# 136. Timeout Rule

```text
R-IF-TIMEOUT-HIERARCHY
R-IF-TIMEOUT-EVIDENCE
```

---

# 137. Retry Rule

```text
R-IF-RETRYABLE
R-IF-MAX-RETRY
R-IF-BACKOFF
R-IF-IDEMPOTENCY
R-IF-FINAL-RECOVERY
```

---

# 138. Security Rule

```text
R-IF-AUTHENTICATION
R-IF-AUTHORIZATION
R-IF-SENSITIVE-DATA
R-IF-SECRET-LOG
```

---

# 139. Observability Rule

```text
R-IF-TRACE
R-IF-METRIC
R-IF-ALERT
R-IF-RUNBOOK
```

---

# 140. Interface Test Architecture

## FIG-IF-89. Test Stack

```text
Schema Test
  ↓
Contract Test
  ↓
Connectivity
  ↓
Security
  ↓
Timeout
  ↓
Retry / Idempotency
  ↓
Failure / Recovery
  ↓
Performance
  ↓
Runtime Evidence
```

---

# 141. Contract Test

## FIG-IF-90. Contract Test

```text
Producer Payload
  ↓
Schema
  ↓
Consumer Expectation
  ↓
Compatible?
```

---

# 142. Connectivity Test

```text
Source
 ↓
Route / Gateway
 ↓
Endpoint / Topic / File
 ↓
Target
```

---

# 143. Timeout Test

## FIG-IF-91. Timeout Test

```text
Slow Target
  ↓
Timeout
  ↓
Caller Resource Release
  ↓
Target Outcome
  ↓
Evidence
```

---

# 144. Retry Test

```text
Transient Failure
  ↓
Retry
  ↓
Backoff
  ↓
Success / Final Recovery
```

---

# 145. Idempotency Test

## FIG-IF-92. Duplicate Test

```text
Same Request Twice
  ↓
Expected Single Business Effect
  ↓
PASS / FAIL
```

---

# 146. Event Failure Test

```text
Consumer Down
  ↓
Lag
  ↓
Restart
  ↓
Replay
  ↓
Reconcile
```

---

# 147. CDC Failure Test

```text
Relay Down
  ↓
Checkpoint
  ↓
Restart
  ↓
Catch-up
  ↓
Freshness Validate
```

---

# 148. File Failure Test

```text
Transfer Fail
  ↓
Retry
  ↓
Quarantine
  ↓
Manual Recovery
  ↓
Reconcile
```

---

# 149. Performance Test

## FIG-IF-93. Performance

```text
TPS / Event Rate / File Volume
          ↓
Integration Platform
          ↓
Target
          ↓
Latency / Lag / Throughput
```

---

# 150. Interface Change Governance

## FIG-IF-94. Change

```text
Change Request
  ↓
Impact
  ↓
Compatibility
  ↓
Version
  ↓
Test
  ↓
Deploy
  ↓
Runtime Evidence
  ↓
Catalog Update
```

---

# 151. Breaking Change Governance

```text
Breaking Change
  ↓
New Version
  ↓
Parallel Support
  ↓
Consumer Migration
  ↓
Old Version Retirement
```

---

# 152. Interface Exception Governance

## FIG-IF-95. Exception

```text
Standard
  ↓
Exception Needed
  ↓
Reason
  ↓
Business / Technical Impact
  ↓
Security / Operations Risk
  ↓
Architecture Review
  ↓
ADR
  ↓
Expiry / Re-review
```

---

# 153. 예외 후보

```text
Legacy Protocol
External Institution Standard
Dedicated Network
Package Adapter
Direct P2P
Direct DB
DB-Link
```

---

# 154. Interface Anti-pattern

## FIG-IF-96. Anti-pattern Map

```text
All REST
All Sync
Direct DB
DB-Link
Blind Retry
No Timeout
No Idempotency
No Version
No Owner
No Trace
No Recovery
Bulk via Online API
Event treated as Sync
```

---

# 155. Application Architecture와 관계

## FIG-IF-97. AA ↔ IF

```text
Application Architecture
Application / ServiceId
       ↓
Interface Architecture
Public Contract / Interaction
       ↓
Other Application
```

---

# 156. Technical Architecture와 관계

## FIG-IF-98. TA ↔ IF

```text
Interface Contract
      ↓
Technical Platform
MCA / API GW / Broker / CDC / ETL / MFT
```

---

# 157. Infrastructure Architecture와 관계

## FIG-IF-99. INFRA ↔ IF

```text
Interface Runtime
  ↓
Network / Port / LB
  ↓
Platform Host
  ↓
Monitoring
```

---

# 158. Data Architecture와 관계

## FIG-IF-100. Data ↔ IF

```text
Data Ownership / Model
       ↓
Interface Schema / CDC / ETL / File
       ↓
Consumer Data
```

---

# 159. Security Architecture와 관계

## FIG-IF-101. Security ↔ IF

```text
Trust Boundary
  ↓
Authentication
  ↓
Authorization
  ↓
Encryption
  ↓
Audit
```

---

# 160. Operations Architecture와 관계

## FIG-IF-102. OPS ↔ IF

```text
Interface
  ↓
Metric / Log / Trace
  ↓
Alert / Runbook
  ↓
Replay / Reconcile
  ↓
Evidence
```

---

# 161. Development Standard와 관계

## FIG-IF-103. Dev ↔ IF

```text
Interface Architecture
Contract / Policy
       ↓
Development Standard
DTO / Client / Handler / Error / Retry
       ↓
Source / Test
```

---

# 162. 신규 Interface 설계 Route

## FIG-IF-104. New Interface

```text
Business Need
  ↓
Source / Target
  ↓
Purpose
  ↓
Type Selection
  ↓
SYNC / ASYNC
  ↓
Contract / Schema
  ↓
Security
  ↓
Timeout / Retry / Idempotency
  ↓
Error / Recovery
  ↓
SLA / Monitoring
  ↓
Test / Evidence
```

---

# 163. 신규 Interface Checklist

```text
[ ] InterfaceId
[ ] Source / Target
[ ] Purpose
[ ] Type
[ ] Sync / Async
[ ] Contract
[ ] Schema
[ ] Version
[ ] Security
[ ] Timeout
[ ] Retry
[ ] Idempotency
[ ] Error
[ ] Recovery
[ ] Trace
[ ] SLA
[ ] Owner
[ ] Test
```

---

# 164. Online Interface Checklist

```text
[ ] ServiceId
[ ] Endpoint
[ ] Header
[ ] Request/Response
[ ] AuthN/AuthZ
[ ] Timeout
[ ] Error
[ ] p95
[ ] Trace
```

---

# 165. Event Interface Checklist

```text
[ ] EventType
[ ] Topic
[ ] Key
[ ] Version
[ ] Consumer
[ ] Retry
[ ] DLQ
[ ] Replay
[ ] Idempotency
[ ] Lag
```

---

# 166. CDC Checklist

```text
[ ] Source DB/Object
[ ] Target
[ ] Capture
[ ] Apply
[ ] Freshness SLA
[ ] Lag Metric
[ ] Restart
[ ] Reconcile
```

---

# 167. File Checklist

```text
[ ] Filename
[ ] Charset
[ ] Schema
[ ] Count
[ ] Hash
[ ] Encryption
[ ] Schedule
[ ] Transfer
[ ] Archive
[ ] Retry
[ ] Reconcile
```

---

# 168. ETL Checklist

```text
[ ] Source
[ ] Target
[ ] Transform
[ ] Validation
[ ] Restart
[ ] Reject
[ ] Reconcile
[ ] Completion SLA
```

---

# 169. Interface Go-Live Blocker

## FIG-IF-105. Go-Live Block

```text
InterfaceId 없음
      OR
Contract / Version 없음
      OR
Unknown Source / Target
      OR
No Timeout
      OR
Unsafe Retry
      OR
No Idempotency for Retryable Write
      OR
Critical Security GAP
      OR
No Recovery / Reconciliation
      OR
No Monitoring / Trace
      ↓
GO-LIVE BLOCK
```

---

# 170. Interface GAP Register

## TEXT ARCHITECTURE — GAP Lifecycle

```text
Target Interface Standard
      ↓ compare
Actual Interface
      ↓
GAP
 ├─ Severity
 ├─ Owner
 ├─ Action
 ├─ ADR
 └─ Evidence
```

| ID | GAP | 영향 |
|---|---|---|
| GAP-IF-01 | Interface Catalog SSOT 미완료 | Governance |
| GAP-IF-02 | InterfaceId Naming 최종 확정 필요 | Naming |
| GAP-IF-03 | P2P/Direct DB 전수 Inventory 필요 | Boundary |
| GAP-IF-04 | SYNC/ASYNC 전수분류 필요 | Runtime |
| GAP-IF-05 | Contract Version Governance 자동화 미완료 | Change |
| GAP-IF-06 | Timeout 전수 Baseline 미완료 | Reliability |
| GAP-IF-07 | Retry/Idempotency 전수정의 미완료 | Recovery |
| GAP-IF-08 | Generic Error Contract 통합 필요 | Error |
| GAP-IF-09 | Event Schema Registry 체계 확정 필요 | Event |
| GAP-IF-10 | CDC Freshness SLA Conflict | CDC |
| GAP-IF-11 | File Reconciliation 표준화 필요 | File |
| GAP-IF-12 | Interface Security Mapping 전수화 필요 | Security |
| GAP-IF-13 | Interface Observability 자동화 미완료 | Operations |
| GAP-IF-14 | Runtime Evidence 연계 미완료 | Governance |

---

# 171. Interface Risk Register

## TEXT ARCHITECTURE — Risk Lifecycle

```text
Interface Weakness
  ↓
Failure / Duplicate / Delay / Data Loss
  ↓
Business Impact
  ↓
Severity
  ↓
Mitigation
  ↓
Recovery / Evidence
```

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-IF-01 | Point-to-Point 확산 | High |
| RISK-IF-02 | Cross-System Direct DB DML | Critical |
| RISK-IF-03 | SYNC Cascade Failure | Critical |
| RISK-IF-04 | No Timeout | Critical |
| RISK-IF-05 | Blind Retry Duplicate | Critical |
| RISK-IF-06 | Version Breaking Change | High |
| RISK-IF-07 | Event Duplicate / Ordering | High |
| RISK-IF-08 | CDC Lag 미탐지 | High |
| RISK-IF-09 | File 누락/중복 | High |
| RISK-IF-10 | Secret / Sensitive Payload 노출 | Critical |
| RISK-IF-11 | Trace 불가 | High |
| RISK-IF-12 | Recovery 없는 Async | Critical |

---

# 172. Interface ADR 후보

## FIG-IF-106. ADR Areas

```text
Interface Decision
│
├─ Type
├─ Sync / Async
├─ P2P Exception
├─ DB Direct Exception
├─ Contract / Version
├─ Timeout
├─ Retry / Idempotency
├─ Event Schema
├─ CDC SLA
├─ Security
├─ Recovery
└─ Observability
```

대표 ADR:

```text
ADR-IF-01 Interface Classification
ADR-IF-02 InterfaceId Standard
ADR-IF-03 P2P Exception
ADR-IF-04 Direct DB Policy
ADR-IF-05 Sync/Async Decision
ADR-IF-06 Contract Versioning
ADR-IF-07 Timeout Budget
ADR-IF-08 Retry / Idempotency
ADR-IF-09 Event Schema Governance
ADR-IF-10 CDC Freshness SLA
ADR-IF-11 File Reconciliation
ADR-IF-12 Interface Security
ADR-IF-13 Observability / Evidence
```

---

# 173. Interface Review Gate

## FIG-IF-107. Review Gate

```text
Purpose?
  ↓
Source / Target?
  ↓
Type?
  ↓
SYNC / ASYNC?
  ↓
Contract / Version?
  ↓
Security?
  ↓
Timeout / Retry?
  ↓
Recovery?
  ↓
SLA / Monitoring?
  ↓
Evidence?
  ↓
PASS / GAP
```

---

# 174. Interface Architecture Completion Gate

## FIG-IF-108. Completion Gate

```text
Principles Defined?
   ↓ YES
Classification Defined?
   ↓ YES
Type Selection Defined?
   ↓ YES
Contracts Defined?
   ↓ YES
SYNC/ASYNC Defined?
   ↓ YES
Timeout/Retry/Idempotency Defined?
   ↓ YES
Security Defined?
   ↓ YES
Recovery Defined?
   ↓ YES
SLA/Monitoring Defined?
   ↓ YES
Inventory/Evidence Defined?
   ↓ YES
INTERFACE ARCHITECTURE PASS
```

---

# 175. 최종 Interface Architecture 지도

## FIG-IF-109. Final Interface Map

```text
BUSINESS / SYSTEM NEED
        ↓
────────────────────────────────────
INTERFACE PRINCIPLE
Purpose / P2P / Service Boundary
        ↓
────────────────────────────────────
CLASSIFICATION
Online / API / Event / CDC / ETL / File / External
        ↓
────────────────────────────────────
CONTRACT
InterfaceId / Schema / Header / Version
        ↓
────────────────────────────────────
RUNTIME POLICY
SYNC / ASYNC
Timeout / Retry / Idempotency
Error / Transaction / Compensation
        ↓
────────────────────────────────────
SECURITY
AuthN / AuthZ / Encryption / Audit
        ↓
────────────────────────────────────
OPERATIONS
Metric / Log / Trace / Alert
Replay / Reconciliation
        ↓
────────────────────────────────────
GOVERNANCE
Catalog / Rule / Test / ADR / Evidence
```

---

# 176. Definition of Done

## TEXT ARCHITECTURE — Interface Architecture DoD

```text
Purpose / Boundary
  ↓
Type / Sync
  ↓
Contract / Version
  ↓
Runtime Policy
  ↓
Security / Recovery
  ↓
Operations / SLA
  ↓
Inventory / Evidence
  ↓
INTERFACE ARCHITECTURE DoD
```

## Principle / Classification
- [x] Purpose-driven
- [x] P2P 최소화
- [x] Service-to-Service
- [x] Online/Bulk 분리
- [x] Interface Type Decision Tree

## Interface Types
- [x] Online / MCA / MCI / API
- [x] Direct
- [x] Event
- [x] CDC
- [x] ETL
- [x] File
- [x] GSE / External / Legacy

## Contract
- [x] InterfaceId
- [x] Header / GUID
- [x] Message / Schema
- [x] Charset / Size
- [x] Version / Compatibility

## Runtime
- [x] SYNC / ASYNC
- [x] Timeout
- [x] Retry
- [x] Idempotency
- [x] Transaction / Compensation
- [x] Error

## Security / Operations
- [x] Authentication
- [x] Authorization
- [x] Sensitive Data
- [x] Metric / Log / Trace
- [x] Replay / Reconciliation
- [x] SLA / Capacity

## Governance
- [x] Inventory
- [x] Traceability
- [x] Rule / Test
- [x] Exception / ADR
- [x] GAP / Risk
- [x] Go-Live Gate

**INTERFACE ARCHITECTURE 별첨 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. Interface Catalog SSOT 확정
2. InterfaceId Naming Standard 승인
3. P2P/Direct DB 전수 Inventory
4. SYNC/ASYNC 전수분류
5. Contract Version Governance 운영
6. Interface별 Timeout 실제값 확정
7. Retry/Idempotency 전수 Policy
8. Error Contract 통합
9. Event Schema Registry 운영
10. CDC Freshness SLA 확정
11. File Reconciliation 표준 승인
12. Interface Security Mapping
13. Observability Coverage
14. Runtime Evidence 자동화
15. Critical Interface Drift 0

---

# 177. 장 최종 결론

## FIG-IF-110. Interface Architecture Final

```text
Business Interaction Need
          ↓
Interface Type
          ↓
Contract / Version
          ↓
SYNC / ASYNC
          ↓
Timeout / Retry / Idempotency
          ↓
Security / Error / Recovery
          ↓
Monitoring / Trace / SLA
          ↓
Catalog / Evidence / Governance
          ↓
Interface Architecture Baseline
```

> **Interface Architecture의 본질은 “무엇으로 연결할 것인가”보다 “어떤 업무 목적과 계약으로 연결하고, 장애·중복·변경·운영을 어떻게 통제할 것인가”를 정의하는 데 있다.**

> **모든 연계를 REST나 EAI 하나로 통일하는 것이 표준화가 아니라, Transaction·Event·CDC·ETL·File 등 업무 특성에 맞는 Interface Type을 선택하고 공통 Contract·Security·Trace·Recovery 규칙을 강제하는 것이 표준화다.**

> **NSIGHT Interface Architecture는 InterfaceId → Contract → Runtime → GUID/Correlation → Recovery → Evidence가 끊기지 않아야 하며, P2P·Direct DB·Blind Retry·No Timeout·No Version은 원칙적으로 제한 또는 금지한다.**
