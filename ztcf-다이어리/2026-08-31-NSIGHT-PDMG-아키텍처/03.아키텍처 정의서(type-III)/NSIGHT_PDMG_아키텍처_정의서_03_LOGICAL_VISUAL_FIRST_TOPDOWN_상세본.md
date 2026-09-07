# NSIGHT / PDMG 아키텍처 정의서
# 03. LOGICAL — Zone / Logical System / Node / Layer / Connection
## Visual-First / Top-down / Drill-down 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-VISUAL-LOGICAL-03`  
> Architecture Level: **LOGICAL / L2**  
> 문서 상태: **Draft / Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 작성 원칙: **Zone → System → Node → Component → Layer → Connection → Environment → Evidence**  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_02_BIG_PICTURE_VISUAL_FIRST_TOPDOWN_상세본.md`  
> 후속 장: **04. PHYSICAL**

---

# 0. 이 문서를 읽는 방법

LOGICAL은 Hostname, CPU, Memory, Port, 제품 Version을 정하는 장이 아니다.

```text
BIG PICTURE
Application / System Group / Data Subject / Boundary
        │
        ▼
LOGICAL
Zone
        │
        ▼
Logical System
        │
        ▼
Logical Node
        │
        ▼
Technology Component
        │
        ▼
Layer
        │
        ▼
Allowed / Forbidden Connection
        │
        ▼
Environment / DR Scope
        │
        ▼
PHYSICAL Handoff
```

가장 중요한 구분은 다음이다.

```text
Zone
≠ VLAN / Subnet

Logical System
≠ Physical Server

Logical Node
≠ Hostname

Technology Component
≠ Product Inventory

Module
≠ Logical Node

Logical Interface Boundary
≠ Protocol / Port 상세
```

---

# 1. VISUAL ROUTE — LOGICAL 전체를 한 장으로 보기

## FIG-LG-01. LOGICAL Architecture Journey

```text
╔══════════════════════════════════════════════════════════════════════════════╗
║                           LOGICAL ARCHITECTURE                               ║
╚══════════════════════════════════════════════════════════════════════════════╝

 BIG PICTURE RESPONSIBILITY
        │
        ▼
 ① ZONE
    누가 들어오고 / 무엇을 신뢰하고 / 어떤 책임을 가지는가?
        │
        ▼
 ② LOGICAL SYSTEM
    어떤 논리 시스템이 그 책임을 소유하는가?
        │
        ▼
 ③ LOGICAL NODE
    어떤 실행 역할로 분리되는가?
        │
        ▼
 ④ TECHNOLOGY COMPONENT
    Client / Service / Interface / Data / Delivery
        │
        ▼
 ⑤ CONNECTION
    무엇이 무엇을 호출할 수 있는가?
        │
        ▼
 ⑥ ENVIRONMENT
    운영 / 개발 / DR / 선도 / 이행
        │
        ▼
 ⑦ PHYSICAL HANDOFF
    Host / JVM / Appliance / Network / HA / Capacity
```

### 이 그림에서 봐야 할 것

- LOGICAL은 **배치 전에 책임과 연결을 고정하는 단계**다.
- Logical에서 제품과 서버 수를 먼저 정하면 Architecture가 인프라 목록으로 변한다.
- Physical은 Logical Decision을 구현하는 단계이지, Logical을 대신하는 단계가 아니다.

---

# 2. BIG PICTURE에서 전달받은 계약

## FIG-LG-02. BIG PICTURE → LOGICAL Input

```text
BIG PICTURE
│
├─ Application Group
│   MP / RD / AD / BI / DG / IM
│
├─ System Group
│   Marketing / Data / BI / Governance / Support
│
├─ Data Subject
│   RDW / ADW / MP / BI / DG / IM
│
├─ Runtime Meaning
│   Online / Event / CDC / ETL / File
│
├─ Boundary
│   Application / Data / External / Security
│
└─ PDMG Reference
    Information Application 하위 Reference
        │
        ▼
LOGICAL
Zone / System / Node / Component / Layer / Connection
```

---

# 3. LOGICAL 핵심 결론

## FIG-LG-03. Logical Decision Principle

```text
Host를 정하기 전에

Zone
 ↓
Logical System
 ↓
Logical Node
 ↓
Layer
 ↓
Connection
 ↓
Environment Scope

를 먼저 고정한다.

가지에 들어가지 않는 신규 구성
        │
        ▼
      GAP
        │
        ▼
      ADR
```

### 한 줄 결론

> **LOGICAL의 핵심은 “서버를 그리기 전에 책임과 허용 연결을 고정하는 것”이다.**

---

# 4. 6 Zone 전체 구조

## FIG-LG-04. Enterprise 6-Zone Logical Architecture

```text
┌────────────────────────────── Channel Zone ────────────────────────────────┐
│                                                                           │
│ [Z1 대내 채널]       [Z2 대고객 채널]       [Z3 대외 채널]               │
│ 통합업무/정보포털     Web/Mobile             기관/계열/외부                │
│        │                    │                      │                       │
└────────┼────────────────────┼──────────────────────┼───────────────────────┘
         └────────────────────┼──────────────────────┘
                              ▼
┌──────────────────────────── Z4 채널 통합 ─────────────────────────────────┐
│ MCA / MCI / API Entry / 인증·프로토콜 변환 / Routing                     │
└──────────────────────────────┬────────────────────────────────────────────┘
                               │ Standard Contract
                               ▼
┌──────────────────────────── Z5 서비스 제공 ────────────────────────────────┐
│                                                                           │
│ Marketing / Data Platform / BI / Governance / IT Service                 │
│                                                                           │
│ Client → Service → Interface → Data → Delivery                           │
│                                                                           │
└──────────────────────────────┬────────────────────────────────────────────┘
                               │ Approved Internal Integration
                               ▼
┌──────────────────────────── Z6 대내 통합 ──────────────────────────────────┐
│ API / EAI / FOS / MFT / 내부 라우팅 / Enterprise Integration             │
└──────────────────────────────┬────────────────────────────────────────────┘
                               ▼
                     Core / Internal / Related
```

---

# 5. Zone의 정의

## FIG-LG-05. Zone Decision Questions

```text
Zone을 정할 때 묻는 질문

Who enters?
   ↓
무엇이 들어오는가?

Trust Level?
   ↓
얼마나 신뢰할 수 있는가?

Responsibility?
   ↓
무엇을 처리하는 공간인가?

Interface?
   ↓
어떤 표준을 통과해야 하는가?

Failure Boundary?
   ↓
