# P0 Closure Submission Checklist

| P0 Item | Area | Current Status | Required Next | 제출 상태 |
|---|---|---|---|---|
| P0-SEC-001 | JWT Signing Key SoT | IMPLEMENTED_CANDIDATE_BUILD_BLOCKED | Integrate into canonical build, provide approved KMS/HSM adapter/config, RUN-JWT-ROTATE multi-node/restart evidence | NOT SUBMITTED |
| P0-SEC-002 | kid / Rotation / JWKS Grace | IMPLEMENTED_CANDIDATE_BUILD_BLOCKED | Canonical build + active/previous JWKS grace validation + RUN-JWT-ROTATE | NOT SUBMITTED |
| P0-TX-001 | Facade Transaction Owner | IMPLEMENTED_CANDIDATE_STATIC_PASS | Integrate candidate patch into canonical repository and run rollback/integration tests | NOT SUBMITTED |
| P0-TMO-003 | Timeout worker cancel semantics | HARNESS_READY_BLOCKED_PRODUCTION_RUNTIME | Fault test proving rollback, no late commit, connection return, thread cleanup | NOT SUBMITTED |
| P0-PHY-001 | 71 Server -> JVM -> WAR -> Route | INGESTION_READY_BLOCKED_PRODUCTION_EVIDENCE | Collect per-host evidence bundle: httpd.conf/includes, server.xml, setenv.sh/CATALINA_BASE, deployment manifest, application config, L4/GSLB and bind by env+hostname+path+hash+timestamp | NOT SUBMITTED |
| P0-PERF-001 | Runtime approved capacity | HARNESS_READY_BLOCKED_PRODUCTION_RUNTIME | RUN-P600/P1200/S1800/N1/HIKARI and approve final VM/JVM/Tomcat/Hikari baseline | NOT SUBMITTED |
| P0-HA-001 | Session / HA / DR | HARNESS_READY_BLOCKED_PRODUCTION_RUNTIME_HUMAN | ADR-SES-001/ADR-HA-001/ADR-DR-* + RUN-SESSION/RUN-CF | NOT SUBMITTED |
| P0-OBS-001 | GUID + ServiceId E2E | HARNESS_READY_BLOCKED_PRODUCTION_RUNTIME | Run end-to-end trace and attach Apache/Tomcat/TCF/SQL/external evidence | NOT SUBMITTED |
| P0-DEP-001 | Rolling/Rollback safety | HARNESS_READY_BLOCKED_PRODUCTION_RUNTIME | RUN-ROLLING + rollback + DB/config backward compatibility | NOT SUBMITTED |
| P0-MIG-001 | Migration cutover readiness | BLOCKED_RUNTIME_HUMAN | Migration reconciliation result, go/no-go criteria, rollback rehearsal | NOT SUBMITTED |

Closure 승인값은 `CLOSED_RUNTIME` 또는 `CLOSED_APPROVED`만 허용한다. `CLOSED_STATIC`은 이미 정적 검증으로 닫힌 항목에만 사용하며 Wave 5 Closure Record로 재제출하지 않는다.
