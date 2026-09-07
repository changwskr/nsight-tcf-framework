# NSIGHT / PDMG 아키텍처 정의서
# 09. OM / DEVOPS / OBSERVABILITY / OPERATIONS
## Visual-First / Top-down → Runtime Operations Drill-down 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-VISUAL-OPERATIONS-09`  
> Architecture Level: **L8 — DEVOPS / OM / OBSERVABILITY / OPERATIONS**  
> 문서 상태: **Draft / Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_08_PDMG_SOURCE_RUNTIME_REFERENCE_DEEP_DIVE_VISUAL_FIRST_상세본.md`  
> 본 장 목적: **Source 변경이 Build/Artifact/Deployment/Runtime으로 전달되고, Metric/Log/Trace/Alert/Runbook/Evidence를 통해 다시 Architecture Baseline으로 환류되는 운영 Control Plane 정의**

---

# 0. 이 장을 읽는 방법

8장에서 PDMG Source와 Runtime을 실제 구현 관점으로 내려갔다.

9장에서는 그것을 다시 운영 Control Plane으로 끌어올린다.

```text
SOURCE
  ↓
BUILD
  ↓
TEST
  ↓
ARTIFACT
  ↓
DEPLOY
  ↓
RUNTIME
  ↓
METRIC / LOG / TRACE
  ↓
ALERT
  ↓
RUNBOOK
  ↓
RECOVERY
  ↓
EVIDENCE
  ↓
DRIFT
  ↓
ARCHITECTURE UPDATE
```

이 장의 핵심은 도구목록이 아니다.

```text
GitLab이 있다
≠ DevOps 완료

APM이 있다
≠ Observability 완료

pdmg-om 이름이 있다
≠ OM 구현 완료

Dashboard가 있다
≠ 운영 가능

Alert가 있다
≠ Recovery 가능
```

---

# 1. VISUAL ROUTE — Operations Architecture 전체

## FIG-OPS-01. DevOps / OM / Observability Big Picture

```text
╔══════════════════════════════════════════════════════════════════════════════╗
║                  DEVOPS / OM / OBSERVABILITY / OPERATIONS                   ║
╚══════════════════════════════════════════════════════════════════════════════╝

 [CHANGE]
 Requirement / ADR / Source / Config
      │
      ▼
 [SCM]
 GitLab
      │
      ▼
 [BUILD / TEST]
 Gradle / Runner / Architecture Rule / Test
      │
      ▼
 [ARTIFACT]
 WAR / Build Metadata / Hash
      │
      ▼
 [DEPLOYMENT]
 DEV / TEST / PROD / DR
 Runner / eCAMS / approved deployment
      │
      ▼
 [RUNTIME]
 WEB / WAS / JVM / WAR / Worker / Hikari / DB
      │
      ▼
 [OBSERVABILITY]
 Metric / Log / Trace / Health / ImageLog
      │
      ▼
 [OM CONTROL PLANE]
 ServiceId / JVM / Thread / DB Pool / Timeout / Error
      │
      ▼
 [OPERATIONS]
 Alert → Diagnosis → Runbook → Recovery
      │
      ▼
 [EVIDENCE]
 Deployment / Runtime / DR / Security / Performance
      │
      ▼
 [GOVERNANCE]
 Drift → GAP → ADR → New Baseline
```

---

# 2. 8장 → 9장 Handoff

## FIG-OPS-02. Source Evidence to Operations

```text
08 PDMG SOURCE
│
├─ ServiceId
├─ Handler / Service
├─ Filter / TCF
├─ Worker
├─ Transaction
├─ Hikari
├─ JWT
├─ Error
├─ GUID
└─ ImageLog
       │
       ▼
09 OPERATIONS
│
├─ Build
├─ Deploy
├─ Runtime Inventory
├─ Monitoring
├─ Alert
├─ Runbook
├─ Recovery
└─ Evidence
```

---

# 3. Source Classification — DevOps와 OM을 섞지 않는다

## FIG-OPS-03. Three Evidence Levels

```text
A. NSIGHT STRATEGY
   GitLab / GitLab Runner / eCAMS
   IaaS
   APM / Integrated Log / GUID Trace
        │
        ▼

B. PDMG CURRENT SOURCE
   Java 21
   Spring Boot 3.5.14
   Gradle Multi-project
   PDMG Modules
   Log4j2 / GUID / ImageLog
        │
        ▼

C. OPERATIONS TARGET
   CI/CD Gate
   Deployment Evidence
   Runtime Inventory
   Monitoring
   Alert
   Runbook
   Drift
```

### 금지

```text
Strategy Tool 존재
=
Current PDMG Pipeline 구현완료

X
```

---

# 4. 운영 Architecture 핵심 결론

## FIG-OPS-04. Operations Principle

```text
Change
  ↓
Controlled Delivery
  ↓
Observable Runtime
  ↓
Actionable Alert
  ↓
Recoverable Operation
  ↓
Evidence
  ↓
Governed Change
```

> **운영 Architecture의 목적은 “시스템을 보는 것”이 아니라 “변경과 장애를 통제하고 복구하며 증명하는 것”이다.**

---

# 5. Standardization ↔ DevOps Loop

## FIG-OPS-05. Standardization Loop

```text
Architecture Standard
      ↓
Developer Guide
      ↓
Source
      ↓
CI Rule
      ↓
Build / Test
      ↓
Deploy
      ↓
Runtime
      ↓
Drift / Incident
      ↓
Architecture Standard Update
      └──────────────────────────────↺
```

### 핵심

표준은 문서배포로 끝나지 않는다.

```text
표준
→ Rule
→ CI
→ Runtime
→ Feedback
```

으로 유지한다.

---

# 6. DevOps Tool Boundary — Strategy Level

## FIG-OPS-06. Tool Responsibility

```text
GitLab
= Source Control / Change History

GitLab Runner
= Build / Test / Development Delivery Candidate

eCAMS
= Production Deployment / Change Control Strategy

IaaS
= Environment / Infrastructure Consistency
```

### 상태

```text
[NSIGHT STRATEGY]
```

현재 PDMG Repository에 모든 Pipeline이 구현되어 있다고 자동 판단하지 않는다.

---

# 7. Source → Build → Test → Artifact → Deploy

## FIG-OPS-07. Delivery Pipeline

```text
Developer
   ↓
Git Commit
   ↓
SCM
   ↓
Build
   ↓
Architecture Rule
   ↓
Unit / Contract / Security Test
   ↓
Package
   ↓
Artifact
   ↓
Hash
   ↓
Deploy
   ↓
Runtime Verification
```

---

# 8. Delivery의 완료 조건

```text
Build SUCCESS
≠ Release Ready
```

필요:

```text
Build
+
Test
+
Security
+
Architecture Rule
+
Artifact Identity
+
Deployment Evidence
+
Runtime Verification
```

---

# 9. PDMG Build Baseline

## FIG-OPS-08. PDMG Build Structure

```text
PDMG Root
│
├─ pdmg-ui
├─ pdmg-jwt
│    └─ pdmg-fw dependency
│
├─ pdmg-service
│    └─ pdmg-fw dependency
│
├─ pdmg-fw
└─ pdmg-om [Source/Build Evidence 추가 확인]
```

Current technology baseline:

```text
Java 21
Spring Boot 3.5.14
Gradle Multi-project
```

### 태그

```text
[PDMG AS-IS EVIDENCE]
```

---

# 10. Build Module vs Artifact

## FIG-OPS-09. Build Boundary

```text
Build Module
   │
   ▼
Compile / Package
   │
   ▼
Artifact
   │
   ▼
Runtime Deployment
```

### 핵심

```text
Module
≠ Artifact 1:1 자동
≠ Runtime Process 1:1 자동
```

---

# 11. Framework Dependency

```text
pdmg-service
      │
      └── depends on pdmg-fw

pdmg-jwt
      │
      └── depends on pdmg-fw
```

### 의미

Framework 변경은 여러 Runtime Artifact에 영향을 줄 수 있다.

---

# 12. Framework Change Impact

## FIG-OPS-10. Shared Framework Blast Radius

```text
pdmg-fw Change
      │
      ├─ pdmg-service Build Impact
      ├─ pdmg-jwt Build Impact
      ├─ Runtime Behavior Impact
      ├─ Security Impact
      └─ Regression Test Impact
