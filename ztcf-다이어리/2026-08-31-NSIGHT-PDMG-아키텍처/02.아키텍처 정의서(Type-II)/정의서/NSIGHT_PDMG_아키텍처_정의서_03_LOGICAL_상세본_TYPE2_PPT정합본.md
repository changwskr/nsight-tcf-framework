# NSIGHT / PDMG 아키텍처 정의서
# 03. LOGICAL — 논리 기술 아키텍처 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-TYPE2-LOGICAL-03-DETAIL`  
> Architecture Level: **LOGICAL**  
> PPT 공식 범위: **2. 논리 기술 아키텍처**  
> PPT 공식 절: **2.1 전사 IT Zone 기반 구성 기준 / 2.2 시스템 노드 정의 및 식별 / 2.3 기술 컴포넌트 정의 / 2.4 논리 기술 아키텍처 정의**  
> Cross Reference: **6. 인터페이스 아키텍처의 Logical Boundary View**  
> 문서 상태: **Draft / Evidence-First / PPT-Aligned / Detailed Baseline**  
> 작성 기준일: **2026-08-31**  
> 선행 문서: `02. BIG PICTURE — Application / Data / System Boundary 상세본`  
> 후속 문서: `04. PHYSICAL — 물리 인프라 / DB / 시스템 표준`  
> 핵심 질문: **BIG PICTURE에서 정의한 Application·System·Data 책임을 어느 Zone·Logical System·Node·Layer에 배치하고, 어떤 논리 연결을 허용하거나 금지할 것인가?**

---

# 0. 문서 사용법

LOGICAL은 Hostname, CPU, Port를 정하는 단계가 아니다.

이 장에서는 다음 순서를 고정한다.

```text
BIG PICTURE
Domain / Application / System Group / Data Subject
        ↓
LOGICAL
Zone
        ↓
Logical System
        ↓
Logical Node
        ↓
Technology Component
        ↓
Layer
        ↓
Allowed / Forbidden Path
        ↓
Environment Scope
        ↓
PHYSICAL Handoff
```

가장 중요한 구분:

```text
Zone
≠ VLAN / Subnet

Logical System
≠ Physical Server

Logical Node
≠ Hostname

Technology Component
≠ Product Inventory

Logical Interface Boundary
≠ Interface Protocol/Contract
```

---

# 0.1 Evidence 태그

| 태그 | 의미 |
|---|---|
| `[PPT-TOC]` | PPT 공식 목차 |
| `[PPT-BODY]` | PPT 본문 직접 확인 |
| `[NUMBERING-DRIFT]` | PPT 목차/본문 번호·명칭 불일치 |
| `[PPT-CONTENT-GAP]` | PPT 상세 부족 |
| `[FACT]` | 공식 기준에서 직접 확인 |
| `[WORKING BASELINE]` | 반복 사용 중인 프로젝트 기준 |
| `[AS-IS]` | 현 구현 |
| `[TO-BE]` | 목표 |
| `[GAP]` | 목표/구현/자료 차이 |
| `[OPEN]` | 추가 결정 필요 |
| `[UNKNOWN]` | 현재 근거 부족 |
| `[RISK]` | 위험 |
| `[ADR]` | Architecture Decision 필요 |

---

# 0.2 Evidence Register

| ID | 근거 | 사용 목적 | 상태 |
|---|---|---|---|
| EV-LG-01 | TYPE2/PPT 정합 프롬프트 | 2.1~2.4 공식 구조, Numbering Drift, 도메인별 View | `[WORKING BASELINE]` |
| EV-LG-02 | `02_논리기술아키텍처_정의서.md` | 6 Zone, 표준 경로, 환경 5/5/3/3, 레이어드, 허용/금지, Part B 규범 | `[FACT/WORKING BASELINE]` |
| EV-LG-03 | `02 BIG PICTURE 상세본` | MP/RD/AD/BI/DG/IM, System Group, Logical Candidate | `[CURRENT BASELINE DRAFT]` |
| EV-LG-04 | `NSIGHT_아키텍처_정의서_도형TEXT_표도형교체_시스템영역구성_1차.pptx` | DR·이행 임시 환경 System/Node 실제 장표 | `[PPT-BODY]` |
| EV-LG-05 | PDMG Module/Application Architecture | Framework/Business 논리 경계 및 동일 JVM 가능성 | `[AS-IS REFERENCE]` |
| EV-LG-06 | Interface 정의자료 | Zone Boundary와 Interface Logical Boundary 교차검증 | `[CROSS-REFERENCE]` |

---

# 1. PPT 공식 구조

[PPT-TOC]

```text
2. 논리 기술 아키텍처
   2.1 전사 IT Zone 기반 구성 기준
   2.2 시스템 노드 정의 및 식별
   2.3 기술 컴포넌트 정의
   2.4 논리 기술 아키텍처 정의
```

---

# 1.1 Numbering Drift

PPT 목차:

```text
2.2 시스템 노드 정의 및 식별
```

PPT 본문:

```text
2.2 시스템 영역 및 구성요소 정의
```

따라서:

```text
[PPT-TOC]
2.2 시스템 노드 정의 및 식별

[PPT-BODY]
2.2 시스템 영역 및 구성요소 정의

[NUMBERING-DRIFT]
둘을 병기
```

임의로 하나를 삭제하거나 재번호화하지 않는다.

---

# 2. 핵심 결론

LOGICAL의 핵심은 다음이다.

> **Host를 정하기 전에 Zone·Logical System·Logical Node·Layer와 책임 경계를 고정하고, 신규/변경 구성요소가 어느 가지에 속하는지를 먼저 결정한다. 가지에 들어가지 않는 구성은 GAP/ADR 대상이다.**

논리구조의 Baseline:

