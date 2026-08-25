# Validation 아키텍처 구조

현재 **PDMG 실제 소스 기준**으로 보면 Validation은 한 군데에서 전부 처리하는 구조가 아닙니다. **System → TCF/Dispatcher → 업무 Service → DB**로 내려가면서 각 계층이 자기 책임에 맞는 검증을 수행하는 구조로 보는 것이 정확합니다.

특히 현재 PDMG AS-IS는 업무 DTO에 `@NotNull`, `@NotBlank`, `@Size` 등을 광범위하게 붙이는 Bean Validation 중심 구조가 아니라, **Service에서 `BizException`을 발생시키는 명시적 업무 Validation이 중심**입니다. PDMG의 계층 책임은 `Handler → Facade → Service → DAO → Mapper`로 분리되어 있고 , DTO는 표준전문과 업무 계층 사이의 서비스별 입력·출력 계약으로 사용됩니다.

## 1. 전체 Validation Big Picture

```text
                     PDMG VALIDATION ARCHITECTURE

┌─────────────────────────────────────────────────────────────┐
│ Client / pdmg-ui                                            │
│                                                             │
│ hdr_nhnis + dto                                             │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
═══════════════════════════════════════════════════════════════
① SYSTEM / MESSAGE VALIDATION
═══════════════════════════════════════════════════════════════

                    DefaultFilter
                         │
                         ├─ JSON / Request Body
                         ├─ hdr_nhnis Parsing
                         ├─ GUID / Context
                         └─ 기본 요청정보 확인
                         │
                         ▼
              ServicePreventionInterceptor
                         │
                         ├─ JWT
                         ├─ User
                         ├─ ServiceId
                         ├─ Client IP
                         └─ 시스템 공통정보 확인
                         │
                         ▼

═══════════════════════════════════════════════════════════════
② TCF / ROUTING VALIDATION
═══════════════════════════════════════════════════════════════

             OnlineTransactionController
                         │
                         ▼
                    TcfFacade
                         │
                         ▼
                       STF
                         │
                         ├─ 거래통제
                         ├─ 실행 가능 여부
                         └─ 정책 검증
                         │
                         ▼
               Timeout Executor
                         │
                         ▼
             TransactionDispatcher
                         │
                         ├─ ServiceId 존재?
                         ├─ Handler 등록?
                         │
                         └─ X → ServiceHandlerNotFound
                         │
                         ▼

═══════════════════════════════════════════════════════════════
③ BUSINESS INPUT VALIDATION
═══════════════════════════════════════════════════════════════

                      Handler
                         │
                         ▼
                      Facade
                         │
                         ▼
                     Service          ★ 핵심
                         │
            ┌────────────┼───────────────┐
            │            │               │
            ▼            ▼               ▼
        필수값 검증    형식검증       업무규칙 검증
        null/blank     Y/N, Code       존재/중복/상태
            │            │               │
            └────────────┼───────────────┘
                         │
                  실패 → BizException
                         │
                         ▼

═══════════════════════════════════════════════════════════════
④ DATA VALIDATION / PERSISTENCE
═══════════════════════════════════════════════════════════════

                      DAO
                       │
                       ▼
                    Mapper
                       │
                       ▼
                      DB

═══════════════════════════════════════════════════════════════
⑤ VALIDATION ERROR STANDARDIZATION
═══════════════════════════════════════════════════════════════

                 BizException
                      │
                      ▼
             GlobalExceptionHandler
                      │
                exceptionCode.yml
                      │
                      ▼
                NH_NIS_ERR_DTO
                      │
                      ▼
             Response Resolver
                      │
                      ▼
             hdr_nhnis + result
```

가장 짧게 줄이면:

```text
요청 구조 검증
   ↓
거래 실행 가능성 검증
   ↓
ServiceId Routing 검증
   ↓
업무 입력 Validation
   ↓
업무 Rule Validation
   ↓
DB 처리
   ↓
표준 오류 응답
```

입니다.

---

## 2. Validation을 5가지로 나누는 것이 중요합니다

