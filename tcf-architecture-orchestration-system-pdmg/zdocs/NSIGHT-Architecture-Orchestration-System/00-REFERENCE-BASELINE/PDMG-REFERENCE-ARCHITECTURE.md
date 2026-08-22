---
document-status: PROPOSED
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# PDMG REFERENCE ARCHITECTURE


현재 Source에서 추출한 Reference Architecture 후보이다. `RHG90 PASS` 전에는 `VERIFIED_REFERENCE`로 선언하지 않는다.

```text
[pdmg-ui]
UI Route / Transaction Catalog
        ↓ ServiceId
[pdmg-service / pdmg-jwt]
        ↓
[pdmg-fw]
OnlineTransactionController
→ TCF/STF
→ DefaultOnlineTimeoutExecutor
→ Worker Thread
→ TransactionTemplate
→ TransactionDispatcher
→ Handler
        ↓
Facade
→ Service
→ DAO
→ Mapper / SQL
→ DB
        ↓
ETF / Runtime Evidence
```

### JWT Candidate Flow

```text
Login/SSO ServiceId
→ Handler / Facade / Service
→ JwtTokenIssuer
→ RSA Private Key
→ JWT
→ JwkSetController
→ Public JWKS
→ Refresh / Revoke / Token Store
```
