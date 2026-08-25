# CHG-SEC-002 — Versioned kid / Key Rotation / JWKS Grace Specification

> 상태: **CHANGE SPEC READY / IMPLEMENTATION NOT EXECUTED**  
> P0 연계: `P0-SEC-002`, `ADR-SEC-002`

## 1. 현재 문제

현재 Source는:

```text
kid = nsight-jwt-rs256
JWKS = 현재 Process에서 생성한 Public Key 1개
```

구조다. Key Version을 구분할 수 없고 Rotation 시 Old Token Grace를 표현할 수 없다.

## 2. Target Key Lifecycle

```text
PREPARED
   ↓ public key 먼저 배포 가능
ACTIVE
   ↓ 신규 Token 서명에 사용
PREVIOUS
   ↓ 신규 서명 금지, 기존 Token 검증만 허용
RETIRED
   ↓ JWKS 제거
REVOKED
   ↓ 사고/침해, 즉시 사용중지
```

정상 Rotation에서는 한 시점에 `ACTIVE` Signing Key는 정확히 1개다.

## 3. kid 규칙

권장 형태:

```text
<key-family>-v<version>
```

예:

```text
nsight-jwt-rs256-v20260819-01
```

필수 속성:

```text
keyFamily
kid
version
status
notBefore
notAfter(optional)
activatedAt
retireAfter
publicFingerprint
```

`kid`는 Private Key 비밀값이 아니라 Public Identifier이므로 JWT Header와 JWKS에 노출된다.

## 4. Rotation 절차

안전한 정상 Rotation 순서:

```text
1. New Key 생성/승인
2. New Public JWK를 JWKS에 PREPARED 상태로 배포
3. 모든 Validator가 새 JWKS를 볼 수 있는지 확인
4. Active Signing Key를 New kid로 전환
5. Old Key → PREVIOUS
6. JWKS = New ACTIVE + Old PREVIOUS
7. Grace Period 동안 Old Token 검증
8. Grace 종료 후 Old Public Key 제거
9. Old Key → RETIRED
```

**JWKS 배포보다 Signing 전환이 먼저 일어나면 안 된다.** Validator가 새 `kid`를 아직 모르는 Window가 생기기 때문이다.

## 5. Grace Period 계산

최소 Grace는 정적 숫자로 임의 지정하지 않고 다음 요소로 계산한다.

```text
Grace >= Access Token 최대수명
       + Clock Skew
       + JWKS Cache/Propagation 최대시간
       + 운영 Safety Margin
```

현재 Source의 Access Token 기본값은 15분, Clock Skew는 60초다. Cache/Propagation 및 Safety Margin은 운영 환경을 확인한 뒤 확정한다.

Refresh Token 수명 전체를 Public Key Grace로 유지할 필요는 없다. Refresh Token은 새 Access Token을 발급받는 별도 Credential이며 Access Token Signature 검증 Grace와 동일 개념이 아니다.

## 6. JWKS 응답

JWKS에는 Public Key만 포함한다.

정상 Grace 상태:

```json
{
  "keys": [
    {"kid": "...-v2", "use": "sig", "alg": "RS256", "kty": "RSA", "n": "...", "e": "AQAB"},
    {"kid": "...-v1", "use": "sig", "alg": "RS256", "kty": "RSA", "n": "...", "e": "AQAB"}
  ]
}
```

Private Parameter (`d`, `p`, `q`, ...)는 절대로 노출하지 않는다.

## 7. Multi-node Consistency

Issuer A/B가 서로 다른 `activeKid`를 임의 선택하면 안 된다.

Target:

```text
Central Key Metadata / Approved Config
         │
    activeKid = v2
         │
   ┌─────┴─────┐
   ▼           ▼
Issuer A     Issuer B
   │           │
   └── sign with v2 ──┘
```

Activation은 Atomic Config/Metadata 변경으로 처리한다.

## 8. Emergency Rotation

Key Compromise 시에는 정상 Grace보다 보안 우선이다.

```text
Compromised Key → REVOKED
JWKS에서 즉시 제거 가능
기존 Access Token 강제 무효화
필요 시 User Re-authentication
```

Emergency Rotation은 정상 Rotation Runbook과 분리한다.

## 9. Source 변경 포인트

| Class | 변경 |
|---|---|
| `JwtTokenIssuer` | 고정 `JwtKeyConfiguration.KEY_ID` 제거 |
| `JwkSetController` | Provider가 가진 Active+Previous Public Set 반환 |
| `JwtKeyConfiguration` | 고정 `KEY_ID` 제거/Local Provider 내부로 이동 |
| 신규 Provider | Active/Previous Key metadata 제공 |
| 운영 Config | `active-kid`, key refs, rotation metadata |

## 10. RUN-JWT-ROTATE 시나리오

### Test A — Normal Rotation

1. v1 Active로 Token T1 발급
2. v2 Public JWK PREPUBLISH
3. v2 Active 전환
4. Token T2의 `kid=v2` 확인
5. Grace 중 T1/T2 모두 Gateway 검증 성공
6. Grace 종료 후 v1 제거
7. T1이 아직 유효기간 내더라도 승인된 정책대로 실패하는지 확인

### Test B — Multi-node

- Issuer A/B가 동일 Active kid 사용
- A/B Rolling Restart 중에도 Active kid 불일치 0

### Test C — Restart

- 재기동 전/후 승인 없이 kid 변경 0

### Test D — JWKS Cache

- Gateway JWKS Cache 갱신 전후 새 Token 검증 Window 측정
- `unknown kid` 오류 0을 목표로 Pre-publish 시간을 정함

## 11. Acceptance Criteria

- [ ] 고정 단일 `kid` 제거
- [ ] Active Key 정확히 1개
- [ ] JWKS Active + Previous Grace 지원
- [ ] New Public Key Pre-publish 후 Signing 전환
- [ ] Multi-node activeKid 일치
- [ ] Restart 시 Key Identity 유지
- [ ] Private Key JWKS 노출 0
- [ ] Normal/Emergency Rotation Runbook 승인
- [ ] `RUN-JWT-ROTATE` PASS
