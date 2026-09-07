# NSIGHT PDMG 아키텍처 정의서
# 제11장. 마케팅플랫폼
## Story: “배치형 캠페인에서 고객 행동에 반응하는 플랫폼으로”


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

Marketing Platform에서는 PDMG를 전체 마케팅 플랫폼과 동일시하지 않습니다. PDMG는 ServiceId 기반의 실행 Runtime Reference이며, Event/Kafka 기반 Real-time Marketing은 NSIGHT Target으로 분리합니다. 두 세계는 명시적인 Mapping Registry와 Interface Contract로 연결합니다.


---

# 1. PDMG Runtime Reference

## FIG-11-02. PDMG Runtime Reference

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


---

# 2. Program과 ServiceId

## FIG-11-03. Program과 ServiceId

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


---

# 3. Business Runtime

## FIG-11-04. Business Runtime

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


---

# 4. Customer Context / Identity

## FIG-11-05. Customer Context / Identity

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


---

# 5. External Interface

## FIG-11-06. External Interface

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


---

# 6. Event/Kafka Target

## FIG-11-07. Event/Kafka Target

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


---

# 7. MP ↔ mg Mapping

## FIG-11-08. MP ↔ mg Mapping

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


---

# 8. Marketing Runtime Evidence

## FIG-11-09. Marketing Runtime Evidence

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

## FIG-11-10. Normal Pattern

```text
MP→Mapping→PDMG ServiceId→Runtime
```

정상패턴은 PDMG Current Runtime과 NSIGHT Marketing Target을 분리하고 공식 Mapping/Interface Contract로만 연결하는 것입니다.

## FIG-11-11. Forbidden Pattern

```text
MP=mg / Target Event=Current
```

`mg`를 `MP`로 자동 치환하거나 NSIGHT Event Target을 PDMG Current 구현으로 표시하지 않습니다.

---

# Architecture Decision

## FIG-11-12. 주안과 대안

```text
[주안]
MP↔PDMG Mapping Registry + ADR

        VS

[대안]
mg를 MP로 일괄 치환
```

NSIGHT `MP`와 PDMG `mg`는 Mapping Registry/ADR로 명시적으로 연결합니다. 이름 유사성보다 책임과 Application Code SSOT가 기준입니다.

일괄 치환은 빠르지만 RD/AD/BI 등 다른 Group과의 Canonical Key 정합을 훼손할 수 있으므로 사용하지 않습니다.

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

### PASS 전환조건

- `MP↔mg Mapping Registry`
- `Identity Binding`
- `Interface Inventory`
- `Event Target Ownership`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-11-16. 다음 장 Handoff

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

Marketing은 실행의 속도를 높이는 영역입니다. 다음 장 BI Portal에서는 같은 Data Platform을 기반으로 실행이 아니라 판단의 속도를 높이는 Analytical Consumer 구조를 봅니다.
