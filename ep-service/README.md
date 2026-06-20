# ep-service — Event Processing (실시간)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `ep-service` |
| 업무코드 | `EP` |
| 메인 클래스 | `com.nh.nsight.marketing.ep.NsightEpServiceApplication` |
| bootRun 포트 | **8090** |
| WAR | `ep-service.war` |
| Tomcat context | `/ep` |

## 개요

NSIGHT 마케팅 플랫폼 **Event Processing (EP)** 업무 서비스입니다.

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
curl -X POST http://localhost:8090/ep/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/ep-sample-inquiry.json
```

## tcf-ui

- http://localhost:8099/ep/index.html
- http://localhost:8099/ep/index-multi.html

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`
