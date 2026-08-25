# P0 Closure Wave 1 — Static Evidence Result

> 목적: HG90 HOLD의 P0 항목 중 **Source/Model만으로 사실을 확정할 수 있는 항목**을 먼저 닫고, Runtime/Human Evidence가 필요한 항목은 분리한다.

## 1. 결론

현재 P0를 전부 닫을 수는 없다. 그러나 이번 정적 Closure에서 다음을 확정했다.

| 항목 | 판정 | 정적 결론 |
|---|---|---|
| Architecture Model Schema | **CLOSED_STATIC** | JSON Schema/Validator PASS |
| TX Timeout Policy 적용 | **CLOSED_STATIC** | `@Transactional` timeout을 정책값으로 덮어쓰는 구현 존재 |
| DB Query Timeout 적용 | **CLOSED_STATIC** | MyBatis MappedStatement timeout 동적 적용 구현 존재 |
| Worker Context Cleanup | **CLOSED_STATIC** | Timeout/Transaction/MDC/RequestAttributes 전파 후 finally clear |
| JWT Signing Key | **FAIL_TARGET** | Process-local RSA key 생성 |
| JWT kid/Rotation | **FAIL_TARGET** | 고정 kid, rotation/grace 구조 미확인 |
| Facade TX Owner | **PARTIAL_FAIL** | 표준 `*-service` 범위에서 Service-level TX 2건 중복 |
| Late Commit/Connection Return | **BLOCKED_RUNTIME** | Source만으로 안전성 증명 불가 |
| Capacity/HA/Session/E2E Trace | **BLOCKED_RUNTIME** | 실제 Run Evidence 필요 |
| 71 Server→JVM→WAR | **BLOCKED_EVIDENCE** | Production config/hostname mapping 필요 |

## 2. JWT — P0 FAIL_TARGET

현재 Source의 `tcf-jwt/.../JwtKeyConfiguration.java`는 `RSAKeyGenerator(2048)`을 사용하여 프로세스 기동 시 Key Pair를 만들며 `KEY_ID = nsight-jwt-rs256`을 고정 사용한다. `JwtTokenIssuer`도 해당 고정 `kid`로 RS256 서명한다.

따라서 운영 목표인 `KMS/HSM Private Key SoT + Versioned kid + JWKS Grace`와 현재 구현 사이의 P0 Drift는 유지한다.

**해제조건**

```text
KMS/HSM Key Provider
  ↓
Versioned kid
  ↓
Issuer A/B 동일 SoT
  ↓
JWKS = active + previous(grace)
  ↓
RUN-JWT-ROTATE
```

## 3. Transaction Owner — 범위 재정리

정적 재스캔 결과, `com.nh.nsight`의 표준 `*-service` 업무 모듈에서 Service-level `@Transactional`은 다음 **2건**이다.

- `eb-service/.../EbUserService.create()`
- `ep-service/.../EpUserEventService.receive()`

두 메서드는 각각 `EbUserFacade.create()`와 `EpUserEventFacade.receive()`가 이미 `@Transactional(timeout=5)` 경계를 가진 상태에서 호출된다. 따라서 기본 `REQUIRED` 기준으로는 **중복 Transaction 선언 후보**다.

`tcf-oc`의 `CapNewWizardService`, `CapNewApprovalService`에도 Service-level Transaction이 존재하지만, 이 모듈은 표준 `*-service` 온라인 업무 모듈과 다른 직접 Controller→Service 형태의 OC/설계지원 런타임이므로 **동일 Conformance Scope에 자동 포함하지 않는다.** 별도 Scope 승인 필요.

## 4. Timeout / Query Timeout — Static Closure

### 4.1 TX Timeout

`PolicyDrivenTransactionAttributeSource`는 `TimeoutContextHolder`의 `txTimeoutSec`을 읽어 `@Transactional`의 timeout을 덮어쓴다. 복사 시 propagation/isolation/readOnly/rollback rules를 유지한다.

### 4.2 DB Query Timeout

`PolicyDrivenQueryTimeoutInterceptor`는 `dbQueryTimeoutSec`을 MyBatis `MappedStatement`에 적용하며 query/update 모두 intercept한다.

### 4.3 Online Timeout

`OnlineTransactionTimeoutExecutor`는 `Future.get(timeout)` 후 Timeout 시 `future.cancel(true)`를 수행한다. 이것은 **중단 요청**일 뿐 DB rollback/late commit 방지를 자동 증명하지 않는다. 따라서 안전성은 `RUN-TIMEOUT`에서만 닫을 수 있다.

## 5. Thread Context — Static Closure

`TimeoutThreadContext`는 다음을 Worker로 전파한다.

```text
TimeoutPolicy
TransactionContext
MDC
RequestAttributes
```

그리고 `finally`에서 RequestAttributes reset, TimeoutContext clear, TransactionContext clear, MDC clear를 수행한다. Source 관점의 cleanup path는 확인됐다. 다만 Thread Pool 재사용 상황의 누수 여부는 Runtime concurrency test가 필요하다.

## 6. Architecture Model Schema — CLOSED_STATIC

생성 파일:

- `29-ARCHITECTURE-MODEL.schema.json`
- `tools/validate_architecture_model.py`
- `29-MODEL-VALIDATION-RESULT.json`

검증 결과:

```text
Status              PASS
Node                 380
Edge                 380
Duplicate Node ID    0
Dangling Edge        0
Undeclared Relation  0
Undeclared Node Type 0
```

따라서 HG90 Checklist의 `Architecture Model JSON Schema`와 `Model Validator PASS`는 이번 Wave에서 닫을 수 있다. 단 **Model Coverage** 자체는 아직 Partial이다.

## 7. Physical Runtime Evidence 주의

Source ZIP의 `ztomcat/apache-tomcat-10.1.34/webapps`에는 `batch, eb, ep, gw, ic, jwt, mg, ms, oc, om, pc, pd, ss, sv, ui, uj` 16개 앱과 ROOT가 하나의 개발 통합 Tomcat에 배치되어 있다.

이것은 **개발/통합 Reference Packaging Evidence**이지, 운영 71대 서버의 `Container=Tomcat JVM` 배치를 부정하는 Production Evidence가 아니다. 두 Runtime Scope를 혼합하지 않는다.

## 8. Wave 1 종료 후 남는 Hard Blocker

- JWT KMS/HSM + Key Rotation
- Service TX 중복 2건 처리
- Timeout Late Commit/Connection Return
- Runtime Approved Capacity
- Session/HA/DR
- 71 Server→JVM→WAR→Route
- GUID+ServiceId E2E Trace
- Rolling/Rollback
- Migration Reconciliation/Cutover
- Human ADR Sign-off
