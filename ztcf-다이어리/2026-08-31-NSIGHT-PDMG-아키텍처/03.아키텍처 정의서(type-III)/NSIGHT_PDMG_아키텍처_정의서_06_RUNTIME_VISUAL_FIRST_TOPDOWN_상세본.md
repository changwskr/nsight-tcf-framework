# NSIGHT / PDMG 아키텍처 정의서
# 06. RUNTIME — Sequence / Thread / Transaction / Timeout / Failure / Recovery / Evidence
## Visual-First / Top-down / Drill-down 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-VISUAL-RUNTIME-06`  
> Architecture Level: **RUNTIME / L5**  
> 문서 상태: **Draft / Evidence-First / Visual-First**  
> 작성 기준일: **2026-08-31**  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_05_MECHANISM_VISUAL_FIRST_TOPDOWN_상세본.md`  
> 후속: **Evidence / Traceability / Closed Loop / Baseline Release**

---

# 0. 이 문서를 읽는 방법

RUNTIME은 “무슨 기술을 쓰는가?”를 설명하는 장이 아니다.

이 장은 다음을 **시간순으로 증명**한다.

```text
Request / Event / Change / File / Schedule
        │
        ▼
Runtime Type
        │
        ▼
Entry
        │
        ▼
Framework / Business / Data
        │
        ▼
Thread / Transaction / Resource
        │
        ▼
Success / Error / Timeout / Overload
        │
        ▼
Recovery / HA / DR
        │
        ▼
Metric / Log / Trace / Evidence
```

MECHANISM이:

```text
"어떻게 동작해야 하는가?"
```

를 정의했다면,

RUNTIME은:

```text
"실제로 어느 순서 / 어느 Thread / 어느 TX에서
어떻게 실행되고 실패하며 복구되는가?"
```

를 증명한다.

---

# 1. VISUAL ROUTE — RUNTIME 전체를 한 장으로 보기

## FIG-RT-01. Runtime Architecture Journey

```text
╔══════════════════════════════════════════════════════════════════════════════╗
║                            RUNTIME ARCHITECTURE                              ║
╚══════════════════════════════════════════════════════════════════════════════╝

 ① RUNTIME TYPE
    #1 ~ #12
        │
        ▼
 ② ENTRY / ACTOR
    Request / Event / Change / File / Schedule
        │
        ▼
 ③ EXECUTION SEQUENCE
    Entry → Framework → Business → Data/External
        │
        ▼
 ④ EXECUTION RESOURCE
    Request Thread / Worker / TX / Connection / DB
        │
        ▼
 ⑤ OUTCOME
    Success / Business Error / Timeout / Overload / System Error
        │
        ▼
 ⑥ RECOVERY
    Retry / Replay / Restart / Failover / Manual Recovery
        │
        ▼
 ⑦ OBSERVABILITY
    GUID / ServiceId / Metric / Log / Trace / Audit
        │
        ▼
 ⑧ AVAILABILITY
    HA / DR / Backup / Restore
        │
        ▼
 ⑨ RUNTIME EVIDENCE
    Scenario / Result / Hash / Deployment / Trace
        │
        ▼
 ⑩ CLOSED LOOP
    Drift → GAP → ADR → New Baseline
```

---

# 2. MECHANISM → RUNTIME Handoff

## FIG-RT-02. Contract to Execution

```text
MECHANISM
│
├─ Interface Type
├─ Message / Header
├─ GUID / ServiceId
├─ Transaction Policy
├─ Timeout Policy
├─ Retry / Idempotency
├─ Error Contract
├─ Security
└─ Logging
       │
       ▼
RUNTIME
│
├─ Actual Sequence
├─ Actual Thread
├─ Actual TX
├─ Actual Timeout
├─ Actual Error Branch
├─ Actual Recovery
└─ Actual Evidence
```

---

# 3. RUNTIME 핵심 결론

## FIG-RT-03. Runtime Truth

```text
Design says
   │
   ▼
Runtime executes
   │
   ▼
Evidence proves
```

### 반드시 분리할 생명주기

```text
HTTP Request Lifecycle
≠
Worker Thread Lifecycle
≠
DB Transaction Lifecycle
≠
JDBC Statement Lifecycle
```

따라서:

```text
HTTP Timeout
≠ Worker 종료
≠ JDBC Statement 취소
≠ DB Transaction Rollback
```

---

# 4. 업무 처리 Runtime Type — 12개 유형

## FIG-RT-04. Runtime Type Map

```text
CHANNEL
├─ #1 정보계 단말 거래
├─ #2 통합업무 거래
└─ #3 미니 싱글뷰

INTEGRATION
├─ #4 대내 연계
└─ #5 대외 연계

MARKETING EVENT
├─ #6 반응형 정보 수집
└─ #7 고객 오퍼링

DATA
├─ #8 CDC
├─ #9 ETL
└─ #10 분석 / 의사결정 지원

FILE
└─ #11 File

BATCH
└─ #12 Batch
```

---

# 5. Runtime Type 정의표

| # | Runtime Type | 주 입력 | 실행 특성 | 대표 회복 |
|---:|---|---|---|---|
| 1 | 정보계 단말 거래 | Request | Sync Online | Timeout/Error |
| 2 | 통합업무 거래 | Request | Sync Enterprise | Timeout/Fail |
| 3 | 미니 싱글뷰 | Request | Sync Aggregation | Partial/Timeout |
| 4 | 대내 연계 | Request/Message | Sync/Async | Retry/Recovery |
| 5 | 대외 연계 | Request/Message | Controlled External | Retry/Manual |
| 6 | 반응형 정보 수집 | Event | Async | Replay/DLQ |
| 7 | 고객 오퍼링 | Event/Decision | Async/Low-latency | Retry/Compensate |
| 8 | CDC | DB Change | Streaming | Restart/Reconcile |
| 9 | ETL | Data Set | Batch | Restart/Reconcile |
| 10 | 분석/의사결정 | Query | Analytical | Resource Isolation |
| 11 | File | File | File Transfer | Retry/Quarantine |
| 12 | Batch | Schedule | Job/Step | Restart/Checkpoint |

---

# 6. 주 유형 / 보조 유형

## FIG-RT-05. Primary / Secondary Runtime

```text
Business Flow
   │
   ├─ Primary Runtime Type
   │
   └─ Secondary Runtime Type
```

예:

```text
고객행동 오퍼링
Primary   = #7
Secondary = #6 / #4
```

### 원칙

신규 Flow는 최소 다음을 가져야 한다.

```text
Primary Runtime Type
Secondary Type [if any]
Owner
SLO
Failure Mode
Recovery
Evidence
```

---

# 7. Runtime Inventory

최소 필드:

```yaml
runtime:
  runtimeId:
  primaryType:
  secondaryTypes:
  businessFlow:
  entry:
  actors:
  sequence:
  threads:
  transaction:
  timeout:
  resourcePools:
  errorCases:
  recovery:
  monitoring:
  slo:
  owner:
  evidence:
  status:
```

---

# 8. 유형 없는 신설 금지

## FIG-RT-06. New Runtime Gate

```text
신규 Flow
  │
  ▼
Runtime Type 지정?
  ├─ NO → 개통 금지 / GAP
  └─ YES
      ↓
    Sequence?
      ↓
    Failure?
      ↓
    Recovery?
      ↓
    Evidence?
      ↓
    Release Candidate
```

---

# 9. #1 정보계 단말 거래

## FIG-RT-07. Online Information Runtime

```text
Information Terminal / Web
      │
      ▼
Channel / Entry
      │
      ▼
WEB / WAS
      │
      ▼
Standard Online Runtime
      │
      ▼
Business
      │
      ▼
RDW / DB
      │
      ▼
Response
```

---

# 10. #1 정상 Sequence

## FIG-RT-08. Normal Online

```text
Client
  │ Request
  ▼
Entry
  │
  ▼
Filter / Security
  │
  ▼
Controller
  │
  ▼
Framework
  │
  ▼
Business
  │
  ▼
DB
  │ Result
  ▼
Response
```

Evidence:

```text
GUID
ServiceId
Start/End
HTTP Status
Business Result
SQL
Elapsed
```

---

# 11. #1 지연 Sequence

## FIG-RT-09. Online Slow

```text
Client
  ↓
Entry
  ↓
Business
  ↓
Slow DB / External
  ↓
Request waits
  ↓
Timeout threshold
  ↓
Timeout response
```

운영질문:

```text
어디서 지연?
Request Thread?
Worker?
Hikari?
SQL?
External?
```

---

# 12. #2 통합업무 거래

## FIG-RT-10. Enterprise Transaction

```text
Integrated Work UI
      │
      ▼
Enterprise Entry / MCA-MCI
      │
      ▼
Information Application
      │
      ▼
Core / Related System
      │
      ▼
Response
```

---

# 13. #2 Failure

## FIG-RT-11. Integrated Transaction Failure

```text
Information App
      │
      ▼
Internal Integration
      │
      ▼
Target
      │
      X Timeout / Reject
      │
      ▼
Error Mapping
      │
      ▼
Caller
```

### 주의

Remote Call을 DB Transaction 안에 오래 포함하면 위험하다.

---

# 14. #3 미니 싱글뷰

## FIG-RT-12. Aggregation Runtime

```text
User
 ↓
Mini Single View
 ↓
Multiple Data Sources / Services
 ├─ Customer
 ├─ Product
 ├─ Account
 └─ Summary
 ↓
Aggregation
 ↓
Response
```

---

# 15. #3 Runtime 주의

```text
Fan-out 증가
   ↓
Slowest Dependency가 전체 응답 지배
```

필요:

```text
Timeout Budget
Partial Result Policy
Cache Policy
Concurrency Control
```

---

# 16. #4 대내 연계

## FIG-RT-13. Internal Integration

```text
NSIGHT
  │
  ▼
Internal Integration
  │
  ▼
Core / Related
  │
  ▼
Response / Event / File
```

---

# 17. #4 금지

```text
Application → Related DB Direct DML    X
Long TX across Remote Systems          X
Blind Retry                            X
Trace Key Loss                         X
```

---

# 18. #5 대외 연계

## FIG-RT-14. External Runtime

```text
NSIGHT
  │
  ▼
External Gateway
  │
  ▼
External Institution
  │
  ▼
Response / Ack
```

필수:

```text
Timeout
Retryability
Institution Code
Certificate
Audit
Recovery
```

---

# 19. #5 Failure 분류

```text
Network
Certificate
Protocol
Business Reject
Timeout
Duplicate
External Maintenance
```

각 유형별 Recovery가 달라야 한다.

---

# 20. #6 반응형 정보 수집

## FIG-RT-15. Event Collection Runtime

```text
Customer Action
   ↓
Collector
   ↓
Event Broker
   ↓
Consumer
   ↓
Behavior Data / Processing
```

---

# 21. #6 Runtime 지표

```text
Produce Rate
Broker Lag
Consumer Lag
Error Rate
Retry
DLQ
Replay Time
```

---

# 22. #6 금지

```text
Event Producer가 Consumer 응답을 기다림   X
Event를 Online Request Thread에 종속      X
Replay 없는 이벤트                        X
```

---

# 23. #7 고객 오퍼링

## FIG-RT-16. Event-to-Offer Runtime

```text
Behavior Event
    ↓
Processing
    ↓
Decision / EBM
    ↓
Offer
    ↓
Contact / Message
```

---

# 24. #6 vs #7

```text
#6
행동을 수집

        ↓

#7
행동을 해석하여 반응
```

---

# 25. #7 Failure

## FIG-RT-17. Offer Failure

```text
Event
  ↓
Decision
  ↓
Offer
  X
Delivery Failure
  ↓
Retry / Alternate / Manual Recovery
```

Decision과 Delivery 결과를 분리해서 기록한다.

---

# 26. #8 CDC

## FIG-RT-18. CDC Runtime

```text
Source DB
  │ change
  ▼
Capture
  ▼
Relay / Transport
  ▼
Apply
  ▼
RDW
```

---

# 27. #8 관측지점

```text
Source LSN/SCN
Capture Time
Transport Time
Apply Time
Lag
Error
Restart Position
```

---

# 28. CDC SLA Conflict

## FIG-RT-19. 30s vs 3s

```text
Baseline A
CDC <= 30 sec
      │
      │ [CONFLICT]
      ▼
Baseline B
CDC <= 3 sec
```

### RUNTIME에서는 반드시 측정구간을 명시한다

```text
Source Commit
   ↓
Capture
   ↓
Transport
   ↓
Apply
   ↓
Consumer Visibility
```

---

# 29. #8 Recovery

## FIG-RT-20. CDC Restart

```text
CDC Failure
  ↓
Last Applied Position
  ↓
Restart
  ↓
Catch-up
  ↓
Reconcile
```

---

# 30. #8 금지

```text
Lag Metric 없음                    X
Restart Position 없음              X
Consumer Visibility 검증 없음       X
```

---

# 31. #9 ETL

## FIG-RT-21. ETL Runtime

```text
RDW / Source
   ↓
Extract
   ↓
Transform
   ↓
Validate
   ↓
Load
   ↓
ADW
   ↓
Reconcile
```

---

# 32. #9 필수 Evidence

```text
JobId
ExecutionId
Business Date
Start/End
Input Count
Output Count
Reject Count
Error
Restart Point
```

---

# 33. #9 Failure

```text
Extract Failure
Transform Failure
Data Quality Failure
Load Failure
Target Constraint
Resource Exhaustion
```

---

# 34. #9 Restart

## FIG-RT-22. ETL Restart

```text
FAILED
  ↓
Checkpoint / Last Step
  ↓
