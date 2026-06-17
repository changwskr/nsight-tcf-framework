# ms-service — Mini Single View (고객)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `ms-service` |
| 업무코드 | `MS` |
| 메인 클래스 | `com.nh.nsight.marketing.ms.NsightMsServiceApplication` |
| bootRun 포트 | **8085** |
| WAR | `ms-service.war` |
| Tomcat context | `/ms` |

## 개요

**미니 싱글뷰(MS)** 업무 서비스입니다.

## 실행

```bash
gradle :ms-service:bootRun
tcf-scripts/run-local.bat ms
```

## API

| Method | Path |
|--------|------|
| POST | `/online`, `/ms/online` |

## tcf-ui

- http://localhost:8099/ms/index.html
- http://localhost:8099/ms/index-multi.html

## 의존성

`tcf-core`, `tcf-web`, `common-etc`, `common-updownload`
