# NSIGHT / PDMG 아키텍처 정의서
# 10. INTEGRATED ARCHITECTURE BASELINE
## Naming / ServiceId / Traceability / Model / Rule / Gate / Baseline Release
## Visual-First / Top-down + Bottom-up Integration 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-VISUAL-INTEGRATED-BASELINE-10`  
> Architecture Level: **L9 — INTEGRATED BASELINE / GOVERNANCE / RELEASE**  
> 문서 상태: **Draft / Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_09_OM_DEVOPS_OBSERVABILITY_OPERATIONS_VISUAL_FIRST_상세본.md`  
> 본 장 목적: **01~09장을 하나의 Architecture Baseline으로 통합하고, Naming·ServiceId·Traceability·Rule·Evidence·Gate·Release를 단일 통제체계로 완결**

---

# 0. 이 장의 역할

1~9장은 각각 서로 다른 질문에 답했다.

```text
01 VISION
왜 바꾸는가?

02 BIG PICTURE
누가 무엇을 책임하는가?

03 LOGICAL
어떤 논리구조로 분리하는가?

04 PHYSICAL
어디에 배치하는가?

05 MECHANISM
어떤 규칙으로 동작하는가?

06 RUNTIME
실제로 어떻게 실행되는가?

07 TRACEABILITY / CLOSED LOOP
어떻게 검증하고 유지하는가?

08 PDMG SOURCE REFERENCE
실제 Source는 어떻게 구현되어 있는가?

09 OM / DEVOPS / OBSERVABILITY
어떻게 배포·관찰·복구·운영하는가?
```

10장은 이 모든 것을 하나로 묶는다.

```text
Classification
   ↓
Naming
   ↓
ServiceId
   ↓
Source
   ↓
Data
   ↓
Artifact
   ↓
Deployment
   ↓
Runtime
   ↓
Evidence
   ↓
Rule / Gate
   ↓
Architecture Baseline
```

---

# 1. VISUAL ROUTE — Integrated Baseline 전체

## FIG-IB-01. Integrated Architecture Baseline Journey

```text
╔══════════════════════════════════════════════════════════════════════════════╗
║                    INTEGRATED ARCHITECTURE BASELINE                         ║
╚══════════════════════════════════════════════════════════════════════════════╝

 [1] CLASSIFICATION
 Application Group / Business / Function / Program
      │
      ▼
 [2] NAMING
 Package / Program / ServiceId / Mapper / Artifact / Deployment
      │
      ▼
 [3] SERVICE ID
 Business Transaction Identity / Runtime Routing Key
      │
      ▼
 [4] SOURCE TRACE
 Handler → Facade → Service → DAO → Mapper → SQL → Table
      │
      ▼
 [5] DEPLOYMENT TRACE
 Source → Build → Artifact → JVM → Host → Environment
      │
      ▼
 [6] RUNTIME TRACE
 GUID / ServiceId / Thread / TX / SQL / Result
      │
      ▼
 [7] MODEL
 Entity / Relation / Policy / Evidence Link
      │
      ▼
 [8] RULE
 Naming / Dependency / TX / Timeout / Security / Deployment
      │
      ▼
 [9] TEST / EVIDENCE
 Static / Contract / Integration / Runtime / DR
      │
      ▼
 [10] DRIFT / GAP / ADR
 Expected vs Actual
      │
      ▼
 [11] GATE
 G00 → G80 → HG90
      │
      ▼
 [12] BASELINE RELEASE
 Document + Model + Rule + Evidence + Approval
```

---

# 2. Top-down과 Bottom-up의 최종 결합

## FIG-IB-02. Architecture Two-way Model

```text
TOP-DOWN

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
Runtime
  ↓
Expected Architecture

────────────────────────────────────

BOTTOM-UP

Actual Source
  ↑
Actual Config
  ↑
Actual Deployment
  ↑
Actual Runtime
  ↑
Evidence
  ↑
Drift / GAP

────────────────────────────────────

MEETING POINT
Architecture Baseline
```

---

# 3. Integrated Baseline의 핵심 원칙

## FIG-IB-03. Baseline Equation

```text
Architecture Baseline
=
Document
+
Model
+
Naming
+
Traceability
+
Rules
+
Tests
+
Runtime Evidence
+
Drift Review
+
ADR
+
Approval
```

### 금지

```text
문서만 있으면 Baseline       X
PPT 승인만 받으면 Baseline    X
Source가 있으면 Baseline      X
Test PASS면 Baseline          X
```

---

# 4. Architecture Classification의 시작점

## FIG-IB-04. Classification Tree

```text
Enterprise
  ↓
Service Domain
  ↓
Application Group
  ↓
Business Group
  ↓
Function
  ↓
Program
  ↓
ServiceId
```

이 축이 Naming과 Traceability의 기준이 된다.

---

# 5. 5대 Service Domain

```text
Marketing Platform
Data Platform
BI Portal
Data Governance
IT Service & Business Support
```

---

# 6. Application Group

## FIG-IB-05. Application Classification

```text
5대 Service Domain
       │
       ▼
Application Classification
│
├─ MP  Marketing
├─ RD  Relational / Operational Data
├─ AD  Analytical Data
├─ BI  Business Intelligence
├─ DG  Data Governance
└─ IM  IT Service / Integration / Management
```

### 핵심

```text
5대 Service Domain
≠
Application Group Code 수
```

Data Platform은 RD/AD로 세분화될 수 있다.

---

# 7. Business Code

PDMG 현재 대표 업무축:

```text
MG
└─ CO
   └─ A
```

예:

```text
MG / CO / A / 9001
```

---

# 8. Naming Axis

## FIG-IB-06. One Classification, Multiple Names

```text
Business Classification
MG / CO / A / 9001
      │
      ├────────► Program
      │          mgcoa9001
      │
      ├────────► ServiceId
      │          mgcoa9001S0
      │
      ├────────► Java Package
      │          nhnis.mg.co.a
      │
      ├────────► Java Class
      │          mgcoa9001Handler
      │
      └────────► Mapper Resource
                 rdw.mg.co.a/mgcoa9001-ORA.xml
```

---

# 9. Naming의 목적

Naming은 보기 좋은 이름을 정하는 일이 아니다.

```text
Classification
  ↓
Search
  ↓
Trace
  ↓
Ownership
  ↓
Automation
```

Naming이 일관되면 Source Scanner와 Architecture Model 연결이 쉬워진다.

---

# 10. Program ID

## FIG-IB-07. Program ID

```text
mg | co | a | 9001
│    │    │     │
│    │    │     └─ Program Number
│    │    └─────── Function
│    └──────────── Business
└───────────────── Application / Major Group
```

---

# 11. ServiceId

## FIG-IB-08. ServiceId Anatomy

```text
mg | co | a | 9001 | S | 0
│    │    │    │      │   │
│    │    │    │      │   └─ Sequence
│    │    │    │      └──── Transaction Type
│    │    │    └─────────── Program Number
│    │    └──────────────── Function
│    └───────────────────── Business
└────────────────────────── Application / Major Group
```

예:

```text
mgcoa9001S0
```

---

# 12. Transaction Type

대표 거래구분:

```text
S = Select
C = Create
U = Update
D = Delete
A = Action
R = Reserved/Reference candidate
```

현재 Handler 실제 사용은 주로:

```text
S / C / U / D
```

이며 `A/R`의 실제 적용범위는 Source Inventory로 확인한다.

---

# 13. ServiceId Regex

일반 후보:

```text
^[a-z]{2}[a-z]{2}[a-z][0-9]{4}[SCUDAR][0-9A-Z]$
```

MG 특화 후보:

```text
^mg[a-z]{2}[a-z][0-9]{4}[SCUDAR][0-9A-Z]$
```

---

# 14. ServiceId의 역할

## FIG-IB-09. ServiceId Identity

```text
ServiceId
│
├─ Business Transaction Identity
├─ Dispatcher Routing Key
├─ Logging Dimension
├─ Monitoring Dimension
├─ Traceability Key
└─ Architecture Model Key
```

---

# 15. ServiceId가 아닌 것

```text
GUID
URL
InterfaceId
Program ID
SQL ID
```

각각 역할이 다르다.

---

# 16. Identifier Taxonomy

## FIG-IB-10. Identifier Map

```text
Program ID
= 프로그램 단위

