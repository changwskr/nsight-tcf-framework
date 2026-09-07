# NSIGHT / PDMG 아키텍처 정의서
# 02. BIG PICTURE — Application / Data / System Boundary 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-TYPE2-BIGPICTURE-02-DETAIL`  
> Architecture Level: **BIG PICTURE**  
> PPT 공식 범위: **1. 아키텍처 정의 > 1.2 어플리케이션 분류 체계 / 1.3 데이터 주제영역 정의 / 1.4 시스템 아키텍처 구성**  
> 문서 상태: **Draft / Evidence-First / PPT-Aligned / Detailed Baseline**  
> 작성 기준일: **2026-08-31**  
> 선행 문서: `01. VISION — Architecture Vision & Strategy 상세본`  
> 후속 문서: `03. LOGICAL — 논리 기술 아키텍처`  
> 작성 원칙: **PPT-First / Responsibility-First / Boundary-Controlled / Inventory-Driven / AS-IS·TO-BE 분리**  
> 핵심 질문: **무엇을 구축하고, Application·System·Data의 책임을 어떤 공간에 배치하며, 어떤 경계와 표준 연결을 통해 전체 시스템을 구성할 것인가?**

---

# 0. 문서 사용법

BIG PICTURE는 서버나 제품을 먼저 나열하는 단계가 아니다.

이 장에서 고정해야 하는 것은 다음이다.

```text
무슨 업무/서비스를 제공하는가
        ↓
어떤 Application Domain이 책임지는가
        ↓
어떤 System Group에 배치되는가
        ↓
어떤 Data Subject를 소유/소비하는가
        ↓
어떤 Boundary를 통해 연결되는가
        ↓
어떤 Logical Server Candidate가 필요한가
```

즉:

```text
Responsibility
   ↓
Classification
   ↓
Ownership
   ↓
Boundary
   ↓
Mapping
```

을 정의한다.

---

# 0.1 Evidence 상태 태그

| 태그 | 의미 |
|---|---|
| `[PPT-TOC]` | PPT 공식 목차 |
| `[PPT-BODY]` | PPT 본문 장표에서 직접 확인 |
| `[FACT]` | 공식 자료/정의서/소스에서 직접 확인 |
| `[CONFIRMED]` | 복수 근거 일치 |
| `[BASELINE]` | 프로젝트 기준선 |
| `[WORKING BASELINE]` | 반복 사용 중이나 최종 승인 여부 별도 |
| `[AS-IS]` | 현재 구현/현행 |
| `[TO-BE]` | 목표 |
| `[PROPOSED]` | 제안 |
| `[GAP]` | 목표와 현재/자료 간 차이 |
| `[OPEN]` | 추가 결정 필요 |
| `[UNKNOWN]` | 근거 부족 |
| `[CONFLICT]` | 자료 간 불일치 |
| `[CROSS-REFERENCE]` | 다른 장/자료의 상세를 참조 |

---

# 0.2 Evidence Register

| ID | 근거 | 본 장 사용 목적 | 상태 |
|---|---|---|---|
| EV-BP-01 | TYPE2/PPT 정합 마스터 프롬프트 | PPT 1.2~1.4 공식 구조·작성 규칙 | `[WORKING BASELINE]` |
| EV-BP-02 | `01_아키텍처정의_정의서.md` | MP/RD/AD/BI/DG/IM 분류코드, 데이터 주제영역 6개 트리, System Group 역할 예 | `[FACT/BASELINE]` |
| EV-BP-03 | `NSIGHT_PDMG_아키텍처_정의서_II_BigPicture_SystemBoundary` | Enterprise Context, 5대 서비스 책임, Boundary, Event/Data/Interface 구조 | `[CURRENT BASELINE DRAFT]` |
| EV-BP-04 | `NSIGHT_시스템간인터페이스_원칙및추진방안_TOPDOWN` | 목적별 Integration Mechanism, P2P/Direct DB 금지 | `[WORKING BASELINE]` |
| EV-BP-05 | `NSIGHT_PDMG_아키텍처_정의서_VIII_Infrastructure_WAS_Capacity_HA_DR` | 주요 Physical Role을 Logical Candidate로 교차 확인 | `[PHYSICAL REFERENCE]` |
| EV-BP-06 | `NSIGHT_PDMG_아키텍처_정의서_III_PDMG_Module_Application_Architecture` | PDMG Reference 범위 | `[AS-IS REFERENCE]` |
| EV-BP-07 | `01 VISION 상세본` | Vision/NFR/Principle Handoff | `[CURRENT BASELINE DRAFT]` |

---

# 1. PPT 공식 구조

[PPT-TOC]

```text
1. 아키텍처 정의

1.2 어플리케이션 분류 체계
    ├─ 차세대 정보계 개념 아키텍처
    ├─ 어플리케이션 도메인 구성 정의
    ├─ 어플리케이션 분류 체계 (1/5~5/5)
    └─ 시스템 그룹 업무 구분 (1/6~6/6)

1.3 데이터 주제영역 정의
    └─ 데이터 주제영역 구성 정의 (1/2~2/2)

1.4 시스템 아키텍처 구성
    ├─ 전체 시스템 아키텍처 구조 정의
    └─ 주요 시스템 대상 서버 식별
```

TYPE2 Architecture Route:

```text
1.1 VISION
   ↓
1.2~1.4 BIG PICTURE
   ↓
2장 LOGICAL
```

---

# 2. 핵심 결론

BIG PICTURE의 핵심은 다음 문장으로 정의한다.

> **NSIGHT는 Application·Data·System의 책임을 다섯 개 상위 서비스 도메인과 데이터플랫폼 세부 영역으로 구분하고, 연결은 Channel·Integration·Data Boundary에서 통제하며, 업무목적에 따라 Online/Event/CDC/ETL/File 등 서로 다른 Integration Mechanism을 사용한다.**

상위 구조:

```text
[User / Channel]
        ↓
[Access / Channel Integration]
        ↓
[Information Application]
   ├─ Marketing Platform
   ├─ BI Portal
   ├─ Data Governance
   └─ IT Service & Support
        ↓
[Data Platform]
   ├─ RDW
   └─ ADW
        ↓
[Core / Related / External]
```

3대 통제:

```text
규격 표준
+
인터페이스 통제
+
논리/물리 자원 분리
```

