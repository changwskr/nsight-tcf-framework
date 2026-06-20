# mg-service — Message (지원)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `mg-service` |
| 업무코드 | `MG` |
| 메인 클래스 | `com.nh.nsight.marketing.mg.NsightMgServiceApplication` |
| bootRun 포트 | **8096** |
| WAR | `mg-service.war` |
| Tomcat context | `/mg` |

## 개요

**메시지(MG)** 지원 업무 서비스입니다.

## 실행

```bash
gradle :mg-service:bootRun
tcf-scripts/run-local.bat mg
```

## API

| Method | Path |
|--------|------|
| POST | `/online`, `/mg/online` |

## tcf-ui

- http://localhost:8099/mg/index.html
- http://localhost:8099/mg/index-multi.html

## 의존성

`tcf-core`, `tcf-web`, `common-etc`
