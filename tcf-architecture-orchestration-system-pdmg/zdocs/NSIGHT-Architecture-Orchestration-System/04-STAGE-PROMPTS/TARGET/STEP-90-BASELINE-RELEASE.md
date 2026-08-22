---
document-status: CONFIRMED
system-scope: TARGET_PROJECT
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# STEP 90-BASELINE-RELEASE


## 실행 프롬프트

released Reference Baseline과 Target evidence chain을 검증하고 모든 blocker 해소 시 Target Baseline을 발급한다.

## 공통 수행

1. 입력 Artifact와 prior Gate를 확인한다.
2. Source/Evidence를 실제로 확인하고 UNKNOWN을 추정으로 채우지 않는다.
3. 산출물에 provenance, status, evidenceId를 기록한다.
4. `HG90` 판정을 위한 measured evidence를 Gate Manager에 전달한다.

## Stop Condition

Critical Evidence가 없으면 PASS를 만들지 않는다. 가능한 범위의 후속 분석은 계속한다.
