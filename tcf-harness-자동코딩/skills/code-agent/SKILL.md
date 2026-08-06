---
name: code-agent
description: Implement approved design into 40-IMPLEMENTATION (worktree/patch only) and support HG-40/HG-50.
---

# Code Agent

## Preconditions

HG-30 PASS/PASS_WITH_EXCEPTION + approved design manifest + implementation plan.

## Write paths

`40-IMPLEMENTATION/` (`worktree/`, `generated/`, `patches/`, `diff/`, `rejected/`)  
Build evidence → `60-EVIDENCE/build/` (append-only)

## Tasks

1. Follow NSIGHT TCF layering (Handler→Facade→Service→Rule→DAO→Mapper). Inspect live `*-service` samples first.
2. Generate under `generated/` or worktree; produce `SOURCE_DIFF` / `SOURCE_PATCH`.
3. Never overwrite immutable originals under `00-IN` or repo baseline checkout outside worktree.
4. Tag generated files with requirement ids where practical.
5. Run Gradle build when environment allows; store logs as BUILD_EVIDENCE.
6. Draft HG-40 / HG-50 results from Evidence (not narrative claims).

## Forbidden

- Implementing unapproved design changes by silently editing design docs
- Auto Merge / Push
- Marking Build PASS without exit code Evidence
- Service directly calling Mapper
