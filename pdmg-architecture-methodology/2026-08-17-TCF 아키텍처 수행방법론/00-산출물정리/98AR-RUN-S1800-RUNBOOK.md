# RUN-S1800 Runbook

> 상태: **OPERATOR READY / PRODUCTION RESULT OPEN**

## 1. 목적

Stress 1,800 TPS and saturation/recovery characterization

## 2. 사전조건

- RUN-P1200 completed
- approved performance environment
- runtime identity fixed
- data reset/reconciliation plan
- JMeter or approved load tool

## 3. 실행 명령 골격

```bash
# 예시: 실제 환경값/승인값으로 치환
export ENVIRONMENT=PERF
export EXECUTE=false
# collector + load/fault scenario 실행 후 evidence bundle에 적재
```

## 4. 필수 증적

- `metrics/summary.json`
- `metrics/resource.json`
- `logs/run.log`
- `db/integrity.json`
- `screenshots/saturation-chart.png`

## 5. Machine Gate

- `actual_tps >= 1800 target attempt`
- `data_integrity = true`
- `post_test_recovery = true`

## 6. Human / ADR Gate

- Confirm whether p95<=3s is a hard stress acceptance or observation-only NFR
- Approve saturation point and degradation pattern
- Approve error/timeout tolerance (currently UNKNOWN)

## 7. Go / No-Go

- **GO**: Production Runtime 증적이며 모든 적용 가능한 Machine Gate와 필수 Human/ADR Gate를 만족한다.
- **NO-GO**: 데이터 정합성 훼손, 복구 실패, 보안키 불일치, 세션 오염, rollback/connection 반환 실패 등 안전성 실패가 발생한다.
- **REVIEW/INCOMPLETE**: 승인 임계치가 UNKNOWN이거나 사전 ADR/Production Evidence가 없다. 이를 임의의 PASS로 바꾸지 않는다.

## 8. 판정 주의

Source defines 1,800 TPS as Stress and asks for saturation/degradation characterization. p95<=3s exists as general NFR, but Wave3D does not silently convert it into a hard stress gate.

## 공통 Evidence Identity

모든 실행은 `RunId + Timestamp + Environment + Build/Commit + Config Version + ServiceId + GUID + Hostname + Tomcat JVM Instance`를 동일 Bundle에 기록한다. 실제 운영/성능환경 증적이 아니면 Runtime PASS로 승격하지 않는다.

## 안전 원칙

장애/배포/키회전 Run은 Wave 3B `operator-hook.sh`의 `EXECUTE=false` 기본값을 유지한다. 실제 실행은 승인된 변경번호를 포함한 Approval Token과 환경별 `OPERATOR_COMMAND`가 있을 때만 수행한다.

