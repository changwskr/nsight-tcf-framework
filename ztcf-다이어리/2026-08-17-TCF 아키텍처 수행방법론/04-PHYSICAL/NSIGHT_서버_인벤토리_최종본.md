# NSIGHT 서버 인벤토리 최종본

## 1. 문서 목적

본 문서는 지금까지 확인·정리된 농협 상호금융 NSIGHT 서버 자료를 기준으로 서버 인벤토리의 최종 관리 기준을 정의한다.

현재 서버 인벤토리는 **71대 서버를 Physical Architecture의 1차 기준 모수**로 관리하며, 단순 서버 목록이 아니라 다음 정보를 연결하는 Master Inventory를 목표로 한다.

- 시스템 / 애플리케이션
- 서버 / Hostname
- 환경 / 센터
- 서버 역할
- CPU / Memory / Disk
- tpmC / Capacity
- Middleware
- Tomcat JVM Runtime
- Network
- HA / DR
- Lifecycle
- 검증 상태 및 Architecture GAP

> 핵심 원칙: **서버 1대 = Master Inventory 1행**을 기본으로 하되, WAS 서버의 Tomcat JVM/Application 정보는 별도 Runtime 상세 인벤토리와 연결한다.

---

## 2. 서버 인벤토리 기준

### 2.1 최종 서버 기준 수량

| 구분 | 기준 |
|---|---|
| 서버 인벤토리 기준 수량 | **71대** |
| 서버 식별 기준 | Hostname |
| 관리 단위 | 서버 1대 = 1행 |
| Physical Architecture 기준 | 71대 서버 Baseline |
| Runtime 상세 단위 | WAS Server → Tomcat JVM Instance → Application |
| 용량관리 | CPU / 수정 CPU / Memory / Disk / tpmC |
| 가용성관리 | HA / DR |
| 검증관리 | 확정 / 확인필요 / GAP |

---

## 3. Master Inventory 최종 관리 항목

| 영역 | 최종 관리 항목 | 관리 기준 |
|---|---|---|
| 식별 | No, Hostname, 서버명 | 필수 |
| 분류 | 환경, 센터, 시스템그룹, 세부시스템 | 필수 |
| 플랫폼 | 제조사, OS, 서버 Type | IaaS / 단독 / Appliance 등 |
| Lifecycle | 신규/기존/삭제/삭제검토 | 필수 |
| 역할 | WEB / WAS / AP / DB / ETL / CDC 등 | 필수 |
| Compute | CPU Core, 수정 CPU Core, Memory | 서버별 관리 |
| Storage | OS Disk, 추가 Disk | 서버별 관리 |
| Capacity | tpmC, 필요 Core, 할당 Core | 용량산정 연계 |
| Network | AP IP, SLB/VIP, DB IP, SCAN, DataGuard IP | 네트워크 원장 연계 |
| Middleware | Apache, Tomcat, ETL, CDC 및 솔루션 | 설치 위치 관리 |
| Runtime | JVM Heap, GC, Thread, HikariCP | WAS 중심 |
| Application | 업무코드, Application, WAR, Tomcat JVM | 상세 매핑 |
| HA | HA Group, Peer Server | 장애대응 |
| DR | 운영 서버 ↔ DR 서버 | 재해복구 |
| 검증 | 확정/확인필요/GAP, 근거, 비고 | Architecture Gate |

---

## 4. 대표 서버 자원 및 용량 정보

현재까지 서버별로 매핑된 대표 자원값은 다음과 같다.

