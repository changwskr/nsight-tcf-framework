# 데이터베이스 아키텍처 — DB 이중화 구성도 분석

> 원본 범위: `4. 데이터베이스 아키텍처 > 4.2 DB 이중화 구성도`  
> 원본 목적: 정보계 차세대 시스템 운영환경의 고가용성을 위한 이중화 구성  
> 핵심 구성: `RDW RAC Active-Active 2노드`, `ADW RAC Active-Active 6노드`, `Exadata RDMA Network Fabric(RoCE)`

---

## 1. 핵심 결론

RDW와 ADW는 Oracle Exadata 위에서 RAC Active-Active로 구성되어 여러 Database Server가 공유 Storage Server에 동시에 접근하는 Scale-out 고가용성 구조다.

- RDW는 Database Server 2노드와 Storage Server 3대로 표시된다.
- ADW는 Database Server 6노드와 다수 Storage Server로 구성된 대규모 Scale-out 구조다.
- Database Server와 Storage Server는 `RDMA Network Fabric(RoCE)`으로 연결된다.
- Database Server는 SQL 실행, Oracle RAC, Database Service를 제공한다.
- Storage Server는 데이터 저장과 Smart Scan, Flash Cache, Flash Log 등 Exadata 고유 기능을 제공한다.
- RAC는 Database Server 노드 장애와 서비스 재배치에 대응하지만 센터 재해, 논리 손상, 사용자 오삭제까지 보호하는 DR·백업 수단은 아니다.

```text
Application / BI / ETL
          ↓ Oracle DB Service
┌──────────── Database Server RAC ────────────┐
│ RDW: 2 Nodes / ADW: 6 Nodes / Active-Active│
└────────────────────┬────────────────────────┘
                     ↓
        RDMA Network Fabric (RoCE)
                     ↓
┌────────────── Exadata Storage Server ───────┐
│ ASM Storage · Smart Scan · Flash Cache/Log │
└─────────────────────────────────────────────┘
```

---

## 2. 근거 수준

| 수준 | 내용 |
|---|---|
| 확인 사실 | RDW RAC는 Active-Active Database Server 2노드로 표시됨 |
| 확인 사실 | ADW RAC는 Active-Active Database Server 6노드로 표시됨 |
| 확인 사실 | RDW 하단에 Storage Server 3대가 표시됨 |
| 확인 사실 | Database Server와 Storage Server 사이에 RDMA Network Fabric(RoCE)가 표시됨 |
| 확인 사실 | Database Server와 Storage Server의 주요 소프트웨어·프로세스가 별도 계층으로 표시됨 |
| 설계 해석 | ADW가 RDW보다 많은 DB 노드를 사용해 분석 병렬성과 동시사용자 처리량을 확장 |
| 설계 해석 | Active-Active는 여러 Instance가 동시에 열려 서비스를 처리한다는 의미 |
| 미확정 | ADW Storage Server의 정확한 물리 수량과 Rack 세대 |
| 미확정 | RAC One Database인지 다중 Database인지, Instance·Service 배치 정책 |
| 미확정 | ASM redundancy, Storage Grid Disk·Cell Disk 구성 |
| 미확정 | DR Data Guard, 백업, 복제 모드와 장애조치 RTO/RPO |

---

## 3. RDW 이중화 구성

```text
                 RDW RAC (Active-Active)
        ┌────────────────┬────────────────┐
        │ DB Server #1   │ DB Server #2   │
        │ RAC Instance 1 │ RAC Instance 2 │
        └────────┬───────┴───────┬────────┘
                 │ Cache Fusion  │
        ┌────────┴───────────────┴────────┐
        │ RDMA Network Fabric (RoCE)      │
        └───────┬──────────┬──────────┬───┘
                │          │          │
         ┌──────▼───┐ ┌────▼─────┐ ┌──▼───────┐
         │ Storage  │ │ Storage  │ │ Storage  │
         │ Server 1 │ │ Server 2 │ │ Server 3 │
         └──────────┘ └──────────┘ └──────────┘
```

### 3.1 정상 동작

- 두 RAC Instance가 동시에 기동되어 RDW Service를 처리한다.
- 서비스는 업무·실시간·마케팅 워크로드별로 Preferred Instance를 달리할 수 있다.
- 양쪽 DB Server는 동일 ASM 공유 Storage에 접근한다.
- Global Cache Service와 Cache Fusion이 instance 간 block 상태를 조정한다.

