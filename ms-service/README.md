# ms-service — Marketing Strategy

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `ms-service` |
| 업무코드 | `MS` |
| bootRun 포트 | **8085** |
| WAR (bootWar) | `ms.war` |
| Tomcat context | `/ms` |

## 개요

NSIGHT 마케팅 플랫폼 **Marketing Strategy (MS)** 업무 서비스입니다. 모든 거래는 TCF 파이프라인(`POST /online` 또는 `POST /ms/online`)을 통해 처리됩니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `MS.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :ms-service:bootRun
tcf-scripts/run-local.bat ms
```

## API

```bash
# bootRun
curl -X POST http://localhost:8085/ms/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/ms-sample-inquiry.json

# ztomcat
curl -X POST http://localhost:8080/ms/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/ms-sample-inquiry.json
```

배포: `ztomcat/deploy-wars.bat ms` — [ztomcat/README.md](../ztomcat/README.md)

## tcf-ui

| 모드 | URL |
|------|-----|
| bootRun | http://localhost:8099/ms/index.html |
| ztomcat | http://localhost:8080/ui/ms/index.html |

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`