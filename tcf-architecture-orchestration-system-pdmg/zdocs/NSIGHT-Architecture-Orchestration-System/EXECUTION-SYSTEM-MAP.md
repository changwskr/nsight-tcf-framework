# PDMG Architecture Orchestration 실행 시스템 맵

## Reference Lane

| 단계 | 실행 엔진 | 자동 산출물 | Gate |
|---|---|---|---|
| R00 Source | `scan-reference` | Source/Config/ServiceId/Mapper/Trace Inventory | RG00 |
| R10 Document | `scan-reference` | Source-derived `CURRENT-ARCHITECTURE.md` | RG10 |
| R20 Model | `scan-reference` + Schema Validator | Reference Model Draft + Schema Result | RG20 |
| R30 Rule | `scan-reference` | Candidate Reference Rules | RG30 |
| R40 Test | `static-check`, `run-check`, `artifact` | Architecture/Security/Build/Test/Artifact Evidence | RG40 |
| R50 Runtime | `record-deployment`, `import-runtime` | Deployment + Runtime Evidence Manifest | RG50 |
| R60 Drift | Scanner/Conformance + `resolve` | Drift Register | RG60 |
| R70 GAP/ADR | `register-gap`, `create-adr`, `resolve` | GAP Register + ADR | RG70 |
| R80 Approval | `require-approval`, `approve` | Hash-bound Approval Register | RG80 |
| R90 Release | `evaluate RHG90`, `release` | PDMG Reference Baseline + Evidence Package | RHG90 |

## Target Lane

| 단계 | 실행 엔진 | 자동 산출물 | Gate |
|---|---|---|---|
| 00 Source | `create-target-run`, `scan-target` | Target Source Baseline | G00 |
| 10 Document | `scan-target` | Target Source-derived Architecture | G10 |
| 20 Model | `scan-target` + Schema Validator | Target Source Model | G20 |
| 30 Code/Conformance | `scan-target` | Reference↔Target Conformance + Drift | G30 |
| 40 Test | `static-check`, `run-check`, `artifact` | Build/Test/Security/Architecture Evidence | G40 |
| 50 Runtime | `record-deployment`, `import-runtime` | Deployment + Runtime Evidence | G50 |
| 60 Drift | Conformance + `resolve` | Drift Register | G60 |
| 70 GAP/ADR | `register-gap`, `create-adr`, `resolve` | GAP/ADR | G70 |
| 80 Approval | `require-approval`, `approve` | Approval Register | G80 |
| 90 Release | `evaluate HG90`, `release` | Target Architecture Baseline | HG90 |

## Final Hard Blockers

```text
Released Reference Baseline (Target HG90)
Document Baseline
Model Schema
Model↔Source / Conformance
Architecture Rule Evidence
Build PASS
Test PASS
Security PASS
Architecture Static Test PASS
Artifact SHA-256
Deployment Evidence
Tested Artifact Hash == Deployed Artifact Hash
Runtime Evidence
Critical Drift OPEN = 0
Critical GAP OPEN = 0
All Required Human Approvals Valid
```

`RHG90/HG90`은 위 조건을 Evaluator가 직접 측정하며 수동 PASS Override를 허용하지 않는다.
