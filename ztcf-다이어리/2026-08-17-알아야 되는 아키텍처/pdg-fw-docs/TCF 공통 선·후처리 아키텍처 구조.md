## TCF 공통 선·후처리 아키텍처 구조

현재 프로젝트의 **PDMG Reference Source (`pdmg-fw`, `pdmg-service`) 기준**으로 보면, TCF의 공통 선·후처리는 한 덩어리가 아니라 **3개 계층**으로 분리해서 이해하는 것이 정확합니다. PDMG Reference 자체도 `Filter → Interceptor → OnlineTransactionController → STF → Timeout Executor → Dispatcher → Handler → ETF`를 Framework Runtime 기본 모델로 잡고 있습니다.

```text
┌────────────────────────────────────────────────────────────┐
│ ① SYSTEM 공통 선·후처리                                    │
│                                                            │
│ DefaultFilter                                              │
│    ↓                                                       │
│ ServicePreventionInterceptor                               │
│                                                            │
│ HTTP / 인증 / GUID / ServiceContext / 전문로그 / ImageLog │
└──────────────────────────────┬─────────────────────────────┘
                               │
                               ▼
                    OnlineTransactionController
                               │
                               ▼
┌────────────────────────────────────────────────────────────┐
│ ② TCF 거래 공통 선·후처리                                  │
│                                                            │
│ TcfFacade                                                  │
│    │                                                       │
│    ├─ STF.preProcess()        ← 거래 선처리                │
│    │                                                       │
│    ├─ Timeout Executor                                    │
│    │      ↓                                                │
│    │   Dispatcher                                         │
│    │      ↓                                                │
│    │   Handler                                            │
│    │                                                       │
│    └─ ETF.postProcess()       ← 거래 후처리                │
└──────────────────────────────┬─────────────────────────────┘
                               │
                               ▼
┌────────────────────────────────────────────────────────────┐
│ ③ BUSINESS 공통 선·후처리                                  │
│                                                            │
│ Handler                                                    │
│   ↓                                                        │
│ Facade                                                     │
│   ↓                                                        │
│ BizPrePostAspect                                           │
│   ├─ @Before       업무 선처리                             │
│   ▼                                                        │
│ Service                                                    │
│   ├─ Rule                                                  │
│   └─ DAO → Mapper → DB                                    │
│   ▲                                                        │
│   └─ @AfterReturning 업무 후처리                          │
└────────────────────────────────────────────────────────────┘
```

PDMG 기준 업무 구조에서도 `Handler → Facade → BizPrePostAspect → Service → DAO → Mapper/SQL → DB`를 별도 Application Model로 관리하고 있습니다.

---

# 1. 전체 실행 순서

현재 소스를 기준으로 가장 정확하게 펼치면 다음과 같습니다.

```text
Client
  │
  │ HTTP + JSON 전문
  ▼
DefaultFilter
  │
  ├─ Request Body 캐싱
  ├─ hdr_nhnis 추출
  ├─ GUID 준비
  ├─ ServiceContext 생성
  └─ MDC 구성
  │
  ▼
ServicePreventionInterceptor.preHandle()
  │
  ├─ 시스템 선처리 START
  ├─ JWT Access Token 검증
  ├─ GUID 보정
  ├─ ServiceId / IP / User 보정
  ├─ 요청전문 로그
  └─ Pre ImageLog
  │
  ▼
OnlineTransactionController
  │
  ├─ ServiceId 결정
  ├─ Client IP 보정
  └─ dto 추출
  │
  ▼
TcfFacade.process()
  │
  ├─ TransactionContext 생성
  ├─ ActiveTransactionRegistry.begin()
  │
  ▼
STF.preProcess()
  │
  └─ 거래통제 MgTxControlService.check()
  │
  ▼
OnlineTimeoutExecutor
  │
  ├─ Timeout OFF → 현재 Thread
  │
  └─ Timeout ON  → Worker Thread
  │
  ▼
TransactionDispatcher
  │
  │ serviceId
  ▼
TransactionHandler
  │
  ▼
Business Facade
  │
  ▼
BizPrePostAspect @Before
  │
  ▼
Service
  │
  ├─ Rule
  └─ DAO
       ↓
     Mapper
       ↓
       DB
  │
  ▼
BizPrePostAspect @AfterReturning
  │
  ▼
Handler Return
  │
  ▼
TimeoutExecutor Return
  │
  ▼
TcfFacade finally
  │
  ├─ ActiveTransactionRegistry.end()
  └─ ETF.postProcess()
       └─ Timeout Interval 최종 점검
  │
  ▼
시스템 응답 후처리
  │
  ├─ Response 전문
  ├─ Post ImageLog
  ├─ Exception ImageLog
  └─ ServiceContext 정리
  │
  ▼
Client
```

