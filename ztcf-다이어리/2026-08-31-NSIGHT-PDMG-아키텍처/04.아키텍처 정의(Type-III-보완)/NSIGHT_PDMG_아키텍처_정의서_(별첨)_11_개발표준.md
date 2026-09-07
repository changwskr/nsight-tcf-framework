# NSIGHT / PDMG 아키텍처 정의서
# 11. ARCHITECTURE DEVELOPMENT STANDARD

## FIG-DEV-00. Architecture Development Standard Master Map

```text
NSIGHT Architecture Baseline
        ↓
Development Environment
        ↓
Repository / Git / Gradle
        ↓
Module / Package / Naming
        ↓
Program / ServiceId
        ↓
Layer / DTO / Message
        ↓
DAO / Mapper / SQL
        ↓
Transaction / Timeout / Retry
        ↓
Error / Logging / Security
        ↓
Cache / File / Batch / Interface
        ↓
Test / Review / CI-CD
        ↓
Deployment / Runtime Evidence
        ↓
Architecture Conformance / New Baseline
```

## 개발자가 실제 구현할 때 따라야 하는 Architecture Development Standard
## Visual-First / Rule-Driven / Source-Conformance 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-DEVELOPMENT-STANDARD-11`  
> Architecture Level: **L10 — DEVELOPMENT STANDARD / IMPLEMENTATION GOVERNANCE**  
> 문서 상태: **Draft / Evidence-First / Visual-First / Rule-Driven**  
> 작성 기준일: **2026-08-31**  
> 선행 장: `01 VISION ~ 10 INTEGRATED ARCHITECTURE BASELINE`  
> 본 장 목적: **Architecture 원칙을 개발자가 실제 Source/Config/Test/Build에 적용할 수 있는 구현 표준으로 변환**

---

# 0. 이 장의 역할

01~10장은 Architecture를 정의하고 검증하는 구조를 만들었다.

11장은 그 Architecture를 **개발자가 실제 코드로 어떻게 구현해야 하는가**를 정의한다.

## FIG-DEV-01. Architecture → Development Standard

```text
VISION / BIG PICTURE
       ↓
LOGICAL / PHYSICAL
       ↓
MECHANISM / RUNTIME
       ↓
TRACEABILITY / BASELINE
       ↓
┌──────────────────────────────────────────┐
│ ARCHITECTURE DEVELOPMENT STANDARD        │
│                                          │
│ Module / Package                         │
│ Naming / ServiceId                       │
│ Controller / Handler / Facade / Service  │
│ DTO / Validation / Message               │
│ TX / Timeout / Retry                     │
│ DAO / Mapper / SQL                       │
│ Error / Logging / GUID                   │
│ JWT / Session / Security                 │
│ Cache / File / Batch                     │
│ Test / Review / CI-CD                    │
└──────────────────────────────────────────┘
       ↓
SOURCE / CONFIG / TEST / BUILD
       ↓
RUNTIME EVIDENCE
```

### 핵심

```text
Architecture Principle
       ↓
Development Rule
       ↓
Source Pattern
       ↓
Architecture Test
       ↓
CI Gate
```

---

# 1. 개발표준 전체 Journey

## FIG-DEV-02. Development Standard Journey

```text
01 개발환경
   ↓
02 Repository / Git
   ↓
03 Gradle / Build
   ↓
04 Module / Project
   ↓
05 Package
   ↓
06 Naming
   ↓
07 Business Code / Program / ServiceId
   ↓
08 Entry / Controller / Handler
   ↓
09 Facade / Service / Rule
   ↓
10 DTO / Validation
   ↓
11 Standard Message / Header / GUID
   ↓
12 DAO / Mapper / SQL
   ↓
13 Transaction / Timeout / Retry
   ↓
14 Error / Logging
   ↓
15 JWT / Session / Security
   ↓
16 Cache / File / Batch
   ↓
17 Test / Review / Quality
   ↓
18 Build / CI-CD / Deployment
   ↓
19 Architecture Conformance
   ↓
20 Runtime Evidence / Change Governance
```

---

# 2. 표준의 상태와 적용범위

## FIG-DEV-03. Evidence State

```text
[AS-IS]
PDMG Source에서 실제 확인

[TO-BE]
NSIGHT 목표표준

[PROPOSED]
승인 전 후보

[GAP]
현재 부족

[CONFLICT]
기준 간 충돌

[UNKNOWN]
근거 없음
```

### 가장 중요한 원칙

```text
PDMG에 구현되어 있다
        │
        ▼
NSIGHT 개발표준

자동 승격 X
```

반드시:

```text
Evidence
→ NFR Fit
→ Scope Fit
→ Security / Ops Review
→ ADR / Approval
```

을 거친다.

---

# 3. 표준 강제수준

## FIG-DEV-04. Rule Severity

```text
MUST
  ↓
위반 시 Build / Gate FAIL 후보

SHOULD
  ↓
원칙적 준수, 예외 사유 필요

MAY
  ↓
상황별 선택

MUST NOT
  ↓
금지
```

---

# 4. 개발환경 표준

## FIG-DEV-05. Development Environment

```text
Developer Workstation
│
├─ JDK
├─ Gradle
├─ IDE
├─ Git
├─ Local Config
└─ Test Tool
      │
      ▼
Same Source / Same Build Definition
      │
      ▼
DEV / TEST / PROD
```

### [AS-IS] PDMG Reference

```text
Java        = 21
Spring Boot = 3.5.14
Build       = Gradle Multi-project
```

이 값들은 **현재 PDMG Source Baseline**이며 NSIGHT 전체 Target 버전 확정은 승인 Baseline을 따른다.

### DEV-ENV-01 [MUST]

개발자는 프로젝트가 지정한 JDK/Gradle Toolchain을 사용해야 한다.

### DEV-ENV-02 [MUST NOT]

```text
개발자 개인 JDK
→ 임의 Compile
→ 운영 Artifact

X
```

---

# 5. 환경간 동일성 표준

## FIG-DEV-06. Same Source / Same Artifact

```text
Source Commit C1
      ↓
Build B1
      ↓
Artifact A1
      ├─ DEV
      ├─ TEST
      ├─ PROD
      └─ DR
```

### 원칙

```text
Build Once
Promote Same Artifact
Separate Environment Config
```

---

# 6. Repository 표준

## FIG-DEV-07. Repository Responsibility

```text
Repository
│
├─ Source
├─ Build Definition
├─ Test
├─ Non-secret Config Template
├─ Architecture Rule
└─ Documentation Link
```

### REPO-01 [MUST]

Source와 Build 정의는 동일 Repository/Baseline으로 추적 가능해야 한다.

### REPO-02 [MUST NOT]

다음은 Repository에 저장하지 않는다.

```text
Password
Private Key
Refresh Token
HMAC Secret
Production Credential
```

---

# 7. Git 표준

## FIG-DEV-08. Change Trace

```text
Requirement / Issue
      ↓
Branch
      ↓
Commit
      ↓
Merge Request
      ↓
Review
      ↓
Merge
      ↓
Build / Test
```

### GIT-01 [MUST]

Commit은 변경 이유를 추적할 수 있어야 한다.

### GIT-02 [MUST]

Architecture 영향 변경은 Issue/Requirement/ADR 중 하나와 연결한다.

### GIT-03 [MUST NOT]

```text
Production Server
  ↓
Source 직접 수정
```

금지한다.

---

# 8. Branch / Merge 표준

## FIG-DEV-09. Branch Governance

```text
Change Request
   ↓
Working Branch
   ↓
Review / Test
   ↓
Approved Merge
   ↓
Baseline Branch
```

Branch Naming 세부는 프로젝트 SCM 정책으로 확정하되,
Architecture Rule은 특정 Branch 전략 자체보다 **Traceability와 Review**를 강제한다.

---

# 9. Gradle Multi-project 표준

## FIG-DEV-10. Build Module

```text
PDMG Root
│
├─ pdmg-ui
├─ pdmg-jwt
│   └─ pdmg-fw
├─ pdmg-service
│   └─ pdmg-fw
├─ pdmg-fw
└─ pdmg-om [Evidence 추가 확인]
```

### BUILD-01 [MUST]

Module Dependency는 책임방향과 일치해야 한다.

```text
Business Module
   ↓ depends on
Framework Module
```

