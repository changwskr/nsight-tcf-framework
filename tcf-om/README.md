# tcf-om — 운영관리 (OM) TCF 서비스

기존 `om-service`를 TCF 프레임워크(`TransactionHandler`, `tcf-core`) 기반으로 마이그레이션한 운영관리 독립 실행 모듈입니다.

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `tcf-om` |
| 업무코드 | `OM` |
| 메인 클래스 | `com.nh.nsight.marketing.om.NsightTcfOmApplication` |
| bootRun 포트 | **8097** |
| WAR | `tcf-om.war` |

> `common-updownload`, `om-service`와 동일 포트(8097)를 사용합니다. 동시 기동 시 포트 충돌이 발생하므로 하나만 실행하세요.

## TCF Handler (22개)

사용자·메뉴·권한·공통코드·배치·캐시·감사로그·대시보드·헬스체크 등 OM 운영 기능을 `OM.*` serviceId로 제공합니다.

예시:

| serviceId | 설명 |
|-----------|------|
| `OM.Sample.inquiry` | 샘플 조회 |
| `OM.User.inquiry` | 사용자 조회 |
| `OM.Menu.inquiry` | 메뉴 조회 |
| `OM.CommonCode.inquiry` | 공통코드 조회 |
| `OM.Batch.execute` | 배치 실행 |

전체 목록은 `tcf-ui/src/main/resources/sample-requests/om-transactions.json` 참고.

## 실행

```bash
gradle :tcf-om:bootRun

# 또는
tcf-scripts/run-local.bat tcf-om
tcf-scripts/run-local.bat om
```

## API

```bash
curl -X POST http://localhost:8097/online \
  -H "Content-Type: application/json" \
  -d @tcf-ui/src/main/resources/sample-requests/om-sample-inquiry.json
```

## 패키지 구조

```text
com.nh.nsight.marketing.om
├── handler/    TransactionHandler (serviceId 등록)
├── facade/     업무 Facade
├── service/    업무 Service
├── dao/        JDBC/MyBatis DAO
├── mapper/     MyBatis Mapper 인터페이스
└── rule/       업무 규칙 검증
```

## tcf-ui 연동

- OM 관리 포털: http://localhost:8099/om/admin/dashboard.html
- JSON 거래 테스트: http://localhost:8099/om/index-multi.html

## om-service와의 관계

| 모듈 | 설명 |
|------|------|
| `tcf-om` | TCF 마이그레이션 완료본 (권장) |
| `om-service` | 17개 업무 WAR 세트에 포함된 레거시 OM 모듈 |
