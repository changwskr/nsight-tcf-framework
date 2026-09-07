# NSIGHT / PDMG 아키텍처 정의서
# 06. RUNTIME — 런타임 / 운영 / 가용성 / DR / Evidence 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-TYPE2-RUNTIME-06-DETAIL`  
> Architecture Level: **RUNTIME**  
> PPT 공식 범위:  
> **7. 런타임 아키텍처**  
> **11. 기타 — 시스템 모니터링 / 가용성 / 확장성 / DR / 백업**  
> 문서 상태: **Draft / Evidence-First / PPT-Aligned / Detailed Baseline**  
> 작성 기준일: **2026-08-31**  
> 선행 문서: `05. MECHANISM — 인터페이스 / 표준화 / Framework / Solution 상세본`  
> 후속 문서: `07. Evidence / GAP / ADR / Traceability / Architecture Closed Loop`  
> 핵심 질문: **설계된 Mechanism이 실제 시간순으로 어떻게 실행되고, 실패·지연·과부하·복구 상황에서 어떤 Runtime Evidence를 남기며, 어떤 Gate를 통과해야 운영 가능한 Architecture가 되는가?**

---

# 0. 문서 사용법

RUNTIME은 “무슨 기술을 쓰는가”를 설명하는 장이 아니다.

이 장은 다음을 증명한다.

```text
Business Request / Event / Data / File / Job
        ↓
Runtime Type
        ↓
Actor / Entry / Interface
        ↓
Sequence
        ↓
Thread / Transaction / Resource Pool
        ↓
Success / Error / Timeout / Retry / Compensation
        ↓
Metric / Log / Trace
        ↓
Recovery / HA / DR / Restore
        ↓
Runtime Evidence
        ↓
Gate
```

즉:

```text
MECHANISM
= 어떻게 동작하도록 설계했는가

RUNTIME
= 실제로 어떻게 실행되고, 실패하고, 복구되는가
```

---

# 0.1 Evidence 태그

| 태그 | 의미 |
|---|---|
| `[PPT-TOC]` | PPT 공식 목차 |
| `[PPT-BODY]` | PPT 본문 직접 확인 |
| `[PPT-CONTENT-GAP]` | 목차는 있으나 독립 상세 부족 |
| `[FACT]` | 공식 정의/소스/설정에서 확인 |
| `[WORKING BASELINE]` | 프로젝트 반복 기준 |
| `[AS-IS EVIDENCE]` | PDMG 현재 Runtime 근거 |
| `[AS-IS SNAPSHOT]` | 특정 시점 현재 설정값 |
| `[SUPPLEMENTAL-DEFINITION]` | PPT Gap을 다른 정의서로 보강 |
| `[CONFLICT]` | 수치/정책 충돌 |
| `[GAP]` | 목표/구현/자료 간 차이 |
| `[RISK]` | 장애/성능/보안 위험 |
| `[OPEN]` | 결정 필요 |
| `[ADR]` | Architecture Decision 필요 |
| `[RUNTIME EVIDENCE]` | 실행·측정·Drill로 확보된 증거 |

---

# 0.2 Evidence Register

| ID | 근거 | 사용 목적 | 상태 |
|---|---|---|---|
| EV-RT-01 | `07_런타임아키텍처_정의서.md` | 대유형 6·소유형 12·주/보조·SLO·Gate | `[FACT/WORKING BASELINE]` |
| EV-RT-02 | TYPE2/PPT 정합 마스터 프롬프트 | 7장+11장 RUNTIME Route | `[WORKING BASELINE]` |
| EV-RT-03 | `IV_PDMG_Online_Runtime_TCF_Flow` | HTTP→Filter→MVC→TCF→Business→DB AS-IS | `[AS-IS EVIDENCE]` |
| EV-RT-04 | `V_Transaction_Timeout_Thread_DB_Architecture` | Request/Worker/TX/Timeout/Cancel/Deadline | `[AS-IS EVIDENCE]` |
| EV-RT-05 | `11_모니터링가용성DR백업_정의서.md` | NFR/HA/DR/8시나리오/Backup/RACI/Gate | `[SUPPLEMENTAL-DEFINITION]` |
| EV-RT-06 | `IX_DevOps_OM_Observability` | GUID/ServiceId/Thread/Pool/DB/Runtime Evidence | `[WORKING BASELINE]` |
| EV-RT-07 | `05 MECHANISM 상세본` | Interface/ServiceId/GUID/Framework/Batch Contract | `[CURRENT BASELINE DRAFT]` |
| EV-RT-08 | `04 PHYSICAL 상세본` | Host/JVM/DB/HA/DR Physical Mapping | `[CURRENT BASELINE DRAFT]` |

---

# 1. PPT 공식 구조

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

---

# 1.1 PPT Content 상태

| 절 | 상태 | 처리 |
|---|---|---|
| 7.1 업무 처리 유형 | `[PPT-BODY]` | 6대/12개 유형 상세 |
| 7.2 단말 거래 처리 | `[PPT-CONTENT-GAP]` 가능 | 8/9장 + PDMG Runtime Cross Reference |
| 7.3 미니 싱글뷰 | `[PPT-TOC]` + Cross Reference | #3 Runtime으로 정리 |
| 7.4 UMS 고객 통지 | `[PPT-CONTENT-GAP]` 가능 | #7 Runtime으로 보강 |
| 11.1~11.5 | `[PPT-CONTENT-GAP]` | 운영 정의서로 보강 |

11장 보강 문구:

```text
PPT 11장 본문 미비.
Baseline =
VISION NFR/SLA
+
PHYSICAL HA-DR/용량
+
RUNTIME NFR-VALIDATION
```

---

# 2. 핵심 결론

RUNTIME Architecture의 핵심은 다음과 같다.

> **모든 업무·연계·이벤트·데이터·파일·배치는 반드시 Runtime Type #1~#12 중 하나 이상의 유형으로 분류되고, 주/보조 유형·실행 Sequence·Owner·SLO·재처리·모니터링·Evidence를 가져야 한다. 유형 번호나 Runtime Evidence가 없는 신규 흐름은 운영 개통 대상이 아니다.**

또한 PDMG AS-IS Online Runtime에서 반드시 구분해야 하는 것은 다음이다.

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

이다.

---

# 3. MECHANISM Handoff 수신

MECHANISM Output:

```text
Interface Type
Interface ID
ServiceId
Standard Entry
Message
GUID
Charset
Transaction 8 Steps
Framework
Retry/Idempotency
File Contract
Batch Contract
Solution Header
```

RUNTIME에서는:

```text
실제 Sequence
실제 Thread
실제 TX
실제 Timeout
실제 Error
실제 Metric
실제 Recovery
```

로 증명한다.

---

# 4. Runtime Architecture 전체 구조

```text
┌────────────────────── Runtime Entry ──────────────────────┐
│ Request / Event / Change / File / Schedule               │
└─────────────────────────────┬─────────────────────────────┘
                              ▼
┌────────────────────── Runtime Type # ─────────────────────┐
│ Channel / Integration / Event / Data / File / Batch      │
└─────────────────────────────┬─────────────────────────────┘
                              ▼
┌────────────────────── Execution Sequence ─────────────────┐
│ Entry → Framework → Business → Data/External             │
└─────────────────────────────┬─────────────────────────────┘
                              ▼
┌──────────────────── Resource / Control ───────────────────┐
│ Thread / Worker / TX / Pool / Queue / DB / Lag           │
└─────────────────────────────┬─────────────────────────────┘
                              ▼
┌──────────────────── Outcome ──────────────────────────────┐
│ Success / Error / Timeout / Overload / Retry             │
└─────────────────────────────┬─────────────────────────────┘
                              ▼
┌──────────────────── Operations ───────────────────────────┐
│ Metric / Log / Trace / Alert / Runbook / Recovery        │
└─────────────────────────────┬─────────────────────────────┘
                              ▼
┌──────────────────── Runtime Evidence ─────────────────────┐
│ Test / Drill / Trace / Validation / Gate                 │
└───────────────────────────────────────────────────────────┘
```

