# PDMG 전체 아키텍처 정의서
# 05. PDMG PHYSICAL / INFRASTRUCTURE ARCHITECTURE
## Center / Compute / Network / WEB-WAS / DB / Storage / HA-DR / Evidence
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-05-PHYSICAL-INFRASTRUCTURE`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-05-01. 이 장의 핵심 질문

```text
4장의 Logical Node를 실제 어떤 Center / Host / VM / JVM / WAR / DB / Network / Storage로 구현할 것인가?
Logical과 Physical의 경계를 어떻게 유지할 것인가?
실제 PDMG 배치와 아직 OPEN인 영역은 무엇인가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-05-02. Evidence Flow

```text
Source / Config
   ↓
Runtime / Deployment
   ↓
PDMG Current Analysis
   ↓
Architecture Decision
   ↓
NSIGHT Target / Working Baseline
   ↓
PASS / GAP / OPEN
```

| Evidence ID | 근거 | 사용목적 | 상태 |
|---|---|---|---|
| EV-05-01 | 04 Logical Technical | Logical Node→Physical Handoff | [WORKING BASELINE] |
| EV-05-02 | VIII Infrastructure/WAS/Capacity/HA/DR | Physical path, capacity, HA/DR | [PHYSICAL REFERENCE] |
| EV-05-03 | Infrastructure Appendix C | Center/Compute/Network/Storage | [TARGET REFERENCE] |
| EV-05-04 | HW/SW Matrix | HW/SW roles and candidates | [WORKING INVENTORY] |
| EV-05-05 | PDMG Runtime | JVM/WAR/Data access context | [AS-IS REFERENCE] |

---

# 2. Figure Plan

## FIG-05-03. Top-down Drill-down

```text
L0  Overall
 ↓
L1  Boundary / Responsibility
 ↓
L2  Node / Platform / Component
 ↓
L3  Runtime / Control / Data
 ↓
L4  Sequence / Failure / Recovery
 ↓
L5  Source / Config / Evidence
```

---

# 3. L0 — PDMG Physical Master Architecture

## FIG-05-04. L0 — PDMG Physical Master Architecture

```text
User / Browser
   ↓
GSLB [Working Baseline]
   ↓
L4 [Working Baseline]
   ↓
WEB / Apache
   ↓
WAS / Tomcat JVM
   ↓
PDMG WAR / Runtime
   ├─ pdmg-service
   └─ pdmg-fw
   ↓
Hikari / MyBatis / JDBC
   ↓
RDW / DB

Authentication Runtime
pdmg-jwt
   ↓
Token / JWKS
   ↓
PDMG Business Runtime

Operations / Monitoring / Backup
= Physical Mapping OPEN / Target Reference
```

이 경로는 PDMG/NSIGHT의 Working Physical Baseline이다. 실제 Hostname, VM 수, Port, 제품 Version은 Inventory Evidence가 없으면 `[OPEN]`으로 유지한다.

---

# 4. Logical Node → Physical Resource Mapping

## FIG-05-05. Logical Node → Physical Resource Mapping

```text
LTN-PD-01 UI Delivery
   ↓
WEB/UI Runtime Resource

LTN-PD-02 Authentication
   ↓
JWT Runtime Resource

LTN-PD-03 Application Runtime
   ↓
WAS VM / JVM / WAR

LTN-PD-04 Data Service
   ↓
DB Service / DB Cluster

LTN-PD-05 Integration
   ↓
Integration Platform [CONDITIONAL]

LTN-PD-06 Operations
   ↓
Monitoring / Control [OPEN]
```

---

# 5. Server / VM / JVM / WAR Boundary

## FIG-05-06. Server / VM / JVM / WAR Boundary

```text
Physical Server / Hypervisor
   ↓
VM
   ↓
OS
   ↓
Runtime Process
   ↓
JVM
   ↓
WAR / Application Artifact

Server ≠ VM ≠ JVM ≠ WAR
```

PDMG Module 이름을 서버명으로 자동 변환하지 않는다. 하나의 VM에 여러 JVM이 있을 수 있고, 하나의 JVM에 여러 WAR이 있을 수 있다.

---

# 6. Environment / Center Axis

## FIG-05-07. Environment / Center Axis

```text
Development
Test
Production
DR
   │
   └─ Deployment Environment

Main Center [의왕 Working Reference]
DR Center   [안성 Working Reference]

Center ≠ Environment
```

의왕 Main/안성 DR은 현재 Physical Reference의 Working Baseline이다. 실제 PDMG 배치 Host/VM Inventory는 별도 확인한다.

---

# 7. WEB Layer Architecture

## FIG-05-08. WEB Layer Architecture

```text
GSLB
 ↓
L4
 ↓
WEB VM
 ↓
Apache Instance
 ↓
Reverse Proxy / Routing
 ↓
Tomcat Connector

Apache Instance ≠ WEB VM
```

---

# 8. WAS / JVM / WAR Architecture

## FIG-05-09. WAS / JVM / WAR Architecture

