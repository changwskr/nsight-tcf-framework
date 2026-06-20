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

NSIGHT 마케팅 플랫폼 **Message (MG)** 업무 서비스입니다.

## 샘플 거래

| serviceId | 설명 |
|-----------|------|
| `MG.Sample.inquiry` | 샘플 조회 |

## 실행

```bash
gradle :mg-service:bootRun
tcf-scripts/run-local.bat mg
```

## API

```bash
curl -X POST http://localhost:8096/mg/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/mg-sample-inquiry.json
```

## tcf-ui

- http://localhost:8099/mg/index.html
- http://localhost:8099/mg/index-multi.html

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`
