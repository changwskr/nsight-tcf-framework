# 08. NSIGHT TCF Framework Architecture — PDMG AS-IS vs NSIGHT TO-BE

> Gate: **G40 — Mechanism / Source Conformance**  
> 기준: `nsight-tcf-framework (2).zip`의 실제 Source Snapshot과 기존 NSIGHT Architecture Baseline을 대조한다.  
> 원칙: **PDMG AS-IS와 NSIGHT TCF TO-BE를 동일 구현으로 취급하지 않는다.**


## 1. 결론

[CONFIRMED] PDMG와 NSIGHT TCF는 모두 **ServiceId 중심의 온라인 거래 생명주기 Framework**라는 공통 구조를 가진다. 그러나 NSIGHT TCF TO-BE는 PDMG의 단순 STF/ETF를 확장하여 인증문맥·권한·멱등성·거래통제·Timeout Policy·거래로그·Audit·Metric·Runtime Hook까지 Framework 책임으로 구조화한다.

## 2. PDMG AS-IS Runtime

```text
DefaultFilter
  ├─ Request Body Cache
  ├─ hdr_nhnis Parse
  ├─ GUID / MDC
  └─ ServiceContext
       ↓
ServicePreventionInterceptor
  ├─ JWT
  ├─ Header/User/IP 보강
  ├─ Request Log
  └─ PRE ImageLog
       ↓
OnlineTransactionController
       ↓
TcfFacade
  ├─ TransactionContext
  ├─ ActiveTransactionRegistry.begin
  ├─ STF = 거래통제
  ├─ OnlineTimeoutExecutor
  │    └─ Dispatcher → Handler → Facade → Service
  └─ finally: ETF + ActiveTransactionRegistry.end
```

### Source Evidence

- `DefaultFilter.java:33-43` — ServiceContext/GUID 준비, JWT는 Interceptor 책임.
- `ServicePreventionInterceptor.java:45-54` — JWT, 거래통제는 STF, ImageLog 책임 분리.
- `TcfFacade.java` — STF → Timeout Executor → Dispatcher, ETF는 finally.
- `stf.java:13-21,50-57` — 현재 PDMG STF 핵심은 `TB_MG_TX_CONTROL` 거래통제.
- `etf.java:49-85` — 종료 시 elapsed timeout interval 점검.

## 3. NSIGHT TCF TO-BE Runtime

```text
OnlineTransactionController / TcfGateway
       ↓
TCF.process
       ↓
STF
  ├─ StandardHeader Validation
  ├─ GUID / TraceId
  ├─ TransactionContext + MDC
  ├─ Session Validation
  ├─ Authentication Context
  ├─ Authorization
  ├─ Transaction Control
  ├─ Timeout Policy
  ├─ Idempotency
  └─ Transaction Log Start
       ↓
OnlineTransactionTimeoutExecutor
       ↓
TransactionDispatcher
       ↓
Handler → Facade(TX) → Service → Rule/DAO
       ↓
ETF
  ├─ Idempotency Success/Fail
  ├─ Transaction Log End
  ├─ Audit
  └─ Metric
       ↓
finally
  ├─ Runtime Hook End
  ├─ TransactionContext clear
  ├─ AuthenticationContext clear
  ├─ TimeoutContext clear
  └─ MDC.clear
```

### Source Evidence

- `tcf-core/.../processor/TCF.java` — STF/Timeout/Dispatcher/ETF/cleanup 전체 조립.
- `tcf-core/.../processor/STF.java:51-93` — 9단계 선처리.
- `tcf-core/.../processor/ETF.java:37-93` — success/businessFail/systemError에서 로그·감사·Metric·멱등성 종료.
- `tcf-web/.../entry/facade/TcfGateway.java:11-28` — REST/파일 API 등 비표준 진입점도 동일 TCF 경로로 수렴.

## 4. 3단계 선후처리 책임

| 계층 | PDMG AS-IS | NSIGHT TO-BE 기준 |
|---|---|---|
| SYSTEM | Filter/Interceptor/Resolver | Web/Security Adapter + Context Preparation |
| TCF | STF=거래통제, ETF=종료 Timeout 점검 | Validation/Auth/Control/Timeout/Idempotency/TxLog + Audit/Metric |
| BUSINESS | BizPrePostAspect at Service | 업무별 AOP/Rule/Business Log, Framework 책임과 분리 |

## 5. Context Lifecycle

