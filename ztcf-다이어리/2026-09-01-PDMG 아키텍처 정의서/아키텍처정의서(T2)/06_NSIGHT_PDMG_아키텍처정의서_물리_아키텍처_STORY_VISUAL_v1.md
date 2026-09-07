# NSIGHT PDMG 아키텍처 정의서
# 제6장. 물리 아키텍처
## Logical Node를 Center·WEB·WAS·JVM·DB로 Mapping
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **5장의 Logical Node를 실제 실행 자원과 네트워크·데이터·스토리지 구조로 내린다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제5장에서 넘어온 질문
     ↓
5장의 Logical Node를 실제 실행 자원과 네트워크·데이터·스토리지 구조로 내린다.
     ↓
이 장이 답해야 할 질문
     ↓
Logical Node를 Center·WEB·WAS·JVM·DB로 Mapping
```

이 장의 핵심 원칙은 다음과 같다.

- Server ≠ VM ≠ JVM ≠ WAR.
- 정확한 Hostname/Port/Version은 Evidence 없이는 OPEN이다.
- Capacity Candidate를 Production Fact로 승격하지 않는다.
- Logical Node는 Go-live 전에 실제 Physical Mapping을 가져야 한다.

---

# 1. Physical Architecture는 '어디에 배치되는가'를 답한다

## FIG-06-01. Physical Architecture는 '어디에 배치되는가'를 답한다

```text
Logical Node
 ↓
Environment
 ↓
Center
 ↓
Compute
 ↓
Runtime Process
 ↓
Artifact
 ↓
Network / Data / Storage
```

---

# 2. PDMG Working Physical Path

## FIG-06-02. PDMG Working Physical Path

```text
User
 ↓
GSLB
 ↓
L4
 ↓
Apache WEB
 ↓
Tomcat JVM
 ↓
PDMG WAR
 ↓
Hikari / MyBatis / JDBC
 ↓
RDW / DB
```

---

# 3. Server / VM / JVM / WAR를 분리한다

## FIG-06-03. Server / VM / JVM / WAR를 분리한다

```text
Physical Server
 ↓
VM
 ↓
OS
 ↓
Runtime Process
 ↓
JVM
 ↓
WAR
```

---

# 4. WEB와 WAS의 책임을 분리한다

## FIG-06-04. WEB와 WAS의 책임을 분리한다

```text
GSLB
 ↓
L4
 ↓
WEB / Apache
 ↓
Reverse Proxy / Routing
 ↓
WAS / Tomcat
 ↓
Application
```

---

# 5. WAS 내부도 Failure Domain을 나눌 수 있다

## FIG-06-05. WAS 내부도 Failure Domain을 나눌 수 있다

```text
WAS VM
 ├─ JVM Group A
 │   ├─ WAR A...
 │   └─ WAR A...
 └─ JVM Group B
     ├─ WAR B...
     └─ WAR B...
```

---

# 6. Data Physical Boundary

## FIG-06-06. Data Physical Boundary

```text
Application JVM
 ↓
Datasource
 ↓
Hikari
 ↓
JDBC
 ↓
DB Service
 ↓
DB Node / Storage
```

---

# 7. Network는 Port/Firewall/LB/Config가 하나의 체계다

## FIG-06-07. Network는 Port/Firewall/LB/Config가 하나의 체계다

```text
DNS/GSLB
 ↓
L4 VIP
 ↓
WEB Port
 ↓
WAS Connector
 ↓
DB Service
 ↓
Management

LB ↔ Firewall ↔ Config
```

---

# 8. 환경과 센터는 다른 축이다

## FIG-06-08. 환경과 센터는 다른 축이다

```text
Environment
DEV / TEST / PROD / DR

          ≠

Center
Main / DR Center
```

---

# 9. Capacity 값은 Candidate 상태를 구분한다

## FIG-06-09. Capacity 값은 Candidate 상태를 구분한다

```text
Candidate A
32C/256G ×4

Candidate B
16C/128G ×8

Candidate C
16C/128G ×4 ×2 groups

