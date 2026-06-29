# tcf-jwt — JWT 인증·토큰 발급 서비스

RS256 JWT Access/Refresh Token 발급·갱신·폐기를 담당하는 TCF 독립 실행 모듈입니다.

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `tcf-jwt` |
| 업무코드 | `JWT` |
| 메인 클래스 | `com.nh.nsight.auth.jwt.NsightJwtServiceApplication` |
| bootRun 포트 | **8110** (local — gateway :8100 과 분리) |
| WAR | `jwt.war` → ztomcat `/jwt` |

## ServiceId

| serviceId | 설명 |
|-----------|------|
| `JWT.Auth.login` | 로그인 → Access + Refresh Token 발급 |
| `JWT.Auth.ssoIssue` | SSO 완료 사용자 JWT pair 발급 (`tcf-om` 내부 호출 전용) |
| `JWT.Auth.refresh` | Refresh Token 갱신 (Rotation) |
| `JWT.Auth.revoke` | Access Token 폐기 (Denylist) |
| `JWT.Auth.logout` | Access + Refresh Token 폐기 |

## 테이블

- `TCF_JWT_TOKEN` — Access Token 이력
- `TCF_REFRESH_TOKEN` — Refresh Token (해시 저장)
- `TCF_TOKEN_DENYLIST` — 폐기 JTI 목록
- `OM_USER` — 사용자 원장 (JWT 발급 시 조회)

## 실행

```bash
# 스크립트 (모듈 scripts/)
tcf-jwt\scripts\build.bat
tcf-jwt\scripts\build.bat run
tcf-jwt\scripts\run-local.bat
tcf-jwt\scripts\deploy.bat

gradle :tcf-jwt:bootRun
curl -X POST http://localhost:8110/online \
  -H "Content-Type: application/json" \
  -d @tcf-jwt/src/main/resources/sample-requests/jwt-auth-login.json
```

## JWK

`GET /.well-known/jwks.json` — RS256 공개키 (Resource Server 검증용)

로컬 테스트 계정: `admin01` / `nsight01!`

## 패키지 구조

```text
com.nh.nsight.auth.jwt
├── NsightJwtServiceApplication      # extends NsightWarBootstrap
├── application/service/     JwtAuthService, JwtAdminService
├── config/                  JwtKeyConfiguration, JwtSecurityConfiguration, JwtSchemaInitializer
├── entry/
│   ├── handler/       JwtAuthLoginHandler, JwtAuthRefreshHandler, JwtAuthRevokeHandler, …
│   ├── facade/        JwtAuthFacade, JwtAdminFacade
│   └── web/           JwkSetController (/.well-known/jwks.json)
├── persistence/
│   ├── dao/           JwtTokenDao, JwtAdminDao
│   └── mapper/        JwtTokenMapper, JwtAdminMapper
└── support/           JwtTokenIssuer, JwtTokenStore, JwtSupport

처리 흐름: entry/handler → entry/facade → application/service → persistence
```
