# eb-service — EBM (마케팅)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `eb-service` |
| 업무코드 | `EB` |
| 메인 클래스 | `com.nh.nsight.marketing.eb.NsightEbServiceApplication` |
| bootRun 포트 | **8089** |
| WAR | `eb-service.war` |
| Tomcat context | `/eb` |

## 개요

**EBM(EB)** 마케팅 업무 서비스입니다.

## 실행

```bash
gradle :eb-service:bootRun
tcf-scripts/run-local.bat eb
```

## API

| Method | Path |
|--------|------|
| POST | `/online`, `/eb/online` |

## tcf-ui

- http://localhost:8099/eb/index.html
- http://localhost:8099/eb/index-multi.html

## 의존성

`tcf-core`, `tcf-web`, `common-etc`
