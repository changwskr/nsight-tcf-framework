# NSIGHT / PDMG 아키텍처 정의서
# 04. PHYSICAL — Center / Network / WEB / WAS / JVM / DB / HA / DR / Capacity
## Visual-First / Top-down / Drill-down 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-VISUAL-PHYSICAL-04`  
> Architecture Level: **PHYSICAL / L3**  
> 문서 상태: **Draft / Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 작성 원칙: **Logical Node → Physical Resource → Capacity → HA/DR → Evidence**  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_03_LOGICAL_VISUAL_FIRST_TOPDOWN_상세본.md`  
> 후속 장: **05. MECHANISM**

---

# 0. 이 문서를 읽는 방법

PHYSICAL은 LOGICAL에서 정한 책임과 분리 원칙을
실제 실행 자원으로 Mapping하는 장이다.

```text
LOGICAL
Zone
  ↓
Logical System
  ↓
Logical Node
  ↓
Failure Domain
  ↓
Scale Characteristic
       │
       ▼
PHYSICAL
Center
  ↓
Network
  ↓
GSLB / L4
  ↓
WEB
  ↓
WAS
  ↓
JVM
  ↓
WAR
  ↓
Thread / Pool
  ↓
DB / Storage
  ↓
HA / DR
```

이 장에서 반드시 구분해야 한다.

```text
Physical Server / VM
≠
Tomcat JVM

Tomcat JVM
≠
WAR

WAR
≠
Module

WEB Node
≠
Apache Instance

WAS Node
≠
Tomcat Process 수

Capacity Candidate
≠
Actual Runtime Config

HA
≠
DR
≠
Backup
```

---

# 1. VISUAL ROUTE — PHYSICAL 전체를 한 장으로 보기

## FIG-PH-01. Physical Architecture Journey

```text
╔══════════════════════════════════════════════════════════════════════════════╗
║                           PHYSICAL ARCHITECTURE                              ║
╚══════════════════════════════════════════════════════════════════════════════╝

 LOGICAL NODE
    │
    ▼
 ① CENTER
    │
    ├─ Main
    └─ DR
    │
    ▼
 ② NETWORK / TRAFFIC
    │
    ├─ GSLB
    ├─ L4
    └─ WEB Entry
    │
    ▼
 ③ WEB / WAS
    │
    ├─ Apache [Working Baseline]
    ├─ Tomcat JVM
    └─ WAR
    │
    ▼
 ④ EXECUTION RESOURCE
    │
    ├─ Request Thread
    ├─ Worker Thread
    └─ Hikari Pool
    │
    ▼
 ⑤ DATA PLATFORM
    │
    ├─ RDW
    ├─ ADW
    ├─ CDC
    └─ ETL
    │
    ▼
 ⑥ CAPACITY / HA / DR
    │
    ├─ Scale-up / Scale-out
    ├─ Session
    ├─ Node HA
    └─ Center DR
    │
    ▼
 ⑦ PHYSICAL EVIDENCE
      Inventory / Config / Runtime Metric / DR Test
```

### 이 그림에서 봐야 할 것

- PHYSICAL은 “서버를 나열하는 장”이 아니다.
- LOGICAL에서 정의한 **분리·확장·장애경계**가 실제 배치에서도 유지되는지 검증하는 장이다.
- 수치와 제품명은 **근거 시점과 상태를 함께 표시**해야 한다.

---

# 2. LOGICAL에서 전달받은 계약

## FIG-PH-02. LOGICAL → PHYSICAL Input

```text
LOGICAL
│
├─ Zone
├─ Logical System
├─ Logical Node
├─ Runtime Type
├─ Layer
├─ Allowed / Forbidden Connection
├─ Failure Domain
├─ Scale Characteristic
├─ Environment Scope
└─ PDMG Reference Position
      │
      ▼
PHYSICAL
│
├─ Center
├─ Host / VM
├─ Network Path
├─ Middleware
├─ JVM
├─ WAR
├─ CPU / Memory
├─ Thread / Pool
├─ DB / Storage
├─ HA Pair
└─ DR Pair
```

---

# 3. PHYSICAL 핵심 결론

## FIG-PH-03. Physical Decision Principle

```text
Logical Responsibility
       │
       ▼
Logical Node
       │
       ▼
Physical Group
       │
       ├─ Host / VM
       ├─ Middleware
       ├─ JVM
       ├─ WAR
       ├─ DB
       ├─ Network
       └─ HA/DR
```

### 한 줄 결론

> **PHYSICAL의 핵심은 “어떤 책임을 어떤 물리 자원에 배치하고, 장애가 나도 그 책임이 유지되게 하는가”다.**

---

# 4. Center Architecture

## FIG-PH-04. Main / DR Center Topology

```text
┌──────────────────────────── 의왕 센터 ────────────────────────────┐
│                            [MAIN]                                 │
│                                                                   │
│ WEB / WAS / AP / DB / ETL / CDC / BI / Governance / Operations  │
│                                                                   │
└──────────────────────────────┬────────────────────────────────────┘
                               │
                         Internal GSLB
                               │
┌──────────────────────────────▼────────────────────────────────────┐
│                            안성 센터                              │
│                            [DR]                                  │
│                                                                   │
│ DR WEB / WAS / AP / DB / Supporting Roles                       │
│                                                                   │
└───────────────────────────────────────────────────────────────────┘
```

### 해설

- 현재 물리 자료의 기본 방향은 **의왕 주센터 / 안성 DR센터**다.
- GSLB를 통한 센터 전환 구조가 상위 Physical Baseline으로 사용된다.
- 실제 RTO/RPO/자동전환 상세는 별도 승인 Evidence로 닫아야 한다.

---

# 5. Center Role

## FIG-PH-05. Center Responsibility

```text
MAIN
│
├─ Normal Production Traffic
├─ Primary Application Runtime
├─ Primary Data Runtime
├─ Main Operations
└─ Primary Integration

DR
│
├─ Disaster Recovery
├─ Standby / Alternate Runtime
├─ Alternate Data Role
├─ DR Integration
└─ Recovery Operations
```

### 주의

```text
DR Server 존재
=
DR 준비 완료

X
```

필요:

```text
Artifact Sync
Config Sync
Key Sync
DB Replication
Route
Runbook
Test Evidence
```

---

# 6. Enterprise Traffic Path

## FIG-PH-06. GSLB → L4 → WEB → WAS

```text
User / Client
     │
     ▼
    GSLB
     │
     ▼
     L4
     │
     ▼
┌────────────── WEB Layer ──────────────┐
│ WEB Node #1                           │
│ WEB Node #2                           │
│                                      │
│ Apache [Working Baseline]             │
└────────────────┬──────────────────────┘
                 │ Reverse Proxy
                 ▼
