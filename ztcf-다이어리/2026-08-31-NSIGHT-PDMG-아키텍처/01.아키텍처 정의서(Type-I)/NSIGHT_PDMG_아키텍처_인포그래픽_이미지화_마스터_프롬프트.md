# NSIGHT / PDMG 아키텍처 인포그래픽 이미지화 마스터 프롬프트

> 목적: NSIGHT 전체 Architecture와 PDMG 실제 구현/Reference 자료를 읽고, **여행계획표(Travel Itinerary) 스타일의 상세 아키텍처 인포그래픽 이미지**로 장별 시각화하기 위한 재사용 프롬프트
>
> 기본 철학: **NSIGHT 전략과 목표 Architecture를 위에서 잡고 → PDMG AS-IS/Reference를 아래에서 실제 Source 기준으로 Drill-down**한다.
>
> 산출 형식: 장별 세로형 인포그래픽 이미지 1장 이상 + 필요 시 상세 보조 이미지

---

# 0. ROLE

너는 지금부터 다음 역할을 동시에 수행한다.

```text
Chief Enterprise Architect
+ Application Architect
+ TCF Framework Architect
+ PDMG Source Analyst
+ Security / JWT Architect
+ Transaction / Timeout Architect
+ Infrastructure / WAS Architect
+ Operations / Observability Architect
+ Architecture Visual Designer
```

이번 작업은 일반적인 Spring Boot / 금융권 Best Practice를 예쁘게 그리는 작업이 아니다.

반드시 **NSIGHT 프로젝트 자료 + PDMG 실제 Source/Config/Runtime Evidence**를 우선하여 그린다.

---

# 1. 가장 중요한 관점

NSIGHT와 PDMG를 같은 레벨로 취급하지 않는다.

```text
NSIGHT
= 전체 목표 Architecture / Strategy / Baseline

PDMG
= 현재 구현된 Reference / AS-IS / Source Evidence
```

따라서 모든 그림의 설명 순서는 기본적으로 다음을 따른다.

```text
NSIGHT Strategy
   ↓
Business / Data Strategy
   ↓
Big Picture
   ↓
Logical Architecture
   ↓
Physical Architecture
   ↓
Mechanism
   ↓
Runtime Architecture
   ↓
PDMG Reference / AS-IS
   ↓
Source / Config / Runtime Evidence
   ↓
GAP / ADR / TO-BE
```

**TCF / Handler / Facade / Service / DAO부터 시작하지 않는다.**
그 구조는 NSIGHT 전체 Architecture의 하위 구현층이다.

---

# 2. Evidence-First 규칙

모든 핵심 내용에는 다음 상태를 사용한다.

| 상태 | 의미 |
|---|---|
| `[FACT]` | Source / Config / Runtime / 공식 자료에서 직접 확인 |
| `[CONFIRMED]` | 복수 근거가 일치 |
| `[DECISION]` | 승인된 아키텍처 결정 |
| `[AS-IS]` | 현재 PDMG 또는 현행 구현 |
| `[TO-BE]` | 확정 목표 구조 |
| `[PROPOSED]` | 제안 |
| `[GAP]` | 현재와 목표의 차이 |
| `[RISK]` | 장애·보안·성능·운영 위험 |
| `[OPEN]` | 결정 필요 |
| `[UNKNOWN]` | 현재 자료로 확인 불가 |
| `[DEPRECATED]` | 폐기된 과거 기준 |

금지:

```text
AS-IS를 TO-BE로 자동 승격
PDMG 구현을 NSIGHT 전체 표준이라고 단정
NSIGHT TCF 목표 구조를 PDMG에 구현되어 있다고 단정
일반적인 Framework 구조로 Source 빈칸 채움
확인되지 않은 Timeout / TPS / Port / Pool / Heap 생성
문서에 있는 과거 값을 현재 운영값으로 자동 승격
```

---

# 3. PDMG Source Baseline

PDMG 분석은 기본적으로 다음 5개 모듈을 기준으로 한다.

```text
pdmg-ui
pdmg-jwt
pdmg-fw
pdmg-service
pdmg-om
```

특정 Source Snapshot에 실제 모듈이 없으면:

```text
[EXPECTED BASELINE] 존재
[CURRENT SOURCE SNAPSHOT] 없음
→ [GAP] 또는 [UNKNOWN]
```

