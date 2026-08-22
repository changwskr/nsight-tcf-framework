찾아봤습니다. 결론부터 말하면, **현재 PDMG 소스 안에 `architecture-model.json` 같은 완성된 모델 파일이 있는 것은 아닙니다.** 대신 `pdmg-ui`, `pdmg-fw`, `pdmg-jwt`, `pdmg-service`의 실제 클래스·설정·ServiceId·Mapper 관계를 읽으면 **아키텍처 모델을 추출할 수 있습니다.** 기존 Reference 설계 역시 이 네 프로젝트의 실제 Source/Configuration을 분석해 Reference Architecture를 추출하도록 정의하고 있습니다.

그리고 중요한 점 하나가 있습니다. 현재 ZIP에는 **독립적인 `pdmg-om` Java 모듈이 없습니다.** 운영 기능은 `pdmg-ui + pdmg-service + pdmg-fw`에 분산되어 있습니다.

## 1. 제가 실제 소스에서 찾은 PDMG 아키텍처 모델

전체를 가장 쉽게 표현하면 이것입니다.

```text
                       PDMG ARCHITECTURE MODEL

┌─────────────────────────────────────────────────────┐
│ ① UI MODEL                                          │
│ pdmg-ui                                             │
│                                                     │
│ Screen / Route                                      │
│      ↓                                              │
│ TransactionCatalog                                  │
│      ↓                                              │
│ ServiceId                                           │
│      ↓                                              │
│ TransactionRelay / HTTP                             │
└──────────────────────┬──────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────┐
│ ② FRAMEWORK MODEL                                   │
│ pdmg-fw                                             │
│                                                     │
│ DefaultFilter                                       │
│      ↓                                              │
│ ServicePreventionInterceptor                        │
│      ↓                                              │
│ OnlineTransactionController                         │
│      ↓                                              │
│ TcfFacade                                           │
│      ↓                                              │
│ STF                                                 │
│      ↓                                              │
│ TimeoutExecutor                                     │
│      ↓                                              │
│ TransactionTemplate                                 │
│      ↓                                              │
│ TransactionDispatcher                               │
└──────────────────────┬──────────────────────────────┘
                       │ ServiceId
                       ▼
┌─────────────────────────────────────────────────────┐
│ ③ BUSINESS APPLICATION MODEL                        │
│ pdmg-service                                        │
│                                                     │
│ Handler                                             │
│      ↓                                              │
│ Facade                                              │
│      ↓                                              │
│ Service                                             │
│      ↓                                              │
│ DAO                                                 │
│      ↓                                              │
│ Mapper / SQL                                        │
│      ↓                                              │
│ Table                                               │
└─────────────────────────────────────────────────────┘


       별도 Security Plane

┌─────────────────────────────────────────────────────┐
│ ④ JWT SECURITY MODEL                                │
│ pdmg-jwt                                            │
│                                                     │
│ Authentication                                      │
│      ↓                                              │
│ JWT ServiceId                                       │
│      ↓                                              │
│ Handler → Facade → Service → DAO                    │
│      │                                              │
│      ├─ JwtTokenIssuer → Private Key → RS256 JWT    │
│      ├─ JwtTokenStore → Refresh / Revoke            │
│      └─ JWKSetController → Public Key / JWKS        │
└─────────────────────────────────────────────────────┘


       별도 Control Plane

┌─────────────────────────────────────────────────────┐
│ ⑤ OPERATION MODEL                                   │
│ 논리적 pdmg-om                                      │
│                                                     │
│ 거래통제 / Runtime / ImageLog / Error / 정책        │
│                                                     │
│ 현재 실제 구현:                                     │
│ pdmg-ui + pdmg-service + pdmg-fw 에 분산             │
└─────────────────────────────────────────────────────┘
```

기존 Reference 설계도 이를 `Framework Model`, `Business Application Model`, `JWT Security Model`, `UI Integration Model`로 분리하도록 정의하고 있습니다.

---

# 2. `pdmg-ui`에서 찾은 모델

실제 소스에서 가장 명확한 모델은 이것입니다.

```text
Screen
  ↓
TransactionCatalog
  ↓
TransactionInfo
  ↓
ServiceId
  ↓
Target URL
  ↓
pdmg-service
```

실제 클래스:

```text
pdmg-ui

TransactionCatalog
TransactionInfo
TransactionRelayService
PdmgUiApiController
PdmgUiProperties
```

특히 `TransactionCatalog` 안에는 실제 거래가 등록되어 있습니다.