┌────────────── WAS Layer ──────────────┐
│ WAS Node #1                           │
│ WAS Node #2                           │
│                                      │
│ Tomcat JVM                            │
└────────────────┬──────────────────────┘
                 ▼
              Business WAR
                 │
                 ▼
              HikariCP
                 │
                 ▼
             RDW / DB
```

### 상태

```text
GSLB → L4 → Apache → Tomcat → WAR → Hikari/MyBatis → DB
= NSIGHT Working Baseline
```

노드별 실제 제품/Version/Port는 Inventory/Config로 재확정한다.

---

# 7. Traffic Layer Responsibility

## FIG-PH-07. Traffic Responsibility

```text
GSLB
= Center / Site Routing

L4
= Service VIP / Member Distribution

WEB
= HTTP Entry / Reverse Proxy / Static / Routing

WAS
= Application Runtime

DB
= Data Runtime
```

### 금지 해석

```text
GSLB = Application Router     X
L4   = Business Dispatcher    X
WEB  = Business Service       X
```

---

# 8. WEB Layer

## FIG-PH-08. WEB Physical Role

```text
┌──────────────────────── WEB NODE ────────────────────────┐
│                                                         │
│ OS                                                      │
│  │                                                      │
│  └─ WEB Server Instance                                 │
│      ├─ Listen                                          │
│      ├─ VirtualHost                                     │
│      ├─ Proxy                                           │
│      ├─ Health                                          │
│      └─ Access Log                                      │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

### Working Standard

```text
Apache
```

### 하지만

```text
WEB Node 존재
=
해당 노드 Apache 설치 확정

이라고 자동 판단하지 않는다.
```

실제 설치 Software는 Server Inventory로 확인한다.

---

# 9. Apache Instance vs WEB VM

## FIG-PH-09. WEB Instance Boundary

```text
WEB VM
┌──────────────────────────────────────┐
│ OS                                   │
│                                      │
│ Apache Instance #1                   │
│   ├─ Listen 1                        │
│   ├─ Listen 2                        │
│   └─ VirtualHost                     │
│                                      │
│ Apache Instance #2 [가능 구조]       │
│   └─ 별도 Config / Process           │
│                                      │
└──────────────────────────────────────┘
```

### 핵심

```text
WEB VM 수
≠
Apache Instance 수
≠
Listen Port 수
```

---

# 10. WAS Layer

## FIG-PH-10. WAS Physical Role

```text
┌──────────────────────── WAS NODE ──────────────────────────┐
│                                                          │
│ OS                                                       │
│  │                                                       │
│  └─ Tomcat JVM                                          │
│      │                                                   │
│      ├─ Spring ApplicationContext                        │
│      ├─ Business WAR                                     │
│      ├─ Framework Library                                │
│      ├─ Thread Pool                                      │
│      ├─ DataSource / HikariCP                            │
│      └─ Logging / Monitoring                             │
│                                                          │
└──────────────────────────────────────────────────────────┘
```

---

# 11. Server / JVM / WAR Boundary

## FIG-PH-11. Three Physical Execution Boundaries

```text
Physical Server / VM
┌───────────────────────────────────────────────┐
│                                               │
│ Tomcat JVM #1                                 │
│ ┌───────────────────────────────────────────┐ │
│ │ Heap / Metaspace / Thread                │ │
│ │                                           │ │
│ │ WAR A                                     │ │
│ │ WAR B [가능 구조]                         │ │
│ └───────────────────────────────────────────┘ │
│                                               │
│ Tomcat JVM #2 [가능 구조]                     │
│ ┌───────────────────────────────────────────┐ │
│ │ 별도 Heap / Port / CATALINA_BASE          │ │
│ └───────────────────────────────────────────┘ │
│                                               │
└───────────────────────────────────────────────┘
```

### 반드시 분리할 개념

```text
Server / VM
Tomcat JVM
WAR
Port
PID
CATALINA_BASE
Heap
Datasource
Log
HA Group
```

---

# 12. WAR / Module Boundary

## FIG-PH-12. WAR vs Module

```text
Business WAR
┌──────────────────────────────────────┐
│                                      │
│ pdmg-service Module                  │
│          +                           │
│ pdmg-fw Library / Bean               │
│                                      │
└──────────────────────────────────────┘
```

### 핵심

```text
pdmg-fw Module
≠
독립 WAR
≠
독립 JVM
≠
독립 Server
```

현재 Reference 기준으로는 `pdmg-service` Runtime 내부 Framework 계층으로 본다.

---

# 13. PDMG Physical Mapping — 상위

## FIG-PH-13. PDMG Deployment Candidate Map

```text
PDMG Reference
│
├─ pdmg-ui
│    └─ UI Process / WEB-like Deployment [VERIFY]
│
├─ pdmg-jwt
│    └─ Auth/Token Application [VERIFY PHYSICAL]
│
├─ pdmg-service
│    └─ Business WAR / Service Runtime
│
├─ pdmg-fw
│    └─ In-process Framework Library
│
└─ pdmg-om
     └─ [UNKNOWN CURRENT PHYSICAL]
```

### GAP

다음 Mapping은 최신 Deployment Manifest가 필요하다.

```text
Module
→ Artifact
→ Host
→ JVM
→ Context
→ Port
→ L4 Pool
→ WEB VHost
```

---

# 14. Marketing Physical Role Evidence

## FIG-PH-14. Marketing WEB/WAS Redundancy

```text
의왕 MAIN

Marketing Platform
│
├─ WEB #1
├─ WEB #2
│
├─ WAS #1
└─ WAS #2
```

### 해설

- 물리 구성도에는 Marketing WEB/WAS 이중화 Role이 표현된다.
- 그러나 `pdmg-service`가 해당 Role에 공식 배포된다는 Mapping은 별도 Deployment Evidence로 닫아야 한다.

---

# 15. Marketing Additional AP Roles

## FIG-PH-15. Marketing AP Roles

```text
Marketing Physical Roles
│
├─ Marketing WEB #1/#2
├─ Marketing WAS #1/#2
├─ Mini Single View WEB #1/#2
├─ Mini Single View WAS #1/#2
├─ Real-time Processing AP #1/#2
├─ Behavior Processing AP #1/#2
└─ Behavior Data AP #1/#2/#3
```

### 의미

BIG PICTURE/LOGICAL에서 분리한:

```text
Online
Single View
Real-time
Behavior
```

책임이 Physical Role로 내려오는 구조다.

---

# 16. BI / Governance Physical Roles

## FIG-PH-16. Supporting Physical Roles