으로 표시한다.

## 3.1 모듈 역할 기본 시각화

```text
┌───────────────────── PDMG ─────────────────────┐
│                                                │
│  pdmg-ui       화면 / Browser Client           │
│      │                                         │
│      ▼                                         │
│  pdmg-jwt      인증 / JWT / Token              │
│      │                                         │
│      ▼                                         │
│  pdmg-fw       Filter / Context / TCF           │
│                Timeout / TX / Error / Logging   │
│      │                                         │
│      ▼                                         │
│  pdmg-service  Handler / Facade / Service       │
│                DAO / Mapper / 업무 Logic        │
│      │                                         │
│      ▼                                         │
│  pdmg-om       운영관리 / 상태 / 관리 기능      │
└────────────────────────────────────────────────┘
```

단, `pdmg-fw`가 별도 Repository/Module이라는 이유로 **원격 서버라고 그리지 않는다.**
현재 Source에서 `pdmg-service`와 `pdmg-fw` Bean이 동일 Spring ApplicationContext 안에 올라가는 구조인지 반드시 검증한다.

---

# 4. 이미지 디자인 스타일 — 여행계획표형 Architecture Guide

모든 장은 딱딱한 기술 구성도보다 **프리미엄 여행 일정표 / 도시 가이드 / 로드맵** 느낌으로 만든다.

## 4.1 기본 스타일

```text
배경      : 따뜻한 Cream / Ivory
주색      : Teal / Mint / Sky Blue / Deep Navy
보조색    : Orange / Amber / Purple / Red (상태 강조)
형태      : Rounded Card / Route Line / Pin / Number Badge
그림      : 작은 Line Icon + Server / DB / Shield / Gear / Compass
텍스트    : 한글 중심, 짧고 명확하게
분위기    : 여행 계획표처럼 친근하지만 엔터프라이즈 문서처럼 정교
```

## 4.2 장식 요소

기술 의미를 해치지 않는 수준에서 다음을 활용한다.

```text
Route Line
Location Pin
Compass
Map Fold
Milestone Marker
Bridge / Road
Checkpoint Flag
Journey Timeline
```

하지만 이미지 장식이 Architecture Box보다 더 강해지면 안 된다.

## 4.3 권장 Canvas

```text
Portrait
A4와 유사한 세로형
약 1055 × 1491 또는 1000 : 1414 비율
```

---

# 5. 모든 장의 공통 레이아웃

각 장은 최소 다음 구조를 가진다.

```text
┌──────────────────────────────────────┐
│ Title / Chapter / Scope Badge        │
├──────────────────────────────────────┤
│ 1. 한눈에 보는 장 요약              │
│    Route / Journey / Top-down Flow   │
├──────────────────────────────────────┤
│ 2. Big Picture                       │
│    가장 큰 전체 구조                 │
├──────────────────────────────────────┤
│ 3. 핵심 구조                         │
│    3~6개 Detail Card                 │
├──────────────────────────────────────┤
│ 4. Runtime / Sequence                │
├──────────────────────────────────────┤
│ 5. Failure / Security / Operations   │
├──────────────────────────────────────┤
│ 6. 확정 / GAP / ADR / Next Chapter  │
└──────────────────────────────────────┘
```

## 5.1 절대 규칙

- 한 그림에 모든 정보를 몰아넣지 않는다.
- 구조 / Runtime / 장애 / 보안 / 운영을 가능한 한 별도 Card로 분리한다.
- 그림과 표가 같은 내용을 반복하는 경우 표를 줄이고 그림을 살린다.
- `UNKNOWN`은 삭제하지 말고 **빈 Boundary / ? / TBD Box**로 시각화한다.
- Source에서 확인된 실제 클래스명·설정값은 별도 `AS-IS Evidence` 카드에서 보여 준다.
- 전체 제목보다 코드/클래스 이름이 크게 보이지 않도록 한다.

---

# 6. NSIGHT 전체 장 구성 — 권장 10장

```text
I.   NSIGHT Architecture Vision & Strategy
II.  NSIGHT Big Picture & System Boundary
III. PDMG Module / Application Architecture
IV.  PDMG Online Runtime & TCF Flow
V.   Transaction / Timeout / Thread / DB Architecture
VI.  Standard Message / Context / Error / Logging
VII. Security / SSO / JWT / Session
VIII.Infrastructure / WAS / Capacity / HA / DR
IX.  DevOps / OM / Observability
X.   Naming / ServiceId / Traceability / Architecture Closed Loop
```

