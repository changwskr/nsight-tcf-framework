# NSIGHT PDMG 아키텍처 정의서
# 제5장. 논리 아키텍처
## Story: “기술을 고르기 전에 무엇을 분리하고 허용할지 정한다”


---

# 0. Opening Script

Big Picture에서 책임을 나눴다면 이제 그 책임을 기술적으로 실행할 구조가 필요합니다.

논리 아키텍처는 Tomcat, Kafka, Oracle 같은 제품을 고르는 단계가 아닙니다. **어떤 Technical Capability가 필요하고, 어떤 Logical Node로 분리하며, 각 Node가 어떤 State·Scale·Failure Domain을 가져야 하는지**를 정하는 단계입니다.

## FIG-05-01. 장 전체 Architecture

```text
Application Responsibility
       ↓
Technical Capability
       ↓
Logical Technical Node
       ↓
Runtime Type
       ↓
State / Scale / Failure / Security
```

논리 아키텍처는 제품선정 전에 필요한 기술역할을 결정합니다. UI Delivery, Authentication, Application Runtime, Data Service 같은 Node를 만들고 State·Scale·Failure·Security 속성을 부여합니다. 이 속성이 Physical 설계의 입력이 됩니다.


---

# 1. Application에서 Capability로

## FIG-05-02. Application에서 Capability로

```text
pdmg-ui      → UI Delivery
pdmg-jwt     → Authentication
pdmg-service → Business Runtime
pdmg-fw      → Runtime Control
DB           → Data Service
```

Application 이름을 바로 Physical Server로 내리지 않고 먼저 Capability로 번역합니다.

이렇게 하면 현재 제품을 바꾸더라도 Architecture의 책임은 유지할 수 있습니다.

Capability가 Logical Node를 만드는 재료가 됩니다.


---

# 2. Logical Node Catalog

## FIG-05-03. Logical Node Catalog

```text
LTN-PD-01 UI Delivery
LTN-PD-02 Authentication
LTN-PD-03 Application Runtime
LTN-PD-04 Data Service
LTN-PD-05 Integration [CONDITIONAL]
LTN-PD-06 Operations [OPEN]
```

Logical Node는 실행책임과 Failure Domain을 표현하는 논리 단위입니다.

PDMG Current Evidence가 강한 UI/Auth/App/Data Node와 달리 Integration/Operations는 Current 범위가 완전히 확인되지 않았으므로 조건부/OPEN으로 둡니다.

Unknown을 Target으로 채우지 않는 것이 중요합니다.


---

# 3. Application Runtime Node 내부

## FIG-05-04. Application Runtime Node 내부

```text
Application Runtime
├─ Framework Runtime
│  ├─ Filter / Context
│  ├─ Security
│  ├─ TCF
│  ├─ Worker / Timeout
│  └─ Transaction / Error
└─ Business Runtime
   ├─ Handler / Controller
   ├─ Facade
   ├─ Service
   └─ DAO
```

Application Runtime Node는 다시 Framework와 Business로 나뉩니다.

Framework는 실행방법을, Business는 업무내용을 책임집니다. 이 분리를 지키면 TCF ON/OFF가 바뀌어도 Business Core를 동일하게 유지할 수 있습니다.

이 구조가 8장 Mechanism의 논리적 기반입니다.


---

# 4. State Model

## FIG-05-05. State Model

```text
UI Delivery       mostly stateless
Authentication      key / refresh state
Application Runtime request/context/TX state
Data Service        persistent state
```

Scale 전략을 정하려면 먼저 State를 알아야 합니다.

Stateless에 가까운 Node는 수평확장이 쉽지만, Session·Key·Transaction State를 가진 Node는 동기화와 Failover 전략이 필요합니다.

Logical 장에서 State를 먼저 정의해야 Physical HA가 설계됩니다.


---

# 5. Scale Unit

## FIG-05-06. Scale Unit

```text
UI  → delivery instance
Auth → auth instance
App  → JVM/runtime instance
DB   → DB service/node
```

모든 Node가 같은 단위로 Scale하는 것은 아닙니다.

Application Runtime은 JVM Instance가 Scale Unit이 될 수 있고, DB는 DB Service/Node 단위로 봐야 합니다.

이 차이를 무시하면 Thread 수와 Server 수를 같은 Capacity 문제로 보게 됩니다.


---

# 6. Failure Domain

## FIG-05-07. Failure Domain

