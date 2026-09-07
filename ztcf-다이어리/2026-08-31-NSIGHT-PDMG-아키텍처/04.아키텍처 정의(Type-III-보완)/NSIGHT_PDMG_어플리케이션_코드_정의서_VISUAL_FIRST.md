# NSIGHT / PDMG
# 어플리케이션 코드 정의서
## Application Classification Code Definition
## App Group / Application / Function / Program / ServiceId 연계 기준
## Visual-First / SSOT-Driven / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-STD-APPLICATION-CODE`  
> 문서 유형: **어플리케이션 코드 기준정보 / 개발·아키텍처 공통표준**  
> 문서 상태: **Baseline Candidate / Conflict Preserved / Evidence-First**  
> 기준일: **2026-08-31**  
> 상위 기준: `Application Architecture`, `Naming Development Standard`  
> 코드 SSOT 후보: **승인 어플리케이션 코드 사전 / CMDB / Architecture Model**

---

## 근거자료 적용 우선순위

```text
최신 Architecture Application Classification (2026-08-25)
        ↓
현재 NSIGHT Architecture Baseline
        ↓
개발표준 Application Code 자료 (2026-08-23)
        ↓
PDMG Source Naming Reference
```

본 정의서의 현재 코드 Baseline은 최신 Architecture 분류체계를 우선 적용한다.
과거 개발표준과 다른 코드는 삭제하지 않고 `[CONFLICT]`로 보존한다.

# 0. 문서 목적

본 문서는 차세대 정보계에서 사용되는 **어플리케이션 분류 코드**를 정의한다.

## FIG-AC-01. Application Code의 역할

```text
Business Responsibility
        ↓
Application Group Code
        ↓
Application Code
        ↓
Function Code
        ↓
Program ID
        ↓
ServiceId
        ↓
Package / Source / Runtime Trace
```

어플리케이션 코드는 단순 약어가 아니라 다음의 공통 식별축이다.

```text
Architecture Classification
+
Program Naming
+
Package Naming
+
ServiceId
+
System Group Mapping
+
Data Subject Mapping
+
Runtime Traceability
```

---

# 1. 적용범위

## FIG-AC-02. Scope

```text
Application Architecture
       ↓
Application Code
       ├─ Program ID
       ├─ ServiceId
       ├─ Package
       ├─ Mapper Resource
       ├─ System Group
       ├─ Data Subject
       ├─ Interface
       └─ Monitoring / Trace
```

적용대상:

- 정보단말/온라인 프로그램
- 배치 프로그램
- 데이터플랫폼 프로그램
- BI 프로그램
- 데이터거버넌스
- IT서비스 및 업무지원
- 프로그램/서비스 명명
- Package/Resource 경로
- 시스템/서버 역할 분류

---

# 2. 어플리케이션 분류 3단 구조

## FIG-AC-03. Three-Level Classification

```text
Level 1
대구분 / Application Group
2 byte
        ↓
Level 2
업무구분 / Application
2 byte
        ↓
Level 3
세부업무 / Function
1 byte
```

결합:

```text
AA + BB + C
=
5-byte Application Classification Key
```

예:

```text
MP + IC + A
=
MPICA
```

---

# 3. 코드 구조

| Level | 구분 | 길이 | 의미 | 예 |
|---|---|---:|---|---|
| L1 | 어플리케이션 그룹 | 2 | 최상위 Application/Domain | MP |
| L2 | 어플리케이션 | 2 | 업무 책임 | IC |
| L3 | 기능 | 1 | 세부 업무 기능 | A |
| 합계 | 분류 Key | 5 | 프로그램 분류 Prefix | MPICA |

### 중요

```text
L1 + L2
```

를 최소 업무 식별 단위로 사용한다.

왜냐하면:

```text
RD-SR
≠
AD-SR
```

처럼 동일 L2 코드가 서로 다른 그룹에서 사용될 수 있기 때문이다.

---

# 4. Application Group Code

## FIG-AC-04. 6 Application Groups

```text
차세대 정보계
│
├─ MP  Marketing Platform
├─ RD  Real-time Data Warehouse
├─ AD  Analytical Data Warehouse
├─ BI  Business Intelligence
├─ DG  Data Governance
└─ IM  Information Management
```

| 코드 | 한글명 | 영문명 | 핵심 책임 |
|---|---|---|---|
| MP | 마케팅플랫폼 | Marketing Platform | 고객·상담·상품·캠페인·행동·메시지 |
| RD | 데이터플랫폼 RDW | Real-time Data Warehouse | 실시간/준실시간 정보 |
| AD | 데이터플랫폼 ADW | Analytical Data Warehouse | 분석·집계·마트 |
| BI | BI 포탈 | Business Intelligence | BI·OLAP·Self BI |
| DG | 데이터거버넌스 | Data Governance | 메타·품질·흐름 |
| IM | IT서비스 및 업무지원 | Information Management | 공통·FW·배포·단말·배치·지원 |

---

# 5. 코드 식별 원칙

## FIG-AC-05. Canonical Identification

```text
Application Group
       +
Application Code
       =
Canonical Business Application Key
```

예:

```text
MP-CO
MP-IC
RD-SR
AD-SR
BI-PT
DG-DQ
IM-FW
```

### MUST

L2 코드만 독립적으로 저장/표기하지 않는다.

### SHOULD

