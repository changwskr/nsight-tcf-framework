# NSIGHT PDMG 아키텍처 정의서
# 제11장. 마케팅플랫폼
## Story: “배치형 캠페인에서 고객 행동에 반응하는 플랫폼으로”
## STORY-FIRST / TEXT-ARCHITECTURE-FIRST / TOP-DOWN → DRILL-DOWN / EVIDENCE-FIRST

> 최종 문서 Edition: `FINAL V4 — Story + Architecture + Drill-down + Evidence in Chapter`  
> 기준일: `2026-09-01`  
> PDMG = Current / Source / Config / Runtime  
> NSIGHT = Target / Alignment / Strategy Reference  
> 문서 상태: **FINAL V4 WORKING BASELINE** / Evidence는 본 장 내부에서 Architecture와 함께 판정

---

# 0. Opening Script

데이터가 준비되면 그 데이터를 실제 고객 접점에서 사용하는 실행플랫폼이 필요합니다.

이 장에서 PDMG는 Marketing Platform 전체를 의미하지 않습니다. PDMG는 **UI·JWT·Framework·ServiceId 기반 Business Runtime을 보여주는 Current Reference**입니다.

NSIGHT의 Event/Kafka/Real-time Target은 별도 Target으로 분리해 설명합니다.

## FIG-11-01. 장 전체 Architecture

```text
NSIGHT Marketing Platform
        ↓
Approved Mapping
        ↓
PDMG Reference
UI / JWT / Framework / ServiceId
        ↓
Business Runtime
        ↓
RDW / Interface

Target Extension
Event / Kafka / Real-time Decision
```

이제 이 전체 그림을 위에서 아래로 해부하겠습니다. 이번 버전에서는 그림의 박스와 화살표를 직접 설명하고, 반복적인 형식문장은 최소화합니다. 각 Drill-down은 상위 그림의 어느 부분을 확대하는지 명확하게 연결합니다.

## FIG-11-02. Drill-down Route

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

# 1. PDMG Runtime Reference

## FIG-11-03. PDMG Runtime Reference

```text
pdmg-ui
pdmg-jwt
pdmg-service
 └─ pdmg-fw
pdmg-om [UNKNOWN]
```

PDMG는 Marketing Application의 실행패턴을 보여주는 중요한 Reference입니다.

다만 `pdmg-om`은 Current 구현범위가 충분히 확인되지 않았고, Marketing Platform 전체 기능을 PDMG로 대표할 수는 없습니다.

그래서 Current Reference라는 표현을 유지합니다.

여기까지가 `PDMG Runtime Reference`의 역할입니다. 이제 이 구조를 더 내려가 **Program과 ServiceId**에서 다음 경계와 실행책임을 보겠습니다.

---

# 2. Program과 ServiceId

## FIG-11-04. Program과 ServiceId

```text
mg | co | a | 9001
 ↓
mgcoa9001
 ↓
mgcoa9001S0
 ↓
Handler / Use Case
```

Marketing Runtime의 Source Trace는 Program과 ServiceId에서 시작합니다.

Program ID는 업무코드 축을 반영하고 ServiceId는 거래유형까지 포함합니다.

이 ID는 Registry, Logging, SQL Trace까지 연결됩니다.

여기까지가 `Program과 ServiceId`의 역할입니다. 이제 이 구조를 더 내려가 **Business Runtime**에서 다음 경계와 실행책임을 보겠습니다.

---

# 3. Business Runtime

## FIG-11-05. Business Runtime

```text
UI
 ↓
ServiceId
 ↓
Handler
 ↓
Facade
 ↓
Service
 ↓
DAO / RDW
```

현재 PDMG의 강점은 ServiceId에서 Business Layer와 Data Access까지 이어지는 실행축입니다.

TCF ON에서는 Handler가 Facade를 향하고, Target에서는 TCF OFF Controller도 같은 Facade를 향하도록 정렬하는 것이 주안입니다.

이렇게 해야 Marketing Use Case의 경계가 Entry Mechanism에 따라 달라지지 않습니다.

여기까지가 `Business Runtime`의 역할입니다. 이제 이 구조를 더 내려가 **Customer Context / Identity**에서 다음 경계와 실행책임을 보겠습니다.

---

