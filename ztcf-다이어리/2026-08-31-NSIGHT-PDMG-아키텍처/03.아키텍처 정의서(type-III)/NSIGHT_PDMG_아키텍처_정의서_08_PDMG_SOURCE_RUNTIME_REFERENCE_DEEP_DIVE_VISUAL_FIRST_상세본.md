# NSIGHT / PDMG 아키텍처 정의서
# 08. PDMG SOURCE / RUNTIME REFERENCE DEEP DIVE
## Visual-First / Bottom-up Evidence Drill-down 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-VISUAL-PDMG-REFERENCE-08`  
> Architecture Level: **L7 — PDMG SOURCE / RUNTIME REFERENCE**  
> 문서 상태: **Draft / AS-IS Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_07_TRACEABILITY_EVIDENCE_CLOSED_LOOP_VISUAL_FIRST_TOPDOWN_상세본.md`  
> 본 장 역할: **NSIGHT Target을 PDMG Source/Config/Runtime으로 Bottom-up 검증하는 Reference 장**

---

# 0. 이 장을 읽는 방법

1~7장은 NSIGHT Target을 위에서 아래로 설명했다.

8장은 반대로 실제 PDMG를 아래에서 위로 읽는다.

```text
SOURCE
  ↓
MODULE
  ↓
PACKAGE
  ↓
CLASS
  ↓
SERVICE ID
  ↓
RUNTIME
  ↓
CONFIG
  ↓
DB / SQL
  ↓
SECURITY / LOG
  ↓
EVIDENCE
  ↓
NSIGHT TARGET과 비교
```

이 장에서 가장 중요한 원칙은 다음이다.

```text
PDMG에서 실제로 구현됨
       │
       ▼
[AS-IS]

NSIGHT에서 목표로 함
       │
       ▼
[TO-BE]

둘이 다름
       │
       ▼
[GAP]
```

---

# 1. VISUAL ROUTE — PDMG Reference 전체를 한 장으로 보기

## FIG-PDMG-01. PDMG Source-to-Runtime Journey

```text
╔══════════════════════════════════════════════════════════════════════════════╗
║                       PDMG SOURCE / RUNTIME REFERENCE                       ║
╚══════════════════════════════════════════════════════════════════════════════╝

 ① MODULE
    pdmg-ui / pdmg-jwt / pdmg-fw / pdmg-service / pdmg-om
        │
        ▼
 ② BOUNDARY
    Build Module ≠ Runtime Process ≠ Spring Context
        │
        ▼
 ③ PACKAGE
    nhnis.mg.ui / nhnis.mg.jw.a / nhnis.fw / nhnis.mg.co.a
        │
        ▼
 ④ SERVICE ID
    mgcoa.... → Handler Registry
        │
        ▼
 ⑤ BUSINESS SOURCE
    Handler → Facade → Service → DAO → Mapper XML → SQL
        │
        ▼
 ⑥ FRAMEWORK SOURCE
    Filter → Context → MVC → TCF → Timeout → TX → Error
        │
        ▼
 ⑦ SECURITY
    Login / JWT / Refresh / SSO / JWKS / Principal
        │
        ▼
 ⑧ OPERATIONS
    GUID / MDC / ImageLog / Error / Runtime Trace
        │
        ▼
 ⑨ EVIDENCE
    Source / Config / Runtime
        │
        ▼
 ⑩ ALIGNMENT
    PDMG AS-IS ↔ NSIGHT TO-BE → CONFORM / DRIFT / GAP / ADR
```

---

# 2. Evidence Register

본 장은 다음 Source 분석 문서를 우선 근거로 사용한다.

```text
01장.PDMG_시스템_개요_ASCII_확장본.md
05장.네이밍_규칙_ASCII_확장본.md
06장.패키지와_프로젝트_구조_ASCII_확장본.md
09장.Filter와 Spring MVC.md
11장.ServiceContext와 GUID.md
14장.Handler와 Controller.md
24장.JWT 인증 전체 구조.md

NSIGHT_PDMG_아키텍처_정의서_III_PDMG_Module_Application_Architecture.md
NSIGHT_PDMG_아키텍처_정의서_IV_PDMG_Online_Runtime_TCF_Flow.md
NSIGHT_PDMG_아키텍처_정의서_VI_Standard_Message_Context_Error_Logging.md
NSIGHT_PDMG_아키텍처_정의서_X_Naming_ServiceId_Traceability_Closed_Loop.md
```

## Evidence 우선순위

```text
Source / Config / Runtime
        >
Current Source Analysis
        >
Official Architecture Baseline
        >
Detailed Design
        >
Past Draft
```

---

# 3. PDMG는 무엇인가

## FIG-PDMG-02. PDMG 한 문장 정의

```text
온라인 요청
    ↓
표준 진입
Header / GUID / JWT / Context
    ↓
거래 식별
ServiceId / TCF
    ↓
실행 제어
Timeout / Transaction
    ↓
업무 실행
Handler / Facade / Service
    ↓
데이터 접근
DAO / Mapper / DB
    ↓
표준 응답 / 운영 추적
Response / Error / ImageLog
```

> **PDMG는 온라인 요청을 표준 계약으로 받아 거래를 식별하고, 공통 실행제어 아래 업무를 수행한 후, 표준 응답과 Runtime Evidence를 남기는 Reference 구현이다.**

---

# 4. PDMG 5-Module Baseline

## FIG-PDMG-03. Five Module Map

```text
┌──────────────────────────── PDMG ─────────────────────────────┐
│                                                              │
│  pdmg-ui                                                     │
│  화면 / Browser 접점                                         │
│      │                                                       │
│      ▼                                                       │
│  pdmg-jwt                                                    │
│  인증 / Token / JWKS                                         │
│                                                              │
│  pdmg-service                                                │
│  ┌────────────────────────────────────────────────────────┐  │
│  │ pdmg-fw                                                │  │
│  │ Filter / Context / TCF / Timeout / Error              │  │
│  └──────────────────────┬─────────────────────────────────┘  │
│                         ▼                                    │
│  Handler / Facade / Service / DAO / Mapper                  │
│                                                              │
│  pdmg-om                                                     │
│  [CURRENT IMPLEMENTATION SCOPE UNKNOWN]                      │
│                                                              │
└──────────────────────────────────────────────────────────────┘
```

---

# 5. 현재 Source Evidence 수준

| Module | Current Source Evidence | 상태 |
|---|---|---|
| `pdmg-ui` | UI/Static/Relay/ServiceId 호출 구조 | `[AS-IS]` |
| `pdmg-jwt` | Login/Token/JWKS/Refresh/SSO Source | `[AS-IS]` |
| `pdmg-fw` | Filter/Context/TCF/Timeout/MVC/Error | `[AS-IS]` |
| `pdmg-service` | Handler/Facade/Service/DAO/Mapper | `[AS-IS]` |
| `pdmg-om` | 상세 Source/Package/Runtime Evidence 부족 | `[UNKNOWN]` |

---

# 6. 세 가지 경계를 절대 혼동하지 않는다

## FIG-PDMG-04. Boundary Separation

```text
[1] PROCESS BOUNDARY

Browser
  ├────HTTP────► pdmg-ui
  ├────HTTP────► pdmg-service
  └────HTTP────► pdmg-jwt


[2] MODULE / SPRING BOUNDARY

pdmg-service Runtime
    └─ pdmg-fw
       Bean / Filter / Interceptor / AOP


[3] DATA BOUNDARY

pdmg-service
    └── MyBatis / JDBC ──► RDW / DB
```

---

# 7. 핵심 Architecture Fact

```text
pdmg-fw
= 별도 Build Module

BUT

pdmg-fw
≠ 별도 Remote Business Server
```

현재 Online Runtime에서 Framework Bean은 `pdmg-service`와 같은 Spring ApplicationContext에서 동작할 수 있다.

---

# 8. Module Boundary ≠ Process Boundary

## FIG-PDMG-05. Boundary Equation

```text
Build Module
pdmg-fw
      │
      │ ≠
      ▼
Runtime Process
Tomcat / Spring Boot
      │
      │ ≠
      ▼
Logical Node
Marketing Service
```

---

# 9. Spring ApplicationContext

## FIG-PDMG-06. Shared Spring Context

```text
┌──────────────── Spring ApplicationContext ────────────────┐
│                                                          │
│ scanBasePackages = "nhnis"                               │
│                                                          │
│  nhnis.fw.*                                              │
│  ├─ Filter                                               │
│  ├─ Context                                              │
│  ├─ TCF                                                  │
│  └─ Common                                               │
│                                                          │
│  nhnis.mg.*                                              │
│  ├─ Business                                             │
│  ├─ JWT                                                  │
│  └─ Application Components                               │
│                                                          │
└──────────────────────────────────────────────────────────┘
```

### 핵심

`pdmg-service`와 `pdmg-fw`는 책임은 분리하지만 같은 ApplicationContext에서 협력할 수 있다.

---

# 10. pdmg-ui 책임

## FIG-PDMG-07. UI Role

```text
사용자
  │
  ▼
