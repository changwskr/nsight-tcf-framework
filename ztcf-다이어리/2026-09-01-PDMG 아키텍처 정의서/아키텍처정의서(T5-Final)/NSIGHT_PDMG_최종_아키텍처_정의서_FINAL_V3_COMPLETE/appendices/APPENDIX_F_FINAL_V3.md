# 별첨 F. Security / SSO / JWT / Session

## F.1 Current Trust Flow

```text
Login / SSO
 ↓
pdmg-jwt
 ↓
Access / Refresh Token
 ↓
Business Runtime Verification
 ↓
Principal
 ↓
Business Authorization
```

## F.2 Critical GAP

```text
pdmg-jwt
RS256 Issue
   ↓
JWT
   ↓
pdmg-fw
HMAC Verify Path

[CRITICAL GAP]
```

## F.3 Key Architecture Requirements

```text
RS256
 ↓
Managed Persistent Key
 ↓
Stable kid
 ↓
JWKS
 ↓
Verifier
 ↓
Rotation / DR / Multi-instance
```

## F.4 Identity

```text
JWT subject / ssoId
      ↓
Trusted Principal
      ↓
Business User Context
      ↓
Authorization
```

Client Header의 사용자정보를 Trusted Principal과 자동 동일시하지 않는다.
