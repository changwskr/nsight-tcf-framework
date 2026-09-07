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