```text
6 Zone
+
표준 요청 경로
+
서비스 제공의 5대 논리 시스템
+
RD/AD 역할 분리
+
환경별 5 / 5 / 3 / 3
+
Client → Service → I/F → Data → (형상·배포)
+
VM / Appliance Component Pattern
+
자기 소유 Data 직접접근 원칙
+
경계 통과 재검증
```

---

# 3. BIG PICTURE Handoff 수신

BIG PICTURE Output:

```text
Application Group
Business Classification
System Group
Data Subject
Enterprise Context
System Boundary
Logical Server Candidate
```

LOGICAL에서 이를 다음으로 바꾼다.

```text
Application
   ↓
Zone
   ↓
Logical System
   ↓
Logical Node
   ↓
Technology Component
   ↓
Layer
   ↓
Allowed Connection
```

---

# 4. LOGICAL 전체 Text Architecture

```text
┌────────────────────────────── Channel Zone ────────────────────────────────┐
│                                                                           │
│ [대내 채널]          [대고객 채널]          [대외 채널]                   │
│ 통합업무/정보포털     Web/Mobile             기관/계열                     │
│      │                    │                     │                         │
└──────┼────────────────────┼─────────────────────┼─────────────────────────┘
       └────────────────────┼─────────────────────┘
                            ▼
┌──────────────────────────── 채널 통합 ────────────────────────────────────┐
│ MCA / MCI / 대외 중계 / 인증·프로토콜 변환                              │
└────────────────────────────┬──────────────────────────────────────────────┘
                             │ Contracted Request
                             ▼
┌──────────────────────────── 서비스 제공 Zone ─────────────────────────────┐
│                                                                           │
│  [MP] Marketing Platform                                                  │
│  [DP] Data Platform = RD + AD                                             │
│  [BI] BI Portal                                                           │
│  [DG] Data Governance                                                     │
│  [IM] IT Service & Business Support                                       │
│                                                                           │
│  Client → Service → I/F → Data → (형상·배포)                              │
│                                                                           │
└────────────────────────────┬──────────────────────────────────────────────┘
                             │ 필요 시 승인된 Internal Integration
                             ▼
┌──────────────────────────── 대내 통합 ────────────────────────────────────┐
│ API / EAI / FOS / MFT / 내부 라우팅                                      │
└────────────────────────────┬──────────────────────────────────────────────┘
                             ▼
                    [Core / Internal / Related]
```

---

# 5. 2.1 전사 IT Zone 기반 구성 기준

## 5.1 Zone의 정의

Zone은 다음을 묶는 **논리 책임 경계**다.

```text
Who enters?
What responsibility?
What trust level?
What interface?
What failure boundary?
```

Zone은 다음과 같지 않다.

```text
Zone ≠ Network VLAN
Zone ≠ Organization
Zone ≠ Product
Zone ≠ Single Server
```

---

# 5.2 6 Zone

| # | Zone | 책임 | 대표 구성 |
|---:|---|---|---|
| 1 | 대내 채널 | 내부 사용자 고객/정보 업무 접근 | 통합업무, 정보포털 |
| 2 | 대고객 채널 | 고객 요청 유입 | 인터넷/스마트/콕뱅킹 등 |
| 3 | 대외 채널 | 외부기관/계열 연계 유입 | 공공·금융·계열 |
| 4 | 채널 통합 | 접속·인증·프로토콜·통합·중계 | MCA/MCI/대외 MCA |
| 5 | 서비스 제공 | 업무로직·데이터처리·저장·서비스 | MP/DP/BI/DG/IM |
| 6 | 대내 통합 | 내부 IF·라우팅·파일·연계 운영 | APIM/EAI/FOS/MFT |

---

# 5.3 Zone 1 — 대내 채널

역할:

```text
Internal User Entry
```

포함:

```text
통합업무시스템
정보포털
정보계 단말
```

원칙:

```text
사용자 접점
≠ Business DB Owner
```

금지:

```text
대내 채널 → 서비스 DB 직접 접근
대내 채널 → 계정 Core DB 직접 SQL
```

---

# 5.4 Zone 2 — 대고객 채널

역할:

```text
Customer Digital Entry
```

예:

```text
Internet Banking
Smart Banking
Mobile
```

논리적으로 요구:

```text
Authentication
Channel Context
Trace
Standard Entry
```

Business Logic의 본체를 채널에 소유시키지 않는다.

---

# 5.5 Zone 3 — 대외 채널

역할:

```text
External Institution Entry
```

특성:

```text
기관 인증
Network Trust Boundary
Protocol Variation
External SLA
Reconciliation
```

외부기관은 내부 DB에 직접 연결하지 않는다.

---

# 5.6 Zone 4 — 채널 통합

역할:

```text
Connection
Authentication Federation
Protocol Conversion
Routing
Channel Mediation
```

대표:

```text
MCA
MCI
대외 MCA
```

채널 통합은 업무 원장의 Owner가 아니다.

금지:

```text
Channel Integration
→ 업무 Rule 과다 구현
→ Business DB 소유
```

---

# 5.7 Zone 5 — 서비스 제공

NSIGHT의 핵심 시스템 공간.

```text
Service Provision
├─ MP Marketing
├─ DP Data Platform
├─ BI
├─ DG
└─ IM
```

이 Zone에서:

```text
Business Use Case
Data Processing
Information Provision
Analytics
Governance
Support
```

를 책임별로 분리한다.

---

# 5.8 Zone 6 — 대내 통합

역할:

```text
Internal Interface
Routing
File
Integration Operations
```

대표:

```text
APIM
EAI
FOS
MFT
```

주의:

```text
API Gateway / EAI = Zone Name
X

API Gateway / EAI = Zone 6 Component
O
```

---

# 6. 표준 요청 경로

shall:

```text
[대내/대고객/대외 채널]
        ↓
[채널 통합]
        ↓
[서비스 제공]
        ↓ 필요 시
[대내 통합]
        ↓
[내부/유관/Core]
```

응답은 동일 Boundary를 역방향으로 통과한다.

---

