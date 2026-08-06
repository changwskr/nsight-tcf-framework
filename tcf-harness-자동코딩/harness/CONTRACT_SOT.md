# Contract SoT

`harness/`의 workflows · gate-rules · schemas · workspace · api · database · architecture · tools 는  
[`참고소스/`](../참고소스/) 를 정본으로 동기화한다.

```bash
node scripts/sync_contracts_from_ref.cjs
pip install jsonschema pyyaml
python harness/tools/validate_package.py
```

- 계약 수정: **참고소스** → sync
- 에이전트 전용: `harness/prompts/`, `skills/`, `scripts/`, `runs/` (sync 대상 아님)
- 플랫폼 Java/Oracle 구현은 참고소스 architecture·database·api 트랙 (별도)

마지막 동기화: 2026-08-06
