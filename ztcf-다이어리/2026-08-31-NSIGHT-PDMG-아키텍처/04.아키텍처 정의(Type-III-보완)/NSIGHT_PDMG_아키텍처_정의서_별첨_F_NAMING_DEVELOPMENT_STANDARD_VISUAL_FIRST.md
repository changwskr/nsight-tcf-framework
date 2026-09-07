# NSIGHT / PDMG 아키텍처 정의서
# 별첨 F. NAMING DEVELOPMENT STANDARD
## Business / Application / Program / ServiceId / Package / Class / Mapper / SQL / Interface / Deployment Naming
## Visual-First / Traceability-Driven / Source-Conformance / Standalone Appendix

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-NAMING-APPENDIX-F`  
> 문서 유형: **별첨 / 독립 Naming Development Standard**  
> 문서 상태: **Draft / Evidence-First / Visual-First / Rule-Driven**  
> 작성 기준일: **2026-08-31**  
> 연계 장: `08 PDMG SOURCE REFERENCE`, `10 INTEGRATED BASELINE`, `11 DEVELOPMENT STANDARD`  
> 연계 별첨: `A Application`, `B Technical`, `C Infrastructure`, `D Interface`, `E Data`

---

# 0. 이 별첨의 목적

## FIG-NAM-01. Naming Standard가 답해야 하는 질문

```text
Business Classification
        ↓
어떤 식별자를 부여할 것인가?
        ↓
Program / Service / Interface / Data / Runtime Object를
어떻게 같은 축으로 연결할 것인가?
        ↓
Source Package / Class / Mapper / SqlId를
어떻게 추적 가능하게 할 것인가?
        ↓
Artifact / Deployment / Host / JVM까지
어떻게 연결할 것인가?
        ↓
규칙 위반을 CI에서 어떻게 검증할 것인가?
```

> **Naming Development Standard는 이름을 예쁘게 통일하는 규칙이 아니라, Business → Application → Program → ServiceId → Source → SQL/Data → Artifact → Deployment → Runtime Evidence를 하나의 식별축으로 연결하는 Traceability Standard다.**

---

# 1. Naming Standard 한 문장 정의

## FIG-NAM-02. Definition

```text
Business Meaning
    ↓
Classification Code
    ↓
Program / Service Identity
    ↓
Source Naming
    ↓
Data / Interface Naming
    ↓
Deployment Naming
    ↓
Runtime Trace
```

---

# 2. Naming Standard가 아닌 것

## FIG-NAM-03. Not Naming

```text
Naming Standard
≠ CamelCase 규칙만의 문서

Naming Standard
≠ 클래스 접미어 목록

Naming Standard
≠ Hostname 규칙만의 문서

Naming Standard
≠ DB 컬럼 약어집만의 문서
```

Naming은 **식별·소유·Traceability·자동검증**까지 포함한다.

---

# 3. Naming의 최상위 목적

## FIG-NAM-04. Naming Objectives

```text
Understandability
      +
Uniqueness
      +
Consistency
      +
Traceability
      +
Automation
      +
Governance
```

---

# 4. Naming Backbone

## FIG-NAM-05. Master Naming Backbone

```text
Business Group
  ↓
Business Code
  ↓
Function Code
  ↓
Program ID
  ↓
ServiceId
  ↓
Java Package
  ↓
Handler / Facade / Service / DAO / DTO
  ↓
Mapper / SqlId
  ↓
Table / View
  ↓
InterfaceId / Event / Job
  ↓
Artifact
  ↓
DeploymentId
  ↓
Host / JVM
  ↓
Runtime Evidence
```

---

# 5. Naming 상태태그

## FIG-NAM-06. Evidence State

```text
[AS-IS]
PDMG Source / Config에서 확인

[CONFIRMED]
승인된 공식 표준

[TO-BE]
목표 표준

[PROPOSED]
승인 전 후보

[GAP]
규칙/정합 부족

[OPEN]
결정 필요

[UNKNOWN]
근거 없음
```

---

# 6. Naming Rule Severity

## FIG-NAM-07. Severity

```text
MUST
  → 위반 금지

MUST NOT
  → 명시적 금지

SHOULD
  → 원칙적 준수, 예외 사유 필요

MAY
  → 선택 가능
```

---

# 7. Naming 적용범위

## FIG-NAM-08. Scope

```text
Business Naming
Application Naming
Program Naming
ServiceId
Package
Class
DTO
Mapper
SqlId
DB Object
InterfaceId
Event
File
Batch
Artifact
Deployment
Host / VM / JVM
Port / Datasource
Config
Test
Evidence
```

---

# 8. Naming 설계원칙 — Business 축을 잃지 않는다

## FIG-NAM-09. Business Axis

```text
Business Classification
        ↓
Program
        ↓
ServiceId
        ↓
Source Package / Class
```

### MUST

기술이름이 Business 분류와 단절되지 않아야 한다.

---

# 9. Naming 설계원칙 — Exact Key

## FIG-NAM-10. Exact Key

```text
ServiceId
mgcoa9001S0

        ↓ exact

Registry
        ↓ exact
Handler Branch
        ↓ exact
Monitoring / Test
```

### MUST NOT

```text
upper/lower 자동변환
trim 자동보정
임의 prefix 제거
alias 자동매핑
```

으로 식별자를 묵시적으로 교정하지 않는다.

---

# 10. Naming 설계원칙 — 하나의 이름은 하나의 의미

## FIG-NAM-11. One Meaning

```text
ProgramId
= Program Identity

ServiceId
= Transaction Identity

InterfaceId
= Interface Contract Identity

GUID
= Runtime Execution Identity
```

서로 대체하지 않는다.

---

# 11. Naming 설계원칙 — 이름만으로 모든 것을 담지 않는다

## FIG-NAM-12. Identity vs Metadata

```text
Identifier
  ↓
Stable Identity

Metadata
  ↓
Owner / Version / Description / Environment / Status
```

환경/버전/상태를 무조건 Identifier 문자열에 과도하게 포함하지 않는다.

---

# 12. Business Classification Naming

## FIG-NAM-13. Classification

```text
Major / Application
      ↓
Business
      ↓
Function
      ↓
Program
```

PDMG 대표 축:

```text
MG / CO / A / 9001
```

---

# 13. Major/Application Code

## FIG-NAM-14. Major Code

```text
MG
```

### [AS-IS]

PDMG Marketing Group 계열에서 `mg` prefix가 확인된다.

---

# 14. Business Code

## FIG-NAM-15. Business Code

```text
CO
IC
PC
BC
MS
SA
PD
CM
EB
EP
BP
BD
SS
CS
CT
MG
OM
```

실제 승인 업무코드 Registry를 SSOT로 관리해야 한다.

---

# 15. Function Code

## FIG-NAM-16. Function Code

```text
Business Code
   ↓
