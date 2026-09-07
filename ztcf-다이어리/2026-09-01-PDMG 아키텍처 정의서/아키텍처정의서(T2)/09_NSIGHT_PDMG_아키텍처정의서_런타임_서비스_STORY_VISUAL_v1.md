# NSIGHT PDMG 아키텍처 정의서
# 제9장. 런타임 서비스
## 거래 한 건의 End-to-End 실행과 FAST/DEEP 경계
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **8장의 Mechanism을 시간축으로 펼쳐 Request/Worker/Transaction/DB/Response가 실제로 어떻게 움직이는지 보여준다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제8장에서 넘어온 질문
     ↓
8장의 Mechanism을 시간축으로 펼쳐 Request/Worker/Transaction/DB/Response가 실제로 어떻게 움직이는지 보여준다.
     ↓
이 장이 답해야 할 질문
     ↓
거래 한 건의 End-to-End 실행과 FAST/DEEP 경계
```

이 장의 핵심 원칙은 다음과 같다.

- Request Thread와 Worker Thread를 분리한다.
- HTTP 504와 Worker/DB 종료를 동일시하지 않는다.
- FAST Online과 DEEP Analytical Workload를 분리한다.
- GUID/ServiceId를 Runtime Evidence의 핵심축으로 유지한다.

---

# 1. Runtime은 정적인 Box를 시간축으로 펼친 것이다

## FIG-09-01. Runtime은 정적인 Box를 시간축으로 펼친 것이다

```text
Static
UI → App → DB

        ↓ time

T0 Request
T1 Filter
T2 Security
T3 MVC
T4 Worker
T5 TX
T6 DB
T7 Response
```

---

# 2. End-to-End Online Runtime

## FIG-09-02. End-to-End Online Runtime

```text
Browser
 ↓
Filter
 ↓
Security
 ↓
MVC / Interceptor
 ↓
OnlineTransactionController
 ↓
TcfFacade
 ↓
OnlineTimeoutExecutor
 ↓
Worker/TX
 ↓
Dispatcher/Handler
 ↓
Facade/Service/DAO/Mapper
 ↓
DB
 ↓
Response Advice
 ↓
Cleanup
```

---

# 3. Request Thread의 역할

## FIG-09-03. Request Thread의 역할

```text
Request Thread
├─ HTTP/MVC
├─ Controller
├─ Worker submit
├─ Future wait
└─ Response
```

---

# 4. Worker Thread의 역할

## FIG-09-04. Worker Thread의 역할

```text
Worker Thread
├─ Context Install
├─ TX BEGIN
├─ Dispatch
├─ Business
├─ DB
├─ Deadline
└─ Commit/Rollback
```

---

# 5. ServiceId Runtime Routing

## FIG-09-05. ServiceId Runtime Routing

```text
Header / Context / Path
 ↓
ServiceId
 ↓
Registry
 ↓
Handler
 ↓
Facade Method
```

---

# 6. 성공 Response Runtime

## FIG-09-06. 성공 Response Runtime

```text
Business Result
 ↓
Controller Return
 ↓
ResponseBodyAdvice
 ↓
hdr_nhnis + dto
 ↓
afterCompletion
 ↓
Filter finally
```

---

# 7. Known Error Runtime

## FIG-09-07. Known Error Runtime

```text
Exception
 ↓
GlobalExceptionHandler
 ↓
ErrorCode / Type
 ↓
hdr_nhnis + result
 ↓
HTTP Status
```

---

# 8. Timeout Runtime

## FIG-09-08. Timeout Runtime

```text
Future.get(5000ms)
 ├─ Complete → Response
 └─ Timeout
      ↓
   cancel(true)
      ↓
   504

Worker may continue
```

---

# 9. Overload Runtime

## FIG-09-09. Overload Runtime

```text
Worker 20
 ↓
Queue 100
 ↓
Saturation
 ↓
Reject
 ↓
503
```

---

# 10. DB Runtime

## FIG-09-10. DB Runtime

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
SQL Wait / Result
```

---

# 11. FAST와 DEEP를 Runtime에서 분리한다

