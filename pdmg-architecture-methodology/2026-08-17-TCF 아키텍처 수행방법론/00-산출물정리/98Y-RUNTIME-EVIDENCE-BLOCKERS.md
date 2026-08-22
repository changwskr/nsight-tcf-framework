# Wave 3 Runtime Evidence Blockers

## 1. Blocker Matrix

| Run | 현재 상태 | 필요한 실제 환경/증적 | 해소 Gate |
|---|---|---|---|
| RUN-TIMEOUT | BLOCKED_PRODUCTION_RUNTIME | Test ServiceId, Oracle before/after, TX Log, Hikari, Thread/Context | G40/G60/G80 |
| RUN-P600 | BLOCKED_PRODUCTION_RUNTIME | Load Generator, APM/JVM/Hikari, Run Log | G60/G80 |
| RUN-P1200 | BLOCKED_PRODUCTION_RUNTIME | 동일 + Design Peak 환경 | G60/G80 |
| RUN-S1800 | BLOCKED_PRODUCTION_RUNTIME | Stress 환경 + Data Integrity | G60/G80 |
| RUN-HIKARI | BLOCKED_PRODUCTION_RUNTIME | Pool Metric + DB Session | G60/G80 |
| RUN-SLOWSQL | BLOCKED_PRODUCTION_RUNTIME | Slow SQL Injection + TX/Pool | G60/G80 |
| RUN-N1 | BLOCKED_PRODUCTION_RUNTIME | L4/Apache + WAS node kill/recovery | G60/G70/G80 |
| RUN-SESSION | BLOCKED_PRODUCTION_RUNTIME_HUMAN | Session ADR + L4/IdP/Session logs | G70/G80/HG90 |
| RUN-CF | BLOCKED_PRODUCTION_RUNTIME_HUMAN | DR topology + RTO/RPO + GSLB/L4/DB | G70/G80/HG90 |
| RUN-TRACE | BLOCKED_PRODUCTION_RUNTIME | Apache→JVM→TCF→SQL/external logs | G70/G80 |
| RUN-ROLLING | BLOCKED_PRODUCTION_RUNTIME | CI/CD + drain/deploy/rejoin + residual capacity | G70/G80 |
| RUN-JWT-ROTATE | BLOCKED_BUILD_KMS_RUNTIME | canonical build + KMS/HSM + multi-node/restart | G50/G70/G80/HG90 |

## 2. 현재 환경에서 증명할 수 없는 것

- 500 TPS와 855 TPS 중 어느 값이 Runtime Approved인지
- 600/1,200/1,800 TPS에서 실제 p95/오류율/Timeout율
- Center Failure 후 1,200 TPS 잔여용량
- DeltaManager/JDBC Session Failover 결과
- Timeout 후 Oracle Late Commit 0 여부
- Hikari Connection 실제 반환
- GUID+ServiceId 실제 End-to-End Trace
- Rolling Deployment 중 Residual Capacity
- KMS/HSM 기반 JWT Rotation

이 값은 문서/정적분석/합성시험으로 대신할 수 없다.
