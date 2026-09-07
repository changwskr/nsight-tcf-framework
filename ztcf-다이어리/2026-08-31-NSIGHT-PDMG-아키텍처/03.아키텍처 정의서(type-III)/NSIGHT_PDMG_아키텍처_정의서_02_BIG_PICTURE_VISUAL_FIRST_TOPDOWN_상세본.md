# NSIGHT / PDMG 아키텍처 정의서
# 02. BIG PICTURE — Application / Data / System Boundary
## Visual-First / Top-down / Drill-down 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-VISUAL-BIGPICTURE-02`  
> Architecture Level: **BIG PICTURE / L1**  
> 문서 상태: **Draft / Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 작성 원칙: **그림 우선 → 책임 → 경계 → Mapping → Evidence / GAP → LOGICAL Handoff**  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_01_VISION_VISUAL_FIRST_TOPDOWN_상세본.md`  
> 후속 장: **03. LOGICAL**

---

# 0. 이 문서를 읽는 방법

BIG PICTURE는 서버나 Framework를 설명하는 장이 아니다.

```text
VISION에서 받은 방향
   ↓
Domain Responsibility
   ↓
Application Classification
   ↓
System Group
   ↓
Data Subject / Ownership
   ↓
Enterprise Context
   ↓
Boundary / Integration
   ↓
Cross-cutting Security / Observability
   ↓
PDMG Reference Position
   ↓
LOGICAL에서 Node / Connection으로 Drill-down
```

이 장에서 답해야 할 핵심 질문은 다음이다.

```text
1. 어떤 책임 공간이 존재하는가?
2. Application은 어떤 분류 체계를 갖는가?
3. 어떤 System Group이 어떤 책임을 맡는가?
4. RDW / ADW / Governance의 데이터 책임은 어떻게 갈리는가?
5. 사용자·채널·외부시스템은 어디에서 진입하는가?
6. Online / Event / CDC / ETL / File 경계는 어떻게 구분되는가?
7. PDMG는 전체 Architecture의 어디에 위치하는가?
```

---

# 1. VISUAL ROUTE — BIG PICTURE 전체를 한 장으로 보기

## FIG-BP-01. NSIGHT Big Picture Journey

```text
╔══════════════════════════════════════════════════════════════════════════════╗
║                          NSIGHT BIG PICTURE                                 ║
╚══════════════════════════════════════════════════════════════════════════════╝

 [CHANNEL / EXTERNAL]
        │
        ▼
 [ACCESS / ENTRY]
        │
        ▼
 [APPLICATION RESPONSIBILITY]
        │
        ├──────── Marketing Platform
        ├──────── BI Portal
        ├──────── Data Governance
        └──────── IT Service & Business Support
        │
        ▼
 [DATA RESPONSIBILITY]
        │
        ├──────── RDW
        └──────── ADW
        │
        ▼
 [INTEGRATION BOUNDARY]
        │
        ├──────── Online / API / MCA
        ├──────── Event / Kafka
        ├──────── CDC
        ├──────── ETL
        └──────── File / MFT / FOS
        │
        ▼
 [CROSS-CUTTING]
        │
        ├──────── Security
        ├──────── Observability
        ├──────── Standard
        └──────── Operations
        │
        ▼
 [REFERENCE]
        └──────── PDMG AS-IS / Source / Runtime Evidence
```

### 이 그림에서 봐야 할 것

- BIG PICTURE는 **책임과 경계의 지도**다.
- Application과 Data는 같은 공간에 보이더라도 **책임이 다르다.**
- Interface는 기술명이 아니라 **경계 통과 Mechanism**이다.
- PDMG는 전체 지도 중 Information Application의 일부를 구현적으로 증명하는 Reference다.

---

# 2. VISION에서 전달받은 계약

## FIG-BP-02. VISION → BIG PICTURE Input

```text
VISION
  │
  ├─ Data-Centric
  ├─ FAST / DEEP
  ├─ 5대 Service Domain
  ├─ 3대 Control
  ├─ 5대 NFR
  ├─ Boundary First
  ├─ Standard Interface
  ├─ Resource Isolation
  └─ Runtime Evidence
  │
  ▼
BIG PICTURE
  │
  ├─ Application Group
  ├─ System Group
  ├─ Data Subject
  ├─ Enterprise Context
  ├─ Interface Boundary
  └─ Cross-cutting Responsibility
```

### BIG PICTURE에서 임의로 바꾸지 않는 것

```text
Data-Centric 방향
FAST / DEEP 분리
RDW / ADW 책임 분리
목적별 Interface
PDMG = Reference
Evidence-first 원칙
```

---

# 3. Service Domain vs Application Group

## FIG-BP-03. 5대 Service Domain과 6개 Application Group의 관계

```text
┌──────────────────────────── 5대 Service Domain ───────────────────────────┐
│                                                                          │
│ ① Marketing Platform                                                    │
│ ② Data Platform                                                         │
│ ③ BI Portal                                                             │
│ ④ Data Governance                                                       │
│ ⑤ IT Service & Business Support                                         │
│                                                                          │
└──────────────────────────────────────────────────────────────────────────┘
                                  │
                                  ▼
┌────────────────────── Application Classification ────────────────────────┐
│                                                                          │
│ Marketing Platform     → MP                                              │
│ Data Platform          → RD + AD                                         │
│ BI Portal              → BI                                              │
│ Data Governance        → DG                                              │
│ IT Service & Support   → IM                                              │
│                                                                          │
└──────────────────────────────────────────────────────────────────────────┘
```

### 핵심 해석

- 상위 **Service Domain은 5개**다.
- Application Classification에서는 Data Platform을 **RD와 AD로 분리**하므로 대구분 코드는 6개가 된다.
- 따라서 다음은 오류다.

```text
5대 도메인이므로 Application Group도 반드시 5개다   X
RD와 AD를 DP 하나로 합치면 더 단순하다              X
```

---

# 4. Application Classification — Top-down

## FIG-BP-04. Application Classification Tree

```text
Application Group / Domain
        │
        ▼
Business / Application
        │
        ▼
Function
        │
        ▼
Program
        │
        ▼
ServiceId / Runtime
```

### 목적

```text
업무 책임 고정
+
System Group 배치
+
Data Ownership 연결
+
Naming / ServiceId 연결
+
Runtime Traceability
```

---

# 5. Application Classification Code Structure

## FIG-BP-05. 3-Level Classification

```text
대구분(2)
   │
   ▼
