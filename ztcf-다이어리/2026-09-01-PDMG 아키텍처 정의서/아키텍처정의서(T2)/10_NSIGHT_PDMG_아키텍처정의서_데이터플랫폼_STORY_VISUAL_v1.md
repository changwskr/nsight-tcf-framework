# NSIGHT PDMG 아키텍처 정의서
# 제10장. 데이터플랫폼
## RDW·ADW 역할분리와 PDMG Data Access
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **9장의 Runtime이 사용하는 Data를 Ownership·Workload·Lineage 관점으로 재정의한다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제9장에서 넘어온 질문
     ↓
9장의 Runtime이 사용하는 Data를 Ownership·Workload·Lineage 관점으로 재정의한다.
     ↓
이 장이 답해야 할 질문
     ↓
RDW·ADW 역할분리와 PDMG Data Access
```

이 장의 핵심 원칙은 다음과 같다.

- ServiceId→DAO→Mapper→SqlId→Table Trace를 유지한다.
- RDW와 ADW의 Workload 목적을 분리한다.
- Cross-system Direct DML/DB-Link를 정상패턴으로 두지 않는다.
- Data Owner/Steward/SOR와 Metadata/Lineage를 관리한다.

---

# 1. Runtime의 끝에는 Data가 있다

## FIG-10-01. Runtime의 끝에는 Data가 있다

```text
ServiceId
 ↓
Business
 ↓
DAO
 ↓
Mapper
 ↓
SQL
 ↓
Data
```

---

# 2. PDMG Current Data Access

## FIG-10-02. PDMG Current Data Access

```text
Handler
 ↓
Facade
 ↓
Service
 ↓
DAO
 ↓
MyBatis Mapper
 ↓
JDBC
 ↓
RDW / DB
```

---

# 3. Data Architecture는 DB Architecture보다 넓다

## FIG-10-03. Data Architecture는 DB Architecture보다 넓다

```text
Data Architecture
├─ Domain / Subject
├─ Ownership
├─ Model
├─ Flow
├─ Quality
├─ Security
├─ Lifecycle
└─ Evidence

DB Architecture
= 구현 하위영역
```

---

# 4. RDW와 ADW의 역할을 분리한다

## FIG-10-04. RDW와 ADW의 역할을 분리한다

```text
RDW
Operational
Near-real-time
Online Service

        VS

ADW
Analytical
Mart / Aggregate
Heavy Query
```

---

# 5. PDMG Current는 RDW Evidence가 더 강하다

## FIG-10-05. PDMG Current는 RDW Evidence가 더 강하다

```text
PDMG
 ↓
Datasource / Mapper
 ↓
RDW / DB
 [Strong]

PDMG → ADW
 [OPEN / Inventory needed]
```

---

# 6. ServiceId에서 Table까지 Trace한다

## FIG-10-06. ServiceId에서 Table까지 Trace한다

```text
ServiceId
 ↓
DAO
 ↓
Mapper Namespace
 ↓
SqlId
 ↓
SQL
 ↓
Table / View
```

---

# 7. Data Ownership이 없는 DML을 허용하지 않는다

## FIG-10-07. Data Ownership이 없는 DML을 허용하지 않는다

```text
Own / Approved Data
 ├─ READ
 └─ WRITE

Other System Data
 ↓
Approved Contract

Direct DML
 X
```

---

# 8. CDC와 ETL은 서로 다른 Data Movement다

## FIG-10-08. CDC와 ETL은 서로 다른 Data Movement다

```text
Change
Source DB
 ↓ CDC
RDW

Bulk
Source
 ↓ ETL
ADW
```

---

# 9. Metadata/Lineage는 역추적을 가능하게 한다

## FIG-10-09. Metadata/Lineage는 역추적을 가능하게 한다

```text
Table
 ↑
SqlId
 ↑
DAO
 ↑
Service
 ↑
ServiceId
 ↑
