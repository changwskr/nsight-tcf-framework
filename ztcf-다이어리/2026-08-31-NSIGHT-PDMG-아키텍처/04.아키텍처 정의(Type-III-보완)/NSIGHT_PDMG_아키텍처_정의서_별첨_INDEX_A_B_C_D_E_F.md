# NSIGHT / PDMG 아키텍처 정의서 — 별첨 INDEX
## A Application / B Technical / C Infrastructure / D Interface / E Data / F Naming Development Standard

```text
A APPLICATION
무엇을 실행하는가?
      ↓
B TECHNICAL
어떤 기술구조로 실행하는가?
      ↓
C INFRASTRUCTURE
어디에 어떤 자원으로 배치하는가?

A APPLICATION ───────────────┐
                            ├─► D INTERFACE
E DATA ─────────────────────┘    어떻게 연결하는가?

A/B/C/D/E
      ↓
F NAMING DEVELOPMENT STANDARD
모든 Architecture Object를 어떤 식별축으로 연결하는가?
      ↓
Source / CI / Runtime Evidence
```

| 별첨 | 문서 | 핵심 |
|---|---|---|
| A | `NSIGHT_PDMG_아키텍처_정의서_별첨_A_APPLICATION_ARCHITECTURE_DEFINITION_VISUAL_FIRST.md` | Application Responsibility / Boundary / Component |
| B | `NSIGHT_PDMG_아키텍처_정의서_별첨_B_TECHNICAL_ARCHITECTURE_DEFINITION_VISUAL_FIRST.md` | Technical Capability / Logical Technology / Runtime |
| C | `NSIGHT_PDMG_아키텍처_정의서_별첨_C_INFRASTRUCTURE_ARCHITECTURE_DEFINITION_VISUAL_FIRST.md` | Center / Compute / Network / Storage / Physical Deployment |
| D | `NSIGHT_PDMG_아키텍처_정의서_별첨_D_INTERFACE_ARCHITECTURE_DEFINITION_VISUAL_FIRST.md` | Interface Contract / Sync-Async / Failure-Recovery |
| E | `NSIGHT_PDMG_아키텍처_정의서_별첨_E_DATA_ARCHITECTURE_DEFINITION_VISUAL_FIRST.md` | Data Domain / Ownership / Model / Flow / Quality |
| F | `NSIGHT_PDMG_아키텍처_정의서_별첨_F_NAMING_DEVELOPMENT_STANDARD_VISUAL_FIRST.md` | Program / ServiceId / Package / Source / Deployment Naming |

## Naming Backbone

```text
Business
 ↓
ProgramId
 ↓
ServiceId
 ↓
Package / Class
 ↓
Mapper / SqlId
 ↓
Data / Interface
 ↓
Artifact / Deployment
 ↓
Host / JVM
 ↓
Runtime Evidence
```