### BUILD-02 [MUST NOT]

```text
pdmg-fw
   ↓
특정 Business Package
```

직접 의존을 만들지 않는다.

---

# 10. Module / Process / Spring Context 표준

## FIG-DEV-11. Three Boundaries

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
```

### DEV-MOD-01 [MUST]

설계서와 개발문서에서 이 세 경계를 명시적으로 구분한다.

### 대표 해석

```text
pdmg-fw
= Build Module

pdmg-fw
≠ 반드시 별도 Runtime Server
```

---

# 11. Application Module 책임

## FIG-DEV-12. Module Responsibility

```text
pdmg-ui
= UI / Browser Entry

pdmg-jwt
= Authentication / Token

pdmg-fw
= Common Framework / Runtime Control

pdmg-service
= Business Application

pdmg-om
= Operations Module [actual implementation verify]
```

---

# 12. Package 표준

## FIG-DEV-13. Package Projection

```text
Business Classification
MG / CO / A
      │
      ├─ Java Package
      │   nhnis.mg.co.a
      │
      └─ Mapper Resource
          rdw.mg.co.a
```

### PKG-01 [MUST]

Business Package는 업무분류 축과 일치해야 한다.

### PKG-02 [MUST NOT]

Business Component를 의미 없는 범용 Package로 숨기지 않는다.

```text
misc
temp
etc
common2
util2
```

---

# 13. 권장 Business Package

## FIG-DEV-14. Business Package Layers

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

대표 PDMG Source에서 확인되는 구조다.

---

# 14. Rule Layer 표준

## FIG-DEV-15. Rule Layer Position

```text
Handler
  ↓
Facade
  ↓
Service
  ↓
Rule [TO-BE candidate where useful]
  ↓
DAO
```

### 중요

Current PDMG 대표 Source에서 일반화된 `application.rule` 계층이 항상 존재한다고 단정하지 않는다.

### RULE-01 [SHOULD]

복잡한 Business Decision이 Service Procedure와 분리되어 재사용/시험할 가치가 있을 때 Rule을 둔다.

### RULE-02 [MUST NOT]

단순 CRUD마다 형식적으로 Rule Class를 만들지 않는다.

---

# 15. Naming 표준 전체

## FIG-DEV-16. Naming Backbone

```text
Business
  ↓
Program ID
  ↓
ServiceId
  ↓
Class Stem
  ↓
Package
  ↓
Mapper
  ↓
Runtime Trace
```

---

# 16. Program ID

## FIG-DEV-17. Program ID

```text
mg | co | a | 9001
│    │    │     │
│    │    │     └─ Program Number
│    │    └─────── Function
│    └──────────── Business
└───────────────── Major/Application
```

예:

```text
mgcoa9001
```

---

# 17. ServiceId 표준

## FIG-DEV-18. ServiceId Anatomy

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

# 18. ServiceId Format

일반 후보:

```text
^[a-z]{2}[a-z]{2}[a-z][0-9]{4}[SCUDAR][0-9A-Z]$
```

MG 후보:

```text
^mg[a-z]{2}[a-z][0-9]{4}[SCUDAR][0-9A-Z]$
```

### SVCID-01 [MUST]

ServiceId는 규칙에 맞아야 한다.

### SVCID-02 [MUST]

ServiceId는 Handler Registry 내에서 Unique해야 한다.

---

# 19. ServiceId 거래구분

```text
S = Select
C = Create
U = Update
D = Delete
A = Action
R = Reserved / Reference candidate
```

### [AS-IS]

현재 대표 Handler 실제 사용은 주로:

```text
S / C / U / D
```

---

# 20. ServiceId Registry 표준

## FIG-DEV-19. Registry SSOT

```text
Architecture Model
      ↓
ServiceId Registry
      ├─ Backend Handler
      ├─ UI Catalog
      ├─ Test Catalog
      ├─ Monitoring Catalog
      └─ Interface Catalog
```

### SVCID-03 [MUST]

Backend Handler Registry와 UI Catalog 차이를 자동검증해야 한다.

---

# 21. Handler 등록 표준

## FIG-DEV-20. Handler Registry

```text
TransactionHandler Beans
      ↓
serviceIds()
      ↓
Registry
      │
      ├─ unique → register
      └─ duplicate → startup fail
```

### [AS-IS]

Current PDMG Source 분석에서는 Duplicate ServiceId는 기동 실패로 처리된다.

---

# 22. Handler Branch 표준

## FIG-DEV-21. Registered vs Branch

```text
serviceIds()
   │
   │ compare
   ▼
handle() branch
```

### SVCID-04 [MUST]

```text
등록 O / Branch X   금지
등록 X / Branch O   금지
```

---

# 23. Layer 표준

## FIG-DEV-22. Standard Layer

```text
Entry
  ↓
Handler
  ↓
Facade
  ↓
Service
  ↓
Rule [optional]
  ↓
DAO
  ↓
Mapper / SQL
```

---

# 24. Dependency Rule

## FIG-DEV-23. Allowed Dependency

```text
Controller / Handler
      ↓
Facade
      ↓
Service
      ↓
DAO
      ↓
Mapper
```

### MUST NOT

```text
Controller → DAO
Controller → Mapper
Handler    → DAO
Handler    → Mapper
UI         → DB
```

---

# 25. Controller 표준

## FIG-DEV-24. Controller Responsibility

```text
HTTP
 ↓
Controller
 │
 ├─ Entry Mapping
 ├─ Input Binding
 ├─ Validation Trigger
 └─ Facade Call
 ↓
Business Result
```

### CTRL-01 [MUST]

Controller는 Transport/Entry Adapter로 유지한다.

### CTRL-02 [MUST NOT]

Controller에 SQL/Data Access 또는 장시간 Business Procedure를 넣지 않는다.

---

# 26. Handler 표준

## FIG-DEV-25. Handler Responsibility

```text
ServiceId
  ↓
Handler
  │
  ├─ Register ServiceId
  ├─ Branch
  ├─ DTO transfer
  └─ Facade Call
```

### HANDLER-01 [MUST]

Handler는 Thin Inbound Adapter로 유지한다.

---

# 27. Facade 표준

## FIG-DEV-26. Facade Responsibility

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

### FACADE-01 [SHOULD]

외부 Entry가 달라도 동일 Business Use Case는 동일 Facade로 수렴하도록 한다.

---

# 28. TCF ON / OFF Business Core

## FIG-DEV-27. Same Business Core

```text
TCF ON
Handler ─────────┐
                 │
                 ▼
               Facade
                 ↓
               Service
                 ↓
                DAO
                 ▲
                 │
TCF OFF           │
Controller ───────┘
```

### [GAP]

Current OFF Controller 중 일부는 Service 직접호출이 존재한다.

### DEV-TCF-01 [TO-BE]

ON/OFF 차이는 Entry Adapter에 국한하고 Business Core는 통합한다.

---

# 29. Service 표준

## FIG-DEV-28. Service Responsibility

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

### SERVICE-01 [MUST]

Service는 업무절차를 소유한다.

### SERVICE-02 [MUST NOT]

HTTP/Servlet 객체를 Business Input으로 직접 의존하지 않는다.

---

# 30. DTO 표준

## FIG-DEV-29. DTO Boundary

```text
Transport Message
  ↓
DTO
  ↓
Facade / Service
  ↓
Business Result DTO
```

### DTO-01 [MUST]

Request/Response DTO는 명시적으로 정의한다.

### DTO-02 [MUST]

Input과 Output의 책임을 구분한다.

---

# 31. DTO Naming

대표 PDMG 형태:

```text
mgcoa9000S0DTOin
mgcoa9000S0DTOout
```

### DTO-03 [SHOULD]

```text
Program + Service + Direction
```

이 추적 가능성을 유지한다.

---

# 32. Validation 표준

## FIG-DEV-30. Validation Layers

```text
Request
  ↓
Syntax / Schema Validation
  ↓
Field Validation
  ↓
Business Validation
  ↓
Authorization
  ↓
Business Execution
```

### 핵심

```text
Validation Error
≠ Business Reject
≠ Authorization Error
```

---

# 33. Validation Error 처리
## TEXT ARCHITECTURE 보완 — Validation Failure

```text
Input
  ↓
Validation
  ├─ PASS → Business
  └─ FAIL
       ↓
    Standard Error
       ↓
    Client Correction
