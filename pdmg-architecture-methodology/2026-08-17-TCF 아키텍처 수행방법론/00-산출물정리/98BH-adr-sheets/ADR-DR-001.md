# ADR-DR-001 — Center Failure Session/Data Continuity

> Priority: **P0**  
> Status: **DRAFT**  
> Decision Readiness: **NEEDS_OWNER_INPUT_AND_RUNTIME**  
> Owner: Ops / Business / Architecture  
> Required Approver Role: Business Owner + Ops Owner + Architecture

## 권고안

센터 장애 시 세션 유지 여부와 재로그인 허용정책을 업무정책으로 먼저 결정하고, 데이터 일관성/복구 방식과 분리해 승인한다.

## 대안

- A. Session preserved
- B. Re-login after center failover
- C. Service-class differentiated

## 근거

현재 자료에는 same-center 복제 후보와 center-failure 재로그인 후보가 공존한다.

## 결과 / Trade-off

- 사용자 경험, 복제비용, 장애 복잡도가 달라진다.

## 승인 전 선행조건

- ADR-SES-001
- service continuity policy

## Runtime Evidence

- `RUN-SESSION`
- `RUN-CF`

## Closure Criteria

- observed behavior matches policy
- data consistency approved

## Human Decision

- [ ] APPROVE recommended decision
- [ ] REJECT / request alternative
- [ ] DEFER pending evidence/input

Decision: `____________________________`  
Approver: `____________________________`  
Decision Date: `____________________________`  
Condition / Exception: `____________________________`