---

# 5. 7.1 업무 처리 유형

런타임 대유형 6:

```text
1. 채널
2. 연계
3. 마케팅 이벤트
4. 데이터 분석/제공
5. 파일
6. 배치
```

소유형:

```text
#1 ~ #12
```

고정.

---

# 6. Runtime Type 정의표

| # | 대유형 | 소유형 | 대표 IF | SLO 감각 |
|---:|---|---|---|---|
| 1 | 채널 | 정보계 단말 거래 | Direct | 일반 Online P95 ~3초 |
| 2 | 채널 | 통합업무시스템 거래 | MCA | 동기 P95 |
| 3 | 채널 | 미니 싱글뷰 | MCA→App | 동기 P95 |
| 4 | 연계 | 대내 시스템간 | Cruz APIM | 연동 SLA |
| 5 | 연계 | 대외 시스템간 | APIM/GSE/대외MCA | 외부 SLA/대사 |
| 6 | 마케팅 이벤트 | 반응형 정보 수집 | Kafka 등 | 수집지연/유실 |
| 7 | 마케팅 이벤트 | 고객 오퍼링 | EBM/UMS | FAST ~1초 |
| 8 | 데이터 | CDC 동기화 | CDC | 지연 SLA `[ADR]` |
| 9 | 데이터 | ETL 연계 | ETL | 배치창/완료시각 |
| 10 | 데이터 | 분석·의사결정 지원 | JDBC/BI | 조회/Report |
| 11 | 파일 | FOS 파일 처리 | FOS | 전송/완료신호 |
| 12 | 배치 | 배치 처리 | Control-M | 예: 일배치 06시 前 |

수치는 자료의 Baseline 감각이며 실제 측정구간/Owner와 함께 확정한다.

---

# 7. 주 유형 / 보조 유형

한 업무가 복수 Runtime에 해당할 수 있다.

예:

```text
Campaign Offering
Primary   = #7
Secondary = #6 / #8 / #10
```

원칙:

```text
한 시퀀스에 전부 섞지 않는다.
```

반드시:

```text
Primary Sequence
Secondary Sequence
Correlation
```

으로 분리한다.

---

# 8. Runtime Inventory 필수 필드

모든 Runtime은 다음 필드를 가진다.

```text
Runtime Type #
Name
Purpose
Actor
Entry
Interface
ServiceId / Event / Job / File ID
Application/System
Data
Primary/Secondary
SYNC/ASYNC
Sequence
Thread / Resource Pool
Transaction
SLO / Timeout
Retry / Compensation
Monitoring
Owner
Evidence
Status
GAP / ADR
```

---

# 9. 유형 없는 신설 금지

금지:

```text
"기타 Runtime"
"임시 Runtime"
유형번호 없음
```

어느 #에도 맞지 않으면:

```text
GAP
→ 영향분석
→ 유형체계 개정 ADR
```

---

# 10. 채널 Runtime #1~#3

```text
Channel
├─ #1 정보계 단말
├─ #2 통합업무
└─ #3 미니 싱글뷰
```

공통 특성:

```text
SYNC
사용자 체감응답
ServiceId
GUID
Standard Error
P95
```

---

# 11. #1 정보계 단말 거래

```text
Information Terminal
   ↓ Direct
/ins/{ServiceId}
   ↓
Application Framework
   ↓
Business
   ↓
DB / Interface
   ↓
Response
```

주요 Runtime Key:

```text
ServiceId
GUID
User
Terminal
```

---

# 12. #1 정상 Sequence

```text
1. User action
2. Terminal Request
3. Standard Entry
4. Framework Pre
5. Business
6. DB/IF
7. Commit/Result
8. Standard Response
9. UI Render
```

---

# 13. #1 지연 Sequence

```text
Slow DB / IF
      ↓
Thread/Worker 점유
      ↓
P95 증가
      ↓
Timeout 후보
```

관측:

```text
ServiceId
GUID
DB Time
External Time
Thread/Worker Wait
```

---

# 14. #2 통합업무 거래

```text
Integrated Work
   ↓
MCA
   ↓
Standard Message Conversion
   ↓
Information Application
   ↓
Business / Data
   ↓
MCA
   ↓
Integrated Work
```

MCA 책임:

```text
FLAT↔JSON
Error Mapping
Channel Context
```

Business Rule은 Application 책임.

---

# 15. #2 Failure

분리:

```text
MCA Failure
Application Failure
DB Failure
Business Reject
```

한 Error Code 그룹으로만 처리하지 않는다.

---

# 16. #3 미니 싱글뷰

```text
Integrated Work / Channel
        ↓
       MCA
        ↓
Mini Single View
        ↓
RDW / Service
        ↓
Response
```

PPT 7.3 상세 부족 시 8장 경로·PDMG Reference를 Cross Reference한다.

---

# 17. #3 Runtime 주의

미니 싱글뷰는:

```text
C/S Context
Web Context
Application Context
```

가 혼재할 수 있으므로:

```text
GUID
User
ServiceId
Session/Auth Context
```

의 연결을 검증한다.

---

# 18. 연계 Runtime #4~#5

```text
#4 Internal
#5 External
```

---

# 19. #4 대내 연계

```text
NSIGHT App
   ↓
Cruz APIM / Logical API
   ↓
Internal System
   ↓
Response
```

must:

```text
Interface ID
Logical API
Timeout
Idempotency
Auth
Error Mapping
GUID
Owner
```

---

# 20. #4 금지

```text
Physical URL hardcoding
Remote Call을 Local TX처럼 가정
무제한 Retry
```

---

# 21. #5 대외 연계

```text
NSIGHT
  ↓
API G/W / GSE / External MCA
  ↓
DMZ / External Boundary
  ↓
External Institution
```

추가 Runtime 계약:

```text
Certificate
Encryption
External Timeout
Reconciliation
RTO
Replay/Manual Recovery
```

---

# 22. #5 Failure 분류

```text
Network
Certificate
Gateway
External Reject
External Timeout
Reconciliation Mismatch
```

각기 다른 Recovery가 필요하다.

---

# 23. 마케팅 이벤트 Runtime #6~#7

```text
#6 Collection
#7 Offering
```

두 흐름은 분리한다.

---

# 24. #6 반응형 정보 수집

Baseline:

```text
Customer Behavior
   ↓
Wise Collector / Collector
   ↓
Kafka / Event Broker
   ↓
Behavior Processing
   ↓
Store / Trigger
```

---

# 25. #6 Runtime 지표

```text
Producer Rate
Broker In Rate
Partition/Queue Lag
Consumer Lag
Processing Error
Dropped Event
Replay Count
```

---

# 26. #6 금지

```text
Online WAS Thread에서 대량 Event 처리
Event Broker 없이 동기 Fan-out
Consumer Lag 미관측
Replay 정책 없음
```

---

# 27. #7 고객 오퍼링

```text
Customer Context / Trigger
       ↓
EBM / Decision
       ↓
Offer
       ↓
UMS
       ├─ SMS
       ├─ PUSH
       └─ MAIL
```

FAST 감각:

```text
~1초
```

실제 측정구간은 별도 확정.

---

# 28. #7 vs #6

```text
#6
수집 / 적재 / Trigger 생성

#7
판단 / Offer / 통지
```

