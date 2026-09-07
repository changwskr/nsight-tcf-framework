# NSIGHT / PDMG 아키텍처 정의서 — VII. Security / SSO / JWT / Session Architecture

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 대상: PDMG Authentication / JWT / SSO / Authorization / Session AS-IS Reference + NSIGHT TO-BE Security Boundary  
> 문서 상태: **Draft / Evidence-First**  
> 작성일: 2026-08-31  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_VI_Standard_Message_Context_Error_Logging.md`

---

# 0. Evidence Register

| ID | 근거 자료 | 본 장 사용 목적 | 상태 |
|---|---|---|---|
| EV-VII-01 | `24장.JWT 인증 전체 구조.md` | 로그인, Access/Refresh, RS256, RSA Key, JWKS, Rotation, Logout, Denylist, SSO, HMAC Internal Call | `[PDMG AS-IS EVIDENCE]` |
| EV-VII-02 | `25장.업무 요청의 JWT 검증.md` | pdmg-service 업무 요청 검증 AS-IS, HMAC/RS256 단절, DefaultFilter, ServiceContext 연계 Gap | `[PDMG AS-IS EVIDENCE]` |
| EV-VII-03 | `NSIGHT_PDMG_아키텍처_정의서_VI_Standard_Message_Context_Error_Logging.md` | Header User와 검증된 JWT 사용자 Trust 연결 | `[CURRENT BASELINE DRAFT]` |
| EV-VII-04 | `NSIGHT_PDMG_아키텍처_정의서_IV_PDMG_Online_Runtime_TCF_Flow.md` | DefaultFilter/SecurityFilterChain 위치 | `[CURRENT BASELINE DRAFT]` |
| EV-VII-05 | `NSIGHT_PDMG_아키텍처_정의서_III_PDMG_Module_Application_Architecture.md` | `pdmg-ui`, `pdmg-jwt`, `pdmg-fw`, `pdmg-service` 모듈 경계 | `[CURRENT BASELINE DRAFT]` |
| EV-VII-06 | `2026-08-17-NSIGHT_전체_아키텍처_통합분석_정의_마스터_프롬프트.md` | NSIGHT Target 보안 원칙: IdP→Token Issuer→Public Key Verify→TCF/권한 | `[WORKING TARGET BASELINE]` |
| EV-VII-07 | `NSIGHT_PDMG_아키텍처_인포그래픽_이미지화_마스터_프롬프트.md` | VII장 필수 View / Private Key / Token Log 금지 | `[WORKING BASELINE]` |

> **본 장의 가장 중요한 판정**
>
> ```text
> [AS-IS 발급]
> pdmg-jwt
> → RS256
> → RSA Private Key
> → JWKS Public Key 제공
>
> [AS-IS 업무 검증]
> pdmg-fw JwtProvider
> → HMAC SecretKey
> → jwt.secret
> → JWKS 미사용
>
> 따라서
>
> RS256 발급
> ≠
> 현재 업무 검증과 호환
> ```
>
> 이 차이를 하나의 “현재 JWT 구조”로 합쳐서 설명하지 않는다.

---

# 1. Current Security Snapshot

현재 PDMG Source에서 확인되는 보안 흐름은 크게 세 개다.

```text
A. 일반 로그인 / JWT 발급
사용자
 → pdmg-ui
 → pdmg-jwt
 → 사용자/비밀번호 검증
 → Access Token + Refresh Token

B. 일반 업무 요청 검증
pdmg-ui / Client
 → Authorization Header [일반 업무 UI는 현재 자동첨부 Gap]
 → pdmg-service DefaultFilter
 → pdmg-fw JwtProvider(HMAC)
 → request.ssoId

C. SSO 연계 토큰 발급
내부 연계자
 → pdmg-jwt SSO 발급 API
 → Service / Timestamp / HMAC / IP 검증
 → 사용자정보
 → PDMG Token Pair
```

이 세 흐름은 서로 목적이 다르다.

---

# 2. Figure Plan

| FIG | 제목 | Level | 목적 | 필수 |
|---|---|---:|---|---|
| FIG-VII-01 | Security / Authentication Big Picture | L0~L3 | 전체 Trust Boundary | Y |
| FIG-VII-02 | pdmg-ui / pdmg-jwt / pdmg-fw / pdmg-service Responsibility | L1~L2 | 보안 책임 분리 | Y |
| FIG-VII-03 | General Login AS-IS | L2~L3 | 사용자→Token Pair | Y |
| FIG-VII-04 | User / Password Validation | L2~L3 | BCrypt/사용자상태 | Y |
| FIG-VII-05 | Access Token Issue | L2~L3 | RS256 Claim/Signature | Y |
| FIG-VII-06 | JWT Header / Claim Map | L2 | `alg/kid/iss/aud/sub/jti/...` | Y |
| FIG-VII-07 | Refresh Token Issue / Store | L2~L3 | Random Plain→Hash DB | Y |
| FIG-VII-08 | Refresh Rotation | L3~L4 | 재사용 방지/Family | Y |
| FIG-VII-09 | Private Key / Public Key / JWKS Boundary | L2~L4 | 비대칭 Trust | Y |
| FIG-VII-10 | Current Business Request JWT Verification AS-IS | L2~L4 | DefaultFilter HMAC | Y |
| FIG-VII-11 | RS256 Issuer vs HMAC Verifier Mismatch | L3~L5 | 현재 핵심 GAP | Y |
| FIG-VII-12 | JWKS Verification TO-BE | L2~L4 | Target 검증 Pipeline | Y |
| FIG-VII-13 | Authenticated Principal → ServiceContext / Header | L2~L4 | Trust Source 정렬 | Y |
| FIG-VII-14 | Authentication vs Authorization vs Transaction Control | L2~L4 | 세 판단 분리 | Y |
| FIG-VII-15 | SSO Internal Issuance Flow | L2~L4 | HMAC/Timestamp/IP | Y |
| FIG-VII-16 | Internal Secret vs RSA Key vs jwt.secret | L2~L4 | 자격증명 분리 | Y |
| FIG-VII-17 | Logout / Denylist / Revocation | L2~L4 | 강제폐기 | Y |
| FIG-VII-18 | Session / Token State Boundary | L2~L4 | Browser/Server state | Y |
| FIG-VII-19 | Key Lifecycle / Rotation | L2~L5 | 현재 키 생성 Risk / TO-BE | Y |
| FIG-VII-20 | Security Failure / Attack Surface | L4 | 위조/만료/XSS/우회 | Y |
| FIG-VII-21 | Security Audit / Logging Boundary | L3~L5 | Token/Secret 로그 금지 | Y |
| FIG-VII-22 | VIII장 Handoff | L5 | Physical/HA/Capacity 연결 | Y |

---

# 3. 핵심 결론

VII장의 핵심 결론은 다음과 같다.

1. **PDMG에서 인증 발급과 업무 검증은 현재 하나의 완성된 체계로 닫혀 있지 않다.**
2. `pdmg-jwt`는 현재 RS256 Access Token을 발급하며 RSA Private Key로 서명하고 Public Key를 JWKS로 공개한다.
3. `pdmg-fw JwtProvider`는 현재 `jwt.secret` 기반 HMAC 검증을 수행하며 JWKS를 사용하지 않는다.
4. 따라서 `pdmg-jwt`에서 정상 발급한 RS256 토큰이 현재 `pdmg-service`의 HMAC 검증기에 의해 정상적으로 검증될 수 없다는 것이 Source 기반 판정이다.
5. `pdmg-ui` JWT 관리 화면은 Token Pair를 `sessionStorage`에 저장하지만 일반 업무용 `service-client.js`는 현재 Authorization Header를 자동 첨부하지 않는 것으로 분석된다.
6. 비-local 일반 JSON 업무요청에서 `DefaultFilter`는 Bearer Token을 요구하지만 local Profile은 JWT 검사를 건너뛰고 multipart도 별도 분기로 JWT 검사를 수행하지 않는 AS-IS Gap이 있다.
7. 현재 HMAC 검증이 성공하면 `sub`를 `request.ssoId`에 저장하지만, 이 값이 `ServiceContext.userContext`, Header `optr_eno`, MDC `userId`의 권위값으로 연결되지 않는다.
8. 즉 **Authentication 성공과 Business Identity 반영 사이에 단절**이 있다.
9. Client가 제공한 Header User ID를 거래통제의 권위값으로 사용하면 JWT 인증을 우회할 수 있으므로 TO-BE에서는 검증된 Principal이 권위값이 되어야 한다.
10. Access Token의 기본 정책은 현재 Source 분석에서 15분, Refresh Token은 8시간으로 확인되며 DB 정책으로 Runtime 갱신될 수 있다. 이 값은 현재 PDMG AS-IS이며 NSIGHT 최종 정책으로 자동 승격하지 않는다.
11. Refresh Token은 JWT가 아니라 난수 불투명 문자열이며 DB에는 SHA-256 Hash만 저장된다.
12. Refresh Rotation/Revocation/Denylist 저장 기능은 `pdmg-jwt`에 존재하지만 현재 업무 검증 `pdmg-fw JwtProvider`가 Denylist를 조회하지 않으므로 강제폐기가 업무 요청에 완전히 연결되지 않았다.
13. SSO 발급 거래는 외부 IdP의 OIDC Callback 검증이 아니라 **신뢰된 내부 호출자의 HMAC/Timestamp/IP/허용서비스를 검증한 뒤 PDMG Token Pair를 발급하는 내부 연계 구조**다.
14. 내부 호출 HMAC Secret, JWT 발급 RSA Private Key, `pdmg-fw` HMAC `jwt.secret`은 서로 다른 자격증명이며 재사용해서는 안 된다.
15. 현재 RSA Key는 애플리케이션 기동 때 메모리에서 새로 생성되며 동일 `kid`를 사용하므로 재시작/다중 인스턴스/키회전 관점에서 Target 운영구조로 부적합하다.
16. NSIGHT TO-BE는 발급 Private Key를 중앙 키 관리체계에 두고 업무 검증기는 JWKS Public Key만 사용하도록 하는 비대칭 Trust를 지향해야 한다.
17. 인증(Authentication), 인가(Authorization), 거래통제(Transaction Control)는 서로 다른 판단이다.
18. JWT가 유효해도 해당 ServiceId를 실행할 권한이 있다는 의미는 아니다.
19. Session 제거와 인증 상태관리 제거는 같은 의미가 아니다. Refresh Token, Denylist, Token Family, 권한상태, 강제로그아웃 등 Server-side State가 남을 수 있다.
20. Security Architecture는 “토큰을 발급하는 것”에서 끝나지 않고 **발급 → 저장 → 전송 → 검증 → 신원전파 → 인가 → 폐기 → 감사 → 키회전**의 전체 Lifecycle로 닫혀야 한다.

---

# 4. 목적 / 범위 / 전제

## 4.1 목적

본 장은 다음 질문에 답한다.

1. 사용자는 어떤 경로로 PDMG에 로그인하는가?
2. 사용자와 비밀번호는 누가 검증하는가?
3. Access Token은 누가 어떤 방식으로 발급하는가?
4. Access Token에는 어떤 Claim이 있는가?
5. Refresh Token은 어떻게 생성·저장·회전·폐기되는가?
6. RSA Private Key와 Public Key는 어디에 있는가?
7. JWKS Endpoint는 무엇을 공개하는가?
8. 현재 업무 Request의 Bearer Token은 누가 검증하는가?
9. 현재 검증 알고리즘과 발급 알고리즘이 왜 호환되지 않는가?
10. 검증된 사용자정보는 ServiceContext/Header/MDC에 어떻게 전달되는가?
11. Authentication과 Authorization은 어떻게 구분되는가?
12. ServiceId/사용자/권한 그룹은 거래통제에서 어떻게 사용되어야 하는가?
13. SSO 연계 발급 API는 무엇을 검증하는가?
14. 외부 IdP Token을 PDMG가 직접 검증하는가?
15. Logout, Refresh Revocation, Access Denylist는 어떻게 동작하는가?
16. Browser Session/Token State와 Server State의 경계는 무엇인가?
17. 다중 인스턴스/재시작/키회전은 어떤 영향을 주는가?
18. Token/Secret/Private Key를 어떻게 로그·운영에서 보호해야 하는가?
19. VIII장 Physical/HA에서 어떤 Security Runtime 결정을 검증해야 하는가?

## 4.2 포함

```text
pdmg-ui
pdmg-jwt
pdmg-fw
pdmg-service
login
PasswordEncoder / BCrypt
Access Token
Refresh Token
RS256
RSA Private Key
Public Key
JWKS
kid
iss
aud
sub
jti
iat
exp
type=ACCESS
userId
branchId
channelId
authGroupId
DefaultFilter
JwtProvider
HMAC jwt.secret
Authorization Bearer
request.ssoId
ServiceContext.userContext
Denylist
Refresh Rotation
Logout
SSO internal issuance
JwtInternalCallValidator
Timestamp
HMAC
IP allowlist
Secret
Key Rotation
Security Audit
```

## 4.3 제외

```text
L4/L7 Network Security 상세          → VIII
WEB/WAS TLS termination               → VIII
KMS/HSM 제품 선정                     → VIII / 별도 Security ADR
OM Security Dashboard                 → IX
ServiceId 권한 Matrix 전체 자동검증   → X
개인정보 DB Field 암호화 상세         → 별도 Security Standard
```

---

# 5. FIG-VII-01 — Security / Authentication Big Picture

```text
┌──────────────────────────── User / Browser ──────────────────────────────┐
│                                                                          │
│ Login Credential                                                         │
│ Access Token                                                             │
│ Refresh Token                                                            │
│                                                                          │
└──────────────────────────────┬───────────────────────────────────────────┘
                               │
                               ▼