장애가 어디까지 퍼질 수 있는가?
```

### Zone은 무엇이 아닌가

```text
Zone = VLAN             X
Zone = Subnet           X
Zone = 서버 랙          X
Zone = 방화벽 구간만    X
```

Zone은 **논리 책임·신뢰·연계 경계**다.

---

# 6. Z1 — 대내 채널

## FIG-LG-06. Internal Channel Zone

```text
┌──────────────────────── Z1 대내 채널 ────────────────────────┐
│                                                             │
│ 내부 직원 / 영업점 / 통합업무 / 정보계 단말                  │
│                                                             │
│  Internal User                                              │
│      │                                                      │
│      ▼                                                      │
│  Terminal / Portal                                          │
│      │                                                      │
│      ▼                                                      │
│  Channel Integration                                        │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### 책임

- 내부 사용자 진입
- 사용자/단말 Context 제공
- 업무 요청 시작
- 직접 Business DB 접근은 하지 않음

---

# 7. Z2 — 대고객 채널

## FIG-LG-07. Customer Channel Zone

```text
┌────────────────────── Z2 대고객 채널 ───────────────────────┐
│                                                            │
│ Customer                                                   │
│   │                                                        │
│   ├─ Web                                                   │
│   ├─ Mobile                                                │
│   └─ Digital Channel                                       │
│        │                                                   │
│        ▼                                                   │
│ Channel Integration / Security                             │
│                                                            │
└────────────────────────────────────────────────────────────┘
```

### 특징

```text
외부 신뢰도 낮음
인증 / 세션 / 토큰 / 위변조 통제 중요
Rate / Abuse / Security 고려
```

---

# 8. Z3 — 대외 채널

## FIG-LG-08. External Institution Zone

```text
External Institution / Affiliate / Partner
        │
        ▼
┌──────────────────────── Z3 대외 채널 ─────────────────────────┐
│ Protocol / Institution Boundary                               │
│ Certificate / Account / Contract / Audit                     │
└───────────────────────────┬────────────────────────────────────┘
                            ▼
                     Channel / Internal Integration
```

### 핵심

- 기관별 계약·인증·프로토콜 차이를 내부 업무 Layer로 침투시키지 않는다.
- Channel/Integration Layer에서 정규화한다.

---

# 9. Z4 — 채널 통합

## FIG-LG-09. Channel Integration Zone

```text
Z1 Internal
   │
Z2 Customer
   │
Z3 External
   │
   └──────────────┐
                  ▼
        ┌───────────────────────┐
        │ Z4 채널 통합          │
        │                       │
        │ Authentication Entry  │
        │ Protocol Translation  │
        │ Routing               │
        │ Channel Context       │
        │ API / MCA / MCI       │
        └───────────┬───────────┘
                    ▼
              Z5 서비스 제공
```

### 역할

```text
진입 표준화
신뢰경계 형성
프로토콜/채널 차이 흡수
Application 직접노출 최소화
```

---

# 10. Z5 — 서비스 제공

## FIG-LG-10. Service Delivery Zone

```text
┌──────────────────────── Z5 서비스 제공 ────────────────────────┐
│                                                               │
│ [MP] Marketing                                                │
│ [RD/AD] Data Platform                                        │
│ [BI] BI Portal                                                │
│ [DG] Data Governance                                         │
│ [IM] IT Service & Support                                    │
│                                                               │
│ Logical Pattern                                              │
│ Client → Service → Interface → Data → Delivery               │
│                                                               │
└───────────────────────────┬───────────────────────────────────┘
                            ▼
                        Z6 대내 통합
```

### 핵심

- 업무 책임의 중심 Zone이다.
- 다른 Zone의 내부 구현을 직접 참조하지 않는다.
- Domain/Application Responsibility에 따라 Logical System을 분리한다.

---

# 11. Z6 — 대내 통합

## FIG-LG-11. Internal Integration Zone

```text
Z5 Service
    │
    │ Approved Integration
    ▼
┌──────────────────────── Z6 대내 통합 ────────────────────────┐
│ API / EAI / FOS / MFT / Relay / Enterprise Routing          │
└───────────────────────────┬──────────────────────────────────┘
                            ▼
                Core / Internal / Related Systems
```

### 역할

- 내부계/연관 시스템과의 표준 통합
- File/Enterprise Integration 통제
- 내부 시스템 변화의 직접 전파 최소화

---

# 12. 표준 요청 경로

## FIG-LG-12. Standard Request Path

```text
Channel
  │
  ▼
Z4 Channel Integration
  │
  ▼
Z5 Service Entry
  │
  ▼
Service Layer
  │
  ▼
Interface / Data Layer
  │
  ├────────► Owned Data
  │
  └────────► Z6 Internal Integration
                 │
                 ▼
          Core / Related System
```

### 표준 경로 원칙

```text
Entry
→ Standard Service
→ Controlled Interface
→ Owned/Approved Data
```

---

# 13. 허용 Path

## FIG-LG-13. Allowed Paths

```text
Z1/Z2/Z3
    ↓
Z4
    ↓
Z5

Z5
    ↓
Owned Data

Z5
    ↓
Z6
    ↓
Internal / Related

Core Change
    ↓
CDC
    ↓
RDW

RDW
    ↓
ETL
    ↓
ADW
```

---

# 14. 금지 Path

## FIG-LG-14. Forbidden Paths

```text
Channel ───────────────► DB                       X

Z1/Z2/Z3 ──────────────► Z5 내부 Service 직접우회 X

Application A ─────────► Application B DAO        X

Application A ─────────► Application B Table DML  X

BI ────────────────────► Core DB Massive Query    X

Event Consumer ────────► Online Request Thread    X

Z5 ────────────────────► External Direct          X
                         (승인된 Integration 없이)
```

---

# 15. Boundary 재검증

## FIG-LG-15. Boundary Revalidation

```text
Channel에서 인증됨
      │
      ▼
Z4 통과
      │
      ▼
Z5 진입
      │
      ▼
업무 인가 / 서비스권한 재검증
      │
      ▼
Data 접근 시 Data 권한 재검증
```

### 핵심

> **상위 Boundary에서 검증됐다고 하위 Boundary가 무조건 신뢰해서는 안 된다.**

---

# 16. Zone Decision Tree

## FIG-LG-16. Zone Classification Decision

```text
신규 구성요소
   │
   ├─ 사용자/고객/외부 진입인가?
   │     ├─ 내부사용자 → Z1
   │     ├─ 고객       → Z2
   │     └─ 외부기관   → Z3
   │
   ├─ 채널통합/프로토콜변환인가?
   │     └─ Z4
   │
   ├─ 업무서비스/데이터/BI/거버넌스인가?
   │     └─ Z5
   │
   └─ 내부계 통합/파일/중계인가?
         └─ Z6
```

가지에 맞지 않으면:

```text
UNKNOWN
  ↓
GAP
  ↓
ADR
```

---

# 17. Logical System의 정의

