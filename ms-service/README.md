# ms-service — Mini Single View (고객)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `ms-service` |
| 업무코드 | `MS` |
| 메인 클래스 | `com.nh.nsight.marketing.ms.NsightMsServiceApplication` |
| bootRun 포트 | **8085** |
| WAR | `ms-service.war` |
| Tomcat context | `/ms` |

## 개요

NSIGHT 마케팅 플랫폼 **Mini Single View (MS)** 업무 서비스입니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `MS.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :ms-service:bootRun
tcf-scripts/run-local.bat ms
```

## API

```bash
curl -X POST http://localhost:8085/ms/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/ms-sample-inquiry.json
```

## tcf-ui

- http://localhost:8099/ms/index.html
- http://localhost:8099/ms/index-multi.html

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`
