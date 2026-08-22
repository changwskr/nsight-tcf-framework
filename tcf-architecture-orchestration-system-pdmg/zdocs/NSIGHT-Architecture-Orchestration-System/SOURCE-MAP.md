---
document-status: AS-IS
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# SOURCE MAP


| Priority | Source | 역할 |
|---:|---|---|
| 1 | `pdmg-ui/src/main/**` | UI/ServiceId Consumer Reference |
| 1 | `pdmg-fw/src/main/**` | Framework/TCF Reference |
| 1 | `pdmg-service/src/main/**` | Business Application Reference |
| 1 | `pdmg-jwt/src/main/**` | JWT/Security Reference |
| 2 | 각 프로젝트 `build.gradle`, `settings.gradle`, `src/main/resources/**` | Applied Configuration/Build |
| 3 | 각 프로젝트 `src/test/**` | Test Evidence |
| 4 | Approved PDMG ADR/Decision | Reference Decision |
| 5 | Legacy NSIGHT/PDMK/PDMP docs | 비교/역사 자료 |

`build/**`, `.gradle/**`, `bin/**`, `target/**`, generated/history/duplicate는 Source of Truth에서 제외한다.
