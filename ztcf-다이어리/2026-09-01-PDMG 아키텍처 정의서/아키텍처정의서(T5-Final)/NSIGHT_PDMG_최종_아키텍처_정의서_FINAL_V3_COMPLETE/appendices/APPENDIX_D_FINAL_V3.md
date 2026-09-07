# 별첨 D. Interface Architecture

## D.1 Interface Selection Principle — NSIGHT Target

```text
Transaction / Enterprise Mediation → MCA
Online API                         → API / JSON
Event                              → Kafka
Change Data                        → CDC
Bulk / Analytical                  → ETL
File                               → MFT / FOS
JDBC                               → controlled internal access
```

## D.2 Contract-first

```text
Business Interaction
 ↓
Interface Type
 ↓
Source / Target
 ↓
Contract / Schema
 ↓
Security
 ↓
Timeout / Retry
 ↓
Idempotency / Compensation
 ↓
Operations / Evidence
```

## D.3 Forbidden

```text
Cross-system Direct DB DML / DB-Link
        X

Blind Retry on
- Financial DML
- Validation Error
- Authorization Error
- Business Reject
        X
```

PDMG Current External Interface Inventory는 전수 확인이 필요하다.
