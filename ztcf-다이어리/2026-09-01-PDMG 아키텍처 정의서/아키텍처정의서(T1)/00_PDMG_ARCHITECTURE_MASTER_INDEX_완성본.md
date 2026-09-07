# PDMG 전체 아키텍처 정의서
# 00. FINAL MASTER INDEX
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 상태: `[WORKING INTEGRATED BASELINE-2026-09-01]`  
> 범위: **00 ~ 18 전체 장**  
> 주인공: **PDMG Current Architecture**  
> 상위 정합기준: **NSIGHT Target Architecture**

---

# 1. 전체 Architecture Journey

```text
00 GUIDE / EVIDENCE
 ↓
01 EXECUTIVE
 ↓
02 SYSTEM CONTEXT / BOUNDARY
 ↓
03 APPLICATION / MODULE
 ↓
04 LOGICAL TECHNICAL
 ↓
05 PHYSICAL / INFRASTRUCTURE
 ↓
06 INTERFACE
 ↓
07 DATA
 ↓
08 FRAMEWORK / MECHANISM
 ↓
09 ONLINE RUNTIME
 ↓
10 TX / TIMEOUT / THREAD / DB
 ↓
11 SECURITY / JWT / SESSION
 ↓
12 MESSAGE / CONTEXT / ERROR / LOGGING
 ↓
13 EVENT / CDC / ETL / BATCH / FILE / CACHE
 ↓
14 DEVOPS / OM / OBSERVABILITY
 ↓
15 NAMING / CODE / DEVELOPMENT STANDARD
 ↓
16 CAPACITY / PERFORMANCE / HA / DR
 ↓
17 TRACEABILITY / PASS / GAP / ADR
 ↓
18 INTEGRATED PDMG BASELINE
 ↓
HG90 Evidence-backed Baseline Release
```

---

# 2. 장별 완료상태

| 장 | 제목 | 파일 | Architecture | Lines | Figures |
|---:|---|---|---|---:|---:|
| 00 | MASTER INDEX / DEFINITION GUIDE | `00_PDMG_ARCHITECTURE_MASTER_INDEX.md` | PASS | 908 | 21 |
| 01 | EXECUTIVE ARCHITECTURE OVERVIEW | `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md` | CONDITIONAL PASS | 1,607 | 36 |
| 02 | SYSTEM CONTEXT & BOUNDARY | `02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md` | PASS | 2,018 | 44 |
| 03 | APPLICATION / MODULE | `03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md` | PASS | 2,408 | 61 |
| 04 | LOGICAL TECHNICAL | `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md` | PASS | 2,616 | 65 |
| 05 | PHYSICAL / INFRASTRUCTURE | `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md` | PASS | 657 | 26 |
| 06 | INTERFACE | `06_PDMG_INTERFACE_상세정의서_v1.md` | PASS | 628 | 28 |
| 07 | DATA | `07_PDMG_DATA_상세정의서_v1.md` | PASS | 582 | 26 |
| 08 | FRAMEWORK / MECHANISM | `08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md` | PASS | 585 | 26 |
| 09 | ONLINE RUNTIME | `09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md` | PASS | 620 | 26 |
| 10 | TRANSACTION / TIMEOUT / THREAD / DB | `10_PDMG_TRANSACTION_TIMEOUT_THREAD_DB_상세정의서_v1.md` | PASS | 552 | 26 |
| 11 | SECURITY / SSO / JWT / SESSION | `11_PDMG_SECURITY_SSO_JWT_SESSION_상세정의서_v1.md` | PASS | 589 | 27 |
| 12 | MESSAGE / CONTEXT / ERROR / LOGGING | `12_PDMG_MESSAGE_CONTEXT_ERROR_LOGGING_상세정의서_v1.md` | PASS | 590 | 26 |
| 13 | EVENT / CDC / ETL / BATCH / FILE / CACHE | `13_PDMG_EVENT_CDC_ETL_BATCH_FILE_CACHE_상세정의서_v1.md` | CONDITIONAL PASS | 560 | 25 |
| 14 | DEVOPS / DEPLOYMENT / OM / OBSERVABILITY | `14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md` | PASS | 617 | 27 |
| 15 | NAMING / APPLICATION CODE / DEVELOPMENT STANDARD | `15_PDMG_NAMING_CODE_DEVELOPMENT_STANDARD_상세정의서_v1.md` | PASS | 613 | 29 |
| 16 | CAPACITY / PERFORMANCE / HA / DR | `16_PDMG_CAPACITY_PERFORMANCE_HA_DR_상세정의서_v1.md` | PASS | 655 | 29 |
| 17 | TRACEABILITY / CONFORMANCE / PASS / GAP / ADR | `17_PDMG_TRACEABILITY_PASS_GAP_ADR_상세정의서_v1.md` | PASS | 630 | 27 |
| 18 | INTEGRATED ARCHITECTURE BASELINE | `18_PDMG_INTEGRATED_ARCHITECTURE_BASELINE_상세정의서_v1.md` | CONDITIONAL PASS | 645 | 26 |

