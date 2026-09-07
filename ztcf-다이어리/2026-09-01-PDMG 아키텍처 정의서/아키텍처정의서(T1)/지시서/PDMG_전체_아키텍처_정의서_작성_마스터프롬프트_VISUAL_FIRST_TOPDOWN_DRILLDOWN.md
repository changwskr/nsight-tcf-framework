# PDMG 전체 아키텍처 정의서 작성 마스터 프롬프트
## PDMG Architecture Definition Master Prompt
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First / Runtime-Verifiable

> 목적: 업로드된 상호금융 정보계 Architecture 자료, PDMG Source/Config/Runtime 분석자료, 현재까지 작성된 Application/Technical/Infrastructure/Interface/Data/Naming 정의서, Architecture Decision/PASS 평가자료를 근간으로 **PDMG 자체를 완전하게 설명하는 독립 아키텍처 정의서**를 작성한다.
>
> 핵심 철학: **PDMG 전체 구조를 위에서 먼저 보여주고 → System/Module/Layer/Component/Runtime/Source 순으로 내려간다.**
>
> 최우선 표현방식: **TEXT/ASCII Architecture Diagram 중심**
>
> 산출형식: Markdown(.md) 기반 장별 정의서 + 최종 통합본 + Evidence/Decision/PASS Matrix
>
> 작성방향:
>
> ```text
> PDMG Overall Architecture
>        ↓
> System Context / Boundary
>        ↓
> Application / Module
>        ↓
> Logical / Technical
>        ↓
> Physical / Infrastructure
>        ↓
> Interface / Data
>        ↓
> Framework Mechanism
>        ↓
> Runtime
>        ↓
> Security / Operations
>        ↓
> Development Standard
>        ↓
> Source / Config / Runtime Evidence
>        ↓
> PASS / GAP / ADR / Closed Loop
> ```

---

# 0. ROLE

너는 지금부터 다음 역할을 동시에 수행한다.

```text
Chief Enterprise Architect
+ PDMG Chief Application Architect
+ Technical Architect
+ Infrastructure Architect
+ Interface Architect
+ Data Architect
+ Security Architect
+ TCF Framework Architect
+ Transaction / Timeout Architect
+ DevOps Architect
+ Operations / Observability Architect
+ PDMG Source Analyst
+ Architecture Decision Reviewer
+ Architecture Technical Writer
```

이번 작업은 Spring Boot, 금융권, TCF에 대한 일반론을 작성하는 작업이 아니다.

반드시 다음을 수행한다.

```text
Uploaded Evidence
      ↓
PDMG Architecture Fact
      ↓
Architecture Model
      ↓
TEXT Architecture
      ↓
Source / Config / Runtime Trace
      ↓
PASS / GAP / ADR
```

---

# 1. 이번 문서의 주인공

이번 문서의 주인공은 **PDMG**다.

기존 NSIGHT/PDMG 문서에서는:

```text
NSIGHT
= Target Architecture

PDMG
= AS-IS Reference
```

관점을 사용했다.

이번 정의서는 관점을 다음처럼 바꾼다.

```text
PDMG
= 문서의 Main Subject
= Current Architecture
= Source / Config / Runtime 중심의 실제 Architecture

NSIGHT
= PDMG Architecture를 평가하기 위한 상위 Target / Alignment Reference
```

따라서 본문 설명순서는:

```text
PDMG Architecture
        ↓
PDMG Current Source / Runtime
        ↓
PDMG Architecture Rule
        ↓
PDMG PASS / GAP
        ↓
NSIGHT Target Alignment
```

으로 작성한다.

**NSIGHT Vision부터 길게 시작하지 않는다.**

---

# 2. 입력자료 사용원칙

## 2.1 필수 입력자료

작업 시작 시 사용자가 제공한 모든 자료를 읽는다.

특히 다음 자료군을 반드시 확인한다.

### A. 현재 업로드 자료

```text
상호금융_정보계_아키텍처_발표스크립트_15분_CHATGPT (최종본).docx
```

이 자료는 PDMG/정보계 Architecture를 설명하는 **상위 Story / Presentation Context Evidence**로 사용한다.

### B. PDMG Architecture 핵심 정의자료

```text
NSIGHT_PDMG_아키텍처_정의서_III_PDMG_Module_Application_Architecture.md
NSIGHT_PDMG_아키텍처_정의서_IV_PDMG_Online_Runtime_TCF_Flow.md
NSIGHT_PDMG_아키텍처_정의서_V_Transaction_Timeout_Thread_DB_Architecture.md
NSIGHT_PDMG_아키텍처_정의서_VI_Standard_Message_Context_Error_Logging.md
NSIGHT_PDMG_아키텍처_정의서_VII_Security_SSO_JWT_Session.md
NSIGHT_PDMG_아키텍처_정의서_VIII_Infrastructure_WAS_Capacity_HA_DR.md
NSIGHT_PDMG_아키텍처_정의서_IX_DevOps_OM_Observability.md
NSIGHT_PDMG_아키텍처_정의서_X_Naming_ServiceId_Traceability_Closed_Loop.md
```

### C. PDMG Source Deep Dive

```text
NSIGHT_PDMG_아키텍처_정의서_08_PDMG_SOURCE_RUNTIME_REFERENCE_DEEP_DIVE_VISUAL_FIRST_상세본_보완개정본_v2.md
```

### D. Architecture Domain 별첨

```text
별첨 A APPLICATION ARCHITECTURE
별첨 B TECHNICAL ARCHITECTURE
별첨 C INFRASTRUCTURE ARCHITECTURE
별첨 D INTERFACE ARCHITECTURE
별첨 E DATA ARCHITECTURE
별첨 F NAMING DEVELOPMENT STANDARD
```

### E. Standard / Inventory / Decision

```text
NSIGHT_PDMG_어플리케이션_코드_정의서_VISUAL_FIRST.md
NSIGHT_PDMG_하드웨어_소프트웨어_매트릭스.xlsx
NSIGHT_PDMG_아키텍처_의사결정_TASK_및_해결방안서.md
NSIGHT_PDMG_아키텍처_의사결정_레지스터_PASS평가_v2.xlsx
```

### F. NSIGHT Target Reference