---

# 3. VISION에서 전달받은 계약

VISION Output:

```text
Data-Centric
Real-time Reaction
Strategic Analytics
Responsibility Boundary
Standard Interface
Resource Isolation
Security
Observability
Traceability
Runtime Evidence
```

BIG PICTURE에서는 이를 **공간·분류·Ownership**으로 바꾼다.

```text
Vision Principle
      ↓
Domain
      ↓
Application
      ↓
System Group
      ↓
Data Subject
      ↓
Boundary
      ↓
Logical Server Candidate
```

---

# 4. 목적 / 범위 / 전제

## 4.1 목적

본 장은 다음을 확정한다.

1. Application Domain
2. Application Classification
3. System Group
4. Application ↔ System Mapping
5. Data Subject Area
6. RDW / ADW 책임
7. Enterprise/System Context
8. Channel / Access Boundary
9. Application / Data / External Boundary
10. Integration Mechanism 상위 분류
11. 주요 Logical Server Candidate
12. PDMG Reference Position

## 4.2 제외

다음은 후속 장에서 확정한다.

```text
Zone 상세             → 03 LOGICAL
Node/Component 상세   → 03 LOGICAL
Host/센터/대수        → 04 PHYSICAL
HW/SW/DB/Port         → 04 PHYSICAL
Header/전문/GUID      → 05 MECHANISM
Timeout/Retry 상세    → 05/06
Thread/TX Sequence    → 06 RUNTIME
```

---

# 5. Big Picture 전체 Text Architecture

```text
┌─────────────────────────────────────────────────────────────────────────────┐
│                           사용자 / 업무 채널                               │
│                                                                             │
│  내부 사용자                           고객 / 외부 접점                     │
│  ├─ 계정 단말                         ├─ Web                               │
│  ├─ 정보계 단말 / Package UI         ├─ Mobile                            │
│  └─ 분석/운영 사용자                 └─ SMS / PUSH / MAIL                 │
└───────────────────────────────┬─────────────────────────────────────────────┘
                                │
                Transaction / JSON / Event / File
                                ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                       Access / Integration Boundary                         │
│                                                                             │
│ MCA / 정보계 Web / BI Web / API Boundary / Collector / File Boundary      │
└───────────────────────────────┬─────────────────────────────────────────────┘
                                │
                                ▼
┌──────────────────────── Information Application ────────────────────────────┐
│                                                                             │
│ [MP] Marketing Platform                                                     │
│ [BI] BI Portal                                                              │
│ [DG] Data Governance                                                        │
│ [IM] IT Service & Business Support                                          │
│                                                                             │
└───────────────────────────────┬─────────────────────────────────────────────┘
                                │
                        API / Event / JDBC
                                ▼
┌──────────────────────────── Data Platform ──────────────────────────────────┐
│                                                                             │
│ [RD] RDW                                                                   │
│   Operational / Near Real-time / Report Mart                               │
│                                                                             │
│ [AD] ADW                                                                   │
│   Analytical / Aggregated / Unit Mart / Analysis Support                   │
│                                                                             │
└───────────────────────────────┬─────────────────────────────────────────────┘
                                │
                          CDC / ETL / File
                                ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                    Core / Related / Big Data / External                    │
│                                                                             │
│ 계정 Core · 계정 연계 · 경영관리 · 리스크 · 유관 · 계열사 · 외부기관      │
└─────────────────────────────────────────────────────────────────────────────┘

Cross-cutting:
Security / Standard / Observability / Operations / Traceability
```

---

# 6. 1.2 어플리케이션 분류 체계 — 기본 개념

Application Classification은 조직도나 제품 목록이 아니다.

```text
Application Group / Domain
       ↓
Business / Application
       ↓
Function
       ↓
Program / Service
```

목적:

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

# 7. 분류 3단 구조

프로젝트 Baseline:

```text
대구분(2)
  ↓
업무구분(2)
  ↓
기능(1)
```

예:

```text
MP + IC + A
=
MPICA
```

대구분:

```text
MP
RD
AD
BI
DG
IM
```

주의:

```text
DP
DW
```

같은 임의 단일 대구분으로 RD/AD를 합치지 않는다.

---

# 8. Application Group / Domain 정의

| 코드 | Domain | 핵심 책임 |
|---|---|---|
| MP | 마케팅플랫폼 | 고객·상담·상품·캠페인·실시간 행동·접점 |
| RD | RDW | 실시간/준실시간 SoR, 요약·보고·피드백 |
| AD | ADW | 분석 SoR, 통합·집계·마트·분석지원 |
| BI | BI 포탈 | BI·신용실적·OLAP·Self BI·UI/UX |
| DG | 데이터거버넌스 | 공통·비즈메타·품질·데이터흐름 |
| IM | IT서비스 및 업무지원 | 아키텍처·시스템공통·배포·FW·운영/단말/배치 |

---

# 9. MP — 마케팅플랫폼 상세 분류

[FACT/BASELINE]

| 코드 | 업무구분 |
|---|---|
| CO | 공통 |
| IC | 통합고객 |
| PC | 개인고객 |
| BC | 기업고객 |
| MS | 미니 싱글뷰 |
| SA | 상담판매 |
| PD | 통합상품 |
| CM | 캠페인 |
| EB | EBM |
| EP | 실시간 처리 |
| BP | 행동정보 처리 |
| BD | 고객 행동 데이터 |
| SS | 영업지원 |
| CS | CS |
| CT | 컨텐츠 |
| MG | 메시지 |

---

# 9.1 MP 책임 그룹

```text
Customer
├─ IC 통합고객
├─ PC 개인고객
├─ BC 기업고객
└─ MS 미니 싱글뷰

Sales / Product
├─ SA 상담판매
└─ PD 통합상품

Campaign
├─ CM 캠페인
└─ EB EBM

Real-time / Behavior
├─ EP 실시간 처리
├─ BP 행동정보 처리
└─ BD 고객 행동 데이터

Support / Contact
├─ SS 영업지원
├─ CS CS
├─ CT 컨텐츠
└─ MG 메시지

Common
└─ CO
```

---

# 9.2 MP Architecture 의미

Marketing Platform은 단일 WAS 하나가 아니다.

논리적으로 최소 다음 Workload를 구분한다.

