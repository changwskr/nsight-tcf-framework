# 네이밍 아키텍처 구조

현재 PDMG 기준에서 **네이밍은 단순한 이름 규칙이 아니라 `ServiceId`를 중심으로 업무분류 → 패키지 → 클래스 → 메서드 → DTO → DAO → Mapper → SQL → 운영로그를 연결하는 아키텍처 체계**입니다.

현재 구현 네이밍의 정본은 `MG-NAMING_CONVENTION.md`이며, 실제 `pdmg-service` 구조를 기준으로 작성되어 있습니다.

가장 중요한 개념부터 보면 다음과 같습니다.

```text
                     PDMG NAMING ARCHITECTURE

                 업무 분류 체계
                      │
          MG → CO → A → 9000
                      │
                      ▼
               Program ID
                mgcoa9000
                      │
          ┌───────────┼───────────┐
          │           │           │
          ▼           ▼           ▼
       Handler      Facade      Service
   mgcoa9000Handler   ...         ...
          │
          ├──────── DAO
          │      mgcoa9000DAO
          │
          └──────── Mapper
                 mgcoa9000-ORA.xml

                      │
                      ▼
                 거래 구분 추가
                      │
                 S0 / C0 / U0 / D0
                      │
                      ▼
                  ServiceId
                mgcoa9000S0
                      │
          ┌───────────┼────────────────┐
          │           │                │
          ▼           ▼                ▼
         URL          DTO             Method
 /mgcoa9000S0   mgcoa9000S0DTOin   mgcoa9000S0()
                mgcoa9000S0DTOout
                      │
                      ▼
                    DAO
                      │
                      ▼
                 SQL ID
              mgcoa9000S0_S0
                      │
                      ▼
                   Mapper
                      │
                      ▼
                    SQL
```

즉 **이름 자체가 Architecture Traceability를 제공하는 구조**입니다.

---

## 1. 네이밍의 출발점은 업무 분류체계입니다

PDMG 네이밍은 먼저 다음 계층을 결정합니다.

```text
대그룹
  ↓
업무
  ↓
세부업무
  ↓
프로그램
  ↓
거래
```

예를 들어:

```text
MG
│
└─ CO
    │
    └─ A
        │
        └─ 9000
            │
            ├─ S0
            ├─ C0
            ├─ U0
            └─ D0
```

의미는:

| 구분     | 값     | 의미                  |
| -------- | ------ | --------------------- |
| 대그룹   | `MG`   | Market Group Platform |
| 업무     | `CO`   | 공통                  |
| 세부업무 | `A`    | 공통관리              |
| 프로그램 | `9000` | 프로그램 식별번호     |
| 거래     | `S0`   | 조회 0번              |

그리고 실제 네이밍에서는 대그룹·업무·세부업무 코드를 소문자로 사용합니다.

```text
MG + CO + A
 ↓    ↓    ↓
mg + co + a

       ↓

     mgcoa
```

현재 정본은 메시지 업무코드를 대그룹 `MG`와 충돌하지 않도록 **`MM`**으로 정의합니다. 예를 들어 메시지/SMS 영역은 `mgmma...`, 패키지는 `nhnis.mg.mm.a`가 됩니다.

---

# 2. ServiceId는 11자리입니다

현재 PDMG 표준 형식은 다음입니다.

```text
[대그룹2][업무2][세부1][식별4][구분1][순번1]

    mg      co     a    9000     S      0
```

즉:

```text
2 + 2 + 1 + 4 + 1 + 1
        =
       11자
```

결과:

```text
mgcoa9000S0
```

입니다.

각 영역을 다시 나누면:

```text
m g | c o | a | 9 0 0 0 | S | 0
────┬─────┬───┬─────────┬───┬──
 MG │ CO  │ A │ Program │조회│순번
```

---

# 3. Program ID와 ServiceId를 반드시 구분해야 합니다

이게 네이밍 아키텍처의 핵심입니다.

### Program ID

```text
mgcoa9000
```

9자리입니다.

하나의 **프로그램 단위**를 나타냅니다.

### ServiceId

```text
mgcoa9000S0
```

11자리입니다.

