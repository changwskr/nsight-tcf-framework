# 데이터베이스 아키텍처 — 서버별 데이터베이스 접근 방안 분석

> 원본 내용: 업무별 부하와 기능 요소를 고려한 애플리케이션–데이터베이스 서버 간 매트릭스  
> 범례: `● 주노드`, `○ 보조노드`  
> 분석 원칙: 표의 원 표시는 업무 서버가 우선 접근할 DB RAC Node와 장애 시 보조 Node를 나타내는 affinity 정보로 해석한다. 실제 connection은 물리 Hostname보다 RAC Service로 구현하는 것이 바람직하다.

---

## 1. 핵심 결론

서버별 DB 접근은 애플리케이션 인스턴스 번호와 DB Node를 교차 배치하여 정상 상태의 부하를 분산하고, 동일 2노드 그룹 안에서 상호 Failover하도록 설계되어 있다.

- RDW #1·#2는 ETL, OLAP, BI포탈, 마케팅플랫폼, 미니싱글뷰가 접근한다.
- ADW #1·#2는 ETL, OLAP, 배치 AP의 대용량 배치 그룹이다.
- ADW #3·#4는 신용실적, BI포탈, Self-BI의 일반 BI 그룹이다.
- ADW #5·#6은 데이터흐름관리와 비즈메타의 Online 거버넌스 그룹이다.
- `#1` 애플리케이션은 일반적으로 홀수 DB Node를 주노드로, `#2`는 짝수 DB Node를 주노드로 사용한다.
- 운영 의왕 인스턴스 `#1/#2`와 DR 안성 인스턴스 `#51/#52`도 RDW #1/#2에 같은 교차 패턴으로 배치된다.

```text
Application #1 ──● DB Node A
               └─○ DB Node B

Application #2 ──● DB Node B
               └─○ DB Node A
```

---

## 2. 근거 수준

| 수준 | 내용 |
|---|---|
| 확인 사실 | 검은 원은 주노드, 흰 원은 보조노드로 범례에 명시됨 |
| 확인 사실 | RDW 2개 Node와 ADW 6개 Node가 행으로 표시됨 |
| 확인 사실 | ETL은 RDW와 ADW #1·#2 모두에 접근함 |
| 확인 사실 | OLAP은 RDW와 ADW #1·#2 모두에 접근함 |
| 확인 사실 | BI포탈은 RDW와 ADW #3·#4 모두에 접근함 |
| 확인 사실 | 마케팅플랫폼·미니싱글뷰 운영/DR 인스턴스는 RDW에 접근함 |
| 설계 해석 | 주·보조 Node 표시는 RAC Service의 Preferred/Available Instance affinity |
| 설계 해석 | 교차 배치는 정상 부하를 두 Node에 나누고 장애 시 상대 Node로 전환하기 위함 |
| 미확정 | 실제 Service 이름, SCAN 주소, failover option과 connection pool 설정 |
| 미확정 | 한 애플리케이션이 RDW와 ADW를 동시에 사용하는 transaction 경계와 호출 순서 |

---

## 3. 전체 접근 매트릭스 요약

기호: `P=Primary(●)`, `S=Secondary(○)`, `-=접근 없음`

| 업무 서버 | RDW1 | RDW2 | ADW1 | ADW2 | ADW3 | ADW4 | ADW5 | ADW6 |
|---|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| ETL #1 | P | S | P | S | - | - | - | - |
| ETL #2 | S | P | S | P | - | - | - | - |
| 신용실적 #1 | - | - | - | - | P | S | - | - |
| 신용실적 #2 | - | - | - | - | S | P | - | - |
| OLAP #1 | P | S | P | S | - | - | - | - |
| OLAP #2 | S | P | S | P | - | - | - | - |
| BI포탈 #1 | P | S | - | - | P | S | - | - |
| BI포탈 #2 | S | P | - | - | S | P | - | - |
| Self-BI #1 | - | - | - | - | P | S | - | - |
| 배치 AP #1 | - | - | P | S | - | - | - | - |
| 데이터흐름관리 #1 | - | - | - | - | - | - | P | S |
| 데이터흐름관리 #2 | - | - | - | - | - | - | S | P |
| 비즈메타 #1 | - | - | - | - | - | - | P | S |
| 비즈메타 #2 | - | - | - | - | - | - | S | P |
| 마케팅플랫폼 #1 | P | S | - | - | - | - | - | - |
| 마케팅플랫폼 #2 | S | P | - | - | - | - | - | - |
| 마케팅플랫폼 #51 | P | S | - | - | - | - | - | - |
| 마케팅플랫폼 #52 | S | P | - | - | - | - | - | - |
| 미니싱글뷰 #1 | P | S | - | - | - | - | - | - |
| 미니싱글뷰 #2 | S | P | - | - | - | - | - | - |
| 미니싱글뷰 #51 | P | S | - | - | - | - | - | - |
| 미니싱글뷰 #52 | S | P | - | - | - | - | - | - |