NSIGHT Target 문서는 다음 용도로만 사용한다.

```text
PDMG GAP 판단
PDMG Architecture PASS 판단
Target Alignment
ADR 후보
```

PDMG Source에 없는 구조를 NSIGHT 자료에서 가져와 **PDMG AS-IS라고 쓰지 않는다.**

---

# 3. Evidence 우선순위

자료가 충돌할 경우 다음 순서를 따른다.

```text
1. 실행 Source / Config
      ↓
2. Runtime Evidence / Deployment Evidence
      ↓
3. 현재 PDMG Architecture 분석문서
      ↓
4. 승인된 Architecture Decision / PASS Register
      ↓
5. 현재 프로젝트 공식 Architecture 자료
      ↓
6. 발표자료 / 설명자료
      ↓
7. 과거 표준 / Historical 문서
      ↓
8. 일반적인 기술지식
```

일반지식은 빈칸을 채우기 위해 사용하지 않는다.

---

# 4. Evidence 상태표기

모든 핵심 주장에는 가능한 한 다음 태그를 붙인다.

| 태그 | 의미 |
|---|---|
| `[FACT]` | Source / Config / Runtime / 공식자료에서 직접 확인 |
| `[CONFIRMED]` | 복수 Evidence가 같은 내용을 지지 |
| `[AS-IS]` | 현재 PDMG 구현 |
| `[BASELINE-YYYY-MM-DD]` | 특정 시점 Architecture Baseline |
| `[DECISION]` | 승인된 Architecture 의사결정 |
| `[PASS]` | 현재 정의 Architecture에 정합 |
| `[CONDITIONAL PASS]` | 방향은 정합하나 추가조건/Evidence 필요 |
| `[PARTIAL]` | 일부 구현 또는 일부 Evidence만 확인 |
| `[GAP]` | Target/정의/구현의 차이 |
| `[CONFLICT]` | 복수 자료 간 충돌 |
| `[RISK]` | 성능/보안/운영/장애 위험 |
| `[OPEN]` | 결정이 필요 |
| `[UNKNOWN]` | 현재 Evidence로 확인할 수 없음 |
| `[PROPOSED]` | 승인 전 제안 |
| `[DEPRECATED]` | 과거 폐기기준 |

---

# 5. 절대 금지사항

다음은 절대로 하지 않는다.

```text
PDMG AS-IS를 NSIGHT TO-BE로 자동 승격
NSIGHT Target을 PDMG AS-IS라고 기술
Source에 없는 Layer / Component를 일반론으로 생성
pdmg-fw가 별도 Module이라는 이유로 별도 Remote Server라고 표현
WAS Server = JVM = WAR로 표현
Module = Process로 표현
Spring Bean = Remote Service로 표현
TCF ON의 의도구조를 실제 AS-IS로 단정
STF / ETF가 Class로 존재한다고 Runtime에 실행된다고 단정
확인되지 않은 Port / Timeout / Heap / TPS / DB Version 생성
과거 Capacity Candidate를 Current Production Fact로 표현
JWT Algorithm/Key 충돌을 임의 해소
pdmg-om 구현을 Evidence 없이 일반론으로 채움
Architecture PASS와 Implementation PASS를 동일하게 표시
```

---

# 6. Visual-First 최우선 원칙

문서는 **TEXT 그림이 먼저**, 설명이 나중이다.

잘못된 순서:

```text
설명 4페이지
   ↓
마지막에 작은 그림
```

올바른 순서:

```text
Architecture Diagram
   ↓
Diagram 해설
   ↓
상세 Drill-down
   ↓
표 / Rule / Evidence
```

---

# 7. Top-down Drill-down Level

모든 Architecture는 아래 Level을 따른다.

```text
L0  PDMG Architecture Landscape
    "PDMG 전체가 무엇인가?"

 ↓

L1  System Context / Boundary
    "누가 PDMG를 호출하고 PDMG는 무엇을 호출하는가?"

 ↓

L2  Container / Module / Logical Node
    "PDMG 내부는 어떤 큰 실행·책임 단위로 나뉘는가?"

 ↓

L3  Component / Layer
    "Handler / Facade / Service / DAO / Framework는 어떻게 나뉘는가?"

 ↓

L4  Runtime / Sequence / Data Flow
    "요청 한 건이 실제로 어떤 순서로 실행되는가?"

 ↓

L5  Source / Config / Failure / Evidence
    "어떤 클래스·설정·Metric으로 이를 증명하는가?"
```

---

# 8. 모든 장의 공통 작성 Template

모든 장은 기본적으로 다음 구조를 사용한다.

```text
0. Chapter Purpose
1. Evidence Register
2. 한눈에 보는 Architecture
3. L0/L1 Top View
4. L2 Drill-down
5. L3 Component Drill-down
6. L4 Runtime / Sequence
7. L5 Source / Config Evidence
8. Responsibility / Boundary
9. Normal Pattern
10. Forbidden Pattern
11. Failure / Risk
12. Security
13. Performance / Availability
14. Operations / Observability
15. Architecture Rule
16. PASS / Conformance
17. GAP / OPEN / CONFLICT
18. ADR / Decision
19. Test / Verification
20. Checklist
21. Next Chapter Handoff
```

각 번호가 있는 주요 절에는 **최소 1개의 TEXT Architecture Diagram**을 둔다.

---

# 9. TEXT 그림 작성규칙

## 9.1 기본 표현

```text
┌──────────────┐
│ Component A  │
└──────┬───────┘
       │ HTTP/JSON
       ▼
┌──────────────┐
│ Component B  │
└──────┬───────┘
       │ JDBC
       ▼
┌──────────────┐
│ Database     │
└──────────────┘
```

## 9.2 Boundary 표현

```text
┌──────── Process Boundary ────────┐
│                                  │
│  Module A        Module B        │
│                                  │
└──────────────────────────────────┘
```

## 9.3 금지표현

```text
A → B → C
```

만 쓰고 끝내지 않는다.

가능하면 다음을 표시한다.

```text
Boundary
Responsibility
Direction
Protocol
State
Failure Point
Evidence
```

---

# 10. TEXT 그림 설명 규칙

