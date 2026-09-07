# PDMG 전체 아키텍처 정의서
# 13. PDMG EVENT / CDC / ETL / BATCH / FILE / CACHE ARCHITECTURE
## Non-Online Runtime / Data Movement / Recovery / Idempotency / Evidence
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-13-EVENT-CDC-ETL-BATCH-FILE-CACHE`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-13-01. 이 장의 핵심 질문

```text
온라인 이외의 Event/CDC/ETL/Batch/File/Cache는 PDMG와 어떤 관계인가?
어떤 부분이 Current이고 어떤 부분이 NSIGHT Platform Reference인가?
재처리/Replay/Restart/Idempotency/Reconciliation을 어떻게 정의하는가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-13-02. Evidence Flow

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
| EV-13-01 | 06 Interface | purpose-driven mechanism | [WORKING BASELINE] |
| EV-13-02 | 07 Data | CDC/ETL relation | [TARGET REFERENCE] |
| EV-13-03 | NSIGHT Big Picture | Event/CDC/ETL/File strategy | [TARGET REFERENCE] |
| EV-13-04 | PDMG source set | current non-online evidence check | [PARTIAL/UNKNOWN] |
| EV-13-05 | Decision Register | event/bulk decisions | [DECISION] |

---

# 2. Figure Plan

## FIG-13-03. Top-down Drill-down

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

# 3. L0 — Non-Online Runtime Map

## FIG-13-04. L0 — Non-Online Runtime Map

```text
PDMG Core Current
= Online / HTTP / DB

NSIGHT Broader Runtime
├─ Event
├─ CDC
├─ ETL
├─ Batch
├─ File
└─ Cache

Current PDMG direct ownership
= evidence-dependent
```

---

# 4. Current vs Reference Boundary

## FIG-13-05. Current vs Reference Boundary

```text
PDMG Source confirmed?
 ├─ YES → [AS-IS]
 └─ NO
      ↓
NSIGHT platform relation?
 ├─ YES → [REFERENCE]
 └─ NO → [UNKNOWN]
```

---

# 5. Event Architecture

## FIG-13-06. Event Architecture

```text
Producer
 ↓
Event Schema
 ↓
Broker
 ↓
Consumer Group
 ↓
Processing
 ↓
DLQ / Replay

PDMG current implementation
= [OPEN]
```

---

# 6. CDC Architecture

## FIG-13-07. CDC Architecture

```text
Source DB
 ↓ capture
Trail / Queue
 ↓ relay
Apply
 ↓
RDW / Target

Freshness SLA
= [CONFLICT 3s vs 30s]
```

---

# 7. ETL Architecture

## FIG-13-08. ETL Architecture

```text
Source
 ↓ Extract
Transform
 ↓ Load
Target
 ↓ Reconcile

Online resource
= isolate
```

---

# 8. Batch Architecture

## FIG-13-09. Batch Architecture

```text
Scheduler
 ↓
Job
 ↓
Step
 ↓
DB / File / API
 ↓
Checkpoint / Restart
 ↓
Result / Evidence
```

---

# 9. File Architecture

## FIG-13-10. File Architecture

```text
Sender
 ↓
MFT/FOS
 ↓
Landing
 ↓
Validation
 ↓
Processing
 ↓
Archive / Quarantine
```

---

# 10. Cache Architecture

## FIG-13-11. Cache Architecture

```text
Business Read
 ↓
Cache?
 ├─ HIT → result
 └─ MISS → source
            ↓
          update cache

Consistency / TTL / invalidation
must be explicit
```

---

# 11. Retry / Replay / Reconciliation

## FIG-13-12. Retry / Replay / Reconciliation

```text
Transient failure
 ↓
Retry / Backoff

Event/File/Batch
 ↓
Replay / Restart
 ↓
Reconciliation
 ↓
Manual recovery if needed
```

---

# 12. Idempotency

## FIG-13-13. Idempotency

```text
Redelivery / Restart
 ↓
Idempotency Key / Business Key
 ↓
Already processed?
 ├─ YES → suppress
 └─ NO  → process
```

---

# 13. Operations

## FIG-13-14. Operations

```text
Event lag
CDC lag
Batch job state
File status
Cache hit ratio
 ↓
Metric / Alert / Runbook
```

---

# 14. PDMG Integration Handoff

## FIG-13-15. PDMG Integration Handoff

```text
PDMG Business
 ↓
Approved Contract
 ↓
Event / CDC / ETL / Batch / File
 ↓
Platform

PDMG business code
≠ platform implementation ownership by default
```

---

# 15. Failure Isolation

## FIG-13-16. Failure Isolation

```text
Online
  X
  should not wait for
  long batch / ETL

Event / Batch / File
 ↓
buffer / retry / checkpoint
```

---

# 16. Evidence Classification

## FIG-13-17. Evidence Classification

```text
Current Source Evidence
   ↓
AS-IS

Architecture Need only
   ↓
PROPOSED / REFERENCE

No Evidence
   ↓
UNKNOWN
```

---

# 17. Architecture Rule Catalog

