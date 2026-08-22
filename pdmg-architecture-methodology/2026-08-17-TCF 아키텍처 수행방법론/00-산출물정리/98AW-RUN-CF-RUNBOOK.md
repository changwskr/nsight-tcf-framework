# RUN-CF Runbook

> 상태: **OPERATOR READY / PRODUCTION RESULT OPEN**

## 1. 목적

Center failover/failback and observed RTO/RPO

## 2. 사전조건

- RUN-N1 completed
- DR pair catalog approved
- GSLB/L4/DB failover runbook approved
- approved RTO and RPO inputs
- business validation team ready
- failback plan approved

## 3. 실행 명령 골격

```bash
export ENVIRONMENT=PERF
export EXECUTE=false
export OPERATOR_COMMAND='<approved environment-specific command>'
./98AA-tools/actions/run-center-failover.sh

# 실제 실행 시에만
# export EXECUTE=true
# export APPROVAL_TOKEN='APPROVED:<change-id>'
```

## 4. 필수 증적

- `logs/failover.log`
- `logs/failback.log`
- `metrics/rto-rpo.json`
- `logs/gslb-l4.log`
- `db/consistency.json`
- `logs/business-validation.log`

## 5. Machine Gate

- `observed_rto <= approved_rto`
- `observed_rpo <= approved_rpo`
- `business_validation = pass`
- `failback_validation = pass`

## 6. Human / ADR Gate

- RTO/RPO approved before pass
- Approve traffic reroute and failback timeline
- Approve data/session consistency result

## 7. Go / No-Go

- **GO**: Production Runtime 증적이며 모든 적용 가능한 Machine Gate와 필수 Human/ADR Gate를 만족한다.
- **NO-GO**: 데이터 정합성 훼손, 복구 실패, 보안키 불일치, 세션 오염, rollback/connection 반환 실패 등 안전성 실패가 발생한다.
- **REVIEW/INCOMPLETE**: 승인 임계치가 UNKNOWN이거나 사전 ADR/Production Evidence가 없다. 이를 임의의 PASS로 바꾸지 않는다.

## 8. 판정 주의

30 minutes is a strategy-level RTO reference for Online AP, not silently treated as the final system-specific approved RTO. RPO remains UNKNOWN until approved.

## 공통 Evidence Identity

모든 실행은 `RunId + Timestamp + Environment + Build/Commit + Config Version + ServiceId + GUID + Hostname + Tomcat JVM Instance`를 동일 Bundle에 기록한다. 실제 운영/성능환경 증적이 아니면 Runtime PASS로 승격하지 않는다.

## 안전 원칙

장애/배포/키회전 Run은 Wave 3B `operator-hook.sh`의 `EXECUTE=false` 기본값을 유지한다. 실제 실행은 승인된 변경번호를 포함한 Approval Token과 환경별 `OPERATOR_COMMAND`가 있을 때만 수행한다.

