# RESOURCE ISOLATION

- 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT
- 방법론 단계: **04. Physical**
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

성능과 장애의 상호 전파를 차단하기 위해 Online, Batch, ETL, Event, RDW, ADW 등 실행 자원을 물리적으로 분리하는 기준을 정의한다.

## 격리 대상

[SOURCE]

```text
Online AP
Batch AP
ETL Server
Event Server
IMDG Server
CDC Relay
RDW
ADW
```

## 격리 목적

- 온라인 성능 보호
- 장애 범위 축소
- 독립적 확장
- 운영 분석 단순화
- DB Session/Thread/CPU 경합 방지

## Isolation Matrix

| Source | Target | 공유 가능? | 원칙 |
|---|---|---:|---|
| Online AP | Batch Thread Pool | No | 실행자원 분리 |
| Online AP | ETL Runtime | No | 장시간 처리 분리 |
| BI | RDW 대량 분석 | 제한/금지 | ADW 사용 |
| FAST Event | ADW Query | No | DB 비의존 우선 |
| Batch | Online DB Pool | No | Pool 분리 |
| RDW | ADW | 역할 분리 | 데이터 흐름만 정의 |

## JVM/Thread/Pool 격리

[PROJECT-BASELINE]

애플리케이션 런타임에서는 최소 다음 자원을 구분해 본다.

- Tomcat Request Thread
- Timeout/Worker Thread
- Async/Audit Executor
- HikariCP DB Pool
- Batch Executor
- External Integration Pool

## 운영 경보

- Busy Thread > 기준치
- DB Pool Active/Wait 증가
- GC Pause 증가
- 특정 WAR/ServiceId 자원 독점
- SQL Time 급증
- Event Lag 증가


## 완료 Gate

- [ ] Online/Batch/Event/ETL 자원이 분리된다.
- [ ] RDW/ADW 역할과 접근 주체가 분리된다.
- [ ] Thread/Pool 공유로 인한 장애 전파가 없다.
- [ ] Resource Utilization 경보가 정의되어 있다.
- [ ] 장애·부하 테스트로 Isolation 효과를 검증한다.

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
