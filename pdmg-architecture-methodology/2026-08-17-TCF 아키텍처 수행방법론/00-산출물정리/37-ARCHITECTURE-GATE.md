# NSIGHT Architecture Gate Status

| Gate | 상태 | 핵심 결과 | 다음 조건 |
|---|---|---|---|
| G00 Source Baseline | CONDITIONAL PASS | Source Scope/Canonical Rule 확정 | Branch/Commit, Root TCF Build, 운영 Config는 UNKNOWN 관리 |
| G10 Vision/NFR | PASS | Vision/5대 NFR/FAST-DEEP 원칙 확정 | Runtime 달성 검증은 후속 Gate |
| G20 Big Picture/Logical | NEXT | 미실행 | Domain/책임/연계금지/Data Flow 기준화 |
| G30 Physical | WAIT | 서버/미들웨어 자료 존재 | App/JVM/Server Mapping |
| G40 Mechanism | WAIT | TCF/PDMG Evidence 풍부 | AS-IS/TO-BE 분리 |
| G50 Security/Data | WAIT | 설계자료 존재 | Key/Data Contract 검증 |
| G60 Capacity/Runtime | WAIT | Working Baseline 존재 | Load Test/Config Evidence 필요 |
| G70 Operations/HA-DR | WAIT | 부분 자료 존재 | Failover/Failback/Residual Capacity |
| G80 Closed Loop | WAIT | Model/Rules 부분 구현 | Runtime Evidence Gate |
| HG90 Human Gate | WAIT | - | Critical ADR/GAP 종료 |

현재 진행 위치:

```text
G00  CONDITIONAL PASS
  ↓
G10  PASS
  ↓
G20  NEXT
```
