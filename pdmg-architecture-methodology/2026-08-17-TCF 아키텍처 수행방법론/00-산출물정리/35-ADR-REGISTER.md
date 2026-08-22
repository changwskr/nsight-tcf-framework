# NSIGHT ADR Register — G80

| ADR ID | Title | Status | Decision Scope | Priority |
|---|---|---|---|---|
| ADR-SEC-001 | JWT Signing Key SoT | OPEN | KMS/HSM vs process-local RSA | P0 |
| ADR-SEC-002 | kid / Key Rotation / JWKS Grace | OPEN | Versioned key lifecycle | P0 |
| ADR-SES-001 | Session Strategy | OPEN | DeltaManager vs Spring Session JDBC/기타 | P0 |
| ADR-TX-001 | NSIGHT Transaction Owner | OPEN | Facade owner + Service exceptions | P0 |
| ADR-TMO-001 | Timeout Execution / Cancellation Model | OPEN | Worker deadline + DB/TX cancel semantics | P0 |
| ADR-HA-001 | AP HA Topology | OPEN | 2+2 vs 3+3 vs 8Core Scale-Out | P0 |
| ADR-DR-001 | Center Failure Session/Data Continuity | OPEN | 재로그인/세션연속성/데이터복구 | P0 |
| ADR-DR-002 | Service RTO/RPO Classes | OPEN | 서비스별 승인등급 | P0 |
| ADR-INFRA-001 | Tomcat JVM : Application Deployment Unit | WORKING | 독립 JVM/Container 기준과 Multi-WAR 예외 | P0 |
| ADR-PERF-001 | Runtime Approved VM Capacity | OPEN | 500 vs 855 및 업무유형별 기준 | P0 |
| ADR-DATA-001 | RDW/ADW Ownership / Read-Write Boundary | WORKING | 전수 Matrix 필요 | P0 |
| ADR-INT-001 | Cross-Domain Integration Contract | WORKING | ServiceId/HTTP + S2S Auth/Deadline | P0 |
| ADR-OPS-001 | OM Control Plane Scope | OPEN | Catalog/Control/Runtime/Deploy 경계 | P0 |
| ADR-OBS-001 | GUID+ServiceId Runtime Evidence Standard | WORKING | Log/Metric/Trace 공통키 | P0 |
| ADR-GOV-001 | Architecture Model SoT | OPEN | JSON Model + Schema + Baseline Manifest | P0 |
| ADR-DEP-001 | Production Deployment Pipeline | OPEN | GitLab→Runner→Artifact→eCAMS | P0 |

`OPEN` ADR은 결정 전이며 본 문서에 임의 결론을 기록하지 않는다. `WORKING`은 현재 설계방향이나 HG90 승인 전 최종 Decision이 아니다.
