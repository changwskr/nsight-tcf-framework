# RUN-SESSION Runbook

> 상태: **OPERATOR READY / PRODUCTION RESULT OPEN**

## 1. 목적

Session failover / re-authentication behavior according to approved Session ADR

## 2. 사전조건

- Session ADR approved
- actual session mode/config captured
- test users/session ids prepared
- L4 sticky and jvmRoute evidence
- approved JVM/VM failover command

## 3. 실행 명령 골격

```bash
export ENVIRONMENT=PERF
export EXECUTE=false
export OPERATOR_COMMAND='<approved environment-specific command>'
./98AA-tools/actions/run-session.sh

# 실제 실행 시에만
# export EXECUTE=true
# export APPROVAL_TOKEN='APPROVED:<change-id>'
```

## 4. 필수 증적

- `logs/session.log`
- `logs/l4.log`
- `logs/idp-auth.log`
- `metrics/session-count.json`
- `logs/failover.log`

## 5. Machine Gate

- `observed_behavior matches approved_session_policy`
- `no_cross_user_session_leak = true`
- `post_failover_login_path works`

## 6. Human / ADR Gate

- Session ADR approved
- Approve center-failure re-login vs preservation policy
- Review session object serialization/memory impact

## 7. Go / No-Go

- **GO**: Production Runtime 증적이며 모든 적용 가능한 Machine Gate와 필수 Human/ADR Gate를 만족한다.
- **NO-GO**: 데이터 정합성 훼손, 복구 실패, 보안키 불일치, 세션 오염, rollback/connection 반환 실패 등 안전성 실패가 발생한다.
- **REVIEW/INCOMPLETE**: 승인 임계치가 UNKNOWN이거나 사전 ADR/Production Evidence가 없다. 이를 임의의 PASS로 바꾸지 않는다.

## 8. 판정 주의

No universal survival rule is imposed. Same-center DeltaManager survival and center-failure re-login are working candidates only until ADR approval.

## 공통 Evidence Identity

모든 실행은 `RunId + Timestamp + Environment + Build/Commit + Config Version + ServiceId + GUID + Hostname + Tomcat JVM Instance`를 동일 Bundle에 기록한다. 실제 운영/성능환경 증적이 아니면 Runtime PASS로 승격하지 않는다.

## 안전 원칙

장애/배포/키회전 Run은 Wave 3B `operator-hook.sh`의 `EXECUTE=false` 기본값을 유지한다. 실제 실행은 승인된 변경번호를 포함한 Approval Token과 환경별 `OPERATOR_COMMAND`가 있을 때만 수행한다.

