# mgbyu1000 Profile 물리 데이터 모델

- 문서 ID: `BY-DATA-MGBYU1000-PDM`
- Program ID: `mgbyu1000`
- 작성 단계: `04_TASK_DATA_MODEL`
- 상태: `PHYSICAL PROPOSED / DBMS·Schema TBD`
- 기준일: 2026-09-01

---

# 1. 실제 PDMG DB 자료에서 확인된 사실

## FACT-DB-001 — 물리명은 대문자 SNAKE_CASE 사용 사례가 확인됨

현재 PDMG/NSIGHT 자료의 실제 DDL에는 다음 패턴이 존재한다.

```text
TCF_TX_LOG
TCF_TRANSACTION_CONTROL
OM_SERVICE_CATALOG
OM_USER
OM_AUDIT_LOG
EB_USER
EP_USER_EVENT
```

또한 과거 업무 CRUD 샘플에서는 다음 물리 테이블이 확인된다.

```text
TB_CR_AH_SALES_TIP_RACT
```

따라서 대문자 SNAKE_CASE 자체는 실제 사용 패턴과 일치한다.

## FACT-DB-002 — PK 전략은 하나로 통일되어 있지 않음

실제 자료에는 다음이 모두 존재한다.

```text
단일 문자열 PK
복합 PK
업무 자연키
별도 ID형 PK
```

일부 별도 설계에서는 Oracle Sequence `NEXTVAL` 사례가 있으나,
이를 모든 PDMG 업무 테이블의 필수 정책으로 일반화할 근거는 없다.

## FACT-DB-003 — 현재 PDMG 개발 자료에는 H2와 Oracle 계열 패턴이 혼재

- Framework/OM 개발 DDL: H2 자료 확인
- 업무 Mapper: `-ORA.xml` 규칙
- 과거 업무 CRUD: Oracle/H2 Oracle mode 사용 사례

따라서 Body AI의 **운영 DBMS/Schema는 아직 확정되지 않았다.**

---

# 2. 물리 Table Naming 제안

## 권장 후보

```text
BY_USER_PROFILE
```

상태: `PROPOSED`

선정 이유:

- BY 업무영역을 직접 식별 가능
- `USER_PROFILE` 의미가 명확
- 대문자 SNAKE_CASE 실제 PDMG 패턴과 부합
- 특정 레거시 `TB_CR_AH_*` 분류체계를 근거 없이 복제하지 않음

## 대안

```text
TB_BY_USER_PROFILE
```

최종 Table Prefix 정책(`BY_` vs `TB_BY_`)은 프로젝트 DB 표준 승인 후 확정한다.

**현재 문서에서는 논리적 물리명 후보로 `BY_USER_PROFILE`을 사용하되 SQL 구현 시 승인 확인이 필요하다.**

---

# 3. PK 전략

## Profile 특성

```text
User 1 ── 0..1 UserProfile
```

따라서 별도 `PROFILE_ID`가 없어도 `USER_ID` 하나로 1:1 무결성을 표현할 수 있다.

## 제안

```text
PRIMARY KEY (USER_ID)
```

상태: `PROPOSED`

장점:

- 중복 Profile을 DB Constraint로 방지
- S0/C0/U0의 Access Key와 동일
- 불필요한 Surrogate Key 제거

## Sequence

```text
Profile 전용 Sequence: 현재 불필요
```

단, 조직 DB 표준이 모든 업무 테이블에 Surrogate ID를 강제한다면 재검토한다.

---

# 4. Column Proposal

> Oracle 호환 표현을 기준으로 작성한 제안이며, 실제 DBMS 확정 전 DDL 실행 기준이 아니다.

| Column | Logical | Type 후보 | Null | Key | Sensitive | Status |
|---|---|---|---|---|---|---|
| `USER_ID` | userId | `VARCHAR2(50)` | N | PK | Y | PROPOSED |
| `DISPLAY_NAME` | displayName | `VARCHAR2(100)` | Y |  | Y | PROPOSED |
| `BIRTH_DATE` | birthDate | `DATE` | Y |  | Y | PROPOSED |
| `GENDER_CD` | genderCode | `VARCHAR2(20)` | Y |  | Y | PROPOSED |
| `TRAIN_EXP_CD` | trainingExperienceCode | `VARCHAR2(20)` | Y* |  | Y | PROPOSED |
| `ACTIVITY_LEVEL_CD` | activityLevelCode | `VARCHAR2(20)` | Y* |  | Y | PROPOSED |
| `CREATED_AT` | createdAt | `TIMESTAMP` | N |  | N | PROPOSED |
| `UPDATED_AT` | updatedAt | `TIMESTAMP` | N |  | N | PROPOSED |

