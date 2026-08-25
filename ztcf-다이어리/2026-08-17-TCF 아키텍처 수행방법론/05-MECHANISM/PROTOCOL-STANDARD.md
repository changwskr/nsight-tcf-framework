# PROTOCOL STANDARD

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

온라인 거래와 도메인 간 통신에서 사용하는 HTTP/JSON, 표준전문, ServiceId, Trace 정보의 공통 규격을 정의한다.

## 기본 Protocol

[SOURCE]

```text
HTTP
  +
JSON
  +
Standard Message
  +
Trace ID / GUID
```

## 표준 요청 구조

[PROJECT-BASELINE]

현재 PDMG/TCF 분석 자료에서는 공통 Header/Context와 업무 DTO를 분리하는 구조를 사용한다.

```text
Standard Request
├─ Common Header
│   ├─ GUID / Trace ID
│   ├─ ServiceId
│   ├─ User / Channel / Screen
│   └─ Common Context
└─ Business DTO
```

업무 계층은 공통 헤더 전체에 종속되지 않고 필요한 Context와 업무 DTO를 분리해 사용한다.

## ServiceId

ServiceId는 거래의 논리적 주소로 사용한다.

```text
ServiceId
   ↓
Dispatcher
   ↓
Handler / Use Case
```

## Protocol 규칙

- HTTP Method/Endpoint는 표준화한다.
- Content-Type은 JSON 표준을 사용한다.
- 공통 Header와 업무 Data를 분리한다.
- 모든 요청은 Trace ID/GUID를 가진다.
- 서비스 실행 식별은 ServiceId와 연결한다.
- 오류도 표준 Error Contract로 반환한다.
- 개인정보 필드는 Logging/Message 정책과 연계한다.

## Error Contract 최소항목

- Error Code
- Error Type
- User Message
- System Message / Action
- Trace ID
- ServiceId
- Timestamp

## Versioning

Breaking Change가 발생하면 다음을 평가한다.

- Message Schema Version
- Client 호환성
- ServiceId 변경 필요성
- 배포 순서
- Rollback


## 완료 Gate

- [ ] HTTP/JSON 표준이 확정된다.
- [ ] 공통 Header와 업무 DTO가 분리된다.
- [ ] ServiceId와 Trace ID가 모든 거래에 적용된다.
- [ ] Error Contract가 표준화된다.
- [ ] 계약 테스트로 호환성을 검증한다.

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