Owner/SLO를 혼합하지 않는다.

---

# 29. #7 Failure

```text
Decision Failure
UMS Failure
Channel Delivery Failure
Duplicate Offer
Late Offer
```

Compensation:

```text
Retry
Fallback
Manual Re-send
Suppression
```

정책을 명시한다.

---

# 30. 데이터 Runtime #8~#10

```text
#8 CDC
#9 ETL
#10 Analysis / Decision Support
```

---

# 31. #8 CDC

```text
Core Commit
   ↓
Capture
   ↓
Trail / Relay / Downstream
   ↓
Apply
   ↓
RDW Visible
```

---

# 32. #8 관측 지점

```text
Source Commit Time
Capture Time
Relay Queue
Apply Time
Query Visible Time
Checkpoint
Backlog
Error
```

CDC SLA는 어느 구간인지 정의한다.

---

# 33. CDC SLA Conflict

자료:

```text
3초
vs
30초
```

따라서:

```text
[CONFLICT]
[ADR REQUIRED]
```

최종 수치 임의 확정 금지.

---

# 34. #8 Recovery

```text
Checkpoint 확인
  ↓
Trail/Queue 검증
  ↓
Apply 재기동
  ↓
Gap/Reconciliation
  ↓
Catch-up
  ↓
Lag 정상화
```

---

# 35. #8 금지

```text
CDC Delay를 ETL SLA로 대체
Checkpoint 없이 재기동
대량 Full Load를 CDC 책임으로 처리
```

---

# 36. #9 ETL

```text
Source / BCV / RDW
   ↓
Extract
   ↓
Stage
   ↓
Transform
   ↓
Validate
   ↓
Load
   ↓
Reconcile
   ↓
RDW / ADW / External
```

---

# 37. #9 필수 Evidence

```text
Batch Window
Start/End
Input Count
Output Count
Reject Count
Hash / Reconciliation
Restart Point
Owner
Completion Time
```

---

# 38. #9 Failure

```text
Extract Failure
Transform Failure
DB Load Failure
Data Quality Failure
Window Overrun
Reconciliation Mismatch
```

---

# 39. #9 Restart

```text
Checkpoint
Chunk
Idempotency
Reprocess
```

없는 ETL은 운영 승인하지 않는다.

---

# 40. #10 분석·의사결정 지원

```text
BI User / Analyst / App
     ↓
BI / JDBC / Analytics
     ↓
RDW / ADW
     ↓
Result / Report
```

---

# 41. #10 Resource Isolation

금지:

```text
Bulk BI
→ RDW Operational Workload 무제한 점유
```

필요:

```text
Query Class
Resource Group
ADW Use
Timeout
Concurrency
```

---

# 42. File Runtime #11

```text
Sender
  ↓
FOS
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

---

# 43. #11 필수 항목

```text
File ID
Interface ID
Filename
Encoding
Size
Hash
Record Count
Completion Signal
Archive
Retry
Reprocess
Owner
SLA
```

---

# 44. #11 핵심 규범

must:

```text
Completion Signal 전 소비 금지
```

---

# 45. #11 Failure

```text
Transfer Interrupted
Hash Mismatch
Layout Error
Encoding Error
Duplicate File
Record Count Mismatch
Consumer Failure
```

---

# 46. #11 Recovery

```text
Reject
→ Correct / Re-transfer
→ Validate
→ Completion
→ Consume
→ Reconcile
```

---

# 47. Batch Runtime #12

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
JobRepository
  +
Business DB
  ↓
Exit Code
```

---

# 48. #12 Runtime Key

```text
Schedule ID
Job ID
JobInstance
Step
Business Date
Exit Code
```

---

# 49. #12 완료

```text
Job Exit
≠ Business Complete
```

필요:

```text
Output Count
Reconciliation
Downstream Signal
```

---

# 50. #12 금지

```text
Online WAS 장기 Batch
App Cron만 운영 Scheduler
Restart 불가
Duplicate Load
One Huge Transaction
System Date 하드코딩
JobRepository=Business Ledger
```

---

# 51. Runtime Type 설계서 Template

```text
Runtime #:
Name:
Primary / Secondary:
Purpose:
Actor:
Entry:
Interface:
ServiceId/Event/Job/File ID:
Sequence:
Thread / Resource:
Transaction:
SLO:
Timeout:
Retry:
Compensation:
Monitoring:
Owner:
Evidence:
Gap/ADR:
```

---

# 52. PDMG Online Runtime — AS-IS Reference

현재 TCF ON + Timeout ON 기준:

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
DefaultFilter.finally
  ↓
HTTP Response
```

이 흐름은 **PDMG AS-IS Reference**다.

---

# 53. PDMG Current Snapshot

```text
tcf.enabled     = true
timeout.enabled = true
timeout         = 5000ms
worker pool     = 20
queue capacity  = 100
```

태그:

```text
[AS-IS SNAPSHOT]
```

다음으로 승격 금지:

```text
NSIGHT Target SLA
Tomcat maxThreads
Hikari Pool
Gateway Timeout
DB Query Timeout
```

---

# 54. PDMG HTTP 생명주기

```text
Request Thread
DefaultFilter
  ↓
Security
  ↓
MVC
  ↓
Interceptor
  ↓
Controller
  ↓
TcfFacade
  ↓
Future.get
  ↓
Response
  ↓
afterCompletion
  ↓
Filter finally
```

---

# 55. PDMG Worker 생명주기

```text
Worker Thread
Context Install
  ↓
TransactionTemplate
  ↓
Dispatcher
  ↓
Handler
  ↓
Facade
  ↓
Service
  ↓
DAO/SQL
  ↓
Deadline
  ↓
Commit/Rollback
  ↓
Context Clear
```

---

# 56. Request Thread vs Worker Thread

```text
════════ Request Thread ════════
Controller
  ↓
OnlineTimeoutExecutor
  ↓
submit
  ↓
Future.get(timeout)

════════ Worker Thread ═════════
Context Install
  ↓
Business Execution
  ↓
DB Transaction
```

---

# 57. ThreadLocal 전파

Request Thread의:

```text
ServiceContext
MDC
GUID
ServiceId
User
```

는 Worker에 자동 전달되지 않는다.

Framework가:

```text
Capture
→ Install
→ Clear
```

해야 한다.

---

# 58. Context Clear must

Worker 재사용 시:

```text
Transaction A Context
-X→ Transaction B
```

가 되어야 한다.

Context/MDC 누수:

```text
Critical Risk
```

---

# 59. Mutable Context Risk

현재 PDMG 분석상 Worker에 동일 mutable `ServiceContext` 참조가 전달될 수 있다.

Timeout 이후:

```text
Request Thread
→ Response/Context 변경

Worker Thread
→ 동일 Context 사용
```

경합 가능.

태그:

```text
[RISK]
```

TO-BE 후보:

```text
Immutable Worker Snapshot
```

---

# 60. DB Transaction Boundary

TCF ON + Timeout ON:

```text
TransactionTemplate BEGIN
│
├─ TransactionDispatcher
├─ TransactionHandler
├─ Business Facade
├─ BizPre
├─ Service
├─ DAO / SQL
├─ BizPost
├─ Deadline Check
└─ COMMIT / ROLLBACK
```

즉:

```text
Handler도 TX 안
```

이다.

---

# 61. Handler가 TX 안이라는 의미

의미:

```text
Transaction Boundary가 Dispatcher부터 감쌈
```

아님:

```text
Handler가 SQL 책임
```

Handler 직접 DAO 호출은 정상 패턴이 아니다.

---

# 62. Facade @Transactional

```text
Outer TransactionTemplate
   ↓
