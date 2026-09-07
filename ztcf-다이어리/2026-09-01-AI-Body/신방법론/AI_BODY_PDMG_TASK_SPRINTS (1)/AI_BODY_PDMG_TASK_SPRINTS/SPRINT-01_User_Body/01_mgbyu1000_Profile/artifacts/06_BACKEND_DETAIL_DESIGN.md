# mgbyu1000 Profile Backend 상세설계

- 문서 ID: `BY-BE-MGBYU1000`
- Program ID: `mgbyu1000`
- 작성 단계: `06_TASK_BACKEND_DESIGN`
- 상태: `DESIGN COMPLETE / IMPLEMENTATION BLOCKED BY TASK03 AUTH GAP`
- 기준일: 2026-09-01
- 다음 정상 단계: `07_TASK_UI_DESIGN`
- 실제 구현 전 필수 선행: `03_TASK_ARCHITECTURE`

---

# 1. 목적

실제 Java/MyBatis 코딩 전에 `mgbyu1000 Profile`의 Backend 구조를
PDMG AS-IS 계약에 맞춰 파일·타입·메서드·Transaction·DAO Statement 단위로 고정한다.

대상 거래:

```text
mgbyu1000S0  본인 Profile 조회
mgbyu1000C0  최초 Profile 등록
mgbyu1000U0  Profile 수정
```

---

# 2. 선행 산출물

```text
01_REQUIREMENTS.md
02_SERVICE_REGISTRY.md
04_LOGICAL_DATA_MODEL.md
04_PHYSICAL_DATA_MODEL.md
04_SQL_LIST.md
05_DTO_MESSAGE_SPEC.md
```

현재 누락:

```text
03_ARCHITECTURE.md
```

따라서 이번 문서는 Backend 구조를 상세설계하지만
**인증된 사용자 식별자 연결 코드는 확정하지 않는다.**

---

# 3. 실제 PDMG에서 확인한 구현 FACT

## FACT-BE-001 — Handler는 Program 단위

```text
mgbyu1000S0 ┐
mgbyu1000C0 ├─> mgbyu1000Handler
mgbyu1000U0 ┘
```

`TransactionHandler`:

```text
serviceIds()
handle(Object dtoBody, TransactionContext context)
```

Handler는 Facade만 생성자 주입한다.

금지:

```text
Handler → Service 직접 호출
Handler → DAO
Handler → ObjectMapper
Handler @Transactional
```

## FACT-BE-002 — Handler Registry Key는 문자열 Service ID

기동 시 `serviceIds()` 목록이 Dispatcher Registry에 등록된다.

중복 Service ID는 기동 실패 대상이다.

등록 목록과 `handle()` switch Case가 불일치하면 Runtime Failure가 발생한다.

## FACT-BE-003 — Facade는 Object → typed DTO 변환 경계

TCF ON:

```text
Handler
  → Object dtoBody
  → Facade.mgbyu1000S0(Object)
  → ObjectMapper.convertValue
  → mgbyu1000S0DTOin
  → Service.mgbyu1000S0(DTOin)
```

## FACT-BE-004 — Transaction은 Facade에 선언

조회:

```java
@Transactional(
    transactionManager = "rdwTransactionManager",
    readOnly = true
)
```

등록/수정:

```java
@Transactional(
    transactionManager = "rdwTransactionManager",
    rollbackFor = Exception.class
)
```

TCF ON + Timeout ON이면 Worker의 `TransactionTemplate`이
더 바깥 Transaction을 열고 Facade REQUIRED가 참여할 수 있다.

## FACT-BE-005 — Service는 Service ID 메서드 사용

```text
public mgbyu1000S0DTOout mgbyu1000S0(mgbyu1000S0DTOin in)
public mgbyu1000C0DTOout mgbyu1000C0(mgbyu1000C0DTOin in)
public mgbyu1000U0DTOout mgbyu1000U0(mgbyu1000U0DTOin in)
```

Service에는 기본 `@Transactional`을 붙이지 않는다.

## FACT-BE-006 — DAO는 @RDWMapper Interface

```text
DAO FQCN
=
Mapper namespace
```

```text
DAO method
=
Mapper statement id
```

PDMG 실제 DAO는 `Map<String,Object>` 입력/결과 패턴을 사용한다.

## FACT-BE-007 — `_exists` 보조 Statement는 AS-IS 허용 패턴

