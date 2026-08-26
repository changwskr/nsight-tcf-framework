# 데이터베이스 아키텍처 — OLTP 및 대용량 배치 수행 방안 분석

> 원본 범위: `4.4 OLTP 및 대용량 배치 수행 방안`  
> 원본 목적: 업무별 부하와 기능 요소를 고려하여 OLTP·대용량 배치 부하를 분산  
> 핵심 원칙: ADW 6개 RAC Node를 2개씩 한 그룹으로 지정하고, RDW 2개 Node도 업무·배치 역할에 따라 분리한다.

---

## 1. 핵심 결론

이 설계는 ADW 6개 노드를 `대용량 배치`, `일반 배치`, `Online`의 세 그룹으로 분리하고, RDW 2개 노드도 마케팅 Online·ETL·OGG 역할을 분산하는 워크로드 격리 구조다.

- ADW #1·#2는 데이터플랫폼의 대용량 배치와 OLAP 배치에 우선 사용한다.
- ADW #3·#4는 BI포탈·신용실적·Self-BI 및 일반 배치를 처리한다.
- ADW #5·#6은 데이터거버넌스 Online 조회·업무를 처리한다.
- ADW Node 그룹은 RAC Service와 Failover 정책을 업무 단위로 구성한다.
- RDW #1·#2는 마케팅플랫폼 Online 처리를 분산한다.
- RDW #2는 OGG와 대용량 ETL 배치를 수행하는 전용 성격이 강하다.
- 대량 적재가 집중되는 ADW #1·#2에는 별도 Undo/Temp Tablespace를 추가해 다른 워크로드 영향을 줄인다.

```text
ADW 6 Nodes
  ├─ #1,#2 : 대용량 배치 / OLAP / 데이터플랫폼
  ├─ #3,#4 : 일반 배치 / BI포탈 / 신용실적 / Self-BI
  └─ #5,#6 : Online / 데이터거버넌스

RDW 2 Nodes
  ├─ #1 : 마케팅 Online 중심
  └─ #2 : 마케팅 Online + OGG + 대용량 ETL
```

---

## 2. 근거 수준

| 수준 | 내용 |
|---|---|
| 확인 사실 | ADW 6개 Node를 2개씩 3개 그룹으로 표시함 |
| 확인 사실 | ADW #1·#2는 대용량 배치 수행 Node로 표시됨 |
| 확인 사실 | ADW #3·#4는 일반 배치 수행 Node로 표시됨 |
| 확인 사실 | ADW #5·#6은 Online 수행 Node로 표시됨 |
| 확인 사실 | OGG는 RDW #2에서 수행한다고 명시됨 |
| 확인 사실 | 대용량 배치는 RDW #2에서 수행한다고 명시됨 |
| 확인 사실 | ADW #1·#2에 대용량 적재용 Undo/Temp TS 추가가 명시됨 |
| 설계 해석 | DB Service·Resource Manager·connection pool로 논리적 affinity를 구현 |
| 설계 해석 | 2노드 그룹은 정상시 분산, 장애시 상호 Failover하는 쌍 |
| 미확정 | 실제 Oracle Service 이름과 Preferred/Available Instance 설정 |
| 미확정 | 장표 하단 Online Node Failover 예시의 `#1/#2`가 `#5/#6`의 오기인지 여부 |
| 미확정 | RDW #2에서 OGG와 대용량 배치가 동시에 수행될 때 자원 우선순위 |

---

## 3. 전체 AP/DB 분리 구성도

