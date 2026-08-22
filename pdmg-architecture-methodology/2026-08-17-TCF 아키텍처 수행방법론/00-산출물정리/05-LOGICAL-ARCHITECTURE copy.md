# 05. NSIGHT Logical Architecture

## 0. 문서 상태

- 단계: STEP 05 — Logical Architecture
- Gate: G20 Big Picture / Logical
- 상태: **CONDITIONAL PASS**
- 기준일: 2026-08-18
- 목적: NSIGHT의 업무·Application·Framework·Data·Integration 책임을 기술제품이 아닌 **정책과 허용/금지 관계**로 정의한다.

---

## 1. Logical Architecture 핵심 원칙

NSIGHT Logical Architecture의 기준은 다음 6개다.

```text
L1. 업무 책임은 Domain이 소유한다.
L2. 거래 Identity는 ServiceId를 중심으로 관리한다.
L3. Framework와 Business 책임을 분리한다.
L4. Data 변경 책임은 Data Owner Domain에 둔다.
L5. Domain 경계는 공개 Service Contract로 넘는다.
L6. Requirement → Runtime Evidence까지 추적 가능하게 만든다.
```

---

## 2. Business / Application Classification Model

```text
Enterprise / System Group
        ↓
Application Group
        ↓
Business Domain
        ↓
Sub Domain
        ↓
Program
        ↓
Transaction Type
        ↓
ServiceId
```

PDMG 계열 Naming 예시는 다음 축으로 확인된다.

```text
MG
 ↓
CO
 ↓
A
 ↓
9000
 ↓
S0
 ↓
mgcoa9000S0
```

### Logical Key

| Level | 역할 | 예시 |
|---|---|---|
| L1 | 대그룹/System | MG / MP |
| L2 | 업무 Domain | CO / MK 등 |
| L3 | 세부업무 | A/B/C/D |
| L4 | Program | 9000 |
| L5 | Transaction Type | S0/C0/U0/D0 |
| L6 | ServiceId | `mgcoa9000S0` |

> [OPEN] NSIGHT 전체 Application Group 코드와 PDMG 업무 분류코드의 Crosswalk는 전체 Catalog로 완성할 필요가 있다.

---

## 3. Logical Component Model

```text
Inbound Adapter / Controller
          ↓
TCF / Dispatcher
          ↓
Handler
          ↓
Facade
          ↓
Service
      ┌───┼─────────────┐
      ▼   ▼             ▼
    Rule  DAO     Integration Port
           │             │
           ▼             ▼
         Mapper        Client
           │             │
           ▼             ▼
          SQL       External Domain
           │
           ▼
        Owned Data
```

---

## 4. Layer Responsibility

| Layer | 책임 | 입력 | 출력 | 금지 |
|---|---|---|---|---|
| Controller/Entry | HTTP/Inbound 변환, TCF 진입 | Standard Request | ServiceId+DTO | 업무규칙/DAO |
| Dispatcher | ServiceId→Handler Routing | ServiceId | Handler | 업무판단 |
| Handler | Use Case Entry | DTO/Context | Facade Call | DAO/Mapper 직접호출 |
| Facade | 거래/Use Case 조립 경계 | Request DTO | Result | SQL/Mapper |
| Service | 업무 절차·검증·상태변경 | Domain Input | Domain Result | 타 Domain DAO/Table |
| Rule | 업무 판단·계산·정책 | Domain Facts | Decision | I/O 중심 구현 |
| DAO | DB Access Contract | Parameter | Data | 업무 절차 |
| Mapper | SQL Mapping | DAO Contract | SQL Result | Domain Orchestration |
| Integration Port | 외부/Domain Contract 추상화 | Domain Request | Domain Response | 상대 내부 구현 의존 |
| TCF | 거래 생명주기·공통통제 | ServiceId/Context | Business Execution | 업무 Data Ownership |

---

## 5. AS-IS / TO-BE 계층 차이

### PDMG AS-IS

```text
Handler
  ↓
Facade
  ↓
Service
  ├─ Validation
  ├─ 업무 판단
  ├─ Paging
  ├─ DAO 호출
  └─ 결과 조립
  ↓
DAO
  ↓
Mapper
```

### Target Logical Model

```text
Handler
  ↓
Facade
  ↓
Service = Process / Orchestration
  ├─ Rule = Decision / Policy
  ├─ DAO = Persistence Port
  └─ Integration = External Port
```

