# NSIGHT / PDMG 아키텍처 정의서
# 별첨 A. APPLICATION ARCHITECTURE DEFINITION
## Application Classification / Boundary / Layer / Component / Runtime / Traceability
## Visual-First / Target + PDMG Reference / Standalone Appendix

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-APP-APPENDIX-A`  
> 문서 유형: **별첨 / 독립 Application Architecture 정의서**  
> 문서 상태: **Draft / Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 연계 장: `02 BIG PICTURE`, `03 LOGICAL`, `05 MECHANISM`, `06 RUNTIME`, `08 PDMG SOURCE REFERENCE`, `10 BASELINE`, `11 DEVELOPMENT STANDARD`

---

# 0. 이 별첨의 목적

Application Architecture는 단순히 다음을 나열하는 문서가 아니다.

```text
UI
Controller
Service
DAO
```

Application Architecture는 다음 질문에 답한다.

## FIG-APP-01. Application Architecture의 질문

```text
어떤 Application이 존재하는가?
        ↓
각 Application은 무엇을 책임하는가?
        ↓
어디까지가 Application Boundary인가?
        ↓
Application 내부는 어떤 Layer/Component로 구성되는가?
        ↓
Application 간에는 어떻게 연결되는가?
        ↓
Business Transaction은 어떻게 식별되는가?
        ↓
Framework/Common과 Business는 어떻게 분리되는가?
        ↓
Runtime / Process / Deployment와 어떻게 연결되는가?
        ↓
Source와 Runtime Evidence로 어떻게 검증되는가?
```

---

# 1. Application Architecture 한 문장 정의

## FIG-APP-02. Definition

```text
Business Responsibility
        ↓
Application Boundary
        ↓
Application Component / Layer
        ↓
Interaction Contract
        ↓
Runtime Execution
        ↓
Deployment / Evidence
```

> **Application Architecture는 비즈니스 책임을 실행 가능한 Application Boundary로 분리하고, 각 Application 내부의 Layer·Component·Dependency와 Application 간 Interaction Contract를 정의하여, Source·Runtime·Deployment까지 추적 가능하게 만드는 Architecture 영역이다.**

---

# 2. Application Architecture가 아닌 것

## FIG-APP-03. Not Application Architecture

```text
Application Architecture
≠ 화면 목록

Application Architecture
≠ Java Package 목록

Application Architecture
≠ 서버 목록

Application Architecture
≠ API 목록

Application Architecture
≠ Source Tree
```

이들은 각각 Application Architecture의 **증거 또는 하위 표현**일 수 있지만 그 자체가 전체 정의는 아니다.

---

# 3. NSIGHT 전체 Architecture에서의 위치

## FIG-APP-04. Architecture Position

```text
VISION
  ↓
BIG PICTURE
  ↓
┌─────────────────────────────┐
│ APPLICATION ARCHITECTURE    │
│                             │
│ Application Boundary        │
│ Layer / Component           │
│ Business Responsibility     │
│ Interaction Contract        │
└──────────────┬──────────────┘
               ↓
LOGICAL / PHYSICAL
               ↓
MECHANISM
               ↓
RUNTIME
               ↓
SOURCE / EVIDENCE
```

---

# 4. Application Architecture의 7개 축

## FIG-APP-05. Seven Axes

```text
1. Classification
      ↓
2. Responsibility
      ↓
3. Boundary
      ↓
4. Layer / Component
      ↓
5. Interaction
      ↓
6. Runtime / Deployment
      ↓
7. Traceability / Governance
```

---

# 5. Application Classification

NSIGHT의 Service Domain과 Application Group은 동일 개념이 아니다.

## FIG-APP-06. Domain → Application Group

```text
Service Domain
│
├─ Marketing Platform
│    └─ MP
│
├─ Data Platform
│    ├─ RD
│    └─ AD
│
├─ BI Portal
│    └─ BI
│
├─ Data Governance
│    └─ DG
│
└─ IT Service & Business Support
     └─ IM
```

### 핵심

```text
5대 Service Domain
≠
6개 Application Group
```

Data Platform은 Application 관점에서 RD/AD로 분리된다.

---

# 6. Marketing Application Classification

## FIG-APP-07. Marketing Business Applications

```text
MP / Marketing Platform
│
├─ CO  공통
├─ IC  통합고객
├─ PC  개인고객
├─ BC  기업고객
├─ MS  미니 싱글뷰
├─ SA  상담판매
├─ PD  통합상품
├─ CM  캠페인
├─ EB  EBM
├─ EP  실시간 처리
├─ BP  행동정보 처리
├─ BD  고객 행동 데이터
├─ SS  영업지원
├─ CS  CS
├─ CT  컨텐츠
└─ MG  메시지
```

이 분류는 **업무 책임축**이며, 실제 Module/Process/Host 수와 동일하지 않다.

---

# 7. Application Responsibility Model

## FIG-APP-08. Responsibility Chain

```text
Service Domain
   ↓
Application Group
   ↓
Business Area
   ↓
Application
   ↓
Program
   ↓
ServiceId
```

### 원칙

Application은 **“무엇을 책임하는가”**가 먼저 정의되어야 한다.

---

# 8. Application Boundary

## FIG-APP-09. Boundary

```text
Outside
  │
  ▼
Application Entry
  │
  ▼
Application Boundary
  │
  ├─ Business Logic
  ├─ Data Access
  ├─ Common Framework Usage
  └─ Outbound Contract
  │
  ▼
Outside
```

---

# 9. Boundary의 4가지 질문

```text
1. 누가 이 Application을 호출하는가?
2. 이 Application은 어떤 Business Responsibility를 소유하는가?
3. 어떤 Data를 소유하거나 사용하도록 승인받았는가?
4. 다른 Application과 어떤 Contract로 연결되는가?
```

---

# 10. Application Boundary와 Data Boundary

## FIG-APP-10. Application / Data

```text
Application A
   │
   ├─ Own Data ─────────► Direct Access O
   │
   └─ Other App Data
          ↓
     Approved Interface /
     Approved Data Contract

Application A
   ───────────────X────► Application B DB Direct DML
