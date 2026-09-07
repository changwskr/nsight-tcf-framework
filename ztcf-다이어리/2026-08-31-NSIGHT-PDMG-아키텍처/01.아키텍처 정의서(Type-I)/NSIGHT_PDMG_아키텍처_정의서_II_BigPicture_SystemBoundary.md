# NSIGHT / PDMG 아키텍처 정의서 — II. Big Picture & System Boundary

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 대상: NSIGHT Target Big Picture + PDMG Reference Position  
> 문서 상태: **Draft / Evidence-First**  
> 작성일: 2026-08-31  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_I_비전_전략.md`

---

# 0. Evidence Register

| ID | 근거 자료 | 시점/버전 | 본 장 사용 목적 | 상태 |
|---|---|---|---|---|
| EV-II-01 | `NH_N-SIGHT_아키텍처_발표자료-20260329 수정본.pptx` | 2026-03-25~03-29 | Big Picture, 5대 서비스 도메인, 3대 통제 정책, LTI | `[FACT]` |
| EV-II-02 | `빅픽처_전체시스템구조정의_통합_TOPDOWN_아키텍처.md` | 2026-08-27 | 전체 System Context, Channel/Application/Data/Governance/External 구조 | `[WORKING BASELINE]` |
| EV-II-03 | `빅픽처_전체시스템아키텍처구조정의_TOPDOWN_강화분석.md` | 2026-08-28 | Big Picture Drill-down, Event/Data/External 경계 | `[WORKING BASELINE]` |
| EV-II-04 | `2026-03-08-NH_아키텍처전략_정리본_(최종본)_V1.0.docx` | 2026-03-08 | 5대 도메인 책임 및 규격/인터페이스/자원 통제 | `[FACT]` |
| EV-II-05 | `NSIGHT_시스템간인터페이스_원칙및추진방안_TOPDOWN.md` | 2026-08-28 | 목적별 Integration Mechanism, P2P/DB 직접접근 통제 | `[WORKING BASELINE]` |
| EV-II-06 | `NSIGHT_시스템간_인터페이스_아키텍처_정의_TOPDOWN_통합본.md` | 2026-08-27 | 시스템간 Interface Domain/Pattern/Runtime Control | `[WORKING BASELINE]` |
| EV-II-07 | `NSIGHT_아키텍처_정의서_도형TEXT_표도형교체_시스템영역구성_1차.pptx` | 2026-08-25 | 서비스 제공 Zone, DR/운영 구성요소 교차 확인 | `[FACT-SOURCE]` |
| EV-II-08 | `01장.PDMG_시스템_개요_ASCII_확장본.md` | 2026-08-14 | PDMG가 전체 Big Picture에서 차지하는 Reference 위치 | `[AS-IS EVIDENCE]` |
| EV-II-09 | `2026-08-17-NSIGHT_전체_아키텍처_통합분석_정의_마스터_프롬프트.md` | 2026-08 | GSLB/L4/Apache/Tomcat, Security/OM/Trace 검증 관점 | `[WORKING BASELINE]` |
| EV-II-10 | `NSIGHT_PDMG_아키텍처_정의서_I_비전_전략.md` | 2026-08-31 | Vision/NFR/도메인/FAST-DEEP Handoff | `[CURRENT BASELINE DRAFT]` |
| EV-II-11 | `NSIGHT_PDMG_아키텍처_인포그래픽_이미지화_마스터_프롬프트.md` | 2026-08-31 | II장 필수 View와 Evidence-First 작성 계약 | `[WORKING BASELINE]` |

> **주의 1:** 이 장은 NSIGHT의 공간·책임·경계를 정의한다. `Handler / Facade / Service / DAO`는 III~V장의 하위 구현 구조이며 II장의 메인 구조가 아니다.  
> **주의 2:** Big Picture의 원본/발표자료와 2026-08 TOP-DOWN 재구성 문서를 구분한다. 재구성 문서의 해석은 `[ANALYSIS]` 또는 `[WORKING BASELINE]`으로 관리한다.  
> **주의 3:** `GSLB → L4 → Apache → Tomcat`은 프로젝트에서 반복된 WEB/WAS Working Baseline이지만 Big Picture 발표장표 자체의 5대 도메인 정의와 동일한 Evidence Level로 취급하지 않는다. 상세는 VIII장에 확정한다.

---

# 1. Figure Plan

| FIG | 제목 | Level | 목적 | 필수 |
|---|---|---:|---|---|
| FIG-II-01 | NSIGHT Enterprise Context | L0 | 전체 사용자·채널·정보계·계정계·외부 경계 | Y |
| FIG-II-02 | 5대 Service Domain Responsibility Map | L1 | 책임 공간 고정 | Y |
| FIG-II-03 | Channel / Access Boundary | L1~L2 | 계정거래·정보계 조회·행동 Event 진입 분리 | Y |
| FIG-II-04 | Information Application Service Map | L2 | Marketing / BI / Support 응용영역 | Y |
| FIG-II-05 | Marketing Event Runtime Boundary | L2~L3 | Collector/Kafka/EBM 실시간 Event 경로 | Y |
| FIG-II-06 | Data Platform Boundary — RDW vs ADW | L2~L3 | 실시간/분석 데이터 책임 분리 | Y |
| FIG-II-07 | Data Governance Boundary | L2~L3 | Meta/Quality/Flow 책임 | Y |
| FIG-II-08 | Integration Mechanism by Purpose | L2~L3 | Online/Event/CDC/ETL/File/JDBC 분리 | Y |
| FIG-II-09 | Application Responsibility vs Data Responsibility | L2 | Application과 DB 소유 책임 구분 | Y |
| FIG-II-10 | Infrastructure / Execution Boundary | L2~L3 | Edge/WEB/WAS/Business/Data 경계 | Y |
| FIG-II-11 | Security Boundary | L2~L4 | 인증·인가·보호 경계 | Y |
| FIG-II-12 | Observability Boundary | L2~L4 | GUID/ServiceId/Log/Metric/Trace 경계 | Y |
| FIG-II-13 | PDMG Reference Position in NSIGHT | L2~L3 | PDMG를 전체 Big Picture에 정확히 배치 | Y |
| FIG-II-14 | Forbidden / Failure Boundary | L4 | P2P/Direct DB/대량 online 등 금지·장애영역 | Y |
| FIG-II-15 | GAP / ADR / Next Chapter Handoff | L5 | III장으로 넘길 미결사항 | Y |

---

# 2. 핵심 결론

NSIGHT Big Picture의 핵심은 시스템을 제품별로 나열하는 것이 아니라 **책임을 공간에 고정하고 연결을 경계에서 통제하는 것**이다.

`[FACT]` 2026-03 전략자료는 Big Picture를 다음 다섯 개 서비스 도메인으로 정의한다.

```text
① 데이터플랫폼
② 마케팅플랫폼
③ BI 포탈
④ 데이터거버넌스
⑤ IT 서비스 및 업무지원
```

이 다섯 도메인을 지탱하는 핵심 통제는 다음 세 축이다.

```text
규격 표준
인터페이스 통제
물리·논리 자원 분리
```

`[FACT]` 전체 End-to-End의 상위 방향은 다음과 같다.

```text
Channel
  → Interface / Access Boundary
  → Information Application
  → Data Platform / Analytics
  → Service / Decision