> [AS-IS] PDMG에서 독립 `Rule` 패키지는 일반화되어 있지 않다.
>
> [TO-BE/PROPOSAL] 복잡한 업무 판단은 Rule로 분리할 수 있으나 모든 Service에 기계적으로 Rule 계층을 강제하지 않는다.

---

## 6. Dependency Direction

기본 의존 방향:

```text
Inbound
   ↓
Handler
   ↓
Facade
   ↓
Service
   ↓
Rule / DAO / Integration Port
   ↓
Infrastructure Adapter
```

### 허용 Matrix

| From \ To | Handler | Facade | Service | Rule | DAO | Mapper | Integration |
|---|---:|---:|---:|---:|---:|---:|---:|
| Controller | O | △ | X | X | X | X | X |
| Handler | - | O | △* | X | X | X | X |
| Facade | X | - | O | △ | X | X | △ |
| Service | X | X | △ | O | O | X | O |
| Rule | X | X | X | △ | X | X | X |
| DAO | X | X | X | X | - | O | X |

`△*`는 AS-IS 변형이 존재할 수 있으나 Target 기준은 Facade를 Use Case 경계로 우선한다.

---

## 7. Domain Ownership Model

각 Domain은 최소 다음을 소유한다.

```text
Domain
├─ Public ServiceId / Contract
├─ Handler / Facade / Service
├─ Domain Rule
├─ DTO / Domain Model
├─ DAO / Mapper
├─ Owned Table / View
├─ Error Code
├─ Authorization Policy
├─ Timeout / SLA
└─ Operational Owner
```

### 필수 Metadata

| Field | 의미 |
|---|---|
| Domain ID | Domain 식별자 |
| Owner | 업무 책임조직/Owner |
| Public ServiceId | 외부 공개 거래 |
| Internal Service | 내부 구현 |
| Owned Data | 소유 Table/View |
| Read Policy | 타 Domain 읽기 정책 |
| Write Policy | 변경 권한 |
| Integration Contract | 연계 계약 |
| Tx Boundary | 로컬 TX 경계 |
| Failure Policy | Timeout/Retry/Compensation |

---

## 8. MG ↔ MK Domain Boundary

자료에서 MG와 MK는 ServiceId·패키지·업무 소유권이 다른 별도 Domain으로 정리돼 있다.

### 정상 호출

```text
MG Service
   ↓
MgToMkPort
   ↓
MgToMkClient
   │
   │ HTTP + Standard Message
   │ targetServiceId = mk...
════════════ DOMAIN BOUNDARY ════════════
   ▼
MK Entry / TCF
   ↓
MK Service
   ↓
MK DAO / Mapper
   ↓
MK Owned Data
```

### Domain Rule

| Rule ID | Rule | 판정 |
|---|---|---|
| R-DOMAIN-001 | Domain은 독립 Business Ownership으로 관리 | 필수 |
| R-DOMAIN-002 | 다른 Domain DAO 직접호출 금지 | 금지 |
| R-DOMAIN-003 | 다른 Domain Mapper 직접호출 금지 | 금지 |
| R-DOMAIN-004 | 다른 Domain Owned Table 직접갱신 금지 | 금지 |
| R-DOMAIN-005 | Domain 간 호출은 Public ServiceId/Contract 사용 | 필수 |
| R-DOMAIN-006 | 별도 WAR 경계는 HTTP+표준전문 사용 | 필수 |
| R-DOMAIN-007 | WAR 간 내부 구현 Java Dependency 금지 | 금지 |
| R-DOMAIN-008 | 순환 동기 호출 금지 | 금지 |
| R-DOMAIN-009 | 호출자는 상대 내부 계층을 알지 않음 | 필수 |
| R-DOMAIN-010 | Data 변경 책임은 Owner Domain | 필수 |

---

## 9. Cross-Domain Transaction Policy

Domain 간 HTTP 연계는 하나의 Spring Local Transaction이 아니다.

```text
MG TX BEGIN
   │
   ├─ MG DB 변경
   │
   ├──── HTTP ─────┐
   │               │
   │           MK TX BEGIN
   │               │
   │           MK DB 변경
   │               │
   │           MK COMMIT
   │◀── Response ──┘
   │
MG COMMIT / ROLLBACK
```

따라서 변경성 연계는 다음 정책이 필요하다.

