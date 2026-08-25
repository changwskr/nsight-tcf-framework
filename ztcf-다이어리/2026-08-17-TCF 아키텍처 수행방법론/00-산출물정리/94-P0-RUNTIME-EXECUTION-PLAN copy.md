# P0 Runtime Evidence Execution Plan

> 목적: Source 정적분석으로 증명할 수 없는 HG90 Hard Blocker를 **실행 가능한 Run ID와 합격조건**으로 변환한다.

## 1. 실행 순서

```text
RUN-TIMEOUT
  ↓
RUN-P600 → RUN-P1200 → RUN-S1800
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
```

## 2. 공통 Run Identity

모든 결과는 다음 키를 필수로 갖는다.

```text
RunId
Timestamp
Environment
Git Commit / Artifact Version
Config Version
ServiceId
GUID
Hostname
Tomcat JVM Instance
DB Target
```

## 3. Run별 합격조건

| Run | 검증 | 최소 합격조건 | 필수 증적 |
|---|---|---|---|
| RUN-TIMEOUT | 강제 Timeout | 응답 Timeout + TX rollback + late commit 0 + Connection 반환 | DB before/after, TX log, pool metric, thread dump |
| RUN-P600 | 일반 Peak | p95≤3s, error/timeout 임계치 충족, CPU/Thread/Pool 안정 | APM/metric/log |
| RUN-P1200 | Design Peak | p95 목표, Busy/Hikari/CPU approved threshold 이내 | 동일 RunId metric |
| RUN-S1800 | Stress | 실패양상/포화점 식별, 데이터 정합성 유지 | saturation chart/log |
| RUN-HIKARI | Pool pressure | pending/timeout 발생 조건과 DB session 상한 확인 | pool+DB session |
| RUN-SLOWSQL | Slow SQL | Query timeout < TX timeout; rollback/connection return | SQL/TX/pool |
| RUN-N1 | AP N-1 | Peak 1,200 처리 및 오류율 승인범위 | routing+metric |
| RUN-SESSION | JVM/VM failover | 승인 Session 정책대로 유지 또는 재인증 | session/idp/l4 log |
| RUN-CF | Center failover/failback | 승인 RTO/RPO 충족 | GSLB/L4/DB/session trace |
| RUN-TRACE | E2E trace | GUID+ServiceId로 Apache→JVM→TCF→SQL/외부연계 역추적 가능 | log bundle |
| RUN-ROLLING | Rolling | 노드 drain/deploy/rejoin 중 Peak residual capacity 충족 | deploy+health+metric |
| RUN-JWT-ROTATE | Key rotation | old/new kid grace, issuer 다중노드/재기동 후 검증 정상 | JWT/JWKS/key audit |

## 4. RUN-TIMEOUT 세부 시나리오

1. 테스트용 ServiceId가 DB 변경 후 의도적으로 online timeout보다 길게 대기
2. Client는 Timeout 응답 수신
3. DB 변경이 rollback되었는지 확인
4. Timeout 이후 2× timeout 시간까지 late commit 여부 재확인
5. Hikari active/pending가 원상복구되는지 확인
6. Worker Thread가 pool로 반환되는지 확인
7. 동일 Worker 재사용 시 이전 GUID/MDC/RequestAttributes 잔존 여부 확인

## 5. Capacity 승인 규칙

`500 TPS`와 `855 TPS` 중 하나를 문서로 선택하지 않는다.

```text
Runtime Approved Capacity
= Load Test에서 NFR + Resource Threshold + N-1 조건을 동시에 만족한 값
```

## 6. Evidence 보관 구조

```text
RUNTIME-EVIDENCE/<RUN_ID>/
├─ run-manifest.json
├─ config-snapshot/
├─ metrics/
├─ logs/
├─ db/
├─ screenshots/
├─ result.md
└─ approval.md
```