Restart
  ↓
Load
  ↓
Count Reconcile
```

---

# 35. #10 분석 / 의사결정 지원

## FIG-RT-23. Analytical Runtime

```text
BI / Analyst
   ↓
Query / OLAP / Self BI
   ↓
RDW / ADW
   ↓
Analysis Result
```

---

# 36. #10 Resource Isolation

## FIG-RT-24. Analytical Isolation

```text
Heavy BI Query
    │
    X
    └────► Online Service degradation
```

필요:

```text
Workload Separation
Resource Group
Concurrency Control
Query Governance
```

---

# 37. #11 File Runtime

## FIG-RT-25. File Runtime

```text
Producer
  ↓
READY
  ↓
TRANSFER
  ↓
RECEIVED
  ↓
VALIDATED
  ↓
PROCESSED
  ↓
ARCHIVED
```

---

# 38. #11 필수 항목

```text
InterfaceId
Filename
Business Date
Size
Hash
Record Count
Encryption
TransferId
Status
Recovery
```

---

# 39. #11 Failure

```text
Transfer Interrupted
Checksum Mismatch
Schema Error
Duplicate File
Partial File
Processing Error
```

---

# 40. #11 Recovery

## FIG-RT-26. File Recovery

```text
Failure
  ↓
Retry
  ↓
Quarantine
  ↓
Operator Review
  ↓
Reprocess
  ↓
Reconcile
```

---

# 41. #12 Batch Runtime

## FIG-RT-27. Batch Runtime

```text
Scheduler
   ↓
Job
   ↓
Step
   ↓
Reader
   ↓
Processor
   ↓
Writer
   ↓
Result
```

---

# 42. #12 Runtime Key

```text
JobId
JobInstance
ExecutionId
StepExecutionId
Business Date
Parameters
```

---

# 43. #12 완료 조건

```text
Job Status = COMPLETED
+
Data Reconciliation PASS
+
Downstream Dependency PASS
```

---

# 44. #12 금지

```text
Job Return Code만 성공                  X
Count Reconcile 없음                   X
Restart Policy 없음                    X
Online Pool 공유                       X
```

---

# 45. Runtime Type 전체 관계

## FIG-RT-28. Runtime Interaction

```text
#1/#2/#3 Online
      │
      ├────────► #4/#5 Integration
      │
      └────────► RDW

#6 Event Collection
      ↓
#7 Offering

#8 CDC
      ↓
RDW
      ↓
#9 ETL
      ↓
ADW
      ↓
#10 Analysis

#11 File
#12 Batch
→ Supporting Runtime
```

---

# 46. PDMG Online Runtime — AS-IS Reference

## FIG-RT-29. Full PDMG Online Flow

```text
HTTP Request
   │
   ▼
DefaultFilter
   │
   ▼
SecurityFilterChain
   │
   ▼
DispatcherServlet
   │
   ▼
ServicePreventionInterceptor.preHandle
   │
   ▼
OnlineTransactionController
   │
   ▼
TcfFacade
   │
   ▼
OnlineTimeoutExecutor
   │
   ▼
Worker TransactionTemplate BEGIN
   │
   ▼
TransactionDispatcher
   │
   ▼
TransactionHandler
   │
   ▼
Business Facade
   │
   ▼
BizPrePostAspect
   │
   ▼
Service
   │
   ▼
DAO / Mapper / SQL
   │
   ▼
Deadline Check
   │
   ├─ COMMIT
   └─ ROLLBACK
   │
   ▼
ResponseBodyAdvice
   │
   ▼
Interceptor.afterCompletion
   │
   ▼
Filter finally / Context Clear
   │
   ▼
HTTP Response
```

---

# 47. PDMG Current Snapshot

```text
[AS-IS SNAPSHOT]

tcf.enabled       = true
timeout.enabled   = true
timeout           = 5000ms
worker pool       = 20
queue capacity    = 100
legacy-web        = true
filter            = true
```

---

# 48. HTTP Request Lifecycle

## FIG-RT-30. Request Thread Life

```text
Request accepted
   ↓
Filter
   ↓
MVC
   ↓
Controller
   ↓
TcfFacade
   ↓
Future.get()
   ↓
Response / Timeout
```

---

# 49. Worker Lifecycle

## FIG-RT-31. Worker Thread Life

```text
Task submit
  ↓
Queue
  ↓
Worker selected
  ↓
Context/MDC install
  ↓
Transaction BEGIN
  ↓
Business
  ↓
Deadline check
  ↓
Commit/Rollback
  ↓
Context/MDC clear
```

---

# 50. Request vs Worker

## FIG-RT-32. Two Timelines

```text
Request Thread               Worker Thread
      │                            │
      ├─ submit ─────────────────► │
      │                            ├─ Context install
      │                            ├─ TX begin
      │ wait                       ├─ Business
      │                            ├─ SQL
      │                            ├─ Commit/Rollback
      │ ◄──────────────────────────┤
      │ response                   │
```

Timeout이면 두 생명주기가 분리된다.

---

# 51. ThreadLocal 전파

## FIG-RT-33. Context Propagation

```text
Request Thread
ServiceContext / MDC
      │ capture
      ▼
Worker Thread
ServiceContext / MDC
      │
      ▼
Business / Log
      │
      ▼
clear
```

자동 전파라고 가정하지 않는다.

---

# 52. Mutable Context Risk

## FIG-RT-34. Shared Reference Risk

```text
Request Thread
    │
    └──── same mutable ServiceContext ────┐
                                          ▼
                                    Worker Thread
```

Risk:

```text
Race
Lifecycle overlap
Servlet request/response reference sharing
```

TO-BE 후보:

```text
Immutable Snapshot
```

---

# 53. Transaction Boundary

## FIG-RT-35. Actual TX Boundary

```text
Worker
  ↓
TransactionTemplate BEGIN
  ↓
Dispatcher
  ↓
Handler
  ↓
Facade REQUIRED
  ↓
Service
  ↓
DAO / Mapper
  ↓
Deadline Check
  ↓
COMMIT / ROLLBACK
```

---

# 54. Handler가 TX 안이라는 의미

```text
TransactionTemplate
  {
    Dispatcher
    Handler
    Facade
    Service
    DAO
    SQL
  }
```

즉 Handler 선택/실행도 Outer Transaction 안에 있다.

---

# 55. Facade @Transactional

## FIG-RT-36. Join Outer TX

```text
Outer TransactionTemplate
      │
      ▼
Facade @Transactional(REQUIRED)
      │
      └─ joins existing transaction
```

Annotation이 물리 TX 시작점이라는 뜻은 아니다.

---

# 56. Transaction Manager 정합

```text
DataSource
  ↓
TransactionManager
  ↓
SqlSessionFactory
  ↓
SqlSessionTemplate
```

현재 RDW 축은 동일 DataSource/TransactionManager 정합을 확인해야 한다.

---

# 57. readOnly 주의

## FIG-RT-37. Outer TX vs readOnly

```text
Outer TX
readOnly?
   │
   ▼