```text
WAS VM
 ├─ JVM Group A
 │   ├─ WAR ...
 │   └─ WAR ...
 └─ JVM Group B
     ├─ WAR ...
     └─ WAR ...

JVM Group / WAR placement
= [OPEN / Candidate]
```

업무그룹 A/B 분리는 장애영향과 자원독점을 줄이는 Target 후보이며 실제 17 WAR 배치표는 Deployment Inventory로 확정해야 한다.

---

# 9. Data / DB Physical Boundary

## FIG-05-10. Data / DB Physical Boundary

```text
PDMG JVM
  ↓ Datasource
Hikari Pool
  ↓ JDBC
DB Service
  ↓
DB Cluster / Node
  ↓
Storage

RDW = Operational / Near-real-time
ADW = Analytical / Mart [Target Reference]
```

---

# 10. Network / Port / Firewall

## FIG-05-11. Network / Port / Firewall

```text
Client
 ↓
DNS/GSLB
 ↓
L4 VIP
 ↓
WEB Port
 ↓
WAS Connector Port
 ↓
DB Service Port
 ↓
Management / Backup Network

Port Inventory ↔ Firewall ↔ LB ↔ Config
```

정확한 Port 값은 현재 정의서에서 임의 생성하지 않는다.

---

# 11. Storage / Filesystem

## FIG-05-12. Storage / Filesystem

```text
OS Filesystem
 ├─ App / Runtime
 ├─ Log
 ├─ Temp
 ├─ Artifact
 └─ Config

Data Storage
 ├─ DB Data
 ├─ Archive
 └─ Backup
```

---

# 12. Security Infrastructure

## FIG-05-13. Security Infrastructure

```text
External / User Zone
  ↓
Traffic / Web Boundary
  ↓
Protected WAS Zone
  ↓
Data Zone
  ↓
Management Zone

Key / Secret
= Runtime Artifact와 분리
```

---

# 13. Monitoring / Backup Physical Handoff

## FIG-05-14. Monitoring / Backup Physical Handoff

```text
WEB / WAS / JWT / DB
   ↓
Agent / Exporter / Log
   ↓
Monitoring / Logging
   ↓
Alert / Dashboard

DB / Config / File
   ↓
Backup
   ↓
Restore Test
```

---

# 14. HA — Main Center

## FIG-05-15. HA — Main Center

```text
GSLB / L4
 ↓
WEB N+1 / Pair
 ↓
WAS Active-Active / N+1 candidate
 ↓
DB Local HA
 ↓
Residual Capacity Validation
```

---

# 15. DR — Center Failure

## FIG-05-16. DR — Center Failure

```text
Main Center
  ↓ detect
Traffic Reroute
  ↓
DR Access
  ↓
DR WEB/WAS
  ↓
DR DB / Replication
  ↓
Config / Key / Artifact
  ↓
Business Validation
```

RTO/RPO는 현재 `[OPEN]`. Backup 성공만으로 DR PASS로 보지 않는다.

---

# 16. Current Capacity Projection

## FIG-05-17. Current Capacity Projection

```text
Candidate A
32C / 256G × 4

Candidate B
16C / 128G × 8

Candidate C
16C / 128G × 4 × 2 groups

All = [CANDIDATE]
```

---

# 17. Physical Traceability

## FIG-05-18. Physical Traceability

```text
Application
 ↓
Artifact
 ↓
DeploymentId
 ↓
WAR
 ↓
JVM
 ↓
VM
 ↓
Host
 ↓
Center
 ↓
Metric / Evidence
```

현재 PDMG의 실제 Artifact→Host/JVM/WAR 전수 Mapping은 핵심 GAP다.

---

# 18. Architecture Rule Catalog

## FIG-05-19. Rule Set

```text
R-PHY-01
Server ≠ VM ≠ JVM ≠ WAR

R-PHY-02
Logical Node는 실제 Physical Mapping을 가져야 한다.

R-PHY-03
미확정 Host/Port/Version은 OPEN으로 유지한다.

R-PHY-04
WEB와 WAS 책임을 분리한다.

R-PHY-05
WAS Scale-out 시 N+1 잔존용량을 검증한다.

R-PHY-06
DB Local HA와 Center DR을 구분한다.

R-PHY-07
Backup 성공 ≠ Restore/Business Recovery 성공.

R-PHY-08
Key/Secret은 Artifact 일반 Config와 분리한다.

R-PHY-09
Port/Firewall/LB/Config는 상호 정합해야 한다.

R-PHY-10
Capacity Candidate를 Production Fact로 표기하지 않는다.
```

| Rule | 정의 |
|---|---|
| R-PHY-01 | Server ≠ VM ≠ JVM ≠ WAR |
| R-PHY-02 | Logical Node는 실제 Physical Mapping을 가져야 한다. |
| R-PHY-03 | 미확정 Host/Port/Version은 OPEN으로 유지한다. |
| R-PHY-04 | WEB와 WAS 책임을 분리한다. |
| R-PHY-05 | WAS Scale-out 시 N+1 잔존용량을 검증한다. |
| R-PHY-06 | DB Local HA와 Center DR을 구분한다. |
| R-PHY-07 | Backup 성공 ≠ Restore/Business Recovery 성공. |
| R-PHY-08 | Key/Secret은 Artifact 일반 Config와 분리한다. |
| R-PHY-09 | Port/Firewall/LB/Config는 상호 정합해야 한다. |
| R-PHY-10 | Capacity Candidate를 Production Fact로 표기하지 않는다. |

