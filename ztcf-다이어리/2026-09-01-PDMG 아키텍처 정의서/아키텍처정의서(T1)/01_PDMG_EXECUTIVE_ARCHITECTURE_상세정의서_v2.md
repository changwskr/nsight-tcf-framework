# PDMG 전체 아키텍처 정의서
# 01. PDMG EXECUTIVE ARCHITECTURE OVERVIEW
## PDMG Overall / Boundary / Module / Runtime / Data / Security / Operations
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-01-EXECUTIVE`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 작성기준: `00_PDMG_ARCHITECTURE_MASTER_INDEX` 및 PDMG Source/Runtime 분석자료  
> 관점: **PDMG를 먼저 정의하고 NSIGHT는 Target Alignment/PASS 판단기준으로 사용한다.**

---

# 0. Chapter Purpose

## FIG-01-01. Executive Architecture가 답해야 하는 질문

```text
PDMG는 무엇인가?
   ↓
어떤 Process / Module / JVM / Spring Context를 가지는가?
   ↓
Framework와 Business는 어디에서 나뉘는가?
   ↓
ServiceId는 어떻게 Business Component를 선택하는가?
   ↓
거래 한 건은 어떤 Thread / Transaction에서 실행되는가?
   ↓
DB / Security / Message / Error / Logging은 어디에 연결되는가?
   ↓
실제 구현은 Architecture와 얼마나 일치하는가?
```

이 장은 이후 02~18장의 상세 내용을 **한 장의 Executive Architecture Story**로 먼저 고정한다.

본 장의 역할은 개별 Class의 상세 구현을 설명하는 것이 아니다. 다음과 같이 전체 구조를 먼저 보여준 뒤 각 영역을 하위 장으로 넘긴다.

```text
PDMG Overall
   ↓
System / Process Boundary
   ↓
Module / Application Boundary
   ↓
Framework / Business Boundary
   ↓
Runtime
   ↓
Transaction / Data
   ↓
Security / Message / Error
   ↓
Physical / Operations
   ↓
Evidence / PASS / GAP
```

---

# 1. Evidence Register

## FIG-01-02. Evidence Stack

```text
PDMG Source / Config
       ↓
PDMG Runtime Analysis
       ↓
PDMG Module / Application Architecture
       ↓
Transaction / Security / Message / Ops Analysis
       ↓
Architecture Decision / PASS Register
       ↓
NSIGHT Target Alignment
```

| Evidence ID | 근거자료 | 본 장 사용 목적 | 상태 |
|---|---|---|---|
| EV-01-01 | `00_PDMG_ARCHITECTURE_MASTER_INDEX` | Evidence/상태/Top-down 작성기준 | `[DECISION]` |
| EV-01-02 | `NSIGHT_PDMG_아키텍처_정의서_III_PDMG_Module_Application_Architecture` | Module/Process/Context/Package/TCF ON-OFF | `[AS-IS REFERENCE]` |
| EV-01-03 | `NSIGHT_PDMG_아키텍처_정의서_IV_PDMG_Online_Runtime_TCF_Flow` 및 Runtime 상세자료 | Online 실행순서 | `[AS-IS REFERENCE]` |
| EV-01-04 | `NSIGHT_PDMG_아키텍처_정의서_V_Transaction_Timeout_Thread_DB_Architecture` | Worker/TX/Timeout/DB | `[AS-IS REFERENCE]` |
| EV-01-05 | `NSIGHT_PDMG_아키텍처_정의서_VI_Standard_Message_Context_Error_Logging` | hdr_nhnis/Context/Error/Logging | `[AS-IS REFERENCE]` |
| EV-01-06 | `NSIGHT_PDMG_아키텍처_정의서_VII_Security_SSO_JWT_Session` | JWT/SSO/JWKS/Session | `[AS-IS REFERENCE]` |
| EV-01-07 | `NSIGHT_PDMG_아키텍처_정의서_VIII_Infrastructure_WAS_Capacity_HA_DR` | Physical Working Path/Capacity/HA | `[PHYSICAL REFERENCE]` |
| EV-01-08 | `NSIGHT_PDMG_아키텍처_정의서_IX_DevOps_OM_Observability` | Build/Deploy/Observability | `[AS-IS + TARGET REFERENCE]` |
| EV-01-09 | `NSIGHT_PDMG_아키텍처_정의서_X_Naming_ServiceId_Traceability_Closed_Loop` | Naming/ServiceId/Traceability | `[GOVERNANCE REFERENCE]` |
| EV-01-10 | `PDMG Source Runtime Reference Deep Dive` | Bottom-up Source Cross-check | `[AS-IS REFERENCE]` |
| EV-01-11 | Architecture Decision/PASS Register | Architecture PASS/PDMG Conformance | `[DECISION]` |
| EV-01-12 | NSIGHT Big Picture / 발표자료 | 상위 Target/전략 Context | `[NSIGHT REFERENCE]` |

---

# 2. Figure Plan

## FIG-01-03. Chapter Drill-down Plan

```text
L0  Master Architecture
 ↓
