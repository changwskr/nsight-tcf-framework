# NSIGHT / PDMG 아키텍처 정의서 — 04. PHYSICAL

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 문서 ID: NSIGHT-ARCH-TYPE2-PHYSICAL-04  
> Architecture Level: **PHYSICAL**  
> PPT 공식 범위: **3. 물리 인프라 아키텍처 / 4. 데이터베이스 아키텍처 / 5. 시스템 표준 정의**  
> 문서 상태: Draft / Evidence-First / PPT-Aligned  
> 작성일: 2026-08-31  
> 선행: `03_LOGICAL`  
> 후속: `05_MECHANISM`  
> Evidence Level: PPT Target + Physical/DB/System Standard Definition Baseline

---

# 0. Evidence Register

| ID | 근거 | 본 장 사용 목적 | 상태 |
|---|---|---|---|
| EV-PH-01 | `03_물리인프라아키텍처_정의서.md` | 센터·환경·논리→물리 매핑·HA/DR·Inventory | `[WORKING BASELINE]` |
| EV-PH-02 | `04_데이터베이스아키텍처_정의서.md` | RDW/ADW·RAC·OGG·CDC/ETL·OLTP/배치 | `[WORKING BASELINE]` |
| EV-PH-03 | `05_시스템표준_정의서.md` | 12자리 Hostname·FS·계정·포트 | `[WORKING BASELINE]` |
| EV-PH-04 | `03_LOGICAL_TYPE2_PPT정합본` | Logical Node/Environment Handoff | `[CURRENT BASELINE DRAFT]` |
| EV-PH-05 | PDMG Infrastructure/Capacity 자료 | Application Runtime 물리 Reference | `[AS-IS / CAPACITY REFERENCE]` |

---

# 1. 핵심 결론

PHYSICAL은 LOGICAL에서 정의한 논리 역할을 **센터·환경·Host·HW/SW·DB·용량·표준 식별자**로 매핑하는 단계다.

```text
Logical System / Node
        ↓
Environment
        ↓
Center
        ↓
Physical Host
        ↓
HW / OS / SW
        ↓
DB / Network / Port
        ↓
HA / DR / Backup
        ↓
Inventory / CMDB / DNS / Monitoring
```

핵심 원칙:

```text
논리 없는 물리 신설 금지
환경별 구성도 분리
운영↔DR 대응표
SW 구성도 ↔ SW 목록 일치
DB 역할 / 유입 / 접근 분리
Hostname / FS / Account / Port 표준화
```

한 문장:

> **PHYSICAL은 “어느 논리를 어느 물리 자원에 어떻게 구현·식별·이중화할 것인가”를 고정한다.**

---

# 2. 목적 / 범위

본 장은 다음 질문에 답한다.

1. 운영/DR/개발/선도 환경의 HW/SW 구성은 무엇인가?
2. 논리 Node는 어느 센터·Host에 매핑되는가?
3. 운영과 DR Host의 대응 관계는 무엇인가?
4. RDW/ADW는 어떤 DB 역할과 HA 형태를 가지는가?
5. OGG/CDC와 ETL은 어떤 물리 경로로 분리되는가?
6. OLTP와 대용량 배치의 자원 경합을 어떻게 피하는가?
7. Hostname/FS/Account/Port를 어떤 표준으로 관리하는가?
8. Inventory·CMDB·DNS·Monitoring·Backup의 식별자가 일치하는가?

---

# 3. PPT 공식 구조 / Trace

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

PPT 정합 규칙:

- HW 구성도와 SW 구성도를 섞지 않는다.
- 운영/DR/개발/선도 환경을 구분한다.
- PPT에 선언된 운영 SW 목록·버전을 기준으로 하며 근거 없는 제품/버전을 추가하지 않는다.
- 시스템 표준의 실제 값은 PPT/공식 표를 우선한다.

---

# 4. Main Physical Architecture

```text
                     ┌────────────── 의왕 / 주센터 ──────────────┐
                     │                                           │
[Channel] → [WEB] → [WAS / Business AP] → [Integration/Event]  │
                     │        │                  │                │
                     │        ├──────────────┐   │                │
                     │        ▼              ▼   ▼                │
                     │      [RDW]          [ADW] [ETL/CDC]        │
                     │                                           │
                     │ 운영 · 개발 · 선도 · 검증                  │
                     │ Host Sequence 01~49                        │
                     └───────────────────┬───────────────────────┘
                                         │ DR / Replication / Failover
                                         ▼
                     ┌────────────── 안성 / DR센터 ──────────────┐
                     │ DR 대상 WEB/WAS/AP/DB/Integration         │
                     │ Host Sequence 51~99                       │
                     └───────────────────────────────────────────┘
```

