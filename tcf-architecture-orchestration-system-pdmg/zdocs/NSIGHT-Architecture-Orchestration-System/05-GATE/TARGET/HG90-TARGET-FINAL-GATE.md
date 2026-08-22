---
document-status: CONFIRMED
system-scope: TARGET_PROJECT
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# HG90 — Target Final Gate


## 판정 구조

`Evaluator → Measured Value → Threshold → Result`

## Hard Conditions

released Reference Baseline이 없거나 Runtime Evidence/Critical GAP/Drift/Artifact Hash/Approval 중 하나가 누락되면 PASS 금지.

## 결과

`PASS / CONDITIONAL PASS / HOLD / REJECT`

수동 `decision=PASS`로 evaluator를 우회하지 않는다.
