# NSIGHT / PDMG 아키텍처 정의서
# 별첨 E. DATA ARCHITECTURE DEFINITION
## Data Domain / Subject Area / Ownership / Model / RDW-ADW / Flow / Quality / Governance / Evidence
## Visual-First / Data-Centric / Evidence-First / Standalone Appendix

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-DATA-APPENDIX-E`  
> 문서 유형: **별첨 / 독립 Data Architecture 정의서**  
> 문서 상태: **Draft / Evidence-First / Visual-First / Data-Centric**  
> 작성 기준일: **2026-08-31**  
> 연계 장: `01 VISION`, `02 BIG PICTURE`, `03 LOGICAL`, `04 PHYSICAL`, `05 MECHANISM`, `06 RUNTIME`, `07 TRACEABILITY`, `10 BASELINE`, `11 DEVELOPMENT STANDARD`  
> 연계 별첨: `A Application`, `B Technical`, `C Infrastructure`, `D Interface`

---

# 0. 이 별첨의 목적

## FIG-DA-01. Data Architecture가 답해야 하는 질문

```text
Business / Analytical Requirement
          ↓
어떤 Data Domain / Subject Area가 존재하는가?
          ↓
누가 데이터를 소유하고 책임지는가?
          ↓
Logical Data Model은 어떻게 구성되는가?
          ↓
Physical Data Model은 어떻게 구현되는가?
          ↓
RDW / ADW 역할은 어떻게 분리되는가?
          ↓
데이터는 어떤 경로로 이동·변환되는가?
          ↓
품질·메타데이터·Lineage는 어떻게 관리되는가?
          ↓
보안·접근·보존·파기는 어떻게 통제되는가?
          ↓
Runtime에서 데이터 신선도·성능·정합성을 어떻게 증명하는가?
```

> **Data Architecture는 비즈니스와 분석 요구를 Data Domain·Subject Area·Ownership·Model·Flow·Quality·Security·Lifecycle로 구조화하고, RDW/ADW·CDC/ETL·Metadata/Lineage·Runtime Evidence까지 연결하여 데이터를 기업 자산으로 일관되게 관리하는 Architecture 영역이다.**

---

# 1. Data Architecture 한 문장 정의

## FIG-DA-02. Definition

```text
Business Meaning
   ↓
Data Domain / Subject Area
   ↓
Ownership
   ↓
Logical Data Model
   ↓
Physical Data Model
   ↓
Data Flow / Integration
   ↓
Quality / Security / Lifecycle
   ↓
Runtime Evidence / Governance
```

---

# 2. Data Architecture가 아닌 것

## FIG-DA-03. Not Data Architecture

```text
Data Architecture
≠ 테이블 목록

Data Architecture
≠ ERD 한 장

Data Architecture
≠ DB 제품 구성도

Data Architecture
≠ ETL Job 목록

Data Architecture
≠ 컬럼 사전만의 집합
```

이 항목들은 Data Architecture의 **구현표현·Inventory·Evidence**다.

---

# 3. Data Architecture와 Database Architecture의 차이

## FIG-DA-04. DA vs DBA

```text
DATA ARCHITECTURE
│
├─ Data Domain / Subject
├─ Ownership
├─ Logical Model
├─ Data Flow
├─ Quality
├─ Metadata / Lineage
├─ Lifecycle
└─ Governance
        ↓
DATABASE ARCHITECTURE
│
├─ DB Service
├─ Schema
├─ Table / Index
├─ Partition
├─ RAC / HA
├─ Backup / Recovery
└─ Physical Storage
```

### 핵심

```text
Database Architecture
⊂
Data Architecture
```

---

# 4. Data Architecture와 Application Architecture의 관계

## FIG-DA-05. AA ↔ DA

```text
APPLICATION ARCHITECTURE
Application / ServiceId
        ↓ reads / writes
DATA ARCHITECTURE
Domain / Entity / Table / View
```

Application이 Data Ownership을 임의로 결정하지 않는다.

---

# 5. Data Architecture와 Interface Architecture의 관계

## FIG-DA-06. IF ↔ DA

```text
DATA ARCHITECTURE
Data Contract / Ownership
      ↓
INTERFACE ARCHITECTURE
API / Event / CDC / ETL / File
      ↓
Consumer Data
```

---

# 6. Data Architecture와 Technical Architecture의 관계

## FIG-DA-07. TA ↔ DA

```text
DATA ARCHITECTURE
Logical / Physical Data Requirement
       ↓
TECHNICAL ARCHITECTURE
DB / CDC / ETL / Storage / Runtime Platform
```

---

# 7. Data Architecture 12대 축

## FIG-DA-08. Twelve Axes

```text
1. Data Principle
2. Data Domain / Subject Area
3. Ownership / Stewardship
4. Logical Data Model
5. Physical Data Model
6. RDW / ADW Role
7. Data Flow / CDC / ETL
8. Metadata / Lineage
9. Data Quality
10. Security / Access / Lifecycle
11. Performance / HA / DR
12. Inventory / Evidence / Governance
```

---

# 8. 최상위 Data 원칙

## FIG-DA-09. Core Principles

```text
Business Meaning First
      +
Single Ownership
      +
RDW / ADW Role Separation
      +
Data Contract
      +
Quality by Design
      +
Metadata / Lineage
      +
Least Privilege
      +
Lifecycle Governance
      +
Runtime Evidence
```

---

# 9. DA-01 — 데이터는 업무 의미를 먼저 가진다

## FIG-DA-10. Meaning First

```text
Business Concept
    ↓
Data Entity
    ↓
Attribute
    ↓
Physical Column
```

### MUST NOT

```text
테이블이 있으니
업무 의미를 나중에 붙인다

X
```

---

# 10. DA-02 — 데이터 소유자는 하나의 책임축을 가진다

## FIG-DA-11. Ownership

```text
Data Subject
   ↓
Business Owner
   ↓
Data Steward
   ↓
System of Record / Master
```

공유 사용자가 많아도 Ownership은 명확해야 한다.

---

# 11. DA-03 — 원천과 활용을 분리한다

## FIG-DA-12. Source vs Consumption

```text
Source / System of Record
        ↓
