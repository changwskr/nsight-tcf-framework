# PDMG 전체 아키텍처 정의서
# 17. PDMG TRACEABILITY / CONFORMANCE / PASS / GAP / ADR ARCHITECTURE
## ServiceId / Model / Rule / Test / Runtime Evidence / Drift / Gate / HG90
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-17-TRACEABILITY-PASS-GAP-ADR`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-17-01. 이 장의 핵심 질문

```text
PDMG Architecture가 Source/Config/Test/Deployment/Runtime과 일치함을 어떻게 증명하는가?
Architecture PASS와 Current Implementation PASS를 어떻게 분리하는가?
Drift/GAP/ADR를 통해 Baseline을 어떻게 갱신하는가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-17-02. Evidence Flow

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
| EV-17-01 | X Naming/Traceability | closed loop/serviceId | [WORKING BASELINE] |
| EV-17-02 | Decision PASS Register | 40 task status | [WORKING DECISION REGISTER] |
| EV-17-03 | 00 Master Index | gate/evidence principles | [DECISION] |
| EV-17-04 | Chapters 01-16 | domain PASS/GAP | [WORKING BASELINE] |
| EV-17-05 | DevOps/Observability | runtime evidence | [WORKING BASELINE] |

---

# 2. Figure Plan

## FIG-17-03. Top-down Drill-down

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

# 3. L0 — Architecture Closed Loop

## FIG-17-04. L0 — Architecture Closed Loop

```text
Document
 ↓
Model
 ↓
Code
 ↓
Test
 ↓
Runtime Evidence
 ↓
Drift
 ↓
GAP
 ↓
ADR
 ↓
New Baseline
```

---

# 4. Workspace / Gate Model

## FIG-17-05. Workspace / Gate Model

```text
00-IN
10-DOCUMENT
20-MODEL
30-CODE / CONFORMANCE
40-TEST
50-RUNTIME-EVIDENCE
60-DRIFT
70-GAP-ADR
80-GATE
90-OUT / HG90
```

---

# 5. Gate G00 → HG90

## FIG-17-06. Gate G00 → HG90

```text
G00 Source
 ↓
G10 Document
 ↓
G20 Model
 ↓
G30 Conformance
 ↓
G40 Rule Test
 ↓
G50 Runtime Evidence
 ↓
G60 Drift
 ↓
G70 GAP/ADR
 ↓
G80 Approval
 ↓
HG90 Baseline Release
```

---

# 6. Master Evidence Chain

## FIG-17-07. Master Evidence Chain

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
GUID / traceId
 ↓
runtimeEvidence
```

---

# 7. Entity Model

## FIG-17-08. Entity Model

```text
Requirement
System
Business
Function
Program
ServiceId
Component
Mapper
SqlId
Table
RuntimePolicy
Artifact
Deployment
Evidence
```

---

# 8. ServiceId Trace

## FIG-17-09. ServiceId Trace

```text
Business
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
Table
```

---

# 9. Reverse Impact Trace

## FIG-17-10. Reverse Impact Trace

```text
Table / SQL
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
 ↑
Requirement
```

---

# 10. Source Conformance

## FIG-17-11. Source Conformance

```text
Architecture Rule
 ↓
Source Scanner
 ↓
PASS / FAIL
 ↓
CI Gate
```

---

# 11. Config Conformance

## FIG-17-12. Config Conformance

```text
Baseline Config
 ↓ compare
application.yml
server.xml
httpd.conf
JVM option
Datasource
 ↓
Drift
```

---

# 12. Runtime Evidence

## FIG-17-13. Runtime Evidence

```text
Rule
 ↓
Runtime Metric / Trace / Failure Test
 ↓
Evidence
 ↓
Gate Result
```

---

# 13. Architecture PASS vs Implementation PASS

## FIG-17-14. Architecture PASS vs Implementation PASS

```text
Architecture Definition
PASS / CONDITIONAL / OPEN / FAIL

≠

Implementation
PASS / PARTIAL / GAP / CONFLICT / OPEN / UNKNOWN
```

---

# 14. Decision Register

## FIG-17-15. Decision Register

```text
Decision Task
 ↓
주안 / 대안
 ↓
Evidence
 ↓
ADR
 ↓
Rule / Baseline
```

---

# 15. Current Decision Snapshot

## FIG-17-16. Current Decision Snapshot

```text
40 Decision Tasks
├─ PASS 23
├─ CONDITIONAL PASS 16
├─ OPEN 1
└─ FAIL 0

[WORKING REGISTER]
```

---

# 16. Critical Drift Examples

## FIG-17-17. Critical Drift Examples

```text
JWT
RS256 issue
≠ HMAC verify

TCF
ON Facade
≠ OFF Service direct

Catalog
UI
≠ Backend registry

Deployment
Architecture node
≠ actual host mapping
```

---

# 17. Baseline Release Criteria

## FIG-17-18. Baseline Release Criteria

```text
Critical Rule PASS
+ Critical GAP closed/ADR
+ Runtime Evidence
+ Deployment Trace
+ Security Test
+ Performance/DR Evidence
 ↓
HG90
```

---

# 18. Architecture Dashboard

## FIG-17-19. Architecture Dashboard

```text
Domain
 ↓
