# P0 Closure Wave 3C — First Runtime Batch Runbook

> 상태: **OPERATOR-READY / PRODUCTION EXECUTION OPEN**  
> 대상: `RUN-TIMEOUT → RUN-P600 → RUN-P1200`  
> 목적: Wave3B 실행자동화를 실제 성능/운영 유사환경에서 사람이 그대로 수행할 수 있도록 사전조건, 명령, 증적, 판정, 중단조건을 Run 단위로 고정한다.

## 1. 이번 Wave의 경계

이번 Wave는 실제 운영 부하/장애를 수행하지 않는다. 현재 세션에는 승인된 NSIGHT 성능환경, 실제 Oracle, 운영 Tomcat, APM, L4/GSLB, KMS가 연결되어 있지 않다.

따라서 이번 결과는 다음을 의미한다.

```text
Runbook / Tool / Checklist Ready
        ↓
실제 환경 Identity 입력
        ↓
RUN-TIMEOUT
        ↓
RUN-P600
        ↓
RUN-P1200
        ↓
1차 Runtime Evidence Review
```

`Runtime PASS`는 실제 `PRODUCTION_RUNTIME` 또는 승인된 production-like evidence가 생성된 경우에만 인정한다.

## 2. 1차 실행군 순서

| 순서 | Run | 목적 | 다음 단계 진입조건 |
|---:|---|---|---|
| 1 | `RUN-TIMEOUT` | Rollback/Late Commit/Pool/Context 안전성 | P0 safety failure 없음 |
| 2 | `RUN-P600` | 정상 운영 Peak 600 TPS | Machine hard gate 충족 + Human threshold review |
| 3 | `RUN-P1200` | Design Peak 1,200 TPS | P600 승인 + P0 병목 없음 |

`RUN-TIMEOUT`이 `NO_GO`이면 부하시험으로 진행하지 않는다.

## 3. 공통 Runtime Identity

모든 Run은 `run-manifest.json`에 다음 필드를 실제값으로 넣는다.

| 필드 | 규칙 |
|---|---|
| environment | `PERF`, `STAGE`, 승인된 `PROD` 등 실제 환경 |
| evidence_class | Runtime 승인 후보는 `PRODUCTION_RUNTIME` |
| synthetic | 실제 Run은 `false` |
| git_commit | 40자리 실제 Commit |
| artifact_version | 배포 Artifact Version |
| config_version | Config Snapshot Version/Hash |
| service_id | 실제 시험 ServiceId |
| guid | Run 추적 GUID |
| hostname | 실제 Hostname, `UNKNOWN` 금지 |
| tomcat_jvm_instance | 실제 CATALINA_BASE/JVM 식별자 |
| db_target | 실제 RDW/ADW/Test DB Target |

## 4. 공통 사전점검

실행자는 다음을 `GO`로 확인해야 한다.

- [ ] 실행 환경/시간대/변경 Ticket 승인
- [ ] 대상 ServiceId와 Request Body 승인
- [ ] Artifact/Config Version 동결
- [ ] Hostname/Tomcat JVM/DB Target 식별
- [ ] 로그/Metric 시간 동기화 확인
- [ ] GUID+ServiceId 검색 가능
- [ ] Rollback/중단 책임자 지정
- [ ] 테스트 데이터 정리/복구 방식 확인
- [ ] `synthetic=false` 확인
- [ ] Production이면 운영 승인 Token/Change-ID 확인

## 5. 공통 중단조건

다음 중 하나가 발생하면 해당 Run을 중단하고 `NO_GO` 또는 `INCOMPLETE`로 기록한다.

1. 실제 Artifact/Config Version을 식별할 수 없음
2. Host/JVM/DB Identity 불명
3. DB 데이터 정합성 이상
4. 예상하지 못한 서비스 오류 확산
5. Hikari/Thread/CPU 자원 고갈로 다른 업무에 영향
6. Log/Metric 수집 실패로 동일 RunId 증적이 깨짐
7. 승인되지 않은 Production 조작이 필요함
8. RUN-TIMEOUT에서 Late Commit 또는 Connection 미반환 발견

## 6. Machine Gate와 Human Gate 분리

### Machine Hard Gate

자동 판정 가능한 사실이다.

```text
RUN-TIMEOUT
- rollback
- late commit 0
- pool restore
- worker return
- context leak 0

RUN-P600 / P1200
- target TPS 충족
- p95 <= 3s
- 필수 증적 완전성
- non-synthetic runtime identity
```

### Human/Open Gate

현재 아키텍처에서 승인값이 아직 확정되지 않은 항목이다.

- Error Rate 허용 상한
- Timeout Rate 허용 상한
- CPU/BusyThread/Hikari 최종 승인 상한
- P600→P1200 진행 승인
- 운영 변경/시험 승인

Working threshold는 경보/검토값으로 사용한다.

```text
CPU <= 70%
Busy Thread <= 70%
Hikari Active <= 80%
```

위 값 초과는 자동 Architecture FAIL로 단정하지 않고 `REVIEW`로 올린다.

## 7. 실행 디렉터리

```text
runtime-first-batch/
├─ identity.json
├─ request.json
├─ resource-metrics.json
├─ evidence/
│  ├─ RUN-TIMEOUT/
│  ├─ RUN-P600/
│  └─ RUN-P1200/
├─ result.jtl
└─ operator-notes.md
```

## 8. 공통 Tool 위치

```text
tools/nsight_run_automation.py
tools/nsight_runtime_evidence.py
tools/nsight_runbook_validate.py
load/jmeter/run-jmeter.sh
timeout/run-timeout-probe.sh
collect/collect-host.sh
collect/collect-jvm.sh
collect/collect-micrometer.sh
```

## 9. Go/No-Go 흐름

```text
Preflight
   ↓
Identity Valid ? ── NO → INCOMPLETE
   ↓ YES
Execute Run
   ↓
Required Evidence Complete ? ── NO → INCOMPLETE
   ↓ YES
Machine Hard Gate
   ├─ FAIL → NO_GO
   └─ PASS
        ↓
Human/Open Gate
   ├─ Pending → CONDITIONAL_REVIEW
   └─ Approved → GO_CANDIDATE
```

`GO_CANDIDATE`는 HG90 PASS와 동일하지 않다. 12개 Mandatory Run과 ADR/G80 재평가가 남아 있다.

## 10. 이번 Wave 종료조건

Wave3C는 다음을 만족하면 완료로 본다.

- 3개 Runbook 존재
- Machine-readable Catalog 존재
- Go/No-Go Validator 테스트 PASS
- 실제환경 미연결 상태에서 Production PASS를 만들지 않음
- G80/HG90 HOLD 유지