```

---

# 11. Application Boundary와 Integration Boundary

## FIG-APP-11. Inter-Application Connection

```text
Application A
   ↓
Public Contract
API / Event / CDC / ETL / File
   ↓
Application B
```

### 금지

```text
Application A Controller
        ↓
Application B DAO

X
```

---

# 12. Application Boundary와 Module Boundary

## FIG-APP-12. Module ≠ Application

```text
Application
   │
   ├─ Build Module 1
   ├─ Build Module 2
   └─ Shared Framework Module

또는

Build Module
   │
   └─ 여러 Logical Responsibility 포함 가능
```

### 핵심

```text
Application Boundary
≠
Gradle Module Boundary
```

---

# 13. Module / Process / Spring Context / Logical Node 구분

## FIG-APP-13. Four Boundaries

```text
Build Module
     │
     │ ≠
     ▼
Runtime Process
     │
     │ ≠
     ▼
Spring ApplicationContext
     │
     │ ≠
     ▼
Logical Node
```

이 네 가지를 동일한 “시스템”으로 표현하지 않는다.

---

# 14. PDMG 5-Module Reference

## FIG-APP-14. PDMG Module Reference

```text
PDMG
│
├─ pdmg-ui
│    └─ UI / Browser Entry
│
├─ pdmg-jwt
│    └─ Authentication / Token
│
├─ pdmg-fw
│    └─ Framework / Runtime Control
│
├─ pdmg-service
│    └─ Business Application
│
└─ pdmg-om
     └─ Operations Module [actual implementation UNKNOWN]
```

### 상태

```text
[AS-IS Reference]
```

PDMG Module 구조를 NSIGHT 전체 Target Application 구조로 자동 승격하지 않는다.

---

# 15. PDMG Process / Module Reference

## FIG-APP-15. PDMG Runtime Interpretation

```text
Browser
  ├──────── HTTP ───────► pdmg-ui
  ├──────── HTTP ───────► pdmg-jwt
  └──────── HTTP ───────► pdmg-service
                               │
                               └─ pdmg-fw Beans
                                  in same Spring Context possible
```

### 핵심

```text
pdmg-fw
= 별도 Build Module

pdmg-fw
≠ 별도 Remote Business Server
```

---

# 16. Application Layer Model

## FIG-APP-16. Standard Application Layers

```text
Client / Channel
      ↓
Entry Layer
Controller / Handler
      ↓
Application Layer
Facade
      ↓
Business Layer
Service / Rule
      ↓
Data Access Layer
DAO / Mapper
      ↓
Data
DB / External
```

---

# 17. Layer와 Component 구분

## FIG-APP-17. Layer vs Component

```text
Layer
= 책임의 논리적 구분

Component
= 실제 구현단위
```

예:

```text
Entry Layer
├─ Controller
└─ Handler

Business Layer
├─ Facade
├─ Service
└─ Rule
```

---

# 18. Entry Layer

## FIG-APP-18. Entry Responsibilities

```text
Request / Event / Batch
        ↓
Entry Adapter
        │
        ├─ Mapping
        ├─ Input Binding
        ├─ Service Identification
        └─ Business Boundary Call
        ↓
Application Core
```

---

# 19. Controller

## FIG-APP-19. Controller

```text
HTTP
 ↓
Controller
 │
 ├─ URL Mapping
 ├─ DTO Binding
 ├─ Validation Trigger
 └─ Facade Call
 ↓
Result
```

### MUST NOT

```text
Controller → DAO
Controller → Mapper
Controller → SQL
```

---

# 20. Handler

## FIG-APP-20. Handler

```text
ServiceId
   ↓
Handler
   │
   ├─ Register ServiceId
   ├─ Branch
   └─ Facade Call
```

Handler는 **Thin Inbound Adapter**다.

---

# 21. Handler와 Controller의 차이

## FIG-APP-21. Two Entry Adapters

```text
TCF ON
Request
  ↓
Common Controller
  ↓
Handler
  ↓
Facade

TCF OFF
Request
  ↓
Business Controller
  ↓
Facade / Service [current drift exists]
```

둘은 Entry 방식이 다르지만 Business Core는 가능한 한 공유해야 한다.

---

# 22. Facade

## FIG-APP-22. Facade

```text
Entry Adapter
   ↓
Facade
   │
   ├─ Use Case Boundary
   ├─ Multi-Service Coordination
   ├─ DTO Coordination
   └─ Transaction Annotation Candidate
   ↓
Service
```

---

# 23. Service

## FIG-APP-23. Service

```text
Facade
  ↓
Service
  │
  ├─ Business Procedure
  ├─ Business Validation
  ├─ Business Decision
  └─ DAO Coordination
```

---

# 24. Rule

## FIG-APP-24. Rule Layer

```text
Service
  ↓
Rule
  │
  └─ Reusable / Complex Business Decision
  ↓
DAO
```

### 상태

```text
[TO-BE Candidate]
```

Current PDMG 대표 Source에서 일반 Rule Layer가 전수 AS-IS로 확인되는 것은 아니다.

---

# 25. DAO

## FIG-APP-25. DAO

```text
Service
  ↓
DAO
  ↓
MyBatis
  ↓
Mapper XML
  ↓
SQL
```

DAO는 Data Access Contract를 소유한다.

---

# 26. Mapper

## FIG-APP-26. Mapper Contract

```text
Java DAO FQCN
       │
       │ exact
       ▼
Mapper Namespace
       ↓
SqlId
       ↓
SQL
```

---

# 27. PDMG Business Package Reference

## FIG-APP-27. Package Map

```text
nhnis.mg.co.a
│
├─ entry
│   └─ handler
│
├─ application
│   ├─ controller
│   ├─ facade
│   └─ service
│
├─ dto
│
└─ persistence
    └─ dao
```

### [AS-IS]

대표 PDMG Business Source에서 확인된 구조다.

---

# 28. Business Classification → Package

## FIG-APP-28. Classification Projection

```text
MG / CO / A
   │
   ├────────► Java Package
   │          nhnis.mg.co.a
   │
   ├────────► Mapper Resource
   │          rdw.mg.co.a
   │
   └────────► Service Prefix
              mgcoa
