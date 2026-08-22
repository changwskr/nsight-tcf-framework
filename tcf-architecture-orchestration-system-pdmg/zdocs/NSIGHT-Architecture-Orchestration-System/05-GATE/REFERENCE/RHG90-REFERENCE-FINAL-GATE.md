---
document-status: CONFIRMED
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# RHG90 — Reference Final Gate


## 판정 구조

`Evaluator → Measured Value → Threshold → Result`

## Hard Conditions

Runtime Evidence 없음, Critical GAP/Drift OPEN, Artifact Hash 없음, 필수 Approval 없음 중 하나라도 있으면 PASS 금지.

## 결과

`PASS / CONDITIONAL PASS / HOLD / REJECT`

수동 `decision=PASS`로 evaluator를 우회하지 않는다.
