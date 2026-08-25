# DOMAIN POLICY

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

도메인 기능 분리, 데이터 소유권, 공개 서비스 계약, 도메인 간 의존 방향을 정책으로 강제한다.

## 최상위 정책

```text
Domain = Responsibility + Data Ownership + Public Contract
```

## 필수 규칙

| Rule | 정책 |
|---|---|
| DOMAIN-001 | 도메인은 독립 Business Responsibility를 가진다. |
| DOMAIN-002 | 타 도메인 DAO 직접 호출 금지 |
| DOMAIN-003 | 타 도메인 Mapper 직접 호출 금지 |
| DOMAIN-004 | 타 도메인 전용 Table 직접 갱신 금지 |
| DOMAIN-005 | 도메인 간 호출은 공개 Service/API/Event 계약 사용 |
| DOMAIN-006 | 별도 배포단위 간 업무호출을 Java Project Dependency로 우회 금지 |
| DOMAIN-007 | 순환 동기 호출 금지 |
| DOMAIN-008 | 호출자는 대상 도메인의 내부 구현을 알 필요가 없어야 함 |
| DOMAIN-009 | 데이터 변경 책임은 소유 도메인에 둔다 |
| DOMAIN-010 | Timeout/Error/Trace/Security를 도메인 계약에 포함 |

## 도메인 호출 예

```text
MG Domain
   │
   ▼
Public Client / Port
   │
   │ Standard Contract
   ▼
MK Domain Public Service
   │
   ▼
MK Internal Service
   │
   ▼
MK DAO / Table
```

금지:

```text
MG Service ──────X──────► MK DAO
MG Mapper  ──────X──────► MK Table Update
```

## 도메인 예외

예외가 필요한 경우 다음을 기록한다.

- 왜 표준 경계를 사용할 수 없는가?
- 데이터 무결성 영향은?
- Transaction 경계는?
- 장애전파 가능성은?
- 종료/대체 계획은?
- Architecture Approval은 누구인가?


## 완료 Gate

- [ ] 각 도메인의 Owner와 데이터 소유권이 명확하다.
- [ ] 직접 DAO/Mapper/Table 접근이 없다.
- [ ] 공개 계약이 정의되어 있다.
- [ ] 순환 의존이 없다.
- [ ] 예외는 Architecture Decision으로 추적된다.

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