┌──────────────────────────── pdmg-ui ─────────────────────────────────────┐
│                                                                          │
│ Login UI                                                                 │
│ Token Session                                                            │
│ Business Client                                                          │
│                                                                          │
└─────────────┬───────────────────────────────────┬────────────────────────┘
              │ Login                             │ Business Request
              ▼                                   ▼
┌──────────────────── pdmg-jwt ─────────────────┐   ┌────────────────────┐
│                                               │   │ pdmg-service       │
│ User Verification                             │   │                    │
│ Token Issue                                   │   │ DefaultFilter      │
│ Refresh / Revoke                              │   │ JWT Verify         │
│ Denylist                                      │   │ Context            │
│ JWKS                                          │   │ Authorization      │
│                                               │   │ TCF / Business     │
└───────┬───────────────────┬───────────────────┘   └─────────┬──────────┘
        │                   │                                  │
        │ Private Key       │ Public Key/JWKS                  │
        │                   │                                  │
        ▼                   └─────────────────────── TO-BE ────┘
   Token Signature

              [Current GAP]
     RS256 issuer ─────── X ────── HMAC verifier
```

---

# 6. FIG-VII-02 — 보안 책임 분리

```text
pdmg-ui
= Credential Input / Token Use / UI Session

pdmg-jwt
= Authentication
  User Verification
  Token Issue
  Refresh
  Revocation
  JWKS
  SSO Token Pair Issue

pdmg-fw
= Business Request Authentication Gate
  DefaultFilter
  JWT Validation
  Context Adapter

pdmg-service
= Business Authorization / Use Case
  + TCF / Transaction Control

pdmg-om
= [Security 운영기능 Current Evidence UNKNOWN]
```

## 6.1 책임 Matrix

| 주체 | 소유 책임 | 소유하면 안 되는 책임 |
|---|---|---|
| UI | 로그인 입력, Token 사용 | 비밀번호 Hash 검증, Private Key |
| JWT Server | Credential 검증, Token 발급 | 업무 Service 권한판단 전체 |
| FW | Token 검증, 신원 Context | JWT 발급 Private Key |
| Business | 업무 인가/권한 사용 | Token 서명 |
| Security/Operations | Key/Secret/Audit 정책 | 업무 Rule |

---

# 7. FIG-VII-03 — General Login AS-IS

현재 로그인 거래:

```text
User
  ↓
/jwt/admin/login.html
  ↓
jwt-admin.js
  ↓
ServiceId = mgjwa1000C0
  ↓
POST pdmg-jwt /online
  ↓
DefaultFilter
  ↓
OnlineTransactionController
  ↓
TransactionDispatcher
  ↓
mgjwa1000Handler
  ↓
mgjwa1000Facade
  ↓
mgjwa1000Service.mgjwa1000C0
  ↓
User / Password Validate
  ↓
Issue Token Pair
  ↓
Response
  ↓
sessionStorage["pdmg.jwt.session"]
```

## 7.1 TCF ON/OFF

TCF ON:

```text
/online
→ Handler
```

TCF OFF:

```text
/mgjwa1000C0
→ Business Controller
```

보안 모듈에서도 III/IV장의 TCF Mode 원칙이 적용된다.

---

# 8. FIG-VII-04 — User / Password Validation

```text
Login Request
   │
   ├─ userId 존재?
   └─ password 존재?
         │
         ▼
       DAO
         │
         ▼
    User 조회
         │
         ├─ 없음
         ├─ useYn != Y
         ├─ password hash 없음
         └─ PasswordEncoder.matches 실패
                 │
                 ▼
             Authentication Error
                 │
                 └─ 사용자 존재여부 과도 노출 방지

정상
  ↓
PasswordEncoder.matches
  ↓
Login Success
  ├─ 최종 로그인 시간 갱신
  └─ 성공이력
```

## 8.1 AS-IS에서 확인하지 못한 것

```text
실패 횟수 증가
Account Lockout
잠금 해제
MFA
CAPTCHA
Adaptive Authentication
```

은 현재 Source Evidence에서 확인되지 않았으므로 현행 기능으로 쓰지 않는다.

---

# 9. Password Security

현재 Source는 `PasswordEncoder.matches()` 사용을 보여 준다.

분석자료는 BCrypt 검증 구조로 설명한다.

중요:

```text
Plain Password
→ Request에서만 일시 사용
→ DB 저장 X
→ Token Claim X
→ Log X
```

Password Hash 알고리즘/Strength의 정확한 현재 Bean 설정은 Source 재검증 대상이다.

`[OPEN-VII-01]`

---

# 10. FIG-VII-05 — Access Token Issue

```text
Authenticated User
   │
   ▼
issueTokenPair()
   │
   ├─ new jti
   │
   ▼
JwtTokenIssuer.issueAccessToken()
   │
   ├─ Header
   │   ├─ alg = RS256
   │   ├─ typ = JWT
   │   └─ kid = nsight-jwt-rs256 [AS-IS]
   │
   ├─ Standard Claims
   │   ├─ iss
   │   ├─ aud
   │   ├─ sub
   │   ├─ jti
   │   ├─ iat
   │   └─ exp
   │
   ├─ Business Claims
   │   ├─ type = ACCESS
   │   ├─ userId
   │   ├─ userName
   │   ├─ branchId
   │   ├─ channelId
   │   └─ authGroupId
   │
   ▼
RSASSASigner
   │
   ▼
RSA Private Key
   │
   ▼
