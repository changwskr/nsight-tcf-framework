# NSIGHT / PDMG 아키텍처 정의서
# 01. VISION — Architecture Vision & Strategy 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-TYPE2-VISION-01-DETAIL`  
> Architecture Level: **VISION**  
> PPT 공식 범위: **1. 아키텍처 정의 > 1.1 개요**  
> PPT 연계 범위: **1.2 어플리케이션 분류 체계 / 1.3 데이터 주제영역 정의 / 1.4 시스템 아키텍처 구성**  
> 문서 상태: **Draft / Evidence-First / PPT-Aligned / Detailed Baseline**  
> 작성 기준일: **2026-08-31**  
> 작성 원칙: **PPT-First / Evidence-First / Top-down / Text Architecture / AS-IS·TO-BE 분리 / PDMG Reference**  
> 선행 문서: `NSIGHT_PDMG_아키텍처_정의서_00_목차_및_작성기준_TYPE2_PPT정합본.md`  
> 후속 문서: `02. BIG PICTURE`  
> 핵심 질문: **왜 차세대 정보계를 개편하며, 어떤 Architecture Vision·Principle·NFR을 기준으로 어디로 갈 것인가?**

---

# 0. 문서 사용법

이 문서는 제품·서버·Framework 목록을 설명하는 문서가 아니다.

VISION 단계에서 가장 먼저 고정해야 하는 것은 다음 네 가지다.

```text
왜 바꾸는가?
      ↓
무엇을 달성해야 하는가?
      ↓
어떤 원칙을 절대 지켜야 하는가?
      ↓
후속 Architecture가 무엇을 증명해야 하는가?
```

따라서 본 장은 다음과 같은 구조로 작성한다.

```text
PPT 공식 구조
  ↓
AS-IS 문제
  ↓
개편 기본 방향
  ↓
구축 방향 및 목표
  ↓
Architecture Vision
  ↓
Architecture Principle
  ↓
NFR / SLA
  ↓
Decision / Guardrail
  ↓
PDMG Reference 위치
  ↓
GAP / OPEN / ADR
  ↓
BIG PICTURE Handoff
```

---

# 0.1 Evidence 상태 태그

본 문서에서는 다음 상태 태그를 사용한다.

| 태그 | 의미 |
|---|---|
| `[PPT-TOC]` | PPT 공식 목차에서 직접 확인 |
| `[PPT-BODY]` | PPT 본문 장표에서 직접 확인 |
| `[FACT]` | 공식 자료·Source·Config·Runtime에서 직접 확인 |
| `[CONFIRMED]` | 복수 근거가 동일 결론을 지지 |
| `[BASELINE-YYYY-MM-DD]` | 특정 시점의 목표/수치/설계 |
| `[AS-IS]` | 현행 또는 현재 PDMG 구현 |
| `[TO-BE]` | 목표 아키텍처 |
| `[PROPOSED]` | 전략/제안자료에 존재하나 승인상태 미확정 |
| `[DECISION]` | 승인된 의사결정 |
| `[GAP]` | 목표와 현재/자료 사이의 차이 |
| `[CONFLICT]` | 복수 근거의 값 또는 정책 불일치 |
| `[RISK]` | 성능·장애·보안·운영 위험 |
| `[OPEN]` | 추가 의사결정 필요 |
| `[UNKNOWN]` | 현재 자료만으로 확인 불가 |
| `[DEPRECATED]` | 과거 기준 또는 폐기 대상 |

---

# 0.2 Evidence Register

| ID | 근거 자료 | 본 장 사용 목적 | 상태 |
|---|---|---|---|
| EV-VSN-01 | `NSIGHT_PDMG_아키텍처_인포그래픽_이미지화_마스터_프롬프트_TYPE2_PPT정합본` | PPT 공식 구조, VISION→BIG PICTURE→LOGICAL→PHYSICAL→MECHANISM→RUNTIME 순서 | `[WORKING BASELINE]` |
| EV-VSN-02 | `NSIGHT_PDMG_아키텍처_정의서_I_비전_전략` | 전략적 배경, 5대 NFR, 6단계 방법론, FAST/DEEP, 원칙 | `[CURRENT BASELINE DRAFT]` |
| EV-VSN-03 | `NSIGHT_PDMG_아키텍처_정의서_II_BigPicture_SystemBoundary` | 5대 서비스 도메인, 3대 통제, Boundary Handoff | `[CURRENT BASELINE DRAFT]` |
| EV-VSN-04 | `NSIGHT_PDMG_아키텍처_정의서_III_PDMG_Module_Application_Architecture` | PDMG가 NSIGHT 전체가 아닌 Application/Framework Reference임을 검증 | `[AS-IS REFERENCE]` |
| EV-VSN-05 | `NSIGHT_PDMG_아키텍처_정의서_IV_PDMG_Online_Runtime_TCF_Flow` | PDMG Runtime이 Vision의 하위 Evidence라는 위치 검증 | `[AS-IS REFERENCE]` |
| EV-VSN-06 | `NSIGHT_PDMG_아키텍처_정의서_V_Transaction_Timeout_Thread_DB_Architecture` | Runtime/Timeout 수치를 Vision Target으로 자동 승격하지 않기 위한 근거 | `[AS-IS REFERENCE]` |
| EV-VSN-07 | `NSIGHT_PDMG_아키텍처_정의서_VIII_Infrastructure_WAS_Capacity_HA_DR` | Capacity/HA/DR 수치가 Variant임을 확인 | `[CAPACITY REFERENCE]` |
| EV-VSN-08 | `NSIGHT_PDMG_아키텍처_정의서_IX_DevOps_OM_Observability` | Standard→Deploy→Runtime Evidence→Drift 운영 Closed Loop | `[WORKING BASELINE]` |
| EV-VSN-09 | `NSIGHT_PDMG_아키텍처_정의서_X_Naming_ServiceId_Traceability_Closed_Loop` | Requirement→Architecture→Source→Runtime→ADR Closed Loop | `[GOVERNANCE BASELINE]` |

---

# 1. PPT 공식 구조

[PPT-TOC]

