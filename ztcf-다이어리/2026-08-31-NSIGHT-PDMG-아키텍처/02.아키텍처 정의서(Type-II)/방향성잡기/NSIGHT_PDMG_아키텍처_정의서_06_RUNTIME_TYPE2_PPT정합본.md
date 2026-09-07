# NSIGHT / PDMG 아키텍처 정의서 — 06. RUNTIME

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 문서 ID: NSIGHT-ARCH-TYPE2-RUNTIME-06  
> Architecture Level: **RUNTIME**  
> PPT 공식 범위: **7. 런타임 아키텍처 / 11. 기타**  
> 문서 상태: Draft / Evidence-First / PPT-Aligned  
> 작성일: 2026-08-31  
> 선행: `05_MECHANISM`  
> 후속: `07_EVIDENCE_GAP_ADR_TRACEABILITY`  
> Evidence Level: PPT Runtime Type + Existing Runtime/Ops Definition + PDMG Source Runtime Evidence

---

# 0. Evidence Register

| ID | 근거 | 사용 목적 | 상태 |
|---|---|---|---|
| EV-RT-01 | `07_런타임아키텍처_정의서.md` | 6대 유형·12개 소유형·SLO/재처리/Gate | `[WORKING BASELINE]` |
| EV-RT-02 | PDMG Online Runtime / TCF Flow 정의서 | 실제 HTTP→Framework→DB 실행 Sequence | `[AS-IS EVIDENCE]` |
| EV-RT-03 | Transaction/Timeout/Thread/DB 정의서 | Request/Worker/TX/Timeout 실제 경계 | `[AS-IS EVIDENCE]` |
| EV-RT-04 | `11_모니터링가용성DR백업_정의서.md` | NFR·HA/DR·장애시나리오·Backup·Validation | `[SUPPLEMENTAL-DEFINITION]` |
| EV-RT-05 | DevOps/OM/Observability 정의서 | Metric/Alert/Runbook/Runtime Evidence | `[WORKING BASELINE]` |
| EV-RT-06 | `05_MECHANISM_TYPE2_PPT정합본` | Runtime 입력 계약 | `[CURRENT BASELINE DRAFT]` |

---

# 1. 핵심 결론

RUNTIME은 설계된 Mechanism이 **실제 시간순으로 어떻게 실행되고, 실패하며, 복구되고, 증명되는가**를 정의한다.

```text
Business Request / Event / Data / File / Job
        ↓
Runtime Type
        ↓
Sequence
        ↓
Thread / Transaction / Resource
        ↓
Success / Error / Timeout / Retry
        ↓
Metric / Log / Trace
        ↓
Recovery / Runbook
        ↓
Runtime Evidence
```

[WORKING BASELINE] NSIGHT Runtime은:

```text
대유형 6
소유형 12
```

로 관리한다.

```text
채널
연계
마케팅 이벤트
데이터 분석/제공
파일
배치
```

PDMG는 이 전체 Runtime 중 **온라인 Application 거래의 실제 AS-IS Reference**다.

---

# 2. 목적 / 범위

본 장은 다음 질문에 답한다.

1. NSIGHT의 업무 Runtime 유형은 어떻게 분류되는가?
2. 각 Runtime 유형의 Actor/Entry/IF/Data/SLO/Owner는 무엇인가?
3. 단말/미니싱글뷰/UMS 거래는 실제로 어떻게 흐르는가?
4. PDMG Online 요청 한 건은 실제 어떤 Framework/Business Layer를 통과하는가?
5. Request Thread와 Worker Thread는 어떻게 분리되는가?
6. DB Transaction은 어디서 시작하고 끝나는가?
7. Timeout 응답과 Worker/JDBC/DB 종료 시점은 같은가?
8. Error/Overload/Retry/Compensation을 어떻게 처리하는가?
9. Monitoring/Availability/Scalability/DR/Backup을 어떤 Evidence로 검증하는가?
10. Runtime 결과를 어떻게 Architecture Baseline으로 되돌리는가?

---

# 3. PPT 공식 구조 / Content Gap

[PPT-TOC]

```text
7. 런타임 아키텍처
   7.1 업무 처리 유형
   7.2 단말 거래 처리
   7.3 미니 싱글뷰
   7.4 UMS 고객 통지

11. 기타
   11.1 시스템 모니터링
   11.2 시스템 가용성
   11.3 시스템 확장성
   11.4 DR 구성 (센터 간 가용성)
   11.5 백업 구성
```

