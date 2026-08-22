# P0 Closure Wave 3 — Runtime Evidence Execution

> 상태: **HARNESS READY / PRODUCTION RUNTIME BLOCKED**  
> 목적: G80/HG90의 Runtime Hard Blocker를 실제 실행 가능한 Evidence Bundle로 전환하고, 현재 실행환경에서 증명 가능한 Local Preflight와 실제 Production Runtime Evidence를 엄격히 분리한다.

## 1. 결론

이번 Wave에서 **12개 Mandatory Run에 대한 실행 Harness, Manifest Schema, Evidence 최소세트, 자동 판정기, 실행 Bundle Template**을 작성하고 로컬 검증을 수행했다.

그러나 현재 세션에는 NSIGHT 운영/성능시험 환경, 실제 L4/Apache/Tomcat/Oracle/APM/IdP/KMS가 연결되어 있지 않으므로 다음은 실행하지 않았다.

- RUN-P600 / RUN-P1200 / RUN-S1800
- RUN-HIKARI / RUN-SLOWSQL
- RUN-N1 / RUN-SESSION / RUN-CF
- RUN-TRACE / RUN-ROLLING / RUN-JWT-ROTATE
- 실제 DB를 사용하는 RUN-TIMEOUT Fault Injection

따라서 **Production Runtime Approved Run = 0/12**이며 G80/HG90은 HOLD를 유지한다.

## 2. 이번에 실제 실행한 항목

| 항목 | 결과 | 의미 |
|---|---|---|
| Harness pytest | 11 PASS | Parser/Validator/Evaluator 기능 확인 |
| Run Manifest JSON Schema | PASS | Runtime Evidence Identity 형식 확인 |
| 12개 Run Template 생성 | PASS | 실행 Bundle 준비 |
| Synthetic RUN-P600 | `SYNTHETIC_ONLY` | PASS 수치여도 Runtime 승인 금지 확인 |
| Synthetic RUN-TIMEOUT | `SYNTHETIC_ONLY` | 가짜 증적의 Runtime 승격 금지 확인 |
| Java Timeout Policy Preflight | PASS | 현재 기본 3/5/5가 strict ordering 위반임을 실제 Java 21에서 확인 |
| Production Runtime Run | 0건 | 실제 Runtime 연결 부재 |

## 3. Mandatory Runtime Run

```text
RUN-TIMEOUT
   ↓
RUN-P600
   ↓
RUN-P1200
   ↓
RUN-S1800
   ↓
RUN-HIKARI / RUN-SLOWSQL
   ↓
RUN-N1
   ↓
RUN-SESSION
   ↓
RUN-CF
   ↓
RUN-TRACE
   ↓
RUN-ROLLING
   ↓
RUN-JWT-ROTATE
   ↓
G80 Re-Gate
   ↓
HG90 Re-Submit
```

## 4. Evidence Identity

모든 실행 결과는 다음 필드를 가져야 한다.

```text
RunId
Timestamp
Environment
Evidence Class
Synthetic Flag
Git Commit
Artifact Version
Config Version
ServiceId
GUID
Hostname
Tomcat JVM Instance
DB Target
```

`hostname=UNKNOWN`, 실제 commit 미확정, synthetic evidence는 Production Runtime PASS로 승격할 수 없다.

## 5. Runtime Evidence 판정 규칙

```text
Evidence Bundle
    ↓
Manifest Identity Validation
    ↓
Required Evidence Presence
    ↓
Run-specific Fact Evaluation
    ↓
Synthetic / Reference Guard
    ↓
PASS / FAIL / INCOMPLETE / INVALID
```

Runtime 승인 조건:

```text
status == PASS
AND evidence_class == PRODUCTION_RUNTIME
AND synthetic == false
```

## 6. RUN-TIMEOUT

필수 증적:

- `db/before-after.json`
- `logs/transaction.json`
- `metrics/pool.json`
- `metrics/thread.json`

PASS 조건:

```text
rolled_back == true
late_commit_count == 0
tx_result == ROLLBACK
client_result == TIMEOUT
Hikari pending_after == 0
Hikari active_after == active_before
worker_returned == true
context_leak_count == 0
```

### Local Policy Preflight

현재 Source 기본값:

```text
DB Query = 3 sec
TX       = 5 sec
Online   = 5 sec
```

Wave2A `TimeoutPolicyValidator`를 Java 21로 컴파일/실행한 결과:

```text
3 < 5 < 5  → FAIL : TX_NOT_LT_ONLINE
3 < 5 < 7  → PASS
```

이 결과는 **정책 Preflight**이며 DB rollback/late commit을 증명하는 Runtime Evidence는 아니다.

## 7. Capacity Run

`RUN-P600`/`RUN-P1200`은 최소 다음 정보를 요구한다.

- TPS
- p95
- errorRate
- timeoutRate
- CPU
- Busy Thread
- Hikari Active
- 동일 RunId Log

p95 3초 기준은 기존 NFR이므로 자동 검사한다. Error/Timeout의 최종 허용률은 아직 승인된 임계치가 없으므로 Harness에 임의 숫자를 하드코딩하지 않았다.

`RUN-S1800`은 정상 SLA 승인이 아니라 **포화점/실패양상 확인 + Data Integrity 유지**를 검증한다.

## 8. Template 구조

```text
98U-runtime-template/<RUN_ID>/
├─ run-manifest.json
├─ REQUIRED-EVIDENCE.txt
├─ config-snapshot/
├─ metrics/
├─ logs/
├─ db/
├─ screenshots/
├─ result.md
└─ approval.md
```

## 9. 현재 Gate 영향

| Gate | 상태 | Wave 3 영향 |
|---|---|---|
| G60 | CONDITIONAL PASS | Runtime Harness 준비, 실제 Capacity Run 미실행 |
| G70 | CONDITIONAL PASS | HA/DR/Session/Rolling Run 미실행 |
| G80 | HOLD | Runtime Evidence 0/12 |
| HG90 | HOLD | G80 및 P0 Runtime 미해소 |

## 10. 다음 실행 전제

실제 성능/운영 환경에서 최소 다음 접근이 필요하다.

1. 배포 Artifact/Commit/Config Version
2. 실제 Hostname/Tomcat JVM 식별
3. 부하발생 도구 또는 테스트 Driver
4. APM/JVM/Thread/Hikari Metric 접근
5. Oracle Session/SQL/TX 확인 권한
6. L4/GSLB/Apache Route Log
7. Session/IdP 로그
8. CI/CD Deploy/Health Log
9. KMS/JWKS/Key Audit Log

이 항목이 확보되면 `98U-tools/nsight_runtime_evidence.py evaluate-bundle <RUN_DIR>`로 각 Run을 자동 판정한다.
