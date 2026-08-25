# Service 아키텍처 구조

현재 **PDMG 실제 소스 기준**에서 Service는 `Handler → Facade → Service → DAO → Mapper` 흐름 가운데 **실제 업무 로직을 수행하는 중심 계층**입니다. 현재 컴포넌트 기준에서도 Service의 책임을 **업무 절차·검증·페이징·업무 규칙·DAO 호출**로 정의하고 있습니다.

가장 중요한 원칙은 다음입니다.

> **Facade는 “하나의 거래/Use Case 경계”이고, Service는 “그 거래에서 실제 업무를 수행하는 계층”입니다.**

---

## 1. Service 전체 Big Picture

```text
                      PDMG SERVICE ARCHITECTURE

┌──────────────────────────────────────────────────────────────┐
│                    pdmg-fw / TCF                             │
│                                                              │
│  OnlineTransactionController                                 │
│             ↓                                                │
│          TcfFacade                                           │
│             ↓                                                │
│            STF                                               │
│             ↓                                                │
│      Timeout Executor                                        │
│             ↓                                                │
│        Dispatcher                                            │
│             │ ServiceId                                      │
└─────────────┼────────────────────────────────────────────────┘
              ▼
┌──────────────────────────────────────────────────────────────┐
│                    BUSINESS ENTRY                            │
│                                                              │
│ Handler                                                      │
│   │                                                          │
│   │ ServiceId → Use Case                                     │
│   ▼                                                          │
│ Facade                                                       │
│   │                                                          │
│   ├─ Request DTO 변환                                        │
│   ├─ Use Case 경계                                           │
│   ├─ @Transactional                                          │
│   └─ Service 호출                                            │
│   │                                                          │
│   ▼                                                          │
├──────────────────────────────────────────────────────────────┤
│                  ★ SERVICE LAYER ★                           │
│                                                              │
│ Service                                                      │
│   │                                                          │
│   ├─ ① 입력 Validation                                      │
│   ├─ ② 업무 상태 확인                                       │
│   ├─ ③ 업무 Rule 실행                                       │
│   ├─ ④ 업무 처리 순서 결정                                  │
│   ├─ ⑤ Paging 정책                                          │
│   ├─ ⑥ DAO 호출                                             │
│   ├─ ⑦ 외부 Client 호출                                     │
│   └─ ⑧ Response DTO 구성                                    │
│                                                              │
│          ┌────────────┼──────────────┐                        │
│          ▼            ▼              ▼                        │
│       [Rule]*        DAO          Client                      │
│          │            │              │                        │
│          │            ▼              ▼                        │
│          │          Mapper        HTTP/EAI                    │
│          │            │                                       │
│          │            ▼                                       │
│          │            DB                                      │
│          │                                                    │
│          └── *현재 PDMG AS-IS에는 독립 Rule 패키지 없음      │
└──────────────────────────────────────────────────────────────┘
```

현재 PDMG에서는 `application.service` 아래 실제 Service들이 존재하고, Service가 업무 처리의 중심 역할을 합니다.

---

# 2. Service의 정확한 위치

현재 AS-IS 패키지는 다음입니다.

```text
pdmg-service
└─ src/main/java
   └─ nhnis
      └─ mg
         └─ co
            └─ a
               ├─ entry
               │   ├─ handler
               │   └─ aspect
               │
               ├─ application
               │   ├─ controller
               │   ├─ facade
               │   └─ service        ★
               │       ├─ mgcoa5530Service
               │       ├─ mgcoa8888Service
               │       ├─ mgcoa9000Service
               │       ├─ mgcoa9001Service
               │       └─ mgcoa9100Service
               │
               ├─ dto
               │
               └─ persistence
                   └─ dao
```

즉 패키지 표준은:

```text
nhnis.mg.[업무].[세부업무].application.service
```

입니다. 현재 컴포넌트 정의에서도 `application.service`를 **업무 절차·검증·DAO 호출을 담당하는 계층**으로 명시합니다.

---

# 3. Service와 다른 계층의 차이

