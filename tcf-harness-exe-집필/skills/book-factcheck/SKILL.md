---
name: book-factcheck
description: Verify chapter claims against cited framework sources and live code.
---

# Book Fact-check

1. Extract every factual claim that names an identifier, path, port, API, or SQL.
2. Resolve each claim to a cited source file or code symbol.
3. Flag unsupported claims, stale paths, and design-vs-code mismatches.
4. Write `factcheck-report.md` with findings and disposition (fix / defer / ask-user).

Stop quality if any high-severity finding lacks disposition. Prefer code when manuals disagree, and document the Gap.