문서·CMDB·Architecture Model에서는:

```text
<APP_GROUP>-<APP_CODE>
```

형태의 Display Key를 병행한다.

---

# 6. MP — 마케팅플랫폼

## FIG-AC-06. MP Classification

```text
MP — Marketing Platform
│
├─ CO Common
├─ IC Integration Customer
├─ PC Private Customer
├─ BC Business Customer
├─ MS Mini SingleView
├─ SA Sale
├─ PD Product
├─ CM Campaign
├─ EB EBM
├─ EP Event Processing
├─ BP Behavior Information Processing
├─ BD Customer Behavior Data
├─ SS Sales Support
├─ CS Customer Service
├─ CT Contents
└─ MG Message
```

---

# 7. MP 업무 코드 상세
## TEXT ARCHITECTURE 보완 — MP Code Set

```text
MP
├─ CO IC PC BC MS
├─ SA PD
├─ CM EB
├─ EP BP BD
└─ SS CS CT MG
```


| 그룹 | 코드 | 한글명 | 영문명 | 상태 |
|---|---|---|---|---|
| MP | CO | 공통 | Common | `[BASELINE]` |
| MP | IC | 통합고객 | Integration Customer | `[BASELINE]` |
| MP | PC | 개인고객 | Private Customer | `[BASELINE]` |
| MP | BC | 기업고객 | Business Customer | `[BASELINE]` |
| MP | MS | 미니 싱글뷰 | Mini SingleView | `[BASELINE]` |
| MP | SA | 상담판매 | Sale | `[BASELINE]` |
| MP | PD | 통합상품 | Product | `[BASELINE]` |
| MP | CM | 캠페인 | Campaign | `[BASELINE]` |
| MP | EB | EBM | EBM | `[BASELINE]` |
| MP | EP | 실시간 처리 | Event Processing | `[BASELINE]` |
| MP | BP | 행동정보 처리 | Behavior Information Processing | `[BASELINE]` |
| MP | BD | 고객 행동 데이터 | Customer Behavior Data | `[BASELINE]` |
| MP | SS | 영업지원 | Sales Support | `[BASELINE]` |
| MP | CS | CS | Customer Service | `[BASELINE]` |
| MP | CT | 컨텐츠 | Contents | `[BASELINE]` |
| MP | MG | 메시지 | Message | `[BASELINE]` |

---

# 8. MP 책임 그룹

## FIG-AC-07. MP Responsibility Group

```text
Common
└─ CO

Customer
├─ IC
├─ PC
├─ BC
└─ MS

Sales / Product
├─ SA
└─ PD

Campaign
├─ CM
└─ EB

Real-time / Behavior
├─ EP
├─ BP
└─ BD

Support / Contact
├─ SS
├─ CS
├─ CT
└─ MG
```

---

# 9. MP System Group Mapping

## FIG-AC-08. MP → Runtime Role

```text
MP-CO/IC/PC/BC/SA/PD/CM/EB/SS/CS/CT/MG
        ↓
Marketing WEB / WAS

MP-MS
        ↓
Mini SingleView WEB / WAS

MP-EP
        ↓
Real-time Processing

MP-BP
        ↓
Behavior Processing

MP-BD
        ↓
Customer Behavior Data
```

System Group은 코드 자체와 동일하지 않으며 배치/실행 책임 Mapping이다.

---

# 10. RD — RDW

## FIG-AC-09. RD Classification

```text
RD — Real-time Data Warehouse
│
├─ CO Common
├─ SR Source of Record
├─ ZD Zipped Data
├─ RM Report Data Mart
└─ FA Feedback Area
```

---

# 11. RD 업무 코드 상세
## TEXT ARCHITECTURE 보완 — RD Code Set

```text
RD
├─ CO
├─ SR
├─ ZD
├─ RM
└─ FA
```


| 그룹 | 코드 | 한글명 | 영문명 | 상태 |
|---|---|---|---|---|
| RD | CO | 공통 | Common | `[BASELINE]` |
| RD | SR | 실시간 SoR | Source of Record | `[BASELINE]` |
| RD | ZD | 준실시간요약집계 | Zipped Data | `[BASELINE]` |
| RD | RM | 준실시간보고서마트 | Report Data Mart | `[BASELINE]` |
| RD | FA | 피드백 | Feedback Area | `[BASELINE]` |

---

# 12. RD 역할

```text
Near Real-time
Operational Information
Information Provision
Feedback
```

---

# 13. AD — ADW

## FIG-AC-10. AD Classification — Current Architecture Baseline

```text
AD — Analytical Data Warehouse
│
├─ CO Common
├─ SR Analytical Source of Record
├─ ZD Analytical Integrated Summary
├─ UM Unit-business Mart
├─ RM Report Data Mart
├─ FA Feedback Area
└─ DA Analysis Assistance
```

---

# 14. AD 업무 코드 상세 — 현재 Architecture Baseline
## TEXT ARCHITECTURE 보완 — AD Code Set

```text
AD
├─ CO
├─ SR
├─ ZD
├─ UM
├─ RM
├─ FA
└─ DA
```


