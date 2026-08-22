# INTEGRATION STANDARD

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

Logical Integration Policy를 실제 도구, 프로토콜, Timeout, 보안, 오류처리, 추적 규칙으로 구현한다.

## 표준 Integration Catalog

[SOURCE]

| 유형 | 표준 Mechanism |
|---|---|
| API | Cruz APIM |
| File | FOS / MFT |
| CDC | CDC Relay |
| Event | Kafka |
| ETL | DataStage |

## API Standard

- HTTP/JSON
- 인증/권한
- Trace ID
- Connect/Read Timeout
- 표준 오류코드
- Retry 제한
- Circuit Breaker/Bulkhead 검토

## File Standard

- 암호화 전송
- ACK/수신확인
- 재전송
- 파일 무결성
- 전송 이력
- 대용량 파일 분리 처리

## Timeout 계층

[PROJECT-BASELINE]

```text
DB Query Timeout
   <
Transaction Timeout
   <
Integration Read Timeout
   <
Client Timeout
```

세부 수치는 서비스 특성에 따라 확정하되 Timeout 계층의 역전은 피한다.

## Retry

- 동기거래 Retry는 기본적으로 제한한다.
- Idempotency가 보장되지 않는 변경거래 자동 Retry를 금지한다.
- 재시도는 부하증폭과 중복처리를 함께 평가한다.

## Integration Observability

모든 외부호출은 최소 다음을 기록한다.

- Source ServiceId
- Target System/Service
- Trace ID
- Start/End
- Result
- Timeout 여부
- Error Code
- Retry Count


## 완료 Gate

- [ ] Integration 유형별 표준 Mechanism이 지정된다.
- [ ] API/File/Event/CDC/ETL의 책임이 겹치지 않는다.
- [ ] Timeout/Retry/Error/Security가 표준화된다.
- [ ] 모든 외부 호출이 Trace 가능하다.
- [ ] 비표준 연결은 Exception Register로 관리된다.

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
