# PHYSICAL ARCHITECTURE — Hybrid Infrastructure

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

## 상세 기준 보조자료

- `NSIGHT_8CORE_VM_운영안정성_전략보고서.docx`
- `2026-05-31-NSIGHT_용량산정_세션60분_32core_256G_기준.docx`
- `OS 메모리와 JVM Heap 관계.docx`

상세 용량값은 아키텍처 전략과 실제 성능시험 결과를 함께 사용해 최종 확정해야 한다.

## 목적

Logical Architecture의 책임·격리 정책을 실제 데이터센터, Private Cloud VM, DB, AP, ETL, Event 자원에 배치한다.

## 핵심 전략

[SOURCE]

> **속도는 전용 자원으로, 유연성은 클라우드로.**  
> **데이터는 깊게 확장하고, 서비스는 넓게 확장한다.**

## Hybrid Architecture

```text
                ┌─────────────────────┐
                │ Private Cloud       │
                │ Service / AP        │
                │ Scale-Out           │
                └─────────┬───────────┘
                          │
                 Standard Interface
                          │
                ┌─────────▼───────────┐
                │ Data Platform       │
                │ Exadata / RDW / ADW │
                │ Parallel Expansion  │
                └─────────────────────┘
```

## 물리 자원 유형

- Online AP
- Batch AP
- ETL Server
- Event/Kafka processing
- IMDG
- CDC Relay
- RDW
- ADW
- BI/Report
- Web/L4/GSLB
- Monitoring/Logging

## 주요 설계 원칙

1. 기능별 자원을 격리한다.
2. 온라인과 분석 DB를 분리한다.
3. 서비스 영역은 수평 확장을 우선한다.
4. 데이터 처리 영역은 데이터 특성에 맞는 확장 방식을 적용한다.
5. 단일 VM/서버 장애의 영향 범위를 작게 만든다.
6. DR은 이론적 복잡성보다 실제 운영 가능한 구조를 우선한다.

## Logical → Physical Mapping

| Logical 요구 | Physical 구현 |
|---|---|
| 온라인/배치 분리 | Online AP / Batch AP 분리 |
| 이벤트 독립 | Event Server/Kafka 경로 |
| 분석 격리 | ADW 독립 |
| 온라인 DB 보호 | RDW 전용 |
| 실시간 데이터 | CDC Relay |
| 대용량 가공 | ETL/DataStage 전용 |
| 서비스 확장 | Private Cloud Scale-Out |


## 완료 Gate

- [ ] 모든 Logical Component의 물리 배치가 정해졌다.
- [ ] Resource Isolation이 실제 서버/VM 수준에서 반영된다.
- [ ] RDW/ADW가 물리적으로 분리된다.
- [ ] HA/DR과 확장 방식을 설명할 수 있다.
- [ ] 단일 장애의 영향 범위가 허용 수준인지 검증되었다.

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