Controlled Data Flow
        ↓
Operational / Analytical Consumption
```

---

# 12. DA-04 — RDW와 ADW 역할을 분리한다

## FIG-DA-13. RDW / ADW

```text
RDW
= Operational / Near Real-time / Information Service

ADW
= Analytical / Aggregation / Mart / BI
```

---

# 13. DA-05 — 직접 Cross-System DML을 제한한다

## FIG-DA-14. Data Boundary

```text
Application A
  ↓
Own Data
  └─ Direct DML O

Other System Data
  ↓
Approved Interface / Data Contract
```

### MUST NOT

```text
Application A
  ─────────► Application B Table DML
```

---

# 14. DA-06 — 모든 주요 Data Flow는 추적 가능해야 한다

## FIG-DA-15. Lineage Principle

```text
Source
  ↓
Transform
  ↓
Target
  ↓
Consumer
```

각 단계의 Data Object와 Rule을 추적한다.

---

# 15. DA-07 — Data Quality를 사후 정제로만 보지 않는다

## FIG-DA-16. Quality by Design

```text
Definition
  ↓
Validation
  ↓
Load / Update
  ↓
Monitor
  ↓
Issue / Remediation
```

---

# 16. DA-08 — Metadata는 Data Architecture의 일부다

## FIG-DA-17. Metadata

```text
Business Metadata
      +
Technical Metadata
      +
Operational Metadata
      ↓
Data Understanding / Governance
```

---

# 17. DA-09 — Security는 데이터 특성에 따라 적용한다

## FIG-DA-18. Security

```text
Data Classification
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

# 18. DA-10 — Lifecycle을 정의한다

## FIG-DA-19. Lifecycle

```text
Create
  ↓
Use
  ↓
Share
  ↓
Archive
  ↓
Retain
  ↓
Dispose
```

---

# 19. Data Domain

## FIG-DA-20. Domain

```text
Enterprise Data
│
├─ Customer
├─ Account
├─ Product
├─ Transaction
├─ Channel
├─ Marketing
├─ Organization
├─ Code / Reference
└─ Operational / Analytical
```

정확한 도메인 세트는 승인된 Data Subject Baseline으로 확정한다.

---

# 20. Subject Area

## FIG-DA-21. Subject Area

```text
Data Domain
   ↓
Subject Area
   ↓
Entity
   ↓
Attribute
```

---

# 21. Subject Area의 목적

```text
Ownership
Modeling Scope
Naming
Data Quality
Security
Lineage
```

---

# 22. RDW Subject Area

## FIG-DA-22. RDW Subject Model

```text
Operational Information
      ↓
RDW Subject Areas
      ↓
Detailed / Near Real-time Data
```

구체 Subject Code는 최신 Data Subject Inventory를 기준으로 관리한다.

---

# 23. ADW Subject Area

## FIG-DA-23. ADW Subject Model

```text
RDW / Source
   ↓
Transformation
   ↓
ADW Subject Areas
   ↓
Mart / Aggregation / Analytics
```

---

# 24. BI Subject Area

## FIG-DA-24. BI Consumption

```text
ADW
  ↓
BI Semantic / Dataset
  ↓
Report / Dashboard / Analysis
```

BI는 Source of Record가 아니라 **소비/분석 계층**이다.

---

# 25. Data Governance Subject

## FIG-DA-25. Governance Data

```text
Metadata
Data Quality
Lineage
Standard
Reference
Policy
```

---

# 26. Data Ownership Model

## FIG-DA-26. Ownership Roles

```text
Business Owner
   ↓
Data Steward
   ↓
Technical Custodian
   ↓
Consumer
```

---

# 27. Business Owner

```text
Definition
Usage Policy
Quality Expectation
Approval
```

을 책임진다.

---

# 28. Data Steward

## FIG-DA-27. Steward

```text
Business Definition
  ↓
Standard Term
  ↓
Quality Rule
  ↓
Issue Management
```

---

# 29. Technical Custodian

```text
Storage
Access
Backup
Performance
Technical Metadata
```

를 운영한다.

---

# 30. Data Consumer

```text
Approved Purpose
  ↓
Approved Access
  ↓
Use
  ↓
Audit
```

---

# 31. System of Record

## FIG-DA-28. SOR

```text
Business Fact
   ↓
System of Record
   ↓
Controlled Replication / Distribution
```

---

# 32. Master Data

## FIG-DA-29. Master Data

```text
Authoritative Master
  ↓
Standard Identifier
  ↓
Distribution
  ↓
Consumer
```

Master Data 관리체계는 별도 MDM/Governance 정책이 있으면 그 기준을 따른다.

---

# 33. Reference Data

```text
Code
Classification
Calendar
Organization Reference
```

와 같은 공통 기준데이터를 별도 관리한다.

---

# 34. Logical Data Model

## FIG-DA-30. LDM

```text
Business Concept
  ↓
Entity
  ↓
Relationship
  ↓
Attribute
  ↓
Business Key
```

DBMS 물리제약과 독립적으로 업무 의미를 표현한다.

---

# 35. Entity

```text
Customer
Account
Product
Transaction
...
```

처럼 Business Object를 표현한다.

---

# 36. Relationship

## FIG-DA-31. Relationship

```text
Customer
  1
  │
  N
Account
```

Cardinality와 Optionality를 명시한다.

---

# 37. Business Key

```text
Natural / Business Identifier
```

가 무엇인지 정의한다.

---

# 38. Surrogate Key

## FIG-DA-32. Key Strategy

```text
Business Key
   +
Surrogate Key [when needed]
```

Surrogate Key가 Business Identity를 대체한다고 보지 않는다.

---

# 39. Attribute

```text
Name
Type
Business Definition
Required
Domain
Sensitivity
```

을 가진다.

---

# 40. Data Type

## FIG-DA-33. Type Mapping

```text
Business Type
  ↓
Logical Type
  ↓
Physical DB Type
```

---

# 41. Domain / Code Value

