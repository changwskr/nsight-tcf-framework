# Security Review — mpcoa8888 stabilize

Date: 2026-08-05  
Scope: `pdmp-service` mpcoa8888 CRUD + FW commons isolation

## Findings

| ID | Severity | Status | Note |
|----|----------|--------|------|
| SEC-01 | High | Fixed | commons `SecurityConfig` vs app `SecurityConfig` bean name clash — commons gated by `nhnis.fw.commons.security.enabled` (default off) |
| SEC-02 | High | Fixed | commons `DefaultFilter` / `WebConfiguration` / `ResponseBodyAdvice` blocked or wrapped TCF traffic — gated by filter/legacy-web flags (default off) |
| SEC-03 | Medium | Accepted | `jwt.enabled=false` locally; `/api/mp/co/a/8888/**` still requires authentication via SecurityFilterChain (SecurityMockMvc `user()` covers test path). Real JWT verification remains off until token issuance is enabled. |
| SEC-04 | Info | Pass | `mpcoa8888-ORA.xml` uses MyBatis `#{}` binding only; no string-concat SQL |
| SEC-05 | Info | Pass | Local JWT secret default is for local profile only; override with `PDMP_JWT_SECRET` |

## Checklist

- [x] `/api/mp/co/a/8888/**` remains `authenticated`
- [x] `/api/mp/co/a/9999/**` remains public (`permitAll` via anyRequest)
- [x] Mapper parameters bound with `#{}`
- [x] No credentials/tokens added to business logs in this change
- [x] Open High issues: 0

## Residual risk

Oracle runtime behavior is unverified in this pass (H2 MODE=Oracle only).
