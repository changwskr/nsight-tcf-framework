# NSIGHT / PDMG 아키텍처 정의서 — I. Architecture Vision & Strategy

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 대상: NSIGHT Target Architecture + PDMG Reference/AS-IS  
> 문서 상태: **Draft / Evidence-First**  
> 작성일: 2026-08-31

---

# 0. Evidence Register

| ID | 근거 자료 | 시점/버전 | 본 장 사용 목적 | 상태 |
|---|---|---|---|---|
| EV-I-01 | `NH_N-SIGHT_아키텍처_발표자료-20260329 수정본.pptx` | 2026-03-25~03-29 | 전략적 배경, 5대 NFR, 6단계 방법론, Big Picture, FAST/DEEP | `[FACT]` |
| EV-I-02 | `NSIGHT_아키텍처_설계전략_금지패턴.pptx` | 2026-03-27 | Boundary, Interface, 자원분리, Hybrid/DR 전략 | `[FACT-SOURCE]` / 승인상태 미확인 |
| EV-I-03 | `차세대_정보계_사전분석_계획서_인프라_아키텍처_추가6_수정.pptx` | 2025-12-18 | AS-IS 이슈, Big Picture 도출 방향, 분석 범위 | `[FACT]` |
| EV-I-04 | `2026-03-08-NH_아키텍처전략_정리본_(최종본)_V1.0.docx` | 2026-03-08 | 비전/NFR 세부 교차검증 | `[FACT]` |
| EV-I-05 | `NSIGHT_PDMG_아키텍처_인포그래픽_이미지화_마스터_프롬프트.md` | 2026-08-31 | NSIGHT↔PDMG 레벨 분리, 10장 정의 구조 | `[FACT-WORKING-BASELINE]` |
| EV-I-06 | `00.BigPicture Tx 처리-1.md` | 2026-08 | PDMG Runtime Reference 위치 확인 | `[AS-IS EVIDENCE]` |
| EV-I-07 | `24장.JWT 인증 전체 구조.md` | 2026-08 | PDMG JWT/SSO Reference 위치 확인 | `[AS-IS EVIDENCE]` |

> **주의:** 본 장은 NSIGHT Strategy가 중심이다. PDMG 클래스·메서드·설정값은 I장에서 설계의 출발점이 아니라 **Reference Evidence**로만 사용한다.

---

# 1. Figure Plan

| FIG | 제목 | Level | 목적 | 필수 |
|---|---|---:|---|---|
| FIG-I-01 | NSIGHT Strategy Journey | L0 | 전체 전략의 Top-down 경로 | Y |
| FIG-I-02 | AS-IS Problem → Architecture Vision | L0 | 왜 전환하는가 | Y |
| FIG-I-03 | Data-Centric Platform Transformation | L1 | Batch 중심에서 Real-time 병행으로 | Y |
| FIG-I-04 | 5대 Service Domain Responsibility | L1 | 책임과 경계 | Y |
| FIG-I-05 | Vision → Big Picture → Logical → Physical → Mechanism → Runtime | L1~L2 | 방법론·설계 깊이 | Y |
| FIG-I-06 | 5대 NFR Architecture Map | L1 | 성능/가용성/확장성/보안/관측성 | Y |
| FIG-I-07 | Architecture Principle → Decision → Evidence | L2~L5 | 의사결정/검증 관계 | Y |
| FIG-I-08 | NSIGHT Target ↔ PDMG Reference Position | L2~L3 | PDMG의 정확한 위치 | Y |
| FIG-I-09 | FAST vs DEEP Strategic Runtime | L2~L3 | 이중 트랙 전략 | Y |
| FIG-I-10 | Conflict / GAP / ADR Handoff | L5 | 다음 장으로 넘길 결정 항목 | Y |

---

# 2. 핵심 결론

NSIGHT의 핵심은 기존 정보계를 단순히 더 큰 DW나 더 빠른 서버로 교체하는 것이 아니다. 목표는 **배치 중심 데이터 창고를 실시간 반응·전략 분석·운영 추적이 함께 가능한 Data-Centric 플랫폼으로 전환**하는 것이다.

`[FACT]` 2026-03 아키텍처 전략 자료는 기존 정보계의 문제를 **익일 배치 제공, 정해진 시간의 마케팅 오퍼링, 단순 DW 창고 역할, 서버별 독립 운영, 분석 쿼리에 의한 성능 저하**로 정의하고, 목표를 **CDC 기반 Near Real-time, Kafka 기반 반응형 오퍼링, 실시간 의사결정, 도메인/장애 격리, RDW·ADW 자원분리**로 제시한다.