pdmg-ui
  │
  ├─ 화면
  ├─ 거래 선택
  ├─ ServiceId
  ├─ 요청 JSON
  └─ Authorization 전달
  │
  ▼
pdmg-service
```

---

# 11. pdmg-ui의 일반 직접호출

## FIG-PDMG-08. Direct Browser Path

```text
Browser
   │
   │ HTTP / JSON
   ▼
pdmg-service
```

이 경로에서는:

```text
CORS
Authorization Header
Service URL
JWT
```

가 실제 장애지점이 된다.

---

# 12. pdmg-ui Relay 호환 경로

## FIG-PDMG-09. Relay Path

```text
Browser
   ↓
pdmg-ui
   ↓
/api/relay/{serviceId}
   ↓
TransactionRelayService
   ↓
pdmg-service
```

### 중요

```text
Relay Path 존재
≠
Relay가 PDMG의 유일 호출경로
```

---

# 13. Direct vs Relay

| 항목 | Direct | Relay |
|---|---|---|
| Browser→Service | 직접 | UI Server 경유 |
| Network Hop | 적음 | 1개 증가 |
| CORS | 중요 | UI→Service Server-side 가능 |
| 장애지점 | Browser/Service | Browser/UI/Service |
| Business Runtime | 동일 | 동일 |

---

# 14. UI Static Program Structure

## FIG-PDMG-10. UI Program Folders

```text
resources/static
├─ _shared
├─ mgcoa5530
├─ mgcoa8888
├─ mgcoa9000
├─ mgcoa9001
├─ mgcoa9100
└─ mgcoa9999
```

### 주의

```text
static/mgcoa9000
≠
Java package nhnis.mg.co.a...
```

하지만 둘은 ServiceId를 통해 업무축으로 연결되어야 한다.

---

# 15. UI → ServiceId → Backend

## FIG-PDMG-11. UI Trace

```text
static/mgcoa9000
     │
     ▼
ServiceId
mgcoa9000S0
     │
     ▼
Backend Handler
mgcoa9000Handler
```

---

# 16. pdmg-service 책임

## FIG-PDMG-12. Business Module

```text
pdmg-service
│
├─ Entry Handler
├─ Business Controller
├─ Facade
├─ Service
├─ DTO
├─ DAO
├─ Mapper Resource
├─ BizPrePostAspect
└─ Business Configuration
```

---

# 17. pdmg-service 업무 Root

```text
nhnis.mg.co.a
```

현재 대표 구조:

```text
nhnis.mg.co.a
├─ entry
├─ application
├─ dto
└─ persistence
```

---

# 18. pdmg-service Package Map

## FIG-PDMG-13. Business Package

```text
nhnis.mg.co.a
│
├─ entry
│  └─ handler
│
├─ application
│  ├─ controller
│  ├─ facade
│  └─ service
│
├─ dto
│
└─ persistence
   └─ dao
```

---

# 19. 현재 일반 Rule Layer 판정

현재 대표 업무경로에서:

```text
application.rule
```

이 일반화된 AS-IS 계층이라고 확인되지 않는다.

따라서:

```text
Handler → Facade → Service → Rule → DAO
```

를 현재 전체 Source 경로로 단정하지 않는다.

### 표현

```text
[AS-IS]
Handler → Facade → Service → DAO

[TO-BE 후보]
Service → Rule → DAO
```

---

# 20. Program `mgcoa9000` Source Drill-down

## FIG-PDMG-14. Program Source Tree

```text
nhnis.mg.co.a
│
├─ entry/handler
│   └─ mgcoa9000Handler.java
│
├─ application/controller
│   └─ mgcoa9000Controller.java
│
├─ application/facade
│   └─ mgcoa9000Facade.java
│
├─ application/service
│   └─ mgcoa9000Service.java
│
├─ dto
│   ├─ mgcoa9000S0DTOin.java
│   ├─ mgcoa9000S0DTOout.java
│   ├─ mgcoa9000C0DTOin.java
│   ├─ mgcoa9000U0DTOin.java
│   └─ mgcoa9000D0DTOin.java
│
└─ persistence/dao
    └─ mgcoa9000DAO.java
```

---

# 21. Mapper Resource

## FIG-PDMG-15. Java ↔ Mapper

```text
Java
nhnis/mg/co/a/persistence/dao/
└─ mgcoa9000DAO.java
        │
        │ namespace
        ▼
Resource
rdw.mg.co.a/
└─ mgcoa9000-ORA.xml
```

---

# 22. 업무분류 축 일치

## FIG-PDMG-16. Naming Axis

```text
Business
MG / CO / A
   │
   ├────────► Java
   │          nhnis.mg.co.a
   │
   ├────────► Mapper
   │          rdw.mg.co.a
   │
   └────────► Service Prefix
              mgcoa
```

---

# 23. Program / ServiceId / Class Stem

## FIG-PDMG-17. Program Stem

```text
Program
mgcoa9001
    │
    ├─ Handler  mgcoa9001Handler
    ├─ Facade   mgcoa9001Facade
    ├─ Service  mgcoa9001Service
    ├─ DAO      mgcoa9001DAO
    └─ Mapper   mgcoa9001-ORA.xml
```

### 주의

모든 거래가 1:1 Class Stem을 완전히 지키는지는 기계적 Source Scan으로 검증한다.

---

# 24. ServiceId Anatomy

## FIG-PDMG-18. ServiceId

```text
mg | co | a | 9001 | S | 0
│    │    │    │      │   │
│    │    │    │      │   └─ Sequence
│    │    │    │      └──── Transaction Type
│    │    │    └─────────── Program ID
│    │    └──────────────── Function
│    └───────────────────── Business
└────────────────────────── Application Group
```

예:

```text
mgcoa9001S0
```

---

# 25. 현재 Handler Registry — 13 ServiceIds

## FIG-PDMG-19. Current Registry

```text
TransactionHandler Beans
│
├─ mgcoa5530Handler
│    └─ mgcoa5530S0
│
├─ mgcoa8888Handler
│    ├─ mgcoa8888S0
│    └─ mgcoa8888D0
│
├─ mgcoa9000Handler
│    ├─ mgcoa9000S0
│    ├─ mgcoa9000C0
│    ├─ mgcoa9000U0
│    └─ mgcoa9000D0
│
├─ mgcoa9001Handler
│    ├─ mgcoa9001S0
│    ├─ mgcoa9001C0
│    ├─ mgcoa9001U0
│    └─ mgcoa9001D0
│
├─ mgcoa9100Handler
│    └─ mgcoa9100S0
│
└─ mgcoa9999Handler
     └─ mgcoa9999S0

TOTAL = 13
```

---

# 26. 과거 8개 vs 현재 13개

```text
Past Document
8 ServiceIds
       │
       │ DRIFT
       ▼
Current Handler Source
13 ServiceIds
```

### 판정

```text
[DRIFT / SUPERSEDED CANDIDATE]
```

현재 Source 분석을 우선한다.

---

# 27. TransactionHandler Interface

## FIG-PDMG-20. Framework Contract

```text
pdmg-fw
TransactionHandler
│
├─ serviceId()
├─ serviceIds()
└─ handle(Object dtoBody, TransactionContext context)
```

Business Handler는 이 Interface를 구현한다.

---

# 28. Handler Registry Build

## FIG-PDMG-21. Registry Startup

```text
Spring Startup
   ↓
TransactionHandler Beans
   ↓
serviceIds()
   ↓
handlerMap
   │
   ├─ unique → register
   ├─ empty  → skip/warn
   └─ duplicate
          ↓
    IllegalStateException
          ↓
       Startup Fail
```

### 현재 판정

과거 문서에서 `[OPEN]`이었던 Duplicate 처리에 대해,
현재 `14장.Handler와 Controller` Source 분석에서는 **중복 ID를 기동 실패로 처리**하는 것으로 정리되어 있다.

---

# 29. Dispatcher

## FIG-PDMG-22. Service Routing

```text
serviceId
mgcoa9000C0
     │
     ▼
handlerMap[serviceId]
     │
     ├─ FOUND
     │    ↓
     │ mgcoa9000Handler
     │
     └─ NOT FOUND
          ↓
      ServiceHandlerNotFound
```

---

# 30. Handler 책임

```text
ServiceId 등록
ServiceId 분기
DTO 전달
Facade 호출
```

금지:

```text
DAO 직접 호출
Mapper 직접 호출
SQL 작성
TransactionManager 직접조작
Servlet Request 재파싱
```

---

# 31. Handler → Facade

## FIG-PDMG-23. Adapter to Use Case

```text
mgcoa9000S0
   ↓
