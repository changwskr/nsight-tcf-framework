# APPENDIX F. Interface Inventory — Detailed Final

### F.1 Current / Target Interface Registry

| InterfaceId | Source | Target | Purpose | Protocol/Type | Mode | 상태 | Evidence | GAP/Next |
|---|---|---|---|---|---|---|---|---|
| IF-PD-001 | Browser/UI | PDMG Runtime | Online Business | HTTP/JSON | SYNC | [AS-IS] | Strong current evidence | Endpoint/contract 전수 Registry 필요 |
| IF-PD-002 | PDMG Runtime | RDW / DB | Data Access | MyBatis/JDBC/SQL | SYNC | [AS-IS] | Strong current evidence | Datasource/DB node mapping 필요 |
| IF-PD-003 | Browser/SSO Caller | pdmg-jwt | Login/SSO Token | HTTP(S)/JSON | SYNC | [AS-IS] | JWT source/runtime reference | Exact deployment endpoint inventory 필요 |
| IF-PD-004 | pdmg-jwt | Client | Access/Refresh Token Response | JSON | SYNC | [AS-IS] | Token issue flow | Client token storage risk 검토 |
| IF-PD-005 | Business Runtime | JWT Public Key/JWKS | Token Verification | JWKS/HTTPS | SYNC | [GAP/TARGET] | RS256+JWKS target | Current fw HMAC verifier와 정합 필요 |
| IF-NS-001 | Producer | Event Platform | Business Event | Event/Kafka | ASYNC | [TARGET] | NSIGHT interface policy | Product/topic/schema registry OPEN |
| IF-NS-002 | Source DB | CDC Consumer/Target | Change Data | CDC | ASYNC | [TARGET/BASELINE] | NSIGHT data movement | Freshness 3s vs 30s conflict |
| IF-NS-003 | RDW/Source | ADW | Bulk Transform/Load | ETL | ASYNC | [BASELINE] | RDW→ADW role separation | ETL product/job inventory OPEN |
| IF-NS-004 | Sender | Receiver | Managed File Transfer | MFT/FOS | ASYNC | [BASELINE] | File interface policy | External file inventory OPEN |
| IF-OPEN-001 | PDMG | External Systems | External API/Event/File | TBD | TBD | [OPEN] | Interface inventory incomplete | InterfaceId/Source/Target 전수등록 |
| IF-OPEN-002 | Any System | Other System DB | Direct DB/DML/DB-Link | JDBC/DB-Link | N/A | [FORBIDDEN/EXCEPTION] | Architecture principle | Privilege/DB-Link exception scan 필요 |

### F.2 Interface Selection Policy

```text
즉시 결과 필요?
 ├─ YES → API / Transaction
 └─ NO
      ↓
   Business Event?
    ├─ YES → Event
    └─ NO
         ↓
      DB Change?
       ├─ YES → CDC
       └─ NO
            ↓
         Bulk?
          ├─ YES → ETL
          └─ NO → File / MFT
```

### F.3 Contract Mandatory Fields

`InterfaceId / Source / Target / Purpose / Type / Endpoint / Sync-Async / Schema / Header / GUID / Error / Timeout / Retry / Idempotency / Security / SLA / Owner / Version`.

### F.4 Current GAP

- External API/Event/File 전수 Interface Inventory 미완료.
- Enterprise InterfaceId 정확한 형식 미확정.
- Interface별 Timeout/Retry 실제 값 미확정.
- CDC Freshness 3s vs 30s 충돌.
- Direct DB/DB-Link 예외 Inventory 미완료.
- Replay/Reconciliation Evidence 미완료.
