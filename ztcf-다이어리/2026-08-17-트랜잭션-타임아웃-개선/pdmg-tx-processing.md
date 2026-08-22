# PDMG `@Transactional(REQUIRED)` — Physical TX 참여 조건

| 항목 | 값 |
| --- | --- |
| audience | Facade/Service 개발자 |
| prerequisite | [pdmg-tx-timeout-easy.md](./pdmg-tx-timeout-easy.md) |
| implementation | [timeout-tx-mg-guide.md](./timeout-tx-mg-guide.md) |
| 핵심 결론 | Timeout ON 경로에서 **Physical TX Owner = Worker의 `TransactionTemplate`** |

---

네. **PDMG의 현재 구조라면 `@Transactional`이 기본 `REQUIRED`이고 같은 `TransactionManager`, 같은 Worker Thread에서 호출된다면, 이미 `TransactionTemplate`이 시작한 외부 Physical Transaction에 자동 참여합니다.** Spring의 `PROPAGATION_REQUIRED`는 현재 트랜잭션이 있으면 참여하고, 없으면 새로 만드는 것이 기본 동작입니다.

PDMG 구조에 그대로 대입하면 다음입니다.

```text
Worker Thread
    │
    ▼
TransactionTemplate.execute()
    │
    │  rdwTransactionManager
    │
    ├──── PHYSICAL TX BEGIN ────────────────┐
    │                                       │
    ▼                                       │
Dispatcher                                  │
    ▼                                       │
Handler                                     │
    ▼                                       │
Facade                                      │
@Transactional                              │
propagation = REQUIRED                      │
transactionManager = rdwTransactionManager  │
    │                                       │
    │  기존 TX 발견                         │
    │  → 새로운 Physical TX 생성 안 함       │
    │  → 기존 TX에 참여                     │
    ▼                                       │
Service                                     │
    ▼                                       │
DAO → MyBatis → JDBC                        │
    │                                       │
    └───────────────────────────────────────┘
                    │
                    ▼
             COMMIT / ROLLBACK
       TransactionTemplate가 최종 결정
```

### 중요한 것은 `Logical TX`와 `Physical TX`를 구분하는 것입니다

Spring 문서에서는 `PROPAGATION_REQUIRED`의 각 메서드 범위를 **논리적 transaction scope**로 보지만, 여러 REQUIRED scope가 하나의 **동일한 physical transaction**에 매핑될 수 있다고 설명합니다. ([Transaction Propagation](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/tx-propagation.html))

PDMG를 예로 들면:

```text
Physical Transaction #1
─────────────────────────────────────────────

TransactionTemplate
│
├─ Logical TX Scope #1
│
├── Facade @Transactional(REQUIRED)
│      └─ Logical TX Scope #2
│
├── Service @Transactional(REQUIRED)
│      └─ Logical TX Scope #3
│
└── DAO / Mapper / JDBC

─────────────────────────────────────────────
실제 DB Connection / Physical TX는 하나
```

즉 다음처럼 이해하면 정확합니다.

```text
TransactionTemplate
       ↓
TX BEGIN
       ↓
Facade @Transactional(REQUIRED)
       ↓
"아, 이미 TX가 있네"
       ↓
JOIN
       ↓
새로운 DB Transaction 만들지 않음
```

## PDMG 실제 호출 경로 (Timeout ON)

```text
TcfFacade.process()
  → MgActiveTransactionRegistry.begin()
  → stf.preProcess()              # 거래통제 (TxControl)
  → DefaultOnlineTimeoutExecutor
       → Worker + TransactionTemplate
       → Dispatcher → Handler → Facade @Transactional(REQUIRED)
  → finally: registry.end() + etf.postProcess()
```

---

## PDMG에서는 조건이 하나 더 중요합니다: 같은 Worker Thread

