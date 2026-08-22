# Rule 아키텍처 구조

현재 **PDMG 실제 소스 기준**으로 보면, `Rule`은 아직 독립된 업무 계층으로 완전히 분리되어 있지 않습니다. 현재 `pdmg-service`의 실제 실행 구조는 **`Handler → Facade → Service → DAO → Mapper`**이고, 업무 규칙·입력 검증·정규화·페이징 정책 등이 상당 부분 `Service` 내부에 들어가 있습니다.

따라서 Rule 아키텍처는 **AS-IS와 TO-BE를 구분**해서 이해해야 합니다.

---

## 1. Rule 아키텍처 Big Picture

```text
                    PDMG RULE ARCHITECTURE

──────────────────────────────────────────────────────────────
                    TCF / Framework
──────────────────────────────────────────────────────────────

ServiceId
    │
    ▼
Dispatcher
    │
    ▼
Handler
    │
    ▼
Facade
    │
    ▼

──────────────────────────────────────────────────────────────
                 Business Application
──────────────────────────────────────────────────────────────

Service
    │
    │ "업무를 어떤 순서로 처리할 것인가?"
    │
    ├──────────────────┬──────────────────┐
    │                  │                  │
    ▼                  ▼                  ▼
   Rule                DAO             Integration
    │                  │                  │
    │                  ▼                  ▼
    │               Mapper            HTTP / EAI
    │                  │
    │                  ▼
    │                  DB
    │
    ▼
업무 판단
검증
계산
상태판정
정규화
마스킹
정책결정
```

가장 중요한 구분은 이것입니다.

> **Service = 업무 처리 절차(Orchestration)**
> **Rule = 업무 판단 기준(Business Decision)**

---

# 2. 현재 PDMG AS-IS

현재 실제 `pdmg-service`에는 명확한:

```text
application/rule
```

패키지가 존재하지 않습니다.

현재 구조는:

```text
nhnis.mg.co.a
│
├─ entry
│   └─ handler
│
├─ application
│   ├─ controller
│   ├─ facade
│   └─ service
│
├─ dto
│
└─ persistence
    └─ dao
```

입니다.

즉 현재는:

```text
Handler
   ↓
Facade
   ↓
Service
   │
   ├─ Validation
   ├─ 업무조건 판단
   ├─ 데이터 정규화
   ├─ Paging 정책
   ├─ DAO 호출
   └─ 결과 DTO 구성
   ↓
DAO
```

구조입니다. 따라서 기존 문서에서도 **독립 Rule 패키지는 현재 PDMG AS-IS에는 없고, Rule은 TO-BE 확장 모델**로 구분하고 있습니다.

---

# 3. 실제로 Service 안에 Rule 성격의 코드가 들어가 있다

예를 들어 현재 `mgcoa9001Service`에는 거래통제 업무를 위해 다음과 같은 판단이 들어가 있습니다.

```text
controlType이 유효한가?
blockYn은 Y/N인가?
changeReason이 존재하는가?
targetValue가 controlType에 적합한가?
입력 문자열을 어떻게 정규화할 것인가?
```

개념적으로 지금은:

```text
mgcoa9001Service
    │
    ├─ requireControlType()
    ├─ requireYn()
    ├─ requireReason()
    ├─ resolveTarget()
    ├─ normalizeControlTypeOptional()
    ├─ normalizeYnOptional()
    │
    ├─ DAO.exists()
    ├─ DAO.insert()
    ├─ DAO.update()
    └─ DAO.delete()
```

처럼 **업무 Rule과 업무 절차가 같은 Service에 섞여 있는 상태**입니다.

`mgcoa8888Service`도 유사합니다.

```text
pageSize 최대 100
withinSeconds 최대 86400
minElapsedSeconds 최대 3600
```

같은 정책 판단이 Service 내부에 있습니다.

이런 부분이 Rule 분리의 대표 후보입니다.

---