# 6.1 허용 Path

```text
Channel
→ Channel Integration

Channel Integration
→ Service Provision

Service Provision
↔ Internal Integration

Service Provision
→ Own Data
```

---

# 6.2 금지 Path

```text
Channel
-X→ Service DB

Channel
-X→ Core DB

External
-X→ Service DB

Business A
-X→ Business B DAO

Business A
-X→ Business B DB

Gateway/EAI
-X→ Business Logic Owner

Online AP
-X→ Large ETL Execution
```

---

# 6.3 Boundary 재검증

Zone 경계 통과 시 수신측은 최소 다음을 재검증해야 한다.

```text
Service / Interface ID
Sender
Authorization
Schema
GUID / Correlation
Request Context
```

실제 Header 필드·Signature·Timeout은 MECHANISM으로 위임한다.

---

# 7. Zone Decision Tree

신규 구성요소가 생기면 다음 순서로 판단한다.

```text
사용자/기관 Entry인가?
 ├─ 내부 → 대내 채널
 ├─ 고객 → 대고객 채널
 └─ 외부 → 대외 채널

중계/인증/프로토콜 책임인가?
 → 채널 통합

업무/정보/Data Processing인가?
 → 서비스 제공

내부 System Integration인가?
 → 대내 통합
```

어느 가지에도 들어가지 않으면:

```text
[GAP]
→ Architecture Review
→ ADR
```

---

# 8. 2.2 시스템 노드 정의 및 식별

[PPT-TOC]

```text
시스템 노드 정의 및 식별
```

[PPT-BODY]

```text
시스템 영역 및 구성요소 정의
```

본 장에서는 다음 계층으로 해석한다.

```text
Zone
  ↓
Logical System
  ↓
Logical Node
  ↓
Technology Component
```

---

# 9. 서비스 제공 Zone의 논리 시스템

```text
Service Provision Zone
│
├─ Marketing Platform
├─ Data Platform
├─ BI Portal
├─ Data Governance
└─ IT Service & Business Support
```

Data Platform:

```text
Data Platform
├─ RD / RDW
└─ AD / ADW
```

여기서:

```text
System = Data Platform 1개 책임공간
Code/Role = RD/AD 분리
```

로 보는 기존 Logical Baseline을 유지한다.

---

# 10. Logical System과 Application Group 구분

```text
Application Group
= 업무 분류

Logical System
= 실행·운영 책임 공간
```

예:

```text
MP-IC / MP-PC / MP-BC
      ↓
Marketing Platform System
```

---

# 11. Logical Node 정의

Logical Node는 특정 Host가 아니다.

정의:

```text
Logical Node
=
독립된 기술 역할
+
명확한 입력/출력
+
책임/확장/장애 경계 후보
```

대표 유형:

```text
WEB
WAS
Business AP
Event AP
CDC
ETL
Batch
RDW
ADW
BI
Governance
Integration
Monitoring
```

---

# 12. Logical Node 필수 속성

| 필드 | 의미 |
|---|---|
| Node ID | 논리 식별자 |
| System | 소속 논리 시스템 |
| Zone | 6 Zone |
| Environment | 운영/개발/DR/선도 |
| Role | WEB/WAS/AP/DB 등 |
| Responsibility | 기술 책임 |
| Layer | Client/Service/I-F/Data |
| Data Ownership | 소유/접근 데이터 |
| Source/Target | 주요 연결 |
| Runtime Type | 대표 실행유형 |
| Physical Mapping | 후속 |
| Evidence | 근거 |
| Status | 상태 |

---

# 13. 환경별 구축 범위

기존 Logical Baseline:

```text
운영 = 5체계
개발 = 5체계
DR   = 즉시기동 3체계
선도 = 3체계
```

---

# 13.1 운영환경

```text
운영
├─ Marketing
├─ Data Platform
├─ BI
├─ Data Governance
└─ IT Support
```

추가:

```text
이행용 임시 시스템
```

은 존재 가능하지만 **상시 5체계 수에 포함하지 않는다.**

---

# 13.2 개발환경

```text
개발 = 운영과 동일 5체계
```

그러나 다음은 격리한다.

```text
Data
Account
External Connection
Secret
Endpoint
```

개발이 운영과 동일 Logical System이라고 해서 운영 Data/External을 그대로 공유하지 않는다.

---

# 13.3 DR환경

[WORKING BASELINE]

```text
DR 즉시기동 3체계

1. Marketing Platform
2. Data Platform
3. IT Service & Support
```

BI/DG는 **즉시기동 대상에서 제외**된 Baseline이 존재한다.

주의:

```text
즉시기동 제외
≠ Backup 없음
≠ 복구하지 않음
```

후순위 복구/RTO 차등으로 해석한다.

---

# 13.4 DR 실제 PPT 구성

PPT Body에서 확인되는 대표 구성:

```text
Marketing Platform
├─ Marketing WEB
├─ Marketing WAS
├─ Mini Single View WEB
├─ Mini Single View WAS
└─ Marketing DB

Data Platform
└─ RDW Appliance

IT Support
├─ Terminal Management WEB/WAS
└─ Terminal Deployment WEB/WAS
```

정확한 Host/대수는 PHYSICAL로 넘긴다.

---

# 13.5 선도환경

[WORKING BASELINE]

```text
선도 3체계

Marketing
Data Platform
IT Support
```

ADW 등 일부 범위 축소 가능.

정확한 축소 범위는 `[OPEN]`.

---

# 13.6 환경 Matrix

| System | 운영 | 개발 | DR | 선도 |
|---|---:|---:|---:|---:|
| Marketing | Y | Y | Y | Y |
| Data Platform | Y | Y | Y | Y |
| BI | Y | Y | 후순위 | 축소/제외 가능 |
| DG | Y | Y | 후순위 | 축소/제외 가능 |
| IT Support | Y | Y | Y | Y |
| Migration Temporary | 필요 시 | 필요 시 | 조건부 | 조건부 |

