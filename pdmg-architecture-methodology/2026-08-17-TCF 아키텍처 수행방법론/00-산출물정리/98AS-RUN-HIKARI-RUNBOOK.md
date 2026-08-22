# RUN-HIKARI Runbook

> 상태: **OPERATOR READY / PRODUCTION RESULT OPEN**

## 1. 목적

Hikari pool pressure / DB session ceiling / backpressure and recovery

## 2. 사전조건

- approved DB session ceiling supplied
- actual Hikari maximumPoolSize captured
- runtime identity fixed
- Oracle metric access
- approved pressure scenario

## 3. 실행 명령 골격

```bash
# 예시: 실제 환경값/승인값으로 치환
export ENVIRONMENT=PERF
export EXECUTE=false
# collector + load/fault scenario 실행 후 evidence bundle에 적재
```

## 4. 필수 증적

- `metrics/pool.json`
- `metrics/db-session.json`
- `metrics/connection-acquire.json`
- `logs/run.log`

## 5. Machine Gate

- `connection_leak = 0`
- `pool_active returns to baseline after pressure`
- `persistent_pending_after_recovery = 0`
- `db_session <= approved_db_session_ceiling`

## 6. Human / ADR Gate

- Approve final Hikari pool size
- Approve Pending/connection acquire operating threshold
- Confirm DB DBA session ceiling

## 7. Go / No-Go

- **GO**: Production Runtime 증적이며 모든 적용 가능한 Machine Gate와 필수 Human/ADR Gate를 만족한다.
- **NO-GO**: 데이터 정합성 훼손, 복구 실패, 보안키 불일치, 세션 오염, rollback/connection 반환 실패 등 안전성 실패가 발생한다.
- **REVIEW/INCOMPLETE**: 승인 임계치가 UNKNOWN이거나 사전 ADR/Production Evidence가 없다. 이를 임의의 PASS로 바꾸지 않는다.

## 8. 판정 주의

70~80% is a working management range, not a final approved hard limit. Pressure test may intentionally create Pending; acceptance focuses on controlled backpressure and recovery.

## 공통 Evidence Identity

모든 실행은 `RunId + Timestamp + Environment + Build/Commit + Config Version + ServiceId + GUID + Hostname + Tomcat JVM Instance`를 동일 Bundle에 기록한다. 실제 운영/성능환경 증적이 아니면 Runtime PASS로 승격하지 않는다.

## 안전 원칙

장애/배포/키회전 Run은 Wave 3B `operator-hook.sh`의 `EXECUTE=false` 기본값을 유지한다. 실제 실행은 승인된 변경번호를 포함한 Approval Token과 환경별 `OPERATOR_COMMAND`가 있을 때만 수행한다.

