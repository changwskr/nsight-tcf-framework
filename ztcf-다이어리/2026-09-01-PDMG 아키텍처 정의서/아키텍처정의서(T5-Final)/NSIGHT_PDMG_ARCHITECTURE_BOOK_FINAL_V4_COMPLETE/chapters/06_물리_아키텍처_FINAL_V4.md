# NSIGHT PDMG 아키텍처 정의서
# 제6장. 물리 아키텍처
## Story: “속도와 안정성을 실제 Center·VM·JVM·DB 구조로 구현한다”
## STORY-FIRST / TEXT-ARCHITECTURE-FIRST / TOP-DOWN → DRILL-DOWN / EVIDENCE-FIRST

> 최종 문서 Edition: `FINAL V4 — Story + Architecture + Drill-down + Evidence in Chapter`  
> 기준일: `2026-09-01`  
> PDMG = Current / Source / Config / Runtime  
> NSIGHT = Target / Alignment / Strategy Reference  
> 문서 상태: **FINAL V4 WORKING BASELINE** / Evidence는 본 장 내부에서 Architecture와 함께 판정

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

이제 이 전체 그림을 위에서 아래로 해부하겠습니다. 이번 버전에서는 그림의 박스와 화살표를 직접 설명하고, 반복적인 형식문장은 최소화합니다. 각 Drill-down은 상위 그림의 어느 부분을 확대하는지 명확하게 연결합니다.

## FIG-06-02. Drill-down Route

```text
L0 전체 Story
 ↓
L1 책임 / Boundary
 ↓
L2 Logical / Application / Platform
 ↓
L3 Component / Contract
 ↓
L4 Runtime / Failure / Security
 ↓
L5 Source / Config / Deployment / Evidence
```

---

# 1. Logical→Physical Mapping

## FIG-06-03. Logical→Physical Mapping

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

여기까지가 `Logical→Physical Mapping`의 역할입니다. 이제 이 구조를 더 내려가 **GSLB/L4/WEB/WAS 경로**에서 다음 경계와 실행책임을 보겠습니다.

---

# 2. GSLB/L4/WEB/WAS 경로

## FIG-06-04. GSLB/L4/WEB/WAS 경로

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

여기까지가 `GSLB/L4/WEB/WAS 경로`의 역할입니다. 이제 이 구조를 더 내려가 **VM/JVM/WAR 분리**에서 다음 경계와 실행책임을 보겠습니다.

---

# 3. VM/JVM/WAR 분리

## FIG-06-05. VM/JVM/WAR 분리

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

여기까지가 `VM/JVM/WAR 분리`의 역할입니다. 이제 이 구조를 더 내려가 **JVM/WAR Isolation**에서 다음 경계와 실행책임을 보겠습니다.

---

# 4. JVM/WAR Isolation

## FIG-06-06. JVM/WAR Isolation

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

여기까지가 `JVM/WAR Isolation`의 역할입니다. 이제 이 구조를 더 내려가 **Data Physical Path**에서 다음 경계와 실행책임을 보겠습니다.

---

# 5. Data Physical Path

## FIG-06-07. Data Physical Path

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

여기까지가 `Data Physical Path`의 역할입니다. 이제 이 구조를 더 내려가 **Network/Firewall/Port**에서 다음 경계와 실행책임을 보겠습니다.

---

# 6. Network/Firewall/Port

## FIG-06-08. Network/Firewall/Port

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

여기까지가 `Network/Firewall/Port`의 역할입니다. 이제 이 구조를 더 내려가 **Capacity Candidate**에서 다음 경계와 실행책임을 보겠습니다.

---

# 7. Capacity Candidate

## FIG-06-09. Capacity Candidate

```text
A 32C/256G ×4
B 16C/128G ×8
C 16C/128G ×4 ×2 groups

[CANDIDATE]
```

현재 자료에는 여러 Capacity Candidate가 있습니다. 이것을 Production Fact로 쓰면 안 됩니다.

Candidate는 부하모델과 비용, N+1 잔존용량, GC/DB 영향까지 시험한 뒤 승인되어야 합니다.

따라서 문서에는 Candidate 상태를 명시합니다.