| 구분                    | 담당 계층            | 검증 내용                      | 실패 형태                |
| ----------------------- | -------------------- | ------------------------------ | ------------------------ |
| **System Validation**   | Filter / Interceptor | Header, JWT, Context 등        | Framework Exception      |
| **TCF Validation**      | STF                  | 거래통제, 실행정책             | `BizException` 등        |
| **Routing Validation**  | Dispatcher           | ServiceId와 Handler 존재 여부  | `ServiceHandlerNotFound` |
| **Input Validation**    | **Service**          | 필수값, 형식, 범위             | `BizException`           |
| **Business Validation** | **Service / Rule**   | 존재여부, 중복, 상태, 업무조건 | `BizException`           |

여기서 가장 중요한 원칙은:

> **문법적 Validation과 업무적 Validation을 구분해야 합니다.**

예를 들어:

```text
customerNo가 비어 있다
    → Input Validation

blockYn이 Y/N이 아니다
    → Input Validation

고객번호는 형식상 맞지만 고객이 존재하지 않는다
    → Business Validation

동일 거래통제 데이터가 이미 존재한다
    → Business Validation
```

입니다.

---

# 3. 현재 PDMG AS-IS의 핵심 Validation 위치는 `Service`

현재 실제 `mgcoa9001Service`를 보면 Validation이 상당히 명확합니다.

예를 들어 등록 거래는 개념적으로:

```java
String controlType =
    requireControlType(input == null ? null : input.getControlType());

String blockYn =
    requireYn(input == null ? null : input.getBlockYn(), "blockYn");

String changeReason =
    requireReason(input == null ? null : input.getChangeReason());
```

처럼 처리합니다.

구조로 보면:

```text
mgcoa9001C0DTOin
       │
       ▼
mgcoa9001Service
       │
       ├─ requireControlType()
       ├─ requireYn()
       ├─ requireReason()
       └─ resolveTarget()
```

즉 현재는 **업무 Service가 입력값 Validation 책임을 상당 부분 가지고 있습니다.**

---

# 4. 필수값 Validation

현재 소스의 대표 구조는 다음과 같습니다.

```java
private String requireText(String value, String field) {

    String trimmed = trimToNull(value);

    if (trimmed == null) {
        throw new BizException("MP0401");
    }

    return trimmed;
}
```

개념적으로:

```text
입력값
  │
  ├─ null
  ├─ ""
  └─ "   "
       │
       ▼
   Validation 실패
       │
       ▼
 BizException("MP0401")
```

입니다.

따라서 현재 오류코드 의미를 구조화하면:

```text
MP0401
  =
필수 입력값 누락
```

으로 사용됩니다.

---

# 5. 형식 Validation

예를 들어 `blockYn`은 단순히 값이 존재한다고 끝나지 않습니다.

```text
blockYn
   │
   ├─ Y  → 정상
   ├─ N  → 정상
   │
   ├─ A  → 오류
   ├─ 1  → 오류
   └─ YES → 오류
```

현재 코드에서는 개념적으로:

```java
String upper = trimmed.toUpperCase(Locale.ROOT);

if (!YN.matcher(upper).matches()) {
    throw new BizException("MP0403");
}
```

형태입니다.

즉:

```text
MP0403
  =
입력값 형식/값이 올바르지 않음
```

으로 사용됩니다.

`controlType`도 마찬가지입니다.

```text
controlType

GLOBAL
BUSINESS
SERVICE
CHANNEL
BRANCH
USER
IP

     ↓

허용 목록에 존재?
     │
 ┌───┴───┐
 O       X
 │       │
통과   MP0403
```

입니다.

---

# 6. 값의 길이·업무 조건도 Service에서 검증합니다

현재 `changeReason`은 단순 NotBlank뿐 아니라 길이 조건도 가지고 있습니다.

```java
String trimmed = requireText(value, "changeReason");

if (trimmed.length() < 5) {
    throw new BizException("MP0403");
}
```

즉:

```text
changeReason
      │
      ├─ null / blank
      │       ↓
      │     MP0401
      │
      └─ 값 존재
             │
             ▼
        길이 >= 5 ?
          │       │
          O       X
          │       │
        통과    MP0403
```

입니다.

이런 검증은 단순 DTO 필드 존재 여부를 넘어선 **업무 입력 정책 Validation**입니다.

---

# 7. 업무 Validation — 존재 여부

Validation은 입력값 형식만 검사하는 것이 아닙니다.

예를 들어 수정하려는 데이터가 실제 존재해야 합니다.

```text
수정 요청
   │
   ▼
입력 Key Validation
   │
   ▼
DAO.exists()
   │
   ├─ > 0
   │    ↓
   │   수정 진행
   │
   └─ 0
        ↓
    BizException
      MP0404
```

실제 구조는:

```java
if (mgcoa9001DAO.mgcoa9001S0_S0_exists(keyRow) <= 0) {
    throw new BizException("MP0404");
}
```

입니다.

따라서:

```text
MP0404
  =
대상 데이터 없음
```

이라는 **업무 의미 Validation**이 됩니다.

---

# 8. 중복 Validation

등록의 경우 반대로 이미 존재하면 안 됩니다.

```text
등록 요청
   │
   ▼
입력 Validation
   │
   ▼
DAO.exists()
   │
   ├─ 0
   │   ↓
   │ 등록 가능
   │
   └─ > 0
       ↓
     MP0409
```

실제 구조도:

```java
if (mgcoa9001DAO.mgcoa9001S0_S0_exists(param) > 0) {
    throw new BizException("MP0409");
}
```

형태입니다.

즉:

```text
MP0409
  =
중복 데이터
```

입니다.

---

# 9. Dispatcher도 Validation의 한 종류입니다

`TransactionDispatcher`는 단순 호출기가 아닙니다.

```text
serviceId
    │
    ▼
handlerMap.get(serviceId)
    │
 ┌──┴────┐
 │       │
있음    없음
 │       │
 ▼       ▼
Handler  ServiceHandlerNotFound
```

따라서 다음도 중요한 Validation입니다.

> **요청된 ServiceId가 시스템이 지원하는 거래인가?**

이것은 업무 데이터 검증이 아니라 **Architecture Routing Validation**입니다.

앞에서 정리한 PDMG/NSIGHT의 Dispatcher 구조 역시 ServiceId를 Handler/Use Case로 연결하는 구조입니다.

---

# 10. STF의 거래통제도 Validation이지만 업무 DTO 검증과는 다릅니다

STF에서는 다음 질문을 합니다.

```text
"입력값이 올바른가?"
          X

"이 거래를 지금 실행해도 되는가?"
          O
```

예:

```text
ServiceId = mgcoa5530S0

        ↓

STF

        ↓

거래 통제 정책 조회

        ↓

SERVICE BLOCK ?
USER BLOCK ?
BRANCH BLOCK ?
IP BLOCK ?

        ↓

ALLOW / BLOCK
```

따라서:

```text
DTO Validation
≠
TCF Transaction Control Validation
```

입니다.

이 둘을 섞으면 Service에 거래통제 로직이 들어가거나 STF에 업무 입력 검증이 들어가는 잘못된 구조가 됩니다.

---

# 11. Validation 실패는 업무 코드가 JSON을 직접 만들지 않습니다

이 부분이 PDMG 아키텍처에서 중요합니다.

```text
Service
   │
   │ Validation 실패
   ▼
throw BizException("MP0401")
   │
   ▼
TransactionTemplate
   │
   └─ ROLLBACK 필요 시 처리
   │
   ▼
GlobalExceptionHandler
   │
   ├─ exceptionCode.yml
   ├─ Error Message
   └─ Action Message
   │
   ▼
NH_NIS_ERR_DTO
   │
   ▼
Response Resolver
   │
   ▼
hdr_nhnis + result
```

현재 PDMG 에러처리 역시 업무 프로그램이 오류 JSON을 직접 생성하지 않고 예외를 던지고 Framework가 표준 오류 전문으로 만드는 구조입니다.