| 그룹 | 코드 | 한글명 | 영문/의미 | 상태 |
|---|---|---|---|---|
| AD | CO | 공통 | Common | `[BASELINE]` |
| AD | SR | 분석 SoR | Source of Record | `[BASELINE]` |
| AD | ZD | 분석통합요약집계 | Zipped Data Area | `[BASELINE]` |
| AD | UM | 분석단위업무마트 | Unit-business Mart | `[BASELINE]` |
| AD | RM | 분석보고서마트 | Report Data Mart | `[BASELINE]` |
| AD | FA | 피드백 | Feedback Area | `[BASELINE]` |
| AD | DA | 분석지원 | DW Analysis Assistance | `[BASELINE]` |

---

# 15. AD 과거 개발표준과의 코드 충돌

## FIG-AC-11. AD Conflict

```text
Older Development Standard
│
├─ CO
├─ SF  분석 SoR 파일
├─ SL  분석 SoR 파일레거시
├─ SC  분석 SoR 복제
├─ ZD
├─ US  분석단위업무표준마트
├─ UM
├─ RM
├─ FA
└─ AS  분석보고서지원

             ≠

Current Architecture Baseline
│
├─ CO
├─ SR
├─ ZD
├─ UM
├─ RM
├─ FA
└─ DA
```

### 판정

```text
[CONFLICT]
```

### 적용원칙

본 정의서의 **현재 Baseline**은 최신 Architecture 분류체계의:

```text
CO / SR / ZD / UM / RM / FA / DA
```

를 사용한다.

다만 다음 과거코드는 삭제하지 않고 Migration/Mapping 검토대상으로 둔다.

```text
SF / SL / SC / US / AS
```

---

# 16. RD-SR와 AD-SR 주의

## FIG-AC-12. Same L2 Different Meaning

```text
RD-SR
= 실시간 SoR

AD-SR
= 분석 SoR
```

따라서:

```text
SR
```

단독으로는 유일 식별자가 아니다.

---

# 17. BI — BI 포탈

## FIG-AC-13. BI Classification — Current Architecture Baseline

```text
BI — Business Intelligence
│
├─ PT Portal
├─ CR Credit Result
├─ OA Online Analysis Process
├─ SB Self Business Intelligence
└─ UI UI/UX
```

---

# 18. BI 업무 코드 상세
## TEXT ARCHITECTURE 보완 — BI Code Set

```text
BI
├─ PT
├─ CR
├─ OA
├─ SB
└─ UI
```


| 그룹 | 코드 | 한글명 | 영문명 | 상태 |
|---|---|---|---|---|
| BI | PT | BI 포탈 | Portal | `[BASELINE]` |
| BI | CR | 신용실적 | Credit Result | `[BASELINE]` |
| BI | OA | OLAP | Online Analysis Process | `[BASELINE]` |
| BI | SB | Self BI | Self Business Intelligence | `[BASELINE]` |
| BI | UI | 신 BI 포털 UI/UX | UI/UX | `[BASELINE]` |

---

# 19. BI 과거 코드 충돌

## FIG-AC-14. BI Portal Conflict

```text
Older Development Standard
BI-PO
Portal

       ≠

Current Architecture Baseline
BI-PT
Portal
```

### 판정

```text
[CONFLICT]
```

신규 코드 채번은 현재 Baseline의:

```text
BI-PT
```

를 사용하고, `BI-PO`의 기존 사용현황은 Migration Inventory로 확인한다.

---

# 20. BI 세부 기능 코드 과거안

Older Development Standard에는 BI 내부 기능으로 다음이 존재한다.

```text
P Package
C Customizing
U Public Utility
O Overall Performance
S Savings Volume
L Loan Amount
D Digital Performance
A Account Performance
P Policy Funds
W Workload Management
M Overall Merit
E Etc Performance
```

### 상태

```text
[HISTORICAL / NOT CURRENT L3 BASELINE]
```

현재 Architecture 분류자료는 L3를:

```text
1 byte
업무개발팀 분석·설계 단계 반영
```

으로 두고 있으므로 자동 병합하지 않는다.

---

# 21. DG — 데이터거버넌스

## FIG-AC-15. DG Classification

```text
DG — Data Governance
│
├─ CO Common
├─ BM Biz-Meta System
├─ DQ Data Quality
└─ DL Data Lineage
```

---

# 22. DG 업무 코드 상세
## TEXT ARCHITECTURE 보완 — DG Code Set

```text
DG
├─ CO
├─ BM
├─ DQ
└─ DL
```


| 그룹 | 코드 | 한글명 | 영문명 | 상태 |
|---|---|---|---|---|
| DG | CO | 공통 | Common | `[BASELINE]` |
| DG | BM | 비즈메타 | Biz-Meta System | `[BASELINE]` |
| DG | DQ | 데이터품질 | Data Quality | `[BASELINE]` |
| DG | DL | 데이터흐름 | Data Lineage | `[BASELINE]` |

---

# 23. DG 역할 구분

```text
BM
= Metadata

DQ
= Quality

DL
= Lineage / Data Flow
```

---

# 24. IM — IT 서비스 및 업무지원

## FIG-AC-16. IM Classification

```text
IM — Information Management
│
├─ AM Architecture Management
├─ SC System Common
├─ DP Deployment
├─ FW Framework
├─ LB Library
├─ SM Source Code Version Management
├─ XM UI/UX Management
├─ XD UI/UX Deployment
├─ BJ Batch Job Processing
├─ CD CDC Gateway
├─ DT Data Transform Load
├─ RD Report Designer
└─ IG In Memory Data Grid
```

---

