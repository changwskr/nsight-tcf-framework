# NSIGHT PDMG 아키텍처 정의서
# 제10장. 데이터플랫폼
## Story: “RDW는 실시간을 지키고 ADW는 분석을 극대화한다”


---

# 0. Opening Script

Runtime의 끝에는 항상 Data가 있습니다. 하지만 Data Architecture를 DB 목록으로 끝내면 안 됩니다.

이 장에서는 **어떤 Data가 누구의 소유인지, Online Workload와 Analytical Workload를 어디에서 분리할지, ServiceId에서 Table까지 어떻게 Trace할지**를 봅니다.

## FIG-10-01. 장 전체 Architecture

```text
PDMG Online
 ↓
Service
 ↓
DAO / Mapper / SqlId
 ↓
RDW
 ↓
Operational

NSIGHT Analytics
Source / RDW
 ↓
ETL / Data Movement
 ↓
ADW
 ↓
BI / Heavy Analysis
```

Data Platform은 DB 목록이 아니라 Workload와 Ownership을 설계하는 장입니다. PDMG Current의 RDW 경로와 NSIGHT Target의 ADW/CDC/ETL을 구분하고, ServiceId에서 SqlId와 Table까지 추적 가능한 Data Lineage를 목표로 합니다.


---

# 1. Data Architecture vs DB

## FIG-10-02. Data Architecture vs DB

```text
Data Architecture
Ownership / Flow / Quality / Security / Lifecycle
        ↓
DB Architecture
Schema / Instance / HA / Storage
```

Data Architecture는 Database Architecture보다 상위개념입니다.

어디에 저장하는지보다 누가 소유하고 어떤 업무가 사용하며 어떤 품질/보안정책을 적용할지가 먼저입니다.

DB 제품과 Instance는 그 정책을 구현하는 수단입니다.


---

# 2. PDMG Current Data Access

## FIG-10-03. PDMG Current Data Access

```text
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
JDBC
 ↓
RDW / DB
```

PDMG Current에서 가장 강하게 확인되는 Data Access는 MyBatis/JDBC를 통한 RDW/DB 경로입니다.

이 구조를 AS-IS Backbone으로 두고 ADW나 External Data는 별도 Inventory로 확인합니다.

확인되지 않은 ADW 직접접근을 Current로 그리지 않습니다.


---

# 3. RDW 역할

## FIG-10-04. RDW 역할

```text
Online Service
 ↓
RDW
Operational / Near-real-time
 ↓
bounded latency
```

RDW는 Online/Operational Workload를 지키는 영역으로 봅니다.

여기에는 즉시 조회·거래에 필요한 데이터가 집중되고, Heavy Analysis가 자원을 독점하지 않도록 해야 합니다.

실제 Table/Schema Ownership은 추가 Registry가 필요합니다.


---

# 4. ADW 역할

## FIG-10-05. ADW 역할

```text
RDW / Source
 ↓
ETL / Data Movement
 ↓
ADW
 ↓
Mart / Aggregate
 ↓
BI / Analytics
```

ADW는 분석과 집계, Mart, Heavy Query를 담당하는 Target 역할입니다.

PDMG Current가 ADW를 직접 사용하는지는 아직 Datasource/Mapper Inventory가 필요합니다.

따라서 ADW는 Target Reference와 Current Evidence를 구분해 설명합니다.


---

# 5. ServiceId→Table Trace

## FIG-10-06. ServiceId→Table Trace

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

Data Lineage를 Application과 연결하는 핵심축입니다.

어떤 ServiceId가 어떤 SqlId와 Table을 사용하는지 추적할 수 있어야 변경영향과 성능분석이 가능합니다.

이 Trace는 수동 문서가 아니라 자동 Index가 목표입니다.


---

# 6. CDC와 ETL 분리

## FIG-10-07. CDC와 ETL 분리

```text
Change Data
Source DB → CDC → RDW/Consumer

Bulk Data
Source → ETL → ADW
```

CDC와 ETL은 목적이 다릅니다.

CDC는 Change를 빠르게 전달하고, ETL은 대량 변환/적재에 적합합니다. 하나를 다른 목적에 무리하게 사용하면 지연이나 운영복잡도가 커집니다.

CDC Freshness 3s와 30s 충돌은 SLA Tier로 정리해야 합니다.


---

# 7. Data Ownership / Direct DML

## FIG-10-08. Data Ownership / Direct DML