## FIG-LG-17. Logical System

```text
Zone
  │
  ▼
Logical System
  │
  ├─ Responsibility
  ├─ Runtime Type
  ├─ Boundary
  ├─ Data Role
  ├─ Owner
  └─ NFR
```

### Logical System은 무엇이 아닌가

```text
Logical System = Hostname      X
Logical System = VM            X
Logical System = WAR           X
Logical System = 제품명        X
```

---

# 18. Z5 서비스 제공의 5대 Logical System

## FIG-LG-18. Service Zone Logical Systems

```text
Z5 Service Delivery
│
├─ Marketing Platform Logical System
│
├─ Data Platform Logical System
│   ├─ RD Logical Responsibility
│   └─ AD Logical Responsibility
│
├─ BI Portal Logical System
│
├─ Data Governance Logical System
│
└─ IT Service & Business Support Logical System
```

### 핵심

- BIG PICTURE의 5대 Service Domain을 Logical System 책임으로 내린다.
- Data Platform 안의 RD/AD는 내부 책임을 분리한다.

---

# 19. Application Group과 Logical System 구분

## FIG-LG-19. Classification vs Logical Architecture

```text
Application Group
= 업무분류 / 책임분류

       │ Mapping
       ▼

Logical System
= 실행·통합·운영 관점의 논리 시스템
```

예:

```text
MP / IC / A
   ↓
Marketing Platform Logical System
   ↓
Customer Service Logical Node
```

---

# 20. Logical Node의 정의

## FIG-LG-20. Logical Node Anatomy

```text
Logical System
    │
    ▼
Logical Node
    │
    ├─ Runtime Role
    ├─ Responsibility
    ├─ Interface
    ├─ Data Dependency
    ├─ Failure Domain
    ├─ Scale Characteristic
    └─ Environment Scope
```

### 예

```text
Marketing Platform
  ├─ WEB Node
  ├─ Online Service Node
  ├─ Real-time/Event Node
  └─ Support Node
```

---

# 21. Logical Node vs Physical Host

## FIG-LG-21. Node/Host Separation

```text
Logical Node
"Marketing Online Service"
        │
        │ Physical Mapping
        ▼
Physical
Host #1
Host #2
...
```

### 핵심

```text
Logical Node 1개
=
Physical Host 1개

라고 가정하지 않는다.
```

---

# 22. Logical Node 필수 속성

최소 속성:

| 속성 | 질문 |
|---|---|
| Node ID | 논리 식별자는 무엇인가 |
| Zone | 어느 Zone인가 |
| System | 어느 Logical System인가 |
| Role | 어떤 실행 책임인가 |
| Runtime Type | Online/Event/CDC/ETL/Batch 등 |
| Interface | 무엇과 통신하는가 |
| Data | 어떤 Data Subject를 사용하는가 |
| Scale | 수평/수직/전용 여부 |
| Failure | 장애 파급범위 |
| Environment | 운영/개발/DR/선도 |
| Evidence | 근거 |
| Status | FACT/OPEN/GAP |

---

# 23. Environment 전체 구조

## FIG-LG-22. Environment Matrix Overview

```text
Environment
│
├─ Production
│
├─ Development
│
├─ DR
│
├─ Pilot / Leading
│
└─ Temporary Migration
```

### Logical 단계에서 정하는 것

```text
어떤 System/Node가 어느 Environment에 존재해야 하는가?
```

### 정하지 않는 것

```text
실제 Hostname / CPU / Memory
```

---

# 24. Production Environment

## FIG-LG-23. Production Logical Scope

```text
Production
│
├─ Marketing
├─ Data Platform
├─ BI
├─ Governance
└─ IT Service & Support
```

대표 원칙:

```text
5개 Logical System 영역 전체 운영 대상
```

---

# 25. Development Environment

## FIG-LG-24. Development Logical Scope

```text
Development
│
├─ Marketing
├─ Data Platform
├─ BI
├─ Governance
└─ IT Service & Support
```

### 원칙

- 운영과 역할 구조를 최대한 정합하게 유지
- 데이터/규모/보안 제약은 환경에 맞게 조정

---

# 26. DR Environment

## FIG-LG-25. DR Logical Scope

```text
DR
│
├─ Marketing
├─ Data Platform
└─ BI / Critical Support
```

현재 Baseline은:

```text
운영 5
개발 5
DR 3
선도 3
```

패턴으로 설명되는 자료가 존재한다.

정확한 DR Scope는 최신 승인본을 재검증한다.

---

# 27. Pilot / Leading Environment

## FIG-LG-26. Pilot Scope

```text
Pilot / Leading
│
├─ 핵심 Application
├─ 핵심 Data
└─ 핵심 BI / Validation
```

### 목적

```text
신기술/구조/표준 검증
운영 전체와 동일한 규모가 목적은 아님
```

---

# 28. Environment 5 / 5 / 3 / 3 Matrix

## FIG-LG-27. Environment Responsibility Matrix

```text
                  PROD   DEV   DR   PILOT
Marketing           ●     ●     ●      ●
Data Platform       ●     ●     ●      ●
BI Portal           ●     ●     ●      ●
Governance          ●     ●     △      △
IT Service          ●     ●     △      △
```

`●/△`는 구조 설명용이며,
실제 Scope는 최신 승인 자료로 재검증한다.

---

# 29. Temporary Migration Environment

## FIG-LG-28. Migration Temporary Structure

```text
Legacy
   │
   ├─ Migration Data
   ├─ Temporary Interface
   └─ Transition Service
       │
       ▼
Temporary Logical Node
       │
       ▼
Target
```

### 원칙

```text
Temporary
≠ Target Baseline

Cut-over 이후
→ 제거 / 비활성 / 별도 Archive
```

---

# 30. Technology Component의 정의

## FIG-LG-29. Technology Component

```text
Logical Node
   │
   ├─ Client Component
   ├─ Service Component
   ├─ Interface Component
   ├─ Data Component
   └─ Configuration / Delivery Component
```

### Component는 무엇이 아닌가

```text
Technology Component = 제품 Version Inventory   X
Technology Component = 서버 패키지 목록         X
```

---

# 31. 공통 Technology Component

## FIG-LG-30. Common Component Pattern

```text
Client
  │
  ▼
Service
  │
  ▼
Interface
  │
  ▼
Data
  │
  ▼
Configuration / Delivery
```

이 구조를 Domain별로 반복 적용한다.

---

# 32. VM Node Component Pattern

## FIG-LG-31. VM-style Logical Node

```text
┌──────────────────── Logical VM Node ────────────────────┐
│                                                       │
│ Client / Entry Component                              │
│        │                                              │
│        ▼                                              │
│ Service Component                                     │
│        │                                              │
│        ▼                                              │
│ Interface Component                                   │
│        │                                              │
│        ▼                                              │
│ Data Access Component                                 │
│                                                       │
│ + Config / Logging / Monitoring / Security            │
└───────────────────────────────────────────────────────┘
```

