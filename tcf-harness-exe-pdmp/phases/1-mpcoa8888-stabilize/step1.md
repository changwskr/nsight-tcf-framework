# Step 1: crud-red-green

## 읽어야 할 파일

- `../pdmp-service/docs/superpowers/specs/2026-08-01-mpcoa8888-crud-design.md`
- `../pdmp-service/src/main/java/nhnis/mp/co/a/controller/mpcoa8888Controller.java`
- `../pdmp-service/src/test/java/nhnis/mp/co/a/**/*.java`
- `../pdmp-service/src/main/java/nhnis/fw/commons/jwt/JwtProvider.java`
- `../pdmp-service/src/main/resources/application.yml`

## 작업

기동을 막는 남은 원인(빈 JWT secret, Filter 의존성 등)을 최소 수정으로 제거한 뒤 mpcoa8888 테스트를 GREEN으로 만든다.

- local 프로필에 32자 이상 개발용 JWT secret 기본값을 두거나, `JwtProvider`를 secret 유효 시에만 초기화
- `gradlew.bat test`에서 8888 관련 실패를 모두 해소
- 기존 9999 계약을 깨지 않는다

## Acceptance Criteria

```bash
cd ../pdmp-service
.\gradlew.bat test --tests nhnis.mp.co.a.*
.\gradlew.bat test --tests nhnis.mp.config.SecurityConfigTest
```

## 금지사항

- 승인되지 않은 API/스키마/삭제 의미 변경 금지
- 운영 secret을 저장소에 하드코딩하지 마라. 로컬 기본값만 허용한다