L1  System / Process Boundary
 ↓
L2  5 Module / Runtime Container
 ↓
L3  Framework / Business Component
 ↓
L4  Online Runtime / Thread / TX
 ↓
L5  Package / ServiceId / Config / Evidence
```

| FIG | 제목 | Level | 목적 |
|---|---|---:|---|
| FIG-01-04 | PDMG Master Architecture | L0 | 전체 구조 |
| FIG-01-05 | PDMG Context | L0/L1 | 외부/내부 위치 |
| FIG-01-06 | Five Module View | L1/L2 | 모듈 책임 |
| FIG-01-07 | Build vs Process vs Context | L2 | 경계 분리 |
| FIG-01-08 | Package/Context | L2/L3/L5 | Source 구조 |
| FIG-01-09 | Framework vs Business | L2/L3 | 책임 분리 |
| FIG-01-10 | TCF ON Runtime | L1~L4 | Online 실행 |
| FIG-01-11 | TCF OFF Runtime | L2~L4 | OFF 차이 |
| FIG-01-12 | Request vs Worker | L3/L4 | Thread 경계 |
| FIG-01-13 | Transaction | L4 | DB TX |
| FIG-01-14 | Timeout | L4/L5 | Deadline |
| FIG-01-15 | Message | L2/L4 | Request/Response |
| FIG-01-16 | Context | L3/L4 | ThreadLocal |
| FIG-01-17 | Error | L3/L4 | Error path |
| FIG-01-18 | Security | L1~L4 | Auth/JWT |
| FIG-01-19 | JWT GAP | L4/L5 | Critical GAP |
| FIG-01-20 | ServiceId | L2~L5 | 거래식별 |
| FIG-01-21 | Data Access | L3/L4 | DAO/Mapper/DB |
| FIG-01-22 | Physical Handoff | L1/L2 | WEB/WAS/DB |
| FIG-01-23 | Build/Deploy | L2/L5 | Delivery |
| FIG-01-24 | Observability | L2~L5 | Evidence |
| FIG-01-25 | NSIGHT Alignment | L0 | Target 관계 |
| FIG-01-26 | Normal Pattern | L0/L3 | 정상구조 |
| FIG-01-27 | Forbidden Pattern | L0/L3 | 금지구조 |
| FIG-01-28 | GAP Map | L0 | Current GAP |
| FIG-01-29 | Decision Map | L0 | ADR 연계 |
| FIG-01-30 | PASS Model | L0 | 판정 |
| FIG-01-31 | Final Story | L0 | 장 결론 |
| FIG-01-32 | Handoff | L0/L1 | 02장 연결 |

---

# 3. L0 — PDMG Master Architecture

## FIG-01-04. PDMG 전체 Architecture

```text
┌──────────────────────────── CHANNEL / USER ────────────────────────────┐
│                                                                       │
│                        User / Browser                                 │
│                                                                       │
└──────────────────────────────┬────────────────────────────────────────┘
                               │ HTTP
             ┌─────────────────┼─────────────────┐
             │                 │                 │
             ▼                 ▼                 ▼
┌──────────────────┐ ┌──────────────────┐ ┌────────────────────────────┐
│ pdmg-ui          │ │ pdmg-jwt         │ │ pdmg-service               │
│ [AS-IS]          │ │ [AS-IS]          │ │ [AS-IS]                   │
│                  │ │                  │ │                            │
│ UI / Static      │ │ Login / SSO      │ │ Online Business Runtime    │
│ Request Build    │ │ Access / Refresh │ │                            │
│ ServiceId Call   │ │ JWKS             │ │ ┌────────────────────────┐ │
└────────┬─────────┘ └────────┬─────────┘ │ │ pdmg-fw [AS-IS]       │ │
         │ HTTP               │ Token     │ │ Framework Mechanism    │ │
         └──────────────┬─────┘           │ │ Filter / Context / TCF │ │
                        │                 │ │ Timeout / Error / Log  │ │
                        ▼                 │ └───────────┬────────────┘ │
                     Bearer               │             ▼              │
                                          │ Handler                    │
                                          │    ↓                       │
                                          │ Facade                     │
                                          │    ↓                       │
                                          │ Service                    │
                                          │    ↓                       │
                                          │ DAO                        │
                                          │    ↓                       │
                                          │ Mapper / SQL               │
                                          └───────────┬────────────────┘
                                                      │ MyBatis/JDBC
                                                      ▼
                                          ┌────────────────────────────┐
                                          │ RDW / DB                   │
                                          │ [DATA BOUNDARY]            │
                                          └────────────────────────────┘


                                          ┌────────────────────────────┐
                                          │ pdmg-om                    │
                                          │ [CURRENT DETAIL UNKNOWN]   │
                                          └────────────────────────────┘