따라서 C0 중복 검사에:

```text
mgbyu1000C0_exists
```

를 사용할 수 있다.

---

# 4. Critical Security GAP

현재 PDMG Source 분석상:

```text
Bearer JWT
   ↓ validate
JWT sub / ssoId
   ↓
request attribute "ssoId"
```

까지는 확인된다.

그러나:

```text
ssoId
   ↓
Trusted Business Principal
   ↓
ServiceContext.userContext
   ↓
Business USER_ID
```

연결 구현은 현재 확인되지 않는다.

반면 현재 Header:

```text
hdr_nhnis.sys_comm.optr_eno
```

는 Client 전문에서 들어올 수 있고,
현재 MDC `userId`도 이 값에서 설정된다.

## Backend 설계 강제 규칙

```text
optr_eno
MDC userId
Client dto.userId
```

를 Profile 소유권 검증의 **신뢰된 userId로 사용하지 않는다.**

구현 전 TASK 03 Architecture에서 반드시:

```text
Verified JWT sub / Trusted Principal
        ↓
authenticatedUserId
        ↓
mgbyu1000 Service
        ↓
DAO USER_ID
```

경로를 확정한다.

이 문제는 기능 개선사항이 아니라 **본인 데이터 접근통제의 구현 Blocker**다.

---

# 5. 최종 Package Tree

```text
pdmg-service
└─ src/main/java
   └─ nhnis/mg/by/u
      │
      ├─ entry
      │  └─ handler
      │     └─ mgbyu1000Handler.java
      │
      ├─ application
      │  ├─ facade
      │  │  └─ mgbyu1000Facade.java
      │  │
      │  ├─ service
      │  │  └─ mgbyu1000Service.java
      │  │
      │  └─ rule
      │     └─ ProfileValidationRule.java
      │
      ├─ dto
      │  ├─ mgbyu1000S0DTOin.java
      │  ├─ mgbyu1000S0DTOout.java
      │  ├─ mgbyu1000C0DTOin.java
      │  ├─ mgbyu1000C0DTOout.java
      │  ├─ mgbyu1000U0DTOin.java
      │  └─ mgbyu1000U0DTOout.java
      │
      └─ persistence
         └─ dao
            └─ mgbyu1000DAO.java
```

Mapper:

```text
pdmg-service
└─ src/main/resources
   └─ mapper
      └─ rdw
         └─ mg
            └─ by
               └─ u
                  └─ mgbyu1000-ORA.xml
```

> 실제 pdmg-service Mapper resource root가
> `mapper/rdw/mg/...`인지 `rdw.mg...` 형태인지
> 구현 TASK에서 현재 실행 설정과 실제 `mgcoa9000-ORA.xml` 위치를 다시 확인한다.
> namespace 계약은 위치와 무관하게 고정이다.

---

# 6. 예상 파일 목록

## 반드시 생성

```text
mgbyu1000Handler.java
mgbyu1000Facade.java
mgbyu1000Service.java
ProfileValidationRule.java
mgbyu1000S0DTOin.java
mgbyu1000S0DTOout.java
mgbyu1000C0DTOin.java
mgbyu1000C0DTOout.java
mgbyu1000U0DTOin.java
mgbyu1000U0DTOout.java
mgbyu1000DAO.java
mgbyu1000-ORA.xml
```

## 테스트

```text
mgbyu1000HandlerTest.java
mgbyu1000FacadeTest.java
mgbyu1000ServiceTest.java
ProfileValidationRuleTest.java
mgbyu1000DAOIntegrationTest.java
```

## 조건부

```text
mgbyu1000Controller.java
```

TCF OFF 지원 요구가 확정될 때만 생성한다.

신규 Program의 기본 경로는 TCF ON Handler + Facade다.

---

# 7. mgbyu1000Handler 상세설계

## 7.1 Annotation / Interface

```java
@Component
@ConditionalOnProperty(
    name = "nhnis.fw.tcf.enabled",
    havingValue = "true"
)
public class mgbyu1000Handler implements TransactionHandler
```

정확한 import package는 실제 `mgcoa9000Handler`에서 복사한다.

## 7.2 Service ID 상수

```java
private static final String S0 = "mgbyu1000S0";
private static final String C0 = "mgbyu1000C0";
private static final String U0 = "mgbyu1000U0";
```