```text
Online Business
Mini Single View
Campaign / EBM
Real-time Processing
Behavior Processing
Customer Behavior Data
Support / Message
```

이 구분은 후속 LOGICAL/PHYSICAL에서 자원 분리의 입력이 된다.

---

# 10. RD — RDW 상세 분류

[FACT/BASELINE]

| 코드 | 의미 |
|---|---|
| CO | 공통 |
| SR | 실시간 SoR |
| ZD | 준실시간 요약집계 |
| RM | 준실시간 보고서마트 |
| FA | 피드백 |

RD 핵심 책임:

```text
Near Real-time
Operational Information
Information Provision
Feedback
```

---

# 10.1 RD 책임 구조

```text
RDW
├─ CO Common
├─ SR Real-time SoR
├─ ZD Near-real-time Summary
├─ RM Near-real-time Report Mart
└─ FA Feedback
```

---

# 11. AD — ADW 상세 분류

[FACT/BASELINE]

| 코드 | 의미 |
|---|---|
| CO | 공통 |
| SR | 분석 SoR |
| ZD | 분석 통합요약집계 |
| UM | 분석 단위업무마트 |
| RM | 분석 보고서마트 |
| FA | 피드백 |
| DA | 분석지원 |

AD 핵심 책임:

```text
Analytics
Aggregation
Mart
Strategic Analysis
```

---

# 11.1 RD와 AD 동일 코드 주의

예:

```text
RD-SR
≠
AD-SR
```

업무구분 코드만으로는 유일 식별이 불가능하다.

반드시:

```text
대구분 + 업무구분
```

을 함께 사용한다.

---

# 12. BI — BI 포탈 상세 분류

[FACT/BASELINE]

| 코드 | 업무구분 |
|---|---|
| PT | BI포탈 |
| CR | 신용실적 |
| OA | OLAP |
| SB | Self BI |
| UI | 신BI포털 UI/UX |

구조:

```text
BI Portal
├─ PT BI포탈
├─ CR 신용실적
├─ OA OLAP
├─ SB Self BI
└─ UI 신BI포털 UI/UX
```

---

# 13. DG — 데이터거버넌스 상세 분류

[FACT/BASELINE]

| 코드 | 업무구분 |
|---|---|
| CO | 공통 |
| BM | 비즈메타 |
| DQ | 데이터품질 |
| DL | 데이터흐름 |

역할:

```text
Metadata
Quality
Lineage / Data Flow
Governance Control
```

DG는 대량 업무데이터 처리 플랫폼 자체가 아니다.

---

# 14. IM — IT서비스 및 업무지원 상세 분류

[FACT/BASELINE]

| 코드 | 업무구분 | 코드 | 업무구분 |
|---|---|---|---|
| AM | 아키텍처 관리 | XM | 정보단말 관리 |
| SC | 시스템 공통 | XD | 정보단말 배포 |
| DP | 배포 | BJ | 배치작업 처리 |
| FW | 프레임워크 | CD | 실시간 중계 |
| LB | 라이브러리 | DT | 데이터 치환 적재 |
| SM | 소스 버전관리 | RD | 보고서 디자이너 |
| IG | 거래 공통 메모리 |  |  |

---

# 14.1 IM 책임 분류

```text
Architecture / Standard
├─ AM 아키텍처 관리
├─ SC 시스템 공통
├─ FW 프레임워크
└─ LB 라이브러리

DevOps
├─ DP 배포
└─ SM 소스 버전관리

Terminal
├─ XM 정보단말 관리
└─ XD 정보단말 배포

Runtime / Batch / Data Support
├─ BJ 배치작업 처리
├─ CD 실시간 중계
├─ DT 데이터 치환 적재
└─ IG 거래 공통 메모리

Report
└─ RD 보고서 디자이너
```

---

# 15. Application Classification Inventory

필수 필드:

| 필드 | 정의 |
|---|---|
| App Group | MP/RD/AD/BI/DG/IM |
| Business Code | IC/SR/PT 등 |
| Function Code | 1자 기능 |
| Application Name | 업무/Application |
| Function Name | 기능 |
| System Group | 배치 책임 |
| Data Subject | 주제영역 |
| Runtime Type | Online/Event/CDC/ETL/BI/File/Batch 등 |
| Owner | 담당 조직 |
| Evidence | PPT/CMDB/정의서 |
| Status | FACT/OPEN/GAP |

---

# 15.1 Inventory SSOT 원칙

코드 사전의 SSOT:

```text
CMDB / 승인 코드 사전
```

본 정의서는 Baseline을 제공하지만:

```text
코드 추가
코드 변경
코드 폐기
```

는 승인 없이 수행하지 않는다.

---

# 16. Application Classification 정상/금지

정상:

```text
MP + IC + A
RD + SR + A
AD + SR + A
```

금지:

```text
IC만으로 업무 식별
SR만으로 데이터 영역 식별
임의 DP/DW 그룹 생성
CMDB 미등록 코드 사용
```

---

# 17. 시스템 그룹 업무 구분

[PPT-TOC/PPT-BODY]

6개 그룹:

```text
1. 마케팅플랫폼 시스템 그룹
2. 데이터플랫폼 시스템 그룹
3. BI 포탈 시스템 그룹
4. 데이터거버넌스 시스템 그룹
5. IT서비스 및 인프라 지원 시스템 그룹
6. 인프라 임시 시스템 그룹
```

---

# 17.1 System Group의 정의

System Group은 Application Group과 동일하지 않다.

```text
Application Group
= 업무 책임 분류

System Group
= 실행/배치/운영 책임 묶음
```

따라서:

```text
Application Group 1개
→ 여러 System/Server Role
```

이 가능하다.

---

# 18. MP System Group

대표 Logical Role Baseline:

```text
마케팅 WEB/WAS 공용
미니싱글뷰 WEB/WAS
실시간처리 AP
행동정보처리 AP
고객행동데이터 AP
```

업무 매핑:

```text
CO/IC/PC/BC/SA/PD/CM...
  → 공용 Marketing WEB/WAS

MS
  → Mini Single View WEB/WAS

EP
  → Real-time AP

BP
  → Behavior Processing AP

BD
  → Customer Behavior Data AP
```

---

# 19. Data Platform System Group

상위 구분:

```text
RDW
ADW
CDC / Relay
ETL
Data Transfer / Support
```

