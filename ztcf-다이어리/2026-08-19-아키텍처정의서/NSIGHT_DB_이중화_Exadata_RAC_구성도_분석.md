# NSIGHT DB 이중화 구성도 분석

## 0. 문서 개요

본 문서는 제공된 **「DB 이중화 구성도」 장표(4.2 DB 이중화 구성도)**를 기준으로
NSIGHT 정보계의 **RDW / ADW Exadata RAC 고가용성 구조**, Database Server와 Storage Server의 역할,
RDMA Network Fabric(RoCE) 기반 통신구조, Exadata 계층 및 주요 프로세스를 분석하여 정리한 문서이다.

> 원본 장표 핵심 문구  
> **“정보계 차세대 시스템 운영 환경의 고가용성을 위한 이중화 구성”**  
> **“RDW, ADW 각 RAC(Active-Active) 2노드, 6노드 구성함”**

### 작성 원칙

- 원본 장표에서 직접 확인되는 내용은 **[FACT]**로 기록한다.
- 장표에서 읽히는 기술적 의미를 정리한 내용은 **[ANALYSIS]**로 구분한다.
- 장표에서 확인되지 않는 RAC Service, SCAN/VIP, Data Guard, DR 등은 임의 확정하지 않고 **확인 필요**로 관리한다.

---

# 1. 핵심 요약

| 구분 | RDW | ADW |
|---|---|---|
| DB 플랫폼 | Exadata | Exadata |
| DB 구성 | Oracle RAC | Oracle RAC |
| RAC 동작 | Active-Active | Active-Active |
| Database Server 노드 | **2노드** | **6노드** |
| DB ↔ Storage 통신 | RDMA Network Fabric (RoCE) | RDMA Network Fabric (RoCE) |
| Storage | 다중 Storage Server | 다중 Storage Server |
| 목적 | 실시간/준실시간 데이터 및 마케팅계 처리 기반 | 분석/집계/BI 대용량 처리 기반 |
| 고가용성 | RAC 노드 이중화/다중화 | RAC 다중노드 구성 |

---

# 2. DB 이중화 전체 구조

```text
                     NSIGHT DB HIGH AVAILABILITY

┌─────────────────────────────────────────────────────────────┐
│                            RDW                              │
│                                                             │
│                RAC (Active - Active)                        │
│                                                             │
│       ┌────────────────┐   ┌────────────────┐               │
│       │ Database       │   │ Database       │               │
│       │ Server #1      │   │ Server #2      │               │
│       └───────┬────────┘   └───────┬────────┘               │
│               └──────────┬─────────┘                        │
│                          ▼                                  │
│              RDMA Network Fabric (RoCE)                     │
│                          │                                  │
│         ┌────────────────┼────────────────┐                 │
│         ▼                ▼                ▼                 │
│   Storage Server   Storage Server   Storage Server          │
└─────────────────────────────────────────────────────────────┘


┌─────────────────────────────────────────────────────────────┐
│                            ADW                              │
│                                                             │
│                RAC (Active - Active)                        │
│                                                             │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐                    │
│  │ DB Server│ │ DB Server│ │ DB Server│                    │
│  │    #1    │ │    #2    │ │    #3    │                    │
│  └────┬─────┘ └────┬─────┘ └────┬─────┘                    │
│       │            │            │                          │
│  ┌────┴─────┐ ┌────┴─────┐ ┌────┴─────┐                    │
│  │ DB Server│ │ DB Server│ │ DB Server│                    │
│  │    #4    │ │    #5    │ │    #6    │                    │
│  └────┬─────┘ └────┬─────┘ └────┬─────┘                    │
│       └─────────────┼─────────────┘                         │
│                     ▼                                       │
│              RDMA Network Fabric (RoCE)                     │
│                     │                                       │
│              Multiple Storage Servers                       │
└─────────────────────────────────────────────────────────────┘
```

---

# 3. RDW RAC 구성

## 3.1 [FACT] RDW Database Server

장표에서는 RDW를 다음과 같이 구성한다.

```text
RDW
└─ RAC (Active-Active)
   ├─ Database Server #1
   └─ Database Server #2
```

즉 **RDW는 2개의 Database Server 노드가 동시에 Active로 참여하는 RAC 2노드 구조**이다.

## 3.2 [FACT] RDW Storage 계층

두 Database Server는 직접 로컬 디스크를 사용하는 구조로 표현되지 않고,
**RDMA Network Fabric(RoCE)**을 통해 Storage Server 계층에 연결된다.

장표에서는 RDW Storage Server가 **3개**로 표현되어 있다.