Physical VM은 다음 장에서 Mapping한다.

---

# 33. DB Appliance Component Pattern

## FIG-LG-32. Data Appliance Logical Pattern

```text
┌──────────────────── Data Platform Node ────────────────────┐
│                                                          │
│ Data Service / Query Entry                               │
│          │                                               │
│          ▼                                               │
│ Logical DB Service                                       │
│          │                                               │
│          ▼                                               │
│ RDW / ADW Data Role                                      │
│                                                          │
│ Monitoring / Backup / DR / Security                      │
└──────────────────────────────────────────────────────────┘
```

### LOGICAL에서 하지 않는 것

```text
RAC Node 수
Exadata Model
Storage Cell 수
SCAN IP
```

---

# 34. 공통 Layered Architecture

## FIG-LG-33. Five Logical Layers

```text
┌─────────────────────────────┐
│ Client Layer                │
└─────────────┬───────────────┘
              ▼
┌─────────────────────────────┐
│ Service Layer               │
└─────────────┬───────────────┘
              ▼
┌─────────────────────────────┐
│ Interface Layer             │
└─────────────┬───────────────┘
              ▼
┌─────────────────────────────┐
│ Data Layer                  │
└─────────────┬───────────────┘
              ▼
┌─────────────────────────────┐
│ Configuration / Delivery    │
└─────────────────────────────┘
```

---

# 35. Client Layer

## FIG-LG-34. Client Layer Responsibility

```text
Client
│
├─ UI / Terminal
├─ Browser / Mobile
├─ Channel Adapter
└─ Request Initiator
```

### 금지

```text
Client → DAO       X
Client → DB        X
Client → Mapper    X
```

---

# 36. Service Layer

## FIG-LG-35. Service Layer

```text
Service Layer
│
├─ Entry
├─ Business Service
├─ Orchestration
├─ Validation
├─ Authorization
└─ Transaction Boundary Candidate
```

PDMG Reference에서는:

```text
Handler / Facade / Service
```

가 이 Layer를 세분화한다.

---

# 37. Interface Layer

## FIG-LG-36. Interface Layer

```text
Service
  │
  ▼
Interface Adapter
  │
  ├─ API
  ├─ Event
  ├─ CDC
  ├─ ETL
  ├─ File
  └─ External/Enterprise Adapter
```

### 핵심

Interface Layer는:

```text
다른 시스템의 내부구조를 숨기는 경계
```

다.

---

# 38. Data Layer

## FIG-LG-37. Data Layer

```text
Service
  │
  ▼
Data Access
  │
  ├─ DAO / Mapper
  ├─ Data Service
  └─ Approved Query
  │
  ▼
Owned / Approved Data
```

---

# 39. Configuration / Delivery Layer

## FIG-LG-38. Delivery Layer

```text
Source / Build
     │
     ▼
Configuration
     │
     ▼
Artifact
     │
     ▼
Deployment
     │
     ▼
Runtime
```

LOGICAL에서는 역할만 정의하고,
실제 CI/CD Tool 상세는 후속 운영장에 둔다.

---

# 40. Data Ownership Rule

## FIG-LG-39. Own Data / Cross-domain Data

```text
Application A
    │
    ├─ Own Data
    │    └─ Direct Approved Access 가능
    │
    └─ Other Domain Data
         └─ Interface / Data Service 경유
```

### 금지

```text
Application A
  └─ Other Domain Table Direct DML   X
```

---

# 41. RDW / ADW Data Role

## FIG-LG-40. Data Role Separation

```text
Core / Source
    │
    │ CDC
    ▼
   RDW
    │
    ├─ Operational / Near-real-time Use
    │
    │ ETL
    ▼
   ADW
    │
    └─ Analytical / Mart / BI Use
```

### Logical 핵심

- RDW와 ADW는 물리 장비 분리 여부 이전에 **논리 책임이 먼저 분리**된다.
- 동일 DB Appliance 여부는 PHYSICAL에서 결정한다.

---

# 42. Governance Data Role

## FIG-LG-41. Governance Plane

```text
Metadata
   │
Quality
   │
Lineage
   │
Control
   ▼
Application / RDW / ADW / BI
```

Governance는 Business Data Runtime을 직접 대신하지 않는다.

---

# 43. Marketing Platform Layered Architecture

## FIG-LG-42. MP Logical Architecture

```text
┌──────────────────────── Marketing Platform ────────────────────────┐
│                                                                  │
│ Client                                                            │
│   Terminal / Web / Channel                                       │
│       │                                                          │
│       ▼                                                          │
│ Service                                                           │
│   Customer / Sales / Campaign / Behavior / Contact               │
│       │                                                          │
│       ▼                                                          │
│ Interface                                                         │
│   API / Event / Data Service                                     │
│       │                                                          │
│       ▼                                                          │
│ Data                                                              │
│   RDW / Approved Data / Event Data                               │
│                                                                  │
└──────────────────────────────────────────────────────────────────┘
```

---

# 44. MP Logical Node 후보

## FIG-LG-43. MP Node Candidates

```text
Marketing Platform
│
├─ WEB / Client Delivery Node
├─ Online Service Node
├─ Customer / Single View Node
├─ Campaign / EBM Node
├─ Real-time Processing Node
├─ Behavior Processing Node
└─ Contact / Message Node
```

### 주의

이 목록은 Logical Candidate다.
실제 서버 개수/배치는 PHYSICAL에서 검증한다.

---

# 45. MP Workload 분리

## FIG-LG-44. Marketing Runtime Separation

```text
Online Query
      │
      ├───────────────┐
      ▼               ▼
Customer Service     Event Processing
                      │
                      ▼
                  EBM / Decision

Batch / Campaign
      │
      ▼
Separate Batch Runtime
```

### 핵심

```text
Online
Event
Batch
```

는 같은 Business Domain 안에서도 Logical Runtime을 분리할 수 있다.

---

# 46. Data Platform Layered Architecture

## FIG-LG-45. Data Platform Logical Architecture

```text
Source
  │
  ├─ CDC
  ▼
RDW
  │
  ├─ Data Service / Query
  │
  └─ ETL
       │
       ▼
      ADW
       │
       └─ Analysis / BI
```

---

# 47. Data Platform Logical Nodes

## FIG-LG-46. Data Nodes

```text
Data Platform
│
├─ CDC Logical Node
├─ RDW Logical Node
├─ ETL Logical Node
└─ ADW Logical Node
```

### 책임

