# INTEGRATION POLICY

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

시스템과 도메인 간 연결을 표준 경로로 통제하여 장애전파, 보안 편차, 비표준 연결, 데이터 소유권 침해를 방지한다.

## Integration 원칙

[SOURCE]

> **P2P 직접 연결을 금지하고 표준 인터페이스를 강제한다.**

## 유형별 표준 경로

| 유형 | 표준 경로 |
|---|---|
| 시스템 간 API 거래 | Cruz APIM |
| 파일 전송 | FOS / MFT |
| 데이터 변경 실시간 전달 | CDC |
| 이벤트 | Kafka |
| 대용량 Batch/가공 | DataStage |
| 온라인 내부 실행 | Framework / 표준 전문 |

## 금지

- 임의 Point-to-Point 연결
- DB Link를 이용한 도메인 우회
- 타 시스템 Table 직접 갱신
- 인증/권한 없는 내부 URL 공개
- Timeout 없는 동기 호출
- 순환 동기 호출
- 프로젝트 의존성을 통한 배포경계 우회

## Integration Contract 필수항목

- Interface ID
- Source / Target
- Protocol
- Message Schema
- Authentication
- Authorization
- Connect Timeout
- Read Timeout
- Retry
- Circuit Breaker/Bulkhead 적용 여부
- Error Code
- Trace ID
- 개인정보/마스킹
- SLA

[PROJECT-BASELINE] Timeout은 하위 구간이 상위 구간보다 짧도록 설계한다.

```text
DB Query
  <
Transaction
  <
Integration Read
  <
Client
```


## 완료 Gate

- [ ] 모든 인터페이스가 표준 유형으로 분류된다.
- [ ] 비표준 P2P가 식별/제거되었다.
- [ ] Timeout/Error/Security/Trace가 계약에 포함된다.
- [ ] 순환 동기 호출이 없다.
- [ ] Integration 장애가 전체 서비스로 전파되지 않도록 설계된다.

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
