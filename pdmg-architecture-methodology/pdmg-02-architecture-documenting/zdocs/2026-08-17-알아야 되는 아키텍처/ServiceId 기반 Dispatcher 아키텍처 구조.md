현재 업로드된 `nsight-tcf-framework (2).zip`의 **NSIGHT_TCF 실제 소스**를 기준으로 보면, ServiceId 기반 Dispatcher는 다음 구조입니다. 프로젝트 기준에서도 Dispatcher는 **ServiceId를 Handler/Use Case로 연결하는 책임**을 가지며, ServiceId 중심 추적성을 핵심으로 관리합니다.

## 1. 전체 구조

```text
[화면 / Client]
      │
      │ HTTP POST
      │ StandardRequest
      ▼
┌──────────────────────────────┐
│ OnlineTransactionController  │
│                              │
│ /online                      │
│ /{businessCode}/online       │
└──────────────┬───────────────┘
               │
               │ tcf.process()
               ▼
┌──────────────────────────────┐
│             TCF              │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│             STF              │
│                              │
│ Header Validation            │
│ GUID / TraceId               │
│ Session                      │
│ Authentication               │
│ Authorization                │
│ 거래통제                     │
│ Timeout Policy               │
│ Idempotency                  │
│ Transaction Log Start        │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│ OnlineTransaction            │
│ TimeoutExecutor              │
└──────────────┬───────────────┘
               │
               ▼
     ★ 핵심 ServiceId Routing
               │
               ▼
┌──────────────────────────────┐
│ TransactionDispatcher        │
│                              │
│ serviceId                    │
│      ↓                       │
│ handlerMap.get(serviceId)    │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│ TransactionHandler           │
│                              │
│ ServiceId → Use Case 선택    │
└──────────────┬───────────────┘
               │
               ▼
            Facade
               │
               ▼
            Service
          ┌────┼────┐
          ▼    ▼    ▼
        Rule  DAO  Integration
               │
               ▼
             Mapper
               │
               ▼
               DB
               │
               ▼
┌──────────────────────────────┐
│             ETF              │
│ Tx Log End / Audit / Metric  │
│ 표준응답 생성                │
└──────────────┬───────────────┘
               ▼
        StandardResponse
```

핵심만 압축하면:

```text
ServiceId
   ↓
Dispatcher
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

입니다.

---

## 2. ServiceId가 왜 중요한가

기존 REST 방식은 대개 URL이 업무를 결정합니다.

```text
/customer/select
/customer/update
/product/select
```

NSIGHT 구조는 공통 URL을 사용할 수 있습니다.

```text
POST /online
```

대신 요청 전문 안의 `serviceId`가 **실행할 업무를 결정하는 논리적 주소**가 됩니다.

예를 들면:

```json
{
  "header": {
    "businessCode": "AV",
    "serviceId": "AV.AssetValuation.selectList"
  },
  "body": {
    "customerId": "C00001"
  }
}
```

그러면 실행 경로는:

```text
POST /online
       │
       ▼
serviceId =
AV.AssetValuation.selectList
       │
       ▼
TransactionDispatcher
       │
       ▼
AvAssetValuationHandler
       │
       ▼
AvAssetValuationFacade.selectList()
```

가 됩니다.

즉 URL이 아니라 **ServiceId가 비즈니스 거래의 Identity**가 되는 구조입니다.

---

# 3. 실제 `TransactionDispatcher` 구조

현재 소스:

```text
tcf-core
└─ com.nh.nsight.tcf.core.support.dispatch
   └─ TransactionDispatcher.java
```

중요한 내부 구조는 다음입니다.

```java
private final Map<String, TransactionHandler> handlerMap
        = new ConcurrentHashMap<>();
```

Dispatcher는 내부적으로 다음 Registry를 가지고 있습니다.

```text
handlerMap