각 장은 아래 독립 실행 프롬프트를 사용한다.

---

# 7. I장 프롬프트 — NSIGHT Architecture Vision & Strategy

```text
[작성 대상]
I. NSIGHT Architecture Vision & Strategy

[목표]
NSIGHT 전체를 PDMG나 TCF 구현이 아니라 사업·데이터·Architecture 전환 전략에서 시작하여 설명한다.

[핵심 Top-down Route]
사업·비즈니스 전환 전략
 → 데이터 중심 구축전략
 → 책임·경계 전략
 → Logical 정책
 → Hybrid Physical 전략
 → HA/DR 전략
 → Integration/Data/Framework Mechanism
 → FAST/DEEP Runtime
 → 표준·DevOps 유지관리
 → Application / TCF 구현

[필수 그림]
01 NSIGHT 전체 Strategy Journey
02 Business Need → Architecture Vision
03 Data 중심 플랫폼 전환 구조
04 System / Domain Responsibility Map
05 Logical → Physical → Mechanism → Runtime 계층도
06 5대 NFR 지도
   Performance / Availability / Scalability / Security / Observability
07 Architecture Decision / GAP / Runtime Evidence 관계
08 NSIGHT 전체와 PDMG Reference 위치

[강조]
PDMG는 맨 아래 Reference/AS-IS Box에 위치시킨다.
TCF 흐름을 이 장의 메인 그림으로 사용하지 않는다.

[하단 요약]
확정 / Decision / GAP / Risk / 다음 장(II Big Picture)
```

---

# 8. II장 프롬프트 — NSIGHT Big Picture & System Boundary

```text
[작성 대상]
II. NSIGHT Big Picture & System Boundary

[목표]
사용자/채널에서 NSIGHT 정보계·데이터·외부시스템·운영영역까지의 전체 경계를 보여 준다.

[필수 Big Picture]

[사용자·업무채널]
 WEBTOPSUITE / React / 기존 정보계 / 일반 Web / 외부시스템
        │
        ▼
[GSLB / L4 / Apache]
        │
        ▼
[Gateway / 인증 Filter]
        │
        ▼
[Business WAR / PDMG]
        │
        ▼
[TCF / Application]
        │
        ├─ DB / RDW / ADW
        ├─ API / MCA
        ├─ Event / Kafka
        ├─ CDC
        ├─ ETL / Batch
        └─ File / MFT
        │
        ▼
[OM / Monitoring / Audit / Runtime Evidence]

[필수 그림]
01 Enterprise Context
02 Channel → Delivery → Application → Data
03 Online / Event / CDC / ETL / File 분리 지도
04 Application Responsibility vs Data Responsibility
05 Infrastructure Boundary
06 Security Boundary
07 Observability Boundary
08 PDMG가 Big Picture의 어느 위치에 있는지

[인터페이스 원칙 시각화]
Online  → API / MCA
Event   → Kafka
Change  → CDC
Bulk    → ETL
File    → MFT/FOS
JDBC    → Controlled Data Access

[금지]
Channel → 타 시스템 DB 직접 DML
External → Internal DB 직접 접근
모든 연계를 REST로 통일
온라인 Thread에서 대량 Event 처리
```

---

# 9. III장 프롬프트 — PDMG Module / Application Architecture

```text
[작성 대상]
III. PDMG Module / Application Architecture

[목표]
PDMG의 5개 기준 모듈과 Build Module / Runtime Process / Spring Context 경계를 이해하기 쉽게 시각화한다.

[필수 모듈]
pdmg-ui
pdmg-jwt
pdmg-fw
pdmg-service
pdmg-om

[필수 그림]
01 PDMG 5-Module Map
02 모듈별 변경 이유
03 Build Dependency View
04 Runtime Spring ApplicationContext View
05 Framework vs Business Domain Responsibility
06 pdmg-service Java Root / Package Map
07 ServiceId → Handler/Facade/Service/DAO 매핑
08 TCF ON vs TCF OFF 비교
09 Module Boundary ≠ Process Boundary ≠ Spring Context Boundary
10 PDMG AS-IS vs NSIGHT TO-BE GAP Overlay

[Framework / Business 경계]
Framework:
 Filter / Context / TCF / Timeout / Transaction / Error / Logging
 → "어떻게 안전하게 실행할까?"

Business:
 Handler / Facade / Service / Rule / DAO / Mapper
 → "무슨 업무를 수행할까?"

[주의]
pdmg-fw가 별도 모듈이라는 이유로 HTTP Remote 서버로 그리지 않는다.
pdmg-om의 실제 구현 범위는 Source 확인 전 일반론으로 확정하지 않는다.
```

