# PDMG 전체 아키텍처 정의서
# 15. PDMG NAMING / APPLICATION CODE / DEVELOPMENT STANDARD
## Program / ServiceId / Package / Class / Mapper / Registry / CI Conformance
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-15-NAMING-CODE-DEVELOPMENT-STANDARD`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-15-01. 이 장의 핵심 질문

```text
PDMG의 Business/Application/Program/ServiceId/Source/Mapper/Artifact를 어떤 식별축으로 연결할 것인가?
어떤 Naming은 Current Fact이고 어떤 Enterprise Naming은 아직 OPEN인가?
개발표준을 CI에서 어떻게 자동검증할 것인가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-15-02. Evidence Flow

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
| EV-15-01 | X Naming/Traceability | ServiceId/package/registry | [AS-IS] |
| EV-15-02 | Appendix F Naming | enterprise naming framework | [TARGET REFERENCE] |
| EV-15-03 | Application Code Definition | application code structure | [WORKING BASELINE] |
| EV-15-04 | 03 Application | class/package/mapper | [WORKING BASELINE] |
| EV-15-05 | Decision Register | code registry/trace | [DECISION] |

---

# 2. Figure Plan

## FIG-15-03. Top-down Drill-down

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

# 3. L0 — Naming Trace Backbone

## FIG-15-04. L0 — Naming Trace Backbone

```text
Business Meaning
 ↓
Classification Code
 ↓
Program ID
 ↓
ServiceId
 ↓
Package / Class
 ↓
Mapper / SqlId
 ↓
Data
 ↓
Artifact / Deployment
 ↓
Runtime Evidence
```

---

# 4. Program ID

## FIG-15-05. Program ID

```text
mg | co | a | 9001
= 9 chars

2 + 2 + 1 + 4
```

---

# 5. ServiceId

## FIG-15-06. ServiceId

```text
mg | co | a | 9001 | S | 0
= 11 chars

Transaction Type
S/C/U/D/A/R
```

---

# 6. ServiceId Regex

## FIG-15-07. ServiceId Regex

```text
General
^[a-z]{2}[a-z]{2}[a-z][0-9]{4}[SCUDAR][0-9A-Z]$

MG
^mg[a-z]{2}[a-z][0-9]{4}[SCUDAR][0-9A-Z]$
```

---

# 7. Package Naming

## FIG-15-08. Package Naming

```text
Business Axis
MG / CO / A
 ↓
Java
nhnis.mg.co.a

Mapper
rdw.mg.co.a
```

---

# 8. Class Naming

## FIG-15-09. Class Naming

```text
Program Stem
mgcoa9001
 ├─ Handler
 ├─ Controller
 ├─ Facade
 ├─ Service
 ├─ DAO
 └─ DTO by ServiceId
```

---

# 9. Mapper Naming

## FIG-15-10. Mapper Naming

```text
DAO FQCN
 ↔ Mapper namespace

Program
mgcoa9001
 ↔ mgcoa9001-ORA.xml

SqlId
service-oriented suffix
```

---

# 10. Current Registry — 13 ServiceIds

## FIG-15-11. Current Registry — 13 ServiceIds

```text
5530S0
8888S0 / D0
9000S0/C0/U0/D0
9001S0/C0/U0/D0
9100S0
9999S0

TOTAL 13
```

---

# 11. UI Catalog Drift

## FIG-15-12. UI Catalog Drift

```text
UI Transaction Catalog
 ↓ compare
Backend Handler Registry
 ↓
MATCH / DRIFT

UI catalog alone
≠ SSOT
```

---

# 12. Identifier Separation

## FIG-15-13. Identifier Separation

```text
ProgramId
≠ ServiceId
≠ InterfaceId
≠ GUID
≠ SqlId
≠ ArtifactHash
≠ DeploymentId
```

---

# 13. Config Naming

## FIG-15-14. Config Naming

```text
nhnis.fw.*
 ↓
framework configuration namespace

Business config
 ↓
application/domain namespace

Secret
≠ ordinary config
```

---

# 14. Artifact / Deployment Naming

## FIG-15-15. Artifact / Deployment Naming

```text
SourceCommit
 ↓
BuildId
 ↓
ArtifactHash
 ↓
DeploymentId

Exact enterprise syntax
= [OPEN]
```

---

# 15. Infrastructure Naming

## FIG-15-16. Infrastructure Naming

```text
Logical Node
 ↓
Environment
 ↓
Host / VM / JVM

Exact hostname convention
= [OPEN / enterprise standard]
```

---

# 16. Registry Governance

## FIG-15-17. Registry Governance

```text
Business Code Registry
Program Registry
ServiceId Registry
Interface Registry
Data Object Registry
Artifact Registry
Deployment Registry
Infrastructure Registry
```

---

# 17. Static Naming Scan

## FIG-15-18. Static Naming Scan

```text
Source
 ↓
Program parser
 ↓
Package parser
 ↓
Class parser
 ↓
ServiceId parser
 ↓
Mapper parser
 ↓
PASS / FAIL
```

---

# 18. Development Dependency Standard

## FIG-15-19. Development Dependency Standard

```text
Handler / Controller
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

Bypass = FAIL
```

---

# 19. Naming Change Lifecycle

## FIG-15-20. Naming Change Lifecycle

```text
Old ID
 ↓ deprecated
New ID
 ↓ mapping / migration
Cutover
 ↓
Retire
```

