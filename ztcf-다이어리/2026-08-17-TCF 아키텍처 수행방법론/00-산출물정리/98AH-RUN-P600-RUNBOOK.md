# RUN-P600 Operator Runbook

> 목적: NSIGHT 일반 운영 Peak **600 TPS**에서 p95 3초 NFR와 Runtime 자원상태를 검증한다.

## 1. 진입조건

- `RUN-TIMEOUT`에서 P0 Safety Failure가 없어야 한다.
- 성능/운영 유사환경이 승인되어 있어야 한다.
- 실제 Artifact/Config/Host/JVM/DB Identity가 있어야 한다.
- Synthetic/Reference Evidence는 승인대상이 아니다.
- Error/Timeout Rate 최종 상한은 현재 Human/Open Gate다.

## 2. Target

```text
Target TPS : >= 600
p95        : <= 3.0 sec
```

Working Review Threshold:

```text
CPU                <= 70%
Tomcat Busy Thread <= 70%
Hikari Active      <= 80%
```

Working Threshold 초과는 자동 Architecture FAIL이 아니라 Review 대상이다.

## 3. 시험 전 고정값

다음은 Run 중 변경하지 않는다.

- Git Commit / Artifact Version
- Config Version
- Tomcat JVM/Heap/GC
- Tomcat maxThreads
- Hikari Pool
- Timeout Policy
- 대상 ServiceId/Request Body
- DB Target

## 4. Bundle 준비

```bash
python3 tools/nsight_run_automation.py prepare-bundle \
  --root ./evidence \
  --run-id RUN-P600 \
  --identity ./identity.json
```

## 5. Metric 수집 시작

실제 환경에서는 RunId/Timestamp를 맞춰 다음을 수집한다.

```bash
./collect/collect-host.sh   ./evidence/RUN-P600/metrics
./collect/collect-jvm.sh    <PID> ./evidence/RUN-P600/metrics
./collect/collect-micrometer.sh <ACTUATOR_URL> ./evidence/RUN-P600/metrics
```

DB Session/SQL은 DBA 승인 도구 또는 제공 SQL을 사용한다.

## 6. JMeter 실행

Wave3B 기본 Tool은 `DURATION_SEC=300`, `RAMP_SEC=30`을 사용한다. 이것은 Tool Default이며 최종 표준시간은 성능시험계획 승인으로 확정한다.

```bash
export JMETER_HOME=/opt/jmeter
export BASE_URL='https://<approved-perf-host>'
export PATH_PROP='/api/service'
export SERVICE_ID='<target-service-id>'
export REQUEST_BODY_FILE="$PWD/request.json"
export AUTH_BEARER='<token-if-required>'
export TARGET_TPS=600
export THREADS='<approved-thread-cap>'
export DURATION_SEC=300
export RAMP_SEC=30
export RESULT_JTL="$PWD/result-P600.jtl"

./load/jmeter/run-jmeter.sh
```

## 7. JTL 정규화

OS/JVM/Tomcat/Hikari 수집값을 `resource-metrics.json`으로 정규화한다.

필수 Key:

```json
{
  "cpu_pct": 0,
  "busy_thread_pct": 0,
  "hikari_active_pct": 0,
  "timeout_rate_pct": 0
}
```

그 다음:

```bash
python3 tools/nsight_run_automation.py ingest-jmeter \
  --bundle ./evidence/RUN-P600 \
  --jtl ./result-P600.jtl \
  --resource-metrics ./resource-metrics.json
```

## 8. Machine Gate

```bash
python3 tools/nsight_runtime_evidence.py \
  evaluate-bundle ./evidence/RUN-P600
```

Hard Gate:

```text
TPS >= 600
p95 <= 3.0s
Evidence complete
PRODUCTION_RUNTIME
synthetic=false
```

## 9. Human/Open Gate

다음 값은 반드시 기록하지만 최종 승인 상한은 아직 Architecture Open Issue다.

- Error Rate
- Timeout Rate
- CPU Peak/Average
- Busy Thread Peak/Average
- Hikari Active/Pending
- GC Pause
- DB Session/Wait

Human Gate가 미승인인 경우 Machine Hard Gate가 PASS여도 최종 판정은 `CONDITIONAL_REVIEW`다.

## 10. P1200 진행조건

다음을 모두 만족해야 한다.

1. TPS 600 이상
2. p95 3초 이하
3. 데이터 정합성 이상 없음
4. Timeout/Late Commit P0 없음
5. Resource 병목이 즉시 P0로 판단되지 않음
6. Error/Timeout Rate에 대한 Human Review 완료
7. 담당자 `approval.md` 기록
