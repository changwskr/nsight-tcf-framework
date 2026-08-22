---
document-status: CONFIRMED
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# RG60 — Reference Drift Gate


## 판정 구조

`Evaluator → Measured Value → Threshold → Result`

## Hard Conditions

Internal Drift의 expected/actual/evidence/severity가 기록되어야 한다.

## 결과

`PASS / CONDITIONAL PASS / HOLD / REJECT`

수동 `decision=PASS`로 evaluator를 우회하지 않는다.