```text
Database Server #1 ─┐
                    ├─ RDMA Network Fabric(RoCE)
Database Server #2 ─┘
                    │
          ┌─────────┼─────────┐
          ▼         ▼         ▼
       Storage    Storage    Storage
       Server     Server     Server
```

## 3.3 [ANALYSIS] RDW의 고가용성 의미

RDW는 2노드 RAC Active-Active 구성이므로,
단일 DB 서버에 서비스가 종속되지 않도록 설계한 것으로 해석할 수 있다.

```text
정상
DB #1 Active
DB #2 Active

DB #1 장애
   ↓
DB #2를 통해 DB 서비스 지속
```

다만 실제 서비스 재배치 정책, RAC Service Preferred/Available Instance,
Application Connection 재접속 방식은 본 장표에서 확인되지 않는다.

---

# 4. ADW RAC 구성

## 4.1 [FACT] ADW Database Server

ADW는 RDW보다 큰 **RAC Active-Active 6노드** 구조이다.

```text
ADW
└─ RAC (Active-Active)
   ├─ Database Server #1
   ├─ Database Server #2
   ├─ Database Server #3
   ├─ Database Server #4
   ├─ Database Server #5
   └─ Database Server #6
```

## 4.2 [FACT] ADW Storage 계층

6개 Database Server가 하나의
**RDMA Network Fabric(RoCE)**을 통해 다수의 Storage Server와 연결된다.

장표상 Storage Server는 여러 개를 병렬 배치한 구조로 표현되어 있다.

```text
DB #1 ─┐
DB #2 ─┤
DB #3 ─┤
DB #4 ─┼── RDMA Network Fabric(RoCE)
DB #5 ─┤
DB #6 ─┘
        │
        ▼
Multiple Exadata Storage Servers
```

## 4.3 [ANALYSIS] ADW가 6노드인 이유

원본 장표는 노드 수만 제시하며 산정 근거는 제시하지 않는다.

다만 앞 장의 DB 아키텍처에서 ADW가 다음과 같은 대용량 분석성 기능을 담당하도록 배치되어 있다.

- 분석 SoR
- 분석 통합 요약집계
- 분석 단위 업무마트
- 분석 보고서마트
- BI 포탈
- OLAP
- Self BI
- 데이터거버넌스

따라서 **RDW 2노드보다 ADW 6노드가 더 큰 Compute 구성을 갖는 것은 분석/집계성 처리량을 수용하기 위한 물리적 분리 구조로 해석 가능**하다.

단, 이는 장표 구조에 대한 아키텍처적 해석이며 정확한 용량산정 근거는 별도 자료 확인이 필요하다.

---

# 5. Exadata 전체 계층

원본 장표 우측의 **「Exadata 계층 및 주요 프로세스」**는 크게 3계층으로 구성된다.

```text
┌────────────────────────────────────┐
│          Database Server           │
│                                    │
│ Database Instance                  │
│ └─ DBRM                            │
│                                    │
│ System Software                    │
│ ├─ Exascale Client Services        │
│ ├─ DBMCLI                          │
│ └─ MS                              │
│                                    │
│ Grid Infrastructure                │
│ └─ ASM Instance                    │
└──────────────────┬─────────────────┘
                   │
                   ▼
┌────────────────────────────────────┐
│    RDMA Network Fabric (RoCE)      │
└──────────────────┬─────────────────┘
                   │
                   ▼
┌────────────────────────────────────┐
│           Storage Server           │
│                                    │
│ Exadata System Software            │
│ Exadata Storage Services           │
│ ├─ CellCLI                         │
│ ├─ MS                              │
│ ├─ CELLSRV                         │
│ └─ RS                              │
│                                    │
│ Memory                             │
│ └─ XRMEM Cache                     │
│                                    │
│ Performance-Optimized Flash        │
│ ├─ Flash Cache                     │
│ └─ Flash Log                       │
│                                    │
│ Disk Storage                       │
└────────────────────────────────────┘
```

---

# 6. Database Server 계층

## 6.1 [FACT] 역할

원본 장표 설명:

> 사용자의 SQL을 실행하고 Oracle RAC 및 데이터베이스 서비스를 제공하는 컴퓨팅 계층

따라서 Database Server는 Exadata에서 **DB SQL 실행 및 RAC Compute 역할**을 담당한다.

## 6.2 구성요소

| 계층 | 프로세스/구성요소 | 장표 표기 |
|---|---|---|
| Database Instance | DBRM | Database Resource Manager |
| System Software | Exascale Client Services | Exadata 관련 시스템 소프트웨어 |
| System Software | DBMCLI | Database Machine Command Line Interface |
| System Software | MS | Management Server |
| Grid Infrastructure | ASM Instance | Oracle ASM |

