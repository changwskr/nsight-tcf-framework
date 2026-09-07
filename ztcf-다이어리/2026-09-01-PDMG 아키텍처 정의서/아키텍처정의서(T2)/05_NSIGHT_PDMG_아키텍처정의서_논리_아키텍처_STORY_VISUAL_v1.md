# NSIGHT PDMG 아키텍처 정의서
# 제5장. 논리 아키텍처
## Application Responsibility를 Logical Technical Node로 변환
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **4장에서 정한 책임경계를 제품이나 서버로 바로 내리지 않고 기술역할과 Logical Node로 변환한다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제4장에서 넘어온 질문
     ↓
4장에서 정한 책임경계를 제품이나 서버로 바로 내리지 않고 기술역할과 Logical Node로 변환한다.
     ↓
이 장이 답해야 할 질문
     ↓
Application Responsibility를 Logical Technical Node로 변환
```

이 장의 핵심 원칙은 다음과 같다.

- Application ≠ Logical Technical Node.
- Build Module ≠ Logical Technical Node.
- Logical Node ≠ Physical Host.
- 모든 Logical Node는 State/Scale/Failure/Security 속성을 가진다.

---

# 1. Big Picture만으로 Physical을 바로 결정하면 안 된다

## FIG-05-01. Big Picture만으로 Physical을 바로 결정하면 안 된다

```text
Application
 ↓
Server

X

Application
 ↓
Technical Capability
 ↓
Logical Node
 ↓
Physical
```

---

# 2. Application Responsibility를 기술 역할로 변환한다

## FIG-05-02. Application Responsibility를 기술 역할로 변환한다

```text
pdmg-ui      → UI Delivery
pdmg-jwt     → Authentication
pdmg-service → Business Runtime
pdmg-fw      → Runtime Control
RDW/DB       → Data Service
```

---

# 3. PDMG Logical Node Set

## FIG-05-03. PDMG Logical Node Set

```text
LTN-PD-01 UI Delivery
LTN-PD-02 Authentication
LTN-PD-03 Application Runtime
LTN-PD-04 Data Service
LTN-PD-05 Integration [CONDITIONAL]
LTN-PD-06 Operations [OPEN]
```

---

# 4. Application Runtime Node 내부

## FIG-05-04. Application Runtime Node 내부

```text
LTN-PD-03
Application Runtime
│
├─ Framework Capability
│  ├─ Filter / Context
│  ├─ Security
│  ├─ TCF
│  ├─ Worker / Timeout
│  └─ Transaction / Error
│
└─ Business Capability
   ├─ Handler / Controller
   ├─ Facade
   ├─ Service
   └─ DAO
```

---

# 5. Logical Node는 제품명이 아니다

## FIG-05-05. Logical Node는 제품명이 아니다

```text
Logical Role
Application Runtime
      ↓
Technology Component
WAS / JVM
      ↓
Current Product
Tomcat / Java

Role ≠ Product
```

---

# 6. State를 정의해야 Scale이 보인다

## FIG-05-06. State를 정의해야 Scale이 보인다

```text
UI Delivery
mostly stateless

Authentication
key / refresh state

Application Runtime
request / context / TX state

Data Service
persistent state
```

---

# 7. Scale Unit를 정의한다

## FIG-05-07. Scale Unit를 정의한다

```text
UI → Delivery Instance
Auth → Auth Instance
App → Runtime/JVM Instance
DB → DB Service/Node
Ops → Collector/Control Instance
```

---

# 8. Failure Domain을 정의한다

## FIG-05-08. Failure Domain을 정의한다

```text
UI Failure
→ access impact

Auth Failure
→ login/token impact

App Runtime Failure
→ business transaction impact

DB Failure
→ query/DML impact
```

---

# 9. Security Boundary를 Logical 구조에 포함한다

## FIG-05-09. Security Boundary를 Logical 구조에 포함한다

```text
Untrusted Client
 ↓
Auth Boundary
 ↓
Application Boundary
 ↓