# 25. IM 업무 코드 상세
## TEXT ARCHITECTURE 보완 — IM Code Set

```text
IM
├─ AM SC DP FW LB SM
├─ XM XD
├─ BJ CD DT
├─ RD
└─ IG
```


| 그룹 | 코드 | 한글명 | 영문명 | 상태 |
|---|---|---|---|---|
| IM | AM | 아키텍처 관리 | Architecture Management | `[BASELINE]` |
| IM | SC | 시스템 공통 | System Common | `[BASELINE]` |
| IM | DP | 배포 | Deployment | `[BASELINE]` |
| IM | FW | 프레임워크 | Framework | `[BASELINE]` |
| IM | LB | 라이브러리 | Library | `[BASELINE]` |
| IM | SM | 소스 코드 버전 관리 | Source Code Version Management | `[BASELINE]` |
| IM | XM | 정보단말 관리 | UI/UX Management | `[BASELINE]` |
| IM | XD | 정보단말 배포 | UI/UX Deployment | `[BASELINE]` |
| IM | BJ | 배치작업 처리 | Batch Job Processing | `[BASELINE]` |
| IM | CD | 실시간 중계 | CDC Gateway | `[BASELINE]` |
| IM | DT | 데이터 치환 적재 | Data Transform Load | `[BASELINE]` |
| IM | RD | 보고서 디자이너 | Report Designer | `[BASELINE]` |
| IM | IG | 거래 공통 메모리 | In Memory Data Grid | `[BASELINE]` |

---

# 26. IM 책임 그룹

## FIG-AC-17. IM Responsibility

```text
Architecture / Standard
├─ AM
├─ SC
├─ FW
└─ LB

DevOps
├─ DP
└─ SM

Terminal
├─ XM
└─ XD

Runtime / Data Support
├─ BJ
├─ CD
├─ DT
└─ IG

Report
└─ RD
```

---

# 27. L3 기능 코드

## FIG-AC-18. Function Code

```text
Application Group
  2 byte
      +
Application
  2 byte
      +
Function
  1 byte
```

### 현재 공식 상태

```text
기능 코드 = 1 byte
업무개발팀이 분석·설계 단계에서 반영
```

즉 현재 상위 Architecture 기준에서는 **코드 길이만 확정**되어 있고,
모든 업무의 기능코드 목록은 전수 확정되지 않았다.

---

# 28. Function Code 정의 원칙
## TEXT ARCHITECTURE 보완 — Function Code Governance

```text
App Group + App Code
        ↓
Function Candidate
        ↓
Duplicate / Meaning Check
        ↓
Owner Review
        ↓
Registry Approval
```


### MUST

- 대구분+업무구분 하위에서 유일해야 한다.
- 동일 기능은 동일 코드 사용을 원칙으로 한다.
- 승인 Registry에 등록한다.

### MUST NOT

- 개발자가 임의 채번하지 않는다.
- 화면번호와 기능코드를 혼동하지 않는다.
- Runtime 유형을 Function Code에 억지로 포함하지 않는다.

---

# 29. Function Registry Template

```yaml
function:
  appGroup: MP
  applicationCode: IC
  functionCode: A
  koreanName:
  englishName:
  description:
  owner:
  status:
  effectiveDate:
```

---

# 30. 전체 Application Code Master

## FIG-AC-19. Current Master

```text
MP
CO IC PC BC MS SA PD CM EB EP BP BD SS CS CT MG

RD
CO SR ZD RM FA

AD
CO SR ZD UM RM FA DA

BI
PT CR OA SB UI

DG
CO BM DQ DL

IM
AM SC DP FW LB SM XM XD BJ CD DT RD IG
```

---

# 31. 총 코드 건수

현재 Baseline 기준 L2 업무코드 수:

```text
MP = 16
RD = 5
AD = 7
BI = 5
DG = 4
IM = 13
────────
합계 = 50
```

주의:

동일 코드 문자열이 여러 그룹에 존재하므로 전사 Unique L2 Code 개수와는 다르다.

---

# 32. Canonical Application Key

## FIG-AC-20. Canonical Key

```text
<GROUP>-<APP>
```

예:

```text
MP-IC
RD-SR
AD-SR
BI-PT
DG-BM
IM-FW
```

Architecture Model/CMDB의 Join Key 후보로 권고한다.

---

# 33. Application Classification Key

## FIG-AC-21. 5-byte Classification

```text
MP + IC + A
=
MPICA
```

용도:

```text
Program Prefix
Package Mapping
Resource Mapping
Service Naming
```

---

# 34. Program ID와의 연결

## FIG-AC-22. Application Code → Program ID

```text
MP | IC | A | 0001
│    │    │    │
│    │    │    └─ Program / Screen Number
│    │    └────── Function
│    └─────────── Application
└──────────────── Application Group
```

프로젝트 개발표준 예:

```text
mpica0001
```

### 주의

PDMG Source Reference는:

```text
mgcoa9001
```

처럼 별도 Major/Business Prefix를 사용한다.

---

# 35. NSIGHT Application Code와 PDMG Code 차이

## FIG-AC-23. Target vs Reference

```text
NSIGHT Application Classification
MP / RD / AD / BI / DG / IM
          │
          │ Target/Baseline
          ▼
Application Code Registry

PDMG Current Source
mg + co + a + 9001
          │
          │ AS-IS Reference
          ▼
mgcoa9001
```

