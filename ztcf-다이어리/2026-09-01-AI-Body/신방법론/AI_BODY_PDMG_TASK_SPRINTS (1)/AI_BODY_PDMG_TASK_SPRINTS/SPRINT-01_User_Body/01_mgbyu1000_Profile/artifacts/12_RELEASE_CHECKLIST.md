# TASK 12 — mgbyu1000 Release Checklist

- Verdict: **RELEASE BLOCKED**
- Program State: **BASELINE_CANDIDATE**
- 판정일: 2026-09-01

## Conformance

| Gate | 결과 |
|---|---|
| Requirement ↔ Program ↔ Service | PASS |
| Program/Service ID | PASS |
| lower-leading 업무 Type | PASS |
| Handler → Facade | PASS |
| Facade Transaction Boundary | PASS |
| Service → Rule/DAO | PASS |
| DAO FQCN = Mapper namespace | PASS |
| DAO Method = Statement ID | PASS |
| UI ServiceId 계약 | PASS |
| Client userId 제거 | PASS |
| SQL `${}` 미사용 | PASS |
| Security/NFR Review 수행 | PASS |
| 실제 Unit Test | FAIL/PENDING |
| Spring/Mapper Integration | FAIL/PENDING |
| DB Integration | FAIL/PENDING |
| Browser/TCF E2E | FAIL/PENDING |
| p95 ≤ 3초 | FAIL/PENDING |
| JWT/Trusted Principal | FAIL/PENDING |
| PII SQL Log 안전성 | FAIL/PENDING |
| USER_ID Unique/PK | FAIL/PENDING |
| BY BizPrePostAspect | FAIL/PENDING |
| 삭제/보존 | FAIL/PENDING |

## Fresh Verification

```text
Conformance exit = 0
Mapper XML = PASS
Node JS = PASS
```

```text
CONFORMANCE PASS
 - required source set exists
 - Program/Service naming contracts pass
 - Facade transaction / Service no-TX contract passes
 - DAO namespace/statement contracts pass
 - auth boundary does not use client optr_eno
 - Profile UI has no client userId/height
 - Mapper XML parses
```

## Definition of Done

```text
Requirement               PASS
Program/Service Registry  PASS
Architecture              FAIL — TASK 03 미실행
Data Model                Physical TBD
DTO Contract              PASS
Backend Design            PASS
UI Design                 PASS
Backend Code              PATCH_READY
UI Code                   PATCH_READY
Test                      PARTIAL
Security/NFR              REVIEW COMPLETE / BLOCKERS
Conformance               STATIC PASS
BASELINE                  NOT APPROVED
```

## Release 차단 사유

1. TASK 03 Architecture 미실행
2. 실제 SSOT Patch 미적용
3. Gradle Unit Test 미실행
4. Spring/Mapper 미검증
5. DB/DDL/Unique 미확정
6. JWT/Bearer/Trusted Principal 미검증
7. PII SQL Log 미검증
8. p95 미측정
9. BY BizPrePostAspect 미적용
10. Retention 미완료

## 현재 상태

```text
BASELINE_CANDIDATE
RELEASE_BLOCKED
STATIC_CONFORMANCE=PASS
RUNTIME_CONFORMANCE=NOT_PROVEN
```

## BASELINE 승격 순서

```text
TASK03
→ SSOT Apply
→ Gradle Test
→ Spring/Mapper
→ DB Integration
→ JWT/TCF/Browser E2E
→ Security Logging
→ p95
→ Retention
→ BASELINE
```
