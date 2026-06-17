# ss-service — Sales Support (지원)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `ss-service` |
| 업무코드 | `SS` |
| 메인 클래스 | `com.nh.nsight.marketing.ss.NsightSsServiceApplication` |
| bootRun 포트 | **8093** |
| WAR | `ss-service.war` |
| Tomcat context | `/ss` |

## 개요

**영업 지원(SS)** 업무 서비스입니다.

## 실행

```bash
gradle :ss-service:bootRun
tcf-scripts/run-local.bat ss
```

## API

| Method | Path |
|--------|------|
| POST | `/online`, `/ss/online` |

## tcf-ui

- http://localhost:8099/ss/index.html
- http://localhost:8099/ss/index-multi.html

## 의존성

`tcf-core`, `tcf-web`, `common-etc`, `common-updownload`