Function Code
   ↓
Program Number
```

대표:

```text
A
```

Function Code 의미표는 업무별 Registry로 관리한다.

---

# 16. Program Number

## FIG-NAM-17. Program Number

```text
0000 ~ 9999
```

대표:

```text
9001
```

### [AS-IS]

4자리 숫자 사용이 확인된다.

---

# 17. Program ID

## FIG-NAM-18. Program ID Anatomy

```text
mg | co | a | 9001
│    │    │     │
│    │    │     └─ Program Number
│    │    └─────── Function
│    └──────────── Business
└───────────────── Major/Application
```

결과:

```text
mgcoa9001
```

---

# 18. Program ID Length

## FIG-NAM-19. Program Length

```text
2 + 2 + 1 + 4
= 9 chars
```

### [AS-IS]

PDMG 대표 Program Naming 축.

---

# 19. Program ID Rule

### NAM-PROG-01 [MUST]

```text
^[a-z]{2}[a-z]{2}[a-z][0-9]{4}$
```

후보 Regex를 Source Scanner와 정합시킨다.

---

# 20. Program ID Case

## FIG-NAM-20. Case

```text
Program ID
→ lowercase
```

대표:

```text
mgcoa9001
```

### [AS-IS]

소문자 식별축 사용.

---

# 21. ServiceId

## FIG-NAM-21. ServiceId Anatomy

```text
mg | co | a | 9001 | S | 0
│    │    │    │      │   │
│    │    │    │      │   └─ Sequence
│    │    │    │      └──── Transaction Type
│    │    │    └─────────── Program Number
│    │    └──────────────── Function
│    └───────────────────── Business
└────────────────────────── Major/Application
```

---

# 22. ServiceId Length

```text
2 + 2 + 1 + 4 + 1 + 1
= 11 chars
```

대표:

```text
mgcoa9001S0
```

---

# 23. ServiceId General Regex

## FIG-NAM-22. General Regex

```text
^[a-z]{2}[a-z]{2}[a-z][0-9]{4}[SCUDAR][0-9A-Z]$
```

---

# 24. MG ServiceId Regex

```text
^mg[a-z]{2}[a-z][0-9]{4}[SCUDAR][0-9A-Z]$
```

---

# 25. Transaction Type

## FIG-NAM-23. Transaction Type

```text
S = Select / Query
C = Create
U = Update
D = Delete
A = Action
R = Reserved / Reference Candidate
```

### [AS-IS]

대표 Handler 실제 사용:

```text
S / C / U / D
```

---

# 26. Sequence

## FIG-NAM-24. Sequence

```text
0 ~ 9 / A ~ Z
```

대표:

```text
0
```

정확한 확장규칙은 ServiceId Registry Standard에서 승인한다.

---

# 27. ServiceId Unique Rule

### NAM-SVC-01 [MUST]

```text
ServiceId
  ↓
Registry
  ↓
Unique
```

Duplicate는 기동 실패 또는 CI Fail 대상.

---

# 28. ServiceId Registry

## FIG-NAM-25. Registry SSOT

```text
Architecture Model
      ↓
ServiceId Registry
      ├─ Backend Handler
      ├─ UI Catalog
      ├─ Test Catalog
      ├─ Monitoring Catalog
      └─ Documentation
```

---

# 29. Current PDMG ServiceId Reference

## FIG-NAM-26. Current Registry

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

### [AS-IS]

Current Source 분석 기준 13 ServiceIds.

---

# 30. Handler Registration Naming

## FIG-NAM-27. Handler Registration

```text
Handler
  ↓
serviceIds()
  ↓
Registry
```

ServiceId와 Handler명은 가능한 한 같은 Program Stem을 공유한다.

---

# 31. Handler Naming

## FIG-NAM-28. Handler

대표 Pattern:

```text
mgcoa9001Handler
```

### [AS-IS / Pattern]

```text
<programId>Handler
```

---

# 32. Facade Naming

## FIG-NAM-29. Facade

대표:

```text
mgcoa9001Facade
```

Pattern:

```text
<programId>Facade
```

---

# 33. Service Naming

## FIG-NAM-30. Service

대표:

```text
mgcoa9001Service
```

Pattern:

```text
<programId>Service
```

---

# 34. DAO Naming

## FIG-NAM-31. DAO

대표:

```text
mgcoa9001DAO
```

Pattern:

```text
<programId>DAO
```

---

# 35. DTO Naming

## FIG-NAM-32. DTO

대표:

```text
mgcoa9000S0DTOin
mgcoa9000S0DTOout
```

Pattern:

```text
<serviceId>DTOin
<serviceId>DTOout
```

---

# 36. DTO Direction

```text
DTOin
= Input

DTOout
= Output
```

### NAM-DTO-01 [SHOULD]

Direction이 식별 가능하도록 한다.

---

# 37. Controller Naming

## FIG-NAM-33. Controller

Business Controller 후보:

```text
<programId>Controller
```

공통 Online Controller는 Business Program Stem을 강제하지 않는다.

---

# 38. Rule Naming

## FIG-NAM-34. Rule

Rule Layer 사용 시 후보:

```text
<BusinessMeaning>Rule
```

또는 Program 기반:

```text
<programId>Rule
```

### [PROPOSED]

Rule Naming은 Rule Layer Target 결정 후 확정.

---

# 39. Exception Class Naming

## FIG-NAM-35. Exception

```text
<DomainMeaning>Exception
```

예:

```text
BizException
ServiceHandlerNotFoundException
OnlineTimeoutException
```

Error Code와 Class Name은 별도 식별축이다.

---

# 40. Package Root

## FIG-NAM-36. Package Root

대표:

```text
nhnis.mg
```

### [AS-IS]

---

# 41. Business Package

## FIG-NAM-37. Package Projection

```text
MG / CO / A
    ↓
nhnis.mg.co.a
```

---

# 42. Package Rule

### NAM-PKG-01 [MUST]

```text
Business Classification
      ↓
Java Package
```

축을 유지한다.

---

# 43. Package Case

```text
lowercase
```

### MUST

Java Package는 소문자로 유지한다.

---

# 44. Layer Package

## FIG-NAM-38. Layer Package

대표 구조:

```text
entry
application
dto
persistence
```

하위:

```text
handler
controller
facade
service
dao
```

---

# 45. Package Anti-pattern

## FIG-NAM-39. Package Anti-pattern

```text
misc
temp
etc
common2
util2
new
old
```

의미 없는 Package를 생성하지 않는다.

---

# 46. Common Package

```text
common
```

사용 시 실제 Cross-Cutting 책임이 있어야 한다.

### MUST NOT

특정 업무를 `common`에 숨기지 않는다.

---

# 47. Mapper Resource Root

## FIG-NAM-40. Mapper Resource

대표:

```text
rdw.mg.co.a/
```

---

# 48. Mapper File Naming

대표:

```text
mgcoa9000-ORA.xml
```

Pattern:

```text
<programId>-<DBMS>.xml
```

### [AS-IS Pattern]

---

# 49. DBMS Suffix

## FIG-NAM-41. DBMS Suffix

```text
-ORA
```

대표 Oracle Mapper에서 확인된 Pattern.

다른 DBMS suffix는 실제 표준 확인 전 `[OPEN]`.

---

# 50. Mapper Namespace

## FIG-NAM-42. Namespace

```text
Java DAO FQCN
        │
        │ exact
        ▼
