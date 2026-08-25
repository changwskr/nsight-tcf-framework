# Big Picture 아키텍처 구조

지금까지 분석한 PDMG 구조를 하나로 합치면, **PDMG는 `ServiceId`를 중심으로 인증 → 표준전문 → 공통통제 → Timeout/Transaction → 업무 → DB → 오류/응답 → 운영추적을 하나의 온라인 거래 생명주기로 관리하는 구조**입니다. 실제 시스템 경계에서는 `pdmg-ui`, `pdmg-jwt`, `pdmg-service`가 HTTP 경계를 가지며, `pdmg-fw`는 별도 서버라기보다 `pdmg-service` 안에서 Filter·Interceptor·TCF·Timeout 등의 공통 실행 기반으로 동작합니다.

## 1. PDMG 전체 Big Picture

```text
┌──────────────────────────────────────────────────────────────────────────────┐
│                           사용자 / CHANNEL                                  │
│                                                                              │
│                    Browser / 전용브라우저 / API                              │
└───────────────────────────────┬──────────────────────────────────────────────┘
                                │
              ┌─────────────────┴──────────────────┐
              │                                    │
              ▼                                    ▼
┌──────────────────────────┐          ┌─────────────────────────────┐
│         pdmg-ui          │          │          pdmg-jwt           │
│                          │          │                             │
│ 화면 / 이벤트            │          │ Login / SSO                 │
│ ServiceId 결정           │          │ Access Token 발급           │
│ 표준전문 생성            │          │ Refresh Token               │
│                          │          │ Private Key 서명            │
└────────────┬─────────────┘          │ JWKS / Public Key 제공      │
             │                        └──────────────┬──────────────┘
             │ HTTP / JSON                          │ JWT
             │ Authorization: Bearer JWT            │
             │ hdr_nhnis + dto                      │
             ▼                                      │
┌───────────────────────────────────────────────────┼──────────────────────────┐
│                         WEB / INFRA               │                          │
│                                                   │                          │
│              GSLB → L4 → Apache → Tomcat          │                          │
│                            │                      │                          │
│                            ▼                      │                          │
│                      pdmg-service.war ◀───────────┘                          │
└────────────────────────────┬─────────────────────────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                       SYSTEM COMMON : pdmg-fw                                │
│                                                                              │
│  DefaultFilter                                                               │
│      │                                                                       │
│      ├─ Request Body Cache                                                   │
│      ├─ hdr_nhnis Parsing                                                   │
│      ├─ ServiceContext 생성                                                  │
│      └─ MDC / GUID 준비                                                      │
│      ▼                                                                       │
│  ServicePreventionInterceptor                                                │
│      │                                                                       │
│      ├─ JWT Validation                                                       │
│      ├─ GUID / User / IP / ServiceId 보강                                    │
│      ├─ 전문로그                                                             │
│      └─ ImageLog                                                             │
│      ▼                                                                       │
│  OnlineTransactionController                                                │
│      │                                                                       │
│      ├─ ServiceId 결정                                                       │
│      ├─ dto 분리                                                             │
│      └─ TcfFacade 호출                                                       │
└────────────────────────────┬─────────────────────────────────────────────────┘
                             │
                             ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                         TCF CONTROL : pdmg-fw                                │
│                                                                              │
│  TcfFacade                                                                   │
│      │                                                                       │
│      ├─ TransactionContext 생성                                              │
│      ├─ Active Transaction 등록                                              │
│      │                                                                       │
│      ├─ STF / 거래통제·실행정책  *                                           │
│      │                                                                       │
│      ▼                                                                       │
│  OnlineTimeoutExecutor                                                       │
│      │                                                                       │
│      ├─ Timeout OFF ────────→ Request Thread                                 │
│      │                                                                       │
│      └─ Timeout ON                                                           │
│             │                                                                │
│             ▼                                                                │
│        Worker Thread                                                         │
│             │                                                                │
│             ▼                                                                │
│       TransactionTemplate                                                    │
│             │                                                                │
│             ├─────────────── TX BEGIN                                        │
│             │                                                                │
│             ▼                                                                │
│       TransactionDispatcher                                                  │
│             │                                                                │
│             │ handlerMap[ServiceId]                                          │
│             ▼                                                                │
│       TransactionHandler                                                     │
└────────────────────────────┬─────────────────────────────────────────────────┘
                             │ Framework / Business 경계
                             ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                    BUSINESS APPLICATION : pdmg-service                       │
│                                                                              │
│  Handler                                                                     │
│     │                                                                        │
│     │ ServiceId → Use Case                                                   │
│     ▼                                                                        │
│  Facade                                                                      │
│     │                                                                        │
│     │ @Transactional(REQUIRED)                                               │
│     │ Timeout ON이면 외부 Worker TX 참여                                     │
│     ▼                                                                        │
│  BizPrePostAspect                                                            │
│     │                                                                        │
│     ▼                                                                        │
│  Service                                                                     │
│     │                                                                        │
│     ├──────────────┐──────────────────────┐                                  │
│     │              │                      │                                  │
│     ▼              ▼                      ▼                                  │
│ [Rule]*           DAO                  Integration                           │
│                    │                      │                                  │
│                    ▼                      ▼                                  │
│               Mapper XML              HTTP / EAI                            │
│                    │                                                         │
│                    ▼                                                         │
│                   SQL                                                        │
└────────────────────┼─────────────────────────────────────────────────────────┘
                     │
                     ▼
       ┌───────────────────────────┐
       │       DATA PLANE          │
       │                           │
       │ HikariCP                  │
       │   ↓                       │
       │ MyBatis / JDBC            │
       │   ↓                       │
       │ RDW / DB                  │
       └─────────────┬─────────────┘
                     │
          ┌──────────┴───────────┐
          │                      │
        정상                   Exception
          │                      │
          ▼                      ▼
       COMMIT                  ROLLBACK
          │                      │
          └──────────┬───────────┘
                     ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                         EXIT / ERROR / RESPONSE                              │
│                                                                              │
│ ETF *                                                                        │
│   ↓                                                                          │
│ GlobalExceptionHandler                                                       │
│   ↓                                                                          │
│ ResponseBodyAdvice / Resolver                                                │
│   ↓                                                                          │
│                                                                              │
│ 정상 : hdr_nhnis + dto                                                       │
│ 오류 : hdr_nhnis + result                                                    │
└────────────────────────────┬─────────────────────────────────────────────────┘
                             │
                             ▼
                         pdmg-ui
                             │
                             ▼
                           사용자


* 현재 자료 간 STF/ETF의 실제 최신 호출 연결상태에 Drift가 있으므로
  클래스 존재와 실제 Runtime 호출을 구분해서 확인해야 함.

* Rule은 목표 계층으로는 유효하지만 현재 pdmg-service AS-IS에서는
  독립 Rule 패키지가 일반화되어 있지 않고 Service 내부에 많은 Rule이 존재함.
```

