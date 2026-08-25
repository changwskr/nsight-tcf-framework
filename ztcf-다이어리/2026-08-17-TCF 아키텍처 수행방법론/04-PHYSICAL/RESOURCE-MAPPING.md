# RESOURCE MAPPING

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

논리 책임을 실제 AP/VM/DB/ETL/Event/Network 자원에 매핑하고, 각 자원의 용량·확장·운영 책임을 추적 가능하게 만든다.

## Resource Mapping 원칙

```text
Logical Component
      ↓
Runtime Role
      ↓
Server Group
      ↓
VM / DB / Middleware
      ↓
Capacity / SLA
```

## 기본 Mapping

| Logical | Runtime Role | Physical |
|---|---|---|
| Channel | Web UI | WebTopSuite / Web |
| Online Service | Online AP | Tomcat/Spring VM |
| Batch | Batch AP | Batch VM |
| Event | Event Processing | Kafka/Event Server |
| Data Ingest | CDC | CDC Relay |
| ETL | Batch Data Processing | DataStage/ETL Server |
| Operational Data | RDW | Oracle Exadata RDW |
| Analytics | ADW | Oracle Exadata ADW |
| Integration | API/File | APIM/FOS/MFT |
| Observability | Monitor/Log | APM/Log Platform |

## Capacity 연결

[PROJECT-BASELINE]

NSIGHT 용량 문서에서는 사용자 수와 동시 요청자 수를 분리하고 다음 흐름으로 산정한다.

```text
사용자/세션
  ↓
동시 요청자
  ↓
TPS
  ↓
AP 수량
  ↓
WAS Thread
  ↓
DB Pool / Session
  ↓
장애 잔여 처리량
```

## Resource Card

| 속성 | 값 |
|---|---|
| Resource ID | |
| Role | |
| Environment | DEV/TEST/PROD/DR |
| vCPU | |
| Memory | |
| JVM Heap | |
| maxThreads | |
| DB Pool | |
| Target TPS | |
| HA Group | |
| Owner | |
| Monitoring | |

## 8CORE / Scale-Out 참고

[PROJECT-BASELINE]

운영 안정성 자료는 동일 총자원 기준에서 작은 VM 단위 Scale-Out이 장애 영향, Rolling 배포, GC/덤프 분석, DB Pool 분산 측면에서 유리하다고 평가한다.

이 값은 **성능시험으로 최종 보정해야 하는 설계 기준**이며 하드웨어 사양만으로 처리량을 확정하지 않는다.


## 완료 Gate

- [ ] 모든 Logical Role에 Physical Resource가 연결된다.
- [ ] 사용자→TPS→Thread→Pool의 용량 Trace가 존재한다.
- [ ] N-1 장애 시 잔여 처리량이 계산된다.
- [ ] Resource Owner와 모니터링 항목이 지정된다.
- [ ] 성능시험으로 보정할 가정이 표시되어 있다.

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