```


### VALID-01 [MUST]

Validation 실패는 Retry 대상이 아니다.

### VALID-02 [MUST]

Client가 수정 가능한 입력오류와 시스템오류를 구분한다.

---

# 34. Standard Message

## FIG-DEV-31. Request Envelope

```text
{
  hdr_nhnis: {
    sys_comm: {
      std_gbl_id,
      rms_svc_c,
      tr_sysid,
      tr_trm_ipadr,
      tr_brc,
      scid,
      optr_eno
    }
  },
  dto: {
    ...
  }
}
```

### [AS-IS]

Current PDMG Request:

```text
{ hdr_nhnis, dto }
```

---

# 35. Success / Error Envelope

## FIG-DEV-32. Standard Response

```text
Success
{ hdr_nhnis, dto }

Known Error
{ hdr_nhnis, result }
```

### MSG-01 [MUST]

UI/Entry별 독립 Response Envelope 난립을 금지한다.

---

# 36. Header 책임

## FIG-DEV-33. Header Responsibilities

```text
Header
│
├─ Transaction Identity
├─ Service Identity
├─ System Identity
├─ Client / Branch
└─ Operator / User Context
```

---

# 37. Header Trust 표준

## FIG-DEV-34. Trusted Identity

```text
Client Header User
     │
     │ untrusted by itself
     ▼
Authentication Evidence
     ↓
Principal
     ↓
Identity Binding
     ↓
Authorization
```

### SEC-ID-01 [MUST NOT]

`optr_eno` 값만으로 인증되었다고 판단하지 않는다.

---

# 38. GUID 표준

## FIG-DEV-35. GUID Lifecycle

```text
std_gbl_id
   ↓
Filter
   ↓
ServiceContext
   ↓
MDC
   ↓
Worker
   ↓
Business / SQL
   ↓
ImageLog
   ↓
Response / Evidence
```

### GUID-01 [MUST]

GUID는 Entry부터 Runtime Evidence까지 전파한다.

### GUID-02 [MUST NOT]

중간 Layer에서 임의로 새 GUID로 덮어쓰지 않는다.

---

# 39. ServiceContext 표준

## FIG-DEV-36. Context

```text
ServiceContext
│
├─ guid
├─ header
├─ request metadata
├─ userContext
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

# 40. ThreadLocal Context 표준

## FIG-DEV-37. Context Lifecycle

```text
Request Thread
  ↓ set
ServiceContext / MDC
  ↓ capture
Worker Thread
  ↓ install
Business
  ↓
finally
  ↓ clear
```

### CTX-01 [MUST]

모든 Exit Path에서 Context/MDC를 Clear해야 한다.

---

# 41. Mutable Context 개선표준

## FIG-DEV-38. Snapshot Candidate

```text
Request Mutable Context
      ↓ capture
Immutable Worker Snapshot
      ↓
Worker
```

### [GAP]

Current PDMG는 동일 mutable `ServiceContext` 참조 공유 Risk가 분석된다.

### CTX-02 [TO-BE]

Worker에는 필요한 값만 복사한 Immutable Snapshot 사용을 우선 검토한다.

---

# 42. DAO 표준

## FIG-DEV-39. DAO Responsibility

```text
Service
  ↓
DAO
  ↓
MyBatis
  ↓
Mapper XML
  ↓
DB
```

### DAO-01 [MUST]

DAO는 Data Access Contract를 소유한다.

### DAO-02 [MUST NOT]

DAO에 업무화면/HTTP 처리 로직을 넣지 않는다.

---

# 43. Mapper Namespace 표준

## FIG-DEV-40. Namespace Contract

```text
Java DAO FQCN
       │
       │ exact match
       ▼
Mapper XML namespace
```

### MAP-01 [MUST]

DAO FQCN과 Mapper Namespace를 일치시킨다.

---

# 44. Mapper Resource 표준

[AS-IS] PDMG:

```text
classpath*:rdw.*/*.xml
```

대표:

```text
rdw.mg.co.a/mgcoa9000-ORA.xml
```

### MAP-02 [MUST]

Resource Path 변경 시 MyBatis Load Pattern과 함께 검증한다.

---

# 45. SqlId 표준

## FIG-DEV-41. SqlId

```text
ServiceId
  ↓
DAO Method
  ↓
SqlId
  ↓
SQL
```

대표:

```text
mgcoa9000S0_S0
mgcoa9000S0_COUNT
mgcoa9000C0_C0
mgcoa9000U0_U0
mgcoa9000D0_D0
```

---

# 46. SQL 표준

## FIG-DEV-42. SQL Responsibility

```text
ServiceId
  ↓
DAO
  ↓
Mapper
  ↓
SQL
  ↓
Owned / Approved Table
```

### SQL-01 [MUST]

SQL은 Mapper/DAO를 통해 추적 가능해야 한다.

### SQL-02 [MUST NOT]

Business Java Code에 ad-hoc SQL 문자열을 흩어놓지 않는다.

---

# 47. SQL → Table Trace

## FIG-DEV-43. Data Trace

```text
SqlId
  ↓
SQL Parser
  ↓
FROM / JOIN / DML
  ↓
Table / View
```

### SQL-03 [MUST]

Critical Service는 SqlId→Table/View Trace를 확보해야 한다.

---

# 48. 타 시스템 DB 접근 표준

## FIG-DEV-44. DB Access Policy

```text
Own DB
  └─ Direct Access O

Other System DB
  └─ Approved Interface / Contract

Cross-System Direct DML
  └─ MUST NOT

DB-Link
  └─ Exception / ADR only
```

---

# 49. Pagination / Large Query 표준

## FIG-DEV-45. Query Volume

```text
Client Request
  ↓
Bounded Query
  ↓
Page / Limit
  ↓
Result
```

### DATA-01 [MUST]

대량 조회는 Online Response Memory/Thread를 무제한 점유하지 않도록 제한한다.

---

# 50. Transaction 표준

## FIG-DEV-46. Transaction Boundary

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
COMMIT / ROLLBACK
```

### [AS-IS]

TCF ON + Timeout ON Current PDMG Reference.

---

# 51. Transaction Owner 판정

## FIG-DEV-47. TX Owner

```text
Annotation
  ↓
Runtime Call Path
  ↓
Transaction Manager
  ↓
Actual Connection
  ↓
BEGIN / COMMIT / ROLLBACK
```

### TX-01 [MUST]

`@Transactional` 위치만 보고 Physical TX Owner를 단정하지 않는다.

---

# 52. TransactionManager / DataSource 정합

## FIG-DEV-48. TX Alignment

```text
DataSource
   ↓
TransactionManager
   ↓
SqlSessionFactory
   ↓
Mapper / DAO
```

### TX-02 [MUST]

동일 업무 TX 경로는 의도한 TransactionManager/DataSource를 사용해야 한다.

---

# 53. Exception과 Rollback

## FIG-DEV-49. Rollback

```text
Business / System Exception
       ↓
Propagate
       ↓
Transaction Manager
       ↓
ROLLBACK
```

### TX-03 [MUST NOT]

예외를 Catch한 후 성공처럼 삼키지 않는다.

---

# 54. Read-only Transaction

```text
Query Facade
  ↓
readOnly candidate
```

### [GAP]

Outer `TransactionTemplate`이 먼저 TX를 열 때 Inner `readOnly=true`가 어떻게 적용되는지는 검증 필요.

---

# 55. Timeout 표준

## FIG-DEV-50. Timeout Hierarchy

```text
DB Query Timeout
       <
Transaction / Worker Deadline
       <
Server / Downstream Timeout
       <
Client Timeout
```

### TIME-01 [MUST]

Timeout은 계층별 Budget으로 설계한다.

---

# 56. PDMG Timeout Snapshot

```text
[AS-IS]

Worker Deadline = 5000ms
Worker Pool     = 20
Queue Capacity  = 100
```

### TIME-02

이 값은 NSIGHT Target SLA로 자동 승격하지 않는다.

---

# 57. Timeout Lifecycle

## FIG-DEV-51. Timeout Runtime

```text
Request Thread
  │ Future.get(timeout)
  ▼
Timeout
  ↓
cancel(true)
  ↓
HTTP 504

Worker Thread
  ↓
Business / SQL
  ↓