업무구분(2)
   │
   ▼
기능(1)
```

예:

```text
MP + IC + A
     │
     ▼
   MPICA
```

대구분 Baseline:

```text
MP
RD
AD
BI
DG
IM
```

### 금지

```text
DP
DW
```

같은 임의 단일 대구분으로 RD/AD를 합치지 않는다.

---

# 6. Application Group Responsibility Map

## FIG-BP-06. 6 Application Groups

```text
┌──────────────────────────────────────────────────────────────────────────┐
│ MP — Marketing Platform                                                 │
│ 고객 / 상담 / 상품 / 캠페인 / 실시간행동 / 접점                          │
├──────────────────────────────────────────────────────────────────────────┤
│ RD — RDW                                                                │
│ 실시간/준실시간 SoR / 요약 / 정보제공 / Feedback                         │
├──────────────────────────────────────────────────────────────────────────┤
│ AD — ADW                                                                │
│ 분석 SoR / 통합 / 집계 / Mart / 분석지원                                 │
├──────────────────────────────────────────────────────────────────────────┤
│ BI — BI Portal                                                          │
│ BI / 신용실적 / OLAP / Self-BI / UI/UX                                  │
├──────────────────────────────────────────────────────────────────────────┤
│ DG — Data Governance                                                    │
│ 공통 / Business Metadata / Data Quality / Data Flow                      │
├──────────────────────────────────────────────────────────────────────────┤
│ IM — IT Service & Business Support                                      │
│ Architecture / Common / FW / 배포 / 운영 / 단말 / Batch / Relay          │
└──────────────────────────────────────────────────────────────────────────┘
```

---

# 7. MP — Marketing Platform 전체 구조

## FIG-BP-07. MP Responsibility Tree

```text
MP — Marketing Platform
│
├─ Common
│   └─ CO
│
├─ Customer
│   ├─ IC : 통합고객
│   ├─ PC : 개인고객
│   ├─ BC : 기업고객
│   └─ MS : 미니 싱글뷰
│
├─ Sales / Product
│   ├─ SA : 상담판매
│   └─ PD : 통합상품
│
├─ Campaign
│   ├─ CM : 캠페인
│   └─ EB : EBM
│
├─ Real-time / Behavior
│   ├─ EP : 실시간 처리
│   ├─ BP : 행동정보 처리
│   └─ BD : 고객 행동 데이터
│
└─ Support / Contact
    ├─ SS : 영업지원
    ├─ CS : CS
    ├─ CT : 컨텐츠
    └─ MG : 메시지
```

### MP Architecture 의미

- MP는 하나의 Application이 아니라 **마케팅 업무 책임 집합**이다.
- Customer / Sales / Campaign / Behavior / Contact 책임을 구분한다.
- EP/BP/BD 계열은 일반 조회성 업무와 Runtime 성격이 다를 수 있으므로 LOGICAL/RUNTIME에서 재분리한다.

---

# 8. MP Runtime 의미

## FIG-BP-08. Marketing FAST Flow

```text
Customer Action
      │
      ▼
Behavior Collection
      │
      ▼
Event / Kafka
      │
      ▼
Behavior Processing
      │
      ▼
EBM / Decision
      │
      ▼
Offer / Contact / Message
```

### 핵심

- 이것은 일반 Online Request/Response 경로와 동일하지 않다.
- Event Burst, Replay, Consumer Lag, Duplicate Event 문제를 별도 Runtime에서 다뤄야 한다.

---

# 9. RD — RDW Responsibility

## FIG-BP-09. RDW Architecture Role

```text
RD — RDW
│
├─ CO : Common
├─ SR : Real-time SoR
├─ ZD : Near-real-time Summary
├─ RM : Near-real-time Report Mart
└─ FA : Feedback
```

### RDW가 담당하는 것

```text
준실시간 정보
운영성 조회
정보제공
Feedback
```

### RDW가 기본적으로 담당하지 않는 것

```text
장기 분석 전체
대규모 전략 분석 Mart 전체
모든 Application 업무로직
```

---

# 10. AD — ADW Responsibility

## FIG-BP-10. ADW Architecture Role

```text
AD — ADW
│
├─ CO : Common
├─ SR : Analytical SoR
├─ ZD : Analytical Integrated Summary
├─ UM : Unit Business Mart
├─ RM : Analytical Report Mart
├─ FA : Feedback
└─ DA : Analysis Support
```

### ADW가 담당하는 것

```text
분석
통합
집계
Mart
전략분석
분석지원
```

---

# 11. RD와 AD의 분리 이유

## FIG-BP-11. RDW vs ADW Boundary

```text
                         DATA PLATFORM
                              │
               ┌──────────────┴──────────────┐
               ▼                             ▼
            ┌───────┐                     ┌───────┐
            │  RDW  │                     │  ADW  │
            └───┬───┘                     └───┬───┘
                │                             │
        준실시간 / 운영성                분석 / 전략성
        정보 제공 중심                  집계 / Mart 중심
        FAST와 가까움                   DEEP와 가까움
        CDC 활용 가능                   ETL 활용 가능
                │                             │
                └──────────────┬──────────────┘
                               ▼
                         역할은 연계되지만
                         책임은 분리한다
```

### 핵심 원칙

```text
RDW = ADW                              X
둘 다 DW니까 동일 Pool / 동일 SLA      X
RDW가 ADW의 단순 Cache                  X
```

---

# 12. BI — BI Portal Responsibility

## FIG-BP-12. BI Consumption Map

```text
BI Portal
│
├─ BI
├─ Credit Performance
├─ OLAP
├─ Self BI
└─ UI / UX
      │
      ▼
Approved Data Service / Query
      │
      ├────────► RDW
      └────────► ADW
```

### BI Architecture 원칙

- BI는 Data Platform의 **소비자**다.
- BI가 계정계나 운영 Core를 대량 직접 조회하는 구조를 기본으로 하지 않는다.
- Self-BI의 자유도는 Data Security / Performance / Governance와 함께 통제한다.

---

# 13. DG — Data Governance Responsibility

## FIG-BP-13. Governance Control Plane

```text
Application / Data Platform
          │
          ▼
┌────────────────────────────────────────┐
│ DATA GOVERNANCE                        │
│                                        │
│ CO : Common                            │
│ BM : Business Metadata                 │
│ DQ : Data Quality                      │
│ DL : Data Flow / Lineage               │
└────────────────────────────────────────┘
          │
          ▼