Access Token
```

## 10.1 Current TTL

Source 분석 기준 기본값:

```text
Access Token = 15분
Refresh Token = 8시간
```

`JwtSecurityProperties` 초기값 / `application.yml`과 Runtime Policy가 관련된다.

이 값은 `[AS-IS CURRENT POLICY]`다.

NSIGHT 최종 Token TTL Decision은 별도 ADR이다.

---

# 11. FIG-VII-06 — JWT Header / Claim Map

```text
JWT
├─ Header
│  ├─ alg : RS256
│  ├─ typ : JWT
│  └─ kid : nsight-jwt-rs256 [AS-IS]
│
├─ Standard Claim
│  ├─ iss
│  ├─ aud
│  ├─ sub
│  ├─ jti
│  ├─ iat
│  └─ exp
│
└─ Business Claim
   ├─ type = ACCESS
   ├─ userId
   ├─ userName
   ├─ branchId
   ├─ channelId
   └─ authGroupId
```

## 11.1 Claim은 암호화되지 않는다

JWT Claim:

```text
Base64URL Encoding
≠ Encryption
```

따라서 넣지 않는다.

```text
Password
주민번호
Secret
Private Data 원문
Internal Key
```

## 11.2 최소 Claim 원칙

현재 `userName` 등의 Claim이 존재하더라도 TO-BE에서는:

```text
업무 검증에 꼭 필요한가?
로그/브라우저 노출을 감수할 가치가 있는가?
```

를 검토한다.

---

# 12. Access Token 저장 / 관리 Row

Source 분석은 Access Token 발급 시 별도 관리정보를 저장하는 구조를 설명한다.

```text
tokenId
jti
issuer
user
branch
channel
authGroup
issuedAt
expiresAt
client information
```

중요:

```text
DB Token Row
≠
JWT Signature Validation
```

DB Row는:

```text
Audit
Revocation
Policy
Management
```

을 돕는다.

전자서명은 Token 자체의 위조 여부를 확인한다.

---

# 13. FIG-VII-07 — Refresh Token Issue / Store

```text
issueTokenPair()
   │
   ▼
JwtSupport.newRefreshTokenPlain()
   │
   ├─ opaque random string
   │
   ▼
Client
   └─ Refresh Token Plain
          │
          ▼
       SHA-256
          │
          ▼
DB
├─ refresh hash
├─ user
├─ token family
├─ issuedAt
├─ expiresAt
├─ client info
└─ revoked/rotated state
```

## 13.1 핵심 원칙

```text
Refresh Token 원문
→ Client에 1회 전달

DB
→ Hash만 저장
```

즉 DB 유출만으로 바로 Refresh Token을 재사용하기 어렵게 한다.

## 13.2 Refresh Token은 JWT가 아니다

현재 Source 기준:

```text
Access Token
= JWT

Refresh Token
= opaque random string
```

따라서 Refresh Token에 JWT Claim 검증을 적용하는 구조로 설명하지 않는다.

---

# 14. Refresh Token Validation

갱신 거래는 개념적으로:

```text
Refresh Plain
   ↓
Hash
   ↓
DB Row Lookup
   ↓
Revoked?
Rotated?
Expired?
User Active?
   │
   ├─ Fail → Reject
   └─ Pass
       ↓
    New Token Pair
```

Access Token의 만료와 Refresh Token의 만료는 서로 다른 정책이다.

---

# 15. FIG-VII-08 — Refresh Rotation

```text
Old Refresh
   │
   ▼
Hash Lookup
   │
   ├─ already rotated?
   ├─ revoked?
   └─ expired?
       │
       ▼
mark rotated
   │
   ▼
same familyId
   │
   ▼
New Refresh
   │
   ▼
Store Hash
```

## 15.1 AS-IS

현재 Source 분석은 기존 Refresh를 `rotatedYn` 상태로 바꾸고 같은 family ID의 새 Refresh를 저장하는 구조를 보여 준다.

## 15.2 Current Gap

탈취된 과거 Refresh Token이 재사용되는 경우:

```text
해당 Token Reject
```

는 확인되지만:

```text
Family 전체 즉시 revoke
Security Event
```

가 자동 수행되는지는 확인되지 않는다.

`[GAP-VII-01]`

TO-BE Strong Rotation 후보:

```text
Refresh reuse detected
   ↓
Revoke entire family
   ↓
Deny related Access token jti
   ↓
Security Alert
```

---

# 16. Browser Token Storage AS-IS

JWT 관리 UI는 현재:

```text
sessionStorage
key = pdmg.jwt.session
```

에:

```text
Access Token
Refresh Token
Token Type
User Information
```

를 저장한다.

## 16.1 Risk

```text
XSS
   ↓
JavaScript sessionStorage 읽기
   ↓
Access + Refresh Token 탈취
```

`[RISK-VII-01]`

## 16.2 TO-BE 후보

분석자료는 운영에서:

```text
Access Token
→ 짧은 수명

Refresh Token
→ HttpOnly + Secure + SameSite Cookie 검토
```

를 제안한다.

그러나 Cookie 전환은:

```text
CSRF
Refresh API
Cookie Scope
SameSite
Domain
```

을 함께 설계해야 한다.

---

# 17. General Business UI Token Propagation Gap

현재 JWT 관리 UI가 Token을 저장하는 것과 일반 업무 UI가 Token을 사용하는 것은 다르다.

PDMG 분석:

```text
jwt-admin.js
→ Token sessionStorage 사용

_shared/service-client.js
→ Content-Type / Accept
→ Authorization Header 자동 추가 없음
```

따라서:

```text
로그인 성공
≠
일반 업무 요청이 인증됨
```

`[GAP-VII-02]`

---

# 18. FIG-VII-09 — Private / Public / JWKS Boundary

```text
┌────────────────────── pdmg-jwt ──────────────────────┐
│                                                       │
│  RSA Private Key                                      │
│       │                                               │
│       │ sign                                          │
│       ▼                                               │
│  Access Token                                         │
│                                                       │
│  Private Key는 이 Boundary 밖으로 배포하지 않는다.  │
└──────────────────────────┬────────────────────────────┘
                           │
                           │ public parameters only
                           ▼
                   /.well-known/jwks.json
                           │
                           ▼
                    Public Key / kid
                           │
                           │ verify
                           ▼
              pdmg-service / Gateway / Verifier
```

## 18.1 원칙

```text
Private Key
→ Sign only
→ Issuer Boundary

Public Key
→ Verify only
→ Verifier Boundary
```

Public Key를 가진 업무서비스는 Token을 위조할 수 없다.

---

# 19. Current RSA Key Generation Risk

현재 `JwtKeyConfiguration`은 2048-bit RSA Key를 **애플리케이션 기동 때 메모리에서 새로 생성**하는 것으로 분석된다.

```text
pdmg-jwt start
   ↓
new RSA key
   ↓
kid = same string
   ↓
JWKS expose
```

## 19.1 Restart Risk

```text
Token A issued
   ↓
pdmg-jwt restart
   ↓
New RSA Key
same kid
   ↓
Old Token A
→ New Public Key
→ verify fail
```

## 19.2 Multi-instance Risk

```text
JWT Instance #1
kid=K / Key=A

JWT Instance #2
kid=K / Key=B

Load Balancer
   ↓
JWKS response
어느 인스턴스?
```

같은 `kid`에 다른 Key가 생길 수 있다.

`[RISK-VII-02]`

---

# 20. JWKS AS-IS

`JwkSetController`:

```text
GET /.well-known/jwks.json
   ↓
jwtSigningKey.toPublicJWK()
   ↓
JWKSet JSON
```

공개되는 것:

```text
RSA Public Parameter
kid
```

포함되지 않는 것:

```text
Private Key
```

## 20.1 Current Important Gap

`pdmg-service/pdmg-fw`는 현재 이 JWKS를 사용해 업무 Token을 검증하지 않는다.

```text
JWKS Endpoint 존재
≠
업무서비스가 JWKS 검증 중
```

---

# 21. FIG-VII-10 — Current Business Request JWT Verification AS-IS

```text
Business Browser
   │
   │ Authorization: Bearer <token>
   ▼
DefaultFilter
   │
   ├─ OPTIONS
   │    └─ skip
   │
   ├─ multipart
   │    └─ JWT 검사 없음 [AS-IS]
   │
   ├─ profile == local
   │    └─ JWT 검사 없음 [AS-IS]
   │
   └─ non-local JSON
        │
        ├─ Bearer 존재?
        │
        ├─ JwtProvider.validate()
        │    └─ HMAC jwt.secret
        │
        ├─ isAccessToken()
        │    └─ type=ACCESS
        │
        ├─ getSsoId()
        │    └─ sub
        │
        └─ request.setAttribute("ssoId")
```

## 21.1 중요한 설정 판정

현재 `jwt.enabled`가 존재하더라도 Filter가 이 값을 읽어 활성/비활성을 결정하는 구조로 분석되지 않는다.

실제 분기:

```text
DefaultFilter enabled
+
active profile local/non-local
```

이 중요하다.

---

# 22. local / multipart 면제 Risk

## local

```text
profile=local
→ JWT 검사 전체 skip
```

개발 편의일 수 있으나 운영 프로파일 오배포 시 Critical Risk다.

## multipart

```text
multipart
→ 별도 Context 생성
→ JWT 검사 없음
```

보호 File Upload API가 있다면 인증 우회가 될 수 있다.

`[RISK-VII-03]`

TO-BE:

```text
OPTIONS
→ CORS 예외

Public Endpoint
→ 명시적 Security Policy

Multipart
→ 보호 API면 동일 인증
```

로 최소화한다.

---

# 23. HMAC Verifier AS-IS

현재 `JwtProvider`:

```text
jwt.secret
   ↓
SecretKey
   ↓
JJWT Parser
   ↓
