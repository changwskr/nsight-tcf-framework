# NSIGHT PDMG 아키텍처 정의서
# 제4장. Big Picture
## Story: “화려한 박스가 아니라 책임과 경계를 먼저 본다”
## STORY-FIRST / TEXT-ARCHITECTURE-FIRST / TOP-DOWN → DRILL-DOWN / EVIDENCE-FIRST

> 최종 문서 Edition: `FINAL V4 — Story + Architecture + Drill-down + Evidence in Chapter`  
> 기준일: `2026-09-01`  
> PDMG = Current / Source / Config / Runtime  
> NSIGHT = Target / Alignment / Strategy Reference  
> 문서 상태: **FINAL V4 WORKING BASELINE** / Evidence는 본 장 내부에서 Architecture와 함께 판정

---

# 0. Opening Script

이제 전체 시스템을 한 장으로 봅니다. 여기서 박스를 많이 넣는 것이 목적이 아닙니다.

Big Picture의 역할은 **사용자가 어디에서 들어오고, 인증은 어디에서 끝나며, 업무 Runtime과 Data는 어디에서 분리되고, External 연결은 어떤 경계를 통과하는가**를 한눈에 보여주는 것입니다.

이 장이 이후 Logical·Physical·Security·Interface 장의 공간적 기준이 됩니다.

## FIG-04-01. 장 전체 Architecture

```text
User / Browser
      ↓
UI Delivery
      ↓
Authentication
      ↓
Application Runtime
      ↓
Data

External → Approved Contract
Security / Observability = Cross-cutting
```

이제 이 전체 그림을 위에서 아래로 해부하겠습니다. 이번 버전에서는 그림의 박스와 화살표를 직접 설명하고, 반복적인 형식문장은 최소화합니다. 각 Drill-down은 상위 그림의 어느 부분을 확대하는지 명확하게 연결합니다.

## FIG-04-02. Drill-down Route

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

# 1. User와 Channel Boundary

## FIG-04-03. User와 Channel Boundary

```text
User
 ↓
Browser / Channel
 ↓
Access Boundary
 ↓
pdmg-ui / pdmg-jwt / pdmg-service
```

첫 번째 경계는 사용자와 시스템 사이입니다. Browser는 업무를 시작하지만 Business Logic을 소유하지 않습니다.

UI는 화면과 요청조립을 담당하고, Authentication은 신뢰를 만들며, Business Runtime은 거래를 실행합니다. 세 역할을 같은 Application처럼 보이게 하면 변경영향이 커집니다.

그래서 Big Picture에서부터 UI/Auth/Business를 분리합니다.

여기까지가 `User와 Channel Boundary`의 역할입니다. 이제 이 구조를 더 내려가 **UI Delivery Boundary**에서 다음 경계와 실행책임을 보겠습니다.

---

# 2. UI Delivery Boundary

## FIG-04-04. UI Delivery Boundary

```text
Browser
 ↓
pdmg-ui
 ├─ Screen
 ├─ Transaction Catalog
 └─ Request Assembly
 ↓
Business Call
```

`pdmg-ui`는 Presentation 영역입니다. 화면, 정적자원, Transaction Catalog, Request Assembly를 담당합니다.

여기서 중요한 금지사항은 UI가 DAO나 DB를 직접 보는 것입니다. UI가 Business/Data Boundary를 넘으면 Layering과 Security가 동시에 무너집니다.

따라서 UI의 마지막 책임은 표준 Request를 만들어 Application Runtime으로 전달하는 데 있습니다.

여기까지가 `UI Delivery Boundary`의 역할입니다. 이제 이 구조를 더 내려가 **Authentication Boundary**에서 다음 경계와 실행책임을 보겠습니다.

---

# 3. Authentication Boundary

## FIG-04-05. Authentication Boundary

```text
Login / SSO
 ↓
pdmg-jwt
 ↓
Access / Refresh Token
 ↓
Verification
 ↓
Trusted Principal
```

Authentication은 별도의 Trust Boundary입니다. 로그인이나 SSO를 통과한 결과는 Token이지만, 업무 Runtime에서 다시 검증되어야 합니다.

현재 RS256 Issue와 HMAC Verify Path의 불일치는 이 Boundary가 완전히 닫히지 않았음을 의미합니다.

