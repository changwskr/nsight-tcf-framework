# NSIGHT Architecture Execution Roadmap — G80

## NOW — G80 HOLD 해소

1. JWT KMS/HSM Key SoT 및 Rotation ADR
2. Transaction/Timeout Runtime Fault Test
3. 71 Server→JVM→WAR 및 Routing 실제 Config 매핑
4. Session/HA Topology/RTO-RPO ADR
5. Architecture Model JSON Schema 및 Validator

## BEFORE PERFORMANCE TEST

- P600/P1200/S1800, N-1, Hikari/SlowSQL/Timeout Test
- VM Capacity, Tomcat, JVM, Hikari를 Runtime Approved Baseline으로 승격
- Error/Timeout 합격 임계치 승인

## BEFORE OPEN

- Center Failover/Failback, Session Failover, Rolling Deploy
- GUID+ServiceId E2E Trace
- CI/CD Rollback/DB/Config Compatibility
- Migration Go/No-Go/Reconciliation
- OM Alert→Runbook→Evidence

## HG90

다음이 충족된 경우에만 Human Gate에 `PASS` 후보로 제출한다.

```text
P0 FAIL_TARGET = 0
P0 OPEN_RUNTIME = 승인된 Exception 제외 0
Critical ADR 승인
Model Schema PASS
Source/Config/Test/Runtime Evidence Manifest 완성
Drift/GAP Owner/Due 지정
```

## POST OPEN

- 주기적 Source/Config/Runtime Scan
- ServiceId/OM/Inventory/Model Drift 자동탐지
- 승인된 Runtime 값을 New Baseline으로 승격