### [ANALYSIS]

Database Server가 담당하는 책임은 다음처럼 정리할 수 있다.

```text
Client / Application SQL
        ↓
Database Instance
        ↓
DBRM
        ↓
RAC / Grid Infrastructure
        ↓
ASM
        ↓
RDMA Network
        ↓
Storage Server
```

---

# 7. RDMA Network Fabric(RoCE)

## 7.1 [FACT] 역할

장표 설명:

> Database Server와 Storage Server 간 데이터를 고대역폭으로 전송하는 고속 통신 계층

즉 RDMA Network Fabric은 **Compute와 Storage 사이의 핵심 데이터 경로**이다.

## 7.2 장표 기술

```text
RDMA Network Fabric (RoCE)
```

여기서 장표는 RoCE 기반 RDMA 네트워크를 명시한다.

### [ANALYSIS]

DB 서버와 Storage 서버가 별도 계층으로 분리되어 있으므로,
해당 네트워크는 단순 관리망이 아니라 **SQL 처리 성능과 Storage I/O 성능에 직접 영향을 주는 데이터 패브릭**으로 관리해야 한다.

---

# 8. Storage Server 계층

## 8.1 [FACT] 역할

원본 장표 설명:

> 데이터를 저장하고 Smart Scan, Flash Cache 등 Exadata 고유 기능을 통해 데이터 처리를 가속화하는 지능형 스토리지 계층

따라서 Storage Server는 일반 Block Storage가 아니라
**데이터 처리 기능을 포함한 Exadata Smart Storage** 역할을 수행한다.

## 8.2 Storage Server 내부 구성

| 계층 | 구성요소 |
|---|---|
| Exadata System Software | Exadata System Software |
| Storage Service | Exadata Storage Services |
| 관리 | CellCLI |
| 관리 | MS |
| Storage Process | CELLSRV |
| Restart | RS |
| Memory | XRMEM Cache |
| Flash | Flash Cache |
| Flash | Flash Log |
| Persistent Storage | Disk |

---

# 9. 주요 프로세스 및 약어

원본 장표 하단에는 다음 약어를 직접 정의하고 있다.

| 약어 | 원본 정의 |
|---|---|
| **DBRM** | Database Resource Manager |
| **DBMCLI** | Database Machine Command Line Interface |
| **MS** | Management Server |
| **RS** | Restart Server |

추가로 장표 내부에는 다음 구성요소가 보인다.

| 구성요소 | 위치 |
|---|---|
| ASM Instance | Database Server / Grid Infrastructure |
| Exascale Client Services | Database Server |
| CellCLI | Storage Server |
| CELLSRV | Storage Server |
| XRMEM Cache | Storage Server Memory |
| Flash Cache | Storage Server Flash |
| Flash Log | Storage Server Flash |

> `CELLSRV`, `XRMEM Cache`의 상세 기능 설명은 장표 본문에 별도로 기재되어 있지 않으므로
> 본 문서에서는 원본 표기 수준으로 유지한다.

---

# 10. Active-Active RAC의 의미

## 10.1 [FACT]

원본 장표는 RDW와 ADW 모두 명시적으로 다음과 같이 표현한다.

```text
RAC (Active - Active)
```

즉 정상상태에서 특정 한 노드만 Standby인 구조가 아니라
복수 Database Server가 RAC Cluster에 동시에 참여하는 구성을 전제로 한다.

## 10.2 [ANALYSIS]

개념적 서비스 구조:

```text
Application
    │
    ▼
Oracle RAC Service
    │
    ├───────────────┐
    ▼               ▼
DB Instance #1    DB Instance #2
Active            Active
```

ADW는 같은 원칙을 6개 Database Server로 확장한다.

```text
RAC Service
 │
 ├─ DB #1 Active
 ├─ DB #2 Active
 ├─ DB #3 Active
 ├─ DB #4 Active
 ├─ DB #5 Active
 └─ DB #6 Active
```

그러나 모든 SQL이 모든 노드에 동일하게 분배된다는 의미까지 본 장표에서 확정할 수는 없다.

---

# 11. RDW / ADW 이중화 비교

| 항목 | RDW | ADW |
|---|---|---|
| RAC 구성 | Active-Active | Active-Active |
| Database Server | 2노드 | 6노드 |
| Storage | 3개 Storage Server로 표현 | 다중 Storage Server |
| Network | RDMA Network Fabric(RoCE) | RDMA Network Fabric(RoCE) |
| 물리 특성 | 상대적으로 소규모 RAC | 대규모 RAC |
| 논리 업무 | 실시간/준실시간 및 마케팅 | 분석/BI/대용량 집계 |
| 장애격리 | RAC 노드 단위 | RAC 노드 단위 |
| Storage 처리 | Exadata Storage Services | Exadata Storage Services |

