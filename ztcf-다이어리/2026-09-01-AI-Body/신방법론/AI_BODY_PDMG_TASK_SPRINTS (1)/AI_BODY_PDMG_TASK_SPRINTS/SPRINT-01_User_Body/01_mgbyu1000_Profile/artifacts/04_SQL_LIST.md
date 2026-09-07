# mgbyu1000 Profile SQL List

- 문서 ID: `BY-SQL-MGBYU1000`
- Program ID: `mgbyu1000`
- Mapper: `mapper/rdw/mg/by/u/mgbyu1000-ORA.xml`
- Namespace: `nhnis.mg.by.u.persistence.dao.mgbyu1000DAO`
- 상태: `BASELINE IDs / SQL PHYSICAL PROPOSED`

---

# 1. PDMG SQL Naming Contract

강제 계약:

```text
DAO Method = Mapper Statement ID
DAO FQCN   = Mapper namespace
```

주 Statement는 Service ID + DML + 순번 규칙을 따른다.
실제 PDMG 자료에는 `_count`, `_exists` 같은 보조 Statement도 존재하므로 필요한 경우 명시적으로 사용한다.

---

# 2. SQL Inventory

| # | Service | DAO Method / Statement ID | Operation | 목적 | Cardinality | Status |
|---:|---|---|---|---|---|---|
| 1 | `mgbyu1000S0` | `mgbyu1000S0_S0` | SELECT | 본인 Profile 조회 | 0..1 | BASELINE |
| 2 | `mgbyu1000C0` | `mgbyu1000C0_exists` | SELECT EXISTS | 중복 Profile 확인 | 1 scalar | PROPOSED helper |
| 3 | `mgbyu1000C0` | `mgbyu1000C0_C0` | INSERT | 최초 Profile 등록 | 1 | BASELINE |
| 4 | `mgbyu1000U0` | `mgbyu1000U0_U0` | UPDATE | Profile 수정 | 0..1 | BASELINE |

삭제 SQL은 현재 Program Registry에 `D0`가 없으므로 생성하지 않는다.

---

# 3. S0 — Profile 조회

## Statement

```text
mgbyu1000S0_S0
```

## Input Key

```text
userId
```

실제 userId는 Client 요청값보다 인증 Context에서 확정하는 방향을 우선 검토한다.

## Logical SQL

```sql
SELECT
    USER_ID,
    DISPLAY_NAME,
    BIRTH_DATE,
    GENDER_CD,
    TRAIN_EXP_CD,
    ACTIVITY_LEVEL_CD,
    CREATED_AT,
    UPDATED_AT
FROM BY_USER_PROFILE
WHERE USER_ID = :userId;
```

Table/Column은 현재 PROPOSED다.

## Result

```text
0 row → Empty vs Business Error = TBD-PRF-003
1 row → 정상
2+ row → PK 위반 상태이므로 데이터 오류
```

---

# 4. C0 — 중복 확인

## Helper Statement

```text
mgbyu1000C0_exists
```

실제 PDMG에 `_exists` 보조 Statement 사례가 있으므로 사용 가능한 패턴이다.

## Logical SQL

```sql
SELECT COUNT(1)
FROM BY_USER_PROFILE
WHERE USER_ID = :userId;
```

또는 DBMS/Mapper 정책에 맞는 존재성 조회로 구현한다.

## Business Result

```text
0 → 등록 가능
1 → Duplicate Profile Business Error
```

PK Unique Constraint를 최종 방어선으로 유지한다.

---

# 5. C0 — Profile 등록

## Statement

```text
mgbyu1000C0_C0
```

## Logical SQL

```sql
INSERT INTO BY_USER_PROFILE (
    USER_ID,
    DISPLAY_NAME,
    BIRTH_DATE,
    GENDER_CD,
    TRAIN_EXP_CD,
    ACTIVITY_LEVEL_CD,
    CREATED_AT,
    UPDATED_AT
) VALUES (
    :userId,
    :displayName,
    :birthDate,
    :genderCode,
    :trainingExperienceCode,
    :activityLevelCode,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
```

`CURRENT_TIMESTAMP` 사용 여부와 Oracle 함수/DB 표준은 실제 DBMS 확정 후 조정한다.

Expected affected rows:

```text
1
```

---

# 6. U0 — Profile 수정

## Statement

```text
mgbyu1000U0_U0
```

## Logical SQL

```sql
UPDATE BY_USER_PROFILE
   SET DISPLAY_NAME       = :displayName,
       BIRTH_DATE         = :birthDate,
       GENDER_CD          = :genderCode,
       TRAIN_EXP_CD       = :trainingExperienceCode,
       ACTIVITY_LEVEL_CD  = :activityLevelCode,
       UPDATED_AT         = CURRENT_TIMESTAMP
 WHERE USER_ID            = :userId;
```

Expected affected rows:

```text
1 → 정상
0 → Profile Not Found Business Error 후보
```

---

# 7. 금지 SQL

```text
DELETE FROM BY_USER_PROFILE ...
```

현재 `mgbyu1000D0` 계약이 없으므로 TASK 04에서 삭제 Statement를 추가하지 않는다.

또한 Profile에서 다음 Body 컬럼을 SELECT/INSERT/UPDATE하지 않는다.

```text
HEIGHT
WEIGHT
BODY_FAT_PERCENTAGE
SKELETAL_MUSCLE_MASS
```

---

# 8. Index Use

모든 MVP SQL Access Pattern:

```text
WHERE USER_ID = ?
```

따라서 Proposed PK `USER_ID`가 Access Path를 제공한다.
추가 Index는 현재 설계하지 않는다.

---

# 9. DAO Signature 예약

실제 DTO 타입은 TASK 05에서 확정하되 메서드명은 다음으로 고정한다.

```text
mgbyu1000S0_S0(...)
mgbyu1000C0_exists(...)
mgbyu1000C0_C0(...)
mgbyu1000U0_U0(...)
```

Return Type 후보:

```text
S0_S0      → Profile DTO/Map 0..1
C0_exists  → int/long
C0_C0      → int
U0_U0      → int
```

정확한 Java Signature는 TASK 06에서 확정한다.

---

# 10. SQL Test Cases

| Test | Expected |
|---|---|
| S0 existing user | 1 row |
| S0 missing user | 0 row |
| C0 new user | insert 1 |
| C0 duplicate user | duplicate rejected |
| U0 existing user | update 1 |
| U0 missing user | update 0 → business handling |
| duplicate direct insert | PK constraint failure |

---

# 11. SQL 결정사항

1. 주 SQL ID는 `S0_S0`, `C0_C0`, `U0_U0` 계약을 유지한다.
2. C0 중복검사용 `_exists`를 PROPOSED helper로 추가한다.
3. Profile SQL은 userId 단건 Access를 기본으로 한다.
4. Delete Statement는 만들지 않는다.
5. 물리 Table `BY_USER_PROFILE`은 아직 PROPOSED다.
6. 최종 SQL 문법은 DBMS/Schema 승인 후 확정한다.