Definition / Quality / Lineage / Control
```

### 핵심

> Governance는 대량 데이터를 직접 이동시키는 적재 Pipeline이 아니라 **Metadata / Quality / Lineage 중심 Control Plane**이다.

---

# 14. IM — IT Service & Business Support

## FIG-BP-14. IM Responsibility Map

```text
IM — IT Service & Business Support
│
├─ Architecture / Standard
├─ System Common
├─ Framework / Library
├─ SCM / Deployment
├─ Terminal Management / Distribution
├─ Batch
├─ Relay
├─ Data Support
├─ Report Designer
└─ Monitoring / Operations
```

### 주의

- 공통영역이라고 해서 Business Logic을 모두 IM으로 이동시키지 않는다.
- 공통 Framework와 업무 Application은 책임을 분리해야 한다.

---

# 15. Application Classification Inventory

## FIG-BP-15. Classification Inventory Concept

```text
Application Group
   │
   ▼
Business Code
   │
   ▼
Function Code
   │
   ▼
System / Application
   │
   ▼
Program / ServiceId
```

최소 Inventory 필드:

| 필드 | 의미 |
|---|---|
| Application Group | MP/RD/AD/BI/DG/IM |
| Business Code | IC/PC/CM 등 |
| Function Code | A/B/... |
| Name | 공식 명칭 |
| Responsibility | 소유 책임 |
| System Group | 배치 대상 |
| Data Subject | 주요 연관 Subject |
| Runtime Type | Online/Event/CDC/ETL 등 |
| Owner | 책임자/조직 |
| Evidence | 근거 |
| Status | FACT/OPEN/DEPRECATED |

---

# 16. Application Classification 정상 / 금지

## FIG-BP-16. Classification Guardrail

```text
[정상]

Application Group
   ↓
Business
   ↓
Function
   ↓
Program
   ↓
ServiceId


[금지]

조직명
   ↓
제품명
   ↓
서버명
   ↓
그것을 Application Classification으로 사용
```

### 핵심

> Application Classification은 **조직도·제품목록·서버목록이 아니다.**

---

# 17. System Group — 정의

## FIG-BP-17. Application vs System Group

```text
Application Classification
= 무엇을 책임하는가?

       ↓ Mapping

System Group
= 어떤 논리 시스템 묶음으로 구현하는가?
```

예:

```text
MP Application Responsibility
   ↓
Marketing System Group
   ↓
Logical Service / Runtime Node
```

---

# 18. System Group 전체 지도

## FIG-BP-18. System Group Map

```text
NSIGHT System Groups
│
├─ Marketing System Group
│
├─ Data Platform System Group
│   ├─ RDW
│   ├─ ADW
│   ├─ CDC
│   └─ ETL
│
├─ BI System Group
│
├─ Data Governance System Group
│
├─ IT Service & Support System Group
│
└─ Infrastructure / Temporary / Supporting Group
```

### System Group 원칙

- System Group은 Application Group과 1:1일 수도 있고 아닐 수도 있다.
- Mapping은 **책임·Runtime·운영경계**를 보고 결정한다.

---

# 19. MP System Group

## FIG-BP-19. Marketing System Group

```text
Marketing System Group
│
├─ Online Marketing Application
├─ Customer / Single View
├─ Campaign / EBM
├─ Real-time Processing
├─ Behavior Processing
├─ Behavior Data
└─ Contact / Message
```

### LOGICAL에서 추가로 결정할 것

```text
몇 개 Logical System으로 분리?
어떤 Node가 독립 Scale?
어떤 영역이 Event Runtime?
어떤 업무가 Online?
어떤 영역이 장애격리 대상?
```

---

# 20. Data Platform System Group

## FIG-BP-20. Data Platform System Group

```text
Core / Source Systems
        │
        ├──── CDC ───────► RDW
        │                   │
        │                   ├──── Online / Data Service
        │                   │
        │                   └──── ETL ─────► ADW
        │                                   │
        └───────────────────────────────────└──► BI / Analysis
```

### 핵심

- CDC와 ETL은 같은 “데이터 이동”이지만 역할이 다르다.
- RDW와 ADW는 동일 System Group 안에서도 Runtime 및 Responsibility를 분리한다.

---

# 21. BI / DG / IM System Group

## FIG-BP-21. Support System Group Map

```text
[BI]
BI Portal / OLAP / Self BI / Performance

[DG]
Metadata / Quality / Lineage

[IM]
Architecture / Common / Framework / Deployment / Batch / Relay / Ops
```

### 해설

이 영역들은 Marketing과 Data Platform을 **지원하거나 소비하거나 통제**하지만,
각자의 소유 책임을 분명히 가져야 한다.

---

# 22. Application ↔ System Mapping

## FIG-BP-22. Mapping Logic

```text
Application Responsibility
        │
        ▼
Runtime Characteristic
        │
        ▼
Failure Domain
        │
        ▼
Operational Ownership
        │
        ▼
System Group
```

최소 Mapping 필드:

```text
Application Group
Business Code
Function
System Group
System Name
Runtime Type
Data Subject
Owner
Evidence
Status
```

---

# 23. Data Subject Area — 기본 개념

## FIG-BP-23. Data Subject Top-down

```text
Business Subject
      │
      ▼
Data Ownership
      │
      ▼
Storage / Platform
      │
      ▼
Producer / Consumer
      │
      ▼
Refresh Mechanism
```

### 핵심

> Data Subject는 Table 목록보다 상위에서 **데이터 책임을 고정하는 분류**다.

---

# 24. Data Subject 6개 트리

## FIG-BP-24. Data Subject Baseline

```text
차세대 정보계 데이터 주제영역
│
├─ 데이터플랫폼 RDW
│   └─ CO · SR · ZD · RM · FA
│
├─ 데이터플랫폼 ADW
│   └─ CO · SR · ZD · UM · RM · FA · DA
│
├─ BI 포탈
│   └─ PT · CR · OA · SB · UI
│
├─ 마케팅플랫폼
│   └─ MP 업무구분 집합
│
├─ 데이터거버넌스
│   └─ CO · BM · DQ · DL
│
└─ IT서비스 및 업무지원
    └─ IM 업무구분 집합