예를 들면:

```text
mgcoa8888S0    이미지로그 조회
mgcoa8888D0    이미지로그 삭제

mgcoa9000S0    거래 파라미터 조회
mgcoa9000C0    등록
mgcoa9000U0    수정
mgcoa9000D0    삭제

mgcoa9001S0    거래통제 조회
mgcoa9001C0    등록
mgcoa9001U0    수정
mgcoa9001D0    삭제

mgcoa9100S0    Runtime 진단
```

따라서 `pdmg-ui`에서 뽑아야 할 Model Entity는:

```text
UI_SCREEN
UI_ROUTE
TRANSACTION
SERVICE_ID
TARGET_APPLICATION
TARGET_URL
REQUEST_SAMPLE
```

관계는:

```text
Screen
  ──USES──>
Transaction

Transaction
  ──IDENTIFIED_BY──>
ServiceId

ServiceId
  ──CALLS──>
pdmg-service
```

입니다.

기존 Reference 문서에서도 `pdmg-ui`의 책임을 `Screen / UI Route → Transaction Catalog → ServiceId → pdmg-service 호출`로 정의합니다.

---

# 3. `pdmg-fw`에서 찾은 모델

여기가 **PDMG Architecture Model의 뼈대**입니다.

실제 주요 클래스가:

```text
DefaultFilter
ServicePreventionInterceptor

ServiceContext
ServiceContextHolder

OnlineTransactionController

TcfFacade
TransactionContext

stf
etf

OnlineTimeoutExecutor
DefaultOnlineTimeoutExecutor
OnlineTimeoutWorkerContext

TransactionDispatcher
TransactionHandler

JwtProvider

MgTxControlService
MgTxControlRepository

MgActiveTransactionRegistry
MgRuntimeMonitor

GlobalExceptionHandler
```

입니다.

따라서 모델은:

```text
HTTP_REQUEST
     │
     ▼
FILTER
DefaultFilter
     │
     ▼
INTERCEPTOR
ServicePreventionInterceptor
     │
     ├──── AUTHENTICATES ───→ JwtProvider
     │
     └──── CREATES ─────────→ ServiceContext
     │
     ▼
CONTROLLER
OnlineTransactionController
     │
     ▼
TCF
TcfFacade
     │
     ▼
STF
     │
     ├──── CHECKS ──────────→ TransactionControl
     │
     ▼
TIMEOUT_POLICY
     │
     ▼
TimeoutExecutor
     │
     ▼
TRANSACTION
TransactionTemplate
     │
     ▼
DISPATCHER
TransactionDispatcher
     │
     │ ServiceId
     ▼
TransactionHandler
```

실제 `TransactionDispatcher`에는:

```text
Map<String, TransactionHandler>
```

형태의 Registry가 있고 Handler의 `serviceIds()`를 읽어 등록합니다.

즉 Model 관계로 보면:

```text
ServiceId
   ──DISPATCHES_TO──>
Handler
```

입니다.

또 실제 Timeout Executor에는:

```text
TransactionTemplate
PROPAGATION_REQUIRED
```

가 존재합니다.

따라서 Runtime Model에는:

```text
TimeoutExecutor
    ──STARTS──>
Transaction

Transaction
    ──WRAPS──>
Dispatcher
```

관계까지 들어가야 합니다.

Reference 문서가 `DefaultFilter → ServicePreventionInterceptor → OnlineTransactionController → TCF/STF → OnlineTimeoutExecutor → TransactionDispatcher → TransactionHandler → Business Layer → ETF`를 Framework Runtime 기준으로 잡는 이유입니다.

---

# 4. `pdmg-service`에서 찾은 모델

여기가 **ServiceId 중심 Architecture Model을 가장 쉽게 이해할 수 있는 곳**입니다.

실제 프로그램들은:

```text
mgcoa5530
mgcoa8888
mgcoa9000
mgcoa9001
mgcoa9100
mgcoa9999
```

입니다.

실제 구조는:

```text
entry.handler
        ↓
application.facade
        ↓
application.service
        ↓
persistence.dao
        ↓
Mapper XML
        ↓
Table
```

입니다.

예를 들어 실제 `mgcoa9001`을 모델로 바꾸면:

```text
ServiceId
mgcoa9001S0
      │
      │ HANDLED_BY
      ▼
Handler
mgcoa9001Handler
      │
      │ CALLS
      ▼
Facade
mgcoa9001Facade
      │
      │ CALLS
      ▼
Service
mgcoa9001Service
      │
      │ USES
      ▼
DAO
mgcoa9001DAO
      │
      │ EXECUTES
      ▼
Mapper
mgcoa9001-ORA.xml
      │
      │ ACCESSES
      ▼
Table
TB_MG_TX_CONTROL
```

