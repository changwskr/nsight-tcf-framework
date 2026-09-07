# PDMG 전체 아키텍처 정의서
# 06. PDMG INTERFACE ARCHITECTURE
## Contract / Sync-Async / API-Event-CDC-ETL-File / Failure / Governance
## Visual-First / TEXT Architecture / Top-down → Drill-down / Evidence-First

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 주인공: **PDMG Current Architecture**  
> 문서 ID: `PDMG-ARCH-06-INTERFACE`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  

---

# 0. Chapter Purpose

## FIG-06-01. 이 장의 핵심 질문

```text
PDMG가 내부/외부 시스템과 어떤 Contract로 연결되는가?
어떤 업무조건에서 API/Event/CDC/ETL/File을 선택하는가?
Timeout/Retry/Idempotency/Security/Recovery를 어떻게 통제하는가?
```

이 장은 PDMG Current를 설명하되, Current Source/Config/Runtime으로 확인되지 않은 Target 구조를 AS-IS로 승격하지 않는다.

---

# 1. Evidence Register

## FIG-06-02. Evidence Flow

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
| EV-06-01 | Interface Principle / Guide | IF-01~IF-10, type selection, retry | [TARGET BASELINE] |
| EV-06-02 | 02 System Context | Inbound/Outbound boundary | [WORKING BASELINE] |
| EV-06-03 | PDMG Runtime | HTTP/JSON request | [AS-IS] |
| EV-06-04 | PDMG Data Access | MyBatis/JDBC | [AS-IS] |
| EV-06-05 | Interface Appendix D | Contract/Operations/Governance | [TARGET REFERENCE] |

---

# 2. Figure Plan

## FIG-06-03. Top-down Drill-down

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

# 3. L0 — PDMG Interface Master

## FIG-06-04. L0 — PDMG Interface Master

```text
Business Interaction Need
   ↓
Interface Classification
   ↓
Source / Target Boundary
   ↓
Interface Contract
   ↓
Runtime Mechanism
   ↓
Failure / Recovery
   ↓
Operations / Evidence
```

---

# 4. Interface Architecture Principles

## FIG-06-05. Interface Architecture Principles

```text
IF-01 Purpose-driven
IF-02 P2P minimize
IF-03 Service-to-Service
IF-04 Sync/Async by business need
IF-05 Online/Bulk separation
IF-06 Contract-first
IF-07 Failure isolation
IF-08 Traceability
IF-09 Versioning
IF-10 Exception via Architecture Review
```

---

# 5. PDMG Current Interface Context

## FIG-06-06. PDMG Current Interface Context

```text
Browser / UI
  ↓ HTTP/JSON
PDMG Runtime
  ↓ JDBC
RDW / DB

External API / Event / File
= Inventory Required
= [OPEN / PARTIAL]
```

Current PDMG에서 강하게 확인되는 것은 HTTP business request와 MyBatis/JDBC DB access다. 외부 API/Event/File 전수 목록은 별도 Interface Inventory가 필요하다.

---

# 6. Interface Type Decision Tree

## FIG-06-07. Interface Type Decision Tree

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

---

# 7. SYNC / ASYNC Policy

## FIG-06-08. SYNC / ASYNC Policy

```text
Current Transaction Completion에
Target 결과가 필수?
  ├─ YES → SYNC
  └─ NO  → ASYNC 우선
```

---

# 8. Interface Contract

## FIG-06-09. Interface Contract

```text
InterfaceId
Source / Target
Purpose
Type
Protocol / Endpoint
Sync/Async
Schema / Header
GUID / Correlation
Error
Timeout / Retry
Idempotency
Security
SLA
Owner / Version
```

---

# 9. Identifiers

## FIG-06-10. Identifiers

```text
ServiceId
= Business Transaction Identity

InterfaceId
= S2S Contract Identity

GUID
= Runtime Execution Identity

ServiceId ≠ InterfaceId ≠ GUID
```

---

# 10. Direct DB / DB-Link Policy

## FIG-06-11. Direct DB / DB-Link Policy

