# 별첨 B. Logical / Technical Architecture

## B.1 Logical Node Catalog

| ID | Logical Node | Current 상태 | 핵심책임 |
|---|---|---|---|
| LTN-PD-01 | UI Delivery | BASELINE | UI/Presentation/Request Assembly |
| LTN-PD-02 | Authentication | BASELINE | Login/SSO/JWT/Trust |
| LTN-PD-03 | Application Runtime | BASELINE | Framework + Business Runtime |
| LTN-PD-04 | Data Service | BASELINE/PARTIAL | RDW/DB Data Service |
| LTN-PD-05 | Integration | CONDITIONAL/OPEN | External Contract/Interface |
| LTN-PD-06 | Operations | OPEN/UNKNOWN | OM/Operations Control |

## B.2 Application Runtime

```text
LTN-PD-03 APPLICATION RUNTIME
│
├─ Framework Runtime
│  ├─ Filter / Context
│  ├─ Security Integration
│  ├─ TCF / Dispatcher
│  ├─ Worker / Timeout
│  ├─ Transaction
│  └─ Error / Logging
│
└─ Business Runtime
   ├─ Handler / Controller
   ├─ Facade
   ├─ Service
   └─ DAO
```

## B.3 Logical Rule

```text
Application ≠ Logical Node
Module      ≠ Logical Node
Logical Node ≠ Physical Host
Technology Component ≠ Product Version
```