RD/AD를 물리적으로 동일 DB라고 가정하지 않는다.

---

# 20. BI System Group

대표:

```text
BI Portal WEB/WAS
OLAP
Self BI WEB/WAS/AP
Credit Performance WEB/WAS
BI UI/UX
```

---

# 21. DG System Group

대표:

```text
Biz Metadata
Data Quality
Data Flow / Lineage
Common Governance
```

실제 제품/Host는 PHYSICAL에서 확정한다.

---

# 22. IM System Group

대표:

```text
Architecture Management
Framework/Common
Deployment/SCM
Terminal Management
Terminal Deployment
Batch
Real-time Relay
Data Replacement Load
Report Designer
Operations / Monitoring
```

---

# 23. Infrastructure Temporary System Group

[PPT-BODY]

독립 그룹으로 존재한다.

의미:

```text
이행
전환
임시 운영
구축 과정의 보조 Infrastructure
```

정확한 시스템 목록/종료조건이 자료에서 확정되지 않으면 `[OPEN]`.

임시 시스템은 영구 Architecture로 자동 승격하지 않는다.

---

# 24. System Group ↔ Logical Server Role Baseline

근거 자료의 대표 예:

| App Group | System/Role | Logical Base Name | 업무 예 |
|---|---|---|---|
| MP | Marketing WEB/WAS 공용 | `sbmpco` | CO, IC, PC, BC, SA, PD, CM 등 |
| MP | Mini Single View WEB/WAS | `sbmpms` | MS |
| MP | Real-time Processing | `sbmpep` | EP |
| MP | Behavior Processing | `sbmpbp` | BP |
| MP | Customer Behavior Data | `sbmpbd` | BD |
| RD | RDW Appliance | `sbrdco` | SR, ZD, RM, FA, CO |
| AD | ADW Appliance | `sbadco` | SR, ZD, UM, FA, CO, RM, DA |
| BI | OLAP | `sbbioa` | OA |
| BI | BI Portal | `sbbipt` | PT |
| BI | Self-BI | `sbbisb` | SB |
| BI | Credit Performance | `sbbicr` | CR/UI |

주의:

```text
Logical Base Name
≠
Final Hostname
```

Hostname/Instance는 PHYSICAL에서 확정한다.

---

# 25. Application ↔ System Mapping 원칙

```text
Application
  ↓ belongs-to
Application Group
  ↓ executes-in
System Group
  ↓ deployed-to
Logical Node
  ↓ mapped-to
Physical Host
```

---

# 25.1 Mapping 최소 필드

| 필드 | 필수 |
|---|---|
| Application Code | Y |
| Application Name | Y |
| App Group | Y |
| System Group | Y |
| Logical Role | Y |
| Runtime Type | Y |
| Data Subject | Y |
| Source/Target | 가능 시 |
| Owner | Y |
| Evidence | Y |
| Physical Mapping | 후속 |
| Status | Y |

---

# 26. Application ↔ System Mapping Baseline

| Application/업무 | App Group | System Role | Runtime |
|---|---|---|---|
| 통합고객 | MP-IC | Marketing WEB/WAS | Online |
| 개인고객 | MP-PC | Marketing WEB/WAS | Online |
| 기업고객 | MP-BC | Marketing WEB/WAS | Online |
| 미니 싱글뷰 | MP-MS | Mini Single View WEB/WAS | Online |
| 캠페인 | MP-CM | Marketing/Application | Online/Batch |
| EBM | MP-EB | Campaign/Event | Event |
| 실시간 처리 | MP-EP | Real-time AP | Event |
| 행동정보 처리 | MP-BP | Behavior AP | Event |
| 고객 행동 데이터 | MP-BD | Behavior Data AP | Event/Data |
| RDW | RD | RDW Data Platform | CDC/Query |
| ADW | AD | ADW Data Platform | ETL/Analytics |
| BI포탈 | BI-PT | BI Portal | BI/Query |
| OLAP | BI-OA | OLAP | Analytics |
| Self BI | BI-SB | Self-BI | Analytics |
| 비즈메타 | DG-BM | Governance | Metadata |
| 데이터품질 | DG-DQ | Governance | Quality |
| 데이터흐름 | DG-DL | Governance | Lineage |
| 배치작업 | IM-BJ | Batch | Batch |
| 실시간 중계 | IM-CD | Relay | Event/Data |

일부 Runtime은 상위 분류이며 상세는 06 RUNTIME에서 확정한다.

---

# 27. 1.3 데이터 주제영역 정의

Data Subject Area의 목적:

```text
Table 이름보다 상위에서
데이터 책임을 고정
```

구조:

```text
Business Subject
   ↓
Data Ownership
   ↓
Storage / Platform
   ↓
Producer / Consumer
   ↓
Refresh Mechanism
```

---

# 28. 데이터 주제영역 6개 트리

[FACT/BASELINE]

```text
차세대 정보계 데이터 주제영역
├─ 데이터플랫폼 RDW
│   └─ CO · SR · ZD · RM · FA
│
├─ 데이터플랫폼 ADW
│   └─ CO · SR · ZD · UM · RM · FA · DA
│
├─ BI포탈
│   └─ PT · CR · OA · SB · UI
│
├─ 마케팅플랫폼
│   └─ MP 업무구분 집합
│
├─ 데이터 거버넌스
│   └─ CO · BM · DQ · DL
│
└─ IT서비스 및 업무지원
    └─ IM 업무구분 집합
```

---

# 28.1 중요한 해석

이 주제영역은 순수 EDW 데이터 모델의 Subject Area만을 뜻하지 않는다.

프로젝트 자료에서는:

```text
플랫폼
업무
분석
관리
운영
```

영역을 데이터관리 관점에서 묶은 상위 분류다.

따라서 일반적인 “고객/계좌/상품” Subject Area 체계로 임의 교체하지 않는다.

---

# 29. RDW Data Subject Responsibility

```text
RDW
├─ CO Common
├─ SR Real-time SoR
├─ ZD Near-real-time Summary
├─ RM Near-real-time Report Mart
└─ FA Feedback
```

역할:

```text
준실시간
운영성 조회
정보제공
Feedback
```

---

# 30. ADW Data Subject Responsibility

```text
ADW
├─ CO Common
├─ SR Analytical SoR
├─ ZD Analytical Integrated Summary
├─ UM Unit Business Mart
├─ RM Analytical Report Mart
├─ FA Feedback
└─ DA Analysis Support
```

