# PDMG 전체 아키텍처 정의서 — 00~18 통합본
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 통합일: 2026-09-01  
> 상태: `[WORKING INTEGRATED BASELINE]`  
> Final HG90 상태: `[OPEN]`

---



<!-- ============================================================ -->
<!-- CHAPTER 00: MASTER INDEX / DEFINITION GUIDE -->
<!-- SOURCE: 00_PDMG_ARCHITECTURE_MASTER_INDEX.md -->
<!-- ============================================================ -->

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


---



<!-- ============================================================ -->
<!-- CHAPTER 01: EXECUTIVE ARCHITECTURE OVERVIEW -->
<!-- SOURCE: 01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md -->
<!-- ============================================================ -->

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


---



<!-- ============================================================ -->
<!-- CHAPTER 02: SYSTEM CONTEXT & BOUNDARY -->
<!-- SOURCE: 02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md -->
<!-- ============================================================ -->

# PDMG 전체 아키텍처 정의서
# 02. PDMG SYSTEM CONTEXT & BOUNDARY ARCHITECTURE
## User / Channel / Process / Module / Data / Security / External / Observability Boundary
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-02-SYSTEM-CONTEXT-BOUNDARY`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 선행 장: `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`  
> 작성 원칙: **PDMG의 외부/내부 경계를 먼저 고정하고, Module/Component/Runtime 상세는 03장 이후로 Drill-down 한다.**

---

# 0. Chapter Purpose

## FIG-02-01. 02장이 답해야 하는 질문

```text
누가 PDMG를 호출하는가?
        ↓
PDMG에 들어오는 진입점은 무엇인가?
        ↓
pdmg-ui / pdmg-jwt / pdmg-service는
같은 Process인가 다른 Process인가?
        ↓
pdmg-service와 pdmg-fw의 경계는 무엇인가?
        ↓
PDMG가 직접 소유하는 Data Boundary는 어디까지인가?
        ↓
외부 API / Event / CDC / ETL / File은
PDMG Current인가, NSIGHT 상위 Context인가?
        ↓
Security Trust Boundary는 어디에 있는가?
        ↓
Observability는 어느 경계를 횡단하는가?
        ↓
어떤 연결은 정상이고 어떤 연결은 금지되는가?
```

02장은 PDMG 내부의 Handler/Facade/Service를 자세히 설명하는 장이 아니다.

이 장의 목표는 다음 경계를 **공간적으로 고정**하는 것이다.

```text
Channel Boundary
HTTP Boundary
Process Boundary
Module Boundary
Spring Context Boundary
Application Boundary
Data Boundary
External Interface Boundary
Security Trust Boundary
Observability Boundary
Operations Boundary
Physical Handoff Boundary
```

---

# 1. Evidence Register

## FIG-02-02. Evidence Sources

```text
PDMG Current
Source / Config / Runtime
        ↓
Module / Application Architecture
        ↓
Executive Architecture
        ↓
NSIGHT Big Picture / System Boundary
        ↓
Interface / Data / Security Target
        ↓
Decision / PASS Register
```

| Evidence ID | 근거자료 | 본 장 사용 목적 | 상태 |
|---|---|---|---|
| EV-02-01 | `00_PDMG_ARCHITECTURE_MASTER_INDEX` | Evidence/PASS/Boundary 작성기준 | `[DECISION]` |
| EV-02-02 | `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2` | PDMG L0/L1 Executive Baseline | `[WORKING BASELINE]` |
| EV-02-03 | `III. PDMG Module / Application Architecture` | Module/HTTP Process/Spring Context | `[AS-IS REFERENCE]` |
| EV-02-04 | `IV. PDMG Online Runtime / TCF Flow` | Online Entry/Request Boundary | `[AS-IS REFERENCE]` |
| EV-02-05 | `V. Transaction / Timeout / Thread / DB` | Runtime/Data Boundary | `[AS-IS REFERENCE]` |
| EV-02-06 | `VI. Message / Context / Error / Logging` | Message/Context/Observability Boundary | `[AS-IS REFERENCE]` |
| EV-02-07 | `VII. Security / SSO / JWT / Session` | Trust Boundary/JWT | `[AS-IS REFERENCE]` |
| EV-02-08 | `VIII. Infrastructure / WAS / HA / DR` | GSLB/L4/WEB/WAS Physical Handoff | `[PHYSICAL REFERENCE]` |
| EV-02-09 | `II. Big Picture & System Boundary` | NSIGHT Channel/Application/Data/External Boundary | `[NSIGHT REFERENCE]` |
| EV-02-10 | Interface Architecture / Interface Principle | 목적별 API/Event/CDC/ETL/File 및 Direct DB 통제 | `[TARGET REFERENCE]` |
| EV-02-11 | Data Architecture | RDW/ADW/Data Ownership | `[TARGET REFERENCE]` |
| EV-02-12 | Architecture Decision/PASS Register | Direct DB/JWT/Observability/Boundary 결정 | `[DECISION]` |

### Evidence Rule

```text
PDMG Source에 있음
    → PDMG [AS-IS]

NSIGHT Big Picture에만 있음
    → [NSIGHT REFERENCE]

양쪽이 다름
    → [GAP / ALIGNMENT REQUIRED]

근거가 없음
    → [UNKNOWN / OPEN]
```

---

# 2. Figure Plan

## FIG-02-03. Top-down Boundary Drill-down

```text
L0 Enterprise Context
   ↓
L1 PDMG System Context
   ↓
L1/L2 Inbound / Outbound
   ↓
L2 HTTP / Process
   ↓
L2 Module / Spring Context
   ↓
L2 Data
   ↓
L2 Security Trust
   ↓
L2 Observability / Operations
   ↓
L3 Normal / Forbidden Boundary
   ↓
L4 Runtime Crossing
   ↓
L5 Evidence / PASS / GAP
```

| FIG | 제목 | Level | 핵심질문 |
|---|---|---:|---|
| FIG-02-04 | Enterprise Context | L0 | PDMG는 어디에 위치하는가 |
| FIG-02-05 | PDMG System Context | L0/L1 | 누가 호출하고 무엇을 호출하는가 |
| FIG-02-06 | Inbound Boundary | L1/L2 | PDMG 진입경로는 무엇인가 |
| FIG-02-07 | Outbound Boundary | L1/L2 | PDMG가 무엇을 호출하는가 |
| FIG-02-08 | HTTP Process Boundary | L2 | Process는 어떻게 나뉘는가 |
| FIG-02-09 | Module Boundary | L2 | Module과 Process는 다른가 |
| FIG-02-10 | Spring Context Boundary | L2/L3 | pdmg-fw는 어디서 실행되는가 |
| FIG-02-11 | UI Boundary | L2/L3 | Browser/UI 책임은 무엇인가 |
| FIG-02-12 | JWT Boundary | L2/L3 | 인증 Process 경계는 무엇인가 |
| FIG-02-13 | Service Boundary | L2/L3 | 업무 Runtime 책임은 무엇인가 |
| FIG-02-14 | Data Boundary | L2/L3 | PDMG DB 경계는 어디인가 |
| FIG-02-15 | RDW/ADW Boundary | L1/L2 | PDMG와 Data Platform 관계는 |
| FIG-02-16 | External API Boundary | L1/L2 | 외부 서비스 연계는 어떻게 통제하는가 |
| FIG-02-17 | Event/CDC/ETL/File Boundary | L1/L2 | 무엇이 Current이고 무엇이 Reference인가 |
| FIG-02-18 | Security Trust Boundary | L1~L3 | 신뢰경계는 어디인가 |
| FIG-02-19 | Identity Boundary | L2/L4 | JWT와 업무사용자 Context는 어떻게 연결되는가 |
| FIG-02-20 | Observability Boundary | L1~L3 | 관측은 어디를 통과하는가 |
| FIG-02-21 | Operations Boundary | L1/L2 | OM/Control Plane은 어디인가 |
| FIG-02-22 | Physical Handoff | L1/L2 | GSLB/L4/WEB/WAS와 어떻게 연결되는가 |
| FIG-02-23 | Normal Boundary | L3 | 정상 연결은 무엇인가 |
| FIG-02-24 | Forbidden Boundary | L3 | 금지 연결은 무엇인가 |
| FIG-02-25 | Failure Boundary | L3/L4 | 장애는 어디에서 격리되는가 |
| FIG-02-26 | Boundary Trace | L2~L5 | ServiceId/GUID로 어떻게 추적하는가 |
| FIG-02-27 | Decision Map | L0 | 관련 의사결정은 무엇인가 |
| FIG-02-28 | GAP Map | L0 | 미해결 경계는 무엇인가 |
| FIG-02-29 | PASS Summary | L0 | 02장 판정은 |
| FIG-02-30 | 02→03 Handoff | L0/L1 | Application/Module로 무엇을 넘기는가 |

---

# 3. L0 — Enterprise Context에서 본 PDMG

## FIG-02-04. PDMG Enterprise Position

```text
┌──────────────────── USER / CHANNEL ────────────────────┐
│                                                       │
│ Internal User / Information Terminal                  │
│ Web / Browser                                         │
│ Other Channel                                         │
│                                                       │
└────────────────────────┬──────────────────────────────┘
                         │
                         ▼
┌──────────────────── ACCESS / DELIVERY ─────────────────┐
│                                                       │
│ GSLB / L4 / WEB                                       │
│ [NSIGHT PHYSICAL REFERENCE]                           │
│                                                       │
└────────────────────────┬──────────────────────────────┘
                         │
                         ▼
┌──────────────────── PDMG APPLICATION ──────────────────┐
│                                                       │
│ pdmg-ui                                               │
│ pdmg-jwt                                              │
│ pdmg-service + pdmg-fw                               │
│ pdmg-om?                                              │
│                                                       │
└────────────────────────┬──────────────────────────────┘
                         │
             ┌───────────┼────────────┐
             │           │            │
             ▼           ▼            ▼
        RDW / DB     External      Operations /
                     Service       Monitoring
             │
             │ NSIGHT broader context
             ▼
       ADW / Event / CDC /
       ETL / File / Governance
```

### 핵심 판정

PDMG는 NSIGHT 전체를 의미하지 않는다.

```text
NSIGHT
= Enterprise Target Architecture

PDMG
= Information Application / Runtime Reference
```

따라서 PDMG Current Source가 직접 증명하지 않는 `Kafka`, `CDC`, `ETL`, `ADW`, `Data Governance` 전체를 PDMG의 내부 Component로 넣지 않는다.

---

# 4. L0/L1 — PDMG System Context

## FIG-02-05. PDMG System Context

```text
                         ┌─────────────────────┐
                         │ User / Browser      │
                         └───────┬─────────────┘
                                 │
              ┌──────────────────┼──────────────────┐
              │                  │                  │
              ▼                  ▼                  ▼
       ┌──────────────┐   ┌──────────────┐   ┌─────────────────┐
       │ pdmg-ui      │   │ pdmg-jwt     │   │ pdmg-service    │
       │ UI Boundary  │   │ Auth Boundary│   │ Biz Boundary    │
       └──────┬───────┘   └──────┬───────┘   └────────┬────────┘
              │                  │ Bearer              │
              └──────────────────┼─────────────────────┘
                                 ▼
                        ┌─────────────────┐
                        │ pdmg-fw        │
                        │ Runtime Control │
                        │ same runtime    │
                        └────────┬────────┘
                                 │
                                 ▼
                        ┌─────────────────┐
                        │ Business Core   │
                        │ Handler→DAO     │
                        └────────┬────────┘
                                 │
                                 ▼
                        ┌─────────────────┐
                        │ RDW / DB        │
                        └─────────────────┘

External API / Event / CDC / ETL / File
        = Context / Target / Inventory dependent
        = PDMG Current direct ownership not assumed
```

### Context Objects

| Context Object | PDMG 관계 | 상태 |
|---|---|---|
| User/Browser | Caller | `[CONFIRMED]` |
| pdmg-ui | UI Entry | `[AS-IS]` |
| pdmg-jwt | Authentication/Token | `[AS-IS]` |
| pdmg-service | Business Runtime | `[AS-IS]` |
| pdmg-fw | In-process Framework | `[AS-IS]` |
| RDW/DB | Data Access Target | `[AS-IS]` |
| pdmg-om | Operations Candidate | `[UNKNOWN]` |
| Event/CDC/ETL/File | NSIGHT broader integration | `[REFERENCE/OPEN]` |

---

# 5. L1/L2 — Inbound Boundary

## FIG-02-06. PDMG Inbound Paths

```text
User / Browser
      │
      ├─────────── UI Request ───────────► pdmg-ui
      │
      ├────────── Login / SSO ───────────► pdmg-jwt
      │
      └────────── Online Business ───────► pdmg-service
                                             │
                                             ▼
                                      Framework Entry
                                      Filter / Security
                                             │
                                             ▼
                                      Controller / TCF
```

### Inbound 책임

```text
pdmg-ui
= 화면/요청 구성

pdmg-jwt
= 인증 및 Token lifecycle

pdmg-service
= Business online transaction
```

### Boundary Rule

PDMG의 inbound는 하나의 URL/Process라고 가정하지 않는다.

```text
pdmg-ui
pdmg-jwt
pdmg-service

각각
HTTP / Runtime Boundary가 존재할 수 있음
```

실제 Production Domain/Port는 05장 Physical Architecture에서 Evidence 기반으로 확정한다.

---

# 6. L1/L2 — Outbound Boundary

## FIG-02-07. PDMG Outbound Context

```text
PDMG Business Runtime
        │
        ├──── JDBC / MyBatis ─────► RDW / DB
        │
        ├──── HTTP/API? ──────────► External Service [Inventory Required]
        │
        ├──── File? ──────────────► File Platform [OPEN]
        │
        └──── Event? ─────────────► Event Platform [OPEN]
```

### Strongest Current Evidence

```text
PDMG
  ↓
DAO / Mapper
  ↓
JDBC
  ↓
DB

[AS-IS]
```

### Important Rule

```text
NSIGHT에 Event/CDC/ETL/File이 존재
        ↓
PDMG가 모두 직접 호출

이라고 해석하지 않는다.
```

Outbound interface는 06장 Interface Architecture에서 실제 Inventory/InterfaceId를 기준으로 전수화한다.

---

# 7. L2 — HTTP / Process Boundary

## FIG-02-08. Process Boundary

```text
┌──────────────── Process A ────────────────┐
│ pdmg-ui                                  │
│ UI / Static / Client-facing Runtime      │
└───────────────────┬──────────────────────┘
                    │ HTTP
                    ▼
┌──────────────── Process B ────────────────┐
│ pdmg-service                             │
│ Business Runtime                         │
│                                          │
│ pdmg-fw Framework Beans                  │
└──────────────────────────────────────────┘

┌──────────────── Process C ────────────────┐
│ pdmg-jwt                                │
│ Authentication / Token Runtime           │
└──────────────────────────────────────────┘

┌──────────────── Process ? ────────────────┐
│ pdmg-om                                 │
│ [UNKNOWN]                                │
└──────────────────────────────────────────┘
```

### 핵심

```text
pdmg-ui ↔ pdmg-service
= HTTP Process Boundary 가능/확인

pdmg-jwt ↔ pdmg-service
= Token/HTTP Security Boundary

pdmg-service ↔ pdmg-fw
≠ HTTP Process Boundary
```

---

# 8. L2 — Module Boundary

## FIG-02-09. Build Module vs Runtime

```text
Repository
│
├─ pdmg-ui
├─ pdmg-jwt
├─ pdmg-fw
├─ pdmg-service
└─ pdmg-om
      │
      │ build/module relationship
      ▼
Runtime
│
├─ pdmg-ui Process
├─ pdmg-jwt Process
└─ pdmg-service Process
      ├─ Business Classes
      └─ pdmg-fw Framework Classes
```

### Rule

```text
Module Boundary
≠ Process Boundary
```

별도 Gradle Module은 책임/빌드 분리일 수 있지만 반드시 별도 Server/Port를 의미하지 않는다.

---

# 9. L2/L3 — Spring ApplicationContext Boundary

## FIG-02-10. pdmg-service Runtime Context

```text
┌──────────── pdmg-service JVM / Spring Context ─────────────┐
│                                                           │
│ @SpringBootApplication(scanBasePackages="nhnis")          │
│                                                           │
│  ┌──────────────────────┐   ┌───────────────────────────┐ │
│  │ nhnis.mg.*          │   │ nhnis.fw.*               │ │
│  │ Business Components │   │ Framework Components      │ │
│  └──────────┬───────────┘   └────────────┬──────────────┘ │
│             │                            │                │
│             └───────────── Spring Bean ──┘                │
│                                                           │
└───────────────────────────────────────────────────────────┘
```

### 핵심 판정

`pdmg-fw`는:

```text
Framework Module
+
Framework Package
+
Framework Bean
```

이지만 Current Runtime에서는:

```text
pdmg-service가 HTTP로 호출하는 Remote Framework Server
```

로 보지 않는다.

---

# 10. L2/L3 — UI Boundary

## FIG-02-11. pdmg-ui Boundary

```text
Browser
  ↓
pdmg-ui
  ├─ Static / UI Resource
  ├─ Menu / Screen
  ├─ Transaction Catalog
  ├─ ServiceId Request
  └─ Bearer Token 전달
  ↓
HTTP
  ↓
pdmg-service
```

### UI가 소유해야 할 것

```text
Presentation
User Interaction
Request Assembly
Client-side Validation
Navigation
```

### UI가 소유하면 안 되는 것

```text
DB Direct Access
Server Transaction
Authorization Final Decision
Business SQL
```

---

# 11. L2/L3 — Authentication / JWT Boundary

## FIG-02-12. pdmg-jwt Boundary

```text
User / SSO Caller
       ↓
┌────────────────────────┐
│ pdmg-jwt               │
│                        │
│ Login                  │
│ SSO Validation         │
│ Access Token           │
│ Refresh Token          │
│ JWKS                   │
└───────────┬────────────┘
            │ Bearer Token
            ▼
┌────────────────────────┐
│ pdmg-service / pdmg-fw │
│ Token Verification     │
└────────────────────────┘
```

### Trust Transition

```text
Unauthenticated Request
       ↓
Authentication Boundary
       ↓
Token
       ↓
Verification Boundary
       ↓
Trusted Principal
```

Current algorithm/key mismatch는 11장에서 상세화한다.

---

# 12. L2/L3 — Business Service Boundary

## FIG-02-13. pdmg-service Responsibility

```text
HTTP Business Request
        ↓
Framework Entry
        ↓
Controller / TCF
        ↓
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

### pdmg-service가 소유하는 것

```text
Business Use Case
ServiceId Handler
Application Facade
Business Service
Persistence Access
DTO
```

### pdmg-service가 직접 소유하지 않는 것으로 분리할 것

```text
UI Presentation
Token Signing Key Lifecycle
Enterprise Data Platform 전체
L4/GSLB
Cross-system Data Ownership
```

---

# 13. L2/L3 — Data Boundary

## FIG-02-14. Application to Data Boundary

```text
PDMG Application
      ↓
Business Service
      ↓
DAO
      ↓
Mapper
      ↓
JDBC
      ↓
┌────────────────── DATA BOUNDARY ──────────────────┐
│                                                  │
│ RDW / DB                                         │
│ Table / View / SQL                               │
│                                                  │
└──────────────────────────────────────────────────┘
```

### Boundary Rule

Application과 DB 사이에는 Data Access Contract가 존재한다.

```text
Service
→ DAO
→ Mapper
→ SQL
→ DB
```

다음은 금지패턴이다.

```text
Controller → SQL
Handler → Mapper
UI → DB
External → PDMG DB direct DML
```

---

# 14. L1/L2 — RDW / ADW Boundary

## FIG-02-15. PDMG Current와 Data Platform

```text
PDMG Current
     │
     │ JDBC / SQL
     ▼
RDW / Operational Data
     │
     │ NSIGHT Data Flow
     ▼
ADW / Analytical Data
```

### 해석

NSIGHT Target은:

```text
RDW
= Operational / Near Real-time

ADW
= Analytical / Mart / Heavy Query
```

분리를 지향한다.

하지만 PDMG Current Source가 RDW와 ADW 모두를 동일 수준으로 직접 사용하는지는 별도 Datasource/Mapper/SQL Inventory로 확인해야 한다.

따라서:

```text
PDMG → RDW
= Stronger Current Evidence

PDMG → ADW
= [OPEN / Inventory Required]
```

---

# 15. L1/L2 — External API Boundary

## FIG-02-16. External Service Call

```text
PDMG
  ↓
Approved Client / Adapter
  ↓
Interface Contract
  ↓
HTTP / API
  ↓
External / Internal Target System
```

### Interface Boundary Rule

```text
Business Service
      ↓
Approved Interface Adapter
      ↓
Contract
      ↓
Target System
```

다음처럼 다른 시스템의 내부 Component를 직접 의존하지 않는다.

```text
PDMG Controller
      ↓
Target System DAO

X
```

실제 External API 목록은 06장에서 Interface Inventory로 확정한다.

---

# 16. L1/L2 — Event / CDC / ETL / File Boundary

## FIG-02-17. Integration Mechanism Boundary

```text
NSIGHT Integration Context
│
├─ Online Immediate Result
│      → API / Service
│
├─ Business Event
│      → Event Broker
│
├─ DB Change
│      → CDC
│
├─ Bulk Data
│      → ETL
│
└─ File
       → MFT / FOS
```

### PDMG Current 상태

```text
API / DB
= Current Evidence 존재 가능

Event / CDC / ETL / File
= PDMG 내부 Component라고 단정하지 않음
= [REFERENCE / OPEN]
```

### 경계 원칙

목적에 따라 Integration Mechanism을 구분한다.

```text
API
≠ Event
≠ CDC
≠ ETL
≠ File
```

---

# 17. L1~L3 — Security Trust Boundary

## FIG-02-18. Trust Boundaries

```text
[UNTRUSTED / USER ZONE]
Browser / User
      │
      ▼
──────── Authentication Boundary ────────
      │
      ▼
pdmg-jwt
      │ Token
      ▼
──────── Verification Boundary ──────────
      │
      ▼
pdmg-service / pdmg-fw
      │ Trusted Principal
      ▼
──────── Business Authorization ─────────
      │
      ▼
Business Service
      │
      ▼
──────── Data Authorization Boundary ─────
      │
      ▼
DB
```

### Security Boundary는 하나가 아니다

```text
Authentication
Verification
Authorization
Data Access
Audit
```

각각 별도 책임을 가진다.

---

# 18. L2/L4 — Identity Binding Boundary

## FIG-02-19. JWT Principal vs Business Header

```text
JWT
sub / ssoId
   ↓
Verified Principal
   ↓
Server Trusted Identity
   │
   ├──────── compare / bind ─────────┐
   ▼                                ▼
hdr_nhnis User Context        Authorization Context
   │                                │
   └──────────── Audit ──────────────┘
```

### Current GAP

Current 분석에서는 JWT subject/ssoId가 Request Attribute에 존재하더라도 `hdr_nhnis`의 operator/user context와 자동으로 일치하도록 강제하는지 추가 검증이 필요하다.

따라서 다음을 Target Rule로 둔다.

```text
Client User Header
≠ Trusted Identity

Trusted Identity
= Verified Principal 기반
```

---

# 19. L1~L3 — Observability Boundary

## FIG-02-20. Cross-boundary Observability

```text
Browser / UI
      │
      │ ServiceId / GUID
      ▼
Filter / Framework
      │
      ├─ MDC
      ├─ Request Log
      ▼
Business
      │
      ├─ ServiceId
      ├─ ErrorCode
      ▼
DAO / SQL
      │
      ├─ SqlId
      ▼
DB
      │
      ▼
Metric / Log / Trace
      │
      ▼
Operations / Evidence
```

Observability는 독립 업무 Domain이 아니라 PDMG의 여러 경계를 가로지르는 Cross-cutting Boundary다.

### Key Correlation

```text
GUID
+
ServiceId
+
DeploymentId [Target]
+
Host/JVM [Target]
+
SqlId
+
ErrorCode
```

---

# 20. L1/L2 — Operations / OM Boundary

## FIG-02-21. Runtime Plane vs Control Plane

```text
┌──────────────── RUNTIME PLANE ────────────────┐
│                                              │
│ pdmg-ui / pdmg-jwt / pdmg-service            │
│ Business Request / Token / DB                 │
│                                              │
└───────────────────┬──────────────────────────┘
                    │ metric/log/control
                    ▼
┌──────────────── CONTROL / OPERATIONS ─────────┐
│                                              │
│ OM / Monitoring / Deployment / Runbook       │
│                                              │
│ pdmg-om Current Detail = [UNKNOWN]            │
│                                              │
└──────────────────────────────────────────────┘
```

### Boundary Rule

운영관리 기능을 Business Runtime 내부 업무기능과 혼합하지 않는다.

다만 현재 `pdmg-om`의 실제 구현범위는 Source Evidence 확보 전까지 `[UNKNOWN]`으로 둔다.

---

# 21. L1/L2 — Physical Handoff Boundary

## FIG-02-22. System Context → Physical

```text
System / Application Context
User
 ↓
PDMG
 ↓
DB

        translates to

Physical Working Path
User
 ↓
GSLB
 ↓
L4
 ↓
Apache WEB
 ↓
Tomcat JVM
 ↓
PDMG WAR / Runtime
 ↓
DB Service
```

### Important

02장은 Physical 수량/제품/Port를 확정하지 않는다.

```text
System Boundary
= 무엇과 무엇이 연결되는가

Physical Boundary
= 어떤 Host/VM/JVM/Port에서 연결되는가
```

후자는 05장에서 확정한다.

---

# 22. L3 — Normal Boundary Pattern

## FIG-02-23. Allowed Connections

```text
Browser
  ↓ HTTP
pdmg-ui
  ↓ HTTP
pdmg-service
  ↓ Framework
Handler
  ↓
Facade
  ↓
Service
  ↓
DAO
  ↓ JDBC
DB
```

Security:

```text
Browser
  ↓
pdmg-jwt
  ↓ Token
pdmg-service
```

Observability:

```text
Runtime
  ↓
Metric / Log / Trace
  ↓
Operations
```

### 정상 경계 원칙

1. 책임은 소유 Boundary 안에 둔다.
2. 연결은 Boundary에서 명시적으로 통제한다.
3. 외부 호출은 Interface Contract를 거친다.
4. DB 접근은 Data Access Layer를 거친다.
5. Security는 인증/검증/인가 단계를 구분한다.
6. Observability ID는 경계를 넘어 유지한다.

---

# 23. L3 — Forbidden Boundary Pattern

## FIG-02-24. Forbidden Paths

```text
[1]
Browser ─────► DB
        X

[2]
External System ─────► PDMG DB DML
                X

[3]
pdmg-ui ─────► DAO / Mapper
        X

[4]
Handler ─────► Target System DAO
        X

[5]
pdmg-fw
   │ HTTP
   ▼
pdmg-service
라고 모델링
        X

[6]
Event / CDC / ETL / File
모두 REST로 통일
        X

[7]
NSIGHT Target Component
=
PDMG Current AS-IS
        X
```

Big Picture 원칙에서도 Channel/Application/Data/Governance 책임을 섞지 않고, P2P Direct DB/DB-Link를 정상패턴으로 두지 않는 것이 핵심 Boundary Rule이다.

---

# 24. L3/L4 — Failure Boundary

## FIG-02-25. Failure Propagation

```text
Client Failure
   ↓
Access Failure
   ↓
UI Failure
   ↓
JWT Failure
   ↓
Service Runtime Failure
   ↓
Worker / Timeout Failure
   ↓
DB Failure
```

### Architecture 목적

각 Failure Domain이 다음 단계까지 무제한 전파되지 않도록 한다.

```text
Detection
  ↓
Isolation
  ↓
Error Mapping
  ↓
Timeout
  ↓
Retry / No-Retry Decision
  ↓
Recovery
  ↓
Evidence
```

예:

```text
DB Slow
 ↓
Hikari Pending
 ↓
Worker Queue
 ↓
Request Timeout
 ↓
Client Error
```

따라서 System Boundary는 단순 연결선이 아니라 **Failure Propagation Boundary**이기도 하다.

---

# 25. L2~L5 — Boundary Traceability

## FIG-02-26. Boundary Crossing Trace

```text
User Action
   ↓
GUID
   ↓
ServiceId
   ↓
pdmg-ui Request
   ↓
pdmg-service Entry
   ↓
Handler
   ↓
Facade / Service
   ↓
DAO / SqlId
   ↓
DB
   ↓
Response / Error
   ↓
Metric / Log / Trace
```

### Target Extension

```text
ServiceId
  ↓
Artifact Hash
  ↓
DeploymentId
  ↓
JVM / Host
  ↓
Runtime Evidence
```

02장의 모든 핵심 Boundary는 이후 장에서 ServiceId/InterfaceId/Data Object/Deployment Evidence로 추적 가능해야 한다.

---

# 26. Architecture Boundary Responsibility Matrix

## TEXT ARCHITECTURE — Boundary Ownership

```text
Channel
  ↓ owned by UI/Channel

Authentication
  ↓ owned by Security/JWT

Business
  ↓ owned by Application

Data Access
  ↓ owned by DAO/Mapper + Data

External Integration
  ↓ owned by Interface Contract

Runtime Control
  ↓ owned by Framework

Operations
  ↓ owned by Ops/Control Plane
```

| Boundary | 주요 책임 | PDMG Current | 후속 장 |
|---|---|---|---|
| Channel | 사용자 접점 | pdmg-ui | 03 |
| Authentication | 로그인/Token | pdmg-jwt | 11 |
| HTTP Process | Process 간 호출 | UI/JWT/Service | 05/09 |
| Framework | 실행통제 | pdmg-fw | 08 |
| Business | Use Case | pdmg-service | 03 |
| Data | DAO/Mapper/DB | 확인 | 07 |
| External | API/Event/File 등 | Inventory 필요 | 06 |
| Trust | 인증/검증/인가 | 부분확인/GAP | 11 |
| Observability | GUID/Log/Trace | 부분확인 | 14/17 |
| OM | 운영통제 | Unknown | 14 |

---

# 27. Architecture Decision Mapping

## FIG-02-27. Boundary Decisions

```text
System Boundary
│
├─ Interface Type
│   ADR-TASK-014
│
├─ Direct DB / DB-Link
│   ADR-TASK-015
│
├─ JWT Verification
│   ADR-TASK-010
│
├─ Identity Binding
│   ADR-TASK-013
│
├─ WEB/WAS Topology
│   ADR-TASK-027
│
├─ Observability
│   ADR-TASK-035
│
└─ OM Control Plane
    ADR-TASK-036
```

| Decision | 02장 방향 | Current PDMG | 판정 |
|---|---|---|---|
| Interface Type | 목적별 Contract | 전수 Inventory 미완료 | CONDITIONAL |
| Direct DB | Cross-system direct DML 금지 | Current 직접 DML Evidence 없음 | PASS/VERIFY |
| JWT | RS256/JWKS 정합 | Algorithm GAP | GAP |
| Identity | Principal 기준 | Header binding 검증필요 | GAP |
| WEB/WAS | Access→WEB→WAS | 실 Host Mapping 미완료 | CONDITIONAL |
| Observability | Cross-cutting | GUID/MDC/ImageLog 부분 | PARTIAL |
| OM | Separate Control Plane | Source Detail Unknown | OPEN |

---

# 28. GAP / OPEN / RISK Map

## FIG-02-28. Boundary GAP Map

```text
SYSTEM CONTEXT
│
├─ Inbound
│   └─ Actual Domain / Port
│      [OPEN → 05]
│
├─ Outbound
│   └─ External API Inventory
│      [OPEN → 06]
│
├─ Data
│   └─ RDW vs ADW actual datasource usage
│      [OPEN → 07]
│
├─ Security
│   ├─ RS256/HMAC
│   │  [CRITICAL GAP]
│   └─ Identity Binding
│      [GAP]
│
├─ Process
│   └─ Actual Host/JVM/WAR mapping
│      [GAP → 05]
│
├─ Integration
│   └─ Event/CDC/ETL/File PDMG ownership
│      [UNKNOWN/REFERENCE]
│
├─ Operations
│   └─ pdmg-om
│      [UNKNOWN]
│
└─ Observability
    └─ Deployment/Host correlation
       [GAP]
```

---

# 29. Architecture Rules

## TEXT ARCHITECTURE — Boundary Rule Set

```text
R-BND-01
Module ≠ Process

R-BND-02
pdmg-fw ≠ Remote Business Server

R-BND-03
UI → DB Direct = Forbidden

R-BND-04
Cross-system Direct DB DML = Forbidden

R-BND-05
External Call → Approved Contract

R-BND-06
Authentication ≠ Authorization

R-BND-07
Client Header ≠ Trusted Principal

R-BND-08
PDMG Current ≠ NSIGHT Entire Target

R-BND-09
Unknown Physical Detail → OPEN

R-BND-10
Every Boundary Crossing → Traceable
```

### Rule Description

| Rule | 설명 | 검증 |
|---|---|---|
| R-BND-01 | Build Module을 Process로 오인하지 않음 | Build/Deployment Map |
| R-BND-02 | FW는 in-process Framework 가능 | Spring Context Scan |
| R-BND-03 | UI는 Data Layer를 우회하지 않음 | Source Dependency Scan |
| R-BND-04 | 시스템간 Direct DB DML 금지 | SQL/DB Privilege Scan |
| R-BND-05 | Outbound는 Contract 기반 | Interface Registry |
| R-BND-06 | 인증 후 인가 별도 | Security Test |
| R-BND-07 | 사용자 Header 신뢰 금지 | Principal Binding Test |
| R-BND-08 | Target→AS-IS 자동승격 금지 | Evidence Tag Review |
| R-BND-09 | Host/Port 미확인 값 창작 금지 | Inventory |
| R-BND-10 | GUID/ServiceId 유지 | Runtime Trace |

---

# 30. Security Boundary Review

## TEXT ARCHITECTURE — Security Review

```text
Inbound
 ↓
Authentication?
 ↓
Token Verification?
 ↓
Trusted Principal?
 ↓
Business Authorization?
 ↓
Data Authorization?
 ↓
Audit?
```

### Review Questions

```text
Q1. 모든 Business Endpoint는 SecurityFilterChain을 거치는가?
Q2. Public Endpoint는 명시적으로 분리되어 있는가?
Q3. JWT의 issuer/audience/exp/signature가 검증되는가?
Q4. kid/JWKS는 Multi-instance에서 일관적인가?
Q5. JWT Principal과 Business Header가 일치하는가?
Q6. ServiceId별 권한통제가 존재하는가?
Q7. DB Credential과 Application 권한이 최소권한인가?
```

02장에서는 상세 구현을 확정하지 않고 Boundary 질문을 고정한다. 상세는 11장으로 이관한다.

---

# 31. Performance / Availability Boundary Review

## TEXT ARCHITECTURE — NFR Crossing

```text
Client
 ↓
Access
 ↓
WEB
 ↓
WAS Request Thread
 ↓
Worker
 ↓
DB Pool
 ↓
DB
```

각 Boundary는 다음 NFR을 가진다.

```text
Latency
Timeout
Concurrency
Queue
Capacity
Failure Detection
Health Check
Retry / No Retry
HA
```

### Important

02장에서 다음 숫자는 확정하지 않는다.

```text
Tomcat maxThreads
Hikari maxPoolSize
JVM Heap
Server Count
Final Timeout
```

이 값들은 10장/16장에 Evidence 기반으로 확정한다.

---

# 32. Operations / Observability Review

## TEXT ARCHITECTURE — Evidence Crossing

```text
Request
 ↓ GUID / ServiceId
Application
 ↓ ErrorCode
SQL
 ↓ SqlId
Runtime
 ↓ Host/JVM/Deployment
Monitoring
 ↓ Alert
Incident
 ↓ Runbook
Evidence
```

### Architecture Goal

장애가 어느 Boundary에서 시작되었는지 역추적할 수 있어야 한다.

```text
Business Error
 ↑
ServiceId
 ↑
Application
 ↑
JVM
 ↑
Host
```

또는:

```text
Host Incident
 ↓
JVM
 ↓
WAR
 ↓
Application
 ↓
ServiceId
```

현재 PDMG는 첫 번째 축의 일부 Evidence가 강하며 두 번째 Physical Reverse Trace는 보완이 필요하다.

---

# 33. Failure / Risk Register

## TEXT ARCHITECTURE — Boundary Risk

```text
Wrong Boundary
      ↓
Wrong Ownership
      ↓
Tight Coupling
      ↓
Failure Propagation
      ↓
Difficult Recovery
```

| Risk ID | Risk | 영향 | 상태 |
|---|---|---|---|
| RISK-BND-01 | Module=Server 오해 | 잘못된 배치/설계 | High |
| RISK-BND-02 | FW를 Remote Service로 모델링 | 불필요 Network Boundary | High |
| RISK-BND-03 | UI/External Direct DB | 보안/변경/정합 | Critical |
| RISK-BND-04 | Authentication/Header Identity 불일치 | 권한오용 | Critical |
| RISK-BND-05 | Event/CDC/ETL Current 오인 | Scope 오류 | High |
| RISK-BND-06 | Actual Host/JVM Mapping 부재 | 장애영향 분석 불가 | High |
| RISK-BND-07 | OM Scope 불명확 | 운영책임 중복/누락 | Medium/High |
| RISK-BND-08 | GUID만 있고 Deployment Trace 없음 | Runtime Evidence 약화 | High |

---

# 34. GAP Register

## TEXT ARCHITECTURE — GAP Lifecycle

```text
Expected Boundary
      ↓ compare
Current Evidence
      ↓
GAP
      ↓
Owner
      ↓
Action / Test / ADR
      ↓
Close
```

| GAP ID | GAP | 후속 |
|---|---|---|
| GAP-BND-01 | PDMG 실제 Host/JVM/WAR Mapping 미완료 | 05장 |
| GAP-BND-02 | External API/Interface Inventory 미완료 | 06장 |
| GAP-BND-03 | RDW/ADW 실제 Datasource Mapping 확인 필요 | 07장 |
| GAP-BND-04 | JWT Issuer/Verifier 불일치 | 11장 |
| GAP-BND-05 | Principal↔Business User Binding | 11장 |
| GAP-BND-06 | Event/CDC/ETL/File PDMG Current Scope 불명확 | 06/13장 |
| GAP-BND-07 | pdmg-om Current Scope | 14장 |
| GAP-BND-08 | Runtime→Host/JVM/Deployment Evidence | 14/17장 |
| GAP-BND-09 | Public/Protected Endpoint Registry | 11장 |
| GAP-BND-10 | Direct DB/DB-Link Exception Inventory | 06/07장 |

---

# 35. ADR Candidates

## TEXT ARCHITECTURE — Boundary Decisions

```text
GAP
 ↓
Decision Question
 ↓
주안 / 대안
 ↓
Evidence
 ↓
ADR
 ↓
Boundary Standard
```

02장에서 연결되는 ADR Candidate:

```text
ADR-BND-01 PDMG System Boundary Baseline
ADR-BND-02 pdmg-fw Runtime Boundary
ADR-BND-03 External Interface Boundary
ADR-BND-04 Cross-system DB Access Policy
ADR-BND-05 Authentication / Trust Boundary
ADR-BND-06 Identity Binding
ADR-BND-07 Operations / OM Boundary
ADR-BND-08 Observability Correlation Boundary
```

기존 Decision Task와 중복 생성하지 않고 기존 `ADR-TASK-*`를 우선 연결한다.

---

# 36. Boundary Test / Verification

## TEXT ARCHITECTURE — Test Path

```text
Architecture Boundary
        ↓
Static Rule
        ↓
Config Test
        ↓
Connectivity Test
        ↓
Security Test
        ↓
Failure Test
        ↓
Runtime Evidence
```

### Test Catalog

| Test | 검증 내용 |
|---|---|
| T-BND-01 | UI Source가 DAO/Mapper에 의존하지 않는지 |
| T-BND-02 | pdmg-fw가 별도 Remote HTTP dependency로 사용되지 않는지 |
| T-BND-03 | Service Endpoint Security Chain 적용 |
| T-BND-04 | JWT Principal/Header mismatch 차단 |
| T-BND-05 | Cross-system DB 권한/DB-Link Inventory |
| T-BND-06 | External API가 Registry/Contract에 존재하는지 |
| T-BND-07 | GUID/ServiceId가 Process Boundary를 넘어 유지되는지 |
| T-BND-08 | DeploymentId/Host/JVM과 거래 Trace 연결 |
| T-BND-09 | DB 장애 시 Application Error Boundary |
| T-BND-10 | JWT 서비스 장애/Key 실패 시 Business 영향 |

---

# 37. Boundary Review Checklist

## TEXT ARCHITECTURE — Checklist

```text
System Context?
   ↓
Inbound?
   ↓
Outbound?
   ↓
Process?
   ↓
Module?
   ↓
Spring Context?
   ↓
Data?
   ↓
Security?
   ↓
Observability?
   ↓
Operations?
   ↓
PASS / GAP
```

Checklist:

```text
[ ] PDMG Main Subject가 명확한가
[ ] NSIGHT Target과 PDMG AS-IS가 분리되었는가
[ ] User/Browser Caller가 정의되었는가
[ ] pdmg-ui / jwt / service 경계가 정의되었는가
[ ] pdmg-fw를 Remote Server로 잘못 표현하지 않았는가
[ ] Data Boundary가 DAO/Mapper 이후로 정의되었는가
[ ] Direct DB 금지경로가 정의되었는가
[ ] External Interface는 Contract 기준인가
[ ] Event/CDC/ETL/File을 Current로 창작하지 않았는가
[ ] Authentication/Authorization가 구분되었는가
[ ] Identity Binding GAP가 표시되었는가
[ ] Observability Boundary가 Cross-cutting으로 표현되었는가
[ ] pdmg-om Unknown이 유지되었는가
[ ] Actual Host/Port 미확인값이 OPEN인가
[ ] 다음 장의 Module Drill-down으로 연결되는가
```

---

# 38. Architecture PASS / PDMG Conformance

## FIG-02-29. 02장 PASS

```text
Architecture Boundary Definition
        ↓
PASS

Current PDMG Conformance
        ↓
PARTIAL / CONDITIONAL

왜?

Core Process / Module / Data Boundary
= Evidence 강함

하지만

Security / External / Deployment / OM
= GAP / OPEN 존재
```

### Summary

| 영역 | Architecture Definition | Current PDMG | 판정 |
|---|---|---|---|
| System Context | 정의완료 | 강한 Evidence | PASS |
| HTTP Process | 정의완료 | UI/JWT/Service 확인 | PASS |
| Module | 정의완료 | FW/Service 정합 | PASS |
| Spring Context | 정의완료 | scanBasePackages Evidence | PASS |
| Data Boundary | 정의완료 | MyBatis/JDBC 확인 | PASS |
| External API | Contract Rule 정의 | Inventory 미완료 | CONDITIONAL |
| Event/CDC/ETL/File | Reference로 분리 | Current Scope Unknown | PASS/OPEN |
| Security Trust | 정의완료 | JWT GAP | CONDITIONAL/GAP |
| Identity | Target 정의 | Current 검증 필요 | GAP |
| Observability | 정의완료 | 부분 구현 | PARTIAL |
| OM | Boundary 정의 | Current Unknown | OPEN |
| Physical Mapping | Handoff 정의 | 실제 Mapping 미완료 | CONDITIONAL |

### Chapter 판정

```text
Architecture Definition     : PASS
Current PDMG Conformance    : PARTIAL / CONDITIONAL
Runtime Evidence Coverage   : MEDIUM
Critical GAP Count          : Security 1+
OPEN Major Areas            : External Inventory / OM / Physical Mapping
```

---

# 39. PASS 전환조건

## TEXT ARCHITECTURE — Conditional to Full Conformance

```text
02 Boundary Defined
       ↓
External Inventory
       ↓
Datasource Mapping
       ↓
JWT / Identity GAP Close
       ↓
Host/JVM/WAR Mapping
       ↓
OM Scope
       ↓
Runtime Correlation
       ↓
PDMG Boundary Conformance PASS
```

Current PDMG Conformance를 PASS로 올리기 위한 조건:

1. Actual inbound Domain/Endpoint/Port Inventory 확보.
2. PDMG outbound API/InterfaceId 전수 목록 확보.
3. Cross-system Direct DB/DB-Link 예외 전수 확인.
4. RDW/ADW Datasource와 Mapper/SQL 사용현황 Mapping.
5. JWT issuer/verifier 알고리즘/Key 정합.
6. Trusted Principal ↔ `hdr_nhnis` Identity Binding.
7. Public/Protected Endpoint Registry.
8. Event/CDC/ETL/File의 PDMG Current Scope 확정.
9. Artifact→Deployment→Host/JVM/WAR Mapping.
10. pdmg-om Current Scope 확정 또는 PDMG Current Scope에서 제외.
11. GUID/ServiceId/DeploymentId/Host/JVM Correlation Evidence.
12. 주요 Boundary의 Failure/Recovery Test.

---

# 40. 이 장에서 확정하지 않는 것

## TEXT ARCHITECTURE — Deferred Detail

```text
Handler / Facade 상세
      → 03

Logical Node
      → 04

Host / VM / Port
      → 05

InterfaceId / Contract
      → 06

Table / Data Ownership
      → 07

TCF / Framework
      → 08

Runtime Sequence
      → 09

TX / Timeout
      → 10

JWT / Session
      → 11

Message / Error
      → 12

Event / CDC / ETL / Batch / File
      → 13

OM / Observability
      → 14
```

02장은 **경계만 확정**하고 하위 구현을 침범하지 않는다.

---

# 41. 02장 Final Boundary Map

## TEXT ARCHITECTURE — Final System Boundary

```text
                   ┌──────── USER / CHANNEL ────────┐
                   │ Browser / Information User     │
                   └──────────────┬─────────────────┘
                                  │
                                  ▼
                   ┌──────── ACCESS BOUNDARY ───────┐
                   │ GSLB / L4 / WEB [Reference]   │
                   └──────────────┬─────────────────┘
                                  │
             ┌────────────────────┼────────────────────┐
             ▼                    ▼                    ▼
     ┌──────────────┐     ┌──────────────┐     ┌──────────────────┐
     │ pdmg-ui      │     │ pdmg-jwt     │     │ pdmg-service     │
     │ UI Boundary  │     │ Auth Boundary│     │ Business Runtime │
     └──────┬───────┘     └──────┬───────┘     └────────┬─────────┘
            │                    │ Token                 │
            └────────────────────┼───────────────────────┘
                                 ▼
                     ┌──────────────────────────┐
                     │ Spring Runtime Boundary  │
                     │ pdmg-service + pdmg-fw   │
                     └────────────┬─────────────┘
                                  │
                                  ▼
                     ┌──────────────────────────┐
                     │ Business Component       │
                     │ Handler / Facade         │
                     │ Service / DAO / Mapper   │
                     └────────────┬─────────────┘
                                  │ JDBC
                                  ▼
                     ┌──────────────────────────┐
                     │ DATA BOUNDARY            │
                     │ RDW / DB                 │
                     └──────────────────────────┘

      External API / Event / CDC / ETL / File
                    │
                    └─ Approved Contract / Reference / OPEN

      Security
      = Cross-boundary Trust Control

      Observability
      = Cross-boundary GUID / ServiceId / Runtime Evidence

      OM
      = Operations Boundary [CURRENT UNKNOWN]
```

---

# 42. 02장 최종 결론

## TEXT ARCHITECTURE — Boundary Conclusion

```text
PDMG System Context
=
User / Channel
+
HTTP Process
+
Application Runtime
+
Framework Runtime
+
Data Access
+
Security Trust
+
External Contract
+
Operations / Evidence
```

이 장에서 확정한 가장 중요한 사실은 다음이다.

```text
PDMG
≠ 단일 Server

PDMG
≠ 단일 Module

pdmg-fw
≠ Remote Service

PDMG Data Access
≠ Cross-system Direct DB

PDMG Current
≠ NSIGHT Entire Target
```

**02장 Architecture Definition 판정: `PASS`**

다만 Current PDMG Implementation Conformance는 Security/External/Physical/OM Evidence가 남아 있으므로:

```text
PDMG Current Conformance
= PARTIAL / CONDITIONAL
```

로 유지한다.

---

# 43. Next Chapter Handoff

## FIG-02-30. 02 → 03

```text
02 System Context & Boundary
"누가 PDMG를 호출하고,
어디에서 어떤 경계가 존재하는가?"
               ↓
03 Application / Module Architecture
"그 경계 안에서
pdmg-ui / pdmg-jwt / pdmg-fw /
pdmg-service / pdmg-om은
어떤 책임·Package·Component를 가지는가?"
```

03장에서 Drill-down할 축:

```text
PDMG
 ↓
Module
 ↓
Build Dependency
 ↓
Process / JVM
 ↓
Spring Context
 ↓
Package
 ↓
Layer
 ↓
Component
 ↓
ServiceId
 ↓
Dependency Rule
```


---



<!-- ============================================================ -->
<!-- CHAPTER 03: APPLICATION / MODULE -->
<!-- SOURCE: 03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md -->
<!-- ============================================================ -->

# PDMG 전체 아키텍처 정의서
# 03. PDMG APPLICATION / MODULE ARCHITECTURE
## Module / Build / Process / Spring Context / Package / Layer / Component / ServiceId
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Application Architecture**  
> 문서 ID: `PDMG-ARCH-03-APPLICATION-MODULE`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 선행 장: `02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md`  
> 관점: **PDMG의 실제 Module/Package/Component 구조를 먼저 정의하고, NSIGHT는 Target Alignment/PASS 판단기준으로 사용한다.**

---

# 0. Chapter Purpose

## FIG-03-01. 03장이 답해야 하는 질문

```text
PDMG의 5개 기준 모듈은
무엇을 책임지는가?
      ↓
Build Module과 Runtime Process는
어디에서 같고 어디에서 다른가?
      ↓
pdmg-fw는 별도 Remote Server인가,
pdmg-service 내부 Framework Runtime인가?
      ↓
Spring ApplicationContext는
어떤 Package를 로딩하는가?
      ↓
업무 Source는
어떤 Layer / Component로 구성되는가?
      ↓
ServiceId는
Handler / Facade / Service / DAO / Mapper와
어떻게 연결되는가?
      ↓
TCF ON/OFF는
어떤 Inbound Adapter 차이를 만드는가?
      ↓
Current PDMG에서
무엇이 PASS이고 무엇이 GAP인가?
```

이 장은 02장에서 정의한 **System Boundary 내부의 Application 구조**를 확대한다.

```text
02 System Boundary
        ↓
03 Application / Module
        ↓
Repository
        ↓
Build Module
        ↓
Process / JVM
        ↓
Spring Context
        ↓
Package
        ↓
Layer / Component
        ↓
Program / ServiceId
        ↓
Runtime Entry
```

---

# 1. Evidence Register

## FIG-03-02. Evidence Stack

```text
PDMG Source
  ↓
Module / Package / Class
  ↓
Runtime / Spring Context
  ↓
Handler Registry
  ↓
TCF ON/OFF
  ↓
Naming / Mapper
  ↓
Decision / PASS
```

| Evidence ID | 근거자료 | 본 장 사용 목적 | 상태 |
|---|---|---|---|
| EV-03-01 | `III. PDMG Module / Application Architecture` | 5 Module, Process/Context, Package/Layer | `[AS-IS REFERENCE]` |
| EV-03-02 | `PDMG Source Runtime Reference Deep Dive` | Source Tree, Handler/Controller/Facade/Service/DAO | `[AS-IS REFERENCE]` |
| EV-03-03 | `X. Naming / ServiceId / Traceability` | Program/ServiceId/Class/Mapper Naming | `[AS-IS REFERENCE]` |
| EV-03-04 | `00_PDMG_ARCHITECTURE_MASTER_INDEX` | Evidence/상태/PASS 기준 | `[DECISION]` |
| EV-03-05 | `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2` | Executive Application Baseline | `[WORKING BASELINE]` |
| EV-03-06 | `02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1` | Process/Module/Spring/Data Boundary | `[WORKING BASELINE]` |
| EV-03-07 | Online Runtime / TCF 분석 | Dispatcher/Handler Runtime | `[AS-IS REFERENCE]` |
| EV-03-08 | Transaction/Timeout 분석 | Handler/Fascade Transaction 위치 | `[AS-IS REFERENCE]` |
| EV-03-09 | Architecture Decision/PASS Register | Business Core/TCF/Rule Layer 의사결정 | `[DECISION]` |
| EV-03-10 | NSIGHT Application Architecture | Target Application Boundary/Layer | `[TARGET REFERENCE]` |

### Evidence Priority

```text
Source / Config
     ↓
Runtime Evidence
     ↓
PDMG Current Analysis
     ↓
Decision
     ↓
NSIGHT Target
```

---

# 2. Figure Plan

## FIG-03-03. Application Drill-down

```text
L0  PDMG Application Landscape
 ↓
L1  Five Modules
 ↓
L2  Build / Process / Context
 ↓
L2  Package / Layer
 ↓
L3  Handler / Controller / Facade / Service / DAO
 ↓
L4  TCF ON / OFF Entry
 ↓
L5  Program / ServiceId / Mapper / Rule / Test
```

| FIG | 제목 | Level | 핵심 |
|---|---|---:|---|
| FIG-03-04 | 5-Module Application Map | L0/L1 | 전체 Module |
| FIG-03-05 | Module Responsibility | L1 | 책임 |
| FIG-03-06 | Build Dependency | L1/L2 | 빌드관계 |
| FIG-03-07 | Process/JVM Relation | L2 | 실행관계 |
| FIG-03-08 | Spring Context | L2 | Bean 경계 |
| FIG-03-09 | Boundary Equation | L2 | 오해 방지 |
| FIG-03-10 | Framework vs Business | L2/L3 | 책임분리 |
| FIG-03-11 | pdmg-service Package | L2/L3 | Source 구조 |
| FIG-03-12 | Module Package Map | L2/L5 | Base Package |
| FIG-03-13 | Program Source Tree | L3/L5 | mgcoa9000 |
| FIG-03-14 | Layer Model | L3 | 계층 |
| FIG-03-15 | Inbound Adapter | L3/L4 | Handler/Controller |
| FIG-03-16 | Handler Contract | L3/L5 | TransactionHandler |
| FIG-03-17 | Handler Registry | L3/L5 | 13 ServiceIds |
| FIG-03-18 | Handler Branch | L3/L4 | ServiceId 분기 |
| FIG-03-19 | Facade Boundary | L3/L4 | Use Case/TX |
| FIG-03-20 | Service Boundary | L3 | 업무로직 |
| FIG-03-21 | Rule Layer | L3 | AS-IS vs Proposed |
| FIG-03-22 | DAO/Mapper | L3/L5 | Persistence |
| FIG-03-23 | DTO | L3/L5 | Input/Output |
| FIG-03-24 | Naming Axis | L2/L5 | MG/CO/A |
| FIG-03-25 | ServiceId Anatomy | L3/L5 | 11자 |
| FIG-03-26 | Program Stem | L3/L5 | Class 연계 |
| FIG-03-27 | TCF ON | L2~L4 | 공통 Adapter |
| FIG-03-28 | TCF OFF | L2~L4 | MVC Adapter |
| FIG-03-29 | ON/OFF Comparison | L2/L4 | Drift |
| FIG-03-30 | Common Business Core | L2/L3 | Target |
| FIG-03-31 | Dependency Direction | L3 | Allowed |
| FIG-03-32 | Forbidden Dependencies | L3 | 금지 |
| FIG-03-33 | Framework Contract | L2/L3 | FW→Biz |
| FIG-03-34 | Application/Data Boundary | L3 | Data |
| FIG-03-35 | Transaction Position | L3/L4 | TX |
| FIG-03-36 | Error Boundary | L3/L4 | Exception |
| FIG-03-37 | Security Boundary | L2/L3 | Principal |
| FIG-03-38 | Observability | L2/L5 | Trace |
| FIG-03-39 | Build/Artifact Handoff | L2/L5 | Delivery |
| FIG-03-40 | App Architecture Rules | L3/L5 | Rule Set |
| FIG-03-41 | Static Scan | L5 | 자동검증 |
| FIG-03-42 | Unit/Context Test | L5 | Test |
| FIG-03-43 | Current GAP | L0 | GAP |
| FIG-03-44 | Decision Map | L0 | ADR |
| FIG-03-45 | PASS | L0 | 판정 |
| FIG-03-46 | 03→04 Handoff | L0/L1 | 다음 장 |

---

# 3. L0 — PDMG Application Landscape

## FIG-03-04. PDMG 5-Module Application Map

```text
┌────────────────────────── PDMG APPLICATION ────────────────────────────┐
│                                                                       │
│  ┌────────────────┐     ┌────────────────┐                            │
│  │ pdmg-ui        │     │ pdmg-jwt       │                            │
│  │ [AS-IS]        │     │ [AS-IS]        │                            │
│  │ UI / Request   │     │ Auth / Token   │                            │
│  └───────┬────────┘     └───────┬────────┘                            │
│          │ HTTP                 │ Bearer / JWKS                        │
│          └───────────────┬──────┘                                     │
│                          ▼                                            │
│  ┌─────────────────────────────────────────────────────────────────┐  │
│  │ pdmg-service [AS-IS]                                            │  │
│  │ Business Application Runtime                                    │  │
│  │                                                                 │  │
│  │  ┌───────────────────────────────────────────────────────────┐  │  │
│  │  │ pdmg-fw [AS-IS]                                          │  │  │
│  │  │ Framework Module / Runtime Mechanism                      │  │  │
│  │  │ Filter / Context / TCF / Timeout / Error / Logging       │  │  │
│  │  └────────────────────────┬──────────────────────────────────┘  │  │
│  │                           ▼                                     │  │
│  │ Handler → Facade → Service → DAO → Mapper                     │  │
│  └───────────────────────────┬─────────────────────────────────────┘  │
│                              │ JDBC                                  │
│                              ▼                                       │
│                          RDW / DB                                    │
│                                                                       │
│  ┌────────────────┐                                                   │
│  │ pdmg-om        │                                                   │
│  │ [UNKNOWN]      │                                                   │
│  └────────────────┘                                                   │
└───────────────────────────────────────────────────────────────────────┘
```

### Executive Conclusion

PDMG의 Application Architecture는 다음 세 종류의 책임을 분리한다.

```text
Presentation
= pdmg-ui

Security / Identity
= pdmg-jwt

Business Runtime
= pdmg-service

Cross-cutting Runtime Mechanism
= pdmg-fw

Operations
= pdmg-om [Current Unknown]
```

---

# 4. L1 — Module Responsibility

## FIG-03-05. Module Responsibility Map

```text
pdmg-ui
  ↓
"사용자와 어떻게 상호작용할 것인가?"

pdmg-jwt
  ↓
"누구인지 어떻게 증명할 것인가?"

pdmg-fw
  ↓
"거래를 어떻게 안전하게 실행할 것인가?"

pdmg-service
  ↓
"무슨 업무를 수행할 것인가?"

pdmg-om
  ↓
"어떻게 운영/통제할 것인가?"
  [CURRENT UNKNOWN]
```

| Module | 핵심 책임 | 소유해야 하는 것 | 소유하지 말아야 할 것 |
|---|---|---|---|
| pdmg-ui | Presentation/Client | 화면, 요청 구성 | DB SQL, Server TX |
| pdmg-jwt | Auth/Token | 로그인, JWT/JWKS | 업무 Use Case |
| pdmg-fw | Runtime Control | Filter/Context/TCF/Timeout/Error | 고객/상품 업무규칙 |
| pdmg-service | Business Application | Handler/Facade/Service/DAO | Token Signing Infrastructure |
| pdmg-om | Operations 후보 | 운영/통제 | `[UNKNOWN]` | `[UNKNOWN]` |

---

# 5. L1/L2 — Build Dependency View

## FIG-03-06. Build Module Relation

```text
Repository
│
├─ pdmg-ui
│
├─ pdmg-jwt
│
├─ pdmg-fw
│
├─ pdmg-service
│    │
│    └──── build/classpath dependency ────► pdmg-fw
│
└─ pdmg-om
     [CURRENT DETAIL UNKNOWN]
```

### 핵심

```text
pdmg-service
depends on
pdmg-fw

= Build / Classpath Dependency

≠ HTTP Remote Call
```

Module을 나눈 목적은 Source/Build/책임을 분리하기 위함이며, Process 분리 여부는 Deployment/Runtime Evidence로 별도 판단한다.

---

# 6. L2 — Runtime Process / JVM Relation

## FIG-03-07. Module-to-Process Projection

```text
BUILD MODULE                          RUNTIME

pdmg-ui             ───────────────► pdmg-ui Process/JVM

pdmg-jwt            ───────────────► pdmg-jwt Process/JVM

pdmg-service        ──────┐
                          ├────────► pdmg-service Process/JVM
pdmg-fw             ──────┘           ├─ Business Beans
                                      └─ Framework Beans

pdmg-om             ───────────────► [UNKNOWN]
```

### Architecture Equation

```text
1 Build Module
≠
1 Runtime Process

여러 Build Module
→
같은 Runtime Process 가능
```

---

# 7. L2 — Spring ApplicationContext

## FIG-03-08. pdmg-service Spring Context

```text
┌──────────────── pdmg-service JVM ────────────────┐
│                                                 │
│ PdmgApplication                                 │
│ @SpringBootApplication                          │
│ scanBasePackages = "nhnis"                      │
│                                                 │
│ ┌─────────────────────┐  ┌────────────────────┐ │
│ │ nhnis.mg.*          │  │ nhnis.fw.*        │ │
│ │ Business            │  │ Framework         │ │
│ │                     │  │                    │ │
│ │ Handler             │  │ Filter            │ │
│ │ Controller          │  │ TCF               │ │
│ │ Facade              │  │ Timeout           │ │
│ │ Service             │  │ Context           │ │
│ │ DAO                 │  │ Error             │ │
│ └─────────┬───────────┘  └──────────┬─────────┘ │
│           └──────── Spring DI ───────┘           │
└─────────────────────────────────────────────────┘
```

### 판정

```text
pdmg-fw
= same Spring ApplicationContext participation 가능
= [AS-IS]
```

---

# 8. L2 — Boundary Equation

## FIG-03-09. 다섯 경계의 차이

```text
Application Responsibility Boundary
          ≠
Build Module Boundary
          ≠
Process / JVM Boundary
          ≠
Spring ApplicationContext Boundary
          ≠
Logical Technical Node Boundary
```

### 예

```text
pdmg-fw
= Build Module
= Framework Responsibility

BUT

pdmg-fw
≠ 반드시 독립 JVM
≠ 반드시 독립 Port
≠ 반드시 독립 Logical Business System
```

이 구분을 잃으면 Physical Architecture와 Application Architecture가 뒤섞인다.

---

# 9. L2/L3 — Framework vs Business Domain

## FIG-03-10. Responsibility Split

```text
┌──────────────── FRAMEWORK ────────────────┐
│                                          │
│ "어떻게 안전하게 실행할까?"             │
│                                          │
│ Filter                                   │
│ ServiceContext                           │
│ TCF                                      │
│ Dispatcher Infrastructure                │
│ Timeout / Worker                         │
│ Transaction Coordination                 │
│ Error / Response                         │
│ Logging                                  │
│ Security Integration                     │
│                                          │
└───────────────────┬──────────────────────┘
                    │ controls
                    ▼
┌──────────────── BUSINESS ────────────────┐
│                                          │
│ "무슨 업무를 수행할까?"                 │
│                                          │
│ Handler                                  │
│ Facade                                   │
│ Service                                  │
│ Rule [selective/proposed]                │
│ DAO                                      │
│ Mapper / SQL                             │
│                                          │
└──────────────────────────────────────────┘
```

### Core Rule

Framework는 **실행방법**, Business는 **업무의미**를 소유한다.

---

# 10. L2/L3 — pdmg-service Package Architecture

## FIG-03-11. Current Business Package

```text
nhnis.mg.co.a
│
├─ entry
│   ├─ handler
│   └─ aspect
│
├─ application
│   ├─ controller
│   ├─ facade
│   ├─ service
│   └─ dto
│
└─ persistence
    └─ dao
```

### Current Business Axis

```text
MG
 ↓
CO
 ↓
A
 ↓
nhnis.mg.co.a
```

과거 축 없는 `nhnis.mg.application.*`를 Current 표준으로 사용하지 않는다.

---

# 11. L2/L5 — Module / Base Package Map

## FIG-03-12. Base Packages

```text
pdmg-service
└─ nhnis.mg.co.a.*

pdmg-fw
├─ nhnis.fw.*
└─ com.ims.superspring.*

pdmg-ui
└─ nhnis.mg.ui.*

pdmg-jwt
└─ nhnis.mg.jw.a.*

Mapper Resource
└─ rdw.mg.co.a/

pdmg-om
└─ [UNKNOWN]
```

### Rule

Package는 Module 이름보다 **책임과 업무축**을 표현해야 한다.

---

# 12. L3/L5 — Program Source Tree

## FIG-03-13. `mgcoa9000` Source Drill-down

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
├─ application/dto
│   ├─ mgcoa9000S0DTOin.java
│   ├─ mgcoa9000S0DTOout.java
│   ├─ mgcoa9000C0DTOin.java
│   ├─ mgcoa9000U0DTOin.java
│   └─ mgcoa9000D0DTOin.java
│
└─ persistence/dao
    └─ mgcoa9000DAO.java

Resource
└─ rdw.mg.co.a/
    └─ mgcoa9000-ORA.xml
```

이 Tree는 Program/ServiceId/Class/Mapper가 동일 업무축을 공유하는 대표 Evidence다.

---

# 13. L3 — Application Layer Model

## FIG-03-14. Layer Architecture

```text
External Request
      ↓
┌───────────────────────┐
│ Inbound Adapter       │
│ Handler / Controller  │
└───────────┬───────────┘
            ↓
┌───────────────────────┐
│ Application Facade    │
│ Use Case Boundary     │
└───────────┬───────────┘
            ↓
┌───────────────────────┐
│ Business Service      │
│ Business Logic        │
└───────────┬───────────┘
            ↓
┌───────────────────────┐
│ Persistence           │
│ DAO / Mapper          │
└───────────┬───────────┘
            ↓
           DB
```

### Layer vs Component

```text
Layer
= 책임의 종류

Component
= 실제 Class/Bean

예:
Application Layer
  └─ mgcoa9000Facade
```

---

# 14. L3/L4 — Handler와 Controller = Inbound Adapter

## FIG-03-15. Two Entry Adapters

```text
TCF ON
ServiceId + dtoBody
       ↓
TransactionHandler
       ↓
Business Facade

TCF OFF
HTTP URL + typed DTO
       ↓
Business Controller
       ↓
Business Facade   [Target]
```

### 공통 목적

둘 다:

```text
External Protocol
       ↓
Application Use Case
```

로 변환하는 Adapter다.

### 차이

```text
Handler
= ServiceId Protocol dependent

Controller
= Spring MVC / HTTP dependent
```

---

# 15. L3/L5 — TransactionHandler Contract

## FIG-03-16. Framework Contract

```text
pdmg-fw
TransactionHandler
│
├─ serviceId()
├─ serviceIds()
└─ handle(
      Object dtoBody,
      TransactionContext context
   )
```

업무 Handler는 이 Contract를 구현한다.

### 책임

```text
serviceIds()
= 자신이 처리할 거래 ID 선언

handle()
= ServiceId → Facade Method 연결
```

### 금지

```text
handle()
→ DAO
→ SQL
→ 복잡한 업무조정

X
```

---

# 16. L3/L5 — Handler Registry

## FIG-03-17. Current Handler Registry — 13 ServiceIds

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

### Current Evidence

```text
Current Handler Source
= 13

Past 일부 문서
= 8

판정
= [DRIFT / SUPERSEDED CANDIDATE]
```

---

# 17. L3/L4 — Handler 등록과 실행분기

## FIG-03-18. Two-stage Consistency

```text
Stage 1
handler.serviceIds()
       ↓
Dispatcher Registry
       ↓
serviceId → handler

AND

Stage 2
handler.handle()
       ↓
switch(context.getServiceId())
       ↓
Facade Method
```

### Failure Cases

```text
Registry에 있고 switch 없음
  ↓
Runtime Default Error

switch에 있고 Registry 없음
  ↓
Dispatcher Not Found

Duplicate Registry
  ↓
Startup Failure
```

따라서 ServiceId Registry와 Handler Branch는 하나의 Test 대상이다.

---

# 18. L3/L4 — Facade Boundary

## FIG-03-19. Application Facade

```text
Inbound Adapter
Handler / Controller
        ↓
┌────────────────────────────┐
│ Business Facade            │
│                            │
│ Use Case Entry             │
│ DTO Conversion             │
│ Service Coordination       │
│ Transaction Annotation     │
└─────────────┬──────────────┘
              ↓
          Service
```

### Facade의 의미

Facade는 단순 Wrapper가 아니라 다음의 **Application Use Case Boundary**다.

```text
Protocol-neutral Use Case Entry
+
Transaction Declaration
+
Business Service Coordination
```

TCF ON/OFF가 같은 Facade를 공유하면 Runtime Entry가 달라도 업무경계가 유지된다.

---

# 19. L3 — Service Boundary

## FIG-03-20. Business Service

```text
Facade
  ↓
Service
  ├─ Business Validation
  ├─ Business Calculation
  ├─ Business State Change
  └─ DAO Coordination
  ↓
DAO
```

### Service가 소유해야 하는 것

```text
업무 규칙
업무 상태변경
업무 흐름
Persistence 호출
```

### Service가 소유하지 말아야 하는 것

```text
HTTP Response 생성
Servlet Request 파싱
JWT Signature 검증
ThreadLocal lifecycle
```

---

# 20. L3 — Rule Layer

## FIG-03-21. AS-IS vs Rule Candidate

```text
Current General Pattern

Facade
  ↓
Service
  ↓
DAO


Target Candidate
복잡 Rule이 필요한 경우

Facade
  ↓
Service
  ↓
Rule
  ↓
DAO / External
```

### 판정

```text
Rule Layer
= 현재 전수 AS-IS 아님
= [OPEN / SELECTIVE PROPOSED]
```

Rule은 복잡도/재사용/독립테스트 필요성이 있는 업무에 선택적으로 도입하는 방향이 적절하다.

---

# 21. L3/L5 — DAO / Mapper Architecture

## FIG-03-22. Persistence Boundary

```text
Service
  ↓
DAO
  ↓
SqlSessionTemplate
  ↓
Mapper XML
  ↓
SqlId
  ↓
SQL
  ↓
DB
```

대표 Mapping:

```text
Java
nhnis/mg/co/a/persistence/dao/
└─ mgcoa9000DAO.java
       │ namespace
       ▼
Resource
rdw.mg.co.a/
└─ mgcoa9000-ORA.xml
```

### Rule

DAO와 Mapper Namespace/SqlId는 자동정합 검증대상이다.

---

# 22. L3/L5 — DTO Architecture

## FIG-03-23. DTO Direction

```text
External
  ↓
DTOin
  ↓
Handler / Controller
  ↓
Facade / Service
  ↓
DTOout
  ↓
Response
```

대표:

```text
mgcoa9000S0DTOin
mgcoa9000S0DTOout
mgcoa9000C0DTOin
mgcoa9000U0DTOin
mgcoa9000D0DTOin
```

### Rule

DTO는 거래 Contract를 표현하되 Entity/DB Object와 동일시하지 않는다.

---

# 23. L2/L5 — Business Naming Axis

## FIG-03-24. Business → Java → Mapper

```text
Business
MG / CO / A
   │
   ├────────► Java Package
   │          nhnis.mg.co.a
   │
   ├────────► Mapper Root
   │          rdw.mg.co.a
   │
   └────────► Program Prefix
              mgcoa
```

이 축은 Application Architecture의 중요한 Traceability Backbone이다.

---

# 24. L3/L5 — ServiceId Anatomy

## FIG-03-25. ServiceId

```text
mg | co | a | 9001 | S | 0
│    │    │    │      │   │
│    │    │    │      │   └─ Sequence
│    │    │    │      └──── Transaction Type
│    │    │    └─────────── Program Number
│    │    └──────────────── Function
│    └───────────────────── Business
└────────────────────────── Application/Major
```

Program:

```text
mgcoa9001
= 9 chars
```

ServiceId:

```text
mgcoa9001S0
= 11 chars
```

---

# 25. L3/L5 — Program / Class Stem

## FIG-03-26. Program Stem

```text
Program
mgcoa9001
    │
    ├─ Handler   mgcoa9001Handler
    ├─ Controller mgcoa9001Controller
    ├─ Facade    mgcoa9001Facade
    ├─ Service   mgcoa9001Service
    ├─ DAO       mgcoa9001DAO
    ├─ DTO       mgcoa9001S0DTOin/out
    └─ Mapper    mgcoa9001-ORA.xml
```

### 주의

모든 Program이 완전한 1:1 Stem을 지키는지는 전수 Scanner로 확인해야 한다.

---

# 26. L2~L4 — TCF ON Application Structure

## FIG-03-27. TCF ON

```text
HTTP
 ↓
OnlineTransactionController
 ↓
TcfFacade
 ↓
TransactionDispatcher
 ↓ serviceId lookup
TransactionHandler
 ↓ serviceId branch
Business Facade
 ↓
Service
 ↓
DAO
```

### Handler 6개 Current Pattern

현재 분석된 6개 Handler는 **대응 Facade를 생성자 주입**하고 ServiceId를 Facade Method로 분기한다.

```text
Handler
→ Facade

[AS-IS / GOOD PATTERN]
```

---

# 27. L2~L4 — TCF OFF Application Structure

## FIG-03-28. TCF OFF

```text
HTTP
 ↓
Business Controller
 ↓
Typed DTO Binding
 ↓
Facade / Service
 ↓
DAO
```

### Current Drift

현재 OFF Controller 6개 중:

```text
mgcoa9100Controller
→ Facade

나머지 5개
→ Service Direct
```

으로 분석된다.

또 일부 Controller에는 업무 개별 선후처리를 Controller에 둘 수 있는 것으로 오해될 여지가 있는 주석이 존재한다.

---

# 28. L2/L4 — TCF ON vs OFF

## FIG-03-29. Entry Adapter Comparison

```text
TCF ON
ServiceId
  ↓
Common Controller
  ↓
Dispatcher
  ↓
Handler
  ↓
Facade
  ↓
Service

          vs

TCF OFF
URL
  ↓
Business Controller
  ↓
Service Direct [AS-IS 일부]
  ↓
DAO
```

### GAP

```text
ON
Business Core Entry = Facade

OFF 일부
Business Core Entry = Service

→ Boundary Drift
```

이 차이는 Transaction/Validation/Logging/업무선후처리의 일관성에도 영향을 줄 수 있다.

---

# 29. L2/L3 — Common Business Core Target

## FIG-03-30. Target Alignment

```text
TCF ON
Handler
   │
   └──────────┐
              ▼
          Business Facade
              │
              ▼
            Service
              │
              ▼
             DAO

TCF OFF
Controller
   │
   └──────────┘
```

### Target Rule

```text
Inbound Adapter는 달라도
Application Use Case Boundary는 동일
```

즉:

```text
Handler → Facade
Controller → Facade
```

를 주안으로 한다.

---

# 30. L3 — Dependency Direction

## FIG-03-31. Allowed Dependency

```text
Inbound Adapter
      ↓
Facade
      ↓
Service
      ↓
Rule [optional]
      ↓
DAO
      ↓
Mapper
      ↓
DB
```

### 의존방향 원칙

```text
상위 책임
→ 하위 책임

하위 책임
-X→ 상위 Adapter
```

예:

```text
DAO
-X→ Controller

Service
-X→ Servlet Request
```

---

# 31. L3 — Forbidden Dependencies

## FIG-03-32. Forbidden Map

```text
Controller ─────► DAO
           X

Controller ─────► Mapper
           X

Handler ────────► DAO
        X

Handler ────────► SQL
        X

Application A ──► Application B DAO
               X

pdmg-fw ────────► 특정 고객 업무 Service
         X
```

### 이유

금지하는 목적은 클래스 수를 늘리는 것이 아니라:

```text
Responsibility Isolation
+
Change Isolation
+
Testability
+
TCF ON/OFF Consistency
```

를 확보하는 것이다.

---

# 32. L2/L3 — Framework Contract와 Business Port

## FIG-03-33. Framework-to-Business Contract

```text
Framework
TransactionDispatcher
       ↓
TransactionHandler Interface
       ↓
Business Handler
       ↓
Business Facade
```

Framework가 알아야 하는 것:

```text
ServiceId
TransactionHandler
TransactionContext
Result / Exception
```

Framework가 알면 안 되는 것:

```text
Customer DAO
Product SQL
Campaign Rule
```

---

# 33. L3 — Application / Data Boundary

## FIG-03-34. Data Access Responsibility

```text
Application
Facade / Service
      ↓
Persistence Port
DAO
      ↓
Mapper
      ↓
DB
```

Application Architecture에서 DB는 Component 내부 구현이 아니라 **외부 Data Boundary**로 취급한다.

따라서 Application Component는 Table을 직접 소유하는 것이 아니라 Data Architecture의 Ownership/Contract와 연계한다.

---

# 34. L3/L4 — Transaction Position

## FIG-03-35. Application Component in Transaction

```text
Worker
  ↓
TransactionTemplate BEGIN
  ↓
Dispatcher
  ↓
Handler
  ↓
Facade @Transactional(REQUIRED)
  ↓
Service
  ↓
DAO / Mapper
  ↓
COMMIT / ROLLBACK
```

### 중요한 해석

```text
Handler도
TransactionTemplate 내부 실행

BUT

실질 DB Work
= Facade/Service 이후
```

Facade의 `@Transactional(REQUIRED)`는 외부 Worker Transaction이 존재하면 해당 TX에 참여한다.

---

# 35. L3/L4 — Error Boundary

## FIG-03-36. Component Error Propagation

```text
DAO / Mapper Error
      ↑
Service
      ↑
Facade
      ↑
Handler / Controller
      ↑
Framework Error Handler
      ↑
Standard Error Response
```

### Rule

업무 Component는 예외를 임의로 삼켜 Framework의 Rollback/Error Contract를 깨지 않는다.

```text
Catch
  ↓
Log only
  ↓
Return success

X
```

---

# 36. L2/L3 — Security Boundary in Application

## FIG-03-37. Trusted Principal to Business

```text
SecurityFilterChain
       ↓
Verified Principal
       ↓
Framework / Context
       ↓
Inbound Adapter
       ↓
Facade / Service
       ↓
Authorization / Business Logic
```

Application 계층은 JWT Signature 자체를 재구현하지 않는다.

```text
Security Infrastructure
→ Identity Verification

Application
→ Business Authorization
```

---

# 37. L2/L5 — Observability within Components

## FIG-03-38. Trace Across Application

```text
GUID
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
SqlId
 ↓
ErrorCode / Result
```

Target에서는 다음까지 확장한다.

```text
DeploymentId
+
JVM / Host
+
Artifact Hash
```

Component 이름 자체보다 Stable Identifier가 Runtime Evidence의 중심이다.

---

# 38. L2/L5 — Build / Artifact Handoff

## FIG-03-39. Module to Artifact

```text
Source Module
  ↓
Gradle Build
  ↓
Module Output
  ↓
WAR / JAR / Static Artifact
  ↓
Deployment
  ↓
Runtime Process
```

### Important

```text
Build Artifact
≠ Runtime Process

WAR
≠ JVM

JVM
≠ Application Responsibility
```

정확한 Artifact 이름/배치 Mapping은 05/14장에서 확정한다.

---

# 39. L3/L5 — Application Architecture Rule Set

## FIG-03-40. Rule Catalog

```text
R-APP-01
Module ≠ Process

R-APP-02
pdmg-fw ≠ Remote Business Server

R-APP-03
Inbound Adapter → Facade

R-APP-04
Handler → DAO = Forbidden

R-APP-05
Controller → DAO = Forbidden

R-APP-06
Business Service → Servlet API = Forbidden

R-APP-07
DAO ↔ Mapper Namespace Consistent

R-APP-08
ServiceId Unique

R-APP-09
Registry ↔ handle Branch Consistent

R-APP-10
PDMG AS-IS ≠ NSIGHT TO-BE
```

추가 후보:

```text
R-APP-11
Program/Package/Mapper Business Axis Consistent

R-APP-12
Rule Layer는 Evidence 없이 AS-IS로 표시하지 않음
```

---

# 40. L5 — Static Conformance Scan

## FIG-03-41. Source Scanner

```text
Source Tree
   ↓
Package Scan
   ↓
Class Suffix Scan
   ↓
Dependency Scan
   ↓
ServiceId Scan
   ↓
Mapper Scan
   ↓
Rule Result
```

### 자동검증 예

```text
Handler imports DAO?
→ FAIL

Controller imports DAO?
→ FAIL

ServiceId duplicate?
→ FAIL

Mapper namespace mismatch?
→ FAIL

Business Package Axis mismatch?
→ FAIL
```

---

# 41. L5 — Unit / Context / Architecture Test

## FIG-03-42. Test Pyramid

```text
Unit Test
  ↓
Handler Branch Test
  ↓
Facade / Service Test
  ↓
Dispatcher Registry Test
  ↓
Spring Context Test
  ↓
Architecture Dependency Test
  ↓
Runtime Integration Test
```

### 필수 Test

1. 모든 `serviceIds()`가 `handle()` Branch로 실행되는가.
2. Duplicate ServiceId는 Startup에서 실패하는가.
3. TCF ON Handler가 Facade만 의존하는가.
4. TCF OFF Controller가 Target Facade를 호출하는가.
5. `pdmg-fw` Bean이 `pdmg-service` Context에 정상 등록되는가.
6. Mapper Namespace와 DAO가 일치하는가.
7. Program/ServiceId/Package 축이 일치하는가.

---

# 42. Application Component Responsibility Matrix

## TEXT ARCHITECTURE — Responsibility

```text
Controller / Handler
  = Adapter

Facade
  = Use Case

Service
  = Business

Rule
  = Complex Reusable Decision [optional]

DAO
  = Persistence Access

Mapper
  = SQL Mapping
```

| Component | 입력 | 출력 | 책임 | 하지 말아야 할 일 |
|---|---|---|---|---|
| Handler | dtoBody, context | Facade result | ServiceId Adapter | DAO/SQL |
| Controller | HTTP/DTO | Facade result | HTTP Adapter | Business orchestration |
| Facade | DTO/Object | DTO/result | Use Case/Tx declaration | SQL 직접수행 |
| Service | Business input | Business result | Business Logic | HTTP 처리 |
| Rule | Domain input | Rule result | 복잡 결정 | 전수 강제 |
| DAO | Persistence input | Data result | DB access | UI/HTTP |
| Mapper | Parameter | SQL result | SQL mapping | Business Rule |

---

# 43. Current Handler / Controller AS-IS Matrix

## TEXT ARCHITECTURE — Entry Drift

```text
Current Handlers
6
  ↓
모두 Facade 의존
  ↓
[GOOD]

Current OFF Controllers
6
  ↓
1 Facade
5 Service Direct
  ↓
[GAP]
```

| Entry | Count/Pattern | Current | Target |
|---|---|---|---|
| TCF Handler | 6 Handler / Facade 의존 | `[AS-IS]` | 유지 |
| OFF Controller | 1 Facade | `[AS-IS]` | 유지 |
| OFF Controller | 5 Service Direct | `[GAP]` | Facade 정렬 |
| Controller 업무선후처리 | 일부 주석/가능성 | `[RISK]` | Framework/Business 책임 재배치 |

---

# 44. Current GAP Map

## FIG-03-43. Application GAP

```text
PDMG Application
│
├─ pdmg-om
│   └─ Current implementation
│      [UNKNOWN]
│
├─ TCF OFF
│   └─ Controller → Service Direct
│      [GAP]
│
├─ Rule Layer
│   └─ Universal AS-IS 아님
│      [OPEN]
│
├─ ServiceId
│   └─ UI Catalog ↔ Backend Registry
│      [GAP]
│
├─ Handler Contract
│   └─ Dispatcher serviceId vs Context serviceId
│      mismatch defense
│      [PROPOSED]
│
├─ Naming
│   └─ Full 1:1 Class Stem
│      [VERIFY]
│
├─ Data
│   └─ RDW/ADW Mapper usage full inventory
│      [OPEN]
│
└─ Deployment
    └─ Module/Artifact/Process mapping
       [OPEN]
```

---

# 45. Architecture Decision Map

## FIG-03-44. Application Decisions

```text
ADR-TASK-003
PDMG ↔ NSIGHT Mapping
      ↓

ADR-TASK-004
Common Business Core
      ↓

ADR-TASK-005
TCF ON/OFF Policy
      ↓

ADR-TASK-006
Rule Layer
      ↓

ADR-TASK-007
Standard Message
      ↓

ADR-TASK-008
Error Standard
```

### 본 장 핵심 권고

```text
주안
Handler / Controller
       ↓
Common Facade
       ↓
Service
       ↓
DAO
```

대안:

```text
ON → Facade
OFF → Service Direct

[유지보수/정합성 측면 열위]
```

---

# 46. AS-IS vs NSIGHT Target Alignment

## TEXT ARCHITECTURE — Alignment

```text
PDMG AS-IS
Handler → Facade → Service → DAO
        │
        ├─ 좋은 Reference
        │
        ▼
NSIGHT Target Candidate
Inbound Adapter → Facade → Service → Rule? → DAO

BUT

PDMG AS-IS
TCF OFF Controller → Service Direct
        ↓
NSIGHT Target
Controller → Facade

= GAP
```

### 승격 원칙

PDMG Current Pattern을 Target으로 승격할 때는:

```text
Evidence
+
NFR Fit
+
Security
+
Operations
+
Conformance Rule
```

을 확인한다.

---

# 47. Application Security Review

## TEXT ARCHITECTURE — Application Security

```text
Verified Principal
       ↓
Inbound Adapter
       ↓
Facade
       ↓
Service
       ↓
Business Authorization
       ↓
DAO
```

Application Layer에서 확인할 질문:

```text
Q1. Controller/Handler가 Client userId를 그대로 신뢰하는가?
Q2. ServiceId에 대한 권한통제가 존재하는가?
Q3. Sensitive DTO가 로그에 그대로 출력되는가?
Q4. DAO 권한이 업무 Scope보다 넓지 않은가?
Q5. Error가 내부 Class/SQL 정보를 노출하는가?
```

상세 JWT/Identity는 11장으로 넘긴다.

---

# 48. Application Performance Review

## TEXT ARCHITECTURE — Performance Impact

```text
Inbound Adapter
  ↓
Facade
  ↓
Service
  ↓
DAO
  ↓
SQL

성능 핵심
= Layer 수가 아니라
DB / External / Serialization / Lock / Pool
```

따라서 Facade Layer를 제거해 Class 하나를 줄이는 것보다:

```text
Business Boundary Consistency
```

가 더 중요하다.

성능 문제는 측정으로 판단하며 계층 우회를 정당화하지 않는다.

---

# 49. Application Availability Review

## TEXT ARCHITECTURE — Availability Boundary

```text
Application Component Failure
      ↓
Exception
      ↓
Framework Error Mapping
      ↓
Rollback
      ↓
Response
```

Application Layer는 HA 자체를 구현하지 않지만:

```text
Statelessness
Idempotency
Externalized State
Clean Transaction Boundary
```

를 통해 Scale-out/HA를 지원해야 한다.

---

# 50. Application Observability Review

## TEXT ARCHITECTURE — Component Evidence

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
SqlId
 ↓
Elapsed / Error
```

운영자가 다음 질문에 답할 수 있어야 한다.

```text
어떤 ServiceId인가?
어떤 Handler가 선택됐는가?
어떤 Facade/Service인가?
어떤 SqlId에서 지연됐는가?
어떤 ErrorCode로 끝났는가?
```

---

# 51. GAP Register

## TEXT ARCHITECTURE — GAP Lifecycle

```text
Expected Application Architecture
       ↓ compare
Current Source
       ↓
GAP
       ↓
Owner / Test / ADR
       ↓
Close
```

| GAP ID | GAP | 중요도 | 후속 |
|---|---|---|---|
| GAP-APP-01 | OFF Controller 5개 Service Direct | High | Facade 정렬 |
| GAP-APP-02 | Rule Layer 전수 AS-IS 아님 | Medium | 선택기준 ADR |
| GAP-APP-03 | UI Catalog ↔ 13 Handler Registry | High | 자동비교 |
| GAP-APP-04 | Dispatcher ID ↔ Context ID mismatch defense | High | Framework 보완 |
| GAP-APP-05 | 모든 Program Class Stem 전수검증 | Medium | Scanner |
| GAP-APP-06 | pdmg-om Source/Runtime | Medium/High | Scope 확보 |
| GAP-APP-07 | Module→Artifact→Process Mapping | High | 05/14장 |
| GAP-APP-08 | Controller 업무선후처리 가능성 | High | 책임정리 |
| GAP-APP-09 | TCF OFF 공통 정책 적용 차이 | High | 08/09장 |
| GAP-APP-10 | Architecture Tests CI 적용 | High | 14/17장 |

---

# 52. Risk Register

## TEXT ARCHITECTURE — Application Risk

```text
Layer Bypass
   ↓
Boundary Drift
   ↓
Different Runtime Behavior
   ↓
Maintenance / Transaction / Security Risk
```

| Risk ID | Risk | 영향 |
|---|---|---|
| RISK-APP-01 | Controller→Service direct 장기화 | ON/OFF Use Case Drift |
| RISK-APP-02 | Handler에 업무로직 축적 | Framework Adapter 비대화 |
| RISK-APP-03 | DAO/Mapper 직접호출 | Transaction/Rule 우회 |
| RISK-APP-04 | Rule Layer 무조건 도입 | 불필요 복잡도 |
| RISK-APP-05 | Module=Server 오해 | 잘못된 배치/네트워크 |
| RISK-APP-06 | ServiceId Registry/Branch 불일치 | Runtime Failure |
| RISK-APP-07 | DTO 전체 로그 | 민감정보 노출 |
| RISK-APP-08 | Package/Naming Drift | Traceability 단절 |
| RISK-APP-09 | pdmg-om 일반론 채움 | False Architecture |
| RISK-APP-10 | AS-IS 자동 Target 승격 | 기술부채 고착 |

---

# 53. ADR Candidates

## TEXT ARCHITECTURE — ADR Flow

```text
Application GAP
      ↓
Decision Question
      ↓
주안 / 대안
      ↓
Test / Evidence
      ↓
ADR
      ↓
Source Rule
```

대표 후보:

```text
ADR-APP-01 Common Business Facade
ADR-APP-02 TCF ON/OFF Support Policy
ADR-APP-03 Rule Layer Adoption Criteria
ADR-APP-04 ServiceId Registry SSOT
ADR-APP-05 Handler/Context ServiceId Consistency
ADR-APP-06 Package/Program Naming Conformance
ADR-APP-07 pdmg-om Current Scope
ADR-APP-08 Application Dependency CI Gate
```

기존 `ADR-TASK-*`가 존재하는 항목은 새 ADR을 중복 생성하지 않고 연결한다.

---

# 54. Verification Checklist

## TEXT ARCHITECTURE — Application Checklist

```text
Module?
 ↓
Process?
 ↓
Spring Context?
 ↓
Package?
 ↓
Layer?
 ↓
Component?
 ↓
ServiceId?
 ↓
Dependency?
 ↓
TCF ON/OFF?
 ↓
PASS / GAP
```

Checklist:

```text
[ ] 5개 Module 상태가 정의되었는가
[ ] pdmg-fw를 Remote Server로 표현하지 않았는가
[ ] scanBasePackages와 Spring Context를 구분했는가
[ ] nhnis.mg.co.a 업무축을 유지했는가
[ ] rdw.mg.co.a Mapper 축을 유지했는가
[ ] Handler는 Facade만 호출하는가
[ ] OFF Controller도 Facade를 호출하는가
[ ] Handler Registry는 13개 Current Source를 기준으로 하는가
[ ] serviceIds()와 handle()이 정합하는가
[ ] Duplicate ServiceId가 차단되는가
[ ] DAO/Mapper 우회 의존이 없는가
[ ] Rule Layer를 AS-IS로 과장하지 않았는가
[ ] DTO/Log 민감정보 위험을 확인했는가
[ ] Module→Artifact→Runtime Trace가 가능한가
[ ] pdmg-om Unknown이 보존되는가
```

---

# 55. Architecture PASS / Current Conformance

## FIG-03-45. 03장 판정

```text
Application Architecture Definition
        ↓
PASS

Current PDMG Conformance
        ↓
PARTIAL / CONDITIONAL

주요 이유

TCF ON Handler 구조
= 좋은 정합

TCF OFF Controller
= 5개 Service Direct GAP

pdmg-om
= UNKNOWN

Registry / Deployment Automation
= 보완 필요
```

### Summary Matrix

| 영역 | Architecture Definition | Current PDMG | 판정 |
|---|---|---|---|
| 5 Module | 정의완료 | 4개 Confirmed + OM Unknown | CONDITIONAL |
| Module/Process | 정의완료 | 정합 | PASS |
| Spring Context | 정의완료 | Source 근거 | PASS |
| Framework/Business | 정의완료 | 대체로 정합 | PASS |
| Handler | Facade Adapter | 6개 정합 | PASS |
| OFF Controller | Facade Adapter | 5개 Service Direct | GAP |
| Facade | Use Case Boundary | 확인 | PASS |
| Service | Business Logic | 확인 | PASS |
| Rule | Selective | 전수 미존재 | PASS/OPEN |
| DAO/Mapper | Persistence | 확인 | PASS |
| ServiceId | Registry/Unique | 13 Current | PASS/PARTIAL |
| Naming | Business Axis | 강한 Pattern | PASS/PARTIAL |
| pdmg-om | Operations Boundary | Unknown | OPEN |
| CI Conformance | 자동검증 | 미완료 | GAP |

### Chapter 판정

```text
Architecture Definition     : PASS
Current PDMG Conformance    : PARTIAL / CONDITIONAL
Runtime Evidence Coverage   : MEDIUM
Critical Application GAP    : TCF OFF Business Core Drift
OPEN Major Area             : pdmg-om / Rule adoption / deployment mapping
```

---

# 56. PASS 전환조건

## TEXT ARCHITECTURE — Full Conformance

```text
Current Application
       ↓
OFF Controller 정렬
       ↓
Registry / Branch 자동검증
       ↓
Naming / Mapper Scan
       ↓
Module / Artifact Mapping
       ↓
OM Scope
       ↓
Architecture Test CI
       ↓
PDMG Application Conformance PASS
```

전환조건:

1. TCF OFF Controller 5개의 Service Direct 호출을 Facade 경계로 정렬하거나 예외 ADR 승인.
2. Handler `serviceIds()`와 `handle()` Branch 자동 Test.
3. Dispatcher 입력 `serviceId`와 `TransactionContext.serviceId` 정합검증.
4. UI Transaction Catalog와 Backend 13 ServiceId Registry 자동 Diff.
5. Program/Package/Handler/Facade/Service/DAO/Mapper Stem 전수 Source Scan.
6. Rule Layer의 적용기준을 확정하고 AS-IS/TO-BE를 분리.
7. Controller/Handler에서 DAO/Mapper 직접의존 0건 검증.
8. DTO/Request/Response Log Masking 정책 정합.
9. Module→Artifact→Process/JVM 배치 Mapping.
10. `pdmg-om` Current Scope 확정 또는 Current PDMG Scope에서 명시적 제외.
11. Dependency/Registry/Naming Rule을 CI Gate에 적용.
12. TCF ON/OFF 동일 Use Case 결과/Transaction 정책 Integration Test.

---

# 57. 이 장에서 확정하지 않는 것

## TEXT ARCHITECTURE — Deferred Detail

```text
Logical Technical Node
→ 04

Actual Host / VM / JVM / WAR
→ 05

External Interface Contract
→ 06

Data Ownership / Table
→ 07

Filter / TCF Mechanism
→ 08

Full Runtime Sequence
→ 09

TX / Timeout
→ 10

JWT / Session
→ 11

Message / Error / Logging
→ 12

OM / DevOps
→ 14
```

03장은 **Application Responsibility와 Source Structure를 확정**하는 장이다.

---

# 58. 03장 Final Application Map

## TEXT ARCHITECTURE — Final Map

```text
PDMG
│
├─ pdmg-ui
│   └─ Presentation / Request
│
├─ pdmg-jwt
│   └─ Authentication / Token
│
├─ pdmg-service
│   │
│   ├─ pdmg-fw
│   │   └─ Runtime Framework
│   │
│   └─ Business
│       │
│       ├─ Handler / Controller
│       │       ↓
│       ├─ Facade
│       │       ↓
│       ├─ Service
│       │       ↓
│       ├─ Rule [optional]
│       │       ↓
│       ├─ DAO
│       │       ↓
│       └─ Mapper
│               ↓
│              DB
│
└─ pdmg-om
    └─ [UNKNOWN]

Trace Backbone
MG/CO/A
  ↓
Program
  ↓
ServiceId
  ↓
Component
  ↓
Mapper / SqlId
  ↓
Runtime Evidence
```

---

# 59. 03장 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG Application Architecture
=
Module Responsibility
+
Build / Runtime Boundary
+
Spring Context
+
Package / Layer
+
Inbound Adapter
+
Facade / Service
+
DAO / Mapper
+
ServiceId
+
Dependency Rules
```

이 장의 핵심 결론은 다음 다섯 가지다.

```text
1.
pdmg-fw
= Framework Module
≠ Remote Business Server

2.
Handler / Controller
= Inbound Adapter

3.
Facade
= Common Use Case Boundary

4.
ServiceId
= Runtime Transaction Identity

5.
PDMG Current Pattern
= Reference
≠ 자동 NSIGHT Target
```

**03장 Architecture Definition 판정: `PASS`**

Current PDMG는 TCF ON Handler 구조와 Package/Naming/Persistence 구조는 강하게 정합하지만, TCF OFF Controller의 Service Direct 호출, OM 미확인, 자동 Conformance 부족이 남아 있어:

```text
Current PDMG Conformance
= PARTIAL / CONDITIONAL
```

로 유지한다.

---

# 60. Next Chapter Handoff

## FIG-03-46. 03 → 04

```text
03 Application / Module Architecture
"무슨 모듈과 Component가
어떤 책임을 가지는가?"
              ↓
04 Logical Technical Architecture
"그 Application 책임을 실행하기 위해
어떤 Logical Technical Node /
Runtime Capability가 필요한가?"
```

04장에서 Drill-down할 축:

```text
Application Module
       ↓
Technical Capability
       ↓
Logical Node
       ↓
Runtime Platform
       ↓
Technology Component
       ↓
Allowed / Forbidden Technical Path
```


---



<!-- ============================================================ -->
<!-- CHAPTER 04: LOGICAL TECHNICAL -->
<!-- SOURCE: 04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md -->
<!-- ============================================================ -->

# PDMG 전체 아키텍처 정의서
# 04. PDMG LOGICAL TECHNICAL ARCHITECTURE
## Zone / Technical Capability / Logical Technical Node / Runtime Type / Allowed Path
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Logical Technical Architecture**  
> 문서 ID: `PDMG-ARCH-04-LOGICAL-TECHNICAL`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 선행 장: `03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md`  
> 후속 장: `05_PDMG_PHYSICAL_INFRASTRUCTURE_ARCHITECTURE.md`  
> 핵심 원칙: **Application 책임을 곧바로 Server/VM으로 치환하지 않고, 먼저 Technical Capability와 Logical Technical Node로 변환한다.**

---

# 0. Chapter Purpose

## FIG-04-01. 04장이 답해야 하는 질문

```text
Application Module
pdmg-ui / jwt / service / fw / om
        ↓
"이 책임을 실행하려면
어떤 기술 Capability가 필요한가?"
        ↓
Technical Capability
        ↓
"어떤 Logical Technical Node가
그 Capability를 책임지는가?"
        ↓
Logical Technical Node
        ↓
"어떤 Runtime Type과
State / Scale / Failure 특성을 가지는가?"
        ↓
Runtime Characteristic
        ↓
"어떤 연결을 허용/금지할 것인가?"
        ↓
Allowed / Forbidden Path
        ↓
"어떤 Physical Resource로 내려갈 것인가?"
        ↓
05 PHYSICAL Handoff
```

04장은 **제품 선정표나 서버 사양표가 아니다.**

다음은 04장에서 정하지 않는다.

```text
Hostname
CPU
Memory
Disk
Exact Port
Exact Tomcat Version
Exact Oracle Version
Final Server Count
```

이 값은 05장 Physical Architecture 또는 Inventory/Evidence에서 결정한다.

---

# 1. Evidence Register

## FIG-04-02. Evidence Stack

```text
PDMG Application Architecture
        ↓
PDMG Runtime / Framework Evidence
        ↓
NSIGHT Logical Architecture
        ↓
Technical Architecture Definition
        ↓
Infrastructure Handoff
        ↓
Decision / PASS
```

| Evidence ID | 근거자료 | 본 장 사용 목적 | 상태 |
|---|---|---|---|
| EV-04-01 | `03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1` | Module/Application Responsibility | `[WORKING BASELINE]` |
| EV-04-02 | `02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1` | System/Trust/Data/Process Boundary | `[WORKING BASELINE]` |
| EV-04-03 | PDMG Module/Application 원본 분석 | pdmg-fw/service 동일 Runtime 가능성 | `[AS-IS REFERENCE]` |
| EV-04-04 | PDMG Online Runtime/TCF 분석 | Request/Worker/Transaction Capability | `[AS-IS REFERENCE]` |
| EV-04-05 | Transaction/Timeout/Thread/DB 분석 | Thread/Worker/Data Access Runtime | `[AS-IS REFERENCE]` |
| EV-04-06 | Security/JWT 분석 | Authentication/Verification Capability | `[AS-IS REFERENCE]` |
| EV-04-07 | NSIGHT LOGICAL 상세본 | Zone/Logical Node/Component/Allowed Path | `[TARGET REFERENCE]` |
| EV-04-08 | Technical Architecture 별첨 B | Technical Capability/Logical Node/Runtime Model | `[TARGET REFERENCE]` |
| EV-04-09 | Physical/Infrastructure 정의서 | 05장 Handoff Rule | `[PHYSICAL REFERENCE]` |
| EV-04-10 | HW/SW Matrix | Logical Role→Runtime/Product 상태 | `[WORKING INVENTORY]` |
| EV-04-11 | Architecture Decision/PASS Register | Topology/Capacity/HA/OM 결정 | `[DECISION]` |

### Evidence Rule

```text
Current Source가 증명
→ [AS-IS]

Technical Architecture 역할로 필요
→ [LOGICAL BASELINE]

제품/Host가 미확정
→ [OPEN]

NSIGHT 상위구조에만 존재
→ [TARGET REFERENCE]
```

---

# 2. Figure Plan

## FIG-04-03. Logical Technical Drill-down

```text
L0  PDMG Logical Technical Landscape
 ↓
L1  Zone / Trust / Workload
 ↓
L1  Technical Capability
 ↓
L2  Logical Technical Nodes
 ↓
L2  Application-to-Node Mapping
 ↓
L3  Runtime Characteristics
 ↓
L3  Allowed / Forbidden Connections
 ↓
L4  Request / Failure / Security Crossing
 ↓
L5  Current Implementation Projection / Evidence
 ↓
05  Physical Handoff
```

| FIG | 제목 | Level | 핵심 |
|---|---|---:|---|
| FIG-04-04 | Logical Technical Master | L0 | 전체 기술구조 |
| FIG-04-05 | Logical vs Physical | L0/L1 | 논리/물리 분리 |
| FIG-04-06 | Application vs Technical Node | L1 | 책임 차이 |
| FIG-04-07 | Module vs Logical Node | L1/L2 | Module과 Node 차이 |
| FIG-04-08 | PDMG Logical Zones | L1 | 기술 경계 |
| FIG-04-09 | Technical Capability Map | L1 | 필요 Capability |
| FIG-04-10 | Node Catalog | L1/L2 | Logical Node |
| FIG-04-11 | UI Delivery Node | L2/L3 | UI |
| FIG-04-12 | Authentication Node | L2/L3 | JWT |
| FIG-04-13 | Application Runtime Node | L2/L3 | Business |
| FIG-04-14 | Framework Runtime Capability | L2/L3 | FW |
| FIG-04-15 | Transaction/Worker Capability | L2/L4 | Thread/TX |
| FIG-04-16 | Data Access Node/Capability | L2/L3 | DB 연결 |
| FIG-04-17 | Data Service Node | L2/L3 | RDW |
| FIG-04-18 | Integration Capability | L1/L2 | External |
| FIG-04-19 | Operations Node | L2/L3 | OM/Ops |
| FIG-04-20 | Observability Capability | L1~L3 | Cross-cutting |
| FIG-04-21 | Security Capability | L1~L3 | Trust |
| FIG-04-22 | Deployment Capability | L1/L2 | Delivery |
| FIG-04-23 | Module→Node Mapping | L2 | Projection |
| FIG-04-24 | Application→Node Mapping | L2 | Responsibility |
| FIG-04-25 | Runtime Type | L2/L3 | Runtime |
| FIG-04-26 | State Model | L2/L3 | Stateful |
| FIG-04-27 | Scale Unit | L2/L3 | Scale |
| FIG-04-28 | Failure Domain | L2/L3 | 장애 |
| FIG-04-29 | Security Boundary | L2/L4 | Trust |
| FIG-04-30 | Workload Classes | L1/L2 | Online/Deep |
| FIG-04-31 | Allowed Path | L1~L3 | 정상경로 |
| FIG-04-32 | Forbidden Path | L1~L3 | 금지 |
| FIG-04-33 | Online Logical Route | L1~L4 | 온라인 |
| FIG-04-34 | Auth Logical Route | L1~L4 | 인증 |
| FIG-04-35 | Data Logical Route | L1~L4 | 데이터 |
| FIG-04-36 | External Logical Route | L1~L4 | 연계 |
| FIG-04-37 | Failure Propagation | L2/L4 | 장애전파 |
| FIG-04-38 | Timeout Logical Budget | L2/L4 | 시간경계 |
| FIG-04-39 | Current Product Projection | L5 | AS-IS 구현 |
| FIG-04-40 | Product != Component | L2/L5 | 제품 오해 |
| FIG-04-41 | Environment Axis | L1/L2 | 환경 |
| FIG-04-42 | Logical Node Attributes | L2/L5 | 관리속성 |
| FIG-04-43 | Inventory Projection | L5 | SSOT |
| FIG-04-44 | Rule Catalog | L3/L5 | Rule |
| FIG-04-45 | Test / Scan | L5 | 검증 |
| FIG-04-46 | GAP Map | L0 | GAP |
| FIG-04-47 | Decision Map | L0 | ADR |
| FIG-04-48 | PASS | L0 | 판정 |
| FIG-04-49 | Physical Handoff | L0/L1 | 05장 |

---

# 3. L0 — PDMG Logical Technical Master Architecture

## FIG-04-04. Logical Technical Master

```text
┌──────────────────────── CLIENT / CHANNEL ────────────────────────┐
│                                                                  │
│ User / Browser                                                   │
│                                                                  │
└─────────────────────────────┬────────────────────────────────────┘
                              │ HTTP
                              ▼
┌──────────────────────── UI DELIVERY ──────────────────────────────┐
│                                                                  │
│ UI Delivery Logical Node                                         │
│ - Static / Presentation                                          │
│ - Request Assembly                                               │
│ - ServiceId Call                                                 │
│                                                                  │
└─────────────────────────────┬────────────────────────────────────┘
                              │
              ┌───────────────┼────────────────┐
              │               │                │
              ▼               ▼                ▼
┌──────────────────┐ ┌──────────────────┐ ┌────────────────────────┐
│ AUTHENTICATION   │ │ APPLICATION      │ │ OPERATIONS             │
│ LOGICAL NODE     │ │ RUNTIME NODE     │ │ LOGICAL NODE           │
│                  │ │                  │ │                        │
│ Login / Token    │ │ Business Runtime │ │ [CURRENT DETAIL       │
│ JWKS / Identity  │ │                  │ │  UNKNOWN]             │
└─────────┬────────┘ └──────────┬───────┘ └────────────────────────┘
          │ Token               │
          └──────────────┬──────┘
                         ▼
              ┌───────────────────────────────┐
              │ APPLICATION RUNTIME NODE      │
              │                               │
              │ Framework Runtime Capability  │
              │ ├─ Filter / Context           │
              │ ├─ TCF / Dispatcher           │
              │ ├─ Timeout / Worker           │
              │ ├─ Transaction Control        │
              │ ├─ Error / Logging            │
              │ └─ Security Integration       │
              │                               │
              │ Business Runtime Capability   │
              │ ├─ Handler / Controller       │
              │ ├─ Facade                     │
              │ ├─ Service                    │
              │ └─ DAO                        │
              └───────────────┬───────────────┘
                              │ JDBC / Data Access
                              ▼
              ┌───────────────────────────────┐
              │ DATA SERVICE LOGICAL NODE     │
              │ RDW / DB                     │
              └───────────────────────────────┘

External Integration
= Approved API / Event / File / Data Mechanism
= Current PDMG Inventory dependent

Observability
= Cross-cutting Capability across all nodes
```

### 핵심

PDMG Logical Technical Architecture의 중심은:

```text
UI Delivery
Authentication
Application Runtime
Framework Runtime
Data Access
Data Service
External Integration
Operations / Observability
```

이다.

---

# 4. Logical Architecture가 아닌 것

## FIG-04-05. Logical ≠ Physical

```text
Logical Technical Node
= 역할 / Runtime 특성 / 실패경계 / Scale 단위

             ↓ mapping

Physical Resource
= Center / Host / VM / Appliance
```

### 금지

```text
WEB01
WAS02
10.1.1.15
8080
32C / 256G

위 값을 Logical Node 이름으로 사용
X
```

Logical Architecture는 **무엇을 해야 하는가**를 정하고 Physical은 **어디에서 수행하는가**를 정한다.

---

# 5. Application과 Logical Technical Node

## FIG-04-06. Business Responsibility vs Technical Responsibility

```text
APPLICATION ARCHITECTURE

pdmg-service
= Business Responsibility
      │
      │ requires
      ▼
LOGICAL TECHNICAL ARCHITECTURE

Application Runtime Node
├─ HTTP Runtime
├─ Framework Runtime
├─ Worker / Transaction
├─ Data Access
└─ Security Integration
```

### 공식 구분

```text
Application
= Business Responsibility

Logical Technical Node
= Runtime / Technology Responsibility

Physical Node
= Compute Resource
```

하나의 Application은 여러 Technical Capability를 필요로 할 수 있다.

---

# 6. Module과 Logical Technical Node

## FIG-04-07. Module ≠ Logical Node

```text
Build Modules
│
├─ pdmg-service ───────┐
│                      │
├─ pdmg-fw ────────────┼────► Application Runtime Logical Node
│                      │
├─ pdmg-ui ────────────┼────► UI Delivery Logical Node
│                      │
├─ pdmg-jwt ───────────┼────► Authentication Logical Node
│                      │
└─ pdmg-om ────────────┴────► Operations Node [UNKNOWN]
```

### 중요한 해석

```text
pdmg-service + pdmg-fw
→ 하나의 Application Runtime Logical Node에
  함께 Mapping 가능
```

반대로 하나의 Module이 여러 Runtime Capability에 참여할 수도 있다.

---

# 7. L1 — PDMG Logical Zone View

## FIG-04-08. PDMG 중심 Logical Zones

```text
┌──────────────── CLIENT / CHANNEL LOGICAL SPACE ────────────────┐
│ User / Browser                                                  │
└─────────────────────────┬───────────────────────────────────────┘
                          ▼
┌──────────────── ACCESS / DELIVERY LOGICAL SPACE ────────────────┐
│ UI Delivery / Traffic / Web Delivery Capability                 │
└─────────────────────────┬───────────────────────────────────────┘
                          ▼
┌──────────────── SERVICE RUNTIME LOGICAL SPACE ──────────────────┐
│ Authentication                                                  │
│ Application Runtime                                             │
│ Framework Runtime                                               │
└─────────────────────────┬───────────────────────────────────────┘
                          ▼
┌──────────────── DATA ACCESS / DATA LOGICAL SPACE ────────────────┐
│ Data Access Capability                                          │
│ RDW / DB Service                                                │
└─────────────────────────────────────────────────────────────────┘

┌──────────────── INTEGRATION LOGICAL SPACE ──────────────────────┐
│ Approved API / Event / File / CDC / ETL as required            │
│ [PDMG Current ownership depends on evidence]                     │
└─────────────────────────────────────────────────────────────────┘

┌──────────────── MANAGEMENT / OPERATIONS ────────────────────────┐
│ Monitoring / Logging / Deployment / OM                          │
└─────────────────────────────────────────────────────────────────┘
```

### 주의

이 Zone 이름은 PDMG 기술구조를 설명하기 위한 Logical View다.

전사 공식 Zone 번호/명칭은 승인된 NSIGHT Logical Baseline과 매핑하되 임의로 물리 VLAN/Subnet으로 치환하지 않는다.

---

# 8. L1 — Technical Capability Map

## FIG-04-09. PDMG Technical Capabilities

```text
PDMG Technical Capability
│
├─ Client Presentation
├─ UI Delivery
├─ Traffic / Web Access
├─ Authentication / Token
├─ Security Verification
├─ HTTP Application Runtime
├─ Framework Runtime Control
├─ ServiceId Routing
├─ Worker / Thread Isolation
├─ Transaction Control
├─ Timeout / Overload Control
├─ Business Execution
├─ Data Access / Connection Pool
├─ Database Service
├─ External Integration
├─ Error / Response Standardization
├─ Logging / Trace
├─ Monitoring / Alert
├─ Build / Artifact
├─ Deployment / Configuration
└─ HA / DR Support
```

### Capability와 제품의 차이

```text
"Application Runtime"
= Capability

"Tomcat / Spring Boot"
= 이를 구현하는 Current Technology
```

---

# 9. L1/L2 — Logical Technical Node Catalog

## FIG-04-10. Node Catalog

```text
LTN-01 UI Delivery Node
LTN-02 Authentication Node
LTN-03 Application Runtime Node
LTN-04 Data Service Node
LTN-05 External Integration Node [conditional]
LTN-06 Operations / Management Node [detail OPEN]
```

Cross-cutting Capability:

```text
Framework Runtime
Security Verification
Observability
Build / Deployment
```

### 왜 Framework를 별도 Logical Node로 고정하지 않는가

Current Evidence에서 `pdmg-fw`는 `pdmg-service`와 같은 Spring Context에 참여할 수 있다.

따라서:

```text
Framework Runtime
= 별도 Technical Capability

BUT

Framework Runtime Node
= 반드시 독립 Node 아님
```

Current PDMG Logical Baseline에서는 Application Runtime Node 내부 Capability로 본다.

---

# 10. L2/L3 — UI Delivery Logical Node

## FIG-04-11. UI Delivery Node

```text
User / Browser
      ↓
┌─────────────────────────────┐
│ LTN-01 UI Delivery          │
│                             │
│ Presentation                │
│ Static Resource             │
│ Transaction Catalog         │
│ Request Assembly            │
│ Bearer Token Propagation    │
└─────────────┬───────────────┘
              │ HTTP
              ▼
Authentication / Application
```

### Logical Attributes

```text
Role        = User Delivery
Runtime     = Web/UI Runtime
State       = Client/UI state dependent
Scale Unit  = UI Delivery Instance
Failure     = UI access unavailable
Security    = untrusted→trusted entry before server authorization
Mapping     = pdmg-ui
```

---

# 11. L2/L3 — Authentication Logical Node

## FIG-04-12. Authentication Node

```text
User / SSO Caller
        ↓
┌─────────────────────────────┐
│ LTN-02 Authentication      │
│                             │
│ Login                       │
│ SSO Validation              │
│ Token Issue                 │
│ Refresh State               │
│ JWKS                        │
└─────────────┬───────────────┘
              │ Token / Public Key
              ▼
Application Runtime
```

### Logical Attributes

```text
Role        = Identity / Token
Runtime     = Security Application Runtime
State       = Refresh / Key State 존재 가능
Scale Unit  = Auth Runtime Instance
Failure     = Login/Token Issue 영향
Security    = Critical Trust Boundary
Mapping     = pdmg-jwt
```

Critical Implementation GAP는 11장에서 상세화한다.

---

# 12. L2/L3 — Application Runtime Logical Node

## FIG-04-13. Core Application Runtime Node

```text
┌──────────────────────────────────────────────┐
│ LTN-03 APPLICATION RUNTIME                  │
│                                              │
│ HTTP / MVC Runtime                           │
│        ↓                                     │
│ Framework Runtime Control                    │
│        ↓                                     │
│ TCF / Dispatcher / Handler                   │
│        ↓                                     │
│ Facade / Service                             │
│        ↓                                     │
│ DAO / Data Access                            │
│                                              │
└───────────────────┬──────────────────────────┘
                    │
                    ▼
                 Data Node
```

### Logical Attributes

```text
Role        = Online Business Runtime
Runtime     = JVM / Spring / WAS class runtime
State       = Transaction local state / Context
Scale Unit  = Application Runtime Instance/JVM candidate
Failure     = Online transaction unavailable
Security    = Protected Business Boundary
Mapping     = pdmg-service + pdmg-fw
```

---

# 13. L2/L3 — Framework Runtime Capability

## FIG-04-14. Framework as Capability

```text
Application Runtime Node
│
├─ Framework Entry
│   ├─ Filter
│   ├─ Context
│   └─ Security Integration
│
├─ Execution Control
│   ├─ TCF
│   ├─ Dispatcher
│   ├─ Worker
│   ├─ Timeout
│   └─ Transaction
│
└─ Cross-cutting
    ├─ Error
    ├─ Logging
    └─ Response
```

### Architecture Decision

```text
pdmg-fw
= independent Build Module
= Framework Capability

Current Logical Node
= Application Runtime 안에 공존

[AS-IS]
```

독립 Node로 분리하려면 실제 별도 Process/Port/Deployment Evidence가 필요하다.

---

# 14. L2/L4 — Transaction / Worker Technical Capability

## FIG-04-15. Execution Control Capability

```text
Request Thread
      ↓
OnlineTimeoutExecutor
      ↓ submit
Worker Execution
      ↓
TransactionTemplate
      ↓
Dispatcher / Handler
      ↓
Business
      ↓
DB
```

### Logical 의미

Worker는 Module이 아니다.

```text
Worker
= Execution Capability
= Thread Pool / Isolation Mechanism
```

Transaction 역시 Logical Node가 아니다.

```text
Transaction
= Runtime Control Capability
```

이 Capability가 Application Runtime Node 안에서 실행된다.

---

# 15. L2/L3 — Data Access Capability

## FIG-04-16. Data Access Technical Layer

```text
Application Runtime
       ↓
DAO
       ↓
┌────────────────────────────┐
│ Data Access Capability     │
│                            │
│ Connection Pool            │
│ SQL Mapping                │
│ JDBC Connectivity          │
└────────────┬───────────────┘
             │
             ▼
        Data Service
```

### Current Implementation Projection

```text
Connection Pool
→ HikariCP [AS-IS]

SQL Mapping
→ MyBatis [AS-IS]

DB Connectivity
→ JDBC [AS-IS]
```

Logical Architecture에서는 제품보다 역할이 우선이다.

---

# 16. L2/L3 — Data Service Logical Node

## FIG-04-17. Data Node

```text
┌──────────────────────────────┐
│ LTN-04 DATA SERVICE          │
│                              │
│ Database Service             │
│ Transactional Data           │
│ Query / DML                  │
│ Session / Lock / SQL Runtime │
└──────────────────────────────┘
```

### Current

```text
PDMG → RDW / DB
= Strong Current Evidence
```

### Open

```text
PDMG → ADW actual direct usage
= Datasource / Mapper Inventory 필요
```

RDW/ADW 역할분리는 07장 Data Architecture에서 정밀화한다.

---

# 17. L1/L2 — External Integration Capability

## FIG-04-18. Integration Logical Boundary

```text
Application Runtime
        ↓
Approved Integration Contract
        ↓
┌─────────────────────────────┐
│ Integration Capability      │
│                             │
│ API / Service Adapter       │
│ Event Adapter               │
│ File Adapter                │
│ Data Movement Adapter       │
└──────────────┬──────────────┘
               ↓
Target System / Platform
```

### Current Status

PDMG 전체 Source에서 어떤 Integration Type이 실제로 사용되는지 전수 Inventory가 아직 필요하다.

따라서:

```text
LTN-05 External Integration Node
= [CONDITIONAL LOGICAL NODE]

특정 API/Event/File이 확인되면
→ 실제 Node/Capability Mapping
```

NSIGHT의 Event/CDC/ETL/File을 PDMG Current로 자동 포함하지 않는다.

---

# 18. L2/L3 — Operations / Management Logical Node

## FIG-04-19. Operations Node

```text
Runtime Nodes
   │
   ├─ Metric
   ├─ Log
   ├─ Trace
   ├─ Health
   └─ Control
   │
   ▼
┌───────────────────────────────┐
│ LTN-06 OPERATIONS / MGMT      │
│                               │
│ Monitoring                    │
│ Logging                       │
│ Deployment Control            │
│ Inventory                     │
│ Runbook / Alert               │
│ OM                            │
└───────────────────────────────┘
```

### Current Status

```text
Operations Capability
= Architecture에 필요

pdmg-om 실제 구현범위
= [UNKNOWN]
```

따라서 OM 기능을 Current Fact로 임의 생성하지 않는다.

---

# 19. L1~L3 — Observability Capability

## FIG-04-20. Cross-cutting Observability

```text
UI
 │
 │ GUID / ServiceId
 ▼
Authentication
 │
 ▼
Application Runtime
 │
 ├─ Handler / Service
 │
 ├─ ErrorCode
 │
 └─ SqlId
 │
 ▼
Data
 │
 ▼
Metric / Log / Trace
 │
 ▼
Operations
```

Observability는 별도 업무 Application이라기보다 **여러 Logical Node를 가로지르는 기술 Capability**다.

Current Evidence:

```text
GUID
MDC
ImageLog
Application Log
```

Target 확장:

```text
DeploymentId
Host / JVM
Artifact Hash
Metric / Trace
```

---

# 20. L1~L3 — Security Technical Capability

## FIG-04-21. Security Capability Map

```text
Client
  ↓
Authentication
  ↓
Token Issue
  ↓
Application Verification
  ↓
Trusted Principal
  ↓
Business Authorization
  ↓
Data Authorization
  ↓
Audit
```

### Security Capability

```text
Authentication
Token Management
Signature Verification
Identity Binding
Authorization
Secret / Key Management
Transport Protection
Audit
```

Security는 하나의 Node만의 기능이 아니다.

---

# 21. L1/L2 — Build / Deployment Capability

## FIG-04-22. Delivery Capability

```text
Source
  ↓
Build
  ↓
Artifact
  ↓
Config
  ↓
Deployment
  ↓
Runtime Instance
  ↓
Evidence
```

Current confirmed:

```text
Java 21
Spring Boot 3.5.14
Gradle Multi-project
```

그러나 이 제품/버전은 Logical Node 정의가 아니라 **Current Implementation Evidence**다.

---

# 22. L2 — Module → Logical Node Mapping

## FIG-04-23. Module Projection

```text
pdmg-ui
   ↓
LTN-01 UI Delivery

pdmg-jwt
   ↓
LTN-02 Authentication

pdmg-service
   ─────┐
        ├─► LTN-03 Application Runtime
pdmg-fw ─────┘
        │
        └─ Framework Runtime Capability

pdmg-om
   ↓
LTN-06 Operations
[Current Detail Unknown]
```

### 핵심

Application Module과 Logical Node의 Mapping은 **N:1 또는 1:N이 가능**하다.

---

# 23. L2 — Application Responsibility → Technical Node

## FIG-04-24. Responsibility Conversion

```text
Application Responsibility
"UI"
    ↓
Technical Need
"Presentation Runtime"
    ↓
Logical Node
"UI Delivery Node"


Application Responsibility
"Business Service"
    ↓
Technical Need
"JVM / HTTP / TX / Data Access"
    ↓
Logical Node
"Application Runtime Node"


Application Responsibility
"JWT"
    ↓
Technical Need
"Token / Key / Verification"
    ↓
Logical Node
"Authentication Node"
```

이 변환단계를 생략하고 Application을 바로 VM으로 매핑하지 않는다.

---

# 24. L2/L3 — Runtime Type Model

## FIG-04-25. Runtime Types

```text
Logical Node
│
├─ UI Delivery Runtime
├─ Authentication Runtime
├─ Business Application Runtime
├─ Database Runtime
├─ Integration Runtime [conditional]
└─ Operations Runtime
```

Runtime Type는 다음 정보를 설명한다.

```text
Execution Model
Thread Model
State
Scale Unit
Network Behavior
Failure Behavior
Security Boundary
Monitoring
```

---

# 25. L2/L3 — State Model

## FIG-04-26. State Classification

```text
UI Delivery
→ mostly stateless delivery
  client state may exist

Authentication
→ token/key/refresh state may exist

Application Runtime
→ request/context/transaction state
  long-lived business state should not reside in JVM memory by default

Data Service
→ persistent state

Operations
→ monitoring/inventory/control state
```

### 핵심

Scale-out 가능성은 **State Location**에 따라 달라진다.

```text
State in JVM
   ↓
Failover / Scale-out complexity ↑
```

---

# 26. L2/L3 — Scale Unit

## FIG-04-27. Logical Scale Unit

```text
UI Delivery
   ↓
Delivery Instance

Authentication
   ↓
Auth Instance

Application Runtime
   ↓
JVM / Runtime Instance candidate

Data
   ↓
DB Node / Service according to DB architecture

Operations
   ↓
Collector / Control Instance
```

Logical Architecture에서는 **무엇을 Scale 단위로 볼 것인지**를 정한다.

정확한 노드 수는 05/16장에서 정한다.

---

# 27. L2/L3 — Failure Domain

## FIG-04-28. Failure Domain Model

```text
UI Delivery Failure
      ↓
UI Access Impact

Authentication Failure
      ↓
Login / Token Impact

Application Runtime Failure
      ↓
Business Transaction Impact

Data Service Failure
      ↓
Query / DML Impact

Operations Failure
      ↓
Monitoring / Control Impact
```

### 설계 목표

```text
한 Node 실패
→ 다른 책임 Node까지
  불필요하게 전파하지 않음
```

---

# 28. L2/L4 — Security Boundary

## FIG-04-29. Trust Crossing

```text
Untrusted Client
      ↓
[Trust Boundary 1]
Authentication Node
      ↓
Token
      ↓
[Trust Boundary 2]
Application Runtime
      ↓
Trusted Principal
      ↓
[Trust Boundary 3]
Business Authorization
      ↓
[Trust Boundary 4]
Data Access
```

### GAP

JWT Issuer와 Verifier Algorithm/Key 정합은 Logical Security Capability가 정의되어 있어도 Current 구현이 정합하지 않으면 Implementation GAP다.

---

# 29. L1/L2 — Workload Classification

## FIG-04-30. PDMG Workload vs NSIGHT Broader Workload

```text
PDMG Core Current
│
└─ ONLINE / FAST Transaction
   ├─ HTTP Request
   ├─ Low/Bounded Latency
   ├─ Transaction
   └─ RDW/Data Access

NSIGHT Broader
│
├─ FAST
│  ├─ Event
│  └─ CDC
│
└─ DEEP
   ├─ ETL
   ├─ ADW
   ├─ BI
   └─ Batch
```

### 원칙

PDMG Online Runtime에 대량 분석/ETL/Event 처리 책임을 무조건 혼재시키지 않는다.

---

# 30. L1~L3 — Allowed Logical Path

## FIG-04-31. Allowed Path

```text
Client
  ↓
UI Delivery
  ↓
Authentication / Token
  ↓
Application Runtime
  ↓
Framework Runtime Control
  ↓
Business Execution
  ↓
Data Access
  ↓
Data Service
```

External Integration이 필요한 경우:

```text
Application Runtime
  ↓
Approved Interface Contract
  ↓
Integration Capability
  ↓
Target
```

Operations:

```text
All Runtime Nodes
  ↓
Observability
  ↓
Operations
```

---

# 31. L1~L3 — Forbidden Logical Path

## FIG-04-32. Forbidden Path

```text
Client ─────────────► Data Service
       X

UI Delivery ────────► DAO / DB
            X

External ───────────► Internal DB DML
         X

Framework ──────────► Specific Business DB Logic
          X

Analytics ──────────► Online RDW Heavy Query
          X / controlled

Module name
────────► Physical Server name
자동변환
          X
```

---

# 32. L1~L4 — Online Logical Route

## FIG-04-33. Online Route

```text
User
  ↓
UI Delivery
  ↓ HTTP
Application Runtime
  ↓
Framework Entry
  ↓
ServiceId Routing
  ↓
Worker / Transaction
  ↓
Business
  ↓
Data Access
  ↓
RDW / DB
  ↓
Response
```

이 Route는 09장의 실제 Class/Sequence보다 한 단계 추상화된 **Logical Runtime Route**다.

---

# 33. L1~L4 — Authentication Logical Route

## FIG-04-34. Auth Route

```text
User
  ↓
Authentication Node
  ↓
Identity Validation
  ↓
Access / Refresh Token
  ↓
UI / Client
  ↓
Application Runtime
  ↓
Token Verification
  ↓
Trusted Principal
```

Key/JWKS/Algorithm의 정확한 Current Gap은 11장에서 상세화한다.

---

# 34. L1~L4 — Data Logical Route

## FIG-04-35. Data Route

```text
Application Runtime
  ↓
Business Service
  ↓
Data Access Capability
  ↓
Connection Pool
  ↓
SQL Mapping
  ↓
JDBC
  ↓
Data Service
```

### Separation

```text
Business Service
≠ DB Session

DAO
≠ Database

Connection Pool
≠ DB
```

---

# 35. L1~L4 — External Integration Logical Route

## FIG-04-36. Outbound Route

```text
Business Need
   ↓
External Interaction Required?
   ↓ YES
Interface Contract
   ↓
Integration Adapter / Capability
   ↓
Target System
```

### Mechanism 선택은 06장으로 넘긴다.

```text
Immediate Result
→ API

Event
→ Event

DB Change
→ CDC

Bulk
→ ETL

File
→ MFT/FOS
```

이 항목은 현재 PDMG 구현이라고 단정하지 않는다.

---

# 36. L2/L4 — Failure Propagation

## FIG-04-37. Saturation / Failure Chain

```text
DB Slow
  ↓
Connection Pending
  ↓
Worker Block
  ↓
Worker Queue
  ↓
Request Waiting
  ↓
Timeout
  ↓
Client Error
```

또는:

```text
Authentication Down
  ↓
New Login/Token Issue Failure
  ↓
Business Entry Impact
```

Logical Node는 **Failure Domain과 Failure Propagation 경로**를 설명할 수 있어야 한다.

---

# 37. L2/L4 — Timeout Logical Budget

## FIG-04-38. Timeout Layers

```text
DB Query
   <
Worker / Transaction Deadline
   <
Server / Downstream
   <
Client
```

Current:

```text
PDMG Worker Deadline
= 5000ms [AS-IS SNAPSHOT]
```

나머지 정확한 값은 `[OPEN]`.

Logical Architecture는 숫자보다 **Timeout Ownership과 순서**를 먼저 고정한다.

---

# 38. L5 — Current Technology Projection

## FIG-04-39. Capability → Current Implementation

```text
UI Delivery
  ↓
pdmg-ui

Authentication
  ↓
pdmg-jwt / Spring Security / JWT

Application Runtime
  ↓
Java 21
Spring Boot 3.5.14
Tomcat [exact version OPEN]

Framework Runtime
  ↓
pdmg-fw
Spring Components

Data Access
  ↓
HikariCP
MyBatis
JDBC

Data Service
  ↓
Oracle / RDW
[exact version OPEN]

Build
  ↓
Gradle Multi-project
```

### 중요

이 표는 **Current Implementation Projection**이지 Logical Architecture 그 자체가 아니다.

---

# 39. L2/L5 — Technology Component ≠ Product

## FIG-04-40. Component / Product / Inventory

```text
Technology Capability
"Application Runtime"
        ↓
Technology Component
"WAS / JVM Runtime"
        ↓
Current Product
"Tomcat / Java"
        ↓
Inventory
"Exact version / Host / Port"
```

다음은 잘못된 표현이다.

```text
Logical Node = Tomcat 10.x

X
```

정확한 Version을 모르면 `[OPEN]`으로 유지한다.

---

# 40. L1/L2 — Environment Axis

## FIG-04-41. Environment vs Runtime

```text
ENVIRONMENT AXIS

Development
   ↓
Test
   ↓
Production
   ↓
DR

              ≠

RUNTIME AXIS

JDK
WAS
Spring
DB
Integration
Monitoring
```

### 핵심

```text
개발환경 / 운영환경
= Deployment Environment

실행환경
= Runtime Technology Stack
```

PDMG Logical Node는 여러 Environment에 반복 배치될 수 있다.

---

# 41. L2/L5 — Logical Node Mandatory Attributes

## FIG-04-42. Logical Node Record

```text
Logical Node
│
├─ Node ID
├─ Node Name
├─ Technical Role
├─ Application Mapping
├─ Runtime Type
├─ Inbound
├─ Outbound
├─ State
├─ Scale Unit
├─ Failure Domain
├─ Security Boundary
├─ HA Requirement
├─ Monitoring
├─ Owner
└─ Evidence Status
```

### PDMG Node Registry Draft

| Node ID | Name | Application Mapping | Runtime | State | Status |
|---|---|---|---|---|---|
| LTN-PD-01 | UI Delivery | pdmg-ui | Web/UI | Client/UI | `[BASELINE]` |
| LTN-PD-02 | Authentication | pdmg-jwt | Security App | Key/Refresh | `[BASELINE]` |
| LTN-PD-03 | Application Runtime | pdmg-service + pdmg-fw | JVM/Spring | Request/TX | `[BASELINE]` |
| LTN-PD-04 | Data Service | RDW/DB | Database | Persistent | `[BASELINE]` |
| LTN-PD-05 | Integration | Interface dependent | API/Event/File | mechanism dependent | `[CONDITIONAL]` |
| LTN-PD-06 | Operations | pdmg-om?/Ops | Control/Monitoring | operational | `[OPEN]` |

---

# 42. L5 — Logical Node → Inventory Projection

## FIG-04-43. SSOT Handoff

```text
Logical Node
   ↓
Environment
   ↓
Physical Node
   ↓
Software / Runtime
   ↓
Port / Datasource
   ↓
Artifact
   ↓
Monitoring
   ↓
Evidence
```

다음 Chain이 완성되어야 Physical/Runtime PASS가 가능하다.

```text
LTN-PD-03
→ PROD
→ WAS VM
→ JVM
→ WAR
→ Datasource
→ Metric
→ Runtime Evidence
```

현재 실제 Host/JVM/WAR Mapping은 `[OPEN/GAP]`.

---

# 43. Responsibility Matrix

## TEXT ARCHITECTURE — Logical Responsibility

```text
UI Delivery
= Presentation Runtime

Authentication
= Identity Runtime

Application Runtime
= Business Execution Runtime

Framework Runtime
= Execution Control Capability

Data Access
= Database Connectivity Capability

Data Service
= Persistent Data Runtime

Integration
= Cross-system Contract Runtime

Operations
= Control / Evidence Runtime
```

| Capability/Node | 책임 | Current PDMG Evidence | 후속 |
|---|---|---|---|
| UI Delivery | User delivery | Strong | 05 |
| Authentication | Auth/Token | Strong + Security GAP | 11 |
| Application Runtime | Online Business | Strong | 08/09 |
| Framework Runtime | TCF/Timeout/TX | Strong | 08/10 |
| Data Access | Pool/Mapper/JDBC | Strong | 07/10 |
| Data Service | RDW/DB | Strong | 07 |
| Integration | External interaction | Partial/Open | 06/13 |
| Operations | Monitoring/OM | Partial/Unknown | 14 |
| Observability | Trace/Evidence | Partial | 14/17 |

---

# 44. Architecture Rule Catalog

## FIG-04-44. Logical Technical Rules

```text
R-LT-01
Application ≠ Logical Technical Node

R-LT-02
Module ≠ Logical Technical Node

R-LT-03
Logical Node ≠ Physical Host

R-LT-04
Technology Component ≠ Product Version

R-LT-05
pdmg-fw ≠ mandatory independent Runtime Node

R-LT-06
Client → Data Direct = Forbidden

R-LT-07
External → PDMG DB Direct DML = Forbidden

R-LT-08
Application Runtime → Data through Data Access Capability

R-LT-09
Every Node defines State / Scale / Failure / Security

R-LT-10
Observability crosses every Runtime Node

R-LT-11
Unknown Host/Product/Port remains OPEN

R-LT-12
PDMG Current ≠ NSIGHT broader platform by default
```

### Additional Target Rules

```text
R-LT-13
FAST / DEEP workload shall be isolated when NFR requires

R-LT-14
Logical Node shall have Physical Mapping before go-live

R-LT-15
Critical Node shall have Monitoring / HA Requirement
```

---

# 45. L5 — Logical Conformance Test

## FIG-04-45. Test Route

```text
Application Model
  ↓
Logical Node Mapping
  ↓
Capability Coverage
  ↓
Allowed Path Check
  ↓
Forbidden Path Scan
  ↓
Runtime Config Check
  ↓
Physical Mapping Check
  ↓
PASS / GAP
```

### Test Catalog

| Test ID | 검증 |
|---|---|
| T-LT-01 | 모든 PDMG Module에 Logical Node/Capability Mapping 존재 |
| T-LT-02 | pdmg-fw를 Remote Node로 잘못 모델링하지 않음 |
| T-LT-03 | UI→DB Direct Path 없음 |
| T-LT-04 | External→DB Direct DML 없음/승인예외 |
| T-LT-05 | Application Runtime→Data Access→DB Path 유지 |
| T-LT-06 | Logical Node별 State/Scale/Failure 속성 존재 |
| T-LT-07 | Authentication/Application Trust Boundary 존재 |
| T-LT-08 | Observability Coverage 존재 |
| T-LT-09 | Logical Node→Physical Mapping 존재 여부 |
| T-LT-10 | Product/Version을 Logical Node 이름으로 사용하지 않음 |

---

# 46. Current GAP Map

## FIG-04-46. Logical Technical GAP

```text
PDMG Logical Technical
│
├─ ACCESS
│   └─ Traffic/Web exact logical/physical ownership
│      [OPEN]
│
├─ AUTH
│   ├─ RS256 issuer vs HMAC verifier
│   │  [CRITICAL GAP]
│   └─ Key/State HA characteristic
│      [GAP]
│
├─ APPLICATION
│   └─ TCF OFF Common Facade drift
│      [APPLICATION GAP]
│
├─ DATA
│   └─ RDW/ADW actual datasource mapping
│      [OPEN]
│
├─ INTEGRATION
│   └─ PDMG current API/Event/File inventory
│      [OPEN]
│
├─ OPERATIONS
│   └─ pdmg-om current technical role
│      [UNKNOWN]
│
├─ OBSERVABILITY
│   └─ DeploymentId/Host/JVM correlation
│      [GAP]
│
└─ PHYSICAL MAPPING
    └─ Node → Host/VM/JVM/WAR
       [GAP]
```

---

# 47. Risk Register

## TEXT ARCHITECTURE — Logical Technical Risk

```text
Wrong Technical Boundary
        ↓
Wrong Physical Mapping
        ↓
Shared Failure Domain
        ↓
Capacity / Security / Operations Problem
```

| Risk ID | Risk | 영향 |
|---|---|---|
| RISK-LT-01 | Application=Server로 해석 | 잘못된 물리설계 |
| RISK-LT-02 | FW 독립 Node 오해 | 불필요 Network Hop |
| RISK-LT-03 | State 위치 미정의 | Scale-out/Failover 문제 |
| RISK-LT-04 | Failure Domain 미정의 | 장애전파 |
| RISK-LT-05 | Integration Current Scope 오인 | 잘못된 책임 |
| RISK-LT-06 | Auth Key State 미정의 | 다중Instance 장애 |
| RISK-LT-07 | RDW/ADW Workload 혼재 | Online 성능 영향 |
| RISK-LT-08 | Observability Node Mapping 없음 | 장애원인 추적불가 |
| RISK-LT-09 | Logical→Physical Mapping 없음 | CMDB/배포 Drift |
| RISK-LT-10 | Product 후보를 확정제품으로 사용 | 기술표준 오류 |

---

# 48. Architecture Decision Map

## FIG-04-47. Logical Technical Decisions

```text
Logical Technical Decisions
│
├─ Application ↔ Technical Node Mapping
├─ Framework Runtime Placement
├─ Authentication State / Key Runtime
├─ Integration Capability Scope
├─ FAST / DEEP Isolation
├─ Scale Unit
├─ Failure Domain
├─ Observability Coverage
└─ Logical → Physical Mapping
```

관련 기존 Decision:

```text
ADR-TASK-003 PDMG↔NSIGHT Mapping
ADR-TASK-010 JWT Algorithm
ADR-TASK-011 JWT Key Management
ADR-TASK-019 Event Platform
ADR-TASK-021 RDW/ADW Separation
ADR-TASK-026 WAS Scale Unit
ADR-TASK-027 WEB/WAS Topology
ADR-TASK-028 JVM/WAR Isolation
ADR-TASK-030 HA Pattern
ADR-TASK-035 Observability
ADR-TASK-036 OM Control Plane
```

---

# 49. 주안 / 대안 — Framework Runtime Placement

## TEXT ARCHITECTURE — Decision Example

```text
[주안]

pdmg-service Runtime
  ├─ Business Components
  └─ pdmg-fw Framework Capability

장점
- Current Source와 정합
- Network Hop 없음
- 동일 Spring Context 활용
- Transaction/Context 직접 연계

단점
- Framework 장애가 Business Runtime에 직접 영향
- Runtime 자원 격리 한계


[대안]

pdmg-fw를 별도 Remote Runtime으로 분리

장점
- Framework Runtime 독립 Scale 가능
- Process Failure Domain 분리 가능

단점
- Current Source와 불일치
- HTTP/RPC 계약 신규 필요
- Context/TX 의미 변경
- 지연/장애지점 증가
```

### 권고

```text
Current PDMG Logical Architecture
= 주안

Remote 분리
= 명확한 비기능 요구와 별도 ADR 없이는 채택하지 않음
```

---

# 50. 주안 / 대안 — Application Runtime Scale Unit

## TEXT ARCHITECTURE — Scale Decision

```text
[주안]
Application Runtime Instance/JVM을
Logical Scale Unit로 본다.

장점
- Scale-out/N+1 설계와 연결
- JVM Resource/Failure 추적 용이
- WAR Group 분리 가능

단점
- JVM 수 증가 시 운영대상 증가


[대안]
대형 Runtime 하나에
다수 업무를 통합하여 Scale-up

장점
- 운영 Node 수 감소
- 초기 구성 단순

단점
- Failure Domain 증가
- GC/Thread/Memory 영향범위 증가
```

Physical 수량은 05/16장에서 결정한다.

---

# 51. Security Review

## TEXT ARCHITECTURE — Logical Security Review

```text
Client
 ↓
Authentication Node
 ↓
Token
 ↓
Application Runtime
 ↓
Principal
 ↓
Business Authorization
 ↓
Data Access
```

Review:

```text
[ ] Authentication Node는 독립 Trust Boundary로 식별되는가
[ ] Token Verification 책임이 Application Runtime에 존재하는가
[ ] Client Header와 Trusted Principal을 동일시하지 않는가
[ ] Key/Secret는 Runtime Artifact와 분리되는가
[ ] Data Credential은 최소권한인가
[ ] Security Log/Audit는 Operations로 전달되는가
```

---

# 52. Performance Review

## TEXT ARCHITECTURE — Logical Performance Chain

```text
Client
 ↓
UI Delivery
 ↓
Application Request Runtime
 ↓
Worker
 ↓
Connection Pool
 ↓
DB
```

성능은 Node 이름보다 **Queue/Pool/Downstream Capacity Chain**으로 본다.

```text
Request Thread
→ Worker
→ Hikari
→ DB Session
```

정확한 수치는 16장으로 넘긴다.

---

# 53. Availability Review

## TEXT ARCHITECTURE — Availability by Node

```text
UI Delivery
  ↓ scale-out candidate

Authentication
  ↓ multi-instance + key consistency

Application Runtime
  ↓ scale-out / N+1 candidate

Data Service
  ↓ DB HA

Operations
  ↓ monitoring/control HA
```

Logical 단계에서 최소 다음을 식별한다.

```text
Critical?
Stateful?
Scale-out?
Failover?
Residual Capacity?
DR Required?
```

---

# 54. Operations / Observability Review

## TEXT ARCHITECTURE — Node Evidence

```text
Logical Node ID
      ↓
Runtime Instance
      ↓
Metric / Log / Trace
      ↓
DeploymentId
      ↓
Host / JVM
      ↓
Evidence
```

운영자가 다음을 역추적할 수 있어야 한다.

```text
ServiceId
→ Logical Node
→ Runtime Instance
→ Host
```

그리고:

```text
Host
→ Runtime
→ Application
→ ServiceId
```

---

# 55. Logical Node Registry Template

## TEXT ARCHITECTURE — Registry

```text
Node ID
 + Role
 + Application
 + Runtime
 + Inbound
 + Outbound
 + State
 + Scale
 + Failure
 + Security
 + Monitoring
 + HA
 + Owner
 + Status
```

YAML 예시:

```yaml
logicalNode:
  nodeId: LTN-PD-03
  nodeName: PDMG-APPLICATION-RUNTIME
  application:
    - pdmg-service
    - pdmg-fw
  role: Online Business Runtime
  runtimeType: JVM-Spring
  inbound:
    - HTTP
  outbound:
    - JDBC
    - APPROVED_INTERFACE
  state:
    requestContext: true
    persistentBusinessState: false
  scaleUnit: runtime-instance
  securityBoundary: protected-business
  monitoringRequired: true
  haRequired: true
  physicalMapping: OPEN
```

이 Template의 제품/Host는 05장 이후 채운다.

---

# 56. GAP Register

## TEXT ARCHITECTURE — GAP Lifecycle

```text
Logical Baseline
      ↓ compare
Current Evidence
      ↓
GAP
      ↓
Decision / Owner
      ↓
Physical / Runtime Test
      ↓
Close
```

| GAP ID | GAP | 중요도 | 후속 |
|---|---|---:|---|
| GAP-LT-01 | Access/Web Logical Role 실제 PDMG 배치 정합 | High | 05 |
| GAP-LT-02 | JWT Key/Verifier Runtime 정합 | Critical | 11 |
| GAP-LT-03 | RDW/ADW 실제 Datasource Mapping | High | 07 |
| GAP-LT-04 | External Integration Node/Inventory | High | 06 |
| GAP-LT-05 | OM Logical Node Current Detail | High | 14 |
| GAP-LT-06 | Observability Deployment Correlation | High | 14/17 |
| GAP-LT-07 | Logical Node→Physical Mapping | Critical | 05 |
| GAP-LT-08 | Logical Node State/Scale/HA 속성 실증 | High | 16 |
| GAP-LT-09 | Environment별 Node Inventory | Medium/High | 05 |
| GAP-LT-10 | Product/Version Inventory 정합 | Medium | 05/TRM |

---

# 57. ADR Candidates

## TEXT ARCHITECTURE — ADR Route

```text
Logical GAP
  ↓
주안 / 대안
  ↓
NFR / Source / Cost
  ↓
ADR
  ↓
Logical Baseline
  ↓
Physical Mapping
```

후보:

```text
ADR-LT-01 PDMG Logical Node Set
ADR-LT-02 Framework Runtime Placement
ADR-LT-03 Authentication Runtime / Key State
ADR-LT-04 Integration Logical Node Scope
ADR-LT-05 Online / Analytical Workload Isolation
ADR-LT-06 Application Runtime Scale Unit
ADR-LT-07 Logical Failure Domain
ADR-LT-08 Operations / Observability Node
ADR-LT-09 Logical→Physical Mapping Gate
```

기존 Decision Task가 있으면 중복 생성하지 않고 연계한다.

---

# 58. Checklist

## TEXT ARCHITECTURE — Logical Checklist

```text
Application?
 ↓
Capability?
 ↓
Logical Node?
 ↓
Runtime?
 ↓
State?
 ↓
Scale?
 ↓
Failure?
 ↓
Security?
 ↓
Allowed Path?
 ↓
Physical Handoff?
```

Checklist:

```text
[ ] Application과 Logical Node를 구분했는가
[ ] Module과 Logical Node를 구분했는가
[ ] pdmg-fw를 별도 Remote Node로 강제하지 않았는가
[ ] UI/Auth/Application/Data Node가 정의되었는가
[ ] Integration/OM Unknown을 창작하지 않았는가
[ ] Node별 Runtime Type이 정의되었는가
[ ] Node별 State가 정의되었는가
[ ] Node별 Scale Unit이 정의되었는가
[ ] Node별 Failure Domain이 정의되었는가
[ ] Trust Boundary가 정의되었는가
[ ] Data Direct Access 금지경로가 표시되었는가
[ ] Observability가 Cross-cutting으로 정의되었는가
[ ] Product/Version을 Logical Node와 동일시하지 않았는가
[ ] Host/CPU/Port를 Logical 단계에서 확정하지 않았는가
[ ] Logical→Physical Mapping이 후속 Task로 연결되는가
```

---

# 59. Architecture PASS / PDMG Conformance

## FIG-04-48. 04장 판정

```text
Logical Technical Definition
        ↓
PASS

Current PDMG Conformance
        ↓
PARTIAL / CONDITIONAL

Strong
- UI/Auth/Application/Data core mapping
- service + fw same runtime concept
- Data access runtime

Open / Gap
- Integration inventory
- OM
- JWT runtime consistency
- Logical→Physical mapping
- Environment inventory
```

### Summary

| 영역 | Architecture Definition | PDMG Current | 판정 |
|---|---|---|---|
| Application→Technical | 정의완료 | 정합 | PASS |
| UI Delivery Node | 정의완료 | pdmg-ui Evidence | PASS |
| Authentication Node | 정의완료 | JWT Source + GAP | CONDITIONAL |
| Application Runtime | 정의완료 | service+fw 정합 | PASS |
| Framework Capability | 정의완료 | 강한 Evidence | PASS |
| Data Access | 정의완료 | Hikari/MyBatis/JDBC | PASS |
| Data Service | 정의완료 | RDW/DB | PASS/PARTIAL |
| Integration | 조건부 정의 | Inventory 미완료 | OPEN |
| Operations | 정의완료 | pdmg-om Unknown | OPEN |
| Observability | 정의완료 | 부분 Evidence | PARTIAL |
| Scale/Failure | 논리속성 정의 | 실측 미완료 | CONDITIONAL |
| Physical Mapping | Handoff 정의 | 미완료 | GAP |

### Chapter 판정

```text
Architecture Definition     : PASS
Current PDMG Conformance    : PARTIAL / CONDITIONAL
Runtime Evidence Coverage   : MEDIUM
Critical Logical GAP        : Logical→Physical Mapping + JWT Runtime
OPEN Major Areas            : Integration / OM / Environment Inventory
```

---

# 60. PASS 전환조건

## TEXT ARCHITECTURE — Full Logical Conformance

```text
Logical Node Set
      ↓
Current Runtime Mapping
      ↓
State / Scale / Failure Confirmation
      ↓
Security Gap Close
      ↓
Integration Inventory
      ↓
OM Scope
      ↓
Environment Mapping
      ↓
Physical Mapping
      ↓
Runtime Evidence
      ↓
Logical Technical Conformance PASS
```

전환조건:

1. LTN-PD-01~06 Node Set을 Architecture Registry에 등록.
2. `pdmg-ui`, `pdmg-jwt`, `pdmg-service`, `pdmg-fw`의 실제 Runtime Mapping 확인.
3. `pdmg-fw` Current Process 경계가 별도 Remote가 아님을 Deployment Evidence로 확정.
4. Authentication Key/Verifier Runtime 정합성 해소.
5. RDW/ADW 실제 Datasource/Mapper 사용현황 확정.
6. PDMG External Integration Inventory 확보.
7. `pdmg-om`의 Current Technical Role 확정 또는 Scope 제외.
8. Environment별 Logical Node Instance 목록 확보.
9. Node별 State/Scale/Failure/HA 속성을 Test/Evidence로 검증.
10. Logical Node→Host/VM/JVM/WAR Physical Mapping.
11. Logical Node→Monitoring/Backup/DR Mapping.
12. Product/Version Inventory와 Logical Capability의 정합 검증.

---

# 61. 이 장에서 확정하지 않는 것

## TEXT ARCHITECTURE — Deferred to Physical

```text
Logical Node
     ↓
05 Physical에서 확정

Center
Host
VM
CPU
Memory
Disk
OS
Exact Software Version
JVM Count
WAR Placement
Port
Firewall
Filesystem
Backup
DR Pair
```

04장의 Output은 05장의 Input이다.

---

# 62. 04장 Final Logical Technical Map

## TEXT ARCHITECTURE — Final Map

```text
USER / CLIENT
      ↓
LTN-PD-01
UI DELIVERY
      ↓
      ├──────────────► LTN-PD-02 AUTHENTICATION
      │                  │
      │                  └─ Token / JWKS
      │
      ▼
LTN-PD-03
APPLICATION RUNTIME
│
├─ Framework Runtime Capability
│  ├─ Filter / Context
│  ├─ TCF / Dispatcher
│  ├─ Worker / Timeout
│  ├─ Transaction
│  └─ Error / Log
│
├─ Business Runtime
│  ├─ Handler / Controller
│  ├─ Facade
│  ├─ Service
│  └─ DAO
│
└─ Data Access Capability
   ├─ Connection Pool
   ├─ MyBatis Mapping
   └─ JDBC
      ↓
LTN-PD-04
DATA SERVICE / RDW
      │
      ├────────► LTN-PD-05 INTEGRATION [CONDITIONAL]
      │
      └────────► LTN-PD-06 OPERATIONS [OPEN]

Cross-cutting
SECURITY
OBSERVABILITY
BUILD / DEPLOYMENT
HA / DR REQUIREMENT
```

---

# 63. 04장 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG Logical Technical Architecture
=
Application Responsibility
        ↓
Technical Capability
        ↓
Logical Technical Node
        ↓
Runtime Characteristic
        ↓
State / Scale / Failure
        ↓
Allowed / Forbidden Path
        ↓
Physical Handoff
```

가장 중요한 결론:

```text
1.
Application
≠ Logical Technical Node

2.
Build Module
≠ Logical Technical Node

3.
Logical Technical Node
≠ Physical Server

4.
Technology Component
≠ Product / Version

5.
pdmg-service + pdmg-fw
→ Current 하나의 Application Runtime Node로 Mapping 가능

6.
Integration / OM은
Evidence 없이 Current Node로 확정하지 않음
```

**04장 Architecture Definition 판정: `PASS`**

Current PDMG의 핵심 Runtime은 Logical Node로 설명 가능하지만, 실제 Integration/OM/Environment/Physical Mapping과 JWT Runtime 정합이 남아 있으므로:

```text
Current PDMG Conformance
= PARTIAL / CONDITIONAL
```

로 유지한다.

---

# 64. Next Chapter Handoff

## FIG-04-49. 04 → 05

```text
04 LOGICAL TECHNICAL
"어떤 Technical Node와 Capability가 필요한가?"
               ↓
05 PHYSICAL / INFRASTRUCTURE
"그 Logical Node를
어느 Center / Host / VM / JVM /
Network / DB / Storage에
어떻게 배치할 것인가?"
```

05장의 기본 Chain:

```text
Logical Node
   ↓
Environment
   ↓
Center
   ↓
Host / VM
   ↓
OS / Runtime
   ↓
JVM / Process
   ↓
Artifact
   ↓
Network / Port
   ↓
DB / Storage
   ↓
Monitoring / Backup
   ↓
HA / DR
   ↓
Runtime Evidence
```


---



<!-- ============================================================ -->
<!-- CHAPTER 05: PHYSICAL / INFRASTRUCTURE -->
<!-- SOURCE: 05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md -->
<!-- ============================================================ -->

# PDMG 전체 아키텍처 정의서
# 05. PDMG PHYSICAL / INFRASTRUCTURE ARCHITECTURE
## Center / Compute / Network / WEB-WAS / DB / Storage / HA-DR / Evidence
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-05-PHYSICAL-INFRASTRUCTURE`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-05-01. 이 장의 핵심 질문

```text
4장의 Logical Node를 실제 어떤 Center / Host / VM / JVM / WAR / DB / Network / Storage로 구현할 것인가?
Logical과 Physical의 경계를 어떻게 유지할 것인가?
실제 PDMG 배치와 아직 OPEN인 영역은 무엇인가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-05-02. Evidence Flow

```text
Source / Config
   ↓
Runtime / Deployment
   ↓
PDMG Current Analysis
   ↓
Architecture Decision
   ↓
NSIGHT Target / Working Baseline
   ↓
PASS / GAP / OPEN
```

| Evidence ID | 근거 | 사용목적 | 상태 |
|---|---|---|---|
| EV-05-01 | 04 Logical Technical | Logical Node→Physical Handoff | [WORKING BASELINE] |
| EV-05-02 | VIII Infrastructure/WAS/Capacity/HA/DR | Physical path, capacity, HA/DR | [PHYSICAL REFERENCE] |
| EV-05-03 | Infrastructure Appendix C | Center/Compute/Network/Storage | [TARGET REFERENCE] |
| EV-05-04 | HW/SW Matrix | HW/SW roles and candidates | [WORKING INVENTORY] |
| EV-05-05 | PDMG Runtime | JVM/WAR/Data access context | [AS-IS REFERENCE] |

---

# 2. Figure Plan

## FIG-05-03. Top-down Drill-down

```text
L0  Overall
 ↓
L1  Boundary / Responsibility
 ↓
L2  Node / Platform / Component
 ↓
L3  Runtime / Control / Data
 ↓
L4  Sequence / Failure / Recovery
 ↓
L5  Source / Config / Evidence
```

---

# 3. L0 — PDMG Physical Master Architecture

## FIG-05-04. L0 — PDMG Physical Master Architecture

```text
User / Browser
   ↓
GSLB [Working Baseline]
   ↓
L4 [Working Baseline]
   ↓
WEB / Apache
   ↓
WAS / Tomcat JVM
   ↓
PDMG WAR / Runtime
   ├─ pdmg-service
   └─ pdmg-fw
   ↓
Hikari / MyBatis / JDBC
   ↓
RDW / DB

Authentication Runtime
pdmg-jwt
   ↓
Token / JWKS
   ↓
PDMG Business Runtime

Operations / Monitoring / Backup
= Physical Mapping OPEN / Target Reference
```

이 경로는 PDMG/NSIGHT의 Working Physical Baseline이다. 실제 Hostname, VM 수, Port, 제품 Version은 Inventory Evidence가 없으면 `[OPEN]`으로 유지한다.

---

# 4. Logical Node → Physical Resource Mapping

## FIG-05-05. Logical Node → Physical Resource Mapping

```text
LTN-PD-01 UI Delivery
   ↓
WEB/UI Runtime Resource

LTN-PD-02 Authentication
   ↓
JWT Runtime Resource

LTN-PD-03 Application Runtime
   ↓
WAS VM / JVM / WAR

LTN-PD-04 Data Service
   ↓
DB Service / DB Cluster

LTN-PD-05 Integration
   ↓
Integration Platform [CONDITIONAL]

LTN-PD-06 Operations
   ↓
Monitoring / Control [OPEN]
```

---

# 5. Server / VM / JVM / WAR Boundary

## FIG-05-06. Server / VM / JVM / WAR Boundary

```text
Physical Server / Hypervisor
   ↓
VM
   ↓
OS
   ↓
Runtime Process
   ↓
JVM
   ↓
WAR / Application Artifact

Server ≠ VM ≠ JVM ≠ WAR
```

PDMG Module 이름을 서버명으로 자동 변환하지 않는다. 하나의 VM에 여러 JVM이 있을 수 있고, 하나의 JVM에 여러 WAR이 있을 수 있다.

---

# 6. Environment / Center Axis

## FIG-05-07. Environment / Center Axis

```text
Development
Test
Production
DR
   │
   └─ Deployment Environment

Main Center [의왕 Working Reference]
DR Center   [안성 Working Reference]

Center ≠ Environment
```

의왕 Main/안성 DR은 현재 Physical Reference의 Working Baseline이다. 실제 PDMG 배치 Host/VM Inventory는 별도 확인한다.

---

# 7. WEB Layer Architecture

## FIG-05-08. WEB Layer Architecture

```text
GSLB
 ↓
L4
 ↓
WEB VM
 ↓
Apache Instance
 ↓
Reverse Proxy / Routing
 ↓
Tomcat Connector

Apache Instance ≠ WEB VM
```

---

# 8. WAS / JVM / WAR Architecture

## FIG-05-09. WAS / JVM / WAR Architecture

```text
WAS VM
 ├─ JVM Group A
 │   ├─ WAR ...
 │   └─ WAR ...
 └─ JVM Group B
     ├─ WAR ...
     └─ WAR ...

JVM Group / WAR placement
= [OPEN / Candidate]
```

업무그룹 A/B 분리는 장애영향과 자원독점을 줄이는 Target 후보이며 실제 17 WAR 배치표는 Deployment Inventory로 확정해야 한다.

---

# 9. Data / DB Physical Boundary

## FIG-05-10. Data / DB Physical Boundary

```text
PDMG JVM
  ↓ Datasource
Hikari Pool
  ↓ JDBC
DB Service
  ↓
DB Cluster / Node
  ↓
Storage

RDW = Operational / Near-real-time
ADW = Analytical / Mart [Target Reference]
```

---

# 10. Network / Port / Firewall

## FIG-05-11. Network / Port / Firewall

```text
Client
 ↓
DNS/GSLB
 ↓
L4 VIP
 ↓
WEB Port
 ↓
WAS Connector Port
 ↓
DB Service Port
 ↓
Management / Backup Network

Port Inventory ↔ Firewall ↔ LB ↔ Config
```

정확한 Port 값은 현재 정의서에서 임의 생성하지 않는다.

---

# 11. Storage / Filesystem

## FIG-05-12. Storage / Filesystem

```text
OS Filesystem
 ├─ App / Runtime
 ├─ Log
 ├─ Temp
 ├─ Artifact
 └─ Config

Data Storage
 ├─ DB Data
 ├─ Archive
 └─ Backup
```

---

# 12. Security Infrastructure

## FIG-05-13. Security Infrastructure

```text
External / User Zone
  ↓
Traffic / Web Boundary
  ↓
Protected WAS Zone
  ↓
Data Zone
  ↓
Management Zone

Key / Secret
= Runtime Artifact와 분리
```

---

# 13. Monitoring / Backup Physical Handoff

## FIG-05-14. Monitoring / Backup Physical Handoff

```text
WEB / WAS / JWT / DB
   ↓
Agent / Exporter / Log
   ↓
Monitoring / Logging
   ↓
Alert / Dashboard

DB / Config / File
   ↓
Backup
   ↓
Restore Test
```

---

# 14. HA — Main Center

## FIG-05-15. HA — Main Center

```text
GSLB / L4
 ↓
WEB N+1 / Pair
 ↓
WAS Active-Active / N+1 candidate
 ↓
DB Local HA
 ↓
Residual Capacity Validation
```

---

# 15. DR — Center Failure

## FIG-05-16. DR — Center Failure

```text
Main Center
  ↓ detect
Traffic Reroute
  ↓
DR Access
  ↓
DR WEB/WAS
  ↓
DR DB / Replication
  ↓
Config / Key / Artifact
  ↓
Business Validation
```

RTO/RPO는 현재 `[OPEN]`. Backup 성공만으로 DR PASS로 보지 않는다.

---

# 16. Current Capacity Projection

## FIG-05-17. Current Capacity Projection

```text
Candidate A
32C / 256G × 4

Candidate B
16C / 128G × 8

Candidate C
16C / 128G × 4 × 2 groups

All = [CANDIDATE]
```

---

# 17. Physical Traceability

## FIG-05-18. Physical Traceability

```text
Application
 ↓
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
 ↓
Center
 ↓
Metric / Evidence
```

현재 PDMG의 실제 Artifact→Host/JVM/WAR 전수 Mapping은 핵심 GAP다.

---

# 18. Architecture Rule Catalog

## FIG-05-19. Rule Set

```text
R-PHY-01
Server ≠ VM ≠ JVM ≠ WAR

R-PHY-02
Logical Node는 실제 Physical Mapping을 가져야 한다.

R-PHY-03
미확정 Host/Port/Version은 OPEN으로 유지한다.

R-PHY-04
WEB와 WAS 책임을 분리한다.

R-PHY-05
WAS Scale-out 시 N+1 잔존용량을 검증한다.

R-PHY-06
DB Local HA와 Center DR을 구분한다.

R-PHY-07
Backup 성공 ≠ Restore/Business Recovery 성공.

R-PHY-08
Key/Secret은 Artifact 일반 Config와 분리한다.

R-PHY-09
Port/Firewall/LB/Config는 상호 정합해야 한다.

R-PHY-10
Capacity Candidate를 Production Fact로 표기하지 않는다.
```

| Rule | 정의 |
|---|---|
| R-PHY-01 | Server ≠ VM ≠ JVM ≠ WAR |
| R-PHY-02 | Logical Node는 실제 Physical Mapping을 가져야 한다. |
| R-PHY-03 | 미확정 Host/Port/Version은 OPEN으로 유지한다. |
| R-PHY-04 | WEB와 WAS 책임을 분리한다. |
| R-PHY-05 | WAS Scale-out 시 N+1 잔존용량을 검증한다. |
| R-PHY-06 | DB Local HA와 Center DR을 구분한다. |
| R-PHY-07 | Backup 성공 ≠ Restore/Business Recovery 성공. |
| R-PHY-08 | Key/Secret은 Artifact 일반 Config와 분리한다. |
| R-PHY-09 | Port/Firewall/LB/Config는 상호 정합해야 한다. |
| R-PHY-10 | Capacity Candidate를 Production Fact로 표기하지 않는다. |

---

# 19. Verification / Test

## FIG-05-20. Verification Flow

```text
Architecture Model
   ↓
Static / Config Check
   ↓
Integration Test
   ↓
Failure / Security / Performance Test
   ↓
Runtime Evidence
   ↓
PASS / GAP
```

| Test ID | 검증내용 |
|---|---|
| T-PHY-01 | Logical Node→Host/VM/JVM/WAR Mapping |
| T-PHY-02 | L4/WEB/WAS/DB Connectivity |
| T-PHY-03 | Port/Firewall/LB Config consistency |
| T-PHY-04 | Node failure / N+1 capacity |
| T-PHY-05 | DB failover |
| T-PHY-06 | DR switchover / failback |
| T-PHY-07 | Backup restore / business validation |

---

# 20. GAP Register

## FIG-05-21. GAP Lifecycle

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

| GAP ID | GAP | 중요도 | 전환조건 |
|---|---|---|---|
| GAP-PHY-01 | PDMG Artifact→Host/JVM/WAR 실배치 미완료 | Critical | Deployment/CMDB Mapping |
| GAP-PHY-02 | 실제 Host/VM/Port Inventory 미확보 | High | Infra Inventory |
| GAP-PHY-03 | JVM/WAR 업무그룹 승인안 미확정 | High | Load/Failure Test + ADR |
| GAP-PHY-04 | RTO/RPO 미확정 | High | Business DR Tier |
| GAP-PHY-05 | DB HA/DR 제품/노드 상세 미확정 | High | DB Inventory/Test |
| GAP-PHY-06 | Backup Restore Evidence 미확보 | High | Restore Drill |

---

# 21. Risk Register

## FIG-05-22. Risk Propagation

```text
Cause
  ↓
Technical Failure
  ↓
Service Impact
  ↓
Operational / Business Impact
```

| Risk ID | Risk | 영향 |
|---|---|---|
| RISK-PHY-01 | 대형 JVM/다수 WAR 자원독점 | GC/장애영향 확대 |
| RISK-PHY-02 | Port/Firewall/Config Drift | Connectivity 장애 |
| RISK-PHY-03 | DR Artifact/Key Drift | DR 전환 실패 |
| RISK-PHY-04 | N+1 잔존용량 미검증 | 노드 장애 시 과부하 |
| RISK-PHY-05 | Backup만 성공하고 Restore 미검증 | 실제 복구 불가 |

---

# 22. Architecture Decision / ADR

## FIG-05-23. Decision Flow

```text
Decision Question
   ↓
주안 / 대안
   ↓
장점 / 단점
   ↓
Evidence / Test
   ↓
ADR
   ↓
Baseline
```

| ADR/Task | 의사결정 주제 | 현재 방향 |
|---|---|---|
| ADR-TASK-026 | WAS Compute Sizing | 중형 VM Scale-out 주안 / Load Test 전제 |
| ADR-TASK-027 | WEB/WAS Topology | GSLB→L4→Apache→Tomcat |
| ADR-TASK-028 | JVM/WAR Isolation | 업무그룹별 격리 주안 |
| ADR-TASK-030 | HA Pattern | Stateless Active-Active/N+1 |
| ADR-TASK-031 | DR Model | Critical 서비스 Warm/Hot 차등 |
| ADR-TASK-032 | DB HA/DR | Local HA + Center DR |

---

# 23. Architecture PASS / PDMG Conformance

## FIG-05-24. PASS Model

```text
Architecture Definition
  ↓
PASS

Current Physical Conformance
  ↓
PARTIAL / CONDITIONAL

Critical
Artifact→Host/JVM/WAR Mapping
RTO/RPO / DR Evidence
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Logical→Physical | PASS | Mapping model defined |
| Online Physical Path | PASS/BASELINE | GSLB→L4→WEB→WAS→DB |
| Actual Inventory | OPEN | Host/Port/Version |
| HA | CONDITIONAL | Failure/N+1 test needed |
| DR | CONDITIONAL | RTO/RPO and drill needed |
| Backup/Restore | CONDITIONAL | Restore evidence needed |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PARTIAL / CONDITIONAL`  
**Runtime Evidence Coverage:** `MEDIUM`

---

# 24. Next Chapter Handoff

## FIG-05-25. 05 → 06

```text
05 PHYSICAL
"어디에 배치하는가?"
     ↓
06 INTERFACE
"그 배치된 Application이 외부/내부 시스템과 어떤 Contract로 연결되는가?" 
```

---

# 25. PDMG PHYSICAL / INFRASTRUCTURE ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG PHYSICAL / INFRASTRUCTURE ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**05장 Architecture Definition 판정: `PASS`**


---



<!-- ============================================================ -->
<!-- CHAPTER 06: INTERFACE -->
<!-- SOURCE: 06_PDMG_INTERFACE_상세정의서_v1.md -->
<!-- ============================================================ -->

# PDMG 전체 아키텍처 정의서
# 06. PDMG INTERFACE ARCHITECTURE
## Contract / Sync-Async / API-Event-CDC-ETL-File / Failure / Governance
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-06-INTERFACE`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-06-01. 이 장의 핵심 질문

```text
PDMG가 내부/외부 시스템과 어떤 Contract로 연결되는가?
어떤 업무조건에서 API/Event/CDC/ETL/File을 선택하는가?
Timeout/Retry/Idempotency/Security/Recovery를 어떻게 통제하는가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-06-02. Evidence Flow

```text
Source / Config
   ↓
Runtime / Deployment
   ↓
PDMG Current Analysis
   ↓
Architecture Decision
   ↓
NSIGHT Target / Working Baseline
   ↓
PASS / GAP / OPEN
```

| Evidence ID | 근거 | 사용목적 | 상태 |
|---|---|---|---|
| EV-06-01 | Interface Principle / Guide | IF-01~IF-10, type selection, retry | [TARGET BASELINE] |
| EV-06-02 | 02 System Context | Inbound/Outbound boundary | [WORKING BASELINE] |
| EV-06-03 | PDMG Runtime | HTTP/JSON request | [AS-IS] |
| EV-06-04 | PDMG Data Access | MyBatis/JDBC | [AS-IS] |
| EV-06-05 | Interface Appendix D | Contract/Operations/Governance | [TARGET REFERENCE] |

---

# 2. Figure Plan

## FIG-06-03. Top-down Drill-down

```text
L0  Overall
 ↓
L1  Boundary / Responsibility
 ↓
L2  Node / Platform / Component
 ↓
L3  Runtime / Control / Data
 ↓
L4  Sequence / Failure / Recovery
 ↓
L5  Source / Config / Evidence
```

---

# 3. L0 — PDMG Interface Master

## FIG-06-04. L0 — PDMG Interface Master

```text
Business Interaction Need
   ↓
Interface Classification
   ↓
Source / Target Boundary
   ↓
Interface Contract
   ↓
Runtime Mechanism
   ↓
Failure / Recovery
   ↓
Operations / Evidence
```

---

# 4. Interface Architecture Principles

## FIG-06-05. Interface Architecture Principles

```text
IF-01 Purpose-driven
IF-02 P2P minimize
IF-03 Service-to-Service
IF-04 Sync/Async by business need
IF-05 Online/Bulk separation
IF-06 Contract-first
IF-07 Failure isolation
IF-08 Traceability
IF-09 Versioning
IF-10 Exception via Architecture Review
```

---

# 5. PDMG Current Interface Context

## FIG-06-06. PDMG Current Interface Context

```text
Browser / UI
  ↓ HTTP/JSON
PDMG Runtime
  ↓ JDBC
RDW / DB

External API / Event / File
= Inventory Required
= [OPEN / PARTIAL]
```

Current PDMG에서 강하게 확인되는 것은 HTTP business request와 MyBatis/JDBC DB access다. 외부 API/Event/File 전수 목록은 별도 Interface Inventory가 필요하다.

---

# 6. Interface Type Decision Tree

## FIG-06-07. Interface Type Decision Tree

```text
즉시 결과 필요?
 ├─ YES → API / Transaction
 └─ NO
      ↓
   Business Event?
    ├─ YES → Event
    └─ NO
         ↓
      DB Change?
       ├─ YES → CDC
       └─ NO
            ↓
         Bulk?
          ├─ YES → ETL
          └─ NO → File / MFT
```

---

# 7. SYNC / ASYNC Policy

## FIG-06-08. SYNC / ASYNC Policy

```text
Current Transaction Completion에
Target 결과가 필수?
  ├─ YES → SYNC
  └─ NO  → ASYNC 우선
```

---

# 8. Interface Contract

## FIG-06-09. Interface Contract

```text
InterfaceId
Source / Target
Purpose
Type
Protocol / Endpoint
Sync/Async
Schema / Header
GUID / Correlation
Error
Timeout / Retry
Idempotency
Security
SLA
Owner / Version
```

---

# 9. Identifiers

## FIG-06-10. Identifiers

```text
ServiceId
= Business Transaction Identity

InterfaceId
= S2S Contract Identity

GUID
= Runtime Execution Identity

ServiceId ≠ InterfaceId ≠ GUID
```

---

# 10. Direct DB / DB-Link Policy

## FIG-06-11. Direct DB / DB-Link Policy

```text
System A
  ↓
Approved API / Data Contract
  ↓
System B

System A ──JDBC/DML──► System B DB
         X

DB-Link
= Exception / ADR only
```

---

# 11. API Pattern

## FIG-06-12. API Pattern

```text
Source
  ↓ Request
Target
  ↓ Response
Source

Timeout / Error / Auth / Version
must be contract-defined
```

---

# 12. Event Pattern

## FIG-06-13. Event Pattern

```text
Producer
  ↓ Event
Broker
  ├─ Consumer A
  ├─ Consumer B
  └─ Consumer C

Schema / Replay / DLQ / Ordering
```

---

# 13. CDC Pattern

## FIG-06-14. CDC Pattern

```text
Source DB
  ↓ Capture
Trail / Queue
  ↓ Relay
Target Apply
  ↓
RDW / Consumer

Freshness SLA
= 30s vs 3s [CONFLICT]
```

---

# 14. ETL / File Pattern

## FIG-06-15. ETL / File Pattern

```text
Bulk
Source
 ↓ Extract
Transform
 ↓ Load
Target

File
Sender
 ↓ MFT
Landing
 ↓ Validation
Receiver
```

---

# 15. Timeout Hierarchy

## FIG-06-16. Timeout Hierarchy

```text
DB Query Timeout
    <
Worker / Transaction Deadline
    <
Server / Downstream Timeout
    <
Client Timeout
```

---

# 16. Retry / Idempotency

## FIG-06-17. Retry / Idempotency

```text
Failure
 ↓
Transient?
 ├─ NO → No Retry
 └─ YES
      ↓
   Idempotent?
   ├─ NO → Compensation / Manual
   └─ YES → Backoff → Max Retry → Recovery
```

---

# 17. Interface Security

## FIG-06-18. Interface Security

```text
Caller Identity
 ↓
Authentication
 ↓
Authorization
 ↓
Transport Protection
 ↓
Schema Validation
 ↓
Audit / Trace
```

---

# 18. Operations / Recovery

## FIG-06-19. Operations / Recovery

```text
Interface
 ├─ Log
 ├─ Metric
 ├─ Trace
 ├─ Alert
 ├─ Retry
 ├─ Replay
 ├─ Reconciliation
 └─ Runbook
```

---

# 19. Interface Inventory / Trace

## FIG-06-20. Interface Inventory / Trace

```text
Requirement
 ↓
InterfaceId
 ↓
Source / Target
 ↓
Contract Version
 ↓
Runtime Endpoint
 ↓
GUID
 ↓
Evidence
```

---

# 20. Architecture Rule Catalog

## FIG-06-21. Rule Set

```text
R-IF-01
업무 목적에 따라 Interface Type을 선택한다.

R-IF-02
P2P를 최소화한다.

R-IF-03
Cross-system Direct DB DML은 금지한다.

R-IF-04
현재 거래완료에 결과가 필수일 때만 SYNC를 사용한다.

R-IF-05
온라인과 Bulk/File을 분리한다.

R-IF-06
Contract-first로 설계한다.

R-IF-07
Retry는 Error/Idempotency 조건으로 제한한다.

R-IF-08
모든 Interface는 InterfaceId/GUID로 추적한다.

R-IF-09
Breaking Change는 Version으로 관리한다.

R-IF-10
예외는 ADR/Architecture Review를 거친다.
```

| Rule | 정의 |
|---|---|
| R-IF-01 | 업무 목적에 따라 Interface Type을 선택한다. |
| R-IF-02 | P2P를 최소화한다. |
| R-IF-03 | Cross-system Direct DB DML은 금지한다. |
| R-IF-04 | 현재 거래완료에 결과가 필수일 때만 SYNC를 사용한다. |
| R-IF-05 | 온라인과 Bulk/File을 분리한다. |
| R-IF-06 | Contract-first로 설계한다. |
| R-IF-07 | Retry는 Error/Idempotency 조건으로 제한한다. |
| R-IF-08 | 모든 Interface는 InterfaceId/GUID로 추적한다. |
| R-IF-09 | Breaking Change는 Version으로 관리한다. |
| R-IF-10 | 예외는 ADR/Architecture Review를 거친다. |

---

# 21. Verification / Test

## FIG-06-22. Verification Flow

```text
Architecture Model
   ↓
Static / Config Check
   ↓
Integration Test
   ↓
Failure / Security / Performance Test
   ↓
Runtime Evidence
   ↓
PASS / GAP
```

| Test ID | 검증내용 |
|---|---|
| T-IF-01 | Interface Registry completeness |
| T-IF-02 | Contract schema compatibility |
| T-IF-03 | Timeout hierarchy |
| T-IF-04 | Retry duplicate prevention |
| T-IF-05 | Security/authz |
| T-IF-06 | Replay/reconciliation |
| T-IF-07 | Direct DB exception scan |

---

# 22. GAP Register

## FIG-06-23. GAP Lifecycle

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

| GAP ID | GAP | 중요도 | 전환조건 |
|---|---|---|---|
| GAP-IF-01 | PDMG External Interface Inventory 미완료 | High | InterfaceId/Source/Target 전수등록 |
| GAP-IF-02 | InterfaceId 정확한 Enterprise 형식 미확정 | Medium | Naming ADR |
| GAP-IF-03 | Timeout/Retry 실제 값 전수 미확정 | High | Contract Registry |
| GAP-IF-04 | CDC SLA 3s vs 30s 충돌 | High | Tiered SLA ADR |
| GAP-IF-05 | Direct DB/DB-Link 예외 Inventory 미완료 | High | Privilege/Link Scan |
| GAP-IF-06 | Replay/Reconciliation Evidence 미완료 | High | Ops test |

---

# 23. Risk Register

## FIG-06-24. Risk Propagation

```text
Cause
  ↓
Technical Failure
  ↓
Service Impact
  ↓
Operational / Business Impact
```

| Risk ID | Risk | 영향 |
|---|---|---|
| RISK-IF-01 | Blind Retry | 중복 금융거래/부하증폭 |
| RISK-IF-02 | P2P Direct DB | 강결합/변경전파 |
| RISK-IF-03 | 동기 호출체인 증가 | Cascade failure |
| RISK-IF-04 | 대량 payload를 Online API 처리 | Timeout/자원고갈 |
| RISK-IF-05 | Versionless Contract | Breaking change |

---

# 24. Architecture Decision / ADR

## FIG-06-25. Decision Flow

```text
Decision Question
   ↓
주안 / 대안
   ↓
장점 / 단점
   ↓
Evidence / Test
   ↓
ADR
   ↓
Baseline
```

| ADR/Task | 의사결정 주제 | 현재 방향 |
|---|---|---|
| ADR-TASK-014 | Interface Type Selection | Purpose-driven |
| ADR-TASK-015 | Direct DB/DB-Link | 원칙 금지 |
| ADR-TASK-016 | SYNC/ASYNC | Result-required only SYNC |
| ADR-TASK-017 | Timeout Budget | 계층형 |
| ADR-TASK-018 | Retry/Idempotency | 분류+Backoff+Idempotency |
| ADR-TASK-019 | Event Platform | 공통 Event Broker 후보 |
| ADR-TASK-020 | Bulk/File | ETL/MFT 분리 |

---

# 25. Architecture PASS / PDMG Conformance

## FIG-06-26. PASS Model

```text
Architecture Definition
 ↓
PASS

Current PDMG Interface Conformance
 ↓
PARTIAL / OPEN

Strong: HTTP/JDBC
Open: external API/Event/File inventory
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Principles | PASS | IF-01~IF-10 |
| HTTP business | PASS | Current runtime |
| DB access | PASS/PARTIAL | Own/approved DB only |
| External Inventory | OPEN | InterfaceId catalog needed |
| Retry/Timeout | CONDITIONAL | per-interface values needed |
| Recovery | CONDITIONAL | replay/reconcile tests |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PARTIAL / OPEN`  
**Runtime Evidence Coverage:** `MEDIUM-LOW`

---

# 26. Next Chapter Handoff

## FIG-06-27. 06 → 07

```text
06 INTERFACE
"어떻게 연결하는가?"
     ↓
07 DATA
"그 연결과 업무 실행이 어떤 데이터 Ownership / Model / Flow / SQL과 연결되는가?" 
```

---

# 27. PDMG INTERFACE ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG INTERFACE ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**06장 Architecture Definition 판정: `PASS`**


---



<!-- ============================================================ -->
<!-- CHAPTER 07: DATA -->
<!-- SOURCE: 07_PDMG_DATA_상세정의서_v1.md -->
<!-- ============================================================ -->

# PDMG 전체 아키텍처 정의서
# 07. PDMG DATA ARCHITECTURE
## Ownership / DAO-Mapper-SQL / RDW-ADW / Lineage / Quality / Security
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-07-DATA`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-07-01. 이 장의 핵심 질문

```text
PDMG는 어떤 데이터를 읽고 변경하며, 어떤 Data Boundary를 가져야 하는가?
ServiceId가 DAO/Mapper/SQL/Table로 어떻게 추적되는가?
RDW/ADW, CDC/ETL, Ownership/Quality/Security를 어떻게 정합시킬 것인가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-07-02. Evidence Flow

```text
Source / Config
   ↓
Runtime / Deployment
   ↓
PDMG Current Analysis
   ↓
Architecture Decision
   ↓
NSIGHT Target / Working Baseline
   ↓
PASS / GAP / OPEN
```

| Evidence ID | 근거 | 사용목적 | 상태 |
|---|---|---|---|
| EV-07-01 | 03 Application | DAO/Mapper/ServiceId | [WORKING BASELINE] |
| EV-07-02 | Transaction/DB analysis | DataSource/TX | [AS-IS] |
| EV-07-03 | Data Appendix E | Ownership/Model/Flow/DQ | [TARGET REFERENCE] |
| EV-07-04 | Interface Guide | Direct DB restrictions | [TARGET BASELINE] |
| EV-07-05 | NSIGHT Data Platform | RDW/ADW/CDC/ETL roles | [TARGET REFERENCE] |

---

# 2. Figure Plan

## FIG-07-03. Top-down Drill-down

```text
L0  Overall
 ↓
L1  Boundary / Responsibility
 ↓
L2  Node / Platform / Component
 ↓
L3  Runtime / Control / Data
 ↓
L4  Sequence / Failure / Recovery
 ↓
L5  Source / Config / Evidence
```

---

# 3. L0 — PDMG Data Master

## FIG-07-04. L0 — PDMG Data Master

```text
Business Meaning
   ↓
ServiceId
   ↓
Service / DAO
   ↓
Mapper / SqlId
   ↓
SQL
   ↓
Table / View
   ↓
RDW / DB
   ↓
Lineage / Evidence
```

---

# 4. PDMG Current Data Access

## FIG-07-05. PDMG Current Data Access

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

# 5. Data Architecture vs Database Architecture

## FIG-07-06. Data Architecture vs Database Architecture

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

Database Architecture
= Data Architecture의 하위 구현영역
```

---

# 6. Data Ownership Boundary

## FIG-07-07. Data Ownership Boundary

```text
Business Data
 ↓
Owner / Steward
 ↓
SOR / Master
 ↓
Approved Consumer
 ↓
PDMG Access
```

---

# 7. RDW / ADW Role

## FIG-07-08. RDW / ADW Role

```text
RDW
= Operational / Near-real-time / Information Service

ADW
= Analytical / Aggregation / Mart / Heavy Query

PDMG Current → RDW stronger evidence
PDMG Current → ADW [OPEN]
```

---

# 8. Datasource / Transaction Alignment

## FIG-07-09. Datasource / Transaction Alignment

```text
TransactionManager
   ↓
DataSource
   ↓
Hikari
   ↓
DAO / Mapper
   ↓
DB

TX Manager ↔ DataSource
must align
```

---

# 9. Mapper / SqlId Trace

## FIG-07-10. Mapper / SqlId Trace

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

# 10. Read / Write Boundary

## FIG-07-11. Read / Write Boundary

```text
Own / Approved Data
  ├─ READ
  └─ WRITE according to ownership

Other System Data
  ↓
Approved Interface / Data Contract

Cross-system direct DML
= Forbidden
```

---

# 11. Data Lineage

## FIG-07-12. Data Lineage

```text
Source
 ↓
Transform / SQL / ETL
 ↓
Target
 ↓
Consumer

Reverse:
Table → SqlId → DAO → ServiceId → Application
```

---

# 12. CDC / ETL Relation

## FIG-07-13. CDC / ETL Relation

```text
Source Data
 ├─ Change stream → CDC → RDW
 └─ Bulk extract  → ETL → ADW

PDMG Current direct ownership
= [REFERENCE / OPEN]
```

---

# 13. Metadata

## FIG-07-14. Metadata

```text
Business Metadata
+ Technical Metadata
+ Operational Metadata
   ↓
Catalog / Lineage
```

---

# 14. Data Quality

## FIG-07-15. Data Quality

```text
Definition
 ↓
Validation
 ↓
Load / Update
 ↓
Monitor
 ↓
Issue
 ↓
Remediation
```

---

# 15. Data Security

## FIG-07-16. Data Security

```text
Classification
 ↓
Access Control
 ↓
Masking / Encryption
 ↓
Audit
 ↓
Retention / Disposal
```

---

# 16. Performance / Workload Isolation

## FIG-07-17. Performance / Workload Isolation

```text
Online Query
 ↓
RDW

Heavy Analytics
 ↓
ADW

Cross impact
= minimize / isolate
```

---

# 17. Data Evidence

## FIG-07-18. Data Evidence

```text
ServiceId
 ↓
SqlId
 ↓
Table
 ↓
Elapsed / Rows / Error
 ↓
GUID
 ↓
Runtime Evidence
```

---

# 18. Architecture Rule Catalog

## FIG-07-19. Rule Set

```text
R-DA-01
Business concept→Logical Entity→Physical Object로 추적한다.

R-DA-02
Data Owner/Steward/SOR를 명시한다.

R-DA-03
Cross-system direct DML을 금지한다.

R-DA-04
ServiceId→DAO→Mapper→SqlId→Table 추적을 유지한다.

R-DA-05
RDW와 ADW의 Workload 목적을 분리한다.

R-DA-06
TX Manager와 DataSource를 정렬한다.

R-DA-07
Metadata/Lineage를 변경영향 분석에 사용한다.

R-DA-08
Critical Data에 Quality Rule을 둔다.

R-DA-09
Security classification과 lifecycle을 정의한다.

R-DA-10
PDMG Current에서 미확인 Data Flow를 창작하지 않는다.
```

| Rule | 정의 |
|---|---|
| R-DA-01 | Business concept→Logical Entity→Physical Object로 추적한다. |
| R-DA-02 | Data Owner/Steward/SOR를 명시한다. |
| R-DA-03 | Cross-system direct DML을 금지한다. |
| R-DA-04 | ServiceId→DAO→Mapper→SqlId→Table 추적을 유지한다. |
| R-DA-05 | RDW와 ADW의 Workload 목적을 분리한다. |
| R-DA-06 | TX Manager와 DataSource를 정렬한다. |
| R-DA-07 | Metadata/Lineage를 변경영향 분석에 사용한다. |
| R-DA-08 | Critical Data에 Quality Rule을 둔다. |
| R-DA-09 | Security classification과 lifecycle을 정의한다. |
| R-DA-10 | PDMG Current에서 미확인 Data Flow를 창작하지 않는다. |

---

# 19. Verification / Test

## FIG-07-20. Verification Flow

```text
Architecture Model
   ↓
Static / Config Check
   ↓
Integration Test
   ↓
Failure / Security / Performance Test
   ↓
Runtime Evidence
   ↓
PASS / GAP
```

| Test ID | 검증내용 |
|---|---|
| T-DA-01 | Mapper→SqlId→Table extraction |
| T-DA-02 | TX Manager/DataSource alignment |
| T-DA-03 | Cross-system DML scan |
| T-DA-04 | RDW/ADW workload test |
| T-DA-05 | Data quality rules |
| T-DA-06 | Access/masking/audit |

---

# 20. GAP Register

## FIG-07-21. GAP Lifecycle

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

| GAP ID | GAP | 중요도 | 전환조건 |
|---|---|---|---|
| GAP-DA-01 | 실제 Table/View 전수 Inventory 미완료 | High | Mapper/SQL scan |
| GAP-DA-02 | RDW/ADW Datasource 사용현황 미확정 | High | Config/Mapper inventory |
| GAP-DA-03 | Data Owner/Steward/SOR 전수 미확정 | High | Data Registry |
| GAP-DA-04 | Lineage 자동화 미완료 | High | Metadata scanner |
| GAP-DA-05 | DQ Rule/Runtime evidence 미완료 | Medium/High | DQ catalog/test |

---

# 21. Risk Register

## FIG-07-22. Risk Propagation

```text
Cause
  ↓
Technical Failure
  ↓
Service Impact
  ↓
Operational / Business Impact
```

| Risk ID | Risk | 영향 |
|---|---|---|
| RISK-DA-01 | RDW에 Heavy Query | Online SLA 침해 |
| RISK-DA-02 | Cross-system DML | 강결합/정합성 위험 |
| RISK-DA-03 | Mapper/SQL Trace 부재 | 영향분석 실패 |
| RISK-DA-04 | Owner 없는 데이터 | 품질/변경 책임 불명 |
| RISK-DA-05 | 민감정보 분류 부재 | 보안/감사 위험 |

---

# 22. Architecture Decision / ADR

## FIG-07-23. Decision Flow

```text
Decision Question
   ↓
주안 / 대안
   ↓
장점 / 단점
   ↓
Evidence / Test
   ↓
ADR
   ↓
Baseline
```

| ADR/Task | 의사결정 주제 | 현재 방향 |
|---|---|---|
| ADR-TASK-021 | RDW/ADW 역할분리 | 분리 주안 |
| ADR-TASK-022 | CDC Freshness SLA | Tiered SLA |
| ADR-TASK-023 | Data Ownership | Subject Registry |
| ADR-TASK-024 | Metadata/Lineage/DQ | 자동수집 주안 |
| ADR-TASK-025 | Heavy Query Isolation | ADW 격리 |

---

# 23. Architecture PASS / PDMG Conformance

## FIG-07-24. PASS Model

```text
Architecture Definition
 ↓
PASS

Current Data Conformance
 ↓
PARTIAL / CONDITIONAL

Strong: DAO/MyBatis/JDBC/RDW
Open: ADW/Ownership/Lineage/DQ
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Data access | PASS | DAO→Mapper→JDBC |
| RDW | PASS/PARTIAL | Current evidence |
| ADW | OPEN | actual use inventory |
| Ownership | CONDITIONAL | registry required |
| Lineage | CONDITIONAL | automation required |
| DQ/Security | CONDITIONAL | rules/evidence |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PARTIAL / CONDITIONAL`  
**Runtime Evidence Coverage:** `MEDIUM`

---

# 24. Next Chapter Handoff

## FIG-07-25. 07 → 08

```text
07 DATA
"무엇을 읽고 쓰는가?"
     ↓
08 FRAMEWORK / MECHANISM
"그 업무와 데이터 접근을 공통 Framework가 어떤 Mechanism으로 통제하는가?" 
```

---

# 25. PDMG DATA ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG DATA ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**07장 Architecture Definition 판정: `PASS`**


---



<!-- ============================================================ -->
<!-- CHAPTER 08: FRAMEWORK / MECHANISM -->
<!-- SOURCE: 08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md -->
<!-- ============================================================ -->

# PDMG 전체 아키텍처 정의서
# 08. PDMG FRAMEWORK / MECHANISM ARCHITECTURE
## Filter / Context / Security / TCF / Timeout / Transaction / Error / Logging
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-08-FRAMEWORK-MECHANISM`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-08-01. 이 장의 핵심 질문

```text
PDMG 거래를 공통으로 통제하는 Framework Mechanism은 무엇인가?
Request가 Business로 들어가기 전후 어떤 Control이 실행되는가?
TCF ON/OFF, Context, Error, Timeout의 Current GAP는 무엇인가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-08-02. Evidence Flow

```text
Source / Config
   ↓
Runtime / Deployment
   ↓
PDMG Current Analysis
   ↓
Architecture Decision
   ↓
NSIGHT Target / Working Baseline
   ↓
PASS / GAP / OPEN
```

| Evidence ID | 근거 | 사용목적 | 상태 |
|---|---|---|---|
| EV-08-01 | PDMG Runtime/TCF | Filter→TCF→Handler | [AS-IS] |
| EV-08-02 | Transaction/Timeout | Worker/TX | [AS-IS] |
| EV-08-03 | Message/Context/Error | Context/Error/Log | [AS-IS] |
| EV-08-04 | 03 Application | Framework/Business boundary | [WORKING BASELINE] |
| EV-08-05 | Security | SecurityFilterChain/JWT | [AS-IS + GAP] |

---

# 2. Figure Plan

## FIG-08-03. Top-down Drill-down

```text
L0  Overall
 ↓
L1  Boundary / Responsibility
 ↓
L2  Node / Platform / Component
 ↓
L3  Runtime / Control / Data
 ↓
L4  Sequence / Failure / Recovery
 ↓
L5  Source / Config / Evidence
```

---

# 3. L0 — Framework Mechanism Master

## FIG-08-04. L0 — Framework Mechanism Master

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
Business
 ↓
Response / Error / Log
```

---

# 4. Framework vs Business

## FIG-08-05. Framework vs Business

```text
Framework
= How to execute safely

Business
= What business work to execute

Framework → Handler Contract → Business
```

---

# 5. DefaultFilter

## FIG-08-06. DefaultFilter

```text
Request
 ↓
Body Cache
 ↓
Header / GUID
 ↓
ServiceContext
 ↓
MDC
 ↓
FilterChain
 ↓
finally / clear
```

---

# 6. Security Integration

## FIG-08-07. Security Integration

```text
DefaultFilter
 ↓
SecurityFilterChain
 ↓
Verified Principal?
 ↓
DispatcherServlet
```

---

# 7. MVC / Interceptor

## FIG-08-08. MVC / Interceptor

```text
DispatcherServlet
 ↓
HandlerMapping
 ↓
ServicePreventionInterceptor.preHandle
 ↓
Controller
 ↓
afterCompletion
```

---

# 8. TCF Facade

## FIG-08-09. TCF Facade

```text
OnlineTransactionController
 ↓
TcfFacade
 ├─ TimeoutExecutor
 └─ Dispatcher

STF / ETF
= classes may exist
≠ current executed path unless evidence
```

---

# 9. ServiceId Resolution

## FIG-08-10. ServiceId Resolution

```text
ServiceContext Header
   ↓ precedence
Request Header
   ↓
Path Variable
   ↓
ServiceId / null

Mismatch rejection
= [GAP / PROPOSED]
```

---

# 10. Dispatcher / Handler Registry

## FIG-08-11. Dispatcher / Handler Registry

```text
Spring Beans
TransactionHandler[]
 ↓
Registry
serviceId → Handler
 ↓
Dispatch
```

---

# 11. Timeout / Worker Mechanism

## FIG-08-12. Timeout / Worker Mechanism

```text
Request Thread
 ↓ submit
Worker Pool
 ↓
TransactionTemplate
 ↓
Business
 ↓
Deadline
```

---

# 12. Transaction Mechanism

## FIG-08-13. Transaction Mechanism

```text
Worker
 ↓
TransactionTemplate BEGIN
 ↓
Handler
 ↓
Facade REQUIRED
 ↓
Service / DAO
 ↓
Commit / Rollback
```

---

# 13. Context Propagation

## FIG-08-14. Context Propagation

```text
Request ServiceContext
 ↓ capture
Worker
 ↓ install
Business
 ↓ clear
```

---

# 14. Error / Response

## FIG-08-15. Error / Response

```text
Known Exception
 ↓
GlobalExceptionHandler
 ↓
Standard Error

Filter/Security early error
 ↓
MVC bypass possible
 [GAP]
```

---

# 15. Logging / Evidence

## FIG-08-16. Logging / Evidence

```text
GUID / ServiceId
 ↓
MDC
 ↓
ImageLog PRE/POST/EX
 ↓
Application Log
 ↓
Evidence
```

---

# 16. TCF OFF Mechanism

## FIG-08-17. TCF OFF Mechanism

```text
HTTP
 ↓
Filter / Security / MVC
 ↓
Business Controller
 ↓
Facade / Service
 ↓
DAO

TCF / TimeoutExecutor
= not applied by OFF automatically
```

---

# 17. Framework Anti-pattern

## FIG-08-18. Framework Anti-pattern

```text
Framework
 ──► Customer DAO
 X

Handler
 ──► Mapper
 X

Business
 ──► ThreadLocal lifecycle
 X
```

---

# 18. Architecture Rule Catalog

## FIG-08-19. Rule Set

```text
R-FW-01
Framework는 실행통제를 소유하고 업무규칙을 소유하지 않는다.

R-FW-02
Handler는 Facade를 호출한다.

R-FW-03
Duplicate ServiceId는 startup fail.

R-FW-04
ServiceId source 간 mismatch를 검증한다.

R-FW-05
Context는 Thread lifecycle에 맞게 install/clear한다.

R-FW-06
Timeout은 HTTP response와 DB cancel을 동일시하지 않는다.

R-FW-07
Known error는 표준 envelope로 매핑한다.

R-FW-08
STF/ETF 존재만으로 executed path로 표시하지 않는다.

R-FW-09
TCF OFF에서도 공통 Business Core를 유지한다.

R-FW-10
Framework logging은 fail-safe/fail-open 영향도를 정의한다.
```

| Rule | 정의 |
|---|---|
| R-FW-01 | Framework는 실행통제를 소유하고 업무규칙을 소유하지 않는다. |
| R-FW-02 | Handler는 Facade를 호출한다. |
| R-FW-03 | Duplicate ServiceId는 startup fail. |
| R-FW-04 | ServiceId source 간 mismatch를 검증한다. |
| R-FW-05 | Context는 Thread lifecycle에 맞게 install/clear한다. |
| R-FW-06 | Timeout은 HTTP response와 DB cancel을 동일시하지 않는다. |
| R-FW-07 | Known error는 표준 envelope로 매핑한다. |
| R-FW-08 | STF/ETF 존재만으로 executed path로 표시하지 않는다. |
| R-FW-09 | TCF OFF에서도 공통 Business Core를 유지한다. |
| R-FW-10 | Framework logging은 fail-safe/fail-open 영향도를 정의한다. |

---

# 19. Verification / Test

## FIG-08-20. Verification Flow

```text
Architecture Model
   ↓
Static / Config Check
   ↓
Integration Test
   ↓
Failure / Security / Performance Test
   ↓
Runtime Evidence
   ↓
PASS / GAP
```

| Test ID | 검증내용 |
|---|---|
| T-FW-01 | Filter/context lifecycle |
| T-FW-02 | Security chain order |
| T-FW-03 | ServiceId resolution/mismatch |
| T-FW-04 | Registry duplicate/branch |
| T-FW-05 | TCF ON/OFF parity |
| T-FW-06 | early error contract |
| T-FW-07 | context leak/concurrency |

---

# 20. GAP Register

## FIG-08-21. GAP Lifecycle

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

| GAP ID | GAP | 중요도 | 전환조건 |
|---|---|---|---|
| GAP-FW-01 | ServiceId mismatch rejection 미확정 | High | Controller/Dispatcher validation |
| GAP-FW-02 | Mutable ServiceContext worker 공유 | High | Immutable snapshot |
| GAP-FW-03 | Filter/Security early error envelope | High | Common error filter |
| GAP-FW-04 | TCF OFF timeout/control 차이 | High | Policy/adapter alignment |
| GAP-FW-05 | STF/ETF current runtime 미연결 | Medium | Scope/ADR |
| GAP-FW-06 | Generic exception fallback/Advice order | High | Exception test |

---

# 21. Risk Register

## FIG-08-22. Risk Propagation

```text
Cause
  ↓
Technical Failure
  ↓
Service Impact
  ↓
Operational / Business Impact
```

| Risk ID | Risk | 영향 |
|---|---|---|
| RISK-FW-01 | Context leak | User/trace contamination |
| RISK-FW-02 | TCF ON/OFF policy drift | Different transaction/error behavior |
| RISK-FW-03 | Late worker after timeout | resource/late commit risk |
| RISK-FW-04 | Framework business coupling | reuse/change isolation loss |
| RISK-FW-05 | early sendError | contract inconsistency |

---

# 22. Architecture Decision / ADR

## FIG-08-23. Decision Flow

```text
Decision Question
   ↓
주안 / 대안
   ↓
장점 / 단점
   ↓
Evidence / Test
   ↓
ADR
   ↓
Baseline
```

| ADR/Task | 의사결정 주제 | 현재 방향 |
|---|---|---|
| ADR-TASK-004 | Common Business Core | Facade common boundary |
| ADR-TASK-005 | TCF policy | ON default, OFF exception |
| ADR-TASK-008 | Error Standard | Central taxonomy/envelope |
| ADR-TASK-009 | Worker Context | Immutable snapshot |
| ADR-TASK-017 | Timeout Budget | layered |

---

# 23. Architecture PASS / PDMG Conformance

## FIG-08-24. PASS Model

```text
Architecture Definition
 ↓
PASS

Current Framework Conformance
 ↓
PARTIAL / GAP

Strong: Filter/TCF/Worker/TX
Gap: context, error, OFF parity
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Filter/MVC | PASS | current path |
| TCF/Dispatcher | PASS | current path |
| Worker/TX | PASS/PARTIAL | current snapshot |
| Context | GAP | mutable sharing risk |
| Error | CONDITIONAL | early error bypass |
| TCF OFF | CONDITIONAL | control parity gap |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PARTIAL / GAP`  
**Runtime Evidence Coverage:** `HIGH-MEDIUM`

---

# 24. Next Chapter Handoff

## FIG-08-25. 08 → 09

```text
08 FRAMEWORK / MECHANISM
"어떤 공통 Mechanism이 거래를 통제하는가?"
     ↓
09 ONLINE RUNTIME
"그 Mechanism들이 한 거래에서 실제 어떤 시간순서로 실행되는가?" 
```

---

# 25. PDMG FRAMEWORK / MECHANISM ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG FRAMEWORK / MECHANISM ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**08장 Architecture Definition 판정: `PASS`**


---



<!-- ============================================================ -->
<!-- CHAPTER 09: ONLINE RUNTIME -->
<!-- SOURCE: 09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md -->
<!-- ============================================================ -->

# PDMG 전체 아키텍처 정의서
# 09. PDMG ONLINE RUNTIME ARCHITECTURE
## Request Thread / Worker / ServiceId / Business / DB / Response / Failure
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-09-ONLINE-RUNTIME`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-09-01. 이 장의 핵심 질문

```text
온라인 거래 한 건이 실제 어떤 순서와 Thread/Transaction 경계로 실행되는가?
TCF ON/OFF의 Runtime 차이는 무엇인가?
Timeout/Overload/Error/Security가 시간축에서 어디에 위치하는가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-09-02. Evidence Flow

```text
Source / Config
   ↓
Runtime / Deployment
   ↓
PDMG Current Analysis
   ↓
Architecture Decision
   ↓
NSIGHT Target / Working Baseline
   ↓
PASS / GAP / OPEN
```

| Evidence ID | 근거 | 사용목적 | 상태 |
|---|---|---|---|
| EV-09-01 | IV Online Runtime | End-to-end sequence | [AS-IS] |
| EV-09-02 | V Transaction/Timeout | Thread/TX/timeout | [AS-IS] |
| EV-09-03 | VI Message/Error/Log | response/error/context | [AS-IS] |
| EV-09-04 | VII Security | JWT path | [AS-IS + GAP] |
| EV-09-05 | 03/08 chapters | business/framework boundaries | [WORKING BASELINE] |

---

# 2. Figure Plan

## FIG-09-03. Top-down Drill-down

```text
L0  Overall
 ↓
L1  Boundary / Responsibility
 ↓
L2  Node / Platform / Component
 ↓
L3  Runtime / Control / Data
 ↓
L4  Sequence / Failure / Recovery
 ↓
L5  Source / Config / Evidence
```

---

# 3. L0 — End-to-End Online Runtime

## FIG-09-04. L0 — End-to-End Online Runtime

```text
Browser
 ↓
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
Worker / TransactionTemplate
 ↓
Dispatcher / Handler
 ↓
Facade / Service / DAO / Mapper
 ↓
DB
 ↓
Response Advice / afterCompletion
 ↓
Filter finally
 ↓
HTTP Response
```

---

# 4. Request Thread Timeline

## FIG-09-05. Request Thread Timeline

```text
T0 Request
 ↓ Filter
 ↓ Security
 ↓ MVC
 ↓ Controller
 ↓ submit Worker
 ↓ Future.get(timeout)
 ↓ Response / Exception
 ↓ Cleanup
```

---

# 5. Worker Thread Timeline

## FIG-09-06. Worker Thread Timeline

```text
submit
 ↓
pdmg-online-N
 ↓
Context Install
 ↓
TX BEGIN
 ↓
Dispatch
 ↓
Business
 ↓
DB
 ↓
Deadline
 ↓
Commit/Rollback
 ↓
Context Clear
```

---

# 6. ServiceId Resolution / Routing

## FIG-09-07. ServiceId Resolution / Routing

```text
Header / Context / Path
 ↓
ServiceId
 ↓
Dispatcher Registry
 ↓
Handler
 ↓
handle branch
 ↓
Facade method
```

---

# 7. Business Execution

## FIG-09-08. Business Execution

```text
Handler
 ↓
Facade
 ↓
BizPrePostAspect
 ↓
Service
 ↓
DAO
 ↓
Mapper
 ↓
DB
```

---

# 8. Response Runtime

## FIG-09-09. Response Runtime

```text
Business Result
 ↓
Controller return
 ↓
ResponseBodyAdvice
 ↓
hdr_nhnis + dto
 ↓
afterCompletion
 ↓
Filter finally
```

---

# 9. Known Error Runtime

## FIG-09-10. Known Error Runtime

```text
Exception
 ↓
GlobalExceptionHandler
 ↓
ErrorCode / Type
 ↓
hdr_nhnis + result
 ↓
HTTP status
```

---

# 10. Timeout Runtime

## FIG-09-11. Timeout Runtime

```text
Future.get(5000ms)
 ├─ complete → response
 └─ timeout
      ↓
   cancel(true)
      ↓
   504

Worker may continue
```

---

# 11. Overload Runtime

## FIG-09-12. Overload Runtime

```text
Worker Active = 20
Queue up to 100
 ↓
saturation
 ↓
reject
 ↓
OnlineOverloadException
 ↓
503
```

---

# 12. TCF OFF Runtime

## FIG-09-13. TCF OFF Runtime

```text
HTTP
 ↓
Filter
 ↓
Security
 ↓
MVC
 ↓
Business Controller
 ↓
Facade / Service
 ↓
DAO
 ↓
DB
```

---

# 13. Context Runtime

## FIG-09-14. Context Runtime

```text
Request ThreadLocal
 ↓
Worker capture/install
 ↓
Business
 ↓
ResponseBody stored
 ↓
afterCompletion
 ↓
remove
```

---

# 14. Security Runtime

## FIG-09-15. Security Runtime

```text
Bearer
 ↓
SecurityFilterChain
 ↓
JwtProvider
 ↓
request.ssoId
 ↓
Business Context?
 [GAP]
```

---

# 15. DB Runtime

## FIG-09-16. DB Runtime

```text
Worker
 ↓
Transaction
 ↓
Hikari Connection
 ↓
JDBC Statement
 ↓
DB Session
 ↓
SQL wait / result
```

---

# 16. Runtime Evidence

## FIG-09-17. Runtime Evidence

```text
GUID
+ ServiceId
+ Thread
+ ErrorCode
+ SqlId
+ elapsed
 ↓
Log / ImageLog / Metric
```

---

# 17. Runtime Failure Matrix

## FIG-09-18. Runtime Failure Matrix

```text
Filter fail
Security fail
Routing fail
Worker reject
Timeout
Business reject
DB fail
Response fail
 ↓
Different owner / evidence
```

---

# 18. Architecture Rule Catalog

## FIG-09-19. Rule Set

```text
R-RT-01
Request Thread와 Worker Thread를 분리한다.

R-RT-02
Worker에서 Transaction을 시작한다.

R-RT-03
ServiceId Registry/Branch를 일치시킨다.

R-RT-04
Timeout Response와 Worker 종료를 동일시하지 않는다.

R-RT-05
Response 이후 Context cleanup을 보장한다.

R-RT-06
Known error는 표준 mapping을 적용한다.

R-RT-07
TCF OFF는 별도 runtime path로 명시한다.

R-RT-08
GUID/ServiceId를 end-to-end 유지한다.

R-RT-09
JDBC/DB waits를 request latency와 연계관측한다.

R-RT-10
Runtime path는 Source Evidence로 검증한다.
```

| Rule | 정의 |
|---|---|
| R-RT-01 | Request Thread와 Worker Thread를 분리한다. |
| R-RT-02 | Worker에서 Transaction을 시작한다. |
| R-RT-03 | ServiceId Registry/Branch를 일치시킨다. |
| R-RT-04 | Timeout Response와 Worker 종료를 동일시하지 않는다. |
| R-RT-05 | Response 이후 Context cleanup을 보장한다. |
| R-RT-06 | Known error는 표준 mapping을 적용한다. |
| R-RT-07 | TCF OFF는 별도 runtime path로 명시한다. |
| R-RT-08 | GUID/ServiceId를 end-to-end 유지한다. |
| R-RT-09 | JDBC/DB waits를 request latency와 연계관측한다. |
| R-RT-10 | Runtime path는 Source Evidence로 검증한다. |

---

# 19. Verification / Test

## FIG-09-20. Verification Flow

```text
Architecture Model
   ↓
Static / Config Check
   ↓
Integration Test
   ↓
Failure / Security / Performance Test
   ↓
Runtime Evidence
   ↓
PASS / GAP
```

| Test ID | 검증내용 |
|---|---|
| T-RT-01 | End-to-end happy path trace |
| T-RT-02 | timeout with late worker |
| T-RT-03 | overload reject |
| T-RT-04 | routing unknown serviceId |
| T-RT-05 | business exception |
| T-RT-06 | DB exception/rollback |
| T-RT-07 | TCF ON/OFF parity |
| T-RT-08 | context cleanup |

---

# 20. GAP Register

## FIG-09-21. GAP Lifecycle

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

| GAP ID | GAP | 중요도 | 전환조건 |
|---|---|---|---|
| GAP-RT-01 | Worker context mutable sharing | High | immutable snapshot |
| GAP-RT-02 | JWT identity binding | Critical | principal binding |
| GAP-RT-03 | TCF OFF facade parity | High | controller→facade |
| GAP-RT-04 | generic/early error coverage | High | fault tests |
| GAP-RT-05 | JDBC cancel/query timeout evidence | High | driver/query test |
| GAP-RT-06 | Runtime→deployment correlation | High | deploymentId/host/jvm tags |

---

# 21. Risk Register

## FIG-09-22. Risk Propagation

```text
Cause
  ↓
Technical Failure
  ↓
Service Impact
  ↓
Operational / Business Impact
```

| Risk ID | Risk | 영향 |
|---|---|---|
| RISK-RT-01 | late worker | late DB work after 504 |
| RISK-RT-02 | queue saturation | 503/timeout cascade |
| RISK-RT-03 | context leak | cross-request contamination |
| RISK-RT-04 | security/runtime mismatch | unauthorized/failed requests |
| RISK-RT-05 | DB slow | pool/worker saturation |

---

# 22. Architecture Decision / ADR

## FIG-09-23. Decision Flow

```text
Decision Question
   ↓
주안 / 대안
   ↓
장점 / 단점
   ↓
Evidence / Test
   ↓
ADR
   ↓
Baseline
```

| ADR/Task | 의사결정 주제 | 현재 방향 |
|---|---|---|
| ADR-TASK-004 | Business Core | common facade |
| ADR-TASK-005 | TCF policy | ON default |
| ADR-TASK-009 | Worker Context | immutable |
| ADR-TASK-017 | Timeout | layered |
| ADR-TASK-029 | Pool capacity | end-to-end budget |

---

# 23. Architecture PASS / PDMG Conformance

## FIG-09-24. PASS Model

```text
Architecture Definition
 ↓
PASS

Current Runtime Conformance
 ↓
PARTIAL / CONDITIONAL

Core path confirmed
Critical gaps: context / identity / JDBC timeout evidence
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| TCF ON path | PASS | strong source |
| Request/Worker | PASS | strong source |
| Transaction | PASS/PARTIAL | strong source |
| Timeout | CONDITIONAL | late worker/JDBC |
| Error | CONDITIONAL | early/generic gaps |
| TCF OFF | CONDITIONAL | facade parity |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PARTIAL / CONDITIONAL`  
**Runtime Evidence Coverage:** `HIGH-MEDIUM`

---

# 24. Next Chapter Handoff

## FIG-09-25. 09 → 10

```text
09 ONLINE RUNTIME
"실제로 어떤 순서로 실행되는가?"
     ↓
10 TRANSACTION / TIMEOUT / THREAD / DB
"그 실행의 Thread, Transaction, Timeout, Pool, DB 경계는 정확히 어디인가?" 
```

---

# 25. PDMG ONLINE RUNTIME ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG ONLINE RUNTIME ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**09장 Architecture Definition 판정: `PASS`**


---



<!-- ============================================================ -->
<!-- CHAPTER 10: TRANSACTION / TIMEOUT / THREAD / DB -->
<!-- SOURCE: 10_PDMG_TRANSACTION_TIMEOUT_THREAD_DB_상세정의서_v1.md -->
<!-- ============================================================ -->

# PDMG 전체 아키텍처 정의서
# 10. PDMG TRANSACTION / TIMEOUT / THREAD / DB ARCHITECTURE
## Worker / TransactionTemplate / Deadline / Hikari / JDBC / DB Session
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-10-TRANSACTION-TIMEOUT-THREAD-DB`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-10-01. 이 장의 핵심 질문

```text
Request Thread와 Worker Thread는 어떻게 분리되는가?
실제 Physical Transaction은 어디에서 시작/종료되는가?
Timeout/Overload/JDBC Cancel/Pool Capacity를 어떻게 구분하고 검증하는가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-10-02. Evidence Flow

```text
Source / Config
   ↓
Runtime / Deployment
   ↓
PDMG Current Analysis
   ↓
Architecture Decision
   ↓
NSIGHT Target / Working Baseline
   ↓
PASS / GAP / OPEN
```

| Evidence ID | 근거 | 사용목적 | 상태 |
|---|---|---|---|
| EV-10-01 | V Transaction/Timeout | core source snapshot | [AS-IS] |
| EV-10-02 | IV Runtime | request/worker sequence | [AS-IS] |
| EV-10-03 | VIII Capacity | Tomcat/Hikari/JVM candidates | [CANDIDATE] |
| EV-10-04 | VI Context | worker propagation | [AS-IS + RISK] |
| EV-10-05 | Decision Register | timeout/capacity | [DECISION] |

---

# 2. Figure Plan

## FIG-10-03. Top-down Drill-down

```text
L0  Overall
 ↓
L1  Boundary / Responsibility
 ↓
L2  Node / Platform / Component
 ↓
L3  Runtime / Control / Data
 ↓
L4  Sequence / Failure / Recovery
 ↓
L5  Source / Config / Evidence
```

---

# 3. L0 — Transaction/Timeout Master

## FIG-10-04. L0 — Transaction/Timeout Master

```text
Request Thread
 ↓ submit
Worker Pool
 ↓
TransactionTemplate BEGIN
 ↓
Handler / Facade / Service
 ↓
DAO / Hikari / JDBC
 ↓
DB
 ↓
Deadline
 ├─ commit
 └─ rollback
```

---

# 4. Current Snapshot

## FIG-10-05. Current Snapshot

```text
timeout.enabled = true
milliseconds = 5000
pool-size = 20
queue-capacity = 100

[AS-IS SNAPSHOT]
```

---

# 5. Request vs Worker

## FIG-10-06. Request vs Worker

```text
Request Thread
= HTTP/MVC/Future wait/response

Worker Thread
= Context/TX/Business/DB
```

---

# 6. Executor / Queue

## FIG-10-07. Executor / Queue

```text
20 active workers
   ↓
100 queue capacity
   ↓
reject
   ↓
503 overload
```

---

# 7. Overload vs Timeout

## FIG-10-08. Overload vs Timeout

```text
Overload
= cannot accept work
→ 503

Timeout
= accepted but did not finish in deadline
→ 504
```

---

# 8. Worker Context

## FIG-10-09. Worker Context

```text
Request Context
 ↓ capture
Worker install
 ↓ business
Worker clear

Current same mutable reference risk
```

---

# 9. Physical Transaction Boundary

## FIG-10-10. Physical Transaction Boundary

```text
Worker
 ↓
TransactionTemplate BEGIN
 ├─ Dispatcher
 ├─ Handler
 ├─ Facade REQUIRED joins
 ├─ Service
 ├─ DAO / SQL
 └─ Deadline
 ↓
COMMIT / ROLLBACK
```

---

# 10. Facade @Transactional

## FIG-10-11. Facade @Transactional

```text
Existing TX exists?
 ├─ YES → REQUIRED joins
 └─ NO  → new TX

TCF ON timeout ON
→ outer TransactionTemplate exists
```

---

# 11. Deadline before Commit

## FIG-10-12. Deadline before Commit

```text
DB work finished
 ↓
Deadline check
 ├─ within → commit
 └─ exceeded → rollbackOnly
```

---

# 12. cancel(true) Limitation

## FIG-10-13. cancel(true) Limitation

```text
Request timeout
 ↓
Future.cancel(true)
 ↓
Thread interrupt request

≠ JDBC cancel guarantee
≠ DB session kill
```

---

# 13. Hikari / DB Session Boundary

## FIG-10-14. Hikari / DB Session Boundary

```text
Worker Thread
 ↓ borrow
Hikari Connection
 ↓
JDBC
 ↓
DB Session
 ↓
SQL

Thread count ≠ Pool size ≠ DB session count
```

---

# 14. Query Timeout Hierarchy

## FIG-10-15. Query Timeout Hierarchy

```text
DB Query Timeout
 <
Worker/TX Deadline
 <
Server/Downstream
 <
Client
```

---

# 15. Capacity Chain

## FIG-10-16. Capacity Chain

```text
Tomcat Request Threads
 ↓
PDMG Worker 20
 ↓
Hikari Pool [target OPEN]
 ↓
DB Sessions
 ↓
CPU / IO / Locks
```

---

# 16. Failure / Rollback Cases

## FIG-10-17. Failure / Rollback Cases

```text
Business Exception
DB Exception
Deadline Exceeded
Interrupt
 ↓
Rollback?
 ↓
Evidence / Error Mapping
```

---

# 17. Metrics

## FIG-10-18. Metrics

```text
Tomcat busy
Worker active / queue
Hikari active / pending
DB sessions / waits
SQL latency
Timeout / reject
 ↓
correlate by ServiceId/GUID
```

---

# 18. Architecture Rule Catalog

## FIG-10-19. Rule Set

```text
R-TX-01
Request Thread와 DB Transaction Thread를 구분한다.

R-TX-02
Worker TransactionTemplate이 Current outer TX boundary다.

R-TX-03
Facade REQUIRED는 기존 TX에 참여한다.

R-TX-04
HTTP 504를 DB cancel 완료로 해석하지 않는다.

R-TX-05
Deadline check는 commit 이전에 수행한다.

R-TX-06
Query timeout < transaction deadline < outer/client timeout.

R-TX-07
Worker/Queue/Hikari/DB pool size를 동일하게 맞추지 않는다.

R-TX-08
Overload와 Timeout을 별도 metric/error로 운영한다.

R-TX-09
Worker context는 안전하게 복제/정리한다.

R-TX-10
금융 DML Blind Retry를 금지한다.
```

| Rule | 정의 |
|---|---|
| R-TX-01 | Request Thread와 DB Transaction Thread를 구분한다. |
| R-TX-02 | Worker TransactionTemplate이 Current outer TX boundary다. |
| R-TX-03 | Facade REQUIRED는 기존 TX에 참여한다. |
| R-TX-04 | HTTP 504를 DB cancel 완료로 해석하지 않는다. |
| R-TX-05 | Deadline check는 commit 이전에 수행한다. |
| R-TX-06 | Query timeout < transaction deadline < outer/client timeout. |
| R-TX-07 | Worker/Queue/Hikari/DB pool size를 동일하게 맞추지 않는다. |
| R-TX-08 | Overload와 Timeout을 별도 metric/error로 운영한다. |
| R-TX-09 | Worker context는 안전하게 복제/정리한다. |
| R-TX-10 | 금융 DML Blind Retry를 금지한다. |

---

# 19. Verification / Test

## FIG-10-20. Verification Flow

```text
Architecture Model
   ↓
Static / Config Check
   ↓
Integration Test
   ↓
Failure / Security / Performance Test
   ↓
Runtime Evidence
   ↓
PASS / GAP
```

| Test ID | 검증내용 |
|---|---|
| T-TX-01 | physical TX begin/commit trace |
| T-TX-02 | deadline exceeded rollback |
| T-TX-03 | JDBC query timeout |
| T-TX-04 | future cancel behavior |
| T-TX-05 | overload rejection |
| T-TX-06 | pool saturation |
| T-TX-07 | TX manager/datasource alignment |
| T-TX-08 | late worker evidence |

---

# 20. GAP Register

## FIG-10-21. GAP Lifecycle

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

| GAP ID | GAP | 중요도 | 전환조건 |
|---|---|---|---|
| GAP-TX-01 | DB/JDBC Query Timeout exact value 미확정 | Critical | driver/config test |
| GAP-TX-02 | cancel(true) DB cancel guarantee 없음 | High | statement cancel test |
| GAP-TX-03 | mutable worker context | High | immutable snapshot |
| GAP-TX-04 | Hikari target size 미확정 | High | load/DB test |
| GAP-TX-05 | Tomcat/worker/pool capacity evidence 미완료 | High | load/soak |
| GAP-TX-06 | late worker runtime evidence 미완료 | High | timeout fault test |

---

# 21. Risk Register

## FIG-10-22. Risk Propagation

```text
Cause
  ↓
Technical Failure
  ↓
Service Impact
  ↓
Operational / Business Impact
```

| Risk ID | Risk | 영향 |
|---|---|---|
| RISK-TX-01 | late commit after client timeout | duplicate/reconciliation risk |
| RISK-TX-02 | oversized thread/pool | DB saturation |
| RISK-TX-03 | worker queue buildup | latency cascade |
| RISK-TX-04 | interrupt ignored by JDBC | resource leak/long DB work |
| RISK-TX-05 | wrong TX manager/datasource | transaction inconsistency |

---

# 22. Architecture Decision / ADR

## FIG-10-23. Decision Flow

```text
Decision Question
   ↓
주안 / 대안
   ↓
장점 / 단점
   ↓
Evidence / Test
   ↓
ADR
   ↓
Baseline
```

| ADR/Task | 의사결정 주제 | 현재 방향 |
|---|---|---|
| ADR-TASK-017 | Timeout Budget | layered |
| ADR-TASK-018 | Retry/Idempotency | conditional |
| ADR-TASK-029 | Thread/Worker/Hikari | end-to-end budget |
| ADR-TASK-026 | WAS sizing | load test based |

---

# 23. Architecture PASS / PDMG Conformance

## FIG-10-24. PASS Model

```text
Architecture Definition
 ↓
PASS

Current TX/Timeout Conformance
 ↓
PARTIAL / CONDITIONAL

Known: worker20/queue100/5000ms
Open: query timeout / target pool / cancel
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Worker/TX boundary | PASS | strong source |
| 5000ms snapshot | PASS AS-IS | not target SLA |
| Query timeout | OPEN | exact config needed |
| Cancel semantics | CONDITIONAL | JDBC test needed |
| Hikari target | OPEN | load/DB test |
| Metrics | CONDITIONAL | end-to-end correlation |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PARTIAL / CONDITIONAL`  
**Runtime Evidence Coverage:** `HIGH-MEDIUM`

---

# 24. Next Chapter Handoff

## FIG-10-25. 10 → 11

```text
10 TX / TIMEOUT / THREAD / DB
"실행자원과 DB 경계는 무엇인가?"
     ↓
11 SECURITY / SSO / JWT / SESSION
"그 Runtime의 Identity, Token, Key, Session, Authorization은 어떻게 보호되는가?" 
```

---

# 25. PDMG TRANSACTION / TIMEOUT / THREAD / DB ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG TRANSACTION / TIMEOUT / THREAD / DB ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**10장 Architecture Definition 판정: `PASS`**


---



<!-- ============================================================ -->
<!-- CHAPTER 11: SECURITY / SSO / JWT / SESSION -->
<!-- SOURCE: 11_PDMG_SECURITY_SSO_JWT_SESSION_상세정의서_v1.md -->
<!-- ============================================================ -->

# PDMG 전체 아키텍처 정의서
# 11. PDMG SECURITY / SSO / JWT / SESSION ARCHITECTURE
## Authentication / RS256 / JWKS / Key / Refresh / Identity / Authorization / State
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-11-SECURITY-SSO-JWT-SESSION`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-11-01. 이 장의 핵심 질문

```text
PDMG의 로그인/SSO/JWT/Refresh/JWKS/Key 구조는 무엇인가?
Issuer와 Business Verifier는 같은 Trust Model을 사용하는가?
Trusted Principal, Business User, ServiceId Authorization, Session State를 어떻게 연결하는가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-11-02. Evidence Flow

```text
Source / Config
   ↓
Runtime / Deployment
   ↓
PDMG Current Analysis
   ↓
Architecture Decision
   ↓
NSIGHT Target / Working Baseline
   ↓
PASS / GAP / OPEN
```

| Evidence ID | 근거 | 사용목적 | 상태 |
|---|---|---|---|
| EV-11-01 | VII Security | login/JWT/JWKS/SSO | [AS-IS + GAP] |
| EV-11-02 | pdmg-jwt source analysis | RS256 issue/refresh | [AS-IS] |
| EV-11-03 | pdmg-fw source analysis | HMAC verifier | [AS-IS GAP] |
| EV-11-04 | 02/04 boundaries | trust/runtime node | [WORKING BASELINE] |
| EV-11-05 | Decision Register | JWT/key/session/identity | [DECISION] |

---

# 2. Figure Plan

## FIG-11-03. Top-down Drill-down

```text
L0  Overall
 ↓
L1  Boundary / Responsibility
 ↓
L2  Node / Platform / Component
 ↓
L3  Runtime / Control / Data
 ↓
L4  Sequence / Failure / Recovery
 ↓
L5  Source / Config / Evidence
```

---

# 3. L0 — Security Master

## FIG-11-04. L0 — Security Master

```text
User
 ↓
Login / SSO
 ↓
pdmg-jwt
 ↓
Access / Refresh Token
 ↓
JWKS / Key
 ↓
pdmg-service / pdmg-fw
 ↓
Verification
 ↓
Trusted Principal
 ↓
Authorization
 ↓
Business / DB
```

---

# 4. Security Responsibility

## FIG-11-05. Security Responsibility

```text
pdmg-jwt
= authentication / token issue / jwks

pdmg-fw
= request verification integration

pdmg-service
= business authorization

Ops/Security
= key/secret/audit
```

---

# 5. General Login

## FIG-11-06. General Login

```text
ServiceId mgjwa1000C0
 ↓
Credential Validation
 ↓
BCrypt
 ↓
Token Pair
```

---

# 6. SSO Internal Issue

## FIG-11-07. SSO Internal Issue

```text
ServiceId mgjwa1000C1
 ↓
Allowed Service
 + Timestamp
 + HMAC
 + Caller IP
 ↓
Token Pair

≠ generic OIDC callback
```

---

# 7. Access Token Issue

## FIG-11-08. Access Token Issue

```text
Header alg=RS256 / kid
Claims issuer/audience/sub/exp
 ↓
RSA Private Key Sign
 ↓
Access Token
```

---

# 8. Refresh Token

## FIG-11-09. Refresh Token

```text
Random Refresh Token
 ↓
Hash
 ↓
DB State
 ↓
Rotate / Revoke
```

---

# 9. JWKS / Public Key

## FIG-11-10. JWKS / Public Key

```text
Private Key
 stays issuer side

Public Key
 ↓
JWKS
 ↓
Verifier
```

---

# 10. Critical Current Mismatch

## FIG-11-11. Critical Current Mismatch

```text
pdmg-jwt
RS256 issue
   ↓
JWT
   ↓
pdmg-fw
HMAC jwt.secret verify
   ↓
[CRITICAL GAP]
```

---

# 11. Target Verification

## FIG-11-12. Target Verification

```text
Bearer
 ↓
Parse kid
 ↓
JWKS Public Key
 ↓
RS256 verify
 ↓
issuer/audience/exp
 ↓
Trusted Principal
```

---

# 12. Key Lifecycle

## FIG-11-13. Key Lifecycle

```text
Managed Key Store
 ↓
Versioned Key / kid
 ↓
Issue
 ↓
JWKS
 ↓
Rotate
 ↓
Retire

Restart / Multi-instance / DR consistent
```

---

# 13. Denylist / Revocation

## FIG-11-14. Denylist / Revocation

```text
Logout / revoke
 ↓
Denylist / Refresh state
 ↓
Verifier checks?
 [Current integration GAP]
```

---

# 14. Identity Binding

## FIG-11-15. Identity Binding

```text
JWT sub / ssoId
 ↓
Trusted Principal
 ↓
Business User Context
 ↓ compare
hdr_nhnis optr/user
 ↓
Authorization / Audit
```

---

# 15. Authorization

## FIG-11-16. Authorization

```text
Authentication success
 ≠
ServiceId execution permission

Principal
 ↓
Role / Permission
 ↓
ServiceId / Resource
 ↓
Allow / Deny
```

---

# 16. Session / State Strategy

## FIG-11-17. Session / State Strategy

```text
Client sessionStorage token [AS-IS]
 +
Refresh server state
 +
Possible HttpSession policy

Target
= minimize server session state
  unless required
```

---

# 17. Secrets Separation

## FIG-11-18. Secrets Separation

```text
SSO internal HMAC secret
≠ JWT RSA private key
≠ pdmg-fw HMAC jwt.secret

Never reuse
```

---

# 18. Security Observability

## FIG-11-19. Security Observability

```text
Login success/fail
Token issue/refresh
JWT verify fail
kid/JWKS error
Authorization deny
Key rotation
 ↓
Audit / Alert
```

---

# 19. Architecture Rule Catalog

## FIG-11-20. Rule Set

```text
R-SEC-01
Issuer와 Verifier의 algorithm/key model을 일치시킨다.

R-SEC-02
Private Key는 Issuer/managed key boundary에 제한한다.

R-SEC-03
Verifier는 Public Key/JWKS만 사용하도록 한다.

R-SEC-04
SSO HMAC secret과 JWT key를 분리한다.

R-SEC-05
Authentication ≠ Authorization.

R-SEC-06
Client header user를 trusted identity로 사용하지 않는다.

R-SEC-07
Key rotation/multi-instance/DR를 설계한다.

R-SEC-08
Revocation/Denylist 정책을 verifier와 연결한다.

R-SEC-09
Token/Secret/Password를 로그에 남기지 않는다.

R-SEC-10
Session state와 token state를 명확히 분리한다.
```

| Rule | 정의 |
|---|---|
| R-SEC-01 | Issuer와 Verifier의 algorithm/key model을 일치시킨다. |
| R-SEC-02 | Private Key는 Issuer/managed key boundary에 제한한다. |
| R-SEC-03 | Verifier는 Public Key/JWKS만 사용하도록 한다. |
| R-SEC-04 | SSO HMAC secret과 JWT key를 분리한다. |
| R-SEC-05 | Authentication ≠ Authorization. |
| R-SEC-06 | Client header user를 trusted identity로 사용하지 않는다. |
| R-SEC-07 | Key rotation/multi-instance/DR를 설계한다. |
| R-SEC-08 | Revocation/Denylist 정책을 verifier와 연결한다. |
| R-SEC-09 | Token/Secret/Password를 로그에 남기지 않는다. |
| R-SEC-10 | Session state와 token state를 명확히 분리한다. |

---

# 20. Verification / Test

## FIG-11-21. Verification Flow

```text
Architecture Model
   ↓
Static / Config Check
   ↓
Integration Test
   ↓
Failure / Security / Performance Test
   ↓
Runtime Evidence
   ↓
PASS / GAP
```

| Test ID | 검증내용 |
|---|---|
| T-SEC-01 | RS256 issue→JWKS verify |
| T-SEC-02 | multi-instance token verify |
| T-SEC-03 | key rotation |
| T-SEC-04 | revocation/denylist |
| T-SEC-05 | principal/header mismatch |
| T-SEC-06 | ServiceId authorization |
| T-SEC-07 | secret/token log scan |

---

# 21. GAP Register

## FIG-11-22. GAP Lifecycle

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

| GAP ID | GAP | 중요도 | 전환조건 |
|---|---|---|---|
| GAP-SEC-01 | RS256 issuer vs HMAC verifier | Critical | RS256/JWKS integration |
| GAP-SEC-02 | Key lifecycle/multi-instance consistency | Critical | managed key store/rotation |
| GAP-SEC-03 | Denylist verifier integration | High | revocation test |
| GAP-SEC-04 | Principal↔Business user binding | Critical | binding enforcement |
| GAP-SEC-05 | ServiceId authorization matrix | High | authz registry |
| GAP-SEC-06 | sessionStorage token exposure risk | High | client security review |

---

# 22. Risk Register

## FIG-11-23. Risk Propagation

```text
Cause
  ↓
Technical Failure
  ↓
Service Impact
  ↓
Operational / Business Impact
```

| Risk ID | Risk | 영향 |
|---|---|---|
| RISK-SEC-01 | same kid/different generated key | multi-node token failure |
| RISK-SEC-02 | shared HMAC secret spread | large blast radius |
| RISK-SEC-03 | header spoof | identity/authorization abuse |
| RISK-SEC-04 | denylist not checked | revoked token usable |
| RISK-SEC-05 | token log leakage | credential exposure |

---

# 23. Architecture Decision / ADR

## FIG-11-24. Decision Flow

```text
Decision Question
   ↓
주안 / 대안
   ↓
장점 / 단점
   ↓
Evidence / Test
   ↓
ADR
   ↓
Baseline
```

| ADR/Task | 의사결정 주제 | 현재 방향 |
|---|---|---|
| ADR-TASK-010 | JWT Algorithm | RS256+JWKS |
| ADR-TASK-011 | Key Management | managed/versioned |
| ADR-TASK-012 | Session/Token State | JWT + server refresh/revoke |
| ADR-TASK-013 | Identity Binding | verified principal authoritative |

---

# 24. Architecture PASS / PDMG Conformance

## FIG-11-25. PASS Model

```text
Architecture Definition
 ↓
PASS

Current Security Conformance
 ↓
GAP / CRITICAL

Target is clear
Current issuer/verifier and key lifecycle not aligned
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Login/issue | PASS/PARTIAL | source exists |
| RS256 issue | PASS AS-IS | pdmg-jwt |
| Business verify | FAIL/GAP | HMAC path |
| JWKS target | PASS architecture | integration needed |
| Identity binding | GAP | must enforce |
| Revocation | PARTIAL/GAP | denylist integration |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `GAP / CRITICAL`  
**Runtime Evidence Coverage:** `HIGH`

---

# 25. Next Chapter Handoff

## FIG-11-26. 11 → 12

```text
11 SECURITY
"누구이며 무엇을 할 수 있는가?"
     ↓
12 MESSAGE / CONTEXT / ERROR / LOGGING
"그 Identity와 거래정보가 전문/Header/Context/Error/Log 안에서 어떻게 전달·추적되는가?" 
```

---

# 26. PDMG SECURITY / SSO / JWT / SESSION ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG SECURITY / SSO / JWT / SESSION ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**11장 Architecture Definition 판정: `PASS`**


---



<!-- ============================================================ -->
<!-- CHAPTER 12: MESSAGE / CONTEXT / ERROR / LOGGING -->
<!-- SOURCE: 12_PDMG_MESSAGE_CONTEXT_ERROR_LOGGING_상세정의서_v1.md -->
<!-- ============================================================ -->

# PDMG 전체 아키텍처 정의서
# 12. PDMG STANDARD MESSAGE / CONTEXT / ERROR / LOGGING ARCHITECTURE
## hdr_nhnis / GUID / ServiceContext / Error Taxonomy / ImageLog / Runtime Evidence
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-12-MESSAGE-CONTEXT-ERROR-LOGGING`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-12-01. 이 장의 핵심 질문

```text
거래 Header와 DTO는 어떤 책임을 가지는가?
GUID/ServiceId/ServiceContext는 Request-Worker-Response를 어떻게 연결하는가?
Error/Logging/ImageLog는 어떤 Contract와 Evidence를 제공하는가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-12-02. Evidence Flow

```text
Source / Config
   ↓
Runtime / Deployment
   ↓
PDMG Current Analysis
   ↓
Architecture Decision
   ↓
NSIGHT Target / Working Baseline
   ↓
PASS / GAP / OPEN
```

| Evidence ID | 근거 | 사용목적 | 상태 |
|---|---|---|---|
| EV-12-01 | VI Message/Context/Error/Logging | request/response/context/log | [AS-IS] |
| EV-12-02 | IV/V Runtime | ServiceId/worker/TX context | [AS-IS] |
| EV-12-03 | VII Security | identity/user context | [AS-IS + GAP] |
| EV-12-04 | X Traceability | GUID/ServiceId closed loop | [WORKING BASELINE] |
| EV-12-05 | Decision Register | error/context decisions | [DECISION] |

---

# 2. Figure Plan

## FIG-12-03. Top-down Drill-down

```text
L0  Overall
 ↓
L1  Boundary / Responsibility
 ↓
L2  Node / Platform / Component
 ↓
L3  Runtime / Control / Data
 ↓
L4  Sequence / Failure / Recovery
 ↓
L5  Source / Config / Evidence
```

---

# 3. L0 — Message / Context / Evidence Master

## FIG-12-04. L0 — Message / Context / Evidence Master

```text
HTTP Request
 ↓
{ hdr_nhnis, dto }
 ↓
Header Enrichment
 ↓
ServiceContext
 ↓
MDC / GUID
 ↓
Business Runtime
 ↓
Response / Error
 ↓
ImageLog / Log / Trace
 ↓
Runtime Evidence
```

---

# 4. Standard Request Envelope

## FIG-12-05. Standard Request Envelope

```text
REQUEST
┌────────────────────────────┐
│ hdr_nhnis                  │
│  └─ sys_comm               │
│      ├─ std_gbl_id (GUID)  │
│      ├─ rms_svc_c          │
│      ├─ user/source fields │
│      └─ screen/ip context  │
├────────────────────────────┤
│ dto                        │
└────────────────────────────┘
```

---

# 5. Standard Success / Error

## FIG-12-06. Standard Success / Error

```text
SUCCESS
{ hdr_nhnis, dto }

KNOWN ERROR
{ hdr_nhnis, result }

Header
= processing-time context
≠ raw request echo only
```

---

# 6. Header Lifecycle

## FIG-12-07. Header Lifecycle

```text
Request Header
 ↓
DefaultFilter
 GUID / source context
 ↓
Interceptor
 enrichment
 ↓
Controller
 ServiceId finalization
 ↓
ServiceContext
 ↓
Response Header
```

---

# 7. GUID Architecture

## FIG-12-08. GUID Architecture

```text
std_gbl_id / GUID
 ↓
ServiceContext
 ↓
MDC
 ↓
TransactionContext
 ↓
Worker Context
 ↓
ImageLog
 ↓
Runtime Evidence
```

---

# 8. ServiceId in Message

## FIG-12-09. ServiceId in Message

```text
URL / Path ServiceId
      ↘
Header rms_svc_c
      ↘
ServiceContext
      ↓
TransactionDispatcher

Mismatch
= must reject or resolve explicitly
```

---

# 9. ServiceContext Structure

## FIG-12-10. ServiceContext Structure

```text
ServiceContext
├─ Header
├─ Request / Response ref
├─ User context
├─ GUID
├─ ServiceId
└─ Runtime data

ThreadLocal lifecycle
```

---

# 10. ThreadLocal Lifecycle

## FIG-12-11. ThreadLocal Lifecycle

```text
Filter
 ↓ create
ThreadLocal.set
 ↓ enrich
Worker capture/install
 ↓ execute
Worker clear
 ↓ response
Filter finally
 ↓ remove
```

---

# 11. TransactionContext

## FIG-12-12. TransactionContext

```text
TransactionContext
= ServiceId
+ ServiceContext reference
+ startedAt / elapsed

≠ DB Transaction
```

---

# 12. Worker Context Risk

## FIG-12-13. Worker Context Risk

```text
Request ServiceContext
   ↓ same mutable reference?
Worker
   ↓
Servlet request/response refs
   ↓
[RISK]

Target
→ immutable worker snapshot
```

---

# 13. Error Taxonomy

## FIG-12-14. Error Taxonomy

```text
ServiceHandlerNotFound
→ E9999 / SERVICE / 500

BizException
→ business code / BIZ / 500

OnlineTimeoutException
→ FW_TIMEOUT / COMMON / 504

OnlineOverloadException
→ FW_OVERLOADED / COMMON / 503
```

---

# 14. Early Error Boundary

## FIG-12-15. Early Error Boundary

```text
Filter / Security Error
 ↓
sendError / servlet response
 ↓
MVC Advice bypass possible
 ↓
Standard Envelope not guaranteed
 [GAP]
```

---

# 15. ImageLog

## FIG-12-16. ImageLog

```text
Transaction
 ↓
PRE
 ↓
Business
 ↓
POST / EX
 ↓
TB_FW_IMAGE_LOG
 keyed by GUID

fail-open logging behavior
```

---

# 16. Logging Security

## FIG-12-17. Logging Security

```text
Log
 ├─ GUID
 ├─ ServiceId
 ├─ ErrorCode
 └─ elapsed

Do NOT log
 ├─ token
 ├─ password
 ├─ secret
 └─ sensitive full DTO
```

---

# 17. Runtime Evidence Chain

## FIG-12-18. Runtime Evidence Chain

```text
GUID
+ ServiceId
+ ErrorCode
+ SqlId
+ DeploymentId [target]
+ Host/JVM [target]
 ↓
Evidence Index
```

---

# 18. Architecture Rule Catalog

## FIG-12-19. Rule Set

```text
R-MSG-01
hdr_nhnis는 Framework 공통정보, dto는 Business Payload다.

R-MSG-02
GUID는 trace key이며 auth/business PK/idempotency key가 아니다.

R-MSG-03
Response Header는 처리 후 ServiceContext를 사용한다.

R-MSG-04
ServiceId source mismatch를 통제한다.

R-MSG-05
ThreadLocal은 request/worker lifecycle마다 clear한다.

R-MSG-06
Worker Context는 immutable snapshot을 지향한다.

R-MSG-07
Known error는 stable code/envelope를 사용한다.

R-MSG-08
Filter/Security error도 표준 contract를 지향한다.

R-MSG-09
Sensitive data/token/secret logging을 금지한다.

R-MSG-10
ImageLog는 business transaction과 독립될 수 있음을 명시한다.
```

| Rule | 정의 |
|---|---|
| R-MSG-01 | hdr_nhnis는 Framework 공통정보, dto는 Business Payload다. |
| R-MSG-02 | GUID는 trace key이며 auth/business PK/idempotency key가 아니다. |
| R-MSG-03 | Response Header는 처리 후 ServiceContext를 사용한다. |
| R-MSG-04 | ServiceId source mismatch를 통제한다. |
| R-MSG-05 | ThreadLocal은 request/worker lifecycle마다 clear한다. |
| R-MSG-06 | Worker Context는 immutable snapshot을 지향한다. |
| R-MSG-07 | Known error는 stable code/envelope를 사용한다. |
| R-MSG-08 | Filter/Security error도 표준 contract를 지향한다. |
| R-MSG-09 | Sensitive data/token/secret logging을 금지한다. |
| R-MSG-10 | ImageLog는 business transaction과 독립될 수 있음을 명시한다. |

---

# 19. Verification / Test

## FIG-12-20. Verification Flow

```text
Architecture Model
   ↓
Static / Config Check
   ↓
Integration Test
   ↓
Failure / Security / Performance Test
   ↓
Runtime Evidence
   ↓
PASS / GAP
```

| Test ID | 검증내용 |
|---|---|
| T-MSG-01 | request/response schema |
| T-MSG-02 | GUID propagation |
| T-MSG-03 | ServiceId mismatch |
| T-MSG-04 | ThreadLocal cleanup |
| T-MSG-05 | early filter/security error |
| T-MSG-06 | sensitive log scan |
| T-MSG-07 | ImageLog PRE/POST/EX consistency |

---

# 20. GAP Register

## FIG-12-21. GAP Lifecycle

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

| GAP ID | GAP | 중요도 | 전환조건 |
|---|---|---|---|
| GAP-MSG-01 | Worker mutable context sharing | High | immutable snapshot |
| GAP-MSG-02 | Filter/Security standard error envelope | High | common error handler |
| GAP-MSG-03 | ServiceId mismatch rejection | High | validation rule |
| GAP-MSG-04 | Generic exception fallback/order | High | fault test |
| GAP-MSG-05 | ImageLog DDL/response field governance | Medium/High | DB/schema governance |
| GAP-MSG-06 | Deployment/Host evidence tags | High | observability integration |

---

# 21. Risk Register

## FIG-12-22. Risk Propagation

```text
Cause
  ↓
Technical Failure
  ↓
Service Impact
  ↓
Operational / Business Impact
```

| Risk ID | Risk | 영향 |
|---|---|---|
| RISK-MSG-01 | ThreadLocal leak | cross-request contamination |
| RISK-MSG-02 | full DTO/image log | sensitive data exposure |
| RISK-MSG-03 | GUID misuse as auth/idempotency | security/business error |
| RISK-MSG-04 | error envelope inconsistency | client handling complexity |
| RISK-MSG-05 | logging failure side effects | runtime disturbance |

---

# 22. Architecture Decision / ADR

## FIG-12-23. Decision Flow

```text
Decision Question
   ↓
주안 / 대안
   ↓
장점 / 단점
   ↓
Evidence / Test
   ↓
ADR
   ↓
Baseline
```

| ADR/Task | 의사결정 주제 | 현재 방향 |
|---|---|---|
| ADR-TASK-007 | Standard Message | versioned envelope |
| ADR-TASK-008 | Error Handling | central taxonomy |
| ADR-TASK-009 | Worker Context | immutable snapshot |
| ADR-TASK-035 | Observability | GUID/ServiceId correlation |

---

# 23. Architecture PASS / PDMG Conformance

## FIG-12-24. PASS Model

```text
Architecture Definition
 ↓
PASS

Current Message/Context Conformance
 ↓
PARTIAL / GAP

Strong: hdr_nhnis/GUID/Context/ImageLog
Gap: early error / mutable worker context
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Request envelope | PASS | current contract |
| GUID trace | PASS/PARTIAL | strong source |
| ServiceContext | PASS/PARTIAL | mutable worker risk |
| Error mapping | PASS/PARTIAL | known errors |
| Early errors | GAP | MVC bypass |
| Logging security | CONDITIONAL | masking/evidence needed |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PARTIAL / GAP`  
**Runtime Evidence Coverage:** `HIGH-MEDIUM`

---

# 24. Next Chapter Handoff

## FIG-12-25. 12 → 13

```text
12 MESSAGE / CONTEXT / ERROR / LOGGING
"거래정보와 증적은 어떻게 흐르는가?"
     ↓
13 EVENT / CDC / ETL / BATCH / FILE / CACHE
"온라인 이외 실행/데이터 이동 Mechanism은 PDMG와 어떤 관계를 가지는가?" 
```

---

# 25. PDMG STANDARD MESSAGE / CONTEXT / ERROR / LOGGING ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG STANDARD MESSAGE / CONTEXT / ERROR / LOGGING ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**12장 Architecture Definition 판정: `PASS`**


---



<!-- ============================================================ -->
<!-- CHAPTER 13: EVENT / CDC / ETL / BATCH / FILE / CACHE -->
<!-- SOURCE: 13_PDMG_EVENT_CDC_ETL_BATCH_FILE_CACHE_상세정의서_v1.md -->
<!-- ============================================================ -->

# PDMG 전체 아키텍처 정의서
# 13. PDMG EVENT / CDC / ETL / BATCH / FILE / CACHE ARCHITECTURE
## Non-Online Runtime / Data Movement / Recovery / Idempotency / Evidence
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-13-EVENT-CDC-ETL-BATCH-FILE-CACHE`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-13-01. 이 장의 핵심 질문

```text
온라인 이외의 Event/CDC/ETL/Batch/File/Cache는 PDMG와 어떤 관계인가?
어떤 부분이 Current이고 어떤 부분이 NSIGHT Platform Reference인가?
재처리/Replay/Restart/Idempotency/Reconciliation을 어떻게 정의하는가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-13-02. Evidence Flow

```text
Source / Config
   ↓
Runtime / Deployment
   ↓
PDMG Current Analysis
   ↓
Architecture Decision
   ↓
NSIGHT Target / Working Baseline
   ↓
PASS / GAP / OPEN
```

| Evidence ID | 근거 | 사용목적 | 상태 |
|---|---|---|---|
| EV-13-01 | 06 Interface | purpose-driven mechanism | [WORKING BASELINE] |
| EV-13-02 | 07 Data | CDC/ETL relation | [TARGET REFERENCE] |
| EV-13-03 | NSIGHT Big Picture | Event/CDC/ETL/File strategy | [TARGET REFERENCE] |
| EV-13-04 | PDMG source set | current non-online evidence check | [PARTIAL/UNKNOWN] |
| EV-13-05 | Decision Register | event/bulk decisions | [DECISION] |

---

# 2. Figure Plan

## FIG-13-03. Top-down Drill-down

```text
L0  Overall
 ↓
L1  Boundary / Responsibility
 ↓
L2  Node / Platform / Component
 ↓
L3  Runtime / Control / Data
 ↓
L4  Sequence / Failure / Recovery
 ↓
L5  Source / Config / Evidence
```

---

# 3. L0 — Non-Online Runtime Map

## FIG-13-04. L0 — Non-Online Runtime Map

```text
PDMG Core Current
= Online / HTTP / DB

NSIGHT Broader Runtime
├─ Event
├─ CDC
├─ ETL
├─ Batch
├─ File
└─ Cache

Current PDMG direct ownership
= evidence-dependent
```

---

# 4. Current vs Reference Boundary

## FIG-13-05. Current vs Reference Boundary

```text
PDMG Source confirmed?
 ├─ YES → [AS-IS]
 └─ NO
      ↓
NSIGHT platform relation?
 ├─ YES → [REFERENCE]
 └─ NO → [UNKNOWN]
```

---

# 5. Event Architecture

## FIG-13-06. Event Architecture

```text
Producer
 ↓
Event Schema
 ↓
Broker
 ↓
Consumer Group
 ↓
Processing
 ↓
DLQ / Replay

PDMG current implementation
= [OPEN]
```

---

# 6. CDC Architecture

## FIG-13-07. CDC Architecture

```text
Source DB
 ↓ capture
Trail / Queue
 ↓ relay
Apply
 ↓
RDW / Target

Freshness SLA
= [CONFLICT 3s vs 30s]
```

---

# 7. ETL Architecture

## FIG-13-08. ETL Architecture

```text
Source
 ↓ Extract
Transform
 ↓ Load
Target
 ↓ Reconcile

Online resource
= isolate
```

---

# 8. Batch Architecture

## FIG-13-09. Batch Architecture

```text
Scheduler
 ↓
Job
 ↓
Step
 ↓
DB / File / API
 ↓
Checkpoint / Restart
 ↓
Result / Evidence
```

---

# 9. File Architecture

## FIG-13-10. File Architecture

```text
Sender
 ↓
MFT/FOS
 ↓
Landing
 ↓
Validation
 ↓
Processing
 ↓
Archive / Quarantine
```

---

# 10. Cache Architecture

## FIG-13-11. Cache Architecture

```text
Business Read
 ↓
Cache?
 ├─ HIT → result
 └─ MISS → source
            ↓
          update cache

Consistency / TTL / invalidation
must be explicit
```

---

# 11. Retry / Replay / Reconciliation

## FIG-13-12. Retry / Replay / Reconciliation

```text
Transient failure
 ↓
Retry / Backoff

Event/File/Batch
 ↓
Replay / Restart
 ↓
Reconciliation
 ↓
Manual recovery if needed
```

---

# 12. Idempotency

## FIG-13-13. Idempotency

```text
Redelivery / Restart
 ↓
Idempotency Key / Business Key
 ↓
Already processed?
 ├─ YES → suppress
 └─ NO  → process
```

---

# 13. Operations

## FIG-13-14. Operations

```text
Event lag
CDC lag
Batch job state
File status
Cache hit ratio
 ↓
Metric / Alert / Runbook
```

---

# 14. PDMG Integration Handoff

## FIG-13-15. PDMG Integration Handoff

```text
PDMG Business
 ↓
Approved Contract
 ↓
Event / CDC / ETL / Batch / File
 ↓
Platform

PDMG business code
≠ platform implementation ownership by default
```

---

# 15. Failure Isolation

## FIG-13-16. Failure Isolation

```text
Online
  X
  should not wait for
  long batch / ETL

Event / Batch / File
 ↓
buffer / retry / checkpoint
```

---

# 16. Evidence Classification

## FIG-13-17. Evidence Classification

```text
Current Source Evidence
   ↓
AS-IS

Architecture Need only
   ↓
PROPOSED / REFERENCE

No Evidence
   ↓
UNKNOWN
```

---

# 17. Architecture Rule Catalog

## FIG-13-18. Rule Set

```text
R-NON-01
PDMG Current에 없는 Mechanism을 AS-IS로 창작하지 않는다.

R-NON-02
Event는 Schema/Replay/DLQ를 가진다.

R-NON-03
CDC는 capture/apply/lag를 추적한다.

R-NON-04
ETL/Batch는 Online 자원과 격리한다.

R-NON-05
File은 landing/validation/archive/quarantine을 정의한다.

R-NON-06
Cache는 TTL/invalidation/source-of-truth를 정의한다.

R-NON-07
Retry 가능한 쓰기는 idempotency를 가진다.

R-NON-08
Replay/Restart 후 reconciliation을 수행한다.

R-NON-09
Platform failure가 Online transaction에 무제한 전파되지 않도록 한다.

R-NON-10
모든 non-online runtime은 job/event/file identity를 갖는다.
```

| Rule | 정의 |
|---|---|
| R-NON-01 | PDMG Current에 없는 Mechanism을 AS-IS로 창작하지 않는다. |
| R-NON-02 | Event는 Schema/Replay/DLQ를 가진다. |
| R-NON-03 | CDC는 capture/apply/lag를 추적한다. |
| R-NON-04 | ETL/Batch는 Online 자원과 격리한다. |
| R-NON-05 | File은 landing/validation/archive/quarantine을 정의한다. |
| R-NON-06 | Cache는 TTL/invalidation/source-of-truth를 정의한다. |
| R-NON-07 | Retry 가능한 쓰기는 idempotency를 가진다. |
| R-NON-08 | Replay/Restart 후 reconciliation을 수행한다. |
| R-NON-09 | Platform failure가 Online transaction에 무제한 전파되지 않도록 한다. |
| R-NON-10 | 모든 non-online runtime은 job/event/file identity를 갖는다. |

---

# 18. Verification / Test

## FIG-13-19. Verification Flow

```text
Architecture Model
   ↓
Static / Config Check
   ↓
Integration Test
   ↓
Failure / Security / Performance Test
   ↓
Runtime Evidence
   ↓
PASS / GAP
```

| Test ID | 검증내용 |
|---|---|
| T-NON-01 | event duplicate/replay |
| T-NON-02 | CDC lag/failover |
| T-NON-03 | ETL restart/reconcile |
| T-NON-04 | batch restart/checkpoint |
| T-NON-05 | file duplicate/quarantine |
| T-NON-06 | cache expiry/invalidation |

---

# 19. GAP Register

## FIG-13-20. GAP Lifecycle

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

| GAP ID | GAP | 중요도 | 전환조건 |
|---|---|---|---|
| GAP-NON-01 | PDMG Event current scope | High | source/inventory scan |
| GAP-NON-02 | PDMG Batch current scope | High | job inventory |
| GAP-NON-03 | PDMG File current scope | Medium/High | interface inventory |
| GAP-NON-04 | Cache current scope/policy | Medium | source/config scan |
| GAP-NON-05 | CDC SLA conflict | High | ADR/SLA tier |
| GAP-NON-06 | Replay/Reconcile evidence | High | recovery tests |

---

# 20. Risk Register

## FIG-13-21. Risk Propagation

```text
Cause
  ↓
Technical Failure
  ↓
Service Impact
  ↓
Operational / Business Impact
```

| Risk ID | Risk | 영향 |
|---|---|---|
| RISK-NON-01 | Online+Batch resource sharing | SLA interference |
| RISK-NON-02 | Event duplicate | duplicate business action |
| RISK-NON-03 | CDC lag unnoticed | stale data |
| RISK-NON-04 | file partial/duplicate | data inconsistency |
| RISK-NON-05 | cache stale data | wrong business result |

---

# 21. Architecture Decision / ADR

## FIG-13-22. Decision Flow

```text
Decision Question
   ↓
주안 / 대안
   ↓
장점 / 단점
   ↓
Evidence / Test
   ↓
ADR
   ↓
Baseline
```

| ADR/Task | 의사결정 주제 | 현재 방향 |
|---|---|---|
| ADR-TASK-019 | Event Platform | standard broker candidate |
| ADR-TASK-020 | Bulk/File | ETL/MFT separation |
| ADR-TASK-022 | CDC SLA | tiered freshness |
| ADR-TASK-018 | Retry/Idempotency | conditional |

---

# 22. Architecture PASS / PDMG Conformance

## FIG-13-23. PASS Model

```text
Architecture Definition
 ↓
CONDITIONAL PASS

Current PDMG Conformance
 ↓
OPEN / PARTIAL

Reason: broader mechanisms are well-defined target patterns,
but PDMG current ownership is not fully evidenced
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Event | OPEN | current source inventory |
| CDC | REFERENCE | NSIGHT data platform |
| ETL | REFERENCE | bulk data |
| Batch | OPEN/PARTIAL | current inventory needed |
| File | OPEN | interface inventory |
| Cache | OPEN | source/config needed |

**Architecture Definition:** `CONDITIONAL PASS`  
**Current PDMG Conformance:** `OPEN / PARTIAL`  
**Runtime Evidence Coverage:** `LOW-MEDIUM`

---

# 23. Next Chapter Handoff

## FIG-13-24. 13 → 14

```text
13 NON-ONLINE RUNTIME
"온라인 외 실행형태는 어떻게 분리되는가?"
     ↓
14 DEVOPS / OM / OBSERVABILITY
"Source와 Runtime을 어떻게 Build/Deploy/운영/관측하고 Evidence로 남기는가?" 
```

---

# 24. PDMG EVENT / CDC / ETL / BATCH / FILE / CACHE ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG EVENT / CDC / ETL / BATCH / FILE / CACHE ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**13장 Architecture Definition 판정: `CONDITIONAL PASS`**


---



<!-- ============================================================ -->
<!-- CHAPTER 14: DEVOPS / DEPLOYMENT / OM / OBSERVABILITY -->
<!-- SOURCE: 14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md -->
<!-- ============================================================ -->

# PDMG 전체 아키텍처 정의서
# 14. PDMG DEVOPS / DEPLOYMENT / OM / OBSERVABILITY ARCHITECTURE
## SCM / Build / Artifact / Config / Deploy / Control Plane / Metric-Log-Trace / Evidence
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-14-DEVOPS-OM-OBSERVABILITY`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-14-01. 이 장의 핵심 질문

```text
PDMG Source가 어떻게 Build/Artifact/Deployment/Runtime으로 이동하는가?
OM Control Plane과 Business Runtime을 어떻게 분리하는가?
ServiceId/GUID/DeploymentId를 Metric/Log/Trace와 어떻게 연결하는가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-14-02. Evidence Flow

```text
Source / Config
   ↓
Runtime / Deployment
   ↓
PDMG Current Analysis
   ↓
Architecture Decision
   ↓
NSIGHT Target / Working Baseline
   ↓
PASS / GAP / OPEN
```

| Evidence ID | 근거 | 사용목적 | 상태 |
|---|---|---|---|
| EV-14-01 | IX DevOps/OM/Observability | strategy/current separation | [AS-IS + STRATEGY] |
| EV-14-02 | PDMG build evidence | Java21/SB3.5.14/Gradle | [AS-IS] |
| EV-14-03 | VI/VIII/X | GUID/infra/traceability | [WORKING BASELINE] |
| EV-14-04 | Decision Register | CI/deploy/obs/OM/backup | [DECISION] |
| EV-14-05 | pdmg-om evidence | current detail | [UNKNOWN] |

---

# 2. Figure Plan

## FIG-14-03. Top-down Drill-down

```text
L0  Overall
 ↓
L1  Boundary / Responsibility
 ↓
L2  Node / Platform / Component
 ↓
L3  Runtime / Control / Data
 ↓
L4  Sequence / Failure / Recovery
 ↓
L5  Source / Config / Evidence
```

---

# 3. L0 — DevOps / Operations Master

## FIG-14-04. L0 — DevOps / Operations Master

```text
Source
 ↓
Build / Test
 ↓
Artifact
 ↓
Config / Secret
 ↓
Deployment
 ↓
Runtime
 ↓
Metric / Log / Trace
 ↓
Alert / Runbook
 ↓
Evidence
 ↓
Drift / ADR
```

---

# 4. Current vs Strategy Classification

## FIG-14-05. Current vs Strategy Classification

```text
PDMG Current
Java 21
Spring Boot 3.5.14
Gradle multi-project
Log / GUID / ImageLog

NSIGHT Strategy
GitLab
GitLab Runner
eCAMS
IaaS
APM / integrated log

Do not auto-mix
```

---

# 5. SCM / Build

## FIG-14-06. SCM / Build

```text
Git Source
 ↓
Commit
 ↓
Gradle
 ↓
Compile / Unit Test
 ↓
WAR / Artifact
 ↓
Hash
```

---

# 6. CI Gate

## FIG-14-07. CI Gate

```text
Commit
 ↓
Build
 ↓
Unit
 ↓
Naming / Dependency
 ↓
Contract / Security
 ↓
Architecture Rule
 ↓
Artifact
```

---

# 7. Artifact Promotion

## FIG-14-08. Artifact Promotion

```text
Build once
 ↓
Immutable Artifact
 ↓
DEV
 ↓
TEST
 ↓
PROD
 ↓
DR

Hash identical
```

---

# 8. Config / Secret Boundary

## FIG-14-09. Config / Secret Boundary

```text
Artifact
= code/binary

Environment Config
= external

Secret / Key
= protected store

Never bundle generic secret
```

---

# 9. Production Deployment

## FIG-14-10. Production Deployment

```text
Approved Release Manifest
 ↓
Change Approval
 ↓
Deploy
 ↓
Health Check
 ↓
Smoke / Runtime Evidence
 ├─ PASS → promote
 └─ FAIL → rollback
```

---

# 10. Deployment Trace

## FIG-14-11. Deployment Trace

```text
sourceCommit
 ↓
buildId
 ↓
artifactHash
 ↓
deploymentId
 ↓
WAR/JVM/Host
 ↓
ServiceId/GUID
```

---

# 11. OM Control Plane

## FIG-14-12. OM Control Plane

```text
Runtime Plane
pdmg-ui/jwt/service
  ↓ metric/log/control
Control Plane
OM / Monitoring / Deployment

pdmg-om current detail
= UNKNOWN
```

---

# 12. Observability Stack

## FIG-14-13. Observability Stack

```text
Metric
+ Structured Log
+ Trace
 ↓
ServiceId / GUID
 ↓
JVM / Host / Deployment
 ↓
Dashboard / Alert
```

---

# 13. Thread / Pool Monitoring

## FIG-14-14. Thread / Pool Monitoring

```text
Tomcat busy
 ↓
Worker active / queue
 ↓
Hikari active / pending
 ↓
DB sessions / waits
 ↓
SQL latency
```

---

# 14. Security Monitoring

## FIG-14-15. Security Monitoring

```text
Login fail
JWT verify fail
kid/JWKS fail
authorization deny
key rotation
 ↓
Security Alert
```

---

# 15. Incident / Runbook

## FIG-14-16. Incident / Runbook

```text
Alert
 ↓
Triage
 ↓
ServiceId / GUID
 ↓
Node / Pool / SQL
 ↓
Mitigation
 ↓
Recovery
 ↓
Problem / ADR
```

---

# 16. Backup / Restore Operations

## FIG-14-17. Backup / Restore Operations

```text
Backup Job
 ↓
Retention
 ↓
Restore Drill
 ↓
Data Consistency
 ↓
Application Validation
```

---

# 17. Runtime Evidence

## FIG-14-18. Runtime Evidence

```text
Architecture Rule
 ↓
Runtime Metric / Trace / Test
 ↓
Evidence ID
 ↓
Gate
 ↓
Baseline PASS
```

---

# 18. Drift Detection

## FIG-14-19. Drift Detection

```text
Architecture / Config Baseline
 ↓ compare
Actual Source / Deployment / Runtime
 ↓
Drift
 ↓
GAP / ADR / Fix
```

---

# 19. Architecture Rule Catalog

## FIG-14-20. Rule Set

```text
R-OPS-01
Build once, immutable artifact를 promotion한다.

R-OPS-02
SourceCommit→ArtifactHash→DeploymentId를 추적한다.

R-OPS-03
Config와 Secret을 Artifact에서 분리한다.

R-OPS-04
OM Control Plane과 Business Runtime Plane을 분리한다.

R-OPS-05
GitLab/Runner/eCAMS 전략을 PDMG current implementation으로 자동표시하지 않는다.

R-OPS-06
Metric/Log/Trace를 ServiceId/GUID와 연결한다.

R-OPS-07
Thread/Worker/Hikari/DB pool을 분리 관측한다.

R-OPS-08
Backup 성공 외 Restore/Business Validation을 수행한다.

R-OPS-09
Deployment 후 runtime evidence를 수집한다.

R-OPS-10
Critical drift는 release gate에서 차단한다.
```

| Rule | 정의 |
|---|---|
| R-OPS-01 | Build once, immutable artifact를 promotion한다. |
| R-OPS-02 | SourceCommit→ArtifactHash→DeploymentId를 추적한다. |
| R-OPS-03 | Config와 Secret을 Artifact에서 분리한다. |
| R-OPS-04 | OM Control Plane과 Business Runtime Plane을 분리한다. |
| R-OPS-05 | GitLab/Runner/eCAMS 전략을 PDMG current implementation으로 자동표시하지 않는다. |
| R-OPS-06 | Metric/Log/Trace를 ServiceId/GUID와 연결한다. |
| R-OPS-07 | Thread/Worker/Hikari/DB pool을 분리 관측한다. |
| R-OPS-08 | Backup 성공 외 Restore/Business Validation을 수행한다. |
| R-OPS-09 | Deployment 후 runtime evidence를 수집한다. |
| R-OPS-10 | Critical drift는 release gate에서 차단한다. |

---

# 20. Verification / Test

## FIG-14-21. Verification Flow

```text
Architecture Model
   ↓
Static / Config Check
   ↓
Integration Test
   ↓
Failure / Security / Performance Test
   ↓
Runtime Evidence
   ↓
PASS / GAP
```

| Test ID | 검증내용 |
|---|---|
| T-OPS-01 | pipeline inventory/conformance |
| T-OPS-02 | artifact hash promotion |
| T-OPS-03 | secret scan |
| T-OPS-04 | deployment rollback |
| T-OPS-05 | observability correlation |
| T-OPS-06 | alert/runbook |
| T-OPS-07 | restore drill |
| T-OPS-08 | config drift |

---

# 21. GAP Register

## FIG-14-22. GAP Lifecycle

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

| GAP ID | GAP | 중요도 | 전환조건 |
|---|---|---|---|
| GAP-OPS-01 | 실제 CI pipeline inventory | High | repository/pipeline scan |
| GAP-OPS-02 | eCAMS production job current evidence | Medium/High | deployment inventory |
| GAP-OPS-03 | pdmg-om current implementation | High | source/runtime evidence |
| GAP-OPS-04 | Artifact→Host/JVM/WAR trace | Critical | deployment manifest |
| GAP-OPS-05 | metric/log/trace integrated correlation | High | observability implementation |
| GAP-OPS-06 | restore/DR evidence | High | drill |

---

# 22. Risk Register

## FIG-14-23. Risk Propagation

```text
Cause
  ↓
Technical Failure
  ↓
Service Impact
  ↓
Operational / Business Impact
```

| Risk ID | Risk | 영향 |
|---|---|---|
| RISK-OPS-01 | environment rebuild | different binary in prod |
| RISK-OPS-02 | secret in source/artifact | credential leakage |
| RISK-OPS-03 | monitoring only CPU | business root cause hidden |
| RISK-OPS-04 | pdmg-om assumed implemented | false baseline |
| RISK-OPS-05 | manual deployment | human error/drift |

---

# 23. Architecture Decision / ADR

## FIG-14-24. Decision Flow

```text
Decision Question
   ↓
주안 / 대안
   ↓
장점 / 단점
   ↓
Evidence / Test
   ↓
ADR
   ↓
Baseline
```

| ADR/Task | 의사결정 주제 | 현재 방향 |
|---|---|---|
| ADR-TASK-033 | CI Orchestration | GitLab Runner primary candidate |
| ADR-TASK-034 | Artifact Promotion | immutable artifact |
| ADR-TASK-035 | Observability | metric+log+trace |
| ADR-TASK-036 | OM Control Plane | separate control plane |
| ADR-TASK-037 | Config/Secret | externalized/protected |
| ADR-TASK-038 | Backup/Restore | restore validation |
| ADR-TASK-039 | Production Release | manifest+health+rollback |

---

# 24. Architecture PASS / PDMG Conformance

## FIG-14-25. PASS Model

```text
Architecture Definition
 ↓
PASS

Current DevOps/Ops Conformance
 ↓
PARTIAL / OPEN

Strong: build/runtime clues
Open: pdmg-om, production pipeline, deployment evidence
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Build | PASS/PARTIAL | Java/Gradle |
| CI | OPEN/PARTIAL | strategy vs actual pipeline |
| Artifact promotion | PASS architecture | implementation evidence needed |
| OM | OPEN | pdmg-om unknown |
| Observability | PARTIAL | GUID/MDC/ImageLog |
| Backup/Restore | CONDITIONAL | restore drill |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PARTIAL / OPEN`  
**Runtime Evidence Coverage:** `MEDIUM`

---

# 25. Next Chapter Handoff

## FIG-14-26. 14 → 15

```text
14 DEVOPS / OM / OBSERVABILITY
"어떻게 배포하고 운영하며 증명하는가?"
     ↓
15 NAMING / CODE / DEVELOPMENT STANDARD
"모든 Architecture Object를 어떤 이름과 개발규칙으로 일관되게 구현하는가?" 
```

---

# 26. PDMG DEVOPS / DEPLOYMENT / OM / OBSERVABILITY ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG DEVOPS / DEPLOYMENT / OM / OBSERVABILITY ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**14장 Architecture Definition 판정: `PASS`**


---



<!-- ============================================================ -->
<!-- CHAPTER 15: NAMING / APPLICATION CODE / DEVELOPMENT STANDARD -->
<!-- SOURCE: 15_PDMG_NAMING_CODE_DEVELOPMENT_STANDARD_상세정의서_v1.md -->
<!-- ============================================================ -->

# PDMG 전체 아키텍처 정의서
# 15. PDMG NAMING / APPLICATION CODE / DEVELOPMENT STANDARD
## Program / ServiceId / Package / Class / Mapper / Registry / CI Conformance
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-15-NAMING-CODE-DEVELOPMENT-STANDARD`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-15-01. 이 장의 핵심 질문

```text
PDMG의 Business/Application/Program/ServiceId/Source/Mapper/Artifact를 어떤 식별축으로 연결할 것인가?
어떤 Naming은 Current Fact이고 어떤 Enterprise Naming은 아직 OPEN인가?
개발표준을 CI에서 어떻게 자동검증할 것인가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-15-02. Evidence Flow

```text
Source / Config
   ↓
Runtime / Deployment
   ↓
PDMG Current Analysis
   ↓
Architecture Decision
   ↓
NSIGHT Target / Working Baseline
   ↓
PASS / GAP / OPEN
```

| Evidence ID | 근거 | 사용목적 | 상태 |
|---|---|---|---|
| EV-15-01 | X Naming/Traceability | ServiceId/package/registry | [AS-IS] |
| EV-15-02 | Appendix F Naming | enterprise naming framework | [TARGET REFERENCE] |
| EV-15-03 | Application Code Definition | application code structure | [WORKING BASELINE] |
| EV-15-04 | 03 Application | class/package/mapper | [WORKING BASELINE] |
| EV-15-05 | Decision Register | code registry/trace | [DECISION] |

---

# 2. Figure Plan

## FIG-15-03. Top-down Drill-down

```text
L0  Overall
 ↓
L1  Boundary / Responsibility
 ↓
L2  Node / Platform / Component
 ↓
L3  Runtime / Control / Data
 ↓
L4  Sequence / Failure / Recovery
 ↓
L5  Source / Config / Evidence
```

---

# 3. L0 — Naming Trace Backbone

## FIG-15-04. L0 — Naming Trace Backbone

```text
Business Meaning
 ↓
Classification Code
 ↓
Program ID
 ↓
ServiceId
 ↓
Package / Class
 ↓
Mapper / SqlId
 ↓
Data
 ↓
Artifact / Deployment
 ↓
Runtime Evidence
```

---

# 4. Program ID

## FIG-15-05. Program ID

```text
mg | co | a | 9001
= 9 chars

2 + 2 + 1 + 4
```

---

# 5. ServiceId

## FIG-15-06. ServiceId

```text
mg | co | a | 9001 | S | 0
= 11 chars

Transaction Type
S/C/U/D/A/R
```

---

# 6. ServiceId Regex

## FIG-15-07. ServiceId Regex

```text
General
^[a-z]{2}[a-z]{2}[a-z][0-9]{4}[SCUDAR][0-9A-Z]$

MG
^mg[a-z]{2}[a-z][0-9]{4}[SCUDAR][0-9A-Z]$
```

---

# 7. Package Naming

## FIG-15-08. Package Naming

```text
Business Axis
MG / CO / A
 ↓
Java
nhnis.mg.co.a

Mapper
rdw.mg.co.a
```

---

# 8. Class Naming

## FIG-15-09. Class Naming

```text
Program Stem
mgcoa9001
 ├─ Handler
 ├─ Controller
 ├─ Facade
 ├─ Service
 ├─ DAO
 └─ DTO by ServiceId
```

---

# 9. Mapper Naming

## FIG-15-10. Mapper Naming

```text
DAO FQCN
 ↔ Mapper namespace

Program
mgcoa9001
 ↔ mgcoa9001-ORA.xml

SqlId
service-oriented suffix
```

---

# 10. Current Registry — 13 ServiceIds

## FIG-15-11. Current Registry — 13 ServiceIds

```text
5530S0
8888S0 / D0
9000S0/C0/U0/D0
9001S0/C0/U0/D0
9100S0
9999S0

TOTAL 13
```

---

# 11. UI Catalog Drift

## FIG-15-12. UI Catalog Drift

```text
UI Transaction Catalog
 ↓ compare
Backend Handler Registry
 ↓
MATCH / DRIFT

UI catalog alone
≠ SSOT
```

---

# 12. Identifier Separation

## FIG-15-13. Identifier Separation

```text
ProgramId
≠ ServiceId
≠ InterfaceId
≠ GUID
≠ SqlId
≠ ArtifactHash
≠ DeploymentId
```

---

# 13. Config Naming

## FIG-15-14. Config Naming

```text
nhnis.fw.*
 ↓
framework configuration namespace

Business config
 ↓
application/domain namespace

Secret
≠ ordinary config
```

---

# 14. Artifact / Deployment Naming

## FIG-15-15. Artifact / Deployment Naming

```text
SourceCommit
 ↓
BuildId
 ↓
ArtifactHash
 ↓
DeploymentId

Exact enterprise syntax
= [OPEN]
```

---

# 15. Infrastructure Naming

## FIG-15-16. Infrastructure Naming

```text
Logical Node
 ↓
Environment
 ↓
Host / VM / JVM

Exact hostname convention
= [OPEN / enterprise standard]
```

---

# 16. Registry Governance

## FIG-15-17. Registry Governance

```text
Business Code Registry
Program Registry
ServiceId Registry
Interface Registry
Data Object Registry
Artifact Registry
Deployment Registry
Infrastructure Registry
```

---

# 17. Static Naming Scan

## FIG-15-18. Static Naming Scan

```text
Source
 ↓
Program parser
 ↓
Package parser
 ↓
Class parser
 ↓
ServiceId parser
 ↓
Mapper parser
 ↓
PASS / FAIL
```

---

# 18. Development Dependency Standard

## FIG-15-19. Development Dependency Standard

```text
Handler / Controller
 ↓
Facade
 ↓
Service
 ↓
Rule [optional]
 ↓
DAO
 ↓
Mapper

Bypass = FAIL
```

---

# 19. Naming Change Lifecycle

## FIG-15-20. Naming Change Lifecycle

```text
Old ID
 ↓ deprecated
New ID
 ↓ mapping / migration
Cutover
 ↓
Retire
```

---

# 20. Open Enterprise Naming Areas

## FIG-15-21. Open Enterprise Naming Areas

```text
InterfaceId exact format
Event/topic naming
File naming
Batch JobId
Artifact/DeploymentId syntax
Hostname/JVM final convention
Metric semantic fields
= [OPEN]
```

---

# 21. Architecture Rule Catalog

## FIG-15-22. Rule Set

```text
R-NAM-01
Canonical identifier는 하나의 의미만 가진다.

R-NAM-02
ServiceId는 exact key로 사용하고 자동 normalization하지 않는다.

R-NAM-03
Program/Package/Mapper 업무축을 정렬한다.

R-NAM-04
Duplicate ServiceId를 금지한다.

R-NAM-05
Handler registry와 handle branch를 정합한다.

R-NAM-06
DAO와 Mapper namespace를 정합한다.

R-NAM-07
ServiceId≠InterfaceId≠GUID를 유지한다.

R-NAM-08
Display name 변경과 canonical ID 변경을 분리한다.

R-NAM-09
미확정 enterprise naming을 임의 생성하지 않는다.

R-NAM-10
Naming rule은 CI에서 기계검증 가능해야 한다.
```

| Rule | 정의 |
|---|---|
| R-NAM-01 | Canonical identifier는 하나의 의미만 가진다. |
| R-NAM-02 | ServiceId는 exact key로 사용하고 자동 normalization하지 않는다. |
| R-NAM-03 | Program/Package/Mapper 업무축을 정렬한다. |
| R-NAM-04 | Duplicate ServiceId를 금지한다. |
| R-NAM-05 | Handler registry와 handle branch를 정합한다. |
| R-NAM-06 | DAO와 Mapper namespace를 정합한다. |
| R-NAM-07 | ServiceId≠InterfaceId≠GUID를 유지한다. |
| R-NAM-08 | Display name 변경과 canonical ID 변경을 분리한다. |
| R-NAM-09 | 미확정 enterprise naming을 임의 생성하지 않는다. |
| R-NAM-10 | Naming rule은 CI에서 기계검증 가능해야 한다. |

---

# 22. Verification / Test

## FIG-15-23. Verification Flow

```text
Architecture Model
   ↓
Static / Config Check
   ↓
Integration Test
   ↓
Failure / Security / Performance Test
   ↓
Runtime Evidence
   ↓
PASS / GAP
```

| Test ID | 검증내용 |
|---|---|
| T-NAM-01 | Program regex/approved classification |
| T-NAM-02 | ServiceId unique |
| T-NAM-03 | registry/branch consistency |
| T-NAM-04 | package/class suffix |
| T-NAM-05 | mapper path/namespace/sqlid |
| T-NAM-06 | UI/backend catalog diff |
| T-NAM-07 | artifact/deployment identity |

---

# 23. GAP Register

## FIG-15-24. GAP Lifecycle

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

| GAP ID | GAP | 중요도 | 전환조건 |
|---|---|---|---|
| GAP-NAM-01 | UI catalog vs backend 13 ServiceIds | High | automated diff |
| GAP-NAM-02 | InterfaceId exact enterprise syntax | Medium | ADR/registry |
| GAP-NAM-03 | Event/File/Batch naming | Medium | platform standard |
| GAP-NAM-04 | Artifact/DeploymentId exact syntax | High | DevOps standard |
| GAP-NAM-05 | Hostname/JVM convention | Medium | Infra standard |
| GAP-NAM-06 | Naming scanner CI enforcement | High | pipeline gate |

---

# 24. Risk Register

## FIG-15-25. Risk Propagation

```text
Cause
  ↓
Technical Failure
  ↓
Service Impact
  ↓
Operational / Business Impact
```

| Risk ID | Risk | 영향 |
|---|---|---|
| RISK-NAM-01 | duplicate ServiceId | startup/runtime failure |
| RISK-NAM-02 | package/business drift | traceability loss |
| RISK-NAM-03 | identifier alias normalization | wrong routing |
| RISK-NAM-04 | registry divergence | UI/backend mismatch |
| RISK-NAM-05 | unstable deployment naming | evidence chain break |

---

# 25. Architecture Decision / ADR

## FIG-15-26. Decision Flow

```text
Decision Question
   ↓
주안 / 대안
   ↓
장점 / 단점
   ↓
Evidence / Test
   ↓
ADR
   ↓
Baseline
```

| ADR/Task | 의사결정 주제 | 현재 방향 |
|---|---|---|
| ADR-TASK-002 | Code Registry SSOT | central registry + CI |
| ADR-TASK-003 | PDMG→NSIGHT Mapping | explicit mapping |
| ADR-TASK-040 | Runtime evidence IDs | baseline/model/artifact/deployment chain |

---

# 26. Architecture PASS / PDMG Conformance

## FIG-15-27. PASS Model

```text
Architecture Definition
 ↓
PASS

Current Naming Conformance
 ↓
PASS / PARTIAL

Strong: Program/ServiceId/package/mapper
Open: enterprise interface/artifact/host naming and CI enforcement
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Program/ServiceId | PASS | strong source |
| Package/Mapper | PASS/PARTIAL | strong pattern |
| Registry | PARTIAL | UI drift |
| Interface/Event naming | OPEN | enterprise standard |
| Artifact/Deployment naming | OPEN | DevOps standard |
| CI enforcement | GAP | scanner/gate needed |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PASS / PARTIAL`  
**Runtime Evidence Coverage:** `HIGH-MEDIUM`

---

# 27. Next Chapter Handoff

## FIG-15-28. 15 → 16

```text
15 NAMING / DEVELOPMENT STANDARD
"어떤 식별과 개발규칙을 따르는가?"
     ↓
16 CAPACITY / PERFORMANCE / HA / DR
"그 구조가 목표 부하와 장애/센터 재해를 견딜 수 있는가?" 
```

---

# 28. PDMG NAMING / APPLICATION CODE / DEVELOPMENT STANDARD 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG NAMING / APPLICATION CODE / DEVELOPMENT STANDARD
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**15장 Architecture Definition 판정: `PASS`**


---



<!-- ============================================================ -->
<!-- CHAPTER 16: CAPACITY / PERFORMANCE / HA / DR -->
<!-- SOURCE: 16_PDMG_CAPACITY_PERFORMANCE_HA_DR_상세정의서_v1.md -->
<!-- ============================================================ -->

# PDMG 전체 아키텍처 정의서
# 16. PDMG CAPACITY / PERFORMANCE / HA / DR ARCHITECTURE
## Workload / Thread-Pool / JVM / Scale-out / Session / N+1 / DR / Evidence
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-16-CAPACITY-PERFORMANCE-HA-DR`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-16-01. 이 장의 핵심 질문

```text
PDMG가 목표 사용자/동시성/TPS/p95를 처리하려면 어떤 Capacity Chain이 필요한가?
어떤 값이 AS-IS이고 어떤 값이 Candidate인가?
노드/센터 장애와 Session/DB/Key/Artifact를 어떻게 복구할 것인가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-16-02. Evidence Flow

```text
Source / Config
   ↓
Runtime / Deployment
   ↓
PDMG Current Analysis
   ↓
Architecture Decision
   ↓
NSIGHT Target / Working Baseline
   ↓
PASS / GAP / OPEN
```

| Evidence ID | 근거 | 사용목적 | 상태 |
|---|---|---|---|
| EV-16-01 | VIII Capacity/HA/DR | user/session/thread/pool variants | [CANDIDATE/CONFLICT] |
| EV-16-02 | V Transaction/Timeout | worker20/queue100/5000 | [AS-IS] |
| EV-16-03 | HW/SW Matrix | VM candidates | [CANDIDATE] |
| EV-16-04 | Physical chapter | HA/DR mapping | [WORKING BASELINE] |
| EV-16-05 | Decision Register | sizing/HA/DR/pools | [DECISION] |

---

# 2. Figure Plan

## FIG-16-03. Top-down Drill-down

```text
L0  Overall
 ↓
L1  Boundary / Responsibility
 ↓
L2  Node / Platform / Component
 ↓
L3  Runtime / Control / Data
 ↓
L4  Sequence / Failure / Recovery
 ↓
L5  Source / Config / Evidence
```

---

# 3. L0 — Capacity / Resilience Master

## FIG-16-04. L0 — Capacity / Resilience Master

```text
Users / Workload
 ↓
Concurrency
 ↓
TPS / Response Time
 ↓
Tomcat Threads
 ↓
PDMG Workers
 ↓
Hikari Connections
 ↓
DB Sessions
 ↓
CPU / Memory / IO
 ↓
Server Count / N+1
 ↓
HA / DR
```

---

# 4. Business Load Assumption

## FIG-16-05. Business Load Assumption

```text
6,000 branches
 × 6 users
 = 36,000 users

Concurrency assumption
= 10% candidate

p95 target
= 3s working target
```

---

# 5. WAS Candidate Options

## FIG-16-06. WAS Candidate Options

```text
Option A
32C / 256G × 4

Option B
16C / 128G × 8

Option C
16C / 128G ×4 ×2 groups

[CANDIDATE]
```

---

# 6. Current Worker vs Target Capacity

## FIG-16-07. Current Worker vs Target Capacity

```text
PDMG AS-IS
Worker 20
Queue 100
Deadline 5000ms

Target Capacity
≠ same numbers
 ↓
Load Test required
```

---

# 7. Tomcat Thread Candidate

## FIG-16-08. Tomcat Thread Candidate

```text
32C/256G variant
maxThreads
1200 ~ 1500
[CANDIDATE]

Busy target
≤70%
[CANDIDATE]
```

---

# 8. Hikari Candidate

## FIG-16-09. Hikari Candidate

```text
General
120 ~ 160

SV
100 ~ 120

connectionTimeout
3s candidate

maxLifetime
≤30m candidate

[VARIANT / CANDIDATE]
```

---

# 9. JVM Heap Candidate

## FIG-16-10. JVM Heap Candidate

```text
Heap
32 ~ 48 GB
G1GC
[CANDIDATE]

Heap
≠ Total VM Memory
```

---

# 10. Capacity Chain

## FIG-16-11. Capacity Chain

```text
Tomcat Busy
 ↓
Worker Active / Queue
 ↓
Hikari Active / Pending
 ↓
DB Session / SQL Wait
 ↓
CPU / IO / Lock
```

---

# 11. Saturation Cascade

## FIG-16-12. Saturation Cascade

```text
DB Slow
 ↓
Hikari Pending
 ↓
Worker Occupied
 ↓
Queue
 ↓
Request Wait
 ↓
Timeout / 504
```

---

# 12. Scale-up vs Scale-out

## FIG-16-13. Scale-up vs Scale-out

```text
Scale-up
few large VMs
 + simple
 - large failure domain

Scale-out
more medium VMs
 + isolation/N+1
 - more operations
```

---

# 13. JVM / WAR Isolation

## FIG-16-14. JVM / WAR Isolation

```text
Business Group A
 → JVM A

Business Group B
 → JVM B

Shared VM possible
but JVM failure domains separated
```

---

# 14. Session Conflict

## FIG-16-15. Session Conflict

```text
Baseline Variant A
Session 60m

Baseline Variant B
Session 90m

[CONFLICT]
Final policy required
```

---

# 15. Session HA Options

## FIG-16-16. Session HA Options

```text
Tomcat Session
 ├─ DeltaManager
 └─ Spring Session JDBC

JWT-centric
 └─ minimize HttpSession

Current final choice
= [OPEN]
```

---

# 16. Local HA

## FIG-16-17. Local HA

```text
GSLB/L4
 ↓
WEB pair/N+1
 ↓
WAS Active-Active
 ↓
DB Local HA
 ↓
node failure test
```

---

# 17. DR

## FIG-16-18. DR

```text
Main
 ↓ replication/sync
DR
 ↓
traffic switch
 ↓
application/config/key
 ↓
data
 ↓
interface/batch
 ↓
business validation
```

---

# 18. RTO / RPO

## FIG-16-19. RTO / RPO

```text
Business Criticality
 ↓
RTO / RPO
 ↓
DR Tier
 ↓
Infrastructure / Data / Key / App
 ↓
Drill Evidence

Exact values
= [OPEN]
```

---

# 19. Performance Test

## FIG-16-20. Performance Test

```text
Baseline
 ↓
Load
 ↓
Stress
 ↓
Soak
 ↓
Node failure under load
 ↓
DB/network fault
 ↓
DR / restore
 ↓
Evidence
```

---

# 20. Capacity Evidence

## FIG-16-21. Capacity Evidence

```text
assumption
 ↓
calculation
 ↓
candidate
 ↓
load test
 ↓
measured
 ↓
approved
 ↓
runtime monitoring
```

---

# 21. Architecture Rule Catalog

## FIG-16-22. Rule Set

```text
R-CAP-01
Assumption→Calculation→Candidate→Test→Measured→Approved 단계를 구분한다.

R-CAP-02
PDMG current worker values를 target capacity로 사용하지 않는다.

R-CAP-03
Tomcat/Worker/Hikari/DB pool을 end-to-end로 산정한다.

R-CAP-04
N+1 잔존용량을 검증한다.

R-CAP-05
Scale-out unit과 failure domain을 함께 결정한다.

R-CAP-06
Session 정책과 HA 전략을 일치시킨다.

R-CAP-07
Local HA와 DR을 구분한다.

R-CAP-08
RTO/RPO는 business criticality로 결정한다.

R-CAP-09
DR에는 app/config/key/interface/monitoring까지 포함한다.

R-CAP-10
Performance 결과를 runtime monitoring baseline으로 전환한다.
```

| Rule | 정의 |
|---|---|
| R-CAP-01 | Assumption→Calculation→Candidate→Test→Measured→Approved 단계를 구분한다. |
| R-CAP-02 | PDMG current worker values를 target capacity로 사용하지 않는다. |
| R-CAP-03 | Tomcat/Worker/Hikari/DB pool을 end-to-end로 산정한다. |
| R-CAP-04 | N+1 잔존용량을 검증한다. |
| R-CAP-05 | Scale-out unit과 failure domain을 함께 결정한다. |
| R-CAP-06 | Session 정책과 HA 전략을 일치시킨다. |
| R-CAP-07 | Local HA와 DR을 구분한다. |
| R-CAP-08 | RTO/RPO는 business criticality로 결정한다. |
| R-CAP-09 | DR에는 app/config/key/interface/monitoring까지 포함한다. |
| R-CAP-10 | Performance 결과를 runtime monitoring baseline으로 전환한다. |

---

# 22. Verification / Test

## FIG-16-23. Verification Flow

```text
Architecture Model
   ↓
Static / Config Check
   ↓
Integration Test
   ↓
Failure / Security / Performance Test
   ↓
Runtime Evidence
   ↓
PASS / GAP
```

| Test ID | 검증내용 |
|---|---|
| T-CAP-01 | load/p95/TPS |
| T-CAP-02 | stress saturation point |
| T-CAP-03 | soak/GC/leak |
| T-CAP-04 | node failure under load |
| T-CAP-05 | DB failover |
| T-CAP-06 | session failover |
| T-CAP-07 | DR switch/failback |
| T-CAP-08 | restore/business validation |

---

# 23. GAP Register

## FIG-16-24. GAP Lifecycle

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

| GAP ID | GAP | 중요도 | 전환조건 |
|---|---|---|---|
| GAP-CAP-01 | Session 60 vs 90 conflict | High | policy ADR |
| GAP-CAP-02 | Final VM/server count | High | load/cost test |
| GAP-CAP-03 | Tomcat maxThreads approval | High | load/soak |
| GAP-CAP-04 | Hikari target size | High | DB session/capacity test |
| GAP-CAP-05 | JVM heap approval | High | GC/soak |
| GAP-CAP-06 | RTO/RPO | Critical | business approval |
| GAP-CAP-07 | DR drill evidence | Critical | drill |
| GAP-CAP-08 | Session HA final pattern | High | failover test |

---

# 24. Risk Register

## FIG-16-25. Risk Propagation

```text
Cause
  ↓
Technical Failure
  ↓
Service Impact
  ↓
Operational / Business Impact
```

| Risk ID | Risk | 영향 |
|---|---|---|
| RISK-CAP-01 | oversized thread count | queue/DB overload |
| RISK-CAP-02 | undersized worker pool | throughput bottleneck |
| RISK-CAP-03 | session replication overhead | memory/network impact |
| RISK-CAP-04 | large failure domain | node loss overload |
| RISK-CAP-05 | untested DR | recovery failure |

---

# 25. Architecture Decision / ADR

## FIG-16-26. Decision Flow

```text
Decision Question
   ↓
주안 / 대안
   ↓
장점 / 단점
   ↓
Evidence / Test
   ↓
ADR
   ↓
Baseline
```

| ADR/Task | 의사결정 주제 | 현재 방향 |
|---|---|---|
| ADR-TASK-026 | WAS sizing | medium VM scale-out candidate |
| ADR-TASK-028 | JVM/WAR isolation | business groups |
| ADR-TASK-029 | Thread/Pool | end-to-end budget |
| ADR-TASK-030 | HA | active-active/N+1 |
| ADR-TASK-031 | DR | criticality tier |
| ADR-TASK-032 | DB HA/DR | local+center |
| ADR-TASK-012 | Session strategy | JWT/HttpSession decision |

---

# 26. Architecture PASS / PDMG Conformance

## FIG-16-27. PASS Model

```text
Architecture Definition
 ↓
PASS

Current Capacity/Resilience Conformance
 ↓
CONDITIONAL / OPEN

Many values are candidates or conflicting baselines,
therefore runtime performance evidence is mandatory
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Workload model | CONDITIONAL | 36k/10% assumptions |
| VM sizing | CANDIDATE | load/cost test |
| Worker snapshot | PASS AS-IS | 20/100/5000 |
| Tomcat/Hikari/JVM | CANDIDATE | performance approval |
| HA | CONDITIONAL | N+1 failure test |
| DR | OPEN/CONDITIONAL | RTO/RPO/drill |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `CONDITIONAL / OPEN`  
**Runtime Evidence Coverage:** `MEDIUM`

---

# 27. Next Chapter Handoff

## FIG-16-28. 16 → 17

```text
16 CAPACITY / HA / DR
"얼마나 처리하고 장애를 어떻게 견디는가?"
     ↓
17 TRACEABILITY / PASS / GAP / ADR
"설계와 실제 구현이 일치함을 어떻게 기계적으로 증명하고 통제하는가?" 
```

---

# 28. PDMG CAPACITY / PERFORMANCE / HA / DR ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG CAPACITY / PERFORMANCE / HA / DR ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**16장 Architecture Definition 판정: `PASS`**


---



<!-- ============================================================ -->
<!-- CHAPTER 17: TRACEABILITY / CONFORMANCE / PASS / GAP / ADR -->
<!-- SOURCE: 17_PDMG_TRACEABILITY_PASS_GAP_ADR_상세정의서_v1.md -->
<!-- ============================================================ -->

# PDMG 전체 아키텍처 정의서
# 17. PDMG TRACEABILITY / CONFORMANCE / PASS / GAP / ADR ARCHITECTURE
## ServiceId / Model / Rule / Test / Runtime Evidence / Drift / Gate / HG90
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-17-TRACEABILITY-PASS-GAP-ADR`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-17-01. 이 장의 핵심 질문

```text
PDMG Architecture가 Source/Config/Test/Deployment/Runtime과 일치함을 어떻게 증명하는가?
Architecture PASS와 Current Implementation PASS를 어떻게 분리하는가?
Drift/GAP/ADR를 통해 Baseline을 어떻게 갱신하는가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-17-02. Evidence Flow

```text
Source / Config
   ↓
Runtime / Deployment
   ↓
PDMG Current Analysis
   ↓
Architecture Decision
   ↓
NSIGHT Target / Working Baseline
   ↓
PASS / GAP / OPEN
```

| Evidence ID | 근거 | 사용목적 | 상태 |
|---|---|---|---|
| EV-17-01 | X Naming/Traceability | closed loop/serviceId | [WORKING BASELINE] |
| EV-17-02 | Decision PASS Register | 40 task status | [WORKING DECISION REGISTER] |
| EV-17-03 | 00 Master Index | gate/evidence principles | [DECISION] |
| EV-17-04 | Chapters 01-16 | domain PASS/GAP | [WORKING BASELINE] |
| EV-17-05 | DevOps/Observability | runtime evidence | [WORKING BASELINE] |

---

# 2. Figure Plan

## FIG-17-03. Top-down Drill-down

```text
L0  Overall
 ↓
L1  Boundary / Responsibility
 ↓
L2  Node / Platform / Component
 ↓
L3  Runtime / Control / Data
 ↓
L4  Sequence / Failure / Recovery
 ↓
L5  Source / Config / Evidence
```

---

# 3. L0 — Architecture Closed Loop

## FIG-17-04. L0 — Architecture Closed Loop

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
GAP
 ↓
ADR
 ↓
New Baseline
```

---

# 4. Workspace / Gate Model

## FIG-17-05. Workspace / Gate Model

```text
00-IN
10-DOCUMENT
20-MODEL
30-CODE / CONFORMANCE
40-TEST
50-RUNTIME-EVIDENCE
60-DRIFT
70-GAP-ADR
80-GATE
90-OUT / HG90
```

---

# 5. Gate G00 → HG90

## FIG-17-06. Gate G00 → HG90

```text
G00 Source
 ↓
G10 Document
 ↓
G20 Model
 ↓
G30 Conformance
 ↓
G40 Rule Test
 ↓
G50 Runtime Evidence
 ↓
G60 Drift
 ↓
G70 GAP/ADR
 ↓
G80 Approval
 ↓
HG90 Baseline Release
```

---

# 6. Master Evidence Chain

## FIG-17-07. Master Evidence Chain

```text
architectureBaselineId
 ↓
architectureModelVersion
 ↓
sourceCommit
 ↓
buildId
 ↓
artifactHash
 ↓
deploymentId
 ↓
serviceId
 ↓
GUID / traceId
 ↓
runtimeEvidence
```

---

# 7. Entity Model

## FIG-17-08. Entity Model

```text
Requirement
System
Business
Function
Program
ServiceId
Component
Mapper
SqlId
Table
RuntimePolicy
Artifact
Deployment
Evidence
```

---

# 8. ServiceId Trace

## FIG-17-09. ServiceId Trace

```text
Business
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
SqlId
 ↓
Table
```

---

# 9. Reverse Impact Trace

## FIG-17-10. Reverse Impact Trace

```text
Table / SQL
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
 ↑
Requirement
```

---

# 10. Source Conformance

## FIG-17-11. Source Conformance

```text
Architecture Rule
 ↓
Source Scanner
 ↓
PASS / FAIL
 ↓
CI Gate
```

---

# 11. Config Conformance

## FIG-17-12. Config Conformance

```text
Baseline Config
 ↓ compare
application.yml
server.xml
httpd.conf
JVM option
Datasource
 ↓
Drift
```

---

# 12. Runtime Evidence

## FIG-17-13. Runtime Evidence

```text
Rule
 ↓
Runtime Metric / Trace / Failure Test
 ↓
Evidence
 ↓
Gate Result
```

---

# 13. Architecture PASS vs Implementation PASS

## FIG-17-14. Architecture PASS vs Implementation PASS

```text
Architecture Definition
PASS / CONDITIONAL / OPEN / FAIL

≠

Implementation
PASS / PARTIAL / GAP / CONFLICT / OPEN / UNKNOWN
```

---

# 14. Decision Register

## FIG-17-15. Decision Register

```text
Decision Task
 ↓
주안 / 대안
 ↓
Evidence
 ↓
ADR
 ↓
Rule / Baseline
```

---

# 15. Current Decision Snapshot

## FIG-17-16. Current Decision Snapshot

```text
40 Decision Tasks
├─ PASS 23
├─ CONDITIONAL PASS 16
├─ OPEN 1
└─ FAIL 0

[WORKING REGISTER]
```

---

# 16. Critical Drift Examples

## FIG-17-17. Critical Drift Examples

```text
JWT
RS256 issue
≠ HMAC verify

TCF
ON Facade
≠ OFF Service direct

Catalog
UI
≠ Backend registry

Deployment
Architecture node
≠ actual host mapping
```

---

# 17. Baseline Release Criteria

## FIG-17-18. Baseline Release Criteria

```text
Critical Rule PASS
+ Critical GAP closed/ADR
+ Runtime Evidence
+ Deployment Trace
+ Security Test
+ Performance/DR Evidence
 ↓
HG90
```

---

# 18. Architecture Dashboard

## FIG-17-19. Architecture Dashboard

```text
Domain
 ↓
Rule PASS%
 ↓
Current Conformance
 ↓
Critical GAP
 ↓
Evidence Coverage
 ↓
Owner / Gate
```

---

# 19. Architecture Rule Catalog

## FIG-17-20. Rule Set

```text
R-TR-01
모든 Critical Architecture Object는 machine-readable ID를 가진다.

R-TR-02
ServiceId를 Source/Data/Runtime 공통 추적축으로 사용한다.

R-TR-03
Architecture PASS와 Implementation PASS를 분리한다.

R-TR-04
Logging만으로 Runtime Evidence PASS를 선언하지 않는다.

R-TR-05
SourceCommit→ArtifactHash→DeploymentId를 유지한다.

R-TR-06
Critical Rule은 CI/Runtime Gate에서 검증한다.

R-TR-07
Drift는 GAP 또는 ADR로 닫는다.

R-TR-08
UNKNOWN/OPEN을 숨기지 않는다.

R-TR-09
승인된 ADR은 Model/Rule/Standard에 반영한다.

R-TR-10
HG90는 Evidence-backed Baseline Release다.
```

| Rule | 정의 |
|---|---|
| R-TR-01 | 모든 Critical Architecture Object는 machine-readable ID를 가진다. |
| R-TR-02 | ServiceId를 Source/Data/Runtime 공통 추적축으로 사용한다. |
| R-TR-03 | Architecture PASS와 Implementation PASS를 분리한다. |
| R-TR-04 | Logging만으로 Runtime Evidence PASS를 선언하지 않는다. |
| R-TR-05 | SourceCommit→ArtifactHash→DeploymentId를 유지한다. |
| R-TR-06 | Critical Rule은 CI/Runtime Gate에서 검증한다. |
| R-TR-07 | Drift는 GAP 또는 ADR로 닫는다. |
| R-TR-08 | UNKNOWN/OPEN을 숨기지 않는다. |
| R-TR-09 | 승인된 ADR은 Model/Rule/Standard에 반영한다. |
| R-TR-10 | HG90는 Evidence-backed Baseline Release다. |

---

# 20. Verification / Test

## FIG-17-21. Verification Flow

```text
Architecture Model
   ↓
Static / Config Check
   ↓
Integration Test
   ↓
Failure / Security / Performance Test
   ↓
Runtime Evidence
   ↓
PASS / GAP
```

| Test ID | 검증내용 |
|---|---|
| T-TR-01 | serviceId source trace |
| T-TR-02 | reverse table impact trace |
| T-TR-03 | rule scanner |
| T-TR-04 | config drift |
| T-TR-05 | artifact/deployment trace |
| T-TR-06 | runtime evidence link |
| T-TR-07 | decision/ADR closure |
| T-TR-08 | HG90 release checklist |

---

# 21. GAP Register

## FIG-17-22. GAP Lifecycle

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

| GAP ID | GAP | 중요도 | 전환조건 |
|---|---|---|---|
| GAP-TR-01 | Architecture model automation completeness | High | entity/relation registry |
| GAP-TR-02 | Source scanner CI enforcement | High | pipeline |
| GAP-TR-03 | Deployment runtime correlation | Critical | deployment manifest |
| GAP-TR-04 | Runtime evidence collector | Critical | metrics/trace/test link |
| GAP-TR-05 | UI/backend registry drift automation | High | catalog diff |
| GAP-TR-06 | Critical decision closure | Critical | ADR/gate |

---

# 22. Risk Register

## FIG-17-23. Risk Propagation

```text
Cause
  ↓
Technical Failure
  ↓
Service Impact
  ↓
Operational / Business Impact
```

| Risk ID | Risk | 영향 |
|---|---|---|
| RISK-TR-01 | document-only baseline | actual drift hidden |
| RISK-TR-02 | logging called evidence | false confidence |
| RISK-TR-03 | manual registry | staleness |
| RISK-TR-04 | unclosed ADR | ambiguous implementation |
| RISK-TR-05 | missing reverse trace | impact analysis failure |

---

# 23. Architecture Decision / ADR

## FIG-17-24. Decision Flow

```text
Decision Question
   ↓
주안 / 대안
   ↓
장점 / 단점
   ↓
Evidence / Test
   ↓
ADR
   ↓
Baseline
```

| ADR/Task | 의사결정 주제 | 현재 방향 |
|---|---|---|
| ADR-TASK-001 | Architecture SSOT | model+registry+gate |
| ADR-TASK-002 | Code Registry | central+CI |
| ADR-TASK-003 | PDMG→NSIGHT mapping | explicit |
| ADR-TASK-040 | Runtime Evidence Gate | critical rules first |

---

# 24. Architecture PASS / PDMG Conformance

## FIG-17-25. PASS Model

```text
Architecture Definition
 ↓
PASS

Current Closed-loop Conformance
 ↓
PARTIAL / CONDITIONAL

Model/rules defined;
runtime evidence/deployment automation remains the major gap
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| ServiceId trace | PASS/PARTIAL | strong source |
| Decision register | PASS | 40 tasks working register |
| Source conformance | CONDITIONAL | automation needed |
| Deployment trace | GAP | manifest/host link |
| Runtime evidence | GAP | collector/gate |
| HG90 | CONDITIONAL | critical evidence required |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PARTIAL / CONDITIONAL`  
**Runtime Evidence Coverage:** `MEDIUM`

---

# 25. Next Chapter Handoff

## FIG-17-26. 17 → 18

```text
17 TRACEABILITY / PASS / GAP / ADR
"설계대로 구현됐음을 어떻게 증명하는가?"
     ↓
18 INTEGRATED BASELINE
"앞의 모든 Domain을 하나의 PDMG Architecture Baseline으로 어떻게 묶고 승인하는가?" 
```

---

# 26. PDMG TRACEABILITY / CONFORMANCE / PASS / GAP / ADR ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG TRACEABILITY / CONFORMANCE / PASS / GAP / ADR ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**17장 Architecture Definition 판정: `PASS`**


---



<!-- ============================================================ -->
<!-- CHAPTER 18: INTEGRATED ARCHITECTURE BASELINE -->
<!-- SOURCE: 18_PDMG_INTEGRATED_ARCHITECTURE_BASELINE_상세정의서_v1.md -->
<!-- ============================================================ -->

# PDMG 전체 아키텍처 정의서
# 18. PDMG INTEGRATED ARCHITECTURE BASELINE
## Application / Technical / Physical / Interface / Data / Runtime / Security / Operations / Traceability
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-18-INTEGRATED-ARCHITECTURE-BASELINE`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-18-01. 이 장의 핵심 질문

```text
앞의 모든 Architecture를 하나의 PDMG Baseline으로 어떻게 통합하는가?
현재 강하게 확인된 AS-IS와 Target Alignment, Critical GAP를 한 눈에 어떻게 보여주는가?
어떤 조건을 충족해야 HG90 Final PASS가 되는가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-18-02. Evidence Flow

```text
Source / Config
   ↓
Runtime / Deployment
   ↓
PDMG Current Analysis
   ↓
Architecture Decision
   ↓
NSIGHT Target / Working Baseline
   ↓
PASS / GAP / OPEN
```

| Evidence ID | 근거 | 사용목적 | 상태 |
|---|---|---|---|
| EV-18-01 | Chapters 01-17 | domain architecture | [WORKING BASELINE] |
| EV-18-02 | 00 Master Index | writing/evidence policy | [DECISION] |
| EV-18-03 | Decision PASS Register | architecture decision status | [WORKING REGISTER] |
| EV-18-04 | NSIGHT Target references | alignment | [TARGET REFERENCE] |
| EV-18-05 | PDMG source/runtime analyses | current facts | [AS-IS] |

---

# 2. Figure Plan

## FIG-18-03. Top-down Drill-down

```text
L0  Overall
 ↓
L1  Boundary / Responsibility
 ↓
L2  Node / Platform / Component
 ↓
L3  Runtime / Control / Data
 ↓
L4  Sequence / Failure / Recovery
 ↓
L5  Source / Config / Evidence
```

---

# 3. L0 — PDMG Master Architecture Baseline

## FIG-18-04. L0 — PDMG Master Architecture Baseline

```text
User / Browser
  ↓
UI Delivery
  ↓
Authentication / JWT
  ↓
Application Runtime
  ├─ Framework / TCF
  ├─ Worker / Transaction
  └─ Business
       ↓
   DAO / Mapper
       ↓
   RDW / DB

External Integration [contract-based]
Operations / Observability [cross-cutting]
Physical / HA / DR [mapping]
Traceability / Evidence [closed loop]
```

---

# 4. L0~L5 Full Drill-down

## FIG-18-05. L0~L5 Full Drill-down

```text
L0 PDMG Landscape
 ↓
L1 System / Trust / Data Boundary
 ↓
L2 Module / Logical Node
 ↓
L3 Layer / Component
 ↓
L4 Runtime / Failure / Security
 ↓
L5 Source / Config / Evidence
```

---

# 5. Application Overlay

## FIG-18-06. Application Overlay

```text
pdmg-ui
pdmg-jwt
pdmg-service + pdmg-fw
pdmg-om [UNKNOWN]
 ↓
Handler / Controller
 ↓
Facade / Service
 ↓
DAO / Mapper
```

---

# 6. Technical / Physical Overlay

## FIG-18-07. Technical / Physical Overlay

```text
Logical Nodes
UI / Auth / App / Data / Integration / Ops
 ↓
GSLB / L4 / WEB / WAS
 ↓
JVM / WAR
 ↓
DB / Storage
 ↓
Monitoring / Backup / DR
```

---

# 7. Runtime Overlay

## FIG-18-08. Runtime Overlay

```text
Filter
 ↓
Security
 ↓
MVC
 ↓
TCF
 ↓
Worker
 ↓
TX
 ↓
Handler / Facade / Service
 ↓
DB
 ↓
Response / Evidence
```

---

# 8. Security Overlay

## FIG-18-09. Security Overlay

```text
Login / SSO
 ↓
RS256 issue
 ↓
JWKS
 ↓
Business verify [Current GAP]
 ↓
Principal
 ↓
Authorization
 ↓
Audit
```

---

# 9. Data / Interface Overlay

## FIG-18-10. Data / Interface Overlay

```text
ServiceId
 ↓
Business
 ↓
DAO / Mapper / SQL
 ↓
RDW

External Need
 ↓
InterfaceId
 ↓
API / Event / CDC / ETL / File
```

---

# 10. Operations Overlay

## FIG-18-11. Operations Overlay

```text
Source
 ↓
Build / Artifact
 ↓
Deployment
 ↓
Runtime
 ↓
Metric / Log / Trace
 ↓
Alert / Runbook
 ↓
Evidence / Drift
```

---

# 11. Naming / Trace Overlay

## FIG-18-12. Naming / Trace Overlay

```text
Business
 ↓
Program
 ↓
ServiceId
 ↓
Component
 ↓
SqlId / Table
 ↓
Artifact
 ↓
Deployment
 ↓
GUID / Evidence
```

---

# 12. Current AS-IS Strong Facts

## FIG-18-13. Current AS-IS Strong Facts

```text
Confirmed
├─ pdmg-ui/jwt/fw/service
├─ nhnis.mg.co.a
├─ rdw.mg.co.a
├─ 13 ServiceIds
├─ Filter/Security/MVC/TCF
├─ Worker20/Queue100/5000ms
├─ TransactionTemplate
├─ Hikari/MyBatis/JDBC
├─ hdr_nhnis/GUID
└─ RS256 issue + HMAC verify conflict
```

---

# 13. Target Alignment

## FIG-18-14. Target Alignment

```text
NSIGHT Target
├─ Standard Interface
├─ RDW/ADW separation
├─ Event/CDC/ETL/File
├─ WEB/WAS scale-out
├─ HA/DR
├─ Security key/JWKS
├─ Observability
└─ Evidence closed loop
       ↓ compare
PDMG Current
       ↓
PASS / GAP / ADR
```

---

# 14. Critical GAP Heatmap

## FIG-18-15. Critical GAP Heatmap

```text
CRITICAL
├─ JWT issuer/verifier/key
├─ Identity binding
├─ Deployment→Host/JVM/WAR
└─ Runtime evidence automation

HIGH
├─ TCF OFF facade drift
├─ Worker mutable context
├─ Query timeout/cancel evidence
├─ Interface inventory
├─ RDW/ADW actual mapping
├─ pdmg-om scope
└─ Capacity/DR approval
```

---

# 15. Chapter PASS Map

## FIG-18-16. Chapter PASS Map

```text
01 Executive        CONDITIONAL PASS
02 Boundary         PASS
03 Application      PASS
04 Logical          PASS
05 Physical         PASS / Current conditional
06 Interface        PASS / Current partial
07 Data             PASS / Current partial
08 Framework        PASS / Current gap
09 Runtime          PASS / Current conditional
10 TX/Timeout       PASS / Current conditional
11 Security         PASS / Current critical gap
12 Message/Error    PASS / Current gap
13 Non-online       CONDITIONAL PASS
14 DevOps/Ops       PASS / Current open
15 Naming           PASS
16 Capacity/HA/DR   PASS / Current open
17 Traceability     PASS / Current conditional
```

---

# 16. Baseline Release Gate

## FIG-18-17. Baseline Release Gate

```text
Architecture Definition
 ↓
Critical ADR Closed
 ↓
Source / Config Conformance
 ↓
Security Integration PASS
 ↓
Performance / Failure PASS
 ↓
Deployment Trace PASS
 ↓
Runtime Evidence PASS
 ↓
DR / Restore PASS
 ↓
G80 Approval
 ↓
HG90 PDMG Baseline
```

---

# 17. Future Change Closed Loop

## FIG-18-18. Future Change Closed Loop

```text
Requirement Change
 ↓
Architecture Impact
 ↓
Model / ADR
 ↓
Source / Test
 ↓
Deploy
 ↓
Runtime Evidence
 ↓
Drift?
 ├─ NO → maintain baseline
 └─ YES → GAP / ADR / new baseline
```

---

# 18. Architecture Rule Catalog

## FIG-18-19. Rule Set

```text
R-BL-01
PDMG Current와 NSIGHT Target을 분리한다.

R-BL-02
모든 주요 Architecture Domain은 TEXT diagram과 evidence를 가진다.

R-BL-03
Critical GAP는 ADR 또는 구현/Evidence로 닫는다.

R-BL-04
Architecture PASS와 Implementation PASS를 분리한다.

R-BL-05
Unknown/Open 값을 숨기거나 추정하지 않는다.

R-BL-06
ServiceId를 Source/Data/Runtime trace의 핵심축으로 사용한다.

R-BL-07
Artifact/Deployment/Runtime evidence를 Baseline에 연결한다.

R-BL-08
Security/Performance/DR는 runtime test evidence가 없으면 최종 PASS가 아니다.

R-BL-09
Baseline 변경은 ADR/Version으로 관리한다.

R-BL-10
HG90만 공식 Release Baseline으로 본다.
```

| Rule | 정의 |
|---|---|
| R-BL-01 | PDMG Current와 NSIGHT Target을 분리한다. |
| R-BL-02 | 모든 주요 Architecture Domain은 TEXT diagram과 evidence를 가진다. |
| R-BL-03 | Critical GAP는 ADR 또는 구현/Evidence로 닫는다. |
| R-BL-04 | Architecture PASS와 Implementation PASS를 분리한다. |
| R-BL-05 | Unknown/Open 값을 숨기거나 추정하지 않는다. |
| R-BL-06 | ServiceId를 Source/Data/Runtime trace의 핵심축으로 사용한다. |
| R-BL-07 | Artifact/Deployment/Runtime evidence를 Baseline에 연결한다. |
| R-BL-08 | Security/Performance/DR는 runtime test evidence가 없으면 최종 PASS가 아니다. |
| R-BL-09 | Baseline 변경은 ADR/Version으로 관리한다. |
| R-BL-10 | HG90만 공식 Release Baseline으로 본다. |

---

# 19. Verification / Test

## FIG-18-20. Verification Flow

```text
Architecture Model
   ↓
Static / Config Check
   ↓
Integration Test
   ↓
Failure / Security / Performance Test
   ↓
Runtime Evidence
   ↓
PASS / GAP
```

| Test ID | 검증내용 |
|---|---|
| T-BL-01 | all chapter files/figures validation |
| T-BL-02 | critical gap status review |
| T-BL-03 | source/config conformance |
| T-BL-04 | security integration |
| T-BL-05 | load/failure/DR |
| T-BL-06 | deployment evidence |
| T-BL-07 | runtime evidence |
| T-BL-08 | G80 approval/HG90 manifest |

---

# 20. GAP Register

## FIG-18-21. GAP Lifecycle

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

| GAP ID | GAP | 중요도 | 전환조건 |
|---|---|---|---|
| GAP-BL-01 | JWT critical integration | Critical | 11장 GAP close |
| GAP-BL-02 | Identity binding | Critical | security test |
| GAP-BL-03 | Actual physical/deployment mapping | Critical | 05/14 trace |
| GAP-BL-04 | Runtime evidence automation | Critical | 17 gate |
| GAP-BL-05 | External interface inventory | High | 06 registry |
| GAP-BL-06 | RDW/ADW/table ownership mapping | High | 07 registry |
| GAP-BL-07 | pdmg-om scope | High | 14 evidence |
| GAP-BL-08 | capacity/RTO/RPO approval | Critical | 16 test/ADR |

---

# 21. Risk Register

## FIG-18-22. Risk Propagation

```text
Cause
  ↓
Technical Failure
  ↓
Service Impact
  ↓
Operational / Business Impact
```

| Risk ID | Risk | 영향 |
|---|---|---|
| RISK-BL-01 | 문서 PASS만으로 완료 선언 | runtime mismatch |
| RISK-BL-02 | PDMG AS-IS를 Target으로 자동승격 | technical debt lock-in |
| RISK-BL-03 | Target을 Current로 표현 | false architecture |
| RISK-BL-04 | critical gaps open at go-live | security/availability failure |
| RISK-BL-05 | evidence chain break | audit/impact analysis failure |

---

# 22. Architecture Decision / ADR

## FIG-18-23. Decision Flow

```text
Decision Question
   ↓
주안 / 대안
   ↓
장점 / 단점
   ↓
Evidence / Test
   ↓
ADR
   ↓
Baseline
```

| ADR/Task | 의사결정 주제 | 현재 방향 |
|---|---|---|
| ADR-TASK-001 | Architecture SSOT | model+registry+gate |
| ADR-TASK-003 | PDMG→NSIGHT Mapping | explicit |
| ADR-TASK-010~013 | Security decisions | must close |
| ADR-TASK-026~032 | capacity/HA/DR | test based |
| ADR-TASK-040 | Runtime Evidence | HG90 gate |

---

# 23. Architecture PASS / PDMG Conformance

## FIG-18-24. PASS Model

```text
Architecture Definition
 ↓
CONDITIONAL PASS

Why not final PASS?
Critical implementation/evidence gaps remain

Target end state
HG90 Evidence-backed Baseline PASS
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Architecture coverage | PASS | 01-17 defined |
| Current source coverage | PASS/PARTIAL | major modules/runtime |
| Security | GAP/CRITICAL | issuer/verifier/key/identity |
| Physical/Deployment | GAP | actual mapping |
| Capacity/DR | CONDITIONAL | test/approval |
| Runtime Evidence | GAP | automation/gate |
| Final HG90 | OPEN | conditions not yet closed |

**Architecture Definition:** `CONDITIONAL PASS`  
**Current PDMG Conformance:** `PARTIAL / GAP`  
**Runtime Evidence Coverage:** `MEDIUM`

---

# 24. Next Chapter Handoff

## FIG-18-25. 18 → 19

```text
18 INTEGRATED BASELINE
"전체 PDMG Architecture를 통합하고 승인한다."
     ↓
HG90
Evidence-backed Architecture Baseline Release
```

---

# 25. PDMG INTEGRATED ARCHITECTURE BASELINE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG INTEGRATED ARCHITECTURE BASELINE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**18장 Architecture Definition 판정: `CONDITIONAL PASS`**


---
