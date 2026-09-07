# NSIGHT / PDMG 아키텍처 정의서
# 07. TRACEABILITY / EVIDENCE / ARCHITECTURE CLOSED LOOP
## Visual-First / Top-down → Bottom-up Verification 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-VISUAL-CLOSED-LOOP-07`  
> Architecture Level: **L6 — TRACEABILITY / GOVERNANCE / EVIDENCE**  
> 문서 상태: **Draft / Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_06_RUNTIME_VISUAL_FIRST_TOPDOWN_상세본.md`  
> 본 장 목적: **Architecture를 문서에서 끝내지 않고 Source/Config/Test/Runtime과 연결하여 검증 가능한 Baseline으로 전환**

---

# 0. 이 장부터 방향이 바뀐다

1~6장은 위에서 아래로 설계를 구체화했다.

```text
VISION
  ↓
BIG PICTURE
  ↓
LOGICAL
  ↓
PHYSICAL
  ↓
MECHANISM
  ↓
RUNTIME
```

7장부터는 반대로 올라간다.

```text
SOURCE / CONFIG
      ↑
TEST
      ↑
RUNTIME EVIDENCE
      ↑
TRACEABILITY
      ↑
DRIFT / GAP
      ↑
ADR
      ↑
NEW BASELINE
```

즉 전체 Architecture는 다음과 같은 왕복구조다.

```text
Top-down Design
      ↓
Runtime
      ↑
Bottom-up Evidence
```

---

# 1. VISUAL ROUTE — Closed Loop 전체를 한 장으로 보기

## FIG-CL-01. Architecture Closed Loop

```text
╔══════════════════════════════════════════════════════════════════════════════╗
║                         ARCHITECTURE CLOSED LOOP                             ║
╚══════════════════════════════════════════════════════════════════════════════╝

 [DOCUMENT]
 Vision / Big Picture / Logical / Physical / Mechanism / Runtime
        │
        ▼
 [MODEL]
 Domain / System / Node / ServiceId / Interface / Policy
        │
        ▼
 [SOURCE / CONFIG]
 Java / XML / YAML / SQL / WEB / WAS / DB / Deployment
        │
        ▼
 [TEST]
 Static / Architecture / Contract / Integration / Security / Performance
        │
        ▼
 [RUNTIME]
 Request / Thread / TX / SQL / Event / Batch / Failover
        │
        ▼
 [EVIDENCE]
 GUID / ServiceId / Log / Metric / Trace / Artifact / Deployment
        │
        ▼
 [DRIFT]
 Expected vs Actual
        │
        ▼
 [GAP / ADR]
 Fix / Accept / Exception / Change
        │
        ▼
 [NEW BASELINE]
 Document + Model + Rule + Evidence
        │
        └───────────────────────────────────────────────↺
```

### 핵심 결론

> **Architecture 문서는 시작점이고, Runtime Evidence가 완성점이며, 둘 사이의 차이를 다시 Baseline에 반영해야 Architecture가 살아 있다.**

---

# 2. Top-down과 Bottom-up의 만나는 지점

## FIG-CL-02. Two-way Architecture

```text
TOP-DOWN

Requirement
   ↓
Vision
   ↓
Big Picture
   ↓
Logical
   ↓
Physical
   ↓
Mechanism
   ↓
Runtime Design

----------------- MEETING POINT -----------------

Runtime Evidence

----------------- MEETING POINT -----------------

BOTTOM-UP

Runtime
   ↑
Deployment
   ↑
Artifact
   ↑
Source / Config
   ↑
Model
   ↑
Document
```

### 핵심

```text
설계는 위에서 아래로
검증은 아래에서 위로
```

---

# 3. Traceability의 목적

## FIG-CL-03. Why Traceability

```text
Requirement
   │
   ▼
Architecture Decision
   │
   ▼
Application / Service
   │
   ▼
Source
   │
   ▼
Runtime
   │
   ▼
Evidence
```

Traceability가 있어야 다음 질문에 답할 수 있다.

```text
이 요구사항은 어디에 구현됐는가?
이 ServiceId는 어떤 SQL을 호출하는가?
이 SQL은 어떤 Table을 사용하는가?
이 WAR는 어떤 Host에서 실행되는가?
이 장애는 어떤 Architecture Rule 위반인가?
이 변경은 어떤 ADR을 필요로 하는가?
```

---

# 4. Traceability 핵심 식별자

## FIG-CL-04. Enterprise Trace Keys

```text
Architecture
├─ Requirement ID
├─ Principle ID
├─ ADR ID
├─ System ID
├─ Node ID
└─ Component ID

Application
├─ Application Group
├─ Business Code
├─ Program ID
└─ ServiceId

Integration
└─ InterfaceId

Runtime
├─ GUID / TraceId
├─ EventId
├─ JobId
├─ FileId
└─ DeploymentId

Data
├─ Mapper Namespace
├─ SqlId
├─ Table
└─ View
```

---

# 5. ServiceId를 중심축으로 사용하는 이유

## FIG-CL-05. ServiceId Spine

```text
Requirement
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
Mapper
   ↓
SqlId
   ↓
Table / View
```

### 핵심

PDMG Current Runtime에서 ServiceId는 실제 Dispatcher/Handler 선택에 사용되므로,
Architecture Traceability의 강력한 연결축이 될 수 있다.

---

# 6. ServiceId Naming Structure

## FIG-CL-06. ServiceId Anatomy

```text
mg | co | a | 9001 | S | 0
│    │    │    │      │   │
│    │    │    │      │   └─ 거래순번
│    │    │    │      └──── 거래구분
│    │    │    └─────────── 프로그램번호
│    │    └──────────────── 기능
│    └───────────────────── 업무구분
└────────────────────────── 대구분
```

예:

```text
mgcoa9001S0
```

---

# 7. ServiceId Trace Chain

## FIG-CL-07. Runtime Trace

```text
ServiceId
  ↓
Handler Registry
  ↓
TransactionHandler
  ↓
Facade
  ↓
Service
  ↓
DAO
  ↓
Mapper
  ↓
SQL
  ↓
DB Object
```

---

# 8. Forward Trace

## FIG-CL-08. Forward Traceability

```text
Requirement
 ↓
Architecture Principle
 ↓
ADR
 ↓
Application Group
 ↓
System
 ↓
Program
 ↓
ServiceId
 ↓
Handler
 ↓
Service
 ↓
SQL
 ↓
Table
 ↓
Artifact
 ↓
Deployment
 ↓
