# LOGICAL ARCHITECTURE — 기술의 나열이 아닌 정책의 수립

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

Big Picture에서 구분한 공간마다 허용/금지, 데이터 흐름, 도메인 책임, Integration, 온라인/배치 분리 정책을 수립한다.

## 논리 설계의 정의

[SOURCE]

> **Logical Architecture는 제품을 고르는 단계가 아니라 정책을 만드는 단계다.**

```text
Big Picture
    ↓
Responsibility
    ↓
Policy
  ┌─ 허용
  ├─ 금지
  ├─ 표준 경로
  └─ 예외 통제
```

## 핵심 정책 축

1. Domain Separation
2. Data-Centric Flow
3. Integration Control
4. Specification / Standard
5. Online / Batch / Analytics Separation

## Logical Architecture 원칙

- 도메인별 책임 공간을 정의한다.
- 기능을 책임 단위로 독립시킨다.
- 데이터 흐름을 강제 정의한다.
- P2P 연결을 최소화하고 표준 Interface를 사용한다.
- 실시간과 대량처리를 분리한다.
- RDW와 ADW의 역할을 분리한다.
- 데이터 변경 책임은 데이터 소유 도메인에 둔다.

## Logical View

```text
Channel
   │
   ▼
[Standard Interface]
   │
   ├────────── FAST ──────────► Event / Marketing
   │
   └────────── DEEP ──────────► CDC → RDW → ETL → ADW → BI
```

## 정책을 작성하는 형식

| 항목 | 내용 |
|---|---|
| Policy ID | `POL-...` |
| 목적 | |
| 대상 도메인 | |
| Allowed | |
| Forbidden | |
| Exception | |
| NFR 연계 | |
| 검증 방법 | |
| Runtime Evidence | |

## Logical → Physical 인계

Logical 문서는 특정 서버명보다 **필요한 책임과 격리조건**을 먼저 전달한다.

예:
- “온라인과 배치는 독립 자원이어야 한다.”
- “RDW 온라인 조회와 ADW 분석을 분리한다.”
- “이벤트 처리는 DB 비의존 경로를 우선한다.”

Physical 단계는 이 정책을 실제 VM/DB/Network에 배치한다.


## 완료 Gate

- [ ] 핵심 Logical Policy가 문서화되었다.
- [ ] 허용/금지 항목이 명시되어 있다.
- [ ] 각 정책이 NFR과 연결된다.
- [ ] Physical 단계에 전달할 격리/배치 요구가 명확하다.
- [ ] 제품명이 정책을 대신하지 않는다.

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