### 중요

```text
MP
≠
mg
```

이라고 즉시 단정할 수도 없고,

```text
MP = mg
```

라고 자동 치환해서도 안 된다.

### 판정

```text
[GAP / MAPPING REQUIRED]
```

---

# 36. PDMG Program Reference

PDMG 대표:

```text
mgcoa9000
mgcoa9001
mgcoa9100
mgcoa9999
```

현재 PDMG 코드축은 Source Evidence로 사용하되,
NSIGHT Application Code Target과의 정식 Mapping이 필요하다.

---

# 37. ServiceId와의 연결

## FIG-AC-24. Program → ServiceId

```text
Program ID
MPICA0001
      ↓
Transaction Type + Sequence
      ↓
ServiceId
MPICA0001S0
```

개발표준 구조:

```text
2 + 2 + 1 + 4 + 1 + 1
```

---

# 38. Service Transaction Type

```text
S = 조회
C = 등록
U = 수정
D = 삭제
A = 혼합
R = 출력물 / Report
```

순번:

```text
0~9
A~Z
```

---

# 39. 화면 ID와의 연결

## FIG-AC-25. Screen ID

```text
App Group
 +
Application
 +
Function
 +
Screen Number
 +
Screen Type / Sequence
```

화면유형 예:

```text
M Main
V Sub/Detail
T Tab
P Popup
B Bottom
```

---

# 40. 화면 없는 프로그램

기존 개발표준에서는 화면식별 번호에:

```text
Z + sequence(3)
```

방식을 사용한다.

예:

```text
mpcoaZ000S0
```

### 상태

```text
[LEGACY/DEVELOPMENT STANDARD REFERENCE]
```

NSIGHT 최신 Program ID 정책과 정합 검토 필요.

---

# 41. Controller/Service Naming 연계

## FIG-AC-26. Class Naming

```text
Application Code
      ↓
Program ID
      ↓
Controller / Service / DAO
```

기존 개발표준 예:

```text
mpcoa0000Controller.java
mpcoa0000Service.java
```

---

# 42. PDMG Class Naming Reference

Current PDMG Reference:

```text
mgcoa9001Handler
mgcoa9001Facade
mgcoa9001Service
mgcoa9001DAO
```

### 판정

```text
[AS-IS REFERENCE]
```

---

# 43. Package Naming 연계

## FIG-AC-27. Package

```text
Application Group
  ↓
Application
  ↓
Function
  ↓
Package
```

PDMG Reference:

```text
MG / CO / A
→ nhnis.mg.co.a
```

NSIGHT Target Package는 승인 Application Code와 일치하도록 정규화해야 한다.

---

# 44. Mapper Resource 연계

```text
App Group / App / Function
        ↓
Mapper Resource Path
```

PDMG:

```text
rdw.mg.co.a/
```

---

# 45. SQL File Naming 연계

## FIG-AC-28. SQL File

기존 개발표준:

```text
<programId>-<DBMS>.xml
```

예:

```text
mpcoa0000-ORA.xml
```

DBMS:

```text
ORA = Oracle
MYS = MySQL
MSS = MS SQL Server
```

---

# 46. Report ID 연계

```text
<programId>R0
```

예:

```text
mpcoa0000R0
mpcoa0000R0.mrd
```

---

# 47. Application Code와 System Group

## FIG-AC-29. Application vs System Group

```text
Application Group
= Business Responsibility Classification
        ↓ maps to
System Group
= Runtime / Server Responsibility
```

둘은 같은 개념이 아니다.

---

# 48. System Group 예시

```text
MP
├─ Marketing WEB/WAS
├─ Mini SingleView
├─ Real-time Processing
├─ Behavior Processing
└─ Customer Behavior Data

RD
└─ RDW Platform

AD
└─ ADW Platform
```

---

# 49. Server Logical Base Name 예

기존 자료의 역할 예:

```text
sbrdco
sbadco
sbmpco
sbmpms
sbmpep
sbmpbp
sbmpbd
```

### 주의

이 값은 Application Code와 Hostname 전체 규칙을 동일시하기 위한 것이 아니라,
**논리 역할 Mapping Reference**다.

---

# 50. Application Code와 Data Subject

## FIG-AC-30. App ↔ Data Subject

```text
Application Group
      ↓
Application Code
      ↓
Data Subject / Ownership
```

프로젝트 자료에서는 Application 분류코드가 Data Subject 분류에도 재사용된다.

---

# 51. RD Data Subject

```text
RD
CO / SR / ZD / RM / FA
```

---

# 52. AD Data Subject

```text
AD
CO / SR / ZD / UM / RM / FA / DA
```

---

# 53. BI Data Subject

```text
BI
PT / CR / OA / SB / UI
```

---

# 54. DG Data Subject

```text
DG
CO / BM / DQ / DL
```

---

# 55. Application Code와 Interface

## FIG-AC-31. Application → Interface

```text
Application Key
  ↓
ServiceId
  ↓
InterfaceId
  ↓
Target Application Key
```

InterfaceId는 Application Code와 별도 식별자다.

---

# 56. Code Registry SSOT

## FIG-AC-32. Registry SSOT

```text
Approved Code Registry / CMDB
         ↓
Architecture Model
         ↓
Development
         ↓
Source
         ↓
Runtime / Monitoring
```

---

# 57. Registry 필수 필드

