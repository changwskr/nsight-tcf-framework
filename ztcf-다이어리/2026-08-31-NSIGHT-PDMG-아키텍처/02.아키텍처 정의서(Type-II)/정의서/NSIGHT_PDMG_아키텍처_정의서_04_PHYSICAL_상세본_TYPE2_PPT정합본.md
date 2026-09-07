# NSIGHT / PDMG 아키텍처 정의서
# 04. PHYSICAL — 물리 인프라 / 데이터베이스 / 시스템 표준 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-TYPE2-PHYSICAL-04-DETAIL`  
> Architecture Level: **PHYSICAL**  
> PPT 공식 범위:  
> **3. 물리 인프라 아키텍처**  
> **4. 데이터베이스 아키텍처**  
> **5. 시스템 표준 정의**  
> 문서 상태: **Draft / Evidence-First / PPT-Aligned / Detailed Baseline**  
> 작성 기준일: **2026-08-31**  
> 선행 문서: `03. LOGICAL — 논리 기술 아키텍처 상세본`  
> 후속 문서: `05. MECHANISM — Interface / Standardization / Framework / Solution`  
> 핵심 질문: **LOGICAL에서 정의한 Node·Role·Environment를 어느 센터·Host·HW/SW·DB에 구현하고, 어떤 식별·HA/DR·운영 표준으로 관리할 것인가?**

---

# 0. 문서 사용법

PHYSICAL은 단순 서버 목록이 아니다.

본 장의 구조는 다음과 같다.

```text
[LOGICAL]
Zone / System / Logical Node / Capability
        ↓
[PHYSICAL]
Environment
        ↓
Center
        ↓
Physical Host / Appliance
        ↓
HW / OS / SW
        ↓
JVM / Process / Artifact
        ↓
DB / Network / Port / Filesystem
        ↓
HA / DR / Backup
        ↓
CMDB / DNS / Monitoring / Inventory
```

핵심:

```text
Logical Node가 없는 Physical Server 신설 금지
```

물리 구성은 반드시 다음 Chain을 가져야 한다.

```text
Logical Node
→ Physical Host
→ Software
→ Runtime
→ Data
→ Monitoring
→ Recovery
```

---

# 0.1 Evidence 태그

| 태그 | 의미 |
|---|---|
| `[PPT-TOC]` | PPT 공식 목차 |
| `[PPT-BODY]` | PPT 본문 직접 확인 |
| `[FACT]` | 공식 정의/자료에서 확인 |
| `[FACT-SOURCE]` | 원본 구성도/소스 근거 |
| `[WORKING BASELINE]` | 반복 사용 중인 프로젝트 기준 |
| `[CAPACITY BASELINE VARIANT]` | 특정 산정 가정에 따른 수치 |
| `[AS-IS SNAPSHOT]` | 현재 PDMG/Config Snapshot |
| `[TO-BE]` | 목표 |
| `[GAP]` | 목표/자료/구현 간 차이 |
| `[CONFLICT]` | 자료 간 충돌 |
| `[OPEN]` | 결정 필요 |
| `[UNKNOWN]` | 근거 부족 |
| `[RISK]` | 운영/성능/보안 위험 |
| `[ADR]` | Architecture Decision 필요 |

---

# 0.2 Evidence Register

| ID | 근거 | 사용 목적 | 상태 |
|---|---|---|---|
| EV-PH-01 | TYPE2/PPT 정합 마스터 프롬프트 | 3·4·5장 공식 범위 및 전개 순서 | `[WORKING BASELINE]` |
| EV-PH-02 | `03_물리인프라아키텍처_정의서.md` | 논리→물리, 센터, 환경, Inventory, SW/Capacity 규범 | `[FACT/WORKING BASELINE]` |
| EV-PH-03 | `04_데이터베이스아키텍처_정의서.md` | RDW/ADW, RAC, OGG/CDC, ETL, Access Matrix, OLTP/Batch | `[FACT/WORKING BASELINE]` |
| EV-PH-04 | `05_시스템표준_정의서.md` | Hostname 12자리, FS, Account, Port, 개통 규범 | `[FACT/WORKING BASELINE]` |
| EV-PH-05 | `VIII_Infrastructure_WAS_Capacity_HA_DR` | GSLB/L4/WEB/WAS/JVM/WAR, Capacity Variant, Session/HA/DR | `[CURRENT BASELINE DRAFT]` |
| EV-PH-06 | `03 LOGICAL 상세본` | Logical Node / Environment / Criticality Handoff | `[CURRENT BASELINE DRAFT]` |
| EV-PH-07 | PDMG Transaction/Timeout 자료 | PDMG Worker 20/Queue100/5s 등 Runtime Snapshot | `[AS-IS REFERENCE]` |

---

# 1. PPT 공식 구조

[PPT-TOC]

```text
3. 물리 인프라 아키텍처
   3.1 하드웨어 구성도
   3.2 소프트웨어 구성도
   3.3 하드웨어 목록
   3.4 소프트웨어 목록

4. 데이터베이스 아키텍처
   4.1 DB 아키텍처 구성도
   4.2 DB 이중화 구성도
   4.3 OGG 구성도
   4.4 OLTP 및 대용량 배치 수행 방안

5. 시스템 표준 정의
   5.1 서버 호스트 명명규칙
   5.2 파일 시스템 구성
   5.3 사용자 계정
   5.4 서비스 포트 현황
```

---

# 2. 핵심 결론

PHYSICAL Architecture의 핵심 결론은 다음과 같다.

> **논리 Node를 센터·환경·Host·HW/SW·DB·표준 식별자로 변환하고, 이 정보가 CMDB·DNS·IaaS·모니터링·백업·배포 체계에서 동일한 Inventory로 관리되어야 한다.**

물리 설계 핵심 원칙:

```text
1. 논리 없는 물리 신설 금지
2. 운영/DR/개발/선도/검증 구성도 분리
3. 의왕 주센터 / 안성 DR센터 구분
4. 운영↔DR Host 대응표
5. HW/SW 구성도 ↔ Inventory 동기화
6. 용량 수치는 산정 근거와 시점을 표시
7. RDW ≠ ADW
8. CDC/OGG ≠ ETL
9. 접근표 없는 DB 접근 금지
10. Hostname/FS/Account/Port 표준 강제
11. HA와 DR을 동일 개념으로 취급하지 않음
12. 물리 배치와 Runtime Evidence를 연결
```

---

# 3. LOGICAL Handoff 수신

LOGICAL Output:

```text
Zone
Logical System
Logical Node
Environment
Technology Capability
Layer
Data Ownership
Criticality
DR Priority
Connection Rule
```

PHYSICAL에서 다음을 확정한다.

```text
Center
Physical Host
Hostname
Host Group
HW Type
CPU / Memory
OS
SW / Version
JVM / Process
Artifact
DB
Filesystem
Account
Port
HA Pair
DR Pair
Monitoring ID
Backup
Capacity Evidence
```