Facade @Transactional(REQUIRED)
```

이미 같은 TransactionManager의 TX가 있으면:

```text
동일 TX 참여
```

한다.

---

# 63. Transaction Manager 정합

필수:

```text
TransactionTemplate TM
=
Facade @Transactional TM
=
DAO Datasource / SqlSession
```

다르면 다중 TX/예상치 못한 경계 가능.

---

# 64. readOnly 주의

Facade:

```text
@Transactional(readOnly=true)
```

가 있어도 외부 TransactionTemplate이 먼저 TX를 시작했다면:

```text
내부 readOnly 선언이
외부 TX 속성을 재정의한다고
단정 금지
```

`[GAP]` 검증 필요.

---

# 65. Exception과 Rollback

정상:

```text
DAO Exception
↑
Service
↑
Facade
↑
TransactionTemplate
→ ROLLBACK
```

금지:

```text
catch(Exception)
→ log
→ 정상 Output return
```

Rollback 계약을 깨뜨릴 수 있다.

---

# 66. Timeout 4개 시점 구분

절대 동일시하지 않는다.

```text
1. HTTP Timeout
2. Worker Thread 종료
3. JDBC Statement 취소
4. DB Transaction Rollback
```

---

# 67. Request Timeout

현재 PDMG:

```text
Future.get(5000ms)
```

만료 시 Request Thread는 Timeout 처리로 진행한다.

---

# 68. cancel(true)

```text
Future.cancel(true)
=
Interrupt 요청
```

아니다:

```text
DB Session Kill
```

---

# 69. JDBC 취소 한계

JDBC Driver/Statement가 Interrupt를 어떻게 처리하는지는 별도다.

따라서:

```text
HTTP 504
```

발생 시에도:

```text
SQL이 잠시 더 실행
```

될 수 있다.

---

# 70. Deadline Late Commit Prevention

Current AS-IS Reference:

```text
Business/SQL 반환
  ↓
Deadline Check
  ├─ within → Commit
  └─ exceeded → rollbackOnly → Rollback
```

중요:

```text
SQL 성공
≠ Commit
```

---

# 71. 정상 Commit Sequence

```text
Request
→ Worker
→ TX BEGIN
→ Business
→ SQL
→ Biz Post
→ Deadline OK
→ COMMIT
→ Response
```

---

# 72. Business Error Rollback

```text
Business Exception
→ Propagate
→ TX Rollback
→ Error Mapping
→ Response
```

---

# 73. Timeout Sequence

```text
Request Thread
Future.get
  ↓
5000ms
  ↓
Timeout
  ↓
cancel(true)
  ↓
504 Candidate

Worker
  ↓
Interrupt 인지 여부
  ↓
SQL 반환/취소
  ↓
Deadline
  ↓
Rollback
```

---

# 74. Overload Sequence

```text
Worker 20 Busy
+
Queue 100 Full
  ↓
Task Reject
  ↓
Overload Exception
  ↓
503 Candidate
```

---

# 75. Timeout vs Overload

| 항목 | Timeout | Overload |
|---|---|---|
| HTTP 후보 | 504 | 503 |
| Worker 실행 | 실행 중일 수 있음 | 시작 안 했을 수 있음 |
| TX | 시작했을 수 있음 | 미시작 가능 |
| 원인 | 지연 | 수용량 초과 |
| Metric | Timeout/Late Worker | Reject/Queue |

---

# 76. Resource Chain

```text
Tomcat Request Thread
      ↓
PDMG Worker
      ↓
Hikari
      ↓
DB Session
      ↓
SQL
```

서로 다른 Pool이다.

---

# 77. DB Slow → Runtime Failure Chain

```text
Slow SQL
  ↓
DB Session 장기점유
  ↓
Hikari Pending
  ↓
Worker 장기점유
  ↓
Queue 증가
  ↓
Timeout / Overload
  ↓
504 / 503
```

---

# 78. TCF ON

```text
Common Controller
→ TcfFacade
→ Timeout
→ Dispatcher
→ Handler
→ Facade
→ Service
```

---

# 79. TCF OFF

```text
Business MVC Controller
→ Service / Facade
```

일부 AS-IS는 Facade 우회 가능.

---

# 80. TCF ON/OFF Runtime Gap

확인 필요:

```text
Transaction Boundary
Timeout
Error
Facade Boundary
Use Case
```

ON/OFF를 동일 Runtime이라고 설명하지 않는다.

---

# 81. STF / ETF

Source에서 Bean 존재와 실제 실행연결은 다르다.

현재 분석에서 TcfFacade 호출선에 직접 연결되지 않았다면:

```text
[AS-IS]
실행단계로 그리지 않음
```

TO-BE 여부는 ADR.

---

# 82. ServiceId Runtime

```text
Path/Header
  ↓
ServiceId
  ↓
Dispatcher
  ↓
Handler Registry
```

Path/Header 불일치 검증이 없다면:

```text
[RISK]
```

---

# 83. ServiceId Routing Failure

```text
ServiceId
  ↓
Handler Not Found
```

는 Business Error와 분리한다.

---

# 84. Response Assembly

정상 PDMG Reference:

```text
hdr_nhnis
+
dto
```

알려진 실패:

```text
hdr_nhnis
+
result
```

---

# 85. Filter Early Error

Filter에서:

```text
sendError
```

로 종료되면:

```text
Controller Advice 미진입
```

가능.

따라서 표준 Error Envelope 불일치 Risk.

---

# 86. Runtime Error Taxonomy

최소 구분:

```text
Validation Error
Business Error
Authentication Error
Authorization Error
Route/Handler Error
System Error
Timeout
Overload
DB Error
External IF Error
Event Error
CDC Error
ETL Error
File Error
Batch Error
DR Failure
```

---

# 87. Error → Recovery Matrix

| Error | Retry | Compensation | Owner |
|---|---|---|---|
| Validation | N | User correction | App |
| Business Reject | N | Business action | App |
| Timeout | Conditional | Idempotency required | App/IF |
| Overload | Backoff | Scale/Throttle | Ops |
| External IF | Policy | Reconcile | IF |
| CDC | Replay | Reconcile | Data |
| Batch | Restart | Reconcile | Batch |

---

# 88. Security Runtime

관측 대상:

```text
Authentication Failure
Authorization Failure
JWT Expired
Signature Failure
JWKS/Key Failure
Session Expiry
Repeated Suspicious Request
```

Security Mechanism이 존재한다고 해서 Monitoring이 구현됐다고 단정하지 않는다.

---

# 89. JWT Runtime

개념:

```text
Client
→ JWT/SSO
→ Token
→ Application
→ Validation
→ Business
```

실제 PDMG Security 상세는 Security 정의서 Source를 따른다.

---

# 90. Session/JWT HA 주의

JWT 도입:

```text
≠ 모든 State 제거
```

Refresh / Denylist / User State / Session 요구가 남을 수 있다.

---

# 91. 11.1 시스템 모니터링

[PPT-CONTENT-GAP]

보강 Baseline:

```text
NFR
+
Runtime
+
DevOps/OM
```

---

# 92. Observability Big Picture

```text
GUID + ServiceId
      ↓
Application Log
      ↓
Thread / Worker
      ↓
Transaction
      ↓
Hikari / DB Session
      ↓
SQL / External IF
      ↓
Metric / APM
      ↓
Alert
      ↓
