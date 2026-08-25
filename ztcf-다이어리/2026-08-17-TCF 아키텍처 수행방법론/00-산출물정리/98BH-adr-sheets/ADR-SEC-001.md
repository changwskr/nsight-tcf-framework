# ADR-SEC-001 — JWT Signing Key Source of Truth

> Priority: **P0**  
> Status: **PROPOSED**  
> Decision Readiness: **READY_FOR_HUMAN_DECISION**  
> Owner: Security / Platform  
> Required Approver Role: Security Architect + Platform Owner

## 권고안

Production signing key의 Source of Truth는 KMS/HSM 또는 승인된 중앙 Key Store로 한다. Process-local RSA 생성은 DEV/LOCAL에만 허용한다.

## 대안

- A. KMS/HSM centralized signer (recommended)
- B. Exportable central keystore
- C. Process-local ephemeral RSA (DEV only)

## 근거

다중 Issuer, 재기동, 감사, 폐기, 회전 시 동일하고 추적 가능한 Key SoT가 필요하다.

## 결과 / Trade-off

- Private key export를 최소화한다.
- Key provider adapter와 운영 권한/감사 정책이 필요하다.

## 승인 전 선행조건

- 승인된 Key Platform/KMS-HSM 규격
- Canonical build integration

## Runtime Evidence

- `RUN-JWT-ROTATE`

## Closure Criteria

- KMS/HSM adapter integrated
- multi-node/restart/rotation evidence

## Human Decision

- [ ] APPROVE recommended decision
- [ ] REJECT / request alternative
- [ ] DEFER pending evidence/input

Decision: `____________________________`  
Approver: `____________________________`  
Decision Date: `____________________________`  
Condition / Exception: `____________________________`