| Node | Logical Responsibility |
|---|---|
| CDC | Change Capture / Delivery |
| RDW | Near-real-time Data |
| ETL | Bulk Transform / Load |
| ADW | Analytical Data |

---

# 48. Data Platform Responsibility

## FIG-LG-47. Data Responsibility Chain

```text
Capture
  ↓
Store
  ↓
Transform
  ↓
Analyze
```

```text
CDC   → Capture
RDW   → Operational Store
ETL   → Transform
ADW   → Analytical Store
```

---

# 49. BI Portal Layered Architecture

## FIG-LG-48. BI Logical Architecture

```text
BI User
   │
   ▼
BI Portal / Self BI / OLAP
   │
   ▼
Query / Data Access Layer
   │
   ├────────► RDW
   └────────► ADW
```

### BI 금지

```text
BI → Core Direct Massive Query    X
BI → Application DAO              X
Self-BI → Any Schema Unlimited    X
```

---

# 50. Data Governance Layered Architecture

## FIG-LG-49. DG Logical Architecture

```text
Governance UI / Admin
      │
      ▼
Metadata / Quality / Lineage Service
      │
      ▼
Governance Repository
      │
      └────────► Application / Data Platform Metadata
```

---

# 51. IT Service & Support Logical Architecture

## FIG-LG-50. IM Logical Architecture

```text
IT Service & Support
│
├─ Framework / Common
├─ SCM / Deployment
├─ Batch / Scheduler
├─ Relay / File
├─ Terminal Management
└─ Monitoring / Operations
```

### 핵심

IM은 Cross-cutting Support 영역이지,
모든 Business Logic의 공통 저장소가 아니다.

---

# 52. Technology Component Inventory

최소:

```text
Component ID
Node ID
Layer
Responsibility
Interface
Runtime Type
Data Dependency
Owner
Evidence
Status
```

---

# 53. Logical Node Inventory Template

```yaml
logicalNode:
  nodeId:
  zone:
  logicalSystem:
  name:
  responsibility:
  runtimeType:
  layers:
  inbound:
  outbound:
  dataSubjects:
  securityBoundary:
  scaleType:
  failureDomain:
  environments:
  evidence:
  status:
```

---

# 54. Marketing Platform 논리 기술 아키텍처

## FIG-LG-51. MP End-to-End Logical View

```text
Channel
   │
   ▼
Channel Integration
   │
   ▼
Marketing Entry
   │
   ├─ Customer
   ├─ Campaign
   ├─ Behavior
   └─ Contact
   │
   ├────────► Event / Kafka
   │
   ├────────► RDW
   │
   └────────► Internal Integration
```

---

# 55. Data Platform 논리 기술 아키텍처

## FIG-LG-52. Data End-to-End

```text
Core / Source
   │
   │ CDC
   ▼
RDW
   │
   ├─ Operational Consumer
   │
   │ ETL
   ▼
ADW
   │
   ▼
BI / Analysis
```

---

# 56. BI 논리 기술 아키텍처

## FIG-LG-53. BI End-to-End

```text
User
 ↓
BI Client
 ↓
BI Service
 ↓
Data Access
 ↓
RDW / ADW
```

---

# 57. DG 논리 기술 아키텍처

## FIG-LG-54. DG End-to-End

```text
Governance User
 ↓
Governance Service
 ↓
Metadata / Quality / Lineage
 ↓
Governance Repository
 ↓
Reference to Enterprise Data Assets
```

---

# 58. Logical Connection Matrix

## FIG-LG-55. Connection Matrix Concept

```text
FROM
  │
  ▼
[Logical Node A]
  │
  │ Mechanism / Direction / Purpose
  ▼
[Logical Node B]
  │
  ▼
TO
```

최소 Matrix:

| From | To | Purpose | Mechanism | Direction | Allowed | Security | Trace |
|---|---|---|---|---|---|---|---|
| Channel | Z4 | Request | Online | → | Y | Auth | GUID |
| Z4 | MP | Service | API/MCA | → | Y | AuthZ | GUID |
| Core | RDW | Change | CDC | → | Y | Account | Batch/Trace |
| RDW | ADW | Bulk | ETL | → | Y | DB/Job | JobId |
| BI | RDW/ADW | Query | Data Access | → | Y | Data Auth | QueryId |

---

# 59. Logical Interface Boundary

## FIG-LG-56. Boundary Pattern

```text
Logical Node A
   │
   ▼
Interface Boundary
   │
   ├─ Contract
   ├─ Security
   ├─ Timeout
   ├─ Recovery
   └─ Trace
   │
   ▼
Logical Node B
```

### Logical에서 확정

```text
누가 누구를 호출?
왜 호출?
동기/비동기?
경계를 통과하는가?
```

### Mechanism에서 확정

```text
Header
Schema
Exact Protocol
Retry
Timeout Value
Message Format
```

---

# 60. Security Logical Boundary

## FIG-LG-57. Security by Zone

```text
Z1/Z2/Z3
  │ Identity / Channel Trust
  ▼
Z4
  │ Entry Authentication / Protocol Trust
  ▼
Z5
  │ Service Authorization / Data Authorization
  ▼
Z6
  │ Enterprise Integration Trust
  ▼
Data
  │ DB Account / Encryption / Masking / Audit
```

---

# 61. Observability Logical Boundary

## FIG-LG-58. Trace Across Logical Nodes

```text
Channel
  │ GUID
  ▼
Z4
  │ GUID / Channel
  ▼
Z5
  │ GUID / ServiceId
  ▼
Interface
  │ Correlation
  ▼
Data / External
  │ SQL/Job/Event ID
  ▼
Result / Log / Metric
```

### 핵심

- Logical Node 경계를 통과해도 Trace Key가 끊기면 안 된다.

---

# 62. NFR → LOGICAL Mapping

## FIG-LG-59. NFR Projection

```text
Performance
→ Runtime Node Separation / Allowed Path

Availability
→ Failure Domain / Node Redundancy Candidate

Scalability
→ Independent Logical Node

Security
→ Zone / Trust / Revalidation

Observability
→ Cross-node Trace
```

---

# 63. FAST / DEEP Logical Mapping

## FIG-LG-60. FAST / DEEP Logical Zones

```text
FAST
Channel
  ↓
Z4
  ↓
Marketing Online / Event
  ↓
RDW

DEEP
RDW
  ↓
ETL
  ↓
ADW
  ↓
BI
```

### 핵심

FAST와 DEEP는 Big Picture의 전략을
Logical Node와 Connection으로 구현한다.

---

# 64. PDMG Logical Reference Position

## FIG-LG-61. PDMG in Z5