현재 소스 분석상 업무 계층은 실질적으로 `Handler → Facade → BizPrePostAspect → Service → DAO → Mapper → DB`이고, 독립 `Rule` 계층은 일반화된 AS-IS라고 단정할 수 없습니다.

---

## 2. 이 구조의 중심에는 `ServiceId`가 있습니다

Big Picture에서 가장 중요한 것은 모듈 이름보다 **ServiceId가 전체 아키텍처를 관통한다는 것**입니다.

```text
                         ServiceId
                            │
          ┌─────────────────┼─────────────────┐
          │                 │                 │
          ▼                 ▼                 ▼
       Routing           Timeout          거래통제
          │                 │                 │
          ▼                 ▼                 ▼
       Handler          TX Policy          STF
          │
          ▼
       Facade
          │
          ▼
       Service
          │
          ▼
        DAO
          │
          ▼
       Mapper
          │
          ▼
         SQL
          │
          ▼
        Table

          │
          ├─────────────→ Test
          ├─────────────→ Transaction Log
          ├─────────────→ Error Log
          ├─────────────→ Runtime Evidence
          └─────────────→ 운영 / 통제
```

즉 `mgcoa5530S0` 같은 ServiceId 하나를 알면 **화면 → Dispatcher → Handler → Facade → Service → DAO → SQL → Table**까지 추적할 수 있어야 합니다. 이 때문에 PDMG 분석에서는 ServiceId를 온라인 거래의 Architecture Key로 보는 것이 적절합니다.