---

# 20. Open Enterprise Naming Areas

## FIG-15-21. Open Enterprise Naming Areas

```text
InterfaceId exact format
Event/topic naming
File naming
Batch JobId
Artifact/DeploymentId syntax
Hostname/JVM final convention
Metric semantic fields
= [OPEN]
```

---

# 21. Architecture Rule Catalog

## FIG-15-22. Rule Set

```text
R-NAM-01
Canonical identifier는 하나의 의미만 가진다.

R-NAM-02
ServiceId는 exact key로 사용하고 자동 normalization하지 않는다.

R-NAM-03
Program/Package/Mapper 업무축을 정렬한다.

R-NAM-04
Duplicate ServiceId를 금지한다.

R-NAM-05
Handler registry와 handle branch를 정합한다.

R-NAM-06
DAO와 Mapper namespace를 정합한다.

R-NAM-07
ServiceId≠InterfaceId≠GUID를 유지한다.

R-NAM-08
Display name 변경과 canonical ID 변경을 분리한다.

R-NAM-09
미확정 enterprise naming을 임의 생성하지 않는다.

R-NAM-10
Naming rule은 CI에서 기계검증 가능해야 한다.
```

| Rule | 정의 |
|---|---|
| R-NAM-01 | Canonical identifier는 하나의 의미만 가진다. |
| R-NAM-02 | ServiceId는 exact key로 사용하고 자동 normalization하지 않는다. |
| R-NAM-03 | Program/Package/Mapper 업무축을 정렬한다. |
| R-NAM-04 | Duplicate ServiceId를 금지한다. |
| R-NAM-05 | Handler registry와 handle branch를 정합한다. |
| R-NAM-06 | DAO와 Mapper namespace를 정합한다. |
| R-NAM-07 | ServiceId≠InterfaceId≠GUID를 유지한다. |
| R-NAM-08 | Display name 변경과 canonical ID 변경을 분리한다. |
| R-NAM-09 | 미확정 enterprise naming을 임의 생성하지 않는다. |
| R-NAM-10 | Naming rule은 CI에서 기계검증 가능해야 한다. |

---

# 22. Verification / Test

## FIG-15-23. Verification Flow

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
| T-NAM-01 | Program regex/approved classification |
| T-NAM-02 | ServiceId unique |
| T-NAM-03 | registry/branch consistency |
| T-NAM-04 | package/class suffix |
| T-NAM-05 | mapper path/namespace/sqlid |
| T-NAM-06 | UI/backend catalog diff |
| T-NAM-07 | artifact/deployment identity |

---

# 23. GAP Register

## FIG-15-24. GAP Lifecycle

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
| GAP-NAM-01 | UI catalog vs backend 13 ServiceIds | High | automated diff |
| GAP-NAM-02 | InterfaceId exact enterprise syntax | Medium | ADR/registry |
| GAP-NAM-03 | Event/File/Batch naming | Medium | platform standard |
| GAP-NAM-04 | Artifact/DeploymentId exact syntax | High | DevOps standard |
| GAP-NAM-05 | Hostname/JVM convention | Medium | Infra standard |
| GAP-NAM-06 | Naming scanner CI enforcement | High | pipeline gate |

---

# 24. Risk Register

## FIG-15-25. Risk Propagation

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
| RISK-NAM-01 | duplicate ServiceId | startup/runtime failure |
| RISK-NAM-02 | package/business drift | traceability loss |
| RISK-NAM-03 | identifier alias normalization | wrong routing |
| RISK-NAM-04 | registry divergence | UI/backend mismatch |
| RISK-NAM-05 | unstable deployment naming | evidence chain break |

---

# 25. Architecture Decision / ADR

## FIG-15-26. Decision Flow

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
| ADR-TASK-002 | Code Registry SSOT | central registry + CI |
| ADR-TASK-003 | PDMG→NSIGHT Mapping | explicit mapping |
| ADR-TASK-040 | Runtime evidence IDs | baseline/model/artifact/deployment chain |

---

# 26. Architecture PASS / PDMG Conformance

## FIG-15-27. PASS Model

```text
Architecture Definition
 ↓
PASS

Current Naming Conformance
 ↓
PASS / PARTIAL

Strong: Program/ServiceId/package/mapper
Open: enterprise interface/artifact/host naming and CI enforcement
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Program/ServiceId | PASS | strong source |
| Package/Mapper | PASS/PARTIAL | strong pattern |
| Registry | PARTIAL | UI drift |
| Interface/Event naming | OPEN | enterprise standard |
| Artifact/Deployment naming | OPEN | DevOps standard |
| CI enforcement | GAP | scanner/gate needed |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PASS / PARTIAL`  
**Runtime Evidence Coverage:** `HIGH-MEDIUM`

---

# 27. Next Chapter Handoff

## FIG-15-28. 15 → 16

```text
15 NAMING / DEVELOPMENT STANDARD
"어떤 식별과 개발규칙을 따르는가?"
     ↓
16 CAPACITY / PERFORMANCE / HA / DR
"그 구조가 목표 부하와 장애/센터 재해를 견딜 수 있는가?" 
```

---

# 28. PDMG NAMING / APPLICATION CODE / DEVELOPMENT STANDARD 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG NAMING / APPLICATION CODE / DEVELOPMENT STANDARD
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**15장 Architecture Definition 판정: `PASS`**