```

### Executive 해석

PDMG는 하나의 단일 Process 이름이 아니다.

```text
PDMG
=
복수 Build Module
+
복수 HTTP Entry
+
Service Runtime
+
Framework Mechanism
+
Business Components
+
DB Access
+
Security / Operations
```

현재 강하게 확인된 Current Source는 `pdmg-ui`, `pdmg-jwt`, `pdmg-fw`, `pdmg-service`이며, `pdmg-om`은 이름과 목표영역은 존재하지만 실제 Current Source/Runtime 상세는 `[UNKNOWN]`으로 유지한다.

---

# 4. L0/L1 — PDMG System Context

## FIG-01-05. PDMG가 정보계에서 차지하는 위치

```text
                    ┌────────────────────┐
                    │ User / Channel     │
                    └─────────┬──────────┘
                              │
                              ▼
                 ┌─────────────────────────┐
                 │ Access / Delivery       │
                 │ GSLB / L4 / WEB        │
                 │ [NSIGHT Physical Ref]   │
                 └───────────┬─────────────┘
                             │
                             ▼
┌────────────────────── PDMG APPLICATION BOUNDARY ─────────────────────┐
│                                                                      │
│ pdmg-ui   pdmg-jwt   pdmg-service + pdmg-fw   pdmg-om?              │
│                                                                      │
└───────────────────────────────┬──────────────────────────────────────┘
                                │
            ┌───────────────────┼────────────────────┐
            │                   │                    │
            ▼                   ▼                    ▼
       RDW / DB            External API        Event/CDC/ETL/File
       [CURRENT]            [Inventory]         [NSIGHT Relation /
                                                 PDMG Current Unknown]
                                │
                                ▼
                       Monitoring / Audit
```

### 경계 판정

- PDMG는 NSIGHT 전체와 동일하지 않다.
- PDMG는 Marketing/Business Application 계층의 실제 Reference 구현으로 해석한다.
- Event/CDC/ETL/File은 NSIGHT 상위 Architecture에서 중요하지만, PDMG Current에 직접 구현되어 있다고 Evidence 없이 단정하지 않는다.
- PDMG의 가장 확실한 현재 Data Access는 MyBatis/JDBC 기반 DB 접근이다.

---

# 5. L1/L2 — PDMG Five Module Architecture

## FIG-01-06. 5 Module Responsibility Map

```text
PDMG
│
├─ pdmg-ui
│    ├─ 화면 / Static Resource
│    ├─ 거래 선택
│    ├─ ServiceId 기반 요청
│    └─ Authorization 전달
│
├─ pdmg-jwt
│    ├─ Login
│    ├─ SSO 요청검증 / Token 발급
│    ├─ Access / Refresh
│    ├─ Refresh State
│    └─ JWKS
│
├─ pdmg-fw
│    ├─ Filter
│    ├─ ServiceContext
│    ├─ Security Integration
│    ├─ TCF / Dispatcher Support
│    ├─ Timeout / Worker
│    ├─ MVC Support
│    ├─ Error
│    └─ Logging
│
├─ pdmg-service
│    ├─ Online Entry
│    ├─ TransactionHandler
│    ├─ Facade
│    ├─ Service
│    ├─ DAO
│    ├─ DTO
│    └─ Mapper
│
└─ pdmg-om
     └─ [UNKNOWN]
```

### Module Responsibility Matrix

| Module | 책임 | Architecture 영역 | 현재상태 |
|---|---|---|---|
| `pdmg-ui` | UI/요청생성/Service 호출 | Presentation/Client | `[AS-IS]` |
| `pdmg-jwt` | 인증/Token/JWKS | Security | `[AS-IS]` |
| `pdmg-fw` | 공통 Runtime Mechanism | Framework | `[AS-IS]` |
| `pdmg-service` | 업무 Use Case/Data Access | Application | `[AS-IS]` |
| `pdmg-om` | 운영관리 후보 | Operations | `[UNKNOWN]` |

---

# 6. L2 — Build Module ≠ Process ≠ Spring Context

## FIG-01-07. 세 가지 경계

```text
[BUILD MODULE]
pdmg-service
pdmg-fw
pdmg-jwt
pdmg-ui
pdmg-om?
       │
       │ build dependency
       ▼
[PROCESS / JVM]
pdmg-service Runtime
       │
       ▼
[SPRING APPLICATION CONTEXT]
scanBasePackages = "nhnis"
       │
       ├─ nhnis.mg.*
       └─ nhnis.fw.*
```

핵심 식:

```text
Build Module
≠ Process
≠ JVM
≠ Spring ApplicationContext
≠ Logical Node
```

`pdmg-fw`가 별도 Gradle/Build Module이라는 사실만으로 별도 HTTP Remote Server로 그리면 안 된다.

Current 분석에서는 `PdmgApplication`의 `scanBasePackages="nhnis"`를 근거로 `nhnis.mg.*`와 `nhnis.fw.*` Bean이 같은 Spring Context에 올라갈 수 있는 구조가 확인된다.

---

# 7. L2/L3/L5 — Package / Source Architecture

## FIG-01-08. Current Package Projection

```text
PDMG Source
│
├─ pdmg-service
│    └─ nhnis.mg.co.a.*
│         ├─ entry.handler
│         ├─ application.controller
│         ├─ application.facade
│         ├─ application.service
│         ├─ application.dto
│         └─ persistence.dao
│
├─ pdmg-fw
│    ├─ nhnis.fw.*
│    └─ com.ims.superspring.*
│
├─ pdmg-ui
│    └─ nhnis.mg.ui.*
│
└─ pdmg-jwt
     └─ nhnis.mg.jw.a.*