mgcoa9000Handler
   ↓
mgcoa9000Facade.mgcoa9000S0(...)
```

---

# 32. 모든 Current Handler의 공통 특성

현재 분석된 Handler들은:

```text
대응 Business Facade 주입
```

을 사용하며:

```text
DAO
Mapper
Service 직접 의존
```

은 확인되지 않는다.

### 판정

```text
[AS-IS CONFORM]
Handler = Thin Inbound Adapter
```

---

# 33. Facade 책임

## FIG-PDMG-24. Facade

```text
Handler
  ↓
Facade
  │
  ├─ Use Case Boundary
  ├─ DTO 변환 / 조정
  ├─ 여러 Service 조정
  └─ Transaction Annotation Candidate
  ↓
Service
```

---

# 34. Facade ≠ 항상 최외곽 Transaction

```text
TCF ON + Timeout ON
Worker
  ↓
TransactionTemplate BEGIN
  ↓
Handler
  ↓
Facade @Transactional(REQUIRED)
```

따라서:

```text
Facade Annotation 위치
≠
Physical TX BEGIN 위치
```

---

# 35. Service 책임

## FIG-PDMG-25. Business Service

```text
Facade
  ↓
BizPrePostAspect.before
  ↓
Service
  │
  ├─ Business Procedure
  ├─ Business Decision
  └─ DAO Call
  ↓
BizPrePostAspect.afterReturning
```

---

# 36. BizPrePostAspect 위치

Current Pointcut 분석:

```text
nhnis.mg.co.a.application.service..*
```

### 핵심

`BizPrePostAspect`는 시스템 Servlet Filter가 아니라
**업무 Service 실행지점에 개입하는 Business-side Aspect**다.

---

# 37. DAO 책임

## FIG-PDMG-26. DAO / Mapper Interface

```text
Service
  ↓
DAO Interface
  ↓
MyBatis
  ↓
Mapper XML
  ↓
SQL
```

DAO와 Mapper XML을 동일 Java Class로 혼동하지 않는다.

---

# 38. Mapper Namespace 계약

## FIG-PDMG-27. Namespace Exact Match

```text
Java DAO FQCN
nhnis.mg.co.a.persistence.dao.mgcoa8888DAO
                    │
                    │ EXACT MATCH
                    ▼
Mapper XML
namespace =
nhnis.mg.co.a.persistence.dao.mgcoa8888DAO
```

---

# 39. Mapper Resource Pattern

Current `RdwDataSourceConfig` 분석:

```text
classpath*:rdw.*/*.xml
```

## FIG-PDMG-28. Resource Load

```text
classpath*
   ↓
rdw.*
   ├─ rdw.mg.co.a
   │    ├─ mgcoa5530-ORA.xml
   │    ├─ mgcoa8888-ORA.xml
   │    ├─ mgcoa9000-ORA.xml
   │    └─ ...
   └─ other rdw.*
```

---

# 40. Mapper 하위 폴더 주의

```text
rdw.mg.co.a/
├─ mgcoa9000-ORA.xml       O
└─ txparam/
   └─ mgcoa9000-ORA.xml    ? / 현재 Pattern 미포함 가능
```

### 핵심

Resource 구조 변경은 Mapper Load Pattern과 함께 바꿔야 한다.

---

# 41. MyBatis 3대 연결계약

## FIG-PDMG-29. MyBatis Contract

```text
1. DAO Scan
@MapperScan
       ↓
2. Mapper Resource Load
classpath*:rdw.*/*.xml
       ↓
3. Namespace / Statement ID
DAO FQCN / Method
       ↓
SQL
```

---

# 42. Current MapperScan

현재 주요 설정 분석:

```text
basePackages =
nhnis.mg.co.a.persistence.dao
```

---

# 43. SqlId Naming

대표 Program `mgcoa9000`:

```text
mgcoa9000S0_S0
mgcoa9000S0_COUNT
mgcoa9000C0_C0
mgcoa9000U0_U0
mgcoa9000D0_D0
..._exists
```

### 핵심

보조 Statement는 실제 Source에 `_exists` 등도 존재할 수 있으므로
`_S0/_C0/_U0/_D0`만 허용한다고 단정하지 않는다.

---

# 44. pdmg-fw 책임

## FIG-PDMG-30. Framework Scope

```text
pdmg-fw
│
├─ HTTP Entry Common
│   └─ DefaultFilter
│
├─ MVC Common
│   ├─ ServicePreventionInterceptor
│   ├─ Request Resolver
│   └─ Response Advice
│
├─ Context
│   └─ ServiceContext
│
├─ TCF
│   ├─ OnlineTransactionController
│   ├─ TcfFacade
│   ├─ TransactionDispatcher
│   └─ TransactionHandler Interface
│
├─ Execution Control
│   └─ OnlineTimeoutExecutor
│
├─ Error
└─ Logging / ImageLog Support
```

---

# 45. Framework가 알아야 하는 것 / 몰라야 하는 것

## FIG-PDMG-31. Framework Boundary

```text
pdmg-fw KNOWS
├─ ServiceId
├─ Request Context
├─ Timeout
├─ Handler Registry
├─ Error / Response
└─ Logging

pdmg-fw SHOULD NOT KNOW
├─ 고객별 업무 Rule
├─ 특정 마케팅 계산
├─ 특정 프로그램 SQL 조건
└─ 화면별 Business 판단
```

---

# 46. PDMG Online Runtime 전체

## FIG-PDMG-32. TCF ON + Timeout ON

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
  │
  ├─ Request Thread : Future.get(timeout)
  │
  └─ Worker Thread
       ↓
     TransactionTemplate
       ↓
     TransactionDispatcher
       ↓
     TransactionHandler
       ↓
     Business Facade
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
     Deadline Check
       ↓
     COMMIT / ROLLBACK
  ↓
Response / Exception
  ↓
ResponseBodyAdvice
  ↓
Interceptor.afterCompletion
  ↓
DefaultFilter.finally
  ↓
HTTP Response
```

---

# 47. Servlet / Security / MVC / TCF 경계

## FIG-PDMG-33. Runtime Boundaries

```text
Servlet
└─ DefaultFilter
      ↓
Security
└─ SecurityFilterChain
      ↓
Spring MVC
├─ DispatcherServlet
├─ Interceptor
└─ Controller
      ↓
TCF
├─ TcfFacade
├─ TimeoutExecutor
├─ Dispatcher
└─ Handler
      ↓
Business
└─ Facade / Service / DAO
```

---

# 48. DefaultFilter

## FIG-PDMG-34. Filter Responsibility

```text
HTTP Request
   ↓
DefaultFilter
   ├─ OPTIONS 제외
   ├─ Body Cache
   ├─ JWT 검사 [비-local JSON 경로]
   ├─ hdr_nhnis 파싱 / local 합성
   ├─ GUID
   ├─ ServiceContext
   └─ MDC
   ↓
filterChain.doFilter(...)
```

---

# 49. Filter와 MVC를 합치면 안 되는 이유

```text
Filter
= Controller를 아직 모르는 Servlet Boundary

MVC
= 특정 Handler / Method / Argument를 아는 Boundary
```

책임이 다르다.

---

# 50. CORS

일반 Local Reference:

```text
pdmg-ui      : 8090
pdmg-service : 8080
```

### 주의

```text
Local Port
≠ Production Port
```

CORS 장애는 Business Logic 장애가 아니다.

---

# 51. Filter Order Drift

과거 일부 문서:

```text
order = 1
```

Current 분석:

```text
Ordered.HIGHEST_PRECEDENCE + 20
```

### 판정

```text
[DRIFT]
```

Current Source를 우선한다.

---

# 52. SecurityFilterChain

## FIG-PDMG-35. Current Security Position

```text
DefaultFilter
   ↓
Spring SecurityFilterChain
   ↓
DispatcherServlet
```

### 핵심

Security Chain 존재:

```text
≠
업무 Authorization 완료
```

---

# 53. ServicePreventionInterceptor

## FIG-PDMG-36. System Pre/Post

```text
preHandle
├─ Header/sys_comm 보강
├─ GUID 보완
├─ ServiceId/IP/User 보완
├─ Request Log
└─ Pre ImageLog
    ↓
Business Runtime
    ↓
afterCompletion
├─ Normal Post ImageLog
└─ Exception ImageLog
```

---

# 54. Interceptor Scope

현재:

```text
/**
```

대상이며:

```text
/error
```

등 일부 제외가 있을 수 있다.

---

# 55. OnlineTransactionController

## FIG-PDMG-37. Common Entry Adapter

```text
HTTP Request
  ↓
OnlineTransactionController
  │
  ├─ ServiceId 결정
  ├─ request["dto"] 추출
  └─ TcfFacade.process()
```

### 중요한 사실

TCF로 넘기는 `dtoBody`는 전체 전문이 아니다.

