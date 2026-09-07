# PDMG 전체 아키텍처 정의서
# 00. PDMG ARCHITECTURE MASTER INDEX / DEFINITION GUIDE / EVIDENCE
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First / Runtime-Verifiable

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-00-MASTER-INDEX`  
> 상태: `[WORKING BASELINE-2026-09-01]`  
> 작성 원칙: PDMG AS-IS를 먼저 정의하고, NSIGHT는 Alignment/PASS/GAP 판단기준으로 사용한다.

---

# 0. Chapter Purpose

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
      ↓
PDMG Architecture Baseline
```

이 장의 목적은 PDMG 전체 아키텍처 정의서를 작성하고 읽는 **공통 규칙, Evidence 우선순위, 상태 표기, Top-down Level, 문서간 연결관계, PASS 판정방식**을 고정하는 것이다.

이 정의서에서 PDMG는 단순한 Spring Boot 애플리케이션 예제가 아니다.

```text
PDMG
=
Module / Build Structure
+
Process / JVM / Spring Context
+
Application Layer / Component
+
TCF / Runtime Mechanism
+
Transaction / Timeout / Thread
+
Security / JWT
+
Message / Context / Error / Logging
+
Data Access
+
Deployment / Operations
+
ServiceId / Traceability
```

---

# 1. Evidence Register

```text
Source / Config
      ↓
Runtime / Deployment
      ↓
PDMG Architecture Analysis
      ↓
Architecture Decision / PASS
      ↓
NSIGHT Official Target
      ↓
Presentation / Historical
```

| Evidence ID | 자료 | 사용 목적 | 우선순위 | 상태 |
|---|---|---|---:|---|
| EV-00-01 | `PDMG_전체_아키텍처_정의서_작성_마스터프롬프트_VISUAL_FIRST_TOPDOWN_DRILLDOWN.md` | 전체 작성방식/목차/품질 Gate | 0 | `[DECISION]` |
| EV-00-02 | `NSIGHT_PDMG_아키텍처_정의서_III_PDMG_Module_Application_Architecture.md` | Module/Process/Spring Context/Application Layer | 3 | `[FACT/ANALYSIS]` |
| EV-00-03 | `NSIGHT_PDMG_아키텍처_정의서_IV_PDMG_Online_Runtime_TCF_Flow.md` | TCF ON/OFF, Online Runtime | 3 | `[FACT/ANALYSIS]` |
| EV-00-04 | `NSIGHT_PDMG_아키텍처_정의서_V_Transaction_Timeout_Thread_DB_Architecture.md` | Thread/TX/Timeout/DB | 3 | `[FACT/ANALYSIS]` |
| EV-00-05 | `NSIGHT_PDMG_아키텍처_정의서_VI_Standard_Message_Context_Error_Logging.md` | Message/Context/Error/Logging | 3 | `[FACT/ANALYSIS]` |
| EV-00-06 | `NSIGHT_PDMG_아키텍처_정의서_VII_Security_SSO_JWT_Session.md` | JWT/SSO/Session/Security | 3 | `[FACT/ANALYSIS]` |
| EV-00-07 | `NSIGHT_PDMG_아키텍처_정의서_VIII_Infrastructure_WAS_Capacity_HA_DR.md` | Physical/Capacity/HA/DR | 3 | `[FACT/ANALYSIS]` |
| EV-00-08 | `NSIGHT_PDMG_아키텍처_정의서_IX_DevOps_OM_Observability.md` | Build/Deploy/OM/Observability | 3 | `[FACT/ANALYSIS]` |
| EV-00-09 | `NSIGHT_PDMG_아키텍처_정의서_X_Naming_ServiceId_Traceability_Closed_Loop.md` | Naming/ServiceId/Traceability/Gates | 3 | `[FACT/ANALYSIS]` |
| EV-00-10 | `NSIGHT_PDMG_아키텍처_정의서_08_PDMG_SOURCE_RUNTIME_REFERENCE_DEEP_DIVE_VISUAL_FIRST_상세본_보완개정본_v2.md` | Source-to-Runtime Bottom-up Evidence | 3 | `[FACT/ANALYSIS]` |
| EV-00-11 | `NSIGHT_PDMG_아키텍처_의사결정_레지스터_PASS평가_v2.xlsx` | Architecture Decision/PASS 상태 | 4 | `[DECISION]` |
| EV-00-12 | `NSIGHT_PDMG_하드웨어_소프트웨어_매트릭스.xlsx` | HW/SW/Capacity 상태 | 4 | `[WORKING BASELINE]` |
| EV-00-13 | `NSIGHT_PDMG_어플리케이션_코드_정의서_VISUAL_FIRST.md` | Application Code/Mapping | 4 | `[WORKING BASELINE]` |
| EV-00-14 | `상호금융_정보계_아키텍처_발표스크립트_15분_CHATGPT (최종본)` 계열 | 상위 NSIGHT Story/Strategy | 6 | `[NSIGHT REFERENCE]` |
| EV-00-15 | NSIGHT Application/Technical/Infrastructure/Interface/Data/Naming 별첨 | Target Alignment | 5 | `[NSIGHT REFERENCE]` |

