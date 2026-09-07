# PDMG 전체 아키텍처 정의서
# 01. PDMG EXECUTIVE ARCHITECTURE OVERVIEW
## PDMG Overall / System / Module / Runtime / Security / Data / Operations
## Visual-First / Top-down → Drill-down

> 문서 ID: `PDMG-ARCH-01-EXECUTIVE`  
> 상태: `[WORKING BASELINE-2026-09-01]`  
> 목적: 이 장 하나만 읽어도 PDMG의 전체 구조, 핵심 Runtime, 주요 Boundary, Critical GAP를 설명할 수 있게 한다.

---

# 0. Chapter Purpose

## FIG-01-01. 이 장의 질문

```text
PDMG는 무엇인가?
   ↓
어떤 Module이 있는가?
   ↓
어떤 Process/JVM/Context로 실행되는가?
   ↓
요청 한 건은 어떻게 DB까지 가는가?
   ↓
Framework와 Business 책임은 무엇인가?
   ↓
Security / Message / Error는 어떻게 겹쳐지는가?
   ↓
어디까지가 Current Fact이고
어디부터가 Target / GAP인가?
```

---

# 1. Evidence Register

| Evidence ID | 근거 | 이 장에서 사용하는 내용 | 상태 |
|---|---|---|---|
| EV-01-01 | III PDMG Module/Application | 5 Module, Process/Context, Business Layer | `[FACT/ANALYSIS]` |
| EV-01-02 | IV Online Runtime/TCF | Request End-to-End, TCF ON/OFF | `[FACT/ANALYSIS]` |
| EV-01-03 | V TX/Timeout/Thread/DB | Request/Worker, Transaction, Deadline | `[FACT/ANALYSIS]` |
| EV-01-04 | VI Message/Context/Error/Logging | Envelope, GUID, Context, Error | `[FACT/ANALYSIS]` |
| EV-01-05 | VII Security/JWT | Login/Token/JWKS, Critical GAP | `[FACT/ANALYSIS]` |
| EV-01-06 | VIII Infrastructure | GSLB/L4/WEB/WAS/DB Working Path, Capacity | `[WORKING BASELINE]` |
| EV-01-07 | IX DevOps/OM/Observability | Java/Spring/Gradle, Runtime Evidence | `[FACT/ANALYSIS]` |
| EV-01-08 | X Naming/Traceability | ServiceId, 13 Registry, Closed Loop | `[FACT/ANALYSIS]` |
| EV-01-09 | PDMG Source Deep Dive | Bottom-up Source Cross-check | `[FACT/ANALYSIS]` |
| EV-01-10 | Architecture Decision PASS Register | PASS/GAP/OPEN status | `[DECISION]` |
| EV-01-11 | 상호금융 정보계 발표자료/스크립트 | RDW/ADW, CDC/Kafka, AP HA 등 상위 전략 | `[NSIGHT REFERENCE]` |

---

# 2. Figure Plan

| FIG | 제목 | Level | 핵심 질문 |
|---|---|---:|---|
| FIG-01-01 | Chapter Question | L0 | 이 장이 무엇을 설명하는가 |
| FIG-01-02 | PDMG Master Architecture | L0 | PDMG 전체는 무엇인가 |
| FIG-01-03 | Five Module Map | L1/L2 | 모듈 책임은 무엇인가 |
| FIG-01-04 | Boundary Separation | L1/L2 | Process/Module/Data 경계는? |
| FIG-01-05 | Framework vs Business | L2/L3 | 누가 무엇을 책임지는가 |
| FIG-01-06 | Online Runtime Big Flow | L1~L4 | 거래 1건은 어떻게 실행되는가 |
| FIG-01-07 | Request vs Worker | L3/L4 | Thread 경계는 어디인가 |
| FIG-01-08 | Transaction Boundary | L4 | DB TX 범위는 어디인가 |
| FIG-01-09 | Message/Context | L2~L4 | Header/DTO/GUID는 어떻게 흐르는가 |
| FIG-01-10 | Security Overlay | L1~L4 | 인증/검증은 어디에 놓이는가 |
| FIG-01-11 | Naming/ServiceId Trace | L2~L5 | 거래 Identity는 Source로 어떻게 연결되는가 |
| FIG-01-12 | Physical Handoff | L1/L2 | 논리 Runtime은 물리에 어떻게 연결되는가 |
| FIG-01-13 | Operations/Evidence | L2~L5 | 운영에서 어떻게 증명하는가 |
| FIG-01-14 | Critical GAP Map | L0 | 현재 주요 위험은 무엇인가 |
| FIG-01-15 | PASS Summary | L0 | 현재 Architecture 상태는? |
| FIG-01-16 | Next Drill-down | L0/L1 | 다음 장으로 무엇을 넘기는가 |