Runtime Evidence
```

---

# 9. Reverse Trace

## FIG-CL-09. Reverse Traceability

```text
Table / SQL
 ↑
Mapper
 ↑
DAO
 ↑
Service
 ↑
Facade
 ↑
Handler
 ↑
ServiceId
 ↑
Program
 ↑
Application
 ↑
Requirement
```

### 핵심

장애 분석에서는 Reverse Trace가 특히 중요하다.

---

# 10. Requirement → Architecture

## FIG-CL-10. Requirement Projection

```text
Requirement
   │
   ▼
Architecture Requirement
   │
   ▼
Principle
   │
   ▼
Decision
   │
   ▼
Rule
```

예:

```text
"실시간 고객반응"

→ Event Architecture
→ FAST Path
→ Event Runtime 분리
→ Kafka/Consumer
→ Lag SLO
```

---

# 11. Principle → Rule

## FIG-CL-11. Principle Operationalization

```text
Principle
"Handler는 DAO를 직접 호출하지 않는다."
        │
        ▼
Architecture Rule
        │
        ▼
Static Scanner / ArchUnit
        │
        ▼
PASS / FAIL
```

---

# 12. Document → Model

## FIG-CL-12. Architecture Model

```text
Markdown / PPT
    │
    ▼
Machine-readable Model
    │
    ├─ System
    ├─ Node
    ├─ ServiceId
    ├─ Interface
    ├─ Data
    ├─ Policy
    └─ Evidence Link
```

### 목적

사람이 읽는 문서와
기계가 검증하는 Model을 분리하되 연결한다.

---

# 13. Model Entity

## FIG-CL-13. Core Entities

```text
System
Application
Business
Program
ServiceId
Handler
Facade
Service
DAO
Mapper
SqlId
Table
Interface
Node
Artifact
Deployment
RuntimeEvidence
ADR
GAP
```

---

# 14. Model Relation

## FIG-CL-14. Core Relations

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

# 15. Design Graph와 Runtime Graph

## FIG-CL-15. Two Graphs

```text
[Design Graph]

ServiceId
→ Handler
→ Facade
→ Service
→ DAO
→ SQL


[Runtime Graph]

Request
→ Dispatcher
→ Handler
→ Worker
→ TX
→ SQL
→ DB
```

### 핵심

```text
Design Relation
≠ Runtime Relation
```

---

# 16. Runtime Relation

## FIG-CL-16. Runtime Model

```text
Dispatcher
  DISPATCHES_TO
Handler

Handler
  RUNS_ON
Worker

TransactionTemplate
  STARTS
Transaction

Facade
  PARTICIPATES_IN
Transaction

DAO
  USES
Datasource
```

---

# 17. Source Baseline

## FIG-CL-17. Source Baseline

```text
Repository
  │
  ├─ Branch
  ├─ Commit
  ├─ Module
  ├─ Build File
  ├─ Source
  ├─ Config
  └─ SQL
```

Architecture 검증은 반드시 특정 Source Baseline과 연결한다.

---

# 18. PDMG Baseline Modules

```text
pdmg-ui
pdmg-jwt
pdmg-fw
pdmg-service
pdmg-om
```

### 주의

PDMG Reference Scope 밖의 Module을 자동 포함하지 않는다.

---

# 19. Source Inventory

## FIG-CL-18. Source Inventory

```text
Module
  ↓
Package
  ↓
Class
  ↓
Method
  ↓
Annotation
  ↓
Dependency
```

---

# 20. Config Inventory

## FIG-CL-19. Config Inventory

```text
application.yml
server.xml
httpd.conf
setenv.sh
log4j2.xml
build.gradle
Mapper XML
Security Config
L4 / GSLB
```

---

# 21. SQL Inventory

## FIG-CL-20. SQL Trace

```text
DAO Method
   ↓
Mapper Namespace
   ↓
SqlId
   ↓
SQL
   ↓
Table / View
```

---

# 22. SQL Parsing Rule

```text
Class 이름만 보고 Table 추정      X
DTO 이름으로 Table 추정           X
Mapper 이름으로 Table 추정        X
```

필요:

```text
Mapper XML Parser
+
SQL Parser
```

---

# 23. ServiceId Scanner

## FIG-CL-21. ServiceId Discovery

```text
Source
  │
  ├─ Handler.serviceIds()
  ├─ Controller Mapping
  ├─ Constants
  ├─ UI Catalog
  └─ Config
  │
  ▼
ServiceId Index
```

---

# 24. Handler Registration Rule

## FIG-CL-22. Registry Validation

```text
Handler
  │
  └─ serviceIds()
       ↓
Registry
       ↓
Unique?
  ├─ YES
  └─ NO → FAIL
```

---

# 25. Handler Branch Validation

## FIG-CL-23. Registered vs Executed

```text
serviceIds()
   │
   ▼
Registered IDs
   │
   │ compare
   ▼
handle() branches
```

가능한 문제:

```text
등록 O / Branch X
등록 X / Branch O
```

---

# 26. UI Catalog vs Backend

## FIG-CL-24. Catalog Drift

```text
UI Transaction Catalog
       │
       │ compare
       ▼
Backend Handler Registry
```

차이가 있으면:

```text
UI Catalog ≠ ServiceId SSOT
```

---

# 27. Package / Naming Trace

## FIG-CL-25. Naming Projection

```text
Business Classification
MG / CO / A
      │
      ├────────► Java nhnis.mg.co.a
      ├────────► Mapper rdw.mg.co.a
      └────────► ServiceId mgcoa...
```

---

# 28. Naming Rule

```text
Business Classification
      ↓
Package
      ↓
Mapper
      ↓
ServiceId
```

모두 동일한 분류축을 사용해야 한다.

---

# 29. Code Dependency Rule

## FIG-CL-26. Layer Dependency

```text
Controller
  ↓
Handler / Facade
  ↓
Service
  ↓
DAO
  ↓
Mapper
```

금지:

```text
Controller → DAO
Handler → DAO
Controller → Mapper
```

---

# 30. Framework Dependency Rule

```text
Business
→ Framework 사용

Framework
→ 특정 Business 직접 의존
```

후자는 최소화/금지한다.

---

# 31. Architecture as Code

## FIG-CL-27. Architecture Rule Pipeline

```text
Architecture Rule
   ↓
Rule Definition
   ↓
Scanner
   ↓
CI Build
   ↓
PASS / FAIL
```

---

# 32. Rule Types

```text
Naming Rule
Dependency Rule
ServiceId Rule
Interface Rule
Transaction Rule
Timeout Rule
Security Rule
Logging Rule
Deployment Rule
Runtime Evidence Rule
```

---

# 33. R-SERVICEID-FORMAT

```text
ServiceId
  ↓
