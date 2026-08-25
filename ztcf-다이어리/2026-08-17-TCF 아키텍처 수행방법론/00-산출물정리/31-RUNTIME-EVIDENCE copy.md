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