Actual End?
```

### 핵심

```text
HTTP 504
≠ Worker 종료
≠ JDBC Cancel
≠ DB Rollback 완료
```

---

# 58. Late Commit 방지

## FIG-DEV-52. Deadline Guard

```text
HTTP Timeout
   ↓
Worker returns late
   ↓
Deadline exceeded?
  ├─ YES → ROLLBACK
  └─ NO  → COMMIT
```

### TIME-03 [MUST]

Timeout 응답 뒤 Late Commit이 발생하지 않도록 Runtime 방어를 둔다.

---

# 59. JDBC Query Timeout
## TEXT ARCHITECTURE 보완 — Query Timeout

```text
Worker Deadline
     │
     └─ must contain
          ↓
    JDBC Query Timeout
          ↓
       DB Statement
```

```text
Query Timeout UNKNOWN
→ Integration Test
→ Config Baseline
→ Runtime Evidence
```


### [GAP]

Current Source 자료에서 최종 Query Timeout 실제값이 확정되지 않았다.

### TIME-04 [MUST]

DB Query Timeout 설정과 실제 Driver/DB 동작을 Integration Test로 검증한다.

---

# 60. Retry 표준

## FIG-DEV-53. Retry Decision

```text
Failure
  ↓
Transient?
 ├─ NO → No Retry
 └─ YES
      ↓
  Idempotent?
 ├─ NO → Manual/Compensation
 └─ YES
      ↓
   Backoff
      ↓
   Max Retry
      ↓
   Final Recovery
```

---

# 61. Retry 금지대상

### RETRY-01 [MUST NOT]

Blind Retry 금지:

```text
금융 DML
중복위험 Command
Validation Error
Authorization Error
Business Reject
```

---

# 62. Idempotency 표준

## FIG-DEV-54. Idempotency

```text
Command
  ↓
Idempotency Key
  ↓
Duplicate?
 ├─ YES → Previous Result / Reject
 └─ NO  → Execute
```

### RETRY-02 [MUST]

Retry 가능한 Write는 Idempotency 전략과 함께 설계한다.

---

# 63. External Call in Transaction

## FIG-DEV-55. Remote Call Risk

```text
TX BEGIN
  ↓
DB Work
  ↓
Remote Call
  ↓ slow / fail
  ↓
Long TX / Lock / Timeout
```

### TX-04 [SHOULD NOT]

원격 호출을 장시간 DB TX 내부에 포함하지 않는다.

---

# 64. Interface 유형 선택

## FIG-DEV-56. Interface Decision

```text
즉시 결과 필요?
 ├─ YES → API / MCA / MCI
 └─ NO
      ↓
Event?
 ├─ YES → Kafka / Event
 └─ NO
      ↓
DB Change?
 ├─ YES → CDC
 └─ NO
      ↓
Bulk?
 ├─ YES → ETL
 └─ NO → File / MFT
```

---

# 65. Point-to-Point 표준

## FIG-DEV-57. Integration Layer

```text
System A
   ↓
Standard Integration Layer
   ↓
System B
```

### IF-01 [MUST NOT]

원칙적으로:

```text
System A → System B DB DML
System A DB → DB-Link → System B DB
```

를 사용하지 않는다.

---

# 66. SYNC / ASYNC 표준

## FIG-DEV-58. Sync Decision

```text
현재 업무 완료에 Target 결과가 반드시 필요한가?
       │
       ├─ YES → SYNC
       └─ NO  → ASYNC 우선
```

---

# 67. Interface Contract

## FIG-DEV-59. Contract First

```text
Interface Contract
│
├─ InterfaceId
├─ Source / Target
├─ Purpose
├─ Sync / Async
├─ Schema
├─ Security
├─ Timeout
├─ Retry
├─ Idempotency
├─ Error
├─ Recovery
├─ Trace
└─ Owner
```

### IF-02 [MUST]

구현 전에 최소 Contract를 정의한다.

---

# 68. API / Event / CDC / ETL / File

```text
Online Transaction → API / MCA
Event              → Kafka
Change Data         → CDC
Bulk Data           → ETL
File                → MFT / FOS
```

### IF-03 [MUST]

목적에 맞는 Mechanism을 사용한다.

---

# 69. File 표준

## FIG-DEV-60. File Contract

```text
Producer
  ↓
File
  ├─ Name
  ├─ Charset
  ├─ Schema
  ├─ Count
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

# 70. File 처리상태

```text
READY
→ TRANSFER
→ RECEIVED
→ VALIDATED
→ PROCESSED
→ ARCHIVED
```

실패:

```text
RETRY / QUARANTINE / MANUAL RECOVERY
```

---

# 71. Batch 표준

## FIG-DEV-61. Batch

```text
Scheduler
  ↓
Job
  ↓
Step
  ↓
Reader / Processor / Writer
  ↓
Data
```

### BATCH-01 [MUST]

다음을 정의한다.

```text
JobId
Business Date
Dependency
Restart
Recovery
Reconciliation
```

---

# 72. Batch 완료조건

```text
Job Status = COMPLETED
      +
Data Reconciliation = PASS
      +
Downstream Ready
```

---

# 73. JobRepository

## FIG-DEV-62. Batch Metadata

```text
JobRepository
│
├─ JobInstance
├─ JobExecution
├─ StepExecution
├─ Parameter
└─ Restart State
```

```text
JobRepository
≠ Business Ledger
```

---

# 74. Cache 표준

## FIG-DEV-63. Cache Boundary

```text
Request
  ↓
Cache?
 ├─ HIT  → Response Candidate
 └─ MISS
      ↓
   Business / DB
      ↓
   Cache Update
```

### CACHE-01 [MUST]

Cache 적용 시 반드시 정의:

```text
Owner
Key
TTL
Invalidation
Consistency
Fallback
Monitoring
```

---

# 75. Cache 금지

```text
Cache가 System of Record
Cache 장애 = Business 장애
TTL/Invalidation 없음
Sensitive Data 무통제 저장
```

을 금지한다.

---

# 76. Error Taxonomy

## FIG-DEV-64. Error Types

```text
Validation
Authentication
Authorization
Business Reject
Routing
Timeout
Overload
DB
External
Unknown
```

---

# 77. HTTP Status + App Code

## FIG-DEV-65. Error Contract

```text
HTTP Status
= Transport / Runtime Result

Application Error Code
= Business / Framework Meaning
```

---

# 78. Current PDMG Error Mapping

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

NSIGHT Enterprise Error Standard로 자동 승격하지 않는다.

---

# 79. Generic Exception 표준

## FIG-DEV-66. Unknown Error

```text
Unknown Exception
  ↓
Standard Error Writer
  ↓
Stable Code
  ↓
Safe Message
  ↓
Trace ID
  ↓
Internal Log
```

### ERR-01 [MUST]

Client에 Stack Trace/내부 Class 정보를 노출하지 않는다.

---

# 80. Early Filter Error

## FIG-DEV-67. Early Error Gap

```text
DefaultFilter
  ↓
400 / 401
  ↓
MVC Advice 우회?
```

### [GAP]

Standard Error Envelope 일관성을 개선해야 한다.

---

# 81. Logging 표준

## FIG-DEV-68. Log Channels

```text
Application
│
├─ Access Log
├─ Transaction Log
├─ Application Log
├─ SQL Log
├─ Security Audit
├─ ImageLog
└─ Deployment Log
```

---

# 82. MDC 표준

## FIG-DEV-69. Correlation Fields

```text
GUID
ServiceId
UserId
Host
JVM
Thread
SqlId
ErrorCode
DeploymentId
```

### LOG-01 [MUST]

Critical Runtime Log는 가능한 범위에서 GUID/ServiceId를 포함한다.

---

# 83. Sensitive Logging

### LOG-02 [MUST NOT]

Raw Logging 금지:

```text
Access Token
Refresh Token
Password
Private Key
HMAC Secret
DB Password
민감 개인정보
```

---

# 84. ImageLog

## FIG-DEV-70. ImageLog Lifecycle

```text
PRE
 ↓
Business Runtime
 ↓
POST / EX
```

### [AS-IS]

PDMG에서 운영 Evidence로 사용한다.

### 주의

```text
ImageLog
≠ Business Ledger
```

---

# 85. ImageLog Fail-open

```text
ImageLog Failure
  ↓
Business Continue
  +
Audit Alert / Reconcile
```