Rule PASS%
 ↓
Current Conformance
 ↓
Critical GAP
 ↓
Evidence Coverage
 ↓
Owner / Gate
```

---

# 19. Architecture Rule Catalog

## FIG-17-20. Rule Set

```text
R-TR-01
모든 Critical Architecture Object는 machine-readable ID를 가진다.

R-TR-02
ServiceId를 Source/Data/Runtime 공통 추적축으로 사용한다.

R-TR-03
Architecture PASS와 Implementation PASS를 분리한다.

R-TR-04
Logging만으로 Runtime Evidence PASS를 선언하지 않는다.

R-TR-05
SourceCommit→ArtifactHash→DeploymentId를 유지한다.

R-TR-06
Critical Rule은 CI/Runtime Gate에서 검증한다.

R-TR-07
Drift는 GAP 또는 ADR로 닫는다.

R-TR-08
UNKNOWN/OPEN을 숨기지 않는다.

R-TR-09
승인된 ADR은 Model/Rule/Standard에 반영한다.

R-TR-10
HG90는 Evidence-backed Baseline Release다.
```

| Rule | 정의 |
|---|---|
| R-TR-01 | 모든 Critical Architecture Object는 machine-readable ID를 가진다. |
| R-TR-02 | ServiceId를 Source/Data/Runtime 공통 추적축으로 사용한다. |
| R-TR-03 | Architecture PASS와 Implementation PASS를 분리한다. |
| R-TR-04 | Logging만으로 Runtime Evidence PASS를 선언하지 않는다. |
| R-TR-05 | SourceCommit→ArtifactHash→DeploymentId를 유지한다. |
| R-TR-06 | Critical Rule은 CI/Runtime Gate에서 검증한다. |
| R-TR-07 | Drift는 GAP 또는 ADR로 닫는다. |
| R-TR-08 | UNKNOWN/OPEN을 숨기지 않는다. |
| R-TR-09 | 승인된 ADR은 Model/Rule/Standard에 반영한다. |
| R-TR-10 | HG90는 Evidence-backed Baseline Release다. |

---

# 20. Verification / Test

## FIG-17-21. Verification Flow

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
| T-TR-01 | serviceId source trace |
| T-TR-02 | reverse table impact trace |
| T-TR-03 | rule scanner |
| T-TR-04 | config drift |
| T-TR-05 | artifact/deployment trace |
| T-TR-06 | runtime evidence link |
| T-TR-07 | decision/ADR closure |
| T-TR-08 | HG90 release checklist |

---

# 21. GAP Register

## FIG-17-22. GAP Lifecycle

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
| GAP-TR-01 | Architecture model automation completeness | High | entity/relation registry |
| GAP-TR-02 | Source scanner CI enforcement | High | pipeline |
| GAP-TR-03 | Deployment runtime correlation | Critical | deployment manifest |
| GAP-TR-04 | Runtime evidence collector | Critical | metrics/trace/test link |
| GAP-TR-05 | UI/backend registry drift automation | High | catalog diff |
| GAP-TR-06 | Critical decision closure | Critical | ADR/gate |

---

# 22. Risk Register

## FIG-17-23. Risk Propagation

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
| RISK-TR-01 | document-only baseline | actual drift hidden |
| RISK-TR-02 | logging called evidence | false confidence |
| RISK-TR-03 | manual registry | staleness |
| RISK-TR-04 | unclosed ADR | ambiguous implementation |
| RISK-TR-05 | missing reverse trace | impact analysis failure |

---

# 23. Architecture Decision / ADR

## FIG-17-24. Decision Flow

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
| ADR-TASK-001 | Architecture SSOT | model+registry+gate |
| ADR-TASK-002 | Code Registry | central+CI |
| ADR-TASK-003 | PDMG→NSIGHT mapping | explicit |
| ADR-TASK-040 | Runtime Evidence Gate | critical rules first |

---

# 24. Architecture PASS / PDMG Conformance

## FIG-17-25. PASS Model

```text
Architecture Definition
 ↓
PASS

Current Closed-loop Conformance
 ↓
PARTIAL / CONDITIONAL

Model/rules defined;
runtime evidence/deployment automation remains the major gap
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| ServiceId trace | PASS/PARTIAL | strong source |
| Decision register | PASS | 40 tasks working register |
| Source conformance | CONDITIONAL | automation needed |
| Deployment trace | GAP | manifest/host link |
| Runtime evidence | GAP | collector/gate |
| HG90 | CONDITIONAL | critical evidence required |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PARTIAL / CONDITIONAL`  
**Runtime Evidence Coverage:** `MEDIUM`

---

# 25. Next Chapter Handoff

## FIG-17-26. 17 → 18

```text
17 TRACEABILITY / PASS / GAP / ADR
"설계대로 구현됐음을 어떻게 증명하는가?"
     ↓
18 INTEGRATED BASELINE
"앞의 모든 Domain을 하나의 PDMG Architecture Baseline으로 어떻게 묶고 승인하는가?" 
```

---

# 26. PDMG TRACEABILITY / CONFORMANCE / PASS / GAP / ADR ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG TRACEABILITY / CONFORMANCE / PASS / GAP / ADR ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**17장 Architecture Definition 판정: `PASS`**
