# pd-service — Product (마케팅)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `pd-service` |
| 업무코드 | `PD` |
| 메인 클래스 | `com.nh.nsight.marketing.pd.NsightPdServiceApplication` |
| bootRun 포트 | **8087** |
| WAR | `pd-service.war` |
| Tomcat context | `/pd` |

## 개요

**상품(PD)** 마케팅 업무 서비스입니다.

## 실행

```bash
gradle :pd-service:bootRun
tcf-scripts/run-local.bat pd
```

## API

| Method | Path |
|--------|------|
| POST | `/online`, `/pd/online` |

## tcf-ui

- http://localhost:8099/pd/index.html
- http://localhost:8099/pd/index-multi.html

## 의존성

`tcf-core`, `tcf-web`, `common-etc`
