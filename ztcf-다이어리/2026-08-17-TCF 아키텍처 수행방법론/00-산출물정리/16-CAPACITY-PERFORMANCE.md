# NSIGHT Capacity / Performance Architecture

## 1. 목적

본 문서는 `2026-08-17-NSIGHT`의 성능·용량 자료를 **하나의 숫자로 강제 통합하지 않고**, 역사적 보수 기준, 현재 Working Baseline, 향후 Runtime 승인값으로 분리하여 관리하기 위한 G60 Capacity / Runtime Architecture Baseline이다.

핵심 원칙은 다음과 같다.

> **NSIGHT 성능은 JVM·Thread·DB Pool 값을 크게 잡아서 확보하는 것이 아니라, 사용자 → 동시 요청 → TPS → VM → Thread → DB Connection → SQL → Transaction → Runtime Evidence를 하나의 Capacity Chain으로 관리하고 성능시험으로 승인값을 확정한다.**

상태 태그:

- `[FACT]` 자료에서 직접 확인
- `[LEGACY]` 기존 보수 산정 기준
- `[WORKING]` 현재 작업 기준이나 Runtime 승인 전
- `[PROPOSAL]` 추가 검증 제안
- `[OPEN]` 의사결정 필요
- `[RUNTIME-APPROVED]` 성능시험/운영증적으로 승인된 값 — 현재 없음

---

## 2. Evidence 범위

주요 근거 자료:

1. `성능 파라미터 비교.txt`
2. `2026-06-03 NSIGHT_용량산정_화면설계서_WAS실행쓰레드포함.docx`
3. `NSIGHT_용량산정_6000지점_세션90분_16CORE128G_정상적용_수정본_확인.docx`
4. `NSIGHT_용량산정_6000지점_세션90분_8CORE_16CORE_8_2_HikariCP_재점검수정본.docx`
5. `NSIGHT_용량산정_세션60분_TomcatThread산정_재작성본.docx`
6. `2026-05-31-NSIGHT_용량산정_세션60분_32core_256G_기준.docx`
7. `NSIGHT_통합_아키텍처_서버인벤토리_전체논의_정리본.md`

문서 간 값 충돌은 삭제하지 않고 Versioned Baseline으로 유지한다.

---

# 3. End-to-End Capacity Chain

```text
Total User
   ↓
Login Session
   ↓
Concurrent Request Rate
   ↓
Concurrent Request User
   ↓
Target Response Time
   ↓
Target TPS
   ↓
VM/AP Capacity
   ↓
Required VM Count
   ↓
Tomcat Worker Thread
   ↓
JVM / GC
   ↓
HikariCP DB Connection
   ↓
DB Session / SQL
   ↓
Transaction / Query Timeout
   ↓
N-1 / Center Failure Residual Capacity
   ↓
Load / Stress / Failure Test
   ↓
Runtime Evidence
   ↓
Approved Baseline
```

### Architecture Rule

```text
Session Count != Concurrent Request Count
TPS != Session Count
Tomcat Thread != DB Connection Count
TPMC != TPS
Maximum Capacity != Operational Capacity
Minimum Spec != Capacity Design Spec
Working Baseline != Runtime Approved Baseline
```

---

# 4. 사용자 / 세션 Baseline

## 4.1 사용자 기준

| 항목 | 값 | 상태 |
|---|---:|---|
| 지점 수 | 6,000 | `[FACT]` |
| 지점당 사용자 | 6명 | `[FACT]` |
| 전체 사용자 | 36,000명 | `[CONFIRMED]` |
| 세션 여유율 | 20~30% | `[CONFIRMED]` |
| 설계 세션 | 43,200~46,800 | `[CONFIRMED]` |
| 일반 온라인 p95 목표 | 3초 | `[CONFIRMED]` |

## 4.2 Session Timeout 버전

| 기준 | Session Idle | L4 Sticky | 상태 |
|---|---:|---:|---|
| 세션 60분 문서 | 60분 | 70~80분 | `[LEGACY/ALTERNATIVE]` |
| 세션 90분 문서 | 90분 | 100~120분 | `[WORKING]` |