Application
```

---

# 10. Data Quality와 Security도 Architecture다

## FIG-10-10. Data Quality와 Security도 Architecture다

```text
Data
 ↓
Classification
 ↓
Quality Rule
 ↓
Access / Masking
 ↓
Audit
 ↓
Lifecycle
```

---

# 11. Heavy Query를 Online Path에서 격리한다

## FIG-10-11. Heavy Query를 Online Path에서 격리한다

```text
Online Query
 ↓
RDW

Heavy Analysis
 ↓
ADW

Cross impact
minimize
```

---

# 12. Data Runtime Evidence

## FIG-10-12. Data Runtime Evidence

```text
ServiceId
 ↓
SqlId
 ↓
Table
 ↓
Rows / Elapsed / Error
 ↓
GUID
 ↓
Evidence
```

---

# 13. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-10-13. Normal Pattern

```text
ServiceId
 ↓
DAO / Mapper / SqlId
 ↓
RDW
 ↓
Operational Service

Heavy Analysis
 ↓
ADW
 ↓
BI / Analytics
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 14. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-10-14. Forbidden Pattern

```text
Cross-system Direct DML      X
RDW Heavy Query 무제한         X
Ownership 없는 Write           X
PDMG ADW 사용 추정             X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 15. Architecture Decision — RDW/ADW 사용원칙

## FIG-10-15. 주안과 대안

```text
[주안]
Online/Analytical Workload 분리

        VS

[대안]
RDW 중심 통합사용
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | Online/Analytical Workload 분리 | RDW 중심 통합사용 |
| 장점 | • Online SLA 보호<br>• 자원격리<br>• Data 역할 명확 | • 구조 단순<br>• 복제 감소 |
| 단점 | • Data Movement/운영 복잡도 증가 | • Heavy Query 간섭<br>• 확장성/운영 위험 |
| 권고 | **주안 채택** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 16. Current PDMG GAP

## FIG-10-16. GAP Map

```text
Current PDMG
│
├─ RDW/ADW 실제 Datasource Mapping OPEN
├─ Table/View Ownership 미완료
├─ Lineage 자동화 미완료
├─ Data Quality Rule Evidence 미완료
└─ CDC SLA 3s vs 30s CONFLICT
```

- `[GAP/OPEN]` RDW/ADW 실제 Datasource Mapping OPEN
- `[GAP/OPEN]` Table/View Ownership 미완료
- `[GAP/OPEN]` Lineage 자동화 미완료
- `[GAP/OPEN]` Data Quality Rule Evidence 미완료
- `[GAP/OPEN]` CDC SLA 3s vs 30s CONFLICT

---

# 17. 제10장 Architecture 판정

## FIG-10-17. Assessment

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
| DAO/Mapper/JDBC | PASS | 강한 Current Evidence |
| RDW | PASS/PARTIAL | Current 사용 근거 |
| ADW | OPEN | 실제 사용 Inventory 필요 |
| Ownership/Lineage | PARTIAL | Registry/자동화 필요 |
| CDC SLA | CONFLICT | 측정점/등급 결정 필요 |

---

# 18. 원천 정의서 Trace

## FIG-10-18. Source Trace

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
| 07 Data | Story/Drill-down/Current-Target 근거 |
| 06 Interface | Story/Drill-down/Current-Target 근거 |
| 13 CDC/ETL | Story/Drill-down/Current-Target 근거 |

---

# 19. 다음 장으로 넘어가는 이유

## FIG-10-19. 10장 → 11

```text
제10장
데이터플랫폼
      ↓
"이 Data와 Runtime을 사용하는 Marketing Platform에서 PDMG는 어떤 역할을 하는가?"
      ↓
제11장
마케팅플랫폼
```

---

# 20. 제10장 최종 결론

## FIG-10-20. Final Story

```text
데이터플랫폼
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

제10장의 결론은 **RDW·ADW 역할분리와 PDMG Data Access**라는 한 문장으로 정리된다.