```

---

# 13. Build Reproducibility

## FIG-OPS-11. Reproducible Build

```text
Source Commit
   +
Build Definition
   +
Dependency Lock / Version
   +
Toolchain
   ↓
Same Artifact
```

필요:

```text
Java Version
Gradle Version
Dependency Version
Build Options
Artifact Hash
```

---

# 14. Artifact Identity

## FIG-OPS-12. Artifact Identity Chain

```text
Source Commit
   ↓
Build ID
   ↓
Artifact Name
   ↓
Version
   ↓
SHA-256 / approved hash
```

---

# 15. Artifact Naming

권장 구성:

```text
System / Module
Version
Build
Commit
```

실제 Naming Rule은 Release Standard로 승인한다.

---

# 16. Same Artifact Principle

## FIG-OPS-13. Environment Promotion

```text
DEV
Artifact A
   ↓ promote
TEST
Artifact A
   ↓ promote
PROD
Artifact A
   ↓ sync
DR
Artifact A
```

### 금지

```text
DEV에서 Build A
PROD에서 다시 Build B
```

---

# 17. Artifact + Config

## FIG-OPS-14. Runtime Composition

```text
Artifact
   +
Environment Config
   +
Secret / Key
   +
Infrastructure
   =
Runtime
```

따라서 Artifact만 같다고 Runtime이 같다고 볼 수 없다.

---

# 18. Config Separation

## FIG-OPS-15. Configuration Boundary

```text
Artifact
├─ Business Code
├─ Framework Code
└─ Static Resource

Environment Config
├─ Endpoint
├─ DB
├─ Timeout
├─ Pool
├─ Feature Toggle
└─ Log Level

Secret
├─ Password
├─ Token Secret
├─ Private Key
└─ Certificate
```

---

# 19. Secret는 Source Config와 다르다

```text
application.yml
   │
   ├─ non-secret config
   │
   └─ secret reference
          ↓
     Secret Store / protected channel
```

### 금지

```text
Private Key in Git       X
DB Password in YAML      X
HMAC Secret in source    X
```

---

# 20. JWT Key Deployment

## FIG-OPS-16. Key Delivery

```text
Approved Key Store
      │
      ├─ JWT #1
      └─ JWT #2
      │
      ▼
Runtime Key Version
      │
      ▼
JWKS
```

운영은 다음을 알아야 한다.

```text
현재 kid?
Active Key?
Rotation Time?
JWT Instance 간 일치?
DR Key 일치?
```

---

# 21. Environment Promotion

## FIG-OPS-17. Promotion Flow

```text
DEV
 ↓
Integration
 ↓
TEST / QA
 ↓
Pre-Prod / Pilot [if used]
 ↓
PROD
 ↓
DR Sync
```

Gate:

```text
Functional
Architecture
Security
Performance
Deployment
Runtime
```

---

# 22. Configuration Promotion

```text
DEV Config
  ↓
Template / Model
  ↓
Environment Overlay
  ↓
Approval
  ↓
Deploy
```

환경간 무분별 복사 금지.

---

# 23. Deployment Manifest

## FIG-OPS-18. Deployment Manifest

```text
Artifact
  ↓
Deployment ID
  ↓
Environment
  ↓
Center
  ↓
Host
  ↓
JVM
  ↓
WAR / Context
  ↓
Port / Endpoint
```

---

# 24. Deployment Manifest 최소 필드

```yaml
deployment:
  deploymentId:
  releaseId:
  sourceCommit:
  buildId:
  artifact:
  artifactHash:
  environment:
  center:
  host:
  jvm:
  context:
  version:
  deployedAt:
  deployedBy:
  configVersion:
  rollbackArtifact:
  evidence:
```

---

# 25. PDMG Deployment 현재 GAP

## FIG-OPS-19. Current Mapping Gap

```text
PDMG Module / Source
      ↓
Artifact
      ↓
[ GAP ]
      ↓
Production Host
      ↓
JVM
      ↓
Context / Port
```

현재 이를 전수 증명하는 Deployment Manifest가 필요하다.

---

# 26. Development Deployment vs Production Deployment

## FIG-OPS-20. Tool Boundary

```text
Development / CI
GitLab Runner
      │
      ▼
DEV / Test Runtime

Production
Approved Change
      │
      ▼
eCAMS Strategy
      │
      ▼
PROD Runtime
```

### 주의

이것은 Architecture Strategy의 책임경계다.
현재 실제 Pipeline 구현은 별도 Evidence로 확인해야 한다.

---

# 27. Release Evidence

## FIG-OPS-21. Release Evidence Package

```text
Release
│
├─ Source Commit
├─ Build Log
├─ Test Result
├─ Architecture Rule Result
├─ Artifact Hash
├─ Security Scan
├─ Deployment Manifest
├─ Config Version
├─ Runtime Smoke Test
├─ Monitoring Check
└─ Rollback Point
```

---

# 28. Rollback

## FIG-OPS-22. Rollback Chain

```text
Deployment
   ↓
Runtime Verification
   │
   ├─ PASS → Continue
   │
   └─ FAIL
        ↓
      Rollback Decision
        ↓
      Previous Artifact
        +
      Compatible Config
        ↓
      Verify
```

---

# 29. Rollback가 어려운 경우

```text
DB Schema incompatible
Data migration irreversible
External Contract changed
Token/Key changed
Event Schema incompatible
```

이 경우 단순 WAR rollback으로 끝나지 않는다.

---

# 30. Database Change Delivery

## FIG-OPS-23. DB Change

```text
Schema / SQL Change
      ↓
Migration Script
      ↓
Review
      ↓
Test
      ↓
Deployment
      ↓
Validation
      ↓
Rollback / Forward Fix
```

### 금지

```text
Application Runtime DDL
```

을 기본 운영전략으로 사용하지 않는다.

---

# 31. ImageLog Runtime DDL Risk

8장 Current 분석에서 ImageLog 관련 Runtime DDL 가능성이 있다면:

```text
Application
   ↓
Schema Alter
```

은 운영 Governance 위험이다.

TO-BE:

```text
Controlled Migration
```

---

# 32. OM의 위치

## FIG-OPS-24. OM Control Plane

```text
                    OM / Operations
                          │
       ┌──────────────────┼──────────────────┐
       ▼                  ▼                  ▼
    Observe             Control            Govern
 Metric/Log/Trace      approved op       config/drift
       │                  │                  │
       └──────────────────┼──────────────────┘
                          ▼
                    Runtime Plane
```

---

# 33. Control Plane vs Runtime Plane

```text
Runtime Plane
= 실제 Business 처리

Control Plane
= 상태 관찰 / 승인된 제어 / 운영정보 관리
```

### 원칙

OM이 장애나 네트워크 문제 때문에 멈춰도
Business Runtime이 불필요하게 같이 멈추지 않아야 한다.

---

# 34. pdmg-om Evidence Maturity

## FIG-OPS-25. pdmg-om Maturity

```text
Module Name
   ↓
Source Package?
   ↓
Runtime Process?
   ↓
Metric Collector?
   ↓
Dashboard?
   ↓
Control API?
   ↓
Deployment?
```

현재:

```text
pdmg-om 상세 구현
= [UNKNOWN]
```

---

# 35. pdmg-om에 대해 하면 안 되는 말

```text
pdmg-om이 이미
Thread Dashboard를 제공한다

X

pdmg-om이 이미
Hikari를 실시간 제어한다

X
```

Source/Runtime Evidence 없이는 Target 기능으로만 표현한다.

---

# 36. OM Target Responsibility

## FIG-OPS-26. Target OM Functions

```text
OM Target
│
├─ Runtime Inventory
├─ Application / WAR Status
├─ JVM / Thread Status
├─ Worker Pool
├─ Hikari
├─ Slow Service
├─ Timeout / Overload
├─ Error
├─ Config Baseline
├─ Deployment History
├─ Alert / Incident
└─ Evidence Link
```

---

# 37. OM이 직접 바꾸면 안 되는 것

```text
운영자 클릭
→ 즉시 maxThreads 임의 변경
→ 기록 없음

X
```

런타임 파라미터 변경은:

```text
Request
→ Approval
→ Controlled Change
→ Evidence
```

로 처리한다.

---

# 38. Observability 정의

## FIG-OPS-27. Three Signals + Context

```text
METRIC
무슨 상태인가?

