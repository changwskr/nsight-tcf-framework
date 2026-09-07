# PDMG 전체 아키텍처 정의서
# 18. PDMG INTEGRATED ARCHITECTURE BASELINE
## Application / Technical / Physical / Interface / Data / Runtime / Security / Operations / Traceability
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-18-INTEGRATED-ARCHITECTURE-BASELINE`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-18-01. 이 장의 핵심 질문

```text
앞의 모든 Architecture를 하나의 PDMG Baseline으로 어떻게 통합하는가?
현재 강하게 확인된 AS-IS와 Target Alignment, Critical GAP를 한 눈에 어떻게 보여주는가?
어떤 조건을 충족해야 HG90 Final PASS가 되는가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-18-02. Evidence Flow

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
| EV-18-01 | Chapters 01-17 | domain architecture | [WORKING BASELINE] |
| EV-18-02 | 00 Master Index | writing/evidence policy | [DECISION] |
| EV-18-03 | Decision PASS Register | architecture decision status | [WORKING REGISTER] |
| EV-18-04 | NSIGHT Target references | alignment | [TARGET REFERENCE] |
| EV-18-05 | PDMG source/runtime analyses | current facts | [AS-IS] |

---

# 2. Figure Plan

## FIG-18-03. Top-down Drill-down

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

# 3. L0 — PDMG Master Architecture Baseline

## FIG-18-04. L0 — PDMG Master Architecture Baseline

```text
User / Browser
  ↓
UI Delivery
  ↓
Authentication / JWT
  ↓
Application Runtime
  ├─ Framework / TCF
  ├─ Worker / Transaction
  └─ Business
       ↓
   DAO / Mapper
       ↓
   RDW / DB

External Integration [contract-based]
Operations / Observability [cross-cutting]
Physical / HA / DR [mapping]
Traceability / Evidence [closed loop]
```

---

# 4. L0~L5 Full Drill-down

## FIG-18-05. L0~L5 Full Drill-down

```text
L0 PDMG Landscape
 ↓
L1 System / Trust / Data Boundary
 ↓
L2 Module / Logical Node
 ↓
L3 Layer / Component
 ↓
L4 Runtime / Failure / Security
 ↓
L5 Source / Config / Evidence
```

---

# 5. Application Overlay

## FIG-18-06. Application Overlay

```text
pdmg-ui
pdmg-jwt
pdmg-service + pdmg-fw
pdmg-om [UNKNOWN]
 ↓
Handler / Controller
 ↓
Facade / Service
 ↓
DAO / Mapper
```

---

# 6. Technical / Physical Overlay

## FIG-18-07. Technical / Physical Overlay

```text
Logical Nodes
UI / Auth / App / Data / Integration / Ops
 ↓
GSLB / L4 / WEB / WAS
 ↓
JVM / WAR
 ↓
DB / Storage
 ↓
Monitoring / Backup / DR
```

---

# 7. Runtime Overlay

## FIG-18-08. Runtime Overlay

```text
Filter
 ↓
Security
 ↓
MVC
 ↓
TCF
 ↓
Worker
 ↓
TX
 ↓
Handler / Facade / Service
 ↓
DB
 ↓
Response / Evidence
```

---

# 8. Security Overlay

## FIG-18-09. Security Overlay

```text
Login / SSO
 ↓
RS256 issue
 ↓
JWKS
 ↓
Business verify [Current GAP]
 ↓
Principal
 ↓
Authorization
 ↓
Audit
```

---

# 9. Data / Interface Overlay

## FIG-18-10. Data / Interface Overlay

```text
ServiceId
 ↓
Business
 ↓
DAO / Mapper / SQL
 ↓
RDW

External Need
 ↓
InterfaceId
 ↓
API / Event / CDC / ETL / File
```

---

# 10. Operations Overlay

## FIG-18-11. Operations Overlay

```text
Source
 ↓
Build / Artifact
 ↓
Deployment
 ↓
Runtime
 ↓
Metric / Log / Trace
 ↓
Alert / Runbook
 ↓
Evidence / Drift
```

---

# 11. Naming / Trace Overlay

## FIG-18-12. Naming / Trace Overlay

```text
Business
 ↓
Program
 ↓
ServiceId
 ↓
Component
 ↓
SqlId / Table
 ↓
Artifact
 ↓
Deployment
 ↓
GUID / Evidence
```

