# ARCHITECTURE GAPS

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

Runtime 검증 및 문서/소스 비교에서 발견된 설계 불일치, 미결정사항, 미검증 가정을 공식 GAP으로 관리하고 다음 아키텍처 사이클로 환류한다.

## GAP 관리 원칙

Runtime은 종료점이 아니라 피드백 지점이다.

```text
Runtime Failure / Drift
        ↓
GAP
        ↓
Root Cause
        ↓
Logical / Physical / Mechanism 영향
        ↓
ADR / Decision
        ↓
재설계
        ↓
재검증
```

## 현재 Seed GAP

### GAP-001 CDC SLA 불일치
[OPEN]
- 자료 A: 30초
- 자료 B: 3초
- 조치: 측정구간/지표/공식 SLA 확정

### GAP-002 AS-IS와 TO-BE Transaction 경계
[OPEN]
- PDMG AS-IS: Timeout Worker + TransactionTemplate 구조 확인
- NSIGHT 전체 표준: 정책 기반 Transaction 구조와 관계를 별도 Baseline으로 관리
- 조치: system-scope별 Source Evidence로 확정

### GAP-003 Runtime Evidence Closed Loop
[PROJECT-BASELINE]
- 문서/코드/모델은 축적되어 있으나 Runtime Evidence까지 자동으로 닫는 체계는 보완 필요
- 조치: Scenario→Trace→Evidence→Gate 체계 정착

### GAP-004 문서 Source of Truth
[PROJECT-BASELINE]
- 과거/현재/제안/복제 문서 혼재
- 조치: Current Baseline, Deprecated, Reference 분리

## GAP Register Template

| 항목 | 내용 |
|---|---|
| GAP ID | |
| 발견 단계 | |
| System Scope | |
| Description | |
| Evidence | |
| Severity | |
| NFR 영향 | |
| Related Policy | |
| Owner | |
| Decision/ADR | |
| Target Date | |
| Revalidation | |
| Status | OPEN/CLOSED |

## 우선순위

- P0: 보안/데이터무결성/운영중단
- P1: NFR/SLA 미충족
- P2: 표준 위반/운영비용 증가
- P3: 문서/추적성 개선


## 완료 Gate

- [ ] 모든 Runtime 실패가 GAP으로 기록된다.
- [ ] GAP에 System Scope와 Evidence가 있다.
- [ ] P0/P1은 Owner와 해결계획이 있다.
- [ ] 해결 후 동일 Scenario로 재검증한다.
- [ ] Closed GAP은 Baseline/Decision에 반영된다.

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