```text
┌──────────────────────────── 의왕 AP 계층 ────────────────────────────┐
│ ETL #1,#2 / 배치 AP #1 / OLAP #1,#2                                │
│ 신용실적 #1,#2 / Self-BI #1 / BI포탈 #1,#2                         │
│ 데이터흐름관리 #1 / 비즈메타·데이터품질 #1,#2                      │
│ 마케팅플랫폼 #1,#2 / 미니싱글뷰 #1,#2                              │
└─────────────────────────────────┬────────────────────────────────────┘
                                  │ 업무별 DB Service
                                  ▼
┌──────────────────────────── ADW RAC ─────────────────────────────────┐
│ #1,#2 대용량 배치 │ #3,#4 일반 배치 │ #5,#6 Online                 │
│ 데이터플랫폼      │ BI포탈           │ 데이터거버넌스               │
└──────────────────────────────────────────────────────────────────────┘

┌──────────────────────────── RDW RAC ─────────────────────────────────┐
│ #1 마케팅 Online          │ #2 마케팅 Online + OGG + 대용량 ETL     │
└─────────────────────────────────┬────────────────────────────────────┘
                                  │ DR 서비스
                                  ▼
┌──────────────────────────── 안성 AP 계층 ────────────────────────────┐
│ 마케팅플랫폼 #51,#52 / 미니싱글뷰 #51,#52                           │
└──────────────────────────────────────────────────────────────────────┘
```

---

## 4. ADW Node 그룹 구성

### 4.1 그룹 A — 대용량 배치 Node `ADW #1, #2`

```text
ETL #1,#2
배치 AP #1
OLAP #1,#2
      ↓ 대용량 배치 Service
┌─────────────────────────┐
│ ADW #1  ↔  ADW #2       │
│ 대용량 배치 수행 Node   │
│ 데이터플랫폼            │
└─────────────────────────┘
```

장표 기준:

- ETL 서버 2대 중 1번 서버를 ADW 전용으로 사용
- ETL 대용량 배치는 ADW #1·#2에서 수행
- OLAP 배치성 업무는 ADW #1·#2에서 수행
- 대용량 적재용 별도 Undo/Temp Tablespace 추가

### 설계 의도

- 대규모 Direct Path Load, Merge, 집계, Parallel Query의 CPU·I/O를 Online Node에서 격리한다.
- 전용 Undo는 대량 DML의 undo segment 경쟁과 공간 부족 영향을 줄인다.
- 전용 Temp는 hash join, sort, parallel query의 temp 사용을 다른 업무에서 격리한다.

### 주의사항

- #1·#2가 동일 shared storage를 사용하므로 storage I/O는 ADW 전체와 완전히 분리되지 않는다.
- parallel degree가 과도하면 Storage Server와 RDMA fabric을 공유하는 다른 그룹에 영향을 줄 수 있다.
- 대용량 배치 동시 실행 수와 resource plan을 제한해야 한다.

---

### 4.2 그룹 B — 일반 배치 Node `ADW #3, #4`

```text
신용실적 #1,#2
Self-BI #1
BI포탈 #1,#2
       ↓ 일반 업무·배치 Service
┌─────────────────────────┐
│ ADW #3  ↔  ADW #4       │
│ 일반 배치 수행 Node     │
│ BI포탈                  │
└─────────────────────────┘
```

장표 기준:

- 일반 ETL 배치는 ADW #3·#4에서 수행
- BI포탈과 신용실적은 ADW #3·#4에서 수행
- Self-BI도 동일 AP 그룹에 표시됨

### 설계 의도

- 정형 BI와 일반 배치를 대용량 적재 노드에서 분리한다.
- BI포탈·신용실적의 예측 가능한 응답시간을 확보한다.
- Self-BI의 비정형 Query는 일반 배치와 경합할 수 있으므로 별도 Consumer Group이 필요하다.

---

### 4.3 그룹 C — Online Node `ADW #5, #6`

```text
데이터흐름관리 #1
비즈메타·데이터품질 #1,#2
          ↓ Online Service
┌─────────────────────────┐
│ ADW #5  ↔  ADW #6       │
│ Online 수행 Node        │
│ 데이터거버넌스          │
└─────────────────────────┘
```

장표 기준:

- 데이터흐름관리, 비즈메타, 데이터품질은 ADW #5·#6에서 수행
- Online용 Node Failover는 업무 그룹 기준으로 설정

### 설계 의도

- 메타·품질·계보의 대화형 조회를 장시간 배치에서 격리한다.
- #5·#6이 상호 Primary/Secondary 역할을 수행하도록 Service affinity를 구성한다.

### 장표 표기 불일치

