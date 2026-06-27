# NSIGHT TCF Framework

NSIGHT HTTP/JSON 표준 전문 처리 구조를 **TCF(Transaction Control Framework)** 중심으로 구성한 멀티 모듈 Gradle 프로젝트입니다.

## 모듈 구조

```text
nsight-tcf-framework
├─ tcf-util              공통 유틸 (Spring 없음)
├─ tcf-core              TCF 엔진 (STF/TCF/ETF, Dispatcher)
├─ tcf-web               HTTP 레이어 (/online, TcfGateway, 거래로그 DB, WAR 부트스트랩)
├─ tcf-cache             EhCache / Spring Cache 공통 모듈
├─ tcf-om                운영관리 + 파일 업·다운로드 (OM/UD)
├─ tcf-batch             OM 대시보드 AP/DB/세션/배포 수집 배치
├─ tcf-ui                거래 테스트 UI · OM 관리 포털 Relay
├─ tcf-scripts           빌드·실행·배포 스크립트
├─ tcf-cicd              local/dev/prod Spring·Tomcat 설정 (SoT)
├─ ztomcat               로컬 Tomcat 8080 WAR 배포 (12 context)
└─ ic-service … mg-service   9개 업무 WAR (+ tcf-om)
```

각 모듈 상세는 해당 디렉터리의 `README.md`를 참고하세요.

## TCF 처리 흐름

```text
Client / tcf-ui / REST API
   ↓
OnlineTransactionController / TcfGateway
   ↓
TCF.process()
   ├─ STF.preProcess()           Header 검증, GUID, 세션·권한, 멱등성
   ├─ TransactionDispatcher      serviceId → TransactionHandler
   └─ ETF.success/fail/error     표준 응답, 감사·메트릭
```

## 의존 방향

```text
tcf-util → tcf-core → tcf-web → tcf-cache(선택) → 업무 서비스 / tcf-om / tcf-batch
```

## 배포 모드

| 모드 | 설명 | 대표 URL |
|------|------|----------|
| **bootRun** | 모듈별 독립 JVM·포트 | `http://localhost:8086/sv/online`, OM UI `8099` |
| **ztomcat** | Tomcat 8080에 WAR 12개 | `http://localhost:8080/sv/online`, OM UI `/ui` |

```text
ztomcat (8080)                    bootRun (별도 프로세스)
────────────────                  ─────────────────────
/ic … /mg  업무 9                 8082–8096  *-service
/om        tcf-om                 8097       tcf-om
/batch     tcf-batch              8098       tcf-batch
/ui        tcf-ui                 8099       tcf-ui
```

상세: [ztomcat/README.md](ztomcat/README.md)

## 빠른 시작

### bootRun

```bash
# SV 업무 단독
gradle :sv-service:bootRun

# OM · 배치 · UI
gradle :tcf-om:bootRun
gradle :tcf-batch:bootRun
gradle :tcf-ui:bootRun

# 스크립트 (프로젝트 루트)
tcf-scripts\run-local.bat sv
tcf-scripts\run-local.bat tcf-om batch ui
```

### ztomcat (Tomcat 8080)

```bat
cd ztomcat
h2-txlog.ps1 start          rem 공유 H2 TCP 9092 (선행 권장)
install-tomcat.bat
deploy-wars.bat all
start.bat
verify-deploy.ps1
```

## 빌드

```bash
gradle buildBusinessWars    # 업무 10 WAR (9 *-service + tcf-om)
gradle buildZtomcatWars     # 12 WAR (+ tcf-batch, tcf-ui)
```

`tcf-scripts\build.bat ztomcat` — 위와 동일한 12 WAR 일괄 빌드

## 포트 요약 (bootRun)

| 포트 | 모듈 | 비고 |
|------|------|------|
| 8082 | ic-service | |
| 8083 | pc-service | |
| 8085 | ms-service | |
| 8086 | sv-service | |
| 8087 | pd-service | |
| 8089 | eb-service | |
| 8090 | ep-service | |
| 8093 | ss-service | |
| 8096 | mg-service | |
| 8097 | **tcf-om** | UD API 내장 |
| 8098 | **tcf-batch** | 대시보드 수집 |
| 8099 | **tcf-ui** | Relay · OM admin UI |

