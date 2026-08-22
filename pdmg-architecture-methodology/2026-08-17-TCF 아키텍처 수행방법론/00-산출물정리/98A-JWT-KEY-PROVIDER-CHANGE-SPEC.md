# CHG-SEC-001 — JWT Signing Key Provider Change Specification

> 상태: **CHANGE SPEC READY / IMPLEMENTATION NOT EXECUTED**  
> P0 연계: `P0-SEC-001`, `ADR-SEC-001`

## 1. 문제

현재 `tcf-jwt/.../JwtKeyConfiguration.java`는 다음 구조다.

```java
new RSAKeyGenerator(2048)
    .keyID("nsight-jwt-rs256")
    .generate();
```

따라서 Process/JVM마다 다른 Private Key가 생성될 수 있고 재기동 시 Key가 바뀐다. 이는 운영 다중 노드, 재기동, 감사, 폐기, 회전 요구와 맞지 않는다.

## 2. 설계 원칙

1. Production Private Key Source of Truth는 **KMS/HSM 또는 승인된 중앙 Key Provider**다.
2. Private Key 원문을 애플리케이션 설정파일/DB/로그에 저장하지 않는다.
3. 가능하면 Private Key를 JVM으로 Export하지 않고 **Signer Adapter**가 서명을 수행한다.
4. DEV/LOCAL에서만 Ephemeral RSA Key를 허용한다.
5. PROD에서 Ephemeral Provider가 선택되면 **Fail Fast** 한다.
6. Token Issuer와 JWKS Controller가 같은 Key Provider Metadata를 사용한다.

## 3. Target API

Private Key 객체 자체보다 `JWSSigner`를 추상화 대상으로 삼는다. HSM/Remote KMS는 Private Key Export 없이도 Signer를 구현할 수 있기 때문이다.

### 3.1 신규 인터페이스

권장 경로:

```text
tcf-jwt/src/main/java/com/nh/nsight/auth/jwt/support/key/
├─ JwtSigningKeyProvider.java
├─ JwtSigningMaterial.java
└─ JwtKeyState.java
```

권장 계약:

```java
public interface JwtSigningKeyProvider {
    JwtSigningMaterial active();
    JWKSet publicJwkSet();
    void reload();
}

public record JwtSigningMaterial(
        String kid,
        JWSAlgorithm algorithm,
        JWSSigner signer,
        RSAKey publicJwk) {
}
```

`reload()`은 구현체에 따라 no-op일 수 있다. Runtime hot rotation을 지원하지 않는 1차 버전이라면 새 Deployment 시점에 Reload하는 방식도 허용하되, 최종 운영모드는 ADR에서 확정한다.

## 4. Provider 구현 분리

### 4.1 Local / Test Provider

```text
LocalEphemeralJwtSigningKeyProvider
```

- RSA 2048 이상
- `local`, `test`, 필요 시 `dev` Profile에서만 허용
- Production Profile에서는 Bean 생성 금지
- 기존 `RSAKeyGenerator` 코드는 이 Provider 안으로 격리

### 4.2 Production Provider

```text
ExternalJwtSigningKeyProvider
```

이 문서에서는 KMS Vendor/API를 임의 확정하지 않는다.

필수 Adapter 계약만 정의한다.

| 기능 | 필수 |
|---|---|
| Active Key Reference 조회 | O |
| Active `kid` 조회 | O |
| RS256 서명 | O |
| Public JWK 조회/생성 | O |
| Previous Grace Public JWK 조회 | O |
| Key 상태/버전 Audit | O |
| Private Key Export 금지 가능 | 권장 |

실제 인프라가 제공하는 KMS/HSM API가 확정된 뒤 Vendor Adapter를 구현한다.

## 5. Configuration 변경

`JwtSecurityProperties` 아래 다음 구조를 추가하는 방안을 권장한다.

```yaml
nsight:
  security:
    jwt:
      key-provider:
        mode: external        # local-ephemeral | external
        key-family: nsight-jwt-rs256
        active-key-ref: ${JWT_ACTIVE_KEY_REF:}
        active-kid: ${JWT_ACTIVE_KID:}
        previous-key-refs: []
        fail-on-ephemeral-in-prod: true
```