```

---

# 29. Program ID

## FIG-APP-29. Program

```text
mg | co | a | 9001
│    │    │     │
│    │    │     └─ Program Number
│    │    └─────── Function
│    └──────────── Business
└───────────────── Major/Application
```

---

# 30. ServiceId

## FIG-APP-30. ServiceId

```text
mg | co | a | 9001 | S | 0
│    │    │    │      │   │
│    │    │    │      │   └─ Sequence
│    │    │    │      └──── Transaction Type
│    │    │    └─────────── Program
│    │    └──────────────── Function
│    └───────────────────── Business
└────────────────────────── Major/Application
```

---

# 31. ServiceId의 Application Architecture 의미

## FIG-APP-31. ServiceId Spine

```text
Application
   ↓
Program
   ↓
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
SQL
```

ServiceId는 단순 URL 이름이 아니라 **Business Transaction 식별축**이다.

---

# 32. Current PDMG Handler Registry

## FIG-APP-32. Current Registry

```text
mgcoa5530S0

mgcoa8888S0
mgcoa8888D0

mgcoa9000S0
mgcoa9000C0
mgcoa9000U0
mgcoa9000D0

mgcoa9001S0
mgcoa9001C0
mgcoa9001U0
mgcoa9001D0

mgcoa9100S0
mgcoa9999S0
```

Current Source 분석 기준:

```text
13 ServiceIds
```

---

# 33. ServiceId Registry Rule

## FIG-APP-33. Registry Rule

```text
Handler Beans
  ↓
serviceIds()
  ↓
Registry
  ├─ unique → register
  └─ duplicate → startup fail
```

---

# 34. ServiceId SSOT

## FIG-APP-34. Single Source of Truth

```text
Architecture Model
      ↓
ServiceId Registry
      ├─ Backend
      ├─ UI Catalog
      ├─ Test
      ├─ Monitoring
      └─ Documentation
```

### [GAP]

Current UI Catalog와 Backend Registry의 자동 정합검증은 추가 필요하다.

---

# 35. PDMG Source Trace

## FIG-APP-35. Program Source Trace

```text
mgcoa9000
   ↓
mgcoa9000Handler
   ↓
mgcoa9000Facade
   ↓
mgcoa9000Service
   ↓
mgcoa9000DAO
   ↓
mgcoa9000-ORA.xml
   ↓
SQL
```

---

# 36. Framework vs Business

## FIG-APP-36. Responsibility Boundary

```text
FRAMEWORK
│
├─ Filter
├─ Context
├─ TCF
├─ Timeout
├─ Transaction Coordination
├─ Error / Response Support
└─ Logging Support

BUSINESS
│
├─ Handler
├─ Facade
├─ Service
├─ Rule
├─ DAO
└─ Mapper / SQL
```

---

# 37. Framework가 소유하면 안 되는 것

```text
고객별 Business Rule
상품별 계산
캠페인 조건
업무 SQL 조건
```

---

# 38. Business가 소유하면 안 되는 것

```text
ThreadLocal Lifecycle
Common Timeout Executor
Global Error Envelope
JWT Signature Verification Infrastructure
Common Dispatcher
```

---

# 39. PDMG Framework Runtime

## FIG-APP-37. Framework Runtime

```text
HTTP Request
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
```

---

# 40. Business Runtime

## FIG-APP-38. Business Runtime

```text
Handler
  ↓
Facade
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

# 41. TCF ON Architecture

## FIG-APP-39. TCF ON

```text
HTTP
 ↓
Common Online Controller
 ↓
TcfFacade
 ↓
Timeout / Transaction
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

---

# 42. TCF OFF Architecture

## FIG-APP-40. TCF OFF

```text
HTTP
 ↓
Business Controller
 ↓
Facade / Service
 ↓
DAO
```

### [GAP]

Current 일부 OFF Controller가 Service를 직접 호출하여 ON/OFF Business Boundary가 다를 수 있다.

---

# 43. ON/OFF Target Candidate

## FIG-APP-41. Same Business Core

```text
TCF ON
Handler ───────┐
               │
               ▼
             Facade
               ↓
             Service
               ↓
              DAO
               ▲
               │
TCF OFF        │
Controller ────┘
```

---

# 44. UI Application Architecture

## FIG-APP-42. pdmg-ui

```text
Browser
  ↓
pdmg-ui
  │
  ├─ Static UI
  ├─ Program / Transaction Selection
  ├─ ServiceId
  ├─ Request DTO
  └─ Authorization Header
  ↓
pdmg-service / pdmg-jwt
```

---

# 45. Direct vs Relay UI

## FIG-APP-43. UI Call Paths

```text
Browser
  ├─ Direct ─────────────► pdmg-service
  │
  └─ Relay
       ↓
     pdmg-ui
       ↓
     /api/relay/{serviceId}
       ↓
     pdmg-service
```

Relay 존재가 유일한 호출모델이라는 뜻은 아니다.

---

# 46. JWT Application Architecture

## FIG-APP-44. pdmg-jwt

```text
Login / SSO
   ↓
pdmg-jwt
   │
   ├─ Handler
   ├─ Facade
   ├─ Service
   ├─ DAO
   ├─ Token Issuer
   └─ JWKS
   ↓
Access / Refresh Token
```

---

# 47. JWT Security Boundary

## FIG-APP-45. Authentication Boundary

```text
Credential / SSO Evidence
      ↓
Authentication
      ↓
Trusted Principal
      ↓
Token
      ↓
Business Authorization
```

---

# 48. JWT AS-IS Critical Gap

Current 분석:

```text
pdmg-jwt
RS256 Issue
      │
      ▼
pdmg-fw
HMAC jwt.secret Verify
```

## FIG-APP-46. Issuer / Verifier Gap

```text
Issuer
RS256
  │
  │ incompatible?
  ▼
Verifier
HMAC
```

### 판정

```text
[CRITICAL GAP]
```

Target 승인 전 Algorithm/Key 정합성을 해결해야 한다.

---

# 49. Identity Binding

## FIG-APP-47. Principal Binding

```text
JWT Subject / ssoId
       ↓