ServiceId
= 업무 거래 단위

GUID / TraceId
= 실행 거래 흐름 단위

InterfaceId
= 시스템 간 Contract 단위

SqlId
= SQL Statement 단위

DeploymentId
= 배포 실행 단위

EvidenceId
= 증적 패키지 단위
```

---

# 17. ServiceId Registry

## FIG-IB-11. Current PDMG Registry

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

현재 Source 분석 기준 총:

```text
13 ServiceIds
```

---

# 18. ServiceId SSOT

## FIG-IB-12. SSOT Candidate

```text
Architecture Model
       │
       ▼
ServiceId Registry
       │
       ├─ Backend Handler Registry
       ├─ UI Transaction Catalog
       ├─ API Catalog
       ├─ Test Catalog
       └─ Monitoring Catalog
```

### 원칙

Backend Handler Registry와 UI Catalog가 각각 독립 SSOT가 되면 Drift가 발생한다.

---

# 19. ServiceId Duplicate Rule

```text
ServiceId
  ↓
Multiple Handler?
  ├─ NO → PASS
  └─ YES
       ↓
    Startup Fail
```

Current PDMG Source 분석에서는 Duplicate ServiceId 등록이 기동 실패로 처리된다.

---

# 20. Registered vs Executed

## FIG-IB-13. Registry / Branch Alignment

```text
Handler.serviceIds()
      │
      │ compare
      ▼
Handler.handle()
      │
      ▼
Branch Coverage
```

가능한 오류:

```text
등록 O / Branch X
등록 X / Branch O
```

---

# 21. ServiceId → Handler

## FIG-IB-14. Runtime Routing

```text
Request
  ↓
ServiceId
  ↓
TransactionDispatcher
  ↓
handlerMap[serviceId]
  ↓
TransactionHandler
```

---

# 22. Handler → Facade

```text
Handler
  ↓
Facade
```

### 원칙

Handler는 Thin Inbound Adapter로 유지한다.

---

# 23. Handler 금지 Dependency

```text
Handler → DAO       X
Handler → Mapper    X
Handler → SQL       X
```

---

# 24. Facade → Service

## FIG-IB-15. Use Case Boundary

```text
Handler / Controller
      ↓
Facade
      ↓
Service
```

Facade는 Use Case Boundary 역할을 한다.

---

# 25. TCF ON/OFF 공통 Business Core

## FIG-IB-16. Target Candidate

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

Current OFF Source는 일부 Controller→Service 직접호출이 있으므로 `[GAP]`.

---

# 26. Service → DAO

```text
Service
  ↓
DAO
```

Service가 Business Procedure를 소유하고,
DAO는 Data Access Contract를 소유한다.

---

# 27. DAO → Mapper

## FIG-IB-17. MyBatis Contract

```text
Java DAO FQCN
       │
       │ namespace exact match
       ▼
Mapper XML
       │
       ▼
