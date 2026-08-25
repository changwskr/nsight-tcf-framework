# `pdmg-fw`의 TCF 아키텍처 구조

> **현행화 (2026-08-25):** Timeout 경로에 Registry/`Statement.cancel` · Worker 외곽 TX Policy ·  
> `OnlineTimeoutException` 시 etf skip 반영.  
> 상세: [01.timeout.md](./01.timeout.md) · [PDMG Transaction + Timeout 아키텍처 구조.md](./PDMG%20Transaction%20+%20Timeout%20아키텍처%20구조.md)  
> 원본: [`pdmg-fw TCF 아키텍처 - 원본.md`](./pdmg-fw%20TCF%20아키텍처%20-%20원본.md)

현재 PDMG 기준에서 `pdmg-fw`의 TCF는 단순 공통 라이브러리가 아니라 **온라인 거래 한 건의 실행 생명주기를 통제하는 Framework Runtime**입니다. 핵심 실행축은 `Controller → TCF → STF → Timeout/Transaction → Dispatcher → Handler → ETF`이며, 실제 업무 계층은 그 뒤의 `Facade → Service → DAO → Mapper`로 연결됩니다.

## 1. 전체 Big Picture

```text
                         PDMG ONLINE TRANSACTION

┌─────────────────────────────────────────────────────────────┐
│ CLIENT                                                      │
│ pdmg-ui / API Client                                        │
│                                                             │
│ Authorization: Bearer JWT                                   │
│ hdr_nhnis + dto                                             │
└───────────────────────────┬─────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                pdmg-fw : SYSTEM COMMON                      │
│                                                             │
│  DefaultFilter                                              │
│      │                                                      │
│      ├─ Request Body Cache                                  │
│      ├─ hdr_nhnis Parsing                                   │
│      ├─ ServiceContext 생성                                 │
│      └─ GUID / MDC 구성                                     │
│      │                                                      │
│      ▼                                                      │
│  ServicePreventionInterceptor                               │
│      │                                                      │
│      ├─ JWT 검증                                            │
│      ├─ User / IP / ServiceId 보정                          │
│      ├─ 요청 전문 로그                                     │
│      └─ PRE ImageLog                                        │
└───────────────────────────┬─────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                     pdmg-fw : TCF                           │
│                                                             │
│  OnlineTransactionController                                │
│      │                                                      │
│      ▼                                                      │
│  TcfFacade.process()                                        │
│      │                                                      │
│      ├─ TransactionContext 생성                             │
│      ├─ ActiveTransaction 등록                              │
│      │                                                      │
│      ▼                                                      │
│  STF.preProcess()                                           │
│      │                                                      │
│      ├─ 거래통제                                            │
│      └─ 실행 전 정책 적용                                   │
│      │                                                      │
│      ▼                                                      │
│  OnlineTimeoutExecutor                                      │
│      │                                                      │
│      ├─ Timeout OFF → 현재 Request Thread                   │
│      │                                                      │
│      └─ Timeout ON                                          │
│             │                                               │
│             ▼                                               │
│        Worker Thread (pdmg-online-*)                        │
│             │ Registry.bind / TrackingStatement             │
│             ▼                                               │
│       TransactionTemplate (+ ServiceId Policy)              │
│             │                                               │
│             └────────────── TX BEGIN                         │
│             │                                               │
│             ▼                                               │
│       TransactionDispatcher                                 │
│             │ ServiceId                                     │
│             ▼                                               │
│       TransactionHandler                                    │
│                                                             │
│  Timeout 시(요청 Thread):                                   │
│    cancelAll → Statement.cancel()                           │
│    future.cancel(true) → interrupt                          │
│    OnlineTimeoutException → 504 (etf skip)                  │
└───────────────────────────┬─────────────────────────────────┘
                            │ Framework / Business 경계
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                    pdmg-service                             │
│                                                             │
│ Handler                                                     │
│    ↓                                                        │
│ Facade                                                      │
│    ↓                                                        │
│ Service                                                     │
│    ↓                                                        │
│ DAO                                                         │
│    ↓                                                        │
│ Mapper XML                                                  │
│    ↓                                                        │
│ DB                                                          │
└───────────────────────────┬─────────────────────────────────┘
                            │
                   ┌────────┴────────┐
                   │                 │
                 정상              예외
                   │                 │
                COMMIT            ROLLBACK
                   │                 │
                   └────────┬────────┘
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                  pdmg-fw : EXIT                            │
│                                                             │
│ ETF.postProcess()                                           │
│      ↓                                                      │
│ GlobalExceptionHandler                                      │
│      ↓                                                      │
│ Response Resolver                                           │
│                                                             │
│ 정상 : hdr_nhnis + dto                                     │
│ 오류 : hdr_nhnis + result                                  │
└─────────────────────────────────────────────────────────────┘
```