### LOG-03 [SHOULD]

Business Availability와 Audit Integrity를 별도 통제한다.

---

# 86. Security 전체

## FIG-DEV-71. Security Chain

```text
Authentication
   ↓
Principal
   ↓
Identity Binding
   ↓
Authorization
   ↓
Business
   ↓
Data Authorization
```

---

# 87. JWT 발급 표준

## FIG-DEV-72. JWT Issue

```text
Authenticated User
   ↓
Token Issuer
   ↓
Private Key
   ↓
RS256 Access Token
```

### [AS-IS]

PDMG `pdmg-jwt`는 RS256 발급 경로가 분석된다.

---

# 88. JWT 검증 표준

## FIG-DEV-73. JWT Verify

```text
Bearer
 ↓
Signature
 ↓
kid / Key
 ↓
issuer / audience / exp
 ↓
Principal
 ↓
Authorization
```

### SEC-JWT-01 [MUST]

Issuer Algorithm과 Verifier Algorithm을 일치시킨다.

---

# 89. JWT Critical GAP

Current 분석:

```text
pdmg-jwt
RS256 Issue
     │
     ▼
pdmg-fw
HMAC jwt.secret Verify
```

### [CRITICAL GAP]

정합성 해결 전 Target 표준으로 승인할 수 없다.

---

# 90. JWT Key Lifecycle

## FIG-DEV-74. Key Management

```text
Approved Key Store
      ↓
Active Key
      ↓
kid
      ↓
Issuer Instances
      ↓
JWKS
      ↓
Verifier
```

### SEC-JWT-02 [MUST]

다중 Instance에서 동일 `kid`는 동일 Public Key를 의미해야 한다.

---

# 91. JWT Rotation

```text
K1 old
  ↓ overlap
K2 active
  ↓
new token uses K2
old token verifies with K1
  ↓
K1 retire after safe window
```

---

# 92. Refresh Token

## FIG-DEV-75. Refresh Token

```text
Random Refresh
   ├─ Client Plain
   └─ Hash
       ↓
      DB
```

### SEC-JWT-03 [MUST]

Refresh 원문을 서버 DB에 평문 저장하지 않는다.

---

# 93. Token Storage
## TEXT ARCHITECTURE 보완 — Client Token Storage

```text
Access / Refresh Token
       ↓
Client Storage
       ↓
XSS / Leakage Risk
       ↓
Security ADR
       ↓
Approved Storage Policy
```


### [AS-IS]

Current UI 분석에서 `sessionStorage` 사용이 확인된다.

### [RISK]

XSS 시 Access/Refresh 동시노출 가능.

### SEC-JWT-04 [TO-BE]

Client Storage 정책은 Security ADR로 확정한다.

---

# 94. SSO

## FIG-DEV-76. SSO Reference

```text
Trusted Caller
  ↓
Service Allow
  ↓
Timestamp
  ↓
HMAC
  ↓
Caller IP
  ↓
Trusted User
  ↓
Token Pair
```

### 중요

Current `mgjwa1000C1`을 일반 OIDC Callback으로 단정하지 않는다.

---

# 95. Identity Binding

## FIG-DEV-77. JWT Subject / Header

```text
JWT Subject
   ↓
Trusted Principal
   ↓
Business User Context
   ↓
Header User
```

### [SECURITY GAP]

Current `ssoId ↔ optr_eno` 자동 Binding은 명확한 검증이 필요하다.

---

# 96. Authorization

### SEC-AUTHZ-01 [MUST]

```text
Authentication 성공
≠ Authorization 성공
```

업무/데이터 권한을 별도 검증한다.

---

# 97. Session 표준

## FIG-DEV-78. Session / JWT

```text
JWT
= Request Authentication Candidate

Refresh State
= Server State

HttpSession [if used]
= Separate Runtime State
```

### 원칙

```text
Stateless
```

라는 단어만으로 HA/DR 설계를 끝내지 않는다.

---

# 98. Session HA

```text
Session Used?
 ├─ NO → JWT/Refresh/Key HA
 └─ YES
      ↓
  Replication / Shared Store
      ↓
  Failover Test
```

---

# 99. File Upload Security

## FIG-DEV-79. Upload

```text
Upload
  ↓
AuthZ
  ↓
Size / Type
  ↓
Virus / Integrity [policy]
  ↓
Temporary Store
  ↓
Business Process
  ↓
Archive / Delete
```

---

# 100. Security Coding

### SEC-CODE-01 [MUST]

다음 방어를 적용한다.

```text
Input Validation
Output Encoding
SQL Parameter Binding
Secret Protection
Path Traversal Prevention
Safe Deserialization
Access Control
```

---

# 101. Dependency / Library 표준

## FIG-DEV-80. Dependency Governance

```text
Library Request
  ↓
Approved Version?
  ↓
Security / License?
  ↓
Compatibility?
  ↓
Build Lock
```

### DEP-01 [MUST]

Critical Dependency Version은 Build에서 재현 가능해야 한다.

---

# 102. Test Architecture

## FIG-DEV-81. Test Layers

```text
Static
 ↓
Architecture
 ↓
Unit
 ↓
Contract
 ↓
Integration
 ↓
Security
 ↓
Performance
 ↓
Failure / DR
 ↓
Runtime Evidence
```

---

# 103. Unit Test
## TEXT ARCHITECTURE 보완 — Unit Test Boundary

```text
Business Service / Rule
      ↓
Mock / Stub External Boundary
      ↓
Deterministic Unit Test
      ↓
PASS / FAIL
```


### TEST-01 [MUST]

Business Service/Rule은 가능한 범위에서 외부 Runtime 없이 단위검증 가능해야 한다.

---

# 104. Architecture Test

## FIG-DEV-82. Architecture Tests

```text
Package Rule
ServiceId Rule
Dependency Rule
Mapper Rule
TX Rule
Security Rule
```

---

# 105. Contract Test

```text
Header
DTO
Success Envelope
Error Envelope
Interface Schema
```

---

# 106. Transaction Test

## FIG-DEV-83. TX Test

```text
Normal Commit
Business Rollback
Runtime Exception
Timeout
Late Commit
Nested REQUIRED
TCF OFF
```

---

# 107. Timeout Test

```text
Slow SQL
Slow External
Queue Full
Hikari Wait
Worker Deadline
```

---

# 108. Security Test

```text
Valid Token
Expired
Wrong Signature
Wrong kid
Revoked
Identity Mismatch
Direct WAS
Sensitive Log
```

---

# 109. Performance Test

## FIG-DEV-84. Performance Scenarios

```text
Normal
Peak
Stress
Soak
DB Slow
External Slow
Node Down
```

---

# 110. Code Review 표준

## FIG-DEV-85. Review

```text
Merge Request
  ↓
Functional Review
  ↓
Architecture Review
  ↓
Security Review
  ↓
Test Evidence
  ↓
Approve / Rework
```

---

# 111. Code Review Checklist

```text
Layer Dependency
ServiceId
DTO / Validation
TX Boundary
Timeout
SQL
Error
Logging
Security
Test
Traceability
```

---

# 112. 품질 금지패턴

## FIG-DEV-86. Anti-pattern

```text
Controller → DAO
Handler → DAO
Direct Cross-System DB
Blind Retry
Long Remote Call inside TX
Raw Token Log
Hardcoded Secret
Unknown ServiceId
Unbounded Query
Runtime Manual DDL
```

---

# 113. Build / CI 표준

## FIG-DEV-87. CI Pipeline

```text
Commit
 ↓
Compile
 ↓
Static Check
 ↓
Architecture Rule
 ↓
Unit Test
 ↓
Contract Test
 ↓
Security Scan
 ↓
Package
 ↓
Artifact Hash
```

---

# 114. CI Gate
## TEXT ARCHITECTURE 보완 — CI Gate

```text
Source
 ↓
Architecture Rule
 ↓
Unit / Contract / Security Test
 ↓
Critical Fail?
 ├─ YES → STOP
 └─ NO  → Artifact
```


### CI-01 [MUST]

Critical Architecture Rule Fail 시 Artifact Promotion을 차단한다.

---

# 115. Artifact 표준

## FIG-DEV-88. Artifact Identity

```text
Source Commit
  ↓
Build ID
  ↓
Artifact
  ↓
Hash
```