또한 Authentication과 Authorization은 다르므로 Trusted Principal이 Business User Context로 어떻게 연결되는지도 별도로 검증해야 합니다.

여기까지가 `Authentication Boundary`의 역할입니다. 이제 이 구조를 더 내려가 **Application Runtime Boundary**에서 다음 경계와 실행책임을 보겠습니다.

---

# 4. Application Runtime Boundary

## FIG-04-06. Application Runtime Boundary

```text
pdmg-service
┌──────────────────────────┐
│ pdmg-fw Runtime Control  │
│                          │
│ Handler / Facade         │
│ Service / DAO            │
└────────────┬─────────────┘
             ↓
           Data
```

PDMG의 중심 실행영역은 `pdmg-service`입니다. 이 안에서 `pdmg-fw` Framework Bean과 Business Bean이 협력할 수 있습니다.

따라서 `pdmg-fw`를 Remote Server로 그리는 것은 현재 Evidence와 맞지 않습니다. Build Module과 Runtime Boundary는 다릅니다.

이 Boundary 안에서 Framework는 실행정책을, Business는 Use Case를 담당합니다.

여기까지가 `Application Runtime Boundary`의 역할입니다. 이제 이 구조를 더 내려가 **Data Boundary**에서 다음 경계와 실행책임을 보겠습니다.

---

# 5. Data Boundary

## FIG-04-07. Data Boundary

```text
Business
 ↓
DAO / Mapper
 ↓
JDBC
 ↓
RDW / DB

External Data
 ↓
Approved Contract
```

Data Boundary에서는 내부 Data Access와 외부 시스템 Data를 분리합니다.

PDMG Current에서 DAO→Mapper→JDBC→RDW/DB는 비교적 강한 Evidence가 있습니다. 반면 ADW 사용이나 Cross-system Data Access는 Inventory를 통해 확인해야 합니다.

External System의 DB에 직접 DML하는 구조는 기본 정상패턴으로 보지 않습니다.

여기까지가 `Data Boundary`의 역할입니다. 이제 이 구조를 더 내려가 **External Integration Boundary**에서 다음 경계와 실행책임을 보겠습니다.

---

# 6. External Integration Boundary

## FIG-04-08. External Integration Boundary

```text
PDMG
 ↓
Interface Contract
 ├─ API
 ├─ Event
 ├─ File
 └─ Data Movement
 ↓
External System
```

외부 연결은 선 하나가 아니라 Contract입니다. Source/Target, Schema, Security, Timeout, Retry, Version, Operations가 함께 있어야 합니다.

PDMG Current에서 어떤 Interface가 실제 존재하는지는 전수 Inventory가 필요합니다. Kafka/CDC/ETL이 NSIGHT Target에 있다는 이유로 PDMG Current Component로 넣지 않습니다.

이 구분이 6장 Interface Architecture와 10장 Data Platform으로 이어집니다.

여기까지가 `External Integration Boundary`의 역할입니다. 이제 이 구조를 더 내려가 **Security와 Observability는 Cross-cutting이다**에서 다음 경계와 실행책임을 보겠습니다.

---

# 7. Security와 Observability는 Cross-cutting이다

## FIG-04-09. Security와 Observability는 Cross-cutting이다

```text
Security
 ↓ all boundaries

GUID / ServiceId
 ↓
Runtime
 ↓
Metric / Log / Trace
 ↓
Operations
```

Security와 Observability는 한쪽 구석의 박스가 아닙니다. 모든 Boundary를 가로지릅니다.

Security는 요청이 이동할 때마다 Trust를 유지해야 하고, Observability는 ServiceId/GUID를 기준으로 같은 거래를 끝까지 추적해야 합니다.

그래서 Big Picture에서부터 Cross-cutting으로 표시합니다.

여기까지가 `Security와 Observability는 Cross-cutting이다`의 역할입니다. 이제 이 구조를 더 내려가 **Big Picture에서 보이는 GAP**에서 다음 경계와 실행책임을 보겠습니다.

---

# 8. Big Picture에서 보이는 GAP

## FIG-04-10. Big Picture에서 보이는 GAP

```text
CRITICAL
JWT / Identity

HIGH
Interface Inventory
Deployment Mapping

OPEN
pdmg-om
ADW current mapping
```

Big Picture는 세부내용을 숨기지만 GAP는 숨기지 않습니다.

