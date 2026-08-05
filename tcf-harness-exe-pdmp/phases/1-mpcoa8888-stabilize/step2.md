# Step 2: security-review

## 읽어야 할 파일

- `../pdmp-service/src/main/java/nhnis/mp/config/SecurityConfig.java`
- `../pdmp-service/src/main/resources/rdw.mp.co.a/mpcoa8888-ORA.xml`
- `tcf-harness-exe-pdmp/skills/pdmp-security/SKILL.md`

## 작업

보안 경계를 점검하고 `phases/1-mpcoa8888-stabilize/security-review.md`에 결과를 남긴다.

체크리스트:
- `/api/mp/co/a/8888/**` authenticated 유지
- MyBatis `#{}` 바인딩만 사용 (문자열 연결 SQL 없음)
- 로그/응답에 토큰·비밀번호·개인정보 미노출
- JWT enabled=false여도 인가 규칙(SecurityMockMvc)과 경로 보호 의이 문서화됨

## Acceptance Criteria

```bash
# security-review.md 존재 및 미해결 High 이슈 0건
Test-Path phases/1-mpcoa8888-stabilize/security-review.md
```

## 금지사항

- JWT 보호 경로를 permitAll로 바꾸지 마라
