# Step 0: research-sources

## 읽어야 할 파일

- `/AGENTS.md`
- `/CLAUDE.md`
- `/docs/ARCHITECTURE.md`
- `/skills/book-development/references/framework-source-map.md`
- `/skills/book-development/references/handoff-protocol.md`
- `../ztcfbook/00-목차.md`
- `../ztcfbook/README.md`
- `../ztcfbook/_gen-book-chapters.cjs` (제1장 섹션·sources 배열만)
- `../znsight-man/03-TCF-개발원칙.md`
- `../zman/05-TCF처리구조.md`
- `../znsight-man/22-Online-Endpoint-기준.md` (존재 확인)
- `../znsight-man/05-개발자-역할과-책임.md` (존재 확인)

## 작업

`book-research` 스킬로 제1장 「NSIGHT-TCF란 무엇인가」 출처를 조사한다.

1. 에디션 대상은 `ztcfbook` (변경 금지).
2. 목차·`_gen-book-chapters.cjs`에서 제1장 절·sources 목록을 추출한다.
3. 각 source 경로 존재 여부를 확인하고, 없거나 이름이 다르면 후보 경로를 찾되 **확정은 사용자에게 묻는다**.
4. 설계서 vs 코드 Gap이 보이면 `../zman/00-설계서-코드베이스-대조표.md`를 읽고 메모한다.
5. phase 디렉터리에 `analysis-summary.md`를 작성한다 (프로토콜 필수 헤딩 준수).

이 step에서는 장 본문을 수정하지 않는다.

## Acceptance Criteria

```bash
# PowerShell (하네스 루트 기준)
Test-Path -LiteralPath 'phases/1-sample-chapter-01/analysis-summary.md'
Test-Path -LiteralPath '../ztcfbook/00-목차.md'
```

`analysis-summary.md`에 Scope, Edition target, Source inventory, Conflicts, Open questions, Acceptance evidence 헤딩이 있어야 한다.

## 검증 절차

1. AC를 실행한다.
2. 출처 inventory에 존재하지 않는 경로가 “확정 출처”로 적혀 있으면 `blocked`.
3. 성공 시 index.json step 0 → `completed` + summary.

## 금지사항

- 장 Markdown 본문 수정. 이유: 아직 outline 미승인.
- `_gen-book-*.cjs` 실행. 이유: 덮어쓰기 위험.
- ServiceId/포트 등 식별자 창작. 이유: SoT 위반.
