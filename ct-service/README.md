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

**콘텐츠(CT)** 지원 업무 서비스입니다.

## 실행

```bash
gradle :ct-service:bootRun
tcf-scripts/run-local.bat ct
```

## API

| Method | Path |
|--------|------|
| POST | `/online`, `/ct/online` |

## tcf-ui

- http://localhost:8099/ct/index.html
- http://localhost:8099/ct/index-multi.html

## 의존성

`tcf-core`, `tcf-web`, `common-etc`, `common-updownload`
