# NSIGHT PDMG 아키텍처 정의서
# 발표스크립트 목차 스타일 재구성본
## Visual-First / Top-down → Drill-down / PDMG Current + NSIGHT Target Alignment

> 기준 목차: `상호금융_정보계_아키텍처_발표스크립트_15분_CHATGPT (최종본)`  
> 재구성 대상: PDMG Architecture Definition 00~18  
> 상태: `[WORKING BASELINE-2026-09-01]`  
> 원칙: **발표스크립트의 Story 순서를 사용하되, 내용은 아키텍처 정의서 수준으로 유지한다.**

---

# 0. 문서 구성 원칙

```text
발표스크립트 Story
왜 → 전환 → 방법론 → 전체 → 논리 → 물리 → DR → Mechanism → Runtime → Data → Marketing → BI → Standard
        ↓
PDMG Architecture Definition
Current Fact + Target Alignment + Rule + Runtime + PASS/GAP
```

이 문서는 발표문을 그대로 옮긴 문서가 아니다. 기존 PDMG 00~18장의 구조를 **의사결정자가 이해하기 쉬운 13장 Story 구조**로 재배열한 Architecture Definition이다.

## 0.1 기존 00~18장 → 발표스크립트 13장 Mapping

| 발표스크립트형 장 | 재구성 주제 | 기존 상세 정의서 주요 원천 |
|---:|---|---|
| 1 | 왜 다시 짓는가 | 01, 17, 18 |
| 2 | 정보계 패러다임의 전환 | 01, 02, 03, 04 |
| 3 | 아키텍처 6단계 수립 방법론 | 00, 01~18 전체 |
| 4 | Big Picture | 01, 02, 18 |
| 5 | 논리 아키텍처 | 03, 04, 06, 07 |
| 6 | 물리 아키텍처 | 05, 16 |
| 7 | DR 센터 활용 전략 | 05, 16 |
| 8 | 메커니즘 | 08, 10, 11, 12, 13 |
| 9 | 런타임 서비스 | 09, 10, 13 |
| 10 | 데이터플랫폼 | 07, 06, 13 |
| 11 | 마케팅플랫폼 | 03, 06, 11, 15 |
| 12 | BI 포탈 | 06, 07 + NSIGHT Target |
| 13 | 표준화와 10년 지속 가능성 | 14, 15, 17, 18 |

---

# 목차

1. **왜 다시 짓는가**
2. **정보계 패러다임의 전환**
3. **아키텍처 6 단계 수립 방법론**
4. **Big Picture**
5. **논리 아키텍처**
6. **물리 아키텍처**
7. **DR 센터 활용 전략**
8. **메커니즘**
9. **런타임 서비스**
10. **데이터플랫폼**
11. **마케팅플랫폼**
12. **BI 포탈**
13. **표준화와 10년 지속 가능성**

---

# 제 1장 왜 다시 짓는가
## PDMG Architecture Definition의 목적과 재정의 이유

### FIG-01-01. 한눈에 보는 Architecture

```text
기존 시스템 설명서
   ↓
Module / Server / Source 단편 정보
   ↓
"실제로 어떻게 동작하는가?" 설명 한계
   ↓
PDMG Architecture 재정의
   ↓
Business / Application / Technical / Runtime / Evidence 통합
   ↓
운영 가능한 Architecture Baseline
```

### 1. 핵심 정의

- PDMG는 단순 모듈 목록이 아니라 실행 가능한 Architecture System으로 정의한다.
- 현재 구현(PDMG AS-IS)과 목표(NSIGHT TO-BE)를 분리한다.
- Source·Config·Runtime Evidence가 없는 내용은 Current Fact로 승격하지 않는다.

### 2. Architecture Drill-down

```text
PDMG Current
= pdmg-ui + pdmg-jwt + pdmg-fw + pdmg-service + pdmg-om?

현재 강한 Evidence
= ui / jwt / fw / service

pdmg-om
= [UNKNOWN]

Architecture Goal
= 구조 설명
+ Source 추적
+ Runtime 검증
+ PASS/GAP 판정
```

### 3. PDMG Current / NSIGHT Target 판정

**판정:** `CONDITIONAL PASS`

**상세 원천:** 기존 01 Executive / 17 Traceability / 18 Integrated Baseline

### 4. 이 장에서의 핵심 Architecture 질문

- PDMG를 단순 구현체가 아니라 Architecture Baseline으로 정의했는가?
- Current와 Target을 분리했는가?
- Runtime Evidence로 PASS를 판단할 수 있는가?

