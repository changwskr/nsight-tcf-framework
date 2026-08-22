# Controller 아키텍처 구조

현재 **PDMG 실제 소스 기준**에서 Controller는 업무 로직을 수행하는 계층이 아니라, **HTTP 요청을 받아 `ServiceId + 업무 DTO`를 내부 실행구조로 전달하는 Inbound Adapter**입니다.

특히 PDMG에는 Controller가 하나만 있는 것이 아니라 **TCF ON용 공통 Controller**와 **TCF OFF용 업무 Controller**가 공존합니다. TCF ON에서는 `pdmg-fw`의 `OnlineTransactionController`가 공통 진입점이고, OFF에서는 `pdmg-service`의 `mgcoaXXXXController`가 직접 HTTP 요청을 받습니다.

## 1. 전체 Controller Big Picture

```text
                         PDMG CONTROLLER ARCHITECTURE

┌───────────────────────────────────────────────────────────────┐
│                         Client / pdmg-ui                      │
│                                                               │
│ Authorization : Bearer JWT                                   │
│ hdr_nhnis + dto                                              │
│ ServiceId = mgcoa9001S0                                      │
└─────────────────────────────┬─────────────────────────────────┘
                              │
                              ▼
                    DefaultFilter
                              │
                              ▼
              ServicePreventionInterceptor
                              │
                              ▼
════════════════════════════════════════════════════════════════
                    HTTP CONTROLLER BOUNDARY
════════════════════════════════════════════════════════════════

                      TCF ON ?
                         │
              ┌──────────┴──────────┐
              │                     │
             YES                    NO
              │                     │
              ▼                     ▼

   OnlineTransactionController      Business Controller
          pdmg-fw                   pdmg-service

        공통 진입점                 업무별 진입점
              │                     │
     ServiceId 결정                 Typed DTO
     dto 분리                        │
              │                     │
              ▼                     ▼
          TcfFacade              Business Facade
              │                 (권장 구조)
              ▼                     │
             STF                    ▼
              │                  Service
              ▼                     │
      Timeout / Transaction         ▼
              │                    DAO
              ▼
         Dispatcher
              │
          ServiceId
              ▼
           Handler
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
              DB
```

TCF ON의 기본 온라인 흐름은 `OnlineTransactionController → TcfFacade → Dispatcher → Handler → Facade → Service → DAO`이고, TCF OFF에서는 업무별 Spring MVC Controller가 직접 진입 Adapter가 됩니다.

---

# 2. TCF ON Controller — `OnlineTransactionController`

현재 실제 위치는 다음입니다.

```text
pdmg-fw
└─ src/main/java
   └─ nhnis/fw/tcf/web
      └─ OnlineTransactionController.java
```

활성 조건은:

```java
@ConditionalOnProperty(
    name = "nhnis.fw.tcf.enabled",
    havingValue = "true"
)
```

입니다.

즉:

```text
nhnis.fw.tcf.enabled=true

        ↓

OnlineTransactionController 활성
```

이 Controller의 역할은 상당히 좁습니다.

```text
HTTP Request
     │
     ▼
OnlineTransactionController
     │
     ├─ ServiceId 결정
     ├─ Client IP 보정
     ├─ dto 영역 추출
     └─ TcfFacade.process(serviceId, dto)
     │
     ▼
TCF
```

현재 온라인 거래 기준에서도 Controller 책임은 **ServiceId 결정 → DTO 추출 → TCF 호출**로 제한하고 있습니다.

---

# 3. 현재 공통 Controller가 지원하는 URL

실제 `OnlineTransactionController`는 세 가지 HTTP 진입형태를 지원합니다.

```text
① POST /online

② POST /{businessCode}/online

③ POST /{serviceId}
```

예:

```text
POST /online

hdr_nhnis.sys_comm.rms_svc_c
        =
mgcoa9001S0
```

또는:

```text
POST /mgcoa9001S0
```

형태입니다.

하지만 내부에서는 결국 모두:

```text
HTTP URL
    │
    ▼
ServiceId 결정
    │
    ▼
TcfFacade.process(serviceId, dto)
```

로 통합됩니다.

따라서 **URL이 업무 실행 구조를 결정하는 것이 아니라 ServiceId가 실제 거래 Identity가 됩니다.**

---

# 4. ServiceId 결정 구조

현재 공통 Controller는 ServiceId를 여러 위치에서 받을 수 있습니다.

개념적인 우선순위는:

```text
① ServiceContext Header
       │
       ▼
hdr_nhnis.sys_comm.rms_svc_c

       없으면

② Request JSON Header
       │
       ▼
hdr_nhnis.sys_comm.rms_svc_c

       없으면

③ URL Path
       │
       ▼
/mgcoa9001S0
```

입니다.

즉:

```text
             ServiceId Resolution

ServiceContext
      │
      │ 존재?
      ├──── YES ──→ 사용
      │
      ▼ NO
Request Header
      │
      │ 존재?
      ├──── YES ──→ 사용
      │
      ▼ NO
URL Path
      │
      ▼
ServiceId
```