Runbook
```

---

# 93. 관측 계층

```text
Channel
Interface
Application
Tomcat/JVM
PDMG Worker
Queue
Hikari
DB
SQL
Event
CDC
ETL
File
Batch
Security
Infrastructure
```

---

# 94. 거래 관측 키

```text
GUID
ServiceId
Interface ID
Runtime Type
User
Host
JVM
Thread
SQL
Error
```

---

# 95. 좋은 운영 질문

운영 Dashboard가 답해야 한다.

```text
어느 ServiceId가
어느 Host/JVM에서
어느 Worker/DB Pool/SQL 때문에
느린가?
```

---

# 96. 모니터링 must

Metric은 최소:

```text
Value
Threshold
Owner
Alert
Runbook
Evidence
```

를 가진다.

---

# 97. Metric만 있고 Alert 없음

```text
Metric 있음
Alert 없음
Owner 없음
```

이면 Observability NFR 완료로 보지 않는다.

---

# 98. Tomcat/JVM Monitoring

```text
Request Thread Busy
Active Thread
Queue
Heap
GC
Metaspace
CPU
Error
```

---

# 99. PDMG Worker Monitoring

```text
Active Worker
Pool Size
Queue Depth
Reject
Timeout
Execution Time
Late Worker
```

---

# 100. Hikari Monitoring

```text
Active
Idle
Pending
Max
Acquire Time
Timeout
```

---

# 101. DB Monitoring

```text
Session
Wait
CPU
I/O
Lock
Slow SQL
Top SQL
```

---

# 102. Event Monitoring

```text
Producer Rate
Consumer Rate
Lag
Partition
Error
Retry
DLQ
```

---

# 103. CDC Monitoring

```text
Capture Lag
Apply Lag
Trail
Checkpoint
Backlog
Error
```

---

# 104. ETL Monitoring

```text
Start/End
Elapsed
Input/Output
Reject
Restart
Window Overrun
```

---

# 105. File Monitoring

```text
Transfer
Ready
Processing
Done
Error
Hash
Record Count
Age
```

---

# 106. Batch Monitoring

```text
Schedule
Start
Job
Step
Exit
Restart
Completion
Downstream
```

---

# 107. Security Monitoring

```text
401/403
Token Failure
JWKS Failure
Repeated Failed Login
Permission Deny
```

---

# 108. Alert → Diagnosis → Recovery

```text
Alert
  ↓
Correlation
  ↓
Scope
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

---

# 109. 11.2 시스템 가용성

가용성은:

```text
"이중화"
```

라는 문구가 아니다.

정의:

```text
Detect
+
Isolate
+
Traffic Switch
+
Service Continuity
+
Data Integrity
+
Recovery
+
Evidence
```

---

# 110. HA 전략

Baseline:

```text
AP Active-Active
+
Scale-out
+
L4/GSLB
+
Fault Isolation
```

---

# 111. DB HA/DR 전략

```text
Integrity First
```

원칙.

이론적 무조건 Active-Active보다:

```text
정합성
운영복잡도
복구
```

를 우선한다.

---

# 112. 장애 시나리오 8종

고정 Baseline:

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

---

# 113. 장애 시나리오 필수 필드

모든 시나리오:

```text
Detection
Traffic/Execution Switch
Service Continuity
Data Consistency
Recovery
Approval
Evidence
```

공란이면 Gate 미통과.

---

# 114. #1 AP VM 장애

```text
VM Failure
  ↓
Health Check
  ↓
L4 제외
  ↓
Other AP 처리
  ↓
Capacity 검증
  ↓
Node 복구
  ↓
Rejoin
```

Evidence:

```text
Detection Time
Traffic Shift
Error Rate
Capacity
Recovery Time
```

---

# 115. #2 AP Group 장애

```text
AP Group Failure
  ↓
Isolate
  ↓
Alternative Group / DR
  ↓
Traffic Switch
  ↓
Business Validation
```

단일 VM 장애와 다르다.

---

# 116. #3 RDW 장애

```text
RDW Failure
  ↓
DB/RAC HA
  ↓
Application Impact
  ↓
Data Integrity
  ↓
Recovery
```

마케팅/운영조회 영향 측정.

---

# 117. #4 ADW 장애

```text
ADW Failure
  ↓
Analytics Impact
  ↓
BI Degradation
  ↓
Recovery
```

RDW와 Criticality/SLO가 다를 수 있다.

---

# 118. #5 Kafka/Event 장애

```text
Broker / Consumer Failure
  ↓
Event Accumulation
  ↓
Lag
  ↓
Failover / Restart
  ↓
Replay
  ↓
Duplicate Check
```

---

# 119. #6 CDC Relay 장애

```text
Relay Failure
  ↓
Capture/Trail 상태 확인
  ↓
Restart
  ↓
Catch-up
  ↓
Reconcile
```

---

# 120. #7 Integration 장애

```text
APIM/Gateway/FOS/GSE Failure
  ↓
Route/Queue 영향
  ↓
Fallback
  ↓
Retry/Reconcile
```

대내/대외 별도 시나리오를 가질 수 있다.

---

# 121. #8 Center DR

```text
Center Failure
  ↓
Detect
  ↓
Decision / Approval
  ↓
Fencing / Isolate
  ↓
GSLB / DNS / Traffic Switch
  ↓
Application Start / Verify
  ↓
DB/Data Integrity
  ↓
Batch/IF/File/Event Recovery
  ↓
Business Validation
  ↓
Evidence
```

---

# 122. 11.3 시스템 확장성

Scale 축:

```text
WEB/WAS
Data
Kafka/Event
Worker
DB Connection
ETL Parallelism
Storage/I/O
```

---

# 123. Scale Trigger

근거:

```text
Capacity Model
+
Runtime Metric
```

감(感) Scale-out 금지.

---

# 124. WEB/WAS Scale-out

Trigger 후보:

```text
TPS
CPU
Thread Busy
P95
Queue
```

---

# 125. Event Scale

Trigger:

```text
Producer Rate
Consumer Lag
Offer Burst
```

---

# 126. Data Scale

Trigger:

```text
Load
Query
Storage
I/O
Batch Window
```

---

# 127. Scale 후 재검증

Scale 후:

```text
Monitoring
Alert
HA
DR
Capacity
```

를 재검증한다.

---

# 128. 11.4 DR 구성

Physical에서:

```text
의왕
↔
안성
```

Mapping을 정의했다.

RUNTIME에서는 전환 절차를 정의한다.

---

# 129. DR 완료조건

```text
Host
+
Artifact
+
Config
+
Network
+
DB/Data
+
Batch
+
Interface
+
File
+
Event
+
Monitoring
+
Runbook
+
Drill
```

---

# 130. RTO/RPO

필수:

```text
Service별
Component별
```

정의.

현재 최종 수치는 `[OPEN]`이면 그대로 둔다.

---

# 131. DR 즉시/후순위

Working Baseline:

```text
Immediate
Marketing
Data Platform
IT Support

Deferred
BI
DG
```

후순위:

```text
≠ Backup/Recovery 면제
```

---

# 132. Failover 순서

예:

```text
1 Detect
2 Approval
3 Fence Primary
4 Activate Data
5 Start Application
6 Switch Traffic
7 Validate IF/Batch/File/Event
8 Business Smoke
9 Monitor
10 Evidence
```

실제 Runbook에서 확정.

---

# 133. Failback

DR Drill은 Failover만이 아니라:

```text
Failback
```

을 포함해야 한다.

필수:

```text
Data Resync
Fencing
Traffic Return
Business Validation
Evidence
```

---

# 134. 11.5 백업 구성

Backup 대상은 동일하지 않다.

```text
DB
Application Artifact
Config
File
Key/Certificate
Operational Data
```

---

# 135. Backup Contract

각 대상:

```text
Backup Type
Frequency
Retention
Encryption
Location
Owner
Restore Procedure
Restore Test
RTO/RPO Link
Evidence
```

---

# 136. Backup Success ≠ Recovery Proven

