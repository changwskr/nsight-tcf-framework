# TASK 11 — mgbyu1000 Profile 보안·비기능·운영 점검

- Program ID: `mgbyu1000`
- 범위: `S0 / C0 / U0`
- 상태: `REVIEW_COMPLETE_WITH_RELEASE_BLOCKERS`
- 기준일: 2026-09-01

## 1. 핵심 결론

정적 코드 수준:

```text
Client userId 제거
→ Trusted User Provider
→ SQL WHERE USER_ID = #{userId}
→ MyBatis #{} 바인딩
→ UI localStorage/console 개인정보 미사용
→ AI/외부 연계 없음
```

Release Blocker:

```text
JWT/Bearer 실제 연결 미완료
Unit/Integration/E2E 미실행
USER_ID Unique/PK 미확정
SQL Parameter PII Logging 미검증
MDC userId와 Verified Principal 미정합
BY BizPrePostAspect 미적용
Profile 삭제/보존 정책 미완료
p95 3초 미검증
```

## 2. 상세 점검

| ID | 영역 | 점검 | 결과 | 근거/해석 | Severity |
|---|---|---|---|---|---|
| `SEC-001` | Access | Client DTO/UI에 userId 입력 없음 | **PASS** | DTOin + UI 정적 검색 | - |
| `SEC-002` | Access | DB USER_ID는 AuthenticatedUserProvider에서 생성 | **PASS** | mgbyu1000Service | - |
| `SEC-003` | Access | 신뢰 사용자 Provider가 optr_eno 미사용 | **PASS** | ServiceContextAuthenticatedUserProvider | - |
| `SEC-004` | Access | non-local 미인증 Fail-Closed | **CONDITIONAL_PASS** | Bridge 존재, JWT E2E 미실행 | P0 |
| `SEC-005` | SQL | MyBatis 문자열 치환 ${} 미사용 | **PASS** | mgbyu1000-ORA.xml | - |
| `SEC-006` | SQL | S0/C0_exists/U0 USER_ID 조건 | **PASS** | Mapper 정적 검색 | - |
| `SEC-007` | Privacy | UI console/localStorage Profile 노출 없음 | **PASS** | index.html 정적 검색 | - |
| `SEC-008` | Privacy | 운영 SQL Parameter PII Masking/DEBUG 정책 | **BLOCKED** | PDMG SQL param debug 가능성, 운영 masking 미검증 | P0 |
| `SEC-009` | Auth | Browser Bearer Authorization 실제 적용 | **BLOCKED** | 공통 service-client Authorization patch는 PROPOSED | P0 |
| `SEC-010` | Data | USER_ID PK/Unique로 동시 중복 방지 | **BLOCKED** | C0 exists→insert 2-step, 물리 DDL 미확정 | P0 |
| `NFR-001` | Performance | S0 1 SELECT / U0 1 UPDATE | **PASS** | 정적 SQL 호출 수 | - |
| `NFR-002` | Performance | C0 DB round trip/경쟁조건 | **REVIEW** | exists + insert 2 round trip; Unique 필요 | P1 |
| `NFR-003` | Performance | p95 ≤ 3초 Runtime 측정 | **BLOCKED** | 실제 DB/TCF/Browser 측정 미실행 | P0 |
| `NFR-004` | Timeout | 외부 연계/AI 호출 없음 | **PASS** | Profile package에 외부 Client/AI dependency 없음 | - |
| `NFR-005` | Timeout | UI > Server > DB Timeout 계층 | **BLOCKED** | UI 10s 후보, Server/DB 실제값 미검증 | P1 |
| `NFR-006` | Availability | AI 장애와 독립 | **PASS** | AI dependency 없음 | - |
| `OPS-001` | Observability | guid/serviceId/sqlId/errCode 추적 기반 | **CONDITIONAL_PASS** | PDMG 공통 MDC/SQL interceptor 의존, BY E2E 미실행 | P1 |
| `OPS-002` | Observability | MDC userId = Verified Principal | **BLOCKED** | 현행 MDC userId는 optr_eno 기반 가능 | P0 |
| `OPS-003` | Observability | BY BizPrePostAspect 적용 | **BLOCKED** | 현행 Pointcut co.a service 한정 | P0 |
| `OPS-004` | Retention | Profile 삭제/보존 정책 | **BLOCKED** | BY-USR-PRF-010 MUST, D0/탈퇴/보존기간 미정 | P0 |
| `OPS-005` | Metrics | 운영 Metric 후보 정의 | **PASS** | TASK 11 문서에 정의 | - |