---

# 14. 이행용 임시 구성

PPT Body에서 확인되는 예:

```text
Temporary Migration
├─ Data Migration Transform AP
├─ Data Migration Extract Server
├─ Data Migration Transfer AP
└─ SQL Quality AP
```

원칙:

```text
Temporary
≠ Permanent Architecture
```

필수 관리:

```text
Purpose
Start Date
End Date
Data Access
Network Access
Owner
Decommission Plan
```

---

# 15. 2.3 기술 컴포넌트 정의

PPT Body 순서:

```text
공통 기술 컴포넌트
마케팅플랫폼 (1/3~3/3)
데이터플랫폼
BI 포탈 (1/4~4/4)
데이터거버넌스
IT서비스 및 업무지원 (1/3~3/3)
기타 기술 컴포넌트
```

---

# 16. Technology Component 정의

Technology Component는 다음 식으로 정의한다.

```text
Logical Responsibility
       ↓
Technology Capability
       ↓
Logical Node
       ↓
Physical Deployment Candidate
```

제품명이 먼저가 아니다.

---

# 17. 공통 기술 컴포넌트

Cross-cutting Capability:

```text
Security
Monitoring
Backup
Account
OS Operation
Configuration
Deployment
Logging
Network Control
```

기존 정의서는 횡단 공통 개념으로 다음을 제시한다.

```text
백신
계정
보안
개인정보
백업
관제
모니터링
서버운영
```

---

# 18. VM Node Component Pattern

기존 Logical Definition Pattern:

```text
Solution
   ↓
Stack
   ↓
JVM
   ↓
Cross-cutting Control
   ↓
OS
   ↓
VM
   ↓
Cloud / Infrastructure
```

해석:

```text
Business/Application Role
→ Runtime Stack
→ JVM
→ OS/VM
```

은 PHYSICAL에서 실제 Host로 매핑된다.

---

# 19. DB Appliance Component Pattern

```text
Business Data
   ↓
Data Stack
   ↓
DBMS
   ↓
Cross-cutting Control
   ↓
OS
   ↓
DB Appliance
```

RDW/ADW가 대표적이다.

RAC/Node/Exadata 상세는 PHYSICAL로 위임한다.

---

# 20. Layered Architecture 공통 골격

```text
Client Access
    ↓
Service
    ↓
Interface
    ↓
Data
    ↓
(Configuration / Delivery)
```

기존 Baseline:

```text
Client → Service → I/F → Data → (형상·배포)
```

---

# 21. Client Layer

책임:

```text
Presentation
User Interaction
Request Creation
Channel Context
```

대표:

```text
Terminal
Browser
Package UI
```

금지:

```text
Business DB Access
Business SQL
Core Data Direct Manipulation
```

---

# 22. Service Layer

책임:

```text
Business Use Case
Application Service
Domain/Business Processing
Event Processing
Analytics Service
```

예:

```text
Marketing WEB/WAS
Mini Single View
EBM
Behavior Processing
BI Service
```

---

# 23. Interface Layer

책임:

```text
Boundary Crossing
Contracted Integration
```

상위 유형:

```text
Online
Event
File
Data
```

LOGICAL에서는:

```text
Source/Target Boundary
```

만 고정한다.

Protocol/Schema/Retry는 MECHANISM.

---

# 24. Data Layer

책임:

```text
Owned Data
Operational Data
Analytical Data
Metadata
```

핵심:

```text
RDW = 준실시간 / 운영성
ADW = 분석 / 대량
DG Store = Metadata/Quality/Lineage
```

---

# 25. Configuration / Delivery Layer

Cross-cutting:

```text
Source Management
Build
Deployment
Configuration
Operations
```

BIG PICTURE IM 책임과 연결된다.

---

# 26. 데이터 소유 원칙

기존 Logical Definition 핵심:

```text
논리 시스템
→ 자기 소유 Data만 직접 접근
```

타 시스템 Data:

```text
Approved Interface
or
Approved Data Access Contract
```

을 사용한다.

---

# 26.1 RDW / ADW Data Role

```text
RDW
= Near Real-time / Operational

ADW
= Analytics / Bulk
```

금지:

```text
RDW와 ADW 역할 통합
```

---

# 26.2 Governance Data Role

```text
DG
= Metadata / Quality / Lineage
```

DG는:

```text
업무 원장
마트
대량 ETL 처리자
```

를 대체하지 않는다.

---

# 27. 마케팅플랫폼 Layered Architecture

```text
[Client]
Information Terminal / Web / Channel
        ↓
[Service]
Marketing WEB/WAS
Mini Single View
Campaign / EBM
Real-time / Behavior
        ↓
[I/F]
MCA / API / Event / File / Approved Data Access
        ↓
[Data]
Marketing Data
RDW Reference
ADW Reference
        ↓
[Ops]
Framework / Deployment / Monitoring
```

---

# 27.1 MP Logical Node 후보

```text
MP-WEB
MP-WAS
MS-WEB
MS-WAS
EP-AP
BP-AP
BD-AP
EBM
Messaging
```

각 Node의 실제 Host 개수는 후속.

---

# 27.2 MP Workload 분리

```text
Online
├─ Marketing WAS
└─ Mini Single View

Event
├─ Real-time AP
├─ Behavior AP
└─ Behavior Data AP

Campaign
└─ EBM
```

Online과 Event를 동일 Thread/자원으로 무제한 혼재하지 않는다.

---

# 28. 데이터플랫폼 Layered Architecture

```text
[Source]
Core / Related
      │
      ├─ CDC
      ▼
     RDW
      │
      ├─ Operational Query
      └─ ETL
          ▼
         ADW
          │
          └─ BI / Analytics
```

---

# 28.1 Data Platform Logical Node

```text
CDC Capture / Relay
RDW
ETL
ADW
File Transfer
Data Integration
```

---