현재 최신 성능 Working 자료에서는 **90분**을 사용한다. 그러나 용량산정 화면 자체는 60분/90분을 선택 가능하게 정의하고 있으므로 최종 운영정책은 별도 ADR 대상이다.

### Working Decision

```text
Session Idle Timeout = 90분 [WORKING]
Final Runtime/Policy Decision = OPEN
```

### Session HA

자료에서는 Tomcat `DeltaManager`를 **센터 내부**에 적용하고 센터 간 복제는 하지 않는 기준이 반복된다.

```text
센터 A Tomcat Cluster
   └─ DeltaManager Replication

센터 B Tomcat Cluster
   └─ DeltaManager Replication

센터 A ↔ 센터 B
   └─ Session Full Replication 미적용
```

`DeltaManager` 최종 적용 여부는 JWT/Session 운영모드 ADR과 함께 G70에서 확정한다.

---

# 5. 동시요청자 / TPS

## 5.1 공식

```text
Concurrent User = Total User × Concurrent Request Rate
TPS = Concurrent User ÷ Target Response Time
```

## 5.2 36,000명 / 3초 기준 시나리오

| 시나리오 | 동시요청률 | 동시요청자 | TPS | 상태 |
|---|---:|---:|---:|---|
| Low | 3% | 1,080 | 360 | Reference |
| General Peak | 5% | 1,800 | 600 | `[WORKING]` |
| Design Peak | 10% | 3,600 | **1,200** | `[WORKING]` |
| Stress | 15% | 5,400 | **1,800** | `[WORKING]` |

현재 성능 Architecture의 핵심 Target은 다음과 같다.

```text
General Peak  = 600 TPS
Design Peak   = 1,200 TPS
Stress        = 1,800 TPS
Response SLA  = p95 <= 3 sec
```

---

# 6. VM 처리용량 — Versioned Baseline

## 6.1 기존 보수 Capacity

과거/기존 용량산정 문서는 정보계 업무 특성에 Core당 약 30~40 TPS를 적용하여 다음 운영 기준을 사용한다.

| VM | 보수 운영 TPS | 상태 |
|---|---:|---|
| 8Core / 32GB 또는 64GB 계열 | 250 TPS | `[LEGACY/CONSERVATIVE]` |
| 16Core / 64GB | 500 TPS | `[LEGACY/CONSERVATIVE]` |
| 16Core / 128GB | 500 TPS | `[LEGACY/CONSERVATIVE]` |
| 32Core / 256GB | 1,000 TPS | `[LEGACY/SPECIAL]` |

중요한 원칙:

> 16Core/128GB는 16Core/64GB보다 Memory가 크지만 CPU Core가 동일하므로 기존 기준에서는 TPS/Thread/Pool을 상향하지 않는다.

## 6.2 현재 Working Capacity

최근 성능 작업자료는 TPMC 환산 결과를 사용하여 다음 값을 Working Baseline으로 둔다.

| 항목 | 값 | 상태 |
|---|---:|---|
| VM | 16 vCPU / 64GB | `[WORKING]` |
| 산정 Capacity | 약 **855 TPS/VM** | `[WORKING]` |
| 운영 허용률 | 80% | `[WORKING]` |
| Operational Capacity | 약 **684 TPS/VM** | `[WORKING]` |

```text
855 × 0.80 = 684 TPS/VM
```

### 핵심 판정

```text
500 TPS/VM = 보수 Capacity Baseline
855 TPS/VM = TPMC 기반 Current Working Capacity
684 TPS/VM = 855의 80% Operational Working Capacity
최종 보장 TPS/VM = UNKNOWN / Load Test 후 승인
```

855를 성능 보장값으로 사용하면 안 된다.

---

# 7. VM 수량과 HA Capacity

## 7.1 500 TPS 보수 기준

| 목표 | 최소 VM | N+1 | 센터 단독수용 기준 자료 |
|---|---:|---:|---|
| 600 TPS | 2 | 3 | - |
| 1,200 TPS | 3 | 4 | 센터당 4대 |
| 1,800 TPS | 4 | 5 | 센터당 5대 |

