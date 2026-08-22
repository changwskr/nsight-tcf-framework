## `pdmg-fw`의 `commons` 공통 아키텍처

업로드된 현재 PDMG 소스를 다시 확인해 보면, `nhnis.fw.commons.*`는 단순한 `StringUtil`, `DateUtil` 같은 **공통 Utility 패키지**가 아닙니다.

정확하게는:

> **PDMG의 HTTP 요청이 TCF에 들어가기 전과 업무 처리가 끝난 후를 감싸는 “System Common Runtime Layer”**

라고 보는 것이 가장 적절합니다.

PDMG의 Framework 영역은 크게 `commons`와 `tcf`로 나뉘며, `commons`는 Filter·Context·JWT·전문·ImageLog·Runtime 등을, `tcf`는 Controller·STF/ETF·Timeout·Dispatcher 등의 거래 실행을 담당합니다.

---

# 1. 전체 Big Picture

```text
                         PDMG FRAMEWORK

┌───────────────────────────────────────────────────────────────┐
│                    nhnis.fw.commons                          │
│                 SYSTEM COMMON LAYER                          │
│                                                               │
│  configuration                                                │
│       │                                                       │
│       ▼                                                       │
│  DefaultFilter                                                │
│       │                                                       │
│       ├─ Request Body Cache                                   │
│       ├─ hdr_nhnis Parsing                                    │
│       ├─ GUID / MDC                                           │
│       └─ ServiceContext                                       │
│       │                                                       │
│       ▼                                                       │
│  ServicePreventionInterceptor                                 │
│       │                                                       │
│       ├─ JWT 검증                                             │
│       ├─ Header/User/IP 보강                                  │
│       ├─ 요청 전문 로그                                      │
│       └─ PRE ImageLog                                        │
│       │                                                       │
│       ▼                                                       │
│  RequestBodyArgumentResolver                                  │
│       │                                                       │
│       └─ 표준전문에서 dto 추출                               │
└───────┼───────────────────────────────────────────────────────┘
        │
        │  SYSTEM COMMON → TRANSACTION FRAMEWORK 경계
        ▼
┌───────────────────────────────────────────────────────────────┐
│                       nhnis.fw.tcf                            │
│                                                               │
│ OnlineTransactionController                                   │
│       ↓                                                       │
│ TcfFacade                                                     │
│       ↓                                                       │
│ STF ──────────────→ commons.txcontrol                         │
│       ↓                                                       │
│ Timeout Executor                                              │
│       ↓                                                       │
│ Worker Thread                                                 │
│       ↓                                                       │
│ TransactionTemplate                                           │
│       ↓                                                       │
│ Dispatcher                                                    │
│       ↓                                                       │
│ Handler                                                       │
└───────┼───────────────────────────────────────────────────────┘
        │
        │ Framework / Business 경계
        ▼
┌───────────────────────────────────────────────────────────────┐
│                       pdmg-service                            │
│                                                               │
│ Handler → Facade → Service → DAO → Mapper → DB               │
└───────┬───────────────────────────────────────────────────────┘
        │
        ▼
┌───────────────────────────────────────────────────────────────┐
│                    nhnis.fw.commons                          │
│                    SYSTEM POST                               │
│                                                               │
│ ResponseBodyArgumentResolver                                  │
│       │                                                       │
│       ├─ 정상 : hdr_nhnis + dto                              │
│       └─ 오류 : hdr_nhnis + result                           │
│                                                               │
│ ServicePreventionInterceptor.afterCompletion                  │
│       ├─ POST ImageLog                                       │
│       └─ Exception ImageLog                                  │
│                                                               │
│ DefaultFilter finally                                         │
│       ├─ ServiceContextHolder.remove                          │
│       └─ MDC clear                                            │
└───────────────────────────────────────────────────────────────┘
```

현재 PDMG의 전체 실행 흐름도 `DefaultFilter → ServicePreventionInterceptor → Controller → TCF → STF → Timeout → Dispatcher → Handler → 업무 → ETF → 시스템 후처리`로 정리되어 있습니다.

---

# 2. 현재 `commons` 실제 패키지

현재 ZIP의 `pdmg-fw/src/main/java/nhnis/fw/commons`를 직접 확인하면 다음 영역이 존재합니다.