```text
Attribute
  ↓
Domain
  ↓
Allowed Code / Range
```

---

# 42. Nullability

```text
Required
Optional
Conditional
```

을 Business Rule과 연결한다.

---

# 43. Logical Naming

## FIG-DA-34. Logical Naming

```text
Standard Term
  ↓
Logical Entity / Attribute Name
  ↓
Physical Name
```

---

# 44. Physical Data Model

## FIG-DA-35. PDM

```text
Logical Entity
  ↓
Table
  ↓
Column
  ↓
PK / FK
  ↓
Index
  ↓
Partition
```

---

# 45. Table

```text
Table Name
Purpose
Owner
Subject Area
Lifecycle
```

을 가진다.

---

# 46. Column

```text
Column Name
Data Type
Length / Precision
Nullability
Default
Sensitivity
```

---

# 47. PK / FK

## FIG-DA-36. Integrity

```text
Primary Key
   ↓
Entity Identity

Foreign Key
   ↓
Relationship Integrity
```

---

# 48. Index

## FIG-DA-37. Index Decision

```text
Query Pattern
  ↓
Selectivity
  ↓
Index Candidate
  ↓
Write Cost
  ↓
Execution Plan
```

---

# 49. Partition

## FIG-DA-38. Partition Decision

```text
Data Volume
  ↓
Access Pattern
  ↓
Retention
  ↓
Partition Key
  ↓
Pruning / Maintenance
```

---

# 50. View

```text
Consumer Purpose
  ↓
View
  ↓
Underlying Tables
```

View를 Data Ownership 우회수단으로 사용하지 않는다.

---

# 51. Materialized View / Aggregate

## FIG-DA-39. Aggregate

```text
Detailed Data
   ↓
Aggregate / MV
   ↓
Analytical Consumer
```

Freshness와 Refresh Policy를 정의한다.

---

# 52. Schema Architecture

## FIG-DA-40. Schema

```text
Database
  ↓
Schema
  ↓
Table / View
  ↓
Privilege
```

---

# 53. Schema Ownership

```text
Schema
  ↓
System / Data Responsibility
```

를 명확히 한다.

---

# 54. RDW Architecture

## FIG-DA-41. RDW Role

```text
Core / Source
   ↓
CDC / ETL
   ↓
RDW
   │
   ├─ Operational Information
   ├─ Near Real-time
   └─ Information Service
```

---

# 55. ADW Architecture

## FIG-DA-42. ADW Role

```text
RDW / Source
   ↓
ETL / Transformation
   ↓
ADW
   │
   ├─ Analytical Data
   ├─ Aggregate
   ├─ Mart
   └─ BI / Analytics
```

---

# 56. RDW와 ADW 데이터 책임

## FIG-DA-43. Responsibility Split

```text
RDW
  ↓ detailed / operational

ADW
  ↓ analytical / transformed
```

같은 데이터를 동일 목적/동일 구조로 중복 보유하지 않도록 한다.

---

# 57. Operational vs Analytical Workload

## FIG-DA-44. Workload

```text
Operational Query
  ↓
RDW

Heavy Analytical Query
  ↓
ADW
```

---

# 58. Data Duplication

```text
Duplication Needed?
  ↓
Purpose?
  ↓
Owner?
  ↓
Freshness?
  ↓
Retention?
  ↓
Lineage?
```

목적 없는 복제를 금지한다.

---

# 59. Data Flow Architecture

## FIG-DA-45. Data Flow

```text
Source
  ↓
Capture / Extract
  ↓
Transform
  ↓
Validate
  ↓
Load / Apply
  ↓
Target
  ↓
Consumer
```

---

# 60. CDC Data Flow

## FIG-DA-46. CDC

```text
Source Commit
  ↓
Capture
  ↓
Transport
  ↓
Apply
  ↓
RDW Visible
```

---

# 61. ETL Data Flow

## FIG-DA-47. ETL

```text
Source
  ↓
Extract
  ↓
Stage
  ↓
Transform
  ↓
Quality Check
  ↓
Load
  ↓
Reconcile
```

---

# 62. File Data Flow

## FIG-DA-48. File

```text
Producer
  ↓
File
  ↓
Transfer
  ↓
Landing
  ↓
Validation
  ↓
Load
```

---

# 63. API Data Flow

## FIG-DA-49. API Data

```text
Consumer Request
  ↓
Application
  ↓
Approved Data Access
  ↓
Response Dataset
```

---

# 64. Event Data Flow

## FIG-DA-50. Event Data

```text
Business Event
  ↓
Event Payload
  ↓
Broker
  ↓
Consumer
  ↓
Consumer Data Update
```

---

# 65. Data Flow Contract

```text
Source
Target
Data Object
Transform Rule
Frequency
Freshness
Quality Rule
Owner
```

---

# 66. Data Lineage

## FIG-DA-51. Lineage

```text
Source Table / Event / File
        ↓
Transform Rule
        ↓
Target Table
        ↓
Mart / Report
```

---

# 67. Column-level Lineage

## FIG-DA-52. Column Lineage

```text
Source.Column A
   +
Source.Column B
       ↓ transform
Target.Column C
```

Critical Data는 Column Level Lineage를 우선 확보한다.

---

# 68. Technical Lineage

```text
Table
  ↓
SqlId / ETL Job
  ↓
Target
```

---

# 69. Business Lineage

## FIG-DA-53. Business Lineage

```text
Business Term
  ↓
Logical Attribute
  ↓
Physical Column
  ↓
Report KPI
```

---

# 70. Metadata Architecture

## FIG-DA-54. Metadata Types

```text
Business Metadata
  ├─ Term
  ├─ Definition
  └─ Owner

Technical Metadata
  ├─ Table
  ├─ Column
  ├─ Type
  └─ Dependency

Operational Metadata
  ├─ Job
  ├─ Runtime
  ├─ Row Count
  └─ Quality Result
```

---

# 71. Metadata Repository

```text
Metadata Source
  ↓
Collection
  ↓
Repository
  ↓
Search / Lineage / Governance
```