하나의 **실제 거래 단위**를 나타냅니다.

따라서:

```text
                mgcoa9000

               Program ID
                    │
      ┌─────────────┼─────────────┐
      ▼             ▼             ▼

mgcoa9000S0    mgcoa9000C0    mgcoa9000U0
   조회            등록            수정

      └─────────────┬─────────────┘
                    ▼

          하나의 Program 영역
```

입니다.

---

# 4. Program ID가 결정하는 이름

`mgcoa9000`이라는 Program ID가 정해지면 다음 클래스들이 거의 자동으로 결정됩니다.

```text
mgcoa9000
    │
    ├─ mgcoa9000Handler
    ├─ mgcoa9000Facade
    ├─ mgcoa9000Service
    ├─ mgcoa9000Controller
    ├─ mgcoa9000DAO
    └─ mgcoa9000-ORA.xml
```

즉 **클래스와 Mapper 파일은 Program 단위**입니다.

반대로 다음 구조는 사용하지 않습니다.

```text
mgcoa9000S0Handler     X
mgcoa9000C0Handler     X
mgcoa9000U0Handler     X
mgcoa9000D0Handler     X
```

CRUD별로 Handler를 만드는 것이 아니라:

```text
                  mgcoa9000Handler
                         │
          ┌──────────────┼──────────────┐
          ▼              ▼              ▼
      mgcoa9000S0    mgcoa9000C0    mgcoa9000U0
          │              │              │
          └──────────────┼──────────────┘
                         ▼
                 mgcoa9000Facade
```

처럼 **Program 하나를 클래스 하나로 묶습니다.**

---

# 5. ServiceId가 결정하는 이름

반대로 실제 거래 단위인:

```text
mgcoa9000S0
```

는 다음에 그대로 투영됩니다.

```text
ServiceId
mgcoa9000S0
      │
      ├─ URL
      │    /mgcoa9000S0
      │
      ├─ DTO
      │    mgcoa9000S0DTOin
      │    mgcoa9000S0DTOout
      │
      ├─ Facade Method
      │    mgcoa9000S0()
      │
      ├─ Service Method
      │    mgcoa9000S0()
      │
      └─ SQL ID Prefix
           mgcoa9000S0_...
```

즉:

```text
Program ID
→ 파일 / 클래스

ServiceId
→ 거래별 메서드 / DTO / SQL
```

라고 기억하면 가장 쉽습니다.

---

# 6. 패키지 네이밍

현재 PDMG 업무 패키지의 기본 공식은:

```text
nhnis.mg.[업무].[세부].[책임계층].[세부패키지]
```

입니다.

CO/A를 적용하면:

```text
nhnis.mg.co.a
```

가 업무 도메인 축이 됩니다.

전체 구조는:

```text
nhnis.mg.co.a
│
├─ entry
│   ├─ handler
│   └─ aspect
│
├─ application
│   ├─ controller
│   ├─ facade
│   └─ service
│
├─ dto
│
├─ persistence
│   └─ dao
│
├─ client
├─ config
└─ support
```

입니다.

예를 들어 `mgcoa9000`은:

```text
Handler
nhnis.mg.co.a.entry.handler.mgcoa9000Handler

Facade
nhnis.mg.co.a.application.facade.mgcoa9000Facade

Service
nhnis.mg.co.a.application.service.mgcoa9000Service

DTO
nhnis.mg.co.a.dto.mgcoa9000S0DTOin

DAO
nhnis.mg.co.a.persistence.dao.mgcoa9000DAO
```

가 됩니다.

---

# 7. Java Package와 Mapper 경로도 같은 업무 축을 사용합니다

이것도 PDMG 네이밍에서 상당히 좋은 구조입니다.

```text
ServiceId
mgcoa9000S0
     │
     ├─ mg
     ├─ co
     └─ a
          │
          ├──────────────┐
          ▼              ▼

Java Package          Mapper Resource
nhnis.mg.co.a         rdw.mg.co.a
```

따라서:

```text
Java
nhnis.mg.co.a.persistence.dao.mgcoa9000DAO

                    ↕ namespace

Mapper
rdw.mg.co.a/mgcoa9000-ORA.xml
```

