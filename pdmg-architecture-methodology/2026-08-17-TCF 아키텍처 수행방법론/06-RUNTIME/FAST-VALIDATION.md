# FAST VALIDATION — 실시간 반응형 마케팅

- 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT
- 방법론 단계: **06. Runtime**
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

FAST 경로가 고객 행동 이벤트를 DB 의존 없이 경량 처리하여 목표 시간 내 오퍼링으로 연결하는지 검증한다.

## FAST 목표

[SOURCE]

> **고객 행동에 즉시 반응한다.**

```text
Customer Action
      ↓
Kafka
      ↓
Marketing Rule Engine
      ↓
Offering
```

## Architecture Characteristic

- Event-Driven
- DB 비의존 처리
- 경량화
- 즉시 반응
- DEEP 경로와 격리

## SLA

- 이벤트 처리 및 오퍼링 실행: **1초 이내**

## Validation Point

| 단계 | 검증 |
|---|---|
| Event 생성 | Timestamp/Trace 생성 |
| Kafka 전달 | Produce/Consume Lag |
| Rule 실행 | Rule Processing Time |
| Offering | End Timestamp |
| End-to-End | ≤ 1s |
| 장애 | Retry/DLQ/Failover |
| Isolation | DEEP 부하와 독립 |

## 부하 시나리오

- 정상 이벤트율
- 피크 이벤트율
- Kafka Broker 장애
- Consumer 지연
- Rule Engine 지연
- DEEP Batch 동시 수행

## PASS 조건

```text
E2E ≤ 1s
AND
Error Rate within target
AND
No DB blocking dependency
AND
DEEP load does not violate FAST SLA
```

## Evidence

- Kafka Lag
- Trace
- Rule Engine Metric
- Offering Result
- Error/DLQ
- CPU/Memory


## 완료 Gate

- [ ] E2E 1초 목표를 측정했다.
- [ ] Kafka Lag이 관리된다.
- [ ] FAST가 DB 완료를 동기 대기하지 않는다.
- [ ] DEEP 부하가 FAST SLA에 영향을 주지 않는다.
- [ ] 장애 시 메시지 유실/중복 처리 정책이 검증되었다.

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