---

# 4. Main Physical Architecture

```text
                           ┌────────────── 의왕 / 주센터 ──────────────┐
                           │                                         │
User/Channel → GSLB → L4 → WEB → WAS/JVM → Business/Application     │
                           │                │                        │
                           │                ├─ Worker / Hikari       │
                           │                │                        │
                           │                ├─ Event / Batch / ETL   │
                           │                │                        │
                           │                └─ RDW / ADW             │
                           │                                         │
                           │ 운영 / 개발 / 선도 / 검증                │
                           │ Host Seq 01~49                          │
                           └────────────────┬────────────────────────┘
                                            │
                              DR Replication / Deployment
                                            │
                                            ▼
                           ┌────────────── 안성 / DR센터 ────────────┐
                           │ DR WEB/WAS/AP/Data/Support             │
                           │ Host Seq 51~99                         │
                           └────────────────────────────────────────┘
```

---

# 5. 물리 설계와 Runtime 설계 구분

PHYSICAL:

```text
어디에
무엇을
몇 대
어떤 SW로
배치하는가
```

RUNTIME:

```text
어떻게 Failover하는가
어떻게 Recover하는가
어떻게 Validate하는가
```

따라서:

```text
DR Host 존재
≠ DR 완료
```

---

# 6. 3.1 하드웨어 구성도 — 공식 전개

PPT 기준:

```text
운영 / DR 환경
개발 환경
선도 환경
```

환경을 하나의 그림에 섞지 않는다.

추가 관리 환경:

```text
검증
```

은 물리 규범에서 별도 Environment로 관리한다.

---

# 7. 센터 정의

[FACT]

| 센터 | 역할 | Host 순번 |
|---|---|---|
| 의왕 | 주센터, 운영·개발·선도·검증 기본 배치 | `01~49` |
| 안성 | DR센터 | `51~99` |

금지:

```text
DR Host → 01~49
개발/검증 Host → 51~99
```

---

# 8. 센터와 환경은 다른 축

예:

```text
의왕
├─ 운영
├─ 개발
├─ 선도
└─ 검증

안성
└─ DR
```

Hostname Environment Code와 Center는 동일 정보가 아니다.

---

# 9. 물리 인프라 전체 계층

```text
Physical Infrastructure
│
├─ Center
│   ├─ 의왕
│   └─ 안성
│
├─ Environment
│   ├─ 운영
│   ├─ DR
│   ├─ 개발
│   ├─ 선도
│   └─ 검증
│
├─ Access
│   ├─ GSLB
│   └─ L4
│
├─ WEB
│
├─ WAS / AP
│   ├─ Tomcat/JVM
│   ├─ WAR/Application
│   └─ Framework
│
├─ Special Processing
│   ├─ Event
│   ├─ CDC
│   ├─ ETL
│   └─ Batch
│
├─ Data
│   ├─ RDW
│   └─ ADW
│
└─ Operations
    ├─ Monitoring
    ├─ Backup
    └─ Deployment
```

---

# 10. 논리→물리 매핑

필수 관계:

```text
Logical Node
  ↓
Environment
  ↓
Center
  ↓
Hostname
  ↓
HW Type
  ↓
OS / Platform
  ↓
SW
  ↓
Runtime / Artifact
```

---

# 10.1 Mapping must 필드

모든 물리 서버 행은 최소 다음을 포함한다.

| 필드 | 필수 |
|---|---:|
| Logical Node Name | Y |
| Environment | Y |
| Center | Y |
| Hostname | Y |
| Count / Type | Y |
| OS / Platform | Y |
| System Group | Y |
| SW / Version | Y |
| Capacity Evidence | Y |
| HA Group | 해당 시 |
| DR Pair | 해당 시 |
| Monitoring ID | 해당 시 |
| Backup Policy | 해당 시 |
| Owner | Y |
| Evidence | Y |

하나라도 빠지면 Inventory/개통 미완료로 본다.

---

# 10.2 Scale-out Mapping

허용:

```text
Logical Node 1
    ↓
Physical Host N
```

예:

```text
MP-WEB
├─ WEB #01
└─ WEB #02
```

각 행에는:

```text
Role
System Group
Environment
```

을 반복 기입한다.

---

# 10.3 복수 역할 Host

한 Host에 복수 Logical Role을 배치하면:

```text
대표 Logical Role
→ Hostname Role

부가 Role
→ CMDB Attribute
```

로 관리한다.

---

# 10.4 선도환경

선도는:

```text
운영과 동일 Zone / Logical Structure
+
축소된 대수
```

를 기본으로 한다.

축소 사유는 문서화한다.

---

# 11. 운영환경 Physical View

개념:

```text
GSLB
 ↓
L4
 ↓
WEB
 ↓
WAS / AP
 ├─ Marketing
 ├─ Mini Single View
 ├─ BI
 ├─ Governance
 └─ Support
 ↓
Data / Integration
```

실제 Host/대수는 HW 목록 SSOT와 일치해야 한다.

---

# 12. DR환경 Physical View

```text
안성 DR
│
├─ Critical WEB/WAS
├─ Marketing
├─ Data Platform
├─ IT Support
└─ DR Data / Replication
```

LOGICAL에서 즉시기동 Baseline:

```text
Marketing
Data Platform
IT Support
```

BI/DG는 후순위 복구 가능성.

정확한 RTO/RPO는 `[OPEN]`.

---

# 13. 운영↔DR 대응표

```text
운영 Host …01
       │
       ├─ Application / Config
       ├─ Network / DNS
       ├─ Data
       └─ Deployment
       ▼
DR Host …51
```

필수:

| Field | 내용 |
|---|---|
| Logical Role | 동일 역할 |
| Operating Host | 의왕 |
| DR Host | 안성 |
| Artifact | 동일/승격 기준 |
| Config | DR별 차이 |
| Network | GSLB/L4/DNS |
| Data | Replication |
| Owner | 운영 책임 |
| Recovery Order | 순서 |
| Evidence | Drill 결과 |

---

# 14. 01↔51 대응 주의

예:

```text
...01 ↔ ...51
```

은 이해를 위한 대표 Pattern이다.

프로젝트 전체에서 1:1 대응이 반드시 의무인지 여부는 별도 할당정책/ADR로 확정한다.

---

# 15. 개발환경 Physical View

목표:

```text
Logical Structure는 운영과 유사
Data/Account/Secret/External은 격리
```

금지:

```text
개발 Account → 운영 DB
개발 Secret → 운영 Secret 재사용
개발 Host → DR Sequence 51~99
```

---

# 16. 선도환경 Physical View

목표:

```text
Pilot / Early Validation
```

규칙:

```text
운영 논리체계 유지
축소 규모 허용
정합성/성능 한계 기록
```

---

# 17. 검증환경