Business Authorization
 ↓
Data Boundary
```

---

# 10. Allowed / Forbidden Path

## FIG-05-10. Allowed / Forbidden Path

```text
Allowed
UI → App Runtime → Data Access → DB

Forbidden
Client → DB
UI → DB
External → DB DML
```

---

# 11. Logical Architecture의 마지막은 Physical Handoff다

## FIG-05-11. Logical Architecture의 마지막은 Physical Handoff다

```text
Logical Node
 ↓
Runtime Characteristic
 ↓
State / Scale / Failure
 ↓
Allowed Path
 ↓
Physical Mapping
```

---

# 12. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-05-12. Normal Pattern

```text
Application
 ↓
Technical Capability
 ↓
Logical Node
 ↓
Runtime Type
 ↓
State / Scale / Failure / Security
 ↓
Physical Handoff
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 13. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-05-13. Forbidden Pattern

```text
pdmg-fw = 독립 Remote Server   X
Logical Node = Tomcat 10.x      X
Application Code = Hostname     X
Unknown = Current Fact          X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 14. Architecture Decision — Framework Runtime Placement

## FIG-05-14. 주안과 대안

```text
[주안]
pdmg-service와 in-process

        VS

[대안]
pdmg-fw Remote Runtime
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | pdmg-service와 in-process | pdmg-fw Remote Runtime |
| 장점 | • Current Source 정합<br>• Network hop 없음<br>• TX/Context 연계 단순 | • 독립 Scale 가능<br>• Process 격리 |
| 단점 | • 동일 Process Failure Domain | • Current 구조와 불일치<br>• RPC/지연/계약 추가 |
| 권고 | **주안 채택** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 15. Current PDMG GAP

## FIG-05-15. GAP Map

```text
Current PDMG
│
├─ LTN-PD-05 Integration Current 범위 OPEN
├─ LTN-PD-06 Operations/pdmg-om UNKNOWN
├─ State/Scale/HA Runtime Evidence 미완료
└─ Logical→Physical 전수 Mapping 미완료
```

- `[GAP/OPEN]` LTN-PD-05 Integration Current 범위 OPEN
- `[GAP/OPEN]` LTN-PD-06 Operations/pdmg-om UNKNOWN
- `[GAP/OPEN]` State/Scale/HA Runtime Evidence 미완료
- `[GAP/OPEN]` Logical→Physical 전수 Mapping 미완료

---

# 16. 제5장 Architecture 판정

## FIG-05-16. Assessment

```text
Architecture Definition
      ↓
PASS

Current PDMG Conformance
      ↓
CONDITIONAL

Runtime Evidence
      ↓
MEDIUM
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| Logical Node Set | PASS | 6개 Node/조건부 Node 정의 |
| Framework Placement | PASS AS-IS | service+fw 동일 Runtime 가능 |
| State/Scale/Failure | PARTIAL | 실측 Evidence 필요 |
| Integration/OM | OPEN | Current 범위 미확정 |
| Physical Handoff | GAP | 전수 Mapping 필요 |

---

# 17. 원천 정의서 Trace

## FIG-05-17. Source Trace

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
| 04 Logical | Story/Drill-down/Current-Target 근거 |
| 06 Interface | Story/Drill-down/Current-Target 근거 |
| 07 Data | Story/Drill-down/Current-Target 근거 |

---

# 18. 다음 장으로 넘어가는 이유

## FIG-05-18. 5장 → 6

```text
제5장
논리 아키텍처
      ↓
"Logical Node를 실제 Center·Host·VM·JVM·WAR에 어떻게 배치할 것인가?"
      ↓
제6장
물리 아키텍처
```

---

# 19. 제5장 최종 결론

## FIG-05-19. Final Story

```text
논리 아키텍처
   ↓
Responsibility
   ↓
Boundary
   ↓
Runtime
   ↓
Evidence
   ↓
PASS
```

제5장의 결론은 **Application Responsibility를 Logical Technical Node로 변환**라는 한 문장으로 정리된다.
