# cm-service — Campaign (마케팅)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `cm-service` |
| 업무코드 | `CM` |
| 메인 클래스 | `com.nh.nsight.marketing.cm.NsightCmServiceApplication` |
| bootRun 포트 | **8088** |
| WAR | `cm-service.war` |
| Tomcat context | `/cm` |

## 개요

NSIGHT 마케팅 플랫폼 **Campaign (CM)** 업무 서비스입니다.

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
curl -X POST http://localhost:8088/cm/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/cm-sample-inquiry.json
```

## tcf-ui

- http://localhost:8099/cm/index.html
- http://localhost:8099/cm/index-multi.html

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`