이 구분이 가장 중요합니다.

| 계층        | 핵심 질문                           | 책임                   |
| ----------- | ----------------------------------- | ---------------------- |
| Handler     | 어떤 거래인가?                      | ServiceId → Facade     |
| Facade      | 이 거래의 경계는 어디인가?          | Use Case, TX, DTO 변환 |
| **Service** | **실제로 무엇을 해야 하는가?**      | **업무 로직**          |
| Rule        | 업무 조건이 맞는가?                 | 복잡한 순수 업무규칙   |
| DAO         | 데이터를 어떻게 읽고 쓸 것인가?     | DB 접근 계약           |
| Mapper      | 어떤 SQL인가?                       | SQL                    |
| Client      | 외부 시스템을 어떻게 호출할 것인가? | HTTP/EAI               |

한 줄로 표현하면:

```text
Handler
= 거래 선택

Facade
= 거래 경계

Service
= 업무 수행

DAO
= DB 접근

Mapper
= SQL 실행
```

현재 프로젝트 문서도 `Facade = Use Case 조립`, `Service = 실제 Business Logic`으로 명확히 구분합니다.

---

# 4. ServiceId와 Service의 관계

예를 들어:

```text
ServiceId
mgcoa9001S0
```

이라는 거래가 있다고 하면 전체 추적은:

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
     │
     ▼
mgcoa9001DAO
     │
     ▼
mgcoa9001-ORA.xml
     │
     ▼
SQL / Table
```

이 됩니다.

ServiceId는 단순 호출 코드가 아니라 Handler, Timeout, 거래통제, Service, DAO, SQL, 테스트 및 Runtime Evidence까지 연결할 수 있는 Architecture Trace Key입니다.

---

# 5. Service가 해야 하는 일

## ① 입력 Validation

Service는 현재 PDMG에서 업무 입력 검증의 핵심 위치입니다.

```text
Service
   │
   ├─ null?
   ├─ blank?
   ├─ 코드값 유효?
   ├─ 범위 정상?
   ├─ 데이터 존재?
   ├─ 중복?
   └─ 현재 상태에서 처리 가능?
```

예:

```text
고객번호 없음
       ↓
Service Validation
       ↓
BizException

거래통제 값이 Y/N 아님
       ↓
Service Validation
       ↓
BizException
```

현재 Service는 **검증·업무규칙·DAO 호출**을 담당하도록 정의되어 있습니다.

---

# 6. 입력 검증과 업무 검증을 구분한다

Service 내부 Validation도 두 가지로 나누는 것이 좋습니다.

```text
Service
  │
  ├─ Input Validation
  │    ├─ 필수값
  │    ├─ 형식
  │    ├─ 길이
  │    └─ 허용값
  │
  └─ Business Validation
       ├─ 고객 존재 여부
       ├─ 중복 여부
       ├─ 현재 상태
       ├─ 권한/업무조건
       └─ 처리 가능 여부
```

예를 들어:

```text
blockYn = "ABC"
        ↓
Input Validation 오류


blockYn = "Y"
하지만 이미 동일 통제정보 존재
        ↓
Business Validation 오류
```

입니다.

---

# 7. Service는 업무 처리 순서를 결정한다

Service의 가장 중요한 역할입니다.

예를 들어 등록 업무라면:

```text
Service
   │
   ├─ 1. 입력 검증
   │
   ├─ 2. 기존 데이터 조회
   │
   ├─ 3. 중복 여부 확인
   │
   ├─ 4. 업무 Rule 판단
   │
   ├─ 5. 등록 데이터 구성
   │
   ├─ 6. DAO INSERT
   │
   ├─ 7. 필요 시 후속 조회
   │
   └─ 8. 결과 DTO 반환
```

즉 DAO가 이 순서를 결정해서는 안 됩니다.

```text
DAO

중복인가?              X
등록 가능한 상태인가?   X
다음 업무를 실행할까?   X

SQL 실행                O
```

---

# 8. Paging도 Service 정책이다

현재 Paging 구조도 Service의 책임을 명확히 보여줍니다.

```text
Request
pageNo = 2
pageSize = 20

       ↓

