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

**행동 처리(BP)** 실시간 업무 서비스입니다.

## 실행

```bash
gradle :bp-service:bootRun
tcf-scripts/run-local.bat bp
```

## API

| Method | Path |
|--------|------|
| POST | `/online`, `/bp/online` |

## tcf-ui

- http://localhost:8099/bp/index.html
- http://localhost:8099/bp/index-multi.html

## 의존성

`tcf-core`, `tcf-web`, `common-etc`, `common-updownload`
