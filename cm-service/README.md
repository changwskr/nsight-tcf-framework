# cm-service — Campaign Management

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `cm-service` |
| 업무코드 | `CM` |
| bootRun 포트 | **8088** |
| WAR (bootWar) | `cm.war` |
| Tomcat context | `/cm` |

## 개요

NSIGHT 마케팅 플랫폼 **Campaign Management (CM)** 업무 서비스입니다. 모든 거래는 TCF 파이프라인(`POST /online` 또는 `POST /cm/online`)을 통해 처리됩니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `CM.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :cm-service:bootRun
tcf-scripts/run-local.bat cm
```

## API

```bash
# bootRun
curl -X POST http://localhost:8088/cm/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/cm-sample-inquiry.json

# ztomcat
curl -X POST http://localhost:8080/cm/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/cm-sample-inquiry.json
```

배포: `ztomcat/deploy-wars.bat cm` — [ztomcat/README.md](../ztomcat/README.md)

## tcf-ui

| 모드 | URL |
|------|-----|
| bootRun | http://localhost:8099/cm/index.html |
| ztomcat | http://localhost:8080/ui/cm/index.html |

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`