Tomcat 모드에서는 업무·OM·batch·UI 모두 **8080** 게이트웨이 context path를 사용합니다.

## OM 관리 포털

### bootRun

1. `gradle :tcf-om:bootRun` (8097)
2. `gradle :tcf-batch:bootRun` (8098) — 대시보드 수집
3. `gradle :tcf-ui:bootRun` (8099)
4. http://localhost:8099/om/admin/login.html (`admin01` / `nsight01!`)

### ztomcat

1. `ztomcat/h2-txlog.ps1 start` → `deploy-wars.bat all` → `start.bat`
2. http://localhost:8080/ui/om/admin/login.html (`admin01` / `nsight01!`)

### 주요 화면

| 화면 | 경로 (ztomcat) |
|------|----------------|
| 운영 대시보드 | `/ui/om/admin/dashboard.html` |
| 거래로그 | `/ui/om/admin/transaction-log.html` |
| ServiceId | `/ui/om/admin/service-catalog.html` |
| **사용자 / 권한 / 메뉴 / 기능·데이터권한** | `/ui/om/admin/user-auth.html` |
| 공통코드 | `/ui/om/admin/common-code.html` |
| 권한이력 | `/ui/om/admin/auth-history.html` |
| 오류코드 | `/ui/om/admin/error-code.html` |
| Cache | `/ui/om/admin/cache.html` |
| 세션 | `/ui/om/admin/session.html` |
| 배치 / 배포 | `/ui/om/admin/batch.html`, `deploy.html` |
| 파일 관리 | `/ui/om/admin/file-management.html` |
| 환경설정 | `/ui/om/admin/system-config.html` |

**user-auth.html** 탭 구성: 사용자 · 권한그룹 · 메뉴 · 기능권한(CRUD) · 데이터권한(조회).  
구 `function-auth.html`, `data-auth.html` URL은 `user-auth.html`로 리다이렉트됩니다.

## 샘플 호출

```bash
# bootRun
curl -X POST http://localhost:8086/sv/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/sv-sample-inquiry.json

# ztomcat
curl -X POST http://localhost:8080/sv/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/sv-sample-inquiry.json
```

## 공유 H2 (거래로그 · OM)

dev(ztomcat) 환경에서는 **H2 TCP 9092** + 파일 DB `data/nsight-txlog/nsight_om` 하나를 모든 WAR가 공유합니다.

| 환경 | 설정 |
|------|------|
| **H2 TCP 서버** | `ztomcat/h2-txlog.ps1 start` (baseDir: `data/nsight-txlog`) |
| **JDBC (dev)** | `jdbc:h2:tcp://127.0.0.1:9092/./nsight_om` |
| **bootRun** | Gradle `bootRun` → `nsight.txlog.path` = 프로젝트 `data/nsight-txlog` |
| **ztomcat** | `ztomcat/conf/setenv.bat` → `-Dnsight.txlog.path={프로젝트}/data/nsight-txlog` |

기능권한 시드(51건 + 커스텀 권한그룹): om 기동 시 `OmDatabaseMigration` 자동 MERGE, 또는 수동:

```bat
tcf-cicd\local\script\seed-function-auth.bat
```

## 요구 사항

- Java 21
- Gradle 8.x

## 문서

| 구분 | 경로 |
|------|------|
| **Gradle 명령어 매뉴얼** | [docs/manual/gradle.md](docs/manual/gradle.md) |
| **환경변수 매뉴얼** | [docs/manual/environment-variables.md](docs/manual/environment-variables.md) |
| **빌드 산출물·기동 파일** | [docs/manual/artifacts.md](docs/manual/artifacts.md) |
| **라이브러리 모듈 참조** | [docs/manual/lib-module.md](docs/manual/lib-module.md) |
| CI/CD 설정 (SoT) | [tcf-cicd/README.md](tcf-cicd/README.md) |
| 아키텍처 | [docs/architecture/architecture.md](docs/architecture/architecture.md) |
| 빌드·모듈 구조 | [docs/architecture/22-build-project.md](docs/architecture/22-build-project.md) |
| 스크립트 | [docs/architecture/38-script.md](docs/architecture/38-script.md) |
| Header 기반 거래통제 | [docs/architecture/39-header-transaction-control.md](docs/architecture/39-header-transaction-control.md) |
