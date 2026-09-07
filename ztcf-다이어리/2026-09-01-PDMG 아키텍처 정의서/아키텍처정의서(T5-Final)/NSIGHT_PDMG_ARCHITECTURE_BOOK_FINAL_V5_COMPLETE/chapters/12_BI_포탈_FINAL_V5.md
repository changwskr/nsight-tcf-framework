# NSIGHT PDMG 아키텍처 정의서
# 제12장. BI 포탈
## Story: “데이터 플랫폼이 신뢰를 만들고 BI는 판단 속도를 높인다”


---

# 0. Opening Script

Marketing Platform이 실행을 담당한다면 BI는 판단을 지원합니다.

중요한 것은 BI가 PDMG 내부 DAO나 Online DB를 직접 끌어다 쓰는 구조가 아니라, **Data Platform이 제공하는 신뢰된 Dataset과 Contract를 소비하는 별도 Analytical Boundary**라는 점입니다.

이 장은 PDMG Current보다는 NSIGHT Target Reference 성격이 강합니다.

## FIG-12-01. 장 전체 Architecture

```text
Operational Data
 ↓
RDW
 ↓
ADW
 ↓
Data Contract
 ↓
BI Portal
 ↓
Report / Self-BI / Analysis
```

BI Portal은 Online Runtime의 연장이 아니라 별도의 Analytical Boundary입니다. RDW의 운영 데이터를 ADW와 신뢰된 Dataset으로 가공해 BI가 소비하도록 하고, Heavy Query가 Online Transaction 자원을 침범하지 않도록 분리합니다.


---

# 1. BI Boundary

## FIG-12-02. BI Boundary

```text
PDMG Operational Runtime
        ≠
BI Analytical Runtime
```

BI는 PDMG 내부 Module이 아닙니다.

두 Runtime은 Workload, Scale, Data Access Pattern이 다르기 때문에 별도 Boundary로 봅니다.

이 분리가 Online SLA 보호의 시작입니다.


---

# 2. RDW→ADW

## FIG-12-03. RDW→ADW

```text
RDW
Operational
 ↓
ETL / Data Movement
 ↓
ADW
Analytical
```

BI가 Online RDW를 직접 Heavy Query하면 운영거래에 영향을 줄 수 있습니다.

따라서 분석 Workload는 ADW나 Analytical Platform으로 이동시키는 방향이 기본입니다.

실제 Refresh/Freshness SLA는 별도 Evidence가 필요합니다.


---

# 3. Data Contract

## FIG-12-04. Data Contract

```text
Dataset
├─ Owner
├─ Schema
├─ Version
├─ Freshness
├─ Security
└─ SLA
```

BI와 Data Platform 사이의 Contract는 API보다 Dataset Contract가 중심이 될 수 있습니다.

이 Contract가 없으면 Report마다 같은 지표를 다르게 계산하고 데이터 품질 책임이 불명확해집니다.

따라서 Dataset Owner와 Metric Definition이 중요합니다.


---

# 4. Report / Self-BI

## FIG-12-05. Report / Self-BI

```text
ADW Dataset
 ↓
BI Semantic Layer
 ↓
Report
 ↓
Self-BI
 ↓
Decision
```

BI Portal은 단순 Report Viewer가 아니라 사용자가 신뢰된 Dataset을 재사용하는 소비계층입니다.

Semantic Layer와 공통 Metric Definition이 있어야 Self-BI가 데이터 혼란으로 이어지지 않습니다.

이 영역은 Target 상세설계가 필요합니다.


---

# 5. BI Security

## FIG-12-06. BI Security

```text
User
 ↓
Authentication
 ↓
BI Role
 ↓
Dataset Permission
 ↓
Row / Column Access
 ↓
Audit
```

BI는 데이터를 넓게 보여주기 때문에 Application 로그인만으로 충분하지 않습니다.

Dataset 단위, Row/Column 단위 권한과 Audit이 필요할 수 있습니다.

정확한 권한모델은 BI Platform 선택과 함께 확정해야 합니다.


---

# 6. Freshness SLA

## FIG-12-07. Freshness SLA

```text
Source Change
 ↓
CDC / ETL
 ↓
ADW Refresh
 ↓
Dataset
 ↓
Report Freshness
```

BI에서 '실시간'은 모호한 표현입니다.

Source Change부터 Report에 보이기까지의 전체 Freshness를 측정해야 합니다.

