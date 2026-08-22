# 11. Validation & Error Architecture — G40

> Gate: **G40 — Mechanism / Source Conformance**  
> 기준: `nsight-tcf-framework (2).zip`의 실제 Source Snapshot과 기존 NSIGHT Architecture Baseline을 대조한다.  
> 원칙: **PDMG AS-IS와 NSIGHT TCF TO-BE를 동일 구현으로 취급하지 않는다.**


## 1. Validation 책임

Validation은 한 계층에 몰지 않고 다음 4단계로 분리한다.

```text
SYSTEM
  Header / JSON / JWT / Context
   ↓
TCF
  ServiceId / Auth / Control / Timeout / Idempotency
   ↓
BUSINESS
  Typed DTO / Business Rule / State
   ↓
DB
  Constraint / Integrity
```

## 2. PDMG AS-IS

PDMG는 다음 방식이 실제 Source에서 확인된다.

- Filter/Interceptor가 Header/JWT/Context를 준비한다.
- STF가 거래통제를 수행한다.
- Handler가 ServiceId를 검증하고 지원하지 않는 ID를 거부한다.
- Service가 Required/Duplicate/Not Found 등 업무 Validation을 수행한다.
- GlobalExceptionHandler/ResponseBodyAdvice가 오류 전문을 구성한다.

업무 DTO Bean Validation Annotation보다 Service의 명시적 Validation이 많이 사용되는 AS-IS가 확인된다.

## 3. NSIGHT TCF TO-BE

STF가 다음 Validation/Control을 명시적으로 수행한다.

1. Standard Header Validation
2. GUID/TraceId 생성
3. Session Validation
4. Authentication Context Validation
5. Authorization Validation
6. Transaction Control
7. Timeout Policy
8. Idempotency
9. Transaction Log Start

근거: `tcf-core/.../processor/STF.java:51-93`

## 4. Error Flow

### PDMG

```text
Business/Framework Exception
  ↓
Spring TX Rollback
  ↓
TcfFacade finally ETF
  ↓
GlobalExceptionHandler / ResponseBodyAdvice
  ↓
hdr_nhnis + result
```

### NSIGHT TCF

```text
Handler/Facade/Service Exception
  ↓
Transaction Rollback
  ↓
TCF catch
  ├─ BusinessException → ETF.businessFail
  ├─ TimeoutExceptionResolver → ETF.businessFail
  └─ Exception → ETF.systemError
  ↓
StandardResponse
```

TCF 진입 전에 발생한 Web/Binding 예외는 `GlobalStandardExceptionHandler`가 처리한다.

## 5. Error Owner Rule

| Error 위치 | Owner |
|---|---|
| JSON/Binding/Web Method | Web Boundary Advice |
| Header/Session/Auth/Control | STF/TCF |
| ServiceId 미등록 | Dispatcher |
| Business Rule | BusinessException |
| DB/Unexpected System | TCF System Error |
| Online Timeout | TimeoutExceptionResolver/TCF |
| Final Standard Envelope | TCF/Framework |

## 6. Source Conformance Risk

### HTTP Status = 200

`GlobalStandardExceptionHandler`는 Business, Validation, Method Not Supported, System Exception 모두 `@ResponseStatus(HttpStatus.OK)`로 반환한다.

[OPEN] 표준전문 결과코드 중심 정책이라면 가능하지만 다음을 별도 보완해야 한다.

- HTTP 기반 APM Error Rate
- L7 Health/Proxy 오류판단
- 외부 API Consumer의 Retry 정책
- 보안 탐지/감사 이벤트

### Error Detail 노출

System Error 응답에는 `e.getClass().getSimpleName()`을 detail로 넣는다. 운영/대외 응답에서 허용되는지 Security Gate에서 검토한다.

## 7. Architecture Rules

- `R-ERR-001` 업무 코드는 오류 JSON을 직접 조립하지 않는다.
- `R-ERR-002` Business Error와 System Error를 구분한다.
- `R-ERR-003` Timeout은 별도 ErrorCode와 Metric을 가진다.
- `R-ERR-004` Rollback 결정과 Error Envelope 생성을 분리한다.
- `R-ERR-005` 사용자 메시지와 내부 상세로그를 분리한다.
- `R-ERR-006` Web Boundary Error와 TCF Runtime Error Owner를 분리한다.

## 8. Gate 판정

**CONDITIONAL PASS** — 오류흐름은 구조화되어 있으나 HTTP Status/Error Detail/Code Mapping 정책이 G50에서 확정되어야 한다.