역할:

```text
분석
통합
집계
마트
전략분석
```

---

# 31. Application Subject와 Data Platform Subject의 관계

예:

```text
MP Customer Application
       │
       │ Consume / Write Contract
       ▼
RDW Subject
       │
       │ ETL
       ▼
ADW Subject
       │
       ▼
BI Consumer
```

Application Domain과 Data Subject를 동일 개념으로 사용하지 않는다.

---

# 32. Data Subject Inventory 필수 필드

| 필드 | 정의 |
|---|---|
| Subject ID | 대구분+업무구분 |
| Subject Name | 공식 명칭 |
| Domain | RD/AD/MP/BI/DG/IM |
| Description | 책임 |
| SoR Type | Operational/Analytical/Management |
| RDW/ADW | 저장 위치 |
| Source | 주요 Producer |
| Consumer | 주요 소비자 |
| Refresh | CDC/ETL/Event/Online 등 |
| SLA | 근거 있을 때 |
| Owner | Data Owner |
| Security Class | 후속 |
| Evidence | PPT/정의서 |
| Status | FACT/OPEN |

---

# 33. Data Ownership 원칙

```text
Application Responsibility
≠
Data Platform Ownership
```

예:

```text
Marketing
= 고객/캠페인 업무 책임

RDW
= 준실시간 데이터 제공 책임

ADW
= 분석 데이터 책임

Governance
= Metadata/Quality/Lineage 책임
```

---

# 34. RDW / ADW Boundary

```text
Core / Source
    │
    ├─ CDC
    ▼
   RDW
    │
    ├─ Near Real-time Service
    ├─ Operational Query
    └─ ETL
        ▼
       ADW
        ├─ Aggregation
        ├─ Mart
        └─ BI / Analytics
```

금지:

```text
RDW = ADW
ADW = Real-time Operational DB
RDW = Unlimited Analytical Workload
```

---

# 35. Data Flow 상위 원칙

```text
Change
→ CDC

Bulk / Historical / Transformation
→ ETL

Event
→ Kafka/Event

Online Query
→ Application / Approved JDBC

File
→ FOS/MFT
```

이 원칙은 MECHANISM에서 상세 계약으로 확장한다.

---

# 36. 1.4 전체 시스템 아키텍처 구조 정의

```text
┌─────────────────────────────────────────────────────────┐
│ Channel                                                 │
│ Account Terminal / Information Terminal / Web / Mobile │
└───────────────────────┬─────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────────┐
│ Access / Integration                                   │
│ MCA / Web / API / Collector / File                    │
└───────────────────────┬─────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────────┐
│ Information Application                                │
│ MP / BI / DG / IM                                      │
└───────────────────────┬─────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────────┐
│ Data Platform                                          │
│ RDW / ADW                                              │
└───────────────────────┬─────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────────┐
│ Core / Related / External                              │
└─────────────────────────────────────────────────────────┘
```

---

# 37. Enterprise Context

## 37.1 사용자/채널

```text
Internal
├─ Account Terminal
├─ Information Terminal
└─ Package UI

Digital
├─ Web
└─ Mobile

Outbound
├─ SMS
├─ PUSH
└─ MAIL
```

각 채널은 동일한 의미의 요청을 만들지 않는다.

---

# 38. Channel Entry 의미 분리

## 38.1 계정성 거래

```text
Account Terminal
   ↓
MCA / Channel Integration
   ↓
Core
```

## 38.2 정보계 업무

```text
Information Terminal / Web
   ↓
Information Application
   ↓
RDW / Related Service
```

## 38.3 고객 행동 Event

```text
Web / Mobile
   ↓ Event
Collector
   ↓
Kafka / Event
   ↓
Marketing Reaction
```

---

# 39. Information Application Boundary

상위:

```text
Marketing Platform
BI Portal
Data Governance
IT Service & Support
```

Application이 타 Application의 Persistence 내부로 들어가지 않는다.

정상:

```text
Application A
   ↓ Contract
Interface
   ↓
Application B
```

금지:

```text
Application A
   ↓
Application B DAO
```

---

# 40. Marketing Platform Boundary

```text
Marketing Platform
├─ Customer
├─ Sales / Product
├─ Campaign / EBM
├─ Real-time / Behavior
└─ Support / Contact
```

주요 Data Relation:

```text
Marketing
  ↔ RDW
  ↔ Event
  ↔ Related/Core via Interface
```

---

# 41. Marketing Event Boundary

```text
Customer Behavior
       ↓
Collector
       ↓
Event Broker
       ↓
Behavior Processing
       ↓
EBM / Offer
       ↓
UMS / Customer Contact
```

이 흐름을 일반 Online Request Thread와 동일 경로로 만들지 않는다.

---

# 42. BI Portal Boundary

```text
User
 ↓
BI Portal / Self BI / OLAP
 ↓
Data Service / Approved Query
 ↓
RDW / ADW
```

BI가 계정 Core를 대량 직접 조회하는 구조를 기본으로 하지 않는다.

---

# 43. Data Governance Boundary

```text
Metadata
Quality
Data Flow / Lineage
        ↓
Governance
        ↓
Application / Data Platform Reference
```

Governance는:

```text
Control / Metadata Plane
```

성격으로 본다.

---

# 44. IT Service & Support Boundary

상위 책임:

```text
Architecture Management
System Common
Framework
Library
SCM
Deployment
Terminal Management/Deployment
Batch
Relay
Data Support
Report Designer
Monitoring/Operations
```

업무 Application Logic을 공통영역으로 옮기지 않는다.

---

# 45. Integration Mechanism by Purpose

| 업무 목적 | 대표 Mechanism |
|---|---|
| 계정/Transaction | MCA |
| Online Service | API / JSON |
| Event | Kafka/Event |
| Change Data | CDC |
| Bulk/Analytics | ETL |
| File | FOS/MFT |
| 승인 Data Access | JDBC |

원칙:

> 모든 Interface를 하나의 기술로 통일하지 않는다.

---

# 46. Interface Boundary

```text
Producer / Consumer
       ↓
Entry Boundary
       ↓
Purpose-specific Mechanism
       ↓
Target Processing
       ↓
Runtime Control
       ↓
Trace / Evidence
```