### CI-02 [MUST]

배포 Artifact는 Source Commit으로 역추적 가능해야 한다.

---

# 116. Configuration as Code

## FIG-DEV-89. Config Governance

```text
Config Template
  ↓
Version Control
  ↓
Environment Overlay
  ↓
Approval
  ↓
Deploy
  ↓
Runtime Drift Check
```

---

# 117. Secret Separation

```text
Source / Artifact
  │
  └─ Secret Reference
          ↓
     Secret Store / Protected Delivery
```

---

# 118. Deployment 표준

## FIG-DEV-90. Deployment

```text
Artifact
  ↓
DeploymentId
  ↓
Environment
  ↓
Host
  ↓
JVM
  ↓
Context / WAR
```

---

# 119. Deployment Manifest

```yaml
deployment:
  deploymentId:
  sourceCommit:
  buildId:
  artifact:
  artifactHash:
  environment:
  host:
  jvm:
  context:
  configVersion:
  deployedAt:
  rollbackArtifact:
```

---

# 120. Deployment 후 Verification

## FIG-DEV-91. Post-deploy

```text
Deploy
 ↓
Process Health
 ↓
Version
 ↓
Config
 ↓
Key
 ↓
Smoke ServiceId
 ↓
DB / External
 ↓
Monitoring
 ↓
Evidence
```

---

# 121. Rollback 표준

```text
Failure
  ↓
Rollback Decision
  ↓
Previous Artifact
  +
Compatible Config
  ↓
Verify
```

DB Schema/Event Contract 변경이 있으면 단순 WAR rollback만으로 충분하지 않을 수 있다.

---

# 122. OM / Observability 개발표준

## FIG-DEV-92. Operable by Design

```text
ServiceId
  ↓
Metric
  ↓
Log
  ↓
Trace
  ↓
Alert
  ↓
Runbook
```

### OPS-DEV-01 [MUST]

Critical Service는 운영 관측포인트를 개발단계에서 설계한다.

---

# 123. Service Metric 표준

```text
TPS
p95
p99
Error
Timeout
Overload
```

---

# 124. Resource Metric 표준

```text
Tomcat Busy
Worker Active / Queue
Hikari Active / Pending
DB Session / Wait
```

---

# 125. Security Metric 표준

```text
Login Fail
JWT Verify Fail
Unknown kid
Authorization Denied
Identity Mismatch
```

---

# 126. Alert / Runbook 연결

## FIG-DEV-93. Operability Contract

```text
Alert
  ↓
Owner
  ↓
Runbook
  ↓
Recovery
  ↓
Evidence
```

---

# 127. Architecture Traceability 표준

## FIG-DEV-94. Full Trace

```text
Requirement
 ↓
Principle / ADR
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
Commit
 ↓
Artifact
 ↓
Deployment
 ↓
GUID
 ↓
Runtime Evidence
```

---

# 128. Reverse Trace

```text
Error / SQL / Table
   ↑
ServiceId
   ↑
Source
   ↑
Architecture
   ↑
Requirement
```

---

# 129. Source Map 최소 항목

```yaml
service:
  serviceId:
  programId:
  handler:
  facade:
  service:
  dao:
  mapper:
  sqlIds:
  tables:
  artifact:
  deployment:
  evidence:
```

---

# 130. Architecture as Code

## FIG-DEV-95. Principle → CI

```text
Architecture Principle
    ↓
Rule
    ↓
Scanner
    ↓
Test
    ↓
CI Gate
```

---

# 131. Mandatory Rule Catalog

```text
R-NAMING-AXIS
R-SERVICEID-FORMAT
R-SERVICEID-UNIQUE
R-HANDLER-BRANCH
R-HANDLER-NO-DAO
R-CONTROLLER-NO-DAO
R-DAO-MAPPER
R-SQL-TABLE-TRACE
R-TX-OWNER
R-TIMEOUT-HIERARCHY
R-RETRY-IDEMPOTENCY
R-JWT-ALGORITHM
R-JWT-KEY-CONSISTENCY
R-IDENTITY-BINDING
R-SENSITIVE-LOG
R-DEPLOYMENT-MANIFEST
R-RUNTIME-EVIDENCE
```

---

# 132. Rule → Validation
## TEXT ARCHITECTURE 보완 — Rule Validation Mapping

```text
Architecture Rule
      ↓
Validation Method
      ├─ Source Scan
      ├─ Arch Test
      ├─ Config Check
      ├─ Integration Test
      └─ Runtime Evidence
      ↓
PASS / FAIL / GAP
```


| Rule | 검증방법 |
|---|---|
| Naming | Source Scan |
| ServiceId Format/Unique | Registry Scan |
| Handler Branch | Source/AST |
| Layer Dependency | ArchUnit/Dependency Scan |
| DAO/Mapper | Namespace Scan |
| SQL/Table | SQL Parser |
| TX Owner | Source + Integration Test |
| Timeout | Config + Runtime Test |
| JWT | Security Integration Test |
| Sensitive Log | Static/Runtime Scan |
| Deployment | Manifest |
| Runtime Evidence | Evidence Manifest |

---

# 133. 개발완료와 Architecture 완료

## FIG-DEV-96. Completion Difference

```text
Code Complete
   ↓
Unit Test PASS
```

만으로 끝나지 않는다.

```text
Code
+
Architecture Rule
+
Contract Test
+
Security
+
Deployment Trace
+
Runtime Evidence
=
Architecture-compliant Delivery
```

---

# 134. 개발 Definition of Done

## FIG-DEV-97. Developer DoD

```text
Naming PASS
 ↓
ServiceId PASS
 ↓
Layer Dependency PASS
 ↓
DTO / Validation PASS
 ↓
TX / Timeout PASS
 ↓
SQL / Mapper PASS
 ↓
Error / Logging PASS
 ↓
Security PASS
 ↓
Test PASS
 ↓
Traceability PASS
 ↓
CI Gate PASS
```

---

# 135. 신규 Online Service 개발 Route

## FIG-DEV-98. New Service Route

```text
Requirement
  ↓
Business Classification
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
Error / Logging
  ↓
Test
  ↓
Runtime Evidence
```

---

# 136. 신규 ServiceId Checklist

```text
[ ] Naming
[ ] Unique
[ ] Handler registration
[ ] Handler branch
[ ] UI/API catalog
[ ] DTO
[ ] Authorization
[ ] TX
[ ] Timeout
[ ] Error
[ ] Log
[ ] Test
[ ] Monitoring
[ ] Trace
```

---

# 137. 신규 Data Access Checklist

```text
[ ] Own/Approved Data?
[ ] DAO?
[ ] Mapper namespace?
[ ] SqlId?
[ ] Bound parameters?
[ ] Pagination?
[ ] Query timeout?
[ ] Table trace?
[ ] SQL monitoring?
```

---

# 138. 신규 Interface Checklist

```text
[ ] InterfaceId
[ ] Producer
[ ] Consumer
[ ] Purpose
[ ] Type
[ ] Sync/Async
[ ] Schema
[ ] Security
[ ] Timeout
[ ] Retry
[ ] Idempotency
[ ] Error
[ ] Recovery
[ ] Trace
[ ] Owner
```

---

# 139. 신규 Batch Checklist

```text
[ ] JobId
[ ] Business Date
[ ] Schedule
[ ] Dependency
[ ] Restart
[ ] Reconciliation
[ ] Resource Isolation
[ ] Monitoring
[ ] Evidence
```

---

# 140. 신규 Security 기능 Checklist

```text
[ ] Principal
[ ] Authorization
[ ] Key Source
[ ] Algorithm
[ ] kid
[ ] Rotation
[ ] Revocation
[ ] Audit
[ ] Sensitive Log
[ ] Failure Test
```

---

# 141. 코드리뷰용 Architecture Checklist

## FIG-DEV-99. Review Gate

```text
Source Change
   ↓
Architecture Impact?
   ├─ NO → Normal Review
   └─ YES
        ↓
     Architecture Review
        ↓
     Rule / Test / ADR
```

---

# 142. Architecture 영향 변경

다음은 Architecture Review 후보:

```text
Module Dependency
Package Boundary
ServiceId
Transaction Boundary
Timeout
Interface Type
DB Ownership
Security Algorithm/Key
Deployment Topology
HA/DR
Runtime Config
```

---