---

# 2. Evidence Priority

## FIG-00-01. Evidence Priority

```text
┌──────────────────────────────────────────────┐
│ 1. Source / Config                          │
│    Class / Gradle / YAML / Mapper / Config  │
└──────────────────────┬───────────────────────┘
                       ▼
┌──────────────────────────────────────────────┐
│ 2. Runtime / Deployment Evidence            │
│    Trace / Log / Metric / Artifact / Host   │
└──────────────────────┬───────────────────────┘
                       ▼
┌──────────────────────────────────────────────┐
│ 3. PDMG Current Architecture Analysis       │
└──────────────────────┬───────────────────────┘
                       ▼
┌──────────────────────────────────────────────┐
│ 4. Decision / PASS Register                 │
└──────────────────────┬───────────────────────┘
                       ▼
┌──────────────────────────────────────────────┐
│ 5. NSIGHT Target / Official Architecture    │
└──────────────────────┬───────────────────────┘
                       ▼
┌──────────────────────────────────────────────┐
│ 6. Presentation / Historical Document       │
└──────────────────────────────────────────────┘
```

### Architecture Rule

```text
Source says A
Target says B
      ↓
PDMG AS-IS = A
Target Alignment = B
Difference = GAP
```

Target을 이용해 Current를 덮어쓰지 않는다.

---

# 3. PDMG Main Subject Rule

## FIG-00-02. 이번 문서의 관점

```text
                 ┌────────────────────────┐
                 │ PDMG Current           │
                 │ Main Subject           │
                 └───────────┬────────────┘
                             │
           ┌─────────────────┼──────────────────┐
           ▼                 ▼                  ▼
      Source/Config       Runtime            Architecture
      [AS-IS]             [EVIDENCE]         [CURRENT]
           │                 │                  │
           └─────────────────┼──────────────────┘
                             ▼
                      PASS / GAP 판단
                             │
                             ▼
                 ┌────────────────────────┐
                 │ NSIGHT Target          │
                 │ Alignment Reference    │
                 └────────────────────────┘
```

### 작성 순서

```text
1. PDMG Architecture를 먼저 설명
2. Current Source/Runtime으로 증명
3. Architecture Rule 도출
4. PDMG 구현 정합을 평가
5. NSIGHT Target과 비교
6. GAP/ADR/PASS 전환조건 정의
```

---

# 4. 상태 Tag

## FIG-00-03. State Model

```text
Evidence
  ↓
FACT / CONFIRMED / AS-IS
  ↓
Architecture Definition
  ↓
PASS / CONDITIONAL PASS / OPEN / FAIL
  ↓
Implementation
  ↓
PASS / PARTIAL / GAP / CONFLICT / UNKNOWN
```