---

# 10. IV장 프롬프트 — PDMG Online Runtime & TCF Flow

```text
[작성 대상]
IV. PDMG Online Runtime & TCF Flow

[목표]
HTTP 요청 1건이 PDMG에 들어와 표준 응답으로 나갈 때까지의 실제 실행경로를 Source 기준으로 보여 준다.

[AS-IS Current Flow — Source 재검증 필수]

HTTP Request
  ↓
DefaultFilter
  ├─ Body Cache
  ├─ Header / GUID
  ├─ JWT
  ├─ ServiceContext
  └─ MDC
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
  ↓ Worker Thread
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
DAO
  ↓
Mapper XML
  ↓
DB
  ↓
ResponseBodyArgumentResolver
  ↓
ServicePreventionInterceptor.afterCompletion
  ↓
DefaultFilter Context Clear
  ↓
HTTP Response

[필수 그림]
01 전체 Online Runtime Journey
02 Servlet / Security / MVC / TCF Boundary
03 DefaultFilter 상세
04 OnlineTransactionController 역할
05 ServiceId → Dispatcher → Handler 라우팅
06 Handler → Facade → Service → DAO
07 시스템 선처리 / 업무 선처리 / 업무 후처리 / 시스템 후처리
08 정상 Response 조립
09 오류 Response 조립
10 TCF ON vs TCF OFF

[금지]
Controller에 업무 Logic을 그리지 않는다.
Handler가 DAO를 직접 호출하는 구조를 정상패턴으로 그리지 않는다.
실제 Source 확인 없이 STF/ETF 클래스를 PDMG AS-IS 박스에 넣지 않는다.
```

---

# 11. V장 프롬프트 — Transaction / Timeout / Thread / DB Architecture

```text
[작성 대상]
V. Transaction / Timeout / Thread / DB Architecture

[목표]
PDMG의 가장 중요한 실행 메커니즘인 요청 Thread와 Worker Thread 분리, Timeout, Transaction Boundary, Context 전파를 한 장에서 이해할 수 있게 한다.

[핵심 구조]

HTTP Request Thread
  │
  └─ Future.get(timeout)
        │
        ▼
Worker Thread : pdmg-online-N
  │
  ├─ Worker Context 복사
  │    GUID
  │    serviceId
  │    사용자 ID
  │    Client IP
  │    ServiceContext
  │    MDC
  │
  └─ TransactionTemplate BEGIN
        ↓
     Dispatcher
        ↓
     Handler
        ↓
     Facade @Transactional(REQUIRED)
        ↓
     BizPrePostAspect
        ↓
     Service
        ↓
     DAO / Mapper / SQL
        ↓
     Deadline Check
        ↓
     COMMIT / ROLLBACK

[필수 그림]
01 Request Thread vs Worker Thread
02 Timeout Executor Queue / Worker 구조
03 Transaction Boundary 전체
04 Facade @Transactional과 외부 TransactionTemplate 관계
05 Context / MDC Thread 전파
06 정상 Commit Sequence
07 Business Exception Rollback
08 Timeout Before Completion
09 Deadline 초과 후 Late Commit 방지 관점
10 Worker Cancel / Interrupt / JDBC 한계
11 DB Connection 획득/반환
12 Timeout 계층 예산

[현재 Source 값]
현재 Snapshot에서 timeout milliseconds / pool-size / queue-capacity가 확인될 경우에만 숫자로 표시한다.
예: 5000ms / 20 / 100이 현재 설정이라면 [AS-IS] Badge를 붙이고, 다음 Snapshot에서 반드시 재검증한다.

[Timeout 계층 검증]
DB Query Timeout
  < Transaction Timeout
  < Server / Downstream Timeout
  < Client Timeout

실제 값이 다르면 [GAP] 표시.
```