로 추적됩니다.

---

# 8. Handler 네이밍

형식:

```text
mgcoa[식별4]Handler
```

예:

```text
mgcoa9000Handler
```

패키지:

```text
nhnis.mg.co.a.entry.handler
```

하나의 Handler가:

```text
mgcoa9000S0
mgcoa9000C0
mgcoa9000U0
mgcoa9000D0
```

를 모두 담당합니다.

```text
TransactionDispatcher
        │
        │ ServiceId
        ▼
mgcoa9000Handler
        │
   ┌────┼────┬────┐
   ▼    ▼    ▼    ▼
  S0   C0   U0   D0
   │    │    │    │
   └────┴────┴────┘
        │
        ▼
mgcoa9000Facade
```

Handler는 **ServiceId Routing만 담당**하고 DAO나 SQL을 직접 호출하지 않습니다.

---

# 9. Facade 네이밍

클래스:

```text
mgcoa9000Facade
```

패키지:

```text
nhnis.mg.co.a.application.facade
```

하지만 메서드는 **ServiceId 전체를 사용**합니다.

```java
mgcoa9000S0(...)
mgcoa9000C0(...)
mgcoa9000U0(...)
mgcoa9000D0(...)
```

따라서:

```text
Class
mgcoa9000Facade
       │
       ├─ mgcoa9000S0()
       ├─ mgcoa9000C0()
       ├─ mgcoa9000U0()
       └─ mgcoa9000D0()
```

입니다.

다음처럼 업무 의미 이름으로 임의 변경하지 않는 것이 현재 PDMG 정본입니다.

```text
selectParameterList()       X
createParameter()           X
updateParameter()           X

mgcoa9000S0()               O
mgcoa9000C0()               O
mgcoa9000U0()               O
```

이는 일반 Java의 예쁜 이름보다 **ServiceId 추적성을 우선한 프로젝트 규칙**입니다.

---

# 10. Service 네이밍

클래스:

```text
mgcoa9000Service
```

메서드:

```text
mgcoa9000S0()
mgcoa9000C0()
mgcoa9000U0()
mgcoa9000D0()
```

즉:

```text
Handler
mgcoa9000Handler
       ↓
Facade
mgcoa9000Facade.mgcoa9000S0()
       ↓
Service
mgcoa9000Service.mgcoa9000S0()
```

처럼 같은 ServiceId가 계속 이어집니다.

이것이 검색성과 장애추적을 높여 줍니다.

---

# 11. DTO 네이밍

DTO는 **Program ID가 아니라 ServiceId 전체**를 사용합니다.

조회 S0:

```text
mgcoa9000S0DTOin
mgcoa9000S0DTOout
```

등록 C0:

```text
mgcoa9000C0DTOin
mgcoa9000C0DTOout
```

목록 Sub DTO:

```text
mgcoa9000S0DTOSub0
```

따라서:

```text
                   mgcoa9000
                       │
           ┌───────────┼───────────┐
           ▼           ▼           ▼

     mgcoa9000S0  mgcoa9000C0  mgcoa9000U0
           │           │           │
           ▼           ▼           ▼
       S0DTOin      C0DTOin      U0DTOin
       S0DTOout     C0DTOout     U0DTOout
```

가 됩니다.

현재 규칙에서는 일반적인:

```text
TransactionRequestDTO
ParameterResponseDTO
ImageLogListDTO
```

처럼 ServiceId 추적이 끊기는 이름을 사용하지 않는 방향입니다.

---

# 12. DAO와 SQL ID

DAO는 Program 단위입니다.

```text
mgcoa9000DAO
```

하지만 DAO 메서드는 **SQL ID와 맞춥니다.**

예:

```text
ServiceId
mgcoa9000S0
      │
      ▼
SQL ID
mgcoa9000S0_S0
      │
      ▼
DAO Method
mgcoa9000S0_S0(...)
      │
      ▼
Mapper Statement
<select id="mgcoa9000S0_S0">
```

즉:

```text
DAO Method Name
        =
Mapper SQL ID
```

가 추적성 원칙입니다.

개념적으로:

```text
mgcoa9000S0
       │
       ├─ S0_S0   첫 조회
       ├─ S0_S1   두 번째 조회
       └─ S0_S2   세 번째 조회
```

처럼 확장할 수 있습니다.

---

# 13. Mapper 파일 네이밍

Program 단위:

```text
mgcoa9000-ORA.xml
```

리소스 위치:

```text
rdw.mg.co.a/mgcoa9000-ORA.xml
```

전체 연결은:

```text
mgcoa9000Service
       │
       ▼
mgcoa9000DAO
       │
       ▼
rdw.mg.co.a/
mgcoa9000-ORA.xml
       │
       ▼
mgcoa9000S0_S0
       │
       ▼
SELECT ...
```

입니다.

그리고 Mapper `namespace`는 DAO FQCN과 연결하는 것이 현재 규칙입니다.

---

# 14. 대소문자 규칙에는 PDMG 특성이 있습니다

일반 Java와 약간 다릅니다.

| 대상              | 규칙                 | 예                    |
| ----------------- | -------------------- | --------------------- |
| Package           | lowercase            | `nhnis.mg.co.a`       |
| PDMG 업무 Class   | 소문자 프로그램 접두 | `mgcoa9000Service`    |
| 일반 FW Class     | PascalCase           | `TcfFacade`           |
| Config            | PascalCase           | `RdwDataSourceConfig` |
| Aspect            | PascalCase           | `BizPrePostAspect`    |
| 일반 Method/Field | camelCase            | `pageNo`              |
| Constant          | UPPER_SNAKE          | `DEFAULT_PAGE_SIZE`   |
| DB Table/Column   | UPPER_SNAKE          | `TB_MG_TX_PARAM`      |
| Service Method    | ServiceId 그대로     | `mgcoa9000S0()`       |

따라서 현재 PDMG 기준에서는 다음처럼 자동으로 Java 관례에 맞춰 “수정”하면 안 됩니다.

```text
현재
mgcoa9000Handler

다음으로 변경
Mgcoa9000Handler       X
MgCoa9000Handler       X
```

업무 Program 파생 클래스는 현재 프로젝트 호환 규칙을 따릅니다. 반면 Framework/Config/Aspect 클래스는 일반 PascalCase를 사용합니다.

---

# 15. CRUD 구분자는 이렇게 사용합니다

| 코드 | 의미      | 예            |
| ---- | --------- | ------------- |
| `S`  | Select    | `mgcoa9000S0` |
| `C`  | Create    | `mgcoa9000C0` |
| `U`  | Update    | `mgcoa9000U0` |
| `D`  | Delete    | `mgcoa9000D0` |
| `A`  | 복합/혼합 | `mgcoa9000A0` |
| `R`  | Report    | `mgcoa9000R0` |

따라서 네이밍만 보고도:

```text
mgcoa9000S0
          ↑
          조회

mgcoa9000C0
          ↑
          등록

mgcoa9000U0
          ↑
          수정

mgcoa9000D0
          ↑
          삭제
```

를 알 수 있습니다.

---

# 16. 하나의 ServiceId를 따라가 보면

예를 들어:

```text
mgcoa9000S0
```

가 운영 로그에서 발견됐다고 하겠습니다.

개발자는 다음과 같이 바로 추적할 수 있습니다.

```text
운영 로그
mgcoa9000S0
       │
       ▼
ServiceId 분석

MG = Marketing Group Platform
CO = 공통
A  = 공통관리
9000 = 프로그램
S  = 조회
0  = 순번

       │
       ▼
Program ID
mgcoa9000
       │
       ├─ mgcoa9000Handler
       ├─ mgcoa9000Facade
       ├─ mgcoa9000Service
       ├─ mgcoa9000DAO
       └─ mgcoa9000-ORA.xml
       │
       ▼
거래 단위 검색
mgcoa9000S0
       │
       ├─ mgcoa9000S0DTOin
       ├─ mgcoa9000S0DTOout
       ├─ Facade.mgcoa9000S0()
       ├─ Service.mgcoa9000S0()
       └─ mgcoa9000S0_S0
       │
       ▼
Mapper
       │
       ▼
SQL
       │
       ▼
Table
```