`[FACT]` NSIGHT 방법론의 중심은 다음 6단계다.

```text
Vision
  → Big Picture
  → Logical
  → Physical
  → Mechanism
  → Runtime
```

이는 단순 산출물 순서가 아니라 **비전과 NFR을 공간·책임·정책·인프라·실행규칙으로 구체화한 뒤 Runtime에서 검증하는 설계 계약**이다.

`[FACT]` Big Picture는 다섯 개 서비스 도메인과 세 가지 핵심 통제 축을 제시한다.

```text
5대 서비스 도메인
- 데이터플랫폼
- 마케팅플랫폼
- BI 포탈
- 데이터 거버넌스
- IT 서비스 및 업무지원

3대 통제
- 규격 표준
- 인터페이스 통제
- 자원 분리
```

`[AS-IS]` PDMG는 이 전체 NSIGHT의 대체물이 아니라, Framework/Application/Runtime 영역을 실제 Source로 검증할 수 있는 Reference다. 따라서 PDMG의 `DefaultFilter → OnlineTimeoutExecutor → Dispatcher → Handler → Facade → Service → DAO` 같은 구조는 I장의 시작점이 아니라 **하위 구현 증거**다.

---

# 3. 목적 / 범위 / 전제

## 3.1 목적

이 장은 다음 질문에 답한다.

1. 왜 기존 정보계 패러다임을 바꾸는가?
2. NSIGHT가 지향하는 비전은 무엇인가?
3. 어떤 NFR이 아키텍처를 강제하는가?
4. 어떤 방법론으로 설계를 구체화하는가?
5. 어떤 경계·연계·자원분리 원칙을 따른는가?
6. PDMG는 전체 구조에서 어디에 위치하는가?

## 3.2 범위

포함:

```text
전략적 배경
아키텍처 비전
5대 NFR
6단계 방법론
5대 도메인 책임
핵심 설계전략
FAST / DEEP 전략
PDMG Reference 위치
GAP / Conflict / ADR
```

제외(후속 장):

```text
상세 System Boundary             → II장
PDMG 5개 모듈/Build/Spring 경계 → III장
HTTP/TCF Runtime                → IV장
Thread/Timeout/DB TX            → V장
전문/Context/Error/Log          → VI장
JWT/SSO                         → VII장
물리 Capacity/HA/DR 상세        → VIII장
DevOps/OM                       → IX장
ServiceId/Traceability          → X장
```

## 3.3 전제

- 발표자료의 수치는 해당 문서 시점의 `[BASELINE]`으로 관리한다.
- 같은 항목의 수치가 다른 자료에서 충돌하면 최신값이라고 임의 선택하지 않는다.
- `설계전략 및 금지패턴` 문서의 원칙은 문서에 명시된 사실은 `[FACT-SOURCE]`이나, 최종 승인 여부는 별도 `[OPEN]`으로 둔다.
- PDMG의 구현은 NSIGHT 목표 아키텍처와의 정합성을 검증하기 위한 Reference다.

---

# 4. FIG-I-01 — NSIGHT 전체 Strategy Journey

```text
[AS-IS 정보계]
배치 중심 · DW 창고 · 서버별 독립 · 분석부하 전이
        │
        ▼
[Architecture Vision]
Scalable · Resilient · Data-Centric
        │
        ▼
[5대 NFR]
Performance / Availability / Scalability / Security / Observability
        │
        ▼
[Big Picture]
책임은 공간에 고정하고 연결은 경계에서 통제
        │
        ▼
[Logical Policy]
Boundary / Standard IF / 자원분리 / 기능서버 독립
        │
        ▼
[Hybrid Physical]
전용 Data Resource + Private Cloud Service + Performance Bare Metal
        │
        ▼
[HA / DR]
AP 확장·이중화 + 현실적 DB 운영 복잡도 통제
        │
        ▼
[Mechanism]
HTTP/JSON · GUID · API · Kafka · CDC · ETL · File · TCF
        │
        ▼
[Runtime]
FAST : Event → Reaction
DEEP : Change → RDW → ETL → ADW → BI
        │
        ▼
[Operations / DevOps]
Standard · Monitoring · Runtime Evidence · Drift
        │
        ▼
[PDMG Reference]
Source / Config / Runtime로 Application Mechanism 검증
```