| 패키지          | 핵심 책임                               |
| --------------- | --------------------------------------- |
| `configuration` | Filter, MVC, Security, HTTP Client 구성 |
| `filter`        | HTTP 요청 전처리, Body Cache            |
| `interceptor`   | 시스템 선·후처리                        |
| `resolver`      | Request DTO 바인딩, Response 전문 조립  |
| `context`       | 요청 단위 공통 Context                  |
| `dto.header`    | `hdr_nhnis`, `sys_comm`                 |
| `dto`           | 표준 오류 DTO                           |
| `jwt`           | JWT 검증                                |
| `imagelog`      | PRE/POST/EXCEPTION ImageLog             |
| `log`           | Framework 거래흐름/전문 로그            |
| `exception`     | commons 예외 기반                       |
| `message`       | 오류·업무 메시지 Cache                  |
| `runtime`       | Active TX, JVM, Thread, DB Pool 관측    |
| `txcontrol`     | 거래통제 정책 조회·판정                 |
| `transaction`   | 과거 ThreadLocal TX 지원 구조           |
| `apigw`         | API Gateway 연계                        |
| `fos`           | Object Storage 연계                     |
| `util`          | String/Date/Number/AES/Mapping 등       |

따라서 구조적으로는 다음처럼 보는 것이 좋습니다.

```text
nhnis.fw.commons
│
├─ web-common
│   ├─ configuration
│   ├─ filter
│   ├─ interceptor
│   └─ resolver
│
├─ transaction-context
│   ├─ context
│   ├─ dto
│   └─ dto.header
│
├─ security
│   └─ jwt
│
├─ observability
│   ├─ log
│   ├─ imagelog
│   └─ runtime
│
├─ control
│   └─ txcontrol
│
├─ error-message
│   ├─ exception
│   └─ message
│
├─ integration
│   ├─ apigw
│   └─ fos
│
├─ legacy
│   └─ transaction
│
└─ util
```

---

# 3. 가장 중요한 `DefaultFilter`

현재 소스의:

```text
nhnis.fw.commons.filter.DefaultFilter
```

는 **PDMG HTTP Request의 최초 Framework 진입점**입니다.

현재 주요 책임은 다음과 같습니다.

```text
HTTP Request
     │
     ▼
DefaultFilter
     │
     ├─ Request Body 읽기
     │
     ├─ CachedBodyHttpServletRequest
     │
     ├─ hdr_nhnis 파싱
     │
     ├─ GUID 준비
     │
     ├─ ServiceId 준비
     │
     ├─ IP / User 추적정보
     │
     ├─ MDC 설정
     │
     └─ ServiceContext 생성
              │
              ▼
     ServiceContextHolder
           ThreadLocal
```

표준전문은:

```text
Request
│
├─ hdr_nhnis
│    └─ sys_comm
│
└─ dto
```

로 나뉘며, 시스템 공통 Header는 `ServiceContext`, 실제 업무정보는 업무 DTO 쪽으로 분리됩니다.

### 매우 중요한 현재 소스 기준

과거 일부 문서에는 `DefaultFilter`가 JWT를 검증한다고 되어 있지만, **현재 ZIP의 실제 소스에서는 JWT 검증 책임이 `ServicePreventionInterceptor`로 이동해 있습니다.**

현재:

```text
DefaultFilter
= Header / Body / Context / MDC

ServicePreventionInterceptor
= JWT + 시스템 선후처리
```

로 보는 것이 정확합니다.

---

# 4. `ServiceContext`가 commons의 중심이다

클래스:

```text
nhnis.fw.commons.context.ServiceContext
nhnis.fw.commons.context.ServiceContextHolder
```

현재 `ServiceContext`에는 대략 다음 정보가 있습니다.

```text
ServiceContext
│
├─ applicationName
├─ guid
├─ active profile
├─ requestHeaders
├─ HttpServletRequest
├─ HttpServletResponse
│
├─ hdr_nhnis
│    └─ sys_comm
│
├─ userContext
├─ requestBody
└─ responseBody
```

그리고:

```java
ThreadLocal<ServiceContext>
```

으로 현재 요청 Thread에 연결합니다.