`*` Required 여부는 TASK 05에서 확정한다.

### USER_ID 길이

실제 OM_USER 자료에는 `USER_ID VARCHAR(50)` 사용 사례가 있으므로 50을 **참고값**으로 제안한다.
Body AI의 실제 인증 ID 길이/문자열 정책은 인증 설계 확인 후 확정한다.

---

# 5. 제외 Column

다음은 Profile Table에 넣지 않는다.

```text
HEIGHT
WEIGHT
BODY_FAT_PERCENTAGE
SKELETAL_MUSCLE_MASS
```

이유:

```text
Body Measurement = 시간축 신체 데이터의 소유자
Profile          = 사용자 기본 Context의 소유자
```

중복 저장하면 Source of Truth가 둘이 되므로 금지한다.

---

# 6. Constraint Proposal

```text
PK_BY_USER_PROFILE
  PRIMARY KEY (USER_ID)
```

추가 Check Constraint는 코드사전 확정 후 고려한다.

예:

```text
GENDER_CD
TRAIN_EXP_CD
ACTIVITY_LEVEL_CD
```

현재는 코드값이 TBD이므로 CHECK 목록을 임의 생성하지 않는다.

---

# 7. Index Proposal

## MVP

```text
PK(USER_ID)
```

만으로 충분한 것으로 판단한다.

S0/C0/U0 모두 userId 단건 접근이므로 추가 Secondary Index는 현재 근거가 없다.

추후 다음 요구가 생기면 별도 검토한다.

```text
Coach 회원 목록
Admin 검색
활동수준 통계
운동경력 Segment 분석
```

---

# 8. Audit Column

최소 후보:

```text
CREATED_AT
UPDATED_AT
```

`CREATED_BY`, `UPDATED_BY`, `GUID` 등을 업무 Table에 반드시 저장해야 한다는 통일 PDMG DB 기준은 현재 자료에서 확인되지 않았다.
따라서 임의 추가하지 않는다.

감사 요구는 PDMG Audit/MDC와 결합해 TASK 11에서 다시 검증한다.

---

# 9. DDL Reference Proposal

> **실행용 확정 DDL이 아니다.** Table Prefix/DBMS/Schema/암호화 정책 확정 후 재생성한다.

```sql
CREATE TABLE BY_USER_PROFILE (
    USER_ID             VARCHAR2(50)  NOT NULL,
    DISPLAY_NAME        VARCHAR2(100),
    BIRTH_DATE          DATE,
    GENDER_CD           VARCHAR2(20),
    TRAIN_EXP_CD        VARCHAR2(20),
    ACTIVITY_LEVEL_CD   VARCHAR2(20),
    CREATED_AT          TIMESTAMP     NOT NULL,
    UPDATED_AT          TIMESTAMP     NOT NULL,
    CONSTRAINT PK_BY_USER_PROFILE PRIMARY KEY (USER_ID)
);
```

---

# 10. 개인정보 / 보호 후보

| Column | 저장 보호 | 로그 | 조회 노출 | Status |
|---|---|---|---|---|
| USER_ID | 보호 필요 | 원문 금지 | 자기 Context 중심 | BASELINE |
| DISPLAY_NAME | 정책 검토 | 지양 | 필요 시 | PROPOSED |
| BIRTH_DATE | 암호화 후보 | 금지 | 최소화 | PROPOSED |
| GENDER_CD | 정책 검토 | 지양 | 최소화 | PROPOSED |
| TRAIN_EXP_CD | 정책 검토 | 지양 | Coaching 필요 | PROPOSED |
| ACTIVITY_LEVEL_CD | 정책 검토 | 지양 | Coaching 필요 | PROPOSED |

암호화/마스킹은 TASK 11 전 최종 확정한다.

---

# 11. DBMS / Schema TBD

아래는 아직 확정되지 않았다.

```text
운영 DBMS
Schema Name
Table Prefix 표준
Oracle Sequence 표준
날짜/시간 표준 Type
암호화 방식
Audit Column 표준
FK를 물리적으로 설정할지 여부
```

따라서 `BY_USER_PROFILE`은 현재 `PROPOSED` 물리 모델이다.

---

# 12. 물리 모델 결론

```text
Logical Entity : UserProfile
Logical Key    : userId
Proposed Table : BY_USER_PROFILE
Proposed PK    : USER_ID
Sequence       : 사용하지 않음 제안
Extra Index    : 없음 제안
DBMS/Schema    : TBD
```
