# NSIGHT TCF Book Writing Harness (TOC-driven, book-first)

## Scope

This harness **writes a book**. The deliverable is rich Markdown under `../ztcfbook` (or approved `-m`/`-h`). The harness SoT is [`TOC.md`](./TOC.md) / [`toc.json`](./toc.json). Each TOC entry has `chapters/{id}/TASK.md` as the agent brief only — **not** an IN/OUT work-order factory.

Do not create `chapters/{id}/IN/` or `OUT/` folders. Do not treat analysis-summary style memos as the product. Inspect `git status --short` first; preserve user changes.

## Book-first contract

1. Pick one `toc.json` `id`.
2. Read `chapters/{id}/TASK.md`.
3. Read framework sources **and live code**, then write a **detailed, narrative chapter** at `target`.
4. Mark `toc.json` status `completed` and run `node scripts/sync_toc_chapters.cjs`.

A good chapter includes: motivation, concepts, end-to-end flow, real paths/code, tables, pitfalls, how to verify, summary, prev/next, source index. Prefer depth over short checklists.

## Source cascade

1. Live code (`tcf-*`, `*-service`, …)
2. Manuals / architecture (`znsight-man`, `zman`, `zarchitecture`, `zguide`, `zdocs-1/architecture`)
3. Existing book drafts
4. `ztcf-다이어리` (supporting only)

Do not invent identifiers. Document design-vs-code Gaps in the chapter prose.

## Stop conditions

Unknown facts → ask. No `_gen-book-*.cjs` without approval. No auto-push to `main`/`develop`.
