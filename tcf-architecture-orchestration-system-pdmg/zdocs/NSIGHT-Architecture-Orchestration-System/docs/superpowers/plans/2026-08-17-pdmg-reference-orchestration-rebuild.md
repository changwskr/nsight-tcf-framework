# PDMG Reference Architecture Orchestration Rebuild Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Rebuild the Architecture Orchestration System so `pdmg-ui`, `pdmg-fw`, `pdmg-service`, and `pdmg-jwt` form the verified PDMG Reference Project Set and all target projects are validated for conformance against a released PDMG Reference Baseline.

**Architecture:** Preserve the existing Closed Loop concepts but split orchestration into a Reference lane (`RG00`–`RHG90`) and a Target Conformance lane (`G00`–`HG90`). Reference source is promoted through `RAW → VERIFIED → REFERENCE`; source existence alone never makes a rule standard. Target releases must carry a `referenceBaselineId` and cannot pass final release when the PDMG reference baseline is not released.

**Tech Stack:** Markdown orchestration prompts, YAML/JSON-shaped contracts expressed in Markdown templates, Gradle/Java source inventory evidence from PDMG modules, ZIP packaging, Python validation script executed during build.

## Global Constraints

- Reference Project Set is exactly `pdmg-ui`, `pdmg-fw`, `pdmg-service`, `pdmg-jwt`.
- Reference promotion is exactly `RAW SOURCE BASELINE → VERIFIED BASELINE → REFERENCE ARCHITECTURE`.
- Branch/Commit remain `UNKNOWN` when Git metadata is unavailable.
- Runtime Evidence is mandatory for `RHG90 PASS` and `HG90 PASS` when runtime verification is required.
- Human Approval is mandatory for Transaction Boundary, Timeout baseline, JWT key boundary, breaking layer/package rules, Reference Exception, and baseline release.
- Existing NSIGHT/PDMK/PDMP material remains reference/target material and is not silently converted into PDMG AS-IS.
- `build/`, `bin/`, `.gradle/`, `target/`, `logs/`, generated/history/duplicate content is excluded from Source of Truth unless explicitly used as runtime evidence.

---

### Task 1: Establish the PDMG reference source inventory

**Files:**
- Create: `00-REFERENCE-BASELINE/REFERENCE-PROJECTS.md`
- Create: `00-REFERENCE-BASELINE/PDMG-SOURCE-BASELINE.md`
- Create: `00-REFERENCE-BASELINE/PDMG-REFERENCE-ARCHITECTURE.md`
- Create: `00-REFERENCE-BASELINE/PDMG-REFERENCE-RULES.md`
- Create: `00-REFERENCE-BASELINE/PDMG-ALLOWED-VARIANTS.md`
- Create: `00-REFERENCE-BASELINE/PDMG-REFERENCE-GAPS.md`

**Interfaces:**
- Consumes: Source facts from the four PDMG reference modules.
- Produces: The canonical Reference Project Set, RAW source facts, candidate architecture, candidate rules, variants, and open reference gaps.

- [ ] **Step 1:** Record module roles, Java/Spring Boot/Gradle versions, packaging, source/test counts, package roots, and sibling dependencies from actual source.
- [ ] **Step 2:** Mark Git branch/commit `UNKNOWN` because the supplied extracted source has no trusted repository metadata.
- [ ] **Step 3:** Record source-derived claims as `AS-IS` or `CANDIDATE`, never `VERIFIED REFERENCE` without the Reference Gate flow.
- [ ] **Step 4:** Verify all four modules are named in `REFERENCE-PROJECTS.md` and no fifth module is listed as a reference project.

### Task 2: Rebuild the root contract and Orchestrator

**Files:**
- Modify/Create: `START-HERE.md`, `README.md`, `00-MASTER-PROMPT.md`, `AGENTS.md`, `ARCHITECTURE-ORCHESTRATION-RULES.md`, `SOURCE-MAP.md`, `CURRENT-KNOWN-GAPS.md`, `PILOT-01-START.md`
- Create/Modify under `01-ORCHESTRATOR/`: `ORCHESTRATOR-PROMPT.md`, `REFERENCE-MISSION-ROUTING-RULES.md`, `TARGET-MISSION-ROUTING-RULES.md`, `MISSION-ROUTING-RULES.md`, `TEAM-SELECTION-RULES.md`, templates and status board.

**Interfaces:**
- Consumes: Reference Baseline files from Task 1.
- Produces: Run routing for `REFERENCE_BOOTSTRAP`, `REFERENCE_RECONCILIATION`, `REFERENCE_RELEASE`, `CONFORMANCE_REVIEW`, and existing target run types.