이렇게 얻은 ServiceId가 이후 전체 아키텍처의 추적키가 됩니다.

```text
ServiceId
   │
   ├─ Dispatcher
   ├─ Handler
   ├─ Timeout Policy
   ├─ Transaction
   ├─ 거래통제
   ├─ Logging
   ├─ ImageLog
   └─ Runtime Evidence
```

---

# 5. Controller는 전문 전체를 업무 계층으로 보내지 않는다

현재 요청 전문은:

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "std_gbl_id": "GUID-...",
      "rms_svc_c": "mgcoa9001S0",
      "optr_eno": "E00001"
    }
  },
  "dto": {
    "controlType": "SERVICE",
    "targetValue": "mgcoa8888S0"
  }
}
```

구조입니다.

Controller를 통과하면서 논리적으로:

```text
Standard Request
      │
      ├───────────────┐
      ▼               ▼
 hdr_nhnis           dto
      │               │
      ▼               ▼
ServiceContext     Business Data
                      │
                      ▼
                    TCF
```

로 나뉩니다.

공통 Header는 Framework의 `ServiceContext`가 관리하고, Controller는 업무 `dto`를 TCF로 넘깁니다. 전문 구조 자체도 공통 Header와 업무 DTO를 분리하도록 정의되어 있습니다.

---

# 6. TCF ON에서 Controller 다음에 바로 Service가 오지 않는다

이 부분이 일반적인 Spring MVC와 PDMG의 가장 큰 차이입니다.

일반적인 구조는 흔히:

```text
Controller
   ↓
Service
   ↓
Repository
```

지만 PDMG TCF ON에서는:

```text
OnlineTransactionController
          │
          ▼
      TcfFacade
          │
          ▼
         STF
          │
          ▼
 Timeout Executor
          │
          ▼
TransactionDispatcher
          │
       ServiceId
          ▼
       Handler
          │
          ▼
       Facade
          │
          ▼
       Service
          │
          ▼
         DAO
```

입니다.

즉 **Controller와 업무 Service 사이에 TCF 실행통제 계층이 존재**합니다.

TCF ON에서 실제 업무 진입 컴포넌트는 Controller가 아니라 Dispatcher가 선택한 `TransactionHandler`입니다.

---

# 7. Controller와 Handler의 차이

두 컴포넌트가 특히 헷갈립니다.

| 구분        | Controller     | Handler                              |
| ----------- | -------------- | ------------------------------------ |
| 경계        | HTTP           | TCF ↔ Business                       |
| 입력        | HTTP Request   | ServiceId + DTO + TransactionContext |
| 핵심 역할   | HTTP Adapter   | Use Case Adapter                     |
| ServiceId   | 추출/전달      | 거래분기                             |
| 전문 처리   | 가능           | 금지                                 |
| Facade 호출 | TCF ON에서는 X | O                                    |
| DAO 호출    | X              | X                                    |
| 업무 로직   | X              | X                                    |
| SQL         | X              | X                                    |

구조로 보면:

```text
HTTP 세계
   │
   ▼
Controller
   │
   │ HTTP → TCF
   ▼
TCF
   │
   ▼
Dispatcher
   │
   ▼
Handler
   │
   │ TCF → Business
   ▼
Facade
```

따라서:

> **Controller는 HTTP Adapter이고, Handler는 ServiceId 기반 Business Adapter입니다.**

---

# 8. Controller에서 하면 안 되는 것

Controller는 가능한 한 **Thin Controller**로 유지해야 합니다.

```text
Controller
│
├─ HTTP Mapping               O
├─ Request Binding            O
├─ ServiceId 추출             O
├─ DTO 추출                   O
├─ 최소 구조 Validation       O
└─ TCF/Facade 호출            O
```

반대로:

```text
Controller
│
├─ 고객 조회                  X
├─ 업무 Rule                  X
├─ DAO 직접 호출              X
├─ Mapper 호출                X
├─ SQL                        X
├─ Paging 계산                X
├─ 중복검사                    X
├─ 거래통제                    X
├─ Transaction 정책 결정       X
└─ 업무별 거대한 switch       X
```

입니다.

현재 프로젝트 기준에서도 Controller에 고객조회·SQL·DAO·업무 Transaction 등을 넣지 않도록 명확히 분리하고 있습니다.

---

# 9. Transaction도 Controller에서 시작하지 않는 것이 핵심

현재 PDMG에서는 Controller를 Transaction Owner로 보는 것이 잘못된 이해입니다.

### TCF ON + Timeout ON

```text
Request Thread

Controller
   ↓
TcfFacade
   ↓
Timeout Executor
   │
   │ Thread 전환
   ▼

Worker Thread

TransactionTemplate
   │
   ├──── TX BEGIN
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
```

따라서 이 경우:

```text
Controller Transaction      X

Worker TransactionTemplate  O
```

입니다.

### TCF ON + Timeout OFF

```text
Controller
 ↓
TCF
 ↓
Dispatcher
 ↓
Handler
 ↓
Facade
@Transactional
 ↓