```

`[FACT]` 원본 Big Picture와 TOP-DOWN 통합본은 계정단말·정보계단말·Web·Mobile 등의 채널이 각각 **계정거래, 정보계 JSON 서비스, 고객행동 Event**라는 서로 다른 의미의 진입경로를 가진다고 보여준다.

`[FACT]` 데이터플랫폼은 **RDW와 ADW를 역할과 자원 관점에서 분리**한다. RDW는 실시간/준실시간 운영·정보제공의 중심이고, ADW는 전략분석·통합/요약/집계·마트의 중심이다.

`[FACT]` 시스템간 연계는 하나의 기술로 통일하지 않고 목적에 따라 **API/MCA, Kafka, CDC, ETL, File/FOS/MFT** 등의 Mechanism을 구분한다.

`[AS-IS]` PDMG는 이 Big Picture 전체가 아니라 **정보계 Application Service의 온라인 거래 실행 Reference**다. 따라서 PDMG는 Marketing/Business WAR와 Framework/Runtime을 증명하는 하위 Reference로 배치하며, RDW/ADW·Kafka·CDC·ETL·BI·Governance 전체를 PDMG가 소유한다고 설명하지 않는다.

---

# 3. 목적 / 범위 / 전제

## 3.1 목적

이 장은 다음 질문에 답한다.

1. NSIGHT의 내부·외부 시스템 경계는 어디인가?
2. 5대 서비스 도메인은 어떤 책임을 갖는가?
3. 채널의 요청은 어떤 의미로 분기되는가?
4. Marketing/BI/Data/Governance는 어떤 연결만 허용하는가?
5. Event, CDC, ETL, File, API는 왜 서로 다른 길을 가지는가?
6. Application과 Data의 책임은 어디에서 분리되는가?
7. Security와 Observability는 어느 경계를 횡단하는가?
8. PDMG는 전체 Big Picture에서 정확히 어디에 있는가?

## 3.2 포함 범위

```text
Enterprise / System Context
5대 서비스 도메인
Channel / Access Boundary
Marketing / BI Application Boundary
Marketing Event Processing
RDW / ADW Data Platform
Data Governance
Related / Core / External Integration
Integration Mechanism
Infrastructure 실행 경계의 상위 구조
Security / Observability Cross-cutting
PDMG Reference Position
Forbidden Pattern / GAP / ADR
```

## 3.3 제외 범위

```text
PDMG 5모듈 상세                 → III
DefaultFilter/TCF/Dispatcher    → IV
Thread/Timeout/Transaction      → V
전문/Context/Error/Logging      → VI
JWT/SSO/Session 상세            → VII
CPU/Memory/Thread/Pool/HA/DR    → VIII
CI/CD/OM/운영대시보드           → IX
ServiceId/Source/SQL Trace      → X
```

## 3.4 작성 전제

- Big Picture는 `공간·책임·경계`를 정의하는 장이다.
- 물리 서버 수량과 세부 설정은 이 장에서 확정하지 않는다.
- Source에 없는 Gateway/Proxy 제품을 Big Picture FACT로 만들지 않는다.
- Interface 기술은 **업무 목적에 따라 구분**하고 모든 연계를 REST/API 하나로 통일하지 않는다.
- PDMG Runtime을 Big Picture 전체 구조와 같은 레벨에 올리지 않는다.

---

# 4. FIG-II-01 — NSIGHT Enterprise Context

```text
┌───────────────────────────────────────────────────────────────────────────────┐
│                          [사용자 / 업무 채널]                                │
│                                                                               │
│ 내부 사용자                                                                  │
│  ├─ 계정 단말                                                               │
│  └─ 정보계 단말 / Package UI                                                │
│                                                                               │
│ 고객 / 외부 접점                                                             │
│  ├─ Web Channel                                                             │
│  ├─ Mobile Channel                                                          │
│  └─ SMS / PUSH / MAIL                                                       │
└───────────────────────────────────┬───────────────────────────────────────────┘
                                    │
                    계정거래 / JSON / Event / Push
                                    │
                                    ▼
┌───────────────────────────────────────────────────────────────────────────────┐
│                       [NSIGHT Access / Integration Boundary]                  │
│                                                                               │
│  MCA / 정보계 Web / BI Web / API Boundary / Event Collector / File Boundary │
└───────────────────────────────────┬───────────────────────────────────────────┘
                                    │
                                    ▼
┌───────────────────────────────────────────────────────────────────────────────┐
│                       [NSIGHT Information Service]                            │
│                                                                               │
│   ② Marketing Platform          ③ BI Portal                                 │
│   - 고객/상담/상품               - BI/실적/OLAP                              │
│   - 캠페인/EBM                   - Self BI                                   │
│   - 실시간/행동                  - 분석·리포트                               │
│                                                                               │
│   ⑤ IT Service & Support                                                     │
│   - SSO / Batch / DevOps / Monitoring / Terminal Support                    │
└───────────────────────┬───────────────────────────────┬───────────────────────┘
                        │                               │
                        ▼                               ▼
┌──────────────────────────────────────┐  ┌─────────────────────────────────────┐
│ ① Data Platform                     │  │ ④ Data Governance                  │
│                                      │  │                                     │
│ RDW                                  │  │ Biz Meta                            │
│ ADW                                  │  │ Data Quality                        │
│ 실시간/준실시간 + 분석/마트         │  │ Data Flow / Lineage                 │
└───────────────────┬──────────────────┘  └──────────────────┬──────────────────┘
                    │                                        │
                    └───────────────────┬────────────────────┘
                                        ▼
┌───────────────────────────────────────────────────────────────────────────────┐
│                     [Related / Core / Big Data / External]                    │
│                                                                               │
│ 계정 Core / 계정 연계 / 경영관리 / 리스크 / 정보 단위업무 / Big Data         │
│ 계열사 / 외부기관                                                             │
│                                                                               │
│ API/MCA · CDC · ETL · FOS/MFT · File · 승인된 JDBC                           │
└───────────────────────────────────────────────────────────────────────────────┘
```

### 그림 해설

1. **위쪽은 사용자와 채널**, 가운데는 정보계 서비스 책임, 아래쪽은 데이터·거버넌스·외부 생태계다.
2. 계정단말은 정보계의 모든 업무를 직접 수행하는 내부 앱으로 해석하지 않는다. 계정성 거래의 주 경계는 MCA/Core다.
3. 정보계단말·Web·Mobile은 정보조회/마케팅/BI와 행동 Event의 진입점이다.
4. RDW/ADW는 Application의 하위 내부 DB가 아니라 **독립 Data Platform 책임공간**으로 본다.
5. Data Governance는 실데이터 적재엔진이 아니라 메타·품질·흐름을 통제하는 Cross/Data Control Plane 성격을 갖는다.

---

# 5. II.1 Big Picture의 5대 Service Domain

## 5.1 FIG-II-02 — 5대 Service Domain Responsibility Map

```text
                        ┌────────────────────────────┐
                        │ ⑤ IT Service & Support    │
                        │ SSO / Batch / DevOps / OM │
                        │ Monitoring / Terminal     │
                        └─────────────┬──────────────┘
                                      │ Cross-cutting
          ┌───────────────────────────┼───────────────────────────┐
          │                           │                           │
          ▼                           ▼                           ▼
