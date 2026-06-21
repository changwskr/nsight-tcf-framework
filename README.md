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
├─ ztomcat               로컬 Tomcat 8080 WAR 배포 (19 context)
├─ cc-service … mg-service   16개 업무 WAR
└─ om-service            레거시 OM WAR (tcf-om 권장)
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
| **ztomcat** | Tomcat 8080에 WAR 19개 | `http://localhost:8080/sv/online`, OM UI `/ui` |

```text
ztomcat (8080)                    bootRun (별도 프로세스)
────────────────                  ─────────────────────
/cc … /mg  업무 16                8081–8096  *-service
/om        tcf-om                 8097       tcf-om (또는 /om 만 사용)
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
install-tomcat.bat
deploy-wars.bat all
start.bat
verify-deploy.ps1
```

## 빌드

```bash
gradle buildBusinessWars    # 업무 17 WAR (16 *-service + tcf-om)
gradle buildZtomcatWars     # 19 WAR (+ tcf-batch, tcf-ui)
```

## 포트 요약 (bootRun)

| 포트 | 모듈 | 비고 |
|------|------|------|
| 8081–8096 | cc ~ mg (*-service) | 업무별 bootRun |
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

1. `ztomcat/deploy-wars.bat all` + `start.bat`
2. http://localhost:8080/ui/om/admin/login.html (`admin01` / `nsight01!`)

주요 화면: 대시보드, 거래로그, ServiceId, 사용자/권한, 공통코드, 오류코드, Cache, 세션, 파일 관리, 환경설정

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

## 공유 H2 (거래로그·OM)

bootRun·Tomcat·tcf-batch가 동일 DB를 쓰려면 `nsight.txlog.path`를 맞춥니다.

- bootRun: Gradle `bootRun`이 프로젝트 `data/nsight-txlog` 자동 설정
- ztomcat: `ztomcat/conf/setenv.bat`의 `-Dnsight.txlog.path=...`

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
| 아키텍처 | [docs/architecture/architecture.md](docs/architecture/architecture.md) |
| 빌드·모듈 구조 | [docs/architecture/22-build-project.md](docs/architecture/22-build-project.md) |
| 스크립트 | [docs/architecture/38-script.md](docs/architecture/38-script.md) |