### 3.2 노드 장애

```text
DB Server #1 장애
        ↓
Clusterware 장애 감지
        ↓
#1 Instance 중지·격리
        ↓
DB Service를 #2 Instance로 재배치
        ↓
Application connection 재연결
```

DB Server #2가 남은 부하 전체를 처리할 수 있도록 N-1 용량을 검증해야 한다. Active-Active라는 이유만으로 장애 후 성능이 자동 보장되지는 않는다.

### 3.3 RDW 운영 관점

- CDC·실시간 SoR 적재와 마케팅 조회가 동시에 수행되므로 서비스·Resource Manager로 부하를 분리해야 한다.
- Instance affinity를 과도하게 고정하면 한 노드 장애 시 failover 부하가 집중될 수 있다.
- connection pool에는 SCAN 주소, Fast Connection Failover, FAN/ONS 또는 적절한 재시도 정책이 필요하다.

---

## 4. ADW 이중화 구성

```text
                       ADW RAC (Active-Active)
┌──────────┬──────────┬──────────┬──────────┬──────────┬──────────┐
│DB Node #1│DB Node #2│DB Node #3│DB Node #4│DB Node #5│DB Node #6│
│Instance 1│Instance 2│Instance 3│Instance 4│Instance 5│Instance 6│
└────┬─────┴────┬─────┴────┬─────┴────┬─────┴────┬─────┴────┬─────┘
     │          │          │          │          │          │
┌────┴──────────┴──────────┴──────────┴──────────┴──────────┴────┐
│                RDMA Network Fabric (RoCE)                       │
└────┬──────┬──────┬──────┬──────┬──────┬──────┬──────┬─────────┘
     │      │      │      │      │      │      │      │
     ▼      ▼      ▼      ▼      ▼      ▼      ▼      ▼
       Exadata Storage Server Grid — 장표상 다수 노드
```

### 4.1 설계 목적

- BI포탈, OLAP, Self-BI, 분석마트 등 대량 조회·집계 부하를 여러 Instance에 분산한다.
- 업무별 DB Service를 여러 Preferred/Available Instance에 배치할 수 있다.
- 6노드는 고가용성뿐 아니라 분석 워크로드의 Scale-out 처리량을 확보한다.

### 4.2 서비스 배치 예시

```text
BI 정형보고 Service  → Instance 1, 2
OLAP Service         → Instance 3, 4
Self-BI Service      → Instance 5, 6
ETL 적재 Service     → 별도 Preferred Instance 집합
```

위 배치는 장표에 직접 명시된 것이 아니라 워크로드 격리 원칙을 설명하기 위한 예시다. 실제 서비스 정책은 `srvctl config service`와 운영 설계서로 확인해야 한다.

### 4.3 ADW 장애·성능 고려

- 1개 노드 장애 시 남은 5개 노드가 부하를 수용할 수 있는지 검증해야 한다.
- 대형 ETL과 Self-BI query가 동시에 수행되면 CPU·I/O·interconnect 경합이 발생할 수 있다.
- Parallel Query의 degree와 instance group을 통제하지 않으면 6노드 전체를 과점유할 수 있다.
- 서비스별 Consumer Group과 query timeout, temp tablespace quota를 설정해야 한다.

---

## 5. Database Server 계층과 주요 프로세스

장표의 Database Server 내부 구조는 다음과 같다.

```text
Database Server
  ├─ Database Instance
  │    └─ DBRM
  ├─ System Software
  │    ├─ Exascale Client Services
  │    ├─ DBMCLI
  │    └─ MS
  └─ Grid Infrastructure
       └─ ASM Instance
```

| 구성요소 | 역할 |
|---|---|
| Database Instance | 사용자 SQL 실행, buffer cache·shared pool·background process 제공 |
| DBRM | Database Resource Manager, workload별 CPU·병렬도·세션 자원 통제 |
| Exascale Client Services | Exadata storage 접근을 지원하는 client 계층으로 표시됨 |
| DBMCLI | Database Machine Command-Line Interface, Exadata 관리 CLI |
| MS | Management Server, 관리·상태 정보 제공 |
| Grid Infrastructure | Clusterware와 ASM 기반 클러스터·스토리지 관리 |
| ASM Instance | 공유 Disk Group과 파일 배치·rebalance 관리 |

Database Server는 사용자의 SQL을 실행하고 Oracle RAC 및 Database Service를 제공하는 컴퓨팅 계층이다.

