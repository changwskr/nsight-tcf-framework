# ic-service — Integration Customer (고객)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `ic-service` |
| 업무코드 | `IC` |
| 메인 클래스 | `com.nh.nsight.marketing.ic.NsightIcServiceApplication` |
| bootRun 포트 | **8082** |
| WAR | `ic-service.war` |
| Tomcat context | `/ic` |

## 개요

**통합 고객(IC)** 업무 서비스입니다. TCF `TransactionHandler` 기반으로 거래를 처리합니다.

## 실행

```bash
gradle :ic-service:bootRun
tcf-scripts/run-local.bat ic
```

## API

| Method | Path |
|--------|------|
| POST | `/online`, `/ic/online` |

## tcf-ui

- http://localhost:8099/ic/index.html
- http://localhost:8099/ic/index-multi.html

## 의존성

`tcf-core`, `tcf-web`, `common-etc`, `common-updownload`
