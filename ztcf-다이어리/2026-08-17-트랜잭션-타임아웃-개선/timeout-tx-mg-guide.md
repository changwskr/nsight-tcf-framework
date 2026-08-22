# PDMG Timeout / Transaction — pdmg-fw vs pdmg-service 수정 가이드

| 항목 | 값 |
| --- | --- |
| audience | 구현 담당자 |
| full-design | [PDMG_트랜잭션_타임아웃_개선_설계서.md](./PDMG_트랜잭션_타임아웃_개선_설계서.md) |
| index | [README.md](./README.md) |
| 1차 수정 파일 | `pdmg-fw/.../DefaultOnlineTimeoutExecutor.java` |

## 모듈 책임 (80% fw / 20% service)

핵심 수정 위치는 **`pdmg-fw`가 맞습니다.**
지금 이야기한 **트랜잭션 타임아웃 엔진 자체**는 `pdmg-fw`에 들어가 있고, `pdmg-service`는 그 엔진에 **업무별 정책과 TransactionManager를 제공하는 역할**로 두는 것이 구조적으로 맞습니다.

실제 소스를 보면 `DefaultOnlineTimeoutExecutor`가 `pdmg-fw`에 있고, 여기에서 Worker Pool, `Future.get(timeout)`, `TransactionTemplate`, Deadline 판단을 모두 수행합니다. 또한 `OnlineTimeoutConfiguration`도 `pdmg-fw`에 있으며 현재 `rdwTransactionManager`를 주입받도록 되어 있습니다.

```text
pdmg-fw
 └─ nhnis.fw.tcf.timeout
      ├─ DefaultOnlineTimeoutExecutor.java   ← 핵심 수정
      ├─ OnlineTimeoutExecutor.java
      ├─ SyncOnlineTimeoutExecutor.java
      ├─ OnlineTimeoutConfiguration.java     ← 수정 필요
      ├─ OnlineTimeoutProperties.java        ← 확장 필요
      └─ OnlineTimeoutWorkerContext.java
```

따라서 역할을 이렇게 나누는 게 좋습니다.

| 영역                                  | 수정 위치                 | 이유                   |
| ------------------------------------- | ------------------------- | ---------------------- |
| Worker Thread / Future timeout        | **pdmg-fw**               | 프레임워크 실행 엔진   |
| Deadline 계산                         | **pdmg-fw**               | 공통 실행 정책         |
| Remaining Budget 계산                 | **pdmg-fw**               | 공통 타임아웃 엔진     |
| `TransactionTemplate.setTimeout()`    | **pdmg-fw**               | TX 경계 생성 위치      |
| Timeout 후 rollback 강제              | **pdmg-fw**               | Framework 책임         |
| Worker 종료 상태 관리                 | **pdmg-fw**               | Framework Runtime 책임 |
| Transaction Policy 인터페이스         | **pdmg-fw**               | 공통 확장점            |
| ServiceId별 timeout 값                | pdmg-service 설정 또는 OM | 업무 정책              |
| ServiceId별 READ_ONLY/READ_WRITE/NONE | **pdmg-service**          | 업무 특성              |
| RDW/ADW TransactionManager 선택       | **pdmg-service**          | 업무 DB 구성           |
| Facade의 `@Transactional` 정리        | pdmg-service              | 업무 코드              |
| SQL 특화 timeout                      | pdmg-service/MyBatis 설정 | 데이터 접근 정책       |

즉 **80%는 `pdmg-fw`, 20%는 `pdmg-service`**라고 보면 됩니다.

### 1. 제일 먼저 고칠 파일

가장 중요한 파일은 이것입니다.

```text
pdmg-fw/src/main/java/
└─ nhnis/fw/tcf/timeout/
   └─ DefaultOnlineTimeoutExecutor.java
```

현재 여기에서:

```java
this.transactionTemplate = new TransactionTemplate(transactionManager);
this.transactionTemplate.setPropagationBehavior(
    TransactionDefinition.PROPAGATION_REQUIRED
);
```

