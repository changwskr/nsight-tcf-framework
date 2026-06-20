# cs-service — Common Service (지원)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `cs-service` |
| 업무코드 | `CS` |
| 메인 클래스 | `com.nh.nsight.marketing.cs.NsightCsServiceApplication` |
| bootRun 포트 | **8094** |
| WAR | `cs-service.war` |
| Tomcat context | `/cs` |

## 개요

**공통 서비스(CS)** 지원 업무 서비스입니다.

## 실행

```bash
gradle :cs-service:bootRun
tcf-scripts/run-local.bat cs
```

## API

| Method | Path |
|--------|------|
| POST | `/online`, `/cs/online` |

## tcf-ui

- http://localhost:8099/cs/index.html
- http://localhost:8099/cs/index-multi.html

## 의존성

`tcf-core`, `tcf-web`, `common-etc`