Mapper XML namespace
```

### NAM-MAP-01 [MUST]

DAO FQCN과 Mapper Namespace 일치.

---

# 51. SqlId Naming

## FIG-NAM-43. SqlId

대표:

```text
mgcoa9000S0_S0
mgcoa9000S0_COUNT
mgcoa9000C0_C0
mgcoa9000U0_U0
mgcoa9000D0_D0
```

---

# 52. SqlId Rule

Pattern 후보:

```text
<serviceId>_<statementRole>
```

---

# 53. Statement Role

```text
S0
C0
U0
D0
COUNT
```

### [AS-IS Pattern]

세부 Role Naming은 Mapper Inventory로 검증한다.

---

# 54. DAO Method와 SqlId

## FIG-NAM-44. DAO / SqlId

```text
DAO Method
  ↓
SqlId
```

가능한 한 동작의 의미가 대응되어야 한다.

---

# 55. SQL Naming Trace

```text
ServiceId
  ↓
DAO
  ↓
SqlId
  ↓
Table / View
```

---

# 56. Table Logical Naming

## FIG-NAM-45. Table Naming

```text
Standard Term
  ↓
Logical Entity Name
  ↓
Physical Table Name
```

정확한 Physical Naming 규칙은 Data Standard SSOT를 따른다.

---

# 57. Column Naming

## FIG-NAM-46. Column

```text
Standard Term
  ↓
Logical Attribute
  ↓
Physical Column
```

---

# 58. DB Object Naming Scope

```text
Table
View
Index
Sequence
Constraint
Schema
Partition
```

각 Object 유형별 Naming Standard가 필요하다.

---

# 59. Table Naming 상태
## TEXT ARCHITECTURE 보완 — Table Naming 결정상태

```text
Enterprise Data Naming Evidence
          ↓
충분한가?
 ├─ YES → [CONFIRMED] Rule
 └─ NO  → [OPEN]
             ↓
         Data Standard Review
             ↓
         ADR / Approval
```


### [OPEN]

현재 대화 기준으로 Enterprise-wide Table Prefix/Length 규칙을 확정할 근거가 부족하다.

따라서 임의 규칙을 생성하지 않는다.

---

# 60. Index Naming

## FIG-NAM-47. Index Naming

```text
Index
  ↓
Target Table
  ↓
Purpose / Column
```

정확한 Prefix/Suffix는 DB Naming Standard에서 확정.

---

# 61. Constraint Naming

```text
PK
FK
UK
CK
```

의 유형과 Target Object를 식별 가능하게 한다.

---

# 62. Sequence Naming

```text
Sequence
  ↓
Owning Entity / Purpose
```

---

# 63. View Naming

```text
View
  ↓
Business / Consumer Purpose
```

Table과 구분 가능한 Rule이 필요하다.

---

# 64. InterfaceId Naming

## FIG-NAM-48. InterfaceId

```text
InterfaceId
= System-to-System Contract Identity
```

### [OPEN]

최종 Enterprise InterfaceId 문자열 규칙은 별도 승인 필요.

---

# 65. InterfaceId 구성 후보

## FIG-NAM-49. InterfaceId Candidate

```text
Source / Domain
  +
Target / Purpose
  +
Sequence
```

### [PROPOSED]

정확한 자리수/구분자는 Catalog 분석 후 확정.

---

# 66. Interface Name

```text
<Source>_<Purpose>_<Target>
```

와 같이 사람이 이해 가능한 Display Name을 별도 둘 수 있다.

---

# 67. ServiceId와 InterfaceId 관계

## FIG-NAM-50. Service / Interface

```text
ServiceId
  ↓ uses
InterfaceId
```

둘은 동일 Identifier가 아니다.

---

# 68. EventType Naming

## FIG-NAM-51. EventType

```text
Business Domain
  ↓
Business Event
  ↓
Version
```

후보:

```text
<CustomerChanged>
<CampaignStarted>
```

문자열 상세규칙은 Event Schema Standard와 함께 확정.

---

# 69. Topic Naming

## FIG-NAM-52. Topic

```text
Domain
  ↓
Event / Stream Purpose
  ↓
Environment? [avoid if platform separates]
```

환경명을 Topic에 넣을지 여부는 Platform 운영정책에 따라 결정한다.

---

# 70. Consumer Group Naming

```text
Application / Consumer Responsibility
```

을 식별 가능하게 한다.

---

# 71. Event Schema Name

```text
<EventType> + Version
```

을 추적 가능하게 한다.

---

# 72. File Interface Naming

## FIG-NAM-53. File Naming

```text
Business Purpose
  +
Business Date
  +
Sequence
  +
Extension
```

정확한 구분자/자리수는 File Interface Standard로 확정.

---

# 73. File Naming 필수 고려

```text
InterfaceId
Business Date
Sequence
Compression
Encryption
Extension
```

---

# 74. File Temporary Naming

```text
.tmp
.partial
```

과 같은 In-progress 상태표현은 운영정책과 합의한다.

---

# 75. File Archive Naming

```text
Original Name
  +
Archive Timestamp / Batch Id
```

후보.

---

# 76. Batch Job Naming

## FIG-NAM-54. Batch Job

```text
Business Domain
  ↓
Job Purpose
  ↓
JobId
```

---

# 77. Batch Step Naming

```text
<JobId>.<StepMeaning>
```

처럼 Job과 Step 관계가 식별 가능해야 한다.

---

# 78. JobRepository Naming

JobRepository Metadata ID와 Business JobId는 별도 개념이다.

```text
Business JobId
≠ Spring Batch Execution Id
```

---

# 79. Scheduler Naming

## FIG-NAM-55. Scheduler

```text
Scheduler Entry
  ↓
Business JobId
```

---

# 80. Cache Naming

## FIG-NAM-56. Cache

```text
Application / Domain
  ↓
Cache Purpose
  ↓
