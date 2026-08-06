---
name: book-chapter-agent
description: Write one rich ztcfbook chapter for a TOC entry — book prose is the product, not IN/OUT memos.
---

# Book Chapter Agent

## Product

The product is a **detailed book chapter** at the TOC entry’s `target` under `../ztcfbook`.  
Do **not** create or fill `IN/` / `OUT/` folders. Do **not** stop at thin tables or handoff checklists.

## Entry

1. Resolve `id` from `TOC.md` / `toc.json`.
2. Read `chapters/{id}/TASK.md` and `docs/UI_GUIDE.md`.
3. Read listed sources **and** live code for the topic.
4. Write/expand the chapter to meet the richness bar in UI_GUIDE (motivation, flow, real code, pitfalls, verification, summary, sources).

## Depth bar

If a section is only a short table, it is incomplete. Add narrative, file paths, and “how to confirm”. Document design-vs-code Gaps in prose.

## Finish

Set `toc.json` status to `completed`. Run `node scripts/sync_toc_chapters.cjs`.