LOG
무슨 일이 있었는가?

TRACE
어디를 지나갔는가?

+
CONTEXT
GUID / ServiceId / Deployment / Host
```

---

# 39. Monitoring vs Observability

```text
Monitoring
= 미리 정의한 상태 감시

Observability
= 내부상태를 외부 Evidence로 추론
```

둘 다 필요하다.

---

# 40. Transaction Observability

## FIG-OPS-28. End-to-End Transaction

```text
Client
 ↓
GSLB / L4
 ↓
WEB
 ↓
Tomcat JVM
 ↓
Filter
 ↓
Security
 ↓
TCF
 ↓
ServiceId
 ↓
Worker
 ↓
Transaction
 ↓
DAO / SqlId
 ↓
DB
 ↓
Response
```

각 구간은 GUID/ServiceId로 이어져야 한다.

---

# 41. 거래 Dashboard 질문

```text
현재 가장 느린 ServiceId는?
Timeout 상위 ServiceId는?
Error 상위 ServiceId는?
어느 JVM에서 발생?
Worker Queue는?
Hikari Pending은?
어느 SqlId가 느린가?
```

---

# 42. ServiceId 중심 운영

## FIG-OPS-29. Service View

```text
ServiceId
│
├─ TPS
├─ p50
├─ p95
├─ p99
├─ Error Rate
├─ Timeout
├─ Overload
├─ SQL Time
└─ External Time
```

---

# 43. GUID 중심 장애추적

## FIG-OPS-30. Trace View

```text
GUID
 ↓
Entry
 ↓
ServiceId
 ↓
JVM
 ↓
Worker
 ↓
SQL
 ↓
Error
 ↓
Response
```

---

# 44. JVM Monitoring

## FIG-OPS-31. JVM Dashboard

```text
JVM
│
├─ CPU
├─ Heap Used
├─ Heap Max
├─ GC Count / Pause
├─ Metaspace
├─ Thread Count
├─ Deadlock
├─ Uptime
└─ Restart Count
```

---

# 45. Tomcat Monitoring

```text
Tomcat
│
├─ Current Threads
├─ Busy Threads
├─ Max Threads
├─ Connection Count
├─ Request Rate
├─ Response Time
└─ Error
```

---

# 46. Worker Monitoring

## FIG-OPS-32. PDMG Worker

```text
Worker Pool
│
├─ pool-size
├─ active
├─ queue depth
├─ queue capacity
├─ rejected
├─ task duration
└─ timeout count
```

Current snapshot:

```text
Pool 20
Queue 100
```

은 기준점이지 Target Alert Threshold가 아니다.

---

# 47. Tomcat / Worker / Hikari를 같이 봐야 한다

## FIG-OPS-33. Resource Chain

```text
Tomcat Busy
   ↓
Worker Active
   ↓
Worker Queue
   ↓
Hikari Active/Pending
   ↓
DB Session / SQL Wait
```

---

# 48. Hikari Monitoring

## FIG-OPS-34. Pool Dashboard

```text
Hikari
│
├─ Active
├─ Idle
├─ Pending
├─ Max
├─ Acquire Time
├─ Connection Timeout
└─ Connection Lifetime
```

---

# 49. Hikari 병목 진단

```text
Pending ↑
  ↓
Active == Max?
  │
  ├─ YES
  │    ↓
  │ SQL slow / pool small / DB slow
  │
  └─ NO
       ↓
     app/thread/config issue
```

---

# 50. DB Monitoring

## FIG-OPS-35. DB Runtime View

```text
DB
│
├─ Sessions
├─ Active Sessions
├─ CPU
├─ I/O
├─ Wait Events
├─ Locks
├─ Long SQL
├─ Execution Count
└─ Error
```

---

# 51. SQL Observability

```text
ServiceId
  ↓
DAO
  ↓
Mapper / SqlId
  ↓
Elapsed
  ↓
Rows
  ↓
Wait / Error
```

---

# 52. Slow SQL View

## FIG-OPS-36. Slow SQL Drill-down

```text
Slow ServiceId
   ↓
Slow SqlId
   ↓
SQL
   ↓
Execution Plan / Wait
   ↓
Table / Index
```

---

# 53. Timeout Monitoring

## FIG-OPS-37. Timeout Dashboard

```text
Timeout
│
├─ Client
├─ Gateway / WEB
├─ Worker Deadline
├─ Hikari Acquire
├─ JDBC Query
└─ External
```

### 핵심

```text
504 Count
만으로 Timeout 원인을 알 수 없다.
```

---

# 54. PDMG 504 진단

```text
HTTP 504
   ↓
Worker still active?
   ↓
SQL still running?
   ↓
Rollback?
   ↓
Late Commit?
```

---

# 55. Overload Monitoring

## FIG-OPS-38. 503 Overload

```text
503
  ↓
Worker Active
  ↓
Queue Full?
  ↓
Rejected Count
  ↓
DB / External Cause?
```

---

# 56. Timeout vs Overload Dashboard

```text
Timeout = 실행은 받았지만 Deadline 초과

Overload = 실행 Capacity 부족 / Reject
```

두 지표를 합치지 않는다.

---

# 57. Error Monitoring

## FIG-OPS-39. Error Taxonomy Dashboard

```text
Errors
│
├─ Validation
├─ AuthN
├─ AuthZ
├─ Business
├─ Handler Not Found
├─ Timeout
├─ Overload
├─ DB
├─ External
└─ Unknown
```

---

# 58. Unknown Error는 별도 지표

```text
Unknown Exception Rate
```

가 증가하면:

```text
Error Standard Gap
Regression
Unhandled Runtime Path
```

가능성을 조사한다.

---

# 59. JWT Monitoring

## FIG-OPS-40. Security Dashboard

```text
JWT
│
├─ Login Success/Fail
├─ Token Issue
├─ Signature Fail
├─ Expired
├─ Unknown kid
├─ JWKS Error
├─ Refresh Fail
├─ Revoked Token
└─ Authorization Denied
```

---

# 60. Current JWT Critical Operations View

8장에서 확인한 핵심 Risk:

```text
RS256 Issuer
    vs
HMAC Verifier
```

운영에서 반드시 다음을 확인한다.

```text
Algorithm
kid
Key Source
Verifier Type
Token Success/Fail
```

---

# 61. JWT Key Monitoring

## FIG-OPS-41. Key Consistency

```text
JWT #1
kid=K2
key fingerprint=F2

JWT #2
kid=K2
key fingerprint=F2

DR JWT
kid=K2
key fingerprint=F2
```

불일치:

```text
CRITICAL ALERT
```

---

# 62. JWKS Monitoring

```text
JWKS Availability
JWK Count
Active kid
Key Fingerprint
Cache Refresh Error
Unknown kid Rate
```

---

# 63. Refresh / Denylist Monitoring

```text
Refresh Success
Refresh Failure
Refresh Reuse
Revocation
Denylist Lookup
Token Family
```

실제 Enforcement 여부는 Source/Runtime 검증이 필요하다.

---

# 64. Identity Binding Security Alert

## FIG-OPS-42. Identity Mismatch

```text
JWT sub = USER-A
Header optr_eno = USER-B
       │
       ▼
Mismatch
       │
       ▼
Reject / Audit / Alert
```

Target Security Control 후보다.

---

# 65. Log Architecture

## FIG-OPS-43. Log Channels

```text
Runtime
│
├─ Access Log
├─ Application Log
├─ Transaction Log
├─ SQL Log
├─ Security Audit
├─ ImageLog
└─ Deployment Log
```

---

# 66. Log Correlation

```text
GUID
ServiceId
Host
JVM
Thread
User
ErrorCode
SqlId
DeploymentId
```

가능한 범위에서 공통 Correlation Field를 유지한다.

---

# 67. ImageLog vs Application Log

## FIG-OPS-44. Evidence Difference

```text
Application Log
= 실행 상세 / 진단

ImageLog
= 요청/응답/오류 운영 Evidence
```

둘을 동일시하지 않는다.

---

# 68. ImageLog Fail-open Alert

```text
Business Success
+
ImageLog Failure
```

일 수 있으므로:

```text
Audit Write Failure
```

를 별도 Alert해야 한다.

---

# 69. Sensitive Log Control

## FIG-OPS-45. Log Security Gate

```text
Log Event
  ↓