```

### 중요한 해석

이 주제영역은 전통적인 순수 EDW 모델의:

```text
고객
계좌
상품
거래
```

Subject Area로 임의 치환하지 않는다.

현재 프로젝트 분류는:

```text
플랫폼
업무
분석
관리
운영
```

영역을 Data Management 관점에서 묶은 상위 체계다.

---

# 25. RDW Data Subject

## FIG-BP-25. RDW Subject Drill-down

```text
RDW
│
├─ CO : Common
├─ SR : Real-time SoR
├─ ZD : Near-real-time Summary
├─ RM : Near-real-time Report Mart
└─ FA : Feedback
```

책임:

```text
준실시간
운영성 조회
정보제공
Feedback
```

---

# 26. ADW Data Subject

## FIG-BP-26. ADW Subject Drill-down

```text
ADW
│
├─ CO : Common
├─ SR : Analytical SoR
├─ ZD : Analytical Integrated Summary
├─ UM : Unit Business Mart
├─ RM : Analytical Report Mart
├─ FA : Feedback
└─ DA : Analysis Support
```

책임:

```text
분석
통합
집계
Mart
전략분석
```

---

# 27. Application Subject vs Data Subject

## FIG-BP-27. Responsibility Separation

```text
Marketing Application
        │
        │ Consume / Contract
        ▼
RDW Subject
        │
        │ ETL / Approved Flow
        ▼
ADW Subject
        │
        ▼
BI Consumer
```

### 핵심

```text
Application Responsibility
≠
Data Ownership
≠
Storage Responsibility
```

---

# 28. Data Subject Inventory

최소 필드:

| 필드 | 정의 |
|---|---|
| Subject ID | 대구분+업무구분 |
| Subject Name | 공식 명칭 |
| Domain | RD/AD/MP/BI/DG/IM |
| Description | 책임 |
| SoR Type | Operational/Analytical/Management |
| Storage | RDW/ADW/기타 |
| Producer | 주요 생산자 |
| Consumer | 주요 소비자 |
| Refresh | CDC/ETL/Event/Online |
| SLA | 근거 있을 때 |
| Owner | Data Owner |
| Security Class | 후속 |
| Evidence | PPT/정의서/Source |
| Status | FACT/OPEN |

---

# 29. Data Ownership 원칙

## FIG-BP-28. Data Ownership

```text
Marketing
= 고객 / 캠페인 / 오퍼링 업무 책임

RDW
= 준실시간 데이터 제공 책임

ADW
= 분석 데이터 책임

Governance
= Metadata / Quality / Lineage 책임
```

### 금지

```text
마케팅이 사용하니 Marketing DB가 데이터 Owner다       X
ADW에 적재되니 모든 데이터 Owner는 ADW다              X
Governance가 관리하니 실데이터 처리도 Governance다     X
```

---

# 30. RDW / ADW Data Flow

## FIG-BP-29. FAST-to-DEEP Data Flow

```text
Core / Related / External
        │
        │ CDC
        ▼
      RDW
        │
        ├────► Online / FAST Consumption
        │
        │ ETL
        ▼
      ADW
        │
        └────► BI / Analysis / DEEP Consumption
```

### 주의

- 이 그림은 상위 흐름이다.
- 실제 Source별 CDC, ETL Job, Table, Schedule은 MECHANISM/RUNTIME에서 내려간다.

---

# 31. Enterprise Context

## FIG-BP-30. Channel / Application / Data / External

```text
┌──────────────────────────── CHANNEL ──────────────────────────────┐
│ Account Terminal / Information Terminal / Package UI             │
│ Web / Mobile                                                     │
│ SMS / PUSH / MAIL                                                │
└──────────────────────────────┬────────────────────────────────────┘
                               │
                               ▼
┌────────────────────────── ACCESS BOUNDARY ────────────────────────┐
│ MCA / API / WEB / Gateway / Authentication                       │
└──────────────────────────────┬────────────────────────────────────┘
                               │
                               ▼
┌──────────────────── INFORMATION APPLICATION ──────────────────────┐
│ Marketing / BI / Governance / IT Service & Support               │
└─────────────────┬───────────────────────┬─────────────────────────┘
                  │                       │
                  ▼                       ▼
             Event / Kafka          Data Platform
                                     RDW / ADW
                  │                       │
                  └──────────────┬────────┘
                                 ▼
┌──────────────────────── CORE / RELATED / EXTERNAL ────────────────┐
│ Core / Enterprise Internal / Big Data / External Institutions     │
└───────────────────────────────────────────────────────────────────┘
```

---

# 32. Channel Entry 의미 분리

## FIG-BP-31. Three Entry Meanings

```text
① 계정성 거래
Account Terminal
    ↓
MCA
    ↓
Core

② 정보계 업무
Information Terminal / Web
    ↓
Information Application
    ↓
RDW / Related Service

③ 고객행동 Event
Web / Mobile
    ↓
Collector
    ↓
Kafka
    ↓
Marketing Reaction
```

### 핵심

- 채널이라는 이유만으로 같은 Runtime이 아니다.
- 각 Entry는 **업무 의미 / Latency / State / Failure Handling**이 다르다.

---

# 33. Information Application Boundary

## FIG-BP-32. Application Contract Boundary

```text
Application A
      │
      │ Approved Contract
      ▼
Interface
      │
      ▼
Application B
```

### 금지

```text
Application A
   ↓
Application B DAO

Application A
   ↓
Application B 내부 Table DML
```

### 이유

- Business Ownership 침해
- 변경 파급 증가
- Transaction 경계 오염
- Security/Audit 우회
- 테스트·운영추적 단절

---

# 34. Marketing Platform Boundary

## FIG-BP-33. Marketing Platform Context

```text
                         Marketing Platform
                                │
        ┌───────────────────────┼────────────────────────┐
        ▼                       ▼                        ▼
     Customer              Campaign/EBM           Real-time/Behavior
        │                       │                        │
        └──────────────┬────────┴────────────┬──────────┘
                       ▼                     ▼
                      RDW                  Event
                       │                     │
                       └──────────┬──────────┘
                                  ▼
                          Contact / Message
```

---

# 35. Marketing Event Boundary

## FIG-BP-34. Event Runtime Boundary

```text
Customer Behavior
       │
       ▼
Collector
       │
       ▼
Event Broker
       │
       ▼
Behavior Processing
       │
       ▼
EBM / Decision
       │
       ▼
UMS / Customer Contact
```

### Runtime 분리 원칙

```text
Event Consumer Thread
≠
Online Request Thread
```

---

# 36. BI Portal Boundary

## FIG-BP-35. BI Data Access

```text
User
 ↓
BI Portal / OLAP / Self BI
 ↓
