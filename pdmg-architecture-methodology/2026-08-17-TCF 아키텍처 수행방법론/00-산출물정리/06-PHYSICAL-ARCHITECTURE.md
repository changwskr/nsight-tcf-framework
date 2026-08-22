# NSIGHT Physical Architecture

> 프로젝트: 농협 상호금융 NSIGHT 차세대 정보계  
> 기준일: 2026-08-18  
> 상태: **Current Working Baseline / G30**

---

## 1. 목적

본 문서는 G20에서 확정한 Logical Architecture를 실제 서버, WEB/WAS, Tomcat JVM, Application, Data Platform, HA/DR 자원에 배치하는 **Physical Architecture Baseline**을 정의한다.

본 단계의 목표는 다음 관계를 물리 자원까지 연결하는 것이다.

```text
System / Domain
    ↓
Application / Service
    ↓
WEB / WAS / AP / DB Role
    ↓
Server / Hostname
    ↓
Apache / Tomcat JVM
    ↓
Application / WAR
    ↓
CPU / Memory / Disk
    ↓
Thread / DB Pool / Session
    ↓
RDW / ADW / External
    ↓
HA / DR
    ↓
Runtime Evidence
```

현재 Evidence에서 확인되지 않은 Application/WAR명, CATALINA_BASE, 실제 Apache Proxy 설정, 일부 IP/VIP, RTO/RPO는 `UNKNOWN` 또는 `OPEN`으로 유지한다.

---

## 2. Evidence 기준

G30은 다음 자료를 주 Evidence로 사용한다.

- NSIGHT 최종 통합 서버 인벤토리 · Physical Architecture · Capacity Baseline
- NSIGHT 서버 인벤토리 최종본
- 운영 시스템 구성 체계 / DR 시스템 구성 체계
- NSIGHT 미들웨어 아키텍처 정의서
- WEB/WAS 구성도 및 미들웨어 점검 결과
- 서버 최소사양 및 tpmC/Core 용량산정 자료
- 성능 파라미터 / JVM / Tomcat / Hikari 자료
- G20 Big Picture / Logical Architecture

### 2.1 Evidence 상태

| 영역 | 상태 | G30 사용방식 |
|---|---|---|
| 서버 71대 | `[CURRENT WORKING]` | Physical Server Baseline |
| Hostname / 역할 | `[CONFIRMED/WORKING]` | 서버 식별 및 역할 매핑 |
| CPU/MEM/Disk/tpmC | `[WORKING]` | Capacity 비교용 |
| WEB=Apache | `[CONFIRMED]` | WEB Runtime 기준 |
| WAS=Tomcat | `[CONFIRMED]` | WAS Runtime 기준 |
| Container=Tomcat JVM Instance | `[DECISION/WORKING]` | 구성도 표준 해석 |
| 1 Tomcat JVM=1 주요 Application | `[WORKING ARCHITECTURE RULE]` | Runtime Isolation 기준 |
| Apache 포트→Tomcat Connector 패턴 | `[CONFIRMED DESIGN]` | Routing 모델 |
| 실제 httpd.conf/server.xml/setenv.sh | `[UNKNOWN/PARTIAL]` | G30 조건부 항목 |
| Application/WAR 전수목록 | `[OPEN]` | G30 미완료 핵심 |
| 운영↔DR #01/#02↔#51/#52 | `[PARTIAL CONFIRMED]` | DR Mapping |
| RTO/RPO | `[UNKNOWN]` | G70 이관 |

---

## 3. Physical Architecture 핵심 원칙

### P-PHY-001 — 서버와 실행 프로세스를 분리한다

```text
WAS Server / VM
      ≠
Tomcat JVM Instance
      ≠
Application / WAR
```

서버는 CPU/Memory/Disk/NIC 자원 단위이고, Tomcat JVM은 Process/Heap/Thread/Connector/DB Pool의 실행 단위이다.

### P-PHY-002 — 서버 1대는 Master Inventory 1행으로 관리한다

Hostname을 서버의 핵심 식별키로 사용한다.

### P-PHY-003 — Runtime 상세는 별도 계층으로 관리한다

```text
SERVER_MASTER
   1
   │
   └──── N TOMCAT_JVM
              │
              └──── 1..N APPLICATION_DEPLOYMENT
```

### P-PHY-004 — Minimum / Allocated / Capacity Design을 분리한다

```text
Minimum Spec
   ≠
Allocated Spec
   ≠
Capacity Requirement
```

### P-PHY-005 — RDW와 ADW의 물리 자원을 분리한다

