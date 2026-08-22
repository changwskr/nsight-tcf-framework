네. **“자바 표준 네이밍 구조 + 표준 주석 규칙”**으로 잡는다면, PDMG의 업무코드/ServiceId 체계는 유지하되 **Java 언어 표준 관례와 충돌하는 부분은 분리**하는 것이 좋습니다.

현재 PDMG에서는 `mgcoa9000Handler`, `mgcoa9000Facade`처럼 클래스명의 첫 글자가 소문자인 AS-IS가 존재하지만, Java 표준 관례에서는 클래스는 `UpperCamelCase`, 메서드·변수는 `lowerCamelCase`, 상수는 `UPPER_SNAKE_CASE`가 적절합니다. 현재 PDMG 네이밍은 ServiceId → 패키지 → Handler/Facade/Service/DAO/Mapper까지 추적성을 제공하는 구조입니다.

## 1. 권장 Java 네이밍 전체 구조

| 대상       | 표준                       | 예                                  |
| ---------- | -------------------------- | ----------------------------------- |
| Package    | 모두 소문자                | `nhnis.mg.co.a.application.service` |
| Class      | UpperCamelCase             | `Mgcoa9000Service`                  |
| Interface  | UpperCamelCase             | `CustomerRepository`                |
| Method     | lowerCamelCase             | `selectCustomerList()`              |
| Variable   | lowerCamelCase             | `customerList`                      |
| Constant   | UPPER_SNAKE_CASE           | `DEFAULT_PAGE_SIZE`                 |
| Enum       | UpperCamelCase             | `TransactionType`                   |
| Enum 값    | UPPER_SNAKE_CASE           | `SELECT`, `CREATE`                  |
| DTO Class  | UpperCamelCase             | `Mgcoa9000S0Request`                |
| Exception  | UpperCamelCase + Exception | `TransactionControlException`       |
| Boolean    | `is/has/can/should`        | `isActive`, `hasPermission`         |
| Collection | 복수형                     | `customers`, `serviceIds`           |

---

# 2. PDMG 업무코드를 적용한 권장 구조

현재 업무 식별체계:

```text
MG + CO + A + 9000
        ↓
Program ID

mgcoa9000
```

ServiceId:

```text
mgcoa9000S0
mgcoa9000C0
mgcoa9000U0
mgcoa9000D0
```

를 유지하면서 **Java 클래스명만 Java 표준에 맞추는 방법**을 권장합니다.

```text
ServiceId
────────────────────────────
mgcoa9000S0

Program ID
────────────────────────────
mgcoa9000

Java Class
────────────────────────────
Mgcoa9000Handler
Mgcoa9000Facade
Mgcoa9000Service
Mgcoa9000Dao

DTO
────────────────────────────
Mgcoa9000S0Request
Mgcoa9000S0Response

Mapper
────────────────────────────
Mgcoa9000Mapper

Mapper XML
────────────────────────────
mgcoa9000-ORA.xml
```

즉 **외부 Architecture Key인 ServiceId는 기존 규칙을 유지하고 Java Identifier만 Java 관례를 적용**합니다.

---

# 3. 패키지 표준

```java
// 좋은 예
package nhnis.mg.co.a.entry.handler;

package nhnis.mg.co.a.application.facade;

package nhnis.mg.co.a.application.service;

package nhnis.mg.co.a.application.rule;

package nhnis.mg.co.a.persistence.dao;

package nhnis.mg.co.a.dto;
```

패키지는 **무조건 소문자**를 권장합니다.

```java
// 비권장
package nhnis.MG.CO.A;
package nhnis.mg.co.A;
```

PDMG의 실제 업무 패키지도 `nhnis.mg.co.a`라는 업무축을 중심으로 `entry`, `application`, `dto`, `persistence`로 책임을 나누고 있습니다.

---

# 4. Handler 표준 — 주석 포함