# 28.2 Data Platform Responsibility

```text
CDC
= Change Propagation

ETL
= Bulk/Transform

RDW
= Operational/Near RT

ADW
= Analytical
```

---

# 29. BI Portal Layered Architecture

```text
[Client]
BI User / Analyst
     ↓
[Service]
BI Portal
Credit Performance
OLAP
Self-BI
     ↓
[I/F/Data Access]
Approved Query / Service
     ↓
[Data]
RDW / ADW / BI Store
```

---

# 29.1 BI 금지

```text
BI
-X→ Core DB 대량 Direct Query
```

기본 분석 대상은 정보계 Data Platform이다.

---

# 30. Data Governance Layered Architecture

```text
[Client]
Governance User
    ↓
[Service]
Business Metadata
Data Quality
Data Flow / Lineage
    ↓
[I/F]
Metadata Collection / Quality Interface
    ↓
[Data]
Governance Repository
```

---

# 31. IT Service & Support Logical Architecture

독립 2.4 상세 장표가 충분하지 않을 수 있으므로 `[PPT-CONTENT-GAP]`을 유지한다.

근거가 있는 상위 책임:

```text
Architecture Management
Framework
Library
SCM
Deployment
Terminal Management
Batch
Real-time Relay
Data Support
Report Designer
Monitoring
```

일반론으로 새로운 Tool/Product를 채우지 않는다.

---

# 32. 기술 컴포넌트 Inventory

| Field | Description |
|---|---|
| Component ID | 논리 식별 |
| Domain/System | MP/DP/BI/DG/IM |
| Zone | 소속 Zone |
| Logical Node | 배치 Node |
| Layer | Client/Service/I-F/Data |
| Capability | 기술역할 |
| Product/SW | 근거 있을 때 |
| Source | 입력 |
| Target | 출력 |
| Data | 소유/참조 |
| Runtime Type | 대표 실행유형 |
| Physical Mapping | 후속 |
| Evidence | 근거 |
| Status | 상태 |

---

# 33. Logical Node Inventory Template

| Node | System | Zone | Layer | Role | Environment | Data | Runtime | Status |
|---|---|---|---|---|---|---|---|---|
| MP-WEB | MP | 서비스 제공 | Client/Service | Web Entry | 운영/개발/DR/선도 | - | Online | Baseline |
| MP-WAS | MP | 서비스 제공 | Service | Business | 운영/개발/DR/선도 | RDW 등 | Online | Baseline |
| EP-AP | MP | 서비스 제공 | Service | Real-time | 운영/개발/DR/선도 | MP/RD | Event | Baseline |
| CDC | DP | 서비스 제공 | I/F/Data | CDC | 운영/개발/DR | RDW | CDC | Baseline |
| RDW | DP | 서비스 제공 | Data | Operational Data | 운영/개발/DR | RD | Data | Baseline |
| ETL | DP | 서비스 제공 | I/F/Data | Bulk Transform | 운영/개발 | RD/AD | ETL | Baseline |
| ADW | DP | 서비스 제공 | Data | Analytics Data | 운영/개발 | AD | Analytics | Baseline |
| BI | BI | 서비스 제공 | Service | BI | 운영/개발 | RD/AD | Analysis | Baseline |
| DG | DG | 서비스 제공 | Service/Data | Governance | 운영/개발 | Meta | Governance | Baseline |
| BATCH | IM | 서비스 제공 | Service | Batch | 운영/개발/DR/선도 | Multiple | Batch | Baseline |

---

# 34. 2.4 논리 기술 아키텍처 — 마케팅플랫폼

```text
Channel
  ↓
Channel Integration
  ↓
MP-WEB
  ↓
MP-WAS
  ├─ Customer
  ├─ Sales/Product
  ├─ Campaign
  └─ Common
  │
  ├───────────────→ RDW
  │
  ├───────────────→ Internal Integration
  │
  └───────────────→ Event AP
                        ├─ EP
                        ├─ BP
                        └─ BD
```

---

# 35. 2.4 논리 기술 아키텍처 — 데이터플랫폼

```text
Core / Source
  │
  ├─ CDC
  ▼
RDW
  │
  ├─ Operational Service
  │
  └─ ETL
       ▼
      ADW
       │
       └─ BI / Analysis

File/Bulk
  ── ETL/FOS/MFT ──▶ RDW/ADW
```

---

# 36. 2.4 논리 기술 아키텍처 — BI포탈

```text
BI User
   ↓
BI Entry
   ↓
BI Portal / OLAP / Self-BI
   ↓
Approved Data Access
   ↓
RDW / ADW
```

---

# 37. 2.4 논리 기술 아키텍처 — 데이터거버넌스

```text
Governance User
   ↓
DG Service
   ├─ Biz Metadata
   ├─ Data Quality
   └─ Data Flow
   ↓
Governance Store
   ↓
Metadata / Quality Link
   ↓
Data Platform / Applications
```

---

# 38. Logical Connection Matrix

| Source Zone/System | Target | Allowed | Condition |
|---|---|---:|---|
| Channel | Channel Integration | Y | 인증/표준 Entry |
| Channel Integration | Service Provision | Y | Contract |
| Service Provision | Own Data | Y | Ownership |
| Service Provision | Internal Integration | Y | Registered IF |
| Internal Integration | Core/Related | Y | Contract |
| Channel | Service DB | N | Direct 금지 |
| External | Internal DB | N | Direct 금지 |
| Application A | Application B DAO | N | Layer/Ownership 위반 |
| BI | Core DB 대량 Direct | N | Data Boundary 위반 |

---

# 39. Logical Interface Boundary

LOGICAL:

```text
A System
  ↓
Boundary
  ↓
B System
```

MECHANISM:

```text
API/Event/CDC/ETL/File
Schema
Header
Timeout
Retry
Security
```

따라서 LOGICAL에서 다음을 과도하게 확정하지 않는다.