PDMG 전체 구조에서도 `pdmg-fw`는 별도의 업무 서버라기보다는 `pdmg-service` 안에서 Filter·Interceptor·TCF·Timeout·Dispatcher를 제공하는 **공통 실행 Framework**로 보는 것이 정확합니다.

---

## 2. `pdmg-fw`는 크게 `commons`와 `tcf` 두 축이다

현재 구조를 가장 쉽게 나누면 다음과 같습니다.

```text
pdmg-fw
│
├─ nhnis.fw.commons
│    │
│    ├─ configuration
│    ├─ filter
│    ├─ interceptor
│    ├─ resolver
│    ├─ context
│    ├─ dto / dto.header
│    ├─ jwt
│    ├─ exception
│    ├─ imagelog
│    ├─ log
│    ├─ runtime
│    ├─ txcontrol
│    └─ util
│
└─ nhnis.fw.tcf
     │
     ├─ Controller
     ├─ TcfFacade
     ├─ STF
     ├─ ETF
     ├─ Timeout
     ├─ Transaction
     ├─ Dispatcher
     └─ TransactionHandler 계약
```

두 영역의 책임은 명확히 다릅니다.

| 영역           | 질문                                        | 책임                            |
| -------------- | ------------------------------------------- | ------------------------------- |
| `commons`      | “HTTP 요청을 Framework가 처리할 수 있는가?” | Header, Context, JWT, Logging   |
| `TCF`          | “이 거래를 어떤 규칙으로 실행할 것인가?”    | STF, Timeout, TX, Dispatch, ETF |
| `pdmg-service` | “실제 업무를 어떻게 처리할 것인가?”         | Handler, Facade, Service, DAO   |

`commons`는 단순 Utility 모음이 아니라 **System Common Runtime Layer**입니다.

---

# 3. TCF의 중심 클래스는 `TcfFacade`

TCF를 하나의 실행 엔진으로 보면 중심은 다음 구조입니다.

```text
OnlineTransactionController
          │
          ▼
      TcfFacade
          │
          ├─ TransactionContext
          │
          ├─ ActiveTransactionRegistry.begin()
          │
          ▼
         STF
          │
          ▼
 OnlineTimeoutExecutor
          │
          ▼
 TransactionDispatcher
          │
          ▼
        Handler
          │
          ▼
        업무처리
          │
          ▼
         ETF
          │
          ▼
 ActiveTransactionRegistry.end()
```

즉 `TcfFacade`의 역할은 직접 업무를 처리하는 것이 아니라:

> **거래 시작 → 선처리 → 실행 → 후처리 → 종료를 하나의 표준 실행 Template으로 묶는 것**

입니다.

---

# 4. STF — 거래 실행 전 통제

`STF`는 **Standard Transaction Front**, 즉 거래 선처리 영역으로 이해하면 됩니다.

현재 PDMG에서 중요한 실행 위치는:

```text
Controller
    ↓
TcfFacade
    ↓
★ STF
    ↓
Timeout Executor
    ↓
Dispatcher
```

입니다.

따라서 STF 질문은 이것입니다.

```text
"이 ServiceId 거래를
 지금 실행해도 되는가?"
```

대표 책임:

```text
STF
 │
 ├─ ServiceId 관련 정책
 ├─ 거래통제
 ├─ 시스템 전체 통제
 ├─ 업무별 통제
 ├─ 사용자 통제
 ├─ 지점 통제
 ├─ IP 통제
 └─ 향후 Timeout / 중복거래 정책
```

현재 실제 거래통제는 `Interceptor`가 아니라 STF 안에서 `MgTxControlService.check()`를 통해 집행되는 구조입니다.

```text
Request
   ↓
STF
   ↓
거래통제
   │
   ├─ ALLOW
   │     ↓
   │  Timeout / Dispatcher
   │
   └─ BLOCK
         ↓
      BizException
         ↓
      업무 Handler 진입 X
```

---

# 5. Timeout Executor — PDMG TCF의 중요한 특징

현재 PDMG에서 `timeout.enabled=true`인 경우 TCF는 업무 실행을 별도 Worker Thread로 넘깁니다.

```text
Tomcat Request Thread
        │
        ▼
      STF
        │
        ▼
OnlineTimeoutExecutor
        │
        │ submit() + Future.get(remaining)
        │ Timeout 시: cancelAll → Statement.cancel()
        │             + future.cancel(true) → 504 (etf skip)
        ▼
================================================
        Worker Thread
        pdmg-online-*
        Registry.bind / TrackingStatement
================================================
        │
        ▼
TransactionTemplate (+ ServiceId Policy)
        │
        ▼
TX BEGIN (min-start-budget 미달 시 TX 미개시)
        │
        ▼
Dispatcher
        ↓
Handler
        ↓
Facade
        ↓
Service
        ↓
DAO / Mapper (queryTimeout + Statement 등록)
```

