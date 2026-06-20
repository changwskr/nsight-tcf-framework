# ct-service — Contents (지원)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `ct-service` |
| 업무코드 | `CT` |
| 메인 클래스 | `com.nh.nsight.marketing.ct.NsightCtServiceApplication` |
| bootRun 포트 | **8095** |
| WAR | `ct-service.war` |
| Tomcat context | `/ct` |

## 개요

NSIGHT 마케팅 플랫폼 **Contents (CT)** 업무 서비스입니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `CT.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :ct-service:bootRun
tcf-scripts/run-local.bat ct
```

## API

```bash
curl -X POST http://localhost:8095/ct/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/ct-sample-inquiry.json
```

## tcf-ui

- http://localhost:8099/ct/index.html
- http://localhost:8099/ct/index-multi.html

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`