```text
System A
  ↓
Approved API / Data Contract
  ↓
System B

System A ──JDBC/DML──► System B DB
         X

DB-Link
= Exception / ADR only
```

---

# 11. API Pattern

## FIG-06-12. API Pattern

```text
Source
  ↓ Request
Target
  ↓ Response
Source

Timeout / Error / Auth / Version
must be contract-defined
```

---

# 12. Event Pattern

## FIG-06-13. Event Pattern

```text
Producer
  ↓ Event
Broker
  ├─ Consumer A
  ├─ Consumer B
  └─ Consumer C

Schema / Replay / DLQ / Ordering
```

---

# 13. CDC Pattern

## FIG-06-14. CDC Pattern

```text
Source DB
  ↓ Capture
Trail / Queue
  ↓ Relay
Target Apply
  ↓
RDW / Consumer

Freshness SLA
= 30s vs 3s [CONFLICT]
```

---

# 14. ETL / File Pattern

## FIG-06-15. ETL / File Pattern

```text
Bulk
Source
 ↓ Extract
Transform
 ↓ Load
Target

File
Sender
 ↓ MFT
Landing
 ↓ Validation
Receiver
```

---

# 15. Timeout Hierarchy

## FIG-06-16. Timeout Hierarchy

```text
DB Query Timeout
    <
Worker / Transaction Deadline
    <
Server / Downstream Timeout
    <
Client Timeout
```

---

# 16. Retry / Idempotency

## FIG-06-17. Retry / Idempotency

```text
Failure
 ↓
Transient?
 ├─ NO → No Retry
 └─ YES
      ↓
   Idempotent?
   ├─ NO → Compensation / Manual
   └─ YES → Backoff → Max Retry → Recovery
```

---

# 17. Interface Security

## FIG-06-18. Interface Security

```text
Caller Identity
 ↓
Authentication
 ↓
Authorization
 ↓
Transport Protection
 ↓
Schema Validation
 ↓
Audit / Trace
```

---

# 18. Operations / Recovery

## FIG-06-19. Operations / Recovery

```text
Interface
 ├─ Log
 ├─ Metric
 ├─ Trace
 ├─ Alert
 ├─ Retry
 ├─ Replay
 ├─ Reconciliation
 └─ Runbook
```

---

# 19. Interface Inventory / Trace

## FIG-06-20. Interface Inventory / Trace

```text
Requirement
 ↓
InterfaceId
 ↓
Source / Target
 ↓
Contract Version
 ↓
Runtime Endpoint
 ↓
GUID
 ↓
Evidence
```

---

# 20. Architecture Rule Catalog

## FIG-06-21. Rule Set

```text
R-IF-01
업무 목적에 따라 Interface Type을 선택한다.

R-IF-02
P2P를 최소화한다.

R-IF-03
Cross-system Direct DB DML은 금지한다.

R-IF-04
현재 거래완료에 결과가 필수일 때만 SYNC를 사용한다.

R-IF-05
온라인과 Bulk/File을 분리한다.

R-IF-06
Contract-first로 설계한다.

R-IF-07
Retry는 Error/Idempotency 조건으로 제한한다.

R-IF-08
모든 Interface는 InterfaceId/GUID로 추적한다.

R-IF-09
Breaking Change는 Version으로 관리한다.

R-IF-10
예외는 ADR/Architecture Review를 거친다.
```

| Rule | 정의 |
|---|---|
| R-IF-01 | 업무 목적에 따라 Interface Type을 선택한다. |
| R-IF-02 | P2P를 최소화한다. |
| R-IF-03 | Cross-system Direct DB DML은 금지한다. |
| R-IF-04 | 현재 거래완료에 결과가 필수일 때만 SYNC를 사용한다. |
| R-IF-05 | 온라인과 Bulk/File을 분리한다. |
| R-IF-06 | Contract-first로 설계한다. |
| R-IF-07 | Retry는 Error/Idempotency 조건으로 제한한다. |
| R-IF-08 | 모든 Interface는 InterfaceId/GUID로 추적한다. |
| R-IF-09 | Breaking Change는 Version으로 관리한다. |
| R-IF-10 | 예외는 ADR/Architecture Review를 거친다. |