하단 예시는 `주노드 #1이면 보조노드 #2`라고 설명하지만 Online Node는 그림에서 #5·#6이다. 이는 일반적인 2노드 쌍 예시이거나 번호 오기일 수 있으므로 실제 Service 설정을 기준으로 확인해야 한다.

---

## 5. ADW 업무별 배치 매핑

| 업무/AP | 정상 DB Node 그룹 | 처리 유형 | Failover 대상 |
|---|---|---|---|
| ETL 대용량 배치 | ADW #1,#2 | 대량 적재·변환 | 그룹 내 상호 전환 |
| OLAP 배치 | ADW #1,#2 | 대량 집계·분석 | 그룹 내 상호 전환 |
| 데이터플랫폼 | ADW #1,#2 | 대용량 배치 중심 | 그룹 내 상호 전환 |
| 일반 ETL 배치 | ADW #3,#4 | 일반 적재 | 그룹 내 상호 전환 |
| BI포탈 | ADW #3,#4 | 조회·정형 보고 | 그룹 내 상호 전환 |
| 신용실적 | ADW #3,#4 | 업무 조회·배치 | 그룹 내 상호 전환 |
| Self-BI | ADW #3,#4 | 비정형 분석 | 그룹 내 상호 전환 |
| 데이터흐름관리 | ADW #5,#6 | Online | 그룹 내 상호 전환 |
| 비즈메타 | ADW #5,#6 | Online | 그룹 내 상호 전환 |
| 데이터품질 | ADW #5,#6 | Online | 그룹 내 상호 전환 |

---

## 6. RDW Node 역할 분리

```text
마케팅플랫폼 AP #1 ── RDW Service A ──> RDW #1
마케팅플랫폼 AP #2 ── RDW Service B ──> RDW #2
미니싱글뷰 #1,#2 ──── Online Service ──> RDW #1,#2
ETL 일반 배치 ─────── Batch Service ──> RDW #1,#2
ETL 대용량 배치 ───── Large Batch ────> RDW #2
OGG Replicat/Process ─ OGG Service ────> RDW #2
```

### 장표 기준

| 항목 | 수행 Node |
|---|---|
| OGG | RDW #2 |
| 마케팅플랫폼 AP #1 | RDW #1 기준 |
| 마케팅플랫폼 AP #2 | RDW #2 기준 |
| ETL 일반 배치 | RDW #1·#2 |
| ETL 대용량 배치 | RDW #2 |

### 분석

- 마케팅 Online은 두 RDW Node로 분산되어 가용성과 처리량을 확보한다.
- OGG와 대용량 배치가 RDW #2에 집중되므로 redo apply·ETL·online query 간 경합 가능성이 있다.
- RDW #2 장애 시 OGG와 대용량 배치의 대체 Node·재시작 절차를 정의해야 한다.
- 마케팅 AP별 Node affinity가 connection string 고정이 아니라 RAC Service로 구현되어야 장애 전환이 가능하다.

---

## 7. 의왕·안성 마케팅 AP 연결

```text
의왕
  마케팅플랫폼 #1,#2
  미니싱글뷰 #1,#2
          │
          ├─────────────┐
          ▼             ▼
       RDW #1        RDW #2

안성(DR)
  마케팅플랫폼 #51,#52
  미니싱글뷰 #51,#52
          │
          └─ DR 전환 시 RDW Service 연결
```

장표는 안성 AP가 RDW 쪽으로 연결되는 화살표를 표시한다. 운영·DR 전환 시 DB Service, SCAN, 방화벽, credential, DNS가 함께 전환되어야 한다.

---

## 8. Oracle RAC Service 기반 구현 방안

노드 그룹은 application connection에 물리 Hostname을 직접 넣기보다 RAC Service로 구현하는 것이 적절하다.

```text
svc_adw_large_batch → Preferred: ADW1, ADW2
svc_adw_bi          → Preferred: ADW3, ADW4
svc_adw_online      → Preferred: ADW5, ADW6

svc_rdw_marketing   → Preferred: RDW1, RDW2
svc_rdw_ogg         → Preferred: RDW2 / Available: RDW1
svc_rdw_large_batch → Preferred: RDW2 / Available: RDW1
```

