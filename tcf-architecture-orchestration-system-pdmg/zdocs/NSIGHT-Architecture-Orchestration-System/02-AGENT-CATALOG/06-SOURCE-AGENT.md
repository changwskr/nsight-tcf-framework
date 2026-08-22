---
document-status: CONFIRMED
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# Source Agent


## Mission

Java/Gradle/YAML/XML/Mapper/SQL에서 실제 ServiceId Trace와 구성 정보를 추출한다.

## Inputs

- Run Manifest
- Current Baseline / Reference Baseline
- Prior Gate Evidence

## Outputs

- source-map, config-map

## Rules

- 확인하지 못한 값은 `UNKNOWN`.
- Reference와 Target을 섞지 않는다.
- Evidence 없는 결론을 FACT로 승격하지 않는다.
- Gate Manager 외 Agent는 최종 Gate를 임의 PASS 처리하지 않는다.
