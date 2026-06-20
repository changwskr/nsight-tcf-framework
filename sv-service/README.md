# sv-service — Single View (마케팅)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `sv-service` |
| 업무코드 | `SV` |
| bootRun 포트 | **8086** |
| WAR (bootWar) | `sv.war` |
| Tomcat context | `/sv` |

## 개요

NSIGHT 마케팅 플랫폼 **Single View (SV)** 업무 서비스입니다. 모든 거래는 TCF 파이프라인(`POST /online` 또는 `POST /sv/online`)을 통해 처리됩니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `SV.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :sv-service:bootRun
tcf-scripts/run-local.bat sv
```

## API

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

배포: `ztomcat/deploy-wars.bat sv` — [ztomcat/README.md](../ztomcat/README.md)

## tcf-ui

| 모드 | URL |
|------|-----|
| bootRun | http://localhost:8099/sv/index.html |
| ztomcat | http://localhost:8080/ui/sv/index.html |

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`