# 4. Customer Context / Identity

## FIG-11-06. Customer Context / Identity

```text
JWT Principal
 ↓
Business User Context
 ↓
Customer / Session Context
 ↓
ServiceId Execution
```

마케팅은 고객맥락을 많이 사용하기 때문에 Identity Binding이 중요합니다.

JWT의 `sub/ssoId`와 Header의 사용자정보를 신뢰 없이 합치면 잘못된 고객문맥이 업무에 들어갈 수 있습니다.

Security GAP를 Marketing Runtime의 핵심조건으로 봅니다.

여기까지가 `Customer Context / Identity`의 역할입니다. 이제 이 구조를 더 내려가 **External Interface**에서 다음 경계와 실행책임을 보겠습니다.

---

# 5. External Interface

## FIG-11-07. External Interface

```text
Marketing Service
 ↓
Interface Contract
 ↓
API / Event / File
 ↓
External Platform
```

Marketing은 여러 외부/내부 시스템과 연결될 가능성이 높습니다.

하지만 연결선이 많아질수록 P2P 강결합 위험이 커집니다. 그래서 Interface Contract를 통해 호출/이벤트/파일을 분리합니다.

Current Inventory가 없으면 특정 연계기술을 AS-IS로 단정하지 않습니다.

여기까지가 `External Interface`의 역할입니다. 이제 이 구조를 더 내려가 **Event/Kafka Target**에서 다음 경계와 실행책임을 보겠습니다.

---

# 6. Event/Kafka Target

## FIG-11-08. Event/Kafka Target

```text
Customer Action
 ↓
Event
 ↓
Kafka / Event Platform
 ↓
Consumer / Decision
 ↓
Response / Offering

[NSIGHT TARGET]
```

실시간 마케팅 Target은 고객행동을 Event로 처리하는 방향을 가질 수 있습니다.

하지만 이 구조가 PDMG Current Source에 구현되어 있다고 자동으로 말하지 않습니다.

Current HTTP/TCF Runtime과 Target Event Runtime을 명확히 분리합니다.

여기까지가 `Event/Kafka Target`의 역할입니다. 이제 이 구조를 더 내려가 **MP ↔ mg Mapping**에서 다음 경계와 실행책임을 보겠습니다.

---

# 7. MP ↔ mg Mapping

## FIG-11-09. MP ↔ mg Mapping

```text
NSIGHT Target
MP
 │
 │ Mapping Registry / ADR
 ▼
PDMG AS-IS
mg

MP ≠ mg automatically
```

이것은 Naming이 아니라 Architecture Governance 문제입니다.

NSIGHT의 `MP`와 PDMG Source의 `mg`는 이름이 비슷해도 동일한 식별자라고 자동 가정하지 않습니다.

공식 Mapping Registry와 ADR가 필요합니다.

여기까지가 `MP ↔ mg Mapping`의 역할입니다. 이제 이 구조를 더 내려가 **Marketing Runtime Evidence**에서 다음 경계와 실행책임을 보겠습니다.

---

# 8. Marketing Runtime Evidence

## FIG-11-10. Marketing Runtime Evidence

```text
Customer Action
 ↓
ServiceId
 ↓
GUID
 ↓
Business / Data / Interface
 ↓
Result
 ↓
Evidence
```

마케팅 실행도 결국 Evidence로 닫아야 합니다.

어떤 고객행동이 어떤 ServiceId와 Data/Interface를 거쳐 어떤 결과를 만들었는지 추적할 수 있어야 합니다.

이 축이 캠페인/추천 품질분석에도 연결될 수 있습니다.

---

# 정상패턴과 금지패턴

## FIG-11-11. Normal Pattern

```text
MP→Mapping→PDMG ServiceId→Runtime
```

정상패턴은 각 영역이 자신의 책임을 유지하면서 명확한 Contract와 Runtime Boundary를 통해 연결되는 구조입니다. 변경·장애·보안·운영 책임이 이 경계를 따라 추적될 수 있어야 합니다.

## FIG-11-12. Forbidden Pattern

```text
MP=mg / Target Event=Current
```

금지패턴은 기술적으로 불가능해서가 아니라 Architecture의 책임과 Evidence Chain을 무너뜨리기 때문에 제한합니다. 예외가 필요하면 묵시적으로 허용하지 않고 ADR와 Test Evidence로 승인합니다.

