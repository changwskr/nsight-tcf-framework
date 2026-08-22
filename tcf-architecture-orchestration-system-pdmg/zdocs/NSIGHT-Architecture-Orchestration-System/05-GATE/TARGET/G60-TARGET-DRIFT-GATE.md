---
document-status: CONFIRMED
system-scope: TARGET_PROJECT
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# G60 — Target Drift Gate


## 판정 구조

`Evaluator → Measured Value → Threshold → Result`

## Hard Conditions

Target Conformance Drift가 evidence/severity와 함께 분류되어야 한다.

## 결과

`PASS / CONDITIONAL PASS / HOLD / REJECT`

수동 `decision=PASS`로 evaluator를 우회하지 않는다.