---

# 3. 한눈에 보는 PDMG Architecture

## FIG-01-02. PDMG Master Architecture

```text
                         ┌─────────────────────────┐
                         │ User / Browser          │
                         └────────────┬────────────┘
                                      │ HTTP
          ┌───────────────────────────┼───────────────────────────┐
          │                           │                           │
          ▼                           ▼                           ▼
┌─────────────────┐        ┌─────────────────┐        ┌─────────────────┐
│ pdmg-ui         │        │ pdmg-jwt        │        │ pdmg-service    │
│ [AS-IS]         │        │ [AS-IS]         │        │ [AS-IS]        │
│                 │        │                 │        │                 │
│ UI / Static     │        │ Login / SSO     │        │ Business       │
│ Direct/Relay    │        │ Token / JWKS    │        │ Runtime        │
└────────┬────────┘        └────────┬────────┘        └────────┬────────┘
         │ HTTP                     │ Token/JWKS                │
         │                          │                           │
         └──────────────────────────┼───────────────────────────┘
                                    ▼
                     ┌─────────────────────────────┐
                     │ pdmg-service Runtime        │
                     │                             │
                     │ ┌─────────────────────────┐ │
                     │ │ pdmg-fw [AS-IS]        │ │
                     │ │ Filter / Context       │ │
                     │ │ Security / TCF         │ │
                     │ │ Timeout / Error / Log  │ │
                     │ └───────────┬─────────────┘ │
                     │             ▼               │
                     │ Handler → Facade → Service  │
                     │                  ↓          │
                     │               DAO/Mapper    │
                     └───────────────┬─────────────┘
                                     │ MyBatis / JDBC
                                     ▼
                            ┌─────────────────┐
                            │ RDW / DB        │
                            │ [DATA BOUNDARY] │
                            └─────────────────┘

                     ┌─────────────────────────────┐
                     │ pdmg-om                    │
                     │ [CURRENT DETAIL UNKNOWN]   │
                     └─────────────────────────────┘
```

### 핵심 해설

1. `[AS-IS]` PDMG의 강한 Source Evidence는 `pdmg-ui`, `pdmg-jwt`, `pdmg-fw`, `pdmg-service`에 존재한다.
2. `pdmg-om`은 Baseline 이름은 존재하지만 Current 구현범위는 Evidence 부족으로 `[UNKNOWN]`이다.
3. `pdmg-fw`는 `pdmg-service`가 HTTP로 호출하는 별도 업무서버가 아니라 같은 Runtime 안에 로드될 수 있는 Framework Module/Bean 계층이다.
4. Business Core는 대표적으로 `Handler → Facade → Service → DAO → Mapper`로 이어진다.
5. 실제 Production Host/JVM/WAR와 이 논리구조의 완전한 Deployment Mapping은 아직 별도 Evidence가 필요하다.

---

# 4. L1 — PDMG 5 Module View

## FIG-01-03. Five Module Map

```text
PDMG
│
├─ pdmg-ui
│    ├─ UI / Static Resource
│    ├─ Browser Entry
│    ├─ ServiceId 호출
│    └─ 일부 Relay Compatibility
│
├─ pdmg-jwt
│    ├─ Login
│    ├─ SSO 연계 발급
│    ├─ Access / Refresh Token
│    └─ JWKS
│
├─ pdmg-fw
│    ├─ DefaultFilter
│    ├─ ServiceContext
│    ├─ TCF
│    ├─ Timeout
│    ├─ Error / Response Support
│    └─ Logging Support
│
├─ pdmg-service
│    ├─ Online Entry
│    ├─ TransactionHandler
│    ├─ Facade
│    ├─ Service
│    ├─ DAO
│    └─ Mapper
│
└─ pdmg-om
     └─ [UNKNOWN]
```

### Module 상태