온라인/운영 조회와 분석/대용량 처리 간 자원경합을 구조적으로 차단한다.

### P-PHY-006 — 실시간 AP와 배치/ETL 자원을 분리한다

실시간 이벤트, 온라인, 분석, 배치, CDC/ETL은 기능별 장애·부하 단위를 독립 관리한다.

### P-PHY-007 — 운영은 Isolation + HA, 개발은 Consolidation 허용

개발환경은 여러 JVM을 하나의 WAS Server에 통합할 수 있지만 운영은 시스템별 대칭 JVM 배치를 기본으로 한다.

---

## 4. 전체 Physical Big Picture

```text
                         [사용자 / WebTopSuite / Client]
                                      │
                                      ▼
                                    GSLB
                                      │
                                      ▼
                                     L4
                        ┌─────────────┴─────────────┐
                        ▼                           ▼
                  WEB Server #01              WEB Server #02
                  Apache Instance             Apache Instance
                  9000/9001/...               9000/9001/...
                        │  ╲                       ╱  │
                        │    ╲                   ╱    │
                        ▼      ▼               ▼      ▼
                  WAS Server #01              WAS Server #02
                ┌───────────────┐           ┌───────────────┐
                │ Tomcat JVM #1 │           │ Tomcat JVM #1 │
                │ App A         │           │ App A         │
                │ :19000        │           │ :19000        │
                ├───────────────┤           ├───────────────┤
                │ Tomcat JVM #2 │           │ Tomcat JVM #2 │
                │ App B         │           │ App B         │
                │ :19001        │           │ :19001        │
                └───────┬───────┘           └───────┬───────┘
                        │                           │
                        └─────────────┬─────────────┘
                                      ▼
                                  Spring / TCF
                                      │
                               HikariCP / MyBatis
                                      │
                      ┌───────────────┴───────────────┐
                      ▼                               ▼
                    RDW                             ADW
             Online / Operational             Analytics / Large
                      │                               │
                      ├─ CDC / Kafka / ETL / Batch ──┤
                      │                               │
                      ▼                               ▼
                Real-time AP                    BI / Analytics
```

### 구성요소 책임

- GSLB/L4: 서비스 진입 및 노드 선택
- Apache: WEB 진입, Listen/VirtualHost/Proxy, Health/Routing
- Tomcat JVM: Application 실행·장애·자원관리 단위
- Application/WAR: 업무 기능과 TCF 실행
- Hikari/MyBatis: DB Connection/SQL 실행 경계
- RDW: 운영/실시간 조회 중심
- ADW: 분석/대용량 처리 중심

### 장애 영향

- WEB 장애: L4에서 다른 WEB으로 우회
- Tomcat JVM 장애: 동일 Application의 Peer JVM으로 우회
- WAS VM 장애: 해당 VM 내 모든 JVM 손실
- DB 장애: DB HA/DataGuard/RAC 정책과 연동
- 센터 장애: DR 자원으로 전환

---

## 5. Physical System Group

운영계는 다음 관리축을 가진다.

| 코드 | Physical System Group | 주요 자원 |
|---|---|---|
| MP | Marketing Platform | 마케팅 WEB/WAS, Mini SingleView WEB/WAS, 실시간/행동/고객행동 AP |
| RD | Real-time Data Warehouse | RDW Appliance |
| AD | Analytical Data Warehouse | ADW Appliance |
| DG | Data Governance | 품질/비정형/데이터흐름 WAS |
| BL | Business Analysis Layer | BI Portal, Self-BI, 신용실적, OLAP |
| IM | Information Management | Framework, 단말, Batch, CDC, ETL, Report, IMDG |

Logical Architecture의 10개 Zone과 이 시스템 그룹은 서로 다른 축이다.

```text
Logical Responsibility Zone
       ↕ mapping
Physical System Group
       ↕ mapping
Server / JVM / Application
```

---

## 6. 서버 Baseline

현재 Physical Server Baseline은 **71대**이다.

### 6.1 시스템 그룹별

| 시스템 그룹 | 서버 수 |
|---|---:|
| 마케팅플랫폼 | 15 |
| 신BI포털시스템 | 16 |
| 데이터거버넌스 | 4 |
| IT 서비스 및 업무지원 | 28 |
| 데이터플랫폼 시스템 | 8 |
| **합계** | **71** |

### 6.2 역할별

| 역할 | 서버 수 |
|---|---:|
| WEB | 20 |
| WAS | 28 |
| AP | 13 |
| DB | 10 |
| **합계** | **71** |

### 6.3 Lifecycle 주의대상