환경 코드 Baseline:

```text
v = 검증
```

검증 Host도 주센터 순번:

```text
01~49
```

대역을 사용한다.

---

# 18. HW 구성도와 SW 구성도 분리

HW View:

```text
Host
CPU
Memory
Disk
Network
Appliance
Center
```

SW View:

```text
OS
WEB/WAS
Framework
DB
ETL
CDC
Monitoring
Security
```

두 View를 한 표에서 의미 없이 섞지 않는다.

---

# 19. 3.2 소프트웨어 구성도

PPT 공식 환경:

```text
운영
DR
개발
선도
```

각 환경에서:

```text
Host/Node
  ↓
OS
  ↓
Middleware
  ↓
Application/Solution
```

관계를 표시한다.

---

# 20. SW 구성도 규칙

```text
SW 구성도
↔
SW 목록
↔
Physical Inventory
```

금지:

```text
구성도에만 존재
목록에만 존재
버전 불일치
Host 미지정 SW
```

---

# 21. 3.3 하드웨어 목록

PPT 전개:

```text
운영 1/3
운영 2/3
운영 3/3
DR
개발
선도
```

Inventory Field:

```text
Hostname
Logical Node
Center
Environment
Type
CPU
Memory
Storage
Network
HA
DR Pair
Owner
Evidence
```

---

# 22. 3.4 소프트웨어 목록

PPT에는 운영 S/W **17종 및 버전**이 선언되어 있다.

원칙:

```text
PPT 공식 목록 우선
```

본 정의서에서 해당 목록 밖 제품/버전을 창작하지 않는다.

SW Inventory:

```text
SW ID
Product
Version
Role
Host / Host Group
Environment
License
Support
Config Baseline
Owner
Evidence
```

---

# 23. 운영 S/W 17종 규칙

현재 이 문서에 공식 17종 전체 이름/버전이 전사되지 않은 경우:

```text
[OPEN]
원본 PPT SW 목록 장표에서 전수 추출
```

로 유지한다.

금지:

```text
일반론으로 17종 채우기
```

---

# 24. WEB/WAS Working Baseline

프로젝트 반복 기준:

```text
User
 ↓
GSLB
 ↓
L4
 ↓
WEB
 ↓
WAS / Tomcat JVM
 ↓
Business Application
 ↓
Hikari / MyBatis
 ↓
DB
```

주의:

```text
Apache/Tomcat
= Working Baseline
```

실제 Host별 설치 제품/버전은 Inventory/Config로 검증한다.

---

# 25. Server / JVM / WAR 경계

```text
Physical Server / VM
┌─────────────────────────────────┐
│ OS                              │
│                                 │
│ Tomcat JVM #1                   │
│ ├─ Heap                         │
│ ├─ Thread                       │
│ ├─ WAR-A                        │
│ └─ WAR-B [Inventory 확인]       │
│                                 │
│ Tomcat JVM #2 [가능]            │
│ └─ 별도 Port/CATALINA_BASE      │
└─────────────────────────────────┘
```

---

# 26. 관리 단위 구분

```text
Server / VM
≠ JVM
≠ WAR
≠ Module
≠ Process
```

필수 식별:

```text
Hostname
PID
OS Account
JVM
CATALINA_BASE
Listen Port
WAR
Datasource
Log
HA Group
```

---

# 27. PDMG Deployment Mapping

현재 구조:

```text
pdmg-service
= Business Application Runtime

pdmg-fw
= Library / Bean inside Application Runtime

pdmg-jwt
= 별도 HTTP Process 가능

pdmg-ui
= 별도 UI Process 가능

pdmg-om
= Current Physical Evidence 부족
```

---

# 28. pdmg-fw 물리 표현 금지

잘못:

```text
pdmg-service Server
    ↓ HTTP
pdmg-fw Server
```

현재 Source 해석:

```text
one JVM / Spring Context
├─ Business Bean
└─ Framework Bean
```

---

# 29. Capacity Evidence 읽는 법

Capacity 수치는 다음 태그로 관리한다.

```text
[CAPACITY BASELINE VARIANT]
```

즉:

```text
해당 문서/가정에서의 후보
≠ 최종 운영표준
```

---

# 30. 사용자/TPS Capacity Variant

업로드된 Capacity 자료의 반복 가정:

```text
6,000 지점 × 6명
= 36,000 사용자
```

동시요청률 예:

```text
5%  → 600 TPS 후보
10% → 1,200 TPS 후보
15% → 1,800 TPS 후보
```

이 값은 산정 시나리오이며 실제 성능시험으로 보정한다.

---

# 31. CPU/Memory Variant

Evidence Set에는 여러 VM 후보가 존재한다.

```text
8C / 32G
16C / 64G
16C / 128G
32C / 256G
```

원칙:

```text
Memory 증가
≠ Thread/TPS/DB Pool 자동 증가
```

---

# 32. 32C/256G Variant

특정 용량산정 문서 후보:

```text
Tomcat maxThreads
= 1,200 ~ 1,500

JVM Heap
= 32 ~ 48GB
```

태그:

```text
[CAPACITY BASELINE VARIANT]
```

성능시험 전 최종값으로 확정하지 않는다.

---

# 33. Session Conflict

Evidence에는:

```text
Session 60분
vs
Session 90분
```

이 존재한다.

따라서:

```text
[CONFLICT]
```

최종 승인본/실제 Config/운영정책으로 닫는다.

---

# 34. Hikari Conflict

예:

```text
SingleView
70~80
vs
100~120
```

Variant가 존재한다.

최종 Pool은:

```text
TPS
SQL Duration
DB Session Capacity
Failover
Load Test
```

를 함께 보고 결정한다.

---

# 35. PDMG Worker Snapshot

현재 PDMG 분석:

```text
timeout = 5000ms
worker pool = 20
queue = 100
```

태그:

```text
[AS-IS SNAPSHOT]
```

이 값을:

```text
Tomcat maxThreads
Hikari
NSIGHT SLA
```

로 자동 승격하지 않는다.

---

# 36. Capacity Chain

```text
Incoming Request
      ↓
WEB Connection
      ↓
Tomcat Request Thread
      ↓
PDMG Worker
      ↓
Hikari Connection
      ↓
DB Session
      ↓
SQL
```

각 Pool은 서로 다른 Resource Pool이다.

---

# 37. Pool 크기 동일화 금지

잘못:

```text
Tomcat 1200
→ Worker 1200
→ Hikari 1200
```

금지.

각 Pool은:

```text
Concurrency
Blocking Time
DB Capacity
Queueing
Failure Isolation
```

관점으로 산정한다.

---

# 38. JVM Memory Boundary

```text
VM Memory
├─ JVM Heap
├─ Metaspace
├─ Thread Stack
├─ Direct Buffer
├─ Native Library
└─ OS / Cache
```

VM Memory 전체를 Heap으로 할당하지 않는다.