```text
Backup Job Success
```

만으로 완료 아님.

필수:

```text
Restore Test
```

---

# 137. DB Backup

확인:

```text
Full/Incremental/Archive
Retention
Offsite/DR
Restore
Consistency
```

구체 값은 Backup Policy 근거로 작성.

---

# 138. Config Backup

대상:

```text
application.yml
server.xml
httpd.conf
JVM Options
Scheduler Config
Interface Config
```

Source/Secret과 분리.

---

# 139. Key/Certificate Backup

보안:

```text
Encryption
Access Control
Rotation
Restore
```

일반 파일 Backup과 동일 취급하지 않는다.

---

# 140. 모니터링 RACI

모든 Alert에:

```text
Responsible
Accountable
Consulted
Informed
```

정의.

---

# 141. Monitoring Owner

최소:

```text
Application
Infrastructure
DBA
Data
Interface
Security
Operations
```

Role 간 Escalation을 정의한다.

---

# 142. Alert Severity

예:

```text
Critical
High
Medium
Info
```

기준은 실제 운영정책에서 확정.

---

# 143. Runtime Evidence Package

최소 Chain:

```text
Architecture Baseline ID
  ↓
Source Commit
  ↓
Build ID
  ↓
Artifact
  ↓
Deployment ID
  ↓
Host / JVM
  ↓
Runtime Type
  ↓
ServiceId / Interface / Job / Event
  ↓
GUID / Trace
  ↓
Log / Metric / SQL / Error
  ↓
Validation Result
```

---

# 144. Runtime Evidence 종류

```text
Request/Response Trace
APM Trace
Application Log
Transaction Log
ImageLog
Metric
DB Session/SQL
Event Lag
CDC Lag
ETL Reconciliation
File Hash
Batch Exit
Failover Log
Restore Test
DR Drill
```

---

# 145. NFR 5축 Runtime Validation

| NFR | Runtime Evidence |
|---|---|
| Performance | P95/P99, Throughput, SQL, Lag |
| Availability | Failure Scenario/Failover |
| Scalability | Load/Scale-out |
| Security | Auth/AuthZ/Token Failure |
| Observability | E2E GUID/ServiceId Trace |

---

# 146. Performance Validation

확인:

```text
P95
P99
TPS
Queue
Worker
Hikari
SQL
External
```

측정구간 명시.

---

# 147. FAST Validation

#7:

```text
Trigger
→ Decision
→ Offer/UMS
```

의 측정구간으로 검증.

---

# 148. DEEP Validation

```text
Source
→ CDC
→ RDW
→ ETL
→ ADW
→ BI
```

각 단계 별도 SLA/시간.

---

# 149. CDC Validation

```text
Commit
→ Capture
→ Apply
→ Visible
```

측정구간 고정 전 숫자 확정 금지.

---

# 150. Batch Validation

```text
Start
→ Job
→ Step
→ Business Complete
→ Downstream
```

완료시각을 검증.

---

# 151. Timeout 계층

Ops Baseline:

```text
DB Query
<
Transaction
<
External Integration
<
Client / Channel
```

계층 역전 금지.

---

# 152. Timeout Budget와 PDMG 5초

PDMG:

```text
Worker Wait 5000ms
```

는 전체 Timeout 계층의 한 지점.

다음과 동일시 금지:

```text
DB Query Timeout
External Timeout
Client Timeout
```

---

# 153. Runtime Gate

릴리스 전 최소:

```text
Normal
Delay
Failure
```

시나리오 Evidence.

---

# 154. NFR Gate

개통 전:

```text
NFR-VALIDATION
FAST Validation
DEEP Validation
Runtime Scenarios
DR Drill
Runtime Evidence
```

필수.

---

# 155. HA/DR 완료 Gate

```text
[ ] AP 장애 전환
[ ] RTO/RPO
[ ] DB Integrity Strategy
[ ] DR 범위표
[ ] Failover/Failback
[ ] Fencing
[ ] AP/DB/Batch/IF/File/Kafka 시나리오
[ ] 8개 장애 시나리오 필수필드
[ ] DR Drill Evidence
```

---

# 156. 유형별 Gate

모든 #1~#12:

```text
[ ] Normal
[ ] Delay
[ ] Failure
[ ] Retry/Recovery
[ ] Monitoring
[ ] Evidence
```

---

# 157. Runtime 준수 규범

shall:

```text
모든 신규 Runtime
→ #1~#12 매핑
```

---

# 158. 주/보조 규범

복수 Runtime이면:

```text
Primary
Secondary
```

를 분리.

---

# 159. Sequence 규범

must:

```text
Actor
Step #
System
Interface
Data
Owner
```

를 갖는다.

---

# 160. SLO 규범

SLO:

```text
숫자
+
측정구간
+
Owner
```

필수.

---

# 161. Retry 규범

Retry:

```text
Error Type
Max
Backoff
Idempotency
Recovery
```

명시.

---

# 162. Monitoring 규범

모든 Runtime:

```text
Metric
Alert
Owner
Runbook
```

must.

---

# 163. Evidence 규범

```text
설계 있음
Metric 없음
```

이면 Gate 미통과.

---

# 164. PDMG Runtime 규범

must:

```text
Filter에 업무 SQL 금지
Controller에 DAO 금지
Handler에 DAO 금지
Context Clear
ON/OFF 분리
STF/ETF 실제 호출선 확인
```

---

# 165. Timeout 규범

금지:

```text
HTTP Timeout
=
DB Kill
```

서술.

---

# 166. Transaction 규범

금지:

```text
Handler TX 밖이라고 현재 AS-IS를 잘못 표현
```

---

# 167. Worker 규범

must:

```text
Context Install
Context Clear
```

---

# 168. Overload 규범

503과 504를 동일 Error Metric으로만 처리하지 않는다.

---

# 169. CDC 규범

must:

```text
Checkpoint
Lag
Recovery
Reconciliation
```

---

# 170. File 규범

must:

```text
Completion Signal
```

---

# 171. Batch 규범

must:

```text
Control-M
Restart
Checkpoint
Exit/Business Completion
```

---

# 172. DR 규범

금지:

```text
DR Host 있음
= DR 완료
```

---

# 173. Backup 규범

금지:

```text
Backup Success
= Restore 가능
```

---

# 174. Monitoring 규범

금지:

```text
오픈 후 Alert 추가 예정
```

---

# 175. RTO/RPO 없는 고가용 문구 금지

```text
"고가용 구성"
```

만 있고 RTO/RPO/절차/Evidence 없음:

```text
미완성
```

---

# 176. BI/DG 후순위 주의

후순위 DR:

```text
≠ 해당 없음
```

반드시:

```text
Recovery Order
Backup
RTO/RPO
```

를 정의.

---

# 177. Runtime Anti-Pattern

```text
유형번호 없음
주/보조 혼재
ServiceId 없는 Online
GUID 없는 Transaction
Owner 없는 SLO
Retry만 있고 Idempotency 없음
Timeout만 있고 DB/JDBC 상태 미관측
504/503 혼합
Event를 Online WAS에서 처리
CDC/ETL 혼합
File 완료신호 없음
App Cron 운영 Batch
RTO/RPO 없는 HA
DR Server only
Restore Test 없음
Metric만 있고 Alert/Runbook 없음
Evidence 없는 개통
```

---

# 178. Runtime Inventory Template

| # | Name | Actor | Entry/IF | Key | SLO | Retry | Monitor | Owner | Evidence |
|---:|---|---|---|---|---|---|---|---|---|
| 1 | 정보단말 | User | Direct | ServiceId/GUID | ~3s 감각 | Policy | P95 | TBD | Trace |
| 8 | CDC | Core | CDC | Checkpoint | ADR | Replay | Lag | Data | Lag/CP |
| 12 | Batch | Scheduler | Control-M | Job ID | Window | Restart | Exit | Batch | Job Log |