---

## 6. RDMA Network Fabric(RoCE)

```text
Database Server
    │ SQL offload / block request
    ▼
RDMA Network Fabric (RoCE)
    │ 고대역폭·저지연 전송
    ▼
Storage Server
```

- `RDMA`: Remote Direct Memory Access로 CPU 개입을 줄여 메모리 간 고속 전송을 지원한다.
- `RoCE`: RDMA over Converged Ethernet으로 Ethernet 기반 RDMA 전송을 제공한다.
- RAC interconnect와 DB–Storage 통신에 낮은 지연과 높은 대역폭을 제공한다.
- 네트워크 이중화, switch 장애, packet loss, PFC/ECN 및 MTU 설정이 성능에 직접 영향을 줄 수 있다.

### 확인 항목

- Fabric switch 이중화와 링크 bonding
- RoCE congestion control과 QoS
- MTU·VLAN·routing 일관성
- RDMA error, retransmit, link 상태 모니터링
- 케이블·포트 장애 시 경로 전환 시험

---

## 7. Storage Server 계층과 주요 프로세스

```text
Storage Server
  ├─ Exadata System Software
  ├─ Exadata Storage Services
  │    ├─ CellCLI
  │    ├─ MS
  │    ├─ CELLSRV
  │    └─ RS
  ├─ Memory
  │    └─ XRMEM Cache
  ├─ Performance-Optimized Flash
  │    ├─ Flash Cache
  │    └─ Flash Log
  └─ Physical Disk / Grid Disk / Cell Disk
```

| 구성요소 | 역할 |
|---|---|
| CELLSRV | DB Server의 I/O 요청 처리, Smart Scan·offload 수행 |
| MS | Storage Cell 관리·모니터링 서비스 |
| RS | Restart Server, Cell 서비스 감시·재시작 |
| CellCLI | Storage Cell 관리 명령 인터페이스 |
| XRMEM Cache | 메모리 기반 저지연 데이터 cache 계층 |
| Flash Cache | 자주 읽는 data block을 flash에 cache |
| Flash Log | redo write 지연을 줄이는 flash 기반 log 최적화 |
| Smart Scan | predicate·column filtering 일부를 Storage로 offload |

장표의 설명처럼 Storage Server는 데이터를 저장할 뿐 아니라 Smart Scan, Flash Cache 등의 기능으로 데이터 처리를 가속한다.

---

## 8. 장애 유형별 동작

| 장애 | 예상 동작 | 서비스 영향 | 확인사항 |
|---|---|---|---|
| RAC Instance 장애 | Service를 다른 Instance로 재배치 | 재연결 시간 동안 일부 오류 | FAN/ONS, retry, TAF/Application Continuity |
| DB Server 장애 | 해당 노드 격리, 잔여 노드 처리 | 용량 감소 | N-1 성능 시험 |
| Storage Server 장애 | ASM redundancy·Cell Grid가 우회 | 성능 저하 가능 | redundancy와 rebalance |
| RDMA Link 장애 | 대체 link/fabric 사용 | 순간 지연·대역폭 감소 | switch·link 이중화 |
| ASM Disk 장애 | mirror copy 사용·rebalance | 복구 중 I/O 증가 | failure group 설계 |
| 전체 Rack 장애 | RAC만으로 복구 불가 | RDW/ADW 중단 | Data Guard·DR 전환 |
| 논리 데이터 손상 | RAC 전체에 즉시 반영 | 전체 서비스 오류 | Flashback·RMAN·PITR |

---

## 9. Active-Active 의미와 오해 방지

```text
Active-Active
  = 여러 RAC Instance가 동시에 Open
  = 여러 Service가 동시에 업무 처리 가능
  ≠ 모든 Instance가 항상 동일한 부하 처리
  ≠ 데이터가 노드별로 별도 복제됨
  ≠ 센터 재해복구 완료
```

RAC의 모든 Instance는 동일한 공유 Database 파일에 접근한다. 따라서 노드 간 데이터 복제형 Active-Active가 아니라 공유 Disk 기반 multi-instance 구조다.

서비스는 다음 방식으로 운용할 수 있다.

- Uniform: 모든 Instance에서 서비스 제공
- Singleton: 한 Instance에서 주로 제공하고 장애 시 이동
- Preferred/Available Instance: 주·대기 Instance 집합 지정