상수 이름은 UPPER_SNAKE_CASE 원칙을 따르되
짧은 거래 코드 상수는 기존 Handler 스타일을 우선한다.

## 7.3 Dependency

```text
mgbyu1000Facade only
```

## 7.4 serviceIds()

```java
@Override
public Collection<String> serviceIds() {
    return List.of(S0, C0, U0);
}
```

## 7.5 handle()

```java
@Override
public Object handle(
        Object dtoBody,
        TransactionContext context)
        throws Exception {

    return switch (context.getServiceId()) {
        case S0 -> facade.mgbyu1000S0(dtoBody);
        case C0 -> facade.mgbyu1000C0(dtoBody);
        case U0 -> facade.mgbyu1000U0(dtoBody);
        default -> /* 실제 PDMG ServiceHandlerNotFound 패턴 사용 */;
    };
}
```

### 구현 주의

`ServiceHandlerNotFound` 생성자/Factory는 실제 PDMG Handler 소스를 그대로 사용한다.
상세설계에서 임의 생성자 Signature를 만들지 않는다.

## 7.6 Handler 금지

```text
DTO Validation
ObjectMapper 변환
Profile Business Rule
DB 접근
userId 신뢰 판정
Transaction 선언
Exception swallow
```

---

# 8. mgbyu1000Facade 상세설계

## 8.1 Annotation

```java
@Service
public class mgbyu1000Facade
```

## 8.2 Dependency

```text
ObjectMapper
mgbyu1000Service
```

AS-IS Facade의 typed DTO 변환에 ObjectMapper를 사용한다.

## 8.3 S0

```java
@Transactional(
    transactionManager = "rdwTransactionManager",
    readOnly = true
)
public mgbyu1000S0DTOout mgbyu1000S0(Object dtoBody)
```

처리:

```text
Object dtoBody
  ↓
mgbyu1000S0DTOin 변환
  ↓
service.mgbyu1000S0(in)
  ↓
DTOout 반환
```

신규 UI는 `dto:{}`를 보내므로 정상 호출에서 dtoBody는 빈 Object다.

`dtoBody == null`의 정확한 변환/오류 정책은 Contract Test에서 고정한다.

## 8.4 C0

```java
@Transactional(
    transactionManager = "rdwTransactionManager",
    rollbackFor = Exception.class
)
public mgbyu1000C0DTOout mgbyu1000C0(Object dtoBody)
```

```text
Object
 → mgbyu1000C0DTOin
 → service.mgbyu1000C0(in)
```

## 8.5 U0

```java
@Transactional(
    transactionManager = "rdwTransactionManager",
    rollbackFor = Exception.class
)
public mgbyu1000U0DTOout mgbyu1000U0(Object dtoBody)
```

## 8.6 Facade 금지

```text
SQL
DAO 직접 호출
Profile 중복 판단
Profile Validation Rule 구현
HTTP 호출
예외 잡고 정상 DTO 반환
```

예외는 Rollback/Error Handler가 처리하도록 밖으로 전달한다.

---

# 9. 인증 사용자 Context 설계 위치

## 9.1 설계 요구

Service는 다음 값을 필요로 한다.

```text
authenticatedUserId
```

하지만 현재 공개 Service Signature는 PDMG 표준에 따라:

```java
mgbyu1000S0(mgbyu1000S0DTOin in)
mgbyu1000C0(mgbyu1000C0DTOin in)
mgbyu1000U0(mgbyu1000U0DTOin in)
```

으로 유지한다.

## 9.2 구현 금지안

### 금지 A

```java
String userId =
    ServiceContextHolder.getInstance()
        .getHeader()
        .getSys_comm()
        .getOptr_eno();
```

현재 `optr_eno`는 검증된 JWT subject와 자동 정합되지 않으므로
Profile 보안 Key로 사용 금지.

### 금지 B

```java
in.getUserId()
```

Client DTO에는 userId가 없다.

## 9.3 TASK 03에서 결정할 후보

```text
Option A
Verified Principal Provider
  → Service가 주입받아 requireUserId()

Option B
Facade에서 Trusted Principal을 얻고
명시적 내부 Command/Context로 Service에 전달

Option C
Framework가 Verified JWT subject를
ServiceContext.userContext에 설치하고
공통 AuthenticatedUserContext API 제공
```

