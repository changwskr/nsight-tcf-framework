# NSIGHT PDMG 아키텍처 정의서
# 제1장. 왜 다시 짓는가
## PDMG Architecture Definition의 출발점
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **PDMG를 왜 다시 아키텍처로 정의해야 하는지를 설명하고 이후 12개 장의 필요성을 만든다.**  
> 원칙: **설명보다 TEXT Architecture가 먼저 나오고, 그림이 Story를 이끈다.**

---

# 0. 이 장의 이야기

이 장은 기술 구성요소를 설명하는 장이 아니다.

이 장에서 먼저 답해야 할 질문은 하나다.

```text
"이미 Source도 있고,
 Module도 있고,
 서버도 있고,
 실행되는 시스템도 있는데

왜 다시
Architecture를 정의해야 하는가?"
```

이 질문에 대한 답을 찾기 위해
PDMG를 세 가지 모습으로 나누어 본다.

```text
첫 번째 모습
"문서 속 PDMG"

두 번째 모습
"Source 속 PDMG"

세 번째 모습
"Runtime에서 실제 움직이는 PDMG"
```

그리고 이 세 모습이 하나로 연결되지 않으면
Architecture는 완성되지 않는다.

---

# 1. 출발점 — 시스템은 존재하지만 Architecture는 보이지 않는다

## FIG-01-01. 기존에 보이던 PDMG

```text
┌───────────────────────────────────────────────┐
│                PDMG System                    │
│                                               │
│  pdmg-ui                                      │
│  pdmg-jwt                                     │
│  pdmg-fw                                      │
│  pdmg-service                                 │
│  pdmg-om                                      │
│                                               │
│  Java                                         │
│  Spring Boot                                  │
│  MyBatis                                      │
│  Oracle                                       │
└───────────────────────────────────────────────┘
```

이 그림만 보면 시스템은 이미 정의되어 있는 것처럼 보인다.

하지만 여기에는 중요한 질문이 빠져 있다.

```text
pdmg-fw는
별도 서버인가?

pdmg-service와
같은 JVM인가?

pdmg-jwt는
어떤 방식으로
Business Runtime과 연결되는가?

거래 하나가
어떤 Thread에서 수행되는가?

Transaction은
어디에서 시작되는가?

Timeout이 발생하면
DB 작업도 끝난 것인가?

ServiceId는
어떤 코드까지 연결되는가?

배포된 WAR는
어느 JVM / Host에서 실행되는가?

장애가 발생하면
어디까지 영향이 전파되는가?
```

이 질문에 답하지 못한다면
우리가 알고 있는 것은 **구성요소 목록**이지
**Architecture**가 아니다.

---

# 2. 문제의 본질 — "무엇이 있다"와 "어떻게 동작한다"는 다르다

## FIG-01-02. Inventory와 Architecture의 차이

```text
Inventory
─────────────────────────────
pdmg-ui
pdmg-jwt
pdmg-fw
pdmg-service
Oracle
Tomcat
MyBatis


Architecture
─────────────────────────────
User
 ↓
UI
 ↓
Authentication
 ↓
Application Runtime
 ↓
Framework Control
 ↓
Business Execution
 ↓
Data Access
 ↓
DB
 ↓
Response / Evidence
```

Inventory는 다음을 말한다.

```text
"무엇이 존재하는가?"
```

Architecture는 다음을 말해야 한다.

```text
"누가 무엇을 책임지는가?"
        +
"무엇과 무엇이 연결되는가?"
        +
"어떤 규칙으로 실행되는가?"
        +
"어디에서 장애가 격리되는가?"
        +
"실제 Runtime이 설계와 일치하는가?"
```

---

# 3. 첫 번째 전환 — Module 중심 설명에서 Responsibility 중심 설명으로

## FIG-01-03. Module View

```text
PDMG
│
├─ pdmg-ui
├─ pdmg-jwt
├─ pdmg-fw
├─ pdmg-service
└─ pdmg-om
```

