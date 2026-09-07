# PDMG 전체 아키텍처 정의서
# 14. PDMG DEVOPS / DEPLOYMENT / OM / OBSERVABILITY ARCHITECTURE
## SCM / Build / Artifact / Config / Deploy / Control Plane / Metric-Log-Trace / Evidence
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-14-DEVOPS-OM-OBSERVABILITY`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-14-01. 이 장의 핵심 질문

```text
PDMG Source가 어떻게 Build/Artifact/Deployment/Runtime으로 이동하는가?
OM Control Plane과 Business Runtime을 어떻게 분리하는가?
ServiceId/GUID/DeploymentId를 Metric/Log/Trace와 어떻게 연결하는가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-14-02. Evidence Flow

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
| EV-14-01 | IX DevOps/OM/Observability | strategy/current separation | [AS-IS + STRATEGY] |
| EV-14-02 | PDMG build evidence | Java21/SB3.5.14/Gradle | [AS-IS] |
| EV-14-03 | VI/VIII/X | GUID/infra/traceability | [WORKING BASELINE] |
| EV-14-04 | Decision Register | CI/deploy/obs/OM/backup | [DECISION] |
| EV-14-05 | pdmg-om evidence | current detail | [UNKNOWN] |

---

# 2. Figure Plan

## FIG-14-03. Top-down Drill-down

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

# 3. L0 — DevOps / Operations Master

## FIG-14-04. L0 — DevOps / Operations Master

```text
Source
 ↓
Build / Test
 ↓
Artifact
 ↓
Config / Secret
 ↓
Deployment
 ↓
Runtime
 ↓
Metric / Log / Trace
 ↓
Alert / Runbook
 ↓
Evidence
 ↓
Drift / ADR
```

---

# 4. Current vs Strategy Classification

## FIG-14-05. Current vs Strategy Classification

```text
PDMG Current
Java 21
Spring Boot 3.5.14
Gradle multi-project
Log / GUID / ImageLog

NSIGHT Strategy
GitLab
GitLab Runner
eCAMS
IaaS
APM / integrated log

Do not auto-mix
```

---

# 5. SCM / Build

## FIG-14-06. SCM / Build

```text
Git Source
 ↓
Commit
 ↓
Gradle
 ↓
Compile / Unit Test
 ↓
WAR / Artifact
 ↓
Hash
```

---

# 6. CI Gate

## FIG-14-07. CI Gate

```text
Commit
 ↓
Build
 ↓
Unit
 ↓
Naming / Dependency
 ↓
Contract / Security
 ↓
Architecture Rule
 ↓
Artifact
```

---

# 7. Artifact Promotion

## FIG-14-08. Artifact Promotion

```text
Build once
 ↓
Immutable Artifact
 ↓
DEV
 ↓
TEST
 ↓
PROD
 ↓
DR

Hash identical
```

---

# 8. Config / Secret Boundary

## FIG-14-09. Config / Secret Boundary

```text
Artifact
= code/binary

Environment Config
= external

Secret / Key
= protected store

Never bundle generic secret
```

---

# 9. Production Deployment

## FIG-14-10. Production Deployment

```text
Approved Release Manifest
 ↓
Change Approval
 ↓
Deploy
 ↓
Health Check
 ↓
Smoke / Runtime Evidence
 ├─ PASS → promote
 └─ FAIL → rollback
```

---

# 10. Deployment Trace

## FIG-14-11. Deployment Trace

```text
sourceCommit
 ↓
buildId
 ↓
artifactHash
 ↓
deploymentId
 ↓
WAR/JVM/Host
 ↓
ServiceId/GUID
```

---

# 11. OM Control Plane

## FIG-14-12. OM Control Plane

```text
Runtime Plane
pdmg-ui/jwt/service
  ↓ metric/log/control
Control Plane
OM / Monitoring / Deployment

pdmg-om current detail
= UNKNOWN
```

---

# 12. Observability Stack

## FIG-14-13. Observability Stack

```text
Metric
+ Structured Log
+ Trace
 ↓
ServiceId / GUID
 ↓
JVM / Host / Deployment
 ↓
Dashboard / Alert
```

---

# 13. Thread / Pool Monitoring

## FIG-14-14. Thread / Pool Monitoring

```text
Tomcat busy
 ↓
Worker active / queue
 ↓
Hikari active / pending
 ↓
DB sessions / waits
 ↓
SQL latency
```

---

# 14. Security Monitoring

## FIG-14-15. Security Monitoring

```text
Login fail
JWT verify fail
kid/JWKS fail
authorization deny
key rotation
 ↓
Security Alert
```

---

# 15. Incident / Runbook

## FIG-14-16. Incident / Runbook

```text
Alert
 ↓
Triage
 ↓
ServiceId / GUID
 ↓
Node / Pool / SQL
 ↓
Mitigation
 ↓
Recovery
 ↓
Problem / ADR
```

---

# 16. Backup / Restore Operations

## FIG-14-17. Backup / Restore Operations

```text
Backup Job
 ↓
Retention
 ↓
Restore Drill
 ↓
Data Consistency
 ↓
Application Validation
```

---

# 17. Runtime Evidence

## FIG-14-18. Runtime Evidence

```text
Architecture Rule
 ↓
Runtime Metric / Trace / Test
 ↓
Evidence ID
 ↓
Gate
 ↓
Baseline PASS
```

---

# 18. Drift Detection

## FIG-14-19. Drift Detection

