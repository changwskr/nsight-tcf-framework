# TASK 10 — mgbyu1000 Profile 테스트 결과

- Program ID: `mgbyu1000`
- Service: `mgbyu1000S0 / C0 / U0`
- 상태: `PARTIAL_VERIFIED / INTEGRATION_BLOCKED`
- 기준일: 2026-09-01

## 실행 결과

| Test Level | 결과 | 비고 |
|---|---|---|
| Static Conformance | PASS | `tests/conformance_test.py` |
| Mapper XML Parse | PASS | MyBatis XML |
| JavaScript Syntax | PASS | `node --check` |
| Test Source 존재 | PASS | Rule/Service JUnit source |
| Unit Runtime | NOT RUN | 실제 PDMG Gradle classpath 필요 |
| Spring/Mapper Integration | NOT RUN | SSOT 적용 필요 |
| DB Integration | NOT RUN | 승인 DDL/DB 필요 |
| Browser/TCF E2E | NOT RUN | JWT/CORS/DB 필요 |
| Performance p95 ≤ 3초 | NOT RUN | Runtime 필요 |

## Fresh Conformance

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

## 포함 테스트 Source

```text
ProfileValidationRuleTest.java
mgbyu1000ServiceTest.java
```

시나리오:
- 운동경력 필수
- 활동수준 필수
- 미래 생년월일 거부
- 최소 유효 Profile
- 중복 Profile 등록 거부
- 등록 affected row = 1

## 미실행 핵심

- JWT 없음
- 타 사용자 식별자 주입
- Profile 미존재 U0
- 동시 중복등록/Unique Constraint
- DB Rollback
- Mapper Binding
- CORS/JWT/Refresh
- Timeout
- E2E

## 판정

```text
TASK 10 = PARTIAL_VERIFIED
Release Test Gate = FAIL
```