이 구조는 Build 관점에서는 유효하다.

하지만 Architecture 관점에서는 충분하지 않다.

왜냐하면 다음 등식이 성립하지 않기 때문이다.

```text
Module
≠
Application

Module
≠
Process

Module
≠
Spring Context

Module
≠
Logical Technical Node

Module
≠
Physical Server
```

따라서 PDMG를 다시 읽어야 한다.

---

# 4. PDMG를 책임 관점으로 다시 읽는다

## FIG-01-04. Module → Responsibility

```text
pdmg-ui
   ↓
UI Delivery Responsibility

pdmg-jwt
   ↓
Authentication / Token Responsibility

pdmg-service
   ↓
Business Application Responsibility

pdmg-fw
   ↓
Framework Runtime Control Responsibility

pdmg-om
   ↓
Operations Responsibility
   [CURRENT DETAIL UNKNOWN]
```

이제 구조가 조금 달라진다.

```text
Build Module
      ↓
Responsibility
      ↓
Runtime Relationship
      ↓
Architecture
```

---

# 5. 두 번째 전환 — Application을 Server로 바로 내려보내지 않는다

과거 시스템 문서에서는
Application 이름과 Server 이름이 거의 같은 의미로 사용되는 경우가 많다.

하지만 PDMG를 정확히 정의하려면
중간 계층이 필요하다.

## FIG-01-05. Responsibility → Logical → Physical

```text
Business / Application Responsibility
                ↓
        Technical Capability
                ↓
      Logical Technical Node
                ↓
         Runtime Type
                ↓
       Physical Resource
                ↓
        Runtime Evidence
```

즉 다음과 같이 바로 연결하면 안 된다.

```text
pdmg-service
     ↓
WAS01

X
```

그 사이에는 다음 질문이 있어야 한다.

```text
pdmg-service가
어떤 Runtime Responsibility를 가지는가?

어떤 Framework Capability를 필요로 하는가?

어떤 State를 가지는가?

어떤 Scale Unit을 가지는가?

어떤 Failure Domain을 가지는가?
```

---

# 6. PDMG의 실제 중심은 Module이 아니라 Runtime이다

## FIG-01-06. PDMG Runtime 중심 구조

```text
User / Browser
      ↓
pdmg-ui
      ↓
pdmg-jwt
      ↓
pdmg-service
      │
      ├───────────────┐
      │               │
      │           pdmg-fw
      │               │
      │        Runtime Control
      │               │
      └───────┬───────┘
              ↓
          Handler
              ↓
           Facade
              ↓
           Service
              ↓
             DAO
              ↓
           Mapper
              ↓
             DB
```

여기서 중요한 것은:

```text
pdmg-service
+
pdmg-fw
```

가 반드시 서로 다른 서버라는 의미가 아니라는 점이다.

현재 PDMG Source 관점에서는
둘이 같은 Spring Application Runtime 안에서 협력할 수 있다.

따라서:

```text
Module Boundary
≠
Runtime Process Boundary
```

라는 원칙이 필요하다.

---

# 7. 세 번째 전환 — Source 구조와 Runtime 구조를 분리한다

Source를 보면 다음과 같은 계층이 보인다.

## FIG-01-07. Source View

```text
Controller / Handler
        ↓
Facade
        ↓
Service
        ↓
DAO
        ↓
Mapper
        ↓
DB
```

하지만 실제 Runtime에서는
그 앞에 훨씬 많은 Mechanism이 존재한다.

## FIG-01-08. Runtime View

```text
HTTP Request
   ↓
DefaultFilter
   ↓
SecurityFilterChain
   ↓
DispatcherServlet
   ↓
Interceptor
   ↓
OnlineTransactionController
   ↓
TcfFacade
   ↓
OnlineTimeoutExecutor
   ↓
Worker Thread
   ↓
TransactionTemplate
   ↓
TransactionDispatcher
   ↓
TransactionHandler
   ↓
Facade
   ↓
Service
   ↓
DAO / Mapper
   ↓
DB
```