```yaml
applicationCode:
  appGroup:
  appGroupName:
  appGroupEnglish:
  applicationCode:
  applicationName:
  applicationEnglish:
  owner:
  runtimeType:
  systemGroup:
  dataSubject:
  status:
  effectiveDate:
  deprecatedDate:
  evidence:
```

---

# 58. Code 상태

## FIG-AC-33. Lifecycle

```text
PROPOSED
   ↓
APPROVED
   ↓
ACTIVE
   ↓
DEPRECATED
   ↓
RETIRED
```

---

# 59. 신규 코드 채번

## FIG-AC-34. New Code Process

```text
Business Need
   ↓
Existing Code?
 ├─ YES → Reuse
 └─ NO
      ↓
   Candidate Code
      ↓
   Conflict Check
      ↓
   Architecture Review
      ↓
   Registry Approval
      ↓
   Active
```

---

# 60. 코드 변경

### MUST NOT

이미 사용 중인 코드의 의미를 변경하여 재사용하지 않는다.

```text
Code A = Meaning X
       ↓
Meaning Y로 변경

X
```

---

# 61. 코드 폐기

## FIG-AC-35. Deprecation

```text
ACTIVE
  ↓
DEPRECATED
  ↓
Migration
  ↓
No Usage
  ↓
RETIRED
```

폐기코드는 즉시 재사용하지 않는다.

---

# 62. 코드 재사용 금지
## TEXT ARCHITECTURE 보완 — Code Retirement

```text
ACTIVE
  ↓
DEPRECATED
  ↓
RETIRED
  ↓
Reserved History

RETIRED ──X──► New Meaning
```


이력 Trace를 보존하기 위해 Retired Code의 재할당을 원칙적으로 금지한다.

---

# 63. 코드 충돌검사

## FIG-AC-36. Conflict Check

```text
Candidate
  ↓
App Group Registry
  ↓
Application Registry
  ↓
Function Registry
  ↓
Program / Service Registry
  ↓
PASS / CONFLICT
```

---

# 64. Application Group 변경

대구분 이동은 사실상 Application Identity 변경이므로 영향분석 대상이다.

```text
MP-XX
   ↓
BI-XX
```

단순 Rename으로 처리하지 않는다.

---

# 65. 업무코드 변경 영향

## FIG-AC-37. Impact

```text
Application Code Change
  ↓
Program ID
  ↓
ServiceId
  ↓
Package
  ↓
Mapper
  ↓
Interface
  ↓
Deployment / Monitoring
```

---

# 66. Function Code 변경 영향

```text
Function Code
  ↓
Program Prefix
  ↓
Source / Resource
```

---

# 67. 정상 예

## FIG-AC-38. Valid Classification

```text
MP + IC + A
RD + SR + A
AD + UM + B
DG + DQ + A
IM + FW + A
```

---

# 68. 금지 예

```text
IC + A
```

대구분 누락.

```text
SR + A
```

RD/AD 구분 불가.

```text
DW + SR + A
```

승인되지 않은 대구분.

```text
MP + ZZ + A
```

미등록 업무코드.

---

# 69. 코드 대소문자

Architecture 문서에서는:

```text
MP / IC / A
```

와 같이 Uppercase 표시를 표준 Display로 사용한다.

Program/Source Naming에서는 개발표준에 따라:

```text
mpica0001
```

처럼 lowercase Projection이 가능하다.

---

# 70. Display Code vs Source Code

## FIG-AC-39. Projection

```text
Architecture Code
MP-IC-A

      ↓ lowercase projection

Source Prefix
mpica
```

---

# 71. PDMG Projection 주의

PDMG:

```text
mg-co-a
→ mgcoa
```

NSIGHT:

```text
mp-ic-a
→ mpica
```

서로 다른 분류축이므로 정식 Mapping 없이 변환하지 않는다.

---

# 72. Code Mapping Registry

## FIG-AC-40. Mapping

```text
Source Scheme
PDMG / Legacy
       ↓
Mapping Registry
       ↓
Target Scheme
NSIGHT Application Code
```

Template:

```yaml
mapping:
  sourceScheme:
  sourceCode:
  targetAppGroup:
  targetApplication:
  targetFunction:
  status:
  rationale:
  owner:
```

---

# 73. Legacy Code 관리

```text
Legacy Code
  ↓
Canonical Mapping
  ↓
Target Code
```

Legacy를 삭제하기보다 Alias/Mapping History를 유지한다.

---

# 74. 현재 주요 Conflict Register
## TEXT ARCHITECTURE 보완 — Conflict Resolution

```text
Historical Code Set
        ↓ compare
Current Architecture Baseline
        ↓
CONFLICT
        ↓
Usage Inventory
        ↓
Mapping / Migration / ADR
```


| ID | 영역 | 구형 | 현재 Baseline | 상태 |
|---|---|---|---|---|
| CONFLICT-AC-01 | MP | 일부 자료에 PC/BC/EB 누락 | PC/BC/EB 포함 | `[CONFLICT]` |
| CONFLICT-AC-02 | AD | SF/SL/SC/US/AS | SR/UM/DA 중심 | `[CONFLICT]` |
| CONFLICT-AC-03 | BI Portal | PO | PT | `[CONFLICT]` |
| CONFLICT-AC-04 | BI L3 | 세부 기능코드 다수 | L3 분석/설계 단계 반영 | `[CONFLICT]` |
| GAP-AC-01 | PDMG | MG 계열 | NSIGHT MP 계열 | `[MAPPING REQUIRED]` |

