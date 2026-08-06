# Book Handoff Protocol (book-first)

The shippable artifact is the **chapter Markdown** at the TOC `target`.

Optional short notes in chat are fine. **Do not** require or create:

- `chapters/{id}/IN/`
- `chapters/{id}/OUT/`
- `analysis-summary.md` / `draft-plan.md` / `qa-report.md` as primary deliverables

Before calling a chapter done:

1. Richness bar in `docs/UI_GUIDE.md` is met.
2. Cited paths/codes exist (or Gap is explained in prose).
3. Prev/next + source index present.
4. `toc.json` status updated; sync run.

`node _gen-book-*.cjs` still needs explicit user approval.  
`node scripts/sync_toc_chapters.cjs` only refreshes harness TOC/TASK (does not overwrite book bodies).
