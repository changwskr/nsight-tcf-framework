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

**캠페인(CM)** 마케팅 업무 서비스입니다.

## 실행

```bash
gradle :cm-service:bootRun
tcf-scripts/run-local.bat cm
```

## API

| Method | Path |
|--------|------|
| POST | `/online`, `/cm/online` |

## tcf-ui

- http://localhost:8099/cm/index.html
- http://localhost:8099/cm/index-multi.html

## 의존성

`tcf-core`, `tcf-web`, `common-etc`, `common-updownload`
