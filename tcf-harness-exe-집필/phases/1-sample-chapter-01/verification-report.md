# verification-report.md — 1-sample-chapter-01

## Changed paths

- `ztcfbook/제01편/01-NSIGHT-TCF란-무엇인가.md`
  - 1.2: Handler `serviceIds()` Gap 문단 추가
  - 1.3: `user`/`branch` JsonAlias 안내 추가
  - 출처 색인: `docs/architecture/...` → `zdocs-1/architecture/architecture.md`
- `tcf-harness-exe-집필/phases/1-sample-chapter-01/analysis-summary.md` (신규)
- `tcf-harness-exe-집필/phases/1-sample-chapter-01/draft-plan.md` (신규)

## Commands and exit codes

| Command | Exit / result |
| --- | --- |
| `Test-Path` chapter / sources (inventory) | 0 — architecture만 MISS→zdocs-1 대체 |
| `Select-String` zdocs-1 link in chapter | match |
| `Select-String` 출처 색인 | match |
| `Select-String` docs/architecture (stale) | no match after fix |
| `_gen-book-*.cjs` | **not run** (비승인) |

## Diff summary

최소 문서 수정 3곳. 본문 대구조·절 ID·네비 유지.

## Remaining gaps

- `_gen-book-chapters.cjs` / `00-목차.md` 출처 표에 아직 `docs/architecture` 문자열이 남아 있을 수 있음 → 별도 TOC 정리 phase 권장.
- `-m`/`-h` 미러 미반영.