```text
HTTP Method
JSON Field
Retry Count
Timeout Seconds
Port
```

---

# 40. Security Logical Boundary

```text
Channel Trust
   ↓
Channel Integration
   ↓ Revalidation
Service Trust
   ↓
Internal Integration
   ↓ Revalidation
Core / External
```

Cross-boundary 최소 논리 요구:

```text
Identity
Authorization
Contract
Trace
```

---

# 41. Observability Logical Boundary

모든 주요 Logical Boundary에:

```text
GUID
ServiceId / InterfaceId
Source System
Target System
```

을 유지할 수 있어야 한다.

구현 상세는 MECHANISM/RUNTIME.

---

# 42. NFR → LOGICAL Mapping

| NFR | Logical Decision |
|---|---|
| Performance | FAST/DEEP Node 분리, Online/Event/ETL 책임 분리 |
| Availability | 즉시기동 System 구분, Node Failure Domain |
| Scalability | 독립 Scale 가능한 Logical Node |
| Security | Zone/Trust Boundary |
| Observability | Boundary Trace Point |

---

# 43. FAST / DEEP Logical Mapping

```text
FAST
Customer Event
 → Event Entry
 → Event AP
 → Offer/Reaction

DEEP
Core Change
 → CDC Node
 → RDW
 → ETL
 → ADW
 → BI
```

이 두 Runtime 경로가 같은 Logical Node를 무조건 공유하지 않도록 한다.

---

# 44. PDMG Logical Reference Position

PDMG:

```text
Service Provision Zone
  ↓
Marketing / Business Application
  ↓
MP-WAS Logical Node Reference
  ↓
PDMG
```

내부:

```text
pdmg-service
+ pdmg-fw
```

는 동일 JVM/ApplicationContext에 공존할 수 있다.

따라서:

```text
pdmg-fw
≠ 독립 Logical Remote System
```

---

# 45. Module / Process / Node 구분

```text
Module Boundary
≠ Process Boundary
≠ JVM Boundary
≠ Logical Node Boundary
```

예:

```text
pdmg-service Module
pdmg-fw Module
     ↓
same JVM
     ↓
one Business Logical Node
```

Source Evidence 없이 Framework Module마다 서버 박스를 만들지 않는다.

---

# 46. Environment Isolation Rule

개발/선도는 운영 Logical Structure를 재현하더라도 다음을 분리한다.

```text
Account
Data
External Endpoint
Secret
Batch Schedule
Message Topic/Queue
File Path
```

실제 Config 값은 PHYSICAL/MECHANISM에서 확인.

---

# 47. DR Logical Rule

DR에서 즉시기동 3체계를 기준으로 할 때:

```text
Critical Service
├─ Marketing
├─ Data Platform
└─ IT Support
```

후순위:

```text
BI
DG
```

그러나 정확한 Business Criticality/RTO는 DR ADR로 확정한다.

---

# 48. Logical HA Principle

LOGICAL 단계에서 정의:

```text
어떤 Role이
Single Point of Failure가 되면 안 되는가?
```

PHYSICAL에서 정의:

```text
몇 대
어느 센터
어떤 HA 방식
```

---

# 49. Logical Scalability Principle

독립 확장 후보:

```text
WEB
WAS
Event AP
CDC
ETL
BI
```

Data Appliance는 별도 확장모델.

실제 Scale Unit은 PHYSICAL/Capacity.

---

# 50. Logical Responsibility Matrix

| Domain | Application Responsibility | Data Responsibility | Interface Responsibility |
|---|---|---|---|
| MP | Customer/Marketing | Business state/use | Channel/Internal/Event |
| DP | Data integration/provision | RD/AD | CDC/ETL/Data |
| BI | Analysis/Reporting | BI local/consume | Query/Data service |
| DG | Governance | Metadata/Quality | Metadata collection |
| IM | Common/Ops/Support | Support stores | Batch/Deploy/Relay |

---

# 51. Technology Capability Map

```text
Web Capability
Business Runtime
Event Processing
Change Data Capture
ETL
Operational Data
Analytical Data
BI/Analytics
Governance
Batch
File Transfer
Monitoring
Deployment
```

각 Capability는 하나 이상의 Logical Node에 매핑해야 한다.

---

# 52. Technology Component 배치 원칙

```text
Component
  ↓
Logical Node
  ↓
System
  ↓
Zone
```

역으로:

```text
Product 먼저 선택
  ↓
어디 넣을지 찾음
```

은 지양한다.

---

# 53. VM Node Rule

VM 기반 Node에서는 다음을 구분한다.

```text
Application/Solution
Runtime Stack
JVM
OS
VM
```

하나를 다른 이름으로 대체하지 않는다.

---

# 54. Appliance Rule

DB Appliance:

```text
Data Role
DBMS Role
HA Role
Capacity Role
```

을 먼저 정의한다.

제품 Model/Node는 PHYSICAL.

---

# 55. Direct Data Access Rule

shall:

```text
System
→ Own Data
```

타 System Data:

```text
System
→ Approved IF/Data Service
→ Target Data
```

예외 JDBC가 필요하면:

```text
Source
Target
Read/Write
Account
SLA
Owner
ADR
```

를 명시한다.

---

# 56. Online / Batch Resource Rule

금지:

```text
Online AP
+
Large Batch/ETL
```

무제한 혼재.

이유:

```text
CPU
Thread
Connection
DB Session
I/O
```

경합.

---

# 57. Gateway/EAI Rule

Gateway/EAI:

```text
Routing
Security
Protocol
Integration
```

책임.

금지:

```text
Business Rule
Business DB Ownership
```

---

# 58. Logging/Monitoring Logical Rule

각 Logical Node는 최소:

```text
Health
Transaction
Error
Resource
Dependency
```

관측 포인트를 가질 수 있어야 한다.

실제 Metric은 RUNTIME.

---

# 59. Part B — 준수 규범

## 59.1 신규/변경 Node