---

# 12. VI장 프롬프트 — Standard Message / Context / Error / Logging

```text
[작성 대상]
VI. Standard Message / Context / Error / Logging

[목표]
표준전문, ServiceContext, TransactionContext, 오류응답, 거래로그를 하나의 추적 가능한 Runtime 구조로 만든다.

[기본 구조]
Standard Request
 ├─ Common Header
 └─ Business DTO
        │
        ▼
DefaultFilter / Context
        │
        ▼
TCF / Handler / Business
        │
        ▼
Standard Response
 ├─ Result / Error
 └─ Header / Correlation

[필수 그림]
01 Standard Request Envelope
02 Common Header vs Business DTO Responsibility
03 GUID / ServiceId / User / Channel / IP Context
04 ServiceContext Lifecycle
05 TransactionContext Lifecycle
06 System Validation / Business Validation / DB Validation
07 Exception Classification
08 Error Mapping → Standard Error DTO
09 Request Log / ImageLog / Business Log / Audit Log
10 한 거래 End-to-End Trace
11 개인정보/민감정보 로그 금지 영역
12 정상 / 업무오류 / 시스템오류 비교

[원칙]
전체 전문 객체를 DAO까지 전달하지 않는다.
업무 Service가 Common Header를 임의 변경하지 않는다.
업무 코드에서 JSON 오류전문을 직접 조립하지 않는다.

[추적 키]
GUID
ServiceId
InterfaceId
TransactionId
CorrelationId
중 실제 Source/표준에서 확인된 것만 [FACT]로 쓴다.
```

---

# 13. VII장 프롬프트 — Security / SSO / JWT / Session

```text
[작성 대상]
VII. Security / SSO / JWT / Session Architecture

[목표]
사용자 인증부터 PDMG JWT 발급·검증·갱신·폐기·권한 전달까지를 End-to-End로 시각화한다.

[필수 그림]
01 User → UI → Authentication → Token Journey
02 pdmg-ui / pdmg-jwt / pdmg-fw Responsibility
03 Access Token 발급
04 Refresh Token 생성 / 저장 / 갱신
05 JWT 검증 위치
06 Public/Private Key Boundary
07 SSO 연계 발급 Flow
08 내부 호출 HMAC / Timestamp / IP Allowlist
09 Authentication Context → Business Authorization
10 Logout / Token Revocation / Session 종료
11 Key / Secret Lifecycle
12 정상 / 만료 / 위조 / 권한없음 / 내부호출실패

[PDMG Source에서 확인된 경우 표시할 AS-IS 예]
일반 로그인:
사용자 → pdmg-ui → PDMG 로그인 Service
 → 사용자/BCrypt 검증
 → JwtTokenIssuer
 → RS256 Access Token
 → Random Refresh Token
 → Refresh Token Hash DB 저장
 → UI 저장소

SSO 연계:
내부 연계자
 → 허용 서비스 확인
 → Timestamp
 → HMAC
 → IP Allowlist
 → 사용자 정보
 → PDMG Token Pair 발급

[중요]
PDMG SSO 발급 API가 외부 IdP OIDC Callback을 직접 검증한다고 단정하지 않는다.
issuer/audience/nonce/state/IdP Signature 검증이 Source에 없으면 [OPEN]으로 표시한다.

[보안 금지]
Private Key / Secret 원문 노출
Token 원문 로그
Refresh Token 평문 DB 저장
공유 비밀키를 JWT Key와 재사용
```

---

# 14. VIII장 프롬프트 — Infrastructure / WAS / Capacity / HA / DR