Pattern Validation
  ↓
PASS / FAIL
```

---

# 34. R-SERVICEID-UNIQUE

```text
All ServiceIds
  ↓
Duplicate?
  ├─ NO  → PASS
  └─ YES → FAIL
```

---

# 35. R-HANDLER-REGISTRATION

```text
ServiceId
  ↓
Registered Handler?
  ├─ YES
  └─ NO → FAIL
```

---

# 36. R-HANDLER-BRANCH

```text
Registered ServiceId
   ↓
Handler branch exists?
   ├─ YES
   └─ NO → FAIL
```

---

# 37. R-HANDLER-NO-DAO

```text
Handler
  ↓ dependency scan
DAO / Mapper?
  ├─ NO → PASS
  └─ YES → FAIL
```

---

# 38. R-CONTROLLER-NO-DAO

```text
Controller
  ↓
DAO dependency?
  ├─ NO
  └─ YES → FAIL
```

---

# 39. R-DAO-MAPPER-MAP

```text
DAO Method
  ↓
Mapper SqlId
  ↓
Exists?
```

---

# 40. R-SQL-TABLE-TRACE

```text
SqlId
  ↓
Parsed SQL
  ↓
Referenced Objects
  ↓
Table / View Index
```

---

# 41. R-TX-OWNER

## FIG-CL-28. Transaction Rule

```text
ServiceId
  ↓
Runtime Entry
  ↓
TX Owner
  ↓
TransactionManager
  ↓
DB
```

TX Owner가 UNKNOWN이면 Gate 미통과.

---

# 42. R-TIMEOUT-POLICY

```text
ServiceId
  ↓
Client Timeout
  ↓
Server Timeout
  ↓
Worker Deadline
  ↓
Query Timeout
```

Hierarchy 위반 시 FAIL 후보.

---

# 43. R-JWT-PRIVATE-KEY

```text
Private Key
   │
   ├─ Issuer only?
   │
   └─ Validator distribution?
```

Validator에 Private Key 배포 시 FAIL.

---

# 44. R-SENSITIVE-LOG

```text
Log Statement
  ↓
Token / Password / Secret?
  ├─ NO
  └─ YES → FAIL
```

---

# 45. R-DEPLOYMENT-MAP

```text
Artifact
  ↓
Deployment Manifest
  ↓
Host / JVM / WAR
```

Mapping이 없으면 Runtime Evidence 연결이 불가능하다.

---

# 46. R-RUNTIME-EVIDENCE

```text
ServiceId
  ↓
Deployment
  ↓
Runtime Scenario
  ↓
Evidence
```

Evidence 없는 Critical Service는 Gate 미통과.

---

# 47. Test Architecture

## FIG-CL-29. Test Layers

```text
Static
  ↓
Architecture Rule
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
Failure
  ↓
Runtime Evidence
```

---

# 48. Static Test

```text
Naming
Dependency
Annotation
Forbidden API
Secret Scan
Config Pattern
```

---

# 49. Architecture Test

```text
Layer Dependency
ServiceId Registry
Package Rule
Transaction Boundary Candidate
Direct DB Access
```

---

# 50. Contract Test

```text
Request Header
DTO Schema
Success Envelope
Error Envelope
Interface Schema
```

---

# 51. Integration Test

```text
Application → DB
Application → External
JWT → Business
Event → Consumer
CDC → RDW
File → Consumer
```

---

# 52. Security Test

```text
JWT Signature
Expired Token
Wrong kid
Revoked Token
Header/JWT Identity mismatch
Direct WAS Access
Sensitive Log
```

---

# 53. Transaction Test

## FIG-CL-30. TX Tests

```text
Normal Commit
Business Exception Rollback
Checked Exception
Timeout
Late Commit Prevention
DB Error
Nested REQUIRED
```

---

# 54. Timeout Test

```text
Slow SQL
Slow External
Queue Wait
Pool Exhaustion
Worker Timeout
Client Timeout
```

---

# 55. Performance Test

```text
Normal
Peak
Stress
Soak
Node Down
DB Slow
External Slow
```

---

# 56. Failure Test

```text
AP Node Down
AP Group Down
DB Down
Kafka Down
CDC Down
External Down
Center DR
```

---

# 57. Runtime Evidence

## FIG-CL-31. Evidence Chain

```text
Source Commit
   ↓
Build ID
   ↓
Artifact Hash
   ↓
Deployment ID
   ↓
Runtime Scenario
   ↓
ServiceId
   ↓
GUID / TraceId
   ↓
Metric / Log / Result
   ↓
Evidence Hash
```

---

# 58. Evidence는 무엇이 아닌가

```text
로그 한 줄
≠ Runtime Evidence

스크린샷 한 장
≠ Runtime Evidence

테스트 결과만
≠ Deployment Evidence
```

Evidence는 반드시 Context를 가진다.

---

# 59. Evidence Manifest

```yaml
evidence:
  evidenceId:
  architectureBaseline:
  modelVersion:
  sourceCommit:
  buildId:
  artifactHash:
  deploymentId:
  environment:
  runtimeScenario:
  serviceId:
  traceId:
  startTime:
  endTime:
  result:
  metrics:
  logRefs:
  attachments:
  evidenceHash:
```

---

# 60. Artifact Trace

## FIG-CL-32. Build Trace

```text
Source Commit
  ↓
Build
  ↓
Artifact
  ↓
Hash
```

---

# 61. Deployment Trace

```text
Artifact Hash
  ↓
Deployment ID
  ↓
Environment
  ↓
Host
  ↓
JVM
  ↓
WAR
```

---

# 62. Runtime Trace

```text
Deployment
  ↓
ServiceId
  ↓
GUID
  ↓
Worker
  ↓
SQL
  ↓
Result
```

---

# 63. Release Trace

## FIG-CL-33. Release Evidence

```text
Release
├─ Source Commit
├─ Artifact Hash
├─ Deployment ID
├─ Architecture Baseline
├─ Test Result
└─ Runtime Evidence
```

---

# 64. Same Artifact Principle

```text
DEV
  │
  └─ Artifact A
        ↓ promote
TEST
  │
  └─ Artifact A
        ↓ promote
PROD
  │
  └─ Artifact A
```

환경별 Source Rebuild는 피한다.

---

# 65. Configuration Trace

```text
Artifact
+
Environment Config
=
Runtime
```

따라서 Config도 Evidence 대상이다.

---

# 66. Drift의 정의

## FIG-CL-34. Expected vs Actual

```text
Expected Architecture
      │
      │ compare
      ▼