TX BEGIN
```

가 될 수 있습니다.

그래서 **Controller에 `@Transactional`을 넣어서 해결하려고 해서는 안 됩니다.**

---

# 10. TCF OFF Controller

현재 `pdmg-service`에는 실제 업무 Controller들도 있습니다.

```text
pdmg-service
└─ nhnis.mg.co.a.application.controller
    │
    ├─ mgcoa5530Controller
    ├─ mgcoa8888Controller
    ├─ mgcoa9000Controller
    ├─ mgcoa9001Controller
    ├─ mgcoa9100Controller
    └─ mgcoa9999Controller
```

이 Controller들은:

```java
@ConditionalOnProperty(
    name = "nhnis.fw.tcf.enabled",
    havingValue = "false",
    matchIfMissing = true
)
```

조건으로 활성화됩니다.

즉:

```text
TCF ON
────────────────────────
OnlineTransactionController O
Business Controller        X


TCF OFF
────────────────────────
OnlineTransactionController X
Business Controller        O
```

입니다. TCF OFF는 단순 옵션 변화가 아니라 Dispatcher·Handler 등의 진입 Architecture를 바꾸는 모드입니다.

---

# 11. 현재 TCF OFF AS-IS에는 한 가지 구조적 문제도 있습니다

예를 들어 현재 `mgcoa9001Controller`는:

```text
Controller
    ↓
Service
    ↓
DAO
```

형태입니다.

즉 TCF ON의:

```text
Handler
   ↓
Facade
   ↓
Service
```

와 경계가 다릅니다.

현재 OFF Controller들도 대부분 Service를 직접 호출하고 일부만 Facade를 호출해 완전히 통일되어 있지는 않습니다.

이것은 장기적으로 다음 구조가 더 좋습니다.

```text
                  [TO-BE]

TCF ON
────────────────────────────────
OnlineTransactionController
       ↓
TCF / Dispatcher
       ↓
Handler
       │
       └───────────┐
                   ▼
                 Facade
                   │
                   ▼
                 Service
                   │
                   ▼
                  DAO


TCF OFF
────────────────────────────────
Business Controller
       │
       └───────────┐
                   ▼
                 Facade
                   │
                   ▼
                 Service
                   │
                   ▼
                  DAO
```

즉:

> **진입 Adapter는 달라도 Facade 이하의 업무 구조는 동일하게 만드는 것이 바람직합니다.**

이 방향은 현재 TCF OFF 호환 구조의 권장 경계와도 일치합니다.

---

# 12. 권장 Controller 표준

최종적으로 NSIGHT/PDMG Controller 표준은 다음처럼 정리하는 것이 좋습니다.

| Rule | 기준                                                          |
| ---- | ------------------------------------------------------------- |
| C-01 | Controller는 HTTP Inbound Adapter다                           |
| C-02 | ServiceId와 DTO를 추출한다                                    |
| C-03 | TCF ON에서는 `TcfFacade`만 호출한다                           |
| C-04 | 업무별 라우팅은 Dispatcher/Handler가 담당한다                 |
| C-05 | TCF OFF에서는 Business Facade를 호출한다                      |
| C-06 | Controller → DAO 금지                                         |
| C-07 | Controller → Mapper 금지                                      |
| C-08 | Controller에 업무 Rule 금지                                   |
| C-09 | Controller에 SQL 금지                                         |
| C-10 | Controller를 Transaction Owner로 사용하지 않는다              |
| C-11 | 공통 Header는 `ServiceContext`에 둔다                         |
| C-12 | 업무 DTO만 업무계층으로 전달한다                              |
| C-13 | 예외를 Controller에서 임의로 정상응답으로 변환하지 않는다     |
| C-14 | 인증/전문로그/ImageLog는 Filter/Interceptor 책임으로 유지한다 |
| C-15 | TCF ON/OFF 모두 Facade 이하 업무 구현을 공유한다              |

## 최종 구조

```text
                    PDMG CONTROLLER STANDARD

Client
  │
  ▼
Filter
  │
  ▼
Interceptor
  │
  ▼
┌──────────────────────────────┐
│ OnlineTransactionController  │
│                              │
│ HTTP Adapter                 │
│ ServiceId 결정               │
│ DTO 추출                     │
└──────────────┬───────────────┘
               │
               ▼
             TCF
               │
        ┌──────┼──────┐
        ▼      ▼      ▼
       STF  Timeout  Context
               │
               ▼
          Dispatcher
               │
          ServiceId
               ▼
            Handler
               │
               ▼
════════════════════════════════
          BUSINESS BOUNDARY
════════════════════════════════
               │
             Facade
               │
             Service
          ┌────┴────┐
          ▼         ▼
        Rule        DAO
                     │
                   Mapper
                     │
                     DB
```

한 문장으로 기억하면 **`Controller = HTTP를 업무로 바꾸는 곳`이 아니라 `HTTP 요청을 TCF 실행구조에 안전하게 진입시키는 곳`**입니다. 그리고 PDMG의 표준 온라인 구조에서는 **`Controller → TCF → Dispatcher → Handler → Facade → Service → DAO`**가 기준 흐름입니다.
