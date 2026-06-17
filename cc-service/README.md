# cc-service — Common (공통)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `cc-service` |
| 업무코드 | `CC` |
| 메인 클래스 | `com.nh.nsight.marketing.cc.NsightCcServiceApplication` |
| bootRun 포트 | **8081** |
| WAR | `cc-service.war` |
| Tomcat context | `/cc` |

## 개요

NSIGHT 마케팅 플랫폼 **공통(CC)** 업무 서비스입니다. 모든 거래는 TCF 파이프라인(`POST /online` 또는 `POST /cc/online`)을 통해 처리됩니다.

## 실행

```bash
gradle :cc-service:bootRun
tcf-scripts/run-local.bat cc
```

## API

| Method | Path | 설명 |
|--------|------|------|
| POST | `/online` | 표준 JSON 거래 |
| POST | `/cc/online` | 업무코드 경로 거래 |

## 패키지 구조

```text
com.nh.nsight.marketing.cc
├── handler/    TransactionHandler (serviceId 등록)
├── facade/     업무 Facade
├── service/    업무 Service
├── dao/        DAO
└── rule/       업무 규칙
```

## tcf-ui

- 단일 거래: http://localhost:8099/cc/index.html
- 다중 거래: http://localhost:8099/cc/index-multi.html

## 의존성

`tcf-core`, `tcf-web`, `common-etc`, `common-updownload`
