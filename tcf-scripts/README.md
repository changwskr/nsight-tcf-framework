# tcf-scripts — 빌드·실행 스크립트

로컬 개발용 Gradle 래퍼 스크립트 모음입니다. 프로젝트 루트에서 실행합니다.

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
| `tcf-om`, `om` | tcf-om (8097) |
| `ud`, `common-updownload` | tcf-om (8097, UD API 내장, 별칭) |
| `et`, `common-etc` | common-etc |
| `all` | 17개 *-service 일괄 기동 (각각 새 창) |

단일 인자: 포그라운드 실행. 복수 인자: 백그라운드(새 창) 실행.

## build — 모듈 빌드

```bash
tcf-scripts\build.bat <target>
tcf-scripts/build.sh <target>
```

| 인자 | 설명 |
|------|------|
| `core` | tcf-util, tcf-core, tcf-web |
| `common` | common-etc |
| `wars` | 17개 업무 WAR 일괄 |
| `all` | 전체 |

## curl-sample — 샘플 거래 호출

```bash
tcf-scripts\curl-sample.bat sv
tcf-scripts/curl-sample.sh sv
```

## deploy — Tomcat webapps 배포

WAR 빌드 후 Tomcat `webapps`로 복사합니다. `deploy.bat`(Windows) / `deploy.sh`(Linux·macOS) 동일 로직입니다.

```bash
# Windows
tcf-scripts\deploy.bat
tcf-scripts\deploy.bat sv cc ud

# Linux / macOS
chmod +x tcf-scripts/deploy.sh
tcf-scripts/deploy.sh
tcf-scripts/deploy.sh sv cc ud
```

| 인자 | 설명 |
|------|------|
| (없음) / `all` | 17개 업무 WAR + `ud.war` 일괄 빌드·배포 |
| `sv`, `cc`, … | 선택 업무만 빌드·배포 |
| `ud` | tcf-om (`ud.war`로 배포, UD API 내장) |
| `tcf-om` | `tcf-om.war` → `om.war`로 배포 |

**배포 경로 (Windows `deploy.bat` 기본값):**

`C:\Programming(23-08-15)\nsight-httpjson-standard\ztomcat\apache-tomcat-10.1.34\webapps`

**배포 경로 (`deploy.sh` 기본값):**

1. `../nsight-httpjson-standard/ztomcat/apache-tomcat-10.1.34/webapps` (형제 디렉터리)
2. 없으면 `/opt/tomcat/webapps`

환경 변수:

| 변수 | 설명 |
|------|------|
| `TOMCAT_WEBAPPS` | webapps 절대 경로 |
| `GRADLE_HOME` / `GRADLE_HOME_OVERRIDE` | Gradle 설치 경로 |

배포 후 `dir`(bat) / `ls -lh`(sh)로 WAR 존재·크기를 검증합니다.

빌드 산출물(`cc-service.war` 등)은 Tomcat context에 맞게 `cc.war` 등으로 이름을 바꿔 복사합니다.

## 포트 참고

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
| tcf-om / om-service | 8097 (UD 파일 API는 tcf-om 내장) |
| tcf-ui | 8099 |
