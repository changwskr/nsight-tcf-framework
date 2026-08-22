# Architecture Board Decision Pack — Wave 6

> 이 문서는 16개 P0 ADR의 승인 회의를 위한 요약본이다. `PROPOSED`와 `추천안`은 승인완료가 아니다.

## READY_FOR_HUMAN_DECISION (11)

| ADR | 제목 | 권고안 | 승인주체 | Runtime 의존 |
|---|---|---|---|---|
| ADR-SEC-001 | JWT Signing Key Source of Truth | Production signing key의 Source of Truth는 KMS/HSM 또는 승인된 중앙 Key Store로 한다. Process-local RSA 생성은 DEV/LOCAL에만 허용한다. | Security Architect + Platform Owner | RUN-JWT-ROTATE |
| ADR-SEC-002 | kid / Rotation / JWKS Grace Lifecycle | Versioned kid를 사용하고 JWKS는 Active + Previous public key를 grace 기간 동안 동시 제공한다. | Security Architect + Platform Owner | RUN-JWT-ROTATE |
| ADR-TX-001 | NSIGHT Transaction Owner | 표준 온라인 업무의 기본 Transaction Owner는 Facade/Use Case Boundary로 한다. Service TX는 명시적 예외만 허용한다. | Application Architect + Framework Owner | RUN-TIMEOUT, RUN-SLOWSQL |
| ADR-TMO-001 | Timeout Execution / Cancellation Model | Online Timeout은 Deadline/응답통제 Owner로 두고 Thread.interrupt를 DB rollback 보장수단으로 간주하지 않는다. DB Query < TX < Online < Client 순서를 강제한다. | Framework Owner + DBA + Application Architect | RUN-TIMEOUT, RUN-SLOWSQL |
| ADR-INFRA-001 | Tomcat JVM : Application Deployment Unit | 운영 기준은 WAS Server/VM, Tomcat JVM, Application/WAR을 별도 Entity로 관리하고 장애격리가 필요한 업무는 독립 JVM을 기본으로 한다. Multi-WAR은 명시적 예외로 관리한다. | Infra Architect + Application Architect | RUN-N1, RUN-ROLLING |
| ADR-DATA-001 | RDW/ADW Ownership / Read-Write Boundary | RDW는 온라인/Near Real-time, ADW는 분석/대량조회 책임을 기본으로 하고 Domain/Table/View Owner 및 Read/Write Matrix를 SoT로 관리한다. | Data Architect + Domain Owners | - |
| ADR-INT-001 | Cross-Domain Integration Contract | Cross-Domain 연계는 공개 ServiceId + 표준전문 + HTTP/EAI 계약을 사용하고 상대 Domain DAO/Mapper/Table 직접 접근을 금지한다. S2S Auth와 Remaining Deadline을 계약에 포함한다. | Integration Architect + Security Architect | RUN-TRACE |
| ADR-OPS-001 | OM Control Plane Scope | OM은 Control Plane으로 Catalog/Policy/Runtime/Deploy/Audit 관점을 제공하고 Runtime Plane의 TCF/STF가 정책을 집행한다. | Ops Owner + Architecture | RUN-TRACE, RUN-ROLLING |
| ADR-OBS-001 | GUID + ServiceId Runtime Evidence Standard | GUID + ServiceId를 System/Transaction/Business/Image/Error/Runtime Evidence의 공통 추적키로 사용한다. | Ops/Observability Owner + Application Architect | RUN-TRACE |
| ADR-GOV-001 | Architecture Model Source of Truth | Machine-readable JSON Model + JSON Schema + Validator + Baseline Manifest를 Architecture SoT로 사용하고 Markdown은 Human-readable View로 관리한다. | Architecture Board | - |
| ADR-DEP-001 | Production Deployment Pipeline | GitLab→Runner→Artifact Repository→eCAMS/승인→Production의 재현가능한 Pipeline을 기준으로 하고 Rolling/rollback evidence를 의무화한다. | DevOps Owner + Ops Change Manager | RUN-ROLLING |

## NEEDS_OWNER_INPUT (1)

| ADR | 제목 | 권고안 | 승인주체 | Runtime 의존 |
|---|---|---|---|---|
| ADR-DR-002 | Service RTO/RPO Classes | 전사 전략의 RTO 참조값을 시스템 승인값으로 자동 승격하지 않고 서비스 등급별 RTO/RPO를 명시 승인한다. | Business Owner + Ops Owner + Data Owner | RUN-CF |

## NEEDS_OWNER_INPUT_AND_RUNTIME (2)

| ADR | 제목 | 권고안 | 승인주체 | Runtime 의존 |
|---|---|---|---|---|
| ADR-SES-001 | Session Strategy | 현재 자료만으로 최종안을 확정하지 않는다. 센터내 Sticky+DeltaManager와 외부 Session Store를 동일 기준으로 비교하고, 센터장애 시 재로그인 허용정책을 먼저 승인한다. | Application Architect + Ops Owner + Security | RUN-SESSION, RUN-CF |
| ADR-DR-001 | Center Failure Session/Data Continuity | 센터 장애 시 세션 유지 여부와 재로그인 허용정책을 업무정책으로 먼저 결정하고, 데이터 일관성/복구 방식과 분리해 승인한다. | Business Owner + Ops Owner + Architecture | RUN-SESSION, RUN-CF |

## RUNTIME_DEPENDENT (2)

| ADR | 제목 | 권고안 | 승인주체 | Runtime 의존 |
|---|---|---|---|---|
| ADR-HA-001 | AP HA Topology | 2+2, 3+3, 8Core Scale-Out 중 최종 토폴로지는 Runtime Approved Capacity와 N-1/Center Failure 결과 후 결정한다. | Infra Architect + Performance Lead + Ops Owner | RUN-P1200, RUN-N1, RUN-CF, RUN-ROLLING |
| ADR-PERF-001 | Runtime Approved VM Capacity | 500 TPS는 Legacy/Conservative, 855 TPS는 Working으로 유지하고 실제 승인값은 P600/P1200/S1800/N1/Hikari 결과로 확정한다. | Performance Lead + Infra Architect + DBA | RUN-P600, RUN-P1200, RUN-S1800, RUN-HIKARI, RUN-N1 |

## 회의 처리 규칙

1. `READY_FOR_HUMAN_DECISION`: 원칙 승인 여부를 `APPROVE/REJECT/DEFER`로 기록한다.
2. `NEEDS_OWNER_INPUT*`: 누락된 Owner 입력을 먼저 기록한 뒤 결정한다.
3. `RUNTIME_DEPENDENT`: Runtime Evidence 이전에는 `DEFER`가 기본 안전값이다.
4. 모든 승인에는 승인주체, 날짜, Evidence Ref를 기록한다.
5. ADR 승인과 Runtime Closure는 별개다. ADR이 승인되어도 필요한 Runtime Run이 미승인이면 G80은 유지된다.
