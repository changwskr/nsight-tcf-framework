---
document-status: CONFIRMED
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# ARCHITECTURE ORCHESTRATION RULES


1. `PDMG_REFERENCE`는 기준 Scope이며 Target Scope와 동일 AS-IS로 취급하지 않는다.
2. 4개 Reference Source에 존재한다는 사실은 `AS-IS`일 뿐 표준 확정을 의미하지 않는다.
3. 공통 패턴은 `REFERENCE_RULE_CANDIDATE`로 시작하고 Self-Test, Runtime Evidence, Approval 후 승격한다.
4. 충돌은 숨기지 않고 `REFERENCE_INTERNAL_DRIFT`로 등록한다.
5. Target 차이는 `MATCH / ALLOWED_VARIANT / GAP / EXCEPTION / UNKNOWN`으로 분류한다.
6. Architecture Rule은 `Evaluator → Measured Value → Threshold → Result`로 판정한다.
7. Runtime Evidence는 `referenceBaselineId/sourceCommit/buildId/artifactHash/deploymentId/serviceId/traceId/evidenceId` Chain을 유지한다.
8. Critical GAP/Drift, Artifact Hash 누락, 필수 Approval 누락, Runtime Evidence 누락 시 Final Gate PASS를 금지한다.
