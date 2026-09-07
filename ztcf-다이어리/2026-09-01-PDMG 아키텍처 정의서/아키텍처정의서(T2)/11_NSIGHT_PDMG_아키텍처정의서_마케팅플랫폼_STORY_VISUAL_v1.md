# NSIGHT PDMG 아키텍처 정의서
# 제11장. 마케팅플랫폼
## PDMG를 Marketing Platform 실행 Reference로 정의
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **10장의 Data Platform 위에서 Marketing Application이 실행될 때 PDMG가 어떤 Runtime Reference 역할을 하는지 정의한다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제10장에서 넘어온 질문
     ↓
10장의 Data Platform 위에서 Marketing Application이 실행될 때 PDMG가 어떤 Runtime Reference 역할을 하는지 정의한다.
     ↓
이 장이 답해야 할 질문
     ↓
PDMG를 Marketing Platform 실행 Reference로 정의
```

이 장의 핵심 원칙은 다음과 같다.

- PDMG는 Marketing Platform의 실행 Reference이지 전체 Target Platform과 동일하지 않다.
- `mg` Source Prefix와 `MP` Application Group은 Mapping Registry/ADR 없이 동일시하지 않는다.
- Event/Kafka/Real-time Target을 PDMG Current로 자동 승격하지 않는다.
- Program/ServiceId를 Marketing Runtime Trace의 핵심축으로 사용한다.

---

# 1. PDMG는 Marketing Platform 전체가 아니라 실행 Reference다

## FIG-11-01. PDMG는 Marketing Platform 전체가 아니라 실행 Reference다

```text
NSIGHT Marketing Platform
        ↓
Business / Application Scope
        ↓
PDMG Reference
UI / JWT / Framework / Service Runtime
```

---

# 2. PDMG Module 구조

## FIG-11-02. PDMG Module 구조

```text
pdmg-ui
pdmg-jwt
pdmg-service
  └─ pdmg-fw
pdmg-om? [UNKNOWN]
```

---

# 3. Marketing Business는 Program/ServiceId로 실행된다

## FIG-11-03. Marketing Business는 Program/ServiceId로 실행된다

```text
Business
 ↓
Program
mgcoa9001
 ↓
ServiceId
mgcoa9001S0
 ↓
Handler
 ↓
Business Runtime
```

---

# 4. Source Business Axis

## FIG-11-04. Source Business Axis

```text
mg | co | a | 9001
│    │    │    │
│    │    │    └ Program No
│    │    └ Function
│    └ Business
└ Application/Major
```

---

# 5. NSIGHT Application Group MP와 PDMG mg는 자동 동일하지 않다

## FIG-11-05. NSIGHT Application Group MP와 PDMG mg는 자동 동일하지 않다

```text
NSIGHT Target
MP
 │
 │ Mapping Registry / ADR
 ▼
PDMG AS-IS
mg

MP ≠ mg
unless approved
```

---

# 6. 마케팅 실행경로

## FIG-11-06. 마케팅 실행경로

```text
UI
 ↓
ServiceId
 ↓
Handler / Facade
 ↓
Service
 ↓
DAO / RDW
 ↓
Result
```

---

# 7. Marketing Platform의 실시간 Target과 PDMG Current를 구분한다

## FIG-11-07. Marketing Platform의 실시간 Target과 PDMG Current를 구분한다

```text
Target Marketing
Event / Kafka / Real-time Decision
        │
        │ reference
        ▼
PDMG Current
HTTP / TCF / TX / RDW
```

---

# 8. 외부 연계는 Marketing Business 내부코드가 아니라 Contract로 분리한다

## FIG-11-08. 외부 연계는 Marketing Business 내부코드가 아니라 Contract로 분리한다

```text
Marketing Service
 ↓
Interface Contract
 ↓
API / Event / File
 ↓
External Platform
```

---

# 9. Security는 Marketing Platform의 공통 기반이다

## FIG-11-09. Security는 Marketing Platform의 공통 기반이다

```text
User
 ↓
JWT / SSO
 ↓
Principal
 ↓
Marketing ServiceId
 ↓
