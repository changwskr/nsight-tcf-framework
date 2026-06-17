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

**싱글뷰(SV)** 마케팅 업무 서비스입니다. TCF 프레임워크 샘플 Handler(`SV.Sample.inquiry`)가 포함되어 있습니다.

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
  -d @docs/sample-requests/sv-sample-inquiry.json
```

## tcf-ui

- http://localhost:8099/sv/index.html
- http://localhost:8099/sv/index-multi.html

## 참고 소스

`handler/SvSampleInquiryHandler.java` — TCF Handler 구현 예시

## 의존성

`tcf-core`, `tcf-web`, `common-etc`, `common-updownload`