## FIG-13-18. Rule Set

```text
R-NON-01
PDMG Current에 없는 Mechanism을 AS-IS로 창작하지 않는다.

R-NON-02
Event는 Schema/Replay/DLQ를 가진다.

R-NON-03
CDC는 capture/apply/lag를 추적한다.

R-NON-04
ETL/Batch는 Online 자원과 격리한다.

R-NON-05
File은 landing/validation/archive/quarantine을 정의한다.

R-NON-06
Cache는 TTL/invalidation/source-of-truth를 정의한다.

R-NON-07
Retry 가능한 쓰기는 idempotency를 가진다.

R-NON-08
Replay/Restart 후 reconciliation을 수행한다.

R-NON-09
Platform failure가 Online transaction에 무제한 전파되지 않도록 한다.

R-NON-10
모든 non-online runtime은 job/event/file identity를 갖는다.
```

| Rule | 정의 |
|---|---|
| R-NON-01 | PDMG Current에 없는 Mechanism을 AS-IS로 창작하지 않는다. |
| R-NON-02 | Event는 Schema/Replay/DLQ를 가진다. |
| R-NON-03 | CDC는 capture/apply/lag를 추적한다. |
| R-NON-04 | ETL/Batch는 Online 자원과 격리한다. |
| R-NON-05 | File은 landing/validation/archive/quarantine을 정의한다. |
| R-NON-06 | Cache는 TTL/invalidation/source-of-truth를 정의한다. |
| R-NON-07 | Retry 가능한 쓰기는 idempotency를 가진다. |
| R-NON-08 | Replay/Restart 후 reconciliation을 수행한다. |
| R-NON-09 | Platform failure가 Online transaction에 무제한 전파되지 않도록 한다. |
| R-NON-10 | 모든 non-online runtime은 job/event/file identity를 갖는다. |

---

# 18. Verification / Test

## FIG-13-19. Verification Flow

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
| T-NON-01 | event duplicate/replay |
| T-NON-02 | CDC lag/failover |
| T-NON-03 | ETL restart/reconcile |
| T-NON-04 | batch restart/checkpoint |
| T-NON-05 | file duplicate/quarantine |
| T-NON-06 | cache expiry/invalidation |

---

# 19. GAP Register

## FIG-13-20. GAP Lifecycle

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
| GAP-NON-01 | PDMG Event current scope | High | source/inventory scan |
| GAP-NON-02 | PDMG Batch current scope | High | job inventory |
| GAP-NON-03 | PDMG File current scope | Medium/High | interface inventory |
| GAP-NON-04 | Cache current scope/policy | Medium | source/config scan |
| GAP-NON-05 | CDC SLA conflict | High | ADR/SLA tier |
| GAP-NON-06 | Replay/Reconcile evidence | High | recovery tests |

---

# 20. Risk Register

## FIG-13-21. Risk Propagation

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
| RISK-NON-01 | Online+Batch resource sharing | SLA interference |
| RISK-NON-02 | Event duplicate | duplicate business action |
| RISK-NON-03 | CDC lag unnoticed | stale data |
| RISK-NON-04 | file partial/duplicate | data inconsistency |
| RISK-NON-05 | cache stale data | wrong business result |

---

# 21. Architecture Decision / ADR

## FIG-13-22. Decision Flow

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
| ADR-TASK-019 | Event Platform | standard broker candidate |
| ADR-TASK-020 | Bulk/File | ETL/MFT separation |
| ADR-TASK-022 | CDC SLA | tiered freshness |
| ADR-TASK-018 | Retry/Idempotency | conditional |

---

# 22. Architecture PASS / PDMG Conformance

## FIG-13-23. PASS Model

```text
Architecture Definition
 ↓
CONDITIONAL PASS

Current PDMG Conformance
 ↓
OPEN / PARTIAL

Reason: broader mechanisms are well-defined target patterns,
but PDMG current ownership is not fully evidenced
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Event | OPEN | current source inventory |
| CDC | REFERENCE | NSIGHT data platform |
| ETL | REFERENCE | bulk data |
| Batch | OPEN/PARTIAL | current inventory needed |
| File | OPEN | interface inventory |
| Cache | OPEN | source/config needed |

**Architecture Definition:** `CONDITIONAL PASS`  
**Current PDMG Conformance:** `OPEN / PARTIAL`  
**Runtime Evidence Coverage:** `LOW-MEDIUM`

---

# 23. Next Chapter Handoff

## FIG-13-24. 13 → 14

```text
13 NON-ONLINE RUNTIME
"온라인 외 실행형태는 어떻게 분리되는가?"
     ↓
14 DEVOPS / OM / OBSERVABILITY
"Source와 Runtime을 어떻게 Build/Deploy/운영/관측하고 Evidence로 남기는가?" 
```

---

# 24. PDMG EVENT / CDC / ETL / BATCH / FILE / CACHE ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG EVENT / CDC / ETL / BATCH / FILE / CACHE ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**13장 Architecture Definition 판정: `CONDITIONAL PASS`**
