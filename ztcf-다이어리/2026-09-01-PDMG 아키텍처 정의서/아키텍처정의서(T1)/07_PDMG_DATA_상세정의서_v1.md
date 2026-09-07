# PDMG 전체 아키텍처 정의서
# 07. PDMG DATA ARCHITECTURE
## Ownership / DAO-Mapper-SQL / RDW-ADW / Lineage / Quality / Security
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-07-DATA`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-07-01. 이 장의 핵심 질문

```text
PDMG는 어떤 데이터를 읽고 변경하며, 어떤 Data Boundary를 가져야 하는가?
ServiceId가 DAO/Mapper/SQL/Table로 어떻게 추적되는가?
RDW/ADW, CDC/ETL, Ownership/Quality/Security를 어떻게 정합시킬 것인가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-07-02. Evidence Flow

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
| EV-07-01 | 03 Application | DAO/Mapper/ServiceId | [WORKING BASELINE] |
| EV-07-02 | Transaction/DB analysis | DataSource/TX | [AS-IS] |
| EV-07-03 | Data Appendix E | Ownership/Model/Flow/DQ | [TARGET REFERENCE] |
| EV-07-04 | Interface Guide | Direct DB restrictions | [TARGET BASELINE] |
| EV-07-05 | NSIGHT Data Platform | RDW/ADW/CDC/ETL roles | [TARGET REFERENCE] |

---

# 2. Figure Plan

## FIG-07-03. Top-down Drill-down

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

# 3. L0 — PDMG Data Master

## FIG-07-04. L0 — PDMG Data Master

```text
Business Meaning
   ↓
ServiceId
   ↓
Service / DAO
   ↓
Mapper / SqlId
   ↓
SQL
   ↓
Table / View
   ↓
RDW / DB
   ↓
Lineage / Evidence
```

---

# 4. PDMG Current Data Access

## FIG-07-05. PDMG Current Data Access

```text
Handler
 ↓
Facade
 ↓
Service
 ↓
DAO
 ↓
MyBatis Mapper
 ↓
JDBC
 ↓
RDW / DB
```

---

# 5. Data Architecture vs Database Architecture

## FIG-07-06. Data Architecture vs Database Architecture

```text
Data Architecture
├─ Domain / Subject
├─ Ownership
├─ Model
├─ Flow
├─ Quality
├─ Security
├─ Lifecycle
└─ Evidence

Database Architecture
= Data Architecture의 하위 구현영역
```

---

# 6. Data Ownership Boundary

## FIG-07-07. Data Ownership Boundary

```text
Business Data
 ↓
Owner / Steward
 ↓
SOR / Master
 ↓
Approved Consumer
 ↓
PDMG Access
```

---

# 7. RDW / ADW Role

## FIG-07-08. RDW / ADW Role

```text
RDW
= Operational / Near-real-time / Information Service

ADW
= Analytical / Aggregation / Mart / Heavy Query

PDMG Current → RDW stronger evidence
PDMG Current → ADW [OPEN]
```

---

# 8. Datasource / Transaction Alignment

## FIG-07-09. Datasource / Transaction Alignment

```text
TransactionManager
   ↓
DataSource
   ↓
Hikari
   ↓
DAO / Mapper
   ↓
DB

TX Manager ↔ DataSource
must align
```

---

# 9. Mapper / SqlId Trace

## FIG-07-10. Mapper / SqlId Trace

```text
ServiceId
 ↓
DAO
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

# 10. Read / Write Boundary

## FIG-07-11. Read / Write Boundary

```text
Own / Approved Data
  ├─ READ
  └─ WRITE according to ownership

Other System Data
  ↓
Approved Interface / Data Contract

Cross-system direct DML
= Forbidden
```

---

# 11. Data Lineage

## FIG-07-12. Data Lineage

```text
Source
 ↓
Transform / SQL / ETL
 ↓
Target
 ↓
Consumer

Reverse:
Table → SqlId → DAO → ServiceId → Application
```

---

# 12. CDC / ETL Relation

## FIG-07-13. CDC / ETL Relation

```text
Source Data
 ├─ Change stream → CDC → RDW
 └─ Bulk extract  → ETL → ADW

PDMG Current direct ownership
= [REFERENCE / OPEN]
```

---

# 13. Metadata

## FIG-07-14. Metadata

```text
Business Metadata
+ Technical Metadata
+ Operational Metadata
   ↓
Catalog / Lineage
```

---

# 14. Data Quality

## FIG-07-15. Data Quality

```text
Definition
 ↓
Validation
 ↓
Load / Update
 ↓
Monitor
 ↓
Issue
 ↓
Remediation
```

---

# 15. Data Security

## FIG-07-16. Data Security

```text
Classification
 ↓
Access Control
 ↓
Masking / Encryption
 ↓
Audit
 ↓
Retention / Disposal
```

---

# 16. Performance / Workload Isolation

## FIG-07-17. Performance / Workload Isolation

```text
Online Query
 ↓
RDW

Heavy Analytics
 ↓
ADW

Cross impact
= minimize / isolate
```

---

# 17. Data Evidence

## FIG-07-18. Data Evidence

```text
ServiceId
 ↓
