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