- Idempotency Key
- 상태 저장
- Retry 제한
- Compensation
- Reconciliation
- 운영 재처리
- 원인 오류 보존

> [DECISION] Domain 간 원자성은 로컬 JDBC Transaction 전파로 해결하지 않는다.

---

## 10. Timeout Budget Policy

Timeout은 각 계층에서 새로 시작하는 숫자가 아니라 상위 Deadline을 소비하는 Budget으로 본다.

```text
Client Deadline
   > Server/Integration Read Timeout
       > Transaction Timeout
           > DB Query Timeout
```

Cross-Domain 예:

```text
MG Total Budget = 5s
MG Internal Used = 2s
Remaining = 3s
   ↓
MK Call Timeout <= 3s
```

> [OPEN] 현재 모든 ServiceId에 Deadline/Timeout Metadata가 등록되어 있는지는 확인이 필요하다.

---

## 11. Standard Message / Context Logical Boundary

```text
Standard Request
├─ Common Header
│    ├─ GUID
│    ├─ ServiceId
│    ├─ User / Branch
│    ├─ System / Screen
│    └─ Client IP / Channel
└─ Business DTO
```

내부 분리:

```text
Common Header → ServiceContext → Framework
Business DTO   → Handler / Facade / Service
```

Logical Rule:

- 전체 표준전문을 DAO까지 전달하지 않는다.
- Service는 공통 Header를 임의 수정하지 않는다.
- 오류 JSON을 Business Service가 직접 조립하지 않는다.
- `ServiceContext`와 `TransactionContext`를 Spring DB Transaction과 동일시하지 않는다.

---

## 12. Security Logical Boundary

```text
SSO / IdP
  ↓
JWT Issuer
  │ Private Key
  ↓
Access / Refresh Token
  ↓
Client
  ↓ Bearer
Gateway / Application Validator
  │ Public Key / JWKS
  ↓
Security Context
  ↓
ServiceId Authorization
  ↓
Business/Data Authorization
```

원칙:

- Private Key는 Token Issuer 경계에 둔다.
- 브라우저 및 업무 WAR에 Private Key 배포 금지.
- Gateway 우회 가능성이 있으면 Application 자체 검증이 필요.
- Authentication과 Authorization을 분리한다.
- 메뉴권한, 기능권한, ServiceId권한, 데이터권한을 구분한다.

---

## 13. Data Logical Architecture

```text
Operational / Source
   ↓ CDC / Event
RDW  ───────────────→ Online / SingleView
 │
 ├─ ETL / Transform
 ▼
ADW  ───────────────→ BI / Analytics / OLAP
```

### Data Policy

| Policy | 내용 |
|---|---|
| D-001 | RDW와 ADW 책임·부하를 분리 |
| D-002 | Online 조회는 RDW 보호 우선 |
| D-003 | 분석성 대용량 처리는 ADW 우선 |
| D-004 | 데이터 변경은 Owner Domain/Process가 수행 |
| D-005 | 타 Domain Table 직접 갱신 금지 |
| D-006 | CDC/ETL/Kafka는 Data Flow Contract로 등록 |

---

## 14. ServiceId as Logical Architecture Key

정방향:

```text
Requirement
→ Menu
→ Screen
→ Event
→ Program ID
→ ServiceId
→ Endpoint
→ Dispatcher
→ Handler
→ Facade
→ Service
→ Rule
→ DAO
→ Mapper / SQL ID
→ Table / View
→ External Interface
→ Application / WAR
→ Tomcat JVM
→ Server
→ Timeout / Control
→ Test
→ Log / Metric / Runtime Evidence
```

역방향:

```text
Table / SQL
→ Mapper
→ DAO
→ Service
→ Facade
→ Handler
→ ServiceId
→ Program / Screen
→ Requirement
```

> [TO-BE] 이 연결이 Architecture Model의 핵심 Relation Set이 된다.

---

## 15. Logical Architecture Policy Catalog

