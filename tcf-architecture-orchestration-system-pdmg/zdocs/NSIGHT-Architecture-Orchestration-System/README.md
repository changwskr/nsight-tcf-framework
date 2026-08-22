---
document-status: CONFIRMED
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# NSIGHT Architecture Orchestration System — PDMG Reference Edition


이 작업공간은 `pdmg-ui`, `pdmg-fw`, `pdmg-service`, `pdmg-jwt` 4개 프로젝트를 **PDMG Reference Project Set**으로 사용한다.

핵심 원칙은 `Source = Standard`가 아니라 다음 3단계 승격이다.

```text
RAW SOURCE BASELINE
      ↓
INTERNAL RECONCILIATION
      ↓
VERIFIED BASELINE
      ↓
REFERENCE ARCHITECTURE
      ↓ RHG90
PDMG REFERENCE BASELINE
      ↓
TARGET PROJECT CONFORMANCE
```

Reference 프로젝트 자체의 충돌은 먼저 `REFERENCE_INTERNAL_DRIFT`로 처리하고, `RHG90 PASS` 된 규칙만 다른 프로젝트의 표준으로 사용한다.

## 기준 프로젝트

| 프로젝트 | 기준 책임 |
|---|---|
| `pdmg-ui` | UI Route, Transaction Catalog, ServiceId Consumer/Relay |
| `pdmg-fw` | TCF/STF/ETF, Dispatcher, Timeout/Transaction Infrastructure, Runtime 공통 |
| `pdmg-service` | 업무 Reference Application, Handler→Facade→Service→DAO→Mapper/SQL |
| `pdmg-jwt` | JWT 발급/검증 보조, RS256/JWKS, Token Lifecycle |

## 두 개의 Closed Loop

```text
REFERENCE LANE                         TARGET LANE
RG00 Source                            G00 Source
RG10 Document                          G10 Document
RG20 Model                             G20 Model
RG30 Rule Extraction                   G30 Reference↔Target
RG40 Self-Test                         G40 Conformance Test
RG50 Runtime Evidence                  G50 Runtime Evidence
RG60 Internal Drift                    G60 Target Drift
RG70 GAP/ADR                           G70 GAP/ADR
RG80 Approval                          G80 Approval
RHG90 Reference Release                HG90 Target Release
```

Target `HG90 PASS`는 유효한 `referenceBaselineId`가 `RHG90 PASS` 상태일 때만 허용한다.


## 실행 가능한 Harness

이 Edition은 Markdown 프롬프트/규칙에 더해 Python 표준 라이브러리 기반 실행 엔진을 포함한다.

```text
execution/
├─ pdmg_orchestrator.py
├─ engine/
├─ config/
├─ schemas/
├─ tests/
├─ workflows/
└─ bin/
```

실행은 [QUICK-START.md](QUICK-START.md)를 먼저 보고, 운영 규칙은 [EXECUTION-GUIDE.md](EXECUTION-GUIDE.md)를 따른다.