---

# 12. Current AS-IS Strong Facts

## FIG-18-13. Current AS-IS Strong Facts

```text
Confirmed
├─ pdmg-ui/jwt/fw/service
├─ nhnis.mg.co.a
├─ rdw.mg.co.a
├─ 13 ServiceIds
├─ Filter/Security/MVC/TCF
├─ Worker20/Queue100/5000ms
├─ TransactionTemplate
├─ Hikari/MyBatis/JDBC
├─ hdr_nhnis/GUID
└─ RS256 issue + HMAC verify conflict
```

---

# 13. Target Alignment

## FIG-18-14. Target Alignment

```text
NSIGHT Target
├─ Standard Interface
├─ RDW/ADW separation
├─ Event/CDC/ETL/File
├─ WEB/WAS scale-out
├─ HA/DR
├─ Security key/JWKS
├─ Observability
└─ Evidence closed loop
       ↓ compare
PDMG Current
       ↓
PASS / GAP / ADR
```

---

# 14. Critical GAP Heatmap

## FIG-18-15. Critical GAP Heatmap

```text
CRITICAL
├─ JWT issuer/verifier/key
├─ Identity binding
├─ Deployment→Host/JVM/WAR
└─ Runtime evidence automation

HIGH
├─ TCF OFF facade drift
├─ Worker mutable context
├─ Query timeout/cancel evidence
├─ Interface inventory
├─ RDW/ADW actual mapping
├─ pdmg-om scope
└─ Capacity/DR approval
```

---

# 15. Chapter PASS Map

## FIG-18-16. Chapter PASS Map

```text
01 Executive        CONDITIONAL PASS
02 Boundary         PASS
03 Application      PASS
04 Logical          PASS
05 Physical         PASS / Current conditional
06 Interface        PASS / Current partial
07 Data             PASS / Current partial
08 Framework        PASS / Current gap
09 Runtime          PASS / Current conditional
10 TX/Timeout       PASS / Current conditional
11 Security         PASS / Current critical gap
12 Message/Error    PASS / Current gap
13 Non-online       CONDITIONAL PASS
14 DevOps/Ops       PASS / Current open
15 Naming           PASS
16 Capacity/HA/DR   PASS / Current open
17 Traceability     PASS / Current conditional
```

---

# 16. Baseline Release Gate

## FIG-18-17. Baseline Release Gate

```text
Architecture Definition
 ↓
Critical ADR Closed
 ↓
Source / Config Conformance
 ↓
Security Integration PASS
 ↓
Performance / Failure PASS
 ↓
Deployment Trace PASS
 ↓
Runtime Evidence PASS
 ↓
DR / Restore PASS
 ↓
G80 Approval
 ↓
HG90 PDMG Baseline
```

---

# 17. Future Change Closed Loop

## FIG-18-18. Future Change Closed Loop

```text
Requirement Change
 ↓
Architecture Impact
 ↓
Model / ADR
 ↓
Source / Test
 ↓
Deploy
 ↓
Runtime Evidence
 ↓
Drift?
 ├─ NO → maintain baseline
 └─ YES → GAP / ADR / new baseline
```

---

# 18. Architecture Rule Catalog

## FIG-18-19. Rule Set

```text
R-BL-01
PDMG Current와 NSIGHT Target을 분리한다.

R-BL-02
모든 주요 Architecture Domain은 TEXT diagram과 evidence를 가진다.

R-BL-03
Critical GAP는 ADR 또는 구현/Evidence로 닫는다.

R-BL-04
Architecture PASS와 Implementation PASS를 분리한다.

R-BL-05
Unknown/Open 값을 숨기거나 추정하지 않는다.

R-BL-06
ServiceId를 Source/Data/Runtime trace의 핵심축으로 사용한다.

R-BL-07
Artifact/Deployment/Runtime evidence를 Baseline에 연결한다.

R-BL-08
Security/Performance/DR는 runtime test evidence가 없으면 최종 PASS가 아니다.

R-BL-09
Baseline 변경은 ADR/Version으로 관리한다.

R-BL-10
HG90만 공식 Release Baseline으로 본다.
```