이 구조가 중요한 이유는 **“선처리”라는 말 하나 아래 서로 다른 책임의 코드가 세 군데 존재하기 때문**입니다.

---

# 2. ① 시스템 공통 선처리

### `DefaultFilter`

이 계층은 TCF 업무를 실행하기 전 **HTTP 요청 자체를 TCF가 사용할 수 있는 형태로 만드는 영역**입니다.

현재 책임은 대략 다음입니다.

| 기능         | 책임                                    |
| ------------ | --------------------------------------- |
| Request Body | Body를 다시 읽을 수 있도록 캐싱         |
| 공통전문     | `hdr_nhnis` 파싱                        |
| GUID         | 요청 추적정보 준비                      |
| Context      | `ServiceContext` 생성                   |
| MDC          | GUID, UserId, ServiceId 등 로그 Context |
| Local 대응   | Local 환경 Header 보정                  |

즉 Filter는 다음 위치입니다.

```text
HTTP Request
    ↓
[DefaultFilter]
    ↓
TCF가 사용할 수 있는
ServiceContext 준비
```

여기에 **거래통제나 업무 Rule을 넣으면 안 됩니다.**

---

# 3. `ServicePreventionInterceptor` 시스템 선처리

Filter 다음에는 MVC Interceptor가 있습니다.

현재 실제 `preHandle()`에서 중요한 처리는 다음 순서입니다.

```text
ServicePreventionInterceptor.preHandle
        │
        ├─ 시스템 선처리 시작
        │
        ├─ JWT 검증
        │     ├ Authorization Bearer 확인
        │     ├ JWT validate
        │     ├ Access Token 여부
        │     └ ssoId → user Context
        │
        ├─ ServiceContext 확인
        │
        ├─ GUID 보정/생성
        │
        ├─ ServiceId/IP/User Header 보정
        │
        ├─ 요청전문 Logging
        │
        ├─ Pre ImageLog
        │
        └─ 시스템 선처리 종료
```

여기서 중요한 경계는:

```text
JWT 검증
        = 시스템 공통 선처리

거래통제
        ≠ Interceptor
        = STF
```

입니다.

이 분리가 현재 PDMG TCF에서 잘 되어 있습니다.

---

# 4. ② TCF 선처리 — STF

현재 `TcfFacade`에서는 명확하게 다음 순서입니다.

```java
activeTransactionRegistry.begin(context);

stf.preProcess(context);

onlineTimeoutExecutor.execute(
    () -> dispatcher.dispatch(...)
);
```

즉 STF는 **Handler 실행보다 앞**이고, 현재 구조에서는 **Timeout Executor보다도 앞**입니다.

```text
Controller
   ↓
TcfFacade
   ↓
[ STF ]
   ↓
Timeout Executor
   ↓
Dispatcher
   ↓
Handler
```

현재 `stf.preProcess()`의 핵심 책임은:

```text
TransactionContext
       ↓
Header / ServiceId
       ↓
MgTxControlService.check()
       ↓
TB_MG_TX_CONTROL 기반 거래통제
       │
       ├─ 허용 → 계속
       └─ 차단 → 업무 실행 금지
```

입니다.

따라서 현재 PDMG STF의 핵심 의미는 **“업무 Handler에 진입하기 전에 거래 자체를 실행해도 되는지를 결정한다”**입니다.

---

# 5. STF에서 해야 할 일 / 하지 말아야 할 일

STF는 장기적으로 아래 정책들의 자연스러운 위치입니다.

| 기능                  | STF 적합성    |
| --------------------- | ------------- |
| 거래통제              | **매우 적합** |
| ServiceId 유효성      | 적합          |
| Timeout 정책 결정     | 적합          |
| 중복거래 통제         | 적합          |
| 거래 시작 로그        | 적합          |
| 서비스 사용 가능 여부 | 적합          |
| 업무 데이터 변경      | **부적합**    |
| 업무 SQL              | **부적합**    |
| 고객 업무 Rule        | **부적합**    |
| 화면별 업무 분기      | **부적합**    |

즉:

```text
STF 질문

"이 거래를 시작해도 되는가?"
```

에 집중해야 합니다.

---

# 6. STF가 중요한 아키텍처 경계

현재 구조는 STF가 Timeout Executor **밖**에 있습니다.

```text
TcfFacade
 │
 ├─ STF                 ← 현재 Thread
 │
 ▼
OnlineTimeoutExecutor
 │
 └─ Worker Thread
       ↓
    Dispatcher
       ↓
    업무처리
```

따라서 의미상:

```text
STF 처리시간
     +
Worker 처리시간
```