Spring의 일반적인 트랜잭션은 **Thread-bound**입니다. 따라서 외부 `TransactionTemplate`과 내부 `@Transactional`이 같은 Worker Thread에서 실행되어야 합니다. ([Spring Batch — Thread-bound TX](https://docs.spring.io/spring-batch/reference/scalability.html))

PDMG의 현재 구조:

```text
Request Thread
    │
    │ submit
    ▼
Worker Thread
    │
    ├─ TransactionTemplate BEGIN
    │
    ├─ Dispatcher
    │
    ├─ Handler
    │
    ├─ Facade @Transactional
    │
    ├─ Service
    │
    └─ DAO
```

에서는 **TransactionTemplate 이후의 호출이 Worker Thread 안에서 계속 이어지므로 정상적으로 참여합니다.**

반대로 중간에:

```text
Worker Thread A
    │
    ├─ TX BEGIN
    │
    ▼
@Async
    │
    ▼
Worker Thread B
```

가 되면 Thread B는 기본적으로 Thread A의 트랜잭션을 이어받지 않습니다.

---

## 같은 TransactionManager여야 합니다

이것도 매우 중요합니다.

예를 들어 외부가:

```java
new TransactionTemplate(rdwTransactionManager)
```

이고 Facade가:

```java
@Transactional(
    transactionManager = "rdwTransactionManager"
)
```

이면:

```text
외부
rdwTransactionManager
        │
        ▼
Facade
rdwTransactionManager
        │
        ▼
같은 Transaction Context 발견
        │
        ▼
JOIN
```

입니다.

그런데 외부가 RDW인데 내부가:

```java
@Transactional(
    transactionManager = "adwTransactionManager"
)
```

이면 단순히 같은 Physical TX에 참여한다고 보면 안 됩니다.

```text
TransactionTemplate
rdwTransactionManager
      ↓
RDW TX

Facade
@Transactional(adwTransactionManager)
      ↓
ADW TransactionManager

≠ 동일 TX
```

따라서 PDMG에서 **TransactionManager 선택 정책이 중요한 이유가 바로 이것**입니다.

---

## 그리고 제가 앞에서 지적한 `readOnly` 문제가 여기서 발생합니다

예를 들어 현재 외부가:

```java
TransactionTemplate tx =
    new TransactionTemplate(rdwTransactionManager);

tx.execute(status -> {
    return dispatcher.dispatch(...);
});
```

이고 Facade가:

```java
@Transactional(
    transactionManager = "rdwTransactionManager",
    readOnly = true
)
public Result inquiry() {
    ...
}
```

라고 합시다.

많이들 이렇게 생각하기 쉽습니다.

```text
TransactionTemplate TX
      ↓
Facade 진입
      ↓
readOnly=true로 TX 변경
```

하지만 **그렇게 되지 않습니다.**

기본적으로 참여 트랜잭션은 외부 트랜잭션의 특성을 따르며, 내부 scope의 isolation, timeout, read-only 설정은 기존 physical transaction에 참여할 때 무시될 수 있습니다. ([Transaction Propagation](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/tx-propagation.html))

따라서 실제로는:

```text
TransactionTemplate
readOnly = false(default)
timeout = default
        │
        ├──── Physical TX #1
        │
        ▼
Facade
@Transactional(
    readOnly=true,
    timeout=3
)
        │
        │ REQUIRED → 기존 TX JOIN
        │
        ▼

Physical TX #1의 특성 유지
```

가 됩니다.

**이 부분 때문에 앞에서 말씀드린 PDMG Transaction Policy 개선이 중요합니다.**

---

## Timeout도 똑같습니다

예를 들어:

```java
@Transactional(timeout = 3)
```

가 Facade에 있어도 이미 외부 `TransactionTemplate`이 Physical TX를 시작했다면, 일반적으로 내부 timeout=3이 그 외부 Physical TX의 timeout을 새로 3초로 바꾸는 구조가 아닙니다. ([Transaction Propagation](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/tx-propagation.html))

따라서 PDMG가 현재:

```text
TransactionTemplate
       ↓
Facade @Transactional(timeout=...)
```

구조라면 **실제 timeout의 주인은 외부 TransactionTemplate 쪽으로 올리는 것이 맞습니다.**

제가 앞에서:

```java
transactionTemplate.setTimeout(remainingSeconds);
```

를 `pdmg-fw`에 넣어야 한다고 한 이유가 이것입니다.

---

# PDMG에서 가장 이상적인 책임 구조

결국 이렇게 가는 것이 깔끔합니다.

```text
ServiceId
   │
   ▼
TransactionPolicy
   │
   ├─ TransactionManager = RDW
   ├─ propagation = REQUIRED
   ├─ readOnly = true
   └─ timeout = remainingBudget
   │
   ▼
pdmg-fw
TransactionTemplate
   │
   ├──── Physical TX BEGIN
   │
   ▼
Dispatcher
   ▼
Handler
   ▼
Facade
@Transactional(REQUIRED)
   │
   │ JOIN
   ▼
Service
   ▼
DAO
   ▼
Mapper
   ▼
DB
   │
   ▼
COMMIT / ROLLBACK
```

그러면 명확하게:

```text
Physical TX의 Owner
= pdmg-fw / TransactionTemplate

업무 TX 참여자
= Facade / Service의 @Transactional(REQUIRED)
```

가 됩니다.

### 한 줄로 정리하면

**네, 자동 참여합니다.**

단 정확한 조건은:

```text
① 외부 TransactionTemplate가 실제 TX를 시작했고
② 내부 @Transactional = REQUIRED이고
③ 같은 PlatformTransactionManager를 사용하고
④ 같은 Worker Thread에서 실행된다면

→ 내부 @Transactional은
  새로운 Physical Transaction을 만들지 않고
  외부 Physical Transaction에 참여한다.
```

그리고 이것 때문에 PDMG에서는 **Facade의 `timeout`, `readOnly`, `isolation`을 믿기보다, 최외곽 `TransactionTemplate`에 ServiceId별 Transaction Policy를 적용해야 합니다.** 이것이 지금 개선 설계의 핵심입니다.

→ 구현 순서: [timeout-tx-mg-guide.md](./timeout-tx-mg-guide.md)  
→ 전체 설계: [PDMG_트랜잭션_타임아웃_개선_설계서.md](./PDMG_트랜잭션_타임아웃_개선_설계서.md)

## 참고 (Spring 공식)

- [Transaction Propagation](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/tx-propagation.html)
- [Scaling and Parallel Processing (Thread-bound TX)](https://docs.spring.io/spring-batch/reference/scalability.html)
