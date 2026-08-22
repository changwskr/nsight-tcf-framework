# RUN-P1200 Operator Runbook

> 목적: NSIGHT **Design Peak 1,200 TPS**를 실제 Runtime Evidence로 검증한다.

## 1. 진입조건

`RUN-P1200`은 `RUN-P600`보다 먼저 실행하지 않는다.

필수 조건:

- RUN-P600 진행승인
- 동일 Artifact/Config 또는 변경사항 명시
- P600에서 발견한 P0 병목 없음
- 부하발생기가 1,200 TPS를 실제 생성할 수 있음
- Metric/Log 수집이 부하 자체를 방해하지 않음
- DB Session/Hikari/Tomcat 상한 관찰 가능

## 2. Target

```text
Target TPS : >= 1,200
p95        : <= 3.0 sec
```

Working Review Threshold:

```text
CPU                <= 70%
Tomcat Busy Thread <= 70%
Hikari Active      <= 80%
```

최종 CPU/Thread/Pool 승인 상한은 Runtime/ADR에서 확정한다.

## 3. P600과 비교해야 할 동일성

| 항목 | 원칙 |
|---|---|
| Git Commit | 동일 또는 변경사유 명시 |
| Artifact | 동일 또는 변경사유 명시 |
| Config | 동일 또는 Version Diff 첨부 |
| ServiceId | 동일 대표 Transaction 권장 |
| Request Mix | 동일하거나 Mix Version 기록 |
| DB Target | 동일 계열/동등 환경 |
| JVM/Tomcat/Hikari | 변경 시 반드시 비교표 작성 |

## 4. Bundle 준비

```bash
python3 tools/nsight_run_automation.py prepare-bundle \
  --root ./evidence \
  --run-id RUN-P1200 \
  --identity ./identity.json
```

## 5. 부하 실행

```bash
export JMETER_HOME=/opt/jmeter
export BASE_URL='https://<approved-perf-host>'
export PATH_PROP='/api/service'
export SERVICE_ID='<target-service-id>'
export REQUEST_BODY_FILE="$PWD/request.json"
export AUTH_BEARER='<token-if-required>'
export TARGET_TPS=1200
export THREADS='<approved-thread-cap>'
export DURATION_SEC=300
export RAMP_SEC=30
export RESULT_JTL="$PWD/result-P1200.jtl"

./load/jmeter/run-jmeter.sh
```

## 6. Evidence 수집

최소 Evidence는 `metrics/summary.json`, `logs/run.log`이지만 Architecture 승인에는 다음도 함께 보관한다.

- Host CPU/MEM/Load
- JVM Heap/GC
- Tomcat Busy/Max Thread
- Hikari Active/Idle/Pending
- DB Session/Wait/Slow SQL
- 동일 GUID+ServiceId Transaction Log Sample
- Config Snapshot

## 7. 정규화/판정

```bash
python3 tools/nsight_run_automation.py ingest-jmeter \
  --bundle ./evidence/RUN-P1200 \
  --jtl ./result-P1200.jtl \
  --resource-metrics ./resource-metrics.json

python3 tools/nsight_runtime_evidence.py \
  evaluate-bundle ./evidence/RUN-P1200
```

## 8. Hard NO-GO

- 실제 TPS < 1,200
- p95 > 3.0초
- Evidence Identity 불명
- 필수 Evidence 누락
- Production Runtime이 아닌데 PASS로 승격 시도
- 데이터 정합성/Timeout Safety 이상
- P600 대비 명백한 치명적 회귀

## 9. Conditional Review

다음은 Machine Hard Gate가 통과해도 Human Review가 필요하다.

- Error/Timeout Rate 상한 미승인
- CPU > 70% Working Threshold
- Busy Thread > 70%
- Hikari Active > 80%
- GC Pause/DB Wait 상승
- P600 대비 지연시간 비선형 증가

## 10. P1200 종료 산출

```text
result.md
approval.md
metrics/summary.json
logs/run.log
config-snapshot/*
P600-vs-P1200 comparison
```

P1200 승인 이후에 `RUN-S1800`, `RUN-HIKARI`, `RUN-SLOWSQL`, `RUN-N1`로 진행한다.
