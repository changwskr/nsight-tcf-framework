# Step 4: quality

## 읽어야 할 파일

- `/skills/book-quality/SKILL.md`
- `/docs/UI_GUIDE.md`
- `/docs/ADR.md`
- `phases/1-sample-chapter-01/factcheck-report.md`
- `../ztcfbook/00-목차.md`
- `../ztcfbook/제01편/01-NSIGHT-TCF란-무엇인가.md`

## 작업

`book-quality`로 품질 게이트를 수행한다.

1. 목차에 제1장 링크가 있는지 확인한다.
2. 이전·다음 상대 링크와 출처 경로 존재 여부를 점검한다.
3. 문체 가이드(표·절 ID·출처 색인)를 체크한다.
4. `-m`/`-h` 미러 후속이 필요하면 qa-report에 명시한다 (이번 phase에서 강제 작성하지 않음).
5. `qa-report.md`에 PASS/FAIL과 release recommendation을 기록한다.

## Acceptance Criteria

```bash
Test-Path -LiteralPath 'phases/1-sample-chapter-01/qa-report.md'
Select-String -LiteralPath '../ztcfbook/00-목차.md' -Pattern 'NSIGHT-TCF란-무엇인가'
Select-String -LiteralPath 'phases/1-sample-chapter-01/qa-report.md' -Pattern 'Release recommendation'
```

## 검증 절차

1. AC 실행.
2. factcheck Decision이 PASS가 아니면 quality PASS 금지.
3. 성공 시 step 4 `completed`, phases/index.json phase를 `completed`로.

## 금지사항

- 무승인 `_gen-book-*.cjs`로 “정리”. 이유: 덮어쓰기.
- QA 실패를 숨기고 completed 처리. 이유: 증적 계약 위반.