Cache Name
```

---

# 81. Cache Key Naming

```text
<BusinessKey>
```

또는 복합:

```text
<Domain>:<BusinessKey>
```

### [PROPOSED]

Cache 제품/운영표준에 따라 확정.

---

# 82. Cache Key 금지

```text
PII raw value
Secret
Unbounded arbitrary string
```

를 Key로 직접 사용하지 않는다.

---

# 83. Configuration Property Naming

## FIG-NAM-57. Config Property

대표:

```yaml
nhnis:
  fw:
    tcf:
      enabled:
    timeout:
      enabled:
      milliseconds:
      pool-size:
      queue-capacity:
```

---

# 84. Config Prefix

### [AS-IS]

PDMG Framework:

```text
nhnis.fw.*
```

---

# 85. Config Naming Rule

```text
Organization / Product
  ↓
Capability
  ↓
Feature
  ↓
Property
```

---

# 86. Boolean Property

```text
enabled: true/false
```

처럼 의미를 명확히 한다.

---

# 87. Time Property Naming

## FIG-NAM-58. Time Unit

```text
timeout:
  milliseconds: 5000
```

### MUST

시간 단위가 이름 또는 Schema에서 명확해야 한다.

---

# 88. Size Property Naming

```text
pool-size
queue-capacity
```

와 같이 단위를 혼동하지 않는다.

---

# 89. Environment Property
## TEXT ARCHITECTURE 보완 — Environment Property

```text
Common Property Name
        ↓
Environment Overlay
   ├─ DEV Value
   ├─ TEST Value
   ├─ PROD Value
   └─ DR Value
```

Property 이름 자체에 환경명을 중복 삽입하기보다 환경 Overlay에서 값을 분리한다.


환경명은 값/Overlay로 관리하고,
Property Name 자체에 DEV/PROD를 중복 삽입하지 않는 것을 원칙으로 한다.

---

# 90. Secret Property Naming

## FIG-NAM-59. Secret Config

```text
secretRef
keyRef
credentialRef
```

등 Reference 표현을 사용한다.

### MUST NOT

```text
password: <plain>
privateKey: <plain>
```

---

# 91. Error Code Naming

## FIG-NAM-60. Error Code

```text
Domain / Category
  ↓
Error Code
  ↓
Stable Meaning
```

PDMG Current 예:

```text
FW_TIMEOUT
FW_OVERLOADED
E9999
```

---

# 92. Error Code vs Exception

```text
Exception Class
= Internal Runtime Type

Error Code
= External / Contract Meaning
```

---

# 93. Error Code Rule
## TEXT ARCHITECTURE 보완 — Error Code Stability

```text
Error Code
   ↓
Stable Meaning
   ↓
Documented Contract
   ↓
Monitoring / Runbook
```

동일 Code를 다른 의미로 재사용하지 않는다.


### MUST

한 Error Code는 안정된 의미를 유지한다.

### MUST NOT

동일 Code에 서로 다른 의미를 재사용하지 않는다.

---

# 94. Log Field Naming

## FIG-NAM-61. Log Fields

```text
guid
serviceId
userId
host
jvm
thread
sqlId
errorCode
deploymentId
```

Central Logging 필드명은 SSOT로 통일한다.

---

# 95. MDC Key Naming

```text
guid
serviceId
```

대표 Context Key.

정확한 Key Catalog는 Logging Standard로 확정.

---

# 96. Metric Naming

## FIG-NAM-62. Metric

```text
Domain
  ↓
Resource / Service
  ↓
Metric
  ↓
Unit
```

---

# 97. Metric Label Naming

```text
serviceId
application
host
jvm
environment
errorCode
```

High-cardinality 위험을 고려한다.

---

# 98. Trace Attribute Naming

```text
service.id
interface.id
deployment.id
```

등 Semantic Convention을 정할 수 있다.

### [PROPOSED]

APM/OTel 표준 채택 시 구체화.

---

# 99. Artifact Naming

## FIG-NAM-63. Artifact

```text
Application / Module
  ↓
Artifact Name
  ↓
Version / Hash
```

---

# 100. WAR Naming

```text
<module-or-application>.war
```

후보.

Current 실제 Artifact명은 Build Inventory로 확인한다.

---

# 101. Artifact Version

```text
Semantic / Release Version
  +
BuildId
  +
Hash
```

중 최소 추적 가능한 조합을 사용한다.

---

# 102. Artifact Hash

```text
Artifact Name
  ↓
SHA-256
```

이름이 같아도 Hash가 다르면 다른 Artifact다.

---

# 103. DeploymentId Naming

## FIG-NAM-64. DeploymentId

```text
Environment
  +
Application / Artifact
  +
Sequence / Timestamp
```

### [PROPOSED]

정확한 형식은 Deployment Governance에서 확정.

---

# 104. Deployment Naming Rule

```text
DeploymentId
  ↓
sourceCommit
  ↓
buildId
  ↓
artifactHash
```

연결되어야 한다.

---

# 105. Release Naming

```text
Release
  ↓
Baseline Version
  ↓
Included Artifacts
```

---

# 106. Baseline ID

## FIG-NAM-65. Baseline ID

후보:

```text
NSIGHT-ARCH-YYYYMMDD-NN
```

### [PROPOSED]

조직 공식 Baseline Naming이 확인되면 대체한다.

---

# 107. EvidenceId Naming

## FIG-NAM-66. Evidence ID

```text
Evidence Type
  +
Target
  +
Execution / Date
```

### [PROPOSED]

Evidence Repository와 함께 확정.

---

# 108. Test Case Naming

## FIG-NAM-67. Test Case

```text
Layer / Feature
  ↓
Scenario
  ↓
Expected Result
```

---

# 109. Architecture Test Naming

```text
R-<DOMAIN>-<RULE>
```

대표:

```text
R-SERVICEID-FORMAT
R-SERVICEID-UNIQUE
R-HANDLER-NO-DAO
```

---

# 110. Rule ID Naming

## FIG-NAM-68. Rule ID

```text
R
-
Domain
-
Rule Meaning
```

예:

```text
R-NAMING-AXIS
R-DAO-MAPPER
R-JWT-ALGORITHM
```

---

# 111. GAP ID Naming

```text
GAP-<DOMAIN>-NN
```

예:

```text
GAP-NAM-01
```

---

# 112. RISK ID Naming

```text
RISK-<DOMAIN>-NN
```

---

# 113. ADR ID Naming

```text
ADR-<DOMAIN>-NN
```

---

# 114. Requirement ID Mapping
## TEXT ARCHITECTURE 보완 — Requirement Trace

```text
RequirementId
   ↓
ProgramId
   ↓
ServiceId
   ↓
Source / Test
   ↓