BIG PICTURE에서는 “어떤 방식의 경계가 필요한가”까지 정의한다.

Header/Timeout/Retry/Schema 상세는 MECHANISM에서 확정한다.

---

# 47. Interface 금지 패턴

## 47.1 Channel → DB

```text
Channel ─────▶ DB
```

금지.

정상:

```text
Channel → Application → Data
```

## 47.2 External → Internal DB

```text
External → JDBC → Internal DB
```

기본 금지.

## 47.3 모든 Interface REST화

금지:

```text
Transaction
Event
CDC
ETL
File
```

을 모두 REST로 구현.

## 47.4 대량 Data를 Online API로 전달

금지.

---

# 48. Application Responsibility vs Data Responsibility

```text
Application
├─ Business Use Case
├─ Validation
├─ Rule / Process
└─ Interface Contract

Data Platform
├─ Data Storage
├─ Integration
├─ Transformation
├─ SoR / Mart
└─ Data Provision

Governance
├─ Metadata
├─ Quality
└─ Lineage
```

책임혼재를 방지한다.

---

# 49. Security Boundary

상위 Security View:

```text
Channel
→ User Authentication

MCA / API
→ Transaction/API Authorization

Event
→ Producer / Consumer ACL

CDC
→ Replication Account

ETL
→ Source / Target Account

File
→ Transfer Account / Encryption / Integrity

External
→ Certificate / Allowlist / Network Boundary
```

상세는 Security/Mechanism에서 확정한다.

---

# 50. Observability Boundary

End-to-End:

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
  │
  ▼
Response / Event / Batch
```

BIG PICTURE의 요구:

```text
모든 주요 Boundary가
Trace Key를 잃지 않아야 함
```

---

# 51. Runtime Type 상위 Mapping

| Domain | 대표 Runtime |
|---|---|
| MP Online | Channel/Online |
| MP Event | Marketing Event |
| RD | CDC / Query |
| AD | ETL / Analysis |
| BI | Analysis/Provision |
| DG | Governance/Metadata |
| IM Batch | Batch |
| IM Relay | Integration |
| File | File Runtime |

상세 6대/12개 Runtime Type은 06장에서 정의한다.

---

# 52. 주요 시스템 대상 서버 식별 — 원칙

BIG PICTURE에서는:

```text
Logical Server Candidate
```

까지만 식별한다.

확정하지 않는 것:

```text
Final Hostname
Instance Number
CPU
Memory
Port
OS
SW Version
```

---

# 53. 주요 Logical Server Candidate

```text
Access / Delivery
├─ WEB
├─ Channel Integration
└─ API / External Boundary

Marketing
├─ Marketing WEB
├─ Marketing WAS
├─ Mini Single View WEB/WAS
├─ Real-time AP
├─ Behavior Processing AP
└─ Customer Behavior Data AP

Data
├─ CDC Relay
├─ ETL
├─ RDW DB
└─ ADW DB

BI
├─ BI Portal WEB/WAS
├─ Self BI WEB/WAS/AP
├─ OLAP
└─ Credit Performance WEB/WAS

Governance
├─ Biz Metadata
├─ Data Quality
└─ Data Flow

Support
├─ Batch
├─ Terminal Management
├─ Deployment
├─ Framework/Common
└─ Monitoring / Dashboard
```

---

# 54. Physical Role Evidence와 Big Picture의 관계

Physical 자료에는 다음 역할들이 확인된다.

```text
Marketing WEB #1/#2
Marketing WAS #1/#2
Mini Single View WEB/WAS
Real-time AP
Behavior AP
Customer Behavior Data AP
BI Portal WEB/WAS
Self BI WEB/WAS/AP
Credit Performance WEB/WAS
Governance WAS
Terminal/Deployment/Dashboard
RDW/ADW
```

하지만 BIG PICTURE에서는 이 정보를:

```text
Logical Candidate 존재를 보강하는 Evidence
```

로만 사용한다.

대수/Host는 PHYSICAL에서 확정한다.

---

# 55. System / External Boundary

다음 경계를 분명히 한다.

```text
Channel Boundary
Application Boundary
Data Boundary
Integration Boundary
External Boundary
Security Boundary
Operations Boundary
```

---

# 56. Boundary 변경은 Architecture 변경

예:

```text
Marketing이
RDW를 거치던 데이터를
Core DB Direct로 변경
```

이는 단순 개발 변경이 아니다.

변경 영향:

```text
Data Ownership
Security
Performance
Interface
Failure
Trace
```

따라서 ADR 대상이다.

---

# 57. PDMG Reference Position

```text
NSIGHT
│
├─ Marketing Platform
│    │
│    └─ Online Application Runtime
│          │
│          └─ PDMG Reference
│              ├─ pdmg-ui
│              ├─ pdmg-service
│              ├─ pdmg-fw
│              ├─ pdmg-jwt
│              └─ pdmg-om [Evidence TBD]
│
├─ Data Platform
├─ BI
├─ Governance
└─ IT Support
```

---

# 57.1 PDMG가 증명하는 것

```text
ServiceId
Handler/Facade/Service/DAO
TCF
Transaction
Timeout
Message/Error/Log
JWT/SSO
Application Runtime
```

---

# 57.2 PDMG가 증명하지 않는 것

```text
전체 Data Platform
Kafka Cluster 전체
CDC/ETL 전체
BI Solution 전체
Data Governance 전체
Enterprise DR 전체
```

---

# 58. Big Picture NFR Mapping

| NFR | Big Picture 반영 |
|---|---|
| Performance | FAST/DEEP, RD/AD, Online/Event/Data 분리 |
| Availability | Domain/Node Failure Boundary |
| Scalability | Application/System Group 독립 확장 |
| Security | Channel/API/Event/Data/External Trust Boundary |
| Observability | GUID/ServiceId/InterfaceId E2E 연결 |

---

# 59. FAST / DEEP Big Picture Mapping

```text
FAST
Customer Event
  ↓
Collector
  ↓
Event
  ↓
Marketing Reaction

DEEP
Core Change
  ↓
CDC
  ↓
RDW
  ↓
ETL
  ↓
ADW
  ↓
