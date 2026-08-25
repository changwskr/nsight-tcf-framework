# DEEP VALIDATION — 분석 및 전략 의사결정

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

원천 데이터가 CDC/RDW/ETL/ADW를 거쳐 정확하고 안정적으로 분석·경영지표에 제공되는지 검증한다.

## DEEP 목표

[SOURCE]

> **깊이 있는 분석과 전략적 의사결정을 지원한다.**

```text
Source
  ↓
CDC Relay
  ↓
RDW
  ↓
DataStage / ETL
  ↓
ADW
  ↓
BI / Analytics
```

## Architecture Characteristic

- 정확성
- 안정성
- 대량 처리
- 분석 전용 경로
- FAST/온라인과 절대 격리

## SLA

- CDC: 자료에 3초/30초 기준 혼재 → `[OPEN]`
- 일배치: 오전 6시 이전 완료

## Validation Point

| 단계 | 검증 |
|---|---|
| Source→CDC | Capture Lag |
| CDC→RDW | Apply Lag / Row Count |
| RDW→ETL | Batch Start |
| ETL | 처리량/오류/재처리 |
| ADW | 적재완료/정합성 |
| BI | 제공 가능시각 |
| Isolation | 온라인/FAST 영향 |

## Data Quality 검증

- Row Count
- Key Consistency
- 중복
- 누락
- 지연
- 재처리
- 기준시점

## 장애 시나리오

- CDC 중단
- ETL Job 실패
- ADW 부하
- 일배치 지연
- 재시작/재처리


## 완료 Gate

- [ ] CDC 측정구간과 공식 SLA가 확정된다.
- [ ] 일배치 오전 6시 이전 완료가 검증된다.
- [ ] 데이터 정합성 검증이 포함된다.
- [ ] 재처리 시 중복/누락이 없다.
- [ ] DEEP 부하가 FAST/온라인을 침해하지 않는다.

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