`active-key-ref`의 실제 문법은 Vendor 종속이므로 여기서는 문자열 Reference로만 정의한다.

### 금지 설정

```yaml
private-key: -----BEGIN PRIVATE KEY----- ...
private-key-base64: ...
private-key-password: ...
```

Production Application Configuration에 Private Key Material 자체를 넣지 않는다.

## 6. 기존 Class 변경

| 파일 | 변경 |
|---|---|
| `JwtKeyConfiguration` | Ephemeral 생성 책임 제거 또는 Local Provider Config로 축소 |
| `JwtTokenIssuer` | `RSAPrivateKey` 직접 주입 제거, `JwtSigningKeyProvider` 사용 |
| `JwkSetController` | 고정 `JWKSet` 대신 Provider의 Public JWK Set 조회 |
| `JwtSecurityProperties` | Key Provider 설정 추가 |
| `application-local.yml` | local-ephemeral 명시 |
| `application-prod.yml` | external 명시 + key ref/kid 환경변수화 |

### JwtTokenIssuer 목표 형태

```java
JwtSigningMaterial key = keyProvider.active();

SignedJWT signedJwt = new SignedJWT(
    new JWSHeader.Builder(key.algorithm())
        .keyID(key.kid())
        .type(JOSEObjectType.JWT)
        .build(),
    claims);

signedJwt.sign(key.signer());
```

## 7. Startup Guard

Production에서 아래 조건 중 하나라도 성립하면 기동 실패가 정상이다.

- `key-provider.mode=local-ephemeral`
- Active Key Reference 없음
- Active `kid` 없음
- Public Key와 Signer의 Key Identity 불일치
- 알고리즘이 승인값(RS256)과 불일치

즉 보안 Key 문제에서 `Fail Open`을 허용하지 않는다.

## 8. Logging / Audit

로그 허용:

```text
provider mode
kid
key family
key status
load/reload time
public fingerprint
```

로그 금지:

```text
private key
raw signing secret
HSM credential
KMS access secret
token raw value
```

## 9. Test Specification

### Static / Unit

- Local Provider가 RSA Sign/Verify 성공
- PROD Profile + local-ephemeral → Startup Fail
- active kid가 JWS Header에 반영
- `publicJwkSet()`에 Private Parameter 없음
- Provider active key와 JWKS public key로 Token 검증 성공

### Integration / Runtime

`RUN-JWT-ROTATE`에서 별도 검증한다.

## 10. Migration

기존 Token은 Process-local Key로 서명되어 있어 신규 중앙 Key Provider로 전환하는 순간 검증 불가할 수 있다.

따라서 실제 Cut-over 시 아래 중 하나를 선택해야 한다.

1. 기존 Token 자연만료 대기 후 Key 전환
2. 기존 Public Key를 Grace JWKS에 포함할 수 있는 경우 Grace 유지
3. 강제 재로그인/Refresh 정책으로 전환

현재 Process-local Key가 재기동마다 바뀌므로 운영 전환 전이라면 **기존 개발 Token은 폐기하고 신규 로그인**하는 방식이 가장 단순하다. Production 적용 시에는 반드시 별도 Cut-over Runbook을 승인한다.

## 11. Rollback

Production에서 `local-ephemeral`로 되돌리는 Rollback은 금지한다.

Rollback은:

```text
New External Key Provider release
        ↓ failure
Previous External Key Provider artifact/config
        ↓
Previous approved active key reference
```

형태로만 수행한다.

## 12. Acceptance Criteria

- [ ] Production Ephemeral Key 생성 0건
- [ ] Issuer A/B가 동일 Active Key Identity 사용
- [ ] 재기동 후 Active Key가 의도 없이 변경되지 않음
- [ ] Private Key Material 로그/Config 노출 0건
- [ ] JWKS Public Key로 발급 Token 검증 성공
- [ ] `RUN-JWT-ROTATE` PASS
