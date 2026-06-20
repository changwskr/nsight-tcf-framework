# pc-service — Private Customer (고객)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `pc-service` |
| 업무코드 | `PC` |
| 메인 클래스 | `com.nh.nsight.marketing.pc.NsightPcServiceApplication` |
| bootRun 포트 | **8083** |
| WAR | `pc-service.war` |
| Tomcat context | `/pc` |

## 개요

NSIGHT 마케팅 플랫폼 **Private Customer (PC)** 업무 서비스입니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `PC.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :pc-service:bootRun
tcf-scripts/run-local.bat pc
```

## API

```bash
curl -X POST http://localhost:8083/pc/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/pc-sample-inquiry.json
```

## tcf-ui

- http://localhost:8099/pc/index.html
- http://localhost:8099/pc/index-multi.html

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`