각 주요 그림 다음에는 최소 다음을 설명한다.

```text
시작점
종료점
주요 책임
Boundary
호출방향
동기/비동기
Protocol
Transaction Boundary
Security Boundary
Failure Point
관측포인트
Source Evidence
```

---

# 11. PDMG Known Baseline — 작성 시 반드시 재검증할 기준

다음은 현재 PDMG 분석에서 강하게 확인된 Working Baseline이다.

새 Evidence가 나오면 갱신하되 근거 없이 삭제하지 않는다.

## 11.1 PDMG Module Baseline

```text
pdmg-ui
pdmg-jwt
pdmg-fw
pdmg-service
pdmg-om
```

주의:

```text
pdmg-om
= Baseline 존재

Current Source Detail
= Evidence 부족 가능

→ [UNKNOWN] 유지 가능
```

## 11.2 Module / Process / Context 구분

```text
Module Boundary
≠ Process Boundary
≠ Spring ApplicationContext Boundary
```

## 11.3 Business Layer Reference

```text
Handler
   ↓
Facade
   ↓
Service
   ↓
DAO
   ↓
Mapper / SQL
   ↓
DB
```

현재 일반 Rule Layer는 Source에서 전수 AS-IS로 확인되지 않았으므로 무조건 넣지 않는다.

## 11.4 Program / ServiceId Reference

```text
Program
mgcoa9001

ServiceId
mgcoa9001S0
```

```text
Program ID
2 + 2 + 1 + 4
= 9 chars

ServiceId
2 + 2 + 1 + 4 + 1 + 1
= 11 chars
```

## 11.5 PDMG Online Runtime Reference

```text
HTTP Request
   ↓
DefaultFilter
   ↓
Spring SecurityFilterChain
   ↓
DispatcherServlet
   ↓
ServicePreventionInterceptor.preHandle
   ↓
OnlineTransactionController
   ↓
TcfFacade
   ↓
OnlineTimeoutExecutor
   ↓
Worker Thread
   ↓
TransactionTemplate
   ↓
TransactionDispatcher
   ↓
TransactionHandler
   ↓
Facade
   ↓
BizPrePostAspect
   ↓
Service
   ↓
DAO / Mapper / SQL
   ↓
DB
   ↓
Response Advice
   ↓
afterCompletion
   ↓
Context Clear
   ↓
HTTP Response
```

실제 Source를 재검증하여 Class/Order 차이가 있으면 수정한다.

## 11.6 Timeout Snapshot

현재 분석 Reference:

```text
timeout enabled = true
milliseconds    = 5000
pool-size       = 20
queue-capacity  = 100
```

반드시:

```text
[AS-IS SNAPSHOT]
```

으로 표시한다.

Target SLA로 표시하지 않는다.

## 11.7 Critical JWT GAP

현재 분석 Reference:

```text
pdmg-jwt
RS256 Issue
       ↓
pdmg-fw
HMAC jwt.secret Verify Path

[CRITICAL GAP]
```

새 Source가 이 문제를 해소한 경우 Evidence를 제시하고 상태를 변경한다.

---

# 12. Architecture PASS와 Implementation Conformance를 분리

모든 장의 마지막에는 다음 두 상태를 반드시 별도 표시한다.

```text
Architecture Definition PASS
        ≠
PDMG Implementation Conformance
```

예:

```text
Architecture Definition
RS256 + JWKS
→ PASS

Current PDMG Source
RS256 Issuer / HMAC Verifier
→ GAP
```

## 12.1 Architecture PASS 값

```text
PASS
CONDITIONAL PASS
OPEN
FAIL
```

## 12.2 PDMG 구현상태 값

```text
PASS
PARTIAL
GAP
CONFLICT
OPEN
UNKNOWN
CANDIDATE
```

---

# 13. 의사결정 Register 연계

현재 Architecture Decision Register의 모든 Task를 관련 장에 매핑한다.

각 장에는 다음 표를 둔다.

| Decision Task | Architecture PASS | PDMG Conformance | Evidence | PASS 전환조건 |
|---|---|---|---|---|

예:

```text
ADR-TASK-010
JWT Algorithm
        ↓
Architecture PASS
        ↓
PDMG GAP
        ↓
Integration Test / Key Design
        ↓
Close
```

Decision Task를 문서 끝에 몰아넣지 말고 관련 Architecture 장에서 다룬다.

---

# 14. 전체 문서 목차

최종 PDMG 전체 아키텍처 정의서는 아래 순서를 기본 목차로 한다.

```text
00. PDMG Architecture Definition Guide / Evidence
01. PDMG Executive Architecture Overview
02. PDMG System Context & Boundary
03. PDMG Application / Module Architecture
04. PDMG Logical Technical Architecture
05. PDMG Physical / Infrastructure Architecture
06. PDMG Interface Architecture
07. PDMG Data Architecture
08. PDMG Framework / Mechanism Architecture
09. PDMG Online Runtime Architecture
10. Transaction / Timeout / Thread / DB Architecture
11. Security / SSO / JWT / Session Architecture
12. Standard Message / Context / Error / Logging Architecture
13. Event / CDC / ETL / Batch / File / Cache Architecture
14. DevOps / Deployment / OM / Observability Architecture
15. Naming / Application Code / Development Standard
16. Capacity / Performance / HA / DR Architecture
17. Traceability / Conformance / PASS / GAP / ADR
18. Integrated PDMG Architecture Baseline
```

---

# 15. 00장 — Architecture Definition Guide / Evidence

## 핵심 질문

```text
이 문서를 어떤 Evidence와 규칙으로 읽어야 하는가?
```

## 작성내용

```text
문서 목적
범위
PDMG Main Subject 원칙
Evidence Priority
상태 Tag
Top-down Level
PASS 판정방법
Source Baseline
파일/문서 관계
```

## 필수 TEXT 그림

```text
FIG-00-01 PDMG Architecture Writing Journey
FIG-00-02 Evidence Priority
FIG-00-03 Architecture PASS vs Implementation PASS
FIG-00-04 Top-down Level L0~L5
FIG-00-05 Document Dependency Map
```

## 작성금지

PDMG 구조를 설명하기 전에 일반 Enterprise Architecture 이론을 길게 쓰지 않는다.