Signed Claims Parse
```

검사:

```text
Signature
Expiration
type=ACCESS
sub 추출
```

현재 명시검증이 확인되지 않은 것:

```text
issuer
audience
nbf
clock skew policy
denylist
JWKS kid
```

---

# 24. FIG-VII-11 — RS256 Issuer vs HMAC Verifier Mismatch

```text
pdmg-jwt
   │
   │ RSA Private Key
   ▼
RS256 Access Token
   │
   │ Authorization
   ▼
pdmg-service
   │
   ▼
DefaultFilter
   │
   ▼
JwtProvider
   │
   │ HMAC SecretKey
   ▼
Signature Validation
   │
   └─ Algorithm / Key Type Mismatch
          ↓
        401
```

## 24.1 Current Matrix

| 항목 | 발급 AS-IS | 검증 AS-IS |
|---|---|---|
| Algorithm | RS256 | HMAC |
| Key | RSA Private | SecretKey |
| Public Key | JWKS 제공 | 미사용 |
| `iss`/`aud` | Token Claim 존재 | 명시검증 없음 |
| Denylist | DB 기능 있음 | 업무경로 미연결 |
| `type` | ACCESS | 검사 |
| `exp` | 발급 | Parser 검사 |

## 24.2 금지 해결책

```text
pdmg-jwt Private Key를
pdmg-service에 복사
           X
```

그렇게 하면 업무서비스도 Token 발급/위조 능력을 갖게 된다.

---

# 25. FIG-VII-12 — JWKS Verification TO-BE

```text
Authorization: Bearer Access Token
             │
             ▼
         Token Header
             │
             ├─ alg allowed?
             └─ kid
             │
             ▼
        JWKS Key Cache
             │
             ├─ kid found
             │     ↓
             │  Public Key
             │
             └─ unknown kid
                   ↓
                JWKS refresh policy
             │
             ▼
       RS256 Signature Verify
             │
             ▼
          Claim Verify
             │
             ├─ iss
             ├─ aud
             ├─ exp
             ├─ iat
             ├─ type=ACCESS
             └─ sub
             │
             ▼
          jti denylist
             │
             ▼
     AuthenticatedPrincipal
             │
             ▼
 ServiceContext.userContext / MDC
             │
             ▼
 Authorization / Transaction Control
             │
             ▼
            TCF
```

`[TO-BE/PROPOSED]`

---

# 26. JWKS Cache 운영정책

TO-BE에서는 다음을 결정해야 한다.

```text
JWKS Cache TTL
unknown kid 재조회
JWKS network 장애
old key 유지기간
new key propagation
clock skew
denylist 장애
```

예:

```text
JWKS endpoint 일시 장애
+
기존 cached key 존재
```

일 때:

```text
기존 Key 계속 사용?
얼마나?
```

를 정책으로 정해야 한다.

현재 값은 Source에서 확정되지 않았다.

---

# 27. FIG-VII-13 — Authenticated Principal → ServiceContext

## 27.1 AS-IS

```text
Validated Token
   ↓
sub
   ↓
request.ssoId

BUT

ServiceContext.userContext
= 빈 Map 중심

MDC.userId
= hdr_nhnis.sys_comm.optr_eno

Transaction Control User
= Header/MDC 기반 가능
```

즉:

```text
검증된 JWT User
≠
현재 업무 Context의 User 권위값
```

## 27.2 TO-BE

```text
JWT Verified Claims
      │
      ▼
AuthenticatedPrincipal
├─ userId
├─ branchId
├─ channelId
├─ authGroupId
├─ issuer
├─ jti
└─ token metadata
      │
      ▼
ServiceContext.userContext
      │
      ├─ MDC userId
      ├─ Authorization
      └─ Transaction Control
```

## 27.3 Header와 교차검증

```text
Verified Principal.userId
         │
         ├─ Header optr_eno 없음
         │     → Principal 값 사용
         │
         ├─ Header와 동일
         │     → 진행
         │
         └─ Header와 불일치
               → Reject / Audit
```

Header가 Principal보다 권위가 높아서는 안 된다.

---

# 28. Header User Spoofing Risk

현재 표준 Header:

```text
optr_eno
tr_brc
```

등은 Client 전문에서 들어올 수 있다.

인증된 JWT의:

```text
sub
branchId
authGroupId
```

와 분리돼 있으면 공격자가 Header만 바꿔:

```text
다른 사용자/영업점으로 보이는 로그
거래통제 기준 우회
```

를 시도할 수 있다.

`[RISK-VII-04]`

---

# 29. FIG-VII-14 — Authentication vs Authorization vs Transaction Control

```text
[Authentication]
이 Token/User는 누구인가?
       │
       ▼
AuthenticatedPrincipal
       │
       ▼
[Authorization]
이 사용자가 이 ServiceId/기능을 실행할 권한이 있는가?
       │
       ▼
Authorized
       │
       ▼
[Transaction Control]
현재 시간/IP/영업점/서비스 상태에서
이 거래를 실행할 수 있는가?
       │
       ▼
Allowed
       │
       ▼
TCF / Business
```

## 29.1 서로 대체할 수 없다

```text
JWT valid
≠
Service 권한 있음

Service 권한 있음
≠
현재 거래통제 통과

거래통제 통과
≠
JWT valid
```

---

# 30. 현재 Spring Security `permitAll`

분석자료에서 `pdmg-jwt`와 업무 Application의 Spring Security 설정에 `permitAll()` 성격이 확인된다.

이것은:

```text
Filter / 별도 인증로직이 존재
```

할 수 있다는 의미이지:

```text
모든 API 무조건 공개가 보안 Target
```

이라는 의미가 아니다.

특히 JWT 관리 API:

```text
정책 변경
폐기
관리 조회
```

까지 PermitAll이면 별도 인가가 필요하다.

`[GAP-VII-03]`

---

# 31. ServiceId Authorization

TO-BE에서 최소 권한단위 후보:

```text
User / Role / AuthGroup
           │
           ▼
      ServiceId
```

예:

```text
authGroupId
   ↓
Allowed ServiceId Set
   ↓
mgcoaXXXXS0?
```

현재 PDMG 전체 ServiceId 권한 Matrix와 실제 Enforcement Source는 본 장 Evidence에서 완전히 확인되지 않는다.

`[OPEN-VII-02]`

X장에서 ServiceId Trace/권한 연계를 검토한다.

---

# 32. Data Authorization

기능 권한과 데이터 권한은 다르다.

```text
ServiceId 실행 가능
        │
        ▼
고객 A 데이터 조회 가능?
영업점 B 데이터 수정 가능?
```

JWT Claim에 Branch/AuthGroup이 있다고 모든 Data Authorization을 Token만으로 해결하지 않는다.

고위험/변경가능 권한은 Server-side Authority를 재조회할 수 있다.

---

# 33. Claim Staleness

Access Token은 발급 시점의 Claim을 갖는다.

발급 후:

```text
권한 변경
영업점 변경
사용자 중지
```

가 생길 수 있다.

따라서:

```text
Token Valid
BUT
Current Authority Changed
```

가 가능하다.

대응 Option:

```text
짧은 Access TTL
Denylist
권한 Version Claim
Server-side Authorization Lookup
High-risk Service 재조회
```

현재 어떤 방식을 최종 채택할지는 `[OPEN]`이다.

---

# 34. FIG-VII-15 — SSO Internal Issuance Flow

현재 SSO 거래 `mgjwa1000C1`은 외부 IdP OIDC Callback 자체가 아니다.

```text
Trusted Internal Caller
     │
     ├─ Service Name
     ├─ Timestamp
     ├─ HMAC Signature
     ├─ Source IP
     └─ User Information
     │
     ▼
JwtInternalCallValidator
     │
     ├─ 허용 Service?
     ├─ Timestamp 허용오차?
     ├─ HMAC Valid?
     └─ IP Allowlist?
     │
     ▼
User Resolution
     │
     ├─ DB User 존재 → 저장 사용자 우선
     └─ 특정 issuer prefix 등 신뢰조건
             ↓
        trusted user map
             │
             ▼
       PDMG Token Pair
```

---

# 35. SSO에서 확인되지 않은 것

현재 SSO 발급 API가 직접 검증한다고 확인되지 않은 것:

```text
OIDC authorization code
IdP Access Token signature
OIDC ID Token signature
nonce
state
PKCE
IdP audience
IdP issuer exact validation
```

따라서:

```text
PDMG pdmg-jwt
= OIDC Relying Party
```

라고 단정하지 않는다.

`[OPEN-VII-03]`

상위 SSO/IdP 계층의 책임인지 명확히 문서화해야 한다.

---

# 36. SSO Trust Boundary

현재 구조의 신뢰는:

```text
External IdP 자체
```

가 아니라:

```text
Internal SSO Integration Caller
```

의 검증에 집중되어 있다.

즉 Security 질문은:

```text
누가 이 Internal Caller를 신뢰시키는가?
그 Caller가 외부 SSO 인증을 이미 검증했는가?
```

이다.

이 upstream evidence가 필요하다.

---

# 37. FIG-VII-16 — Secret / Key 종류 분리

```text
1. JWT RSA Private Key
   목적: RS256 Access Token Sign
   소유: pdmg-jwt
   공유: 금지

2. JWKS Public Key
   목적: RS256 Verify
   소유: issuer가 공개
   공유: verifier에게 가능

3. pdmg-fw jwt.secret
   목적: Current HMAC JWT Verify
   소유: 현행 verifier
   Target: RS256/JWKS 전환 후 제거 후보

4. Internal SSO Shared Secret
   목적: 내부 발급 API Caller HMAC
   소유: pdmg-jwt + 승인 Caller
   JWT RSA Key와 별개

5. Refresh Token
   목적: Token Refresh credential
   Client가 Plain 보유
   DB는 Hash
```

## 37.1 절대 재사용 금지

```text
Internal SSO HMAC Secret
=
JWT HMAC Secret
=
RSA Private Key

