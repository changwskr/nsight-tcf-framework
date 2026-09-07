# 별첨 N. Evidence Register

## N.1 Evidence Priority

```text
1. Source / Config
2. Runtime / Deployment Evidence
3. PDMG Current Architecture Analysis
4. Approved ADR / PASS Register
5. Official Project Architecture Documents
6. Presentation / Explanatory Documents
7. Historical Standards
8. General Technical Knowledge
```

## N.2 Current Strong Evidence

```text
Modules
pdmg-ui / pdmg-jwt / pdmg-fw / pdmg-service

Runtime
Filter → Security → MVC → TCF
→ Worker → Transaction
→ Handler → Facade → Service
→ DAO / Mapper → DB

Technology
Java 21
Spring Boot 3.5.14
Gradle Multi-project

Message
{ hdr_nhnis, dto }
{ hdr_nhnis, result }

Trace
GUID / ServiceId
```

## N.3 Evidence Still Required

```text
CMDB / Host / VM / JVM / WAR Inventory
Deployment Manifest / ArtifactHash
L4 / Apache / Connector Config
Port / Firewall Matrix
Datasource / DB Node Inventory
JWT Key/JWKS Integration Test
JDBC Timeout / Cancel Test
Load / Failure Test
Backup / Restore Test
DR Failover / Failback Test
Runtime Evidence Collector
```