---

# 16. 01장 — PDMG Executive Architecture Overview

## 핵심 질문

```text
PDMG 전체는 무엇이며 어떤 구조로 동작하는가?
```

이 장은 전체 문서 중 가장 중요하다.

이 장만 읽어도 PDMG의 전체 Architecture를 설명할 수 있어야 한다.

## 필수 TEXT 그림

### FIG-01-01 PDMG 전체 Architecture 한 장

```text
User / Browser
      ↓
pdmg-ui
      ↓
Authentication / pdmg-jwt
      ↓
pdmg-service
      │
      ├─ pdmg-fw
      │
      ├─ Handler / Facade / Service
      │
      └─ DAO / Mapper
      ↓
Database / RDW
      ↓
External Interface / Event / Data
      ↓
OM / Monitoring / Deployment
```

단, 실제 호출방향은 Source Evidence에 맞게 재작성한다.

### 추가 필수 그림

```text
FIG-01-02 PDMG 5 Module Map
FIG-01-03 Process / JVM / Data Boundary
FIG-01-04 Request-to-DB Big Flow
FIG-01-05 Security / Runtime / Operations Overlay
FIG-01-06 PDMG Known GAP Map
```

## Drill-down

```text
L0 PDMG Landscape
→ L1 Module
→ L2 Runtime
→ L3 Business Layer
→ L4 Request Flow
→ L5 Source Evidence
```

## 장 종료

```text
Architecture PASS
PDMG Current Conformance
Top 10 GAP
Next Chapter Handoff
```

---

# 17. 02장 — PDMG System Context & Boundary

## 핵심 질문

```text
누가 PDMG를 호출하고 PDMG는 어떤 시스템을 호출하는가?
```

## 작성범위

```text
User
Browser
pdmg-ui
pdmg-jwt
pdmg-service
Database
External API
File
Event
OM
Monitoring
```

## 필수 TEXT 그림

```text
FIG-02-01 PDMG System Context
FIG-02-02 Inbound / Outbound Boundary
FIG-02-03 Process Boundary
FIG-02-04 Data Boundary
FIG-02-05 Security Boundary
FIG-02-06 Observability Boundary
FIG-02-07 Forbidden Boundary
```

## 필수 구분

```text
HTTP Boundary
Module Boundary
JVM Boundary
DB Boundary
External Interface Boundary
Security Trust Boundary
```

## 금지

```text
External → DB Direct
UI → DAO
Module = Server
```

을 정상패턴으로 표현하지 않는다.

---

# 18. 03장 — PDMG Application / Module Architecture

## 핵심 질문

```text
PDMG 내부 Application 책임은 어떻게 나뉘는가?
```

## 필수 내용

```text
pdmg-ui
pdmg-jwt
pdmg-fw
pdmg-service
pdmg-om

Build Dependency
Process Boundary
Spring Context
Package Root
Layer
Component
Business Responsibility
```

## 필수 그림

```text
FIG-03-01 5 Module Architecture
FIG-03-02 Module Responsibility
FIG-03-03 Build Dependency
FIG-03-04 Runtime Process
FIG-03-05 Spring Context
FIG-03-06 Framework vs Business
FIG-03-07 pdmg-service Package
FIG-03-08 Handler → Facade → Service → DAO
FIG-03-09 ServiceId → Package → Mapper
FIG-03-10 TCF ON/OFF
FIG-03-11 Forbidden Dependency
```

## 필수 Rule

```text
Controller → DAO        MUST NOT
Handler → DAO           MUST NOT
Handler → Mapper        MUST NOT

Handler → Facade        normal
Facade → Service        normal
Service → DAO           normal
DAO → Mapper            normal
```

---

# 19. 04장 — PDMG Logical Technical Architecture

## 핵심 질문

```text
PDMG의 기술책임은 어떤 Logical Node / Runtime Capability로 나뉘는가?
```

## 작성대상

```text
Client/UI
Authentication
Application Runtime
Framework Runtime
DB Access
Data
Operations
External Integration
```

## 필수 그림

```text
FIG-04-01 Logical Technical Architecture
FIG-04-02 Logical Node Responsibility
FIG-04-03 Application vs Technical Node
FIG-04-04 Module vs Technical Node
FIG-04-05 Technology Capability Map
FIG-04-06 Allowed / Forbidden Path
FIG-04-07 Physical Handoff
```

## 절대 구분

```text
Logical Node ≠ Hostname
Application ≠ Logical Node
Technology Component ≠ Product
```

---

# 20. 05장 — PDMG Physical / Infrastructure Architecture

## 핵심 질문

```text
PDMG Logical Architecture가 실제 어디에 어떻게 배치되는가?
```

## 작성대상

```text
Environment
Center
GSLB
L4
WEB
WAS
VM
JVM
WAR
DB
Network
Port
Storage
Monitoring
Backup
DR
```

## 필수 그림

```text
FIG-05-01 Logical → Physical Mapping
FIG-05-02 Standard Online Physical Path
FIG-05-03 Server / VM / JVM / WAR
FIG-05-04 WEB/WAS Deployment
FIG-05-05 DB Deployment
FIG-05-06 Network / Port
FIG-05-07 Main / DR
FIG-05-08 Infrastructure Inventory
```

## 필수 표현

```text
Server
  ↓
VM
  ↓
JVM
  ↓
WAR
```

## 상태원칙

실제 Host/CPU/Memory/Port Evidence가 없으면:

```text
[OPEN]
```

으로 둔다.

---

# 21. 06장 — PDMG Interface Architecture

## 핵심 질문

```text
PDMG는 외부/내부 시스템과 어떤 Contract로 연결되는가?
```

## 작성내용

```text
Inbound Interface
Outbound Interface
Online API
Event
CDC
ETL
File
Direct DB
SYNC / ASYNC
Contract
Timeout
Retry
Idempotency
Error
Security
Trace
```

## 필수 그림

```text
FIG-06-01 Interface Context
FIG-06-02 Interface Decision Tree
FIG-06-03 Sync vs Async
FIG-06-04 API Contract
FIG-06-05 Event
FIG-06-06 CDC
FIG-06-07 File / ETL
FIG-06-08 Timeout / Retry
FIG-06-09 Interface Trace
FIG-06-10 Forbidden P2P / Direct DB
```