주의:

```text
센터/순번 정책 = Physical Identification
DR 실제 전환 절차 = Runtime / Runbook
```

---

# 5. 3.1 하드웨어 구성도

PPT 실제 전개 순서를 유지한다.

```text
운영 / DR 환경
개발 환경
선도 환경
```

## 5.1 센터 Baseline

| 센터 | 역할 | Host 순번 Baseline |
|---|---|---|
| 의왕 | 주센터 / 운영·개발·선도·검증 기본 | 01~49 |
| 안성 | DR센터 | 51~99 |

[WORKING BASELINE]

금지:

```text
DR Host를 01~49에 할당
개발/검증 Host를 51~99에 할당
```

## 5.2 환경 구분

```text
운영
DR
개발
선도
검증
```

PPT 구성도 작성 시 한 그림에서 환경을 섞어 Hostname/센터가 오인되지 않도록 분리한다.

---

# 6. Logical → Physical Mapping

기본 매핑:

```text
Logical Node
  ↓
Environment
  ↓
Center
  ↓
Hostname
  ↓
HW Type / CPU / Memory
  ↓
OS / Middleware / SW
  ↓
Network / Port
  ↓
HA / DR Pair
```

## 6.1 Physical Server Inventory 필수 필드

| 필드 | 필수 |
|---|---|
| Logical Node ID | Y |
| Domain / System Group | Y |
| Environment | Y |
| Center | Y |
| Hostname | Y |
| Role | Y |
| HW Type | Y |
| CPU / Memory | 근거 있을 때 |
| OS | Y |
| SW / Version | 근거 있을 때 |
| Network / Port | 해당 시 |
| HA Pair | 해당 시 |
| DR Pair | 해당 시 |
| Backup Policy | 해당 시 |
| Capacity Evidence | Y |
| Monitoring ID | 해당 시 |
| Evidence | Y |
| Status | Y |

논리 1 → 물리 N Scale-out은 허용한다.

```text
Logical WAS
  ├─ Host 01
  ├─ Host 02
  └─ Host 03 ...
```

---

# 7. 3.2 소프트웨어 구성도

환경별 SW 구성을 분리한다.

```text
운영 SW
DR SW
개발 SW
선도 SW
```

원칙:

```text
SW 구성도
   ↔
SW 목록
   ↔
Inventory / CMDB
```

다음은 금지한다.

```text
구성도에만 존재하는 유령 SW
목록에만 있고 배치 위치가 없는 SW
버전 근거 없는 임의 최신화
```

PPT에서 운영 S/W 종수/버전을 선언한 경우 그 목록을 SSOT로 사용하고, 본 정의서에서 임의 제품을 추가하지 않는다.

---

# 8. 3.3 하드웨어 목록

PPT 전개 기준:

```text
운영 1/3
운영 2/3
운영 3/3
DR
개발
선도
```

HW Inventory는 단순 자산목록이 아니라 LOGICAL과 연결한다.

```text
System Group
  → Logical Node
  → Physical Host
  → Capacity
```

용량값은 별도의 용량산정 근거와 연결한다.

---

# 9. 3.4 소프트웨어 목록

SW Inventory 필드:

```text
SW ID
Product
Version
Role
Logical Node
Host / Host Group
Environment
License / Support
Config Baseline
Owner
Evidence
Status
```

PDMG Source의 Spring Boot/Java/Gradle 버전이 확인되어도, **그 값을 NSIGHT 전체 SW 표준값으로 자동 승격하지 않는다.**

---

# 10. 4.1 DB 아키텍처 구성도

핵심:

```text
Core / Source
   ├─ CDC / OGG → Downstream → RDW
   └─ ETL / BCV ─────────────→ RDW
                                  │
                                  └─ ETL → ADW
```

## 10.1 RDW / ADW 역할

| 구분 | 역할 |
|---|---|
| RDW | 준실시간 SoR, 운영성 조회, 로그/피드백/마케팅 정보 제공 |
| ADW | 분석 SoR, 집계/마트/대량분석 |

금지:

```text
RDW = ADW라고 뭉뚱그림
ADW를 실시간 운영 DB처럼 사용
RDW에서 대량 분석을 상시 점유
```

---

# 11. 4.2 DB 이중화 구성도

[WORKING BASELINE]

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