| Module | 현재 확인된 책임 | Current |
|---|---|---|
| pdmg-ui | UI/Static/Relay/Service 호출 | `[AS-IS]` |
| pdmg-jwt | Login/Token/JWKS/Refresh/SSO | `[AS-IS]` |
| pdmg-fw | Filter/Context/TCF/Timeout/MVC/Error | `[AS-IS]` |
| pdmg-service | Handler/Facade/Service/DAO/Mapper | `[AS-IS]` |
| pdmg-om | 상세 Source/Runtime 미확보 | `[UNKNOWN]` |

---

# 5. L1/L2 — Boundary Separation

## FIG-01-04. Process / Module / Data

```text
┌────────────────── PROCESS / HTTP BOUNDARY ──────────────────┐
│                                                             │
│ Browser ─────► pdmg-ui                                      │
│    │                                                        │
│    ├────────► pdmg-jwt                                      │
│    │                                                        │
│    └────────► pdmg-service                                  │
│                                                             │
└─────────────────────────────────────────────────────────────┘

                       │
                       ▼

┌──────────── pdmg-service Runtime / Spring Context ───────────┐
│                                                             │
│  pdmg-fw Beans                                              │
│  Filter / Context / TCF / Timeout / Error                   │
│                  │                                          │
│                  ▼                                          │
│  Handler / Facade / Service / DAO                           │
│                                                             │
└───────────────────────────┬─────────────────────────────────┘
                            │ JDBC
                            ▼
┌──────────────────── DATA BOUNDARY ──────────────────────────┐
│ RDW / DB                                                    │
└─────────────────────────────────────────────────────────────┘
```

### 경계식

```text
Module Boundary
≠ Process Boundary
≠ Spring ApplicationContext Boundary
≠ Data Boundary
```

이 구분은 이후 Physical Architecture에서 `Server/VM/JVM/WAR`까지 확장된다.

---

# 6. L2/L3 — Framework vs Business

## FIG-01-05. Responsibility Split

```text
┌──────────────── FRAMEWORK ────────────────┐
│ "어떻게 안전하게 실행할 것인가?"        │
│                                          │
│ Filter                                   │
│ Context                                  │
│ Security Integration                     │
│ TCF / Dispatcher Support                 │
│ Timeout                                  │
│ Transaction Coordination                 │
│ Error / Response Support                 │
│ Logging                                  │
└───────────────────┬──────────────────────┘
                    │ controls
                    ▼
┌──────────────── BUSINESS ────────────────┐
│ "무슨 업무를 수행할 것인가?"            │
│                                         │
│ Handler                                 │
│ Facade                                  │
│ Service                                 │
│ DAO                                     │
│ Mapper / SQL                            │
└─────────────────────────────────────────┘
```

### Architecture Rule

Framework가 소유하면 안 되는 것:

```text
고객/상품 업무판단
업무 SQL
업무별 Use Case
```

Business가 소유하면 안 되는 것:

```text
ThreadLocal lifecycle
공통 Timeout Executor
전역 Error Contract
공통 Dispatcher
JWT Signature Infrastructure
```

---

# 7. L1~L4 — Online Runtime Big Flow

## FIG-01-06. End-to-End Runtime

```text
Browser
  │
  │ POST /{serviceId}
  │ { hdr_nhnis, dto }
  ▼
pdmg-ui
  │
  ▼
════════════════════ HTTP Request Thread ════════════════════
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
  ├── Future.get(timeout) ───────────────┐
  │                                      │
  │ submit                               │
  ▼                                      │
════════════════════ Worker Thread ══════════════════════════
WorkerContext.install                    │
  ↓                                      │
TransactionTemplate BEGIN                │
  ↓                                      │
TransactionDispatcher                    │
  ↓                                      │
TransactionHandler                       │
  ↓                                      │
Facade                                   │
  ↓                                      │
BizPrePostAspect                         │
  ↓                                      │
Service                                  │
  ↓                                      │
DAO → Mapper XML → JDBC → DB             │
  ↓                                      │
Deadline Check                           │
  ├─ OK → COMMIT                         │
  └─ EXCEEDED → ROLLBACK                 │
  ↓                                      │
WorkerContext.clear                      │
════════════════════ 결과 반환 ═════════════════════════════
                                         │
Request Thread ◄─────────────────────────┘
  ↓
Response / Exception Mapping
  ↓
ResponseBodyAdvice
  ↓
afterCompletion
  ↓
DefaultFilter.finally
  ↓
HTTP Response
```

### 현재 핵심 사실

```text
HTTP Request Lifetime
≠ DB Transaction Lifetime
```

---

# 8. L3/L4 — Request Thread vs Worker Thread

