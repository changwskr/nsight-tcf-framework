# tcf-harness-exe-집필

NSIGHT-TCF-FRAMEWORK를 읽어 **자세하고 풍부한 책**(`ztcfbook`)을 목차 항목 단위로 집필하는 하네스다.

## 핵심

| | |
|--|--|
| 제품 | `../ztcfbook/...` **본문** |
| 아님 | `IN/` · `OUT/` 메모·체크리스트 |
| 단위 | `TOC.md` 항목 1개 |
| 브리프 | `chapters/{id}/TASK.md` |

## 빠른 시작

```text
tcf-harness-exe-집필 TOC의 CH-22를 자세하고 풍부하게 집필해줘
```

에이전트는 출처·실코드를 읽고 `ztcfbook` 장을 **가이드 챕터 수준**으로 쓴다.

## 동기화

```bash
cd tcf-harness-exe-집필
node scripts/sync_toc_chapters.cjs
```

목차·TASK만 맞춘다. 책 본문은 덮어쓰지 않는다. IN/OUT 폴더는 제거한다.

## 관련

- [`TOC.md`](./TOC.md) · [`AGENTS.md`](./AGENTS.md) · [`docs/UI_GUIDE.md`](./docs/UI_GUIDE.md)
- [`../ztcfbook/00-목차.md`](../ztcfbook/00-목차.md)