┌──────────────────┐       ┌──────────────────┐       ┌──────────────────┐
│ ② Marketing     │       │ ③ BI Portal     │       │ ④ Governance    │
│                  │       │                  │       │                  │
│ 고객              │       │ BI / 실적       │       │ Biz Meta         │
│ 상담/상품         │       │ OLAP / Self BI  │       │ Data Quality     │
│ 캠페인/EBM        │       │ Reporting       │       │ Data Flow        │
│ 실시간/행동       │       │ Analytics UX    │       │ Lineage          │
└────────┬─────────┘       └────────┬─────────┘       └────────┬─────────┘
         │                           │                           │
         └──────────────┬────────────┘                           │
                        ▼                                        │
              ┌─────────────────────────┐                        │
              │ ① Data Platform         │◀───────────────────────┘
              │                         │    Metadata / Quality
              │ RDW                     │
              │ ADW                     │
              │ Real-time + Analytics   │
              └─────────────────────────┘
```

## 5.2 도메인별 책임

| 도메인 | 핵심 책임 | 소유하지 않는 책임 | 상태 |
|---|---|---|---|
| 데이터플랫폼 | RDW/ADW, 실시간·분석 데이터의 저장/가공 기반 | 채널 UI, 마케팅 정책 실행 | `[FACT]` |
| 마케팅플랫폼 | 고객/상담/상품/캠페인/실시간 행동기반 실행 | 전행 데이터 표준 소유, 분석 DW 운영 | `[FACT]` |
| BI 포탈 | 분석·실적·OLAP·Self BI·리포트 소비 | 계정 원장 처리, Event 수집 원천 | `[FACT]` |
| 데이터거버넌스 | 메타·품질·데이터 흐름/영향 분석 | 대량 실데이터 ETL 자체 처리 | `[FACT + ANALYSIS]` |
| IT 서비스 및 업무지원 | SSO, Batch 운영, DevOps, Monitoring, 단말/운영 지원 | 개별 업무 서비스 로직 | `[FACT]` |

## 5.3 책임 원칙

```text
Channel은 Channel 책임만
Marketing은 Marketing 책임만
Data Platform은 Data 책임만
Governance는 Governance 책임만
IT Support는 공통 운영/통제를 담당
```

`[ANALYSIS]` 이 원칙은 “모든 기능을 하나의 통합 플랫폼으로 집중”하는 방향보다 **책임 분리 + 표준 경계 연결**을 우선한다.

---

# 6. II.2 Channel / Access Boundary

## 6.1 Channel 구성 `[FACT]`

Big Picture의 채널은 다음으로 읽힌다.

```text
Internal
 ├─ 계정 단말
 └─ 정보계 단말 / Package UI

Customer / Digital
 ├─ Web
 ├─ Mobile
 └─ SMS / PUSH / MAIL
```

## 6.2 FIG-II-03 — Channel의 세 가지 진입 의미

```text
A. 계정성 거래
────────────────────────────────────────
계정단말
   │
   ▼
MCA / 채널통합
   │
   ▼
계정 Core

B. 정보계 업무 / 조회
────────────────────────────────────────
정보계단말 / Package UI / Web
   │
   │ JSON / Service
   ▼
Marketing Web / BI Web
   │
   ▼
Information Application

C. 고객행동 Event
────────────────────────────────────────
Web / Mobile
   │ Event
   ▼
Collector
   │
   ▼
Kafka
   │
   ▼
EBM / Marketing Reaction
```

### 핵심 해석

- **계정거래**, **정보계 서비스**, **행동 Event**는 같은 종류의 진입이 아니다.
- 모든 Channel 요청을 하나의 Runtime으로 억지 통합하지 않는다.
- Web/Mobile은 화면 요청뿐 아니라 Event Producer 역할도 가질 수 있다.
- SMS/PUSH/MAIL은 고객 접촉의 **outbound 접점**으로도 나타난다.

## 6.3 Boundary 질문

| 질문 | II장 답 |
|---|---|
| 계정단말이 Marketing DB에 직접 접근하는가 | 아니오. Big Picture 근거 없음 |
| 정보계단말의 기본 업무 진입은 무엇인가 | JSON 기반 Information Application 진입 `[FACT]` |
| 고객행동 Event는 온라인 Request Thread와 동일한가 | 별도 Collector/Kafka 경로 `[FACT]` |
| 모든 채널이 MCA를 타는가 | 아님. 계정성 거래 중심 경계로 읽음 |
| Gateway의 제품/배치 위치는 확정인가 | Big Picture 수준에서는 `[OPEN]`, VIII에서 확정 |

---

# 7. II.3 Information Application Service Boundary

## 7.1 FIG-II-04 — Application Service Map

```text
┌──────────────────────── Information Application Service ─────────────────────┐
│                                                                              │
│  ┌──────────────── Marketing Platform ────────────────┐                      │
│  │                                                   │                      │
│  │ [Customer]                                        │                      │
│  │ 통합고객 / 개인고객 / 기업고객 / 미니싱글뷰       │                      │
│  │                                                   │                      │
│  │ [Sales / Product]                                 │                      │
│  │ 상담판매 / 통합상품                               │                      │
│  │                                                   │                      │
│  │ [Campaign / Event]                                │                      │
│  │ 캠페인 / EBM / 실시간처리 / 행동정보처리         │                      │
│  │                                                   │                      │
│  │ [Support / Contact]                               │                      │
│  │ 영업지원 / CS / 컨텐츠 / 메시지                  │                      │
│  └─────────────────────┬─────────────────────────────┘                      │
│                        │                                                    │
│                        │ API / JDBC / Event                                 │
│                        │                                                    │
│  ┌──────────────── BI Portal ────────────────────────┐                      │
│  │ BI포탈 / 신용실적 / OLAP / Self BI / UI·UX      │                      │
│  └─────────────────────┬─────────────────────────────┘                      │
│                        │ JDBC / Data Service                                │
│                                                                              │
│  ┌──────────── IT Service & Business Support ───────────────┐               │
│  │ SSO / Control-M / Batch / DevOps / Terminal / Monitoring │               │
│  └───────────────────────────────────────────────────────────┘               │
└──────────────────────────────────────────────────────────────────────────────┘
```

## 7.2 Marketing Platform 책임

`[FACT]` Big Picture/강화분석에서 확인되는 주요 기능군:

```text
Customer
- 통합고객
- 개인고객
- 기업고객
- 미니싱글뷰

Sales / Product
- 상담판매
- 통합상품

Campaign / Event
- 캠페인
- EBM
- 실시간처리
- 행동정보처리
- 고객행동데이터