과 실제 Worker Timeout 예산은 완전히 같은 개념이 아닐 수 있습니다.

이 위치 선택은 TCF 아키텍처에서 중요한 판단사항입니다. 기존 TCF 설계에서도 STF를 Executor 밖/안/Transaction 내부 어디에 둘지에 따라 **Timeout 예산과 Transaction 참여가 달라진다**고 분석되어 있습니다.

현재 PDMG는 **빠른 거래 차단을 Worker 제출보다 먼저 수행하는 방식**이라고 이해하면 됩니다.

---

# 7. Dispatcher 이후는 업무 실행 영역

STF를 통과하면 TCF는 `ServiceId`를 가지고 Handler를 찾습니다.

```text
STF PASS
   ↓
Timeout Executor
   ↓
TransactionDispatcher
   │
   │ serviceId
   ▼
TransactionHandler
```

여기서부터가 본격적인 업무 Use Case 실행입니다.

```text
ServiceId
   ↓
Handler
   ↓
Facade
   ↓
Service
   ├─ Rule
   └─ DAO
        ↓
      Mapper
        ↓
       SQL
        ↓
        DB
```

ServiceId를 중심으로 Handler부터 DB까지 추적해야 한다는 기준도 현재 Architecture Baseline의 핵심입니다.

---

# 8. ③ 업무 공통 선처리 — `BizPrePostAspect`

TCF의 STF와 **업무 선처리는 완전히 다른 개념**입니다.

현재 `pdmg-service`의 `BizPrePostAspect`는 Service 메서드를 Pointcut으로 잡습니다.

```text
Handler
   ↓
Facade
   ↓
┌─────────────────────────┐
│ BizPrePostAspect        │
│                         │
│ @Before                 │
│ 업무 선처리             │
└──────────┬──────────────┘
           ▼
        Service
           │
           ▼
        DAO / DB
           │
           ▼
┌─────────────────────────┐
│ @AfterReturning         │
│ 업무 후처리             │
└─────────────────────────┘
```

현재 업무 선처리에서는 대표적으로:

```text
GUID
ServiceId
호출 Service Method
BRC
업무처리 시작 로그
```

등을 남깁니다.

업무 후처리에서는:

```text
업무 처리 종료
업무 후처리 시작
응답 DTO
업무 후처리 종료
```

등을 처리합니다.

---

# 9. STF와 BizPrePostAspect 차이

이 구분을 반드시 기억하는 것이 좋습니다.

| 구분             | STF                  | BizPrePostAspect           |
| ---------------- | -------------------- | -------------------------- |
| 영역             | TCF Framework        | Business Application       |
| 실행 위치        | Handler 이전         | Service 전후               |
| 기준             | 거래/ServiceId       | 실제 업무 메서드           |
| 목적             | 거래 실행 가능 여부  | 업무 공통 처리             |
| 대표 기능        | 거래통제             | 업무 로그                  |
| 업무 DML 전 실행 | 예                   | 예                         |
| TCF OFF          | 기본적으로 우회 가능 | Service 호출이면 적용 가능 |
| Framework 책임   | O                    | X                          |
| Business 책임    | X                    | O                          |

한 문장으로 하면:

```text
STF
= "이 거래를 실행해도 되는가?"

BizPre
= "이 업무를 시작하기 전에 무엇을 해야 하는가?"
```

입니다.

---

# 10. ② TCF 후처리 — ETF

업무가 끝나면 현재 `TcfFacade`의 `finally`에서 ETF가 수행됩니다.

```java
finally {
    activeTransactionRegistry.end(context);

    etf.postProcess(context);
}
```

따라서 **성공했을 때만 ETF가 호출되는 것이 아닙니다.**

```text
정상
  ───────┐
업무예외  ├──→ ETF
시스템예외 ┘
```

이 구조는 종료 처리를 `finally`에 둔 것이므로 공통 후처리 관점에서는 좋은 특성입니다.

---

# 11. 현재 ETF가 하는 일

현재 PDMG `etf` 구현은 비교적 단순합니다.

```text
ETF.postProcess()
      ↓
checkTimeoutInterval()
      │
      ├─ ServiceId별 Timeout 조회
      ├─ TransactionContext 시작시간 조회
      ├─ elapsedMs 계산
      │
      └─ elapsed > timeout
              ↓
       OnlineTimeoutException
```

즉 현재 ETF의 주 책임은 **거래 종료 시점의 Timeout Interval 확인**입니다.

---

# 12. ETF에서 장기적으로 관리하면 좋은 영역

ETF의 성격은 다음과 같습니다.

```text
"거래가 끝났는데,
공통적으로 무엇을 마무리해야 하는가?"
```

