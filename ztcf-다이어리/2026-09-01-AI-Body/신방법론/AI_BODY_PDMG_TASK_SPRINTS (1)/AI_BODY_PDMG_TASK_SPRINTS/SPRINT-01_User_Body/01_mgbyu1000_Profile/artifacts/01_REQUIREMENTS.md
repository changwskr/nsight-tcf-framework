# mgbyu1000 Profile 요구사항 정의서

- 문서 ID: `BY-REQ-MGBYU1000`
- Program ID: `mgbyu1000`
- 업무영역: User / Profile
- 작성 단계: `01_TASK_REQUIREMENTS`
- 상태: `BASELINE + PROPOSED`
- 기준일: 2026-09-01
- 다음 단계: `02_TASK_PROGRAM_SERVICE`

---

# 1. 목적

`mgbyu1000 Profile` Program은 AI Body Coaching Platform에서 회원 개인의 기본 Profile을 관리한다.

Profile은 다음 후속 기능의 기본 Context가 된다.

```text
Profile
  ↓
Goal
  ↓
Body Measurement
  ↓
Assessment
  ↓
Workout / Nutrition / Recovery
  ↓
Coaching / AI
```

본 단계의 목적은 DB/Java/UI 구현을 시작하기 전에
`누가 / 무엇을 / 어떤 조건으로 / 어디까지` 수행하는지를 요구사항 수준에서 고정하는 것이다.

---

# 2. 기준 근거

## BASELINE

상위 요구사항에서 User 영역은 다음을 포함한다.

```text
회원가입
개인 Profile
운동경력 / 활동수준
복수 Goal
개인정보 / 동의
```

MVP MUST에는 `Profile`이 포함된다.

접근통제 원칙:

```text
Member        본인 데이터만 접근
Coach         승인된 담당 회원만 접근
Administrator 운영 책임 범위에 따른 최소권한
AI            필요한 Context만 제공
```

## PROPOSED

현재 Program / Service Registry 초안:

```text
mgbyu1000S0  Profile 조회
mgbyu1000C0  Profile 등록
mgbyu1000U0  Profile 수정
```

Program 번호와 Service ID는 Registry 최종 승인 전까지 `PROPOSED`다.

---

# 3. Program 범위

## 3.1 In Scope

`mgbyu1000`의 핵심 범위:

1. 로그인한 Member의 본인 Profile 조회
2. Profile이 없는 Member의 최초 Profile 등록
3. 기존 Profile 수정
4. 운동경력 관리
5. 활동수준 관리
6. 후속 Goal / Body / Assessment가 사용할 기본 Profile Context 제공
7. Profile 개인정보의 접근통제 및 최소 노출

## 3.2 Out of Scope

다음은 `mgbyu1000`에서 직접 구현하지 않는다.

| 대상 | 처리 방향 | 상태 |
|---|---|---|
| 회원가입/인증 자체 | PDMG JWT/SSO 및 별도 인증 흐름 | TBD |
| Goal | `mgbyu1100` | PROPOSED |
| 개인정보/AI/마케팅 동의 | `mgbyu1200 Consent` | PROPOSED |
| Body Measurement | `mgbyb1000` | PROPOSED |
| Coach Dashboard | `mgbyh*` 계열 | PROPOSED |
| 관리자 사용자관리 | `mgbym*` 계열 | PROPOSED |
| 의료정보/진단 | 플랫폼 범위 밖 | BASELINE |
| Profile 삭제 거래 | 현재 Registry에 D0 없음 | TBD |

---

# 4. Actor 및 접근 범위

| Actor | 조회 | 등록 | 수정 | 비고 | 상태 |
|---|---:|---:|---:|---|---|
| Member | 허용 | 허용 | 허용 | 본인 Profile만 | BASELINE |
| Coach | 제한 | 금지 | 금지 | 승인된 담당 회원 조회 요구는 존재하나 `mgbyu1000` 직접 접근 여부는 추후 권한설계에서 결정 | TBD |
| Administrator | 제한 | 금지 | 제한 | 운영 책임 범위의 최소권한 원칙. 직접 CRUD 범위는 관리자 Program에서 결정 | TBD |
| AI Coach | 직접 접근 금지 | 금지 | 금지 | Coaching Context Builder를 통해 필요한 최소 Profile만 사용 | BASELINE |

핵심 원칙:

```text
Client가 임의 userId를 지정하여 타 회원 Profile에 접근하면 안 된다.
```

실제 사용자 식별값을 JWT/SSO/Header 중 어디에서 취득할지는
`pdmg-jwt`와 실제 운영 인증정책 확인 후 결정한다.

---

# 5. 요구사항 목록

