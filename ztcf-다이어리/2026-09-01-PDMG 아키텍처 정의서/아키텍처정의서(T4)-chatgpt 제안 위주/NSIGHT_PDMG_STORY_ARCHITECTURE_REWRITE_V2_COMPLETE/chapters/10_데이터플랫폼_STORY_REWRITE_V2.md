# NSIGHT PDMG 아키텍처 정의서
# 제10장. 데이터플랫폼
## Story: “RDW는 실시간을 지키고 ADW는 분석을 극대화한다”
## STORY-FIRST / TEXT-ARCHITECTURE-FIRST / TOP-DOWN → DRILL-DOWN / EVIDENCE-FIRST

> 작성 버전: `REWRITE V2 — Story Quality / Figure-to-Explanation Consistency 보완`  
> 기준일: `2026-09-01`  
> PDMG = Current / Source / Config / Runtime  
> NSIGHT = Target / Alignment / Strategy Reference

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

이제 이 전체 그림을 위에서 아래로 해부하겠습니다. 이번 버전에서는 그림의 박스와 화살표를 직접 설명하고, 반복적인 형식문장은 최소화합니다. 각 Drill-down은 상위 그림의 어느 부분을 확대하는지 명확하게 연결합니다.

## FIG-10-02. Drill-down Route

```text
L0 전체 Story
 ↓
L1 책임 / Boundary
 ↓
L2 Logical / Application / Platform
 ↓
L3 Component / Contract
 ↓
L4 Runtime / Failure / Security
 ↓
L5 Source / Config / Deployment / Evidence
```

---

# 1. Data Architecture vs DB

## FIG-10-03. Data Architecture vs DB

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

여기까지가 `Data Architecture vs DB`의 역할입니다. 이제 이 구조를 더 내려가 **PDMG Current Data Access**에서 다음 경계와 실행책임을 보겠습니다.

---

# 2. PDMG Current Data Access

## FIG-10-04. PDMG Current Data Access

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

여기까지가 `PDMG Current Data Access`의 역할입니다. 이제 이 구조를 더 내려가 **RDW 역할**에서 다음 경계와 실행책임을 보겠습니다.

---

# 3. RDW 역할

## FIG-10-05. RDW 역할

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

여기까지가 `RDW 역할`의 역할입니다. 이제 이 구조를 더 내려가 **ADW 역할**에서 다음 경계와 실행책임을 보겠습니다.

---

# 4. ADW 역할

## FIG-10-06. ADW 역할

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

여기까지가 `ADW 역할`의 역할입니다. 이제 이 구조를 더 내려가 **ServiceId→Table Trace**에서 다음 경계와 실행책임을 보겠습니다.

---

# 5. ServiceId→Table Trace

## FIG-10-07. ServiceId→Table Trace

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

여기까지가 `ServiceId→Table Trace`의 역할입니다. 이제 이 구조를 더 내려가 **CDC와 ETL 분리**에서 다음 경계와 실행책임을 보겠습니다.

---

# 6. CDC와 ETL 분리

## FIG-10-08. CDC와 ETL 분리

```text
Change Data
Source DB → CDC → RDW/Consumer

Bulk Data
Source → ETL → ADW
```

CDC와 ETL은 목적이 다릅니다.

CDC는 Change를 빠르게 전달하고, ETL은 대량 변환/적재에 적합합니다. 하나를 다른 목적에 무리하게 사용하면 지연이나 운영복잡도가 커집니다.

CDC Freshness 3s와 30s 충돌은 SLA Tier로 정리해야 합니다.

여기까지가 `CDC와 ETL 분리`의 역할입니다. 이제 이 구조를 더 내려가 **Data Ownership / Direct DML**에서 다음 경계와 실행책임을 보겠습니다.

---

# 7. Data Ownership / Direct DML

## FIG-10-09. Data Ownership / Direct DML

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

여기까지가 `Data Ownership / Direct DML`의 역할입니다. 이제 이 구조를 더 내려가 **Data Evidence**에서 다음 경계와 실행책임을 보겠습니다.

---

# 8. Data Evidence

## FIG-10-10. Data Evidence

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

## FIG-10-11. Normal Pattern

```text
ServiceId→SqlId→Table / Online→RDW / Analysis→ADW
```

정상패턴은 각 영역이 자신의 책임을 유지하면서 명확한 Contract와 Runtime Boundary를 통해 연결되는 구조입니다. 변경·장애·보안·운영 책임이 이 경계를 따라 추적될 수 있어야 합니다.

## FIG-10-12. Forbidden Pattern

```text
Cross-system DML / Heavy Query→Online
```

금지패턴은 기술적으로 불가능해서가 아니라 Architecture의 책임과 Evidence Chain을 무너뜨리기 때문에 제한합니다. 예외가 필요하면 묵시적으로 허용하지 않고 ADR와 Test Evidence로 승인합니다.

---

# Architecture Decision

## FIG-10-13. 주안과 대안

```text
[주안]
RDW/ADW Workload 분리

        VS

[대안]
RDW 중심 통합사용
```

이번 장의 주안은 **RDW/ADW Workload 분리**입니다. 이 방향은 현재 확인된 PDMG 구조와 NSIGHT Target을 연결하면서 책임·운영·Evidence를 가장 일관되게 유지할 수 있는 선택입니다.

대안인 **RDW 중심 통합사용**도 특정 조건에서는 사용할 수 있습니다. 다만 대안을 선택하려면 주안보다 나은 성능·가용성·비용 또는 운영효과가 PoC/Runtime Test로 확인되어야 하고, 그 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---

# Current GAP / PASS

## FIG-10-14. Current GAP

```text
Current
│
├─ datasource mapping
├─ ownership
├─ lineage automation
└─ cdc sla conflict
```

- `[GAP/OPEN]` datasource mapping
- `[GAP/OPEN]` ownership
- `[GAP/OPEN]` lineage automation
- `[GAP/OPEN]` cdc sla conflict

## FIG-10-15. Architecture Assessment

```text
Architecture Definition
 ↓
PASS

Current PDMG Conformance
 ↓
PARTIAL / CONDITIONAL

Runtime Evidence
 ↓
MEDIUM

Architecture PASS
 ≠
Implementation PASS
```

Architecture가 잘 정의되었다는 것과 현재 구현이 그 정의를 지킨다는 것은 별도 판단입니다. 이 문서는 둘을 분리해 평가하며, `[OPEN]`과 `[UNKNOWN]`을 임의로 채우지 않습니다.

---

# Evidence Card

## FIG-10-16. Evidence Chain

```text
Architecture Rule
 ↓
Source / Config
 ↓
Build / Artifact
 ↓
Deployment
 ↓
ServiceId / GUID
 ↓
Metric / Log / Trace / Test
 ↓
Runtime Evidence
 ↓
PASS / GAP / ADR
```

본문에서는 Story와 Architecture 설명을 우선하고, Evidence는 이 카드에서 정리합니다. 앞으로 자동화 단계에서는 이 Chain을 Manifest/Registry로 기계적으로 생성하는 것이 목표입니다.

---

# Chapter Closing Script

## FIG-10-17. 다음 장 Handoff

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

여기까지가 **데이터플랫폼**입니다. 이 장에서 중요한 것은 개별 기술을 많이 보여준 것이 아니라, 전체 그림을 시작점으로 책임과 Runtime을 하나씩 내려가며 설명했다는 점입니다.

이제 자연스럽게 다음 질문이 생깁니다. **배치형 캠페인에서 고객 행동에 반응하는 플랫폼으로**. 그 질문이 다음 단계인 **마케팅플랫폼**의 출발점입니다.