```text
BI / Analysis
│
├─ BI Portal WEB/WAS
├─ Self-BI WEB/WAS
├─ Self-BI AP
└─ Credit Performance WEB/WAS

Governance / Operations
│
├─ Metadata / Quality WAS
├─ Data Flow WAS
├─ Report/Output WAS
├─ Terminal Management
├─ Distribution
└─ Dashboard / Operations
```

실제 Hostname / 수량 / Version은 최신 Inventory로 검증한다.

---

# 17. Data Platform Physical Strategy

## FIG-PH-17. Hybrid Physical Strategy

```text
┌───────────────────────────────────────────────────────────┐
│ Service Resource                                          │
│ Marketing / BI / Governance                              │
│ → NH Private Cloud VM / Scale-out                         │
├───────────────────────────────────────────────────────────┤
│ Data Resource                                             │
│ RDW / ADW                                                 │
│ → Exadata / Dedicated Parallel Data Resource              │
├───────────────────────────────────────────────────────────┤
│ Performance-sensitive Resource                            │
│ ETL / IMDG                                                │
│ → Dedicated / Bare-metal Candidate                        │
├───────────────────────────────────────────────────────────┤
│ CDC                                                       │
│ → Unix / Dedicated Change Data Role                       │
└───────────────────────────────────────────────────────────┘
```

### 상태

이 구성은 특정 시점 Physical Strategy Baseline이다.
최신 실제 Inventory로 재검증해야 한다.

---

# 18. RDW / ADW Physical Separation

## FIG-PH-18. Data Platform Physical Boundary

```text
                    Data Platform
                        │
          ┌─────────────┴─────────────┐
          ▼                           ▼
     ┌───────────┐               ┌───────────┐
     │    RDW    │               │    ADW    │
     │ Exadata   │               │ Exadata   │
     └─────┬─────┘               └─────┬─────┘
           │                           │
 Near Real-time                 Analytical / Mart
           │                           │
           └────────────┬──────────────┘
                        ▼
                  Data Integration
```

### 핵심

- LOGICAL에서 분리한 RD/AD 책임을 Physical에서도 가능한 한 보존한다.
- 동일/공유 자원 여부는 실제 Baseline에 따라 검증하되,
  **성능과 장애 책임을 혼합하지 않는 것**이 원칙이다.

---

# 19. CDC Physical Role

## FIG-PH-19. CDC Physical Runtime

```text
Core / Source DB
      │
      ▼
CDC Capture / Relay
      │
      ▼
RDW
```

### CDC Resource 관심

```text
CPU
Network
Change Lag
Queue / Buffer
Source DB Impact
Target Apply
```

---

# 20. ETL Physical Role

## FIG-PH-20. ETL Physical Runtime

```text
Source / RDW
      │
      ▼
ETL Engine
      │
      ├─ Extract
      ├─ Transform
      └─ Load
      │
      ▼
ADW
```

### 핵심

- ETL은 대량 I/O와 CPU를 사용하므로 Online WAS와 같은 자원에 혼재시키지 않는 방향이 중요하다.

---

# 21. JVM Memory Boundary

## FIG-PH-21. JVM Memory Anatomy

```text
VM Memory
┌─────────────────────────────────────────┐
│                                         │
│ JVM Process                             │
│ ┌─────────────────────────────────────┐ │
│ │ Java Heap                           │ │
│ │ Metaspace                           │ │
│ │ Thread Stack                        │ │
│ │ Code Cache                          │ │
│ │ Direct Buffer                       │ │
│ │ Native / GC Structure               │ │
│ └─────────────────────────────────────┘ │
│                                         │
│ OS / Page Cache / Agent / Other         │
└─────────────────────────────────────────┘
```

### 핵심

```text
VM Memory 256GB
≠
Heap 256GB
```

---

# 22. JVM Heap Candidate

## FIG-PH-22. Heap Variant

```text
8C / 32G
   └─ Heap 약 12~14G 후보

16C / 64G
   └─ Heap 약 24~28G 후보

16C / 128G
   └─ Heap 약 32~40G 후보

32C / 256G
   └─ Heap 약 32~48G 후보
```

### 상태

```text
[CAPACITY VARIANT]
```

실제 `-Xms/-Xmx`는 JVM Config로 확인한다.

---

# 23. CPU / Memory Variant

## FIG-PH-23. VM Candidate Comparison

```text
8C / 32G
  │
  ├─ 작은 Failure Domain
  └─ 더 많은 Node 필요

16C / 64G
  │
  ├─ 중간 Scale-out
  └─ 일반 균형형

16C / 128G
  │
  ├─ CPU는 동일 16C
  └─ Memory 증가

32C / 256G
  │
  ├─ 큰 Capacity
  └─ Failure Blast Radius 증가
```

### 핵심

```text
Memory 증가
≠
CPU 처리능력 증가
≠
TPS 자동 증가
```

---

# 24. Scale-Up vs Scale-Out

## FIG-PH-24. Physical Scaling Strategy

```text
[Scale-Up]

작은 Node
   ↓
큰 VM
   ↓
고성능 단일 Node

장점
- Node 수 감소
- 관리 단순

위험
- 장애 Blast Radius 증가
- GC / Thread 복잡도
- 장애 후 잔여용량 급감


[Scale-Out]

여러 Node
   ↓
L4 분산
   ↓
독립 확장

장점
- 장애격리
- 단계적 확장
- 잔여용량 분산

비용
- 운영 노드 증가
- Session / Deploy / Monitoring 복잡
```

---

# 25. Capacity Assumption

## FIG-PH-25. User → TPS

```text
6,000 지점
    ×
6명
    =
36,000 사용자
       │
       ▼
동시 요청률
5% / 10% / 15%
       │
       ▼
Concurrent Request
1,800 / 3,600 / 5,400
       │
       ▼
응답시간 3초 후보
       │
       ▼
TPS
600 / 1,200 / 1,800
```

### 상태

```text
[CAPACITY BASELINE VARIANT]
```

실제 Production TPS 실측값으로 쓰지 않는다.

---

# 26. Capacity Chain

## FIG-PH-26. End-to-End Capacity Chain

```text
Users
  ↓
Concurrent Request
  ↓
TPS
  ↓
WEB Connections
  ↓
Tomcat Request Threads
  ↓
PDMG Worker Threads
  ↓
Hikari Connections
  ↓
DB Sessions
  ↓
CPU / Memory / I/O
```

### 핵심

이 값들은 **서로 연결되어 있지만 같은 크기로 맞추는 값이 아니다.**

---

# 27. Tomcat Thread

## FIG-PH-27. Tomcat Capacity Candidate