Service

① pageNo 보정
② pageSize 제한
③ offset 계산
④ COUNT 호출
⑤ LIST 호출
⑥ totalPages 계산
⑦ DTOout 구성

       ↓

DAO / Mapper

COUNT SQL
PAGE SQL
```

즉:

```text
Paging Policy
     =
   Service

Paging SQL
     =
 Mapper / DB
```

입니다.

Service는 정책을 결정하고 DAO/Mapper는 DB 처리를 담당합니다.

---

# 9. Service → DAO 구조

현재 PDMG DAO는 별도 DAO 구현 클래스가 아니라 MyBatis Mapper Interface 역할까지 함께 수행합니다.

따라서:

```text
Service
    ↓
DAO Interface
    ↓
MyBatis
    ↓
Mapper XML
    ↓
SQL
    ↓
DB
```

입니다.

현재 의존 방향도:

```text
application.service
       │
       ▼
persistence.dao
       │
       ▼
Mapper XML
```

로 정의되어 있습니다.

Service는 SQL을 알아서는 안 됩니다.

```java
// 비권장

@Service
class Mgcoa9001Service {

    public void process() {

        String sql =
            "SELECT * FROM TB_MG_TX_CONTROL";  // X
    }
}
```

대신:

```text
Service
   ↓
dao.selectTransactionControl(...)
```

처럼 사용합니다.

---

# 10. Service와 Transaction

이 부분이 PDMG에서는 특히 중요합니다.

현재 기본 책임은:

```text
Facade
    =
Transaction Boundary

Service
    =
Business Boundary
```

입니다.

현재 컴포넌트 기준에서 Service는 **기본적으로 자체 TX를 소유하지 않고 Facade Transaction 안에서 실행**됩니다.

```text
Handler
   ↓
Facade Proxy
   │
   ├─ @Transactional
   │
   ├──── TX BEGIN
   │
   ▼
Service
   │
   ├─ Validation
   ├─ Rule
   ├─ DAO
   │
   ▼
DAO / Mapper
   │
   ▼
Service Return
   │
   ▼
Facade Return
   │
   └──── COMMIT
```

Service에 무조건 `@Transactional`을 붙이는 구조가 기본은 아닙니다.

---

# 11. Timeout ON에서는 외부 Transaction까지 존재한다

PDMG의 Timeout ON 구조에서는 더 바깥에 Worker Transaction이 있을 수 있습니다.

```text
Timeout Executor
      ↓
Worker Thread
      ↓
TransactionTemplate
      │
      ├──── Physical TX BEGIN
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

같은 TransactionManager + `REQUIRED`이면 Facade는 외부 Transaction에 참여합니다.

따라서 Service 입장에서는:

```text
Service

"TX를 내가 만드는 계층"
          X

"이미 만들어진 TX 안에서
 업무를 수행하는 계층"
          O
```

로 이해하는 것이 가장 명확합니다. Facade TX와 Service AOP가 같은 Transaction 내부에서 동작하도록 현재 구조가 정리되어 있습니다.

---

# 12. Service와 업무 선·후처리

현재 `BizPrePostAspect`는 Service 계층을 Pointcut으로 잡습니다.

```text
Facade
   ↓
TX BEGIN
   ↓
Service Proxy
   │
   ├─ BizPrePostAspect @Before
   │
   ▼
Service
   │
   ├─ Validation
   ├─ Business Logic
   ├─ DAO
   │
   ▼
BizPrePostAspect @AfterReturning
   │
   ▼
Facade
   ↓
COMMIT
```

현재 Pointcut은 `application.service`의 public 메서드를 대상으로 하며, 따라서 업무 선후처리가 Facade Transaction 안에서 실행됩니다.

여기서 중요한 점:

```text
Service 호출 1회
     ↓
업무 선처리 1회
     ↓
Service 업무
     ↓
정상 반환
     ↓
업무 후처리
```

입니다.

---

# 13. 현재 AS-IS Service와 Rule