```

Mapper Resource:

```text
rdw.mg.co.a/
```

### 중요한 Source Fact

현재 일반 업무계층에서 `application.rule`은 전수 확인되지 않았다.

따라서:

```text
Rule Layer
= Target Candidate / Selective Pattern
≠ Current Universal AS-IS
```

---

# 8. L2/L3 — Framework / Business Responsibility

## FIG-01-09. Framework vs Business

```text
┌──────────────────── FRAMEWORK ──────────────────────┐
│                                                    │
│ How to execute safely?                             │
│                                                    │
│ Filter                                             │
│ Context                                            │
│ Security Integration                               │
│ TCF / Dispatcher Infrastructure                    │
│ Worker / Timeout                                   │
│ Transaction Coordination                           │
│ Error / Response                                   │
│ Logging                                            │
│                                                    │
└───────────────────────┬────────────────────────────┘
                        │ governs
                        ▼
┌──────────────────── BUSINESS ───────────────────────┐
│                                                    │
│ What business work to execute?                     │
│                                                    │
│ Handler                                            │
│ Facade                                             │
│ Service                                            │
│ DAO                                                │
│ Mapper / SQL                                       │
│                                                    │
└────────────────────────────────────────────────────┘
```

### 정상 호출

```text
Handler
  ↓
Facade
  ↓
Service
  ↓
DAO
  ↓
Mapper
```

### 금지 호출

```text
Handler ─────► DAO
        X

Handler ─────► Mapper
        X

Controller ──► DAO
        X
```

---

# 9. L1~L4 — TCF ON Online Runtime

## FIG-01-10. TCF ON + Timeout ON Current Runtime

```text
HTTP Request
   │
   ▼
DefaultFilter
   ├─ Request Body Cache
   ├─ Header / GUID
   ├─ ServiceContext
   └─ MDC
   │
   ▼
Spring SecurityFilterChain
   │
   ▼
DispatcherServlet
   │
   ▼
ServicePreventionInterceptor.preHandle
   │
   ▼
OnlineTransactionController
   │
   ▼
TcfFacade
   │
   ▼
OnlineTimeoutExecutor
   │ submit
   ▼
════════════ Worker Thread ════════════
TransactionTemplate BEGIN
   │
   ▼
TransactionDispatcher
   │
   ▼
TransactionHandler
   │
   ▼
Business Facade
   │
   ▼
BizPrePostAspect
   │
   ▼
Service
   │
   ▼
DAO
   │
   ▼
Mapper XML / JDBC
   │
   ▼
DB
   │
   ▼
Deadline Check
   ├─ OK       → COMMIT
   └─ EXCEEDED → ROLLBACK
═══════════════════════════════════════
   │
   ▼
Response / Exception
   │
   ▼
ResponseBodyAdvice
   │
   ▼
Interceptor.afterCompletion
   │
   ▼
DefaultFilter.finally / Context Clear
```

이 흐름은 PDMG Current의 대표적인 **TCF ON + Timeout ON** 경로다.

---

# 10. L2~L4 — TCF OFF Runtime

## FIG-01-11. TCF OFF Current Pattern

```text
HTTP
 ↓
DefaultFilter
 ↓
Security
 ↓
DispatcherServlet
 ↓
Business MVC Controller
 ↓
Facade / Service
 ↓
DAO / Mapper
 ↓
DB
```

### 핵심 차이

```text
TCF ON
Common Controller
  ↓
Dispatcher
  ↓
Handler
  ↓
Facade

TCF OFF
Business Controller
  ↓
Facade / Service
```

현재 AS-IS 일부 OFF Controller가 Facade를 우회하고 Service를 직접 호출하는 정황이 있으므로:

```text
TCF ON Business Boundary
           ≠
TCF OFF Business Boundary

[GAP]
```

으로 본다.

---

# 11. L3/L4 — Request Thread vs Worker Thread

## FIG-01-12. Thread Architecture

```text
════════════ Request Thread ════════════
Filter
Security
MVC
Controller
TcfFacade
OnlineTimeoutExecutor
Future.get(timeout)
Response
Filter.finally
        │
        │ submit
        ▼
════════════ Worker Thread ═════════════
Context Install
TransactionTemplate BEGIN
Dispatcher
Handler
Facade
Service
DAO / SQL
Deadline Check
COMMIT / ROLLBACK
Context Clear
```

Current Worker 이름은 `pdmg-online-N` 형태로 분석된다.

Current Snapshot:

```text
timeout.enabled = true
milliseconds    = 5000
pool-size       = 20
queue-capacity  = 100