Facade readOnly=true
```

Inner REQUIRED가 Outer에 참여하는 경우
Outer 속성이 실제 Query TX에 어떻게 적용되는지 검증 필요.

```text
[GAP]
```

---

# 58. 정상 Commit Sequence

## FIG-RT-38. Normal Commit

```text
Request
 ↓
Worker
 ↓
TX BEGIN
 ↓
Business
 ↓
SQL
 ↓
Deadline OK
 ↓
COMMIT
 ↓
Result
 ↓
HTTP 200
```

---

# 59. Business Error Rollback

## FIG-RT-39. Business Exception

```text
TX BEGIN
 ↓
Business
 ↓
BizException
 ↓
ROLLBACK
 ↓
Error Mapping
 ↓
HTTP 500 + Business Code [current]
```

---

# 60. Timeout 4개 시점

## FIG-RT-40. Timeout Types

```text
① Queue Wait
② Worker Execution
③ DB Connection Wait
④ SQL / External Wait
```

하나의 "5초"로 모든 지연을 설명하면 안 된다.

---

# 61. Request Timeout

## FIG-RT-41. Timeout Response

```text
Request
 ↓
Future.get(5000ms)
 ↓
Timeout
 ↓
cancel(true)
 ↓
HTTP 504
```

---

# 62. JDBC 취소 한계

```text
cancel(true)
   ↓
Thread Interrupt
   ↓
JDBC Driver Behavior?
   ↓
DB Statement Cancel?
```

반드시 Integration Test로 검증한다.

---

# 63. Deadline Late Commit Prevention

## FIG-RT-42. Late Commit Guard

```text
Request timed out
      │
      ▼
Worker SQL returns late
      │
      ▼
Deadline exceeded?
   ├─ YES → rollback
   └─ NO  → commit
```

---

# 64. Overload Sequence

## FIG-RT-43. Queue Full

```text
Request
 ↓
Worker Pool busy
 ↓
Queue full
 ↓
Reject
 ↓
OnlineOverloadException
 ↓
HTTP 503
```

---

# 65. Timeout vs Overload

```text
Timeout
= accepted but deadline exceeded

Overload
= execution capacity unavailable / queue full
```

운영에서 반드시 분리한다.

---

# 66. Resource Chain

## FIG-RT-44. Runtime Resource Chain

```text
Tomcat Request Thread
       ↓
PDMG Worker Pool
       ↓
Worker Queue
       ↓
Hikari Connection
       ↓
DB Session
       ↓
SQL / I/O
```

---

# 67. DB Slow → Runtime Failure

## FIG-RT-45. Cascading Slowdown

```text
Slow SQL
  ↓
Connection Hold
  ↓
Hikari Pending
  ↓
Worker Busy
  ↓
Queue Growth
  ↓
Request Timeout / Overload
```

---

# 68. TCF ON

```text
TCF ON
→ Common Controller
→ TcfFacade
→ Timeout Executor
→ Dispatcher
→ Handler
```

---

# 69. TCF OFF

```text
TCF OFF
→ Business Controller
→ Facade / Service
```

OnlineTimeoutExecutor는 TcfFacade 경로를 타지 않으므로
TCF OFF에서는 적용되지 않을 수 있다.

---

# 70. TCF ON/OFF Runtime GAP

## FIG-RT-46. Policy Difference

```text
TCF ON
Transaction / Timeout / Error / Routing
      │
      X consistency?
      │
TCF OFF
Business Controller / Method-specific TX
```

```text
[GAP]
Target Runtime Policy 통일 필요
```

---

# 71. STF / ETF

## FIG-RT-47. Exists vs Executed

```text
Class Exists
   │
Bean Exists
   │
Runtime Called?
```

Current `TcfFacade`에 STF/ETF 호출이 없으면:

```text
[AS-IS Executed]
로 그리지 않는다.
```

---

# 72. ServiceId Runtime

## FIG-RT-48. Routing

```text
ServiceId
  ↓
Registry
  ↓
Handler
  ↓
Business
```

---

# 73. ServiceId Routing Failure

```text
ServiceId 없음
   ↓
Handler Not Found
   ↓
ServiceHandlerNotFound
   ↓
Error Response
```

---

# 74. Response Assembly

## FIG-RT-49. Success Path

```text
Business Result
  ↓
ResponseBodyAdvice
  ↓
hdr_nhnis + dto
  ↓
HTTP Response
```

---

# 75. Error Assembly

## FIG-RT-50. Known Error Path

```text
Exception
 ↓
GlobalExceptionHandler
 ↓
NH_NIS_ERR_DTO
 ↓
ResponseBodyAdvice
 ↓
hdr_nhnis + result
```

---

# 76. Filter Early Error

## FIG-RT-51. Early Exit

```text
DefaultFilter
  │
  ├─ 400/401
  └─ sendError
      ↓
MVC / ResponseBodyAdvice 우회 가능
```

---

# 77. Runtime Error Taxonomy

```text
Validation
AuthN
AuthZ
Business
Routing
Timeout
Overload
DB
External
Unknown
```

---

# 78. Error → Recovery Matrix

| Error | Retry | Rollback | Caller Result | Recovery |
|---|---|---|---|---|
| Validation | N | N/A | 4xx/Business | 입력수정 |
| AuthN/AuthZ | N | N/A | 401/403 | 재인증/권한 |
| Business Reject | N | Y/Policy | Business Error | 업무조치 |
| Timeout | Caution | Y target | 504 | 상태확인 |
| Overload | Limited | N/A | 503 | Backoff |
| DB Error | Case | Y | 5xx | DB Recovery |
| External Error | Case | Y/Comp | 5xx | Retry/Manual |
| Unknown | N | Y | 500 | Investigation |

---

# 79. Security Runtime

## FIG-RT-52. Request Security Runtime

```text
Request
 ↓
Security Filter
 ↓
JWT Verify
 ↓
Principal
 ↓
Business Authorization
 ↓
Service
```

---

# 80. JWT Runtime

## FIG-RT-53. Token Runtime

```text
Login
  ↓
Token Issue
  ↓
Client Storage
  ↓
Bearer Request
  ↓
Verify
  ↓
Principal
  ↓
Authorization
```

---

# 81. JWT Subject vs Header User

## FIG-RT-54. Identity Runtime GAP

```text
JWT ssoId
   │
   │ ?
   ▼
Header optr_eno
   │
   ▼
ServiceContext.userContext
```

이 Binding을 Runtime Test로 증명해야 한다.

---

# 82. Session / JWT HA

```text
JWT Stateless Verification
+
Refresh / Revoke State
+
Optional HttpSession
```

따라서 Stateless 한 단어로 HA 설계를 끝내지 않는다.

---

# 83. Runtime Observability Big Picture

## FIG-RT-55. E2E Observability

```text
Client
 ↓