```text
Header / GUID
→ ServiceContext / TransactionContext

Business DTO
→ dtoBody
```

---

# 56. Controller Mapping

현재 공통 Entry는 다음 형태를 지원하는 것으로 분석된다.

```text
POST /online
POST /{businessCode}/online
POST /{serviceId}
```

모두 공통 `handle()` 흐름으로 수렴한다.

---

# 57. ServiceId 결정 우선순위

Current 분석:

```text
1. ServiceContext.header.sys_comm.rms_svc_c
2. Request JSON hdr_nhnis.sys_comm.rms_svc_c
3. Path Variable
4. null
```

### 위험

Header / Path 불일치에 대한 강제 Reject 정책은 별도 확인이 필요하다.

---

# 58. TcfFacade

## FIG-PDMG-38. Facade of TCF

```text
OnlineTransactionController
       ↓
TcfFacade
       │
       ├─ TransactionContext.fromCurrent
       └─ OnlineTimeoutExecutor.execute
```

TcfFacade가 하지 않는 것:

```text
SQL 실행
Business Rule
ImageLog 조립
HTTP Envelope 조립
```

---

# 59. STF / ETF Actual vs Intended

## FIG-PDMG-39. Exists ≠ Executed

```text
STF / ETF Class / Bean
        │
        ▼
TcfFacade가 호출하는가?
        │
        ├─ YES → Runtime Step
        └─ NO  → Not AS-IS Runtime
```

현재 분석에서는:

```text
TcfFacade Current Call Path
→ STF / ETF 직접 호출 없음
```

### 판정

```text
[AS-IS]
STF/ETF를 실행 Sequence로 그리지 않는다.
```

---

# 60. OnlineTimeoutExecutor

## FIG-PDMG-40. Worker Executor

```text
Request Thread
    │
    ├─ capture Context / MDC
    ├─ submit Task
    └─ Future.get(5000ms)
          │
          ▼
    pdmg-online-N
          │
          ├─ install Context/MDC
          ├─ TransactionTemplate
          └─ clear
```

---

# 61. Current Timeout Snapshot

```text
[AS-IS SNAPSHOT]

milliseconds    = 5000
pool-size       = 20
queue-capacity  = 100
```

---

# 62. Overload

## FIG-PDMG-41. Queue Saturation

```text
20 Workers Busy
      +
100 Queue Full
      ↓
Task Reject
      ↓
OnlineOverloadException
      ↓
HTTP 503
```

---

# 63. Request vs Worker Thread

## FIG-PDMG-42. Two Lifecycles

```text
Request Thread
Filter / MVC / TcfFacade
       │
       │ submit
       ▼
Worker Thread
TX / Handler / Business / DB
```

### 핵심

```text
HTTP Request Lifecycle
≠
DB Transaction Lifecycle
```

---

# 64. Worker Context Propagation

## FIG-PDMG-43. Explicit Propagation

```text
Request Thread
ServiceContext + MDC
       │ capture
       ▼
Worker
       │ install
       ▼
Business
       │
       ▼
finally clear
```

자동 ThreadLocal 전파가 아니다.

---

# 65. Shared Mutable ServiceContext Risk

현재 Worker Context는 별도 Immutable Snapshot보다
같은 `ServiceContext` 참조를 공유하는 것으로 분석된다.

## FIG-PDMG-44. Mutable Reference

```text
Request Thread
      │
      └────── same ServiceContext object ──────┐
                                               ▼
                                         Worker Thread
```

### 위험

```text
Thread Race
Servlet Request/Response Reference
Lifecycle overlap
Mutable Header / responseBody
```

---

# 66. ServiceContext

## FIG-PDMG-45. Context Fields

```text
ServiceContext
│
├─ applicationName
├─ guid
├─ active profile
├─ requestHeaders
├─ httpServletRequest
├─ httpServletResponse
├─ header
├─ userContext
├─ requestBody
└─ responseBody
```

---

# 67. ServiceContext는 무엇이 아닌가

```text
DB Transaction            X
JDBC Connection           X
Business DTO Store        X
Authentication Policy     X
Automatic Thread Transfer X
```

---

# 68. ServiceContextHolder

```text
ThreadLocal<ServiceContext>
```

기본 API:

```text
setInstance
getInstance
removeInstance
```

### 핵심

Container가 자동으로 관리하는 Request Scope라고 오해하지 않는다.

---

# 69. Context Lifecycle

## FIG-PDMG-46. Request Context

```text
DefaultFilter
  ↓ create/set
ServiceContext
  ↓
Interceptor
  ↓
Controller / TCF
  ↓
Worker capture/install
  ↓
Business
  ↓
ResponseBodyAdvice
  ↓
afterCompletion
  ↓
Filter finally
  ↓ remove
```

---

# 70. userContext Risk

Current:

```text
Map<String,Object>
```

장점:

```text
확장성
```

위험:

```text
문자열 Key Drift
Type Drift
Owner 불명확
Hidden Business Input
```

TO-BE 후보:

```text
Immutable AuthenticatedUserContext
```

---

# 71. TransactionContext

## FIG-PDMG-47. TransactionContext

```text
TransactionContext
│
├─ serviceId
├─ ServiceContext reference
└─ startedAtNanos
```

### 혼동 금지

```text
TransactionContext
≠
DB Transaction
```

---

# 72. Worker Transaction

## FIG-PDMG-48. TransactionTemplate

```text
Worker
  ↓
TransactionTemplate BEGIN
  ↓
Dispatcher
  ↓
Handler
  ↓
Facade REQUIRED
  ↓
Service
  ↓
DAO / SQL
  ↓
Deadline Check
  ↓
COMMIT / ROLLBACK
```

---

# 73. Facade REQUIRED

```text
Outer TransactionTemplate
   ↓
Facade @Transactional(REQUIRED)
   ↓
JOIN EXISTING TX
```

---

# 74. readOnly GAP

Facade Query에:

```text
readOnly=true
```

가 선언되어도 Outer `TransactionTemplate`이 먼저 열리면
실제 Read-only 속성 적용 여부를 검증해야 한다.

```text
[GAP]
```

---

# 75. Timeout 504의 의미

## FIG-PDMG-49. Timeout Semantics

```text
Future.get(5000ms)
       ↓
Timeout
       ↓
cancel(true)
       ↓
HTTP 504
```

하지만:

```text
HTTP 504
≠ Worker ended
≠ JDBC Statement canceled
≠ DB rollback completed
```

---

# 76. Deadline Guard

## FIG-PDMG-50. Late Commit Prevention

```text
HTTP Timeout
   ↓
Worker continues
   ↓
SQL returns
   ↓
Deadline exceeded?
   ├─ YES → rollback
   └─ NO  → commit
```

---

# 77. TCF OFF

## FIG-PDMG-51. OFF Path

```text
HTTP
 ↓
Filter / Interceptor
 ↓
Business Controller
 ↓
Service / Facade
 ↓
DAO
```

---

# 78. OFF는 Framework 전체 OFF가 아니다

```text
TCF OFF
=
Common TCF Controller / Dispatcher / Handler Registry OFF
```

별도 설정에 따라 다음은 남을 수 있다.

```text
Filter
Interceptor
Response Advice
DataSource
TransactionManager
```

---

# 79. Current OFF Controller Drift

현재 분석:

```text
mgcoa5530 → Service
mgcoa8888 → Service
mgcoa9000 → Service
mgcoa9001 → Service
mgcoa9999 → Service
mgcoa9100 → Facade
```

### 문제

```text
ON
Handler → Facade → Service

OFF
Controller → Service
```

Facade Use Case Boundary가 일관되지 않다.

---

# 80. TCF ON/OFF TO-BE Candidate

## FIG-PDMG-52. Same Business Core

```text
TCF ON
Handler ───────┐
               │
               ▼
             Facade
               ↓
             Service
               ↓
              DAO
               ▲
               │
TCF OFF        │
Controller ────┘
```

Entry Adapter만 다르게 하고 Business Core는 통합하는 방향이다.

---

# 81. Standard Message

## FIG-PDMG-53. Request

```text
{
  "hdr_nhnis": {
    "sys_comm": {
      "rms_svc_c": "mgcoa8888S0",
      "std_gbl_id": "...",
      "tr_sysid": "PDMG",
      "tr_trm_ipadr": "127.0.0.1",
      "tr_brc": "10001",
      "scid": "mgcoa8888",
      "optr_eno": "E0000001"
    }
  },
  "dto": {
    "...": "..."
  }
}
```

---

# 82. Local vs Non-local

```text
Local
dto-only 요청 일부 허용/합성 가능

Non-local
표준 hdr_nhnis 요구
```

### 주의

Local 편의기능을 Production Contract로 승격하지 않는다.

---

# 83. Header Lifecycle

## FIG-PDMG-54. Header Evolution