Trusted Principal
       ↓
Business User Context
       ↓
hdr_nhnis.optr_eno
```

### [SECURITY GAP]

Current 자동 Binding 정합성은 추가 검증이 필요하다.

---

# 50. OM Application Architecture

## FIG-APP-48. pdmg-om Boundary

```text
pdmg-om
│
├─ Source ?
├─ Runtime Process ?
├─ Dashboard ?
├─ Metrics ?
├─ Control API ?
└─ Deployment ?
```

### 상태

```text
[UNKNOWN]
```

실제 Source/Runtime Evidence 없이 Target OM 기능을 AS-IS로 채우지 않는다.

---

# 51. DTO Architecture

## FIG-APP-49. DTO Flow

```text
Transport Message
   ↓
DTO In
   ↓
Facade / Service
   ↓
Business Result
   ↓
DTO Out
```

---

# 52. Standard Message

## FIG-APP-50. Online Message

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

### [AS-IS]

Current PDMG Standard Envelope.

---

# 53. Header / Context

## FIG-APP-51. Header to Context

```text
hdr_nhnis
   ↓
DefaultFilter
   ↓
ServiceContext
   ↓
Interceptor
   ↓
Controller / TCF
   ↓
Worker
```

---

# 54. GUID

## FIG-APP-52. GUID Trace

```text
std_gbl_id
   ↓
ServiceContext.guid
   ↓
MDC
   ↓
Worker
   ↓
Business / SQL
   ↓
ImageLog
   ↓
Runtime Evidence
```

---

# 55. ServiceContext

## FIG-APP-53. Context Model

```text
ServiceContext
│
├─ guid
├─ header
├─ userContext
├─ request metadata
├─ requestBody
└─ responseBody
```

### 주의

```text
ServiceContext
≠ DB Transaction
≠ JDBC Connection
≠ Business DTO
```

---

# 56. Worker Context Risk

## FIG-APP-54. Mutable Context

```text
Request Thread
      │
      └──── same mutable context ────┐
                                     ▼
                                Worker Thread
```

### [RISK]

Immutable Snapshot 전환이 TO-BE 후보다.

---

# 57. Transaction Architecture

## FIG-APP-55. TX Boundary

```text
Worker
  ↓
TransactionTemplate BEGIN
  ↓
Dispatcher
  ↓
Handler
  ↓
Facade REQUIRED
  ↓
Service
  ↓
DAO / SQL
  ↓
Deadline Check
  ↓
COMMIT / ROLLBACK
```

### [AS-IS]

TCF ON + Timeout ON Current Reference.

---

# 58. Transaction Owner

## FIG-APP-56. Actual TX

```text
@Transactional Annotation
       ↓
Runtime Call Path
       ↓
TransactionManager
       ↓
Actual BEGIN / COMMIT / ROLLBACK
```

Annotation 위치와 Physical TX 시작점을 동일시하지 않는다.

---

# 59. Timeout Architecture

## FIG-APP-57. Timeout Hierarchy

```text
DB Query Timeout
      <
TX / Worker Deadline
      <
Server / Downstream Timeout
      <
Client Timeout
```

---

# 60. PDMG Timeout Reference

```text
[AS-IS SNAPSHOT]

Worker Deadline = 5000ms
Worker Pool     = 20
Queue Capacity  = 100
```

이 값은 NSIGHT Target SLA로 자동 승격하지 않는다.

---

# 61. Request Thread vs Worker Thread

## FIG-APP-58. Two Lifecycles

```text
Request Thread
 Filter / MVC / TcfFacade
       │
       │ submit
       ▼
Worker Thread
 TX / Handler / Business / DB
```

---

# 62. Timeout Semantics

## FIG-APP-59. 504 Meaning

```text
Future.get(timeout)
       ↓
Timeout
       ↓
HTTP 504
```

하지만:

```text
HTTP 504
≠ Worker 종료
≠ JDBC Cancel
≠ DB Rollback 완료
```

---

# 63. Data Access Architecture

## FIG-APP-60. Data Access

```text
Service
  ↓
DAO
  ↓
Mapper
  ↓
SqlId
  ↓
SQL
  ↓
Owned / Approved Data
```

---

# 64. SQL Trace

## FIG-APP-61. SQL to Data

```text
ServiceId
  ↓
DAO Method
  ↓
SqlId
  ↓
SQL
  ↓
Table / View
```

Critical Service는 이 Trace를 확보해야 한다.

---

# 65. RDW / ADW Application Data Role

## FIG-APP-62. Data Role

```text
Operational / Near Real-time Application
        ↓
       RDW

Analytical / BI Application
        ↓
       ADW
```

Application Architecture는 Data Platform 역할을 침범하지 않고 **사용 Contract**를 정의한다.

---

# 66. Application Interface Architecture

## FIG-APP-63. Purpose-based Integration

```text
Online Transaction → API / MCA / MCI
Event              → Kafka
Change Data        → CDC
Bulk Data          → ETL
File               → FOS / MFT
```

---

# 67. Cross-Application Call

## FIG-APP-64. Call Boundary

```text
Same Application
Controller / Handler
      ↓
Facade / Service

Other Application
      ↓
Public API / Event / Interface Contract
```

---

# 68. Interface Contract

## FIG-APP-65. Contract

```text
Interface
│
├─ InterfaceId
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
└─ Trace
```

---

# 69. Application Security Architecture

## FIG-APP-66. Security Layers

```text
Request
  ↓
Authentication
  ↓
Principal
  ↓
Identity Binding
  ↓
Application Authorization
  ↓
Business Authorization
  ↓
Data Authorization
```

---

# 70. Direct WAS Access

## FIG-APP-67. Security Boundary

```text
Approved Entry
  ↓
Security / Gateway / WEB
  ↓
Application

Direct Internal WAS Access
  ↓