```text
8C
→ maxThreads 400~500 후보

16C
→ maxThreads 800~1000 후보

32C
→ maxThreads 1200~1500 후보
```

### 상태

```text
[CAPACITY VARIANT]
```

실제 `server.xml` 확인 필요.

---

# 28. Request Thread / Worker / Hikari

## FIG-PH-28. Three Pool Architecture

```text
Tomcat Request Thread
        │
        ▼
PDMG Worker Pool
        │
        ▼
Hikari Connection Pool
        │
        ▼
DB Session
```

### 구분

```text
Tomcat maxThreads
= HTTP Request 자원

PDMG Worker
= Business Execution 자원

Hikari maxPoolSize
= DB Connection 자원
```

---

# 29. PDMG Current Worker Snapshot

## FIG-PH-29. PDMG Worker Current Snapshot

```text
[AS-IS SNAPSHOT]

Worker Pool = 20
Queue       = 100
Timeout     = 5000ms
```

### 중요

```text
Worker 20
≠
Tomcat maxThreads 20
```

이 값은 PDMG Source Snapshot이며
NSIGHT 전체 Capacity Target이 아니다.

---

# 30. Pool Relationship

## FIG-PH-30. Pool Bottleneck Propagation

```text
Tomcat Request 증가
      │
      ▼
Worker Busy
      │
      ▼
Queue 증가
      │
      ▼
Hikari Pending
      │
      ▼
DB Session / SQL Wait
      │
      ▼
Timeout / 503 / 504
```

---

# 31. Hikari Candidate

## FIG-PH-31. Hikari Capacity Variant

```text
일반 AP
8C   → 약 50 후보
16C  → 약 80~100 후보

SingleView
60분 Variant → 70~80
90분 Variant → 100~120
```

### 판정

```text
[CONFLICT / VARIANT]
최신 승인 Baseline 필요
```

---

# 32. Hikari Too Small

## FIG-PH-32. Small Pool Failure

```text
Worker
  ↓
Connection Request
  ↓
Pool Full
  ↓
Pending
  ↓
Worker Hold
  ↓
Queue
  ↓
Timeout
```

---

# 33. Hikari Too Large

## FIG-PH-33. Large Pool Failure

```text
Hikari 확대
   ↓
DB Session 증가
   ↓
DB CPU / PGA / Lock / I/O 증가
   ↓
DB 병목
```

### 핵심

> **Pool 확대는 무료 성능향상이 아니다.**

---

# 34. Thread / DB Pool Ratio

## FIG-PH-34. Ratio Candidate

```text
Thread / DB Pool

4:1 ~ 8:1
→ 정상 후보

8:1 ~ 12:1
→ 주의

12:1 초과
→ Pool Wait / SQL 병목 확인
```

이 값은 Capacity Design Candidate다.

---

# 35. Capacity Operational Rule Candidate

## FIG-PH-35. Thread Utilization

```text
산정 Thread
≤ maxThreads 70%
    → 정상 후보

70~85%
    → 주의

85% 초과
    → Scale / DB / External 병목 확인
```

Production Alert Threshold로 자동 승격하지 않는다.

---

# 36. Session Architecture

## FIG-PH-36. Session / Token / State

```text
Browser
│
├─ Access Token
├─ Refresh Token
└─ Client State
   │
   ▼
WAS
│
├─ HttpSession [필요 시]
├─ JWT Verification
└─ Request Context
   │
   ▼
Server State
├─ Refresh Hash
├─ Denylist
├─ User/Auth State
└─ Session State
```

### 핵심

```text
JWT
≠
Server State 0
```

---

# 37. Session Timeout Conflict

## FIG-PH-37. 60min vs 90min

```text
[Variant A]
Session Timeout = 60분
Sticky          = 70~80분

          VS

[Variant B]
Session Timeout = 90분
Sticky          = 100~120분
```

### 판정

```text
[CONFLICT-PH-01]
Final Session Idle Timeout = TBD
Final Sticky Timeout       = TBD
```

---

# 38. Session Size Candidate

## FIG-PH-38. Session Size Guardrail

```text
Target
≤ 2KB 후보

Maximum
≤ 5KB 후보
```

저장 후보:

```text
userId
branchId
role
authLevel
```

금지 후보:

```text
고객조회 전체 결과
Single View 전체 결과
대량 List
```

---

# 39. DeltaManager

## FIG-PH-39. In-Center Session Replication

```text
Center Internal Cluster

Tomcat A
  │
  │ Session Replication
  ▼
Tomcat B
```

### Working Baseline

```text
DeltaManager
센터 내부 복제
```

---

# 40. Cross-Center Session

## FIG-PH-40. Main / DR Session Boundary

```text
의왕
Session Cluster
   │
   │ X Cross-center Replication
   │
안성
DR Cluster
```

### 의미

센터 장애 시:

```text
기존 HttpSession 유지
```

를 기본 보장하지 않는다.

---

# 41. DR Session Scenario

## FIG-PH-41. Center Failure Session Behavior

```text
User
 ↓
의왕 WAS
 ↓
HttpSession
 ↓
[Center Failure]
 ↓
GSLB
 ↓
안성 DR WAS
 ↓
Session 없음
 ↓
Re-login / Re-auth
```

실제 정책은 최신 보안/DR 승인과 연계한다.

---

# 42. Node Failure HA

## FIG-PH-42. WEB Node Failure

```text
Client
 ↓
L4
 ├─ WEB #1  X
 └─ WEB #2  O
      │
      ▼
    WAS Pool
```

---

# 43. WAS Node Failure

## FIG-PH-43. WAS Failover

```text
WEB
 ↓
WAS #1  X
 ↓
Health Check / Member Remove
 ↓
WAS #2
 ↓
Business Continue
```

필요:

```text
Residual Capacity
Session Behavior
In-flight Transaction
Connection Cleanup
```

---

# 44. JVM Failure Domain

## FIG-PH-44. Shared JVM Blast Radius

```text
Tomcat JVM
┌─────────────────────────────┐
│ WAR A                       │
│ WAR B                       │
└─────────────────────────────┘

JVM Crash
  ↓
WAR A Down
WAR B Down
```

### 핵심

```text
WAR가 다름
≠
Failure Domain 완전 분리
```

---

# 45. Separate JVM Isolation

## FIG-PH-45. Stronger Isolation

```text
VM
│
├─ Tomcat JVM A
│    └─ WAR A
│
└─ Tomcat JVM B
     └─ WAR B
```

분리 기준 후보:

```text
업무 중요도
부하 특성
배포주기
장애영향
보안
```

---

# 46. Application Group A/B Isolation

## FIG-PH-46. Group Isolation Candidate