| 태그 | 의미 |
|---|---|
| `[FACT]` | Source/Config/Runtime/공식자료에서 직접 확인 |
| `[CONFIRMED]` | 복수 Evidence가 같은 내용을 지지 |
| `[AS-IS]` | 현재 PDMG 구현 |
| `[BASELINE-YYYY-MM-DD]` | 특정 시점 기준 |
| `[DECISION]` | 의사결정으로 선택 |
| `[PASS]` | 정의 Architecture에 정합 |
| `[CONDITIONAL PASS]` | 방향은 정합하나 추가 Evidence/수치/결정 필요 |
| `[PARTIAL]` | 일부 구현/일부 Evidence |
| `[GAP]` | Target/정의와 Current 차이 |
| `[CONFLICT]` | 근거간 충돌 |
| `[RISK]` | 장애/보안/운영 위험 |
| `[OPEN]` | 결정 필요 |
| `[UNKNOWN]` | Evidence 부족 |
| `[PROPOSED]` | 승인 전 제안 |

---

# 5. Top-down Drill-down Level

## FIG-00-04. L0 → L5

```text
L0
PDMG Architecture Landscape
"전체가 무엇인가?"
          ↓
L1
System Context / Boundary
"누가 호출하고 무엇을 호출하는가?"
          ↓
L2
Module / Logical Node / Container
"큰 책임단위는 무엇인가?"
          ↓
L3
Component / Layer
"Handler/Facade/Service/DAO/Framework는?"
          ↓
L4
Runtime / Sequence / Data Flow
"거래 한 건은 어떻게 실행되는가?"
          ↓
L5
Source / Config / Failure / Evidence
"어떤 코드와 설정으로 증명하는가?"
```

### 원칙

```text
L5부터 시작하지 않는다.
```

즉 `DefaultFilter.java`, `OnlineTimeoutExecutor.java` 등의 Class를 먼저 나열하지 않고, 그것이 속한 상위 Architecture를 먼저 보여준다.

---

# 6. Architecture Boundary Taxonomy

## FIG-00-05. Boundary Taxonomy

```text
Repository Boundary
      ↓
Build Module Boundary
      ↓
Process Boundary
      ↓
JVM Boundary
      ↓
Spring ApplicationContext Boundary
      ↓
Application Layer / Component Boundary
      ↓
Transaction Boundary
      ↓
Data Boundary
      ↓
Security Trust Boundary
      ↓
Deployment / Center Boundary
```

### 절대 동일시하지 않는다

```text
Build Module
≠ Process

Process
≠ JVM

JVM
≠ WAR

WAR
≠ Application

Module
≠ Logical Node

Spring Bean
≠ Remote Service
```

---

# 7. PDMG Known Baseline

## FIG-00-06. Five Module Baseline

```text
┌────────────────────────── PDMG ──────────────────────────┐
│                                                         │
│  pdmg-ui        [AS-IS] UI / Static / Relay            │
│                                                         │
│  pdmg-jwt       [AS-IS] Login / Token / JWKS / SSO     │
│                                                         │
│  pdmg-service   [AS-IS] Business Runtime               │
│       │                                                 │
│       └── pdmg-fw [AS-IS] Framework Module / Beans      │
│                                                         │
│  pdmg-om        [UNKNOWN] 상세 Current Evidence 부족    │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

### 중요한 해석

```text
pdmg-fw
= 별도 Build Module

BUT

pdmg-fw
≠ 별도 Remote Business Server
```

Current 분석에서는 `pdmg-service`의 Spring ApplicationContext에 Framework Bean이 함께 동작할 수 있다.

---

# 8. Current Process / Spring / Data Boundary

## FIG-00-07. Boundary Separation

```text
[PROCESS / HTTP]

Browser
  ├──── HTTP ────► pdmg-ui
  ├──── HTTP ────► pdmg-jwt
  └──── HTTP ────► pdmg-service