[AS-IS SNAPSHOT]
```

이 값은 **Target SLA / Capacity Standard가 아니다.**

---

# 12. L4 — Transaction Architecture

## FIG-01-13. Transaction Ownership

```text
Worker Thread
   ↓
TransactionTemplate
   ↓
BEGIN Physical Transaction
   ↓
TransactionDispatcher
   ↓
TransactionHandler
   ↓
Facade
@Transactional(REQUIRED)
   ↓
Service
   ↓
DAO / Mapper / DB
   ↓
Deadline
   ├─ OK → COMMIT
   └─ FAIL → rollbackOnly → ROLLBACK
```

핵심:

```text
@Transactional(REQUIRED)
= 기존 Transaction이 있으면 참여

따라서

Facade annotation
≠ 반드시 Physical BEGIN 지점
```

TCF ON + Timeout ON에서는 Worker의 `TransactionTemplate`이 외부 Physical Transaction을 먼저 시작할 수 있다.

---

# 13. L4/L5 — Timeout Architecture

## FIG-01-14. Timeout Lifecycle

```text
Request Thread
   │
   ├─ submit Worker
   │
   └─ Future.get(5000ms)
          │
          ├─ Worker 완료 → Result
          │
          └─ Timeout
                ↓
             cancel(true)
                ↓
             HTTP 504
```

하지만:

```text
HTTP 504
≠ Worker Thread 즉시 종료
≠ JDBC Statement Cancel 보장
≠ DB Session Kill
≠ DB Rollback 완료
```

따라서 Target Architecture는 다음 계층을 별도로 결정해야 한다.

```text
DB Query Timeout
      <
Transaction / Worker Deadline
      <
Server / Downstream Timeout
      <
Client Timeout
```

---

# 14. L2/L4 — Standard Message Architecture

## FIG-01-15. Request / Response Envelope

```text
REQUEST
┌────────────────────────────────┐
│ hdr_nhnis                      │
│  └─ sys_comm                   │
│      ├─ GUID / std_gbl_id      │
│      ├─ ServiceId              │
│      ├─ Source Context         │
│      └─ User Context           │
├────────────────────────────────┤
│ dto                            │
└────────────────┬───────────────┘
                 │
                 ▼
              Business
                 │
        ┌────────┴────────┐
        ▼                 ▼
SUCCESS                 ERROR
{hdr_nhnis,dto}         {hdr_nhnis,result}
```

### Executive 의미

`hdr_nhnis`는 단순 화면 Header가 아니라 Runtime Context/Trace/Error/Security를 연결하는 공통 Contract 영역이다.

---

# 15. L3/L4 — ServiceContext Architecture

## FIG-01-16. Context Lifecycle

```text
Request Thread
DefaultFilter
   ↓
ServiceContext Create
   ↓
Header / GUID / MDC
   ↓
Interceptor / Controller enrich
   ↓
Worker Capture
   ↓
Worker Context Install
   ↓
Business Execution
   ↓
Worker Context Clear
   ↓
Response Advice
   ↓
afterCompletion
   ↓
Filter.finally
   ↓
ThreadLocal remove
```

### Current Risk

현재 분석에서는 Worker에 동일 mutable `ServiceContext` reference가 전달되며 Servlet Request/Response reference도 함께 포함될 가능성이 있다.

따라서:

```text
Request Scoped Mutable Context
          ↓
Worker Thread 공유

[RISK / GAP]
```

Target 후보는 Worker 전용 Immutable Snapshot이다.

---

# 16. L3/L4 — Error Architecture

## FIG-01-17. Error Handling Boundary

```text
Business / Controller
        ↓
MVC Exception
        ↓
GlobalExceptionHandler
        ↓
Standard Result Envelope

                    BUT

Filter / Security
Early sendError
        ↓
Servlet Response
        ↓
MVC Advice 우회 가능
        ↓
Standard Envelope 미보장
```

현재 대표 Mapping Reference:

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

Global `Exception.class` 전수 fallback과 Filter 단계 표준 Error Contract는 추가 검증/정의가 필요하다.

---

# 17. L1~L4 — Security Architecture

## FIG-01-18. Authentication / Token / Business Trust

```text
User
  ↓
Login / SSO
  ↓
pdmg-jwt
  ├─ Credential / SSO Validation
  ├─ Access Token
  ├─ Refresh Token
  └─ JWKS
  ↓
Bearer Token
  ↓
pdmg-service / pdmg-fw
  ↓
JWT Verification
  ↓
Trusted Principal
  ↓
Business User Context
  ↓
Authorization
  ↓
ServiceId / Business
```

Login/SSO 대표 Reference:

```text
mgjwa1000C0
= Login