### 그림 해설

- **시작점:** 기술제품이 아니라 현행 정보계의 구조적 한계다.
- **핵심:** NSIGHT는 `Data → Reaction → Decision`을 끊김 없이 제공하는 플랫폼을 목표로 한다.
- **PDMG 위치:** 전체 전략의 맨 아래 Application/Framework Reference 영역이다.
- **종료점:** 전략이 구현·Runtime Evidence로 검증되고 다시 Baseline에 반영된다.

---

# 5. I.1 전략적 배경 — 창고에서 살아있는 플랫폼으로

## 5.1 AS-IS 구조적 문제 `[FACT]`

2026-03 NSIGHT 전략자료는 기존 정보계의 핵심 문제를 다음과 같이 정리한다.

| AS-IS 문제 | 구조적 영향 |
|---|---|
| 야간 배치 후 익일 데이터 제공 | 고객행동에 즉시 반응할 수 없음 |
| 정해진 시간의 마케팅 오퍼링 | Event 기반 실시간 반응 불가 |
| 단순 DW/창고 중심 | 분석·판단·행동 연결 단절 |
| 서버별 독립 운영 | 장애 파급과 운영복잡도 증가 |
| 분석 쿼리의 시스템 성능 저해 | 실시간 거래와 분석 자원 경합 |
| 개발환경 표준화 부족 | 유지보수 품질과 변경속도 저하 |
| 다양한 데이터 송수신 기준 부족 | 연계 복잡도·비표준 경로 증가 |
| App/IF/Batch 모니터링 부족 | 장애 원인 추적 어려움 |
| 데이터 중복·분산 | 데이터 신뢰성과 관리비용 저하 |

## 5.2 FIG-I-02 — Business Need → Architecture Vision

```text
[Business / Operation Pain]
  익일 데이터
  고정 배치
  장애 파급
  분석 성능 저하
  비표준 연계
  분산된 데이터
        │
        ▼
[Architecture Change Drivers]
  Real-time
  Fault Isolation
  Data Integration
  Standard Interface
  Independent Scaling
  Observability
        │
        ▼
[NSIGHT Vision]
"끊김 없는 데이터 관리를 통해
 고객 행동에 즉시 반응하는
 실시간 경영 기반의 시스템"
        │
        ▼
[Business Value]
Data Platform = 신뢰
Marketing     = 실행
BI            = 판단
```

## 5.3 전략적 전환 선언

`[FACT]` 전환의 중심은 **Batch 제거**가 아니라 **Batch 중심 → Real-time 병행(Coexistence)** 이다.

```text
AS-IS
Batch → DW → Next Day Report

TO-BE
Real-time Event / CDC  +  Existing/Strategic Batch
          │
          ├─ 즉시 반응
          └─ 정확한 전략 분석
```

따라서 실시간 경로와 분석 경로는 공존하되 자원과 장애영향은 분리되어야 한다.

---

# 6. I.2 Architecture Vision & 5대 NFR

## 6.1 비전

```text
Scalable
+ Resilient
+ Data-Centric
      │
      ▼
실시간 반응과 전략 분석을 동시에 제공하는
차세대 정보계 플랫폼
```

## 6.2 FIG-I-06 — 5대 NFR Architecture Map

```text
                    [NSIGHT Vision]
                          │
     ┌────────────┬───────┼────────┬──────────────┐
     ▼            ▼       ▼        ▼              ▼
Performance  Availability Scalability Security Observability
     │            │       │        │              │
 FAST/DEEP     장애격리   Scale    SSO/암호화     GUID/Trace
 RDW/ADW       DR/AP HA   Out/In   마스킹         APM/Log
 Event SLA     도메인독립 Hybrid   망분리         Alert
```

## 6.3 NFR Baseline 표

