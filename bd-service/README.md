# bd-service — Business Data

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `bd-service` |
| 업무코드 | `BD` |
| bootRun 포트 | **8092** |
| WAR (bootWar) | `bd.war` |
| Tomcat context | `/bd` |

## 개요

NSIGHT 마케팅 플랫폼 **Business Data (BD)** 업무 서비스입니다. 모든 거래는 TCF 파이프라인(`POST /online` 또는 `POST /bd/online`)을 통해 처리됩니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `BD.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :bd-service:bootRun
tcf-scripts/run-local.bat bd
```

## API

```bash
# bootRun
curl -X POST http://localhost:8092/bd/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/bd-sample-inquiry.json

# ztomcat
curl -X POST http://localhost:8080/bd/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/bd-sample-inquiry.json
```

배포: `ztomcat/deploy-wars.bat bd` — [ztomcat/README.md](../ztomcat/README.md)

## tcf-ui

| 모드 | URL |
|------|-----|
| bootRun | http://localhost:8099/bd/index.html |
| ztomcat | http://localhost:8080/ui/bd/index.html |

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`