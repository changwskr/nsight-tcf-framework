# 12. Security / SSO / JWT / Session Architecture

## 1. 목적

본 문서는 NSIGHT의 SSO, JWT, KMS/Key, JWKS, Access/Refresh Token, Gateway 인증, Session, 권한, Header 신뢰경계를 하나의 Security Architecture로 기준화한다.

핵심 원칙은 다음과 같다.

```text
인증(Authentication)
≠
토큰 발급(Token Issuance)
≠
토큰 검증(Token Validation)
≠
세션(Session State)
≠
권한(Authorization)
```

또한 설계 문서의 KMS 기반 목표와 현재 Source Snapshot의 실제 Key 구현을 구분한다.

---

## 2. Evidence / Source Scope

### [FACT] 문서/화면 Evidence

- SSO/IdP 인증 후 JWT 발급 구조
- KMS에서 RSA Key를 기동 시 Load하는 설계 자료
- Access Token / Refresh Token 분리
- Public Key / JWKS 기반 검증 방향
- Refresh Repository / Revoke / Logout 방향
- `kid`, Key Rotation, Refresh Rotation 보완 필요사항

### [FACT] Source Snapshot

- `tcf-jwt`
  - `JwtTokenIssuer`
  - `JwtKeyConfiguration`
  - `JwtRuntimePolicy`
  - `JwtAuthService`
  - `JwtTokenStore`
  - `JwkSetController`
- `tcf-gateway`
  - `GatewayAuthenticationService`
  - `GatewayJwtValidator`
  - `GatewayJwtConfiguration`
  - `GatewayProperties`
- `tcf-core`
  - `AuthenticationContextValidator`
  - `AuthorizationValidator`
  - `SessionValidator`

---

## 3. Current Security Big Picture

```text
사용자
  │
  ▼
SSO / IdP
  │  인증결과 / 내부 SSO 연계
  ▼
JWT Issuer (tcf-jwt)
  │
  ├─ Access Token (RS256)
  ├─ Refresh Token
  ├─ Access Token Registry
  ├─ Refresh Token Hash Registry
  └─ Denylist / Revoke
  │
  ▼
Client / UI
  │ Authorization: Bearer <JWT>
  ▼
Gateway
  │
  ├─ JWT Enabled + Bearer 있음 → JWT 검증
  ├─ OM 거래 → JWT 필수
  └─ 그 외 Bearer 없으면 Session 검증 가능
  │
  ▼
Authentication Context
  │
  ▼
TCF
  ├─ Header ↔ JWT Claim 정합성
  ├─ Session Validation
  └─ Authorization Validation
  │
  ▼
Business Service
```

---

## 4. SSO와 JWT 책임 분리

| 영역 | 책임 | 현재 판정 |
|---|---|---|
| SSO/IdP | 최초 사용자 신원 인증 | `[TO-BE/EXTERNAL]` |
| `tcf-jwt` | SSO 결과를 기반으로 JWT 발급 | `[AS-IS/PARTIAL]` |
| Gateway | JWT 또는 Session 검증/사용자 문맥 설정 | `[AS-IS]` |
| TCF | Header와 인증문맥 정합성 검증 | `[AS-IS]` |
| Business | 인증 메커니즘 자체가 아닌 업무권한 사용 | `[TO-BE]` |

### [FACT] 현재 Source의 SSO Issue

`tcf-jwt`의 `JwtAuthService.ssoIssue()`는 `JwtInternalCallValidator`를 먼저 수행하고, `userId`를 기준으로 사용자 정보를 해석한 뒤 Token Pair를 발급한다.

단, 실제 외부 IdP Authorization Code 교환/Signature 검증이 `ssoIssue()` 내부에 구현된 구조는 확인되지 않았다. `issuer`가 `OM-SSO`로 시작하는 경우 신뢰 사용자 Map을 구성하는 코드가 있으므로, **SSO Assertion을 누가 사전에 검증하여 `ssoIssue`를 호출하는지**가 Security Boundary의 핵심이다.

판정:

```text
[OPEN]
SSO/IdP Assertion Verification Owner
```

---

## 5. JWT Access Token

### [FACT] 현재 Source

`JwtTokenIssuer`는 다음 Claim을 생성한다.

```text
iss
sub = userId
aud
jti
iat
exp
userId
userName
branchId
authGroupId
channelId
```

Header:

```text
alg = RS256
kid = nsight-jwt-rs256
typ = JWT
```

기본 정책(`JwtSecurityProperties`):

| 항목 | 기본값 |
|---|---:|
| Issuer | `NSIGHT-AUTH` |
| Audience | `NSIGHT-MP` |
| Access TTL | 15분 |
| Refresh TTL | 8시간 |
| Clock Skew | 60초 |
| Algorithm | RS256 |
| Denylist Check | true |
| Refresh Rotation | true |