BI
```

경계:

```text
FAST
≠
DEEP
```

이지만 결과/Feedback은 연계될 수 있다.

---

# 60. Big Picture Architecture Principle

| ID | 원칙 |
|---|---|
| BP-01 | 책임을 제품보다 먼저 정의 |
| BP-02 | App Group과 System Group을 분리 |
| BP-03 | 대구분+업무구분으로 유일성 확보 |
| BP-04 | CMDB/승인 코드사전을 SSOT로 사용 |
| BP-05 | RD와 AD를 독립 책임으로 관리 |
| BP-06 | Data Subject와 Application을 동일시하지 않음 |
| BP-07 | Interface는 업무목적별 Mechanism 선택 |
| BP-08 | Direct DB/P2P는 기본 금지 |
| BP-09 | Logical Candidate와 Physical Host를 분리 |
| BP-10 | Security/Observability를 Cross-cutting으로 적용 |
| BP-11 | PDMG는 Application Runtime Reference로 한정 |
| BP-12 | Boundary 변경은 Architecture 변경으로 관리 |

---

# 61. 정상 패턴

```text
Application
  ↓
Approved System Group
  ↓
Approved Interface
  ↓
Owned / Approved Data
  ↓
Traceable Runtime
```

---

# 62. 금지 패턴

```text
분류코드 없는 Application
임의 업무코드
RD/AD 통합 DW 표현
Channel→DB
External→DB
Application→타 Application DAO
Online Thread→대량 ETL
모든 연계 REST
Logical Hostname을 Physical 확정값으로 사용
PDMG 구현을 전체 NSIGHT 표준으로 자동 승격
```

---

# 63. Application Inventory Template

| App Group | Business | Function | Application | System Group | Data Subject | Runtime | Owner | Evidence | Status |
|---|---|---|---|---|---|---|---|---|---|
| MP | IC | TBD | 통합고객 | MP System | MP/RD | Online | TBD | PPT | Baseline |
| MP | MS | TBD | 미니싱글뷰 | MP-MS | RD | Online | TBD | PPT | Baseline |
| MP | EP | TBD | 실시간처리 | MP-EP | MP/RD | Event | TBD | PPT | Baseline |
| RD | SR | TBD | 실시간SoR | RDW | RD-SR | CDC/Query | TBD | PPT | Baseline |
| AD | UM | TBD | 분석단위마트 | ADW | AD-UM | ETL | TBD | PPT | Baseline |
| BI | SB | TBD | Self BI | BI-SB | AD/RD | BI | TBD | PPT | Baseline |
| DG | DQ | TBD | 데이터품질 | DG | DG-DQ | Governance | TBD | PPT | Baseline |

Function Code/Owner는 실제 사전/조직자료로 보완한다.

---

# 64. Application ↔ System ↔ Data Mapping Template

```text
Application
  │
  ├─ System Group
  │
  ├─ Logical Role
  │
  ├─ Data Subject
  │
  ├─ Interface
  │
  └─ Runtime Type
```

| Application | System Group | Logical Role | Data Subject | Interface | Runtime |
|---|---|---|---|---|---|
| 통합고객 | MP | Marketing WAS | MP/RD | MCA/API/JDBC 승인 | Online |
| 미니싱글뷰 | MP | MS WAS | RD | MCA/API | Online |
| 실시간처리 | MP | EP AP | MP/RD | Event | Event |
| RDW SR | DP/RD | RDW | RD-SR | CDC | CDC |
| ADW UM | DP/AD | ADW | AD-UM | ETL | ETL |
| Self BI | BI | BI/SB | RD/AD | Query | Analysis |

세부 Interface ID는 MECHANISM Inventory에서 확정한다.

---

# 65. Data Subject Mapping Template

| Subject | Platform | Producer | Consumer | Mechanism | SoR Type | Status |
|---|---|---|---|---|---|---|
| RD-SR | RDW | Core/Source | MP/BI | CDC | Operational | Baseline |
| RD-ZD | RDW | RDW Process | MP/BI | ETL/Process | Near-real-time Summary | Baseline |
| AD-SR | ADW | RDW/Source | BI | ETL | Analytical | Baseline |
| AD-UM | ADW | ETL | BI/Analysis | ETL | Mart | Baseline |
| DG-BM | DG | Systems/Data | Governance Users | Metadata | Metadata | Baseline |

정확한 Source/Consumer 목록은 상세 Data Inventory에서 보완한다.

---

# 66. Logical Server Candidate Inventory

| Candidate | Domain | Role | Runtime | Physical Detail |
|---|---|---|---|---|
| MP-WEB | MP | Web Entry | Online | 04에서 확정 |
| MP-WAS | MP | Business | Online | 04 |
| MP-MS-WAS | MP | Single View | Online | 04 |
| MP-EP-AP | MP | Real-time | Event | 04 |
| MP-BP-AP | MP | Behavior | Event | 04 |
| MP-BD-AP | MP | Behavior Data | Event/Data | 04 |
| CDC | RD | Change Capture | CDC | 04 |
| ETL | RD/AD | Bulk/Transform | ETL | 04 |
| RDW | RD | Operational DW | CDC/Query | 04 |
| ADW | AD | Analytics DW | ETL/Query | 04 |
| BI-PORTAL | BI | BI Service | Analysis | 04 |
| SELF-BI | BI | Self BI | Analysis | 04 |
| DG | DG | Governance | Metadata | 04 |
| BATCH | IM | Batch | Batch | 04 |
| MONITOR | IM | Operations | Ops | 04 |

---

# 67. GAP Register

| ID | GAP | 영향 | 조치 |
|---|---|---|---|
| GAP-BP-01 | Function 1자 코드 전체표 미확보 | 분류 완결성 | CMDB 추출 |
| GAP-BP-02 | Application별 Owner 미완성 | 운영 책임 | RACI 정리 |
| GAP-BP-03 | Application↔System 전수 Mapping 미완성 | 배치/영향분석 | Inventory 구축 |
| GAP-BP-04 | Data Subject 상세 Source/Consumer 미완성 | Data Lineage | Data Inventory 구축 |
| GAP-BP-05 | Infrastructure Temporary Group 상세 | 종료/전환 | 이행계획 확인 |
| GAP-BP-06 | 일부 DG/IM Physical Role 상세 | Logical Handoff | 03에서 보완 |
| GAP-BP-07 | Interface ID/SLA 전수목록 | Runtime/운영 | 05 Inventory |
| GAP-BP-08 | PDMG 정확한 Target App Mapping | AS-IS/TO-BE | Alignment ADR |

---

# 68. ADR 후보

| ADR | 주제 |
|---|---|
| ADR-BP-01 | Application Classification Governance |
| ADR-BP-02 | RDW / ADW Ownership |
| ADR-BP-03 | Marketing Online vs Event Resource Boundary |
| ADR-BP-04 | Application간 Direct Access 예외정책 |
| ADR-BP-05 | System Group 배치원칙 |
| ADR-BP-06 | Data Subject Ownership/RACI |
| ADR-BP-07 | PDMG Target Position |
| ADR-BP-08 | Infrastructure Temporary Group 종료/전환 |

---

# 69. Verification Checklist — 1.2

```text
[ ] MP/RD/AD/BI/DG/IM 대구분이 유지되는가
[ ] 업무구분 코드가 공식 Baseline과 일치하는가
[ ] 업무코드 단독으로 유일식별하지 않는가
[ ] App Group과 System Group을 혼동하지 않는가
[ ] CMDB/승인 사전을 SSOT로 사용하는가
[ ] MP의 Online/Event 업무가 분리되는가
```

---

# 70. Verification Checklist — 1.3

```text
[ ] 데이터 주제영역 6개 트리가 존재하는가
[ ] RDW/ADW가 분리되는가
[ ] RD-SR와 AD-SR가 구분되는가
[ ] Subject와 Application을 동일시하지 않는가
[ ] Producer/Consumer/Refresh 필드가 있는가
[ ] 일반적인 EDW 주제영역으로 임의 대체하지 않았는가
```

---

# 71. Verification Checklist — 1.4

```text
[ ] Channel→Access→Application→Data→External 흐름이 보이는가
[ ] Marketing/BI/Governance/IM 책임이 보이는가
[ ] RDW/ADW가 데이터플랫폼으로 분리되는가
[ ] Online/Event/CDC/ETL/File 목적별 경계가 보이는가
[ ] Logical Server Candidate까지만 정의했는가
[ ] Hostname/대수/CPU를 조기 확정하지 않았는가
[ ] PDMG를 전체 Big Picture로 확대하지 않았는가
```

---

# 72. Big Picture Completion Gate

```text
G-BP-01  Application Group 확정
G-BP-02  Business Code Baseline 확인
G-BP-03  System Group 확인
G-BP-04  Application↔System Mapping 구조 확인
G-BP-05  Data Subject 6개 영역 확인
G-BP-06  RDW/ADW Ownership 확인
G-BP-07  Enterprise Context 확인
G-BP-08  Integration Boundary 확인
G-BP-09  Logical Server Candidate 확인
G-BP-10  PDMG Reference 범위 확인
```

---

# 73. LOGICAL Handoff

BIG PICTURE Output:

```text
Application Group
Business Classification
System Group
Application ↔ System Mapping
Data Subject
RDW / ADW Role
Enterprise Context
System Boundary
Integration Boundary
Logical Server Candidate
PDMG Reference Position
```

LOGICAL에서 확정:

```text
IT Zone
Logical System
Environment
Logical Node
Technology Component
Layer
Allowed / Forbidden Connection
Domain Logical Architecture
```

---

# 74. Big Picture → Logical 연결

```text
[WHAT]
Application / Data / System 책임
        │
        ▼