| Policy ID | 정책 | 상태 |
|---|---|---|
| L-APP-001 | Controller는 업무/DB 로직을 소유하지 않음 | TO-BE |
| L-APP-002 | Dispatcher는 ServiceId Routing만 책임 | CONFIRMED |
| L-APP-003 | Handler는 Use Case Entry | CONFIRMED |
| L-APP-004 | Facade는 거래/Use Case 조립 경계 | CURRENT/TO-BE |
| L-APP-005 | Service는 업무 절차 책임 | CONFIRMED |
| L-APP-006 | Rule은 판단/정책 책임, 필요 시 분리 | PROPOSAL/TO-BE |
| L-DATA-001 | DAO가 DB Access Contract 책임 | CONFIRMED |
| L-DATA-002 | Mapper가 SQL Mapping 책임 | CONFIRMED |
| L-DOM-001 | 타 Domain 내부 DAO/Mapper/Table 접근 금지 | DECISION |
| L-INT-001 | Domain 간 공개 Contract/ServiceId 사용 | DECISION |
| L-INT-002 | Cross-Domain Local TX 전파 기대 금지 | DECISION |
| L-SEC-001 | JWT Private Key Issuer Boundary | DECISION |
| L-OBS-001 | GUID+ServiceId End-to-End 추적 | TO-BE |
| L-TRACE-001 | Requirement→Runtime Evidence Traceability | TO-BE |

---

## 16. Logical Anti-Patterns

```text
X  UI → DB 직접 접근
X  Controller → DAO
X  Handler → DAO
X  Service → Mapper
X  Domain A → Domain B DAO/Mapper
X  Domain A → Domain B Table Update
X  WAR A → WAR B 내부 클래스 Project Dependency
X  Cross-Domain 변경을 하나의 Local TX로 간주
X  ServiceId를 원 호출/대상 호출에서 혼용
X  Timeout Budget을 하위 호출마다 리셋
X  업무 Service가 공통 오류 전문을 직접 생성
```

---

## 17. Logical GAP

| GAP ID | 내용 | 영향 | Priority |
|---|---|---|---|
| `G20-LG-001` | 전체 Domain Catalog/Owner 미완성 | 책임 불명확 | P0 |
| `G20-LG-002` | Domain별 Owned Table/View Catalog 미완성 | Data 결합 위험 | P0 |
| `G20-LG-003` | 전체 ServiceId Public/Internal 구분 미완성 | Interface Governance | P0 |
| `G20-LG-004` | Integration Port/Contract Registry 미완성 | 장애/Timeout 관리 | P0 |
| `G20-LG-005` | Rule 계층 적용 기준 ADR 미확정 | 구현 편차 | P1 |
| `G20-LG-006` | Deadline/Timeout Metadata ServiceId 연결 미완성 | Timeout 정합성 | P0 |
| `G20-LG-007` | Screen→ServiceId→SQL→Table 자동 Trace 미완성 | Closed Loop | P0 |

---

## 18. 우선 ADR 후보

1. `ADR-APP-001` — Facade를 Use Case/Transaction 경계로 유지할 범위
2. `ADR-RULE-001` — Rule 계층 분리 기준
3. `ADR-DOMAIN-001` — Cross-Domain Public Contract 표준
4. `ADR-DATA-001` — Cross-Domain Data Ownership/Read Policy
5. `ADR-TIMEOUT-001` — ServiceId Deadline Budget 표준
6. `ADR-TRACE-001` — ServiceId Traceability Model SoT

---

## 19. G20 Logical 판정

**CONDITIONAL PASS**

### 충족

- Application Layer 책임 분리가 정의됨.
- ServiceId 중심 Routing/Trace 모델이 확립됨.
- Domain Boundary의 허용/금지 정책이 구체화됨.
- Cross-Domain Transaction/Timeout 원칙이 정의됨.
- Data/Message/Security 논리 경계를 연결함.

### 조건

- Domain Catalog와 Data Ownership을 실제 전수 목록으로 완성
- Public ServiceId/Contract Registry 생성
- Rule 계층 ADR 확정
- Deadline/Timeout Catalog 연결
- Traceability 자동 추출 모델 구현

---

## 20. 다음 단계 — G30 Physical Architecture

Logical Architecture를 다음 실제 자원에 매핑한다.

```text
Domain / ServiceId
   ↓
Application / WAR
   ↓
Tomcat JVM Instance
   ↓
WAS Server / VM
   ↓
Apache / WEB / L4 / GSLB
   ↓
RDW / ADW / External
   ↓
PROD / DR
```

G30에서는 특히 다음을 확정한다.

- 71대 Master Inventory와 Logical Component 연결
- Apache Instance / Listen / Proxy
- Tomcat JVM / CATALINA_BASE / Port
- JVM Heap / Thread / Hikari
- Application/WAR 배치
- DB/Integration 연결
- HA Group / DR Pair
- Center/Zone/Residual Capacity
