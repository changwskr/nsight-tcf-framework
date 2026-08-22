# Wave 4 — Human ADR Decision Checklist

> 체크는 실제 승인자/일자/조건이 기록된 경우에만 완료한다.

| 승인 | ADR | Readiness | Required Approver | Runtime Dependency |
|---|---|---|---|---|
| [ ] | ADR-SEC-001 | READY_FOR_HUMAN_DECISION | Security Architect + Platform Owner | RUN-JWT-ROTATE |
| [ ] | ADR-SEC-002 | READY_FOR_HUMAN_DECISION | Security Architect + Platform Owner | RUN-JWT-ROTATE |
| [ ] | ADR-SES-001 | NEEDS_OWNER_INPUT_AND_RUNTIME | Application Architect + Ops Owner + Security | RUN-SESSION, RUN-CF |
| [ ] | ADR-TX-001 | READY_FOR_HUMAN_DECISION | Application Architect + Framework Owner | RUN-TIMEOUT, RUN-SLOWSQL |
| [ ] | ADR-TMO-001 | READY_FOR_HUMAN_DECISION | Framework Owner + DBA + Application Architect | RUN-TIMEOUT, RUN-SLOWSQL |
| [ ] | ADR-HA-001 | RUNTIME_DEPENDENT | Infra Architect + Performance Lead + Ops Owner | RUN-P1200, RUN-N1, RUN-CF, RUN-ROLLING |
| [ ] | ADR-DR-001 | NEEDS_OWNER_INPUT_AND_RUNTIME | Business Owner + Ops Owner + Architecture | RUN-SESSION, RUN-CF |
| [ ] | ADR-DR-002 | NEEDS_OWNER_INPUT | Business Owner + Ops Owner + Data Owner | RUN-CF |
| [ ] | ADR-INFRA-001 | READY_FOR_HUMAN_DECISION | Infra Architect + Application Architect | RUN-N1, RUN-ROLLING |
| [ ] | ADR-PERF-001 | RUNTIME_DEPENDENT | Performance Lead + Infra Architect + DBA | RUN-P600, RUN-P1200, RUN-S1800, RUN-HIKARI, RUN-N1 |
| [ ] | ADR-DATA-001 | READY_FOR_HUMAN_DECISION | Data Architect + Domain Owners | - |
| [ ] | ADR-INT-001 | READY_FOR_HUMAN_DECISION | Integration Architect + Security Architect | RUN-TRACE |
| [ ] | ADR-OPS-001 | READY_FOR_HUMAN_DECISION | Ops Owner + Architecture | RUN-TRACE, RUN-ROLLING |
| [ ] | ADR-OBS-001 | READY_FOR_HUMAN_DECISION | Ops/Observability Owner + Application Architect | RUN-TRACE |
| [ ] | ADR-GOV-001 | READY_FOR_HUMAN_DECISION | Architecture Board | - |
| [ ] | ADR-DEP-001 | READY_FOR_HUMAN_DECISION | DevOps Owner + Ops Change Manager | RUN-ROLLING |

## 승인기록 최소요건

- Decision = APPROVE / REJECT / DEFER
- Approver 실명 또는 승인주체
- Decision Date
- 승인조건/예외
- 관련 Runtime Run 또는 정책 입력 링크

`PROPOSED`는 승인완료가 아니다.