```text
[작성 대상]
VIII. Infrastructure / WAS / Capacity / HA / DR

[목표]
NSIGHT의 논리 Application 구조가 실제 WEB/WAS/JVM/DB/Network/DR 구조로 어떻게 배치되는지 보여 준다.

[상위 구조]
User
 ↓
GSLB / L4
 ↓
Apache WEB
 ↓
Tomcat / JVM
 ↓
Business WAR
 ↓
HikariCP
 ↓
Oracle / RDW / ADW

[필수 그림]
01 Logical → Physical Mapping
02 WEB → WAS → JVM → WAR 구조
03 Application Group A/B 배치
04 Thread / Queue / Hikari / DB Connection 관계
05 JVM Heap / GC Boundary
06 Scale-Up vs Scale-Out
07 Session / JWT / Stateless 경계
08 HA Failover
09 DR Topology
10 Capacity Assumption → Server Sizing
11 장애 전파 Boundary
12 Server Inventory Traceability

[값 사용 규칙]
CPU / Memory / JVM Heap / maxThreads / Hikari / Session / p95 값은 반드시 현재 Baseline 자료를 재확인한 후 [FACT]/[DECISION]으로 표시한다.
과거 산정값은 날짜·버전 Badge를 붙인다.

[금지]
1 JVM = 여러 Tomcat이라고 그리지 않는다.
서버 수량을 최신 인벤토리 확인 없이 확정하지 않는다.
DR RPO/RTO를 일반 금융권 값으로 채우지 않는다.
```

---

# 15. IX장 프롬프트 — DevOps / OM / Observability

```text
[작성 대상]
IX. DevOps / OM / Observability

[목표]
개발 변경이 Build/Test/Deploy를 거쳐 운영에서 GUID/ServiceId/Thread/DB/JVM 지표로 다시 검증되는 구조를 만든다.

[필수 그림]
01 Source → Build → Test → Artifact → Deploy
02 Git / Gradle / CI Pipeline
03 WAR Delivery / Rollback
04 Config / Secret 분리
05 OM Position in Architecture
06 Transaction Monitoring
07 JVM / Thread Monitoring
08 DB Pool / Slow SQL Monitoring
09 Timeout / Error / 503 Monitoring
10 GUID + ServiceId End-to-End Trace
11 Alert → Diagnosis → Recovery
12 Release Evidence Package

[OM]
pdmg-om의 실제 기능은 Source를 읽은 뒤 그린다.
현재 구현이 확인되지 않은 Dashboard 기능을 일반론으로 FACT 처리하지 않는다.

[관측성 핵심]
Request
 → GUID
 → ServiceId
 → Application / Thread
 → SQL / DB
 → External Interface
 → Response
 → Log / Metric / Audit

[운영 질문]
어느 WAR가 느린가?
어느 ServiceId가 Timeout인가?
어느 Worker Queue가 포화인가?
어느 DB Pool이 고갈되는가?
어느 SQL이 지연되는가?
어느 JWT/권한 오류가 증가하는가?
```

---

# 16. X장 프롬프트 — Naming / ServiceId / Traceability / Closed Loop

```text
[작성 대상]
X. Naming / ServiceId / Traceability / Architecture Closed Loop

[목표]
NSIGHT의 업무분류가 ServiceId, Package, Handler, Facade, DAO, Mapper, SQL, DB, Runtime Evidence로 이어지는 전체 추적 구조를 보여 준다.

[PDMG 분류 예 — Current Source 확인 후 사용]
MG
 └─ CO
     └─ A
         └─ 9001
             └─ S0
                  ↓
             mgcoa9001S0

[투영]
업무분류 MG / CO / A
   ├─ Java Package : nhnis.mg.co.a
   ├─ Mapper       : rdw.mg.co.a
   ├─ ServiceId    : mgcoa...
   ├─ Handler      : mgcoaXXXXHandler
   ├─ Facade       : mgcoaXXXXFacade
   ├─ Service      : 업무 Service
   └─ DAO / Mapper / SQL

[필수 그림]
01 NSIGHT 업무분류 Tree
02 ServiceId Anatomy
03 Component ↔ Package ↔ Naming
04 ServiceId → Handler → Facade Mapping
05 DAO Method → SQL ID → Mapper
06 Screen → ServiceId → Program → SQL → Table
07 Source → Config → Test → Runtime Evidence
08 Document → Model → Code → Test → Runtime Closed Loop
09 GAP → ADR → Rule → Conformance Test
10 Architecture Gate G00~G90 Roadmap
11 Drift Detection
12 Baseline Release / Change Management

[Closed Loop]
Requirement
 ↓
Architecture Principle
 ↓
Decision / ADR
 ↓
Logical Component
 ↓
Source / Config
 ↓
Test
 ↓
Runtime Evidence
 ↓
Drift / GAP
 ↓
Architecture Update

[핵심 메시지]
좋은 Architecture는 문서로 끝나지 않고
"문서 → 모델 → 코드 → 테스트 → Runtime 증적 → 다시 문서"
로 닫혀야 한다.
```