```text
Application Group A
      │
      └─ JVM / Host Group A

Application Group B
      │
      └─ JVM / Host Group B
```

### 현재 상태

실제 PDMG 업무그룹별 Host/JVM 배치 Matrix는 Evidence 부족.

```text
[GAP]
```

---

# 47. HA Capacity

## FIG-PH-47. N+1 Principle

```text
정상
Node A + B + C + D

1 Node Down
A + B + C

      │
      ▼
남은 3개 Node가 Peak SLA 유지?
```

### 핵심

정상 TPS만 계산하면 HA Capacity가 아니다.

---

# 48. Residual Capacity

## FIG-PH-48. Failure Capacity

```text
Normal Load
   ↓
Node Failure
   ↓
Remaining Capacity
   ↓
p95 / CPU / Thread / Pool
   ↓
SLA 유지 여부
```

---

# 49. DR Architecture

## FIG-PH-49. Main → DR Failover

```text
Normal
User
 ↓
GSLB
 ↓
의왕
 ↓
WEB/WAS
 ↓
DB

Disaster
의왕 X
 ↓
GSLB Decision
 ↓
안성
 ↓
DR WEB/WAS
 ↓
DR Data
```

---

# 50. DR Decision Chain

## FIG-PH-50. DR Readiness

```text
Detect
 ↓
Declare
 ↓
Route
 ↓
Application Ready
 ↓
Data Ready
 ↓
Security Ready
 ↓
External Ready
 ↓
Validate
 ↓
Failback
```

---

# 51. HA vs DR vs Backup

## FIG-PH-51. Three Different Recovery Mechanisms

```text
HA
= Node / Process Failure Continuity

DR
= Center Disaster Recovery

Backup
= Data / System Restore Asset
```

### 금지

```text
Backup 있으니 DR 완료        X
서버 2대 있으니 HA 완료      X
DR센터 있으니 RTO 충족       X
```

---

# 52. DR RPO / RTO

## FIG-PH-52. RPO / RTO Decision

```text
Business Criticality
      │
      ▼
RTO / RPO
      │
      ▼
Replication / Standby
      │
      ▼
DR Runbook
      │
      ▼
DR Test
```

### 현재 상태

최종 NSIGHT/PDMG 전체 RPO/RTO는 현재 확보 Evidence로 확정하지 않는다.

```text
[GAP-PH-02]
```

---

# 53. DB HA / DR

## FIG-PH-53. Data HA Responsibility

```text
Application
   │
   ▼
DB Service
   │
   ├─ Local HA
   └─ DR Replication
```

### PHYSICAL에서 확인할 것

```text
DB Role
Primary / Standby
Connection Endpoint
Failover
Replication
Storage
Backup
```

정확한 RAC/Data Guard 등은 DB Architecture Evidence로 확정해야 한다.

---

# 54. JWT Physical HA

## FIG-PH-54. JWT Multi-instance Risk

```text
L4
├─ JWT #1
│    └─ Key A / kid=K
│
└─ JWT #2
     └─ Key B / kid=K
```

### 위험

```text
same kid
different key
```

는 Critical.

---

# 55. JWT TO-BE Physical

## FIG-PH-55. Central Key Store

```text
                Central Key Store
                       │
           ┌───────────┴───────────┐
           ▼                       ▼
       JWT #1                   JWT #2
       key=K2                   key=K2
       kid=K2                   kid=K2
           │                       │
           └───────────┬───────────┘
                       ▼
                      L4
                       │
                       ▼
                     JWKS
```

제품은 `[TBD]`.

---

# 56. JWKS HA

## FIG-PH-56. JWKS Availability

```text
Business Verifier
      │
      ▼
JWKS VIP
      │
      ├─ JWT #1
      └─ JWT #2
```

필요:

```text
Consistent JWK Set
Cache
Unknown kid refresh
Rotation
DR
```

---

# 57. JWT DR

## FIG-PH-57. Token Compatibility at DR

```text
Main Token
   │
   ▼
Center Failure
   │
   ▼
DR
   │
   ├─ Same Logical Issuer?
   ├─ Same Public Key?
   ├─ Denylist replicated?
   ├─ Refresh state replicated?
   └─ Clock synchronized?
```

---

# 58. Refresh / Denylist State

## FIG-PH-58. Security State HA

```text
JWT Process
   │
   ├─ Access Token
   └─ Refresh / Revoke
         │
         ▼
Security State DB
   ├─ Refresh Hash
   ├─ Token Family
   └─ Denylist
```

### 핵심

```text
JWT Process HA
+
Security State DB HA
```

둘 다 필요하다.

---

# 59. Network Security Boundary

## FIG-PH-59. Network Zone

```text
Client
  │ TLS
  ▼
GSLB / L4
  │
  ▼
WEB
  │
  ▼
WAS
  │
  ▼
DB Network
```

확인 필요:

```text
TLS Termination
WEB→WAS 암호화
mTLS
Firewall
Direct WAS Port
Admin Port
JWKS Exposure
```

---

# 60. Direct WAS Access Risk

## FIG-PH-60. Bypass Risk

```text
Normal
Client → WEB → WAS

Risk
Client / Internal
      ─────────► WAS Direct
```

### 질문

```text
WAS Direct Port가 열려 있는가?
Business Filter가 자체 인증을 수행하는가?
Gateway/WEB 우회 방어가 있는가?
```

---

# 61. Timeout Physical Layers

## FIG-PH-61. End-to-End Timeout Stack

```text
Client
  ↓
GSLB / L4
  ↓
Apache Proxy
  ↓
Tomcat Request
  ↓
PDMG Worker Deadline
  ↓
Hikari Connection Wait
  ↓
JDBC / DB Query
```

### 원칙

```text
하위 Timeout
<
상위 Timeout
```

정확한 수치는 Config Evidence로 확정한다.

---

# 62. Capacity Timeout vs Current Config

## FIG-PH-62. Candidate vs Actual

```text
Capacity Document
Apache / DB / Transaction / Client 후보값
        │
        │ ≠
        ▼
PDMG Current Source
OnlineTimeoutExecutor = 5000ms
```

### 핵심

```text
Capacity Recommendation
≠
Current Runtime Config
```

---

# 63. Physical Configuration Evidence

## FIG-PH-63. Config Sources

```text
WEB
→ httpd.conf / vhost

WAS
→ server.xml

JVM
→ setenv.sh / startup options

Application
→ application.yml

Hikari
→ datasource config

Network
→ L4 / GSLB config

DB
→ DB service config
```

---

# 64. Capacity vs Actual Config Drift

## FIG-PH-64. Physical Drift

```text
Capacity Baseline
      │
      ▼ compare
Actual Config
      │
      ▼ compare
Runtime Metric
      │
      ▼
PASS / DRIFT
```