---

# 72. Standard Term

## FIG-DA-55. Standard Term

```text
Business Term
  ↓
Standard Name
  ↓
Definition
  ↓
Synonym / Abbreviation
  ↓
Physical Naming
```

---

# 73. Data Dictionary

```text
Entity
Attribute
Table
Column
Code
Domain
```

을 연결한다.

---

# 74. Data Quality Architecture

## FIG-DA-56. Quality Lifecycle

```text
Quality Requirement
  ↓
Rule
  ↓
Measure
  ↓
Threshold
  ↓
Issue
  ↓
Remediation
  ↓
Evidence
```

---

# 75. Data Quality Dimensions

```text
Accuracy
Completeness
Consistency
Validity
Uniqueness
Timeliness
```

---

# 76. Completeness

## FIG-DA-57. Completeness

```text
Expected Required Data
        ↓ compare
Actual Populated Data
        ↓
Completeness %
```

---

# 77. Uniqueness

```text
Business Key
  ↓
Duplicate?
  ↓
PASS / Issue
```

---

# 78. Validity

```text
Value
  ↓
Domain / Rule
  ↓
Valid?
```

---

# 79. Consistency

## FIG-DA-58. Consistency

```text
Source Fact
  ↓ compare
Target Fact
  ↓
Consistent?
```

---

# 80. Timeliness

```text
Expected Freshness
  ↓
Actual Arrival
  ↓
Lag
```

---

# 81. Quality Rule Ownership

## FIG-DA-59. Ownership

```text
Business Owner
  ↓ defines expectation
Data Steward
  ↓ manages rule
Technical Platform
  ↓ measures
Operations
  ↓ alerts
```

---

# 82. Quality Issue Lifecycle

```text
Detect
  ↓
Classify
  ↓
Assign
  ↓
Fix
  ↓
Reprocess
  ↓
Validate
  ↓
Close
```

---

# 83. Data Reconciliation

## FIG-DA-60. Reconciliation

```text
Source Count / Amount
       ↓ compare
Target Count / Amount
       ↓
Difference
       ↓
Investigate / Recover
```

---

# 84. CDC Reconciliation

```text
Source Position
  ↓
Target Position
  ↓
Lag / Missing
```

---

# 85. ETL Reconciliation

```text
Extract Rows
  ↓
Transform Rows
  ↓
Loaded Rows
  ↓
Rejected Rows
```

---

# 86. Data Security Architecture

## FIG-DA-61. Security

```text
Data Classification
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

# 87. Data Classification

```text
Public
Internal
Confidential
Sensitive / Personal
Critical
```

실제 분류체계는 Security/Data Governance 기준으로 확정한다.

---

# 88. Access Control

## FIG-DA-62. Access

```text
User / Service
  ↓
Role
  ↓
Purpose
  ↓
Approved Data
```

---

# 89. Least Privilege

```text
Required Table / View
  ↓
Required Operation
SELECT / DML
  ↓
Minimum Privilege
```

---

# 90. Column-level Protection

## FIG-DA-63. Column Protection

```text
Sensitive Column
  ↓
Mask / Encrypt / Tokenize [policy]
  ↓
Authorized Consumer
```

---

# 91. Encryption

```text
At Rest
In Transit
Key Management
```

을 분리한다.

---

# 92. Masking

## FIG-DA-64. Masking

```text
Production Sensitive Data
      ↓
Masking
      ↓
Non-production Use
```

---

# 93. Audit

```text
Who
What Data
When
Purpose
Result
```

를 추적한다.

---

# 94. Data Privacy

## FIG-DA-65. Privacy by Design

```text
Collect Minimum
  ↓
Use for Approved Purpose
  ↓
Retain only as required
  ↓
Dispose safely
```

---

# 95. Data Lifecycle

## FIG-DA-66. Lifecycle

```text
Create
 ↓
Active
 ↓
Historical
 ↓
Archive
 ↓
Retention
 ↓
Dispose
```

---

# 96. Retention

```text
Data Type
  ↓
Legal / Business Requirement
  ↓
Retention Period
  ↓
Archive / Delete
```

정확한 기간은 승인 정책이 없으면 `[OPEN]`.

---

# 97. Archiving

## FIG-DA-67. Archive

```text
Active Data
  ↓
Archive Rule
  ↓
Archive Storage
  ↓
Search / Restore
```

---

# 98. Disposal

```text
Retention End
  ↓
Approval
  ↓
Delete / Destroy
  ↓
Evidence
```

---

# 99. Data Backup

## FIG-DA-68. Backup

```text
Database / Data Files
  ↓
Backup
  ↓
Retention
  ↓
Restore Test
```

---

# 100. Data Recovery

## FIG-DA-69. Recovery

```text
Failure
  ↓
Restore / Failover
  ↓
Data Consistency
  ↓
Business Validation
```

---

# 101. Backup ≠ Recovery

```text
Backup SUCCESS
≠ Restore SUCCESS
≠ Data Consistency PASS
≠ Business Recovery PASS
```

---

# 102. Data HA

## FIG-DA-70. HA

```text
DB Service
  ↓
Local HA / RAC / Cluster
  ↓
Node Failure
  ↓
Service Continuity
```

---

# 103. Data DR

## FIG-DA-71. DR

```text
Main Data
  ↓ replicate / backup
DR Data
  ↓
Recovery
  ↓
Consistency Validation
```

---

# 104. RPO / RTO

## FIG-DA-72. Recovery Objective

```text
Business Data
  ↓
Criticality
  ↓
RPO / RTO
  ↓
Replication / Backup Design
  ↓
DR Test
```

---

# 105. Data Performance Architecture

## FIG-DA-73. Performance

```text
Query Pattern
  ↓
Data Volume
  ↓
Index / Partition
  ↓
SQL
  ↓
DB Resource
  ↓
Response / Throughput
```

---

# 106. Query Pattern

```text
Point Lookup
Range
Join
Aggregate
Full Scan
```

에 따라 물리설계를 달리한다.

---

# 107. Large Query Isolation

## FIG-DA-74. Workload Isolation

```text
Operational Query
   ↓