```text
Z5 Service Delivery
│
├─ Marketing Platform Logical System
│     │
│     └─ PDMG Reference
│         │
│         ├─ pdmg-ui      [Client / Entry Reference]
│         ├─ pdmg-jwt     [Security Service Reference]
│         ├─ pdmg-service [Business Service Reference]
│         ├─ pdmg-fw      [Framework Component]
│         └─ pdmg-om      [Operational Reference / Evidence Gap]
│
├─ Data Platform
├─ BI
├─ Governance
└─ IT Support
```

---

# 65. Module / Process / Node 구분

## FIG-LG-62. Three Different Boundaries

```text
Module Boundary
pdmg-service / pdmg-fw
        │
        │ ≠
        ▼
Process Boundary
Tomcat / Spring Boot Process
        │
        │ ≠
        ▼
Logical Node
Marketing Business Service Node
```

### 핵심

```text
pdmg-fw 모듈
→ 별도 Logical Node

라고 자동 판단하지 않는다.
```

---

# 66. PDMG Framework / Business Logical Relation

## FIG-LG-63. PDMG Reference Drill-down

```text
Marketing Business Service Logical Node
┌──────────────────────────────────────────────┐
│                                              │
│ Framework Components                        │
│ Filter / Context / TCF / Timeout / Error    │
│                  │                           │
│                  ▼                           │
│ Business Components                         │
│ Handler → Facade → Service → DAO / Mapper   │
│                                              │
└──────────────────────────────────────────────┘
```

### 해설

- `pdmg-fw`와 `pdmg-service`는 Build Module은 다르지만 같은 Runtime Node 안에서 동작 가능하다.
- Logical Architecture는 이 관계를 **책임 Layer**로 표현한다.

---

# 67. Environment Isolation Rule

## FIG-LG-64. Environment Boundary

```text
DEV
 ─────X────► PROD DB Direct

PILOT
 ─────X────► PROD Runtime uncontrolled

DR
 ─────X────► PROD와 무계획 상태공유
```

### 원칙

각 Environment는:

```text
Data
Credential
Endpoint
Configuration
Operational Permission
```

을 통제한다.

---

# 68. DR Logical Rule

## FIG-LG-65. DR Responsibility

```text
Primary Logical System
       │
       ▼
DR Logical Counterpart
       │
       ├─ Application Role
       ├─ Data Role
       ├─ Integration
       ├─ Security
       └─ Operations
```

DR 장비만 존재하는 것이 아니라
Logical Responsibility가 복구 가능해야 한다.

---

# 69. Logical HA Principle

## FIG-LG-66. HA at Logical Level

```text
Logical Service
    │
    ├─ Stateless / Replicated?
    ├─ Shared State?
    ├─ DB Dependency?
    ├─ Session?
    ├─ Message?
    └─ Failure Route?
```

### PHYSICAL로 넘길 것

```text
몇 대?
어느 L4?
어떤 Cluster?
어떤 Replication?
```

---

# 70. Logical Scalability Principle

## FIG-LG-67. Independent Scale Candidate

```text
Online Node
  ── independent scale

Event Node
  ── independent scale

ETL Node
  ── independent scale

BI Node
  ── independent scale

RDW / ADW
  ── data scale strategy
```

---

# 71. Logical Responsibility Matrix

| Logical System | Primary Responsibility | Runtime Type | Data Role |
|---|---|---|---|
| Marketing | 고객/마케팅 업무 | Online/Event | Consumer/Business |
| Data Platform RD | 준실시간 데이터 | CDC/Query | Operational Data |
| Data Platform AD | 분석 데이터 | ETL/Query | Analytical Data |
| BI | 분석 소비 | Query | Consumer |
| DG | Metadata/Quality | Control | Governance |
| IM | 공통/운영/배치 | Mixed | Support |

---

# 72. Technology Capability Map

## FIG-LG-68. Capability by Logical Role

```text
WEB / Delivery
→ Client Delivery

WAS / Service
→ Business Service

Event
→ Asynchronous Processing

CDC
→ Change Propagation

ETL
→ Bulk Transform

DB
→ Data Storage

BI
→ Analysis Consumption

Governance
→ Control

OM
→ Operations
```

---

# 73. Technology Component 배치 원칙

```text
Component
  ↓
Logical Node
  ↓
Responsibility
  ↓
NFR
  ↓
Physical Mapping
```

제품이 먼저가 아니다.

---

# 74. VM Node Rule

## FIG-LG-69. VM Candidate Rule

```text
Logical Node
    │
    ├─ 독립 장애영역 필요?
    ├─ 독립 Scale 필요?
    ├─ 보안경계 필요?
    ├─ 자원격리 필요?
    └─ 운영분리 필요?
        │
        ▼
   Physical VM Candidate
```

---

# 75. Appliance Rule

## FIG-LG-70. Appliance Candidate Rule

```text
Data Role
   │
   ├─ 대용량?
   ├─ 병렬처리?
   ├─ 고정형 Data Platform?
   └─ 전용 HW 필요?
        │
        ▼
   Appliance Candidate
```

Logical은 “전용 Data Platform”까지,
제품/모델은 Physical에서 결정한다.

---

# 76. Direct Data Access Rule

## FIG-LG-71. Data Access Guardrail

```text
Service
  │
  ├─ Own Data
  │    └─ Direct Approved Access
  │
  └─ Other Domain Data
       └─ Contracted Service / Interface
```

### 금지

```text
Cross-domain Direct DML
DB-Link by convenience
Shared generic account
```

---

# 77. Online / Batch Resource Rule

## FIG-LG-72. Logical Resource Separation

```text
Online
  │
  ├─ Interactive Request
  └─ Low Latency

Batch
  │
  ├─ Bulk
  └─ Throughput

Event
  │
  ├─ Async
  └─ Burst

ETL
  │
  ├─ Heavy I/O
  └─ Data Transform
```

### 핵심

같은 Business Domain이라도 Logical Runtime Role은 분리할 수 있다.

---

# 78. Gateway / EAI Rule

## FIG-LG-73. Integration Gateway Role

```text
External / Channel
        │
        ▼
Gateway / Integration
        │
        ▼
Business Service
```

역할:

```text
Routing
Protocol
Security
Contract Boundary
```

금지:

```text
Gateway = Business Logic Container   X
```

---

# 79. Logging / Monitoring Logical Rule

## FIG-LG-74. Observability Components

```text
Business Node
   │
   ├─ Log
   ├─ Metric
   ├─ Trace
   └─ Health
       │
       ▼
Monitoring / OM Logical Node
```

OM/Monitoring이 Business Runtime의 필수 동기 Dependency가 되지 않도록 설계한다.

---

# 80. Architecture Review — 신규 Node 질문

## FIG-LG-75. New Node Review

