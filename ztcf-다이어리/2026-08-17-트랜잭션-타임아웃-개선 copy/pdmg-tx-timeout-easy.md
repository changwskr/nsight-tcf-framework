물론입니다. 초보자 관점에서는 **트랜잭션 관리 = “업무를 하나의 묶음으로 보고, 끝까지 성공하면 저장하고 중간에 문제가 생기면 전부 취소하는 것”**이라고 이해하면 됩니다.

은행 업무로 예를 들어보겠습니다.

계좌이체를 한다고 하면 실제로는 여러 작업이 있습니다.

```text
1. A계좌 잔액 확인
2. A계좌에서 10만원 차감
3. B계좌에 10만원 증가
4. 이체 내역 저장
```

이 네 작업 중 하나라도 실패하면 어떻게 해야 할까요?

예를 들어:

```text
A계좌 -10만원       성공
B계좌 +10만원       실패
```

인데 A계좌 차감만 저장되면 큰 문제가 생깁니다.

그래서 트랜잭션을 사용합니다.

```text
TX BEGIN

A계좌 -10만원
B계좌 +10만원
이체내역 저장

모두 성공
     ↓
COMMIT

하나라도 실패
     ↓
ROLLBACK
```

`COMMIT`은 **“지금까지 한 작업을 확정 저장”**, `ROLLBACK`은 **“이번 거래에서 한 DB 변경을 모두 취소”**라고 이해하면 됩니다.

---

## PDMG에서는 어디서 트랜잭션을 시작하느냐

현재 우리가 이야기한 PDMG 구조에서는 Timeout을 사용하는 경우 대략 이렇게 됩니다.

```text
사용자 요청
   ↓
TCF
   ↓
Timeout Executor
   ↓
Worker Thread
   ↓
TransactionTemplate
   ↓
========================
   TX BEGIN
========================
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
   ↓
========================
성공 → COMMIT
실패 → ROLLBACK
========================
```

여기서 가장 중요한 부분은 이것입니다.

```text
TransactionTemplate
        ↓
TX BEGIN
```

즉 **PDMG Framework가 업무 프로그램을 호출하기 전에 먼저 트랜잭션을 시작합니다.**

그리고 업무가 전부 끝난 뒤:

```text
정상
 ↓
COMMIT
```

하거나:

```text
예외 발생
 ↓
ROLLBACK
```

합니다.

---

# `@Transactional`은 무엇인가

개발자가 Facade에 다음처럼 작성했다고 해보겠습니다.

```java
@Transactional
public void transfer() {
    ...
}
```

기본 설정은:

```text
Propagation.REQUIRED
```

입니다.

쉽게 말하면:

> "이미 진행 중인 트랜잭션이 있으면 거기에 같이 들어가고, 없으면 내가 새로 만들겠다."

라는 뜻입니다.

PDMG에서는 이미 바깥에서 `TransactionTemplate`이 TX를 시작했습니다.

```text
TransactionTemplate
      ↓
TX 시작됨
      ↓
Handler
      ↓
Facade
@Transactional(REQUIRED)
```

Facade 입장에서는:

```text
"어? 이미 트랜잭션이 있네."
        ↓
"그럼 새로 만들지 않고 여기에 참여할게."
```

가 됩니다.

그래서 실제 DB Transaction은 하나입니다.

```text
하나의 Physical Transaction
──────────────────────────────

TransactionTemplate

   ├─ Handler
   │
   ├─ Facade
   │   @Transactional(REQUIRED)
   │
   ├─ Service
   │
   ├─ DAO
   │
   └─ Mapper / SQL

──────────────────────────────
             ↓
         COMMIT/ROLLBACK
```

이 구조에서는 **최종 Commit/Rollback의 주인은 바깥쪽 `TransactionTemplate`**이라고 보면 됩니다.

---

# 그런데 Timeout은 왜 필요한가

예를 들어 어떤 거래가 5초 안에 끝나야 한다고 합시다.

```text
서비스 허용시간 = 5초
```

그런데 SQL이 30초 걸린다면 사용자가 계속 기다리게 됩니다.

그래서 PDMG에서는 거래마다 제한시간을 둡니다.