## FIG-01-07. Thread Boundary

```text
Request Thread
├─ Filter
├─ Security
├─ MVC
├─ Interceptor
├─ Controller
├─ TcfFacade
├─ Worker submit
├─ Future.get(timeout)
├─ Response
└─ Cleanup

            ≠

pdmg-online-N Worker
├─ Context install
├─ Transaction BEGIN
├─ Dispatcher
├─ Handler
├─ Facade
├─ Service
├─ DAO / SQL
├─ Deadline
├─ COMMIT / ROLLBACK
└─ Context clear
```

Current Snapshot:

```text
pool-size       = 20
queue-capacity  = 100
timeout         = 5000ms

[AS-IS SNAPSHOT]
```

이 값은 PDMG 현재 설정 Snapshot이며 NSIGHT Target Capacity/SLA로 승격하지 않는다.

---

# 9. L4 — Transaction Boundary

## FIG-01-08. Worker DB Transaction

```text
Worker Thread
   ↓
TransactionTemplate BEGIN
   ↓
┌───────────────────────────────────────┐
│ TransactionDispatcher                 │
│   ↓                                   │
│ TransactionHandler                    │
│   ↓                                   │
│ Facade @Transactional(REQUIRED)       │
│   ↓                                   │
│ BizPrePostAspect                      │
│   ↓                                   │
│ Service                               │
│   ↓                                   │
│ DAO / Mapper / JDBC / DB              │
│   ↓                                   │
│ Deadline Check                        │
│   ├─ OK → COMMIT                      │
│   └─ Timeout → rollbackOnly→ROLLBACK  │
└───────────────────────────────────────┘
```

### 반드시 구분

```text
Facade @Transactional
≠ 항상 Physical TX BEGIN 지점
```

TCF ON + Timeout ON에서는 외부 `TransactionTemplate`이 먼저 Transaction을 열 수 있다.

---

# 10. L2~L4 — Message / Context / Error

## FIG-01-09. Standard Message and Context

```text
REQUEST
┌────────────────────────┐
│ hdr_nhnis              │
│  └─ sys_comm           │
│      ├─ GUID           │
│      ├─ ServiceId      │
│      ├─ Source Context │
│      └─ User Context   │
├────────────────────────┤
│ dto                    │
└───────────┬────────────┘
            ▼
      ServiceContext
            │
            ├─ MDC / GUID
            ├─ Request Context
            └─ Header State
            │
            ▼
          Business
            │
      ┌─────┴─────┐
      ▼           ▼
SUCCESS         KNOWN ERROR
{hdr,dto}       {hdr,result}
```

### Current GAP

Filter/Security 단계에서 `sendError`로 종료되는 오류는 MVC Advice를 통과하지 않을 수 있어 표준 Error Envelope가 보장되지 않는 경로가 존재한다.

---

# 11. L1~L4 — Security Overlay

## FIG-01-10. Security Overlay

```text
User
  ↓
Login / SSO
  ↓
pdmg-jwt
  ├─ Authentication
  ├─ Access Token
  ├─ Refresh Token
  └─ JWKS
  ↓
Bearer Token
  ↓
pdmg-service / pdmg-fw
  ↓
Token Verification
  ↓
Trusted Principal
  ↓
Business User Context
  ↓
Authorization
  ↓
ServiceId / Business Execution
```

### Current Critical GAP

```text
Issuer
pdmg-jwt
RS256
   ↓
Token
   ↓
Verifier Path
pdmg-fw
HMAC jwt.secret

[CRITICAL GAP]
```

### 추가 Security GAP

```text
JWT subject/ssoId
       ↓
Trusted Principal
       ↓
hdr_nhnis user/operator context

Current 자동 정합
= 추가 검증 필요
```

---

# 12. L2~L5 — Naming / ServiceId / Source Trace

## FIG-01-11. Service Identity Backbone

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

### Current Facts

```text
Program ID
= 9 chars
2 + 2 + 1 + 4

ServiceId
= 11 chars
2 + 2 + 1 + 4 + 1 + 1
```

Current Handler Registry 분석:

```text
13 ServiceIds
```

### Current Drift Candidate

UI Transaction Catalog와 Backend Handler Registry가 완전한 하나의 SSOT로 정합되어 있는지는 자동검증이 필요하다.

---

# 13. L1/L2 — Physical Handoff

## FIG-01-12. Logical Runtime → Physical Working Path

