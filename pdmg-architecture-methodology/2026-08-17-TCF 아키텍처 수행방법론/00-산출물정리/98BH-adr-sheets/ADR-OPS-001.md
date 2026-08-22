# ADR-OPS-001 — OM Control Plane Scope

> Priority: **P0**  
> Status: **PROPOSED**  
> Decision Readiness: **READY_FOR_HUMAN_DECISION**  
> Owner: Ops / Architecture  
> Required Approver Role: Ops Owner + Architecture

## 권고안

OM은 Control Plane으로 Catalog/Policy/Runtime/Deploy/Audit 관점을 제공하고 Runtime Plane의 TCF/STF가 정책을 집행한다.

## 대안

- A. Separate control/runtime responsibilities (recommended)
- B. Embedded ad-hoc operations

## 근거

운영통제 변경과 거래실행 책임을 분리해야 감사/복구가 가능하다.

## 결과 / Trade-off

- OM Runtime Catalog와 policy audit가 필요하다.

## 승인 전 선행조건

- OM catalog

## Runtime Evidence

- `RUN-TRACE`
- `RUN-ROLLING`

## Closure Criteria

- catalog/metric/alert/runbook loop connected

## Human Decision

- [ ] APPROVE recommended decision
- [ ] REJECT / request alternative
- [ ] DEFER pending evidence/input

Decision: `____________________________`  
Approver: `____________________________`  
Decision Date: `____________________________`  
Condition / Exception: `____________________________`
