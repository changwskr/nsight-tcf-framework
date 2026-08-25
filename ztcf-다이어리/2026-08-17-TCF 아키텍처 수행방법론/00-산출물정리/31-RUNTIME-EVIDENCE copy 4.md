# NSIGHT Runtime Evidence Registry — G80

## 1. 상태

**전체 상태: OPEN / G80 BLOCKING**

G60에서 정의한 Evidence Schema를 G80 Registry로 승격했으나, 실제 Load/Failover/Timeout/Trace 실행 원본은 현재 Evidence Set에 충분히 연결되지 않았다. Runtime 성질을 주장하는 Rule은 이 상태에서 PASS하지 않는다.

## 2. Evidence Identity

```text
RunId + Timestamp + Environment + Build/Commit + Config Version
+ ServiceId + GUID + Hostname + Tomcat JVM Instance
```

## 3. Mandatory Run Registry

| Run ID | Scenario | Target | 상태 | 승격 대상 |
|---|---|---|---|---|
| RUN-P600 | General Peak | 600 TPS | OPEN | PERF-002/003 |
| RUN-P1200 | Design Peak | 1,200 TPS | OPEN | PERF-002/003 |
| RUN-S1800 | Stress | 1,800 TPS | OPEN | PERF-005 |
| RUN-N1 | AP N-1 | 1,200 TPS | OPEN | PERF-005/R4-008 |
| RUN-CF | Center Failure/Failback | 1,200 TPS | OPEN | R4-008 |
| RUN-HIKARI | Pool Pressure | Hold-Time/DB Session | OPEN | PERF-003 |
| RUN-SLOWSQL | Slow SQL | Query<TX Deadline | OPEN | PERF-004 |
| RUN-TIMEOUT | Timeout Fault Injection | Rollback/Connection Return | OPEN | R4-003 |
| RUN-SESSION | Session Failover | Survival/Memory | OPEN | R3-006/R4-008 |
| RUN-ROLLING | Rolling Deployment | Peak Residual Capacity | OPEN | PERF-006/R4-007 |
| RUN-TRACE | E2E Trace | GUID+ServiceId | OPEN | R4-001 |
| RUN-JWT-ROTATE | JWT Key Rotation | Old/New kid Grace | OPEN | R3-001/002 |

## 4. 필수 Metric

`TPS, p95/p99, errorRate, timeoutRate, CPU, Heap, GC, BusyThread, Hikari Active/Pending, DB Session, SQL elapsed, TX result, failover seconds`를 동일 RunId로 묶는다.

## 5. Gate Rule

```text
Document claim
  ↓
Rule
  ↓
Expected Config
  ↓
Test Run
  ↓
Runtime Metric / Log / Trace
  ↓
PASS/FAIL
  ↓
Drift/GAP/ADR
```

## 6. Wave 3 Harness 상태 — 2026-08-19

- Runtime Evidence Harness: **READY**
- Manifest JSON Schema: **PASS**
- Run Template: **12/12 생성**
- Harness pytest: **11/11 PASS**
- Java Timeout Policy Local Preflight: **PASS**
- Production Runtime Executed: **0/12**
- Production Runtime Approved: **0/12**

Synthetic/Reference evidence는 `SYNTHETIC_ONLY`/`REFERENCE_ONLY`로 강제 분류하며 Runtime PASS로 승격하지 않는다.


## 7. Wave 3C First Batch — 2026-08-19

첫 실제 실행군을 다음 순서로 고정한다.

```text
RUN-TIMEOUT → RUN-P600 → RUN-P1200
```

- Operator Runbook: READY
- Machine Go/No-Go Validator: pytest 9 PASS
- Runtime Identity: 미제공
- JMeter/Gatling/SQL*Plus: 현재 세션 미설치
- 실제 Production Runtime: **0/3**
- G80/HG90 상태: **HOLD**

Machine Hard Gate:

```text
RUN-TIMEOUT : rollback + late commit 0 + pool/thread/context cleanup
RUN-P600    : TPS>=600  + p95<=3s
RUN-P1200   : TPS>=1200 + p95<=3s
```

Error/Timeout Rate와 최종 Resource Ceiling은 Human/Open Gate로 유지한다.


## 8. Wave 3D Remaining Batch — 2026-08-19

Wave 3C 이후 남은 9개 Run에 대한 Operator Runbook과 Machine/Human Gate를 작성했다.

- Remaining Runbook: **9/9 OPERATOR READY**
- Total Runtime Runbook coverage: **12/12**
- Production Runtime Executed: **0/12**
- Production Runtime Approved: **0/12**
- `RUN-SESSION`: Session ADR 없이는 PASS 불가
- `RUN-CF`: 승인된 RTO/RPO 없이는 PASS 불가
- `RUN-JWT-ROTATE`: canonical Key Provider/KMS-HSM 통합 전 BLOCKED

Stress/Hikari/Error/Timeout에 대해 승인되지 않은 임계치를 새 Hard Gate로 만들지 않는다. 실제 결과가 들어오기 전 G80/HG90은 HOLD를 유지한다.