---

# 12. 이전 DB 아키텍처 구성도와의 연결

앞 장의 DB 아키텍처 구조와 이번 이중화 장표를 연결하면 다음과 같다.

```text
                  Logical Data Responsibility

          RDW                              ADW
  실시간/준실시간                    분석/집계/BI
  Marketing Platform                 Data Governance
          │                              │
          ▼                              ▼

                 Physical DB Architecture

       Exadata RDW                     Exadata ADW
       RAC 2 Node                      RAC 6 Node
       Active-Active                   Active-Active
          │                              │
          ▼                              ▼
    RDMA Fabric                     RDMA Fabric
          │                              │
          ▼                              ▼
  Exadata Storage                  Exadata Storage
```

따라서 이 장표는 앞 장의 **논리적 RDW/ADW 책임분리**를
실제 Exadata Compute/Storage/RAC 구조로 내려주는 물리 DB 아키텍처 자료라고 볼 수 있다.

---

# 13. HA와 DR 구분

## 13.1 [FACT]

이번 장표가 직접 정의하는 것은 **Exadata 내부 RAC 고가용성**이다.

```text
Database HA
=
Oracle RAC Active-Active
```

## 13.2 확인 필요

이번 장표만으로 다음은 확인할 수 없다.

```text
센터 장애
   ↓
DR 센터 전환
```

즉 아래 두 개는 구분해야 한다.

| 구분 | 역할 | 본 장표 |
|---|---|---|
| RAC HA | DB 노드/인스턴스 장애 대응 | **확인됨** |
| DR | 센터 전체 재해 대응 | **미표기** |

**RAC 이중화가 곧 DR을 의미하지 않는다.**

---

# 14. 아키텍처 Rule 후보

| Rule ID | Rule | 근거 |
|---|---|---|
| `DB-HA-001` | RDW는 Oracle RAC Active-Active 2노드로 구성한다 | 장표 직접 명시 |
| `DB-HA-002` | ADW는 Oracle RAC Active-Active 6노드로 구성한다 | 장표 직접 명시 |
| `DB-HA-003` | Database Server와 Storage Server는 RDMA Network Fabric(RoCE)으로 연결한다 | 장표 직접 명시 |
| `DB-HA-004` | DB Compute와 Storage는 Exadata 계층으로 분리한다 | 장표 직접 명시 |
| `DB-HA-005` | Storage Server는 Exadata Smart Storage 기능을 사용한다 | 장표 직접 명시 |
| `DB-HA-006` | RAC HA와 센터 DR은 별개의 가용성 구조로 관리한다 | 분석 |
| `DB-HA-007` | DB Connection은 개별 Instance보다 RAC Service를 기준으로 관리하는 방향을 검토한다 | 설계 검토 필요 |
| `DB-HA-008` | 장애 시 Application Connection 재접속 정책을 별도 정의해야 한다 | GAP |
| `DB-HA-009` | RAC 노드별 Workload/Service 배치 정책을 정의해야 한다 | GAP |
| `DB-HA-010` | RDMA Fabric 장애를 DB 고가용성 설계 범위에 포함해야 한다 | 분석 |

---

# 15. 추가 확인 GAP

| GAP ID | 확인 항목 | 현재 상태 |
|---|---|---|
| `GAP-HA-001` | RDW RAC Service 구성 | 미표기 |
| `GAP-HA-002` | ADW RAC Service 구성 | 미표기 |
| `GAP-HA-003` | SCAN / VIP 주소 | 미표기 |
| `GAP-HA-004` | RAC Instance 이름 | 미표기 |
| `GAP-HA-005` | Preferred / Available Instance 정책 | 미표기 |
| `GAP-HA-006` | Client Failover / TAF / Application Continuity 정책 | 미표기 |
| `GAP-HA-007` | Storage Server 정확한 ADW 개수 및 Cell 배치 | 상세 확인 필요 |
| `GAP-HA-008` | RDMA Fabric 이중화 상세 | 미표기 |
| `GAP-HA-009` | ASM Disk Group 구성 | 미표기 |
| `GAP-HA-010` | Database Resource Manager 정책 | 미표기 |
| `GAP-HA-011` | Flash Cache / Smart Scan 운영정책 | 미표기 |
| `GAP-HA-012` | Data Guard / DR 구성 | 본 장표 미표기 |
| `GAP-HA-013` | Backup / Recovery 구조 | 미표기 |
| `GAP-HA-014` | RAC 장애시험 시나리오 | 미표기 |
| `GAP-HA-015` | N-1 상태 성능보장 기준 | 미표기 |

