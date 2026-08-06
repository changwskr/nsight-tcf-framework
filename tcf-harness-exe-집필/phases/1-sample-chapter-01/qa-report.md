# qa-report.md — 1-sample-chapter-01

## TOC/link check

| Check | Result |
| --- | --- |
| `00-목차.md`에 제1장 제목 (`제1장. NSIGHT TCF란 무엇인가`) | PASS |
| 이전: `../서문/00-서문.md` | PASS |
| 다음: `./02-전체-시스템-구조.md` | PASS |
| 출처 상대경로 (장 기준) | PASS (`zdocs-1` 포함) |
| `_gen-book-*.cjs` 실행 | N/A (비승인, 미실행) |

## Style checklist

| 항목 | Result |
| --- | --- |
| 절 ID 1.1~1.5 | PASS |
| 출처 색인 | PASS |
| 이전·다음 | PASS |
| 출처 없는 신규 식별자 창작 | PASS (없음) |
| 문체(설명체) | PASS |

## Cross-edition impact

- `ztcfbook-m` / `ztcfbook-h` 동기화 **미수행**. 후속 phase에서 Gap 문단·출처 경로 미러 권장.

## PASS

- 샘플 phase 목표(실행·증적·최소 정합 수정) 달성
- factcheck Decision PASS

## FAIL

- 없음

## Release recommendation

**조건부 승인**: base `ztcfbook` 제1장 수정분 커밋 가능.  
`-m`/`-h` 및 TOC/gen 스크립트 경로 정리는 별도 phase. 원격 push는 사용자 명시 요청 시.
