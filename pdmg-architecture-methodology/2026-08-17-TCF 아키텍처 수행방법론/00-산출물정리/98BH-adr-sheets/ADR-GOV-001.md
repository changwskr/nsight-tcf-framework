# ADR-GOV-001 — Architecture Model Source of Truth

> Priority: **P0**  
> Status: **PROPOSED**  
> Decision Readiness: **READY_FOR_HUMAN_DECISION**  
> Owner: Enterprise/Application Architecture  
> Required Approver Role: Architecture Board

## 권고안

Machine-readable JSON Model + JSON Schema + Validator + Baseline Manifest를 Architecture SoT로 사용하고 Markdown은 Human-readable View로 관리한다.

## 대안

- A. Machine-readable SoT + Markdown view (recommended)
- B. Markdown-only

## 근거

현재 Model Schema/Validator가 정적 PASS했으며 drift 자동검증을 위해 machine-readable 기준이 필요하다.

## 결과 / Trade-off

- Requirement/Screen/Table/Server/Runtime Evidence edge 확장이 필요하다.

## 승인 전 선행조건

- model ownership/change process

## Runtime Evidence

- Runtime Run 직접 의존 없음

## Closure Criteria

- full critical trace edges complete
- manifest/signoff integrated

## Human Decision

- [ ] APPROVE recommended decision
- [ ] REJECT / request alternative
- [ ] DEFER pending evidence/input

Decision: `____________________________`  
Approver: `____________________________`  
Decision Date: `____________________________`  
Condition / Exception: `____________________________`