Same Security Enforcement?
```

Gateway 우회 경로에서도 동등한 인증/인가 통제가 필요하다.

---

# 71. Error Architecture

## FIG-APP-68. Error Taxonomy

```text
Validation
Authentication
Authorization
Business
Routing
Timeout
Overload
DB
External
Unknown
```

---

# 72. Current PDMG Error Mapping

```text
ServiceHandlerNotFound
→ E9999 / SERVICE / HTTP 500

BizException
→ Business Code / BIZ / HTTP 500

OnlineTimeoutException
→ FW_TIMEOUT / COMMON / HTTP 504

OnlineOverloadException
→ FW_OVERLOADED / COMMON / HTTP 503
```

### [AS-IS]

Enterprise Error Standard 확정 전 Reference로만 사용한다.

---

# 73. Logging Architecture

## FIG-APP-69. Application Observability

```text
Application Runtime
│
├─ Access Log
├─ Application Log
├─ Transaction Log
├─ SQL Log
├─ Security Audit
└─ ImageLog
```

---

# 74. Correlation

## FIG-APP-70. Correlation Keys

```text
GUID
ServiceId
User
Host
JVM
Thread
SqlId
ErrorCode
DeploymentId
```

---

# 75. ImageLog

## FIG-APP-71. ImageLog

```text
PRE
 ↓
Business Runtime
 ↓
POST / EX
```

ImageLog는 운영 Evidence이며 Business Ledger와 동일하지 않다.

---

# 76. Application Runtime Model

## FIG-APP-72. End-to-End Runtime

```text
Client
  ↓
WEB / Entry
  ↓
Security
  ↓
Controller / TCF
  ↓
Worker / TX
  ↓
Handler
  ↓
Facade
  ↓
Service
  ↓
DAO / SQL
  ↓
DB / External
  ↓
Response
```

---

# 77. Application Runtime Type

## FIG-APP-73. Runtime Classification

```text
Application
  │
  ├─ Online
  ├─ Event
  ├─ CDC
  ├─ ETL
  ├─ Analysis
  ├─ File
  └─ Batch
```

Application마다 주 Runtime Type과 보조 Runtime Type을 정의한다.

---

# 78. Runtime Isolation

## FIG-APP-74. Workload Separation

```text
Online
  ──► Online Resource

Event
  ──► Consumer Resource

Batch / ETL
  ──► Batch Resource

BI / Analysis
  ──► Analytical Resource
```

Heavy Runtime이 Online SLA를 침해하지 않도록 한다.

---

# 79. Application Deployment Architecture

## FIG-APP-75. Application to Deployment

```text
Application
  ↓
Module
  ↓
Artifact
  ↓
DeploymentId
  ↓
Host
  ↓
JVM
  ↓
Context / WAR
```

---

# 80. Application ≠ WAR

## FIG-APP-76. Boundary Reminder

```text
Application
≠ Build Module
≠ WAR
≠ JVM
≠ Host
```

하나의 Application이 여러 Artifact/JVM으로 배치될 수 있고,
한 Runtime Process가 여러 Component를 포함할 수도 있다.

---

# 81. Application HA

## FIG-APP-77. HA Responsibility

```text
Application Service
  ↓
Multiple Runtime Instances
  ↓
Health Check
  ↓
Traffic Distribution
  ↓
Residual Capacity
```

---

# 82. Application DR

## FIG-APP-78. DR Trace

```text
Application
  ↓
Artifact / Config / Key
  ↓
DR Runtime
  ↓
Data
  ↓
Route
  ↓
Business Validation
```

---

# 83. Application Observability

## FIG-APP-79. Service View

```text
Application
  ↓
ServiceId
  ↓
TPS / p95 / Error
  ↓
Worker / Hikari
  ↓
SqlId / External
```

---

# 84. Application Runtime Evidence

## FIG-APP-80. Evidence Chain

```text
Application
  ↓
ServiceId
  ↓
Source Commit
  ↓
Artifact Hash
  ↓
DeploymentId
  ↓
GUID
  ↓
Runtime Evidence
```

---

# 85. Application Traceability

## FIG-APP-81. Full Trace

```text
Requirement
  ↓
Application
  ↓
Program
  ↓
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
SqlId
  ↓
Table
  ↓
Artifact
  ↓
Deployment
  ↓
GUID
  ↓
Evidence
```

---

# 86. Reverse Traceability

## FIG-APP-82. Reverse Trace

```text
Incident / SQL / Table
   ↑
ServiceId
   ↑
Source
   ↑
Application
   ↑
Requirement
```

---

# 87. Application Architecture Model

## FIG-APP-83. Model Entities

```text
ApplicationGroup
Application
Program
ServiceId
Controller
Handler
Facade
Service
Rule
DAO
Mapper
SqlId
Table
Interface
Artifact
Deployment
RuntimeScenario
Evidence
```

---

# 88. Model Relations

## FIG-APP-84. Relations

```text
Application
  CONTAINS
Program

Program
  EXPOSES
ServiceId

ServiceId
  HANDLED_BY
Handler

Handler
  CALLS
Facade

Facade
  CALLS
Service

Service
  USES
DAO

DAO
  EXECUTES
SqlId

SqlId
  ACCESSES
Table
```

---

# 89. Deployment Relations

```text
Application
  BUILDS_TO
Artifact

Artifact
  DEPLOYED_AS
Deployment

Deployment
  RUNS_ON
JVM / Host
```

---

# 90. Application Architecture Rules

## FIG-APP-85. Rule Categories

```text
Classification Rule
Naming Rule
ServiceId Rule
Layer Dependency Rule
Data Access Rule
Interface Rule
Transaction Rule
Timeout Rule
Security Rule
Logging Rule
Deployment Rule
Evidence Rule
```

---

# 91. Mandatory Rule — Layer

```text
R-APP-HANDLER-NO-DAO
R-APP-CONTROLLER-NO-DAO
R-APP-FRAMEWORK-NO-BUSINESS
```

---

# 92. Mandatory Rule — ServiceId

```text
R-SERVICEID-FORMAT
R-SERVICEID-UNIQUE
R-HANDLER-BRANCH
R-SERVICEID-CATALOG-ALIGNMENT
```

---

# 93. Mandatory Rule — Data

```text
R-DAO-MAPPER
R-SQL-TABLE-TRACE
R-CROSS-SYSTEM-DML-FORBIDDEN
```

---

# 94. Mandatory Rule — Runtime

```text
R-TX-OWNER
R-TIMEOUT-HIERARCHY
R-RETRY-IDEMPOTENCY
```

---

# 95. Mandatory Rule — Security

```text
R-JWT-ALGORITHM
R-JWT-KEY-CONSISTENCY
R-IDENTITY-BINDING
R-SENSITIVE-LOG
```

---

# 96. Mandatory Rule — Deployment / Evidence

```text
R-DEPLOYMENT-MANIFEST
R-RUNTIME-INVENTORY
R-RUNTIME-EVIDENCE
```

---

# 97. Application Architecture Test

## FIG-APP-86. Test Layers

```text
Static Dependency
      ↓
