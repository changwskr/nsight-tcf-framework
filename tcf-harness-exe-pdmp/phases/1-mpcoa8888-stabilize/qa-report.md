# QA Report — mpcoa8888 stabilize

Date: 2026-08-05  
Phase: `1-mpcoa8888-stabilize`

## Commands

| Command | Exit | Evidence |
|---------|------|----------|
| `gradlew.bat test --tests nhnis.mp.config.SecurityConfigTest --tests nhnis.mp.co.a.*` | 0 | BUILD SUCCESSFUL |
| `gradlew.bat test war` | 0 | BUILD SUCCESSFUL |

## Summary

- FW commons Security/Filter/legacy-web defaults disabled so app TCF Security owns the chain.
- Local JWT secret default added for `JwtProvider` init when `PDMP_JWT_SECRET` is unset.
- mpcoa8888 CRUD APIs and tests remain the contract from `2026-08-01-mpcoa8888-crud-design.md`.

## Environment notes

- H2 (`MODE=Oracle`): verified via tests
- Oracle: **unverified** in this QA pass
