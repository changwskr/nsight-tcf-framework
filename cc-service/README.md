# cc-service — Common (공통)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `cc-service` |
| 업무코드 | `CC` |
| 메인 클래스 | `com.nh.nsight.marketing.cc.NsightCcServiceApplication` |
| bootRun 포트 | **8081** |
| WAR | `cc-service.war` |
| Tomcat context | `/cc` |

## 개요

NSIGHT 마케팅 플랫폼 **Common (CC)** 업무 서비스입니다. 모든 거래는 TCF 파이프라인(`POST /online` 또는 `POST /cc/online`)을 통해 처리됩니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `CC.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :cc-service:bootRun
tcf-scripts/run-local.bat cc
```

## API

```bash
curl -X POST http://localhost:8081/cc/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/cc-sample-inquiry.json
```

## tcf-ui

- http://localhost:8099/cc/index.html
- http://localhost:8099/cc/index-multi.html

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`