- OLAP WEB/WAS 2대 + OLAP AP 2대: 삭제 대상
- 데이터흐름 WAS #02: 삭제검토
- RDW/ADW: Appliance 특성상 노드별/서버군 공통 사양 구분 필요

---

## 7. 대표 Physical Mapping

### 7.1 마케팅플랫폼

```text
MP / MPCO
│
├─ WEB #01  sbmpcolowb01  Apache
├─ WEB #02  sbmpcolowb02  Apache
│
├─ WAS #01  sbmpcolows01
│   ├─ Tomcat JVM #1 :19000 → 공통 Application [WORKING]
│   └─ Tomcat JVM #2 :19001 → 화면 Application [WORKING]
│
└─ WAS #02  sbmpcolows02
    ├─ Tomcat JVM #1 :19000 → 공통 Application [WORKING]
    └─ Tomcat JVM #2 :19001 → 화면 Application [WORKING]
```

대표 자원:

| 서버 | 수정CPU | MEM | OS Disk | 추가 Disk | tpmC |
|---|---:|---:|---:|---:|---:|
| 마케팅 WEB #01/#02 | 12C | 48GB | 250GB | 100GB | 1,130,017/대 |
| 마케팅 WAS #01/#02 | 32C | 256GB | 250GB | 110GB | 3,849,561/대 |

### 7.2 Mini SingleView

| 서버 | 수정CPU | MEM | OS Disk | 추가 Disk | tpmC |
|---|---:|---:|---:|---:|---:|
| Mini SingleView WEB #01/#02 | 8C | 32GB | 250GB | 100GB | 751,802/대 |
| Mini SingleView WAS #01/#02 | 32C | 256GB | 250GB | 110GB | 4,603,836/대 |

WAS당 2 JVM, 포트 `19000/19001` 패턴으로 관리한다.

### 7.3 BI Portal

| 서버 | 수정CPU | MEM | tpmC |
|---|---:|---:|---:|
| BI Portal WEB #01/#02 | 4C | 16GB | 362,070/대 |
| BI Portal WAS #01/#02 | 8C | 64GB | 706,045/대 |

BI Portal Capacity 자료에는 전체 요구량 WEB 7C, WAS 13C가 존재하며 현재 배치 8C/16C는 정상상태 요구량을 충족한다. 다만 N-1 상태의 100% 피크 요구량 충족 여부는 G60/G70에서 별도 검증한다.

---

## 8. WEB / Apache Physical Pattern

```text
WEB Server / VM
└─ Apache Instance
   ├─ Listen 9000 → WAS/JVM :19000
   ├─ Listen 9001 → WAS/JVM :19001
   ├─ Listen 9010 → WAS/JVM :19010
   └─ Listen 9011 → WAS/JVM :19011
```

하나의 Apache Instance가 여러 포트를 Listen하는 구조는 허용한다.

그러나 다음은 실제 Config Evidence로 확인되어야 한다.

- Apache Instance 수
- Listen/VirtualHost 실제 값
- ProxyPass/Worker 실제 Target
- Health Check
- KeepAlive/Connection/Read Timeout
- SSL/TLS 종단
- L4 Pool과 WEB Node 매핑

---

## 9. WAS / Tomcat JVM Physical Pattern

```text
WAS Server
│
├─ Tomcat JVM #1
│   ├─ CATALINA_BASE #1          [UNKNOWN until config evidence]
│   ├─ Connector :19000          [DESIGN CONFIRMED]
│   ├─ Application A             [WORKING]
│   ├─ Heap / GC
│   ├─ maxThreads
│   └─ Hikari Pool
│
└─ Tomcat JVM #2
    ├─ CATALINA_BASE #2          [UNKNOWN until config evidence]
    ├─ Connector :19001          [DESIGN CONFIRMED]
    ├─ Application B             [WORKING]
    ├─ Heap / GC
    ├─ maxThreads
    └─ Hikari Pool
```

### 9.1 운영 대표 패턴

| 시스템 | WEB | WAS | WAS당 JVM | Port Pattern | 판정 |
|---|---:|---:|---:|---|---|
| 마케팅플랫폼 | 2 | 2 | 2 | 9000/9001→19000/19001 | Working Confirmed |
| Mini SingleView | 2 | 2 | 2 | 9000/9001→19000/19001 | Working Confirmed |
| BI Portal | 2 | 2 | 1 | 9000→19000 계열 | Working Confirmed |
| 신용실적 | 2 | 2 | 2 | 9000/9001→19000/19001 | Working Confirmed |
| 단말관리 | 복수 | 복수 | 1 | 9000→19000 | Partial Confirmed |
| 보고서디자이너 | 2 | 2 | 1 | 9000 계열 | Exception Pattern |