Actual Source / Config / Runtime
      │
      ├─ MATCH
      └─ DIFFERENCE
             ↓
           DRIFT
```

---

# 67. Drift Type

```text
Document Drift
Model Drift
Source Drift
Config Drift
Deployment Drift
Runtime Drift
Security Drift
Data Drift
```

---

# 68. Document Drift

예:

```text
문서
Filter order = 1

실제
HIGHEST_PRECEDENCE + 20

→ Document Drift
```

---

# 69. Model Drift

```text
Model
ServiceId S0 registered

Source
ServiceId S0 missing

→ Model Drift
```

---

# 70. Config Drift

```text
Design
Session 60m

Actual
90m

→ Config Drift
```

---

# 71. Deployment Drift

```text
Architecture
WAR A → JVM A

Actual
WAR A → JVM B

→ Deployment Drift
```

---

# 72. Runtime Drift

```text
Design
Timeout rollback

Runtime
HTTP timeout + DB commit

→ Critical Runtime Drift
```

---

# 73. Security Drift

```text
Design
JWT principal drives user identity

Runtime
Header optr_eno trusted independently

→ Security Drift
```

---

# 74. Drift Severity

## FIG-CL-35. Severity

```text
CRITICAL
  - Security bypass
  - Data inconsistency
  - Late commit
  - DR impossible

HIGH
  - NFR violation
  - Trace loss
  - Unsupported runtime path

MEDIUM
  - Naming / documentation
  - non-critical config mismatch

LOW
  - cosmetic / metadata
```

---

# 75. GAP의 정의

```text
Required Architecture
      │
      ▼
Evidence?
  ├─ YES → conform / drift
  └─ NO  → GAP
```

또는:

```text
Target
≠
AS-IS
→ GAP
```

---

# 76. GAP vs DRIFT

## FIG-CL-36. Difference

```text
GAP
= 필요한 것이 없거나 미정

DRIFT
= 정한 것과 실제가 다름
```

---

# 77. GAP Register

최소:

```text
Gap ID
Architecture Layer
Expected
Actual
Impact
Risk
Owner
Target Date
ADR
Evidence
Status
```

---

# 78. ADR

## FIG-CL-37. ADR Lifecycle

```text
Issue / GAP / Drift
     ↓
Decision Needed
     ↓
ADR
     │
     ├─ Context
     ├─ Decision
     ├─ Alternatives
     ├─ Consequence
     ├─ Owner
     └─ Date
     ↓
Architecture Update
```

---

# 79. ADR 상태

```text
PROPOSED
ACCEPTED
REJECTED
SUPERSEDED
DEPRECATED
```

---

# 80. ADR Exception

```text
Standard
  ↓
Exception
  ↓
ADR
  ↓
Compensating Control
  ↓
Expiry / Review
```

예외는 영구 방치하지 않는다.

---

# 81. Architecture Gate 전체

## FIG-CL-38. G00 → HG90

```text
G00
Source Baseline
   ↓
G10
Document Classification
   ↓
G20
Architecture Model
   ↓
G30
Model ↔ Code Conformance
   ↓
G40
Architecture / Contract / Security Test
   ↓
G50
Runtime Evidence
   ↓
G60
Drift Detection
   ↓
G70
GAP / ADR
   ↓
G80
Approval
   ↓
HG90
Architecture Baseline Release
```

---

# 82. G00 — Source Baseline

## FIG-CL-39. Gate G00

```text
Repository
  ↓
Branch
  ↓
Commit
  ↓
Scope
  ↓
Source Baseline
```

PASS 조건:

```text
Commit fixed
Module Scope fixed
Config Scope fixed
Evidence source identified
```

---

# 83. G10 — Document Classification

```text
Document Content
  ↓
Classification
```

사용 태그:

```text
[FACT]
[CONFIRMED]
[DECISION]
[AS-IS]
[TO-BE]
[PROPOSED]
[GAP]
[CONFLICT]
[RISK]
[OPEN]
[UNKNOWN]
[DEPRECATED]
```

---

# 84. G20 — Architecture Model

```text
Document
  ↓
Machine-readable Model
  ↓
Entity / Relation / Policy
```

PASS:

```text
ServiceId
Interface
Node
Data
Policy
Trace
```

가 모델링되어야 한다.

---

# 85. G30 — Model ↔ Source

## FIG-CL-40. Conformance Scan

```text
Architecture Model
      │
      │ compare
      ▼
Source / Config
      │
      ▼
Conformance Result
```

---

# 86. G40 — Architecture Test

```text
Rule
  ↓
Test
  ↓
PASS / FAIL
```

Critical Rule FAIL이면 Release 진행 불가.

---

# 87. G50 — Runtime Evidence

## FIG-CL-41. Runtime Gate

```text
Exact Artifact
   ↓
Exact Deployment
   ↓
Scenario
   ↓
Runtime Trace
   ↓
Expected Result
   ↓
Evidence
```

---

# 88. G60 — Drift

```text
Expected
   ↓ compare
Actual
   ↓
Drift
```

Critical Drift는 0을 목표로 한다.

---

# 89. G70 — GAP / ADR

```text
Gap / Drift
   ↓
Fix?
Accept?
Exception?
Change Architecture?
   ↓
ADR
```

---

# 90. G80 — Approval

승인대상:

```text
Architecture Owner
Business Owner
Security
Operations
Data
Project / PMO
```

실제 RACI는 프로젝트 승인체계에 맞춰 확정한다.

---

# 91. HG90 — Baseline Release

## FIG-CL-42. Final Gate

```text
Document
+
Model
+
Source Baseline
+
Rule/Test PASS
+
Runtime Evidence
+
Drift Review
+
ADR
+
Human Approval
        │
        ▼
Architecture Baseline Release
```

---

# 92. HG90 금지

```text
문서 완료만으로 Release       X
테스트 통과만으로 Release     X
Runtime Evidence 없이 Release X
Critical Drift 남은 채 Release X
```

---

# 93. Closed Loop Workspace

## FIG-CL-43. Workspace Structure

```text
00-IN
  │
10-DOCUMENT
  │
20-MODEL
  │
30-CODE / CONFORMANCE
  │
40-TEST
  │
50-RUNTIME-EVIDENCE
  │
60-DRIFT
  │
70-GAP-ADR
  │
80-GATE
  │
