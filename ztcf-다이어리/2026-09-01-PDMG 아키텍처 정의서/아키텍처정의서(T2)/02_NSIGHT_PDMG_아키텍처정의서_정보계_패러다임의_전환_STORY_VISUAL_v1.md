# NSIGHT PDMG 아키텍처 정의서
# 제2장. 정보계 패러다임의 전환
## Application 중심에서 Runtime·Platform·Data·Evidence 중심으로
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **1장에서 확인한 단편적 시스템 설명을, 책임과 Runtime이 연결된 정보계 Architecture 관점으로 전환한다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제1장에서 넘어온 질문
     ↓
1장에서 확인한 단편적 시스템 설명을, 책임과 Runtime이 연결된 정보계 Architecture 관점으로 전환한다.
     ↓
이 장이 답해야 할 질문
     ↓
Application 중심에서 Runtime·Platform·Data·Evidence 중심으로
```

이 장의 핵심 원칙은 다음과 같다.

- Module Boundary ≠ Process Boundary ≠ Spring Context Boundary ≠ Physical Server.
- 제품명보다 Technical Capability와 Responsibility를 먼저 정의한다.
- PDMG Current와 NSIGHT Target을 같은 것으로 쓰지 않는다.
- 온라인 FAST와 분석 DEEP Workload를 분리한다.

---

# 1. 과거 정보계는 Application을 중심으로 설명했다

## FIG-02-01. 과거 정보계는 Application을 중심으로 설명했다

```text
과거 관점

User
 ↓
Application
 ↓
Database

Application
= 화면
+ 업무 Java
+ SQL
```

이 구조는 업무기능을 설명하는 데는 충분했지만, 대규모 정보계에서 중요한
Thread, Transaction, Security, Interface, Deployment, Observability를 한 구조로 설명하기 어렵다.

---

# 2. PDMG를 보면 Application만으로는 설명되지 않는 것이 보인다

## FIG-02-02. PDMG를 보면 Application만으로는 설명되지 않는 것이 보인다

```text
PDMG

pdmg-ui
   ↓
pdmg-jwt
   ↓
pdmg-service
   │
   ├─ pdmg-fw
   │   ├─ Filter
   │   ├─ Context
   │   ├─ TCF
   │   ├─ Timeout
   │   ├─ Transaction
   │   └─ Logging
   │
   └─ Business
       ↓
      Data
```

PDMG는 이미 Application Source와 Runtime Platform의 성격을 동시에 가지고 있다.

---

# 3. 첫 번째 전환 — Application에서 Responsibility로

## FIG-02-03. 첫 번째 전환 — Application에서 Responsibility로

```text
Application 이름
   ↓
"무슨 책임을 가지는가?"
   ↓
UI Delivery
Authentication
Business Runtime
Framework Control
Data Access
Operations
```

---

# 4. 두 번째 전환 — Module에서 Runtime Boundary로

## FIG-02-04. 두 번째 전환 — Module에서 Runtime Boundary로

```text
Build Module
pdmg-service
pdmg-fw
     ↓
same Spring Runtime 가능
     ↓
Module Boundary
≠
Process Boundary
≠
JVM Boundary
```

---

# 5. 세 번째 전환 — 제품에서 Capability로

## FIG-02-05. 세 번째 전환 — 제품에서 Capability로

```text
Tomcat
 ↓
"무슨 역할?"
 ↓
Application Runtime

HikariCP
 ↓
"무슨 역할?"
 ↓
Connection Pool

MyBatis
 ↓
"무슨 역할?"
 ↓
SQL Mapping
```

제품은 바뀔 수 있지만 Capability와 책임은 Architecture의 더 안정적인 축이다.

---

# 6. 네 번째 전환 — 온라인 Application에서 FAST/DEEP Workload로

## FIG-02-06. 네 번째 전환 — 온라인 Application에서 FAST/DEEP Workload로

```text
PDMG Current
ONLINE / FAST
 HTTP
 Worker
 TX
 RDW

NSIGHT Broader
FAST
 Event / CDC

DEEP
 ETL / ADW / BI
```

---

# 7. 다섯 번째 전환 — Server 중심에서 Logical→Physical Mapping으로

## FIG-02-07. 다섯 번째 전환 — Server 중심에서 Logical→Physical Mapping으로

```text
Application
 ↓
Logical Technical Node
 ↓
Runtime Type
 ↓
Environment
 ↓
VM / JVM / WAR
 ↓
Host / Center
```

---

# 8. 여섯 번째 전환 — 로그 중심에서 Runtime Evidence로

## FIG-02-08. 여섯 번째 전환 — 로그 중심에서 Runtime Evidence로

```text
Log
 ↓