Runtime Evidence
```

Requirement ID 형식 자체는 PMO/Requirements Standard를 따르고 Naming Standard는 연결을 관리한다.


Requirement ID 자체의 공식 형식은 PMO/Requirements Standard를 따른다.

Naming Standard는 **연결관계**를 관리한다.

---

# 115. Git Repository Naming

## FIG-NAM-69. Repository

대표:

```text
nsight-tcf-framework
```

Repository Naming은 Product/System Scope를 명확히 표현해야 한다.

---

# 116. Branch Naming

## FIG-NAM-70. Branch

현재 과거 예:

```text
f-20250628-routing
f-20260630-ssotoken
```

### [AS-IS Historical Pattern]

최종 Branch Naming Standard는 SCM Governance에서 확정한다.

---

# 117. Branch Naming 후보

```text
feature/<issue>-<meaning>
fix/<issue>-<meaning>
release/<version>
```

### [PROPOSED]

기존 Branch 관행과 충돌 여부 검토 필요.

---

# 118. Commit Message Naming

```text
<type>: <meaning> [issue]
```

후보.

Architecture 변경은 ADR/Issue 연결을 포함한다.

---

# 119. Module Naming

## FIG-NAM-71. Module

Current:

```text
pdmg-ui
pdmg-jwt
pdmg-fw
pdmg-service
pdmg-om
```

### [AS-IS]

---

# 120. Module Naming Rule

```text
<product>-<responsibility>
```

대표:

```text
pdmg-service
```

---

# 121. Module Naming 금지

```text
module1
module2
new-module
temp-service
```

의미 없는 이름 금지.

---

# 122. Application Naming

## FIG-NAM-72. Application

```text
Application Name
  ↓
Stable Business / Platform Responsibility
```

Application Name과 Module Name을 동일시하지 않는다.

---

# 123. Logical Node Naming

```text
<Role>-<Purpose>
```

와 같이 Technology Responsibility를 드러낸다.

### MUST NOT

Hostname을 Logical Node Name으로 사용.

---

# 124. Hostname Naming

## FIG-NAM-73. Hostname

```text
Organization
  ↓
Application/System
  ↓
Platform/Role
  ↓
Environment
  ↓
Sequence
```

기존 Physical Baseline에 12자리 규칙 후보가 있었으나,
최종 공식 자리수/코드표는 Infrastructure Naming Standard 승인본을 따른다.

---

# 125. Hostname vs Application

```text
Hostname
= Physical Runtime Identity

Application
= Business Responsibility
```

---

# 126. VM Naming

## FIG-NAM-74. VM

```text
System / Role
  ↓
Environment
  ↓
Sequence
```

정확한 Cloud/IaaS Naming 규칙은 Infra Standard에서 확정.

---

# 127. JVM Naming

## FIG-NAM-75. JVM

```text
Host
  ↓
JVM Instance
  ↓
Port / WAR
```

JVM Identifier는 Host 내 Unique해야 한다.

---

# 128. Context Path Naming

```text
/cc
/ic
/pc
/sv
...
```

대표 Routing Context는 Application Routing Standard와 정합시킨다.

---

# 129. URL Naming

## FIG-NAM-76. URL

```text
Scheme
Host / Domain
Context
Resource / Service
Version [where applicable]
```

---

# 130. URL Domain Naming

현재/과거 목표 예:

```text
nh.marketing.com
```

### 주의

실제 DNS 승인상태와 환경별 Domain을 별도 확인한다.

---

# 131. API Path Naming

```text
/resource
/{resourceId}
```

REST Resource 스타일은 API 유형에 한정한다.

ServiceId 기반 Transaction Endpoint와 동일 규칙으로 강제하지 않는다.

---

# 132. PDMG Online URL

대표:

```text
/online
/{businessCode}/online
/{serviceId}
```

### [AS-IS]

---

# 133. Datasource Naming

## FIG-NAM-77. Datasource

```text
Application / Data Role
  ↓
Datasource Name
  ↓
DB Service
```

예:

```text
RDW
ADW
```

구체 JNDI/Bean Naming은 Config Inventory로 확정.

---

# 134. Bean Naming
## TEXT ARCHITECTURE 보완 — Bean Naming

```text
Spring Bean
   ↓
Technical Responsibility
   ↓
Qualifier [if required]
```

Bean 이름을 Business/Runtime Routing의 유일 식별자로 사용하지 않는다.


Spring Bean Name을 Runtime Routing Key처럼 남용하지 않는다.

필요 시 명시적 Qualifier를 사용한다.

---

# 135. TransactionManager Naming

## FIG-NAM-78. TX Manager

```text
DataSource
  ↓
TransactionManager
```

다중 Datasource 환경에서는 역할을 식별 가능하게 한다.

---

# 136. SqlSessionFactory Naming

```text
Datasource Role
  ↓
SqlSessionFactory
```

---

# 137. Scheduler Bean Naming

```text
Business Job
  ↓
Scheduler / Trigger
```

---

# 138. Security Bean Naming

```text
jwtDecoder
jwtEncoder
securityFilterChain
```

같은 역할 중심 Naming을 사용한다.

---

# 139. Key ID Naming

## FIG-NAM-79. kid

```text
kid
= Cryptographic Key Version Identity
```

### MUST

same kid = same public key semantics.

---

# 140. Key Alias Naming

```text
environment
purpose
version
```

을 Metadata로 관리할 수 있다.

---

# 141. Secret Naming

```text
<system>/<environment>/<purpose>
```

후보.

Secret Store 제품에 따라 실제 Path 규칙 확정.

---

# 142. Certificate Naming

```text
service / domain
  +
environment
  +
purpose
```

---

# 143. Port Naming / Inventory Key

## FIG-NAM-80. Port Identity

```text
Host + Protocol + Port
= Runtime Listener Identity
```

Port 번호만으로 Service Identity를 대체하지 않는다.

---

# 144. Filesystem Naming

## FIG-NAM-81. Filesystem Path

```text
OS Standard
  ↓
Application
  ↓
Purpose
  ↓
Environment
```

구체 Mount Path는 Infra Standard를 따른다.

---

# 145. Log File Naming

```text
Application
  +
Log Type
  +
Date / Rotation
```

후보.

Central Logging에서는 File Name보다 Structured Field가 더 중요하다.

---

# 146. Backup Naming

```text
Source
  +
Backup Type
  +
Timestamp
  +
Sequence
```

복구대상을 식별 가능해야 한다.

---

# 147. DR Resource Naming

## FIG-NAM-82. DR Naming

```text
Primary Resource
  ↓ mapped to
DR Resource
```

Primary/DR Pair를 Inventory에서 명시적으로 연결한다.

---

# 148. Monitoring Object Naming

```text
Application
Host
JVM
ServiceId
InterfaceId
DB
```

이름을 Dashboard/Alert에서 동일 축으로 사용한다.

---

# 149. Alert Rule Naming

## FIG-NAM-83. Alert

```text
<Domain>-<Target>-<Condition>
```

후보.

예:

```text
PDMG-ServiceId-Timeout
```

---

# 150. Dashboard Naming

```text
Executive
Service
Application
JVM
DB
Security
Deployment
```

등 관점 기반으로 구분한다.

---

# 151. Runbook Naming

```text
RB-<DOMAIN>-<SCENARIO>
```

후보.

---

# 152. Naming Registry

## FIG-NAM-84. Registry Set

```text
Business Code Registry
Program Registry
ServiceId Registry
Interface Registry
Event Registry
Batch Registry
Data Object Registry
Artifact Registry
Deployment Registry
Infrastructure Registry
```

---

# 153. Naming SSOT

## FIG-NAM-85. SSOT

```text
Architecture Model
       ↓
