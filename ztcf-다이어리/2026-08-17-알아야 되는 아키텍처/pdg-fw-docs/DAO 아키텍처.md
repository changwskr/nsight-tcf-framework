# DAO 아키텍처 구조

현재 **PDMG 실제 소스 기준**에서 DAO는 단순히 “SQL을 호출하는 클래스”가 아니라, **업무 Service와 MyBatis/DB 사이의 데이터 접근 경계**입니다.

현재 `pdmg-service`에서는 별도의 Repository 구현체 계층을 두기보다 **DAO 인터페이스가 MyBatis Mapper Interface 역할까지 함께 수행하는 구조**입니다.

## 1. 전체 DAO 아키텍처

```text
                   PDMG ONLINE TRANSACTION
──────────────────────────────────────────────────

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
Service
   │
   │ 업무 판단 / 처리순서
   │
   ▼
┌───────────────────────────────────┐
│              DAO                  │
│                                   │
│  DB 접근 Interface               │
│  SQL ID 계약                     │
│  Parameter / Result 계약         │
└─────────────────┬─────────────────┘
                  │
                  │ MyBatis
                  ▼
┌───────────────────────────────────┐
│           Mapper XML              │
│                                   │
│ namespace = DAO FQCN              │
│ id        = DAO Method            │
│                                   │
│ SELECT / INSERT / UPDATE / DELETE │
└─────────────────┬─────────────────┘
                  │
                  ▼
                JDBC
                  │
                  ▼
               HikariCP
                  │
                  ▼
               RDW DB
```

가장 짧게 표현하면:

```text
Service
   ↓
DAO
   ↓
MyBatis
   ↓
Mapper XML
   ↓
SQL
   ↓
DB
```

입니다. 현재 패키지 기준에서도 `application.service → persistence.dao → Mapper XML → DB`가 명확한 하향 의존관계로 정의되어 있습니다.

---

## 2. PDMG에서 DAO가 위치하는 곳

현재 `pdmg-service`의 AS-IS 구조는 다음과 같습니다.

```text
pdmg-service
└─ src/main/java
   └─ nhnis
      └─ mg
         └─ co
            └─ a
               │
               ├─ application
               │   ├─ facade
               │   └─ service
               │
               └─ persistence
                   └─ dao
                       ├─ mgcoa5530DAO
                       ├─ mgcoa8888DAO
                       ├─ mgcoa9000DAO
                       └─ mgcoa9001DAO
```

Mapper SQL은 Java 패키지가 아니라 리소스 영역에 있습니다.

```text
src/main/resources
└─ rdw.mg.co.a
   ├─ mgcoa5530-ORA.xml
   ├─ mgcoa8888-ORA.xml
   ├─ mgcoa9000-ORA.xml
   └─ mgcoa9001-ORA.xml
```

즉 같은 업무분류 축을 공유합니다.

```text
              MG / CO / A
                  │
        ┌─────────┴─────────┐
        │                   │
        ▼                   ▼

Java Package            Mapper Resource

nhnis.mg.co.a           rdw.mg.co.a
        │                   │
        ▼                   ▼
persistence.dao       *-ORA.xml
```

이 구조가 현재 PDMG 패키지 아키텍처의 핵심입니다.

---

# 3. 실제 프로그램 하나로 보면

거래통제 프로그램 `mgcoa9001`을 예로 들면:

```text
ServiceId
mgcoa9001S0
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
SQL
      │
      ▼
TB_MG_TX_CONTROL
```

즉 ServiceId 하나가 최종 DB Table까지 추적됩니다.

```text
mgcoa9001S0
     ↓
Handler
     ↓
Facade
     ↓
Service
     ↓
DAO
     ↓
Mapper SQL ID
     ↓
Table
```

이 관계는 향후 Architecture Model에서도 중요한 추적성 축으로 삼는 것이 좋습니다.

---

# 4. 현재 DAO는 MyBatis Mapper Interface 역할을 같이 한다

현재 소스의 DAO 형태는 개념적으로 다음과 같습니다.