RDW

Heavy Analytical Query
   ↓
ADW / Analytical Platform
```

---

# 108. SQL Traceability

## FIG-DA-75. Service to Data

```text
ServiceId
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

# 109. Table Impact Analysis

## FIG-DA-76. Reverse Impact

```text
Table
  ↑
SqlId
  ↑
DAO
  ↑
ServiceId
  ↑
Application
```

---

# 110. DML Ownership

```text
Table
  ↓
Authorized Writer
  ↓
Service / Batch / ETL
```

Writer를 전수 식별한다.

---

# 111. Read Ownership

```text
Table / View
  ↓
Approved Readers
```

---

# 112. Data Interface Architecture

## FIG-DA-77. Data Interfaces

```text
Online API
Event
CDC
ETL
File
```

Data Architecture는 Data Contract와 Ownership을 정의하고,
Interface Architecture가 Interaction Policy를 정의한다.

---

# 113. Data Contract

## FIG-DA-78. Data Contract

```text
Producer
  ↓
Data Contract
  ├─ Meaning
  ├─ Schema
  ├─ Quality
  ├─ Freshness
  ├─ Security
  └─ Version
  ↓
Consumer
```

---

# 114. Data Contract Version

```text
Schema v1
  ↓ compatible evolution
v1.x
  ↓ breaking
v2
```

---

# 115. Schema Evolution

## FIG-DA-79. Evolution

```text
Add Optional
  → Compatible candidate

Remove / Type Change
  → Breaking candidate
```

---

# 116. Event Schema와 Data Model

```text
Business Entity
  ↓
Event Projection
  ↓
Consumer Model
```

Event Payload가 원본 전체 Table Dump가 되지 않도록 한다.

---

# 117. CDC와 Data Model

## FIG-DA-80. CDC Semantics

```text
Physical Change
  ↓
CDC
  ↓
Target Physical Data
```

CDC는 Business Semantic Contract와 동일하지 않을 수 있다.

---

# 118. ETL Transformation Rule

```text
Source Column
  ↓
Transform Rule
  ↓
Target Column
```

---

# 119. Data Mart

## FIG-DA-81. Mart

```text
ADW
  ↓
Subject / Department Mart
  ↓
BI / Analysis
```

---

# 120. Semantic Layer

```text
Physical Data
  ↓
Business Metric / Dimension
  ↓
Semantic Dataset
  ↓
BI
```

---

# 121. KPI Definition

## FIG-DA-82. KPI

```text
Business KPI
  ↓
Definition
  ↓
Formula
  ↓
Source Data
  ↓
Report
```

KPI 산식도 Lineage 대상이다.

---

# 122. Data Catalog

## FIG-DA-83. Catalog

```text
Business Term
Entity
Table
Column
Owner
Quality
Lineage
Security
```

를 검색 가능하게 관리한다.

---

# 123. Data Inventory SSOT

## FIG-DA-84. Inventory

```text
Data Subject Inventory
Logical Model
Physical Model
Table Inventory
Column Dictionary
Data Flow
Lineage
Quality Rule
Security Classification
```

---

# 124. Data Subject Inventory Template

```yaml
subject:
  subjectId:
  domain:
  name:
  definition:
  owner:
  steward:
  systemOfRecord:
  rdw:
  adw:
  consumers:
  sensitivity:
  status:
```

---

# 125. Entity Inventory

```yaml
entity:
  entityId:
  subject:
  logicalName:
  definition:
  businessKey:
  owner:
  relationships:
  sensitivity:
```

---

# 126. Table Inventory

```yaml
table:
  database:
  schema:
  table:
  logicalEntity:
  subject:
  owner:
  writers:
  readers:
  retention:
  backup:
  evidence:
```

---

# 127. Column Inventory

```yaml
column:
  table:
  column:
  logicalAttribute:
  type:
  nullable:
  domain:
  sensitivity:
  lineage:
```

---

# 128. Data Flow Inventory

```yaml
dataFlow:
  flowId:
  source:
  target:
  mechanism:
  objects:
  transform:
  frequency:
  freshness:
  quality:
  owner:
```

---

# 129. Quality Rule Inventory

```yaml
qualityRule:
  ruleId:
  dataObject:
  dimension:
  condition:
  threshold:
  owner:
  remediation:
  evidence:
```

---

# 130. Lineage Inventory

```yaml
lineage:
  sourceObject:
  transform:
  targetObject:
  runtimeJob:
  consumer:
  evidence:
```

---

# 131. Data Security Inventory

```yaml
dataSecurity:
  dataObject:
  classification:
  allowedRoles:
  masking:
  encryption:
  audit:
  retention:
```

---

# 132. Runtime Data Evidence

## FIG-DA-85. Evidence

```text
Data Architecture Baseline
      ↓
Model Version
      ↓
Source / Job / Service
      ↓
Runtime Flow
      ↓
Row Count / Lag / Quality
      ↓
Evidence
```

---

# 133. CDC Runtime Evidence

```text
Source Position
Target Position
Lag
Apply Error
Timestamp
```

---

# 134. ETL Runtime Evidence

```text
JobId
Start / End
Extract Rows
Load Rows
Reject Rows
Status
```

---

# 135. Data Quality Evidence

```text
Rule
Threshold
Actual
Result
Issue
```

---

# 136. Data Access Evidence

```text
User / Service
  ↓
Access
  ↓
Table / View
  ↓
Audit
```

---

# 137. Data Architecture Model

## FIG-DA-86. Model Entities

```text
DataDomain
SubjectArea
Entity
Attribute
Table
Column
DataFlow
QualityRule
DataOwner
DataSteward
Classification
RetentionPolicy
Lineage
Evidence
```

---

# 138. Model Relations

## FIG-DA-87. Relations

```text
Domain
  CONTAINS
Subject

Subject
  CONTAINS
Entity

Entity
  IMPLEMENTED_BY
Table

Attribute
  IMPLEMENTED_BY
Column

DataFlow
  MOVES
DataObject

QualityRule
  VALIDATES
DataObject
```