# 4. TO-BE Rule 구조

권장하는 PDMG 구조는 다음입니다.

```text
nhnis.mg.co.a
│
├─ entry
│   └─ handler
│
├─ application
│   ├─ controller
│   ├─ facade
│   ├─ service
│   │
│   └─ rule                  ★ 추가
│       ├─ Mgcoa9001Rule
│       └─ Mgcoa8888Rule
│
├─ dto
│
└─ persistence
    └─ dao
```

실행 구조는:

```text
Handler
   ↓
Facade
   ↓
Service
   │
   ├──────→ Rule
   │          │
   │          ├─ Validate
   │          ├─ Normalize
   │          ├─ Calculate
   │          ├─ Decide
   │          └─ Mask
   │
   ├──────→ DAO
   │          ↓
   │        Mapper
   │          ↓
   │          DB
   │
   └──────→ Integration
```

이 구조를 **PDMG Rule 아키텍처의 권장 TO-BE**로 잡는 것이 좋습니다.

---

# 5. Service와 Rule의 차이

이 구분이 Rule 아키텍처의 핵심입니다.

| 구분          | Service                      | Rule                       |
| ------------- | ---------------------------- | -------------------------- |
| 핵심 질문     | 무엇을 어떤 순서로 처리할까? | 이 조건에서 무엇이 맞는가? |
| 업무 흐름     | O                            | X                          |
| DAO 호출      | O                            | 원칙적으로 X               |
| 외부 API 호출 | O                            | X                          |
| 입력 검증     | 호출/조립                    | O                          |
| 업무조건 판단 | Rule 호출                    | O                          |
| 계산          | 조립                         | O                          |
| 정규화        | 가능하지만 Rule 권장         | O                          |
| 상태 판정     | Rule 결과 이용               | O                          |
| Transaction   | 참여                         | 직접 정의 금지 권장        |
| 테스트        | Integration 중심             | Unit Test 중심             |

쉽게 말하면:

```text
Service
= 일을 시킨다.

Rule
= 판단한다.
```

입니다.

---

# 6. Rule에는 무엇을 넣는가

Rule은 크게 5종류 정도로 분류하면 관리하기 좋습니다.

| Rule 종류          | 예                                 |
| ------------------ | ---------------------------------- |
| Validation Rule    | 필수값, 범위, 코드 검증            |
| Decision Rule      | 등록 가능 여부, 처리 유형 결정     |
| Calculation Rule   | 금액, 점수, 비율, 기간 계산        |
| Normalization Rule | Y/N, 코드, 문자열 표준화           |
| Policy Rule        | 페이징 한도, 마스킹, 상태전이 정책 |

예를 들어:

```text
Mgcoa9001Rule

├─ validateControlType()
├─ validateBlockYn()
├─ validateChangeReason()
├─ normalizeControlType()
├─ normalizeBlockYn()
└─ resolveTarget()
```

처럼 만들 수 있습니다.

---

# 7. 반대로 Rule에 넣으면 안 되는 것

Rule이 커지기 시작하면 다시 Service처럼 변합니다.

다음은 금지하는 것이 좋습니다.

```text
Rule
 ├─ DAO 직접 호출                X
 ├─ Mapper 호출                  X
 ├─ SQL                          X
 ├─ HTTP Client 호출             X
 ├─ 다른 시스템 호출             X
 ├─ Transaction 시작             X
 ├─ ServiceContext 직접 조작     X
 ├─ ThreadLocal 조작             X
 └─ Response 전문 생성           X
```

특히 다음 구조는 피하는 것이 좋습니다.

```text
Service
   ↓
Rule
   ↓
DAO
   ↓
Mapper
```

보다는:

```text
             Service
               │
       ┌───────┴───────┐
       ▼               ▼
      Rule             DAO
                         │
                         ▼
                       Mapper
```

가 더 좋습니다.

---

# 8. DB 데이터가 필요한 업무 Rule은 어떻게 처리하는가

