---
document-status: PROPOSED
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# PILOT 01 — PDMG REFERENCE BOOTSTRAP


첫 Pilot은 Target 검증이 아니라 Reference 구축이다.

```text
REFERENCE_BOOTSTRAP
→ RG00
→ REFERENCE_RECONCILIATION
→ RG10/RG20/RG30/RG40
→ RG50 Runtime Evidence
→ RG60/RG70/RG80
→ RHG90
→ PDMG-REF-...
```

Vertical Slice 후보는 `pdmg-service: mgcoa5530S0`, `pdmg-jwt: mgjwa1000C0`이며 실제 실행 전 Source에서 다시 확인한다. `pdmg-ui`는 해당 ServiceId Consumer/Relay 관계를, `pdmg-fw`는 공통 Runtime 관계를 연결한다.