위 이름은 설명을 위한 예시이며 실제 Service 이름은 운영 표준을 따라야 한다.

### 권장 속성

- Preferred/Available Instance
- Service failover type·method·retries·delay
- FAN/ONS 및 Fast Connection Failover
- Application Continuity 또는 transaction replay 적용 가능성
- server-side connection load balancing goal
- client-side connect timeout·retry

---

## 9. DB Resource Manager 설계

```text
ADW Resource Plan
  ├─ LARGE_BATCH_GROUP
  │    └─ CPU·Parallel·I/O 상한
  ├─ BI_GENERAL_GROUP
  │    └─ 정형보고 우선순위
  ├─ SELF_BI_GROUP
  │    └─ Query timeout·parallel 제한
  └─ ONLINE_GOV_GROUP
       └─ 응답시간 우선
```

| Consumer Group | 우선순위 | 통제 대상 |
|---|---:|---|
| Online | 높음 | 짧은 query 응답시간 |
| BI 정형조회 | 중상 | 동시사용자·CPU |
| 일반 배치 | 중 | 배치 window·parallel |
| 대용량 배치 | window별 가변 | CPU·I/O·parallel server |
| Self-BI | 제한 | runaway query·temp·session |

노드 affinity만으로는 공유 Storage와 interconnect 부하를 완전히 분리할 수 없으므로 DBRM이 병행되어야 한다.

---

## 10. Undo·Temp Tablespace 분리

장표는 ADW #1·#2가 대용량 배치를 주로 수행하며 대용량 적재용 Undo/Temp Tablespace를 추가한다고 명시한다.

```text
대용량 배치 Service
       ↓
별도 Undo/Temp 정책
  ├─ 대량 DML Undo 공간 확보
  ├─ 대규모 Sort/Hash Temp 확보
  ├─ 일반 BI 공간 고갈 방지
  └─ 사용률·증가율 독립 모니터링
```

### 주의

- Oracle RAC의 Undo Tablespace는 Instance별로 배정되는 구조이므로 #1·#2 전용 Undo 크기와 retention을 별도 sizing해야 한다.
- Temp Tablespace Group을 사용하면 parallel query의 temp I/O를 여러 tempfile로 분산할 수 있다.
- 별도 Tablespace만으로 storage I/O가 물리적으로 격리되는 것은 아니다.
- 대량 적재 실패 후 Undo·Temp 회수 시간과 재처리 window를 고려해야 한다.

---

## 11. 배치 실행 제어 메커니즘

```text
Control-M / Batch Scheduler
          ↓
선행조건·업무그룹·배치 Window 확인
          ↓
전용 DB Service 선택
          ↓
동시실행 수·Parallel 제한
          ↓
배치 수행
          ↓
대사·통계·성공여부 기록
```

### 대용량 배치

- 대량 적재 window를 Online peak 시간과 분리한다.
- direct path load, partition exchange, parallel DML을 검토한다.
- commit 단위와 redo·undo 발생량을 사전 측정한다.
- 실패 시 재시작 가능한 stage·checkpoint 구조를 사용한다.

### 일반 배치

- ADW #3·#4에서 정형 BI와의 동시 실행을 고려한다.
- 업무별 우선순위와 SLA에 따라 동시 실행 수를 제한한다.
- 장시간 batch가 BI connection을 고갈시키지 않도록 pool·session quota를 적용한다.

---

## 12. 장애조치 시나리오

### 12.1 ADW 그룹 내부 장애

```text
ADW #1 장애
   ↓
svc_adw_large_batch가 #2로 재배치
   ↓
중단 transaction rollback
   ↓
Scheduler가 checkpoint 기준 재시작
```

- Online query는 connection 재연결만으로 복구할 수 있지만 batch는 transaction 상태와 재시작 지점이 필요하다.
- 장애 후 한 노드가 2노드 그룹의 전체 부하를 수용하는 N-1 sizing이 필요하다.

### 12.2 RDW #2 장애

