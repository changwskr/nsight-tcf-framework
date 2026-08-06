# Step 2: draft-chapter

## 읽어야 할 파일

- `/docs/UI_GUIDE.md`
- `/skills/book-draft/SKILL.md`
- `/skills/book-development/references/chapter-template.md`
- `phases/1-sample-chapter-01/draft-plan.md`
- `phases/1-sample-chapter-01/analysis-summary.md`
- draft-plan에 나열된 **모든** 출처 파일
- `../ztcfbook/제01편/01-NSIGHT-TCF란-무엇인가.md` (현재본)
- 이전·다음 장 파일 (네비게이션 링크용)

## 작업

`book-draft`로 승인된 outline만 반영한다.

1. `draft-plan.md`의 section-to-source map만 사용한다.
2. 대상 경로에 장을 작성/갱신한다: `../ztcfbook/제01편/01-NSIGHT-TCF란-무엇인가.md`
3. 장 요약 · 이전/다음 · 출처 색인을 포함한다.
4. `phases/1-sample-chapter-01/verification-report.md`에 변경 경로·명령·exit code를 기록한다.
5. 범위 밖 장·목차 전면 개편·생성기 실행은 하지 않는다.

## Acceptance Criteria

```bash
Test-Path -LiteralPath '../ztcfbook/제01편/01-NSIGHT-TCF란-무엇인가.md'
Test-Path -LiteralPath 'phases/1-sample-chapter-01/verification-report.md'
Select-String -LiteralPath '../ztcfbook/제01편/01-NSIGHT-TCF란-무엇인가.md' -Pattern '출처 색인'
```

## 검증 절차

1. AC 실행.
2. draft-plan에 없는 절을 추가했다면 되돌려 `error` 또는 재작성.
3. 성공 시 step 2 `completed`.

## 금지사항

- `_gen-book-*.cjs` 실행. 이유: 본문 덮어쓰기.
- 출처에 없는 API/식별자 서술. 이유: SoT 위반.
- `ztcfbook-m`/`ztcfbook-h` 동시 수정. 이유: 본 phase 범위 밖 (후속 phase).
