# RUN-TIMEOUT Operator Runbook

> 목적: **Client Timeout이 발생해도 DB Late Commit, Connection Leak, Worker/Context Leak이 없는지 증명**한다.

## 1. Architecture Gate 연결

- G40-C02 Timeout Late Commit / Connection 반환
- G40-C10 Context/ThreadLocal Leak
- G60-C06 ServiceId Timeout Chain
- G60-C07 Timeout Runtime Evidence
- P0-TMO-003 Timeout Worker Cancel Semantics

## 2. Formal Acceptance 사전조건

| 항목 | 조건 | 상태 |
|---|---|---|
| Test ServiceId | DB 변경 후 의도적으로 Online Timeout 초과 | 필수 |
| DB Query Timeout | TX보다 짧음 | 필수 |
| TX Timeout | Online보다 짧음 | 필수 |
| Online Timeout | Client보다 짧음 | 필수 |
| DB Before/After | 동일 Test Key로 검증 | 필수 |
| TX Log | GUID+ServiceId 추적 | 필수 |
| Hikari Metric | active/pending before/after | 필수 |
| Worker/Context | worker return + leak count | 필수 |

현재 Source 기본값 `3s / 5s / 5s`는 `DB < TX < Online` strict ordering을 충족하지 않는다. 이 상태의 실행은 **Diagnostic Run**으로만 기록하고 Formal PASS로 승격하지 않는다.

## 3. 권고 Acceptance Timeout 예

다음은 기존 Wave2A Validator를 통과시키기 위한 **시험용 예시**이지 최종 운영값이 아니다.

```text
DB Query = 3s
TX       = 5s
Online   = 7s
Client   = 9s 이상
```

최종 운영값은 ADR/Runtime 결과로 확정한다.

## 4. 실행 준비

```bash
export BASELINE=/path/to/wave3c
cd "$BASELINE"

# 실제값 작성
vi identity.json
vi request.json

python3 tools/nsight_runbook_validate.py validate-catalog runbooks/first-batch-run-catalog.json
python3 tools/nsight_run_automation.py prepare-bundle \
  --root ./evidence \
  --run-id RUN-TIMEOUT \
  --identity ./identity.json
```

## 5. Before Evidence

시험 Key 기준으로 DB/Pool/Thread 상태를 먼저 저장한다.

필수 결과 파일 목표:

```text
evidence/RUN-TIMEOUT/db/before-after.json
evidence/RUN-TIMEOUT/metrics/pool.json
evidence/RUN-TIMEOUT/metrics/thread.json
evidence/RUN-TIMEOUT/logs/transaction.json
```

DB Query는 DBA가 승인한 Read-only SQL을 사용한다.

## 6. Client Timeout Probe

```bash
export TIMEOUT_URL='https://<approved-host>/<timeout-path>'
export SERVICE_ID='<approved-timeout-service-id>'
export REQUEST_BODY_FILE="$PWD/request.json"
export CLIENT_TIMEOUT_SEC=9
export AUTH_BEARER='<token-if-required>'

./timeout/run-timeout-probe.sh ./evidence/RUN-TIMEOUT/logs
```

Probe는 Client 관점의 timeout만 기록한다. 이것만으로 PASS가 아니다.

## 7. Late Commit 관찰

Online Timeout 이후 **최소 2× Online Timeout**까지 기다린 뒤 동일 Test Key를 다시 조회한다.

확인해야 할 사실:

```text
rolled_back       = true
late_commit_count = 0
```

## 8. Pool / Worker / Context 확인

```text
pending_after      = 0
active_after       = active_before
worker_returned    = true
context_leak_count = 0
```

동일 Worker가 재사용될 때 이전 GUID/MDC/RequestAttributes가 보이면 `NO_GO`다.

## 9. Machine 판정

Evidence가 완성되면:

```bash
python3 tools/nsight_runtime_evidence.py \
  evaluate-bundle ./evidence/RUN-TIMEOUT
```

추가 Operator Go/No-Go 판정은 `facts.json`을 만든 후:

```bash
python3 tools/nsight_runbook_validate.py \
  go-nogo RUN-TIMEOUT ./facts.json
```

## 10. Hard NO-GO

다음 중 하나라도 발생하면 즉시 `NO_GO`다.

- rollback 미발생
- late commit 1건 이상
- TX Result가 ROLLBACK 아님
- Client가 Timeout으로 종료되지 않음
- Hikari pending 복구 안 됨
- active connection 원복 안 됨
- worker 미반환
- Context/MDC leak 발견

## 11. 실행 후 Result

`result.md`에 최소 다음을 기록한다.

| 항목 | 값 |
|---|---|
| RunId | RUN-TIMEOUT |
| GUID | |
| ServiceId | |
| DB/TX/Online/Client Timeout | |
| Client Result | |
| Rollback | |
| Late Commit Count | |
| Hikari Before/After | |
| Worker Returned | |
| Context Leak Count | |
| Machine Decision | |
| Human Approval | |
