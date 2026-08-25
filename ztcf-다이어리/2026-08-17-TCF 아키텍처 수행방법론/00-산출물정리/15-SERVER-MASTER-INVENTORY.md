# NSIGHT Server Master Inventory Architecture

> 상태: **71 Server Current Working Baseline / G30**

## 1. 기준

- 서버 기준 수량: **71대**
- 서버 식별키: **Hostname**
- 기본 관리단위: **서버 1대 = SERVER_MASTER 1행**
- Runtime 상세: **WAS Server → Tomcat JVM Instance → Application**

## 2. 서버 수량

### 시스템 그룹별

| 시스템 그룹 | 수량 |
|---|---:|
| 마케팅플랫폼 | 15 |
| 신BI포털시스템 | 16 |
| 데이터거버넌스 | 4 |
| IT 서비스 및 업무지원 | 28 |
| 데이터플랫폼 시스템 | 8 |
| **합계** | **71** |

### 역할별

| 역할 | 수량 |
|---|---:|
| WEB | 20 |
| WAS | 28 |
| AP | 13 |
| DB | 10 |
| **합계** | **71** |

## 3. Master Schema

| 영역 | 필드 |
|---|---|
| Identity | No, Hostname, 서버명 |
| Classification | Application Group, System Group, Business/App Code |
| Placement | DEV/PROD/DR, Center, Zone |
| Platform | Manufacturer, OS, Server Type |
| Role | WEB/WAS/AP/DB/ETL/CDC |
| Compute | CPU 원산정, CPU 할당, Memory |
| Storage | OS/Data/Log Disk |
| Capacity | tpmC, TPS, Required/Allocated Core |
| Network | AP IP, VIP, DB IP, SCAN, DataGuard |
| Middleware | Apache/Tomcat/Solution |
| Runtime | JVM/Thread/Hikari/Session |
| Application | App/WAR/JVM/ServiceId Set |
| Availability | HA Group, Peer, DR Pair |
| Lifecycle | New/Operating/Delete/Review |
| Evidence | Source, Verification Status, Notes |

## 4. 대표 Server Mapping

| Hostname | 서버명 | Role | Alloc CPU | MEM | Middleware | DR |
|---|---|---|---:|---:|---|---|
| `sbmpcolowb01` | 마케팅플랫폼 WEB #01 | WEB | 12 | 48GB | Apache | `sbmpcolowb51` |
| `sbmpcolowb02` | 마케팅플랫폼 WEB #02 | WEB | 12 | 48GB | Apache | `sbmpcolowb52` |
| `sbmpcolows01` | 마케팅플랫폼 WAS #01 | WAS | 32 | 256GB | Tomcat/NH Cloud | `sbmpcolows51` |
| `sbmpcolows02` | 마케팅플랫폼 WAS #02 | WAS | 32 | 256GB | Tomcat/NH Cloud | `sbmpcolows52` |
| `sbmpmslowb01` | Mini SingleView WEB #01 | WEB | 8 | 32GB | Apache | DR Mapping 확인됨 |
| `sbmpmslows01` | Mini SingleView WAS #01 | WAS | 32 | 256GB | Tomcat | DR Mapping 확인됨 |
| `sbbiptlowb01` | BI Portal WEB #01 | WEB | 4 | 16GB | Apache | 확인필요 |
| `sbbiptlows01` | BI Portal WAS #01 | WAS | 8 | 64GB | Tomcat | 확인필요 |
| `sbrdcoxodb01` | RDW Appliance #01 | DB | 확인필요 | 확인필요 | Exadata/RDW | `sbrdcoxodb51` |
| `sbrdcoxodb02` | RDW Appliance #02 | DB | 확인필요 | 확인필요 | Exadata/RDW | `sbrdcoxodb52` |

## 5. Hostname Validation Model

```text
sb | mpco | l | o | wb | 01
│    │      │   │   │    └─ Sequence
│    │      │   │   └──── Role
│    │      │   └──────── Environment
│    │      └──────────── Platform/OS
│    └─────────────────── Application/Business
└──────────────────────── Company
```

Hostname Parsing은 Inventory 자동검증 Rule 후보로 관리한다.

## 6. Capacity 관리

`tpmC`와 `TPS`를 분리한다.

```text
tpmC = 서버 벤치마크/산정 지표
TPS  = 실제 업무 처리량
```

서버별로 다음을 함께 저장한다.

```text
Required Core
Allocated Core
Capacity GAP
Normal Capacity
N-1 Residual Capacity
DR Residual Capacity
```

## 7. Lifecycle / Baseline 정규화

Current Runtime Baseline 집계 시 삭제 자원은 분리한다.

- OLAP WEB/WAS/AP 4대: DELETE
- 데이터흐름 WAS #02: REVIEW
- Appliance: 노드별/전체 사양 여부 추가 확인

## 8. Runtime 연결 모델

```text
SERVER_MASTER.hostname
        ↓
TOMCAT_JVM.was_hostname
        ↓
APPLICATION_DEPLOYMENT
        ↓
SERVICEID_SET
        ↓
RUNTIME_EVIDENCE
```

이 연결이 완료되어야 서버에서 ServiceId까지, ServiceId에서 서버까지 양방향 추적이 가능하다.

## 9. G30 핵심 미완료

1. 71대 전체 Application/WAR 매핑
2. JVM별 Connector/CATALINA_BASE/Heap/Thread/Hikari
3. 모든 WEB의 Apache Route Target
4. 모든 Server의 HA Group/Peer
5. 운영↔DR 전수 Pair와 RTO/RPO
6. IP/VIP/SCAN/DG 원장 통합
7. 삭제/Review/Appliance 용량 정규화

## 10. 판정

Server Master 구조 자체는 **Current Working Baseline**으로 사용 가능하다.

그러나 Runtime Deployment Inventory가 아직 미완성이라 `SERVER_MASTER → JVM → Application → ServiceId`의 전체 Closed Traceability는 `OPEN`이다.
