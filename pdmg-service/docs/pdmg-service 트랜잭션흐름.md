현재 PDMG(TCF ON) 거래 흐름입니다.

```text
[브라우저 / pdmg-ui :8090]
        │  POST /api/relay/mgcoa5530S0
        │  body: { hdr_nhnis, dto }
        ▼
[pdmg-ui TransactionRelayService]
        │  POST http://localhost:8080/mgcoa5530S0
        ▼
┌───────────────────────────────────────────────────────────┐
│ pdmg-service (:8080) + pdmg-fw                            │
│                                                           │
│  1. DefaultFilter                                         │
│     · Body 캐시, ServiceContext/GUID/Header 준비          │
│                                                           │
│  2. ServicePreventionInterceptor (시스템 선처리)          │
│     · 요청전문 로그, ImageLog INSERT                      │
│                                                           │
│  3. OnlineTransactionController  POST /{serviceId}        │
│     · serviceId 결정 (Header rms_svc_c → path)            │
│     · dto 추출                                            │
│                                                           │
│  4. TcfFacade.process(serviceId, dto)                     │
│                                                           │
│  5. TransactionDispatcher                                 │
│     · serviceId → Handler 라우팅                          │
│                                                           │
│  6. TransactionHandler (예: mgcoa5530Handler)             │
│                                                           │
│  7. Business Facade (예: mgcoa5530Facade)  ★업무 경계     │
│     · BizPrePostAspect 선처리                             │
│     · (@Transactional 권장 위치)                          │
│         │                                                 │
│         ▼                                                 │
│  8. Service → DAO/MyBatis → DB                            │
│         │                                                 │
│         ▼                                                 │
│     · BizPrePostAspect 후처리                             │
│                                                           │
│  9. ResponseBodyAdvice (시스템 후처리)                    │
│     · { hdr_nhnis, dto } 응답 봉투, 응답전문 로그         │
│                                                           │
│ 10. ServicePreventionInterceptor.afterCompletion         │
│     · ImageLog UPDATE 등                                  │
└───────────────────────────────────────────────────────────┘
        │
        ▼
[클라이언트 응답]
```

### 한 줄 요약
```text
Filter → 시스템선처리 → OnlineController → TcfFacade → Handler
  → Facade(업무선처리·TX) → Service → DAO
  → 업무후처리 → 시스템후처리
```

### serviceId 매핑 예
| URL | Handler | Facade | Service |
|-----|---------|--------|---------|
| `/mgcoa5530S0` | mgcoa5530Handler | mgcoa5530Facade | mgcoa5530Service |
| `/mgcoa8888S0` | mgcoa8888Handler | mgcoa8888Facade | mgcoa8888Service |
| `/mgcoa8888D0` | mgcoa8888Handler | mgcoa8888Facade | mgcoa8888Service |
| `/mgcoa9999S0` | mgcoa9999Handler | mgcoa9999Facade | mgcoa9999Service |

---

## Business Facade 트랜잭션 선언

### 원칙

DB `@Transactional` 은 **Business Facade 메서드**에 선언한다.

- 업무 유스케이스 1건 = Facade 메서드 1개 = 트랜잭션 1개
- `BizPrePostAspect` 업무 선후처리 대상과 트랜잭션 경계를 맞춘다
- tcf-core `AvSampleFacade` 와 동일한 패턴이다

```text
Handler
  → Facade.@Transactional   ← 시작 / 커밋·롤백
      → Service
          → DAO
```

### 선언 위치

| 계층 | `@Transactional` | 이유 |
|------|------------------|------|
| OnlineTransactionController | X | URL 수신만 |
| TcfFacade / Dispatcher / Handler | X | 라우팅만 |
| **Business Facade** | **O** | 업무·TX 경계 |
| Service | 기본 X (필요 시 중첩만) | Facade 안에서 실행 |
| DAO | X | SQL 실행만 |

### 조회(S0)

```java
@Transactional(
        transactionManager = "rdwTransactionManager",
        readOnly = true)
public mgcoa5530S0DTOout mgcoa5530S0(Object dtoBody) throws Exception {
    ...
    return service.mgcoa5530S0(input);
}
```

- `readOnly = true`: 조회 최적화, 쓰기 실수 방지
- timeout 이 필요하면 `timeout = 초` 추가 (예: 5)

### 쓰기(D0/U0/C0)

```java
@Transactional(
        transactionManager = "rdwTransactionManager",
        rollbackFor = Exception.class)
public mgcoa8888D0DTOout mgcoa8888D0(Object dtoBody) throws Exception {
    ...
    return service.mgcoa8888D0(input);
}
```

- `rollbackFor = Exception.class`: checked 예외 포함 롤백
- 여러 DAO 호출은 같은 Facade 메서드 안에서 한 트랜잭션으로 묶인다
- timeout 이 필요하면 `timeout = 초` 추가 (예: 4)

### transactionManager

PDMG RDW 데이터소스는 `rdwTransactionManager` 빈을 사용한다.

```java
// nhnis.mg.config.RdwDataSourceConfig
@Bean
public PlatformTransactionManager rdwTransactionManager(DataSource rdwDataSource) { ... }
```

`transactionManager` 를 생략하면 기본 TM 을 찾게 되므로, **명시 권장**이다.

### 적용 예시 (현재 Facade)

| Facade 메서드 | 유형 | 권장 선언 |
|---------------|------|-----------|
| `mgcoa5530Facade.mgcoa5530S0` | 조회 | `readOnly = true` |
| `mgcoa8888Facade.mgcoa8888S0` | 조회 | `readOnly = true` |
| `mgcoa8888Facade.mgcoa8888D0` | 삭제 | `rollbackFor = Exception.class` |
| `mgcoa9999Facade.mgcoa9999S0` | 조회 | `readOnly = true` |

### 주의사항

1. **self-invocation**: 같은 Facade 안 private/this 호출에는 `@Transactional` 이 걸리지 않는다. TX가 필요한 단위는 public Facade 메서드로 노출한다.
2. **Service에 중복 선언**: Facade에 이미 있으면 Service에는 보통 넣지 않는다. Service만 단독 호출하는 경로가 있을 때만 Service에도 둔다.
3. **시스템 선후처리와 분리**: Filter / Interceptor / ImageLog 는 업무 TX 밖이다. ImageLog 실패가 업무 롤백을 유발하지 않도록 설계되어 있다.
4. **업무 예외**: Facade/Service 에서 예외를 던지면 쓰기 TX는 롤백되고, 시스템 후처리·응답 봉투는 FW 가 처리한다.