Naming Registries
       ↓
Source / Config
       ↓
Runtime
```

---

# 154. Registry Key Rule
## TEXT ARCHITECTURE 보완 — Registry Key

```text
Canonical ID
   ↓
Registry Primary Key
   ↓
Display Name / Alias / Metadata
```

Stable ID를 Primary Key로 사용한다.


### MUST

각 Registry는 Stable Primary Identifier를 가진다.

### MUST NOT

Display Name만으로 Join하지 않는다.

---

# 155. Display Name vs ID

```text
ID
= Stable / Machine

Display Name
= Human-readable / Changeable
```

---

# 156. Alias Naming

Alias가 필요한 경우:

```text
Canonical ID
  ↓
Alias
```

방향을 명확히 한다.

---

# 157. Deprecated Name

## FIG-NAM-86. Rename Lifecycle

```text
Old Name
  ↓ Deprecated
New Canonical Name
  ↓
Migration
  ↓
Old Name Retire
```

---

# 158. Rename Governance
## TEXT ARCHITECTURE 보완 — Rename Governance

```text
Rename Request
   ↓
Impact Analysis
   ↓
Source / Contract / DB / Monitoring 영향
   ↓
Version / Alias / Migration
   ↓
Runtime Verify
   ↓
Old Name Retire
```


Breaking Rename은 Source/API/Monitoring/Docs 영향을 분석한다.

---

# 159. Naming Collision

## FIG-NAM-87. Collision

```text
New ID
  ↓
Registry Lookup
  ├─ Exists → Reject
  └─ Free   → Register
```

---

# 160. Naming Drift

## FIG-NAM-88. Drift

```text
Approved Naming
      ↓ compare
Source / Runtime Naming
      ↓
DRIFT
```

예:

```text
Package Code mismatch
ServiceId missing
Mapper path mismatch
Artifact/Deployment mismatch
```

---

# 161. Program ↔ Package Drift

```text
mgcoa9001
  ↓ expected
nhnis.mg.co.a
```

불일치 탐지.

---

# 162. Program ↔ Mapper Drift

```text
mgcoa9001
  ↓ expected
rdw.mg.co.a/mgcoa9001-ORA.xml
```

---

# 163. ServiceId ↔ Handler Drift

```text
Registry
  ↓ compare
Handler.handle branch
```

---

# 164. UI Catalog ↔ Backend Drift

## FIG-NAM-89. Catalog Drift

```text
UI Transaction Catalog
        ↓ compare
Backend Handler Registry
        ↓
MATCH / DRIFT
```

---

# 165. Source ↔ Deployment Drift

```text
Source Commit
  ↓
Artifact
  ↓
Deployment
```

이름과 Hash 모두 비교한다.

---

# 166. Naming Architecture Rule

## FIG-NAM-90. Rule Pipeline

```text
Naming Principle
  ↓
Regex / Registry Rule
  ↓
Scanner
  ↓
CI
  ↓
PASS / FAIL
```

---

# 167. Program Rule

```text
R-PROGRAM-FORMAT
R-PROGRAM-BUSINESS-AXIS
```

---

# 168. ServiceId Rule

```text
R-SERVICEID-FORMAT
R-SERVICEID-UNIQUE
R-SERVICEID-CATALOG
R-HANDLER-BRANCH
```

---

# 169. Package Rule

```text
R-PACKAGE-BUSINESS-AXIS
R-PACKAGE-LOWERCASE
```

---

# 170. Class Rule

```text
R-HANDLER-NAMING
R-FACADE-NAMING
R-SERVICE-NAMING
R-DAO-NAMING
R-DTO-NAMING
```

---

# 171. Mapper Rule

```text
R-MAPPER-PATH
R-MAPPER-FILE
R-MAPPER-NAMESPACE
R-SQLID-NAMING
```

---

# 172. Interface Naming Rule

```text
R-INTERFACE-ID-UNIQUE
R-EVENT-NAMING
R-FILE-NAMING
```

---

# 173. Deployment Naming Rule

```text
R-ARTIFACT-IDENTITY
R-DEPLOYMENT-ID
R-HOST-JVM-WAR-MAPPING
```

---

# 174. Naming Static Scan

## FIG-NAM-91. Static Scan

```text
Source Tree
  ↓
Program Parser
  ↓
Package Parser
  ↓
Class Parser
  ↓
ServiceId Parser
  ↓
Mapper Parser
  ↓
Rule Result
```

---

# 175. Regex Validation

```text
ProgramId
ServiceId
Package
Class Suffix
```

를 정적검사한다.

---

# 176. Registry Validation

```text
ServiceId Unique
InterfaceId Unique
Program Unique
```

---

# 177. Dependency Validation

Naming만 맞고 계층의존이 틀린 경우도 Fail 대상이다.

```text
Handler Name O
but
Handler → DAO

X
```

---

# 178. Naming CI Gate

## FIG-NAM-92. CI Gate

```text
Commit
  ↓
Naming Scan
  ↓
Registry Check
  ↓
Mapper Check
  ↓
Trace Check
  ↓
Critical Fail?
  ├─ YES → Build Stop
  └─ NO  → Continue
```

---

# 179. Naming Exception

## FIG-NAM-93. Exception

```text
Standard Violation Needed?
  ↓
Reason
  ↓
Impact
  ↓
Temporary Alias / Adapter
  ↓
ADR
  ↓
Expiry
```

---

# 180. Legacy Naming

Legacy는:

```text
Legacy Canonical Name
  ↓
Mapping Table
  ↓
Target Canonical Name
```

으로 흡수한다.

---

# 181. Naming Migration

## FIG-NAM-94. Migration

```text
Old Identifier
  ↓
Impact Analysis
  ↓
Dual Mapping
  ↓
Source Migration
  ↓
Runtime Verify
  ↓
Old Retire
```

---

# 182. Rename 금지대상

다음은 임의 Rename 금지:

```text
ServiceId
InterfaceId
External Contract Field
DB Object
Public URL
```

변경 시 Version/ADR 필요.

---

# 183. Application Architecture와 Naming

## FIG-NAM-95. AA ↔ Naming

```text
Application
  ↓
Program
  ↓
ServiceId
  ↓