```java
package nhnis.mg.co.a.entry.handler;

import org.springframework.stereotype.Component;

/**
 * MG 공통관리 프로그램(9000) 거래 Handler.
 *
 * <p>지원 ServiceId:</p>
 * <ul>
 *     <li>mgcoa9000S0 : 조회</li>
 *     <li>mgcoa9000C0 : 등록</li>
 *     <li>mgcoa9000U0 : 수정</li>
 *     <li>mgcoa9000D0 : 삭제</li>
 * </ul>
 *
 * <p>책임:</p>
 * <ul>
 *     <li>ServiceId에 따른 Use Case 선택</li>
 *     <li>요청 DTO 전달</li>
 *     <li>Facade 호출</li>
 * </ul>
 *
 * <p>금지:</p>
 * <ul>
 *     <li>DAO 직접 호출</li>
 *     <li>SQL 수행</li>
 *     <li>업무 규칙 구현</li>
 * </ul>
 */
@Component
public class Mgcoa9000Handler {

    private static final String SERVICE_SELECT = "mgcoa9000S0";
    private static final String SERVICE_CREATE = "mgcoa9000C0";
    private static final String SERVICE_UPDATE = "mgcoa9000U0";
    private static final String SERVICE_DELETE = "mgcoa9000D0";

    private final Mgcoa9000Facade facade;

    public Mgcoa9000Handler(Mgcoa9000Facade facade) {
        this.facade = facade;
    }

    /**
     * ServiceId에 해당하는 업무 Use Case를 실행한다.
     *
     * @param serviceId 거래 ServiceId
     * @param request 업무 요청 데이터
     * @return 업무 처리 결과
     */
    public Object handle(String serviceId, Object request) {

        return switch (serviceId) {
            case SERVICE_SELECT -> facade.select(request);
            case SERVICE_CREATE -> facade.create(request);
            case SERVICE_UPDATE -> facade.update(request);
            case SERVICE_DELETE -> facade.delete(request);
            default ->
                throw new IllegalArgumentException(
                    "지원하지 않는 ServiceId: " + serviceId
                );
        };
    }
}
```

중요한 것은 상수를:

```java
private static final String SERVICE_SELECT
```

처럼 작성하는 것입니다.

다음은 Java 표준상 비권장입니다.

```java
private static final String service_select = "...";   // X
private static final String ServiceSelect = "...";    // X
```

---

# 5. Facade 표준

```java
/**
 * MG 공통관리 프로그램(9000)의 Use Case Facade.
 *
 * <p>Handler와 업무 Service 사이의 경계를 담당한다.</p>
 *
 * 주요 책임:
 * - 요청 DTO 변환
 * - Use Case 조립
 * - Transaction 경계 설정
 * - Service 호출
 */
@Service
public class Mgcoa9000Facade {

    private final Mgcoa9000Service service;

    public Mgcoa9000Facade(Mgcoa9000Service service) {
        this.service = service;
    }

    /**
     * 공통관리 목록을 조회한다.
     *
     * @param request 조회 요청
     * @return 조회 결과
     */
    @Transactional(
        transactionManager = "rdwTransactionManager",
        readOnly = true
    )
    public Mgcoa9000S0Response select(
            Mgcoa9000S0Request request) {

        return service.select(request);
    }
}
```

여기서 메서드명은 선택할 수 있습니다.

### 기존 ServiceId 추적성을 최우선한다면

```java
public Mgcoa9000S0Response mgcoa9000S0(...)
```

### Java 가독성을 더 중시한다면

```java
public Mgcoa9000S0Response select(...)
```

저는 **ServiceId를 별도의 상수/Annotation/등록정보로 관리하고 메서드명은 업무 의미로 작성**하는 쪽을 권장합니다.

---

# 6. Service 표준

