# NSIGHT PDMG 아키텍처 정의서
# 제12장. BI 포탈
## Story: “데이터 플랫폼이 신뢰를 만들고 BI는 판단 속도를 높인다”
## STORY-FIRST / TEXT-ARCHITECTURE-FIRST / TOP-DOWN → DRILL-DOWN / EVIDENCE-FIRST

> 최종 문서 Edition: `FINAL V4 — Story + Architecture + Drill-down + Evidence in Chapter`  
> 기준일: `2026-09-01`  
> PDMG = Current / Source / Config / Runtime  
> NSIGHT = Target / Alignment / Strategy Reference  
> 문서 상태: **FINAL V4 WORKING BASELINE** / Evidence는 본 장 내부에서 Architecture와 함께 판정

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

이제 이 전체 그림을 위에서 아래로 해부하겠습니다. 이번 버전에서는 그림의 박스와 화살표를 직접 설명하고, 반복적인 형식문장은 최소화합니다. 각 Drill-down은 상위 그림의 어느 부분을 확대하는지 명확하게 연결합니다.

## FIG-12-02. Drill-down Route

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

# 1. BI Boundary

## FIG-12-03. BI Boundary

```text
PDMG Operational Runtime
        ≠
BI Analytical Runtime
```

BI는 PDMG 내부 Module이 아닙니다.

두 Runtime은 Workload, Scale, Data Access Pattern이 다르기 때문에 별도 Boundary로 봅니다.

이 분리가 Online SLA 보호의 시작입니다.

여기까지가 `BI Boundary`의 역할입니다. 이제 이 구조를 더 내려가 **RDW→ADW**에서 다음 경계와 실행책임을 보겠습니다.

---

# 2. RDW→ADW

## FIG-12-04. RDW→ADW

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

여기까지가 `RDW→ADW`의 역할입니다. 이제 이 구조를 더 내려가 **Data Contract**에서 다음 경계와 실행책임을 보겠습니다.

---

# 3. Data Contract

## FIG-12-05. Data Contract

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

여기까지가 `Data Contract`의 역할입니다. 이제 이 구조를 더 내려가 **Report / Self-BI**에서 다음 경계와 실행책임을 보겠습니다.

---

# 4. Report / Self-BI

## FIG-12-06. Report / Self-BI

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

여기까지가 `Report / Self-BI`의 역할입니다. 이제 이 구조를 더 내려가 **BI Security**에서 다음 경계와 실행책임을 보겠습니다.

---

# 5. BI Security

## FIG-12-07. BI Security

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

여기까지가 `BI Security`의 역할입니다. 이제 이 구조를 더 내려가 **Freshness SLA**에서 다음 경계와 실행책임을 보겠습니다.

---

# 6. Freshness SLA

## FIG-12-08. Freshness SLA

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

여기까지가 `Freshness SLA`의 역할입니다. 이제 이 구조를 더 내려가 **Performance Isolation**에서 다음 경계와 실행책임을 보겠습니다.

---

# 7. Performance Isolation

## FIG-12-09. Performance Isolation

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

여기까지가 `Performance Isolation`의 역할입니다. 이제 이 구조를 더 내려가 **BI Evidence**에서 다음 경계와 실행책임을 보겠습니다.

---

# 8. BI Evidence

## FIG-12-10. BI Evidence

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

## FIG-12-11. Normal Pattern

```text
RDW→ADW→Data Contract→BI
```

정상패턴은 각 영역이 자신의 책임을 유지하면서 명확한 Contract와 Runtime Boundary를 통해 연결되는 구조입니다. 변경·장애·보안·운영 책임이 이 경계를 따라 추적될 수 있어야 합니다.

## FIG-12-12. Forbidden Pattern

```text
BI→PDMG DAO / Heavy Query→Online
```

금지패턴은 기술적으로 불가능해서가 아니라 Architecture의 책임과 Evidence Chain을 무너뜨리기 때문에 제한합니다. 예외가 필요하면 묵시적으로 허용하지 않고 ADR와 Test Evidence로 승인합니다.

---

# Architecture Decision

## FIG-12-13. 주안과 대안

```text
[주안]
ADW/Data Contract 기반 BI 연결

        VS

[대안]
PDMG 내부 DAO/DB 직접 연결
```

이번 장의 주안은 **ADW/Data Contract 기반 BI 연결**입니다. 이 방향은 현재 확인된 PDMG 구조와 NSIGHT Target을 연결하면서 책임·운영·Evidence를 가장 일관되게 유지할 수 있는 선택입니다.

대안인 **PDMG 내부 DAO/DB 직접 연결**도 특정 조건에서는 사용할 수 있습니다. 다만 대안을 선택하려면 주안보다 나은 성능·가용성·비용 또는 운영효과가 PoC/Runtime Test로 확인되어야 하고, 그 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---

# Current GAP / PASS

## FIG-12-14. Current GAP

```text
Current
│
├─ bi current evidence
├─ data contract inventory
├─ adw freshness
└─ dataset ownership/security
```

- `[GAP/OPEN]` bi current evidence
- `[GAP/OPEN]` data contract inventory
- `[GAP/OPEN]` adw freshness
- `[GAP/OPEN]` dataset ownership/security

## FIG-12-15. Architecture Assessment

```text
Architecture Definition
 ↓
TARGET REFERENCE / CONDITIONAL PASS

Current PDMG Conformance
 ↓
N-A / OPEN

Runtime Evidence
 ↓
LOW

Architecture PASS
 ≠
Implementation PASS
```

Architecture가 잘 정의되었다는 것과 현재 구현이 그 정의를 지킨다는 것은 별도 판단입니다. 이 문서는 둘을 분리해 평가하며, `[OPEN]`과 `[UNKNOWN]`을 임의로 채우지 않습니다.

---

# Evidence Card

## FIG-12-16. Evidence Chain

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

이 장의 정의가 완료되었다고 해서 현재 구현이 자동으로 PASS가 되는 것은 아니다. Source·Config·Runtime Evidence가 실제 Architecture Rule과 정합할 때 Current Conformance를 PASS로 승격한다.

---
# Chapter Closing Script

## FIG-12-17. 다음 장 Handoff

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

여기까지가 **BI 포탈**입니다. 이 장에서 중요한 것은 개별 기술을 많이 보여준 것이 아니라, 전체 그림을 시작점으로 책임과 Runtime을 하나씩 내려가며 설명했다는 점입니다.

이제 자연스럽게 다음 질문이 생깁니다. **좋은 Architecture는 처음 잘 그린 그림이 아니라 계속 정합되는 체계다**. 그 질문이 다음 단계인 **표준화와 10년 지속 가능성**의 출발점입니다.