## 3. 접근통제

```text
Client DTO userId 없음
  ↓
AuthenticatedUserProvider.requireUserId()
  ↓
DAO userId
  ↓
WHERE USER_ID = #{userId}
```

타 회원 ID를 Client가 직접 지정하는 IDOR 경로는 제거됐다.

단 실제 운영은 반드시:

```text
Verified JWT Subject
→ Trusted Principal
→ authenticatedUserId
```

로 연결되어야 한다.

현재 `ssoId` Bridge는 최종 인증 Architecture가 아니다.

## 4. Coach / Admin

`mgbyu1000`에는 `targetUserId`를 추가하지 않는다.

```text
Member → mgbyu1000
Coach  → mgbyh*
Admin  → mgbym*
AI     → Context Builder
```

## 5. 개인정보 / 로그

Profile Field:

```text
displayName
birthDate
genderCode
trainingExperienceCode
activityLevelCode
```

Program/UI에는 DTO 전체 로그, localStorage 저장, hidden userId가 없다.

하지만 PDMG SQL Logger의 Parameter DEBUG 로그에서 PII가 남을 수 있으므로 Release 전 아래 중 하나를 확정한다.

- 운영 SQL Parameter DEBUG 비활성
- Profile Parameter Masking
- PII Field Logger Masking
- 안전한 별도 SQL Telemetry

JWT/Token 원문 로그 금지.

## 6. SQL Injection

Mapper는 `#{...}`만 사용하고 `${...}` 문자열 치환을 사용하지 않는다.

정적 기준 PASS.

## 7. 데이터 무결성

C0:

```text
SELECT COUNT
→ INSERT
```

동시성 Race가 가능하므로 DB가 최종 방어해야 한다.

```text
UNIQUE / PK (USER_ID)
```

필수.

## 8. 성능

```text
S0 = SELECT 1회
C0 = exists + INSERT = 2회
U0 = UPDATE 1회
```

N+1/외부 HTTP/AI 호출 없음.

p95 ≤ 3초는 Runtime 측정 필요.

필수 측정:
- USER_ID Index/PK
- SQL Execution Plan
- DB elapsed
- Pool wait
- TCF elapsed
- Browser E2E p95

## 9. Timeout

```text
Browser/UI
>
PDMG TCF
>
DB
```

계층 원칙은 맞지만 실제 Server/DB timeout 값은 미검증.

## 10. AI 장애 격리

Profile은 AI 의존성이 없어 AI 장애와 독립적으로 동작하도록 설계됐다.

정적 기준 PASS.

## 11. MDC / Observability

논리 MDC:

```text
guid
traceId
userId
serviceId
ip
sqlId
ifId
errCode
```

현행 주의점:

```text
MDC userId = optr_eno 기반 가능
Business USER_ID = authenticatedUserId
```

둘을 같은 Security Principal로 해석하지 않는다.

또 BY Service는 현재 `BizPrePostAspect` Pointcut에서 빠질 수 있어 업무 선후처리 Log Coverage가 불완전하다.

## 12. 삭제 / 보존

`BY-USR-PRF-010`은 MUST이지만 현재:

```text
D0 없음
탈퇴연계 없음
보존기간 없음
Backup 삭제정책 없음
```

따라서 Release Blocker.

## 13. 운영 Metric 후보

- `profile_request_total{serviceId,result}`
- `profile_request_duration_ms{serviceId}`
- `profile_db_duration_ms{sqlId}`
- `profile_auth_failure_total{serviceId}`
- `profile_duplicate_create_total`
- `profile_update_not_found_total`
- `profile_error_total{serviceId,errCode}`
- `db_pool_wait_ms`
- `tcf_timeout_total{serviceId}`
- `profile_mapper_error_total`

PII/JWT 원문을 Metric Label에 넣지 않는다.

## 14. 최종 판정

| 영역 | 판정 |
|---|---|
| 본인 Scope 정적 설계 | PASS |
| SQL Injection | PASS |
| UI 최소노출 | PASS |
| AI 장애 격리 | PASS |
| JWT/운영 인증 | BLOCKED |
| PII Logging | BLOCKED |
| DB Unique/Physical Security | BLOCKED |
| Runtime p95 | BLOCKED |
| Observability Principal | BLOCKED |
| BY Biz Pre/Post | BLOCKED |
| Retention/Delete | BLOCKED |

**TASK 11 = REVIEW_COMPLETE_WITH_RELEASE_BLOCKERS**
