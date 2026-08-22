---
document-status: CONFIRMED
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# STEP R50-RUNTIME


## 실행 프롬프트

pdmg-service와 pdmg-jwt 대표 ServiceId를 실제 실행하여 build/artifact/deploy/trace/runtime evidence chain을 수집한다. 환경 없으면 HOLD.

## 공통 수행

1. 입력 Artifact와 prior Gate를 확인한다.
2. Source/Evidence를 실제로 확인하고 UNKNOWN을 추정으로 채우지 않는다.
3. 산출물에 provenance, status, evidenceId를 기록한다.
4. `RG50` 판정을 위한 measured evidence를 Gate Manager에 전달한다.

## Stop Condition

Critical Evidence가 없으면 PASS를 만들지 않는다. 가능한 범위의 후속 분석은 계속한다.
