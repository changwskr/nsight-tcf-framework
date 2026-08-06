# tcf-harness-자동코딩

NSIGHT **승인 Gate형** 요구분석·설계·구현 자동코딩 하네스.

## 빠른 시작

```bash
node scripts/new_run.cjs --id RUN-DEMO --workflow WF-ONLINE-INQUIRY --business AV --module av-service
node scripts/sync_contracts_from_ref.cjs
python harness/tools/validate_package.py
```

```text
tcf-harness-자동코딩 Run SAMPLE-AV-INQUIRY 다음 Stage 진행해줘. 사람 승인이면 멈춰.
```

## 구성

| 경로 | 역할 |
| --- | --- |
| [`참고소스/`](./참고소스/) | **계약 정본** (WF·Gate·Schema·API·DDL·validate) |
| [`harness/`](./harness/) | 참고소스 sync 사본 + `prompts/` |
| [`skills/`](./skills/) | WF agent별 Skill |
| [`scripts/`](./scripts/) | Run/Gate/승격/계약 sync |
| [`runs/`](./runs/) | Run 작업공간 |
| [`AGENTS.md`](./AGENTS.md) | 에이전트 불변 계약 |

## 계약 동기화

```bash
node scripts/sync_contracts_from_ref.cjs
python harness/tools/validate_package.py
```

참고소스 validation 기준: Schema 9 · Workflow 3 · Step 27 · Gate Rule 48 · API 17 · Table 35.

## 한 줄

**참고소스가 계약을 정의하고, 이 하네스가 Run·Agent·승인 Gate로 실행한다.**
