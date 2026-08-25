# NSIGHT Wave3C First Runtime Batch

실행 순서:

```text
RUN-TIMEOUT → RUN-P600 → RUN-P1200
```

각 Run 폴더의 `run-manifest.json`은 `UNKNOWN`을 실제값으로 교체하거나 `nsight_run_automation.py prepare-bundle`로 새 Evidence Root를 생성한다.

`facts.json`은 기계판정 입력용 Worksheet이며 실제 Runtime Evidence에서 추출한 값만 기록한다.

Production Runtime 승인 후보 조건:

```text
evidence_class = PRODUCTION_RUNTIME
synthetic = false
machine hard gate = PASS
human gates = approved
```
