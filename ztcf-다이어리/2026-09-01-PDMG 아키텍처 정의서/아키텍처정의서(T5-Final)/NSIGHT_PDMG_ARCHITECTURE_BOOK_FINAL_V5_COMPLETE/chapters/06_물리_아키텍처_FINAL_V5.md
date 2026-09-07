# NSIGHT PDMG 아키텍처 정의서
# 제6장. 물리 아키텍처
## Story: “속도와 안정성을 실제 Center·VM·JVM·DB 구조로 구현한다”


---

# 0. Opening Script

논리적으로 무엇을 분리할지 정했다면 이제 실제 어디에서 실행할지 결정합니다.

물리 아키텍처의 핵심은 서버 수가 아닙니다. **Logical Node를 어떤 Center, VM, JVM, WAR에 배치하고, Network와 DB를 어디서 끊어 장애를 격리할 것인가**가 핵심입니다.

## FIG-06-01. 장 전체 Architecture

```text
User
 ↓
GSLB
 ↓
L4
 ↓
WEB / Apache
 ↓
WAS / Tomcat JVM
 ↓
PDMG WAR
 ↓
Hikari / MyBatis / JDBC
 ↓
RDW / DB
```

물리 아키텍처에서는 Logical Node를 실제 Center·VM·JVM·WAR·DB로 내립니다. 여기서 가장 중요한 원칙은 **Server, VM, JVM, WAR가 서로 다른 단위**라는 점입니다. 이 구분이 명확해야 배포, 용량, 장애격리, DR을 같은 모델에서 설명할 수 있습니다.


---

# 1. Logical→Physical Mapping

## FIG-06-02. Logical→Physical Mapping

```text
Logical Node
 ↓
Environment
 ↓
Center
 ↓
Host / VM
 ↓
JVM
 ↓
WAR
 ↓
Port / DB / Storage
```

물리 설계는 Logical Node의 구현입니다. Environment와 Center를 구분하고 그 아래 Compute/Runtime/Artifact를 배치합니다.

Server, VM, JVM, WAR는 서로 다른 Failure/Scale 단위입니다.

이 구분이 있어야 Capacity와 HA를 정확히 설계할 수 있습니다.


---

# 2. GSLB/L4/WEB/WAS 경로

## FIG-06-03. GSLB/L4/WEB/WAS 경로

```text
Client
 ↓
GSLB
 ↓
L4
 ↓
Apache WEB
 ↓
Tomcat WAS
 ↓
Application
```

사용자 요청은 여러 계층을 통과합니다. 각 계층은 다른 책임을 가집니다.

GSLB는 광역/도메인 수준, L4는 서비스 분산, WEB은 Reverse Proxy/정적처리, WAS는 Application Runtime을 담당합니다.

제품명보다 이 책임분리가 중요합니다.


---

# 3. VM/JVM/WAR 분리

## FIG-06-04. VM/JVM/WAR 분리

```text
Physical Server
 ↓
VM
 ↓
OS
 ↓
JVM
 ↓
WAR
```

이 그림은 물리 장에서 가장 중요한 구분입니다.

WAS Server라는 한 단어 안에 VM, JVM, WAR가 섞이면 장애영향과 배포단위를 설명하기 어렵습니다.

JVM 장애는 그 JVM 안의 WAR에 영향을 주지만 같은 VM의 다른 JVM까지 반드시 죽는 것은 아닙니다.


---

# 4. JVM/WAR Isolation

## FIG-06-05. JVM/WAR Isolation

```text
WAS VM
├─ JVM Group A
│  └─ WAR A...
└─ JVM Group B
   └─ WAR B...
```

업무그룹을 JVM으로 분리하면 Resource Contention과 Failure Domain을 줄일 수 있습니다.

다만 JVM 수가 늘면 운영복잡도와 Memory Overhead가 증가합니다.

따라서 Isolation은 Candidate가 아니라 부하/장애시험으로 확정해야 합니다.


---

# 5. Data Physical Path

## FIG-06-06. Data Physical Path

```text
JVM
 ↓
Hikari
 ↓
JDBC
 ↓
DB Service
 ↓
DB Node / Storage
```

Application에서 DB까지도 여러 물리경계가 있습니다.

Connection Pool은 Application Resource이고, DB Session은 Database Resource입니다. 둘을 같은 Capacity 수치로 보면 안 됩니다.

DB HA/Storage 구조는 별도 Evidence가 필요합니다.


---

# 6. Network/Firewall/Port

## FIG-06-07. Network/Firewall/Port

```text
GSLB/L4
 ↓
WEB Port
 ↓
WAS Connector
 ↓
DB Service
 ↓
Management

Firewall Rule ↔ Config ↔ Inventory
```

Physical Architecture는 Network Diagram과 Config가 일치해야 합니다.

문서에 Port를 적는 것만으로 끝나는 것이 아니라 LB, Firewall, Connector, Application Config가 같은 값을 가져야 합니다.