이 값은 Runtime Policy/DB에서 변경 가능하도록 구성되어 있으므로 운영 Baseline에는 실제값을 별도로 증적화해야 한다.

---

## 6. 가장 중요한 GAP — Key Management

### [TO-BE/DESIGN]

기존 설계 자료는:

```text
KMS
 ↓
Private/Public Key Load
 ↓
JWT Sign / Verify
```

또는 더 높은 보안수준으로:

```text
Payload Hash
 ↓
KMS Sign API
 ↓
Signature
```

를 검토하도록 되어 있다.

### [AS-IS/SOURCE]

그러나 현재 `tcf-jwt/JwtKeyConfiguration`은 애플리케이션 기동 시 다음처럼 **RSA 2048 Key Pair를 Runtime에서 새로 생성**한다.

```text
RSAKeyGenerator(2048)
  .keyID("nsight-jwt-rs256")
  .generate()
```

따라서 현재 Source Snapshot은 KMS Key Load 구조가 아니다.

### [GAP] 영향

```text
Process Restart
   ↓
새 RSA Key 생성 가능
   ↓
기존 Access Token Signature 검증 실패 가능
```

HA 환경에서 JWT Issuer가 복수 Instance라면 각 Instance가 서로 다른 Key를 생성할 수 있어 더 심각하다.

### [P0 DECISION REQUIRED]

운영 TO-BE는 다음 중 하나를 확정해야 한다.

1. KMS Private Key Load
2. KMS/HSM Sign API (Private Key 비반출)
3. 중앙 Key Store + Key Version Registry

권장 우선순위:

```text
KMS/HSM Sign API
  > 중앙 Key Store Load
  > Runtime Ephemeral Key Generation
```

Runtime Ephemeral Key Generation은 개발/테스트 용도로만 허용하는 것이 적절하다.

---

## 7. JWKS / Public Key Distribution

### [FACT]

`tcf-jwt/JwkSetController`가 Public JWK Set을 제공한다.

`tcf-gateway/GatewayJwtConfiguration`은 `jwk-set-uri`가 없으면 JWT Enable 시 기동 실패하며, `NimbusJwtDecoder.withJwkSetUri()`로 Public Key를 조회한다.

또한:

- Issuer 검증
- Audience 검증
- 기본 JWT validation(exp 등)

을 적용한다.

### [GAP]

현재 `kid`는 고정값 `nsight-jwt-rs256`이며 Key Version/Rotation Model이 구현되어 있지 않다.

TO-BE:

```text
kid = key-version
JWKS = old/current public keys coexist
Issuer = new private key로 서명
Validator = kid 기준 key 선택
Grace Period 후 old key 제거
```

---

## 8. Refresh Token Architecture

### [FACT]

현재 Source는 Refresh Token 원문을 DB에 저장하지 않고 `SHA-256 Hash`를 저장한다.

```text
Plain Refresh Token
   ↓ SHA-256
Token Hash
   ↓
DB
```

Refresh 시:

1. Plain token hash 계산
2. DB 조회
3. revoked/rotated 확인
4. expiry 확인
5. Rotation Enable이면 기존 Token `rotated` 처리
6. 동일 Token Family로 새 Token Pair 발급

이는 기본적인 Refresh Rotation과 Reuse 방지 구조를 이미 가지고 있다.

### [FACT]

Logout 시:

- Access Token → denylist
- Refresh Token → DB revoke

처리를 수행한다.

### [OPEN]

Token Family 탈취 탐지 시 **Family 전체 revoke**까지 수행하는지는 추가 검증이 필요하다.

---

## 9. Access Token Revoke / Denylist

### [FACT]

`JwtTokenStore`는 `jti` 기반 denylist를 저장하고 Access Token Registry에도 revoke 정보를 반영한다.

```text
Access Token
  ↓ parse
jti / exp / sub
  ↓
Denylist
  +
JWT Token Registry Revoke
```

단, 실제 Gateway validation 경로가 Denylist를 조회하는지 Source 전체 연결을 전수 검증해야 한다.

`GatewayJwtValidator`는 JWKS Signature/Issuer/Audience 기반 검증을 수행하지만, 해당 클래스 내부에서는 Denylist 조회가 보이지 않는다.

판정:

```text
[GAP/OPEN]
Denylist enforcement at every protected ingress
```

---

## 10. Gateway Authentication Mode

### [FACT]

`GatewayAuthenticationService`는 Hybrid 인증을 지원한다.

```text
loginRequired = false
 → Auth Skip

loginRequired = true
  ↓
ServiceId Login Exempt ?
  ├─ Yes → Skip
  └─ No
       ↓
JWT Enabled + businessCode=OM
  ├─ Bearer 필수 → JWT
  └─ missing → 401

그 외
JWT Enabled + Bearer 있음
  → JWT
Bearer 없음
  → Session Validation
```

즉 현재 Source는 **JWT-only가 아니라 JWT + Session Hybrid Mode**이다.