┌───────────────────────────────────┬─────────────────────────────┐
│ ServiceId                         │ Handler                     │
├───────────────────────────────────┼─────────────────────────────┤
│ AV.AssetValuation.selectList      │ AvAssetValuationHandler     │
│ AV.CustomerContact.selectList     │ AvCustomerContactHandler    │
│ AV.CustomerContact.selectDetail   │ AvCustomerContactHandler    │
│ AV.Sample.inquiry                 │ AvSampleHandler             │
└───────────────────────────────────┴─────────────────────────────┘
```

따라서 실제 거래 시에는 복잡한 Reflection이나 Bean 검색을 다시 하지 않고:

```java
TransactionHandler handler = handlerMap.get(serviceId);
```

로 찾아냅니다.

이 부분이 Dispatcher 구조를 이해할 때 매우 중요합니다.

> **Dispatcher = ServiceId → Handler Registry**

라고 이해하면 됩니다.

---

# 4. Handler 등록은 언제 하는가

Spring이 애플리케이션을 시작하면서 모든 `TransactionHandler` 구현체를 찾아 Dispatcher 생성자에 주입합니다.

개념적으로:

```text
Spring Boot 시작
      │
      ▼
@Component Scan
      │
      ├─ AvAssetValuationHandler
      ├─ AvCustomerContactHandler
      ├─ AvSampleHandler
      └─ ...
      │
      ▼
List<TransactionHandler>
      │
      ▼
TransactionDispatcher
      │
      ▼
각 Handler.serviceIds() 호출
      │
      ▼
handlerMap 등록
```

실제 Dispatcher 생성자는:

```java
public TransactionDispatcher(List<TransactionHandler> handlers) {

    for (TransactionHandler handler : handlers) {

        Collection<String> serviceIds = handler.serviceIds();

        for (String serviceId : serviceIds) {

            handlerMap.put(serviceId, handler);
        }
    }
}
```

와 같은 구조입니다.

따라서 **Dispatcher 등록을 위해 별도 XML에 Handler 이름을 일일이 쓰는 구조가 아닙니다.**

---

# 5. 중복 ServiceId는 시작 단계에서 막는다

현재 소스에서 상당히 좋은 부분입니다.

```java
TransactionHandler previous =
        handlerMap.put(serviceId, handler);

if (previous != null) {
    throw new IllegalStateException(
        "Duplicate serviceId detected: " + serviceId
    );
}
```

즉 다음 상황을 허용하지 않습니다.

```text
Handler A
 └─ AV.AssetValuation.selectList

Handler B
 └─ AV.AssetValuation.selectList
```

이렇게 되면:

```text
Spring Boot Startup
       ↓
Dispatcher Registry 생성
       ↓
Duplicate ServiceId 발견
       ↓
IllegalStateException
       ↓
애플리케이션 기동 실패
```

가 됩니다.

이것은 중요한 Architecture Rule입니다.

```text
R-SERVICEID-UNIQUE

하나의 ServiceId는
Dispatcher Registry에서
하나의 Handler에만 매핑되어야 한다.
```

---

# 6. 실제 요청이 들어왔을 때 Dispatcher 동작

Dispatcher의 핵심 실행은 사실 매우 단순합니다.

```text
dispatch(request, context)
       │
       ▼
request.header.serviceId 추출
       │
       ▼
ServiceId 존재?
       │
   ┌───┴────┐
   │        │
  NO       YES
   │        │
INVALID     ▼
HEADER   handlerMap.get(serviceId)
            │
            ▼
        Handler 존재?
           │
       ┌───┴────┐
       │        │
      NO       YES
       │        │
SERVICE_NOT    ▼
_FOUND      handler.handle()
                │
                ▼
             doHandle()
```

실제 소스도 거의 그대로입니다.

```java
String serviceId =
    request.getHeader().getServiceId();

TransactionHandler handler =
    handlerMap.get(serviceId);

if (handler == null) {
    throw new BusinessException(
        ErrorCode.SERVICE_NOT_FOUND,
        "등록되지 않은 serviceId입니다: " + serviceId
    );
}

return handler.handle(request, context);
```

그래서 Dispatcher 자체는 업무 로직을 수행하지 않습니다.

---

# 7. `TransactionHandler`가 중요한 이유

현재 TCF는 두 가지 Handler 모델을 지원합니다.

### ① ServiceId 하나 = Handler 하나

```text
ServiceId
SV.Customer.selectSummary
       │
       ▼
CustomerSelectSummaryHandler
```

Handler:

```java
@Override
public String serviceId() {
    return "SV.Customer.selectSummary";
}
```

### ② 여러 ServiceId = 도메인 Handler 하나

현재 AV 구현은 이 형태에 가깝습니다.

```text
AV.AssetValuation.selectList ─┐
AV.AssetValuation.selectDetail│
AV.AssetValuation.update      │
                              ▼
                  AvAssetValuationHandler
