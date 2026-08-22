# Runtime Evidence Submission Checklist

> 12개 Run은 Runbook 준비완료 상태이며 Production PASS는 아직 0/12이다.

| Run | Purpose | 필수 제출 | 현재 |
|---|---|---|---|
| RUN-TIMEOUT | Timeout rollback / late commit / pool & context cleanup | db/before-after.json, logs/transaction.json, metrics/pool.json, metrics/thread.json | OPEN |
| RUN-P600 | General peak 600 TPS | metrics/summary.json, logs/run.log | OPEN |
| RUN-P1200 | Design peak 1,200 TPS | metrics/summary.json, logs/run.log | OPEN |
| RUN-S1800 | Stress 1,800 TPS and saturation characterization | metrics/summary.json, logs/run.log, db/integrity.json | OPEN |
| RUN-HIKARI | Hikari pool pressure and DB session ceiling | metrics/pool.json, metrics/db-session.json | OPEN |
| RUN-SLOWSQL | Slow SQL query timeout < TX timeout and connection return | metrics/slow-sql.json, logs/transaction.json, metrics/pool.json | OPEN |
| RUN-N1 | AP N-1 at design peak | metrics/summary.json, logs/routing.log, logs/failover.log | OPEN |
| RUN-SESSION | Session failover/re-authentication policy | logs/session.log, logs/l4.log | OPEN |
| RUN-CF | Center failover/failback RTO/RPO | logs/failover.log, metrics/rto-rpo.json | OPEN |
| RUN-TRACE | GUID+ServiceId end-to-end traceability | logs/e2e-trace.json | OPEN |
| RUN-ROLLING | Rolling deployment residual capacity / health | logs/deploy.log, logs/health.log, metrics/summary.json | OPEN |
| RUN-JWT-ROTATE | JWT active/previous kid grace, multi-node/restart verification | logs/jwt-rotation.json, logs/jwks.json, logs/key-audit.log | OPEN |

## Runtime 승인 최소 계약

```text
status = PASS
evidence_class = PRODUCTION_RUNTIME
runtime_approved = true
synthetic = false
```

위 조건 중 하나라도 충족하지 않으면 G80 Runtime Blocker는 닫히지 않는다.