```text
1. 아키텍처 정의
   1.1 개요
   1.2 어플리케이션 분류 체계
   1.3 데이터 주제영역 정의
   1.4 시스템 아키텍처 구성
```

TYPE2 Architecture Route에서는 다음처럼 분리한다.

```text
1.1 개요
  → VISION

1.2 어플리케이션 분류 체계
1.3 데이터 주제영역 정의
1.4 시스템 아키텍처 구성
  → BIG PICTURE
```

따라서 본 문서는 **PPT 1.1 개요를 상세 Architecture Vision 정의서로 확장**한다.

---

# 1.1 1장 전체에서 VISION의 위치

```text
┌──────────────────── 1. 아키텍처 정의 ────────────────────┐
│                                                          │
│ 1.1 개요                                                 │
│ ├─ 왜 바꾸는가                                           │
│ ├─ 어떤 목표를 갖는가                                    │
│ ├─ 어떤 원칙을 지킬 것인가                               │
│ └─ 어떤 NFR을 후속 설계의 Guardrail로 둘 것인가         │
│                 │                                        │
│                 ▼                                        │
│ 1.2 어플리케이션 분류 체계                               │
│ 1.3 데이터 주제영역 정의                                 │
│ 1.4 시스템 아키텍처 구성                                 │
│                                                          │
└──────────────────────────────────────────────────────────┘
```

즉, 1.1의 Output이 1.2~1.4의 Input이다.

---

# 2. 핵심 결론

NSIGHT의 목표는 기존 정보계를 단순히 확장하거나 DW 장비를 교체하는 것이 아니다.

목표는 다음 전환이다.

```text
[AS-IS]
배치 중심
DW 창고 중심
익일 정보 제공
고정시점 마케팅
분산·중복 데이터
분석/운영 자원 경합
비표준 연계
낮은 End-to-End 추적성

              ↓

[TO-BE]
Data-Centric
Near Real-time
Event Driven Reaction
통합 데이터 기반
RDW / ADW 책임 분리
Online / Event / Batch / ETL 자원 분리
표준 Interface
GUID / ServiceId 기반 추적
Runtime Evidence 기반 운영
```

NSIGHT Architecture Vision을 한 문장으로 정리하면 다음과 같다.

> **신뢰 가능한 데이터를 기반으로 고객 행동에 빠르게 반응하고, 실시간 운영과 전략 분석을 동시에 제공하며, 확장·장애·보안·운영 상태를 Runtime Evidence로 검증할 수 있는 Data-Centric 차세대 정보계 플랫폼을 구축한다.**

이를 Architecture Keyword로 표현하면:

```text
Scalable
+
Resilient
+
Data-Centric
+
Standardized
+
Observable
+
Traceable
```

---

# 3. 아키텍처 정의 목적

## 3.1 왜 Architecture Definition이 필요한가

Architecture 정의의 목적은 “시스템 구성도를 만드는 것”이 아니다.

Architecture는 다음을 하나의 계약으로 묶는다.

```text
Business Requirement
       ↓
Architecture Principle
       ↓
Application / Data / Technical Structure
       ↓
Standard / Mechanism
       ↓
Source / Config
       ↓
Runtime
       ↓
Evidence
```

즉 Architecture 정의는:

1. 무엇을 만들 것인지 정하고,
2. 어디에 책임을 둘지 정하고,
3. 어떤 연결만 허용할지 정하고,
4. 어떤 NFR을 지켜야 하는지 정하고,
5. 실제 구현이 그 계약을 지켰는지 검증하기 위한 기준을 만든다.

---

# 3.2 Architecture 3축

기존 정의 자료에서 Architecture는 크게 다음 세 축으로 구분된다.

```text
Application Architecture
Data Architecture
Technical Architecture
```

이 세 축은 별개 산출물이 아니라 서로 종속된다.

```text
Application Responsibility
        │
        ├───────────────┐
        ▼               ▼
Data Responsibility   Technical Execution
        │               │
        └───────┬───────┘
                ▼
             Runtime
```

## 3.2.1 Application Architecture

다음 질문을 해결한다.

```text
어떤 업무를
어떤 Application이
어떤 책임으로
어떤 Interface를 통해
처리할 것인가?
```

## 3.2.2 Data Architecture

다음 질문을 해결한다.

```text
어떤 데이터가
어디에서 생성되고
누가 소유하며
RDW/ADW 어디에 위치하고
어떻게 유입/가공/제공되는가?
```

## 3.2.3 Technical Architecture

다음 질문을 해결한다.

```text
어떤 Logical Node가
어떤 Host/JVM/DB/SW에 배치되고
어떤 Network/HA/DR/Monitoring 구조로
실행되는가?
```

---

# 4. 차세대 정보계 개편 배경

## 4.1 현행 구조적 문제

기존 전략 자료에서 반복적으로 확인되는 핵심 AS-IS 문제는 다음과 같다.

| 문제 | Architecture 영향 |
|---|---|
| 야간 배치 후 익일 데이터 제공 | 고객행동 기반 즉시 반응 불가 |
| 정해진 시점의 마케팅 오퍼링 | Event 기반 동적 오퍼링 한계 |
| 단순 DW/데이터 창고 중심 | 데이터→판단→행동 연결 단절 |
| 분석 쿼리 부하 | 운영/정보제공 성능 저하 |
| 서버별 독립·개별 운영 | 장애 파급, 확장/운영 복잡도 |
| 데이터 중복·분산 | 일관성·품질·Ownership 저하 |
| 다양한 연계 방식의 비표준화 | 장애/변경/보안 영향 증가 |
| App/IF/Batch 모니터링 부족 | 장애 원인 식별 지연 |
| 개발·운영 표준 미흡 | 변경품질·배포품질 저하 |
| End-to-End Trace 부족 | 화면→Service→SQL→DB 역추적 어려움 |

---

# 4.2 AS-IS 문제의 Root Cause 구조