본 Program은 Option C와 같이 PDMG 공통 Framework에서
신뢰 신원을 제공하는 방향이 장기적으로 가장 재사용성이 높지만,
이번 TASK에서 FRAMEWORK 변경을 임의 확정하지 않는다.

---

# 10. mgbyu1000Service 상세설계

## 10.1 Annotation / Dependency

```java
@Service
public class mgbyu1000Service
```

Dependency:

```text
mgbyu1000DAO
ProfileValidationRule
[Trusted User Context Provider — TASK03에서 확정]
```

Service에 기본 `@Transactional` 없음.

---

# 11. S0 Service Flow

```text
mgbyu1000S0DTOin
    ↓
authenticatedUserId 획득  ← TASK03 Blocker
    ↓
DAO Parameter Map 생성
    │
    └─ USER_ID / userId
    ↓
mgbyu1000DAO.mgbyu1000S0_S0(param)
    ↓
0 row ?
 ├─ YES → Empty vs BizException (TBD-PRF-003)
 └─ NO  → Map → mgbyu1000S0DTOout
    ↓
return
```

## S0 Method

```java
public mgbyu1000S0DTOout mgbyu1000S0(
        mgbyu1000S0DTOin in)
```

## Mapping

```text
DISPLAY_NAME           → displayName
BIRTH_DATE             → birthDate
GENDER_CD              → genderCode
TRAIN_EXP_CD           → trainingExperienceCode
ACTIVITY_LEVEL_CD      → activityLevelCode
```

`USER_ID`는 DTOout에 노출하지 않는다.

---

# 12. C0 Service Flow

```text
mgbyu1000C0DTOin
    ↓
ProfileValidationRule.validateCreate(in)
    ↓
authenticatedUserId 획득 ← TASK03
    ↓
Persistence Map 작성
    ├─ userId
    ├─ displayName
    ├─ birthDate
    ├─ genderCode
    ├─ trainingExperienceCode
    └─ activityLevelCode
    ↓
DAO.mgbyu1000C0_exists(param)
    ↓
exists ?
 ├─ YES → Duplicate BizException
 └─ NO
     ↓
 DAO.mgbyu1000C0_C0(param)
     ↓
affectedRows == 1 ?
 ├─ YES → processedCount=1
 └─ NO  → 처리건수 오류
```

## C0 Method

```java
public mgbyu1000C0DTOout mgbyu1000C0(
        mgbyu1000C0DTOin in)
```

## 동시성 주의

사전 `_exists`는 UX/업무 오류 변환용이다.

최종 중복 방지는:

```text
DB PRIMARY KEY / UNIQUE Constraint
```

가 담당한다.

따라서 INSERT Unique Constraint Exception도
동일 Duplicate Profile 업무오류로 변환하는 정책이 필요하다.

실제 Error Code는 TBD.

---

# 13. U0 Service Flow

```text
mgbyu1000U0DTOin
    ↓
ProfileValidationRule.validateUpdate(in)
    ↓
authenticatedUserId 획득 ← TASK03
    ↓
Persistence Map
    ↓
DAO.mgbyu1000U0_U0(param)
    ↓
affectedRows
 ├─ 1 → processedCount=1
 └─ 0 → Profile Not Found BizException
```

## U0 Method

```java
public mgbyu1000U0DTOout mgbyu1000U0(
        mgbyu1000U0DTOin in)
```

U0는 TASK 05 계약대로 Full Update다.

---

# 14. ProfileValidationRule 상세설계

## 14.1 Type

```java
@Component
public class ProfileValidationRule
```

일반 Rule은 PascalCase를 사용한다.

## 14.2 책임

```text
입력 정규화 정책
필수값
문자열 길이
날짜 형식
미래 생년월일 금지
코드값 유효성
```

DB 접근 금지.

Transaction 금지.

## 14.3 Public Method 후보

```java
void validateCreate(mgbyu1000C0DTOin in)
void validateUpdate(mgbyu1000U0DTOin in)
```

공통 private/helper:

```text
validateBirthDate
validateTrainingExperienceCode
validateActivityLevelCode
validateGenderCode
```

## 14.4 CodeSet

다음 CodeSet은 아직 확정하지 않는다.

```text
trainingExperienceCode
activityLevelCode
genderCode
```

따라서 실제 허용값을 Java 상수로 임의 작성하지 않는다.

---

# 15. mgbyu1000DAO 상세설계

