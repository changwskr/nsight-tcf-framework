# FRAMEWORK STANDARD

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

온라인 거래의 공통 생명주기, ServiceId Routing, 표준 선·후처리, 업무 계층 책임을 Framework로 통일한다.

## Framework 목적

[SOURCE] Framework 통일은 프로젝트/개발자별 구현 편차를 줄이고 운영 표준을 일치시키기 위한 Mechanism이다.

## NSIGHT TCF 실행모델

[PROJECT-BASELINE]

```text
HTTP Request
    ↓
System Common
(Filter / Interceptor / Context)
    ↓
Online Controller
    ↓
TCF
    ↓
STF
    ↓
Timeout / Transaction Policy
    ↓
Dispatcher
    ↓
Handler
    ↓
Facade
    ↓
Service
    ↓
Rule / DAO / Integration
    ↓
ETF / Standard Response
```

> 주의: PDMG AS-IS의 `TransactionTemplate` 소유 구조와 NSIGHT 전체 TO-BE Transaction 정책은 같은 Baseline으로 자동 합치지 않는다. 시스템별 Source Baseline을 구분한다.

## 책임 분리

| 계층 | 책임 |
|---|---|
| Filter/Interceptor | HTTP·인증·Context·공통로그 |
| STF | 거래 전 정책 |
| Dispatcher | ServiceId Routing |
| Handler | Use Case Entry |
| Facade | 거래/Use Case 조립 |
| Service | 업무 절차 |
| Rule | 업무 판단 |
| DAO/Mapper | 데이터 접근 |
| ETF | 거래 후 공통처리 |

## Framework 공통 기능

- Standard Message
- Context
- Validation
- Authentication/Authorization
- Timeout
- Transaction
- Error Handling
- Logging / ImageLog
- ServiceId Routing
- Runtime Monitoring

## 금지

- Controller/Handler에서 DAO 직접호출
- 업무 코드가 공통 Error JSON 직접 생성
- ServiceId 없는 임의 거래 등록
- 업무별 독자 Context/Logging 체계
- Framework 경계를 우회하는 직접 호출


## 완료 Gate

- [ ] Framework Runtime Flow가 정의된다.
- [ ] 계층별 책임이 명확하다.
- [ ] ServiceId→Handler 추적성이 있다.
- [ ] 공통 Error/Timeout/Logging이 Framework화된다.
- [ ] AS-IS와 TO-BE Transaction 구조가 구분되어 있다.

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