Approved Query / Data Service
 ↓
┌──────────────┬──────────────┐
▼              ▼
RDW            ADW
```

### 금지

```text
BI → Core 대량 직접조회    X
Self-BI → 운영DB 무제한 SQL X
```

---

# 37. Data Governance Boundary

## FIG-BP-36. Governance Cross-cutting

```text
          Metadata
             │
             ▼
Application ─┼─ Data Platform
             │
             ▼
          Quality
             │
             ▼
          Lineage
```

### 핵심

Governance는:

```text
Control / Metadata Plane
```

이며 대량 실데이터 처리 Runtime과 역할을 혼동하지 않는다.

---

# 38. IT Service & Support Boundary

## FIG-BP-37. IM Cross-cutting Support

```text
                 IT Service & Support
                         │
     ┌───────────────────┼─────────────────────┐
     ▼                   ▼                     ▼
 Framework / Common    DevOps / Deploy     Operations
     │                   │                     │
     └───────────────────┼─────────────────────┘
                         ▼
                    Business Systems
```

---

# 39. Integration Mechanism by Purpose

## FIG-BP-38. Purpose-based Interface Map

```text
[Transaction]
    │
    └─────────► MCA / Online Service

[Online Service]
    │
    └─────────► API / JSON

[Event]
    │
    └─────────► Kafka / Event

[Change Data]
    │
    └─────────► CDC

[Bulk / Analytics]
    │
    └─────────► ETL

[File]
    │
    └─────────► FOS / MFT

[Controlled Data Access]
    │
    └─────────► JDBC / SQL [승인·소유권 통제]
```

### 핵심 결론

> **모든 Interface를 하나의 기술로 통일하지 않는다.**

---

# 40. Interface Decision Tree

## FIG-BP-39. Integration Choice

```text
업무 목적?
   │
   ├─ 즉시 Request/Response?
   │      └─ API / MCA
   │
   ├─ 비동기 Event?
   │      └─ Kafka
   │
   ├─ DB 변경 전달?
   │      └─ CDC
   │
   ├─ 대량 데이터?
   │      └─ ETL
   │
   └─ 파일?
          └─ MFT / FOS
```

---

# 41. “HTTP/JSON”과 목적별 Mechanism의 관계

## FIG-BP-40. Scope Clarification `[OPEN]`

```text
온라인 서비스 메시지 표준
HTTP / JSON
       │
       │ 적용범위
       ▼
Online Service Boundary

BUT

Event     → Kafka
Change    → CDC
Bulk      → ETL
File      → MFT/FOS
```

### 판정

```text
[OPEN]
"모든 통신 HTTP/JSON"의 공식 범위 재확정 필요
```

권장 해석 후보:

```text
HTTP/JSON
= 온라인 서비스 메시지 표준

Kafka/CDC/ETL/File
= 목적별 Data/Integration Mechanism
```

하지만 최종 Decision으로 자동 승격하지 않는다.

---

# 42. Interface Boundary Contract

## FIG-BP-41. Boundary Contract

```text
Producer
   │
   ▼
Entry Contract
   │
   ├─ Interface ID
   ├─ Schema / Message
   ├─ Owner
   ├─ Security
   ├─ Timeout
   ├─ Recovery
   └─ Trace
   │
   ▼
Mechanism
   │
   ▼
Consumer
```

BIG PICTURE에서는 Contract의 존재를 요구하고,
상세 필드는 MECHANISM에서 확정한다.

---

# 43. Interface 금지 패턴

## FIG-BP-42. Anti-pattern Map

```text
Channel ───────► DB                            X

External ──────► Internal DB Direct            X

Application A ─► Application B DAO             X

Bulk Data ─────► Online API Massive Payload    X

Event ─────────► Synchronous Request Thread    X

All Integration ─► REST Only                   X
```

---

# 44. Application vs Data Responsibility

## FIG-BP-43. Responsibility Split

```text
┌──────────────────────── APPLICATION ────────────────────────┐
│ Business Use Case                                           │
│ Validation / Rule / Process                                 │
│ Interface Contract                                          │
│ Customer / User Experience                                  │
└─────────────────────────┬───────────────────────────────────┘
                          │
                          ▼
                       Contract
                          │
                          ▼
┌──────────────────────── DATA PLATFORM ──────────────────────┐
│ Storage                                                     │
│ Integration                                                 │
│ Transformation                                              │
│ SoR / Summary / Mart                                        │
│ Data Provision                                              │
└─────────────────────────┬───────────────────────────────────┘
                          │
                          ▼
┌──────────────────────── GOVERNANCE ─────────────────────────┐
│ Metadata / Quality / Lineage                                │
└─────────────────────────────────────────────────────────────┘
```

---

# 45. 승인된 JDBC의 의미

## FIG-BP-44. Controlled DB Access

```text
Application
    │
    │ Approved / Owned Data Access
    ▼
JDBC
    │
    ▼
Owned / Approved Schema
```

### JDBC가 허용된다고 해서 허용되는 것이 아닌 것

```text
타 Application DB 직접 DML
Cross-domain 업무소유권 침해
DB-Link 기반 임의 결합
권한검증 없는 공용계정
```

---

# 46. Infrastructure / Execution Boundary — Big Picture 수준

## FIG-BP-45. Logical Space → Execution Space

```text
Application Responsibility
       │
       ▼
System Group
       │
       ▼
Logical Runtime Role
       │
       ▼
Execution Space
 WEB / WAS / Event / CDC / ETL / DB
       │
       ▼
Physical Resource
```

### BIG PICTURE에서 확정하지 않는 것

```text
Hostname
CPU / Memory
Tomcat maxThreads
Hikari Pool
Port
Exact JVM Count
```

이 값들은 PHYSICAL에서 확정한다.

---

# 47. 세 가지 경계

## FIG-BP-46. Boundary Layers

```text
Business Boundary
= 어느 Domain / Application 책임인가?

System Boundary
= 어느 Logical System이 담당하는가?

Execution Boundary
= 어느 Runtime Node에서 실행되는가?
```

### 핵심

이 세 경계를 하나의 “서버” 그림으로 합치지 않는다.

---

# 48. Security Boundary

## FIG-BP-47. Security Cross-cutting

```text
CHANNEL
  │ Authentication
  ▼
ACCESS
  │ Authorization / Trust
  ▼
APPLICATION
  │ Service/Data Permission
  ▼
INTEGRATION
  │ Producer/Consumer / Certificate / ACL
  ▼
