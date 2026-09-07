# NSIGHT PDMG 아키텍처 정의서
# 제5장. 논리 아키텍처
## Story: “기술을 고르기 전에 무엇을 분리하고 허용할지 정한다”
## STORY-FIRST / TEXT-ARCHITECTURE-FIRST / TOP-DOWN → DRILL-DOWN / EVIDENCE-FIRST

> 작성 버전: `REWRITE V2 — Story Quality / Figure-to-Explanation Consistency 보완`  
> 기준일: `2026-09-01`  
> PDMG = Current / Source / Config / Runtime  
> NSIGHT = Target / Alignment / Strategy Reference

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

이제 이 전체 그림을 위에서 아래로 해부하겠습니다. 이번 버전에서는 그림의 박스와 화살표를 직접 설명하고, 반복적인 형식문장은 최소화합니다. 각 Drill-down은 상위 그림의 어느 부분을 확대하는지 명확하게 연결합니다.

## FIG-05-02. Drill-down Route

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

# 1. Application에서 Capability로

## FIG-05-03. Application에서 Capability로

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

여기까지가 `Application에서 Capability로`의 역할입니다. 이제 이 구조를 더 내려가 **Logical Node Catalog**에서 다음 경계와 실행책임을 보겠습니다.

---

# 2. Logical Node Catalog

## FIG-05-04. Logical Node Catalog

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

여기까지가 `Logical Node Catalog`의 역할입니다. 이제 이 구조를 더 내려가 **Application Runtime Node 내부**에서 다음 경계와 실행책임을 보겠습니다.

---

# 3. Application Runtime Node 내부

## FIG-05-05. Application Runtime Node 내부

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

여기까지가 `Application Runtime Node 내부`의 역할입니다. 이제 이 구조를 더 내려가 **State Model**에서 다음 경계와 실행책임을 보겠습니다.

---

# 4. State Model

## FIG-05-06. State Model

```text
UI Delivery       mostly stateless
Authentication      key / refresh state
Application Runtime request/context/TX state
Data Service        persistent state
```

Scale 전략을 정하려면 먼저 State를 알아야 합니다.

Stateless에 가까운 Node는 수평확장이 쉽지만, Session·Key·Transaction State를 가진 Node는 동기화와 Failover 전략이 필요합니다.

Logical 장에서 State를 먼저 정의해야 Physical HA가 설계됩니다.

여기까지가 `State Model`의 역할입니다. 이제 이 구조를 더 내려가 **Scale Unit**에서 다음 경계와 실행책임을 보겠습니다.

---

# 5. Scale Unit

## FIG-05-07. Scale Unit

```text
UI  → delivery instance
Auth → auth instance
App  → JVM/runtime instance
DB   → DB service/node
```

모든 Node가 같은 단위로 Scale하는 것은 아닙니다.

Application Runtime은 JVM Instance가 Scale Unit이 될 수 있고, DB는 DB Service/Node 단위로 봐야 합니다.

이 차이를 무시하면 Thread 수와 Server 수를 같은 Capacity 문제로 보게 됩니다.

여기까지가 `Scale Unit`의 역할입니다. 이제 이 구조를 더 내려가 **Failure Domain**에서 다음 경계와 실행책임을 보겠습니다.

---

# 6. Failure Domain

## FIG-05-08. Failure Domain

```text
UI Failure   → access impact
Auth Failure → login/token impact
App Failure  → transaction impact
DB Failure   → data impact
```

Failure Domain은 장애가 어디까지 번지는지를 정의합니다.

Node를 나누는 이유는 단순한 모듈화가 아니라 장애격리입니다. Authentication 장애와 Business Runtime 장애가 동일한 Failure Domain일 필요는 없습니다.

Physical 장에서 이 Domain을 실제 JVM/VM에 매핑합니다.

여기까지가 `Failure Domain`의 역할입니다. 이제 이 구조를 더 내려가 **Security Boundary**에서 다음 경계와 실행책임을 보겠습니다.

---

# 7. Security Boundary

## FIG-05-09. Security Boundary

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

여기까지가 `Security Boundary`의 역할입니다. 이제 이 구조를 더 내려가 **Logical→Physical Handoff**에서 다음 경계와 실행책임을 보겠습니다.

---

# 8. Logical→Physical Handoff

## FIG-05-10. Logical→Physical Handoff

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

## FIG-05-11. Normal Pattern

```text
Application→Capability→Logical Node→Physical
```

정상패턴은 각 영역이 자신의 책임을 유지하면서 명확한 Contract와 Runtime Boundary를 통해 연결되는 구조입니다. 변경·장애·보안·운영 책임이 이 경계를 따라 추적될 수 있어야 합니다.

## FIG-05-12. Forbidden Pattern

```text
Logical Node=Product / Module=Server
```

금지패턴은 기술적으로 불가능해서가 아니라 Architecture의 책임과 Evidence Chain을 무너뜨리기 때문에 제한합니다. 예외가 필요하면 묵시적으로 허용하지 않고 ADR와 Test Evidence로 승인합니다.

---

# Architecture Decision

## FIG-05-13. 주안과 대안

```text
[주안]
Logical Node 먼저 정의

        VS

[대안]
제품/서버부터 정의
```

이번 장의 주안은 **Logical Node 먼저 정의**입니다. 이 방향은 현재 확인된 PDMG 구조와 NSIGHT Target을 연결하면서 책임·운영·Evidence를 가장 일관되게 유지할 수 있는 선택입니다.

대안인 **제품/서버부터 정의**도 특정 조건에서는 사용할 수 있습니다. 다만 대안을 선택하려면 주안보다 나은 성능·가용성·비용 또는 운영효과가 PoC/Runtime Test로 확인되어야 하고, 그 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---

# Current GAP / PASS

## FIG-05-14. Current GAP

```text
Current
│
├─ integration node current scope
├─ operations node current scope
├─ state/scale evidence
└─ logical→physical mapping
```

- `[GAP/OPEN]` integration node current scope
- `[GAP/OPEN]` operations node current scope
- `[GAP/OPEN]` state/scale evidence
- `[GAP/OPEN]` logical→physical mapping

## FIG-05-15. Architecture Assessment

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

## FIG-05-16. Evidence Chain

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

## FIG-05-17. 다음 장 Handoff

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

여기까지가 **논리 아키텍처**입니다. 이 장에서 중요한 것은 개별 기술을 많이 보여준 것이 아니라, 전체 그림을 시작점으로 책임과 Runtime을 하나씩 내려가며 설명했다는 점입니다.

이제 자연스럽게 다음 질문이 생깁니다. **속도와 안정성을 실제 Center·VM·JVM·DB 구조로 구현한다**. 그 질문이 다음 단계인 **물리 아키텍처**의 출발점입니다.