| 서버명 | CPU 산정 | 수정 CPU | Memory | OS Disk | 추가 Disk | tpmC |
|---|---:|---:|---:|---:|---:|---:|
| 마케팅플랫폼 WEB #01 | 11 | **12** | 48GB | 250GB | 100GB | 1,130,017 |
| 마케팅플랫폼 WEB #02 | 11 | **12** | 48GB | 250GB | 100GB | 1,130,017 |
| 마케팅플랫폼 WAS #01 | 35 | **32** | 256GB | 250GB | 110GB | 3,849,561 |
| 마케팅플랫폼 WAS #02 | 35 | **32** | 256GB | 250GB | 110GB | 3,849,561 |
| 미니싱글뷰 WEB #01 | 7 | **8** | 32GB | 250GB | 100GB | 751,802 |
| 미니싱글뷰 WEB #02 | 7 | **8** | 32GB | 250GB | 100GB | 751,802 |
| 미니싱글뷰 WAS #01 | 42 | **32** | 256GB | 250GB | 110GB | 4,603,836 |
| 미니싱글뷰 WAS #02 | 42 | **32** | 256GB | 250GB | 110GB | 4,603,836 |
| BI포털 WEB #01/#02 | 4 | **4** | 16GB | 250GB | 100GB | 362,070 |
| BI포털 WAS #01/#02 | 7 | **8** | 64GB | 250GB | 110GB | 706,045 |

### 4.1 CPU 관리 원칙

`CPU 산정 Core`와 `수정/할당 Core`는 반드시 별도 컬럼으로 관리한다.

예:

```text
마케팅플랫폼 WAS
산정 : 35 Core
  ↓
수정/할당 : 32 Core
  ↓
Capacity GAP 검증

미니싱글뷰 WAS
산정 : 42 Core
  ↓
수정/할당 : 32 Core
  ↓
Capacity GAP 검증
```

산정 Core보다 실제 할당 Core가 작은 경우 단순 정상으로 처리하지 않고 성능시험 및 장애 시 잔여 처리량을 포함하여 검증한다.

---

## 5. 서버명 / Hostname 관리체계

상호금융 UNIX/x86 서버명은 다음 12자리 체계로 관리한다.

```text
[법인 2] + [업무 4] + [서버 1] + [운영 1] + [용도 2] + [순번 2]
= 12자리
```

| 구분 | 길이 | 의미 |
|---|---:|---|
| 법인 | 2 | 상호금융 등 법인 구분 |
| 업무 | 4 | 메타시스템 등록 업무코드 |
| 서버 | 1 | OS/서버 플랫폼 |
| 운영 | 1 | 운영/검증/개발 |
| 용도 | 2 | WEB/WAS/AP/DB 등 |
| 순번 | 2 | 서버 일련번호 |

상호금융 법인코드는 `sb`를 사용한다.

Hostname은 서버 인벤토리의 핵심 식별키로 사용한다.

---

## 6. 서버 인벤토리 논리 구조

```text
NSIGHT MASTER SERVER INVENTORY
          │
          ├─ System / Application
          │    ├─ Application Group
          │    ├─ System Group
          │    ├─ 업무코드
          │    └─ Application
          │
          ├─ Server
          │    ├─ Hostname
          │    ├─ 서버명
          │    ├─ 환경
          │    ├─ 센터
          │    ├─ 역할
          │    └─ Lifecycle
          │
          ├─ Resource
          │    ├─ CPU
          │    ├─ 수정 CPU
          │    ├─ Memory
          │    ├─ OS Disk
          │    └─ 추가 Disk
          │
          ├─ Capacity
          │    ├─ tpmC
          │    ├─ 필요 Core
          │    ├─ 할당 Core
          │    ├─ TPS
          │    └─ Capacity GAP
          │
          ├─ Middleware / Runtime
          │    ├─ Apache
          │    └─ Tomcat JVM
          │         ├─ Connector Port
          │         ├─ Application
          │         ├─ JVM Heap
          │         ├─ GC
          │         ├─ maxThreads
          │         └─ HikariCP
          │
          ├─ Network / Data
          │    ├─ AP IP
          │    ├─ VIP
          │    ├─ DB IP
          │    ├─ SCAN
          │    ├─ RDW / ADW
          │    └─ DataGuard
          │
          └─ Availability
               ├─ HA Group
               ├─ Peer Node
               ├─ DR 여부
               └─ 운영 ↔ DR Mapping
```

---

## 7. WEB 아키텍처 관리 기준

NSIGHT WEB 계층은 Apache HTTP Server를 기준으로 관리한다.

하나의 Apache Instance에서 여러 서비스 포트를 Listen할 수 있으며, 포트별로 서로 다른 Tomcat JVM으로 라우팅할 수 있다.