---

# 제 2장 정보계 패러다임의 전환
## PDMG를 Application이 아니라 Runtime Platform 관점까지 확장

### FIG-02-01. 한눈에 보는 Architecture

```text
과거 관점
Application
= 화면 + 업무 Java + DB

        ↓ 전환

PDMG 관점
Application Responsibility
+
Framework Runtime
+
Transaction / Timeout / Thread
+
Security / JWT
+
Data Access
+
Deployment / Observability
+
Evidence
```

### 1. 핵심 정의

- Module Boundary ≠ Process Boundary ≠ Spring Context Boundary ≠ Physical Server.
- PDMG Current는 온라인/거래 Runtime Reference로 보고, Event/CDC/ETL/BI 전체를 Current로 과장하지 않는다.
- NSIGHT는 Scalable / Resilient / Data-Centric 목표와 Alignment 기준을 제공한다.

### 2. Architecture Drill-down

```text
AS-IS
Browser
 ↓
pdmg-ui
 ↓
pdmg-service
 ↓
DAO / Mapper
 ↓
DB

확장된 Architecture 관점
Browser
 ↓
UI / Auth / Runtime
 ↓
Framework Control
 ↓
Business Core
 ↓
Data
 ↓
Operations / Evidence
```

### 3. PDMG Current / NSIGHT Target 판정

**판정:** `PASS / Current PARTIAL`

**상세 원천:** 기존 02 System Boundary / 03 Application / 04 Logical

### 4. 이 장에서의 핵심 Architecture 질문

- Module/Process/Context/Server를 혼동하지 않는가?
- PDMG 범위를 NSIGHT 전체와 혼동하지 않는가?
- 실행/운영/보안까지 Architecture에 포함했는가?

---

# 제 3장 아키텍처 6 단계 수립 방법론
## VISION → BIG PICTURE → LOGICAL → PHYSICAL → MECHANISM → RUNTIME

### FIG-03-01. 한눈에 보는 Architecture

```text
VISION
"무엇을 지향하는가?"
 ↓
BIG PICTURE
"누가 무엇을 책임지는가?"
 ↓
LOGICAL
"어떤 기술 역할/경계가 필요한가?"
 ↓
PHYSICAL
"어디에 어떻게 배치하는가?"
 ↓
MECHANISM
"어떤 실행규칙으로 통제하는가?"
 ↓
RUNTIME
"거래가 실제로 어떻게 동작하는가?"
 ↓
EVIDENCE / PASS
```

### 1. 핵심 정의

- 6단계는 문서 분류가 아니라 Architecture Drill-down 순서다.
- 하위 Runtime Evidence가 상위 Architecture 정의와 일치해야 한다.
- 마지막 단계는 그림 완성이 아니라 Source/Runtime 검증이다.

### 2. Architecture Drill-down

```text
Top-down
L0 Landscape
 ↓
L1 System Boundary
 ↓
L2 Module / Logical Node
 ↓
L3 Component / Layer
 ↓
L4 Runtime / Failure
 ↓
L5 Source / Config / Evidence
```

### 3. PDMG Current / NSIGHT Target 판정

**판정:** `PASS`

**상세 원천:** 기존 00 Guide / 01~18 전체 작성체계

### 4. 이 장에서의 핵심 Architecture 질문

- Top-down 6단계가 실제 하위 Evidence와 연결되는가?
- 각 단계의 산출물이 다음 단계 Input이 되는가?
- Runtime 검증이 최종 Gate로 포함되는가?

---

# 제 4장 Big Picture
## PDMG 전체 System Context와 책임 경계

### FIG-04-01. 한눈에 보는 Architecture

```text
User / Browser
                          │
          ┌───────────────┼───────────────┐
          ▼               ▼               ▼
       pdmg-ui         pdmg-jwt       pdmg-service
       UI              Auth           Business
                                           │
                                 ┌─────────┴─────────┐
                                 │ pdmg-fw           │
                                 │ Runtime Control   │
                                 └─────────┬─────────┘
                                           ↓
                                  Handler → Facade
                                           ↓
                                         Service
                                           ↓
                                      DAO / Mapper
                                           ↓
                                         RDW / DB

pdmg-om = [UNKNOWN]

External Integration
= Approved Contract / Inventory dependent
```

### 1. 핵심 정의

- 책임은 Application/Technical Boundary 안에 고정하고 연결은 경계에서 통제한다.
- pdmg-fw는 별도 Remote Business Server가 아니라 Framework Module/Capability다.
- PDMG Current ≠ NSIGHT 전체 Target Architecture.

