# NSIGHT Configuration Baseline — G60 Working Version

## 1. 목적

본 문서는 G60에서 확인한 NSIGHT 온라인 Runtime 설정을 **Design/Legacy/Working/Runtime Approved** 상태로 분리하여 관리한다.

현재 `Runtime Approved` 값은 아직 없다.

---

## 2. 상태 정의

| 상태 | 의미 |
|---|---|
| LEGACY | 이전 보수 기준 또는 과거 문서 |
| WORKING | 현재 시험 준비용 기준 |
| CANDIDATE | 시험할 대안 |
| OPEN | 결정 필요 |
| RUNTIME_APPROVED | 부하/장애 시험으로 승인 |

---

# 3. User / Session

| Key | Legacy | Working | Approved |
|---|---:|---:|---|
| Total User | 36,000 | 36,000 | - |
| Design Session | 43,200~46,800 | 43,200~46,800 | - |
| Session Idle | 60m | **90m** | UNKNOWN |
| Session Replication | DeltaManager 센터 내부 | DeltaManager 센터 내부 후보 | UNKNOWN |
| L4 Sticky | 70~80m (60m session) | 100~120m (90m session) | UNKNOWN |

---

# 4. Capacity / VM

| Key | Legacy | Working | Approved |
|---|---:|---:|---|
| Peak TPS | 1,200 | 1,200 | UNKNOWN |
| Stress TPS | 1,800 | 1,800 | UNKNOWN |
| 16Core Capacity | 500 TPS | **855 TPS calculated** | UNKNOWN |
| Operational Capacity | 500 conservative | **684 TPS @80%** | UNKNOWN |
| Center Topology | 4/center @500 reference | 2/center example; 3/center N+1 candidate | UNKNOWN |

---

# 5. JVM

| Key | Working | Status |
|---|---:|---|
| Heap General | 24GB initial | WORKING |
| Heap SingleView | 28GB | WORKING |
| Heap General 28GB | test candidate | CANDIDATE |
| 16C/128G Heap General | <=32GB reference | REFERENCE |
| 16C/128G Heap SV | <=40GB reference | REFERENCE |
| GC | G1GC | WORKING |
| MaxGCPauseMillis | 200ms | WORKING |
| Heap Operating Threshold | <=70% | WORKING |
| Xss | 512k candidate | CANDIDATE |
| Heap Dump on OOM | Required | WORKING |
| GC Log | Required | WORKING |

---

# 6. Tomcat

| Key | Legacy/Range | Working | Approved |
|---|---:|---:|---|
| maxThreads | 800~1,000 | **800 initial** | UNKNOWN |
| maxThreads Test Upper | - | **1,000** | UNKNOWN |
| minSpareThreads | 150~200 | 200 candidate | UNKNOWN |
| acceptCount | 500~800 | 800 candidate | UNKNOWN |
| maxConnections | 16,000~20,000 versions | OPEN | UNKNOWN |
| connectionTimeout | 8s reference | 8s working | UNKNOWN |
| keepAliveTimeout | 5s | 5s | UNKNOWN |
| Busy Thread Threshold | - | <=70% | UNKNOWN |

---

# 7. HikariCP

| Key | Legacy | Working | Approved |
|---|---:|---:|---|
| General Pool | 80~100 | **120~150** | UNKNOWN |
| SingleView Pool | 100~120 | **150~180** | UNKNOWN |
| connectionTimeout | 3s | 3s | UNKNOWN |
| Pool Usage | - | <70% normal / 70~80% warning | UNKNOWN |
| DB Session Total | AP × Pool | AP × Pool | UNKNOWN |

Pool 최종값은 DB Connection Hold Time과 DB Session 상한으로 승인한다.

---

# 8. SQL / Transaction / Request

| Layer | Working | Status |
|---|---:|---|
| DB Query Timeout | 2~3s | WORKING |
| Spring Transaction | 4~5s | WORKING |
| TCF Service Deadline | ServiceId별 필요 | OPEN |
| Client/Web Request | 6~8s | WORKING |
| External Connect | 1~2s 자료 존재 | REFERENCE |
| External Read | 3~5s 자료 존재 | REFERENCE |
| L4 Idle | 10~15s / 70~90s 충돌 | OPEN |
| L4 Sticky | Session보다 길게 | WORKING RULE |

### Timeout Rule

```text
DB Query < DB Transaction < Overall Transaction Deadline < Client Request
```

단, L4 Idle/Sticky는 위 Application Deadline Chain과 별도로 관리한다.

---

# 9. Configuration Evidence Required

Runtime Approved 승격 전 실제 파일을 확보한다.

```text
Apache httpd.conf / vhost/proxy config
Tomcat server.xml
Tomcat setenv.sh
Spring application.yml/properties
Hikari datasource config
MyBatis mapper/query-timeout config
Transaction timeout config
L4/GSLB 운영 설정 Export
JVM startup command / process args
```

각 설정은 아래 속성으로 관리한다.

| Config ID | Component | Key | Design | Current | Env | Evidence | Verdict |
|---|---|---|---|---|---|---|---|

현재 실제 `Current` 값이 없는 항목은 Working 값을 실제 설정으로 간주하지 않는다.

---

# 10. G60 Configuration Gate

**판정: CONDITIONAL PASS**

조건:

1. 실제 운영/성능시험 Config Snapshot 확보
2. 16Core VM 승인 TPS 확정
3. Thread/Pool/Heap Load Test
4. Session 60/90 ADR
5. L4 Idle/Sticky 정합성
6. Timeout Late Commit Test
7. DB Session 총량 승인

