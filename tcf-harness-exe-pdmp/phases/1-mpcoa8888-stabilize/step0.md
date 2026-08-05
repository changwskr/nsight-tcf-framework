# Step 0: fw-security-isolate

## 읽어야 할 파일

- `../pdmp-service/src/main/java/nhnis/mp/config/SecurityConfig.java`
- `../pdmp-service/src/main/java/nhnis/fw/commons/configuration/SecurityConfig.java`
- `../pdmp-service/src/test/java/nhnis/mp/config/SecurityConfigTest.java`
- `tcf-harness-exe-pdmp/AGENTS.md`

## 작업

`nhnis.fw.commons.configuration.SecurityConfig`가 앱 `nhnis.mp.config.SecurityConfig`와 빈 이름 `securityConfig`로 충돌한다. commons 쪽은 기본 비활성으로 두고, 앱 Security만 로드되게 한다.

- commons `SecurityConfig`에 `@ConditionalOnProperty(name = "nhnis.fw.commons.security.enabled", havingValue = "true")` 적용 (matchIfMissing=false)
- `pdmp-fw`에 동일 파일이 있으면 같은 조건으로 맞춘다
- CORS Bean이 commons에만 있으면 앱이 깨지지 않는지 확인한다. 필요하면 앱 Security에 CORS를 유지한다

## Acceptance Criteria

```bash
cd ../pdmp-service
.\gradlew.bat test --tests nhnis.mp.config.SecurityConfigTest
```

컨텍스트 로딩이 `ConflictingBeanDefinitionException` 없이 되어야 한다.

## 금지사항

- `/api/mp/co/a/8888/**` authenticated 규칙을 풀지 마라. 이유: CRUD 보호 계약이다.
- `mpcoa9999` public 접근을 authenticated로 올리지 마라. 이유: 기존 공개 조회 계약이다.