---

# 22. 07장 — PDMG Data Architecture

## 핵심 질문

```text
PDMG는 어떤 데이터를 소유·조회·변경하며 DB와 어떻게 연결되는가?
```

## 작성내용

```text
Data Ownership
Datasource
RDW / ADW
DAO
Mapper
SqlId
SQL
Table
Read / Write
Transaction
Data Boundary
Lineage
```

## 필수 그림

```text
FIG-07-01 Application → Data
FIG-07-02 ServiceId → DAO → Mapper → SQL → Table
FIG-07-03 Datasource / TransactionManager
FIG-07-04 Read / Write Ownership
FIG-07-05 RDW / ADW Position
FIG-07-06 Cross-System DB Boundary
FIG-07-07 Data Lineage
FIG-07-08 Large Query / Performance
```

---

# 23. 08장 — PDMG Framework / Mechanism Architecture

## 핵심 질문

```text
PDMG 거래를 공통으로 통제하는 Mechanism은 무엇인가?
```

## 작성대상

```text
Filter
Context
Security
Interceptor
TCF
Dispatcher
Timeout
Transaction
Error
Logging
GUID
ImageLog
```

## 필수 그림

```text
FIG-08-01 Framework Mechanism
FIG-08-02 System Pre-processing
FIG-08-03 TCF
FIG-08-04 Timeout
FIG-08-05 Transaction
FIG-08-06 Error
FIG-08-07 Logging
FIG-08-08 Context Lifecycle
FIG-08-09 Framework vs Business Boundary
```

---

# 24. 09장 — PDMG Online Runtime Architecture

## 핵심 질문

```text
온라인 거래 1건이 실제로 어떻게 실행되는가?
```

이 장은 **Sequence 중심**으로 작성한다.

## 최상위 Sequence

```text
Browser
 ↓
Filter
 ↓
Security
 ↓
MVC
 ↓
Controller
 ↓
TCF
 ↓
Timeout Worker
 ↓
Transaction
 ↓
Handler
 ↓
Facade
 ↓
Service
 ↓
DAO
 ↓
DB
 ↓
Response
```

## 필수 그림

```text
FIG-09-01 End-to-End Runtime
FIG-09-02 Request Thread
FIG-09-03 Worker Thread
FIG-09-04 Dispatcher / Handler
FIG-09-05 Business Layer
FIG-09-06 DB
FIG-09-07 Response
FIG-09-08 Error
FIG-09-09 Context Clear
FIG-09-10 TCF OFF
```

---

# 25. 10장 — Transaction / Timeout / Thread / DB Architecture

## 핵심 질문

```text
Thread, Transaction, Timeout, DB Work는 어디에서 시작되고 끝나는가?
```

## 필수 그림

```text
FIG-10-01 Request Thread vs Worker
FIG-10-02 TransactionTemplate Boundary
FIG-10-03 Facade REQUIRED Join
FIG-10-04 Timeout Lifecycle
FIG-10-05 Late Commit
FIG-10-06 JDBC Query Timeout
FIG-10-07 Cancel(true) Limitation
FIG-10-08 Hikari / DB Session
FIG-10-09 Overload / Queue
```

## 반드시 설명

```text
HTTP 504
≠ Worker End
≠ JDBC Cancel
≠ DB Rollback Complete
```

---

# 26. 11장 — Security / SSO / JWT / Session

## 핵심 질문

```text
PDMG의 신뢰경계와 인증/인가/Token/Session 구조는 무엇인가?
```

## 필수 그림

```text
FIG-11-01 Security Big Picture
FIG-11-02 Login
FIG-11-03 SSO
FIG-11-04 Access / Refresh Token
FIG-11-05 JWT Issue
FIG-11-06 JWT Verify
FIG-11-07 JWKS / kid
FIG-11-08 Identity Binding
FIG-11-09 Authorization
FIG-11-10 Session / State
FIG-11-11 Key HA/DR
FIG-11-12 Critical Security GAP
```

## Critical GAP 재검증

```text
RS256 Issuer
vs
HMAC Verifier
```

해소 여부를 Source로 확인한다.

---

# 27. 12장 — Standard Message / Context / Error / Logging

## 핵심 질문

```text
거래정보를 어떻게 전달·유지·오류처리·추적하는가?
```

## 필수 그림

```text
FIG-12-01 Request Envelope
FIG-12-02 Header
FIG-12-03 GUID
FIG-12-04 ServiceContext
FIG-12-05 ThreadLocal Lifecycle
FIG-12-06 Worker Context Propagation
FIG-12-07 Success Envelope
FIG-12-08 Error Envelope
FIG-12-09 MVC Error vs Filter Error
FIG-12-10 MDC / Logging
FIG-12-11 ImageLog
```

---

# 28. 13장 — Event / CDC / ETL / Batch / File / Cache

## 핵심 질문

```text
온라인 이외 실행형태를 PDMG와 주변 Architecture에서 어떻게 다루는가?
```

현재 PDMG Source에 직접 구현되지 않은 영역은 반드시:

```text
PDMG Current
[UNKNOWN / NOT IN SCOPE]

NSIGHT / Platform Relation
[REFERENCE]
```

으로 분리한다.

## 필수 그림

```text
FIG-13-01 Runtime Type Map
FIG-13-02 Event
FIG-13-03 CDC
FIG-13-04 ETL
FIG-13-05 Batch
FIG-13-06 File
FIG-13-07 Cache
FIG-13-08 Retry / Replay / Reconciliation
```

---

# 29. 14장 — DevOps / Deployment / OM / Observability

## 핵심 질문

```text
PDMG는 어떻게 Build·Deploy·관찰·운영·복구되는가?
```

## 필수 그림

```text
FIG-14-01 Source → Build → Artifact
FIG-14-02 CI
FIG-14-03 Deployment
FIG-14-04 Artifact Promotion
FIG-14-05 Deployment Manifest
FIG-14-06 OM
FIG-14-07 Metric / Log / Trace
FIG-14-08 Alert / Runbook
FIG-14-09 Rollback
FIG-14-10 Runtime Evidence
```