```java
@RDWMapper
public interface mgcoa9001DAO {

    List<Map<String, Object>> mgcoa9001S0_S0(
        Map<String, Object> params
    );

    int mgcoa9001S0_S0_count(
        Map<String, Object> params
    );

    int mgcoa9001C0_C0(
        Map<String, Object> params
    );

    int mgcoa9001U0_U0(
        Map<String, Object> params
    );

    int mgcoa9001D0_D0(
        Map<String, Object> params
    );
}
```

즉 일반적인:

```text
Service
  ↓
DAO Implementation
  ↓
Mapper Interface
  ↓
Mapper XML
```

처럼 2~3개의 Java 계층을 두지 않고 현재 PDMG는 단순화되어 있습니다.

```text
Service
  ↓
DAO Interface
  ↓
MyBatis Proxy
  ↓
Mapper XML
```

Spring/MyBatis가 실행 시 DAO 인터페이스의 Proxy를 만들어 줍니다.

---

# 5. DAO와 Mapper XML 연결 구조

DAO:

```text
nhnis.mg.co.a.persistence.dao.mgcoa9001DAO
```

Mapper:

```text
rdw.mg.co.a/mgcoa9001-ORA.xml
```

Mapper XML의 namespace가 DAO의 FQCN과 연결됩니다.

```xml
<mapper namespace=
 "nhnis.mg.co.a.persistence.dao.mgcoa9001DAO">

    <select id="mgcoa9001S0_S0">
        ...
    </select>

</mapper>
```

따라서 연결관계는:

```text
DAO Interface
nhnis.mg.co.a.persistence.dao.mgcoa9001DAO
              │
              │ namespace
              ▼
Mapper XML
mgcoa9001-ORA.xml
              │
              │ id
              ▼
<select id="mgcoa9001S0_S0">
              │
              ▼
SQL
```

입니다.

---

# 6. DAO Method와 SQL ID도 맞춰야 한다

현재 구조에서는 다음 관계를 유지하는 것이 중요합니다.

```text
DAO Method
mgcoa9001S0_S0()

       =

Mapper SQL ID
<select id="mgcoa9001S0_S0">
```

예를 들어 조회 거래:

```text
ServiceId
mgcoa9001S0
     │
     ▼
DAO Method
mgcoa9001S0_S0()
     │
     ▼
SQL ID
mgcoa9001S0_S0
     │
     ▼
SELECT
```

등록:

```text
mgcoa9001C0
      ↓
mgcoa9001C0_C0()
      ↓
<insert id="mgcoa9001C0_C0">
      ↓
INSERT
```

수정:

```text
mgcoa9001U0
      ↓
mgcoa9001U0_U0()
      ↓
UPDATE
```

삭제:

```text
mgcoa9001D0
      ↓
mgcoa9001D0_D0()
      ↓
DELETE
```

즉 DAO 메서드명 자체도 ServiceId 기반 추적성을 유지합니다.

---

# 7. DAO의 책임은 어디까지인가

DAO의 책임은 좁고 명확해야 합니다.

| DAO가 해야 하는 일    |  여부 |
| --------------------- | ----: |
| SELECT 실행           |     O |
| INSERT 실행           |     O |
| UPDATE 실행           |     O |
| DELETE 실행           |     O |
| SQL Parameter 전달    |     O |
| SQL Result 반환       |     O |
| DB 접근 추상화        |     O |
| ServiceId Routing     | **X** |
| 업무 Rule 판단        | **X** |
| 화면 분기             | **X** |
| Transaction 시작/종료 | **X** |
| HTTP 처리             | **X** |
| JWT 처리              | **X** |
| Timeout 정책 결정     | **X** |

현재 자료에서도 DAO는 **SQL 실행 책임만 가지며 비즈니스 if, HTTP, Transaction 통제를 두지 않는 구조**로 정리되어 있습니다.

---

# 8. Service와 DAO 책임 분리가 중요하다

예를 들어 고객조회가 있다고 하면 잘못된 DAO는:

```java
if (customerType.equals("VIP")) {

    if (branchCode.equals("001")) {
        ...
    }
}
```

처럼 업무를 판단합니다.

이것은 DAO의 책임이 아닙니다.

권장 구조:

```text
Service

입력 검증
    ↓
업무 규칙 판단
    ↓
조회 조건 생성
    ↓
DAO 호출
    ↓
조회 결과 해석
    ↓
Response DTO 생성


DAO

조회조건 받음
    ↓
SQL 실행
    ↓
결과 반환
```

따라서:

```text
Service
= 무엇을 조회해야 하는가 판단

DAO
= 요청받은 데이터를 DB에서 가져옴
```

입니다.

---

# 9. 조회의 대표적인 구조

PDMG에서 목록조회는 대략 다음 형태가 적절합니다.

```text
Service
   │
   ├─ 입력값 검증
   ├─ 조회조건 생성
   │
   ├──────────────┐
   ▼              ▼
DAO Count      DAO List
   │              │
   ▼              ▼
COUNT SQL       SELECT SQL
   │              │
   └──────┬───────┘
          ▼
      Paging 계산
          │
          ▼
      Response DTO
```

현재 통합 자료에서도 Service가 `DTO → 조회조건`, 목록/count 호출, 결과 DTO 조립을 담당하고 DAO는 SQL 실행에 집중하도록 분리하고 있습니다.

---

# 10. Transaction은 DAO가 시작하지 않는다

이 부분은 PDMG에서 특히 중요합니다.

TCF ON + Timeout ON 기준:

```text
Request Thread
     ↓
Timeout Executor
     ↓
Worker Thread
     ↓
TransactionTemplate
     ↓
=====================
      TX BEGIN
=====================
     ↓
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
     ↓
=====================
COMMIT / ROLLBACK
=====================
```

즉 DAO는 **이미 시작된 Transaction 안에서 SQL을 실행하는 계층**입니다.

```text
DAO
= TX Owner        X

DAO
= TX Participant  O
```

따라서 DAO에:

```java
@Transactional
public ...
```

를 붙여 Transaction 경계를 새로 만드는 방식은 기본 구조로 권장하지 않습니다.

PDMG의 현재 Timeout 구조에서는 외부 `TransactionTemplate`이 Physical TX를 먼저 생성하고 업무 계층이 그 TX에 참여하는 구조로 분석되어 있습니다.

---

# 11. DAO와 HikariCP/JDBC의 관계

DAO가 직접 Connection을 관리하는 것은 아닙니다.

```text
DAO Method
   ↓
MyBatis
   ↓
SqlSession
   ↓
Spring Transaction Manager
   ↓
DataSource
   ↓
HikariCP
   ↓
JDBC Connection
   ↓
Oracle / RDW
```

따라서 다음처럼 하면 안 됩니다.

```java
Connection connection =
    DriverManager.getConnection(...);
```

또는:

```java
connection.commit();
connection.rollback();
connection.close();
```

를 DAO가 직접 수행하는 것도 기본 구조에 맞지 않습니다.

Connection/TX 관리는 Framework/Spring이 담당하고 DAO는 SQL 실행에 집중합니다.

---

# 12. DataSource가 여러 개라면

PDMG는 RDW 중심 데이터 접근 구조이므로 기본적으로:

```text
Service
   ↓
DAO
   ↓
@RDWMapper
   ↓
RDW SqlSessionFactory
   ↓
RDW TransactionManager
   ↓
RDW DataSource
```

와 같이 연결됩니다.

구성 영역에서는 DataSource, TransactionManager, MyBatis, Mapper Scan을 관리하도록 되어 있습니다.

```text
config
 ├─ rdwDataSource
 ├─ rdwTransactionManager
 ├─ SqlSessionFactory
 ├─ SqlSessionTemplate
 └─ MapperScan
```

따라서 향후 ADW까지 사용한다면 개념적으로:

```text
                  Service
                     │
            ┌────────┴─────────┐
            │                  │
            ▼                  ▼
         RDW DAO            ADW DAO
            │                  │
            ▼                  ▼
      RDW Mapper         ADW Mapper
            │                  │
            ▼                  ▼
          RDW DB             ADW DB
```

처럼 **DB/DataSource 경계를 명시적으로 나누는 편이 좋습니다.**

---

# 13. 외부 시스템 호출은 DAO가 아니다

이것도 중요한 Architecture Rule입니다.

```text
Service
  │
  ├──── DAO ───────→ DB
  │
  └──── Client ─────→ External System
```

