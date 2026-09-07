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