모든 신규/변경 Logical Node는:

```text
Zone
System
Layer
Data Ownership
Interface Boundary
```

에 먼저 매핑한 뒤 물리·IF를 확정한다.

---

# 59.2 Architecture Conflict

본 정의서와 충돌하는 설계는:

```text
GAP 등록
→ 영향분석
→ ADR
→ 승인
```

전 적용하지 않는다.

---

# 59.3 Zone Rule

shall:

```text
6 Zone 중 하나에 배치
```

하지 못하면 신규 Zone 필요 여부를 Architecture Review한다.

---

# 59.4 Standard Path Rule

must:

```text
Channel → Channel Integration → Service Provision
```

원칙을 따른다.

Direct 우회는 승인된 예외만 허용.

---

# 59.5 Environment Rule

```text
운영 5
개발 5
DR 3 immediate
선도 3
```

Working Baseline을 따르며 축소/추가는 근거를 남긴다.

---

# 59.6 Layer Rule

호출은 기본:

```text
Client
→ Service
→ Interface/Data
```

역행/우회 금지.

---

# 59.7 Data Ownership Rule

must:

```text
Own Data Direct Access
```

타 Data는 계약을 통해 접근.

---

# 59.8 Physical Premature Decision 금지

금지:

```text
Logical Node 정의 전
Hostname 배정

Logical Role 정의 전
Server Count 확정
```

---

# 60. Architecture Review 질문

신규 구성 시 반드시 답한다.

1. 어느 Zone인가?
2. 어느 Logical System인가?
3. 어느 Layer인가?
4. 어떤 Logical Node인가?
5. 어떤 Capability인가?
6. 어떤 Data를 소유하는가?
7. 타 Data 접근이 필요한가?
8. 어떤 Boundary를 통과하는가?
9. FAST인가 DEEP인가?
10. 어느 환경에 필요한가?
11. DR 즉시기동 대상인가?
12. 독립 Scale이 필요한가?
13. Failure Domain은 무엇인가?
14. Trace Point는 어디인가?
15. Physical로 무엇을 넘길 것인가?

---

# 61. Logical Anti-Pattern

## A. Zone = VLAN

```text
VLAN A
= Zone A
```

로 단정.

금지.

---

## B. System = Server

```text
Marketing System
= marketing01 host
```

금지.

---

## C. Module = Remote System

```text
pdmg-fw
= 별도 FW Server
```

금지.

---

## D. Logical에서 제품버전 확정

근거 없는:

```text
Apache x.x
Tomcat x.x
Oracle x.x
```

기입 금지.

---

## E. DR 미표시 = 불필요

PPT 부재를 근거로 Business 복구 필요성을 삭제하지 않는다.

---

# 62. Logical Inventory SSOT

최종적으로 다음 Inventory를 유지한다.

```text
Zone Inventory
Logical System Inventory
Logical Node Inventory
Technology Component Inventory
Environment Matrix
Connection Matrix
Data Ownership Matrix
```

---

# 63. Zone Inventory Template

| Zone ID | Name | Responsibility | Allowed Source | Allowed Target | Owner | Status |
|---|---|---|---|---|---|---|
| Z01 | 대내 채널 | Internal Access | User | Z04 | TBD | Baseline |
| Z02 | 대고객 채널 | Customer Access | Customer | Z04 | TBD | Baseline |
| Z03 | 대외 채널 | External Access | External | Z04 | TBD | Baseline |
| Z04 | 채널 통합 | Mediation | Z01~03 | Z05 | TBD | Baseline |
| Z05 | 서비스 제공 | Service/Data | Z04 | Z06/Data | TBD | Baseline |
| Z06 | 대내 통합 | Internal IF | Z05 | Internal/Core | TBD | Baseline |

---

# 64. System Inventory Template

| System | Zone | Domain | Environment | Criticality | DR | Owner | Status |
|---|---|---|---|---|---|---|---|
| Marketing | Z05 | MP | O/D/R/P | High | Immediate | TBD | Baseline |
| Data Platform | Z05 | RD/AD | O/D/R/P | High | Immediate | TBD | Baseline |
| BI | Z05 | BI | O/D | Medium | Deferred | TBD | Baseline |
| DG | Z05 | DG | O/D | Medium | Deferred | TBD | Baseline |
| IT Support | Z05 | IM | O/D/R/P | High/Support | Immediate | TBD | Baseline |

O=운영, D=개발, R=DR, P=선도.

---

# 65. Connection Matrix Template

| Source | Target | Relation | Allowed | Mechanism Detail | ADR |
|---|---|---|---:|---|---|
| Channel | Channel Integration | Request | Y | 05 | - |
| Channel Integration | MP | Service | Y | 05 | - |
| MP | RDW | Data Access | Y/Controlled | 05 | 필요 시 |
| MP | Other App DB | Direct | N | - | Exception only |
| BI | ADW | Analysis | Y | 05 | - |
| External | Internal DB | Direct | N | - | Exception only |

---

# 66. Data Ownership Matrix Template

| System | Own Data | Reference Data | Direct Allowed | Contract Required |
|---|---|---|---:|---:|
| MP | MP State | RD/AD | Own only | Other |
| DP-RD | RD | Source | Own | Source ingestion |
| DP-AD | AD | RD | Own | ETL |
| BI | BI local | RD/AD | Local | Data access |
| DG | Meta/Quality | Application/Data | Own | Collection |

---

# 67. NFR Checklist

## Performance

```text
[ ] Online/Event/ETL Node 책임이 분리되는가
[ ] BI 대량부하가 Operational Node를 우회하는가
```

## Availability

```text
[ ] Critical Logical System 식별
[ ] DR immediate/deferred 구분
```

## Scalability

```text
[ ] 독립 Scale Node가 식별되는가
```

## Security

```text
[ ] Trust Boundary가 Zone에 반영되는가
```

## Observability