```text
User
 ↓
GSLB
 ↓
L4
 ↓
WEB
Apache Working Standard
 ↓
WAS / Tomcat JVM
 ↓
Business WAR
 ↓
Hikari / MyBatis
 ↓
RDW / ADW / DB
```

### 상태 구분

```text
Architecture Working Path
= [BASELINE]

PDMG Artifact → Actual Production Host/JVM/WAR Mapping
= [GAP/OPEN]
```

`pdmg-fw`는 이 그림에서 별도 Server Box로 그리지 않는다.

---

# 14. L2~L5 — Build / Operations / Evidence

## FIG-01-13. Source to Runtime Evidence

```text
Source
  ↓
Gradle Multi-project
  ↓
Build
  ↓
Artifact
  ↓
Deployment Manifest
  ↓
Runtime
  ↓
GUID / ServiceId
  ↓
Metric / Log / Trace
  ↓
Drift / GAP
  ↓
ADR / New Baseline
```

### Current confirmed build context

```text
Java 21
Spring Boot 3.5.14
Gradle Multi-project
```

### PDMG OM

```text
pdmg-om
Current detailed implementation
= [UNKNOWN]
```

따라서 OM Dashboard/Control 기능을 Current AS-IS로 창작하지 않는다.

---

# 15. NSIGHT Strategic Context Overlay

## FIG-01-14. PDMG와 상위 정보계 Architecture의 관계

```text
NSIGHT Strategic Context
│
├─ Marketing Platform
├─ RDW / ADW Separation
├─ CDC
├─ Event / Kafka
├─ ETL
├─ Standard Interface
├─ AP Scale-out / HA
└─ Observability
        │
        │ Alignment
        ▼
PDMG Current Architecture
│
├─ UI / JWT
├─ Framework / TCF
├─ Service Runtime
├─ DB Access
└─ Runtime Evidence
```

상위 발표자료에서 강조된 `RDW/ADW 분리`, `CDC/Kafka`, `AP 고가용성`, `표준화/통합개발환경`은 PDMG를 평가하는 전략적 방향이다.

PDMG Current에 직접 존재한다고 확인되지 않은 Event/CDC/ETL 기능은 **PDMG AS-IS로 표현하지 않는다.**

---

# 16. Normal Architecture Pattern

## FIG-01-15. Normal Pattern

```text
HTTP
 ↓
Framework Entry
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
```

정상 Boundary:

```text
Framework
→ execution control

Business
→ business work

Data
→ controlled access

Security
→ trusted identity

Operations
→ evidence
```

---

# 17. Forbidden / Risk Pattern

## FIG-01-16. Forbidden Patterns

```text
[1]
Handler ─────► DAO / Mapper
        X

[2]
Controller ──► DAO
        X

[3]
pdmg-fw
  ↓ HTTP
pdmg-service
라고 그림
        X

[4]
HTTP 504
=
DB Cancel Complete
라고 해석
        X

[5]
RS256 발급
+
HMAC 검증
을 정상구조로 승인
        X

[6]
PDMG Current 미확인 Event/CDC/ETL
=
AS-IS
        X
```

---

# 18. Current Critical GAP Map

## FIG-01-17. GAP Heatmap

```text
PDMG
│
├─ Application
│    └─ TCF OFF 일부 Controller → Service 직접
│       [GAP]
│
├─ Runtime
│    └─ Mutable ServiceContext Worker 공유
│       [RISK/GAP]
│
├─ Timeout
│    └─ HTTP timeout ≠ JDBC cancel guarantee
│       [RISK]
│
├─ Error
│    └─ Filter/Security Early Error Envelope
│       [GAP]
│
├─ Security
│    ├─ RS256 Issuer vs HMAC Verifier
│    │  [CRITICAL GAP]
│    ├─ Key lifecycle / multi-instance
│    │  [GAP]
│    └─ Identity Binding
│       [GAP]
│
├─ Traceability
│    └─ UI Catalog ↔ Backend Registry automation
│       [GAP]
│
├─ Deployment
│    └─ Artifact → Host/JVM/WAR mapping
│       [OPEN/GAP]
│
└─ OM
     └─ Current Source/Runtime detail
        [UNKNOWN]
```

---

# 19. Architecture Decision Mapping — Executive Level

