# MECHANISM ARCHITECTURE — 시스템을 움직이는 실행 규칙

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

Logical Policy와 Physical Structure를 실제 개발·통신·데이터·운영 방식으로 구현할 공통 실행 규칙을 정의한다.

## 정의

[SOURCE]

> 논리와 물리가 집의 구조라면, Mechanism은 그 집의 전기와 배관이다.

```text
Logical Policy
      +
Physical Structure
      ↓
Mechanism
      ↓
Executable Architecture
```

## 5대 Mechanism

| 구분 | 기준 |
|---|---|
| Protocol | HTTP/JSON + 표준 전문 |
| Framework | 공통 Framework / UI 표준 |
| Integration | APIM/FOS 등 표준 경로 |
| Data Transport | CDC와 Kafka 역할 분리 |
| Batch | DataStage 기반 대용량 처리 |

## Mechanism이 해결해야 할 문제

- 개발자마다 다른 통신방식
- 서비스마다 다른 오류/로그 처리
- 임의 P2P 연결
- 이벤트와 데이터 변경 전달의 혼용
- 온라인과 Batch 실행규칙 혼재
- 운영 추적성 부족

## Mechanism Trace

```text
Request
  ↓
Standard Protocol
  ↓
Framework
  ↓
Service Routing
  ↓
Business Logic
  ↓
Integration / DB / Event
  ↓
Logging / Trace / Metric
```

## Mechanism 완료 조건

각 Logical Policy마다 실제 구현 규칙이 존재해야 한다.

예:

| Logical Policy | Mechanism |
|---|---|
| 표준 인터페이스 | HTTP/JSON + Standard Message |
| 서비스 식별 | ServiceId |
| 공통 선후처리 | Framework |
| 실시간 반응 | Kafka |
| 변경 데이터 수집 | CDC |
| 대용량 가공 | DataStage |
| 파일 전송 | FOS/MFT |


## 완료 Gate

- [ ] 모든 Logical Policy에 구현 Mechanism이 연결된다.
- [ ] 개발자 임의 구현 영역이 최소화된다.
- [ ] 표준 Protocol/Framework/Integration/Data Transport가 확정된다.
- [ ] Error/Timeout/Trace/Logging 공통규칙이 포함된다.
- [ ] Runtime에서 적용 여부를 검증할 수 있다.

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
