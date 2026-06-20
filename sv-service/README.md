# sv-service — Single View (마케팅)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `sv-service` |
| 업무코드 | `SV` |
| 메인 클래스 | `com.nh.nsight.marketing.sv.NsightSvServiceApplication` |
| bootRun 포트 | **8086** |
| WAR | `sv-service.war` |
| Tomcat context | `/sv` |

## 개요

NSIGHT 마케팅 플랫폼 **Single View (SV)** 업무 서비스입니다. TCF 샘플 Handler가 포함되어 있으며, 모든 거래는 TCF 파이프라인(`POST /online` 또는 `POST /sv/online`)을 통해 처리됩니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `SV.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :sv-service:bootRun
tcf-scripts/run-local.bat sv
```

## API

```bash
curl -X POST http://localhost:8086/sv/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/sv-sample-inquiry.json
```

## 패키지 구조

```text
com.nh.nsight.marketing.sv
├── handler/    TransactionHandler (serviceId 등록)
├── facade/     업무 Facade
├── service/    업무 Service
├── dao/        DAO
└── rule/       업무 규칙
```

## tcf-ui

- 단일 거래: http://localhost:8099/sv/index.html
- 다중 거래: http://localhost:8099/sv/index-multi.html

## 참고 소스

`handler/SvSampleInquiryHandler.java` — TCF Handler 구현 예시

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`