```text
요청 시작
   ↓
5초 안에 끝나야 함
   ↓
5초 초과
   ↓
TIMEOUT
```

하지만 여기서 초보자가 가장 많이 헷갈리는 부분이 있습니다.

**사용자에게 5초 후 Timeout을 반환하는 것과 DB 작업이 5초에 실제로 멈추는 것은 다른 문제입니다.**

예를 들어 현재 단순한 구조라면:

```text
Request Thread
     ↓
5초 기다림
     ↓
TIMEOUT 응답

하지만

Worker Thread
     ↓
SQL이 아직 실행 중
```

일 수 있습니다.

그래서 타임아웃을 여러 단계에서 관리해야 합니다.

---

# PDMG에서는 3종류의 시간을 관리한다고 생각하면 쉽습니다

가장 쉽게 이해하려면 다음 세 가지로 구분하면 됩니다.

| 종류                | 의미                          | 담당                   |
| ------------------- | ----------------------------- | ---------------------- |
| 전체 거래 시간      | 사용자가 최대 몇 초 기다릴지  | PDMG Deadline / Future |
| DB Transaction 시간 | TX를 얼마나 오래 유지할지     | TransactionTemplate    |
| SQL 실행 시간       | SQL 하나를 몇 초까지 실행할지 | JDBC                   |

예를 들어 전체 거래가 5초라면:

```text
전체 거래 제한
5초
 │
 ├─ Queue 대기
 │
 ├─ Handler
 │
 ├─ Service
 │
 └─ DB
```

이 모든 시간이 합쳐서 5초 안에 끝나야 합니다.

---

# 그런데 Worker가 바로 실행된다는 보장은 없습니다

예를 들어:

```text
전체 Timeout = 5초

요청
 ↓
Worker Queue에서 2초 대기
 ↓
Worker 실행 시작
```

그러면 이제 남은 시간은:

```text
5초 - 2초 = 3초
```

입니다.

여기서 다시 DB에 5초를 주면 안 됩니다.

잘못된 방식:

```text
전체 제한        5초

Queue            2초
DB Transaction   다시 5초
────────────────────
최대             7초
```

우리가 개선하려는 방식은:

```text
전체 제한       5초

Queue 사용      2초
────────────────
남은 시간       3초

Transaction     최대 3초
SQL             최대 3초 이하
```

입니다.

이것을 **Remaining Budget**, 즉 "남아 있는 시간 예산"이라고 합니다.

---

# TransactionTemplate의 timeout은 무슨 역할인가

Worker가 시작됐을 때 3초가 남았다고 하겠습니다.

그러면 Framework가:

```java
transactionTemplate.setTimeout(3);
```

같은 식으로 설정합니다.

개념적으로:

```text
Worker 시작
   ↓
남은 시간 계산
3초
   ↓
TransactionTemplate
timeout = 3초
   ↓
TX BEGIN
```

이 됩니다.

즉:

> "이 DB 트랜잭션은 이제 3초 이상 끌면 안 돼."

라는 기준을 주는 것입니다.

---

# 그런데 이것만으로 SQL이 바로 멈추는 것은 아니다

여기서 JDBC가 등장합니다.

DB에:

```sql
SELECT ...
```

를 실행했는데 SQL 자체가 20초 걸린다고 하겠습니다.

그래서 JDBC Statement에도 timeout을 줍니다.

```java
statement.setQueryTimeout(3);
```

그러면:

```text
SQL 실행
 ↓
3초 초과
 ↓
JDBC Driver
 ↓
Statement.cancel()
 ↓
DB SQL 취소 요청
 ↓
예외 발생
 ↓
Transaction ROLLBACK
```

하게 됩니다.

즉 역할을 다시 보면:

```text
PDMG Deadline
"전체 거래 5초"

        ↓

TransactionTemplate
"DB TX는 남은 3초"

        ↓

JDBC QueryTimeout
"SQL도 3초 이상 하지 마"

        ↓

Oracle DB
"시간 초과 → SQL 취소"

        ↓

ROLLBACK
```

입니다.

---

# `Future.cancel(true)`는 그러면 왜 필요한가

