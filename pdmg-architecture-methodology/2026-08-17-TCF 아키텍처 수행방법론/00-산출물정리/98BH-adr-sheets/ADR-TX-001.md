# ADR-TX-001 — NSIGHT Transaction Owner

> Priority: **P0**  
> Status: **PROPOSED**  
> Decision Readiness: **READY_FOR_HUMAN_DECISION**  
> Owner: Application / Framework  
> Required Approver Role: Application Architect + Framework Owner

## 권고안

표준 온라인 업무의 기본 Transaction Owner는 Facade/Use Case Boundary로 한다. Service TX는 명시적 예외만 허용한다.

## 대안

- A. Facade owner (recommended)
- B. Service owner
- C. Outer worker/transaction template owner

## 근거

Handler→Facade→Service 구조에서 업무 유스케이스 경계와 rollback/timeout 책임을 일치시킨다.

## 결과 / Trade-off

- EbUserService/EpUserEventService 중복 TX 정리 필요
- REQUIRES_NEW 등은 Exception Registry 필요

## 승인 전 선행조건

- Wave2A candidate canonical integration

## Runtime Evidence

- `RUN-TIMEOUT`
- `RUN-SLOWSQL`

## Closure Criteria

- canonical build pass
- rollback integration evidence

## Human Decision

- [ ] APPROVE recommended decision
- [ ] REJECT / request alternative
- [ ] DEFER pending evidence/input

Decision: `____________________________`  
Approver: `____________________________`  
Decision Date: `____________________________`  
Condition / Exception: `____________________________`