- [ ] **Step 1:** Make the Reference Project Set the default source/architecture baseline for reference-building missions.
- [ ] **Step 2:** Make `PDMG_REFERENCE` and `TARGET_PROJECT` explicit, non-interchangeable scope classes.
- [ ] **Step 3:** Route first-time use through `REFERENCE_BOOTSTRAP → REFERENCE_RECONCILIATION → REFERENCE_RELEASE` before target conformance.
- [ ] **Step 4:** Require every target run manifest to contain `referenceBaselineId` or `UNKNOWN` with an explicit blocker.

### Task 3: Rebuild Agent Catalog, Workspaces, and Stage Prompts

**Files:**
- Create/Modify: `02-AGENT-CATALOG/*.md`
- Rebuild: `03-WORKSPACE/REFERENCE/`, `03-WORKSPACE/TARGET-TEMPLATE/`, `03-WORKSPACE/RUNS/`
- Create: `04-STAGE-PROMPTS/REFERENCE/STEP-R00...STEP-R90`
- Create: `04-STAGE-PROMPTS/TARGET/STEP-00...STEP-90`

**Interfaces:**
- Consumes: Orchestrator run types and scope classes.
- Produces: Dedicated reference agents and target conformance execution prompts.

- [ ] **Step 1:** Add PDMG Reference Baseline, Rule Extractor, Reconciliation, and Conformance agents.
- [ ] **Step 2:** Keep Document, Model, Source, Code Rule, Test, Deploy, Runtime, Drift, GAP/ADR, and Gate Manager responsibilities but make Reference/Target inputs explicit.
- [ ] **Step 3:** Split stage prompts into Reference and Target lanes and include outputs, evidence, stop conditions, and next Gate.
- [ ] **Step 4:** Ensure Reference runtime absence forces `RG50=HOLD` and prevents `RHG90 PASS` while permitting static analysis to continue.

### Task 4: Rebuild Gate Profiles and Governance contracts

**Files:**
- Create: `05-GATE/REFERENCE/RG00...RHG90`
- Create: `05-GATE/TARGET/G00...HG90`
- Modify/Create: `05-GATE/GATE-RULES.md`, `08-GOVERNANCE/*.md`

**Interfaces:**
- Consumes: Reference/Target Stage outputs.
- Produces: Objective Gate rules and release blockers.

- [ ] **Step 1:** Define Reference Gate profile and Target Conformance Gate profile separately.
- [ ] **Step 2:** Prohibit target `HG90 PASS` when `referenceBaselineId` does not point to a released `RHG90 PASS` baseline.
- [ ] **Step 3:** Require Evaluator → Measured Value → Threshold → Result for automated Gate checks and prohibit manual PASS substitution.
- [ ] **Step 4:** Require runtime evidence, artifact hash, resolved critical drift/gap, and valid human approval for final release gates.

### Task 5: Rebuild templates, use cases, user views, and reference documentation

**Files:**
- Modify/Create: `06-TEMPLATES/*.md`
- Modify/Create: `07-USE-CASES/*.md`
- Modify/Create: `09-USER-VIEWS/*.md`
- Modify/Create: `99-REFERENCE/*.md`
- Copy: approved design spec and implementation plan into `docs/superpowers/`.

**Interfaces:**
- Consumes: Reference/Target model and Gate contracts.
- Produces: Reusable artifacts for source baseline, reference model, conformance matrix, drift, ADR, approval, and baseline release.

- [ ] **Step 1:** Add Reference Baseline, Reference Rule, Allowed Variant, Conformance Matrix, and Reference Release templates.
- [ ] **Step 2:** Update vertical-slice use cases to use `pdmg-service`, `pdmg-jwt`, `pdmg-ui`, and `pdmg-fw` as the reference chain.
- [ ] **Step 3:** Update user views to show Reference Baseline status and Target Conformance result.
- [ ] **Step 4:** Preserve historical NSIGHT documents only under `99-REFERENCE` and label them non-authoritative for PDMG Reference AS-IS.

### Task 6: Validate and package the full reconstructed system

**Files:**
- Create: `MANIFEST.md`
- Create: `VALIDATION-REPORT.md`
- Create: `tools/validate_orchestration.py`
- Create archive: `/mnt/data/NSIGHT-Architecture-Orchestration-System-PDMG-Reference.zip`

**Interfaces:**
- Consumes: Entire reconstructed workspace.
- Produces: Validated distributable ZIP.

- [ ] **Step 1:** Validate required paths, exactly four reference module names, expected Reference/Target stage prompt counts, and expected Gate counts.
- [ ] **Step 2:** Scan Markdown for empty files, replacement characters, unbalanced fenced code blocks, and broken relative Markdown links.
- [ ] **Step 3:** Generate `MANIFEST.md` and `VALIDATION-REPORT.md` from actual filesystem results.
- [ ] **Step 4:** Create ZIP and run `unzip -t` to verify archive integrity.
- [ ] **Step 5:** Verify the ZIP contains `00-REFERENCE-BASELINE`, both Gate profiles, both Stage Prompt profiles, the design spec, implementation plan, manifest, and validation report.