```text
                 [Batch-Centric]
                       │
          ┌────────────┼─────────────┐
          ▼            ▼             ▼
     Data Delay    Fixed Timing   Data Duplication
          │            │             │
          └──────┬─────┴──────┬──────┘
                 ▼            ▼
           Slow Reaction   Low Trust
                 │            │
                 └─────┬──────┘
                       ▼
                Business Delay

[Independent System]
        │
        ├─ Non-standard Interface
        ├─ Resource Contention
        ├─ Monitoring Fragmentation
        └─ Failure Propagation
```

핵심은 개별 기술 문제가 아니라 **구조적 책임·경계·데이터 흐름의 문제**다.

---

# 5. 개편 기본 방향

PPT 1.1의 핵심 방향은 다음 다섯 축으로 정리한다.

```text
고객 중심 서비스 강화
데이터 기반 의사결정 강화
통합 정보 활용 기반
실시간 정보 활용
운영 효율 / 민첩성
```

---

# 5.1 고객 중심 서비스 강화

## 목표

```text
고객 행동 / 상태
       ↓
빠른 정보 인지
       ↓
Customer Context
       ↓
Offer / 상담 / 안내 / 지원
       ↓
결과 Feedback
```

## Architecture Implication

```text
Marketing Platform
Event Processing
Customer Context
Near Real-time Data
Low Latency Integration
Feedback Loop
```

## 후속 Architecture 요구

- Marketing Application Responsibility 정의
- Event Runtime 정의
- Customer 관련 Data Subject 정의
- Online/Event 경로 분리
- UMS/Offering Runtime 정의
- End-to-End GUID/Trace 적용

---

# 5.2 데이터 기반 의사결정 강화

## 목표

```text
Source Data
   ↓
Integrated / Trusted Data
   ↓
Analysis / BI
   ↓
Decision
   ↓
Action
```

## Architecture Implication

```text
Data Platform
RDW
ADW
Data Governance
CDC
ETL
BI Portal
```

## 핵심 책임 분리

```text
RDW
= 운영/준실시간 정보 기반

ADW
= 분석/집계/마트 기반
```

두 영역을 하나의 “DW” 역할로 뭉개지 않는다.

---

# 5.3 통합 정보 활용 기반

## 목표

```text
분산·중복 정보
      ↓
표준 분류
      ↓
Ownership
      ↓
공통 활용
```

필요 Architecture:

```text
Application Classification
System Group
Data Subject Area
Interface Catalog
ServiceId
Traceability
```

---

# 5.4 실시간 정보 활용

실시간은 “모든 것을 실시간으로 만든다”는 의미가 아니다.

```text
즉시 반응이 필요한 업무
        ↓
FAST Runtime

정확성·대량분석이 필요한 업무
        ↓
DEEP Runtime
```

따라서 Real-time Architecture의 핵심은 **필요한 업무만 빠른 경로로 분리하는 것**이다.

---

# 5.5 운영 효율 / 민첩성

목표:

```text
Standard
  ↓
Automation
  ↓
Observability
  ↓
Fast Diagnosis
  ↓
Controlled Change
```

필요 구조:

```text
GitLab / CI/CD Strategy
Config Separation
Runtime Monitoring
GUID / ServiceId
Alert / Runbook
Drift Detection
Architecture Gate
```

---

# 6. 구축 방향 및 목표

## 6.1 구축 목표 4축

| 목표 축 | 목표 | Architecture 결과 |
|---|---|---|
| Customer / Service | 빠르고 일관된 고객 서비스 | Marketing/Event/Channel |
| Data | 신뢰 가능한 통합 데이터 | RDW/ADW/Governance |
| Architecture | 책임·경계·표준화 | Zone/System/Interface/Framework |
| Operations | 안정·확장·복구·관측 | HA/DR/Monitoring/DevOps |

---

# 6.2 Business → Architecture Goal Map

| Business Need | Architecture Goal | 후속 장 |
|---|---|---|
| 고객 행동 즉시 반응 | Event/FAST 경로 | RUNTIME |
| 데이터 신뢰성 | RDW/ADW/Governance | BIG PICTURE/PHYSICAL |
| 분석 성능 | 분석 자원 분리 | PHYSICAL |
| 빠른 변경 | 표준 Framework/DevOps | MECHANISM/RUNTIME |
| 장애 영향 최소 | Fault Isolation/HA | LOGICAL/PHYSICAL |
| 문제 원인 추적 | GUID/ServiceId/Observability | MECHANISM/RUNTIME |
| 표준 연계 | Interface Control | LOGICAL/MECHANISM |

---

# 7. Architecture Vision

## 7.1 Vision Statement

```text
Data-Centric
      +
Real-time Reaction
      +
Strategic Analysis
      +
Independent Scaling
      +
Failure Isolation
      +
Operational Evidence
```

이를 Business Language로 표현하면:

> **데이터가 생성되는 순간부터 고객 대응과 경영 의사결정에 활용될 때까지의 흐름을 끊김 없이 관리하고, 실시간 반응과 전략 분석을 상호 간섭 없이 제공하는 정보계 플랫폼을 구축한다.**

---

# 7.2 Vision Journey

```text
[AS-IS]
Batch-centric Information System
        │
        ▼
[CHANGE DRIVER]
Real-time / Data Trust / Standard / Resilience
        │
        ▼
[VISION]
Scalable · Resilient · Data-Centric
        │
        ▼
[BUSINESS RESULT]
Data → Insight → Action
        │
        ▼
[ARCHITECTURE RESULT]
Boundary + Standard IF + Resource Isolation + Trace
```

---

# 8. 6단계 Architecture 방법론

NSIGHT Architecture는 다음 단계로 구체화한다.

```text
① VISION
왜 바꾸는가
      ↓
② BIG PICTURE
무엇을 만들고 책임을 어떻게 나누는가
      ↓
③ LOGICAL
논리적으로 어디에 어떤 역할을 둘 것인가
      ↓
④ PHYSICAL
어느 물리 자원에 구현할 것인가
      ↓
⑤ MECHANISM
어떤 표준 원리로 동작시킬 것인가
      ↓
⑥ RUNTIME
실제로 어떻게 실행·실패·복구되는가
```

---

# 8.1 단계별 Definition of Done