---

# 3. 요청 전문과 Context는 이렇게 흐릅니다

PDMG 요청은 개념적으로 두 영역입니다.

```text
HTTP Request
│
├─ hdr_nhnis
│    │
│    ├─ GUID
│    ├─ ServiceId
│    ├─ User
│    ├─ Branch
│    ├─ Client IP
│    ├─ Screen
│    └─ System
│
└─ dto
     │
     └─ 실제 업무 입력
```

이를 내부에서는 다음처럼 분리합니다.

```text
                 Standard Request
                       │
               ┌───────┴───────┐
               │               │
               ▼               ▼
          hdr_nhnis            dto
               │               │
               ▼               ▼
        ServiceContext     Business DTO
               │               │
               ▼               ▼
     Framework 공통처리       Handler
                               ↓
                            Facade
                               ↓
                            Service
```

그리고 TCF 안에 들어가면:

```text
ServiceContext
    │
    │ HTTP Request 전체 문맥
    │
    └──────────────┐
                   ▼
          TransactionContext
                   │
                   ├─ ServiceId
                   ├─ 시작시간
                   └─ 실행문맥
```

으로 한 단계 더 좁혀집니다.

중요한 것은:

```text
ServiceContext       ≠ DB Transaction
TransactionContext   ≠ Spring Transaction
```

이라는 것입니다.

---

# 4. Big Picture에서 가장 중요한 Thread + Transaction 구조

**TCF ON + Timeout ON**을 이해할 때는 그림을 두 Thread로 나눠 봐야 합니다.

```text
Tomcat Request Thread
────────────────────────────────────────────────────

Filter
  ↓
Interceptor
  ↓
Controller
  ↓
TcfFacade
  ↓
TimeoutExecutor
  │
  │ Worker submit
  │
  │ Future.get(timeout)
  │
  ├──────────────────────────────┐
  │                              │
  │                              ▼


Worker Thread
────────────────────────────────────────────────────

Context 설치/전파
  ↓
TransactionTemplate
  ↓
══════════════════════════════
       PHYSICAL TX BEGIN
══════════════════════════════
  ↓
Dispatcher
  ↓
Handler
  ↓
Facade
@Transactional(REQUIRED)
  ↓
Service
  ↓
DAO
  ↓
Mapper
  ↓
DB

     ┌──────────────────┐
     │                  │
   성공               Exception
     │                  │
     ▼                  ▼
  COMMIT             ROLLBACK
     │                  │
     └─────────┬────────┘
               ▼
           Worker 종료


Tomcat Request Thread
────────────────────────────────────────────────────

결과 수신
  ↓
ETF / Response 처리
  ↓
Response
```

Timeout ON에서는 `TransactionTemplate`이 Worker Thread에서 최외곽 Physical Transaction을 만들고, 동일 TransactionManager의 `@Transactional(REQUIRED)` Facade는 그 기존 Transaction에 참여하는 구조로 분석되어 있습니다.

따라서:

```text
Request Thread ≠ DB Transaction Thread
```

라는 점이 중요합니다.

---

# 5. JWT는 Transaction 바깥에서 먼저 끝내야 합니다

```text
                     인증

pdmg-ui
   │
   │ ID/PW 또는 SSO
   ▼
pdmg-jwt
   │
   ├─ 인증
   ├─ Private Key
   └─ JWT Sign
   │
   ▼
Access Token
   │
   ▼
pdmg-ui
   │
   │ Authorization: Bearer
   ▼
pdmg-service
   │
   ▼
pdmg-fw
Interceptor / JWT Validator
   │
   ├─ Signature
   ├─ exp
   ├─ issuer/audience 등
   │
   ├─ 실패 ──────────→ 401
   │
   └─ 성공
        │
        ▼
       TCF
        │
        ▼
  업무 Transaction
```

