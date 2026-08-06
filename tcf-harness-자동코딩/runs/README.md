# Runs

각 Run은 독립 작업공간이다.

```text
runs/{runId}/
  run.yaml
  TASK.md
  00-IN … 99-ARCHIVE
```

## 생성

```bash
node scripts/new_run.cjs --id RUN-20260806-0001 --workflow WF-ONLINE-INQUIRY --business AV --module av-service
node scripts/validate_run.cjs --id RUN-20260806-0001
```

## 샘플

- `SAMPLE-AV-INQUIRY` — 온라인 조회 Golden Path 연습용
