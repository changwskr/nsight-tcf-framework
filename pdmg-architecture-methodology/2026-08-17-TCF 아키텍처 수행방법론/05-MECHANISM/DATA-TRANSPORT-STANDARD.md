# DATA TRANSPORT STANDARD

- 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT
- 방법론 단계: **05. Mechanism**
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

CDC, Kafka, DataStage, File Transfer의 역할을 분리하여 데이터의 성격에 맞는 전송 메커니즘을 선택하고 FAST/DEEP 이중 흐름을 유지한다.

## Transport 선택 원칙

[SOURCE]

```text
Event                    → Kafka
Database Change          → CDC
Large Batch Transform    → DataStage
File                     → FOS/MFT
Online Request/Response  → HTTP/JSON
```

## Kafka

목적:
- 고객 행동/Event
- 실시간 반응
- 비동기 전달
- FAST Flow

금지:
- 대량 DW 적재 전체를 이벤트 스트림 하나로 해결
- 이벤트 처리에서 불필요한 DB 의존

## CDC

목적:
- 원천 데이터 변경 전달
- RDW 실시간/준실시간 적재
- DEEP 데이터 수집

[OPEN]
CDC SLA는 자료에 3초와 30초가 혼재하므로 Baseline Decision 필요.

## DataStage

목적:
- 대량 Batch
- 데이터 정제/가공
- RDW→ADW 분석경로

## File

목적:
- 대용량/정형 파일 교환
- ACK, 암호화, 이력 관리

## Transport Decision Matrix

| 질문 | 선택 |
|---|---|
| 사용자 행동에 즉시 반응? | Kafka |
| DB 변경을 전달? | CDC |
| 대량 변환/정제? | DataStage |
| 파일 계약? | FOS/MFT |
| 동기 업무요청? | HTTP/JSON |


## 완료 Gate

- [ ] 모든 데이터 이동이 표준 Transport 유형에 매핑된다.
- [ ] Kafka와 CDC의 역할이 분리된다.
- [ ] Batch/ETL이 Event 경로를 침범하지 않는다.
- [ ] CDC SLA 불일치가 해결된다.
- [ ] Transport별 장애/재처리 전략이 존재한다.

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