90-OUT
```

---

# 94. 00-IN

```text
Source
PPT
Requirements
Design
Config
Runtime Inputs
```

---

# 95. 10-DOCUMENT

```text
CURRENT-ARCHITECTURE.md
Vision
Big Picture
Logical
Physical
Mechanism
Runtime
```

---

# 96. 20-MODEL

```text
reference-baseline.json
serviceid-index.json
interface-index.json
deployment-model.json
```

---

# 97. 30-CODE / CONFORMANCE

```text
reference-rules.json
scanner
source-index
mapper-index
dependency-index
```

---

# 98. 40-TEST

```text
architecture-test
contract-test
security-test
transaction-test
timeout-test
integration-test
```

---

# 99. 50-RUNTIME-EVIDENCE

```text
scenario
deployment
metric
log
trace
test result
evidence manifest
```

---

# 100. 60-DRIFT

```text
document-drift
model-drift
source-drift
config-drift
runtime-drift
```

---

# 101. 70-GAP-ADR

```text
GAP register
Risk register
ADR
Exception
Action
```

---

# 102. 80-GATE

```text
Gate Result
Approval
Waiver
Condition
```

---

# 103. 90-OUT

```text
Released Baseline
Model
Rule
Evidence Index
ADR Pack
```

---

# 104. Evidence Classification

## FIG-CL-44. Evidence Hierarchy

```text
Runtime Evidence
   ↑
Config / Source
   ↑
Official Baseline
   ↑
ADR
   ↑
Detailed Design
   ↑
Requirement / Interview
   ↑
Guide / Draft
```

### 원칙

실제 구현판정은 가능한 한:

```text
Source / Config / Runtime
```

을 우선한다.

---

# 105. Evidence Tagging

```text
[FACT]
실제 Source/Config/Runtime에서 확인

[CONFIRMED]
승인된 Baseline

[DECISION]
공식 결정

[AS-IS]
현재 구현

[TO-BE]
목표

[PROPOSED]
제안

[GAP]
부족/미정

[CONFLICT]
서로 다른 근거

[UNKNOWN]
증거 없음
```

---

# 106. Fact vs Target

## FIG-CL-45. AS-IS / TO-BE Separation

```text
PDMG Source
     │
     ▼
AS-IS
     │
     │ compare
     ▼
NSIGHT Target
     │
     ▼
GAP
```

### 금지

```text
PDMG Source
→ NSIGHT Target 자동 승격
```

---

# 107. Old Baseline Handling

```text
2026-03 Baseline
  ↓
[BASELINE-2026-03]
```

최신값으로 자동 치환하지 않는다.

---

# 108. Conflict Handling

## FIG-CL-46. Conflict Resolution

```text
Source A
CDC <= 30s
      │
      ├─ CONFLICT
      │
Source B
CDC <= 3s
      │
      ▼
Owner / Approval / Measurement Review
      │
      ▼
Decision / ADR
```

---

# 109. Unknown Handling

```text
Evidence 없음
   ↓
UNKNOWN
```

빈칸으로 숨기지 않는다.

---

# 110. Architecture Review Board Input

## FIG-CL-47. Review Package

```text
Architecture Change
      │
      ▼
Review Pack
├─ Context
├─ Diagram
├─ Model Diff
├─ Rule Impact
├─ Risk
├─ Test
├─ Runtime Evidence
└─ ADR
```

---

# 111. Change Impact Trace

```text
Change
  ↓
ServiceId
  ↓
Handler / Service
  ↓
Interface
  ↓
SQL / Table
  ↓
Artifact
  ↓
Deployment
  ↓
Test
```

---

# 112. ServiceId Change Impact

## FIG-CL-48. Service Change

```text
ServiceId Change
   │
   ├─ UI Catalog
   ├─ Handler Registry
   ├─ Header
   ├─ Logging
   ├─ Interface
   ├─ Test
   └─ Monitoring
```

---

# 113. Table Change Impact

```text
Table Change
  ↑
SqlId
  ↑
DAO
  ↑
Service
  ↑
ServiceId
  ↑
Application
```

---

# 114. Interface Change Impact

```text
Interface Contract
  ↓
Producer
  ↓
Consumer
  ↓
Security
  ↓
Timeout
  ↓
Monitoring
  ↓
Recovery
```

---

# 115. Deployment Change Impact

```text
WAR / JVM Change
   ↓
Host
   ↓
Capacity
   ↓
HA
   ↓
Monitoring
   ↓
DR
```

---

# 116. Runtime Drift Alert

## FIG-CL-49. Automated Drift

```text
Runtime Metric / Config
       │
       ▼
Expected Baseline
       │
       ▼ compare
       │
       ├─ match
       └─ drift
            ↓
          Alert
```

---

# 117. Configuration Drift Examples

```text
Session 60m → Actual 90m
Timeout 4s → Actual 5s
Hikari 80 → Actual 200
Filter Order changed
JWT Key Source changed
```

---

# 118. Architecture Dashboard

## FIG-CL-50. Governance Dashboard

```text
Architecture Health
│
├─ Rule Pass %
├─ Critical Drift
├─ Open GAP
├─ ADR Pending
├─ Runtime Evidence Coverage
├─ ServiceId Trace Coverage
├─ Interface Trace Coverage
└─ Deployment Trace Coverage
```

---

# 119. Coverage Metrics

```text
ServiceId Trace Coverage
= traced ServiceIds / total ServiceIds

Runtime Evidence Coverage
= evidenced critical scenarios / required scenarios

Deployment Trace Coverage
= mapped artifacts / deployed artifacts
```

---

# 120. Quality Gate Metrics

```text
Critical Rule Fail = 0

Critical Drift = 0

Unknown Critical Node = 0 target

Runtime Evidence Coverage
= 100% for critical services
```

---

# 121. PDMG Current Traceability Gaps

## FIG-CL-51. PDMG Gap Map

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
  ↓
SQL
  ↓
Table
  ↓
Artifact
  ↓
Deployment
  ↓
Runtime Evidence
```

현재 후반부:

```text
Table → Artifact → Deployment → Evidence
```

전수 자동화가 주요 과제다.

---

# 122. ServiceId Full Trace Target

## FIG-CL-52. Target Trace

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
Mapper
  ↓
SqlId
  ↓
Table / View
  ↓
WAR
  ↓
JVM
  ↓
Host
  ↓
GUID
  ↓
Runtime Evidence
```

---

# 123. Runtime Evidence → ServiceId

```text
GUID
  ↓
ServiceId
  ↓
Deployment
  ↓
Artifact
  ↓