Architecture Rule
      ↓
Contract
      ↓
Integration
      ↓
Security
      ↓
Transaction / Timeout
      ↓
Performance
      ↓
Runtime Evidence
```

---

# 98. 신규 Application 설계 Route

## FIG-APP-87. New Application

```text
Business Responsibility
      ↓
Application Group
      ↓
Application Boundary
      ↓
Owned / Used Data
      ↓
Entry / Interface
      ↓
Layer / Component
      ↓
Program / ServiceId
      ↓
Runtime Type
      ↓
Deployment
      ↓
NFR / Security
      ↓
Monitoring / Evidence
```

---

# 99. 신규 업무 Program 추가 Route

## FIG-APP-88. New Program

```text
Business Requirement
   ↓
Program ID
   ↓
ServiceId
   ↓
DTO
   ↓
Handler
   ↓
Facade
   ↓
Service
   ↓
DAO / Mapper
   ↓
Test
   ↓
Runtime Evidence
```

---

# 100. Application Architecture Checklist

```text
[ ] Application Group?
[ ] Responsibility?
[ ] Boundary?
[ ] Owner?
[ ] Program?
[ ] ServiceId?
[ ] Entry?
[ ] Facade/Service?
[ ] Data?
[ ] Interface?
[ ] TX?
[ ] Timeout?
[ ] Security?
[ ] Deployment?
[ ] Monitoring?
[ ] Evidence?
```

---

# 101. Component Checklist

```text
[ ] Layer가 명확한가?
[ ] 상위/하위 Dependency가 올바른가?
[ ] Business 책임인가 Framework 책임인가?
[ ] 다른 Application 내부 Component를 직접 호출하지 않는가?
[ ] Source와 Runtime으로 추적 가능한가?
```

---

# 102. Application Boundary 금지패턴

## FIG-APP-89. Forbidden Patterns

```text
Controller → DAO
Handler → DAO
Application A → Application B DAO
Application A → Application B DB DML
Business → Framework Internal State Manipulation
UI → DB
```

---

# 103. Runtime 금지패턴

```text
HTTP 504 = Worker End
Event = Online Request Thread
Batch = Online Resource
BI Heavy Query = Online RDW direct without governance
```

---

# 104. Security 금지패턴

```text
Header User = Trusted Principal
Private Key in Source
same kid / different key
Raw Token Log
Authentication = Authorization
```

---

# 105. PDMG AS-IS 강점

## FIG-APP-90. Strong Reference Areas

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
Mapper
```

대표 Source에서 비교적 강한 Trace를 가진다.

---

# 106. PDMG 재검증 필요영역

## FIG-APP-91. Partial / GAP

```text
TCF OFF Boundary
JWT Issuer / Verifier
JWT Key Lifecycle
Identity Binding
Query / TX Timeout
Context Snapshot
Generic Error
Deployment Mapping
pdmg-om
Runtime Evidence Automation
```

---

# 107. NSIGHT Target Promotion

## FIG-APP-92. Promotion Gate

```text
PDMG Pattern
   ↓
Source Evidence
   ↓
Architecture Principle Fit
   ↓
NFR Fit
   ↓
Security Fit
   ↓
Operations Fit
   ↓
ADR / Approval
   ↓
NSIGHT Application Standard
```

---

# 108. Application Architecture와 11장 개발표준의 관계

## FIG-APP-93. Definition vs Standard

```text
별첨 A Application Architecture
= 무엇을 어떤 책임/경계/구조로 구성하는가?

             ↓ derives

11 Development Standard
= 개발자가 그것을 Source/Config/Test로 어떻게 구현하는가?
```

---

# 109. Application Architecture와 03 LOGICAL의 관계

```text
03 LOGICAL
= Zone / Logical System / Logical Node

별첨 A
= Application Responsibility / Layer / Component

Application
   ↓ mapped to
Logical System / Node
```

둘은 겹치지만 동일 문서는 아니다.

---

# 110. Application Architecture와 04 PHYSICAL의 관계

```text
Application
  ↓
Artifact
  ↓
Deployment
  ↓
JVM / Host
```

Physical은 **Application을 어디에 실행할 것인가**를 정의한다.

---

# 111. Application Architecture와 05 MECHANISM의 관계

```text
Application Boundary
  ↓
Interaction Contract
  ↓
API / Event / CDC / ETL / File
```

Mechanism은 **Application 간 연결규칙**을 정의한다.

---

# 112. Application Architecture와 06 RUNTIME의 관계

```text
Application Structure
  ↓
Runtime Sequence
  ↓
Thread / TX / Pool
  ↓
Outcome / Failure / Recovery
```

---

# 113. Application Architecture와 10 Baseline의 관계

```text
Application Model
  ↓
Naming / ServiceId
  ↓
Traceability
  ↓
Rule
  ↓
Gate
  ↓
Baseline
```

---

# 114. Application Inventory Template

```yaml
application:
  applicationId:
  applicationGroup:
  serviceDomain:
  businessResponsibilities:
  owner:
  entryTypes:
  runtimeTypes:
  modules:
  packages:
  programs:
  serviceIds:
  ownedData:
  usedData:
  interfaces:
  security:
  deployment:
  monitoring:
  evidence:
  status:
```

---

# 115. Program Inventory Template