### 9.2 개발 대표 패턴

```text
마케팅 개발 WAS #1
├─ JVM01 : Marketing Common     :19000
├─ JVM02 : Marketing UI         :19001
├─ JVM03 : MiniSV Common        :19010
└─ JVM04 : MiniSV UI            :19011
```

개발은 Consolidation을 허용하지만 운영 Baseline으로 자동 승격하지 않는다.

---

## 10. HA Physical Architecture

### 10.1 WEB/WAS Cross Routing

```text
                     L4
              ┌──────┴──────┐
              ▼             ▼
          Apache01       Apache02
            │ ╲           ╱ │
            │  ╲         ╱  │
            ▼   ▼       ▼   ▼
       WAS01/JVM-A   WAS02/JVM-A
       WAS01/JVM-B   WAS02/JVM-B
```

WEB와 WAS는 고정 1:1 종속으로 관리하지 않는다.

### 10.2 HA Pool 단위

```text
Application A HA Pool
├─ WAS01 / JVM-A
└─ WAS02 / JVM-A

Application B HA Pool
├─ WAS01 / JVM-B
└─ WAS02 / JVM-B
```

WAS VM 장애 시 같은 VM의 복수 Application JVM이 동시에 손실될 수 있으므로 VM 자원배치와 N-1 Capacity를 함께 검증한다.

---

## 11. DR Physical Architecture

현재 확인된 대표 매핑:

```text
운영 #01/#02          DR #51/#52
──────────────────────────────────
MP WEB 01/02      →   MP WEB 51/52
MP WAS 01/02      →   MP WAS 51/52
MiniSV WEB 01/02  →   MiniSV WEB 51/52
MiniSV WAS 01/02  →   MiniSV WAS 51/52
RDW DB 01/02      →   RDW DB 51/52
```

대표 Hostname:

| 운영 | DR |
|---|---|
| `sbmpcolowb01` | `sbmpcolowb51` |
| `sbmpcolowb02` | `sbmpcolowb52` |
| `sbmpcolows01` | `sbmpcolows51` |
| `sbmpcolows02` | `sbmpcolows52` |
| `sbrdcoxodb01` | `sbrdcoxodb51` |
| `sbrdcoxodb02` | `sbrdcoxodb52` |

`DR 존재`와 `DR 용량 충족/RTO/RPO 충족`은 다른 문제이며 후자는 G70에서 검증한다.

---

## 12. Physical Resource / Runtime Consistency Chain

G30에서 Physical Architecture의 관리 모델을 다음으로 고정한다.

```text
Server CPU / Memory
      ↓
Tomcat JVM Count
      ↓
Heap + Native + Thread Stack
      ↓
Tomcat maxThreads
      ↓
Application Transaction Concurrency
      ↓
Hikari maximumPoolSize
      ↓
DB Session
      ↓
RDW/ADW Capacity
```

현재 숫자값은 여러 Version이 존재하므로 G30에서는 구조만 확정하고 숫자 최종 승격은 G60에서 수행한다.

---

## 13. Physical Inventory Data Model

```text
SERVER_MASTER
├─ hostname [PK]
├─ system_group
├─ application_code
├─ environment
├─ center
├─ role
├─ cpu_allocated
├─ memory_gb
├─ disk
├─ tpmc
├─ lifecycle
├─ ha_group
└─ dr_hostname

APACHE_INSTANCE
├─ server_hostname [FK]
├─ instance_id
├─ listen_port
├─ vhost
├─ target_was
└─ target_connector

TOMCAT_JVM
├─ was_hostname [FK]
├─ jvm_id
├─ os_account
├─ catalina_base
├─ connector_port
├─ xms/xmx
├─ gc
├─ max_threads
└─ hikari_pool

APPLICATION_DEPLOYMENT
├─ was_hostname
├─ jvm_id
├─ application_id
├─ war_name
├─ context_path
└─ serviceid_set
```

이 모델은 G80 Architecture Model/Runtime Evidence의 기반이 된다.

---

## 14. 정상 패턴 / 금지 패턴

### 정상 패턴

- Hostname 기준 서버 식별
- WEB/Apache와 WAS/Tomcat 분리
- JVM별 Connector/Heap/Thread/Hikari 독립 관리
- Application별 HA Peer JVM 구성
- RDW/ADW 물리 분리
- Capacity 요구량과 실제 할당량 별도 관리
- 운영↔DR Pair 명시

