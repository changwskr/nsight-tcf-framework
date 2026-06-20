# eb-service — EBM (마케팅)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `eb-service` |
| 업무코드 | `EB` |
| 메인 클래스 | `com.nh.nsight.marketing.eb.NsightEbServiceApplication` |
| bootRun 포트 | **8089** |
| WAR | `eb-service.war` |
| Tomcat context | `/eb` |

## 개요

NSIGHT 마케팅 플랫폼 **EBM (EB)** 업무 서비스입니다.

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
curl -X POST http://localhost:8089/eb/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/eb-sample-inquiry.json
```

## tcf-ui

- http://localhost:8099/eb/index.html
- http://localhost:8099/eb/index-multi.html

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`