DATA
  │ DB Account / Encryption / Masking / Audit
```

### Mechanism별 보안 질문

| Mechanism | 상위 보안 질문 |
|---|---|
| API/MCA | 인증/인가/채널 신뢰 |
| Kafka | Producer/Consumer ACL |
| CDC | Replication Account |
| ETL | Source/Target Account |
| File | Encryption/Integrity/Transfer Account |
| JDBC | Schema/Privilege/Owner |

---

# 49. Observability Boundary

## FIG-BP-48. End-to-End Evidence

```text
Channel
  │ GUID
  ▼
Application
  │ ServiceId
  ▼
Interface
  │ InterfaceId / Correlation
  ▼
Target / Data
  │ SQL / Event / Job
  ▼
Response / Result
  │
  ▼
Log / Metric / Trace / Audit
```

### BIG PICTURE 요구

> **모든 주요 Boundary에서 Trace Key를 잃지 않아야 한다.**

---

# 50. Observability 질문

BIG PICTURE가 운영에게 넘기는 질문:

```text
어느 채널에서 왔는가?
어느 Application Group인가?
어느 ServiceId인가?
어느 Interface를 통과했는가?
어느 Data Platform을 사용했는가?
어디서 지연/오류가 발생했는가?
```

---

# 51. FAST / DEEP Big Picture Mapping

## FIG-BP-49. FAST / DEEP Placement

```text
FAST
│
├─ Channel
├─ Marketing Online
├─ Event
├─ Near Real-time
└─ RDW

DEEP
│
├─ RDW
├─ ETL
├─ ADW
└─ BI / Analysis
```

### 해설

RDW는 FAST와 DEEP의 연결점이 될 수 있지만,
Runtime과 Responsibility를 단순히 하나로 합치지 않는다.

---

# 52. Runtime Type 상위 Mapping

## FIG-BP-50. Domain → Runtime

```text
MP Online     → Online Runtime
MP Event      → Event Runtime
RD            → CDC / Query Runtime
AD            → ETL / Analysis Runtime
BI            → Analytical Runtime
DG            → Governance / Management Runtime
IM Batch      → Batch Runtime
IM Relay      → Integration Runtime
File          → File Runtime
```

상세 Sequence/Failure/SLO는 RUNTIME에서 확정한다.

---

# 53. Major Logical Server Candidate

## FIG-BP-51. Logical Role Candidates

```text
Logical Runtime Roles
│
├─ WEB
├─ WAS
├─ Marketing AP
├─ Real-time/Event AP
├─ CDC Relay
├─ ETL
├─ BI / Analysis
├─ Governance
├─ Batch / Scheduler
├─ OM / Monitoring
├─ RDW
└─ ADW
```

### 주의

이 목록은 **Logical Candidate**이며 실제 Hostname/수량을 의미하지 않는다.

---

# 54. Physical Role Evidence와의 관계

## FIG-BP-52. Big Picture vs Physical Evidence

```text
BIG PICTURE
Marketing System Group
       │
       ▼
Logical Marketing WEB/WAS
       │
       ▼
PHYSICAL
Actual WEB #1/#2
Actual WAS #1/#2
       │
       ▼
Inventory / Config
```

### 핵심

```text
Big Picture Role
≠
Physical Host
```

---

# 55. System / External Boundary

## FIG-BP-53. External Context

```text
NSIGHT
  │
  ├─ Core / Account System
  ├─ Related Enterprise Systems
  ├─ Big Data / Analysis Systems
  ├─ External Institutions
  └─ Customer Contact Systems
```

모든 External 관계는:

```text
Boundary
Contract
Owner
Security
Recovery
Trace
```

를 가져야 한다.

---

# 56. Boundary 변경은 Architecture 변경

## FIG-BP-54. Boundary Change Impact

```text
Boundary Change
   │
   ├─ Responsibility
   ├─ Interface
   ├─ Security
   ├─ Data Ownership
   ├─ Deployment
   ├─ Monitoring
   └─ Test
```

따라서 System Boundary 변경은 단순 구성변경이 아니라 Architecture Change다.

---

# 57. PDMG Reference Position

## FIG-BP-55. PDMG in Enterprise Context

```text
NSIGHT Enterprise Context
│
├─ Channel / Access
│
├─ Information Application
│   │
│   ├─ Marketing / Business Applications
│   │      │
│   │      └─ PDMG REFERENCE
│   │          ├─ pdmg-ui
│   │          ├─ pdmg-jwt
│   │          ├─ pdmg-fw
│   │          ├─ pdmg-service
│   │          └─ pdmg-om [범위 재확인]
│   │
│   └─ BI / Governance / Support
│
├─ Event Platform
│
├─ Data Platform
│   ├─ RDW
│   └─ ADW
│
└─ External / Core
```

---

# 58. PDMG가 증명하는 것

## FIG-BP-56. PDMG Evidence Coverage

```text
PDMG
│
├─ Module / Package / Layer
├─ Online Request Entry
├─ TCF / Dispatcher / Handler
├─ Timeout / Worker / Transaction
├─ Message / Context / Error / Logging
├─ JWT / SSO
└─ ServiceId / Trace
```

---

# 59. PDMG가 증명하지 않는 것

```text
전체 5대 Service Domain
전체 RDW / ADW
전체 Kafka/Event Platform
전체 CDC / ETL
전체 BI
전체 Governance
전체 Enterprise DR
전체 Physical Infrastructure
```

### 핵심

> **PDMG를 전체 NSIGHT Architecture로 확대 해석하지 않는다.**

---

# 60. 대표 End-to-End Scenario A — 정보계 Online 조회

## FIG-BP-57. Online Information Flow

```text
Information Terminal / Web
        │
        ▼
Access Boundary
        │
        ▼
Information Application
        │
        ▼
Business Service
        │
        ▼
RDW / Approved Data Service
        │
        ▼
Response
```

### 책임

- Channel: 요청 시작
- Application: 업무처리
- Data Platform: 데이터 제공
- Observability: GUID/ServiceId 추적

---

# 61. 대표 Scenario B — 고객행동 실시간 반응

## FIG-BP-58. Event Reaction

```text
Web / Mobile
     │
     ▼
Customer Action
     │
     ▼
Collector
     │
     ▼
Kafka
     │
     ▼
Behavior Processing
     │
     ▼
EBM / Decision
     │
     ▼