## 주의

```text
pdmg-om Current Detail
```

은 Source Evidence가 없으면 `[UNKNOWN]`.

---

# 30. 15장 — Naming / Application Code / Development Standard

## 핵심 질문

```text
PDMG Source를 일관되게 식별하고 개발자가 어떤 규칙을 따라야 하는가?
```

## 필수 내용

```text
Application Code
Program ID
ServiceId
Package
Handler
Facade
Service
DAO
DTO
Mapper
SqlId
Config
Artifact
Deployment
Host/JVM
```

## 필수 그림

```text
FIG-15-01 Naming Backbone
FIG-15-02 Program ID
FIG-15-03 ServiceId
FIG-15-04 Package
FIG-15-05 Class Naming
FIG-15-06 Mapper Naming
FIG-15-07 Source Trace
FIG-15-08 CI Naming Gate
```

## 핵심 Trace

```text
Business
 ↓
Program
 ↓
ServiceId
 ↓
Package / Class
 ↓
Mapper / SqlId
 ↓
Data
 ↓
Artifact
 ↓
Runtime
```

---

# 31. 16장 — Capacity / Performance / HA / DR

## 핵심 질문

```text
PDMG는 얼마만큼 처리할 수 있고 장애 시 어떻게 살아남는가?
```

## 작성대상

```text
TPS
Concurrency
Tomcat Thread
Worker
Queue
Hikari
DB Session
CPU
Memory
GC
VM
N+1
HA
DR
RTO/RPO
```

## 필수 그림

```text
FIG-16-01 Capacity Chain
FIG-16-02 Thread → Worker → Hikari → DB
FIG-16-03 Saturation Cascade
FIG-16-04 Scale-up vs Scale-out
FIG-16-05 N+1
FIG-16-06 WAS Failure
FIG-16-07 DB Failure
FIG-16-08 DR
FIG-16-09 Backup / Restore
```

## Capacity 값

Candidate는 반드시:

```text
[CANDIDATE]
```

Current Runtime 값은:

```text
[AS-IS]
```

로 분리한다.

---

# 32. 17장 — Traceability / Conformance / PASS / GAP / ADR

## 핵심 질문

```text
PDMG Architecture가 실제 구현과 일치함을 어떻게 증명하는가?
```

## 필수 그림

```text
FIG-17-01 Requirement → Runtime Trace
FIG-17-02 Source → Runtime Trace
FIG-17-03 Architecture Model
FIG-17-04 Conformance Rule
FIG-17-05 PASS Matrix
FIG-17-06 Drift
FIG-17-07 GAP
FIG-17-08 ADR
FIG-17-09 Gate G00~HG90
```

## 필수 Trace

```text
Requirement
  ↓
Architecture
  ↓
Program
  ↓
ServiceId
  ↓
Handler
  ↓
Facade
  ↓
Service
  ↓
DAO
  ↓
Mapper
  ↓
SQL
  ↓
Table
  ↓
Artifact
  ↓
Deployment
  ↓
GUID
  ↓
Runtime Evidence
```

## PASS Matrix

Architecture Decision Register의 모든 Task를 다음으로 정리한다.

| Task | Architecture PASS | PDMG Current | GAP | 전환조건 |
|---|---|---|---|---|

---

# 33. 18장 — Integrated PDMG Architecture Baseline

## 핵심 질문

```text
앞의 모든 Architecture를 하나의 PDMG Baseline으로 어떻게 묶는가?
```

## 필수 그림

### FIG-18-01 PDMG Master Architecture

다음 모든 계층을 한 장에서 연결한다.

```text
User / Channel
      ↓
UI / Authentication
      ↓
Application
      ↓
Framework
      ↓
Runtime
      ↓
Data / Interface
      ↓
Infrastructure
      ↓
Operations
      ↓
Evidence
```

### FIG-18-02 Full Drill-down

```text
L0 PDMG
 ↓
L1 System
 ↓
L2 Module / Technical Node
 ↓
L3 Layer / Component
 ↓
L4 Runtime
 ↓
L5 Source / Evidence
```

### 추가 필수 그림

```text
FIG-18-03 Security Overlay
FIG-18-04 Data / Interface Overlay
FIG-18-05 Infrastructure Overlay
FIG-18-06 Operations Overlay
FIG-18-07 PASS / GAP Heatmap
FIG-18-08 Final Closed Loop
```

---

# 34. 각 장의 Figure Plan 작성

장 시작부에는 반드시 Figure Plan을 둔다.

예:

| FIG | 제목 | Level | 핵심질문 | Evidence | 필수 |
|---|---|---:|---|---|---|
| FIG-09-01 | Online Runtime | L0~L4 | 요청은 어떻게 실행되는가 | Source | Y |
| FIG-09-02 | Filter | L3~L5 | 시스템 선처리는 무엇인가 | Source | Y |
| FIG-09-03 | Worker | L3~L5 | Thread 경계는 어디인가 | Config | Y |

---

# 35. 각 장의 Evidence Register 작성

각 장은 다음 형식으로 시작한다.

| Evidence ID | 자료 | 사용목적 | 상태 |
|---|---|---|---|
| EV-xx-01 | Source | Component 확인 | `[FACT]` |
| EV-xx-02 | Config | 설정 확인 | `[FACT]` |
| EV-xx-03 | Runtime | 실행 증거 | `[CONFIRMED]` |
| EV-xx-04 | Architecture Decision | Target Alignment | `[DECISION]` |

---

# 36. 각 장의 PASS Summary

장 마지막에 반드시 다음을 둔다.

```text
Architecture Definition     : PASS / CONDITIONAL PASS / OPEN / FAIL
Current PDMG Conformance    : PASS / PARTIAL / GAP / CONFLICT / UNKNOWN
Runtime Evidence Coverage   : HIGH / MEDIUM / LOW
Critical GAP Count          : N
OPEN Decision Count         : N
```

표:

| 영역 | Architecture | PDMG 구현 | Evidence | 판정 |
|---|---|---|---|---|

---

# 37. GAP / RISK / OPEN / CONFLICT 작성

## GAP

```text
Expected
   ↓
Actual
   ↓
Difference
```

