# ADR-TMO-001 — Timeout Execution / Cancellation Model

> Priority: **P0**  
> Status: **PROPOSED**  
> Decision Readiness: **READY_FOR_HUMAN_DECISION**  
> Owner: Framework / DB  
> Required Approver Role: Framework Owner + DBA + Application Architect

## 권고안

Online Timeout은 Deadline/응답통제 Owner로 두고 Thread.interrupt를 DB rollback 보장수단으로 간주하지 않는다. DB Query < TX < Online < Client 순서를 강제한다.

## 대안

- A. Strict layered deadline (recommended)
- B. Equal TX/Online timeout
- C. Client-only timeout

## 근거

현재 기본 3/5/5는 TX와 Online 경계가 동일하여 race window가 존재한다.

## 결과 / Trade-off

- ServiceId별 timeout metadata가 필요하다.
- Fault injection으로 late commit/connection return을 증명해야 한다.

## 승인 전 선행조건

- Timeout policy approval
- controlled slow SQL/fault fixture

## Runtime Evidence

- `RUN-TIMEOUT`
- `RUN-SLOWSQL`

## Closure Criteria

- late_commit=0
- connection_return=true
- context leak=0

## Human Decision

- [ ] APPROVE recommended decision
- [ ] REJECT / request alternative
- [ ] DEFER pending evidence/input

Decision: `____________________________`  
Approver: `____________________________`  
Decision Date: `____________________________`  
Condition / Exception: `____________________________`