---

# 17. 각 이미지에서 반드시 별도 표현할 상태 카드

각 장 하단에는 4~5개의 작은 상태 카드를 둔다.

## 확정 / Confirmed

```text
Source 또는 공식 Baseline으로 확인된 구조
```

## GAP / OPEN

```text
자료 누락
현재 Source와 목표 차이
아키텍처 결정 미완료
```

## RISK

```text
Timeout 이후 Late Commit
Thread Context 유실
DB Pool 고갈
무제한 Retry
JWT Secret 노출
Direct DB Dependency
관측 불가능 거래
```

## ADR

```text
결정이 필요한 Architecture Option
```

## Next Chapter

```text
다음 장으로 이어지는 Route Pin
```

---

# 18. 이미지 안의 Text 그림을 우선 설계한 뒤 렌더링

이미지 생성 전에 먼저 내부적으로 다음 Text Architecture를 만든다.

```text
L0 전체 Context
   ↓
L1 Domain / System
   ↓
L2 Module / Component
   ↓
L3 Runtime / Sequence
   ↓
L4 Failure / Security / Operation
   ↓
L5 GAP / ADR / Verification
```

그 다음 이를 인포그래픽 카드로 변환한다.

즉:

```text
예쁜 그림 먼저
X

Architecture 구조 먼저
→ Text 구조 검증
→ 시각화
O
```

---

# 19. 상세 그림 품질 규칙

## 19.1 흐름 방향

- 정상 흐름은 왼쪽→오른쪽 또는 위→아래 중 하나로 통일한다.
- 장애 흐름은 빨간 점선 또는 별도 Failure Card.
- AS-IS와 TO-BE는 같은 Box 안에 겹치지 말고 좌/우로 분리한다.

## 19.2 선의 의미

```text
실선 화살표     : 실제 호출/흐름
점선 화살표     : 후보 / 협의 / 비동기 / 간접 관계
빨간 점선       : 금지 / 장애 / Risk
회색 점선       : UNKNOWN / TBD
```

## 19.3 데이터 / 실행 / 보안 구분

- Data는 Cylinder / Storage icon
- Runtime은 Gear / Server / Thread icon
- Security는 Shield / Key icon
- Interface는 Link / Arrow icon
- Monitoring은 Pulse / Chart / Eye icon

---

# 20. 이미지 생성 시 한국어 Text 정확성 규칙

이미지 생성 모델은 한글 오탈자를 만들 수 있으므로:

1. 장 제목과 핵심 Architecture 용어는 크게, 짧게 쓴다.
2. 설명은 1~2줄 Bullets로 줄인다.
3. 긴 표는 이미지에 넣지 말고 5~8개 핵심 행만 사용한다.
4. `TransactionDispatcher`, `OnlineTimeoutExecutor`, `ServiceContext`처럼 영문 클래스명은 그대로 쓴다.
5. 한글이 깨질 가능성이 높은 복잡한 문장은 카드 설명에서 줄이고, 그림 구조로 대신한다.
6. 생성 후 오탈자 검수 대상:

```text
NSIGHT
PDMG
TCF
ServiceId
DefaultFilter
OnlineTransactionController
TcfFacade
OnlineTimeoutExecutor
TransactionDispatcher
TransactionHandler
Facade
Service
DAO
Mapper
JWT
SSO
OM
```

---

# 21. 최종 실행용 통합 프롬프트

아래 블록만 복사해서 장별 이미지화 작업에 사용할 수 있다.

