---
document-status: CONFIRMED
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# RG40 — Reference Self-Test Gate


## 판정 구조

`Evaluator → Measured Value → Threshold → Result`

## Hard Conditions

Critical Reference Rule의 evaluator 결과가 존재해야 한다.

## 결과

`PASS / CONDITIONAL PASS / HOLD / REJECT`

수동 `decision=PASS`로 evaluator를 우회하지 않는다.