```text
Own Data
 Read / Write

Other System Data
 ↓
Approved Contract

Direct DML
 X
```

Data Ownership이 불명확하면 시스템 경계도 무너집니다.

타 시스템 Table을 직접 DML하면 변경 영향, Lock, 보안, 운영책임이 강하게 결합됩니다.

따라서 Cross-system Direct DML은 기본 금지로 보고 예외는 ADR로 관리합니다.


---

# 8. Data Evidence

## FIG-10-09. Data Evidence

```text
ServiceId
 ↓
SqlId
 ↓
Table
 ↓
elapsed / rows / error
 ↓
GUID
 ↓
Evidence
```

Data Architecture도 Runtime Evidence로 닫아야 합니다.

어떤 거래가 어떤 SQL을 실행했고 얼마나 걸렸는지 연결되면 Performance와 Data Lineage를 함께 볼 수 있습니다.

이 Evidence가 13장 Closed Loop의 입력이 됩니다.

---

# 정상패턴과 금지패턴

## FIG-10-10. Normal Pattern

```text
ServiceId→SqlId→Table / Online→RDW / Analysis→ADW
```

정상패턴은 Online Operational Workload를 RDW에서 보호하고 Heavy Analysis는 ADW로 분리하며 Data Access를 ServiceId→SqlId→Table까지 추적하는 것입니다.

## FIG-10-11. Forbidden Pattern

```text
Cross-system DML / Heavy Query→Online
```

Cross-system Direct DML, Heavy BI Query의 Online RDW 직접 수행, 근거 없는 ADW Current 표시를 금지합니다.

---

# Architecture Decision

## FIG-10-12. 주안과 대안

```text
[주안]
RDW/ADW Workload 분리

        VS

[대안]
RDW 중심 통합사용
```

RDW는 Operational SLA를 보호하고 ADW는 Analytical Workload를 담당하도록 역할을 분리합니다.

RDW 중심 통합사용은 Data Copy를 줄일 수 있지만 Heavy Analysis가 Online Workload와 Resource를 경쟁하게 되므로 기본안으로 두지 않습니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---


# Evidence / Conformance — 이 장의 Architecture를 무엇으로 증명하는가

## Evidence Architecture

```text
데이터플랫폼 Architecture Rule
        ↓
Source Evidence
        ↓
Config Evidence
        ↓
Runtime / Deployment Evidence
        ↓
Conformance Test
        ↓
PASS / GAP / ADR
```

### Source Evidence

- `[SOURCE]` DAO / Mapper / SqlId / RDW Current 경로
- `[SOURCE]` Data Architecture / Interface / Event-CDC-ETL 분석

### Config Evidence

- `[CONFIG]` Datasource/Mapper Inventory 필요
- `[CONFIG]` CDC SLA baseline 충돌 3s vs 30s

### Runtime / Deployment Evidence

- `[RUNTIME]` ServiceId→DAO→Mapper→SqlId→Table Trace
- `[RUNTIME]` RDW Operational / ADW Analytical Target

### Architecture Decision

- `[DECISION]` RDW/ADW Workload Separation
- `[DECISION]` Cross-system Direct DML 금지

### Related ADR

- `ADR-015 Direct DB 제한`
- `ADR-021 RDW/ADW 분리`
- `ADR-022 CDC SLA`

### Current GAP / OPEN

- `[GAP/OPEN]` RDW/ADW 실제 Mapping
- `[GAP/OPEN]` Ownership
- `[GAP/OPEN]` Lineage/DQ 자동화
- `[GAP/OPEN]` CDC SLA Conflict

## 이 장의 판정

```text
Architecture Definition
        ↓
PASS

Current PDMG Conformance
        ↓
PARTIAL / CONDITIONAL

Runtime Evidence Coverage
        ↓
MEDIUM

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `RDW/ADW Datasource Mapping`
- `Table Ownership/Lineage`
- `CDC SLA ADR`
- `Data Runtime Evidence`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-10-16. 다음 장 Handoff

```text
데이터플랫폼
 ↓
RDW는 실시간을 지키고 ADW는 분석을 극대화한다
 ↓
남은 질문
"배치형 캠페인에서 고객 행동에 반응하는 플랫폼으로"
 ↓
마케팅플랫폼
```

Data Platform에서 신뢰할 수 있는 Operational/Analytical 경계를 만들었습니다. 다음 장에서는 이 Data와 PDMG Runtime이 Marketing Platform의 실행구조에서 어떻게 사용되는지 연결합니다.