현재 가장 중요한 것은 Security, External Interface Inventory, Deployment Mapping, pdmg-om Scope입니다. 이 항목들이 뒤 장에서 구체화됩니다.

Big Picture의 역할은 문제가 어디에 있는지 공간적으로 먼저 보여주는 것입니다.

---

# 정상패턴과 금지패턴

## FIG-04-11. Normal Pattern

```text
User→UI/Auth→Runtime→Data / External→Contract
```

정상패턴은 각 영역이 자신의 책임을 유지하면서 명확한 Contract와 Runtime Boundary를 통해 연결되는 구조입니다. 변경·장애·보안·운영 책임이 이 경계를 따라 추적될 수 있어야 합니다.

## FIG-04-12. Forbidden Pattern

```text
Browser→DB / External→Direct DML
```

금지패턴은 기술적으로 불가능해서가 아니라 Architecture의 책임과 Evidence Chain을 무너뜨리기 때문에 제한합니다. 예외가 필요하면 묵시적으로 허용하지 않고 ADR와 Test Evidence로 승인합니다.

---

# Architecture Decision

## FIG-04-13. 주안과 대안

```text
[주안]
책임 고정 + Contract 연결

        VS

[대안]
P2P/Direct 연결 확대
```

이번 장의 주안은 **책임 고정 + Contract 연결**입니다. 이 방향은 현재 확인된 PDMG 구조와 NSIGHT Target을 연결하면서 책임·운영·Evidence를 가장 일관되게 유지할 수 있는 선택입니다.

대안인 **P2P/Direct 연결 확대**도 특정 조건에서는 사용할 수 있습니다. 다만 대안을 선택하려면 주안보다 나은 성능·가용성·비용 또는 운영효과가 PoC/Runtime Test로 확인되어야 하고, 그 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---

# Current GAP / PASS

## FIG-04-14. Current GAP

```text
Current
│
├─ JWT/identity
├─ interface inventory
├─ pdmg-om
└─ deployment mapping
```

- `[GAP/OPEN]` JWT/identity
- `[GAP/OPEN]` interface inventory
- `[GAP/OPEN]` pdmg-om
- `[GAP/OPEN]` deployment mapping

## FIG-04-15. Architecture Assessment

```text
Architecture Definition
 ↓
PASS

Current PDMG Conformance
 ↓
CONDITIONAL / PARTIAL

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

## FIG-04-16. Evidence Chain

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
Big Picture Architecture Rule
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

- `[SOURCE]` pdmg-ui / pdmg-jwt / pdmg-service / pdmg-fw 경계 분석
- `[SOURCE]` RDW/DB Current Data Boundary

### Config Evidence

- `[CONFIG]` Security Filter / HTTP / Application Runtime 설정

### Runtime / Deployment Evidence

- `[RUNTIME]` User→UI→Auth→Application Runtime→Data
- `[RUNTIME]` External은 Approved Contract로만 연결

### Architecture Decision

- `[DECISION]` 책임을 공간에 고정하고 연결을 경계에서 통제

### Related ADR

- `ADR-013 Identity Binding`
- `ADR-014 Interface Selection`
- `ADR-036 OM Control Plane`

### Current GAP / OPEN

- `[GAP/OPEN]` JWT/Identity
- `[GAP/OPEN]` External Interface Inventory
- `[GAP/OPEN]` pdmg-om
- `[GAP/OPEN]` Deployment Mapping

## 이 장의 판정

```text
Architecture Definition
        ↓
PASS

Current PDMG Conformance
        ↓
CONDITIONAL / PARTIAL

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

## FIG-04-17. 다음 장 Handoff

```text
Big Picture
 ↓
화려한 박스가 아니라 책임과 경계를 먼저 본다
 ↓
남은 질문
"기술을 고르기 전에 무엇을 분리하고 허용할지 정한다"
 ↓
논리 아키텍처
```

여기까지가 **Big Picture**입니다. 이 장에서 중요한 것은 개별 기술을 많이 보여준 것이 아니라, 전체 그림을 시작점으로 책임과 Runtime을 하나씩 내려가며 설명했다는 점입니다.

이제 자연스럽게 다음 질문이 생깁니다. **기술을 고르기 전에 무엇을 분리하고 허용할지 정한다**. 그 질문이 다음 단계인 **논리 아키텍처**의 출발점입니다.