여기가 PDMG TCF에서 특히 중요합니다.

**Timeout ON일 때 DB Transaction의 최외곽 경계가 Worker Thread 안에 위치합니다.** `TransactionTemplate`은 `PROPAGATION_REQUIRED`로 동작하며 Dispatcher보다 먼저 Transaction을 시작합니다. Timeout 시에는 등록된 JDBC에 `Statement.cancel()`을 시도한 뒤 interrupt하며, 늦은 커밋은 Deadline 재검사로 막습니다.

---

# 6. Transaction 구조

구조를 확대하면:

```text
Worker Thread
     │
     ▼
TransactionTemplate
PROPAGATION_REQUIRED
     │
     ├───────────── TX BEGIN
     │
     ▼
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
     │
 ┌───┴──────────┐
 │              │
정상            예외
 │              │
COMMIT        ROLLBACK
```

중요한 점은 Facade에 `@Transactional(REQUIRED)`가 있더라도 같은 Transaction Manager를 사용한다면:

```text
바깥 TransactionTemplate TX
              │
              ▼
Facade @Transactional(REQUIRED)
              │
              └─ 기존 TX 참여
```

가 된다는 점입니다.

따라서 일반적인 경우 Physical DB Transaction은 **하나**입니다.

---

# 7. Dispatcher — ServiceId 기반 업무 Routing

TCF의 또 하나의 중심이 `TransactionDispatcher`입니다.

핵심은 매우 단순합니다.

```text
ServiceId
   ↓
handlerMap
   ↓
TransactionHandler
```

개념적으로:

```java
Map<String, TransactionHandler> handlerMap;
```

을 가지고 있고 거래가 들어오면:

```text
serviceId 추출
     ↓
handlerMap.get(serviceId)
     ↓
Handler
```

로 실행합니다.

예:

```text
mgcoa9001S0
      │
      ▼
TransactionDispatcher
      │
      ▼
mgcoa9001Handler
      │
      ▼
mgcoa9001Facade
      │
      ▼
mgcoa9001Service
```

따라서 Dispatcher의 Architecture Responsibility는:

> **ServiceId → TransactionHandler Registry**

입니다.

업무 로직은 Dispatcher에 넣지 않습니다.

---

# 8. Handler부터가 Business 영역

아키텍처 경계는 다음처럼 보는 것이 좋습니다.

```text
               FRAMEWORK

OnlineTransactionController
        ↓
TcfFacade
        ↓
STF
        ↓
Timeout / Transaction
        ↓
Dispatcher

────────────────────────── Framework / Business Boundary

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
```

실제 PDMG AS-IS 업무 구조는:

```text
Handler
 → Facade
 → BizPrePostAspect
 → Service
 → DAO
 → Mapper
 → DB
```

에 가깝고, 별도의 `Rule` 패키지가 전체 업무에 일반화되어 있다고 보기는 어렵습니다. `Rule`은 TO-BE 확장 계층으로 구분하는 것이 정확합니다.

---

# 9. ETF — 거래 종료 공통처리

ETF는 STF의 반대쪽입니다.

```text
STF
"거래를 시작해도 되는가?"

        ↓

Business

        ↓

ETF
"거래를 어떻게 종료할 것인가?"
```

구조는:

```text
TcfFacade
   │
   ├─ STF
   │
   ├─ 업무 실행
   │
   └─ finally
        │
        ▼
       ETF
```

즉 업무에서 예외가 발생해도 ETF는 `finally` 성격으로 실행됩니다.

특히 현재 PDMG 에러처리에서는 **원래 업무 예외를 ETF 예외가 덮어쓰지 않도록 Primary/Suppressed 관계를 유지하는 구조**로 정리되어 있습니다.

---

# 10. Context 구조도 TCF에서 중요하다

PDMG에는 이름이 비슷한 두 Context가 존재합니다.

```text
ServiceContext
      ≠
TransactionContext
```

### ServiceContext

HTTP 요청 전체의 공통정보입니다.

```text
ServiceContext
│
├─ GUID
├─ ServiceId
├─ User
├─ Branch
├─ Client IP
├─ hdr_nhnis
├─ Request
└─ Response
```

`ServiceContextHolder(ThreadLocal)`를 통해 Framework에서 접근합니다.

### TransactionContext

TCF 거래 실행을 위한 문맥입니다.

```text
TransactionContext
│
├─ ServiceId
├─ 거래 시작시간
├─ Timeout / Deadline 관련 정보
└─ ServiceContext 참조
```

둘 다 **DB Transaction 자체는 아닙니다.**

---

# 11. 에러처리도 TCF 실행 생명주기의 일부다

업무에서는 오류 응답 JSON을 직접 만들지 않는 것이 기본 구조입니다.

