# NSIGHT PDMG 아키텍처 정의서
# 제9장. 런타임 서비스
## Story: “정적인 Architecture를 거래 한 건의 시간축으로 펼쳐본다”


---

# 0. Opening Script

8장에서 실행규칙을 봤다면, 9장에서는 그 규칙이 실제 거래 한 건에서 어떤 순서로 움직이는지 봅니다.

이 장에서는 박스의 위치보다 **시간**이 중요합니다. Request Thread가 언제 Worker에게 일을 넘기고, Transaction이 언제 시작되고, DB가 늦어졌을 때 어떤 현상이 위로 전파되는지 시간축으로 펼칩니다.

## FIG-09-01. 장 전체 Architecture

```text
════════ Request Thread ════════
HTTP
 ↓
Filter
 ↓
Security
 ↓
MVC / Controller
 ↓
Future.get(timeout)
        │ submit
        ▼
════════ Worker Thread ═════════
Context Install
 ↓
Transaction BEGIN
 ↓
Dispatcher / Handler
 ↓
Facade / Service / DAO
 ↓
DB
 ↓
Deadline
 ↓
Commit / Rollback
        │
        ▼
Response / Evidence
```

Runtime에서는 8장의 정적인 Mechanism을 시간축으로 펼칩니다. 특히 `submit`을 기준으로 Request Thread와 Worker Thread가 갈라지고, DB Transaction은 Worker 쪽에서 시작됩니다. 이 차이를 이해해야 504, Overload, DB 지연을 정확히 해석할 수 있습니다.


---

# 1. Request Thread 역할

## FIG-09-02. Request Thread 역할

```text
Request Thread
├─ Filter/Security/MVC
├─ Controller
├─ Worker submit
├─ Future wait
└─ HTTP Response
```

Request Thread는 HTTP와 MVC 수명을 담당합니다.

Business Transaction 전체를 직접 수행하지 않고 Worker에게 위임하고 완료를 기다립니다. 이 분리가 Timeout의 의미를 복잡하게 만드는 동시에 Request Thread 보호장치가 됩니다.

그래서 Request Thread의 종료와 Business 작업 종료를 같은 것으로 보면 안 됩니다.


---

# 2. Worker Thread 역할

## FIG-09-03. Worker Thread 역할

```text
pdmg-online-N
├─ Context Install
├─ Transaction BEGIN
├─ Dispatch
├─ Business
├─ DB
├─ Deadline
└─ Commit/Rollback
```

Worker는 Business Execution의 실제 주체입니다.

Request Thread에서 캡처한 Context/MDC를 설치하고 Transaction을 시작한 뒤, Handler/Facade/Service/DAO를 실행합니다.

작업이 끝나면 반드시 Worker Context를 clear해야 합니다.


---

# 3. ServiceId Runtime Routing

## FIG-09-04. ServiceId Runtime Routing

```text
Request
 ↓
ServiceId
 ↓
Registry
 ↓
Handler
 ↓
Facade Method
 ↓
Business Use Case
```

Runtime에서 ServiceId는 단순 Header가 아니라 Routing Key입니다.

잘못된 ServiceId는 잘못된 Handler로 연결되므로 Registry uniqueness와 Context/Header/Path 일치검증이 중요합니다.

이 흐름은 Naming/Traceability 장과 연결됩니다.


---

# 4. DB Runtime

## FIG-09-05. DB Runtime

```text
Worker
 ↓
Transaction
 ↓
Hikari Connection
 ↓
JDBC Statement
 ↓
DB Session
 ↓
SQL / Wait / Result
```

DB에 도달하기까지도 여러 Resource Boundary가 있습니다.

Worker가 있다고 Connection이 있는 것은 아니며, Connection이 있다고 DB가 즉시 응답하는 것도 아닙니다.

성능문제는 이 체인을 따라 역으로 전파됩니다.


---

# 5. Timeout 504

## FIG-09-06. Timeout 504

```text
Future.get(5000ms)
 ├─ complete → success
 └─ timeout
      ↓
   cancel(true)
      ↓
   HTTP 504

Worker may continue
```

504는 Client에게 보이는 결과일 뿐 내부작업의 종료증명이 아닙니다.

`cancel(true)`가 Interrupt를 전달해도 JDBC Driver나 DB가 즉시 취소되는지는 별도 Integration Test가 필요합니다.

그래서 Query Timeout과 Transaction Deadline을 계층적으로 설계해야 합니다.


---

# 6. Overload 503

## FIG-09-07. Overload 503

```text
Worker 20
 ↓
Queue 100
 ↓
Full
 ↓
Reject
 ↓
OnlineOverloadException
 ↓
HTTP 503
```

Overload는 느린 응답과 다르게 **수용할 수 없는 부하를 빠르게 거절하는 정책**입니다.

Queue를 무한히 키우면 Timeout만 늘어납니다. 제한된 Worker/Queue와 503은 Backpressure의 일부입니다.

Target Capacity는 부하시험으로 재산정해야 합니다.