---

# 39. Physical Capacity Checklist

```text
User
TPS
Response Time
Thread
Worker
Connection
DB Session
CPU
Memory
GC
I/O
Network
Failover
```

모두 연결해 산정한다.

---

# 40. 4.1 DB 아키텍처 구성도

기본:

```text
Core / Source
   │
   ├─ CDC / OGG
   │      ↓
   │   Downstream
   │      ↓
   │     RDW
   │
   └─ BCV / ETL
          ↓
         RDW
          │
          └─ ETL
              ↓
             ADW
```

---

# 41. DB Architecture 핵심

```text
RDW ≠ ADW
CDC ≠ ETL
```

그리고:

```text
Source DB
≠ Information System Bulk Query Target
```

---

# 42. RDW 정의

RDW:

```text
Near Real-time SoR
Operational Query
Marketing Data
Log / Feedback
Near Real-time Summary
Near Real-time Report Mart
```

부적합:

```text
Unlimited Bulk Analytics
```

---

# 43. ADW 정의

ADW:

```text
Analytical SoR
Aggregation
Mart
Bulk Analysis
BI
Analysis Support
```

부적합:

```text
실시간 운영 DB처럼 사용
```

---

# 44. RDW / ADW 비교

| 항목 | RDW | ADW |
|---|---|---|
| 주 역할 | 운영/준실시간 | 분석/대량 |
| 유입 | CDC + ETL | RDW→ETL |
| 소비 | MP/BI/운영조회 | BI/Analytics |
| 부하 | Low-latency | Bulk/Complex |
| 위험 | 분석 부하 전이 | 실시간 요구 오용 |

---

# 45. Data Platform Physical View

```text
Core DB
  │
  ├─ OGG/CDC
  ▼
Downstream
  ▼
RDW Exadata RAC
  │
  └─ ETL
      ▼
ADW Exadata RAC
```

---

# 46. 4.2 DB 이중화 구성도

Baseline:

```text
RDW Exadata RAC
 ├─ Node 1
 └─ Node N
 Active-Active

ADW Exadata RAC
 ├─ Node 1
 └─ Node N
 Active-Active
```

정확한 Node 수는:

```text
PPT 구성도
+
DB Inventory
```

로 확정한다.

---

# 47. RAC와 DR 구분

```text
RAC
= 센터/클러스터 내부 DB 가용성

DR
= 센터 장애 복구
```

따라서:

```text
RAC
≠ Center DR
```

---

# 48. DB DR 원칙

핵심:

```text
Integrity First
```

즉:

```text
이론적 DB Active-Active
```

를 무조건 채택하는 것이 아니라:

```text
Data Consistency
Operation Complexity
Recovery
Reconciliation
```

를 우선 고려한다.

---

# 49. 4.3 OGG 구성도

```text
Core DB
   ↓
OGG Capture
   ↓
Trail / Queue
   ↓
Downstream / Relay
   ↓
RDW Apply
```

목적:

```text
Source Load Minimize
Change Delivery
Checkpoint Recovery
```

---

# 50. CDC must 항목

CDC 설계에서 반드시 정의:

```text
Capture 대상
DDL 대응
Commit 순서
Trail / Queue
Insert / Update / Delete
Checkpoint
Lag
Error
Replay / Recovery
Owner
```

---

# 51. CDC SLA

Vision 자료에:

```text
30초
vs
3초
```

Conflict가 존재한다.

따라서:

```text
[CONFLICT]
```

측정구간 정의 전 최종값을 쓰지 않는다.

예:

```text
Source Commit
→ Capture
→ Apply
→ Query Visible
```

중 어디까지인지 확정해야 한다.

---

# 52. ETL 경로

```text
Core BCV
→ ETL
→ RDW

RDW
→ ETL
→ ADW

DW
↔ ETL
↔ Other System
```

---

# 53. CDC vs ETL

| 구분 | CDC | ETL |
|---|---|---|
| 목적 | Change Propagation | Bulk/Transform |
| 시간성 | Real/Near RT | Batch |
| 단위 | Change | Dataset |
| 복구 | Checkpoint | Restart Point |
| 주요 위험 | Lag/Order | Window/Volume |

---

# 54. 유입 금지 패턴

금지:

```text
CDC에 대량 Batch 혼재
ETL을 Real-time처럼 사용
Core Source DB에 정보계 대량조회 직결
비표준 JDBC/File 유입
CDC/ETL Owner 미분리
```

---

# 55. Application ↔ DB Access Matrix

모든 DB 접근은 등록한다.

| Calling System | Target DB | Access | Account | R/W | Owner | Status |
|---|---|---|---|---|---|---|
| Marketing | RDW | JDBC/Framework | TBD | Contract | TBD | Open |
| BI | RDW/ADW | Query/BI | TBD | Read 중심 | TBD | Open |
| ETL | RDW/ADW | ETL | TBD | R/W | Data | Open |
| CDC | RDW | OGG | TBD | Apply | Data | Open |

실제 계정명은 Source/Inventory에서 확정한다.

---

# 56. DB 접근 원칙

must:

```text
Caller
Target DB
Method
Account
Privilege
Owner
```

가 Access Matrix에 존재해야 한다.

없는 경로:

```text
배포/개통 금지
```

---

# 57. DB 접근 금지

```text
Application A → Application B DB 직접 DML
공유 DBA Account
Core DB 대량 Direct Query
Access Matrix 없는 JDBC
```

---

# 58. DB Account Rule

다음 계정은 분리한다.

```text
Application Account
ETL Account
CDC Account
DBA Account
Monitoring Account
```

최소권한을 적용한다.

---

# 59. 4.4 OLTP 및 대용량 배치 수행 방안

목표:

```text
OLTP Peak
≠
Large Batch Window
```

---

# 60. 자원 경합 축

```text
AP Thread
Worker
DB Connection
DB Session
SQL CPU
I/O
ETL Process
Batch Window
```

---

# 61. OLTP / Batch 분리 원칙

1. Online AP와 Batch/ETL 실행 Node 분리
2. DB 공유 시 Resource/Window 제어
3. 대량 단일 TX 금지
4. Restart 가능한 Chunk
5. Batch Owner/SLA/Window 명시
6. Peak Time 충돌 금지
7. Runtime Metric으로 검증

---

# 62. Large Transaction 금지

잘못:

```text
수백만 건
→ one transaction
```

위험:

```text
Undo
Lock
Rollback Time
Recovery
```

정상:

```text
Chunk
Checkpoint
Restart
Idempotency
```

---

# 63. DB HA / Failure Scenario Handoff

RUNTIME에서 검증:

```text
RDW Node Failure
ADW Node Failure
CDC Failure
ETL Failure
Center Failure
```

PHYSICAL은:

```text
Topology
Pair
Replication
```

까지 정의한다.

---

# 64. 5. 시스템 표준 전체 구조