| NFR | 자료에 명시된 목표 | 상태 | Architecture Implication |
|---|---|---|---|
| Performance | 마케팅 오퍼링 1초 이내 | `[BASELINE-2026-03]` | Kafka/Event 기반 FAST 경로 |
| Performance | CDC 데이터 통합 30초 이내 | `[BASELINE-2026-03-08/25]` | CDC 중계→RDW |
| Performance | CDC 3초 이내 | `[BASELINE-2026-03-29 Runtime]` | **[CONFLICT] 30초와 불일치** |
| Availability | DR 활용 AP 레벨 Active-Active | `[BASELINE-2026-03]` | AP 단위 이중화/센터 분산 |
| Availability | RTO 30분 이내 | `[BASELINE-2026-03]` | DR 운영·전환 절차 필요 |
| Scalability | 서비스 VM Scale-Out | `[BASELINE-2026-03]` | 마케팅/BI/서비스 수평확장 |
| Scalability | RDW·ADW 병렬/랙 확장 | `[BASELINE-2026-03]` | Exadata 전용 데이터 자원 |
| Security | 망분리·SSO·구간암호화·마스킹 | `[BASELINE-2026-03]` | Security by Design |
| Observability | APM, GUID/Trace-ID, 통합로그·알림 | `[BASELINE-2026-03]` | End-to-End 거래 추적 |

### NFR 해석

NFR은 품질 목록이 아니라 **구조를 강제하는 설계 입력**이다.

```text
1초 반응
  → Event Path 분리

분석 부하 격리
  → RDW / ADW 물리분리

Scale-Out
  → 서비스 VM/Cloud

RTO / HA
  → AP 이중화 + DR 운영

Observability
  → GUID/ServiceId/Log/Metric 연계
```

---

# 7. I.3 6단계 Architecture 방법론

## 7.1 FIG-I-05 — Architecture Definition Ladder

```text
① Vision
   비전·NFR 수립
      │
      ▼
② Big Picture
   도메인·Zone·경계 정의
      │
      ▼
③ Logical
   정책·책임·허용/금지 정의
      │
      ▼
④ Physical
   서버·DB·Network·HA 배치
      │
      ▼
⑤ Mechanism
   실행규칙·표준전문·연계·Framework 정의
      │
      ▼
⑥ Runtime
   실제 거래·이벤트·배치·장애 시나리오 검증
```

## 7.2 각 단계의 완료조건

| 단계 | 핵심 질문 | 완료조건 |
|---|---|---|
| Vision | 무엇을 바꿔야 하는가 | 비전/NFR/우선순위 합의 |
| Big Picture | 책임은 어디에 있는가 | 도메인/Zone/Boundary 확정 |
| Logical | 무엇을 허용/금지하는가 | Policy/Responsibility/Contract |
| Physical | 어디에 어떻게 배치하는가 | Node/SW/DB/Network/HA 매핑 |
| Mechanism | 실제로 어떻게 실행하는가 | IF/TCF/Security/Data/Timeout 규칙 |
| Runtime | 정말 동작하는가 | 정상/장애/성능/보안 시나리오 검증 |

## 7.3 방법론의 핵심 해석

```text
Logical + Physical
        ↓
Mechanism
        ↓
Runtime Verification
```

즉 메커니즘은 논리·물리 설계의 실행 규칙이며, Runtime은 그 규칙의 검증 단계다.

---

# 8. I.4 책임과 경계 중심의 핵심 전략

## 8.1 FIG-I-04 — 5대 Service Domain Responsibility

```text
┌────────────────────────────────────────────────────┐
│                NSIGHT Service Space                │
│                                                    │
│  [Data Platform]                                   │
│   RDW / ADW / Data Integration                     │
│   책임: 신뢰 가능한 데이터 기반                    │
│                                                    │
│  [Marketing Platform]                              │
│   Event / Rule / Offering                          │
│   책임: 고객행동 기반 즉시 실행                    │
│                                                    │
│  [BI Portal]                                       │
│   Analysis / Decision                              │
│   책임: 의사결정 공간                              │
│                                                    │
│  [Data Governance]                                 │
│   Metadata / Quality / Standard / Model            │
│   책임: 데이터 일관성과 통제                       │
│                                                    │
│  [IT Service & Business Support]                   │
│   SSO / Batch / Operations / Architecture Support  │
│   책임: 공통 운영·지원                             │
└────────────────────────────────────────────────────┘
```

핵심 선언:

> **책임은 공간에 고정하고, 연결은 경계를 통제한다.**

## 8.2 검증된 설계전략/원칙

아래는 `아키텍처 설계전략 및 금지패턴` 문서에서 확인된 항목이다. 문서에 명시된 사실은 확정되지만 최종 승인 상태는 별도 확인이 필요하므로 본 정의서에서는 `[PROPOSED/STRATEGY]`로 관리한다.