바로 **이것이 우리가 이야기했던 Architecture Model의 실제 예**입니다.

실제 Handler는:

```text
mgcoa9001S0
mgcoa9001C0
mgcoa9001U0
mgcoa9001D0
```

네 개 ServiceId를 지원합니다.

따라서 모델은:

```text
                 mgcoa9001Handler
                    ▲ ▲ ▲ ▲
                    │ │ │ │
         ┌──────────┘ │ │ └──────────┐
         │            │ │            │
mgcoa9001S0     mgcoa9001C0    mgcoa9001U0    mgcoa9001D0
```

처럼도 표현할 수 있습니다.

---

# 5. `pdmg-service`에서 발견한 Model Entity

따라서 `pdmg-service`에서는 최소한 다음을 모델로 만들어야 합니다.

```text
BusinessDomain
Program
ServiceId

Handler
Facade
Service
DTO
DAO
Mapper
SqlId
Table

TransactionPolicy
PagingPolicy
ValidationPolicy
```

그리고 Relation:

```text
BusinessDomain
   HAS_PROGRAM
        ↓
Program
   PROVIDES_SERVICE
        ↓
ServiceId
   HANDLED_BY
        ↓
Handler
   CALLS
        ↓
Facade
   CALLS
        ↓
Service
   USES
        ↓
DAO
   EXECUTES
        ↓
Mapper / SqlId
   ACCESSES
        ↓
Table
```

이게 `20-MODEL`에 들어가야 하는 가장 중요한 세로축입니다.

---

# 6. `pdmg-jwt`에서도 같은 모델이 발견됩니다

`pdmg-jwt`도 특수한 별도 프로그램이 아니라 상당 부분 같은 구조입니다.

실제 패키지는:

```text
nhnis.mg.jw.a

entry.handler
application.controller
application.facade
application.service
dto
persistence.dao
support
config
```

입니다.

실제 ServiceId도 확인됩니다.

```text
mgjwa1000C0
mgjwa1000C1
mgjwa1000U0
mgjwa1000D0
mgjwa1000D1

mgjwa1001S0
mgjwa1001D0

mgjwa1002S0
mgjwa1003S0

mgjwa1004S0
mgjwa1004U0
```

예를 들어:

```text
mgjwa1000C0
     ↓
mgjwa1000Handler
     ↓
mgjwa1000Facade
     ↓
mgjwa1000Service
     ↓
mgjwa1000DAO
```

입니다.

여기까지만 보면 `pdmg-service`와 같은 Business Application Model입니다.

그런데 JWT에는 추가적인 Security Model이 있습니다.

```text
mgjwa1000Service
       │
       ▼
JwtTokenIssuer
       │
       ├─ RSAPrivateKey
       ├─ RS256
       └─ Access Token
                │
                ▼
             JWT
```

그리고:

```text
Refresh Token
      ↓
JwtTokenStore
      ↓
Store / Rotate / Revoke
```

또:

```text
RSAPublicKey
     ↓
JWKSet
     ↓
JwkSetController
     ↓
GET /.well-known/jwks.json
```

이 존재합니다.

따라서 Security Model은:

```text
PrivateKey
    │
    │ SIGNS
    ▼
JWT

JWT
    │
    │ VERIFIED_BY
    ▼
PublicKey

PublicKey
    │
    │ EXPOSED_AS
    ▼
JWKS
```

가 됩니다.

Reference 설계에서도 `pdmg-jwt`를 `Authentication / Token Issue → Private Key Boundary → JWT → JWKS/Public Key → Token Store/Refresh/Revoke → Security Policy` 모델로 정의합니다.

---

# 7. 그러면 `pdmg-om` 모델은?

여기가 조금 다릅니다.

**현재 ZIP에는 `pdmg-om` 소스가 없습니다.**

따라서:

```text
pdmg-om AS-IS Model
=
UNKNOWN
```

으로 두어야 합니다.

대신 현재 실제 운영 기능은 다음처럼 분산되어 있습니다.