여기서 중요한 설계 문제가 하나 있습니다.

예를 들어:

> "이미 등록된 거래통제 정책이면 등록할 수 없다."

이것은 분명 업무 Rule이지만 DB 조회가 필요합니다.

Rule이 직접 DAO를 호출하게 하지 말고:

```text
Service
   │
   ├─ DAO.exists()
   │      ↓
   │   exists = true
   │
   ▼
Rule.validateCreateAllowed(exists)
```

구조로 만드는 것이 좋습니다.

즉:

```text
DB에서 사실(Fact)을 가져오는 책임
             =
          Service / DAO

그 사실을 판단하는 책임
             =
             Rule
```

입니다.

예:

```java
boolean exists = dao.exists(key);

rule.validateCreateAllowed(exists);
```

Rule:

```java
public void validateCreateAllowed(boolean exists) {

    if (exists) {
        throw new BizException("MP0409");
    }
}
```

이렇게 하면 Rule 테스트에는 DB가 필요 없습니다.

---

# 9. `mgcoa9001`을 Rule 구조로 바꾸면

현재 구조:

```text
mgcoa9001Service
    │
    ├─ 입력검증
    ├─ 값 정규화
    ├─ Target 판단
    ├─ 중복조회
    ├─ 등록
    ├─ 수정
    └─ 삭제
```

TO-BE:

```text
                   mgcoa9001Service

                         │
        ┌────────────────┼────────────────┐
        │                │                │
        ▼                ▼                ▼

 Mgcoa9001Rule      mgcoa9001DAO     DTO Mapping
        │                │
        │                ▼
        │             Mapper
        │                │
        │                ▼
        │                DB
        │
        ├─ validateControlType
        ├─ validateBlockYn
        ├─ validateReason
        ├─ resolveTarget
        ├─ normalize
        └─ validateCreateAllowed
```

등록 거래라면:

```text
mgcoa9001C0()

      │
      ▼
Rule.validateInput()
      │
      ▼
Rule.normalize()
      │
      ▼
DAO.exists()
      │
      ▼
Rule.validateCreateAllowed(exists)
      │
      ▼
DAO.insert()
      │
      ▼
Response DTO
```

이렇게 됩니다.

---

# 10. 코드 구조 예

```java
@Component
public class Mgcoa9001Rule {

    private static final Set<String> CONTROL_TYPES =
        Set.of(
            "GLOBAL",
            "BUSINESS",
            "SERVICE",
            "CHANNEL",
            "BRANCH",
            "USER",
            "IP"
        );

    public String requireControlType(String value) {

        if (value == null || value.isBlank()) {
            throw new BizException("MP0401");
        }

        String normalized = value.trim().toUpperCase();

        if (!CONTROL_TYPES.contains(normalized)) {
            throw new BizException("MP0403");
        }

        return normalized;
    }

    public String requireYn(String value) {

        if (!"Y".equalsIgnoreCase(value)
                && !"N".equalsIgnoreCase(value)) {
            throw new BizException("MP0403");
        }

        return value.toUpperCase();
    }

    public void validateCreateAllowed(boolean exists) {

        if (exists) {
            throw new BizException("MP0409");
        }
    }
}
```

그러면 Service는 훨씬 단순해집니다.

```java
@Service
public class Mgcoa9001Service {

    private final Mgcoa9001Rule rule;
    private final Mgcoa9001Dao dao;

    public Mgcoa9001C0Response create(
            Mgcoa9001C0Request input) {

        String controlType =
                rule.requireControlType(input.getControlType());

        String blockYn =
                rule.requireYn(input.getBlockYn());

        boolean exists =
                dao.exists(controlType, input.getTargetValue());

        rule.validateCreateAllowed(exists);

        int count = dao.insert(...);

        return new Mgcoa9001C0Response(count);
    }
}
```

이제 코드를 읽을 때도 명확합니다.