노드 수는 PPT/Inventory 근거로 확정한다.

```text
RAC 이중화
≠
센터 간 완전한 DB Active-Active DR
```

DR은 **Integrity First** 원칙으로 별도 설계/검증한다.

---

# 12. 4.3 OGG 구성도

```text
Core DB
   ↓ OGG / CDC
Downstream / Relay
   ↓
RDW
```

목적:

```text
원천 계정계 부하 최소화
변경데이터 저지연 전달
체크포인트 기반 복구
```

CDC 설계에 필요한 항목:

```text
Capture 대상
DDL 대응
Commit 순서
Trail / Queue
I/U/D 적용
Checkpoint
Lag Metric
Recovery / Reconciliation
Owner
```

CDC Lag SLA가 미확정이면 `[OPEN]`/ADR로 둔다.

---

# 13. CDC / ETL 책임 분리

```text
CDC / OGG
= 실시간/준실시간 변경 전파

ETL
= 대량·초기·마감·보정·모델링
```

표준 경로:

| Source | Mechanism | Target | 목적 |
|---|---|---|---|
| Core DB | CDC/OGG | RDW | 실시간 변경 |
| Core BCV | ETL | RDW | 대량/보정 |
| RDW | ETL | ADW | 분석 적재 |
| DW | ETL | 외부/타 시스템 | 대량 제공 |
| 타 시스템 | ETL | DW | 대량 수신 |

금지:

```text
CDC에 대량 Batch 책임 혼재
ETL을 실시간처럼 가장
계정계 원천 DB에 정보계 대량 조회 직결
```

---

# 14. Application ↔ DB Access Matrix

모든 접근은 표로 닫는다.

| Calling System | Logical Node | Target DB | Access Type | Account | Read/Write | Owner | Evidence | Status |
|---|---|---|---|---|---|---|---|---|
| Marketing | AP | RDW | JDBC/Framework | TBD | R/W 계약범위 | TBD | PPT/설계 | Open |
| BI | BI Service | RDW/ADW | Query/BI | TBD | R 중심 | TBD | PPT | Open |
| ETL | ETL Node | RDW/ADW | ETL | TBD | R/W | Data | PPT | Open |

실제 계정명·권한은 근거가 있을 때만 채운다.

금지:

```text
접근표에 없는 DB 접근
타 시스템 DB 직접 DML
공유 DBA 계정 사용
```

---

# 15. 4.4 OLTP 및 대용량 배치 수행 방안

목표:

```text
Online / OLTP Peak
        ≠
Large Batch / ETL Window
```

자원 경합 통제 축:

```text
AP / Worker
DB Session
SQL Resource
ETL Process
I/O
Batch Window
```

원칙:

1. 온라인 AP와 대용량 배치 AP/ETL을 가능한 한 분리한다.
2. 동일 DB를 사용해도 Workload Resource Group/Window/동시성을 관리한다.
3. 대량 단일 Transaction을 금지하고 재시작 가능한 단위로 나눈다.
4. 배치 완료시간·영향도·Owner를 정의한다.
5. 실제 임계값은 용량시험/Runtime Evidence로 확정한다.

---

# 16. 5.1 서버 호스트 명명규칙

기존 시스템 표준 정의서의 Baseline:

```text
고정 12자리
```

구성:

```text
1~2   법인
3~6   Application Code (대구분+업무구분)
7     Platform/Server
8     Environment
9~10  Role
11~12 Sequence
```

예시:

```text
sb | mpco | l | o | wb | 01
→ sbmpcolowb01
```

센터/순번:

```text
의왕 01~49
안성 DR 51~99
```

DR은 단순히 환경 문자만으로 구분하지 않고 순번 정책과 함께 관리한다.

실제 코드표는 PPT/CMDB 사전을 따른다.

---

# 17. 5.2 파일 시스템 구성

본 장에서 고정할 것은:

```text
표준 Root / Mount
Application / Log / Data 분리
DB / AP 유형별 기준
용량 / 소유자 / 권한
예외 등록
```

정확한 경로값은 PPT/FS 표에서 추출한다.

근거가 없는 경로를 생성하지 않는다.

금지:

```text
임의 Home 하위에 운영 Data 저장
Log/Data/Artifact 혼재
예외표 없는 비표준 Mount
```

---

# 18. 5.3 사용자 계정

원칙:

```text
환경 분리
역할 분리
최소권한
개인/서비스 계정 구분
비밀정보 별도 관리
```

금지:

```text
공용 Password
개발/운영 동일 비밀
비공식 운영계정
Source에 Password 저장
```

실제 계정명은 Inventory 근거로만 기입한다.

---

# 19. 5.4 서비스 포트 현황

Port는 서버 개인 설정이 아니라 Inventory Object다.

필수 필드:

```text
System
Host
Role
Protocol
Listen Port
Source Zone
Target Zone
FW Rule
LB Rule
Owner
Evidence
Status
```

원칙:

```text
Port Inventory
 ↔ Firewall
 ↔ Load Balancer
 ↔ Application Config
```

불일치하면 개통 Gate를 통과하지 않는다.

---

# 20. HA / DR Physical Mapping

```text
운영 Host ...01
       │
       ├──── HA Pair / L4
       │
       └──── DR Mapping ───▶ ...51
```

필수:

```text
Operating Host
DR Host
Application / Artifact
Config
DNS / GSLB / L4
DB Replication
Batch/Interface Recovery
Owner
```

Physical Host만 존재하고 전환 절차가 없으면 DR 완료가 아니다.

전환 Runtime은 06에서 다룬다.

---

# 21. Security / NFR / Observability 연결

| NFR | PHYSICAL 반영 |
|---|---|
| Performance | CPU/Memory/Thread/Pool/DB/I/O Capacity |
| Availability | HA Pair, RAC, L4/GSLB, Fault Isolation |
| Scalability | Scale-out 가능한 Host Group |
| Security | Network Segmentation, Account, Port, Secret |
| Observability | Host/JVM/DB/Pool/Port Monitoring ID |

모든 물리 구성요소는 모니터링 식별자와 연결되어야 한다.

---

# 22. PDMG Physical Reference

PDMG는 다음을 검증하는 Reference로만 사용한다.

```text
WAR / JAR / Module
JVM / Spring Runtime
Tomcat / Worker / Hikari 후보
Config
Java / Spring Boot / Gradle Version
```

그러나:

```text
PDMG Current Value
≠
NSIGHT 전체 Production Standard
```

특히 Thread/Pool/Heap/Timeout 값은 **AS-IS Snapshot 또는 Capacity Candidate**로 태그한다.

---

# 23. GAP / RISK / OPEN / ADR

| 항목 | 상태 | 조치 |
|---|---|---|
| PPT 운영 SW 전체 17종 상세 전사 | `[OPEN]` | SW 목록 원본 추출 |
| HW CPU/MEM/대수 최종값 | `[OPEN/CONFLICT]` 가능 | 용량산정 Baseline 정합 |
| RDW/ADW RAC 실제 노드수 | `[OPEN]` | DB Inventory 확인 |
| CDC Lag SLA | `[OPEN]` | ADR/SLA 확정 |
| FS 실제 표준 경로 | `[OPEN]` | PPT/운영표 확인 |
| Port 전체 현황 | `[OPEN]` | CMDB/FW/LB와 대사 |
| PDMG 정확한 Host/WAR Mapping | `[GAP]` 가능 | Deployment Evidence 확보 |
| DR RTO/RPO/전환 절차 | Runtime Handoff | 06에서 검증 |

---

# 24. Verification Checklist

```text
[ ] Logical Node가 Physical Host와 추적되는가
[ ] 운영/DR/개발/선도 환경이 분리되는가
[ ] 의왕/안성 센터가 구분되는가
[ ] 01~49 / 51~99 순번 정책과 정합되는가
[ ] HW/SW 구성도와 Inventory가 일치하는가
[ ] RDW/ADW 역할이 분리되는가
[ ] RAC와 DR을 같은 개념으로 쓰지 않았는가
[ ] CDC와 ETL 책임이 분리되는가
[ ] Application↔DB 접근표가 존재하는가
[ ] Hostname/FS/Account/Port가 표준화되는가
[ ] 근거 없는 SW 버전/용량을 생성하지 않았는가
[ ] DR Host뿐 아니라 전환 Runtime으로 Handoff 되었는가
```

---

# 25. MECHANISM Handoff

PHYSICAL Output:

```text
Physical Host / Host Group
HW / SW
DB / RAC / OGG
Application ↔ DB Access
Hostname / FS / Account / Port
HA / DR Pair
Capacity Evidence
```

MECHANISM에서 확정할 것:

```text
Interface Type / Contract
Application Layer
Transaction Processing Structure
Standard Message
GUID
Charset
Framework Entry / Responsibility
SSO / Exception / Log
Batch Mechanism
Business Solution Integration
```