```text
                    Logical OM Model

                       pdmg-ui
                ┌────────┴────────┐
                ▼                 ▼
          /txcontrol           /rtdiag
                │                 │
                ▼                 ▼
          mgcoa9001*         mgcoa9100S0
                │                 │
                ▼                 ▼
          pdmg-service       pdmg-service
                │                 │
                ▼                 ▼
     TB_MG_TX_CONTROL       MgRuntimeMonitor
                │                 │
                └────────┬────────┘
                         ▼
                       pdmg-fw
                 Runtime / Control
```

즉 현재 **논리적 OM 모델**은 존재하지만 **물리적인 `pdmg-om` 모듈은 없습니다.**

---

# 8. 그래서 최종 PDMG Architecture Model은 이렇게 잡으면 됩니다

제가 실제 소스를 기준으로 뽑으면 최상위 모델은 이 구조가 가장 적절합니다.

```text
                         SYSTEM
                          PDMG
                            │
       ┌────────────────────┼─────────────────────┐
       │                    │                     │
       ▼                    ▼                     ▼
    pdmg-ui              pdmg-jwt             pdmg-service
       │                    │                     ▲
       │                    │                     │
       │                    │                  pdmg-fw
       │                    │                Runtime Library
       │                    │
       ▼                    ▼
    Screen              Authentication
       │                    │
       ▼                    ▼
 Transaction            JWT ServiceId
 Catalog                    │
       │                    ▼
       ▼                  Handler
   ServiceId                │
       │                    ▼
       │                  Facade
       │                    │
       └──────────────┐     ▼
                      │   Service
                      │     │
                      ▼     └─ TokenIssuer/Store
                 pdmg-service
                      │
                  ServiceId
                      │
                      ▼
                pdmg-fw TCF
                      │
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
                    Table
```

---

# 9. `20-MODEL/reference-baseline-draft.json`에는 결국 이런 내용이 들어갑니다

예를 들어 `mgcoa9001S0` 한 건만 모델화하면:

```json
{
  "serviceId": "mgcoa9001S0",

  "module": "pdmg-service",

  "business": {
    "group": "MG",
    "business": "CO",
    "subBusiness": "A",
    "programId": "mgcoa9001"
  },

  "execution": {
    "framework": {
      "filter": "DefaultFilter",
      "interceptor": "ServicePreventionInterceptor",
      "controller": "OnlineTransactionController",
      "tcf": "TcfFacade",
      "dispatcher": "TransactionDispatcher"
    },

    "business": {
      "handler": "mgcoa9001Handler",
      "facade": "mgcoa9001Facade",
      "service": "mgcoa9001Service",
      "dao": "mgcoa9001DAO",
      "mapper": "mgcoa9001-ORA.xml"
    }
  },

  "tables": ["TB_MG_TX_CONTROL"]
}
```

이것을 그림으로 보면 훨씬 간단합니다.

```text
mgcoa9001S0
     │
     ├─ UI에서 사용하는 거래
     │
     ▼
DefaultFilter
     ↓
Interceptor
     ↓
TCF
     ↓
Timeout / Transaction
     ↓
Dispatcher
     ↓
mgcoa9001Handler
     ↓
mgcoa9001Facade
     ↓
mgcoa9001Service
     ↓
mgcoa9001DAO
     ↓
mgcoa9001-ORA.xml
     ↓
TB_MG_TX_CONTROL
```

**바로 이 한 줄짜리 연결관계를 JSON으로 저장한 것이 Architecture Model입니다.**

### 따라서 PDMG에서 우선 만들어야 할 모델은 5개입니다

| 모델                   | 근거 모듈      | 핵심                                                 |
| ---------------------- | -------------- | ---------------------------------------------------- |
| `ui-integration-model` | `pdmg-ui`      | Screen → ServiceId                                   |
| `framework-model`      | `pdmg-fw`      | Filter → TCF → Timeout/TX → Dispatcher               |
| `application-model`    | `pdmg-service` | ServiceId → Handler → Facade → Service → DAO → Table |
| `jwt-security-model`   | `pdmg-jwt`     | 인증 → Token → Key → JWKS                            |
| `operation-model`      | 현재 분산      | 거래통제 → Runtime → Evidence                        |

즉 **`reference-baseline-draft.json` 하나에 모든 것을 뭉개기보다, 위 5개 모델을 먼저 만들고 마지막에 `pdmg-reference-model.json`이 이들을 통합하는 구조가 훨씬 이해하기 쉽고 관리하기도 좋습니다.**

다음 단계에서는 이 실제 소스를 기준으로 **`20-MODEL` 디렉터리에 들어갈 5개 JSON 모델을 실제 파일로 생성**해 드리는 것이 가장 자연스럽습니다.