---

## 4. RDW 접근 매트릭스

### 4.1 RDW #1 주노드 그룹

```text
ETL #1
OLAP #1
BI포탈 #1
마케팅플랫폼 #1
마케팅플랫폼 #51
미니싱글뷰 #1
미니싱글뷰 #51
        │
        ├─ ● RDW #1
        └─ ○ RDW #2
```

### 4.2 RDW #2 주노드 그룹

```text
ETL #2
OLAP #2
BI포탈 #2
마케팅플랫폼 #2
마케팅플랫폼 #52
미니싱글뷰 #2
미니싱글뷰 #52
        │
        ├─ ● RDW #2
        └─ ○ RDW #1
```

### 4.3 분석

- 애플리케이션 인스턴스 번호를 기준으로 RDW 부하를 대칭 분산한다.
- ETL·OLAP·BI포탈은 RDW의 준실시간 또는 원천성 데이터에 접근하는 경로를 갖는다.
- 마케팅플랫폼과 미니싱글뷰는 운영 및 DR 인스턴스 모두 같은 RDW 쌍을 사용한다.
- RDW 한 노드 장애 시 모든 보조 connection이 잔여 노드로 집중되므로 N-1 부하를 검증해야 한다.

---

## 5. ADW #1·#2 접근 — 데이터플랫폼 대용량 배치 그룹

```text
ETL #1 ─────────┐
OLAP #1 ────────┼─ ● ADW #1 / ○ ADW #2
배치 AP #1 ─────┘

ETL #2 ─────────┐
OLAP #2 ────────┼─ ● ADW #2 / ○ ADW #1
```

| 업무 서버 | 주노드 | 보조노드 | 목적 |
|---|---|---|---|
| ETL #1 | ADW #1 | ADW #2 | 대량 적재·변환 |
| ETL #2 | ADW #2 | ADW #1 | 대량 적재·변환 분산 |
| OLAP #1 | ADW #1 | ADW #2 | 배치성 집계·분석 |
| OLAP #2 | ADW #2 | ADW #1 | OLAP 부하 분산 |
| 배치 AP #1 | ADW #1 | ADW #2 | 공통 대용량 배치 |

### 분석

- 동일 AP가 RDW와 ADW #1·#2에 모두 접근할 수 있어 추출–변환–적재 파이프라인을 구성한다.
- 배치 AP #1은 #2 짝이 없으므로 ADW #1을 Primary, #2를 Secondary로 사용한다.
- 대량 배치 connection은 Online connection pool과 분리하고 parallel·undo·temp를 통제해야 한다.

---

## 6. ADW #3·#4 접근 — BI 일반 업무 그룹

```text
신용실적 #1
BI포탈 #1
Self-BI #1
      ├─ ● ADW #3
      └─ ○ ADW #4

신용실적 #2
BI포탈 #2
      ├─ ● ADW #4
      └─ ○ ADW #3
```

| 업무 서버 | 주노드 | 보조노드 | 목적 |
|---|---|---|---|
| 신용실적 #1 | ADW #3 | ADW #4 | 실적 조회·배치 |
| 신용실적 #2 | ADW #4 | ADW #3 | 실적 부하 분산 |
| BI포탈 #1 | ADW #3 | ADW #4 | 정형 BI 조회 |
| BI포탈 #2 | ADW #4 | ADW #3 | BI 부하 분산 |
| Self-BI #1 | ADW #3 | ADW #4 | 비정형 분석 |

### 분석

- BI포탈은 RDW와 ADW 모두 접근하므로 최신 준실시간 정보와 분석마트를 조합할 수 있다.
- 두 DB를 하나의 transaction으로 묶는 XA 구조인지, 독립 조회 후 애플리케이션에서 조합하는지 확인해야 한다.
- Self-BI는 ADW #3에만 Primary affinity가 있으므로 runaway query가 #3에 집중되지 않도록 DBRM과 timeout이 필요하다.

---

## 7. ADW #5·#6 접근 — 데이터거버넌스 Online 그룹

```text
데이터흐름관리 #1
비즈메타 #1
      ├─ ● ADW #5
      └─ ○ ADW #6

데이터흐름관리 #2
비즈메타 #2
      ├─ ● ADW #6
      └─ ○ ADW #5
```