| 단계 | 핵심 질문 | 주요 산출 | 완료조건 |
|---|---|---|---|
| VISION | 왜/어디로 | Vision/NFR/Principle | 방향 합의 |
| BIG PICTURE | 무엇/누가 | Domain/Application/Data/System | 책임공간 확정 |
| LOGICAL | 어디/경계 | Zone/Node/Component | 허용·금지 연결 확정 |
| PHYSICAL | 어디에 배치 | Host/HW/SW/DB/HA | 물리 Mapping |
| MECHANISM | 어떻게 동작 | IF/Message/GUID/FW | 실행 규칙 확정 |
| RUNTIME | 정말 동작 | Sequence/SLO/Failure | Evidence 검증 |

---

# 9. 5대 서비스 도메인 방향

VISION 단계에서는 상세 분류표를 작성하지 않지만, BIG PICTURE로 전달할 상위 책임은 고정한다.

```text
① 데이터플랫폼
② 마케팅플랫폼
③ BI 포탈
④ 데이터거버넌스
⑤ IT 서비스 및 업무지원
```

---

# 9.1 책임 Map

```text
                          [IT Service & Support]
                         SSO / DevOps / OM / Batch
                                  │
               ┌──────────────────┼──────────────────┐
               ▼                  ▼                  ▼
        [Marketing]            [BI Portal]      [Governance]
      고객/캠페인/실시간      분석/리포트        메타/품질/흐름
               │                  │                  │
               └──────────────┬───┘                  │
                              ▼                      │
                         [Data Platform] ◄────────────┘
                           RDW / ADW
```

VISION에서 상세 Application 이름은 확정하지 않는다.

---

# 10. Big Picture로 전달할 3대 통제

전략의 중심 통제는 다음 세 축이다.

```text
① 규격 표준
② 인터페이스 통제
③ 자원 분리
```

---

# 10.1 규격 표준

목표:

```text
Naming
Message
ServiceId
Interface Contract
Error
GUID
Charset
Deployment
```

표준은 문서로만 존재해서는 안 된다.

```text
Standard
  ↓
Rule
  ↓
Test
  ↓
Runtime Evidence
```

---

# 10.2 인터페이스 통제

원칙:

```text
책임은 System에 고정
연결은 Boundary에서 통제
```

목적별 Mechanism:

```text
Transaction → API / MCA
Event       → Kafka/Event
DB Change   → CDC
Bulk Data   → ETL
File        → FOS/MFT
```

금지:

```text
모든 연계를 REST 하나로 통일
Application 간 DB 직접 DML
외부 시스템의 내부 DB 직접 접근
P2P 예외를 문서 없이 허용
```

---

# 10.3 자원 분리

자원 분리는 물리 서버만의 문제가 아니다.

```text
Online
Event
Batch
ETL
CDC
BI / Analytics
```

각 Workload는:

```text
CPU
Thread
Connection
DB Session
I/O
Failure
Scaling
```

관점에서 독립성을 검토해야 한다.

---

# 11. Data-Centric 전환

## 11.1 AS-IS

```text
Core
 ↓
Night Batch
 ↓
DW
 ↓
Next-Day Report
```

## 11.2 TO-BE

```text
                   ┌──────── FAST ────────┐
Customer Event ───▶ Event/Kafka ─▶ Action
                   └──────────────────────┘

Core Change ─▶ CDC ─▶ RDW ─▶ ETL ─▶ ADW ─▶ BI
                   └──────── DEEP ────────┘
```

핵심은 FAST와 DEEP의 공존이다.

---

# 12. FAST / DEEP 전략

## 12.1 FAST

목적:

```text
고객행동을 빠르게 감지
        ↓
판단
        ↓
반응
```

특징:

```text
Event Driven
Low Latency
Failure Isolation
Replay/Trace 필요
```

대표:

```text
Customer Event
→ Collector
→ Kafka/Event
→ Marketing Rule/EBM
→ Offer/UMS
```

---

# 12.2 DEEP

목적:

```text
정확성
통합
분석
집계
전략적 의사결정
```

대표:

```text
Core Change
→ CDC
→ RDW
→ ETL
→ ADW
→ BI / Analytics
```

---

# 12.3 FAST와 DEEP의 분리 원칙

```text
FAST 장애
   ─X→ DEEP 전체 중단

DEEP 대량 Query
   ─X→ FAST Online 지연

ETL
   ─X→ Online Worker 점유

Event Burst
   ─X→ OLTP Thread 고갈
```

---

# 13. Architecture Principle

VISION에서 후속 모든 설계에 전달할 원칙을 다음과 같이 정의한다.

| ID | 원칙 | 정의 |
|---|---|---|
| AP-01 | Responsibility First | 제품/서버보다 책임을 먼저 정의 |
| AP-02 | Boundary First | 연결보다 경계를 먼저 정의 |
| AP-03 | Standard Interface Only | 경계 통과는 승인된 표준 Interface |
| AP-04 | Data Role Separation | RDW/ADW, CDC/ETL 책임을 분리 |
| AP-05 | Runtime Isolation | Online/Event/Batch/ETL 자원 경합을 최소화 |
| AP-06 | Independent Scaling | 각 Workload를 독립 확장 가능하게 구성 |
| AP-07 | Failure Isolation | 장애 파급 범위를 Domain/Node 단위로 제한 |
| AP-08 | Security by Design | 인증/인가/암호화/마스킹/Trust Boundary 내재화 |
| AP-09 | Observability by Design | GUID/ServiceId/Metric/Log를 설계에 포함 |
| AP-10 | Evidence First | Runtime Evidence 없이 완료로 보지 않음 |
| AP-11 | Traceable Change | Requirement부터 Runtime까지 추적 |
| AP-12 | Exception Governed | 표준 예외는 ADR/Owner/만료일 관리 |
| AP-13 | Same Artifact Principle | 환경 승격 시 동일 Artifact 유지 지향 |
| AP-14 | Recovery Proven | Backup/DR은 복구 Test까지 증명 |

---

# 14. 상위 금지 패턴

VISION 단계에서 금지할 구조를 먼저 선언한다.