## 15.1 Type

```java
@RDWMapper
public interface mgbyu1000DAO
```

FQCN:

```text
nhnis.mg.by.u.persistence.dao.mgbyu1000DAO
```

## 15.2 Method

PDMG AS-IS의 Map 기반 DAO 계약을 적용한다.

```java
Map<String, Object> mgbyu1000S0_S0(
    Map<String, Object> input
);

int mgbyu1000C0_exists(
    Map<String, Object> input
);

int mgbyu1000C0_C0(
    Map<String, Object> input
);

int mgbyu1000U0_U0(
    Map<String, Object> input
);
```

정확한 S0 Return Map Key는 Mapper alias와 함께 맞춘다.

## 15.3 DAO 책임

```text
Statement 호출
결과 반환
```

금지:

```text
중복 Profile이면 BizException
필수값 Validation
보안/권한 판정
DTO 조립
```

---

# 16. Mapper 상세설계

파일:

```text
mgbyu1000-ORA.xml
```

namespace:

```xml
<mapper namespace="nhnis.mg.by.u.persistence.dao.mgbyu1000DAO">
```

Statement:

```text
mgbyu1000S0_S0
mgbyu1000C0_exists
mgbyu1000C0_C0
mgbyu1000U0_U0
```

강제:

```text
DAO method
=
XML id
```

## Table

현재:

```text
BY_USER_PROFILE
```

은 `PROPOSED`.

DBMS/Schema/Table Prefix 승인 전 실행용 확정 SQL로 간주하지 않는다.

---

# 17. Mapper Result Alias

S0 SELECT는 Java mapping이 안정적으로 되도록
한 가지 Alias 방식을 일관되게 사용한다.

권장 신규 수기 Mapper:

```sql
SELECT
    DISPLAY_NAME       AS "displayName",
    BIRTH_DATE         AS "birthDate",
    GENDER_CD          AS "genderCode",
    TRAIN_EXP_CD       AS "trainingExperienceCode",
    ACTIVITY_LEVEL_CD  AS "activityLevelCode"
...
```

단 실제 RDW MyBatis 설정에서 quoted camelCase alias가
Map Key로 그대로 유지되는지는 DAO Integration Test로 검증한다.

기존 PDMG 대문자 Map Key 방식을 선택하면
Service Mapping도 그 Key를 정확히 사용한다.

둘을 섞지 않는다.

---

# 18. Exception / Error 처리 위치

## Handler

```text
미지원 Service ID
→ Handler/Framework Routing Error
```

## Facade

```text
DTO convert 실패
→ Framework/Global Error
```

Facade에서 예외를 먹지 않는다.

## Rule

```text
잘못된 Profile 입력
→ BizException
```

## Service

```text
Duplicate Profile
Profile Not Found
Unexpected affected rows
DB unique collision → Duplicate business mapping
```

## DAO / Mapper

```text
SQL/DB Exception
→ Spring/MyBatis Exception
→ 상위 Global Error
```

Profile 전용 Error Code:

```text
PROFILE_NOT_FOUND
PROFILE_ALREADY_EXISTS
PROFILE_INVALID_INPUT
```

의 **실제 코드 문자열은 TBD**다.

임의로 `BY0404` 같은 코드를 만들지 않는다.

---

# 19. Transaction 상세

## S0

```text
Facade @Transactional(readOnly=true)
   ↓
Service
   ↓
DAO SELECT
```

## C0

```text
Facade TX
  BEGIN/participate
     │
     ├─ exists SELECT
     ├─ INSERT
     └─ processedCount
  COMMIT
```

Duplicate/Exception:

```text
ROLLBACK
```

## U0

```text
Facade TX
  BEGIN/participate
     │
     └─ UPDATE
  COMMIT
```

0 row:

```text
BizException
→ ROLLBACK
```

TCF Timeout ON:

```text
Worker TransactionTemplate
   ↓
Facade REQUIRED 참여
```

이 구조에서 Facade Annotation이 항상 물리적 최초 BEGIN 지점은 아니다.

---

# 20. JavaDoc 계획

## Handler

Class JavaDoc:

```text
BY 사용자 Profile Program의 TCF ON Handler.
지원 Service ID: S0/C0/U0.
Service ID를 Facade Use Case로 라우팅한다.
```

## Facade

```text
Profile Use Case Transaction / DTO conversion 경계.
```

