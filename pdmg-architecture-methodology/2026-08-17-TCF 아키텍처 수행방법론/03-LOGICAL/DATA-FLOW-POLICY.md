# DATA FLOW POLICY

- 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT
- 방법론 단계: **03. Logical**
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

데이터 종류와 목적에 따라 FAST, CDC, Batch/ETL, 분석 경로를 분리하고 데이터가 임의의 길로 이동하지 않도록 표준 흐름을 정의한다.

## 데이터 흐름 원칙

[SOURCE]

```text
실시간 이벤트   → Kafka
데이터 변경     → CDC
대용량 가공     → DataStage / ETL
운영/조회 데이터 → RDW
분석 데이터     → ADW
```

## FAST Flow

```text
Customer Action
      ↓
Event
      ↓
Kafka
      ↓
Marketing Rule
      ↓
Offering
```

- 목표: 즉시 반응
- DB 비의존 처리
- 경량 이벤트
- 1초 SLA 대상

## DEEP Flow

```text
Source System
      ↓
CDC
      ↓
RDW
      ↓
DataStage / ETL
      ↓
ADW
      ↓
BI / Analytics
```

- 목표: 정확성·안정성·분석
- 대용량 처리
- 온라인과 자원 분리
- 일배치 완료시각 관리

## Data Flow 정책

1. 이벤트와 트랜잭션 데이터 전달을 동일하게 취급하지 않는다.
2. 분석 데이터 가공이 온라인 서비스 자원을 점유하지 않는다.
3. 원천 DB 간 임의 DB Link/P2P 연결을 금지한다.
4. 데이터 흐름마다 Owner, SLA, 오류복구 방식을 지정한다.
5. 데이터 흐름은 Trace/Evidence를 남겨야 한다.

## Flow Contract

| 항목 | 정의 |
|---|---|
| Source | |
| Target | |
| Data Type | Event / CDC / Batch / File / API |
| Owner | |
| Latency SLA | |
| Volume | |
| Retry | |
| Ordering | |
| Idempotency | |
| Error Route | |
| Audit/Trace | |


## 완료 Gate

- [ ] 모든 핵심 데이터 흐름이 FAST/DEEP/기타로 분류된다.
- [ ] Source/Target/Owner가 정의된다.
- [ ] 데이터 흐름별 SLA와 오류처리가 있다.
- [ ] 온라인 자원과 분석 자원 경합이 없다.
- [ ] 임의 P2P/DB Link가 존재하지 않는다.

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