이것은 Worker에게:

> "이 거래는 이미 시간이 끝났으니 그만해야 한다."

라고 알려주는 역할입니다.

```text
Request Thread
     ↓
Timeout 발견
     ↓
future.cancel(true)
     ↓
Worker Thread에 interrupt 요청
```

하지만 이것만 믿으면 안 됩니다.

왜냐하면:

```text
Thread interrupt
```

를 JDBC Driver나 DB가 반드시 즉시 처리한다는 보장이 없기 때문입니다.

그래서:

```text
Future cancel
      +
Transaction timeout
      +
JDBC query timeout
```

을 같이 사용하는 것입니다.

---

# 초보자용으로 자동차에 비유하면

PDMG의 타임아웃 관리를 자동차로 생각하면 아주 쉽습니다.

```text
전체 여행시간 5시간
        │
        │
        ├─ 전체 Deadline
        │
        ▼
"5시간 안에 목적지에 도착해야 함"

        │
        ├─ Transaction Timeout
        │
        ▼
"이 구간은 최대 3시간만 사용"

        │
        ├─ JDBC QueryTimeout
        │
        ▼
"이 터널에서 최대 30분 이상 기다리지 않음"

        │
        └─ Future.cancel
                ↓
        "전체 시간이 끝났으니 운행 중단 요청"
```

하나만 있으면 부족합니다.

---

# PDMG에서 최종적으로 원하는 구조

전체 흐름을 정말 간단히 그리면 다음입니다.

```text
사용자 요청
   │
   ▼
ServiceId
   │
   │ Timeout = 5초
   ▼
Deadline 생성
   │
   ▼
Worker Queue
   │
   │ 1초 소비
   ▼
Worker 시작
   │
   │ 남은 시간 = 4초
   ▼
TransactionTemplate
   │ timeout = 4초
   │
   ├──── TX BEGIN ────────────────┐
   │                              │
   ▼                              │
Handler                           │
   ▼                              │
Facade                            │
@Transactional(REQUIRED)          │
   │                              │
   │ 기존 TX에 참여               │
   ▼                              │
Service                           │
   ▼                              │
DAO                               │
   ▼                              │
MyBatis                           │
   ▼                              │
JDBC                              │
queryTimeout <= 4초               │
   ▼                              │
DB                                │
   │                              │
   ├─ 성공 ───────────────────────┤
   │                              ▼
   │                           COMMIT
   │
   └─ Timeout / 오류
          ↓
       Exception
          ↓
       ROLLBACK
```

---

## 한 문장씩만 기억한다면

초보자에게는 이 여섯 가지만 기억하면 됩니다.

1. **Transaction은 여러 DB 작업을 하나의 묶음으로 만든다.**
2. **모두 성공하면 COMMIT, 하나라도 실패하면 ROLLBACK한다.**
3. **PDMG에서는 Worker 안의 TransactionTemplate이 가장 바깥 Physical Transaction을 만든다.**
4. **Facade의 `@Transactional(REQUIRED)`는 이미 만들어진 그 Transaction에 참여한다.**
5. **전체 거래 Timeout과 DB Transaction Timeout, SQL Timeout은 서로 다른 개념이다.**
6. **PDMG 개선의 핵심은 하나의 ServiceId Deadline에서 남은 시간을 계산해 Transaction과 SQL까지 전달하는 것이다.**

가장 중요한 그림 하나만 기억한다면 이것입니다.

```text
ServiceId
   │
   │ 5초
   ▼
전체 Deadline
   │
   ▼
남은시간 계산
   │
   ▼
TransactionTemplate
   │
   │ Physical TX
   ▼
@Transactional(REQUIRED)
   │
   │ 자동 참여
   ▼
DAO / SQL
   │
   │ JDBC QueryTimeout
   ▼
DB

성공     → COMMIT
오류     → ROLLBACK
시간초과 → SQL 취소 → ROLLBACK
```

즉 **PDMG 트랜잭션 관리의 핵심은 “하나의 거래 시간을 정하고, 그 시간 안에서 Physical Transaction과 SQL 실행까지 함께 통제하는 것”**이라고 이해하면 가장 쉽습니다.