Content 상태:

| 절 | 상태 |
|---|---|
| 7.1 | `[PPT-BODY]` 상세 존재 |
| 7.2 | `[PPT-CONTENT-GAP]` 가능, 8/9장 Cross Reference |
| 7.3 | `[PPT-TOC]` + 8장 거래처리 경로 Cross Reference |
| 7.4 | `[PPT-CONTENT-GAP]` 가능 |
| 11.1~11.5 | `[PPT-CONTENT-GAP]` → 기존 Ops 정의서 보강 |

---

# 4. Runtime Type 체계

## 4.1 대유형 6

```text
1. 채널
2. 연계
3. 마케팅 이벤트
4. 데이터 분석/제공
5. 파일
6. 배치
```

## 4.2 소유형 12

| # | 소유형 | 대표 Mechanism |
|---:|---|---|
| 1 | 정보계 단말 거래 | Direct / Standard Entry |
| 2 | 통합업무시스템 거래 | MCA |
| 3 | 미니 싱글뷰 | MCA → Application |
| 4 | 대내 연계 | APIM/API |
| 5 | 대외 연계 | APIM/GSE/대외 연계 |
| 6 | 마케팅 이벤트 수집 | Collector/Event |
| 7 | 오퍼링/고객통지 | EBM/UMS |
| 8 | CDC | OGG/CDC |
| 9 | ETL | ETL/Batch |
| 10 | 데이터 분석/제공 | JDBC/BI/Analytics |
| 11 | 파일 | FOS/File |
| 12 | 배치 | Control-M/Batch FW |

실제 프로젝트 Baseline에서 번호/명칭이 변경되면 변경이력을 남긴다.

---

# 5. Runtime Inventory

모든 Runtime 유형은 최소 다음 필드를 가진다.

```text
Runtime Type ID
Name
Purpose
Actor
Entry
Interface / Mechanism
ServiceId / Event / Job / File ID
Application/System
Data
SYNC/ASYNC
Sequence
Thread / Resource Pool
Transaction
Timeout / SLO
Retry / Compensation
Monitoring
Owner
Evidence
Status
GAP / ADR
```

---

# 6. 7.1 업무 처리 유형 — 전체 Text Architecture

```text
[Channel Runtime #1~#3]
User / Channel
      ↓
MCA / Direct
      ↓
Application
      ↓
RDW / Service

[Integration Runtime #4~#5]
System A
      ↓
APIM / GSE / External Boundary
      ↓
System B

[Marketing Event #6~#7]
Behavior / Event
      ↓
Collector / Event
      ↓
Processing / EBM
      ↓
UMS / Offer / Push

[Data #8~#10]
Core DB ─ CDC → RDW
RDW ─ ETL → ADW
User / BI → Analysis / Query

[File #11]
Sender → FOS → Landing → Validate → Consumer

[Batch #12]
Control-M → Agent/Shell → Batch FW → Job/Step → DB
```

---

# 7. Channel Runtime (#1~#3)

## 7.1 #1 정보계 단말 거래

```text
Information Terminal
        ↓
Standard Entry / Direct
        ↓
Application Framework
        ↓
Business Service
        ↓
DB / Interface
        ↓
Response
```

목표:

```text
동기 응답
ServiceId / GUID
표준 Error
P95/SLO 측정
```

## 7.2 #2 통합업무시스템 거래

```text
Integrated Work System
        ↓
MCA
        ↓
Information Application
        ↓
Business
        ↓
Response
```

MCA의 전문변환/오류변환 책임과 Application 업무책임을 분리한다.

## 7.3 #3 미니 싱글뷰

[PPT-TOC 7.3] + [PPT-BODY CROSS-REFERENCE]

```text
Channel / Integrated Work
        ↓
MCA
        ↓
Mini Single View Service
        ↓
Data / Application
```

정확한 Context/URL/Deployment는 PPT와 Source를 교차 확인한다.

---

# 8. Integration Runtime (#4~#5)

## 8.1 #4 대내 연계

```text
NSIGHT Application
       ↓
Internal Integration / APIM
       ↓
Internal System
       ↓
Response / Async Result
```

필수:

