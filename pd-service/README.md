# pd-service — Product

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `pd-service` |
| 업무코드 | `PD` |
| bootRun 포트 | **8087** |
| WAR (bootWar) | `pd.war` |
| Tomcat context | `/pd` |

## 개요

NSIGHT 마케팅 플랫폼 **Product (PD)** 업무 서비스입니다. 모든 거래는 TCF 파이프라인(`POST /online` 또는 `POST /pd/online`)을 통해 처리됩니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `PD.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :pd-service:bootRun
tcf-scripts/run-local.bat pd
```

## API

```bash
# bootRun
curl -X POST http://localhost:8087/pd/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/pd-sample-inquiry.json

# ztomcat
curl -X POST http://localhost:8080/pd/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/pd-sample-inquiry.json
```

배포: `ztomcat/deploy-wars.bat pd` — [ztomcat/README.md](../ztomcat/README.md)

## tcf-ui

| 모드 | URL |
|------|-----|
| bootRun | http://localhost:8099/pd/index.html |
| ztomcat | http://localhost:8080/ui/pd/index.html |

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`