Source Layer Diagram과
Runtime Diagram은 서로 다른 질문에 답한다.

```text
Source Layer
"코드는 어떤 책임구조로 나뉘는가?"

Runtime
"거래 한 건이 실제로 어떤 순서로 실행되는가?"
```

---

# 8. Architecture를 다시 정의해야 하는 첫 번째 이유
# "Source만 봐서는 Runtime을 알 수 없다"

예를 들어 다음 코드를 본다고 하자.

```text
Controller
 ↓
Facade
 ↓
Service
 ↓
DAO
```

이 구조만으로는 다음을 알 수 없다.

```text
어느 Thread에서 실행되는가?

Transaction은 누가 시작하는가?

Timeout은 어디서 발생하는가?

Context는 어떻게 전달되는가?

SecurityFilter는 언제 실행되는가?

DB Connection은 언제 획득되는가?
```

따라서 Architecture는
Source Structure보다 한 단계 더 넓어야 한다.

---

# 9. Architecture를 다시 정의해야 하는 두 번째 이유
# "Timeout과 Transaction은 같은 경계가 아니다"

## FIG-01-09. Request Thread와 Worker Thread

```text
════════════ Request Thread ════════════

HTTP
 ↓
Filter
 ↓
Security
 ↓
MVC
 ↓
Controller
 ↓
TcfFacade
 ↓
Future.get(timeout)
        │
        │ submit
        ▼

════════════ Worker Thread ═════════════

Context Install
 ↓
TransactionTemplate BEGIN
 ↓
Dispatcher
 ↓
Handler
 ↓
Facade
 ↓
Service
 ↓
DAO
 ↓
DB
 ↓
Deadline
 ↓
COMMIT / ROLLBACK
```

현재 PDMG의 중요한 특성 중 하나는
Request Thread와 Business Worker Thread가 분리된다는 점이다.

이 구조는 단순 구현 세부사항이 아니라
Architecture다.

왜냐하면 장애 의미가 달라지기 때문이다.

---

# 10. HTTP Timeout은 DB 종료가 아니다

## FIG-01-10. Timeout의 오해

```text
Client
  ↓
HTTP Request
  ↓
Future.get(5000ms)
  ↓
TIMEOUT
  ↓
504
```

여기까지만 보면
거래가 끝난 것처럼 보인다.

하지만 실제로는:

```text
HTTP 504
   ≠
Worker Thread 종료

Worker cancel(true)
   ≠
JDBC Statement Cancel 보장

JDBC Interrupt
   ≠
DB Session Kill

HTTP Response 종료
   ≠
Transaction Rollback 완료
```

따라서 Timeout은
단순 설정값이 아니라
Runtime Architecture의 핵심이다.

---

# 11. Architecture를 다시 정의해야 하는 세 번째 이유
# "Authentication과 Authorization은 다르다"

## FIG-01-11. Security Flow

```text
User
 ↓
Login / SSO
 ↓
pdmg-jwt
 ↓
Access Token
 ↓
PDMG Runtime
 ↓
Token Verification
 ↓
Trusted Principal
 ↓
Business Authorization
 ↓
ServiceId / Resource
```

Token을 검증했다고 해서
업무권한까지 검증된 것은 아니다.

```text
Authentication
≠
Authorization
```

그리고 현재 PDMG에는
더 중요한 Security GAP가 존재한다.

---

# 12. Security의 Critical GAP가 Architecture 문제인 이유

## FIG-01-12. JWT Current GAP

```text
pdmg-jwt
   │
   │ RS256 Issue
   ▼
JWT
   │
   ▼
pdmg-fw
   │
   │ HMAC Secret Verify Path
   ▼
Business Runtime

        [CRITICAL GAP]
```

이 문제는 단순 코드 버그가 아니다.

왜냐하면 다음 Architecture 질문과 연결되기 때문이다.

