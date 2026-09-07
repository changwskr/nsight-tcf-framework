# PDMG 전체 아키텍처 정의서
# 16. PDMG CAPACITY / PERFORMANCE / HA / DR ARCHITECTURE
## Workload / Thread-Pool / JVM / Scale-out / Session / N+1 / DR / Evidence
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-16-CAPACITY-PERFORMANCE-HA-DR`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-16-01. 이 장의 핵심 질문

```text
PDMG가 목표 사용자/동시성/TPS/p95를 처리하려면 어떤 Capacity Chain이 필요한가?
어떤 값이 AS-IS이고 어떤 값이 Candidate인가?
노드/센터 장애와 Session/DB/Key/Artifact를 어떻게 복구할 것인가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-16-02. Evidence Flow

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
| EV-16-01 | VIII Capacity/HA/DR | user/session/thread/pool variants | [CANDIDATE/CONFLICT] |
| EV-16-02 | V Transaction/Timeout | worker20/queue100/5000 | [AS-IS] |
| EV-16-03 | HW/SW Matrix | VM candidates | [CANDIDATE] |
| EV-16-04 | Physical chapter | HA/DR mapping | [WORKING BASELINE] |
| EV-16-05 | Decision Register | sizing/HA/DR/pools | [DECISION] |

---

# 2. Figure Plan

## FIG-16-03. Top-down Drill-down

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

# 3. L0 — Capacity / Resilience Master

## FIG-16-04. L0 — Capacity / Resilience Master

```text
Users / Workload
 ↓
Concurrency
 ↓
TPS / Response Time
 ↓
Tomcat Threads
 ↓
PDMG Workers
 ↓
Hikari Connections
 ↓
DB Sessions
 ↓
CPU / Memory / IO
 ↓
Server Count / N+1
 ↓
HA / DR
```

---

# 4. Business Load Assumption

## FIG-16-05. Business Load Assumption

```text
6,000 branches
 × 6 users
 = 36,000 users

Concurrency assumption
= 10% candidate

p95 target
= 3s working target
```

---

# 5. WAS Candidate Options

## FIG-16-06. WAS Candidate Options

```text
Option A
32C / 256G × 4

Option B
16C / 128G × 8

Option C
16C / 128G ×4 ×2 groups

[CANDIDATE]
```

---

# 6. Current Worker vs Target Capacity

## FIG-16-07. Current Worker vs Target Capacity

```text
PDMG AS-IS
Worker 20
Queue 100
Deadline 5000ms

Target Capacity
≠ same numbers
 ↓
Load Test required
```

---

# 7. Tomcat Thread Candidate

## FIG-16-08. Tomcat Thread Candidate

```text
32C/256G variant
maxThreads
1200 ~ 1500
[CANDIDATE]

Busy target
≤70%
[CANDIDATE]
```

---

# 8. Hikari Candidate

## FIG-16-09. Hikari Candidate

```text
General
120 ~ 160

SV
100 ~ 120

connectionTimeout
3s candidate

maxLifetime
≤30m candidate

[VARIANT / CANDIDATE]
```

---

# 9. JVM Heap Candidate

## FIG-16-10. JVM Heap Candidate

```text
Heap
32 ~ 48 GB
G1GC
[CANDIDATE]

Heap
≠ Total VM Memory
```

---

# 10. Capacity Chain

## FIG-16-11. Capacity Chain

```text
Tomcat Busy
 ↓
Worker Active / Queue
 ↓
Hikari Active / Pending
 ↓
DB Session / SQL Wait
 ↓
CPU / IO / Lock
```

---

# 11. Saturation Cascade

## FIG-16-12. Saturation Cascade

```text
DB Slow
 ↓
Hikari Pending
 ↓
Worker Occupied
 ↓
Queue
 ↓
Request Wait
 ↓
Timeout / 504
```

---

# 12. Scale-up vs Scale-out

## FIG-16-13. Scale-up vs Scale-out

```text
Scale-up
few large VMs
 + simple
 - large failure domain

Scale-out
more medium VMs
 + isolation/N+1
 - more operations
```

---

# 13. JVM / WAR Isolation

## FIG-16-14. JVM / WAR Isolation

```text
Business Group A
 → JVM A

Business Group B
 → JVM B

Shared VM possible
but JVM failure domains separated
```

---

# 14. Session Conflict

## FIG-16-15. Session Conflict

```text
Baseline Variant A
Session 60m

Baseline Variant B
Session 90m

[CONFLICT]
Final policy required
```

---

# 15. Session HA Options

## FIG-16-16. Session HA Options

```text
Tomcat Session
 ├─ DeltaManager
 └─ Spring Session JDBC

JWT-centric
 └─ minimize HttpSession

Current final choice
= [OPEN]
```

---

# 16. Local HA

## FIG-16-17. Local HA

```text
GSLB/L4
 ↓
WEB pair/N+1
 ↓
WAS Active-Active
 ↓
DB Local HA
 ↓
node failure test
```

---

# 17. DR

## FIG-16-18. DR

```text
Main
 ↓ replication/sync
DR
 ↓
traffic switch
 ↓
application/config/key
 ↓
data
 ↓
interface/batch
 ↓
business validation
```

---

# 18. RTO / RPO

## FIG-16-19. RTO / RPO

```text
Business Criticality
 ↓
RTO / RPO
 ↓
DR Tier
 ↓
Infrastructure / Data / Key / App
 ↓
Drill Evidence

Exact values
= [OPEN]
```

---

# 19. Performance Test

## FIG-16-20. Performance Test

```text
Baseline
 ↓
Load
 ↓
Stress
 ↓
Soak
 ↓
Node failure under load
 ↓
DB/network fault
 ↓
DR / restore
 ↓
Evidence
```