여기서는 **AS-IS와 TO-BE를 반드시 구분해야 합니다.**

현재 PDMG 실제 소스에는 독립적인:

```text
application.rule
```

패키지가 명확하게 존재하지 않습니다.

따라서 현재는:

```text
[AS-IS]

Service
  │
  ├─ Validation
  ├─ Business Rule
  ├─ Business Flow
  ├─ Paging
  └─ DAO
```

입니다.

---

# 14. 권장 TO-BE Service 구조

업무가 커지면 Service 하나에 모든 Rule을 넣으면 코드가 비대해집니다.

따라서 TO-BE는:

```text
[TO-BE]

Service
   │
   ├───────────────┐
   │               │
   ▼               ▼
 Rule              DAO
   │               │
   │               ▼
   │             Mapper
   │               │
   │               ▼
   │               DB
   │
   └────────────── Client
                    │
                    ▼
               External API
```

로 발전시키는 것이 좋습니다.

예:

```text
application
├─ facade
├─ service
│   └─ CustomerService
└─ rule
    ├─ CustomerEligibilityRule
    ├─ CustomerStatusRule
    └─ CustomerDuplicateRule
```

이때:

```text
Service
= 처리 순서

Rule
= 업무 판단
```

으로 나눕니다.

---

# 15. Service와 Rule의 차이

예를 들어 상품 가입이라면:

### Service

```text
1. 고객 조회
2. 상품 조회
3. 가입가능 여부 판단
4. 계좌 생성
5. 가입정보 저장
6. 결과 생성
```

### Rule

```text
"고객 나이가 19세 이상인가?"

"해당 상품 가입 자격이 있는가?"

"동일 상품에 이미 가입했는가?"
```

즉:

```text
Service
= Workflow / Orchestration

Rule
= Decision / Business Policy
```

입니다.

현재 PDMG에서는 이 둘이 Service에 함께 존재하지만, 업무가 복잡해질수록 Rule 분리 가치가 커집니다. 현재 별도 Rule 패키지는 AS-IS가 아니라는 점은 유지해야 합니다.

---

# 16. 외부 연동은 DAO와 분리한다

권장 Service 구조는:

```text
                   Service
                      │
          ┌───────────┼───────────┐
          │           │           │
          ▼           ▼           ▼
        Rule         DAO        Client
                      │           │
                      ▼           ▼
                     DB       외부 API/EAI
```

입니다.

즉:

```text
DAO
= Database

Client
= External System
```

입니다.

현재 패키지 구조에도 `client`가 DB 접근 영역과 별도 경계로 존재합니다.

---

# 17. Service가 하면 안 되는 일

이 부분을 개발표준으로 강하게 잡는 것이 좋습니다.

| 금지                            | 이유                     |
| ------------------------------- | ------------------------ |
| HTTP `@PostMapping`             | Controller 책임          |
| ServiceId → Handler 선택        | Dispatcher/Handler 책임  |
| 표준전문 Header 조립            | Framework 책임           |
| JWT 검증                        | System Security 책임     |
| Transaction 전체 경계 직접 통제 | Facade/TCF 책임          |
| SQL 문자열 작성                 | Mapper 책임              |
| JDBC 직접 사용                  | DAO/Mapper 경계 침범     |
| HTTP Response 생성              | Controller/Resolver 책임 |
| ImageLog 직접 공통 처리         | Framework 책임           |
| Runtime Thread 생성             | TCF/Executor 책임        |

의존 방향도 현재 기준에서:

```text
Handler ─────────► Facade
                    │
                    ▼
                 Service
                    │
           ┌────────┴─────────┐
           ▼                  ▼
          DAO               Client
           │
           ▼
         Mapper
```

이어야 하며, `Service → Handler/Controller` 같은 역방향 의존은 금지됩니다.

---

# 18. Service 코드의 권장 형태

아래 코드는 **현재 소스의 복사본이 아니라 아키텍처 구조를 설명하기 위한 권장 예시**입니다.

