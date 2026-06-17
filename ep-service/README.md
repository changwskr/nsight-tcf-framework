# ep-service — Event Processing (실시간)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `ep-service` |
| 업무코드 | `EP` |
| 메인 클래스 | `com.nh.nsight.marketing.ep.NsightEpServiceApplication` |
| bootRun 포트 | **8090** |
| WAR | `ep-service.war` |
| Tomcat context | `/ep` |

## 개요

**이벤트 처리(EP)** 실시간 업무 서비스입니다.

## 실행

```bash
gradle :ep-service:bootRun
tcf-scripts/run-local.bat ep
```

## API

| Method | Path |
|--------|------|
| POST | `/online`, `/ep/online` |

## tcf-ui

- http://localhost:8099/ep/index.html
- http://localhost:8099/ep/index-multi.html

## 의존성

`tcf-core`, `tcf-web`, `common-etc`, `common-updownload`
