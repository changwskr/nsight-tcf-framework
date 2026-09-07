# APPENDIX I. GAP Register — Detailed Final
## Cross-domain Architecture GAP SSOT

### I.1 Summary

- 총 GAP: **99**
- Critical 표기 GAP: **14**
- High 포함 GAP: **76**

### I.2 Domain Count

| Domain Prefix | Count |
|---|---|
| APP | 10 |
| CAP | 8 |
| DA | 5 |
| FW | 6 |
| IF | 6 |
| LT | 10 |
| MSG | 6 |
| NAM | 6 |
| NON | 6 |
| OPS | 6 |
| PHY | 6 |
| RT | 6 |
| SEC | 6 |
| TR | 6 |
| TX | 6 |

### I.3 Complete GAP Registry

| GAP ID | GAP | Severity | Closure / Next | Source |
|---|---|---|---|---|
| GAP-APP-01 | OFF Controller 5개 Service Direct | High | Facade 정렬 | 03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md |
| GAP-APP-02 | Rule Layer 전수 AS-IS 아님 | Medium | 선택기준 ADR | 03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md |
| GAP-APP-03 | UI Catalog ↔ 13 Handler Registry | High | 자동비교 | 03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md |
| GAP-APP-04 | Dispatcher ID ↔ Context ID mismatch defense | High | Framework 보완 | 03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md |
| GAP-APP-05 | 모든 Program Class Stem 전수검증 | Medium | Scanner | 03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md |
| GAP-APP-06 | pdmg-om Source/Runtime | Medium/High | Scope 확보 | 03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md |
| GAP-APP-07 | Module→Artifact→Process Mapping | High | 05/14장 | 03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md |
| GAP-APP-08 | Controller 업무선후처리 가능성 | High | 책임정리 | 03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md |
| GAP-APP-09 | TCF OFF 공통 정책 적용 차이 | High | 08/09장 | 03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md |
| GAP-APP-10 | Architecture Tests CI 적용 | High | 14/17장 | 03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md |
| GAP-CAP-01 | Session 60 vs 90 conflict | High | policy ADR | 16_PDMG_CAPACITY_PERFORMANCE_HA_DR_상세정의서_v1.md |
| GAP-CAP-02 | Final VM/server count | High | load/cost test | 16_PDMG_CAPACITY_PERFORMANCE_HA_DR_상세정의서_v1.md |
| GAP-CAP-03 | Tomcat maxThreads approval | High | load/soak | 16_PDMG_CAPACITY_PERFORMANCE_HA_DR_상세정의서_v1.md |
| GAP-CAP-04 | Hikari target size | High | DB session/capacity test | 16_PDMG_CAPACITY_PERFORMANCE_HA_DR_상세정의서_v1.md |
| GAP-CAP-05 | JVM heap approval | High | GC/soak | 16_PDMG_CAPACITY_PERFORMANCE_HA_DR_상세정의서_v1.md |
| GAP-CAP-06 | RTO/RPO | Critical | business approval | 16_PDMG_CAPACITY_PERFORMANCE_HA_DR_상세정의서_v1.md |
| GAP-CAP-07 | DR drill evidence | Critical | drill | 16_PDMG_CAPACITY_PERFORMANCE_HA_DR_상세정의서_v1.md |
| GAP-CAP-08 | Session HA final pattern | High | failover test | 16_PDMG_CAPACITY_PERFORMANCE_HA_DR_상세정의서_v1.md |
| GAP-DA-01 | 실제 Table/View 전수 Inventory 미완료 | High | Mapper/SQL scan | 07_PDMG_DATA_상세정의서_v1.md |
| GAP-DA-02 | RDW/ADW Datasource 사용현황 미확정 | High | Config/Mapper inventory | 07_PDMG_DATA_상세정의서_v1.md |
| GAP-DA-03 | Data Owner/Steward/SOR 전수 미확정 | High | Data Registry | 07_PDMG_DATA_상세정의서_v1.md |
| GAP-DA-04 | Lineage 자동화 미완료 | High | Metadata scanner | 07_PDMG_DATA_상세정의서_v1.md |
| GAP-DA-05 | DQ Rule/Runtime evidence 미완료 | Medium/High | DQ catalog/test | 07_PDMG_DATA_상세정의서_v1.md |
| GAP-FW-01 | ServiceId mismatch rejection 미확정 | High | Controller/Dispatcher validation | 08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md |
| GAP-FW-02 | Mutable ServiceContext worker 공유 | High | Immutable snapshot | 08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md |
| GAP-FW-03 | Filter/Security early error envelope | High | Common error filter | 08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md |
| GAP-FW-04 | TCF OFF timeout/control 차이 | High | Policy/adapter alignment | 08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md |
| GAP-FW-05 | STF/ETF current runtime 미연결 | Medium | Scope/ADR | 08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md |
| GAP-FW-06 | Generic exception fallback/Advice order | High | Exception test | 08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md |
| GAP-IF-01 | PDMG External Interface Inventory 미완료 | High | InterfaceId/Source/Target 전수등록 | 06_PDMG_INTERFACE_상세정의서_v1.md |
| GAP-IF-02 | InterfaceId 정확한 Enterprise 형식 미확정 | Medium | Naming ADR | 06_PDMG_INTERFACE_상세정의서_v1.md |
| GAP-IF-03 | Timeout/Retry 실제 값 전수 미확정 | High | Contract Registry | 06_PDMG_INTERFACE_상세정의서_v1.md |
| GAP-IF-04 | CDC SLA 3s vs 30s 충돌 | High | Tiered SLA ADR | 06_PDMG_INTERFACE_상세정의서_v1.md |
| GAP-IF-05 | Direct DB/DB-Link 예외 Inventory 미완료 | High | Privilege/Link Scan | 06_PDMG_INTERFACE_상세정의서_v1.md |
| GAP-IF-06 | Replay/Reconciliation Evidence 미완료 | High | Ops test | 06_PDMG_INTERFACE_상세정의서_v1.md |
| GAP-LT-01 | Access/Web Logical Role 실제 PDMG 배치 정합 | High | 05 | 04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md |
| GAP-LT-02 | JWT Key/Verifier Runtime 정합 | Critical | 11 | 04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md |
| GAP-LT-03 | RDW/ADW 실제 Datasource Mapping | High | 07 | 04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md |
| GAP-LT-04 | External Integration Node/Inventory | High | 06 | 04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md |
| GAP-LT-05 | OM Logical Node Current Detail | High | 14 | 04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md |
| GAP-LT-06 | Observability Deployment Correlation | High | 14/17 | 04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md |
| GAP-LT-07 | Logical Node→Physical Mapping | Critical | 05 | 04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md |
| GAP-LT-08 | Logical Node State/Scale/HA 속성 실증 | High | 16 | 04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md |
| GAP-LT-09 | Environment별 Node Inventory | Medium/High | 05 | 04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md |
| GAP-LT-10 | Product/Version Inventory 정합 | Medium | 05/TRM | 04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md |
| GAP-MSG-01 | Worker mutable context sharing | High | immutable snapshot | 12_PDMG_MESSAGE_CONTEXT_ERROR_LOGGING_상세정의서_v1.md |
| GAP-MSG-02 | Filter/Security standard error envelope | High | common error handler | 12_PDMG_MESSAGE_CONTEXT_ERROR_LOGGING_상세정의서_v1.md |
| GAP-MSG-03 | ServiceId mismatch rejection | High | validation rule | 12_PDMG_MESSAGE_CONTEXT_ERROR_LOGGING_상세정의서_v1.md |
| GAP-MSG-04 | Generic exception fallback/order | High | fault test | 12_PDMG_MESSAGE_CONTEXT_ERROR_LOGGING_상세정의서_v1.md |
| GAP-MSG-05 | ImageLog DDL/response field governance | Medium/High | DB/schema governance | 12_PDMG_MESSAGE_CONTEXT_ERROR_LOGGING_상세정의서_v1.md |
| GAP-MSG-06 | Deployment/Host evidence tags | High | observability integration | 12_PDMG_MESSAGE_CONTEXT_ERROR_LOGGING_상세정의서_v1.md |
| GAP-NAM-01 | UI catalog vs backend 13 ServiceIds | High | automated diff | 15_PDMG_NAMING_CODE_DEVELOPMENT_STANDARD_상세정의서_v1.md |
| GAP-NAM-02 | InterfaceId exact enterprise syntax | Medium | ADR/registry | 15_PDMG_NAMING_CODE_DEVELOPMENT_STANDARD_상세정의서_v1.md |
| GAP-NAM-03 | Event/File/Batch naming | Medium | platform standard | 15_PDMG_NAMING_CODE_DEVELOPMENT_STANDARD_상세정의서_v1.md |
| GAP-NAM-04 | Artifact/DeploymentId exact syntax | High | DevOps standard | 15_PDMG_NAMING_CODE_DEVELOPMENT_STANDARD_상세정의서_v1.md |
| GAP-NAM-05 | Hostname/JVM convention | Medium | Infra standard | 15_PDMG_NAMING_CODE_DEVELOPMENT_STANDARD_상세정의서_v1.md |
| GAP-NAM-06 | Naming scanner CI enforcement | High | pipeline gate | 15_PDMG_NAMING_CODE_DEVELOPMENT_STANDARD_상세정의서_v1.md |
| GAP-NON-01 | PDMG Event current scope | High | source/inventory scan | 13_PDMG_EVENT_CDC_ETL_BATCH_FILE_CACHE_상세정의서_v1.md |
| GAP-NON-02 | PDMG Batch current scope | High | job inventory | 13_PDMG_EVENT_CDC_ETL_BATCH_FILE_CACHE_상세정의서_v1.md |
| GAP-NON-03 | PDMG File current scope | Medium/High | interface inventory | 13_PDMG_EVENT_CDC_ETL_BATCH_FILE_CACHE_상세정의서_v1.md |
| GAP-NON-04 | Cache current scope/policy | Medium | source/config scan | 13_PDMG_EVENT_CDC_ETL_BATCH_FILE_CACHE_상세정의서_v1.md |
| GAP-NON-05 | CDC SLA conflict | High | ADR/SLA tier | 13_PDMG_EVENT_CDC_ETL_BATCH_FILE_CACHE_상세정의서_v1.md |
| GAP-NON-06 | Replay/Reconcile evidence | High | recovery tests | 13_PDMG_EVENT_CDC_ETL_BATCH_FILE_CACHE_상세정의서_v1.md |
| GAP-OPS-01 | 실제 CI pipeline inventory | High | repository/pipeline scan | 14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md |
| GAP-OPS-02 | eCAMS production job current evidence | Medium/High | deployment inventory | 14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md |
| GAP-OPS-03 | pdmg-om current implementation | High | source/runtime evidence | 14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md |
| GAP-OPS-04 | Artifact→Host/JVM/WAR trace | Critical | deployment manifest | 14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md |
| GAP-OPS-05 | metric/log/trace integrated correlation | High | observability implementation | 14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md |
| GAP-OPS-06 | restore/DR evidence | High | drill | 14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md |
| GAP-PHY-01 | PDMG Artifact→Host/JVM/WAR 실배치 미완료 | Critical | Deployment/CMDB Mapping | 05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md |
| GAP-PHY-02 | 실제 Host/VM/Port Inventory 미확보 | High | Infra Inventory | 05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md |
| GAP-PHY-03 | JVM/WAR 업무그룹 승인안 미확정 | High | Load/Failure Test + ADR | 05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md |
| GAP-PHY-04 | RTO/RPO 미확정 | High | Business DR Tier | 05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md |
| GAP-PHY-05 | DB HA/DR 제품/노드 상세 미확정 | High | DB Inventory/Test | 05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md |
| GAP-PHY-06 | Backup Restore Evidence 미확보 | High | Restore Drill | 05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md |
| GAP-RT-01 | Worker context mutable sharing | High | immutable snapshot | 09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md |
| GAP-RT-02 | JWT identity binding | Critical | principal binding | 09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md |
| GAP-RT-03 | TCF OFF facade parity | High | controller→facade | 09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md |
| GAP-RT-04 | generic/early error coverage | High | fault tests | 09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md |
| GAP-RT-05 | JDBC cancel/query timeout evidence | High | driver/query test | 09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md |
| GAP-RT-06 | Runtime→deployment correlation | High | deploymentId/host/jvm tags | 09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md |
| GAP-SEC-01 | RS256 issuer vs HMAC verifier | Critical | RS256/JWKS integration | 11_PDMG_SECURITY_SSO_JWT_SESSION_상세정의서_v1.md |
| GAP-SEC-02 | Key lifecycle/multi-instance consistency | Critical | managed key store/rotation | 11_PDMG_SECURITY_SSO_JWT_SESSION_상세정의서_v1.md |
| GAP-SEC-03 | Denylist verifier integration | High | revocation test | 11_PDMG_SECURITY_SSO_JWT_SESSION_상세정의서_v1.md |
| GAP-SEC-04 | Principal↔Business user binding | Critical | binding enforcement | 11_PDMG_SECURITY_SSO_JWT_SESSION_상세정의서_v1.md |
| GAP-SEC-05 | ServiceId authorization matrix | High | authz registry | 11_PDMG_SECURITY_SSO_JWT_SESSION_상세정의서_v1.md |
| GAP-SEC-06 | sessionStorage token exposure risk | High | client security review | 11_PDMG_SECURITY_SSO_JWT_SESSION_상세정의서_v1.md |
| GAP-TR-01 | Architecture model automation completeness | High | entity/relation registry | 17_PDMG_TRACEABILITY_PASS_GAP_ADR_상세정의서_v1.md |
| GAP-TR-02 | Source scanner CI enforcement | High | pipeline | 17_PDMG_TRACEABILITY_PASS_GAP_ADR_상세정의서_v1.md |
| GAP-TR-03 | Deployment runtime correlation | Critical | deployment manifest | 17_PDMG_TRACEABILITY_PASS_GAP_ADR_상세정의서_v1.md |
| GAP-TR-04 | Runtime evidence collector | Critical | metrics/trace/test link | 17_PDMG_TRACEABILITY_PASS_GAP_ADR_상세정의서_v1.md |
| GAP-TR-05 | UI/backend registry drift automation | High | catalog diff | 17_PDMG_TRACEABILITY_PASS_GAP_ADR_상세정의서_v1.md |
| GAP-TR-06 | Critical decision closure | Critical | ADR/gate | 17_PDMG_TRACEABILITY_PASS_GAP_ADR_상세정의서_v1.md |
| GAP-TX-01 | DB/JDBC Query Timeout exact value 미확정 | Critical | driver/config test | 10_PDMG_TRANSACTION_TIMEOUT_THREAD_DB_상세정의서_v1.md |
| GAP-TX-02 | cancel(true) DB cancel guarantee 없음 | High | statement cancel test | 10_PDMG_TRANSACTION_TIMEOUT_THREAD_DB_상세정의서_v1.md |
| GAP-TX-03 | mutable worker context | High | immutable snapshot | 10_PDMG_TRANSACTION_TIMEOUT_THREAD_DB_상세정의서_v1.md |
| GAP-TX-04 | Hikari target size 미확정 | High | load/DB test | 10_PDMG_TRANSACTION_TIMEOUT_THREAD_DB_상세정의서_v1.md |
| GAP-TX-05 | Tomcat/worker/pool capacity evidence 미완료 | High | load/soak | 10_PDMG_TRANSACTION_TIMEOUT_THREAD_DB_상세정의서_v1.md |
| GAP-TX-06 | late worker runtime evidence 미완료 | High | timeout fault test | 10_PDMG_TRANSACTION_TIMEOUT_THREAD_DB_상세정의서_v1.md |

### I.4 Critical Closure Chain

```text
Security / Identity
      ↓
Runtime / Timeout
      ↓
Physical Deployment Trace
      ↓
Data / Interface Inventory
      ↓
Operations / Evidence
      ↓
HA / DR
      ↓
G70 GAP / ADR
      ↓
G80 Approval
      ↓
HG90
```
