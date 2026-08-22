---
document-status: PROPOSED
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# PDMG ALLOWED VARIANTS


Variant는 표준 위반을 숨기는 수단이 아니다. 업무 특성상 합리적이고 Evidence/Approval이 있는 차이만 `ALLOWED_VARIANT`로 승격한다.

초기 후보:

- UI는 Boot JAR이고 업무/JWT는 WAR인 Packaging 차이.
- `pdmg-fw`는 Library Project이므로 Handler/Facade/DAO 업무 계층을 강제하지 않는다.
- `pdmg-jwt`의 Security 구성은 일반 업무서비스보다 엄격한 Key/JWKS 규칙을 가진다.

현재 확정된 Allowed Variant는 없으며 모두 `CANDIDATE`다.