---

# 21. Verification / Test

## FIG-06-22. Verification Flow

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
| T-IF-01 | Interface Registry completeness |
| T-IF-02 | Contract schema compatibility |
| T-IF-03 | Timeout hierarchy |
| T-IF-04 | Retry duplicate prevention |
| T-IF-05 | Security/authz |
| T-IF-06 | Replay/reconciliation |
| T-IF-07 | Direct DB exception scan |

---

# 22. GAP Register

## FIG-06-23. GAP Lifecycle

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
| GAP-IF-01 | PDMG External Interface Inventory 미완료 | High | InterfaceId/Source/Target 전수등록 |
| GAP-IF-02 | InterfaceId 정확한 Enterprise 형식 미확정 | Medium | Naming ADR |
| GAP-IF-03 | Timeout/Retry 실제 값 전수 미확정 | High | Contract Registry |
| GAP-IF-04 | CDC SLA 3s vs 30s 충돌 | High | Tiered SLA ADR |
| GAP-IF-05 | Direct DB/DB-Link 예외 Inventory 미완료 | High | Privilege/Link Scan |
| GAP-IF-06 | Replay/Reconciliation Evidence 미완료 | High | Ops test |

---

# 23. Risk Register

## FIG-06-24. Risk Propagation

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
| RISK-IF-01 | Blind Retry | 중복 금융거래/부하증폭 |
| RISK-IF-02 | P2P Direct DB | 강결합/변경전파 |
| RISK-IF-03 | 동기 호출체인 증가 | Cascade failure |
| RISK-IF-04 | 대량 payload를 Online API 처리 | Timeout/자원고갈 |
| RISK-IF-05 | Versionless Contract | Breaking change |

---

# 24. Architecture Decision / ADR

## FIG-06-25. Decision Flow

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
| ADR-TASK-014 | Interface Type Selection | Purpose-driven |
| ADR-TASK-015 | Direct DB/DB-Link | 원칙 금지 |
| ADR-TASK-016 | SYNC/ASYNC | Result-required only SYNC |
| ADR-TASK-017 | Timeout Budget | 계층형 |
| ADR-TASK-018 | Retry/Idempotency | 분류+Backoff+Idempotency |
| ADR-TASK-019 | Event Platform | 공통 Event Broker 후보 |
| ADR-TASK-020 | Bulk/File | ETL/MFT 분리 |

---

# 25. Architecture PASS / PDMG Conformance

## FIG-06-26. PASS Model

```text
Architecture Definition
 ↓
PASS

Current PDMG Interface Conformance
 ↓
PARTIAL / OPEN

Strong: HTTP/JDBC
Open: external API/Event/File inventory
```

| 평가항목 | 판정 | 근거/조건 |
|---|---|---|
| Principles | PASS | IF-01~IF-10 |
| HTTP business | PASS | Current runtime |
| DB access | PASS/PARTIAL | Own/approved DB only |
| External Inventory | OPEN | InterfaceId catalog needed |
| Retry/Timeout | CONDITIONAL | per-interface values needed |
| Recovery | CONDITIONAL | replay/reconcile tests |

**Architecture Definition:** `PASS`  
**Current PDMG Conformance:** `PARTIAL / OPEN`  
**Runtime Evidence Coverage:** `MEDIUM-LOW`

---

# 26. Next Chapter Handoff

## FIG-06-27. 06 → 07

```text
06 INTERFACE
"어떻게 연결하는가?"
     ↓
07 DATA
"그 연결과 업무 실행이 어떤 데이터 Ownership / Model / Flow / SQL과 연결되는가?" 
```

---

# 27. PDMG INTERFACE ARCHITECTURE 최종 결론

## TEXT ARCHITECTURE — Conclusion

```text
PDMG INTERFACE ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

**06장 Architecture Definition 판정: `PASS`**