X
```

각 Credential의 Blast Radius를 분리해야 한다.

---

# 38. Internal Call Validator

현재 `JwtInternalCallValidator`는 분석자료상:

```text
Service Allowlist
Timestamp
HMAC
IP Allowlist
```

를 검증한다.

HMAC 입력은 요청 본문 정규화 문자열과 Timestamp를 포함하는 구조로 설명된다.

정확한 Canonical String Format은 현재 본 장에서 원문을 재출력하지 않는다.

---

# 39. Internal HMAC Replay Protection

Timestamp 검사는 Replay Window를 줄일 수 있지만:

```text
동일 Timestamp + 동일 Signature
```

가 허용시간 안에서 재사용 가능한지 여부는 nonce/request-id 저장까지 보아야 한다.

현재 Source에서 별도 Nonce Cache의 존재는 확인되지 않았다.

`[OPEN-VII-04]`

---

# 40. Internal Secret Current Risk

AS-IS 설정/Properties에 개발용 기본 Secret 문자열이 존재한다는 분석이 있다.

운영 Target:

```text
Default Secret 없음
환경변수/Secret Store
미설정 시 Start Fail
Rotation
Audit
```

으로 강화할 필요가 있다.

실제 Secret 값은 어떤 문서/로그에도 쓰지 않는다.

---

# 41. FIG-VII-17 — Logout / Revocation / Denylist

## 41.1 Logout AS-IS

```text
UI
  ↓
Logout Request
  ↓
pdmg-jwt
  ├─ Access Token 존재
  │    ↓
  │  jti denylist
  │
  └─ Refresh Token 존재
       ↓
     hash lookup
       ↓
     revokedAt 기록
  ↓
UI finally
  ↓
sessionStorage clear
```

## 41.2 별도 Revoke

`mgjwa1000D0`:

```text
Access Token 원문
또는
jti
  ↓
Denylist
```

분석자료는 Access Token 원문으로 jti/exp/sub를 꺼내는 경로에서 해당 Method 자체가 Signature를 검증하지 않을 수 있음을 지적한다.

관리 API 접근통제가 중요하다.

---

# 42. Denylist

Denylist 의미:

```text
Access Token exp 아직 남음
BUT
jti revoked
  ↓
업무 요청 거부
```

## 42.1 Current Disconnect

`pdmg-jwt`:

```text
Denylist 저장/조회 기능 존재
```

하지만 현재 `pdmg-fw JwtProvider`:

```text
Denylist 조회 없음
```

따라서:

```text
Logout / Forced Revoke
→ 업무서비스에서 즉시 효력?
```

이 현재는 완전히 연결되어 있지 않다.

`[GAP-VII-04]`

---

# 43. Denylist Failure Policy

TO-BE JWKS 검증 시:

```text
Signature Valid
  ↓
Denylist Check
  ↓
Denylist DB Down
```

일 때:

```text
Fail-open?
Fail-closed?
Cache?
```

결정이 필요하다.

고가용성과 보안을 동시에 고려해야 한다.

`[OPEN-VII-05]`

---

# 44. FIG-VII-18 — Session / Token State Boundary

```text
Browser State
┌─────────────────────────────┐
│ Access Token                │
│ Refresh Token [AS-IS JS]    │
│ UI User Info                │
└─────────────┬───────────────┘
              │
              ▼
Server Security State
┌─────────────────────────────┐
│ User DB                     │
│ Token Management Row        │
│ Refresh Token Hash          │
│ Token Family                │
│ Revocation                  │
│ Denylist                    │
│ Runtime Security Policy     │
└─────────────────────────────┘
```

JWT를 사용한다고 Server State가 완전히 사라지는 것이 아니다.

---

# 45. JWT와 HTTP Session

현재 JWT Architecture의 목적은 Server HTTP Session에 인증 상태를 강하게 묶지 않는 방향이다.

그러나:

```text
HTTP Session 없음
=
Authentication State 없음

X
```

이다.

남는 State:

```text
Refresh Token Store
Denylist
User Status
Auth Group
Runtime Policy
Audit
```

## 45.1 `pdmg-ui` sessionStorage와 HTTP Session도 다르다

```text
Browser sessionStorage
≠
Server HttpSession
```

---

# 46. Forced Logout

강제로그아웃은 단순 UI Storage 삭제가 아니다.

TO-BE 의미:

```text
Access jti deny
+
Refresh revoke/family revoke
+
필요 시 current authority change
+
Client 401/Logout
```

다중 Device/Browser에서 어느 범위를 강제종료할지는 정책이 필요하다.

```text
특정 Token?
특정 Device?
Token Family?
User 전체?
```

`[OPEN-VII-06]`

---

# 47. FIG-VII-19 — Key Lifecycle / Rotation

## AS-IS

```text
Application Start
   ↓
New RSA Key
   ↓
fixed kid
   ↓
Issue Token
   ↓
JWKS Public
```

## TO-BE

```text
Approved Key Store / KMS / HSM [제품 미확정]
          │
          ▼
Active Private Key
          │
          ├─ kid=K2
          └─ pdmg-jwt sign
          │
          ▼
JWKS
├─ K2 current
└─ K1 previous [grace]
          │
          ▼
Verifier Cache
          │
          ▼
Old Token + New Token 모두 검증
          │
          ▼
K1 token expiry
          │
          ▼
K1 remove
```

## 47.1 Key Rotation의 세 시점

```text
1. New Key 발급 시작
2. Old Token 검증 병행
3. Old Key 제거
```

이 세 시점을 Test해야 한다.

---

# 48. kid 정책

현재 `kid = nsight-jwt-rs256` 고정 문자열로 분석된다.

Key를 회전하면서 같은 kid를 계속 쓰면:

```text
Cache
Multi-instance
Old Token
```

문제가 발생할 수 있다.

TO-BE에서는 Key Version마다 고유 `kid`를 사용해야 한다.

`[ADR 후보]`

---

# 49. Issuer / Audience

Access Token에는 현재 `iss`, `aud` Claim이 존재한다.

그러나 현재 `pdmg-fw` HMAC 검증기는 명시적으로 issuer/audience를 제한하지 않는 것으로 분석된다.

TO-BE:

```text
Signature valid
+
iss expected
+
aud contains target
```

을 모두 검증해야 한다.

---

# 50. type / Token Confusion

현재 업무 API는:

```text
type == ACCESS
```

를 검사한다.

이것은 Token Type Confusion을 줄이는 좋은 방어다.

Refresh Token은 opaque라서 업무 Bearer Token으로 사용할 수 없다.

TO-BE에서도 유지한다.

---

# 51. sub vs userId

현재 Access Token에는:

```text
sub
userId
```

가 모두 존재한다.

이 둘의 불일치 가능성과 Single Source of Truth를 정의해야 한다.

권장 후보:

```text
sub = canonical authenticated user identifier

userId = 중복 제거 또는 표시/호환 목적 명확화
```

현재 공식 Decision은 `[OPEN]`.

---

# 52. branchId / authGroupId / channelId

이 Claim은 업무 인가에 유용하지만 변경될 수 있다.

질문:

```text
Token 발급 후 branch 변경?
AuthGroup 변경?
Channel 권한 변경?
```

따라서 고위험 권한을 Claim 하나만으로 장시간 신뢰하지 않는다.

---

# 53. Authorization Source Priority

TO-BE 후보:

```text
1. Verified Principal
2. Server-side Current Authority
3. Header = compatibility metadata
4. Client UI state = trust 없음
```

Header/Client 값을 Principal보다 먼저 신뢰하지 않는다.

---

# 54. Security Context

현재 Filter는 Spring Security `Authentication` 객체를 완전하게 구축하는 Resource Server 구조가 아니라 request attribute `ssoId` 중심이다.

TO-BE 후보:

```text
AuthenticatedPrincipal
   ↓
Security Context / ServiceContext
   ↓
Authorization
```

Spring Security Authentication을 사용할지 자체 Context를 유지할지는 ADR 대상이다.

---

# 55. Gateway vs Business WAR Verification

NSIGHT Target 원칙:

```text
Gateway
  ↓ JWT verify
Business WAR
```

만으로 충분한지 여부는 **Gateway 우회 가능성**에 달려 있다.

## 우회 불가가 보장되는 경우

```text
Network
mTLS
Firewall
Service Mesh
Private Routing
```

등으로 직접접근이 차단되면 Gateway 검증을 신뢰할 수 있다.

## 우회 가능

```text
Business WAR 직접 URL 가능
```

하면:

```text
Business WAR 자체 JWT 검증
```

이 필요하다.

현재 PDMG Source에서는 Business `DefaultFilter`가 검증점이므로 이 방향과 정합적이다.

---

# 56. Double Verification

Gateway와 Business가 모두 JWT를 검증한다고 해서 항상 낭비는 아니다.

목적:

```text
Gateway
= Edge reject / routing trust