[MODULE / SPRING]

pdmg-service Runtime
   ├─ Business Components
   └─ pdmg-fw
      Filter / Context / TCF / Timeout / Error


[DATA]

pdmg-service
   └─ DAO / MyBatis / JDBC
          ↓
        RDW / DB
```

### 상태

- `pdmg-ui`, `pdmg-jwt`, `pdmg-service`, `pdmg-fw`: `[AS-IS]`
- `pdmg-om` Runtime/Process 상세: `[UNKNOWN]`
- 실제 Production Host/JVM/WAR Mapping: `[GAP/OPEN]`

---

# 9. Current Business Architecture Backbone

## FIG-00-08. Business Layer

```text
Entry Adapter
   ↓
TransactionHandler / Controller
   ↓
Facade
   ↓
Service
   ↓
DAO
   ↓
Mapper XML / SqlId
   ↓
SQL
   ↓
DB
```

### Current Rule

```text
Handler → Facade         normal
Facade  → Service        normal
Service → DAO            normal
DAO     → Mapper         normal

Handler → DAO            MUST NOT
Controller → DAO         MUST NOT
Handler → Mapper         MUST NOT
```

### Rule Layer

```text
General Rule Layer
= Current Source에서 전수 확인되지 않음

→ [OPEN / PROPOSED]
```

---

# 10. Current Online Runtime Backbone

## FIG-00-09. Runtime Backbone

```text
HTTP Request
   ↓
DefaultFilter
   ↓
Spring SecurityFilterChain
   ↓
DispatcherServlet
   ↓
ServicePreventionInterceptor
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
Service
   ↓
DAO / Mapper
   ↓
DB
   ↓
Response / Error
```

이 흐름은 **TCF ON + Timeout ON Current Snapshot**을 대표한다.

---

# 11. Current Thread / Transaction Baseline

## FIG-00-10. Request vs Worker

```text
════════ Request Thread ════════
Filter
Security
MVC
Controller
TcfFacade
Future.get(timeout)
Response
Cleanup
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
DAO / SQL
Deadline Check
COMMIT / ROLLBACK
Context Clear
```

### Snapshot

```text
timeout        = 5000ms
worker pool    = 20
queue capacity = 100

[AS-IS SNAPSHOT]
```

### 반드시 분리

```text
HTTP Timeout
≠ Worker Completion
≠ JDBC Cancel
≠ DB Rollback Complete
```

---

# 12. Current Message / Trace Baseline

## FIG-00-11. Message / Context

```text
Request
{
  hdr_nhnis,
  dto
}
       ↓
ServiceContext
       ├─ Header / GUID
       ├─ Request Context
       └─ Runtime Context
       ↓
Business
       ↓
Success
{
  hdr_nhnis,
  dto
}

Known Error
{
  hdr_nhnis,
  result
}
```

핵심 추적키:

```text
GUID / std_gbl_id
+
ServiceId
```

---

# 13. Current Security Baseline / Critical Gap

## FIG-00-12. Security Gap

```text
pdmg-jwt
  │
  │ RS256 Issue
  ▼
Access Token
  │
  │ Business Request
  ▼
pdmg-fw
  │
  │ HMAC jwt.secret Verify Path
  ▼
[CRITICAL GAP]
```

또한 RSA Key lifecycle에서 Runtime 재기동/다중 Instance/DR 정합성 검증이 필요하다.

---

# 14. Current Naming / Traceability Baseline

## FIG-00-13. Naming Backbone

```text
Business Axis
MG / CO / A
    ↓
Program
mgcoa9001
    ↓
ServiceId
mgcoa9001S0
    ↓
Package
nhnis.mg.co.a
    ↓
Handler / Facade / Service / DAO
    ↓
Mapper
rdw.mg.co.a
    ↓