까지만 수행합니다.

여기에 현재 빠져 있는 핵심이:

```java
transactionTemplate.setTimeout(...)
```

입니다.

하지만 단순히 이렇게:

```java
transactionTemplate.setTimeout(5);
```

로 고정하면 부족합니다.

제가 권장하는 것은:

```text
ServiceId timeout
        ↓
Absolute Deadline
        ↓
Queue 대기
        ↓
Worker 시작
        ↓
Remaining Budget 계산
        ↓
TransactionTemplate timeout
```

입니다.

예를 들어 전체 타임아웃이 5초인데 Worker Queue에서 1.8초를 기다렸다면:

```text
전체 Timeout       5000ms
Queue 대기         1800ms
────────────────────────
남은 시간          3200ms
```

Transaction에는 약 3초만 주는 식입니다.

```java
long remainingMs = deadline.remainingMillis();

if (remainingMs <= 0) {
    throw new OnlineTimeoutException(...);
}

TransactionTemplate tx = new TransactionTemplate(transactionManager);
tx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
tx.setTimeout(toSecondsCeiling(remainingMs));
```

### 2. `OnlineTimeoutConfiguration`도 `pdmg-fw`에서 바꿔야 합니다

현재 이 파일은 명시적으로:

```text
rdwTransactionManager
```

를 주입합니다.

실제 소스에서도:

```java
@Qualifier("rdwTransactionManager")
PlatformTransactionManager transactionManager
```

구조입니다.

이것은 PDMG가 앞으로 커지면 약점입니다.

예를 들어:

```text
Service A → RDW 조회
Service B → RDW 변경
Service C → ADW 조회
Service D → DB 미사용
```

인데 지금 구조는 모두:

```text
rdwTransactionManager
```

로 들어가기 때문입니다.

그래서 `pdmg-fw`에는 구체적인 RDW를 알게 하지 말고 다음 인터페이스를 두는 것을 권합니다.

```java
public interface TransactionPolicyResolver {

    TransactionPolicy resolve(String serviceId);
}
```

그리고:

```java
public record TransactionPolicy(
    TransactionMode mode,
    String transactionManager,
    boolean readOnly
) {}
```

형태로 만드는 것입니다.

---

## 그러면 `pdmg-service`에서는 무엇을 고치느냐

`pdmg-service`는 **엔진을 수정하는 곳이 아니라 정책을 제공하는 곳**입니다.

예를 들어:

```yaml
nhnis:
  fw:
    transaction:
      policies:
        mgcoa5530S0:
          manager: rdwTransactionManager
          mode: READ_ONLY

        mgcoa9999S0:
          manager: rdwTransactionManager
          mode: READ_WRITE

        mgcoa9000S0:
          mode: NONE
```

처럼 정의할 수 있습니다.

구조적으로는:

```text
pdmg-service
    │
    ├─ ServiceId
    ├─ 업무 특성
    ├─ 사용하는 DB
    └─ Read / Write 여부
             │
             ▼
TransactionPolicyResolver
             │
             ▼
          pdmg-fw
             │
             ▼
TransactionBoundaryExecutor
```

가 좋습니다.

### 중요한 설계 원칙

**`pdmg-fw`가 `rdwTransactionManager`라는 업무 애플리케이션 Bean 이름을 직접 알아서는 안 됩니다.**

현재 설계 문서에서도 여러 TransactionManager가 있을 경우 `pdmg-service`가 온라인용 Manager를 제공하거나 qualifier 연결을 제공한다고 되어 있습니다. 따라서 앞으로는 한 단계 더 발전시켜 **Framework는 정책 인터페이스만 알고, 실제 RDW/ADW 선택은 Service 쪽에서 제공**하는 것이 좋습니다.

---

## Facade의 `@Transactional`은 당장 삭제하지 않는 것이 좋습니다

`pdmg-service`에는 현재 다음과 같은 코드가 있을 수 있습니다.