# 143. Change Management

## FIG-DEV-100. Change Closed Loop

```text
Change
  ↓
Impact
  ↓
Source / Config
  ↓
Rule / Test
  ↓
Deploy
  ↓
Runtime Evidence
  ↓
Drift?
  ↓
Baseline Update
```

---

# 144. Exception Governance

```text
Standard
  ↓
Exception Required
  ↓
ADR
  ↓
Compensating Control
  ↓
Expiry
  ↓
Review
```

---

# 145. 개발표준과 01~10장 Trace

## FIG-DEV-101. Standard Derivation

```text
01 VISION
  ↓ principles
02 BIG PICTURE
  ↓ responsibility
03 LOGICAL
  ↓ layer / node
04 PHYSICAL
  ↓ runtime resource
05 MECHANISM
  ↓ message / TX / security
06 RUNTIME
  ↓ thread / failure / evidence
07 CLOSED LOOP
  ↓ rule / gate
08 PDMG SOURCE
  ↓ AS-IS pattern
09 OPERATIONS
  ↓ operability
10 BASELINE
  ↓ naming / trace / release
11 DEVELOPMENT STANDARD
  ↓ source implementation rules
```

---

# 146. 개발표준과 Runtime Evidence

## FIG-DEV-102. Rule Verification

```text
Development Rule
  ↓
Source
  ↓
Test
  ↓
Deployment
  ↓
Runtime
  ↓
Evidence
  ↓
PASS / DRIFT / GAP
```

---

# 147. 정상 구현 예

## FIG-DEV-103. Normal Pattern

```text
mgcoa9001S0
   ↓
mgcoa9001Handler
   ↓
mgcoa9001Facade
   ↓
mgcoa9001Service
   ↓
mgcoa9001DAO
   ↓
mgcoa9001-ORA.xml
   ↓
SQL
```

---

# 148. 금지 구현 예

## FIG-DEV-104. Forbidden Pattern

```text
Controller
   ├────► DAO
   └────► Other System DB DML

Handler
   └────► Mapper

Service
   └────► Raw Token Log
```

---

# 149. Timeout 금지 예

```text
Client 3s
   ↓
Worker 5s
   ↓
Query unlimited
```

이 구조는 하위 작업이 상위 응답 이후 계속될 위험이 있다.

---

# 150. Retry 금지 예

```text
Timeout
  ↓
Original Worker unknown
  ↓
Blind Retry
  ↓
Duplicate DML
```

---

# 151. Security 금지 예

```text
Header optr_eno
   ↓
Trust directly
   ↓
Business
```

또는:

```text
RS256 Issuer
   ↓
HMAC Verifier
```

---

# 152. Logging 금지 예

```text
log.info("token={}", accessToken)
log.info("password={}", password)
```

금지한다.

---

# 153. Config 금지 예

```text
application.yml
password: plaintext
private-key: ...
```

금지한다.

---

# 154. Runtime Manual Change 금지

```text
Operator
 ↓
Production File Edit
 ↓
Restart
```

Evidence 없는 변경을 금지한다.

---

# 155. Architecture Conformance Gate

## FIG-DEV-105. Developer Gate

```text
Source Rule
 ↓
Architecture Test
 ↓
Contract Test
 ↓
Security Test
 ↓
Build
 ↓
Artifact
 ↓
Deployment
 ↓
Runtime Smoke
 ↓
Evidence
 ↓
PASS
```

---

# 156. Critical Fail 후보

```text
Duplicate ServiceId
Handler → DAO
Cross-System DB DML
Unknown TX Owner
JWT Algorithm mismatch
Raw Secret Log
Wrong Artifact
Missing Critical Runtime Evidence
```

---

# 157. Development Standard Rule Catalog
## TEXT ARCHITECTURE 보완 — Rule Catalog

```text
Development Standard
│
├─ Environment
├─ Naming / ServiceId
├─ Layer
├─ Data / SQL
├─ TX / Timeout / Retry
├─ Error / Logging
├─ Security
├─ CI / Deployment
└─ Runtime Evidence
      ↓
Rule Catalog
```


| ID | 표준 | 수준 |
|---|---|---|
| DEV-ENV-01 | 지정 Toolchain | MUST |
| REPO-01 | Source/Build Trace | MUST |
| GIT-01 | Commit Trace | MUST |
| PKG-01 | 업무축 Package | MUST |
| SVCID-01 | ServiceId Format | MUST |
| SVCID-02 | ServiceId Unique | MUST |
| SVCID-04 | Registry/Branch 일치 | MUST |
| HANDLER-01 | Thin Handler | MUST |
| FACADE-01 | Use Case Boundary | SHOULD |
| SERVICE-01 | Business Procedure | MUST |
| MAP-01 | DAO/Namespace 일치 | MUST |
| SQL-03 | SQL→Table Trace | MUST for Critical |
| TX-01 | Runtime TX Owner | MUST |
| TIME-01 | Timeout Hierarchy | MUST |
| RETRY-02 | Retry + Idempotency | MUST |
| GUID-01 | GUID Propagation | MUST |
| LOG-02 | Sensitive Raw Log 금지 | MUST NOT |
| SEC-JWT-01 | Issuer/Verifier 정합 | MUST |
| SEC-ID-01 | Header Identity 자체신뢰 금지 | MUST NOT |
| CI-02 | Artifact Trace | MUST |
| OPS-DEV-01 | Operability by Design | MUST |

---

# 158. GAP Register
## TEXT ARCHITECTURE 보완 — Development GAP

```text
Required Standard
     ↓
Current Implementation
     ↓
Difference
     ↓
GAP
  ├─ Severity
  ├─ Owner
  ├─ Action
  ├─ ADR
  └─ Evidence
```


| ID | GAP | 개발 영향 |
|---|---|---|
| GAP-DEV-01 | ServiceId SSOT 미완료 | Naming/Trace |
| GAP-DEV-02 | UI Catalog 자동비교 미완료 | Runtime Entry |
| GAP-DEV-03 | Rule Layer Target 미확정 | Business Layer |
| GAP-DEV-04 | TCF OFF Facade Boundary 불일치 | Application |
| GAP-DEV-05 | Query Timeout 미확정 | Data/Runtime |
| GAP-DEV-06 | TX Timeout 미확정 | Transaction |
| GAP-DEV-07 | JDBC Cancel 실증 미완료 | Timeout |
| GAP-DEV-08 | Immutable Worker Context 미적용 | Thread |
| GAP-DEV-09 | Generic Error 표준 미완료 | Error |
| GAP-DEV-10 | Early Filter Error Envelope 미완료 | Error |
| GAP-DEV-11 | RS256/HMAC 정합 미완료 | Security |
| GAP-DEV-12 | JWT Key Lifecycle 미완료 | Security |
| GAP-DEV-13 | Identity Binding 미완료 | Security |
| GAP-DEV-14 | Denylist Enforcement 검증 미완료 | Security |
| GAP-DEV-15 | SqlId→Table 자동 Trace 미완료 | Data |
| GAP-DEV-16 | Deployment Manifest 자동화 미완료 | DevOps |
| GAP-DEV-17 | Runtime Evidence 자동화 미완료 | Governance |
| GAP-DEV-18 | pdmg-om 실제 구현 미확인 | Operations |

---

# 159. RISK Register
## TEXT ARCHITECTURE 보완 — Development Risk

```text
Cause
 ↓
Risk Event
 ↓
Runtime / Security / Data Impact
 ↓
Severity
 ↓
Mitigation
 ↓
Owner / Evidence
```


| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-DEV-01 | Layer Bypass | High |
| RISK-DEV-02 | Duplicate ServiceId | Critical |
| RISK-DEV-03 | Cross-System DB DML | Critical |
| RISK-DEV-04 | Long TX / Remote Call | Critical |
| RISK-DEV-05 | Timeout Late Commit | Critical |
| RISK-DEV-06 | Blind Retry Duplicate | Critical |
| RISK-DEV-07 | ThreadLocal Leak | Critical |
| RISK-DEV-08 | RS256/HMAC mismatch | Critical |
| RISK-DEV-09 | Ephemeral JWT Key | Critical |
| RISK-DEV-10 | Header Identity Spoof | Critical |
| RISK-DEV-11 | Raw Token/Secret Log | Critical |
| RISK-DEV-12 | Manual Production Change | Critical |
| RISK-DEV-13 | Artifact Trace Loss | Critical |
| RISK-DEV-14 | Runtime Evidence Missing | High |