```text
Architecture / Config Baseline
 ↓ compare
Actual Source / Deployment / Runtime
 ↓
Drift
 ↓
GAP / ADR / Fix
```

---

# 19. Architecture Rule Catalog

## FIG-14-20. Rule Set

```text
R-OPS-01
Build once, immutable artifact를 promotion한다.

R-OPS-02
SourceCommit→ArtifactHash→DeploymentId를 추적한다.

R-OPS-03
Config와 Secret을 Artifact에서 분리한다.

R-OPS-04
OM Control Plane과 Business Runtime Plane을 분리한다.

R-OPS-05
GitLab/Runner/eCAMS 전략을 PDMG current implementation으로 자동표시하지 않는다.

R-OPS-06
Metric/Log/Trace를 ServiceId/GUID와 연결한다.

R-OPS-07
Thread/Worker/Hikari/DB pool을 분리 관측한다.

R-OPS-08
Backup 성공 외 Restore/Business Validation을 수행한다.

R-OPS-09
Deployment 후 runtime evidence를 수집한다.

R-OPS-10
Critical drift는 release gate에서 차단한다.
```

| Rule | 정의 |
|---|---|
| R-OPS-01 | Build once, immutable artifact를 promotion한다. |
| R-OPS-02 | SourceCommit→ArtifactHash→DeploymentId를 추적한다. |
| R-OPS-03 | Config와 Secret을 Artifact에서 분리한다. |
| R-OPS-04 | OM Control Plane과 Business Runtime Plane을 분리한다. |
| R-OPS-05 | GitLab/Runner/eCAMS 전략을 PDMG current implementation으로 자동표시하지 않는다. |
| R-OPS-06 | Metric/Log/Trace를 ServiceId/GUID와 연결한다. |
| R-OPS-07 | Thread/Worker/Hikari/DB pool을 분리 관측한다. |
| R-OPS-08 | Backup 성공 외 Restore/Business Validation을 수행한다. |
| R-OPS-09 | Deployment 후 runtime evidence를 수집한다. |
| R-OPS-10 | Critical drift는 release gate에서 차단한다. |

---

# 20. Verification / Test

## FIG-14-21. Verification Flow

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
| T-OPS-01 | pipeline inventory/conformance |
| T-OPS-02 | artifact hash promotion |
| T-OPS-03 | secret scan |
| T-OPS-04 | deployment rollback |
| T-OPS-05 | observability correlation |
| T-OPS-06 | alert/runbook |
| T-OPS-07 | restore drill |
| T-OPS-08 | config drift |

---

# 21. GAP Register

## FIG-14-22. GAP Lifecycle

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
| GAP-OPS-01 | 실제 CI pipeline inventory | High | repository/pipeline scan |
| GAP-OPS-02 | eCAMS production job current evidence | Medium/High | deployment inventory |
| GAP-OPS-03 | pdmg-om current implementation | High | source/runtime evidence |
| GAP-OPS-04 | Artifact→Host/JVM/WAR trace | Critical | deployment manifest |
| GAP-OPS-05 | metric/log/trace integrated correlation | High | observability implementation |
| GAP-OPS-06 | restore/DR evidence | High | drill |

---

# 22. Risk Register

## FIG-14-23. Risk Propagation

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
| RISK-OPS-01 | environment rebuild | different binary in prod |
| RISK-OPS-02 | secret in source/artifact | credential leakage |
| RISK-OPS-03 | monitoring only CPU | business root cause hidden |
| RISK-OPS-04 | pdmg-om assumed implemented | false baseline |
| RISK-OPS-05 | manual deployment | human error/drift |

---

# 23. Architecture Decision / ADR

## FIG-14-24. Decision Flow

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
| ADR-TASK-033 | CI Orchestration | GitLab Runner primary candidate |
| ADR-TASK-034 | Artifact Promotion | immutable artifact |
| ADR-TASK-035 | Observability | metric+log+trace |
| ADR-TASK-036 | OM Control Plane | separate control plane |
| ADR-TASK-037 | Config/Secret | externalized/protected |
| ADR-TASK-038 | Backup/Restore | restore validation |
| ADR-TASK-039 | Production Release | manifest+health+rollback |

---

# 24. Architecture PASS / PDMG Conformance

## FIG-14-25. PASS Model

```text
Architecture Definition
 ↓
PASS

Current DevOps/Ops Conformance
 ↓
PARTIAL / OPEN

Strong: build/runtime clues
Open: pdmg-om, production pipeline, deployment evidence
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Build | PASS/PARTIAL | Java/Gradle |
| CI | OPEN/PARTIAL | strategy vs actual pipeline |
| Artifact promotion | PASS architecture | implementation evidence needed |
| OM | OPEN | pdmg-om unknown |
| Observability | PARTIAL | GUID/MDC/ImageLog |
| Backup/Restore | CONDITIONAL | restore drill |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PARTIAL / OPEN`  
**Runtime Evidence Coverage:** `MEDIUM`

---

# 25. Next Chapter Handoff

## FIG-14-26. 14 → 15

```text
14 DEVOPS / OM / OBSERVABILITY
"어떻게 배포하고 운영하며 증명하는가?"
     ↓
15 NAMING / CODE / DEVELOPMENT STANDARD
"모든 Architecture Object를 어떤 이름과 개발규칙으로 일관되게 구현하는가?" 
```

---

# 26. PDMG DEVOPS / DEPLOYMENT / OM / OBSERVABILITY ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG DEVOPS / DEPLOYMENT / OM / OBSERVABILITY ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**14장 Architecture Definition 판정: `PASS`**
