# RUN-ROLLING Runbook

> 상태: **OPERATOR READY / PRODUCTION RESULT OPEN**

## 1. 목적

Rolling deployment residual capacity / health / rollback safety

## 2. 사전조건

- RUN-N1 completed
- artifact/config/db versions fixed
- rollback artifact available
- approved drain/deploy/rejoin commands
- ServiceId smoke test list prepared

## 3. 실행 명령 골격

```bash
export ENVIRONMENT=PERF
export EXECUTE=false
export OPERATOR_COMMAND='<approved environment-specific command>'
./98AA-tools/actions/run-rolling.sh

# 실제 실행 시에만
# export EXECUTE=true
# export APPROVAL_TOKEN='APPROVED:<change-id>'
```

## 4. 필수 증적

- `logs/deploy.log`
- `logs/health.log`
- `metrics/summary.json`
- `logs/smoke-test.json`
- `logs/version-trace.json`
- `logs/rollback-test.json`

## 5. Machine Gate

- `actual_tps >= 1200 during planned peak test`
- `p95_sec <= 3 during planned peak test`
- `drained_node_receives_new_traffic = false`
- `serviceid_smoke_test = pass`
- `rejoined_node_health = healthy`

## 6. Human / ADR Gate

- Approve residual-capacity safety factor (currently OPEN)
- Approve DB/config backward compatibility
- Approve rollback or roll-forward strategy

## 7. Go / No-Go

- **GO**: Production Runtime 증적이며 모든 적용 가능한 Machine Gate와 필수 Human/ADR Gate를 만족한다.
- **NO-GO**: 데이터 정합성 훼손, 복구 실패, 보안키 불일치, 세션 오염, rollback/connection 반환 실패 등 안전성 실패가 발생한다.
- **REVIEW/INCOMPLETE**: 승인 임계치가 UNKNOWN이거나 사전 ADR/Production Evidence가 없다. 이를 임의의 PASS로 바꾸지 않는다.

## 8. 판정 주의

If the run is executed below design peak, it is diagnostic only and cannot close residual-capacity approval.

## 공통 Evidence Identity

모든 실행은 `RunId + Timestamp + Environment + Build/Commit + Config Version + ServiceId + GUID + Hostname + Tomcat JVM Instance`를 동일 Bundle에 기록한다. 실제 운영/성능환경 증적이 아니면 Runtime PASS로 승격하지 않는다.

## 안전 원칙

장애/배포/키회전 Run은 Wave 3B `operator-hook.sh`의 `EXECUTE=false` 기본값을 유지한다. 실제 실행은 승인된 변경번호를 포함한 Approval Token과 환경별 `OPERATOR_COMMAND`가 있을 때만 수행한다.