Support / Contact
- 영업지원
- CS
- 컨텐츠
- 메시지
```

## 7.3 BI Portal 책임

`[FACT]`

```text
BI포탈
신용실적
OLAP
Self BI
신BI포털 UI/UX
```

`[FACT]` 전략정리본은 BI 포탈을 현업 분석·Self-BI·Reporting의 의사결정 공간으로 설명한다.

## 7.4 IT Service & Support 책임

`[FACT]` 전략정리본과 시스템 영역 구성자료에는 다음 운영지원 성격이 반복된다.

```text
SSO
Control-M
Framework / 공통기반
형상/배포
단말관리 / 단말배포
APM / Dashboard / 통합로그
CDC / ETL 운영지원
```

단, `pdmg-om`의 실제 구현 범위를 위 전체 목록과 동일하다고 보지 않는다. `OM` Source 검증은 IX장이다.

---

# 8. II.4 Marketing Event Processing Boundary

## 8.1 핵심 목적

실시간 마케팅은 일반 Online Request의 DB 조회 경로와 동일하지 않다.

```text
Online Request
= 사용자가 요청하고 즉시 응답을 기다리는 경로

Behavior Event
= 고객행동을 수집·전달·반응하는 Event 경로
```

## 8.2 FIG-II-05 — Marketing Event Runtime

```text
[Customer Behavior]
Web / Mobile
     │
     │ Event
     ▼
┌──────────────────────┐
│ Wise Collector Proxy │
└──────────┬───────────┘
           ▼
┌──────────────────────┐
│ Behavior Log / Event │
└──────────┬───────────┘
           ▼
┌──────────────────────┐
│ Wise Collector       │
└──────────┬───────────┘
           ▼
┌──────────────────────┐
│ Kafka                │
│ Customer Event       │
└──────────┬───────────┘
           ▼
┌──────────────────────┐
│ Real-time Processing │
│ EBM / Event Info     │
└──────────┬───────────┘
           ├──────────────► Marketing Action
           └──────────────► UMS / PUSH / Message
```

## 8.3 설계 해석

- Event 처리경로는 **DB 중심 Sync Online**과 분리되어야 한다.
- Kafka는 API의 대체품이 아니라 Event Streaming 책임이다.
- Event 처리 실패를 Online Transaction Retry로 단순 대체하지 않는다.
- Event ID, Replay, DLQ, Idempotency는 II장에서 필요성만 열어 두고 상세는 VI/IX 또는 별도 Interface 상세에 둔다.

## 8.4 `[RISK]`

```text
온라인 Request Thread에서 대량 Event 처리
        ↓
Thread 점유 증가
        ↓
응답시간 악화
        ↓
Timeout / Queue 포화
        ↓
온라인 장애로 전파
```

따라서 Event Boundary를 독립시키는 것은 단순 기술선택이 아니라 **Failure Domain 분리**다.

---

# 9. II.5 Data Platform Boundary

## 9.1 핵심 원칙

`[FACT]` NSIGHT Data Platform의 핵심은 RDW와 ADW를 구분하는 것이다.

```text
RDW
= Real-time / Near Real-time 중심
= 운영/정보제공 데이터

ADW
= Analytical 중심
= 통합/요약/집계/마트/BI
```

## 9.2 FIG-II-06 — RDW vs ADW

```text
                    ┌──────── Account / Related Source ────────┐
                    │                                         │
                    └──────────────┬──────────────────────────┘
                                   │ Change / CDC
                                   ▼
┌──────────────────────────────── RDW ────────────────────────────────┐
│                                                                    │
│ Common                                                             │
│ Real-time SoR                                                      │
│ Near Real-time Integration / Summary                               │
│ Near Real-time Report Mart                                         │
│ Marketing Information                                              │
│ Feedback                                                           │
│                                                                    │
└───────────────────────────┬────────────────────────────────────────┘
                            │
                            │ ETL / Controlled Data Movement
                            ▼
┌──────────────────────────────── ADW ────────────────────────────────┐
│                                                                    │
│ Common                                                             │
│ Analytical SoR                                                     │
│ Analytical Integration / Summary / Aggregation                     │
│ Business Mart                                                      │
│ Report Mart                                                        │
│ Analytical Support                                                 │
│ Feedback                                                           │
│                                                                    │
└───────────────────────────┬────────────────────────────────────────┘
                            │
                            ▼
                  BI / OLAP / Self BI / Report
```

## 9.3 왜 분리하는가

`[FACT]` 전략자료는 RDW/ADW 분리의 목적을 **대량 분석쿼리가 실시간 거래 성능에 영향을 주지 않도록 자원경합을 원천 차단**하는 것으로 설명한다.

```text
Real-time Workload
     ↓
    RDW

Analytical / Bulk Workload
     ↓
    ADW

두 Workload의
CPU / I/O / Session / Query 경합 분리
```

## 9.4 Application과 Data의 관계

```text
Marketing
  ├─ 실시간 고객/마케팅 정보 → RDW 중심
  └─ 분석결과 활용            → ADW/마트 소비 가능

BI
  └─ 전략분석/리포트          → ADW 중심

Governance
  └─ Meta/Quality/Flow         → RDW/ADW를 관리대상으로 관측
```

`[OPEN]` 실제 ServiceId별 RDW/ADW 접근 매트릭스는 Source/Mapper/SQL 분석 후 X장에서 닫는다.

---

# 10. II.6 Data Governance Boundary

## 10.1 책임

`[FACT]` 데이터거버넌스 영역은 다음 세 축으로 나타난다.

```text
Biz Meta
Data Quality
Data Flow / Lineage
```

## 10.2 FIG-II-07 — Governance는 적재 파이프가 아니다

```text
                  [Data Assets / Interfaces / Models]
                   RDW / ADW / ETL / CDC / Source
                               │
                               │ Meta / Profile / Flow
                               ▼
┌──────────────────────────────────────────────────────┐
│                Data Governance                      │
│                                                      │
│  Biz Meta      Data Quality      Data Flow           │
│  용어/정의      CTQ/DQI/Profile   Lineage/Impact      │
│                                                      │
└───────────────────────┬──────────────────────────────┘
                        │
                        ▼
           Search / Analysis / Standard / Evidence
```

## 10.3 실데이터 vs 관리정보

```text
[실데이터]
Source → CDC/ETL → RDW/ADW

[관리정보]
CDC/ETL/DB/Model Metadata → Governance

[업무의미]
Business Term / Standard → Biz Meta
```

`[ANALYSIS]` 데이터흐름관리 자체를 ETL 엔진으로 해석하면 책임경계가 무너진다.

---

# 11. II.7 Related / Core / Big Data / External Boundary

## 11.1 Big Picture에서의 외부/인접 영역

```text
Account / Core
Account Integration
Management
Risk
Information-related systems
Big Data
Affiliates
External Institutions
```

## 11.2 책임 원칙

- Core의 원장/업무책임은 NSIGHT가 소유하지 않는다.
- NSIGHT는 필요한 데이터를 **승인된 Interface Boundary**를 통해 사용한다.
- External은 내부 DB에 직접 접근하지 않는다.
- Big Data는 NSIGHT와 중복영역이 있을 수 있으나 Big Picture에서 별도 생태계로 표현되므로 자동 흡수하지 않는다.

## 11.3 External Flow

```text
NSIGHT
  │
  ├─ API/MCA ─────────────► Related/Core Service
  ├─ FOS/MFT/File ────────► External / Affiliate
  ├─ CDC ◄──────────────── Account Source Change
  └─ ETL ◄───────────────► Analytical/Bulk Exchange
