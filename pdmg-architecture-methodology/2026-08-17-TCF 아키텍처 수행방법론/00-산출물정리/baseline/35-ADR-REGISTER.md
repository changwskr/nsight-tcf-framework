# NSIGHT ADR Register — Wave 4 Human Decision Preparation

> 상태 원칙: `PROPOSED/DRAFT`는 승인 전 상태이며, 승인자/일자 없는 `APPROVED`는 허용하지 않는다.

| ADR ID | Title | Status | Readiness | Priority | Runtime |
|---|---|---|---|---|---|
| ADR-SEC-001 | JWT Signing Key Source of Truth | PROPOSED | READY_FOR_HUMAN_DECISION | P0 | RUN-JWT-ROTATE |
| ADR-SEC-002 | kid / Rotation / JWKS Grace Lifecycle | PROPOSED | READY_FOR_HUMAN_DECISION | P0 | RUN-JWT-ROTATE |
| ADR-SES-001 | Session Strategy | DRAFT | NEEDS_OWNER_INPUT_AND_RUNTIME | P0 | RUN-SESSION, RUN-CF |
| ADR-TX-001 | NSIGHT Transaction Owner | PROPOSED | READY_FOR_HUMAN_DECISION | P0 | RUN-TIMEOUT, RUN-SLOWSQL |
| ADR-TMO-001 | Timeout Execution / Cancellation Model | PROPOSED | READY_FOR_HUMAN_DECISION | P0 | RUN-TIMEOUT, RUN-SLOWSQL |
| ADR-HA-001 | AP HA Topology | DRAFT | RUNTIME_DEPENDENT | P0 | RUN-P1200, RUN-N1, RUN-CF, RUN-ROLLING |
| ADR-DR-001 | Center Failure Session/Data Continuity | DRAFT | NEEDS_OWNER_INPUT_AND_RUNTIME | P0 | RUN-SESSION, RUN-CF |
| ADR-DR-002 | Service RTO/RPO Classes | DRAFT | NEEDS_OWNER_INPUT | P0 | RUN-CF |
| ADR-INFRA-001 | Tomcat JVM : Application Deployment Unit | PROPOSED | READY_FOR_HUMAN_DECISION | P0 | RUN-N1, RUN-ROLLING |
| ADR-PERF-001 | Runtime Approved VM Capacity | DRAFT | RUNTIME_DEPENDENT | P0 | RUN-P600, RUN-P1200, RUN-S1800, RUN-HIKARI, RUN-N1 |
| ADR-DATA-001 | RDW/ADW Ownership / Read-Write Boundary | PROPOSED | READY_FOR_HUMAN_DECISION | P0 | - |
| ADR-INT-001 | Cross-Domain Integration Contract | PROPOSED | READY_FOR_HUMAN_DECISION | P0 | RUN-TRACE |
| ADR-OPS-001 | OM Control Plane Scope | PROPOSED | READY_FOR_HUMAN_DECISION | P0 | RUN-TRACE, RUN-ROLLING |
| ADR-OBS-001 | GUID + ServiceId Runtime Evidence Standard | PROPOSED | READY_FOR_HUMAN_DECISION | P0 | RUN-TRACE |
| ADR-GOV-001 | Architecture Model Source of Truth | PROPOSED | READY_FOR_HUMAN_DECISION | P0 | - |
| ADR-DEP-001 | Production Deployment Pipeline | PROPOSED | READY_FOR_HUMAN_DECISION | P0 | RUN-ROLLING |

상세 승인안은 `98BH-adr-sheets/` 및 `98BI-ADR-APPROVAL-REGISTER.json`을 사용한다.
