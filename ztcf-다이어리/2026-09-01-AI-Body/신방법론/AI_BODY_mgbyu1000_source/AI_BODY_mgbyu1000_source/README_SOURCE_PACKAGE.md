# AI Body - mgbyu1000 Profile Source Package

## Program

- Program ID: `mgbyu1000`
- 기능: Member Profile
- Service:
  - `mgbyu1000S0` Profile 조회
  - `mgbyu1000C0` Profile 등록
  - `mgbyu1000U0` Profile 수정

## Backend

```text
pdmg-service/src/main/java/nhnis/mg/by/
├─ config/
│  ├─ RDWMapper.java
│  └─ ByRdwMapperScanConfig.java
└─ u/
   ├─ entry/handler/mgbyu1000Handler.java
   ├─ application/facade/mgbyu1000Facade.java
   ├─ application/service/mgbyu1000Service.java
   ├─ application/rule/ProfileValidationRule.java
   ├─ dto/
   ├─ persistence/dao/mgbyu1000DAO.java
   └─ support/
      ├─ AuthenticatedUserProvider.java
      └─ ServiceContextAuthenticatedUserProvider.java
```

Mapper:

```text
pdmg-service/src/main/resources/rdw.mg.by.u/mgbyu1000-ORA.xml
```

## UI

```text
pdmg-ui/src/main/resources/static/mgbyu1000/index.html
```

## Tests

```text
pdmg-service/src/test/java/nhnis/mg/by/u/
├─ application/rule/ProfileValidationRuleTest.java
└─ application/service/mgbyu1000ServiceTest.java
```

## Current Status

```text
Source Status        : PATCH_READY
Static Conformance   : PASS
Mapper XML Parse     : PASS
JavaScript Syntax    : PASS
Runtime Conformance  : NOT PROVEN
Release              : BLOCKED
```

## Important

이 ZIP은 지금까지 정리한 내용을 기준으로 만든 개발 소스 패키지다.

아직 다음 항목은 최종 확정 전이다.

- TASK 03 Architecture
- JWT Subject → authenticatedUserId
- 실제 SSOT 적용
- 운영 DB/Schema/Table/PK/Unique
- Gradle Unit/Integration Test
- Spring/Mapper Binding
- DB Integration
- Browser/TCF/JWT E2E
- p95 3초
- 삭제/보존 정책

따라서 이 패키지는 **개발 기준 소스(PATCH_READY)**이며,
운영 Release 완료본은 아니다.
