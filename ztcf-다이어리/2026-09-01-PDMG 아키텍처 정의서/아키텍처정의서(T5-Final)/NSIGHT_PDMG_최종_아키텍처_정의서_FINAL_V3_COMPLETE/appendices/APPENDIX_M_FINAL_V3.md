# 별첨 M. ADR / Architecture Decision Summary

주요 Decision:

| ADR | 주제 | 방향 |
|---|---|---|
| 001 | Architecture Baseline / SSOT | Evidence-backed Baseline |
| 002 | Code Registry / ServiceId | SSOT + 자동검증 |
| 003 | PDMG↔NSIGHT Mapping | Explicit Mapping Registry |
| 004 | Business Core | Common Facade |
| 005 | TCF Policy | ON default / OFF exception |
| 007 | Message Envelope | versioned hdr_nhnis + dto/result |
| 008 | Error Taxonomy | centralized |
| 009 | Worker Context | immutable snapshot |
| 010 | JWT | RS256 + JWKS |
| 011 | JWT Key | managed persistent key |
| 013 | Identity | Principal→Business User binding |
| 014 | Interface | purpose-driven selection |
| 015 | Direct DB | cross-system DML/DB-Link 금지 |
| 017 | Timeout | hierarchical timeout budget |
| 018 | Retry | retryable + backoff + idempotency |
| 021 | Data | RDW/ADW separation |
| 022 | CDC | tiered freshness SLA |
| 026 | Capacity | 16C/128G scale-out candidate |
| 027 | WEB/WAS | L4→Apache→Tomcat |
| 028 | JVM | business-group isolation |
| 029 | Capacity | end-to-end budget/backpressure |
| 030 | HA | stateless Active-Active + N+1 |
| 031 | DR | Warm/Hot conditional |
| 032 | DB DR | RDW/ADW separated HA/DR |
| 033 | CI/CD | Orchestrator OPEN |
| 034 | Artifact | immutable promotion |
| 035 | Observability | metrics+logs+traces |
| 036 | OM | control plane separation |
| 037 | Config/Secret | external config + secret store |
| 038 | Recovery | restore drills |
| 039 | Release | controlled promotion/rollback |
| 040 | Runtime Evidence | automated release gate |