```text
Service
   │
   X BizException
   │
   ▼
TransactionTemplate
   │
   ▼
ROLLBACK
   │
   ▼
TcfFacade
   │
   ▼
ETF
   │
   ▼
GlobalExceptionHandler
   │
   ▼
exceptionCode.yml
   │
   ▼
표준 오류 DTO
   │
   ▼
hdr_nhnis + result
```

즉:

```text
업무 Application
= 예외를 던진다

pdmg-fw
= 예외를 표준 오류 전문으로 변환한다
```

라는 책임분리가 핵심입니다.

---

# 12. TCF 공통 선·후처리는 3단계로 구분해야 한다

전체를 더 정확하게 보면:

```text
① System Common
──────────────────────────
Filter
Interceptor
JWT
Context
전문 Logging

        ↓

② Transaction Common
──────────────────────────
TCF
STF
Timeout
Transaction
Dispatcher
ETF

        ↓

③ Business Common
──────────────────────────
Handler
Facade
BizPrePostAspect
Service
```

따라서 `Filter`, `Interceptor`, `STF`, `BizPrePostAspect`를 전부 같은 “선처리”라고 보면 구조를 잘못 이해하게 됩니다.

---

# 13. ServiceId가 TCF 전체를 관통한다

PDMG TCF를 제대로 이해하려면 `ServiceId`를 중심에 놓는 것이 좋습니다.

```text
                     ServiceId
                        │
        ┌───────────────┼─────────────────┐
        │               │                 │
        ▼               ▼                 ▼
    Dispatcher        Timeout          거래통제
        │               │                 │
        ▼               │                 ▼
     Handler            │                STF
        │               │
        ▼               │
     Facade             │
        │               │
        ▼               │
     Service            │
        │               │
        ▼               │
       DAO              │
        │               │
        ▼               │
      Mapper            │
        │               │
        ▼               │
       SQL              │
        │               │
        ▼               │
      Table             │
                        │
        ┌───────────────┴───────────────┐
        ▼                               ▼
      Error                           Runtime
      Log                             Evidence
```

즉 ServiceId는 단순 Dispatcher Key가 아니라 **PDMG 온라인 거래의 Architecture Key**라고 볼 수 있습니다.

---

## 14. 각 구성요소를 한 표로 정리하면

| 구성요소                       | 위치                       | 핵심 책임               |
| ------------------------------ | -------------------------- | ----------------------- |
| `DefaultFilter`                | `pdmg-fw commons`          | Body/Header/Context/MDC |
| `ServicePreventionInterceptor` | `pdmg-fw commons`          | JWT, 사용자, 전문로그   |
| `OnlineTransactionController`  | `pdmg-fw tcf`              | 온라인 공통 진입점      |
| `TcfFacade`                    | `pdmg-fw tcf`              | 거래 실행 Lifecycle     |
| `STF`                          | `pdmg-fw tcf`              | 거래 실행 전 공통 정책  |
| `OnlineTimeoutExecutor`        | `pdmg-fw tcf`              | Deadline/Worker 실행    |
| `TransactionTemplate`          | `pdmg-fw tcf`              | 최외곽 DB TX            |
| `TransactionDispatcher`        | `pdmg-fw tcf`              | ServiceId → Handler     |
| `TransactionHandler`           | Framework 계약 / 업무 구현 | Use Case 진입           |
| `ETF`                          | `pdmg-fw tcf`              | 거래 종료 공통처리      |
| `GlobalExceptionHandler`       | `pdmg-fw commons`          | 표준 오류 응답          |
| `ServiceContext`               | `pdmg-fw commons`          | HTTP 요청 공통문맥      |
| `TransactionContext`           | `pdmg-fw tcf`              | TCF 실행문맥            |
| `Facade/Service/DAO`           | `pdmg-service`             | 실제 업무처리           |

---

# 15. 한 문장으로 정리

`pdmg-fw`의 TCF를 가장 정확하게 표현하면:

```text
                    pdmg-fw TCF

HTTP 공통문맥 준비
        ↓
거래 실행 가능 여부 판단
        ↓
ServiceId별 Timeout 정책 적용
        ↓
Worker Thread 실행
        ↓
최외곽 Transaction 시작
        ↓
ServiceId → Handler Routing
        ↓
업무 Application 실행
        ↓
Commit / Rollback
        ↓
ETF 종료처리
        ↓
표준 정상/오류 응답
        ↓
운영 추적
```

즉 **`pdmg-fw` TCF는 “업무 프로그램을 호출해 주는 Dispatcher” 정도가 아니라, ServiceId를 중심으로 `Context + Control + Timeout + Transaction + Dispatch + Error + Observability`를 하나의 거래 생명주기로 통제하는 실행 프레임워크**라고 보는 것이 핵심입니다.
