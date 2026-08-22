---
document-status: GAP
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# PDMG REFERENCE GAPS


| GAP ID | 내용 | 근거 | Severity | 상태 |
|---|---|---|---|---|
| PRG-001 | `pdmg-fw` 외곽 `TransactionTemplate`와 `pdmg-service` Facade/Service `@Transactional`의 최종 Reference TX 경계 확정 필요 | Source | HIGH | OPEN |
| PRG-002 | `pdmg-ui`와 `pdmg-jwt`의 Test Java가 현재 추출본 기준 0건 | Source Inventory | HIGH | OPEN |
| PRG-003 | Git Branch/Commit을 ZIP 추출본만으로 확정할 수 없음 | Source Packaging | MEDIUM | OPEN |
| PRG-004 | Runtime Evidence가 현재 Orchestration 패키지 생성 시점에 연결되지 않음 | Evidence Chain | HIGH | OPEN |
| PRG-005 | RSA signing key가 `JwtKeyConfiguration`에서 런타임 생성됨. KMS/HSM/운영 Key Lifecycle Reference 결정 필요 | pdmg-jwt Source | HIGH | OPEN |