```text
Client JSON
   ↓
DefaultFilter
   ↓
ServiceContext.header
   ↓
Interceptor enrichment
   ↓
Controller ServiceId/IP enrichment
   ↓
ResponseBodyAdvice
   ↓
Response Header
```

### 핵심

응답 Header는 단순 Raw Echo가 아니다.

---

# 84. Success Envelope

```text
{
  "hdr_nhnis": {...},
  "dto": {...}
}
```

---

# 85. Known Error Envelope

```text
{
  "hdr_nhnis": {...},
  "result": {...}
}
```

---

# 86. GUID

Current:

```text
std_gbl_id
```

를 GUID/Correlation Key로 사용한다.

## FIG-PDMG-55. GUID

```text
Header.std_gbl_id
     ↓
ServiceContext.guid
     ↓
MDC["guid"]
     ↓
Worker MDC
     ↓
Application Log
     ↓
ImageLog.GUID
```

---

# 87. GUID는 무엇이 아닌가

```text
Authentication Key
Business Primary Key
Idempotency Key [자동]
```

---

# 88. ResponseBodyArgumentResolver

이름은 Resolver지만 실제 역할은:

```text
ResponseBodyAdvice
```

성격으로 분석된다.

## FIG-PDMG-56. Response

```text
Controller / Handler Result
      ↓
ResponseBodyAdvice
      ↓
Success DTO or Error Result
      ↓
Standard Envelope
```

---

# 89. RequestBodyArgumentResolver GAP

Typed DTO Binding 의도와
TCF Common Controller의 전체 Map/Node 수신 관계에서
Custom Resolver 적용범위가 애매할 수 있다.

```text
[GAP]
Resolver Scope / Ordering
```

---

# 90. Error Handling

## FIG-PDMG-57. Current Known Mapping

```text
ServiceHandlerNotFound
→ E9999 / SERVICE / HTTP 500

BizException
→ Business Code / BIZ / HTTP 500

OnlineTimeoutException
→ FW_TIMEOUT / COMMON / HTTP 504

OnlineOverloadException
→ FW_OVERLOADED / COMMON / HTTP 503
```

---

# 91. Early Filter Error

```text
DefaultFilter
  ↓
sendError 400 / 401
  ↓
MVC Advice 우회 가능
```

### GAP

```text
Standard {hdr_nhnis,result} 보장 불가
```

---

# 92. Generic Exception GAP

명시적 Catch되지 않은:

```text
RuntimeException
SQL Exception
DataSource Error
```

등이 Spring Default `/error`로 갈 가능성을 검증해야 한다.

---

# 93. Legacy vs TCF Error Path

PDMG에는:

```text
TCF GlobalExceptionHandler
Legacy NhBaseException Handler
ResponseBodyAdvice
```

가 공존한다.

### 위험

```text
Handler precedence
Error format drift
Stack information exposure
```

---

# 94. ImageLog

## FIG-PDMG-58. ImageLog Lifecycle

```text
preHandle
  ↓
PRE INSERT
  ↓
Business Runtime
  ↓
ResponseBodyAdvice
  ↓
afterCompletion
  │
  ├─ Normal POST UPDATE
  └─ Exception UPDATE/INSERT
```

---

# 95. ImageLog Table 역할

대표:

```text
TB_FW_IMAGE_LOG
```

저장 후보:

```text
GUID
ServiceId
Screen
Operator
Client IP
Request Time
Response Time
Error
Request/Response Message
```

---

# 96. ImageLog Fail-open

Current 분석:

```text
ImageLog failure
→ catch/log
→ Business continue
```

### 장점

Availability 보호

### 위험

Audit Gap

---

# 97. Runtime DDL Risk

ImageLog에서 누락 Column을 Runtime에 추가하려는 로직이 있다면:

```text
Application Runtime
   ↓
DDL
```

은 운영 Schema Governance 측면의 위험이다.

TO-BE:

```text
Migration / DBA controlled schema change
```

---

# 98. pdmg-jwt Package

## FIG-PDMG-59. JWT Source Tree

```text
nhnis.mg.jw.a
│
├─ entry
│  ├─ handler
│  └─ web
│
├─ application
│  ├─ facade
│  └─ service
│
├─ dto
├─ persistence
│  └─ dao
├─ config
└─ support
```

JWT는 `MG/JW/A`라는 별도 업무축이다.

---

# 99. JWT Normal Login

## FIG-PDMG-60. mgjwa1000C0

```text
Browser
  ↓
pdmg-jwt /online
  ↓
ServiceId mgjwa1000C0
  ↓
mgjwa1000Handler
  ↓
mgjwa1000Facade
  ↓
mgjwa1000Service.mgjwa1000C0()
  ↓
User Lookup
  ↓
PasswordEncoder.matches()
  ↓
JwtTokenIssuer
  ↓
Access + Refresh
```

---

# 100. Current Token Lifetimes

Current JWT source analysis indicates defaults such as:

```text
Access Token  = 15 minutes
Refresh Token = 8 hours
```

### 태그

```text
[AS-IS SNAPSHOT]
```

운영 Target Security Baseline로 자동 승격하지 않는다.

---

# 101. Access Token

## FIG-PDMG-61. RS256 Issue

```text
Claims
  ↓
JwtTokenIssuer
  ↓
RSA Private Key
  ↓
RS256
  ↓
Access Token
```

---

# 102. Current JWT Claims

대표:

```text
Header
alg = RS256
typ = JWT
kid = nsight-jwt-rs256

Claims
iss
aud
sub
jti
iat
exp
type
userId
userName
branchId
channelId
authGroupId
```

### 주의

JWT Payload는 암호화가 아니라 Base64URL Encoding이다.

---

# 103. Refresh Token

## FIG-PDMG-62. Refresh

```text
Random Plain Refresh Token
       │
       ├────► Client
       │
       └────► SHA-256
                 ↓
              DB Hash
```

DB에는 원문이 아니라 Hash를 저장하는 구조다.

---

# 104. Refresh Validation

```text
Refresh Plain
  ↓
Hash
  ↓
DB Lookup
  ↓
Revoked?
Rotated?
Expired?
User Active?
  ↓
New Token Pair
```

---

# 105. JWT UI Storage

Current Admin UI 분석:

```text
sessionStorage
key = pdmg.jwt.session
```

Access/Refresh pair를 JavaScript가 읽을 수 있는 형태로 저장한다.

### Risk

```text
XSS
→ Access + Refresh 동시노출 가능
```

---

# 106. JWT SSO Flow

## FIG-PDMG-63. mgjwa1000C1

```text
Trusted Internal Caller
   ↓
mgjwa1000C1
   │
   ├─ allowed service
   ├─ timestamp
   ├─ HMAC
   └─ caller IP
   ↓
Trusted User Info
   ↓
PDMG Token Pair
```

### 핵심

```text
mgjwa1000C1
≠
일반 OIDC Callback로 자동해석
```

외부 IdP 검증은 `[OPEN UPSTREAM]`.

---

# 107. HMAC Secret vs RSA Key

## FIG-PDMG-64. Credential Separation

```text
RS256 Private Key
= Access Token Signing

RS256 Public Key / JWKS
= Verification

Internal HMAC Secret
= SSO Internal Caller Validation

pdmg-fw jwt.secret
= Legacy HMAC Validator Secret
```

서로 같은 Secret으로 취급하지 않는다.

---

# 108. Critical JWT AS-IS Gap

Current JWT analysis에서는:

```text
pdmg-jwt
→ RS256 issue

pdmg-fw JwtProvider
→ jwt.secret HMAC verify
```

구조가 동시에 분석된다.

## FIG-PDMG-65. Issuer / Verifier Incompatibility

```text
pdmg-jwt
RS256
  │ Access Token
  ▼
pdmg-service / pdmg-fw
JwtProvider
HMAC secret
```

### 판정

```text
[CRITICAL GAP]
발급 Algorithm과 검증 Algorithm 경로 정합성 확보 필요
```

---

# 109. JWT Key Generation Risk

Current `JwtKeyConfiguration` 분석:

```text
Application startup
   ↓
Generate RSA 2048 key in memory
   ↓
kid = nsight-jwt-rs256
```

### 위험

```text
Restart
→ same kid / different key

Multi-instance
→ same kid / different key
```

---

# 110. JWKS

## FIG-PDMG-66. Current JWKS

```text
Jwt Signing Key
   ↓
toPublicJWK()
   ↓
GET /.well-known/jwks.json
   ↓
Public Key + kid
```

Private Key는 노출하지 않는다.

---

# 111. JWKS Integration GAP

Current business-side verifier에:

```text
JWKS-based RS256 Decoder
```

가 실제 연결되어 있는지 별도 확인이 필요하며,
기존 분석에서는 `pdmg-fw JwtProvider`가 HMAC 경로로 파악된다.

```text
[GAP]
```

---