| 업무 서버 | 주노드 | 보조노드 | 목적 |
|---|---|---|---|
| 데이터흐름관리 #1 | ADW #5 | ADW #6 | 계보·흐름 Online 조회 |
| 데이터흐름관리 #2 | ADW #6 | ADW #5 | 조회 부하 분산 |
| 비즈메타 #1 | ADW #5 | ADW #6 | 메타·품질 관리 |
| 비즈메타 #2 | ADW #6 | ADW #5 | 관리 부하 분산 |

장표의 상위 업무명은 `비즈메타`로 표시되며, 이전 구성도에서는 비즈메타·데이터품질이 통합되어 있으므로 실제 Service 적용 범위를 확인해야 한다.

---

## 8. 애플리케이션별 DB 접근 요약

### 8.1 단일 DB 계층 접근

```text
신용실적       → ADW #3,#4
Self-BI        → ADW #3,#4
배치 AP        → ADW #1,#2
데이터흐름관리 → ADW #5,#6
비즈메타       → ADW #5,#6
마케팅플랫폼   → RDW #1,#2
미니싱글뷰     → RDW #1,#2
```

### 8.2 RDW·ADW 이중 계층 접근

```text
ETL     → RDW #1,#2 + ADW #1,#2
OLAP    → RDW #1,#2 + ADW #1,#2
BI포탈  → RDW #1,#2 + ADW #3,#4
```

이중 계층 접근 업무는 DB별 계정·connection pool·transaction timeout·오류처리를 분리해야 한다.

---

## 9. RAC Service 기반 구현

물리 DB Node 주소를 애플리케이션에 직접 설정하지 않고 업무별 Service를 사용해야 한다.

```text
ETL #1
  ├─ svc_rdw_etl_1 → Preferred RDW1 / Available RDW2
  └─ svc_adw_etl_1 → Preferred ADW1 / Available ADW2

BI포탈 #1
  ├─ svc_rdw_bi_1  → Preferred RDW1 / Available RDW2
  └─ svc_adw_bi_1  → Preferred ADW3 / Available ADW4

거버넌스 #1
  └─ svc_adw_gov_1 → Preferred ADW5 / Available ADW6
```

Service 이름은 예시다. 실제 구현에는 다음 속성이 필요하다.

- SCAN 기반 connect descriptor
- Preferred/Available Instance
- Load Balancing Goal과 CLB Goal
- FAN/ONS 및 Fast Connection Failover
- failover retry·delay·type·method
- Application Continuity 적용 여부
- Service별 DB Resource Manager Consumer Group

---

## 10. Connection Pool 설계

```text
Application
  ├─ RDW Read Pool
  ├─ RDW Batch Pool
  ├─ ADW Online Pool
  └─ ADW Batch Pool
```

| 설정 | 권장 방향 |
|---|---|
| Initial/Min/Max Pool | 업무 동시성과 DB session 한도에 맞춤 |
| Connection Validation | 장애 후 stale connection 제거 |
| Connect/Read Timeout | 무한 대기 방지 |
| Retry | 짧은 backoff와 제한된 횟수 |
| Statement Timeout | runaway SQL 차단 |
| FAN/ONS | RAC Service 상태를 pool에 즉시 전달 |
| Credential | 업무·DB·환경별 별도 계정 |

RDW와 ADW를 동시에 접근하는 업무는 pool 이름, 계정, transaction boundary를 명시적으로 분리해야 한다.

---

## 11. 장애 전환 메커니즘

### 11.1 주노드 장애

```text
Application #1 → Primary DB Node 장애
                     ↓
RAC Clusterware가 Instance·Service 장애 감지
                     ↓
Service를 Secondary Node로 재배치
                     ↓
FAN/ONS가 connection pool에 이벤트 전달
                     ↓
stale connection 제거·재연결
```

### 11.2 복구 후 재분산

```text
장애 Node 복구
   ↓
Instance 정상 확인
   ↓
Service planned relocation
   ↓
Application #1/#2 부하를 다시 교차 분산
```

서비스를 즉시 원복할지 안정화 후 계획 전환할지 정책이 필요하다. 잦은 자동 failback은 connection churn과 업무 오류를 유발할 수 있다.

---

## 12. 이중 DB 접근 시 Transaction 원칙

ETL·OLAP·BI포탈처럼 RDW와 ADW를 모두 사용하는 업무는 다음 원칙이 필요하다.

```text
권장
RDW 조회/추출 → Application 처리 → ADW 적재
각 DB transaction 독립 + checkpoint + 대사

주의
RDW와 ADW를 하나의 장기 XA transaction으로 묶음
→ lock·장애복구·in-doubt transaction 복잡도 증가
```

- 읽기와 쓰기 방향을 명확히 한다.
- source SCN·batch ID·load ID를 기록한다.
- 재시작 시 중복을 막는 idempotency key를 사용한다.
- partial success 상태와 보상·재처리 절차를 정의한다.
- 최종 row count·금액·checksum을 대사한다.