Business
= Defense in depth / direct access protection
```

다만 동일 Token을 서로 다른 알고리즘/정책으로 검증하면 안 된다.

JWKS/issuer/audience 정책 SSOT가 필요하다.

---

# 57. CORS와 Authorization Header

일반 업무 UI가 Bearer를 전송하려면 CORS에서:

```text
Authorization
```

Header가 허용되어야 한다.

현재 분석에서는 `allowed-headers: "*"`가 이를 포함한다.

하지만:

```text
* 허용
```

이 최종 보안정책인지 여부는 별도 판단이다.

Production에서는 최소 허용 Header/Origin 정책을 검토한다.

---

# 58. Public Endpoint

로그인/Refresh/JWKS 같은 Endpoint는 기존 Access Token이 없어도 호출되어야 할 수 있다.

반면:

```text
관리정책 변경
Token revoke
User 관리
```

는 인증/인가되어야 한다.

따라서 URL 문자열 하드코딩 예외가 아니라 **Endpoint Policy Catalog**가 필요하다.

```text
PUBLIC
AUTHENTICATED
ADMIN
INTERNAL
```

`[PROPOSED]`

---

# 59. Multipart Security

현재 DefaultFilter multipart branch가 JWT 검증을 수행하지 않는 분석이 있다.

파일 업로드가 보호업무라면:

```text
multipart
→ Authentication same policy
```

여야 한다.

파일 내용 검증은 별도지만 인증을 면제할 이유가 되지 않는다.

---

# 60. Security Error Contract

VI장과 연결:

```text
Missing Token
Invalid Token
Expired Token
Revoked Token
Forbidden
Transaction Control Reject
```

는 서로 내부 원인은 달라도 Client에 과도한 보안정보를 노출하지 않는다.

예:

```text
외부
401 Unauthorized

내부
AUTH_TOKEN_EXPIRED
AUTH_SIGNATURE_INVALID
AUTH_KID_UNKNOWN
```

같은 분리 정책을 검토한다.

현재 Filter `sendError()`는 표준 `result` Envelope를 우회할 수 있으므로 VI의 `StandardErrorWriter` ADR과 연결한다.

---

# 61. Token Logging

절대 Log 금지:

```text
Authorization Header
Access Token Raw
Refresh Token Raw
RSA Private Key
Internal HMAC Secret
jwt.secret
Password
```

안전한 추적 후보:

```text
GUID
ServiceId
jti [정책상 허용]
issuer
verification result code
key id
userId masked/controlled
```

---

# 62. FIG-VII-21 — Security Audit Boundary

```text
Authentication Event
├─ login success
├─ login failure
├─ token issue
├─ refresh
├─ refresh reuse
├─ logout
├─ revoke
└─ invalid token
       │
       ▼
Security Audit
       │
       ├─ timestamp
       ├─ GUID
       ├─ user [controlled]
       ├─ jti [safe]
       ├─ client / IP
       ├─ event type
       └─ result
```

Security Audit는 VI장의 일반 ImageLog와 책임이 겹칠 수 있지만 동일한 것은 아니다.

---

# 63. Audit에 남기면 안 되는 것

```text
Password
Token Raw
Secret
Private Key
Full HMAC Input with sensitive payload
```

Refresh Token Hash도 반드시 운영 로그에 출력할 필요는 없다.

---

# 64. Login History

현재 Source 분석은 로그인 성공/실패 이력을 저장하는 동작을 설명한다.

이를 Security Audit와 어떻게 통합할지는 IX장 운영 Governance에서 결정한다.

현재 Table/Retention 값은 본 장에서 창작하지 않는다.

---

# 65. Security Failure Scenario A — 정상 로그인

```text
User
 ↓
Login
 ↓
User/BCrypt valid
 ↓
RS256 Access
+
Opaque Refresh
 ↓
Token Pair
 ↓
UI Storage
```

---

# 66. Security Failure Scenario B — Password invalid

```text
User
 ↓
Password mismatch
 ↓
Authentication Error
 ↓
Failure History
 ↓
No Token
```

사용자 존재 여부가 과도하게 드러나지 않도록 동일 오류계열 사용이 분석된다.

---

# 67. Security Failure Scenario C — RS256 Token to Current HMAC Verifier

```text
Valid RS256 Access Token
   ↓
DefaultFilter
   ↓
HMAC JwtProvider
   ↓
Signature mismatch
   ↓
401
```

이것이 현재 핵심 Integration Gap이다.

---

# 68. Security Failure Scenario D — Expired Token

현재 HMAC Parser에서는 `exp` 만료가 parse failure로 처리될 수 있다.

TO-BE JWKS Verifier에서도:

```text
Signature valid
BUT
exp expired
→ 401
```

이다.

Refresh Token이 별도로 유효하면 Client는 Refresh Flow를 수행할 수 있다.

---

# 69. Security Failure Scenario E — Token Denied

TO-BE:

```text
Signature valid
Claim valid
   ↓
jti denylist hit
   ↓
401 / revoked
```

현재 업무 검증 경로에는 Denylist가 연결되지 않은 Gap이 있다.

---

# 70. Security Failure Scenario F — Header User Mismatch

```text
JWT sub = USER-A

Header optr_eno = USER-B
        │
        ▼
TO-BE
Mismatch Reject
+
Security Audit
```

현재는 자동 연결/검증이 완성되지 않았다.

---

# 71. Security Failure Scenario G — Unknown kid

TO-BE:

```text
Token kid=K3
   ↓
JWKS cache miss
   ↓
one refresh?
   │
   ├─ key found → verify
   └─ still missing → reject
```

재조회 횟수/DoS 방어는 정책이 필요하다.

---

# 72. Security Failure Scenario H — JWKS unavailable

```text
Verifier
   ↓
JWKS fetch fail
   │
   ├─ valid cached key?
   └─ no key?
```

Fail-open으로 Signature 검증을 생략해서는 안 된다.

캐시 정책/가용성은 VIII장 HA와 연계한다.

---

# 73. Security Failure Scenario I — Refresh Reuse

```text
Old Refresh
already rotated
   ↓
reuse attempt
   ↓
Reject
   ↓
[TO-BE] family revoke + alert
```

현재 family 전체 revoke 여부는 Gap이다.

---

# 74. Security Failure Scenario J — SSO HMAC invalid

```text
Internal Caller
  ↓
HMAC mismatch
  ↓
SSO Token Pair 발급 거부
```

외부 IdP 인증과는 다른 실패다.

---

# 75. FIG-VII-20 — Security Attack Surface

```text
[Browser]
XSS
 └─ sessionStorage Token 탈취

[Transport]
Authorization 누락/탈취
CORS 설정오류

[Filter]
local/multipart 인증 우회

[JWT]
RS256/HMAC 정책 불일치
iss/aud 미검증
denylist 미연결

[Header]
optr_eno 위조
JWT Principal 미연결

[SSO]
Internal HMAC Secret 노출
Replay
IP spoof/Proxy 고려

[Key]
기동시 임시키 생성
same kid / different key
Private Key 배포

[Refresh]
Reuse
family revoke 미완성

[Admin API]
permitAll
revoke/policy 관리 접근통제

