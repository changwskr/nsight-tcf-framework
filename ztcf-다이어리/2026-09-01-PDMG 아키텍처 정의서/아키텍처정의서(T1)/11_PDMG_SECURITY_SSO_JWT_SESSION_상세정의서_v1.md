# PDMG 전체 아키텍처 정의서
# 11. PDMG SECURITY / SSO / JWT / SESSION ARCHITECTURE
## Authentication / RS256 / JWKS / Key / Refresh / Identity / Authorization / State
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-11-SECURITY-SSO-JWT-SESSION`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-11-01. 이 장의 핵심 질문

```text
PDMG의 로그인/SSO/JWT/Refresh/JWKS/Key 구조는 무엇인가?
Issuer와 Business Verifier는 같은 Trust Model을 사용하는가?
Trusted Principal, Business User, ServiceId Authorization, Session State를 어떻게 연결하는가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-11-02. Evidence Flow

```text
Source / Config
   ↓
Runtime / Deployment
   ↓
PDMG Current Analysis
   ↓
Architecture Decision
   ↓
NSIGHT Target / Working Baseline
   ↓
PASS / GAP / OPEN
```

| Evidence ID | 근거 | 사용목적 | 상태 |
|---|---|---|---|
| EV-11-01 | VII Security | login/JWT/JWKS/SSO | [AS-IS + GAP] |
| EV-11-02 | pdmg-jwt source analysis | RS256 issue/refresh | [AS-IS] |
| EV-11-03 | pdmg-fw source analysis | HMAC verifier | [AS-IS GAP] |
| EV-11-04 | 02/04 boundaries | trust/runtime node | [WORKING BASELINE] |
| EV-11-05 | Decision Register | JWT/key/session/identity | [DECISION] |

---

# 2. Figure Plan

## FIG-11-03. Top-down Drill-down

```text
L0  Overall
 ↓
L1  Boundary / Responsibility
 ↓
L2  Node / Platform / Component
 ↓
L3  Runtime / Control / Data
 ↓
L4  Sequence / Failure / Recovery
 ↓
L5  Source / Config / Evidence
```

---

# 3. L0 — Security Master

## FIG-11-04. L0 — Security Master

```text
User
 ↓
Login / SSO
 ↓
pdmg-jwt
 ↓
Access / Refresh Token
 ↓
JWKS / Key
 ↓
pdmg-service / pdmg-fw
 ↓
Verification
 ↓
Trusted Principal
 ↓
Authorization
 ↓
Business / DB
```

---

# 4. Security Responsibility

## FIG-11-05. Security Responsibility

```text
pdmg-jwt
= authentication / token issue / jwks

pdmg-fw
= request verification integration

pdmg-service
= business authorization

Ops/Security
= key/secret/audit
```

---

# 5. General Login

## FIG-11-06. General Login

```text
ServiceId mgjwa1000C0
 ↓
Credential Validation
 ↓
BCrypt
 ↓
Token Pair
```

---

# 6. SSO Internal Issue

## FIG-11-07. SSO Internal Issue

```text
ServiceId mgjwa1000C1
 ↓
Allowed Service
 + Timestamp
 + HMAC
 + Caller IP
 ↓
Token Pair

≠ generic OIDC callback
```

---

# 7. Access Token Issue

## FIG-11-08. Access Token Issue

```text
Header alg=RS256 / kid
Claims issuer/audience/sub/exp
 ↓
RSA Private Key Sign
 ↓
Access Token
```

---

# 8. Refresh Token

## FIG-11-09. Refresh Token

```text
Random Refresh Token
 ↓
Hash
 ↓
DB State
 ↓
Rotate / Revoke
```

---

# 9. JWKS / Public Key

## FIG-11-10. JWKS / Public Key

```text
Private Key
 stays issuer side

Public Key
 ↓
JWKS
 ↓
Verifier
```

---

# 10. Critical Current Mismatch

## FIG-11-11. Critical Current Mismatch

