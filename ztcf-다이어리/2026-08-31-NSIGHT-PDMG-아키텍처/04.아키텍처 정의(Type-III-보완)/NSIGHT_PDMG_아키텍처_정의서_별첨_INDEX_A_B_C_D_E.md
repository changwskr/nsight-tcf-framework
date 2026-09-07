# NSIGHT / PDMG 아키텍처 정의서 — 별첨 INDEX
## A Application / B Technical / C Infrastructure / D Interface / E Data

---

# 별첨 A. APPLICATION ARCHITECTURE

```text
Business Responsibility
  ↓
Application Boundary
  ↓
Program / ServiceId
  ↓
Layer / Component
  ↓
Runtime / Deployment / Evidence
```

파일: `NSIGHT_PDMG_아키텍처_정의서_별첨_A_APPLICATION_ARCHITECTURE_DEFINITION_VISUAL_FIRST.md`

---

# 별첨 B. TECHNICAL ARCHITECTURE

```text
Application / Data Need
  ↓
Technical Capability
  ↓
Logical Technical Node
  ↓
Runtime Platform
  ↓
Technical Standard / TRM / Evidence
```

파일: `NSIGHT_PDMG_아키텍처_정의서_별첨_B_TECHNICAL_ARCHITECTURE_DEFINITION_VISUAL_FIRST.md`

---

# 별첨 C. INFRASTRUCTURE ARCHITECTURE

```text
Logical Technical Node
  ↓
Center / Environment
  ↓
Compute / Network / Storage
  ↓
WEB / WAS / JVM / DB
  ↓
Capacity / HA / DR / Inventory / Evidence
```

파일: `NSIGHT_PDMG_아키텍처_정의서_별첨_C_INFRASTRUCTURE_ARCHITECTURE_DEFINITION_VISUAL_FIRST.md`

---

# 별첨 D. INTERFACE ARCHITECTURE

```text
Business Interaction Need
  ↓
Interface Type
  ↓
Contract / Version
  ↓
SYNC / ASYNC
  ↓
Timeout / Retry / Idempotency
  ↓
Security / Recovery / Evidence
```

파일: `NSIGHT_PDMG_아키텍처_정의서_별첨_D_INTERFACE_ARCHITECTURE_DEFINITION_VISUAL_FIRST.md`

---

# 별첨 E. DATA ARCHITECTURE

```text
Business Meaning
  ↓
Data Domain / Subject Area
  ↓
Ownership / Stewardship
  ↓
Logical / Physical Data Model
  ↓
RDW / ADW
  ↓
CDC / ETL / Data Flow
  ↓
Metadata / Lineage / Quality
  ↓
Security / Lifecycle / Evidence
```

파일: `NSIGHT_PDMG_아키텍처_정의서_별첨_E_DATA_ARCHITECTURE_DEFINITION_VISUAL_FIRST.md`

---

# A / B / C / D / E 관계

```text
                 APPLICATION ARCHITECTURE
              Application / Program / ServiceId
                         │
             ┌───────────┼───────────┐
             ▼           ▼           ▼
       TECHNICAL     INTERFACE      DATA
      ARCHITECTURE  ARCHITECTURE  ARCHITECTURE
       기술구조       연결계약        데이터구조
             │           │           │
             └──────┬────┴────┬──────┘
                    ▼         ▼
           INFRASTRUCTURE   OPERATIONS
            물리/가상 자원    Runtime Evidence
```

---

# 핵심 구분

```text
A Application
= Business Responsibility / Application Boundary

B Technical
= Technical Capability / Logical Technology / Runtime Platform

C Infrastructure
= Physical / Virtual Resource / Network / Storage

D Interface
= Interaction Contract / Sync-Async / Failure-Recovery / Version

E Data
= Data Domain / Ownership / Model / Flow / Quality / Lifecycle
```