---

# 139. Application / Data Relations

```text
Application
  READS
DataObject

Application
  WRITES
DataObject

ServiceId
  ACCESSES
Table
```

---

# 140. Interface / Data Relations

```text
Interface
  PRODUCES
DataContract

DataContract
  MAPS_TO
DataObject
```

---

# 141. Data Rule Categories

## FIG-DA-88. Rule Set

```text
Ownership
Naming
Model
Integrity
Cross-System DML
RDW/ADW Role
Lineage
Quality
Security
Retention
Backup / DR
Runtime Evidence
```

---

# 142. Ownership Rule

```text
R-DA-OWNER-REQUIRED
R-DA-STEWARD-REQUIRED
```

---

# 143. Naming Rule

```text
R-DA-STANDARD-TERM
R-DA-LOGICAL-PHYSICAL-NAMING
```

---

# 144. Integrity Rule

```text
R-DA-KEY
R-DA-RELATIONSHIP
R-DA-NOTNULL
```

---

# 145. Data Boundary Rule

```text
R-DA-CROSS-SYSTEM-DML-FORBIDDEN
R-DA-APPROVED-WRITER
```

---

# 146. RDW / ADW Rule

```text
R-DA-RDW-OPERATIONAL
R-DA-ADW-ANALYTICAL
R-DA-WORKLOAD-ISOLATION
```

---

# 147. Lineage Rule

```text
R-DA-LINEAGE-CRITICAL
R-DA-SQL-TABLE-TRACE
```

---

# 148. Quality Rule

```text
R-DA-QUALITY-RULE
R-DA-QUALITY-OWNER
R-DA-QUALITY-EVIDENCE
```

---

# 149. Security Rule

```text
R-DA-CLASSIFICATION
R-DA-ACCESS
R-DA-SENSITIVE-PROTECTION
R-DA-AUDIT
```

---

# 150. Lifecycle Rule

```text
R-DA-RETENTION
R-DA-ARCHIVE
R-DA-DISPOSAL
```

---

# 151. Recovery Rule

```text
R-DA-BACKUP
R-DA-RESTORE-EVIDENCE
R-DA-DR-CONSISTENCY
```

---

# 152. Data Architecture Test

## FIG-DA-89. Test Stack

```text
Model Validation
  ↓
Schema Validation
  ↓
Integrity Test
  ↓
Data Quality Test
  ↓
Lineage Test
  ↓
Security Test
  ↓
Performance Test
  ↓
Backup / Restore / DR
  ↓
Runtime Evidence
```

---

# 153. Model Validation

```text
Entity
Relationship
Key
Domain
Required Attribute
```

을 검증한다.

---

# 154. Schema Validation

```text
Logical Model
  ↓ compare
Physical Schema
  ↓
Conformance
```

---

# 155. Integrity Test

## FIG-DA-90. Integrity

```text
PK
FK
Not Null
Unique
Domain
  ↓
PASS / FAIL
```

---

# 156. Quality Test

```text
Quality Rule
  ↓
Execute
  ↓
Threshold
  ↓
PASS / FAIL
```

---

# 157. Lineage Test

```text
Source
  ↓
Transform
  ↓
Target
```

실제 Runtime Job/SQL과 일치하는지 검증한다.

---

# 158. Security Test

## FIG-DA-91. Security Test

```text
Role
  ↓
Allowed Data?
  ↓
Sensitive Protection?
  ↓
Audit?
```

---

# 159. Performance Test

```text
Representative Query
  ↓
Execution Plan
  ↓
Index / Partition
  ↓
Response / Resource
```

---

# 160. Restore Test

```text
Backup
  ↓
Restore
  ↓
Consistency
  ↓
Business Validation
```

---

# 161. Data Change Governance

## FIG-DA-92. Change

```text
Data Change
  ↓
Model Impact
  ↓
Application Impact
  ↓
Interface Impact
  ↓
ETL / CDC Impact
  ↓
Quality / Security Impact
  ↓
Migration
  ↓
Evidence
```

---

# 162. Schema Change

```text
Column Add
Type Change
Column Remove
Key Change
Index Change
Partition Change
```

은 영향분석 대상이다.

---

# 163. Data Migration

## FIG-DA-93. Migration

```text
Source
  ↓
Extract
  ↓
Clean / Transform
  ↓
Load
  ↓
Reconcile
  ↓
Cutover
  ↓
Validate
```

---

# 164. Migration Reconciliation

```text
Count
Amount
Key
Quality
Exception
```

을 검증한다.

---

# 165. Historical Data Migration

```text
Required History
  ↓
Retention Need
  ↓
Migration Scope
  ↓
Archive / Target
```

---

# 166. Data Exception Governance

## FIG-DA-94. Exception

```text
Standard
  ↓
Exception Needed
  ↓
Reason
  ↓
Data / Security / Performance Impact
  ↓
Architecture Review
  ↓
ADR
  ↓
Expiry / Review
```

---

# 167. Data Anti-pattern

## FIG-DA-95. Anti-pattern Map

```text
Owner 없는 Table
Direct Cross-System DML
목적 없는 복제
RDW에 Heavy BI Query 집중
ADW를 Online SOR처럼 사용
Lineage 없는 ETL
Quality Rule 없는 중요데이터
Sensitive Data 무분류
Retention 없는 영구보관
Backup만 있고 Restore Test 없음
```

---

# 168. Application Architecture와 관계

## FIG-DA-96. AA ↔ DA

```text
Application
  ↓
ServiceId
  ↓
DAO / SQL
  ↓
Data Object
```

---

# 169. Interface Architecture와 관계

## FIG-DA-97. IF ↔ DA

```text
Data Contract
  ↓
Interface
  ↓
Consumer
```

---

# 170. Technical Architecture와 관계

## FIG-DA-98. TA ↔ DA

```text
Data Requirement
  ↓
DB / CDC / ETL Platform
  ↓
Runtime
```

---

# 171. Infrastructure Architecture와 관계

## FIG-DA-99. INFRA ↔ DA