Layer Class
```

---

# 184. Technical Architecture와 Naming

## FIG-NAM-96. TA ↔ Naming

```text
Logical Node
  ↓
Technology Component
  ↓
Runtime Object Name
```

---

# 185. Infrastructure Architecture와 Naming

## FIG-NAM-97. Infra ↔ Naming

```text
Center
  ↓
Host / VM / JVM
  ↓
Port / Filesystem / Account
```

---

# 186. Interface Architecture와 Naming

## FIG-NAM-98. IF ↔ Naming

```text
InterfaceId
  ↓
Endpoint / Topic / File
  ↓
Version
```

---

# 187. Data Architecture와 Naming

## FIG-NAM-99. DA ↔ Naming

```text
Standard Term
  ↓
Entity / Attribute
  ↓
Table / Column
```

---

# 188. Development Standard와 Naming

## FIG-NAM-100. Dev ↔ Naming

```text
Naming Standard
  ↓
Source Structure
  ↓
CI Rule
```

---

# 189. Naming Traceability

## FIG-NAM-101. Full Trace

```text
Business
  ↓
ProgramId
  ↓
ServiceId
  ↓
Package
  ↓
Handler
  ↓
Facade
  ↓
Service
  ↓
DAO
  ↓
Mapper / SqlId
  ↓
Table
  ↓
Artifact
  ↓
Deployment
  ↓
Host / JVM
  ↓
GUID / Evidence
```

---

# 190. Reverse Traceability

## FIG-NAM-102. Reverse Trace

```text
Runtime Error
  ↑
Host / JVM
  ↑
Artifact
  ↑
ServiceId
  ↑
Program
  ↑
Business
```

---

# 191. Naming Master Inventory

```yaml
naming:
  businessCode:
  functionCode:
  programId:
  serviceIds:
  javaPackage:
  handler:
  facade:
  service:
  dao:
  mapper:
  sqlIds:
  tables:
  interfaces:
  artifact:
  deployment:
  host:
  jvm:
  evidence:
```

---

# 192. Program Registry Template

```yaml
program:
  programId:
  majorCode:
  businessCode:
  functionCode:
  programNumber:
  name:
  owner:
  package:
  status:
```

---

# 193. ServiceId Registry Template

```yaml
service:
  serviceId:
  programId:
  transactionType:
  sequence:
  handler:
  uiCatalog:
  owner:
  status:
```

---

# 194. Class Registry Template

```yaml
class:
  programId:
  layer:
  package:
  className:
  responsibility:
```

---

# 195. Mapper Registry Template

```yaml
mapper:
  programId:
  resourcePath:
  fileName:
  namespace:
  sqlIds:
```

---

# 196. Interface Naming Registry Template

```yaml
interface:
  interfaceId:
  displayName:
  producer:
  consumer:
  type:
  version:
  endpoint:
  owner:
```

---

# 197. Runtime Naming Registry Template

```yaml
runtime:
  application:
  artifact:
  deploymentId:
  host:
  vm:
  jvm:
  ports:
  datasource:
```

---

# 198. Naming Review Checklist

## FIG-NAM-103. Review Gate

```text
Business Axis?
  ↓
ProgramId?
  ↓
ServiceId?
  ↓
Package/Class?
  ↓
Mapper/SqlId?
  ↓
Interface/Data?
  ↓
Artifact/Deployment?
  ↓
Runtime Mapping?
  ↓
CI Rule?
  ↓
PASS / GAP
```

---

# 199. 신규 Program Naming Checklist

```text
[ ] Major Code
[ ] Business Code
[ ] Function Code
[ ] 4-digit Program Number
[ ] ProgramId Unique
[ ] Package
[ ] Owner
```

---

# 200. 신규 ServiceId Checklist

```text
[ ] ProgramId
[ ] Transaction Type
[ ] Sequence
[ ] Regex
[ ] Unique
[ ] Handler
[ ] Branch
[ ] UI Catalog
[ ] Test
```

---

# 201. 신규 Class Checklist

```text
[ ] Correct Package
[ ] Correct Program Stem
[ ] Correct Layer Suffix
[ ] Responsibility Match
```

---

# 202. 신규 Mapper Checklist

```text
[ ] Resource Path
[ ] Program File Name
[ ] DBMS Suffix
[ ] Namespace
[ ] SqlId
[ ] DAO Mapping
```

---

# 203. 신규 Interface Naming Checklist

```text
[ ] InterfaceId
[ ] Display Name
[ ] Source/Target
[ ] Type
[ ] Version
[ ] Endpoint/Topic/File
[ ] Owner
```

---

# 204. 신규 Runtime Naming Checklist

```text
[ ] Artifact
[ ] DeploymentId
[ ] Host
[ ] VM
[ ] JVM
[ ] Port
[ ] Datasource
[ ] Monitoring ID
```

---

# 205. Naming Go-Live Blocker

## FIG-NAM-104. Go-Live Block

```text
Duplicate ServiceId
      OR
Program/Package mismatch
      OR
Handler Registry mismatch
      OR
Mapper Namespace mismatch
      OR
Critical InterfaceId missing
      OR
Artifact/Deployment identity missing
      OR
Host/JVM/WAR mapping missing
      ↓
GO-LIVE BLOCK
```

---

# 206. Naming GAP Register

## TEXT ARCHITECTURE — GAP Lifecycle

```text
Target Naming Standard
      ↓ compare
Actual Source / Runtime
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
| GAP-NAM-01 | Business Code Registry SSOT 정비 필요 | Business |
| GAP-NAM-02 | Function Code 의미표 전수화 필요 | Program |
| GAP-NAM-03 | ServiceId SSOT 자동화 미완료 | Service |
| GAP-NAM-04 | UI Catalog↔Backend Registry 자동비교 미완료 | Runtime |
| GAP-NAM-05 | Rule Layer Naming 미확정 | Application |
| GAP-NAM-06 | Table/Column Enterprise Naming 별도 확정 필요 | Data |
| GAP-NAM-07 | InterfaceId 최종 형식 미확정 | Interface |
| GAP-NAM-08 | Event/Topic Naming 표준 미확정 | Event |
| GAP-NAM-09 | File Naming 상세표준 미확정 | File |
| GAP-NAM-10 | Batch JobId Naming 전수화 필요 | Batch |
| GAP-NAM-11 | Artifact/DeploymentId 규칙 승인 필요 | DevOps |
| GAP-NAM-12 | Hostname/VM/JVM 표준 최신화 필요 | Infrastructure |
| GAP-NAM-13 | Metric/Trace Semantic Naming 미확정 | Observability |
| GAP-NAM-14 | Naming CI Gate 전수적용 미완료 | Governance |

---

# 207. Naming Risk Register

## TEXT ARCHITECTURE — Risk Lifecycle

