# NSIGHT / PDMG 아키텍처 정의서
# 01. VISION — Architecture Vision & Strategy
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