### 2. Architecture Drill-down

```text
정상 경계
UI → Service Runtime → Data Access → DB

금지 경계
Browser → DB                 X
UI → DAO/Mapper              X
External → PDMG DB Direct DML X

Cross-cutting
Security / Observability / Traceability
```

### 3. PDMG Current / NSIGHT Target 판정

**판정:** `PASS / Current CONDITIONAL`

**상세 원천:** 기존 01 Executive / 02 System Context / 18 Integrated

### 4. 이 장에서의 핵심 Architecture 질문

- 누가 PDMG를 호출하고 어떤 경계가 있는가?
- 정상/금지 연결이 명확한가?
- Security/Observability가 Cross-cutting으로 정의되는가?

---

# 제 5장 논리 아키텍처
## Application Responsibility를 Logical Technical Node로 변환

### FIG-05-01. 한눈에 보는 Architecture

```text
Application
pdmg-ui
pdmg-jwt
pdmg-service + pdmg-fw
pdmg-om?
      ↓
Technical Capability
      ↓
LTN-PD-01 UI Delivery
LTN-PD-02 Authentication
LTN-PD-03 Application Runtime
LTN-PD-04 Data Service
LTN-PD-05 Integration [CONDITIONAL]
LTN-PD-06 Operations [OPEN]
```

### 1. 핵심 정의

- Application ≠ Logical Technical Node.
- Build Module ≠ Logical Technical Node.
- Logical Node ≠ Physical Host.
- 각 Logical Node는 Runtime Type, State, Scale Unit, Failure Domain, Security Boundary를 정의한다.

### 2. Architecture Drill-down

```text
LTN-PD-03 Application Runtime
│
├─ Framework Runtime
│  ├─ Filter / Context
│  ├─ TCF / Dispatcher
│  ├─ Worker / Timeout
│  ├─ Transaction
│  └─ Error / Logging
│
└─ Business Runtime
   ├─ Handler / Controller
   ├─ Facade
   ├─ Service
   └─ DAO
```

### 3. PDMG Current / NSIGHT Target 판정

**판정:** `PASS / Current CONDITIONAL`

**상세 원천:** 기존 03 Application / 04 Logical / 06 Interface / 07 Data

### 4. 이 장에서의 핵심 Architecture 질문

- Application과 Technical Node를 구분했는가?
- Logical Node별 State/Scale/Failure/Security가 있는가?
- Integration/OM Unknown을 Current로 과장하지 않았는가?

---

# 제 6장 물리 아키텍처
## Logical Node를 Center·WEB·WAS·JVM·DB로 Mapping

### FIG-06-01. 한눈에 보는 Architecture

```text
User
 ↓
GSLB
 ↓
L4
 ↓
WEB / Apache
 ↓
WAS / Tomcat JVM
 ↓
PDMG WAR
 ↓
Hikari / MyBatis / JDBC
 ↓
RDW / DB

Logical Node
 ↓
Environment
 ↓
Center
 ↓
Host / VM
 ↓
JVM / Process
 ↓
Artifact
 ↓
Port / DB / Storage
 ↓
Monitoring / Backup
```

### 1. 핵심 정의

- Server ≠ VM ≠ JVM ≠ WAR.
- 의왕 Main / 안성 DR은 Working Physical Reference로 사용하되 실제 PDMG Host Mapping은 Evidence로 확정한다.
- 정확한 Hostname·Port·Version·Server Count는 Evidence 없이는 OPEN이다.

### 2. Architecture Drill-down

```text
Current Working Physical Path
GSLB → L4 → Apache → Tomcat → WAR → DB

Current Critical GAP
Artifact
 ↓
DeploymentId
 ↓
WAR
 ↓
JVM
 ↓
VM
 ↓
Host

= 전수 Mapping 미완료
```

### 3. PDMG Current / NSIGHT Target 판정

**판정:** `PASS / Current PARTIAL`

**상세 원천:** 기존 05 Physical / 16 Capacity

### 4. 이 장에서의 핵심 Architecture 질문

- Logical Node가 실제 Host/VM/JVM/WAR로 Mapping되는가?
- Port/Firewall/LB/Config가 정합하는가?
- Capacity Candidate를 Production Fact로 표현하지 않는가?

---

# 제 7장 DR 센터 활용 전략
## AP 가용성과 DB 정합성을 분리하는 HA/DR 전략

### FIG-07-01. 한눈에 보는 Architecture