| Decision Area | Architecture 방향 | PDMG Current | Executive 판정 |
|---|---|---|---|
| Baseline/SSOT | Model/Registry/Evidence | PARTIAL | CONDITIONAL PASS |
| TCF Business Core | ON/OFF 공통 Facade | OFF 일부 우회 | GAP |
| Standard Message | hdr_nhnis + dto/result | 확인 | PASS |
| Timeout | 계층형 Budget | Worker 5000ms만 Current | CONDITIONAL |
| Retry/Idempotency | 목적/멱등성 기반 | 전수 Evidence 부족 | OPEN |
| JWT | RS256 + JWKS | RS256/HMAC 충돌 | CRITICAL GAP |
| Identity Binding | Trusted Principal 기반 | 추가 검증 | GAP |
| RDW/ADW | 상위 Target 역할 분리 | PDMG 직접 Scope 제한 | REFERENCE |
| WAS Topology | WEB→WAS→DB | Host Mapping 미완료 | CONDITIONAL |
| Observability | Metric+Log+Trace | 일부 GUID/MDC/ImageLog | PARTIAL |
| Runtime Evidence | Deployment→GUID→Evidence | 자동화 미완료 | GAP |

---

# 20. Architecture PASS / PDMG Conformance

## FIG-01-18. Executive PASS

```text
PDMG Architecture Definition
        ↓
[CONDITIONAL PASS]

이유:
Core Module / Runtime / Thread / TX / Message /
Security / Naming 구조는 설명 가능

BUT

Current Implementation / Evidence
        ↓
[PARTIAL + GAP]

Critical:
JWT
Identity
TCF OFF
Deployment Mapping
OM
Runtime Evidence Automation
```

### Summary

```text
Architecture Definition     : CONDITIONAL PASS
Current PDMG Conformance    : PARTIAL / GAP
Runtime Evidence Coverage   : MEDIUM
Critical GAP Count          : 1+
OPEN/UNKNOWN Major Areas    : Deployment / OM / 일부 Interface
```

---

# 21. Top 10 PASS 전환조건

```text
1. JWT Issuer/Verifier Algorithm 정합
2. Versioned Key / JWKS / Multi-instance Key lifecycle
3. JWT Principal → Business User Context Identity Binding
4. TCF OFF Controller를 공통 Facade Business Core로 정렬
5. Worker Context를 Immutable Snapshot 구조로 검토/정렬
6. Filter/Security Early Error의 Standard Error Contract
7. Query/TX/Worker/Server/Client Timeout Budget 확정
8. UI Catalog ↔ Backend ServiceId Registry 자동 비교
9. Artifact → DeploymentId → Host/JVM/WAR 실제 Mapping
10. pdmg-om Current Source/Runtime Evidence 확보 또는 Scope 제외결정
```

---

# 22. 이 장에서 확정하지 않는 것

## FIG-01-19. OPEN Boundary

```text
Exact Production Hostname
Exact Port
Exact Tomcat Version
Exact Oracle Version
Final Hikari Size
Final Tomcat maxThreads
Final JVM Heap
Final RTO/RPO
Final Event/CDC/ETL Product Mapping in PDMG
pdmg-om Current Function
```

근거가 없는 값은 이후 장에서도 `[OPEN]`으로 유지한다.

---

# 23. Executive Architecture Story

## FIG-01-20. PDMG Story

```text
PDMG는
단순한 5개 Module 목록이 아니다.
       ↓
HTTP Process Boundary가 있고
       ↓
Business Runtime 안에
Framework Control Plane 성격의 Bean/Mechanism이 있고
       ↓
ServiceId로 Handler를 선택하고
       ↓
Worker Thread에서 Transaction을 열어
       ↓
Facade / Service / DAO / Mapper로 DB 작업을 수행하고
       ↓
GUID / Context / Error / Log로 거래를 추적하며
       ↓
JWT와 Security Boundary를 통과하고
       ↓
Build / Deploy / Runtime Evidence로
실제 Architecture를 증명해야 하는
실행형 Reference Architecture다.
```

---

# 24. Next Chapter Handoff

## FIG-01-21. 01 → 02

```text
01 Executive
"PDMG 전체는 이렇게 생겼다."
          ↓
02 System Context & Boundary
"그렇다면 정확히 누가 PDMG를 호출하고,
PDMG 안팎의 Process / Data / Security 경계는 어디인가?"
```

02장에서는 아래 경계를 더 확대한다.

```text
Browser / User
PDMG Process
UI / JWT / Service
External Systems
DB
Security Trust Boundary
Observability Boundary
Forbidden Boundary
```
