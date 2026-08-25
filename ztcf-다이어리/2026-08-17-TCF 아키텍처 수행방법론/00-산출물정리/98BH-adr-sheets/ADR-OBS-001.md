# ADR-OBS-001 — GUID + ServiceId Runtime Evidence Standard

> Priority: **P0**  
> Status: **PROPOSED**  
> Decision Readiness: **READY_FOR_HUMAN_DECISION**  
> Owner: Observability / Application  
> Required Approver Role: Ops/Observability Owner + Application Architect

## 권고안

GUID + ServiceId를 System/Transaction/Business/Image/Error/Runtime Evidence의 공통 추적키로 사용한다.

## 대안

- A. GUID+ServiceId standard (recommended)
- B. component-local ids

## 근거

요청부터 SQL/외부연계까지 정·역방향 추적을 가능하게 한다.

## 결과 / Trade-off

- MDC/Context 전파와 마스킹 정책이 필요하다.

## 승인 전 선행조건

- logging field standard

## Runtime Evidence

- `RUN-TRACE`

## Closure Criteria

- same GUID/ServiceId across required hops
- reverse trace pass

## Human Decision

- [ ] APPROVE recommended decision
- [ ] REJECT / request alternative
- [ ] DEFER pending evidence/input

Decision: `____________________________`  
Approver: `____________________________`  
Decision Date: `____________________________`  
Condition / Exception: `____________________________`
