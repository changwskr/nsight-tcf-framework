# Step 1: approve-outline

## 읽어야 할 파일

- `/docs/UI_GUIDE.md`
- `/skills/book-development/references/chapter-template.md`
- `/skills/book-development/references/handoff-protocol.md`
- `phases/1-sample-chapter-01/analysis-summary.md`
- `../ztcfbook/제01편/01-NSIGHT-TCF란-무엇인가.md` (현재본, 있으면)

## 작업

1. `analysis-summary.md`를 바탕으로 **절별 outline** (제목, 핵심 메시지 1줄, 인용 출처)을 사용자에게 제시한다.
2. **사용자 명시 승인**을 받기 전에는 `draft-plan.md`를 “approved”로 표시하지 않는다.
3. 승인 후 `draft-plan.md`를 작성한다:
   - Approved outline
   - Section-to-source map
   - Write path: `../ztcfbook/제01편/01-NSIGHT-TCF란-무엇인가.md`
   - Validation commands
   - Rollback (git checkout -- path)
   - Fact-check / Quality 계획
4. 승인을 받을 수 없으면 step을 `blocked`로 두고 중단한다.

## Acceptance Criteria

```bash
Test-Path -LiteralPath 'phases/1-sample-chapter-01/draft-plan.md'
```

`draft-plan.md`에 사용자 승인 문구(일시·범위)와 프로토콜 필수 헤딩이 있어야 한다.

## 검증 절차

1. AC 실행.
2. 승인 기록 없으면 `blocked`.
3. 성공 시 step 1 `completed`.

## 금지사항

- 승인 전 장 파일 덮어쓰기. 이유: 워크플로 위반.
- 에디션을 `-m`/`-h`로 임의 변경. 이유: ADR-001.