```text
누가 Token Issuer인가?

누가 Verifier인가?

Key는 어디에 존재하는가?

Public Key는 어떻게 배포되는가?

Multi-instance에서 Key는 동일한가?

Restart 후에도 Token 검증이 가능한가?

DR 센터에서도 같은 Trust가 유지되는가?
```

즉 Security 역시 Architecture Definition의 일부다.

---

# 13. Architecture를 다시 정의해야 하는 네 번째 이유
# "ServiceId가 Business와 Runtime을 연결하는 축이기 때문이다"

PDMG에는 매우 중요한 식별자가 있다.

```text
ServiceId
```

## FIG-01-13. ServiceId Backbone

```text
Business
  ↓
Program
  ↓
ServiceId
  ↓
Dispatcher
  ↓
Handler
  ↓
Facade
  ↓
Service
  ↓
DAO
  ↓
Mapper
  ↓
SqlId
  ↓
Table
```

예:

```text
mgcoa9001S0
```

이 식별자는 단순 API 코드가 아니다.

PDMG Architecture에서는 다음을 연결하는 축이 된다.

```text
Business
+
Source
+
Data
+
Runtime
+
Logging
+
Evidence
```

---

# 14. ServiceId가 Architecture Trace의 중심이 된다

## FIG-01-14. ServiceId → Runtime Evidence

```text
ServiceId
   ↓
Handler
   ↓
Business Logic
   ↓
SqlId
   ↓
DB
   ↓
GUID
   ↓
Elapsed
   ↓
ErrorCode
   ↓
Runtime Evidence
```

그리고 최종적으로는 다음까지 연결되어야 한다.

```text
ServiceId
   ↓
Artifact
   ↓
DeploymentId
   ↓
JVM
   ↓
Host
   ↓
Metric / Log / Trace
```

이 연결이 없으면
운영 중 장애가 발생했을 때
다음 질문에 답하기 어렵다.

```text
"이 ServiceId는
어느 WAR,
어느 JVM,
어느 Host에서
실행됐는가?"
```

---

# 15. Architecture를 다시 정의해야 하는 다섯 번째 이유
# "Application과 Physical Infrastructure 사이의 연결이 빠져 있기 때문이다"

## FIG-01-15. Logical → Physical → Runtime

```text
Application
   ↓
Logical Technical Node
   ↓
Environment
   ↓
Center
   ↓
Host / VM
   ↓
JVM
   ↓
WAR
   ↓
Runtime Instance
   ↓
Evidence
```

현재 Architecture Definition에서
가장 중요한 Physical GAP 중 하나는:

```text
Artifact
   ↓
DeploymentId
   ↓
WAR
   ↓
JVM
   ↓
VM
   ↓
Host
```

의 전수 Mapping이다.

Architecture는 이 Mapping을 요구해야 한다.

---

# 16. Physical Architecture의 기본 경로

## FIG-01-16. Working Physical Path

```text
User
 ↓
GSLB
 ↓
L4
 ↓
Apache WEB
 ↓
Tomcat JVM
 ↓
PDMG WAR
 ↓
Spring Runtime
 ↓
Hikari
 ↓
MyBatis
 ↓
JDBC
 ↓
RDW / DB
```

이 그림에서 중요한 것은
제품 이름 자체가 아니다.

중요한 것은
각 단계의 책임과 장애 경계다.

---

# 17. Server, VM, JVM, WAR를 같은 것으로 보면 안 된다

## FIG-01-17. Physical Boundary

```text
Physical Server
   ↓
Virtual Machine
   ↓
Operating System
   ↓
Runtime Process
   ↓
JVM
   ↓
WAR / Application
```

따라서 다음은 틀린 표현이다.

```text
WAS Server
=
Tomcat JVM
=
WAR

X
```

이 구분이 필요한 이유는
Capacity와 HA 때문이다.

---

# 18. Architecture를 다시 정의해야 하는 여섯 번째 이유
# "Scale과 Failure Domain을 설명해야 하기 때문이다"