```java
@Transactional(
    transactionManager = "rdwTransactionManager",
    readOnly = true
)
public ...
```

이걸 바로 모두 제거하지 않는 것이 좋습니다.

1차 개선은:

```text
pdmg-fw
Worker
 ↓
Remaining Budget
 ↓
TransactionTemplate(timeout)
 ↓
Facade @Transactional(REQUIRED)
```

로 하고,

Facade는 기존 transaction에 참여하게 둡니다.

그다음 2차에서:

```text
외곽 Transaction Policy
       ↓
readOnly=true
timeout=...
manager=RDW
```

가 완전히 안정되면 Facade annotation 정책을 단순화할 수 있습니다.

---

# 제가 권장하는 실제 수정 순서

```text
1단계
pdmg-fw
DefaultOnlineTimeoutExecutor
        ↓
Remaining Budget 추가
TransactionTemplate timeout 추가

2단계
pdmg-fw
Worker 실행 직전 Deadline 검사

3단계
pdmg-fw
Timeout / Worker 상태 세분화

RUNNING
→ DEADLINE_EXCEEDED
→ CANCEL_REQUESTED
→ ROLLBACK_PENDING
→ ROLLED_BACK

4단계
pdmg-fw
TransactionPolicyResolver 인터페이스 추가

5단계
pdmg-service
ServiceId별 Transaction Policy 구현

6단계
pdmg-fw
rdwTransactionManager 고정 의존 제거

7단계
pdmg-service
Facade @Transactional과 외곽 TX 정책 정합성 정리

8단계
MyBatis/JDBC
Statement timeout 연결

9단계
Runtime Evidence / Test
```

최종적으로 모듈 책임은 이렇게 가는 것이 가장 깔끔합니다.

```text
┌──────────────────────────────┐
│         pdmg-service         │
│                              │
│ ServiceId                    │
│ 업무 Transaction Policy     │
│ RDW / ADW 선택              │
│ ReadOnly / ReadWrite        │
└──────────────┬───────────────┘
               │ Policy
               ▼
┌──────────────────────────────┐
│           pdmg-fw            │
│                              │
│ Deadline                     │
│ Worker Pool                  │
│ Remaining Budget             │
│ Transaction Boundary         │
│ Transaction Timeout          │
│ Cancellation                 │
│ Rollback                     │
│ Runtime State                │
└──────────────┬───────────────┘
               │
               ▼
        Handler / Facade
               │
               ▼
        Service / DAO
               │
               ▼
      MyBatis / JDBC / DB
```

**따라서 지금 당장 소스를 고친다면 시작점은 `pdmg-fw`의 `DefaultOnlineTimeoutExecutor.java`입니다.**  
다만 최종적으로 제대로 개선하려면 `pdmg-fw`만 고쳐 끝내는 것이 아니라, **`pdmg-fw = 실행 엔진`, `pdmg-service = ServiceId별 Transaction Policy 공급자`**로 역할을 나누는 것이 맞습니다.

---

## 현재 구현 vs 1단계 목표

| 항목 | 현재 (2026-08-22) | 1단계 목표 |
| --- | --- | --- |
| `Future.get(timeoutMs)` | ✅ | 유지 |
| Worker `TransactionTemplate` | ✅ (timeout 없음) | `setTimeout(remainingSec)` |
| Queue 대기 후 Remaining Budget | ❌ | Worker 시작 직전 검사 |
| `readOnly` 외곽 TX | ❌ | Policy 기반 |
| `rdwTransactionManager` 고정 | ✅ | `TransactionPolicyResolver` |
| 단위 테스트 | ✅ `DefaultOnlineTimeoutExecutorTest` | Remaining Budget 케이스 추가 |

## 설정 분리 (운영 시 혼동 금지)

```yaml
nhnis:
  fw:
    timeout:          # ← 본 가이드·설계서 범위
      enabled: true
      milliseconds: 5000
    txcontrol:        # ← 거래통제 (mgcoa9001). 타임아웃과 무관
      enabled: true
```