# 112. JWT Private Key TO-BE

## FIG-PDMG-67. Key Management

```text
Approved Key Store / KMS / HSM
        │
        ├─ JWT #1
        └─ JWT #2
        │
        ▼
Same Active Key / kid
```

제품은 `[TBD]`.

---

# 113. Key Rotation

```text
Old kid K1
      │ keep verify
      ▼
New kid K2
      │ new issue
      ▼
Overlap Period
      ↓
K1 retirement after token expiry
```

---

# 114. JWT Authorization GAP

JWT Signature 유효:

```text
=
인증 성공
```

일 수 있지만:

```text
=
해당 업무권한 허용
```

은 아니다.

업무 인가 정책은 별도 검증해야 한다.

---

# 115. JWT Subject vs Header User

## FIG-PDMG-68. Identity Binding

```text
JWT Validated Subject
ssoId
   │
   │ ?
   ▼
hdr_nhnis.sys_comm.optr_eno
   │
   │ ?
   ▼
ServiceContext.userContext
```

### 판정

```text
[SECURITY GAP]
```

---

# 116. pdmg-om

## FIG-PDMG-69. OM Unknown Boundary

```text
pdmg-om
│
├─ Package ?
├─ Process ?
├─ Dashboard ?
├─ Metric ?
├─ Control API ?
├─ DB ?
└─ Deployment ?
```

### 현재 판정

```text
[UNKNOWN]
```

Source Evidence 없이 일반 운영관리 구조로 채우지 않는다.

---

# 117. OM은 무엇을 확인해야 하는가

```text
Thread
JVM
Hikari
Slow Service
Timeout
Error
WAR
Deployment
Runtime Config
Alert
```

그러나 실제 `pdmg-om` 구현 여부는 별도 Source Scan 대상이다.

---

# 118. Source-to-Runtime Trace

## FIG-PDMG-70. Full Trace

```text
UI Folder
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
Mapper XML
   ↓
SqlId
   ↓
Table / View
   ↓
WAR
   ↓
JVM
   ↓
GUID
   ↓
Runtime Evidence
```

---

# 119. Source-to-Runtime 현재 강한 구간

```text
ServiceId
→ Handler
→ Facade
→ Service
→ DAO
→ Mapper
```

는 대표 Source에서 비교적 잘 연결된다.

---

# 120. Source-to-Runtime 현재 약한 구간

```text
Table
→ Artifact
→ JVM
→ Host
→ Runtime Evidence
```

전수 자동 Mapping은 아직 GAP이다.

---

# 121. ServiceId → Handler Coverage

현재 Source 분석:

```text
13 ServiceIds
→ 6 Handlers
```

Registry 기준 대표 Coverage는 확보된다.

---

# 122. Handler → Facade

Current Handlers는 대응 Facade 하나를 주입하는 구조로 분석된다.

```text
[AS-IS CONFORM]
```

---

# 123. Facade → Service

대표 Program Stem 기반 연결은 존재하지만
전수 Call Graph 자동화는 필요하다.

```text
[GAP]
```

---

# 124. DAO → Mapper

Java DAO FQCN과 Mapper Namespace가 계약이다.

```text
[AS-IS CONTRACT]
```

---

# 125. Mapper → Table

SQL Parser 없이:

```text
100% Trace
```

라고 쓰지 않는다.

```text
[GAP]
```

---

# 126. UI Catalog → Handler Registry

## FIG-PDMG-71. UI / Backend Drift

```text
UI Transaction Catalog
        │
        │ compare
        ▼
Handler Registry
```

두 Catalog를 SSOT로 통합/검증할 필요가 있다.

---

# 127. Source Conformance Rules

```text
R-PDMG-MODULE
R-PACKAGE-AXIS
R-SERVICEID-FORMAT
R-SERVICEID-UNIQUE
R-HANDLER-BRANCH
R-HANDLER-NO-DAO
R-DAO-MAPPER
R-MAPPER-RESOURCE
R-TX-OWNER
R-TIMEOUT
R-CONTEXT-CLEAR
R-JWT-ALGORITHM
R-JWT-KEY
R-SENSITIVE-LOG
```

---

# 128. R-PDMG-MODULE

```text
Required Reference Modules
pdmg-ui
pdmg-jwt
pdmg-fw
pdmg-service
pdmg-om
```

실제 Repository 존재 여부를 기계 스캔한다.

---

# 129. R-PACKAGE-AXIS

```text
ServiceId mgcoa...
   ↓
Java nhnis.mg.co.a
   ↓
Mapper rdw.mg.co.a
```

업무축이 다르면 FAIL 후보.

---

# 130. R-SERVICEID-UNIQUE

```text
All serviceIds()
   ↓
Duplicate?
  ├─ NO → PASS
  └─ YES → Startup FAIL / Rule FAIL
```

---

# 131. R-HANDLER-BRANCH

```text
serviceIds()
  │
  │ compare
  ▼
handle() branch
```

등록과 실행 Branch가 일치해야 한다.

---

# 132. R-HANDLER-NO-DAO

```text
Handler Dependency
  ↓
DAO / Mapper?
  ├─ NO → PASS
  └─ YES → FAIL
```

---

# 133. R-DAO-MAPPER

```text
DAO FQCN
  ↓ exact
Mapper namespace
```

---

# 134. R-MAPPER-RESOURCE

```text
Mapper XML
  ↓
classpath*:rdw.*/*.xml
  ↓
Loaded?
```

---

# 135. R-TX-OWNER

```text
ServiceId
  ↓
Runtime Mode
  ↓
Actual TX Owner
```

TCF ON/OFF 별도로 검증한다.

---

# 136. R-TIMEOUT

```text
Worker Deadline
Query Timeout
TX Timeout
External Timeout
Client Timeout
```

전수 Matrix 필요.

---

# 137. R-CONTEXT-CLEAR

```text
Worker
finally
  ↓
ServiceContextHolder.remove
MDC.clear
```

모든 Exit Path에서 검증한다.

---

# 138. R-JWT-ALGORITHM

```text
Issuer Algorithm
      │
      │ compare
      ▼
Verifier Algorithm
```

현재 RS256 vs HMAC 분석은 Critical Conformance 대상이다.

---

# 139. R-JWT-KEY

```text
Multi-instance
   ↓
Same kid?
Same public key?
Same active signing key?
```

---

# 140. R-SENSITIVE-LOG

다음 원문 로그 금지:

```text
Access Token
Refresh Token
Password
Private Key
HMAC Secret
DB Password
```

---

# 141. Source Test Matrix

| 영역 | Test |
|---|---|
| Module | Repository/Gradle scan |
| Package | Naming scan |
| ServiceId | format/unique/branch |
| Dependency | Handler/Controller no DAO |
| Mapper | namespace/resource/sql |
| TX | commit/rollback/owner |
| Timeout | slow SQL / queue full |
| Context | thread propagation / clear |
| JWT | issue/verify/restart/multi-instance |
| Error | filter/TCF/generic |
| Logging | sensitive/masking/GUID |

---

# 142. Runtime Test — Normal

```text
mgcoa9000S0
  ↓
Handler
  ↓
Facade
  ↓
Service
  ↓
DAO
  ↓
SQL
  ↓
COMMIT/Result
```

검증:

```text
GUID
ServiceId
TX
Response
ImageLog
```

---

# 143. Runtime Test — Handler Not Found

```text
Unknown ServiceId
  ↓
Dispatcher
  ↓
ServiceHandlerNotFound
  ↓
Error Envelope
```

---

# 144. Runtime Test — Duplicate Handler

```text
Two Handlers
same ServiceId
  ↓
Spring Startup
  ↓
IllegalStateException
  ↓
Startup Fail
```

---

# 145. Runtime Test — Timeout

```text
Slow SQL
  ↓
Worker > 5000ms
  ↓
HTTP 504
  ↓
Worker status
  ↓
Deadline rollback
```

---

# 146. Runtime Test — Queue Full

```text
20 Workers busy
+
100 Queue
  ↓
Next request
  ↓
503 Overload
```

---

# 147. Runtime Test — Context Leak

```text
Request A
  ↓
Worker
  ↓
Failure
  ↓
finally clear?
  ↓
Request B
  ↓
A Context visible?
```

반드시 `NO`여야 한다.

---

# 148. Runtime Test — JWT Restart

```text
Issue Access Token
  ↓
Restart pdmg-jwt
  ↓
JWKS changes?
  ↓
Old token verifies?
```

Current ephemeral key 구조에서는 중요 위험 시나리오다.

---

# 149. Runtime Test — JWT Multi-instance

```text
JWT #1 issue
   ↓
JWT #2 JWKS
   ↓
Verify?
```

same `kid` different key 문제를 검증한다.

---

# 150. Runtime Test — Identity Mismatch

```text
JWT sub = USER-A
Header optr_eno = USER-B
       ↓
Request
       ↓
Expected?
Reject / Bind / Audit
```