```text
신규 Node
  │
  ├─ 어느 Zone?
  ├─ 어느 Logical System?
  ├─ 어떤 Runtime Type?
  ├─ 어떤 Data?
  ├─ 어떤 Inbound/Outbound?
  ├─ 어떤 Security Boundary?
  ├─ 어떤 Scale/Failure 특성?
  ├─ 어떤 Environment?
  └─ 어떤 Evidence?
```

---

# 81. Architecture Conflict

신규 요소가:

```text
Zone에 안 맞음
System Responsibility에 안 맞음
허용 Path에 안 맞음
Data Owner에 안 맞음
```

이면:

```text
Architecture Conflict
     ↓
GAP
     ↓
ADR
```

로 처리한다.

---

# 82. Logical Anti-Pattern A — Zone = VLAN

```text
VLAN 10
= Channel Zone

VLAN 20
= Service Zone
```

만으로 정의하면:

```text
책임
신뢰
연계
장애
```

의미가 사라진다.

---

# 83. Logical Anti-Pattern B — System = Server

```text
Logical System
= WAS01
```

X

Logical System은 책임 단위다.

---

# 84. Logical Anti-Pattern C — Module = Remote System

```text
pdmg-fw Module
     ↓
FW Server
```

X

Module Boundary는 Runtime Process/Node를 자동 결정하지 않는다.

---

# 85. Logical Anti-Pattern D — 제품 Version 조기확정

```text
LOGICAL에서
Tomcat 10.x
Oracle Version
CPU 32C
Port 8080
```

확정 X

제품/Version/Port는 Physical/Mechanism에서 Evidence와 함께 결정한다.

---

# 86. Logical Anti-Pattern E — DR 미표시 = 불필요

```text
Logical 그림에 DR이 작게 보임
→ DR 필요 없음
```

X

DR Scope는 별도 승인/Business Criticality 기준으로 본다.

---

# 87. Logical Inventory SSOT

필요 Inventory:

```text
Zone Inventory
System Inventory
Node Inventory
Connection Matrix
Data Ownership Matrix
Environment Matrix
NFR Mapping
```

---

# 88. Zone Inventory Template

```yaml
zone:
  zoneId:
  name:
  trustLevel:
  responsibility:
  entryType:
  allowedInbound:
  allowedOutbound:
  securityRule:
  failureBoundary:
  evidence:
  status:
```

---

# 89. System Inventory Template

```yaml
logicalSystem:
  systemId:
  zone:
  name:
  responsibility:
  applicationGroups:
  runtimeTypes:
  dataSubjects:
  owner:
  environments:
  evidence:
  status:
```

---

# 90. Connection Matrix Template

```yaml
connection:
  fromNode:
  toNode:
  purpose:
  runtimeType:
  direction:
  allowed:
  securityBoundary:
  traceKey:
  evidence:
  status:
```

---

# 91. Data Ownership Matrix Template

```text
Data Subject
   ↓
Owner
   ↓
Primary Platform
   ↓
Producer
   ↓
Consumer
   ↓
Allowed Access
   ↓
Forbidden Access
```

---

# 92. NFR Checklist — Performance

```text
[ ] FAST / DEEP 분리?
[ ] Online / Batch/Event 자원 분리?
[ ] Unnecessary Hop 최소화?
[ ] Data Access Ownership 명확?
[ ] Heavy BI Query가 Online에 영향?
```

---

# 93. NFR Checklist — Availability

```text
[ ] Failure Domain 식별?
[ ] Critical Logical Node 식별?
[ ] DR Counterpart?
[ ] Shared State 확인?
[ ] External dependency failure route?
```

---

# 94. NFR Checklist — Scalability

```text
[ ] 독립 Scale 가능한 Node?
[ ] Workload별 Node 분리?
[ ] Shared DB bottleneck?
[ ] Event/Batch 확장경로?
```

---

# 95. NFR Checklist — Security

```text
[ ] Zone Trust Boundary?
[ ] Revalidation?
[ ] Cross-domain Data Access?
[ ] External Integration Security?
[ ] Environment Isolation?
```

---

# 96. NFR Checklist — Observability

```text
[ ] GUID/ServiceId 경계 유지?
[ ] Logical Node별 Metric?
[ ] Interface Correlation?
[ ] Failure Source 추적?
[ ] OM과 Business Runtime 분리?
```

---

# 97. CONFIRMED / WORKING BASELINE

```text
[CONFIRMED / WORKING BASELINE]
- 6 Zone 구조
- Channel → Channel Integration → Service → Internal Integration 경로
- 서비스 제공 Zone의 5대 Logical System 책임
- RD/AD 논리 역할 분리
- Client → Service → Interface → Data → Delivery Layer
- Environment 5/5/3/3 패턴 자료 존재
- PDMG는 Z5 Information Application Reference
- Module ≠ Process ≠ Logical Node
```

---

# 98. OPEN

```text
[OPEN-LG-01]
Zone 최신 공식 Inventory / Owner

[OPEN-LG-02]
Application↔Logical System 전수 Mapping

[OPEN-LG-03]
Logical Node 최신 승인 목록

[OPEN-LG-04]
DR/Pilot 정확한 System Scope

[OPEN-LG-05]
PDMG Target Logical Node 공식 매핑

[OPEN-LG-06]
Temporary Migration Node 종료 기준

[OPEN-LG-07]
Logical Connection Matrix 전수 승인
```

---

# 99. GAP Register

| ID | GAP | 영향 |
|---|---|---|
| GAP-LG-01 | Zone SSOT 최신화 필요 | Boundary |
| GAP-LG-02 | Logical System Inventory 최신화 필요 | System |
| GAP-LG-03 | Logical Node 전수 식별 미완료 | Physical Handoff |
| GAP-LG-04 | Application→System→Node Mapping 미완료 | Traceability |
| GAP-LG-05 | Connection Matrix 전수 미완료 | Interface |
| GAP-LG-06 | Data Ownership Matrix 미완료 | Data |
| GAP-LG-07 | Environment 5/5/3/3 최신 승인 확인 필요 | Environment |
| GAP-LG-08 | DR Scope 최신 승인 필요 | DR |
| GAP-LG-09 | PDMG Logical Target Mapping 미완료 | Reference |
| GAP-LG-10 | Technology Component Inventory 미완료 | Component |
| GAP-LG-11 | Cross-boundary Security Rule 미완료 | Security |
| GAP-LG-12 | Cross-node Trace 검증 미완료 | Observability |

---

# 100. RISK Register

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-LG-01 | Zone을 네트워크 구간으로만 해석 | High |
| RISK-LG-02 | Logical System을 Server로 해석 | High |
| RISK-LG-03 | Module을 Remote Node로 해석 | High |
| RISK-LG-04 | Logical 단계에서 제품/Version 조기확정 | Medium/High |
| RISK-LG-05 | Application간 DAO/DB 직접접근 | Critical |
| RISK-LG-06 | Online/Event/Batch 자원 혼재 | High |
| RISK-LG-07 | RDW/ADW 논리 역할 혼합 | High |
| RISK-LG-08 | Environment 경계 약화 | Critical |
| RISK-LG-09 | PDMG를 전사 Logical Architecture로 과대해석 | High |
| RISK-LG-10 | Trace Key가 Node 경계에서 손실 | High |