| ID | 전략 | 핵심 선언 | 상태 |
|---|---|---|---|
| P-01 | Boundary First | Zone/시스템 경계를 먼저 정의 | `[PROPOSED/STRATEGY]` |
| P-02 | Standard Interface Only | 통제된 표준 경로만 허용, P2P/DB Link 금지 | `[PROPOSED/STRATEGY]` |
| P-03 | Real-time/Batch 자원·경로 분리 | RDW·ADW 및 온라인/배치 혼용 금지 | `[PROPOSED/STRATEGY]` |
| P-04 | 독립 기능 서버 | 온라인/배치/ETL/Event/CDC 기능자원 분리 | `[PROPOSED/STRATEGY]` |
| P-06 | 전용자원 수직/병렬 확장 | RDW·ADW 전용자원 확장 | `[PROPOSED/STRATEGY]` |
| P-07 | 서비스 수평 확장 | Private Cloud VM Scale-Out | `[PROPOSED/STRATEGY]` |
| P-08 | Data Resource Appliance | RDW·ADW 전용 Appliance | `[PROPOSED/STRATEGY]` |
| P-09 | Service Resource Private Cloud | 마케팅/BI/Governance VM | `[PROPOSED/STRATEGY]` |
| P-10 | Performance Sensitive Dedicated | ETL Bare Metal, CDC Unix 등 | `[PROPOSED/STRATEGY]` |
| P-11 | Hybrid Trade-off | All Bare Metal / All VM 극단 회피 | `[PROPOSED/STRATEGY]` |
| P-12 | 현실적 HA/DR | AP 중심 확장·센터활용, DB 동기화 복잡도 통제 | `[PROPOSED/STRATEGY]` |

> **[GAP]** 현재 확보된 슬라이드 조각에는 `P-05`가 나타나지 않는다. 번호를 추정하여 보완하지 않는다.

---

# 9. I.5 Data-Centric 전환과 FAST / DEEP 전략

## 9.1 FIG-I-03 — Data-Centric Platform Transformation

```text
AS-IS
Core / Source
   │
   ▼
Night Batch
   │
   ▼
DW
   │
   ▼
Next-Day Report

                ↓ Transition ↓

TO-BE
                 ┌──────────── FAST ────────────┐
Customer Action ─► Kafka ─► Marketing Rule ─► Reaction
                 │    DB 비의존 / 즉시 반응     │
                 └───────────────────────────────┘

Core Change ─► CDC ─► RDW ─► ETL/DataStage ─► ADW ─► BI
                 └──────────── DEEP ─────────────┘
```

## 9.2 FIG-I-09 — FAST vs DEEP Strategic Runtime

```text
FAST
고객 행동
  → Kafka
  → Marketing Rule Engine
  → Offering / Reaction

목표: 즉각 반응
특성: Event-Driven / DB 비의존 처리 지향

──────────────── Resource / Failure Isolation ────────────────

DEEP
계정계 원천
  → CDC 중계
  → RDW
  → DataStage ETL
  → ADW
  → BI / Strategic Analysis

목표: 정확성·분석·대량처리
특성: Change Capture + Batch/ETL
```

### 핵심 정책

- FAST 장애가 DEEP에 전이되지 않아야 한다.
- DEEP 대량 분석부하가 FAST 거래를 저해하지 않아야 한다.
- 실시간과 분석을 같은 DB/Thread/Server 경로에 무리하게 통합하지 않는다.

---

# 10. I.6 Hybrid Physical / HA·DR 전략의 방향

I장은 상세 물리구성을 확정하지 않지만 전략 수준의 배치원칙은 다음과 같이 읽힌다.

```text
[Data Dedicated]
RDW / ADW
  → 전용 Appliance / 병렬확장

[Flexible Service]
Marketing / BI / Governance / Online AP
  → NH Private Cloud VM / Scale-Out

[Performance Sensitive]
ETL / CDC 등
  → Dedicated / Bare Metal / Unix 후보

[HA / DR]
AP
  → 이중화 / 센터 분산 / Active-Active 전략
DB
  → 운영복잡도·정합성 Trade-off를 고려한 별도 정책
```

`[OPEN]` 상세 서버 수량, JVM, Thread, Hikari, DR RPO/RTO, 세션 방식은 VIII장에서 최신 인벤토리와 함께 확정한다.

---

# 11. I.7 Standard Interface / Control 전략

## 11.1 Big Picture의 3대 통제

