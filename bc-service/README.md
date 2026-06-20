# bc-service — Business Customer (고객)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `bc-service` |
| 업무코드 | `BC` |
| 메인 클래스 | `com.nh.nsight.marketing.bc.NsightBcServiceApplication` |
| bootRun 포트 | **8084** |
| WAR | `bc-service.war` |
| Tomcat context | `/bc` |

## 개요

NSIGHT 마케팅 플랫폼 **Business Customer (BC)** 업무 서비스입니다.

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
curl -X POST http://localhost:8084/bc/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/bc-sample-inquiry.json
```

## tcf-ui

- http://localhost:8099/bc/index.html
- http://localhost:8099/bc/index-multi.html

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`