### 예

```text
Design maxThreads 800
Actual 1500
→ DRIFT

Design Session 60m
Actual 90m
→ DRIFT / Baseline Conflict

Design Hikari 80
Actual 200
→ DRIFT
```

---

# 65. Server Master Inventory

## FIG-PH-65. Physical Trace Chain

```text
Architecture Component
      ↓
Application Group
      ↓
System Group
      ↓
Environment
      ↓
Center
      ↓
Hostname
      ↓
Role
      ↓
CPU / Memory
      ↓
Middleware
      ↓
JVM
      ↓
WAR
      ↓
Port
      ↓
Datasource
      ↓
HA Group
      ↓
DR Pair
```

---

# 66. Inventory 최소 컬럼

| Category | Fields |
|---|---|
| Business | Application Group / Business Code |
| Environment | Prod/Dev/DR/Pilot |
| Physical | Center / Hostname / IP / VM |
| Resource | CPU / Memory / Disk |
| Middleware | WEB/WAS/JVM |
| Deployment | WAR / Context / Port |
| Data | Datasource / DB |
| Availability | HA Group / DR Pair |
| Evidence | Source / Config / Verification |

---

# 67. Physical Hostname Evidence

물리 구성도에는 실제 Hostname 예가 존재할 수 있다.

예:

```text
sbmpcoltwb01-02
sbmpcoltws01-02
...
```

### 주의

```text
Hostname이 Marketing처럼 보임
=
pdmg-service 배포 Host 확정

X
```

Deployment Manifest 필요.

---

# 68. Physical Failure Domain Map

## FIG-PH-66. Blast Radius

```text
Code Failure
   ↓
WAR

JVM Failure
   ↓
All WARs in JVM

VM Failure
   ↓
All JVMs in VM

WEB Failure
   ↓
Route Member

L4 Failure
   ↓
VIP / Service

DB Failure
   ↓
Multiple Applications

Center Failure
   ↓
All Center Resources
```

---

# 69. Failure Isolation Verification

## FIG-PH-67. Logical vs Physical Isolation

```text
LOGICAL
Online Node
Event Node
ETL Node
       │
       ▼
PHYSICAL
Separate JVM / Host / Resource?
       │
       ├─ YES → Isolation preserved
       └─ NO  → Shared failure/resource risk
```

---

# 70. Physical Scaling Decision Tree

## FIG-PH-68. Scale Decision

```text
Workload 증가
  │
  ├─ CPU Bound?
  ├─ Memory Bound?
  ├─ DB Bound?
  ├─ Thread Bound?
  ├─ I/O Bound?
  └─ External Bound?
      │
      ▼
Scale-up / Scale-out / Tune / Isolate
```

### 금지

```text
느림
→ 서버 추가

만으로 결정
```

---

# 71. Capacity Test

## FIG-PH-69. Capacity Validation

```text
Normal
  ↓
Peak
  ↓
Stress
  ↓
Soak
  ↓
Node Failure
  ↓
DB Slow
  ↓
External Slow
  ↓
Session Load
  ↓
DR
```

---

# 72. Peak Test

```text
36,000 Users
  ↓
10% Concurrent Candidate
  ↓
1,200 TPS Candidate
  ↓
WEB/WAS/Worker/Hikari/DB
  ↓
p95 / CPU / Queue / Pool
```

---

# 73. Stress Test

```text
15%
  ↓
1,800 TPS Candidate
  ↓
Saturation Point
  ↓
Failure Mode
```

---

# 74. One Node Down Test

## FIG-PH-70. HA Capacity Test

```text
Peak Load
   +
WAS 1 Node Down
   ↓
Remaining Nodes
   ↓
SLA?
```

---

# 75. Hikari Saturation Test

```text
Pool Pressure
   ↓
Pending
   ↓
Worker Hold
   ↓
Queue
   ↓
Timeout
```

---

# 76. DB Slow Test

```text
Slow SQL
 ↓
Connection Hold
 ↓
Hikari Pending
 ↓
Worker Queue
 ↓
Tomcat Waiting
 ↓
504 / Timeout
```

---

# 77. Session Load Test

```text
Active Session
  ↓
Heap
  ↓
Replication Traffic
  ↓
GC
  ↓
Failover
```

최종 60/90분 정책 결정 후 재시험한다.

---

# 78. JWT Restart Test

```text
Token Issue
  ↓
JWT Restart
  ↓
Existing Token Verify?
```

Current ephemeral key 구조에서는 위험이 존재한다.

---

# 79. JWT Multi-instance Test

```text
JWT #1 / #2
   ↓
Same kid?
Same public key?
Same JWKS?
```

---

# 80. DR Test

## FIG-PH-71. End-to-End DR Test

```text
Main Failure
  ↓
GSLB Route
  ↓
DR WEB
  ↓
DR WAS
  ↓
Login / JWT
  ↓
Business Service
  ↓
DB
  ↓
External
  ↓
Session
  ↓
Failback
```

---

# 81. PHYSICAL Architecture Rules

## FIG-PH-72. Rule Cards

```text
PH-01 Server / JVM / WAR Separate
PH-02 Module ≠ Physical Server
PH-03 Logical Isolation must survive Physical Mapping
PH-04 Capacity Candidate ≠ Actual
PH-05 VM Memory ≠ Heap
PH-06 Thread ≠ Worker ≠ Hikari
PH-07 In-center Session ≠ Cross-center Session
PH-08 HA ≠ DR ≠ Backup
PH-09 JWT Multi-instance Key must be consistent
PH-10 Physical Config must be traceable
PH-11 Node Failure test required
PH-12 DR test required
```

---

# 82. 정상 패턴

```text
GSLB → L4 → WEB → WAS → JVM → WAR → Hikari → DB

Separate Event / ETL / Data Runtime

Center Internal HA

Approved DR Pair

Central Key Store for JWT

Inventory ↔ Config ↔ Runtime Metric
```

---

# 83. 금지 패턴

```text
Server = JVM = WAR                     X
pdmg-fw = 독립 Server                  X
VM Memory = Heap                       X
Tomcat Thread = Worker = Hikari        X
Session Replication across DR assumed  X
Capacity Candidate = Production Fact   X
Backup = DR                            X
JWT Instance마다 다른 Key              X
```

---

# 84. PDMG Physical Mapping Gap

## FIG-PH-73. Current Mapping Gap

```text
PDMG Source
   │
   ▼
Module / Artifact
   │
   ▼
[ GAP ]
   │
   ▼
Production Host / JVM / Context / Port
```

필요 Evidence:

```text
Deployment Manifest
server.xml
application.yml
eCAMS
L4 Pool
WEB VHost
Runtime Process
```

---

# 85. PDMG Local Port Guardrail

Local 예:

```text
pdmg-ui      : 8090
pdmg-service : 8080
```

### 금지

```text
Local Port
→ Production Port

자동 승격 X
```

---

# 86. PDMG Runtime Chain in Physical

## FIG-PH-74. PDMG Physical Runtime

```text
WEB / Client
   │
   ▼
Business WAS
   │
   ▼
Tomcat Request Thread
   │
   ▼
PDMG Worker
   │
   ▼
Transaction
   │
   ▼
DAO / Mapper
   │
   ▼
Hikari
   │
   ▼
RDW / DB
```

---

# 87. PDMG Security Physical

## FIG-PH-75. JWT / Service Physical

```text
Client
  │
  ▼
pdmg-jwt [Physical TBD]
  │
  ├─ Key Store [TO-BE]
  ├─ JWKS
  └─ Token State DB
  │
  ▼
Business WAS
  │
  └─ JWT Verify
```

---

# 88. PDMG OM Physical

```text
pdmg-om
  │
  ├─ Host?
  ├─ JVM?
  ├─ Port?
  ├─ DB?
  └─ Dashboard?
```

현재:

```text
[UNKNOWN]
```

---

# 89. NFR — Performance Physical Mapping

## FIG-PH-76. Performance Physical

```text
Performance
  ↓
Separate Runtime
  ↓
CPU / Memory
  ↓
Thread / Pool
  ↓
DB
  ↓
Load Test
  ↓
Metric
```

---

# 90. NFR — Availability Physical Mapping

```text
Availability
  ↓
Redundant Nodes
  ↓
Health Check
  ↓
Failover
  ↓
Residual Capacity
  ↓
DR
```

---

# 91. NFR — Scalability Physical Mapping

```text
Scalability
  ↓
VM Size
  ↓
Node Count
  ↓
L4 Distribution
  ↓
Independent Scale
```

---

# 92. NFR — Security Physical Mapping

```text
Security
  ↓
Network Boundary
  ↓
TLS
  ↓
Port Control
  ↓
Key Store
  ↓
DB Account
```

---

# 93. NFR — Observability Physical Mapping

```text
Observability
  ↓
Host
  ↓
JVM
  ↓
WAR
  ↓
Thread / Pool
  ↓
DB
  ↓
Metric / Log / Trace
```

---

# 94. Physical Evidence Types

```text
Inventory
Config
Deployment
Runtime Process
Metric
Load Test
Failover Test
DR Test
```

---

# 95. Evidence Strength

```text
Runtime Process / Config
       >
Deployment Manifest
       >
Inventory
       >
Capacity Design
       >
Architecture Drawing
       >
General Assumption
```

---

# 96. CONFIRMED / WORKING BASELINE

```text
[CONFIRMED / WORKING BASELINE]

- 의왕 Main / 안성 DR 구조
- 내부 GSLB 기반 센터 연결 방향
- Marketing WEB/WAS 이중화 역할
- GSLB→L4→WEB→WAS→JVM→WAR→DB 실행경로
- RDW / ADW Data Platform 분리
- Service VM / Data Dedicated Resource Hybrid 전략
- PDMG Worker 20 / Queue100 / 5000ms AS-IS snapshot
- 36,000 사용자 Capacity assumption
- Session 60/90 Variant 존재
- DeltaManager 센터 내부 복제 방향
```

---

# 97. CONFLICT

```text
[CONFLICT-PH-01]
Session 60분 vs 90분

[CONFLICT-PH-02]
SingleView Hikari 70~80 vs 100~120

[CONFLICT-PH-03]
Capacity Timeout Candidate vs PDMG Current 5000ms

[CONFLICT-PH-04]
Physical Strategy Baseline vs Latest Actual Inventory
```

---

# 98. OPEN

```text
[OPEN-PH-01]
PDMG Production Host/JVM/WAR/Port

[OPEN-PH-02]
실제 Apache/Tomcat Version

[OPEN-PH-03]
실제 JVM Heap / GC

[OPEN-PH-04]
실제 Tomcat maxThreads

[OPEN-PH-05]
실제 Hikari Config

[OPEN-PH-06]
Session 최종값

[OPEN-PH-07]
DR RPO/RTO

[OPEN-PH-08]
JWT Key Store 제품/위치

[OPEN-PH-09]
Direct WAS Port 통제

[OPEN-PH-10]
pdmg-om Physical Deployment
```

---

# 99. GAP Register

| ID | GAP | 영향 |
|---|---|---|
| GAP-PH-01 | PDMG Deployment Mapping 미완료 | Deployment |
| GAP-PH-02 | DR RPO/RTO 미확정 | DR |
| GAP-PH-03 | App Group A/B 실제 물리분리 미확정 | Isolation |
| GAP-PH-04 | DB HA/DR 상세 미확정 | Data Availability |
| GAP-PH-05 | Apache/Tomcat Actual Config 미확정 | Middleware |
| GAP-PH-06 | JVM Heap/GC Actual 미확정 | JVM |
| GAP-PH-07 | Tomcat Thread Actual 미확정 | Capacity |
| GAP-PH-08 | Hikari Actual 미확정 | DB Pool |
| GAP-PH-09 | Session Final Baseline 미확정 | Session |
| GAP-PH-10 | JWT Central Key Store 미확정 | Security |
| GAP-PH-11 | JWKS HA/DR 미확정 | Security |
| GAP-PH-12 | Refresh/Denylist DR 미확정 | Security |
| GAP-PH-13 | Network Direct Access 통제 미확정 | Security |
| GAP-PH-14 | Server Master Inventory↔Architecture 연결 미완료 | Traceability |
| GAP-PH-15 | Capacity↔Runtime Drift 자동검증 미완료 | Governance |

---

# 100. RISK Register

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-PH-01 | Capacity Candidate를 운영값으로 오인 | High |
| RISK-PH-02 | Session 60/90 Drift | High |
| RISK-PH-03 | maxThreads 과대설정 | High |
| RISK-PH-04 | Hikari 과대설정 | High |
| RISK-PH-05 | Worker 과소설정 | High |
| RISK-PH-06 | Worker 과대설정 | High |
| RISK-PH-07 | 대형 JVM Blast Radius | High |
| RISK-PH-08 | VM Memory 전체 Heap 사용 | Critical |
| RISK-PH-09 | DR Session 무중단 오판 | High |
| RISK-PH-10 | JWT Instance별 다른 Key | Critical |
| RISK-PH-11 | Direct WAS Access | Critical |
| RISK-PH-12 | DR Test 부재 | Critical |
| RISK-PH-13 | DB/ETL/Event 자원 혼재 | High |
| RISK-PH-14 | Shared JVM 장애영역 오판 | High |
| RISK-PH-15 | Physical Inventory Drift | High |