| Requirement ID | Actor | Requirement | Precondition | Input | Output | Business Rule | Error Case | Security Scope | MVP | Status |
|---|---|---|---|---|---|---|---|---|---|---|
| `BY-USR-PRF-001` | Member | 회원은 자신의 Profile을 조회할 수 있어야 한다. | 인증 완료 | 인증 사용자 Context, 조회조건(필요 시) | 본인 Profile | 타 회원 Profile 조회 금지 | 미인증, Profile 없음, 권한오류 | SELF | MUST | BASELINE |
| `BY-USR-PRF-002` | Member | Profile이 없는 회원은 최초 Profile을 등록할 수 있어야 한다. | 인증 완료, Profile 미존재 | Profile 입력값 | 등록 결과 / Profile | 회원당 현재 Profile의 중복 생성 방지 정책 필요 | 필수값 누락, 중복 Profile, Validation 오류 | SELF | MUST | BASELINE |
| `BY-USR-PRF-003` | Member | 회원은 기존 자신의 Profile을 수정할 수 있어야 한다. | 인증 완료, Profile 존재 | 변경 가능한 Profile 값 | 수정 결과 / Profile | 본인 데이터만 수정 | Profile 없음, Validation 오류, 권한오류 | SELF | MUST | BASELINE |
| `BY-USR-PRF-004` | Member | Profile은 사용자의 운동경력을 관리할 수 있어야 한다. | Profile 등록/수정 | training experience | Profile | 값 체계는 코드/Enum 정책 확정 필요 | 허용되지 않은 값 | SELF | MUST | BASELINE |
| `BY-USR-PRF-005` | Member | Profile은 사용자의 활동수준을 관리할 수 있어야 한다. | Profile 등록/수정 | activity level | Profile | 값 체계는 코드/Enum 정책 확정 필요 | 허용되지 않은 값 | SELF | MUST | BASELINE |
| `BY-USR-PRF-006` | System | Profile은 Goal/Body/Assessment 등 후속 Domain이 참조 가능한 사용자 기본 Context를 제공해야 한다. | Profile 존재 | 내부 사용자 식별 Context | 필요한 Profile 정보 | Domain별 필요한 정보만 전달 | Profile 없음 | INTERNAL | MUST | BASELINE |
| `BY-USR-PRF-007` | System | Profile의 개인정보는 최소권한 원칙으로 보호되어야 한다. | 인증/인가 완료 | 접근주체 | 허용된 필드 | Member 본인, Coach 승인회원, Admin 최소권한 원칙 | Unauthorized / Forbidden | ROLE/SCOPE | MUST | BASELINE |
| `BY-USR-PRF-008` | AI Coach | AI는 Profile 전체를 직접 CRUD하지 않고 Coaching 목적에 필요한 최소 Context만 전달받아야 한다. | AI Coaching 실행 | Context Builder 결과 | 최소 Profile Context | 전체 개인정보 무제한 전송 금지 | Context 과다노출 | MINIMUM_CONTEXT | MUST | BASELINE |
| `BY-USR-PRF-009` | System | Profile 관련 민감정보를 운영로그에 과도하게 남기지 않아야 한다. | 거래 수행 | 로그 Context | 추적정보 | 개인정보/민감정보 로그 최소화 | 민감정보 노출 | LOG_MASKING | MUST | BASELINE |
| `BY-USR-PRF-010` | Member | Profile 관련 개인정보 삭제/보존 요구에 대응할 수 있어야 한다. | 삭제/탈퇴 요청 | 사용자 요청 | 정책에 따른 처리결과 | 실제 삭제 범위/보존기간은 별도 정책 | 보존정책 충돌 | PRIVACY | MUST | BASELINE/TBD |

---

# 6. Service 거래 분해 준비

현재 요구사항은 다음 3개 거래로 분해 가능하다.

## 조회 후보

```text
Requirement
BY-USR-PRF-001
BY-USR-PRF-006
BY-USR-PRF-007
BY-USR-PRF-009

        ↓

PROPOSED Service
mgbyu1000S0
```

## 등록 후보

```text
Requirement
BY-USR-PRF-002
BY-USR-PRF-004
BY-USR-PRF-005
BY-USR-PRF-007
BY-USR-PRF-009

        ↓

PROPOSED Service
mgbyu1000C0
```

## 수정 후보

```text
Requirement
BY-USR-PRF-003
BY-USR-PRF-004
BY-USR-PRF-005
BY-USR-PRF-007
BY-USR-PRF-009

        ↓

PROPOSED Service
mgbyu1000U0
```

삭제 요구는 존재하지만 현재 `mgbyu1000D0`은 Registry에 없다.
따라서 삭제/탈퇴 데이터 정책은 `TBD`로 다음 설계 단계에 전달한다.

