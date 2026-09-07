# NSIGHT PDMG 아키텍처 정의서
# 제12장. BI 포탈
## PDMG 경계 밖의 분석 소비계층과 Data Contract
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **11장의 Marketing Runtime과 10장의 Data Platform을 소비하는 BI를 별도 Analytical Boundary로 정의한다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제11장에서 넘어온 질문
     ↓
11장의 Marketing Runtime과 10장의 Data Platform을 소비하는 BI를 별도 Analytical Boundary로 정의한다.
     ↓
이 장이 답해야 할 질문
     ↓
PDMG 경계 밖의 분석 소비계층과 Data Contract
```

이 장의 핵심 원칙은 다음과 같다.

- BI Portal은 PDMG Current 내부 Module로 정의하지 않는다.
- Operational Runtime과 Analytical Runtime을 분리한다.
- BI와 PDMG의 연결은 DAO/내부 DB가 아니라 Data Contract다.
- Dataset Ownership/Freshness/Security를 Architecture에 포함한다.

---

# 1. BI Portal은 PDMG 내부 Module이 아니다

## FIG-12-01. BI Portal은 PDMG 내부 Module이 아니다

```text
PDMG
= Operational Application Runtime

BI Portal
= Analytical Consumer

둘은 다른 Application Boundary
```

---

# 2. BI의 기본 Data Flow

## FIG-12-02. BI의 기본 Data Flow

```text
Operational Source
 ↓
RDW
 ↓
ADW
 ↓
BI Portal
 ↓
Report / Self-BI / Analysis
```

---

# 3. Operational과 Analytical Runtime을 분리한다

## FIG-12-03. Operational과 Analytical Runtime을 분리한다

```text
PDMG Online
 ↓
bounded latency
 ↓
RDW

BI / Analytics
 ↓
heavy query
 ↓
ADW
```

---

# 4. BI는 PDMG 내부 DAO를 호출하지 않는다

## FIG-12-04. BI는 PDMG 내부 DAO를 호출하지 않는다

```text
BI Portal
 ↓
Approved Data Contract
 ↓
ADW / Data Service

BI → PDMG DAO
X
```

---

# 5. BI Data Contract

## FIG-12-05. BI Data Contract

```text
InterfaceId / Dataset
Source
Target
Schema
Freshness
Security
Owner
Version
SLA
```

---

# 6. BI에서 필요한 Data Governance

## FIG-12-06. BI에서 필요한 Data Governance

```text
Business Term
 ↓
Data Subject
 ↓
Owner / Steward
 ↓
Metric Definition
 ↓
Dataset
 ↓
BI Report
```

---

# 7. BI 권한은 Application Login만으로 끝나지 않는다

## FIG-12-07. BI 권한은 Application Login만으로 끝나지 않는다

```text
User
 ↓
Authentication
 ↓
BI Role
 ↓
Dataset Permission
 ↓
Column/Row Access
 ↓
Audit
```

---

# 8. BI Refresh는 Runtime SLA다

## FIG-12-08. BI Refresh는 Runtime SLA다

```text
Source Change
 ↓
CDC / ETL
 ↓
ADW Refresh
 ↓
BI Dataset
 ↓
Report Freshness
```

---

# 9. BI 성능을 PDMG WAS에 전가하지 않는다

## FIG-12-09. BI 성능을 PDMG WAS에 전가하지 않는다

```text
Heavy Query
 ↓
ADW / BI Runtime

not

PDMG WAS / RDW Online
```

---

# 10. BI와 PDMG의 연결은 Data Contract다

## FIG-12-10. BI와 PDMG의 연결은 Data Contract다

```text
PDMG / Data Platform
        ↓
Approved Data Flow
        ↓
ADW / BI
```

---

# 11. BI는 Target Reference로 관리한다

## FIG-12-11. BI는 Target Reference로 관리한다

```text
PDMG Current Evidence
        ↓
BI implementation?
        ↓
No direct evidence
        ↓
TARGET REFERENCE / N-A
```

---

# 12. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-12-12. Normal Pattern

```text
Operational Data
 ↓
RDW
 ↓
ADW
 ↓
Approved Data Contract
 ↓
BI Portal / Self-BI
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 13. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-12-13. Forbidden Pattern

```text
BI → PDMG DAO                X
BI → PDMG 내부 Table DML       X
Heavy Query → Online RDW       X
BI Runtime → PDMG JVM 혼재      X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 14. Architecture Decision — BI 연결방식

## FIG-12-14. 주안과 대안

```text
[주안]
ADW / Data Contract 기반

        VS

[대안]
PDMG 내부 DB/DAO 직접 접근
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | ADW / Data Contract 기반 | PDMG 내부 DB/DAO 직접 접근 |
| 장점 | • 결합도 낮음<br>• 분석 Workload 격리<br>• Data Governance 적용 | • 초기 개발 쉬움 |
| 단점 | • Data Pipeline 필요 | • 강결합<br>• Online 영향<br>• Ownership 붕괴 |
| 권고 | **주안 채택** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 15. Current PDMG GAP

## FIG-12-15. GAP Map

```text
Current PDMG
│
├─ BI Target 상세 Current Evidence 없음
├─ Data Contract/Interface Inventory 미완료
├─ ADW Freshness/SLA OPEN
└─ Dataset Owner/Security Matrix OPEN
```

- `[GAP/OPEN]` BI Target 상세 Current Evidence 없음
- `[GAP/OPEN]` Data Contract/Interface Inventory 미완료
- `[GAP/OPEN]` ADW Freshness/SLA OPEN
- `[GAP/OPEN]` Dataset Owner/Security Matrix OPEN

---

# 16. 제12장 Architecture 판정

## FIG-12-16. Assessment

```text
Architecture Definition
      ↓
TARGET REFERENCE / CONDITIONAL

Current PDMG Conformance
      ↓
N-A / OPEN

Runtime Evidence
      ↓
LOW
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| PDMG Boundary | PASS | BI 외부 Boundary로 정의 |
| Data Contract | PASS architecture | 실제 Catalog 필요 |
| ADW/BI Runtime | TARGET REFERENCE | Current PDMG evidence 아님 |
| Security/Ownership | OPEN | BI 상세 설계 필요 |
| Runtime Evidence | OPEN | BI platform evidence 필요 |

---

# 17. 원천 정의서 Trace

## FIG-12-17. Source Trace

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
| 06 Interface | Story/Drill-down/Current-Target 근거 |
| 07 Data | Story/Drill-down/Current-Target 근거 |
| 18 Integrated Target | Story/Drill-down/Current-Target 근거 |

---

# 18. 다음 장으로 넘어가는 이유

## FIG-12-18. 12장 → 13

```text
제12장
BI 포탈
      ↓
"이 모든 구조를 시간이 지나도 Source와 Runtime에 맞게 유지하려면 무엇이 필요한가?"
      ↓
제13장
표준화와 10년 지속 가능성
```

---

# 19. 제12장 최종 결론

## FIG-12-19. Final Story

```text
BI 포탈
   ↓
Responsibility
   ↓
Boundary
   ↓
Runtime
   ↓
Evidence
   ↓
TARGET REFERENCE / CONDITIONAL
```

제12장의 결론은 **PDMG 경계 밖의 분석 소비계층과 Data Contract**라는 한 문장으로 정리된다.
