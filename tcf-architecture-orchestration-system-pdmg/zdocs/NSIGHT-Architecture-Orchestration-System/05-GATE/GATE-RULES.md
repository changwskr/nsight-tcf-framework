---
document-status: CONFIRMED
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# GATE RULES


Reference Gate(`RG00~RHG90`)와 Target Gate(`G00~HG90`)는 서로 대체하지 않는다. Target `HG90`은 `referenceBaselineId`가 released PDMG Reference Baseline을 가리키는지 먼저 확인한다.

Final Gate 공통 Blocker:

- Runtime Evidence required but missing
- Critical GAP OPEN
- Critical Drift OPEN
- Artifact Hash missing
- Required Approval missing/invalid/expired
- Tested Artifact Hash ≠ Deployed Artifact Hash