---

# 160. ADR 후보

```text
ADR-DEV-01 Development Toolchain
ADR-DEV-02 Package / Layer Standard
ADR-DEV-03 ServiceId SSOT
ADR-DEV-04 TCF ON/OFF Business Core
ADR-DEV-05 Rule Layer
ADR-DEV-06 Transaction Ownership
ADR-DEV-07 Timeout Budget
ADR-DEV-08 Context Snapshot
ADR-DEV-09 Error Contract
ADR-DEV-10 JWT Algorithm Alignment
ADR-DEV-11 JWT Key Lifecycle
ADR-DEV-12 Identity Binding
ADR-DEV-13 Token Storage
ADR-DEV-14 Retry / Idempotency
ADR-DEV-15 Interface Contract
ADR-DEV-16 Deployment Manifest
ADR-DEV-17 Runtime Evidence
ADR-DEV-18 pdmg-om Integration
```

---

# 161. 개발자 작업 시작 Checklist

## FIG-DEV-106. Before Coding

```text
Requirement
 ↓
Business Code
 ↓
Program / ServiceId
 ↓
Architecture Pattern
 ↓
Data Owner
 ↓
Interface
 ↓
TX / Timeout
 ↓
Security
 ↓
Test Plan
 ↓
Coding Start
```

---

# 162. 개발 완료 Checklist

```text
[ ] Naming
[ ] ServiceId
[ ] Layer
[ ] DTO
[ ] Validation
[ ] Mapper/SQL
[ ] TX
[ ] Timeout
[ ] Retry
[ ] Error
[ ] Logging
[ ] Security
[ ] Test
[ ] Monitoring
[ ] Traceability
[ ] CI
```

---

# 163. Code Review Checklist

```text
[ ] Controller/Handler Thin?
[ ] Facade Boundary?
[ ] Service Business Procedure?
[ ] DAO only data?
[ ] Mapper namespace?
[ ] No direct DB bypass?
[ ] Exception propagate?
[ ] Timeout budget?
[ ] Retry safe?
[ ] Sensitive log?
[ ] JWT identity?
[ ] Evidence trace?
```

---

# 164. Release Readiness Checklist

```text
[ ] Build reproducible?
[ ] Architecture Rule PASS?
[ ] Test PASS?
[ ] Security PASS?
[ ] Artifact Hash?
[ ] Deployment Manifest?
[ ] Smoke Test?
[ ] Monitoring?
[ ] Rollback?
[ ] Runtime Evidence?
```

---

# 165. Go-Live 금지조건

## FIG-DEV-107. Go-Live Blocker

```text
Critical Rule Fail
      OR
Duplicate ServiceId
      OR
Unknown TX Owner
      OR
Security Critical GAP
      OR
Deployment Trace Missing
      OR
Rollback Missing
      OR
Critical Runtime Evidence Missing
      ↓
GO-LIVE BLOCK
```

---

# 166. 개발표준 Completion Gate

## FIG-DEV-108. Completion Gate

```text
Development Environment Defined?
   ↓ YES
Module / Package Rule?
   ↓ YES
Naming / ServiceId?
   ↓ YES
Layer Dependency?
   ↓ YES
Message / DTO / Validation?
   ↓ YES
DAO / Mapper / SQL?
   ↓ YES
TX / Timeout / Retry?
   ↓ YES
Error / Logging?
   ↓ YES
JWT / Security?
   ↓ YES
Test / Review?
   ↓ YES
CI / Deployment Trace?
   ↓ YES
Runtime Evidence?
   ↓ YES
DEVELOPMENT STANDARD PASS
```

---

# 167. 01~11 최종 Architecture Journey

## FIG-DEV-109. Final 11-Chapter Architecture

```text
01 VISION
   왜 바꾸는가?
      ↓
02 BIG PICTURE
   누가 무엇을 책임하는가?
      ↓
03 LOGICAL
   어떤 논리구조로 분리하는가?
      ↓
04 PHYSICAL
   어디에 배치하는가?
      ↓
05 MECHANISM
   어떤 규칙으로 동작하는가?
      ↓
06 RUNTIME
   실제로 어떻게 실행·실패·복구되는가?
      ↓
07 TRACEABILITY / CLOSED LOOP
   어떻게 검증하고 유지하는가?
      ↓
08 PDMG SOURCE REFERENCE
   실제 구현은 어떻게 되어 있는가?
      ↓
09 OM / DEVOPS / OBSERVABILITY
   어떻게 배포·관찰·복구하는가?
      ↓
10 INTEGRATED BASELINE
   어떻게 하나의 Baseline으로 묶는가?
      ↓
11 ARCHITECTURE DEVELOPMENT STANDARD
   개발자가 실제로 어떻게 구현해야 하는가?
```

---

# 168. 최종 Development Closed Loop

## FIG-DEV-110. Development Closed Loop

```text
Architecture Baseline
      ↓
Development Standard
      ↓
Source / Config
      ↓
Architecture Rule / Test
      ↓
Artifact
      ↓
Deployment
      ↓
Runtime Evidence
      ↓
Drift / GAP
      ↓
ADR
      ↓
New Architecture Baseline
```

---

# 169. Definition of Done
## TEXT ARCHITECTURE 보완 — Development DoD

```text
Code Complete
  ↓
Architecture Rule PASS
  ↓
Test PASS
  ↓
Security PASS
  ↓
Artifact Trace
  ↓
Deployment Verification
  ↓
Runtime Evidence
  ↓
Development DoD
```


## 169.1 개발환경 / Build
- [x] Toolchain
- [x] Repository / Git
- [x] Gradle / Module
- [x] Same Artifact
- [x] Config / Secret Separation

## 169.2 Application
- [x] Package
- [x] Naming
- [x] Program / ServiceId
- [x] Controller / Handler
- [x] Facade / Service / Rule
- [x] DTO / Validation

## 169.3 Data / Runtime
- [x] DAO / Mapper / SQL
- [x] SQL→Table
- [x] Transaction
- [x] Timeout
- [x] Retry / Idempotency

## 169.4 Common / Security
- [x] Standard Message
- [x] Header / GUID / Context
- [x] Error / Logging
- [x] JWT / SSO / Session
- [x] Security Coding

## 169.5 Supporting Runtime
- [x] Interface
- [x] File
- [x] Batch
- [x] Cache
- [x] Observability

## 169.6 Quality / Governance
- [x] Test
- [x] Code Review
- [x] CI / Artifact
- [x] Deployment
- [x] Architecture as Code
- [x] Runtime Evidence
- [x] GAP / Risk / ADR
- [x] Go-Live Blocker

**ARCHITECTURE DEVELOPMENT STANDARD 장 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. ServiceId SSOT 승인
2. TCF ON/OFF Business Core 정합
3. Rule Layer Target 결정
4. Query/TX Timeout 실제값 승인
5. JDBC Cancel 실증
6. Worker Context Snapshot 정책
7. Error Envelope 통일
8. JWT Issuer/Verifier 정합성 해결
9. JWT Key Lifecycle / Identity Binding
10. Retry/Idempotency 표준 승인
11. SqlId→Table 자동 Trace
12. Deployment Manifest 자동화
13. Runtime Evidence 자동화
14. Architecture Rule CI Gate 적용
15. pdmg-om 실제 Source/Runtime 확인

---

# 170. 장 최종 결론

## FIG-DEV-111. Final Development Standard

```text
Architecture
   ↓
Rule
   ↓
Developer
   ↓
Source
   ↓
Test
   ↓
Artifact
   ↓
Runtime
   ↓
Evidence
   ↓
Architecture
```

> **아키텍처 개발 표준은 코딩컨벤션 문서가 아니다. Architecture Principle을 Source·Config·Test·Build·Runtime까지 강제하는 구현 계약이다.**

> **개발자는 ServiceId와 Layer Boundary를 중심으로 구현하고, Transaction·Timeout·Retry·Security·Logging을 공통 Mechanism과 일관되게 사용해야 한다.**

> **개발 완료의 최종 기준은 “코드가 동작한다”가 아니라 Architecture Rule을 만족하고 Runtime Evidence로 검증 가능하다는 것이다.**
