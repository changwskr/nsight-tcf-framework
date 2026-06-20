# pc-service — Private Customer (고객)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `pc-service` |
| 업무코드 | `PC` |
| 메인 클래스 | `com.nh.nsight.marketing.pc.NsightPcServiceApplication` |
| bootRun 포트 | **8083** |
| WAR | `pc-service.war` |
| Tomcat context | `/pc` |

## 개요

**개인 고객(PC)** 업무 서비스입니다.

## 실행

```bash
gradle :pc-service:bootRun
tcf-scripts/run-local.bat pc
```

## API

| Method | Path |
|--------|------|
| POST | `/online`, `/pc/online` |

## tcf-ui

- http://localhost:8099/pc/index.html
- http://localhost:8099/pc/index-multi.html

## 의존성

`tcf-core`, `tcf-web`, `common-etc`