---

# 3. PDMG 전체 Baseline — 한 장 요약

```text
User / Browser
      ↓
UI Delivery / pdmg-ui
      ↓
Authentication / pdmg-jwt
      ↓
Application Runtime
pdmg-service + pdmg-fw
      │
      ├─ Filter / Context / Security
      ├─ TCF / Dispatcher / Handler
      ├─ Worker / Transaction / Timeout
      ├─ Facade / Service
      └─ DAO / Mapper
              ↓
          RDW / DB

External Interaction
→ Approved Interface Contract

Physical Working Path
→ GSLB → L4 → Apache → Tomcat/JVM → WAR → DB

Cross-cutting
→ Security / Observability / DevOps / HA-DR / Traceability
```

---

# 4. 현재 강하게 확인된 PDMG AS-IS

```text
pdmg-ui
pdmg-jwt
pdmg-fw
pdmg-service
pdmg-om = Current Detail UNKNOWN

Java 21
Spring Boot 3.5.14
Gradle Multi-project

Business Root
nhnis.mg.co.a

Mapper Root
rdw.mg.co.a

Handler Registry
13 ServiceIds

Online Runtime
Filter → Security → MVC → TCF → Worker → TX → Handler → Facade → Service → DAO → DB

Timeout Snapshot
5000ms / Worker 20 / Queue 100

Message
{ hdr_nhnis, dto }
Success
{ hdr_nhnis, dto }
Known Error
{ hdr_nhnis, result }

Trace
GUID + ServiceId

Security Current
RS256 Issue
vs
HMAC Verify Path
= CRITICAL GAP
```

---

# 5. 전체 Critical GAP

```text
CRITICAL
├─ JWT RS256 issuer ↔ HMAC verifier mismatch
├─ JWT key lifecycle / multi-instance consistency
├─ Trusted Principal ↔ Business User identity binding
├─ Logical Node / Artifact → Host/JVM/WAR mapping
├─ Runtime Evidence automation / HG90 Gate
└─ Capacity / RTO / RPO final evidence

HIGH
├─ TCF OFF Controller → Service Direct
├─ Mutable Worker ServiceContext
├─ Filter/Security early error contract
├─ JDBC query timeout / cancel evidence
├─ External Interface Inventory
├─ RDW/ADW actual datasource/table ownership
├─ pdmg-om current scope
└─ UI Catalog ↔ Backend ServiceId Registry drift automation
```

---

# 6. 최종 판정원칙

```text
Architecture Definition PASS
        ≠
Current Implementation PASS
        ≠
Project Final PASS
```

Project Final PASS는 다음이 필요하다.

```text
Architecture Rule PASS
+
Source / Config Conformance
+
Security Integration PASS
+
Performance / Failure PASS
+
Deployment Mapping
+
Runtime Evidence
+
DR / Restore Evidence
+
Critical ADR Closure
+
G80 Approval
      ↓
HG90
```

---

# 7. 현재 전체 판정

**PDMG Architecture Definition:** `CONDITIONAL PASS`  
**Current PDMG Implementation Conformance:** `PARTIAL / GAP`  
**Runtime Evidence Coverage:** `MEDIUM`  
**Final HG90 Baseline:** `OPEN`

Architecture의 구조 자체는 00~18장으로 정의되었으나, Current PDMG의 Critical Security/Deployment/Runtime Evidence/Capacity·DR 항목이 아직 닫히지 않았으므로 최종 Project PASS로 승격하지 않는다.
