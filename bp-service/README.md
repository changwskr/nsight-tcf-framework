# bp-service — Behavior Processing (실시간)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `bp-service` |
| 업무코드 | `BP` |
| 메인 클래스 | `com.nh.nsight.marketing.bp.NsightBpServiceApplication` |
| bootRun 포트 | **8091** |
| WAR | `bp-service.war` |
| Tomcat context | `/bp` |

## 개요

NSIGHT 마케팅 플랫폼 **Behavior Processing (BP)** 업무 서비스입니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `BP.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :bp-service:bootRun
tcf-scripts/run-local.bat bp
```

## API

```bash
curl -X POST http://localhost:8091/bp/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/bp-sample-inquiry.json
```

## tcf-ui

- http://localhost:8099/bp/index.html
- http://localhost:8099/bp/index-multi.html

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`