mgjwa1000C1
= SSO Token Issue
```

SSO C1은 단순 OIDC Callback으로 단정하지 않고, 현재 분석상 Allowed Service/Timestamp/HMAC/Caller IP 검증 뒤 Token Pair를 발급하는 구조로 본다.

---

# 18. L4/L5 — Critical JWT GAP

## FIG-01-19. Issuer / Verifier Mismatch

```text
┌──────────────────┐
│ pdmg-jwt         │
│ RS256 Issuer     │
└────────┬─────────┘
         │ JWT
         ▼
┌──────────────────┐
│ Business Runtime │
│ pdmg-fw          │
│ HMAC jwt.secret  │
│ verifier path    │
└────────┬─────────┘
         ▼
   [CRITICAL GAP]
```

Target Decision:

```text
RS256
+ Versioned Signing Key
+ kid
+ JWKS
+ Business-side Public Key Verification
```

Current Key lifecycle에도 재기동/다중 Instance/DR에서 동일 `kid`와 Key Material 정합을 검증해야 하는 GAP가 있다.

---

# 19. L2~L5 — Program / ServiceId Architecture

## FIG-01-20. ServiceId Backbone

```text
Business Classification
MG / CO / A
       ↓
Program ID
mgcoa9001
       ↓
ServiceId
mgcoa9001S0
       ↓
Handler Registry
       ↓
TransactionHandler
       ↓
Facade
       ↓
Service
       ↓
DAO
       ↓
Mapper / SqlId
       ↓
SQL / Table
```

Program:

```text
2 + 2 + 1 + 4
= 9 chars
```

ServiceId:

```text
2 + 2 + 1 + 4 + 1 + 1
= 11 chars
```

Current Handler Registry 분석은 13 ServiceIds를 가진다.

### Drift Candidate

```text
UI Transaction Catalog
        ↓ compare
Backend Handler Registry
        ↓
MATCH / DRIFT
```

자동검증이 필요하다.

---

# 20. L3/L4 — Data Access Architecture

## FIG-01-21. Service to DB

```text
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
MyBatis Mapper
   ↓
SqlId
   ↓
SQL
   ↓
RDW / DB
```

Mapper Resource 대표:

```text
rdw.mg.co.a/
```

Current Data Access는:

```text
MyBatis
+
JDBC
+
Connection Pool
```

구조로 분석된다.

PDMG가 RDW/ADW 전체 Data Platform을 소유한다고 표현하지 않는다. PDMG Current의 직접 역할과 상위 NSIGHT Data Architecture 역할을 분리한다.

---

# 21. L1/L2 — Physical Architecture Handoff

## FIG-01-22. Logical → Physical Working Path

```text
User / Channel
      ↓
GSLB
      ↓
L4
      ↓
Apache WEB
      ↓
Tomcat JVM
      ↓
Business WAR
      ↓
Spring Runtime
      ├─ pdmg-fw
      └─ pdmg-service
      ↓
Hikari / MyBatis / JDBC
      ↓
RDW / ADW / DB
```

이 경로는 **NSIGHT/PDMG Working Physical Baseline**이다.

하지만 다음은 별도 실재 Evidence가 필요하다.

```text
Actual Hostname
Actual VM
Actual JVM Count
Actual WAR Mapping
Actual Port
Actual Oracle Version
```

따라서 Executive 수준에서는 `[OPEN/GAP]`으로 둔다.

---

# 22. L2/L5 — Build / Deployment Architecture

## FIG-01-23. Source to Artifact

```text
Git Source
   ↓
Gradle Multi-project
   ↓
Compile / Test
   ↓
WAR / Artifact
   ↓
Artifact Hash
   ↓
Deployment
   ↓
DeploymentId
   ↓
Host / JVM / WAR
```

Current Build Reference:

```text
Java 21
Spring Boot 3.5.14
Gradle Multi-project
```

Architecture Target은 동일 Source Commit/Build/Artifact/Deployment를 추적 가능하게 연결하는 것이다.

---

# 23. L2~L5 — Operations / Observability

## FIG-01-24. Runtime Evidence Architecture

```text
Runtime
  ↓
┌────────────────────────────────┐
│ Metric                         │
│ Log                            │
│ Trace                          │
│ ImageLog                       │
└───────────────┬────────────────┘
                ↓
GUID / ServiceId
Host / JVM
SqlId / ErrorCode
DeploymentId
                ↓
Dashboard / Alert
                ↓
Runbook
                ↓
Runtime Evidence
                ↓
Drift / GAP / ADR
```

PDMG Current에는 GUID/MDC/ImageLog 등 관측 단서가 존재한다.

다만:

```text
Logging
≠ Runtime Evidence 완성
```

Deployment/Host/JVM/Rule/Test까지 연결되어야 Architecture Evidence가 된다.

---

# 24. L0 — NSIGHT Target Alignment

## FIG-01-25. Current PDMG ↔ NSIGHT Target

```text
NSIGHT Target Context
│
├─ Scalable / Resilient / Data-Centric
├─ RDW / ADW Workload Separation
├─ CDC Near Real-time
├─ Event-driven Integration
├─ Standard API / ETL / File
├─ WEB/WAS Scale-out
├─ HA / DR
└─ Observability / Evidence
        │
        │ compare
        ▼
