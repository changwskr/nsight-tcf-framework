# mgbyu1000 Profile 논리 데이터 모델

- 문서 ID: `BY-DATA-MGBYU1000-LDM`
- Program ID: `mgbyu1000`
- 작성 단계: `04_TASK_DATA_MODEL`
- 상태: `BASELINE / 일부 PROPOSED`
- 기준일: 2026-09-01
- 선행 산출물: `01_REQUIREMENTS.md`, `02_SERVICE_REGISTRY.md`
- 주의: `03_ARCHITECTURE.md`가 아직 생성되지 않았으므로 Runtime 경계 관련 사항은 후속 보정 대상이다.

---

# 1. 목적

`mgbyu1000 Profile`이 소유해야 할 데이터의 의미·식별자·관계·생명주기를 정의한다.
이 문서는 물리 Table/Column보다 **업무 의미와 데이터 소유권**을 먼저 고정한다.

---

# 2. 핵심 Entity

## 2.1 UserProfile

`UserProfile`은 회원 1명의 코칭 기본 Context를 보관하는 현재 상태형 Master Entity다.

```text
User Identity
    │ 1
    │
    │ 0..1
    ▼
UserProfile
```

### 논리 식별자

```text
userId
```

- 회원당 활성 Profile은 최대 1건을 기본으로 한다.
- `userId`의 실제 인증 출처(JWT/SSO/Header)는 `TBD-PRF-004`다.
- Profile 자체의 별도 Surrogate ID는 현재 요구사항상 필수 근거가 없다.

---

# 3. 데이터 소유권

Profile이 소유하는 데이터와 다른 Domain이 소유하는 데이터를 분리한다.

| 데이터 | 소유 Domain | Profile 저장 | 상태 | 근거/이유 |
|---|---|---:|---|---|
| 사용자 식별자 | Identity/Auth | 참조키 | BASELINE | Profile 연결 키 |
| 표시명 | User/Profile | O | PROPOSED | 사용자 표시 목적 |
| 생년월일 | User/Profile | O | PROPOSED | 연령 기반 개인화 후보 |
| 성별/성별코드 | User/Profile | O | PROPOSED | 개인화 Context 후보 |
| 운동경력 | User/Profile | O | BASELINE | 상위 요구사항 명시 |
| 활동수준 | User/Profile | O | BASELINE | 상위 요구사항 명시 |
| 키(height) | Body | X | BASELINE 결정 | `BodyMeasurement`가 신체 측정값 소유 |
| 체중/체지방/근육량 | Body | X | BASELINE | `mgbyb1000` 소유 |
| Goal | Goal | X | BASELINE | `mgbyu1100` 소유 |
| Consent | Consent | X | BASELINE | `mgbyu1200` 후보 |

## 핵심 결정

TASK 01의 논리 후보에 있던 `height`는 Profile 물리 저장대상에서 제외한다.
신체 수치를 Profile과 Body에 이중 저장하지 않고 `BodyMeasurement`를 단일 소유자로 둔다.

---

# 4. 논리 Attribute

| Logical Attribute | 의미 | 필수 후보 | 변경 가능 | Sensitive | Status |
|---|---|---:|---:|---:|---|
| `userId` | 회원 식별자 | Y | N | Y | BASELINE |
| `displayName` | 서비스 표시명 | N | Y | Y | PROPOSED |
| `birthDate` | 생년월일 | N | Y | Y | PROPOSED |
| `genderCode` | 성별/개인화 코드 | N | Y | Y | PROPOSED |
| `trainingExperienceCode` | 운동경력 구간 | Y 후보 | Y | Y | BASELINE concept / 코드 TBD |
| `activityLevelCode` | 활동수준 | Y 후보 | Y | Y | BASELINE concept / 코드 TBD |
| `createdAt` | 최초 생성시각 | System | N | N | PROPOSED |
| `updatedAt` | 최종 수정시각 | System | System | N | PROPOSED |

정확한 Required 여부와 코드값은 TASK 05 DTO/Validation 계약에서 확정한다.

---

# 5. Raw / Derived 분류

## Raw / Source Data

```text
userId
displayName
birthDate
genderCode
trainingExperienceCode
activityLevelCode
```

## Derived Data

`mgbyu1000` 자체는 파생 Score를 소유하지 않는다.

```text
Age                     → 계산 시점 Derived
Training Score          → Assessment 소유
Activity-based TDEE     → Nutrition/Algorithm 소유
Readiness               → Recovery/Assessment 소유
```

**원칙:** 계산 결과를 Profile 컬럼으로 중복 저장하지 않는다.