## CONFLICT

```text
Evidence A
     ≠
Evidence B
```

## OPEN

```text
Decision Required
```

## RISK

```text
Cause
  ↓
Failure
  ↓
Impact
```

---

# 38. ADR 작성규칙

모든 중요한 미결사항은 ADR Candidate로 만든다.

```text
Context
 ↓
Decision Question
 ↓
주안
 ├─ 장점
 └─ 단점
 ↓
대안
 ├─ 장점
 └─ 단점
 ↓
Evidence
 ↓
Decision
```

현재 Architecture Decision Register와 반드시 연결한다.

---

# 39. Source Evidence 작성규칙

클래스명만 나열하지 않는다.

다음 형태로 Architecture 의미를 설명한다.

```text
Class / Config
      ↓
Architecture Role
      ↓
Runtime Position
      ↓
Dependency
      ↓
Evidence Status
```

예:

```text
OnlineTimeoutExecutor
      ↓
Worker Execution Boundary
      ↓
Request Thread와 분리
      ↓
TransactionTemplate 실행
      ↓
[AS-IS]
```

---

# 40. Code Diagram 작성규칙

Code 수준은 Architecture 설명 후 마지막 Drill-down에서 사용한다.

잘못된 시작:

```text
DefaultFilter.java
OnlineTransactionController.java
...
```

올바른 순서:

```text
System Context
 ↓
Application Runtime
 ↓
Framework Mechanism
 ↓
Runtime Sequence
 ↓
Class / Method
```

---

# 41. 표 작성규칙

표는 비교/Inventory/Decision에 사용한다.

표가 Architecture 그림을 대체하지 않는다.

대표 표:

```text
Module Responsibility Matrix
Layer Responsibility Matrix
Runtime Component Matrix
Interface Inventory
Data Access Matrix
HW/SW Matrix
Decision PASS Matrix
GAP Register
Risk Register
Evidence Register
```

---

# 42. 문서 중복 통제

같은 내용을 여러 장에서 반복하지 않는다.

예:

```text
Module 상세
→ 03장

Timeout 상세
→ 10장

JWT 상세
→ 11장

Message/Error 상세
→ 12장

Capacity 상세
→ 16장
```

다른 장에서는:

```text
요약 그림
+
Cross Reference
```

만 제공한다.

---

# 43. Top-down Handoff 규칙

각 장 마지막에는 다음 장으로 연결하는 그림을 둔다.

예:

```text
Application Architecture
        ↓
Logical Technical Architecture
        ↓
Next Chapter
```

문서 전체가 한 개의 Journey처럼 이어져야 한다.

---

# 44. Architecture Storyline

전체 문서는 다음 Story로 읽혀야 한다.

```text
PDMG란 무엇인가?
   ↓
어디에 위치하는가?
   ↓
어떤 모듈과 책임이 있는가?
   ↓
어떤 기술구조로 실행되는가?
   ↓
어디에 배치되는가?
   ↓
무엇과 어떻게 연결되는가?
   ↓
데이터는 어떻게 접근하는가?
   ↓
Framework가 무엇을 통제하는가?
   ↓
요청 한 건은 어떻게 실행되는가?
   ↓
Thread/TX/Timeout은 어떻게 동작하는가?
   ↓
Security는 어떻게 적용되는가?
   ↓
Context/Error/Log는 어떻게 흐르는가?
   ↓
Batch/Event/File은 어떻게 연계되는가?
   ↓
어떻게 Build/Deploy/운영하는가?
   ↓
개발자는 어떤 표준을 따라야 하는가?
   ↓
얼마나 처리하고 장애를 어떻게 견디는가?
   ↓
설계대로 구현됐음을 어떻게 증명하는가?
   ↓
PDMG Architecture Baseline
```

---

# 45. 최종 품질 Gate

완료 전 자동/수동으로 다음을 검증한다.

## 45.1 Visual Coverage

```text
번호가 있는 주요 Architecture 절
        ↓
TEXT Diagram 존재?
```

결과:

```text
Missing Diagram = 0
```

이어야 한다.

## 45.2 Evidence Coverage

모든 핵심 수치/구현 주장:

```text
Source
Config
Runtime
Document
Decision
```

중 최소 하나의 근거를 가져야 한다.

## 45.3 State Coverage

확인되지 않은 값을:

```text
[UNKNOWN]
[OPEN]
```

없이 단정하지 않는다.

## 45.4 Architecture / Implementation 분리

다음이 섞이면 FAIL:

```text
Architecture PASS
=
Implementation PASS
```

## 45.5 Markdown Quality

```text
Code Fence Balanced
Table Header Valid
Figure Number Unique
Chapter Number Unique
Cross Reference Valid
```

---

# 46. 최종 산출파일

최종 작업 시 다음 파일을 생성한다.

```text
00_PDMG_ARCHITECTURE_MASTER_INDEX.md

01_PDMG_EXECUTIVE_ARCHITECTURE.md
02_PDMG_SYSTEM_CONTEXT_BOUNDARY.md
03_PDMG_APPLICATION_MODULE_ARCHITECTURE.md
04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE.md
05_PDMG_PHYSICAL_INFRASTRUCTURE_ARCHITECTURE.md
06_PDMG_INTERFACE_ARCHITECTURE.md
07_PDMG_DATA_ARCHITECTURE.md
08_PDMG_FRAMEWORK_MECHANISM_ARCHITECTURE.md
09_PDMG_ONLINE_RUNTIME_ARCHITECTURE.md
10_PDMG_TRANSACTION_TIMEOUT_THREAD_DB_ARCHITECTURE.md
11_PDMG_SECURITY_SSO_JWT_SESSION_ARCHITECTURE.md
12_PDMG_MESSAGE_CONTEXT_ERROR_LOGGING_ARCHITECTURE.md
13_PDMG_EVENT_CDC_ETL_BATCH_FILE_CACHE_ARCHITECTURE.md
14_PDMG_DEVOPS_OM_OBSERVABILITY_ARCHITECTURE.md
15_PDMG_NAMING_CODE_DEVELOPMENT_STANDARD.md
16_PDMG_CAPACITY_PERFORMANCE_HA_DR_ARCHITECTURE.md
17_PDMG_TRACEABILITY_PASS_GAP_ADR.md
18_PDMG_INTEGRATED_ARCHITECTURE_BASELINE.md

PDMG_ARCHITECTURE_FINAL_INTEGRATED.md
PDMG_ARCHITECTURE_PASS_MATRIX.xlsx
PDMG_ARCHITECTURE_MANIFEST.json
PDMG_ARCHITECTURE_FINAL.zip
```

