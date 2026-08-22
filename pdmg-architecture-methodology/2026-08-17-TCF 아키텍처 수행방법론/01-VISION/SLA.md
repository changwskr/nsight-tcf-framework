# SLA — Architecture Service Level Targets

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

NFR을 측정 가능한 목표로 바꾸고 Runtime 단계에서 검증할 SLA 기준을 관리한다.

## SLA 기준

[SOURCE] 전략 자료에서 직접 확인되는 대표 SLA는 다음과 같다.

| 구분 | 기준 | 상태 |
|---|---:|---|
| 일반 마케팅/온라인 응답 | 3초 이내 | 프로젝트 전략 기준 |
| FAST 이벤트 처리 및 오퍼링 | 1초 이내 | 전략 자료 공통 |
| CDC 데이터 통합 | 30초 이내 | 다수 전략 문서 |
| CDC 지연 | 3초 이내 | 일부 2026-03-29 발표자료 |
| 일배치 완료 | 오전 6시 이전 | 런타임 전략 자료 |

## 중요 OPEN — CDC SLA 불일치

[OPEN]

현재 자료에는 CDC SLA가 **3초 이내**와 **30초 이내** 두 기준으로 존재한다.

```text
전략 브리핑 일부     : 최대 30초
2026-03-29 발표자료 : 3초 이내
```

이 문서에서는 임의로 하나를 확정하지 않는다.

### 결정 필요

- Architecture Baseline에서 공식 CDC SLA를 하나로 확정
- 소스→중계→RDW 중 어느 구간을 측정하는지 정의
- 평균/최대/p95 중 어떤 지표인지 정의
- 운영환경과 성능시험 환경의 측정 방식 통일

## SLA 계층

```text
Business SLA
    ↓
Service SLA
    ↓
Component / Integration SLO
    ↓
Runtime Metric
    ↓
Evidence
```

예를 들어 일반 온라인 3초 SLA를 만족시키기 위해 하위 Timeout이 순차적으로 더 짧아야 한다.

[PROJECT-BASELINE]

```text
DB Query Timeout
      <
Transaction Timeout
      <
외부 Integration Read Timeout
      <
Client / Channel Timeout
```

## SLA 검증항목

### 온라인
- p95 응답시간
- 오류율
- Busy Thread
- DB Pool 대기시간
- SQL 시간
- GC Pause

### FAST
- Event 생성→Kafka 전달
- Kafka→Rule Engine
- Rule Engine→Offering
- End-to-End 1초 목표

### DEEP
- 원천 변경→CDC 중계
- CDC→RDW
- ETL 시작/종료
- ADW 적재
- BI 제공 가능시점
- 일배치 완료시각

## SLA 증적 형식

| 항목 | 예 |
|---|---|
| Scenario ID | `FAST-001` |
| Baseline ID | `ARCH-BL-...` |
| 측정구간 | 고객행동→오퍼링 |
| 목표 | ≤ 1s |
| 결과 | 측정값 |
| 판정 | PASS/HOLD |
| Trace ID | 실행 추적 ID |
| Evidence | APM/Log/Metric 링크 |


## 완료 Gate

- [ ] 모든 핵심 SLA가 측정구간과 함께 정의되었다.
- [ ] CDC 3초/30초 불일치가 공식 Decision으로 해소되었다.
- [ ] FAST/DEEP/온라인 각 SLA에 Runtime 시나리오가 연결된다.
- [ ] 측정 방식(p95/Max 등)이 정의되어 있다.
- [ ] Runtime Evidence 없이 PASS 처리하지 않는다.

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