CDC 3초/30초와 같은 SLA 충돌도 최종 사용자 관점의 Freshness로 재정의해야 합니다.


---

# 7. Performance Isolation

## FIG-12-08. Performance Isolation

```text
Heavy BI Query
 ↓
ADW / BI Runtime

not

PDMG WAS / RDW Online
```

분석부하를 Online Runtime과 분리하는 이유는 Performance Isolation입니다.

BI Query가 느리더라도 고객/직원 Online Transaction SLA에 영향을 주지 않아야 합니다.

따라서 Compute/Data Resource도 별도 Scale Unit을 가져야 합니다.


---

# 8. BI Evidence

## FIG-12-09. BI Evidence

```text
Dataset Version
 ↓
Report / Query
 ↓
User / Role
 ↓
Refresh Time
 ↓
Audit / Performance Evidence
```

BI도 운영 가능하려면 Evidence가 필요합니다.

어떤 Dataset Version을 어떤 사용자가 조회했고 얼마나 걸렸는지 추적되어야 Governance와 Performance를 함께 볼 수 있습니다.

현재는 Target Reference 수준이므로 실제 구현 Evidence는 OPEN입니다.

---

# 정상패턴과 금지패턴

## FIG-12-10. Normal Pattern

```text
RDW→ADW→Data Contract→BI
```

정상패턴은 BI가 Data Platform의 신뢰된 Dataset을 소비하고 Online DAO/DB와 직접 결합하지 않는 것입니다.

## FIG-12-11. Forbidden Pattern

```text
BI→PDMG DAO / Heavy Query→Online
```

BI가 PDMG DAO/Mapper나 Online DB에 직접 결합하여 분석부하를 운영거래에 전파하는 구조를 금지합니다.

---

# Architecture Decision

## FIG-12-12. 주안과 대안

```text
[주안]
ADW/Data Contract 기반 BI 연결

        VS

[대안]
PDMG 내부 DAO/DB 직접 연결
```

BI는 ADW/Data Contract 기반으로 연결합니다. 운영 Application 내부에 분석 기능을 붙이는 것이 아니라 신뢰된 Dataset을 독립 소비하는 구조가 목표입니다.

PDMG 내부 DAO/DB 직접 연결은 구현은 빠르지만 Online SLA와 Data Ownership을 동시에 침범하므로 금지대안입니다.

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
BI 포탈 Architecture Rule
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

- `[SOURCE]` Data/Interface Architecture와 NSIGHT BI Target 자료
- `[SOURCE]` PDMG Current에는 BI 구현 직접 Evidence 제한적

### Config Evidence

- `[CONFIG]` Dataset/Freshness/Security Contract는 Target 상세설계 필요

### Runtime / Deployment Evidence

- `[RUNTIME]` RDW→ADW→Dataset→BI Portal
- `[RUNTIME]` Heavy BI Query는 Online Runtime과 분리

### Architecture Decision

- `[DECISION]` ADW/Data Contract 기반 BI
- `[DECISION]` PDMG DAO/DB Direct 접근 금지

### Related ADR

- `ADR-021 RDW/ADW 분리`
- `ADR-035 Observability`

### Current GAP / OPEN

- `[GAP/OPEN]` BI Current Evidence
- `[GAP/OPEN]` Data Contract Inventory
- `[GAP/OPEN]` ADW Freshness
- `[GAP/OPEN]` Dataset Ownership/Security

## 이 장의 판정

```text
Architecture Definition
        ↓
TARGET REFERENCE / CONDITIONAL PASS

Current PDMG Conformance
        ↓
N-A / OPEN

Runtime Evidence Coverage
        ↓
LOW

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `Dataset Contract/Ownership`
- `ADW Freshness SLA`
- `BI Security Model`
- `Target Runtime Evidence`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-12-16. 다음 장 Handoff

```text
BI 포탈
 ↓
데이터 플랫폼이 신뢰를 만들고 BI는 판단 속도를 높인다
 ↓
남은 질문
"좋은 Architecture는 처음 잘 그린 그림이 아니라 계속 정합되는 체계다"
 ↓
표준화와 10년 지속 가능성
```

이제 데이터의 생성·실행·판단까지 연결했습니다. 마지막 장에서는 이 Architecture가 시간이 지나도 무너지지 않도록 Naming, CI, Artifact, Deployment, Evidence, ADR를 하나의 Closed Loop로 묶습니다.