---

# 7. Saturation Cascade

## FIG-09-08. Saturation Cascade

```text
DB Slow
 ↓
Hikari Pending
 ↓
Worker Occupied
 ↓
Queue Increase
 ↓
Request Wait
 ↓
504 / 503
```

이 그림이 Runtime 장의 핵심 Failure Story입니다.

DB가 느리면 DB만 느린 것이 아니라 Connection, Worker, Queue, Request Thread로 영향이 올라옵니다.

따라서 Capacity는 Tomcat `maxThreads` 하나로 결정할 수 없습니다.


---

# 8. Runtime Evidence

## FIG-09-09. Runtime Evidence

```text
GUID
+ ServiceId
+ Thread
+ SqlId
+ ErrorCode
+ elapsed
+ deploymentId
+ host/jvm
 ↓
Evidence
```

Runtime을 설계했다면 마지막에는 증적이 남아야 합니다.

현재 GUID/ServiceId/MDC/ImageLog는 강한 기반입니다. 여기에 DeploymentId, Host/JVM, SqlId를 연결하면 Source부터 Runtime까지 추적할 수 있습니다.

이 연결이 자동화되어야 HG90 Gate가 실제로 동작합니다.

---

# 정상패턴과 금지패턴

## FIG-09-10. Normal Pattern

```text
Request→Worker→TX→DB→Response/Evidence
```

정상패턴은 Request와 Worker의 수명을 구분하고, Resource Chain 전체에 Backpressure와 계층형 Timeout을 적용하는 것입니다.

## FIG-09-11. Forbidden Pattern

```text
504=Worker 종료 / Worker=DB Session
```

HTTP 504를 Worker 종료나 DB Rollback 완료로 해석하거나 Queue를 무한 확장해 부하를 숨기는 방식을 금지합니다.

---

# Architecture Decision

## FIG-09-12. 주안과 대안

```text
[주안]
Request/Worker 분리 + 계층형 Timeout

        VS

[대안]
Request Thread 단일 실행
```

Request/Worker 분리 구조를 유지하면서 DB Query Timeout부터 Client Timeout까지 계층형 Budget을 정의하는 것을 주안으로 합니다.

단일 Request Thread에서 모든 작업을 실행하면 구조는 단순하지만 Slow DB가 HTTP Thread를 직접 점유해 Saturation 전파가 빨라질 수 있습니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---


# Evidence / Conformance — 이 장의 Architecture를 무엇으로 증명하는가

## Evidence Architecture

```text
런타임 서비스 Architecture Rule
        ↓
Source Evidence
        ↓
Config Evidence
        ↓
Runtime / Deployment Evidence
        ↓
Conformance Test
        ↓
PASS / GAP / ADR
```

### Source Evidence

- `[SOURCE]` OnlineTimeoutExecutor / TransactionTemplate / TransactionDispatcher / Handler Source 분석
- `[SOURCE]` GlobalExceptionHandler 및 Runtime 흐름 분석

### Config Evidence

- `[CONFIG]` timeout 5000ms / worker 20 / queue 100
- `[CONFIG]` Query Timeout/Hikari exact target 값 미확정

### Runtime / Deployment Evidence

- `[RUNTIME]` Request Thread와 Worker Thread 분리
- `[RUNTIME]` HTTP 504 ≠ Worker 종료 ≠ JDBC Cancel ≠ Rollback 완료
- `[RUNTIME]` 503 Overload Backpressure

### Architecture Decision

- `[DECISION]` DB Query Timeout < Worker/Transaction Deadline < Downstream/Server < Client Timeout

### Related ADR

- `ADR-017 Timeout Budget`
- `ADR-018 Retry/Idempotency`
- `ADR-029 Capacity Budget`
- `ADR-040 Runtime Evidence`

### Current GAP / OPEN

- `[GAP/OPEN]` JDBC Query Timeout
- `[GAP/OPEN]` JDBC Cancel Evidence
- `[GAP/OPEN]` Late Worker
- `[GAP/OPEN]` Deployment/Host Correlation
- `[GAP/OPEN]` Identity Binding

## 이 장의 판정

```text
Architecture Definition
        ↓
PASS

Current PDMG Conformance
        ↓
PARTIAL / CONDITIONAL

Runtime Evidence Coverage
        ↓
HIGH-MEDIUM

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `DB Query Timeout`
- `JDBC Cancel/Late Worker Test`
- `Overload/Capacity Test`
- `Deployment Correlation`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-09-16. 다음 장 Handoff

```text
런타임 서비스
 ↓
정적인 Architecture를 거래 한 건의 시간축으로 펼쳐본다
 ↓
남은 질문
"RDW는 실시간을 지키고 ADW는 분석을 극대화한다"
 ↓
데이터플랫폼
```

Runtime을 시간축으로 보면 성능과 장애가 어디서 전파되는지 설명할 수 있습니다. 다음 장에서는 그 Runtime의 최종 대상인 Data를 Operational과 Analytical Workload로 분리합니다.
