# ARCHITECTURE VISION

- 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT
- 방법론 단계: **01. Vision**
- 문서 성격: Architecture Methodology / Design Standard
- 핵심 철학: **아키텍처는 생존을 위한 설계**
- 상위 흐름: `Vision → Big Picture → Logical → Physical → Mechanism → Runtime`

## 기준 자료

이 문서는 다음 프로젝트 자료를 기준으로 작성한다.

- `2026-02-18 Future 발표 스크립터 조정의 사본 (임원) - Type 3 - JUNO.docx`
- `Future 아키텍처 임원 발표 스크립트 — Type 3 JUNO 정리본.md`
- `NH_N-SIGHT_아키텍처_발표자료-20260329 수정본.pptx`
- `2026-05-07_농협 상호금융 아키텍처 전략 브리핑-v1.0.docx`
- `NSIGHT_아키텍처_수행_방법론_임원발표스크립트_기반.md`

상세 기술 기준이 필요한 경우 현재 NSIGHT/PDMG 아키텍처 분석 자료를 보조 근거로 사용한다.

### 상태 표기

| 표기 | 의미 |
|---|---|
| `[SOURCE]` | 임원 발표/전략 브리핑에 직접 제시된 내용 |
| `[PROJECT-BASELINE]` | 현재 NSIGHT 프로젝트에서 기준으로 관리 중인 내용 |
| `[VALIDATION]` | Runtime/시험에서 증명해야 하는 항목 |
| `[OPEN]` | 자료 간 차이 또는 추가 의사결정이 필요한 항목 |

> 원칙: 발표자료의 전략 방향, 현재 구현(AS-IS), 목표 구조(TO-BE)를 섞지 않는다.


## 목적

NSIGHT가 왜 존재해야 하는지, 무엇을 바꾸려는지, 향후 설계 판단이 어떤 목적을 향해야 하는지를 한 문장과 핵심 원칙으로 고정한다.

## Architecture Vision

[SOURCE]

> **끊김 없는 데이터 관리를 통해 고객 행동에 즉시 반응하는 실시간 경영 기반의 시스템**

NSIGHT는 기존의 배치 중심 정보계를 단순 교체하거나 DW를 고도화하는 사업이 아니다.  
저장 중심 시스템을 **데이터가 흐르고, 연결되고, 반응하며, 경영 판단을 지원하는 플랫폼**으로 재설계하는 것이 목표다.

## Why — 왜 다시 짓는가

기존 정보계의 한계와 차세대 방향은 다음과 같이 정리한다.

| 기존 정보계 | NSIGHT 지향 |
|---|---|
| 배치 중심 | 실시간 + 배치 병행 |
| 데이터 저장/보고 | 실시간 의사결정·반응 |
| 지연된 데이터 활용 | Near Real-time 데이터 활용 |
| 시스템별 독립 운영 | 도메인 책임과 장애격리 |
| 분석과 온라인 자원 경합 | RDW/ADW 및 실행영역 분리 |
| 운영자의 사후 대응 | SLA·APM·통합로그 기반 관측 |

## Vision을 구성하는 5개 핵심어

```text
Data
  ↓
Flow
  ↓
Reaction
  ↓
Decision
  ↓
Management
```

1. **Data-Centric** — 데이터 흐름을 중심으로 설계한다.
2. **Reactive** — 고객 행동에 빠르게 반응한다.
3. **Scalable** — 서비스와 데이터 특성에 맞게 확장한다.
4. **Resilient** — 장애를 격리하고 서비스 지속성을 확보한다.
5. **Operable** — 측정·추적·통제 가능한 구조로 운영한다.

## Vision → Architecture 연결

```text
Vision
  │
  ├─ 고객 행동 즉시 반응
  │     └→ FAST / Event / Kafka
  │
  ├─ 정확한 데이터 기반 판단
  │     └→ DEEP / CDC / RDW / ETL / ADW
  │
  ├─ 안정적인 온라인 서비스
  │     └→ 온라인·배치·분석 자원 분리
  │
  ├─ 지속적 확장
  │     └→ 서비스 Scale-Out / 데이터 병렬확장
  │
  └─ 운영 가능
        └→ SLA / Trace / Logging / APM
```

## Vision 설계 원칙

- 기술과 솔루션보다 **목적과 정책을 먼저 결정**한다.
- Big Picture는 제품 목록이 아니라 **책임과 경계의 공간배치**로 작성한다.
- 실시간, 배치, 분석, 이벤트는 목적과 자원을 분리한다.
- 데이터 이동은 임의 연결이 아니라 표준 경로를 사용한다.
- Runtime에서 증명되지 않은 구조는 완료된 아키텍처로 보지 않는다.

## Vision 의사결정 질문

- 이 설계는 고객 반응 시간을 단축하는가?
- 이 설계는 온라인과 분석의 자원 경합을 줄이는가?
- 이 설계는 장애의 영향 범위를 줄이는가?
- 이 설계는 향후 확장 방식이 명확한가?
- 이 설계는 운영자가 측정하고 추적할 수 있는가?

## 주요 산출물

- Architecture Vision Statement
- 5대 NFR
- SLA
- Big Picture 설계 입력조건
- Architecture Decision 평가기준


## 완료 Gate

- [ ] Vision이 한 문장으로 정의되어 있다.
- [ ] 기존 구조에서 무엇을 바꾸는지 설명할 수 있다.
- [ ] 5대 NFR과 Vision의 연결이 명확하다.
- [ ] 각 주요 설계 결정이 Vision에 역추적 가능하다.
- [ ] 경영/업무/기술 이해관계자가 동일한 방향으로 해석할 수 있다.

## 변경관리

이 문서의 기준 변경은 다음 순서로 관리한다.

```text
요구/문제 발생
   ↓
영향 분석
   ↓
Architecture Decision 또는 GAP 등록
   ↓
관련 단계 문서 갱신
   ↓
Runtime Validation
   ↓
Baseline 반영
```

단순 문구 수정이 아니라 아키텍처 정책·책임·경계·SLA에 영향을 주는 변경은 반드시 영향 분석 후 반영한다.