```text
Interface ID
Timeout
Idempotency
Auth
Error Mapping
Trace
```

## 8.2 #5 대외 연계

```text
NSIGHT
  ↓
External Gateway / DMZ Boundary
  ↓
External Institution
```

대내 연계보다 추가로:

```text
Certificate
Encryption
Reconciliation
External SLA
RTO / Recovery
```

를 별도 관리한다.

---

# 9. Marketing Event Runtime (#6~#7)

## 9.1 #6 Event 수집

```text
Customer Behavior / Event
        ↓
Collector
        ↓
Event Broker
        ↓
Behavior Processing
        ↓
Store / Trigger
```

원칙:

```text
온라인 WAS에서 대량 Event 처리 금지
Event Consumer Lag 관측
Replay 가능성 정의
```

## 9.2 #7 Offering / UMS 고객 통지

```text
Trigger / Customer Context
        ↓
EBM / Offer Decision
        ↓
UMS
        ├─ SMS
        ├─ PUSH
        └─ MAIL
```

수집 #6과 Offering #7을 하나의 Transaction으로 뭉개지 않는다.

[PPT 7.4] 독립 상세가 부족하면 `[PPT-CONTENT-GAP]`을 유지한다.

---

# 10. Data Runtime (#8~#10)

## 10.1 #8 CDC

```text
Core Commit
   ↓
Capture
   ↓
Trail / Relay / Downstream
   ↓
RDW Apply
   ↓
Checkpoint / Lag / Reconciliation
```

관측:

```text
Capture Lag
Apply Lag
Checkpoint
Error
Backlog
```

CDC를 ETL SLA로 대체하지 않는다.

## 10.2 #9 ETL

```text
Source / BCV / RDW
        ↓
Extract
        ↓
Transform
        ↓
Load
        ↓
RDW / ADW / External
```

필수:

```text
Batch Window
Input Count
Output Count
Hash / Reconciliation
Restart Point
Owner
Completion Time
```

## 10.3 #10 분석/제공

```text
BI / Analyst / Application
       ↓
Query / Analytics
       ↓
RDW / ADW
       ↓
Result / Report
```

대량분석이 RDW 운영성 workload를 침해하지 않도록 소유/부하 기준을 적용한다.

---

# 11. File Runtime (#11)

```text
Sender
  ↓
FOS / File Transfer
  ↓
Landing
  ↓
ready
  ↓
processing
  ↓
Validation
  ↓
done
  ↘ error
```

완료 Signal 이전 소비 금지.

필수:

```text
File ID
Filename Rule
Encoding
Size
Hash
Record Count
Completion Signal
Retry / Reprocess
Archive
Owner
```

---

# 12. Batch Runtime (#12)

```text
Control-M
  ↓
Agent
  ↓
Shell
  ↓
JobLauncher
  ↓
Job
  ↓
Step
  ↓
JobRepository + Business DB
  ↓
Exit Code / Monitoring
```

금지:

```text
온라인 WAS에 장기 Batch
App 내부 Scheduler만으로 운영
Restart 불가능 Job
중복 적재 방지 없는 재실행
초대형 단일 TX
```

---

# 13. PDMG Online Runtime AS-IS Reference

현재 분석된 PDMG TCF ON 경로:

```text
HTTP Request
  ↓
DefaultFilter
  ├─ Body Cache
  ├─ Header / GUID
  ├─ JWT
  ├─ ServiceContext
  └─ MDC
  ↓
Spring SecurityFilterChain
  ↓
DispatcherServlet
  ↓
ServicePreventionInterceptor.preHandle
  ↓
OnlineTransactionController
  ↓
TcfFacade
  ↓
OnlineTimeoutExecutor
  ↓
TransactionDispatcher
  ↓
TransactionHandler
  ↓
Business Facade
  ↓
BizPrePostAspect
  ↓
Service
  ↓
DAO
  ↓
Mapper XML / JDBC
  ↓
DB
  ↓
Response / Exception
  ↓
ResponseBodyArgumentResolver
  ↓
Interceptor.afterCompletion
  ↓
DefaultFilter.finally / Context Clear
  ↓
HTTP Response
```

이 흐름은 PDMG AS-IS Reference이며 NSIGHT 모든 Runtime의 단일 표준이라고 단정하지 않는다.

---