```yaml
program:
  programId:
  application:
  businessCode:
  functionCode:
  package:
  ui:
  serviceIds:
  owner:
  status:
```

---

# 116. Service Inventory Template

```yaml
service:
  serviceId:
  programId:
  transactionType:
  entry:
  handler:
  facade:
  service:
  dao:
  mapper:
  sqlIds:
  tables:
  txPolicy:
  timeoutPolicy:
  securityPolicy:
  deployment:
  runtimeEvidence:
```

---

# 117. Component Inventory Template

```yaml
component:
  application:
  layer:
  module:
  package:
  class:
  responsibility:
  dependencies:
  interfaces:
  evidence:
```

---

# 118. Application Dependency Matrix
## TEXT ARCHITECTURE 보완 — Application Dependency Matrix

```text
Entry
Controller / Handler
      ↓
Facade
      ↓
Service
      ↓
Rule [optional]
      ↓
DAO
      ↓
Mapper

금지:
Controller/Handler ──X──► DAO / Mapper
Application A ───────X──► Application B DAO
```


| Caller | Target | 허용 |
|---|---|---|
| Controller | Facade | O |
| Handler | Facade | O |
| Facade | Service | O |
| Service | Rule | O / Optional |
| Service | DAO | O |
| DAO | Mapper | O |
| Controller | DAO | X |
| Handler | DAO | X |
| App A | App B DAO | X |
| App A | App B Public API/Event | O |

---

# 119. Application Runtime Matrix
## TEXT ARCHITECTURE 보완 — Application Runtime Matrix

```text
Online      → Sync Request      → Thread / Worker / Hikari
Event       → Async Consumer    → Lag / Retry / DLQ
CDC         → Streaming Change  → Capture / Apply Lag
Batch       → Job / Step        → Restart / Reconcile
BI          → Analytical Query  → Concurrency / Heavy Query
File        → Transfer/Process  → Hash / Status / Recovery
```


| Application Type | Runtime | 주요 자원 | 주요 관측 |
|---|---|---|---|
| Online | Sync Request | Tomcat/Worker/Hikari | p95/Timeout/Error |
| Event | Async Consumer | Consumer/Partition | Lag/Retry/DLQ |
| Data Change | CDC | Capture/Apply | Lag/Restart |
| Batch | Job/Step | Batch Pool/DB | Duration/Restart |
| BI | Query | Analytical Resource | Query/Concurrency |
| File | Transfer/Process | FOS/MFT/Storage | Hash/Status |

---

# 120. Application NFR Matrix

## FIG-APP-94. NFR Projection

```text
Application
  │
  ├─ Performance
  ├─ Availability
  ├─ Scalability
  ├─ Security
  └─ Observability
       ↓
Runtime Scenario
       ↓
Evidence
```

---

# 121. Availability

Application Architecture에서 정의:

```text
Failure Domain
Stateless/Stateful
Session/Token State
Dependency
Failover Requirement
```

Physical/Runtime에서 구체화한다.

---

# 122. Scalability

```text
Application
  ↓
Runtime Bottleneck
  ↓
Scale Unit
  ↓
Scale-out / Scale-up
```

Scale Unit은 Application, Module, JVM, Worker, Consumer 등에서 달라질 수 있다.

---

# 123. Security

```text
Application
  ↓
Authentication Boundary
  ↓
Authorization Boundary
  ↓
Data Access Boundary
  ↓
Audit
```

---

# 124. Observability

```text
Application
  ↓
ServiceId
  ↓
GUID
  ↓
Metric / Log / Trace
  ↓
Runbook
```

---

# 125. Application Architecture GAP Register
## TEXT ARCHITECTURE 보완 — Application GAP Lifecycle

```text
Target Application Architecture
        ↓ compare
Current Source / Runtime
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
| GAP-APP-01 | Application SSOT Inventory 미완료 | Governance |
| GAP-APP-02 | Application→Program→ServiceId 전수 Mapping 미완료 | Traceability |
| GAP-APP-03 | UI Catalog↔Handler Registry 자동정합 미완료 | Runtime Entry |
| GAP-APP-04 | TCF ON/OFF Business Core 불일치 | Application |
| GAP-APP-05 | Rule Layer Target 미확정 | Business Layer |
| GAP-APP-06 | Query/TX Timeout 미확정 | Runtime |
| GAP-APP-07 | Immutable Worker Context 미적용 | Thread |
| GAP-APP-08 | JWT Issuer/Verifier 정합 미완료 | Security |
| GAP-APP-09 | JWT Key Lifecycle 미완료 | Security |
| GAP-APP-10 | Identity Binding 미완료 | Security |
| GAP-APP-11 | SqlId→Table 전수 Trace 미완료 | Data |
| GAP-APP-12 | Application→Artifact→Deployment Mapping 미완료 | Deployment |
| GAP-APP-13 | pdmg-om 구현 미확인 | Operations |
| GAP-APP-14 | Runtime Evidence 자동화 미완료 | Governance |

---

# 126. Application Architecture Risk Register
## TEXT ARCHITECTURE 보완 — Application Risk Lifecycle

```text
Boundary / Runtime Cause
      ↓
Risk Event
      ↓
Business / Data / Security Impact
      ↓
Severity
      ↓
Mitigation / Control
      ↓
Evidence / Closure
```


| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-APP-01 | Application Boundary 침범 | High |
| RISK-APP-02 | Controller/Handler Layer Bypass | High |
| RISK-APP-03 | Cross-System DB Direct DML | Critical |
| RISK-APP-04 | ServiceId SSOT 불일치 | High |
| RISK-APP-05 | TCF ON/OFF Behavior Drift | High |
| RISK-APP-06 | Timeout Late Commit | Critical |
| RISK-APP-07 | Mutable Context Leak | Critical |
| RISK-APP-08 | JWT Algorithm/Key 불일치 | Critical |
| RISK-APP-09 | Identity Spoof | Critical |
| RISK-APP-10 | Deployment Trace 누락 | Critical |
| RISK-APP-11 | Heavy Runtime의 Online SLA 침해 | High |
| RISK-APP-12 | Runtime Evidence 미연결 | High |

---

# 127. Application Architecture ADR 후보

```text
ADR-APP-01 Application Classification SSOT
ADR-APP-02 Application Boundary
ADR-APP-03 Layer / Dependency Standard
ADR-APP-04 ServiceId SSOT
ADR-APP-05 TCF ON/OFF Business Core
ADR-APP-06 Rule Layer
ADR-APP-07 Transaction Ownership
ADR-APP-08 Timeout Budget
ADR-APP-09 Context Snapshot
ADR-APP-10 JWT Algorithm / Key
ADR-APP-11 Identity Binding
ADR-APP-12 Application Deployment Model
ADR-APP-13 Runtime Evidence
ADR-APP-14 pdmg-om Scope
```

---

# 128. Application Architecture Review Checklist

## FIG-APP-95. Review Gate

```text
Responsibility?
  ↓