```java
@Service
@RequiredArgsConstructor
public class Mgcoa9001Service {

    private final Mgcoa9001DAO dao;

    public Mgcoa9001S0DTOout mgcoa9001S0(
            Mgcoa9001S0DTOin input) {

        // 1. 입력 검증
        validate(input);

        // 2. 업무 조회
        List<Map<String, Object>> rows =
                dao.mgcoa9001S0_S0(
                    toParameters(input)
                );

        // 3. 결과 구성
        return Mgcoa9001S0DTOout.builder()
                .rows(rows)
                .build();
    }
}
```

핵심 구조는:

```text
Input
  ↓
Validate
  ↓
Business Decision
  ↓
DAO / Client
  ↓
Result Composition
  ↓
Output
```

입니다.

---

# 19. 조금 더 발전된 TO-BE 형태

Rule을 분리하면:

```java
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerDao customerDao;
    private final CustomerEligibilityRule eligibilityRule;
    private final CreditClient creditClient;

    public CustomerResponse register(CustomerRequest request) {

        validate(request);

        Customer customer =
            customerDao.select(request.customerId());

        eligibilityRule.check(customer);

        CreditResult credit =
            creditClient.inquiry(customer);

        eligibilityRule.checkCredit(credit);

        customerDao.insertRegistration(
            customer,
            credit
        );

        return createResponse(customer);
    }
}
```

구조적으로는:

```text
Service
 │
 ├─ Validate
 │
 ├─ DAO
 │     └─ 데이터 조회
 │
 ├─ Rule
 │     └─ 가입 가능 판단
 │
 ├─ Client
 │     └─ 외부 신용조회
 │
 ├─ Rule
 │     └─ 결과 판단
 │
 ├─ DAO
 │     └─ 저장
 │
 └─ Response
```

입니다.

이 방향은 **Service가 절차를 제어하고 Rule/DAO/Client가 각각 전문 책임을 수행**하게 합니다.

---

# 20. Service 아키텍처의 권장 최종 기준

| 영역              | PDMG 기준             |
| ----------------- | --------------------- |
| 패키지            | `application.service` |
| 단위              | Program 중심          |
| 호출자            | Facade                |
| 입력              | 업무 DTO              |
| 핵심 책임         | 업무 절차             |
| Validation        | O                     |
| Business Rule     | O, 복잡하면 Rule 분리 |
| Paging 정책       | O                     |
| DAO 호출          | O                     |
| Client 호출       | O                     |
| SQL               | X                     |
| HTTP              | X                     |
| ServiceId Routing | X                     |
| 표준전문 조립     | X                     |
| TX 시작           | 기본 X                |
| Business AOP 적용 | O                     |
| 응답              | 업무 DTO              |

---

## 21. 전체 PDMG에서 Service의 위치를 한 장으로 정리하면

```text
                    ServiceId
                       │
                       ▼
                  Dispatcher
                       │
                       ▼
                    Handler
                       │
                 거래 선택
                       │
                       ▼
                    Facade
                       │
                 Use Case / TX
                       │
                       ▼
             ┌─────────────────┐
             │     Service     │
             │                 │
             │ Validation      │
             │ Business Flow   │
             │ Business Rule   │
             │ Paging          │
             │ Result Assembly │
             └────────┬────────┘
                      │
          ┌───────────┼────────────┐
          │           │            │
          ▼           ▼            ▼
        Rule*        DAO         Client
                      │            │
                      ▼            ▼
                   Mapper       HTTP/EAI
                      │
                      ▼
                     SQL
                      │
                      ▼
                     DB

          * Rule은 현재 TO-BE 확장 영역
```

결국 **PDMG Service 아키텍처의 핵심은 “Service가 업무를 수행하되, HTTP·라우팅·Transaction·SQL 같은 다른 계층의 책임까지 가져가지 않는 것”**입니다. 현재 실제 구조에서는 `Facade → Service → DAO`가 기본이며, Service는 업무 절차·Validation·Paging·업무 Rule을 담당하고, 복잡도가 증가하면 `Service → Rule / DAO / Client` 구조로 발전시키는 것이 가장 자연스럽습니다.