---

# 16. 권장 검증 시나리오

아래 항목은 원본 장표에 직접 적힌 내용이 아니라,
현재 구조를 운영 Architecture Baseline으로 만들기 위해 필요한 **[ANALYSIS] 검증항목**이다.

| Test ID | 시나리오 | 검증 목적 |
|---|---|---|
| `DBT-001` | RDW DB Node 1 Down | 2노드 RAC Failover 검증 |
| `DBT-002` | ADW DB Node 1 Down | 6노드 RAC 재분배 검증 |
| `DBT-003` | RAC Service Relocation | 서비스 이동 검증 |
| `DBT-004` | Storage Cell 1 Down | Storage 장애 영향 검증 |
| `DBT-005` | RDMA Path 장애 | Fabric 이중화 검증 |
| `DBT-006` | ASM Disk 장애 | Storage 보호 검증 |
| `DBT-007` | N-1 성능시험 | 장애 후 처리용량 검증 |
| `DBT-008` | 대량 SQL / Smart Scan | Exadata Offload 효과 검증 |
| `DBT-009` | Flash Cache 부하 | Flash 계층 동작 검증 |
| `DBT-010` | 센터 DR 전환 | RAC가 아닌 DR 구조 별도 검증 |

---

# 17. 최종 아키텍처 Big Picture

```text
                        NSIGHT EXADATA DB HA

                          Application
                               │
                               ▼
                        Oracle RAC Service
                               │
             ┌─────────────────┴─────────────────┐
             │                                   │
             ▼                                   ▼

┌──────────────────────────┐       ┌──────────────────────────────┐
│           RDW            │       │             ADW              │
│                          │       │                              │
│ RAC Active-Active        │       │ RAC Active-Active            │
│                          │       │                              │
│ DB Server #1             │       │ DB Server #1                 │
│ DB Server #2             │       │ DB Server #2                 │
│                          │       │ DB Server #3                 │
│                          │       │ DB Server #4                 │
│                          │       │ DB Server #5                 │
│                          │       │ DB Server #6                 │
└────────────┬─────────────┘       └──────────────┬───────────────┘
             │                                    │
             ▼                                    ▼
       RDMA Fabric                           RDMA Fabric
          (RoCE)                                (RoCE)
             │                                    │
             ▼                                    ▼
┌──────────────────────────┐       ┌──────────────────────────────┐
│ Exadata Storage Servers  │       │ Exadata Storage Servers      │
│                          │       │                              │
│ Smart Scan               │       │ Smart Scan                   │
│ XRMEM Cache              │       │ XRMEM Cache                  │
│ Flash Cache              │       │ Flash Cache                  │
│ Flash Log                │       │ Flash Log                    │
└──────────────────────────┘       └──────────────────────────────┘
```

---

# 18. 결론

이번 DB 이중화 구성도에서 확인되는 NSIGHT DB 고가용성의 핵심은 다음과 같다.

1. **RDW와 ADW 모두 Oracle Exadata 기반 RAC Active-Active 구조다.**
2. **RDW는 RAC 2노드**, **ADW는 RAC 6노드**로 구성된다.
3. Database Server와 Storage Server 사이에는 **RDMA Network Fabric(RoCE)**이 위치한다.
4. Database Server는 사용자 SQL 실행과 Oracle RAC/DB 서비스를 담당한다.
5. Storage Server는 데이터를 저장하면서 **Smart Scan, Flash Cache 등 Exadata 기능으로 처리 가속**을 담당한다.
6. Database Server에는 **Database Instance / DBRM / Exascale Client Services / DBMCLI / MS / ASM Instance**가 표현되어 있다.
7. Storage Server에는 **Exadata Storage Services / CellCLI / MS / CELLSRV / RS / XRMEM Cache / Flash Cache / Flash Log**가 표현되어 있다.
8. 이번 장표는 **DB 노드 단위 HA 구조**를 설명하며, 센터 간 DR 구조는 별도 설계 영역이다.
9. 운영 Baseline 확정을 위해서는 RAC Service, SCAN/VIP, Client Failover, RDMA 이중화, ASM, N-1 성능보장 등의 상세 설계를 추가해야 한다.

본 문서는 제공된 장표를 기준으로 한 **NSIGHT Exadata DB High Availability Working Baseline**으로 활용한다.
