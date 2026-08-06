# Step 3: factcheck

## 읽어야 할 파일

- `/skills/book-factcheck/SKILL.md`
- `/skills/book-development/references/handoff-protocol.md`
- `phases/1-sample-chapter-01/draft-plan.md`
- `phases/1-sample-chapter-01/verification-report.md`
- `../ztcfbook/제01편/01-NSIGHT-TCF란-무엇인가.md`
- 장에 인용된 모든 출처 경로
- (필요 시) 관련 코드: `../tcf-core`, `../pdmp-service` 등 draft-plan에 명시된 것만

## 작업

`book-factcheck`로 사실 검증을 수행한다.

1. 장에서 식별자·경로·구조 주장을 추출한다.
2. 각 주장을 출처 파일 또는 코드 심볼에 대조한다.
3. 불일치·미지원·철회 필요 항목을 Findings로 정리하고 처분(fix/defer/ask-user)을 적는다.
4. `factcheck-report.md`를 작성한다.
5. High severity 미처분이면 `blocked` 또는 수정 후 재검증한다. 필요하면 step 2 범위를 벗어나지 않는 최소 수정을 허용하되 verification-report에 기록한다.

## Acceptance Criteria

```bash
Test-Path -LiteralPath 'phases/1-sample-chapter-01/factcheck-report.md'
Select-String -LiteralPath 'phases/1-sample-chapter-01/factcheck-report.md' -Pattern 'Decision'
```

## 검증 절차

1. AC 실행.
2. Decision이 PASS가 아니면 사용자 확인 또는 수정 루프.
3. 성공 시 step 3 `completed`.

## 금지사항

- 출처를 “추정으로 맞추기” 위해 식별자를 새로 발명. 이유: SoT 위반.
- factcheck를 건너뛰고 quality로 진행. 이유: 프로토콜 위반.