```text
[규격 표준]
HTTP/JSON
Header + Data
GUID 기반 추적

[인터페이스 통제]
API / Kafka / CDC / ETL / File 등 목적별 표준 경로
P2P / DB Link 직접연계 금지 전략

[자원 분리]
RDW / ADW
Online / Batch
ETL / Event / CDC 기능서버
```

## 11.2 목적별 연계전략

자료에 나타난 연계 방식은 다음과 같이 목적이 다르다.

```text
Transaction / Service → API / MCA 계열
Event                 → Kafka
Data Change           → CDC
Bulk / Analytics      → ETL
File                  → FOS / MFT 계열
```

`[OPEN]` 표준 인터페이스 문서별 제품명·SLA·Gate 표현이 시점별로 다르므로 II/VI/IX장에서 최종 Standard Baseline을 재검증한다.

---

# 12. I.8 NSIGHT Target과 PDMG Reference의 관계

## 12.1 FIG-I-08 — Target ↔ Reference Position

```text
┌──────────────── NSIGHT Target Architecture ────────────────┐
│ Vision / NFR                                               │
│ Big Picture / Boundary                                     │
│ Logical Policy                                             │
│ Physical / HA-DR                                           │
│ Interface / Data / Security Mechanism                      │
│ Runtime Scenario                                           │
└──────────────────────────────┬──────────────────────────────┘
                               │ 검증 / 투영
                               ▼
┌──────────────── PDMG Reference / AS-IS ────────────────────┐
│ pdmg-ui                                                     │
│ pdmg-jwt                                                    │
│ pdmg-fw                                                     │
│ pdmg-service                                                │
│ pdmg-om                                                     │
│                                                            │
│ Source / Config / Runtime Evidence                          │
└──────────────────────────────┬──────────────────────────────┘
                               │
                               ▼
                     GAP / ADR / TO-BE
```

## 12.2 PDMG가 I장에서 증명하는 것

`[AS-IS]` 현재 PDMG Runtime 분석자료에는 다음 실행 구조가 확인된다.

```text
HTTP Request
  → DefaultFilter
  → ServicePreventionInterceptor
  → OnlineTransactionController
  → TcfFacade
  → OnlineTimeoutExecutor
  → Worker Thread / TransactionTemplate
  → TransactionDispatcher
  → TransactionHandler
  → Facade
  → Service
  → DAO / Mapper / DB
```

이 구조는 NSIGHT 전체 비전이 아니라 다음을 검증하는 Source Evidence다.

```text
Boundary가 실제 코드에 존재하는가?
Framework와 Business가 분리되는가?
Timeout/TX가 Runtime에서 통제되는가?
ServiceId가 실행·로그와 연결되는가?
JWT/SSO가 Trust Boundary를 지키는가?
```

`[AS-IS]` JWT 자료에서는 RS256 발급, Refresh Token 저장/회전 관련 구현과 내부 SSO 연계 HMAC/IP 검증 구조가 확인되지만, 업무 요청 검증의 AS-IS와 TO-BE JWKS 구조 사이에 연결 GAP가 존재한다. 상세는 VII장에서 다룬다.

---

# 13. FIG-I-07 — Architecture Decision / Evidence Closed Loop

```text
Business / NFR
     │
     ▼
Architecture Principle
     │
     ▼
Decision / ADR
     │
     ▼
Logical / Physical Design
     │
     ▼
Mechanism / Standard
     │
     ▼
PDMG Source / Config
     │
     ▼
Test / Runtime Evidence
     │
     ├─ 일치 ─► Baseline 유지
     │
     └─ 불일치 ─► GAP / Risk / Drift
                    │
                    ▼
                  ADR
                    │
                    └──────────────↺ Architecture Update
```

NSIGHT의 목표는 문서와 구현을 별도로 관리하는 것이 아니라 **문서→모델→코드→테스트→Runtime→Drift→새 Baseline**으로 닫는 것이다.

---

# 14. 구성요소 / 책임 표

| 영역 | 책임 | 본 장 상태 | 후속 장 |
|---|---|---|---|
| Architecture Vision | 전환 방향과 가치 | `[FACT]` | 전 장 공통 |
| NFR | 품질·SLA 설계 입력 | `[FACT + CONFLICT]` | VIII/IX |
| Big Picture | 도메인/경계 | `[FACT]` | II |
| Logical Policy | 허용/금지/책임 | `[PROPOSED/STRATEGY]` | II/VI |
| Physical Strategy | Hybrid 자원배치 | `[PROPOSED/STRATEGY]` | VIII |
| Runtime | FAST/DEEP | `[FACT]` | II/VIII |
| Application Mechanism | TCF/PDMG | `[AS-IS Reference]` | III~VII |
| Operations | Monitoring/Trace/DevOps | 전략수준 | IX |
| Traceability | ServiceId/Evidence/Gate | 전략수준 | X |