```text
Main Center
 ├─ WEB
 ├─ WAS
 └─ DB Local HA
      │
      │ replication / recovery
      ▼
DR Center
 ├─ WEB
 ├─ WAS
 └─ DR DB

Failure
 ↓
Detection
 ↓
Isolation
 ↓
Traffic Reroute
 ↓
Application / Config / Key
 ↓
Data Recovery
 ↓
Business Validation
```

### 1. 핵심 정의

- Local HA와 Center DR은 다르다.
- AP Active-Active/N+1은 주안 후보이나 DB 양방향 Active-Active는 정합성 리스크 때문에 별도 판단한다.
- RTO/RPO는 Architecture가 임의로 숫자를 만들지 않고 Business Criticality로 확정한다.

### 2. Architecture Drill-down

```text
DR PASS
≠ Backup Success

DR PASS
= Network
+ Artifact
+ Config
+ Key
+ DB
+ Interface
+ Monitoring
+ Failover/Failback
+ Business Validation
```

### 3. PDMG Current / NSIGHT Target 판정

**판정:** `PASS / Current OPEN`

**상세 원천:** 기존 05 Physical / 16 Capacity·HA·DR

### 4. 이 장에서의 핵심 Architecture 질문

- Local HA와 DR을 구분했는가?
- DB 정합성보다 보여주기식 Active-Active를 우선하지 않는가?
- RTO/RPO/Restore/Failback Evidence가 있는가?

---

# 제 8장 메커니즘
## Architecture를 실제로 움직이게 하는 공통 실행 규칙

### FIG-08-01. 한눈에 보는 Architecture

```text
HTTP
 ↓
DefaultFilter
 ↓
SecurityFilterChain
 ↓
DispatcherServlet
 ↓
Interceptor
 ↓
OnlineTransactionController
 ↓
TcfFacade
 ↓
OnlineTimeoutExecutor
 ↓
TransactionDispatcher
 ↓
Handler
 ↓
Facade / Service / DAO
 ↓
DB
 ↓
Response / Error / Log
```

### 1. 핵심 정의

- Framework는 '어떻게 안전하게 실행할 것인가'를 소유한다.
- Business는 '무슨 업무를 수행할 것인가'를 소유한다.
- TCF ON/OFF가 달라도 Common Business Core는 Facade로 정렬하는 것이 주안이다.

### 2. Architecture Drill-down

```text
Framework
├─ Filter
├─ Context
├─ Security Integration
├─ TCF
├─ Dispatcher
├─ Timeout / Worker
├─ Transaction
├─ Error
└─ Logging

Business
├─ Handler / Controller
├─ Facade
├─ Service
├─ Rule [optional]
├─ DAO
└─ Mapper
```

### 3. PDMG Current / NSIGHT Target 판정

**판정:** `PASS / Current PARTIAL-GAP`

**상세 원천:** 기존 08 Framework / 10 Transaction / 11 Security / 12 Message

### 4. 이 장에서의 핵심 Architecture 질문

- Framework와 Business 책임이 분리되는가?
- TCF ON/OFF의 Business Core가 정합하는가?
- Timeout/Error/Context/JWT Mechanism이 서로 연결되는가?

---

# 제 9장 런타임 서비스
## 거래 한 건의 End-to-End 실행과 FAST/DEEP 경계

### FIG-09-01. 한눈에 보는 Architecture

```text
════════ Request Thread ════════
Filter
Security
MVC
Controller
TcfFacade
Future.get(timeout)
        │
        │ submit
        ▼
════════ Worker Thread ═════════
Context Install
TransactionTemplate BEGIN
Dispatcher
Handler
Facade
Service
DAO / Mapper
DB
Deadline
COMMIT / ROLLBACK
Context Clear
        │
        ▼
Response / Error / Evidence
```

### 1. 핵심 정의

- Current PDMG 핵심 Runtime은 Online/FAST Transaction 성격이다.
- Request Thread와 Worker Thread, HTTP Timeout과 DB Transaction Lifetime을 분리한다.
- NSIGHT의 DEEP 분석 흐름은 PDMG 온라인 Runtime과 자원/책임을 분리한다.

### 2. Architecture Drill-down

```text
PDMG FAST
HTTP → Worker → TX → RDW

NSIGHT Broader FAST
Event / CDC

NSIGHT DEEP
ETL → ADW → BI

FAST ≠ DEEP
```

### 3. PDMG Current / NSIGHT Target 판정

**판정:** `PASS / Current CONDITIONAL`

