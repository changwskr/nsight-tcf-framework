# DOMAIN SCOPE

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

NSIGHT의 주요 도메인/플랫폼이 담당하는 업무, 데이터, 실행 책임과 외부 공개 경계를 정의한다.

## Domain Scope 원칙

도메인은 단순 URL이나 서버명이 아니다.

```text
Business Responsibility
        +
Data Ownership
        +
Execution Boundary
        +
Integration Contract
```

를 함께 정의하는 책임 단위다.

## 주요 Domain Scope

### 1. Data Platform

**책임**
- 원천 데이터 수집
- RDW/ADW 적재
- CDC/ETL 데이터 흐름
- 데이터 제공

**비책임**
- 고객 행동 오퍼링 실행
- BI 화면 책임
- 채널 업무 로직

### 2. Marketing Platform

**책임**
- 고객/마케팅 업무
- Single View
- 실시간 Rule/Offering
- 업무 Service/API

**비책임**
- 대용량 원천 ETL
- ADW 운영 책임

### 3. BI Portal

**책임**
- 분석 조회
- 보고서
- 경영지표
- 분석 사용자 인터페이스

**비책임**
- 온라인 거래 DB 갱신
- FAST 이벤트 처리

### 4. Data Governance

**책임**
- 데이터 표준
- 메타데이터
- 데이터 품질
- 공통 정의

### 5. IT Service / Infrastructure Support

**책임**
- 인증/SSO
- Framework
- Logging/APM
- Batch 운영
- CI/CD
- 배포·운영 공통기반

## Domain Boundary 규칙

- 각 도메인은 자기 데이터와 서비스를 책임진다.
- 다른 도메인의 내부 DAO/Mapper/Table을 직접 변경하지 않는다.
- 도메인 간 호출은 공개된 표준 계약을 사용한다.
- 배포단위가 다른 업무 서비스는 프로젝트 의존으로 우회 호출하지 않는다.
- 순환 동기 호출은 금지한다.
- Timeout, 오류, 권한, 로그, 추적정보를 계약에 포함한다.

## Scope Card Template

| 항목 | 정의 |
|---|---|
| Domain | |
| Business Owner | |
| Architecture Owner | |
| 기능 책임 | |
| 데이터 책임 | |
| 공개 Interface | |
| 의존 Domain | |
| Runtime | |
| 주요 NFR | |
| 금지 접근 | |


## 완료 Gate

- [ ] 모든 상위 도메인의 Scope Card가 작성되었다.
- [ ] 데이터 소유권이 명확하다.
- [ ] 공개 계약과 내부 구현이 분리된다.
- [ ] 도메인 간 직접 DB/DAO 접근이 없다.
- [ ] 순환 의존 후보가 식별되었다.

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