```text
Tomcat Worker Thread

       │
       ▼
ServiceContextHolder
       │
       ▼
ServiceContext

GUID
ServiceId
User
Branch
IP
Header
Request
Response
```

그래서 Framework의 여러 클래스가 공통정보를 매번 메서드 파라미터로 전달하지 않아도 됩니다.

다만 중요한 원칙은:

```text
ServiceContext
      ≠ DB Transaction

ServiceContext
      ≠ 업무 DTO

ServiceContext
      ≠ SqlSession

ServiceContext
      ≠ JDBC Connection
```

입니다.

---

# 5. Timeout Worker로 넘어갈 때 Context 문제

여기가 `commons`와 `tcf`가 연결되는 중요한 지점입니다.

`ServiceContextHolder`는 `ThreadLocal`입니다.

그래서:

```text
Request Thread
   │
   ├─ ServiceContext 존재
   │
   ▼
Timeout Executor
   │
   ▼
Worker Thread
```

로 Thread가 바뀌면 원래 값이 자동으로 넘어가지 않습니다.

현재 TCF에는 이를 보완하기 위한:

```text
nhnis.fw.tcf.timeout.OnlineTimeoutWorkerContext
```

가 있습니다.

개념적으로:

```text
Request Thread

ServiceContext
MDC
Security Context
      │
      │ capture
      ▼
OnlineTimeoutWorkerContext
      │
      │ restore
      ▼
Worker Thread

ServiceContextHolder.set(...)
MDC restore
```

가 됩니다.

따라서 `commons.context`와 `tcf.timeout`은 **서로 완전히 독립적이지 않고 Context 전파 지점에서 연결**됩니다.

---

# 6. 시스템 인증 — `commons.jwt`

현재 JWT 처리 구조는:

```text
pdmg-ui
   │
Authorization: Bearer ...
   ▼
pdmg-service
   ▼
DefaultFilter
   │
   ▼
ServicePreventionInterceptor
   │
   ▼
JwtProvider
   │
   ├─ Token 존재
   ├─ Signature
   ├─ 만료시간
   ├─ Access Token 여부
   └─ ssoId
   │
   ▼
인증 성공
```

입니다.

현재 PDMG 기준에서도 JWT는 `pdmg-fw`의 `ServicePreventionInterceptor → JwtProvider`에서 검증하는 구조로 정리되어 있습니다.

그리고 검증된 `ssoId`는 Request Attribute와 MDC 사용자 정보에 연결됩니다.

여기서:

```text
JWT 인증
     ≠
거래통제

JWT 인증
     ≠
업무 권한
```

을 구분해야 합니다.

---

# 7. Request Resolver

```text
nhnis.fw.commons.resolver.RequestBodyArgumentResolver
```

는 표준전문 전체를 업무 Controller에 던지는 것이 아니라:

```json
{
  "hdr_nhnis": { ... },
  "dto": {
     "customerNo": "..."
  }
}
```

에서:

```text
dto
 ↓
업무 DTO Class
```

만 변환합니다.

즉:

```text
시스템 공통정보
hdr_nhnis
      │
      └─ ServiceContext

업무정보
dto
      │
      └─ DTOin
```

라는 중요한 분리를 만듭니다.

---

# 8. Response 공통 구조

현재:

```text
nhnis.fw.commons.resolver.ResponseBodyArgumentResolver
```

는 `@ControllerAdvice + ResponseBodyAdvice` 방식으로 최종 Response를 표준화합니다.

### 정상

```text
업무 결과 DTO
      │
      ▼
ResponseBodyArgumentResolver
      │
      ▼

{
   "hdr_nhnis": {...},
   "dto": {...}
}
```

### 오류

`NH_NIS_ERR_DTO`인 경우:

```text
NH_NIS_ERR_DTO
      │
      ▼
ResponseBodyArgumentResolver
      │
      ▼

{
   "hdr_nhnis": {...},
   "result": {
       ...
   }
}
```

즉 PDMG의 중요한 전문 규칙은:

```text
정상
hdr_nhnis + dto

오류
hdr_nhnis + result
```

입니다. 이 Header/DTO/Result 분리는 표준전문 아키텍처에서도 명확하게 정의되어 있습니다.

---

# 9. ImageLog 아키텍처