예를 들면:

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "rms_svc_c": "mgcoa9001C0"
    }
  },
  "result": {
    "stdErrCode": "MP0401",
    "stdErrMsgCntn": "필수 입력값이 누락되었습니다.",
    "errType": "BIZ"
  }
}
```

와 같은 구조가 됩니다.

---

# 12. 현재 `ValidateUtil`은 있지만 핵심 업무 Validation 엔진은 아닙니다

`pdmg-fw`에는 실제로:

```text
nhnis.fw.commons.util.ValidateUtil
```

이 존재합니다.

지원 기능은 대략:

```text
null
blank
empty collection
empty map
object field recursion
boolean expression
```

입니다.

하지만 현재 소스를 보면 `ValidateUtil`이 업무 DTO Validation의 표준 진입점으로 광범위하게 사용되는 구조는 아닙니다.

즉 AS-IS를 정확히 표현하면:

```text
[AS-IS]

ValidateUtil 존재
       │
       └─ 공통 Utility 성격

업무 Validation
       │
       └─ 각 Service의
          requireText()
          requireYn()
          requireReason()
          exists()
          등의 명시적 구현
```

입니다.

따라서 `ValidateUtil`이 PDMG의 중앙 Validation Framework라고 설명하면 현재 실제 소스와 맞지 않습니다.

---

# 13. Bean Validation 관점에서 본 현재 AS-IS

현재 PDMG 업무 소스에서는 다음과 같은 구조가 주류로 확인되지 않습니다.

```java
@NotBlank
@Size(max = 20)
@Pattern(...)
@Valid
public class Mgcoa9001C0DTOin {
}
```

즉 현재 PDMG는:

```text
DTO Annotation Validation 중심
              X

Service Explicit Validation 중심
              O
```

입니다.

이 점은 NSIGHT TCF의 다른 모듈과 혼동하면 안 됩니다. 현재 ZIP의 NSIGHT TCF 영역에는 `spring-boot-starter-validation` 의존성과 `MethodArgumentNotValidException` 처리 코드가 존재하지만, **PDMG AS-IS 업무 Validation 기준을 그것으로 자동 대체해서는 안 됩니다.** 프로젝트 자체도 PDMG AS-IS와 NSIGHT TCF 기준을 분리하도록 요구합니다.

---

# 14. 제가 권장하는 TO-BE Validation 구조

PDMG를 개선한다면 **Bean Validation으로 모든 것을 옮기는 방식보다 3단계 Validation**이 좋습니다.

```text
                 TO-BE VALIDATION

Request DTO
    │
    ▼
┌───────────────────────────────┐
│ ① Structural Validation      │
│ Bean Validation              │
│                               │
│ @NotBlank                    │
│ @Size                        │
│ @Pattern                     │
│ @Min / @Max                  │
└───────────────┬───────────────┘
                │
                ▼
             Handler
                │
                ▼
             Facade
                │
                ▼
┌───────────────────────────────┐
│ ② Business Validation        │
│ Service / Rule               │
│                               │
│ 존재 여부                    │
│ 중복 여부                    │
│ 업무 상태                    │
│ 권한/조건                    │
│ 다른 데이터와의 관계         │
└───────────────┬───────────────┘
                │
                ▼
              DAO
                │
                ▼
┌───────────────────────────────┐
│ ③ Data Integrity             │
│ DB                           │
│                               │
│ PK / UK                      │
│ NOT NULL                     │
│ FK                           │
│ CHECK                        │
└───────────────────────────────┘
```

이렇게 역할을 나누는 것이 좋습니다.

---

## 15. 예를 들어 고객등록이라면

DTO에서는:

```java
public class CustomerCreateRequest {

    @NotBlank
    @Size(max = 50)
    private String customerName;

    @NotBlank
    @Pattern(regexp = "[0-9]{10}")
    private String customerNo;
}
```

여기서는:

```text
null인가?
길이는 맞나?
형식은 맞나?
```

만 검사합니다.

Service에서는:

```java
if (customerDao.exists(customerNo)) {
    throw new BizException("MP0409");
}