---

# 179. Runtime Sequence Template

```text
1. Actor
2. Entry
3. Interface
4. Framework
5. Business
6. Data/External
7. Outcome
8. Monitoring
9. Recovery
10. Evidence
```

---

# 180. Failure Scenario Template

| Field | Required |
|---|---:|
| Scenario ID | Y |
| Trigger | Y |
| Detection | Y |
| Impact | Y |
| Traffic/Execution Switch | Y |
| Service Continuity | Y |
| Data Integrity | Y |
| Recovery | Y |
| Approval | Y |
| Evidence | Y |
| RTO/RPO | Y/해당 |
| Owner | Y |

---

# 181. Monitoring Inventory Template

| Runtime | Metric | Threshold | Alert | Owner | Runbook | Evidence |
|---|---|---|---|---|---|---|
| Online | P95 | TBD | Y | App/Ops | TBD | APM |
| Worker | Queue | TBD | Y | App/Ops | TBD | Metric |
| CDC | Lag | ADR | Y | Data | TBD | CDC Log |
| Batch | End Time | Window | Y | Batch | TBD | Job |

---

# 182. Backup Inventory Template

| Target | Type | Frequency | Retention | Location | Owner | Restore Test | Evidence |
|---|---|---|---|---|---|---|---|
| DB | TBD | TBD | TBD | TBD | DBA | Required | TBD |
| Config | TBD | TBD | TBD | TBD | Ops | Required | TBD |
| Key | Secure | TBD | TBD | Secure | Security | Required | TBD |

---

# 183. DR Inventory Template

| Component | Primary | DR | RTO | RPO | Switch | Failback | Owner | Evidence |
|---|---|---|---|---|---|---|---|---|
| MP-WAS | 의왕 | 안성 | TBD | N/A | GSLB/L4 | Required | TBD | Drill |
| RDW | Primary | DR | TBD | TBD | DB Procedure | Required | DBA | Drill |

---

# 184. NFR Validation Template

| NFR | Requirement | Measure | Result | Evidence | Status |
|---|---|---|---|---|---|
| Performance | P95 | APM | TBD | Trace | Open |
| Availability | Failover | Drill | TBD | Log | Open |
| Scalability | Scale-out | Load Test | TBD | Test | Open |
| Security | Auth/AuthZ | Security Test | TBD | Log | Open |
| Observability | E2E Trace | GUID | TBD | APM/Log | Open |

---

# 185. Runtime → Observability Trace

```text
Runtime #
  ↓
ServiceId / Interface / Job / Event
  ↓
GUID / Correlation
  ↓
Host / JVM
  ↓
Thread / Pool
  ↓
DB / SQL / External
  ↓
Result / Error
```

---

# 186. Runtime → Physical Trace

```text
Runtime Type
  ↓
Application
  ↓
JVM
  ↓
Host
  ↓
Center
```

DR 시 동일 Trace가 DR Host까지 이어져야 한다.

---

# 187. Runtime → Data Trace

```text
ServiceId/Event/Job
  ↓
DAO / CDC / ETL
  ↓
DB / Subject
  ↓
Outcome
```

---

# 188. Runtime → Interface Trace

```text
Runtime #
  ↓
IF Standard No.
  ↓
Interface ID
  ↓
Source / Target
```

---

# 189. Runtime Evidence Closed Loop Handoff

```text
Architecture Definition
  ↓
Runtime Scenario
  ↓
Test
  ↓
Deployment
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

# 190. Drift 대상

```text
Runtime Type
Sequence
Timeout
Pool
SLO
Interface
Host
DB
Monitoring
DR
```

문서와 실제가 다르면 Drift.

---

# 191. PDMG Drift 예시

```text
Document:
TCF OFF도 Timeout 적용

Actual:
OFF는 TimeoutExecutor 우회