실제 정책은 서비스 성격, connection pool, transaction 특성에 따라 선택해야 한다.

---

## 10. RAC와 DR·백업의 관계

```text
RAC
  └─ 동일 센터·공유 Storage에서 노드 고가용성

Data Guard
  └─ 별도 DB 복제본과 센터 재해복구

RMAN / Flashback
  └─ 물리·논리 손상과 시점 복구
```

| 보호 영역 | RAC | Data Guard | RMAN/Flashback |
|---|---:|---:|---:|
| DB Server 장애 | O | O | X |
| Instance 장애 | O | O | X |
| Storage/Rack 전체 장애 | X | O | 복원 필요 |
| 센터 장애 | X | O | 복원 필요 |
| 사용자 오삭제 | X | X 또는 지연적용 | O |
| 잘못된 배치·DDL | X | 복제될 수 있음 | O |

---

## 11. 운영·모니터링 지표

### RAC

- Instance·Service 상태
- Global Cache wait, `gc cr request`, `gc current request`
- interconnect latency와 block transfer
- service relocation·failover 이력
- node eviction, CSS misscount 관련 이벤트

### Database

- CPU, DB Time, AAS, wait class
- session·connection pool·transaction rate
- redo generation, log file sync
- temp·undo·tablespace 사용률
- long SQL, parallel query, plan regression

### Exadata Storage

- Smart Scan offload efficiency
- flash cache hit ratio와 flash log 상태
- Cell I/O latency·throughput
- Storage Server·physical disk 상태
- ASM rebalance와 disk group 사용률
- RDMA fabric link·error·congestion

---

## 12. 주요 위험과 대응

| 위험 | 영향 | 대응 방향 |
|---|---|---|
| Active-Active 과신 | 장애 후 잔여 노드 용량 부족 | RDW N-1, ADW N-1 성능 시험 |
| Service 설계 부재 | 특정 Instance 부하 집중 | 업무별 Preferred/Available 정책 |
| Self-BI·ETL 경합 | ADW 정형 보고 지연 | DBRM, Service, Consumer Group 격리 |
| RDMA 설정 오류 | RAC·Storage latency 증가 | Fabric baseline과 이중화 시험 |
| Storage failure group 오류 | 다중 장애 시 데이터 위험 | ASM redundancy·배치 검증 |
| RAC를 DR로 오인 | Rack·센터 장애 대응 불가 | Data Guard·백업·DR Runbook |
| application 재연결 미흡 | DB는 살아도 업무 오류 지속 | FAN/ONS, connection validation, retry |
| patch 조합 불일치 | 노드 장애·지원성 문제 | GI·DB·Exadata image 인증 조합 관리 |

---

## 13. 검증 체크리스트

- [ ] RDW 2노드와 ADW 6노드의 RAC 구성 및 Instance 이름을 확인했는가?
- [ ] 업무별 DB Service와 Preferred/Available Instance가 정의되어 있는가?
- [ ] RDW 1노드, ADW 1노드 장애 상태에서 성능을 시험했는가?
- [ ] SCAN·VIP·FAN/ONS와 application connection pool을 연계했는가?
- [ ] RDMA Fabric의 switch·link·port 이중화를 시험했는가?
- [ ] ASM Disk Group과 failure group의 redundancy가 검증되었는가?
- [ ] Storage Server 장애·disk 장애 시 rebalance와 성능 영향을 측정했는가?
- [ ] DBRM으로 ETL·BI·Self-BI 워크로드를 격리하는가?
- [ ] Data Guard·RMAN·Flashback으로 RAC 외 장애를 보호하는가?
- [ ] GI·DB RU·Exadata image patch의 인증 조합과 rolling 절차가 있는가?

---

## 14. 최종 평가

RDW 2노드와 ADW 6노드 RAC는 각각 실시간 업무와 대규모 분석의 특성에 맞춰 가용성과 처리량을 확보한 Exadata Scale-out 구조다. RDMA RoCE 패브릭과 Smart Scan·Flash Cache·Flash Log를 통해 DB Server와 Storage Server의 병렬 처리 성능을 높인다.

다만 성공적인 이중화는 노드 수만으로 완성되지 않는다. **DB Service 배치, N-1 용량, application 재연결, RDMA 이중화, ASM redundancy, DBRM 워크로드 격리, Data Guard·백업**을 함께 시험하고 운영해야 실제 고가용성이 확보된다.