```text
RDW #2 장애
  ├─ 마케팅 Service → RDW #1
  ├─ OGG → RDW #1에서 재시작 또는 대기
  └─ 대용량 ETL → 중단·재스케줄
```

OGG Replicat와 대용량 ETL의 우선 복구순서를 정의해야 한다. 일반적으로 실시간 데이터 최신성을 위한 OGG를 우선하고, 대용량 배치는 재스케줄하는 정책이 합리적이다.

---

## 13. 성능·용량 모니터링

### Node·Service

- Instance별 CPU·DB Time·Active Session
- Service별 transaction·response time·connection
- service relocation·failover 횟수
- RAC global cache wait와 interconnect latency

### Batch

- 배치별 시작·종료·처리건수·throughput
- redo·undo·temp 사용량
- parallel worker와 I/O throughput
- 실패·재시작·재처리 건수

### Online

- percentile 응답시간과 timeout
- connection pool 사용률
- SQL plan regression과 blocking session
- Self-BI runaway query

### Shared Storage

- Exadata Cell I/O·Smart Scan
- flash cache hit와 disk latency
- ASM Disk Group 사용률
- RDMA fabric throughput·congestion

---

## 14. 주요 위험과 대응

| 위험 | 영향 | 대응 방향 |
|---|---|---|
| 노드 affinity만 적용 | 공유 Storage·fabric 경합 지속 | DBRM·parallel·I/O 통제 병행 |
| ADW #1·#2 배치 집중 | 장애 후 #2 또는 #1 과부하 | N-1 시험·동시배치 제한 |
| Self-BI와 일반배치 경합 | BI 응답 지연 | Consumer Group·query timeout |
| ADW Online 번호 불일치 | 잘못된 Service 배치 | #5·#6 실제 설정과 장표 정정 |
| RDW #2 역할 집중 | OGG·ETL·마케팅 동시 영향 | Service 분리·우선순위·failover |
| Undo/Temp 고갈 | 배치 실패·Online 영향 | 전용 공간·자동확장 상한·알람 |
| 물리 Hostname 연결 | Node 장애 시 연결 실패 | SCAN·RAC Service 사용 |
| 배치 재시작점 부재 | 중복·누락·장시간 재처리 | checkpoint·멱등성·대사 |

---

## 15. 검증 체크리스트

- [ ] ADW #1~#6의 실제 RAC Instance와 3개 업무 Service를 매핑했는가?
- [ ] ADW Online Node가 #5·#6인지 장표의 Failover 예시를 정정했는가?
- [ ] 대용량·일반·Online Service의 Preferred/Available Instance를 설정했는가?
- [ ] ADW 각 2노드 그룹에서 1노드 장애 N-1 부하 시험을 수행했는가?
- [ ] DBRM으로 대용량 배치·BI·Self-BI·Online 자원을 통제하는가?
- [ ] ADW #1·#2의 Undo·Temp 크기와 retention을 부하 시험으로 검증했는가?
- [ ] RDW #2 장애 시 OGG와 대용량 ETL 복구순서가 정의되어 있는가?
- [ ] 모든 AP가 Hostname 대신 RAC SCAN·Service를 사용하는가?
- [ ] 배치에 checkpoint·멱등성·데이터 대사 절차가 있는가?
- [ ] 의왕·안성 전환 시 DB Service·credential·방화벽이 함께 전환되는가?

---

## 16. 최종 평가

이 설계는 ADW 6노드를 2개씩 세 그룹으로 나눠 대용량 배치, 일반 BI·배치, Online 거버넌스 워크로드를 논리적으로 분리하고, RDW 2노드도 마케팅·OGG·ETL 역할에 맞게 배치한 합리적인 부하 분산 구조다.

다만 노드 그룹만으로 공유 Storage와 RDMA 부하가 완전히 분리되지는 않는다. 실제 효과를 확보하려면 **RAC Service affinity, DB Resource Manager, Undo·Temp sizing, batch 동시성, N-1 장애시험, checkpoint·대사, RDW #2 역할 집중 완화**가 함께 구현되어야 한다.