---

# 7. 입력값 후보

> 이 절은 요구사항 수준의 논리 후보이며 DTO/DB 스키마가 아니다.

| Logical Field | 목적 | 생성/수정 입력 | 조회 출력 | 상태 |
|---|---|---:|---:|---|
| `userId` | 사용자 식별 | Client 입력 여부 TBD | 필요 시 | TBD |
| `displayName` | 표시명 | O | O | PROPOSED |
| `birthDate` | 연령/개인화 Context | O | 제한적 | PROPOSED |
| `gender` | 개인화 Context | O | 제한적 | PROPOSED |
| `height` | 신체/계산 Context | O | O | PROPOSED |
| `trainingExperience` | 운동경력 | O | O | BASELINE concept |
| `activityLevel` | 활동수준 | O | O | BASELINE concept |
| `createdAt` | 생성시각 | 시스템 | 필요 시 | PROPOSED |
| `updatedAt` | 수정시각 | 시스템 | 필요 시 | PROPOSED |

주의:

- `trainingExperience`, `activityLevel`은 상위 요구사항에서 직접 확인되는 Profile 항목이다.
- `displayName`, `birthDate`, `gender`, `height` 등은 현재 설계 초안의 논리 후보이며 다음 DTO/Data Task에서 재검토한다.
- 개인정보 최소수집 원칙에 따라 필요성이 입증되지 않는 항목은 제거할 수 있다.

---

# 8. 정상 시나리오

## 8.1 Profile 최초 등록

```text
Member 인증
   ↓
Profile 존재여부 확인
   ↓
미존재
   ↓
Profile 입력
   ↓
Validation
   ↓
본인 식별 Context 확인
   ↓
Profile 등록
   ↓
등록결과 반환
```

## 8.2 Profile 조회

```text
Member 인증
   ↓
본인 식별 Context 확인
   ↓
Profile 조회
   ↓
허용 필드만 반환
```

## 8.3 Profile 수정

```text
Member 인증
   ↓
본인 Profile 확인
   ↓
변경값 입력
   ↓
Validation
   ↓
Profile 수정
   ↓
수정결과 반환
```

---

# 9. 예외 / 오류 시나리오

| Case | 기대 처리 | 상태 |
|---|---|---|
| 미인증 사용자 | 인증 오류 | BASELINE |
| 타 사용자 Profile 접근 | 권한 오류 | BASELINE |
| Profile 미존재 조회 | 표준 오류 또는 Empty 처리 정책 필요 | TBD |
| Profile 중복 등록 | 중복 방지 오류 | PROPOSED |
| 필수값 누락 | Validation 오류 | BASELINE |
| 허용되지 않은 운동경력/활동수준 | Validation 오류 | PROPOSED |
| Profile 수정 대상 없음 | 표준 업무 오류 | PROPOSED |
| DB/System 오류 | PDMG Global Error 체계 | BASELINE |
| AI 장애 | Profile 조회/등록/수정에는 영향 없어야 함 | BASELINE |

---

# 10. 개인정보 / 민감정보 분류

Profile은 개인정보를 포함하는 업무로 취급한다.

## 보호 원칙

- 전송 구간 보호
- 저장 보호
- 최소권한
- 로그 최소화
- 삭제 요청 지원
- AI 전달 데이터 최소화

## 필드별 민감도

정확한 개인정보 등급/암호화 컬럼은 보안정책과 물리 DB 설계에서 확정한다.

현재 단계:

```text
Profile 전체       개인정보 포함
birthDate          개인정보 후보
gender             개인정보 후보
height             신체정보 후보
trainingExperience 개인화 정보
activityLevel      생활/활동 정보
```

상세 등급은 `TBD`.

---

# 11. 비기능 요구사항

| 항목 | 요구 | 상태 |
|---|---|---|
| 응답시간 | 일반 화면 p95 ≤ 3초 | BASELINE |
| UX | Mobile First | BASELINE |
| 데이터 | 안정적 장기 보존 | BASELINE |
| 보안 | 최소권한 / 개인정보 보호 | BASELINE |
| 가용성 | AI 장애와 독립적으로 Profile CRUD 정상 동작 | BASELINE |
| 추적성 | ServiceId / 사용자 / 오류 추적 가능 | BASELINE |
| 로그 | 개인정보/민감정보 최소화 | BASELINE |

---

# 12. 결정사항

이번 TASK에서 다음을 요구사항 Baseline으로 고정한다.

