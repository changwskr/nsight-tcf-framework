# ADR-DEP-001 — Production Deployment Pipeline

> Priority: **P0**  
> Status: **PROPOSED**  
> Decision Readiness: **READY_FOR_HUMAN_DECISION**  
> Owner: DevOps / Ops  
> Required Approver Role: DevOps Owner + Ops Change Manager

## 권고안

GitLab→Runner→Artifact Repository→eCAMS/승인→Production의 재현가능한 Pipeline을 기준으로 하고 Rolling/rollback evidence를 의무화한다.

## 대안

- A. Controlled pipeline with artifact/config trace (recommended)
- B. manual production deployment

## 근거

형상/설정/DB 변경 이력과 rollback 재현성이 필요하다.

## 결과 / Trade-off

- artifact hash, config version, DB compatibility evidence가 필요하다.

## 승인 전 선행조건

- actual pipeline evidence

## Runtime Evidence

- `RUN-ROLLING`

## Closure Criteria

- rolling/rollback pass
- artifact/config/db version trace complete

## Human Decision

- [ ] APPROVE recommended decision
- [ ] REJECT / request alternative
- [ ] DEFER pending evidence/input

Decision: `____________________________`  
Approver: `____________________________`  
Decision Date: `____________________________`  
Condition / Exception: `____________________________`