현재 정책 확정 필요.

---

# 151. Runtime Test — Early Filter Error

```text
Missing/Invalid Token
  ↓
DefaultFilter
  ↓
401
  ↓
CORS?
Standard Envelope?
GUID?
Audit?
```

---

# 152. Runtime Test — Generic Exception

```text
Unexpected RuntimeException
  ↓
Which Handler?
  ↓
HTTP?
Envelope?
Trace?
Sensitive Detail?
```

---

# 153. AS-IS / TO-BE Alignment Map

## FIG-PDMG-72. Alignment

```text
NSIGHT TARGET
       │
       │ compare
       ▼
PDMG AS-IS
       │
       ├─ CONFORM
       ├─ PARTIAL
       ├─ DRIFT
       └─ GAP
```

---

# 154. 현재 CONFORM 후보

```text
ServiceId 기반 Handler 확장
Thin Handler
Facade/Service/DAO 책임 분리
GUID Correlation
Standard Message Envelope
Worker Timeout Boundary
Mapper Namespace Contract
```

---

# 155. 현재 PARTIAL 후보

```text
Error Standardization
Security Authorization
Runtime Evidence Automation
TCF ON/OFF Core Reuse
Context Typed Model
```

---

# 156. 현재 CRITICAL GAP 후보

```text
RS256 Issuer vs HMAC Verifier
JWT ephemeral key / multi-instance
JWT Principal ↔ Header Identity
Query/TX Timeout
TCF OFF Business Boundary Drift
Generic/Early Error Contract
Deployment Mapping
Runtime Evidence Automation
```

---

# 157. pdmg-om GAP

```text
Module Baseline에는 존재
        │
        ▼
Actual Source / Runtime?
        │
        ▼
UNKNOWN
```

Source 확보 전 TO-BE 운영기능을 AS-IS로 채우지 않는다.

---

# 158. Source Evidence Coverage

## FIG-PDMG-73. Coverage Pyramid

```text
Strong
ServiceId → Handler
Handler → Facade
DAO → Mapper Namespace

Medium
Facade → Service
Service → DAO
Message / Context
Runtime Sequence

Weak / GAP
SQL → Table 전수
Artifact → Host/JVM
Runtime Evidence
pdmg-om
```

---

# 159. PDMG Reference를 NSIGHT에 사용하는 방법

## FIG-PDMG-74. Reference Promotion

```text
PDMG AS-IS Pattern
      │
      ▼
Good Practice?
      │
      ▼
Scope Applicable?
      │
      ▼
NFR Satisfied?
      │
      ▼
Security / Ops Validated?
      │
      ▼
ADR Approval
      │
      ▼
NSIGHT TO-BE Standard
```

---

# 160. 자동 승격 금지

```text
PDMG에 있음
     ↓
NSIGHT 표준

X
```

반드시:

```text
Evidence
Scope
Gap
ADR
Approval
```

이 필요하다.

---

# 161. Module Inventory Template

```yaml
module:
  name:
  buildType:
  packageRoots:
  runtimeProcess:
  springContext:
  artifact:
  dependencies:
  responsibility:
  evidence:
  status:
```

---

# 162. Source Component Inventory

```yaml
component:
  module:
  package:
  class:
  role:
  serviceIds:
  dependencies:
  annotations:
  runtimePath:
  evidence:
```

---

# 163. Service Trace Inventory

```yaml
serviceTrace:
  serviceId:
  ui:
  handler:
  facade:
  service:
  dao:
  mapper:
  sqlIds:
  tables:
  txPolicy:
  timeoutPolicy:
  securityPolicy:
  runtimeEvidence:
```

---

# 164. Security Inventory

```yaml
security:
  module:
  loginServiceId:
  tokenAlgorithm:
  kid:
  keySource:
  jwks:
  verifier:
  refreshStore:
  denylist:
  identityBinding:
  authorization:
  evidence:
```

---

# 165. Runtime Config Inventory

```yaml
runtime:
  tcfEnabled:
  timeoutEnabled:
  timeoutMs:
  workerPool:
  queueCapacity:
  datasource:
  transactionManager:
  mapperPattern:
  filterOrder:
  securityChain:
  evidence:
```

---

# 166. GAP Register

| ID | GAP | 영향 |
|---|---|---|
| GAP-PDMG-01 | `pdmg-om` Source/Runtime 미확인 | Operations |
| GAP-PDMG-02 | Module→Artifact→Host/JVM 전수 Mapping 미완료 | Deployment |
| GAP-PDMG-03 | UI Catalog↔Handler Registry SSOT 미통합 | Trace |
| GAP-PDMG-04 | Handler→Facade→Service→DAO 전수 Call Graph 미완료 | Source Trace |
| GAP-PDMG-05 | SqlId→Table/View 전수 Trace 미완료 | Data |
| GAP-PDMG-06 | Query Timeout 실제값 미확정 | Timeout |
| GAP-PDMG-07 | Spring TX Timeout 실제값 미확정 | TX |
| GAP-PDMG-08 | Outer TX readOnly 적용 검증 미완료 | TX |
| GAP-PDMG-09 | JDBC Interrupt/Cancel 검증 미완료 | Timeout |
| GAP-PDMG-10 | Mutable ServiceContext Snapshot 개선 미결정 | Thread |
| GAP-PDMG-11 | TCF OFF Facade Boundary 불일치 | Application |
| GAP-PDMG-12 | Early Filter Error Standard 미완료 | Error |
| GAP-PDMG-13 | Generic Exception Standard 미완료 | Error |
| GAP-PDMG-14 | RS256 Issuer ↔ HMAC Verifier 불일치 | Security |
| GAP-PDMG-15 | Ephemeral RSA Key | Security |
| GAP-PDMG-16 | JWT Principal↔Header Identity Binding 미완료 | Security |
| GAP-PDMG-17 | JWKS business verifier 연결 미완료 | Security |
| GAP-PDMG-18 | Denylist Enforcement 전수 검증 필요 | Security |
| GAP-PDMG-19 | Sensitive Log/Masking 전사표준 미완료 | Security |
| GAP-PDMG-20 | Runtime Evidence Manifest 자동화 미완료 | Governance |

---

# 167. RISK Register

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-PDMG-01 | `pdmg-fw`를 Remote Server로 오해 | High |
| RISK-PDMG-02 | Module/Process/Context 혼동 | High |
| RISK-PDMG-03 | ServiceId Registry/UI Catalog Drift | High |
| RISK-PDMG-04 | Handler에 Business/DAO 침투 | High |
| RISK-PDMG-05 | Mapper Resource 경로 변경 후 Load 실패 | High |
| RISK-PDMG-06 | Timeout 후 Late Commit | Critical |
| RISK-PDMG-07 | Context Thread Leak | Critical |
| RISK-PDMG-08 | TCF OFF 결과 Drift | High |
| RISK-PDMG-09 | RS256/HMAC 검증 불일치 | Critical |
| RISK-PDMG-10 | JWT Restart로 기존 Token 검증 실패 | Critical |
| RISK-PDMG-11 | Multi-instance same kid/different key | Critical |
| RISK-PDMG-12 | JWT/Header 사용자 불일치 | Critical |
| RISK-PDMG-13 | UI sessionStorage Refresh 노출 | High |
| RISK-PDMG-14 | Early Error 비표준화 | High |
| RISK-PDMG-15 | ImageLog Fail-open 감사공백 | High |

---

# 168. ADR 후보

```text
ADR-PDMG-01 PDMG Module Baseline
ADR-PDMG-02 TCF ON/OFF Target
ADR-PDMG-03 Business Facade Boundary
ADR-PDMG-04 ServiceId SSOT
ADR-PDMG-05 Mapper Resource Standard
ADR-PDMG-06 Transaction Ownership
ADR-PDMG-07 Timeout Budget
ADR-PDMG-08 Context Snapshot
ADR-PDMG-09 Error Standard
ADR-PDMG-10 JWT Algorithm Alignment
ADR-PDMG-11 JWT Key Store
ADR-PDMG-12 JWKS Verification
ADR-PDMG-13 Identity Binding
ADR-PDMG-14 Revocation
ADR-PDMG-15 UI Token Storage
ADR-PDMG-16 pdmg-om Scope
ADR-PDMG-17 Runtime Evidence
```

---

# 169. CONFIRMED / AS-IS

