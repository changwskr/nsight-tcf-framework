# NSIGHT PDMG 아키텍처 정의서
# 제4장. Big Picture
## Story: “화려한 박스가 아니라 책임과 경계를 먼저 본다”


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

Big Picture에서는 세부 Class나 서버 이름을 최대한 지웁니다. 대신 사용자가 어디에서 들어오고, 인증은 어디에서 신뢰를 만들며, 업무 Runtime과 Data는 어디에서 갈라지고, External 연결은 어떤 Contract를 통과해야 하는지를 한 장에 고정합니다. 이후 모든 장은 이 경계를 확대하는 작업입니다.


---

# 1. User와 Channel Boundary

## FIG-04-02. User와 Channel Boundary

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


---

# 2. UI Delivery Boundary

## FIG-04-03. UI Delivery Boundary

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


---

# 3. Authentication Boundary

## FIG-04-04. Authentication Boundary

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


---

# 4. Application Runtime Boundary

## FIG-04-05. Application Runtime Boundary

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


---

# 5. Data Boundary

## FIG-04-06. Data Boundary

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


---

# 6. External Integration Boundary

## FIG-04-07. External Integration Boundary

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


---

# 7. Security와 Observability는 Cross-cutting이다

## FIG-04-08. Security와 Observability는 Cross-cutting이다

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


---

# 8. Big Picture에서 보이는 GAP

## FIG-04-09. Big Picture에서 보이는 GAP

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

## FIG-04-10. Normal Pattern

```text
User→UI/Auth→Runtime→Data / External→Contract
```

정상패턴은 UI·Authentication·Business·Data의 책임을 분리하고 External 연결을 Contract를 통해 통제하는 것입니다.

## FIG-04-11. Forbidden Pattern

```text
Browser→DB / External→Direct DML
```

Browser→DB, UI→DAO, External→PDMG DB DML처럼 책임경계를 뛰어넘는 연결은 Architecture 기본패턴으로 허용하지 않습니다.

---

# Architecture Decision

## FIG-04-12. 주안과 대안

```text
[주안]
책임 고정 + Contract 연결

        VS

[대안]
P2P/Direct 연결 확대
```

Big Picture의 주안은 책임을 공간적으로 고정하고 경계 연결을 Contract로 통제하는 것입니다. 이 구조가 Logical·Security·Interface 설계의 공통 기준이 됩니다.

P2P와 Direct 접근을 확대하면 초기 연결은 쉬우나 변경영향과 장애전파가 빠르게 증가하므로 예외 승인 외에는 기본구조로 사용하지 않습니다.

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

### PASS 전환조건

- `External Interface Inventory`
- `Identity Boundary Closure`
- `Deployment Mapping`
- `pdmg-om Scope`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-04-16. 다음 장 Handoff

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

Big Picture에서 공간적 경계를 고정했습니다. 다음 장에서는 이 책임들을 제품이 아니라 Technical Capability와 Logical Node로 변환해 State·Scale·Failure 관점으로 내려갑니다.