| Rule | 정의 |
|---|---|
| R-BL-01 | PDMG Current와 NSIGHT Target을 분리한다. |
| R-BL-02 | 모든 주요 Architecture Domain은 TEXT diagram과 evidence를 가진다. |
| R-BL-03 | Critical GAP는 ADR 또는 구현/Evidence로 닫는다. |
| R-BL-04 | Architecture PASS와 Implementation PASS를 분리한다. |
| R-BL-05 | Unknown/Open 값을 숨기거나 추정하지 않는다. |
| R-BL-06 | ServiceId를 Source/Data/Runtime trace의 핵심축으로 사용한다. |
| R-BL-07 | Artifact/Deployment/Runtime evidence를 Baseline에 연결한다. |
| R-BL-08 | Security/Performance/DR는 runtime test evidence가 없으면 최종 PASS가 아니다. |
| R-BL-09 | Baseline 변경은 ADR/Version으로 관리한다. |
| R-BL-10 | HG90만 공식 Release Baseline으로 본다. |

---

# 19. Verification / Test

## FIG-18-20. Verification Flow

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
| T-BL-01 | all chapter files/figures validation |
| T-BL-02 | critical gap status review |
| T-BL-03 | source/config conformance |
| T-BL-04 | security integration |
| T-BL-05 | load/failure/DR |
| T-BL-06 | deployment evidence |
| T-BL-07 | runtime evidence |
| T-BL-08 | G80 approval/HG90 manifest |

---

# 20. GAP Register

## FIG-18-21. GAP Lifecycle

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
| GAP-BL-01 | JWT critical integration | Critical | 11장 GAP close |
| GAP-BL-02 | Identity binding | Critical | security test |
| GAP-BL-03 | Actual physical/deployment mapping | Critical | 05/14 trace |
| GAP-BL-04 | Runtime evidence automation | Critical | 17 gate |
| GAP-BL-05 | External interface inventory | High | 06 registry |
| GAP-BL-06 | RDW/ADW/table ownership mapping | High | 07 registry |
| GAP-BL-07 | pdmg-om scope | High | 14 evidence |
| GAP-BL-08 | capacity/RTO/RPO approval | Critical | 16 test/ADR |

---

# 21. Risk Register

## FIG-18-22. Risk Propagation

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
| RISK-BL-01 | 문서 PASS만으로 완료 선언 | runtime mismatch |
| RISK-BL-02 | PDMG AS-IS를 Target으로 자동승격 | technical debt lock-in |
| RISK-BL-03 | Target을 Current로 표현 | false architecture |
| RISK-BL-04 | critical gaps open at go-live | security/availability failure |
| RISK-BL-05 | evidence chain break | audit/impact analysis failure |

---

# 22. Architecture Decision / ADR

## FIG-18-23. Decision Flow

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
| ADR-TASK-003 | PDMG→NSIGHT Mapping | explicit |
| ADR-TASK-010~013 | Security decisions | must close |
| ADR-TASK-026~032 | capacity/HA/DR | test based |
| ADR-TASK-040 | Runtime Evidence | HG90 gate |

---

# 23. Architecture PASS / PDMG Conformance

## FIG-18-24. PASS Model

```text
Architecture Definition
 ↓
CONDITIONAL PASS

Why not final PASS?
Critical implementation/evidence gaps remain

Target end state
HG90 Evidence-backed Baseline PASS
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Architecture coverage | PASS | 01-17 defined |
| Current source coverage | PASS/PARTIAL | major modules/runtime |
| Security | GAP/CRITICAL | issuer/verifier/key/identity |
| Physical/Deployment | GAP | actual mapping |
| Capacity/DR | CONDITIONAL | test/approval |
| Runtime Evidence | GAP | automation/gate |
| Final HG90 | OPEN | conditions not yet closed |

**Architecture Definition:** `CONDITIONAL PASS`  
**Current PDMG Conformance:** `PARTIAL / GAP`  
**Runtime Evidence Coverage:** `MEDIUM`

---

# 24. Next Chapter Handoff

## FIG-18-25. 18 → 19

```text
18 INTEGRATED BASELINE
"전체 PDMG Architecture를 통합하고 승인한다."
     ↓
HG90
Evidence-backed Architecture Baseline Release
```

---

# 25. PDMG INTEGRATED ARCHITECTURE BASELINE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG INTEGRATED ARCHITECTURE BASELINE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**18장 Architecture Definition 판정: `CONDITIONAL PASS`**