**상세 원천:** 기존 09 Online Runtime / 10 TX / 13 Non-online

### 4. 이 장에서의 핵심 Architecture 질문

- Request Thread와 Worker Thread가 분리되는가?
- HTTP 504와 DB Rollback 완료를 동일시하지 않는가?
- FAST와 DEEP Workload를 분리하는가?

---

# 제 10장 데이터플랫폼
## RDW·ADW 역할분리와 PDMG Data Access

### FIG-10-01. 한눈에 보는 Architecture

```text
PDMG
 ↓
Service
 ↓
DAO
 ↓
Mapper
 ↓
SqlId
 ↓
SQL
 ↓
RDW / DB

NSIGHT Data Platform
RDW
= Operational / Near-real-time

ADW
= Analytical / Mart / Heavy Query
```

### 1. 핵심 정의

- PDMG Current의 강한 Evidence는 MyBatis/JDBC 기반 RDW/DB 접근이다.
- PDMG가 ADW를 직접 사용하는지는 Datasource/Mapper Inventory로 확인해야 한다.
- Cross-system Direct DML/DB-Link를 정상패턴으로 두지 않는다.

### 2. Architecture Drill-down

```text
Trace
ServiceId
 ↓
DAO
 ↓
Mapper Namespace
 ↓
SqlId
 ↓
Table/View
 ↓
Lineage / Evidence

Workload
Online → RDW
Heavy Analysis → ADW
```

### 3. PDMG Current / NSIGHT Target 판정

**판정:** `PASS / Current PARTIAL`

**상세 원천:** 기존 07 Data / 06 Interface / 13 CDC·ETL

### 4. 이 장에서의 핵심 Architecture 질문

- ServiceId→SQL→Table Lineage가 가능한가?
- RDW와 ADW의 역할을 분리하는가?
- Cross-system Direct DB DML을 금지하는가?

---

# 제 11장 마케팅플랫폼
## PDMG를 Marketing Platform 실행 Reference로 정의

### FIG-11-01. 한눈에 보는 Architecture

```text
Marketing Platform Responsibility
         ↓
PDMG Reference
│
├─ pdmg-ui
├─ pdmg-jwt
├─ pdmg-service
│   └─ pdmg-fw
└─ pdmg-om? [UNKNOWN]
         ↓
Program / ServiceId
         ↓
Business Runtime
         ↓
RDW / External Contract
```

### 1. 핵심 정의

- PDMG는 Marketing Platform의 Application/Runtime Reference로 사용할 수 있다.
- PDMG AS-IS의 `mg` Source Prefix와 NSIGHT Application Group `MP`는 동일하다고 자동 간주하지 않고 Mapping Registry/ADR로 연결한다.
- 실시간 Event/Kafka 기반 마케팅 전체 플랫폼 기능을 PDMG Current로 자동 승격하지 않는다.

### 2. Architecture Drill-down

```text
NSIGHT Target
MP Marketing Platform
        │
        │ mapping required
        ▼
PDMG AS-IS
mg / co / a
        ↓
mgcoa9001
        ↓
mgcoa9001S0

MP ≠ mg
unless approved mapping
```

### 3. PDMG Current / NSIGHT Target 판정

**판정:** `CONDITIONAL PASS`

**상세 원천:** 기존 03 Application / 06 Interface / 11 Security / 15 Naming

### 4. 이 장에서의 핵심 Architecture 질문

- PDMG를 Marketing Platform Reference로 어떻게 Mapping하는가?
- `mg`와 `MP`의 Mapping을 승인 없이 동일시하지 않는가?
- Event/Kafka Target을 Current로 과장하지 않는가?

---

# 제 12장 BI 포탈
## PDMG 경계 밖의 분석 소비계층과 Data Contract

### FIG-12-01. 한눈에 보는 Architecture

```text
PDMG / Operational Service
       ↓
RDW / Data Platform
       ↓
ADW / Analytical Platform
       ↓
BI Portal
       ↓
Self-BI / Report / Analysis

PDMG
≠ BI Portal Current Implementation
```

### 1. 핵심 정의

- BI Portal은 PDMG Current 내부 Module로 정의하지 않는다.
- BI는 Data Platform의 신뢰된 데이터와 표준 Interface/Data Contract를 소비하는 상위 분석 Application이다.
- PDMG와 BI의 연결점은 Application 직접 의존이 아니라 Data/Interface Contract다.

### 2. Architecture Drill-down