```

인터페이스가:

```java
default Collection<String> serviceIds()
```

를 제공하기 때문에 하나의 Handler가 여러 ServiceId를 등록할 수 있습니다.

그리고 Handler 안에서 다시:

```java
switch (serviceId)
```

를 사용해 Facade의 Use Case로 연결할 수 있습니다.

---

# 8. 현재 실제 Handler 예

실제 `av-service`에는 다음 코드가 있습니다.

```text
av-service
└─ entry
   └─ handler
      └─ AvAssetValuationHandler
```

구조를 단순화하면:

```java
@Component
public class AvAssetValuationHandler
        implements TransactionHandler {

    private static final String SELECT_LIST =
        "AV.AssetValuation.selectList";

    private final AvAssetValuationFacade facade;

    @Override
    public Collection<String> serviceIds() {
        return List.of(SELECT_LIST);
    }

    @Override
    public Object doHandle(
            StandardRequest<Map<String,Object>> request,
            TransactionContext context) {

        String serviceId =
            context.getHeader().getServiceId();

        return switch (serviceId) {

            case SELECT_LIST ->
                facade.selectList(
                    request.getBody(),
                    context);

            default ->
                throw new BusinessException(...);
        };
    }
}
```

따라서 역할은 명확합니다.

```text
Dispatcher
   │
   │ ServiceId로
   ▼
Handler 선택
   │
   │ Handler 내부에서
   ▼
Use Case 선택
   │
   ▼
Facade Method
```

---

# 9. Dispatcher와 Handler의 책임을 섞으면 안 된다

이 부분을 꼭 구분해야 합니다.

| 계층           | 책임                          | 하면 안 되는 것   |
| -------------- | ----------------------------- | ----------------- |
| Controller     | HTTP 진입                     | 업무 분기         |
| TCF            | 전체 거래 Lifecycle           | 업무 SQL 실행     |
| STF            | 공통 선처리                   | 업무 처리         |
| **Dispatcher** | **ServiceId → Handler 검색**  | 업무 로직         |
| **Handler**    | **ServiceId → Use Case 연결** | DAO 직접 호출     |
| Facade         | Use Case 조립/경계            | HTTP 처리         |
| Service        | 업무 처리                     | ServiceId Routing |
| Rule           | 업무규칙                      | Dispatcher 역할   |
| DAO            | DB 접근                       | 업무 Routing      |
| Mapper         | SQL                           | 업무 흐름 제어    |

따라서 다음은 좋지 않습니다.

```text
Dispatcher
   │
   ├─ if ServiceId == A → DAO
   ├─ if ServiceId == B → SQL
   └─ if ServiceId == C → External API
```

Dispatcher는 **라우터일 뿐입니다.**

좋은 구조는:

```text
Dispatcher
   ↓
Handler
   ↓
Facade
   ↓
Service
```

입니다.

---

# 10. Timeout과 Dispatcher의 관계

현재 실제 TCF 코드상 실행 순서는 이것도 중요합니다.

```text
TCF.process()
      │
      ▼
STF.preProcess()
      │
      ▼
OnlineTransactionTimeoutExecutor.execute(
      │
      └─ Dispatcher.dispatch()
)
```

즉:

```text
STF
 │
 ▼
Timeout Executor
 │
 │ Worker Thread 가능
 ▼
Dispatcher
 │
 ▼
Handler
 │
 ▼
Facade
```

입니다.

따라서 **Timeout은 Dispatcher보다 바깥쪽에서 업무 실행 전체를 감쌉니다.**

현재 `OnlineTransactionTimeoutExecutor`는 Timeout 기능이 활성화되면:

```text
HTTP Thread
     │
     ▼
ExecutorService.submit()
     │
     ▼
Worker Thread
     │
     ▼
Dispatcher
     ▼
Handler
     ▼
Facade
     ▼
