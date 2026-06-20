# ic-service — Integration Customer (고객)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `ic-service` |
| 업무코드 | `IC` |
| 메인 클래스 | `com.nh.nsight.marketing.ic.NsightIcServiceApplication` |
| bootRun 포트 | **8082** |
| WAR | `ic-service.war` |
| Tomcat context | `/ic` |

## 개요

NSIGHT 마케팅 플랫폼 **Integration Customer (IC)** 업무 서비스입니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `IC.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :ic-service:bootRun
tcf-scripts/run-local.bat ic
```

## API

```bash
curl -X POST http://localhost:8082/ic/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/ic-sample-inquiry.json
```

## tcf-ui

- http://localhost:8099/ic/index.html
- http://localhost:8099/ic/index-multi.html

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`