## FIG-09-11. FAST와 DEEP를 Runtime에서 분리한다

```text
FAST
HTTP / Event / CDC
 ↓
bounded latency

DEEP
ETL / ADW / BI
 ↓
heavy workload
```

---

# 12. Runtime Evidence의 최소축

## FIG-09-12. Runtime Evidence의 최소축

```text
GUID
+ ServiceId
+ Thread
+ ErrorCode
+ SqlId
+ elapsed
+ DeploymentId [target]
+ Host/JVM [target]
```

---

# 13. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-09-13. Normal Pattern

```text
Request Thread
 ↓ submit
Worker Thread
 ↓
Transaction
 ↓
Business
 ↓
DB
 ↓
Response / Evidence
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 14. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-09-14. Forbidden Pattern

```text
HTTP 504 = Worker 종료       X
HTTP 504 = DB Rollback 완료    X
Worker Count = DB Session      X
FAST Runtime = DEEP Runtime    X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 15. Architecture Decision — Timeout 실행모델

## FIG-09-15. 주안과 대안

```text
[주안]
Request/Worker 분리 + 계층형 Timeout

        VS

[대안]
Request Thread 단일 실행
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | Request/Worker 분리 + 계층형 Timeout | Request Thread 단일 실행 |
| 장점 | • 격리/Deadline 통제<br>• Overload 제어<br>• DB 작업 경계 명확 | • 구조 단순 |
| 단점 | • Context/Thread 관리 복잡 | • 장기 DB 호출이 Request 점유<br>• 격리/Backpressure 약함 |
| 권고 | **주안 유지** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 16. Current PDMG GAP

## FIG-09-16. GAP Map

```text
Current PDMG
│
├─ JDBC Query Timeout exact value OPEN
├─ cancel(true) DB Cancel Evidence 미완료
├─ Late Worker Evidence 미완료
├─ Identity Binding GAP
└─ Deployment/Host correlation GAP
```

- `[GAP/OPEN]` JDBC Query Timeout exact value OPEN
- `[GAP/OPEN]` cancel(true) DB Cancel Evidence 미완료
- `[GAP/OPEN]` Late Worker Evidence 미완료
- `[GAP/OPEN]` Identity Binding GAP
- `[GAP/OPEN]` Deployment/Host correlation GAP

---

# 17. 제9장 Architecture 판정

## FIG-09-17. Assessment

```text
Architecture Definition
      ↓
PASS

Current PDMG Conformance
      ↓
CONDITIONAL

Runtime Evidence
      ↓
HIGH-MEDIUM
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| TCF ON Runtime | PASS | Source path 확인 |
| Request/Worker | PASS | 명확히 분리 |
| Timeout | CONDITIONAL | Late Worker/JDBC Evidence |
| Error | PARTIAL | Early Error Gap |
| Runtime Evidence | PARTIAL | Deployment/Host 축 미완료 |

---

# 18. 원천 정의서 Trace

## FIG-09-18. Source Trace

```text
Story Chapter
   ↓
PDMG 00~18 Source
   ↓
Current Fact / Target Reference
   ↓
Architecture Rule / GAP
```

| 원천 | 용도 |
|---|---|
| 09 Runtime | Story/Drill-down/Current-Target 근거 |
| 10 Transaction/Timeout | Story/Drill-down/Current-Target 근거 |
| 13 Non-online | Story/Drill-down/Current-Target 근거 |

---

# 19. 다음 장으로 넘어가는 이유

## FIG-09-19. 9장 → 10

```text
제9장
런타임 서비스
      ↓
"이 Runtime이 읽고 쓰는 데이터는 어떤 역할과 Ownership으로 분리되어야 하는가?"
      ↓
제10장
데이터플랫폼
```

---

# 20. 제9장 최종 결론

## FIG-09-20. Final Story

```text
런타임 서비스
   ↓
Responsibility
   ↓
Boundary
   ↓
Runtime
   ↓
Evidence
   ↓
PASS
```

제9장의 결론은 **거래 한 건의 End-to-End 실행과 FAST/DEEP 경계**라는 한 문장으로 정리된다.