```text
[금지 1]
Channel → Internal DB Direct

[금지 2]
Application A → Application B DAO/DB Direct

[금지 3]
External → Internal DB DML

[금지 4]
Online / Batch / ETL을 동일 Resource로 무제한 혼재

[금지 5]
RDW / ADW를 동일 역할의 DW로 정의

[금지 6]
모든 Integration을 하나의 기술로 통일

[금지 7]
PDMG AS-IS = NSIGHT 전체 TO-BE라고 해석

[금지 8]
수치 Variant 중 임의 하나를 최신값으로 채택

[금지 9]
Monitoring 없이 HA/DR 완료 선언

[금지 10]
Document만 있고 Source/Test/Runtime 검증 없는 Architecture 완료 선언
```

---

# 15. NFR — Non-Functional Requirement Architecture

NSIGHT VISION의 핵심 NFR은 다음 5개 축이다.

```text
Performance
Availability
Scalability
Security
Observability
```

---

# 15.1 Performance

목표:

```text
FAST Runtime의 지연 최소화
+
DEEP Runtime의 처리량 확보
+
상호 자원간섭 최소화
```

Architecture 반영:

```text
Event Path 분리
RDW / ADW 분리
Online / Batch 분리
Thread / Connection / DB Resource Budget
Timeout Budget
```

---

# 15.2 Performance Baseline Conflict

기존 전략자료에는 CDC 관련 서로 다른 Baseline이 존재한다.

예:

```text
CDC 30초 이내
vs
CDC 3초 이내
```

따라서:

```text
[CONFLICT]
```

로 관리하고 본 VISION에서 임의 최종값을 확정하지 않는다.

원칙:

```text
SLA
= 시점 + 업무범위 + 측정구간 + 측정방법 + Owner
```

이 네 조건 없이 숫자만 채택하지 않는다.

---

# 15.3 Availability

목표:

```text
Fault Isolation
AP HA
Data Integrity
DR
Recovery
```

Architecture 반영:

```text
Node / JVM / Host Failure Domain
HA Pair
Center Mapping
DR Pair
Failover
Restore / Reconciliation
```

---

# 15.4 Scalability

목표:

```text
Scale-out
+
Data Parallel Scaling
+
Independent Resource Scaling
```

후속 설계에서는 다음을 분리한다.

```text
WEB Scale
WAS Scale
Worker Scale
DB Connection Scale
Event Consumer Scale
ETL Parallelism
DB/Data Scale
```

---

# 15.5 Security

목표:

```text
Authentication
Authorization
SSO
Encryption
Masking
Trust Boundary
Audit
```

원칙:

```text
Security
≠ 마지막 배포단계에서 추가
```

Security는 Boundary와 Mechanism 단계에서 설계한다.

---

# 15.6 Observability

목표:

```text
GUID
ServiceId
Trace
Metric
Log
APM
Alert
Runbook
Runtime Evidence
```

좋은 운영 질문:

```text
어느 사용자의
어느 ServiceId가
어느 Application/JVM에서
어느 Worker/DB Pool/SQL 때문에
느렸는가?
```

이 질문에 답할 수 있어야 한다.

---

# 16. NFR → Architecture → Runtime Validation Chain

```text
NFR
  ↓
Architecture Decision
  ↓
Logical / Physical / Mechanism Design
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

NFR은 “목표 문구”로 끝나지 않는다.

---

# 17. Hybrid Physical 전략의 VISION 수준 방향

VISION 단계에서는 상세 Host 수를 정하지 않는다.

다만 전략 자료의 방향은 다음처럼 읽힌다.

```text
[Data Dedicated Resource]
RDW / ADW
→ 전용 자원 / 병렬 확장

[Service Resource]
Marketing / BI / Governance
→ Scale-out 가능한 서비스 자원

[Performance Sensitive]
ETL / CDC
→ 독립/전용 실행자원 후보

[HA / DR]
AP
→ 이중화 / 센터 활용

DB
→ 정합성 / 복제 / 운영복잡도 고려
```

정확한 VM/CPU/Memory/Thread/Pool 값은 PHYSICAL에서 별도 Evidence로 확정한다.

---

# 18. Capacity 수치를 읽는 원칙

업로드된 인프라/용량자료에는 여러 Variant가 존재한다.

예:

```text
Session 60분
vs
Session 90분

16C / 128G
vs
32C / 256G

Hikari Candidate 차이
Timeout Layer 차이
```

따라서 VISION에서의 원칙:

```text
Capacity Variant
≠ Target Standard
```

최종값은:

```text
Approved Baseline
+
Actual Config
+
Performance Test
+
Runtime Metric
```

으로 확정한다.

---

# 19. PDMG Reference Position

PDMG를 NSIGHT 전체 아키텍처와 동일시하지 않는다.

```text
┌──────────────────── NSIGHT Target ────────────────────┐
│                                                       │
│ Application                                           │
│ Data Platform                                         │
│ BI                                                    │
│ Governance                                            │
│ Interface                                             │
│ Physical / HA / DR                                    │
│ Operations                                            │
│                                                       │
│         ┌──────── PDMG Reference ────────┐            │
│         │                               │            │
│         │ Application / Framework       │            │
│         │ Online Runtime                │            │
│         │ Transaction / Timeout         │            │
│         │ Message / Error / Log         │            │
│         │ JWT / SSO                     │            │
│         │ ServiceId / Trace             │            │
│         └───────────────────────────────┘            │
└───────────────────────────────────────────────────────┘
```

---

# 19.1 PDMG가 증명할 수 있는 영역

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
Error
Logging
JWT / SSO
Build / Artifact 일부
GUID / Trace
```

---

# 19.2 PDMG가 직접 증명하지 않는 영역

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

이 구분을 문서 전 장에서 유지한다.

---

# 20. AS-IS / TO-BE 관계

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

PDMG Source가 목표와 다르면:

```text
Source = AS-IS
Architecture = TO-BE
Difference = GAP
```

으로 기록한다.

---

# 21. Architecture Decision 체계

VISION Principle은 후속 장에서 실제 Decision으로 변환되어야 한다.

예:

```text
Principle
"Online과 대량처리 자원 분리"
        ↓
Decision
"Online AP와 ETL Execution Node 분리"
        ↓
Physical
별도 Node/Host Group
        ↓
Runtime
Thread / CPU / DB 부하 검증
        ↓
Evidence
Load Test / Metric
```

---

# 21.1 Decision Lifecycle

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

---

# 22. Architecture Closed Loop 방향

최종적으로 Architecture는 다음 Loop를 가져야 한다.

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
   └────────────────────↺
```

Architecture 완료의 정의:

```text
문서 존재
≠ Architecture 완료
```

```text
문서→모델→코드→테스트→Runtime→Evidence→Drift→ADR
가 반복 가능
= Architecture Operationalized
```

---

# 23. Architecture Gate 개념

VISION 수준의 Governance Gate:

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

이 Gate의 상세는 Appendix/Traceability 장에서 확정한다.

---

# 24. 적용 범위

VISION의 대상 범위:

```text
Channel
Application
Data
Integration
Security
Infrastructure
Runtime
Operations
Governance
```

VISION 단계에서는 개별 Server/Port/Class를 정의하지 않는다.

---

# 25. 이해관계자 및 책임

| Stakeholder | VISION 책임 |
|---|---|
| Enterprise Architecture | Vision/Principle/NFR 통합 |
| Application Architect | Domain/Application 책임 Handoff |
| Data Architect | RDW/ADW/Data Subject 방향 |
| Technical Architect | Logical/Physical 실현성 |
| Interface Architect | 경계/표준 연계 |
| Security Architect | Trust/Security Principle |
| Operations Architect | HA/DR/Observability/Recovery |
| DevOps | Standard→Delivery→Evidence |
| 개발팀 | PDMG/Source 구현 Evidence |
| PMO | Scope/Decision/GAP/승인 추적 |

---

# 26. Vision Requirement Inventory

| ID | Requirement | Architecture 의미 | 후속 장 | 상태 |
|---|---|---|---|---|
| VR-01 | 고객행동 신속 대응 | FAST/Event Runtime | BIG/RUNTIME | `[TO-BE]` |
| VR-02 | 데이터 신뢰성 | Data Platform/Governance | BIG | `[TO-BE]` |
| VR-03 | 분석성능 분리 | RDW/ADW/Resource Isolation | BIG/PHYSICAL | `[TO-BE]` |
| VR-04 | 표준 연계 | Boundary + Contract | LOGICAL/MECHANISM | `[TO-BE]` |
| VR-05 | 유연 확장 | Scale-out | PHYSICAL | `[TO-BE]` |
| VR-06 | 장애 영향 최소화 | Failure Isolation | LOGICAL/PHYSICAL | `[TO-BE]` |
| VR-07 | 보안 일관성 | SSO/Auth/Trust | MECHANISM | `[TO-BE]` |
| VR-08 | 거래 추적 | GUID/ServiceId | MECHANISM/RUNTIME | `[TO-BE]` |
| VR-09 | 운영 자동화 | DevOps/OM | RUNTIME | `[TO-BE]` |
| VR-10 | 변경 추적성 | Closed Loop | APPENDIX | `[TO-BE]` |

---

# 27. Architecture Principle → 후속 장 Mapping

| Principle | BIG PICTURE | LOGICAL | PHYSICAL | MECHANISM | RUNTIME |
|---|---:|---:|---:|---:|---:|
| Responsibility First | ● | ● |  |  |  |
| Boundary First | ● | ● |  | ● |  |
| Standard Interface | ● | ● |  | ● | ● |
| Data Role Separation | ● | ● | ● | ● | ● |
| Runtime Isolation |  | ● | ● | ● | ● |
| Independent Scaling |  | ● | ● |  | ● |
| Failure Isolation |  | ● | ● |  | ● |
| Security by Design | ● | ● | ● | ● | ● |
| Observability by Design |  | ● | ● | ● | ● |
| Evidence First |  |  |  | ● | ● |
| Traceable Change | ● | ● | ● | ● | ● |

---

# 28. Vision 상위 정상 패턴

```text
Business Need
  ↓
Architecture Principle
  ↓
Domain Responsibility
  ↓
Standard Boundary
  ↓
Independent Resource
  ↓
Runtime Evidence
```

---

# 29. Vision 상위 Anti-Pattern

## Anti-Pattern A — 제품 우선

```text
"Kafka를 쓰자"
"Exadata를 쓰자"
"API Gateway를 쓰자"
```

부터 시작하는 것.

정상:

```text
Need
→ Responsibility
→ NFR
→ Mechanism
→ Product
```

---

## Anti-Pattern B — PDMG Source를 Target으로 승격

```text
PDMG timeout=5s
→ NSIGHT 전체 SLA=5s

X
```

---

## Anti-Pattern C — 숫자 최신성 추정

```text
60분과 90분 문서가 존재
→ 더 큰 90분이 최신일 것

X
```

---

## Anti-Pattern D — Runtime 없는 Architecture

```text
HA 구성도 있음
→ HA 검증 완료

X
```

정상:

```text
HA 구성
→ Failure Test
→ Failover
→ Business Validation
→ Evidence
```

---

# 30. Security Vision

Security는 별도의 “보안 장”만의 책임이 아니다.

```text
Channel
  ↓ Authentication

Boundary
  ↓ Authorization

Transport
  ↓ Encryption

Application/Data
  ↓ Masking / Least Privilege

Operations
  ↓ Audit / Detection
```

VISION 단계의 Security 질문:

1. Trust Boundary는 어디인가?
2. 인증은 어디서 발생하는가?
3. 권한은 어느 Layer에서 재검증하는가?
4. Data Exposure를 어떻게 제한하는가?
5. Key/Secret은 Code와 분리되는가?
6. Security Event가 운영 Metric으로 보이는가?

---

# 31. Availability Vision

Availability를 단순 “이중화”로 정의하지 않는다.

```text
Availability
=
Redundancy
+
Failure Detection
+
Traffic Switch
+
Data Integrity
+
Recovery
+
Validation
```

후속 장애 시나리오:

```text
WEB Failure
WAS/JVM Failure
Application Group Failure
DB Failure
Event Failure
CDC Failure
Integration Failure
Center Failure
```

---

# 32. Scalability Vision

Scale은 단순 서버 추가가 아니다.

```text
Scale-out
  ↓
