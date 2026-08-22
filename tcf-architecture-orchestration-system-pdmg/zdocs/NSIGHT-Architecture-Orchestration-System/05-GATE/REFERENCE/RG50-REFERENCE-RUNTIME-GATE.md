---
document-status: CONFIRMED
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# RG50 — Reference Runtime Gate


## 판정 구조

`Evaluator → Measured Value → Threshold → Result`

## Hard Conditions

실제 Runtime Evidence가 없으면 HOLD.

## 결과

`PASS / CONDITIONAL PASS / HOLD / REJECT`

수동 `decision=PASS`로 evaluator를 우회하지 않는다.
