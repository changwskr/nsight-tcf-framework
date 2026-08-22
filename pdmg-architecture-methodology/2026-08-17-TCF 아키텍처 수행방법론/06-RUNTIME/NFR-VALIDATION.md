# NFR VALIDATION

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

5대 NFR을 실제 Runtime Evidence로 검증하고 Architecture Gate의 PASS/HOLD 판단 근거를 제공한다.

## 검증 원칙

```text
NFR
 ↓
SLA/SLO
 ↓
Scenario
 ↓
Metric
 ↓
Evidence
 ↓
Gate
```

## Performance

검증:
- p95 응답시간
- FAST 1초
- CDC 지연
- Batch 완료
- DB/Thread/Pool 대기

## Availability

검증:
- AP 1대 장애
- N-1 처리량
- Traffic 전환
- DR 전환
- 데이터 정합성

## Scalability

검증:
- VM Scale-Out
- 증가 TPS
- DB/Pool/Thread 병목
- Event Consumer 확장
- ETL 병렬성

## Security

검증:
- Authentication/Authorization
- JWT/SSO
- 권한 우회
- 개인정보 마스킹
- 로그 민감정보
- 구간보안

## Observability

검증:
- Trace ID End-to-End
- ServiceId별 거래 추적
- APM Metric
- Error Correlation
- Runtime Evidence 저장

## NFR Result Table

| NFR | Target | Scenario | Result | Evidence | Gate |
|---|---|---|---|---|---|
| Performance | | | | | |
| Availability | | | | | |
| Scalability | | | | | |
| Security | | | | | |
| Observability | | | | | |

## 판정

- `PASS`: 기준 충족 + Evidence 확보
- `CONDITIONAL`: 제한조건 하 충족
- `HOLD`: 증적 부족 또는 기준 미충족
- `REJECT`: 구조적 결함으로 진행 불가


## 완료 Gate

- [ ] 5대 NFR 모두 Runtime Scenario가 존재한다.
- [ ] 수치 결과와 Evidence가 연결된다.
- [ ] NFR별 PASS/HOLD가 명시된다.
- [ ] 미충족 결과는 GAP으로 자동 이관된다.
- [ ] Evidence 없는 항목은 PASS 처리하지 않는다.

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