```text
금지
PDMG Controller → BI DAO       X
BI → PDMG Internal Table DML   X

정상
PDMG / Data Platform
 ↓
Approved Data Flow / Contract
 ↓
BI Portal
```

### 3. PDMG Current / NSIGHT Target 판정

**판정:** `TARGET REFERENCE / PDMG N-A`

**상세 원천:** 기존 06 Interface / 07 Data + NSIGHT BI Target

### 4. 이 장에서의 핵심 Architecture 질문

- BI가 PDMG 내부 Module이 아니라 Data Consumer임을 유지하는가?
- Data/Interface Contract를 통해 연결되는가?
- Operational Runtime과 Analytical Runtime을 분리하는가?

---

# 제 13장 표준화와 10년 지속 가능성
## Naming·DevOps·Observability·Traceability로 Architecture를 유지

### FIG-13-01. 한눈에 보는 Architecture

```text
Architecture
 ↓
Naming / Code Standard
 ↓
Source
 ↓
CI / Rule Test
 ↓
Artifact
 ↓
Deployment
 ↓
Runtime Evidence
 ↓
Drift / GAP
 ↓
ADR
 ↓
New Baseline
```

### 1. 핵심 정의

- 표준은 문서가 아니라 CI와 Runtime에서 검증 가능한 Rule이어야 한다.
- Program→ServiceId→Package→Mapper→Artifact→Deployment→GUID를 하나의 Trace Chain으로 연결한다.
- Architecture Definition PASS와 Current Implementation PASS를 분리한다.

### 2. Architecture Drill-down

```text
Business
 ↓
Program
 ↓
ServiceId
 ↓
Handler / Facade / Service
 ↓
DAO / Mapper / SqlId
 ↓
ArtifactHash
 ↓
DeploymentId
 ↓
Host / JVM
 ↓
GUID / Runtime Evidence

HG90
= Evidence-backed Baseline Release
```

### 3. PDMG Current / NSIGHT Target 판정

**판정:** `CONDITIONAL PASS`

**상세 원천:** 기존 14 DevOps / 15 Naming / 17 Traceability / 18 Baseline

### 4. 이 장에서의 핵심 Architecture 질문

- Naming/Rule이 CI에서 자동검증되는가?
- Artifact/Deployment/Runtime Evidence가 Trace되는가?
- Critical GAP가 닫혀야 HG90가 되는가?

---

# 마무리 — PDMG 통합 Architecture Baseline

```text
User / Browser
      ↓
pdmg-ui
      ↓
pdmg-jwt
      ↓
pdmg-service + pdmg-fw
      │
      ├─ Filter / Context / Security
      ├─ TCF / Dispatcher / Handler
      ├─ Worker / Timeout / Transaction
      ├─ Facade / Service
      └─ DAO / Mapper
              ↓
            RDW / DB

Physical
GSLB → L4 → Apache → Tomcat/JVM → WAR → DB

Cross-cutting
Interface / Security / Observability / DevOps / HA-DR / Traceability

Evidence
Architecture → Source → Artifact → Deployment → GUID → Runtime Evidence
```

## 현재 전체 판정

| 구분 | 판정 |
|---|---|
| Architecture Definition | **CONDITIONAL PASS** |
| Current PDMG Conformance | **PARTIAL / GAP** |
| Runtime Evidence Coverage | **MEDIUM** |
| Final HG90 Baseline | **OPEN** |

### Final PASS 전에 닫아야 할 Critical GAP

```text
JWT RS256 Issuer ↔ HMAC Verifier
JWT Key Lifecycle / Multi-instance
Trusted Principal ↔ Business User Binding
Artifact / Deployment → Host/JVM/WAR Mapping
Runtime Evidence Automation
Capacity / RTO / RPO / DR Evidence
```

최종적으로 이 문서의 Story는 다음과 같다.

```text
왜 다시 짓는가
 ↓
어떤 방향으로 전환하는가
 ↓
어떤 방법론으로 정의하는가
 ↓
전체 책임과 경계는 무엇인가
 ↓
논리적으로 어떻게 분리하는가
 ↓
물리적으로 어디에 배치하는가
 ↓
장애와 DR을 어떻게 견디는가
 ↓
어떤 Mechanism으로 통제하는가
 ↓
Runtime에서 실제 어떻게 실행되는가
 ↓
데이터를 어떻게 분리·관리하는가
 ↓
마케팅과 BI에 어떻게 연결되는가
 ↓
어떻게 표준화하고 10년 동안 유지하는가
 ↓
Evidence-backed Architecture Baseline
```