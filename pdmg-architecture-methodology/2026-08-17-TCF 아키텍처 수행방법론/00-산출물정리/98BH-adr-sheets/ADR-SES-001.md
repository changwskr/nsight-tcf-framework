# ADR-SES-001 — Session Strategy

> Priority: **P0**  
> Status: **DRAFT**  
> Decision Readiness: **NEEDS_OWNER_INPUT_AND_RUNTIME**  
> Owner: Architecture / Ops  
> Required Approver Role: Application Architect + Ops Owner + Security

## 권고안

현재 자료만으로 최종안을 확정하지 않는다. 센터내 Sticky+DeltaManager와 외부 Session Store를 동일 기준으로 비교하고, 센터장애 시 재로그인 허용정책을 먼저 승인한다.

## 대안

- A. Sticky + DeltaManager (same-center HA)
- B. Spring Session JDBC/external store
- C. Re-login centric continuity

## 근거

60/90분 세션, 복제비용, 센터 전환정책이 혼재하며 failover 증적이 없다.

## 결과 / Trade-off

- Session object serialization/메모리/DB 부하 영향이 달라진다.
- 센터 장애 UX가 정책에 따라 달라진다.

## 승인 전 선행조건

- Session idle 정책 60/90분 승인
- 센터장애 재로그인 허용정책

## Runtime Evidence

- `RUN-SESSION`
- `RUN-CF`

## Closure Criteria

- approved session policy
- failover behavior matches ADR

## Human Decision

- [ ] APPROVE recommended decision
- [ ] REJECT / request alternative
- [ ] DEFER pending evidence/input

Decision: `____________________________`  
Approver: `____________________________`  
Decision Date: `____________________________`  
Condition / Exception: `____________________________`
