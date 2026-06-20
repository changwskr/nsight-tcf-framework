# pd-service — Product (마케팅)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `pd-service` |
| 업무코드 | `PD` |
| 메인 클래스 | `com.nh.nsight.marketing.pd.NsightPdServiceApplication` |
| bootRun 포트 | **8087** |
| WAR | `pd-service.war` |
| Tomcat context | `/pd` |

## 개요

NSIGHT 마케팅 플랫폼 **Product (PD)** 업무 서비스입니다.

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
curl -X POST http://localhost:8087/pd/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/pd-sample-inquiry.json
```

## tcf-ui

- http://localhost:8099/pd/index.html
- http://localhost:8099/pd/index-multi.html

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`
