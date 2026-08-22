---
document-status: PROPOSED
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# UC08 RUNTIME DRIFT DETECTION


## 목적

Reference/Target Config와 실제 Runtime Timeout/TX/SQL/Pool/JVM 차이를 검출한다.

## 성공조건

Evidence가 없는 부분은 UNKNOWN으로 남고, 필요한 Gate 결과가 객관적 측정값으로 생성된다.