## FIG-01-18. Scale Unit

```text
User Load
   ↓
WEB
   ↓
WAS VM
   ↓
JVM
   ↓
Worker Pool
   ↓
Hikari Pool
   ↓
DB Session
```

각 계층은 서로 다른 Scale Unit을 가진다.

```text
Tomcat Thread
≠
Worker Thread

Worker Thread
≠
Hikari Connection

Hikari Connection
≠
DB Session Capacity
```

Capacity는 하나의 숫자로 정의할 수 없다.

---

# 19. Capacity는 Chain이다

## FIG-01-19. Capacity Chain

```text
Tomcat Request Thread
        ↓
PDMG Worker Pool
        ↓
Worker Queue
        ↓
Hikari Connection Pool
        ↓
DB Session
        ↓
CPU / IO / Lock
```

한 곳이 느려지면
앞 단계로 역전파된다.

---

# 20. DB가 느리면 어디까지 문제가 올라오는가

## FIG-01-20. Saturation Propagation

```text
DB Slow
  ↓
SQL Wait
  ↓
Hikari Connection 점유
  ↓
Worker Thread 점유
  ↓
Worker Queue 증가
  ↓
Request 대기
  ↓
Timeout
  ↓
HTTP 504
```

이 흐름이 보이지 않으면
성능문제를 단순히
"WAS Thread 부족"으로 오판할 수 있다.

---

# 21. Architecture를 다시 정의해야 하는 일곱 번째 이유
# "Data Platform과 PDMG의 책임을 분리해야 하기 때문이다"

## FIG-01-21. PDMG Data Access

```text
PDMG
 ↓
Service
 ↓
DAO
 ↓
Mapper
 ↓
SQL
 ↓
RDW / DB
```

NSIGHT Target에서는
데이터 역할이 더 넓어진다.

```text
RDW
= Operational / Near Real-time

ADW
= Analytical / Heavy Query
```

하지만 이 Target 구조를
PDMG Current로 자동 해석하면 안 된다.

---

# 22. PDMG Current와 NSIGHT Target을 분리한다

## FIG-01-22. Current vs Target

```text
┌──────────────────────────────┐
│ PDMG CURRENT                 │
│                              │
│ UI                           │
│ JWT                          │
│ Framework / TCF              │
│ Worker / Transaction         │
│ Business Layer               │
│ MyBatis / JDBC               │
│ RDW / DB                     │
└───────────────┬──────────────┘
                │ compare
                ▼
┌──────────────────────────────┐
│ NSIGHT TARGET                │
│                              │
│ Scalable                     │
│ Resilient                    │
│ Data-Centric                 │
│ RDW / ADW separation         │
│ Event / CDC                  │
│ Standard Interface           │
│ HA / DR                      │
│ Observability / Evidence     │
└──────────────────────────────┘
```

핵심 원칙은 단순하다.

```text
PDMG AS-IS
≠
NSIGHT TO-BE
```

---

# 23. Target을 Current로 쓰지 않는다

다음은 금지한다.

## FIG-01-23. 잘못된 승격

```text
NSIGHT에
Kafka가 필요하다
      ↓
PDMG Current에도
Kafka가 있다

X
```

또는:

```text
NSIGHT Application Group
MP
 ↓
PDMG Source Prefix
mg

그러므로
MP = mg

X
```

Current와 Target 사이에는
Mapping과 Decision이 필요하다.

---

# 24. Architecture를 다시 정의해야 하는 여덟 번째 이유
# "Interface를 연결선이 아니라 Contract로 정의해야 하기 때문이다"

## FIG-01-24. Interface as Contract

```text
Business Interaction Need
        ↓
Interface Type
        ↓
Source / Target
        ↓
Contract
        ↓
Protocol / Schema
        ↓
Timeout / Retry
        ↓
Security
        ↓
Operations / Evidence
```

연결선 하나로는
Interface Architecture를 설명할 수 없다.

---

# 25. Cross-System Direct DB는 왜 문제가 되는가