→ Drift/GAP
```

---

# 192. Runtime Conformance Rule 후보

```text
RULE-RT-01 Every flow has Runtime Type
RULE-RT-02 Primary/Secondary separated
RULE-RT-03 Every online flow has ServiceId/GUID
RULE-RT-04 Every Runtime has SLO/Owner
RULE-RT-05 Every retryable write has idempotency
RULE-RT-06 Every File flow has completion signal
RULE-RT-07 Every Batch is centrally scheduled
RULE-RT-08 Every CDC has checkpoint/lag
RULE-RT-09 Every Worker context is cleared
RULE-RT-10 Every DR component has Runbook/Evidence
RULE-RT-11 Backup requires Restore Test
RULE-RT-12 No release without Runtime Evidence
```

---

# 193. GAP Register

| ID | GAP | 영향 | 조치 |
|---|---|---|---|
| GAP-RT-01 | PPT 7.2 상세 부족 | 단말 Runtime | 8/9/PDMG Cross Reference |
| GAP-RT-02 | PPT 7.4 상세 부족 | UMS | #7 Supplemental |
| GAP-RT-03 | PPT 11장 상세 부족 | Ops | 11 정의서 보강 |
| GAP-RT-04 | CDC SLA 3/30초 | Data SLO | ADR |
| GAP-RT-05 | RTO/RPO 최종값 | DR | ADR |
| GAP-RT-06 | TCF OFF Timeout 차이 | SLA Drift | ON/OFF Alignment |
| GAP-RT-07 | Mutable Worker Context | Thread Safety | Immutable Snapshot 검토 |
| GAP-RT-08 | Filter Error Envelope | Client Contract | Standard Writer |
| GAP-RT-09 | STF/ETF Runtime 연결 | Control | Source/ADR |
| GAP-RT-10 | Path/Header ServiceId 검증 | Routing Risk | Validation |
| GAP-RT-11 | Runtime Metric Threshold | Ops | Capacity/Production Tune |
| GAP-RT-12 | pdmg-om 구현 Evidence | OM | Source/Runtime 확인 |
| GAP-RT-13 | Backup 상세주기 | Recovery | Policy 확인 |
| GAP-RT-14 | 8시나리오 실제 Drill | Availability | Validation 실행 |

---

# 194. Risk Register

| ID | Risk | Severity 후보 |
|---|---|---|
| RISK-RT-01 | Context/MDC Leak | Critical |
| RISK-RT-02 | Timeout 후 Late DB Work | High |
| RISK-RT-03 | JDBC Interrupt 미보장 | High |
| RISK-RT-04 | Path/Header ServiceId mismatch | High |
| RISK-RT-05 | Filter Error 표준 Envelope 불일치 | High |
| RISK-RT-06 | Worker Queue Saturation | High |
| RISK-RT-07 | RDW 분석부하 전이 | High |
| RISK-RT-08 | CDC Lag 미탐지 | High |
| RISK-RT-09 | DR Host-only 설계 | Critical |
| RISK-RT-10 | Restore 미검증 Backup | Critical |

---

# 195. ADR 후보

| ADR | 주제 |
|---|---|
| ADR-RT-01 | CDC SLA |
| ADR-RT-02 | RTO/RPO |
| ADR-RT-03 | PDMG Timeout Target |
| ADR-RT-04 | TCF OFF 운영 허용 |
| ADR-RT-05 | Worker Immutable Context |
| ADR-RT-06 | JDBC/DB Timeout Stack |
| ADR-RT-07 | Error/HTTP Status |
| ADR-RT-08 | Event Retry/DLQ |
| ADR-RT-09 | DR Failover/Failback |
| ADR-RT-10 | Monitoring Threshold |
| ADR-RT-11 | Backup/Restore Policy |
| ADR-RT-12 | OM Control Plane |

---

# 196. Verification Checklist — 7.1

```text
[ ] 대유형 6을 유지하는가
[ ] 소유형 #1~#12가 고정되는가
[ ] 유형 없는 신설이 없는가
[ ] 주/보조 유형을 분리했는가
[ ] SLO/Owner/Evidence가 있는가
```

---

# 197. Verification Checklist — 채널/연계

```text
[ ] #1~#5가 Interface 표준과 정합되는가
[ ] Online에 ServiceId/GUID가 있는가
[ ] 대외에 대사/RTO가 있는가
[ ] Physical URL 하드코딩이 없는가
```

---

# 198. Verification Checklist — 이벤트/데이터

```text
[ ] #6/#7를 분리했는가
[ ] Consumer Lag를 관측하는가
[ ] CDC Checkpoint/Lag가 있는가
[ ] ETL Count/Hash/Restart가 있는가
[ ] #10 분석부하가 RDW를 침해하지 않는가
```

---

# 199. Verification Checklist — 파일/배치

```text
[ ] FOS Completion Signal이 있는가
[ ] File Hash/Count가 있는가
[ ] Control-M 중앙통제인가
[ ] Batch Restart/Checkpoint가 있는가
[ ] Business Completion을 검증하는가
```

---

# 200. Verification Checklist — PDMG Online

```text
[ ] Request/Worker Thread를 분리했는가
[ ] Transaction Boundary가 Dispatcher부터임을 반영했는가
[ ] Handler가 DAO를 직접 호출하지 않는가
[ ] Context Install/Clear가 있는가
[ ] 5000/20/100을 AS-IS Snapshot으로만 표시했는가
[ ] 503/504를 구분했는가
[ ] cancel(true)=DB Kill로 설명하지 않았는가
[ ] Deadline Late Commit 방지를 확인했는가
```

---

# 201. Verification Checklist — 11.1 Monitoring

```text
[ ] GUID+ServiceId E2E Trace가 가능한가
[ ] Tomcat/Worker/Hikari/DB를 분리하는가
[ ] Event/CDC/ETL/File/Batch Metric이 있는가
[ ] Alert Owner/RACI가 있는가
[ ] Runbook이 연결되는가
```

---

# 202. Verification Checklist — 11.2 Availability

```text
[ ] AP AA/Fault Isolation이 정의되는가
[ ] 8개 장애 시나리오가 모두 있는가
[ ] 필수필드가 공란이 아닌가
[ ] DB Integrity First 원칙이 반영되는가
```

---

# 203. Verification Checklist — 11.3 Scale

```text
[ ] Scale Trigger가 Capacity/Metric 기반인가
[ ] 감 Scale-out이 아닌가
[ ] Scale 후 HA/DR/Monitoring 재검증하는가
```

---

# 204. Verification Checklist — 11.4 DR

```text
[ ] RTO/RPO가 있는가
[ ] Physical Host Mapping과 일치하는가
[ ] Failover/Failback/Fencing이 있는가
[ ] AP/DB/Batch/IF/File/Kafka 각각 시나리오가 있는가
[ ] DR Drill Evidence가 있는가
```

---

# 205. Verification Checklist — 11.5 Backup

```text
[ ] Backup 대상별 정책이 다른가
[ ] Retention/Owner가 있는가
[ ] Restore Procedure가 있는가
[ ] Restore Test Evidence가 있는가
```

---

# 206. Runtime Completion Gate

```text
G-RT-01 Runtime Type
G-RT-02 Primary/Secondary
G-RT-03 Sequence
G-RT-04 SLO/Owner
G-RT-05 Retry/Compensation
G-RT-06 Monitoring
G-RT-07 PDMG Online Runtime
G-RT-08 Thread/TX/Timeout
G-RT-09 Failure Scenarios
G-RT-10 HA/DR
G-RT-11 Backup/Restore
G-RT-12 Runtime Evidence
G-RT-13 GAP/ADR
```

---

# 207. 개통 금지조건

다음 중 하나라도 해당되면 개통 불가:

```text
Runtime Type 없음
SLO 측정구간 없음
Owner 없음
Monitoring 없음
Retry/Recovery 없음
8시나리오 미완성
RTO/RPO 없음
Restore Test 없음
DR Drill 없음
Runtime Evidence 없음
```

---

# 208. Architecture Closed Loop Handoff

RUNTIME Output:

```text
Runtime Type
Actual Sequence
Thread/TX/Pool
Timeout/Error
Metric
Alert
Recovery
HA/DR
Backup/Restore
Runtime Evidence
```

다음 장에서:

```text
Requirement
→ Architecture
→ Inventory
→ Source/Config
→ Test
→ Deployment
→ Runtime Evidence
→ Drift
→ GAP/ADR
→ Baseline
```

을 닫는다.

---

# 209. 최종 평가

RUNTIME Architecture가 완료됐다는 뜻은 다음 질문에 답할 수 있다는 의미다.

```text
이 거래는 Runtime # 몇 번인가?
어디서 시작하는가?
무슨 Interface를 쓰는가?
어느 Thread/Pool에서 실행되는가?
DB Transaction은 어디서 시작·종료되는가?
Timeout 시 HTTP/Worker/JDBC/TX는 각각 어떻게 되는가?
오류와 과부하는 어떻게 구분되는가?
Event/CDC/File/Batch는 어떻게 재처리되는가?
어떤 Metric/Alert/Runbook으로 운영되는가?
장애 시 어떻게 전환·복구되는가?
Backup이 실제 Restore 가능한가?
어떤 Runtime Evidence가 남는가?
```

최종 선언:

> **RUNTIME Architecture는 설계된 경로가 실제 운영에서 시간순으로 실행·실패·복구되는 방식을 정의하고, 그 결과를 GUID·ServiceId·Metric·Log·Drill·Validation Evidence로 증명하는 Architecture의 최종 실행 계층이다.**

---

# Appendix A. Runtime 12 Summary

```text
#1  정보계 단말
#2  통합업무
#3  미니 싱글뷰
#4  대내 연계
#5  대외 연계
#6  반응형 정보 수집
#7  고객 오퍼링
#8  CDC
#9  ETL
#10 분석·의사결정
#11 File
#12 Batch
```

---

# Appendix B. PDMG Runtime Summary

```text
HTTP
→ Filter
→ Security
→ MVC
→ Interceptor
→ Controller
→ TCF
→ Timeout Executor
→ Worker
→ Transaction
→ Dispatcher
→ Handler
→ Facade
→ Service
→ DAO
→ DB
→ Response
```

---

# Appendix C. Timeout Summary

```text
HTTP Timeout
≠ Worker End
≠ JDBC Cancel
≠ TX Rollback
```

---

# Appendix D. Failure Scenario 8

```text
AP VM
AP Group
RDW
ADW
Kafka
CDC Relay
Integration
Center DR
```

---

# Appendix E. NFR Summary

```text
Performance
Availability
Scalability
Security
Observability
```

---

# Appendix F. Runtime Evidence Summary

```text
Trace
Metric
Log
SQL
Event Lag
CDC Lag
ETL Count
File Hash
Batch Exit
Failover
Restore Test
DR Drill
```

---

# Appendix G. 본 장에서 임의 확정하지 않는 값

```text
CDC 최종 SLA
RTO/RPO
Production Alert Threshold
최종 Timeout Stack
최종 PDMG Worker Size
최종 Tomcat Thread
최종 Hikari
Backup 상세 주기
DR 자동/수동 세부정책
OM Dashboard 구현 범위
```

근거/승인/Evidence로 닫는다.