```text
pdmg-jwt
RS256 issue
   ↓
JWT
   ↓
pdmg-fw
HMAC jwt.secret verify
   ↓
[CRITICAL GAP]
```

---

# 11. Target Verification

## FIG-11-12. Target Verification

```text
Bearer
 ↓
Parse kid
 ↓
JWKS Public Key
 ↓
RS256 verify
 ↓
issuer/audience/exp
 ↓
Trusted Principal
```

---

# 12. Key Lifecycle

## FIG-11-13. Key Lifecycle

```text
Managed Key Store
 ↓
Versioned Key / kid
 ↓
Issue
 ↓
JWKS
 ↓
Rotate
 ↓
Retire

Restart / Multi-instance / DR consistent
```

---

# 13. Denylist / Revocation

## FIG-11-14. Denylist / Revocation

```text
Logout / revoke
 ↓
Denylist / Refresh state
 ↓
Verifier checks?
 [Current integration GAP]
```

---

# 14. Identity Binding

## FIG-11-15. Identity Binding

```text
JWT sub / ssoId
 ↓
Trusted Principal
 ↓
Business User Context
 ↓ compare
hdr_nhnis optr/user
 ↓
Authorization / Audit
```

---

# 15. Authorization

## FIG-11-16. Authorization

```text
Authentication success
 ≠
ServiceId execution permission

Principal
 ↓
Role / Permission
 ↓
ServiceId / Resource
 ↓
Allow / Deny
```

---

# 16. Session / State Strategy

## FIG-11-17. Session / State Strategy

```text
Client sessionStorage token [AS-IS]
 +
Refresh server state
 +
Possible HttpSession policy

Target
= minimize server session state
  unless required
```

---

# 17. Secrets Separation

## FIG-11-18. Secrets Separation

```text
SSO internal HMAC secret
≠ JWT RSA private key
≠ pdmg-fw HMAC jwt.secret

Never reuse
```

---

# 18. Security Observability

## FIG-11-19. Security Observability

```text
Login success/fail
Token issue/refresh
JWT verify fail
kid/JWKS error
Authorization deny
Key rotation
 ↓
Audit / Alert
```

---

# 19. Architecture Rule Catalog

## FIG-11-20. Rule Set

```text
R-SEC-01
Issuer와 Verifier의 algorithm/key model을 일치시킨다.

R-SEC-02
Private Key는 Issuer/managed key boundary에 제한한다.

R-SEC-03
Verifier는 Public Key/JWKS만 사용하도록 한다.

R-SEC-04
SSO HMAC secret과 JWT key를 분리한다.

R-SEC-05
Authentication ≠ Authorization.

R-SEC-06
Client header user를 trusted identity로 사용하지 않는다.

R-SEC-07
Key rotation/multi-instance/DR를 설계한다.

R-SEC-08
Revocation/Denylist 정책을 verifier와 연결한다.

R-SEC-09
Token/Secret/Password를 로그에 남기지 않는다.

R-SEC-10
Session state와 token state를 명확히 분리한다.
```

| Rule | 정의 |
|---|---|
| R-SEC-01 | Issuer와 Verifier의 algorithm/key model을 일치시킨다. |
| R-SEC-02 | Private Key는 Issuer/managed key boundary에 제한한다. |
| R-SEC-03 | Verifier는 Public Key/JWKS만 사용하도록 한다. |
| R-SEC-04 | SSO HMAC secret과 JWT key를 분리한다. |
| R-SEC-05 | Authentication ≠ Authorization. |
| R-SEC-06 | Client header user를 trusted identity로 사용하지 않는다. |
| R-SEC-07 | Key rotation/multi-instance/DR를 설계한다. |
| R-SEC-08 | Revocation/Denylist 정책을 verifier와 연결한다. |
| R-SEC-09 | Token/Secret/Password를 로그에 남기지 않는다. |
| R-SEC-10 | Session state와 token state를 명확히 분리한다. |