```text
System Standard
├─ Hostname
├─ Filesystem
├─ Account
└─ Port
```

목적:

```text
Name
Path
Identity
Communication
```

을 표준화한다.

---

# 65. 5.1 서버 호스트 명명규칙

Hostname:

```text
고정 12자리
영문 소문자 + 숫자
구분자 없음
```

구조:

```text
법인(2)
+ Application(4)
+ Platform/Server(1)
+ Environment(1)
+ Role(2)
+ Sequence(2)
```

---

# 66. Hostname Anatomy

```text
1-2   3-6   7   8   9-10  11-12
 sb   mpco   l   o    wb     01

→ sbmpcolowb01
```

---

# 67. 법인 코드

공식 표:

| 코드 | 법인 |
|---|---|
| `sb` | 상호금융 |
| `nh` | 중앙회(상호금융 제외) |
| `nb` | 은행 |
| `fg` | 금융지주 |
| `ag` | 경제지주 |

---

# 68. Application Code

```text
대구분 2
+
업무구분 2
```

예:

```text
mpco
```

CMDB 승인 코드 사전 필수.

---

# 69. Platform/Server Code

1자리 Code는 공식 표준 코드표를 사용한다.

예시 Hostname의:

```text
l
```

은 해당 Baseline 예시다.

표에 없는 Code를 창작하지 않는다.

---

# 70. Environment Code

공식:

| Code | 의미 |
|---|---|
| `o` | 운영 |
| `v` | 검증 |
| `t` | 개발 |

중요:

```text
DR
→ 환경 o 유지
→ Sequence 51~99로 구분
```

금지:

```text
d 같은 임의 DR Environment Code
```

---

# 71. Role Code

공식 표:

| Code | Role |
|---|---|
| `ap` | AP |
| `db` | DB |
| `wb` | WEB |
| `ws` | WAS |
| `bk` | Backup |
| `bt` | Batch |
| `tl` | ETL |

표에 없는:

```text
mq
kf
```

등을 임의 추가하지 않는다.

필요하면 표준 개정/ADR.

---

# 72. Sequence

| Range | 의미 |
|---|---|
| `01~49` | 의왕 주센터 |
| `51~99` | 안성 DR |
| `00` | 금지 |
| `50` | 금지 |

---

# 73. Hostname 예시

운영 WEB:

```text
sbmpcolowb01
```

DR WEB:

```text
sbmpcolowb51
```

개발 WEB:

```text
sbmpcoltwb01
```

---

# 74. Hostname 검증식

1차 형식검사:

```regex
^(sb|nh|nb|fg|ag)[a-z]{4}[iholwx][ovt](ap|db|wb|ws|bk|bt|tl)(0[1-9]|[1-4][0-9]|5[1-9]|[6-9][0-9])$
```

주의:

```text
Regex PASS
≠ CMDB Code Valid
```

Application Code는 사전 대조 필수.

---

# 75. Hostname 금지

```text
12자리 아님
대문자
구분자
별칭 Hostname
CMDB 미등록 앱코드
표 밖 Role Code
00 / 50
개발/검증에 51~99
DR에 01~49
```

---

# 76. 5.2 파일 시스템 구성

Filesystem은 다음 세 축으로 관리한다.

```text
OS Standard Mount
AP Business Path
DB Business Path
```

예외는 예외표로 등록한다.

---

# 77. OS Standard Mount

대표 Baseline:

| Mount | 역할 |
|---|---|
| `/` | root |
| `/boot` | boot |
| `/etc` | configuration |
| `/dev` | device virtual FS |
| `/root` | root home |
| `/usr` | OS program |
| `/home` | 일반 사용자 home |
| `/tmp` | temporary |
| `/var` | variable/log |
| `/var/crash` | dump |
| `/userdir` | 업무 계정 home |
| `/<SW명>` | 솔루션 설치 |

---

# 78. 업무 Account Home

원칙:

```text
업무 서비스 계정
→ /userdir
```

금지:

```text
업무 서비스 계정
→ /home
```

---

# 79. AP 프로그램 경로

```text
/pgm
```

용도:

```text
Application Deploy / Binary
```

HA 시:

```text
/pgm_<hostname>
```

등 접미 규칙은 표준표에 등록해야 한다.

---

# 80. AP 로그 경로

```text
/aplog
```

용도:

```text
Application Log
```

Program/Data와 혼재하지 않는다.

---

# 81. `/nhod` 규칙

금지:

```text
/nhod
→ DB Data Storage
```

자료의 표준 용도 밖 Data 저장 금지.

---

# 82. FS 예외

표준 밖 Mount가 필요하면:

```text
Purpose
Capacity
Owner
Backup
Permission
Retention
Expiry
ADR/Approval
```

을 예외표에 등록한다.

---

# 83. FS 금지

```text
임의 /app1 /data1
업무계정 /home
DB Data /nhod
Log와 Program 혼재
예외표 없는 Path
```

---

# 84. 5.3 사용자 계정

계정 설계 원칙:

```text
Environment Separation
Role Separation
Least Privilege
Secret Separation
```

---

# 85. 계정 분류

대표:

```text
Engine Account
Business Service Account
Solution Account
DB Account
Monitoring Account
Deployment Account
```

---

# 86. 업무계정 Code

기존 시스템 표준은:

```text
업무계정 4자리 표준표
```

를 기준으로 관리한다.

정확한 계정명은 공식 계정표에서 추출한다.

---

# 87. Account 금지

```text
공용 Password
개발/운영 동일 Secret
개인 Account로 서비스 기동
비공식 운영 Account
Source Repository에 Password
```

---

# 88. Secret Boundary

```text
Code
≠
Password
≠
Private Key
≠
Token Secret
```

비밀정보는 CI/CD/Secret Store 등 승인된 관리영역으로 분리한다.

---

# 89. 5.4 서비스 포트 현황

Port는 Host 개인 설정이 아니다.

```text
Port
= Managed Inventory Object
```

---

# 90. Port Inventory

필수:

```text
System
Hostname
Role
Protocol
Listen Port
Source Zone
Target Zone
Firewall Rule
Load Balancer Rule
Owner
Evidence
Status
```

---

# 91. Port Baseline

확인된 예:

```text
Tomcat 8080
Oracle Listener
```

은 승인된 구간에서만 접근한다.

모든 포트가 8080이라는 의미가 아니다.

---

# 92. Port OPEN 처리

공식 표에서 미기재/OPEN인 값:

```text
[OPEN]
```

으로 유지한다.

금지:

```text
임의 Port 숫자 생성 후 상시 개통
```

---

# 93. FW/LB와 Port 정합

```text
Application Config
   ↕
Port Inventory
   ↕
Firewall
   ↕
Load Balancer
```

불일치하면 개통 금지.

---

# 94. Physical Inventory SSOT

최종 관리 대상:

```text
Server Inventory
Software Inventory
DB Inventory
Port Inventory
Account Inventory
Filesystem Inventory
HA/DR Mapping
```

---

# 95. Inventory Cross Check

```text
Physical Definition
        ↓
CMDB
        ↓
DNS
        ↓
IaaS
        ↓
Monitoring
        ↓
Backup
        ↓
Deployment
```

Hostname/Role 불일치:

```text
Deployment Gate FAIL
```

---

# 96. Server Master Inventory

필수 Field:

```text
Hostname
Logical Node
System Group
Environment
Center
Role
OS
CPU
Memory
Storage
JVM
Artifact
Port
DataSource
HA Group
DR Pair
Monitoring ID
Backup
Owner
Evidence
```

---

# 97. PDMG Physical Mapping Field

PDMG를 실제 Host에 연결할 때:

```text
pdmg-service
→ WAR
→ JVM
→ Host
→ Port
→ Datasource
```

Trace를 만들어야 한다.

현재 Host Mapping이 완전하지 않으면 `[GAP]`.

---

# 98. HA Physical Principle

HA 대상:

```text
WEB
WAS
Critical AP
DB RAC
Integration
```

구현 방식은 역할별로 다르다.

---

# 99. AP Active-Active

Working Baseline:

```text
AP Active-Active
```

예:

```text
L4
├─ WAS #1
└─ WAS #2
```

실제 세션/상태 전략은 RUNTIME/Security와 정합해야 한다.

---

# 100. Session HA Conflict

Evidence:

```text
60 min Variant
vs
90 min Variant
```

DeltaManager 관련 자료도 존재한다.

따라서 최종 Session/Replication 정책은 `[OPEN/CONFLICT]`.

---

# 101. Center DR Principle

```text
의왕
  ↓
Center Failure
  ↓
안성
```

필수 전환 대상:

```text
Traffic
Application
Config
Data
Batch
Interface
Monitoring
```

---

# 102. DR 재로그인 가능성

기존 Capacity/Session 자료에는:

```text
센터간 Session Replication 미적용
→ 재로그인
```

방향이 존재한다.

최종 보안/세션 정책과 정합 필요.

---

# 103. JWT Key HA

PDMG Security 자료와 Physical Architecture의 연결:

```text
JWT Instance Scale-out
→ Shared/Versioned Key Store
→ kid
→ JWKS
```

per-process Key 생성은 Production HA/DR과 충돌 가능.

태그:

```text
[RISK/GAP]
```

---

# 104. Backup Physical Principle

Backup 대상:

```text
DB
Application Artifact
Config
File
Key/Certificate
Operational Data
```

각 대상의 Backup/Restore 방식이 다르다.

---

# 105. Backup 완료 정의

```text
Backup Success
≠ Recovery Proven
```

RUNTIME에서 Restore Test를 검증한다.

---

# 106. Security Physical Boundary

Physical Security:

```text
Network Segment
Port
Account
Secret
TLS
Key
File Permission
DB Privilege
```

---

# 107. Direct Access 금지

```text
Internet
-X→ WAS Internal Port

External
-X→ DB

User
-X→ Oracle Listener
```

승인된 Zone/LB/Firewall 경로만 허용한다.

---

# 108. Observability Physical Boundary

각 Host/JVM/DB는 최소:

```text
Host ID
JVM ID
Application ID
Port
Datasource
Metric
Log Path
```

와 연결되어야 한다.

---

# 109. Monitoring ID

CMDB Hostname과 Monitoring Entity가 다르면:

```text
Traceability Gap
```

이다.

별칭만으로 운영하지 않는다.

---

# 110. Capacity Drift

```text
Design
maxThreads 1200
        ↓
Actual Config
800
        ↓
Runtime
Queue / Latency
```

같은 차이를 Drift로 관리한다.

---

# 111. Physical Configuration Drift

비교 대상:

```text
Architecture
CMDB
server.xml
application.yml
httpd.conf
JVM Options
Hikari Config
DB Config
```

---

# 112. Capacity Baseline vs Actual

표:

| 항목 | Design | Actual | Runtime | Status |
|---|---|---|---|---|
| CPU | Candidate | Inventory | Usage | Drift |
| Heap | Candidate | JVM Option | GC | Drift |
| Thread | Candidate | server.xml | Busy | Drift |
| Worker | PDMG Snapshot/Target | app config | Queue | Drift |
| Hikari | Candidate | app config | Pending | Drift |

---

# 113. SW Version Drift

```text
PPT SW List
   ↓
CMDB
   ↓
Installed Version
```

다르면:

```text
[DRIFT]
```

---

# 114. Database Drift

비교:

```text
RDW/ADW Role
RAC Node
OGG Route
Account
Privilege
CDC Lag
```

Architecture와 실제 Config를 대사한다.

---

# 115. Part B — 물리 준수 규범

## 115.1 신설 규칙

shall:

```text
Logical Node 존재
→ Physical Host 생성
```

예외:

```text
ADR 승인
```

---

# 116. 환경별 구성도 규칙

must:

```text
운영
DR
개발
선도
검증
```

을 별도로 관리한다.

---

# 117. SW 동기화 규칙

must:

```text
SW Diagram
=
SW Inventory
=
Installed SW
```

---

# 118. Capacity 근거 규칙

대수/CPU/Memory/Scale은:

```text
Capacity Document
+
Inventory
```

를 인용한다.

감(感) 산정 금지.

---

# 119. Center 규칙

must:

```text
Center Field
= 의왕 / 안성 / 승인센터
```

Hostname Sequence와 정합해야 한다.

---

# 120. DR Sequence 규칙

```text
안성 DR
→ 51~99
```

개발/검증:

```text
-X→ 51~99
```

---

# 121. 물리 매핑 규칙

must Field:

```text
Logical Node
Environment
Center
Hostname
Count/Type
OS/Platform
System Group
SW/Version
```

---

# 122. 선도 규칙

Pilot:

```text
Same Logical Architecture
+
Reduced Capacity
```

축소 사유 기록.

---

# 123. DB 준수 규범

shall:

```text
RDW / ADW 역할 분리
```

---

# 124. RAC 규범

DB 설계는:

```text
RAC/HA Topology
```

를 명시하고 단일노드 가정 금지.

노드 수는 Inventory 근거.

---

# 125. CDC 규범

CDC:

```text
Capture
Order
Trail
Apply
Checkpoint
Lag
Recovery
```

를 정의해야 한다.

---

# 126. Source DB 보호 규범

금지:

```text
정보계 대량 조회
→ Core Source DB Direct
```

Downstream/BCV 등 승인 경로를 사용한다.

---

# 127. DB Access 규범

Access Matrix에 없는:

```text
JDBC
DML
Account
```

는 개통하지 않는다.

---

# 128. OLTP/Batch 규범

must:

```text
Peak / Batch Window
Resource Separation
Restart
Monitoring
```

