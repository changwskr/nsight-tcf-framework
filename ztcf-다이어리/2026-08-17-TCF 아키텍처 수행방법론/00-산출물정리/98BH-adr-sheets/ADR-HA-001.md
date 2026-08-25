# ADR-HA-001 — AP HA Topology

> Priority: **P0**  
> Status: **DRAFT**  
> Decision Readiness: **RUNTIME_DEPENDENT**  
> Owner: Infra / Performance  
> Required Approver Role: Infra Architect + Performance Lead + Ops Owner

## 권고안

2+2, 3+3, 8Core Scale-Out 중 최종 토폴로지는 Runtime Approved Capacity와 N-1/Center Failure 결과 후 결정한다.

## 대안

- A. 2+2
- B. 3+3
- C. 8Core scale-out

## 근거

500/855 TPS 기준이 확정되지 않아 잔여용량을 설계값만으로 최종 승인할 수 없다.

## 결과 / Trade-off

- 서버수/비용/장애단위/rolling 여유가 달라진다.

## 승인 전 선행조건

- ADR-PERF-001 runtime value

## Runtime Evidence

- `RUN-P1200`
- `RUN-N1`
- `RUN-CF`
- `RUN-ROLLING`

## Closure Criteria

- residual capacity approved
- center failure and rolling evidence

## Human Decision

- [ ] APPROVE recommended decision
- [ ] REJECT / request alternative
- [ ] DEFER pending evidence/input

Decision: `____________________________`  
Approver: `____________________________`  
Decision Date: `____________________________`  
Condition / Exception: `____________________________`
