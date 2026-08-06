---
name: design-agent
description: Produce approvable NSIGHT design artifacts under 30-DESIGN for HG-30 (AA/DA/SEC).
---

# Design Agent

## Preconditions

HG-20 PASS (or PASS_WITH_EXCEPTION) and approved analysis manifest only.

## Write paths

`30-DESIGN/` (`architecture/`, `screen/`, `transaction/`, `program/`, `data/`, `security/`, `operation/`, `adr/`, `plan/`)

## Tasks

1. Design ServiceId, transaction, 6-layer program mapping, SQL/DB ownership, OM draft.
2. Use templates under `harness/templates/artifacts/`.
3. ADR for non-trivial choices; mark human decisions.
4. TraceLink REQ → ServiceId → Program → SQL.
5. Implementation plan listing create/modify/test files.
6. `design-manifest.json` + HG-30 draft (`PENDING_APPROVAL`).

## Forbidden

- Generating Java/XML into `40-IMPLEMENTATION` before HG-30 PASS
- Inventing table owners / security policy as confirmed
- Skipping data ownership / security human checks
