# P0 Closure Wave 2A — Change Specification Package

> Candidate: `NSIGHT-ARCH-CANDIDATE-2026-08-19`  
> 상태: **IMPLEMENTATION-READY SPEC / NOT YET IMPLEMENTED**  
> 목적: Wave 1에서 `FAIL_TARGET` 또는 `PARTIAL_FAIL`로 확정된 P0 항목을 실제 코드/설정 변경 단위로 분해하고, 변경 후 Gate를 닫기 위한 정적·런타임 합격조건을 정의한다.

## 1. Wave 2A 범위

이번 Wave는 다음 네 영역만 다룬다.

| Change ID | 영역 | 현재 상태 | Wave 2A 결과 |
|---|---|---|---|
| `CHG-SEC-001` | JWT Signing Key Provider | `FAIL_TARGET` | Change Spec Ready |
| `CHG-SEC-002` | Versioned `kid` / Rotation / JWKS Grace | `FAIL_TARGET` | Change Spec Ready |
| `CHG-TX-001` | Facade Transaction Owner 정리 | `PARTIAL_FAIL` | Change Spec Ready |
| `CHG-TMO-001` | Timeout Safety Harness / Policy Guard | `BLOCKED_RUNTIME` | Harness Spec Ready |

이번 Wave에서 **소스코드를 직접 수정했다고 간주하지 않는다.** 따라서 G80/HG90은 `HOLD`를 유지한다.

## 2. Source Evidence 기준

Wave 2A는 다음 실제 Source를 기준으로 작성했다.

```text
tcf-jwt
 ├─ config/JwtKeyConfiguration.java
 ├─ config/JwtSecurityProperties.java
 ├─ support/JwtTokenIssuer.java
 └─ entry/web/JwkSetController.java

tcf-gateway
 └─ config/GatewayJwtConfiguration.java

eb-service
 ├─ entry/facade/EbUserFacade.java
 └─ application/service/EbUserService.java

ep-service
 ├─ entry/facade/EpUserEventFacade.java
 └─ application/service/EpUserEventService.java

tcf-core
 ├─ support/timeout/OnlineTransactionTimeoutExecutor.java
 ├─ support/timeout/TimeoutPolicy.java
 └─ support/timeout/TcfServiceTimeoutConstants.java

tcf-om
 └─ support/TimeoutPolicySeedData.java
```

확인된 주요 사실:

- `JwtKeyConfiguration`은 `RSAKeyGenerator(2048)`로 프로세스 기동 시 Key Pair를 생성한다.
- `KEY_ID = nsight-jwt-rs256`이 고정되어 있다.
- `JwtTokenIssuer`는 위 고정 `kid`를 JWS Header에 사용한다.
- Gateway는 `jwk-set-uri`로 JWKS를 조회하고 Issuer/Audience를 검증한다.
- `EbUserFacade.create()`와 `EpUserEventFacade.receive()`는 `@Transactional(timeout=5)`이다.
- 해당 Service 메서드도 각각 `@Transactional(timeout=5)`을 중복 선언한다.
- 현재 Source 검색상 두 Service 메서드의 직접 호출자는 각각 해당 Facade 1곳이다.
- Online Timeout은 `Future.get(timeout)` 후 `future.cancel(true)`를 호출한다.
- 기본 Timeout 상수는 `online=5s`, `tx=5s`, `db=3s`이며 일부 정책은 online=5와 tx=5가 같을 수 있다.

## 3. Target 구조

```text
                      SECURITY
                         │
        ┌────────────────┴─────────────────┐
        │                                  │
        ▼                                  ▼
Approved Key Store                  JWT Runtime Policy
(KMS/HSM/approved provider)          issuer/audience/ttl
        │
        ▼
JwtSigningKeyProvider
        │
        ├─ active kid + signer
        ├─ active public JWK
        └─ previous public JWK(s)
        │
        ├────────→ JwtTokenIssuer
        │             └─ Sign with active kid only
        │
        └────────→ JwkSetController
                      └─ active + grace public keys


                   TRANSACTION
                         │
Handler → Facade @Transactional  ← default TX Owner
                ↓
              Service            ← no duplicate REQUIRED TX
                ↓
             DAO/Mapper


                     TIMEOUT
                         │
DB Query Timeout < TX Timeout < Online Timeout < Client Timeout
                         │
                RUN-TIMEOUT Harness
                         │
        rollback / late commit / pool / context evidence
```

## 4. Change 적용 순서

```text
1. CHG-TX-001
   중복 Service TX 제거 + 정적 Rule 추가

2. CHG-SEC-001
   Signing Key Provider SPI 도입

3. CHG-SEC-002
   Versioned kid + JWKS grace + rotation state 도입

4. CHG-TMO-001
   Timeout policy validation + integration harness 추가

5. Static Scan / Unit Test / Build

6. RUN-TIMEOUT / RUN-JWT-ROTATE

7. G40/G50/G80 재평가
```

JWT 변경을 `kid rotation`보다 Provider SPI를 먼저 적용하는 이유는 `kid`와 JWKS의 Source of Truth가 Provider가 되어야 하기 때문이다.

## 5. Gate 영향

| Gate | Change 적용 전 | Change 적용 후 예상 | 실제 승격조건 |
|---|---|---|---|
| G40 | Conditional Pass | 개선 가능 | TX 재스캔 + RUN-TIMEOUT |
| G50 | Conditional Pass | 개선 가능 | KMS/HSM Adapter + RUN-JWT-ROTATE |
| G80 | HOLD | HOLD 유지 가능 | Runtime Evidence 포함 재평가 |
| HG90 | HOLD | HOLD 유지 | P0 Runtime/Human blocker 해소 |

**Change Spec 작성만으로 Gate는 PASS가 되지 않는다.**

## 6. 생성 문서

- `98A-JWT-KEY-PROVIDER-CHANGE-SPEC.md`
- `98B-JWT-KID-ROTATION-CHANGE-SPEC.md`
- `98C-TX-BOUNDARY-CLEANUP-SPEC.md`
- `98D-TIMEOUT-SAFETY-HARNESS-SPEC.md`
- `98E-WAVE2A-CHANGE-MANIFEST.json`

## 7. 다음 단계

Wave 2A 이후 실제 구현이 가능한 Source Workspace에서 다음을 수행한다.

```text
Implement
  ↓
Compile / Unit Test
  ↓
Static Conformance Re-scan
  ↓
RUN-TIMEOUT
  ↓
RUN-JWT-ROTATE
  ↓
P0 Closure Matrix 갱신
```