Sensitive?
  ├─ Token
  ├─ Password
  ├─ Private Key
  ├─ Secret
  └─ Personal Data
  ↓
Mask / Drop / Secure Audit
```

---

# 70. Log Retention

필요 정책:

```text
Application Log Retention
Security Audit Retention
ImageLog Retention
Deployment Log Retention
Evidence Retention
```

정확한 기간은 현재 자료로 임의 확정하지 않는다.

---

# 71. Trace Architecture

## FIG-OPS-46. Trace Chain

```text
GUID / TraceId
    │
    ├─ WEB
    ├─ WAS
    ├─ TCF
    ├─ Worker
    ├─ SQL
    ├─ External
    └─ Response
```

---

# 72. Trace Sampling

대규모 환경에서는 Sampling 정책이 필요할 수 있다.

하지만:

```text
Error
Timeout
Security Event
Critical Transaction
```

은 높은 Evidence 보존 필요성이 있다.

정확한 Sampling Rate는 `[TBD]`.

---

# 73. Metric / Log / Trace 연결

## FIG-OPS-47. Triangulation

```text
Metric
"p95 증가"
   ↓
Trace
"어느 구간?"
   ↓
Log
"왜?"
```

---

# 74. Observability Failure

```text
Business 정상
  +
Monitoring System 장애
```

가능하다.

운영 Monitoring 자체도 HA/Health를 가져야 한다.

---

# 75. Observability Dependency 금지

```text
APM Down
   ↓
Business Runtime Down

X
```

Observability는 가능한 한 Business Runtime의 강한 동기 Dependency가 아니어야 한다.

---

# 76. Alert Architecture

## FIG-OPS-48. Alert Lifecycle

```text
Metric / Log / Trace
      ↓
Rule
      ↓
Alert
      ↓
Severity
      ↓
Owner
      ↓
Runbook
      ↓
Action
      ↓
Evidence
```

---

# 77. Alert Quality

좋은 Alert:

```text
무슨 서비스?
무슨 증상?
언제?
얼마나?
어디서?
무슨 Runbook?
```

---

# 78. Alert Storm

## FIG-OPS-49. Alert Correlation

```text
DB Slow
  ↓
Hikari Pending Alert
Worker Queue Alert
Timeout Alert
Service p95 Alert
```

이를 4개의 독립 Incident로 만들지 않도록 Correlation이 필요하다.

---

# 79. Severity

```text
CRITICAL
HIGH
MEDIUM
INFO
```

판정축:

```text
Business Impact
Data Risk
Security Risk
Recovery Urgency
Affected Scope
```

---

# 80. Runbook Architecture

## FIG-OPS-50. Runbook Flow

```text
Symptom
  ↓
Check
  ↓
Diagnosis
  ↓
Decision
  ↓
Action
  ↓
Validation
  ↓
Escalation
  ↓
Evidence
```

---

# 81. Runbook은 증상 중심

나쁜 시작:

```text
Tomcat을 재시작한다
```

좋은 시작:

```text
"504 급증"
→ Worker?
→ Hikari?
→ SQL?
→ External?
```

---

# 82. 503 Runbook

## FIG-OPS-51. Overload Runbook

```text
503
 ↓
Worker Rejected?
 ↓
Queue Full?
 ↓
Active Worker?
 ↓
Hikari Pending?
 ↓
DB / External?
 ↓
Scale / Recover / Throttle
```

---

# 83. 504 Runbook

## FIG-OPS-52. Timeout Runbook

```text
504
 ↓
ServiceId
 ↓
Worker Task Duration
 ↓
SQL / External
 ↓
TX Outcome
 ↓
Late Commit Check
 ↓
Recovery
```

---

# 84. JVM High CPU Runbook

```text
CPU High
 ↓
GC?
 ↓
Busy Thread?
 ↓
Hot Method?
 ↓
SQL Wait?
 ↓
Thread Dump / Profile
 ↓
Action
```

---

# 85. Hikari Exhaustion Runbook

## FIG-OPS-53. Pool Exhaustion

```text
Pending ↑
 ↓
Active == Max?
 ↓
Long SQL?
 ↓
Connection Leak?
 ↓
DB Slow?
 ↓
Pool Size / SQL / DB Action
```

---

# 86. JWT Error Runbook

```text
JWT Failure
 ↓
Expired?
 ↓
Unknown kid?
 ↓
Signature?
 ↓
JWKS?
 ↓
Clock?
 ↓
Key Consistency?
```

---

# 87. ImageLog Failure Runbook

```text
ImageLog Error
 ↓
DB?
 ↓
Schema?
 ↓
Permission?
 ↓
Runtime DDL?
 ↓
Business Impact?
 ↓
Audit Gap Reconcile
```

---

# 88. Event Lag Runbook

```text
Consumer Lag
 ↓
Producer Burst?
 ↓
Consumer Down?
 ↓
Partition?
 ↓
External/DB Slow?
 ↓
Scale / Restart / Replay
```

---

# 89. CDC Lag Runbook

```text
CDC Lag
 ↓
Capture?
 ↓
Network?
 ↓
Relay?
 ↓
Apply?
 ↓
Target DB?
 ↓
Restart / Catch-up / Reconcile
```

---

# 90. Batch Delay Runbook

```text
Job Late
 ↓
Scheduler?
 ↓
Previous Dependency?
 ↓
DB?
 ↓
Data Volume?
 ↓
Retry/Restart?
```

---

# 91. Runtime Inventory

## FIG-OPS-54. Runtime Inventory Chain

```text
System
  ↓
Application
  ↓
Artifact
  ↓
Host
  ↓
JVM
  ↓
WAR
  ↓
ServiceId
  ↓
Runtime Metric
```

---

# 92. Runtime Inventory 최소 필드

```yaml
runtime:
  system:
  application:
  environment:
  center:
  host:
  jvm:
  artifact:
  artifactHash:
  context:
  version:
  serviceIds:
  datasource:
  deploymentId:
  monitoring:
  status:
```

---

# 93. Capacity Baseline vs Runtime

## FIG-OPS-55. Capacity Drift

```text
Capacity Design
  │
  ├─ VM Size
  ├─ Thread
  ├─ Worker
  ├─ Hikari
  └─ Session
  │
  ▼ compare
Actual Config
  │
  ▼ compare
Runtime Metric
  │
  ▼
PASS / DRIFT
```

---

# 94. Candidate Threshold ≠ Alert Threshold

```text
Capacity Design에서 70%
=
Production Alert 70%

자동 아님
```

실제 Alert Threshold는:

```text
Load Test
+
Production Baseline
+
SLO
```

로 확정한다.

---

# 95. Config Baseline Registry

## FIG-OPS-56. Config Baseline

```text
Component
│
├─ Expected Config
├─ Actual Config
├─ Source
├─ Effective Time
└─ Drift
```

예:

```text
Tomcat maxThreads
Worker Pool
Hikari maxPoolSize
Session Timeout
JWT kid
Filter Order
```

---

# 96. Config Drift

```text
Expected
timeout=4000
    │
    ▼
Actual
timeout=5000
    │
    ▼
DRIFT
```

---

# 97. Deployment Drift

## FIG-OPS-57. Deployment Architecture Drift

```text
Architecture
WAR A → JVM A → Host Group A
        │
        │ compare
        ▼
Runtime Inventory
WAR A → JVM B → Host Group B
        │
        ▼
DRIFT
```

---

# 98. Source Drift

```text
Baseline Commit
    │
    ▼
Production Artifact
    │
    ▼
Different Commit?
```

---

# 99. Artifact Drift

```text
Manifest Hash
   │
   │ compare
   ▼
Runtime Artifact Hash
```

---

# 100. Schema Drift

## FIG-OPS-58. DB Drift

```text
Expected Schema
    │
    ▼ compare
Actual Schema
    │
    ▼
Missing / Extra / Modified
```

---

# 101. JWT Drift

```text
Expected
alg = RS256
kid = K2
fingerprint = F2

Actual
alg = HS256 / kid K2 / F3

→ CRITICAL DRIFT
```

---

# 102. ServiceId Drift

```text
Model
13 ServiceIds
   │
   ▼ compare