## FIG-01-25. 정상 / 금지 Interface

```text
정상

System A
   ↓
Approved API / Data Contract
   ↓
System B
```

```text
금지

System A
   ↓
System B DB Direct DML

X
```

Direct DB는 빠르게 보일 수 있지만
장기적으로 다음 문제를 만든다.

```text
강결합
변경전파
Ownership 불명확
보안통제 어려움
장애영향 확대
```

---

# 26. Architecture를 다시 정의해야 하는 아홉 번째 이유
# "운영이 Architecture의 마지막이 아니라 일부이기 때문이다"

## FIG-01-26. Runtime → Operations

```text
Application Runtime
   ↓
Metric
   +
Log
   +
Trace
   ↓
ServiceId / GUID
   ↓
JVM / Host
   ↓
Alert
   ↓
Runbook
   ↓
Evidence
```

운영자가 다음 질문에 답할 수 없다면
Architecture는 아직 Runtime까지 닫히지 않은 것이다.

```text
어떤 거래인가?
어떤 ServiceId인가?
어떤 Thread에서 실행됐는가?
어떤 SQL이 느렸는가?
어느 JVM인가?
어느 Host인가?
어떤 Artifact 버전인가?
```

---

# 27. Logging과 Runtime Evidence는 다르다

## FIG-01-27. Log vs Evidence

```text
Logging
= "무슨 일이 기록되었는가?"

Runtime Evidence
= "설계한 Architecture Rule이
   실제 Runtime에서도 지켜졌음을
   무엇으로 증명할 것인가?"
```

예를 들어:

```text
로그에
ServiceId가 있음
      ↓
Architecture PASS

X
```

아니다.

다음과 같이 연결되어야 한다.

```text
Architecture Rule
      ↓
Source
      ↓
Deployment
      ↓
Runtime
      ↓
Metric / Trace / Test
      ↓
Evidence ID
      ↓
PASS
```

---

# 28. Architecture를 다시 정의해야 하는 열 번째 이유
# "변경될 수 있어야 하기 때문이다"

Architecture는 한 번 그리고 끝나는 그림이 아니다.

## FIG-01-28. Architecture Closed Loop

```text
Document
   ↓
Model
   ↓
Code
   ↓
Test
   ↓
Runtime Evidence
   ↓
Drift
   ↓
GAP
   ↓
ADR
   ↓
Architecture Update
   ↓
New Baseline
```

이 Closed Loop가 없으면
문서는 시간이 지날수록 Source와 멀어진다.

---

# 29. 결국 PDMG Architecture는 무엇을 연결해야 하는가

## FIG-01-29. PDMG Complete Trace

```text
Business Requirement
       ↓
Application
       ↓
Program
       ↓
ServiceId
       ↓
Handler
       ↓
Facade
       ↓
Service
       ↓
DAO
       ↓
Mapper / SqlId
       ↓
Table / View
       ↓
Source Commit
       ↓
BuildId
       ↓
Artifact Hash
       ↓
DeploymentId
       ↓
JVM / Host
       ↓
GUID / TraceId
       ↓
Runtime Evidence
```

이 연결이 만들어지면
Architecture는 문서가 아니라
**운영 가능한 지식체계**가 된다.

---

# 30. 이 정의서가 필요한 이유를 한 장으로 요약한다

## FIG-01-30. Why Rebuild Architecture Definition

```text
기존
────────────────────────────────────────────
Module
Server
Source
Config
운영자료
각각 존재
────────────────────────────────────────────
          │
          │ 연결 부족
          ▼
"실제 Architecture가 무엇인지
 하나의 그림으로 설명하기 어려움"


재정의
────────────────────────────────────────────
Business
 ↓
Application
 ↓
Logical
 ↓
Physical
 ↓
Mechanism
 ↓
Runtime
 ↓
Evidence
────────────────────────────────────────────
          │
          ▼
"설계와 실제 구현을
 연결하고 검증할 수 있음"
```