---

# 15. 설계 규칙 / 금지 / 예외

## 15.1 이 장에서 채택할 상위 규칙

1. **Strategy First** — TCF 클래스보다 Architecture Vision을 먼저 설명한다.
2. **Boundary First** — 기능 개발보다 시스템/도메인 경계를 먼저 확정한다.
3. **Purpose-Based Integration** — 연계 목적에 따라 API/Event/CDC/ETL/File을 분리한다.
4. **FAST / DEEP Separation** — 실시간 반응과 분석경로를 자원·장애 관점에서 격리한다.
5. **Hybrid by Workload** — 모든 자원을 동일 인프라 유형으로 통일하지 않는다.
6. **Evidence First** — Source/Config/Runtime과 충돌하는 문서는 자동 Baseline이 아니다.
7. **No Silent Assumption** — 누락된 수치·제품·정책은 `[UNKNOWN]`으로 유지한다.

## 15.2 금지 패턴

```text
Channel → 타 시스템 DB 직접 DML
P2P / DB Link를 표준 경계로 사용
Online과 대량 Batch를 같은 자원에 무분별하게 혼용
RDW와 ADW 역할 혼용
PDMG AS-IS를 NSIGHT TO-BE로 자동 승격
설계문서에 없는 Timeout / Pool / RTO / Server 수량 생성
```

---

# 16. AS-IS vs TO-BE / 변경 영향

| 관점 | AS-IS 문제/참조 | TO-BE 방향 | 검증 필요 |
|---|---|---|---|
| 데이터 제공 | Batch/익일 중심 | CDC/Event + Batch 병행 | 실제 SLA |
| 데이터 저장 | 분석/실시간 자원 경합 | RDW/ADW 분리 | 물리 배치 |
| 마케팅 | 정해진 시간 실행 | Event 기반 반응 | Rule/Event Platform |
| 시스템 경계 | 서버별 독립/비표준 연계 | Domain/Zone + 표준 IF | II장 |
| 확장 | 서버별 개별 증설 | Data 전용 확장 + Service Scale-Out | VIII장 |
| 장애 | 장애 파급 | 기능/도메인 격리 | HA/DR |
| 관측 | App/IF/Batch 통합 추적 부족 | GUID/Trace/APM/Log | IX장 |
| Framework | 기존/Reference 혼재 | 표준 Mechanism + Runtime 검증 | III~VII |

---

# 17. 확정 / Conflict / GAP / OPEN / ADR

## 17.1 확정 가능한 핵심

- `[FACT]` NSIGHT 비전: Scalable · Resilient · Data-Centric.
- `[FACT]` 6단계 방법론: Vision → Big Picture → Logical → Physical → Mechanism → Runtime.
- `[FACT]` 5대 NFR: 성능 / 가용성 / 확장성 / 보안 / 관측성.
- `[FACT]` 5대 서비스 도메인과 규격/인터페이스/자원분리 통제 개념.
- `[FACT]` FAST/DEEP 이중 트랙 전략과 RDW/ADW 분리 방향.
- `[AS-IS]` PDMG는 Source/Runtime Evidence를 제공하는 Reference 구현.

## 17.2 Conflict

| ID | 내용 | 영향 |
|---|---|---|
| CON-I-01 | CDC SLA `30초`와 `3초`가 서로 다른 2026-03 자료에 존재 | NFR Baseline 재확정 필요 |
| CON-I-02 | DR/AP Active-Active 표현과 DB 주센터 Active 전략의 조합이 자료별로 단순화되어 표현 | VIII장에서 역할/센터/DB 단위로 재구성 필요 |
| CON-I-03 | 표준 인터페이스 제품/명칭이 문서 시점에 따라 CruzAPIM/API/MCA/FOS/GSE 등으로 다양 | II/VI에서 목적·제품·Owner 정합화 필요 |

## 17.3 GAP / OPEN