# 14. TCF ON / OFF Runtime

TCF ON:

```text
Common Controller
  ↓
TcfFacade
  ↓
Dispatcher
  ↓
Handler
  ↓
Business
```

TCF OFF:

```text
Business MVC Controller
  ↓
Business Service / Facade
```

현재 AS-IS 일부 OFF 경로가 Facade를 우회할 수 있으므로:

```text
[GAP / RISK]
ON/OFF Transaction Boundary 및 Use Case Boundary 정합성 검증
```

이 필요하다.

---

# 15. Request Thread vs Worker Thread

Timeout 기능 활성 시:

```text
════════ Request Thread ════════
Filter / MVC / Controller
        ↓
OnlineTimeoutExecutor
        ↓ submit
        ├──────────── Future.get(timeout)
        │
        ▼
════════ Worker Thread ═════════
Context Install
        ↓
DB Transaction
        ↓
Dispatcher / Handler / Business / DAO
        ↓
Commit / Rollback
        ↓
Context Clear
```

핵심:

```text
HTTP Request Lifecycle
≠
DB Transaction Lifecycle
```

---

# 16. Current PDMG Timeout Snapshot

현재 분석 Snapshot에서 확인된 값:

```text
timeout.enabled = true
milliseconds    = 5000
pool-size       = 20
queue-capacity  = 100
```

태그:

```text
[AS-IS SNAPSHOT]
```

다음으로 자동 승격 금지:

```text
NSIGHT Target SLA
Tomcat maxThreads
Hikari Pool
Gateway Timeout
DB Query Timeout
전사 운영 표준
```

---

# 17. DB Transaction Boundary

현재 TCF ON + Timeout ON Reference:

```text
Worker Thread
  ↓
TransactionTemplate BEGIN
  ↓
TransactionDispatcher
  ↓
TransactionHandler
  ↓
Business Facade @Transactional(REQUIRED)
  ↓
Biz Pre
  ↓
Service
  ↓
DAO / Mapper / SQL
  ↓
Biz Post
  ↓
Deadline Check
  ├─ OK       → COMMIT
  └─ EXCEEDED → rollbackOnly → ROLLBACK
```

핵심:

- Handler도 외부 Transaction 범위 안에 들어갈 수 있다.
- Handler가 SQL을 직접 실행하는 것이 정상 책임이라는 뜻은 아니다.
- Facade `REQUIRED`는 이미 외부 TX가 있으면 같은 TX에 참여한다.

---

# 18. Timeout 의미 분리

다음 네 문장은 같은 뜻이 아니다.

```text
1. HTTP Timeout 발생
2. Worker Thread 종료
3. JDBC Statement 취소
4. DB Transaction Rollback
```

`Future.cancel(true)`:

```text
= Interrupt 요청
≠ DB 강제 Kill 보장
```

따라서 Runtime Validation에서 각각 별도 Evidence가 필요하다.

---

# 19. Late Commit Prevention

AS-IS Reference의 중요한 보호:

```text
SQL/업무 실행 완료
   ↓
Deadline Check
   ├─ 제한 내 → Commit
   └─ 제한 초과 → rollbackOnly → Rollback
```

HTTP 응답이 이미 Timeout 되었더라도 늦은 Commit을 방지하는 계약을 검증한다.

---

# 20. Normal / Error / Overload Runtime

## 20.1 Normal

```text
Request
  ↓
Business
  ↓
Commit
  ↓
Standard Response
  ↓
Trace Completion
```

## 20.2 Business Error

```text
Business Exception
  ↓
Rollback
  ↓
Error Mapping
  ↓
Standard Error Response
```

예외를 잡아 정상값으로 반환하여 Rollback 계약을 깨지 않도록 한다.

## 20.3 Timeout

```text
Future.get Timeout
  ↓
HTTP 504 Candidate
  ↓
Cancel/Interrupt Request
  ↓
Worker / JDBC 종료 별도 관찰
  ↓
Rollback / Late Commit Prevention 확인
```

## 20.4 Overload

```text
Worker Pool Full
  +
Queue Full
  ↓
Reject / 503 Candidate
```

Timeout과 Overload를 같은 오류율 하나로 합치지 않는다.

---

# 21. Security Runtime

Runtime에서 관측할 Security Event:

```text
Authentication Failure
Authorization Failure
JWT Expired
JWT Signature / Key Failure
JWKS / Key Retrieval Failure
Session Expiry
Suspicious Repeated Request
```

Security Mechanism이 존재한다는 것과 운영 Metric/Alert가 구현되어 있다는 것은 별개다.

---

# 22. 11.1 시스템 모니터링

[PPT-CONTENT-GAP]을 숨기지 않고 기존 Ops 정의서로 보강한다.

관측 레이어:

```text
Channel / Interface
Application
Tomcat / JVM
PDMG Worker / Queue
Hikari / DB Session
SQL
Event / CDC / ETL
File / Batch
Security
Infrastructure
```

핵심 상관키:

```text
GUID
ServiceId
Interface ID
Job / Event / File ID
Host / JVM
```

---

# 23. Runtime Observability Chain

```text
GUID + ServiceId
      ↓
Application Log
      ↓
Thread / Transaction
      ↓
SQL / DB
      ↓
Interface / Event / File
      ↓
Metric / APM
      ↓
Alert
      ↓
Runbook / Recovery
      ↓
Runtime Evidence
```

운영 화면은 “CPU 높음”에서 끝나지 않고:

```text
어느 ServiceId
어느 JVM
어느 Worker
어느 DB Pool
어느 SQL
때문에 느린가?
```

를 추적할 수 있어야 한다.

---

# 24. 11.2 시스템 가용성

가용성은 문구가 아니라 Failure Scenario로 검증한다.

```text
AP VM Failure
AP Group Failure
RDW Failure
ADW Failure
Event/Kafka Failure
CDC Relay Failure
Integration Failure
Center Failure
```

기존 Ops 정의서의 8개 시나리오 Baseline:

```text
#1 AP VM
#2 AP Group
#3 RDW
#4 ADW
#5 Kafka/Event
#6 CDC Relay
#7 Integration
#8 Center DR
```

각 시나리오 필수:

```text
Detect
Traffic Switch
Data Consistency
Service Continuity
Recovery
Approval
Evidence
```

---

# 25. 11.3 시스템 확장성

확장 축:

```text
WEB / AP Instance
JVM
Worker Pool
DB Connection
DB Node / Resource
Event Consumer
ETL Parallelism
Storage / I/O
```

Capacity 문서의 숫자는 Candidate/Baseline 시점을 명시한다.

Runtime Metric과 설계값 차이는 Drift로 관리한다.

---

# 26. 11.4 DR 구성 — 센터 간 가용성

Physical:

```text
의왕
  ↓
Replication / Deployment / Mapping
  ↓
안성
```

Runtime:

```text
Detect
  ↓
Decision / Approval
  ↓
Traffic / DNS / GSLB Switch
  ↓
Application Start / Verify
  ↓
DB/Data Integrity Check
  ↓
Batch / Interface / File Recovery
  ↓
Business Validation
  ↓
Evidence
```

핵심:

```text
DR Host 존재
≠
DR 완료
```

RTO/RPO가 미확정이면 `[OPEN]`.

---

# 27. 11.5 백업 구성

Backup의 완료조건:

```text
Backup Success
   ≠
Recovery Proven
```

필수:

```text
Target
Backup Type
Frequency
Retention
Encryption
Offsite/DR
Restore Procedure
Restore Test
RTO/RPO Link
Owner
Evidence
```

DB/파일/Config/Artifact/Key는 동일 정책으로 뭉개지 않는다.

---

# 28. Alert → Diagnosis → Recovery

```text
Alert
  ↓
Correlation
  ↓
Diagnosis
  ↓
Impact Analysis
  ↓
Runbook
  ↓
Recovery
  ↓
Validation
  ↓
Evidence
```

예시 원인 연쇄:

```text
Slow DB
  ↓
Hikari Pending
  ↓
Worker Queue
  ↓
Timeout
  ↓
HTTP 504
```

단순 504 건수만 보는 것은 원인분석이 아니다.

---

# 29. Runtime Evidence Package

최소 연결:

```text
Architecture Baseline ID
  ↓
Source Commit
  ↓
Build / Artifact
  ↓
Deployment ID
  ↓
Host / JVM
  ↓
ServiceId / Runtime Type
  ↓
GUID / Trace ID
  ↓
Log / Metric / SQL / Error
  ↓
Validation Result
```

