# ADR-PERF-001 — Runtime Approved VM Capacity

> Priority: **P0**  
> Status: **DRAFT**  
> Decision Readiness: **RUNTIME_DEPENDENT**  
> Owner: Performance / Infra  
> Required Approver Role: Performance Lead + Infra Architect + DBA

## 권고안

500 TPS는 Legacy/Conservative, 855 TPS는 Working으로 유지하고 실제 승인값은 P600/P1200/S1800/N1/Hikari 결과로 확정한다.

## 대안

- A. Runtime-measured capacity (recommended)
- B. 500 fixed
- C. 855 fixed

## 근거

서로 다른 산정 문서의 숫자를 시험 없이 하나로 합치면 HA/DB/Thread 설계가 왜곡된다.

## 결과 / Trade-off

- VM 수량, thread, Hikari, N-1 여유가 최종값에 따라 변경된다.

## 승인 전 선행조건

- performance environment identity

## Runtime Evidence

- `RUN-P600`
- `RUN-P1200`
- `RUN-S1800`
- `RUN-HIKARI`
- `RUN-N1`

## Closure Criteria

- runtime capacity approved
- config baseline frozen

## Human Decision

- [ ] APPROVE recommended decision
- [ ] REJECT / request alternative
- [ ] DEFER pending evidence/input

Decision: `____________________________`  
Approver: `____________________________`  
Decision Date: `____________________________`  
Condition / Exception: `____________________________`