SqlId
 ↓
Table
 ↓
Elapsed / Rows / Error
 ↓
GUID
 ↓
Runtime Evidence
```

---

# 18. Architecture Rule Catalog

## FIG-07-19. Rule Set

```text
R-DA-01
Business concept→Logical Entity→Physical Object로 추적한다.

R-DA-02
Data Owner/Steward/SOR를 명시한다.

R-DA-03
Cross-system direct DML을 금지한다.

R-DA-04
ServiceId→DAO→Mapper→SqlId→Table 추적을 유지한다.

R-DA-05
RDW와 ADW의 Workload 목적을 분리한다.

R-DA-06
TX Manager와 DataSource를 정렬한다.

R-DA-07
Metadata/Lineage를 변경영향 분석에 사용한다.

R-DA-08
Critical Data에 Quality Rule을 둔다.

R-DA-09
Security classification과 lifecycle을 정의한다.

R-DA-10
PDMG Current에서 미확인 Data Flow를 창작하지 않는다.
```

| Rule | 정의 |
|---|---|
| R-DA-01 | Business concept→Logical Entity→Physical Object로 추적한다. |
| R-DA-02 | Data Owner/Steward/SOR를 명시한다. |
| R-DA-03 | Cross-system direct DML을 금지한다. |
| R-DA-04 | ServiceId→DAO→Mapper→SqlId→Table 추적을 유지한다. |
| R-DA-05 | RDW와 ADW의 Workload 목적을 분리한다. |
| R-DA-06 | TX Manager와 DataSource를 정렬한다. |
| R-DA-07 | Metadata/Lineage를 변경영향 분석에 사용한다. |
| R-DA-08 | Critical Data에 Quality Rule을 둔다. |
| R-DA-09 | Security classification과 lifecycle을 정의한다. |
| R-DA-10 | PDMG Current에서 미확인 Data Flow를 창작하지 않는다. |

---

# 19. Verification / Test

## FIG-07-20. Verification Flow

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
| T-DA-01 | Mapper→SqlId→Table extraction |
| T-DA-02 | TX Manager/DataSource alignment |
| T-DA-03 | Cross-system DML scan |
| T-DA-04 | RDW/ADW workload test |
| T-DA-05 | Data quality rules |
| T-DA-06 | Access/masking/audit |

---

# 20. GAP Register

## FIG-07-21. GAP Lifecycle

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
| GAP-DA-01 | 실제 Table/View 전수 Inventory 미완료 | High | Mapper/SQL scan |
| GAP-DA-02 | RDW/ADW Datasource 사용현황 미확정 | High | Config/Mapper inventory |
| GAP-DA-03 | Data Owner/Steward/SOR 전수 미확정 | High | Data Registry |
| GAP-DA-04 | Lineage 자동화 미완료 | High | Metadata scanner |
| GAP-DA-05 | DQ Rule/Runtime evidence 미완료 | Medium/High | DQ catalog/test |

---

# 21. Risk Register

## FIG-07-22. Risk Propagation

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
| RISK-DA-01 | RDW에 Heavy Query | Online SLA 침해 |
| RISK-DA-02 | Cross-system DML | 강결합/정합성 위험 |
| RISK-DA-03 | Mapper/SQL Trace 부재 | 영향분석 실패 |
| RISK-DA-04 | Owner 없는 데이터 | 품질/변경 책임 불명 |
| RISK-DA-05 | 민감정보 분류 부재 | 보안/감사 위험 |

---

# 22. Architecture Decision / ADR

## FIG-07-23. Decision Flow

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
| ADR-TASK-021 | RDW/ADW 역할분리 | 분리 주안 |
| ADR-TASK-022 | CDC Freshness SLA | Tiered SLA |
| ADR-TASK-023 | Data Ownership | Subject Registry |
| ADR-TASK-024 | Metadata/Lineage/DQ | 자동수집 주안 |
| ADR-TASK-025 | Heavy Query Isolation | ADW 격리 |

---

# 23. Architecture PASS / PDMG Conformance

## FIG-07-24. PASS Model

```text
Architecture Definition
 ↓
PASS

Current Data Conformance
 ↓
PARTIAL / CONDITIONAL

Strong: DAO/MyBatis/JDBC/RDW
Open: ADW/Ownership/Lineage/DQ
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Data access | PASS | DAO→Mapper→JDBC |
| RDW | PASS/PARTIAL | Current evidence |
| ADW | OPEN | actual use inventory |
| Ownership | CONDITIONAL | registry required |
| Lineage | CONDITIONAL | automation required |
| DQ/Security | CONDITIONAL | rules/evidence |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PARTIAL / CONDITIONAL`  
**Runtime Evidence Coverage:** `MEDIUM`

---

# 24. Next Chapter Handoff

## FIG-07-25. 07 → 08

```text
07 DATA
"무엇을 읽고 쓰는가?"
     ↓
08 FRAMEWORK / MECHANISM
"그 업무와 데이터 접근을 공통 Framework가 어떤 Mechanism으로 통제하는가?" 
```

---

# 25. PDMG DATA ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG DATA ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**07장 Architecture Definition 판정: `PASS`**
