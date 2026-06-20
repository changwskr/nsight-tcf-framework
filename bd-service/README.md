# bd-service — Behavior Data (데이터)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `bd-service` |
| 업무코드 | `BD` |
| 메인 클래스 | `com.nh.nsight.marketing.bd.NsightBdServiceApplication` |
| bootRun 포트 | **8092** |
| WAR | `bd-service.war` |
| Tomcat context | `/bd` |

## 개요

NSIGHT 마케팅 플랫폼 **Behavior Data (BD)** 업무 서비스입니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `BD.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :bd-service:bootRun
tcf-scripts/run-local.bat bd
```

## API

```bash
curl -X POST http://localhost:8092/bd/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/bd-sample-inquiry.json
```

## tcf-ui

- http://localhost:8099/bd/index.html
- http://localhost:8099/bd/index-multi.html

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`