SqlId
```

---

# 28. Mapper Resource

Current PDMG Reference:

```text
classpath*:rdw.*/*.xml
```

대표:

```text
rdw.mg.co.a/mgcoa9000-ORA.xml
```

---

# 29. Mapper Namespace

```text
Java
nhnis.mg.co.a.persistence.dao.mgcoa9000DAO

        │ exact
        ▼

Mapper
namespace="nhnis.mg.co.a.persistence.dao.mgcoa9000DAO"
```

---

# 30. SqlId

## FIG-IB-18. SqlId Trace

```text
DAO Method
   ↓
SqlId
   ↓
SQL
   ↓
Table / View
```

대표 형태:

```text
mgcoa9000S0_S0
mgcoa9000S0_COUNT
mgcoa9000C0_C0
mgcoa9000U0_U0
mgcoa9000D0_D0
```

보조 Statement도 존재할 수 있으므로 Source 기준으로 수집한다.

---

# 31. SQL → Table

```text
SQL Parser
  ↓
FROM / JOIN / INSERT / UPDATE / DELETE
  ↓
Table / View
```

이 영역은 문자열추정이 아니라 Parser가 필요하다.

---

# 32. Full Source Trace

## FIG-IB-19. Service-to-Data Trace

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
SqlId
  ↓
SQL
  ↓
Table / View
```

---

# 33. Program → Source Trace

## FIG-IB-20. Program Stem

```text
mgcoa9000
│
├─ mgcoa9000Handler
├─ mgcoa9000Facade
├─ mgcoa9000Service
├─ mgcoa9000DAO
├─ mgcoa9000 DTOs
└─ mgcoa9000-ORA.xml
```

---

# 34. Package Naming

## FIG-IB-21. Package Rule

```text
Business Axis
MG / CO / A
    │
    ├─ Java
    │   nhnis.mg.co.a
    │
    └─ Mapper
        rdw.mg.co.a
```

---

# 35. Package Rule의 목적

```text
Business Ownership
→ Source Location
→ Mapper Location
→ Automated Trace
```

---

# 36. Package Anti-pattern

```text
common
util
etc
temp
misc
```

와 같은 무분별한 범용 Package에 Business Code를 숨기지 않는다.

---

# 37. Framework Package

```text
nhnis.fw.*
```

Business Package:

```text
nhnis.mg.*
```

### 원칙

Framework가 특정 Business Package에 직접 의존하지 않도록 한다.

---

# 38. Module Naming

```text
pdmg-ui
pdmg-jwt
pdmg-fw
pdmg-service
pdmg-om
```

Module 명은 Build Responsibility를 표현한다.

---

# 39. Module ≠ Runtime Node

## FIG-IB-22. Boundary Reminder

```text
Build Module
     │
     │ ≠
     ▼
Runtime Process
     │
     │ ≠
     ▼
Logical Node
```

---

# 40. Source → Artifact

## FIG-IB-23. Build Trace

```text
Repository
 ↓
Branch
 ↓
Commit
 ↓
Module
 ↓
Build
 ↓
Artifact
 ↓
Hash
```

---

# 41. Artifact → Deployment

## FIG-IB-24. Deployment Trace

```text
Artifact
  ↓
DeploymentId
  ↓
Environment
  ↓
Center
  ↓
Host
  ↓
JVM
  ↓
Context / WAR
```

---

# 42. Deployment → Runtime

```text
Host
 ↓
JVM
 ↓
WAR
 ↓
ServiceId
 ↓
GUID
 ↓
Runtime Evidence
```

---

# 43. End-to-End Trace

## FIG-IB-25. Requirement to Evidence

```text
Requirement
  ↓
Architecture Principle
  ↓
ADR
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
SQL
  ↓
Table
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

# 44. Reverse Trace

## FIG-IB-26. Incident to Requirement

```text
Runtime Incident
  ↑
GUID
  ↑
ServiceId
  ↑
Deployment
  ↑
Artifact
  ↑
Commit
  ↑
Source
  ↑
Architecture Decision
  ↑
Requirement
```

---

# 45. GUID

```text
std_gbl_id
```

Current PDMG Reference의 Correlation Key다.

---

# 46. GUID Lifecycle

## FIG-IB-27. GUID Propagation

```text
Request Header
  ↓
DefaultFilter
  ↓
ServiceContext
  ↓
MDC
  ↓
Worker
  ↓
Business
  ↓
ImageLog
  ↓
Response / Evidence
```

---

# 47. GUID와 ServiceId의 조합

```text
GUID
= 이 실행은 어느 흐름인가?

ServiceId
= 이 실행은 무슨 업무거래인가?
```

운영에서는 둘을 함께 본다.

---

# 48. InterfaceId

## FIG-IB-28. Interface Trace

```text
Producer
  ↓
InterfaceId
  ↓
Mechanism
  ↓
Consumer
```

InterfaceId와 ServiceId는 별도 Identifier다.

---

# 49. EventId / JobId / FileId

```text
Online
→ GUID + ServiceId

Event
→ EventId / CorrelationId

Batch
→ JobId / ExecutionId

File
→ FileId / InterfaceId
```

Runtime Type에 맞는 Trace Key를 사용한다.

---

# 50. Architecture Model의 목적

## FIG-IB-29. Human + Machine

```text
Human-readable
Markdown / PPT
       │
       ▼
Machine-readable
Architecture Model
       │
       ▼
Rule / Scan / Test / Drift
```

---

# 51. Model Entity

## FIG-IB-30. Entity Map

```text
Requirement
Principle
ADR
Domain
ApplicationGroup
System
LogicalNode
PhysicalNode
Module
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
Artifact
Deployment
RuntimeScenario
Evidence
Gap
Risk
```

---

# 52. Model Relation

## FIG-IB-31. Relation Map

```text
Requirement
  DRIVES
Principle

Principle
  GOVERNED_BY
ADR

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

# 53. Deployment Relations

```text
Module
  BUILDS
Artifact

Artifact
  DEPLOYED_AS
Deployment

Deployment
  RUNS_ON
PhysicalNode

Deployment
  HOSTS
ServiceId
```

---

# 54. Runtime Relations

## FIG-IB-32. Runtime Graph

```text
ServiceId
  EXECUTES_AS
RuntimeScenario

RuntimeScenario
  USES_THREAD
Worker

RuntimeScenario
  USES_TX
Transaction

RuntimeScenario
  PRODUCES
Evidence
```

---

# 55. Evidence Relations

```text
Evidence
  PROVES
RuntimeScenario

Evidence
  REFERENCES
Deployment

Evidence
  REFERENCES
Artifact

Evidence
  REFERENCES
ServiceId
```

---

# 56. Model Version

```text
architectureModelVersion
```

은 Baseline마다 고정한다.

---

# 57. Model Baseline

## FIG-IB-33. Model Baseline

```text
Architecture Baseline v1
       │
       ├─ Document v1
       ├─ Model v1
       ├─ Rule v1
       └─ Evidence Index v1
```

---

# 58. Model Diff

```text
Model v1
  ↓
Change
  ↓
Model v2
  ↓
Entity / Relation Diff
```

---

# 59. Architecture Rule

## FIG-IB-34. Rule Lifecycle

```text
Principle
  ↓
Rule
  ↓
Scanner / Test
  ↓
PASS / FAIL
  ↓
Gate
```

---

# 60. Rule Categories

```text
Naming
ServiceId
Dependency
Mapper
Transaction
Timeout
Security
Logging
Deployment
Runtime Evidence
DR
```

---

# 61. Naming Rule

```text
Business Axis
↔ Package
↔ Mapper
↔ Service Prefix
```

불일치 시 Warning/Fail 후보.

---

# 62. ServiceId Format Rule

```text
ServiceId
  ↓
Regex
  ↓
PASS / FAIL
```

---

# 63. ServiceId Unique Rule

```text
All ServiceIds
  ↓
Duplicate?
  ├─ NO → PASS
  └─ YES → FAIL
```

---

# 64. Handler Branch Rule

```text
serviceIds()
  ↓ compare
handle() branches
```

---

# 65. Handler Dependency Rule

```text
Handler
  ↓
DAO?
  └─ YES → FAIL
```

---

# 66. Controller Dependency Rule

```text
Controller
  ↓
DAO / Mapper?
  └─ YES → FAIL
```

---

# 67. Framework Dependency Rule

## FIG-IB-35. Framework Rule

```text
Business
   ─────► Framework     O

Framework
   ─────► Specific Business Package   X
```

---

# 68. DAO / Mapper Rule

```text
DAO FQCN
=
Mapper Namespace
```

---

# 69. Mapper Resource Rule

```text
Mapper XML
  ↓
Resource Pattern
  ↓
Loaded?
```

---

# 70. SQL Trace Rule

```text
SqlId
  ↓
SQL Parse
  ↓
Table / View
```

Trace 불가 시 GAP.

---

# 71. Transaction Owner Rule

## FIG-IB-36. TX Rule

```text
ServiceId
  ↓
Runtime Mode
  ↓
TX Owner
  ↓
TransactionManager
  ↓
Datasource
```

UNKNOWN이면 Gate 미통과 후보.

---

# 72. Timeout Hierarchy Rule

```text
DB Query
   <
TX / Worker
   <
Server / External
   <
Client
```

---

# 73. Current PDMG Timeout Snapshot

```text
Worker Deadline = 5000ms
Worker Pool     = 20
Queue           = 100
```

### 태그

```text
[AS-IS SNAPSHOT]
```

---

# 74. Query Timeout Rule

```text
Query Timeout
  ↓
Known?
  ├─ YES
  └─ NO → GAP
```

---

# 75. Retry Rule

```text
Retry
=
Retryable Error
+
Backoff
+
Max Retry
+
Idempotency
+
Recovery
```

---

# 76. Security Rule — Algorithm Alignment

## FIG-IB-37. JWT Rule

```text
Issuer Algorithm
      │
      │ compare
      ▼
Verifier Algorithm
```

불일치:

```text
CRITICAL FAIL
```

---

# 77. Security Rule — Key Consistency

```text
Same kid
→ Same public key fingerprint
```

다르면 Critical.

---

# 78. Security Rule — Identity Binding

```text
JWT Principal
  ↓
Business User Identity
```

Header Identity가 독립적으로 신뢰되지 않아야 한다.

---

# 79. Sensitive Log Rule

```text
Token / Password / Secret / Private Key
        ↓
Raw Logging?
        └─ YES → FAIL
```

---

# 80. Deployment Manifest Rule

## FIG-IB-38. Deployment Rule

```text
Artifact
  ↓
Host/JVM/Context Mapping?
  ├─ YES
  └─ NO → FAIL/GAP
```

---

# 81. Runtime Inventory Rule

```text
Running JVM
  ↓
Known DeploymentId?
```

UNKNOWN Runtime Process는 운영 Risk다.

---

# 82. Runtime Evidence Rule

```text
Critical ServiceId
  ↓
Required Runtime Scenario
  ↓
Evidence?
  ├─ YES
  └─ NO → Gate Fail Candidate
```

---

# 83. DR Evidence Rule

```text
Critical Service
  ↓
DR Scenario
  ↓
Evidence
```

---

# 84. Restore Evidence Rule

```text
Backup
  ↓
Restore Test
  ↓
Evidence
```

---

# 85. Architecture as Code

## FIG-IB-39. Architecture Rule Pipeline

```text
Architecture Rule
    ↓
Rule File
    ↓
Scanner / Test
    ↓
CI
    ↓
PASS / FAIL
```

---

# 86. Rule Definition Example

```yaml
rule:
  id: R-SERVICEID-UNIQUE
  scope: pdmg-service
  severity: critical
  source: handlerRegistry
  condition: duplicateCount == 0
  evidence:
  owner:
```

---

# 87. Test Architecture

## FIG-IB-40. Test Pyramid for Architecture

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

# 88. Static Test

```text
Naming
Package
Forbidden Dependency
Secret Scan
Config Pattern
```

---

# 89. Architecture Test

```text
ServiceId Unique
Layer Dependency
Handler/Controller Rule
Mapper Contract
```

---

# 90. Contract Test

```text
Request Header
DTO
Success Envelope
Error Envelope
Interface Schema
```

---

# 91. Transaction Test

## FIG-IB-41. TX Test Set

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

# 92. Timeout Test

```text
Queue Wait
Worker Slow
Hikari Wait
SQL Slow
External Slow
```

---

# 93. Security Test

```text
Valid Token
Expired
Wrong kid
Wrong signature
Revoked
Identity mismatch
Direct WAS bypass
```

---

# 94. Deployment Test

```text
Correct Artifact?
Correct Hash?
Correct Config?
Correct Key?
Correct Host/JVM?
```

---

# 95. Runtime Evidence Test

```text
Scenario
  ↓
Execute
  ↓
Trace
  ↓
Expected Outcome
  ↓
Evidence Manifest
```

---

# 96. Evidence Chain

## FIG-IB-42. Evidence Identity

```text
architectureBaselineId
       ↓
architectureModelVersion
       ↓
sourceCommit
       ↓
buildId
       ↓
artifactHash
       ↓
deploymentId
       ↓
serviceId
       ↓
traceId / GUID
       ↓
runtimeEvidence
```

---

# 97. Evidence Manifest

```yaml
evidence:
  evidenceId:
  architectureBaselineId:
  modelVersion:
  sourceCommit:
  buildId:
  artifactHash:
  deploymentId:
  environment:
  serviceId:
  traceId:
  scenarioId:
  result:
  metrics:
  logs:
  attachments:
  owner:
  evidenceHash:
```

---

# 98. Evidence의 강도

## FIG-IB-43. Evidence Strength

```text
Runtime Evidence
      >
Source / Config
      >
Official Approved Baseline
      >
ADR
      >
Detailed Design
      >
Requirement / Interview
      >
Draft / Past Conversation
```

---

# 99. AS-IS / TO-BE / GAP

## FIG-IB-44. State Model

```text
PDMG / Current
   ↓
[AS-IS]

NSIGHT Target
   ↓
[TO-BE]

Difference
   ↓
[GAP]
```

---

# 100. Evidence Tag Standard

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
[BASELINE-YYYY-MM-DD]
```

---

# 101. Conflict 처리

## FIG-IB-45. Conflict

```text
Evidence A
   │
   ├─ different
   │
Evidence B
   │
   ▼
[CONFLICT]
   ↓
Owner Review
   ↓
Runtime Measurement
   ↓
ADR
   ↓
New Baseline
```

---

# 102. CDC SLA Conflict 예

```text
Baseline A
30 sec

vs

Baseline B
3 sec
```

Runtime Measurement Point를 정의한 후 결정한다.

---

# 103. Session Conflict 예

```text
60 min
vs
90 min
```

최종 승인 전까지 `[CONFLICT]`.

---

# 104. Filter Order Drift 예

```text
Old Document
order = 1

Current Source
HIGHEST_PRECEDENCE + 20

→ DRIFT
```

---

# 105. ServiceId Count Drift 예

```text
Past Document
8

Current Source
13

→ DRIFT / SUPERSEDED
```

---

# 106. Drift의 정의

## FIG-IB-46. Drift

```text
Expected
  │
  │ compare
  ▼
Actual
  │
  ├─ same
  └─ different
        ↓
      DRIFT
```

---

# 107. Drift Type

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

# 108. GAP vs DRIFT

```text
GAP
= 필요한 것이 없거나 미정

DRIFT
= 정의한 것과 실제가 다름
```

---

# 109. Drift Severity

## FIG-IB-47. Severity

```text
CRITICAL
- Security bypass
- Data inconsistency
- Wrong artifact
- Late commit
- DR impossible

HIGH
- NFR violation
- Trace loss
- Config mismatch

MEDIUM
- Naming / Documentation

LOW
- Cosmetic / metadata
```

---

# 110. GAP Register 최소구조

```yaml
gap:
  gapId:
  layer:
  expected:
  actual:
  impact:
  severity:
  owner:
  action:
  targetDate:
  adr:
  evidence:
  status:
```

---

# 111. Risk Register 최소구조

```yaml
risk:
  riskId:
  cause:
  event:
  impact:
  likelihood:
  severity:
  mitigation:
  owner:
  evidence:
```

---

# 112. ADR Lifecycle

## FIG-IB-48. ADR

```text
GAP / DRIFT / CONFLICT
       ↓
Decision Needed
       ↓
ADR
       │
       ├─ Context
       ├─ Decision
       ├─ Alternatives
       ├─ Consequences
       ├─ Owner
       └─ Date
       ↓
Architecture Update
```

---

# 113. ADR Status

```text
PROPOSED
ACCEPTED
REJECTED
SUPERSEDED
DEPRECATED
```

---

# 114. Standard Exception

```text
Standard Rule
    ↓
Exception
    ↓
ADR
    ↓
Compensating Control
    ↓
Expiry / Review
```

---

# 115. Architecture Debt

## FIG-IB-49. Debt

```text
Accepted GAP
  ↓
Architecture Debt
  ↓
Owner
  ↓
Due Date
  ↓
Review
  ↓
Close / Extend
```

---

# 116. Unknown

```text
Evidence 없음
   ↓
[UNKNOWN]
   ↓
Evidence Collection Task
```

Unknown을 빈칸으로 숨기지 않는다.

---

# 117. Architecture Gate 전체

## FIG-IB-50. G00 → HG90

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
Model ↔ Source Conformance
   ↓
G40
Rule / Test
   ↓
G50
Runtime Evidence
   ↓
G60
Drift
   ↓
G70
GAP / ADR
   ↓
G80
Human Approval
   ↓
HG90
Architecture Baseline Release
```

---

# 118. G00 — Source Baseline

## FIG-IB-51. Source Freeze

```text
Repository
  ↓
Branch
  ↓
Commit
  ↓
Module Scope
  ↓
Config Scope
  ↓
Baseline ID
```

---

# 119. G10 — Document Classification

```text
Document Statement
  ↓
Tag
  ↓
FACT / AS-IS / TO-BE / GAP / ...
```

---

# 120. G20 — Model

```text
Documents
  ↓
Entity
  ↓
Relation
  ↓
Policy
  ↓
Model Version
```

---

# 121. G30 — Conformance

## FIG-IB-52. Model vs Source

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

# 122. G40 — Rule / Test

```text
Rule
  ↓
Test
  ↓
PASS / FAIL
```

Critical Rule FAIL은 다음 Gate 진행을 막을 수 있다.

---

# 123. G50 — Runtime Evidence

## FIG-IB-53. Runtime Gate

```text
Exact Artifact
  ↓
Exact Deployment
  ↓
Scenario
  ↓
Trace
  ↓
Outcome
  ↓
Evidence
```

---

# 124. G60 — Drift

```text
Expected
  ↓ compare
Actual
  ↓
Drift Report
```

---

# 125. G70 — GAP / ADR

```text
Gap / Drift
   ↓
Fix?
Accept?
Exception?
Change Target?
   ↓
ADR
```

---

# 126. G80 — Human Approval

필요 역할 후보:

```text
Architecture
Business
Security
Data
Operations
PMO / Governance
```

실제 승인자는 프로젝트 RACI에 따라 확정한다.

---

# 127. HG90 — Baseline Release

## FIG-IB-54. Final Release

```text
Document
+
Model
+
Rules
+
Tests
+
Evidence
+
Drift Review
+
ADR
+
Approval
      │
      ▼
HG90
Architecture Baseline Release
```

---

# 128. HG90 실패조건

```text
Critical Rule Fail > 0
Critical Drift > 0
Critical Runtime Evidence Missing
Unapproved Critical GAP
Unknown Critical Deployment
```

---

# 129. Workspace Structure

## FIG-IB-55. Integrated Workspace

```text
00-IN
 ↓
10-DOCUMENT
 ↓
20-MODEL
 ↓
30-CODE / CONFORMANCE
 ↓
40-TEST
 ↓
50-RUNTIME-EVIDENCE
 ↓
60-DRIFT
 ↓
70-GAP-ADR
 ↓
80-GATE
 ↓
90-OUT
```

---

# 130. 00-IN

```text
RFP
Requirement
PPT
Source
Config
Runtime Input
```

---

# 131. 10-DOCUMENT

```text
01 VISION
02 BIG PICTURE
03 LOGICAL
04 PHYSICAL
05 MECHANISM
06 RUNTIME
07 CLOSED LOOP
08 PDMG REFERENCE
09 OPERATIONS
10 INTEGRATED BASELINE
```

---

# 132. 20-MODEL

```text
reference-baseline.json
serviceid-index.json
interface-index.json
deployment-model.json
runtime-scenario.json
evidence-index.json
```

---

# 133. 30-CODE / CONFORMANCE

```text
source-index
dependency-index
serviceid-index
mapper-sql-index
config-index
deployment-index
```

---

# 134. 40-TEST

```text
architecture-test
contract-test
security-test
transaction-test
timeout-test
integration-test
performance-test
failure-test
```

---

# 135. 50-RUNTIME-EVIDENCE

```text
runtime-scenario
metric
log
trace
screenshot/report
manifest
hash
```

---

# 136. 60-DRIFT

```text
document
model
source
config
deployment
runtime
security
data
```

---

# 137. 70-GAP-ADR

```text
gap-register
risk-register
adr
exception
debt
```

---

# 138. 80-GATE

```text
gate-result
approval
waiver
condition
```

---

# 139. 90-OUT

```text
Released Architecture Baseline
Model
Rules
Test Result
Evidence Index
Drift Report
ADR Pack
```

---

# 140. Architecture Baseline Package

## FIG-IB-56. Release Package

```text
BASELINE
│
├─ 01 VISION
├─ 02 BIG PICTURE
├─ 03 LOGICAL
├─ 04 PHYSICAL
├─ 05 MECHANISM
├─ 06 RUNTIME
├─ 07 CLOSED LOOP
├─ 08 PDMG REFERENCE
├─ 09 OPERATIONS
├─ 10 INTEGRATED BASELINE
├─ Architecture Model
├─ Naming Standard
├─ ServiceId Registry
├─ Interface Catalog
├─ Deployment Manifest
├─ Rule Set
├─ Test Result
├─ Runtime Evidence Index
├─ Drift Report
├─ GAP/Risk Register
└─ ADR Pack
```

---

# 141. Baseline ID

권장 개념:

```text
architectureBaselineId
```

예:

```text
NSIGHT-ARCH-2026-08-RC1
```

실제 Naming은 프로젝트 Release 규칙으로 승인한다.

---

# 142. Baseline Version

```text
v1.0
v1.1
v2.0
```

Version 증가 기준은 Change Management와 연계한다.

---

# 143. Baseline Date

과거 기준은:

```text
[BASELINE-YYYY-MM-DD]
```

태그로 보존한다.

---

# 144. Baseline Diff

## FIG-IB-57. Baseline N → N+1

```text
Baseline N
  ↓
Change
  ↓
Model Diff
  ↓
Source / Config Diff
  ↓
Rule / Test
  ↓
Runtime Evidence
  ↓
ADR
  ↓
Baseline N+1
```

---

# 145. Change Request

```text
Requirement Change
Architecture Change
Source Change
Config Change
Infrastructure Change
Security Change
```

모두 Baseline 영향평가 대상이다.

---

# 146. Change Impact

## FIG-IB-58. Impact Graph

```text
Change
  ↓
Application
  ↓
Program
  ↓
ServiceId
  ↓
Source
  ↓
Interface
  ↓
Data
  ↓
Deployment
  ↓
Test
  ↓
Runtime
```

---

# 147. ServiceId Change Impact

```text
ServiceId Change
  ↓
Handler Registry
  ↓
UI Catalog
  ↓
API/Interface
  ↓
Logging
  ↓
Monitoring
  ↓
Test
```

---

# 148. Package Change Impact

```text
Package Move
  ↓
Component Scan
  ↓
Mapper Namespace?
  ↓
Architecture Rule?
  ↓
Build / Runtime?
```

---

# 149. Mapper Change Impact

```text
Mapper Namespace / Path Change
  ↓
Mapper Resource Load
  ↓
DAO Binding
  ↓
SQL Execution
```

---

# 150. Table Change Impact

```text
Table
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

# 151. Framework Change Impact

## FIG-IB-59. pdmg-fw Impact

```text
pdmg-fw Change
   │
   ├─ pdmg-service
   ├─ pdmg-jwt
   ├─ Filter
   ├─ Context
   ├─ TCF
   ├─ Timeout
   ├─ Error
   └─ Security / Runtime
```

---

# 152. JWT Change Impact

```text
Algorithm
Key
kid
Issuer
Audience
Refresh
Denylist
Identity Binding
```

변경 시 모든 Validator/Client/DR 영향평가가 필요하다.

---

# 153. Timeout Change Impact

## FIG-IB-60. Timeout Change

```text
Worker Timeout
  ↓
Client
Gateway
WEB
TX
Hikari
JDBC
External
```

하나의 Timeout 변경이 전체 Budget에 영향을 준다.

---

# 154. Deployment Change Impact

```text
Artifact / JVM / Host Change
  ↓
Capacity
  ↓
HA
  ↓
Monitoring
  ↓
DR
  ↓
Evidence
```

---

# 155. Architecture Review Package

## FIG-IB-61. Review Pack

```text
Change Request
   ↓
Review Package
│
├─ Context
├─ Diagram
├─ Model Diff
├─ Naming/ServiceId Impact
├─ Source Impact
├─ Data Impact
├─ Deployment Impact
├─ Rule/Test Impact
├─ Risk
├─ Evidence
└─ ADR
```

---

# 156. Architecture Review Board

```text
Architect
Business
Security
Data
Operations
Infra
PMO
```

실제 구성은 프로젝트 Governance에 따라 확정한다.

---

# 157. Architecture KPI

## FIG-IB-62. Governance Dashboard

```text
Architecture Health
│
├─ Critical Drift
├─ Critical GAP
├─ Rule Pass Rate
├─ ServiceId Trace Coverage
├─ Deployment Trace Coverage
├─ Runtime Evidence Coverage
├─ DR Evidence Coverage
└─ ADR Aging
```

---

# 158. ServiceId Trace Coverage

```text
End-to-end traced ServiceIds
---------------------------
Total ServiceIds
```

---

# 159. Source Trace Coverage

```text
ServiceId→SQL/Table traced
--------------------------
Total ServiceIds
```

---

# 160. Deployment Trace Coverage

```text
Artifact→Host/JVM mapped
------------------------
Deployed Artifacts
```

---

# 161. Runtime Evidence Coverage

```text
Critical Runtime Scenarios with Evidence
----------------------------------------
Required Critical Scenarios
```

---

# 162. Critical Drift

```text
Critical Drift Count
```

Release 전 목표:

```text
0
```

---

# 163. Unknown Critical Item

```text
Unknown Critical Node
Unknown Critical Deployment
Unknown Critical Key
Unknown Critical TX
```

HG90 전 0을 목표로 한다.

---

# 164. Architecture Dashboard Drill-down

## FIG-IB-63. Dashboard

```text
Architecture Health
  ↓
Application
  ↓
ServiceId
  ↓
Source
  ↓
Deployment
  ↓
Runtime Evidence
```

---

# 165. Source Evidence Dashboard

```text
ServiceId
├─ Handler
├─ Facade
├─ Service
├─ DAO
├─ Mapper
└─ SqlId
```

---

# 166. Deployment Dashboard

```text
Artifact
├─ Hash
├─ Environment
├─ Host
├─ JVM
├─ Context
└─ DeploymentId
```

---

# 167. Runtime Dashboard

```text
ServiceId
├─ TPS
├─ p95
├─ Error
├─ Timeout
├─ Worker
├─ Hikari
└─ SQL
```

---

# 168. Security Dashboard

```text
JWT Algorithm
kid
Key Fingerprint
JWKS
Unknown kid
Auth Failure
Identity Mismatch
```

---

# 169. DR Dashboard

```text
Critical Service
├─ Main
├─ DR
├─ Artifact Sync
├─ Config Sync
├─ Key Sync
├─ Data Sync
└─ Last DR Evidence
```

---

# 170. PDMG Alignment — Strong Areas

## FIG-IB-64. Strong Reference

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

대표 Source에서 강한 Trace가 확인되는 구간이다.

---

# 171. PDMG Alignment — Partial Areas

```text
Error Standard
TCF OFF
Context Type Safety
Runtime Evidence
Deployment Mapping
Authorization
```

---

# 172. PDMG Alignment — Critical GAP

```text
RS256 Issuer vs HMAC Verifier
Ephemeral JWT Key
JWT Principal vs Header Identity
Query/TX Timeout
Direct WAS Security
Generic Error
```

---

# 173. PDMG `pdmg-om`

```text
Module Name exists in baseline
       │
       ▼
Actual Source / Runtime
       │
       ▼
[UNKNOWN]
```

UNKNOWN을 Target 기능으로 채우지 않는다.

---

# 174. NSIGHT Target Promotion Rule

## FIG-IB-65. Reference Promotion

```text
PDMG AS-IS Pattern
      ↓
Evidence
      ↓
NFR Fit?
      ↓
Scope Fit?
      ↓
Security/Operations Fit?
      ↓
ADR
      ↓
Approval
      ↓
NSIGHT TO-BE Standard
```

---

# 175. 자동 Promotion 금지

```text
PDMG에 구현됨
      ↓
NSIGHT 표준

X
```

---

# 176. Baseline Promotion 후보

```text
ServiceId Routing
Thin Handler
Facade Boundary
Service/DAO Separation
GUID Correlation
Standard Envelope
Framework/Common Separation
Mapper Naming Trace
```

---

# 177. 개선 후 Promotion 후보

```text
TCF ON/OFF Runtime
Timeout Budget
JWT
Error Contract
Context Model
OM
Runtime Evidence
```

---

# 178. Architecture Principle Registry

## FIG-IB-66. Principle Registry

```text
P-01
P-02
P-03
P-04
P-05 [GAP]
P-06
P-07
P-08
P-09
P-10
P-11
P-12
```

현재 P-05가 누락된 자료가 있다면:

```text
[GAP]
```

으로 유지한다.

---

# 179. Principle → Rule → Evidence

```text
Principle
  ↓
Rule
  ↓
Test
  ↓
Runtime Evidence
```

---

# 180. Example — Thin Handler

## FIG-IB-67. Principle Operationalization

```text
Principle
Handler는 Business Entry Adapter
      ↓
Rule
Handler→DAO forbidden
      ↓
Static Test
      ↓
PASS / FAIL
      ↓
Evidence
```

---

# 181. Example — Timeout

```text
Principle
하위 Timeout < 상위 Timeout
      ↓
Rule
Query < Worker < Client
      ↓
Config Scan + Integration Test
      ↓
Evidence
```

---

# 182. Example — Identity

```text
Principle
Business Identity는 Trusted Principal에서 유도
      ↓
Rule
JWT Subject ↔ Header User Binding
      ↓
Security Test
      ↓
Evidence
```

---

# 183. Example — Runtime Evidence

```text
Principle
Architecture는 Runtime으로 검증
      ↓
Rule
Critical Service Runtime Evidence required
      ↓
Gate G50
```

---

# 184. Baseline Release Candidate

## FIG-IB-68. RC

```text
RC
│
├─ Documents Complete
├─ Model Complete
├─ ServiceId Registry Complete
├─ Rule/Test PASS
├─ Deployment Trace Complete
├─ Runtime Evidence Complete
├─ Drift Reviewed
├─ GAP/ADR Complete
└─ Approval Ready
```

---

# 185. Baseline Release

```text
RC
  ↓
G80 Approval
  ↓
HG90
  ↓
Released Baseline
```

---

# 186. Release 후 변화

## FIG-IB-69. Baseline Never Stops

```text
Released Baseline
   ↓
Source / Config / Runtime Change
   ↓
Drift
   ↓
Review
   ↓
New ADR
   ↓
New Baseline
```

---

# 187. Continuous Architecture

```text
Architecture
=
Design
+
Verification
+
Operation
+
Change Governance
```

---

# 188. Architecture as Document

```text
Markdown
PPT
Guide
```

사람이 이해하기 위한 표현이다.

---

# 189. Architecture as Model

```text
JSON / Graph / Inventory
```

기계가 이해하기 위한 표현이다.

---

# 190. Architecture as Code

```text
Rule
Scanner
Test
CI Gate
```

자동 검증을 위한 표현이다.

---

# 191. Architecture as Evidence

```text
Runtime
Deployment
Performance
Security
DR
```

설계가 실제 동작한다는 증명이다.

---

# 192. Architecture as Governance

```text
ADR
Gate
Approval
Baseline
```

변경을 통제하는 체계다.

---

# 193. Integrated Architecture Equation

## FIG-IB-70. Final Equation

```text
Architecture
=
Document
+
Model
+
Code Rule
+
Runtime Evidence
+
Governance
```

---

# 194. Master Traceability Matrix

## FIG-IB-71. Master Matrix

```text
Requirement
  ↓
Principle
  ↓
ADR
  ↓
Domain
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
Runtime
  ↓
Evidence
```

---

# 195. Master Matrix 최소 컬럼

| 영역 | 컬럼 |
|---|---|
| Requirement | Requirement ID |
| Architecture | Principle / ADR / Baseline |
| Application | Group / Business / Program |
| Runtime | ServiceId / InterfaceId |
| Source | Handler / Facade / Service / DAO |
| Data | Mapper / SqlId / Table |
| Build | Commit / Build / Artifact Hash |
| Deploy | DeploymentId / Host / JVM |
| Evidence | GUID / Scenario / Result |
| Governance | GAP / Risk / ADR / Gate |

---

# 196. Master ServiceId Registry Template

```yaml
service:
  serviceId:
  applicationGroup:
  businessCode:
  functionCode:
  programId:
  transactionType:
  sequence:
  ui:
  endpoint:
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
  owner:
  status:
```

---

# 197. Master Deployment Registry

```yaml
deployment:
  application:
  artifact:
  artifactHash:
  sourceCommit:
  environment:
  center:
  host:
  jvm:
  context:
  port:
  datasource:
  serviceIds:
  deploymentId:
  deployedAt:
  status:
```

---

# 198. Master Runtime Evidence Registry

```yaml
runtimeEvidence:
  evidenceId:
  baselineId:
  deploymentId:
  serviceId:
  guid:
  scenario:
  expected:
  actual:
  result:
  metrics:
  logs:
  owner:
  hash:
```

---

# 199. Master GAP Registry

```yaml
gap:
  id:
  architectureLayer:
  expected:
  actual:
  source:
  severity:
  impact:
  owner:
  adr:
  dueDate:
  status:
```

---

# 200. Master ADR Registry

```yaml
adr:
  id:
  title:
  status:
  context:
  decision:
  alternatives:
  consequences:
  affectedLayers:
  affectedServices:
  evidence:
  owner:
  approvedAt:
```

---

# 201. Integrated Baseline Conformance Rules

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
R-RUNTIME-INVENTORY
R-RUNTIME-EVIDENCE
R-DR-EVIDENCE
R-RESTORE-EVIDENCE
```

---

# 202. Integrated Gate Rule

## FIG-IB-72. Baseline Gate Rule

```text
Critical Rule Fail
= 0

Critical Drift
= 0

Critical Unknown
= 0

Critical Runtime Evidence Missing
= 0
```

이 조건들은 Release Policy 후보이며 실제 프로젝트 승인 후 확정한다.

---

# 203. Quality Gate Dashboard

```text
Rule Pass %
Critical Fail
Critical Drift
Open Critical GAP
Unknown Critical
Evidence Coverage
Trace Coverage
```

---

# 204. Architecture Review Checklist — Naming

```text
[ ] Application Group?
[ ] Business Code?
[ ] Function?
[ ] Program ID?
[ ] ServiceId?
[ ] Java Package?
[ ] Mapper Package?
[ ] Artifact?
```

---

# 205. Review Checklist — ServiceId

```text
[ ] Format?
[ ] Unique?
[ ] Handler Registered?
[ ] Branch?
[ ] UI Catalog?
[ ] Endpoint?
[ ] Monitoring?
[ ] Owner?
```

---

# 206. Review Checklist — Source Trace

```text
[ ] Handler?
[ ] Facade?
[ ] Service?
[ ] DAO?
[ ] Mapper?
[ ] SqlId?
[ ] Table/View?
```

---

# 207. Review Checklist — Transaction

```text
[ ] Runtime Entry?
[ ] TX Owner?
[ ] TransactionManager?
[ ] Datasource?
[ ] Rollback?
[ ] Timeout?
[ ] Late Commit?
```

---

# 208. Review Checklist — Security

```text
[ ] Authentication?
[ ] Authorization?
[ ] Algorithm?
[ ] kid?
[ ] Key Source?
[ ] Identity Binding?
[ ] Revocation?
[ ] Sensitive Log?
```

---

# 209. Review Checklist — Deployment

```text
[ ] Commit?
[ ] Artifact?
[ ] Hash?
[ ] DeploymentId?
[ ] Environment?
[ ] Host?
[ ] JVM?
[ ] Config Version?
```

---

# 210. Review Checklist — Runtime Evidence

```text
[ ] Scenario?
[ ] ServiceId?
[ ] GUID?
[ ] Expected?
[ ] Actual?
[ ] Metrics?
[ ] Logs?
[ ] Result?
[ ] Evidence Hash?
```

---

# 211. Review Checklist — HA / DR

```text
[ ] Node Failover?
[ ] Residual Capacity?
[ ] DR Artifact?
[ ] DR Config?
[ ] DR Key?
[ ] DR Data?
[ ] DR Business Test?
[ ] Restore Test?
```

---

# 212. Review Checklist — Drift

```text
[ ] Document?
[ ] Model?
[ ] Source?
[ ] Config?
[ ] Deployment?
[ ] Runtime?
[ ] Security?
[ ] Data?
```

---

# 213. Review Checklist — Gate

```text
[ ] G00?
[ ] G10?
[ ] G20?
[ ] G30?
[ ] G40?
[ ] G50?
[ ] G60?
[ ] G70?
[ ] G80?
[ ] HG90?
```

---

# 214. Integrated Architecture Completion Gate

## FIG-IB-73. Final Completion

```text
VISION defined?
  ↓
BIG PICTURE defined?
  ↓
LOGICAL defined?
  ↓
PHYSICAL defined?
  ↓
MECHANISM defined?
  ↓
RUNTIME defined?
  ↓
TRACEABILITY complete?
  ↓
SOURCE reference verified?
  ↓
OPERATIONS ready?
  ↓
MODEL complete?
  ↓
RULE/Test PASS?
  ↓
RUNTIME EVIDENCE?
  ↓
DRIFT reviewed?
  ↓
ADR approved?
  ↓
HG90 RELEASE
```

---

# 215. 전체 10장 Architecture Journey

## FIG-IB-74. 01 → 10

```text
01 VISION
왜 바꾸는가?
   ↓
02 BIG PICTURE
누가 무엇을 책임하는가?
   ↓
03 LOGICAL
어떤 논리 구조인가?
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
어떻게 검증하고 유지하는가?
   ↓
08 PDMG SOURCE REFERENCE
실제 구현은 어떻게 되어 있는가?
   ↓
09 OM / DEVOPS / OBSERVABILITY
어떻게 배포·관찰·복구하는가?
   ↓
10 INTEGRATED BASELINE
어떻게 하나의 승인 가능한 Architecture Baseline으로 묶는가?
```

---

# 216. Architecture Storyline

## FIG-IB-75. One Story

```text
WHY
Vision
 ↓
WHO / WHAT
Big Picture
 ↓
HOW STRUCTURED
Logical
 ↓
WHERE
Physical
 ↓
HOW CONTROLLED
Mechanism
 ↓
HOW EXECUTED
Runtime
 ↓
HOW PROVEN
Traceability
 ↓
WHAT EXISTS
PDMG Source
 ↓
HOW OPERATED
Operations
 ↓
HOW RELEASED
Integrated Baseline
```

---

# 217. 최종 Top-down / Bottom-up 통합

## FIG-IB-76. Final Closed Loop

```text
                   TOP-DOWN
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
──────────────────────────
      RUNTIME EVIDENCE
──────────────────────────
  ↑
DEPLOYMENT
  ↑
ARTIFACT
  ↑
SOURCE / CONFIG
  ↑
MODEL / TRACE
  ↑
DRIFT / GAP
  ↑
ADR
  ↑
NEW BASELINE
                 BOTTOM-UP
```

---

# 218. 최종 핵심 Architecture 원칙

```text
1. Responsibility는 경계에 고정한다.
2. 연결은 목적별 Interface로 통제한다.
3. Naming은 Classification과 Traceability를 지원한다.
4. ServiceId는 Business Transaction의 단일 식별축으로 관리한다.
5. Module / Process / Node / JVM / WAR를 혼동하지 않는다.
6. Framework와 Business 책임을 분리한다.
7. Transaction / Timeout / Retry를 분리하고 Runtime으로 검증한다.
8. Security Identity는 Trusted Principal에서 유도한다.
9. Runtime은 Metric/Log/Trace/Evidence로 관찰 가능해야 한다.
10. Architecture는 Source/Runtime Evidence와 Closed Loop를 이뤄야 한다.
```

---

# 219. CONFIRMED / WORKING BASELINE

```text
[CONFIRMED / WORKING BASELINE]

- NSIGHT Architecture 흐름은 Vision→Big Picture→Logical→Physical→Mechanism→Runtime
- PDMG는 Source/Runtime Reference
- Application/Business/Function/Program/ServiceId Naming 축
- PDMG Current Handler Registry = 13 ServiceIds
- Handler→Facade→Service→DAO→Mapper의 대표 Source Trace
- Java Package = nhnis.mg.co.a
- Mapper Package = rdw.mg.co.a
- GUID = std_gbl_id
- PDMG timeout snapshot = 5000ms / Worker20 / Queue100
- Source→Artifact→Deployment→Runtime Evidence Trace 필요
- G00→G80→HG90 Gate
- Architecture Baseline = Document + Model + Rule + Evidence + Approval
```

---

# 220. CONFLICT / DRIFT / UNKNOWN

```text
[CONFLICT]
CDC SLA 30s vs 3s

[CONFLICT]
Session 60m vs 90m

[DRIFT]
Filter order old doc vs current source

[DRIFT]
Past ServiceId count 8 vs current 13

[CRITICAL GAP]
RS256 issuer vs HMAC verifier

[UNKNOWN]
pdmg-om actual implementation

[OPEN]
Production deployment mapping
```

---

# 221. GAP Register

| ID | GAP | 영향 |
|---|---|---|
| GAP-IB-01 | ServiceId SSOT 미완료 | Naming/Trace |
| GAP-IB-02 | UI Catalog↔Backend Registry 자동비교 미완료 | Trace |
| GAP-IB-03 | Handler→SQL→Table 전수 Trace 미완료 | Data |
| GAP-IB-04 | Architecture Model Schema 미확정 | Model |
| GAP-IB-05 | Architecture Rule Engine 전수구현 미완료 | Governance |
| GAP-IB-06 | Query/TX Timeout 미확정 | Runtime |
| GAP-IB-07 | TCF OFF Business Boundary 불일치 | Application |
| GAP-IB-08 | JWT Algorithm/Verifier 불일치 | Security |
| GAP-IB-09 | JWT Key Lifecycle 미완료 | Security |
| GAP-IB-10 | Identity Binding 미완료 | Security |
| GAP-IB-11 | Deployment Manifest 미완료 | Deployment |
| GAP-IB-12 | Runtime Inventory 미완료 | Operations |
| GAP-IB-13 | Runtime Evidence Manifest 자동화 미완료 | Evidence |
| GAP-IB-14 | Config/Deployment Drift 자동화 미완료 | Drift |
| GAP-IB-15 | pdmg-om Source/Runtime 미확인 | OM |
| GAP-IB-16 | DR/Restore Evidence 자동연계 미완료 | Availability |
| GAP-IB-17 | Gate RACI 미확정 | Governance |
| GAP-IB-18 | Baseline Versioning Rule 미확정 | Release |
| GAP-IB-19 | Critical Service Evidence Coverage 미확정 | Runtime |
| GAP-IB-20 | Principle P-05 누락 확인 필요 | Principle |

---

# 222. RISK Register

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-IB-01 | Naming Drift로 자동추적 실패 | High |
| RISK-IB-02 | ServiceId SSOT 불일치 | High |
| RISK-IB-03 | Handler→DAO 직접침투 | High |
| RISK-IB-04 | Mapper/SQL Trace 누락 | High |
| RISK-IB-05 | TX Owner 오판 | Critical |
| RISK-IB-06 | Timeout Late Commit | Critical |
| RISK-IB-07 | JWT Algorithm/Key 불일치 | Critical |
| RISK-IB-08 | Identity Mismatch | Critical |
| RISK-IB-09 | Wrong Artifact Production | Critical |
| RISK-IB-10 | Runtime Evidence 미연결 | Critical |
| RISK-IB-11 | Critical Drift 미탐지 | Critical |
| RISK-IB-12 | UNKNOWN 장기 방치 | High |
| RISK-IB-13 | Gate 형식화 | Critical |
| RISK-IB-14 | PDMG AS-IS의 무검증 Target 승격 | High |
| RISK-IB-15 | Baseline Release 후 Change Drift | High |

---

# 223. ADR 후보

```text
ADR-IB-01 Classification / Naming Standard
ADR-IB-02 ServiceId SSOT
ADR-IB-03 ServiceId Registry Governance
ADR-IB-04 Package/Mapper Naming
ADR-IB-05 Source Trace Model
ADR-IB-06 Architecture Model Schema
ADR-IB-07 Rule Engine
ADR-IB-08 Transaction Ownership
ADR-IB-09 Timeout Budget
ADR-IB-10 TCF ON/OFF Business Core
ADR-IB-11 JWT Algorithm Alignment
ADR-IB-12 JWT Key Lifecycle
ADR-IB-13 Identity Binding
ADR-IB-14 Deployment Manifest
ADR-IB-15 Runtime Inventory
ADR-IB-16 Runtime Evidence
ADR-IB-17 Drift Automation
ADR-IB-18 Gate RACI
ADR-IB-19 Baseline Versioning
ADR-IB-20 Principle P-05 Resolution
```

---

# 224. Definition of Done

## Classification / Naming
- [x] Service Domain/Application Group 분리
- [x] Business/Function/Program/ServiceId 계층
- [x] Java/Mapper/ServiceId Naming 축
- [x] Identifier Taxonomy

## ServiceId
- [x] Anatomy
- [x] Regex
- [x] Current 13 Registry
- [x] Unique/Branch Rule
- [x] Handler Routing
- [x] SSOT 방향

## Source Trace
- [x] Handler→Facade→Service→DAO→Mapper
- [x] Mapper Namespace
- [x] SqlId
- [x] SQL→Table
- [x] Program Stem

## Deployment / Runtime Trace
- [x] Source→Artifact
- [x] Artifact→Deployment
- [x] Deployment→Runtime
- [x] GUID/ServiceId
- [x] Full Forward/Reverse Trace

## Model
- [x] Entity
- [x] Relation
- [x] Deployment Relation
- [x] Runtime Relation
- [x] Evidence Relation
- [x] Model Version/Diff

## Rule / Test
- [x] Naming
- [x] ServiceId
- [x] Dependency
- [x] Mapper
- [x] TX/Timeout
- [x] Security
- [x] Deployment
- [x] Runtime Evidence

## Evidence / Drift
- [x] Evidence Chain
- [x] Evidence Manifest
- [x] Evidence Strength
- [x] State Tag
- [x] GAP/DRIFT/CONFLICT
- [x] ADR/Debt

## Gate / Release
- [x] G00~HG90
- [x] Workspace 00-IN~90-OUT
- [x] Baseline Package
- [x] Baseline Diff
- [x] Review Package
- [x] KPI
- [x] Completion Gate

**INTEGRATED ARCHITECTURE BASELINE 장 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. ServiceId SSOT 확정
2. UI Catalog↔Handler Registry 자동비교
3. Handler→SqlId→Table 자동 Trace
4. Architecture Model Schema 확정
5. Rule Engine / CI Gate 적용
6. Query/TX Timeout 실제값 확정
7. TCF OFF Business Core 정합성 확보
8. JWT Issuer/Verifier Algorithm 정합
9. JWT Key Lifecycle / Identity Binding 해결
10. Deployment Manifest 자동화
11. Runtime Inventory 자동수집
12. Runtime Evidence Manifest 자동화
13. Drift 자동탐지
14. pdmg-om Source/Runtime 확인
15. DR/Restore Evidence 연결
16. Gate RACI 승인
17. Baseline Versioning Rule 승인
18. Critical Drift 0
19. Critical Unknown 0
20. Critical Runtime Evidence Coverage 기준 충족

---

# 225. 최종 결론

> **10장은 01~09장을 한 권의 Architecture Baseline으로 묶는 장이다.**

> **Naming은 단순 규칙이 아니라 Application→Program→ServiceId→Package→Mapper→Runtime을 연결하는 Traceability Backbone이다.**

> **ServiceId는 PDMG에서 실제 Dispatcher Routing Key로 동작하므로, Source·Data·Deployment·Monitoring·Runtime Evidence를 연결하는 핵심 Business Transaction Key로 관리할 가치가 크다.**

> **Architecture Baseline은 문서가 아니라 Document + Model + Rule + Source + Deployment + Runtime Evidence + Drift + ADR + Approval의 결합물이다.**

> **최종적으로 NSIGHT Architecture는 Top-down 설계와 Bottom-up Evidence가 HG90 Baseline Release에서 만나고, 이후 Runtime Drift가 다시 다음 Baseline을 만드는 지속적 Closed Loop로 운영되어야 한다.**