즉 원칙적으로:

```text
인증 검증
   ↓
공통 거래통제
   ↓
업무 Transaction
```

이어야 합니다. **인증 실패 요청 때문에 DB 업무 Transaction을 만들 필요가 없습니다.** JWT 발급과 검증 책임도 `pdmg-jwt`와 `pdmg-fw`로 나누어져 있습니다.

---

# 6. 공통 처리도 3단계로 나누어 봐야 합니다

```text
① SYSTEM COMMON
────────────────────────────────
Filter
Interceptor

JWT
Header
GUID
ServiceContext
MDC
전문로그
ImageLog

        ↓

② TRANSACTION COMMON
────────────────────────────────
TCF
STF
Timeout
Transaction
Dispatcher
ETF

거래실행 통제

        ↓

③ BUSINESS COMMON
────────────────────────────────
BizPrePostAspect
Service

업무 선후처리
업무 로그
업무 공통규칙
```

그래서 모두 “선처리”라는 이름으로 합치면 안 됩니다.

```text
Filter / Interceptor
= 시스템 관점

STF / ETF
= 거래 관점

BizPrePostAspect
= 업무 관점
```

입니다.

---

# 7. 운영·통제는 옆에서 전체 거래를 바라봅니다

Big Picture는 단순히 위에서 아래로 내려가는 것만으로 끝나지 않습니다.

```text
                         CONTROL PLANE

             ┌────────────────────────────────┐
             │       운영자 / 운영화면        │
             └───────────────┬────────────────┘
                             │
                 ┌───────────┴──────────┐
                 ▼                      ▼
             거래통제              Runtime 진단
                 │                      │
                 ▼                      ▼
        ServiceId 정책           Active Transaction
        User / Branch            Thread
        IP / Channel             Hikari
        Business                 JVM / GC
                 │              Timeout / Error
                 │                      ▲
                 ▼                      │
────────────────────────────────────────────────────────
                         DATA PLANE

사용자 → TCF → Dispatcher → Handler → Service → DB
                 │
                 ├─ ServiceId
                 ├─ GUID / TraceId
                 ├─ User
                 ├─ Timeout
                 ├─ TX State
                 ├─ SQL
                 ├─ Error
                 └─ Elapsed Time
```

현재 PDMG에서 운영·통제 기능은 독립 `pdmg-om` Java 모듈 하나에 완전히 모여 있다고 확인되지는 않았고, `pdmg-fw`의 Runtime/거래통제 기능과 `pdmg-service`의 운영 ServiceId 등에 분산된 AS-IS로 보는 것이 안전합니다.

즉 논리적 목표는:

```text
Control Plane
       │
       ├─ 정책
       ▼
Runtime/Data Plane
       │
       ├─ Evidence
       ▼
Control Plane
```

의 폐쇄 루프입니다.

---

# 8. 모듈 관점으로 다시 보면

```text
                         PDMG

          ┌─────────────────────────┐
          │        pdmg-ui          │
          │ Channel / UI / Request  │
          └────────────┬────────────┘
                       │ HTTP
                       ▼
┌─────────────────────────────────────────────────────┐
│                   pdmg-service                      │
│                                                     │
│ ┌─────────────────────────────────────────────────┐ │
│ │                   pdmg-fw                       │ │
│ │                                                 │ │
│ │ Filter / Interceptor / Context / JWT Validation│ │
│ │ TCF / STF / ETF / Timeout / Transaction        │ │
│ │ Dispatcher / Exception / Runtime               │ │
│ └───────────────────────┬─────────────────────────┘ │
│                         │                           │
│                         ▼                           │
│ Handler → Facade → Service → DAO / Client          │
│                          │                          │
└──────────────────────────┼──────────────────────────┘
                           │
                    MyBatis / JDBC
                           │
                           ▼
                         RDW/DB

          ┌─────────────────────────┐
          │        pdmg-jwt         │
          │ Token Issue / Refresh   │
          │ Private Key / JWKS      │
          └─────────────────────────┘


          [운영 기능]
          Runtime / Tx Control / Audit
                현재 여러 영역에 분산
```