1. `mgbyu1000`은 Member의 Profile 조회/최초등록/수정을 담당한다.
2. Profile은 MVP MUST 범위다.
3. 핵심 업무 요구는 조회/등록/수정 3개로 분해 가능하다.
4. Member는 본인 Profile만 접근한다.
5. AI는 Profile CRUD 주체가 아니다.
6. AI에는 필요한 최소 Profile Context만 전달한다.
7. Goal/Consent/Body/Coach/Admin 기능은 해당 별도 Program으로 분리한다.
8. Profile은 개인정보로 취급하고 최소권한/로그 최소화 원칙을 적용한다.
9. DB/DTO/UI 상세구조는 이번 TASK에서 확정하지 않는다.

---

# 13. 미결사항(TBD)

다음은 후속 TASK에서 결정해야 한다.

| TBD ID | 항목 | 결정 시점 |
|---|---|---|
| `TBD-PRF-001` | `mgbyu1000` Program 번호 최종 승인 | TASK 02 |
| `TBD-PRF-002` | S0/C0/U0 Service ID 최종 승인 | TASK 02 |
| `TBD-PRF-003` | Profile 없음 조회 시 Empty vs 업무오류 | TASK 02/05 |
| `TBD-PRF-004` | 사용자 식별값을 JWT/SSO/Header 중 어디서 취득하는지 | TASK 03/05 |
| `TBD-PRF-005` | `userId`를 Client DTO에서 받을지 인증 Context로 강제할지 | TASK 05 |
| `TBD-PRF-006` | Profile 논리 필드 최종 목록 | TASK 04/05 |
| `TBD-PRF-007` | trainingExperience 코드값 | TASK 04/05 |
| `TBD-PRF-008` | activityLevel 코드값 | TASK 04/05 |
| `TBD-PRF-009` | Profile 중복 판단 기준 | TASK 04 |
| `TBD-PRF-010` | Profile 삭제/탈퇴 시 데이터 처리 | Security/Retention Task |
| `TBD-PRF-011` | Coach가 `mgbyu1000S0`을 직접 호출할 수 있는지 | Security/Architecture Task |
| `TBD-PRF-012` | Admin 직접 Profile 수정 허용 여부 | Security/Admin Program Task |
| `TBD-PRF-013` | 개인정보 암호화/마스킹 대상 필드 | TASK 04/11 |

---

# 14. Registry 변경 후보

다음 Mapping을 TASK 02의 Service Registry 입력으로 전달한다.

| Requirement ID | Program ID | Service ID 후보 | Transaction | Status |
|---|---|---|---|---|
| `BY-USR-PRF-001` | `mgbyu1000` | `mgbyu1000S0` | 조회 | PROPOSED |
| `BY-USR-PRF-002` | `mgbyu1000` | `mgbyu1000C0` | 등록 | PROPOSED |
| `BY-USR-PRF-003` | `mgbyu1000` | `mgbyu1000U0` | 수정 | PROPOSED |
| `BY-USR-PRF-004` | `mgbyu1000` | `C0/U0` | 등록/수정 | PROPOSED |
| `BY-USR-PRF-005` | `mgbyu1000` | `C0/U0` | 등록/수정 | PROPOSED |
| `BY-USR-PRF-006` | `mgbyu1000` | `S0` | 조회 | PROPOSED |

---

# 15. 테스트 결과

현재 단계는 요구사항 정의 단계이므로 실행 코드 테스트는 수행하지 않는다.

대신 요구사항 Completeness를 점검했다.

- [x] Actor 정의
- [x] 정상 시나리오
- [x] 예외 시나리오
- [x] 입력/출력 논리 후보
- [x] 개인정보/보안 Scope
- [x] MVP 여부
- [x] Working Requirement ID
- [x] Service 거래 분해 준비
- [x] TBD 목록
- [x] 다음 TASK 입력정보

---

# 16. 다음 TASK 입력

다음 `02_TASK_PROGRAM_SERVICE/SPRINT.md`에서는 아래 내용을 입력으로 사용한다.

```text
Program ID 후보
mgbyu1000

Service ID 후보
mgbyu1000S0
mgbyu1000C0
mgbyu1000U0

Working Requirement
BY-USR-PRF-001 ~ BY-USR-PRF-010

핵심 Actor
Member

보안 기준
본인 데이터만 접근

AI
직접 CRUD 금지
필요 최소 Context만 사용

TBD
TBD-PRF-001 ~ TBD-PRF-013
```

---

# 17. TASK 01 완료 게이트

- [x] 요구사항 산출물이 존재한다.
- [x] BASELINE / PROPOSED / TBD가 구분되어 있다.
- [x] PDMG 표준과 충돌하는 DB/Java/UI 상세설계를 선행하지 않았다.
- [x] 다음 TASK가 추가 질문 없이 Program/Service Registry 작업을 시작할 수 있다.

**TASK 01 상태: COMPLETE**
