# TASK 08 — mgbyu1000 Backend Implementation Log

- Program: `mgbyu1000 Profile`
- TASK: `08_TASK_BACKEND_IMPLEMENT`
- 상태: `PATCH_READY`
- SSOT 적용: `NO`
- 전체 Gradle Compile: `NOT RUN`
- Integration/E2E: `NOT RUN`
- 기준일: 2026-09-01

---

# 1. 실행 결과

TASK 08의 요구에 따라 실제 PDMG 소스 패턴을 확인하고
`pdmg-service`에 적용 가능한 Backend Source Patch를 생성했다.

```text
DTO
→ DAO
→ Mapper XML
→ Rule
→ Service
→ Facade
→ Handler
```

Controller는 TCF OFF 요구가 확정되지 않아 생성하지 않았다.

---

# 2. 실제 PDMG 소스 확인으로 보정한 항목

## 2.1 Mapper 경로

초기 설계의 nested mapper 경로가 아니라 실제 Loader 계약에 맞게:

```text
src/main/resources/rdw.mg.by.u/mgbyu1000-ORA.xml
```

로 구현했다.

PDMG AS-IS Loader:

```text
classpath*:rdw.*/*.xml
```

## 2.2 Mapper Scan

AS-IS Mapper Scan은:

```text
nhnis.mg.co.a.persistence.dao
```

로 한정돼 있다.

BY DAO는 자동 등록되지 않으므로 Patch에:

```text
nhnis.mg.by.config.ByRdwMapperScanConfig
```

를 추가했다.

## 2.3 업무 선후처리 Aspect

현재 BizPrePostAspect Pointcut은:

```text
nhnis.mg.co.a.application.service..*
```

이다.

`nhnis.mg.by.u.application.service`는 자동 대상이 아니다.

공통 Aspect를 Program Patch에서 무단 수정하지 않고 `P1 GAP`으로 기록했다.

---

# 3. 생성 Backend 소스

```text
pdmg-service/src/main/java/nhnis/mg/by/config/RDWMapper.java
pdmg-service/src/main/java/nhnis/mg/by/config/ByRdwMapperScanConfig.java

pdmg-service/src/main/java/nhnis/mg/by/u/support/
  AuthenticatedUserProvider.java
  ServiceContextAuthenticatedUserProvider.java

pdmg-service/src/main/java/nhnis/mg/by/u/dto/
  mgbyu1000S0DTOin.java
  mgbyu1000S0DTOout.java
  mgbyu1000C0DTOin.java
  mgbyu1000C0DTOout.java
  mgbyu1000U0DTOin.java
  mgbyu1000U0DTOout.java

pdmg-service/src/main/java/nhnis/mg/by/u/persistence/dao/
  mgbyu1000DAO.java

pdmg-service/src/main/java/nhnis/mg/by/u/application/rule/
  ProfileValidationRule.java

pdmg-service/src/main/java/nhnis/mg/by/u/application/service/
  mgbyu1000Service.java

pdmg-service/src/main/java/nhnis/mg/by/u/application/facade/
  mgbyu1000Facade.java

pdmg-service/src/main/java/nhnis/mg/by/u/entry/handler/
  mgbyu1000Handler.java

pdmg-service/src/main/resources/rdw.mg.by.u/
  mgbyu1000-ORA.xml
```

---

# 4. 구현 계약

## Handler

```text
mgbyu1000Handler
serviceIds:
  mgbyu1000S0
  mgbyu1000C0
  mgbyu1000U0
```

Handler는 Facade만 호출한다.

## Facade

```text
S0
@Transactional(
  transactionManager="rdwTransactionManager",
  readOnly=true
)

C0/U0
@Transactional(
  transactionManager="rdwTransactionManager",
  rollbackFor=Exception.class
)
```

## Service

기본 `@Transactional` 없음.

## DAO

```text
DAO FQCN
nhnis.mg.by.u.persistence.dao.mgbyu1000DAO

=
Mapper namespace
```

Statement:

```text
mgbyu1000S0_S0
mgbyu1000C0_exists
mgbyu1000C0_C0
mgbyu1000U0_U0
```

---

# 5. 인증 구현

Client `userId`, Header `optr_eno`, MDC userId를
Profile DB Key로 사용하지 않는다.

Patch:

```text
AuthenticatedUserProvider
        ↓
ServiceContextAuthenticatedUserProvider
        ↓
1. userContext["authenticatedUserId"]
2. request attribute "ssoId"
3. local profile → LOCAL
4. otherwise FW0401
```

이는 TASK 03을 대체하는 최종 인증 Architecture가 아니라
현재 PDMG Filter/Context와 호환되는 Fail-Closed Bridge다.

---

# 6. TDD 증거

## RED

구현 전에 `tests/conformance_test.py`를 작성하고 실행했다.

결과:

```text
FAIL
MISSING:
  Handler
  Facade
  Service
  Rule
  DTO
  DAO
  Mapper
  UI
```

즉 Production 파일 부재 때문에 의도대로 실패했다.

## GREEN

구현 Patch 생성 후 같은 Test를 다시 실행했다.

결과:

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

---

# 7. 추가 검증

```text
Mapper XML parse             PASS
Java main source stub compile PASS
```

Stub Compile은 Java 21 문법과 사용한 외부 API 계약 모양을 검증한 것이며
실제 PDMG dependency classpath의 전체 Gradle Compile을 대신하지 않는다.

---

# 8. 테스트 소스

생성:

```text
ProfileValidationRuleTest.java
mgbyu1000ServiceTest.java
```

실제 저장소에 적용 후 JUnit/Mockito dependency와 함께 실행해야 한다.

---

# 9. 미결사항 / Blocker

## P0

1. TASK 03 Trusted Principal Architecture
2. pdmg-jwt 발급 ↔ pdmg-service 검증 계약
3. 운영 DBMS/Schema/Table/암호화 정책
4. 실제 PDMG 저장소 Gradle Compile
5. Spring Context / Mapper Binding
6. DB Integration

## P1

1. BY 업무 Error Code
2. CodeSet
3. BizPrePostAspect의 BY 업무축 적용

---

# 10. 완료 판정

```text
Source Patch 생성        PASS
PDMG 정적 계약검증       PASS
Java 문법검증            PASS
XML 검증                 PASS
SSOT 반영                 PENDING
Gradle Build              PENDING
DB Integration            PENDING
TCF E2E                   PENDING
```

**TASK 08 상태: PATCH_READY**

실제 저장소 적용과 통합검증 전에는 `COMPLETE`로 승격하지 않는다.