Runtime Registry
? ServiceIds
```

기동 시 실제 Registry Evidence를 남길 수 있다면 가장 강한 검증이 된다.

---

# 103. Filter Order Drift

```text
Old Doc
order=1

Current Source
HIGHEST_PRECEDENCE+20
```

운영 Baseline은 Source/Config를 기준으로 최신화한다.

---

# 104. Deployment Verification

## FIG-OPS-59. Post-deploy Verification

```text
Deploy
 ↓
Process Up
 ↓
Health
 ↓
Correct Version
 ↓
Correct Config
 ↓
Correct Key
 ↓
Smoke ServiceId
 ↓
Monitoring
 ↓
Release Complete
```

---

# 105. Smoke Test

대표:

```text
Health
Login / JWT
Read-only Business
DB Connectivity
External Connectivity
Critical ServiceId
```

실제 Smoke Scenario는 서비스별 승인한다.

---

# 106. Runtime Verification

```text
Artifact Version?
Config Version?
JVM?
Thread?
Datasource?
JWT kid?
ServiceId Registry?
```

---

# 107. Release Gate

## FIG-OPS-60. Operations Release Gate

```text
Source fixed?
 ↓
Build PASS?
 ↓
Architecture Rule PASS?
 ↓
Test PASS?
 ↓
Security PASS?
 ↓
Artifact identified?
 ↓
Deployment manifest?
 ↓
Runtime health?
 ↓
Monitoring active?
 ↓
Rollback ready?
 ↓
PASS
```

---

# 108. Runtime Evidence Gate

## FIG-OPS-61. Evidence Gate

```text
Deployment ID
    ↓
Runtime Scenario
    ↓
GUID / TraceId
    ↓
Metric / Log
    ↓
Expected Outcome
    ↓
Evidence Manifest
```

---

# 109. Runtime Evidence 없는 PASS 금지

```text
Deploy 성공
+
Process 살아 있음
```

만으로:

```text
Architecture PASS
```

라 할 수 없다.

---

# 110. Release Evidence Package

## FIG-OPS-62. Evidence Pack

```text
RELEASE
│
├─ Architecture Baseline ID
├─ Source Commit
├─ Build ID
├─ Artifact Hash
├─ Deployment ID
├─ Config Version
├─ Security Scan
├─ Test Result
├─ Runtime Smoke
├─ Performance / Failure [필요 시]
├─ Monitoring Screenshot/Export
├─ Rollback Point
└─ Approval
```

---

# 111. Runtime Evidence Chain

```text
architectureBaselineId
        ↓
modelVersion
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

# 112. OM Dashboard — Executive View

## FIG-OPS-63. Top-level Dashboard

```text
SYSTEM HEALTH
│
├─ Availability
├─ TPS
├─ p95
├─ Error Rate
├─ Timeout
├─ Overload
├─ Critical Alert
└─ Deployment Version
```

---

# 113. OM Dashboard — Service View

```text
ServiceId
├─ TPS
├─ p95
├─ p99
├─ Error
├─ Timeout
├─ SQL Time
├─ External Time
└─ Instances
```

---

# 114. OM Dashboard — Instance View

## FIG-OPS-64. Instance Drill-down

```text
Host
 ↓
JVM
 ↓
WAR
 ↓
ServiceId
 ↓
Thread / Worker
 ↓
Hikari
 ↓
SQL
```

---

# 115. OM Dashboard — JVM View

```text
JVM
├─ CPU
├─ Heap
├─ GC
├─ Threads
├─ Uptime
├─ Restart
└─ Version
```

---

# 116. OM Dashboard — DB Pool View

```text
Datasource
├─ Active
├─ Idle
├─ Pending
├─ Max
└─ Acquire Time
```

---

# 117. OM Dashboard — Error View

```text
Error Code
├─ Count
├─ ServiceId
├─ JVM
├─ GUID
└─ Trend
```

---

# 118. OM Dashboard — Security View

```text
Login Fail
JWT Verify Fail
Unknown kid
Revocation
Authorization Denied
Identity Mismatch
```

---

# 119. OM Dashboard — Deployment View

```text
Environment
 ↓
System
 ↓
Artifact Version
 ↓
Deployment Time
 ↓
Commit
 ↓
Operator
 ↓
Status
```

---

# 120. OM Dashboard — Drift View

```text
Config Drift
Deployment Drift
Source Drift
Schema Drift
Security Drift
Runtime Drift
```

---

# 121. Service Level View

## FIG-OPS-65. SLO Dashboard

```text
Service
│
├─ Availability SLO
├─ Latency SLO
├─ Error SLO
└─ Freshness SLO [Data]
```

SLO는 반드시 측정지점을 가진다.

---

# 122. CDC Freshness View

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

30초/3초 Conflict를 Dashboard에 임의 반영하지 않는다.
승인된 SLA가 필요하다.

---

# 123. BI / ETL View

```text
ETL Job
├─ Duration
├─ Rows In/Out
├─ Reject
└─ Delay

BI
├─ Query Duration
├─ Concurrency
└─ Heavy Query
```

---

# 124. Event View

```text
Topic / Stream
├─ Produce Rate
├─ Consume Rate
├─ Lag
├─ Retry
└─ DLQ
```

---

# 125. File View

```text
InterfaceId
├─ File Name
├─ Size
├─ Hash
├─ Record Count
├─ Status
└─ Retry
```

---

# 126. HA Monitoring

## FIG-OPS-66. HA View

```text
Service VIP
│
├─ Member #1 UP
├─ Member #2 UP
└─ Residual Capacity
```

---

# 127. Node Failure Evidence

```text
Node Down
 ↓
Detection
 ↓
Member Remove
 ↓
Traffic Shift
 ↓
p95 / Error
 ↓
Evidence
```

---

# 128. DR Monitoring

## FIG-OPS-67. DR Readiness

```text
DR
│
├─ Artifact Sync
├─ Config Sync
├─ Key Sync
├─ Data Replication
├─ Route Ready
├─ External Ready
├─ Runbook
└─ Last DR Test
```

---

# 129. DR Runtime Evidence

```text
Failover Start
 ↓
Route
 ↓
Application
 ↓
Security
 ↓
Data
 ↓
Business Validation
 ↓
RTO/RPO
```

---

# 130. Backup Monitoring

```text
Backup Job Success
Restore Test Date
Restore Duration
Validation Result
```

Backup 성공만으로 Recovery Ready로 표시하지 않는다.

---

# 131. Operational Change

## FIG-OPS-68. Runtime Parameter Change

```text
Need Change
 ↓
Change Request
 ↓
Impact Review
 ↓
Approval
 ↓
Apply
 ↓
Runtime Verify
 ↓
Evidence
 ↓
Baseline Update
```

---

# 132. Dynamic Config 주의

실시간 Config 변경이 가능하더라도:

```text
Who
What
Before
After
Why
When
Approval
```

가 남아야 한다.

---

# 133. Config as Code

## FIG-OPS-69. Configuration Governance

```text
Config Source
  ↓
Version Control
  ↓
Review
  ↓
Deploy
  ↓
Runtime Compare
```

---

# 134. Security in CI/CD

## FIG-OPS-70. Security Gate

```text
Source
 ↓
Secret Scan
 ↓
Dependency Scan
 ↓
Security Test
 ↓
Artifact
 ↓
Key/Secret Injection
 ↓
Runtime Security Check
```

---

# 135. Private Key CI/CD 원칙

```text
Private Key
≠ Build Artifact content
```

Key는 Deployment/Runtime Security Boundary에서 주입한다.

---

# 136. Database Change Governance

```text
Migration
 ↓
Review
 ↓
Test
 ↓
Approval
 ↓
Apply
 ↓
Schema Verify
 ↓
Evidence
```

---

# 137. Log Configuration Change

```text
Log Level
Masking
Appender
Retention
```

도 Config Baseline으로 관리한다.

---

# 138. Debug Log 운영 위험

```text
DEBUG ON
 ↓
Volume ↑
Sensitive Info Risk ↑
I/O ↑
```

긴급변경은 만료시간을 둔다.

---

# 139. Observability Maturity

## FIG-OPS-71. Maturity Model

```text
L0
로그만 있음
  ↓
L1
Metric 있음
  ↓
L2
Dashboard
  ↓
L3
Alert / Runbook
  ↓
L4
Trace / Correlation
  ↓
L5
Drift / Evidence / Architecture Closed Loop
```