정확한 Port/Hostname은 실제 Inventory 없이 창작하지 않습니다.


---

# 7. Capacity Candidate

## FIG-06-08. Capacity Candidate

```text
A 32C/256G ×4
B 16C/128G ×8
C 16C/128G ×4 ×2 groups

[CANDIDATE]
```

현재 자료에는 여러 Capacity Candidate가 있습니다. 이것을 Production Fact로 쓰면 안 됩니다.

Candidate는 부하모델과 비용, N+1 잔존용량, GC/DB 영향까지 시험한 뒤 승인되어야 합니다.

따라서 문서에는 Candidate 상태를 명시합니다.


---

# 8. Physical Traceability

## FIG-06-09. Physical Traceability

```text
sourceCommit
 ↓
artifactHash
 ↓
deploymentId
 ↓
WAR
 ↓
JVM
 ↓
VM
 ↓
Host / Center
```

물리 아키텍처를 운영과 연결하려면 Deployment Trace가 필요합니다.

'어느 서버에 배포됐다'가 아니라 SourceCommit과 ArtifactHash까지 이어져야 정확한 장애분석과 Rollback이 가능합니다.

현재 이 전수 Mapping이 주요 GAP입니다.

---

# 정상패턴과 금지패턴

## FIG-06-10. Normal Pattern

```text
Logical→VM/JVM/WAR→DB→Evidence
```

정상패턴은 Logical Node를 Center→VM→JVM→WAR로 단계적으로 투영하고 배포 Artifact와 실제 Runtime을 추적하는 것입니다.

## FIG-06-11. Forbidden Pattern

```text
Server=JVM=WAR / Candidate=Fact
```

Server=VM=JVM=WAR처럼 물리단위를 한 단계로 축약하거나 Candidate Capacity를 Production Fact로 표기하는 것을 금지합니다.

---

# Architecture Decision

## FIG-06-12. 주안과 대안

```text
[주안]
중형 VM Scale-out + JVM/WAR Isolation

        VS

[대안]
대형 VM Scale-up 중심
```

Physical은 Working Baseline인 L4→Apache→Tomcat 흐름을 중심으로 하되 VM/JVM/WAR를 분리해 Scale-out과 Isolation을 설계합니다.

대형 VM Scale-up은 운영단순성이 장점이지만 Failure Domain과 GC/Resource Contention이 커질 수 있어 부하시험 없이 기본안으로 확정하지 않습니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---


# Evidence / Conformance — 이 장의 Architecture를 무엇으로 증명하는가

## Evidence Architecture

```text
물리 아키텍처 Architecture Rule
        ↓
Source Evidence
        ↓
Config Evidence
        ↓
Runtime / Deployment Evidence
        ↓
Conformance Test
        ↓
PASS / GAP / ADR
```

### Source Evidence

- `[SOURCE]` Physical/Infrastructure 상세정의
- `[SOURCE]` Capacity/HA/DR 상세정의

### Config Evidence

- `[CONFIG]` GSLB/L4/Apache/Tomcat/JDBC working baseline
- `[CONFIG]` Host/Port/Version은 Inventory Evidence 필요

### Runtime / Deployment Evidence

- `[RUNTIME]` GSLB→L4→Apache→Tomcat/JVM→WAR→DB

### Architecture Decision

- `[DECISION]` Server ≠ VM ≠ JVM ≠ WAR
- `[DECISION]` Scale-out + JVM/WAR Isolation 후보

### Related ADR

- `ADR-026 VM Scale-out Candidate`
- `ADR-027 L4→Apache→Tomcat`
- `ADR-028 JVM Isolation`
- `ADR-034 Immutable Artifact`

### Current GAP / OPEN

- `[GAP/OPEN]` Artifact→Host/JVM/WAR
- `[GAP/OPEN]` 실제 Host/Port/Version
- `[GAP/OPEN]` Placement 승인
- `[GAP/OPEN]` Restore Evidence

## 이 장의 판정

```text
Architecture Definition
        ↓
PASS

Current PDMG Conformance
        ↓
PARTIAL / OPEN

Runtime Evidence Coverage
        ↓
MEDIUM

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `CMDB/Host/JVM/WAR Inventory`
- `Artifact Deployment Trace`
- `Capacity/Failure Test`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-06-16. 다음 장 Handoff

```text
물리 아키텍처
 ↓
속도와 안정성을 실제 Center·VM·JVM·DB 구조로 구현한다
 ↓
남은 질문
"완벽해 보이는 구조보다 운영 가능한 복구구조를 선택한다"
 ↓
DR 센터 활용 전략
```

Physical 배치가 끝나면 장애가 발생했을 때 어떤 자원이 함께 실패하고 어떤 경로로 우회할지가 보입니다. 다음 장에서는 이 Failure Domain을 Local HA와 Center DR 전략으로 연결합니다.