| ID | 유형 | 내용 |
|---|---|---|
| GAP-I-01 | GAP | NFR 최신 승인본/최종 SLA Baseline 미확정 |
| GAP-I-02 | GAP | P-05 전략 원문 조각 미확인 |
| GAP-I-03 | GAP | 5대 원칙/12개 전략의 최종 승인/폐기 상태 정리 필요 |
| OPEN-I-01 | OPEN | FAST 경로의 Marketing Rule Engine 최종 제품/책임 경계 |
| OPEN-I-02 | OPEN | DR AP A-A / DB Active 정책의 시스템별 적용 매트릭스 |
| OPEN-I-03 | OPEN | PDMG를 최종 표준 Framework Baseline으로 승격할 범위 |

## 17.4 ADR 후보

| ADR | 질문 |
|---|---|
| ADR-I-01 | CDC 목표 SLA를 3초/30초 중 어떤 Baseline으로 확정할 것인가 |
| ADR-I-02 | FAST/DEEP 경계에서 DB 비경유 이벤트 처리의 책임을 어디까지 강제할 것인가 |
| ADR-I-03 | PDMG AS-IS 중 NSIGHT 표준으로 승격할 Framework Mechanism 범위는 어디까지인가 |
| ADR-I-04 | 표준 인터페이스 제품명보다 목적 기반 LTI 분류를 상위 Baseline으로 둘 것인가 |

---

# 18. FIG-I-10 — 다음 장 Handoff

```text
I. Vision & Strategy
  │
  ├─ Vision / NFR
  ├─ 5대 Domain
  ├─ Boundary / IF / Resource Principle
  ├─ Hybrid / HA-DR Direction
  └─ FAST / DEEP
        │
        ▼
II. NSIGHT Big Picture & System Boundary
  │
  ├─ Channel / User
  ├─ Delivery / Gateway
  ├─ Application / Business WAR
  ├─ Data / RDW / ADW
  ├─ API / Event / CDC / ETL / File
  ├─ Security Boundary
  └─ OM / Monitoring / Runtime Evidence
```

II장에서는 본 장의 전략을 실제 **공간/시스템/경계/연결선**으로 배치한다.

---

# 19. 검증 체크리스트

- [x] PDMG보다 NSIGHT Strategy가 먼저 설명되는가
- [x] 6단계 방법론이 중심 구조로 유지되는가
- [x] 5대 NFR이 아키텍처 구조와 연결되는가
- [x] 5대 서비스 도메인이 표시되는가
- [x] FAST/DEEP가 분리되어 있는가
- [x] PDMG가 Reference/AS-IS로 내려가 있는가
- [x] Source 시점이 있는 수치에 Baseline 표시가 있는가
- [x] CDC 3초/30초 충돌을 숨기지 않았는가
- [x] P-05를 임의로 생성하지 않았는가
- [x] 다음 장으로 Handoff가 정의되어 있는가

---

# 20. Completion Gate

```text
필수 Figure Plan : 10
실제 Text Figure : 10

L0 Strategy          PASS
L1 Domain/NFR        PASS
L2 Policy/Physical   PASS
L3 Runtime Strategy  PASS
PDMG Reference 분리  PASS
Conflict 시각화      PASS
GAP/ADR              PASS
Handoff              PASS
창작 수치            0건
```

**판정: CONDITIONAL PASS**

조건:

1. CDC SLA 최신 Baseline 확정
2. 설계전략 P-01~P-12 승인상태 정리
3. DR/AP/DB 적용 매트릭스 최신화

---

# 21. 최종 평가

NSIGHT의 Architecture Vision은 비교적 명확하다. 핵심은 **배치 중심 정보계를 없애는 것**이 아니라, 실시간 반응과 전략 분석을 서로 다른 경로와 자원으로 공존시키면서 데이터 신뢰·장애 격리·표준 연계·관측성을 동시에 확보하는 것이다.

이 장에서 가장 중요한 설계 문장은 다음이다.

> **책임은 공간에 고정하고, 연결은 경계를 통제한다.**

그리고 이를 Runtime 관점으로 바꾸면 다음과 같다.

> **FAST는 즉시 반응하고, DEEP는 정확히 분석하며, 두 경로의 부하와 장애는 서로 전이되지 않아야 한다.**

PDMG는 이 전략을 직접 정의하는 시스템이 아니라, 하위 Application/Framework Mechanism이 실제 Source에서 어떻게 구현되고 있는지를 증명하는 Reference다. 다음 II장에서는 이 전략을 NSIGHT 전체 Big Picture와 System Boundary로 구체화한다.