if (!customerStatusService.canRegister(customerNo)) {
    throw new BizException("MP0410");
}
```

처럼:

```text
실제 고객이 이미 존재하는가?
현재 상태에서 등록할 수 있는가?
다른 업무조건을 만족하는가?
```

를 검증합니다.

즉 최종 표준은:

```text
DTO
 └─ Syntax / Structure

Service / Rule
 └─ Business Semantic

DAO / DB
 └─ Data Integrity
```

가 가장 적절합니다.

---

# 16. Validation을 어디에 넣으면 안 되는가

| 계층        | 금지 Validation                          |
| ----------- | ---------------------------------------- |
| Filter      | 고객 존재 여부 같은 업무 Validation      |
| Interceptor | DB를 조회하는 업무 조건                  |
| Controller  | 대량의 `if/else` 업무 검증               |
| Handler     | 필수값·업무조건 반복 구현                |
| Facade      | 필드별 단순 검증 남발                    |
| DAO         | 입력 기본값이나 업무 상태 판단           |
| Mapper      | 업무 Validation                          |
| UI          | 서버 Validation을 대신하는 유일한 방어선 |

특히 다음 구조는 피하는 것이 좋습니다.

```text
Controller
   │
   ├─ null 확인
   ├─ 코드 확인
   ├─ DB 조회
   ├─ 중복 확인
   ├─ 상태 확인
   └─ 오류 JSON 생성
```

대신:

```text
Controller
    ↓
DTO Validation
    ↓
Handler
    ↓
Facade
    ↓
Service / Rule
    ↓
Business Validation
    ↓
DAO
```

로 분리해야 합니다.

---

# 17. PDMG Validation 권장 최종 모델

```text
                         REQUEST
                            │
                            ▼
                ┌─────────────────────┐
                │ SYSTEM VALIDATION   │
                │                     │
                │ Header / JWT        │
                │ Context / Message   │
                └──────────┬──────────┘
                           ▼
                ┌─────────────────────┐
                │ TCF VALIDATION      │
                │                     │
                │ 거래통제 / 정책     │
                └──────────┬──────────┘
                           ▼
                ┌─────────────────────┐
                │ ROUTING VALIDATION  │
                │                     │
                │ ServiceId → Handler │
                └──────────┬──────────┘
                           ▼
                ┌─────────────────────┐
                │ DTO VALIDATION      │
                │                     │
                │ Required / Format   │
                │ Length / Range      │
                └──────────┬──────────┘
                           ▼
                      Handler
                           ↓
                       Facade
                           ↓
                ┌─────────────────────┐
                │ BUSINESS VALIDATION │
                │                     │
                │ Service / Rule      │
                │ Exist / Duplicate   │
                │ State / Relation    │
                └──────────┬──────────┘
                           ▼
                         DAO
                           ↓
                        Mapper
                           ↓
                          DB
                           │
                           ▼
                   정상 → COMMIT
                   오류 → ROLLBACK
                           │
                           ▼
                    BizException
                           │
                           ▼
               GlobalExceptionHandler
                           │
                           ▼
                    표준 오류전문
```

### 핵심 결론

현재 PDMG Validation 아키텍처를 한 문장으로 정의하면:

> **시스템·거래·라우팅 Validation은 `pdmg-fw`가 담당하고, 실제 업무 입력과 업무 규칙 Validation은 `pdmg-service`의 Service가 담당하며, 실패는 `BizException → GlobalExceptionHandler → 표준 오류전문`으로 일원화하는 구조**입니다.

그리고 개선 방향은 **DTO의 단순 형식 검증은 Bean Validation으로 표준화하고, DB 조회가 필요한 의미적 업무 검증은 Service/Rule에 유지하는 것**이 가장 적절합니다. 이렇게 해야 Validation이 Controller·Handler·Service에 중복되지 않고, 앞에서 정리한 DTO/계층/에러처리 아키텍처와도 일관됩니다.
