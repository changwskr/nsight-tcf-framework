# NSIGHT Open Issues — G80

| Open ID | Issue | Required Evidence/Decision | Due Point | Blocking |
|---|---|---|---|---|
| OPEN-001 | Runtime Mandatory Runs 미실행/원본 미연결 | P600/P1200/S1800/N1/CF/TIMEOUT/SESSION/ROLLING/TRACE | Before HG90 | YES |
| OPEN-002 | JWT KMS/HSM 및 Key Rotation | 운영 Key Platform/Policy/Config | Before Security Gate | YES |
| OPEN-003 | Transaction/Timeout Owner | 4 Service TX 예외 + fault test | Before Performance Test | YES |
| OPEN-004 | 71 Server→JVM→WAR 전수 Mapping | server.xml/setenv/deploy inventory | Before Build Complete | YES |
| OPEN-005 | Apache/L4/GSLB 실제 Routing | httpd.conf/L4/GSLB config | Before HA Test | YES |
| OPEN-006 | Session Strategy | ADR + failover evidence | Before HA Test | YES |
| OPEN-007 | HA Topology/RTO/RPO | 승인값 + failover/failback result | Before Open | YES |
| OPEN-008 | Domain/Table/View Ownership | Data Catalog/Read-Write Matrix | Before Build Complete | YES |
| OPEN-009 | Requirement/Screen/ServiceId Traceability | UI transaction catalog + requirement mapping | Before HG90 | YES |
| OPEN-010 | OM Runtime Catalog | ServiceId/Policy/Metric/Alert Catalog | Before Open | YES |
| OPEN-011 | CI/CD Pipeline/Rollback Evidence | pipeline run + artifact/config/db compatibility | Before Open | YES |
| OPEN-012 | Migration Reconciliation/Go-NoGo | test result + rollback rehearsal | Before Cutover | YES |
| OPEN-013 | Model JSON Schema/Validator | schema + validation output | Before HG90 | YES |
| OPEN-014 | Service-to-Service Auth/Deadline | integration policy + tests | Before Security/Integration Gate | YES |
| OPEN-015 | Official Error/Timeout acceptance threshold | NFR approval | Before Performance Test | YES |