이것은 기술적으로 가능하지만 운영 표준으로 사용할 인증모드를 ADR로 명확하게 확정해야 한다.

---

## 11. Session Architecture

### [FACT]

Gateway는 다음 Session Validation 요소를 가지고 있다.

```text
JSESSIONID / NSIGHTSID
  ↓
SPRING_SESSION 존재/만료
  ↓
TCF_USER_SESSION STATUS
  ↓
Header User ↔ Session User
```

또한 `TCF_USER_SESSION ↔ SPRING_SESSION` Sync 기능이 존재한다.

따라서 현재 NSIGHT Source는 Spring Session DB 계열의 상태 모델을 포함하고 있다.

### [OPEN]

기존 문서에는 DeltaManager와 Spring Session JDBC 후보가 함께 존재하므로 **최종 운영 Session SoT**는 별도 ADR이 필요하다.

권장 결정문은 최소 다음을 포함해야 한다.

- Browser UI Session 사용 여부
- JWT와 Session의 역할
- Session Store
- Session TTL
- Cross JVM Session 공유
- Logout/Forced Logout
- DR Session 복구 여부

---

## 12. Header Trust Boundary

NSIGHT Standard Header에서 다음 값은 Client 입력값을 그대로 신뢰하면 안 된다.

| Header | 신뢰 Source |
|---|---|
| userId | JWT/Session |
| branchId | JWT/User Context |
| channelId | Gateway/Auth Context |
| clientIp | Gateway/Filter |
| globalId | Gateway/Framework |
| serviceId | Client 요청 + Framework 검증 |

### [FACT]

`GatewayJwtValidator`는 `headerUserStrict=true`인 경우 Header userId와 JWT userId가 다르면 401을 반환한다.

`tcf-core/AuthenticationContextValidator`도 JWT/Auth Context의 userId/branchId/channelId와 Standard Header를 비교한다.

이는 **Client Header Spoofing 방지에 중요한 이중 검증**이다.

---

## 13. Authorization Architecture

현재 `tcf-core/AuthorizationValidator`는 최소 수준으로 branchId 존재 여부를 확인한다.

따라서 다음은 아직 별도 권한 아키텍처가 필요하다.

```text
User
 ↓
Auth Group / Role
 ↓
Menu
 ↓
Function
 ↓
ServiceId
 ↓
Data Scope
 ↓
Masking
```

[P0 OPEN]

- ServiceId 권한
- 메뉴/기능 권한
- 데이터 접근범위
- 고객정보 마스킹
- 관리자/운영자 권한 분리

---

## 14. Security Audit

최소 Security Event:

```text
LOGIN_SUCCESS / FAIL
SSO_SUCCESS / FAIL
TOKEN_ISSUE
TOKEN_REFRESH
TOKEN_ROTATE
TOKEN_REVOKE
LOGOUT
KEY_ROTATE
JWT_VALIDATION_FAIL
HEADER_CLAIM_MISMATCH
AUTHORIZATION_DENIED
SESSION_INVALID
```

모든 이벤트는 다음을 연결해야 한다.

```text
GUID / TraceId
userId
serviceId
jti
sessionId
clientIp
channelId
host/JVM
reason
```

---

## 15. Security Architecture Rules

| Rule ID | Rule | 상태 |
|---|---|---|
| SEC-001 | Private Key는 Issuer 영역 밖으로 배포 금지 | 필수 |
| SEC-002 | Runtime Ephemeral RSA Key는 운영 사용 금지 | **GAP** |
| SEC-003 | JWT는 RS256 + iss/aud/exp/jti 검증 | 구현 |
| SEC-004 | JWKS는 `kid` 기반 Key Version을 지원 | 부분/GAP |
| SEC-005 | Refresh Token 원문 DB 저장 금지 | 구현(Hash) |
| SEC-006 | Refresh Rotation/Revoke 적용 | 구현 |
| SEC-007 | Header user/branch/channel은 Auth Context와 정합성 검증 | 구현 |
| SEC-008 | Gateway 우회 가능 경로는 Application 자체 검증 필요 | 검증 필요 |
| SEC-009 | Token Revoke/Denylist를 모든 보호 진입점에서 강제 | 검증 필요 |
| SEC-010 | Session/JWT Hybrid Mode를 운영 ADR로 명문화 | Open |
| SEC-011 | ServiceId 권한을 Authorization 모델에 포함 | Open |
| SEC-012 | Security 이벤트 Audit Log 필수 | 부분 |

---

## 16. G50 Security 판정

**CONDITIONAL PASS**

핵심 구조는 Source에서 상당 부분 구현되었다.

그러나 운영 승인 전 P0:

1. KMS/HSM 기반 Key SoT
2. Key Rotation/kid Versioning
3. SSO Assertion Verification Owner
4. Denylist Enforcement Path
5. Session vs JWT 운영모드
6. ServiceId/Data Authorization

을 확정해야 한다.