---

# 20. Verification / Test

## FIG-11-21. Verification Flow

```text
Architecture Model
   ↓
Static / Config Check
   ↓
Integration Test
   ↓
Failure / Security / Performance Test
   ↓
Runtime Evidence
   ↓
PASS / GAP
```

| Test ID | 검증내용 |
|---|---|
| T-SEC-01 | RS256 issue→JWKS verify |
| T-SEC-02 | multi-instance token verify |
| T-SEC-03 | key rotation |
| T-SEC-04 | revocation/denylist |
| T-SEC-05 | principal/header mismatch |
| T-SEC-06 | ServiceId authorization |
| T-SEC-07 | secret/token log scan |

---

# 21. GAP Register

## FIG-11-22. GAP Lifecycle

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

| GAP ID | GAP | 중요도 | 전환조건 |
|---|---|---|---|
| GAP-SEC-01 | RS256 issuer vs HMAC verifier | Critical | RS256/JWKS integration |
| GAP-SEC-02 | Key lifecycle/multi-instance consistency | Critical | managed key store/rotation |
| GAP-SEC-03 | Denylist verifier integration | High | revocation test |
| GAP-SEC-04 | Principal↔Business user binding | Critical | binding enforcement |
| GAP-SEC-05 | ServiceId authorization matrix | High | authz registry |
| GAP-SEC-06 | sessionStorage token exposure risk | High | client security review |

---

# 22. Risk Register

## FIG-11-23. Risk Propagation

```text
Cause
  ↓
Technical Failure
  ↓
Service Impact
  ↓
Operational / Business Impact
```

| Risk ID | Risk | 영향 |
|---|---|---|
| RISK-SEC-01 | same kid/different generated key | multi-node token failure |
| RISK-SEC-02 | shared HMAC secret spread | large blast radius |
| RISK-SEC-03 | header spoof | identity/authorization abuse |
| RISK-SEC-04 | denylist not checked | revoked token usable |
| RISK-SEC-05 | token log leakage | credential exposure |

---

# 23. Architecture Decision / ADR

## FIG-11-24. Decision Flow

```text
Decision Question
   ↓
주안 / 대안
   ↓
장점 / 단점
   ↓
Evidence / Test
   ↓
ADR
   ↓
Baseline
```

| ADR/Task | 의사결정 주제 | 현재 방향 |
|---|---|---|
| ADR-TASK-010 | JWT Algorithm | RS256+JWKS |
| ADR-TASK-011 | Key Management | managed/versioned |
| ADR-TASK-012 | Session/Token State | JWT + server refresh/revoke |
| ADR-TASK-013 | Identity Binding | verified principal authoritative |

---

# 24. Architecture PASS / PDMG Conformance

## FIG-11-25. PASS Model

```text
Architecture Definition
 ↓
PASS

Current Security Conformance
 ↓
GAP / CRITICAL

Target is clear
Current issuer/verifier and key lifecycle not aligned
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Login/issue | PASS/PARTIAL | source exists |
| RS256 issue | PASS AS-IS | pdmg-jwt |
| Business verify | FAIL/GAP | HMAC path |
| JWKS target | PASS architecture | integration needed |
| Identity binding | GAP | must enforce |
| Revocation | PARTIAL/GAP | denylist integration |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `GAP / CRITICAL`  
**Runtime Evidence Coverage:** `HIGH`

---

# 25. Next Chapter Handoff

## FIG-11-26. 11 → 12

```text
11 SECURITY
"누구이며 무엇을 할 수 있는가?"
     ↓
12 MESSAGE / CONTEXT / ERROR / LOGGING
"그 Identity와 거래정보가 전문/Header/Context/Error/Log 안에서 어떻게 전달·추적되는가?" 
```

---

# 26. PDMG SECURITY / SSO / JWT / SESSION ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG SECURITY / SSO / JWT / SESSION ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**11장 Architecture Definition 판정: `PASS`**
