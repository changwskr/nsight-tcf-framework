# ADR-SEC-002 — kid / Rotation / JWKS Grace Lifecycle

> Priority: **P0**  
> Status: **PROPOSED**  
> Decision Readiness: **READY_FOR_HUMAN_DECISION**  
> Owner: Security / Platform  
> Required Approver Role: Security Architect + Platform Owner

## 권고안

Versioned kid를 사용하고 JWKS는 Active + Previous public key를 grace 기간 동안 동시 제공한다.

## 대안

- A. Active+Previous grace (recommended)
- B. Immediate cutover without grace

## 근거

회전 직후 기존 Access Token의 정상 검증과 운영 롤백 가능성을 보장해야 한다.

## 결과 / Trade-off

- Grace 기간과 retirement 정책을 명시해야 한다.
- Previous private key를 신규 signing에 사용하지 않는다.

## 승인 전 선행조건

- ADR-SEC-001
- Access token 최대수명/운영여유 입력

## Runtime Evidence

- `RUN-JWT-ROTATE`

## Closure Criteria

- rotation grace observed
- restart does not create unplanned key

## Human Decision

- [ ] APPROVE recommended decision
- [ ] REJECT / request alternative
- [ ] DEFER pending evidence/input

Decision: `____________________________`  
Approver: `____________________________`  
Decision Date: `____________________________`  
Condition / Exception: `____________________________`
