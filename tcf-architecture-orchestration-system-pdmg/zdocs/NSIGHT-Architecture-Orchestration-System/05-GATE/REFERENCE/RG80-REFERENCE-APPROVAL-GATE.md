---
document-status: CONFIRMED
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# RG80 — Reference Approval Gate


## 판정 구조

`Evaluator → Measured Value → Threshold → Result`

## Hard Conditions

필수 Approval의 role/hash/expiry가 유효해야 한다.

## 결과

`PASS / CONDITIONAL PASS / HOLD / REJECT`

수동 `decision=PASS`로 evaluator를 우회하지 않는다.