```text
[CONFIRMED / AS-IS]

- pdmg-ui / pdmg-jwt / pdmg-fw / pdmg-service Source Evidence 존재
- pdmg-service 업무 Root = nhnis.mg.co.a
- pdmg-jwt Root = nhnis.mg.jw.a
- pdmg-fw Root = nhnis.fw.*
- pdmg-service + pdmg-fw same Spring Context 가능
- Direct Browser→Service와 Relay 경로 공존
- Current Handler 6개 / ServiceId 13개
- Handler duplicate ID는 startup failure 분석
- Handler는 대응 Facade를 주입
- DAO ↔ Mapper Namespace exact contract
- Mapper resource = classpath*:rdw.*/*.xml
- TCF ON Current Runtime
- Timeout 5000 / Worker20 / Queue100 snapshot
- ServiceContext ThreadLocal
- Worker 명시적 Context/MDC 전파
- Current success/error envelope
- pdmg-jwt RS256 issue
- Refresh token hash DB 저장
- SSO mgjwa1000C1 내부 HMAC validation
```

---

# 170. CONFLICT / DRIFT / UNKNOWN

```text
[DRIFT]
Filter Order 과거 1 vs Current HIGHEST_PRECEDENCE+20

[DRIFT]
Past Handler Service Count 8 vs Current Source 13

[CRITICAL GAP / CONFLICT]
pdmg-jwt RS256 issuer vs pdmg-fw HMAC JwtProvider

[UNKNOWN]
pdmg-om Source / Package / Runtime / Deployment

[OPEN]
PDMG Production Host/JVM/WAR/Port Mapping
```

---

# 171. Source Verification Checklist

```text
[ ] 5 Module Repository 존재?
[ ] Gradle Dependency?
[ ] Spring scanBasePackages?
[ ] UI Static Folder?
[ ] UI Transaction Catalog?
[ ] ServiceId 13개?
[ ] Handler Duplicate Startup Fail?
[ ] Handler→Facade?
[ ] Controller→Facade/Service?
[ ] DAO MapperScan?
[ ] Mapper Pattern?
[ ] Namespace Match?
[ ] SQL/Table Trace?
[ ] TCF ON/OFF?
[ ] Timeout 5000/20/100?
[ ] Context clear?
[ ] JWT RS256?
[ ] HMAC Verifier?
[ ] Key generation?
[ ] JWKS?
[ ] Refresh Hash?
[ ] Denylist?
[ ] ImageLog?
[ ] Deployment Mapping?
```

---

# 172. PDMG Reference Completion Gate

## FIG-PDMG-75. Evidence Gate

```text
Module Source?
  ↓ YES
Package / Class?
  ↓ YES
ServiceId Registry?
  ↓ YES
Business Call Path?
  ↓ YES
Mapper / SQL?
  ↓ YES
Runtime Path?
  ↓ YES
Config?
  ↓ YES
Security?
  ↓ YES
Deployment?
  ↓ YES
Runtime Evidence?
  ↓ YES
PDMG REFERENCE VERIFIED
```

현재는:

```text
Source / Runtime 강함
Deployment / OM / 일부 Security 정합 약함
```

이므로 **CONDITIONAL PASS**다.

---

# 173. NSIGHT Target과 PDMG Reference의 만나는 지점

## FIG-PDMG-76. Target ↔ Reference

```text
NSIGHT TARGET
Vision
Big Picture
Logical
Physical
Mechanism
Runtime
    │
    │ compare
    ▼
PDMG REFERENCE
Module
Package
ServiceId
Source
Config
Runtime
    │
    ▼
CONFORM / GAP / DRIFT
    │
    ▼
ADR / Standard Promotion
```

---

# 174. PDMG에서 NSIGHT 표준으로 승격 가능한 후보

```text
ServiceId 기반 Routing
Thin Handler
Facade Use Case Boundary
Service / DAO Separation
GUID Correlation
Standard Message Envelope
Framework/Common Responsibility Separation
Runtime Timeout Boundary
Source-to-Mapper Naming Trace
```

단, 모두 Scope/ADR 승인 후 Target Standard가 된다.

---

# 175. 승격 전에 개선이 필요한 후보

```text
TCF OFF Business Boundary
JWT Algorithm / Verifier
JWT Key Lifecycle
Identity Binding
Generic Error
Early Error
Context Mutable Model
Runtime Evidence
OM
```

---

# 176. Bottom-up Evidence 최종 지도

## FIG-PDMG-77. PDMG Bottom-up

```text
DB / SQL
   ↑
Mapper
   ↑
DAO
   ↑
Service
   ↑
Facade
   ↑
Handler
   ↑
ServiceId
   ↑
TCF / Framework
   ↑
HTTP / JWT / UI
   ↑
Runtime Evidence
   ↑
NSIGHT Architecture
```

---

# 177. 다음 장 Handoff

다음 장은 다음 두 축을 결합하는 것이 자연스럽다.

```text
09. OM / DEVOPS / OBSERVABILITY / OPERATIONS
```

PDMG Source Deep Dive에서 확인한:

```text
Thread
Worker
Hikari
ServiceId
GUID
Error
JWT
ImageLog
Deployment
```

을 실제 운영 Control Plane으로 올린다.

---

# 178. 09장에서 반드시 답할 질문

```text
1. OM은 실제 어느 Source/Process에서 동작하는가?
2. JVM/Thread/Worker/Hikari/SQL을 어떻게 관측하는가?
3. ServiceId별 p95/Error/Timeout을 어떻게 보는가?
4. CI/CD가 어떤 Artifact를 어느 Host/JVM에 배포하는가?
5. Config Drift를 어떻게 탐지하는가?
6. Runtime Log/Metric/Trace를 어디에 모으는가?
7. Alert→Runbook→Recovery가 어떻게 연결되는가?
8. Architecture Gate를 CI/CD와 어떻게 결합하는가?
9. Deployment Evidence를 어떻게 생성하는가?
10. pdmg-om과 외부 APM/Monitoring의 책임은 어떻게 나누는가?
```

---

# 179. Definition of Done

## Module / Boundary
- [x] 5개 Module Baseline
- [x] Process/Module/Spring Context 분리
- [x] pdmg-om UNKNOWN 유지

## UI
- [x] Direct / Relay 공존
- [x] Static Folder
- [x] ServiceId 연결

## Business Source
- [x] Package Map
- [x] mgcoa9000 Source Tree
- [x] Handler/Facade/Service/DAO
- [x] Rule Layer AS-IS 오판 방지

## ServiceId
- [x] Naming
- [x] Current 13 Services
- [x] Duplicate Startup Fail
- [x] Dispatcher / Handler

## Mapper / Data
- [x] Java↔Mapper
- [x] Namespace
- [x] Resource Pattern
- [x] MapperScan
- [x] SqlId 주의

## Framework / Runtime
- [x] DefaultFilter
- [x] Security/MVC
- [x] Interceptor
- [x] Controller
- [x] TcfFacade
- [x] STF/ETF Actual 구분
- [x] Timeout / Worker
- [x] Context
- [x] TX
- [x] TCF OFF

## Message / Error / Logging
- [x] Request/Success/Error Envelope
- [x] Header Lifecycle
- [x] GUID
- [x] Error Paths
- [x] ImageLog

## JWT / Security
- [x] Login
- [x] RS256
- [x] Refresh Hash
- [x] SSO HMAC
- [x] RS256/HMAC Critical GAP
- [x] Ephemeral Key Risk
- [x] JWKS
- [x] Identity Binding GAP

## Governance
- [x] Source Rules
- [x] Test Matrix
- [x] GAP/RISK/ADR
- [x] NSIGHT Alignment
- [x] 09장 Handoff

**PDMG SOURCE / RUNTIME REFERENCE 장 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. `pdmg-om` Source/Runtime 확인
2. 전체 Module/Gradle Dependency 기계 스캔
3. UI Transaction Catalog ↔ Handler Registry 자동비교
4. Handler→Facade→Service→DAO Call Graph 전수생성
5. SqlId→Table/View 자동 Trace
6. Query/TX Timeout 실제값 확인
7. JDBC Cancel/Interrupt Integration Test
8. TCF OFF Business Boundary Target 결정
9. JWT RS256 Issuer↔Verifier 정합성 해결
10. Central Key Store / Key Rotation 결정
11. JWT Principal↔Header User Binding
12. Deployment Manifest / Host/JVM Mapping
13. Runtime Evidence Manifest 자동화

---

# 180. 장 최종 결론

> **PDMG는 NSIGHT 전체 Architecture가 아니라, 실제 Source와 Runtime으로 검증 가능한 매우 중요한 Reference 구현이다.**

> **가장 강한 부분은 ServiceId → Handler → Facade → Service → DAO → Mapper의 실행축과 Filter/TCF/Timeout/Context의 공통 Runtime 구조다.**

> **가장 우선적으로 닫아야 할 부분은 JWT 발급/검증 정합성, TCF OFF 경계, Query/TX Timeout, mutable Context, Deployment Mapping, pdmg-om, Runtime Evidence 자동화다.**

> **PDMG Pattern을 NSIGHT 표준으로 승격하려면 “구현되어 있다”는 이유가 아니라 Evidence → NFR → GAP → ADR → Approval 절차를 통과해야 한다.**