[Log]
Token/Secret/Stack 노출
```

---

# 76. AS-IS vs TO-BE 전체 비교

| 주제 | AS-IS | TO-BE 후보 |
|---|---|---|
| Access Issue | RS256 | 유지 |
| Private Key | 기동 시 메모리 생성 | KMS/HSM/Secret Key Store |
| kid | 고정 | Key Version별 고유 |
| JWKS | 제공 | 검증 SSOT |
| Business Verify | HMAC jwt.secret | RS256/JWKS |
| iss/aud | 발급 | 검증 필수 |
| denylist | JWT Server DB 기능 | Business Verify 연결 |
| Principal | request.ssoId | Typed AuthenticatedPrincipal |
| Header User | Client 제공 | Principal 교차검증 |
| UI Token | sessionStorage | Threat model 기반 개선 |
| Refresh DB | Hash | 유지/강화 |
| Rotation | Token reuse reject | Family revoke 강화 |
| SSO | Internal HMAC/IP | Upstream IdP trust 명확화 |
| local/multipart | 면제 | 승인 Policy 기반 |
| Error | Filter sendError | Standard Error |
| Admin Security | permitAll 가능 | Endpoint Authorization |

---

# 77. Current GAP

| ID | GAP | 영향 |
|---|---|---|
| GAP-VII-01 | Refresh reuse 시 family 전체 revoke 미확인 | Token theft |
| GAP-VII-02 | 일반 업무 UI Authorization Header 자동첨부 없음 | non-local 업무 인증 단절 |
| GAP-VII-03 | JWT/관리 API의 Spring Security permitAll 성격 | Authorization |
| GAP-VII-04 | Denylist가 업무 검증 경로에 미연결 | Forced Logout 무효 |
| GAP-VII-05 | RS256 발급 vs HMAC 업무검증 불일치 | Authentication 불가 |
| GAP-VII-06 | JWT `sub`가 ServiceContext/MDC 권위값으로 미연결 | Identity spoof |
| GAP-VII-07 | Header User와 JWT Principal 불일치 검증 없음 | Transaction Control |
| GAP-VII-08 | issuer/audience 명시 검증 없음 | Cross-token misuse |
| GAP-VII-09 | JWKS Verifier/Cache 미구현 | RS256 Target 미완성 |
| GAP-VII-10 | RSA Key가 기동 시 새로 생성됨 | Restart/Multi-instance |
| GAP-VII-11 | 고정 kid + 변경 Key | Rotation/Cache |
| GAP-VII-12 | local Profile JWT 면제 | 운영 오배포 Risk |
| GAP-VII-13 | multipart JWT 면제 | File API bypass |
| GAP-VII-14 | SSO upstream IdP 검증 책임 미확정 | Trust Chain |
| GAP-VII-15 | Internal HMAC replay nonce 정책 미확정 | Replay |
| GAP-VII-16 | Admin/Revoke/Policy API 인가 미확정 | Security Admin |
| GAP-VII-17 | Token Storage 운영정책 미확정 | XSS/CSRF |
| GAP-VII-18 | ServiceId Authorization Matrix 미확정 | Function Authorization |
| GAP-VII-19 | Data Authorization 정책 미확정 | Data Security |
| GAP-VII-20 | Security Audit와 ImageLog 통합/분리 정책 미확정 | Audit |

---

# 78. Current RISK

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-VII-01 | sessionStorage Access+Refresh Token XSS 노출 | Critical |
| RISK-VII-02 | same kid + per-process RSA Key | Critical |
| RISK-VII-03 | local/multipart JWT 우회 | Critical |
| RISK-VII-04 | Header User Spoofing | Critical |
| RISK-VII-05 | RS256/HMAC 불일치 | Critical |
| RISK-VII-06 | Denylist 미연결 | High |
| RISK-VII-07 | issuer/audience 미검증 | High |
| RISK-VII-08 | Private Key 메모리 임시생성 | High |
| RISK-VII-09 | Default Secret 운영잔존 | Critical |
| RISK-VII-10 | Admin API permitAll | Critical |
| RISK-VII-11 | Refresh Reuse Family 미폐기 | High |
| RISK-VII-12 | JWT Claim PII 과다 | Medium/High |
| RISK-VII-13 | Internal HMAC Replay | High |
| RISK-VII-14 | Token/Secret Log | Critical |
| RISK-VII-15 | Upstream SSO trust 불명확 | High |
| RISK-VII-16 | JWKS 장애 정책 미정 | High |

---

# 79. OPEN Issue

| ID | 질문 |
|---|---|
| OPEN-VII-01 | 현재 PasswordEncoder 실제 알고리즘/strength는 무엇인가 |
| OPEN-VII-02 | ServiceId Authorization Matrix의 공식 Owner/Store는 무엇인가 |
| OPEN-VII-03 | 외부 IdP/OIDC 검증은 어느 시스템이 책임지는가 |
| OPEN-VII-04 | Internal SSO HMAC에 nonce/replay store가 필요한가 |
| OPEN-VII-05 | Denylist 장애 시 fail-open/fail-closed 정책은 무엇인가 |
| OPEN-VII-06 | 강제로그아웃 범위는 token/family/device/user 중 무엇인가 |
| OPEN-VII-07 | Access Token 최종 TTL은 얼마인가 |
| OPEN-VII-08 | Refresh Token 최종 TTL은 얼마인가 |
| OPEN-VII-09 | Refresh Token Browser 저장 방식을 Cookie로 바꿀 것인가 |
| OPEN-VII-10 | Authorization을 Spring Security Context로 통합할 것인가 ServiceContext 기반으로 둘 것인가 |
| OPEN-VII-11 | Gateway와 Business WAR 이중 JWT 검증을 표준으로 할 것인가 |
| OPEN-VII-12 | JWKS Cache TTL/unknown kid refresh 정책은 무엇인가 |
| OPEN-VII-13 | Key Store/KMS/HSM 제품과 배포방식은 무엇인가 |
| OPEN-VII-14 | 고위험 Service는 Server-side 권한 재조회를 할 것인가 |
| OPEN-VII-15 | Branch/AuthGroup Claim 변경을 어떤 방식으로 반영할 것인가 |
| OPEN-VII-16 | multipart 보호 API 인증정책은 무엇인가 |
| OPEN-VII-17 | Public/Admin/Internal Endpoint 분류를 어떻게 관리할 것인가 |
| OPEN-VII-18 | Security Audit의 저장/Retention/SIEM 연계는 무엇인가 |

---

# 80. ADR 후보

| ADR | 결정 주제 |
|---|---|
| ADR-VII-01 | PDMG Business JWT Verify를 JWKS/RS256으로 전환 |
| ADR-VII-02 | RSA Private Key 중앙 Key Store 도입 |
| ADR-VII-03 | Key Rotation / kid Version 정책 |
| ADR-VII-04 | AuthenticatedPrincipal 모델 |
| ADR-VII-05 | Header User vs JWT Principal 우선순위 |
| ADR-VII-06 | Denylist 업무요청 연결 |
| ADR-VII-07 | Refresh Reuse Family Revoke |
| ADR-VII-08 | Browser Token Storage 전략 |
| ADR-VII-09 | local/multipart 인증면제 정책 |
| ADR-VII-10 | ServiceId Authorization Enforcement |
| ADR-VII-11 | Gateway + Business Defense-in-depth |
| ADR-VII-12 | SSO Upstream IdP Trust Contract |
| ADR-VII-13 | Internal HMAC Secret 관리 / Replay |
| ADR-VII-14 | Public/Admin/Internal Endpoint Policy |
| ADR-VII-15 | Security Error Standard Writer |
| ADR-VII-16 | Security Audit SSOT |

---

# 81. TO-BE Security Architecture 후보

```text
User
  ↓
SSO / Login
  ↓
Trusted Authentication Result
  ↓
pdmg-jwt
  │
  ├─ User Status
  ├─ Current Policy
  └─ RSA Private Key from Key Store
  ↓
RS256 Access Token
+
Opaque Refresh Token
  │
  ▼
Client
  │ Authorization: Bearer
  ▼
Gateway [optional/target]
  │ Public Key / JWKS Verify
  ▼
pdmg-service DefaultFilter
  │ JWKS Verify / defense in depth
  ▼
AuthenticatedPrincipal
  │
  ├─ Header Cross-check
  ├─ ServiceContext
  └─ MDC
  ▼
Authorization
  │
  ├─ ServiceId permission
  └─ Data permission
  ▼
Transaction Control
  ▼
TCF / Business
```

---

# 82. TO-BE Token Lifecycle

```text
Login
 ↓
Access + Refresh
 ↓
Business Requests
 ↓
Access Expiry
 ↓
Refresh Rotation
 ↓
New Access
 ↓
Logout / Revoke
 ↓
Denylist / Refresh Revoke
 ↓
Expiry / Cleanup
```

Token Lifecycle이 DB Cleanup/Retention까지 닫혀야 한다.

---

# 83. TO-BE Key Lifecycle

```text
Generate / Import
 ↓
Approve
 ↓
Store Securely
 ↓
Activate kid=K2
 ↓
Issue K2
 ↓
JWKS K1+K2
 ↓
Old K1 Token Expire
 ↓
Remove K1 Public
 ↓
Retire / Destroy Private K1
 ↓
Audit
```

---

# 84. Security Architecture Rules

## 84.1 Must

1. PDMG AS-IS RS256 발급과 HMAC 검증을 하나의 완성된 현재 구조로 표현하지 않는다.
2. Private Key를 `pdmg-service`, UI, Git, 일반 설정에 배포하지 않는다.
3. Authorization Header/Access/Refresh Token 원문을 로그하지 않는다.
4. Refresh Token 원문을 DB에 저장하지 않는다.
5. Header `optr_eno`를 검증된 인증사용자보다 우선 신뢰하지 않는다.
6. JWT Payload Decode만으로 사용자를 인증하지 않는다.
7. Access Token은 허용 알고리즘/서명/만료/type을 검증한다.
8. TO-BE RS256에서는 `iss/aud/sub/kid` 정책을 검증한다.
9. Business WAR가 직접 접근 가능하면 업무 WAR 검증을 제거하지 않는다.
10. JWKS Public Key와 RSA Private Key 책임을 분리한다.
11. Internal HMAC Secret과 JWT Key를 재사용하지 않는다.
12. `local` 인증면제를 운영정책으로 남기지 않는다.
13. 보호 multipart API를 인증에서 면제하지 않는다.
14. Logout은 Browser Storage 삭제만으로 완료했다고 보지 않는다.
15. Denylist 저장기능 존재와 업무 검증 적용을 구분한다.
16. `permitAll`을 “인가 설계 완료”로 해석하지 않는다.
17. SSO 발급 API가 외부 OIDC를 직접 검증한다고 Source 없이 쓰지 않는다.
18. 기동 때 생성되는 RSA Key를 Production HA Key Architecture로 인정하지 않는다.

## 84.2 Should

1. JWKS Verifier를 공통 Framework Component로 구현한다.
2. AuthenticatedPrincipal을 immutable Type으로 정의한다.
3. JWT Principal과 Header 사용자값을 교차검증한다.
4. Denylist/권한 정책에 적절한 Cache와 장애정책을 둔다.
5. Key Store/KMS/HSM 기반 Key Lifecycle을 정의한다.
6. Refresh Reuse 시 Token Family revoke를 검토한다.
7. Public/Admin/Internal Endpoint Policy를 코드/설정 SSOT로 관리한다.
8. Security Audit를 GUID/jti/ServiceId 기반으로 추적한다.
9. UI Token Storage를 XSS/CSRF Threat Model 기준으로 결정한다.
10. Key Rotation을 자동 Integration Test한다.

---

# 85. Security Test Scenario

## 85.1 Login Success

```text
Valid user/password
Expect
  Access Token RS256
  Refresh opaque
  DB refresh hash only
  no password/token log
```

## 85.2 Invalid Password

```text
Expect
  no token
  generic auth failure
  failure audit
```

## 85.3 Valid RS256 → Current HMAC

```text
Expect AS-IS
  401
```

이 Test는 현재 단절을 증명한다.

## 85.4 JWKS TO-BE

```text
Valid RS256
correct kid/iss/aud/exp/type/sub
denylist miss
Expect
  Principal
  Business entry
```

## 85.5 Wrong Signature

```text
Expect
  401
  no business
  no token raw log
```

## 85.6 Expired

```text
Expect
  401
  refresh path possible
```

## 85.7 Wrong issuer

TO-BE:

```text
valid signature
wrong iss
Expect 401
```

## 85.8 Wrong audience

```text
Expect 401
```

## 85.9 Denylist

```text
valid token
jti revoked
Expect 401
```

## 85.10 Header Identity mismatch

```text
sub=A
optr_eno=B
Expect
  reject/audit
```

## 85.11 local Profile Production Guard

```text
Production deployment
active profile contains local
Expect
  startup fail / deployment gate fail
```

## 85.12 Multipart protected API

```text
no token
multipart
Expect TO-BE
  401
```

## 85.13 Refresh Rotation

```text
old refresh use
→ new refresh