---

## 13. 접근 보안

| 통제 | 적용 방향 |
|---|---|
| 계정 분리 | 업무·DB·환경·읽기/쓰기별 계정 |
| 최소권한 | table 직접 권한 최소화, role·view·procedure 활용 |
| 비밀관리 | password를 설정파일에 평문 저장하지 않음 |
| Network | AP subnet–DB Service port allowlist |
| 암호화 | Oracle Native Network Encryption 또는 TLS 검토 |
| 감사 | logon, 권한변경, DDL, 민감정보 조회 감사 |
| 접근제어 | DB Safer 정책과 RAC Service·계정 매핑 |
| DR | #51/#52 계정·접속경로를 운영과 구분하되 전환 가능하게 관리 |

---

## 14. 운영 모니터링

### 애플리케이션

- pool active/idle/waiting connection
- connection acquisition time과 timeout
- DB별 오류율·retry·failover 횟수
- 주/보조 Node 연결 비율

### RAC Service

- Service 상태와 Instance 배치
- service relocation·failover 이력
- FAN/ONS event 전달 성공
- connection load balancing 편차

### Database

- Service별 DB Time·AAS·CPU
- session 수와 login storm
- wait event·blocking session
- Node 장애 후 N-1 응답시간
- RDW·ADW 이중 접근 batch의 처리시점 대사

---

## 15. 장표 매트릭스 관리 방안

이 매트릭스는 문서 그림으로만 관리하지 말고 기계 판독 가능한 설정 대장으로 전환하는 것이 좋다.

```text
application_id
application_instance
environment
database
service_name
primary_instance
secondary_instance
db_user
access_mode
connection_pool
failover_policy
owner
```

배포 파이프라인은 이 대장과 실제 `srvctl config service`, application datasource 설정의 차이를 자동 검사할 수 있다.

---

## 16. 주요 위험과 대응

| 위험 | 영향 | 대응 방향 |
|---|---|---|
| 물리 Node 직접 접속 | 장애 시 자동 전환 실패 | SCAN·RAC Service 사용 |
| 주·보조 매핑 불일치 | 부하 편중·장애 시 접속 실패 | 설정 대장과 실제 Service 자동 대조 |
| N-1 용량 부족 | Failover 후 응답 지연 | 그룹별 단일 Node 부하 시험 |
| RDW·ADW pool 혼용 | 잘못된 DB 쓰기·transaction 혼선 | datasource·계정·권한 분리 |
| XA 남용 | in-doubt·lock·복구 복잡성 | checkpoint·멱등성 기반 비동기 처리 |
| Self-BI #3 집중 | ADW #3 과부하 | DBRM·query timeout·필요시 별도 Service |
| DR #51/#52 설정 불일치 | 재해 전환 실패 | 정기 connection·권한·방화벽 시험 |
| 자동 failback 반복 | connection churn·업무 오류 | 계획 원복과 안정화 기준 |

---

## 17. 검증 체크리스트

- [ ] 22개 업무 인스턴스의 주·보조 DB Node 매핑을 실제 Service와 대조했는가?
- [ ] 모든 datasource가 물리 Hostname이 아닌 SCAN·Service를 사용하는가?
- [ ] Preferred/Available Instance와 매트릭스의 ●/○가 일치하는가?
- [ ] FAN/ONS와 connection pool의 Fast Connection Failover가 동작하는가?
- [ ] 각 2노드 그룹에서 Primary 장애 N-1 부하 시험을 수행했는가?
- [ ] ETL·OLAP·BI포탈의 RDW/ADW datasource와 계정이 분리되어 있는가?
- [ ] 이중 DB 처리에 checkpoint·멱등성·대사 절차가 있는가?
- [ ] 업무별 최소권한과 DB Safer 접근정책을 검증했는가?
- [ ] 의왕 #1/#2와 안성 #51/#52의 전환 연결을 시험했는가?
- [ ] 매트릭스 설정과 실제 배포설정의 drift를 자동 탐지하는가?

---

## 18. 최종 평가

서버별 DB 접근 매트릭스는 애플리케이션 인스턴스를 DB Node 쌍에 교차 배치하여 정상 부하를 균등화하고 장애 시 상호 전환하는 명확한 affinity 구조다. 또한 RDW와 ADW의 목적별 접근을 분리해 실시간·마케팅·배치·BI·거버넌스 워크로드를 각 Node 그룹에 배치한다.

실제 가용성을 확보하려면 이 표를 물리 Node 접속 설정으로 구현해서는 안 된다. **RAC Service, SCAN, Preferred/Available Instance, FAN/ONS, 분리된 connection pool, 최소권한, N-1 시험과 설정 drift 관리**로 구체화해야 한다.