Message / Offer
```

### 특징

```text
Asynchronous
Burst 가능
Replay 필요
Online Request와 Thread/Failure Model 다름
```

---

# 62. 대표 Scenario C — Core Change → RDW

## FIG-BP-59. CDC Flow

```text
Core DB
   │
   │ Change
   ▼
CDC
   │
   ▼
RDW
   │
   ▼
Operational / Near Real-time Consumer
```

---

# 63. 대표 Scenario D — RDW → ADW → BI

## FIG-BP-60. DEEP Flow

```text
RDW
  │
  │ ETL
  ▼
ADW
  │
  ▼
BI / OLAP / Self BI
```

---

# 64. 대표 Scenario E — External File

## FIG-BP-61. File Boundary

```text
External Institution
       │
       ▼
Managed File Boundary
       │
       ├─ Authentication
       ├─ Encryption
       ├─ Integrity
       ├─ Filename / Schema
       └─ Retry / Recovery
       │
       ▼
NSIGHT File Processing
```

---

# 65. Cross-Cutting Responsibility Matrix

| 영역 | Application | Data | Integration | Security | Operations |
|---|---|---|---|---|---|
| Marketing Online | 업무책임 | RDW 소비 | API/MCA | 사용자/업무권한 | p95/Error |
| Event | 반응로직 | Event/Data | Kafka | Producer/Consumer ACL | Lag/Replay |
| RDW | Data Service | 운영형 Data | CDC/Query | DB권한 | Freshness |
| ADW | 분석지원 | 분석형 Data | ETL | DB권한 | Batch/Load |
| BI | 분석소비 | RD/AD 소비 | Query | Data Auth | Query Load |
| DG | 통제 | Metadata | Governance API | Admin | Quality/Lineage |
| IM | 공통/운영 | 운영정보 | Batch/Relay | Admin | Deploy/Ops |

---

# 66. Big Picture NFR Mapping

## FIG-BP-62. NFR → Boundary

```text
Performance
→ FAST / DEEP / Resource Separation

Availability
→ Failure Domain / HA / DR Candidate

Scalability
→ System Group / Runtime Node Independent Scale

Security
→ Entry / Interface / Data Boundary

Observability
→ Cross-boundary GUID / ServiceId
```

---

# 67. Big Picture Architecture Principles

## FIG-BP-63. Principle Cards

```text
BP-01 Responsibility First
BP-02 Boundary First
BP-03 Application / Data Separation
BP-04 Purpose-based Interface
BP-05 FAST / DEEP Separation
BP-06 RDW / ADW Role Separation
BP-07 Cross-boundary Security
BP-08 Cross-boundary Trace
BP-09 PDMG Reference-only
BP-10 Physical Detail Deferral
```

---

# 68. 정상 패턴

```text
Application → Approved Interface → Application
Channel → Application → Data
Core → CDC → RDW
RDW → ETL → ADW
Event → Kafka → Consumer
BI → Approved Query/Data Service → RDW/ADW
```

---

# 69. 금지 패턴

```text
Channel → DB
Application → 다른 Application DAO
External → Internal DB Direct
All Integration = REST
Bulk = Online API
RDW = ADW
PDMG = NSIGHT 전체
Big Picture = Host/Port 상세
```

---

# 70. Inventory Template — Application

```yaml
application:
  group:
  businessCode:
  functionCode:
  name:
  responsibility:
  systemGroup:
  runtimeType:
  dataSubjects:
  owner:
  evidence:
  status:
```

---

# 71. Inventory Template — System

```yaml
system:
  systemId:
  name:
  systemGroup:
  applicationGroups:
  responsibility:
  runtimeTypes:
  entryPoints:
  dataPlatforms:
  externalRelations:
  owner:
  evidence:
  status:
```

---

# 72. Inventory Template — Data Subject

```yaml
dataSubject:
  subjectId:
  name:
  domain:
  description:
  sorType:
  platform:
  producers:
  consumers:
  refreshMechanism:
  sla:
  owner:
  securityClass:
  evidence:
  status:
```

---

# 73. Mapping Template

```text
Application Group
   ↓
Business / Function
   ↓
System Group
   ↓
Logical Runtime Role
   ↓
Data Subject
   ↓
Integration Mechanism
   ↓
Owner / Evidence
```

---

# 74. CONFIRMED

```text
[CONFIRMED / BASELINE]
- 상위 Service Domain은 5개다.
- Application Group은 MP/RD/AD/BI/DG/IM 6개로 분류된다.
- Data Platform Domain은 RD와 AD로 분리된다.
- MP는 Customer/Sales/Campaign/Behavior/Support 책임을 포함한다.
- RDW와 ADW의 역할은 분리한다.
- Channel Entry는 계정성/정보계/Event로 의미가 다르다.
- Integration은 목적별 Mechanism을 사용한다.
- PDMG는 Information Application 영역의 Reference다.
```

---

# 75. WORKING BASELINE

```text
[WORKING BASELINE]
- GSLB/L4/WEB/WAS 등 Physical 실행 경로는 후속장 기준으로 사용
- Logical Runtime Candidate는 WEB/WAS/Event/CDC/ETL/BI/Governance 등
- 목적별 Interface는 API/MCA/Kafka/CDC/ETL/File/JDBC
```

---

# 76. CONFLICT / OPEN

```text
[OPEN-BP-01]
"모든 통신 HTTP/JSON"의 공식 적용범위

[OPEN-BP-02]
Application Group / System Group 최신 승인 Inventory

[OPEN-BP-03]
Data Subject Owner / SLA / Security Class

[OPEN-BP-04]
PDMG가 MP System Group 내 정확히 어느 Target System에 매핑되는가