각 public method:

```text
Service ID
업무 의미
입력 Object가 dto body임
Transaction 성격
```

## Service

```text
Profile의 실제 업무 규칙과 영속성 호출 순서.
```

## Rule

```text
왜 이 검증이 필요한지
코드값/정책 출처
```

## DAO

```text
Mapper namespace와 Statement 계약
```

주석 금지:

```java
// DAO 호출
dao.xxx();
```

코드 자체를 다시 말하는 주석은 작성하지 않는다.

---

# 21. Logging 계획

로그의 Architecture Key:

```text
guid
serviceId
```

사용자 ID는 검증된 Principal 연결이 완료되기 전
`optr_eno/MDC userId`를 인증 신원이라고 표기하지 않는다.

DTO 전체 `toString()` 로그 금지:

```text
birthDate
displayName
genderCode
activityLevel
```

등 개인정보가 포함될 수 있다.

업무 로그는:

```text
serviceId
result
elapsed
error code
```

중심으로 남긴다.

---

# 22. Unit Test 상세설계

## 22.1 mgbyu1000HandlerTest

### 지원 거래 목록

```text
serviceIds == [S0,C0,U0]
```

### Routing

```text
S0 → facade.mgbyu1000S0
C0 → facade.mgbyu1000C0
U0 → facade.mgbyu1000U0
```

### Negative

```text
미지원 Service ID → ServiceHandlerNotFound 계열
```

Parameterized Test로 등록 ID와 switch 분기 일치 검증.

---

# 23. mgbyu1000FacadeTest

## S0

```text
Object dtoBody
→ mgbyu1000S0DTOin
→ service.mgbyu1000S0
```

## C0/U0

JSON Map의 camelCase field가 typed DTO로 정상 변환되는지 검증.

## Transaction Metadata

Reflection/Architecture Test 후보:

```text
S0 readOnly=true
C0/U0 rollbackFor Exception
transactionManager=rdwTransactionManager
```

실제 rollback은 Integration Test에서 검증.

---

# 24. ProfileValidationRuleTest

필수 Test:

```text
trainingExperienceCode null/blank
activityLevelCode null/blank
displayName max length
birthDate malformed
birthDate future
invalid gender code
invalid training code
invalid activity code
valid create
valid update
```

CodeSet 확정 전에는 invalid-code Test를 Pending으로 둔다.

---

# 25. mgbyu1000ServiceTest

Trusted User Context는 TASK 03 확정 후 Mock 가능한 구조여야 한다.

## S0

```text
trusted user = U1
DAO param USER_ID = U1
DAO result → DTOout
```

Client가 보낸 userId를 사용할 경로가 없어야 한다.

## C0

```text
Validation
→ trusted U1
→ exists=0
→ insert=1
→ processedCount=1
```

Duplicate:

```text
exists=1
→ BizException
→ insert never()
```

DB unique collision도 Duplicate business error 정책 Test 필요.

## U0

```text
update=1 → processedCount=1
update=0 → Not Found BizException
```

---

# 26. DAO Integration Test

Mapper binding:

```text
DAO FQCN
=
namespace
```

각 Method:

```text
mgbyu1000S0_S0
mgbyu1000C0_exists
mgbyu1000C0_C0
mgbyu1000U0_U0
```

검증:

```text
insert
→ exists
→ select
→ update
→ select changed value
```

DB/Table DDL이 승인된 환경에서 실행한다.

---

# 27. Architecture / Conformance Test

자동 검증 후보:

```text
Program ID = 9
Service ID = 11
Handler serviceIds unique
Program prefix/package match
Handler → only Facade
Facade → only Service(+ObjectMapper)
Service no @Transactional
DAO @RDWMapper
DAO FQCN = Mapper namespace
DAO Method = Mapper id
UI Default Transaction = mgbyu1000S0
```

---

# 28. Requirement Traceability

| Requirement | Backend Component |
|---|---|
| BY-USR-PRF-001 | S0 Handler/Facade/Service/DAO |
| BY-USR-PRF-002 | C0 Handler/Facade/Service/Rule/DAO |
| BY-USR-PRF-003 | U0 Handler/Facade/Service/Rule/DAO |
| BY-USR-PRF-004 | ProfileValidationRule + C0/U0 |
| BY-USR-PRF-005 | ProfileValidationRule + C0/U0 |
| BY-USR-PRF-006 | S0 Service output |
| BY-USR-PRF-007 | trusted authenticatedUserId Blocker |
| BY-USR-PRF-008 | Profile DTO 직접 AI 전달 금지 |
| BY-USR-PRF-009 | DTO full logging 금지 |
| BY-USR-PRF-010 | D0 없음 / retention TASK11 |