을 정의한다.

---

# 129. Hostname 준수 규범

must:

```text
12 chars
lowercase + digit
official code
unique
```

---

# 130. FS 준수 규범

must:

```text
Standard Mount
AP Program / Log separation
Business Home /userdir
Exception Table
```

---

# 131. Account 준수 규범

must:

```text
Environment separation
Role separation
Least privilege
Secret management
```

---

# 132. Port 준수 규범

must:

```text
Inventory registration
Conflict check
FW/LB match
```

---

# 133. 개통 Gate

개통 전:

```text
Logical Mapping PASS
Hostname PASS
SW PASS
FS PASS
Account PASS
Port PASS
DB Access PASS
Monitoring PASS
Backup PASS
```

---

# 134. Physical Anti-Pattern

## A. 논리 없는 서버

```text
"필요할 것 같아서 VM 생성"
```

금지.

---

## B. 환경 혼재

하나의 구성도에서 운영/DR/개발 Host를 섞어 표현.

금지.

---

## C. 유령 Software

```text
Diagram O
Inventory X
```

금지.

---

## D. 임의 Hostname

```text
Mkt-Web-01
```

금지.

---

## E. DR 환경코드 d

금지.

---

## F. Core DB 직결

정보계 대량 Query가 Source DB를 직접 사용.

금지.

---

## G. CDC=ETL

금지.

---

## H. Pool Same Size

Tomcat/Worker/Hikari를 같은 값으로 맞춤.

금지.

---

## I. Backup만 있고 Restore Test 없음

PHYSICAL 완료 아님.

---

# 135. Physical Inventory Template

| Hostname | Logical Node | Env | Center | Role | HW | OS | SW | JVM | DB | HA | DR | Owner |
|---|---|---|---|---|---|---|---|---|---|---|---|---|
| TBD | MP-WEB | 운영 | 의왕 | WEB | TBD | TBD | PPT List | - | - | Y | Pair | TBD |
| TBD | MP-WAS | 운영 | 의왕 | WAS | TBD | TBD | WAS | JVM | RDW | Y | Pair | TBD |
| TBD | RDW | 운영 | 의왕 | DB | Exadata | TBD | Oracle | - | RDW | RAC | DR | DBA |

실제 값은 Inventory에서 채운다.

---

# 136. Operating ↔ DR Mapping Template

| Logical Role | Operating | DR | Artifact | Data | Switch | Owner | Evidence |
|---|---|---|---|---|---|---|---|
| MP-WEB | …01 | …51 | Web | N/A | GSLB/L4 | TBD | Drill |
| MP-WAS | …01 | …51 | WAR | RDW | GSLB/L4 | TBD | Drill |
| RDW | Primary | DR | DB | Replication | DB Procedure | DBA | Drill |

---

# 137. Software Inventory Template

| SW | Version | Role | Host Group | Env | License | Config | Evidence | Status |
|---|---|---|---|---|---|---|---|---|
| PPT SW #1 | PPT | TBD | TBD | 운영 | TBD | TBD | PPT | Open |
| ... | ... | ... | ... | ... | ... | ... | ... | ... |

17종은 PPT 원본에서 전수 추출 후 채운다.

---

# 138. DB Inventory Template

| DB | Role | Platform | RAC | Environment | Center | Source | Consumer | Owner | Status |
|---|---|---|---|---|---|---|---|---|---|
| RDW | Near RT | Exadata | AA | 운영 | 의왕 | CDC/ETL | MP/BI | DBA | Baseline |
| ADW | Analytics | Exadata | AA | 운영 | 의왕 | ETL | BI | DBA | Baseline |

---

# 139. DB Access Matrix Template

| Caller | Target DB | Method | Account | Privilege | R/W | SLA | Owner | Status |
|---|---|---|---|---|---|---|---|---|
| MP-WAS | RDW | JDBC | TBD | TBD | Contract | TBD | App/Data | Open |
| BI | ADW | Query | TBD | Read | R | TBD | BI/Data | Open |
| ETL | RDW/ADW | ETL | TBD | ETL | R/W | Batch | Data | Open |

---

# 140. Filesystem Inventory Template

| Host | Mount | Purpose | Capacity | Owner | Backup | Exception | Status |
|---|---|---|---|---|---|---|---|
| TBD | /pgm | Program | TBD | App | TBD | No | Baseline |
| TBD | /aplog | Log | TBD | App/Ops | TBD | No | Baseline |
| TBD | /userdir | Service Home | TBD | Ops | TBD | No | Baseline |

---

# 141. Account Inventory Template

| Account | Type | Env | Host/DB | Role | Privilege | Secret Store | Owner | Status |
|---|---|---|---|---|---|---|---|---|
| TBD | Business | 운영 | WAS | Service | Minimum | TBD | TBD | Open |
| TBD | ETL | 운영 | ETL/DB | ETL | Contract | TBD | Data | Open |

---

# 142. Port Inventory Template

| Host | Process | Protocol | Port | Source | Target | FW | LB | Owner | Status |
|---|---|---|---:|---|---|---|---|---|---|
| TBD | Tomcat | TCP | 8080 | WEB | WAS | Approved | L4/Proxy | App/Ops | Baseline candidate |
| TBD | Oracle Listener | TCP | TBD | Approved App | DB | Approved | N/A | DBA | Open |

---

# 143. NFR → PHYSICAL Mapping

| NFR | Physical Design |
|---|---|
| Performance | CPU/Memory/Thread/Pool/DB/I/O |
| Availability | Host Pair/RAC/L4/GSLB |
| Scalability | Scale-out Host Group |
| Security | Network/Port/Account/Secret/FS |
| Observability | Host/JVM/DB/Pool Monitoring |
| Recoverability | DR Pair/Backup/Restore |

---

# 144. Physical Failure Domain

```text
Process Failure
JVM Failure
Host Failure
WEB Failure
WAS Group Failure
DB Node Failure
RAC Failure
Network/L4 Failure
Center Failure
```

각 Failure는 같은 것이 아니다.

---

# 145. HA vs DR

```text
HA
= 같은 서비스의 단일 장애 대응

DR
= 센터급 재해 대응
```

한 Architecture Box로 합치지 않는다.

---

# 146. Data Integrity vs Availability

DB DR는:

```text
Availability
+
Integrity
```

를 동시에 만족해야 한다.

무리한 Active-Active로 정합성을 훼손하지 않는다.

---

# 147. Runtime Evidence Handoff

PHYSICAL에서 RUNTIME으로 넘기는 Evidence:

```text
Host
JVM
Artifact
Config
DB
HA Pair
DR Pair
Port
Capacity
Monitoring ID
```

---

# 148. GAP Register