### 금지 패턴

- `WAS Server = Tomcat JVM = WAR`로 표현
- 개발환경 Consolidation 구조를 운영 표준으로 자동 승격
- 최소사양을 운영 권장사양으로 사용
- tpmC와 TPS를 1:1로 동일시
- WEB01→WAS01만 가능한 고정 1:1 HA 구조
- DR 서버가 있다는 이유만으로 DR Gate PASS
- 실제 Config 없이 CATALINA_BASE/Proxy Target을 추정

---

## 15. G30 GAP / OPEN

| ID | 상태 | 항목 | 영향 | 후속 Gate |
|---|---|---|---|---|
| PHY-GAP-001 | GAP | 71대 전체 Application/WAR→JVM→Hostname 전수 매핑 미완료 | ServiceId→Runtime 추적 단절 | G30/G40 |
| PHY-GAP-002 | OPEN | 실제 `httpd.conf` Routing 전수증적 부족 | WEB→WAS 실제 경로 미확정 | G30/G70 |
| PHY-GAP-003 | OPEN | 실제 `server.xml/setenv.sh` 기반 CATALINA_BASE/JVM 전수증적 부족 | JVM 독립성 실증 부족 | G30/G60 |
| PHY-GAP-004 | OPEN | 일부 IP/VIP/SCAN/DataGuard 상세 미완료 | Network/DB Failover 추적 부족 | G70 |
| PHY-GAP-005 | OPEN | DR RTO/RPO 및 Residual Capacity 미확정 | 재해복구 성능 불명확 | G70 |
| PHY-GAP-006 | REVIEW | 삭제/삭제검토 서버가 Working Baseline에 포함 | 총자원 집계 왜곡 가능 | G30 |
| PHY-GAP-007 | REVIEW | Appliance 노드별/전체 사양 구분 필요 | DB 자원집계 위험 | G50/G60 |
| PHY-GAP-008 | OPEN | HA Group 및 Application Peer JVM 전수 Catalog 미완료 | N-1 분석 불완전 | G60/G70 |

---

## 16. Architecture Rules — Physical

| Rule ID | 규칙 | Severity |
|---|---|---|
| R-PHY-001 | 서버 식별키는 Hostname으로 관리 | P0 |
| R-PHY-002 | WAS Server와 Tomcat JVM을 분리 모델링 | P0 |
| R-PHY-003 | Tomcat JVM의 Connector Port는 Instance별 Unique | P0 |
| R-PHY-004 | Application HA는 Peer JVM 단위로 정의 | P0 |
| R-PHY-005 | Apache는 복수 WAS Peer로 Routing 가능해야 함 | P0 |
| R-PHY-006 | Minimum/Allocated/Capacity Spec을 분리 | P0 |
| R-PHY-007 | RDW/ADW는 자원경합 방지를 위해 분리 | P0 |
| R-PHY-008 | 삭제/폐기자원은 Current Runtime Capacity에서 제외 | P1 |
| R-PHY-009 | DR Pair에는 Runtime Role/RTO/RPO Evidence를 연결 | P0 |
| R-PHY-010 | JVM/App/Server Mapping은 Runtime Evidence와 연결 | P0 |

---

## 17. G30 Gate 판정

### 판정: **CONDITIONAL PASS**

Physical Architecture의 구조, 서버 기준 모수, WEB/WAS/Tomcat JVM 모델, 대표 운영/DR 패턴은 기준화할 수 있는 수준이다.

다만 다음 P0 조건이 남아 있으므로 `PASS`로 승격하지 않는다.

| Condition ID | 조건 | 우선순위 | 후속 |
|---|---|---:|---|
| G30-C01 | 71대 서버 ↔ Application/WAR ↔ JVM 전수 Mapping | P0 | G40 |
| G30-C02 | Apache Routing Config Evidence 확보 | P0 | G70 |
| G30-C03 | Tomcat Instance Config Evidence 확보 | P0 | G60 |
| G30-C04 | Application HA Peer JVM Catalog | P0 | G60/G70 |
| G30-C05 | 운영↔DR 전수 Mapping + RTO/RPO | P0 | G70 |
| G30-C06 | 삭제/삭제검토/Appliance 자원 정규화 | P1 | G60 |

다음 단계는 **G40 Mechanism / Source Conformance**이다.

```text
G00  CONDITIONAL PASS
  ↓
G10  PASS
  ↓
G20  CONDITIONAL PASS
  ↓
G30  CONDITIONAL PASS
  ↓
G40  NEXT
```
