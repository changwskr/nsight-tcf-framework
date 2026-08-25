# BIG PICTURE — 책임과 경계가 명확한 공간배치

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

Channel부터 Data Platform, Analytics, Marketing, BI까지 End-to-End 흐름을 한 장에서 정의하고, 기술 목록이 아니라 책임과 경계를 공간에 고정한다.

## 핵심 원칙

[SOURCE]

> **책임은 공간에 고정하고, 연결은 경계를 통제한다.**

Big Picture의 목적은 화려한 구성도가 아니라 **누가 무엇을 책임하고 어디까지가 경계인지**를 보여주는 것이다.

## End-to-End 구조

```text
[Customer / Channel]
        │
        ▼
[Interface / Integration]
        │
        ▼
[Data Platform]
  ┌─────┴─────┐
  ▼           ▼
 RDW         ADW
  │           │
  │           ▼
  │       Analytics / BI
  │
  ├─────────────► Marketing / Single View
  │
  └─────────────► Event / FAST
```

## 5개 책임 공간

[SOURCE]

1. **Data Platform**
2. **Marketing Platform**
3. **BI Portal**
4. **Data Governance**
5. **IT Service / Infrastructure Support**

## 공간배치의 의미

| 공간 | 중심 책임 | 경계에서 통제할 것 |
|---|---|---|
| Data Platform | 데이터 수집·저장·가공 | CDC/ETL/DB 접근 |
| Marketing | 실시간 고객 반응·업무서비스 | API/ServiceId/Event |
| BI Portal | 분석·조회·보고 | ADW 접근, 대량조회 |
| Data Governance | 표준·품질·메타 | 데이터 정의·품질 |
| IT Service/Infra | 인증·운영·배포·관측 | 공통서비스 접근 |

## Big Picture 설계 질문

- 책임이 중복된 공간이 있는가?
- 같은 데이터가 여러 공간에서 독립적으로 갱신되는가?
- 온라인 요청이 분석/배치 자원을 직접 점유하는가?
- 시스템 간 연결이 표준 Integration 경계를 우회하는가?
- 장애 발생 시 영향범위를 공간 단위로 설명할 수 있는가?

## 기대효과

- 책임 소재 명확화
- 장애 격리
- 독립적 확장
- 변경 영향 최소화
- 운영·보안·성능 통제 단순화


## 완료 Gate

- [ ] End-to-End 흐름이 한 장에서 이해된다.
- [ ] 5개 책임 공간이 식별되어 있다.
- [ ] 각 공간의 Owner와 Scope가 정의되어 있다.
- [ ] 영역 간 In/Out 경계가 식별되어 있다.
- [ ] 직접 연결과 공유 자원 위험이 표시되어 있다.

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