---

# 101. ADR 후보

```text
ADR-PH-01 Session Baseline
ADR-PH-02 VM Size / Scale-out
ADR-PH-03 JVM Isolation
ADR-PH-04 App Group Physical Isolation
ADR-PH-05 Tomcat Thread Baseline
ADR-PH-06 Worker Pool Baseline
ADR-PH-07 Hikari Baseline
ADR-PH-08 JWT Key Store
ADR-PH-09 JWKS HA
ADR-PH-10 DR RPO/RTO
ADR-PH-11 Direct WAS Access
ADR-PH-12 TLS Boundary
ADR-PH-13 Server Inventory SSOT
ADR-PH-14 Capacity Drift Gate
```

---

# 102. MECHANISM Handoff

## FIG-PH-77. PHYSICAL → MECHANISM

```text
PHYSICAL Output
│
├─ Center
├─ Network Path
├─ WEB / WAS
├─ JVM / WAR
├─ Runtime Resource
├─ RDW / ADW
├─ HA / DR
├─ Security Boundary
└─ Physical Inventory
       │
       ▼
MECHANISM Input
│
├─ Online Interface
├─ Event Interface
├─ CDC / ETL
├─ Message / Header
├─ GUID / ServiceId
├─ Charset
├─ Framework Entry
├─ Transaction / Timeout Rule
├─ Retry / Recovery
├─ File Transfer
├─ JWT / SSO
└─ Logging / Error Contract
```

---

# 103. PHYSICAL → MECHANISM Example

## FIG-PH-78. Runtime Path to Mechanism

```text
WEB
 ↓
WAS
 ↓
Business WAR
 ↓
DB
```

Physical에서:

```text
"어디에 배치?"
```

를 답했다면,

MECHANISM에서는:

```text
"무슨 Contract로 통신?"
"무슨 Header?"
"무슨 Timeout?"
"무슨 Retry?"
"무슨 Error?"
```

를 답한다.

---

# 104. MECHANISM에서 반드시 답할 질문

```text
1. Online Interface 표준은 무엇인가?
2. Event는 어떤 Schema와 Key를 사용하는가?
3. CDC/ETL/File은 어떤 운영계약을 갖는가?
4. Message Header에 무엇이 들어가는가?
5. GUID/ServiceId는 어디서 생성/전파되는가?
6. Charset / Date / Number 표현은 무엇인가?
7. Framework Entry는 어디인가?
8. Timeout / Retry / Transaction Rule은 무엇인가?
9. Error Code / Envelope는 무엇인가?
10. JWT/SSO는 어떤 Trust Mechanism을 사용하는가?
```

---

# 105. PHYSICAL 최종 통합 지도

## FIG-PH-79. Physical Summary

```text
MAIN 의왕
   │
   ├─ GSLB / L4
   │
   ├─ WEB #1/#2
   │
   ├─ WAS #1/#2
   │    └─ Tomcat JVM
   │         └─ Business WAR
   │              └─ Framework + Business
   │
   ├─ Real-time/Event AP
   ├─ CDC / ETL
   ├─ RDW / ADW
   ├─ BI / Governance
   └─ Operations
        │
        │ DR
        ▼
DR 안성
   │
   └─ Alternate Runtime / Data / Route

Execution Resource
Thread → Worker → Hikari → DB

Governance
Inventory → Config → Test → Runtime Metric → Drift
```

---

# 106. Definition of Done

## 106.1 Center / Network

- [x] Main / DR Center 시각화
- [x] GSLB / L4 / WEB / WAS 경로 정의
- [x] Traffic Layer 책임 구분
- [x] Direct Access 위험 정의

## 106.2 WEB / WAS / JVM / WAR

- [x] WEB VM / Apache Instance 구분
- [x] Server / JVM / WAR 구분
- [x] Module / WAR 구분
- [x] Shared JVM Failure Domain 정의
- [x] Strong Isolation 후보 정의

## 106.3 Data Platform

- [x] RDW / ADW Physical 역할 정의
- [x] CDC / ETL 역할 정의
- [x] Hybrid Physical Strategy 정의
- [x] Data Dedicated Resource와 Service VM 구분

## 106.4 Capacity

- [x] 36,000 사용자 기반 Candidate 표시
- [x] 600/1200/1800 TPS Candidate 표시
- [x] 8C/16C/32C Variant
- [x] JVM Heap Candidate
- [x] Tomcat/Worker/Hikari 분리
- [x] Hikari Conflict 표시

## 106.5 Session / HA / DR

- [x] Session 60/90 Conflict
- [x] DeltaManager In-center
- [x] Cross-center Session 미복제 방향
- [x] Node Failure HA
- [x] Center DR
- [x] HA/DR/Backup 분리

## 106.6 Security

- [x] JWT Multi-instance Key 문제
- [x] Central Key Store TO-BE
- [x] JWKS HA
- [x] Refresh/Denylist State
- [x] TLS / Direct WAS Access Open

## 106.7 PDMG

- [x] PDMG Physical Mapping Candidate
- [x] pdmg-fw Standalone Server 오해 제거
- [x] Local Port→Production Port 승격 금지
- [x] pdmg-om UNKNOWN 유지

## 106.8 Governance

- [x] Inventory Trace Chain
- [x] Config Drift
- [x] Capacity Test
- [x] DR Test
- [x] CONFIRMED/CONFLICT/OPEN/GAP/RISK
- [x] MECHANISM Handoff

**PHYSICAL 장 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. 최신 Production Deployment Manifest 확보
2. 실제 Apache/Tomcat/JVM/Hikari Config 수집
3. Session 최종 Baseline 확정
4. Worker 20/100 부하시험
5. JWT Central Key Store/JWKS HA 확정
6. DR RPO/RTO 및 Runbook 승인
7. Server Master Inventory와 Architecture Mapping
8. Node Failure + Peak Load Test
9. DR End-to-End Test

---

# 107. 다음 장

다음은 **05. MECHANISM — Interface / Message / GUID / Framework / Transaction / Timeout / Security / Logging**이다.

다음 장은 PHYSICAL에서 정한:

```text
WEB
WAS
JVM
WAR
DB
Event
CDC
ETL
Network Boundary
```

사이에 실제로 어떤 표준 Contract가 흐르는지를 정의한다.

즉,

```text
PHYSICAL
"어디에 배치할 것인가?"

        ↓

MECHANISM
"어떤 규칙으로 동작할 것인가?"
```

로 넘어간다.
