# ss-service — Sales Support (지원)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `ss-service` |
| 업무코드 | `SS` |
| 메인 클래스 | `com.nh.nsight.marketing.ss.NsightSsServiceApplication` |
| bootRun 포트 | **8093** |
| WAR | `ss-service.war` |
| Tomcat context | `/ss` |

## 개요

NSIGHT 마케팅 플랫폼 **Sales Support (SS)** 업무 서비스입니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `SS.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :ss-service:bootRun
tcf-scripts/run-local.bat ss
```

## API

```bash
curl -X POST http://localhost:8093/ss/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/ss-sample-inquiry.json
```

## tcf-ui

- http://localhost:8099/ss/index.html
- http://localhost:8099/ss/index-multi.html

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`
