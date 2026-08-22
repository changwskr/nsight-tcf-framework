---
document-status: CONFIRMED
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# STEP R60-INTERNAL-DRIFT


## 실행 프롬프트

Reference Document↔Model↔Code↔Config↔Test↔Runtime 내부 차이를 REFERENCE_INTERNAL_DRIFT로 등록한다.

## 공통 수행

1. 입력 Artifact와 prior Gate를 확인한다.
2. Source/Evidence를 실제로 확인하고 UNKNOWN을 추정으로 채우지 않는다.
3. 산출물에 provenance, status, evidenceId를 기록한다.
4. `RG60` 판정을 위한 measured evidence를 Gate Manager에 전달한다.

## Stop Condition

Critical Evidence가 없으면 PASS를 만들지 않는다. 가능한 범위의 후속 분석은 계속한다.