---

# 75. MP 과거 자료 누락 주의

구형 개발표준의 MP 코드표에는 일부 자료에서:

```text
PC
BC
EB
```

가 보이지 않지만 최신 Architecture 분류체계에는 포함된다.

본 정의서는 최신 Architecture Baseline을 따른다.

---

# 76. Code Evidence 우선순위

## FIG-AC-41. Evidence Priority

```text
1 Approved Code Registry / CMDB
        ↓
2 Current Architecture Definition
        ↓
3 Current Development Standard
        ↓
4 Source / Runtime Mapping
        ↓
5 Historical Standard
```

충돌 시 상위 Evidence를 기준으로 하되 충돌 이력은 남긴다.

---

# 77. Application Code와 Naming Standard 관계

```text
Application Code Definition
= 어떤 코드를 사용할 것인가?

Naming Development Standard
= 그 코드를 Program/Class/Resource에 어떻게 투영할 것인가?
```

---

# 78. Application Code와 Application Architecture 관계

```text
Application Architecture
= 책임과 경계

Application Code
= 그 책임의 Canonical Identifier
```

---

# 79. Application Code와 Data Architecture 관계

```text
Application Code
  ↓
Data Subject / Owner
```

---

# 80. Application Code와 Infrastructure 관계

```text
Application Code
  ↓
System Group
  ↓
Logical Node / Host Mapping
```

---

# 81. Application Code와 Runtime Evidence

## FIG-AC-42. End-to-End Trace

```text
MP-IC-A
  ↓
ProgramId
  ↓
ServiceId
  ↓
Source
  ↓
Artifact
  ↓
Deployment
  ↓
GUID
  ↓
Runtime Evidence
```

---

# 82. Architecture Model Entity

```yaml
application:
  canonicalKey: MP-IC
  appGroup: MP
  applicationCode: IC
  koreanName: 통합고객
  englishName: Integration Customer
  functions:
  systemGroups:
  runtimeTypes:
  dataSubjects:
  owner:
  status:
```

---

# 83. Code Validation Rules

## FIG-AC-43. Rules

```text
R-APPGROUP-REGISTERED
R-APPCODE-REGISTERED
R-APP-CANONICAL-KEY
R-FUNCTION-REGISTERED
R-PROGRAM-CODE-MATCH
R-SERVICEID-CODE-MATCH
R-PACKAGE-CODE-MATCH
R-MAPPER-CODE-MATCH
```

---

# 84. Static Validation

```text
Program ID
  ↓ parse
App Group / App / Function
  ↓
Registry
  ↓
PASS / FAIL
```

---

# 85. ServiceId Validation

```text
ServiceId
  ↓
Program Prefix
  ↓
Application Code Registry
```

---

# 86. Package Validation

```text
Source Package
  ↓
Classification Code
  ↓
Registry
```

---

# 87. Mapper Validation

```text
Mapper Resource
  ↓
Program / App Code
  ↓
DAO / Package
```

---

# 88. CMDB Validation

## FIG-AC-44. CMDB Conformance

```text
Architecture Code Registry
      ↓ compare
CMDB
      ↓ compare
Source
      ↓ compare
Runtime Inventory
```

---

# 89. CI Gate

## FIG-AC-45. CI Gate

```text
Commit
  ↓
Application Code Scanner
  ↓
Program / ServiceId
  ↓
Package / Mapper
  ↓
Registry Match?
  ├─ NO → FAIL
  └─ YES → Build Continue
```

---

# 90. 신규 Application Code Checklist

```text
[ ] App Group
[ ] 2-byte Code
[ ] 한글명
[ ] 영문명
[ ] 책임
[ ] Owner
[ ] 기존코드 충돌
[ ] Function Scope
[ ] System Group
[ ] Data Subject
[ ] Runtime Type
[ ] Registry 승인
```

---

# 91. 신규 Function Code Checklist

```text
[ ] Parent App Group
[ ] Parent App Code
[ ] 1-byte Function
[ ] 의미
[ ] Owner
[ ] Program Impact
[ ] Duplicate Check
[ ] Registry
```

---

# 92. 코드 변경 Checklist

```text
[ ] Existing Usage
[ ] Program
[ ] ServiceId
[ ] Package
[ ] Mapper
[ ] Interface
[ ] DB
[ ] Monitoring
[ ] Migration
[ ] ADR
```

---

# 93. Go-Live Blocker

## FIG-AC-46. Go-Live Block

```text
Unregistered App Group
       OR
Unregistered App Code
       OR
Duplicate Function Code
       OR
Program Code Mismatch
       OR
ServiceId Mapping Mismatch
       OR
Package/Mapper Drift
       OR
Legacy Mapping Missing
       ↓
GO-LIVE BLOCK
```

---

# 94. GAP Register
## TEXT ARCHITECTURE 보완 — GAP Lifecycle

```text
Expected Code Standard
        ↓ compare
Current Registry / Source
        ↓
GAP
        ↓
Owner / Action / ADR / Evidence
```