따라서:

```text
DAO
= Database Access

Client / Adapter
= HTTP / EAI / External Integration
```

입니다. 현재 패키지 기준에서도 `client`와 `persistence.dao`를 별도의 하위 책임으로 분리하고 있습니다.

다음 구조는 피해야 합니다.

```text
CustomerDAO
   ├─ Oracle SELECT
   └─ 외부 REST API 호출       X
```

---

# 14. 권장 DAO 아키텍처 Rule

향후 Architecture Gate에서는 최소한 다음 규칙을 자동 검증하는 것이 좋습니다.

```text
R-DAO-001
Controller → DAO 직접 호출 금지

R-DAO-002
Handler → DAO 직접 호출 금지

R-DAO-003
Facade → DAO 직접 호출은 기본 금지
Service 경유

R-DAO-004
DAO → Service 역참조 금지

R-DAO-005
DAO → Handler / Controller 역참조 금지

R-DAO-006
DAO에서 @Transactional로 거래 경계 생성 금지

R-DAO-007
DAO Method ↔ Mapper SQL ID 일치

R-DAO-008
Mapper namespace = DAO FQCN

R-DAO-009
DAO에서 ServiceId 분기 금지

R-DAO-010
DAO에서 업무 Rule 수행 금지

R-DAO-011
외부 API/EAI 호출 금지

R-DAO-012
DAO가 Connection commit/rollback 직접 수행 금지
```

---

# 15. AS-IS와 TO-BE를 구분하면

### 현재 PDMG AS-IS

```text
Service
   ↓
DAO Interface
   ↓
MyBatis Proxy
   ↓
Mapper XML
   ↓
SQL
   ↓
DB
```

DAO가 MyBatis Mapper 인터페이스 역할을 동시에 수행합니다.

### 향후 복잡도가 커졌을 때 가능한 TO-BE

```text
Service
   │
   ▼
Repository / DAO Port
   │
   ▼
Persistence Adapter
   │
   ▼
MyBatis Mapper
   │
   ▼
Mapper XML
   │
   ▼
DB
```

다만 **현재 PDMG에 이 계층이 이미 있다고 설명하면 안 됩니다.** 현재 기준으로는 오히려 DAO와 Mapper Interface를 하나로 유지한 구조가 단순하고 명확합니다.

---

# 16. DAO를 이해하는 가장 쉬운 방법

DAO를 **“DB 창구”**라고 생각하면 됩니다.

```text
Service

"거래통제 정책 목록을
 이 조건으로 찾아줘"

        ↓

DAO

"알겠습니다.
이 SQL을 실행하겠습니다."

        ↓

Mapper

SELECT ...
FROM TB_MG_TX_CONTROL

        ↓

DB

결과 반환
```

DAO는 다음 질문에 답하지 않습니다.

```text
"이 고객은 VIP인가?"
"이 거래를 허용할까?"
"이 ServiceId를 실행할까?"
"Timeout은 몇 초인가?"
```

그것은 Service/Rule/TCF의 책임입니다.

DAO의 질문은 오직:

```text
"어떤 데이터를
어떤 SQL로
DB에서 읽거나 변경할 것인가?"
```

입니다.

---

## 최종 구조

```text
             ServiceId
                 │
                 ▼
              Handler
                 │
                 ▼
              Facade
                 │
                 ▼
              Service
        업무 판단 / 조립
                 │
                 ▼
       ┌──────────────────┐
       │       DAO        │
       │ Data Access Only │
       └────────┬─────────┘
                │
         MyBatis Proxy
                │
                ▼
       ┌──────────────────┐
       │    Mapper XML    │
       │ namespace / id   │
       └────────┬─────────┘
                │
                ▼
               SQL
                │
                ▼
             HikariCP
                │
                ▼
              JDBC
                │
                ▼
             RDW / DB
```

**PDMG DAO 아키텍처의 핵심 원칙은 `Service가 업무를 판단하고, DAO는 데이터 접근만 담당하며, Mapper가 SQL을 소유한다`입니다.** 그리고 `ServiceId → Service → DAO → Mapper SQL → Table`까지 추적 가능하도록 네이밍과 구조를 유지하는 것이 중요합니다.
