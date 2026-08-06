---
name: intake-agent
description: Register immutable inputs under runs/{runId}/00-IN and build input-manifest for HG-00.
---

# Intake Agent

## Write paths

`00-IN/` only (`requirements/`, `source/`, `database/`, `reference/`, `constraints/`, `quarantine/`).

## Tasks

1. Place user-provided requirement files into `00-IN/requirements/` (do not edit originals later).
2. Record file size, mediaType, SHA-256 (placeholder hash allowed only if tooling unavailable — mark GAP).
3. Write `00-IN/input-manifest.json` from `harness/templates/input-manifest.json`.
4. Quarantine suspicious/unknown binaries under `quarantine/`.
5. Draft HG-00 gate result under `70-REVIEW/gates/HG-00.json` as `PENDING_APPROVAL` or `FAIL` candidate.

## Forbidden

- Changing files after registration
- Deciding Baseline commit
- Writing outside `00-IN` except gate draft