Stateless / Shared State Strategy
  ↓
Load Distribution
  ↓
Pool / DB Capacity
  ↓
Observability
```

각 Resource Pool을 별도로 본다.

```text
Tomcat Thread
PDMG Worker
Hikari
DB Session
Kafka Consumer
ETL Process
```

---

# 33. Observability Vision

Observability는 다음 세 계층을 연결해야 한다.

```text
Business
ServiceId / Customer / Function

Application
GUID / Thread / Error / Transaction

Infrastructure
JVM / CPU / Heap / Pool / DB / Network
```

그리고 하나의 Trace로 연결한다.

```text
User
 ↓
ServiceId
 ↓
GUID
 ↓
Application
 ↓
SQL
 ↓
DB
 ↓
Response/Error
```

---

# 34. DevOps / Operations Vision

목표:

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
```

중요 원칙:

```text
Deployment 완료
≠ Architecture 검증 완료
```

배포 후 최소:

```text
Smoke Test
Transaction Trace
Metric
Error Rate
Config Drift
HA State
```

를 확인해야 한다.

---

# 35. Traceability Vision

최종적으로 다음 질문에 답할 수 있어야 한다.

```text
이 Requirement가
어느 Application을 만들었고
어느 ServiceId를 호출하며
어느 Component/SQL/Table을 사용하고
어느 Host/JVM에 배포되고
어떤 GUID/Metric/Error로
Runtime에서 증명되는가?
```

정방향:

```text
Requirement
→ Architecture
→ Application
→ ServiceId
→ Source
→ SQL
→ Runtime
```

역방향:

```text
Table / SQL / Error
→ ServiceId
→ Application
→ Requirement
```

---

# 36. Conflict Register

## CONFLICT-VSN-01 — CDC SLA

자료에 서로 다른 Baseline이 존재한다.

```text
30초
vs
3초
```

처리:

```text
[CONFLICT]
```

필수 의사결정:

```text
대상 Source
대상 Target
측정구간
측정방법
Peak/Normal
Owner
```

---

## CONFLICT-VSN-02 — Capacity / Session

후속 인프라 자료에는:

```text
Session 60분
Session 90분
```

Variant가 존재한다.

VISION에서는 최종값 미확정.

---

## CONFLICT-VSN-03 — Timeout

PDMG AS-IS:

```text
OnlineTimeoutExecutor = 5000ms Snapshot
```

Capacity/Infrastructure Recommendation:

```text
다른 Timeout Layer 값 존재 가능
```

두 값을 동일 SLA로 사용하지 않는다.

---

# 37. GAP Register

| ID | GAP | 영향 | 후속조치 |
|---|---|---|---|
| GAP-VSN-01 | Vision/NFR 최종 승인상태 | 후속 설계 기준 흔들림 | Architecture Review |
| GAP-VSN-02 | CDC SLA Conflict | Data Runtime 설계 | SLA ADR |
| GAP-VSN-03 | PDMG 적용범위 | Target/AS-IS 혼동 | Scope Matrix |
| GAP-VSN-04 | 일부 설계전략 승인상태 | Principle 강제력 | Decision Register |
| GAP-VSN-05 | 최종 Capacity Baseline | Physical 설계 | Capacity Review |
| GAP-VSN-06 | OM 구현 Evidence | Runtime 검증 | Source/Runtime 확인 |
| GAP-VSN-07 | 5대 Domain 세부 Inventory | Big Picture | 1.2에서 작성 |
| GAP-VSN-08 | Data Subject 상세 | Data Ownership | 1.3에서 작성 |

---

# 38. ADR 후보

| ADR | 주제 | 핵심 결정 |
|---|---|---|
| ADR-VSN-01 | CDC SLA | 3초/30초/다단계 SLA 확정 |
| ADR-VSN-02 | FAST/DEEP Boundary | Event/CDC/ETL 책임경계 |
| ADR-VSN-03 | Standard Interface | API/Event/CDC/ETL/File 선택 원칙 |
| ADR-VSN-04 | Resource Isolation | Online/Event/Batch/ETL 분리 수준 |
| ADR-VSN-05 | PDMG Alignment | 어떤 AS-IS를 TO-BE 표준으로 승격할지 |
| ADR-VSN-06 | Observability | GUID/ServiceId E2E 기준 |
| ADR-VSN-07 | HA/DR | Application/Data/Center 복구 수준 |
| ADR-VSN-08 | Capacity Baseline | 최종 Session/Thread/Pool 기준 |

---

# 39. Verification Checklist

## 39.1 PPT 정합성

```text
[ ] 1.1 개요 범위를 벗어나 세부 Host/Class를 확정하지 않았는가
[ ] 개편 기본 방향이 PPT 핵심축과 정합되는가
[ ] 구축 방향 및 목표가 후속 장에 연결되는가
[ ] 1.2~1.4를 BIG PICTURE Handoff로 남겼는가
```

## 39.2 Evidence

```text
[ ] FACT / AS-IS / TO-BE / PROPOSED를 구분했는가
[ ] PDMG를 NSIGHT 전체 Target으로 확대하지 않았는가
[ ] 수치 충돌을 임의 해소하지 않았는가
[ ] 근거 없는 제품/Host/SLA를 생성하지 않았는가
```

## 39.3 Architecture

```text
[ ] Vision이 기술제품부터 시작하지 않는가
[ ] 5대 NFR이 명시되는가
[ ] 6단계 방법론이 연결되는가
[ ] 5대 Domain Handoff가 있는가
[ ] 규격/인터페이스/자원분리 3대 통제가 보이는가
[ ] FAST/DEEP 전략이 구분되는가
[ ] Security/HA/Scalability/Observability가 Vision에 내재화되는가
```

## 39.4 Governance

```text
[ ] Principle→Decision→Evidence Chain이 있는가
[ ] GAP / Conflict / ADR가 남아 있는가
[ ] Architecture Closed Loop 방향이 있는가
```