Boundary?
  ↓
Layer?
  ↓
ServiceId?
  ↓
Data?
  ↓
Interface?
  ↓
Runtime?
  ↓
Security?
  ↓
Deployment?
  ↓
Evidence?
  ↓
PASS / GAP
```

---

# 129. 신규 Application 개통 금지조건

## FIG-APP-96. Go-Live Blocker

```text
Application Responsibility 불명확
        OR
ServiceId / Interface SSOT 없음
        OR
Cross-System DB Bypass
        OR
Unknown TX Owner
        OR
Critical Security GAP
        OR
Deployment Trace 없음
        OR
Monitoring / Runtime Evidence 없음
        ↓
GO-LIVE BLOCK
```

---

# 130. Application Architecture Completion Gate

## FIG-APP-97. Completion Gate

```text
Classification Defined?
   ↓ YES
Responsibility Defined?
   ↓ YES
Boundary Defined?
   ↓ YES
Layer / Component Defined?
   ↓ YES
Program / ServiceId Defined?
   ↓ YES
Data / Interface Defined?
   ↓ YES
Runtime Defined?
   ↓ YES
Security Defined?
   ↓ YES
Deployment Traceable?
   ↓ YES
Evidence Available?
   ↓ YES
APPLICATION ARCHITECTURE PASS
```

---

# 131. 최종 Application Architecture 지도

## FIG-APP-98. Final Application Architecture

```text
BUSINESS
Service Domain
   ↓
Application Group
   ↓
Application Responsibility
   ↓
────────────────────────────────
APPLICATION
Program / ServiceId
   ↓
Entry
Controller / Handler
   ↓
Facade
   ↓
Service / Rule
   ↓
DAO / Mapper
   ↓
────────────────────────────────
DATA / INTEGRATION
DB / API / Event / CDC / ETL / File
   ↓
────────────────────────────────
RUNTIME
Thread / TX / Timeout / Security
   ↓
────────────────────────────────
DEPLOYMENT
Artifact / JVM / Host
   ↓
────────────────────────────────
EVIDENCE
GUID / Metric / Trace / Test
   ↓
BASELINE / GOVERNANCE
```

---

# 132. Definition of Done
## TEXT ARCHITECTURE 보완 — Application Architecture DoD

```text
Responsibility Defined
      ↓
Boundary Defined
      ↓
Layer / Component Defined
      ↓
ServiceId / Data / Interface Defined
      ↓
Runtime / Security / Deployment Defined
      ↓
Trace / Evidence Available
      ↓
APPLICATION ARCHITECTURE DoD
```


## Classification / Boundary
- [x] Service Domain와 Application Group 구분
- [x] Marketing 업무분류
- [x] Application Responsibility
- [x] Application/Data/Integration Boundary

## Structure
- [x] Module/Process/Context/Node 구분
- [x] Layer / Component
- [x] Controller / Handler / Facade / Service / Rule
- [x] DAO / Mapper

## Identity / Trace
- [x] Program ID
- [x] ServiceId
- [x] Current PDMG Registry
- [x] SSOT
- [x] GUID

## Runtime / Common
- [x] Framework vs Business
- [x] TCF ON/OFF
- [x] Context
- [x] Transaction
- [x] Timeout
- [x] Error / Logging

## Security
- [x] JWT
- [x] Identity Binding
- [x] Authorization Boundary
- [x] Critical GAP 유지

## Deployment / Governance
- [x] Application→Artifact→Deployment
- [x] Runtime Evidence
- [x] Model/Rule
- [x] GAP/RISK/ADR
- [x] Go-Live Gate

**APPLICATION ARCHITECTURE 별첨 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. Application Inventory SSOT 확정
2. Application→Program→ServiceId 전수 Mapping
3. UI Catalog↔Backend Registry 자동비교
4. TCF ON/OFF Business Core 정합
5. Rule Layer Target 결정
6. Query/TX Timeout 승인
7. JWT Issuer/Verifier/Key 정합
8. Identity Binding 확정
9. SqlId→Table 자동 Trace
10. Application→Artifact→Host/JVM Deployment Manifest
11. pdmg-om 실제 구현 확인
12. Runtime Evidence 자동화

---

# 133. 장 최종 결론

## FIG-APP-99. Application Architecture Final

```text
Business Responsibility
        ↓
Application Boundary
        ↓
Program / ServiceId
        ↓
Layer / Component
        ↓
Data / Interface
        ↓
Runtime / Security
        ↓
Deployment
        ↓
Evidence
        ↓
Architecture Baseline
```

> **Application Architecture의 본질은 “Controller-Service-DAO 구조”가 아니라, 비즈니스 책임을 Application Boundary로 고정하고 그 책임이 Source·Runtime·Deployment까지 일관되게 유지되도록 하는 것이다.**

> **PDMG는 ServiceId→Handler→Facade→Service→DAO→Mapper 축이 강한 Reference 구현이지만, TCF OFF 경계, JWT 정합성, Timeout, Deployment Mapping, OM, Runtime Evidence는 Target 승격 전에 보완해야 한다.**

> **이 별첨은 03 LOGICAL의 Logical System/Node 정의와 11 Development Standard의 구현규칙 사이에서, “Application 자체를 무엇으로 보고 어떻게 구조화할 것인가”를 독립적으로 정의한다.**
