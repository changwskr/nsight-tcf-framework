# RESPONSIBILITY & BOUNDARY

- 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT
- 방법론 단계: **02. Big Picture**
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

각 Architecture Zone의 책임, 경계, 허용 인터페이스, 금지 접근을 명확히 하여 성능·장애·운영 책임을 분리한다.

## 책임과 경계

[SOURCE]

Big Picture의 본질은 **책임과 경계를 분리하는 것**이다.

```text
Responsibility
      ↓
Space / Zone
      ↓
Boundary
      ↓
Controlled Interface
```

## Responsibility Matrix

| 영역 | 책임 | 경계 | 대표 금지 |
|---|---|---|---|
| Channel | 사용자 접점 | HTTP/API | DB 직접접근 |
| Integration | 시스템 연결 | APIM/FOS/CDC/Kafka | 임의 P2P |
| Data Platform | 데이터 수집·저장 | DB/Data API | 타 도메인 업무로직 |
| Marketing | 업무/오퍼링 | Service/API/Event | 분석 자원 직접 사용 |
| BI | 분석/리포트 | ADW/BI Service | RDW 대량 조회 |
| Governance | 표준·품질 | 메타/표준 | 업무 데이터 직접처리 |
| IT/Infra | 운영 공통 | 공통 Platform | 업무 책임 침범 |

## Boundary의 4가지 관점

### 기능 경계
누가 업무를 수행하는가?

### 데이터 경계
누가 데이터를 생성·갱신·소유하는가?

### 런타임 경계
어느 AP/DB/Batch/Event 자원을 사용하는가?

### 운영 경계
장애·SLA·로그·배포 책임은 누구에게 있는가?

## Boundary Contract

모든 경계는 최소 다음을 정의한다.

- 호출 주체
- 대상 도메인
- Interface Type
- 인증/권한
- Request/Response 규격
- Timeout
- Error Contract
- Trace ID
- 데이터 소유권
- SLA

## 장애격리 확인

```text
Domain A 장애
   │
   ├─ 직접 Thread 고갈 전파?  → 금지
   ├─ DB Pool 고갈 전파?      → 금지
   ├─ 동기 순환호출?          → 금지
   └─ 표준 Timeout/Bulkhead?  → 적용 대상
```


## 완료 Gate

- [ ] 모든 주요 Zone의 책임이 정의되었다.
- [ ] Boundary Contract가 존재한다.
- [ ] 기능/데이터/런타임/운영 경계가 구분된다.
- [ ] 장애 전파 경로가 식별되고 통제된다.
- [ ] 책임이 겹치는 항목은 Decision/GAP으로 등록되었다.

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
