# tcf-ui — NSIGHT 온라인 거래 테스트 UI

WebTopSuite/Client 없이 브라우저에서 표준 HTTP/JSON 전문을 작성·전송·응답 확인하기 위한 Spring Boot 애플리케이션입니다.

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `tcf-ui` |
| 메인 클래스 | `com.nh.nsight.tcf.ui.NsightTcfUiApplication` |
| 산출물 | `tcf-ui.jar` |
| 포트 | **8099** |

`tcf-ui`는 **Relay 서버** 역할을 합니다.

```text
브라우저 → tcf-ui API → 업무 WAS (/{code}/online)
```

## 실행

```bash
# 프로젝트 루트
gradle :tcf-ui:bootRun

# 또는
cd tcf-ui
run-tcf-ui.bat      # Windows
./run-tcf-ui.sh     # Linux/macOS
```

브라우저: http://localhost:8099

## 사전 조건

테스트 대상 업무 WAS가 기동되어 있어야 합니다.

```bash
gradle :sv-service:bootRun   # 예: SV (포트 8086)
gradle :tcf-om:bootRun       # OM 운영관리 (포트 8097)
```

## 패키지 구조

```text
com.nh.nsight.tcf.ui
├── NsightTcfUiApplication      # 메인
├── config/                     # TcfUiProperties, TcfUiConfiguration
├── catalog/                    # BusinessModuleDefinitions (업무·포트 정의)
├── controller/                 # TcfApiController, UpdownloadApiController, EtcApiController
├── service/                    # Relay·카탈로그 서비스
└── model/                      # API 응답 모델
```

## 설정

`src/main/resources/application.yml`

```yaml
nsight:
  tcf-ui:
    deployment-mode: bootrun
    tomcat-gateway-url: http://localhost:8080
    bootrun-host: http://127.0.0.1
```

## 주요 API

| API | 설명 |
|-----|------|
| `GET /api/business-modules` | 업무 모듈 목록 |
| `GET /api/business-modules/{code}/target-url` | Relay 대상 URL |
| `POST /api/relay/{code}/online` | 온라인 거래 Relay |
| `GET /api/config` | UI 설정 조회 |

## 화면

| URL | 설명 |
|-----|------|
| `/index.html` | 업무 허브 |
| `/{code}/index.html` | 단일 거래 테스트 |
| `/{code}/index-multi.html` | 다중 거래 테스트 |
| `/ud/updownload.html` | UD 파일 관리 (common-updownload 연동) |
| `/et/transaction-log.html` | ET 거래 IO 로그 |
| `/om/admin/dashboard.html` | OM 운영관리 포털 (tcf-om 연동) |