```

---

# 12. II.8 Integration Mechanism by Purpose

## 12.1 핵심 결론

인터페이스의 상위 원칙은 다음이다.

> **연결을 하나의 기술로 통일하지 않고, 업무 의미와 데이터 특성에 따라 적합한 Integration Mechanism을 선택한다.**

## 12.2 FIG-II-08 — 목적별 Interface Map

```text
┌─────────────────────┬────────────────────┬─────────────────────┐
│ Business Purpose    │ Standard Mechanism │ Main Responsibility │
├─────────────────────┼────────────────────┼─────────────────────┤
│ 계정성 Transaction │ MCA                │ Transaction Boundary│
│ Online Service      │ API / JSON         │ Service Contract    │
│ Event / Behavior    │ Kafka              │ Async Event Stream  │
│ Change Data         │ CDC                │ DB Change Capture   │
│ Bulk / Analytics    │ ETL                │ Large Data Movement │
│ File / External     │ FOS / MFT / File   │ File Boundary       │
│ Data Access         │ Controlled JDBC    │ Owned/Approved Data │
└─────────────────────┴────────────────────┴─────────────────────┘
```

## 12.3 선택 Decision Tree

```text
연계 요구 발생
  │
  ├─ 계정성 거래인가?
  │     └─ YES → MCA 후보
  │
  ├─ 즉시 서비스 응답이 필요한가?
  │     └─ YES → API/JSON 후보
  │
  ├─ 비동기 Event인가?
  │     └─ YES → Kafka 후보
  │
  ├─ Source DB 변경 복제인가?
  │     └─ YES → CDC 후보
  │
  ├─ 대량/분석 적재인가?
  │     └─ YES → ETL 후보
  │
  ├─ 파일/기관 마감인가?
  │     └─ YES → FOS/MFT/File
  │
  └─ 승인된 자기/공유 Data Access인가?
        └─ Controlled JDBC
```

## 12.4 규격 통제와 목적별 연계의 관계 `[CONFLICT/OPEN]`

2026-03 전략자료에는 한편으로 다음 표현이 있다.

```text
"모든 통신 HTTP/JSON 방식으로 통일"
"비표준 전문 전면 차단"
```

동시에 LTI/Big Picture에는 다음이 있다.

```text
Kafka
CDC
ETL
FOS / MFT
MCA
```

따라서 II장에서는 이를 다음과 같이 **임의로 화해시키지 않고** Open Issue로 둔다.

```text
[OPEN-II-01]
"HTTP/JSON 통일"의 Scope는
A. 온라인 Service Message 규격인가?
B. 모든 시스템간 Data Movement인가?

목적별 LTI와 충돌하지 않도록
공식 적용 범위 확정 필요
```

`[PROPOSED]` 현재 Working Baseline에서는 **온라인 메시지 계약은 HTTP/JSON 중심, Event/CDC/ETL/File은 목적별 Mechanism**으로 해석하는 것이 구조적으로 정합하지만, 이는 승인된 Decision으로 승격하기 전 ADR이 필요하다.

---

# 13. II.9 Application Responsibility vs Data Responsibility

## 13.1 FIG-II-09 — 책임 분리

```text
┌──────────────── Application Responsibility ────────────────┐
│                                                            │
│ Channel UI                                                 │
│ Marketing Use Case                                         │
│ BI / Reporting                                             │
│ Authorization / Service Control                            │
│ Runtime / TCF / Business Logic                             │
│                                                            │
└───────────────────────┬────────────────────────────────────┘
                        │ Contracted Data Access
                        ▼
┌────────────────────── Data Responsibility ─────────────────┐
│                                                            │
│ RDW Real-time / SoR                                        │
│ ADW Analytical / Mart                                      │
│ Data Quality / Standard                                    │
│ Data Movement CDC / ETL                                    │
│ Data Retention / Consistency                               │
│                                                            │
└────────────────────────────────────────────────────────────┘
```

## 13.2 금지되는 책임 혼합

```text
Channel
  └─ 타 시스템 DB 직접 DML      X

Marketing Handler
  └─ ADW 대량 배치 실행          X (정상 Online Pattern 아님)

BI Query
  └─ RDW 실시간 운영부하 잠식    X / 통제 대상

Governance
  └─ 실제 ETL 업무로직 소유       X
```

## 13.3 승인된 JDBC의 의미

`JDBC`를 `어디서든 DB에 직접 연결 가능`으로 해석하지 않는다.

```text
Controlled JDBC
=
자기 책임 DB
또는
승인된 Data Platform Access
+
권한
+
Connection/Timeout
+
SQL/Mapper 표준
+
Trace
```

구체적 JDBC/DAO/Mapper 통제는 III~VI 및 X장에서 닫는다.

---

# 14. II.10 Infrastructure / Execution Boundary

## 14.1 Big Picture와 물리구조를 구분한다

Big Picture의 공간책임:

```text
Channel
Integration
Application
Data
Governance
External
Operations
```

물리 실행 Working Baseline:

```text
User
 ↓
GSLB
 ↓
L4
 ↓
Apache
 ↓
Tomcat / JVM
 ↓
Business WAR
 ↓
Hikari / MyBatis
 ↓
RDW / ADW
```

후자는 프로젝트에서 반복적으로 사용된 인프라 기준이지만 **VIII장에서 Server/JVM/WAR/Port/Pool까지 Source로 재검증**한다.

## 14.2 FIG-II-10 — Logical Space → Execution Space

```text
[Logical Big Picture]
Channel
   ↓
Information Service
   ↓
Data / Interface
   ↓
Operations

          │ Mapping
          ▼

[Execution Working Baseline]
Client
   ↓
GSLB / L4
   ↓
WEB / Apache
   ↓
WAS / Tomcat JVM
   ↓
Business WAR / PDMG
   ↓
TCF / Business
   ↓
DB / External
```

## 14.3 세 가지 경계

```text
Network / Process Boundary
Client ─HTTP─> WEB/WAS/JWT/API

JVM / Module Boundary
Business Application
 └─ Framework Bean / Filter / Interceptor / AOP

Data Boundary
Application ─JDBC/MyBatis─> DB
```

PDMG 분석에서도 이 세 경계를 혼동하지 않는 것이 중요하다.

---

# 15. II.11 Security Boundary

## 15.1 핵심 목적

Security는 하나의 `보안 서버`가 아니라 각 경계에서 다른 책임을 가진다.

## 15.2 FIG-II-11 — Security Cross-cutting

```text
User / Channel
  │
  ├─ Authentication / SSO
  ▼
Access Boundary
  │
  ├─ Token / Session / Request Validation
  ▼
Application
  │
  ├─ Function / Service Authorization
  ├─ Input Validation
  ▼
Data Access
  │
  ├─ Data Authorization
  ├─ Masking / Encryption
  ▼
Interface
  │
  ├─ TLS / Contract / Partner Trust
  ▼
Operation
     Audit / Security Log / Key / Secret