---

# 140. Current vs Target

```text
PDMG Current
GUID / MDC / ImageLog / Error / Source Runtime
        │
        ▼
Target Operations
Central Metric / Trace / Dashboard / Alert / Runbook / Drift / Evidence
```

---

# 141. Operations Closed Loop

## FIG-OPS-72. Operations → Architecture

```text
Deploy
 ↓
Operate
 ↓
Observe
 ↓
Incident / Drift
 ↓
GAP
 ↓
ADR
 ↓
Standard / Code Change
 ↓
Deploy
```

---

# 142. Incident → Architecture

```text
Incident
  ↓
Root Cause
  ↓
Was architecture rule missing?
  ↓
Rule / Test / Baseline Update
```

---

# 143. Problem Management

```text
Repeated Incident
   ↓
Problem
   ↓
Root Cause
   ↓
Architecture Debt
   ↓
Permanent Fix
```

---

# 144. Architecture Drift Categories

```text
Document
Model
Source
Config
Deployment
Runtime
Security
Data
```

---

# 145. Drift Handling

## FIG-OPS-73. Drift Process

```text
Detect
 ↓
Classify
 ↓
Severity
 ↓
Owner
 ↓
Fix / Accept / ADR
 ↓
Verify
 ↓
Close
```

---

# 146. Critical Drift 예

```text
JWT alg mismatch
Direct WAS bypass
Timeout late commit
Wrong production artifact
DR key mismatch
```

---

# 147. High Drift 예

```text
Session config mismatch
Hikari baseline mismatch
Filter order doc drift
UI Service Catalog drift
```

---

# 148. Operations KPI

```text
Availability
p95
Error Rate
Timeout Rate
Overload Rate
MTTD
MTTR
Deployment Failure Rate
Rollback Rate
Critical Drift
Evidence Coverage
```

---

# 149. Architecture KPI와 Operations KPI

```text
Operations KPI
= 실제 운영 품질

Architecture KPI
= 설계-구현-운영 정합성
```

둘을 함께 본다.

---

# 150. Change Failure Rate

```text
Deployments
  ↓
Incident / Rollback?
  ↓
Change Failure Rate
```

---

# 151. Deployment Frequency

단순 많이 배포하는 것이 목표가 아니라:

```text
Safe
Traceable
Recoverable
```

배포가 목표다.

---

# 152. MTTR

```text
Detect
 ↓
Diagnose
 ↓
Recover
 ↓
Validate
```

Observability와 Runbook이 MTTR에 직접 영향을 준다.

---

# 153. Evidence Coverage

```text
Critical Services with Runtime Evidence
---------------------------------------
Total Critical Services
```

Critical Service는 100%를 목표로 할 수 있으나 실제 목표값은 승인 필요.

---

# 154. Deployment Trace Coverage

```text
Mapped Runtime Artifacts
------------------------
Deployed Artifacts
```

---

# 155. Service Trace Coverage

```text
ServiceIds traced to SQL/Deployment/Evidence
-------------------------------------------
Total ServiceIds
```

---

# 156. Operations RACI

| Activity | Dev | Architect | Ops | Security | DBA/Data | PMO |
|---|---|---|---|---|---|---|
| Build | R | C | I | C | I | I |
| Architecture Rule | C | A/R | I | C | C | I |
| Deploy | C | C | A/R | C | C | I |
| Monitor | C | C | A/R | C | C | I |
| Security Incident | C | C | C | A/R | I | I |
| DB Incident | C | C | C | I | A/R | I |
| Drift Review | C | A/R | R | C | C | I |
| Baseline Release | C | A/R | C | C | C | A/C |

실제 조직체계에 따라 확정한다.

---

# 157. OM / Ops 권한 분리

## FIG-OPS-74. Read vs Change Permission

```text
Viewer
→ Read Dashboard

Operator
→ Approved Operational Action

Admin
→ Config / Control

Architect
→ Baseline / Rule

Security
→ Security Control
```

---

# 158. Break-glass

긴급 운영권한은:

```text
Emergency
 ↓
Temporary Elevated Access
 ↓
Action
 ↓
Audit
 ↓
Expiry
 ↓
Review
```

가 필요하다.

---

# 159. Operations Security Audit

```text
Login
Privilege Change
Config Change
Deployment
Runtime Control
Key Rotation
DR Action
```

은 Audit 대상 후보다.

---

# 160. Operations Evidence Repository

## FIG-OPS-75. Evidence Repository

```text
Evidence
│
├─ Build
├─ Test
├─ Deployment
├─ Runtime
├─ Performance
├─ Security
├─ HA
├─ DR
└─ Backup/Restore
```

---

# 161. Evidence Manifest

```yaml
evidence:
  evidenceId:
  baselineId:
  sourceCommit:
  buildId:
  artifactHash:
  deploymentId:
  environment:
  serviceId:
  traceId:
  scenario:
  result:
  metrics:
  logs:
  owner:
  createdAt:
  evidenceHash:
```

---

# 162. Evidence Integrity

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

# 163. Evidence Retention

정확한 기간은 정책 확정이 필요하나:

```text
Release Audit
Security Audit
DR Evidence
Architecture Baseline
```

과 연계되어야 한다.

---

# 164. Operations Gate

## FIG-OPS-76. Operations Gate

```text
Build Artifact Identified?
 ↓
Deployment Manifest?
 ↓
Config Baseline?
 ↓
Monitoring Active?
 ↓
Security Check?
 ↓
Smoke Test?
 ↓
Rollback Ready?
 ↓
Runtime Evidence?
 ↓
No Critical Drift?
 ↓
PASS
```

---

# 165. Architecture Closed Loop와 연결

```text
09 Operations
   ↓
Deployment Evidence
   ↓
Runtime Evidence
   ↓
Drift
   ↓
07 Closed Loop
   ↓
GAP / ADR
   ↓
New Baseline
```

---

# 166. X장 Naming / Traceability와 연결

기존 Architecture 구조의 X장 관점으로 보면:

```text
ServiceId
 ↓
Source
 ↓
Artifact
 ↓
Deployment
 ↓
GUID
 ↓
Runtime Evidence
```

9장은 그 중:

```text
Artifact
Deployment
Runtime
Evidence
```

를 운영 Control Plane에서 담당한다.

---

# 167. OM 대상 Metric Matrix

| 대상 | 핵심 Metric |
|---|---|
| ServiceId | TPS/p95/Error/Timeout |
| Tomcat | Busy/Max Threads |
| Worker | Active/Queue/Reject |
| JVM | CPU/Heap/GC/Thread |
| Hikari | Active/Idle/Pending |
| DB | Session/Wait/SQL |
| JWT | Verify/Unknown kid/Auth Fail |
| Event | Lag/Retry/DLQ |
| CDC | Capture/Apply Lag |
| ETL | Duration/Rows/Reject |
| Batch | Status/Delay/Restart |
| File | Transfer/Hash/Record |
| Deployment | Version/Hash/Status |

---

# 168. Monitoring Requirement — Transaction

```text
GUID
ServiceId
Start Time
End Time
Elapsed
HTTP Status
Error Code
Host/JVM
```

---

# 169. Monitoring Requirement — Thread

```text
Tomcat Busy
Worker Active
Worker Queue
Rejected
Thread Dump Trigger
```

---

# 170. Monitoring Requirement — DB

```text
Hikari Pending
Connection Acquire
SQL Elapsed
DB Wait
Lock
```

---

# 171. Monitoring Requirement — Security

```text
Authentication Fail
Authorization Denied
JWT Verify Fail
Unknown kid
Identity Mismatch
```

---

# 172. Monitoring Requirement — Deployment

```text
Commit
Artifact Hash
Deployment ID
Host
JVM
Version
Health
```

---

# 173. Runtime Test Scenario — Deploy

```text
Deploy Artifact
 ↓
Health
 ↓
Smoke ServiceId
 ↓
DB
 ↓
JWT
 ↓
Monitoring
 ↓
Evidence
```

---

# 174. Runtime Test Scenario — 503

```text
Saturate Worker
 ↓
Queue Full
 ↓
503
 ↓
Alert
 ↓
Runbook
 ↓
Recovery
```

---

