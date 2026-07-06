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
  ├─ STF.preProcess()        Header 검증, GUID/TraceId, 세션·권한, 멱등성
  │                          거래통제(7필드) → Timeout 정책 → TimeoutContextHolder
  ├─ OnlineTransactionTimeoutExecutor
  │     └─ TransactionDispatcher   serviceId → TransactionHandler
  └─ ETF.success/fail/error  표준 응답, 감사·메트릭
```

## 주요 패키지

| 패키지 | 설명 |
|--------|------|
| `message` | `StandardRequest`, `StandardResponse`, `StandardHeader`, `Result`, `TcfStandardMessageCatalog` |
| `processor` | `TCF`, `STF`, `ETF` |
| `dispatch` | `TransactionDispatcher` |
| `transaction` | `TransactionHandler` 인터페이스 |
| `context` | `TransactionContext`, `TransactionContextHolder` |
| `control` | `TransactionControlService` — Header 7필드 거래통제 |
| `timeout` | `TimeoutPolicyService`, `OnlineTransactionTimeoutExecutor`, `TimeoutExceptionResolver` |
| `validation` | `StandardHeaderValidator` |
| `security` | `SessionValidator`, `AuthorizationValidator`, `AuthenticationContextValidator` |
| `idempotency` | `IdempotencyChecker` |
| `logging` | `TransactionLogService`, `AuditLogService` |
| `error` | `BusinessException`, `ErrorCode` |
| `support` | `TcfConsoleLog` (UTF-8 콘솔 로그) |

## Handler 구현 규약

업무 WAR에서는 Handler를 **`entry.handler`** 패키지에 둡니다 (6계층 규약).

핸들러는 **도메인(Service)당 1개**를 원칙으로 하며, `serviceIds()`로 담당 거래 목록을 선언하고
`doHandle` 내부에서 `context.getHeader().getServiceId()` 기준으로 분기합니다.
새 거래 추가 시 상수 + `case` 한 줄씩만 확장하면 됩니다.

```java
@Component
public class SvSampleHandler implements TransactionHandler {

    private static final String INQUIRY = "SV.Sample.inquiry";

    private final SvSampleFacade facade;

    public SvSampleHandler(SvSampleFacade facade) {
        this.facade = facade;
    }

    @Override
    public Collection<String> serviceIds() {
        return List.of(INQUIRY);
    }

    @Override
    public Object doHandle(StandardRequest<Map<String, Object>> request, TransactionContext context) {
        String serviceId = context.getHeader().getServiceId();
        return switch (serviceId) {
            case INQUIRY -> facade.inquiry(request.getBody(), context);
            default -> throw new BusinessException(ErrorCode.SERVICE_NOT_FOUND,
                    "SvSampleHandler 미지원 serviceId: " + serviceId);
        };
    }
}
```

> 하위호환: 단일 거래만 처리하는 경우 `serviceId()` 하나만 재정의해도 됩니다
> (`serviceIds()` 기본 구현이 `serviceId()` 단일값을 감쌉니다).

## 먼저 볼 소스

1. `processor/TCF.java`
2. `processor/STF.java`
3. `timeout/OnlineTransactionTimeoutExecutor.java`
4. `control/TransactionControlService.java`
5. `message/StandardRequest.java`
6. `dispatch/TransactionDispatcher.java`

## 빌드

```bash
gradle :tcf-core:build
```