이 Chain은 07 Appendix/Closed Loop로 Handoff한다.

---

# 30. NFR Validation

VISION의 5축을 Runtime에서 증명한다.

| NFR | Runtime Evidence |
|---|---|
| Performance | p95/p99, Thread, Queue, SQL, Event Lag |
| Availability | Failure Test, Failover, Recovery |
| Scalability | Load Test, Scale-out Result |
| Security | Auth/JWT/Permission Failure Evidence |
| Observability | GUID/ServiceId E2E Trace |

수치는 근거·시점·측정구간과 함께 기록한다.

---

# 31. RUNTIME 원칙

| ID | 원칙 |
|---|---|
| RT-01 | 모든 주요 흐름은 Runtime Type을 가진다 |
| RT-02 | 주/보조 Runtime을 구분한다 |
| RT-03 | Sequence에 Owner와 SLO를 둔다 |
| RT-04 | HTTP/TX/Worker/JDBC Timeout을 구분한다 |
| RT-05 | Retry는 Runtime Integrity를 보장해야 한다 |
| RT-06 | Event/CDC/ETL/File/Batch는 Replay/Restart를 정의한다 |
| RT-07 | Failure Scenario는 Evidence로 검증한다 |
| RT-08 | DR은 Host가 아니라 실행절차까지 포함한다 |
| RT-09 | Backup은 Restore Test까지 포함한다 |
| RT-10 | Runtime Evidence를 Baseline으로 환류한다 |

---

# 32. GAP / RISK / OPEN / ADR

| 항목 | 상태 | 조치 |
|---|---|---|
| PPT 7.2 상세 | `[PPT-CONTENT-GAP]` | 8/9장 + PDMG로 보강 |
| PPT 7.3 독립 상세 | Cross Reference | 8장 경로와 정합 |
| PPT 7.4 UMS 상세 | `[PPT-CONTENT-GAP]` | Event/UMS 설계 보강 |
| PPT 11장 상세 | `[PPT-CONTENT-GAP]` | Ops Baseline 사용 |
| PDMG Timeout 5000/20/100 | `[AS-IS SNAPSHOT]` | Target NFR 자동승격 금지 |
| JDBC Cancel 보장 | `[RISK/OPEN]` | Driver/Query Timeout 검증 |
| PDMG TCF OFF TX 경계 | `[GAP]` 가능 | ON/OFF 정합 검증 |
| RTO/RPO | `[OPEN]` | DR ADR/SLA |
| CDC Lag SLA | `[OPEN]` | Data Runtime ADR |
| OM Dashboard 구현상태 | `[UNKNOWN/GAP]` 가능 | Source/Runtime Evidence 확인 |

---

# 33. Verification Checklist

```text
[ ] 7장과 11장 PPT 공식 목차를 추적하는가
[ ] 6대 Runtime / 12개 소유형이 정의되는가
[ ] 각 Runtime에 Actor/Entry/IF/Data/Owner/SLO가 있는가
[ ] PDMG Online Runtime을 AS-IS Reference로만 사용했는가
[ ] Request Thread와 Worker Thread를 분리했는가
[ ] DB Transaction Boundary를 명확히 했는가
[ ] HTTP Timeout/Worker/JDBC/TX 종료를 구분했는가
[ ] Timeout/Overload/Error를 구분했는가
[ ] Event/CDC/ETL/File/Batch에 재처리 계약이 있는가
[ ] Monitoring이 GUID/ServiceId와 연결되는가
[ ] HA/DR에 Failure Scenario와 Evidence가 있는가
[ ] Backup이 Restore Test까지 포함하는가
[ ] 근거 없는 SLO/RTO/RPO를 생성하지 않았는가
```

---

# 34. Appendix / Closed Loop Handoff

RUNTIME Output:

```text
Runtime Type
Actual Sequence
Thread / Transaction / Resource
Timeout / Error / Retry
Metric / Log / Trace
HA / DR / Recovery
Runtime Evidence
```

Appendix에서 닫을 것:

```text
Requirement
  ↓
Architecture Principle
  ↓
PPT / Definition
  ↓
Inventory / Model
  ↓
Source / Config
  ↓
Test
  ↓
Deployment
  ↓
Runtime Evidence
  ↓
Drift
  ↓
GAP / ADR
  ↓
New Baseline
```