```text
Data Platform
  ↓
DB / Storage
  ↓
HA / DR / Backup
```

---

# 172. Security Architecture와 관계

## FIG-DA-100. Security ↔ DA

```text
Data Classification
  ↓
Access / Encryption / Masking
  ↓
Audit
```

---

# 173. Operations Architecture와 관계

## FIG-DA-101. OPS ↔ DA

```text
Data Runtime
  ↓
Lag / Quality / DB / Job Metric
  ↓
Alert / Runbook
  ↓
Recovery
```

---

# 174. Development Standard와 관계

## FIG-DA-102. Dev ↔ DA

```text
Data Architecture
Model / Ownership / Rule
      ↓
Development Standard
DAO / Mapper / SQL / DTO
      ↓
Source
```

---

# 175. 신규 Data Subject 설계 Route

## FIG-DA-103. New Subject

```text
Business Concept
  ↓
Domain / Subject
  ↓
Owner / Steward
  ↓
Logical Entity
  ↓
Physical Model
  ↓
Data Flow
  ↓
Quality / Security
  ↓
Lifecycle
  ↓
Evidence
```

---

# 176. 신규 Table Checklist

```text
[ ] Subject Area
[ ] Logical Entity
[ ] Owner
[ ] Naming
[ ] PK/FK
[ ] Column Definition
[ ] Sensitivity
[ ] Retention
[ ] Index
[ ] Partition
[ ] Writers
[ ] Readers
[ ] Backup
```

---

# 177. 신규 Data Flow Checklist

```text
[ ] Source
[ ] Target
[ ] Data Object
[ ] Mechanism
[ ] Transform
[ ] Frequency
[ ] Freshness
[ ] Quality
[ ] Security
[ ] Reconcile
[ ] Owner
[ ] Evidence
```

---

# 178. 신규 Quality Rule Checklist

```text
[ ] Data Object
[ ] Dimension
[ ] Rule
[ ] Threshold
[ ] Owner
[ ] Frequency
[ ] Alert
[ ] Remediation
[ ] Evidence
```

---

# 179. 신규 Analytical Mart Checklist

```text
[ ] Business Purpose
[ ] Source
[ ] Grain
[ ] Dimension
[ ] Measure
[ ] Refresh
[ ] Lineage
[ ] Quality
[ ] Owner
[ ] Consumer
```

---

# 180. Data Go-Live Blocker

## FIG-DA-104. Go-Live Block

```text
Owner 없음
     OR
Logical/Physical Mapping 없음
     OR
Critical Data Quality Rule 없음
     OR
Sensitive Classification 없음
     OR
Lineage 없음
     OR
Cross-System DML Bypass
     OR
Backup/Restore Evidence 없음
     OR
Critical Migration Reconcile 미완료
     ↓
GO-LIVE BLOCK
```

---

# 181. Data GAP Register

## TEXT ARCHITECTURE — GAP Lifecycle

```text
Target Data Architecture
      ↓ compare
Actual Data Model / Runtime
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
| GAP-DA-01 | Data Subject SSOT 최신화 필요 | Governance |
| GAP-DA-02 | Data Owner/Steward 전수지정 미완료 | Ownership |
| GAP-DA-03 | Logical↔Physical Model 전수 Mapping 미완료 | Modeling |
| GAP-DA-04 | Table/Column Dictionary 최신화 필요 | Metadata |
| GAP-DA-05 | ServiceId→SqlId→Table 전수 Trace 미완료 | Trace |
| GAP-DA-06 | CDC Freshness SLA Conflict | Freshness |
| GAP-DA-07 | ETL Lineage 전수화 미완료 | Lineage |
| GAP-DA-08 | Critical Data Quality Rule Coverage 미완료 | Quality |
| GAP-DA-09 | Data Classification 전수화 필요 | Security |
| GAP-DA-10 | Retention/Disposal Policy Mapping 미완료 | Lifecycle |
| GAP-DA-11 | RDW/ADW Data Duplication Review 필요 | Platform |
| GAP-DA-12 | Restore/DR Data Consistency Evidence 미완료 | Recovery |
| GAP-DA-13 | Metadata/Lineage 자동수집 보강 필요 | Governance |
| GAP-DA-14 | Runtime Data Evidence 자동화 미완료 | Evidence |

---

# 182. Data Risk Register

## TEXT ARCHITECTURE — Risk Lifecycle

```text
Data Weakness
  ↓
Quality / Consistency / Security Event
  ↓
Business Impact
  ↓
Severity
  ↓
Mitigation
  ↓
Evidence
```

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-DA-01 | Owner 없는 중요데이터 | High |
| RISK-DA-02 | Cross-System Direct DML | Critical |
| RISK-DA-03 | RDW/ADW 역할혼재 | High |
| RISK-DA-04 | CDC Lag 미탐지 | High |
| RISK-DA-05 | ETL 누락/중복 | Critical |
| RISK-DA-06 | Lineage 부재 | High |
| RISK-DA-07 | Quality Rule 부재 | High |
| RISK-DA-08 | Sensitive Data 노출 | Critical |
| RISK-DA-09 | Retention 미관리 | High |
| RISK-DA-10 | Migration Reconcile 실패 | Critical |
| RISK-DA-11 | Restore 미검증 | Critical |
| RISK-DA-12 | Heavy BI Query의 Operational 영향 | High |

---

# 183. Data ADR 후보

## FIG-DA-105. ADR Areas

```text
Data Decision
│
├─ Subject / Ownership
├─ Logical / Physical Model
├─ RDW / ADW Role
├─ CDC / ETL
├─ Metadata / Lineage
├─ Quality
├─ Security
├─ Retention
├─ Migration
├─ Backup / DR
└─ Performance
```

대표 ADR:

```text
ADR-DA-01 Data Subject SSOT
ADR-DA-02 Data Ownership
ADR-DA-03 Logical/Physical Naming
ADR-DA-04 RDW/ADW Responsibility
ADR-DA-05 CDC Freshness
ADR-DA-06 ETL/Lineage
ADR-DA-07 Data Quality Governance
ADR-DA-08 Sensitive Data Protection
ADR-DA-09 Retention/Disposal
ADR-DA-10 Migration/Reconciliation
ADR-DA-11 Backup/Restore
ADR-DA-12 Data Runtime Evidence
```

---

# 184. Data Architecture Review Gate

## FIG-DA-106. Review Gate

```text
Domain / Subject?
  ↓
