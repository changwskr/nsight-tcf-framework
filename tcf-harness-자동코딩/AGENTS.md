# NSIGHT 자동코딩 하네스 (승인 Gate형)

## Scope

이 하네스는 LLM으로 코드를 “한 번에 뽑는” 도구가 **아니다**.  
요구분석 → 설계 → 구현 → Build/Test → 추적성·Drift → **사람 승인**까지를  
`runs/{runId}/` 작업공간에서 통제하는 **개발 실행 하네스**다.

SoT(우선순위):

1. [`AGENTS.md`](./AGENTS.md) (본 파일)
2. 현재 Run의 `runs/{runId}/run.yaml` + stage Manifest
3. **[`참고소스/`](./참고소스/)** — 계약 정본 (Workflow·Gate·Schema·API·DDL)
4. [`harness/`](./harness/) — 참고소스 sync 사본 + `prompts/` (에이전트용)
5. [`NSIGHT-자동-하네스-상세설계서.md`](./NSIGHT-자동-하네스-상세설계서.md) · 요구사항 정의서
6. 실제 NSIGHT 소스 (`../tcf-*`, `../*-service`, …)

계약 동기화:

```bash
node scripts/sync_contracts_from_ref.cjs
python harness/tools/validate_package.py
```

## 절대 원칙

1. **Agent는 Run 상태·Gate 결과를 직접 PASS 처리하지 않는다.** Orchestrator 절차(`scripts` + 사용자 승인)만 전이한다.
2. **승인되지 않은 Artifact는 다음 Stage 입력으로 쓰지 않는다.**
3. **`00-IN`, `10-BASELINE`, `60-EVIDENCE`는 불변/Append-only.** 덮어쓰지 않는다.
4. 기존 소스 변경은 **Run 전용 worktree/patch**만. 기준 Branch 직접 수정·Force Push·자동 Merge 금지.
5. 운영 DB DDL 실행·운영 배포·승인 없는 Git Push·OM 운영 반영 금지.
6. 테스트 삭제·Skip·조건 완화로 “통과” 위장 금지.
7. Secret·개인정보 원문을 산출물/로그에 남기지 않는다.
8. 확인된 사실 / 사용자 확정 / 가정 / 설계제안을 **분리** 기록한다.
9. Manifest에 없는 파일은 정식 산출물이 아니다.
10. Evidence 없이 Gate PASS 금지.

## Golden Path (참고소스 WF-ONLINE-INQUIRY)

```text
S00-INPUT      HG-00
S10-BASELINE   HG-10
S20-ANALYSIS   HG-20  (BA)     requirement + domain
S30-DESIGN     HG-30  (AA/DA/SEC)  design + adr
S40-PLAN       —
S50-IMPLEMENT  HG-40           code + sql + test-gen
S60-BUILD      HG-50
S70-TEST       HG-60
S80-VERIFY     HG-70  (HUMAN)  quality + security
S85-TRACE      HG-80  (AA)
S90-FINAL      HG-90  (AA+QA)
```

## Agent ↔ Skill (WF agent 이름)

| WF agent | Skill |
| --- | --- |
| intake-agent | `intake-agent` |
| baseline-agent | `baseline-agent` |
| requirement-agent | `requirement-agent` |
| domain-agent | `domain-agent` |
| design-agent | `design-agent` |
| adr-agent | `adr-agent` |
| planning-agent | `planning-agent` |
| code-agent | `code-agent` |
| sql-agent | `sql-agent` |
| test-agent | `test-agent` |
| build-agent | `build-agent` |
| test-execution-agent | `test-execution-agent` |
| quality-agent / security-agent | `quality-agent` / `security-agent` |
| trace-agent | `trace-agent` |
| packaging-agent | `packaging-agent` |
| (통제) | `harness-orchestrator` |

레거시 Stage ID(`S120-TRACE` 등)는 `promote_stage.cjs`가 참고소스 ID로 정규화한다.

## 스크립트

```bash
node scripts/new_run.cjs --id RUN-... --workflow WF-ONLINE-INQUIRY
node scripts/validate_run.cjs --id RUN-...
node scripts/sync_contracts_from_ref.cjs
python harness/tools/validate_package.py
node scripts/record_gate.cjs --id RUN-... --gate HG-80 --decision PASS
node scripts/promote_stage.cjs --id RUN-...
```

## Stop conditions

- HUMAN Gate → 초안만 작성 후 승인 대기
- 미확정 Critical Gap → 구현 금지
- 사실 불명 식별자 발명 금지