PDMG Current
│
├─ UI / JWT
├─ Framework / TCF
├─ Worker / Transaction
├─ Business Layer
├─ DB Access
└─ Logging / Trace Clues
```

판정원칙:

```text
PDMG Current에 있음
→ AS-IS

NSIGHT에만 있음
→ Target Reference

Current와 Target이 다름
→ GAP / ADR
```

---

# 25. Normal Architecture Pattern

## FIG-01-26. PDMG Normal Pattern

```text
Request
  ↓
Framework Entry
  ↓
Trusted Identity
  ↓
ServiceId Routing
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
DB
  ↓
Standard Response
  ↓
Evidence
```

정상구조의 핵심은 **책임과 실행경계를 분리하면서 Trace가 끊기지 않는 것**이다.

---

# 26. Forbidden Architecture Pattern

## FIG-01-27. 금지 / 오해 방지

```text
[1] Module = Server
          X

[2] pdmg-fw →HTTP→ pdmg-service
          X

[3] Handler → DAO / Mapper
          X

[4] Controller → DAO
          X

[5] HTTP 504 = DB rollback 완료
          X

[6] RS256 Issue + HMAC Verify를 정상으로 승인
          X

[7] NSIGHT Event/CDC/ETL = PDMG AS-IS
          X

[8] Candidate Capacity = Production Fact
          X
```

---

# 27. Current GAP / Risk Map

## FIG-01-28. Executive GAP Map

```text
PDMG Current
│
├─ MODULE
│   └─ pdmg-om detail
│      [UNKNOWN]
│
├─ APPLICATION
│   └─ TCF OFF Controller → Service direct
│      [GAP]
│
├─ CONTEXT
│   └─ mutable ServiceContext worker sharing
│      [RISK/GAP]
│
├─ TIMEOUT
│   ├─ 5000ms current only
│   └─ JDBC cancel guarantee 없음
│      [OPEN/RISK]
│
├─ ERROR
│   └─ Filter/Security early error envelope
│      [GAP]
│
├─ SECURITY
│   ├─ RS256 Issue vs HMAC Verify
│   │  [CRITICAL GAP]
│   ├─ Key lifecycle / multi-instance
│   │  [GAP]
│   └─ Principal ↔ Business User Binding
│      [GAP]
│
├─ TRACE
│   └─ UI Catalog ↔ Handler Registry
│      [GAP]
│
├─ DEPLOYMENT
│   └─ Artifact → Host/JVM/WAR mapping
│      [OPEN/GAP]
│
└─ EVIDENCE
    └─ Runtime Evidence automation
       [GAP]
```

---

# 28. Architecture Decision Map

## FIG-01-29. Executive Decisions

```text
Application
├─ TCF ON/OFF Policy
├─ Common Facade Business Core
├─ Rule Layer
└─ Standard Error

Security
├─ RS256/JWKS
├─ Key Management
├─ Session/State
└─ Identity Binding

Interface/Runtime
├─ Timeout Budget
├─ Retry/Idempotency
└─ Direct DB Boundary

Infrastructure
├─ WAS Scale-out
├─ JVM/WAR Isolation
├─ Pool Capacity
└─ HA/DR

Operations
├─ Artifact Promotion
├─ Observability
├─ Config/Secret
└─ Runtime Evidence
```

각 결정은 17장의 PASS/GAP/ADR에서 최종 집계한다.

---

# 29. Architecture PASS vs Current PDMG

## FIG-01-30. Dual PASS Model

```text
Architecture Definition
        ↓
[CONDITIONAL PASS]

Current PDMG
        ↓
[PARTIAL / GAP]

이 두 값은 동일하지 않다.
```

| 영역 | Architecture 정의 | Current PDMG | Executive 판정 |
|---|---|---|---|
| Module Boundary | 명확 | 4개 강한 Evidence, OM Unknown | CONDITIONAL PASS |
| Framework/Business | 명확 | 대체로 정합 | PASS/PARTIAL |
| TCF Runtime | 명확 | ON 정합, OFF drift | CONDITIONAL |
| Thread/TX | 명확 | Current Snapshot 확인 | PASS/PARTIAL |
| Timeout | 계층형 필요 | Worker 5s만 확인 | CONDITIONAL |
| Message | hdr_nhnis + dto/result | 확인 | PASS |
| Error | Standard Contract | Early error GAP | CONDITIONAL |
| JWT | RS256+JWKS Target | Issuer/Verifier conflict | GAP |
| Data Access | DAO/MyBatis/JDBC | 확인 | PASS |
| Deployment Trace | Source→Artifact→Host/JVM | 전수 미확보 | GAP |
| Observability | Metric/Log/Trace/Evidence | 일부만 확인 | PARTIAL |
| OM | Control Plane 정의 필요 | Unknown | OPEN |

---

# 30. PASS 전환조건

## FIG-01-31. Conditional → PASS

```text
Current Conditional
      ↓
Critical GAP Close
      ↓