Commit
```

---

# 124. Runtime Evidence → Architecture

```text
Runtime Evidence
  ↑
Scenario
  ↑
NFR
  ↑
Architecture Principle
```

---

# 125. Failure Evidence Trace

## FIG-CL-53. Failure Proof

```text
Failure Scenario
  ↓
Trigger
  ↓
Detection
  ↓
Failover / Recovery
  ↓
SLO
  ↓
Evidence
```

---

# 126. DR Evidence Trace

```text
DR Scenario
  ↓
Main Failure
  ↓
GSLB Route
  ↓
DR App
  ↓
DR DB
  ↓
Business Validation
  ↓
RTO/RPO Evidence
```

---

# 127. Backup Evidence Trace

```text
Backup
  ↓
Restore
  ↓
Application Start
  ↓
Data Validation
  ↓
Evidence
```

---

# 128. Security Evidence Trace

```text
Token
  ↓
Verify
  ↓
Principal
  ↓
Authorization
  ↓
Business
  ↓
Audit
```

---

# 129. Performance Evidence Trace

```text
Load Scenario
  ↓
TPS
  ↓
p95
  ↓
Thread
  ↓
Worker
  ↓
Hikari
  ↓
DB
```

---

# 130. Evidence Naming

권장 패턴:

```text
EV-{Scenario}-{Date}-{Sequence}
```

실제 최종 Naming은 프로젝트 표준으로 승인한다.

---

# 131. Evidence Storage

```text
Evidence Repository
│
├─ Runtime
├─ Performance
├─ Security
├─ HA
├─ DR
├─ Backup
└─ Screenshots / Reports
```

---

# 132. Evidence Integrity

## FIG-CL-54. Evidence Hash

```text
Evidence File
   ↓
Hash
   ↓
Manifest
   ↓
Release Record
```

---

# 133. Architecture Baseline Package

## FIG-CL-55. Baseline Release Pack

```text
Architecture Baseline
│
├─ 01 VISION
├─ 02 BIG PICTURE
├─ 03 LOGICAL
├─ 04 PHYSICAL
├─ 05 MECHANISM
├─ 06 RUNTIME
├─ 07 TRACEABILITY / CLOSED LOOP
├─ Machine-readable Model
├─ Rules
├─ Test Result
├─ Evidence Index
├─ Drift Report
├─ GAP Register
└─ ADR Pack
```

---

# 134. Baseline Version

```text
Architecture Baseline
= Versioned

Example
v1.0
v1.1
v2.0
```

Version Rule은 별도 Change Management 기준으로 확정한다.

---

# 135. Baseline Diff

## FIG-CL-56. Baseline Change

```text
Baseline N
   │
   ▼
Change
   │
   ▼
Model Diff
   │
   ▼
Rule / Test
   │
   ▼
Runtime Evidence
   │
   ▼
Baseline N+1
```

---

# 136. Architecture Release Candidate

```text
RC
│
├─ Document Complete
├─ Model Complete
├─ Rule Pass
├─ Test Pass
├─ Evidence Complete
├─ Drift Reviewed
└─ ADR Approved
```

---

# 137. Architecture Release

```text
RC
  ↓
Human Approval
  ↓
HG90
  ↓
Released Baseline
```

---

# 138. Release 후 Drift

```text
Released Baseline
  ↓
Runtime Change
  ↓
Drift Detection
  ↓
Change Review
```

Architecture는 Release 후에도 계속 감시한다.

---

# 139. Operational Architecture

## FIG-CL-57. Architecture as Operation

```text
Architecture
  ↓
Rule
  ↓
CI
  ↓
Deployment
  ↓
Monitoring
  ↓
Drift
  ↓
Change
```

---

# 140. Architecture as Code

```text
Architecture Rule
+
Scanner
+
CI Gate
```

---

# 141. Architecture as Data

```text
Architecture Model
+
Inventory
+
Trace Index
```

---

# 142. Architecture as Evidence

```text
Runtime Evidence
+
Deployment Evidence
+
Test Evidence
```

---

# 143. Architecture as Governance

```text
Gate
+
ADR
+
Approval
```

---

# 144. End-to-End Governance Chain

## FIG-CL-58. Governance Chain

```text
Requirement
  ↓
Decision
  ↓
Rule
  ↓
Implementation
  ↓
Test
  ↓
Runtime
  ↓
Evidence
  ↓
Drift
  ↓
ADR
```

---

# 145. Architecture Owner Responsibility

```text
Define
Approve
Review
Measure
Resolve Drift
Maintain Baseline
```

---

# 146. Developer Responsibility

```text
Follow Rule
Maintain Trace
Write Test
Avoid Unauthorized Exception
```

---

# 147. Operations Responsibility

```text
Maintain Runtime Evidence
Detect Drift
Execute Runbook
Feed Incident Back to Architecture
```

---

# 148. Security Responsibility

```text
Security Rule
Key/Secret
Identity
Authorization
Audit
Security Evidence
```

---

# 149. Data Responsibility

```text
Data Ownership
SQL/Table Trace
Quality
Lineage
CDC/ETL Evidence
```

---

# 150. PMO / Governance Responsibility

```text
Gate
Approval
Status
Risk
Baseline Release
```

---

# 151. RACI 후보

| Activity | Architect | Dev | Ops | Security | Data | PMO |
|---|---|---|---|---|---|---|
| Architecture Rule | A/R | C | C | C | C | I |
| Source Conformance | A | R | I | C | C | I |
| Runtime Evidence | A | C | R | C | C | I |
| Security Evidence | C | C | C | A/R | I | I |
| Data Trace | C | C | I | I | A/R | I |
| Baseline Release | A/R | C | C | C | C | A/C |

실제 조직 기준으로 재조정한다.

---

# 152. Audit Trail

## FIG-CL-59. Architecture Audit

```text
Who changed?
What changed?
Why changed?
Which ADR?
Which Source Commit?
Which Artifact?
Which Deployment?
Which Evidence?
```

---

# 153. Exception Audit

```text
Exception
  ↓
ADR
  ↓
Owner
  ↓
Expiry
  ↓
Review
```

---

# 154. Architecture Debt

## FIG-CL-60. Debt Lifecycle

```text
Accepted GAP
  ↓
Architecture Debt
  ↓
Owner
  ↓
Due Date
  ↓
Risk
  ↓
Close / Extend
```

---

# 155. Unknown Debt

```text
UNKNOWN
  ↓
Evidence Collection Task
```

UNKNOWN을 장기 방치하지 않는다.

---

# 156. Drift Debt

```text
Known Drift
  ↓