Service
```

형태가 됩니다.

이 부분은 ServiceId Dispatcher를 단순 Router만으로 보지 않고 **전체 온라인 거래 실행 구조 안에서 이해해야 하는 이유**입니다.

---

# 11. 전체 시퀀스로 보면

```text
Browser        Controller       TCF/STF       Timeout       Dispatcher       Handler       Facade       Service
   │               │               │              │              │              │             │             │
   │ POST /online  │               │              │              │              │             │             │
   │──────────────>│               │              │              │              │             │             │
   │               │ process()     │              │              │              │             │             │
   │               │──────────────>│              │              │              │             │             │
   │               │               │ validation   │              │              │             │             │
   │               │               │──────────────│              │              │             │             │
   │               │               │ execute()    │              │              │             │             │
   │               │               │─────────────>│              │              │             │             │
   │               │               │              │ dispatch()   │              │             │             │
   │               │               │              │─────────────>│              │             │             │
   │               │               │              │              │ get(serviceId)             │             │
   │               │               │              │              │──────┐       │             │             │
   │               │               │              │              │<─────┘       │             │             │
   │               │               │              │              │ handle()     │             │             │
   │               │               │              │              │─────────────>│             │             │
   │               │               │              │              │              │ facade.xxx()│             │
   │               │               │              │              │              │────────────>│             │
   │               │               │              │              │              │             │ service.xxx │
   │               │               │              │              │              │             │────────────>│
```

---

# 12. ServiceId 기반 Dispatcher의 가장 큰 장점

이 구조를 쓰는 이유는 단순히 URL을 줄이기 위해서가 아닙니다.

ServiceId를 중심으로 다음 모든 정책을 연결할 수 있기 때문입니다.

```text
                    ServiceId
                       │
        ┌──────────────┼───────────────┐
        │              │               │
        ▼              ▼               ▼
     Handler        Timeout         거래통제
        │
        ▼
     Facade
        │
        ▼
     Service
        │
   ┌────┼────┐
   ▼    ▼    ▼
 Rule  DAO  EAI
        │
        ▼
      Mapper
        │
        ▼
       SQL
        │
        ▼
      Table

ServiceId
   ├─ 권한
   ├─ Timeout
   ├─ 거래통제
   ├─ 중복통제
   ├─ 거래로그
   ├─ Audit
   ├─ Metric
   ├─ TestCase
   ├─ OM Catalog
   └─ Runtime Evidence
```

즉 **ServiceId는 단순 Dispatcher Key가 아니라 NSIGHT 온라인 아키텍처의 중심 Trace Key**입니다. 이 방향은 프로젝트 연속성 기준에서도 `요구사항 → 화면 → ServiceId → Handler → Facade → Service → DAO → Mapper/SQL → Table → OM → 테스트 → 운영로그`의 추적성으로 정의돼 있습니다.

---

## 13. 가장 쉽게 이해하면

초보자 관점에서는 이렇게 보면 됩니다.

```text
ServiceId = 업무 전화번호
Dispatcher = 교환원
Handler    = 담당 창구
Facade     = 업무 책임자
Service    = 실제 업무 담당자
DAO/Mapper = DB 담당자
```

예를 들어:

```text
"AV.AssetValuation.selectList"
        │
        ▼
     Dispatcher
  "누가 담당하지?"
        │
        ▼
AvAssetValuationHandler
  "자산평가 조회군이군"
        │
        ▼
AvAssetValuationFacade.selectList()
        │
        ▼
AvAssetValuationService.selectList()
        │
        ▼
DAO → Mapper → DB
```

가 되는 것입니다.

### 핵심 결론

**NSIGHT의 ServiceId 기반 Dispatcher 아키텍처는**

```text
공통 Endpoint
    +
표준전문
    +
ServiceId
    +
Dispatcher Registry
    +
Handler
```

를 이용해 **HTTP 진입 구조와 실제 업무 구현을 분리하는 아키텍처**입니다.

그리고 가장 중요한 설계 원칙은:

```text
Dispatcher는
"무엇을 처리할지" 판단하지 않는다.

Dispatcher는
"누가 처리할지"만 찾는다.

Handler가
ServiceId를 실제 Use Case로 연결한다.
```

입니다.

이 구조를 이해하면 다음으로 **`ServiceId → Dispatcher → Handler → Facade`가 Spring 기동 시 어떻게 자동 등록되고 실제 요청 1건이 어떻게 흘러가는지 소스 단위로 추적**하는 것이 가장 이해하기 쉽습니다.