[OPEN-BP-05]
Temporary Infrastructure System Group의 최종 운영분류
```

---

# 77. GAP Register

| ID | GAP | 영향 |
|---|---|---|
| GAP-BP-01 | Application Classification 최신 SSOT 미확정 | Naming/Ownership |
| GAP-BP-02 | Application↔System Mapping 전수 미완료 | Logical 설계 |
| GAP-BP-03 | Data Subject Owner/SLA 미완료 | Data Governance |
| GAP-BP-04 | RDW/ADW 상세 Producer/Consumer Mapping 미완료 | Data Flow |
| GAP-BP-05 | HTTP/JSON vs 목적별 Mechanism 공식 Scope 미확정 | Interface |
| GAP-BP-06 | PDMG Target System Mapping 미완료 | Reference Alignment |
| GAP-BP-07 | External Interface 전수 Catalog 미완료 | Boundary |
| GAP-BP-08 | Cross-boundary Security Owner 미완료 | Security |
| GAP-BP-09 | Trace Key 전구간 유지 여부 미검증 | Observability |
| GAP-BP-10 | Logical Server Candidate 최신 승인본 미확정 | LOGICAL Handoff |

---

# 78. RISK Register

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-BP-01 | RD/AD를 하나의 DW로 단순화 | High |
| RISK-BP-02 | Application 책임과 Data Owner 혼동 | High |
| RISK-BP-03 | System Group을 제품/서버 목록으로 정의 | Medium/High |
| RISK-BP-04 | 모든 Interface REST화 | High |
| RISK-BP-05 | Event를 Online Request Thread로 처리 | Critical |
| RISK-BP-06 | 외부/타도메인 DB 직접접근 | Critical |
| RISK-BP-07 | PDMG를 NSIGHT 전체로 일반화 | High |
| RISK-BP-08 | Big Picture에 Physical 상세를 조기확정 | Medium |
| RISK-BP-09 | Data Subject를 일반 EDW Subject로 임의치환 | High |
| RISK-BP-10 | Trace Key 경계 손실 | High |

---

# 79. ADR 후보

```text
ADR-BP-01 Application Classification SSOT
ADR-BP-02 System Group Baseline
ADR-BP-03 RDW / ADW Responsibility Contract
ADR-BP-04 Data Subject Ownership
ADR-BP-05 Purpose-based Interface Selection
ADR-BP-06 HTTP/JSON Scope
ADR-BP-07 Cross-domain DB Access
ADR-BP-08 PDMG Target Mapping
ADR-BP-09 Enterprise Trace Key
ADR-BP-10 External Boundary Governance
```

---

# 80. LOGICAL Handoff

## FIG-BP-64. BIG PICTURE → LOGICAL

```text
BIG PICTURE Output
│
├─ Application Group
│   MP / RD / AD / BI / DG / IM
│
├─ System Group
│   Marketing / Data / BI / DG / IM
│
├─ Data Subject
│   RDW / ADW / BI / MP / DG / IM
│
├─ Runtime Meaning
│   Online / Event / CDC / ETL / File
│
├─ Boundary
│   Application / Data / External / Security
│
└─ PDMG Reference Position
    Information Application 하위 Reference
          │
          ▼
LOGICAL Input
│
├─ Zone
├─ Logical System
├─ Logical Node
├─ Component
├─ Layer
├─ Connection
├─ Allowed Flow
└─ Forbidden Flow
```

---

# 81. LOGICAL에서 반드시 답할 질문

```text
1. 각 System Group은 어떤 Zone에 배치되는가?
2. 각 Logical System은 어떤 책임을 갖는가?
3. Logical Node는 어떤 Runtime 역할을 갖는가?
4. Online/Event/CDC/ETL/File 연결은 어떤 방향으로 허용되는가?
5. Application에서 다른 Application의 Persistence로 갈 수 있는가?
6. RDW와 ADW는 어떤 Logical Boundary로 분리되는가?
7. Security/Observability Cross-cutting은 어떤 Logical Node에 걸리는가?
8. PDMG Module/Application은 어느 Logical Node에 대응하는가?
```

---

# 82. BIG PICTURE 최종 통합 지도

## FIG-BP-65. Big Picture Summary

```text
CHANNEL / EXTERNAL
       │
       ▼
ACCESS / ENTRY
       │
       ▼
APPLICATION DOMAINS
 MP ───── BI ───── DG ───── IM
       │
       ▼
DATA PLATFORM
 RDW ─────────── ADW
       │
       ▼
PURPOSE-BASED INTEGRATION
 API/MCA | Kafka | CDC | ETL | File
       │
       ▼
CROSS-CUTTING
 Security | Standard | Observability | Operations
       │
       ▼
PDMG REFERENCE
 Module | Runtime | TCF | TX | JWT | ServiceId
       │
       ▼
LOGICAL DRILL-DOWN
 Zone | System | Node | Component | Connection
```

---

# 83. Definition of Done

## 83.1 Application

- [x] 5대 Service Domain과 6개 Application Group을 구분
- [x] MP/RD/AD/BI/DG/IM Responsibility 시각화
- [x] MP 상세 업무분류를 책임 Group으로 재구성
- [x] Application Classification을 조직/제품 목록과 구분

## 83.2 System

- [x] System Group의 의미 정의
- [x] Application↔System Mapping 원칙 정의
- [x] Logical Candidate와 Physical Host 구분

## 83.3 Data

- [x] Data Subject 6개 상위체계 시각화
- [x] RDW/ADW Subject 분리
- [x] Application Responsibility와 Data Ownership 구분
- [x] Data Subject Inventory 필드 정의

## 83.4 Boundary

- [x] Enterprise Context 시각화
- [x] Channel Entry 3종 의미 분리
- [x] Application Contract Boundary 정의
- [x] 목적별 Interface 정의
- [x] 금지 Interface 정의

## 83.5 Cross-cutting

- [x] Security Boundary 정의
- [x] Observability Boundary 정의
- [x] FAST/DEEP Mapping 정의
- [x] PDMG Reference 위치 정의

## 83.6 Governance

- [x] CONFIRMED / OPEN / GAP / RISK 분리
- [x] ADR 후보 정의
- [x] LOGICAL Handoff 정의

**BIG PICTURE 장 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. 최신 Application Classification / System Group 승인 Inventory 연결
2. Data Subject Owner / SLA / Security Class 보완
3. HTTP/JSON Scope 공식 Decision
4. PDMG → Target System 공식 Mapping
5. External Interface Catalogue 연결
6. Cross-boundary Trace 검증

---

# 84. 다음 장

다음은 **03. LOGICAL — Zone / Logical System / Node / Layer / Connection**이다.

LOGICAL에서는 BIG PICTURE에서 확정한 책임공간을:

```text
Application Group
   ↓
System Group
   ↓
Logical Zone
   ↓
Logical System
   ↓
Logical Node
   ↓
Component / Layer
   ↓
Connection
```

순서로 Drill-down한다.

특히 다음을 그림으로 명확히 한다.

```text
어떤 연결이 허용되는가?
어떤 연결이 금지되는가?
어느 Node가 독립 장애영역인가?
어느 Runtime이 Online/Event/CDC/ETL인가?
PDMG는 어느 Logical Node에 대응하는가?
```