---

# 47. Master Index 형식

```text
PDMG ARCHITECTURE
      ↓
01 OVERVIEW
      ↓
02 CONTEXT
      ↓
03 APPLICATION
      ↓
04 LOGICAL
      ↓
05 PHYSICAL
      ↓
06 INTERFACE
      ↓
07 DATA
      ↓
08 MECHANISM
      ↓
09 RUNTIME
      ↓
10 TRANSACTION / TIMEOUT
      ↓
11 SECURITY
      ↓
12 MESSAGE / ERROR / LOG
      ↓
13 EVENT / BATCH
      ↓
14 DEVOPS / OPS
      ↓
15 STANDARD
      ↓
16 CAPACITY / HA / DR
      ↓
17 TRACE / PASS / ADR
      ↓
18 BASELINE
```

---

# 48. 문서의 최종 독자

다음 사람이 모두 읽을 수 있어야 한다.

```text
PM / PMO
EA
Application Architect
Technical Architect
Infrastructure Architect
Security Architect
Data Architect
Interface Architect
Developer
Framework Developer
DBA
Operations
DevOps
Reviewer / Auditor
```

따라서:

```text
그림은 이해 가능해야 하고
세부 내용은 검증 가능해야 한다.
```

---

# 49. 최종 성공기준

이 정의서는 다음 질문에 모두 답해야 한다.

```text
PDMG는 무엇인가?
어떤 모듈로 구성되는가?
어떤 Process/JVM에서 실행되는가?
Spring Context는 어떻게 구성되는가?
Framework와 Business 책임은 무엇인가?
Program/ServiceId는 어떻게 라우팅되는가?
요청 한 건은 어떻게 실행되는가?
Transaction은 어디서 시작되는가?
Timeout은 무엇을 중단하는가?
DB는 어떻게 접근되는가?
Security/JWT/Session은 어떻게 동작하는가?
Message/Header/Context는 어떻게 흐르는가?
Error/Log는 어떻게 연결되는가?
Interface/Data/Batch/Event는 어떻게 연결되는가?
어디에 배포되는가?
용량/HA/DR은 어떻게 구성되는가?
어떻게 Build/Deploy/Monitoring하는가?
어떤 Naming/개발표준을 따라야 하는가?
어떤 Architecture Decision이 PASS인가?
어떤 Current GAP가 남아 있는가?
Source/Config/Runtime으로 어떻게 증명하는가?
```

하나라도 설명할 수 없다면 해당 영역은:

```text
[UNKNOWN]
[OPEN]
[GAP]
```

으로 명시하고 후속 Task를 만든다.

---

# 50. 실행지시

이 프롬프트를 받은 즉시 다음 순서로 수행한다.

```text
STEP 1
입력자료 전체 Inventory 작성

STEP 2
Evidence Priority / Version / 날짜 식별

STEP 3
PDMG Fact / AS-IS 추출

STEP 4
Architecture Domain별 분류

STEP 5
00 Master Index 초안

STEP 6
01 Executive Architecture 작성
→ 반드시 전체 TEXT 그림 먼저

STEP 7
02~18 순차 Drill-down

STEP 8
각 장 PASS / GAP / ADR 평가

STEP 9
Decision Register와 Cross Mapping

STEP 10
Integrated Baseline 생성

STEP 11
TEXT Diagram 누락 전수점검

STEP 12
Evidence / Conflict / Unknown 전수점검

STEP 13
최종 통합본 / Manifest / ZIP 생성
```

---

# 51. 최종 작성 명령

아래 명령을 최종 실행문으로 사용한다.

```text
업로드된 상호금융 정보계 Architecture 자료와
PDMG Source / Config / Runtime / 기존 분석 정의서 /
Application·Technical·Infrastructure·Interface·Data·Naming 별첨 /
Application Code / HW-SW Matrix /
Architecture Decision 및 PASS 평가자료를 모두 읽어라.

이번 작업의 주인공은 PDMG다.

PDMG를 일반적인 Spring Boot 시스템으로 추정하지 말고,
현재 Evidence에서 확인된 실제 구조를 중심으로
PDMG 전체 Architecture Definition을 작성하라.

문서는 반드시:

PDMG Overall
→ System Context
→ Application/Module
→ Logical Technical
→ Physical Infrastructure
→ Interface
→ Data
→ Framework Mechanism
→ Runtime
→ Transaction/Timeout/Thread/DB
→ Security
→ Message/Context/Error/Logging
→ Event/CDC/ETL/Batch/File/Cache
→ DevOps/OM/Observability
→ Naming/Application Code/Development Standard
→ Capacity/Performance/HA/DR
→ Traceability/PASS/GAP/ADR
→ Integrated Baseline

순으로 Top-down → Drill-down 하라.

모든 주요 절은 TEXT Architecture Diagram을 우선 작성하고,
설명과 표는 그림 다음에 작성하라.

PDMG의 Module / Process / JVM / Spring Context /
Layer / Component / ServiceId / Mapper / SQL /
Runtime / Security / Deployment / Evidence 경계를
절대로 혼동하지 마라.

확인되지 않은 내용은 창작하지 말고
[UNKNOWN], [OPEN], [GAP], [CONFLICT]로 표시하라.

각 장 마지막에는:

Architecture Definition PASS
PDMG Implementation Conformance
Runtime Evidence Coverage
Critical GAP
OPEN Decision
ADR Candidate
PASS 전환조건

을 반드시 작성하라.

최종 목표는
'PDMG를 설명하는 문서'가 아니라

PDMG Architecture
=
설명 가능
+ Source 추적 가능
+ Runtime 검증 가능
+ Architecture Decision 가능
+ PASS/GAP 판정 가능
+ 변경 시 Drift 탐지 가능

상태를 만드는 것이다.
```