# 175. Runtime Test Scenario — 504

```text
Slow SQL
 ↓
Worker Deadline
 ↓
504
 ↓
Rollback
 ↓
Late Commit check
 ↓
Evidence
```

---

# 176. Runtime Test Scenario — Hikari

```text
Hold Connections
 ↓
Pending ↑
 ↓
Alert
 ↓
Diagnosis
 ↓
Recovery
```

---

# 177. Runtime Test Scenario — JWT Key

```text
Token Issue
 ↓
JWT Restart / Instance Change
 ↓
Verify
 ↓
Unknown kid / Signature?
 ↓
Evidence
```

---

# 178. Runtime Test Scenario — Node Failure

```text
WAS Node Down
 ↓
L4 Detect
 ↓
Traffic Shift
 ↓
p95/Error
 ↓
Residual Capacity
 ↓
Evidence
```

---

# 179. Runtime Test Scenario — DR

```text
Main Failure
 ↓
DR Route
 ↓
App
 ↓
JWT
 ↓
DB
 ↓
Business
 ↓
RTO/RPO
```

---

# 180. Architecture Conformance Rules — Operations

```text
R-OPS-ARTIFACT-HASH
R-OPS-DEPLOYMENT-MANIFEST
R-OPS-CONFIG-BASELINE
R-OPS-RUNTIME-INVENTORY
R-OPS-SERVICE-METRIC
R-OPS-WORKER-METRIC
R-OPS-HIKARI-METRIC
R-OPS-JWT-KEY-CONSISTENCY
R-OPS-ALERT-RUNBOOK
R-OPS-DR-EVIDENCE
R-OPS-RESTORE-EVIDENCE
R-OPS-CRITICAL-DRIFT
```

---

# 181. R-OPS-ARTIFACT-HASH

```text
Deployment Artifact
   ↓
Hash exists?
   ├─ YES
   └─ NO → FAIL
```

---

# 182. R-OPS-DEPLOYMENT-MANIFEST

```text
Artifact
→ Host/JVM/Context
```

전수 Mapping이 있어야 한다.

---

# 183. R-OPS-CONFIG-BASELINE

```text
Expected Config
 vs
Actual Config
```

Critical Difference는 FAIL 후보.

---

# 184. R-OPS-RUNTIME-INVENTORY

```text
Running JVM
 ↓
Known Deployment?
```

UNKNOWN Runtime Process는 운영 Risk다.

---

# 185. R-OPS-JWT-KEY-CONSISTENCY

```text
JWT Instances
 ↓
same kid?
same key fingerprint?
```

불일치:

```text
CRITICAL FAIL
```

---

# 186. R-OPS-ALERT-RUNBOOK

```text
Critical Alert
 ↓
Runbook exists?
```

없으면 Operations Gate 조건 미완료 후보.

---

# 187. R-OPS-DR-EVIDENCE

```text
Critical Service
 ↓
Recent DR Evidence?
```

정책 Window는 별도 승인한다.

---

# 188. R-OPS-RESTORE-EVIDENCE

```text
Backup
 ↓
Restore Test Evidence?
```

---

# 189. R-OPS-CRITICAL-DRIFT

```text
Critical Drift Count
= 0
```

HG90 후보조건과 연결한다.

---

# 190. CONFIRMED / WORKING BASELINE

```text
[CONFIRMED / WORKING BASELINE]

- NSIGHT Strategy에 GitLab / GitLab Runner / eCAMS 방향 존재
- PDMG Java 21 / Spring Boot 3.5.14 / Gradle Multi-project
- pdmg-service와 pdmg-jwt가 pdmg-fw를 사용
- PDMG Runtime에 GUID/MDC/ImageLog/Error/Worker/Timeout 관측지점 존재
- Runtime Evidence는 Deployment/ServiceId/GUID와 연결해야 함
- OM은 Control Plane으로 설계
- pdmg-om 실제 상세 구현은 UNKNOWN
- Capacity Design 값과 Alert Threshold를 구분
- Release PASS에는 Runtime Evidence가 필요
```

---

# 191. OPEN

```text
[OPEN-OPS-01]
Current GitLab CI Pipeline 구현

[OPEN-OPS-02]
Current eCAMS 실제 배포 Mapping

[OPEN-OPS-03]
pdmg-om Source / Runtime

[OPEN-OPS-04]
Current Metric Exporter / APM Agent

[OPEN-OPS-05]
Central Log Platform

[OPEN-OPS-06]
Trace Platform

[OPEN-OPS-07]
Alert Threshold

[OPEN-OPS-08]
Runbook Repository

[OPEN-OPS-09]
Deployment Manifest 자동생성

[OPEN-OPS-10]
Runtime Inventory 자동수집

[OPEN-OPS-11]
Evidence Repository

[OPEN-OPS-12]
DR/Restore Evidence 주기
```

---

# 192. GAP Register

| ID | GAP | 영향 |
|---|---|---|
| GAP-OPS-01 | Current CI/CD Pipeline Evidence 미확인 | DevOps |
| GAP-OPS-02 | Production Deployment Manifest 미완료 | Deployment |
| GAP-OPS-03 | pdmg-om Source/Runtime 미확인 | OM |
| GAP-OPS-04 | Central Metric Collection 미확인 | Observability |
| GAP-OPS-05 | Central Trace 미확인 | Trace |
| GAP-OPS-06 | Runtime Inventory 자동화 미완료 | Operations |
| GAP-OPS-07 | ServiceId Dashboard 미완료 | Observability |
| GAP-OPS-08 | Worker/Hikari 통합 Dashboard 미완료 | Capacity |
| GAP-OPS-09 | JWT Key Consistency Monitoring 미완료 | Security |
| GAP-OPS-10 | Alert Threshold 미확정 | Operations |
| GAP-OPS-11 | Runbook Catalog 미완료 | Recovery |
| GAP-OPS-12 | Config Drift 자동화 미완료 | Governance |
| GAP-OPS-13 | Artifact Drift 자동화 미완료 | Governance |
| GAP-OPS-14 | Evidence Manifest 자동화 미완료 | Evidence |
| GAP-OPS-15 | DR Evidence 자동연결 미완료 | DR |
| GAP-OPS-16 | Restore Evidence 자동연결 미완료 | Backup |
| GAP-OPS-17 | ImageLog Fail-open Alert 미완료 | Audit |
| GAP-OPS-18 | Sensitive Log Masking 전사정책 미확정 | Security |
| GAP-OPS-19 | Runtime Parameter Change Governance 미완료 | Control |
| GAP-OPS-20 | Operations Gate 자동화 미완료 | Release |

---

# 193. RISK Register

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-OPS-01 | Strategy Tool 존재를 구현완료로 오판 | High |
| RISK-OPS-02 | Artifact/Host/JVM 추적 불가 | Critical |
| RISK-OPS-03 | 운영서버 수동변경 | Critical |
| RISK-OPS-04 | Secret/Key Source 저장 | Critical |
| RISK-OPS-05 | JWT Instance Key 불일치 | Critical |
| RISK-OPS-06 | Alert Storm | High |
| RISK-OPS-07 | Alert에 Runbook 없음 | High |
| RISK-OPS-08 | 504 원인을 HTTP 레벨로만 판단 | High |
| RISK-OPS-09 | Worker/Hikari/DB 연쇄병목 미탐지 | Critical |
| RISK-OPS-10 | ImageLog 장애 감사공백 | High |
| RISK-OPS-11 | Config Drift 미탐지 | High |
| RISK-OPS-12 | Production Artifact Drift | Critical |
| RISK-OPS-13 | DR Resource만 있고 Runtime Evidence 없음 | Critical |
| RISK-OPS-14 | Backup Success만으로 Recovery 판단 | Critical |
| RISK-OPS-15 | OM이 Business Runtime 강한 Dependency | High |
| RISK-OPS-16 | pdmg-om 기능을 근거 없이 가정 | High |

---

# 194. ADR 후보

