# bp-service — Business Process

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `bp-service` |
| 업무코드 | `BP` |
| bootRun 포트 | **8091** |
| WAR (bootWar) | `bp.war` |
| Tomcat context | `/bp` |

## 개요

NSIGHT 마케팅 플랫폼 **Business Process (BP)** 업무 서비스입니다. 모든 거래는 TCF 파이프라인(`POST /online` 또는 `POST /bp/online`)을 통해 처리됩니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `BP.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :bp-service:bootRun
tcf-scripts/run-local.bat bp
```

## API

```bash
# bootRun
curl -X POST http://localhost:8091/bp/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/bp-sample-inquiry.json

# ztomcat
curl -X POST http://localhost:8080/bp/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/bp-sample-inquiry.json
```

배포: `ztomcat/deploy-wars.bat bp` — [ztomcat/README.md](../ztomcat/README.md)

## tcf-ui

| 모드 | URL |
|------|-----|
| bootRun | http://localhost:8099/bp/index.html |
| ztomcat | http://localhost:8080/ui/bp/index.html |

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`