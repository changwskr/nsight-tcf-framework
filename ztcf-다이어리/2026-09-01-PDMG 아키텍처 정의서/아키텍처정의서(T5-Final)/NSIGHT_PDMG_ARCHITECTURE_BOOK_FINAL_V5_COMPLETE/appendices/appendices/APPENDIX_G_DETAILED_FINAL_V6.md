# APPENDIX G. Configuration Inventory — Detailed Final

### G.1 Current Configuration Registry

| Config ID | Key/Item | Value | Source/Runtime | 상태 | Architecture Meaning | 주의/GAP |
|---|---|---|---|---|---|---|
| CFG-FW-001 | nhnis.fw.tcf.enabled | true | pdmg-fw / application config | [AS-IS] | TCF ON current snapshot | Target policy ≠ snapshot |
| CFG-FW-002 | nhnis.fw.timeout.enabled | true | pdmg-fw / application config | [AS-IS] | Timeout mechanism enabled |  |
| CFG-FW-003 | nhnis.fw.timeout.milliseconds | 5000 | pdmg-fw / application config | [AS-IS] | Worker deadline snapshot | Target SLA 아님 |
| CFG-FW-004 | nhnis.fw.timeout.pool-size | 20 | pdmg-fw / application config | [AS-IS] | PDMG worker pool | Tomcat threads와 별개 |
| CFG-FW-005 | nhnis.fw.timeout.queue-capacity | 100 | pdmg-fw / application config | [AS-IS] | Worker queue | Backpressure capacity |
| CFG-FW-006 | nhnis.fw.commons.legacy-web.enabled | true | pdmg-fw / application config | [AS-IS] | Legacy web commons enabled | TCF OFF와 동일 의미 아님 |
| CFG-FW-007 | nhnis.fw.commons.filter.enabled | true | pdmg-fw / application config | [AS-IS] | DefaultFilter enabled |  |
| CFG-APP-001 | Spring scanBasePackages | nhnis | pdmg-service bootstrap | [AS-IS] | pdmg-service + pdmg-fw same Spring context 가능 | Module ≠ Process |
| CFG-MYB-001 | Mapper resource pattern | classpath*:rdw.*/*.xml | RdwDataSourceConfig | [AS-IS] | Mapper XML load pattern | Subfolder 구조 변경 주의 |
| CFG-MYB-002 | MapperScan basePackages | nhnis.mg.co.a.persistence.dao | MyBatis config | [AS-IS] | DAO interface scan |  |
| CFG-RUN-001 | Java | 21 | Build/Runtime | [AS-IS] | JVM runtime |  |
| CFG-RUN-002 | Spring Boot | 3.5.14 | Build/Runtime | [AS-IS] | Application framework |  |
| CFG-BLD-001 | Build | Gradle Multi-project | Build config | [AS-IS] | pdmg-service→pdmg-fw, pdmg-jwt→pdmg-fw |  |
| CFG-SEC-001 | JWT issuer algorithm | RS256 | pdmg-jwt current source | [AS-IS] | Token issue | Verifier mismatch GAP |
| CFG-SEC-002 | JWT verifier path | HMAC jwt.secret | pdmg-fw current source | [GAP] | Business-side verification | RS256/JWKS로 정합 필요 |
| CFG-SEC-003 | JWKS exposure | /.well-known/jwks.json pattern | pdmg-jwt current source | [AS-IS] | Public key distribution | Business verifier integration GAP |
| CFG-TCF-001 | Controller mappings | /online ; /{businessCode}/online ; /{serviceId} | OnlineTransactionController | [AS-IS] | TCF entry mappings | ServiceId mismatch 방어 필요 |
| CFG-TCF-002 | ServiceId resolution | Context header → request header → path variable | Controller runtime | [AS-IS] | Routing key resolution | Mismatch reject currently not confirmed |

### G.2 Timeout Interpretation

```text
5000ms / pool 20 / queue 100
= PDMG Current Worker Snapshot
≠ Tomcat maxThreads
≠ Hikari Pool Size
≠ NSIGHT Target SLA
```

### G.3 Security Configuration Conflict

```text
pdmg-jwt
RS256 Issue
    ↓
JWT
    ↓
pdmg-fw
HMAC jwt.secret Verify Path

[CRITICAL GAP]
```

### G.4 Config Governance

- 환경별 Config는 Artifact와 분리.
- Secret/Key는 일반 Config와 분리.
- Config Drift는 Deployment/Runtime Evidence와 비교.
- Production exact values는 승인된 Config Registry/Secret Store를 SSOT로 한다.