기존 문서의 2센터 Active-Active 기준은 1센터 장애 시 나머지 센터가 전체 Peak 1,200 TPS를 감당하도록 **16Core 기준 센터당 4대, 전체 8대**를 권고한다.

## 7.2 855 / 684 Working 기준

Current Working 자료에는 센터당 2 VM, 총 4 VM 예시가 있다.

```text
센터 A = 2 VM
센터 B = 2 VM
VM Operational Capacity = 684 TPS
```

| 장애상태 | 잔존 VM | Working Capacity | 1,200 Peak 판정 |
|---|---:|---:|---|
| 정상 | 4 | 2,736 TPS | 수용 |
| 1 VM 장애 | 3 | 2,052 TPS | 수용 |
| 1센터 장애 | 2 | **1,368 TPS** | 수용 |
| 1센터 + 잔여센터 1 VM 장애 | 1 | 684 TPS | **미수용** |

### [INFERENCE] HA 해석

`2 VM + 2 VM`은 **단일 센터 장애 시 1,200 TPS를 수용**하지만, 센터 장애와 노드 장애가 겹치는 `Center Failure + N-1` 조건까지 요구하면 충분하지 않다.

운영허용 Capacity 684 TPS 기준으로:

```text
Peak 1,200 TPS
ceil(1,200 / 684) = 2 VM
+ N+1 = 3 VM / surviving center
```

따라서 `2+2`와 `3+3` 중 어떤 가용성 목표를 채택할지 ADR이 필요하다.

### G60 Decision Required

- `2+2`: Center Failure 대응 중심
- `3+3`: Center Failure 후 추가 N-1까지 고려한 후보
- 최종 수량: Runtime Load + Failure Test 후 승인

---

# 8. WAS Thread Architecture

## 8.1 Thread 산정 공식

용량산정 화면은 p95 3초를 Thread 수에 직접 곱하지 않고 **평균 Thread 점유시간**을 사용한다.

```text
Total WAS Thread
= TPS × Average Thread Hold Time × Thread Safety Factor

VM Thread
= Total WAS Thread ÷ AP Count

maxThreads
= VM Thread × 1.2~1.4
```

입력 후보:

- Average Thread Hold Time: 1.0 / 1.2 / 1.5 sec
- Safety: 20% / 30%

## 8.2 Versioned Thread Baseline

| VM 계열 | 값 | 상태 |
|---|---:|---|
| 8Core | 400~500 | `[LEGACY/REFERENCE]` |
| 16Core | 800~1,000 | `[CONFIRMED RANGE]` |
| 최신 Working 초기값 | **800** | `[WORKING]` |
| 시험상한 | **1,000** | `[WORKING TEST RANGE]` |
| 과거 화면 1,411/1,552 계열 | 과대 가능 | `[DEPRECATED/REVIEW]` |

Current Working 설정 후보:

| Tomcat 항목 | Working | 상태 |
|---|---:|---|
| maxThreads | 800 initial | `[WORKING]` |
| 시험범위 | 800~1,000 | `[WORKING]` |
| minSpareThreads | 150~200 / 최신 200 후보 | `[WORKING RANGE]` |
| acceptCount | 500~800 / 최신 800 후보 | `[WORKING RANGE]` |
| maxConnections | 16,000~20,000 자료 존재 | `[VERSIONED]` |
| keepAliveTimeout | 5 sec | `[WORKING]` |

### Runtime 승인 조건

maxThreads는 단순 CPU 배수로 승인하지 않는다.

```text
Busy Thread
Queue / acceptCount
CPU
GC Pause
Hikari Pending
DB Session
p95/p99
Error / Timeout
```

을 함께 측정해야 한다.

운영 목표는 Busy Thread **70% 이하**를 주요 임계값으로 사용한다.

---

# 9. JVM / GC Architecture

## 9.1 16Core 기준 Version

