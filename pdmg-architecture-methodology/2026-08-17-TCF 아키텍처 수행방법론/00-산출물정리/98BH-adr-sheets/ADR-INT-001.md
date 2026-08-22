# ADR-INT-001 — Cross-Domain Integration Contract

> Priority: **P0**  
> Status: **PROPOSED**  
> Decision Readiness: **READY_FOR_HUMAN_DECISION**  
> Owner: Integration / Security  
> Required Approver Role: Integration Architect + Security Architect

## 권고안

Cross-Domain 연계는 공개 ServiceId + 표준전문 + HTTP/EAI 계약을 사용하고 상대 Domain DAO/Mapper/Table 직접 접근을 금지한다. S2S Auth와 Remaining Deadline을 계약에 포함한다.

## 대안

- A. Service contract via ServiceId (recommended)
- B. Direct code/DB coupling

## 근거

MG↔MK 규칙 및 tcf-eai 구조와 정합하며 장애/보안 경계를 유지한다.

## 결과 / Trade-off

- Route Registry, S2S Auth, deadline metadata가 필요하다.

## 승인 전 선행조건

- integration contract registry

## Runtime Evidence

- `RUN-TRACE`

## Closure Criteria

- route/auth/deadline evidence
- reverse trace available

## Human Decision

- [ ] APPROVE recommended decision
- [ ] REJECT / request alternative
- [ ] DEFER pending evidence/input

Decision: `____________________________`  
Approver: `____________________________`  
Decision Date: `____________________________`  
Condition / Exception: `____________________________`