GSLB / L4
 ↓
WEB
 ↓
Tomcat JVM
 ↓
Filter / Security
 ↓
TCF
 ↓
ServiceId
 ↓
Worker
 ↓
TX
 ↓
DAO / SQL
 ↓
DB / External
 ↓
Response
```

각 구간에:

```text
Metric
Log
Trace
Health
Evidence
```

가 있어야 한다.

---

# 84. 거래 관측 Key

```text
GUID
ServiceId
UserId
Host
JVM
Thread
Worker
SqlId
InterfaceId
ErrorCode
```

---

# 85. 좋은 운영 질문

```text
어느 ServiceId가 느린가?
어느 JVM인가?
Request Thread가 막혔는가?
Worker Queue가 증가했는가?
Hikari Pending인가?
어느 SqlId인가?
DB Wait인가?
External System인가?
```

---

# 86. Tomcat / JVM Monitoring

## FIG-RT-56. JVM Metrics

```text
Request Threads
Busy Threads
Queue
Heap
Metaspace
GC
CPU
Thread Count
Restart
```

---

# 87. PDMG Worker Monitoring

```text
Pool Size
Active Worker
Queue Depth
Rejected
Task Duration
Timeout Count
```

---

# 88. Hikari Monitoring

```text
Active
Idle
Pending
Max
Acquire Time
Timeout
```

---

# 89. DB Monitoring

```text
Session
Active SQL
Wait Event
CPU
I/O
Lock
Long SQL
Query Timeout
```

---

# 90. Event Monitoring

```text
Produce Rate
Consume Rate
Lag
Partition
Error
Retry
DLQ
```

---

# 91. CDC Monitoring

```text
Capture Lag
Transport Lag
Apply Lag
Restart
Error
```

---

# 92. ETL Monitoring

```text
Job Duration
Rows In
Rows Out
Reject
Step Failure
Restart
```

---

# 93. File Monitoring

```text
Transfer Status
File Size
Hash
Record Count
Duplicate
Quarantine
```

---

# 94. Batch Monitoring

```text
Job Status
Step Status
Delay
Retry
Restart
Business Date
```

---

# 95. Security Monitoring

```text
Auth Failure
JWT Verify Failure
Unknown kid
Revoked Token
Authorization Denied
Suspicious Direct Access
```

---

# 96. Alert → Diagnosis → Recovery

## FIG-RT-57. Operations Loop

```text
Metric / Log
   ↓
Alert
   ↓
Diagnosis
   ↓
Runbook
   ↓
Recovery
   ↓
Evidence
   ↓
Postmortem / GAP
```

---

# 97. Alert without Runbook

```text
Alert
  ↓
Operator sees red
  ↓
What now?
```

이 상태는 운영 가능한 Observability가 아니다.

---

# 98. HA Strategy

## FIG-RT-58. Application HA

```text
L4
├─ Node A
└─ Node B
```

Failure:

```text
Node A X
  ↓
Health Check
  ↓
Remove Member
  ↓
Node B serves
```

---

# 99. AP VM Failure

## FIG-RT-59. Scenario #1

```text
AP VM #1 X
   ↓
L4 Detect
   ↓
Remaining AP
   ↓
Session / In-flight handling
   ↓
Residual Capacity
```

Evidence:

```text
Detection Time
Failover Time
p95
Error Count
Session Behavior
```

---

# 100. AP Group Failure

## FIG-RT-60. Scenario #2

```text
AP Group A X
   ↓
Alternate Group?
   ↓
Route
   ↓
Capacity
```

---

# 101. RDW Failure

## FIG-RT-61. Scenario #3

```text
RDW Failure
   ↓
Online Data Service Impact
   ↓
Fallback / Failover?
   ↓
Recovery
```

---

# 102. ADW Failure

## FIG-RT-62. Scenario #4

```text
ADW Failure
   ↓
BI / Analytical Impact
   ↓
Online unaffected?
   ↓
Recovery
```

FAST/DEEP 분리의 검증 시나리오다.

---

# 103. Kafka/Event Failure

## FIG-RT-63. Scenario #5

```text
Broker / Consumer Failure
   ↓
Lag
   ↓
Buffer
   ↓
Restart
   ↓
Replay
   ↓
Catch-up
```

---

# 104. CDC Relay Failure

## FIG-RT-64. Scenario #6

```text
CDC Relay X
   ↓
Lag increases
   ↓
Restart
   ↓
Catch-up
   ↓
Reconcile
```

---

# 105. Integration Failure

## FIG-RT-65. Scenario #7

```text
Gateway / External Path X
   ↓
Request Error / Queue
   ↓
Retry Policy
   ↓
Recovery
```

---

# 106. Center DR

## FIG-RT-66. Scenario #8

```text
Main Center X
   ↓
Disaster Declare
   ↓
GSLB Route
   ↓
DR WEB/WAS
   ↓
Security / Key
   ↓
DR DB
   ↓
External
   ↓
Business Validation
```

---

# 107. Failure Scenario 필수 필드

```yaml
failureScenario:
  scenarioId:
  target:
  trigger:
  detection:
  impact:
  autoAction:
  manualAction:
  retry:
  dataConsistency:
  capacityAfterFailure:
  rto:
  rpo:
  evidence:
  result:
```

---

# 108. Scalability Runtime

## FIG-RT-67. Scale Trigger

```text
Metric Trend
   ↓
Threshold / Capacity Rule
   ↓
Scale Decision
   ↓
Scale-out / Scale-up
   ↓
Rebalance
   ↓
Re-test
```

---

# 109. WEB/WAS Scale-out

```text
Add Node
  ↓
Deploy same artifact
  ↓
Config
  ↓
L4 member
  ↓
Health
  ↓
Traffic
```

---

# 110. Event Scale

```text
Lag
  ↓
Consumer Increase
  ↓
Partition Balance
  ↓
Catch-up
```

---

# 111. Data Scale

```text
Storage / Query / Load
   ↓
Scale Data Resource
   ↓
Rebalance / Parallelism
   ↓
Performance Re-test
```

---

# 112. Scale 후 재검증

```text
Scale
 ↓
Functional Test
 ↓
Performance Test
 ↓
Failure Test
 ↓
Observability Check
```

---

# 113. DR 완료조건

## FIG-RT-68. DR Complete

```text
DR Resource Exists
      │
      ▼
Artifact Synced
      │
      ▼
Config Synced
      │
      ▼
Security Key Synced
      │
      ▼
Data Replicated
      │
      ▼
Route Ready
      │
      ▼
Runbook Ready
      │
      ▼
DR Test PASS
```

---

# 114. RTO / RPO

## FIG-RT-69. Recovery Objectives

```text
Failure Time
   │
   ├──── RPO ────► Data Loss Window
   │
   └──── RTO ────► Service Recovery Time