```

## 15.3 II장 확정 범위

`[FACT]` 전략자료:

```text
물리 망분리
SSO 통합인증
구간 암호화
개인정보 마스킹
설계 단계 보안 내재화
```

`[OPEN]`

```text
JWT 검증 위치
Gateway 우회 방어
Session vs JWT
Key/JWKS
ServiceId 권한
Data 권한
```

위 항목은 VII장에서 PDMG Source와 연결한다.

---

# 16. II.12 Observability Boundary

## 16.1 핵심 원칙

`[FACT]` NFR의 관측성 목표는 다음을 포함한다.

```text
APM
GUID / Trace-ID
전사 통합 로그
알림
전구간 거래 추적
```

## 16.2 FIG-II-12 — End-to-End Evidence

```text
Client
  │
  │ GUID / Trace
  ▼
Access / WEB / WAS
  │
  ▼
Business / PDMG
  │
  │ ServiceId
  ▼
DB / External / Event
  │
  ▼
Response
  │
  └─────────────────────────────┐
                                ▼
                     Log / Metric / Trace / Audit
                                │
                                ▼
                    OM / APM / Dashboard / Alert
```

## 16.3 관측 질문

Big Picture가 운영 가능하려면 최소 다음 질문에 답할 수 있어야 한다.

```text
어떤 채널에서 시작했는가?
어떤 ServiceId였는가?
어느 Business WAR/JVM에서 실행됐는가?
어느 DB/SQL을 사용했는가?
어느 외부 Interface를 호출했는가?
Event인가 Online인가?
어디에서 Timeout/Failure가 발생했는가?
응답은 어떤 Error Code로 끝났는가?
```

세부 로그와 Context 구조는 VI, OM/Metric은 IX, ServiceId Closed Loop는 X에서 닫는다.

---

# 17. II.13 PDMG Reference Position

## 17.1 가장 중요한 구분

```text
NSIGHT Big Picture
= Enterprise / Domain / Boundary / Target Architecture

PDMG
= Information Application Runtime Reference
```

## 17.2 FIG-II-13 — PDMG가 들어가는 정확한 위치

```text
┌──────────────────────────── NSIGHT ────────────────────────────────┐
│                                                                    │
│ Channel                                                            │
│    │                                                               │
│    ▼                                                               │
│ Access / Integration                                               │
│    │                                                               │
│    ▼                                                               │
│ ┌──────────────── Information Application ──────────────────────┐  │
│ │                                                               │  │
│ │ Marketing / Business WAR                                      │  │
│ │      │                                                        │  │
│ │      ▼                                                        │  │
│ │  ┌────────────── PDMG Reference ────────────────┐             │  │
│ │  │ pdmg-ui / pdmg-jwt                           │             │  │
│ │  │ pdmg-fw / pdmg-service / pdmg-om             │             │  │
│ │  │ Filter / TCF / Handler / Business / DAO      │             │  │
│ │  └─────────────────────┬────────────────────────┘             │  │
│ │                        │                                      │  │
│ └────────────────────────┼──────────────────────────────────────┘  │
│                          ▼                                         │
│                 RDW / ADW / External                               │
│                                                                    │
│ Event / Kafka        Governance        BI                           │
│ = PDMG와 별도 책임공간                                              │
└────────────────────────────────────────────────────────────────────┘
```

## 17.3 PDMG가 소유하지 않는 것

```text
전체 RDW/ADW Data Platform 설계
Kafka Platform 전체 운영
CDC 전체 동기화 체계
ETL 전체 Batch Platform
BI Portal 전체
Data Governance 전체
Core Banking
External Institution Integration 전체
```

## 17.4 PDMG가 증명할 수 있는 것

```text
온라인 요청 진입
JWT/Context 일부
ServiceId 라우팅
TCF 실행
Timeout / Transaction
Handler / Facade / Service / DAO
MyBatis / DB 접근
표준 응답 / 오류
Runtime Log / Trace의 일부
```

따라서 III장부터는 **Big Picture의 Information Application Box를 Source 수준으로 확대**한다.

---

# 18. II.14 Forbidden Pattern / Failure Domain

## 18.1 FIG-II-14 — 금지/위험 패턴

```text
[1] P2P / DB-Link
Application A ───────────────► B DB
            직접 Read/Write
                 X

[2] Channel Direct DB
Channel ─────────────────────► RDW/ADW
                 X

[3] Online Thread for Bulk
Online Request
   └─ 대량 ETL / File / Event 처리
                 X

[4] RDW/ADW Resource Collapse
BI Heavy Query
   └─ RDW Real-time Resource 점유
                 X

[5] Governance = ETL
Data Flow Tool
   └─ 실데이터 적재엔진으로 대체
                 X

[6] PDMG = 전체 NSIGHT
PDMG Runtime
   └─ Kafka/CDC/BI/Governance 전체를 소유
                 X
```

## 18.2 장애영역을 나누는 이유

```text
Channel Failure
Integration Failure
WEB/WAS Failure
Business Runtime Failure
Event Platform Failure
RDW Failure
ADW Failure
Governance Failure
External Dependency Failure
Center Failure
```

Big Picture가 좋은 이유는 장애가 났을 때 “전체 정보계가 느리다”가 아니라 **어느 책임공간이 실패했는지** 말할 수 있기 때문이다.

## 18.3 Retry의 상위 원칙

II장에서는 Retry 상세를 확정하지 않는다.

```text
SYNC API
  → 제한적 Retry / Timeout Budget 필요

Event
  → Consumer Retry / DLQ / Replay 가능성

CDC
  → Replication Recovery / Reconciliation

ETL
  → Batch Restart / Checkpoint

File
  → Re-send / Reconciliation
```

상세 정책은 인터페이스 정의 및 VI/IX의 Error/Operations에서 결정한다.

---

# 19. End-to-End 대표 시나리오

## 19.1 시나리오 A — 정보계 온라인 조회

```text
User
  ↓
정보계 단말 / Web
  ↓ JSON
Information Application
  ↓
Marketing / Business Runtime
  ↓ Controlled JDBC
RDW
  ↓
Response
  ↓
GUID / ServiceId / Log
```

### 책임

| 단계 | 책임 |
|---|---|
| UI | 요청 작성/표시 |
| Access | 인증·경계 |
| Application | Use Case / Business |
| RDW | 실시간 데이터 제공 |
| Observability | Trace/Evidence |

---

## 19.2 시나리오 B — 고객행동 실시간 반응

```text
Customer Behavior
  ↓
Web / Mobile
  ↓ Event
Collector
  ↓
Kafka
  ↓
EBM / Rule
  ↓
Marketing Reaction
  ↓
PUSH / Message
```

이 경로는 PDMG 일반 Sync Online Runtime과 동일시하지 않는다.

---

## 19.3 시나리오 C — 계정계 변경데이터 → RDW

```text
Account Source DB
  ↓ Change
CDC
  ↓
CDC Relay / Boundary
  ↓
RDW
  ↓
Marketing / Information Service
```

CDC SLA는 자료간 충돌이 있어 I장에서 `[CONFLICT]`로 등록되어 있다. II장에서는 연결구조만 확정한다.

---

## 19.4 시나리오 D — RDW → ADW → BI

```text
RDW
  ↓
ETL
  ↓
ADW
  ↓
Integration / Aggregation
  ↓