Accepted temporarily
  ↓
ADR / Debt
```

---

# 157. Architecture KPI

```text
Critical Drift Count
Open Critical GAP
Rule Pass Rate
Evidence Coverage
Trace Coverage
ADR Lead Time
Recovery Test Coverage
```

---

# 158. Architecture Dashboard Sample

## FIG-CL-61. Health Dashboard

```text
Architecture Health
┌─────────────────────────────────────┐
│ Critical Drift          0           │
│ Critical GAP            2           │
│ Rule Pass               97%         │
│ Service Trace           85%         │
│ Runtime Evidence        70%         │
│ DR Test Coverage        60%         │
└─────────────────────────────────────┘
```

수치는 예시 구조이며 실제값이 아니다.

---

# 159. PDMG Closed Loop Example

## FIG-CL-62. ServiceId Example

```text
mgcoa9001S0
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
   ↓
SQL
   ↓
RDW Table
   ↓
WAR
   ↓
JVM
   ↓
Runtime GUID
   ↓
Evidence
```

---

# 160. Timeout Closed Loop Example

## FIG-CL-63. Timeout Example

```text
Architecture Rule
DB Query < Worker < Client
       ↓
Config Scan
       ↓
PDMG Worker = 5000ms
Query Timeout = UNKNOWN
       ↓
GAP
       ↓
Integration Test
       ↓
ADR / Baseline
```

---

# 161. Security Closed Loop Example

## FIG-CL-64. Identity Binding

```text
Target
JWT Principal → Business Identity
       ↓
Source
ssoId / optr_eno mapping unclear
       ↓
GAP
       ↓
Security Test
       ↓
ADR
```

---

# 162. DR Closed Loop Example

## FIG-CL-65. DR

```text
Target
RTO/RPO
  ↓
Physical
Main / DR
  ↓
Runtime
Failover Test
  ↓
Evidence
  ↓
PASS / GAP
```

---

# 163. CDC Closed Loop Example

## FIG-CL-66. CDC SLA

```text
Baseline A 30s
Baseline B 3s
       ↓
CONFLICT
       ↓
Measurement Definition
       ↓
Runtime Test
       ↓
Owner Decision
       ↓
ADR / New Baseline
```

---

# 164. Architecture Completion Definition

## FIG-CL-67. What "Complete" Means

```text
Document complete?
        │
        ▼
Model complete?
        │
        ▼
Source conformance?
        │
        ▼
Test pass?
        │
        ▼
Runtime evidence?
        │
        ▼
Drift reviewed?
        │
        ▼
ADR approved?
        │
        ▼
Baseline released?
```

---

# 165. 문서 완료와 Architecture 완료의 차이

```text
문서가 있음
=
Documentation Complete

문서 + Model + Code + Test + Runtime + Evidence + Gate
=
Architecture Complete
```

---

# 166. Architecture Never Final

```text
Baseline
  ↓
Change
  ↓
Drift
  ↓
Review
  ↓
New Baseline
```

Architecture는 정적 산출물이 아니라 지속적으로 운영되는 통제체계다.

---

# 167. CONFIRMED / WORKING BASELINE

```text
[CONFIRMED / WORKING BASELINE]

- Top-down Design → Bottom-up Evidence 구조
- ServiceId가 PDMG Runtime Trace 핵심축
- Document / Model / Code / Test / Runtime / Drift / ADR Closed Loop
- G00→G10→G20→G30→G40→G50→G60→G70→G80→HG90 Gate
- Source Commit / Artifact / Deployment / Runtime Evidence 연결 필요
- Architecture Rule을 CI/Test로 검증
- AS-IS / TO-BE / GAP / DRIFT 구분
```

---

# 168. OPEN

```text
[OPEN-CL-01]
Architecture Model 최종 Schema

[OPEN-CL-02]
ServiceId 전수 Source Index

[OPEN-CL-03]
Mapper/SQL/Table 자동 Parser

[OPEN-CL-04]
Artifact→Deployment 전수 Mapping

[OPEN-CL-05]
Runtime Evidence Repository

[OPEN-CL-06]
Evidence Hash / Integrity 표준

[OPEN-CL-07]
Gate Owner / Approval RACI

[OPEN-CL-08]
Architecture KPI 실제 목표값

[OPEN-CL-09]
Architecture Dashboard Tool