SqlId / SQL / Table
```

Current Handler Registry:

```text
13 ServiceIds
[AS-IS]
```

UI Transaction Catalog와 Backend Registry의 전수 정합은 별도 검증이 필요하다.

---

# 15. NSIGHT Strategic Context의 사용범위

## FIG-00-14. PDMG vs NSIGHT Context

```text
PDMG Current
Source / Runtime
      │
      │ 평가
      ▼
NSIGHT Target Context
├─ RDW / ADW 역할분리
├─ CDC / Event / ETL / File
├─ AP Scale-out
├─ HA / DR
├─ Standard Interface
└─ Observability
```

NSIGHT 발표자료의 `RDW/ADW 분리`, `CDC/Kafka`, `AP Active-Active`, `표준 인터페이스`는 **PDMG Current Fact를 만들기 위한 자료가 아니라 PDMG가 향후 정합해야 할 상위 Architecture Context**로 사용한다.

---

# 16. Architecture PASS vs Implementation Conformance

## FIG-00-15. Dual PASS

```text
Architecture Definition
          ↓
PASS / CONDITIONAL / OPEN / FAIL

          ≠

PDMG Current Implementation
          ↓
PASS / PARTIAL / GAP / CONFLICT / UNKNOWN
```

예:

```text
Target JWT Verification
RS256 + JWKS
      ↓
Architecture PASS

Current Source
RS256 Issue vs HMAC Verify
      ↓
PDMG GAP
```

---

# 17. Architecture Decision 연계방식

## FIG-00-16. Decision Closure

```text
GAP / OPEN
   ↓
Decision Task
   ↓
주안 / 대안
   ↓
장점 / 단점
   ↓
Evidence / PoC / Test
   ↓
ADR
   ↓
Architecture Model / Rule
   ↓
Source / Runtime
```

각 장은 해당 Decision Task를 본문에 포함한다.

---

# 18. Document Architecture

## FIG-00-17. 전체 00~18 Journey

```text
00 GUIDE / EVIDENCE
        ↓
01 EXECUTIVE OVERVIEW
        ↓
02 SYSTEM CONTEXT
        ↓
03 APPLICATION / MODULE
        ↓
04 LOGICAL TECHNICAL
        ↓
05 PHYSICAL / INFRA
        ↓
06 INTERFACE
        ↓
07 DATA
        ↓
08 FRAMEWORK / MECHANISM
        ↓
09 ONLINE RUNTIME
        ↓
10 TX / TIMEOUT / THREAD / DB
        ↓
11 SECURITY
        ↓
12 MESSAGE / CONTEXT / ERROR / LOG
        ↓
13 EVENT / CDC / ETL / BATCH / FILE / CACHE
        ↓
14 DEVOPS / OM / OBSERVABILITY
        ↓
15 NAMING / CODE / DEV STANDARD
        ↓
16 CAPACITY / PERFORMANCE / HA / DR
        ↓
17 TRACE / PASS / GAP / ADR
        ↓
18 INTEGRATED BASELINE
```

---

# 19. Final Table of Contents

| 장 | 제목 | Top-down 핵심 |
|---:|---|---|
| 00 | PDMG Architecture Definition Guide / Evidence | 작성규칙과 Evidence |
| 01 | PDMG Executive Architecture Overview | 전체 구조 한 장 |
| 02 | PDMG System Context & Boundary | 외부/내부 경계 |
| 03 | PDMG Application / Module Architecture | Module/Process/Context/Layer |
| 04 | PDMG Logical Technical Architecture | 기술책임/Logical Node |
| 05 | PDMG Physical / Infrastructure Architecture | Center/WEB/WAS/JVM/WAR/DB |
| 06 | PDMG Interface Architecture | API/Event/CDC/ETL/File |
| 07 | PDMG Data Architecture | DAO/Mapper/SQL/Data Boundary |
| 08 | PDMG Framework / Mechanism Architecture | Filter/Context/TCF/Error |
| 09 | PDMG Online Runtime Architecture | 거래 1건 End-to-End |
| 10 | Transaction / Timeout / Thread / DB | Worker/TX/JDBC/Deadline |
| 11 | Security / SSO / JWT / Session | Trust/Token/State |
| 12 | Message / Context / Error / Logging | 전문/Context/운영증적 |
| 13 | Event / CDC / ETL / Batch / File / Cache | 비온라인 Runtime |
| 14 | DevOps / Deployment / OM / Observability | Delivery/Control/Evidence |
| 15 | Naming / Application Code / Development Standard | 식별/Source 규칙 |
| 16 | Capacity / Performance / HA / DR | 부하/장애/복구 |
| 17 | Traceability / Conformance / PASS / GAP / ADR | 검증 Closed Loop |
| 18 | Integrated PDMG Architecture Baseline | 최종 통합 Baseline |

---

# 20. 공통 Chapter Template

## FIG-00-18. Chapter Internal Flow

```text
Chapter Purpose
   ↓
