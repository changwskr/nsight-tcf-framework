# HA / DR ARCHITECTURE

- 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT
- 방법론 단계: **04. Physical**
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

가용성과 데이터 정합성, 운영복잡도의 Trade-Off를 고려하여 실제 운영 가능한 고가용성 및 재해복구 구조를 정의한다.

## 전략 방향

[SOURCE]

전략 자료에서는 이론적인 DB 레벨 Active-Active의 복잡성을 그대로 적용하기보다 **AP 레벨 Active-Active + 현실적인 DB DR** 방향을 채택한다.

## 기본 구조

```text
          Center A                         Center B / DR
     ┌──────────────┐                  ┌──────────────┐
     │ AP Group A   │◄──Traffic──────►│ AP Group B   │
     │ Active       │                  │ Active       │
     └──────┬───────┘                  └──────┬───────┘
            │                                 │
            └──────── DB Strategy ────────────┘
                       Integrity First
```

## HA 설계 원칙

- AP는 무상태/세션전략을 고려해 수평 확장 가능해야 한다.
- VM 1대 장애가 전체 처리량을 크게 감소시키지 않도록 한다.
- L4/GSLB 등 Traffic 전환 구조를 갖춘다.
- 배포는 Rolling 방식이 가능하도록 구성한다.

## DR 설계 원칙

- DB 정합성을 최우선한다.
- 복잡한 다중 Writer 구조의 운영위험을 평가한다.
- 센터 전환 시 RTO/RPO를 정의한다.
- AP, DB, Batch, Integration, File, Kafka 각각의 전환 시나리오를 작성한다.

## 장애 시나리오

1. AP VM 1대 장애
2. AP Group 장애
3. RDW 장애
4. ADW 장애
5. Kafka/Event 장애
6. CDC Relay 장애
7. Integration/APIM 장애
8. Center 전체 장애

각 시나리오에는 다음을 기록한다.

- 탐지
- Traffic 전환
- 처리 지속성
- 데이터 정합성
- 복구
- 운영 승인
- Runtime Evidence


## 완료 Gate

- [ ] AP 장애 시 자동/수동 전환 절차가 정의된다.
- [ ] RTO/RPO가 정의된다.
- [ ] DB 정합성 전략이 명확하다.
- [ ] 주요 구성요소별 DR 시나리오가 존재한다.
- [ ] 실제 DR Drill/Runtime Evidence로 검증된다.

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