```java
/**
 * MG 공통관리 프로그램(9000)의 업무 서비스.
 *
 * <p>실제 업무 처리 순서와 업무 규칙을 수행한다.</p>
 */
@Service
public class Mgcoa9000Service {

    private final Mgcoa9000Dao dao;
    private final Mgcoa9000Rule rule;

    public Mgcoa9000Service(
            Mgcoa9000Dao dao,
            Mgcoa9000Rule rule) {

        this.dao = dao;
        this.rule = rule;
    }

    /**
     * 공통관리 데이터를 조회한다.
     *
     * @param request 조회 조건
     * @return 조회 결과
     */
    public Mgcoa9000S0Response select(
            Mgcoa9000S0Request request) {

        // 1. 입력 업무규칙 검증
        rule.validateSearchCondition(request);

        // 2. 데이터 조회
        var rows = dao.selectList(request);

        // 3. 결과 DTO 생성
        return Mgcoa9000S0Response.from(rows);
    }
}
```

주석도 **코드 자체를 읽으면 알 수 있는 내용을 반복해서는 안 됩니다.**

나쁜 주석:

```java
// DAO 호출
dao.selectList(request);
```

좋은 주석:

```java
// 거래통제 이력은 최신 변경순으로 조회하여
// 동일 조건이 중복된 경우 가장 최근 정책을 우선 적용한다.
var rows = dao.selectList(request);
```

즉 **“무엇을 하는지”보다 “왜 그렇게 하는지”를 주석으로 남기는 것**이 좋습니다.

---

# 7. Rule 표준

현재 PDMG AS-IS에서는 독립 `rule` 패키지가 일반화되어 있지 않고 여러 규칙이 Service 안에 있습니다.

TO-BE에서는 다음과 같이 분리하는 것을 권장합니다.

```java
/**
 * MG 공통관리 프로그램의 업무 규칙.
 *
 * 순수 업무 판단을 담당하며
 * 데이터 접근이나 Transaction 제어를 수행하지 않는다.
 */
@Component
public class Mgcoa9000Rule {

    /**
     * 조회조건의 업무 유효성을 검증한다.
     *
     * @param request 조회 요청
     * @throws BizException 업무조건 위반 시
     */
    public void validateSearchCondition(
            Mgcoa9000S0Request request) {

        if (request.pageSize() > 1000) {
            throw new BizException("MP0403");
        }
    }
}
```

---

# 8. DAO 표준

Java 표준 측면에서는 `DAO`보다 `Dao`가 클래스명으로 조금 더 자연스럽습니다.

```java
@Repository
public class Mgcoa9000Dao {

    private final SqlSessionTemplate sqlSession;

    public Mgcoa9000Dao(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    /**
     * 공통관리 목록을 조회한다.
     *
     * @param request 조회조건
     * @return 조회 데이터
     */
    public List<Mgcoa9000Row> selectList(
            Mgcoa9000S0Request request) {

        return sqlSession.selectList(
            "Mgcoa9000Mapper.selectList",
            request
        );
    }
}
```

권장:

```text
Mgcoa9000Dao
```

PDMG 기존 표준과 100% 맞춰야 한다면:

```text
mgcoa9000DAO        ← AS-IS
```

이지만 이는 Java Class Naming 관례와는 차이가 있습니다.

---

# 9. DTO 네이밍도 개선 가능

현재 PDMG 방식:

```text
mgcoa9000S0DTOin
mgcoa9000S0DTOout
```

Java 표준 가독성 관점에서는:

```text
Mgcoa9000S0Request
Mgcoa9000S0Response
```

를 권장합니다.

```java
/**
 * mgcoa9000S0 조회 요청.
 *
 * @param pageNo 페이지 번호
 * @param pageSize 페이지당 조회건수
 * @param keyword 검색어
 */
public record Mgcoa9000S0Request(
        int pageNo,
        int pageSize,
        String keyword) {
}
```

응답:

```java
/**
 * mgcoa9000S0 조회 응답.
 *
 * @param totalCount 전체 건수
 * @param items 조회 결과
 */
public record Mgcoa9000S0Response(
        long totalCount,
        List<Mgcoa9000Item> items) {
}
```

---

# 10. 메서드 네이밍 표준

CRUD 문자보다 **업무 의도를 표현하는 동사**를 사용하는 것이 Java에서는 좋습니다.

