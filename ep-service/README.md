# ep-service — Event Processing

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `ep-service` |
| 업무코드 | `EP` |
| bootRun 포트 | **8090** |
| WAR (bootWar) | `ep.war` |
| Tomcat context | `/ep` |

## 개요

NSIGHT 마케팅 플랫폼 **Event Processing (EP)** 업무 서비스입니다. 모든 거래는 TCF 파이프라인(`POST /online` 또는 `POST /ep/online`)을 통해 처리됩니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `EP.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :ep-service:bootRun
tcf-scripts/run-local.bat ep
```

## API

```bash
# bootRun
curl -X POST http://localhost:8090/ep/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/ep-sample-inquiry.json

# ztomcat
curl -X POST http://localhost:8080/ep/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/ep-sample-inquiry.json
```

배포: `ztomcat/deploy-wars.bat ep` — [ztomcat/README.md](../ztomcat/README.md)

## tcf-ui

| 모드 | URL |
|------|-----|
| bootRun | http://localhost:8099/ep/index.html |
| ztomcat | http://localhost:8080/ui/ep/index.html |

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`