```

최종 수치는 최신 승인 Baseline으로 확정한다.

---

# 115. Failover 순서

## FIG-RT-70. DR Failover

```text
Detect
 ↓
Declare
 ↓
Stop / Fence Primary
 ↓
Data Ready
 ↓
Application Ready
 ↓
Network Route
 ↓
Security Ready
 ↓
External Check
 ↓
Business Validation
```

---

# 116. Failback

## FIG-RT-71. Failback

```text
DR Running
  ↓
Primary Restored
  ↓
Data Re-sync
  ↓
Config / Artifact verify
  ↓
Controlled Return
  ↓
Business Validation
```

---

# 117. Backup Architecture

## FIG-RT-72. Backup Scope

```text
Backup
│
├─ DB
├─ Configuration
├─ Artifact / Manifest
├─ Key / Certificate
└─ Operational Metadata
```

---

# 118. Backup 성공 ≠ Recovery Proven

```text
Backup Job SUCCESS
       │
       ▼
Restore Test?
   ├─ NO → Recovery not proven
   └─ YES → Evidence
```

---

# 119. DB Backup

```text
DB Backup
  ↓
Restore
  ↓
Recover
  ↓
Consistency
  ↓
Application Validation
```

---

# 120. Config Backup

```text
httpd.conf
server.xml
application.yml
JVM options
L4/GSLB config
```

복구 가능한 형태로 Version 관리한다.

---

# 121. Key / Certificate Backup

```text
Private Key
Certificate
Trust Store
HMAC Secret
JWKS metadata
```

보안 절차에 따라 안전하게 복구 가능해야 한다.

---

# 122. Monitoring RACI

## FIG-RT-73. Runtime Ownership

```text
Application
→ Business / Service Runtime

Framework
→ Thread / Context / TCF / Error

Infra
→ Host / Network / JVM

DBA
→ DB / SQL / Session

Security
→ Auth / JWT / Audit

Operations
→ Alert / Runbook / Incident
```

---

# 123. Alert Severity

```text
Critical
High
Medium
Info
```

Severity는:

```text
Business Impact
Recovery Urgency
Data Risk
Security Risk
```

를 기준으로 정의한다.

---

# 124. Runtime Evidence Package

## FIG-RT-74. Evidence Package

```text
Runtime Evidence
│
├─ Scenario ID
├─ Architecture Baseline ID
├─ Model Version
├─ Source Commit
├─ Artifact Hash
├─ Deployment ID
├─ Environment
├─ ServiceId
├─ GUID / TraceId
├─ Start/End
├─ Metrics
├─ Logs
├─ Test Result
├─ Screenshot / Report
└─ Evidence Hash
```

---

# 125. Evidence 종류

```text
Functional Evidence
Performance Evidence
Transaction Evidence
Timeout Evidence
Security Evidence
HA Evidence
DR Evidence
Backup/Restore Evidence
Observability Evidence
```

---

# 126. NFR 5축 Runtime Validation

## FIG-RT-75. NFR Runtime Gate

```text
Performance
Availability
Scalability
Security
Observability
      │
      ▼
Runtime Scenario
      │
      ▼
Evidence
      │
      ▼
PASS / FAIL / GAP
```

---

# 127. Performance Validation

```text
Normal Load
Peak Load
Stress
Soak
Slow DB
Slow External
Node Down
```

---

# 128. FAST Validation

```text
#7 Event/Offer
  ↓
Latency
Lag
Error
Replay
```

---

# 129. DEEP Validation

```text
#9/#10
ETL / BI
  ↓
Throughput
Duration
Query Performance
Resource Isolation
```

---

# 130. CDC Validation

```text
Source Commit
  ↓
Capture
  ↓
Transport
  ↓
Apply
  ↓
Consumer Visibility
```

실제 SLA는 이 측정구간으로 검증한다.

---

# 131. Batch Validation

```text
Schedule
 ↓
Start
 ↓
Job
 ↓
Data Count
 ↓
Complete
 ↓
Downstream Ready
```

---

# 132. Timeout 계층 검증

## FIG-RT-76. Timeout Runtime Validation

```text
DB Query Timeout
   <
TX / Worker Deadline
   <
Server / External Timeout
   <
Client Timeout
```

검증:

```text
Config
+
Integration Test
+
Runtime Trace
```

---

# 133. PDMG 5초 의미

```text
PDMG 5000ms
=
Current Online Worker Deadline Snapshot
```

아니다:

```text
Client SLA
DB Query Timeout
Enterprise Standard
```

---

# 134. Runtime Gate

## FIG-RT-77. Runtime Gate

```text
Scenario Defined?
  ↓
Executable?
  ↓
Exact Artifact Deployed?
  ↓
Trace Captured?
  ↓
Expected Outcome?
  ↓
Recovery Proven?
  ↓
Evidence Stored?
  ↓
PASS
```

---

# 135. HA / DR 완료 Gate

```text
Node Failover PASS
Group Failure PASS
DB Failure PASS
Event Failure PASS
CDC Failure PASS
Center DR PASS
Restore PASS
```

---

# 136. Runtime 준수 규범

```text
모든 Runtime은 Type을 가진다.
모든 Runtime은 Owner를 가진다.
모든 Runtime은 Sequence를 가진다.
모든 Runtime은 Failure를 가진다.
모든 Runtime은 Recovery를 가진다.
모든 Runtime은 Evidence를 가진다.
```

---

# 137. Sequence 규범

```text
Actor
Entry
Thread
TX
Resource
External
Outcome
```

을 생략하지 않는다.

---

# 138. SLO 규범

```text
SLO
=
Metric
+
Measurement Point
+
Window
+
Threshold
+
Owner
```

수치 하나만 적는 것은 SLO가 아니다.

---

# 139. Retry 규범

```text
Retry
=
Retryable Error
+
Backoff
+
Max Retry
+
Idempotency
+
Final Recovery
```

---

# 140. Monitoring 규범

```text
Metric only
≠ Monitoring Complete

Metric
→ Alert
→ Diagnosis
→ Runbook
→ Recovery
```

---

# 141. Evidence 규범

```text
Log File
≠ Runtime Evidence Package
```

Evidence는 반드시 Release/Deployment/Scenario와 연결한다.

---

# 142. PDMG Runtime 규범

```text
Request Thread
Worker Thread
Transaction
JDBC
```

을 한 생명주기로 설명하지 않는다.

---

# 143. Timeout 규범

```text
HTTP Timeout 후
Worker / DB 상태 확인 없이
성공/실패를 단정하지 않는다.
```

---

# 144. Transaction 규범

```text
@Transactional annotation
=
TX Owner
```

라고 단정하지 않는다.

---

# 145. Worker 규범

```text
Context/MDC
Capture
Install
Clear
```

을 반드시 관리한다.

---

# 146. Overload 규범

```text
Overload
≠ Timeout
```

Queue Full / Reject를 별도 운영지표로 본다.

---

# 147. CDC 규범

```text
"실시간"
이라고만 쓰지 않는다.

