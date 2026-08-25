# P0 Closure Execution — Wave 1

> Candidate: `NSIGHT-ARCH-CANDIDATE-2026-08-19`  
> 목적: HG90 HOLD를 해제하기 위한 첫 Closure Wave.

## 1. Wave 1 결과

**Gate는 여전히 HOLD**다. 이번 Wave는 Runtime Evidence 없이 닫을 수 있는 정적 항목만 처리했다.

### Closed Static

- Architecture Model JSON Schema 생성
- Architecture Model Validator PASS
- Policy-driven Transaction Timeout 구현 경로 확인
- MyBatis Query Timeout 구현 경로 확인
- Timeout Worker Context 전파/정리 구현 경로 확인

### Confirmed Target Fail / Change Required

- JWT Signing Key = process-local RSA generation
- fixed `kid` / rotation-grace 미구현
- 표준 업무 `*-service` 범위의 Service-level TX 중복 2건

### Runtime/Human Blocking

- Timeout rollback/late commit/connection return
- 500 vs 855 TPS 승인
- Session/HA/DR
- 71 Server→JVM→WAR→Route
- E2E Trace
- Rolling/Rollback
- Migration Cutover
- Critical ADR Sign-off

## 2. Gate 영향

```text
G80  HOLD 유지
HG90 HOLD 유지
```

단, `OPEN-013 Model JSON Schema/Validator`는 이번 Wave에서 해소 가능하다.

## 3. 다음 Wave

```text
Wave 2A — Code/Config Change
  JWT Key Provider / kid rotation
  Service TX duplicate cleanup

Wave 2B — Production Mapping Evidence
  71 Server→JVM→WAR→Route
  GSLB/L4/Apache/Tomcat config

Wave 3 — Runtime Test
  Timeout → Capacity → Hikari/SQL → N-1 → Session/Center → Trace → Rolling → JWT Rotation

Wave 4 — Human ADR / Re-Gate
  G80 재평가 → HG90 Sign-off
```


## 4. Wave 2A Change Specification

상태: **SPEC READY / NOT IMPLEMENTED**

- JWT Signing Key Provider SPI 및 Production External Provider 경계 정의
- Versioned kid / Rotation / JWKS Grace lifecycle 정의
- EB/EP Service 중복 Transaction 제거 변경점 정의
- Timeout Safety Harness + Policy Audit/Enforce Guard 정의

생성문서: `98`, `98A`, `98B`, `98C`, `98D`, `98E`.

Gate는 변경 구현/시험 전이므로 `G80 HOLD / HG90 HOLD` 유지.

## 5. Wave 2A Source Implementation Candidate — 2026-08-19

상태: **SOURCE IMPLEMENTED PARTIAL / BUILD & RUNTIME NOT CLOSED**

이번 Wave에서 원본 Snapshot을 직접 덮어쓰지 않고 `/mnt/data/_wave2a_impl` 격리 복사본에 아래 변경을 구현했다.

- `JwtSigningKeyProvider` / `JwtSigningMaterial` / `JwtKeyState` 추가
- `LocalEphemeralJwtSigningKeyProvider`로 개발용 RSA 생성 책임 격리
- `JwtExternalKeyClient` + `ExternalJwtSigningKeyProvider`로 KMS/HSM Vendor Adapter 경계 추가
- `JwtTokenIssuer`의 `RSAPrivateKey` 직접 주입과 고정 `KEY_ID` 제거
- `JwkSetController`를 Provider 기반 동적 JWKS 조회로 변경
- `JwtSecurityProperties.key-provider` 설정 추가
- local/dev=`local-ephemeral`, prod=`external` 명시
- `EbUserService.create`, `EpUserEventService.receive`의 중복 Service `@Transactional` 제거
- `TimeoutPolicyValidator` 추가 (`DB < TX < Online` Strict Order 검증)

정적 Source Contract는 PASS했고 순수 JDK 범위의 Timeout Validator는 `javac`/실행 PASS했다.

다만 Source Snapshot에 Root Gradle Build Baseline(`settings.gradle`, wrapper jar/Gradle executable)이 없고 실행환경 네트워크도 사용할 수 없어 Spring/Nimbus 의존성을 포함한 Gradle Build/Test는 이번 실행에서 수행하지 못했다. 또한 실제 KMS/HSM Vendor Adapter, `RUN-TIMEOUT`, `RUN-JWT-ROTATE`는 운영/통합 환경 증적이 필요하다.

따라서 Gate는 그대로 유지한다.

```text
G80  HOLD
HG90 HOLD
```


## Wave 2B Production Mapping Update

- 71대 Server Master를 CSV/JSON으로 전수 정규화했다.
- WEB 20 / WAS 28 / AP 13 / DB 10 수량을 재검증했다.
- Hostname/서버명 기반 Coarse Application 분류와 HA/DR Candidate를 생성하되 Config Evidence가 없는 값은 CONFIRMED로 승격하지 않았다.
- 실제 Apache Route, Tomcat JVM, CATALINA_BASE, WAR, ServiceId Deployment Set, Datasource는 `UNKNOWN`으로 유지한다.
- `P0-PHY-001`은 `PARTIAL_MAPPING_BLOCKED_EVIDENCE`로 변경하며 G80/HG90 HOLD 조건은 유지한다.
