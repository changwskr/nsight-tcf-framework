# APPENDIX J. Evidence Register — Detailed Final
## Source / Config / Runtime / Deployment Evidence Index

### J.1 Evidence Priority

```text
1. Source / Config
2. Runtime / Deployment Evidence
3. PDMG Current Architecture Analysis
4. Approved ADR / PASS Register
5. Official Architecture Documents
6. Presentation / Explanatory Documents
7. Historical Standards
8. General Technical Knowledge
```

### J.2 Evidence Master

| Evidence ID | Type | Artifact / Evidence | Supports | 상태 | Domain |
|---|---|---|---|---|---|
| EVD-001 | Source/Architecture | 03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md | Module/Package/Handler Registry/TCF ON-OFF | [AS-IS REFERENCE] | Application |
| EVD-002 | Source/Runtime | NSIGHT_PDMG_아키텍처_정의서_08_PDMG_SOURCE_RUNTIME_REFERENCE_DEEP_DIVE_VISUAL_FIRST_상세본_보완개정본_v2.md | Source Tree/Runtime/Mapper/Controller Drift | [AS-IS REFERENCE] | Application/Runtime |
| EVD-003 | Runtime | NSIGHT_PDMG_아키텍처_정의서_IV_PDMG_Online_Runtime_TCF_Flow.md | TCF Online Runtime Flow | [AS-IS REFERENCE] | Mechanism/Runtime |
| EVD-004 | Runtime | NSIGHT_PDMG_아키텍처_정의서_V_Transaction_Timeout_Thread_DB_Architecture.md | Thread/Timeout/Transaction/DB | [AS-IS REFERENCE] | Runtime/Capacity |
| EVD-005 | Message/Context | NSIGHT_PDMG_아키텍처_정의서_VI_Standard_Message_Context_Error_Logging.md | hdr_nhnis/dto/result, Context, Error, ImageLog | [AS-IS REFERENCE] | Message/Operations |
| EVD-006 | Security | NSIGHT_PDMG_아키텍처_정의서_VII_Security_SSO_JWT_Session.md | SSO/JWT/Session/Security GAP | [AS-IS REFERENCE] | Security |
| EVD-007 | Infrastructure | NSIGHT_PDMG_아키텍처_정의서_VIII_Infrastructure_WAS_Capacity_HA_DR.md | WEB/WAS/Capacity/HA/DR | [WORKING REFERENCE] | Physical/HA-DR |
| EVD-008 | DevOps/Ops | NSIGHT_PDMG_아키텍처_정의서_IX_DevOps_OM_Observability.md | Build/Deploy/OM/Observability | [WORKING REFERENCE] | DevOps/Ops |
| EVD-009 | Naming/Trace | NSIGHT_PDMG_아키텍처_정의서_X_Naming_ServiceId_Traceability_Closed_Loop.md | Program/ServiceId/Traceability | [AS-IS+TARGET] | Naming/Trace |
| EVD-010 | Application Code | NSIGHT_PDMG_어플리케이션_코드_정의서_VISUAL_FIRST.md | MP/RD/AD/BI/DG/IM 50 Application codes | [BASELINE] | Application Code |
| EVD-011 | HW/SW Inventory | NSIGHT_PDMG_하드웨어_소프트웨어_매트릭스.xlsx | HW/SW/Capacity/Status Matrix | [BASELINE/CANDIDATE/OPEN] | Physical/Technical |
| EVD-012 | Decision | NSIGHT_PDMG_아키텍처_의사결정_레지스터_PASS평가_v2.xlsx | 40 ADR Tasks / PASS-Conformance | [DECISION] | Governance |
| EVD-013 | Interface | 06_PDMG_INTERFACE_상세정의서_v1.md | HTTP/JDBC current + Interface policy/GAP | [AS-IS+TARGET] | Interface |
| EVD-014 | Data | 07_PDMG_DATA_상세정의서_v1.md | RDW/ADW/Data ownership/lineage | [BASELINE/PARTIAL] | Data |
| EVD-015 | Framework | 08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md | Filter/Security/TCF/Timeout/TX/Error | [AS-IS REFERENCE] | Mechanism |
| EVD-016 | Runtime | 09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md | Request/Worker Runtime/Failure | [AS-IS REFERENCE] | Runtime |
| EVD-017 | Capacity | 16_PDMG_CAPACITY_PERFORMANCE_HA_DR_상세정의서_v1.md | Capacity candidates/HA/DR | [CANDIDATE/BASELINE] | Capacity |
| EVD-018 | Traceability | 17_PDMG_TRACEABILITY_PASS_GAP_ADR_상세정의서_v1.md | Gate/GAP/ADR/Closed Loop | [DECISION] | Governance |
| EVD-019 | Final Definition | NSIGHT_PDMG_ARCHITECTURE_BOOK_FINAL_V5.md | 13-chapter final Architecture Story/Definition | [FINAL EDITION] | All |
| EVD-020 | Config Snapshot | nhnis.fw.* current config snapshot | TCF true / timeout true / 5000ms / pool20 / queue100 / filter true | [AS-IS] | Framework Runtime |
| EVD-021 | Runtime Evidence Gap | Deployment/Runtime Evidence Collector | sourceCommit→artifactHash→deploymentId→serviceId→trace | [OPEN] | HG90 |
| EVD-022 | CMDB Gap | Host/VM/JVM/WAR Inventory | Actual production placement | [OPEN] | Physical |
| EVD-023 | Security Test Gap | JWT End-to-End Integration Test | RS256 issuer↔JWKS verifier / key rotation / DR | [OPEN] | Security |
| EVD-024 | Timeout Test Gap | JDBC Query Timeout/Cancel/Late Worker Test | HTTP 504 vs Worker/JDBC/DB behavior | [OPEN] | Runtime |
| EVD-025 | DR Test Gap | Restore/Failover/Failback Business Validation | RTO/RPO and recovery evidence | [OPEN] | HA/DR |

### J.3 Evidence Chain

```text
architectureBaselineId
 ↓
architectureModelVersion
 ↓
sourceCommit
 ↓
buildId
 ↓
artifactHash
 ↓
deploymentId
 ↓
serviceId
 ↓
traceId / GUID
 ↓
runtime evidence
```

### J.4 HG90 Required Evidence

- Source/Config Inventory freeze.
- Static Architecture Rule/Conformance Test.
- Security JWT end-to-end integration test.
- Load/Stress/Soak + Failure test.
- Artifact/Deployment→JVM/Host correlation.
- JDBC Query Timeout/Cancel/Late Worker test.
- Restore/DR Failover/Failback Business validation.
- Runtime Evidence Collector and G50 Gate.
