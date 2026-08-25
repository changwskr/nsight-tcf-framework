# NSIGHT Architecture Board — One-Page Decision Sheet

> 목적: P0 ADR 16건 중 지금 원칙 승인이 가능한 11건을 한 번에 처리한다. **권고안은 승인완료가 아니며**, 실제 Decision/Approver/Date가 기록되어야 승인된다.

## A. 이번 회의 즉시 의결 대상 — 11건

| ADR | 권고 Board Action | 핵심 결정문 | 승인주체 | 승인 후에도 필요한 증적 |
|---|---|---|---|---|
| ADR-SEC-001 | **APPROVE 원칙 권고** | Production signing key의 Source of Truth는 KMS/HSM 또는 승인된 중앙 Key Store로 한다. Process-local RSA 생성은 DEV/LOCAL에만 허용한다. | Security Architect + Platform Owner | RUN-JWT-ROTATE |
| ADR-SEC-002 | **APPROVE 원칙 권고** | Versioned kid를 사용하고 JWKS는 Active + Previous public key를 grace 기간 동안 동시 제공한다. | Security Architect + Platform Owner | RUN-JWT-ROTATE |
| ADR-TX-001 | **APPROVE 원칙 권고** | 표준 온라인 업무의 기본 Transaction Owner는 Facade/Use Case Boundary로 한다. Service TX는 명시적 예외만 허용한다. | Application Architect + Framework Owner | RUN-TIMEOUT, RUN-SLOWSQL |
| ADR-TMO-001 | **APPROVE 원칙 권고** | Online Timeout은 Deadline/응답통제 Owner로 두고 Thread.interrupt를 DB rollback 보장수단으로 간주하지 않는다. DB Query < TX < Online < Client 순서를 강제한다. | Framework Owner + DBA + Application Architect | RUN-TIMEOUT, RUN-SLOWSQL |
| ADR-INFRA-001 | **APPROVE 원칙 권고** | 운영 기준은 WAS Server/VM, Tomcat JVM, Application/WAR을 별도 Entity로 관리하고 장애격리가 필요한 업무는 독립 JVM을 기본으로 한다. Multi-WAR은 명시적 예외로 관리한다. | Infra Architect + Application Architect | RUN-N1, RUN-ROLLING |
| ADR-DATA-001 | **APPROVE 원칙 권고** | RDW는 온라인/Near Real-time, ADW는 분석/대량조회 책임을 기본으로 하고 Domain/Table/View Owner 및 Read/Write Matrix를 SoT로 관리한다. | Data Architect + Domain Owners | - |
| ADR-INT-001 | **APPROVE 원칙 권고** | Cross-Domain 연계는 공개 ServiceId + 표준전문 + HTTP/EAI 계약을 사용하고 상대 Domain DAO/Mapper/Table 직접 접근을 금지한다. S2S Auth와 Remaining Deadline을 계약에 포함한다. | Integration Architect + Security Architect | RUN-TRACE |
| ADR-OPS-001 | **APPROVE 원칙 권고** | OM은 Control Plane으로 Catalog/Policy/Runtime/Deploy/Audit 관점을 제공하고 Runtime Plane의 TCF/STF가 정책을 집행한다. | Ops Owner + Architecture | RUN-TRACE, RUN-ROLLING |
| ADR-OBS-001 | **APPROVE 원칙 권고** | GUID + ServiceId를 System/Transaction/Business/Image/Error/Runtime Evidence의 공통 추적키로 사용한다. | Ops/Observability Owner + Application Architect | RUN-TRACE |
| ADR-GOV-001 | **APPROVE 원칙 권고** | Machine-readable JSON Model + JSON Schema + Validator + Baseline Manifest를 Architecture SoT로 사용하고 Markdown은 Human-readable View로 관리한다. | Architecture Board | - |
| ADR-DEP-001 | **APPROVE 원칙 권고** | GitLab→Runner→Artifact Repository→eCAMS/승인→Production의 재현가능한 Pipeline을 기준으로 하고 Rolling/rollback evidence를 의무화한다. | DevOps Owner + Ops Change Manager | RUN-ROLLING |

### Board 공통 승인조건

1. `APPROVE`는 **아키텍처 원칙 승인**이며 Runtime Closure를 의미하지 않는다.
2. Runtime 의존 ADR은 지정 Run이 `PRODUCTION_RUNTIME + PASS + runtime_approved=true`가 되어야 P0 Closure 가능하다.
3. 승인 기록에는 `Decision / Approver / Decision Date / Condition / Evidence Ref`를 남긴다.
4. 구현이 권고안과 다르면 ADR을 재개정하고 Drift/GAP Register에 반영한다.

## B. 이번 회의에서 최종 승인 보류 — 5건

| ADR | Readiness | Board 권고 Action | 보류 이유 / 선행조건 | 필요 Runtime |
|---|---|---|---|---|
| ADR-SES-001 | NEEDS_OWNER_INPUT_AND_RUNTIME | **DEFER** | Session idle 정책 60/90분 승인; 센터장애 재로그인 허용정책 | RUN-SESSION, RUN-CF |
| ADR-HA-001 | RUNTIME_DEPENDENT | **DEFER** | ADR-PERF-001 runtime value | RUN-P1200, RUN-N1, RUN-CF, RUN-ROLLING |
| ADR-DR-001 | NEEDS_OWNER_INPUT_AND_RUNTIME | **DEFER** | ADR-SES-001; service continuity policy | RUN-SESSION, RUN-CF |
| ADR-DR-002 | NEEDS_OWNER_INPUT | **DEFER** | business criticality classes | RUN-CF |
| ADR-PERF-001 | RUNTIME_DEPENDENT | **DEFER** | performance environment identity | RUN-P600, RUN-P1200, RUN-S1800, RUN-HIKARI, RUN-N1 |

## C. 회의 종료 시 기록

| 항목 | 기록 |
|---|---|
| 회의일 |  |
| Architecture Board Chair |  |
| 참석 승인주체 |  |
| 승인(Approve) 건수 |  |
| 보류(Defer) 건수 |  |
| 반려(Reject) 건수 |  |
| 조건부/후속 Action |  |
| Evidence Pack Ref |  |

> Gate 영향: 11건이 실제 승인되더라도 Runtime/P0 Closure가 남아 있으면 G80/HG90은 자동 PASS되지 않는다.