| 구분 | General AP | SingleView | 상태 |
|---|---:|---:|---|
| 기존 16C/64G | 24GB | 28GB | `[LEGACY/REFERENCE]` |
| 최신 Working | **24GB 초기** | **28GB** | `[WORKING]` |
| 화면상 일반 `-Xms/-Xmx 28g` | 28GB | - | `[CANDIDATE]` |
| 16C/128G 메모리 여유형 | 32GB 이내 | 40GB 이내 | `[REFERENCE]` |

Current Working JVM 원칙:

```text
GC = G1GC
MaxGCPauseMillis = 200ms
Heap Usage Target <= 70%
-Xss candidate = 512k
OOM Heap Dump = enabled
GC Log = required
```

### Architecture Rule

> Memory 증설은 TPS 자동 증가 근거가 아니다.

16Core/128GB의 추가 Memory는 Heap/Native/Cache/분석성 응답조립/운영여유로 해석하며 CPU가 동일하면 TPS를 별도로 검증한다.

---

# 10. HikariCP / DB Pool Architecture

## 10.1 Pool 산정 원칙

DB Pool은 `Tomcat maxThreads`와 같은 크기로 맞추지 않는다.

```text
DB Pool
≈ TPS
 × DB Connection Hold Time(sec)
 × DB Usage Ratio
 × Safety Factor
```

또한:

```text
DB Pool <= 실제 동시에 DB Connection을 점유하는 요청량
DB Pool 총량 = AP 수 × VM당 maximumPoolSize
```

으로 DB Session 총량을 반드시 검증한다.

## 10.2 Versioned Hikari Baseline

| 구분 | General AP | SingleView | 상태 |
|---|---:|---:|---|
| 기존 16Core | 80~100 | 100~120 | `[LEGACY/REFERENCE]` |
| Current Working Range | **120~150** | **150~180** | `[WORKING/VALIDATION]` |
| 최신 화면 상한 후보 | 150 | 180 | `[CANDIDATE]` |

공통 후보:

| 항목 | 값 | 상태 |
|---|---:|---|
| Hikari connectionTimeout | 3 sec | `[WORKING]` |
| 정상 Pool Usage | <70% 권장 / 최대 70~80% 관리 | `[WORKING]` |
| Pending Thread | 지속 발생 금지 | `[RULE]` |

### G60 판정

`150/180`은 현재 **검증값**이지 최종 승인값이 아니다.

최종 Pool은 다음 Runtime Evidence가 있어야 한다.

```text
Transaction별 DB Connection Hold Time
SQL Elapsed Distribution
DB 사용 거래 비율
Hikari Active/Idle/Pending
DB Session Limit
AP 전체 Pool 합계
N-1 / 센터 장애 시 Pool 합계
```

---

# 11. SQL / Transaction / Request Timeout Chain

## 11.1 Working Chain

```text
DB Query Timeout       2~3 sec
        <
Spring TX Timeout      4~5 sec
        <
Client/Web Request     6~8 sec
```

일부 최신 TCF 자료에서는 서비스별 Deadline과 3~5초 중심 값을 사용하므로 실제 ServiceId별 정책은 G40/G60 연계 대상이다.

## 11.2 구분해야 하는 timeout

| Timeout | 의미 |
|---|---|
| Hikari connectionTimeout | Pool에서 Connection 획득 대기 |
| MyBatis/Statement Query Timeout | DB SQL 수행 한도 |
| Spring Transaction Timeout | DB Transaction 수행 한도 |
| TCF/Online Timeout | 거래 전체 Deadline |
| External Connect/Read | 외부연계 접속/응답 한도 |
| Tomcat connectionTimeout | Connector socket/request wait 성격 |
| Client Request Timeout | 사용자/단말 응답대기 |
| L4 Idle Timeout | Network idle connection 관리 |
| L4 Sticky Timeout | Session affinity 유지 |

`L4 Idle Timeout`을 Query/TX/Client의 단순 숫자 체인으로 보지 않는다.

## 11.3 L4 Idle Timeout Drift

자료에는 다음 두 계열이 공존한다.

| 값 | 상태 |
|---|---|
| 10~15초 이상 | 기존 90분 기준 문서 |
| 70~90초 | 다른 기준 문서 |

