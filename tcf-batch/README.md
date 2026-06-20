# tcf-batch — OM 대시보드 데이터 수집 배치

OM 운영 대시보드(`http://localhost:8099/om/admin/dashboard.html`)에 표시되는 **AP/DB/세션/배포 상태**를 수집해 `OM_AP_STATUS`, `OM_DB_STATUS`, `OM_SESSION_STATUS`, `OM_DEPLOY_STATUS` 테이블에 적재하는 배치 모듈입니다.

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `tcf-batch` |
| 메인 클래스 | `com.nh.nsight.tcf.batch.NsightTcfBatchApplication` |
| bootRun 포트 | **8098** |
| Job ID | `BAT-BATCH-001` |

## Job 목록

| Job ID | 설명 | 저장 테이블 |
|--------|------|-------------|
| `BAT-BATCH-001` | AP 상태 수집 (Actuator) | `OM_AP_STATUS` |
| `BAT-BATCH-002` | DB 상태 수집 (Actuator/JDBC) | `OM_DB_STATUS` |
| `BAT-BATCH-003` | 세션 현황 수집 (Spring Session/Actuator) | `OM_SESSION_STATUS` |
| `BAT-BATCH-004` | 배포 현황 수집 (Actuator/HTTP) | `OM_DEPLOY_STATUS` |

## 배포 현황 (`BAT-BATCH-004`)

```text
tcf-batch
  ├─ actuator: /actuator/health, /actuator/info, process.start.time
  ├─ http: UI 등 Actuator 미노출 대상 HTTP 200 확인
  └─ OM_DEPLOY_STATUS MERGE
         ↓
dashboard.html 배포 현황 패널
```

```bash
curl -X POST http://localhost:8098/batch/jobs/deploy-status/run
```

설정: `nsight.batch.deploy-status.targets` — `source-type: actuator | http`

## 세션 현황 (`BAT-BATCH-003`)

```text
tcf-batch
  ├─ spring-session: SPRING_SESSION 집계 (활성/만료/사용자)
  ├─ actuator: tomcat.sessions.active.current
  └─ OM_SESSION_STATUS MERGE
         ↓
dashboard.html 세션 현황 패널
```

```bash
curl -X POST http://localhost:8098/batch/jobs/session-status/run
```

설정: `nsight.batch.session-status.targets` — `source-type: spring-session | actuator`

## DB 상태 (`BAT-BATCH-002`)

```text
tcf-batch
  ├─ actuator: /actuator/health (db) + hikaricp pool metrics
  ├─ jdbc: SELECT 1 ping
  └─ OM_DB_STATUS MERGE
         ↓
dashboard.html DB 상태 패널
```

```bash
curl -X POST http://localhost:8098/batch/jobs/db-status/run
```

설정: `nsight.batch.db-status.targets` — `source-type: actuator | jdbc`

## AP 상태 (`BAT-BATCH-001`) — 동작

```text
tcf-batch (8098)
  ├─ 각 AP /actuator/health, /actuator/metrics 호출
  ├─ CPU / Heap / Thread / Health 산출
  └─ OM H2 OM_AP_STATUS · OM_BATCH_HISTORY MERGE/INSERT
         ↓
OM.Dashboard.inquiry → dashboard.html AP 상태 패널
```

## 실행

```bash
gradle :tcf-batch:bootRun
tcf-batch/scripts/run-local.bat
```

**사전 조건:** tcf-om과 동일 H2(`data/nsight-txlog/nsight_om`) 사용. 수집 대상 AP는 Actuator(`health`, `metrics`) 노출 필요.

## 수동 실행 API

```bash
curl -X POST http://localhost:8098/batch/jobs/ap-status/run
curl -X POST http://localhost:8098/batch/jobs/db-status/run
curl -X POST http://localhost:8098/batch/jobs/session-status/run
curl -X POST http://localhost:8098/batch/jobs/deploy-status/run
```

## OM 배치 화면 연동

- Job: `BAT-BATCH-001` ~ `BAT-BATCH-004` — OM 배치 관리 화면에서 수동 재실행 가능
- tcf-om 설정: `nsight.om.batch-service-url: http://127.0.0.1:8098`

## AP 대상 설정

`src/main/resources/application.yml` → `nsight.batch.ap-status.targets`

```yaml
nsight:
  batch:
    ap-status:
      cron: "0 */5 * * * *"
      targets:
        - ap-id: om-ap
          ap-name: tcf-om (:8097)
          base-url: http://127.0.0.1:8097
          enabled: true
```

## 로컬 검증 순서

1. `gradle :tcf-om:bootRun` (8097)
2. `gradle :sv-service:bootRun` (8086) 등 대상 AP 기동
3. `gradle :tcf-batch:bootRun` (8098)
4. `curl -X POST http://localhost:8098/batch/jobs/ap-status/run`
5. http://localhost:8099/om/admin/dashboard.html 에서 AP 상태 확인