---

# 29. 결정사항

1. Handler는 `mgbyu1000Handler` 하나에서 S0/C0/U0를 처리한다.
2. Handler는 Facade만 호출한다.
3. Facade Method 이름은 Service ID 그대로 사용한다.
4. Facade가 ObjectMapper DTO 변환 책임을 가진다.
5. 조회 Facade는 `readOnly=true`.
6. 등록/수정 Facade는 `rollbackFor=Exception.class`.
7. Service에는 기본 `@Transactional`을 붙이지 않는다.
8. C0/U0 공통 입력검증은 `ProfileValidationRule`로 분리한다.
9. DAO는 `@RDWMapper` Interface다.
10. DAO는 실제 PDMG AS-IS에 맞춰 Map parameter/result 방식을 사용한다.
11. DAO Method와 Mapper Statement ID는 완전 일치한다.
12. `_exists` helper Statement를 중복검사에 사용한다.
13. Controller는 TCF OFF 지원 확정 전 생성하지 않는다.
14. Client DTO에는 userId가 없다.
15. Header `optr_eno`와 MDC userId를 인증된 Profile Key로 사용하지 않는다.

---

# 30. 구현 Blocker / TBD

## P0 — 반드시 TASK 03에서 해결

### `TBD-AUTH-001`

```text
JWT sub/ssoId
→ Trusted Principal
→ authenticatedUserId
→ Business Layer
```

실제 구현 방식.

### `TBD-AUTH-002`

Timeout Worker Thread에서 Trusted Principal이 안전하게 전달되는 방식.

### `TBD-AUTH-003`

Client Header `optr_eno`와 Verified JWT subject 불일치 정책:

```text
Reject
Audit
Ignore Header
```

중 결정.

## P1

- `TBD-PRF-003` S0 Profile 없음 응답
- Profile 전용 Error Code
- CodeSet 실제 값
- DTO `birthDate` Java 타입
- DBMS/Schema/Table Prefix
- Mapper 실제 Resource Root
- TCF OFF 필요 여부

---

# 31. 구현 시작 Gate

TASK 08 Backend Implement 전에 반드시:

```text
[ ] TASK 03 Architecture COMPLETE
[ ] authenticatedUserId trusted source 확정
[ ] Timeout worker 신원 Context 전달 확인
[ ] Profile DB Physical Naming 승인
[ ] Error Code Registry 결정
[ ] CodeSet 결정 또는 명시적 임시정책 승인
[ ] Mapper Resource Root 실제 소스 확인
```

P0 Auth Gate가 열리지 않으면
본인 Profile 보안 요구사항을 만족하는 Backend 구현으로 간주하지 않는다.

---

# 32. 다음 TASK 전달

현재 Runbook상:

```text
07_TASK_UI_DESIGN
```

이지만 실행 순서 GAP이 남아 있다.

실제 권장 순서:

```text
03_TASK_ARCHITECTURE
    ↓
04/05/06 충돌 여부 보정
    ↓
07_TASK_UI_DESIGN
```

UI 설계에 전달할 고정 계약:

```text
Program          mgbyu1000
Default Service  mgbyu1000S0

S0 Request dto   {}
S0 Response      5 Profile fields

C0/U0 Input
  displayName
  birthDate
  genderCode
  trainingExperienceCode
  activityLevelCode

C0/U0 Output
  processedCount

Client userId
  없음
```

---

# 33. TASK 06 완료 게이트

- [x] Package Tree
- [x] 파일 목록
- [x] Handler serviceIds
- [x] Handler routing
- [x] Facade method / TX
- [x] Service method flow
- [x] Rule 위치
- [x] DAO method / SQL ID
- [x] Mapper namespace
- [x] Exception 책임
- [x] JavaDoc 계획
- [x] Logging 계획
- [x] Unit/Integration Test 대상
- [x] 구현 Blocker 명시

**TASK 06 상세설계 상태: COMPLETE**

**Backend Implementation 상태: BLOCKED — TASK 03 Auth Architecture 미확정**