Lag 측정구간을 정의한다.
```

---

# 148. File 규범

```text
Transfer Success
≠ Business Process Complete
```

---

# 149. Batch 규범

```text
Job COMPLETED
+
Data Reconcile
```

를 완료기준으로 본다.

---

# 150. DR 규범

```text
DR Resource Exists
≠ DR Ready
```

---

# 151. Backup 규범

```text
Backup Success
≠ Restore Proven
```

---

# 152. Runtime Anti-pattern

## FIG-RT-78. Runtime Anti-pattern Map

```text
HTTP Timeout = Worker End                     X
Worker Cancel = JDBC Cancel                   X
@Transactional 위치 = TX Begin                X
Event = Online Thread                         X
CDC = Strong Sync                             X
Batch Success = Data Correct                  X
Backup Success = Recovery Proven              X
DR Center Exists = DR Complete                X
Metric Exists = Operable                      X
Log Exists = Runtime Evidence                 X
```

---

# 153. Runtime Inventory Template

```yaml
runtime:
  runtimeId:
  type:
  businessFlow:
  owner:
  entry:
  actors:
  sequence:
  threads:
  txBoundary:
  timeout:
  pools:
  failureModes:
  recovery:
  metrics:
  slo:
  evidence:
  status:
```

---

# 154. Sequence Template

```text
Actor A
  │
  ▼
Entry
  │
  ▼
Framework
  │
  ▼
Business
  │
  ▼
Data / External
  │
  ▼
Outcome
```

---

# 155. Failure Scenario Template

```yaml
failure:
  id:
  trigger:
  detection:
  affectedRuntime:
  userImpact:
  dataImpact:
  automaticAction:
  manualAction:
  rollback:
  retry:
  failover:
  recovery:
  evidence:
```

---

# 156. Monitoring Inventory Template

```yaml
monitoring:
  component:
  metrics:
  thresholds:
  alert:
  owner:
  runbook:
  dashboard:
  evidence:
```

---

# 157. Backup Inventory Template

```yaml
backup:
  asset:
  type:
  frequency:
  retention:
  encryption:
  restoreProcedure:
  lastRestoreTest:
  owner:
  evidence:
```

---

# 158. DR Inventory Template

```yaml
dr:
  service:
  primary:
  recoverySite:
  rto:
  rpo:
  dependencies:
  route:
  dataReplication:
  securityDependencies:
  runbook:
  lastDrTest:
  evidence:
```

---

# 159. Runtime → Observability Trace

## FIG-RT-79. Runtime Trace

```text
Runtime Type
   ↓
ServiceId / InterfaceId
   ↓
GUID / EventId / JobId
   ↓
JVM / Worker / SQL
   ↓
Metric / Log
   ↓
Evidence
```

---

# 160. Runtime → Physical Trace

```text
Runtime
  ↓
Logical Node
  ↓
Host
  ↓
JVM
  ↓
WAR
  ↓
Pool
```

---

# 161. Runtime → Data Trace

```text
Runtime
  ↓
ServiceId
  ↓
DAO / Mapper
  ↓
SqlId
  ↓
Table / View
```

---

# 162. Runtime → Interface Trace

```text
Runtime
  ↓
InterfaceId
  ↓
Producer
  ↓
Mechanism
  ↓
Consumer
```

---

# 163. Runtime Evidence Closed Loop

## FIG-RT-80. Runtime → New Baseline

```text
Architecture
  ↓
Model
  ↓
Source / Config
  ↓
Test
  ↓
Deploy
  ↓
Runtime
  ↓
Evidence
  ↓
Drift
  ↓
GAP / ADR
  ↓
New Baseline
```

---

# 164. Drift 대상

```text
Expected Runtime Type
vs Actual Runtime

Expected Timeout
vs Config

Expected TX Boundary
vs Runtime

Expected Deployment
vs Host

Expected Security
vs Runtime

Expected SLO
vs Metric
```

---

# 165. PDMG Drift 예시

```text
Design
Filter order = old value

Current
HIGHEST_PRECEDENCE + 20

→ DRIFT
```

또는:

```text
Target
Standard Identity Binding

Current
JWT ssoId ↔ optr_eno uncertain

→ GAP
```

---

# 166. Runtime Conformance Rule 후보

```text
R-RUNTIME-TYPE
R-RUNTIME-SEQUENCE
R-RUNTIME-EVIDENCE
R-TX-OWNER
R-TIMEOUT-HIERARCHY
R-WORKER-CONTEXT-CLEAR
R-OVERLOAD-MONITOR
R-CDC-LAG
R-BATCH-RECONCILE
R-DR-TEST
R-BACKUP-RESTORE
```

---

# 167. GAP Register

| ID | GAP | 영향 |
|---|---|---|
| GAP-RT-01 | Runtime Type 전수 Inventory 미완료 | Governance |
| GAP-RT-02 | ServiceId→Runtime Type Mapping 미완료 | Traceability |
| GAP-RT-03 | Query Timeout 미확정 | DB |
| GAP-RT-04 | TX Timeout 미확정 | Transaction |
| GAP-RT-05 | JDBC Interrupt/Cancel 실증 미완료 | Timeout |
| GAP-RT-06 | Mutable ServiceContext 개선 미결정 | Thread |
| GAP-RT-07 | TCF OFF Transaction Policy 불일치 | Runtime |
| GAP-RT-08 | Generic Exception 표준 미완료 | Error |
| GAP-RT-09 | JWT Identity Binding 미완료 | Security |
| GAP-RT-10 | Runtime Dashboard/Alert Threshold 미확정 | Observability |
| GAP-RT-11 | CDC 최종 SLA 미확정 | Data |
| GAP-RT-12 | Event Replay/DLQ 전수정책 미완료 | Event |
| GAP-RT-13 | DR RTO/RPO 미확정 | DR |
| GAP-RT-14 | Restore Test Evidence 미완료 | Backup |
| GAP-RT-15 | Runtime Evidence Manifest 자동화 미완료 | Closed Loop |

---

# 168. RISK Register

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-RT-01 | HTTP timeout 후 late commit | Critical |
| RISK-RT-02 | Worker thread leak / context leak | Critical |
| RISK-RT-03 | Hikari/DB slow가 전체 queue를 막음 | High |
| RISK-RT-04 | TCF ON/OFF 정책 차이 | High |
| RISK-RT-05 | Unknown exception 비표준 응답 | High |
| RISK-RT-06 | Identity mismatch | Critical |
| RISK-RT-07 | Event lag 미탐지 | High |
| RISK-RT-08 | CDC lag 미탐지 | Critical |
| RISK-RT-09 | Batch 성공 오판 | High |
| RISK-RT-10 | DR Resource만 있고 Runbook/Test 없음 | Critical |
| RISK-RT-11 | Backup 성공만으로 복구 가능 판단 | Critical |
| RISK-RT-12 | Runtime Evidence가 Release와 연결 안 됨 | Critical |

---

# 169. ADR 후보

```text
ADR-RT-01 Runtime Type SSOT
ADR-RT-02 TCF ON/OFF Target Runtime
ADR-RT-03 Transaction Ownership
ADR-RT-04 Timeout Budget
ADR-RT-05 JDBC Cancel Strategy
ADR-RT-06 Context Snapshot Model
ADR-RT-07 Overload Policy
ADR-RT-08 Error Runtime Standard
ADR-RT-09 JWT Identity Binding
ADR-RT-10 Event Replay / DLQ
ADR-RT-11 CDC SLA
ADR-RT-12 Runtime Alert Threshold
ADR-RT-13 DR RTO/RPO
ADR-RT-14 Backup Restore Test
ADR-RT-15 Runtime Evidence Manifest
```

---

# 170. CONFIRMED / WORKING BASELINE

```text
[CONFIRMED / WORKING BASELINE]