---

# 19. Verification / Test

## FIG-05-20. Verification Flow

```text
Architecture Model
   ↓
Static / Config Check
   ↓
Integration Test
   ↓
Failure / Security / Performance Test
   ↓
Runtime Evidence
   ↓
PASS / GAP
```

| Test ID | 검증내용 |
|---|---|
| T-PHY-01 | Logical Node→Host/VM/JVM/WAR Mapping |
| T-PHY-02 | L4/WEB/WAS/DB Connectivity |
| T-PHY-03 | Port/Firewall/LB Config consistency |
| T-PHY-04 | Node failure / N+1 capacity |
| T-PHY-05 | DB failover |
| T-PHY-06 | DR switchover / failback |
| T-PHY-07 | Backup restore / business validation |

---

# 20. GAP Register

## FIG-05-21. GAP Lifecycle

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

| GAP ID | GAP | 중요도 | 전환조건 |
|---|---|---|---|
| GAP-PHY-01 | PDMG Artifact→Host/JVM/WAR 실배치 미완료 | Critical | Deployment/CMDB Mapping |
| GAP-PHY-02 | 실제 Host/VM/Port Inventory 미확보 | High | Infra Inventory |
| GAP-PHY-03 | JVM/WAR 업무그룹 승인안 미확정 | High | Load/Failure Test + ADR |
| GAP-PHY-04 | RTO/RPO 미확정 | High | Business DR Tier |
| GAP-PHY-05 | DB HA/DR 제품/노드 상세 미확정 | High | DB Inventory/Test |
| GAP-PHY-06 | Backup Restore Evidence 미확보 | High | Restore Drill |

---

# 21. Risk Register

## FIG-05-22. Risk Propagation

```text
Cause
  ↓
Technical Failure
  ↓
Service Impact
  ↓
Operational / Business Impact
```

| Risk ID | Risk | 영향 |
|---|---|---|
| RISK-PHY-01 | 대형 JVM/다수 WAR 자원독점 | GC/장애영향 확대 |
| RISK-PHY-02 | Port/Firewall/Config Drift | Connectivity 장애 |
| RISK-PHY-03 | DR Artifact/Key Drift | DR 전환 실패 |
| RISK-PHY-04 | N+1 잔존용량 미검증 | 노드 장애 시 과부하 |
| RISK-PHY-05 | Backup만 성공하고 Restore 미검증 | 실제 복구 불가 |

---

# 22. Architecture Decision / ADR

## FIG-05-23. Decision Flow

```text
Decision Question
   ↓
주안 / 대안
   ↓
장점 / 단점
   ↓
Evidence / Test
   ↓
ADR
   ↓
Baseline
```

| ADR/Task | 의사결정 주제 | 현재 방향 |
|---|---|---|
| ADR-TASK-026 | WAS Compute Sizing | 중형 VM Scale-out 주안 / Load Test 전제 |
| ADR-TASK-027 | WEB/WAS Topology | GSLB→L4→Apache→Tomcat |
| ADR-TASK-028 | JVM/WAR Isolation | 업무그룹별 격리 주안 |
| ADR-TASK-030 | HA Pattern | Stateless Active-Active/N+1 |
| ADR-TASK-031 | DR Model | Critical 서비스 Warm/Hot 차등 |
| ADR-TASK-032 | DB HA/DR | Local HA + Center DR |

---

# 23. Architecture PASS / PDMG Conformance

## FIG-05-24. PASS Model

```text
Architecture Definition
  ↓
PASS

Current Physical Conformance
  ↓
PARTIAL / CONDITIONAL

Critical
Artifact→Host/JVM/WAR Mapping
RTO/RPO / DR Evidence
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Logical→Physical | PASS | Mapping model defined |
| Online Physical Path | PASS/BASELINE | GSLB→L4→WEB→WAS→DB |
| Actual Inventory | OPEN | Host/Port/Version |
| HA | CONDITIONAL | Failure/N+1 test needed |
| DR | CONDITIONAL | RTO/RPO and drill needed |
| Backup/Restore | CONDITIONAL | Restore evidence needed |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PARTIAL / CONDITIONAL`  
**Runtime Evidence Coverage:** `MEDIUM`

---

# 24. Next Chapter Handoff

## FIG-05-25. 05 → 06

```text
05 PHYSICAL
"어디에 배치하는가?"
     ↓
06 INTERFACE
"그 배치된 Application이 외부/내부 시스템과 어떤 Contract로 연결되는가?" 
```

---

# 25. PDMG PHYSICAL / INFRASTRUCTURE ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG PHYSICAL / INFRASTRUCTURE ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**05장 Architecture Definition 판정: `PASS`**