**이 추적 구조가 바로 PDMG 네이밍 아키텍처의 목적입니다.**

---

# 17. 네이밍은 Dispatcher 아키텍처와도 연결됩니다

네이밍이 중요한 이유는 실제 Runtime에서도 ServiceId를 사용하기 때문입니다.

```text
표준전문
rms_svc_c = mgcoa9000S0
       │
       ▼
TCF
       │
       ▼
TransactionDispatcher
       │
       ▼
handlerMap
       │
       ▼
mgcoa9000Handler
       │
       ▼
mgcoa9000Facade.mgcoa9000S0()
       │
       ▼
mgcoa9000Service.mgcoa9000S0()
```

즉 네이밍 규칙은 단순 파일 이름 규칙이 아니라 **Runtime Routing 구조와 일치**합니다.

---

# 18. Timeout·거래통제·로그와도 ServiceId가 연결됩니다

ServiceId는 업무 소스만 찾는 키가 아닙니다.

```text
                       ServiceId
                     mgcoa9000S0
                           │
        ┌──────────────────┼───────────────────┐
        │                  │                   │
        ▼                  ▼                   ▼
     Dispatcher         Timeout            거래통제
        │                  │                   │
        ▼                  ▼                   ▼
     Handler           5초/10초             BLOCK?
        │
        ▼
     Facade
        │
        ▼
     Service
        │
        ├──────────────→ Transaction Log
        ├──────────────→ Error Log
        ├──────────────→ ImageLog
        └──────────────→ Runtime Trace
```

따라서 PDMG의 핵심 Architecture Key는 사실상:

> **`ServiceId`**

라고 봐도 됩니다.

---

# 19. 현재 AS-IS와 주의해야 할 GAP

현재 네이밍은 핵심 프로그램 축에서는 상당히 일관되지만 **모든 영역이 100% 동일 규칙은 아닙니다.**

특히 기존 점검 자료에는 DTO 필드·자동생성 DTO 메서드에 일부 예외가 있고, 오류 코드도 단일 형식으로 완전히 통일되어 있지 않다고 정리되어 있습니다.

따라서 구분하면:

```text
[CONFIRMED / 핵심 표준]

ServiceId 11자리
Program ID 9자리

ServiceId
→ Package 축
→ Handler
→ Facade
→ Service
→ DTO
→ DAO
→ Mapper
→ SQL

Program 단위 Class
거래 단위 Method/DTO/SQL


[AS-IS 예외]

자동 생성 DTO 일부 Naming
오류코드 형식 일부 혼재
Legacy 생성 코드의 Field Naming


[TO-BE]

Naming Rule 자동검증
ServiceId 중복검사
Package-ServiceId 일치검사
Class Naming 검사
DTO Naming 검사
DAO↔Mapper SQL ID 검사
```

---

# 20. 최종적으로 네이밍 아키텍처는 이렇게 기억하면 됩니다

```text
                    업무 분류
                       │
                 MG / CO / A
                       │
                       ▼
                   Program ID
                   mgcoa9000
                       │
          ┌────────────┼─────────────┐
          │            │             │
          ▼            ▼             ▼
       Handler       Facade       Service
          │
          ├────────── DAO
          └────────── Mapper File

                       │
                       ▼
                  거래 구분
                     S0
                       │
                       ▼
                   ServiceId
                 mgcoa9000S0
                       │
      ┌────────────────┼─────────────────┐
      │                │                 │
      ▼                ▼                 ▼
     DTO             Method            SQL ID
      │                │                 │
      └────────────────┼─────────────────┘
                       ▼
                    Runtime
                       │
          ┌────────────┼─────────────┐
          ▼            ▼             ▼
       Routing      Timeout       거래통제
          │
          ▼
       Logging
          │
          ▼
     Runtime Evidence
```

따라서 **PDMG 네이밍의 최종 목적은 “이름을 통일하는 것”보다 더 큽니다.**

> **ServiceId 하나만 알면 어느 업무·패키지·Handler·Facade·Service·DTO·DAO·Mapper·SQL·운영정책인지 추적할 수 있어야 한다.**

이것이 현재 PDMG의 **네이밍 아키텍처 구조**입니다.