---

# Architecture Decision

## FIG-11-13. 주안과 대안

```text
[주안]
MP↔PDMG Mapping Registry + ADR

        VS

[대안]
mg를 MP로 일괄 치환
```

이번 장의 주안은 **MP↔PDMG Mapping Registry + ADR**입니다. 이 방향은 현재 확인된 PDMG 구조와 NSIGHT Target을 연결하면서 책임·운영·Evidence를 가장 일관되게 유지할 수 있는 선택입니다.

대안인 **mg를 MP로 일괄 치환**도 특정 조건에서는 사용할 수 있습니다. 다만 대안을 선택하려면 주안보다 나은 성능·가용성·비용 또는 운영효과가 PoC/Runtime Test로 확인되어야 하고, 그 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---

# Current GAP / PASS

## FIG-11-14. Current GAP

```text
Current
│
├─ mp↔mg mapping
├─ jwt/identity
├─ interface inventory
├─ event current ownership
└─ pdmg-om
```

- `[GAP/OPEN]` mp↔mg mapping
- `[GAP/OPEN]` jwt/identity
- `[GAP/OPEN]` interface inventory
- `[GAP/OPEN]` event current ownership
- `[GAP/OPEN]` pdmg-om

## FIG-11-15. Architecture Assessment

```text
Architecture Definition
 ↓
CONDITIONAL PASS

Current PDMG Conformance
 ↓
PARTIAL / GAP

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

## FIG-11-16. Evidence Chain

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
마케팅플랫폼 Architecture Rule
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

- `[SOURCE]` PDMG Application/ServiceId Source
- `[SOURCE]` Application Code Definition: MP/RD/AD/BI/DG/IM
- `[SOURCE]` PDMG mg Source Naming

### Config Evidence

- `[CONFIG]` Program/ServiceId Registry
- `[CONFIG]` JWT/Identity Runtime Config

### Runtime / Deployment Evidence

- `[RUNTIME]` Program→ServiceId→Handler→Facade→Service→DAO/RDW
- `[RUNTIME]` Event/Kafka는 Target Reference

### Architecture Decision

- `[DECISION]` NSIGHT MP ↔ PDMG mg는 Mapping Registry/ADR로 연결
- `[DECISION]` 자동 치환 금지

### Related ADR

- `ADR-003 Mapping Registry`
- `ADR-013 Identity Binding`
- `ADR-014 Interface Selection`

### Current GAP / OPEN

- `[GAP/OPEN]` MP↔mg Mapping
- `[GAP/OPEN]` JWT/Identity
- `[GAP/OPEN]` External Interface Inventory
- `[GAP/OPEN]` Event Current Ownership
- `[GAP/OPEN]` pdmg-om

## 이 장의 판정

```text
Architecture Definition
        ↓
CONDITIONAL PASS

Current PDMG Conformance
        ↓
PARTIAL / GAP

Runtime Evidence Coverage
        ↓
MEDIUM

Architecture Definition PASS
        ≠
Current Implementation PASS
```

이 장의 정의가 완료되었다고 해서 현재 구현이 자동으로 PASS가 되는 것은 아니다. Source·Config·Runtime Evidence가 실제 Architecture Rule과 정합할 때 Current Conformance를 PASS로 승격한다.

---
# Chapter Closing Script

## FIG-11-17. 다음 장 Handoff

```text
마케팅플랫폼
 ↓
배치형 캠페인에서 고객 행동에 반응하는 플랫폼으로
 ↓
남은 질문
"데이터 플랫폼이 신뢰를 만들고 BI는 판단 속도를 높인다"
 ↓
BI 포탈
```

여기까지가 **마케팅플랫폼**입니다. 이 장에서 중요한 것은 개별 기술을 많이 보여준 것이 아니라, 전체 그림을 시작점으로 책임과 Runtime을 하나씩 내려가며 설명했다는 점입니다.

이제 자연스럽게 다음 질문이 생깁니다. **데이터 플랫폼이 신뢰를 만들고 BI는 판단 속도를 높인다**. 그 질문이 다음 단계인 **BI 포탈**의 출발점입니다.