---

# 6. History / Append 판단

`UserProfile`은 현재값 중심 Master이므로 BodyMeasurement처럼 Append-only Entity로 정의하지 않는다.

```text
UserProfile
  S0 조회
  C0 최초 생성
  U0 현재값 변경
```

단, 개인정보 변경 감사/Audit 요구는 운영·보안 정책에서 별도 처리한다.

가능한 후속안:

```text
UserProfile            현재 상태
UserProfileHistory     변경 이력 (필요 시 별도)
Audit Log              접근/변경 감사
```

`UserProfileHistory` 도입 여부는 현재 `TBD`이며 Sprint 01 MVP에 필수로 확정하지 않는다.

---

# 7. Business Key / Cardinality

## 논리 Key

```text
UserProfile.userId
```

## Cardinality

```text
User 1 ───── 0..1 UserProfile
```

## Business Constraint

- 동일 `userId`의 활성 Profile 중복 등록 금지.
- C0는 이미 존재하면 Duplicate Business Error 후보.
- U0는 대상이 없으면 Not Found Business Error 후보.
- S0의 미존재 처리(Empty vs Error)는 여전히 `TBD-PRF-003`.

---

# 8. 조회 패턴

MVP의 핵심 조회는 모두 단건 Key Access다.

```text
S0 : 현재 로그인 Member의 userId로 Profile 0..1건 조회
C0 : 동일 userId 존재 여부 확인 후 1건 등록
U0 : 동일 userId 1건 수정
```

따라서 MVP에서는 `userId` Key 인덱스 외 별도 Secondary Index의 근거가 없다.

Coach/Admin 검색·목록 기능은 별도 Program에서 요구가 생길 때 설계한다.

---

# 9. 개인정보 분류

| Attribute | 분류 | 로그 원문 출력 | AI Context | 비고 |
|---|---|---:|---:|---|
| userId | 식별정보 | 금지/마스킹 | 내부 식별 필요 시 최소화 | BASELINE |
| displayName | 개인정보 | 지양 | 필요 시 | PROPOSED |
| birthDate | 개인정보 | 금지 | 전체 날짜 대신 나이/연령대 Derived 우선 검토 | PROPOSED |
| genderCode | 개인정보 후보 | 지양 | 목적에 필요한 경우만 | PROPOSED |
| trainingExperienceCode | 개인화 정보 | 지양 | Coaching에 필요 | BASELINE concept |
| activityLevelCode | 생활/활동 정보 | 지양 | Coaching에 필요 | BASELINE concept |

암호화 대상 컬럼은 `TBD-PRF-013`이며 물리 보안정책에서 확정한다.

---

# 10. Requirement Traceability

| Requirement | Data Responsibility |
|---|---|
| BY-USR-PRF-001 | `userId` 기반 UserProfile 조회 |
| BY-USR-PRF-002 | UserProfile 최초 생성 |
| BY-USR-PRF-003 | UserProfile 현재값 수정 |
| BY-USR-PRF-004 | `trainingExperienceCode` 관리 |
| BY-USR-PRF-005 | `activityLevelCode` 관리 |
| BY-USR-PRF-006 | 후속 Domain에 필요한 최소 Profile Context 제공 |
| BY-USR-PRF-007 | 식별자/개인정보 Scope 보호 |
| BY-USR-PRF-008 | AI 전달 Profile 최소화 |
| BY-USR-PRF-009 | 민감정보 로그 최소화 |
| BY-USR-PRF-010 | 삭제/Retention 정책은 후속 Security Task |

---

# 11. 결정사항

1. 논리 Entity는 `UserProfile` 하나로 시작한다.
2. 회원당 Profile은 0..1건이다.
3. 논리 Key는 `userId`다.
4. Profile은 현재 상태형 Master이며 Append-only가 아니다.
5. 운동경력과 활동수준은 Profile 소유다.
6. 신체 측정값 `height/weight/bodyFat...`는 Body Domain 소유로 분리한다.
7. Assessment/AI 파생값은 Profile에 저장하지 않는다.
8. MVP 조회는 userId 단건 접근을 기본으로 한다.

---

# 12. TBD

- 실제 Identity/User Master와 FK 연결 여부
- userId 실제 길이/형식
- displayName 저장 필요성
- birthDate/genderCode 필수 여부
- trainingExperienceCode 코드 사전
- activityLevelCode 코드 사전
- Profile History/Audit 별도 Entity 필요 여부
- 삭제/익명화/보존 정책
- 암호화 대상