---

# 40. Completion Gate

VISION 장의 완료조건:

```text
G-VSN-01  개편 배경 확인
G-VSN-02  Vision Statement 확인
G-VSN-03  구축 방향 확인
G-VSN-04  Architecture Principle 확인
G-VSN-05  NFR 5축 확인
G-VSN-06  FAST/DEEP 전략 확인
G-VSN-07  PDMG Reference 범위 확인
G-VSN-08  Conflict/GAP 확인
G-VSN-09  BIG PICTURE Handoff 확인
```

다음 항목이 미결이면 VISION은 `Draft`로 유지한다.

```text
NFR 승인
CDC SLA
PDMG Target Alignment
최종 Capacity Baseline
Architecture Principle 승인
```

---

# 41. BIG PICTURE Handoff

VISION Output:

```text
1. 개편 기본 방향
2. Architecture Vision
3. 5대 NFR
4. Architecture Principle
5. FAST / DEEP 전략
6. 5대 Service Domain 상위 책임
7. 규격/Interface/자원분리 3대 통제
8. Security/HA/Observability 방향
9. PDMG Reference Position
10. GAP / ADR
```

BIG PICTURE에서 구체화할 것:

```text
1.2 어플리케이션 분류 체계
  ├─ 개념 아키텍처
  ├─ 5대 Application Domain
  ├─ Application Classification
  ├─ Application Inventory
  ├─ System Group
  └─ Application ↔ System Mapping

1.3 데이터 주제영역 정의
  ├─ Subject Area
  ├─ RDW/ADW
  ├─ Source/Consumer
  └─ Data Ownership Inventory

1.4 시스템 아키텍처 구성
  ├─ 전체 시스템 구조
  ├─ Channel / External Boundary
  └─ 주요 Logical Server Candidate
```

---

# 42. Vision → Big Picture 최종 연결

```text
[WHY]
왜 개편하는가
        │
        ▼
[WHAT VALUE]
어떤 가치를 제공하는가
        │
        ▼
[PRINCIPLE]
어떤 원칙을 지키는가
        │
        ▼
[NFR]
어떤 품질을 보장하는가
        │
        ▼
[BIG PICTURE]
그 책임을
Application / System / Data 공간에
어떻게 배치할 것인가
```

---

# 43. 최종 평가

본 VISION 정의서는 후속 아키텍처가 따라야 할 최상위 계약을 다음과 같이 정의한다.

```text
NSIGHT
=
Data-Centric Platform
+
Real-time Reaction
+
Strategic Analytics
+
Responsibility Boundary
+
Standard Interface
+
Resource Isolation
+
Security
+
Observability
+
Traceability
+
Runtime Evidence
```

가장 중요한 원칙은 다음이다.

> **NSIGHT 아키텍처는 그림이나 제품 목록으로 완료되는 것이 아니라, Vision과 NFR이 Big Picture·Logical·Physical·Mechanism·Runtime으로 구체화되고, 그 결과가 Source·Config·Test·Runtime Evidence로 검증될 때 비로소 완료된다.**

---

# Appendix A. VISION 핵심 용어

| 용어 | 정의 |
|---|---|
| Data-Centric | 데이터가 시스템 부속물이 아니라 서비스/의사결정의 중심 자산이 되는 구조 |
| FAST | Event 중심 저지연 반응 경로 |
| DEEP | CDC/ETL/RDW/ADW 중심 통합·분석 경로 |
| Boundary | 책임·보안·장애·연계가 통제되는 경계 |
| NFR | 성능/가용성/확장성/보안/관측성 등 구조를 강제하는 품질 요구 |
| PDMG Reference | NSIGHT 하위 Application/Framework 영역을 검증하는 AS-IS 구현근거 |
| Evidence | Source/Config/Test/Runtime에서 Architecture 준수 여부를 확인할 수 있는 증거 |
| Drift | 문서/모델/코드/설정/Runtime 간 불일치 |
| ADR | 중요한 Architecture 결정을 승인·기록하는 문서 |
| Closed Loop | Architecture가 Runtime Evidence와 Drift를 통해 지속 갱신되는 관리체계 |

---

# Appendix B. VISION 핵심 질문 20선

1. NSIGHT는 기존 정보계와 무엇이 구조적으로 다른가?
2. 실시간은 어떤 업무에 필요한가?
3. Batch는 무엇을 계속 담당해야 하는가?
4. RDW와 ADW는 왜 분리하는가?
5. FAST와 DEEP는 어떤 장애경계를 가지는가?
6. 5대 Domain의 상위 책임은 무엇인가?
7. Application과 Data Ownership은 어떻게 나뉘는가?
8. 어떤 Interface를 표준으로 인정할 것인가?
9. Direct DB 접근 예외는 누가 승인하는가?
10. Online/Event/Batch/ETL 자원을 어디까지 분리할 것인가?
11. 성능 SLA는 어떤 구간을 측정하는가?
12. Availability는 어떤 Failure Scenario로 검증하는가?
13. Scale-out이 가능한 단위는 무엇인가?
14. Security Trust Boundary는 어디인가?
15. GUID와 ServiceId는 어디까지 전달되는가?
16. PDMG의 어느 구현을 Target으로 승격할 것인가?
17. Source와 PPT가 다르면 무엇을 AS-IS로 볼 것인가?
18. Runtime Evidence가 없는 설계는 승인 가능한가?
19. GAP는 어떤 경우 ADR이 필요한가?
20. BIG PICTURE에서 무엇을 반드시 확정해야 하는가?

---

# Appendix C. 본 장에서 후속 장으로 넘기지 않는 세부값

VISION에서 다음을 임의 확정하지 않는다.

```text
정확한 Hostname
서버 대수
CPU / Memory
Tomcat maxThreads
Hikari Pool
PDMG Worker 최종값
Session 최종시간
Gateway Timeout
DB Query Timeout
RTO / RPO
CDC 최종 SLA
Kafka Partition
ETL 병렬도
제품 Version
포트
URL
```

이 값은 해당 Architecture Level의 Evidence로 확정한다.