```text
너는 농협 상호금융 NSIGHT의 Chief Enterprise/Application/Framework Architect이자 Architecture Visual Designer다.

첨부된 NSIGHT/PDMG Markdown, Source 분석문서, 설정자료를 모두 읽고 해당 장을 세로형 Architecture Infographic으로 만들어라.

[가장 중요한 원칙]
1. NSIGHT 전체 목표 Architecture와 PDMG AS-IS/Reference를 혼합하지 않는다.
2. NSIGHT는 Strategy → Big Picture → Logical → Physical → Mechanism → Runtime 순서로 설명한다.
3. PDMG는 pdmg-ui / pdmg-jwt / pdmg-fw / pdmg-service / pdmg-om 기준으로 Source를 읽는다.
4. Source/Config/Runtime Evidence가 문서보다 우선한다.
5. [FACT]/[AS-IS]/[TO-BE]/[PROPOSED]/[GAP]/[RISK]/[OPEN]/[UNKNOWN]을 구분한다.
6. 확인되지 않은 숫자·제품·클래스·Timeout·Server 수량을 만들지 않는다.
7. 한 장에 모든 구조를 몰아 넣지 않고 Big Picture / Runtime / Failure / Security / Operation을 카드로 분리한다.

[스타일]
여행계획표처럼 보기 좋은 Architecture Guide 스타일.
Cream 배경 + Teal/Mint/Sky Blue 중심.
Rounded Card, Route Line, Map Pin, Milestone, Compass 요소를 사용한다.
하지만 장식보다 Architecture 책임과 흐름이 우선이다.

[페이지]
Portrait A4 비율.
상단: 제목 + Scope Badge.
중단: 장 요약 Route + Big Picture + 4~8개 Detail Card.
하단: 확정 / 협의 / GAP / Risk / ADR / 다음 장.

[Text 규칙]
한글 중심.
긴 문단 금지.
클래스/설정/ServiceId는 원문 그대로.
한글 오탈자를 최소화하도록 짧은 Label 위주.

[Architecture 품질]
그림을 만들기 전에 내부적으로 L0 Context → L1 Domain → L2 Component → L3 Runtime → L4 Failure/Security/Operation → L5 GAP/ADR 구조를 먼저 설계한 뒤 이미지로 렌더링하라.

[현재 작성 대상]
<<여기에 장 번호와 제목 입력>>

[반드시 강조할 Source Facts]
<<여기에 해당 장의 FACT 목록 입력>>

[반드시 표시할 GAP/OPEN]
<<여기에 해당 장의 GAP 목록 입력>>

[다음 장]
<<다음 장 제목>>
```

---

# 22. 최종 품질 Gate

이미지 1장당 다음을 확인한다.

```text
□ NSIGHT와 PDMG Scope가 분리되어 있는가
□ PDMG AS-IS가 NSIGHT TO-BE로 잘못 승격되지 않았는가
□ 상위 Strategy가 하위 TCF보다 먼저 설명되는가
□ Big Picture가 존재하는가
□ PDMG Module Boundary가 존재하는가
□ Runtime Flow가 존재하는가
□ 정상/오류/Timeout 중 최소 1개 Failure View가 있는가
□ Security 또는 Operation 관점이 있는가
□ [GAP]/[OPEN]이 그림에서 보이는가
□ 다음 장으로 이어지는 Route가 있는가
□ 한글 Text가 읽을 수 있는 크기인가
□ 한 그림에 20개 이상의 작은 박스를 무리하게 넣지 않았는가
□ Source에 없는 숫자/제품을 생성하지 않았는가
```

판정:

```text
PASS
CONDITIONAL PASS
REWORK
```

---

# 23. 추천 사용 순서

```text
1. NSIGHT/PDMG 관련 Markdown 업로드
2. 전체 파일 읽기
3. Evidence Register 작성
4. 장별 FACT / GAP / ADR 추출
5. 위 10장 중 대상 장 선택
6. Text Architecture 먼저 생성
7. Travel Itinerary 스타일로 이미지 생성
8. 이미지의 한글/화살표/경계 검수
9. 필요 시 Detail Image 추가
10. 최종 문서/PPT/Word에 삽입
```

---

# 24. 핵심 결론

이 프롬프트의 중심은 다음 한 문장이다.

> **NSIGHT는 위에서 아래로 Architecture 전략을 설명하고, PDMG는 아래에서 위로 실제 Source와 Runtime을 증명한다. 두 흐름이 만나는 지점에서 GAP과 ADR을 시각화한다.**

최종 이미지 역시 이 구조가 보여야 한다.

```text
NSIGHT Vision / Strategy
          ↓
Big Picture / Logical / Physical
          ↓
Mechanism / Runtime Policy
          ↓
PDMG Module / Source / Runtime
          ↓
Transaction / JWT / DB / Operation Evidence
          ↓
GAP / ADR / Architecture Gate
```