Authorization
```

---

# 10. Marketing Platform의 Runtime Evidence

## FIG-11-10. Marketing Platform의 Runtime Evidence

```text
Campaign / User Action
 ↓
ServiceId
 ↓
GUID
 ↓
Business Runtime
 ↓
Data / External Call
 ↓
Evidence
```

---

# 11. PDMG를 Target에 맞추는 방식

## FIG-11-11. PDMG를 Target에 맞추는 방식

```text
PDMG AS-IS
 ↓
Mapping Registry
 ↓
GAP
 ↓
ADR
 ↓
Target Alignment
```

---

# 12. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-11-12. Normal Pattern

```text
NSIGHT MP
 ↓
Approved Mapping
 ↓
PDMG Program / ServiceId
 ↓
Business Runtime
 ↓
RDW / Approved Interface
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 13. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-11-13. Forbidden Pattern

```text
MP = mg 자동치환                   X
Target Event/Kafka = Current PDMG      X
Marketing Platform 전체 = pdmg-service X
External 연계 = 내부 DB Direct          X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 14. Architecture Decision — MP↔PDMG 코드 Mapping

## FIG-11-14. 주안과 대안

```text
[주안]
Mapping Registry + ADR

        VS

[대안]
`mg`를 `MP`로 일괄 치환
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | Mapping Registry + ADR | `mg`를 `MP`로 일괄 치환 |
| 장점 | • AS-IS 보존<br>• Traceability<br>• 단계적 전환 | • 표면상 코드 통일 |
| 단점 | • Registry 관리 필요 | • Source/운영 ID 영향<br>• 충돌/역추적 문제 |
| 권고 | **주안 채택** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 15. Current PDMG GAP

## FIG-11-15. GAP Map

```text
Current PDMG
│
├─ MP↔mg 공식 Mapping 미승인
├─ Marketing External Interface Inventory OPEN
├─ JWT Security Critical GAP
├─ pdmg-om 범위 UNKNOWN
└─ Event/Kafka Current Ownership OPEN
```

- `[GAP/OPEN]` MP↔mg 공식 Mapping 미승인
- `[GAP/OPEN]` Marketing External Interface Inventory OPEN
- `[GAP/OPEN]` JWT Security Critical GAP
- `[GAP/OPEN]` pdmg-om 범위 UNKNOWN
- `[GAP/OPEN]` Event/Kafka Current Ownership OPEN

---

# 16. 제11장 Architecture 판정

## FIG-11-16. Assessment

```text
Architecture Definition
      ↓
CONDITIONAL PASS

Current PDMG Conformance
      ↓
PARTIAL

Runtime Evidence
      ↓
MEDIUM
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| PDMG Runtime Reference | PASS | UI/JWT/service/fw 근거 |
| Program/ServiceId | PASS | Current naming/registry |
| MP Mapping | OPEN | Registry/ADR 필요 |
| Real-time Target | REFERENCE | PDMG Current로 승격 금지 |
| Security | GAP | JWT/Identity |

---

# 17. 원천 정의서 Trace

## FIG-11-17. Source Trace

```text
Story Chapter
   ↓
PDMG 00~18 Source
   ↓
Current Fact / Target Reference
   ↓
Architecture Rule / GAP
```

| 원천 | 용도 |
|---|---|
| 03 Application | Story/Drill-down/Current-Target 근거 |
| 06 Interface | Story/Drill-down/Current-Target 근거 |
| 11 Security | Story/Drill-down/Current-Target 근거 |
| 15 Naming | Story/Drill-down/Current-Target 근거 |

---

# 18. 다음 장으로 넘어가는 이유

## FIG-11-18. 11장 → 12

```text
제11장
마케팅플랫폼
      ↓
"Marketing과 Operational Data를 분석하는 BI는 PDMG와 어떤 경계로 연결되어야 하는가?"
      ↓
제12장
BI 포탈
```

---

# 19. 제11장 최종 결론

## FIG-11-19. Final Story

```text
마케팅플랫폼
   ↓
Responsibility
   ↓
Boundary
   ↓
Runtime
   ↓
Evidence
   ↓
CONDITIONAL PASS
```

제11장의 결론은 **PDMG를 Marketing Platform 실행 Reference로 정의**라는 한 문장으로 정리된다.