old refresh reuse
Expect
  reject
  [TO-BE] family revoke
```

## 85.14 Restart Key Test

AS-IS:

```text
issue token
restart jwt
verify old token with new JWKS
Expected risk: fail
```

이 Test로 현재 임시 Key 구조를 증명할 수 있다.

## 85.15 Multi-instance Key Test

```text
JWT #1 and #2
same kid
compare JWKS modulus
```

AS-IS 위험 검증.

## 85.16 SSO HMAC Invalid

```text
Expect
  token pair not issued
```

## 85.17 SSO Replay

현재 Policy 검증 목적:

```text
same signed request within allowed timestamp
repeat
```

Nonce 정책 필요 여부 평가.

---

# 86. Architecture Conformance Rule 후보

```text
RULE-VII-01
No RSA Private Key outside pdmg-jwt issuer boundary

RULE-VII-02
No raw token/secret logging

RULE-VII-03
Non-local protected request must carry Authorization Bearer

RULE-VII-04
Verified principal must be propagated to ServiceContext

RULE-VII-05
Header user must not override verified principal

RULE-VII-06
Every protected ServiceId must have authorization policy

RULE-VII-07
Production profile must not enable local auth bypass

RULE-VII-08
Protected multipart endpoint must authenticate

RULE-VII-09
JWKS verifier must validate alg/iss/aud/exp/type/sub

RULE-VII-10
Revoked jti must be rejected

RULE-VII-11
Refresh plain token must never be stored server-side

RULE-VII-12
Key rotation must use distinct kid

RULE-VII-13
Default internal secret is prohibited in production

RULE-VII-14
Administrative token APIs require explicit authorization

RULE-VII-15
SSO internal call must validate service/timestamp/HMAC/IP policy
```

---

# 87. Security Operation Metric 후보

IX장으로 넘길 최소 Metric:

```text
login_success_count
login_failure_count
token_issue_count
refresh_success_count
refresh_failure_count
refresh_reuse_count
logout_count
denylist_hit_count
jwt_verify_success_count
jwt_verify_failure_count{reason}
jwt_expired_count
jwt_wrong_issuer_count
jwt_wrong_audience_count
jwt_unknown_kid_count
jwks_refresh_count
jwks_refresh_failure_count
auth_header_missing_count
identity_mismatch_count
authorization_denied_count
transaction_control_denied_count
sso_hmac_failure_count
sso_timestamp_failure_count
sso_ip_denied_count
security_secret_default_detected
```

현재 `pdmg-om`에 구현돼 있다고 단정하지 않는다.

---

# 88. Traceability

## 88.1 VI → VII

```text
VI Header optr_eno
       ↓
VII Principal Cross-check

VI Filter 401
       ↓
VII JWT Verification

VI Sensitive Log
       ↓
VII Token/Secret Protection

VI GUID
       ↓
VII Security Audit
```

## 88.2 VII → VIII

```text
Private Key
     ↓
Key Store / HA / Deployment

JWKS
     ↓
L4/URL/Cache/Availability

JWT Server
     ↓
JVM / Instance / Scale-out

Business Verifier
     ↓
WAR / Filter / Network

SSO Internal Caller
     ↓
Network Zone / IP / TLS
```

---

# 89. FIG-VII-22 — VIII장 Handoff

```text
VII. Security / SSO / JWT / Session
        │
        ├─ pdmg-jwt
        ├─ RS256
        ├─ Private Key
        ├─ JWKS
        ├─ Refresh / Denylist
        ├─ DefaultFilter Verify
        ├─ AuthenticatedPrincipal
        ├─ Authorization
        ├─ SSO HMAC/IP
        └─ Security Audit
                │
                ▼
VIII. Infrastructure / WAS / Capacity / HA / DR
                │
                ├─ WEB / Apache
                ├─ WAS / Tomcat
                ├─ JWT Server Deployment
                ├─ Business WAR Deployment
                ├─ L4 / GSLB
                ├─ JVM
                ├─ Hikari / DB
                ├─ Key Store Placement
                ├─ JWKS HA
                ├─ Session / Stateless
                ├─ Scale-out
                └─ DR
```

## VIII장에서 반드시 답할 질문

1. `pdmg-jwt`는 실제 어떤 Server/JVM/WAR에 배포되는가?
2. `pdmg-service`와 `pdmg-jwt`는 물리적으로 어떤 L4/URL 경계를 가지는가?
3. JWT 발급기 Scale-out 시 Private Key를 어떻게 공유하는가?
4. JWKS Endpoint는 어떻게 HA를 보장하는가?
5. Key Store/KMS/HSM은 어느 보안 Zone에 있는가?
6. Business WAR가 Gateway/L4를 우회해 직접 접근 가능한가?
7. UI/WEB/WAS TLS 종료 지점은 어디인가?
8. Refresh/Denylist DB는 어떤 HA/DR 구조를 갖는가?
9. JWT가 Stateless해도 어떤 Server-side State가 남는가?
10. Session 60분 등 기존 Session Baseline과 JWT 전환은 어떤 관계인가?
11. WAR Scale-out 시 Token/Context/Session 의존성이 있는가?
12. DR 전환 시 기존 Access/Refresh Token과 Key/JWKS는 어떻게 처리되는가?

---

# 90. Completion Gate

```text
Figure Plan                       22
실제 Text Figure                 22

Security Big Picture             PASS
Responsibility                   PASS
Login AS-IS                      PASS
Password Validation              PASS
Access Issue                     PASS
Claim Map                        PASS
Refresh Issue/Store              PASS
Rotation                         PASS
Key/JWKS Boundary                PASS
Business Verify AS-IS            PASS
RS256/HMAC Mismatch              PASS
JWKS TO-BE                       PASS
Principal Context                PASS
Auth/AuthZ/Control               PASS
SSO Internal Flow                PASS
Secret Separation               PASS
Logout/Denylist                  PASS
Session/State                    PASS
Key Lifecycle                    CONDITIONAL
Attack Surface                   PASS
Security Audit                   PASS
VIII Handoff                     PASS

Private Key 원문 노출            0건
Token 원문 노출                  0건
SSO OIDC 검증 창작               0건
RS256/HMAC 혼합                  0건
Denylist 적용 창작               0건
```

**판정: CONDITIONAL PASS**

## PASS 전환 조건

```text
Condition-VII-01
pdmg-jwt / pdmg-fw 현재 Source Snapshot의
실제 Security Bean / Endpoint / Properties 기계적 재스캔

Condition-VII-02
JWKS RS256 Business Verifier 구현/Integration Test

Condition-VII-03
AuthenticatedPrincipal → ServiceContext/MDC 연결 구현

Condition-VII-04
Header User vs JWT Principal 정합 정책 ADR

Condition-VII-05
Denylist 업무요청 검증 연결

Condition-VII-06
RSA Private Key 외부 Key Store 전환

Condition-VII-07
Key Rotation / multi-instance / restart Test

Condition-VII-08
local/multipart Authentication Bypass 정책 제거/승인

Condition-VII-09
일반 업무 UI Authorization Header 구현

Condition-VII-10
ServiceId Authorization Matrix 및 Enforcement

Condition-VII-11
SSO upstream IdP Trust Responsibility 명문화

Condition-VII-12
Admin/Revoke/Policy API Authorization 확정

Condition-VII-13
Refresh Token Browser Storage 전략 확정

Condition-VII-14
Security Audit/Retention/Monitoring 구현 증적
```

---

# 91. 장 최종 평가

VII장은 PDMG Authentication을 **발급 기능이 아니라 End-to-End Trust Lifecycle**로 재구성했다.

가장 중요한 결론은 다음과 같다.

> **현재 PDMG의 JWT 발급과 업무 검증은 완성된 하나의 체계가 아니다. `pdmg-jwt`는 RS256을 발급하지만 `pdmg-fw`는 HMAC으로 검증하므로 현재 두 경로가 호환되지 않는다.**

> **Access Token은 RSA Private Key로 서명되고 JWKS는 Public Key를 공개하지만, 현재 업무서비스가 그 JWKS를 검증에 사용하지 않는다.**

> **현재 검증된 `sub`는 request `ssoId`에만 저장되고 업무 Header/MDC/ServiceContext의 사용자 권위값으로 완전히 연결되지 않으므로 Authentication과 Business Identity 사이에 Gap이 있다.**

> **TO-BE에서는 검증된 JWT Principal이 사용자·영업점·권한그룹의 권위값이 되고 Client Header는 교차검증용 메타정보가 되어야 한다.**

> **Refresh Token은 opaque random credential이며 DB에는 Hash만 저장한다. Rotation과 Denylist 기능은 있으나 Refresh Family 강제폐기와 업무요청 Denylist Enforcement는 추가 보완이 필요하다.**

> **SSO 발급은 외부 OIDC Callback 검증이 아니라 Internal Caller의 HMAC/Timestamp/IP/허용서비스를 신뢰하는 별도 경계이므로 Upstream SSO Authentication 책임을 명확히 해야 한다.**

> **현재 RSA Key를 Process Start마다 생성하는 방식은 재시작·다중 인스턴스·키회전 관점에서 Production HA 구조로 사용할 수 없으며, 중앙 Key Store와 Versioned `kid` 정책이 필요하다.**

> **JWT를 사용한다고 인증 State가 완전히 사라지는 것은 아니다. Refresh Store, Denylist, User Status, AuthGroup, Token Family, Security Policy는 계속 Server-side State로 남는다.**

다음 VIII장에서는 이 Security 논리를 실제 인프라로 내려:

```text
VII
Authentication / JWT / Key / State
        ↓
VIII
GSLB / L4 / Apache / Tomcat / JVM / WAR
Key Store / JWKS HA / Session / Capacity / HA / DR
```

를 검증한다.
