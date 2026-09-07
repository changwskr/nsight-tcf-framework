# APPENDIX F. Interface Inventory

필수 Interface Registry:

| 항목 | 설명 |
|---|---|
| Interface ID | Canonical ID |
| Source / Target | 시스템 경계 |
| Business Purpose | 업무 목적 |
| Type | API / Event / CDC / ETL / File |
| Sync/Async | 처리 방식 |
| Contract | Schema / Version |
| Security | AuthN/AuthZ/Encryption |
| Timeout | Budget |
| Retry | Retryability / Backoff |
| Idempotency | 중복 방지 |
| Operations | SLA / Owner / Alert |
| Evidence | Test / Runtime |

Cross-system Direct DB DML / DB-Link는 기본 금지.
