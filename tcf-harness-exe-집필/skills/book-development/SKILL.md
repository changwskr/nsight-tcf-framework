---
name: book-development
description: Own writing one rich NSIGHT-TCF book chapter for a toc.json entry (book-first, no IN/OUT).
---

# Book Development

Orchestrate **one** TOC entry. Prefer `book-chapter-agent`.

Read: `TOC.md`/`toc.json`, `chapters/{id}/TASK.md`, [chapter template](references/chapter-template.md), [framework source map](references/framework-source-map.md), `docs/UI_GUIDE.md`.

1. Survey sources + code.
2. Agree scope with the user if rewriting heavily.
3. Write a **rich** chapter at `target` (see UI_GUIDE richness bar).
4. Self-check facts against code; fix Gaps in the chapter.
5. Update `toc.json` → `completed` → `node scripts/sync_toc_chapters.cjs`.

Specialist skills (`book-research`, `book-draft`, `book-factcheck`, `book-quality`) support judgment — their outputs belong **in the chapter**, not as separate OUT files.