- Runtime Type #1~#12 구조
- PDMG TCF ON Online Runtime
- Request Thread / Worker Thread 분리
- Worker TransactionTemplate Boundary
- Facade REQUIRED joins outer TX
- Current PDMG timeout 5000ms
- Worker Pool 20 / Queue 100
- Deadline Late Commit Prevention
- HTTP 504 != Worker End
- Error / Success Envelope current pattern
- GUID / ServiceId Runtime Trace
- HA/DR/Backup는 각각 별도 책임
```

---

# 171. CONFLICT / OPEN

```text
[CONFLICT-RT-01]
CDC SLA 30s vs 3s

[OPEN-RT-01]
Spring TX Timeout

[OPEN-RT-02]
JDBC Query Timeout

[OPEN-RT-03]
JDBC Interrupt / Cancel actual behavior

[OPEN-RT-04]
Runtime Type별 SLO

[OPEN-RT-05]
DR RTO/RPO

[OPEN-RT-06]
Alert Threshold

[OPEN-RT-07]
PDMG TCF OFF Target Policy

[OPEN-RT-08]
Session/JWT DR Behavior
```

---

# 172. Runtime Completion Gate

## FIG-RT-81. Final Runtime Gate

```text
Runtime Type Defined?
   ↓ YES
Sequence Defined?
   ↓ YES
Thread / TX Defined?
   ↓ YES
Timeout / Error Defined?
   ↓ YES
Failure Scenario Defined?
   ↓ YES
Recovery Defined?
   ↓ YES
Monitoring Defined?
   ↓ YES
Runtime Test Executed?
   ↓ YES
Evidence Captured?
   ↓ YES
Critical Drift = 0?
   ↓ YES
RUNTIME PASS
```

---

# 173. 전체 Architecture Route 완결

## FIG-RT-82. VISION → RUNTIME

```text
VISION
왜 바꾸는가?
   ↓
BIG PICTURE
누가 무엇을 책임하는가?
   ↓
LOGICAL
어떤 논리 구조로 분리하는가?
   ↓
PHYSICAL
어디에 배치하는가?
   ↓
MECHANISM
어떤 규칙으로 동작하는가?
   ↓
RUNTIME
실제로 어떻게 실행되고 실패하며 복구되는가?
   ↓
EVIDENCE
설계대로 동작했음을 무엇으로 증명하는가?
```

---

# 174. RUNTIME 최종 통합 지도

## FIG-RT-83. Runtime Summary

```text
ENTRY
Request / Event / Change / File / Schedule
   ↓
RUNTIME TYPE
#1 ~ #12
   ↓
SEQUENCE
Filter / Security / TCF / Business / Data
   ↓
RESOURCE
Request Thread / Worker / Hikari / DB
   ↓
TRANSACTION
BEGIN / COMMIT / ROLLBACK
   ↓
OUTCOME
Success / Error / Timeout / Overload
   ↓
RECOVERY
Retry / Replay / Restart / Failover
   ↓
OBSERVABILITY
GUID / ServiceId / Metric / Log / Trace
   ↓
AVAILABILITY
HA / DR / Backup / Restore
   ↓
RUNTIME EVIDENCE
   ↓
DRIFT / GAP / ADR
   ↓
NEW BASELINE
```

---

# 175. Definition of Done

## Runtime Type
- [x] #1~#12 분류
- [x] Primary/Secondary 개념
- [x] 유형 없는 신설 금지

## Online
- [x] PDMG Full Runtime
- [x] Request/Worker 분리
- [x] Context/MDC
- [x] TX Boundary
- [x] Commit/Rollback
- [x] Timeout/Overload

## Data / Event / File / Batch
- [x] Event Runtime
- [x] CDC Runtime
- [x] ETL Runtime
- [x] BI Runtime
- [x] File Runtime
- [x] Batch Runtime

## Security / Error
- [x] JWT Runtime
- [x] Identity Binding GAP
- [x] Success/Error Assembly
- [x] Early Error
- [x] Error→Recovery Matrix

## Observability
- [x] JVM/Worker/Hikari/DB
- [x] Event/CDC/ETL/File/Batch
- [x] Security Monitoring
- [x] Alert→Runbook→Recovery

## HA / DR / Backup
- [x] 8개 Failure Scenario
- [x] Scale Trigger
- [x] DR Failover/Failback
- [x] Backup/Restore
- [x] DR Completion Gate

## Evidence / Governance
- [x] Runtime Evidence Package
- [x] NFR Validation
- [x] Drift
- [x] GAP/RISK/ADR
- [x] Final Runtime Gate

**RUNTIME 장 판정: CONDITIONAL PASS**

### PASS 전환 조건

1. Runtime Type 전수 Inventory 생성
2. ServiceId→Runtime Type Mapping
3. Query/TX Timeout 실제값 수집
4. JDBC Cancel Integration Test
5. Worker Context Snapshot 정책 확정
6. TCF OFF Runtime Target 정책 확정
7. CDC 최종 SLA 확정
8. Event/File/Batch Recovery Test
9. HA/DR End-to-End Test
10. Restore Test
11. Runtime Evidence Manifest 자동화
12. Critical Runtime Drift 0건

---

# 176. 다음 단계 — Architecture Closed Loop

RUNTIME 이후에는 새로운 기술 장을 더 붙이는 것이 핵심이 아니다.

이제 해야 할 것은:

```text
VISION
BIG PICTURE
LOGICAL
PHYSICAL
MECHANISM
RUNTIME
     │
     ▼
TRACEABILITY
     │
     ▼
EVIDENCE
     │
     ▼
DRIFT
     │
     ▼
GAP / ADR
     │
     ▼
NEW ARCHITECTURE BASELINE
```

이다.

즉,

> **RUNTIME은 Top-down Architecture의 마지막 Drill-down 단계이며, 이후부터는 다시 Bottom-up Evidence를 통해 상위 Architecture를 검증하는 단계로 전환된다.**