```text
Naming Weakness
  ↓
Identity Collision / Trace Loss
  ↓
Runtime / Operations Impact
  ↓
Severity
  ↓
Mitigation
  ↓
Evidence
```

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-NAM-01 | Duplicate ServiceId | Critical |
| RISK-NAM-02 | Program/Package 불일치 | High |
| RISK-NAM-03 | Handler/Branch Drift | High |
| RISK-NAM-04 | Mapper/DAO Namespace 불일치 | Critical |
| RISK-NAM-05 | SqlId Trace 단절 | High |
| RISK-NAM-06 | InterfaceId 부재 | High |
| RISK-NAM-07 | 같은 이름의 다른 Artifact | Critical |
| RISK-NAM-08 | Host/JVM/WAR 식별 불일치 | Critical |
| RISK-NAM-09 | Legacy Alias 난립 | High |
| RISK-NAM-10 | Rename 영향누락 | High |
| RISK-NAM-11 | Naming Drift 미탐지 | High |
| RISK-NAM-12 | 규칙 미정 영역의 임의 Naming | High |

---

# 208. Naming ADR 후보

## FIG-NAM-105. ADR Areas

```text
Naming Decision
│
├─ Business Code
├─ ProgramId
├─ ServiceId
├─ Package/Class
├─ Mapper/SqlId
├─ DB Object
├─ InterfaceId
├─ Event/File/Batch
├─ Artifact/Deployment
├─ Infrastructure
└─ Observability
```

대표 ADR:

```text
ADR-NAM-01 Business Code Registry
ADR-NAM-02 Program ID
ADR-NAM-03 ServiceId SSOT
ADR-NAM-04 Package/Class Standard
ADR-NAM-05 Mapper/SqlId
ADR-NAM-06 DB Naming
ADR-NAM-07 InterfaceId
ADR-NAM-08 Event/File/Batch Naming
ADR-NAM-09 Artifact/DeploymentId
ADR-NAM-10 Host/VM/JVM
ADR-NAM-11 Metric/Trace Naming
ADR-NAM-12 Legacy Rename/Alias
```

---

# 209. Naming Completion Gate

## FIG-NAM-106. Completion Gate

```text
Business Code Defined?
   ↓ YES
ProgramId Defined?
   ↓ YES
ServiceId Defined?
   ↓ YES
Package/Class Defined?
   ↓ YES
Mapper/SqlId Defined?
   ↓ YES
Interface/Data Naming Defined?
   ↓ YES
Artifact/Deployment Naming Defined?
   ↓ YES
Infra Naming Defined?
   ↓ YES
Registry/CI Validation?
   ↓ YES
Runtime Trace?
   ↓ YES
NAMING DEVELOPMENT STANDARD PASS
```

---

# 210. 최종 Naming Architecture 지도

## FIG-NAM-107. Final Naming Map

```text
BUSINESS
Major / Business / Function
      ↓
PROGRAM
ProgramId
      ↓
TRANSACTION
ServiceId
      ↓
SOURCE
Package / Handler / Facade / Service / DAO / DTO
      ↓
DATA
Mapper / SqlId / Table / Column
      ↓
INTERFACE
InterfaceId / Event / File / Job
      ↓
DELIVERY
Artifact / DeploymentId
      ↓
RUNTIME
Host / VM / JVM / Port / Datasource
      ↓
EVIDENCE
GUID / Metric / Trace / Test
```

---

# 211. Definition of Done

## TEXT ARCHITECTURE — Naming Standard DoD

```text
Business Axis
  ↓
Program
  ↓
ServiceId
  ↓
Source
  ↓
Data / Interface
  ↓
Deployment
  ↓
Runtime
  ↓
CI / Evidence
  ↓
NAMING DoD
```

## Business / Program
- [x] Major/Business/Function
- [x] Program ID
- [x] 9-char Structure
- [x] Program Regex

## Service
- [x] ServiceId
- [x] 11-char Structure
- [x] Transaction Type
- [x] Sequence
- [x] Unique/Registry

## Source
- [x] Package
- [x] Handler
- [x] Facade
- [x] Service
- [x] DAO
- [x] DTO
- [x] Controller / Rule

## Persistence / Data
- [x] Mapper Path
- [x] Mapper File
- [x] Namespace
- [x] SqlId
- [x] DB Naming scope

## Interface / Runtime
- [x] InterfaceId
- [x] Event / Topic
- [x] File
- [x] Batch / Cache
- [x] Config / Error / Log

## Delivery / Infrastructure
- [x] Module
- [x] Artifact
- [x] DeploymentId
- [x] Host / VM / JVM
- [x] Port / Datasource
- [x] Filesystem / Account / Monitoring

## Governance
- [x] Registry
- [x] Drift
- [x] Static Scan
- [x] CI Gate
- [x] GAP / Risk / ADR
- [x] Go-Live Blocker

**NAMING DEVELOPMENT STANDARD 별첨 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. Business Code Registry SSOT 승인
2. Function Code 의미표 전수화
3. ServiceId Registry 자동화
4. UI Catalog↔Backend 자동정합
5. Rule Layer Naming 결정
6. DB Table/Column Naming 공식표준 확정
7. InterfaceId Naming 승인
8. Event/Topic/File/Batch Naming 승인
9. Artifact/DeploymentId Naming 승인
10. Host/VM/JVM Naming 최신화
11. Metric/Trace Field Naming 통일
12. Legacy Alias/Rename 정책
13. Naming Scanner CI Gate 적용
14. Critical Naming Drift 0

---

# 212. 장 최종 결론

## FIG-NAM-108. Naming Final

```text
Business Meaning
      ↓
ProgramId
      ↓
ServiceId
      ↓
Package / Class
      ↓
Mapper / SqlId
      ↓
Data / Interface
      ↓
Artifact / Deployment
      ↓
Host / JVM
      ↓
Runtime Evidence
```

> **Naming Development Standard의 본질은 이름 자체가 아니라 식별체계의 연속성이다. `mgcoa9001 → mgcoa9001S0 → nhnis.mg.co.a → Handler/Facade/Service/DAO → Mapper/SqlId → Artifact/Deployment → Runtime Evidence`가 하나의 Trace로 이어져야 한다.**

> **PDMG에서 확인된 9자리 Program ID, 11자리 ServiceId, `nhnis.mg.co.a`, `rdw.mg.co.a`, `<programId>Handler/Facade/Service/DAO`, `<serviceId>DTOin/out` 패턴은 강한 AS-IS Reference이지만, NSIGHT Target 표준으로 승격하려면 Registry·자동검증·예외정책·Data/Interface/Infrastructure Naming과의 통합이 필요하다.**

> **확정 근거가 없는 Table/Column, InterfaceId, Event/Topic, Hostname 세부 자리수 등은 임의로 창작하지 않고 `[OPEN]/[PROPOSED]`로 유지한다.**