```text
nhnis.fw.commons.imagelog.ImageLogHandler
```

는 일반 로그파일과 다른 **거래 증적성 데이터**입니다.

현재 구현에는:

```text
preImagelog()
postImagelog()
exceptionImagelog()
```

가 존재합니다.

전체적으로:

```text
Request
   │
   ▼
Interceptor.preHandle
   │
   ▼
PRE ImageLog
   │
   ▼
업무 처리
   │
 ┌─┴────────────────┐
 │                  │
정상               Exception
 │                  │
 ▼                  ▼
POST               EXCEPTION
ImageLog           ImageLog
```

입니다.

DB에는:

```text
TB_FW_IMAGE_LOG
```

를 사용합니다.

특히 ImageLog는 업무 DAO가 책임지는 구조가 아니라 **Framework System Common 영역의 책임**입니다.

---

# 10. 거래통제는 왜 `commons.txcontrol`에 있는가

현재 실제 클래스:

```text
nhnis.fw.commons.txcontrol.MgTxControlService
nhnis.fw.commons.txcontrol.MgTxControlRepository
nhnis.fw.commons.txcontrol.MgTxControlRule
...
```

입니다.

그러나 실행 위치는 TCF입니다.

```text
TCF
 │
 ▼
STF.preProcess()
 │
 ▼
commons.txcontrol
MgTxControlService.check()
 │
 ▼
통제 정책
 │
 ├─ PASS
 │
 └─ BLOCK → BizException
```

즉 책임은 이렇게 구분됩니다.

```text
commons.txcontrol

"거래통제 정책을
 어떻게 조회하고 판정할 것인가?"
           │
           ▼
          TCF.STF

"언제 거래통제를 실행할 것인가?"
```

현재 TCF 선처리에서도 거래통제는 `STF → MgTxControlService.check()` 구조입니다.

이 책임 분리는 잘 되어 있습니다.

---

# 11. Runtime Monitoring도 commons에 있다

현재 소스에는:

```text
nhnis.fw.commons.runtime

├─ MgActiveTransactionRegistry
├─ MgRuntimeMonitor
└─ MgTomcatThreadPoolProbe
```

가 있습니다.

실제 연결은:

```text
TcfFacade
   │
   ├─ begin
   ▼
MgActiveTransactionRegistry
   │
   └─ 현재 실행 거래
          │
          ▼
MgRuntimeMonitor
     │
     ├─ Active Transaction
     ├─ Tomcat Thread
     ├─ JVM Heap
     ├─ GC
     ├─ CPU
     └─ Hikari Pool
          │
          ▼
pdmg-service
mgcoa9100Service
```

입니다.

따라서 `commons`는 단순 개발 공통기능뿐 아니라 **운영 공통기능까지 포함**하고 있습니다.

---

# 12. `commons.transaction`은 특히 주의해야 한다

현재 패키지에는:

```text
commons.transaction

├─ InitTransactionManager
├─ ServiceTransactionManager
└─ TransactionManagerHolder
```

가 존재합니다.

구조 자체는:

```text
ThreadLocal
   ↓
TransactionManagerHolder
   ↓
PlatformTransactionManager
   ↓
begin / commit / rollback
```

입니다.

하지만 현재 ZIP 전체의 Java Source를 다시 추적해 보면 이 3개 클래스는 **현재 핵심 TCF 실행 흐름에서 사용되지 않고 해당 패키지 내부에서만 연결되어 있습니다.**

따라서 이것을 현재 PDMG 온라인 거래의 Transaction Architecture로 설명하면 안 됩니다.

현재 핵심은:

```text
[현재 Transaction Architecture]

OnlineTimeoutExecutor
       ↓
Worker Thread
       ↓
TransactionTemplate
       ↓
TX BEGIN
       ↓
Dispatcher
       ↓
Handler
       ↓
Facade / Service
       ↓
DAO
       ↓
COMMIT / ROLLBACK
```

입니다.

즉:

```text
commons.transaction
= Legacy / Compatibility 성격

tcf.timeout + TransactionTemplate
= 현재 온라인 Transaction 핵심
```

으로 구분하는 것이 맞습니다.

이 구분은 상당히 중요합니다.

---

# 13. 외부연계 공통