≠ Production Fact
```

---

# 10. Physical Traceability가 완성되어야 한다

## FIG-06-10. Physical Traceability가 완성되어야 한다

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
Evidence
```

---

# 11. Physical Architecture는 HA/DR의 기반이 된다

## FIG-06-11. Physical Architecture는 HA/DR의 기반이 된다

```text
Node
 ↓
Failure Domain
 ↓
N+1
 ↓
Local HA
 ↓
Center DR
```

---

# 12. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-06-12. Normal Pattern

```text
Logical Node
 ↓
Environment
 ↓
Center
 ↓
Host / VM
 ↓
JVM / WAR
 ↓
Network / DB / Storage
 ↓
Monitoring / Evidence
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 13. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-06-13. Forbidden Pattern

```text
Server = JVM = WAR            X
Candidate = Approved Capacity   X
Port/Hostname 추정 기입        X
Artifact와 Host 연결 없음       X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 14. Architecture Decision — WAS 배치전략

## FIG-06-14. 주안과 대안

```text
[주안]
중형 VM Scale-out + JVM/WAR 격리

        VS

[대안]
대형 VM Scale-up
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | 중형 VM Scale-out + JVM/WAR 격리 | 대형 VM Scale-up |
| 장점 | • N+1/장애격리 유리<br>• 업무그룹 분리<br>• 수평확장 | • 구성 단순<br>• 노드 수 감소 |
| 단점 | • 운영 인스턴스 증가 | • Failure Domain 확대<br>• GC/자원독점 영향 |
| 권고 | **주안 채택 — Load Test 전제** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 15. Current PDMG GAP

## FIG-06-15. GAP Map

```text
Current PDMG
│
├─ Artifact→Host/JVM/WAR 전수 Mapping 미완료
├─ 실제 Port/Firewall/Version Inventory OPEN
├─ JVM/WAR 업무그룹 최종 승인 OPEN
└─ Backup/Restore Evidence 미완료
```

- `[GAP/OPEN]` Artifact→Host/JVM/WAR 전수 Mapping 미완료
- `[GAP/OPEN]` 실제 Port/Firewall/Version Inventory OPEN
- `[GAP/OPEN]` JVM/WAR 업무그룹 최종 승인 OPEN
- `[GAP/OPEN]` Backup/Restore Evidence 미완료

---

# 16. 제6장 Architecture 판정

## FIG-06-16. Assessment

```text
Architecture Definition
      ↓
PASS

Current PDMG Conformance
      ↓
PARTIAL

Runtime Evidence
      ↓
MEDIUM
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| Physical Route | PASS/BASELINE | GSLB→L4→WEB→WAS→DB |
| Host/Port Inventory | OPEN | 실제 CMDB/Config 필요 |
| JVM/WAR Placement | CONDITIONAL | Candidate/Load Test |
| Traceability | GAP | Artifact→Host 연결 필요 |
| Backup | CONDITIONAL | Restore Evidence 필요 |

---

# 17. 원천 정의서 Trace

## FIG-06-17. Source Trace

```text
Story Chapter
   ↓
PDMG 00~18 Source
   ↓
Current Fact / Target Reference
   ↓
Architecture Rule / GAP
```

| 원천 | 용도 |
|---|---|
| 05 Physical | Story/Drill-down/Current-Target 근거 |
| 16 Capacity/HA/DR | Story/Drill-down/Current-Target 근거 |

---

# 18. 다음 장으로 넘어가는 이유

## FIG-06-18. 6장 → 7

```text
제6장
물리 아키텍처
      ↓
"이 물리구조가 노드 장애와 센터 재해를 어떻게 견딜 것인가?"
      ↓
제7장
DR 센터 활용 전략
```

---

# 19. 제6장 최종 결론

## FIG-06-19. Final Story

```text
물리 아키텍처
   ↓
Responsibility
   ↓
Boundary
   ↓
Runtime
   ↓
Evidence
   ↓
PASS
```

제6장의 결론은 **Logical Node를 Center·WEB·WAS·JVM·DB로 Mapping**라는 한 문장으로 정리된다.
