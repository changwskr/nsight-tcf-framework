# NFR — 5대 비기능 요구사항

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

Architecture Vision을 실제 설계 판단 기준으로 사용할 수 있도록 Performance, Availability, Scalability, Security, Observability의 5대 NFR을 정의한다.

## 5대 NFR

[SOURCE]

```text
Performance
Availability
Scalability
Security
Observability
```

기능 요구사항이 “무엇을 하는가”라면 NFR은 **“어떤 품질과 조건으로 해야 하는가”**에 대한 구조적 약속이다.

## 1. Performance

### 목표
- 고객/마케팅 온라인 응답 성능 보호
- 이벤트 기반 실시간 반응
- 데이터 통합 지연 최소화
- 대량 분석이 온라인 성능을 침해하지 않도록 격리

### 설계 전략
- FAST와 DEEP 분리
- 온라인과 배치 분리
- RDW와 ADW 분리
- 이벤트는 Kafka 기반 경량 흐름
- 대량 가공은 ETL 전용 경로

## 2. Availability

### 목표
- 중요 서비스 이중화
- 장애 영향 최소화
- 현실적으로 운영 가능한 DR 구성

### 설계 전략
- 기능별 서버/자원 분리
- Fault Isolation
- AP 단위 Active-Active 활용
- DB 무결성과 운영복잡도를 함께 고려한 DR 전략

## 3. Scalability

### 목표
- 서비스 증가와 부하 증가에 유연하게 대응
- 데이터 플랫폼과 서비스 플랫폼의 서로 다른 확장 특성 반영

### 설계 전략
- 서비스 영역: Private Cloud VM 기반 Scale-Out
- 데이터 영역: Exadata 등 데이터 처리 특성에 맞는 병렬 확장
- 도메인별 독립 확장

## 4. Security

### 목표
- 설계 단계부터 보안 내재화
- 인증·권한·개인정보·전송구간 보호
- 업무와 운영의 책임 경계 유지

### 설계 전략
- SSO/JWT 등 표준 인증체계
- 개인정보 마스킹
- 전송구간 암호화
- 접근통제 및 감사 추적
- 보안 공통기능의 Framework화

## 5. Observability

### 목표
- 장애가 발생한 뒤 찾는 구조가 아니라, 평시 측정·추적 가능한 구조
- 서비스 단위 End-to-End 거래 추적

### 설계 전략
- GUID / Trace ID
- APM
- 통합로그
- 거래로그 / ImageLog
- SLA 지표
- Runtime Evidence

## NFR Traceability

| NFR | Big Picture | Logical | Physical | Mechanism | Runtime |
|---|---|---|---|---|---|
| Performance | FAST/DEEP 분리 | 온라인/분석 정책 | 자원 분리 | Kafka/CDC/ETL | 응답·지연 검증 |
| Availability | 기능 Zone | 장애격리 정책 | 이중화/DR | 전환 규칙 | 장애 시나리오 |
| Scalability | 도메인 분리 | 독립확장 정책 | Scale-Out | 표준 Framework | 부하 증가 검증 |
| Security | 보안 영역 | 접근통제 정책 | 망/구간 보호 | 인증·권한 표준 | 보안시험 |
| Observability | 운영 공통영역 | 추적 정책 | APM/로그 자원 | GUID/로그 표준 | E2E Trace 검증 |

## NFR 관리 원칙

- NFR은 문서 첫 장에만 존재하는 목표가 아니다.
- 모든 Architecture Decision은 어느 NFR을 만족시키는지 명시해야 한다.
- NFR 간 Trade-Off는 숨기지 않고 ADR/GAP으로 기록한다.
- 성능 수치와 지연 시간은 Runtime Evidence로 확인한다.


## 완료 Gate

- [ ] 5대 NFR이 모두 정의되어 있다.
- [ ] 각 NFR별 설계 전략이 존재한다.
- [ ] Big Picture~Runtime까지 Traceability가 있다.
- [ ] NFR 충돌/Trade-Off가 식별되어 있다.
- [ ] 측정 가능한 NFR은 SLA와 연결되어 있다.

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