Source / Config Conformance
      ↓
Integration / Security Test
      ↓
Performance / Failure Test
      ↓
Deployment Mapping
      ↓
Runtime Evidence
      ↓
01 Executive PASS
```

Executive PASS 전환조건:

1. JWT Issuer/Verifier를 하나의 알고리즘/키체계로 정합.
2. Versioned Key/kid/JWKS/Rotation/Multi-instance 운영구조 증적.
3. Trusted Principal과 `hdr_nhnis` Business User Context의 정합.
4. TCF OFF의 Business Core 진입경계를 Facade 중심으로 정리하거나 예외 ADR 승인.
5. Worker Context의 mutable object 공유 위험 해소 또는 안전성 Evidence.
6. Filter/Security Early Error를 포함하는 Standard Error Contract 확정.
7. DB Query/Worker/TX/Server/Client Timeout Budget 확정.
8. UI Catalog와 Backend Handler ServiceId Registry 자동정합.
9. Artifact→DeploymentId→Host/JVM/WAR 실배치 Mapping.
10. `pdmg-om` Source/Runtime Evidence 확보 또는 Current Scope 제외 결정.
11. Runtime Evidence를 GUID/ServiceId/DeploymentId와 연결.
12. Critical Architecture Rules에 대한 자동/수동 Conformance Test.

---

# 31. 이 장에서 확정하지 않는 값

## FIG-01-32. OPEN Values

```text
Production Hostname
Production Port
Tomcat Exact Version
Oracle Exact Version
Final Hikari Pool
Final Tomcat maxThreads
Final JVM Heap
Final Server Count
Final RTO / RPO
Final CDC SLA
Event/CDC/ETL Product in PDMG
pdmg-om Current Implementation
```

이 값들은 해당 상세 장에서 Evidence가 확보될 때만 확정한다.

---

# 32. Executive Architecture Final Story

## TEXT ARCHITECTURE — PDMG를 한 문장으로 읽는 방식

```text
PDMG
  ↓
복수 Build Module로 구성되지만
Module = Server가 아니며
  ↓
pdmg-service Runtime 안에서
pdmg-fw Framework Mechanism과
Business Component가 협력하고
  ↓
HTTP 요청은
Filter → Security → MVC → TCF를 지나
  ↓
Worker Thread의 Transaction 안에서
Handler → Facade → Service → DAO → Mapper → DB
경로를 수행하고
  ↓
hdr_nhnis / GUID / ServiceContext / Error / Logging으로
거래를 추적하며
  ↓
pdmg-jwt의 인증/Token 구조와 결합되고
  ↓
Source / Artifact / Deployment / Runtime Evidence까지
연결되어야
완전한 PDMG Architecture가 된다.
```

---

# 33. Chapter PASS Summary

## TEXT ARCHITECTURE — 01장 판정

```text
Architecture Definition
       ↓
CONDITIONAL PASS

PDMG Current Conformance
       ↓
PARTIAL / GAP

Runtime Evidence Coverage
       ↓
MEDIUM

Highest Criticality
       ↓
JWT Issuer / Verifier
```

### Summary

| 항목 | 판정 |
|---|---|
| Architecture Definition | **CONDITIONAL PASS** |
| Current PDMG Conformance | **PARTIAL / GAP** |
| Runtime Evidence Coverage | **MEDIUM** |
| Critical Security GAP | **존재** |
| Module Boundary | **설명 가능** |
| TCF ON Runtime | **설명 가능** |
| TCF OFF 정합 | **GAP** |
| Deployment 실배치 | **OPEN/GAP** |
| pdmg-om | **UNKNOWN** |

---

# 34. Next Chapter Handoff

## TEXT ARCHITECTURE — 01 → 02

```text
01 Executive Architecture
"PDMG 전체는 이렇게 구성되고 실행된다."
              ↓
02 System Context & Boundary
"정확히 누가 PDMG를 호출하고,
어떤 HTTP / Process / Data / Security /
External / Observability Boundary가 존재하는가?"
```

02장에서는 다음을 확대한다.

```text
User / Browser
PDMG Inbound
PDMG Outbound
pdmg-ui / pdmg-jwt / pdmg-service Process
DB Boundary
External Interface Boundary
Security Trust Boundary
Observability Boundary
Forbidden Boundary
```

---

# 35. 01장 최종 결론

## TEXT ARCHITECTURE — Final Executive Conclusion

```text
PDMG Architecture
=
Module Structure
+
Runtime Mechanism
+
Business Layer
+
Thread / Transaction
+
Message / Context
+
Security
+
Data Access
+
Deployment / Operations
+
Traceability

       ↓

설명 가능
+
Source 추적 가능
+
Runtime 검증 가능
+
PASS / GAP 판정 가능
```

**01장 판정: `CONDITIONAL PASS`**

이 판정은 Architecture 정의가 부족해서라기보다, Current PDMG의 Critical GAP와 Runtime/Deployment Evidence가 아직 완전하게 닫히지 않았기 때문이다.