---

# 31. 제1장의 핵심 Story

이 장의 이야기는 다음과 같다.

## FIG-01-31. Story Flow

```text
시스템은 이미 존재한다
        ↓
하지만
구성요소만 보인다
        ↓
Source만으로
Runtime은 보이지 않는다
        ↓
Module과 Server의
경계도 혼재한다
        ↓
Security / Timeout / Transaction /
Data / HA / Operations가
하나의 구조로 연결되지 않는다
        ↓
따라서
PDMG를 다시 Architecture로 읽어야 한다
        ↓
Business
→ Application
→ Logical
→ Physical
→ Mechanism
→ Runtime
→ Evidence
        ↓
Architecture Baseline
```

---

# 32. 이 장의 정상패턴

## FIG-01-32. Normal Architecture Thinking

```text
Current Fact
   ↓
Responsibility
   ↓
Boundary
   ↓
Runtime
   ↓
Physical
   ↓
Evidence
   ↓
PASS / GAP
```

---

# 33. 이 장의 금지패턴

## FIG-01-33. Forbidden Thinking

```text
문서에 적혀 있다
       ↓
Fact

X
```

```text
Module 이름
       ↓
Server 이름

X
```

```text
Target Architecture
       ↓
Current Architecture

X
```

```text
HTTP 504
       ↓
DB 작업 종료

X
```

```text
로그가 있다
       ↓
Runtime Evidence PASS

X
```

---

# 34. Architecture Decision — Baseline 운영방식

PDMG를 어떤 방식으로 관리할 것인가?

## FIG-01-34. 주안 / 대안

```text
[주안]

Evidence-backed Architecture Baseline

Architecture
 ↓
Source / Config
 ↓
Deployment
 ↓
Runtime Evidence
 ↓
PASS / GAP
 ↓
ADR / Baseline


[대안]

Document-centered Baseline

Architecture Document
 ↓
Review
 ↓
Approval
```

### 주안의 장점

```text
Source와 Architecture 정합 가능
Runtime과 Architecture 정합 가능
Drift 탐지 가능
PASS/GAP 객관화 가능
변경영향 추적 가능
```

### 주안의 단점

```text
Architecture Model 필요
Rule 정의 필요
Evidence 자동수집 필요
초기 구축비용 증가
```

### 대안의 장점

```text
초기 도입이 빠름
문서 작성이 단순함
운영조직 변화가 적음
```

### 대안의 단점

```text
Source Drift를 놓치기 쉬움
실제 Deployment와 분리됨
Runtime 검증이 약함
문서 노후화 위험
```

### 권고

```text
주안
=
Evidence-backed Architecture Baseline
```

---

# 35. Current PDMG에서 이미 확인된 강한 Evidence

## FIG-01-35. Strong Current Facts

```text
PDMG Current
│
├─ pdmg-ui
├─ pdmg-jwt
├─ pdmg-fw
├─ pdmg-service
│
├─ Java 21
├─ Spring Boot 3.5.14
├─ Gradle Multi-project
│
├─ ServiceId Routing
├─ Handler Registry
├─ TCF
├─ Worker / Timeout
├─ TransactionTemplate
│
├─ Hikari
├─ MyBatis
├─ JDBC
│
├─ hdr_nhnis
├─ GUID
├─ ServiceContext
└─ Runtime Logging Clues
```

이 항목들은
PDMG를 다시 Architecture로 정의할 수 있는
출발점이 된다.

---

# 36. 아직 Architecture가 닫히지 않은 영역

## FIG-01-36. Major GAP Map

```text
PDMG Architecture
│
├─ Security
│   ├─ RS256 Issuer
│   └─ HMAC Verifier
│      [CRITICAL GAP]
│
├─ Identity
│   └─ Principal ↔ Business User
│      [CRITICAL GAP]
│
├─ Deployment
│   └─ Artifact → Host/JVM/WAR
│      [GAP]
│
├─ Data
│   └─ RDW/ADW actual mapping
│      [OPEN]
│
├─ Integration
│   └─ External Interface Inventory
│      [OPEN]
│
├─ Operations
│   └─ pdmg-om current role
│      [UNKNOWN]
│
├─ Capacity
│   └─ Final Load-tested values
│      [OPEN]
│
└─ Runtime Evidence
    └─ automated evidence chain
       [GAP]
```

