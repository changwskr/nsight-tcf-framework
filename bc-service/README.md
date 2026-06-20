# bc-service — Business Customer

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `bc-service` |
| 업무코드 | `BC` |
| bootRun 포트 | **8084** |
| WAR (bootWar) | `bc.war` |
| Tomcat context | `/bc` |

## 개요

NSIGHT 마케팅 플랫폼 **Business Customer (BC)** 업무 서비스입니다. 모든 거래는 TCF 파이프라인(`POST /online` 또는 `POST /bc/online`)을 통해 처리됩니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `BC.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :bc-service:bootRun
tcf-scripts/run-local.bat bc
```

## API

```bash
# bootRun
curl -X POST http://localhost:8084/bc/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/bc-sample-inquiry.json

# ztomcat
curl -X POST http://localhost:8080/bc/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/bc-sample-inquiry.json
```

배포: `ztomcat/deploy-wars.bat bc` — [ztomcat/README.md](../ztomcat/README.md)

## tcf-ui

| 모드 | URL |
|------|-----|
| bootRun | http://localhost:8099/bc/index.html |
| ztomcat | http://localhost:8080/ui/bc/index.html |

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`