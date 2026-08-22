# P0 Closure Wave 3B — Runtime Execution Preparation Automation

> 상태: **AUTOMATION READY / PRODUCTION RUNTIME BLOCKED**  
> 목적: Wave 3의 12개 Runtime Evidence Run을 실제 성능/운영 유사환경에서 반복 실행할 수 있도록 부하발생, Metric 수집, Fault/HA Operator Hook, 결과 정규화 및 Evidence 평가 흐름을 실행 패키지로 구성한다.

## 1. 이번 Wave의 범위

이번 단계는 실제 NSIGHT 운영 환경에서 장애 또는 부하를 발생시키지 않는다. 현재 세션에는 L4/GSLB/Apache/Tomcat/Oracle/APM/IdP/KMS 및 승인된 성능환경이 연결되어 있지 않기 때문이다.

대신 다음 실행준비를 제공한다.

1. JMeter HTTP/JSON 부하계획 및 실행 Wrapper
2. Gatling 동등 시나리오 Reference
3. OS/JVM/Micrometer/Oracle 수집 스크립트
4. Timeout Client Probe 및 DB Before/After Template
5. N-1/Session/Center/Standard Rolling/JWT Rotation Operator Hook
6. JMeter JTL → `metrics/summary.json` 정규화
7. Run Bundle 자동 생성
8. 기존 `nsight_runtime_evidence.py`와 연계한 최종 Evidence 평가

## 2. 안전 원칙

```text
기본 실행 = DRY_RUN

PROD 실행
  ↓
EXECUTE=true
  +
APPROVAL_TOKEN=APPROVED:<change-id>
  +
운영자가 제공한 OPERATOR_COMMAND
  ↓
실행 가능
```

환경별 L4, Tomcat, GSLB, 배포, Key Rotation 명령은 패키지 안에 하드코딩하지 않는다. 실제 운영 명령은 해당 시스템 Owner가 승인한 명령만 주입한다.

## 3. 부하 실행 구조

```text
JMeter / Gatling
      ↓
HTTP/JSON + ServiceId + GUID
      ↓
Apache / Gateway
      ↓
Tomcat JVM
      ↓
TCF / Business Service
      ↓
DB / External

동시에
  OS / JVM / Micrometer / DB Metric 수집
      ↓
Runtime Evidence Bundle
      ↓
nsight_runtime_evidence.py
      ↓
PASS / FAIL / INCOMPLETE / SYNTHETIC_ONLY
```

JMeter는 stock component만 사용하는 property-driven Template로 제공한다. Gatling은 동일 workload의 Reference Template이며 승인된 Gatling Build 환경에서 별도 컴파일해야 한다.

## 4. JMeter 주요 Runtime Property

| Property | 의미 |
|---|---|
| `BASE_URL` | 대상 Gateway/Apache URL |
| `PATH` | HTTP Path |
| `SERVICE_ID` | 대상 ServiceId |
| `AUTH_BEARER` | JWT, 환경변수 주입 |
| `TARGET_TPS` | 목표 TPS |
| `THREADS` | JMeter Thread 상한 |
| `DURATION_SEC` | 본부하 시간 |
| `RAMP_SEC` | Ramp-up |
| `REQUEST_BODY_FILE` | 표준전문 JSON |

JTL 결과는 `nsight_run_automation.py ingest-jmeter`로 `sample_count`, `TPS`, `p95/p99`, `error_rate`, `timeout_rate`를 정규화한다. CPU/BusyThread/Hikari 값은 동일 RunId에서 수집된 Resource Metric을 결합해야 한다.

## 5. Runtime Metric 수집

| 영역 | 제공 도구 | 수집 내용 |
|---|---|---|
| Host | `collect-host.sh` | uptime, memory, vmstat, process, socket |
| JVM | `collect-jvm.sh` | VM flags, command line, heap, thread dump |
| Spring | `collect-micrometer.sh` | CPU, JVM, Tomcat Thread, Hikari raw metrics |
| Oracle | `oracle-session.sql` | Session/Transaction snapshot |
| Oracle | `oracle-slow-sql.sql` | Slow SQL diagnostic snapshot |

실제 APM/DBA 승인도구가 있는 경우 이 파일들을 대체할 수 있으나 최종 Evidence Manifest의 RunId/Timestamp/Host/JVM/Build/Config Identity는 유지해야 한다.

## 6. Failure / HA Run

Operator Wrapper 대상:

- RUN-N1
- RUN-SESSION
- RUN-CF
- RUN-ROLLING
- RUN-JWT-ROTATE

Wrapper는 구체적인 `systemctl`, `L4 disable`, `GSLB switch`, `KMS rotate` 명령을 포함하지 않는다. `OPERATOR_COMMAND`를 운영자가 넣고 기본값은 DRY_RUN이다.

## 7. Timeout Run

`run-timeout-probe.sh`는 승인된 테스트 전용 Timeout ServiceId를 Client 관점에서 호출하고 GUID, Curl 결과, 경과시간을 기록한다.

하지만 최종 RUN-TIMEOUT PASS는 이 Probe만으로 결정하지 않는다.

```text
Client Timeout
  + DB Before/After
  + TX Log
  + Hikari Before/After
  + Worker/Context Metric
  + 2×Timeout 이후 Late Commit 재확인
  ↓
RUN-TIMEOUT 판정
```

## 8. Local Preflight 결과

이번 세션에서 실행한 것은 Production Runtime이 아닌 Local/Synthetic Preflight이다.

| 검증 | 결과 |
|---|---|
| pytest | 11 PASS |
| Python compile | PASS |
| Shell `bash -n` | PASS |
| JMeter JMX XML parse | PASS |
| Synthetic JTL ingest | PASS |
| Runtime evaluator | `SYNTHETIC_ONLY` |
| N-1 Operator Hook | `DRY_RUN`, 명령 미실행 |
| JMeter binary | 현재 세션 미설치 |
| Gatling binary | 현재 세션 미설치 |
| SQL*Plus | 현재 세션 미설치 |
| Java / jcmd / curl | 사용 가능 |
| Production Run | 0 / 12 |

Synthetic Preflight에서 `RUN-P600`은 샘플 4건/1초의 소규모 입력이므로 `tps<600` Failure도 함께 기록되었지만 `SYNTHETIC_ONLY`이므로 어떤 경우에도 Runtime Approved로 승격되지 않는다.

## 9. 실제 실행 절차

```text
1. identity.json 작성
2. prepare-bundle
3. Config Snapshot 고정
4. Metric Collector 시작
5. JMeter/Gatling 실행
6. 필요 시 승인 Operator Hook 실행
7. Metric/Log/DB Evidence 종료수집
8. ingest-jmeter / Evidence 정규화
9. evaluate-bundle
10. Human approval.md
```

## 10. Gate 영향

```text
P0 Runtime Automation Tooling = CLOSED_STATIC
Production Runtime Evidence    = 0 / 12
G80                            = HOLD
HG90                           = HOLD
```

Wave 3B는 실행 준비의 정적 GAP을 닫았지만 Runtime P0 자체를 닫지 않는다.

## 11. 다음 단계

실제 성능/운영 유사환경이 제공되면 다음 순서로 실행한다.

```text
RUN-TIMEOUT
 → RUN-P600
 → RUN-P1200
 → RUN-S1800
 → RUN-HIKARI / RUN-SLOWSQL
 → RUN-N1
 → RUN-SESSION
 → RUN-CF
 → RUN-TRACE
 → RUN-ROLLING
 → RUN-JWT-ROTATE
 → G80 Re-Gate
```