```text
ADR-OPS-01 DevOps Tool Responsibility
ADR-OPS-02 Build / Artifact Standard
ADR-OPS-03 Deployment Manifest
ADR-OPS-04 Same Artifact Promotion
ADR-OPS-05 Config / Secret Separation
ADR-OPS-06 OM Control Plane
ADR-OPS-07 pdmg-om Scope
ADR-OPS-08 ServiceId Observability
ADR-OPS-09 Worker/Hikari Monitoring
ADR-OPS-10 JWT Key Monitoring
ADR-OPS-11 Central Log / Trace
ADR-OPS-12 Alert Severity
ADR-OPS-13 Runbook Standard
ADR-OPS-14 Config Drift
ADR-OPS-15 Runtime Evidence Manifest
ADR-OPS-16 DR Evidence
ADR-OPS-17 Restore Evidence
ADR-OPS-18 Operations Release Gate
```

---

# 195. Verification Checklist — DevOps

```text
[ ] Source Commit 고정?
[ ] Build Reproducible?
[ ] Test/Rule 실행?
[ ] Artifact Hash?
[ ] Same Artifact Promotion?
[ ] Secret 분리?
[ ] Deployment Manifest?
[ ] Rollback Point?
```

---

# 196. Verification Checklist — OM

```text
[ ] Runtime Inventory?
[ ] Application/WAR 상태?
[ ] JVM/Thread?
[ ] Worker?
[ ] Hikari?
[ ] ServiceId View?
[ ] Error/Timeout?
[ ] Deployment History?
[ ] Drift?
```

---

# 197. Verification Checklist — Observability

```text
[ ] Metric?
[ ] Log?
[ ] Trace?
[ ] GUID?
[ ] ServiceId?
[ ] Deployment Context?
[ ] SQL Correlation?
[ ] Security Event?
```

---

# 198. Verification Checklist — Alert / Runbook

```text
[ ] Alert Owner?
[ ] Severity?
[ ] Symptom?
[ ] Diagnostic Step?
[ ] Recovery?
[ ] Escalation?
[ ] Evidence?
```

---

# 199. Verification Checklist — Security Operations

```text
[ ] JWT alg?
[ ] kid?
[ ] Key fingerprint?
[ ] JWKS?
[ ] Unknown kid Alert?
[ ] Identity mismatch?
[ ] Secret Scan?
[ ] Sensitive Log Masking?
```

---

# 200. Verification Checklist — HA / DR / Backup

```text
[ ] Node Failover?
[ ] Residual Capacity?
[ ] DR Artifact Sync?
[ ] DR Config Sync?
[ ] DR Key Sync?
[ ] DR Data Ready?
[ ] DR Test Evidence?
[ ] Restore Test Evidence?
```

---

# 201. Completion Gate

## FIG-OPS-77. Operations Completion Gate

```text
Controlled Build?
   ↓ YES
Artifact Identified?
   ↓ YES
Deployment Traceable?
   ↓ YES
Runtime Inventory?
   ↓ YES
Metric / Log / Trace?
   ↓ YES
Alert / Runbook?
   ↓ YES
Security Monitoring?
   ↓ YES
HA/DR/Restore Evidence?
   ↓ YES
Drift Detection?
   ↓ YES
Runtime Evidence?
   ↓ YES
OPERATIONS PASS
```

---

# 202. 다음 장 Handoff

다음 장은 1~9장을 다시 하나로 묶는 **10. ARCHITECTURE BASELINE / NAMING / SERVICEID / TRACEABILITY INTEGRATION**이 자연스럽다.

## FIG-OPS-78. 09 → 10

```text
09 OPERATIONS
│
├─ Artifact
├─ Deployment
├─ Runtime Inventory
├─ Metric / Log / Trace
├─ Evidence
└─ Drift
       │
       ▼
10 INTEGRATION
│
├─ Naming
├─ ServiceId
├─ End-to-End Traceability
├─ Architecture Model
├─ Rule
├─ Gate
└─ Baseline Release
```

---

# 203. 10장에서 반드시 답할 질문

```text
1. Naming 체계가 Application/Package/Mapper/ServiceId에 일관적인가?
2. ServiceId 전수 Registry는 무엇인가?
3. ServiceId→Handler→SQL→Table가 추적되는가?
4. ServiceId→Artifact→JVM→Host가 추적되는가?
5. GUID가 Runtime Evidence와 연결되는가?
6. Architecture Model Entity/Relation은 무엇인가?
7. 어떤 Rule을 CI에서 실행하는가?
8. 어떤 Drift를 자동탐지하는가?
9. G00~HG90 Gate는 어떻게 통합되는가?
10. 최종 Baseline Package는 무엇인가?
```

---

# 204. 전체 1~9장 흐름

## FIG-OPS-79. Architecture Journey to Operations

```text
01 VISION
   ↓
02 BIG PICTURE
   ↓
03 LOGICAL
   ↓
04 PHYSICAL
   ↓
05 MECHANISM
   ↓
06 RUNTIME
   ↓
07 TRACEABILITY / CLOSED LOOP
   ↓
08 PDMG SOURCE REFERENCE
   ↓
09 OM / DEVOPS / OBSERVABILITY / OPERATIONS
   ↓
10 INTEGRATED BASELINE
```

---

# 205. Definition of Done

## DevOps
- [x] SCM/Runner/eCAMS Strategy 구분
- [x] Source→Build→Test→Artifact→Deploy
- [x] PDMG Build Baseline
- [x] Artifact/Config/Secret 분리
- [x] Same Artifact Promotion
- [x] Deployment Manifest
- [x] Rollback

## OM
- [x] Control Plane 정의
- [x] pdmg-om UNKNOWN 유지
- [x] OM Target Responsibility
- [x] Read/Change 권한 분리
- [x] Runtime Parameter Change Governance

## Observability
- [x] Metric/Log/Trace
- [x] ServiceId/GUID View
- [x] JVM/Tomcat/Worker
- [x] Hikari/DB/SQL
- [x] Timeout/Overload/Error
- [x] JWT/Security
- [x] Event/CDC/ETL/File/Batch

## Operations
- [x] Alert Lifecycle
- [x] Alert Storm
- [x] 503/504/JVM/Hikari/JWT Runbook
- [x] Event/CDC/Batch Runbook
- [x] Runtime Inventory
- [x] Config/Deployment/Schema/JWT Drift

## HA/DR/Backup
- [x] HA Monitoring
- [x] DR Readiness
- [x] DR Evidence
- [x] Backup/Restore Evidence

## Governance
- [x] Release Evidence
- [x] Operations Gate
- [x] Runtime Evidence Chain
- [x] Conformance Rules
- [x] GAP/RISK/ADR
- [x] 10장 Handoff

**OM / DEVOPS / OBSERVABILITY / OPERATIONS 장 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. 실제 GitLab CI/CD Pipeline Evidence 확보
2. eCAMS Production Deployment Evidence 확보
3. `pdmg-om` Source/Runtime 확인
4. Central Metric / Log / Trace Platform 실제 구성 확인
5. Runtime Inventory 자동수집
6. ServiceId / Worker / Hikari Dashboard 구현 또는 기존도구 Mapping
7. JWT Key/JWKS Monitoring
8. Alert Threshold / Severity 승인
9. Runbook Catalog 구축
10. Deployment Manifest 자동생성
11. Config/Artifact/Runtime Drift 자동탐지
12. DR/Restore Evidence Repository 연계
13. Operations Gate 자동화
14. Critical Drift 0건
15. Runtime Evidence Coverage 승인기준 충족

---

# 206. 장 최종 결론

> **DevOps는 GitLab이나 배포도구의 존재가 아니라 Source Commit부터 Runtime Evidence까지 변경을 추적하는 Delivery Architecture다.**

> **OM은 Business Runtime을 대신하는 시스템이 아니라 Runtime을 관찰하고 승인된 제어를 수행하는 Control Plane이다.**

> **Observability는 Metric·Log·Trace를 GUID/ServiceId/Deployment Context로 연결하여 “어디서 왜 느리고 실패했는가”를 설명할 수 있어야 한다.**

> **운영의 최종 산출물은 Dashboard가 아니라 Alert→Runbook→Recovery→Evidence이며, 이 Evidence가 다시 Drift/GAP/ADR을 통해 Architecture Baseline을 갱신해야 한다.**

> **현재 PDMG는 GUID/MDC/ImageLog/Worker/Timeout 등 중요한 관측 지점을 제공하지만, `pdmg-om`, Deployment Mapping, 중앙 Metric/Trace, JWT Key Monitoring, Runtime Evidence 자동화는 추가 확인·구현이 필요한 영역이다.**
