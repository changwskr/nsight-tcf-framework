# ct-service — Customer Touch

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `ct-service` |
| 업무코드 | `CT` |
| bootRun 포트 | **8095** |
| WAR (bootWar) | `ct.war` |
| Tomcat context | `/ct` |

## 개요

NSIGHT 마케팅 플랫폼 **Customer Touch (CT)** 업무 서비스입니다. 모든 거래는 TCF 파이프라인(`POST /online` 또는 `POST /ct/online`)을 통해 처리됩니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `CT.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :ct-service:bootRun
tcf-scripts/run-local.bat ct
```

## API

```bash
# bootRun
curl -X POST http://localhost:8095/ct/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/ct-sample-inquiry.json

# ztomcat
curl -X POST http://localhost:8080/ct/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/ct-sample-inquiry.json
```

배포: `ztomcat/deploy-wars.bat ct` — [ztomcat/README.md](../ztomcat/README.md)

## tcf-ui

| 모드 | URL |
|------|-----|
| bootRun | http://localhost:8099/ct/index.html |
| ztomcat | http://localhost:8080/ui/ct/index.html |

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`