```text
Service
"등록 업무를 수행한다."

Rule
"등록 가능한지를 판단한다."

DAO
"DB에 등록한다."
```

---

# 11. Rule과 Validation의 관계

모든 Validation을 Rule이라고 부를 필요는 없습니다.

```text
Validation
│
├─ 형식 Validation
│   ├─ null
│   ├─ blank
│   ├─ length
│   └─ format
│
└─ Business Rule
    ├─ 고객 상태가 정상인가?
    ├─ 상품 판매가 가능한가?
    ├─ 중복 등록인가?
    └─ 이 상태에서 변경 가능한가?
```

PDMG에서는 현재 Service가 입력 Validation과 업무 Validation을 모두 상당 부분 수행합니다.

TO-BE에서는 다음처럼 나누는 것이 좋습니다.

```text
단순 DTO 형식
    ↓
Validation

업무 의미를 가진 조건
    ↓
Rule
```

예를 들어:

```text
pageNo < 1
          → Validation / Policy Rule

고객번호 미입력
          → Input Validation

이미 해지된 고객은 상품가입 불가
          → Business Rule

고객등급에 따라 한도 계산
          → Calculation Rule
```

---

# 12. Rule과 Transaction 관계

Rule에는 일반적으로:

```java
@Transactional
```

을 붙이지 않는 것이 좋습니다.

PDMG 거래 구조에서는 Transaction은 Framework 또는 Facade/Service 경계에서 관리됩니다. Rule은 그 Transaction 안에서 단순 계산/판단 객체로 실행되면 됩니다.

```text
TransactionTemplate / @Transactional
┌──────────────────────────────────────────┐
│                                          │
│ Service                                  │
│    │                                     │
│    ├─ Rule                               │
│    ├─ DAO → SQL                          │
│    ├─ Rule                               │
│    └─ DAO → SQL                          │
│                                          │
└──────────────────────────────────────────┘
        ↓
 COMMIT / ROLLBACK
```

즉 **Rule은 Transaction Owner가 아닙니다.**

---

# 13. 전체 컴포넌트에서 Rule 위치

최종적으로 PDMG 업무 아키텍처를 다음과 같이 잡으면 가장 명확합니다.

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
    │
    ├──────────────── Rule
    │                  │
    │                  ├─ Validation
    │                  ├─ Decision
    │                  ├─ Calculation
    │                  ├─ Normalize
    │                  └─ Policy
    │
    ├──────────────── DAO
    │                  ↓
    │                Mapper
    │                  ↓
    │                  DB
    │
    └──────────────── Integration
                       ↓
                    HTTP/EAI
```

각 계층을 한 문장으로 정리하면:

| 계층        | 한 문장 정의               |
| ----------- | -------------------------- |
| Controller  | HTTP 요청을 받는다         |
| Dispatcher  | ServiceId로 거래를 찾는다  |
| Handler     | 거래를 Use Case에 연결한다 |
| Facade      | 거래 경계를 만든다         |
| **Service** | **업무 절차를 수행한다**   |
| **Rule**    | **업무 판단을 수행한다**   |
| DAO         | DB 접근을 정의한다         |
| Mapper      | SQL을 실행한다             |
| Integration | 외부 시스템을 호출한다     |

### PDMG Rule 아키텍처의 핵심 원칙

```text
              Service
                 │
      ┌──────────┼──────────┐
      ▼          ▼          ▼
    Rule        DAO      Integration
      │
      │
      └─ Pure Business Logic

Service = FLOW
Rule    = DECISION
DAO     = DATA
Client  = EXTERNAL
```

따라서 현재 PDMG는 **`Service 안에 Rule이 포함된 AS-IS`**이고, 앞으로는 **`Service와 Rule을 분리하는 TO-BE`**가 적절합니다. 특히 `mgcoa9001Service`처럼 검증·정규화·상태판정이 많은 프로그램부터 Rule을 분리하면 효과가 가장 큽니다.
