# om-service — Operation Management (운영, 레거시)

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `om-service` |
| 업무코드 | `OM` |
| 메인 클래스 | `com.nh.nsight.marketing.om.NsightOmServiceApplication` |
| bootRun 포트 | **8097** |
| WAR | `om-service.war` |
| Tomcat context | `/om` |

## 개요

**운영관리(OM)** 업무 WAR 모듈입니다. 17개 업무 WAR 일괄 빌드(`buildBusinessWars`)에 포함되며, TCF 마이그레이션 완료본은 **`tcf-om`** 모듈을 사용하세요.

> `tcf-om`과 동일 포트(8097)를 사용합니다. UD·OM admin 기능은 **tcf-om**에 통합되어 있습니다. 동시 기동 불가.

## 실행

```bash
gradle :om-service:bootRun
tcf-scripts/run-local.bat om-service
```

## API

| Method | Path |
|--------|------|
| POST | `/online`, `/om/online` |

## tcf-ui

- http://localhost:8099/om/index.html
- OM 관리 포털(tcf-om 연동): http://localhost:8099/om/admin/dashboard.html

## tcf-om과의 관계

| 모듈 | 설명 |
|------|------|
| `tcf-om` | TCF 마이그레이션 완료본 (**권장**) |
| `om-service` | WAR 배포 세트용 레거시 모듈 |

## 의존성

`tcf-util`, `tcf-core`, `tcf-web`