무슨 일이 있었나

            VS

Architecture Rule
 ↓
Metric / Trace / Test
 ↓
설계가 지켜졌는가
```

---

# 9. 일곱 번째 전환 — 수동 문서에서 Closed Loop로

## FIG-02-09. 일곱 번째 전환 — 수동 문서에서 Closed Loop로

```text
Document
 ↓
Model
 ↓
Code
 ↓
Test
 ↓
Runtime Evidence
 ↓
Drift
 ↓
ADR
 ↓
New Baseline
```

---

# 10. PDMG Current와 NSIGHT Target의 관계

## FIG-02-10. PDMG Current와 NSIGHT Target의 관계

```text
PDMG CURRENT
UI / JWT / TCF / Worker / TX / DB
        │
        │ compare / map
        ▼
NSIGHT TARGET
Scalable / Resilient / Data-Centric
RDW/ADW / Event/CDC / HA/DR / Evidence
```

---

# 11. 패러다임 전환의 최종 형태

## FIG-02-11. 패러다임 전환의 최종 형태

```text
Application-centric
     ↓
Responsibility-centric
     ↓
Runtime-aware
     ↓
Data-aware
     ↓
Operation-aware
     ↓
Evidence-backed
```

---

# 12. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-02-12. Normal Pattern

```text
Business Responsibility
 ↓
Application
 ↓
Technical Capability
 ↓
Runtime / Physical
 ↓
Operations / Evidence
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 13. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-02-13. Forbidden Pattern

```text
Module = Server               X
Product = Architecture Node     X
PDMG Current = NSIGHT Target    X
Online = Analytics Runtime      X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 14. Architecture Decision — PDMG Architecture 범위

## FIG-02-14. 주안과 대안

```text
[주안]
Application + Runtime + Operations + Evidence

        VS

[대안]
Application Source 중심
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | Application + Runtime + Operations + Evidence | Application Source 중심 |
| 장점 | • 운영/보안/성능까지 연결<br>• Current/Target 차이 설명 가능<br>• Runtime 검증 가능 | • 개발자 Source 이해가 빠름<br>• 초기 문서 단순 |
| 단점 | • 문서 범위 증가<br>• 모델/책임 분류 필요 | • Physical/Runtime 설명 한계<br>• 운영/보안 단절 |
| 권고 | **주안 채택** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 15. Current PDMG GAP

## FIG-02-15. GAP Map

```text
Current PDMG
│
├─ Module→Runtime/Physical 전수 Mapping 미완료
├─ Event/CDC/ETL Current 범위 일부 OPEN
├─ pdmg-om Current 역할 UNKNOWN
└─ Runtime Evidence 자동화 미완료
```

- `[GAP/OPEN]` Module→Runtime/Physical 전수 Mapping 미완료
- `[GAP/OPEN]` Event/CDC/ETL Current 범위 일부 OPEN
- `[GAP/OPEN]` pdmg-om Current 역할 UNKNOWN
- `[GAP/OPEN]` Runtime Evidence 자동화 미완료

---

# 16. 제2장 Architecture 판정

## FIG-02-16. Assessment

```text
Architecture Definition
      ↓
PASS

Current PDMG Conformance
      ↓
PARTIAL

Runtime Evidence
      ↓
MEDIUM
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| Responsibility 전환 | PASS | Application/Framework/Data 책임 구분 |
| Runtime 인식 | PASS | Thread/TX/Timeout 구조 확인 |
| Current/Target 분리 | PASS | PDMG≠NSIGHT 전체 |
| Operations | PARTIAL | pdmg-om/운영 구현 상세 OPEN |
| Evidence | PARTIAL | 자동화 미완료 |

---

# 17. 원천 정의서 Trace

## FIG-02-17. Source Trace

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
| 03 Application | Story/Drill-down/Current-Target 근거 |
| 04 Logical | Story/Drill-down/Current-Target 근거 |
| 18 Integrated | Story/Drill-down/Current-Target 근거 |

---

# 18. 다음 장으로 넘어가는 이유

## FIG-02-18. 2장 → 3

```text
제2장
정보계 패러다임의 전환
      ↓
"이 새로운 관점을 어떤 순서로 설계해야 하는가?"
      ↓
제3장
아키텍처 6단계 수립 방법론
```

---

# 19. 제2장 최종 결론

## FIG-02-19. Final Story

```text
정보계 패러다임의 전환
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

제2장의 결론은 **Application 중심에서 Runtime·Platform·Data·Evidence 중심으로**라는 한 문장으로 정리된다.