```text
조회
find...
get...
select...
search...

등록
create...
register...
save...

수정
update...
change...

삭제
delete...
remove...

검증
validate...
check...

존재확인
exists...

변환
convert...
map...
to...
from...
```

예:

```java
findCustomer()
searchCustomers()
registerCustomer()
updateCustomer()
deleteCustomer()

validateCustomer()
existsCustomer()

calculateLimit()
checkPermission()
```

피해야 할 이름:

```java
process()      // 의미가 너무 넓음
doProcess()
executeJob()
proc()
work()
runLogic()
```

Framework 영역이 아니라 **업무 Service에서는 의미가 드러나는 이름**이 좋습니다.

---

# 11. 변수 네이밍

좋음:

```java
String customerId;
String serviceId;
int pageSize;

List<Customer> customers;
Map<String, ServicePolicy> servicePolicies;

boolean active;
boolean authenticated;
boolean transactionAllowed;
```

더 좋은 Boolean:

```java
boolean isActive;
boolean hasPermission;
boolean canExecute;
boolean shouldRollback;
```

피해야 함:

```java
String str1;
Object obj;
List list;
Map map;
int a;
boolean flag;
```

---

# 12. 표준 JavaDoc 규칙

**Class JavaDoc**

```java
/**
 * 고객 통합조회 업무 서비스.
 *
 * <p>고객 기본정보와 계좌정보를 조합하여
 * Single View를 생성한다.</p>
 *
 * <p>Transaction Boundary는 Facade에서 관리한다.</p>
 */
public class CustomerService {
}
```

**Method JavaDoc**

```java
/**
 * 고객 통합정보를 조회한다.
 *
 * @param customerId 고객 식별번호
 * @return 고객 통합정보
 * @throws CustomerNotFoundException 고객이 존재하지 않는 경우
 */
public CustomerSummary findCustomerSummary(
        String customerId) {
}
```

**상수 주석**

```java
/** 기본 페이지당 조회 건수. */
private static final int DEFAULT_PAGE_SIZE = 20;

/** 한 번에 조회할 수 있는 최대 건수. */
private static final int MAX_PAGE_SIZE = 1000;
```

---

# 13. NSIGHT/PDMG에 권장하는 최종형

```text
ServiceId
mgcoa9000S0
        │
        ▼
Mgcoa9000Handler
        │
        ▼
Mgcoa9000Facade
        │
        ▼
Mgcoa9000Service
     ┌──┴────────────┐
     ▼               ▼
Mgcoa9000Rule    Mgcoa9000Dao
                     │
                     ▼
             Mgcoa9000Mapper
                     │
                     ▼
                Mapper XML
                     │
                     ▼
                    DB
```

패키지는:

```text
nhnis.mg.co.a
│
├─ entry
│   └─ handler
│       └─ Mgcoa9000Handler
│
├─ application
│   ├─ facade
│   │   └─ Mgcoa9000Facade
│   ├─ service
│   │   └─ Mgcoa9000Service
│   └─ rule
│       └─ Mgcoa9000Rule
│
├─ dto
│   ├─ Mgcoa9000S0Request
│   └─ Mgcoa9000S0Response
│
└─ persistence
    ├─ dao
    │   └─ Mgcoa9000Dao
    └─ mapper
        └─ Mgcoa9000Mapper
```

### 가장 중요한 기준

```text
Package       → lowercase
Class         → UpperCamelCase
Method        → lowerCamelCase
Variable      → lowerCamelCase
Constant      → UPPER_SNAKE_CASE
ServiceId     → 프로젝트 표준 유지
DB Object     → 프로젝트 DB 표준 유지
```

즉 **`ServiceId/업무코드의 PDMG 추적성 규칙`과 `Java 언어 네이밍 표준`을 서로 구분해서 적용하는 구조**가 가장 좋습니다. 현재 PDMG의 네이밍 체계가 ServiceId 중심 추적성에는 강점이 있으므로 이를 없애는 것이 아니라, Java 식별자 부분만 표준화하는 방향입니다.
