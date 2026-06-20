# cs-service — Common Service (지원)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `cs-service` |
| 업무코드 | `CS` |
| 메인 클래스 | `com.nh.nsight.marketing.cs.NsightCsServiceApplication` |
| bootRun 포트 | **8094** |
| WAR | `cs-service.war` |
| Tomcat context | `/cs` |

## 개요

NSIGHT 마케팅 플랫폼 **Common Service (CS)** 업무 서비스입니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `CS.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :cs-service:bootRun
tcf-scripts/run-local.bat cs
```

## API

```bash
curl -X POST http://localhost:8094/cs/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/cs-sample-inquiry.json
```

## tcf-ui

- http://localhost:8099/cs/index.html
- http://localhost:8099/cs/index-multi.html

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`