[OPEN-CL-10]
Baseline Versioning Rule
```

---

# 169. GAP Register

| ID | GAP | 영향 |
|---|---|---|
| GAP-CL-01 | ServiceId 전수 인덱스 자동화 미완료 | Traceability |
| GAP-CL-02 | Handler→SQL→Table 자동 Trace 미완료 | Data Trace |
| GAP-CL-03 | Artifact→Host/JVM Mapping 미완료 | Deployment |
| GAP-CL-04 | Runtime Evidence Manifest 자동화 미완료 | Evidence |
| GAP-CL-05 | Architecture Model Schema 미확정 | Model |
| GAP-CL-06 | CI Architecture Rule 전수적용 미완료 | Governance |
| GAP-CL-07 | Runtime Drift 자동탐지 미완료 | Drift |
| GAP-CL-08 | Gate RACI 미확정 | Approval |
| GAP-CL-09 | Evidence Repository 미확정 | Evidence |
| GAP-CL-10 | Architecture KPI 미확정 | Governance |
| GAP-CL-11 | Baseline Release Automation 미완료 | Release |
| GAP-CL-12 | Critical Service Evidence Coverage 미확정 | Runtime |

---

# 170. RISK Register

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-CL-01 | 문서만 업데이트되고 Source는 Drift | Critical |
| RISK-CL-02 | Source만 변경되고 Architecture 미갱신 | Critical |
| RISK-CL-03 | ServiceId SSOT 불일치 | High |
| RISK-CL-04 | SQL/Table Trace 누락 | High |
| RISK-CL-05 | Artifact/Deployment 추적 불가 | Critical |
| RISK-CL-06 | Runtime Evidence가 Release와 분리 | Critical |
| RISK-CL-07 | Critical Drift 미탐지 | Critical |
| RISK-CL-08 | Exception ADR 만료관리 부재 | High |
| RISK-CL-09 | UNKNOWN 장기 방치 | High |
| RISK-CL-10 | Gate가 형식승인으로 전락 | Critical |

---

# 171. ADR 후보

```text
ADR-CL-01 Architecture Model Schema
ADR-CL-02 ServiceId SSOT
ADR-CL-03 Traceability Index
ADR-CL-04 SQL/Table Parser
ADR-CL-05 Artifact/Deployment Manifest
ADR-CL-06 Runtime Evidence Manifest
ADR-CL-07 Architecture Rule Engine
ADR-CL-08 Drift Detection
ADR-CL-09 Gate RACI
ADR-CL-10 Baseline Versioning
ADR-CL-11 Evidence Repository
ADR-CL-12 Architecture Dashboard
```

---

# 172. Verification Checklist — Traceability

```text
[ ] Requirement→Architecture?
[ ] Architecture→ServiceId?
[ ] ServiceId→Handler?
[ ] Handler→Service?
[ ] Service→DAO?
[ ] DAO→Mapper?
[ ] Mapper→SQL?
[ ] SQL→Table?
[ ] Artifact→Deployment?
[ ] Deployment→Runtime Evidence?
```

---

# 173. Verification Checklist — Model

```text
[ ] Entity 정의?
[ ] Relation 정의?
[ ] ServiceId 포함?
[ ] Interface 포함?
[ ] Data 포함?
[ ] Policy 포함?
[ ] Evidence Link 포함?
```

---

# 174. Verification Checklist — Rule

```text
[ ] Naming Rule?
[ ] Dependency Rule?
[ ] ServiceId Rule?
[ ] TX Rule?
[ ] Timeout Rule?
[ ] Security Rule?
[ ] Logging Rule?
[ ] Deployment Rule?
[ ] Runtime Evidence Rule?
```

---

# 175. Verification Checklist — Evidence

```text
[ ] Source Commit?
[ ] Artifact Hash?
[ ] Deployment ID?
[ ] Environment?
[ ] Scenario?
[ ] ServiceId?
[ ] TraceId?
[ ] Metric?
[ ] Result?
[ ] Evidence Hash?
```

---

# 176. Verification Checklist — Drift

```text
[ ] Document Drift?
[ ] Model Drift?
[ ] Source Drift?
[ ] Config Drift?
[ ] Deployment Drift?
[ ] Runtime Drift?
[ ] Security Drift?
```

---

# 177. Verification Checklist — Gate

```text
[ ] G00 Source fixed?
[ ] G10 Document classified?
[ ] G20 Model complete?
[ ] G30 Conformance?
[ ] G40 Test?
[ ] G50 Runtime Evidence?
[ ] G60 Drift?
[ ] G70 GAP/ADR?
[ ] G80 Approval?
[ ] HG90 Release?
```

---

# 178. Closed Loop Completion Gate

## FIG-CL-68. Final Closed Loop Gate

```text
Document?
  ↓ YES
Model?
  ↓ YES
Source Baseline?
  ↓ YES
Rules?
  ↓ YES
Tests?
  ↓ YES
Runtime Evidence?
  ↓ YES
Critical Drift = 0?
  ↓ YES
Critical GAP resolved/approved?
  ↓ YES
ADR complete?
  ↓ YES
Human approval?
  ↓ YES
HG90 BASELINE RELEASE
```

---

# 179. 전체 Architecture 7단계

## FIG-CL-69. End-to-End Architecture

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
   실제로 어떻게 실행되는가?
      ↓
07 TRACEABILITY / CLOSED LOOP
   설계대로 동작하는 것을 어떻게 증명하고 유지하는가?
```

---

# 180. 최종 Architecture 왕복 구조

## FIG-CL-70. Top-down + Bottom-up

```text
                 TOP-DOWN DESIGN

VISION
  ↓
BIG PICTURE
  ↓
LOGICAL
  ↓
PHYSICAL
  ↓
MECHANISM
  ↓
RUNTIME
  ↓
────────────────────────────
       RUNTIME EVIDENCE
────────────────────────────
  ↑
SOURCE / CONFIG
  ↑
MODEL
  ↑
DRIFT / GAP
  ↑
ADR
  ↑
NEW BASELINE

             BOTTOM-UP VERIFICATION
```

---

# 181. Definition of Done

## Traceability
- [x] Forward Trace 정의
- [x] Reverse Trace 정의
- [x] ServiceId 중심 Trace 정의
- [x] Requirement→Runtime 연결
- [x] SQL/Table Trace 정의

## Model
- [x] Entity 정의
- [x] Relation 정의
- [x] Design/Runtime Graph 구분
- [x] Policy 연결
- [x] Evidence Link 구조 정의

## Architecture as Code
- [x] Naming Rule
- [x] ServiceId Rule
- [x] Handler/Layer Rule
- [x] TX/Timeout Rule
- [x] Security/Logging Rule
- [x] Deployment/Evidence Rule

## Test
- [x] Static
- [x] Architecture
- [x] Contract
- [x] Integration
- [x] Security
- [x] TX/Timeout
- [x] Performance/Failure

## Evidence
- [x] Source→Artifact→Deployment→Runtime Chain
- [x] Evidence Manifest
- [x] Evidence Hash
- [x] Release Trace
- [x] Failure/DR/Security/Performance Evidence

## Drift / GAP / ADR
- [x] Drift Type
- [x] Severity
- [x] GAP vs DRIFT
- [x] ADR Lifecycle
- [x] Architecture Debt

## Gate
- [x] G00~HG90
- [x] Workspace 00-IN~90-OUT
- [x] Completion Gate
- [x] Human Approval
- [x] Baseline Release

**TRACEABILITY / CLOSED LOOP 장 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. ServiceId 전수 자동 Index 생성
2. Handler→Mapper→SQL→Table 자동 Trace
3. Artifact→Deployment Manifest 생성
4. Runtime Evidence Manifest 자동화
5. Architecture Model Schema 확정
6. Architecture Rule CI 적용
7. Critical Drift 자동탐지
8. Gate RACI 승인
9. Evidence Repository 확정
10. Baseline Versioning / Release Rule 확정
11. Critical Runtime Evidence Coverage 100%
12. HG90 실제 Release 절차 검증

---

# 182. 다음 단계

이 장 이후에는 두 방향으로 확장할 수 있다.

```text
A. 08. PDMG SOURCE / RUNTIME REFERENCE DEEP DIVE
   - pdmg-ui
   - pdmg-jwt
   - pdmg-fw
   - pdmg-service
   - pdmg-om
   - ServiceId
   - Source/Class/Config

B. 08. STANDARD / DEVOPS / OM / OBSERVABILITY
   - CI/CD
   - OM
   - Dashboard
   - Runtime Operations
   - Architecture Gate Automation
```

현재 01~07은 **NSIGHT Target Architecture의 Top-down 설명과 Bottom-up 검증 체계**를 하나의 Closed Loop로 완결한 상태다.
