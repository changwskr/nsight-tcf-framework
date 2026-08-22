# PDMG Complete Architecture Orchestration Execution System Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Turn the PDMG Reference Architecture Orchestration documentation package into a directly runnable local harness for reference-baseline creation, target conformance analysis, gate evaluation, evidence integrity, approvals, and baseline release.

**Architecture:** Keep the existing Markdown governance package as the human-readable contract and add a Python-standard-library execution layer. A single CLI drives run creation, repository scanning, reference reconciliation, rule evaluation, evidence import/hash generation, approval validation, gate evaluation, and release packaging. All execution artifacts are JSON/Markdown under `03-WORKSPACE`, and every result is reproducible from explicit source paths and SHA-256 hashes.

**Tech Stack:** Python 3.11+ standard library, JSON, XML parsing, regular-expression based Java/Gradle/config source inspection, SHA-256, shell/Windows launchers, `unittest`.

## Global Constraints

- Reference projects are exactly `pdmg-ui`, `pdmg-fw`, `pdmg-service`, `pdmg-jwt`.
- RAW source is never automatically promoted to REFERENCE.
- Generated paths such as `build/`, `bin/`, `.gradle/`, `target/`, `logs/`, `generated/`, `history/`, and duplicate mirrors are excluded from Source of Truth scans by default.
- Unknown branch/commit/runtime values remain `UNKNOWN`; they are never fabricated.
- Gate results are evaluator-derived; a free-form manual PASS field cannot satisfy a hard rule.
- Runtime Evidence, artifact hash, critical drift/gap closure, and required approval are hard blockers for final release gates.
- Existing baselines are immutable; a release creates a new baseline and records predecessor/supersession metadata.

---

### Task 1: Execution package and CLI skeleton

**Files:**
- Create: `execution/pdmg_orchestrator.py`
- Create: `execution/engine/common.py`
- Create: `execution/engine/__init__.py`
- Create: `execution/bin/pdmg-orchestrator`
- Create: `execution/bin/pdmg-orchestrator.bat`
- Create: `execution/config/reference-projects.json`

**Interfaces:**
- Consumes: existing Orchestration root and source repository paths.
- Produces: CLI with `init`, `scan-reference`, `create-target-run`, `scan-target`, `evaluate`, `approve`, `import-runtime`, `release-reference`, `release-target`, `status`, and `validate` subcommands.

- [ ] Create CLI skeleton and shared JSON/hash/path helpers.
- [ ] Add cross-platform launchers.
- [ ] Add default reference project configuration.
- [ ] Verify `--help` exits 0.

### Task 2: Repository source scanner

**Files:**
- Create: `execution/engine/scanner.py`
- Create: `execution/schemas/source-baseline.schema.json`

**Interfaces:**
- Consumes: repository root and project list.
- Produces: `source-baseline.json`, `source-inventory.json`, `config-inventory.json`, Java/ServiceId/Mapper/SQL indexes, and Markdown summary.

- [ ] Implement exclusion-aware recursive inventory.
- [ ] Extract Git branch/commit when available, otherwise `UNKNOWN`.
- [ ] Extract Java/Spring Boot/Gradle facts from project files.
- [ ] Extract Java package/class/import/annotation facts.
- [ ] Extract PDMG-style ServiceId occurrences and Handler ownership.
- [ ] Extract MyBatis namespaces/statement IDs/table candidates.
- [ ] Verify against the four PDMG reference projects.

### Task 3: Reference reconciliation and rule extraction

**Files:**
- Create: `execution/engine/reference.py`
- Create: `execution/config/reference-rules.json`
- Create: `execution/schemas/reference-baseline.schema.json`

**Interfaces:**
- Consumes: reference scan results.
- Produces: candidate reference architecture, rule catalog, allowed-variant candidates, internal drift register, reference baseline draft.

- [ ] Compare common technology baselines across the four projects.
- [ ] Derive package/layer/build/service-id/JWT/transaction/timeout candidate rules from evidence.
- [ ] Mark uncertain rules `CANDIDATE`, never `CONFIRMED` automatically.
- [ ] Produce internal reference drift records.

### Task 4: Target conformance scanner

**Files:**
- Create: `execution/engine/conformance.py`
- Create: `execution/schemas/conformance-result.schema.json`

**Interfaces:**
- Consumes: released/draft PDMG reference baseline plus target scan.
- Produces: `MATCH`, `ALLOWED_VARIANT`, `GAP`, `EXCEPTION_CANDIDATE`, and `UNKNOWN` findings.

- [ ] Compare Java/Spring/Gradle versions.
- [ ] Compare layer/package/service-id/mapper constraints.
- [ ] Compare transaction/JWT/timeout evidence where statically observable.
- [ ] Generate traceability and drift candidate summaries.

### Task 5: Evaluator-driven gate engine

**Files:**
- Create: `execution/engine/gates.py`
- Create: `execution/config/gates/reference-gates.json`
- Create: `execution/config/gates/target-gates.json`
- Create: `execution/schemas/gate-result.schema.json`

**Interfaces:**
- Consumes: run artifacts and rule definitions.
- Produces: gate result with `evaluator`, `measuredValue`, `threshold`, `operator`, `result`, and evidence paths.

- [ ] Implement file-exists/count/zero-open/hash-present/approval-valid/runtime-present evaluators.
- [ ] Implement hard-blocker semantics for RHG90/HG90.
- [ ] Prevent manual decision override from satisfying evaluator failures.
- [ ] Create readable Markdown gate report.

### Task 6: Runtime evidence, artifact integrity, and approvals

**Files:**
- Create: `execution/engine/evidence.py`
- Create: `execution/engine/approval.py`
- Create: `execution/schemas/evidence-manifest.schema.json`
- Create: `execution/schemas/approval.schema.json`

**Interfaces:**
- Consumes: runtime files/artifacts/approval inputs.
- Produces: SHA-256 evidence manifests and hash-bound approval objects.

- [ ] Import runtime evidence without inventing missing runtime fields.
- [ ] Hash artifact/evidence files.
- [ ] Create approval object bound to exact artifact hashes.
- [ ] Revalidate approval hashes and expiration at gate time.

### Task 7: Baseline release and continuous run creation

**Files:**
- Create: `execution/engine/release.py`
- Create: `execution/engine/impact.py`
- Create: `execution/schemas/baseline-release.schema.json`

**Interfaces:**
- Consumes: final gate results and predecessor baseline metadata.
- Produces: immutable release directory, release manifest, predecessor link, evidence package, and change-impact run manifests.

- [ ] Generate reference and target baseline IDs only after final gate PASS.
- [ ] Copy/hash release evidence package.
- [ ] Record previous baseline as superseded in lineage metadata without modifying historical evidence.
- [ ] Create incremental run plan from changed files/service IDs.

### Task 8: Validation, tests, samples, and operator docs

**Files:**
- Create: `execution/tests/test_scanner.py`
- Create: `execution/tests/test_gates.py`
- Create: `execution/tests/test_approval_release.py`
- Create: `execution/examples/runtime-evidence.sample.json`
- Create: `execution/examples/approval.sample.json`
- Create: `EXECUTION-GUIDE.md`
- Create: `QUICK-START.md`
- Modify: `START-HERE.md`
- Modify: `README.md`
- Modify: `tools/validate_orchestration.py`

**Interfaces:**
- Consumes: all execution components.
- Produces: runnable verification and complete operating instructions.

- [ ] Add focused unit tests.
- [ ] Add end-to-end dry-run using the four PDMG projects.
- [ ] Validate Markdown/package structure and execution assets.
- [ ] Build final ZIP and verify `unzip -t` plus SHA-256.