Business Mart / Report Mart
  ↓
BI / OLAP / Self BI
```

이는 FAST와 다른 **DEEP / Analytical 경로**다.

---

## 19.5 시나리오 E — 외부기관 File

```text
NSIGHT / Related
  ↓
FOS / MFT / File Boundary
  ↓
Affiliate / External Institution
  ↓
Receipt / Deadline / Reconciliation
```

Online API 플랫폼을 대용량 기관 File 전송수단으로 사용하지 않는다.

---

# 20. Cross-Cutting Responsibility Matrix

| Cross-cutting | Channel | Integration | Application | Data | Governance | Operation |
|---|:---:|:---:|:---:|:---:|:---:|:---:|
| Authentication | ● | ● | ● |  |  | ● |
| Authorization |  | ● | ● | ● | ● | ● |
| GUID / Trace | ● | ● | ● | ● | ● | ● |
| Timeout |  | ● | ● | ● |  | ● |
| Error | ● | ● | ● | ● | ● | ● |
| Encryption | ● | ● | ● | ● | ● | ● |
| Masking |  |  | ● | ● | ● |  |
| Monitoring |  | ● | ● | ● | ● | ● |
| Audit |  | ● | ● | ● | ● | ● |
| Config/Policy |  | ● | ● | ● | ● | ● |

`●`는 “반드시 실제 구현이 이 위치에 있다”가 아니라 **이 책임이 해당 Boundary와 교차한다**는 의미다. 실제 Owner/Component는 후속 장에서 확정한다.

---

# 21. Big Picture 설계 규칙

## 21.1 채택 규칙

1. **책임을 공간에 고정한다.**
2. **연결은 경계에서 통제한다.**
3. Channel, Application, Data, Governance 책임을 섞지 않는다.
4. Online, Event, CDC, ETL, File을 목적에 맞게 분리한다.
5. RDW와 ADW의 Workload/Resource 책임을 분리한다.
6. P2P Direct DB/DB-Link를 정상패턴으로 두지 않는다.
7. PDMG를 Information Application Runtime Reference로 사용한다.
8. Security/Observability는 전 구간 Cross-cutting으로 둔다.
9. Unknown Physical Detail은 VIII장 전까지 Big Picture FACT로 올리지 않는다.
10. 모든 핵심 연결선은 후속 장에서 InterfaceId/ServiceId/Source Evidence로 추적 가능해야 한다.

## 21.2 금지 규칙

```text
Channel → 타 시스템 DB 직접 DML
External → Internal DB 직접 접근
모든 Integration을 REST 하나로 통일
Kafka를 Sync API로 사용
CDC를 Event Marketing API로 사용
ETL을 Online Transaction 경로에 넣기
FOS/MFT를 Real-time API 대체로 사용
BI Heavy Query를 RDW에 무제한 허용
PDMG AS-IS를 NSIGHT 전체 TO-BE로 자동 승격
```

---

# 22. Traceability

| II장 요소 | 상위 근거 | 후속 검증 |
|---|---|---|
| 5대 서비스 도메인 | I장 / 2026-03 전략 | III/VIII/IX |
| Channel 3가지 진입 | Big Picture | IV/VII |
| Marketing 기능군 | Big Picture | III/IV |
| BI 책임 | Big Picture | VIII/IX |
| RDW/ADW 분리 | I장 NFR/전략 | VIII/X |
| Event/Kafka | Big Picture/LTI | Interface/IX |
| CDC | Big Picture/LTI | VIII/X |
| ETL | Big Picture/LTI | VIII/IX |
| File/FOS/MFT | Big Picture/LTI | Interface |
| Governance | Big Picture | IX |
| PDMG 위치 | PDMG 시스템 개요 | III~VII |
| Security Boundary | NFR | VII |
| Observability Boundary | NFR | VI/IX/X |
| GSLB/L4/Apache/Tomcat | Working Baseline | VIII |

---

# 23. 확정 / Working Baseline / GAP / OPEN / RISK

## 23.1 `[FACT]` 또는 강한 근거

- NSIGHT 5대 서비스 도메인
- Big Picture의 책임·경계 철학
- RDW/ADW 분리 방향
- Marketing / BI / Governance 주요 책임
- 계정단말·정보계단말·Web/Mobile의 상이한 진입 의미
- Kafka Event 경로
- CDC / ETL / FOS 등 목적별 연계수단 존재
- SSO/Control-M/APM/DevOps 등 IT 서비스 지원영역
- GUID/Trace 기반 관측성 방향

## 23.2 `[WORKING BASELINE]`

- Big Picture TOP-DOWN 통합본의 L0~L7 구조
- 목적별 Interface Domain 7분류
- GSLB→L4→Apache→Tomcat 상위 실행 경로
- Controlled JDBC 정의
- PDMG를 Information Application Runtime Reference로 배치

## 23.3 `[GAP]`

| ID | 내용 | 영향 |
|---|---|---|
| GAP-II-01 | 전체 Big Picture의 최신 승인본/Version SoT 확정 필요 | 모든 하위 장 |
| GAP-II-02 | 5대 도메인별 공식 Owner/RACI 미확정 | 경계 승인 |
| GAP-II-03 | Application별 공식 시스템코드/ServiceId 범위와 Big Picture 매핑 필요 | III/X |
| GAP-II-04 | Interface Catalogue의 Source/Target/InterfaceId 최신본 필요 | VI/X |
| GAP-II-05 | Big Picture의 Physical Node/Zone 최신 매핑 필요 | VIII |
| GAP-II-06 | Security Boundary의 Gateway/JWT/Session 실제 적용위치 미확정 | VII |
| GAP-II-07 | OM/APM/통합로그 실제 기능과 pdmg-om 구현범위 차이 검증 필요 | IX |

## 23.4 `[OPEN]`

| ID | 질문 |
|---|---|
| OPEN-II-01 | “모든 통신 HTTP/JSON”의 적용범위는 Online Message인가, 모든 Data Movement인가 |
| OPEN-II-02 | API Gateway/tcf-gateway의 목표 위치와 모든 Online 거래의 강제 경유 여부는 무엇인가 |
| OPEN-II-03 | 계정단말→MCA→정보계/Marketing 경로와 계정계 Core 경로를 어떻게 구분하는가 |
| OPEN-II-04 | RDW 직접 JDBC 허용 Application/ServiceId 목록은 무엇인가 |
| OPEN-II-05 | Big Data와 ADW/Marketing의 공식 책임경계는 어디인가 |
| OPEN-II-06 | Data Governance의 BizMeta/Data Quality/Data Flow 별 Owner는 누구인가 |
| OPEN-II-07 | DR에서 BI/Governance/IT Support의 정확한 포함범위와 서비스 수준은 무엇인가 |

## 23.5 `[RISK]`

| ID | Risk | 구조적 영향 |
|---|---|---|
| RISK-II-01 | P2P/DB-Link 잔존 | 경계 우회, 변경영향 증가 |
| RISK-II-02 | Online/Event/Bulk 경로 혼합 | Thread/Resource 경합 |
| RISK-II-03 | RDW에 BI Heavy Query 집중 | 실시간 성능 저하 |
| RISK-II-04 | PDMG/TCF 책임 과대확장 | Platform 책임 중복 |
| RISK-II-05 | Gateway 우회 가능 | 인증/인가 통제 분산 |
| RISK-II-06 | GUID/ServiceId 단절 | End-to-End 장애 추적 불가 |
| RISK-II-07 | Governance와 ETL 책임혼합 | 메타/실데이터 처리 혼선 |
| RISK-II-08 | Big Picture와 Physical Inventory Drift | 문서와 운영 불일치 |

---

# 24. ADR 후보

| ADR | 질문 | 선행 증거 |
|---|---|---|
| ADR-II-01 | NSIGHT Online Interface의 표준은 HTTP/JSON인가 API Gateway 강제경유인가 | LTI, Gateway 구성 |
| ADR-II-02 | P2P/Direct DB 예외를 완전 금지할 것인가 승인형 예외로 둘 것인가 | 현행 IF Inventory |
| ADR-II-03 | RDW 직접조회와 Data Service/API 조회의 허용기준은 무엇인가 | Mapper/SQL/성능 |
| ADR-II-04 | PDMG를 NSIGHT 표준 Reference로 승격할 범위는 어디까지인가 | III~VII Source |
| ADR-II-05 | Kafka/CDC/ETL/FOS의 Control Plane과 운영 Owner를 어떻게 나눌 것인가 | IX 운영조직 |
| ADR-II-06 | Big Data와 ADW의 분석데이터 책임경계를 어떻게 고정할 것인가 | Data Architecture |
| ADR-II-07 | Gateway 미경유 내부 호출의 인증/인가를 어떻게 보장할 것인가 | VII Security |

---

# 25. FIG-II-15 — II장 Handoff

```text
I. Vision & Strategy
       │
       ▼