Owner / Steward?
  ↓
Logical Model?
  ↓
Physical Model?
  ↓
RDW / ADW Role?
  ↓
Data Flow?
  ↓
Quality / Lineage?
  ↓
Security / Lifecycle?
  ↓
Performance / Recovery?
  ↓
Evidence?
  ↓
PASS / GAP
```

---

# 185. Data Architecture Completion Gate

## FIG-DA-107. Completion Gate

```text
Principles Defined?
   ↓ YES
Subject Areas Defined?
   ↓ YES
Ownership Defined?
   ↓ YES
Logical Model Defined?
   ↓ YES
Physical Model Defined?
   ↓ YES
RDW/ADW Role Defined?
   ↓ YES
Data Flow/Lineage Defined?
   ↓ YES
Quality/Security/Lifecycle Defined?
   ↓ YES
Recovery/Performance Defined?
   ↓ YES
Inventory/Evidence Defined?
   ↓ YES
DATA ARCHITECTURE PASS
```

---

# 186. 최종 Data Architecture 지도

## FIG-DA-108. Final Data Map

```text
BUSINESS MEANING
      ↓
────────────────────────────────────
DATA DOMAIN / SUBJECT AREA
      ↓
OWNERSHIP / STEWARDSHIP
      ↓
────────────────────────────────────
LOGICAL DATA MODEL
Entity / Attribute / Relationship
      ↓
PHYSICAL DATA MODEL
Table / Column / Key / Index / Partition
      ↓
────────────────────────────────────
DATA PLATFORM
RDW ──► Operational / Near Real-time
ADW ──► Analytical / Mart / BI
      ↓
────────────────────────────────────
DATA FLOW
API / Event / CDC / ETL / File
      ↓
────────────────────────────────────
GOVERNANCE
Metadata / Lineage / Quality
Security / Retention / Audit
      ↓
────────────────────────────────────
OPERATIONS
Performance / Backup / DR / Evidence
```

---

# 187. Definition of Done

## TEXT ARCHITECTURE — Data Architecture DoD

```text
Business Meaning
  ↓
Domain / Subject
  ↓
Ownership
  ↓
Logical / Physical Model
  ↓
RDW / ADW / Flow
  ↓
Quality / Lineage
  ↓
Security / Lifecycle
  ↓
Runtime Evidence
  ↓
DATA ARCHITECTURE DoD
```

## Principle / Domain
- [x] Data Architecture 정의
- [x] DA vs Database Architecture
- [x] Domain / Subject Area
- [x] Ownership / Stewardship
- [x] System of Record / Master / Reference

## Modeling
- [x] Logical Data Model
- [x] Entity / Attribute / Relationship
- [x] Business / Surrogate Key
- [x] Physical Data Model
- [x] Table / Column / PK/FK / Index / Partition

## Data Platform / Flow
- [x] RDW / ADW
- [x] CDC
- [x] ETL
- [x] File / API / Event
- [x] Data Contract
- [x] Schema Evolution

## Governance
- [x] Metadata
- [x] Lineage
- [x] Data Quality
- [x] Reconciliation
- [x] Catalog / Inventory

## Security / Lifecycle
- [x] Classification
- [x] Access
- [x] Masking / Encryption
- [x] Audit
- [x] Retention / Archive / Disposal

## NFR / Operations
- [x] Performance
- [x] Backup / Restore
- [x] HA / DR
- [x] RPO / RTO
- [x] Runtime Evidence
- [x] GAP / Risk / ADR
- [x] Go-Live Blocker

**DATA ARCHITECTURE 별첨 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. Data Subject Inventory 최신 Baseline 승인
2. Owner/Steward 전수지정
3. Logical↔Physical Model 전수 Mapping
4. Table/Column Dictionary 최신화
5. ServiceId→SqlId→Table 자동 Trace
6. CDC Freshness SLA 확정
7. ETL/Data Flow Lineage 전수화
8. Critical Data Quality Rule Coverage 확보
9. Data Classification/Protection 전수화
10. Retention/Disposal Mapping
11. RDW/ADW 중복데이터 검토
12. Backup/Restore/DR Consistency Evidence
13. Metadata/Lineage 자동수집
14. Runtime Data Evidence 자동화
15. Critical Data Drift 0

---

# 188. 장 최종 결론

## FIG-DA-109. Data Architecture Final

```text
Business Meaning
      ↓
Data Domain / Subject
      ↓
Ownership
      ↓
Logical / Physical Model
      ↓
RDW / ADW
      ↓
CDC / ETL / Data Flow
      ↓
Metadata / Lineage / Quality
      ↓
Security / Lifecycle
      ↓
Performance / Recovery
      ↓
Runtime Evidence
      ↓
Data Architecture Baseline
```

> **Data Architecture의 본질은 테이블과 ERD를 그리는 것이 아니라, 비즈니스 의미를 Data Domain·Subject·Ownership으로 구조화하고 이를 Logical/Physical Model·RDW/ADW·Data Flow·Quality·Lineage·Security·Lifecycle까지 일관되게 연결하는 것이다.**

> **RDW는 운영·준실시간 정보서비스 역할, ADW는 분석·집계·마트 역할로 분리하며, Application과 BI가 각자의 목적에 맞는 Data Platform을 사용하도록 Workload와 Ownership을 통제해야 한다.**

> **NSIGHT Data Architecture는 Requirement → Data Subject → Entity/Attribute → Table/Column → Data Flow → Consumer → Runtime Quality/Lineage Evidence가 끊기지 않아야 하며, Cross-System Direct DML·Owner 없는 Data·Lineage 없는 ETL·Restore 미검증은 원칙적으로 허용하지 않는다.**
