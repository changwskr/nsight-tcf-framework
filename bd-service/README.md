# bd-service — Behavior Data (데이터)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `bd-service` |
| 업무코드 | `BD` |
| 메인 클래스 | `com.nh.nsight.marketing.bd.NsightBdServiceApplication` |
| bootRun 포트 | **8092** |
| WAR | `bd-service.war` |
| Tomcat context | `/bd` |

## 개요

**행동 데이터(BD)** 데이터 업무 서비스입니다.

## 실행

```bash
gradle :bd-service:bootRun
tcf-scripts/run-local.bat bd
```

## API

| Method | Path |
|--------|------|
| POST | `/online`, `/bd/online` |

## tcf-ui

- http://localhost:8099/bd/index.html
- http://localhost:8099/bd/index-multi.html

## 의존성

`tcf-core`, `tcf-web`, `common-etc`
