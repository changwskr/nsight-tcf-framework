# ONLINE / BATCH / ANALYTICS SEPARATION POLICY

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

온라인, 배치, 이벤트, 분석 처리를 논리적으로 분리하여 자원 경합과 장애 전파를 방지하고 각각 다른 SLA와 확장전략을 적용한다.

## 핵심 원칙

[SOURCE]

> **실시간과 배치, 운영과 분석을 명확히 분리한다.**

## 실행영역

```text
Online AP       → 고객/업무 온라인
Batch AP        → 배치 실행
Event Server    → Kafka/Event
ETL Server      → DataStage
RDW             → 운영/조회
ADW             → 분석
```

## 분리 정책

### Online
- 짧은 응답시간
- 동기 거래 중심
- DB Query/Transaction Timeout 제한
- 대량 파일/장시간 연산 금지

### Batch
- 대량 처리
- 스케줄/재처리
- 온라인 Thread/Pool과 독립

### Event
- 경량 이벤트
- 빠른 전달
- DB 비의존 우선

### Analytics
- 대량 Scan/Join 가능
- ADW 기반
- 온라인 RDW 보호

## 금지 예

```text
Online AP → 장시간 ETL 실행          X
BI Portal → RDW 대량 Full Scan       X
Batch AP → Online Thread Pool 공유   X
FAST → ADW 분석 완료 대기            X
```

## 예외 판단

실시간/배치 경계가 애매한 처리에는 다음 기준을 사용한다.

- 사용자 응답을 기다리는가?
- 10초 이상 걸릴 가능성이 있는가?
- 대량 데이터 Scan/Update가 필요한가?
- 재처리/스케줄링이 필요한가?
- 실패 시 동기 거래를 롤백해야 하는가?


## 완료 Gate

- [ ] Online/Batch/Event/Analytics 책임이 분리된다.
- [ ] 각 실행영역의 Runtime Resource가 구분된다.
- [ ] 온라인 거래에 장시간 작업이 포함되지 않는다.
- [ ] BI/분석이 RDW 온라인 성능을 침해하지 않는다.
- [ ] 각 영역별 SLA와 확장전략이 존재한다.

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