```text
UI Failure   → access impact
Auth Failure → login/token impact
App Failure  → transaction impact
DB Failure   → data impact
```

Failure Domain은 장애가 어디까지 번지는지를 정의합니다.

Node를 나누는 이유는 단순한 모듈화가 아니라 장애격리입니다. Authentication 장애와 Business Runtime 장애가 동일한 Failure Domain일 필요는 없습니다.

Physical 장에서 이 Domain을 실제 JVM/VM에 매핑합니다.


---

# 7. Security Boundary

## FIG-05-08. Security Boundary

```text
Untrusted
 ↓
Authentication
 ↓
Application Runtime
 ↓
Business Authorization
 ↓
Data
```

Logical Security는 방화벽보다 먼저 Trust 흐름을 정의합니다.

사용자 입력은 Untrusted에서 시작하고, Authentication을 통해 Principal을 만들고, Business Authorization과 Data Authorization을 별도로 적용해야 합니다.

이 논리경계를 실제 Security Filter/JWT/DB 권한으로 구현하는 것은 이후 Mechanism/Runtime 장의 책임입니다.


---

# 8. Logical→Physical Handoff

## FIG-05-09. Logical→Physical Handoff

```text
Logical Node
 ↓
State / Scale / Failure
 ↓
Availability Requirement
 ↓
VM / JVM / WAR Mapping
```

논리 아키텍처의 마지막 질문은 '이 Node를 실제 어디에 배치할 것인가'입니다.

Physical 장은 Logical Node의 속성을 받아 실제 VM/JVM/WAR를 결정합니다.

즉 Logical이 없다면 Physical은 서버 배치표가 되고, Logical이 있으면 Physical은 설계의 구현이 됩니다.

---

# 정상패턴과 금지패턴

## FIG-05-10. Normal Pattern

```text
Application→Capability→Logical Node→Physical
```

정상패턴은 Application 책임을 Logical Node로 변환한 뒤 State·Scale·Failure 특성을 정의하고 Physical 설계로 넘기는 것입니다.

## FIG-05-11. Forbidden Pattern

```text
Logical Node=Product / Module=Server
```

Logical Node를 제품명이나 Build Module과 동일시하면 State와 Failure Domain을 표현할 수 없으므로 금지합니다.

---

# Architecture Decision

## FIG-05-12. 주안과 대안

```text
[주안]
Logical Node 먼저 정의

        VS

[대안]
제품/서버부터 정의
```

Logical Node를 제품보다 먼저 정의합니다. State·Scale·Failure·Security 속성을 기술한 뒤 제품과 Physical Resource를 선택해야 기술변경에도 Architecture Intent가 유지됩니다.

제품/서버부터 정의하면 현재 기술에 설계가 고정되고 Capability와 Failure Domain이 제품명 뒤에 숨게 됩니다.

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
논리 아키텍처 Architecture Rule
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

- `[SOURCE]` Application/Module 상세정의
- `[SOURCE]` Technical Architecture Definition
- `[SOURCE]` PDMG Logical 상세정의

### Config Evidence

- `[CONFIG]` Runtime State/Scale/Failure 속성은 Config/Deployment와 연결 필요

### Runtime / Deployment Evidence

- `[RUNTIME]` UI/Auth/Application/Data Logical Node 분리

### Architecture Decision

- `[DECISION]` Logical Node를 Product보다 먼저 정의

### Related ADR

- `ADR-004 Common Facade`
- `ADR-028 JVM Isolation`
- `ADR-029 Capacity Budget`

### Current GAP / OPEN

- `[GAP/OPEN]` Integration Node Current Scope
- `[GAP/OPEN]` Operations Node Current Scope
- `[GAP/OPEN]` State/Scale Evidence
- `[GAP/OPEN]` Logical→Physical Mapping

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

- `Logical Node→Physical Mapping`
- `Integration/Operations Node Evidence`
- `State/Scale 검증`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-05-16. 다음 장 Handoff

```text
논리 아키텍처
 ↓
기술을 고르기 전에 무엇을 분리하고 허용할지 정한다
 ↓
남은 질문
"속도와 안정성을 실제 Center·VM·JVM·DB 구조로 구현한다"
 ↓
물리 아키텍처
```

논리적으로 무엇을 분리할지 정했으므로 다음 질문은 실제 어디에서 실행할 것인가입니다. 6장에서는 Logical Node를 Center·VM·JVM·WAR·DB로 투영합니다.