---

# 37. Architecture Definition PASS와 Current PASS는 다르다

## FIG-01-37. Two Different PASS

```text
Architecture Definition PASS
=
구조 / 원칙 / 경계 / Rule이
명확하게 정의되어 있음


Current Implementation PASS
=
Source / Config / Deployment / Runtime이
그 Architecture를 실제로 지키고 있음
```

따라서 다음이 가능하다.

```text
Architecture Definition
PASS

Current Implementation
GAP
```

이것은 모순이 아니다.

오히려 정확한 Architecture Assessment다.

---

# 38. 제1장 현재 판정

## FIG-01-38. Chapter Assessment

```text
Architecture Definition
      ↓
CONDITIONAL PASS

Reason
- PDMG를 다시 정의해야 하는 이유 명확
- Current/Target 분리원칙 명확
- Runtime/Evidence 방향 명확
- 이후 장의 Drill-down 구조 명확

BUT

Current PDMG
      ↓
PARTIAL / GAP
```

### 판정표

| 평가항목 | 판정 | 근거 |
|---|---|---|
| Architecture 재정의 필요성 | PASS | Module/Source/Runtime 간 단절 확인 |
| Current vs Target 구분 | PASS | PDMG Current ≠ NSIGHT Target |
| Runtime 중심 구조 | PASS | Filter→Worker→TX→DB 구조 확인 |
| Security 정합 | GAP | RS256 Issuer ↔ HMAC Verifier |
| Physical Trace | GAP | Artifact→JVM/Host Mapping 미완료 |
| Runtime Evidence | PARTIAL | GUID/Log은 있으나 완전한 Evidence Chain 미완료 |
| Final Baseline | OPEN | HG90 조건 미완료 |

---

# 39. 제1장에서 다음 장으로 넘어가는 이유

제1장에서
"왜 다시 정의해야 하는가"를 확인했다.

이제 다음 질문이 생긴다.

```text
"그러면
기존 정보계 Architecture는
어떤 관점에서 바뀌어야 하는가?"
```

즉 다음 장의 주제는
단순 기술 변경이 아니다.

## FIG-01-39. 1장 → 2장

```text
제1장
왜 다시 짓는가
        ↓
"기존 설명방식으로는
 Runtime Architecture를 설명할 수 없다"
        ↓
제2장
정보계 패러다임의 전환
        ↓
Application 중심
        ↓
Platform / Runtime / Data / Evidence 중심
```

---

# 40. 제1장 최종 결론

## FIG-01-40. Final Story

```text
PDMG는 이미 존재한다
        ↓
하지만
Architecture는 자동으로 존재하지 않는다
        ↓
Module
Source
Server
Runtime
Security
Data
Operations
Evidence
        ↓
이들을 연결해야 한다
        ↓
Business
→ Application
→ Logical
→ Physical
→ Mechanism
→ Runtime
→ Evidence
        ↓
PDMG Architecture Baseline
```

제1장의 결론은 다음 한 문장으로 정리할 수 있다.

> **PDMG 아키텍처를 다시 정의하는 이유는 새로운 시스템을 상상하기 위해서가 아니라, 이미 존재하는 Source·Runtime·Infrastructure·Security·Data·Operations를 하나의 책임과 Evidence 체계로 연결하여 실제 동작하는 Architecture를 보이게 만들기 위해서다.**

---

# 41. 다음 장

```text
제2장
정보계 패러다임의 전환

핵심 질문
"Application 중심으로 설명하던 정보계를
어떻게
Runtime / Platform / Data / Evidence 중심으로
전환할 것인가?"
```
