# RUNTIME SCENARIOS

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

설계도가 실제 서비스 흐름으로 동작하는지 FAST/DEEP/온라인/장애 시나리오를 이용해 End-to-End로 검증한다.

## Runtime의 의미

[SOURCE]

> Runtime은 그림을 설명하는 단계가 아니라, 논리·물리·메커니즘이 실제로 융합되어 Vision과 NFR을 만족하는지 확인하는 단계다.

```text
Logical
  +
Physical
  +
Mechanism
   ↓
Runtime Scenario
   ↓
Evidence
   ↓
PASS / GAP
```

## 필수 시나리오

### R01 일반 온라인 거래
- Channel → Web/AP → Framework → Service → RDW
- p95 응답
- Timeout/Transaction
- Trace

### R02 FAST
- Customer Action → Kafka → Rule → Offering
- 1초 이내

### R03 DEEP
- Source → CDC → RDW → ETL → ADW → BI
- CDC/Batch SLA

### R04 AP 장애
- VM 1대 Down
- Traffic 재분배
- 잔여 처리량

### R05 Integration 장애
- 외부 시스템 지연/Timeout
- Circuit/Bulkhead
- 오류 응답

### R06 DB 부하
- Slow SQL / Pool Wait
- 온라인 영향

## Scenario Template

| 항목 | 내용 |
|---|---|
| Scenario ID | |
| 목적 | |
| Input | |
| Expected Flow | |
| NFR | |
| SLA | |
| Failure Injection | |
| Evidence | |
| Result | PASS/HOLD |
| GAP | |


## 완료 Gate

- [ ] 일반 온라인/FAST/DEEP 시나리오가 존재한다.
- [ ] 장애 시나리오가 존재한다.
- [ ] 각 시나리오가 NFR/SLA와 연결된다.
- [ ] Runtime Evidence가 수집된다.
- [ ] 실패 결과가 GAP/ADR로 환류된다.

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
