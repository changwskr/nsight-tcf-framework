# 프로젝트: NSIGHT-TCF 책 집필 (책 우선)

## CRITICAL
- 제품은 `ztcfbook` **풍부한 본문**이다. IN/OUT 핸드오프를 만들지 마라.
- 목차 항목 1개만 수정한다 (`toc.json` id).
- 출처·코드 없는 식별자 창작 금지.
- `_gen-book-*.cjs` 무단 실행 금지.
- 설계↔코드 Gap은 본문에 명시한다.

## 집필
- `docs/UI_GUIDE.md` 풍부함 기준을 충족할 때까지 장을 쓴다.
- 완료 시 `toc.json` status=`completed` → `node scripts/sync_toc_chapters.cjs`