| ID | GAP | 조치 |
|---|---|---|
| GAP-AC-01 | PDMG `mg` ↔ NSIGHT `MP` Mapping | Mapping Registry/ADR |
| GAP-AC-02 | L3 기능코드 전수 미정의 | 업무분석/설계 반영 |
| GAP-AC-03 | AD 구/신 코드 충돌 | Migration Inventory |
| GAP-AC-04 | BI PO/PT 충돌 | 사용현황 확인 및 정리 |
| GAP-AC-05 | MP 구형 문서 누락코드 | 최신 Baseline으로 정렬 |
| GAP-AC-06 | CMDB Code SSOT 확인 필요 | CMDB Sync |
| GAP-AC-07 | Code Scanner 자동화 | CI Rule 구현 |
| GAP-AC-08 | Owner/RACI 전수 지정 | Governance 보완 |

---

# 95. Risk Register
## TEXT ARCHITECTURE 보완 — Code Risk

```text
Code Weakness
  ↓
Collision / Ambiguity / Drift
  ↓
Source / Runtime Impact
  ↓
Mitigation
```


| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-AC-01 | 동일 L2 코드 단독 사용 | High |
| RISK-AC-02 | 미등록 코드 임의 생성 | Critical |
| RISK-AC-03 | 구/신 코드 혼용 | High |
| RISK-AC-04 | PDMG↔NSIGHT 자동치환 | Critical |
| RISK-AC-05 | Program/ServiceId Prefix Drift | Critical |
| RISK-AC-06 | Package/Mapper 분류 불일치 | High |
| RISK-AC-07 | 폐기코드 재사용 | High |
| RISK-AC-08 | Owner 없는 코드 | High |

---

# 96. ADR 후보

```text
ADR-AC-01 Application Code SSOT
ADR-AC-02 PDMG↔NSIGHT Code Mapping
ADR-AC-03 Function Code Governance
ADR-AC-04 AD Legacy Code Migration
ADR-AC-05 BI PO→PT Migration
ADR-AC-06 Program/ServiceId Projection
ADR-AC-07 Code Lifecycle / Reuse
ADR-AC-08 CI Code Conformance
```

---

# 97. Application Code Completion Gate

## FIG-AC-47. Completion Gate

```text
6 App Groups Defined?
   ↓ YES
L2 Codes Defined?
   ↓ YES
Current Conflict Preserved?
   ↓ YES
L3 Governance Defined?
   ↓ YES
Program Projection Defined?
   ↓ YES
ServiceId Projection Defined?
   ↓ YES
Package/Mapper Projection Defined?
   ↓ YES
Registry/CMDB Defined?
   ↓ YES
Validation/CI Defined?
   ↓ YES
Mapping/GAP Defined?
   ↓ YES
APPLICATION CODE STANDARD PASS
```

---

# 98. 최종 코드 구조

## FIG-AC-48. Final Code Map

```text
APPLICATION GROUP
2 byte
MP / RD / AD / BI / DG / IM
       ↓
APPLICATION
2 byte
CO / IC / SR / PT / DQ / FW ...
       ↓
FUNCTION
1 byte
Analysis / Design Registry
       ↓
PROGRAM NUMBER
4 byte
0000~9999
       ↓
TRANSACTION
2 byte
S0 / C0 / U0 / D0 / A0 / R0
```

---

# 99. 최종 Master Table
## TEXT ARCHITECTURE 보완 — Application Code Master

```text
MP → CO IC PC BC MS SA PD CM EB EP BP BD SS CS CT MG
RD → CO SR ZD RM FA
AD → CO SR ZD UM RM FA DA
BI → PT CR OA SB UI
DG → CO BM DQ DL
IM → AM SC DP FW LB SM XM XD BJ CD DT RD IG
```


| Group | Application Codes |
|---|---|
| **MP** | CO, IC, PC, BC, MS, SA, PD, CM, EB, EP, BP, BD, SS, CS, CT, MG |
| **RD** | CO, SR, ZD, RM, FA |
| **AD** | CO, SR, ZD, UM, RM, FA, DA |
| **BI** | PT, CR, OA, SB, UI |
| **DG** | CO, BM, DQ, DL |
| **IM** | AM, SC, DP, FW, LB, SM, XM, XD, BJ, CD, DT, RD, IG |

---

# 100. 최종 결론

## FIG-AC-49. Application Code Final

```text
Business Responsibility
        ↓
Application Group
        ↓
Application Code
        ↓
Function
        ↓
Program ID
        ↓
ServiceId
        ↓
Package / Mapper
        ↓
Runtime Evidence
```

> **어플리케이션 코드의 목적은 업무를 축약하는 것이 아니라, 차세대 정보계의 업무 책임을 Program·ServiceId·Package·System Group·Data Subject·Runtime Evidence와 연결하는 Canonical Classification Key를 제공하는 것이다.**

> **현재 Architecture Baseline의 Application Group은 `MP / RD / AD / BI / DG / IM`이며, 업무코드까지 결합한 `MP-IC`, `RD-SR`, `AD-SR`, `BI-PT`, `DG-DQ`, `IM-FW`와 같은 Key를 업무 식별의 기본 단위로 사용한다.**

> **기존 개발표준과 최신 Architecture 자료의 AD·BI·MP 코드 차이는 숨기지 않고 Conflict로 관리하며, PDMG의 `mg` 계열 코드 역시 NSIGHT의 `MP` 계열과 자동 치환하지 않고 Mapping/ADR을 통해 정식 정합시켜야 한다.**
