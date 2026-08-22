---
document-status: CONFIRMED
system-scope: TARGET_PROJECT
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# G50 — Target Runtime Gate


## 판정 구조

`Evaluator → Measured Value → Threshold → Result`

## Hard Conditions

Mission이 Runtime을 요구하면 Runtime Evidence 없이는 PASS 금지.

## 결과

`PASS / CONDITIONAL PASS / HOLD / REJECT`

수동 `decision=PASS`로 evaluator를 우회하지 않는다.