Evidence Register
   ↓
Figure Plan
   ↓
한눈에 보는 Architecture
   ↓
L0/L1
   ↓
L2
   ↓
L3
   ↓
L4 Runtime
   ↓
L5 Evidence
   ↓
Boundary / Rule
   ↓
Failure / Security / NFR
   ↓
PASS / GAP / ADR
   ↓
Verification / Handoff
```

모든 주요 번호절에 TEXT 그림을 배치한다.

---

# 21. Quality Gate

## FIG-00-19. Document Quality Gate

```text
TEXT Diagram Missing?
   ├─ YES → FAIL
   └─ NO
       ↓
Evidence 없는 핵심 주장?
   ├─ YES → OPEN/UNKNOWN으로 수정
   └─ NO
       ↓
AS-IS / Target 혼재?
   ├─ YES → FAIL
   └─ NO
       ↓
Boundary 혼동?
   ├─ YES → FAIL
   └─ NO
       ↓
PASS / Implementation 분리?
   ├─ NO → FAIL
   └─ YES
       ↓
Chapter PASS
```

---

# 22. Current Working Baseline Summary

## FIG-00-20. 현재 시작점

```text
PDMG Source Evidence
      ↓
Modules
UI / JWT / FW / Service / OM?
      ↓
Online Runtime
Filter → Security → MVC → TCF → Worker → Business → DB
      ↓
Cross-cutting
Message / Context / Error / Logging / JWT
      ↓
Delivery / Infra
Build / Deployment / HA / DR / Observability
      ↓
Traceability
Program → ServiceId → Source → SQL → Runtime
```

현재 정의서 작성은 이 시작점에서 **PDMG를 위에서 아래로 다시 재구성**한다.

---

# 23. 00장 PASS Summary

```text
Architecture Definition   : PASS
PDMG Implementation       : N/A — Guide Chapter
Runtime Evidence Coverage : N/A
Critical GAP Count        : 0
OPEN Decision Count       : 0
```

| 영역 | 판정 | 설명 |
|---|---|---|
| Evidence Rule | PASS | Source/Runtime 우선순위 정의 |
| PDMG Main Subject | PASS | NSIGHT Target과 관점 분리 |
| L0~L5 | PASS | Top-down Drill-down 고정 |
| State Tag | PASS | Fact/Gap/Open/Pass 분리 |
| Boundary Rule | PASS | Module/Process/JVM/Context 구분 |
| Document Route | PASS | 00~18 고정 |
| Quality Gate | PASS | Diagram/Evidence/PASS 분리 검증 |

---

# 24. Next Chapter Handoff

## FIG-00-21. 00 → 01

```text
00
"어떻게 정의하고 검증할 것인가?"
        ↓
01
"PDMG 전체는 무엇이며
어떤 Architecture로 동작하는가?"
```

다음 장에서는 세부 Class부터 시작하지 않는다.

먼저 **PDMG 전체 Architecture 한 장**을 고정한다.