여기까지가 `Capacity Candidate`의 역할입니다. 이제 이 구조를 더 내려가 **Physical Traceability**에서 다음 경계와 실행책임을 보겠습니다.

---

# 8. Physical Traceability

## FIG-06-10. Physical Traceability

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

## FIG-06-11. Normal Pattern

```text
Logical→VM/JVM/WAR→DB→Evidence
```

정상패턴은 각 영역이 자신의 책임을 유지하면서 명확한 Contract와 Runtime Boundary를 통해 연결되는 구조입니다. 변경·장애·보안·운영 책임이 이 경계를 따라 추적될 수 있어야 합니다.

## FIG-06-12. Forbidden Pattern

```text
Server=JVM=WAR / Candidate=Fact
```

금지패턴은 기술적으로 불가능해서가 아니라 Architecture의 책임과 Evidence Chain을 무너뜨리기 때문에 제한합니다. 예외가 필요하면 묵시적으로 허용하지 않고 ADR와 Test Evidence로 승인합니다.

---

# Architecture Decision

## FIG-06-13. 주안과 대안

```text
[주안]
중형 VM Scale-out + JVM/WAR Isolation

        VS

[대안]
대형 VM Scale-up 중심
```

이번 장의 주안은 **중형 VM Scale-out + JVM/WAR Isolation**입니다. 이 방향은 현재 확인된 PDMG 구조와 NSIGHT Target을 연결하면서 책임·운영·Evidence를 가장 일관되게 유지할 수 있는 선택입니다.

대안인 **대형 VM Scale-up 중심**도 특정 조건에서는 사용할 수 있습니다. 다만 대안을 선택하려면 주안보다 나은 성능·가용성·비용 또는 운영효과가 PoC/Runtime Test로 확인되어야 하고, 그 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---

# Current GAP / PASS

## FIG-06-14. Current GAP

```text
Current
│
├─ artifact→host mapping
├─ host/port/version inventory
├─ jvm/war approval
└─ rto/rpo evidence
```

- `[GAP/OPEN]` artifact→host mapping
- `[GAP/OPEN]` host/port/version inventory
- `[GAP/OPEN]` jvm/war approval
- `[GAP/OPEN]` rto/rpo evidence

## FIG-06-15. Architecture Assessment

```text
Architecture Definition
 ↓
PASS

Current PDMG Conformance
 ↓
PARTIAL / OPEN

Runtime Evidence
 ↓
MEDIUM

Architecture PASS
 ≠
Implementation PASS
```

Architecture가 잘 정의되었다는 것과 현재 구현이 그 정의를 지킨다는 것은 별도 판단입니다. 이 문서는 둘을 분리해 평가하며, `[OPEN]`과 `[UNKNOWN]`을 임의로 채우지 않습니다.

---

# Evidence Card

## FIG-06-16. Evidence Chain

```text
Architecture Rule
 ↓
Source / Config
 ↓
Build / Artifact
 ↓
Deployment
 ↓
ServiceId / GUID
 ↓
Metric / Log / Trace / Test
 ↓
Runtime Evidence
 ↓
PASS / GAP / ADR
```

본문에서는 Story와 Architecture 설명을 우선하고, Evidence는 이 카드에서 정리합니다. 앞으로 자동화 단계에서는 이 Chain을 Manifest/Registry로 기계적으로 생성하는 것이 목표입니다.

---


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

이 장의 정의가 완료되었다고 해서 현재 구현이 자동으로 PASS가 되는 것은 아니다. Source·Config·Runtime Evidence가 실제 Architecture Rule과 정합할 때 Current Conformance를 PASS로 승격한다.

---
# Chapter Closing Script

## FIG-06-17. 다음 장 Handoff

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

여기까지가 **물리 아키텍처**입니다. 이 장에서 중요한 것은 개별 기술을 많이 보여준 것이 아니라, 전체 그림을 시작점으로 책임과 Runtime을 하나씩 내려가며 설명했다는 점입니다.

이제 자연스럽게 다음 질문이 생깁니다. **완벽해 보이는 구조보다 운영 가능한 복구구조를 선택한다**. 그 질문이 다음 단계인 **DR 센터 활용 전략**의 출발점입니다.