따라서 G60에서는 **OPEN**으로 유지한다. 실제 L4 제품 정책, KeepAlive, Client Connection 동작 증적을 기준으로 G70에서 확정한다.

---

# 12. Runtime Operating Threshold

현재 Working 임계값:

| 영역 | Metric | Working Threshold |
|---|---|---:|
| OS | CPU | <=70% 평균 목표 |
| JVM | Heap Used | <=70% |
| JVM | GC Pause | <=200ms 목표 |
| Tomcat | Busy Thread | <=70% |
| Hikari | Pool Usage | <70%, 70~80% 주의 |
| DB | SQL Time | 정상 업무 100~300ms 목표 자료 존재 |
| SLA | p95 | <=3 sec |
| Error | Error Rate | Runtime 시험에서 기준 확정 필요 |
| Timeout | Timeout Rate | Runtime 시험에서 기준 확정 필요 |

Error Rate와 Timeout Rate의 공식 허용치는 현재 Evidence에서 확정할 수 없어 `UNKNOWN`으로 유지한다.

---

# 13. Runtime Test Architecture

## 13.1 필수 Source-supported Scenario

```text
1. General Peak    600 TPS
2. Design Peak   1,200 TPS
3. Stress        1,800 TPS
4. AP Node N-1
5. Center Failure
6. DB Connection / Session
7. Network
8. Storage IOPS
```

## 13.2 G60에서 추가로 필요한 Runtime Verification

| Test ID | 테스트 | 판정 목적 | 상태 |
|---|---|---|---|
| PERF-001 | 600 TPS Load | 일반 운영 안정성 | REQUIRED |
| PERF-002 | 1,200 TPS Peak | Peak SLA | REQUIRED |
| PERF-003 | 1,800 TPS Stress | 포화점/열화 | REQUIRED |
| PERF-004 | 1 VM N-1 | 무중단/잔여용량 | REQUIRED |
| PERF-005 | Center Failure | 잔여센터 1,200 TPS | REQUIRED |
| PERF-006 | Center Failure + Node N-1 | 2+2 vs 3+3 결정 | `[PROPOSAL/P0]` |
| PERF-007 | Hikari Exhaustion | DB Pool backpressure | REQUIRED |
| PERF-008 | Slow SQL | Query→TX Timeout 정합성 | REQUIRED |
| PERF-009 | Online Timeout | Late Commit/Connection 반환 | REQUIRED |
| PERF-010 | Thread Saturation | queue/acceptCount/Busy | REQUIRED |
| PERF-011 | Session Failover | DeltaManager/메모리 영향 | REQUIRED if adopted |
| PERF-012 | Rolling Deploy Capacity | 배포 중 잔여 TPS | REQUIRED |

---

# 14. Runtime Evidence Minimum Set

시험 1건마다 최소 다음을 수집한다.

```text
Test Run ID
Environment / Build / Config Version
Scenario / Target TPS
Actual TPS
p50 / p95 / p99
CPU / Load
Heap / GC Pause / Allocation
Tomcat Current/Busy/Max Thread
Queue / acceptCount
Hikari Active/Idle/Pending/Max
Connection Acquire Time
DB Session
SQL p95 / Slow SQL
Transaction Time
External Call Time
Error Rate
Timeout Rate
Node/Center State
Thread Dump when threshold exceeded
Heap Dump when required
Pass/Fail
```

### Evidence Key

```text
Run ID
+ ServiceId
+ GUID
+ Hostname
+ Tomcat JVM Instance
+ Config Version
```

으로 G80 Closed Loop와 연결할 수 있어야 한다.

---

# 15. Versioned Performance Baseline 표

