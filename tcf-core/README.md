# tcf-core — TCF 엔진 코어

표준 HTTP/JSON 전문 모델, 거래 컨텍스트, STF/TCF/ETF 처리기, `TransactionHandler` 디스패처를 제공하는 프레임워크 핵심 모듈입니다.

| 항목 | 값 |
|------|-----|
| Gradle 모듈 | `tcf-core` |
| 패키지 | `com.nh.nsight.tcf.core` |
| 산출물 | JAR (라이브러리) |

## 처리 흐름

```text
TCF.process(request)
  ├─ STF.preProcess()        Header 검증, GUID/TraceId, 세션·권한, 멱등성, 거래 시작 로그
  ├─ TransactionDispatcher   serviceId → TransactionHandler
  └─ ETF.success/fail/error  표준 응답, 감사·메트릭
```

## 주요 패키지

| 패키지 | 설명 |
|--------|------|
| `message` | `StandardRequest`, `StandardResponse`, `StandardHeader`, `Result` |
| `processor` | `TCF`, `STF`, `ETF` |
| `dispatch` | `TransactionDispatcher` |
| `transaction` | `TransactionHandler` 인터페이스 |
| `context` | `TransactionContext`, `TransactionContextHolder` |
| `validation` | `StandardHeaderValidator` |
| `security` | `SessionValidator`, `AuthorizationValidator` |
| `idempotency` | `IdempotencyChecker` |
| `logging` | `TransactionLogService`, `AuditLogService` |
| `error` | `BusinessException`, `ErrorCode` |

## Handler 구현 규약

```java
@Component
public class XxxHandler implements TransactionHandler {
    @Override
    public String serviceId() { return "SV.Sample.inquiry"; }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        return facade.inquiry(request.getBody(), context);
    }
}
```

## 먼저 볼 소스

1. `processor/TCF.java`
2. `message/StandardRequest.java`
3. `dispatch/TransactionDispatcher.java`
4. `transaction/TransactionHandler.java`

## 빌드

```bash
gradle :tcf-core:build
```