### PDMG

- `DefaultFilter`가 `ServiceContextHolder`를 설정하고 `finally`에서 제거한다.
- Timeout Worker는 `OnlineTimeoutWorkerContext`로 ServiceContext와 MDC를 Snapshot한다.
- PDMG WorkerContext 주석은 **Servlet request/response를 보관하지 않는다**고 명시한다.

### NSIGHT TCF

- STF가 `TransactionContextHolder`와 MDC를 설정한다.
- `TimeoutThreadContext`는 TimeoutPolicy, TransactionContext, MDC뿐 아니라 `RequestContextHolder.getRequestAttributes()`까지 Worker에 전달한다.
- Worker 종료 시 RequestAttributes/Timeout/Transaction/MDC를 정리한다.

### [RISK] HTTP Request Context Worker 전파

NSIGHT TO-BE의 `TimeoutThreadContext`는 HTTP RequestAttributes를 Worker로 전달한다. 이는 편의성이 있지만, Caller가 Timeout으로 반환된 후 Worker가 계속 실행될 수 있는 상황에서 Servlet Request 수명과 비동기 Worker 수명이 결합될 수 있다.

따라서 다음 중 하나를 ADR로 확정해야 한다.

1. Worker에서는 불변 Snapshot만 전달하고 Servlet RequestAttributes 전파를 제거한다.
2. RequestAttributes 사용 범위를 명시하고 Timeout 후 접근 안전성을 테스트한다.

## 6. Error Ownership

NSIGHT TCF는 `TCF.process` 내부에서 Business/Timeout/System Error를 StandardResponse로 변환하고, `GlobalStandardExceptionHandler`는 TCF 진입 이전 Web 계층 예외를 보완 처리한다.

이 구조는 **TCF 내부 오류와 Web Boundary 오류의 Owner를 분리**한다는 점에서 PDMG의 `GlobalExceptionHandler + ResponseBodyAdvice` 구조보다 명확하다.

단, `GlobalStandardExceptionHandler`는 Validation/System Exception도 HTTP 200으로 반환한다. 이는 표준전문 정책일 수 있으나 L4/APM/HTTP Error Rate 관측과 충돌할 수 있으므로 G50/G70에서 HTTP Status 정책 ADR이 필요하다.

## 7. Logging Source Conformance

[CURRENT SOURCE] `TCF`, `STF`, `ETF`, `OnlineTransactionController`에는 `System.out.println` 기반 Trace가 다수 존재한다.

[TO-BE RULE] 운영 Runtime에서는 구조화 Logger/MDC/TransactionLog/Metric을 정본으로 사용하고 Console Trace는 개발 Profile 또는 Debug 옵션으로 제한해야 한다.

## 8. Framework Conformance Matrix

| Mechanism | PDMG AS-IS | NSIGHT TCF | 판정 |
|---|---|---|---|
| ServiceId Dispatcher | 구현 | 구현 | PASS |
| Duplicate Fail-Fast | 구현 | 구현 | PASS |
| Transaction Context | 구현 | 확장 구현 | PASS |
| System Context Cleanup | 구현 | 구현 | PASS |
| Transaction Control | STF | STF | PASS |
| Timeout Policy | YML/ServiceId override | Repository/ServiceId/TxCode/BusinessCode | TO-BE 확장 |
| Session Validation | Interceptor/별도 | STF Extension Point | TO-BE |
| Authorization | 일부 Security | STF Extension Point | TO-BE |
| Idempotency | 제한적 | STF/ETF | TO-BE |
| Tx Log | 분산 | STF/ETF | TO-BE |
| Audit/Metric | 분산 | ETF | TO-BE |
| Runtime Hook | Active Registry | Hook + Runtime Model | TO-BE |
| Worker HTTP Context | 미전파 | RequestAttributes 전파 | RISK/ADR |

## 9. G40 Framework 결론

**CONDITIONAL PASS**

TCF의 구조적 방향은 소스에서 구현이 확인되었다. 다만 다음 P0/P1 조건이 남는다.

- P0: Transaction Owner와 Timeout Worker 경계 확정.
- P0: Worker Context에서 Servlet RequestAttributes 전파 정책 확정.
- P0: `om-service`/`tcf-om` Runtime Scope 정리.
- P1: Console Trace 운영 제거/프로파일링.
- P1: HTTP 200 Error Response 정책과 Observability 정합성 결정.