`commons`에는 외부 연계 유틸도 들어 있습니다.

```text
commons.apigw
   │
   └─ ApiGatewayHandler
          ↓
       WebClient/API

commons.fos
   │
   └─ ObjectStorageHandler
          ↓
      Object Storage
```

즉 업무 Service마다 HTTP Client나 Object Storage 처리를 제각각 구현하지 않고 Framework 공통 Adapter 성격의 기능을 제공합니다.

---

# 14. `commons`와 `tcf`의 경계를 한 장으로 보면

가장 중요합니다.

```text
┌─────────────────────────────────────────────────┐
│                 COMMONS                         │
│                                                 │
│ "거래를 실행할 수 있는 시스템 환경을 만든다"   │
│                                                 │
│ Request                                         │
│ Context                                         │
│ Header                                          │
│ JWT                                             │
│ Logging                                         │
│ ImageLog                                        │
│ Runtime                                         │
│ Control Policy                                  │
│ Response                                        │
└───────────────────┬─────────────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────────────┐
│                    TCF                          │
│                                                 │
│ "하나의 온라인 거래를 실행·통제한다"           │
│                                                 │
│ OnlineTransactionController                     │
│ TcfFacade                                       │
│ STF                                             │
│ Timeout                                         │
│ Transaction                                     │
│ Dispatcher                                      │
│ ETF                                             │
└───────────────────┬─────────────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────────────┐
│              BUSINESS APPLICATION               │
│                                                 │
│ "실제 업무를 처리한다"                         │
│                                                 │
│ Handler                                         │
│ Facade                                          │
│ Service                                         │
│ DAO                                             │
│ Mapper                                          │
│ DB                                              │
└─────────────────────────────────────────────────┘
```

이를 한 문장씩 정리하면:

| 영역         | 핵심 질문                                            |
| ------------ | ---------------------------------------------------- |
| **commons**  | 이 HTTP 요청을 시스템 거래로 사용할 준비가 되었는가? |
| **TCF**      | 이 거래를 어떤 정책과 실행경계로 수행할 것인가?      |
| **Business** | 실제 업무를 어떻게 처리할 것인가?                    |

---

# 15. 제가 보는 현재 `commons` 아키텍처의 핵심 개선점

현재 구조는 기능적으로 상당히 잘 갖추어져 있지만, **`commons`라는 이름 아래 책임이 너무 많이 모여 있습니다.**

현재는:

```text
commons

HTTP
Context
JWT
Error
Logging
ImageLog
Runtime
TxControl
Integration
Legacy TX
Utility
```

까지 모두 포함합니다.

장기적으로는 논리적으로 다음 정도로 나누는 것이 더 명확합니다.

```text
nhnis.fw

├─ web
│   ├─ filter
│   ├─ interceptor
│   └─ resolver
│
├─ context
├─ message
├─ security
├─ observability
│   ├─ log
│   ├─ imagelog
│   └─ runtime
│
├─ control
│   └─ txcontrol
│
├─ integration
│   ├─ apigw
│   └─ fos
│
├─ util
│
└─ tcf
    ├─ stf
    ├─ timeout
    ├─ transaction
    ├─ dispatch
    └─ etf
```

다만 **현재 소스를 당장 이렇게 변경하자는 뜻은 아닙니다.** 현재 AS-IS는 `nhnis.fw.commons.*`이고, 위 구조는 향후 패키지 책임을 더 명확히 만들 때의 TO-BE 후보입니다.

### 핵심만 기억하면

```text
              pdmg-fw

                 │
       ┌─────────┴─────────┐
       │                   │
    commons               tcf
       │                   │
       │                   │
 시스템 실행환경         거래 실행엔진
       │                   │
 Filter                  STF
 Context                 Timeout
 JWT                     Transaction
 전문                    Dispatcher
 Logging                 ETF
 ImageLog
 Runtime
 TxControl
       │                   │
       └────────┬──────────┘
                ▼
             Handler
                ▼
             Facade
                ▼
             Service
                ▼
           DAO / Mapper
```

즉 **`commons`는 PDMG의 “시스템 공통 기반”, `tcf`는 “온라인 거래 실행 기반”**이라고 구분하면 `pdmg-fw` 전체 구조가 가장 쉽게 이해됩니다.