---

# 101. ADR 후보

```text
ADR-LG-01 Zone SSOT / Owner
ADR-LG-02 Logical System Baseline
ADR-LG-03 Logical Node Identification
ADR-LG-04 Application→Node Mapping
ADR-LG-05 Allowed Connection Matrix
ADR-LG-06 Cross-domain Data Access
ADR-LG-07 Environment Scope
ADR-LG-08 DR Logical Scope
ADR-LG-09 PDMG Logical Position
ADR-LG-10 Technology Component Standard
ADR-LG-11 FAST / DEEP Logical Isolation
ADR-LG-12 Cross-zone Trace Standard
```

---

# 102. PHYSICAL Handoff

## FIG-LG-76. LOGICAL → PHYSICAL

```text
LOGICAL Output
│
├─ Zone
├─ Logical System
├─ Logical Node
├─ Runtime Type
├─ Layer
├─ Data Role
├─ Connection
├─ Environment Scope
├─ Failure Domain
└─ Scale Characteristic
        │
        ▼
PHYSICAL Input
│
├─ Center
├─ Network
├─ GSLB / L4
├─ WEB
├─ WAS
├─ JVM
├─ Host / VM
├─ CPU / Memory
├─ DB / Appliance
├─ Port
├─ Storage
├─ HA / DR
└─ Capacity
```

---

# 103. LOGICAL → PHYSICAL Mapping Example

## FIG-LG-77. Node to Physical Mapping

```text
Logical Node
Marketing Online Service
        │
        ▼
Physical Group
Marketing WAS
        │
        ├─ Host #1
        └─ Host #2
        │
        ▼
Tomcat JVM / WAR / Port / Capacity
```

### 핵심

```text
LOGICAL은 "왜 분리?"
PHYSICAL은 "어떻게 배치?"
```

---

# 104. PHYSICAL에서 반드시 답할 질문

```text
1. 각 Logical Node는 어느 Center/Host에 배치되는가?
2. WEB/WAS/JVM/WAR 경계는 어떻게 구성되는가?
3. CPU/Memory/Thread/Hikari Capacity는 얼마인가?
4. RDW/ADW는 어떤 Data Appliance/DB에 매핑되는가?
5. HA Pair는 어떻게 구성되는가?
6. DR Pair는 어떻게 구성되는가?
7. GSLB/L4/Network Path는 무엇인가?
8. PDMG의 실제 Host/JVM/WAR/Port는 무엇인가?
9. Logical Failure Domain이 Physical에서도 유지되는가?
```

---

# 105. LOGICAL 최종 통합 지도

## FIG-LG-78. Logical Summary

```text
CHANNEL
Z1 / Z2 / Z3
      │
      ▼
Z4 CHANNEL INTEGRATION
      │
      ▼
Z5 SERVICE DELIVERY
      │
      ├─ Marketing
      ├─ Data Platform
      │   ├─ RD
      │   └─ AD
      ├─ BI
      ├─ Governance
      └─ IT Service
      │
      ▼
Client → Service → Interface → Data → Delivery
      │
      ├────────► Owned Data
      │
      └────────► Z6 Internal Integration
                       │
                       ▼
                  Core / Related
      │
      ▼
ENVIRONMENT
Prod / Dev / DR / Pilot / Migration
      │
      ▼
PDMG REFERENCE
Module / Framework / Business / Runtime
      │
      ▼
PHYSICAL
Host / JVM / DB / Network / HA / Capacity
```

---

# 106. Definition of Done

## 106.1 Zone

- [x] 6 Zone을 Top-down으로 시각화
- [x] Zone을 VLAN/Subnet과 구분
- [x] 각 Zone의 책임과 진입 의미 정의
- [x] 표준 요청 경로 정의
- [x] 허용/금지 Path 정의

## 106.2 System / Node

- [x] Logical System과 Physical Server 구분
- [x] Z5의 5대 Logical System 정의
- [x] Logical Node 개념/필수속성 정의
- [x] Module/Process/Node 차이 정의

## 106.3 Component / Layer

- [x] Client/Service/Interface/Data/Delivery 5 Layer
- [x] VM Node / Data Appliance Pattern
- [x] MP/Data/BI/DG/IM Layered View
- [x] Data Ownership Rule 정의

## 106.4 Connection

- [x] Connection Matrix 구조 정의
- [x] Interface Boundary 정의
- [x] Cross-domain Direct Access 금지
- [x] Security/Observability Boundary 정의

## 106.5 Environment

- [x] Production/Development/DR/Pilot/Migration 구분
- [x] 5/5/3/3 패턴 자료를 Working Baseline으로 표현
- [x] DR Logical Rule 정의
- [x] Temporary Migration을 Target과 구분

## 106.6 PDMG

- [x] PDMG를 Z5 Reference로 배치
- [x] pdmg-fw를 별도 Logical Node로 자동 해석하지 않음
- [x] Framework/Business Layer 관계 시각화
- [x] Physical Mapping은 다음 장으로 이관

## 106.7 Governance

- [x] CONFIRMED / OPEN / GAP / RISK 분리
- [x] ADR 후보 작성
- [x] PHYSICAL Handoff 정의

**LOGICAL 장 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. Zone / Logical System / Node 최신 승인 Inventory 확보
2. Application→System→Node 전수 Mapping
3. Logical Connection Matrix 승인
4. Data Ownership Matrix 승인
5. Environment/DR Scope 최신 승인
6. PDMG Target Logical Node 공식 Mapping
7. Cross-zone Security/Trace 검증

---

# 107. 다음 장

다음은 **04. PHYSICAL — Center / Network / WEB / WAS / JVM / DB / HA / DR / Capacity**다.

다음 장은 LOGICAL의:

```text
Zone
→ Logical System
→ Logical Node
→ Failure Domain
→ Runtime Type
```

을 다음처럼 실제 자원으로 내려간다.

```text
Center
→ Network
→ GSLB/L4
→ WEB
→ WAS
→ JVM
→ WAR
→ CPU/Memory
→ Thread/Pool
→ DB/Storage
→ HA/DR
```

즉,

> **LOGICAL이 “어떤 책임을 어떤 실행역할로 나눌 것인가”를 결정했다면, PHYSICAL은 “그 역할을 실제 어디에, 몇 개, 어떤 자원으로 배치할 것인가”를 결정한다.**
