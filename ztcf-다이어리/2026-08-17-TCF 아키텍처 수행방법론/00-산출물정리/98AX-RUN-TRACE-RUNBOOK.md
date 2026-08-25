# RUN-TRACE Runbook

> 상태: **OPERATOR READY / PRODUCTION RESULT OPEN**

## 1. 목적

GUID + ServiceId end-to-end and reverse traceability

## 2. 사전조건

- logging/trace collection enabled
- test ServiceId selected
- GUID injection/capture ready
- Apache/Tomcat/TCF/SQL/external log access

## 3. 실행 명령 골격

```bash
# 예시: 실제 환경값/승인값으로 치환
export ENVIRONMENT=PERF
export EXECUTE=false
# collector + load/fault scenario 실행 후 evidence bundle에 적재
```

## 4. 필수 증적

- `logs/GUID-trace.json`
- `logs/ServiceId-trace.json`
- `logs/apache.log`
- `logs/tomcat.log`
- `logs/tcf.log`
- `logs/sql-or-external.json`

## 5. Machine Gate

- `same_guid_across_required_hops = true`
- `same_serviceid_across_required_hops = true`
- `reverse_trace_to_ingress = true`
- `worker_context_leak = 0`

## 6. Human / ADR Gate

- Approve required hop coverage for the selected ServiceId
- Confirm masking/security policy compliance

## 7. Go / No-Go

- **GO**: Production Runtime 증적이며 모든 적용 가능한 Machine Gate와 필수 Human/ADR Gate를 만족한다.
- **NO-GO**: 데이터 정합성 훼손, 복구 실패, 보안키 불일치, 세션 오염, rollback/connection 반환 실패 등 안전성 실패가 발생한다.
- **REVIEW/INCOMPLETE**: 승인 임계치가 UNKNOWN이거나 사전 ADR/Production Evidence가 없다. 이를 임의의 PASS로 바꾸지 않는다.

## 8. 판정 주의

At least one DB-backed and one external-integration ServiceId should be sampled when available; this is a proposed coverage expansion, not a claim that evidence already exists.

## 공통 Evidence Identity

모든 실행은 `RunId + Timestamp + Environment + Build/Commit + Config Version + ServiceId + GUID + Hostname + Tomcat JVM Instance`를 동일 Bundle에 기록한다. 실제 운영/성능환경 증적이 아니면 Runtime PASS로 승격하지 않는다.

## 안전 원칙

장애/배포/키회전 Run은 Wave 3B `operator-hook.sh`의 `EXECUTE=false` 기본값을 유지한다. 실제 실행은 승인된 변경번호를 포함한 Approval Token과 환경별 `OPERATOR_COMMAND`가 있을 때만 수행한다.