```text
[ ] Boundary별 Trace Point가 정의되는가
```

---

# 68. GAP Register

| ID | GAP | 영향 | 조치 |
|---|---|---|---|
| GAP-LG-01 | 2.1 독립 PPT 상세 부족 가능 | Zone 근거 | 기존 Logical Baseline 사용 |
| GAP-LG-02 | 2.2 목차/본문 명칭 Drift | Trace | 병기 |
| GAP-LG-03 | IT Support 2.4 상세 부족 | Logical Completeness | 추가 자료 확인 |
| GAP-LG-04 | 선도 ADW 범위 미확정 | 환경설계 | ADR |
| GAP-LG-05 | DR BI/DG 복구순위 상세 | DR | RTO/RPO 설계 |
| GAP-LG-06 | Node별 Owner | 운영책임 | RACI |
| GAP-LG-07 | Logical IF 전수 Connection Matrix | Interface | 05 연계 |
| GAP-LG-08 | PDMG Physical Mapping | Reference | 04에서 확인 |

---

# 69. ADR 후보

| ADR | 주제 |
|---|---|
| ADR-LG-01 | Zone 예외 연결 정책 |
| ADR-LG-02 | DR Immediate System 범위 |
| ADR-LG-03 | 선도환경 Data Platform 범위 |
| ADR-LG-04 | RDW Direct Access 정책 |
| ADR-LG-05 | BI 분석 Access Path |
| ADR-LG-06 | Event AP 독립 Node |
| ADR-LG-07 | Temporary Migration Node 종료 기준 |
| ADR-LG-08 | PDMG Logical Position |

---

# 70. Verification Checklist — 2.1

```text
[ ] 6 Zone이 모두 정의됐는가
[ ] Zone과 VLAN을 구분했는가
[ ] 표준 요청 경로가 존재하는가
[ ] 허용/금지 연결이 있는가
[ ] Gateway/EAI를 Zone으로 부르지 않는가
```

---

# 71. Verification Checklist — 2.2

```text
[ ] Numbering Drift를 병기했는가
[ ] 5대 Logical System을 정의했는가
[ ] 운영5/개발5/DR3/선도3을 표시했는가
[ ] Migration Temporary를 상시5에 포함하지 않았는가
[ ] Logical Node와 Host를 구분했는가
```

---

# 72. Verification Checklist — 2.3

```text
[ ] Component를 Capability 기준으로 정의했는가
[ ] VM Node와 Appliance Pattern을 구분했는가
[ ] Cross-cutting Control이 있는가
[ ] 제품/버전을 근거 없이 넣지 않았는가
[ ] Component Inventory가 Node와 연결되는가
```

---

# 73. Verification Checklist — 2.4

```text
[ ] MP/DP/BI/DG Logical View가 존재하는가
[ ] IM 상세 Gap을 숨기지 않았는가
[ ] Client→Service→I/F→Data Layer가 유지되는가
[ ] RDW/ADW Ownership을 구분했는가
[ ] PDMG를 독립 전사 Logical System으로 확대하지 않았는가
```

---

# 74. Logical Completion Gate

```text
G-LG-01  Zone Baseline
G-LG-02  Standard Path
G-LG-03  Logical System
G-LG-04  Environment Scope
G-LG-05  Logical Node
G-LG-06  Technology Component
G-LG-07  Layer
G-LG-08  Data Ownership
G-LG-09  Connection Matrix
G-LG-10  GAP / ADR
```

---

# 75. PHYSICAL Handoff

LOGICAL Output:

```text
Zone
Logical System
Logical Node
Environment
Technology Capability
Layer
Data Ownership
Connection Rule
Criticality / DR Priority
```

PHYSICAL에서 확정:

```text
Center
Hostname
Server Count
HW
OS
SW
JVM
DB
RAC / OGG
Capacity
Network
Port
HA Pair
DR Pair
Filesystem
Account
```

---

# 76. Logical → Physical 연결

```text
Logical Node
      ↓
Environment
      ↓
Center
      ↓
Host / Host Group
      ↓
HW / OS / SW
      ↓
Runtime / DB / Network
```

---

# 77. 최종 평가

LOGICAL의 완료 상태는 다음과 같다.

```text
신규/변경 구성요소를 보았을 때

어느 Zone인지 알고,
어느 Logical System인지 알고,
어느 Node인지 알고,
어느 Layer인지 알고,
어떤 Data를 소유하는지 알고,
어디와 연결할 수 있는지 알고,
어느 환경에 필요한지 알고,
물리팀에 무엇을 넘겨야 하는지
명확히 설명할 수 있어야 한다.
```

최종 핵심 선언:

> **논리기술아키텍처는 서버 그림이 아니라 책임·경계·환경·레이어·소유권의 규범이다. 모든 물리 배치와 인터페이스 설계는 이 논리 기준에 매핑된 뒤 확정되어야 한다.**

---

# Appendix A. 6 Zone Summary

```text
Z1 대내 채널
Z2 대고객 채널
Z3 대외 채널
Z4 채널 통합
Z5 서비스 제공
Z6 대내 통합
```

---

# Appendix B. Environment Summary

```text
운영 5
Marketing / Data / BI / DG / IM

개발 5
Marketing / Data / BI / DG / IM

DR 3 Immediate
Marketing / Data / IM

선도 3
Marketing / Data / IM
```

---

# Appendix C. Layer Summary

```text
Client
 ↓
Service
 ↓
Interface
 ↓
Data
 ↓
Configuration / Delivery
```

---

# Appendix D. 본 장에서 확정하지 않는 값

```text
Hostname
IP
Port
CPU
Memory
JVM Heap
Tomcat Thread
Hikari Pool
RAC Node Count
Oracle Version
Apache Version
Timeout Seconds
Retry Count
Kafka Partition
RTO/RPO final value
```

이 값은 후속 PHYSICAL / MECHANISM / RUNTIME Evidence로 확정한다.