```text
Apache Instance
     │
     ├─ Listen 9000
     │      └─ Tomcat JVM #1 :19000
     │
     ├─ Listen 9001
     │      └─ Tomcat JVM #2 :19001
     │
     └─ Listen 9010
            └─ Tomcat JVM #3 :19010
```

따라서 WEB 서버 인벤토리에는 최소 다음 항목을 추가 관리한다.

| 항목 | 내용 |
|---|---|
| Apache Instance | 인스턴스 식별 |
| Listen Port | 9000/9001 등 |
| Target WAS | 대상 WAS Hostname |
| Target JVM | Tomcat JVM Instance |
| Target Port | 19000/19001 등 |
| HA Group | WEB 이중화 그룹 |

---

## 8. WAS / Tomcat JVM 관리 기준

### 8.1 기본 정의

NSIGHT WAS의 관리구조는 다음과 같이 정의한다.

> **WAS Server/VM 내부에 1개 이상의 독립 Tomcat JVM Instance가 존재하며 각 JVM은 독립 실행·장애·자원관리 단위이다.**

```text
WAS Server / VM
│
├─ Tomcat JVM Instance #1
│    ├─ CATALINA_BASE
│    ├─ Connector Port
│    ├─ JVM Heap
│    ├─ Thread
│    ├─ HikariCP
│    └─ Application
│
└─ Tomcat JVM Instance #2
     ├─ CATALINA_BASE
     ├─ Connector Port
     ├─ JVM Heap
     ├─ Thread
     ├─ HikariCP
     └─ Application
```

기존 구성도에서 표현된 `Container`는 서버 인벤토리 및 아키텍처 문서에서는 가능한 한 **Tomcat JVM Instance**로 명확하게 표현한다.

### 8.2 Runtime 상세 인벤토리

| 항목 | 관리값 |
|---|---|
| WAS Hostname | 서버 식별 |
| JVM Instance ID | JVM #1/#2 등 |
| OS Account | 실행계정 |
| CATALINA_HOME | Tomcat Engine |
| CATALINA_BASE | Instance별 설정 |
| Connector Port | 19000/19001 등 |
| Application | 업무 애플리케이션 |
| WAR | 배포 Artifact |
| Xms/Xmx | Heap |
| GC | G1GC 등 |
| maxThreads | Tomcat Worker |
| Hikari maximumPoolSize | DB Pool |
| DB Target | RDW/ADW 등 |

---

## 9. Capacity 관리

### 9.1 tpmC와 TPS 분리

`tpmC`와 `TPS`는 동일한 지표로 관리하지 않는다.

```text
tpmC
 = 벤치마크성 서버 처리성능 지표

TPS
 = 실제 업무 초당 거래 처리량
```

업무 복잡도, DB 처리시간, 네트워크, Framework, Logging 등의 보정 없이 tpmC를 TPS와 1:1로 환산하지 않는다.

### 9.2 Capacity Chain

```text
전체 사용자
   ↓
세션
   ↓
동시 요청자
   ↓
목표 TPS
   ↓
서버 처리용량
   ↓
필요 Core
   ↓
실제 할당 Core
   ↓
Tomcat Thread
   ↓
DB Connection Pool
   ↓
DB Session
   ↓
장애 시 잔여 처리량
```

---

## 10. HA / DR 관리

서버 인벤토리는 개별 서버의 `DR=O/X`만 관리해서는 부족하다.

최종적으로 다음 관계를 명시한다.

```text
운영 WEB #01 ─┐
              ├─ HA Group
운영 WEB #02 ─┘
       │
       │ DR Mapping
       ▼
DR WEB #51 ─── 운영 #01 대응
DR WEB #52 ─── 운영 #02 대응
```

WAS도 동일하게 관리한다.

| 관리항목 | 설명 |
|---|---|
| HA Group ID | 동일 서비스 이중화 그룹 |
| HA Peer | 상대 노드 |
| Active/Standby | 운영 방식 |
| DR Target | DR 서버 Hostname |
| Failover | 장애 전환 대상 |
| Failback | 복귀 대상 |
| N-1 Capacity | 서버 1대 장애 시 잔여 처리량 |

---

## 11. Network / DB 연결정보

서버 인벤토리와 별도로 Network Relation을 관리하고 Hostname으로 연결한다.