특히 `pdmg-fw`는 `pdmg-service`가 HTTP로 다시 호출하는 별도의 원격 Framework 서버가 아니라 **애플리케이션 안에 함께 들어가 실행되는 Framework 모듈**이라는 점이 중요합니다.

---

# 9. AS-IS와 목표 구조를 구분해야 하는 부분

| 항목                                        | 현재 판단                                                       |
| ------------------------------------------- | --------------------------------------------------------------- |
| `pdmg-ui`                                   | **AS-IS 확인**                                                  |
| `pdmg-jwt`                                  | **AS-IS 확인**                                                  |
| `pdmg-fw`                                   | **AS-IS 확인**                                                  |
| `pdmg-service`                              | **AS-IS 확인**                                                  |
| 독립 `pdmg-om` Java 구현                    | **현재 자료에서 미확인**                                        |
| `Handler → Facade → Service → DAO → Mapper` | **AS-IS 확인**                                                  |
| 독립 `Rule` 계층                            | **TO-BE 성격**                                                  |
| ServiceId Dispatcher                        | **AS-IS 핵심 구조**                                             |
| Timeout Worker + TransactionTemplate        | **Timeout ON 기준 핵심 구조**                                   |
| STF/ETF                                     | **설계 기준에는 존재, 최신 실제 호출 연결은 Drift 재확인 필요** |

특히 현재 자료에는 STF/ETF가 호출되는 것으로 기술한 자료와 실제 호출선에서 연결 여부를 다시 확인해야 한다고 기록한 자료가 함께 존재합니다. 따라서 Big Picture에서는 아키텍처 위치를 표시하되 **최신 Runtime AS-IS라고 무조건 확정하지 않는 것**이 맞습니다.

---

# 10. Big Picture를 7개 층으로 외우면 됩니다

```text
01 CHANNEL
────────────────────────
pdmg-ui
화면 / 이벤트
표준전문

       ↓

02 SECURITY
────────────────────────
pdmg-jwt
JWT 발급
JWT 검증

       ↓

03 WEB ENTRY
────────────────────────
Filter
Interceptor
Controller
ServiceContext

       ↓

04 TCF CONTROL
────────────────────────
TransactionContext
STF
Timeout
Transaction
Dispatcher

       ↓

05 BUSINESS
────────────────────────
Handler
Facade
Service
Rule*

       ↓

06 DATA / INTEGRATION
────────────────────────
DAO
Mapper
SQL
DB
External

       ↓

07 EXIT / OPERATION
────────────────────────
ETF
Exception
표준응답
Transaction Log
ImageLog
Runtime Evidence
운영통제
```

이 7개 층이 현재 PDMG를 이해하기 위한 가장 좋은 전체 지도입니다. 기존 전체 온라인 거래 분석도 같은 7개 관점으로 구조를 정리하고 있습니다.

## 최종 한 장

PDMG의 핵심을 정말 하나의 선으로만 줄이면 다음입니다.

```text
사용자
 ↓
pdmg-ui
 ↓
JWT
 ↓
표준전문
 ↓
Filter / Interceptor
 ↓
ServiceContext
 ↓
Controller
 ↓
TCF
 ↓
STF
 ↓
Timeout Executor
 ↓
Worker Thread
 ↓
TransactionTemplate
 ↓
TX BEGIN
 ↓
ServiceId Dispatcher
 ↓
Handler
 ↓
Facade
 ↓
Service
 ↓
DAO
 ↓
Mapper
 ↓
DB
 ↓
COMMIT / ROLLBACK
 ↓
ETF / Exception
 ↓
표준응답
 ↓
사용자

        ↕
 ServiceId + GUID
        ↕
거래통제 / Timeout
Transaction Log
Runtime Evidence
운영관리
```

**따라서 PDMG Big Picture에서 가장 먼저 기억할 세 가지는 `ServiceId`, `TCF`, `Transaction Boundary`입니다.** 이 세 축을 이해하면 JWT, Context, Dispatcher, Handler, Timeout, 에러처리, 운영통제가 각각 어디에 붙는지 자연스럽게 연결됩니다.
