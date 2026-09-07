# TASK 12 — mgbyu1000 Decision / TBD Register

- State: `BASELINE_CANDIDATE / RELEASE_BLOCKED`

## Closed

- Program ID = `mgbyu1000`
- Service = `S0/C0/U0`
- 업무 Type = lower-leading PDMG naming
- Facade = `application.facade`
- TX Manager = `rdwTransactionManager`
- DAO FQCN = Mapper namespace
- DAO Method = Mapper Statement ID
- UI = `static/mgbyu1000/index.html`
- Client DTO/UI userId 없음
- Profile Key는 Trusted Principal에서 파생
- AI 직접 의존 없음

## P0 Release Blockers

- `TBD-AUTH-001` JWT subject → authenticatedUserId
- `TBD-AUTH-002` Browser Authorization/Refresh
- `TBD-AUTH-003` pdmg-jwt ↔ pdmg-service 검증 정합
- `TBD-DB-001` Physical DB/Schema/Table
- `TBD-DB-002` USER_ID PK/Unique
- `TBD-DB-003` 개인정보 암호화
- `TBD-LOG-001` SQL Parameter PII Masking
- `TBD-OBS-001` MDC userId Principal 정합
- `TBD-AOP-001` BY BizPrePostAspect
- `TBD-TEST-001` Gradle Unit Test
- `TBD-TEST-002` Spring/Mapper Integration
- `TBD-TEST-003` DB Integration
- `TBD-TEST-004` Browser/TCF/JWT E2E
- `TBD-PERF-001` p95 ≤ 3초
- `TBD-RET-001` 삭제/탈퇴/보존

## P1

- CodeSet
- BY 업무 오류코드
- Main Shell Route/Menu
- Error Popup API
- UI/Server/DB Timeout 값
- Profile 미존재 최종 계약

## Next

```text
03_TASK_ARCHITECTURE
→ Patch 보정
→ SSOT 적용
→ TASK10 재실행
→ TASK11 재검증
→ TASK12 재판정
```

```text
P0 > 0
→ BASELINE 금지
```