---

# 20. Capacity Evidence

## FIG-16-21. Capacity Evidence

```text
assumption
 ↓
calculation
 ↓
candidate
 ↓
load test
 ↓
measured
 ↓
approved
 ↓
runtime monitoring
```

---

# 21. Architecture Rule Catalog

## FIG-16-22. Rule Set

```text
R-CAP-01
Assumption→Calculation→Candidate→Test→Measured→Approved 단계를 구분한다.

R-CAP-02
PDMG current worker values를 target capacity로 사용하지 않는다.

R-CAP-03
Tomcat/Worker/Hikari/DB pool을 end-to-end로 산정한다.

R-CAP-04
N+1 잔존용량을 검증한다.

R-CAP-05
Scale-out unit과 failure domain을 함께 결정한다.

R-CAP-06
Session 정책과 HA 전략을 일치시킨다.

R-CAP-07
Local HA와 DR을 구분한다.

R-CAP-08
RTO/RPO는 business criticality로 결정한다.

R-CAP-09
DR에는 app/config/key/interface/monitoring까지 포함한다.

R-CAP-10
Performance 결과를 runtime monitoring baseline으로 전환한다.
```

| Rule | 정의 |
|---|---|
| R-CAP-01 | Assumption→Calculation→Candidate→Test→Measured→Approved 단계를 구분한다. |
| R-CAP-02 | PDMG current worker values를 target capacity로 사용하지 않는다. |
| R-CAP-03 | Tomcat/Worker/Hikari/DB pool을 end-to-end로 산정한다. |
| R-CAP-04 | N+1 잔존용량을 검증한다. |
| R-CAP-05 | Scale-out unit과 failure domain을 함께 결정한다. |
| R-CAP-06 | Session 정책과 HA 전략을 일치시킨다. |
| R-CAP-07 | Local HA와 DR을 구분한다. |
| R-CAP-08 | RTO/RPO는 business criticality로 결정한다. |
| R-CAP-09 | DR에는 app/config/key/interface/monitoring까지 포함한다. |
| R-CAP-10 | Performance 결과를 runtime monitoring baseline으로 전환한다. |

---

# 22. Verification / Test

## FIG-16-23. Verification Flow

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
| T-CAP-01 | load/p95/TPS |
| T-CAP-02 | stress saturation point |
| T-CAP-03 | soak/GC/leak |
| T-CAP-04 | node failure under load |
| T-CAP-05 | DB failover |
| T-CAP-06 | session failover |
| T-CAP-07 | DR switch/failback |
| T-CAP-08 | restore/business validation |

---

# 23. GAP Register

## FIG-16-24. GAP Lifecycle

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
| GAP-CAP-01 | Session 60 vs 90 conflict | High | policy ADR |
| GAP-CAP-02 | Final VM/server count | High | load/cost test |
| GAP-CAP-03 | Tomcat maxThreads approval | High | load/soak |
| GAP-CAP-04 | Hikari target size | High | DB session/capacity test |
| GAP-CAP-05 | JVM heap approval | High | GC/soak |
| GAP-CAP-06 | RTO/RPO | Critical | business approval |
| GAP-CAP-07 | DR drill evidence | Critical | drill |
| GAP-CAP-08 | Session HA final pattern | High | failover test |

---

# 24. Risk Register

## FIG-16-25. Risk Propagation

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
| RISK-CAP-01 | oversized thread count | queue/DB overload |
| RISK-CAP-02 | undersized worker pool | throughput bottleneck |
| RISK-CAP-03 | session replication overhead | memory/network impact |
| RISK-CAP-04 | large failure domain | node loss overload |
| RISK-CAP-05 | untested DR | recovery failure |

---

# 25. Architecture Decision / ADR

## FIG-16-26. Decision Flow

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
| ADR-TASK-026 | WAS sizing | medium VM scale-out candidate |
| ADR-TASK-028 | JVM/WAR isolation | business groups |
| ADR-TASK-029 | Thread/Pool | end-to-end budget |
| ADR-TASK-030 | HA | active-active/N+1 |
| ADR-TASK-031 | DR | criticality tier |
| ADR-TASK-032 | DB HA/DR | local+center |
| ADR-TASK-012 | Session strategy | JWT/HttpSession decision |

---

# 26. Architecture PASS / PDMG Conformance

## FIG-16-27. PASS Model

```text
Architecture Definition
 ↓
PASS

Current Capacity/Resilience Conformance
 ↓
CONDITIONAL / OPEN

Many values are candidates or conflicting baselines,
therefore runtime performance evidence is mandatory
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Workload model | CONDITIONAL | 36k/10% assumptions |
| VM sizing | CANDIDATE | load/cost test |
| Worker snapshot | PASS AS-IS | 20/100/5000 |
| Tomcat/Hikari/JVM | CANDIDATE | performance approval |
| HA | CONDITIONAL | N+1 failure test |
| DR | OPEN/CONDITIONAL | RTO/RPO/drill |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `CONDITIONAL / OPEN`  
**Runtime Evidence Coverage:** `MEDIUM`

---

# 27. Next Chapter Handoff

## FIG-16-28. 16 → 17

```text
16 CAPACITY / HA / DR
"얼마나 처리하고 장애를 어떻게 견디는가?"
     ↓
17 TRACEABILITY / PASS / GAP / ADR
"설계와 실제 구현이 일치함을 어떻게 기계적으로 증명하고 통제하는가?" 
```

---

# 28. PDMG CAPACITY / PERFORMANCE / HA / DR ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG CAPACITY / PERFORMANCE / HA / DR ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**16장 Architecture Definition 판정: `PASS`**