| Metric | Legacy/Reference | Current Working | Runtime Approved | 상태 |
|---|---|---|---|---|
| Session Idle | 60m | **90m** | UNKNOWN | OPEN |
| Peak TPS | 1,200 | **1,200** | UNKNOWN | CONSISTENT |
| Stress TPS | 1,800 | **1,800** | UNKNOWN | CONSISTENT |
| 16Core TPS | 500 | **855 calculated** | UNKNOWN | CONFLICT/TEST |
| 16Core Operational TPS | 500 conservative | **684 @80%** | UNKNOWN | WORKING |
| JVM General Heap | 24GB | **24GB initial** | UNKNOWN | WORKING |
| JVM SingleView Heap | 28GB | **28GB** | UNKNOWN | WORKING |
| Tomcat maxThreads | 800~1,000 | **800 initial / 1,000 test max** | UNKNOWN | WORKING |
| Hikari General | 80~100 | **120~150** | UNKNOWN | TEST |
| Hikari SV | 100~120 | **150~180** | UNKNOWN | TEST |
| Query Timeout | 2~3s | **2~3s** | UNKNOWN | WORKING |
| TX Timeout | 4~5s | **4~5s** | UNKNOWN | WORKING |
| Client Timeout | 6~8s | **6~8s** | UNKNOWN | WORKING |
| L4 Idle | 10~15s / 70~90s | OPEN | UNKNOWN | CONFLICT |
| Center Topology | 4/center @500 reference | **2/center @684 example** | UNKNOWN | ADR |

---

# 16. G60 Critical GAP / Decision

| ID | 항목 | 문제 | 우선순위 |
|---|---|---|---:|
| G60-C01 | VM Capacity | 500 vs 855 최종 승인값 없음 | P0 |
| G60-C02 | Session Policy | 60m vs 90m | P0 |
| G60-C03 | Tomcat Thread | 800~1,000 최종값 Runtime 미승인 | P0 |
| G60-C04 | Hikari Pool | 80~120 vs 120~180 | P0 |
| G60-C05 | JVM Heap | 24/28 및 28 일반 후보 | P0 |
| G60-C06 | Timeout Chain | Query/TX/TCF/Client 전수 ServiceId 적용 | P0 |
| G60-C07 | Late Commit | Timeout 후 DB Commit/Connection 반환 | P0 |
| G60-C08 | HA Capacity | 2+2 vs 3+3 가용성 목표 | P0 |
| G60-C09 | Stress | 1,800 TPS 열화/복구 Evidence | P0 |
| G60-C10 | Session HA | DeltaManager 실제 객체크기/Failover | P0 |
| G60-C11 | Actual Config | server.xml/setenv/application 설정 증적 | P0 |
| G60-C12 | L4 Timeout | Idle 값 충돌과 Sticky/KeepAlive 정합성 | P1 |
| G60-C13 | Runtime Metrics | Error/Timeout 공식 허용치 | P1 |
| G60-C14 | DB Session | AP 전체 Pool 합계와 DB 상한 | P0 |

---

# 17. G60 Gate 판정

## 판정: **CONDITIONAL PASS**

### Pass 근거

- 사용자/동시요청/TPS Capacity Chain이 정립됨
- Peak 1,200 / Stress 1,800의 공통 목표가 여러 자료에서 일치
- Tomcat/Hikari/JVM/Timeout의 Working Range가 정의됨
- 보수 Capacity와 Current Working Capacity를 분리함
- N-1/센터장애 산정 규칙이 존재함
- Runtime Evidence 수집항목과 Test Matrix를 정의함

### PASS 불가 이유

현재 어떤 VM TPS/Thread/Pool/Heap도 **Runtime Approved** 상태가 아니다.

특히 다음이 없으므로 완전 PASS를 부여하지 않는다.

```text
1,200 TPS 실제 p95 <=3초 Evidence
1,800 TPS Stress Evidence
N-1 / Center Failure Evidence
Hikari Pending/DB Session Evidence
Timeout Late Commit Evidence
Final Config Snapshot
```

---

# 18. 다음 Gate 인계

G70에서는 G60의 성능값을 운영/HA/DR 관점과 결합한다.

```text
G60 Capacity / Runtime
      ↓
G70 Operations / HA-DR / Deployment
      ↓
Failover / Failback
RTO / RPO
OM Monitoring
Alert / Runbook
Rolling Deploy Capacity
DR Cutover
      ↓
G80 Closed Loop
```