따라서 다음 영역이 적합합니다.

| 기능                  | ETF 적합성 |
| --------------------- | ---------- |
| 거래 종료 상태 기록   | 적합       |
| Runtime Metric        | 적합       |
| Audit                 | 적합       |
| Idempotency 상태 마감 | 적합       |
| 공통 결과 상태        | 적합       |
| 거래 소요시간         | 적합       |
| Runtime Evidence      | 적합       |
| 업무 데이터 Commit    | 부적합     |
| 업무 Rule             | 부적합     |

NSIGHT TCF의 보다 확장된 구조에서는 ETF 책임을 `Transaction Log End → Audit → Metric → Response Standardization`까지 두는 모델도 정의되어 있습니다.

---

# 13. 그런데 현재 ETF에는 중요한 주의점이 있습니다

현재 코드는:

```text
Dispatcher 업무 완료
      ↓
OnlineTimeoutExecutor 종료
      ↓
activeTransactionRegistry.end()
      ↓
ETF
      ↓
Timeout Interval 검사
```

입니다.

따라서 ETF가 이 시점에:

```text
"Timeout을 넘었다!"
```

라고 판단한다고 해도, **이미 Worker 안의 DB Transaction이 Commit된 뒤일 가능성**을 반드시 고려해야 합니다.

즉:

```text
DB COMMIT 완료

     ↓

ETF

     ↓

TIMEOUT 발견
```

이라면 ETF 예외로 앞선 Commit을 되돌릴 수 없습니다.

기존 Timeout 개선 설계도 그래서 Client ETF와 Worker Finalizer를 구분해야 한다고 제안합니다.

---

# 14. 권장 책임 구조

TCF 선·후처리는 최종적으로 다음처럼 정리하는 것이 가장 안정적입니다.

```text
                    HTTP REQUEST
                         │
                         ▼
             ┌──────────────────────┐
             │ SYSTEM PRE           │
             │                      │
             │ Filter               │
             │ Interceptor          │
             │ JWT / Context / GUID │
             │ 전문 / ImageLog      │
             └──────────┬───────────┘
                        ▼
                  Controller
                        │
                        ▼
             ┌──────────────────────┐
             │ TCF STF              │
             │                      │
             │ 거래통제             │
             │ Timeout 정책         │
             │ 중복거래             │
             │ 서비스 상태          │
             └──────────┬───────────┘
                        ▼
                 Execution
                        │
                  Timeout
                        │
                  Dispatcher
                        │
                    Handler
                        │
                    Facade
                        │
             ┌──────────────────────┐
             │ BUSINESS PRE         │
             │                      │
             │ BizPrePostAspect     │
             └──────────┬───────────┘
                        ▼
                     Service
                   ┌────┴────┐
                   ▼         ▼
                  Rule       DAO
                              │
                            Mapper
                              │
                              DB
                        │
             ┌──────────▼───────────┐
             │ BUSINESS POST        │
             │ 응답/업무 로그        │
             └──────────┬───────────┘
                        ▼
                     Handler
                        │
                        ▼
             ┌──────────────────────┐
             │ TCF ETF              │
             │                      │
             │ 거래 종료            │
             │ Metric / Evidence    │
             │ 공통 상태 마감       │
             └──────────┬───────────┘
                        ▼
             ┌──────────────────────┐
             │ SYSTEM POST          │
             │                      │
             │ 응답전문             │
             │ ImageLog / Exception│
             │ Context Cleanup      │
             └──────────┬───────────┘
                        ▼
                  HTTP RESPONSE
```

---

## 핵심 아키텍처 판단

TCF 공통 선·후처리는 **하나의 거대한 Interceptor로 합치는 구조보다 책임별로 분리하는 것이 맞습니다.**

```text
SYSTEM PRE/POST
= HTTP·보안·Context·전문

TCF STF/ETF
= 거래 실행 생명주기·정책

BUSINESS PRE/POST
= 업무 Service 공통처리
```

그리고 현재 PDMG 소스 기준으로 가장 중요한 실행선은 다음 한 줄입니다.

```text
DefaultFilter
→ ServicePreventionInterceptor
→ OnlineTransactionController
→ TcfFacade
→ STF
→ OnlineTimeoutExecutor
→ Dispatcher
→ Handler
→ Facade
→ BizPre
→ Service/DAO/Mapper
→ BizPost
→ ETF
→ System Post
```

**이 구조를 TCF 공통 선·후처리의 기준 아키텍처로 잡는 것이 가장 이해하기 쉽고, 향후 거래통제·JWT·Timeout·Transaction·로그 기능을 어디에 넣을지 판단하는 기준점도 됩니다.**
