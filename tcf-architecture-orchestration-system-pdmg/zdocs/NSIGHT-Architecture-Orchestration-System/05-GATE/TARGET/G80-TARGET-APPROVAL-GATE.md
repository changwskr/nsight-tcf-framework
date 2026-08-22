---
document-status: CONFIRMED
system-scope: TARGET_PROJECT
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# G80 — Target Approval Gate


## 판정 구조

`Evaluator → Measured Value → Threshold → Result`

## Hard Conditions

필수 Approval과 Artifact Hash가 일치해야 한다.

## 결과

`PASS / CONDITIONAL PASS / HOLD / REJECT`

수동 `decision=PASS`로 evaluator를 우회하지 않는다.
