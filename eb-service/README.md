# eb-service — Event Bridge

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `eb-service` |
| 업무코드 | `EB` |
| bootRun 포트 | **8089** |
| WAR (bootWar) | `eb.war` |
| Tomcat context | `/eb` |

## 개요

NSIGHT 마케팅 플랫폼 **Event Bridge (EB)** 업무 서비스입니다. 모든 거래는 TCF 파이프라인(`POST /online` 또는 `POST /eb/online`)을 통해 처리됩니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `EB.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :eb-service:bootRun
tcf-scripts/run-local.bat eb
```

## API

```bash
# bootRun
curl -X POST http://localhost:8089/eb/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/eb-sample-inquiry.json

# ztomcat
curl -X POST http://localhost:8080/eb/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/eb-sample-inquiry.json
```

배포: `ztomcat/deploy-wars.bat eb` — [ztomcat/README.md](../ztomcat/README.md)

## tcf-ui

| 모드 | URL |
|------|-----|
| bootRun | http://localhost:8099/eb/index.html |
| ztomcat | http://localhost:8080/ui/eb/index.html |

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`