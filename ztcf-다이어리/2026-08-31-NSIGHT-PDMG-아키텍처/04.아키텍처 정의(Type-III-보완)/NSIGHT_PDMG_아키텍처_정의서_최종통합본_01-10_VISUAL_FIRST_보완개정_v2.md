# NSIGHT / PDMG 아키텍처 정의서 — 최종 통합본 보완개정 v2
## 01 VISION → 10 INTEGRATED ARCHITECTURE BASELINE

> **Visual-First / Top-down / Drill-down / Bottom-up Evidence / 주요 절 TEXT Architecture Figure Coverage 100%**

---

# PART 0. MASTER INDEX

# NSIGHT / PDMG 아키텍처 정의서
# 00. MASTER INDEX — 보완개정 v2

```text
01 VISION
 ↓
02 BIG PICTURE
 ↓
03 LOGICAL
 ↓
04 PHYSICAL
 ↓
05 MECHANISM
 ↓
06 RUNTIME
 ↓
07 TRACEABILITY / CLOSED LOOP
 ↓
08 PDMG SOURCE REFERENCE
 ↓
09 OM / DEVOPS / OBSERVABILITY
 ↓
10 INTEGRATED BASELINE
```

## 보완개정본 파일

| 장 | 파일 | Lines | Figures/Text Diagrams | Diagramless H1 |
|---:|---|---:|---:|---:|
| 01 | `NSIGHT_PDMG_아키텍처_정의서_01_VISION_VISUAL_FIRST_TOPDOWN_상세본_보완개정본_v2.md` | 1,445 | 39 | 1 |
| 02 | `NSIGHT_PDMG_아키텍처_정의서_02_BIG_PICTURE_VISUAL_FIRST_TOPDOWN_상세본_보완개정본_v2.md` | 2,652 | 77 | 1 |
| 03 | `NSIGHT_PDMG_아키텍처_정의서_03_LOGICAL_VISUAL_FIRST_TOPDOWN_상세본_보완개정본_v2.md` | 3,015 | 91 | 1 |
| 04 | `NSIGHT_PDMG_아키텍처_정의서_04_PHYSICAL_VISUAL_FIRST_TOPDOWN_상세본_보완개정본_v2.md` | 3,300 | 94 | 1 |
| 05 | `NSIGHT_PDMG_아키텍처_정의서_05_MECHANISM_VISUAL_FIRST_TOPDOWN_상세본_보완개정본_v2.md` | 2,892 | 96 | 1 |
| 06 | `NSIGHT_PDMG_아키텍처_정의서_06_RUNTIME_VISUAL_FIRST_TOPDOWN_상세본_보완개정본_v2.md` | 3,846 | 96 | 1 |
| 07 | `NSIGHT_PDMG_아키텍처_정의서_07_TRACEABILITY_EVIDENCE_CLOSED_LOOP_VISUAL_FIRST_TOPDOWN_상세본_보완개정본_v2.md` | 3,794 | 79 | 1 |
| 08 | `NSIGHT_PDMG_아키텍처_정의서_08_PDMG_SOURCE_RUNTIME_REFERENCE_DEEP_DIVE_VISUAL_FIRST_상세본_보완개정본_v2.md` | 4,117 | 90 | 1 |
| 09 | `NSIGHT_PDMG_아키텍처_정의서_09_OM_DEVOPS_OBSERVABILITY_OPERATIONS_VISUAL_FIRST_상세본_보완개정본_v2.md` | 4,380 | 91 | 1 |
| 10 | `NSIGHT_PDMG_아키텍처_정의서_10_INTEGRATED_ARCHITECTURE_BASELINE_NAMING_SERVICEID_TRACEABILITY_VISUAL_FIRST_상세본_보완개정본_v2.md` | 4,485 | 85 | 2 |

## 최종 점검 핵심

```text
Reference Coverage
   +
TEXT Figure Coverage
   +
Evidence State Consistency
   +
Cross-Chapter Handoff
   +
Closed Loop / HG90
   ↓
보완개정 Architecture Baseline
```

## 최종 TEXT Figure Coverage 재검증

| 장 | 주요 번호 절 누락 | Fence |
|---:|---:|---|
| 01 | 0 | PASS |
| 02 | 0 | PASS |
| 03 | 0 | PASS |
| 04 | 0 | PASS |
| 05 | 0 | PASS |
| 06 | 0 | PASS |
| 07 | 0 | PASS |
| 08 | 0 | PASS |
| 09 | 0 | PASS |
| 10 | 0 | PASS |

> **01~10장 번호가 있는 주요 절 기준 TEXT Figure 누락 0건.**


---

# PART 0-1. 보완점검 REPORT

# NSIGHT / PDMG 아키텍처 정의서
# 01~10장 보완점검 REPORT v2

> 점검 범위: Visual-First 01~10장 전체  
> 점검 기준: **Reference Coverage / TEXT Architecture Figure Coverage / Evidence State / Cross-Chapter Handoff / AS-IS↔TO-BE 분리**

---

# 1. 종합 점검 결과

```text
기존 최종본
  ↓
장간 Storyline / Runtime Closed Loop       : 양호
  ↓
표·RACI·Register·DoD 일부 TEXT 그림 부재  : 보완 필요
  ↓
TYPE2 세부 Reference 일부 축약             : 보완 필요
  ↓
AS-IS / TO-BE / GAP 구분                   : 유지
  ↓
보완개정본 v2
```

## 주요 보완 영역

1. **01 VISION** — 데이터기반 의사결정, FAST/DEEP 실시간, 이해관계자, DevOps/Traceability Vision, Timeout Conflict 시각화 보강
2. **02 BIG PICTURE** — MP 상세 업무분류, 6개 데이터 주제영역, 전체 System Architecture, Logical Server Candidate 경계 보강
3. **03 LOGICAL** — 환경별 구축범위, Cross-cutting 기술컴포넌트, 데이터 소유, MP/Data/BI/DG Logical Map 보강
4. **04 PHYSICAL** — Center≠Environment, HW/SW Inventory, RAC/OGG, OLTP/Batch, Hostname/Filesystem/Account/Port 표준 복원
5. **05 MECHANISM** — Online IF 표준경로, GSE, File/Data Interface, Cross-App Call, xDataSet, RD Reporting, JobRepository, Solution/OLAP/Package 예외 보강
6. **06 RUNTIME** — Monitoring, DB HA/DR, 8 Failure Scenario, Scalability, RTO/RPO, Go-live Blocker 시각화 보강
7. **07~10** — RACI/Metric/Master Matrix/Conclusion 등 표·문장형 절을 TEXT Figure로 보완

---

# 2. 장별 정량 점검

| 장 | 기존 Lines | 보완 Lines | TEXT 그림/FIG 기존 | 보완 후 | 그림 없는 H1 기존 | 보완 후 | Fence |
|---:|---:|---:|---:|---:|---:|---:|---|
| 01 | 1,243 | 1,445 | 30 | 39 | 3 | 1 | PASS |
| 02 | 2,416 | 2,652 | 65 | 77 | 7 | 1 | PASS |
| 03 | 2,773 | 3,015 | 78 | 91 | 7 | 1 | PASS |
| 04 | 2,996 | 3,300 | 79 | 94 | 6 | 1 | PASS |
| 05 | 2,617 | 2,892 | 81 | 96 | 5 | 1 | PASS |
| 06 | 3,594 | 3,846 | 83 | 96 | 7 | 1 | PASS |
| 07 | 3,629 | 3,794 | 70 | 79 | 6 | 1 | PASS |
| 08 | 3,871 | 4,117 | 77 | 90 | 9 | 1 | PASS |
| 09 | 4,139 | 4,380 | 79 | 91 | 8 | 1 | PASS |
| 10 | 4,291 | 4,485 | 76 | 85 | 7 | 2 | PASS |

---

# 3. 내용 일관성 점검

## 3.1 유지한 핵심 구분

```text
NSIGHT = Target Architecture / Strategy / Baseline
                     │
                     │ compare
                     ▼
PDMG   = AS-IS / Source / Runtime Reference
                     │
                     ▼
CONFORM / GAP / DRIFT / ADR
```

## 3.2 자동 승격 금지

```text
PDMG Source에 존재
      ↓
NSIGHT TO-BE 표준

자동 승격 X

Evidence → NFR Fit → Scope Fit → Security/Ops Fit → ADR → Approval
```

## 3.3 수치/제품/포트의 상태

```text
Source/Config Snapshot
       ≠
Capacity Candidate
       ≠
Approved Target SLA / Baseline
```

5000ms / Worker20 / Queue100, Session 60/90, CDC 30s/3s 등은 기존 상태태그를 유지했다.

---

# 4. 최종 보완 원칙

```text
Major Section
  ↓
TEXT Architecture Figure
  ↓
Minimal Explanation
  ↓
FACT / AS-IS / TO-BE / GAP / CONFLICT
  ↓
Drill-down / Handoff
```

> 이번 v2는 원본 01~10장을 삭제하거나 덮어쓰지 않고 별도 보완개정본으로 생성하였다.

---

# 5. 최종 재검증

```text
번호가 있는 주요 절
  ↓
TEXT Architecture Figure 존재 여부 검사
  ↓
01장 : 누락 0건
02장 : 누락 0건
03장 : 누락 0건
04장 : 누락 0건
05장 : 누락 0건
06장 : 누락 0건
07장 : 누락 0건
08장 : 누락 0건
09장 : 누락 0건
10장 : 누락 0건
```

> **최종 재검증 결과: 번호가 있는 주요 내용 절의 TEXT Architecture Figure 누락은 0건이다.**


---

# PART I. 01~10장 보완개정본


====================================================================================================

# CHAPTER 01

====================================================================================================

# NSIGHT / PDMG 아키텍처 정의서
# 01. VISION — Architecture Vision & Strategy

> 보완개정: **v2 — TEXT Architecture Figure Coverage / Reference Coverage / Evidence Consistency 재점검**
## TEXT ARCHITECTURE 보완 — 장 전체 위치

```text
VISION
   ↓
Change Driver
   ↓
Vision
   ↓
Principle
   ↓
NFR
   ↓
Target Direction
   ↓
BIG PICTURE
```

## Visual-First / Top-down / Drill-down 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-VISUAL-VISION-01`  
> Architecture Level: **VISION / L0**  
> 문서 상태: **Draft / Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 작성 원칙: **그림 우선 → 짧은 해설 → Evidence / GAP → 다음 단계 Drill-down**  
> 상위 기준: NSIGHT Target Architecture  
> 하위 Reference: PDMG AS-IS / Source / Runtime Evidence  
> 후속 장: **02. BIG PICTURE**

---

# 0. 이 문서를 읽는 방법

이 장은 기술 제품이나 Framework부터 설명하지 않는다.

```text
WHY
왜 바꾸는가?
   ↓
WHAT
무엇을 달성해야 하는가?
   ↓
RULE
어떤 원칙을 지켜야 하는가?
   ↓
QUALITY
어떤 NFR을 만족해야 하는가?
   ↓
ROUTE
후속 Architecture가 무엇을 구체화해야 하는가?
   ↓
EVIDENCE
무엇으로 실제 구현을 검증할 것인가?
```

이 장에서 중요한 것은 **VISION 자체가 다음 장의 설계 계약이 되는 것**이다.

---

# 1. VISUAL ROUTE — 이 장 전체를 한 장으로 보기

## FIG-VSN-01. NSIGHT Architecture Journey

```text
╔════════════════════════════════════════════════════════════════════════════╗
║                     NSIGHT ARCHITECTURE JOURNEY                           ║
╚════════════════════════════════════════════════════════════════════════════╝

  ① AS-IS PROBLEM
  ──────────────────────────────────────────────────────────────────────────
  Batch-Centric / 익일 데이터 / 고정 마케팅 / 데이터 분산 / 분석부하
  비표준 연계 / 운영 추적성 부족 / 서버별 독립 운영
                                  │
                                  ▼
  ② CHANGE DRIVER
  ──────────────────────────────────────────────────────────────────────────
  고객행동 즉시 반응 / 데이터 신뢰 / 통합 활용 / 운영 민첩성 / 표준화
                                  │
                                  ▼
  ③ ARCHITECTURE VISION
  ──────────────────────────────────────────────────────────────────────────
       Scalable + Resilient + Data-Centric + Standardized + Observable
                                  │
                                  ▼
  ④ STRATEGIC STRUCTURE
  ──────────────────────────────────────────────────────────────────────────
     5대 Service Domain + 3대 Control + FAST / DEEP + 5대 NFR
                                  │
                                  ▼
  ⑤ ARCHITECTURE ROUTE
  ──────────────────────────────────────────────────────────────────────────
       VISION → BIG PICTURE → LOGICAL → PHYSICAL → MECHANISM → RUNTIME
                                  │
                                  ▼
  ⑥ IMPLEMENTATION / REFERENCE
  ──────────────────────────────────────────────────────────────────────────
        Source / Config / PDMG Reference / Deployment / Runtime Evidence
                                  │
                                  ▼
  ⑦ GOVERNANCE LOOP
  ──────────────────────────────────────────────────────────────────────────
               Drift → GAP → ADR → Rule → Test → New Baseline
```

### 이 그림에서 봐야 할 것

- NSIGHT는 **시스템 증설**이 아니라 **정보계의 구조적 전환**이다.
- 아키텍처의 시작은 제품이 아니라 **문제와 책임**이다.
- 아키텍처의 끝은 문서가 아니라 **Runtime Evidence**다.
- PDMG는 이 전체 구조의 하위 Reference다.

---

# 2. WHY — 왜 기존 정보계를 바꾸는가

## FIG-VSN-02. AS-IS 구조적 문제 지도

```text
                              ┌───────────────────────┐
                              │   BATCH-CENTRIC      │
                              └──────────┬────────────┘
                                         │
                  ┌──────────────────────┼──────────────────────┐
                  ▼                      ▼                      ▼
          [Data Delay]            [Fixed Timing]        [Data Duplication]
       익일 데이터 제공           고정 시점 오퍼링       중복/분산 데이터
                  │                      │                      │
                  └──────────────┬───────┴──────────────┬──────┘
                                 ▼                      ▼
                          Slow Reaction              Low Trust
                                 │                      │
                                 └───────────┬──────────┘
                                             ▼
                                       Business Delay


                              ┌───────────────────────┐
                              │ INDEPENDENT SYSTEM    │
                              └──────────┬────────────┘
                                         │
                  ┌──────────────────────┼───────────────────────────┐
                  ▼                      ▼                           ▼
        Non-standard IF          Resource Contention         Fragmented Ops
            비표준 연계          분석/운영 자원경합         모니터링/운영 분절
                  │                      │                           │
                  └──────────────────────┼───────────────────────────┘
                                         ▼
                                  Failure / Change Impact
```

### AS-IS 핵심 문제

| 구조적 문제 | Architecture 영향 |
|---|---|
| 야간 배치 후 익일 정보 | 고객행동 기반 즉시 반응 어려움 |
| 정해진 시점 마케팅 | Event 기반 동적 오퍼링 한계 |
| 단순 DW 창고 중심 | Data → Insight → Action 연결 약함 |
| 분석 쿼리 부하 | 운영/정보제공 성능 간섭 |
| 서버별 독립 운영 | 장애 파급·확장·운영 복잡도 증가 |
| 데이터 중복·분산 | 정합성·품질·Ownership 약화 |
| 비표준 연계 | 변경/보안/장애 영향 증가 |
| App/IF/Batch 모니터링 부족 | 장애 원인 식별 지연 |
| End-to-End Trace 부족 | 화면→Service→SQL→DB 역추적 어려움 |

---

# 3. CHANGE DRIVER — 무엇이 달라져야 하는가

## FIG-VSN-03. Business Change Driver → Architecture Demand

```text
┌──────────────────────────── BUSINESS DRIVER ────────────────────────────┐
│                                                                         │
│  고객 중심 서비스 강화                                                  │
│          │                                                              │
│          └──────────────► 빠른 고객 Context / Offer / Feedback          │
│                                                                         │
│  데이터 기반 의사결정                                                    │
│          │                                                              │
│          └──────────────► Trusted Data / BI / Analysis / Decision       │
│                                                                         │
│  통합 정보 활용                                                          │
│          │                                                              │
│          └──────────────► Classification / Ownership / Common Use       │
│                                                                         │
│  실시간 정보 활용                                                        │
│          │                                                              │
│          └──────────────► FAST Path / Event / Near Real-time            │
│                                                                         │
│  운영 효율 / 민첩성                                                      │
│          │                                                              │
│          └──────────────► Standard / Automation / Observability         │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
                                   │
                                   ▼
┌──────────────────────── ARCHITECTURE DEMAND ────────────────────────────┐
│ Boundary / Standard IF / Data Role Separation / Runtime Isolation       │
│ Independent Scaling / Failure Isolation / Trace / Runtime Evidence       │
└─────────────────────────────────────────────────────────────────────────┘
```

---

# 4. TO-BE — Architecture Vision

## FIG-VSN-04. AS-IS → TO-BE Transformation

```text
┌────────────────────────────── AS-IS ──────────────────────────────┐
│                                                                  │
│  Batch 중심                                                      │
│  DW 창고 중심                                                    │
│  익일 정보 제공                                                  │
│  고정시점 마케팅                                                 │
│  데이터 분산 / 중복                                              │
│  분석 / 운영 자원 경합                                           │
│  연계 비표준                                                     │
│  낮은 End-to-End 추적성                                          │
│                                                                  │
└───────────────────────────────┬──────────────────────────────────┘
                                │
                                │ Transformation
                                ▼
┌────────────────────────────── TO-BE ─────────────────────────────┐
│                                                                  │
│  Data-Centric                                                    │
│  Near Real-time                                                  │
│  Event Driven Reaction                                           │
│  RDW / ADW Responsibility Separation                             │
│  Online / Event / Batch / ETL Resource Isolation                 │
│  Standard Interface                                              │
│  GUID / ServiceId Trace                                          │
│  Runtime Evidence-based Operation                                │
│                                                                  │
└──────────────────────────────────────────────────────────────────┘
```

## Vision Statement

> **신뢰 가능한 데이터를 기반으로 고객 행동에 빠르게 반응하고, 실시간 운영과 전략 분석을 동시에 제공하며, 확장·장애·보안·운영 상태를 Runtime Evidence로 검증할 수 있는 Data-Centric 차세대 정보계 플랫폼을 구축한다.**

---

# 5. Architecture Vision Keyword Map

## FIG-VSN-05. Vision 6 Keywords

```text
                            [ DATA-CENTRIC ]
                                  │
        ┌─────────────────────────┼─────────────────────────┐
        ▼                         ▼                         ▼
   [SCALABLE]                [RESILIENT]              [STANDARDIZED]
 독립적 확장 가능          장애영향 최소화          동일 규칙 / 계약
        │                         │                         │
        └──────────────┬──────────┴──────────────┬──────────┘
                       ▼                         ▼
                 [OBSERVABLE]                [TRACEABLE]
              상태가 보여야 함              변경/거래 추적
```

### Keyword 의미

- **Data-Centric**: 데이터가 저장만 되는 것이 아니라 반응과 의사결정에 연결되어야 한다.
- **Scalable**: Workload별 독립 확장성을 확보한다.
- **Resilient**: 장애가 전체로 파급되지 않도록 격리한다.
- **Standardized**: Interface, Message, Naming, Runtime Rule을 표준화한다.
- **Observable**: 운영 상태를 Metric/Log/Trace로 관찰할 수 있어야 한다.
- **Traceable**: Requirement부터 Runtime Evidence까지 양방향 추적 가능해야 한다.

---

# 6. BUILD DIRECTION — 구축 목표 4축

## FIG-VSN-06. Business → Architecture Goal

```text
[Customer / Service]
빠르고 일관된 고객 서비스
        │
        ▼
Marketing / Event / Channel Architecture


[Data]
신뢰 가능한 통합 데이터
        │
        ▼
RDW / ADW / Governance Architecture


[Architecture]
책임 / 경계 / 표준화
        │
        ▼
Zone / System / Interface / Framework Architecture


[Operations]
안정 / 확장 / 복구 / 관측
        │
        ▼
HA / DR / Monitoring / DevOps / Runtime Evidence
```

---

# 7. DATA-CENTRIC — FAST / DEEP 이중 트랙

## FIG-VSN-07. FAST / DEEP Strategic Architecture

```text
                                DATA-CENTRIC
                                     │
                   ┌─────────────────┴─────────────────┐
                   ▼                                   ▼
              ┌───────────┐                       ┌───────────┐
              │   FAST    │                       │   DEEP    │
              └─────┬─────┘                       └─────┬─────┘
                    │                                   │
          Customer Action                         Core/Data Change
                    │                                   │
                    ▼                                   ▼
            Event / Collector                         CDC
                    │                                   │
                    ▼                                   ▼
                 Kafka                                RDW
                    │                                   │
                    ▼                                   ▼
            Real-time Decision                         ETL
                    │                                   │
                    ▼                                   ▼
              Offer / Action                           ADW
                                                        │
                                                        ▼
                                                   BI / Analysis
```

### FAST

```text
목적
고객 행동을 빠르게 감지 → 판단 → 반응

특성
Event Driven
Low Latency
Failure Isolation
Replay / Trace 필요
```

### DEEP

```text
목적
정확성 / 통합 / 분석 / 집계 / 전략적 의사결정

특성
CDC
RDW
ETL
ADW
BI / Analytics
```

---

# 8. FAST / DEEP 자원 분리 원칙

## FIG-VSN-08. Runtime Isolation Guardrail

```text
FAST Failure
    ─────X────► DEEP 전체 중단

DEEP Heavy Query
    ─────X────► FAST Online 지연

ETL
    ─────X────► Online Worker 점유

Event Burst
    ─────X────► OLTP Thread / DB Pool 고갈
```

### 핵심 원칙

> **같은 Data Strategy를 공유하더라도 동일 Runtime Resource를 공유해야 하는 것은 아니다.**

---

# 9. 5대 서비스 도메인 방향

## FIG-VSN-09. 5 Service Domain Responsibility Map

```text
                         ┌────────────────────────────┐
                         │ IT Service & Biz Support   │
                         │ SSO / DevOps / OM / Batch │
                         └────────────┬───────────────┘
                                      │
                ┌─────────────────────┼─────────────────────┐
                ▼                     ▼                     ▼
       ┌────────────────┐    ┌────────────────┐    ┌─────────────────┐
       │   Marketing    │    │   BI Portal    │    │   Governance    │
       │ 고객/캠페인     │    │ 분석/리포트     │    │ 메타/품질/흐름   │
       │ 실시간 반응     │    │ Self BI        │    │ 기준/정합성       │
       └───────┬────────┘    └───────┬────────┘    └────────┬────────┘
               │                     │                       │
               └─────────────┬───────┴───────────────────────┘
                             ▼
                    ┌─────────────────────┐
                    │    Data Platform    │
                    │      RDW / ADW      │
                    └─────────────────────┘
```

### VISION 단계의 책임

- VISION은 **상위 책임만 고정**한다.
- 상세 Application/System 이름은 BIG PICTURE에서 정의한다.
- 이 단계에서 PDMG Module 이름이 전면에 나오면 Top-down이 무너진다.

---

# 10. 3대 통제 — BIG PICTURE에 넘기는 핵심 계약

## FIG-VSN-10. Three Architecture Controls

```text
╔════════════════════╗
║ ① 규격 표준       ║
╚════════════════════╝
Naming / Message / ServiceId / Error / GUID / Charset / Deployment

              │
              ▼

╔════════════════════╗
║ ② 인터페이스 통제 ║
╚════════════════════╝
Online / Event / CDC / ETL / File
목적별 Mechanism / Boundary 통제

              │
              ▼

╔════════════════════╗
║ ③ 자원 분리       ║
╚════════════════════╝
Online / Event / Batch / ETL / CDC / BI
CPU / Thread / Connection / DB / I/O / Failure 분리
```

---

# 11. Interface Control

## FIG-VSN-11. Purpose-based Integration Selection

```text
Transaction
    │
    └──────────────► API / MCA / Controlled Online

Event
    │
    └──────────────► Kafka / Event

DB Change
    │
    └──────────────► CDC

Bulk Data
    │
    └──────────────► ETL

File
    │
    └──────────────► FOS / MFT / Managed File
```

### 상위 금지

```text
모든 연계를 REST 하나로 통일                X
Application 간 DB 직접 DML                  X
External System의 Internal DB Direct Access X
대량 Data를 Online API로 전달               X
```

---

# 12. 6단계 Architecture 방법론

## FIG-VSN-12. Top-down Architecture Route

```text
┌──────────────────────────────┐
│ ① VISION                    │
│ 왜 바꾸는가 / 어디로 가는가 │
└──────────────┬───────────────┘
               ▼
┌──────────────────────────────┐
│ ② BIG PICTURE               │
│ 무엇을 만들고 누가 책임지는가│
└──────────────┬───────────────┘
               ▼
┌──────────────────────────────┐
│ ③ LOGICAL                   │
│ 논리적으로 어디에 둘 것인가 │
└──────────────┬───────────────┘
               ▼
┌──────────────────────────────┐
│ ④ PHYSICAL                  │
│ 어느 자원에 구현할 것인가   │
└──────────────┬───────────────┘
               ▼
┌──────────────────────────────┐
│ ⑤ MECHANISM                 │
│ 어떤 표준으로 동작할 것인가 │
└──────────────┬───────────────┘
               ▼
┌──────────────────────────────┐
│ ⑥ RUNTIME                   │
│ 실제로 어떻게 실행/실패/복구│
└──────────────────────────────┘
```

## 단계별 Definition of Done

| 단계 | 핵심 질문 | 산출 | 완료 기준 |
|---|---|---|---|
| VISION | 왜/어디로 | Vision/NFR/Principle | 방향 합의 |
| BIG PICTURE | 무엇/누가 | Domain/Application/System | 책임공간 확정 |
| LOGICAL | 어디/경계 | Zone/Node/Component | 허용/금지 연결 |
| PHYSICAL | 어디 배치 | Host/HW/SW/DB/HA | 물리 Mapping |
| MECHANISM | 어떻게 동작 | IF/Message/GUID/FW | 실행 규칙 |
| RUNTIME | 실제 동작 | Sequence/Failure/SLO | Evidence 검증 |

---

# 13. Architecture Principle Map

## FIG-VSN-13. Principle Set

```text
[RESPONSIBILITY]
 AP-01 Responsibility First
 AP-02 Boundary First

[STANDARD]
 AP-03 Standard Interface Only
 AP-04 Data Role Separation

[RUNTIME]
 AP-05 Runtime Isolation
 AP-06 Independent Scaling
 AP-07 Failure Isolation

[SECURITY / OPS]
 AP-08 Security by Design
 AP-09 Observability by Design
 AP-10 Evidence First

[GOVERNANCE]
 AP-11 Traceable Change
 AP-12 Exception Governed
 AP-13 Same Artifact Principle
 AP-14 Recovery Proven
```

### Principle 상세

| ID | 원칙 | 정의 |
|---|---|---|
| AP-01 | Responsibility First | 제품/서버보다 책임을 먼저 정의 |
| AP-02 | Boundary First | 연결보다 경계를 먼저 정의 |
| AP-03 | Standard Interface Only | 경계 통과는 승인된 표준 Interface |
| AP-04 | Data Role Separation | RDW/ADW, CDC/ETL 책임 분리 |
| AP-05 | Runtime Isolation | Online/Event/Batch/ETL 자원 경합 최소화 |
| AP-06 | Independent Scaling | Workload별 독립 확장 가능 |
| AP-07 | Failure Isolation | 장애 파급을 Domain/Node 단위로 제한 |
| AP-08 | Security by Design | 인증/인가/암호화/마스킹을 내재화 |
| AP-09 | Observability by Design | GUID/ServiceId/Metric/Log를 설계에 포함 |
| AP-10 | Evidence First | Runtime Evidence 없이 완료 아님 |
| AP-11 | Traceable Change | Requirement부터 Runtime까지 추적 |
| AP-12 | Exception Governed | 예외는 ADR/Owner/만료일 관리 |
| AP-13 | Same Artifact Principle | 환경 승격 시 동일 Artifact 지향 |
| AP-14 | Recovery Proven | Backup/DR은 복구 Test까지 증명 |

---

# 14. NFR — Non-Functional Requirement Map

## FIG-VSN-14. 5대 NFR → Architecture Impact

```text
                               ┌────────────────┐
                               │ 5대 핵심 NFR   │
                               └───────┬────────┘
                                       │
      ┌────────────────┬───────────────┼───────────────┬─────────────────┐
      ▼                ▼               ▼               ▼                 ▼
 PERFORMANCE       AVAILABILITY     SCALABILITY      SECURITY       OBSERVABILITY
      │                │               │               │                 │
      ▼                ▼               ▼               ▼                 ▼
Low Latency      Fault Isolation   Scale-out      Auth / AuthZ      GUID / ServiceId
Timeout Budget   HA / DR           Parallelism    Encryption        Log / Metric
Resource Split   Recovery          Indep. Scale   Masking           Trace / APM
```

---

# 15. NFR 1 — Performance

## FIG-VSN-15. Performance Design Chain

```text
Performance NFR
      │
      ▼
FAST / DEEP 분리
      │
      ▼
Online / Event / Batch / ETL Resource Isolation
      │
      ▼
Thread / Connection / DB Resource Budget
      │
      ▼
Timeout Budget
      │
      ▼
Load Test / p95 / Runtime Metric
```

### 핵심

- FAST 경로는 저지연이 중요하다.
- DEEP 경로는 처리량과 분석성능이 중요하다.
- 둘을 같은 자원으로 묶지 않는 것이 핵심이다.

---

# 16. Performance Baseline Conflict

## FIG-VSN-16. CDC SLA Conflict

```text
[Baseline A]
CDC <= 30 sec
      │
      │
      ├─────────────── [CONFLICT]
      │
[Baseline B]
CDC <= 3 sec
```

### 판정

```text
[CONFLICT]
최신 승인값 / 측정구간 / 대상업무 / 측정방법 재확정 필요
```

### 원칙

> **수치만으로 SLA를 결정하지 않는다. 시점 + 업무범위 + 측정구간 + 측정방법 + Owner가 함께 있어야 한다.**

---

# 17. NFR 2 — Availability

## FIG-VSN-17. Availability Drill-down

```text
Availability
    │
    ├─ Node Failure
    ├─ JVM Failure
    ├─ Host Failure
    ├─ Network Failure
    ├─ DB Failure
    └─ Center Failure
         │
         ▼
Fault Isolation
         │
         ▼
HA / DR / Recovery / Reconciliation
         │
         ▼
Failure Test Evidence
```

### 핵심

- 서버 2대가 있다고 HA가 끝난 것이 아니다.
- DR Center가 있다고 DR이 끝난 것이 아니다.
- **장애 탐지 → 격리 → 재라우팅 → 복구 → 정합성 검증**이 있어야 한다.

---

# 18. NFR 3 — Scalability

## FIG-VSN-18. Independent Scaling

```text
WEB Scale
   │
WAS Scale
   │
Worker Scale
   │
DB Connection Scale
   │
Event Consumer Scale
   │
ETL Parallelism
   │
Data / DB Scale
```

### 핵심

> **한 영역의 확장 때문에 다른 영역을 반드시 함께 확장해야 하는 구조를 최소화한다.**

---

# 19. NFR 4 — Security

## FIG-VSN-19. Security by Design

```text
User / Channel
      │
      ▼
Authentication
      │
      ▼
Authorization
      │
      ▼
Trust Boundary
      │
      ▼
Application / Data Access
      │
      ▼
Encryption / Masking / Audit
```

### 핵심

- Security는 배포 직전 점검 항목이 아니다.
- Boundary와 Mechanism 단계부터 설계에 포함되어야 한다.

---

# 20. NFR 5 — Observability

## FIG-VSN-20. Observability by Design

```text
Request / Event / Batch
        │
        ▼
GUID / ServiceId
        │
        ▼
Application / Framework
        │
        ▼
Thread / TX / SQL / External
        │
        ▼
Metric / Log / Trace / APM
        │
        ▼
Alert / Runbook / Runtime Evidence
```

### 운영에서 답해야 하는 질문

```text
어느 사용자의
어느 ServiceId가
어느 Application/JVM에서
어느 Worker / DB Pool / SQL 때문에
느렸는가?
```

---

# 21. NFR → Runtime Validation

## FIG-VSN-21. Quality Closed Chain

```text
NFR
 ↓
Architecture Decision
 ↓
Logical / Physical / Mechanism
 ↓
Configuration
 ↓
Test
 ↓
Deployment
 ↓
Metric / Log / Trace
 ↓
Validation
```

### 핵심

> **NFR은 선언문이 아니라 실행 가능한 검증 계약이어야 한다.**

---

# 22. Hybrid Physical Direction — VISION 수준

## FIG-VSN-22. Resource Strategy

```text
[Data Dedicated Resource]
RDW / ADW
→ 전용 자원 / 병렬 확장

[Service Resource]
Marketing / BI / Governance
→ Scale-out 가능한 서비스 자원

[Performance-sensitive]
ETL / CDC
→ 독립/전용 실행자원 후보

[HA / DR]
AP
→ 이중화 / 센터 활용

DB
→ 정합성 / 복제 / 운영복잡도 고려
```

### 주의

- VISION에서는 Host 수, CPU, Memory, Thread 수를 확정하지 않는다.
- 그런 값은 PHYSICAL에서 Evidence와 함께 다룬다.

---

# 23. Capacity 값을 읽는 방법

## FIG-VSN-23. Capacity Variant Guardrail

```text
Session 60분      ┐
Session 90분      ├──► Capacity Variant
16C / 128G        │
32C / 256G        │
Hikari 후보 차이  ┘

Capacity Variant
       ≠
Target Standard

Target Standard
       =
Approved Baseline
 + Actual Config
 + Performance Test
 + Runtime Metric
```

---

# 24. PDMG Reference Position

## FIG-VSN-24. NSIGHT Target ↔ PDMG AS-IS Reference

```text
┌──────────────────────────── NSIGHT TARGET ─────────────────────────────┐
│                                                                        │
│  Application                                                           │
│  Data Platform                                                         │
│  BI                                                                    │
│  Governance                                                            │
│  Integration                                                           │
│  Security                                                              │
│  Infrastructure / HA / DR                                              │
│  Operations / DevOps                                                   │
│                                                                        │
│      ┌──────────────────── PDMG REFERENCE ──────────────────────┐      │
│      │                                                          │      │
│      │ Module / Package / Layer                                 │      │
│      │ Online Runtime / TCF                                     │      │
│      │ Transaction / Timeout / Worker                            │      │
│      │ Message / Error / Logging                                 │      │
│      │ JWT / SSO                                                │      │
│      │ ServiceId / Trace                                        │      │
│      │                                                          │      │
│      └──────────────────────────────────────────────────────────┘      │
│                                                                        │
└────────────────────────────────────────────────────────────────────────┘
```

### PDMG가 증명할 수 있는 영역

```text
Module Boundary
Package / Layer
ServiceId
Handler / Facade / Service / DAO
TCF Runtime
Filter / Context
Transaction
Timeout
Worker Thread
Message
Error / Logging
JWT / SSO
GUID / Trace
```

### PDMG가 직접 증명하지 않는 영역

```text
NSIGHT 전체 5대 도메인
전체 RDW/ADW Data Platform
전사 Kafka/Event Platform
전사 ETL/CDC
BI 전체
Data Governance 전체
Enterprise DR 전체
모든 Physical Server
```

---

# 25. AS-IS / TO-BE Alignment

## FIG-VSN-25. Alignment Model

```text
[NSIGHT]
Strategy / Target / Baseline
        │
        │ Alignment
        ▼
[PDMG]
Current Source / Config / Runtime
        │
        ▼
Difference
        │
        ├─ Conform
        ├─ Drift
        ├─ GAP
        └─ ADR
```

### 판정 원칙

```text
PDMG Source가 목표와 다르면

Source       = AS-IS
Architecture = TO-BE
Difference   = GAP
```

---

# 26. Architecture Decision Lifecycle

## FIG-VSN-26. Principle → Decision → Runtime

```text
Requirement
   ↓
Principle
   ↓
Decision
   ↓
Model
   ↓
Rule
   ↓
Source / Config
   ↓
Test
   ↓
Runtime Evidence
   ↓
Drift
   ↓
GAP / ADR
   ↓
New Baseline
```

### 예시

```text
Principle
"Online과 대량처리 자원 분리"
        ↓
Decision
"Online AP와 ETL 실행자원 분리"
        ↓
Physical
별도 Node / Host Group
        ↓
Runtime
Thread / CPU / DB 부하 검증
        ↓
Evidence
Load Test / Metric
```

---

# 27. Architecture Closed Loop

## FIG-VSN-27. Document → Runtime → New Baseline

```text
Document
   ↓
Model
   ↓
Code / Config
   ↓
Test
   ↓
Deployment
   ↓
Runtime
   ↓
Evidence
   ↓
Drift
   ↓
GAP / ADR
   ↓
Baseline Update
   └──────────────────────────↺
```

### 완료의 정의

```text
문서 존재
≠ Architecture 완료

문서 → 모델 → 코드 → 테스트 → Runtime → Evidence → Drift → ADR
가 반복 가능
= Architecture Operationalized
```

---

# 28. Architecture Gate

## FIG-VSN-28. Governance Gate Overview

```text
G00 Source / PPT Baseline
  ↓
G10 Document
  ↓
G20 Model / Inventory
  ↓
G30 Code / Config
  ↓
G40 Test
  ↓
G50 Runtime Evidence
  ↓
G60 Drift
  ↓
G70 GAP / ADR
  ↓
HG90 Baseline Release
```

VISION 단계에서는 Gate의 존재와 방향만 정의한다.
상세 Rule/Evaluator는 후속 Traceability/Closed Loop 장에서 확정한다.

---

# 29. 상위 Anti-Pattern Map

## FIG-VSN-29. VISION 단계 금지 패턴

```text
┌────────────────────────────────────────────────────────────────────┐
│ [X] 제품 / Framework부터 설명 시작                                │
│ [X] PDMG 구현을 NSIGHT 전체 표준으로 자동 승격                    │
│ [X] AS-IS 수치를 TO-BE 운영 기준으로 자동 승격                    │
│ [X] 도메인 책임 없이 서버/시스템 목록부터 나열                    │
│ [X] NFR을 선언만 하고 Logical/Physical/Runtime으로 내리지 않음    │
│ [X] 모든 Integration을 하나의 기술로 통일                         │
│ [X] RDW와 ADW를 동일 역할의 DW로 정의                             │
│ [X] Runtime Evidence 없이 Architecture 완료 선언                  │
└────────────────────────────────────────────────────────────────────┘
```

---

# 30. CONFIRMED / CONFLICT / GAP / OPEN

## 30.1 CONFIRMED

```text
[CONFIRMED]
- NSIGHT의 방향은 Data-Centric이다.
- FAST와 DEEP를 분리해 보는 전략이 필요하다.
- 5대 서비스 도메인이 존재한다.
- 규격 표준 / 인터페이스 통제 / 자원 분리가 핵심 통제축이다.
- VISION→BIG PICTURE→LOGICAL→PHYSICAL→MECHANISM→RUNTIME 순서가 기본 Route다.
- PDMG는 NSIGHT 전체가 아니라 하위 Reference다.
```

## 30.2 CONFLICT

```text
[CONFLICT]
CDC SLA 30초 vs 3초
→ 최신 승인값 / 측정범위 / 측정방법 재확정 필요
```

## 30.3 GAP

```text
[GAP]
- Strategy Principle과 실제 Rule/Test 연결 미완료 영역 존재
- PDMG AS-IS와 NSIGHT TO-BE Alignment 전수검증 필요
- Capacity Variant 중 최종 Baseline 미확정 항목 존재
- 일부 HA/DR/NFR 수치는 최신 승인 Baseline 재검증 필요
```

## 30.4 OPEN

```text
[OPEN]
- CDC 최신 SLA
- 최종 Session / Capacity Baseline
- HA/DR 최종 승인 Matrix
- PDMG Reference 중 NSIGHT 표준으로 승격할 범위
```

---

# 31. BIG PICTURE Handoff

## FIG-VSN-30. VISION → BIG PICTURE

```text
VISION OUTPUT
   │
   ├─ Data-Centric
   ├─ 5대 Service Domain
   ├─ 3대 통제
   ├─ FAST / DEEP
   ├─ 5대 NFR
   ├─ Architecture Principle
   ├─ PDMG Reference Position
   └─ Anti-Pattern
   │
   ▼
BIG PICTURE INPUT
   │
   ├─ 어떤 시스템이 어느 도메인의 책임인가?
   ├─ 사용자/채널/외부는 어디서 진입하는가?
   ├─ Application과 Data 책임은 어디서 갈리는가?
   ├─ Online/Event/CDC/ETL/File 경계는 어디인가?
   └─ PDMG는 전체 구조의 어디에 위치하는가?
```

---

# 32. VISION 최종 요약 지도

```text
WHY
Batch-Centric 문제
      ↓
WHAT
Data-Centric 전환
      ↓
WHO
5대 Service Domain
      ↓
CONTROL
규격 표준 / 인터페이스 통제 / 자원 분리
      ↓
QUALITY
Performance / Availability / Scalability / Security / Observability
      ↓
METHOD
Vision → Big Picture → Logical → Physical → Mechanism → Runtime
      ↓
REFERENCE
PDMG AS-IS / Source / Runtime
      ↓
GOVERNANCE
Evidence → Drift → GAP → ADR → New Baseline
```

---

# 33. 이 장의 Definition of Done

## TEXT ARCHITECTURE 보완 — 이 장의 Definition of Done

```text
Architecture Inputs
      ↓
Structure / Contract / Runtime Check
      ↓
Evidence Check
      ↓
GAP / CONFLICT / UNKNOWN Review
      ↓
PASS Condition
      │
      ├─ satisfied → PASS
      └─ pending   → CONDITIONAL PASS
```


- [x] AS-IS 문제를 기술 목록이 아니라 구조적 Root Cause로 표현
- [x] TO-BE Vision을 Data-Centric 중심으로 표현
- [x] FAST / DEEP 전략을 별도 Route로 시각화
- [x] 5대 서비스 도메인 책임을 시각화
- [x] 3대 통제 원칙을 시각화
- [x] 6단계 Architecture Route를 Top-down으로 표현
- [x] 5대 NFR을 후속 Architecture와 연결
- [x] PDMG를 하위 Reference로 분리
- [x] 수치 Conflict를 임의해결하지 않음
- [x] Closed Loop 및 Runtime Evidence 방향 제시
- [x] BIG PICTURE Handoff 정의

**VISION 장 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. CDC 최종 SLA 승인값 재확정
2. NFR별 최신 Owner / 측정방법 확정
3. PDMG Reference → NSIGHT Target 승격 범위 ADR화
4. Capacity/HA/DR 최신 승인 Baseline 연결

---

# 34. 다음 장

다음은 **02. BIG PICTURE — Application / Data / System Boundary**다.

다음 장에서는 VISION에서 정의한:

```text
5대 도메인
3대 통제
FAST / DEEP
NFR
PDMG Reference Position
```

을 실제 **Application Group → System Group → Data Subject → Enterprise Context → Integration Boundary**로 Drill-down한다.

---

# 보완검토 A. VISION Reference Coverage 보강

## FIG-VSN-SUP-01. 데이터 기반 의사결정

```text
Source Data
   ↓
Integrated / Trusted Data
   ↓
RDW / ADW
   ↓
Analysis / BI
   ↓
Decision
   ↓
Action
```

### 보완 포인트

```text
RDW
= 운영 / 준실시간 정보 기반

ADW
= 분석 / 집계 / 마트 기반
```

두 책임을 하나의 “DW”로 뭉개지 않는다.

---

## FIG-VSN-SUP-02. 실시간 정보 활용

```text
즉시 반응이 필요한 업무
      ↓
FAST Runtime
Event / Near Real-time / Decision

정확성·대량분석이 필요한 업무
      ↓
DEEP Runtime
ETL / ADW / BI / Analytical Query
```

`실시간`은 모든 처리를 빠른 경로에 올리는 것이 아니라 **업무목적에 맞는 Runtime 분리**다.

---

## FIG-VSN-SUP-03. 인터페이스 통제 Vision

```text
Transaction ──► API / MCA
Event       ──► Kafka / Event
DB Change   ──► CDC
Bulk Data   ──► ETL
File        ──► FOS / MFT
```

금지:

```text
모든 연계 REST 일원화
Application 간 DB 직접 DML
외부 시스템의 내부 DB 직접 접근
근거 없는 P2P 예외
```

---

## FIG-VSN-SUP-04. 이해관계자와 VISION 책임

```text
Enterprise Architecture
        │
        ├─ Vision / Principle / NFR
        ├─ Application Architecture
        ├─ Data Architecture
        ├─ Technical Architecture
        ├─ Interface Architecture
        ├─ Security Architecture
        ├─ Operations Architecture
        └─ DevOps / PMO / Development
                │
                ▼
        Decision / Evidence / Approval
```

---

## FIG-VSN-SUP-05. DevOps / Operations Vision

```text
Architecture Standard
       ↓
Source
       ↓
Build
       ↓
Test
       ↓
Artifact
       ↓
Deploy
       ↓
Runtime Evidence
       ↓
Drift
       ↓
Architecture Update
```

```text
Deployment 완료
≠
Architecture 검증 완료
```

---

## FIG-VSN-SUP-06. Traceability Vision

```text
Requirement
→ Architecture
→ Application
→ ServiceId
→ Source
→ SQL / Table
→ Host / JVM
→ GUID / Metric / Error
→ Runtime Evidence
```

역방향도 가능해야 한다.

```text
Table / SQL / Error
→ ServiceId
→ Application
→ Requirement
```

---

## FIG-VSN-SUP-07. Timeout Conflict를 Vision 수준에서 분리

```text
PDMG AS-IS
OnlineTimeoutExecutor = 5000ms
        │
        │ ≠
        ▼
NSIGHT Capacity / SLA / Timeout Budget
        │
        ▼
DB Query < Worker/TX < Server < Client
```

`5000ms`는 Source Snapshot이며 Enterprise SLA로 자동 승격하지 않는다.

---


====================================================================================================

# CHAPTER 02

====================================================================================================

# NSIGHT / PDMG 아키텍처 정의서
# 02. BIG PICTURE — Application / Data / System Boundary

> 보완개정: **v2 — TEXT Architecture Figure Coverage / Reference Coverage / Evidence Consistency 재점검**
## TEXT ARCHITECTURE 보완 — 장 전체 위치

```text
VISION
   ↓
Service Domain
   ↓
Application/Data Classification
   ↓
Ownership
   ↓
Boundary
   ↓
LOGICAL
```

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

## TEXT ARCHITECTURE 보완 — Data Subject Inventory

```text
Service Domain
   ↓
Data Subject
   ↓
Data Owner
   ↓
Logical / Physical Data Platform
   ↓
Approved Access Contract
   ↓
Runtime / Lineage Evidence
```


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

## TEXT ARCHITECTURE 보완 — Cross-Cutting Responsibility Matrix

```text
Business / Data / Application
        │
        ├──────── Security
        ├──────── Observability
        ├──────── Deployment
        ├──────── Backup / DR
        └──────── Governance
                 │
                 ▼
          Cross-Cutting Owner
```


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

## TEXT ARCHITECTURE 보완 — GAP Register

```text
Expected / Required
      │
      ▼
Actual / Evidence
      │
      ▼
Difference
      │
      ▼
[GAP]
      │
      ├─ Owner
      ├─ Action
      ├─ ADR
      └─ Evidence
```


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

## TEXT ARCHITECTURE 보완 — RISK Register

```text
Cause
  ↓
Risk Event
  ↓
Business / Data / Security / Operation Impact
  ↓
Severity
  ↓
Mitigation / Control
  ↓
Owner
  ↓
Evidence / Closure
```


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

# 보완검토 A. BIG PICTURE Reference Coverage 보강

## FIG-BP-SUP-01. Marketing Platform 상세 업무분류

```text
MP / Marketing Platform
│
├─ CO  공통
├─ IC  통합고객
├─ PC  개인고객
├─ BC  기업고객
├─ MS  미니 싱글뷰
├─ SA  상담판매
├─ PD  통합상품
├─ CM  캠페인
├─ EB  EBM
├─ EP  실시간 처리
├─ BP  행동정보 처리
├─ BD  고객 행동 데이터
├─ SS  영업지원
├─ CS  CS
├─ CT  컨텐츠
└─ MG  메시지
```

---

## FIG-BP-SUP-02. 데이터 주제영역 6개 트리

```text
차세대 정보계 데이터 주제영역
├─ 데이터플랫폼 RDW
│   └─ CO · SR · ZD · RM · FA
├─ 데이터플랫폼 ADW
│   └─ CO · SR · ZD · UM · RM · FA · DA
├─ BI포탈
│   └─ PT · CR · OA · SB · UI
├─ 마케팅플랫폼
│   └─ MP 업무구분 집합
├─ 데이터거버넌스
│   └─ CO · BM · DQ · DL
└─ IT서비스 및 업무지원
    └─ IM 업무구분 집합
```

---

## FIG-BP-SUP-03. 전체 시스템 Architecture

```text
Channel
Account Terminal / Information Terminal / Web / Mobile
        ↓
Access / Integration
MCA / Web / API / Collector / File
        ↓
Information Application
MP / BI / DG / IM
        ↓
Data Platform
RDW / ADW
        ↓
Core / Related / External
```

---

## FIG-BP-SUP-04. 채널과 계정성 거래의 경계

```text
Account / Channel Transaction
      ↓
Enterprise Channel / MCA-MCI
      ↓
Information Application
      ↓
Approved Core / Data Interface
```

BIG PICTURE에서는 경계와 책임을 정의하며 상세 Protocol/Port는 하위 장으로 넘긴다.

---

## FIG-BP-SUP-05. System Group → Logical Server Candidate

```text
System Group
   ↓
Responsibility
   ↓
Runtime Characteristic
   ↓
Logical Server Candidate
```

BIG PICTURE에서 확정하지 않는 것:

```text
Final Hostname
Instance Number
CPU / Memory
Port
OS / SW Version
```

---

## FIG-BP-SUP-06. BIG PICTURE 책임 체인

```text
Responsibility
   ↓
Application Classification
   ↓
Ownership
   ↓
System Boundary
   ↓
Data Subject
   ↓
Interface Boundary
   ↓
LOGICAL Mapping
```

---

# 83. Definition of Done

## TEXT ARCHITECTURE 보완 — Definition of Done

```text
Architecture Inputs
      ↓
Structure / Contract / Runtime Check
      ↓
Evidence Check
      ↓
GAP / CONFLICT / UNKNOWN Review
      ↓
PASS Condition
      │
      ├─ satisfied → PASS
      └─ pending   → CONDITIONAL PASS
```


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

---


====================================================================================================

# CHAPTER 03

====================================================================================================

# NSIGHT / PDMG 아키텍처 정의서
# 03. LOGICAL — Zone / Logical System / Node / Layer / Connection

> 보완개정: **v2 — TEXT Architecture Figure Coverage / Reference Coverage / Evidence Consistency 재점검**
## TEXT ARCHITECTURE 보완 — 장 전체 위치

```text
BIG PICTURE
   ↓
Zone
   ↓
Logical System
   ↓
Logical Node
   ↓
Component
   ↓
Allowed Path
   ↓
PHYSICAL
```

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

## TEXT ARCHITECTURE 보완 — Logical Node 필수 속성

```text
Logical Node
│
├─ Responsibility
├─ Runtime Type
├─ Owned Data
├─ Allowed Connection
├─ Failure Domain
├─ Scale Characteristic
├─ Security Boundary
└─ Environment Scope
```


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

## TEXT ARCHITECTURE 보완 — Logical Responsibility Matrix

```text
Service Domain
   ↓
Logical System
   ↓
Logical Node
   ↓
Responsibility
   ↓
Owned Data / Interface
   ↓
Failure / Scale Boundary
```


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

## TEXT ARCHITECTURE 보완 — GAP Register

```text
Expected / Required
      │
      ▼
Actual / Evidence
      │
      ▼
Difference
      │
      ▼
[GAP]
      │
      ├─ Owner
      ├─ Action
      ├─ ADR
      └─ Evidence
```


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

## TEXT ARCHITECTURE 보완 — RISK Register

```text
Cause
  ↓
Risk Event
  ↓
Business / Data / Security / Operation Impact
  ↓
Severity
  ↓
Mitigation / Control
  ↓
Owner
  ↓
Evidence / Closure
```


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

# 보완검토 A. LOGICAL Reference Coverage 보강

## FIG-LOG-SUP-01. 환경별 구축범위

```text
Logical Architecture
│
├─ 운영환경 : 5체계 Baseline
├─ 개발환경 : 5체계 Baseline
├─ DR환경   : 즉시기동 3체계 Baseline
└─ 선도환경 : 3체계 Baseline
```

수량은 기존 Logical Baseline이며 최신 물리 Inventory와 혼동하지 않는다.

---

## FIG-LOG-SUP-02. Center와 Environment를 Logical에서도 분리

```text
Environment
운영 / 개발 / DR / 선도
       │
       │ independent axis
       ▼
Logical System / Node
```

센터 위치는 PHYSICAL에서 매핑한다.

---

## FIG-LOG-SUP-03. 공통 기술 컴포넌트

```text
Logical Systems
      │
      ├──────── Security
      ├──────── Monitoring
      ├──────── Backup
      ├──────── Account
      ├──────── Configuration
      ├──────── Deployment
      ├──────── Logging
      └──────── Network Control
```

이들은 특정 업무 한 곳이 아니라 Cross-Cutting Capability다.

---

## FIG-LOG-SUP-04. 데이터 소유 원칙

```text
Logical System
   │
   └─ Own Data
        │
        └─ Direct Access O

Other System Data
   │
   └─ Approved Interface
      or Approved Data Access Contract
```

---

## FIG-LOG-SUP-05. Marketing Logical Architecture

```text
Channel
  ↓
Channel Integration
  ↓
MP-WEB
  ↓
MP-WAS
  ├─ Customer
  ├─ Sales / Product
  ├─ Campaign
  └─ Common
  │
  ├────────► RDW
  ├────────► Internal Integration
  └────────► Event AP
                 ├─ EP
                 ├─ BP
                 └─ BD
```

---

## FIG-LOG-SUP-06. Data / BI / Governance Logical Separation

```text
Data Platform
RDW ──► Operational / Near Real-time
ADW ──► Analytical / Mart
          │
          ├────────► BI Portal
          └────────► Analytical Service

Data Governance
Metadata / Quality / Lineage
          │
          └────────► Cross-cutting Data Control
```

---

## FIG-LOG-SUP-07. LOGICAL → PHYSICAL Handoff 강화

```text
Logical Node
│
├─ Responsibility
├─ Runtime Type
├─ Failure Domain
├─ Scale Characteristic
├─ Owned Data
├─ Allowed Connection
└─ Environment
      │
      ▼
Physical Mapping
Center / Host / JVM / DB / HA / DR
```

---

# 106. Definition of Done

## TEXT ARCHITECTURE 보완 — Definition of Done

```text
Architecture Inputs
      ↓
Structure / Contract / Runtime Check
      ↓
Evidence Check
      ↓
GAP / CONFLICT / UNKNOWN Review
      ↓
PASS Condition
      │
      ├─ satisfied → PASS
      └─ pending   → CONDITIONAL PASS
```


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

---


====================================================================================================

# CHAPTER 04

====================================================================================================

# NSIGHT / PDMG 아키텍처 정의서
# 04. PHYSICAL — Center / Network / WEB / WAS / JVM / DB / HA / DR / Capacity

> 보완개정: **v2 — TEXT Architecture Figure Coverage / Reference Coverage / Evidence Consistency 재점검**
## TEXT ARCHITECTURE 보완 — 장 전체 위치

```text
LOGICAL NODE
   ↓
Center
   ↓
Host/VM
   ↓
WEB/WAS/JVM/WAR
   ↓
DB
   ↓
HA/DR
   ↓
MECHANISM
```

## Visual-First / Top-down / Drill-down 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-VISUAL-PHYSICAL-04`  
> Architecture Level: **PHYSICAL / L3**  
> 문서 상태: **Draft / Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 작성 원칙: **Logical Node → Physical Resource → Capacity → HA/DR → Evidence**  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_03_LOGICAL_VISUAL_FIRST_TOPDOWN_상세본.md`  
> 후속 장: **05. MECHANISM**

---

# 0. 이 문서를 읽는 방법

PHYSICAL은 LOGICAL에서 정한 책임과 분리 원칙을
실제 실행 자원으로 Mapping하는 장이다.

```text
LOGICAL
Zone
  ↓
Logical System
  ↓
Logical Node
  ↓
Failure Domain
  ↓
Scale Characteristic
       │
       ▼
PHYSICAL
Center
  ↓
Network
  ↓
GSLB / L4
  ↓
WEB
  ↓
WAS
  ↓
JVM
  ↓
WAR
  ↓
Thread / Pool
  ↓
DB / Storage
  ↓
HA / DR
```

이 장에서 반드시 구분해야 한다.

```text
Physical Server / VM
≠
Tomcat JVM

Tomcat JVM
≠
WAR

WAR
≠
Module

WEB Node
≠
Apache Instance

WAS Node
≠
Tomcat Process 수

Capacity Candidate
≠
Actual Runtime Config

HA
≠
DR
≠
Backup
```

---

# 1. VISUAL ROUTE — PHYSICAL 전체를 한 장으로 보기

## FIG-PH-01. Physical Architecture Journey

```text
╔══════════════════════════════════════════════════════════════════════════════╗
║                           PHYSICAL ARCHITECTURE                              ║
╚══════════════════════════════════════════════════════════════════════════════╝

 LOGICAL NODE
    │
    ▼
 ① CENTER
    │
    ├─ Main
    └─ DR
    │
    ▼
 ② NETWORK / TRAFFIC
    │
    ├─ GSLB
    ├─ L4
    └─ WEB Entry
    │
    ▼
 ③ WEB / WAS
    │
    ├─ Apache [Working Baseline]
    ├─ Tomcat JVM
    └─ WAR
    │
    ▼
 ④ EXECUTION RESOURCE
    │
    ├─ Request Thread
    ├─ Worker Thread
    └─ Hikari Pool
    │
    ▼
 ⑤ DATA PLATFORM
    │
    ├─ RDW
    ├─ ADW
    ├─ CDC
    └─ ETL
    │
    ▼
 ⑥ CAPACITY / HA / DR
    │
    ├─ Scale-up / Scale-out
    ├─ Session
    ├─ Node HA
    └─ Center DR
    │
    ▼
 ⑦ PHYSICAL EVIDENCE
      Inventory / Config / Runtime Metric / DR Test
```

### 이 그림에서 봐야 할 것

- PHYSICAL은 “서버를 나열하는 장”이 아니다.
- LOGICAL에서 정의한 **분리·확장·장애경계**가 실제 배치에서도 유지되는지 검증하는 장이다.
- 수치와 제품명은 **근거 시점과 상태를 함께 표시**해야 한다.

---

# 2. LOGICAL에서 전달받은 계약

## FIG-PH-02. LOGICAL → PHYSICAL Input

```text
LOGICAL
│
├─ Zone
├─ Logical System
├─ Logical Node
├─ Runtime Type
├─ Layer
├─ Allowed / Forbidden Connection
├─ Failure Domain
├─ Scale Characteristic
├─ Environment Scope
└─ PDMG Reference Position
      │
      ▼
PHYSICAL
│
├─ Center
├─ Host / VM
├─ Network Path
├─ Middleware
├─ JVM
├─ WAR
├─ CPU / Memory
├─ Thread / Pool
├─ DB / Storage
├─ HA Pair
└─ DR Pair
```

---

# 3. PHYSICAL 핵심 결론

## FIG-PH-03. Physical Decision Principle

```text
Logical Responsibility
       │
       ▼
Logical Node
       │
       ▼
Physical Group
       │
       ├─ Host / VM
       ├─ Middleware
       ├─ JVM
       ├─ WAR
       ├─ DB
       ├─ Network
       └─ HA/DR
```

### 한 줄 결론

> **PHYSICAL의 핵심은 “어떤 책임을 어떤 물리 자원에 배치하고, 장애가 나도 그 책임이 유지되게 하는가”다.**

---

# 4. Center Architecture

## FIG-PH-04. Main / DR Center Topology

```text
┌──────────────────────────── 의왕 센터 ────────────────────────────┐
│                            [MAIN]                                 │
│                                                                   │
│ WEB / WAS / AP / DB / ETL / CDC / BI / Governance / Operations  │
│                                                                   │
└──────────────────────────────┬────────────────────────────────────┘
                               │
                         Internal GSLB
                               │
┌──────────────────────────────▼────────────────────────────────────┐
│                            안성 센터                              │
│                            [DR]                                  │
│                                                                   │
│ DR WEB / WAS / AP / DB / Supporting Roles                       │
│                                                                   │
└───────────────────────────────────────────────────────────────────┘
```

### 해설

- 현재 물리 자료의 기본 방향은 **의왕 주센터 / 안성 DR센터**다.
- GSLB를 통한 센터 전환 구조가 상위 Physical Baseline으로 사용된다.
- 실제 RTO/RPO/자동전환 상세는 별도 승인 Evidence로 닫아야 한다.

---

# 5. Center Role

## FIG-PH-05. Center Responsibility

```text
MAIN
│
├─ Normal Production Traffic
├─ Primary Application Runtime
├─ Primary Data Runtime
├─ Main Operations
└─ Primary Integration

DR
│
├─ Disaster Recovery
├─ Standby / Alternate Runtime
├─ Alternate Data Role
├─ DR Integration
└─ Recovery Operations
```

### 주의

```text
DR Server 존재
=
DR 준비 완료

X
```

필요:

```text
Artifact Sync
Config Sync
Key Sync
DB Replication
Route
Runbook
Test Evidence
```

---

# 6. Enterprise Traffic Path

## FIG-PH-06. GSLB → L4 → WEB → WAS

```text
User / Client
     │
     ▼
    GSLB
     │
     ▼
     L4
     │
     ▼
┌────────────── WEB Layer ──────────────┐
│ WEB Node #1                           │
│ WEB Node #2                           │
│                                      │
│ Apache [Working Baseline]             │
└────────────────┬──────────────────────┘
                 │ Reverse Proxy
                 ▼
┌────────────── WAS Layer ──────────────┐
│ WAS Node #1                           │
│ WAS Node #2                           │
│                                      │
│ Tomcat JVM                            │
└────────────────┬──────────────────────┘
                 ▼
              Business WAR
                 │
                 ▼
              HikariCP
                 │
                 ▼
             RDW / DB
```

### 상태

```text
GSLB → L4 → Apache → Tomcat → WAR → Hikari/MyBatis → DB
= NSIGHT Working Baseline
```

노드별 실제 제품/Version/Port는 Inventory/Config로 재확정한다.

---

# 7. Traffic Layer Responsibility

## FIG-PH-07. Traffic Responsibility

```text
GSLB
= Center / Site Routing

L4
= Service VIP / Member Distribution

WEB
= HTTP Entry / Reverse Proxy / Static / Routing

WAS
= Application Runtime

DB
= Data Runtime
```

### 금지 해석

```text
GSLB = Application Router     X
L4   = Business Dispatcher    X
WEB  = Business Service       X
```

---

# 8. WEB Layer

## FIG-PH-08. WEB Physical Role

```text
┌──────────────────────── WEB NODE ────────────────────────┐
│                                                         │
│ OS                                                      │
│  │                                                      │
│  └─ WEB Server Instance                                 │
│      ├─ Listen                                          │
│      ├─ VirtualHost                                     │
│      ├─ Proxy                                           │
│      ├─ Health                                          │
│      └─ Access Log                                      │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

### Working Standard

```text
Apache
```

### 하지만

```text
WEB Node 존재
=
해당 노드 Apache 설치 확정

이라고 자동 판단하지 않는다.
```

실제 설치 Software는 Server Inventory로 확인한다.

---

# 9. Apache Instance vs WEB VM

## FIG-PH-09. WEB Instance Boundary

```text
WEB VM
┌──────────────────────────────────────┐
│ OS                                   │
│                                      │
│ Apache Instance #1                   │
│   ├─ Listen 1                        │
│   ├─ Listen 2                        │
│   └─ VirtualHost                     │
│                                      │
│ Apache Instance #2 [가능 구조]       │
│   └─ 별도 Config / Process           │
│                                      │
└──────────────────────────────────────┘
```

### 핵심

```text
WEB VM 수
≠
Apache Instance 수
≠
Listen Port 수
```

---

# 10. WAS Layer

## FIG-PH-10. WAS Physical Role

```text
┌──────────────────────── WAS NODE ──────────────────────────┐
│                                                          │
│ OS                                                       │
│  │                                                       │
│  └─ Tomcat JVM                                          │
│      │                                                   │
│      ├─ Spring ApplicationContext                        │
│      ├─ Business WAR                                     │
│      ├─ Framework Library                                │
│      ├─ Thread Pool                                      │
│      ├─ DataSource / HikariCP                            │
│      └─ Logging / Monitoring                             │
│                                                          │
└──────────────────────────────────────────────────────────┘
```

---

# 11. Server / JVM / WAR Boundary

## FIG-PH-11. Three Physical Execution Boundaries

```text
Physical Server / VM
┌───────────────────────────────────────────────┐
│                                               │
│ Tomcat JVM #1                                 │
│ ┌───────────────────────────────────────────┐ │
│ │ Heap / Metaspace / Thread                │ │
│ │                                           │ │
│ │ WAR A                                     │ │
│ │ WAR B [가능 구조]                         │ │
│ └───────────────────────────────────────────┘ │
│                                               │
│ Tomcat JVM #2 [가능 구조]                     │
│ ┌───────────────────────────────────────────┐ │
│ │ 별도 Heap / Port / CATALINA_BASE          │ │
│ └───────────────────────────────────────────┘ │
│                                               │
└───────────────────────────────────────────────┘
```

### 반드시 분리할 개념

```text
Server / VM
Tomcat JVM
WAR
Port
PID
CATALINA_BASE
Heap
Datasource
Log
HA Group
```

---

# 12. WAR / Module Boundary

## FIG-PH-12. WAR vs Module

```text
Business WAR
┌──────────────────────────────────────┐
│                                      │
│ pdmg-service Module                  │
│          +                           │
│ pdmg-fw Library / Bean               │
│                                      │
└──────────────────────────────────────┘
```

### 핵심

```text
pdmg-fw Module
≠
독립 WAR
≠
독립 JVM
≠
독립 Server
```

현재 Reference 기준으로는 `pdmg-service` Runtime 내부 Framework 계층으로 본다.

---

# 13. PDMG Physical Mapping — 상위

## FIG-PH-13. PDMG Deployment Candidate Map

```text
PDMG Reference
│
├─ pdmg-ui
│    └─ UI Process / WEB-like Deployment [VERIFY]
│
├─ pdmg-jwt
│    └─ Auth/Token Application [VERIFY PHYSICAL]
│
├─ pdmg-service
│    └─ Business WAR / Service Runtime
│
├─ pdmg-fw
│    └─ In-process Framework Library
│
└─ pdmg-om
     └─ [UNKNOWN CURRENT PHYSICAL]
```

### GAP

다음 Mapping은 최신 Deployment Manifest가 필요하다.

```text
Module
→ Artifact
→ Host
→ JVM
→ Context
→ Port
→ L4 Pool
→ WEB VHost
```

---

# 14. Marketing Physical Role Evidence

## FIG-PH-14. Marketing WEB/WAS Redundancy

```text
의왕 MAIN

Marketing Platform
│
├─ WEB #1
├─ WEB #2
│
├─ WAS #1
└─ WAS #2
```

### 해설

- 물리 구성도에는 Marketing WEB/WAS 이중화 Role이 표현된다.
- 그러나 `pdmg-service`가 해당 Role에 공식 배포된다는 Mapping은 별도 Deployment Evidence로 닫아야 한다.

---

# 15. Marketing Additional AP Roles

## FIG-PH-15. Marketing AP Roles

```text
Marketing Physical Roles
│
├─ Marketing WEB #1/#2
├─ Marketing WAS #1/#2
├─ Mini Single View WEB #1/#2
├─ Mini Single View WAS #1/#2
├─ Real-time Processing AP #1/#2
├─ Behavior Processing AP #1/#2
└─ Behavior Data AP #1/#2/#3
```

### 의미

BIG PICTURE/LOGICAL에서 분리한:

```text
Online
Single View
Real-time
Behavior
```

책임이 Physical Role로 내려오는 구조다.

---

# 16. BI / Governance Physical Roles

## FIG-PH-16. Supporting Physical Roles

```text
BI / Analysis
│
├─ BI Portal WEB/WAS
├─ Self-BI WEB/WAS
├─ Self-BI AP
└─ Credit Performance WEB/WAS

Governance / Operations
│
├─ Metadata / Quality WAS
├─ Data Flow WAS
├─ Report/Output WAS
├─ Terminal Management
├─ Distribution
└─ Dashboard / Operations
```

실제 Hostname / 수량 / Version은 최신 Inventory로 검증한다.

---

# 17. Data Platform Physical Strategy

## FIG-PH-17. Hybrid Physical Strategy

```text
┌───────────────────────────────────────────────────────────┐
│ Service Resource                                          │
│ Marketing / BI / Governance                              │
│ → NH Private Cloud VM / Scale-out                         │
├───────────────────────────────────────────────────────────┤
│ Data Resource                                             │
│ RDW / ADW                                                 │
│ → Exadata / Dedicated Parallel Data Resource              │
├───────────────────────────────────────────────────────────┤
│ Performance-sensitive Resource                            │
│ ETL / IMDG                                                │
│ → Dedicated / Bare-metal Candidate                        │
├───────────────────────────────────────────────────────────┤
│ CDC                                                       │
│ → Unix / Dedicated Change Data Role                       │
└───────────────────────────────────────────────────────────┘
```

### 상태

이 구성은 특정 시점 Physical Strategy Baseline이다.
최신 실제 Inventory로 재검증해야 한다.

---

# 18. RDW / ADW Physical Separation

## FIG-PH-18. Data Platform Physical Boundary

```text
                    Data Platform
                        │
          ┌─────────────┴─────────────┐
          ▼                           ▼
     ┌───────────┐               ┌───────────┐
     │    RDW    │               │    ADW    │
     │ Exadata   │               │ Exadata   │
     └─────┬─────┘               └─────┬─────┘
           │                           │
 Near Real-time                 Analytical / Mart
           │                           │
           └────────────┬──────────────┘
                        ▼
                  Data Integration
```

### 핵심

- LOGICAL에서 분리한 RD/AD 책임을 Physical에서도 가능한 한 보존한다.
- 동일/공유 자원 여부는 실제 Baseline에 따라 검증하되,
  **성능과 장애 책임을 혼합하지 않는 것**이 원칙이다.

---

# 19. CDC Physical Role

## FIG-PH-19. CDC Physical Runtime

```text
Core / Source DB
      │
      ▼
CDC Capture / Relay
      │
      ▼
RDW
```

### CDC Resource 관심

```text
CPU
Network
Change Lag
Queue / Buffer
Source DB Impact
Target Apply
```

---

# 20. ETL Physical Role

## FIG-PH-20. ETL Physical Runtime

```text
Source / RDW
      │
      ▼
ETL Engine
      │
      ├─ Extract
      ├─ Transform
      └─ Load
      │
      ▼
ADW
```

### 핵심

- ETL은 대량 I/O와 CPU를 사용하므로 Online WAS와 같은 자원에 혼재시키지 않는 방향이 중요하다.

---

# 21. JVM Memory Boundary

## FIG-PH-21. JVM Memory Anatomy

```text
VM Memory
┌─────────────────────────────────────────┐
│                                         │
│ JVM Process                             │
│ ┌─────────────────────────────────────┐ │
│ │ Java Heap                           │ │
│ │ Metaspace                           │ │
│ │ Thread Stack                        │ │
│ │ Code Cache                          │ │
│ │ Direct Buffer                       │ │
│ │ Native / GC Structure               │ │
│ └─────────────────────────────────────┘ │
│                                         │
│ OS / Page Cache / Agent / Other         │
└─────────────────────────────────────────┘
```

### 핵심

```text
VM Memory 256GB
≠
Heap 256GB
```

---

# 22. JVM Heap Candidate

## FIG-PH-22. Heap Variant

```text
8C / 32G
   └─ Heap 약 12~14G 후보

16C / 64G
   └─ Heap 약 24~28G 후보

16C / 128G
   └─ Heap 약 32~40G 후보

32C / 256G
   └─ Heap 약 32~48G 후보
```

### 상태

```text
[CAPACITY VARIANT]
```

실제 `-Xms/-Xmx`는 JVM Config로 확인한다.

---

# 23. CPU / Memory Variant

## FIG-PH-23. VM Candidate Comparison

```text
8C / 32G
  │
  ├─ 작은 Failure Domain
  └─ 더 많은 Node 필요

16C / 64G
  │
  ├─ 중간 Scale-out
  └─ 일반 균형형

16C / 128G
  │
  ├─ CPU는 동일 16C
  └─ Memory 증가

32C / 256G
  │
  ├─ 큰 Capacity
  └─ Failure Blast Radius 증가
```

### 핵심

```text
Memory 증가
≠
CPU 처리능력 증가
≠
TPS 자동 증가
```

---

# 24. Scale-Up vs Scale-Out

## FIG-PH-24. Physical Scaling Strategy

```text
[Scale-Up]

작은 Node
   ↓
큰 VM
   ↓
고성능 단일 Node

장점
- Node 수 감소
- 관리 단순

위험
- 장애 Blast Radius 증가
- GC / Thread 복잡도
- 장애 후 잔여용량 급감


[Scale-Out]

여러 Node
   ↓
L4 분산
   ↓
독립 확장

장점
- 장애격리
- 단계적 확장
- 잔여용량 분산

비용
- 운영 노드 증가
- Session / Deploy / Monitoring 복잡
```

---

# 25. Capacity Assumption

## FIG-PH-25. User → TPS

```text
6,000 지점
    ×
6명
    =
36,000 사용자
       │
       ▼
동시 요청률
5% / 10% / 15%
       │
       ▼
Concurrent Request
1,800 / 3,600 / 5,400
       │
       ▼
응답시간 3초 후보
       │
       ▼
TPS
600 / 1,200 / 1,800
```

### 상태

```text
[CAPACITY BASELINE VARIANT]
```

실제 Production TPS 실측값으로 쓰지 않는다.

---

# 26. Capacity Chain

## FIG-PH-26. End-to-End Capacity Chain

```text
Users
  ↓
Concurrent Request
  ↓
TPS
  ↓
WEB Connections
  ↓
Tomcat Request Threads
  ↓
PDMG Worker Threads
  ↓
Hikari Connections
  ↓
DB Sessions
  ↓
CPU / Memory / I/O
```

### 핵심

이 값들은 **서로 연결되어 있지만 같은 크기로 맞추는 값이 아니다.**

---

# 27. Tomcat Thread

## FIG-PH-27. Tomcat Capacity Candidate

```text
8C
→ maxThreads 400~500 후보

16C
→ maxThreads 800~1000 후보

32C
→ maxThreads 1200~1500 후보
```

### 상태

```text
[CAPACITY VARIANT]
```

실제 `server.xml` 확인 필요.

---

# 28. Request Thread / Worker / Hikari

## FIG-PH-28. Three Pool Architecture

```text
Tomcat Request Thread
        │
        ▼
PDMG Worker Pool
        │
        ▼
Hikari Connection Pool
        │
        ▼
DB Session
```

### 구분

```text
Tomcat maxThreads
= HTTP Request 자원

PDMG Worker
= Business Execution 자원

Hikari maxPoolSize
= DB Connection 자원
```

---

# 29. PDMG Current Worker Snapshot

## FIG-PH-29. PDMG Worker Current Snapshot

```text
[AS-IS SNAPSHOT]

Worker Pool = 20
Queue       = 100
Timeout     = 5000ms
```

### 중요

```text
Worker 20
≠
Tomcat maxThreads 20
```

이 값은 PDMG Source Snapshot이며
NSIGHT 전체 Capacity Target이 아니다.

---

# 30. Pool Relationship

## FIG-PH-30. Pool Bottleneck Propagation

```text
Tomcat Request 증가
      │
      ▼
Worker Busy
      │
      ▼
Queue 증가
      │
      ▼
Hikari Pending
      │
      ▼
DB Session / SQL Wait
      │
      ▼
Timeout / 503 / 504
```

---

# 31. Hikari Candidate

## FIG-PH-31. Hikari Capacity Variant

```text
일반 AP
8C   → 약 50 후보
16C  → 약 80~100 후보

SingleView
60분 Variant → 70~80
90분 Variant → 100~120
```

### 판정

```text
[CONFLICT / VARIANT]
최신 승인 Baseline 필요
```

---

# 32. Hikari Too Small

## FIG-PH-32. Small Pool Failure

```text
Worker
  ↓
Connection Request
  ↓
Pool Full
  ↓
Pending
  ↓
Worker Hold
  ↓
Queue
  ↓
Timeout
```

---

# 33. Hikari Too Large

## FIG-PH-33. Large Pool Failure

```text
Hikari 확대
   ↓
DB Session 증가
   ↓
DB CPU / PGA / Lock / I/O 증가
   ↓
DB 병목
```

### 핵심

> **Pool 확대는 무료 성능향상이 아니다.**

---

# 34. Thread / DB Pool Ratio

## FIG-PH-34. Ratio Candidate

```text
Thread / DB Pool

4:1 ~ 8:1
→ 정상 후보

8:1 ~ 12:1
→ 주의

12:1 초과
→ Pool Wait / SQL 병목 확인
```

이 값은 Capacity Design Candidate다.

---

# 35. Capacity Operational Rule Candidate

## FIG-PH-35. Thread Utilization

```text
산정 Thread
≤ maxThreads 70%
    → 정상 후보

70~85%
    → 주의

85% 초과
    → Scale / DB / External 병목 확인
```

Production Alert Threshold로 자동 승격하지 않는다.

---

# 36. Session Architecture

## FIG-PH-36. Session / Token / State

```text
Browser
│
├─ Access Token
├─ Refresh Token
└─ Client State
   │
   ▼
WAS
│
├─ HttpSession [필요 시]
├─ JWT Verification
└─ Request Context
   │
   ▼
Server State
├─ Refresh Hash
├─ Denylist
├─ User/Auth State
└─ Session State
```

### 핵심

```text
JWT
≠
Server State 0
```

---

# 37. Session Timeout Conflict

## FIG-PH-37. 60min vs 90min

```text
[Variant A]
Session Timeout = 60분
Sticky          = 70~80분

          VS

[Variant B]
Session Timeout = 90분
Sticky          = 100~120분
```

### 판정

```text
[CONFLICT-PH-01]
Final Session Idle Timeout = TBD
Final Sticky Timeout       = TBD
```

---

# 38. Session Size Candidate

## FIG-PH-38. Session Size Guardrail

```text
Target
≤ 2KB 후보

Maximum
≤ 5KB 후보
```

저장 후보:

```text
userId
branchId
role
authLevel
```

금지 후보:

```text
고객조회 전체 결과
Single View 전체 결과
대량 List
```

---

# 39. DeltaManager

## FIG-PH-39. In-Center Session Replication

```text
Center Internal Cluster

Tomcat A
  │
  │ Session Replication
  ▼
Tomcat B
```

### Working Baseline

```text
DeltaManager
센터 내부 복제
```

---

# 40. Cross-Center Session

## FIG-PH-40. Main / DR Session Boundary

```text
의왕
Session Cluster
   │
   │ X Cross-center Replication
   │
안성
DR Cluster
```

### 의미

센터 장애 시:

```text
기존 HttpSession 유지
```

를 기본 보장하지 않는다.

---

# 41. DR Session Scenario

## FIG-PH-41. Center Failure Session Behavior

```text
User
 ↓
의왕 WAS
 ↓
HttpSession
 ↓
[Center Failure]
 ↓
GSLB
 ↓
안성 DR WAS
 ↓
Session 없음
 ↓
Re-login / Re-auth
```

실제 정책은 최신 보안/DR 승인과 연계한다.

---

# 42. Node Failure HA

## FIG-PH-42. WEB Node Failure

```text
Client
 ↓
L4
 ├─ WEB #1  X
 └─ WEB #2  O
      │
      ▼
    WAS Pool
```

---

# 43. WAS Node Failure

## FIG-PH-43. WAS Failover

```text
WEB
 ↓
WAS #1  X
 ↓
Health Check / Member Remove
 ↓
WAS #2
 ↓
Business Continue
```

필요:

```text
Residual Capacity
Session Behavior
In-flight Transaction
Connection Cleanup
```

---

# 44. JVM Failure Domain

## FIG-PH-44. Shared JVM Blast Radius

```text
Tomcat JVM
┌─────────────────────────────┐
│ WAR A                       │
│ WAR B                       │
└─────────────────────────────┘

JVM Crash
  ↓
WAR A Down
WAR B Down
```

### 핵심

```text
WAR가 다름
≠
Failure Domain 완전 분리
```

---

# 45. Separate JVM Isolation

## FIG-PH-45. Stronger Isolation

```text
VM
│
├─ Tomcat JVM A
│    └─ WAR A
│
└─ Tomcat JVM B
     └─ WAR B
```

분리 기준 후보:

```text
업무 중요도
부하 특성
배포주기
장애영향
보안
```

---

# 46. Application Group A/B Isolation

## FIG-PH-46. Group Isolation Candidate

```text
Application Group A
      │
      └─ JVM / Host Group A

Application Group B
      │
      └─ JVM / Host Group B
```

### 현재 상태

실제 PDMG 업무그룹별 Host/JVM 배치 Matrix는 Evidence 부족.

```text
[GAP]
```

---

# 47. HA Capacity

## FIG-PH-47. N+1 Principle

```text
정상
Node A + B + C + D

1 Node Down
A + B + C

      │
      ▼
남은 3개 Node가 Peak SLA 유지?
```

### 핵심

정상 TPS만 계산하면 HA Capacity가 아니다.

---

# 48. Residual Capacity

## FIG-PH-48. Failure Capacity

```text
Normal Load
   ↓
Node Failure
   ↓
Remaining Capacity
   ↓
p95 / CPU / Thread / Pool
   ↓
SLA 유지 여부
```

---

# 49. DR Architecture

## FIG-PH-49. Main → DR Failover

```text
Normal
User
 ↓
GSLB
 ↓
의왕
 ↓
WEB/WAS
 ↓
DB

Disaster
의왕 X
 ↓
GSLB Decision
 ↓
안성
 ↓
DR WEB/WAS
 ↓
DR Data
```

---

# 50. DR Decision Chain

## FIG-PH-50. DR Readiness

```text
Detect
 ↓
Declare
 ↓
Route
 ↓
Application Ready
 ↓
Data Ready
 ↓
Security Ready
 ↓
External Ready
 ↓
Validate
 ↓
Failback
```

---

# 51. HA vs DR vs Backup

## FIG-PH-51. Three Different Recovery Mechanisms

```text
HA
= Node / Process Failure Continuity

DR
= Center Disaster Recovery

Backup
= Data / System Restore Asset
```

### 금지

```text
Backup 있으니 DR 완료        X
서버 2대 있으니 HA 완료      X
DR센터 있으니 RTO 충족       X
```

---

# 52. DR RPO / RTO

## FIG-PH-52. RPO / RTO Decision

```text
Business Criticality
      │
      ▼
RTO / RPO
      │
      ▼
Replication / Standby
      │
      ▼
DR Runbook
      │
      ▼
DR Test
```

### 현재 상태

최종 NSIGHT/PDMG 전체 RPO/RTO는 현재 확보 Evidence로 확정하지 않는다.

```text
[GAP-PH-02]
```

---

# 53. DB HA / DR

## FIG-PH-53. Data HA Responsibility

```text
Application
   │
   ▼
DB Service
   │
   ├─ Local HA
   └─ DR Replication
```

### PHYSICAL에서 확인할 것

```text
DB Role
Primary / Standby
Connection Endpoint
Failover
Replication
Storage
Backup
```

정확한 RAC/Data Guard 등은 DB Architecture Evidence로 확정해야 한다.

---

# 54. JWT Physical HA

## FIG-PH-54. JWT Multi-instance Risk

```text
L4
├─ JWT #1
│    └─ Key A / kid=K
│
└─ JWT #2
     └─ Key B / kid=K
```

### 위험

```text
same kid
different key
```

는 Critical.

---

# 55. JWT TO-BE Physical

## FIG-PH-55. Central Key Store

```text
                Central Key Store
                       │
           ┌───────────┴───────────┐
           ▼                       ▼
       JWT #1                   JWT #2
       key=K2                   key=K2
       kid=K2                   kid=K2
           │                       │
           └───────────┬───────────┘
                       ▼
                      L4
                       │
                       ▼
                     JWKS
```

제품은 `[TBD]`.

---

# 56. JWKS HA

## FIG-PH-56. JWKS Availability

```text
Business Verifier
      │
      ▼
JWKS VIP
      │
      ├─ JWT #1
      └─ JWT #2
```

필요:

```text
Consistent JWK Set
Cache
Unknown kid refresh
Rotation
DR
```

---

# 57. JWT DR

## FIG-PH-57. Token Compatibility at DR

```text
Main Token
   │
   ▼
Center Failure
   │
   ▼
DR
   │
   ├─ Same Logical Issuer?
   ├─ Same Public Key?
   ├─ Denylist replicated?
   ├─ Refresh state replicated?
   └─ Clock synchronized?
```

---

# 58. Refresh / Denylist State

## FIG-PH-58. Security State HA

```text
JWT Process
   │
   ├─ Access Token
   └─ Refresh / Revoke
         │
         ▼
Security State DB
   ├─ Refresh Hash
   ├─ Token Family
   └─ Denylist
```

### 핵심

```text
JWT Process HA
+
Security State DB HA
```

둘 다 필요하다.

---

# 59. Network Security Boundary

## FIG-PH-59. Network Zone

```text
Client
  │ TLS
  ▼
GSLB / L4
  │
  ▼
WEB
  │
  ▼
WAS
  │
  ▼
DB Network
```

확인 필요:

```text
TLS Termination
WEB→WAS 암호화
mTLS
Firewall
Direct WAS Port
Admin Port
JWKS Exposure
```

---

# 60. Direct WAS Access Risk

## FIG-PH-60. Bypass Risk

```text
Normal
Client → WEB → WAS

Risk
Client / Internal
      ─────────► WAS Direct
```

### 질문

```text
WAS Direct Port가 열려 있는가?
Business Filter가 자체 인증을 수행하는가?
Gateway/WEB 우회 방어가 있는가?
```

---

# 61. Timeout Physical Layers

## FIG-PH-61. End-to-End Timeout Stack

```text
Client
  ↓
GSLB / L4
  ↓
Apache Proxy
  ↓
Tomcat Request
  ↓
PDMG Worker Deadline
  ↓
Hikari Connection Wait
  ↓
JDBC / DB Query
```

### 원칙

```text
하위 Timeout
<
상위 Timeout
```

정확한 수치는 Config Evidence로 확정한다.

---

# 62. Capacity Timeout vs Current Config

## FIG-PH-62. Candidate vs Actual

```text
Capacity Document
Apache / DB / Transaction / Client 후보값
        │
        │ ≠
        ▼
PDMG Current Source
OnlineTimeoutExecutor = 5000ms
```

### 핵심

```text
Capacity Recommendation
≠
Current Runtime Config
```

---

# 63. Physical Configuration Evidence

## FIG-PH-63. Config Sources

```text
WEB
→ httpd.conf / vhost

WAS
→ server.xml

JVM
→ setenv.sh / startup options

Application
→ application.yml

Hikari
→ datasource config

Network
→ L4 / GSLB config

DB
→ DB service config
```

---

# 64. Capacity vs Actual Config Drift

## FIG-PH-64. Physical Drift

```text
Capacity Baseline
      │
      ▼ compare
Actual Config
      │
      ▼ compare
Runtime Metric
      │
      ▼
PASS / DRIFT
```

### 예

```text
Design maxThreads 800
Actual 1500
→ DRIFT

Design Session 60m
Actual 90m
→ DRIFT / Baseline Conflict

Design Hikari 80
Actual 200
→ DRIFT
```

---

# 65. Server Master Inventory

## FIG-PH-65. Physical Trace Chain

```text
Architecture Component
      ↓
Application Group
      ↓
System Group
      ↓
Environment
      ↓
Center
      ↓
Hostname
      ↓
Role
      ↓
CPU / Memory
      ↓
Middleware
      ↓
JVM
      ↓
WAR
      ↓
Port
      ↓
Datasource
      ↓
HA Group
      ↓
DR Pair
```

---

# 66. Inventory 최소 컬럼

## TEXT ARCHITECTURE 보완 — Inventory 최소 컬럼

```text
Architecture Component
      ↓
Center / Environment
      ↓
Hostname / VM
      ↓
CPU / Memory / Storage
      ↓
Middleware / JVM / WAR
      ↓
Port / Datasource
      ↓
HA / DR Pair
      ↓
Owner / Evidence
```


| Category | Fields |
|---|---|
| Business | Application Group / Business Code |
| Environment | Prod/Dev/DR/Pilot |
| Physical | Center / Hostname / IP / VM |
| Resource | CPU / Memory / Disk |
| Middleware | WEB/WAS/JVM |
| Deployment | WAR / Context / Port |
| Data | Datasource / DB |
| Availability | HA Group / DR Pair |
| Evidence | Source / Config / Verification |

---

# 67. Physical Hostname Evidence

물리 구성도에는 실제 Hostname 예가 존재할 수 있다.

예:

```text
sbmpcoltwb01-02
sbmpcoltws01-02
...
```

### 주의

```text
Hostname이 Marketing처럼 보임
=
pdmg-service 배포 Host 확정

X
```

Deployment Manifest 필요.

---

# 68. Physical Failure Domain Map

## FIG-PH-66. Blast Radius

```text
Code Failure
   ↓
WAR

JVM Failure
   ↓
All WARs in JVM

VM Failure
   ↓
All JVMs in VM

WEB Failure
   ↓
Route Member

L4 Failure
   ↓
VIP / Service

DB Failure
   ↓
Multiple Applications

Center Failure
   ↓
All Center Resources
```

---

# 69. Failure Isolation Verification

## FIG-PH-67. Logical vs Physical Isolation

```text
LOGICAL
Online Node
Event Node
ETL Node
       │
       ▼
PHYSICAL
Separate JVM / Host / Resource?
       │
       ├─ YES → Isolation preserved
       └─ NO  → Shared failure/resource risk
```

---

# 70. Physical Scaling Decision Tree

## FIG-PH-68. Scale Decision

```text
Workload 증가
  │
  ├─ CPU Bound?
  ├─ Memory Bound?
  ├─ DB Bound?
  ├─ Thread Bound?
  ├─ I/O Bound?
  └─ External Bound?
      │
      ▼
Scale-up / Scale-out / Tune / Isolate
```

### 금지

```text
느림
→ 서버 추가

만으로 결정
```

---

# 71. Capacity Test

## FIG-PH-69. Capacity Validation

```text
Normal
  ↓
Peak
  ↓
Stress
  ↓
Soak
  ↓
Node Failure
  ↓
DB Slow
  ↓
External Slow
  ↓
Session Load
  ↓
DR
```

---

# 72. Peak Test

```text
36,000 Users
  ↓
10% Concurrent Candidate
  ↓
1,200 TPS Candidate
  ↓
WEB/WAS/Worker/Hikari/DB
  ↓
p95 / CPU / Queue / Pool
```

---

# 73. Stress Test

```text
15%
  ↓
1,800 TPS Candidate
  ↓
Saturation Point
  ↓
Failure Mode
```

---

# 74. One Node Down Test

## FIG-PH-70. HA Capacity Test

```text
Peak Load
   +
WAS 1 Node Down
   ↓
Remaining Nodes
   ↓
SLA?
```

---

# 75. Hikari Saturation Test

```text
Pool Pressure
   ↓
Pending
   ↓
Worker Hold
   ↓
Queue
   ↓
Timeout
```

---

# 76. DB Slow Test

```text
Slow SQL
 ↓
Connection Hold
 ↓
Hikari Pending
 ↓
Worker Queue
 ↓
Tomcat Waiting
 ↓
504 / Timeout
```

---

# 77. Session Load Test

```text
Active Session
  ↓
Heap
  ↓
Replication Traffic
  ↓
GC
  ↓
Failover
```

최종 60/90분 정책 결정 후 재시험한다.

---

# 78. JWT Restart Test

```text
Token Issue
  ↓
JWT Restart
  ↓
Existing Token Verify?
```

Current ephemeral key 구조에서는 위험이 존재한다.

---

# 79. JWT Multi-instance Test

```text
JWT #1 / #2
   ↓
Same kid?
Same public key?
Same JWKS?
```

---

# 80. DR Test

## FIG-PH-71. End-to-End DR Test

```text
Main Failure
  ↓
GSLB Route
  ↓
DR WEB
  ↓
DR WAS
  ↓
Login / JWT
  ↓
Business Service
  ↓
DB
  ↓
External
  ↓
Session
  ↓
Failback
```

---

# 81. PHYSICAL Architecture Rules

## FIG-PH-72. Rule Cards

```text
PH-01 Server / JVM / WAR Separate
PH-02 Module ≠ Physical Server
PH-03 Logical Isolation must survive Physical Mapping
PH-04 Capacity Candidate ≠ Actual
PH-05 VM Memory ≠ Heap
PH-06 Thread ≠ Worker ≠ Hikari
PH-07 In-center Session ≠ Cross-center Session
PH-08 HA ≠ DR ≠ Backup
PH-09 JWT Multi-instance Key must be consistent
PH-10 Physical Config must be traceable
PH-11 Node Failure test required
PH-12 DR test required
```

---

# 82. 정상 패턴

```text
GSLB → L4 → WEB → WAS → JVM → WAR → Hikari → DB

Separate Event / ETL / Data Runtime

Center Internal HA

Approved DR Pair

Central Key Store for JWT

Inventory ↔ Config ↔ Runtime Metric
```

---

# 83. 금지 패턴

```text
Server = JVM = WAR                     X
pdmg-fw = 독립 Server                  X
VM Memory = Heap                       X
Tomcat Thread = Worker = Hikari        X
Session Replication across DR assumed  X
Capacity Candidate = Production Fact   X
Backup = DR                            X
JWT Instance마다 다른 Key              X
```

---

# 84. PDMG Physical Mapping Gap

## FIG-PH-73. Current Mapping Gap

```text
PDMG Source
   │
   ▼
Module / Artifact
   │
   ▼
[ GAP ]
   │
   ▼
Production Host / JVM / Context / Port
```

필요 Evidence:

```text
Deployment Manifest
server.xml
application.yml
eCAMS
L4 Pool
WEB VHost
Runtime Process
```

---

# 85. PDMG Local Port Guardrail

Local 예:

```text
pdmg-ui      : 8090
pdmg-service : 8080
```

### 금지

```text
Local Port
→ Production Port

자동 승격 X
```

---

# 86. PDMG Runtime Chain in Physical

## FIG-PH-74. PDMG Physical Runtime

```text
WEB / Client
   │
   ▼
Business WAS
   │
   ▼
Tomcat Request Thread
   │
   ▼
PDMG Worker
   │
   ▼
Transaction
   │
   ▼
DAO / Mapper
   │
   ▼
Hikari
   │
   ▼
RDW / DB
```

---

# 87. PDMG Security Physical

## FIG-PH-75. JWT / Service Physical

```text
Client
  │
  ▼
pdmg-jwt [Physical TBD]
  │
  ├─ Key Store [TO-BE]
  ├─ JWKS
  └─ Token State DB
  │
  ▼
Business WAS
  │
  └─ JWT Verify
```

---

# 88. PDMG OM Physical

```text
pdmg-om
  │
  ├─ Host?
  ├─ JVM?
  ├─ Port?
  ├─ DB?
  └─ Dashboard?
```

현재:

```text
[UNKNOWN]
```

---

# 89. NFR — Performance Physical Mapping

## FIG-PH-76. Performance Physical

```text
Performance
  ↓
Separate Runtime
  ↓
CPU / Memory
  ↓
Thread / Pool
  ↓
DB
  ↓
Load Test
  ↓
Metric
```

---

# 90. NFR — Availability Physical Mapping

```text
Availability
  ↓
Redundant Nodes
  ↓
Health Check
  ↓
Failover
  ↓
Residual Capacity
  ↓
DR
```

---

# 91. NFR — Scalability Physical Mapping

```text
Scalability
  ↓
VM Size
  ↓
Node Count
  ↓
L4 Distribution
  ↓
Independent Scale
```

---

# 92. NFR — Security Physical Mapping

```text
Security
  ↓
Network Boundary
  ↓
TLS
  ↓
Port Control
  ↓
Key Store
  ↓
DB Account
```

---

# 93. NFR — Observability Physical Mapping

```text
Observability
  ↓
Host
  ↓
JVM
  ↓
WAR
  ↓
Thread / Pool
  ↓
DB
  ↓
Metric / Log / Trace
```

---

# 94. Physical Evidence Types

```text
Inventory
Config
Deployment
Runtime Process
Metric
Load Test
Failover Test
DR Test
```

---

# 95. Evidence Strength

```text
Runtime Process / Config
       >
Deployment Manifest
       >
Inventory
       >
Capacity Design
       >
Architecture Drawing
       >
General Assumption
```

---

# 96. CONFIRMED / WORKING BASELINE

```text
[CONFIRMED / WORKING BASELINE]

- 의왕 Main / 안성 DR 구조
- 내부 GSLB 기반 센터 연결 방향
- Marketing WEB/WAS 이중화 역할
- GSLB→L4→WEB→WAS→JVM→WAR→DB 실행경로
- RDW / ADW Data Platform 분리
- Service VM / Data Dedicated Resource Hybrid 전략
- PDMG Worker 20 / Queue100 / 5000ms AS-IS snapshot
- 36,000 사용자 Capacity assumption
- Session 60/90 Variant 존재
- DeltaManager 센터 내부 복제 방향
```

---

# 97. CONFLICT

```text
[CONFLICT-PH-01]
Session 60분 vs 90분

[CONFLICT-PH-02]
SingleView Hikari 70~80 vs 100~120

[CONFLICT-PH-03]
Capacity Timeout Candidate vs PDMG Current 5000ms

[CONFLICT-PH-04]
Physical Strategy Baseline vs Latest Actual Inventory
```

---

# 98. OPEN

```text
[OPEN-PH-01]
PDMG Production Host/JVM/WAR/Port

[OPEN-PH-02]
실제 Apache/Tomcat Version

[OPEN-PH-03]
실제 JVM Heap / GC

[OPEN-PH-04]
실제 Tomcat maxThreads

[OPEN-PH-05]
실제 Hikari Config

[OPEN-PH-06]
Session 최종값

[OPEN-PH-07]
DR RPO/RTO

[OPEN-PH-08]
JWT Key Store 제품/위치

[OPEN-PH-09]
Direct WAS Port 통제

[OPEN-PH-10]
pdmg-om Physical Deployment
```

---

# 99. GAP Register

## TEXT ARCHITECTURE 보완 — GAP Register

```text
Expected / Required
      │
      ▼
Actual / Evidence
      │
      ▼
Difference
      │
      ▼
[GAP]
      │
      ├─ Owner
      ├─ Action
      ├─ ADR
      └─ Evidence
```


| ID | GAP | 영향 |
|---|---|---|
| GAP-PH-01 | PDMG Deployment Mapping 미완료 | Deployment |
| GAP-PH-02 | DR RPO/RTO 미확정 | DR |
| GAP-PH-03 | App Group A/B 실제 물리분리 미확정 | Isolation |
| GAP-PH-04 | DB HA/DR 상세 미확정 | Data Availability |
| GAP-PH-05 | Apache/Tomcat Actual Config 미확정 | Middleware |
| GAP-PH-06 | JVM Heap/GC Actual 미확정 | JVM |
| GAP-PH-07 | Tomcat Thread Actual 미확정 | Capacity |
| GAP-PH-08 | Hikari Actual 미확정 | DB Pool |
| GAP-PH-09 | Session Final Baseline 미확정 | Session |
| GAP-PH-10 | JWT Central Key Store 미확정 | Security |
| GAP-PH-11 | JWKS HA/DR 미확정 | Security |
| GAP-PH-12 | Refresh/Denylist DR 미확정 | Security |
| GAP-PH-13 | Network Direct Access 통제 미확정 | Security |
| GAP-PH-14 | Server Master Inventory↔Architecture 연결 미완료 | Traceability |
| GAP-PH-15 | Capacity↔Runtime Drift 자동검증 미완료 | Governance |

---

# 100. RISK Register

## TEXT ARCHITECTURE 보완 — RISK Register

```text
Cause
  ↓
Risk Event
  ↓
Business / Data / Security / Operation Impact
  ↓
Severity
  ↓
Mitigation / Control
  ↓
Owner
  ↓
Evidence / Closure
```


| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-PH-01 | Capacity Candidate를 운영값으로 오인 | High |
| RISK-PH-02 | Session 60/90 Drift | High |
| RISK-PH-03 | maxThreads 과대설정 | High |
| RISK-PH-04 | Hikari 과대설정 | High |
| RISK-PH-05 | Worker 과소설정 | High |
| RISK-PH-06 | Worker 과대설정 | High |
| RISK-PH-07 | 대형 JVM Blast Radius | High |
| RISK-PH-08 | VM Memory 전체 Heap 사용 | Critical |
| RISK-PH-09 | DR Session 무중단 오판 | High |
| RISK-PH-10 | JWT Instance별 다른 Key | Critical |
| RISK-PH-11 | Direct WAS Access | Critical |
| RISK-PH-12 | DR Test 부재 | Critical |
| RISK-PH-13 | DB/ETL/Event 자원 혼재 | High |
| RISK-PH-14 | Shared JVM 장애영역 오판 | High |
| RISK-PH-15 | Physical Inventory Drift | High |

---

# 101. ADR 후보

```text
ADR-PH-01 Session Baseline
ADR-PH-02 VM Size / Scale-out
ADR-PH-03 JVM Isolation
ADR-PH-04 App Group Physical Isolation
ADR-PH-05 Tomcat Thread Baseline
ADR-PH-06 Worker Pool Baseline
ADR-PH-07 Hikari Baseline
ADR-PH-08 JWT Key Store
ADR-PH-09 JWKS HA
ADR-PH-10 DR RPO/RTO
ADR-PH-11 Direct WAS Access
ADR-PH-12 TLS Boundary
ADR-PH-13 Server Inventory SSOT
ADR-PH-14 Capacity Drift Gate
```

---

# 102. MECHANISM Handoff

## FIG-PH-77. PHYSICAL → MECHANISM

```text
PHYSICAL Output
│
├─ Center
├─ Network Path
├─ WEB / WAS
├─ JVM / WAR
├─ Runtime Resource
├─ RDW / ADW
├─ HA / DR
├─ Security Boundary
└─ Physical Inventory
       │
       ▼
MECHANISM Input
│
├─ Online Interface
├─ Event Interface
├─ CDC / ETL
├─ Message / Header
├─ GUID / ServiceId
├─ Charset
├─ Framework Entry
├─ Transaction / Timeout Rule
├─ Retry / Recovery
├─ File Transfer
├─ JWT / SSO
└─ Logging / Error Contract
```

---

# 103. PHYSICAL → MECHANISM Example

## FIG-PH-78. Runtime Path to Mechanism

```text
WEB
 ↓
WAS
 ↓
Business WAR
 ↓
DB
```

Physical에서:

```text
"어디에 배치?"
```

를 답했다면,

MECHANISM에서는:

```text
"무슨 Contract로 통신?"
"무슨 Header?"
"무슨 Timeout?"
"무슨 Retry?"
"무슨 Error?"
```

를 답한다.

---

# 104. MECHANISM에서 반드시 답할 질문

```text
1. Online Interface 표준은 무엇인가?
2. Event는 어떤 Schema와 Key를 사용하는가?
3. CDC/ETL/File은 어떤 운영계약을 갖는가?
4. Message Header에 무엇이 들어가는가?
5. GUID/ServiceId는 어디서 생성/전파되는가?
6. Charset / Date / Number 표현은 무엇인가?
7. Framework Entry는 어디인가?
8. Timeout / Retry / Transaction Rule은 무엇인가?
9. Error Code / Envelope는 무엇인가?
10. JWT/SSO는 어떤 Trust Mechanism을 사용하는가?
```

---

# 105. PHYSICAL 최종 통합 지도

## FIG-PH-79. Physical Summary

```text
MAIN 의왕
   │
   ├─ GSLB / L4
   │
   ├─ WEB #1/#2
   │
   ├─ WAS #1/#2
   │    └─ Tomcat JVM
   │         └─ Business WAR
   │              └─ Framework + Business
   │
   ├─ Real-time/Event AP
   ├─ CDC / ETL
   ├─ RDW / ADW
   ├─ BI / Governance
   └─ Operations
        │
        │ DR
        ▼
DR 안성
   │
   └─ Alternate Runtime / Data / Route

Execution Resource
Thread → Worker → Hikari → DB

Governance
Inventory → Config → Test → Runtime Metric → Drift
```

---

# 보완검토 A. PHYSICAL Reference Coverage 보강

## FIG-PH-SUP-01. Center와 Environment는 다른 축

```text
의왕 Center
├─ 운영
├─ 개발
├─ 선도
└─ 검증

안성 Center
└─ DR
```

```text
Center
≠
Environment
```

---

## FIG-PH-SUP-02. Hardware Inventory

```text
Logical Node
   ↓
Hostname
   ↓
Center / Environment
   ↓
Type
   ↓
CPU / Memory / Storage / Network
   ↓
HA / DR Pair
   ↓
Owner / Evidence
```

---

## FIG-PH-SUP-03. Software Inventory

```text
Host / Host Group
      ↓
Product
      ↓
Version
      ↓
Role
      ↓
License / Support
      ↓
Config Baseline
      ↓
Owner / Evidence
```

PPT의 공식 S/W 목록 밖 제품/버전을 임의 창작하지 않는다.

---

## FIG-PH-SUP-04. DB Local HA와 DR 분리

```text
RDW Exadata RAC
├─ Node 1
└─ Node N
   │
   └─ Local HA / Active-Active
          │
          │ ≠
          ▼
      DR Replication / Recovery

ADW Exadata RAC
├─ Node 1
└─ Node N
```

정확한 Node 수는 DB Inventory로 확정한다.

---

## FIG-PH-SUP-05. OGG / CDC Physical Flow

```text
Core DB
   ↓
OGG Capture
   ↓
Trail / Queue
   ↓
Relay / Downstream
   ↓
RDW Apply
```

운영 핵심:

```text
Checkpoint
Lag
Restart Position
Source Load
Apply Error
```

---

## FIG-PH-SUP-06. OLTP와 대용량 Batch 자원 분리

```text
OLTP Peak
  │
  └────────► Online Resource / SLA

Large Batch
  │
  └────────► Batch / ETL Resource Window
```

```text
OLTP Peak
≠
Large Batch Window
```

---

## FIG-PH-SUP-07. Hostname Naming

```text
Hostname = 12 chars
│
├─ 법인          2
├─ Application   4
├─ Platform/Server 1
├─ Environment   1
├─ Role          2
└─ Sequence      2
```

기존 Baseline 규칙이며 실제 Hostname은 Inventory와 대조한다.

---

## FIG-PH-SUP-08. Filesystem Standard

```text
Filesystem
│
├─ OS Standard Mount
├─ AP Business Path
└─ DB Business Path
```

예외 경로는 예외표/ADR로 관리한다.

---

## FIG-PH-SUP-09. Account Standard

```text
Environment Separation
        ↓
Role Separation
        ↓
Least Privilege
        ↓
Secret Separation
        ↓
Audit
```

---

## FIG-PH-SUP-10. Port Inventory

```text
Service
  ↓
Host / JVM
  ↓
Listen Port
  ↓
Protocol
  ↓
L4 / Firewall
  ↓
Owner / Evidence
```

```text
Port
= Managed Inventory Object
```

개별 Host의 임의 설정으로 두지 않는다.

---

# 106. Definition of Done

## TEXT ARCHITECTURE 보완 — Definition of Done

```text
Architecture Inputs
      ↓
Structure / Contract / Runtime Check
      ↓
Evidence Check
      ↓
GAP / CONFLICT / UNKNOWN Review
      ↓
PASS Condition
      │
      ├─ satisfied → PASS
      └─ pending   → CONDITIONAL PASS
```


## 106.1 Center / Network

- [x] Main / DR Center 시각화
- [x] GSLB / L4 / WEB / WAS 경로 정의
- [x] Traffic Layer 책임 구분
- [x] Direct Access 위험 정의

## 106.2 WEB / WAS / JVM / WAR

- [x] WEB VM / Apache Instance 구분
- [x] Server / JVM / WAR 구분
- [x] Module / WAR 구분
- [x] Shared JVM Failure Domain 정의
- [x] Strong Isolation 후보 정의

## 106.3 Data Platform

- [x] RDW / ADW Physical 역할 정의
- [x] CDC / ETL 역할 정의
- [x] Hybrid Physical Strategy 정의
- [x] Data Dedicated Resource와 Service VM 구분

## 106.4 Capacity

- [x] 36,000 사용자 기반 Candidate 표시
- [x] 600/1200/1800 TPS Candidate 표시
- [x] 8C/16C/32C Variant
- [x] JVM Heap Candidate
- [x] Tomcat/Worker/Hikari 분리
- [x] Hikari Conflict 표시

## 106.5 Session / HA / DR

- [x] Session 60/90 Conflict
- [x] DeltaManager In-center
- [x] Cross-center Session 미복제 방향
- [x] Node Failure HA
- [x] Center DR
- [x] HA/DR/Backup 분리

## 106.6 Security

- [x] JWT Multi-instance Key 문제
- [x] Central Key Store TO-BE
- [x] JWKS HA
- [x] Refresh/Denylist State
- [x] TLS / Direct WAS Access Open

## 106.7 PDMG

- [x] PDMG Physical Mapping Candidate
- [x] pdmg-fw Standalone Server 오해 제거
- [x] Local Port→Production Port 승격 금지
- [x] pdmg-om UNKNOWN 유지

## 106.8 Governance

- [x] Inventory Trace Chain
- [x] Config Drift
- [x] Capacity Test
- [x] DR Test
- [x] CONFIRMED/CONFLICT/OPEN/GAP/RISK
- [x] MECHANISM Handoff

**PHYSICAL 장 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. 최신 Production Deployment Manifest 확보
2. 실제 Apache/Tomcat/JVM/Hikari Config 수집
3. Session 최종 Baseline 확정
4. Worker 20/100 부하시험
5. JWT Central Key Store/JWKS HA 확정
6. DR RPO/RTO 및 Runbook 승인
7. Server Master Inventory와 Architecture Mapping
8. Node Failure + Peak Load Test
9. DR End-to-End Test

---

# 107. 다음 장

다음은 **05. MECHANISM — Interface / Message / GUID / Framework / Transaction / Timeout / Security / Logging**이다.

다음 장은 PHYSICAL에서 정한:

```text
WEB
WAS
JVM
WAR
DB
Event
CDC
ETL
Network Boundary
```

사이에 실제로 어떤 표준 Contract가 흐르는지를 정의한다.

즉,

```text
PHYSICAL
"어디에 배치할 것인가?"

        ↓

MECHANISM
"어떤 규칙으로 동작할 것인가?"
```

로 넘어간다.

---


====================================================================================================

# CHAPTER 05

====================================================================================================

# NSIGHT / PDMG 아키텍처 정의서
# 05. MECHANISM — Interface / Message / GUID / Framework / Transaction / Timeout / Security / Logging

> 보완개정: **v2 — TEXT Architecture Figure Coverage / Reference Coverage / Evidence Consistency 재점검**
## TEXT ARCHITECTURE 보완 — 장 전체 위치

```text
PHYSICAL BOUNDARY
   ↓
Interface
   ↓
Message
   ↓
GUID/ServiceId
   ↓
TX/Timeout
   ↓
Security
   ↓
RUNTIME
```

## Visual-First / Top-down / Drill-down 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-VISUAL-MECHANISM-05`  
> Architecture Level: **MECHANISM / L4**  
> 문서 상태: **Draft / Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_04_PHYSICAL_VISUAL_FIRST_TOPDOWN_상세본.md`  
> 후속 장: **06. RUNTIME**

---

# 0. 이 문서를 읽는 방법

MECHANISM은 **“어디에 배치했는가?”가 아니라 “어떤 표준 계약과 실행규칙으로 동작하는가?”**를 정의한다.

```text
PHYSICAL
WEB / WAS / JVM / WAR / DB / Event / CDC / ETL
        │
        ▼
MECHANISM
Interface Selection
        │
        ▼
Standard Entry
        │
        ▼
Message / Header / GUID / ServiceId
        │
        ▼
Framework / Business Boundary
        │
        ▼
Transaction / Timeout / Retry
        │
        ▼
Event / CDC / ETL / File / Batch
        │
        ▼
Security / JWT / SSO
        │
        ▼
Error / Logging / Evidence
        │
        ▼
RUNTIME
Sequence / Thread / TX / Failure / Recovery
```

---

# 1. VISUAL ROUTE — MECHANISM 전체를 한 장으로 보기

## FIG-MEC-01. Mechanism Architecture Journey

```text
╔══════════════════════════════════════════════════════════════════════════════╗
║                           MECHANISM ARCHITECTURE                             ║
╚══════════════════════════════════════════════════════════════════════════════╝

 ① INTERFACE
    Online / Event / CDC / ETL / File / Batch
         │
         ▼
 ② ENTRY
    Gateway / MCA-MCI / Direct / Standard Controller
         │
         ▼
 ③ MESSAGE
    Header / DTO / Result / Charset
         │
         ▼
 ④ TRACE
    GUID / ServiceId / InterfaceId
         │
         ▼
 ⑤ FRAMEWORK
    Filter / Context / TCF / Dispatcher / Handler
         │
         ▼
 ⑥ EXECUTION RULE
    Transaction / Timeout / Retry / Idempotency
         │
         ▼
 ⑦ SECURITY
    Authentication / Authorization / JWT / SSO / Key
         │
         ▼
 ⑧ ERROR / LOGGING
    HTTP Status / Error Code / Log / ImageLog / Audit
         │
         ▼
 ⑨ RUNTIME
    실제 Sequence / Thread / Failure / Recovery / Evidence
```

---

# 2. PHYSICAL → MECHANISM Handoff

## FIG-MEC-02. Physical Boundary를 Contract로 변환

```text
WEB
 ↓
WAS
 ↓
JVM
 ↓
WAR
 ↓
DB
```

PHYSICAL이 답한 것:

```text
어디에 배치되는가?
```

MECHANISM이 답할 것:

```text
무슨 Interface인가?
무슨 Header인가?
무슨 Timeout인가?
무슨 Error Contract인가?
무슨 Security인가?
무슨 Trace를 남기는가?
```

---

# 3. MECHANISM 핵심 원칙

## FIG-MEC-03. Boundary → Purpose → Contract

```text
Boundary
   ↓
Purpose
   ↓
Mechanism Type
   ↓
Contract
   ↓
Security
   ↓
Timeout / Retry
   ↓
Recovery
   ↓
Trace / Evidence
```

> **경계를 먼저 정의하고, 그 경계를 통과하는 표준계약을 정의한다.**

---

# 4. 목적별 Interface Selection

## FIG-MEC-04. Interface Decision Tree

```text
업무 목적?
   │
   ├─ 즉시 Request / Response
   │      └─ API / MCA / MCI / Controlled Online
   │
   ├─ 비동기 Event
   │      └─ Kafka / Event Broker
   │
   ├─ DB 변경 전달
   │      └─ CDC
   │
   ├─ 대량 데이터 이동
   │      └─ ETL
   │
   ├─ 파일 전달
   │      └─ MFT / FOS
   │
   └─ 일정 기반 대량처리
          └─ Batch Framework
```

### 금지

```text
모든 연계 = REST             X
모든 연계 = DB Direct        X
Bulk Data = Online API       X
Event = Sync HTTP            X
```

---

# 5. 공통 Interface Contract

## FIG-MEC-05. Universal Contract

```text
Interface
│
├─ Interface ID
├─ Producer
├─ Consumer
├─ Purpose
├─ Sync / Async
├─ Schema
├─ Security
├─ Timeout
├─ Retry
├─ Idempotency
├─ Error
├─ Recovery
├─ Trace Key
├─ Owner
└─ Evidence
```

---

# 6. Online Interface

## FIG-MEC-06. Standard Online Path

```text
Channel / Client
      │
      ▼
Gateway / MCA / MCI / Direct
      │
      ▼
WEB / WAS
      │
      ▼
Standard Entry
      │
      ▼
ServiceId
      │
      ▼
Business Runtime
      │
      ▼
Owned / Approved Data
```

---

# 7. Direct도 표준을 유지한다

## FIG-MEC-07. Controlled Direct

```text
Information Terminal / Package UI
       │
       ▼
Direct HTTP/JSON
       │
       ▼
Standard Header
       │
       ▼
ServiceId
       │
       ▼
Business Runtime
```

```text
Direct
≠
표준 면제
```

필수:

```text
GUID
ServiceId
Authentication
Authorization
Timeout
Error Contract
Logging
```

---

# 8. Gateway / APIM 역할

## FIG-MEC-08. Gateway Responsibility

```text
External / Channel
       │
       ▼
Gateway / APIM
       │
       ├─ Authentication
       ├─ Routing
       ├─ Rate Control
       ├─ Policy
       └─ Access Log
       │
       ▼
Business Service
```

금지:

```text
Gateway = Business Logic       X
Gateway → DAO                  X
Gateway → Internal Table DML   X
```

---

# 9. MCA / MCI 역할

## FIG-MEC-09. MCA / MCI Boundary

```text
Channel Message
      │
      ▼
MCA / MCI
      │
      ├─ Channel Protocol
      ├─ Routing
      ├─ Header Mapping
      └─ Format Conversion
      │
      ▼
NSIGHT Application
```

---

# 10. Standard Message

## FIG-MEC-10. PDMG Current Request Envelope

```text
Request
┌─────────────────────────────────┐
│ hdr_nhnis                       │
│   └─ sys_comm                   │
│      ├─ std_gbl_id              │
│      ├─ rms_svc_c               │
│      ├─ tr_sysid                │
│      ├─ tr_trm_ipadr            │
│      ├─ tr_brc                  │
│      ├─ scid                    │
│      └─ optr_eno                │
│                                 │
│ dto                             │
│   └─ business input             │
└─────────────────────────────────┘
```

Current PDMG:

```text
Request = { hdr_nhnis, dto }
```

---

# 11. Success / Error Envelope

## FIG-MEC-11. Response Contract

```text
[Success]

Business Result
   ↓
Framework Response
   ↓
{ hdr_nhnis, dto }


[Known Error]

Exception / Business Error
   ↓
Error Mapping
   ↓
{ hdr_nhnis, result }
```

Current PDMG known contract:

```text
Success = { hdr_nhnis, dto }
Error   = { hdr_nhnis, result }
```

---

# 12. Header vs DTO

## FIG-MEC-12. Message Responsibility

```text
Header
= System / Trace / Identity Context

DTO
= Business Input / Output
```

```text
Request
├─ hdr_nhnis
└─ dto
```

Business Service는 가능한 한 DTO를 명시적으로 사용하고,
공통 Trace/User/System 정보는 Framework Context로 관리한다.

---

# 13. Header Trust Boundary

## FIG-MEC-13. optr_eno는 자체 인증증거가 아니다

```text
Client Header
optr_eno
   │
   │ Untrusted by itself
   ▼
Authentication Evidence
   │
   ▼
Principal
   │
   ▼
Identity Binding
   │
   ▼
Authorization
```

---

# 14. GUID

## FIG-MEC-14. GUID End-to-End

```text
std_gbl_id
   │
   ▼
Filter
   │
   ▼
ServiceContext
   │
   ▼
MDC
   │
   ▼
TCF / Business
   │
   ▼
SQL / External
   │
   ▼
Response / ImageLog / Trace
```

### 역할

```text
GUID = 한 거래흐름의 Correlation Key
```

---

# 15. ServiceId

## FIG-MEC-15. ServiceId Runtime Routing

```text
Request
  │
  ▼
rms_svc_c / Path
  │
  ▼
ServiceId
  │
  ▼
TransactionDispatcher
  │
  ▼
Handler
  │
  ▼
Business
```

### 역할

```text
ServiceId = 무슨 업무거래인가?
```

---

# 16. GUID vs ServiceId

## FIG-MEC-16. Two Different Keys

```text
GUID
= 이 호출이 어느 흐름에 속하는가?

ServiceId
= 이 호출이 무슨 업무거래인가?
```

```text
GUID G1
   └─ ServiceId mgcoa9001S0
```

---

# 17. Header / Path ServiceId Conflict

## FIG-MEC-17. Multiple Sources

```text
ServiceContext.header.rms_svc_c
           │
           ▼
Request JSON rms_svc_c
           │
           ▼
Path Variable
```

Risk:

```text
Header ServiceId ≠ Path ServiceId
```

TO-BE 후보:

```text
Mismatch Reject
```

---

# 18. Charset

## FIG-MEC-18. Encoding Boundary

```text
Client
  │
  ▼
Entry Adapter
  │
  ├─ Validate Charset
  └─ Convert if needed
  │
  ▼
Application Internal String
  │
  ▼
External / File / DB
```

기본 신규 방향:

```text
UTF-8 중심
```

Legacy/File/대외는 별도 Contract로 관리한다.

---

# 19. Framework vs Business

## FIG-MEC-19. Responsibility Boundary

```text
┌──────────────── FRAMEWORK ────────────────┐
│ Filter                                    │
│ Context                                   │
│ TCF                                       │
│ Timeout                                   │
│ Transaction Control                      │
│ Error                                     │
│ Logging                                   │
└──────────────────┬────────────────────────┘
                   ▼
┌──────────────── BUSINESS ─────────────────┐
│ Handler                                   │
│ Facade                                    │
│ Service                                   │
│ DAO / Mapper                              │
│ Business Validation / Decision            │
└───────────────────────────────────────────┘
```

---

# 20. PDMG Current Mechanism Reference

## FIG-MEC-20. PDMG AS-IS Components

```text
PDMG
│
├─ DefaultFilter
├─ ServiceContext
├─ ServicePreventionInterceptor
├─ OnlineTransactionController
├─ TcfFacade
├─ OnlineTimeoutExecutor
├─ TransactionDispatcher
├─ TransactionHandler
├─ ResponseBodyAdvice
├─ GlobalExceptionHandler
└─ ImageLog
```

이 구조는 Reference이며 NSIGHT Target 전체로 자동 승격하지 않는다.

---

# 21. TCF ON / OFF

## FIG-MEC-21. Entry Difference

```text
[TCF ON]

Request
 ↓
OnlineTransactionController
 ↓
TcfFacade
 ↓
Dispatcher
 ↓
Handler
 ↓
Business


[TCF OFF]

Request
 ↓
Business Controller
 ↓
Facade / Service
 ↓
DAO
```

### 핵심 질문

```text
ON/OFF에서
Timeout
Transaction
Error
Logging
Policy
가 동일한가?
```

---

# 22. Transaction 8-Step Responsibility Model

## FIG-MEC-22. 8 Steps

```text
① System Pre
      ↓
② Common Pre
      ↓
③ Business Pre
      ↓
④ Controller
      ↓
⑤ Business Service
      ↓
⑥ Business Post
      ↓
⑦ Common Post
      ↓
⑧ System Post
```

### 주의

Current PDMG 실제 TcfFacade가 STF/ETF를 호출하지 않는다면,
STF/ETF를 AS-IS 실행단계처럼 그리지 않는다.

---

# 23. Current 실제 선후처리

## FIG-MEC-23. PDMG Current Pre/Post

```text
DefaultFilter
    ↓
ServicePreventionInterceptor.preHandle
    ↓
Controller / TCF / Business
    ↓
ServicePreventionInterceptor.afterCompletion
    ↓
DefaultFilter finally
    ↓
Context Clear
```

---

# 24. Transaction Boundary

## FIG-MEC-24. Current Worker TX

```text
Request Thread
   │
   ▼
OnlineTimeoutExecutor
   │
   │ submit
   ▼
Worker Thread
   │
   ▼
TransactionTemplate BEGIN
   │
   ▼
Dispatcher
   ↓
Handler
   ↓
Facade @Transactional(REQUIRED)
   ↓
Service
   ↓
DAO / Mapper / SQL
   │
   ▼
COMMIT / ROLLBACK
```

### 핵심

```text
@Transactional annotation 위치
≠
실제 물리 TX BEGIN 위치
```

---

# 25. Transaction Owner

## FIG-MEC-25. TX Owner Determination

```text
Runtime Call Path
  ↓
Transaction Manager
  ↓
TransactionTemplate / AOP
  ↓
DB Connection
  ↓
Commit / Rollback
```

Transaction Owner는 소스 Annotation 하나로 판단하지 않는다.

---

# 26. Timeout Hierarchy

## FIG-MEC-26. Budget

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

# 27. Current PDMG Timeout Snapshot

## FIG-MEC-27. AS-IS Snapshot

```text
tcf.enabled        = true
timeout.enabled    = true
milliseconds       = 5000
pool-size          = 20
queue-capacity     = 100
```

```text
[AS-IS SNAPSHOT]
```

NSIGHT Target NFR로 자동 승격하지 않는다.

---

# 28. Request Thread vs Worker

## FIG-MEC-28. Thread Split

```text
Request Thread
   │
   │ Future.get(5000ms)
   ▼
OnlineTimeoutExecutor
   │
   └─ submit
        │
        ▼
   Worker pdmg-online-N
        │
        ▼
     Business / DB
```

---

# 29. Timeout Timeline

## FIG-MEC-29. HTTP Timeout vs Worker

```text
T0
Request Thread        Worker Thread
    │                       │
    │ wait                  │ business
    │                       │ SQL
    │                       │
T+5s│ timeout               │ still running?
    │                       │
    ├─ cancel(true)         │
    └─ 504                  │
```

### 절대 동일시 금지

```text
HTTP 504
≠ Worker 종료
≠ JDBC Cancel
≠ DB Rollback 완료
```

---

# 30. cancel(true)의 한계

## FIG-MEC-30. Interrupt

```text
future.cancel(true)
      │
      ▼
Thread interrupt request
      │
      ├─ Java code 반응?       verify
      ├─ JDBC driver 반응?     verify
      ├─ SQL cancel?           verify
      └─ DB session kill?      자동 가정 금지
```

---

# 31. Late Commit Prevention

## FIG-MEC-31. Deadline Guard

```text
HTTP Timeout
   ↓
Worker continues
   ↓
SQL returns
   ↓
Deadline Check
   │
   ├─ expired → ROLLBACK
   └─ valid   → COMMIT
```

목적:

```text
사용자는 실패 응답을 받았는데
DB가 뒤늦게 성공 commit 되는 문제 방지
```

---

# 32. Retry

## FIG-MEC-32. Retry Decision

```text
Failure
  │
  ├─ Transient?
  │     ├─ YES
  │     │   ↓
  │     │ Backoff
  │     │   ↓
  │     │ Max Retry
  │     │   ↓
  │     │ DLQ / Recovery
  │     │
  │     └─ NO → No Retry
```

---

# 33. Retry 금지

```text
금융거래 DML
중복위험 거래
Validation Error
Authorization Error
Business Reject
Non-idempotent Command
```

→ Blind Retry 금지.

---

# 34. Idempotency

## FIG-MEC-33. Duplicate Prevention

```text
Retryable Command
      │
      ▼
Idempotency Key
      │
      ▼
Already Processed?
   ├─ YES → previous result / reject
   └─ NO  → execute
```

---

# 35. Timeout + Retry 중복위험

## FIG-MEC-34. Duplicate Execution Risk

```text
Original Request
   │
   ├─ Worker still executing
   │
   └─ Caller sees Timeout
            │
            ▼
          Retry
            │
            ▼
       Duplicate DML Risk
```

---

# 36. Error Taxonomy

## FIG-MEC-35. Error Classes

```text
Validation
Authentication
Authorization
Business Reject
Routing / Handler
Timeout
Overload
External
DB / SQL
System / Unknown
```

각 Error Class는 Retry/HTTP/Logging 정책이 달라야 한다.

---

# 37. HTTP Status + Application Error Code

## FIG-MEC-36. Dual Contract

```text
HTTP Status
= Transport / Runtime Result

Application Error Code
= Framework / Business Meaning
```

Known PDMG examples:

```text
503 + FW_OVERLOADED
504 + FW_TIMEOUT
500 + SERVICE / BIZ Error
```

---

# 38. Early Filter Error

## FIG-MEC-37. MVC 우회 오류

```text
Request
  ↓
DefaultFilter
  │
  ├─ 400
  └─ 401
  │
  ▼
sendError
```

이 경우:

```text
ResponseBodyAdvice
GlobalExceptionHandler
Standard Result Envelope
```

를 우회할 수 있다.

```text
[GAP]
Early Error Standardization
```

---

# 39. Generic Exception Gap

## FIG-MEC-38. Unknown Exception

```text
Unknown Runtime / SQL Exception
       │
       ▼
Explicit Handler?
  ├─ YES → Standard Error
  └─ NO  → Spring default /error 가능
```

필요:

```text
Stable Code
Safe Message
Trace ID
No sensitive stack exposure
```

---

# 40. Logging Mechanism

## FIG-MEC-39. Logging Channels

```text
Runtime
│
├─ MDC Correlation Log
├─ Application Transaction Log
├─ SQL Log
├─ ImageLog
├─ Security Audit
└─ Deployment / Operation Log
```

---

# 41. MDC

## FIG-MEC-40. MDC Correlation

```text
GUID
ServiceId
UserId
Client IP
SQL ID
   │
   ▼
MDC / ThreadContext
   │
   ▼
Application Log
```

Worker Thread 전환 시 명시적 Capture/Restore/Clear가 필요하다.

---

# 42. ImageLog

## FIG-MEC-41. ImageLog Lifecycle

```text
Request
  ↓
PRE INSERT
  ↓
Business Runtime
  │
  ├─ Normal
  │    ↓
  │ POST UPDATE
  │
  └─ Exception
       ↓
      EX UPDATE / INSERT
```

ImageLog는 Business Ledger가 아니라 운영/감사 Evidence다.

---

# 43. ImageLog Fail-open

## FIG-MEC-42. Audit Failure

```text
ImageLog Write Failure
      │
      ├─ Business Continue
      └─ Log Error
```

장점:

```text
Business Availability 보호
```

위험:

```text
Audit Gap
```

필요:

```text
Alert
Reconciliation
Recovery
```

---

# 44. Sensitive Logging

## FIG-MEC-43. Never Log Raw

```text
Authorization Bearer
Access Token
Refresh Token
Password
Private Key
HMAC Secret
DB Password
Sensitive Personal Data
```

---

# 45. SQL Evidence

## FIG-MEC-44. ServiceId → SQL

```text
ServiceId
  ↓
Handler
  ↓
Service
  ↓
DAO / Mapper
  ↓
SqlId
  ↓
Elapsed / Rows / Error
```

SQL Parameter는 Masking 정책을 적용한다.

---

# 46. Event Mechanism

## FIG-MEC-45. Event Contract

```text
Producer
  │
  ▼
Event
  ├─ Event Type
  ├─ Event Key
  ├─ Event Time
  ├─ Producer
  ├─ Payload
  └─ Correlation
  │
  ▼
Kafka / Broker
  │
  ▼
Consumer
```

---

# 47. Event State

## FIG-MEC-46. Event Processing State

```text
PRODUCED
  ↓
STORED
  ↓
CONSUMED
  ↓
PROCESSED
  ↓
ACK / OFFSET COMMIT

Failure
  ├─ Retry
  ├─ Replay
  ├─ DLQ
  └─ Manual Recovery
```

---

# 48. Event Idempotency

## FIG-MEC-47. Duplicate Event

```text
Same Event Key
    │
    ├─ first     → process
    └─ duplicate → skip / reconcile
```

---

# 49. CDC Mechanism

## FIG-MEC-48. CDC Flow

```text
Source DB
  │
  ▼
Capture
  │
  ▼
Transport / Relay
  │
  ▼
Apply
  │
  ▼
RDW
```

필수 운영지표:

```text
Capture Lag
Transport Lag
Apply Lag
Error
Restart
```

---

# 50. CDC 금지

```text
CDC = Business API 대체                  X
CDC = Strong Synchronous Consistency      X
Lag 측정 없이 "실시간" 주장             X
Source DB Impact 검증 없이 적용          X
```

---

# 51. ETL Mechanism

## FIG-MEC-49. ETL Flow

```text
Source
  ↓
Extract
  ↓
Transform
  ↓
Validate
  ↓
Load
  ↓
Reconcile
  ↓
ADW / Mart
```

---

# 52. ETL 금지

```text
Online Request 안에서 ETL 실행       X
Restart 없는 Script성 Batch          X
Online DB Pool과 Heavy ETL 공유      X
Reconciliation 없이 성공 선언        X
```

---

# 53. File Mechanism

## FIG-MEC-50. File Contract

```text
Producer
  ↓
Create File
  │
  ├─ Filename
  ├─ Charset
  ├─ Schema
  ├─ Record Count
  ├─ Hash
  └─ Encryption
  ↓
MFT / FOS
  ↓
Consumer
  ↓
Validate / Process / Archive
```

---

# 54. File State

## FIG-MEC-51. File Runtime State

```text
READY
  ↓
TRANSFER
  ↓
RECEIVED
  ↓
VALIDATED
  ↓
PROCESSED
  ↓
ARCHIVED

Failure
→ RETRY / QUARANTINE / MANUAL RECOVERY
```

---

# 55. File Recovery

## FIG-MEC-52. Recovery

```text
Transfer Failure
   ↓
Retry
   ↓
Max Retry
   ↓
Quarantine
   ↓
Operator Recovery
   ↓
Reconciliation
```

---

# 56. Batch Framework

## FIG-MEC-53. Batch Runtime Contract

```text
Scheduler / Control-M
      │
      ▼
Batch Entry
      │
      ▼
Job
      │
      ▼
Step
      │
      ▼
Reader / Processor / Writer
      │
      ▼
DB / File / External
```

---

# 57. Batch Dependency

## FIG-MEC-54. Batch Orchestration

```text
Job A
  ↓ success
Job B
  ↓ success
Job C
```

필수:

```text
Schedule
Dependency
Business Date
Job Parameter
Restart Point
Recovery
```

---

# 58. Batch Restart

## FIG-MEC-55. Restart Contract

```text
FAILED
  ↓
Failure Point
  ↓
Restartable?
  ├─ YES → restart/checkpoint
  └─ NO  → compensate / full rerun
```

---

# 59. Delivery Mechanism

## FIG-MEC-56. Source → Runtime

```text
Source
  ↓
Build
  ↓
Artifact
  ↓
Deploy
  ↓
Config
  ↓
Runtime
```

원칙:

```text
Build Once
Promote Artifact
Environment Config Separation
```

---

# 60. 수동 운영변경 금지

```text
운영서버 직접 Compile       X
운영 WAR 직접 덮어쓰기       X
운영 Config 무승인 수정      X
변경 Evidence 없음           X
```

---

# 61. Solution Boundary

## FIG-MEC-57. Commercial Solution Contract

```text
Business Solution
      │
      ▼
Standard Adapter / Interface
      │
      ▼
NSIGHT Service / Data / Framework
```

```text
Solution
≠ Standard 면제
```

---

# 62. SELF-BI

## FIG-MEC-58. Self-BI Contract

```text
Self-BI User
    ↓
BI Portal
    ↓
Approved Data Access
    ↓
RDW / ADW
```

금지:

```text
Self-BI → Core Unlimited SQL
```

---

# 63. EBM

## FIG-MEC-59. Event → Decision → Offering

```text
Customer Action
   ↓
Event
   ↓
Behavior Processing
   ↓
EBM Decision
   ↓
Offer / Contact
```

---

# 64. SSO / JWT

## FIG-MEC-60. Security Chain

```text
User
 ↓
SSO / Authentication
 ↓
Identity Evidence
 ↓
Token Issue
 ↓
Service Request
 ↓
JWT Verify
 ↓
Principal
 ↓
Authorization
```

---

# 65. PDMG Normal Login Reference

## FIG-MEC-61. mgjwa1000C0

```text
User
 ↓
pdmg-ui
 ↓
mgjwa1000C0
 ↓
Credential Check / BCrypt
 ↓
JwtTokenIssuer
 ↓
RS256 Access Token
+
Random Refresh Token
 ↓
Refresh Hash DB
```

---

# 66. PDMG SSO Reference

## FIG-MEC-62. mgjwa1000C1

```text
Trusted Caller
   ↓
mgjwa1000C1
   │
   ├─ allowed service
   ├─ timestamp
   ├─ HMAC
   └─ caller IP
   ↓
Trusted User Info
   ↓
PDMG Token Pair
```

이 흐름을 직접 OIDC Callback이라고 단정하지 않는다.

---

# 67. Key / Secret Taxonomy

## FIG-MEC-63. Secret Separation

```text
RS256 Private Key
= Token Signing

RS256 Public Key / JWKS
= Token Verification

HMAC Secret
= Trusted Caller Validation

Legacy jwt.secret
= 별도 Legacy Mechanism 가능
```

---

# 68. JWT Verify

## FIG-MEC-64. Verification Contract

```text
Bearer Token
  ↓
Signature
  ↓
Issuer / Audience / Expiration [Target]
  ↓
Principal
  ↓
Identity Binding
  ↓
Authorization
```

---

# 69. Identity Binding GAP

## FIG-MEC-65. ssoId vs optr_eno

```text
Validated JWT Subject
      │
      └─ ssoId
          │
          │ ?
          ▼
Header User
      └─ optr_eno
```

```text
[GAP]
Trusted Principal → Header/UserContext Binding
```

---

# 70. Authentication vs Authorization

## FIG-MEC-66. Two Security Decisions

```text
Authentication
= 누구인가?

        ↓

Authorization
= 무엇을 할 수 있는가?
```

SecurityFilterChain 존재만으로 업무인가가 완결되었다고 판단하지 않는다.

---

# 71. Security Revalidation

## FIG-MEC-67. Trust Boundary Revalidation

```text
Gateway Authentication
       ↓
Application JWT Verify
       ↓
Principal
       ↓
Business Authorization
       ↓
Data Authorization
```

---

# 72. Direct WAS Bypass

## FIG-MEC-68. Bypass Defense

```text
Normal
Client → WEB/Gateway → WAS

Risk
Internal Client ─────→ WAS Direct
```

필요:

```text
Network ACL
JWT Validation
Trusted Source Policy
```

---

# 73. Exception Mechanism

## FIG-MEC-69. Exception Flow

```text
Exception
  ↓
Classify
  ↓
Map
  ↓
HTTP Status + App Code
  ↓
Safe Message
  ↓
Trace / Log / Audit
```

---

# 74. External vs Internal Error Detail

## FIG-MEC-70. Safe Error Boundary

```text
Internal
├─ Stack
├─ Class
├─ Method
└─ Root Cause

External
├─ Stable Error Code
├─ Safe Message
└─ Trace ID
```

---

# 75. Traceability Keys

## FIG-MEC-71. Key Map

```text
Architecture / Business Transaction
→ ServiceId

Runtime Request
→ GUID

System Interface
→ InterfaceId

Event
→ EventId / CorrelationId

File
→ FileId / InterfaceId

Batch
→ JobId / ExecutionId
```

---

# 76. Service Trace

## FIG-MEC-72. ServiceId Drill-down

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
Mapper / SQL
  ↓
DB
```

---

# 77. Error Trace

## FIG-MEC-73. Error Drill-down

```text
HTTP Status
  ↓
Error Code
  ↓
GUID
  ↓
ServiceId
  ↓
Application / JVM
  ↓
Worker
  ↓
SQL / External
```

---

# 78. Retry / Security / Timeout Decision

## FIG-MEC-74. Failure Decision Matrix

```text
Failure
  │
  ├─ Validation      → No Retry
  ├─ Authentication  → No Retry
  ├─ Authorization   → No Retry
  ├─ Business Reject → No Retry
  ├─ Timeout         → Original status 확인
  └─ Transient Infra → Controlled Retry Candidate
```

---

# 79. MUST — Interface

```text
Interface ID
Producer / Consumer
Purpose
Schema
Security
Timeout
Error
Recovery
Trace
Owner
```

---

# 80. MUST — ServiceId

```text
Unique
Naming Standard
Registered Handler
Handler Branch
Header/Path Consistency
Trace
```

---

# 81. MUST — GUID

```text
Entry에서 생성/수용
임의 재생성 금지
Thread / Interface 간 전파
Error/Log/Evidence 포함
```

---

# 82. MUST — Framework

```text
공통 기능 중앙화
Context lifecycle 관리
Thread propagation 명시
Business Logic 침투 금지
Error/Logging 일관성
```

---

# 83. MUST — Retry

```text
Retryable Condition
Max Retry
Backoff
Idempotency
Final Recovery
```

---

# 84. MUST — Security

```text
Trusted Principal
Identity Binding
Authorization
Key Separation
No Raw Token Log
Direct Bypass Control
```

---

# 85. Standard Exception Governance

## FIG-MEC-75. Rule Exception

```text
Standard
  │
  ├─ Comply
  │
  └─ Exception
       ↓
      ADR
       │
       ├─ Reason
       ├─ Scope
       ├─ Owner
       ├─ Risk
       ├─ Control
       └─ Expiry
```

---

# 86. Anti-pattern Map

## FIG-MEC-76. Mechanism Anti-pattern

```text
[Interface]
All REST / Direct DB / Bulk Online

[Message]
UI별 Header / Error / Charset

[Trace]
GUID reset / ServiceId mismatch

[Framework]
업무별 Timeout / JWT parser / Handler→DAO

[Transaction]
Blind Retry / Long TX / Timeout Late Commit

[Security]
Header Identity Trust / Raw Token Log

[Data]
CDC=Strong Sync / ETL in Online

[Operations]
No Recovery / No Evidence / Manual Prod Edit
```

---

# 87. Inventory — Interface

```yaml
interface:
  interfaceId:
  type:
  producer:
  consumer:
  purpose:
  syncType:
  schema:
  security:
  timeout:
  retry:
  recovery:
  traceKey:
  owner:
  evidence:
  status:
```

---

# 88. Inventory — Service

```yaml
service:
  serviceId:
  endpoint:
  handler:
  facade:
  service:
  transactionPolicy:
  timeoutPolicy:
  securityPolicy:
  errorPolicy:
  evidence:
```

---

# 89. Inventory — Timeout

```yaml
timeoutPolicy:
  serviceId:
  client:
  gateway:
  web:
  worker:
  transaction:
  datasource:
  query:
  external:
  evidence:
```

---

# 90. Inventory — Retry

```yaml
retryPolicy:
  interfaceId:
  retryableErrors:
  maxRetry:
  backoff:
  idempotencyKey:
  finalRecovery:
  owner:
```

---

# 91. Inventory — Security

```yaml
security:
  mechanismId:
  authType:
  issuer:
  verifier:
  keyType:
  keyStore:
  identityBinding:
  authorization:
  revocation:
  audit:
```

---

# 92. NFR → MECHANISM

## FIG-MEC-77. NFR Projection

```text
Performance
→ Timeout / Sync-Async / Message Size

Availability
→ Retry / Recovery / Replay / Idempotency

Scalability
→ Event / Async / Stateless

Security
→ Authentication / Authorization / Key / Masking

Observability
→ GUID / ServiceId / Error / Logging
```

---

# 93. AS-IS vs TO-BE Alignment

## FIG-MEC-78. PDMG Reference Alignment

```text
NSIGHT Mechanism Target
       │
       ▼ compare
PDMG Current Mechanism
       │
       ├─ conform
       ├─ partial
       ├─ drift
       └─ gap
```

PDMG에서 비교할 대상:

```text
Message
GUID / ServiceId
Framework
TCF
Transaction
Timeout
Error
Logging
JWT / SSO
```

---

# 94. PDMG AS-IS로만 유지할 항목

```text
5000ms Timeout
Worker 20
Queue 100
Local Port
Current UI token storage
Current Error Handler implementation
Current ImageLog schema
```

Target Standard로 자동 승격하지 않는다.

---

# 95. CONFIRMED / WORKING BASELINE

```text
[CONFIRMED / WORKING BASELINE]

- 목적별 Interface 분리
- Request {hdr_nhnis,dto}
- Success {hdr_nhnis,dto}
- Known Error {hdr_nhnis,result}
- GUID = std_gbl_id
- ServiceId = rms_svc_c 중심 Runtime Key
- Framework / Business 책임 분리
- TCF ON / OFF 구조
- PDMG Timeout 5000 / Worker20 / Queue100 AS-IS
- CDC / ETL / File / Batch 별도 Mechanism
- SSO / JWT Security Mechanism 존재
```

---

# 96. OPEN

```text
[OPEN-MEC-01] HTTP/JSON 공식 적용범위
[OPEN-MEC-02] Header/Path ServiceId mismatch rejection
[OPEN-MEC-03] Spring TX Timeout 실제값
[OPEN-MEC-04] JDBC Query Timeout 실제값
[OPEN-MEC-05] Retryable Error Catalog
[OPEN-MEC-06] Idempotency Standard
[OPEN-MEC-07] Generic Exception Contract
[OPEN-MEC-08] JWT issuer/audience/JWKS 최종 정책
[OPEN-MEC-09] Refresh/Denylist 실제 Enforcement
[OPEN-MEC-10] Event/File/Batch 전사 Catalog
```

---

# 97. GAP Register

## TEXT ARCHITECTURE 보완 — GAP Register

```text
Expected / Required
      │
      ▼
Actual / Evidence
      │
      ▼
Difference
      │
      ▼
[GAP]
      │
      ├─ Owner
      ├─ Action
      ├─ ADR
      └─ Evidence
```


| ID | GAP | 영향 |
|---|---|---|
| GAP-MEC-01 | Interface Catalog 전수 미완료 | Integration |
| GAP-MEC-02 | HTTP/JSON Scope 미확정 | Standard |
| GAP-MEC-03 | ServiceId mismatch enforcement 미확정 | Routing |
| GAP-MEC-04 | Request Resolver / TCF Controller 계약 미확정 | MVC |
| GAP-MEC-05 | Timeout Budget 전수 미완료 | Runtime |
| GAP-MEC-06 | Query Timeout 미확정 | DB |
| GAP-MEC-07 | TX Timeout 미확정 | TX |
| GAP-MEC-08 | Retry / Idempotency 표준 미완료 | Recovery |
| GAP-MEC-09 | Early Error Envelope 미완료 | Error |
| GAP-MEC-10 | Generic Exception 표준 미완료 | Error |
| GAP-MEC-11 | JWT Identity Binding 미완료 | Security |
| GAP-MEC-12 | Denylist Enforcement 미확정 | Security |
| GAP-MEC-13 | Sensitive Log Masking 미완료 | Security |
| GAP-MEC-14 | Event/File/Batch Recovery Catalog 미완료 | Ops |
| GAP-MEC-15 | ImageLog Fail-open Recovery 미완료 | Audit |
| GAP-MEC-16 | Direct WAS Bypass 통제 미확정 | Security |

---

# 98. RISK Register

## TEXT ARCHITECTURE 보완 — RISK Register

```text
Cause
  ↓
Risk Event
  ↓
Business / Data / Security / Operation Impact
  ↓
Severity
  ↓
Mitigation / Control
  ↓
Owner
  ↓
Evidence / Closure
```


| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-MEC-01 | Blind Retry 금융 DML 중복 | Critical |
| RISK-MEC-02 | Timeout Late Commit | Critical |
| RISK-MEC-03 | Header/Path ServiceId 불일치 | High |
| RISK-MEC-04 | Header 사용자정보 신뢰 | Critical |
| RISK-MEC-05 | Raw Token/Secret Log | Critical |
| RISK-MEC-06 | Event/Batch의 Online 자원 점유 | High |
| RISK-MEC-07 | CDC Strong Sync 오판 | High |
| RISK-MEC-08 | File 중복/덮어쓰기 | High |
| RISK-MEC-09 | Unknown Exception 비표준 응답 | High |
| RISK-MEC-10 | Retry without Idempotency | Critical |
| RISK-MEC-11 | ImageLog Fail-open 감사공백 | High |
| RISK-MEC-12 | Framework 기능 업무별 재구현 | High |

---

# 99. ADR 후보

```text
ADR-MEC-01 Interface Type Selection
ADR-MEC-02 HTTP/JSON Scope
ADR-MEC-03 Standard Message Envelope
ADR-MEC-04 ServiceId Consistency
ADR-MEC-05 GUID Propagation
ADR-MEC-06 Charset Standard
ADR-MEC-07 TCF ON/OFF Target Policy
ADR-MEC-08 Transaction Ownership
ADR-MEC-09 Timeout Budget
ADR-MEC-10 Retry / Idempotency
ADR-MEC-11 Error Contract
ADR-MEC-12 Generic Exception Policy
ADR-MEC-13 JWT Identity Binding
ADR-MEC-14 Revocation / Denylist
ADR-MEC-15 Sensitive Logging
ADR-MEC-16 File/Event/Batch Recovery
```

---

# 100. Verification Checklist — Interface

```text
[ ] Interface ID?
[ ] Producer/Consumer?
[ ] 목적에 맞는 Mechanism?
[ ] Schema?
[ ] Security?
[ ] Timeout?
[ ] Retry?
[ ] Recovery?
[ ] Trace Key?
```

---

# 101. Verification Checklist — Message / Trace

```text
[ ] Header/DTO 분리?
[ ] GUID 존재?
[ ] ServiceId 존재?
[ ] Charset 명확?
[ ] Success/Error Envelope?
[ ] Identity Binding?
```

---

# 102. Verification Checklist — Transaction / Timeout

```text
[ ] 실제 TX Owner 검증?
[ ] Query Timeout?
[ ] Worker Deadline?
[ ] Timeout Hierarchy?
[ ] Late Commit 방지?
[ ] cancel(true) 한계 검증?
```

---

# 103. Verification Checklist — Recovery

```text
[ ] Retryable Error?
[ ] Backoff?
[ ] Max Retry?
[ ] Idempotency?
[ ] DLQ / Quarantine?
[ ] Reconciliation?
```

---

# 104. Verification Checklist — Security

```text
[ ] Authentication / Authorization 분리?
[ ] Principal / Header Identity Binding?
[ ] Key / Secret 분리?
[ ] Raw Token Log 금지?
[ ] Direct Bypass 통제?
[ ] Revocation 실제 반영?
```

---

# 105. Verification Checklist — Error / Logging

```text
[ ] Error Taxonomy?
[ ] HTTP + App Code?
[ ] Early Error Standard?
[ ] Generic Exception?
[ ] Stack Trace External 차단?
[ ] GUID/ServiceId Log?
[ ] ImageLog Failure Alert?
```

---

# 106. Completion Gate

## FIG-MEC-79. Mechanism Completion Gate

```text
Interface Selected?
    ↓ YES
Contract Defined?
    ↓ YES
Message Standardized?
    ↓ YES
Trace Defined?
    ↓ YES
Security Defined?
    ↓ YES
Transaction/Timeout Defined?
    ↓ YES
Retry/Recovery Defined?
    ↓ YES
Error/Logging Defined?
    ↓ YES
Runtime Testable?
    ↓ YES
MECHANISM PASS
```

하나라도 NO:

```text
CONDITIONAL / GAP / ADR
```

---

# 107. RUNTIME Handoff

## FIG-MEC-80. MECHANISM → RUNTIME

```text
MECHANISM Output
│
├─ Interface Type
├─ Entry Contract
├─ Message / Header / DTO
├─ GUID / ServiceId
├─ Framework Entry
├─ Transaction Policy
├─ Timeout Policy
├─ Retry / Idempotency
├─ Security
├─ Error Contract
├─ Event / CDC / ETL / File / Batch
└─ Logging / Evidence
       │
       ▼
RUNTIME Input
│
├─ 실제 Sequence
├─ 실제 Thread
├─ 실제 TX Begin/Commit/Rollback
├─ 실제 Timeout 시점
├─ 실제 Failure Branch
├─ 실제 Recovery
├─ 실제 Resource Usage
└─ 실제 Runtime Evidence
```

---

# 108. RUNTIME에서 반드시 답할 질문

```text
1. 실제 요청은 어떤 순서로 흐르는가?
2. 어느 Thread에서 실행되는가?
3. TX는 어디서 Begin/Commit/Rollback 되는가?
4. Timeout은 언제 발생하는가?
5. Timeout 후 Worker/DB는 어떻게 되는가?
6. Error는 어느 계층에서 변환되는가?
7. GUID/MDC/Context는 Thread를 넘어 어떻게 전달되는가?
8. JWT는 실제 어디서 검증되는가?
9. Event/CDC/ETL/File/Batch의 Failure State는 무엇인가?
10. Runtime Evidence는 무엇으로 남는가?
```

---

# 109. MECHANISM 최종 통합 지도

## FIG-MEC-81. Summary

```text
BOUNDARY
  ↓
PURPOSE
  ↓
INTERFACE
API / MCA / Event / CDC / ETL / File / Batch
  ↓
CONTRACT
Header / DTO / Schema / Charset
  ↓
TRACE
GUID / ServiceId / InterfaceId
  ↓
FRAMEWORK
Filter / Context / TCF / Dispatcher / Handler
  ↓
EXECUTION RULE
Transaction / Timeout / Retry / Idempotency
  ↓
SECURITY
SSO / JWT / Auth / AuthZ / Key
  ↓
ERROR / LOGGING
HTTP + Code / Log / ImageLog / Audit
  ↓
RUNTIME
Sequence / Thread / TX / Failure / Recovery / Evidence
```

---

# 보완검토 A. MECHANISM Reference Coverage 보강

## FIG-MEC-SUP-01. Online Interface 표준 경로

```text
통합업무 ── MCA ─────────► 정보계
비대면   ── MCI ─────────► 정보계
정보단말 ── Direct ──────► 정보계
Package  ── Direct 예외 ─► 정보계 솔루션

정보계 ── Cruz APIM ─────► 대내
대내   ── Cruz APIM ─────► 정보계

정보계 ── API G/W ───────► 대외
대외   ── API G/W ───────► 정보계

타 법인 ── GSE ──────────► 정보계
```

---

## FIG-MEC-SUP-02. GSE 법인간 경계

```text
Other Corporation
     ↓
GSE
     │
     ├─ Corporation Code
     ├─ Authorization
     ├─ Message Version
     └─ Error Ownership
     ↓
NSIGHT
```

GSE를 일반 내부 API와 동일 취급하지 않는다.

---

## FIG-MEC-SUP-03. File Interface 표준

```text
정보단말 / Package UI
       ↓
      FOS
       ↓
정보계 / 정보계 솔루션

정보계 ↔ FOS ↔ 대내시스템

정보계 ↔ FOS + 대외MCA ↔ 대외기관
```

---

## FIG-MEC-SUP-04. Data Interface

```text
Core DB ── CDC ─────────► RDW
Core BCV ─ ETL ─────────► RDW
RDW ───── ETL ──────────► ADW
DW ────── ETL ──────────► Other System
Other System ─ ETL ─────► DW
```

---

## FIG-MEC-SUP-05. Cross-Application Call Rule

```text
Same Function
Controller → Service → DAO

Other Function
Caller → Relative Service Contract

Other Application
Caller → Public Controller / API

Other Group / External
Caller → Standard Interface
```

---

## FIG-MEC-SUP-06. xDataSet Compatibility Structure

```text
xDataSet
│
├─ header
│   ├─ version
│   └─ screen number
│
└─ datasets[]
```

상세 스키마는 단말 표준을 따른다.

---

## FIG-MEC-SUP-07. RD Reporting Entry

```text
Reporting Client
      ↓
/rd/{serviceId}
      ↓
RD Reporting Runtime
      │
      ├─ Input  = Map
      └─ Output = String
```

일반 JSON DTO 거래와 분리한다.

---

## FIG-MEC-SUP-08. JobRepository의 위치

```text
Scheduler / Batch
      ↓
Job / Step Execution
      ↓
JobRepository
      │
      ├─ Instance
      ├─ Execution
      ├─ Step
      └─ Restart State
```

```text
JobRepository
≠
Business Ledger DB
```

---

## FIG-MEC-SUP-09. 업무 Solution Architecture

```text
Business Solution
│
├─ SELF-BI
├─ OLAP
├─ EBM
└─ Data Flow
      │
      ▼
Standard Adapter / Interface
      │
      ▼
NSIGHT Service / RDW / ADW
```

Package/Solution도 Architecture Boundary와 Trace를 따라야 한다.

---

## FIG-MEC-SUP-10. OLAP

```text
RDW / ADW
   ↓
OLAP / Analytical Engine
   ↓
BI Consumer
```

Online Business Runtime과 자원격리한다.

---

## FIG-MEC-SUP-11. Package UI Direct Exception

```text
Package UI
  ↓
Direct
  ↓
[EXCEPTION]
  │
  ├─ Reason
  ├─ Owner
  ├─ Contract
  ├─ Security
  └─ Expiry / Review
```

Package 예외를 일반 Direct API로 확대하지 않는다.

---

# 110. Definition of Done

## TEXT ARCHITECTURE 보완 — Definition of Done

```text
Architecture Inputs
      ↓
Structure / Contract / Runtime Check
      ↓
Evidence Check
      ↓
GAP / CONFLICT / UNKNOWN Review
      ↓
PASS Condition
      │
      ├─ satisfied → PASS
      └─ pending   → CONDITIONAL PASS
```


## Interface
- [x] Online/Event/CDC/ETL/File/Batch 목적별 분리
- [x] Standard Entry 정의
- [x] Direct도 표준 유지
- [x] Gateway/MCA/MCI 책임 구분

## Message / Trace
- [x] Header/DTO/Result 구조
- [x] Success/Error Envelope
- [x] GUID/ServiceId 구분
- [x] Header Identity Trust Boundary
- [x] Charset 책임

## Framework
- [x] Framework / Business 책임 분리
- [x] TCF ON/OFF
- [x] Current 선후처리와 8-Step 모델 구분

## Transaction / Timeout / Retry
- [x] Worker TX Boundary
- [x] Timeout Hierarchy
- [x] Current 5000/20/100 Snapshot
- [x] cancel(true) 한계
- [x] Late Commit 방지
- [x] Retry/Idempotency

## Data Movement
- [x] Event
- [x] CDC
- [x] ETL
- [x] File
- [x] Batch

## Security
- [x] SSO/JWT
- [x] Key/Secret 분리
- [x] Identity Binding GAP
- [x] Auth/AuthZ 분리
- [x] Direct WAS Bypass

## Error / Logging
- [x] Error Taxonomy
- [x] HTTP/App Code
- [x] Early/Generic Error GAP
- [x] MDC/ImageLog/SQL/Audit 분리
- [x] Sensitive Logging 금지

## Governance
- [x] Inventory Template
- [x] GAP/RISK/ADR
- [x] Completion Gate
- [x] RUNTIME Handoff

**MECHANISM 장 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. 전체 Interface Catalog 승인
2. HTTP/JSON Scope 확정
3. Header/Path ServiceId mismatch 정책 확정
4. Query/TX Timeout 실제값 확보
5. Retry/Idempotency 표준 승인
6. Generic/Early Error 표준 승인
7. JWT Identity Binding / Revocation 확정
8. Event/File/Batch Recovery Contract 승인
9. Sensitive Logging/Masking 표준 승인
10. Runtime Scenario별 실제 Evidence 확보

---

# 111. 다음 장

다음은 **06. RUNTIME — 실제 거래 Sequence / Thread / Transaction / Timeout / Failure / Recovery / Evidence**다.

```text
MECHANISM
"어떤 규칙으로 동작해야 하는가?"

        ↓

RUNTIME
"실제로 어느 순서 / 어느 Thread / 어느 TX에서
어떻게 실행되고 실패하며 복구되는가?"
```

---


====================================================================================================

# CHAPTER 06

====================================================================================================

# NSIGHT / PDMG 아키텍처 정의서
# 06. RUNTIME — Sequence / Thread / Transaction / Timeout / Failure / Recovery / Evidence

> 보완개정: **v2 — TEXT Architecture Figure Coverage / Reference Coverage / Evidence Consistency 재점검**
## TEXT ARCHITECTURE 보완 — 장 전체 위치

```text
Request/Event/Data/File/Job
   ↓
Sequence
   ↓
Thread/TX
   ↓
Outcome
   ↓
Recovery
   ↓
Evidence
```

## Visual-First / Top-down / Drill-down 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-VISUAL-RUNTIME-06`  
> Architecture Level: **RUNTIME / L5**  
> 문서 상태: **Draft / Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_05_MECHANISM_VISUAL_FIRST_TOPDOWN_상세본.md`  
> 후속: **Evidence / Traceability / Closed Loop / Baseline Release**

---

# 0. 이 문서를 읽는 방법

RUNTIME은 “무슨 기술을 쓰는가?”를 설명하는 장이 아니다.

이 장은 다음을 **시간순으로 증명**한다.

```text
Request / Event / Change / File / Schedule
        │
        ▼
Runtime Type
        │
        ▼
Entry
        │
        ▼
Framework / Business / Data
        │
        ▼
Thread / Transaction / Resource
        │
        ▼
Success / Error / Timeout / Overload
        │
        ▼
Recovery / HA / DR
        │
        ▼
Metric / Log / Trace / Evidence
```

MECHANISM이:

```text
"어떻게 동작해야 하는가?"
```

를 정의했다면,

RUNTIME은:

```text
"실제로 어느 순서 / 어느 Thread / 어느 TX에서
어떻게 실행되고 실패하며 복구되는가?"
```

를 증명한다.

---

# 1. VISUAL ROUTE — RUNTIME 전체를 한 장으로 보기

## FIG-RT-01. Runtime Architecture Journey

```text
╔══════════════════════════════════════════════════════════════════════════════╗
║                            RUNTIME ARCHITECTURE                              ║
╚══════════════════════════════════════════════════════════════════════════════╝

 ① RUNTIME TYPE
    #1 ~ #12
        │
        ▼
 ② ENTRY / ACTOR
    Request / Event / Change / File / Schedule
        │
        ▼
 ③ EXECUTION SEQUENCE
    Entry → Framework → Business → Data/External
        │
        ▼
 ④ EXECUTION RESOURCE
    Request Thread / Worker / TX / Connection / DB
        │
        ▼
 ⑤ OUTCOME
    Success / Business Error / Timeout / Overload / System Error
        │
        ▼
 ⑥ RECOVERY
    Retry / Replay / Restart / Failover / Manual Recovery
        │
        ▼
 ⑦ OBSERVABILITY
    GUID / ServiceId / Metric / Log / Trace / Audit
        │
        ▼
 ⑧ AVAILABILITY
    HA / DR / Backup / Restore
        │
        ▼
 ⑨ RUNTIME EVIDENCE
    Scenario / Result / Hash / Deployment / Trace
        │
        ▼
 ⑩ CLOSED LOOP
    Drift → GAP → ADR → New Baseline
```

---

# 2. MECHANISM → RUNTIME Handoff

## FIG-RT-02. Contract to Execution

```text
MECHANISM
│
├─ Interface Type
├─ Message / Header
├─ GUID / ServiceId
├─ Transaction Policy
├─ Timeout Policy
├─ Retry / Idempotency
├─ Error Contract
├─ Security
└─ Logging
       │
       ▼
RUNTIME
│
├─ Actual Sequence
├─ Actual Thread
├─ Actual TX
├─ Actual Timeout
├─ Actual Error Branch
├─ Actual Recovery
└─ Actual Evidence
```

---

# 3. RUNTIME 핵심 결론

## FIG-RT-03. Runtime Truth

```text
Design says
   │
   ▼
Runtime executes
   │
   ▼
Evidence proves
```

### 반드시 분리할 생명주기

```text
HTTP Request Lifecycle
≠
Worker Thread Lifecycle
≠
DB Transaction Lifecycle
≠
JDBC Statement Lifecycle
```

따라서:

```text
HTTP Timeout
≠ Worker 종료
≠ JDBC Statement 취소
≠ DB Transaction Rollback
```

---

# 4. 업무 처리 Runtime Type — 12개 유형

## FIG-RT-04. Runtime Type Map

```text
CHANNEL
├─ #1 정보계 단말 거래
├─ #2 통합업무 거래
└─ #3 미니 싱글뷰

INTEGRATION
├─ #4 대내 연계
└─ #5 대외 연계

MARKETING EVENT
├─ #6 반응형 정보 수집
└─ #7 고객 오퍼링

DATA
├─ #8 CDC
├─ #9 ETL
└─ #10 분석 / 의사결정 지원

FILE
└─ #11 File

BATCH
└─ #12 Batch
```

---

# 5. Runtime Type 정의표

## TEXT ARCHITECTURE 보완 — Runtime Type 정의표

```text
Input
Request / Event / Change / File / Schedule
      ↓
Runtime Type #1~#12
      ↓
Execution Model
Sync / Async / Batch / Stream
      ↓
Failure Mode
      ↓
Recovery
      ↓
Runtime Evidence
```


| # | Runtime Type | 주 입력 | 실행 특성 | 대표 회복 |
|---:|---|---|---|---|
| 1 | 정보계 단말 거래 | Request | Sync Online | Timeout/Error |
| 2 | 통합업무 거래 | Request | Sync Enterprise | Timeout/Fail |
| 3 | 미니 싱글뷰 | Request | Sync Aggregation | Partial/Timeout |
| 4 | 대내 연계 | Request/Message | Sync/Async | Retry/Recovery |
| 5 | 대외 연계 | Request/Message | Controlled External | Retry/Manual |
| 6 | 반응형 정보 수집 | Event | Async | Replay/DLQ |
| 7 | 고객 오퍼링 | Event/Decision | Async/Low-latency | Retry/Compensate |
| 8 | CDC | DB Change | Streaming | Restart/Reconcile |
| 9 | ETL | Data Set | Batch | Restart/Reconcile |
| 10 | 분석/의사결정 | Query | Analytical | Resource Isolation |
| 11 | File | File | File Transfer | Retry/Quarantine |
| 12 | Batch | Schedule | Job/Step | Restart/Checkpoint |

---

# 6. 주 유형 / 보조 유형

## FIG-RT-05. Primary / Secondary Runtime

```text
Business Flow
   │
   ├─ Primary Runtime Type
   │
   └─ Secondary Runtime Type
```

예:

```text
고객행동 오퍼링
Primary   = #7
Secondary = #6 / #4
```

### 원칙

신규 Flow는 최소 다음을 가져야 한다.

```text
Primary Runtime Type
Secondary Type [if any]
Owner
SLO
Failure Mode
Recovery
Evidence
```

---

# 7. Runtime Inventory

최소 필드:

```yaml
runtime:
  runtimeId:
  primaryType:
  secondaryTypes:
  businessFlow:
  entry:
  actors:
  sequence:
  threads:
  transaction:
  timeout:
  resourcePools:
  errorCases:
  recovery:
  monitoring:
  slo:
  owner:
  evidence:
  status:
```

---

# 8. 유형 없는 신설 금지

## FIG-RT-06. New Runtime Gate

```text
신규 Flow
  │
  ▼
Runtime Type 지정?
  ├─ NO → 개통 금지 / GAP
  └─ YES
      ↓
    Sequence?
      ↓
    Failure?
      ↓
    Recovery?
      ↓
    Evidence?
      ↓
    Release Candidate
```

---

# 9. #1 정보계 단말 거래

## FIG-RT-07. Online Information Runtime

```text
Information Terminal / Web
      │
      ▼
Channel / Entry
      │
      ▼
WEB / WAS
      │
      ▼
Standard Online Runtime
      │
      ▼
Business
      │
      ▼
RDW / DB
      │
      ▼
Response
```

---

# 10. #1 정상 Sequence

## FIG-RT-08. Normal Online

```text
Client
  │ Request
  ▼
Entry
  │
  ▼
Filter / Security
  │
  ▼
Controller
  │
  ▼
Framework
  │
  ▼
Business
  │
  ▼
DB
  │ Result
  ▼
Response
```

Evidence:

```text
GUID
ServiceId
Start/End
HTTP Status
Business Result
SQL
Elapsed
```

---

# 11. #1 지연 Sequence

## FIG-RT-09. Online Slow

```text
Client
  ↓
Entry
  ↓
Business
  ↓
Slow DB / External
  ↓
Request waits
  ↓
Timeout threshold
  ↓
Timeout response
```

운영질문:

```text
어디서 지연?
Request Thread?
Worker?
Hikari?
SQL?
External?
```

---

# 12. #2 통합업무 거래

## FIG-RT-10. Enterprise Transaction

```text
Integrated Work UI
      │
      ▼
Enterprise Entry / MCA-MCI
      │
      ▼
Information Application
      │
      ▼
Core / Related System
      │
      ▼
Response
```

---

# 13. #2 Failure

## FIG-RT-11. Integrated Transaction Failure

```text
Information App
      │
      ▼
Internal Integration
      │
      ▼
Target
      │
      X Timeout / Reject
      │
      ▼
Error Mapping
      │
      ▼
Caller
```

### 주의

Remote Call을 DB Transaction 안에 오래 포함하면 위험하다.

---

# 14. #3 미니 싱글뷰

## FIG-RT-12. Aggregation Runtime

```text
User
 ↓
Mini Single View
 ↓
Multiple Data Sources / Services
 ├─ Customer
 ├─ Product
 ├─ Account
 └─ Summary
 ↓
Aggregation
 ↓
Response
```

---

# 15. #3 Runtime 주의

```text
Fan-out 증가
   ↓
Slowest Dependency가 전체 응답 지배
```

필요:

```text
Timeout Budget
Partial Result Policy
Cache Policy
Concurrency Control
```

---

# 16. #4 대내 연계

## FIG-RT-13. Internal Integration

```text
NSIGHT
  │
  ▼
Internal Integration
  │
  ▼
Core / Related
  │
  ▼
Response / Event / File
```

---

# 17. #4 금지

```text
Application → Related DB Direct DML    X
Long TX across Remote Systems          X
Blind Retry                            X
Trace Key Loss                         X
```

---

# 18. #5 대외 연계

## FIG-RT-14. External Runtime

```text
NSIGHT
  │
  ▼
External Gateway
  │
  ▼
External Institution
  │
  ▼
Response / Ack
```

필수:

```text
Timeout
Retryability
Institution Code
Certificate
Audit
Recovery
```

---

# 19. #5 Failure 분류

```text
Network
Certificate
Protocol
Business Reject
Timeout
Duplicate
External Maintenance
```

각 유형별 Recovery가 달라야 한다.

---

# 20. #6 반응형 정보 수집

## FIG-RT-15. Event Collection Runtime

```text
Customer Action
   ↓
Collector
   ↓
Event Broker
   ↓
Consumer
   ↓
Behavior Data / Processing
```

---

# 21. #6 Runtime 지표

```text
Produce Rate
Broker Lag
Consumer Lag
Error Rate
Retry
DLQ
Replay Time
```

---

# 22. #6 금지

```text
Event Producer가 Consumer 응답을 기다림   X
Event를 Online Request Thread에 종속      X
Replay 없는 이벤트                        X
```

---

# 23. #7 고객 오퍼링

## FIG-RT-16. Event-to-Offer Runtime

```text
Behavior Event
    ↓
Processing
    ↓
Decision / EBM
    ↓
Offer
    ↓
Contact / Message
```

---

# 24. #6 vs #7

```text
#6
행동을 수집

        ↓

#7
행동을 해석하여 반응
```

---

# 25. #7 Failure

## FIG-RT-17. Offer Failure

```text
Event
  ↓
Decision
  ↓
Offer
  X
Delivery Failure
  ↓
Retry / Alternate / Manual Recovery
```

Decision과 Delivery 결과를 분리해서 기록한다.

---

# 26. #8 CDC

## FIG-RT-18. CDC Runtime

```text
Source DB
  │ change
  ▼
Capture
  ▼
Relay / Transport
  ▼
Apply
  ▼
RDW
```

---

# 27. #8 관측지점

```text
Source LSN/SCN
Capture Time
Transport Time
Apply Time
Lag
Error
Restart Position
```

---

# 28. CDC SLA Conflict

## FIG-RT-19. 30s vs 3s

```text
Baseline A
CDC <= 30 sec
      │
      │ [CONFLICT]
      ▼
Baseline B
CDC <= 3 sec
```

### RUNTIME에서는 반드시 측정구간을 명시한다

```text
Source Commit
   ↓
Capture
   ↓
Transport
   ↓
Apply
   ↓
Consumer Visibility
```

---

# 29. #8 Recovery

## FIG-RT-20. CDC Restart

```text
CDC Failure
  ↓
Last Applied Position
  ↓
Restart
  ↓
Catch-up
  ↓
Reconcile
```

---

# 30. #8 금지

```text
Lag Metric 없음                    X
Restart Position 없음              X
Consumer Visibility 검증 없음       X
```

---

# 31. #9 ETL

## FIG-RT-21. ETL Runtime

```text
RDW / Source
   ↓
Extract
   ↓
Transform
   ↓
Validate
   ↓
Load
   ↓
ADW
   ↓
Reconcile
```

---

# 32. #9 필수 Evidence

```text
JobId
ExecutionId
Business Date
Start/End
Input Count
Output Count
Reject Count
Error
Restart Point
```

---

# 33. #9 Failure

```text
Extract Failure
Transform Failure
Data Quality Failure
Load Failure
Target Constraint
Resource Exhaustion
```

---

# 34. #9 Restart

## FIG-RT-22. ETL Restart

```text
FAILED
  ↓
Checkpoint / Last Step
  ↓
Restart
  ↓
Load
  ↓
Count Reconcile
```

---

# 35. #10 분석 / 의사결정 지원

## FIG-RT-23. Analytical Runtime

```text
BI / Analyst
   ↓
Query / OLAP / Self BI
   ↓
RDW / ADW
   ↓
Analysis Result
```

---

# 36. #10 Resource Isolation

## FIG-RT-24. Analytical Isolation

```text
Heavy BI Query
    │
    X
    └────► Online Service degradation
```

필요:

```text
Workload Separation
Resource Group
Concurrency Control
Query Governance
```

---

# 37. #11 File Runtime

## FIG-RT-25. File Runtime

```text
Producer
  ↓
READY
  ↓
TRANSFER
  ↓
RECEIVED
  ↓
VALIDATED
  ↓
PROCESSED
  ↓
ARCHIVED
```

---

# 38. #11 필수 항목

```text
InterfaceId
Filename
Business Date
Size
Hash
Record Count
Encryption
TransferId
Status
Recovery
```

---

# 39. #11 Failure

```text
Transfer Interrupted
Checksum Mismatch
Schema Error
Duplicate File
Partial File
Processing Error
```

---

# 40. #11 Recovery

## FIG-RT-26. File Recovery

```text
Failure
  ↓
Retry
  ↓
Quarantine
  ↓
Operator Review
  ↓
Reprocess
  ↓
Reconcile
```

---

# 41. #12 Batch Runtime

## FIG-RT-27. Batch Runtime

```text
Scheduler
   ↓
Job
   ↓
Step
   ↓
Reader
   ↓
Processor
   ↓
Writer
   ↓
Result
```

---

# 42. #12 Runtime Key

```text
JobId
JobInstance
ExecutionId
StepExecutionId
Business Date
Parameters
```

---

# 43. #12 완료 조건

```text
Job Status = COMPLETED
+
Data Reconciliation PASS
+
Downstream Dependency PASS
```

---

# 44. #12 금지

```text
Job Return Code만 성공                  X
Count Reconcile 없음                   X
Restart Policy 없음                    X
Online Pool 공유                       X
```

---

# 45. Runtime Type 전체 관계

## FIG-RT-28. Runtime Interaction

```text
#1/#2/#3 Online
      │
      ├────────► #4/#5 Integration
      │
      └────────► RDW

#6 Event Collection
      ↓
#7 Offering

#8 CDC
      ↓
RDW
      ↓
#9 ETL
      ↓
ADW
      ↓
#10 Analysis

#11 File
#12 Batch
→ Supporting Runtime
```

---

# 46. PDMG Online Runtime — AS-IS Reference

## FIG-RT-29. Full PDMG Online Flow

```text
HTTP Request
   │
   ▼
DefaultFilter
   │
   ▼
SecurityFilterChain
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
   │
   ▼
Worker TransactionTemplate BEGIN
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
DAO / Mapper / SQL
   │
   ▼
Deadline Check
   │
   ├─ COMMIT
   └─ ROLLBACK
   │
   ▼
ResponseBodyAdvice
   │
   ▼
Interceptor.afterCompletion
   │
   ▼
Filter finally / Context Clear
   │
   ▼
HTTP Response
```

---

# 47. PDMG Current Snapshot

```text
[AS-IS SNAPSHOT]

tcf.enabled       = true
timeout.enabled   = true
timeout           = 5000ms
worker pool       = 20
queue capacity    = 100
legacy-web        = true
filter            = true
```

---

# 48. HTTP Request Lifecycle

## FIG-RT-30. Request Thread Life

```text
Request accepted
   ↓
Filter
   ↓
MVC
   ↓
Controller
   ↓
TcfFacade
   ↓
Future.get()
   ↓
Response / Timeout
```

---

# 49. Worker Lifecycle

## FIG-RT-31. Worker Thread Life

```text
Task submit
  ↓
Queue
  ↓
Worker selected
  ↓
Context/MDC install
  ↓
Transaction BEGIN
  ↓
Business
  ↓
Deadline check
  ↓
Commit/Rollback
  ↓
Context/MDC clear
```

---

# 50. Request vs Worker

## FIG-RT-32. Two Timelines

```text
Request Thread               Worker Thread
      │                            │
      ├─ submit ─────────────────► │
      │                            ├─ Context install
      │                            ├─ TX begin
      │ wait                       ├─ Business
      │                            ├─ SQL
      │                            ├─ Commit/Rollback
      │ ◄──────────────────────────┤
      │ response                   │
```

Timeout이면 두 생명주기가 분리된다.

---

# 51. ThreadLocal 전파

## FIG-RT-33. Context Propagation

```text
Request Thread
ServiceContext / MDC
      │ capture
      ▼
Worker Thread
ServiceContext / MDC
      │
      ▼
Business / Log
      │
      ▼
clear
```

자동 전파라고 가정하지 않는다.

---

# 52. Mutable Context Risk

## FIG-RT-34. Shared Reference Risk

```text
Request Thread
    │
    └──── same mutable ServiceContext ────┐
                                          ▼
                                    Worker Thread
```

Risk:

```text
Race
Lifecycle overlap
Servlet request/response reference sharing
```

TO-BE 후보:

```text
Immutable Snapshot
```

---

# 53. Transaction Boundary

## FIG-RT-35. Actual TX Boundary

```text
Worker
  ↓
TransactionTemplate BEGIN
  ↓
Dispatcher
  ↓
Handler
  ↓
Facade REQUIRED
  ↓
Service
  ↓
DAO / Mapper
  ↓
Deadline Check
  ↓
COMMIT / ROLLBACK
```

---

# 54. Handler가 TX 안이라는 의미

```text
TransactionTemplate
  {
    Dispatcher
    Handler
    Facade
    Service
    DAO
    SQL
  }
```

즉 Handler 선택/실행도 Outer Transaction 안에 있다.

---

# 55. Facade @Transactional

## FIG-RT-36. Join Outer TX

```text
Outer TransactionTemplate
      │
      ▼
Facade @Transactional(REQUIRED)
      │
      └─ joins existing transaction
```

Annotation이 물리 TX 시작점이라는 뜻은 아니다.

---

# 56. Transaction Manager 정합

```text
DataSource
  ↓
TransactionManager
  ↓
SqlSessionFactory
  ↓
SqlSessionTemplate
```

현재 RDW 축은 동일 DataSource/TransactionManager 정합을 확인해야 한다.

---

# 57. readOnly 주의

## FIG-RT-37. Outer TX vs readOnly

```text
Outer TX
readOnly?
   │
   ▼
Facade readOnly=true
```

Inner REQUIRED가 Outer에 참여하는 경우
Outer 속성이 실제 Query TX에 어떻게 적용되는지 검증 필요.

```text
[GAP]
```

---

# 58. 정상 Commit Sequence

## FIG-RT-38. Normal Commit

```text
Request
 ↓
Worker
 ↓
TX BEGIN
 ↓
Business
 ↓
SQL
 ↓
Deadline OK
 ↓
COMMIT
 ↓
Result
 ↓
HTTP 200
```

---

# 59. Business Error Rollback

## FIG-RT-39. Business Exception

```text
TX BEGIN
 ↓
Business
 ↓
BizException
 ↓
ROLLBACK
 ↓
Error Mapping
 ↓
HTTP 500 + Business Code [current]
```

---

# 60. Timeout 4개 시점

## FIG-RT-40. Timeout Types

```text
① Queue Wait
② Worker Execution
③ DB Connection Wait
④ SQL / External Wait
```

하나의 "5초"로 모든 지연을 설명하면 안 된다.

---

# 61. Request Timeout

## FIG-RT-41. Timeout Response

```text
Request
 ↓
Future.get(5000ms)
 ↓
Timeout
 ↓
cancel(true)
 ↓
HTTP 504
```

---

# 62. JDBC 취소 한계

```text
cancel(true)
   ↓
Thread Interrupt
   ↓
JDBC Driver Behavior?
   ↓
DB Statement Cancel?
```

반드시 Integration Test로 검증한다.

---

# 63. Deadline Late Commit Prevention

## FIG-RT-42. Late Commit Guard

```text
Request timed out
      │
      ▼
Worker SQL returns late
      │
      ▼
Deadline exceeded?
   ├─ YES → rollback
   └─ NO  → commit
```

---

# 64. Overload Sequence

## FIG-RT-43. Queue Full

```text
Request
 ↓
Worker Pool busy
 ↓
Queue full
 ↓
Reject
 ↓
OnlineOverloadException
 ↓
HTTP 503
```

---

# 65. Timeout vs Overload

```text
Timeout
= accepted but deadline exceeded

Overload
= execution capacity unavailable / queue full
```

운영에서 반드시 분리한다.

---

# 66. Resource Chain

## FIG-RT-44. Runtime Resource Chain

```text
Tomcat Request Thread
       ↓
PDMG Worker Pool
       ↓
Worker Queue
       ↓
Hikari Connection
       ↓
DB Session
       ↓
SQL / I/O
```

---

# 67. DB Slow → Runtime Failure

## FIG-RT-45. Cascading Slowdown

```text
Slow SQL
  ↓
Connection Hold
  ↓
Hikari Pending
  ↓
Worker Busy
  ↓
Queue Growth
  ↓
Request Timeout / Overload
```

---

# 68. TCF ON

```text
TCF ON
→ Common Controller
→ TcfFacade
→ Timeout Executor
→ Dispatcher
→ Handler
```

---

# 69. TCF OFF

```text
TCF OFF
→ Business Controller
→ Facade / Service
```

OnlineTimeoutExecutor는 TcfFacade 경로를 타지 않으므로
TCF OFF에서는 적용되지 않을 수 있다.

---

# 70. TCF ON/OFF Runtime GAP

## FIG-RT-46. Policy Difference

```text
TCF ON
Transaction / Timeout / Error / Routing
      │
      X consistency?
      │
TCF OFF
Business Controller / Method-specific TX
```

```text
[GAP]
Target Runtime Policy 통일 필요
```

---

# 71. STF / ETF

## FIG-RT-47. Exists vs Executed

```text
Class Exists
   │
Bean Exists
   │
Runtime Called?
```

Current `TcfFacade`에 STF/ETF 호출이 없으면:

```text
[AS-IS Executed]
로 그리지 않는다.
```

---

# 72. ServiceId Runtime

## FIG-RT-48. Routing

```text
ServiceId
  ↓
Registry
  ↓
Handler
  ↓
Business
```

---

# 73. ServiceId Routing Failure

```text
ServiceId 없음
   ↓
Handler Not Found
   ↓
ServiceHandlerNotFound
   ↓
Error Response
```

---

# 74. Response Assembly

## FIG-RT-49. Success Path

```text
Business Result
  ↓
ResponseBodyAdvice
  ↓
hdr_nhnis + dto
  ↓
HTTP Response
```

---

# 75. Error Assembly

## FIG-RT-50. Known Error Path

```text
Exception
 ↓
GlobalExceptionHandler
 ↓
NH_NIS_ERR_DTO
 ↓
ResponseBodyAdvice
 ↓
hdr_nhnis + result
```

---

# 76. Filter Early Error

## FIG-RT-51. Early Exit

```text
DefaultFilter
  │
  ├─ 400/401
  └─ sendError
      ↓
MVC / ResponseBodyAdvice 우회 가능
```

---

# 77. Runtime Error Taxonomy

```text
Validation
AuthN
AuthZ
Business
Routing
Timeout
Overload
DB
External
Unknown
```

---

# 78. Error → Recovery Matrix

## TEXT ARCHITECTURE 보완 — Error → Recovery Matrix

```text
Error Class
   ↓
Rollback Needed?
   ↓
Retry Allowed?
   ↓
Caller Result
   ↓
Recovery Action
   ↓
Evidence / Incident
```


| Error | Retry | Rollback | Caller Result | Recovery |
|---|---|---|---|---|
| Validation | N | N/A | 4xx/Business | 입력수정 |
| AuthN/AuthZ | N | N/A | 401/403 | 재인증/권한 |
| Business Reject | N | Y/Policy | Business Error | 업무조치 |
| Timeout | Caution | Y target | 504 | 상태확인 |
| Overload | Limited | N/A | 503 | Backoff |
| DB Error | Case | Y | 5xx | DB Recovery |
| External Error | Case | Y/Comp | 5xx | Retry/Manual |
| Unknown | N | Y | 500 | Investigation |

---

# 79. Security Runtime

## FIG-RT-52. Request Security Runtime

```text
Request
 ↓
Security Filter
 ↓
JWT Verify
 ↓
Principal
 ↓
Business Authorization
 ↓
Service
```

---

# 80. JWT Runtime

## FIG-RT-53. Token Runtime

```text
Login
  ↓
Token Issue
  ↓
Client Storage
  ↓
Bearer Request
  ↓
Verify
  ↓
Principal
  ↓
Authorization
```

---

# 81. JWT Subject vs Header User

## FIG-RT-54. Identity Runtime GAP

```text
JWT ssoId
   │
   │ ?
   ▼
Header optr_eno
   │
   ▼
ServiceContext.userContext
```

이 Binding을 Runtime Test로 증명해야 한다.

---

# 82. Session / JWT HA

```text
JWT Stateless Verification
+
Refresh / Revoke State
+
Optional HttpSession
```

따라서 Stateless 한 단어로 HA 설계를 끝내지 않는다.

---

# 83. Runtime Observability Big Picture

## FIG-RT-55. E2E Observability

```text
Client
 ↓
GSLB / L4
 ↓
WEB
 ↓
Tomcat JVM
 ↓
Filter / Security
 ↓
TCF
 ↓
ServiceId
 ↓
Worker
 ↓
TX
 ↓
DAO / SQL
 ↓
DB / External
 ↓
Response
```

각 구간에:

```text
Metric
Log
Trace
Health
Evidence
```

가 있어야 한다.

---

# 84. 거래 관측 Key

```text
GUID
ServiceId
UserId
Host
JVM
Thread
Worker
SqlId
InterfaceId
ErrorCode
```

---

# 85. 좋은 운영 질문

```text
어느 ServiceId가 느린가?
어느 JVM인가?
Request Thread가 막혔는가?
Worker Queue가 증가했는가?
Hikari Pending인가?
어느 SqlId인가?
DB Wait인가?
External System인가?
```

---

# 86. Tomcat / JVM Monitoring

## FIG-RT-56. JVM Metrics

```text
Request Threads
Busy Threads
Queue
Heap
Metaspace
GC
CPU
Thread Count
Restart
```

---

# 87. PDMG Worker Monitoring

```text
Pool Size
Active Worker
Queue Depth
Rejected
Task Duration
Timeout Count
```

---

# 88. Hikari Monitoring

```text
Active
Idle
Pending
Max
Acquire Time
Timeout
```

---

# 89. DB Monitoring

```text
Session
Active SQL
Wait Event
CPU
I/O
Lock
Long SQL
Query Timeout
```

---

# 90. Event Monitoring

```text
Produce Rate
Consume Rate
Lag
Partition
Error
Retry
DLQ
```

---

# 91. CDC Monitoring

```text
Capture Lag
Transport Lag
Apply Lag
Restart
Error
```

---

# 92. ETL Monitoring

```text
Job Duration
Rows In
Rows Out
Reject
Step Failure
Restart
```

---

# 93. File Monitoring

```text
Transfer Status
File Size
Hash
Record Count
Duplicate
Quarantine
```

---

# 94. Batch Monitoring

```text
Job Status
Step Status
Delay
Retry
Restart
Business Date
```

---

# 95. Security Monitoring

```text
Auth Failure
JWT Verify Failure
Unknown kid
Revoked Token
Authorization Denied
Suspicious Direct Access
```

---

# 96. Alert → Diagnosis → Recovery

## FIG-RT-57. Operations Loop

```text
Metric / Log
   ↓
Alert
   ↓
Diagnosis
   ↓
Runbook
   ↓
Recovery
   ↓
Evidence
   ↓
Postmortem / GAP
```

---

# 97. Alert without Runbook

```text
Alert
  ↓
Operator sees red
  ↓
What now?
```

이 상태는 운영 가능한 Observability가 아니다.

---

# 98. HA Strategy

## FIG-RT-58. Application HA

```text
L4
├─ Node A
└─ Node B
```

Failure:

```text
Node A X
  ↓
Health Check
  ↓
Remove Member
  ↓
Node B serves
```

---

# 99. AP VM Failure

## FIG-RT-59. Scenario #1

```text
AP VM #1 X
   ↓
L4 Detect
   ↓
Remaining AP
   ↓
Session / In-flight handling
   ↓
Residual Capacity
```

Evidence:

```text
Detection Time
Failover Time
p95
Error Count
Session Behavior
```

---

# 100. AP Group Failure

## FIG-RT-60. Scenario #2

```text
AP Group A X
   ↓
Alternate Group?
   ↓
Route
   ↓
Capacity
```

---

# 101. RDW Failure

## FIG-RT-61. Scenario #3

```text
RDW Failure
   ↓
Online Data Service Impact
   ↓
Fallback / Failover?
   ↓
Recovery
```

---

# 102. ADW Failure

## FIG-RT-62. Scenario #4

```text
ADW Failure
   ↓
BI / Analytical Impact
   ↓
Online unaffected?
   ↓
Recovery
```

FAST/DEEP 분리의 검증 시나리오다.

---

# 103. Kafka/Event Failure

## FIG-RT-63. Scenario #5

```text
Broker / Consumer Failure
   ↓
Lag
   ↓
Buffer
   ↓
Restart
   ↓
Replay
   ↓
Catch-up
```

---

# 104. CDC Relay Failure

## FIG-RT-64. Scenario #6

```text
CDC Relay X
   ↓
Lag increases
   ↓
Restart
   ↓
Catch-up
   ↓
Reconcile
```

---

# 105. Integration Failure

## FIG-RT-65. Scenario #7

```text
Gateway / External Path X
   ↓
Request Error / Queue
   ↓
Retry Policy
   ↓
Recovery
```

---

# 106. Center DR

## FIG-RT-66. Scenario #8

```text
Main Center X
   ↓
Disaster Declare
   ↓
GSLB Route
   ↓
DR WEB/WAS
   ↓
Security / Key
   ↓
DR DB
   ↓
External
   ↓
Business Validation
```

---

# 107. Failure Scenario 필수 필드

```yaml
failureScenario:
  scenarioId:
  target:
  trigger:
  detection:
  impact:
  autoAction:
  manualAction:
  retry:
  dataConsistency:
  capacityAfterFailure:
  rto:
  rpo:
  evidence:
  result:
```

---

# 108. Scalability Runtime

## FIG-RT-67. Scale Trigger

```text
Metric Trend
   ↓
Threshold / Capacity Rule
   ↓
Scale Decision
   ↓
Scale-out / Scale-up
   ↓
Rebalance
   ↓
Re-test
```

---

# 109. WEB/WAS Scale-out

```text
Add Node
  ↓
Deploy same artifact
  ↓
Config
  ↓
L4 member
  ↓
Health
  ↓
Traffic
```

---

# 110. Event Scale

```text
Lag
  ↓
Consumer Increase
  ↓
Partition Balance
  ↓
Catch-up
```

---

# 111. Data Scale

```text
Storage / Query / Load
   ↓
Scale Data Resource
   ↓
Rebalance / Parallelism
   ↓
Performance Re-test
```

---

# 112. Scale 후 재검증

```text
Scale
 ↓
Functional Test
 ↓
Performance Test
 ↓
Failure Test
 ↓
Observability Check
```

---

# 113. DR 완료조건

## FIG-RT-68. DR Complete

```text
DR Resource Exists
      │
      ▼
Artifact Synced
      │
      ▼
Config Synced
      │
      ▼
Security Key Synced
      │
      ▼
Data Replicated
      │
      ▼
Route Ready
      │
      ▼
Runbook Ready
      │
      ▼
DR Test PASS
```

---

# 114. RTO / RPO

## FIG-RT-69. Recovery Objectives

```text
Failure Time
   │
   ├──── RPO ────► Data Loss Window
   │
   └──── RTO ────► Service Recovery Time
```

최종 수치는 최신 승인 Baseline으로 확정한다.

---

# 115. Failover 순서

## FIG-RT-70. DR Failover

```text
Detect
 ↓
Declare
 ↓
Stop / Fence Primary
 ↓
Data Ready
 ↓
Application Ready
 ↓
Network Route
 ↓
Security Ready
 ↓
External Check
 ↓
Business Validation
```

---

# 116. Failback

## FIG-RT-71. Failback

```text
DR Running
  ↓
Primary Restored
  ↓
Data Re-sync
  ↓
Config / Artifact verify
  ↓
Controlled Return
  ↓
Business Validation
```

---

# 117. Backup Architecture

## FIG-RT-72. Backup Scope

```text
Backup
│
├─ DB
├─ Configuration
├─ Artifact / Manifest
├─ Key / Certificate
└─ Operational Metadata
```

---

# 118. Backup 성공 ≠ Recovery Proven

```text
Backup Job SUCCESS
       │
       ▼
Restore Test?
   ├─ NO → Recovery not proven
   └─ YES → Evidence
```

---

# 119. DB Backup

```text
DB Backup
  ↓
Restore
  ↓
Recover
  ↓
Consistency
  ↓
Application Validation
```

---

# 120. Config Backup

```text
httpd.conf
server.xml
application.yml
JVM options
L4/GSLB config
```

복구 가능한 형태로 Version 관리한다.

---

# 121. Key / Certificate Backup

```text
Private Key
Certificate
Trust Store
HMAC Secret
JWKS metadata
```

보안 절차에 따라 안전하게 복구 가능해야 한다.

---

# 122. Monitoring RACI

## FIG-RT-73. Runtime Ownership

```text
Application
→ Business / Service Runtime

Framework
→ Thread / Context / TCF / Error

Infra
→ Host / Network / JVM

DBA
→ DB / SQL / Session

Security
→ Auth / JWT / Audit

Operations
→ Alert / Runbook / Incident
```

---

# 123. Alert Severity

```text
Critical
High
Medium
Info
```

Severity는:

```text
Business Impact
Recovery Urgency
Data Risk
Security Risk
```

를 기준으로 정의한다.

---

# 124. Runtime Evidence Package

## FIG-RT-74. Evidence Package

```text
Runtime Evidence
│
├─ Scenario ID
├─ Architecture Baseline ID
├─ Model Version
├─ Source Commit
├─ Artifact Hash
├─ Deployment ID
├─ Environment
├─ ServiceId
├─ GUID / TraceId
├─ Start/End
├─ Metrics
├─ Logs
├─ Test Result
├─ Screenshot / Report
└─ Evidence Hash
```

---

# 125. Evidence 종류

```text
Functional Evidence
Performance Evidence
Transaction Evidence
Timeout Evidence
Security Evidence
HA Evidence
DR Evidence
Backup/Restore Evidence
Observability Evidence
```

---

# 126. NFR 5축 Runtime Validation

## FIG-RT-75. NFR Runtime Gate

```text
Performance
Availability
Scalability
Security
Observability
      │
      ▼
Runtime Scenario
      │
      ▼
Evidence
      │
      ▼
PASS / FAIL / GAP
```

---

# 127. Performance Validation

```text
Normal Load
Peak Load
Stress
Soak
Slow DB
Slow External
Node Down
```

---

# 128. FAST Validation

```text
#7 Event/Offer
  ↓
Latency
Lag
Error
Replay
```

---

# 129. DEEP Validation

```text
#9/#10
ETL / BI
  ↓
Throughput
Duration
Query Performance
Resource Isolation
```

---

# 130. CDC Validation

```text
Source Commit
  ↓
Capture
  ↓
Transport
  ↓
Apply
  ↓
Consumer Visibility
```

실제 SLA는 이 측정구간으로 검증한다.

---

# 131. Batch Validation

```text
Schedule
 ↓
Start
 ↓
Job
 ↓
Data Count
 ↓
Complete
 ↓
Downstream Ready
```

---

# 132. Timeout 계층 검증

## FIG-RT-76. Timeout Runtime Validation

```text
DB Query Timeout
   <
TX / Worker Deadline
   <
Server / External Timeout
   <
Client Timeout
```

검증:

```text
Config
+
Integration Test
+
Runtime Trace
```

---

# 133. PDMG 5초 의미

```text
PDMG 5000ms
=
Current Online Worker Deadline Snapshot
```

아니다:

```text
Client SLA
DB Query Timeout
Enterprise Standard
```

---

# 134. Runtime Gate

## FIG-RT-77. Runtime Gate

```text
Scenario Defined?
  ↓
Executable?
  ↓
Exact Artifact Deployed?
  ↓
Trace Captured?
  ↓
Expected Outcome?
  ↓
Recovery Proven?
  ↓
Evidence Stored?
  ↓
PASS
```

---

# 135. HA / DR 완료 Gate

```text
Node Failover PASS
Group Failure PASS
DB Failure PASS
Event Failure PASS
CDC Failure PASS
Center DR PASS
Restore PASS
```

---

# 136. Runtime 준수 규범

```text
모든 Runtime은 Type을 가진다.
모든 Runtime은 Owner를 가진다.
모든 Runtime은 Sequence를 가진다.
모든 Runtime은 Failure를 가진다.
모든 Runtime은 Recovery를 가진다.
모든 Runtime은 Evidence를 가진다.
```

---

# 137. Sequence 규범

```text
Actor
Entry
Thread
TX
Resource
External
Outcome
```

을 생략하지 않는다.

---

# 138. SLO 규범

```text
SLO
=
Metric
+
Measurement Point
+
Window
+
Threshold
+
Owner
```

수치 하나만 적는 것은 SLO가 아니다.

---

# 139. Retry 규범

```text
Retry
=
Retryable Error
+
Backoff
+
Max Retry
+
Idempotency
+
Final Recovery
```

---

# 140. Monitoring 규범

```text
Metric only
≠ Monitoring Complete

Metric
→ Alert
→ Diagnosis
→ Runbook
→ Recovery
```

---

# 141. Evidence 규범

```text
Log File
≠ Runtime Evidence Package
```

Evidence는 반드시 Release/Deployment/Scenario와 연결한다.

---

# 142. PDMG Runtime 규범

```text
Request Thread
Worker Thread
Transaction
JDBC
```

을 한 생명주기로 설명하지 않는다.

---

# 143. Timeout 규범

```text
HTTP Timeout 후
Worker / DB 상태 확인 없이
성공/실패를 단정하지 않는다.
```

---

# 144. Transaction 규범

```text
@Transactional annotation
=
TX Owner
```

라고 단정하지 않는다.

---

# 145. Worker 규범

```text
Context/MDC
Capture
Install
Clear
```

을 반드시 관리한다.

---

# 146. Overload 규범

```text
Overload
≠ Timeout
```

Queue Full / Reject를 별도 운영지표로 본다.

---

# 147. CDC 규범

```text
"실시간"
이라고만 쓰지 않는다.

Lag 측정구간을 정의한다.
```

---

# 148. File 규범

```text
Transfer Success
≠ Business Process Complete
```

---

# 149. Batch 규범

```text
Job COMPLETED
+
Data Reconcile
```

를 완료기준으로 본다.

---

# 150. DR 규범

```text
DR Resource Exists
≠ DR Ready
```

---

# 151. Backup 규범

```text
Backup Success
≠ Restore Proven
```

---

# 152. Runtime Anti-pattern

## FIG-RT-78. Runtime Anti-pattern Map

```text
HTTP Timeout = Worker End                     X
Worker Cancel = JDBC Cancel                   X
@Transactional 위치 = TX Begin                X
Event = Online Thread                         X
CDC = Strong Sync                             X
Batch Success = Data Correct                  X
Backup Success = Recovery Proven              X
DR Center Exists = DR Complete                X
Metric Exists = Operable                      X
Log Exists = Runtime Evidence                 X
```

---

# 153. Runtime Inventory Template

```yaml
runtime:
  runtimeId:
  type:
  businessFlow:
  owner:
  entry:
  actors:
  sequence:
  threads:
  txBoundary:
  timeout:
  pools:
  failureModes:
  recovery:
  metrics:
  slo:
  evidence:
  status:
```

---

# 154. Sequence Template

```text
Actor A
  │
  ▼
Entry
  │
  ▼
Framework
  │
  ▼
Business
  │
  ▼
Data / External
  │
  ▼
Outcome
```

---

# 155. Failure Scenario Template

```yaml
failure:
  id:
  trigger:
  detection:
  affectedRuntime:
  userImpact:
  dataImpact:
  automaticAction:
  manualAction:
  rollback:
  retry:
  failover:
  recovery:
  evidence:
```

---

# 156. Monitoring Inventory Template

```yaml
monitoring:
  component:
  metrics:
  thresholds:
  alert:
  owner:
  runbook:
  dashboard:
  evidence:
```

---

# 157. Backup Inventory Template

```yaml
backup:
  asset:
  type:
  frequency:
  retention:
  encryption:
  restoreProcedure:
  lastRestoreTest:
  owner:
  evidence:
```

---

# 158. DR Inventory Template

```yaml
dr:
  service:
  primary:
  recoverySite:
  rto:
  rpo:
  dependencies:
  route:
  dataReplication:
  securityDependencies:
  runbook:
  lastDrTest:
  evidence:
```

---

# 159. Runtime → Observability Trace

## FIG-RT-79. Runtime Trace

```text
Runtime Type
   ↓
ServiceId / InterfaceId
   ↓
GUID / EventId / JobId
   ↓
JVM / Worker / SQL
   ↓
Metric / Log
   ↓
Evidence
```

---

# 160. Runtime → Physical Trace

```text
Runtime
  ↓
Logical Node
  ↓
Host
  ↓
JVM
  ↓
WAR
  ↓
Pool
```

---

# 161. Runtime → Data Trace

```text
Runtime
  ↓
ServiceId
  ↓
DAO / Mapper
  ↓
SqlId
  ↓
Table / View
```

---

# 162. Runtime → Interface Trace

```text
Runtime
  ↓
InterfaceId
  ↓
Producer
  ↓
Mechanism
  ↓
Consumer
```

---

# 163. Runtime Evidence Closed Loop

## FIG-RT-80. Runtime → New Baseline

```text
Architecture
  ↓
Model
  ↓
Source / Config
  ↓
Test
  ↓
Deploy
  ↓
Runtime
  ↓
Evidence
  ↓
Drift
  ↓
GAP / ADR
  ↓
New Baseline
```

---

# 164. Drift 대상

```text
Expected Runtime Type
vs Actual Runtime

Expected Timeout
vs Config

Expected TX Boundary
vs Runtime

Expected Deployment
vs Host

Expected Security
vs Runtime

Expected SLO
vs Metric
```

---

# 165. PDMG Drift 예시

```text
Design
Filter order = old value

Current
HIGHEST_PRECEDENCE + 20

→ DRIFT
```

또는:

```text
Target
Standard Identity Binding

Current
JWT ssoId ↔ optr_eno uncertain

→ GAP
```

---

# 166. Runtime Conformance Rule 후보

```text
R-RUNTIME-TYPE
R-RUNTIME-SEQUENCE
R-RUNTIME-EVIDENCE
R-TX-OWNER
R-TIMEOUT-HIERARCHY
R-WORKER-CONTEXT-CLEAR
R-OVERLOAD-MONITOR
R-CDC-LAG
R-BATCH-RECONCILE
R-DR-TEST
R-BACKUP-RESTORE
```

---

# 167. GAP Register

## TEXT ARCHITECTURE 보완 — GAP Register

```text
Expected / Required
      │
      ▼
Actual / Evidence
      │
      ▼
Difference
      │
      ▼
[GAP]
      │
      ├─ Owner
      ├─ Action
      ├─ ADR
      └─ Evidence
```


| ID | GAP | 영향 |
|---|---|---|
| GAP-RT-01 | Runtime Type 전수 Inventory 미완료 | Governance |
| GAP-RT-02 | ServiceId→Runtime Type Mapping 미완료 | Traceability |
| GAP-RT-03 | Query Timeout 미확정 | DB |
| GAP-RT-04 | TX Timeout 미확정 | Transaction |
| GAP-RT-05 | JDBC Interrupt/Cancel 실증 미완료 | Timeout |
| GAP-RT-06 | Mutable ServiceContext 개선 미결정 | Thread |
| GAP-RT-07 | TCF OFF Transaction Policy 불일치 | Runtime |
| GAP-RT-08 | Generic Exception 표준 미완료 | Error |
| GAP-RT-09 | JWT Identity Binding 미완료 | Security |
| GAP-RT-10 | Runtime Dashboard/Alert Threshold 미확정 | Observability |
| GAP-RT-11 | CDC 최종 SLA 미확정 | Data |
| GAP-RT-12 | Event Replay/DLQ 전수정책 미완료 | Event |
| GAP-RT-13 | DR RTO/RPO 미확정 | DR |
| GAP-RT-14 | Restore Test Evidence 미완료 | Backup |
| GAP-RT-15 | Runtime Evidence Manifest 자동화 미완료 | Closed Loop |

---

# 168. RISK Register

## TEXT ARCHITECTURE 보완 — RISK Register

```text
Cause
  ↓
Risk Event
  ↓
Business / Data / Security / Operation Impact
  ↓
Severity
  ↓
Mitigation / Control
  ↓
Owner
  ↓
Evidence / Closure
```


| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-RT-01 | HTTP timeout 후 late commit | Critical |
| RISK-RT-02 | Worker thread leak / context leak | Critical |
| RISK-RT-03 | Hikari/DB slow가 전체 queue를 막음 | High |
| RISK-RT-04 | TCF ON/OFF 정책 차이 | High |
| RISK-RT-05 | Unknown exception 비표준 응답 | High |
| RISK-RT-06 | Identity mismatch | Critical |
| RISK-RT-07 | Event lag 미탐지 | High |
| RISK-RT-08 | CDC lag 미탐지 | Critical |
| RISK-RT-09 | Batch 성공 오판 | High |
| RISK-RT-10 | DR Resource만 있고 Runbook/Test 없음 | Critical |
| RISK-RT-11 | Backup 성공만으로 복구 가능 판단 | Critical |
| RISK-RT-12 | Runtime Evidence가 Release와 연결 안 됨 | Critical |

---

# 169. ADR 후보

```text
ADR-RT-01 Runtime Type SSOT
ADR-RT-02 TCF ON/OFF Target Runtime
ADR-RT-03 Transaction Ownership
ADR-RT-04 Timeout Budget
ADR-RT-05 JDBC Cancel Strategy
ADR-RT-06 Context Snapshot Model
ADR-RT-07 Overload Policy
ADR-RT-08 Error Runtime Standard
ADR-RT-09 JWT Identity Binding
ADR-RT-10 Event Replay / DLQ
ADR-RT-11 CDC SLA
ADR-RT-12 Runtime Alert Threshold
ADR-RT-13 DR RTO/RPO
ADR-RT-14 Backup Restore Test
ADR-RT-15 Runtime Evidence Manifest
```

---

# 170. CONFIRMED / WORKING BASELINE

```text
[CONFIRMED / WORKING BASELINE]

- Runtime Type #1~#12 구조
- PDMG TCF ON Online Runtime
- Request Thread / Worker Thread 분리
- Worker TransactionTemplate Boundary
- Facade REQUIRED joins outer TX
- Current PDMG timeout 5000ms
- Worker Pool 20 / Queue 100
- Deadline Late Commit Prevention
- HTTP 504 != Worker End
- Error / Success Envelope current pattern
- GUID / ServiceId Runtime Trace
- HA/DR/Backup는 각각 별도 책임
```

---

# 171. CONFLICT / OPEN

```text
[CONFLICT-RT-01]
CDC SLA 30s vs 3s

[OPEN-RT-01]
Spring TX Timeout

[OPEN-RT-02]
JDBC Query Timeout

[OPEN-RT-03]
JDBC Interrupt / Cancel actual behavior

[OPEN-RT-04]
Runtime Type별 SLO

[OPEN-RT-05]
DR RTO/RPO

[OPEN-RT-06]
Alert Threshold

[OPEN-RT-07]
PDMG TCF OFF Target Policy

[OPEN-RT-08]
Session/JWT DR Behavior
```

---

# 172. Runtime Completion Gate

## FIG-RT-81. Final Runtime Gate

```text
Runtime Type Defined?
   ↓ YES
Sequence Defined?
   ↓ YES
Thread / TX Defined?
   ↓ YES
Timeout / Error Defined?
   ↓ YES
Failure Scenario Defined?
   ↓ YES
Recovery Defined?
   ↓ YES
Monitoring Defined?
   ↓ YES
Runtime Test Executed?
   ↓ YES
Evidence Captured?
   ↓ YES
Critical Drift = 0?
   ↓ YES
RUNTIME PASS
```

---

# 173. 전체 Architecture Route 완결

## FIG-RT-82. VISION → RUNTIME

```text
VISION
왜 바꾸는가?
   ↓
BIG PICTURE
누가 무엇을 책임하는가?
   ↓
LOGICAL
어떤 논리 구조로 분리하는가?
   ↓
PHYSICAL
어디에 배치하는가?
   ↓
MECHANISM
어떤 규칙으로 동작하는가?
   ↓
RUNTIME
실제로 어떻게 실행되고 실패하며 복구되는가?
   ↓
EVIDENCE
설계대로 동작했음을 무엇으로 증명하는가?
```

---

# 174. RUNTIME 최종 통합 지도

## FIG-RT-83. Runtime Summary

```text
ENTRY
Request / Event / Change / File / Schedule
   ↓
RUNTIME TYPE
#1 ~ #12
   ↓
SEQUENCE
Filter / Security / TCF / Business / Data
   ↓
RESOURCE
Request Thread / Worker / Hikari / DB
   ↓
TRANSACTION
BEGIN / COMMIT / ROLLBACK
   ↓
OUTCOME
Success / Error / Timeout / Overload
   ↓
RECOVERY
Retry / Replay / Restart / Failover
   ↓
OBSERVABILITY
GUID / ServiceId / Metric / Log / Trace
   ↓
AVAILABILITY
HA / DR / Backup / Restore
   ↓
RUNTIME EVIDENCE
   ↓
DRIFT / GAP / ADR
   ↓
NEW BASELINE
```

---

# 보완검토 A. RUNTIME Reference Coverage 보강

## FIG-RT-SUP-01. 시스템 모니터링 3축

```text
NFR
  ↓
Runtime Metric
  ↓
DevOps / OM
  ↓
Alert / Runbook
  ↓
Recovery Evidence
```

---

## FIG-RT-SUP-02. DB HA/DR — Integrity First

```text
DB Availability
      ↓
Local HA
      ↓
Replication / DR
      ↓
Consistency
      ↓
Recovery
      ↓
Business Validation
```

```text
무조건 Active-Active
보다
정합성 / 운영복잡도 / 복구
우선
```

---

## FIG-RT-SUP-03. 8대 장애 시나리오

```text
#1 AP VM
#2 AP Group
#3 RDW
#4 ADW
#5 Kafka / Event
#6 CDC Relay
#7 Integration
#8 Center DR
        │
        ▼
Detection → Impact → Recovery → Evidence
```

---

## FIG-RT-SUP-04. Scalability 축

```text
Scale Demand
  │
  ├─ WEB / WAS
  ├─ Worker
  ├─ DB Connection
  ├─ Kafka / Event
  ├─ Data Platform
  ├─ ETL Parallelism
  └─ Storage / I/O
```

---

## FIG-RT-SUP-05. RTO / RPO는 서비스 단위로

```text
Business Service
   ↓
Component Dependency
   ↓
RTO / RPO
   ↓
Failover / Restore Design
   ↓
DR Test
   ↓
Evidence
```

최종 수치가 미확정이면 `[OPEN]` 상태를 유지한다.

---

## FIG-RT-SUP-06. 개통 금지조건

```text
Runtime Type 없음
      OR
SLO 측정구간 없음
      OR
Owner / Monitoring 없음
      OR
Retry / Recovery 없음
      OR
8 Failure Scenario 미검증
      OR
RTO/RPO 없음
      OR
Restore / DR Drill 없음
      OR
Runtime Evidence 없음
      ↓
GO-LIVE BLOCK
```

---

## FIG-RT-SUP-07. Session / JWT HA

```text
JWT Verification
     +
Refresh / Revoke State
     +
Optional HttpSession
     +
Key / JWKS
        │
        ▼
HA / DR Behavior
```

`Stateless` 한 단어로 HA/DR을 설명하지 않는다.

---

# 175. Definition of Done

## TEXT ARCHITECTURE 보완 — Definition of Done

```text
Architecture Inputs
      ↓
Structure / Contract / Runtime Check
      ↓
Evidence Check
      ↓
GAP / CONFLICT / UNKNOWN Review
      ↓
PASS Condition
      │
      ├─ satisfied → PASS
      └─ pending   → CONDITIONAL PASS
```


## Runtime Type
- [x] #1~#12 분류
- [x] Primary/Secondary 개념
- [x] 유형 없는 신설 금지

## Online
- [x] PDMG Full Runtime
- [x] Request/Worker 분리
- [x] Context/MDC
- [x] TX Boundary
- [x] Commit/Rollback
- [x] Timeout/Overload

## Data / Event / File / Batch
- [x] Event Runtime
- [x] CDC Runtime
- [x] ETL Runtime
- [x] BI Runtime
- [x] File Runtime
- [x] Batch Runtime

## Security / Error
- [x] JWT Runtime
- [x] Identity Binding GAP
- [x] Success/Error Assembly
- [x] Early Error
- [x] Error→Recovery Matrix

## Observability
- [x] JVM/Worker/Hikari/DB
- [x] Event/CDC/ETL/File/Batch
- [x] Security Monitoring
- [x] Alert→Runbook→Recovery

## HA / DR / Backup
- [x] 8개 Failure Scenario
- [x] Scale Trigger
- [x] DR Failover/Failback
- [x] Backup/Restore
- [x] DR Completion Gate

## Evidence / Governance
- [x] Runtime Evidence Package
- [x] NFR Validation
- [x] Drift
- [x] GAP/RISK/ADR
- [x] Final Runtime Gate

**RUNTIME 장 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. Runtime Type 전수 Inventory 생성
2. ServiceId→Runtime Type Mapping
3. Query/TX Timeout 실제값 수집
4. JDBC Cancel Integration Test
5. Worker Context Snapshot 정책 확정
6. TCF OFF Runtime Target 정책 확정
7. CDC 최종 SLA 확정
8. Event/File/Batch Recovery Test
9. HA/DR End-to-End Test
10. Restore Test
11. Runtime Evidence Manifest 자동화
12. Critical Runtime Drift 0건

---

# 176. 다음 단계 — Architecture Closed Loop

RUNTIME 이후에는 새로운 기술 장을 더 붙이는 것이 핵심이 아니다.

이제 해야 할 것은:

```text
VISION
BIG PICTURE
LOGICAL
PHYSICAL
MECHANISM
RUNTIME
     │
     ▼
TRACEABILITY
     │
     ▼
EVIDENCE
     │
     ▼
DRIFT
     │
     ▼
GAP / ADR
     │
     ▼
NEW ARCHITECTURE BASELINE
```

이다.

즉,

> **RUNTIME은 Top-down Architecture의 마지막 Drill-down 단계이며, 이후부터는 다시 Bottom-up Evidence를 통해 상위 Architecture를 검증하는 단계로 전환된다.**

---


====================================================================================================

# CHAPTER 07

====================================================================================================

# NSIGHT / PDMG 아키텍처 정의서
# 07. TRACEABILITY / EVIDENCE / ARCHITECTURE CLOSED LOOP

> 보완개정: **v2 — TEXT Architecture Figure Coverage / Reference Coverage / Evidence Consistency 재점검**
## TEXT ARCHITECTURE 보완 — 장 전체 위치

```text
Document
   ↓
Model
   ↓
Source
   ↓
Test
   ↓
Runtime Evidence
   ↓
Drift
   ↓
GAP/ADR
   ↓
HG90
```

## Visual-First / Top-down → Bottom-up Verification 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-VISUAL-CLOSED-LOOP-07`  
> Architecture Level: **L6 — TRACEABILITY / GOVERNANCE / EVIDENCE**  
> 문서 상태: **Draft / Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_06_RUNTIME_VISUAL_FIRST_TOPDOWN_상세본.md`  
> 본 장 목적: **Architecture를 문서에서 끝내지 않고 Source/Config/Test/Runtime과 연결하여 검증 가능한 Baseline으로 전환**

---

# 0. 이 장부터 방향이 바뀐다

1~6장은 위에서 아래로 설계를 구체화했다.

```text
VISION
  ↓
BIG PICTURE
  ↓
LOGICAL
  ↓
PHYSICAL
  ↓
MECHANISM
  ↓
RUNTIME
```

7장부터는 반대로 올라간다.

```text
SOURCE / CONFIG
      ↑
TEST
      ↑
RUNTIME EVIDENCE
      ↑
TRACEABILITY
      ↑
DRIFT / GAP
      ↑
ADR
      ↑
NEW BASELINE
```

즉 전체 Architecture는 다음과 같은 왕복구조다.

```text
Top-down Design
      ↓
Runtime
      ↑
Bottom-up Evidence
```

---

# 1. VISUAL ROUTE — Closed Loop 전체를 한 장으로 보기

## FIG-CL-01. Architecture Closed Loop

```text
╔══════════════════════════════════════════════════════════════════════════════╗
║                         ARCHITECTURE CLOSED LOOP                             ║
╚══════════════════════════════════════════════════════════════════════════════╝

 [DOCUMENT]
 Vision / Big Picture / Logical / Physical / Mechanism / Runtime
        │
        ▼
 [MODEL]
 Domain / System / Node / ServiceId / Interface / Policy
        │
        ▼
 [SOURCE / CONFIG]
 Java / XML / YAML / SQL / WEB / WAS / DB / Deployment
        │
        ▼
 [TEST]
 Static / Architecture / Contract / Integration / Security / Performance
        │
        ▼
 [RUNTIME]
 Request / Thread / TX / SQL / Event / Batch / Failover
        │
        ▼
 [EVIDENCE]
 GUID / ServiceId / Log / Metric / Trace / Artifact / Deployment
        │
        ▼
 [DRIFT]
 Expected vs Actual
        │
        ▼
 [GAP / ADR]
 Fix / Accept / Exception / Change
        │
        ▼
 [NEW BASELINE]
 Document + Model + Rule + Evidence
        │
        └───────────────────────────────────────────────↺
```

### 핵심 결론

> **Architecture 문서는 시작점이고, Runtime Evidence가 완성점이며, 둘 사이의 차이를 다시 Baseline에 반영해야 Architecture가 살아 있다.**

---

# 2. Top-down과 Bottom-up의 만나는 지점

## FIG-CL-02. Two-way Architecture

```text
TOP-DOWN

Requirement
   ↓
Vision
   ↓
Big Picture
   ↓
Logical
   ↓
Physical
   ↓
Mechanism
   ↓
Runtime Design

----------------- MEETING POINT -----------------

Runtime Evidence

----------------- MEETING POINT -----------------

BOTTOM-UP

Runtime
   ↑
Deployment
   ↑
Artifact
   ↑
Source / Config
   ↑
Model
   ↑
Document
```

### 핵심

```text
설계는 위에서 아래로
검증은 아래에서 위로
```

---

# 3. Traceability의 목적

## FIG-CL-03. Why Traceability

```text
Requirement
   │
   ▼
Architecture Decision
   │
   ▼
Application / Service
   │
   ▼
Source
   │
   ▼
Runtime
   │
   ▼
Evidence
```

Traceability가 있어야 다음 질문에 답할 수 있다.

```text
이 요구사항은 어디에 구현됐는가?
이 ServiceId는 어떤 SQL을 호출하는가?
이 SQL은 어떤 Table을 사용하는가?
이 WAR는 어떤 Host에서 실행되는가?
이 장애는 어떤 Architecture Rule 위반인가?
이 변경은 어떤 ADR을 필요로 하는가?
```

---

# 4. Traceability 핵심 식별자

## FIG-CL-04. Enterprise Trace Keys

```text
Architecture
├─ Requirement ID
├─ Principle ID
├─ ADR ID
├─ System ID
├─ Node ID
└─ Component ID

Application
├─ Application Group
├─ Business Code
├─ Program ID
└─ ServiceId

Integration
└─ InterfaceId

Runtime
├─ GUID / TraceId
├─ EventId
├─ JobId
├─ FileId
└─ DeploymentId

Data
├─ Mapper Namespace
├─ SqlId
├─ Table
└─ View
```

---

# 5. ServiceId를 중심축으로 사용하는 이유

## FIG-CL-05. ServiceId Spine

```text
Requirement
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
Table / View
```

### 핵심

PDMG Current Runtime에서 ServiceId는 실제 Dispatcher/Handler 선택에 사용되므로,
Architecture Traceability의 강력한 연결축이 될 수 있다.

---

# 6. ServiceId Naming Structure

## FIG-CL-06. ServiceId Anatomy

```text
mg | co | a | 9001 | S | 0
│    │    │    │      │   │
│    │    │    │      │   └─ 거래순번
│    │    │    │      └──── 거래구분
│    │    │    └─────────── 프로그램번호
│    │    └──────────────── 기능
│    └───────────────────── 업무구분
└────────────────────────── 대구분
```

예:

```text
mgcoa9001S0
```

---

# 7. ServiceId Trace Chain

## FIG-CL-07. Runtime Trace

```text
ServiceId
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
Mapper
  ↓
SQL
  ↓
DB Object
```

---

# 8. Forward Trace

## FIG-CL-08. Forward Traceability

```text
Requirement
 ↓
Architecture Principle
 ↓
ADR
 ↓
Application Group
 ↓
System
 ↓
Program
 ↓
ServiceId
 ↓
Handler
 ↓
Service
 ↓
SQL
 ↓
Table
 ↓
Artifact
 ↓
Deployment
 ↓
Runtime Evidence
```

---

# 9. Reverse Trace

## FIG-CL-09. Reverse Traceability

```text
Table / SQL
 ↑
Mapper
 ↑
DAO
 ↑
Service
 ↑
Facade
 ↑
Handler
 ↑
ServiceId
 ↑
Program
 ↑
Application
 ↑
Requirement
```

### 핵심

장애 분석에서는 Reverse Trace가 특히 중요하다.

---

# 10. Requirement → Architecture

## FIG-CL-10. Requirement Projection

```text
Requirement
   │
   ▼
Architecture Requirement
   │
   ▼
Principle
   │
   ▼
Decision
   │
   ▼
Rule
```

예:

```text
"실시간 고객반응"

→ Event Architecture
→ FAST Path
→ Event Runtime 분리
→ Kafka/Consumer
→ Lag SLO
```

---

# 11. Principle → Rule

## FIG-CL-11. Principle Operationalization

```text
Principle
"Handler는 DAO를 직접 호출하지 않는다."
        │
        ▼
Architecture Rule
        │
        ▼
Static Scanner / ArchUnit
        │
        ▼
PASS / FAIL
```

---

# 12. Document → Model

## FIG-CL-12. Architecture Model

```text
Markdown / PPT
    │
    ▼
Machine-readable Model
    │
    ├─ System
    ├─ Node
    ├─ ServiceId
    ├─ Interface
    ├─ Data
    ├─ Policy
    └─ Evidence Link
```

### 목적

사람이 읽는 문서와
기계가 검증하는 Model을 분리하되 연결한다.

---

# 13. Model Entity

## FIG-CL-13. Core Entities

```text
System
Application
Business
Program
ServiceId
Handler
Facade
Service
DAO
Mapper
SqlId
Table
Interface
Node
Artifact
Deployment
RuntimeEvidence
ADR
GAP
```

---

# 14. Model Relation

## FIG-CL-14. Core Relations

```text
Application
  CONTAINS
Program

Program
  EXPOSES
ServiceId

ServiceId
  HANDLED_BY
Handler

Handler
  CALLS
Facade

Facade
  CALLS
Service

Service
  USES
DAO

DAO
  EXECUTES
SqlId

SqlId
  ACCESSES
Table
```

---

# 15. Design Graph와 Runtime Graph

## FIG-CL-15. Two Graphs

```text
[Design Graph]

ServiceId
→ Handler
→ Facade
→ Service
→ DAO
→ SQL


[Runtime Graph]

Request
→ Dispatcher
→ Handler
→ Worker
→ TX
→ SQL
→ DB
```

### 핵심

```text
Design Relation
≠ Runtime Relation
```

---

# 16. Runtime Relation

## FIG-CL-16. Runtime Model

```text
Dispatcher
  DISPATCHES_TO
Handler

Handler
  RUNS_ON
Worker

TransactionTemplate
  STARTS
Transaction

Facade
  PARTICIPATES_IN
Transaction

DAO
  USES
Datasource
```

---

# 17. Source Baseline

## FIG-CL-17. Source Baseline

```text
Repository
  │
  ├─ Branch
  ├─ Commit
  ├─ Module
  ├─ Build File
  ├─ Source
  ├─ Config
  └─ SQL
```

Architecture 검증은 반드시 특정 Source Baseline과 연결한다.

---

# 18. PDMG Baseline Modules

```text
pdmg-ui
pdmg-jwt
pdmg-fw
pdmg-service
pdmg-om
```

### 주의

PDMG Reference Scope 밖의 Module을 자동 포함하지 않는다.

---

# 19. Source Inventory

## FIG-CL-18. Source Inventory

```text
Module
  ↓
Package
  ↓
Class
  ↓
Method
  ↓
Annotation
  ↓
Dependency
```

---

# 20. Config Inventory

## FIG-CL-19. Config Inventory

```text
application.yml
server.xml
httpd.conf
setenv.sh
log4j2.xml
build.gradle
Mapper XML
Security Config
L4 / GSLB
```

---

# 21. SQL Inventory

## FIG-CL-20. SQL Trace

```text
DAO Method
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

# 22. SQL Parsing Rule

```text
Class 이름만 보고 Table 추정      X
DTO 이름으로 Table 추정           X
Mapper 이름으로 Table 추정        X
```

필요:

```text
Mapper XML Parser
+
SQL Parser
```

---

# 23. ServiceId Scanner

## FIG-CL-21. ServiceId Discovery

```text
Source
  │
  ├─ Handler.serviceIds()
  ├─ Controller Mapping
  ├─ Constants
  ├─ UI Catalog
  └─ Config
  │
  ▼
ServiceId Index
```

---

# 24. Handler Registration Rule

## FIG-CL-22. Registry Validation

```text
Handler
  │
  └─ serviceIds()
       ↓
Registry
       ↓
Unique?
  ├─ YES
  └─ NO → FAIL
```

---

# 25. Handler Branch Validation

## FIG-CL-23. Registered vs Executed

```text
serviceIds()
   │
   ▼
Registered IDs
   │
   │ compare
   ▼
handle() branches
```

가능한 문제:

```text
등록 O / Branch X
등록 X / Branch O
```

---

# 26. UI Catalog vs Backend

## FIG-CL-24. Catalog Drift

```text
UI Transaction Catalog
       │
       │ compare
       ▼
Backend Handler Registry
```

차이가 있으면:

```text
UI Catalog ≠ ServiceId SSOT
```

---

# 27. Package / Naming Trace

## FIG-CL-25. Naming Projection

```text
Business Classification
MG / CO / A
      │
      ├────────► Java nhnis.mg.co.a
      ├────────► Mapper rdw.mg.co.a
      └────────► ServiceId mgcoa...
```

---

# 28. Naming Rule

```text
Business Classification
      ↓
Package
      ↓
Mapper
      ↓
ServiceId
```

모두 동일한 분류축을 사용해야 한다.

---

# 29. Code Dependency Rule

## FIG-CL-26. Layer Dependency

```text
Controller
  ↓
Handler / Facade
  ↓
Service
  ↓
DAO
  ↓
Mapper
```

금지:

```text
Controller → DAO
Handler → DAO
Controller → Mapper
```

---

# 30. Framework Dependency Rule

```text
Business
→ Framework 사용

Framework
→ 특정 Business 직접 의존
```

후자는 최소화/금지한다.

---

# 31. Architecture as Code

## FIG-CL-27. Architecture Rule Pipeline

```text
Architecture Rule
   ↓
Rule Definition
   ↓
Scanner
   ↓
CI Build
   ↓
PASS / FAIL
```

---

# 32. Rule Types

```text
Naming Rule
Dependency Rule
ServiceId Rule
Interface Rule
Transaction Rule
Timeout Rule
Security Rule
Logging Rule
Deployment Rule
Runtime Evidence Rule
```

---

# 33. R-SERVICEID-FORMAT

```text
ServiceId
  ↓
Pattern Validation
  ↓
PASS / FAIL
```

---

# 34. R-SERVICEID-UNIQUE

```text
All ServiceIds
  ↓
Duplicate?
  ├─ NO  → PASS
  └─ YES → FAIL
```

---

# 35. R-HANDLER-REGISTRATION

```text
ServiceId
  ↓
Registered Handler?
  ├─ YES
  └─ NO → FAIL
```

---

# 36. R-HANDLER-BRANCH

```text
Registered ServiceId
   ↓
Handler branch exists?
   ├─ YES
   └─ NO → FAIL
```

---

# 37. R-HANDLER-NO-DAO

```text
Handler
  ↓ dependency scan
DAO / Mapper?
  ├─ NO → PASS
  └─ YES → FAIL
```

---

# 38. R-CONTROLLER-NO-DAO

```text
Controller
  ↓
DAO dependency?
  ├─ NO
  └─ YES → FAIL
```

---

# 39. R-DAO-MAPPER-MAP

```text
DAO Method
  ↓
Mapper SqlId
  ↓
Exists?
```

---

# 40. R-SQL-TABLE-TRACE

```text
SqlId
  ↓
Parsed SQL
  ↓
Referenced Objects
  ↓
Table / View Index
```

---

# 41. R-TX-OWNER

## FIG-CL-28. Transaction Rule

```text
ServiceId
  ↓
Runtime Entry
  ↓
TX Owner
  ↓
TransactionManager
  ↓
DB
```

TX Owner가 UNKNOWN이면 Gate 미통과.

---

# 42. R-TIMEOUT-POLICY

```text
ServiceId
  ↓
Client Timeout
  ↓
Server Timeout
  ↓
Worker Deadline
  ↓
Query Timeout
```

Hierarchy 위반 시 FAIL 후보.

---

# 43. R-JWT-PRIVATE-KEY

```text
Private Key
   │
   ├─ Issuer only?
   │
   └─ Validator distribution?
```

Validator에 Private Key 배포 시 FAIL.

---

# 44. R-SENSITIVE-LOG

```text
Log Statement
  ↓
Token / Password / Secret?
  ├─ NO
  └─ YES → FAIL
```

---

# 45. R-DEPLOYMENT-MAP

```text
Artifact
  ↓
Deployment Manifest
  ↓
Host / JVM / WAR
```

Mapping이 없으면 Runtime Evidence 연결이 불가능하다.

---

# 46. R-RUNTIME-EVIDENCE

```text
ServiceId
  ↓
Deployment
  ↓
Runtime Scenario
  ↓
Evidence
```

Evidence 없는 Critical Service는 Gate 미통과.

---

# 47. Test Architecture

## FIG-CL-29. Test Layers

```text
Static
  ↓
Architecture Rule
  ↓
Unit
  ↓
Contract
  ↓
Integration
  ↓
Security
  ↓
Performance
  ↓
Failure
  ↓
Runtime Evidence
```

---

# 48. Static Test

```text
Naming
Dependency
Annotation
Forbidden API
Secret Scan
Config Pattern
```

---

# 49. Architecture Test

```text
Layer Dependency
ServiceId Registry
Package Rule
Transaction Boundary Candidate
Direct DB Access
```

---

# 50. Contract Test

```text
Request Header
DTO Schema
Success Envelope
Error Envelope
Interface Schema
```

---

# 51. Integration Test

```text
Application → DB
Application → External
JWT → Business
Event → Consumer
CDC → RDW
File → Consumer
```

---

# 52. Security Test

```text
JWT Signature
Expired Token
Wrong kid
Revoked Token
Header/JWT Identity mismatch
Direct WAS Access
Sensitive Log
```

---

# 53. Transaction Test

## FIG-CL-30. TX Tests

```text
Normal Commit
Business Exception Rollback
Checked Exception
Timeout
Late Commit Prevention
DB Error
Nested REQUIRED
```

---

# 54. Timeout Test

```text
Slow SQL
Slow External
Queue Wait
Pool Exhaustion
Worker Timeout
Client Timeout
```

---

# 55. Performance Test

```text
Normal
Peak
Stress
Soak
Node Down
DB Slow
External Slow
```

---

# 56. Failure Test

```text
AP Node Down
AP Group Down
DB Down
Kafka Down
CDC Down
External Down
Center DR
```

---

# 57. Runtime Evidence

## FIG-CL-31. Evidence Chain

```text
Source Commit
   ↓
Build ID
   ↓
Artifact Hash
   ↓
Deployment ID
   ↓
Runtime Scenario
   ↓
ServiceId
   ↓
GUID / TraceId
   ↓
Metric / Log / Result
   ↓
Evidence Hash
```

---

# 58. Evidence는 무엇이 아닌가

```text
로그 한 줄
≠ Runtime Evidence

스크린샷 한 장
≠ Runtime Evidence

테스트 결과만
≠ Deployment Evidence
```

Evidence는 반드시 Context를 가진다.

---

# 59. Evidence Manifest

```yaml
evidence:
  evidenceId:
  architectureBaseline:
  modelVersion:
  sourceCommit:
  buildId:
  artifactHash:
  deploymentId:
  environment:
  runtimeScenario:
  serviceId:
  traceId:
  startTime:
  endTime:
  result:
  metrics:
  logRefs:
  attachments:
  evidenceHash:
```

---

# 60. Artifact Trace

## FIG-CL-32. Build Trace

```text
Source Commit
  ↓
Build
  ↓
Artifact
  ↓
Hash
```

---

# 61. Deployment Trace

```text
Artifact Hash
  ↓
Deployment ID
  ↓
Environment
  ↓
Host
  ↓
JVM
  ↓
WAR
```

---

# 62. Runtime Trace

```text
Deployment
  ↓
ServiceId
  ↓
GUID
  ↓
Worker
  ↓
SQL
  ↓
Result
```

---

# 63. Release Trace

## FIG-CL-33. Release Evidence

```text
Release
├─ Source Commit
├─ Artifact Hash
├─ Deployment ID
├─ Architecture Baseline
├─ Test Result
└─ Runtime Evidence
```

---

# 64. Same Artifact Principle

```text
DEV
  │
  └─ Artifact A
        ↓ promote
TEST
  │
  └─ Artifact A
        ↓ promote
PROD
  │
  └─ Artifact A
```

환경별 Source Rebuild는 피한다.

---

# 65. Configuration Trace

```text
Artifact
+
Environment Config
=
Runtime
```

따라서 Config도 Evidence 대상이다.

---

# 66. Drift의 정의

## FIG-CL-34. Expected vs Actual

```text
Expected Architecture
      │
      │ compare
      ▼
Actual Source / Config / Runtime
      │
      ├─ MATCH
      └─ DIFFERENCE
             ↓
           DRIFT
```

---

# 67. Drift Type

```text
Document Drift
Model Drift
Source Drift
Config Drift
Deployment Drift
Runtime Drift
Security Drift
Data Drift
```

---

# 68. Document Drift

예:

```text
문서
Filter order = 1

실제
HIGHEST_PRECEDENCE + 20

→ Document Drift
```

---

# 69. Model Drift

```text
Model
ServiceId S0 registered

Source
ServiceId S0 missing

→ Model Drift
```

---

# 70. Config Drift

```text
Design
Session 60m

Actual
90m

→ Config Drift
```

---

# 71. Deployment Drift

```text
Architecture
WAR A → JVM A

Actual
WAR A → JVM B

→ Deployment Drift
```

---

# 72. Runtime Drift

```text
Design
Timeout rollback

Runtime
HTTP timeout + DB commit

→ Critical Runtime Drift
```

---

# 73. Security Drift

```text
Design
JWT principal drives user identity

Runtime
Header optr_eno trusted independently

→ Security Drift
```

---

# 74. Drift Severity

## FIG-CL-35. Severity

```text
CRITICAL
  - Security bypass
  - Data inconsistency
  - Late commit
  - DR impossible

HIGH
  - NFR violation
  - Trace loss
  - Unsupported runtime path

MEDIUM
  - Naming / documentation
  - non-critical config mismatch

LOW
  - cosmetic / metadata
```

---

# 75. GAP의 정의

```text
Required Architecture
      │
      ▼
Evidence?
  ├─ YES → conform / drift
  └─ NO  → GAP
```

또는:

```text
Target
≠
AS-IS
→ GAP
```

---

# 76. GAP vs DRIFT

## FIG-CL-36. Difference

```text
GAP
= 필요한 것이 없거나 미정

DRIFT
= 정한 것과 실제가 다름
```

---

# 77. GAP Register

최소:

```text
Gap ID
Architecture Layer
Expected
Actual
Impact
Risk
Owner
Target Date
ADR
Evidence
Status
```

---

# 78. ADR

## FIG-CL-37. ADR Lifecycle

```text
Issue / GAP / Drift
     ↓
Decision Needed
     ↓
ADR
     │
     ├─ Context
     ├─ Decision
     ├─ Alternatives
     ├─ Consequence
     ├─ Owner
     └─ Date
     ↓
Architecture Update
```

---

# 79. ADR 상태

```text
PROPOSED
ACCEPTED
REJECTED
SUPERSEDED
DEPRECATED
```

---

# 80. ADR Exception

```text
Standard
  ↓
Exception
  ↓
ADR
  ↓
Compensating Control
  ↓
Expiry / Review
```

예외는 영구 방치하지 않는다.

---

# 81. Architecture Gate 전체

## FIG-CL-38. G00 → HG90

```text
G00
Source Baseline
   ↓
G10
Document Classification
   ↓
G20
Architecture Model
   ↓
G30
Model ↔ Code Conformance
   ↓
G40
Architecture / Contract / Security Test
   ↓
G50
Runtime Evidence
   ↓
G60
Drift Detection
   ↓
G70
GAP / ADR
   ↓
G80
Approval
   ↓
HG90
Architecture Baseline Release
```

---

# 82. G00 — Source Baseline

## FIG-CL-39. Gate G00

```text
Repository
  ↓
Branch
  ↓
Commit
  ↓
Scope
  ↓
Source Baseline
```

PASS 조건:

```text
Commit fixed
Module Scope fixed
Config Scope fixed
Evidence source identified
```

---

# 83. G10 — Document Classification

```text
Document Content
  ↓
Classification
```

사용 태그:

```text
[FACT]
[CONFIRMED]
[DECISION]
[AS-IS]
[TO-BE]
[PROPOSED]
[GAP]
[CONFLICT]
[RISK]
[OPEN]
[UNKNOWN]
[DEPRECATED]
```

---

# 84. G20 — Architecture Model

```text
Document
  ↓
Machine-readable Model
  ↓
Entity / Relation / Policy
```

PASS:

```text
ServiceId
Interface
Node
Data
Policy
Trace
```

가 모델링되어야 한다.

---

# 85. G30 — Model ↔ Source

## FIG-CL-40. Conformance Scan

```text
Architecture Model
      │
      │ compare
      ▼
Source / Config
      │
      ▼
Conformance Result
```

---

# 86. G40 — Architecture Test

```text
Rule
  ↓
Test
  ↓
PASS / FAIL
```

Critical Rule FAIL이면 Release 진행 불가.

---

# 87. G50 — Runtime Evidence

## FIG-CL-41. Runtime Gate

```text
Exact Artifact
   ↓
Exact Deployment
   ↓
Scenario
   ↓
Runtime Trace
   ↓
Expected Result
   ↓
Evidence
```

---

# 88. G60 — Drift

```text
Expected
   ↓ compare
Actual
   ↓
Drift
```

Critical Drift는 0을 목표로 한다.

---

# 89. G70 — GAP / ADR

```text
Gap / Drift
   ↓
Fix?
Accept?
Exception?
Change Architecture?
   ↓
ADR
```

---

# 90. G80 — Approval

승인대상:

```text
Architecture Owner
Business Owner
Security
Operations
Data
Project / PMO
```

실제 RACI는 프로젝트 승인체계에 맞춰 확정한다.

---

# 91. HG90 — Baseline Release

## FIG-CL-42. Final Gate

```text
Document
+
Model
+
Source Baseline
+
Rule/Test PASS
+
Runtime Evidence
+
Drift Review
+
ADR
+
Human Approval
        │
        ▼
Architecture Baseline Release
```

---

# 92. HG90 금지

```text
문서 완료만으로 Release       X
테스트 통과만으로 Release     X
Runtime Evidence 없이 Release X
Critical Drift 남은 채 Release X
```

---

# 93. Closed Loop Workspace

## FIG-CL-43. Workspace Structure

```text
00-IN
  │
10-DOCUMENT
  │
20-MODEL
  │
30-CODE / CONFORMANCE
  │
40-TEST
  │
50-RUNTIME-EVIDENCE
  │
60-DRIFT
  │
70-GAP-ADR
  │
80-GATE
  │
90-OUT
```

---

# 94. 00-IN

```text
Source
PPT
Requirements
Design
Config
Runtime Inputs
```

---

# 95. 10-DOCUMENT

```text
CURRENT-ARCHITECTURE.md
Vision
Big Picture
Logical
Physical
Mechanism
Runtime
```

---

# 96. 20-MODEL

```text
reference-baseline.json
serviceid-index.json
interface-index.json
deployment-model.json
```

---

# 97. 30-CODE / CONFORMANCE

```text
reference-rules.json
scanner
source-index
mapper-index
dependency-index
```

---

# 98. 40-TEST

```text
architecture-test
contract-test
security-test
transaction-test
timeout-test
integration-test
```

---

# 99. 50-RUNTIME-EVIDENCE

```text
scenario
deployment
metric
log
trace
test result
evidence manifest
```

---

# 100. 60-DRIFT

```text
document-drift
model-drift
source-drift
config-drift
runtime-drift
```

---

# 101. 70-GAP-ADR

```text
GAP register
Risk register
ADR
Exception
Action
```

---

# 102. 80-GATE

```text
Gate Result
Approval
Waiver
Condition
```

---

# 103. 90-OUT

```text
Released Baseline
Model
Rule
Evidence Index
ADR Pack
```

---

# 104. Evidence Classification

## FIG-CL-44. Evidence Hierarchy

```text
Runtime Evidence
   ↑
Config / Source
   ↑
Official Baseline
   ↑
ADR
   ↑
Detailed Design
   ↑
Requirement / Interview
   ↑
Guide / Draft
```

### 원칙

실제 구현판정은 가능한 한:

```text
Source / Config / Runtime
```

을 우선한다.

---

# 105. Evidence Tagging

```text
[FACT]
실제 Source/Config/Runtime에서 확인

[CONFIRMED]
승인된 Baseline

[DECISION]
공식 결정

[AS-IS]
현재 구현

[TO-BE]
목표

[PROPOSED]
제안

[GAP]
부족/미정

[CONFLICT]
서로 다른 근거

[UNKNOWN]
증거 없음
```

---

# 106. Fact vs Target

## FIG-CL-45. AS-IS / TO-BE Separation

```text
PDMG Source
     │
     ▼
AS-IS
     │
     │ compare
     ▼
NSIGHT Target
     │
     ▼
GAP
```

### 금지

```text
PDMG Source
→ NSIGHT Target 자동 승격
```

---

# 107. Old Baseline Handling

```text
2026-03 Baseline
  ↓
[BASELINE-2026-03]
```

최신값으로 자동 치환하지 않는다.

---

# 108. Conflict Handling

## FIG-CL-46. Conflict Resolution

```text
Source A
CDC <= 30s
      │
      ├─ CONFLICT
      │
Source B
CDC <= 3s
      │
      ▼
Owner / Approval / Measurement Review
      │
      ▼
Decision / ADR
```

---

# 109. Unknown Handling

```text
Evidence 없음
   ↓
UNKNOWN
```

빈칸으로 숨기지 않는다.

---

# 110. Architecture Review Board Input

## FIG-CL-47. Review Package

```text
Architecture Change
      │
      ▼
Review Pack
├─ Context
├─ Diagram
├─ Model Diff
├─ Rule Impact
├─ Risk
├─ Test
├─ Runtime Evidence
└─ ADR
```

---

# 111. Change Impact Trace

```text
Change
  ↓
ServiceId
  ↓
Handler / Service
  ↓
Interface
  ↓
SQL / Table
  ↓
Artifact
  ↓
Deployment
  ↓
Test
```

---

# 112. ServiceId Change Impact

## FIG-CL-48. Service Change

```text
ServiceId Change
   │
   ├─ UI Catalog
   ├─ Handler Registry
   ├─ Header
   ├─ Logging
   ├─ Interface
   ├─ Test
   └─ Monitoring
```

---

# 113. Table Change Impact

```text
Table Change
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
```

---

# 114. Interface Change Impact

```text
Interface Contract
  ↓
Producer
  ↓
Consumer
  ↓
Security
  ↓
Timeout
  ↓
Monitoring
  ↓
Recovery
```

---

# 115. Deployment Change Impact

```text
WAR / JVM Change
   ↓
Host
   ↓
Capacity
   ↓
HA
   ↓
Monitoring
   ↓
DR
```

---

# 116. Runtime Drift Alert

## FIG-CL-49. Automated Drift

```text
Runtime Metric / Config
       │
       ▼
Expected Baseline
       │
       ▼ compare
       │
       ├─ match
       └─ drift
            ↓
          Alert
```

---

# 117. Configuration Drift Examples

```text
Session 60m → Actual 90m
Timeout 4s → Actual 5s
Hikari 80 → Actual 200
Filter Order changed
JWT Key Source changed
```

---

# 118. Architecture Dashboard

## FIG-CL-50. Governance Dashboard

```text
Architecture Health
│
├─ Rule Pass %
├─ Critical Drift
├─ Open GAP
├─ ADR Pending
├─ Runtime Evidence Coverage
├─ ServiceId Trace Coverage
├─ Interface Trace Coverage
└─ Deployment Trace Coverage
```

---

# 119. Coverage Metrics

```text
ServiceId Trace Coverage
= traced ServiceIds / total ServiceIds

Runtime Evidence Coverage
= evidenced critical scenarios / required scenarios

Deployment Trace Coverage
= mapped artifacts / deployed artifacts
```

---

# 120. Quality Gate Metrics

```text
Critical Rule Fail = 0

Critical Drift = 0

Unknown Critical Node = 0 target

Runtime Evidence Coverage
= 100% for critical services
```

---

# 121. PDMG Current Traceability Gaps

## FIG-CL-51. PDMG Gap Map

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
Mapper
  ↓
SQL
  ↓
Table
  ↓
Artifact
  ↓
Deployment
  ↓
Runtime Evidence
```

현재 후반부:

```text
Table → Artifact → Deployment → Evidence
```

전수 자동화가 주요 과제다.

---

# 122. ServiceId Full Trace Target

## FIG-CL-52. Target Trace

```text
Requirement
  ↓
Application
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
Table / View
  ↓
WAR
  ↓
JVM
  ↓
Host
  ↓
GUID
  ↓
Runtime Evidence
```

---

# 123. Runtime Evidence → ServiceId

```text
GUID
  ↓
ServiceId
  ↓
Deployment
  ↓
Artifact
  ↓
Commit
```

---

# 124. Runtime Evidence → Architecture

```text
Runtime Evidence
  ↑
Scenario
  ↑
NFR
  ↑
Architecture Principle
```

---

# 125. Failure Evidence Trace

## FIG-CL-53. Failure Proof

```text
Failure Scenario
  ↓
Trigger
  ↓
Detection
  ↓
Failover / Recovery
  ↓
SLO
  ↓
Evidence
```

---

# 126. DR Evidence Trace

```text
DR Scenario
  ↓
Main Failure
  ↓
GSLB Route
  ↓
DR App
  ↓
DR DB
  ↓
Business Validation
  ↓
RTO/RPO Evidence
```

---

# 127. Backup Evidence Trace

```text
Backup
  ↓
Restore
  ↓
Application Start
  ↓
Data Validation
  ↓
Evidence
```

---

# 128. Security Evidence Trace

```text
Token
  ↓
Verify
  ↓
Principal
  ↓
Authorization
  ↓
Business
  ↓
Audit
```

---

# 129. Performance Evidence Trace

```text
Load Scenario
  ↓
TPS
  ↓
p95
  ↓
Thread
  ↓
Worker
  ↓
Hikari
  ↓
DB
```

---

# 130. Evidence Naming

권장 패턴:

```text
EV-{Scenario}-{Date}-{Sequence}
```

실제 최종 Naming은 프로젝트 표준으로 승인한다.

---

# 131. Evidence Storage

```text
Evidence Repository
│
├─ Runtime
├─ Performance
├─ Security
├─ HA
├─ DR
├─ Backup
└─ Screenshots / Reports
```

---

# 132. Evidence Integrity

## FIG-CL-54. Evidence Hash

```text
Evidence File
   ↓
Hash
   ↓
Manifest
   ↓
Release Record
```

---

# 133. Architecture Baseline Package

## FIG-CL-55. Baseline Release Pack

```text
Architecture Baseline
│
├─ 01 VISION
├─ 02 BIG PICTURE
├─ 03 LOGICAL
├─ 04 PHYSICAL
├─ 05 MECHANISM
├─ 06 RUNTIME
├─ 07 TRACEABILITY / CLOSED LOOP
├─ Machine-readable Model
├─ Rules
├─ Test Result
├─ Evidence Index
├─ Drift Report
├─ GAP Register
└─ ADR Pack
```

---

# 134. Baseline Version

```text
Architecture Baseline
= Versioned

Example
v1.0
v1.1
v2.0
```

Version Rule은 별도 Change Management 기준으로 확정한다.

---

# 135. Baseline Diff

## FIG-CL-56. Baseline Change

```text
Baseline N
   │
   ▼
Change
   │
   ▼
Model Diff
   │
   ▼
Rule / Test
   │
   ▼
Runtime Evidence
   │
   ▼
Baseline N+1
```

---

# 136. Architecture Release Candidate

```text
RC
│
├─ Document Complete
├─ Model Complete
├─ Rule Pass
├─ Test Pass
├─ Evidence Complete
├─ Drift Reviewed
└─ ADR Approved
```

---

# 137. Architecture Release

```text
RC
  ↓
Human Approval
  ↓
HG90
  ↓
Released Baseline
```

---

# 138. Release 후 Drift

```text
Released Baseline
  ↓
Runtime Change
  ↓
Drift Detection
  ↓
Change Review
```

Architecture는 Release 후에도 계속 감시한다.

---

# 139. Operational Architecture

## FIG-CL-57. Architecture as Operation

```text
Architecture
  ↓
Rule
  ↓
CI
  ↓
Deployment
  ↓
Monitoring
  ↓
Drift
  ↓
Change
```

---

# 140. Architecture as Code

```text
Architecture Rule
+
Scanner
+
CI Gate
```

---

# 141. Architecture as Data

```text
Architecture Model
+
Inventory
+
Trace Index
```

---

# 142. Architecture as Evidence

```text
Runtime Evidence
+
Deployment Evidence
+
Test Evidence
```

---

# 143. Architecture as Governance

```text
Gate
+
ADR
+
Approval
```

---

# 144. End-to-End Governance Chain

## FIG-CL-58. Governance Chain

```text
Requirement
  ↓
Decision
  ↓
Rule
  ↓
Implementation
  ↓
Test
  ↓
Runtime
  ↓
Evidence
  ↓
Drift
  ↓
ADR
```

---

# 145. Architecture Owner Responsibility

```text
Define
Approve
Review
Measure
Resolve Drift
Maintain Baseline
```

---

# 146. Developer Responsibility

```text
Follow Rule
Maintain Trace
Write Test
Avoid Unauthorized Exception
```

---

# 147. Operations Responsibility

```text
Maintain Runtime Evidence
Detect Drift
Execute Runbook
Feed Incident Back to Architecture
```

---

# 148. Security Responsibility

```text
Security Rule
Key/Secret
Identity
Authorization
Audit
Security Evidence
```

---

# 149. Data Responsibility

```text
Data Ownership
SQL/Table Trace
Quality
Lineage
CDC/ETL Evidence
```

---

# 150. PMO / Governance Responsibility

```text
Gate
Approval
Status
Risk
Baseline Release
```

---

# 151. RACI 후보

## TEXT ARCHITECTURE 보완 — RACI 후보

```text
Activity
  ↓
Accountable
  ↓
Responsible
  ↓
Consulted
  ↓
Informed
  ↓
Approval / Evidence
```


| Activity | Architect | Dev | Ops | Security | Data | PMO |
|---|---|---|---|---|---|---|
| Architecture Rule | A/R | C | C | C | C | I |
| Source Conformance | A | R | I | C | C | I |
| Runtime Evidence | A | C | R | C | C | I |
| Security Evidence | C | C | C | A/R | I | I |
| Data Trace | C | C | I | I | A/R | I |
| Baseline Release | A/R | C | C | C | C | A/C |

실제 조직 기준으로 재조정한다.

---

# 152. Audit Trail

## FIG-CL-59. Architecture Audit

```text
Who changed?
What changed?
Why changed?
Which ADR?
Which Source Commit?
Which Artifact?
Which Deployment?
Which Evidence?
```

---

# 153. Exception Audit

```text
Exception
  ↓
ADR
  ↓
Owner
  ↓
Expiry
  ↓
Review
```

---

# 154. Architecture Debt

## FIG-CL-60. Debt Lifecycle

```text
Accepted GAP
  ↓
Architecture Debt
  ↓
Owner
  ↓
Due Date
  ↓
Risk
  ↓
Close / Extend
```

---

# 155. Unknown Debt

```text
UNKNOWN
  ↓
Evidence Collection Task
```

UNKNOWN을 장기 방치하지 않는다.

---

# 156. Drift Debt

```text
Known Drift
  ↓
Accepted temporarily
  ↓
ADR / Debt
```

---

# 157. Architecture KPI

```text
Critical Drift Count
Open Critical GAP
Rule Pass Rate
Evidence Coverage
Trace Coverage
ADR Lead Time
Recovery Test Coverage
```

---

# 158. Architecture Dashboard Sample

## FIG-CL-61. Health Dashboard

```text
Architecture Health
┌─────────────────────────────────────┐
│ Critical Drift          0           │
│ Critical GAP            2           │
│ Rule Pass               97%         │
│ Service Trace           85%         │
│ Runtime Evidence        70%         │
│ DR Test Coverage        60%         │
└─────────────────────────────────────┘
```

수치는 예시 구조이며 실제값이 아니다.

---

# 159. PDMG Closed Loop Example

## FIG-CL-62. ServiceId Example

```text
mgcoa9001S0
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
SQL
   ↓
RDW Table
   ↓
WAR
   ↓
JVM
   ↓
Runtime GUID
   ↓
Evidence
```

---

# 160. Timeout Closed Loop Example

## FIG-CL-63. Timeout Example

```text
Architecture Rule
DB Query < Worker < Client
       ↓
Config Scan
       ↓
PDMG Worker = 5000ms
Query Timeout = UNKNOWN
       ↓
GAP
       ↓
Integration Test
       ↓
ADR / Baseline
```

---

# 161. Security Closed Loop Example

## FIG-CL-64. Identity Binding

```text
Target
JWT Principal → Business Identity
       ↓
Source
ssoId / optr_eno mapping unclear
       ↓
GAP
       ↓
Security Test
       ↓
ADR
```

---

# 162. DR Closed Loop Example

## FIG-CL-65. DR

```text
Target
RTO/RPO
  ↓
Physical
Main / DR
  ↓
Runtime
Failover Test
  ↓
Evidence
  ↓
PASS / GAP
```

---

# 163. CDC Closed Loop Example

## FIG-CL-66. CDC SLA

```text
Baseline A 30s
Baseline B 3s
       ↓
CONFLICT
       ↓
Measurement Definition
       ↓
Runtime Test
       ↓
Owner Decision
       ↓
ADR / New Baseline
```

---

# 164. Architecture Completion Definition

## FIG-CL-67. What "Complete" Means

```text
Document complete?
        │
        ▼
Model complete?
        │
        ▼
Source conformance?
        │
        ▼
Test pass?
        │
        ▼
Runtime evidence?
        │
        ▼
Drift reviewed?
        │
        ▼
ADR approved?
        │
        ▼
Baseline released?
```

---

# 165. 문서 완료와 Architecture 완료의 차이

```text
문서가 있음
=
Documentation Complete

문서 + Model + Code + Test + Runtime + Evidence + Gate
=
Architecture Complete
```

---

# 166. Architecture Never Final

```text
Baseline
  ↓
Change
  ↓
Drift
  ↓
Review
  ↓
New Baseline
```

Architecture는 정적 산출물이 아니라 지속적으로 운영되는 통제체계다.

---

# 167. CONFIRMED / WORKING BASELINE

```text
[CONFIRMED / WORKING BASELINE]

- Top-down Design → Bottom-up Evidence 구조
- ServiceId가 PDMG Runtime Trace 핵심축
- Document / Model / Code / Test / Runtime / Drift / ADR Closed Loop
- G00→G10→G20→G30→G40→G50→G60→G70→G80→HG90 Gate
- Source Commit / Artifact / Deployment / Runtime Evidence 연결 필요
- Architecture Rule을 CI/Test로 검증
- AS-IS / TO-BE / GAP / DRIFT 구분
```

---

# 168. OPEN

```text
[OPEN-CL-01]
Architecture Model 최종 Schema

[OPEN-CL-02]
ServiceId 전수 Source Index

[OPEN-CL-03]
Mapper/SQL/Table 자동 Parser

[OPEN-CL-04]
Artifact→Deployment 전수 Mapping

[OPEN-CL-05]
Runtime Evidence Repository

[OPEN-CL-06]
Evidence Hash / Integrity 표준

[OPEN-CL-07]
Gate Owner / Approval RACI

[OPEN-CL-08]
Architecture KPI 실제 목표값

[OPEN-CL-09]
Architecture Dashboard Tool

[OPEN-CL-10]
Baseline Versioning Rule
```

---

# 169. GAP Register

## TEXT ARCHITECTURE 보완 — GAP Register

```text
Expected / Required
      │
      ▼
Actual / Evidence
      │
      ▼
Difference
      │
      ▼
[GAP]
      │
      ├─ Owner
      ├─ Action
      ├─ ADR
      └─ Evidence
```


| ID | GAP | 영향 |
|---|---|---|
| GAP-CL-01 | ServiceId 전수 인덱스 자동화 미완료 | Traceability |
| GAP-CL-02 | Handler→SQL→Table 자동 Trace 미완료 | Data Trace |
| GAP-CL-03 | Artifact→Host/JVM Mapping 미완료 | Deployment |
| GAP-CL-04 | Runtime Evidence Manifest 자동화 미완료 | Evidence |
| GAP-CL-05 | Architecture Model Schema 미확정 | Model |
| GAP-CL-06 | CI Architecture Rule 전수적용 미완료 | Governance |
| GAP-CL-07 | Runtime Drift 자동탐지 미완료 | Drift |
| GAP-CL-08 | Gate RACI 미확정 | Approval |
| GAP-CL-09 | Evidence Repository 미확정 | Evidence |
| GAP-CL-10 | Architecture KPI 미확정 | Governance |
| GAP-CL-11 | Baseline Release Automation 미완료 | Release |
| GAP-CL-12 | Critical Service Evidence Coverage 미확정 | Runtime |

---

# 170. RISK Register

## TEXT ARCHITECTURE 보완 — RISK Register

```text
Cause
  ↓
Risk Event
  ↓
Business / Data / Security / Operation Impact
  ↓
Severity
  ↓
Mitigation / Control
  ↓
Owner
  ↓
Evidence / Closure
```


| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-CL-01 | 문서만 업데이트되고 Source는 Drift | Critical |
| RISK-CL-02 | Source만 변경되고 Architecture 미갱신 | Critical |
| RISK-CL-03 | ServiceId SSOT 불일치 | High |
| RISK-CL-04 | SQL/Table Trace 누락 | High |
| RISK-CL-05 | Artifact/Deployment 추적 불가 | Critical |
| RISK-CL-06 | Runtime Evidence가 Release와 분리 | Critical |
| RISK-CL-07 | Critical Drift 미탐지 | Critical |
| RISK-CL-08 | Exception ADR 만료관리 부재 | High |
| RISK-CL-09 | UNKNOWN 장기 방치 | High |
| RISK-CL-10 | Gate가 형식승인으로 전락 | Critical |

---

# 171. ADR 후보

```text
ADR-CL-01 Architecture Model Schema
ADR-CL-02 ServiceId SSOT
ADR-CL-03 Traceability Index
ADR-CL-04 SQL/Table Parser
ADR-CL-05 Artifact/Deployment Manifest
ADR-CL-06 Runtime Evidence Manifest
ADR-CL-07 Architecture Rule Engine
ADR-CL-08 Drift Detection
ADR-CL-09 Gate RACI
ADR-CL-10 Baseline Versioning
ADR-CL-11 Evidence Repository
ADR-CL-12 Architecture Dashboard
```

---

# 172. Verification Checklist — Traceability

```text
[ ] Requirement→Architecture?
[ ] Architecture→ServiceId?
[ ] ServiceId→Handler?
[ ] Handler→Service?
[ ] Service→DAO?
[ ] DAO→Mapper?
[ ] Mapper→SQL?
[ ] SQL→Table?
[ ] Artifact→Deployment?
[ ] Deployment→Runtime Evidence?
```

---

# 173. Verification Checklist — Model

```text
[ ] Entity 정의?
[ ] Relation 정의?
[ ] ServiceId 포함?
[ ] Interface 포함?
[ ] Data 포함?
[ ] Policy 포함?
[ ] Evidence Link 포함?
```

---

# 174. Verification Checklist — Rule

```text
[ ] Naming Rule?
[ ] Dependency Rule?
[ ] ServiceId Rule?
[ ] TX Rule?
[ ] Timeout Rule?
[ ] Security Rule?
[ ] Logging Rule?
[ ] Deployment Rule?
[ ] Runtime Evidence Rule?
```

---

# 175. Verification Checklist — Evidence

```text
[ ] Source Commit?
[ ] Artifact Hash?
[ ] Deployment ID?
[ ] Environment?
[ ] Scenario?
[ ] ServiceId?
[ ] TraceId?
[ ] Metric?
[ ] Result?
[ ] Evidence Hash?
```

---

# 176. Verification Checklist — Drift

```text
[ ] Document Drift?
[ ] Model Drift?
[ ] Source Drift?
[ ] Config Drift?
[ ] Deployment Drift?
[ ] Runtime Drift?
[ ] Security Drift?
```

---

# 177. Verification Checklist — Gate

```text
[ ] G00 Source fixed?
[ ] G10 Document classified?
[ ] G20 Model complete?
[ ] G30 Conformance?
[ ] G40 Test?
[ ] G50 Runtime Evidence?
[ ] G60 Drift?
[ ] G70 GAP/ADR?
[ ] G80 Approval?
[ ] HG90 Release?
```

---

# 178. Closed Loop Completion Gate

## FIG-CL-68. Final Closed Loop Gate

```text
Document?
  ↓ YES
Model?
  ↓ YES
Source Baseline?
  ↓ YES
Rules?
  ↓ YES
Tests?
  ↓ YES
Runtime Evidence?
  ↓ YES
Critical Drift = 0?
  ↓ YES
Critical GAP resolved/approved?
  ↓ YES
ADR complete?
  ↓ YES
Human approval?
  ↓ YES
HG90 BASELINE RELEASE
```

---

# 179. 전체 Architecture 7단계

## FIG-CL-69. End-to-End Architecture

```text
01 VISION
   왜 바꾸는가?
      ↓
02 BIG PICTURE
   누가 무엇을 책임하는가?
      ↓
03 LOGICAL
   어떤 논리구조로 분리하는가?
      ↓
04 PHYSICAL
   어디에 배치하는가?
      ↓
05 MECHANISM
   어떤 규칙으로 동작하는가?
      ↓
06 RUNTIME
   실제로 어떻게 실행되는가?
      ↓
07 TRACEABILITY / CLOSED LOOP
   설계대로 동작하는 것을 어떻게 증명하고 유지하는가?
```

---

# 180. 최종 Architecture 왕복 구조

## FIG-CL-70. Top-down + Bottom-up

```text
                 TOP-DOWN DESIGN

VISION
  ↓
BIG PICTURE
  ↓
LOGICAL
  ↓
PHYSICAL
  ↓
MECHANISM
  ↓
RUNTIME
  ↓
────────────────────────────
       RUNTIME EVIDENCE
────────────────────────────
  ↑
SOURCE / CONFIG
  ↑
MODEL
  ↑
DRIFT / GAP
  ↑
ADR
  ↑
NEW BASELINE

             BOTTOM-UP VERIFICATION
```

---

# 보완검토 A. CLOSED LOOP 시각화 보강

## FIG-CL-SUP-01. RACI → Gate

```text
Architecture Rule / Change
       ↓
Responsible
       ↓
Accountable
       ↓
Consulted
       ↓
Evidence
       ↓
G80 Approval
```

---

## FIG-CL-SUP-02. Evidence Coverage

```text
ServiceId
  ↓
Source Trace
  ↓
Deployment Trace
  ↓
Runtime Scenario
  ↓
Evidence
  ↓
Coverage %
```

---

## FIG-CL-SUP-03. UNKNOWN 처리

```text
UNKNOWN
   ↓
Evidence Collection Task
   ↓
FACT / GAP / CONFLICT
   ↓
Owner Decision
```

---

## FIG-CL-SUP-04. Closed Loop Exit Condition

```text
Critical Rule Fail = 0
       +
Critical Drift = 0
       +
Critical Evidence Missing = 0
       +
Approved GAP / ADR
       ↓
HG90 Candidate
```

---

# 181. Definition of Done

## TEXT ARCHITECTURE 보완 — Definition of Done

```text
Architecture Inputs
      ↓
Structure / Contract / Runtime Check
      ↓
Evidence Check
      ↓
GAP / CONFLICT / UNKNOWN Review
      ↓
PASS Condition
      │
      ├─ satisfied → PASS
      └─ pending   → CONDITIONAL PASS
```


## Traceability
- [x] Forward Trace 정의
- [x] Reverse Trace 정의
- [x] ServiceId 중심 Trace 정의
- [x] Requirement→Runtime 연결
- [x] SQL/Table Trace 정의

## Model
- [x] Entity 정의
- [x] Relation 정의
- [x] Design/Runtime Graph 구분
- [x] Policy 연결
- [x] Evidence Link 구조 정의

## Architecture as Code
- [x] Naming Rule
- [x] ServiceId Rule
- [x] Handler/Layer Rule
- [x] TX/Timeout Rule
- [x] Security/Logging Rule
- [x] Deployment/Evidence Rule

## Test
- [x] Static
- [x] Architecture
- [x] Contract
- [x] Integration
- [x] Security
- [x] TX/Timeout
- [x] Performance/Failure

## Evidence
- [x] Source→Artifact→Deployment→Runtime Chain
- [x] Evidence Manifest
- [x] Evidence Hash
- [x] Release Trace
- [x] Failure/DR/Security/Performance Evidence

## Drift / GAP / ADR
- [x] Drift Type
- [x] Severity
- [x] GAP vs DRIFT
- [x] ADR Lifecycle
- [x] Architecture Debt

## Gate
- [x] G00~HG90
- [x] Workspace 00-IN~90-OUT
- [x] Completion Gate
- [x] Human Approval
- [x] Baseline Release

**TRACEABILITY / CLOSED LOOP 장 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. ServiceId 전수 자동 Index 생성
2. Handler→Mapper→SQL→Table 자동 Trace
3. Artifact→Deployment Manifest 생성
4. Runtime Evidence Manifest 자동화
5. Architecture Model Schema 확정
6. Architecture Rule CI 적용
7. Critical Drift 자동탐지
8. Gate RACI 승인
9. Evidence Repository 확정
10. Baseline Versioning / Release Rule 확정
11. Critical Runtime Evidence Coverage 100%
12. HG90 실제 Release 절차 검증

---

# 182. 다음 단계

이 장 이후에는 두 방향으로 확장할 수 있다.

```text
A. 08. PDMG SOURCE / RUNTIME REFERENCE DEEP DIVE
   - pdmg-ui
   - pdmg-jwt
   - pdmg-fw
   - pdmg-service
   - pdmg-om
   - ServiceId
   - Source/Class/Config

B. 08. STANDARD / DEVOPS / OM / OBSERVABILITY
   - CI/CD
   - OM
   - Dashboard
   - Runtime Operations
   - Architecture Gate Automation
```

현재 01~07은 **NSIGHT Target Architecture의 Top-down 설명과 Bottom-up 검증 체계**를 하나의 Closed Loop로 완결한 상태다.

---


====================================================================================================

# CHAPTER 08

====================================================================================================

# NSIGHT / PDMG 아키텍처 정의서
# 08. PDMG SOURCE / RUNTIME REFERENCE DEEP DIVE

> 보완개정: **v2 — TEXT Architecture Figure Coverage / Reference Coverage / Evidence Consistency 재점검**
## TEXT ARCHITECTURE 보완 — 장 전체 위치

```text
Module
   ↓
Package
   ↓
ServiceId
   ↓
Class
   ↓
Config
   ↓
Runtime
   ↓
Evidence
   ↓
NSIGHT Alignment
```

## Visual-First / Bottom-up Evidence Drill-down 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-VISUAL-PDMG-REFERENCE-08`  
> Architecture Level: **L7 — PDMG SOURCE / RUNTIME REFERENCE**  
> 문서 상태: **Draft / AS-IS Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_07_TRACEABILITY_EVIDENCE_CLOSED_LOOP_VISUAL_FIRST_TOPDOWN_상세본.md`  
> 본 장 역할: **NSIGHT Target을 PDMG Source/Config/Runtime으로 Bottom-up 검증하는 Reference 장**

---

# 0. 이 장을 읽는 방법

1~7장은 NSIGHT Target을 위에서 아래로 설명했다.

8장은 반대로 실제 PDMG를 아래에서 위로 읽는다.

```text
SOURCE
  ↓
MODULE
  ↓
PACKAGE
  ↓
CLASS
  ↓
SERVICE ID
  ↓
RUNTIME
  ↓
CONFIG
  ↓
DB / SQL
  ↓
SECURITY / LOG
  ↓
EVIDENCE
  ↓
NSIGHT TARGET과 비교
```

이 장에서 가장 중요한 원칙은 다음이다.

```text
PDMG에서 실제로 구현됨
       │
       ▼
[AS-IS]

NSIGHT에서 목표로 함
       │
       ▼
[TO-BE]

둘이 다름
       │
       ▼
[GAP]
```

---

# 1. VISUAL ROUTE — PDMG Reference 전체를 한 장으로 보기

## FIG-PDMG-01. PDMG Source-to-Runtime Journey

```text
╔══════════════════════════════════════════════════════════════════════════════╗
║                       PDMG SOURCE / RUNTIME REFERENCE                       ║
╚══════════════════════════════════════════════════════════════════════════════╝

 ① MODULE
    pdmg-ui / pdmg-jwt / pdmg-fw / pdmg-service / pdmg-om
        │
        ▼
 ② BOUNDARY
    Build Module ≠ Runtime Process ≠ Spring Context
        │
        ▼
 ③ PACKAGE
    nhnis.mg.ui / nhnis.mg.jw.a / nhnis.fw / nhnis.mg.co.a
        │
        ▼
 ④ SERVICE ID
    mgcoa.... → Handler Registry
        │
        ▼
 ⑤ BUSINESS SOURCE
    Handler → Facade → Service → DAO → Mapper XML → SQL
        │
        ▼
 ⑥ FRAMEWORK SOURCE
    Filter → Context → MVC → TCF → Timeout → TX → Error
        │
        ▼
 ⑦ SECURITY
    Login / JWT / Refresh / SSO / JWKS / Principal
        │
        ▼
 ⑧ OPERATIONS
    GUID / MDC / ImageLog / Error / Runtime Trace
        │
        ▼
 ⑨ EVIDENCE
    Source / Config / Runtime
        │
        ▼
 ⑩ ALIGNMENT
    PDMG AS-IS ↔ NSIGHT TO-BE → CONFORM / DRIFT / GAP / ADR
```

---

# 2. Evidence Register

본 장은 다음 Source 분석 문서를 우선 근거로 사용한다.

```text
01장.PDMG_시스템_개요_ASCII_확장본.md
05장.네이밍_규칙_ASCII_확장본.md
06장.패키지와_프로젝트_구조_ASCII_확장본.md
09장.Filter와 Spring MVC.md
11장.ServiceContext와 GUID.md
14장.Handler와 Controller.md
24장.JWT 인증 전체 구조.md

NSIGHT_PDMG_아키텍처_정의서_III_PDMG_Module_Application_Architecture.md
NSIGHT_PDMG_아키텍처_정의서_IV_PDMG_Online_Runtime_TCF_Flow.md
NSIGHT_PDMG_아키텍처_정의서_VI_Standard_Message_Context_Error_Logging.md
NSIGHT_PDMG_아키텍처_정의서_X_Naming_ServiceId_Traceability_Closed_Loop.md
```

## Evidence 우선순위

```text
Source / Config / Runtime
        >
Current Source Analysis
        >
Official Architecture Baseline
        >
Detailed Design
        >
Past Draft
```

---

# 3. PDMG는 무엇인가

## FIG-PDMG-02. PDMG 한 문장 정의

```text
온라인 요청
    ↓
표준 진입
Header / GUID / JWT / Context
    ↓
거래 식별
ServiceId / TCF
    ↓
실행 제어
Timeout / Transaction
    ↓
업무 실행
Handler / Facade / Service
    ↓
데이터 접근
DAO / Mapper / DB
    ↓
표준 응답 / 운영 추적
Response / Error / ImageLog
```

> **PDMG는 온라인 요청을 표준 계약으로 받아 거래를 식별하고, 공통 실행제어 아래 업무를 수행한 후, 표준 응답과 Runtime Evidence를 남기는 Reference 구현이다.**

---

# 4. PDMG 5-Module Baseline

## FIG-PDMG-03. Five Module Map

```text
┌──────────────────────────── PDMG ─────────────────────────────┐
│                                                              │
│  pdmg-ui                                                     │
│  화면 / Browser 접점                                         │
│      │                                                       │
│      ▼                                                       │
│  pdmg-jwt                                                    │
│  인증 / Token / JWKS                                         │
│                                                              │
│  pdmg-service                                                │
│  ┌────────────────────────────────────────────────────────┐  │
│  │ pdmg-fw                                                │  │
│  │ Filter / Context / TCF / Timeout / Error              │  │
│  └──────────────────────┬─────────────────────────────────┘  │
│                         ▼                                    │
│  Handler / Facade / Service / DAO / Mapper                  │
│                                                              │
│  pdmg-om                                                     │
│  [CURRENT IMPLEMENTATION SCOPE UNKNOWN]                      │
│                                                              │
└──────────────────────────────────────────────────────────────┘
```

---

# 5. 현재 Source Evidence 수준

## TEXT ARCHITECTURE 보완 — 현재 Source Evidence 수준

```text
PDMG Module
   ↓
Source / Config Evidence
   ↓
Runtime Path Evidence
   ↓
Deployment Evidence
   ↓
[AS-IS] / [UNKNOWN] / [GAP]
```


| Module | Current Source Evidence | 상태 |
|---|---|---|
| `pdmg-ui` | UI/Static/Relay/ServiceId 호출 구조 | `[AS-IS]` |
| `pdmg-jwt` | Login/Token/JWKS/Refresh/SSO Source | `[AS-IS]` |
| `pdmg-fw` | Filter/Context/TCF/Timeout/MVC/Error | `[AS-IS]` |
| `pdmg-service` | Handler/Facade/Service/DAO/Mapper | `[AS-IS]` |
| `pdmg-om` | 상세 Source/Package/Runtime Evidence 부족 | `[UNKNOWN]` |

---

# 6. 세 가지 경계를 절대 혼동하지 않는다

## FIG-PDMG-04. Boundary Separation

```text
[1] PROCESS BOUNDARY

Browser
  ├────HTTP────► pdmg-ui
  ├────HTTP────► pdmg-service
  └────HTTP────► pdmg-jwt


[2] MODULE / SPRING BOUNDARY

pdmg-service Runtime
    └─ pdmg-fw
       Bean / Filter / Interceptor / AOP


[3] DATA BOUNDARY

pdmg-service
    └── MyBatis / JDBC ──► RDW / DB
```

---

# 7. 핵심 Architecture Fact

```text
pdmg-fw
= 별도 Build Module

BUT

pdmg-fw
≠ 별도 Remote Business Server
```

현재 Online Runtime에서 Framework Bean은 `pdmg-service`와 같은 Spring ApplicationContext에서 동작할 수 있다.

---

# 8. Module Boundary ≠ Process Boundary

## FIG-PDMG-05. Boundary Equation

```text
Build Module
pdmg-fw
      │
      │ ≠
      ▼
Runtime Process
Tomcat / Spring Boot
      │
      │ ≠
      ▼
Logical Node
Marketing Service
```

---

# 9. Spring ApplicationContext

## FIG-PDMG-06. Shared Spring Context

```text
┌──────────────── Spring ApplicationContext ────────────────┐
│                                                          │
│ scanBasePackages = "nhnis"                               │
│                                                          │
│  nhnis.fw.*                                              │
│  ├─ Filter                                               │
│  ├─ Context                                              │
│  ├─ TCF                                                  │
│  └─ Common                                               │
│                                                          │
│  nhnis.mg.*                                              │
│  ├─ Business                                             │
│  ├─ JWT                                                  │
│  └─ Application Components                               │
│                                                          │
└──────────────────────────────────────────────────────────┘
```

### 핵심

`pdmg-service`와 `pdmg-fw`는 책임은 분리하지만 같은 ApplicationContext에서 협력할 수 있다.

---

# 10. pdmg-ui 책임

## FIG-PDMG-07. UI Role

```text
사용자
  │
  ▼
pdmg-ui
  │
  ├─ 화면
  ├─ 거래 선택
  ├─ ServiceId
  ├─ 요청 JSON
  └─ Authorization 전달
  │
  ▼
pdmg-service
```

---

# 11. pdmg-ui의 일반 직접호출

## FIG-PDMG-08. Direct Browser Path

```text
Browser
   │
   │ HTTP / JSON
   ▼
pdmg-service
```

이 경로에서는:

```text
CORS
Authorization Header
Service URL
JWT
```

가 실제 장애지점이 된다.

---

# 12. pdmg-ui Relay 호환 경로

## FIG-PDMG-09. Relay Path

```text
Browser
   ↓
pdmg-ui
   ↓
/api/relay/{serviceId}
   ↓
TransactionRelayService
   ↓
pdmg-service
```

### 중요

```text
Relay Path 존재
≠
Relay가 PDMG의 유일 호출경로
```

---

# 13. Direct vs Relay

## TEXT ARCHITECTURE 보완 — Direct vs Relay

```text
Browser
  │
  ├─ Direct ─────────────► pdmg-service
  │
  └─ Relay
       ↓
     pdmg-ui
       ↓
     /api/relay/{serviceId}
       ↓
     pdmg-service
          │
          ▼
     Same Business Runtime
```


| 항목 | Direct | Relay |
|---|---|---|
| Browser→Service | 직접 | UI Server 경유 |
| Network Hop | 적음 | 1개 증가 |
| CORS | 중요 | UI→Service Server-side 가능 |
| 장애지점 | Browser/Service | Browser/UI/Service |
| Business Runtime | 동일 | 동일 |

---

# 14. UI Static Program Structure

## FIG-PDMG-10. UI Program Folders

```text
resources/static
├─ _shared
├─ mgcoa5530
├─ mgcoa8888
├─ mgcoa9000
├─ mgcoa9001
├─ mgcoa9100
└─ mgcoa9999
```

### 주의

```text
static/mgcoa9000
≠
Java package nhnis.mg.co.a...
```

하지만 둘은 ServiceId를 통해 업무축으로 연결되어야 한다.

---

# 15. UI → ServiceId → Backend

## FIG-PDMG-11. UI Trace

```text
static/mgcoa9000
     │
     ▼
ServiceId
mgcoa9000S0
     │
     ▼
Backend Handler
mgcoa9000Handler
```

---

# 16. pdmg-service 책임

## FIG-PDMG-12. Business Module

```text
pdmg-service
│
├─ Entry Handler
├─ Business Controller
├─ Facade
├─ Service
├─ DTO
├─ DAO
├─ Mapper Resource
├─ BizPrePostAspect
└─ Business Configuration
```

---

# 17. pdmg-service 업무 Root

```text
nhnis.mg.co.a
```

현재 대표 구조:

```text
nhnis.mg.co.a
├─ entry
├─ application
├─ dto
└─ persistence
```

---

# 18. pdmg-service Package Map

## FIG-PDMG-13. Business Package

```text
nhnis.mg.co.a
│
├─ entry
│  └─ handler
│
├─ application
│  ├─ controller
│  ├─ facade
│  └─ service
│
├─ dto
│
└─ persistence
   └─ dao
```

---

# 19. 현재 일반 Rule Layer 판정

현재 대표 업무경로에서:

```text
application.rule
```

이 일반화된 AS-IS 계층이라고 확인되지 않는다.

따라서:

```text
Handler → Facade → Service → Rule → DAO
```

를 현재 전체 Source 경로로 단정하지 않는다.

### 표현

```text
[AS-IS]
Handler → Facade → Service → DAO

[TO-BE 후보]
Service → Rule → DAO
```

---

# 20. Program `mgcoa9000` Source Drill-down

## FIG-PDMG-14. Program Source Tree

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
├─ dto
│   ├─ mgcoa9000S0DTOin.java
│   ├─ mgcoa9000S0DTOout.java
│   ├─ mgcoa9000C0DTOin.java
│   ├─ mgcoa9000U0DTOin.java
│   └─ mgcoa9000D0DTOin.java
│
└─ persistence/dao
    └─ mgcoa9000DAO.java
```

---

# 21. Mapper Resource

## FIG-PDMG-15. Java ↔ Mapper

```text
Java
nhnis/mg/co/a/persistence/dao/
└─ mgcoa9000DAO.java
        │
        │ namespace
        ▼
Resource
rdw.mg.co.a/
└─ mgcoa9000-ORA.xml
```

---

# 22. 업무분류 축 일치

## FIG-PDMG-16. Naming Axis

```text
Business
MG / CO / A
   │
   ├────────► Java
   │          nhnis.mg.co.a
   │
   ├────────► Mapper
   │          rdw.mg.co.a
   │
   └────────► Service Prefix
              mgcoa
```

---

# 23. Program / ServiceId / Class Stem

## FIG-PDMG-17. Program Stem

```text
Program
mgcoa9001
    │
    ├─ Handler  mgcoa9001Handler
    ├─ Facade   mgcoa9001Facade
    ├─ Service  mgcoa9001Service
    ├─ DAO      mgcoa9001DAO
    └─ Mapper   mgcoa9001-ORA.xml
```

### 주의

모든 거래가 1:1 Class Stem을 완전히 지키는지는 기계적 Source Scan으로 검증한다.

---

# 24. ServiceId Anatomy

## FIG-PDMG-18. ServiceId

```text
mg | co | a | 9001 | S | 0
│    │    │    │      │   │
│    │    │    │      │   └─ Sequence
│    │    │    │      └──── Transaction Type
│    │    │    └─────────── Program ID
│    │    └──────────────── Function
│    └───────────────────── Business
└────────────────────────── Application Group
```

예:

```text
mgcoa9001S0
```

---

# 25. 현재 Handler Registry — 13 ServiceIds

## FIG-PDMG-19. Current Registry

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

---

# 26. 과거 8개 vs 현재 13개

```text
Past Document
8 ServiceIds
       │
       │ DRIFT
       ▼
Current Handler Source
13 ServiceIds
```

### 판정

```text
[DRIFT / SUPERSEDED CANDIDATE]
```

현재 Source 분석을 우선한다.

---

# 27. TransactionHandler Interface

## FIG-PDMG-20. Framework Contract

```text
pdmg-fw
TransactionHandler
│
├─ serviceId()
├─ serviceIds()
└─ handle(Object dtoBody, TransactionContext context)
```

Business Handler는 이 Interface를 구현한다.

---

# 28. Handler Registry Build

## FIG-PDMG-21. Registry Startup

```text
Spring Startup
   ↓
TransactionHandler Beans
   ↓
serviceIds()
   ↓
handlerMap
   │
   ├─ unique → register
   ├─ empty  → skip/warn
   └─ duplicate
          ↓
    IllegalStateException
          ↓
       Startup Fail
```

### 현재 판정

과거 문서에서 `[OPEN]`이었던 Duplicate 처리에 대해,
현재 `14장.Handler와 Controller` Source 분석에서는 **중복 ID를 기동 실패로 처리**하는 것으로 정리되어 있다.

---

# 29. Dispatcher

## FIG-PDMG-22. Service Routing

```text
serviceId
mgcoa9000C0
     │
     ▼
handlerMap[serviceId]
     │
     ├─ FOUND
     │    ↓
     │ mgcoa9000Handler
     │
     └─ NOT FOUND
          ↓
      ServiceHandlerNotFound
```

---

# 30. Handler 책임

```text
ServiceId 등록
ServiceId 분기
DTO 전달
Facade 호출
```

금지:

```text
DAO 직접 호출
Mapper 직접 호출
SQL 작성
TransactionManager 직접조작
Servlet Request 재파싱
```

---

# 31. Handler → Facade

## FIG-PDMG-23. Adapter to Use Case

```text
mgcoa9000S0
   ↓
mgcoa9000Handler
   ↓
mgcoa9000Facade.mgcoa9000S0(...)
```

---

# 32. 모든 Current Handler의 공통 특성

현재 분석된 Handler들은:

```text
대응 Business Facade 주입
```

을 사용하며:

```text
DAO
Mapper
Service 직접 의존
```

은 확인되지 않는다.

### 판정

```text
[AS-IS CONFORM]
Handler = Thin Inbound Adapter
```

---

# 33. Facade 책임

## FIG-PDMG-24. Facade

```text
Handler
  ↓
Facade
  │
  ├─ Use Case Boundary
  ├─ DTO 변환 / 조정
  ├─ 여러 Service 조정
  └─ Transaction Annotation Candidate
  ↓
Service
```

---

# 34. Facade ≠ 항상 최외곽 Transaction

```text
TCF ON + Timeout ON
Worker
  ↓
TransactionTemplate BEGIN
  ↓
Handler
  ↓
Facade @Transactional(REQUIRED)
```

따라서:

```text
Facade Annotation 위치
≠
Physical TX BEGIN 위치
```

---

# 35. Service 책임

## FIG-PDMG-25. Business Service

```text
Facade
  ↓
BizPrePostAspect.before
  ↓
Service
  │
  ├─ Business Procedure
  ├─ Business Decision
  └─ DAO Call
  ↓
BizPrePostAspect.afterReturning
```

---

# 36. BizPrePostAspect 위치

Current Pointcut 분석:

```text
nhnis.mg.co.a.application.service..*
```

### 핵심

`BizPrePostAspect`는 시스템 Servlet Filter가 아니라
**업무 Service 실행지점에 개입하는 Business-side Aspect**다.

---

# 37. DAO 책임

## FIG-PDMG-26. DAO / Mapper Interface

```text
Service
  ↓
DAO Interface
  ↓
MyBatis
  ↓
Mapper XML
  ↓
SQL
```

DAO와 Mapper XML을 동일 Java Class로 혼동하지 않는다.

---

# 38. Mapper Namespace 계약

## FIG-PDMG-27. Namespace Exact Match

```text
Java DAO FQCN
nhnis.mg.co.a.persistence.dao.mgcoa8888DAO
                    │
                    │ EXACT MATCH
                    ▼
Mapper XML
namespace =
nhnis.mg.co.a.persistence.dao.mgcoa8888DAO
```

---

# 39. Mapper Resource Pattern

Current `RdwDataSourceConfig` 분석:

```text
classpath*:rdw.*/*.xml
```

## FIG-PDMG-28. Resource Load

```text
classpath*
   ↓
rdw.*
   ├─ rdw.mg.co.a
   │    ├─ mgcoa5530-ORA.xml
   │    ├─ mgcoa8888-ORA.xml
   │    ├─ mgcoa9000-ORA.xml
   │    └─ ...
   └─ other rdw.*
```

---

# 40. Mapper 하위 폴더 주의

```text
rdw.mg.co.a/
├─ mgcoa9000-ORA.xml       O
└─ txparam/
   └─ mgcoa9000-ORA.xml    ? / 현재 Pattern 미포함 가능
```

### 핵심

Resource 구조 변경은 Mapper Load Pattern과 함께 바꿔야 한다.

---

# 41. MyBatis 3대 연결계약

## FIG-PDMG-29. MyBatis Contract

```text
1. DAO Scan
@MapperScan
       ↓
2. Mapper Resource Load
classpath*:rdw.*/*.xml
       ↓
3. Namespace / Statement ID
DAO FQCN / Method
       ↓
SQL
```

---

# 42. Current MapperScan

현재 주요 설정 분석:

```text
basePackages =
nhnis.mg.co.a.persistence.dao
```

---

# 43. SqlId Naming

대표 Program `mgcoa9000`:

```text
mgcoa9000S0_S0
mgcoa9000S0_COUNT
mgcoa9000C0_C0
mgcoa9000U0_U0
mgcoa9000D0_D0
..._exists
```

### 핵심

보조 Statement는 실제 Source에 `_exists` 등도 존재할 수 있으므로
`_S0/_C0/_U0/_D0`만 허용한다고 단정하지 않는다.

---

# 44. pdmg-fw 책임

## FIG-PDMG-30. Framework Scope

```text
pdmg-fw
│
├─ HTTP Entry Common
│   └─ DefaultFilter
│
├─ MVC Common
│   ├─ ServicePreventionInterceptor
│   ├─ Request Resolver
│   └─ Response Advice
│
├─ Context
│   └─ ServiceContext
│
├─ TCF
│   ├─ OnlineTransactionController
│   ├─ TcfFacade
│   ├─ TransactionDispatcher
│   └─ TransactionHandler Interface
│
├─ Execution Control
│   └─ OnlineTimeoutExecutor
│
├─ Error
└─ Logging / ImageLog Support
```

---

# 45. Framework가 알아야 하는 것 / 몰라야 하는 것

## FIG-PDMG-31. Framework Boundary

```text
pdmg-fw KNOWS
├─ ServiceId
├─ Request Context
├─ Timeout
├─ Handler Registry
├─ Error / Response
└─ Logging

pdmg-fw SHOULD NOT KNOW
├─ 고객별 업무 Rule
├─ 특정 마케팅 계산
├─ 특정 프로그램 SQL 조건
└─ 화면별 Business 판단
```

---

# 46. PDMG Online Runtime 전체

## FIG-PDMG-32. TCF ON + Timeout ON

```text
HTTP Request
  ↓
DefaultFilter
  ↓
Spring SecurityFilterChain
  ↓
DispatcherServlet
  ↓
ServicePreventionInterceptor.preHandle
  ↓
OnlineTransactionController
  ↓
TcfFacade
  ↓
OnlineTimeoutExecutor
  │
  ├─ Request Thread : Future.get(timeout)
  │
  └─ Worker Thread
       ↓
     TransactionTemplate
       ↓
     TransactionDispatcher
       ↓
     TransactionHandler
       ↓
     Business Facade
       ↓
     BizPrePostAspect
       ↓
     Service
       ↓
     DAO
       ↓
     Mapper XML
       ↓
     DB
       ↓
     Deadline Check
       ↓
     COMMIT / ROLLBACK
  ↓
Response / Exception
  ↓
ResponseBodyAdvice
  ↓
Interceptor.afterCompletion
  ↓
DefaultFilter.finally
  ↓
HTTP Response
```

---

# 47. Servlet / Security / MVC / TCF 경계

## FIG-PDMG-33. Runtime Boundaries

```text
Servlet
└─ DefaultFilter
      ↓
Security
└─ SecurityFilterChain
      ↓
Spring MVC
├─ DispatcherServlet
├─ Interceptor
└─ Controller
      ↓
TCF
├─ TcfFacade
├─ TimeoutExecutor
├─ Dispatcher
└─ Handler
      ↓
Business
└─ Facade / Service / DAO
```

---

# 48. DefaultFilter

## FIG-PDMG-34. Filter Responsibility

```text
HTTP Request
   ↓
DefaultFilter
   ├─ OPTIONS 제외
   ├─ Body Cache
   ├─ JWT 검사 [비-local JSON 경로]
   ├─ hdr_nhnis 파싱 / local 합성
   ├─ GUID
   ├─ ServiceContext
   └─ MDC
   ↓
filterChain.doFilter(...)
```

---

# 49. Filter와 MVC를 합치면 안 되는 이유

```text
Filter
= Controller를 아직 모르는 Servlet Boundary

MVC
= 특정 Handler / Method / Argument를 아는 Boundary
```

책임이 다르다.

---

# 50. CORS

일반 Local Reference:

```text
pdmg-ui      : 8090
pdmg-service : 8080
```

### 주의

```text
Local Port
≠ Production Port
```

CORS 장애는 Business Logic 장애가 아니다.

---

# 51. Filter Order Drift

과거 일부 문서:

```text
order = 1
```

Current 분석:

```text
Ordered.HIGHEST_PRECEDENCE + 20
```

### 판정

```text
[DRIFT]
```

Current Source를 우선한다.

---

# 52. SecurityFilterChain

## FIG-PDMG-35. Current Security Position

```text
DefaultFilter
   ↓
Spring SecurityFilterChain
   ↓
DispatcherServlet
```

### 핵심

Security Chain 존재:

```text
≠
업무 Authorization 완료
```

---

# 53. ServicePreventionInterceptor

## FIG-PDMG-36. System Pre/Post

```text
preHandle
├─ Header/sys_comm 보강
├─ GUID 보완
├─ ServiceId/IP/User 보완
├─ Request Log
└─ Pre ImageLog
    ↓
Business Runtime
    ↓
afterCompletion
├─ Normal Post ImageLog
└─ Exception ImageLog
```

---

# 54. Interceptor Scope

현재:

```text
/**
```

대상이며:

```text
/error
```

등 일부 제외가 있을 수 있다.

---

# 55. OnlineTransactionController

## FIG-PDMG-37. Common Entry Adapter

```text
HTTP Request
  ↓
OnlineTransactionController
  │
  ├─ ServiceId 결정
  ├─ request["dto"] 추출
  └─ TcfFacade.process()
```

### 중요한 사실

TCF로 넘기는 `dtoBody`는 전체 전문이 아니다.

```text
Header / GUID
→ ServiceContext / TransactionContext

Business DTO
→ dtoBody
```

---

# 56. Controller Mapping

현재 공통 Entry는 다음 형태를 지원하는 것으로 분석된다.

```text
POST /online
POST /{businessCode}/online
POST /{serviceId}
```

모두 공통 `handle()` 흐름으로 수렴한다.

---

# 57. ServiceId 결정 우선순위

Current 분석:

```text
1. ServiceContext.header.sys_comm.rms_svc_c
2. Request JSON hdr_nhnis.sys_comm.rms_svc_c
3. Path Variable
4. null
```

### 위험

Header / Path 불일치에 대한 강제 Reject 정책은 별도 확인이 필요하다.

---

# 58. TcfFacade

## FIG-PDMG-38. Facade of TCF

```text
OnlineTransactionController
       ↓
TcfFacade
       │
       ├─ TransactionContext.fromCurrent
       └─ OnlineTimeoutExecutor.execute
```

TcfFacade가 하지 않는 것:

```text
SQL 실행
Business Rule
ImageLog 조립
HTTP Envelope 조립
```

---

# 59. STF / ETF Actual vs Intended

## FIG-PDMG-39. Exists ≠ Executed

```text
STF / ETF Class / Bean
        │
        ▼
TcfFacade가 호출하는가?
        │
        ├─ YES → Runtime Step
        └─ NO  → Not AS-IS Runtime
```

현재 분석에서는:

```text
TcfFacade Current Call Path
→ STF / ETF 직접 호출 없음
```

### 판정

```text
[AS-IS]
STF/ETF를 실행 Sequence로 그리지 않는다.
```

---

# 60. OnlineTimeoutExecutor

## FIG-PDMG-40. Worker Executor

```text
Request Thread
    │
    ├─ capture Context / MDC
    ├─ submit Task
    └─ Future.get(5000ms)
          │
          ▼
    pdmg-online-N
          │
          ├─ install Context/MDC
          ├─ TransactionTemplate
          └─ clear
```

---

# 61. Current Timeout Snapshot

```text
[AS-IS SNAPSHOT]

milliseconds    = 5000
pool-size       = 20
queue-capacity  = 100
```

---

# 62. Overload

## FIG-PDMG-41. Queue Saturation

```text
20 Workers Busy
      +
100 Queue Full
      ↓
Task Reject
      ↓
OnlineOverloadException
      ↓
HTTP 503
```

---

# 63. Request vs Worker Thread

## FIG-PDMG-42. Two Lifecycles

```text
Request Thread
Filter / MVC / TcfFacade
       │
       │ submit
       ▼
Worker Thread
TX / Handler / Business / DB
```

### 핵심

```text
HTTP Request Lifecycle
≠
DB Transaction Lifecycle
```

---

# 64. Worker Context Propagation

## FIG-PDMG-43. Explicit Propagation

```text
Request Thread
ServiceContext + MDC
       │ capture
       ▼
Worker
       │ install
       ▼
Business
       │
       ▼
finally clear
```

자동 ThreadLocal 전파가 아니다.

---

# 65. Shared Mutable ServiceContext Risk

현재 Worker Context는 별도 Immutable Snapshot보다
같은 `ServiceContext` 참조를 공유하는 것으로 분석된다.

## FIG-PDMG-44. Mutable Reference

```text
Request Thread
      │
      └────── same ServiceContext object ──────┐
                                               ▼
                                         Worker Thread
```

### 위험

```text
Thread Race
Servlet Request/Response Reference
Lifecycle overlap
Mutable Header / responseBody
```

---

# 66. ServiceContext

## FIG-PDMG-45. Context Fields

```text
ServiceContext
│
├─ applicationName
├─ guid
├─ active profile
├─ requestHeaders
├─ httpServletRequest
├─ httpServletResponse
├─ header
├─ userContext
├─ requestBody
└─ responseBody
```

---

# 67. ServiceContext는 무엇이 아닌가

```text
DB Transaction            X
JDBC Connection           X
Business DTO Store        X
Authentication Policy     X
Automatic Thread Transfer X
```

---

# 68. ServiceContextHolder

```text
ThreadLocal<ServiceContext>
```

기본 API:

```text
setInstance
getInstance
removeInstance
```

### 핵심

Container가 자동으로 관리하는 Request Scope라고 오해하지 않는다.

---

# 69. Context Lifecycle

## FIG-PDMG-46. Request Context

```text
DefaultFilter
  ↓ create/set
ServiceContext
  ↓
Interceptor
  ↓
Controller / TCF
  ↓
Worker capture/install
  ↓
Business
  ↓
ResponseBodyAdvice
  ↓
afterCompletion
  ↓
Filter finally
  ↓ remove
```

---

# 70. userContext Risk

Current:

```text
Map<String,Object>
```

장점:

```text
확장성
```

위험:

```text
문자열 Key Drift
Type Drift
Owner 불명확
Hidden Business Input
```

TO-BE 후보:

```text
Immutable AuthenticatedUserContext
```

---

# 71. TransactionContext

## FIG-PDMG-47. TransactionContext

```text
TransactionContext
│
├─ serviceId
├─ ServiceContext reference
└─ startedAtNanos
```

### 혼동 금지

```text
TransactionContext
≠
DB Transaction
```

---

# 72. Worker Transaction

## FIG-PDMG-48. TransactionTemplate

```text
Worker
  ↓
TransactionTemplate BEGIN
  ↓
Dispatcher
  ↓
Handler
  ↓
Facade REQUIRED
  ↓
Service
  ↓
DAO / SQL
  ↓
Deadline Check
  ↓
COMMIT / ROLLBACK
```

---

# 73. Facade REQUIRED

```text
Outer TransactionTemplate
   ↓
Facade @Transactional(REQUIRED)
   ↓
JOIN EXISTING TX
```

---

# 74. readOnly GAP

Facade Query에:

```text
readOnly=true
```

가 선언되어도 Outer `TransactionTemplate`이 먼저 열리면
실제 Read-only 속성 적용 여부를 검증해야 한다.

```text
[GAP]
```

---

# 75. Timeout 504의 의미

## FIG-PDMG-49. Timeout Semantics

```text
Future.get(5000ms)
       ↓
Timeout
       ↓
cancel(true)
       ↓
HTTP 504
```

하지만:

```text
HTTP 504
≠ Worker ended
≠ JDBC Statement canceled
≠ DB rollback completed
```

---

# 76. Deadline Guard

## FIG-PDMG-50. Late Commit Prevention

```text
HTTP Timeout
   ↓
Worker continues
   ↓
SQL returns
   ↓
Deadline exceeded?
   ├─ YES → rollback
   └─ NO  → commit
```

---

# 77. TCF OFF

## FIG-PDMG-51. OFF Path

```text
HTTP
 ↓
Filter / Interceptor
 ↓
Business Controller
 ↓
Service / Facade
 ↓
DAO
```

---

# 78. OFF는 Framework 전체 OFF가 아니다

```text
TCF OFF
=
Common TCF Controller / Dispatcher / Handler Registry OFF
```

별도 설정에 따라 다음은 남을 수 있다.

```text
Filter
Interceptor
Response Advice
DataSource
TransactionManager
```

---

# 79. Current OFF Controller Drift

현재 분석:

```text
mgcoa5530 → Service
mgcoa8888 → Service
mgcoa9000 → Service
mgcoa9001 → Service
mgcoa9999 → Service
mgcoa9100 → Facade
```

### 문제

```text
ON
Handler → Facade → Service

OFF
Controller → Service
```

Facade Use Case Boundary가 일관되지 않다.

---

# 80. TCF ON/OFF TO-BE Candidate

## FIG-PDMG-52. Same Business Core

```text
TCF ON
Handler ───────┐
               │
               ▼
             Facade
               ↓
             Service
               ↓
              DAO
               ▲
               │
TCF OFF        │
Controller ────┘
```

Entry Adapter만 다르게 하고 Business Core는 통합하는 방향이다.

---

# 81. Standard Message

## FIG-PDMG-53. Request

```text
{
  "hdr_nhnis": {
    "sys_comm": {
      "rms_svc_c": "mgcoa8888S0",
      "std_gbl_id": "...",
      "tr_sysid": "PDMG",
      "tr_trm_ipadr": "127.0.0.1",
      "tr_brc": "10001",
      "scid": "mgcoa8888",
      "optr_eno": "E0000001"
    }
  },
  "dto": {
    "...": "..."
  }
}
```

---

# 82. Local vs Non-local

```text
Local
dto-only 요청 일부 허용/합성 가능

Non-local
표준 hdr_nhnis 요구
```

### 주의

Local 편의기능을 Production Contract로 승격하지 않는다.

---

# 83. Header Lifecycle

## FIG-PDMG-54. Header Evolution

```text
Client JSON
   ↓
DefaultFilter
   ↓
ServiceContext.header
   ↓
Interceptor enrichment
   ↓
Controller ServiceId/IP enrichment
   ↓
ResponseBodyAdvice
   ↓
Response Header
```

### 핵심

응답 Header는 단순 Raw Echo가 아니다.

---

# 84. Success Envelope

```text
{
  "hdr_nhnis": {...},
  "dto": {...}
}
```

---

# 85. Known Error Envelope

```text
{
  "hdr_nhnis": {...},
  "result": {...}
}
```

---

# 86. GUID

Current:

```text
std_gbl_id
```

를 GUID/Correlation Key로 사용한다.

## FIG-PDMG-55. GUID

```text
Header.std_gbl_id
     ↓
ServiceContext.guid
     ↓
MDC["guid"]
     ↓
Worker MDC
     ↓
Application Log
     ↓
ImageLog.GUID
```

---

# 87. GUID는 무엇이 아닌가

```text
Authentication Key
Business Primary Key
Idempotency Key [자동]
```

---

# 88. ResponseBodyArgumentResolver

이름은 Resolver지만 실제 역할은:

```text
ResponseBodyAdvice
```

성격으로 분석된다.

## FIG-PDMG-56. Response

```text
Controller / Handler Result
      ↓
ResponseBodyAdvice
      ↓
Success DTO or Error Result
      ↓
Standard Envelope
```

---

# 89. RequestBodyArgumentResolver GAP

Typed DTO Binding 의도와
TCF Common Controller의 전체 Map/Node 수신 관계에서
Custom Resolver 적용범위가 애매할 수 있다.

```text
[GAP]
Resolver Scope / Ordering
```

---

# 90. Error Handling

## FIG-PDMG-57. Current Known Mapping

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

---

# 91. Early Filter Error

```text
DefaultFilter
  ↓
sendError 400 / 401
  ↓
MVC Advice 우회 가능
```

### GAP

```text
Standard {hdr_nhnis,result} 보장 불가
```

---

# 92. Generic Exception GAP

명시적 Catch되지 않은:

```text
RuntimeException
SQL Exception
DataSource Error
```

등이 Spring Default `/error`로 갈 가능성을 검증해야 한다.

---

# 93. Legacy vs TCF Error Path

PDMG에는:

```text
TCF GlobalExceptionHandler
Legacy NhBaseException Handler
ResponseBodyAdvice
```

가 공존한다.

### 위험

```text
Handler precedence
Error format drift
Stack information exposure
```

---

# 94. ImageLog

## FIG-PDMG-58. ImageLog Lifecycle

```text
preHandle
  ↓
PRE INSERT
  ↓
Business Runtime
  ↓
ResponseBodyAdvice
  ↓
afterCompletion
  │
  ├─ Normal POST UPDATE
  └─ Exception UPDATE/INSERT
```

---

# 95. ImageLog Table 역할

대표:

```text
TB_FW_IMAGE_LOG
```

저장 후보:

```text
GUID
ServiceId
Screen
Operator
Client IP
Request Time
Response Time
Error
Request/Response Message
```

---

# 96. ImageLog Fail-open

Current 분석:

```text
ImageLog failure
→ catch/log
→ Business continue
```

### 장점

Availability 보호

### 위험

Audit Gap

---

# 97. Runtime DDL Risk

ImageLog에서 누락 Column을 Runtime에 추가하려는 로직이 있다면:

```text
Application Runtime
   ↓
DDL
```

은 운영 Schema Governance 측면의 위험이다.

TO-BE:

```text
Migration / DBA controlled schema change
```

---

# 98. pdmg-jwt Package

## FIG-PDMG-59. JWT Source Tree

```text
nhnis.mg.jw.a
│
├─ entry
│  ├─ handler
│  └─ web
│
├─ application
│  ├─ facade
│  └─ service
│
├─ dto
├─ persistence
│  └─ dao
├─ config
└─ support
```

JWT는 `MG/JW/A`라는 별도 업무축이다.

---

# 99. JWT Normal Login

## FIG-PDMG-60. mgjwa1000C0

```text
Browser
  ↓
pdmg-jwt /online
  ↓
ServiceId mgjwa1000C0
  ↓
mgjwa1000Handler
  ↓
mgjwa1000Facade
  ↓
mgjwa1000Service.mgjwa1000C0()
  ↓
User Lookup
  ↓
PasswordEncoder.matches()
  ↓
JwtTokenIssuer
  ↓
Access + Refresh
```

---

# 100. Current Token Lifetimes

Current JWT source analysis indicates defaults such as:

```text
Access Token  = 15 minutes
Refresh Token = 8 hours
```

### 태그

```text
[AS-IS SNAPSHOT]
```

운영 Target Security Baseline로 자동 승격하지 않는다.

---

# 101. Access Token

## FIG-PDMG-61. RS256 Issue

```text
Claims
  ↓
JwtTokenIssuer
  ↓
RSA Private Key
  ↓
RS256
  ↓
Access Token
```

---

# 102. Current JWT Claims

대표:

```text
Header
alg = RS256
typ = JWT
kid = nsight-jwt-rs256

Claims
iss
aud
sub
jti
iat
exp
type
userId
userName
branchId
channelId
authGroupId
```

### 주의

JWT Payload는 암호화가 아니라 Base64URL Encoding이다.

---

# 103. Refresh Token

## FIG-PDMG-62. Refresh

```text
Random Plain Refresh Token
       │
       ├────► Client
       │
       └────► SHA-256
                 ↓
              DB Hash
```

DB에는 원문이 아니라 Hash를 저장하는 구조다.

---

# 104. Refresh Validation

```text
Refresh Plain
  ↓
Hash
  ↓
DB Lookup
  ↓
Revoked?
Rotated?
Expired?
User Active?
  ↓
New Token Pair
```

---

# 105. JWT UI Storage

Current Admin UI 분석:

```text
sessionStorage
key = pdmg.jwt.session
```

Access/Refresh pair를 JavaScript가 읽을 수 있는 형태로 저장한다.

### Risk

```text
XSS
→ Access + Refresh 동시노출 가능
```

---

# 106. JWT SSO Flow

## FIG-PDMG-63. mgjwa1000C1

```text
Trusted Internal Caller
   ↓
mgjwa1000C1
   │
   ├─ allowed service
   ├─ timestamp
   ├─ HMAC
   └─ caller IP
   ↓
Trusted User Info
   ↓
PDMG Token Pair
```

### 핵심

```text
mgjwa1000C1
≠
일반 OIDC Callback로 자동해석
```

외부 IdP 검증은 `[OPEN UPSTREAM]`.

---

# 107. HMAC Secret vs RSA Key

## FIG-PDMG-64. Credential Separation

```text
RS256 Private Key
= Access Token Signing

RS256 Public Key / JWKS
= Verification

Internal HMAC Secret
= SSO Internal Caller Validation

pdmg-fw jwt.secret
= Legacy HMAC Validator Secret
```

서로 같은 Secret으로 취급하지 않는다.

---

# 108. Critical JWT AS-IS Gap

Current JWT analysis에서는:

```text
pdmg-jwt
→ RS256 issue

pdmg-fw JwtProvider
→ jwt.secret HMAC verify
```

구조가 동시에 분석된다.

## FIG-PDMG-65. Issuer / Verifier Incompatibility

```text
pdmg-jwt
RS256
  │ Access Token
  ▼
pdmg-service / pdmg-fw
JwtProvider
HMAC secret
```

### 판정

```text
[CRITICAL GAP]
발급 Algorithm과 검증 Algorithm 경로 정합성 확보 필요
```

---

# 109. JWT Key Generation Risk

Current `JwtKeyConfiguration` 분석:

```text
Application startup
   ↓
Generate RSA 2048 key in memory
   ↓
kid = nsight-jwt-rs256
```

### 위험

```text
Restart
→ same kid / different key

Multi-instance
→ same kid / different key
```

---

# 110. JWKS

## FIG-PDMG-66. Current JWKS

```text
Jwt Signing Key
   ↓
toPublicJWK()
   ↓
GET /.well-known/jwks.json
   ↓
Public Key + kid
```

Private Key는 노출하지 않는다.

---

# 111. JWKS Integration GAP

Current business-side verifier에:

```text
JWKS-based RS256 Decoder
```

가 실제 연결되어 있는지 별도 확인이 필요하며,
기존 분석에서는 `pdmg-fw JwtProvider`가 HMAC 경로로 파악된다.

```text
[GAP]
```

---

# 112. JWT Private Key TO-BE

## FIG-PDMG-67. Key Management

```text
Approved Key Store / KMS / HSM
        │
        ├─ JWT #1
        └─ JWT #2
        │
        ▼
Same Active Key / kid
```

제품은 `[TBD]`.

---

# 113. Key Rotation

```text
Old kid K1
      │ keep verify
      ▼
New kid K2
      │ new issue
      ▼
Overlap Period
      ↓
K1 retirement after token expiry
```

---

# 114. JWT Authorization GAP

JWT Signature 유효:

```text
=
인증 성공
```

일 수 있지만:

```text
=
해당 업무권한 허용
```

은 아니다.

업무 인가 정책은 별도 검증해야 한다.

---

# 115. JWT Subject vs Header User

## FIG-PDMG-68. Identity Binding

```text
JWT Validated Subject
ssoId
   │
   │ ?
   ▼
hdr_nhnis.sys_comm.optr_eno
   │
   │ ?
   ▼
ServiceContext.userContext
```

### 판정

```text
[SECURITY GAP]
```

---

# 116. pdmg-om

## FIG-PDMG-69. OM Unknown Boundary

```text
pdmg-om
│
├─ Package ?
├─ Process ?
├─ Dashboard ?
├─ Metric ?
├─ Control API ?
├─ DB ?
└─ Deployment ?
```

### 현재 판정

```text
[UNKNOWN]
```

Source Evidence 없이 일반 운영관리 구조로 채우지 않는다.

---

# 117. OM은 무엇을 확인해야 하는가

```text
Thread
JVM
Hikari
Slow Service
Timeout
Error
WAR
Deployment
Runtime Config
Alert
```

그러나 실제 `pdmg-om` 구현 여부는 별도 Source Scan 대상이다.

---

# 118. Source-to-Runtime Trace

## FIG-PDMG-70. Full Trace

```text
UI Folder
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
Mapper XML
   ↓
SqlId
   ↓
Table / View
   ↓
WAR
   ↓
JVM
   ↓
GUID
   ↓
Runtime Evidence
```

---

# 119. Source-to-Runtime 현재 강한 구간

```text
ServiceId
→ Handler
→ Facade
→ Service
→ DAO
→ Mapper
```

는 대표 Source에서 비교적 잘 연결된다.

---

# 120. Source-to-Runtime 현재 약한 구간

```text
Table
→ Artifact
→ JVM
→ Host
→ Runtime Evidence
```

전수 자동 Mapping은 아직 GAP이다.

---

# 121. ServiceId → Handler Coverage

현재 Source 분석:

```text
13 ServiceIds
→ 6 Handlers
```

Registry 기준 대표 Coverage는 확보된다.

---

# 122. Handler → Facade

Current Handlers는 대응 Facade 하나를 주입하는 구조로 분석된다.

```text
[AS-IS CONFORM]
```

---

# 123. Facade → Service

대표 Program Stem 기반 연결은 존재하지만
전수 Call Graph 자동화는 필요하다.

```text
[GAP]
```

---

# 124. DAO → Mapper

Java DAO FQCN과 Mapper Namespace가 계약이다.

```text
[AS-IS CONTRACT]
```

---

# 125. Mapper → Table

SQL Parser 없이:

```text
100% Trace
```

라고 쓰지 않는다.

```text
[GAP]
```

---

# 126. UI Catalog → Handler Registry

## FIG-PDMG-71. UI / Backend Drift

```text
UI Transaction Catalog
        │
        │ compare
        ▼
Handler Registry
```

두 Catalog를 SSOT로 통합/검증할 필요가 있다.

---

# 127. Source Conformance Rules

```text
R-PDMG-MODULE
R-PACKAGE-AXIS
R-SERVICEID-FORMAT
R-SERVICEID-UNIQUE
R-HANDLER-BRANCH
R-HANDLER-NO-DAO
R-DAO-MAPPER
R-MAPPER-RESOURCE
R-TX-OWNER
R-TIMEOUT
R-CONTEXT-CLEAR
R-JWT-ALGORITHM
R-JWT-KEY
R-SENSITIVE-LOG
```

---

# 128. R-PDMG-MODULE

```text
Required Reference Modules
pdmg-ui
pdmg-jwt
pdmg-fw
pdmg-service
pdmg-om
```

실제 Repository 존재 여부를 기계 스캔한다.

---

# 129. R-PACKAGE-AXIS

```text
ServiceId mgcoa...
   ↓
Java nhnis.mg.co.a
   ↓
Mapper rdw.mg.co.a
```

업무축이 다르면 FAIL 후보.

---

# 130. R-SERVICEID-UNIQUE

```text
All serviceIds()
   ↓
Duplicate?
  ├─ NO → PASS
  └─ YES → Startup FAIL / Rule FAIL
```

---

# 131. R-HANDLER-BRANCH

```text
serviceIds()
  │
  │ compare
  ▼
handle() branch
```

등록과 실행 Branch가 일치해야 한다.

---

# 132. R-HANDLER-NO-DAO

```text
Handler Dependency
  ↓
DAO / Mapper?
  ├─ NO → PASS
  └─ YES → FAIL
```

---

# 133. R-DAO-MAPPER

```text
DAO FQCN
  ↓ exact
Mapper namespace
```

---

# 134. R-MAPPER-RESOURCE

```text
Mapper XML
  ↓
classpath*:rdw.*/*.xml
  ↓
Loaded?
```

---

# 135. R-TX-OWNER

```text
ServiceId
  ↓
Runtime Mode
  ↓
Actual TX Owner
```

TCF ON/OFF 별도로 검증한다.

---

# 136. R-TIMEOUT

```text
Worker Deadline
Query Timeout
TX Timeout
External Timeout
Client Timeout
```

전수 Matrix 필요.

---

# 137. R-CONTEXT-CLEAR

```text
Worker
finally
  ↓
ServiceContextHolder.remove
MDC.clear
```

모든 Exit Path에서 검증한다.

---

# 138. R-JWT-ALGORITHM

```text
Issuer Algorithm
      │
      │ compare
      ▼
Verifier Algorithm
```

현재 RS256 vs HMAC 분석은 Critical Conformance 대상이다.

---

# 139. R-JWT-KEY

```text
Multi-instance
   ↓
Same kid?
Same public key?
Same active signing key?
```

---

# 140. R-SENSITIVE-LOG

다음 원문 로그 금지:

```text
Access Token
Refresh Token
Password
Private Key
HMAC Secret
DB Password
```

---

# 141. Source Test Matrix

## TEXT ARCHITECTURE 보완 — Source Test Matrix

```text
Source / Config
      ↓
Architecture Rule
      ↓
Static / Contract / Integration Test
      ↓
Runtime Scenario
      ↓
Evidence
      ↓
PASS / FAIL / GAP
```


| 영역 | Test |
|---|---|
| Module | Repository/Gradle scan |
| Package | Naming scan |
| ServiceId | format/unique/branch |
| Dependency | Handler/Controller no DAO |
| Mapper | namespace/resource/sql |
| TX | commit/rollback/owner |
| Timeout | slow SQL / queue full |
| Context | thread propagation / clear |
| JWT | issue/verify/restart/multi-instance |
| Error | filter/TCF/generic |
| Logging | sensitive/masking/GUID |

---

# 142. Runtime Test — Normal

```text
mgcoa9000S0
  ↓
Handler
  ↓
Facade
  ↓
Service
  ↓
DAO
  ↓
SQL
  ↓
COMMIT/Result
```

검증:

```text
GUID
ServiceId
TX
Response
ImageLog
```

---

# 143. Runtime Test — Handler Not Found

```text
Unknown ServiceId
  ↓
Dispatcher
  ↓
ServiceHandlerNotFound
  ↓
Error Envelope
```

---

# 144. Runtime Test — Duplicate Handler

```text
Two Handlers
same ServiceId
  ↓
Spring Startup
  ↓
IllegalStateException
  ↓
Startup Fail
```

---

# 145. Runtime Test — Timeout

```text
Slow SQL
  ↓
Worker > 5000ms
  ↓
HTTP 504
  ↓
Worker status
  ↓
Deadline rollback
```

---

# 146. Runtime Test — Queue Full

```text
20 Workers busy
+
100 Queue
  ↓
Next request
  ↓
503 Overload
```

---

# 147. Runtime Test — Context Leak

```text
Request A
  ↓
Worker
  ↓
Failure
  ↓
finally clear?
  ↓
Request B
  ↓
A Context visible?
```

반드시 `NO`여야 한다.

---

# 148. Runtime Test — JWT Restart

```text
Issue Access Token
  ↓
Restart pdmg-jwt
  ↓
JWKS changes?
  ↓
Old token verifies?
```

Current ephemeral key 구조에서는 중요 위험 시나리오다.

---

# 149. Runtime Test — JWT Multi-instance

```text
JWT #1 issue
   ↓
JWT #2 JWKS
   ↓
Verify?
```

same `kid` different key 문제를 검증한다.

---

# 150. Runtime Test — Identity Mismatch

```text
JWT sub = USER-A
Header optr_eno = USER-B
       ↓
Request
       ↓
Expected?
Reject / Bind / Audit
```

현재 정책 확정 필요.

---

# 151. Runtime Test — Early Filter Error

```text
Missing/Invalid Token
  ↓
DefaultFilter
  ↓
401
  ↓
CORS?
Standard Envelope?
GUID?
Audit?
```

---

# 152. Runtime Test — Generic Exception

```text
Unexpected RuntimeException
  ↓
Which Handler?
  ↓
HTTP?
Envelope?
Trace?
Sensitive Detail?
```

---

# 153. AS-IS / TO-BE Alignment Map

## FIG-PDMG-72. Alignment

```text
NSIGHT TARGET
       │
       │ compare
       ▼
PDMG AS-IS
       │
       ├─ CONFORM
       ├─ PARTIAL
       ├─ DRIFT
       └─ GAP
```

---

# 154. 현재 CONFORM 후보

```text
ServiceId 기반 Handler 확장
Thin Handler
Facade/Service/DAO 책임 분리
GUID Correlation
Standard Message Envelope
Worker Timeout Boundary
Mapper Namespace Contract
```

---

# 155. 현재 PARTIAL 후보

```text
Error Standardization
Security Authorization
Runtime Evidence Automation
TCF ON/OFF Core Reuse
Context Typed Model
```

---

# 156. 현재 CRITICAL GAP 후보

```text
RS256 Issuer vs HMAC Verifier
JWT ephemeral key / multi-instance
JWT Principal ↔ Header Identity
Query/TX Timeout
TCF OFF Business Boundary Drift
Generic/Early Error Contract
Deployment Mapping
Runtime Evidence Automation
```

---

# 157. pdmg-om GAP

```text
Module Baseline에는 존재
        │
        ▼
Actual Source / Runtime?
        │
        ▼
UNKNOWN
```

Source 확보 전 TO-BE 운영기능을 AS-IS로 채우지 않는다.

---

# 158. Source Evidence Coverage

## FIG-PDMG-73. Coverage Pyramid

```text
Strong
ServiceId → Handler
Handler → Facade
DAO → Mapper Namespace

Medium
Facade → Service
Service → DAO
Message / Context
Runtime Sequence

Weak / GAP
SQL → Table 전수
Artifact → Host/JVM
Runtime Evidence
pdmg-om
```

---

# 159. PDMG Reference를 NSIGHT에 사용하는 방법

## FIG-PDMG-74. Reference Promotion

```text
PDMG AS-IS Pattern
      │
      ▼
Good Practice?
      │
      ▼
Scope Applicable?
      │
      ▼
NFR Satisfied?
      │
      ▼
Security / Ops Validated?
      │
      ▼
ADR Approval
      │
      ▼
NSIGHT TO-BE Standard
```

---

# 160. 자동 승격 금지

```text
PDMG에 있음
     ↓
NSIGHT 표준

X
```

반드시:

```text
Evidence
Scope
Gap
ADR
Approval
```

이 필요하다.

---

# 161. Module Inventory Template

```yaml
module:
  name:
  buildType:
  packageRoots:
  runtimeProcess:
  springContext:
  artifact:
  dependencies:
  responsibility:
  evidence:
  status:
```

---

# 162. Source Component Inventory

```yaml
component:
  module:
  package:
  class:
  role:
  serviceIds:
  dependencies:
  annotations:
  runtimePath:
  evidence:
```

---

# 163. Service Trace Inventory

```yaml
serviceTrace:
  serviceId:
  ui:
  handler:
  facade:
  service:
  dao:
  mapper:
  sqlIds:
  tables:
  txPolicy:
  timeoutPolicy:
  securityPolicy:
  runtimeEvidence:
```

---

# 164. Security Inventory

```yaml
security:
  module:
  loginServiceId:
  tokenAlgorithm:
  kid:
  keySource:
  jwks:
  verifier:
  refreshStore:
  denylist:
  identityBinding:
  authorization:
  evidence:
```

---

# 165. Runtime Config Inventory

```yaml
runtime:
  tcfEnabled:
  timeoutEnabled:
  timeoutMs:
  workerPool:
  queueCapacity:
  datasource:
  transactionManager:
  mapperPattern:
  filterOrder:
  securityChain:
  evidence:
```

---

# 166. GAP Register

## TEXT ARCHITECTURE 보완 — GAP Register

```text
Expected / Required
      │
      ▼
Actual / Evidence
      │
      ▼
Difference
      │
      ▼
[GAP]
      │
      ├─ Owner
      ├─ Action
      ├─ ADR
      └─ Evidence
```


| ID | GAP | 영향 |
|---|---|---|
| GAP-PDMG-01 | `pdmg-om` Source/Runtime 미확인 | Operations |
| GAP-PDMG-02 | Module→Artifact→Host/JVM 전수 Mapping 미완료 | Deployment |
| GAP-PDMG-03 | UI Catalog↔Handler Registry SSOT 미통합 | Trace |
| GAP-PDMG-04 | Handler→Facade→Service→DAO 전수 Call Graph 미완료 | Source Trace |
| GAP-PDMG-05 | SqlId→Table/View 전수 Trace 미완료 | Data |
| GAP-PDMG-06 | Query Timeout 실제값 미확정 | Timeout |
| GAP-PDMG-07 | Spring TX Timeout 실제값 미확정 | TX |
| GAP-PDMG-08 | Outer TX readOnly 적용 검증 미완료 | TX |
| GAP-PDMG-09 | JDBC Interrupt/Cancel 검증 미완료 | Timeout |
| GAP-PDMG-10 | Mutable ServiceContext Snapshot 개선 미결정 | Thread |
| GAP-PDMG-11 | TCF OFF Facade Boundary 불일치 | Application |
| GAP-PDMG-12 | Early Filter Error Standard 미완료 | Error |
| GAP-PDMG-13 | Generic Exception Standard 미완료 | Error |
| GAP-PDMG-14 | RS256 Issuer ↔ HMAC Verifier 불일치 | Security |
| GAP-PDMG-15 | Ephemeral RSA Key | Security |
| GAP-PDMG-16 | JWT Principal↔Header Identity Binding 미완료 | Security |
| GAP-PDMG-17 | JWKS business verifier 연결 미완료 | Security |
| GAP-PDMG-18 | Denylist Enforcement 전수 검증 필요 | Security |
| GAP-PDMG-19 | Sensitive Log/Masking 전사표준 미완료 | Security |
| GAP-PDMG-20 | Runtime Evidence Manifest 자동화 미완료 | Governance |

---

# 167. RISK Register

## TEXT ARCHITECTURE 보완 — RISK Register

```text
Cause
  ↓
Risk Event
  ↓
Business / Data / Security / Operation Impact
  ↓
Severity
  ↓
Mitigation / Control
  ↓
Owner
  ↓
Evidence / Closure
```


| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-PDMG-01 | `pdmg-fw`를 Remote Server로 오해 | High |
| RISK-PDMG-02 | Module/Process/Context 혼동 | High |
| RISK-PDMG-03 | ServiceId Registry/UI Catalog Drift | High |
| RISK-PDMG-04 | Handler에 Business/DAO 침투 | High |
| RISK-PDMG-05 | Mapper Resource 경로 변경 후 Load 실패 | High |
| RISK-PDMG-06 | Timeout 후 Late Commit | Critical |
| RISK-PDMG-07 | Context Thread Leak | Critical |
| RISK-PDMG-08 | TCF OFF 결과 Drift | High |
| RISK-PDMG-09 | RS256/HMAC 검증 불일치 | Critical |
| RISK-PDMG-10 | JWT Restart로 기존 Token 검증 실패 | Critical |
| RISK-PDMG-11 | Multi-instance same kid/different key | Critical |
| RISK-PDMG-12 | JWT/Header 사용자 불일치 | Critical |
| RISK-PDMG-13 | UI sessionStorage Refresh 노출 | High |
| RISK-PDMG-14 | Early Error 비표준화 | High |
| RISK-PDMG-15 | ImageLog Fail-open 감사공백 | High |

---

# 168. ADR 후보

```text
ADR-PDMG-01 PDMG Module Baseline
ADR-PDMG-02 TCF ON/OFF Target
ADR-PDMG-03 Business Facade Boundary
ADR-PDMG-04 ServiceId SSOT
ADR-PDMG-05 Mapper Resource Standard
ADR-PDMG-06 Transaction Ownership
ADR-PDMG-07 Timeout Budget
ADR-PDMG-08 Context Snapshot
ADR-PDMG-09 Error Standard
ADR-PDMG-10 JWT Algorithm Alignment
ADR-PDMG-11 JWT Key Store
ADR-PDMG-12 JWKS Verification
ADR-PDMG-13 Identity Binding
ADR-PDMG-14 Revocation
ADR-PDMG-15 UI Token Storage
ADR-PDMG-16 pdmg-om Scope
ADR-PDMG-17 Runtime Evidence
```

---

# 169. CONFIRMED / AS-IS

```text
[CONFIRMED / AS-IS]

- pdmg-ui / pdmg-jwt / pdmg-fw / pdmg-service Source Evidence 존재
- pdmg-service 업무 Root = nhnis.mg.co.a
- pdmg-jwt Root = nhnis.mg.jw.a
- pdmg-fw Root = nhnis.fw.*
- pdmg-service + pdmg-fw same Spring Context 가능
- Direct Browser→Service와 Relay 경로 공존
- Current Handler 6개 / ServiceId 13개
- Handler duplicate ID는 startup failure 분석
- Handler는 대응 Facade를 주입
- DAO ↔ Mapper Namespace exact contract
- Mapper resource = classpath*:rdw.*/*.xml
- TCF ON Current Runtime
- Timeout 5000 / Worker20 / Queue100 snapshot
- ServiceContext ThreadLocal
- Worker 명시적 Context/MDC 전파
- Current success/error envelope
- pdmg-jwt RS256 issue
- Refresh token hash DB 저장
- SSO mgjwa1000C1 내부 HMAC validation
```

---

# 170. CONFLICT / DRIFT / UNKNOWN

```text
[DRIFT]
Filter Order 과거 1 vs Current HIGHEST_PRECEDENCE+20

[DRIFT]
Past Handler Service Count 8 vs Current Source 13

[CRITICAL GAP / CONFLICT]
pdmg-jwt RS256 issuer vs pdmg-fw HMAC JwtProvider

[UNKNOWN]
pdmg-om Source / Package / Runtime / Deployment

[OPEN]
PDMG Production Host/JVM/WAR/Port Mapping
```

---

# 171. Source Verification Checklist

```text
[ ] 5 Module Repository 존재?
[ ] Gradle Dependency?
[ ] Spring scanBasePackages?
[ ] UI Static Folder?
[ ] UI Transaction Catalog?
[ ] ServiceId 13개?
[ ] Handler Duplicate Startup Fail?
[ ] Handler→Facade?
[ ] Controller→Facade/Service?
[ ] DAO MapperScan?
[ ] Mapper Pattern?
[ ] Namespace Match?
[ ] SQL/Table Trace?
[ ] TCF ON/OFF?
[ ] Timeout 5000/20/100?
[ ] Context clear?
[ ] JWT RS256?
[ ] HMAC Verifier?
[ ] Key generation?
[ ] JWKS?
[ ] Refresh Hash?
[ ] Denylist?
[ ] ImageLog?
[ ] Deployment Mapping?
```

---

# 172. PDMG Reference Completion Gate

## FIG-PDMG-75. Evidence Gate

```text
Module Source?
  ↓ YES
Package / Class?
  ↓ YES
ServiceId Registry?
  ↓ YES
Business Call Path?
  ↓ YES
Mapper / SQL?
  ↓ YES
Runtime Path?
  ↓ YES
Config?
  ↓ YES
Security?
  ↓ YES
Deployment?
  ↓ YES
Runtime Evidence?
  ↓ YES
PDMG REFERENCE VERIFIED
```

현재는:

```text
Source / Runtime 강함
Deployment / OM / 일부 Security 정합 약함
```

이므로 **CONDITIONAL PASS**다.

---

# 173. NSIGHT Target과 PDMG Reference의 만나는 지점

## FIG-PDMG-76. Target ↔ Reference

```text
NSIGHT TARGET
Vision
Big Picture
Logical
Physical
Mechanism
Runtime
    │
    │ compare
    ▼
PDMG REFERENCE
Module
Package
ServiceId
Source
Config
Runtime
    │
    ▼
CONFORM / GAP / DRIFT
    │
    ▼
ADR / Standard Promotion
```

---

# 174. PDMG에서 NSIGHT 표준으로 승격 가능한 후보

```text
ServiceId 기반 Routing
Thin Handler
Facade Use Case Boundary
Service / DAO Separation
GUID Correlation
Standard Message Envelope
Framework/Common Responsibility Separation
Runtime Timeout Boundary
Source-to-Mapper Naming Trace
```

단, 모두 Scope/ADR 승인 후 Target Standard가 된다.

---

# 175. 승격 전에 개선이 필요한 후보

```text
TCF OFF Business Boundary
JWT Algorithm / Verifier
JWT Key Lifecycle
Identity Binding
Generic Error
Early Error
Context Mutable Model
Runtime Evidence
OM
```

---

# 176. Bottom-up Evidence 최종 지도

## FIG-PDMG-77. PDMG Bottom-up

```text
DB / SQL
   ↑
Mapper
   ↑
DAO
   ↑
Service
   ↑
Facade
   ↑
Handler
   ↑
ServiceId
   ↑
TCF / Framework
   ↑
HTTP / JWT / UI
   ↑
Runtime Evidence
   ↑
NSIGHT Architecture
```

---

# 177. 다음 장 Handoff

다음 장은 다음 두 축을 결합하는 것이 자연스럽다.

```text
09. OM / DEVOPS / OBSERVABILITY / OPERATIONS
```

PDMG Source Deep Dive에서 확인한:

```text
Thread
Worker
Hikari
ServiceId
GUID
Error
JWT
ImageLog
Deployment
```

을 실제 운영 Control Plane으로 올린다.

---

# 178. 09장에서 반드시 답할 질문

```text
1. OM은 실제 어느 Source/Process에서 동작하는가?
2. JVM/Thread/Worker/Hikari/SQL을 어떻게 관측하는가?
3. ServiceId별 p95/Error/Timeout을 어떻게 보는가?
4. CI/CD가 어떤 Artifact를 어느 Host/JVM에 배포하는가?
5. Config Drift를 어떻게 탐지하는가?
6. Runtime Log/Metric/Trace를 어디에 모으는가?
7. Alert→Runbook→Recovery가 어떻게 연결되는가?
8. Architecture Gate를 CI/CD와 어떻게 결합하는가?
9. Deployment Evidence를 어떻게 생성하는가?
10. pdmg-om과 외부 APM/Monitoring의 책임은 어떻게 나누는가?
```

---

# 보완검토 A. PDMG SOURCE Reference 시각화 보강

## FIG-PDMG-SUP-01. Source Evidence Maturity

```text
Module Name
   ↓
Source
   ↓
Config
   ↓
Runtime Path
   ↓
Deployment
   ↓
Runtime Evidence
```

각 단계에서 증거가 없으면 `[UNKNOWN]` 또는 `[GAP]`으로 둔다.

---

## FIG-PDMG-SUP-02. Source Test Matrix

```text
Module / Package
   ↓
ServiceId / Dependency Rule
   ↓
Mapper / TX / Timeout Rule
   ↓
JWT / Error / Logging Rule
   ↓
Runtime Scenario
   ↓
Evidence
```

---

## FIG-PDMG-SUP-03. PDMG Source Promotion Gate

```text
PDMG AS-IS
   ↓
Source Evidence
   ↓
NFR Fit
   ↓
Security Fit
   ↓
Operations Fit
   ↓
ADR
   ↓
NSIGHT TO-BE Candidate
```

---

## FIG-PDMG-SUP-04. pdmg-om UNKNOWN Boundary

```text
pdmg-om
  ↓
Source?
  ↓
Process?
  ↓
Metric / Dashboard?
  ↓
Control API?
  ↓
Deployment?
```

증거 확보 전 Target 기능을 AS-IS로 기입하지 않는다.

---

## FIG-PDMG-SUP-05. PDMG 핵심 개선우선순위

```text
P0
├─ JWT Issuer / Verifier
├─ Key Lifecycle
├─ Identity Binding
├─ Query / TX Timeout
└─ Deployment Mapping

P1
├─ Context Snapshot
├─ Early / Generic Error
├─ UI / Backend Catalog Drift
└─ Runtime Evidence Automation
```

---

# 179. Definition of Done

## TEXT ARCHITECTURE 보완 — Definition of Done

```text
Architecture Inputs
      ↓
Structure / Contract / Runtime Check
      ↓
Evidence Check
      ↓
GAP / CONFLICT / UNKNOWN Review
      ↓
PASS Condition
      │
      ├─ satisfied → PASS
      └─ pending   → CONDITIONAL PASS
```


## Module / Boundary
- [x] 5개 Module Baseline
- [x] Process/Module/Spring Context 분리
- [x] pdmg-om UNKNOWN 유지

## UI
- [x] Direct / Relay 공존
- [x] Static Folder
- [x] ServiceId 연결

## Business Source
- [x] Package Map
- [x] mgcoa9000 Source Tree
- [x] Handler/Facade/Service/DAO
- [x] Rule Layer AS-IS 오판 방지

## ServiceId
- [x] Naming
- [x] Current 13 Services
- [x] Duplicate Startup Fail
- [x] Dispatcher / Handler

## Mapper / Data
- [x] Java↔Mapper
- [x] Namespace
- [x] Resource Pattern
- [x] MapperScan
- [x] SqlId 주의

## Framework / Runtime
- [x] DefaultFilter
- [x] Security/MVC
- [x] Interceptor
- [x] Controller
- [x] TcfFacade
- [x] STF/ETF Actual 구분
- [x] Timeout / Worker
- [x] Context
- [x] TX
- [x] TCF OFF

## Message / Error / Logging
- [x] Request/Success/Error Envelope
- [x] Header Lifecycle
- [x] GUID
- [x] Error Paths
- [x] ImageLog

## JWT / Security
- [x] Login
- [x] RS256
- [x] Refresh Hash
- [x] SSO HMAC
- [x] RS256/HMAC Critical GAP
- [x] Ephemeral Key Risk
- [x] JWKS
- [x] Identity Binding GAP

## Governance
- [x] Source Rules
- [x] Test Matrix
- [x] GAP/RISK/ADR
- [x] NSIGHT Alignment
- [x] 09장 Handoff

**PDMG SOURCE / RUNTIME REFERENCE 장 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. `pdmg-om` Source/Runtime 확인
2. 전체 Module/Gradle Dependency 기계 스캔
3. UI Transaction Catalog ↔ Handler Registry 자동비교
4. Handler→Facade→Service→DAO Call Graph 전수생성
5. SqlId→Table/View 자동 Trace
6. Query/TX Timeout 실제값 확인
7. JDBC Cancel/Interrupt Integration Test
8. TCF OFF Business Boundary Target 결정
9. JWT RS256 Issuer↔Verifier 정합성 해결
10. Central Key Store / Key Rotation 결정
11. JWT Principal↔Header User Binding
12. Deployment Manifest / Host/JVM Mapping
13. Runtime Evidence Manifest 자동화

---

# 180. 장 최종 결론

## TEXT ARCHITECTURE 보완 — 장 최종 결론

```text
Chapter Evidence
      ↓
Confirmed Structure
      ↓
Remaining GAP / RISK
      ↓
Target Promotion Decision
      ↓
Next Architecture Layer / Baseline
```


> **PDMG는 NSIGHT 전체 Architecture가 아니라, 실제 Source와 Runtime으로 검증 가능한 매우 중요한 Reference 구현이다.**

> **가장 강한 부분은 ServiceId → Handler → Facade → Service → DAO → Mapper의 실행축과 Filter/TCF/Timeout/Context의 공통 Runtime 구조다.**

> **가장 우선적으로 닫아야 할 부분은 JWT 발급/검증 정합성, TCF OFF 경계, Query/TX Timeout, mutable Context, Deployment Mapping, pdmg-om, Runtime Evidence 자동화다.**

> **PDMG Pattern을 NSIGHT 표준으로 승격하려면 “구현되어 있다”는 이유가 아니라 Evidence → NFR → GAP → ADR → Approval 절차를 통과해야 한다.**

---


====================================================================================================

# CHAPTER 09

====================================================================================================

# NSIGHT / PDMG 아키텍처 정의서
# 09. OM / DEVOPS / OBSERVABILITY / OPERATIONS

> 보완개정: **v2 — TEXT Architecture Figure Coverage / Reference Coverage / Evidence Consistency 재점검**
## TEXT ARCHITECTURE 보완 — 장 전체 위치

```text
Source
   ↓
Build
   ↓
Artifact
   ↓
Deploy
   ↓
Runtime
   ↓
Metric/Log/Trace
   ↓
Alert/Runbook
   ↓
Evidence
```

## Visual-First / Top-down → Runtime Operations Drill-down 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-VISUAL-OPERATIONS-09`  
> Architecture Level: **L8 — DEVOPS / OM / OBSERVABILITY / OPERATIONS**  
> 문서 상태: **Draft / Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_08_PDMG_SOURCE_RUNTIME_REFERENCE_DEEP_DIVE_VISUAL_FIRST_상세본.md`  
> 본 장 목적: **Source 변경이 Build/Artifact/Deployment/Runtime으로 전달되고, Metric/Log/Trace/Alert/Runbook/Evidence를 통해 다시 Architecture Baseline으로 환류되는 운영 Control Plane 정의**

---

# 0. 이 장을 읽는 방법

8장에서 PDMG Source와 Runtime을 실제 구현 관점으로 내려갔다.

9장에서는 그것을 다시 운영 Control Plane으로 끌어올린다.

```text
SOURCE
  ↓
BUILD
  ↓
TEST
  ↓
ARTIFACT
  ↓
DEPLOY
  ↓
RUNTIME
  ↓
METRIC / LOG / TRACE
  ↓
ALERT
  ↓
RUNBOOK
  ↓
RECOVERY
  ↓
EVIDENCE
  ↓
DRIFT
  ↓
ARCHITECTURE UPDATE
```

이 장의 핵심은 도구목록이 아니다.

```text
GitLab이 있다
≠ DevOps 완료

APM이 있다
≠ Observability 완료

pdmg-om 이름이 있다
≠ OM 구현 완료

Dashboard가 있다
≠ 운영 가능

Alert가 있다
≠ Recovery 가능
```

---

# 1. VISUAL ROUTE — Operations Architecture 전체

## FIG-OPS-01. DevOps / OM / Observability Big Picture

```text
╔══════════════════════════════════════════════════════════════════════════════╗
║                  DEVOPS / OM / OBSERVABILITY / OPERATIONS                   ║
╚══════════════════════════════════════════════════════════════════════════════╝

 [CHANGE]
 Requirement / ADR / Source / Config
      │
      ▼
 [SCM]
 GitLab
      │
      ▼
 [BUILD / TEST]
 Gradle / Runner / Architecture Rule / Test
      │
      ▼
 [ARTIFACT]
 WAR / Build Metadata / Hash
      │
      ▼
 [DEPLOYMENT]
 DEV / TEST / PROD / DR
 Runner / eCAMS / approved deployment
      │
      ▼
 [RUNTIME]
 WEB / WAS / JVM / WAR / Worker / Hikari / DB
      │
      ▼
 [OBSERVABILITY]
 Metric / Log / Trace / Health / ImageLog
      │
      ▼
 [OM CONTROL PLANE]
 ServiceId / JVM / Thread / DB Pool / Timeout / Error
      │
      ▼
 [OPERATIONS]
 Alert → Diagnosis → Runbook → Recovery
      │
      ▼
 [EVIDENCE]
 Deployment / Runtime / DR / Security / Performance
      │
      ▼
 [GOVERNANCE]
 Drift → GAP → ADR → New Baseline
```

---

# 2. 8장 → 9장 Handoff

## FIG-OPS-02. Source Evidence to Operations

```text
08 PDMG SOURCE
│
├─ ServiceId
├─ Handler / Service
├─ Filter / TCF
├─ Worker
├─ Transaction
├─ Hikari
├─ JWT
├─ Error
├─ GUID
└─ ImageLog
       │
       ▼
09 OPERATIONS
│
├─ Build
├─ Deploy
├─ Runtime Inventory
├─ Monitoring
├─ Alert
├─ Runbook
├─ Recovery
└─ Evidence
```

---

# 3. Source Classification — DevOps와 OM을 섞지 않는다

## FIG-OPS-03. Three Evidence Levels

```text
A. NSIGHT STRATEGY
   GitLab / GitLab Runner / eCAMS
   IaaS
   APM / Integrated Log / GUID Trace
        │
        ▼

B. PDMG CURRENT SOURCE
   Java 21
   Spring Boot 3.5.14
   Gradle Multi-project
   PDMG Modules
   Log4j2 / GUID / ImageLog
        │
        ▼

C. OPERATIONS TARGET
   CI/CD Gate
   Deployment Evidence
   Runtime Inventory
   Monitoring
   Alert
   Runbook
   Drift
```

### 금지

```text
Strategy Tool 존재
=
Current PDMG Pipeline 구현완료

X
```

---

# 4. 운영 Architecture 핵심 결론

## FIG-OPS-04. Operations Principle

```text
Change
  ↓
Controlled Delivery
  ↓
Observable Runtime
  ↓
Actionable Alert
  ↓
Recoverable Operation
  ↓
Evidence
  ↓
Governed Change
```

> **운영 Architecture의 목적은 “시스템을 보는 것”이 아니라 “변경과 장애를 통제하고 복구하며 증명하는 것”이다.**

---

# 5. Standardization ↔ DevOps Loop

## FIG-OPS-05. Standardization Loop

```text
Architecture Standard
      ↓
Developer Guide
      ↓
Source
      ↓
CI Rule
      ↓
Build / Test
      ↓
Deploy
      ↓
Runtime
      ↓
Drift / Incident
      ↓
Architecture Standard Update
      └──────────────────────────────↺
```

### 핵심

표준은 문서배포로 끝나지 않는다.

```text
표준
→ Rule
→ CI
→ Runtime
→ Feedback
```

으로 유지한다.

---

# 6. DevOps Tool Boundary — Strategy Level

## FIG-OPS-06. Tool Responsibility

```text
GitLab
= Source Control / Change History

GitLab Runner
= Build / Test / Development Delivery Candidate

eCAMS
= Production Deployment / Change Control Strategy

IaaS
= Environment / Infrastructure Consistency
```

### 상태

```text
[NSIGHT STRATEGY]
```

현재 PDMG Repository에 모든 Pipeline이 구현되어 있다고 자동 판단하지 않는다.

---

# 7. Source → Build → Test → Artifact → Deploy

## FIG-OPS-07. Delivery Pipeline

```text
Developer
   ↓
Git Commit
   ↓
SCM
   ↓
Build
   ↓
Architecture Rule
   ↓
Unit / Contract / Security Test
   ↓
Package
   ↓
Artifact
   ↓
Hash
   ↓
Deploy
   ↓
Runtime Verification
```

---

# 8. Delivery의 완료 조건

```text
Build SUCCESS
≠ Release Ready
```

필요:

```text
Build
+
Test
+
Security
+
Architecture Rule
+
Artifact Identity
+
Deployment Evidence
+
Runtime Verification
```

---

# 9. PDMG Build Baseline

## FIG-OPS-08. PDMG Build Structure

```text
PDMG Root
│
├─ pdmg-ui
├─ pdmg-jwt
│    └─ pdmg-fw dependency
│
├─ pdmg-service
│    └─ pdmg-fw dependency
│
├─ pdmg-fw
└─ pdmg-om [Source/Build Evidence 추가 확인]
```

Current technology baseline:

```text
Java 21
Spring Boot 3.5.14
Gradle Multi-project
```

### 태그

```text
[PDMG AS-IS EVIDENCE]
```

---

# 10. Build Module vs Artifact

## FIG-OPS-09. Build Boundary

```text
Build Module
   │
   ▼
Compile / Package
   │
   ▼
Artifact
   │
   ▼
Runtime Deployment
```

### 핵심

```text
Module
≠ Artifact 1:1 자동
≠ Runtime Process 1:1 자동
```

---

# 11. Framework Dependency

```text
pdmg-service
      │
      └── depends on pdmg-fw

pdmg-jwt
      │
      └── depends on pdmg-fw
```

### 의미

Framework 변경은 여러 Runtime Artifact에 영향을 줄 수 있다.

---

# 12. Framework Change Impact

## FIG-OPS-10. Shared Framework Blast Radius

```text
pdmg-fw Change
      │
      ├─ pdmg-service Build Impact
      ├─ pdmg-jwt Build Impact
      ├─ Runtime Behavior Impact
      ├─ Security Impact
      └─ Regression Test Impact
```

---

# 13. Build Reproducibility

## FIG-OPS-11. Reproducible Build

```text
Source Commit
   +
Build Definition
   +
Dependency Lock / Version
   +
Toolchain
   ↓
Same Artifact
```

필요:

```text
Java Version
Gradle Version
Dependency Version
Build Options
Artifact Hash
```

---

# 14. Artifact Identity

## FIG-OPS-12. Artifact Identity Chain

```text
Source Commit
   ↓
Build ID
   ↓
Artifact Name
   ↓
Version
   ↓
SHA-256 / approved hash
```

---

# 15. Artifact Naming

권장 구성:

```text
System / Module
Version
Build
Commit
```

실제 Naming Rule은 Release Standard로 승인한다.

---

# 16. Same Artifact Principle

## FIG-OPS-13. Environment Promotion

```text
DEV
Artifact A
   ↓ promote
TEST
Artifact A
   ↓ promote
PROD
Artifact A
   ↓ sync
DR
Artifact A
```

### 금지

```text
DEV에서 Build A
PROD에서 다시 Build B
```

---

# 17. Artifact + Config

## FIG-OPS-14. Runtime Composition

```text
Artifact
   +
Environment Config
   +
Secret / Key
   +
Infrastructure
   =
Runtime
```

따라서 Artifact만 같다고 Runtime이 같다고 볼 수 없다.

---

# 18. Config Separation

## FIG-OPS-15. Configuration Boundary

```text
Artifact
├─ Business Code
├─ Framework Code
└─ Static Resource

Environment Config
├─ Endpoint
├─ DB
├─ Timeout
├─ Pool
├─ Feature Toggle
└─ Log Level

Secret
├─ Password
├─ Token Secret
├─ Private Key
└─ Certificate
```

---

# 19. Secret는 Source Config와 다르다

```text
application.yml
   │
   ├─ non-secret config
   │
   └─ secret reference
          ↓
     Secret Store / protected channel
```

### 금지

```text
Private Key in Git       X
DB Password in YAML      X
HMAC Secret in source    X
```

---

# 20. JWT Key Deployment

## FIG-OPS-16. Key Delivery

```text
Approved Key Store
      │
      ├─ JWT #1
      └─ JWT #2
      │
      ▼
Runtime Key Version
      │
      ▼
JWKS
```

운영은 다음을 알아야 한다.

```text
현재 kid?
Active Key?
Rotation Time?
JWT Instance 간 일치?
DR Key 일치?
```

---

# 21. Environment Promotion

## FIG-OPS-17. Promotion Flow

```text
DEV
 ↓
Integration
 ↓
TEST / QA
 ↓
Pre-Prod / Pilot [if used]
 ↓
PROD
 ↓
DR Sync
```

Gate:

```text
Functional
Architecture
Security
Performance
Deployment
Runtime
```

---

# 22. Configuration Promotion

```text
DEV Config
  ↓
Template / Model
  ↓
Environment Overlay
  ↓
Approval
  ↓
Deploy
```

환경간 무분별 복사 금지.

---

# 23. Deployment Manifest

## FIG-OPS-18. Deployment Manifest

```text
Artifact
  ↓
Deployment ID
  ↓
Environment
  ↓
Center
  ↓
Host
  ↓
JVM
  ↓
WAR / Context
  ↓
Port / Endpoint
```

---

# 24. Deployment Manifest 최소 필드

```yaml
deployment:
  deploymentId:
  releaseId:
  sourceCommit:
  buildId:
  artifact:
  artifactHash:
  environment:
  center:
  host:
  jvm:
  context:
  version:
  deployedAt:
  deployedBy:
  configVersion:
  rollbackArtifact:
  evidence:
```

---

# 25. PDMG Deployment 현재 GAP

## FIG-OPS-19. Current Mapping Gap

```text
PDMG Module / Source
      ↓
Artifact
      ↓
[ GAP ]
      ↓
Production Host
      ↓
JVM
      ↓
Context / Port
```

현재 이를 전수 증명하는 Deployment Manifest가 필요하다.

---

# 26. Development Deployment vs Production Deployment

## FIG-OPS-20. Tool Boundary

```text
Development / CI
GitLab Runner
      │
      ▼
DEV / Test Runtime

Production
Approved Change
      │
      ▼
eCAMS Strategy
      │
      ▼
PROD Runtime
```

### 주의

이것은 Architecture Strategy의 책임경계다.
현재 실제 Pipeline 구현은 별도 Evidence로 확인해야 한다.

---

# 27. Release Evidence

## FIG-OPS-21. Release Evidence Package

```text
Release
│
├─ Source Commit
├─ Build Log
├─ Test Result
├─ Architecture Rule Result
├─ Artifact Hash
├─ Security Scan
├─ Deployment Manifest
├─ Config Version
├─ Runtime Smoke Test
├─ Monitoring Check
└─ Rollback Point
```

---

# 28. Rollback

## FIG-OPS-22. Rollback Chain

```text
Deployment
   ↓
Runtime Verification
   │
   ├─ PASS → Continue
   │
   └─ FAIL
        ↓
      Rollback Decision
        ↓
      Previous Artifact
        +
      Compatible Config
        ↓
      Verify
```

---

# 29. Rollback가 어려운 경우

```text
DB Schema incompatible
Data migration irreversible
External Contract changed
Token/Key changed
Event Schema incompatible
```

이 경우 단순 WAR rollback으로 끝나지 않는다.

---

# 30. Database Change Delivery

## FIG-OPS-23. DB Change

```text
Schema / SQL Change
      ↓
Migration Script
      ↓
Review
      ↓
Test
      ↓
Deployment
      ↓
Validation
      ↓
Rollback / Forward Fix
```

### 금지

```text
Application Runtime DDL
```

을 기본 운영전략으로 사용하지 않는다.

---

# 31. ImageLog Runtime DDL Risk

8장 Current 분석에서 ImageLog 관련 Runtime DDL 가능성이 있다면:

```text
Application
   ↓
Schema Alter
```

은 운영 Governance 위험이다.

TO-BE:

```text
Controlled Migration
```

---

# 32. OM의 위치

## FIG-OPS-24. OM Control Plane

```text
                    OM / Operations
                          │
       ┌──────────────────┼──────────────────┐
       ▼                  ▼                  ▼
    Observe             Control            Govern
 Metric/Log/Trace      approved op       config/drift
       │                  │                  │
       └──────────────────┼──────────────────┘
                          ▼
                    Runtime Plane
```

---

# 33. Control Plane vs Runtime Plane

```text
Runtime Plane
= 실제 Business 처리

Control Plane
= 상태 관찰 / 승인된 제어 / 운영정보 관리
```

### 원칙

OM이 장애나 네트워크 문제 때문에 멈춰도
Business Runtime이 불필요하게 같이 멈추지 않아야 한다.

---

# 34. pdmg-om Evidence Maturity

## FIG-OPS-25. pdmg-om Maturity

```text
Module Name
   ↓
Source Package?
   ↓
Runtime Process?
   ↓
Metric Collector?
   ↓
Dashboard?
   ↓
Control API?
   ↓
Deployment?
```

현재:

```text
pdmg-om 상세 구현
= [UNKNOWN]
```

---

# 35. pdmg-om에 대해 하면 안 되는 말

```text
pdmg-om이 이미
Thread Dashboard를 제공한다

X

pdmg-om이 이미
Hikari를 실시간 제어한다

X
```

Source/Runtime Evidence 없이는 Target 기능으로만 표현한다.

---

# 36. OM Target Responsibility

## FIG-OPS-26. Target OM Functions

```text
OM Target
│
├─ Runtime Inventory
├─ Application / WAR Status
├─ JVM / Thread Status
├─ Worker Pool
├─ Hikari
├─ Slow Service
├─ Timeout / Overload
├─ Error
├─ Config Baseline
├─ Deployment History
├─ Alert / Incident
└─ Evidence Link
```

---

# 37. OM이 직접 바꾸면 안 되는 것

```text
운영자 클릭
→ 즉시 maxThreads 임의 변경
→ 기록 없음

X
```

런타임 파라미터 변경은:

```text
Request
→ Approval
→ Controlled Change
→ Evidence
```

로 처리한다.

---

# 38. Observability 정의

## FIG-OPS-27. Three Signals + Context

```text
METRIC
무슨 상태인가?

LOG
무슨 일이 있었는가?

TRACE
어디를 지나갔는가?

+
CONTEXT
GUID / ServiceId / Deployment / Host
```

---

# 39. Monitoring vs Observability

```text
Monitoring
= 미리 정의한 상태 감시

Observability
= 내부상태를 외부 Evidence로 추론
```

둘 다 필요하다.

---

# 40. Transaction Observability

## FIG-OPS-28. End-to-End Transaction

```text
Client
 ↓
GSLB / L4
 ↓
WEB
 ↓
Tomcat JVM
 ↓
Filter
 ↓
Security
 ↓
TCF
 ↓
ServiceId
 ↓
Worker
 ↓
Transaction
 ↓
DAO / SqlId
 ↓
DB
 ↓
Response
```

각 구간은 GUID/ServiceId로 이어져야 한다.

---

# 41. 거래 Dashboard 질문

```text
현재 가장 느린 ServiceId는?
Timeout 상위 ServiceId는?
Error 상위 ServiceId는?
어느 JVM에서 발생?
Worker Queue는?
Hikari Pending은?
어느 SqlId가 느린가?
```

---

# 42. ServiceId 중심 운영

## FIG-OPS-29. Service View

```text
ServiceId
│
├─ TPS
├─ p50
├─ p95
├─ p99
├─ Error Rate
├─ Timeout
├─ Overload
├─ SQL Time
└─ External Time
```

---

# 43. GUID 중심 장애추적

## FIG-OPS-30. Trace View

```text
GUID
 ↓
Entry
 ↓
ServiceId
 ↓
JVM
 ↓
Worker
 ↓
SQL
 ↓
Error
 ↓
Response
```

---

# 44. JVM Monitoring

## FIG-OPS-31. JVM Dashboard

```text
JVM
│
├─ CPU
├─ Heap Used
├─ Heap Max
├─ GC Count / Pause
├─ Metaspace
├─ Thread Count
├─ Deadlock
├─ Uptime
└─ Restart Count
```

---

# 45. Tomcat Monitoring

```text
Tomcat
│
├─ Current Threads
├─ Busy Threads
├─ Max Threads
├─ Connection Count
├─ Request Rate
├─ Response Time
└─ Error
```

---

# 46. Worker Monitoring

## FIG-OPS-32. PDMG Worker

```text
Worker Pool
│
├─ pool-size
├─ active
├─ queue depth
├─ queue capacity
├─ rejected
├─ task duration
└─ timeout count
```

Current snapshot:

```text
Pool 20
Queue 100
```

은 기준점이지 Target Alert Threshold가 아니다.

---

# 47. Tomcat / Worker / Hikari를 같이 봐야 한다

## FIG-OPS-33. Resource Chain

```text
Tomcat Busy
   ↓
Worker Active
   ↓
Worker Queue
   ↓
Hikari Active/Pending
   ↓
DB Session / SQL Wait
```

---

# 48. Hikari Monitoring

## FIG-OPS-34. Pool Dashboard

```text
Hikari
│
├─ Active
├─ Idle
├─ Pending
├─ Max
├─ Acquire Time
├─ Connection Timeout
└─ Connection Lifetime
```

---

# 49. Hikari 병목 진단

```text
Pending ↑
  ↓
Active == Max?
  │
  ├─ YES
  │    ↓
  │ SQL slow / pool small / DB slow
  │
  └─ NO
       ↓
     app/thread/config issue
```

---

# 50. DB Monitoring

## FIG-OPS-35. DB Runtime View

```text
DB
│
├─ Sessions
├─ Active Sessions
├─ CPU
├─ I/O
├─ Wait Events
├─ Locks
├─ Long SQL
├─ Execution Count
└─ Error
```

---

# 51. SQL Observability

```text
ServiceId
  ↓
DAO
  ↓
Mapper / SqlId
  ↓
Elapsed
  ↓
Rows
  ↓
Wait / Error
```

---

# 52. Slow SQL View

## FIG-OPS-36. Slow SQL Drill-down

```text
Slow ServiceId
   ↓
Slow SqlId
   ↓
SQL
   ↓
Execution Plan / Wait
   ↓
Table / Index
```

---

# 53. Timeout Monitoring

## FIG-OPS-37. Timeout Dashboard

```text
Timeout
│
├─ Client
├─ Gateway / WEB
├─ Worker Deadline
├─ Hikari Acquire
├─ JDBC Query
└─ External
```

### 핵심

```text
504 Count
만으로 Timeout 원인을 알 수 없다.
```

---

# 54. PDMG 504 진단

```text
HTTP 504
   ↓
Worker still active?
   ↓
SQL still running?
   ↓
Rollback?
   ↓
Late Commit?
```

---

# 55. Overload Monitoring

## FIG-OPS-38. 503 Overload

```text
503
  ↓
Worker Active
  ↓
Queue Full?
  ↓
Rejected Count
  ↓
DB / External Cause?
```

---

# 56. Timeout vs Overload Dashboard

```text
Timeout = 실행은 받았지만 Deadline 초과

Overload = 실행 Capacity 부족 / Reject
```

두 지표를 합치지 않는다.

---

# 57. Error Monitoring

## FIG-OPS-39. Error Taxonomy Dashboard

```text
Errors
│
├─ Validation
├─ AuthN
├─ AuthZ
├─ Business
├─ Handler Not Found
├─ Timeout
├─ Overload
├─ DB
├─ External
└─ Unknown
```

---

# 58. Unknown Error는 별도 지표

```text
Unknown Exception Rate
```

가 증가하면:

```text
Error Standard Gap
Regression
Unhandled Runtime Path
```

가능성을 조사한다.

---

# 59. JWT Monitoring

## FIG-OPS-40. Security Dashboard

```text
JWT
│
├─ Login Success/Fail
├─ Token Issue
├─ Signature Fail
├─ Expired
├─ Unknown kid
├─ JWKS Error
├─ Refresh Fail
├─ Revoked Token
└─ Authorization Denied
```

---

# 60. Current JWT Critical Operations View

8장에서 확인한 핵심 Risk:

```text
RS256 Issuer
    vs
HMAC Verifier
```

운영에서 반드시 다음을 확인한다.

```text
Algorithm
kid
Key Source
Verifier Type
Token Success/Fail
```

---

# 61. JWT Key Monitoring

## FIG-OPS-41. Key Consistency

```text
JWT #1
kid=K2
key fingerprint=F2

JWT #2
kid=K2
key fingerprint=F2

DR JWT
kid=K2
key fingerprint=F2
```

불일치:

```text
CRITICAL ALERT
```

---

# 62. JWKS Monitoring

```text
JWKS Availability
JWK Count
Active kid
Key Fingerprint
Cache Refresh Error
Unknown kid Rate
```

---

# 63. Refresh / Denylist Monitoring

```text
Refresh Success
Refresh Failure
Refresh Reuse
Revocation
Denylist Lookup
Token Family
```

실제 Enforcement 여부는 Source/Runtime 검증이 필요하다.

---

# 64. Identity Binding Security Alert

## FIG-OPS-42. Identity Mismatch

```text
JWT sub = USER-A
Header optr_eno = USER-B
       │
       ▼
Mismatch
       │
       ▼
Reject / Audit / Alert
```

Target Security Control 후보다.

---

# 65. Log Architecture

## FIG-OPS-43. Log Channels

```text
Runtime
│
├─ Access Log
├─ Application Log
├─ Transaction Log
├─ SQL Log
├─ Security Audit
├─ ImageLog
└─ Deployment Log
```

---

# 66. Log Correlation

```text
GUID
ServiceId
Host
JVM
Thread
User
ErrorCode
SqlId
DeploymentId
```

가능한 범위에서 공통 Correlation Field를 유지한다.

---

# 67. ImageLog vs Application Log

## FIG-OPS-44. Evidence Difference

```text
Application Log
= 실행 상세 / 진단

ImageLog
= 요청/응답/오류 운영 Evidence
```

둘을 동일시하지 않는다.

---

# 68. ImageLog Fail-open Alert

```text
Business Success
+
ImageLog Failure
```

일 수 있으므로:

```text
Audit Write Failure
```

를 별도 Alert해야 한다.

---

# 69. Sensitive Log Control

## FIG-OPS-45. Log Security Gate

```text
Log Event
  ↓
Sensitive?
  ├─ Token
  ├─ Password
  ├─ Private Key
  ├─ Secret
  └─ Personal Data
  ↓
Mask / Drop / Secure Audit
```

---

# 70. Log Retention

필요 정책:

```text
Application Log Retention
Security Audit Retention
ImageLog Retention
Deployment Log Retention
Evidence Retention
```

정확한 기간은 현재 자료로 임의 확정하지 않는다.

---

# 71. Trace Architecture

## FIG-OPS-46. Trace Chain

```text
GUID / TraceId
    │
    ├─ WEB
    ├─ WAS
    ├─ TCF
    ├─ Worker
    ├─ SQL
    ├─ External
    └─ Response
```

---

# 72. Trace Sampling

대규모 환경에서는 Sampling 정책이 필요할 수 있다.

하지만:

```text
Error
Timeout
Security Event
Critical Transaction
```

은 높은 Evidence 보존 필요성이 있다.

정확한 Sampling Rate는 `[TBD]`.

---

# 73. Metric / Log / Trace 연결

## FIG-OPS-47. Triangulation

```text
Metric
"p95 증가"
   ↓
Trace
"어느 구간?"
   ↓
Log
"왜?"
```

---

# 74. Observability Failure

```text
Business 정상
  +
Monitoring System 장애
```

가능하다.

운영 Monitoring 자체도 HA/Health를 가져야 한다.

---

# 75. Observability Dependency 금지

```text
APM Down
   ↓
Business Runtime Down

X
```

Observability는 가능한 한 Business Runtime의 강한 동기 Dependency가 아니어야 한다.

---

# 76. Alert Architecture

## FIG-OPS-48. Alert Lifecycle

```text
Metric / Log / Trace
      ↓
Rule
      ↓
Alert
      ↓
Severity
      ↓
Owner
      ↓
Runbook
      ↓
Action
      ↓
Evidence
```

---

# 77. Alert Quality

좋은 Alert:

```text
무슨 서비스?
무슨 증상?
언제?
얼마나?
어디서?
무슨 Runbook?
```

---

# 78. Alert Storm

## FIG-OPS-49. Alert Correlation

```text
DB Slow
  ↓
Hikari Pending Alert
Worker Queue Alert
Timeout Alert
Service p95 Alert
```

이를 4개의 독립 Incident로 만들지 않도록 Correlation이 필요하다.

---

# 79. Severity

```text
CRITICAL
HIGH
MEDIUM
INFO
```

판정축:

```text
Business Impact
Data Risk
Security Risk
Recovery Urgency
Affected Scope
```

---

# 80. Runbook Architecture

## FIG-OPS-50. Runbook Flow

```text
Symptom
  ↓
Check
  ↓
Diagnosis
  ↓
Decision
  ↓
Action
  ↓
Validation
  ↓
Escalation
  ↓
Evidence
```

---

# 81. Runbook은 증상 중심

나쁜 시작:

```text
Tomcat을 재시작한다
```

좋은 시작:

```text
"504 급증"
→ Worker?
→ Hikari?
→ SQL?
→ External?
```

---

# 82. 503 Runbook

## FIG-OPS-51. Overload Runbook

```text
503
 ↓
Worker Rejected?
 ↓
Queue Full?
 ↓
Active Worker?
 ↓
Hikari Pending?
 ↓
DB / External?
 ↓
Scale / Recover / Throttle
```

---

# 83. 504 Runbook

## FIG-OPS-52. Timeout Runbook

```text
504
 ↓
ServiceId
 ↓
Worker Task Duration
 ↓
SQL / External
 ↓
TX Outcome
 ↓
Late Commit Check
 ↓
Recovery
```

---

# 84. JVM High CPU Runbook

```text
CPU High
 ↓
GC?
 ↓
Busy Thread?
 ↓
Hot Method?
 ↓
SQL Wait?
 ↓
Thread Dump / Profile
 ↓
Action
```

---

# 85. Hikari Exhaustion Runbook

## FIG-OPS-53. Pool Exhaustion

```text
Pending ↑
 ↓
Active == Max?
 ↓
Long SQL?
 ↓
Connection Leak?
 ↓
DB Slow?
 ↓
Pool Size / SQL / DB Action
```

---

# 86. JWT Error Runbook

```text
JWT Failure
 ↓
Expired?
 ↓
Unknown kid?
 ↓
Signature?
 ↓
JWKS?
 ↓
Clock?
 ↓
Key Consistency?
```

---

# 87. ImageLog Failure Runbook

```text
ImageLog Error
 ↓
DB?
 ↓
Schema?
 ↓
Permission?
 ↓
Runtime DDL?
 ↓
Business Impact?
 ↓
Audit Gap Reconcile
```

---

# 88. Event Lag Runbook

```text
Consumer Lag
 ↓
Producer Burst?
 ↓
Consumer Down?
 ↓
Partition?
 ↓
External/DB Slow?
 ↓
Scale / Restart / Replay
```

---

# 89. CDC Lag Runbook

```text
CDC Lag
 ↓
Capture?
 ↓
Network?
 ↓
Relay?
 ↓
Apply?
 ↓
Target DB?
 ↓
Restart / Catch-up / Reconcile
```

---

# 90. Batch Delay Runbook

```text
Job Late
 ↓
Scheduler?
 ↓
Previous Dependency?
 ↓
DB?
 ↓
Data Volume?
 ↓
Retry/Restart?
```

---

# 91. Runtime Inventory

## FIG-OPS-54. Runtime Inventory Chain

```text
System
  ↓
Application
  ↓
Artifact
  ↓
Host
  ↓
JVM
  ↓
WAR
  ↓
ServiceId
  ↓
Runtime Metric
```

---

# 92. Runtime Inventory 최소 필드

```yaml
runtime:
  system:
  application:
  environment:
  center:
  host:
  jvm:
  artifact:
  artifactHash:
  context:
  version:
  serviceIds:
  datasource:
  deploymentId:
  monitoring:
  status:
```

---

# 93. Capacity Baseline vs Runtime

## FIG-OPS-55. Capacity Drift

```text
Capacity Design
  │
  ├─ VM Size
  ├─ Thread
  ├─ Worker
  ├─ Hikari
  └─ Session
  │
  ▼ compare
Actual Config
  │
  ▼ compare
Runtime Metric
  │
  ▼
PASS / DRIFT
```

---

# 94. Candidate Threshold ≠ Alert Threshold

```text
Capacity Design에서 70%
=
Production Alert 70%

자동 아님
```

실제 Alert Threshold는:

```text
Load Test
+
Production Baseline
+
SLO
```

로 확정한다.

---

# 95. Config Baseline Registry

## FIG-OPS-56. Config Baseline

```text
Component
│
├─ Expected Config
├─ Actual Config
├─ Source
├─ Effective Time
└─ Drift
```

예:

```text
Tomcat maxThreads
Worker Pool
Hikari maxPoolSize
Session Timeout
JWT kid
Filter Order
```

---

# 96. Config Drift

```text
Expected
timeout=4000
    │
    ▼
Actual
timeout=5000
    │
    ▼
DRIFT
```

---

# 97. Deployment Drift

## FIG-OPS-57. Deployment Architecture Drift

```text
Architecture
WAR A → JVM A → Host Group A
        │
        │ compare
        ▼
Runtime Inventory
WAR A → JVM B → Host Group B
        │
        ▼
DRIFT
```

---

# 98. Source Drift

```text
Baseline Commit
    │
    ▼
Production Artifact
    │
    ▼
Different Commit?
```

---

# 99. Artifact Drift

```text
Manifest Hash
   │
   │ compare
   ▼
Runtime Artifact Hash
```

---

# 100. Schema Drift

## FIG-OPS-58. DB Drift

```text
Expected Schema
    │
    ▼ compare
Actual Schema
    │
    ▼
Missing / Extra / Modified
```

---

# 101. JWT Drift

```text
Expected
alg = RS256
kid = K2
fingerprint = F2

Actual
alg = HS256 / kid K2 / F3

→ CRITICAL DRIFT
```

---

# 102. ServiceId Drift

```text
Model
13 ServiceIds
   │
   ▼ compare
Runtime Registry
? ServiceIds
```

기동 시 실제 Registry Evidence를 남길 수 있다면 가장 강한 검증이 된다.

---

# 103. Filter Order Drift

```text
Old Doc
order=1

Current Source
HIGHEST_PRECEDENCE+20
```

운영 Baseline은 Source/Config를 기준으로 최신화한다.

---

# 104. Deployment Verification

## FIG-OPS-59. Post-deploy Verification

```text
Deploy
 ↓
Process Up
 ↓
Health
 ↓
Correct Version
 ↓
Correct Config
 ↓
Correct Key
 ↓
Smoke ServiceId
 ↓
Monitoring
 ↓
Release Complete
```

---

# 105. Smoke Test

대표:

```text
Health
Login / JWT
Read-only Business
DB Connectivity
External Connectivity
Critical ServiceId
```

실제 Smoke Scenario는 서비스별 승인한다.

---

# 106. Runtime Verification

```text
Artifact Version?
Config Version?
JVM?
Thread?
Datasource?
JWT kid?
ServiceId Registry?
```

---

# 107. Release Gate

## FIG-OPS-60. Operations Release Gate

```text
Source fixed?
 ↓
Build PASS?
 ↓
Architecture Rule PASS?
 ↓
Test PASS?
 ↓
Security PASS?
 ↓
Artifact identified?
 ↓
Deployment manifest?
 ↓
Runtime health?
 ↓
Monitoring active?
 ↓
Rollback ready?
 ↓
PASS
```

---

# 108. Runtime Evidence Gate

## FIG-OPS-61. Evidence Gate

```text
Deployment ID
    ↓
Runtime Scenario
    ↓
GUID / TraceId
    ↓
Metric / Log
    ↓
Expected Outcome
    ↓
Evidence Manifest
```

---

# 109. Runtime Evidence 없는 PASS 금지

```text
Deploy 성공
+
Process 살아 있음
```

만으로:

```text
Architecture PASS
```

라 할 수 없다.

---

# 110. Release Evidence Package

## FIG-OPS-62. Evidence Pack

```text
RELEASE
│
├─ Architecture Baseline ID
├─ Source Commit
├─ Build ID
├─ Artifact Hash
├─ Deployment ID
├─ Config Version
├─ Security Scan
├─ Test Result
├─ Runtime Smoke
├─ Performance / Failure [필요 시]
├─ Monitoring Screenshot/Export
├─ Rollback Point
└─ Approval
```

---

# 111. Runtime Evidence Chain

```text
architectureBaselineId
        ↓
modelVersion
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
traceId / GUID
        ↓
runtimeEvidence
```

---

# 112. OM Dashboard — Executive View

## FIG-OPS-63. Top-level Dashboard

```text
SYSTEM HEALTH
│
├─ Availability
├─ TPS
├─ p95
├─ Error Rate
├─ Timeout
├─ Overload
├─ Critical Alert
└─ Deployment Version
```

---

# 113. OM Dashboard — Service View

```text
ServiceId
├─ TPS
├─ p95
├─ p99
├─ Error
├─ Timeout
├─ SQL Time
├─ External Time
└─ Instances
```

---

# 114. OM Dashboard — Instance View

## FIG-OPS-64. Instance Drill-down

```text
Host
 ↓
JVM
 ↓
WAR
 ↓
ServiceId
 ↓
Thread / Worker
 ↓
Hikari
 ↓
SQL
```

---

# 115. OM Dashboard — JVM View

```text
JVM
├─ CPU
├─ Heap
├─ GC
├─ Threads
├─ Uptime
├─ Restart
└─ Version
```

---

# 116. OM Dashboard — DB Pool View

```text
Datasource
├─ Active
├─ Idle
├─ Pending
├─ Max
└─ Acquire Time
```

---

# 117. OM Dashboard — Error View

```text
Error Code
├─ Count
├─ ServiceId
├─ JVM
├─ GUID
└─ Trend
```

---

# 118. OM Dashboard — Security View

```text
Login Fail
JWT Verify Fail
Unknown kid
Revocation
Authorization Denied
Identity Mismatch
```

---

# 119. OM Dashboard — Deployment View

```text
Environment
 ↓
System
 ↓
Artifact Version
 ↓
Deployment Time
 ↓
Commit
 ↓
Operator
 ↓
Status
```

---

# 120. OM Dashboard — Drift View

```text
Config Drift
Deployment Drift
Source Drift
Schema Drift
Security Drift
Runtime Drift
```

---

# 121. Service Level View

## FIG-OPS-65. SLO Dashboard

```text
Service
│
├─ Availability SLO
├─ Latency SLO
├─ Error SLO
└─ Freshness SLO [Data]
```

SLO는 반드시 측정지점을 가진다.

---

# 122. CDC Freshness View

```text
Source Commit
  ↓
Capture
  ↓
Transport
  ↓
Apply
  ↓
Consumer Visible
```

30초/3초 Conflict를 Dashboard에 임의 반영하지 않는다.
승인된 SLA가 필요하다.

---

# 123. BI / ETL View

```text
ETL Job
├─ Duration
├─ Rows In/Out
├─ Reject
└─ Delay

BI
├─ Query Duration
├─ Concurrency
└─ Heavy Query
```

---

# 124. Event View

```text
Topic / Stream
├─ Produce Rate
├─ Consume Rate
├─ Lag
├─ Retry
└─ DLQ
```

---

# 125. File View

```text
InterfaceId
├─ File Name
├─ Size
├─ Hash
├─ Record Count
├─ Status
└─ Retry
```

---

# 126. HA Monitoring

## FIG-OPS-66. HA View

```text
Service VIP
│
├─ Member #1 UP
├─ Member #2 UP
└─ Residual Capacity
```

---

# 127. Node Failure Evidence

```text
Node Down
 ↓
Detection
 ↓
Member Remove
 ↓
Traffic Shift
 ↓
p95 / Error
 ↓
Evidence
```

---

# 128. DR Monitoring

## FIG-OPS-67. DR Readiness

```text
DR
│
├─ Artifact Sync
├─ Config Sync
├─ Key Sync
├─ Data Replication
├─ Route Ready
├─ External Ready
├─ Runbook
└─ Last DR Test
```

---

# 129. DR Runtime Evidence

```text
Failover Start
 ↓
Route
 ↓
Application
 ↓
Security
 ↓
Data
 ↓
Business Validation
 ↓
RTO/RPO
```

---

# 130. Backup Monitoring

```text
Backup Job Success
Restore Test Date
Restore Duration
Validation Result
```

Backup 성공만으로 Recovery Ready로 표시하지 않는다.

---

# 131. Operational Change

## FIG-OPS-68. Runtime Parameter Change

```text
Need Change
 ↓
Change Request
 ↓
Impact Review
 ↓
Approval
 ↓
Apply
 ↓
Runtime Verify
 ↓
Evidence
 ↓
Baseline Update
```

---

# 132. Dynamic Config 주의

실시간 Config 변경이 가능하더라도:

```text
Who
What
Before
After
Why
When
Approval
```

가 남아야 한다.

---

# 133. Config as Code

## FIG-OPS-69. Configuration Governance

```text
Config Source
  ↓
Version Control
  ↓
Review
  ↓
Deploy
  ↓
Runtime Compare
```

---

# 134. Security in CI/CD

## FIG-OPS-70. Security Gate

```text
Source
 ↓
Secret Scan
 ↓
Dependency Scan
 ↓
Security Test
 ↓
Artifact
 ↓
Key/Secret Injection
 ↓
Runtime Security Check
```

---

# 135. Private Key CI/CD 원칙

```text
Private Key
≠ Build Artifact content
```

Key는 Deployment/Runtime Security Boundary에서 주입한다.

---

# 136. Database Change Governance

```text
Migration
 ↓
Review
 ↓
Test
 ↓
Approval
 ↓
Apply
 ↓
Schema Verify
 ↓
Evidence
```

---

# 137. Log Configuration Change

```text
Log Level
Masking
Appender
Retention
```

도 Config Baseline으로 관리한다.

---

# 138. Debug Log 운영 위험

```text
DEBUG ON
 ↓
Volume ↑
Sensitive Info Risk ↑
I/O ↑
```

긴급변경은 만료시간을 둔다.

---

# 139. Observability Maturity

## FIG-OPS-71. Maturity Model

```text
L0
로그만 있음
  ↓
L1
Metric 있음
  ↓
L2
Dashboard
  ↓
L3
Alert / Runbook
  ↓
L4
Trace / Correlation
  ↓
L5
Drift / Evidence / Architecture Closed Loop
```

---

# 140. Current vs Target

```text
PDMG Current
GUID / MDC / ImageLog / Error / Source Runtime
        │
        ▼
Target Operations
Central Metric / Trace / Dashboard / Alert / Runbook / Drift / Evidence
```

---

# 141. Operations Closed Loop

## FIG-OPS-72. Operations → Architecture

```text
Deploy
 ↓
Operate
 ↓
Observe
 ↓
Incident / Drift
 ↓
GAP
 ↓
ADR
 ↓
Standard / Code Change
 ↓
Deploy
```

---

# 142. Incident → Architecture

```text
Incident
  ↓
Root Cause
  ↓
Was architecture rule missing?
  ↓
Rule / Test / Baseline Update
```

---

# 143. Problem Management

```text
Repeated Incident
   ↓
Problem
   ↓
Root Cause
   ↓
Architecture Debt
   ↓
Permanent Fix
```

---

# 144. Architecture Drift Categories

```text
Document
Model
Source
Config
Deployment
Runtime
Security
Data
```

---

# 145. Drift Handling

## FIG-OPS-73. Drift Process

```text
Detect
 ↓
Classify
 ↓
Severity
 ↓
Owner
 ↓
Fix / Accept / ADR
 ↓
Verify
 ↓
Close
```

---

# 146. Critical Drift 예

```text
JWT alg mismatch
Direct WAS bypass
Timeout late commit
Wrong production artifact
DR key mismatch
```

---

# 147. High Drift 예

```text
Session config mismatch
Hikari baseline mismatch
Filter order doc drift
UI Service Catalog drift
```

---

# 148. Operations KPI

```text
Availability
p95
Error Rate
Timeout Rate
Overload Rate
MTTD
MTTR
Deployment Failure Rate
Rollback Rate
Critical Drift
Evidence Coverage
```

---

# 149. Architecture KPI와 Operations KPI

```text
Operations KPI
= 실제 운영 품질

Architecture KPI
= 설계-구현-운영 정합성
```

둘을 함께 본다.

---

# 150. Change Failure Rate

```text
Deployments
  ↓
Incident / Rollback?
  ↓
Change Failure Rate
```

---

# 151. Deployment Frequency

단순 많이 배포하는 것이 목표가 아니라:

```text
Safe
Traceable
Recoverable
```

배포가 목표다.

---

# 152. MTTR

```text
Detect
 ↓
Diagnose
 ↓
Recover
 ↓
Validate
```

Observability와 Runbook이 MTTR에 직접 영향을 준다.

---

# 153. Evidence Coverage

```text
Critical Services with Runtime Evidence
---------------------------------------
Total Critical Services
```

Critical Service는 100%를 목표로 할 수 있으나 실제 목표값은 승인 필요.

---

# 154. Deployment Trace Coverage

```text
Mapped Runtime Artifacts
------------------------
Deployed Artifacts
```

---

# 155. Service Trace Coverage

```text
ServiceIds traced to SQL/Deployment/Evidence
-------------------------------------------
Total ServiceIds
```

---

# 156. Operations RACI

## TEXT ARCHITECTURE 보완 — Operations RACI

```text
Build / Deploy / Monitor / Recover
        │
        ├─ Dev
        ├─ Architect
        ├─ Ops
        ├─ Security
        ├─ DBA / Data
        └─ PMO
             │
             ▼
        R / A / C / I
```


| Activity | Dev | Architect | Ops | Security | DBA/Data | PMO |
|---|---|---|---|---|---|---|
| Build | R | C | I | C | I | I |
| Architecture Rule | C | A/R | I | C | C | I |
| Deploy | C | C | A/R | C | C | I |
| Monitor | C | C | A/R | C | C | I |
| Security Incident | C | C | C | A/R | I | I |
| DB Incident | C | C | C | I | A/R | I |
| Drift Review | C | A/R | R | C | C | I |
| Baseline Release | C | A/R | C | C | C | A/C |

실제 조직체계에 따라 확정한다.

---

# 157. OM / Ops 권한 분리

## FIG-OPS-74. Read vs Change Permission

```text
Viewer
→ Read Dashboard

Operator
→ Approved Operational Action

Admin
→ Config / Control

Architect
→ Baseline / Rule

Security
→ Security Control
```

---

# 158. Break-glass

긴급 운영권한은:

```text
Emergency
 ↓
Temporary Elevated Access
 ↓
Action
 ↓
Audit
 ↓
Expiry
 ↓
Review
```

가 필요하다.

---

# 159. Operations Security Audit

```text
Login
Privilege Change
Config Change
Deployment
Runtime Control
Key Rotation
DR Action
```

은 Audit 대상 후보다.

---

# 160. Operations Evidence Repository

## FIG-OPS-75. Evidence Repository

```text
Evidence
│
├─ Build
├─ Test
├─ Deployment
├─ Runtime
├─ Performance
├─ Security
├─ HA
├─ DR
└─ Backup/Restore
```

---

# 161. Evidence Manifest

```yaml
evidence:
  evidenceId:
  baselineId:
  sourceCommit:
  buildId:
  artifactHash:
  deploymentId:
  environment:
  serviceId:
  traceId:
  scenario:
  result:
  metrics:
  logs:
  owner:
  createdAt:
  evidenceHash:
```

---

# 162. Evidence Integrity

```text
Evidence File
  ↓
Hash
  ↓
Manifest
  ↓
Release Record
```

---

# 163. Evidence Retention

정확한 기간은 정책 확정이 필요하나:

```text
Release Audit
Security Audit
DR Evidence
Architecture Baseline
```

과 연계되어야 한다.

---

# 164. Operations Gate

## FIG-OPS-76. Operations Gate

```text
Build Artifact Identified?
 ↓
Deployment Manifest?
 ↓
Config Baseline?
 ↓
Monitoring Active?
 ↓
Security Check?
 ↓
Smoke Test?
 ↓
Rollback Ready?
 ↓
Runtime Evidence?
 ↓
No Critical Drift?
 ↓
PASS
```

---

# 165. Architecture Closed Loop와 연결

```text
09 Operations
   ↓
Deployment Evidence
   ↓
Runtime Evidence
   ↓
Drift
   ↓
07 Closed Loop
   ↓
GAP / ADR
   ↓
New Baseline
```

---

# 166. X장 Naming / Traceability와 연결

기존 Architecture 구조의 X장 관점으로 보면:

```text
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

9장은 그 중:

```text
Artifact
Deployment
Runtime
Evidence
```

를 운영 Control Plane에서 담당한다.

---

# 167. OM 대상 Metric Matrix

## TEXT ARCHITECTURE 보완 — OM 대상 Metric Matrix

```text
ServiceId
  ↓
Tomcat
  ↓
Worker
  ↓
Hikari
  ↓
DB / SQL
  ↓
Error / Timeout / Security
  ↓
Alert / Runbook
```


| 대상 | 핵심 Metric |
|---|---|
| ServiceId | TPS/p95/Error/Timeout |
| Tomcat | Busy/Max Threads |
| Worker | Active/Queue/Reject |
| JVM | CPU/Heap/GC/Thread |
| Hikari | Active/Idle/Pending |
| DB | Session/Wait/SQL |
| JWT | Verify/Unknown kid/Auth Fail |
| Event | Lag/Retry/DLQ |
| CDC | Capture/Apply Lag |
| ETL | Duration/Rows/Reject |
| Batch | Status/Delay/Restart |
| File | Transfer/Hash/Record |
| Deployment | Version/Hash/Status |

---

# 168. Monitoring Requirement — Transaction

```text
GUID
ServiceId
Start Time
End Time
Elapsed
HTTP Status
Error Code
Host/JVM
```

---

# 169. Monitoring Requirement — Thread

```text
Tomcat Busy
Worker Active
Worker Queue
Rejected
Thread Dump Trigger
```

---

# 170. Monitoring Requirement — DB

```text
Hikari Pending
Connection Acquire
SQL Elapsed
DB Wait
Lock
```

---

# 171. Monitoring Requirement — Security

```text
Authentication Fail
Authorization Denied
JWT Verify Fail
Unknown kid
Identity Mismatch
```

---

# 172. Monitoring Requirement — Deployment

```text
Commit
Artifact Hash
Deployment ID
Host
JVM
Version
Health
```

---

# 173. Runtime Test Scenario — Deploy

```text
Deploy Artifact
 ↓
Health
 ↓
Smoke ServiceId
 ↓
DB
 ↓
JWT
 ↓
Monitoring
 ↓
Evidence
```

---

# 174. Runtime Test Scenario — 503

```text
Saturate Worker
 ↓
Queue Full
 ↓
503
 ↓
Alert
 ↓
Runbook
 ↓
Recovery
```

---

# 175. Runtime Test Scenario — 504

```text
Slow SQL
 ↓
Worker Deadline
 ↓
504
 ↓
Rollback
 ↓
Late Commit check
 ↓
Evidence
```

---

# 176. Runtime Test Scenario — Hikari

```text
Hold Connections
 ↓
Pending ↑
 ↓
Alert
 ↓
Diagnosis
 ↓
Recovery
```

---

# 177. Runtime Test Scenario — JWT Key

```text
Token Issue
 ↓
JWT Restart / Instance Change
 ↓
Verify
 ↓
Unknown kid / Signature?
 ↓
Evidence
```

---

# 178. Runtime Test Scenario — Node Failure

```text
WAS Node Down
 ↓
L4 Detect
 ↓
Traffic Shift
 ↓
p95/Error
 ↓
Residual Capacity
 ↓
Evidence
```

---

# 179. Runtime Test Scenario — DR

```text
Main Failure
 ↓
DR Route
 ↓
App
 ↓
JWT
 ↓
DB
 ↓
Business
 ↓
RTO/RPO
```

---

# 180. Architecture Conformance Rules — Operations

```text
R-OPS-ARTIFACT-HASH
R-OPS-DEPLOYMENT-MANIFEST
R-OPS-CONFIG-BASELINE
R-OPS-RUNTIME-INVENTORY
R-OPS-SERVICE-METRIC
R-OPS-WORKER-METRIC
R-OPS-HIKARI-METRIC
R-OPS-JWT-KEY-CONSISTENCY
R-OPS-ALERT-RUNBOOK
R-OPS-DR-EVIDENCE
R-OPS-RESTORE-EVIDENCE
R-OPS-CRITICAL-DRIFT
```

---

# 181. R-OPS-ARTIFACT-HASH

```text
Deployment Artifact
   ↓
Hash exists?
   ├─ YES
   └─ NO → FAIL
```

---

# 182. R-OPS-DEPLOYMENT-MANIFEST

```text
Artifact
→ Host/JVM/Context
```

전수 Mapping이 있어야 한다.

---

# 183. R-OPS-CONFIG-BASELINE

```text
Expected Config
 vs
Actual Config
```

Critical Difference는 FAIL 후보.

---

# 184. R-OPS-RUNTIME-INVENTORY

```text
Running JVM
 ↓
Known Deployment?
```

UNKNOWN Runtime Process는 운영 Risk다.

---

# 185. R-OPS-JWT-KEY-CONSISTENCY

```text
JWT Instances
 ↓
same kid?
same key fingerprint?
```

불일치:

```text
CRITICAL FAIL
```

---

# 186. R-OPS-ALERT-RUNBOOK

```text
Critical Alert
 ↓
Runbook exists?
```

없으면 Operations Gate 조건 미완료 후보.

---

# 187. R-OPS-DR-EVIDENCE

```text
Critical Service
 ↓
Recent DR Evidence?
```

정책 Window는 별도 승인한다.

---

# 188. R-OPS-RESTORE-EVIDENCE

```text
Backup
 ↓
Restore Test Evidence?
```

---

# 189. R-OPS-CRITICAL-DRIFT

```text
Critical Drift Count
= 0
```

HG90 후보조건과 연결한다.

---

# 190. CONFIRMED / WORKING BASELINE

```text
[CONFIRMED / WORKING BASELINE]

- NSIGHT Strategy에 GitLab / GitLab Runner / eCAMS 방향 존재
- PDMG Java 21 / Spring Boot 3.5.14 / Gradle Multi-project
- pdmg-service와 pdmg-jwt가 pdmg-fw를 사용
- PDMG Runtime에 GUID/MDC/ImageLog/Error/Worker/Timeout 관측지점 존재
- Runtime Evidence는 Deployment/ServiceId/GUID와 연결해야 함
- OM은 Control Plane으로 설계
- pdmg-om 실제 상세 구현은 UNKNOWN
- Capacity Design 값과 Alert Threshold를 구분
- Release PASS에는 Runtime Evidence가 필요
```

---

# 191. OPEN

```text
[OPEN-OPS-01]
Current GitLab CI Pipeline 구현

[OPEN-OPS-02]
Current eCAMS 실제 배포 Mapping

[OPEN-OPS-03]
pdmg-om Source / Runtime

[OPEN-OPS-04]
Current Metric Exporter / APM Agent

[OPEN-OPS-05]
Central Log Platform

[OPEN-OPS-06]
Trace Platform

[OPEN-OPS-07]
Alert Threshold

[OPEN-OPS-08]
Runbook Repository

[OPEN-OPS-09]
Deployment Manifest 자동생성

[OPEN-OPS-10]
Runtime Inventory 자동수집

[OPEN-OPS-11]
Evidence Repository

[OPEN-OPS-12]
DR/Restore Evidence 주기
```

---

# 192. GAP Register

## TEXT ARCHITECTURE 보완 — GAP Register

```text
Expected / Required
      │
      ▼
Actual / Evidence
      │
      ▼
Difference
      │
      ▼
[GAP]
      │
      ├─ Owner
      ├─ Action
      ├─ ADR
      └─ Evidence
```


| ID | GAP | 영향 |
|---|---|---|
| GAP-OPS-01 | Current CI/CD Pipeline Evidence 미확인 | DevOps |
| GAP-OPS-02 | Production Deployment Manifest 미완료 | Deployment |
| GAP-OPS-03 | pdmg-om Source/Runtime 미확인 | OM |
| GAP-OPS-04 | Central Metric Collection 미확인 | Observability |
| GAP-OPS-05 | Central Trace 미확인 | Trace |
| GAP-OPS-06 | Runtime Inventory 자동화 미완료 | Operations |
| GAP-OPS-07 | ServiceId Dashboard 미완료 | Observability |
| GAP-OPS-08 | Worker/Hikari 통합 Dashboard 미완료 | Capacity |
| GAP-OPS-09 | JWT Key Consistency Monitoring 미완료 | Security |
| GAP-OPS-10 | Alert Threshold 미확정 | Operations |
| GAP-OPS-11 | Runbook Catalog 미완료 | Recovery |
| GAP-OPS-12 | Config Drift 자동화 미완료 | Governance |
| GAP-OPS-13 | Artifact Drift 자동화 미완료 | Governance |
| GAP-OPS-14 | Evidence Manifest 자동화 미완료 | Evidence |
| GAP-OPS-15 | DR Evidence 자동연결 미완료 | DR |
| GAP-OPS-16 | Restore Evidence 자동연결 미완료 | Backup |
| GAP-OPS-17 | ImageLog Fail-open Alert 미완료 | Audit |
| GAP-OPS-18 | Sensitive Log Masking 전사정책 미확정 | Security |
| GAP-OPS-19 | Runtime Parameter Change Governance 미완료 | Control |
| GAP-OPS-20 | Operations Gate 자동화 미완료 | Release |

---

# 193. RISK Register

## TEXT ARCHITECTURE 보완 — RISK Register

```text
Cause
  ↓
Risk Event
  ↓
Business / Data / Security / Operation Impact
  ↓
Severity
  ↓
Mitigation / Control
  ↓
Owner
  ↓
Evidence / Closure
```


| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-OPS-01 | Strategy Tool 존재를 구현완료로 오판 | High |
| RISK-OPS-02 | Artifact/Host/JVM 추적 불가 | Critical |
| RISK-OPS-03 | 운영서버 수동변경 | Critical |
| RISK-OPS-04 | Secret/Key Source 저장 | Critical |
| RISK-OPS-05 | JWT Instance Key 불일치 | Critical |
| RISK-OPS-06 | Alert Storm | High |
| RISK-OPS-07 | Alert에 Runbook 없음 | High |
| RISK-OPS-08 | 504 원인을 HTTP 레벨로만 판단 | High |
| RISK-OPS-09 | Worker/Hikari/DB 연쇄병목 미탐지 | Critical |
| RISK-OPS-10 | ImageLog 장애 감사공백 | High |
| RISK-OPS-11 | Config Drift 미탐지 | High |
| RISK-OPS-12 | Production Artifact Drift | Critical |
| RISK-OPS-13 | DR Resource만 있고 Runtime Evidence 없음 | Critical |
| RISK-OPS-14 | Backup Success만으로 Recovery 판단 | Critical |
| RISK-OPS-15 | OM이 Business Runtime 강한 Dependency | High |
| RISK-OPS-16 | pdmg-om 기능을 근거 없이 가정 | High |

---

# 194. ADR 후보

```text
ADR-OPS-01 DevOps Tool Responsibility
ADR-OPS-02 Build / Artifact Standard
ADR-OPS-03 Deployment Manifest
ADR-OPS-04 Same Artifact Promotion
ADR-OPS-05 Config / Secret Separation
ADR-OPS-06 OM Control Plane
ADR-OPS-07 pdmg-om Scope
ADR-OPS-08 ServiceId Observability
ADR-OPS-09 Worker/Hikari Monitoring
ADR-OPS-10 JWT Key Monitoring
ADR-OPS-11 Central Log / Trace
ADR-OPS-12 Alert Severity
ADR-OPS-13 Runbook Standard
ADR-OPS-14 Config Drift
ADR-OPS-15 Runtime Evidence Manifest
ADR-OPS-16 DR Evidence
ADR-OPS-17 Restore Evidence
ADR-OPS-18 Operations Release Gate
```

---

# 195. Verification Checklist — DevOps

```text
[ ] Source Commit 고정?
[ ] Build Reproducible?
[ ] Test/Rule 실행?
[ ] Artifact Hash?
[ ] Same Artifact Promotion?
[ ] Secret 분리?
[ ] Deployment Manifest?
[ ] Rollback Point?
```

---

# 196. Verification Checklist — OM

```text
[ ] Runtime Inventory?
[ ] Application/WAR 상태?
[ ] JVM/Thread?
[ ] Worker?
[ ] Hikari?
[ ] ServiceId View?
[ ] Error/Timeout?
[ ] Deployment History?
[ ] Drift?
```

---

# 197. Verification Checklist — Observability

```text
[ ] Metric?
[ ] Log?
[ ] Trace?
[ ] GUID?
[ ] ServiceId?
[ ] Deployment Context?
[ ] SQL Correlation?
[ ] Security Event?
```

---

# 198. Verification Checklist — Alert / Runbook

```text
[ ] Alert Owner?
[ ] Severity?
[ ] Symptom?
[ ] Diagnostic Step?
[ ] Recovery?
[ ] Escalation?
[ ] Evidence?
```

---

# 199. Verification Checklist — Security Operations

```text
[ ] JWT alg?
[ ] kid?
[ ] Key fingerprint?
[ ] JWKS?
[ ] Unknown kid Alert?
[ ] Identity mismatch?
[ ] Secret Scan?
[ ] Sensitive Log Masking?
```

---

# 200. Verification Checklist — HA / DR / Backup

```text
[ ] Node Failover?
[ ] Residual Capacity?
[ ] DR Artifact Sync?
[ ] DR Config Sync?
[ ] DR Key Sync?
[ ] DR Data Ready?
[ ] DR Test Evidence?
[ ] Restore Test Evidence?
```

---

# 201. Completion Gate

## FIG-OPS-77. Operations Completion Gate

```text
Controlled Build?
   ↓ YES
Artifact Identified?
   ↓ YES
Deployment Traceable?
   ↓ YES
Runtime Inventory?
   ↓ YES
Metric / Log / Trace?
   ↓ YES
Alert / Runbook?
   ↓ YES
Security Monitoring?
   ↓ YES
HA/DR/Restore Evidence?
   ↓ YES
Drift Detection?
   ↓ YES
Runtime Evidence?
   ↓ YES
OPERATIONS PASS
```

---

# 202. 다음 장 Handoff

다음 장은 1~9장을 다시 하나로 묶는 **10. ARCHITECTURE BASELINE / NAMING / SERVICEID / TRACEABILITY INTEGRATION**이 자연스럽다.

## FIG-OPS-78. 09 → 10

```text
09 OPERATIONS
│
├─ Artifact
├─ Deployment
├─ Runtime Inventory
├─ Metric / Log / Trace
├─ Evidence
└─ Drift
       │
       ▼
10 INTEGRATION
│
├─ Naming
├─ ServiceId
├─ End-to-End Traceability
├─ Architecture Model
├─ Rule
├─ Gate
└─ Baseline Release
```

---

# 203. 10장에서 반드시 답할 질문

```text
1. Naming 체계가 Application/Package/Mapper/ServiceId에 일관적인가?
2. ServiceId 전수 Registry는 무엇인가?
3. ServiceId→Handler→SQL→Table가 추적되는가?
4. ServiceId→Artifact→JVM→Host가 추적되는가?
5. GUID가 Runtime Evidence와 연결되는가?
6. Architecture Model Entity/Relation은 무엇인가?
7. 어떤 Rule을 CI에서 실행하는가?
8. 어떤 Drift를 자동탐지하는가?
9. G00~HG90 Gate는 어떻게 통합되는가?
10. 최종 Baseline Package는 무엇인가?
```

---

# 204. 전체 1~9장 흐름

## FIG-OPS-79. Architecture Journey to Operations

```text
01 VISION
   ↓
02 BIG PICTURE
   ↓
03 LOGICAL
   ↓
04 PHYSICAL
   ↓
05 MECHANISM
   ↓
06 RUNTIME
   ↓
07 TRACEABILITY / CLOSED LOOP
   ↓
08 PDMG SOURCE REFERENCE
   ↓
09 OM / DEVOPS / OBSERVABILITY / OPERATIONS
   ↓
10 INTEGRATED BASELINE
```

---

# 보완검토 A. OPERATIONS 시각화 보강

## FIG-OPS-SUP-01. Operations RACI

```text
Source / Build
  └─ Dev

Architecture Rule / Drift
  └─ Architect

Deploy / Monitor / Recover
  └─ Operations

JWT / Audit
  └─ Security

DB / SQL / Data
  └─ DBA / Data

Gate / Release
  └─ PMO + Architecture
```

---

## FIG-OPS-SUP-02. OM Metric Matrix

```text
ServiceId
  ↓
Tomcat Thread
  ↓
PDMG Worker
  ↓
Hikari
  ↓
DB / SqlId
  ↓
Timeout / Error
  ↓
Alert
  ↓
Runbook
```

---

## FIG-OPS-SUP-03. Tool 존재와 운영성숙도

```text
Tool Installed
   ↓
Metric Collected?
   ↓
Correlated?
   ↓
Alert?
   ↓
Runbook?
   ↓
Recovery?
   ↓
Evidence?
```

마지막까지 연결되어야 운영 Architecture가 된다.

---

## FIG-OPS-SUP-04. Deployment → Runtime Verification

```text
Artifact Hash
   ↓
DeploymentId
   ↓
Host / JVM
   ↓
Version / Config / Key
   ↓
Smoke ServiceId
   ↓
Metric / Log / Trace
   ↓
Release Evidence
```

---

## FIG-OPS-SUP-05. Operations 최종 출력

```text
Observe
  ↓
Diagnose
  ↓
Recover
  ↓
Validate
  ↓
Evidence
  ↓
Drift / GAP
  ↓
Architecture Update
```

---

# 205. Definition of Done

## TEXT ARCHITECTURE 보완 — Definition of Done

```text
Architecture Inputs
      ↓
Structure / Contract / Runtime Check
      ↓
Evidence Check
      ↓
GAP / CONFLICT / UNKNOWN Review
      ↓
PASS Condition
      │
      ├─ satisfied → PASS
      └─ pending   → CONDITIONAL PASS
```


## DevOps
- [x] SCM/Runner/eCAMS Strategy 구분
- [x] Source→Build→Test→Artifact→Deploy
- [x] PDMG Build Baseline
- [x] Artifact/Config/Secret 분리
- [x] Same Artifact Promotion
- [x] Deployment Manifest
- [x] Rollback

## OM
- [x] Control Plane 정의
- [x] pdmg-om UNKNOWN 유지
- [x] OM Target Responsibility
- [x] Read/Change 권한 분리
- [x] Runtime Parameter Change Governance

## Observability
- [x] Metric/Log/Trace
- [x] ServiceId/GUID View
- [x] JVM/Tomcat/Worker
- [x] Hikari/DB/SQL
- [x] Timeout/Overload/Error
- [x] JWT/Security
- [x] Event/CDC/ETL/File/Batch

## Operations
- [x] Alert Lifecycle
- [x] Alert Storm
- [x] 503/504/JVM/Hikari/JWT Runbook
- [x] Event/CDC/Batch Runbook
- [x] Runtime Inventory
- [x] Config/Deployment/Schema/JWT Drift

## HA/DR/Backup
- [x] HA Monitoring
- [x] DR Readiness
- [x] DR Evidence
- [x] Backup/Restore Evidence

## Governance
- [x] Release Evidence
- [x] Operations Gate
- [x] Runtime Evidence Chain
- [x] Conformance Rules
- [x] GAP/RISK/ADR
- [x] 10장 Handoff

**OM / DEVOPS / OBSERVABILITY / OPERATIONS 장 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. 실제 GitLab CI/CD Pipeline Evidence 확보
2. eCAMS Production Deployment Evidence 확보
3. `pdmg-om` Source/Runtime 확인
4. Central Metric / Log / Trace Platform 실제 구성 확인
5. Runtime Inventory 자동수집
6. ServiceId / Worker / Hikari Dashboard 구현 또는 기존도구 Mapping
7. JWT Key/JWKS Monitoring
8. Alert Threshold / Severity 승인
9. Runbook Catalog 구축
10. Deployment Manifest 자동생성
11. Config/Artifact/Runtime Drift 자동탐지
12. DR/Restore Evidence Repository 연계
13. Operations Gate 자동화
14. Critical Drift 0건
15. Runtime Evidence Coverage 승인기준 충족

---

# 206. 장 최종 결론

## TEXT ARCHITECTURE 보완 — 장 최종 결론

```text
Chapter Evidence
      ↓
Confirmed Structure
      ↓
Remaining GAP / RISK
      ↓
Target Promotion Decision
      ↓
Next Architecture Layer / Baseline
```


> **DevOps는 GitLab이나 배포도구의 존재가 아니라 Source Commit부터 Runtime Evidence까지 변경을 추적하는 Delivery Architecture다.**

> **OM은 Business Runtime을 대신하는 시스템이 아니라 Runtime을 관찰하고 승인된 제어를 수행하는 Control Plane이다.**

> **Observability는 Metric·Log·Trace를 GUID/ServiceId/Deployment Context로 연결하여 “어디서 왜 느리고 실패했는가”를 설명할 수 있어야 한다.**

> **운영의 최종 산출물은 Dashboard가 아니라 Alert→Runbook→Recovery→Evidence이며, 이 Evidence가 다시 Drift/GAP/ADR을 통해 Architecture Baseline을 갱신해야 한다.**

> **현재 PDMG는 GUID/MDC/ImageLog/Worker/Timeout 등 중요한 관측 지점을 제공하지만, `pdmg-om`, Deployment Mapping, 중앙 Metric/Trace, JWT Key Monitoring, Runtime Evidence 자동화는 추가 확인·구현이 필요한 영역이다.**

---


====================================================================================================

# CHAPTER 10

====================================================================================================

# NSIGHT / PDMG 아키텍처 정의서
# 10. INTEGRATED ARCHITECTURE BASELINE

> 보완개정: **v2 — TEXT Architecture Figure Coverage / Reference Coverage / Evidence Consistency 재점검**
## TEXT ARCHITECTURE 보완 — 장 전체 위치

```text
Classification
   ↓
Naming
   ↓
ServiceId
   ↓
Trace
   ↓
Model
   ↓
Rule
   ↓
Evidence
   ↓
Gate
   ↓
Baseline
```

## Naming / ServiceId / Traceability / Model / Rule / Gate / Baseline Release
## Visual-First / Top-down + Bottom-up Integration 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-VISUAL-INTEGRATED-BASELINE-10`  
> Architecture Level: **L9 — INTEGRATED BASELINE / GOVERNANCE / RELEASE**  
> 문서 상태: **Draft / Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_09_OM_DEVOPS_OBSERVABILITY_OPERATIONS_VISUAL_FIRST_상세본.md`  
> 본 장 목적: **01~09장을 하나의 Architecture Baseline으로 통합하고, Naming·ServiceId·Traceability·Rule·Evidence·Gate·Release를 단일 통제체계로 완결**

---

# 0. 이 장의 역할

1~9장은 각각 서로 다른 질문에 답했다.

```text
01 VISION
왜 바꾸는가?

02 BIG PICTURE
누가 무엇을 책임하는가?

03 LOGICAL
어떤 논리구조로 분리하는가?

04 PHYSICAL
어디에 배치하는가?

05 MECHANISM
어떤 규칙으로 동작하는가?

06 RUNTIME
실제로 어떻게 실행되는가?

07 TRACEABILITY / CLOSED LOOP
어떻게 검증하고 유지하는가?

08 PDMG SOURCE REFERENCE
실제 Source는 어떻게 구현되어 있는가?

09 OM / DEVOPS / OBSERVABILITY
어떻게 배포·관찰·복구·운영하는가?
```

10장은 이 모든 것을 하나로 묶는다.

```text
Classification
   ↓
Naming
   ↓
ServiceId
   ↓
Source
   ↓
Data
   ↓
Artifact
   ↓
Deployment
   ↓
Runtime
   ↓
Evidence
   ↓
Rule / Gate
   ↓
Architecture Baseline
```

---

# 1. VISUAL ROUTE — Integrated Baseline 전체

## FIG-IB-01. Integrated Architecture Baseline Journey

```text
╔══════════════════════════════════════════════════════════════════════════════╗
║                    INTEGRATED ARCHITECTURE BASELINE                         ║
╚══════════════════════════════════════════════════════════════════════════════╝

 [1] CLASSIFICATION
 Application Group / Business / Function / Program
      │
      ▼
 [2] NAMING
 Package / Program / ServiceId / Mapper / Artifact / Deployment
      │
      ▼
 [3] SERVICE ID
 Business Transaction Identity / Runtime Routing Key
      │
      ▼
 [4] SOURCE TRACE
 Handler → Facade → Service → DAO → Mapper → SQL → Table
      │
      ▼
 [5] DEPLOYMENT TRACE
 Source → Build → Artifact → JVM → Host → Environment
      │
      ▼
 [6] RUNTIME TRACE
 GUID / ServiceId / Thread / TX / SQL / Result
      │
      ▼
 [7] MODEL
 Entity / Relation / Policy / Evidence Link
      │
      ▼
 [8] RULE
 Naming / Dependency / TX / Timeout / Security / Deployment
      │
      ▼
 [9] TEST / EVIDENCE
 Static / Contract / Integration / Runtime / DR
      │
      ▼
 [10] DRIFT / GAP / ADR
 Expected vs Actual
      │
      ▼
 [11] GATE
 G00 → G80 → HG90
      │
      ▼
 [12] BASELINE RELEASE
 Document + Model + Rule + Evidence + Approval
```

---

# 2. Top-down과 Bottom-up의 최종 결합

## FIG-IB-02. Architecture Two-way Model

```text
TOP-DOWN

Vision
  ↓
Big Picture
  ↓
Logical
  ↓
Physical
  ↓
Mechanism
  ↓
Runtime
  ↓
Expected Architecture

────────────────────────────────────

BOTTOM-UP

Actual Source
  ↑
Actual Config
  ↑
Actual Deployment
  ↑
Actual Runtime
  ↑
Evidence
  ↑
Drift / GAP

────────────────────────────────────

MEETING POINT
Architecture Baseline
```

---

# 3. Integrated Baseline의 핵심 원칙

## FIG-IB-03. Baseline Equation

```text
Architecture Baseline
=
Document
+
Model
+
Naming
+
Traceability
+
Rules
+
Tests
+
Runtime Evidence
+
Drift Review
+
ADR
+
Approval
```

### 금지

```text
문서만 있으면 Baseline       X
PPT 승인만 받으면 Baseline    X
Source가 있으면 Baseline      X
Test PASS면 Baseline          X
```

---

# 4. Architecture Classification의 시작점

## FIG-IB-04. Classification Tree

```text
Enterprise
  ↓
Service Domain
  ↓
Application Group
  ↓
Business Group
  ↓
Function
  ↓
Program
  ↓
ServiceId
```

이 축이 Naming과 Traceability의 기준이 된다.

---

# 5. 5대 Service Domain

```text
Marketing Platform
Data Platform
BI Portal
Data Governance
IT Service & Business Support
```

---

# 6. Application Group

## FIG-IB-05. Application Classification

```text
5대 Service Domain
       │
       ▼
Application Classification
│
├─ MP  Marketing
├─ RD  Relational / Operational Data
├─ AD  Analytical Data
├─ BI  Business Intelligence
├─ DG  Data Governance
└─ IM  IT Service / Integration / Management
```

### 핵심

```text
5대 Service Domain
≠
Application Group Code 수
```

Data Platform은 RD/AD로 세분화될 수 있다.

---

# 7. Business Code

PDMG 현재 대표 업무축:

```text
MG
└─ CO
   └─ A
```

예:

```text
MG / CO / A / 9001
```

---

# 8. Naming Axis

## FIG-IB-06. One Classification, Multiple Names

```text
Business Classification
MG / CO / A / 9001
      │
      ├────────► Program
      │          mgcoa9001
      │
      ├────────► ServiceId
      │          mgcoa9001S0
      │
      ├────────► Java Package
      │          nhnis.mg.co.a
      │
      ├────────► Java Class
      │          mgcoa9001Handler
      │
      └────────► Mapper Resource
                 rdw.mg.co.a/mgcoa9001-ORA.xml
```

---

# 9. Naming의 목적

Naming은 보기 좋은 이름을 정하는 일이 아니다.

```text
Classification
  ↓
Search
  ↓
Trace
  ↓
Ownership
  ↓
Automation
```

Naming이 일관되면 Source Scanner와 Architecture Model 연결이 쉬워진다.

---

# 10. Program ID

## FIG-IB-07. Program ID

```text
mg | co | a | 9001
│    │    │     │
│    │    │     └─ Program Number
│    │    └─────── Function
│    └──────────── Business
└───────────────── Application / Major Group
```

---

# 11. ServiceId

## FIG-IB-08. ServiceId Anatomy

```text
mg | co | a | 9001 | S | 0
│    │    │    │      │   │
│    │    │    │      │   └─ Sequence
│    │    │    │      └──── Transaction Type
│    │    │    └─────────── Program Number
│    │    └──────────────── Function
│    └───────────────────── Business
└────────────────────────── Application / Major Group
```

예:

```text
mgcoa9001S0
```

---

# 12. Transaction Type

대표 거래구분:

```text
S = Select
C = Create
U = Update
D = Delete
A = Action
R = Reserved/Reference candidate
```

현재 Handler 실제 사용은 주로:

```text
S / C / U / D
```

이며 `A/R`의 실제 적용범위는 Source Inventory로 확인한다.

---

# 13. ServiceId Regex

일반 후보:

```text
^[a-z]{2}[a-z]{2}[a-z][0-9]{4}[SCUDAR][0-9A-Z]$
```

MG 특화 후보:

```text
^mg[a-z]{2}[a-z][0-9]{4}[SCUDAR][0-9A-Z]$
```

---

# 14. ServiceId의 역할

## FIG-IB-09. ServiceId Identity

```text
ServiceId
│
├─ Business Transaction Identity
├─ Dispatcher Routing Key
├─ Logging Dimension
├─ Monitoring Dimension
├─ Traceability Key
└─ Architecture Model Key
```

---

# 15. ServiceId가 아닌 것

```text
GUID
URL
InterfaceId
Program ID
SQL ID
```

각각 역할이 다르다.

---

# 16. Identifier Taxonomy

## FIG-IB-10. Identifier Map

```text
Program ID
= 프로그램 단위

ServiceId
= 업무 거래 단위

GUID / TraceId
= 실행 거래 흐름 단위

InterfaceId
= 시스템 간 Contract 단위

SqlId
= SQL Statement 단위

DeploymentId
= 배포 실행 단위

EvidenceId
= 증적 패키지 단위
```

---

# 17. ServiceId Registry

## FIG-IB-11. Current PDMG Registry

```text
mgcoa5530S0

mgcoa8888S0
mgcoa8888D0

mgcoa9000S0
mgcoa9000C0
mgcoa9000U0
mgcoa9000D0

mgcoa9001S0
mgcoa9001C0
mgcoa9001U0
mgcoa9001D0

mgcoa9100S0
mgcoa9999S0
```

현재 Source 분석 기준 총:

```text
13 ServiceIds
```

---

# 18. ServiceId SSOT

## FIG-IB-12. SSOT Candidate

```text
Architecture Model
       │
       ▼
ServiceId Registry
       │
       ├─ Backend Handler Registry
       ├─ UI Transaction Catalog
       ├─ API Catalog
       ├─ Test Catalog
       └─ Monitoring Catalog
```

### 원칙

Backend Handler Registry와 UI Catalog가 각각 독립 SSOT가 되면 Drift가 발생한다.

---

# 19. ServiceId Duplicate Rule

```text
ServiceId
  ↓
Multiple Handler?
  ├─ NO → PASS
  └─ YES
       ↓
    Startup Fail
```

Current PDMG Source 분석에서는 Duplicate ServiceId 등록이 기동 실패로 처리된다.

---

# 20. Registered vs Executed

## FIG-IB-13. Registry / Branch Alignment

```text
Handler.serviceIds()
      │
      │ compare
      ▼
Handler.handle()
      │
      ▼
Branch Coverage
```

가능한 오류:

```text
등록 O / Branch X
등록 X / Branch O
```

---

# 21. ServiceId → Handler

## FIG-IB-14. Runtime Routing

```text
Request
  ↓
ServiceId
  ↓
TransactionDispatcher
  ↓
handlerMap[serviceId]
  ↓
TransactionHandler
```

---

# 22. Handler → Facade

```text
Handler
  ↓
Facade
```

### 원칙

Handler는 Thin Inbound Adapter로 유지한다.

---

# 23. Handler 금지 Dependency

```text
Handler → DAO       X
Handler → Mapper    X
Handler → SQL       X
```

---

# 24. Facade → Service

## FIG-IB-15. Use Case Boundary

```text
Handler / Controller
      ↓
Facade
      ↓
Service
```

Facade는 Use Case Boundary 역할을 한다.

---

# 25. TCF ON/OFF 공통 Business Core

## FIG-IB-16. Target Candidate

```text
TCF ON
Handler ─────────┐
                 │
                 ▼
               Facade
                 ↓
               Service
                 ↓
                DAO
                 ▲
                 │
TCF OFF           │
Controller ───────┘
```

Current OFF Source는 일부 Controller→Service 직접호출이 있으므로 `[GAP]`.

---

# 26. Service → DAO

```text
Service
  ↓
DAO
```

Service가 Business Procedure를 소유하고,
DAO는 Data Access Contract를 소유한다.

---

# 27. DAO → Mapper

## FIG-IB-17. MyBatis Contract

```text
Java DAO FQCN
       │
       │ namespace exact match
       ▼
Mapper XML
       │
       ▼
SqlId
```

---

# 28. Mapper Resource

Current PDMG Reference:

```text
classpath*:rdw.*/*.xml
```

대표:

```text
rdw.mg.co.a/mgcoa9000-ORA.xml
```

---

# 29. Mapper Namespace

```text
Java
nhnis.mg.co.a.persistence.dao.mgcoa9000DAO

        │ exact
        ▼

Mapper
namespace="nhnis.mg.co.a.persistence.dao.mgcoa9000DAO"
```

---

# 30. SqlId

## FIG-IB-18. SqlId Trace

```text
DAO Method
   ↓
SqlId
   ↓
SQL
   ↓
Table / View
```

대표 형태:

```text
mgcoa9000S0_S0
mgcoa9000S0_COUNT
mgcoa9000C0_C0
mgcoa9000U0_U0
mgcoa9000D0_D0
```

보조 Statement도 존재할 수 있으므로 Source 기준으로 수집한다.

---

# 31. SQL → Table

```text
SQL Parser
  ↓
FROM / JOIN / INSERT / UPDATE / DELETE
  ↓
Table / View
```

이 영역은 문자열추정이 아니라 Parser가 필요하다.

---

# 32. Full Source Trace

## FIG-IB-19. Service-to-Data Trace

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
Mapper
  ↓
SqlId
  ↓
SQL
  ↓
Table / View
```

---

# 33. Program → Source Trace

## FIG-IB-20. Program Stem

```text
mgcoa9000
│
├─ mgcoa9000Handler
├─ mgcoa9000Facade
├─ mgcoa9000Service
├─ mgcoa9000DAO
├─ mgcoa9000 DTOs
└─ mgcoa9000-ORA.xml
```

---

# 34. Package Naming

## FIG-IB-21. Package Rule

```text
Business Axis
MG / CO / A
    │
    ├─ Java
    │   nhnis.mg.co.a
    │
    └─ Mapper
        rdw.mg.co.a
```

---

# 35. Package Rule의 목적

```text
Business Ownership
→ Source Location
→ Mapper Location
→ Automated Trace
```

---

# 36. Package Anti-pattern

```text
common
util
etc
temp
misc
```

와 같은 무분별한 범용 Package에 Business Code를 숨기지 않는다.

---

# 37. Framework Package

```text
nhnis.fw.*
```

Business Package:

```text
nhnis.mg.*
```

### 원칙

Framework가 특정 Business Package에 직접 의존하지 않도록 한다.

---

# 38. Module Naming

```text
pdmg-ui
pdmg-jwt
pdmg-fw
pdmg-service
pdmg-om
```

Module 명은 Build Responsibility를 표현한다.

---

# 39. Module ≠ Runtime Node

## FIG-IB-22. Boundary Reminder

```text
Build Module
     │
     │ ≠
     ▼
Runtime Process
     │
     │ ≠
     ▼
Logical Node
```

---

# 40. Source → Artifact

## FIG-IB-23. Build Trace

```text
Repository
 ↓
Branch
 ↓
Commit
 ↓
Module
 ↓
Build
 ↓
Artifact
 ↓
Hash
```

---

# 41. Artifact → Deployment

## FIG-IB-24. Deployment Trace

```text
Artifact
  ↓
DeploymentId
  ↓
Environment
  ↓
Center
  ↓
Host
  ↓
JVM
  ↓
Context / WAR
```

---

# 42. Deployment → Runtime

```text
Host
 ↓
JVM
 ↓
WAR
 ↓
ServiceId
 ↓
GUID
 ↓
Runtime Evidence
```

---

# 43. End-to-End Trace

## FIG-IB-25. Requirement to Evidence

```text
Requirement
  ↓
Architecture Principle
  ↓
ADR
  ↓
Application
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
SQL
  ↓
Table
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

# 44. Reverse Trace

## FIG-IB-26. Incident to Requirement

```text
Runtime Incident
  ↑
GUID
  ↑
ServiceId
  ↑
Deployment
  ↑
Artifact
  ↑
Commit
  ↑
Source
  ↑
Architecture Decision
  ↑
Requirement
```

---

# 45. GUID

```text
std_gbl_id
```

Current PDMG Reference의 Correlation Key다.

---

# 46. GUID Lifecycle

## FIG-IB-27. GUID Propagation

```text
Request Header
  ↓
DefaultFilter
  ↓
ServiceContext
  ↓
MDC
  ↓
Worker
  ↓
Business
  ↓
ImageLog
  ↓
Response / Evidence
```

---

# 47. GUID와 ServiceId의 조합

```text
GUID
= 이 실행은 어느 흐름인가?

ServiceId
= 이 실행은 무슨 업무거래인가?
```

운영에서는 둘을 함께 본다.

---

# 48. InterfaceId

## FIG-IB-28. Interface Trace

```text
Producer
  ↓
InterfaceId
  ↓
Mechanism
  ↓
Consumer
```

InterfaceId와 ServiceId는 별도 Identifier다.

---

# 49. EventId / JobId / FileId

```text
Online
→ GUID + ServiceId

Event
→ EventId / CorrelationId

Batch
→ JobId / ExecutionId

File
→ FileId / InterfaceId
```

Runtime Type에 맞는 Trace Key를 사용한다.

---

# 50. Architecture Model의 목적

## FIG-IB-29. Human + Machine

```text
Human-readable
Markdown / PPT
       │
       ▼
Machine-readable
Architecture Model
       │
       ▼
Rule / Scan / Test / Drift
```

---

# 51. Model Entity

## FIG-IB-30. Entity Map

```text
Requirement
Principle
ADR
Domain
ApplicationGroup
System
LogicalNode
PhysicalNode
Module
Program
ServiceId
Handler
Facade
Service
DAO
Mapper
SqlId
Table
Interface
Artifact
Deployment
RuntimeScenario
Evidence
Gap
Risk
```

---

# 52. Model Relation

## FIG-IB-31. Relation Map

```text
Requirement
  DRIVES
Principle

Principle
  GOVERNED_BY
ADR

Application
  CONTAINS
Program

Program
  EXPOSES
ServiceId

ServiceId
  HANDLED_BY
Handler

Handler
  CALLS
Facade

Facade
  CALLS
Service

Service
  USES
DAO

DAO
  EXECUTES
SqlId

SqlId
  ACCESSES
Table
```

---

# 53. Deployment Relations

```text
Module
  BUILDS
Artifact

Artifact
  DEPLOYED_AS
Deployment

Deployment
  RUNS_ON
PhysicalNode

Deployment
  HOSTS
ServiceId
```

---

# 54. Runtime Relations

## FIG-IB-32. Runtime Graph

```text
ServiceId
  EXECUTES_AS
RuntimeScenario

RuntimeScenario
  USES_THREAD
Worker

RuntimeScenario
  USES_TX
Transaction

RuntimeScenario
  PRODUCES
Evidence
```

---

# 55. Evidence Relations

```text
Evidence
  PROVES
RuntimeScenario

Evidence
  REFERENCES
Deployment

Evidence
  REFERENCES
Artifact

Evidence
  REFERENCES
ServiceId
```

---

# 56. Model Version

```text
architectureModelVersion
```

은 Baseline마다 고정한다.

---

# 57. Model Baseline

## FIG-IB-33. Model Baseline

```text
Architecture Baseline v1
       │
       ├─ Document v1
       ├─ Model v1
       ├─ Rule v1
       └─ Evidence Index v1
```

---

# 58. Model Diff

```text
Model v1
  ↓
Change
  ↓
Model v2
  ↓
Entity / Relation Diff
```

---

# 59. Architecture Rule

## FIG-IB-34. Rule Lifecycle

```text
Principle
  ↓
Rule
  ↓
Scanner / Test
  ↓
PASS / FAIL
  ↓
Gate
```

---

# 60. Rule Categories

```text
Naming
ServiceId
Dependency
Mapper
Transaction
Timeout
Security
Logging
Deployment
Runtime Evidence
DR
```

---

# 61. Naming Rule

```text
Business Axis
↔ Package
↔ Mapper
↔ Service Prefix
```

불일치 시 Warning/Fail 후보.

---

# 62. ServiceId Format Rule

```text
ServiceId
  ↓
Regex
  ↓
PASS / FAIL
```

---

# 63. ServiceId Unique Rule

```text
All ServiceIds
  ↓
Duplicate?
  ├─ NO → PASS
  └─ YES → FAIL
```

---

# 64. Handler Branch Rule

```text
serviceIds()
  ↓ compare
handle() branches
```

---

# 65. Handler Dependency Rule

```text
Handler
  ↓
DAO?
  └─ YES → FAIL
```

---

# 66. Controller Dependency Rule

```text
Controller
  ↓
DAO / Mapper?
  └─ YES → FAIL
```

---

# 67. Framework Dependency Rule

## FIG-IB-35. Framework Rule

```text
Business
   ─────► Framework     O

Framework
   ─────► Specific Business Package   X
```

---

# 68. DAO / Mapper Rule

```text
DAO FQCN
=
Mapper Namespace
```

---

# 69. Mapper Resource Rule

```text
Mapper XML
  ↓
Resource Pattern
  ↓
Loaded?
```

---

# 70. SQL Trace Rule

```text
SqlId
  ↓
SQL Parse
  ↓
Table / View
```

Trace 불가 시 GAP.

---

# 71. Transaction Owner Rule

## FIG-IB-36. TX Rule

```text
ServiceId
  ↓
Runtime Mode
  ↓
TX Owner
  ↓
TransactionManager
  ↓
Datasource
```

UNKNOWN이면 Gate 미통과 후보.

---

# 72. Timeout Hierarchy Rule

```text
DB Query
   <
TX / Worker
   <
Server / External
   <
Client
```

---

# 73. Current PDMG Timeout Snapshot

```text
Worker Deadline = 5000ms
Worker Pool     = 20
Queue           = 100
```

### 태그

```text
[AS-IS SNAPSHOT]
```

---

# 74. Query Timeout Rule

```text
Query Timeout
  ↓
Known?
  ├─ YES
  └─ NO → GAP
```

---

# 75. Retry Rule

```text
Retry
=
Retryable Error
+
Backoff
+
Max Retry
+
Idempotency
+
Recovery
```

---

# 76. Security Rule — Algorithm Alignment

## FIG-IB-37. JWT Rule

```text
Issuer Algorithm
      │
      │ compare
      ▼
Verifier Algorithm
```

불일치:

```text
CRITICAL FAIL
```

---

# 77. Security Rule — Key Consistency

```text
Same kid
→ Same public key fingerprint
```

다르면 Critical.

---

# 78. Security Rule — Identity Binding

```text
JWT Principal
  ↓
Business User Identity
```

Header Identity가 독립적으로 신뢰되지 않아야 한다.

---

# 79. Sensitive Log Rule

```text
Token / Password / Secret / Private Key
        ↓
Raw Logging?
        └─ YES → FAIL
```

---

# 80. Deployment Manifest Rule

## FIG-IB-38. Deployment Rule

```text
Artifact
  ↓
Host/JVM/Context Mapping?
  ├─ YES
  └─ NO → FAIL/GAP
```

---

# 81. Runtime Inventory Rule

```text
Running JVM
  ↓
Known DeploymentId?
```

UNKNOWN Runtime Process는 운영 Risk다.

---

# 82. Runtime Evidence Rule

```text
Critical ServiceId
  ↓
Required Runtime Scenario
  ↓
Evidence?
  ├─ YES
  └─ NO → Gate Fail Candidate
```

---

# 83. DR Evidence Rule

```text
Critical Service
  ↓
DR Scenario
  ↓
Evidence
```

---

# 84. Restore Evidence Rule

```text
Backup
  ↓
Restore Test
  ↓
Evidence
```

---

# 85. Architecture as Code

## FIG-IB-39. Architecture Rule Pipeline

```text
Architecture Rule
    ↓
Rule File
    ↓
Scanner / Test
    ↓
CI
    ↓
PASS / FAIL
```

---

# 86. Rule Definition Example

```yaml
rule:
  id: R-SERVICEID-UNIQUE
  scope: pdmg-service
  severity: critical
  source: handlerRegistry
  condition: duplicateCount == 0
  evidence:
  owner:
```

---

# 87. Test Architecture

## FIG-IB-40. Test Pyramid for Architecture

```text
Static
  ↓
Architecture
  ↓
Unit
  ↓
Contract
  ↓
Integration
  ↓
Security
  ↓
Performance
  ↓
Failure / DR
  ↓
Runtime Evidence
```

---

# 88. Static Test

```text
Naming
Package
Forbidden Dependency
Secret Scan
Config Pattern
```

---

# 89. Architecture Test

```text
ServiceId Unique
Layer Dependency
Handler/Controller Rule
Mapper Contract
```

---

# 90. Contract Test

```text
Request Header
DTO
Success Envelope
Error Envelope
Interface Schema
```

---

# 91. Transaction Test

## FIG-IB-41. TX Test Set

```text
Normal Commit
Business Rollback
Runtime Exception
Timeout
Late Commit
Nested REQUIRED
TCF OFF
```

---

# 92. Timeout Test

```text
Queue Wait
Worker Slow
Hikari Wait
SQL Slow
External Slow
```

---

# 93. Security Test

```text
Valid Token
Expired
Wrong kid
Wrong signature
Revoked
Identity mismatch
Direct WAS bypass
```

---

# 94. Deployment Test

```text
Correct Artifact?
Correct Hash?
Correct Config?
Correct Key?
Correct Host/JVM?
```

---

# 95. Runtime Evidence Test

```text
Scenario
  ↓
Execute
  ↓
Trace
  ↓
Expected Outcome
  ↓
Evidence Manifest
```

---

# 96. Evidence Chain

## FIG-IB-42. Evidence Identity

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
traceId / GUID
       ↓
runtimeEvidence
```

---

# 97. Evidence Manifest

```yaml
evidence:
  evidenceId:
  architectureBaselineId:
  modelVersion:
  sourceCommit:
  buildId:
  artifactHash:
  deploymentId:
  environment:
  serviceId:
  traceId:
  scenarioId:
  result:
  metrics:
  logs:
  attachments:
  owner:
  evidenceHash:
```

---

# 98. Evidence의 강도

## FIG-IB-43. Evidence Strength

```text
Runtime Evidence
      >
Source / Config
      >
Official Approved Baseline
      >
ADR
      >
Detailed Design
      >
Requirement / Interview
      >
Draft / Past Conversation
```

---

# 99. AS-IS / TO-BE / GAP

## FIG-IB-44. State Model

```text
PDMG / Current
   ↓
[AS-IS]

NSIGHT Target
   ↓
[TO-BE]

Difference
   ↓
[GAP]
```

---

# 100. Evidence Tag Standard

```text
[FACT]
[CONFIRMED]
[DECISION]
[AS-IS]
[TO-BE]
[PROPOSED]
[GAP]
[CONFLICT]
[RISK]
[OPEN]
[UNKNOWN]
[DEPRECATED]
[BASELINE-YYYY-MM-DD]
```

---

# 101. Conflict 처리

## FIG-IB-45. Conflict

```text
Evidence A
   │
   ├─ different
   │
Evidence B
   │
   ▼
[CONFLICT]
   ↓
Owner Review
   ↓
Runtime Measurement
   ↓
ADR
   ↓
New Baseline
```

---

# 102. CDC SLA Conflict 예

```text
Baseline A
30 sec

vs

Baseline B
3 sec
```

Runtime Measurement Point를 정의한 후 결정한다.

---

# 103. Session Conflict 예

```text
60 min
vs
90 min
```

최종 승인 전까지 `[CONFLICT]`.

---

# 104. Filter Order Drift 예

```text
Old Document
order = 1

Current Source
HIGHEST_PRECEDENCE + 20

→ DRIFT
```

---

# 105. ServiceId Count Drift 예

```text
Past Document
8

Current Source
13

→ DRIFT / SUPERSEDED
```

---

# 106. Drift의 정의

## FIG-IB-46. Drift

```text
Expected
  │
  │ compare
  ▼
Actual
  │
  ├─ same
  └─ different
        ↓
      DRIFT
```

---

# 107. Drift Type

```text
Document Drift
Model Drift
Source Drift
Config Drift
Deployment Drift
Runtime Drift
Security Drift
Data Drift
```

---

# 108. GAP vs DRIFT

```text
GAP
= 필요한 것이 없거나 미정

DRIFT
= 정의한 것과 실제가 다름
```

---

# 109. Drift Severity

## FIG-IB-47. Severity

```text
CRITICAL
- Security bypass
- Data inconsistency
- Wrong artifact
- Late commit
- DR impossible

HIGH
- NFR violation
- Trace loss
- Config mismatch

MEDIUM
- Naming / Documentation

LOW
- Cosmetic / metadata
```

---

# 110. GAP Register 최소구조

```yaml
gap:
  gapId:
  layer:
  expected:
  actual:
  impact:
  severity:
  owner:
  action:
  targetDate:
  adr:
  evidence:
  status:
```

---

# 111. Risk Register 최소구조

```yaml
risk:
  riskId:
  cause:
  event:
  impact:
  likelihood:
  severity:
  mitigation:
  owner:
  evidence:
```

---

# 112. ADR Lifecycle

## FIG-IB-48. ADR

```text
GAP / DRIFT / CONFLICT
       ↓
Decision Needed
       ↓
ADR
       │
       ├─ Context
       ├─ Decision
       ├─ Alternatives
       ├─ Consequences
       ├─ Owner
       └─ Date
       ↓
Architecture Update
```

---

# 113. ADR Status

```text
PROPOSED
ACCEPTED
REJECTED
SUPERSEDED
DEPRECATED
```

---

# 114. Standard Exception

```text
Standard Rule
    ↓
Exception
    ↓
ADR
    ↓
Compensating Control
    ↓
Expiry / Review
```

---

# 115. Architecture Debt

## FIG-IB-49. Debt

```text
Accepted GAP
  ↓
Architecture Debt
  ↓
Owner
  ↓
Due Date
  ↓
Review
  ↓
Close / Extend
```

---

# 116. Unknown

```text
Evidence 없음
   ↓
[UNKNOWN]
   ↓
Evidence Collection Task
```

Unknown을 빈칸으로 숨기지 않는다.

---

# 117. Architecture Gate 전체

## FIG-IB-50. G00 → HG90

```text
G00
Source Baseline
   ↓
G10
Document Classification
   ↓
G20
Architecture Model
   ↓
G30
Model ↔ Source Conformance
   ↓
G40
Rule / Test
   ↓
G50
Runtime Evidence
   ↓
G60
Drift
   ↓
G70
GAP / ADR
   ↓
G80
Human Approval
   ↓
HG90
Architecture Baseline Release
```

---

# 118. G00 — Source Baseline

## FIG-IB-51. Source Freeze

```text
Repository
  ↓
Branch
  ↓
Commit
  ↓
Module Scope
  ↓
Config Scope
  ↓
Baseline ID
```

---

# 119. G10 — Document Classification

```text
Document Statement
  ↓
Tag
  ↓
FACT / AS-IS / TO-BE / GAP / ...
```

---

# 120. G20 — Model

```text
Documents
  ↓
Entity
  ↓
Relation
  ↓
Policy
  ↓
Model Version
```

---

# 121. G30 — Conformance

## FIG-IB-52. Model vs Source

```text
Architecture Model
       │
       │ compare
       ▼
Source / Config
       │
       ▼
Conformance Result
```

---

# 122. G40 — Rule / Test

```text
Rule
  ↓
Test
  ↓
PASS / FAIL
```

Critical Rule FAIL은 다음 Gate 진행을 막을 수 있다.

---

# 123. G50 — Runtime Evidence

## FIG-IB-53. Runtime Gate

```text
Exact Artifact
  ↓
Exact Deployment
  ↓
Scenario
  ↓
Trace
  ↓
Outcome
  ↓
Evidence
```

---

# 124. G60 — Drift

```text
Expected
  ↓ compare
Actual
  ↓
Drift Report
```

---

# 125. G70 — GAP / ADR

```text
Gap / Drift
   ↓
Fix?
Accept?
Exception?
Change Target?
   ↓
ADR
```

---

# 126. G80 — Human Approval

필요 역할 후보:

```text
Architecture
Business
Security
Data
Operations
PMO / Governance
```

실제 승인자는 프로젝트 RACI에 따라 확정한다.

---

# 127. HG90 — Baseline Release

## FIG-IB-54. Final Release

```text
Document
+
Model
+
Rules
+
Tests
+
Evidence
+
Drift Review
+
ADR
+
Approval
      │
      ▼
HG90
Architecture Baseline Release
```

---

# 128. HG90 실패조건

```text
Critical Rule Fail > 0
Critical Drift > 0
Critical Runtime Evidence Missing
Unapproved Critical GAP
Unknown Critical Deployment
```

---

# 129. Workspace Structure

## FIG-IB-55. Integrated Workspace

```text
00-IN
 ↓
10-DOCUMENT
 ↓
20-MODEL
 ↓
30-CODE / CONFORMANCE
 ↓
40-TEST
 ↓
50-RUNTIME-EVIDENCE
 ↓
60-DRIFT
 ↓
70-GAP-ADR
 ↓
80-GATE
 ↓
90-OUT
```

---

# 130. 00-IN

```text
RFP
Requirement
PPT
Source
Config
Runtime Input
```

---

# 131. 10-DOCUMENT

```text
01 VISION
02 BIG PICTURE
03 LOGICAL
04 PHYSICAL
05 MECHANISM
06 RUNTIME
07 CLOSED LOOP
08 PDMG REFERENCE
09 OPERATIONS
10 INTEGRATED BASELINE
```

---

# 132. 20-MODEL

```text
reference-baseline.json
serviceid-index.json
interface-index.json
deployment-model.json
runtime-scenario.json
evidence-index.json
```

---

# 133. 30-CODE / CONFORMANCE

```text
source-index
dependency-index
serviceid-index
mapper-sql-index
config-index
deployment-index
```

---

# 134. 40-TEST

```text
architecture-test
contract-test
security-test
transaction-test
timeout-test
integration-test
performance-test
failure-test
```

---

# 135. 50-RUNTIME-EVIDENCE

```text
runtime-scenario
metric
log
trace
screenshot/report
manifest
hash
```

---

# 136. 60-DRIFT

```text
document
model
source
config
deployment
runtime
security
data
```

---

# 137. 70-GAP-ADR

```text
gap-register
risk-register
adr
exception
debt
```

---

# 138. 80-GATE

```text
gate-result
approval
waiver
condition
```

---

# 139. 90-OUT

```text
Released Architecture Baseline
Model
Rules
Test Result
Evidence Index
Drift Report
ADR Pack
```

---

# 140. Architecture Baseline Package

## FIG-IB-56. Release Package

```text
BASELINE
│
├─ 01 VISION
├─ 02 BIG PICTURE
├─ 03 LOGICAL
├─ 04 PHYSICAL
├─ 05 MECHANISM
├─ 06 RUNTIME
├─ 07 CLOSED LOOP
├─ 08 PDMG REFERENCE
├─ 09 OPERATIONS
├─ 10 INTEGRATED BASELINE
├─ Architecture Model
├─ Naming Standard
├─ ServiceId Registry
├─ Interface Catalog
├─ Deployment Manifest
├─ Rule Set
├─ Test Result
├─ Runtime Evidence Index
├─ Drift Report
├─ GAP/Risk Register
└─ ADR Pack
```

---

# 141. Baseline ID

권장 개념:

```text
architectureBaselineId
```

예:

```text
NSIGHT-ARCH-2026-08-RC1
```

실제 Naming은 프로젝트 Release 규칙으로 승인한다.

---

# 142. Baseline Version

```text
v1.0
v1.1
v2.0
```

Version 증가 기준은 Change Management와 연계한다.

---

# 143. Baseline Date

과거 기준은:

```text
[BASELINE-YYYY-MM-DD]
```

태그로 보존한다.

---

# 144. Baseline Diff

## FIG-IB-57. Baseline N → N+1

```text
Baseline N
  ↓
Change
  ↓
Model Diff
  ↓
Source / Config Diff
  ↓
Rule / Test
  ↓
Runtime Evidence
  ↓
ADR
  ↓
Baseline N+1
```

---

# 145. Change Request

```text
Requirement Change
Architecture Change
Source Change
Config Change
Infrastructure Change
Security Change
```

모두 Baseline 영향평가 대상이다.

---

# 146. Change Impact

## FIG-IB-58. Impact Graph

```text
Change
  ↓
Application
  ↓
Program
  ↓
ServiceId
  ↓
Source
  ↓
Interface
  ↓
Data
  ↓
Deployment
  ↓
Test
  ↓
Runtime
```

---

# 147. ServiceId Change Impact

```text
ServiceId Change
  ↓
Handler Registry
  ↓
UI Catalog
  ↓
API/Interface
  ↓
Logging
  ↓
Monitoring
  ↓
Test
```

---

# 148. Package Change Impact

```text
Package Move
  ↓
Component Scan
  ↓
Mapper Namespace?
  ↓
Architecture Rule?
  ↓
Build / Runtime?
```

---

# 149. Mapper Change Impact

```text
Mapper Namespace / Path Change
  ↓
Mapper Resource Load
  ↓
DAO Binding
  ↓
SQL Execution
```

---

# 150. Table Change Impact

```text
Table
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
```

---

# 151. Framework Change Impact

## FIG-IB-59. pdmg-fw Impact

```text
pdmg-fw Change
   │
   ├─ pdmg-service
   ├─ pdmg-jwt
   ├─ Filter
   ├─ Context
   ├─ TCF
   ├─ Timeout
   ├─ Error
   └─ Security / Runtime
```

---

# 152. JWT Change Impact

```text
Algorithm
Key
kid
Issuer
Audience
Refresh
Denylist
Identity Binding
```

변경 시 모든 Validator/Client/DR 영향평가가 필요하다.

---

# 153. Timeout Change Impact

## FIG-IB-60. Timeout Change

```text
Worker Timeout
  ↓
Client
Gateway
WEB
TX
Hikari
JDBC
External
```

하나의 Timeout 변경이 전체 Budget에 영향을 준다.

---

# 154. Deployment Change Impact

```text
Artifact / JVM / Host Change
  ↓
Capacity
  ↓
HA
  ↓
Monitoring
  ↓
DR
  ↓
Evidence
```

---

# 155. Architecture Review Package

## FIG-IB-61. Review Pack

```text
Change Request
   ↓
Review Package
│
├─ Context
├─ Diagram
├─ Model Diff
├─ Naming/ServiceId Impact
├─ Source Impact
├─ Data Impact
├─ Deployment Impact
├─ Rule/Test Impact
├─ Risk
├─ Evidence
└─ ADR
```

---

# 156. Architecture Review Board

```text
Architect
Business
Security
Data
Operations
Infra
PMO
```

실제 구성은 프로젝트 Governance에 따라 확정한다.

---

# 157. Architecture KPI

## FIG-IB-62. Governance Dashboard

```text
Architecture Health
│
├─ Critical Drift
├─ Critical GAP
├─ Rule Pass Rate
├─ ServiceId Trace Coverage
├─ Deployment Trace Coverage
├─ Runtime Evidence Coverage
├─ DR Evidence Coverage
└─ ADR Aging
```

---

# 158. ServiceId Trace Coverage

```text
End-to-end traced ServiceIds
---------------------------
Total ServiceIds
```

---

# 159. Source Trace Coverage

```text
ServiceId→SQL/Table traced
--------------------------
Total ServiceIds
```

---

# 160. Deployment Trace Coverage

```text
Artifact→Host/JVM mapped
------------------------
Deployed Artifacts
```

---

# 161. Runtime Evidence Coverage

```text
Critical Runtime Scenarios with Evidence
----------------------------------------
Required Critical Scenarios
```

---

# 162. Critical Drift

```text
Critical Drift Count
```

Release 전 목표:

```text
0
```

---

# 163. Unknown Critical Item

```text
Unknown Critical Node
Unknown Critical Deployment
Unknown Critical Key
Unknown Critical TX
```

HG90 전 0을 목표로 한다.

---

# 164. Architecture Dashboard Drill-down

## FIG-IB-63. Dashboard

```text
Architecture Health
  ↓
Application
  ↓
ServiceId
  ↓
Source
  ↓
Deployment
  ↓
Runtime Evidence
```

---

# 165. Source Evidence Dashboard

```text
ServiceId
├─ Handler
├─ Facade
├─ Service
├─ DAO
├─ Mapper
└─ SqlId
```

---

# 166. Deployment Dashboard

```text
Artifact
├─ Hash
├─ Environment
├─ Host
├─ JVM
├─ Context
└─ DeploymentId
```

---

# 167. Runtime Dashboard

```text
ServiceId
├─ TPS
├─ p95
├─ Error
├─ Timeout
├─ Worker
├─ Hikari
└─ SQL
```

---

# 168. Security Dashboard

```text
JWT Algorithm
kid
Key Fingerprint
JWKS
Unknown kid
Auth Failure
Identity Mismatch
```

---

# 169. DR Dashboard

```text
Critical Service
├─ Main
├─ DR
├─ Artifact Sync
├─ Config Sync
├─ Key Sync
├─ Data Sync
└─ Last DR Evidence
```

---

# 170. PDMG Alignment — Strong Areas

## FIG-IB-64. Strong Reference

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
Mapper
```

대표 Source에서 강한 Trace가 확인되는 구간이다.

---

# 171. PDMG Alignment — Partial Areas

```text
Error Standard
TCF OFF
Context Type Safety
Runtime Evidence
Deployment Mapping
Authorization
```

---

# 172. PDMG Alignment — Critical GAP

```text
RS256 Issuer vs HMAC Verifier
Ephemeral JWT Key
JWT Principal vs Header Identity
Query/TX Timeout
Direct WAS Security
Generic Error
```

---

# 173. PDMG `pdmg-om`

```text
Module Name exists in baseline
       │
       ▼
Actual Source / Runtime
       │
       ▼
[UNKNOWN]
```

UNKNOWN을 Target 기능으로 채우지 않는다.

---

# 174. NSIGHT Target Promotion Rule

## FIG-IB-65. Reference Promotion

```text
PDMG AS-IS Pattern
      ↓
Evidence
      ↓
NFR Fit?
      ↓
Scope Fit?
      ↓
Security/Operations Fit?
      ↓
ADR
      ↓
Approval
      ↓
NSIGHT TO-BE Standard
```

---

# 175. 자동 Promotion 금지

```text
PDMG에 구현됨
      ↓
NSIGHT 표준

X
```

---

# 176. Baseline Promotion 후보

```text
ServiceId Routing
Thin Handler
Facade Boundary
Service/DAO Separation
GUID Correlation
Standard Envelope
Framework/Common Separation
Mapper Naming Trace
```

---

# 177. 개선 후 Promotion 후보

```text
TCF ON/OFF Runtime
Timeout Budget
JWT
Error Contract
Context Model
OM
Runtime Evidence
```

---

# 178. Architecture Principle Registry

## FIG-IB-66. Principle Registry

```text
P-01
P-02
P-03
P-04
P-05 [GAP]
P-06
P-07
P-08
P-09
P-10
P-11
P-12
```

현재 P-05가 누락된 자료가 있다면:

```text
[GAP]
```

으로 유지한다.

---

# 179. Principle → Rule → Evidence

```text
Principle
  ↓
Rule
  ↓
Test
  ↓
Runtime Evidence
```

---

# 180. Example — Thin Handler

## FIG-IB-67. Principle Operationalization

```text
Principle
Handler는 Business Entry Adapter
      ↓
Rule
Handler→DAO forbidden
      ↓
Static Test
      ↓
PASS / FAIL
      ↓
Evidence
```

---

# 181. Example — Timeout

```text
Principle
하위 Timeout < 상위 Timeout
      ↓
Rule
Query < Worker < Client
      ↓
Config Scan + Integration Test
      ↓
Evidence
```

---

# 182. Example — Identity

```text
Principle
Business Identity는 Trusted Principal에서 유도
      ↓
Rule
JWT Subject ↔ Header User Binding
      ↓
Security Test
      ↓
Evidence
```

---

# 183. Example — Runtime Evidence

```text
Principle
Architecture는 Runtime으로 검증
      ↓
Rule
Critical Service Runtime Evidence required
      ↓
Gate G50
```

---

# 184. Baseline Release Candidate

## FIG-IB-68. RC

```text
RC
│
├─ Documents Complete
├─ Model Complete
├─ ServiceId Registry Complete
├─ Rule/Test PASS
├─ Deployment Trace Complete
├─ Runtime Evidence Complete
├─ Drift Reviewed
├─ GAP/ADR Complete
└─ Approval Ready
```

---

# 185. Baseline Release

```text
RC
  ↓
G80 Approval
  ↓
HG90
  ↓
Released Baseline
```

---

# 186. Release 후 변화

## FIG-IB-69. Baseline Never Stops

```text
Released Baseline
   ↓
Source / Config / Runtime Change
   ↓
Drift
   ↓
Review
   ↓
New ADR
   ↓
New Baseline
```

---

# 187. Continuous Architecture

```text
Architecture
=
Design
+
Verification
+
Operation
+
Change Governance
```

---

# 188. Architecture as Document

```text
Markdown
PPT
Guide
```

사람이 이해하기 위한 표현이다.

---

# 189. Architecture as Model

```text
JSON / Graph / Inventory
```

기계가 이해하기 위한 표현이다.

---

# 190. Architecture as Code

```text
Rule
Scanner
Test
CI Gate
```

자동 검증을 위한 표현이다.

---

# 191. Architecture as Evidence

```text
Runtime
Deployment
Performance
Security
DR
```

설계가 실제 동작한다는 증명이다.

---

# 192. Architecture as Governance

```text
ADR
Gate
Approval
Baseline
```

변경을 통제하는 체계다.

---

# 193. Integrated Architecture Equation

## FIG-IB-70. Final Equation

```text
Architecture
=
Document
+
Model
+
Code Rule
+
Runtime Evidence
+
Governance
```

---

# 194. Master Traceability Matrix

## FIG-IB-71. Master Matrix

```text
Requirement
  ↓
Principle
  ↓
ADR
  ↓
Domain
  ↓
Application
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
SqlId
  ↓
Table
  ↓
Artifact
  ↓
Deployment
  ↓
Runtime
  ↓
Evidence
```

---

# 195. Master Matrix 최소 컬럼

## TEXT ARCHITECTURE 보완 — Master Matrix 최소 컬럼

```text
Requirement
  ↓
Architecture
  ↓
Application / ServiceId
  ↓
Source
  ↓
Data
  ↓
Build / Artifact
  ↓
Deployment
  ↓
Runtime Evidence
  ↓
GAP / ADR / Gate
```


| 영역 | 컬럼 |
|---|---|
| Requirement | Requirement ID |
| Architecture | Principle / ADR / Baseline |
| Application | Group / Business / Program |
| Runtime | ServiceId / InterfaceId |
| Source | Handler / Facade / Service / DAO |
| Data | Mapper / SqlId / Table |
| Build | Commit / Build / Artifact Hash |
| Deploy | DeploymentId / Host / JVM |
| Evidence | GUID / Scenario / Result |
| Governance | GAP / Risk / ADR / Gate |

---

# 196. Master ServiceId Registry Template

```yaml
service:
  serviceId:
  applicationGroup:
  businessCode:
  functionCode:
  programId:
  transactionType:
  sequence:
  ui:
  endpoint:
  handler:
  facade:
  service:
  dao:
  mapper:
  sqlIds:
  tables:
  txPolicy:
  timeoutPolicy:
  securityPolicy:
  deployment:
  runtimeEvidence:
  owner:
  status:
```

---

# 197. Master Deployment Registry

```yaml
deployment:
  application:
  artifact:
  artifactHash:
  sourceCommit:
  environment:
  center:
  host:
  jvm:
  context:
  port:
  datasource:
  serviceIds:
  deploymentId:
  deployedAt:
  status:
```

---

# 198. Master Runtime Evidence Registry

```yaml
runtimeEvidence:
  evidenceId:
  baselineId:
  deploymentId:
  serviceId:
  guid:
  scenario:
  expected:
  actual:
  result:
  metrics:
  logs:
  owner:
  hash:
```

---

# 199. Master GAP Registry

```yaml
gap:
  id:
  architectureLayer:
  expected:
  actual:
  source:
  severity:
  impact:
  owner:
  adr:
  dueDate:
  status:
```

---

# 200. Master ADR Registry

```yaml
adr:
  id:
  title:
  status:
  context:
  decision:
  alternatives:
  consequences:
  affectedLayers:
  affectedServices:
  evidence:
  owner:
  approvedAt:
```

---

# 201. Integrated Baseline Conformance Rules

```text
R-NAMING-AXIS
R-SERVICEID-FORMAT
R-SERVICEID-UNIQUE
R-HANDLER-BRANCH
R-HANDLER-NO-DAO
R-CONTROLLER-NO-DAO
R-DAO-MAPPER
R-SQL-TABLE-TRACE
R-TX-OWNER
R-TIMEOUT-HIERARCHY
R-RETRY-IDEMPOTENCY
R-JWT-ALGORITHM
R-JWT-KEY-CONSISTENCY
R-IDENTITY-BINDING
R-SENSITIVE-LOG
R-DEPLOYMENT-MANIFEST
R-RUNTIME-INVENTORY
R-RUNTIME-EVIDENCE
R-DR-EVIDENCE
R-RESTORE-EVIDENCE
```

---

# 202. Integrated Gate Rule

## FIG-IB-72. Baseline Gate Rule

```text
Critical Rule Fail
= 0

Critical Drift
= 0

Critical Unknown
= 0

Critical Runtime Evidence Missing
= 0
```

이 조건들은 Release Policy 후보이며 실제 프로젝트 승인 후 확정한다.

---

# 203. Quality Gate Dashboard

```text
Rule Pass %
Critical Fail
Critical Drift
Open Critical GAP
Unknown Critical
Evidence Coverage
Trace Coverage
```

---

# 204. Architecture Review Checklist — Naming

```text
[ ] Application Group?
[ ] Business Code?
[ ] Function?
[ ] Program ID?
[ ] ServiceId?
[ ] Java Package?
[ ] Mapper Package?
[ ] Artifact?
```

---

# 205. Review Checklist — ServiceId

```text
[ ] Format?
[ ] Unique?
[ ] Handler Registered?
[ ] Branch?
[ ] UI Catalog?
[ ] Endpoint?
[ ] Monitoring?
[ ] Owner?
```

---

# 206. Review Checklist — Source Trace

```text
[ ] Handler?
[ ] Facade?
[ ] Service?
[ ] DAO?
[ ] Mapper?
[ ] SqlId?
[ ] Table/View?
```

---

# 207. Review Checklist — Transaction

```text
[ ] Runtime Entry?
[ ] TX Owner?
[ ] TransactionManager?
[ ] Datasource?
[ ] Rollback?
[ ] Timeout?
[ ] Late Commit?
```

---

# 208. Review Checklist — Security

```text
[ ] Authentication?
[ ] Authorization?
[ ] Algorithm?
[ ] kid?
[ ] Key Source?
[ ] Identity Binding?
[ ] Revocation?
[ ] Sensitive Log?
```

---

# 209. Review Checklist — Deployment

```text
[ ] Commit?
[ ] Artifact?
[ ] Hash?
[ ] DeploymentId?
[ ] Environment?
[ ] Host?
[ ] JVM?
[ ] Config Version?
```

---

# 210. Review Checklist — Runtime Evidence

```text
[ ] Scenario?
[ ] ServiceId?
[ ] GUID?
[ ] Expected?
[ ] Actual?
[ ] Metrics?
[ ] Logs?
[ ] Result?
[ ] Evidence Hash?
```

---

# 211. Review Checklist — HA / DR

```text
[ ] Node Failover?
[ ] Residual Capacity?
[ ] DR Artifact?
[ ] DR Config?
[ ] DR Key?
[ ] DR Data?
[ ] DR Business Test?
[ ] Restore Test?
```

---

# 212. Review Checklist — Drift

```text
[ ] Document?
[ ] Model?
[ ] Source?
[ ] Config?
[ ] Deployment?
[ ] Runtime?
[ ] Security?
[ ] Data?
```

---

# 213. Review Checklist — Gate

```text
[ ] G00?
[ ] G10?
[ ] G20?
[ ] G30?
[ ] G40?
[ ] G50?
[ ] G60?
[ ] G70?
[ ] G80?
[ ] HG90?
```

---

# 214. Integrated Architecture Completion Gate

## FIG-IB-73. Final Completion

```text
VISION defined?
  ↓
BIG PICTURE defined?
  ↓
LOGICAL defined?
  ↓
PHYSICAL defined?
  ↓
MECHANISM defined?
  ↓
RUNTIME defined?
  ↓
TRACEABILITY complete?
  ↓
SOURCE reference verified?
  ↓
OPERATIONS ready?
  ↓
MODEL complete?
  ↓
RULE/Test PASS?
  ↓
RUNTIME EVIDENCE?
  ↓
DRIFT reviewed?
  ↓
ADR approved?
  ↓
HG90 RELEASE
```

---

# 215. 전체 10장 Architecture Journey

## FIG-IB-74. 01 → 10

```text
01 VISION
왜 바꾸는가?
   ↓
02 BIG PICTURE
누가 무엇을 책임하는가?
   ↓
03 LOGICAL
어떤 논리 구조인가?
   ↓
04 PHYSICAL
어디에 배치하는가?
   ↓
05 MECHANISM
어떤 규칙으로 동작하는가?
   ↓
06 RUNTIME
실제로 어떻게 실행되는가?
   ↓
07 TRACEABILITY / CLOSED LOOP
어떻게 검증하고 유지하는가?
   ↓
08 PDMG SOURCE REFERENCE
실제 구현은 어떻게 되어 있는가?
   ↓
09 OM / DEVOPS / OBSERVABILITY
어떻게 배포·관찰·복구하는가?
   ↓
10 INTEGRATED BASELINE
어떻게 하나의 승인 가능한 Architecture Baseline으로 묶는가?
```

---

# 216. Architecture Storyline

## FIG-IB-75. One Story

```text
WHY
Vision
 ↓
WHO / WHAT
Big Picture
 ↓
HOW STRUCTURED
Logical
 ↓
WHERE
Physical
 ↓
HOW CONTROLLED
Mechanism
 ↓
HOW EXECUTED
Runtime
 ↓
HOW PROVEN
Traceability
 ↓
WHAT EXISTS
PDMG Source
 ↓
HOW OPERATED
Operations
 ↓
HOW RELEASED
Integrated Baseline
```

---

# 217. 최종 Top-down / Bottom-up 통합

## FIG-IB-76. Final Closed Loop

```text
                   TOP-DOWN
VISION
  ↓
BIG PICTURE
  ↓
LOGICAL
  ↓
PHYSICAL
  ↓
MECHANISM
  ↓
RUNTIME
  ↓
──────────────────────────
      RUNTIME EVIDENCE
──────────────────────────
  ↑
DEPLOYMENT
  ↑
ARTIFACT
  ↑
SOURCE / CONFIG
  ↑
MODEL / TRACE
  ↑
DRIFT / GAP
  ↑
ADR
  ↑
NEW BASELINE
                 BOTTOM-UP
```

---

# 218. 최종 핵심 Architecture 원칙

```text
1. Responsibility는 경계에 고정한다.
2. 연결은 목적별 Interface로 통제한다.
3. Naming은 Classification과 Traceability를 지원한다.
4. ServiceId는 Business Transaction의 단일 식별축으로 관리한다.
5. Module / Process / Node / JVM / WAR를 혼동하지 않는다.
6. Framework와 Business 책임을 분리한다.
7. Transaction / Timeout / Retry를 분리하고 Runtime으로 검증한다.
8. Security Identity는 Trusted Principal에서 유도한다.
9. Runtime은 Metric/Log/Trace/Evidence로 관찰 가능해야 한다.
10. Architecture는 Source/Runtime Evidence와 Closed Loop를 이뤄야 한다.
```

---

# 219. CONFIRMED / WORKING BASELINE

```text
[CONFIRMED / WORKING BASELINE]

- NSIGHT Architecture 흐름은 Vision→Big Picture→Logical→Physical→Mechanism→Runtime
- PDMG는 Source/Runtime Reference
- Application/Business/Function/Program/ServiceId Naming 축
- PDMG Current Handler Registry = 13 ServiceIds
- Handler→Facade→Service→DAO→Mapper의 대표 Source Trace
- Java Package = nhnis.mg.co.a
- Mapper Package = rdw.mg.co.a
- GUID = std_gbl_id
- PDMG timeout snapshot = 5000ms / Worker20 / Queue100
- Source→Artifact→Deployment→Runtime Evidence Trace 필요
- G00→G80→HG90 Gate
- Architecture Baseline = Document + Model + Rule + Evidence + Approval
```

---

# 220. CONFLICT / DRIFT / UNKNOWN

```text
[CONFLICT]
CDC SLA 30s vs 3s

[CONFLICT]
Session 60m vs 90m

[DRIFT]
Filter order old doc vs current source

[DRIFT]
Past ServiceId count 8 vs current 13

[CRITICAL GAP]
RS256 issuer vs HMAC verifier

[UNKNOWN]
pdmg-om actual implementation

[OPEN]
Production deployment mapping
```

---

# 221. GAP Register

## TEXT ARCHITECTURE 보완 — GAP Register

```text
Expected / Required
      │
      ▼
Actual / Evidence
      │
      ▼
Difference
      │
      ▼
[GAP]
      │
      ├─ Owner
      ├─ Action
      ├─ ADR
      └─ Evidence
```


| ID | GAP | 영향 |
|---|---|---|
| GAP-IB-01 | ServiceId SSOT 미완료 | Naming/Trace |
| GAP-IB-02 | UI Catalog↔Backend Registry 자동비교 미완료 | Trace |
| GAP-IB-03 | Handler→SQL→Table 전수 Trace 미완료 | Data |
| GAP-IB-04 | Architecture Model Schema 미확정 | Model |
| GAP-IB-05 | Architecture Rule Engine 전수구현 미완료 | Governance |
| GAP-IB-06 | Query/TX Timeout 미확정 | Runtime |
| GAP-IB-07 | TCF OFF Business Boundary 불일치 | Application |
| GAP-IB-08 | JWT Algorithm/Verifier 불일치 | Security |
| GAP-IB-09 | JWT Key Lifecycle 미완료 | Security |
| GAP-IB-10 | Identity Binding 미완료 | Security |
| GAP-IB-11 | Deployment Manifest 미완료 | Deployment |
| GAP-IB-12 | Runtime Inventory 미완료 | Operations |
| GAP-IB-13 | Runtime Evidence Manifest 자동화 미완료 | Evidence |
| GAP-IB-14 | Config/Deployment Drift 자동화 미완료 | Drift |
| GAP-IB-15 | pdmg-om Source/Runtime 미확인 | OM |
| GAP-IB-16 | DR/Restore Evidence 자동연계 미완료 | Availability |
| GAP-IB-17 | Gate RACI 미확정 | Governance |
| GAP-IB-18 | Baseline Versioning Rule 미확정 | Release |
| GAP-IB-19 | Critical Service Evidence Coverage 미확정 | Runtime |
| GAP-IB-20 | Principle P-05 누락 확인 필요 | Principle |

---

# 222. RISK Register

## TEXT ARCHITECTURE 보완 — RISK Register

```text
Cause
  ↓
Risk Event
  ↓
Business / Data / Security / Operation Impact
  ↓
Severity
  ↓
Mitigation / Control
  ↓
Owner
  ↓
Evidence / Closure
```


| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-IB-01 | Naming Drift로 자동추적 실패 | High |
| RISK-IB-02 | ServiceId SSOT 불일치 | High |
| RISK-IB-03 | Handler→DAO 직접침투 | High |
| RISK-IB-04 | Mapper/SQL Trace 누락 | High |
| RISK-IB-05 | TX Owner 오판 | Critical |
| RISK-IB-06 | Timeout Late Commit | Critical |
| RISK-IB-07 | JWT Algorithm/Key 불일치 | Critical |
| RISK-IB-08 | Identity Mismatch | Critical |
| RISK-IB-09 | Wrong Artifact Production | Critical |
| RISK-IB-10 | Runtime Evidence 미연결 | Critical |
| RISK-IB-11 | Critical Drift 미탐지 | Critical |
| RISK-IB-12 | UNKNOWN 장기 방치 | High |
| RISK-IB-13 | Gate 형식화 | Critical |
| RISK-IB-14 | PDMG AS-IS의 무검증 Target 승격 | High |
| RISK-IB-15 | Baseline Release 후 Change Drift | High |

---

# 223. ADR 후보

```text
ADR-IB-01 Classification / Naming Standard
ADR-IB-02 ServiceId SSOT
ADR-IB-03 ServiceId Registry Governance
ADR-IB-04 Package/Mapper Naming
ADR-IB-05 Source Trace Model
ADR-IB-06 Architecture Model Schema
ADR-IB-07 Rule Engine
ADR-IB-08 Transaction Ownership
ADR-IB-09 Timeout Budget
ADR-IB-10 TCF ON/OFF Business Core
ADR-IB-11 JWT Algorithm Alignment
ADR-IB-12 JWT Key Lifecycle
ADR-IB-13 Identity Binding
ADR-IB-14 Deployment Manifest
ADR-IB-15 Runtime Inventory
ADR-IB-16 Runtime Evidence
ADR-IB-17 Drift Automation
ADR-IB-18 Gate RACI
ADR-IB-19 Baseline Versioning
ADR-IB-20 Principle P-05 Resolution
```

---

# 보완검토 A. INTEGRATED BASELINE 시각화 보강

## FIG-IB-SUP-01. Master Matrix

```text
Requirement
  ↓
Principle / ADR
  ↓
Application / Program
  ↓
ServiceId
  ↓
Source
  ↓
SQL / Data
  ↓
Artifact
  ↓
Deployment
  ↓
Runtime / GUID
  ↓
Evidence
  ↓
GAP / ADR / Gate
```

---

## FIG-IB-SUP-02. SSOT 연결

```text
Architecture Model
       │
       ├─ ServiceId Registry
       ├─ Interface Catalog
       ├─ Deployment Registry
       ├─ Runtime Scenario
       └─ Evidence Index
              │
              ▼
        Single Baseline View
```

---

## FIG-IB-SUP-03. Baseline 상태기계

```text
DRAFT
  ↓
RC
  ↓
G00~G70
  ↓
G80 APPROVED
  ↓
HG90 RELEASED
  ↓
DRIFT / CHANGE
  ↓
NEXT DRAFT
```

---

## FIG-IB-SUP-04. 최종 Architecture 운영모델

```text
Document
   +
Model
   +
Rule
   +
Source / Config
   +
Deployment
   +
Runtime Evidence
   +
Governance
        ↓
Living Architecture Baseline
```

---

# 224. Definition of Done

## TEXT ARCHITECTURE 보완 — Definition of Done

```text
Architecture Inputs
      ↓
Structure / Contract / Runtime Check
      ↓
Evidence Check
      ↓
GAP / CONFLICT / UNKNOWN Review
      ↓
PASS Condition
      │
      ├─ satisfied → PASS
      └─ pending   → CONDITIONAL PASS
```


## Classification / Naming
- [x] Service Domain/Application Group 분리
- [x] Business/Function/Program/ServiceId 계층
- [x] Java/Mapper/ServiceId Naming 축
- [x] Identifier Taxonomy

## ServiceId
- [x] Anatomy
- [x] Regex
- [x] Current 13 Registry
- [x] Unique/Branch Rule
- [x] Handler Routing
- [x] SSOT 방향

## Source Trace
- [x] Handler→Facade→Service→DAO→Mapper
- [x] Mapper Namespace
- [x] SqlId
- [x] SQL→Table
- [x] Program Stem

## Deployment / Runtime Trace
- [x] Source→Artifact
- [x] Artifact→Deployment
- [x] Deployment→Runtime
- [x] GUID/ServiceId
- [x] Full Forward/Reverse Trace

## Model
- [x] Entity
- [x] Relation
- [x] Deployment Relation
- [x] Runtime Relation
- [x] Evidence Relation
- [x] Model Version/Diff

## Rule / Test
- [x] Naming
- [x] ServiceId
- [x] Dependency
- [x] Mapper
- [x] TX/Timeout
- [x] Security
- [x] Deployment
- [x] Runtime Evidence

## Evidence / Drift
- [x] Evidence Chain
- [x] Evidence Manifest
- [x] Evidence Strength
- [x] State Tag
- [x] GAP/DRIFT/CONFLICT
- [x] ADR/Debt

## Gate / Release
- [x] G00~HG90
- [x] Workspace 00-IN~90-OUT
- [x] Baseline Package
- [x] Baseline Diff
- [x] Review Package
- [x] KPI
- [x] Completion Gate

**INTEGRATED ARCHITECTURE BASELINE 장 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. ServiceId SSOT 확정
2. UI Catalog↔Handler Registry 자동비교
3. Handler→SqlId→Table 자동 Trace
4. Architecture Model Schema 확정
5. Rule Engine / CI Gate 적용
6. Query/TX Timeout 실제값 확정
7. TCF OFF Business Core 정합성 확보
8. JWT Issuer/Verifier Algorithm 정합
9. JWT Key Lifecycle / Identity Binding 해결
10. Deployment Manifest 자동화
11. Runtime Inventory 자동수집
12. Runtime Evidence Manifest 자동화
13. Drift 자동탐지
14. pdmg-om Source/Runtime 확인
15. DR/Restore Evidence 연결
16. Gate RACI 승인
17. Baseline Versioning Rule 승인
18. Critical Drift 0
19. Critical Unknown 0
20. Critical Runtime Evidence Coverage 기준 충족

---

# 225. 최종 결론


## FIG-IB-SUP-05. 최종 Baseline 결론

```text
01~09 Architecture Knowledge
        ↓
Classification / Naming / ServiceId
        ↓
Source / Data / Deployment Trace
        ↓
Model / Rule / Test
        ↓
Runtime Evidence
        ↓
Drift / GAP / ADR
        ↓
G80 Approval
        ↓
HG90 Released Architecture Baseline
        ↓
Runtime Change
        └──────────────► Next Baseline
```

> **10장은 01~09장을 한 권의 Architecture Baseline으로 묶는 장이다.**

> **Naming은 단순 규칙이 아니라 Application→Program→ServiceId→Package→Mapper→Runtime을 연결하는 Traceability Backbone이다.**

> **ServiceId는 PDMG에서 실제 Dispatcher Routing Key로 동작하므로, Source·Data·Deployment·Monitoring·Runtime Evidence를 연결하는 핵심 Business Transaction Key로 관리할 가치가 크다.**

> **Architecture Baseline은 문서가 아니라 Document + Model + Rule + Source + Deployment + Runtime Evidence + Drift + ADR + Approval의 결합물이다.**

> **최종적으로 NSIGHT Architecture는 Top-down 설계와 Bottom-up Evidence가 HG90 Baseline Release에서 만나고, 이후 Runtime Drift가 다시 다음 Baseline을 만드는 지속적 Closed Loop로 운영되어야 한다.**

---