| ID | GAP | 영향 | 조치 |
|---|---|---|---|
| GAP-PH-01 | 운영 S/W 17종 전수목록 미전사 | SW Baseline | PPT 추출 |
| GAP-PH-02 | 모든 Logical Node의 Host Mapping 미완성 | Deployment | CMDB Mapping |
| GAP-PH-03 | CPU/Memory 최종 승인값 | Capacity | 성능시험 |
| GAP-PH-04 | Session 60/90분 Conflict | HA/Capacity | ADR |
| GAP-PH-05 | Hikari Variant | DB Capacity | Load Test |
| GAP-PH-06 | RDW/ADW 실제 RAC Node 수 | DB HA | DB Inventory |
| GAP-PH-07 | CDC SLA 3/30초 Conflict | Data NFR | ADR |
| GAP-PH-08 | RTO/RPO 최종값 | DR | DR ADR |
| GAP-PH-09 | 전체 FS 상세경로 | OS Standard | 표준표 전수 |
| GAP-PH-10 | 전체 Account/Port | Security/Network | Inventory |
| GAP-PH-11 | PDMG Physical Host Mapping | Traceability | Deployment Evidence |
| GAP-PH-12 | JWT Key Store 물리구성 | Security HA | Security ADR |

---

# 149. ADR 후보

| ADR | 주제 |
|---|---|
| ADR-PH-01 | 운영↔DR Host Mapping |
| ADR-PH-02 | Session/Replication |
| ADR-PH-03 | Final WAS Capacity |
| ADR-PH-04 | Hikari/DB Session Budget |
| ADR-PH-05 | CDC SLA |
| ADR-PH-06 | DB DR/Replication |
| ADR-PH-07 | PDMG Deployment Unit |
| ADR-PH-08 | JWT Key HA |
| ADR-PH-09 | Non-standard Host Role Code |
| ADR-PH-10 | FS Exception |

---

# 150. Verification Checklist — 3장

```text
[ ] Logical Node가 모두 물리 Host와 연결되는가
[ ] 의왕/안성이 구분되는가
[ ] 운영/DR/개발/선도/검증이 분리되는가
[ ] 01~49/51~99가 센터와 정합되는가
[ ] HW와 SW View를 혼재하지 않았는가
[ ] SW 구성도와 SW 목록이 일치하는가
[ ] Capacity 값에 Evidence/Variant가 붙는가
[ ] 운영↔DR 대응표가 있는가
```

---

# 151. Verification Checklist — 4장

```text
[ ] RDW/ADW 역할이 분리되는가
[ ] RAC와 Center DR을 구분하는가
[ ] CDC와 ETL을 구분하는가
[ ] Downstream이 존재하는가
[ ] Core Source DB 대량직결이 없는가
[ ] Application↔DB Access Matrix가 있는가
[ ] OLTP/Batch Resource가 분리되는가
[ ] CDC Lag/Checkpoint가 운영대상인가
```

---

# 152. Verification Checklist — 5장

```text
[ ] Hostname이 12자리인가
[ ] CMDB App Code인가
[ ] DR이 환경 o + 51~99인가
[ ] 00/50이 없는가
[ ] Role Code가 표준표에 있는가
[ ] /pgm /aplog /userdir 규칙을 지키는가
[ ] 비공식 Account/공용 Password가 없는가
[ ] Port Inventory와 FW/LB가 일치하는가
```

---

# 153. Physical Completion Gate

```text
G-PH-01 Logical→Physical Mapping
G-PH-02 Center/Environment
G-PH-03 Hostname
G-PH-04 HW Inventory
G-PH-05 SW Inventory
G-PH-06 Capacity Evidence
G-PH-07 DB Role/RAC
G-PH-08 CDC/ETL
G-PH-09 DB Access
G-PH-10 FS/Account/Port
G-PH-11 HA/DR Mapping
G-PH-12 Monitoring/Backup
```

---

# 154. 배포/개통 Gate

다음이 일치해야 한다.

```text
Architecture Definition
=
Physical Inventory
=
CMDB
=
DNS
=
IaaS
=
SW Installed
=
Port/FW/LB
=
Monitoring
=
Backup
```

불일치:

```text
Deploy / Open FAIL
```

---

# 155. MECHANISM Handoff

PHYSICAL Output:

```text
Center
Host/Host Group
HW/SW
JVM/Application
DB
RAC
OGG/CDC Physical Path
Account
Filesystem
Port
HA/DR Pair
Capacity Evidence
```

MECHANISM에서 확정:

```text
Interface Contract
Message
GUID
Charset
Application Layer
Framework
SSO
Error
Logging
Batch Mechanism
Solution Integration
```

---

# 156. PHYSICAL → MECHANISM 연결

```text
[WHERE]
어느 Host/JVM/DB에서 실행되는가
        ↓
[HOW]
어떤 Interface/Framework/Message 규칙으로 실행되는가
```

---

# 157. 최종 평가

PHYSICAL Architecture의 완료상태:

```text
어떤 Logical Node를 보았을 때

어느 Center인가?
어느 Environment인가?
어느 Host인가?
어떤 HW/SW인가?
어떤 JVM/Artifact인가?
어떤 DB를 사용하는가?
어떤 Port/Account/FS인가?
HA Pair는 무엇인가?
DR Pair는 무엇인가?
Monitoring/Backup은 무엇인가?

를 하나의 Inventory로 설명할 수 있어야 한다.
```

최종 핵심 선언:

> **물리 아키텍처는 서버 목록이 아니라 논리 책임을 실제 실행 자원으로 변환하는 계약이며, 그 계약은 CMDB·DNS·배포·DB·네트워크·모니터링·백업에서 동일한 식별자로 검증되어야 한다.**

---

# Appendix A. Center / Sequence Summary

```text
의왕
→ 01~49

안성 DR
→ 51~99

00 / 50
→ 금지
```

---

# Appendix B. Hostname Summary

```text
법인2
+ App4
+ Platform1
+ Env1
+ Role2
+ Seq2
= 12
```

Example:

```text
sbmpcolowb01
```

---

# Appendix C. Role Code Summary

```text
ap AP
db DB
wb WEB
ws WAS
bk Backup
bt Batch
tl ETL
```

---

# Appendix D. FS Summary

```text
/userdir
→ Business Account Home

/pgm
→ Application Program

/aplog
→ Application Log
```

---

# Appendix E. DB Summary

```text
Core
 ├─ CDC/OGG → RDW
 └─ ETL/BCV → RDW
                ↓ ETL
               ADW
```

---

# Appendix F. 본 장에서 확정하지 않는 항목

근거가 완전하지 않으면 다음을 임의 확정하지 않는다.

```text
전체 17종 SW 이름/버전
모든 Hostname
모든 CPU/Memory
최종 Tomcat Thread
최종 Worker Pool
최종 Hikari Pool
Session 최종 시간
RTO/RPO
CDC 최종 SLA
RAC 실제 Node 수
모든 Account 이름
미기재 Port
JWT Key Store 제품
```