[WHERE LOGICALLY]
Zone / Logical System / Logical Node
        │
        ▼
[CONNECTION]
Allowed / Forbidden Logical Path
```

---

# 75. 최종 평가

BIG PICTURE의 완성 상태는 다음과 같다.

```text
업무가
어느 Application Group에 속하는지 알고,

Application이
어느 System Group에서 실행되는지 알고,

그 Application이
어느 Data Subject를 사용/소유하는지 알고,

다른 시스템과
어떤 Boundary를 통해 연결되는지 알고,

후속 LOGICAL에서
어떤 Node/Component를 설계해야 하는지
추적할 수 있어야 한다.
```

핵심 선언:

> **NSIGHT Big Picture는 “시스템 목록”이 아니라 Application·System·Data의 책임 지도이며, 이 책임 지도는 CMDB/Inventory/Interface/Data Ownership/Runtime Traceability의 상위 기준선으로 사용한다.**

---

# Appendix A. Application Classification Baseline

## MP

```text
CO 공통
IC 통합고객
PC 개인고객
BC 기업고객
MS 미니 싱글뷰
SA 상담판매
PD 통합상품
CM 캠페인
EB EBM
EP 실시간 처리
BP 행동정보 처리
BD 고객 행동 데이터
SS 영업지원
CS CS
CT 컨텐츠
MG 메시지
```

## RD

```text
CO 공통
SR 실시간SoR
ZD 준실시간요약집계
RM 준실시간보고서마트
FA 피드백
```

## AD

```text
CO 공통
SR 분석SoR
ZD 분석통합요약집계
UM 분석단위업무마트
RM 분석보고서마트
FA 피드백
DA 분석지원
```

## BI

```text
PT BI포탈
CR 신용실적
OA OLAP
SB Self BI
UI 신BI포털UIUX
```

## DG

```text
CO 공통
BM 비즈메타
DQ 데이터품질
DL 데이터흐름
```

## IM

```text
AM 아키텍처 관리
SC 시스템 공통
DP 배포
FW 프레임워크
LB 라이브러리
SM 소스 버전관리
IG 거래 공통 메모리
XM 정보단말 관리
XD 정보단말 배포
BJ 배치작업 처리
CD 실시간 중계
DT 데이터 치환 적재
RD 보고서 디자이너
```

---

# Appendix B. Data Subject Baseline

```text
RD
CO / SR / ZD / RM / FA

AD
CO / SR / ZD / UM / RM / FA / DA

BI
PT / CR / OA / SB / UI

MP
MP 업무구분 전체

DG
CO / BM / DQ / DL

IM
IM 업무구분 전체
```

---

# Appendix C. BIG PICTURE에서 확정하지 않는 것

```text
Final Hostname
Server Count
CPU / Memory
JVM Heap
Tomcat Thread
Worker Pool
Hikari
Port
OS
SW Version
RAC Node Count
Kafka Partition
CDC SLA 최종값
ETL Parallelism
Full Interface ID
Full Table List
```

후속 Architecture Level에서 근거로 확정한다.
