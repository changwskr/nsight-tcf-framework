# tcf-scripts — 빌드·실행 스크립트

로컬 개발용 Gradle 래퍼 스크립트 모음입니다. **프로젝트 루트**에서 실행합니다.

> 전체 스크립트 맵 (ztomcat, tcf-cicd, 모듈 scripts): [docs/architecture/38-script.md](../docs/architecture/38-script.md)

## run-local — 서비스 기동

```bash
# Windows
tcf-scripts\run-local.bat <target>

# Linux/macOS
tcf-scripts/run-local.sh <target>
```

| 인자 | 대상 모듈 |
|------|-----------|
| `sv`, `sv-service` | sv-service |
| `tcf-ui`, `ui` | tcf-ui (8099) |
| `tcf-om`, `om`, `ud` | tcf-om (8097, UD API 내장) |
| `batch`, `tcf-batch` | tcf-batch (8098) |
| `all` | 16개 *-service + tcf-om 일괄 기동 (각각 새 창) |

단일 인자: 포그라운드 실행. 복수 인자: 백그라운드(새 창) 실행.

## build — 모듈 빌드

```bash
tcf-scripts\build.bat <target>
tcf-scripts/build.sh <target>
```

| 인자 | 설명 |
|------|------|
| `all` | clean + 업무 WAR 일괄 빌드 |
| `wars` | `buildBusinessWars` (17 WAR: 16 *-service + tcf-om) |
| `ztomcat` | `gradle buildZtomcatWars` (19 WAR — Gradle 직접 호출) |
| `tcf` | tcf-util, tcf-core, tcf-web |
| `ui` | tcf-ui bootJar |
| `batch` | tcf-batch bootJar |
| `sv`, `tcf-om`, … | 개별 모듈 build |

## curl-sample — 샘플 거래 호출

```bash
tcf-scripts\curl-sample.bat sv
tcf-scripts/curl-sample.sh sv
```

샘플 JSON: `tcf-ui/src/main/resources/sample-requests/{code}-sample-inquiry.json`

## deploy — Tomcat webapps 배포

WAR 빌드 후 [ztomcat](../ztomcat/README.md) `webapps`로 복사합니다.

```bash
tcf-scripts\deploy.bat sv cc om
tcf-scripts/deploy.sh sv cc om
```

| 인자 | 설명 |
|------|------|
| (없음) / `all` | 업무 WAR + tcf-om 일괄 |
| `sv`, `cc`, … | 선택 업무만 |
| `ud`, `tcf-om`, `om` | tcf-om (`om.war`) |
| `batch`, `ui` | tcf-batch, tcf-ui |

환경 변수: `TOMCAT_WEBAPPS`, `GRADLE_HOME` / `GRADLE_HOME_OVERRIDE`

> 전체 Tomcat 배포·검증은 `ztomcat/deploy-wars.bat`, `ztomcat/verify-deploy.ps1` 사용을 권장합니다.

## 포트 참고 (bootRun)

| 모듈 | 포트 |
|------|------|
| cc-service | 8081 |
| ic-service | 8082 |
| pc-service | 8083 |
| bc-service | 8084 |
| ms-service | 8085 |
| sv-service | 8086 |
| pd-service | 8087 |
| cm-service | 8088 |
| eb-service | 8089 |
| ep-service | 8090 |
| bp-service | 8091 |
| bd-service | 8092 |
| ss-service | 8093 |
| cs-service | 8094 |
| ct-service | 8095 |
| mg-service | 8096 |
| tcf-om | 8097 |
| tcf-batch | 8098 |
| tcf-ui | 8099 |

Tomcat(ztomcat) 모드: 모든 context **8080** — `/cc` … `/mg`, `/om`, `/batch`, `/ui`

## Gradle 직접 호출

```bash
gradle buildBusinessWars     # 17 WAR
gradle buildZtomcatWars      # 19 WAR
gradle :tcf-om:bootRun
gradle :tcf-batch:bootRun
gradle :tcf-ui:bootRun
```
