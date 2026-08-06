# 아키텍처 — 책 우선 집필 하네스

```text
TOC.md / toc.json
    → chapters/{id}/TASK.md   (브리프만)
    → ../ztcfbook/...md       (풍부한 본문 = 제품)
```

- IN/OUT 작업장 패턴 **폐기**
- Skills는 본문 품질을 돕는 역할 계약
- sync 스크립트는 TOC·TASK 동기화 + 레거시 IN/OUT 제거
