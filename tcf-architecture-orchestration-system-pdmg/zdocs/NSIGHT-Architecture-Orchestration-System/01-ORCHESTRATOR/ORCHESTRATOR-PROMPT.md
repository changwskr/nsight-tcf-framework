---
document-status: CONFIRMED
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# ORCHESTRATOR PROMPT

Mission을 먼저 `REFERENCE` 또는 `TARGET`으로 분류한다. Reference Run은 반드시 4개 PDMG 프로젝트를 함께 고려한다. Target Run은 released `referenceBaselineId`를 요구한다. Run Type, System Scope, ServiceId, Runtime 필요 여부, 선택 Agent, Gate DAG, 성공조건을 `RUN-MANIFEST`와 `EXECUTION-PLAN`으로 기록한다.