| 항목 | 설명 |
|---|---|
| AP IP | 서버 서비스 IP |
| SLB VIP | Load Balancer VIP |
| WEB→WAS | Apache Proxy 대상 |
| WAS IP | WAS 서비스 IP |
| DB IP | DB 접속 주소 |
| DB VIP | RAC VIP |
| SCAN IP | Oracle RAC SCAN |
| DataGuard IP | DR 복제 네트워크 |
| DB Target | RDW / ADW |
| Pool | HikariCP Pool |

---

## 12. 최종 Architecture GAP

71대 서버 식별 및 주요 자원정보는 Physical Architecture의 1차 Baseline으로 사용할 수 있다.

다만 CMDB 수준의 최종 인벤토리를 위해 다음 항목을 계속 보강한다.

| 우선순위 | GAP | 조치 |
|---:|---|---|
| P0 | Application/WAR 완전 매핑 | WAS/JVM별 배포 Application 연결 |
| P0 | Tomcat JVM Instance 매핑 | Port/CATALINA_BASE/JVM/Application 연결 |
| P0 | 운영↔DR 1:1 관계 검증 | Hostname 기준 DR Mapping |
| P0 | 산정 Core ↔ 할당 Core GAP | 성능시험 및 N-1 검증 |
| P1 | IP/VIP/SCAN 완전 매핑 | Network Inventory 연결 |
| P1 | JVM/Thread/Hikari 실설정 | Runtime Evidence 확보 |
| P1 | WAS→RDW/ADW 연결 | DB Pool/Session 산정 연결 |
| P2 | File System 상세 | VG/LV/Mount/User/Group 보강 |
| P2 | Middleware Version | Apache/Tomcat/Solution 버전 등록 |

---

## 13. Architecture Gate

서버 인벤토리는 다음 조건을 충족해야 최종 Baseline으로 승인한다.

- [ ] 71대 Hostname 중복 없음
- [ ] 서버명과 Hostname 규칙 일치
- [ ] 모든 서버에 환경/센터 지정
- [ ] 모든 서버에 역할 지정
- [ ] CPU/Memory/Disk 값 검증
- [ ] 산정 Core와 실제 할당 Core 비교
- [ ] tpmC 근거 연결
- [ ] WEB→WAS 관계 연결
- [ ] WAS→Tomcat JVM 관계 연결
- [ ] JVM→Application/WAR 관계 연결
- [ ] WAS→DB 관계 연결
- [ ] HA Peer 지정
- [ ] 운영→DR Target 지정
- [ ] N-1 Capacity 검증
- [ ] 삭제/삭제검토 서버 Lifecycle 확인
- [ ] 미확정값에 근거/담당자/완료예정일 지정

---

## 14. 최종 관리 모델

```text
SYSTEM
  │
  ▼
APPLICATION
  │
  ▼
SERVER
  │
  ├─ RESOURCE
  │    CPU / MEM / DISK
  │
  ├─ CAPACITY
  │    tpmC / TPS / Core
  │
  ├─ MIDDLEWARE
  │    Apache / Tomcat
  │
  ├─ RUNTIME
  │    JVM / Thread / Hikari
  │
  ├─ NETWORK
  │    IP / VIP / SCAN
  │
  ├─ DATA
  │    RDW / ADW
  │
  └─ AVAILABILITY
       HA / DR
```

---

## 15. 최종 결론

NSIGHT 서버 인벤토리는 **71대 서버 목록을 기준으로 유지**한다.

향후 작업은 서버 대수를 다시 만드는 방식이 아니라 Hostname을 중심으로 다음 관계를 지속적으로 보강하는 방식으로 수행한다.

```text
Hostname
   ↓
Application
   ↓
Tomcat JVM
   ↓
Port
   ↓
CPU / Memory
   ↓
JVM Heap / Thread
   ↓
HikariCP
   ↓
RDW / ADW
   ↓
HA / DR
   ↓
Capacity / Runtime Evidence
```

이 구조가 완성되면 서버 인벤토리는 단순 자산목록이 아니라 **NSIGHT Physical Architecture / Capacity / Runtime / HA·DR을 통합 관리하는 CMDB Baseline**으로 사용할 수 있다.