II. Big Picture & System Boundary
       │
       ├─ 5대 Domain
       ├─ Channel / Access
       ├─ Marketing / BI
       ├─ Event
       ├─ RDW / ADW
       ├─ Governance
       ├─ Integration by Purpose
       ├─ Security / Observability
       └─ PDMG Reference Position
              │
              ▼
III. PDMG Module / Application Architecture
              │
              ├─ pdmg-ui
              ├─ pdmg-jwt
              ├─ pdmg-fw
              ├─ pdmg-service
              ├─ pdmg-om
              │
              ├─ Build Dependency
              ├─ Runtime Process Boundary
              ├─ Spring ApplicationContext
              └─ Framework vs Business Responsibility
```

### 다음 장에서 반드시 답할 질문

1. `pdmg-ui / pdmg-jwt / pdmg-fw / pdmg-service / pdmg-om`은 Build와 Runtime에서 실제로 어떻게 연결되는가?
2. 모듈 경계와 Process/JVM 경계는 어디에서 다른가?
3. `pdmg-fw`는 별도 Remote System인가, 동일 ApplicationContext 내부 Framework인가?
4. Business Layer의 실제 책임은 Handler/Facade/Service/DAO 어디에 있는가?
5. `Rule` 계층은 AS-IS Source에 실제 일반화되어 있는가?
6. PDMG AS-IS 중 어느 Mechanism을 NSIGHT TO-BE Standard로 승격할 수 있는가?
7. TCF ON/OFF 경로가 Big Picture의 Online Responsibility에 어떤 영향을 주는가?

---

# 26. 검증 체크리스트

## 26.1 Big Picture

- [x] 5대 서비스 도메인이 모두 존재하는가
- [x] Channel → Application → Data → Governance → External이 연결되는가
- [x] Marketing과 BI 책임이 분리되는가
- [x] RDW와 ADW가 분리되는가
- [x] Event 경로가 Online 경로와 분리되는가
- [x] PDMG가 전체 NSIGHT로 과대 표현되지 않았는가

## 26.2 Boundary

- [x] Core/External이 NSIGHT 내부 책임으로 흡수되지 않았는가
- [x] Channel→DB 직접접근이 정상패턴으로 존재하지 않는가
- [x] Governance가 ETL 엔진으로 표현되지 않았는가
- [x] Controlled JDBC가 무제한 Direct DB로 표현되지 않았는가
- [x] Physical Working Baseline과 Big Picture FACT를 구분했는가

## 26.3 Interface

- [x] API/MCA/Kafka/CDC/ETL/File이 목적별로 분리되는가
- [x] 모든 연계를 REST 하나로 통일하지 않았는가
- [x] HTTP/JSON 통일 문구의 Scope Conflict를 숨기지 않았는가
- [x] P2P/DB-Link 위험이 명시되었는가

## 26.4 Cross-cutting

- [x] Security Boundary가 존재하는가
- [x] Observability Boundary가 존재하는가
- [x] GUID/Trace/ServiceId 연결 필요성이 표시되는가
- [x] Failure Domain이 분리되는가

## 26.5 Handoff

- [x] III장으로 PDMG 5모듈과 경계 질문이 전달되는가
- [x] VIII장으로 Physical Detail을 위임했는가
- [x] VII/IX/X로 Security/OM/Trace를 전달했는가

---

# 27. Completion Gate

```text
필수 Figure Plan          : 15
실제 Text Figure          : 15

L0 Enterprise Context     PASS
L1 Domain Responsibility PASS
L2 Application Boundary  PASS
L2 Data Boundary         PASS
L2 Integration Boundary  PASS
L3 Event/Data Flow       PASS
L4 Security              PASS
L4 Observability         PASS
L4 Failure/Forbidden     PASS
PDMG Reference 분리       PASS
GAP/OPEN/RISK/ADR        PASS
III장 Handoff             PASS
확인되지 않은 물리수치     0건
```

**판정: CONDITIONAL PASS**

### PASS 전환 조건

```text
Condition-II-01
최신 Big Picture 승인본 / Version SoT 확정

Condition-II-02
5대 도메인 Owner / RACI 확정

Condition-II-03
Interface Catalogue + InterfaceId 매핑 확보

Condition-II-04
HTTP/JSON 규격통제와 목적별 LTI의 공식 Scope 정리

Condition-II-05
PDMG 5모듈 실제 Source Snapshot 기준 III장 검증
```

---

# 28. 장 최종 평가

II장은 NSIGHT 전략을 실제 **공간·책임·경계·연결선**으로 변환했다.

가장 중요한 결론은 다음 다섯 문장이다.

> **첫째, NSIGHT는 5대 서비스 도메인의 책임을 분리한다.**  
> **둘째, Channel의 계정거래·정보계 서비스·고객행동 Event는 서로 다른 진입경로다.**  
> **셋째, Online/API·Event/Kafka·Change/CDC·Bulk/ETL·File/FOS/MFT를 목적에 따라 분리한다.**  
> **넷째, RDW와 ADW는 실시간과 분석 Workload를 분리하여 자원경합을 통제한다.**  
> **다섯째, PDMG는 이 전체 아키텍처의 Information Application Runtime을 Source로 증명하는 Reference이지 NSIGHT 전체 그 자체가 아니다.**

따라서 III장부터는 Big Picture의 한 상자인 **Information Application / PDMG**를 확대하여 모듈·프로세스·Spring Context·업무/Framework 책임을 Source 수준으로 검증한다.
