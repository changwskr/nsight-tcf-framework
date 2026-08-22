---
document-status: CONFIRMED
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# START HERE


## 최초 실행 순서

1. `REFERENCE_BOOTSTRAP`: 4개 프로젝트를 RAW Source Baseline으로 수집한다.
2. `REFERENCE_RECONCILIATION`: 4개 프로젝트의 공통점과 충돌을 검증한다.
3. `REFERENCE_RELEASE`: Test/Runtime/Human Approval을 거쳐 PDMG Reference Baseline을 발급한다.
4. 이후 `CONFORMANCE_REVIEW`, `VERTICAL_SLICE`, `DECISION_REVIEW`, `RELEASE_VALIDATION`을 Target Project에 수행한다.

```text
pdmg-ui + pdmg-fw + pdmg-service + pdmg-jwt
                   ↓
         PDMG-SRC-YYYYMMDD-NNN
                   ↓
         PDMG-REF-YYYYMMDD-NNN
                   ↓
         Target Conformance Run
```

첫 Pilot은 `pdmg-service`의 실제 ServiceId `mgcoa5530S0`와 `pdmg-jwt`의 `mgjwa1000C0`를 우선 후보로 삼되, 실행 시 Source에서 다시 존재를 확인한다.


## 실행 엔진

설계 문서만 읽지 말고 실제 실행은 다음에서 시작한다.

- [QUICK-START.md](QUICK-START.md) — 최초 실행 명령
- [EXECUTION-GUIDE.md](EXECUTION-GUIDE.md) — 전체 Run/Gate/Evidence/Release 체계
- `execution/bin/pdmg-orchestrator` — Linux/macOS CLI
- `execution\bin\pdmg-orchestrator.bat` — Windows CLI
- `execution/bin/pdmg-orchestrator.ps1` — PowerShell CLI

최초 명령:

```bash
./execution/bin/pdmg-orchestrator init
./execution/bin/pdmg-orchestrator scan-reference --repo /path/to/repository
```
