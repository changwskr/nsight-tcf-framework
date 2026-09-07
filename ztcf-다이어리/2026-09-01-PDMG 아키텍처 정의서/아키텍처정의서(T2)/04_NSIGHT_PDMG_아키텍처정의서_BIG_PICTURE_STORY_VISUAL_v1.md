# NSIGHT PDMG 아키텍처 정의서
# 제4장. Big Picture
## PDMG 전체 System Context와 책임 경계
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **3장의 방법론을 실제 PDMG 전체 구조에 적용해 책임과 경계를 한 장에서 보이게 만든다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제3장에서 넘어온 질문
     ↓
3장의 방법론을 실제 PDMG 전체 구조에 적용해 책임과 경계를 한 장에서 보이게 만든다.
     ↓
이 장이 답해야 할 질문
     ↓
PDMG 전체 System Context와 책임 경계
```

이 장의 핵심 원칙은 다음과 같다.

- 책임은 Application/Technical Boundary 안에 고정한다.
- 연결은 승인된 Contract를 통해 통제한다.
- pdmg-fw는 별도 Remote Business Server로 가정하지 않는다.
- PDMG Current의 UNKNOWN을 Target 기능으로 채우지 않는다.

---

# 1. Big Picture의 목적

## FIG-04-01. Big Picture의 목적

```text
수많은 Source / Module / Server
        ↓
한 장에서 먼저 답할 것
        ↓
누가 호출하는가
무엇을 책임지는가
어디가 경계인가
무엇이 외부인가
```

---

# 2. PDMG System Context

## FIG-04-02. PDMG System Context

```text
User / Browser
      ↓
pdmg-ui
      ↓
pdmg-jwt
      ↓
pdmg-service
      │
      └─ pdmg-fw
           ↓
        Business
           ↓
        RDW / DB

pdmg-om [UNKNOWN]
External [Contract dependent]
```

---

# 3. UI Boundary

## FIG-04-03. UI Boundary

```text
Browser
 ↓
pdmg-ui
 ├─ Screen / Static
 ├─ Transaction Catalog
 ├─ Request Assembly
 └─ Bearer Token
 ↓
pdmg-service
```

---

# 4. Authentication Boundary

## FIG-04-04. Authentication Boundary

```text
Login / SSO
 ↓
pdmg-jwt
 ↓
Access / Refresh
 ↓
JWKS
 ↓
Business Runtime
```

---

# 5. Application Runtime Boundary

## FIG-04-05. Application Runtime Boundary

```text
pdmg-service
┌──────────────────────────┐
│ pdmg-fw                  │
│ Runtime Control          │
│                          │
│ Business Component       │
│ Handler / Facade /       │
│ Service / DAO            │
└────────────┬─────────────┘
             ↓
           Data
```

---

# 6. Data Boundary

## FIG-04-06. Data Boundary

```text
Business Runtime
 ↓
DAO / Mapper
 ↓
JDBC
 ↓
RDW / DB

Other System Data
 ↓
Approved Contract
```

---

# 7. External Integration Boundary

## FIG-04-07. External Integration Boundary

```text
PDMG
 ↓
Approved Interface Contract
 ├─ API
 ├─ Event
 ├─ File
 └─ Data Movement
 ↓
External System

Current inventory
= OPEN / PARTIAL
```

---

# 8. Cross-cutting Security

## FIG-04-08. Cross-cutting Security

```text
User
 ↓
Auth
 ↓
Token
 ↓
Verification
 ↓
Principal
 ↓
Authorization
 ↓
Data
```

---

# 9. Cross-cutting Observability

## FIG-04-09. Cross-cutting Observability

```text
ServiceId
+ GUID
 ↓
Runtime
 ↓
Metric / Log / Trace
 ↓
Operations
```

---

# 10. Big Picture에서 보이는 핵심 GAP

## FIG-04-10. Big Picture에서 보이는 핵심 GAP

```text
Security
RS256 ↔ HMAC [CRITICAL]

Identity Binding [GAP]

External Interface Inventory [OPEN]

pdmg-om [UNKNOWN]

Deployment Mapping [GAP]
```

---

# 11. Big Picture는 세부구조를 숨기되 경계를 숨기지 않는다

## FIG-04-11. Big Picture는 세부구조를 숨기되 경계를 숨기지 않는다

```text
Big Picture
= 적은 박스
+ 명확한 책임
+ 명확한 경계
+ 정상/금지 연결
```

---

# 12. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-04-12. Normal Pattern

```text
User → UI / Auth → Application Runtime
                    ↓
                 Data

External
→ Approved Contract

Security / Observability
= Cross-cutting
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 13. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-04-13. Forbidden Pattern

```text
Browser → DB                   X
UI → DAO / Mapper               X
External → PDMG DB Direct DML   X
Target Component → Current Fact X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 14. Architecture Decision — System Boundary

## FIG-04-14. 주안과 대안

```text
[주안]
책임 고정 + Contract 연결

        VS

[대안]
P2P/Direct 연결 확대
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | 책임 고정 + Contract 연결 | P2P/Direct 연결 확대 |
| 장점 | • 변경격리<br>• 보안/운영 통제<br>• Traceability 향상 | • 초기 구현 단순 |
| 단점 | • Interface Governance 필요 | • 강결합<br>• 장애전파<br>• Ownership 붕괴 |
| 권고 | **주안 채택** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 15. Current PDMG GAP

## FIG-04-15. GAP Map

```text
Current PDMG
│
├─ JWT/Identity Critical GAP
├─ External Interface Inventory OPEN
├─ pdmg-om Current Detail UNKNOWN
└─ Artifact→Host/JVM/WAR Mapping GAP
```

- `[GAP/OPEN]` JWT/Identity Critical GAP
- `[GAP/OPEN]` External Interface Inventory OPEN
- `[GAP/OPEN]` pdmg-om Current Detail UNKNOWN
- `[GAP/OPEN]` Artifact→Host/JVM/WAR Mapping GAP

---

# 16. 제4장 Architecture 판정

## FIG-04-16. Assessment

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
| UI/Auth/Business Boundary | PASS | Source/Runtime 근거 |
| Data Boundary | PASS/PARTIAL | RDW 강한 근거 |
| External Boundary | OPEN | 전수 Interface Inventory 필요 |
| Operations Boundary | OPEN | pdmg-om 상세 UNKNOWN |
| Security | GAP | JWT/Identity Critical |

---

# 17. 원천 정의서 Trace

## FIG-04-17. Source Trace

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
| 01 Executive | Story/Drill-down/Current-Target 근거 |
| 02 System Context | Story/Drill-down/Current-Target 근거 |
| 18 Integrated | Story/Drill-down/Current-Target 근거 |

---

# 18. 다음 장으로 넘어가는 이유

## FIG-04-18. 4장 → 5

```text
제4장
Big Picture
      ↓
"이 책임들을 기술 역할과 Logical Node로 어떻게 구조화할 것인가?"
      ↓
제5장
논리 아키텍처
```

---

# 19. 제4장 최종 결론

## FIG-04-19. Final Story

```text
Big Picture
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

제4장의 결론은 **PDMG 전체 System Context와 책임 경계**라는 한 문장으로 정리된다.
