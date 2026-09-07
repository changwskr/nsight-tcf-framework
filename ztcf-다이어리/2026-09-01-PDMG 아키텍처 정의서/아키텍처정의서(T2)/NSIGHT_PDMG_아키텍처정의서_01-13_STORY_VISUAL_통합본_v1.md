# NSIGHT PDMG 아키텍처 정의서 — 01~13 Story Visual 통합본
## TEXT Architecture-First / Story-First / Top-down → Drill-down / Evidence-First

> 상태: `[WORKING INTEGRATED BASELINE-2026-09-01]`

# 전체 Story

```text
01 왜 다시 짓는가
 ↓
02 정보계 패러다임의 전환
 ↓
03 아키텍처 6단계 수립 방법론
 ↓
04 Big Picture
 ↓
05 논리 아키텍처
 ↓
06 물리 아키텍처
 ↓
07 DR 센터 활용 전략
 ↓
08 메커니즘
 ↓
09 런타임 서비스
 ↓
10 데이터플랫폼
 ↓
11 마케팅플랫폼
 ↓
12 BI 포탈
 ↓
13 표준화와 10년 지속 가능성
 ↓
HG90 Evidence-backed Baseline
```

---

<!-- CHAPTER 01: 01_NSIGHT_PDMG_아키텍처정의서_왜_다시_짓는가_STORY_VISUAL_v1.md -->

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



---

<!-- CHAPTER 02: 02_NSIGHT_PDMG_아키텍처정의서_정보계_패러다임의_전환_STORY_VISUAL_v1.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제2장. 정보계 패러다임의 전환
## Application 중심에서 Runtime·Platform·Data·Evidence 중심으로
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **1장에서 확인한 단편적 시스템 설명을, 책임과 Runtime이 연결된 정보계 Architecture 관점으로 전환한다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제1장에서 넘어온 질문
     ↓
1장에서 확인한 단편적 시스템 설명을, 책임과 Runtime이 연결된 정보계 Architecture 관점으로 전환한다.
     ↓
이 장이 답해야 할 질문
     ↓
Application 중심에서 Runtime·Platform·Data·Evidence 중심으로
```

이 장의 핵심 원칙은 다음과 같다.

- Module Boundary ≠ Process Boundary ≠ Spring Context Boundary ≠ Physical Server.
- 제품명보다 Technical Capability와 Responsibility를 먼저 정의한다.
- PDMG Current와 NSIGHT Target을 같은 것으로 쓰지 않는다.
- 온라인 FAST와 분석 DEEP Workload를 분리한다.

---

# 1. 과거 정보계는 Application을 중심으로 설명했다

## FIG-02-01. 과거 정보계는 Application을 중심으로 설명했다

```text
과거 관점

User
 ↓
Application
 ↓
Database

Application
= 화면
+ 업무 Java
+ SQL
```

이 구조는 업무기능을 설명하는 데는 충분했지만, 대규모 정보계에서 중요한
Thread, Transaction, Security, Interface, Deployment, Observability를 한 구조로 설명하기 어렵다.

---

# 2. PDMG를 보면 Application만으로는 설명되지 않는 것이 보인다

## FIG-02-02. PDMG를 보면 Application만으로는 설명되지 않는 것이 보인다

```text
PDMG

pdmg-ui
   ↓
pdmg-jwt
   ↓
pdmg-service
   │
   ├─ pdmg-fw
   │   ├─ Filter
   │   ├─ Context
   │   ├─ TCF
   │   ├─ Timeout
   │   ├─ Transaction
   │   └─ Logging
   │
   └─ Business
       ↓
      Data
```

PDMG는 이미 Application Source와 Runtime Platform의 성격을 동시에 가지고 있다.

---

# 3. 첫 번째 전환 — Application에서 Responsibility로

## FIG-02-03. 첫 번째 전환 — Application에서 Responsibility로

```text
Application 이름
   ↓
"무슨 책임을 가지는가?"
   ↓
UI Delivery
Authentication
Business Runtime
Framework Control
Data Access
Operations
```

---

# 4. 두 번째 전환 — Module에서 Runtime Boundary로

## FIG-02-04. 두 번째 전환 — Module에서 Runtime Boundary로

```text
Build Module
pdmg-service
pdmg-fw
     ↓
same Spring Runtime 가능
     ↓
Module Boundary
≠
Process Boundary
≠
JVM Boundary
```

---

# 5. 세 번째 전환 — 제품에서 Capability로

## FIG-02-05. 세 번째 전환 — 제품에서 Capability로

```text
Tomcat
 ↓
"무슨 역할?"
 ↓
Application Runtime

HikariCP
 ↓
"무슨 역할?"
 ↓
Connection Pool

MyBatis
 ↓
"무슨 역할?"
 ↓
SQL Mapping
```

제품은 바뀔 수 있지만 Capability와 책임은 Architecture의 더 안정적인 축이다.

---

# 6. 네 번째 전환 — 온라인 Application에서 FAST/DEEP Workload로

## FIG-02-06. 네 번째 전환 — 온라인 Application에서 FAST/DEEP Workload로

```text
PDMG Current
ONLINE / FAST
 HTTP
 Worker
 TX
 RDW

NSIGHT Broader
FAST
 Event / CDC

DEEP
 ETL / ADW / BI
```

---

# 7. 다섯 번째 전환 — Server 중심에서 Logical→Physical Mapping으로

## FIG-02-07. 다섯 번째 전환 — Server 중심에서 Logical→Physical Mapping으로

```text
Application
 ↓
Logical Technical Node
 ↓
Runtime Type
 ↓
Environment
 ↓
VM / JVM / WAR
 ↓
Host / Center
```

---

# 8. 여섯 번째 전환 — 로그 중심에서 Runtime Evidence로

## FIG-02-08. 여섯 번째 전환 — 로그 중심에서 Runtime Evidence로

```text
Log
 ↓
무슨 일이 있었나

            VS

Architecture Rule
 ↓
Metric / Trace / Test
 ↓
설계가 지켜졌는가
```

---

# 9. 일곱 번째 전환 — 수동 문서에서 Closed Loop로

## FIG-02-09. 일곱 번째 전환 — 수동 문서에서 Closed Loop로

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
ADR
 ↓
New Baseline
```

---

# 10. PDMG Current와 NSIGHT Target의 관계

## FIG-02-10. PDMG Current와 NSIGHT Target의 관계

```text
PDMG CURRENT
UI / JWT / TCF / Worker / TX / DB
        │
        │ compare / map
        ▼
NSIGHT TARGET
Scalable / Resilient / Data-Centric
RDW/ADW / Event/CDC / HA/DR / Evidence
```

---

# 11. 패러다임 전환의 최종 형태

## FIG-02-11. 패러다임 전환의 최종 형태

```text
Application-centric
     ↓
Responsibility-centric
     ↓
Runtime-aware
     ↓
Data-aware
     ↓
Operation-aware
     ↓
Evidence-backed
```

---

# 12. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-02-12. Normal Pattern

```text
Business Responsibility
 ↓
Application
 ↓
Technical Capability
 ↓
Runtime / Physical
 ↓
Operations / Evidence
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 13. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-02-13. Forbidden Pattern

```text
Module = Server               X
Product = Architecture Node     X
PDMG Current = NSIGHT Target    X
Online = Analytics Runtime      X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 14. Architecture Decision — PDMG Architecture 범위

## FIG-02-14. 주안과 대안

```text
[주안]
Application + Runtime + Operations + Evidence

        VS

[대안]
Application Source 중심
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | Application + Runtime + Operations + Evidence | Application Source 중심 |
| 장점 | • 운영/보안/성능까지 연결<br>• Current/Target 차이 설명 가능<br>• Runtime 검증 가능 | • 개발자 Source 이해가 빠름<br>• 초기 문서 단순 |
| 단점 | • 문서 범위 증가<br>• 모델/책임 분류 필요 | • Physical/Runtime 설명 한계<br>• 운영/보안 단절 |
| 권고 | **주안 채택** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 15. Current PDMG GAP

## FIG-02-15. GAP Map

```text
Current PDMG
│
├─ Module→Runtime/Physical 전수 Mapping 미완료
├─ Event/CDC/ETL Current 범위 일부 OPEN
├─ pdmg-om Current 역할 UNKNOWN
└─ Runtime Evidence 자동화 미완료
```

- `[GAP/OPEN]` Module→Runtime/Physical 전수 Mapping 미완료
- `[GAP/OPEN]` Event/CDC/ETL Current 범위 일부 OPEN
- `[GAP/OPEN]` pdmg-om Current 역할 UNKNOWN
- `[GAP/OPEN]` Runtime Evidence 자동화 미완료

---

# 16. 제2장 Architecture 판정

## FIG-02-16. Assessment

```text
Architecture Definition
      ↓
PASS

Current PDMG Conformance
      ↓
PARTIAL

Runtime Evidence
      ↓
MEDIUM
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| Responsibility 전환 | PASS | Application/Framework/Data 책임 구분 |
| Runtime 인식 | PASS | Thread/TX/Timeout 구조 확인 |
| Current/Target 분리 | PASS | PDMG≠NSIGHT 전체 |
| Operations | PARTIAL | pdmg-om/운영 구현 상세 OPEN |
| Evidence | PARTIAL | 자동화 미완료 |

---

# 17. 원천 정의서 Trace

## FIG-02-17. Source Trace

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
| 01 Executive | Story/Drill-down/Current-Target 근거 |
| 02 System Context | Story/Drill-down/Current-Target 근거 |
| 03 Application | Story/Drill-down/Current-Target 근거 |
| 04 Logical | Story/Drill-down/Current-Target 근거 |
| 18 Integrated | Story/Drill-down/Current-Target 근거 |

---

# 18. 다음 장으로 넘어가는 이유

## FIG-02-18. 2장 → 3

```text
제2장
정보계 패러다임의 전환
      ↓
"이 새로운 관점을 어떤 순서로 설계해야 하는가?"
      ↓
제3장
아키텍처 6단계 수립 방법론
```

---

# 19. 제2장 최종 결론

## FIG-02-19. Final Story

```text
정보계 패러다임의 전환
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

제2장의 결론은 **Application 중심에서 Runtime·Platform·Data·Evidence 중심으로**라는 한 문장으로 정리된다.



---

<!-- CHAPTER 03: 03_NSIGHT_PDMG_아키텍처정의서_아키텍처_6단계_수립_방법론_STORY_VISUAL_v1.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제3장. 아키텍처 6단계 수립 방법론
## VISION → BIG PICTURE → LOGICAL → PHYSICAL → MECHANISM → RUNTIME
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **2장에서 정립한 새 관점을 순서 있는 설계 방법론으로 변환한다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제2장에서 넘어온 질문
     ↓
2장에서 정립한 새 관점을 순서 있는 설계 방법론으로 변환한다.
     ↓
이 장이 답해야 할 질문
     ↓
VISION → BIG PICTURE → LOGICAL → PHYSICAL → MECHANISM → RUNTIME
```

이 장의 핵심 원칙은 다음과 같다.

- 6단계는 문서 분류가 아니라 Drill-down 순서다.
- 각 단계의 산출물은 다음 단계의 입력이 된다.
- Top-down 설계와 Bottom-up Evidence가 마지막에 만나야 한다.
- 제품/서버를 먼저 결정하고 Architecture를 사후 설명하지 않는다.

---

# 1. 방법론이 필요한 이유

## FIG-03-01. 방법론이 필요한 이유

```text
Architecture 요소가 많다
Application
Logical
Physical
Security
Data
Runtime
Operations
     ↓
한 번에 그리면
경계가 섞인다
     ↓
단계가 필요하다
```

---

# 2. 6단계 전체 Journey

## FIG-03-02. 6단계 전체 Journey

```text
VISION
 ↓
BIG PICTURE
 ↓
LOGICAL
 ↓
PHYSICAL
 ↓
MECHANISM
 ↓
RUNTIME
 ↓
EVIDENCE
```

---

# 3. VISION — 무엇을 지향하는가

## FIG-03-03. VISION — 무엇을 지향하는가

```text
VISION

Scalable
+
Resilient
+
Data-Centric
     ↓
Architecture 판단기준
```

---

# 4. BIG PICTURE — 누가 무엇을 책임지는가

## FIG-03-04. BIG PICTURE — 누가 무엇을 책임지는가

```text
User / Channel
 ↓
Application Boundary
 ↓
Data / Integration Boundary
 ↓
Operations

책임은 공간에 고정
연결은 경계에서 통제
```

---

# 5. LOGICAL — 제품보다 기술 역할을 먼저 정한다

## FIG-03-05. LOGICAL — 제품보다 기술 역할을 먼저 정한다

```text
Application
 ↓
Technical Capability
 ↓
Logical Node
 ↓
State / Scale / Failure / Security
```

---

# 6. PHYSICAL — Logical Node를 실제 자원으로 내린다

## FIG-03-06. PHYSICAL — Logical Node를 실제 자원으로 내린다

```text
Logical Node
 ↓
Environment
 ↓
Center
 ↓
Host / VM
 ↓
JVM / WAR
 ↓
Network / DB / Storage
```

---

# 7. MECHANISM — 실행규칙을 정의한다

## FIG-03-07. MECHANISM — 실행규칙을 정의한다

```text
Filter
 ↓
Security
 ↓
TCF
 ↓
Timeout
 ↓
Transaction
 ↓
Error / Logging
```

---

# 8. RUNTIME — 거래 한 건을 시간축으로 본다

## FIG-03-08. RUNTIME — 거래 한 건을 시간축으로 본다

```text
HTTP
 ↓
Request Thread
 ↓ submit
Worker Thread
 ↓
TX
 ↓
Business
 ↓
DB
 ↓
Response / Evidence
```

---

# 9. 각 단계는 다음 단계의 입력이다

## FIG-03-09. 각 단계는 다음 단계의 입력이다

```text
VISION
  "Scalable"
     ↓
LOGICAL
  Scale Unit
     ↓
PHYSICAL
  N+1 Node
     ↓
RUNTIME
  Failure Test
     ↓
EVIDENCE
  PASS
```

---

# 10. Top-down과 Bottom-up을 닫는다

## FIG-03-10. Top-down과 Bottom-up을 닫는다

```text
Top-down
Architecture Intent
      ↓
Design
      ↓
Implementation

Bottom-up
Source / Config
      ↑
Runtime Evidence
      ↑
Actual Behavior
```

---

# 11. 6단계가 끝나도 최종 단계는 Evidence다

## FIG-03-11. 6단계가 끝나도 최종 단계는 Evidence다

```text
그림 완성
   ≠
Architecture 완료

Architecture
 ↓
Source / Config
 ↓
Test
 ↓
Runtime Evidence
 ↓
PASS
```

---

# 12. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-03-12. Normal Pattern

```text
VISION → BIG PICTURE → LOGICAL
→ PHYSICAL → MECHANISM → RUNTIME
→ EVIDENCE / PASS
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 13. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-03-13. Forbidden Pattern

```text
제품선정 → 서버배치 → 사후 Architecture 설명   X
Logical 없이 Physical 확정                    X
Runtime Evidence 없이 최종 PASS               X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 14. Architecture Decision — Architecture 수립순서

## FIG-03-14. 주안과 대안

```text
[주안]
6단계 Top-down + Evidence

        VS

[대안]
제품/Physical 선결정
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | 6단계 Top-down + Evidence | 제품/Physical 선결정 |
| 장점 | • 책임→기술→배치 논리 유지<br>• Target/Current 분리<br>• 변경영향 추적 | • 빠르게 구체화 가능 |
| 단점 | • 초기 정의시간 필요<br>• 모델 관리 필요 | • 제품 종속<br>• 사후합리화<br>• 경계 혼재 |
| 권고 | **주안 채택** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 15. Current PDMG GAP

## FIG-03-15. GAP Map

```text
Current PDMG
│
├─ 6단계 산출물 간 자동 Trace 미완료
├─ Architecture Model SSOT 자동화 미완료
└─ Runtime Gate 자동화 미완료
```

- `[GAP/OPEN]` 6단계 산출물 간 자동 Trace 미완료
- `[GAP/OPEN]` Architecture Model SSOT 자동화 미완료
- `[GAP/OPEN]` Runtime Gate 자동화 미완료

---

# 16. 제3장 Architecture 판정

## FIG-03-16. Assessment

```text
Architecture Definition
      ↓
PASS

Current PDMG Conformance
      ↓
PARTIAL

Runtime Evidence
      ↓
MEDIUM
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| 6단계 정의 | PASS | Story/산출물 구조 명확 |
| 단계간 Handoff | PASS | Logical→Physical→Runtime 연결 |
| Bottom-up Evidence | PARTIAL | 자동수집 미완료 |
| Gate | PARTIAL | HG90 자동화 OPEN |

---

# 17. 원천 정의서 Trace

## FIG-03-17. Source Trace

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
| 00 Master Index | Story/Drill-down/Current-Target 근거 |
| 01 Executive | Story/Drill-down/Current-Target 근거 |
| 04 Logical | Story/Drill-down/Current-Target 근거 |
| 05 Physical | Story/Drill-down/Current-Target 근거 |
| 08 Mechanism | Story/Drill-down/Current-Target 근거 |
| 09 Runtime | Story/Drill-down/Current-Target 근거 |
| 17 Traceability | Story/Drill-down/Current-Target 근거 |
| 18 Integrated | Story/Drill-down/Current-Target 근거 |

---

# 18. 다음 장으로 넘어가는 이유

## FIG-03-18. 3장 → 4

```text
제3장
아키텍처 6단계 수립 방법론
      ↓
"이제 전체 시스템에서 누가 무엇을 책임지는지 한눈에 어떻게 볼 것인가?"
      ↓
제4장
Big Picture
```

---

# 19. 제3장 최종 결론

## FIG-03-19. Final Story

```text
아키텍처 6단계 수립 방법론
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

제3장의 결론은 **VISION → BIG PICTURE → LOGICAL → PHYSICAL → MECHANISM → RUNTIME**라는 한 문장으로 정리된다.



---

<!-- CHAPTER 04: 04_NSIGHT_PDMG_아키텍처정의서_BIG_PICTURE_STORY_VISUAL_v1.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제4장. Big Picture
## PDMG 전체 System Context와 책임 경계
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **3장의 방법론을 실제 PDMG 전체 구조에 적용해 책임과 경계를 한 장에서 보이게 만든다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제3장에서 넘어온 질문
     ↓
3장의 방법론을 실제 PDMG 전체 구조에 적용해 책임과 경계를 한 장에서 보이게 만든다.
     ↓
이 장이 답해야 할 질문
     ↓
PDMG 전체 System Context와 책임 경계
```

이 장의 핵심 원칙은 다음과 같다.

- 책임은 Application/Technical Boundary 안에 고정한다.
- 연결은 승인된 Contract를 통해 통제한다.
- pdmg-fw는 별도 Remote Business Server로 가정하지 않는다.
- PDMG Current의 UNKNOWN을 Target 기능으로 채우지 않는다.

---

# 1. Big Picture의 목적

## FIG-04-01. Big Picture의 목적

```text
수많은 Source / Module / Server
        ↓
한 장에서 먼저 답할 것
        ↓
누가 호출하는가
무엇을 책임지는가
어디가 경계인가
무엇이 외부인가
```

---

# 2. PDMG System Context

## FIG-04-02. PDMG System Context

```text
User / Browser
      ↓
pdmg-ui
      ↓
pdmg-jwt
      ↓
pdmg-service
      │
      └─ pdmg-fw
           ↓
        Business
           ↓
        RDW / DB

pdmg-om [UNKNOWN]
External [Contract dependent]
```

---

# 3. UI Boundary

## FIG-04-03. UI Boundary

```text
Browser
 ↓
pdmg-ui
 ├─ Screen / Static
 ├─ Transaction Catalog
 ├─ Request Assembly
 └─ Bearer Token
 ↓
pdmg-service
```

---

# 4. Authentication Boundary

## FIG-04-04. Authentication Boundary

```text
Login / SSO
 ↓
pdmg-jwt
 ↓
Access / Refresh
 ↓
JWKS
 ↓
Business Runtime
```

---

# 5. Application Runtime Boundary

## FIG-04-05. Application Runtime Boundary

```text
pdmg-service
┌──────────────────────────┐
│ pdmg-fw                  │
│ Runtime Control          │
│                          │
│ Business Component       │
│ Handler / Facade /       │
│ Service / DAO            │
└────────────┬─────────────┘
             ↓
           Data
```

---

# 6. Data Boundary

## FIG-04-06. Data Boundary

```text
Business Runtime
 ↓
DAO / Mapper
 ↓
JDBC
 ↓
RDW / DB

Other System Data
 ↓
Approved Contract
```

---

# 7. External Integration Boundary

## FIG-04-07. External Integration Boundary

```text
PDMG
 ↓
Approved Interface Contract
 ├─ API
 ├─ Event
 ├─ File
 └─ Data Movement
 ↓
External System

Current inventory
= OPEN / PARTIAL
```

---

# 8. Cross-cutting Security

## FIG-04-08. Cross-cutting Security

```text
User
 ↓
Auth
 ↓
Token
 ↓
Verification
 ↓
Principal
 ↓
Authorization
 ↓
Data
```

---

# 9. Cross-cutting Observability

## FIG-04-09. Cross-cutting Observability

```text
ServiceId
+ GUID
 ↓
Runtime
 ↓
Metric / Log / Trace
 ↓
Operations
```

---

# 10. Big Picture에서 보이는 핵심 GAP

## FIG-04-10. Big Picture에서 보이는 핵심 GAP

```text
Security
RS256 ↔ HMAC [CRITICAL]

Identity Binding [GAP]

External Interface Inventory [OPEN]

pdmg-om [UNKNOWN]

Deployment Mapping [GAP]
```

---

# 11. Big Picture는 세부구조를 숨기되 경계를 숨기지 않는다

## FIG-04-11. Big Picture는 세부구조를 숨기되 경계를 숨기지 않는다

```text
Big Picture
= 적은 박스
+ 명확한 책임
+ 명확한 경계
+ 정상/금지 연결
```

---

# 12. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-04-12. Normal Pattern

```text
User → UI / Auth → Application Runtime
                    ↓
                 Data

External
→ Approved Contract

Security / Observability
= Cross-cutting
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 13. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-04-13. Forbidden Pattern

```text
Browser → DB                   X
UI → DAO / Mapper               X
External → PDMG DB Direct DML   X
Target Component → Current Fact X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 14. Architecture Decision — System Boundary

## FIG-04-14. 주안과 대안

```text
[주안]
책임 고정 + Contract 연결

        VS

[대안]
P2P/Direct 연결 확대
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | 책임 고정 + Contract 연결 | P2P/Direct 연결 확대 |
| 장점 | • 변경격리<br>• 보안/운영 통제<br>• Traceability 향상 | • 초기 구현 단순 |
| 단점 | • Interface Governance 필요 | • 강결합<br>• 장애전파<br>• Ownership 붕괴 |
| 권고 | **주안 채택** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 15. Current PDMG GAP

## FIG-04-15. GAP Map

```text
Current PDMG
│
├─ JWT/Identity Critical GAP
├─ External Interface Inventory OPEN
├─ pdmg-om Current Detail UNKNOWN
└─ Artifact→Host/JVM/WAR Mapping GAP
```

- `[GAP/OPEN]` JWT/Identity Critical GAP
- `[GAP/OPEN]` External Interface Inventory OPEN
- `[GAP/OPEN]` pdmg-om Current Detail UNKNOWN
- `[GAP/OPEN]` Artifact→Host/JVM/WAR Mapping GAP

---

# 16. 제4장 Architecture 판정

## FIG-04-16. Assessment

```text
Architecture Definition
      ↓
PASS

Current PDMG Conformance
      ↓
CONDITIONAL

Runtime Evidence
      ↓
MEDIUM
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| UI/Auth/Business Boundary | PASS | Source/Runtime 근거 |
| Data Boundary | PASS/PARTIAL | RDW 강한 근거 |
| External Boundary | OPEN | 전수 Interface Inventory 필요 |
| Operations Boundary | OPEN | pdmg-om 상세 UNKNOWN |
| Security | GAP | JWT/Identity Critical |

---

# 17. 원천 정의서 Trace

## FIG-04-17. Source Trace

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
| 01 Executive | Story/Drill-down/Current-Target 근거 |
| 02 System Context | Story/Drill-down/Current-Target 근거 |
| 18 Integrated | Story/Drill-down/Current-Target 근거 |

---

# 18. 다음 장으로 넘어가는 이유

## FIG-04-18. 4장 → 5

```text
제4장
Big Picture
      ↓
"이 책임들을 기술 역할과 Logical Node로 어떻게 구조화할 것인가?"
      ↓
제5장
논리 아키텍처
```

---

# 19. 제4장 최종 결론

## FIG-04-19. Final Story

```text
Big Picture
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

제4장의 결론은 **PDMG 전체 System Context와 책임 경계**라는 한 문장으로 정리된다.



---

<!-- CHAPTER 05: 05_NSIGHT_PDMG_아키텍처정의서_논리_아키텍처_STORY_VISUAL_v1.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제5장. 논리 아키텍처
## Application Responsibility를 Logical Technical Node로 변환
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **4장에서 정한 책임경계를 제품이나 서버로 바로 내리지 않고 기술역할과 Logical Node로 변환한다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제4장에서 넘어온 질문
     ↓
4장에서 정한 책임경계를 제품이나 서버로 바로 내리지 않고 기술역할과 Logical Node로 변환한다.
     ↓
이 장이 답해야 할 질문
     ↓
Application Responsibility를 Logical Technical Node로 변환
```

이 장의 핵심 원칙은 다음과 같다.

- Application ≠ Logical Technical Node.
- Build Module ≠ Logical Technical Node.
- Logical Node ≠ Physical Host.
- 모든 Logical Node는 State/Scale/Failure/Security 속성을 가진다.

---

# 1. Big Picture만으로 Physical을 바로 결정하면 안 된다

## FIG-05-01. Big Picture만으로 Physical을 바로 결정하면 안 된다

```text
Application
 ↓
Server

X

Application
 ↓
Technical Capability
 ↓
Logical Node
 ↓
Physical
```

---

# 2. Application Responsibility를 기술 역할로 변환한다

## FIG-05-02. Application Responsibility를 기술 역할로 변환한다

```text
pdmg-ui      → UI Delivery
pdmg-jwt     → Authentication
pdmg-service → Business Runtime
pdmg-fw      → Runtime Control
RDW/DB       → Data Service
```

---

# 3. PDMG Logical Node Set

## FIG-05-03. PDMG Logical Node Set

```text
LTN-PD-01 UI Delivery
LTN-PD-02 Authentication
LTN-PD-03 Application Runtime
LTN-PD-04 Data Service
LTN-PD-05 Integration [CONDITIONAL]
LTN-PD-06 Operations [OPEN]
```

---

# 4. Application Runtime Node 내부

## FIG-05-04. Application Runtime Node 내부

```text
LTN-PD-03
Application Runtime
│
├─ Framework Capability
│  ├─ Filter / Context
│  ├─ Security
│  ├─ TCF
│  ├─ Worker / Timeout
│  └─ Transaction / Error
│
└─ Business Capability
   ├─ Handler / Controller
   ├─ Facade
   ├─ Service
   └─ DAO
```

---

# 5. Logical Node는 제품명이 아니다

## FIG-05-05. Logical Node는 제품명이 아니다

```text
Logical Role
Application Runtime
      ↓
Technology Component
WAS / JVM
      ↓
Current Product
Tomcat / Java

Role ≠ Product
```

---

# 6. State를 정의해야 Scale이 보인다

## FIG-05-06. State를 정의해야 Scale이 보인다

```text
UI Delivery
mostly stateless

Authentication
key / refresh state

Application Runtime
request / context / TX state

Data Service
persistent state
```

---

# 7. Scale Unit를 정의한다

## FIG-05-07. Scale Unit를 정의한다

```text
UI → Delivery Instance
Auth → Auth Instance
App → Runtime/JVM Instance
DB → DB Service/Node
Ops → Collector/Control Instance
```

---

# 8. Failure Domain을 정의한다

## FIG-05-08. Failure Domain을 정의한다

```text
UI Failure
→ access impact

Auth Failure
→ login/token impact

App Runtime Failure
→ business transaction impact

DB Failure
→ query/DML impact
```

---

# 9. Security Boundary를 Logical 구조에 포함한다

## FIG-05-09. Security Boundary를 Logical 구조에 포함한다

```text
Untrusted Client
 ↓
Auth Boundary
 ↓
Application Boundary
 ↓
Business Authorization
 ↓
Data Boundary
```

---

# 10. Allowed / Forbidden Path

## FIG-05-10. Allowed / Forbidden Path

```text
Allowed
UI → App Runtime → Data Access → DB

Forbidden
Client → DB
UI → DB
External → DB DML
```

---

# 11. Logical Architecture의 마지막은 Physical Handoff다

## FIG-05-11. Logical Architecture의 마지막은 Physical Handoff다

```text
Logical Node
 ↓
Runtime Characteristic
 ↓
State / Scale / Failure
 ↓
Allowed Path
 ↓
Physical Mapping
```

---

# 12. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-05-12. Normal Pattern

```text
Application
 ↓
Technical Capability
 ↓
Logical Node
 ↓
Runtime Type
 ↓
State / Scale / Failure / Security
 ↓
Physical Handoff
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 13. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-05-13. Forbidden Pattern

```text
pdmg-fw = 독립 Remote Server   X
Logical Node = Tomcat 10.x      X
Application Code = Hostname     X
Unknown = Current Fact          X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 14. Architecture Decision — Framework Runtime Placement

## FIG-05-14. 주안과 대안

```text
[주안]
pdmg-service와 in-process

        VS

[대안]
pdmg-fw Remote Runtime
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | pdmg-service와 in-process | pdmg-fw Remote Runtime |
| 장점 | • Current Source 정합<br>• Network hop 없음<br>• TX/Context 연계 단순 | • 독립 Scale 가능<br>• Process 격리 |
| 단점 | • 동일 Process Failure Domain | • Current 구조와 불일치<br>• RPC/지연/계약 추가 |
| 권고 | **주안 채택** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 15. Current PDMG GAP

## FIG-05-15. GAP Map

```text
Current PDMG
│
├─ LTN-PD-05 Integration Current 범위 OPEN
├─ LTN-PD-06 Operations/pdmg-om UNKNOWN
├─ State/Scale/HA Runtime Evidence 미완료
└─ Logical→Physical 전수 Mapping 미완료
```

- `[GAP/OPEN]` LTN-PD-05 Integration Current 범위 OPEN
- `[GAP/OPEN]` LTN-PD-06 Operations/pdmg-om UNKNOWN
- `[GAP/OPEN]` State/Scale/HA Runtime Evidence 미완료
- `[GAP/OPEN]` Logical→Physical 전수 Mapping 미완료

---

# 16. 제5장 Architecture 판정

## FIG-05-16. Assessment

```text
Architecture Definition
      ↓
PASS

Current PDMG Conformance
      ↓
CONDITIONAL

Runtime Evidence
      ↓
MEDIUM
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| Logical Node Set | PASS | 6개 Node/조건부 Node 정의 |
| Framework Placement | PASS AS-IS | service+fw 동일 Runtime 가능 |
| State/Scale/Failure | PARTIAL | 실측 Evidence 필요 |
| Integration/OM | OPEN | Current 범위 미확정 |
| Physical Handoff | GAP | 전수 Mapping 필요 |

---

# 17. 원천 정의서 Trace

## FIG-05-17. Source Trace

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
| 03 Application | Story/Drill-down/Current-Target 근거 |
| 04 Logical | Story/Drill-down/Current-Target 근거 |
| 06 Interface | Story/Drill-down/Current-Target 근거 |
| 07 Data | Story/Drill-down/Current-Target 근거 |

---

# 18. 다음 장으로 넘어가는 이유

## FIG-05-18. 5장 → 6

```text
제5장
논리 아키텍처
      ↓
"Logical Node를 실제 Center·Host·VM·JVM·WAR에 어떻게 배치할 것인가?"
      ↓
제6장
물리 아키텍처
```

---

# 19. 제5장 최종 결론

## FIG-05-19. Final Story

```text
논리 아키텍처
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

제5장의 결론은 **Application Responsibility를 Logical Technical Node로 변환**라는 한 문장으로 정리된다.



---

<!-- CHAPTER 06: 06_NSIGHT_PDMG_아키텍처정의서_물리_아키텍처_STORY_VISUAL_v1.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제6장. 물리 아키텍처
## Logical Node를 Center·WEB·WAS·JVM·DB로 Mapping
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **5장의 Logical Node를 실제 실행 자원과 네트워크·데이터·스토리지 구조로 내린다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제5장에서 넘어온 질문
     ↓
5장의 Logical Node를 실제 실행 자원과 네트워크·데이터·스토리지 구조로 내린다.
     ↓
이 장이 답해야 할 질문
     ↓
Logical Node를 Center·WEB·WAS·JVM·DB로 Mapping
```

이 장의 핵심 원칙은 다음과 같다.

- Server ≠ VM ≠ JVM ≠ WAR.
- 정확한 Hostname/Port/Version은 Evidence 없이는 OPEN이다.
- Capacity Candidate를 Production Fact로 승격하지 않는다.
- Logical Node는 Go-live 전에 실제 Physical Mapping을 가져야 한다.

---

# 1. Physical Architecture는 '어디에 배치되는가'를 답한다

## FIG-06-01. Physical Architecture는 '어디에 배치되는가'를 답한다

```text
Logical Node
 ↓
Environment
 ↓
Center
 ↓
Compute
 ↓
Runtime Process
 ↓
Artifact
 ↓
Network / Data / Storage
```

---

# 2. PDMG Working Physical Path

## FIG-06-02. PDMG Working Physical Path

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
Hikari / MyBatis / JDBC
 ↓
RDW / DB
```

---

# 3. Server / VM / JVM / WAR를 분리한다

## FIG-06-03. Server / VM / JVM / WAR를 분리한다

```text
Physical Server
 ↓
VM
 ↓
OS
 ↓
Runtime Process
 ↓
JVM
 ↓
WAR
```

---

# 4. WEB와 WAS의 책임을 분리한다

## FIG-06-04. WEB와 WAS의 책임을 분리한다

```text
GSLB
 ↓
L4
 ↓
WEB / Apache
 ↓
Reverse Proxy / Routing
 ↓
WAS / Tomcat
 ↓
Application
```

---

# 5. WAS 내부도 Failure Domain을 나눌 수 있다

## FIG-06-05. WAS 내부도 Failure Domain을 나눌 수 있다

```text
WAS VM
 ├─ JVM Group A
 │   ├─ WAR A...
 │   └─ WAR A...
 └─ JVM Group B
     ├─ WAR B...
     └─ WAR B...
```

---

# 6. Data Physical Boundary

## FIG-06-06. Data Physical Boundary

```text
Application JVM
 ↓
Datasource
 ↓
Hikari
 ↓
JDBC
 ↓
DB Service
 ↓
DB Node / Storage
```

---

# 7. Network는 Port/Firewall/LB/Config가 하나의 체계다

## FIG-06-07. Network는 Port/Firewall/LB/Config가 하나의 체계다

```text
DNS/GSLB
 ↓
L4 VIP
 ↓
WEB Port
 ↓
WAS Connector
 ↓
DB Service
 ↓
Management

LB ↔ Firewall ↔ Config
```

---

# 8. 환경과 센터는 다른 축이다

## FIG-06-08. 환경과 센터는 다른 축이다

```text
Environment
DEV / TEST / PROD / DR

          ≠

Center
Main / DR Center
```

---

# 9. Capacity 값은 Candidate 상태를 구분한다

## FIG-06-09. Capacity 값은 Candidate 상태를 구분한다

```text
Candidate A
32C/256G ×4

Candidate B
16C/128G ×8

Candidate C
16C/128G ×4 ×2 groups

≠ Production Fact
```

---

# 10. Physical Traceability가 완성되어야 한다

## FIG-06-10. Physical Traceability가 완성되어야 한다

```text
Application
 ↓
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
 ↓
Center
 ↓
Evidence
```

---

# 11. Physical Architecture는 HA/DR의 기반이 된다

## FIG-06-11. Physical Architecture는 HA/DR의 기반이 된다

```text
Node
 ↓
Failure Domain
 ↓
N+1
 ↓
Local HA
 ↓
Center DR
```

---

# 12. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-06-12. Normal Pattern

```text
Logical Node
 ↓
Environment
 ↓
Center
 ↓
Host / VM
 ↓
JVM / WAR
 ↓
Network / DB / Storage
 ↓
Monitoring / Evidence
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 13. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-06-13. Forbidden Pattern

```text
Server = JVM = WAR            X
Candidate = Approved Capacity   X
Port/Hostname 추정 기입        X
Artifact와 Host 연결 없음       X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 14. Architecture Decision — WAS 배치전략

## FIG-06-14. 주안과 대안

```text
[주안]
중형 VM Scale-out + JVM/WAR 격리

        VS

[대안]
대형 VM Scale-up
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | 중형 VM Scale-out + JVM/WAR 격리 | 대형 VM Scale-up |
| 장점 | • N+1/장애격리 유리<br>• 업무그룹 분리<br>• 수평확장 | • 구성 단순<br>• 노드 수 감소 |
| 단점 | • 운영 인스턴스 증가 | • Failure Domain 확대<br>• GC/자원독점 영향 |
| 권고 | **주안 채택 — Load Test 전제** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 15. Current PDMG GAP

## FIG-06-15. GAP Map

```text
Current PDMG
│
├─ Artifact→Host/JVM/WAR 전수 Mapping 미완료
├─ 실제 Port/Firewall/Version Inventory OPEN
├─ JVM/WAR 업무그룹 최종 승인 OPEN
└─ Backup/Restore Evidence 미완료
```

- `[GAP/OPEN]` Artifact→Host/JVM/WAR 전수 Mapping 미완료
- `[GAP/OPEN]` 실제 Port/Firewall/Version Inventory OPEN
- `[GAP/OPEN]` JVM/WAR 업무그룹 최종 승인 OPEN
- `[GAP/OPEN]` Backup/Restore Evidence 미완료

---

# 16. 제6장 Architecture 판정

## FIG-06-16. Assessment

```text
Architecture Definition
      ↓
PASS

Current PDMG Conformance
      ↓
PARTIAL

Runtime Evidence
      ↓
MEDIUM
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| Physical Route | PASS/BASELINE | GSLB→L4→WEB→WAS→DB |
| Host/Port Inventory | OPEN | 실제 CMDB/Config 필요 |
| JVM/WAR Placement | CONDITIONAL | Candidate/Load Test |
| Traceability | GAP | Artifact→Host 연결 필요 |
| Backup | CONDITIONAL | Restore Evidence 필요 |

---

# 17. 원천 정의서 Trace

## FIG-06-17. Source Trace

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
| 05 Physical | Story/Drill-down/Current-Target 근거 |
| 16 Capacity/HA/DR | Story/Drill-down/Current-Target 근거 |

---

# 18. 다음 장으로 넘어가는 이유

## FIG-06-18. 6장 → 7

```text
제6장
물리 아키텍처
      ↓
"이 물리구조가 노드 장애와 센터 재해를 어떻게 견딜 것인가?"
      ↓
제7장
DR 센터 활용 전략
```

---

# 19. 제6장 최종 결론

## FIG-06-19. Final Story

```text
물리 아키텍처
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

제6장의 결론은 **Logical Node를 Center·WEB·WAS·JVM·DB로 Mapping**라는 한 문장으로 정리된다.



---

<!-- CHAPTER 07: 07_NSIGHT_PDMG_아키텍처정의서_DR_센터_활용_전략_STORY_VISUAL_v1.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제7장. DR 센터 활용 전략
## AP 가용성과 DB 정합성을 분리하는 HA/DR 전략
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **6장에서 배치한 Physical Node가 장애와 센터 재해를 어떻게 견디고 복구되는지 정의한다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제6장에서 넘어온 질문
     ↓
6장에서 배치한 Physical Node가 장애와 센터 재해를 어떻게 견디고 복구되는지 정의한다.
     ↓
이 장이 답해야 할 질문
     ↓
AP 가용성과 DB 정합성을 분리하는 HA/DR 전략
```

이 장의 핵심 원칙은 다음과 같다.

- Local HA와 Center DR을 구분한다.
- AP 가용성 전략과 DB 정합성 전략을 같은 것으로 취급하지 않는다.
- RTO/RPO는 Business Criticality로 결정한다.
- Backup 성공만으로 DR PASS를 선언하지 않는다.

---

# 1. HA와 DR은 같은 것이 아니다

## FIG-07-01. HA와 DR은 같은 것이 아니다

```text
Local HA
= Node / Process Failure 대응

DR
= Center / Site Disaster 대응
```

---

# 2. Main Center의 Local HA

## FIG-07-02. Main Center의 Local HA

```text
GSLB / L4
 ↓
WEB Pair / N+1
 ↓
WAS Active-Active / N+1
 ↓
DB Local HA
 ↓
Residual Capacity
```

---

# 3. 센터 장애 시 DR Flow

## FIG-07-03. 센터 장애 시 DR Flow

```text
Main Center Failure
 ↓
Detect
 ↓
Isolate
 ↓
Traffic Reroute
 ↓
DR WEB/WAS
 ↓
DR DB
 ↓
Business Validation
```

---

# 4. DR에서 Application만 복제하면 부족하다

## FIG-07-04. DR에서 Application만 복제하면 부족하다

```text
DR 준비대상
├─ Artifact
├─ Config
├─ Secret / Key
├─ DB
├─ Interface
├─ Scheduler/Batch
├─ Monitoring
└─ Runbook
```

---

# 5. Key/Secret도 DR 데이터다

## FIG-07-05. Key/Secret도 DR 데이터다

```text
Main JWT Key / Secret
       ↓
Versioned / Managed
       ↓
DR Sync
       ↓
Same Trust
```

---

# 6. DB 정합성은 AP 가용성과 별도 문제다

## FIG-07-06. DB 정합성은 AP 가용성과 별도 문제다

```text
AP Active-Active
= request availability

DB Active-Active
= data write consistency problem

둘은 같은 결정이 아니다
```

---

# 7. RTO/RPO가 DR Tier를 결정한다

## FIG-07-07. RTO/RPO가 DR Tier를 결정한다

```text
Business Criticality
 ↓
RTO / RPO
 ↓
DR Tier
 ↓
Warm / Hot / Other
 ↓
Test Evidence
```

---

# 8. Backup과 DR을 구분한다

## FIG-07-08. Backup과 DR을 구분한다

```text
Backup
= Data Copy

Restore
= Data Recovery

DR
= Service Recovery

Business Validation
= Real Recovery PASS
```

---

# 9. Failover만큼 Failback도 중요하다

## FIG-07-09. Failover만큼 Failback도 중요하다

```text
Main → DR
 Failover
    ↓
Business on DR
    ↓
Main Recovery
    ↓
Data/Config Re-sync
    ↓
Failback
```

---

# 10. DR Test는 전체 경로를 검증한다

## FIG-07-10. DR Test는 전체 경로를 검증한다

```text
Network
 ↓
WEB/WAS
 ↓
Artifact/Config/Key
 ↓
DB
 ↓
Interface
 ↓
Business Transaction
 ↓
Evidence
```

---

# 11. DR의 최종 목적

## FIG-07-11. DR의 최종 목적

```text
"센터가 살아있는가?"
        X

"업무가
정합성을 유지하며
복구되는가?"
        O
```

---

# 12. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-07-12. Normal Pattern

```text
Detect
 ↓
Isolate
 ↓
Reroute
 ↓
Recover App / Config / Key / Data
 ↓
Business Validation
 ↓
Failback
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 13. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-07-13. Forbidden Pattern

```text
Backup Success = DR PASS          X
DB 양방향 Active-Active 무검증       X
RTO/RPO 임의 숫자 확정               X
Failover만 테스트, Failback 미검증    X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 14. Architecture Decision — DR 전략

## FIG-07-14. 주안과 대안

```text
[주안]
AP Active-Active/N+1 + DB 정합성 우선 DR

        VS

[대안]
DB 양방향 Active-Active
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | AP Active-Active/N+1 + DB 정합성 우선 DR | DB 양방향 Active-Active |
| 장점 | • 정합성 위험 통제<br>• 운영복잡도 관리<br>• Current 구조와 자연스러운 연결 | • RTO 단축 가능성 |
| 단점 | • 전환절차/동기화 자동화 필요 | • Split-brain/충돌 위험<br>• 검증/운영비용 증가 |
| 권고 | **주안 채택** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 15. Current PDMG GAP

## FIG-07-15. GAP Map

```text
Current PDMG
│
├─ RTO/RPO 최종 값 OPEN
├─ DR Artifact/Config/Key 동기화 Evidence 미완료
├─ DB HA/DR 상세 Inventory OPEN
└─ Failback/Business Validation Evidence 미완료
```

- `[GAP/OPEN]` RTO/RPO 최종 값 OPEN
- `[GAP/OPEN]` DR Artifact/Config/Key 동기화 Evidence 미완료
- `[GAP/OPEN]` DB HA/DR 상세 Inventory OPEN
- `[GAP/OPEN]` Failback/Business Validation Evidence 미완료

---

# 16. 제7장 Architecture 판정

## FIG-07-16. Assessment

```text
Architecture Definition
      ↓
PASS

Current PDMG Conformance
      ↓
OPEN / CONDITIONAL

Runtime Evidence
      ↓
LOW-MEDIUM
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| Local HA | CONDITIONAL | N+1 Failure Test 필요 |
| DR Topology | PASS/REFERENCE | Main→DR 구조 정의 |
| RTO/RPO | OPEN | Business 승인 필요 |
| DB DR | CONDITIONAL | 제품/복제 상세 필요 |
| Drill | OPEN | Failover/Failback Evidence 필요 |

---

# 17. 원천 정의서 Trace

## FIG-07-17. Source Trace

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
| 05 Physical | Story/Drill-down/Current-Target 근거 |
| 16 Capacity/HA/DR | Story/Drill-down/Current-Target 근거 |

---

# 18. 다음 장으로 넘어가는 이유

## FIG-07-18. 7장 → 8

```text
제7장
DR 센터 활용 전략
      ↓
"그렇다면 정상상태에서 거래를 안전하게 움직이는 공통 실행규칙은 무엇인가?"
      ↓
제8장
메커니즘
```

---

# 19. 제7장 최종 결론

## FIG-07-19. Final Story

```text
DR 센터 활용 전략
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

제7장의 결론은 **AP 가용성과 DB 정합성을 분리하는 HA/DR 전략**라는 한 문장으로 정리된다.



---

<!-- CHAPTER 08: 08_NSIGHT_PDMG_아키텍처정의서_메커니즘_STORY_VISUAL_v1.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제8장. 메커니즘
## Architecture를 실제로 움직이게 하는 공통 실행 규칙
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **7장에서 다룬 장애복구 이전에, 정상거래가 어떤 공통 Rule로 실행되는지 Framework Mechanism을 정의한다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제7장에서 넘어온 질문
     ↓
7장에서 다룬 장애복구 이전에, 정상거래가 어떤 공통 Rule로 실행되는지 Framework Mechanism을 정의한다.
     ↓
이 장이 답해야 할 질문
     ↓
Architecture를 실제로 움직이게 하는 공통 실행 규칙
```

이 장의 핵심 원칙은 다음과 같다.

- Framework는 '어떻게 실행하는가'를 소유하고 Business는 '무엇을 실행하는가'를 소유한다.
- Handler/Controller는 Business Core인 Facade를 향한다.
- Timeout Response와 DB 작업 종료를 동일시하지 않는다.
- Context는 Thread lifecycle에 맞춰 install/clear한다.

---

# 1. Architecture가 실제로 움직이려면 Mechanism이 필요하다

## FIG-08-01. Architecture가 실제로 움직이려면 Mechanism이 필요하다

```text
Architecture Box
      ↓
실행규칙 없음
      ↓
각 업무가 제각각 구현
      ↓
Drift

따라서
Framework Mechanism 필요
```

---

# 2. PDMG Mechanism 전체

## FIG-08-02. PDMG Mechanism 전체

```text
HTTP
 ↓
DefaultFilter
 ↓
SecurityFilterChain
 ↓
DispatcherServlet
 ↓
Interceptor
 ↓
Controller
 ↓
TcfFacade
 ↓
TimeoutExecutor
 ↓
Dispatcher
 ↓
Handler
 ↓
Business
 ↓
Response/Error/Log
```

---

# 3. Framework와 Business를 분리한다

## FIG-08-03. Framework와 Business를 분리한다

```text
Framework
= How safely execute

Business
= What to execute

Framework
 ↓ Contract
Business
```

---

# 4. Filter — 거래 Context의 시작

## FIG-08-04. Filter — 거래 Context의 시작

```text
Request
 ↓
Body Cache
 ↓
Header / GUID
 ↓
ServiceContext
 ↓
MDC
 ↓
FilterChain
 ↓
finally clear
```

---

# 5. Security — Runtime 앞의 Trust Gate

## FIG-08-05. Security — Runtime 앞의 Trust Gate

```text
Filter
 ↓
SecurityFilterChain
 ↓
Token Verification
 ↓
Trusted Principal?
 ↓
MVC
```

---

# 6. TCF — ServiceId를 Business Handler로 연결한다

## FIG-08-06. TCF — ServiceId를 Business Handler로 연결한다

```text
ServiceId
 ↓
TransactionDispatcher
 ↓
Handler Registry
 ↓
TransactionHandler
 ↓
Facade
```

---

# 7. Timeout — Request와 Worker를 분리한다

## FIG-08-07. Timeout — Request와 Worker를 분리한다

```text
Request Thread
 ↓ submit
Worker Pool
 ↓
TransactionTemplate
 ↓
Business
```

---

# 8. Transaction — Worker 안에서 시작된다

## FIG-08-08. Transaction — Worker 안에서 시작된다

```text
Worker
 ↓
TransactionTemplate BEGIN
 ↓
Handler
 ↓
Facade REQUIRED joins
 ↓
Service / DAO
 ↓
Commit / Rollback
```

---

# 9. Context — Thread 경계를 건너야 한다

## FIG-08-09. Context — Thread 경계를 건너야 한다

```text
Request ServiceContext
 ↓ capture
Worker install
 ↓
Business
 ↓
Worker clear
```

---

# 10. Error — 모든 실패를 같은 방식으로 보이게 한다

## FIG-08-10. Error — 모든 실패를 같은 방식으로 보이게 한다

```text
Exception
 ↓
Taxonomy
 ↓
ErrorCode
 ↓
HTTP Status
 ↓
Standard Envelope
 ↓
Evidence
```

---

# 11. Logging — 거래와 Runtime을 연결한다

## FIG-08-11. Logging — 거래와 Runtime을 연결한다

```text
GUID / ServiceId
 ↓
MDC
 ↓
ImageLog / Application Log
 ↓
Metric / Trace
 ↓
Evidence
```

---

# 12. TCF OFF도 별도 시스템이 아니라 Entry Mechanism 차이다

## FIG-08-12. TCF OFF도 별도 시스템이 아니라 Entry Mechanism 차이다

```text
TCF ON
Handler → Facade → Service

TCF OFF
Controller → Facade → Service
        [TARGET]

Current 일부
Controller → Service Direct
        [GAP]
```

---

# 13. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-08-13. Normal Pattern

```text
Filter
 ↓
Security
 ↓
MVC
 ↓
TCF / Adapter
 ↓
Common Facade
 ↓
Service
 ↓
DAO
 ↓
DB
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 14. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-08-14. Forbidden Pattern

```text
Handler → DAO                 X
Controller → Mapper             X
Framework → 특정 업무 SQL       X
TCF OFF가 공통 Business Core 우회 X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 15. Architecture Decision — TCF ON/OFF Business Core

## FIG-08-15. 주안과 대안

```text
[주안]
Handler/Controller 모두 Common Facade

        VS

[대안]
OFF Controller→Service 직접
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | Handler/Controller 모두 Common Facade | OFF Controller→Service 직접 |
| 장점 | • Use Case/Tx/Validation 일관<br>• TCF 전환 영향 축소<br>• 테스트 기준 통일 | • Current 일부 Source 변경 적음 |
| 단점 | • Facade 정비 필요 | • 정책 Drift<br>• 선후처리/Tx 차이<br>• 운영 복잡도 |
| 권고 | **주안 채택** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 16. Current PDMG GAP

## FIG-08-16. GAP Map

```text
Current PDMG
│
├─ Mutable Worker ServiceContext
├─ TCF OFF Controller→Service Direct
├─ Filter/Security Early Error Envelope
├─ ServiceId source mismatch defense 미완료
└─ Generic Exception/Advice Order 검증 미완료
```

- `[GAP/OPEN]` Mutable Worker ServiceContext
- `[GAP/OPEN]` TCF OFF Controller→Service Direct
- `[GAP/OPEN]` Filter/Security Early Error Envelope
- `[GAP/OPEN]` ServiceId source mismatch defense 미완료
- `[GAP/OPEN]` Generic Exception/Advice Order 검증 미완료

---

# 17. 제8장 Architecture 판정

## FIG-08-17. Assessment

```text
Architecture Definition
      ↓
PASS

Current PDMG Conformance
      ↓
PARTIAL / GAP

Runtime Evidence
      ↓
HIGH-MEDIUM
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| Filter/MVC | PASS | Current path 확인 |
| TCF/Dispatcher | PASS | Registry/Handler 확인 |
| Worker/TX | PASS/PARTIAL | Current 구조 확인 |
| Context | GAP | mutable sharing risk |
| TCF OFF | GAP | Common Facade parity 미완료 |

---

# 18. 원천 정의서 Trace

## FIG-08-18. Source Trace

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
| 08 Framework | Story/Drill-down/Current-Target 근거 |
| 10 Transaction/Timeout | Story/Drill-down/Current-Target 근거 |
| 11 Security | Story/Drill-down/Current-Target 근거 |
| 12 Message/Context | Story/Drill-down/Current-Target 근거 |

---

# 19. 다음 장으로 넘어가는 이유

## FIG-08-19. 8장 → 9

```text
제8장
메커니즘
      ↓
"이 Mechanism들이 거래 한 건에서 실제로 어떤 시간순서로 실행되는가?"
      ↓
제9장
런타임 서비스
```

---

# 20. 제8장 최종 결론

## FIG-08-20. Final Story

```text
메커니즘
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

제8장의 결론은 **Architecture를 실제로 움직이게 하는 공통 실행 규칙**라는 한 문장으로 정리된다.



---

<!-- CHAPTER 09: 09_NSIGHT_PDMG_아키텍처정의서_런타임_서비스_STORY_VISUAL_v1.md -->

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



---

<!-- CHAPTER 10: 10_NSIGHT_PDMG_아키텍처정의서_데이터플랫폼_STORY_VISUAL_v1.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제10장. 데이터플랫폼
## RDW·ADW 역할분리와 PDMG Data Access
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **9장의 Runtime이 사용하는 Data를 Ownership·Workload·Lineage 관점으로 재정의한다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제9장에서 넘어온 질문
     ↓
9장의 Runtime이 사용하는 Data를 Ownership·Workload·Lineage 관점으로 재정의한다.
     ↓
이 장이 답해야 할 질문
     ↓
RDW·ADW 역할분리와 PDMG Data Access
```

이 장의 핵심 원칙은 다음과 같다.

- ServiceId→DAO→Mapper→SqlId→Table Trace를 유지한다.
- RDW와 ADW의 Workload 목적을 분리한다.
- Cross-system Direct DML/DB-Link를 정상패턴으로 두지 않는다.
- Data Owner/Steward/SOR와 Metadata/Lineage를 관리한다.

---

# 1. Runtime의 끝에는 Data가 있다

## FIG-10-01. Runtime의 끝에는 Data가 있다

```text
ServiceId
 ↓
Business
 ↓
DAO
 ↓
Mapper
 ↓
SQL
 ↓
Data
```

---

# 2. PDMG Current Data Access

## FIG-10-02. PDMG Current Data Access

```text
Handler
 ↓
Facade
 ↓
Service
 ↓
DAO
 ↓
MyBatis Mapper
 ↓
JDBC
 ↓
RDW / DB
```

---

# 3. Data Architecture는 DB Architecture보다 넓다

## FIG-10-03. Data Architecture는 DB Architecture보다 넓다

```text
Data Architecture
├─ Domain / Subject
├─ Ownership
├─ Model
├─ Flow
├─ Quality
├─ Security
├─ Lifecycle
└─ Evidence

DB Architecture
= 구현 하위영역
```

---

# 4. RDW와 ADW의 역할을 분리한다

## FIG-10-04. RDW와 ADW의 역할을 분리한다

```text
RDW
Operational
Near-real-time
Online Service

        VS

ADW
Analytical
Mart / Aggregate
Heavy Query
```

---

# 5. PDMG Current는 RDW Evidence가 더 강하다

## FIG-10-05. PDMG Current는 RDW Evidence가 더 강하다

```text
PDMG
 ↓
Datasource / Mapper
 ↓
RDW / DB
 [Strong]

PDMG → ADW
 [OPEN / Inventory needed]
```

---

# 6. ServiceId에서 Table까지 Trace한다

## FIG-10-06. ServiceId에서 Table까지 Trace한다

```text
ServiceId
 ↓
DAO
 ↓
Mapper Namespace
 ↓
SqlId
 ↓
SQL
 ↓
Table / View
```

---

# 7. Data Ownership이 없는 DML을 허용하지 않는다

## FIG-10-07. Data Ownership이 없는 DML을 허용하지 않는다

```text
Own / Approved Data
 ├─ READ
 └─ WRITE

Other System Data
 ↓
Approved Contract

Direct DML
 X
```

---

# 8. CDC와 ETL은 서로 다른 Data Movement다

## FIG-10-08. CDC와 ETL은 서로 다른 Data Movement다

```text
Change
Source DB
 ↓ CDC
RDW

Bulk
Source
 ↓ ETL
ADW
```

---

# 9. Metadata/Lineage는 역추적을 가능하게 한다

## FIG-10-09. Metadata/Lineage는 역추적을 가능하게 한다

```text
Table
 ↑
SqlId
 ↑
DAO
 ↑
Service
 ↑
ServiceId
 ↑
Application
```

---

# 10. Data Quality와 Security도 Architecture다

## FIG-10-10. Data Quality와 Security도 Architecture다

```text
Data
 ↓
Classification
 ↓
Quality Rule
 ↓
Access / Masking
 ↓
Audit
 ↓
Lifecycle
```

---

# 11. Heavy Query를 Online Path에서 격리한다

## FIG-10-11. Heavy Query를 Online Path에서 격리한다

```text
Online Query
 ↓
RDW

Heavy Analysis
 ↓
ADW

Cross impact
minimize
```

---

# 12. Data Runtime Evidence

## FIG-10-12. Data Runtime Evidence

```text
ServiceId
 ↓
SqlId
 ↓
Table
 ↓
Rows / Elapsed / Error
 ↓
GUID
 ↓
Evidence
```

---

# 13. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-10-13. Normal Pattern

```text
ServiceId
 ↓
DAO / Mapper / SqlId
 ↓
RDW
 ↓
Operational Service

Heavy Analysis
 ↓
ADW
 ↓
BI / Analytics
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 14. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-10-14. Forbidden Pattern

```text
Cross-system Direct DML      X
RDW Heavy Query 무제한         X
Ownership 없는 Write           X
PDMG ADW 사용 추정             X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 15. Architecture Decision — RDW/ADW 사용원칙

## FIG-10-15. 주안과 대안

```text
[주안]
Online/Analytical Workload 분리

        VS

[대안]
RDW 중심 통합사용
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | Online/Analytical Workload 분리 | RDW 중심 통합사용 |
| 장점 | • Online SLA 보호<br>• 자원격리<br>• Data 역할 명확 | • 구조 단순<br>• 복제 감소 |
| 단점 | • Data Movement/운영 복잡도 증가 | • Heavy Query 간섭<br>• 확장성/운영 위험 |
| 권고 | **주안 채택** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 16. Current PDMG GAP

## FIG-10-16. GAP Map

```text
Current PDMG
│
├─ RDW/ADW 실제 Datasource Mapping OPEN
├─ Table/View Ownership 미완료
├─ Lineage 자동화 미완료
├─ Data Quality Rule Evidence 미완료
└─ CDC SLA 3s vs 30s CONFLICT
```

- `[GAP/OPEN]` RDW/ADW 실제 Datasource Mapping OPEN
- `[GAP/OPEN]` Table/View Ownership 미완료
- `[GAP/OPEN]` Lineage 자동화 미완료
- `[GAP/OPEN]` Data Quality Rule Evidence 미완료
- `[GAP/OPEN]` CDC SLA 3s vs 30s CONFLICT

---

# 17. 제10장 Architecture 판정

## FIG-10-17. Assessment

```text
Architecture Definition
      ↓
PASS

Current PDMG Conformance
      ↓
PARTIAL

Runtime Evidence
      ↓
MEDIUM
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| DAO/Mapper/JDBC | PASS | 강한 Current Evidence |
| RDW | PASS/PARTIAL | Current 사용 근거 |
| ADW | OPEN | 실제 사용 Inventory 필요 |
| Ownership/Lineage | PARTIAL | Registry/자동화 필요 |
| CDC SLA | CONFLICT | 측정점/등급 결정 필요 |

---

# 18. 원천 정의서 Trace

## FIG-10-18. Source Trace

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
| 07 Data | Story/Drill-down/Current-Target 근거 |
| 06 Interface | Story/Drill-down/Current-Target 근거 |
| 13 CDC/ETL | Story/Drill-down/Current-Target 근거 |

---

# 19. 다음 장으로 넘어가는 이유

## FIG-10-19. 10장 → 11

```text
제10장
데이터플랫폼
      ↓
"이 Data와 Runtime을 사용하는 Marketing Platform에서 PDMG는 어떤 역할을 하는가?"
      ↓
제11장
마케팅플랫폼
```

---

# 20. 제10장 최종 결론

## FIG-10-20. Final Story

```text
데이터플랫폼
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

제10장의 결론은 **RDW·ADW 역할분리와 PDMG Data Access**라는 한 문장으로 정리된다.



---

<!-- CHAPTER 11: 11_NSIGHT_PDMG_아키텍처정의서_마케팅플랫폼_STORY_VISUAL_v1.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제11장. 마케팅플랫폼
## PDMG를 Marketing Platform 실행 Reference로 정의
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **10장의 Data Platform 위에서 Marketing Application이 실행될 때 PDMG가 어떤 Runtime Reference 역할을 하는지 정의한다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제10장에서 넘어온 질문
     ↓
10장의 Data Platform 위에서 Marketing Application이 실행될 때 PDMG가 어떤 Runtime Reference 역할을 하는지 정의한다.
     ↓
이 장이 답해야 할 질문
     ↓
PDMG를 Marketing Platform 실행 Reference로 정의
```

이 장의 핵심 원칙은 다음과 같다.

- PDMG는 Marketing Platform의 실행 Reference이지 전체 Target Platform과 동일하지 않다.
- `mg` Source Prefix와 `MP` Application Group은 Mapping Registry/ADR 없이 동일시하지 않는다.
- Event/Kafka/Real-time Target을 PDMG Current로 자동 승격하지 않는다.
- Program/ServiceId를 Marketing Runtime Trace의 핵심축으로 사용한다.

---

# 1. PDMG는 Marketing Platform 전체가 아니라 실행 Reference다

## FIG-11-01. PDMG는 Marketing Platform 전체가 아니라 실행 Reference다

```text
NSIGHT Marketing Platform
        ↓
Business / Application Scope
        ↓
PDMG Reference
UI / JWT / Framework / Service Runtime
```

---

# 2. PDMG Module 구조

## FIG-11-02. PDMG Module 구조

```text
pdmg-ui
pdmg-jwt
pdmg-service
  └─ pdmg-fw
pdmg-om? [UNKNOWN]
```

---

# 3. Marketing Business는 Program/ServiceId로 실행된다

## FIG-11-03. Marketing Business는 Program/ServiceId로 실행된다

```text
Business
 ↓
Program
mgcoa9001
 ↓
ServiceId
mgcoa9001S0
 ↓
Handler
 ↓
Business Runtime
```

---

# 4. Source Business Axis

## FIG-11-04. Source Business Axis

```text
mg | co | a | 9001
│    │    │    │
│    │    │    └ Program No
│    │    └ Function
│    └ Business
└ Application/Major
```

---

# 5. NSIGHT Application Group MP와 PDMG mg는 자동 동일하지 않다

## FIG-11-05. NSIGHT Application Group MP와 PDMG mg는 자동 동일하지 않다

```text
NSIGHT Target
MP
 │
 │ Mapping Registry / ADR
 ▼
PDMG AS-IS
mg

MP ≠ mg
unless approved
```

---

# 6. 마케팅 실행경로

## FIG-11-06. 마케팅 실행경로

```text
UI
 ↓
ServiceId
 ↓
Handler / Facade
 ↓
Service
 ↓
DAO / RDW
 ↓
Result
```

---

# 7. Marketing Platform의 실시간 Target과 PDMG Current를 구분한다

## FIG-11-07. Marketing Platform의 실시간 Target과 PDMG Current를 구분한다

```text
Target Marketing
Event / Kafka / Real-time Decision
        │
        │ reference
        ▼
PDMG Current
HTTP / TCF / TX / RDW
```

---

# 8. 외부 연계는 Marketing Business 내부코드가 아니라 Contract로 분리한다

## FIG-11-08. 외부 연계는 Marketing Business 내부코드가 아니라 Contract로 분리한다

```text
Marketing Service
 ↓
Interface Contract
 ↓
API / Event / File
 ↓
External Platform
```

---

# 9. Security는 Marketing Platform의 공통 기반이다

## FIG-11-09. Security는 Marketing Platform의 공통 기반이다

```text
User
 ↓
JWT / SSO
 ↓
Principal
 ↓
Marketing ServiceId
 ↓
Authorization
```

---

# 10. Marketing Platform의 Runtime Evidence

## FIG-11-10. Marketing Platform의 Runtime Evidence

```text
Campaign / User Action
 ↓
ServiceId
 ↓
GUID
 ↓
Business Runtime
 ↓
Data / External Call
 ↓
Evidence
```

---

# 11. PDMG를 Target에 맞추는 방식

## FIG-11-11. PDMG를 Target에 맞추는 방식

```text
PDMG AS-IS
 ↓
Mapping Registry
 ↓
GAP
 ↓
ADR
 ↓
Target Alignment
```

---

# 12. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-11-12. Normal Pattern

```text
NSIGHT MP
 ↓
Approved Mapping
 ↓
PDMG Program / ServiceId
 ↓
Business Runtime
 ↓
RDW / Approved Interface
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 13. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-11-13. Forbidden Pattern

```text
MP = mg 자동치환                   X
Target Event/Kafka = Current PDMG      X
Marketing Platform 전체 = pdmg-service X
External 연계 = 내부 DB Direct          X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 14. Architecture Decision — MP↔PDMG 코드 Mapping

## FIG-11-14. 주안과 대안

```text
[주안]
Mapping Registry + ADR

        VS

[대안]
`mg`를 `MP`로 일괄 치환
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | Mapping Registry + ADR | `mg`를 `MP`로 일괄 치환 |
| 장점 | • AS-IS 보존<br>• Traceability<br>• 단계적 전환 | • 표면상 코드 통일 |
| 단점 | • Registry 관리 필요 | • Source/운영 ID 영향<br>• 충돌/역추적 문제 |
| 권고 | **주안 채택** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 15. Current PDMG GAP

## FIG-11-15. GAP Map

```text
Current PDMG
│
├─ MP↔mg 공식 Mapping 미승인
├─ Marketing External Interface Inventory OPEN
├─ JWT Security Critical GAP
├─ pdmg-om 범위 UNKNOWN
└─ Event/Kafka Current Ownership OPEN
```

- `[GAP/OPEN]` MP↔mg 공식 Mapping 미승인
- `[GAP/OPEN]` Marketing External Interface Inventory OPEN
- `[GAP/OPEN]` JWT Security Critical GAP
- `[GAP/OPEN]` pdmg-om 범위 UNKNOWN
- `[GAP/OPEN]` Event/Kafka Current Ownership OPEN

---

# 16. 제11장 Architecture 판정

## FIG-11-16. Assessment

```text
Architecture Definition
      ↓
CONDITIONAL PASS

Current PDMG Conformance
      ↓
PARTIAL

Runtime Evidence
      ↓
MEDIUM
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| PDMG Runtime Reference | PASS | UI/JWT/service/fw 근거 |
| Program/ServiceId | PASS | Current naming/registry |
| MP Mapping | OPEN | Registry/ADR 필요 |
| Real-time Target | REFERENCE | PDMG Current로 승격 금지 |
| Security | GAP | JWT/Identity |

---

# 17. 원천 정의서 Trace

## FIG-11-17. Source Trace

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
| 03 Application | Story/Drill-down/Current-Target 근거 |
| 06 Interface | Story/Drill-down/Current-Target 근거 |
| 11 Security | Story/Drill-down/Current-Target 근거 |
| 15 Naming | Story/Drill-down/Current-Target 근거 |

---

# 18. 다음 장으로 넘어가는 이유

## FIG-11-18. 11장 → 12

```text
제11장
마케팅플랫폼
      ↓
"Marketing과 Operational Data를 분석하는 BI는 PDMG와 어떤 경계로 연결되어야 하는가?"
      ↓
제12장
BI 포탈
```

---

# 19. 제11장 최종 결론

## FIG-11-19. Final Story

```text
마케팅플랫폼
   ↓
Responsibility
   ↓
Boundary
   ↓
Runtime
   ↓
Evidence
   ↓
CONDITIONAL PASS
```

제11장의 결론은 **PDMG를 Marketing Platform 실행 Reference로 정의**라는 한 문장으로 정리된다.



---

<!-- CHAPTER 12: 12_NSIGHT_PDMG_아키텍처정의서_BI_포탈_STORY_VISUAL_v1.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제12장. BI 포탈
## PDMG 경계 밖의 분석 소비계층과 Data Contract
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **11장의 Marketing Runtime과 10장의 Data Platform을 소비하는 BI를 별도 Analytical Boundary로 정의한다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제11장에서 넘어온 질문
     ↓
11장의 Marketing Runtime과 10장의 Data Platform을 소비하는 BI를 별도 Analytical Boundary로 정의한다.
     ↓
이 장이 답해야 할 질문
     ↓
PDMG 경계 밖의 분석 소비계층과 Data Contract
```

이 장의 핵심 원칙은 다음과 같다.

- BI Portal은 PDMG Current 내부 Module로 정의하지 않는다.
- Operational Runtime과 Analytical Runtime을 분리한다.
- BI와 PDMG의 연결은 DAO/내부 DB가 아니라 Data Contract다.
- Dataset Ownership/Freshness/Security를 Architecture에 포함한다.

---

# 1. BI Portal은 PDMG 내부 Module이 아니다

## FIG-12-01. BI Portal은 PDMG 내부 Module이 아니다

```text
PDMG
= Operational Application Runtime

BI Portal
= Analytical Consumer

둘은 다른 Application Boundary
```

---

# 2. BI의 기본 Data Flow

## FIG-12-02. BI의 기본 Data Flow

```text
Operational Source
 ↓
RDW
 ↓
ADW
 ↓
BI Portal
 ↓
Report / Self-BI / Analysis
```

---

# 3. Operational과 Analytical Runtime을 분리한다

## FIG-12-03. Operational과 Analytical Runtime을 분리한다

```text
PDMG Online
 ↓
bounded latency
 ↓
RDW

BI / Analytics
 ↓
heavy query
 ↓
ADW
```

---

# 4. BI는 PDMG 내부 DAO를 호출하지 않는다

## FIG-12-04. BI는 PDMG 내부 DAO를 호출하지 않는다

```text
BI Portal
 ↓
Approved Data Contract
 ↓
ADW / Data Service

BI → PDMG DAO
X
```

---

# 5. BI Data Contract

## FIG-12-05. BI Data Contract

```text
InterfaceId / Dataset
Source
Target
Schema
Freshness
Security
Owner
Version
SLA
```

---

# 6. BI에서 필요한 Data Governance

## FIG-12-06. BI에서 필요한 Data Governance

```text
Business Term
 ↓
Data Subject
 ↓
Owner / Steward
 ↓
Metric Definition
 ↓
Dataset
 ↓
BI Report
```

---

# 7. BI 권한은 Application Login만으로 끝나지 않는다

## FIG-12-07. BI 권한은 Application Login만으로 끝나지 않는다

```text
User
 ↓
Authentication
 ↓
BI Role
 ↓
Dataset Permission
 ↓
Column/Row Access
 ↓
Audit
```

---

# 8. BI Refresh는 Runtime SLA다

## FIG-12-08. BI Refresh는 Runtime SLA다

```text
Source Change
 ↓
CDC / ETL
 ↓
ADW Refresh
 ↓
BI Dataset
 ↓
Report Freshness
```

---

# 9. BI 성능을 PDMG WAS에 전가하지 않는다

## FIG-12-09. BI 성능을 PDMG WAS에 전가하지 않는다

```text
Heavy Query
 ↓
ADW / BI Runtime

not

PDMG WAS / RDW Online
```

---

# 10. BI와 PDMG의 연결은 Data Contract다

## FIG-12-10. BI와 PDMG의 연결은 Data Contract다

```text
PDMG / Data Platform
        ↓
Approved Data Flow
        ↓
ADW / BI
```

---

# 11. BI는 Target Reference로 관리한다

## FIG-12-11. BI는 Target Reference로 관리한다

```text
PDMG Current Evidence
        ↓
BI implementation?
        ↓
No direct evidence
        ↓
TARGET REFERENCE / N-A
```

---

# 12. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-12-12. Normal Pattern

```text
Operational Data
 ↓
RDW
 ↓
ADW
 ↓
Approved Data Contract
 ↓
BI Portal / Self-BI
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 13. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-12-13. Forbidden Pattern

```text
BI → PDMG DAO                X
BI → PDMG 내부 Table DML       X
Heavy Query → Online RDW       X
BI Runtime → PDMG JVM 혼재      X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 14. Architecture Decision — BI 연결방식

## FIG-12-14. 주안과 대안

```text
[주안]
ADW / Data Contract 기반

        VS

[대안]
PDMG 내부 DB/DAO 직접 접근
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | ADW / Data Contract 기반 | PDMG 내부 DB/DAO 직접 접근 |
| 장점 | • 결합도 낮음<br>• 분석 Workload 격리<br>• Data Governance 적용 | • 초기 개발 쉬움 |
| 단점 | • Data Pipeline 필요 | • 강결합<br>• Online 영향<br>• Ownership 붕괴 |
| 권고 | **주안 채택** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 15. Current PDMG GAP

## FIG-12-15. GAP Map

```text
Current PDMG
│
├─ BI Target 상세 Current Evidence 없음
├─ Data Contract/Interface Inventory 미완료
├─ ADW Freshness/SLA OPEN
└─ Dataset Owner/Security Matrix OPEN
```

- `[GAP/OPEN]` BI Target 상세 Current Evidence 없음
- `[GAP/OPEN]` Data Contract/Interface Inventory 미완료
- `[GAP/OPEN]` ADW Freshness/SLA OPEN
- `[GAP/OPEN]` Dataset Owner/Security Matrix OPEN

---

# 16. 제12장 Architecture 판정

## FIG-12-16. Assessment

```text
Architecture Definition
      ↓
TARGET REFERENCE / CONDITIONAL

Current PDMG Conformance
      ↓
N-A / OPEN

Runtime Evidence
      ↓
LOW
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| PDMG Boundary | PASS | BI 외부 Boundary로 정의 |
| Data Contract | PASS architecture | 실제 Catalog 필요 |
| ADW/BI Runtime | TARGET REFERENCE | Current PDMG evidence 아님 |
| Security/Ownership | OPEN | BI 상세 설계 필요 |
| Runtime Evidence | OPEN | BI platform evidence 필요 |

---

# 17. 원천 정의서 Trace

## FIG-12-17. Source Trace

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
| 06 Interface | Story/Drill-down/Current-Target 근거 |
| 07 Data | Story/Drill-down/Current-Target 근거 |
| 18 Integrated Target | Story/Drill-down/Current-Target 근거 |

---

# 18. 다음 장으로 넘어가는 이유

## FIG-12-18. 12장 → 13

```text
제12장
BI 포탈
      ↓
"이 모든 구조를 시간이 지나도 Source와 Runtime에 맞게 유지하려면 무엇이 필요한가?"
      ↓
제13장
표준화와 10년 지속 가능성
```

---

# 19. 제12장 최종 결론

## FIG-12-19. Final Story

```text
BI 포탈
   ↓
Responsibility
   ↓
Boundary
   ↓
Runtime
   ↓
Evidence
   ↓
TARGET REFERENCE / CONDITIONAL
```

제12장의 결론은 **PDMG 경계 밖의 분석 소비계층과 Data Contract**라는 한 문장으로 정리된다.



---

<!-- CHAPTER 13: 13_NSIGHT_PDMG_아키텍처정의서_표준화와_10년_지속가능성_STORY_VISUAL_v1.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제13장. 표준화와 10년 지속 가능성
## Naming·DevOps·Observability·Traceability로 Architecture를 유지
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **12장까지 정의한 Application/Data/Runtime 구조를 시간이 지나도 Source와 Deployment에 정합하게 유지하는 운영체계를 정의한다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제12장에서 넘어온 질문
     ↓
12장까지 정의한 Application/Data/Runtime 구조를 시간이 지나도 Source와 Deployment에 정합하게 유지하는 운영체계를 정의한다.
     ↓
이 장이 답해야 할 질문
     ↓
Naming·DevOps·Observability·Traceability로 Architecture를 유지
```

이 장의 핵심 원칙은 다음과 같다.

- 표준은 CI/Runtime에서 검증 가능한 Rule이어야 한다.
- SourceCommit→ArtifactHash→DeploymentId→ServiceId→GUID를 연결한다.
- Architecture PASS와 Implementation PASS를 분리한다.
- Critical Drift는 GAP 또는 ADR로 반드시 닫는다.

---

# 1. Architecture는 시간이 지나면 자동으로 낡는다

## FIG-13-01. Architecture는 시간이 지나면 자동으로 낡는다

```text
Architecture Document
      ↓ time
Source Changes
Config Changes
Deployment Changes
      ↓
Drift
```

---

# 2. 지속 가능성의 시작은 Naming이다

## FIG-13-02. 지속 가능성의 시작은 Naming이다

```text
Business
 ↓
Program
 ↓
ServiceId
 ↓
Package / Class
 ↓
Mapper / SqlId
 ↓
Artifact / Deployment
```

---

# 3. ServiceId는 개발표준과 운영증적을 연결한다

## FIG-13-03. ServiceId는 개발표준과 운영증적을 연결한다

```text
ServiceId
 ↓
Handler
 ↓
Facade / Service
 ↓
DAO / SqlId
 ↓
GUID / Log
 ↓
Metric / Evidence
```

---

# 4. 표준은 문서가 아니라 Rule이어야 한다

## FIG-13-04. 표준은 문서가 아니라 Rule이어야 한다

```text
Standard Document
 ↓
Machine-readable Rule
 ↓
Scanner
 ↓
CI Gate
 ↓
PASS / FAIL
```

---

# 5. Build Once / Promote Artifact

## FIG-13-05. Build Once / Promote Artifact

```text
Source Commit
 ↓
Build
 ↓
Artifact Hash
 ↓
DEV
 ↓
TEST
 ↓
PROD
 ↓
DR

same binary
```

---

# 6. Config와 Secret을 Artifact에서 분리한다

## FIG-13-06. Config와 Secret을 Artifact에서 분리한다

```text
Artifact
= code/binary

Environment Config
= externalized

Secret / Key
= protected store
```

---

# 7. Deployment Trace를 만든다

## FIG-13-07. Deployment Trace를 만든다

```text
sourceCommit
 ↓
buildId
 ↓
artifactHash
 ↓
deploymentId
 ↓
WAR / JVM / Host
 ↓
ServiceId / GUID
```

---

# 8. Observability를 Runtime Evidence로 확장한다

## FIG-13-08. Observability를 Runtime Evidence로 확장한다

```text
Metric
+ Log
+ Trace
 ↓
ServiceId / GUID
 ↓
Deployment / Host
 ↓
Rule Evidence
```

---

# 9. Drift를 자동 탐지한다

## FIG-13-09. Drift를 자동 탐지한다

```text
Architecture Baseline
 ↓ compare
Source / Config / Deployment / Runtime
 ↓
Drift
 ↓
GAP / ADR / Fix
```

---

# 10. Architecture Decision을 Baseline에 반영한다

## FIG-13-10. Architecture Decision을 Baseline에 반영한다

```text
Decision Task
 ↓
주안 / 대안
 ↓
Evidence
 ↓
ADR
 ↓
Rule / Model / Standard
 ↓
New Baseline
```

---

# 11. G00부터 HG90까지

## FIG-13-11. G00부터 HG90까지

```text
G00 Source
 ↓
G10 Document
 ↓
G20 Model
 ↓
G30 Conformance
 ↓
G40 Test
 ↓
G50 Runtime Evidence
 ↓
G60 Drift
 ↓
G70 GAP / ADR
 ↓
G80 Approval
 ↓
HG90
```

---

# 12. 10년 지속 가능한 Architecture의 의미

## FIG-13-12. 10년 지속 가능한 Architecture의 의미

```text
좋은 Architecture
= 처음 잘 그린 그림
        X

좋은 Architecture
= 변화할 때마다
  Source / Runtime과
  다시 정합되는 체계
        O
```

---

# 13. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-13-13. Normal Pattern

```text
Architecture
 ↓
Rule / Naming
 ↓
CI Test
 ↓
Immutable Artifact
 ↓
Deployment
 ↓
Runtime Evidence
 ↓
Drift
 ↓
ADR
 ↓
New Baseline / HG90
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 14. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-13-14. Forbidden Pattern

```text
표준문서만 작성, 자동검증 없음 X
Environment별 재빌드             X
Secret을 Artifact에 포함          X
Runtime Evidence 없이 PASS        X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 15. Architecture Decision — Architecture Governance

## FIG-13-15. 주안과 대안

```text
[주안]
CI + Runtime Evidence Gate

        VS

[대안]
문서 Review / 수동점검
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | CI + Runtime Evidence Gate | 문서 Review / 수동점검 |
| 장점 | • 지속적 Conformance<br>• Drift 조기탐지<br>• HG90 신뢰성 | • 초기 도입 단순 |
| 단점 | • 자동화 투자 필요 | • 사람 의존<br>• 누락/노후화<br>• Runtime 불일치 |
| 권고 | **주안 채택** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 16. Current PDMG GAP

## FIG-13-16. GAP Map

```text
Current PDMG
│
├─ Naming Scanner CI Enforcement 미완료
├─ Artifact/Deployment Identity 표준 OPEN
├─ Runtime Evidence Collector 미완료
├─ pdmg-om/OM Control Plane Current 상세 OPEN
└─ Critical ADR 일부 미종결
```

- `[GAP/OPEN]` Naming Scanner CI Enforcement 미완료
- `[GAP/OPEN]` Artifact/Deployment Identity 표준 OPEN
- `[GAP/OPEN]` Runtime Evidence Collector 미완료
- `[GAP/OPEN]` pdmg-om/OM Control Plane Current 상세 OPEN
- `[GAP/OPEN]` Critical ADR 일부 미종결

---

# 17. 제13장 Architecture 판정

## FIG-13-17. Assessment

```text
Architecture Definition
      ↓
CONDITIONAL PASS

Current PDMG Conformance
      ↓
PARTIAL / GAP

Runtime Evidence
      ↓
MEDIUM
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| Naming/ServiceId | PASS/PARTIAL | Current 규칙 강함, 일부 Enterprise naming OPEN |
| CI Rule Gate | PARTIAL | 자동화 미완료 |
| Artifact Promotion | PASS architecture | 실제 pipeline evidence 필요 |
| Observability | PARTIAL | GUID/Log 강함, full trace 미완료 |
| HG90 | OPEN | Critical Gate/Evidence 필요 |

---

# 18. 원천 정의서 Trace

## FIG-13-18. Source Trace

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
| 14 DevOps/OM/Observability | Story/Drill-down/Current-Target 근거 |
| 15 Naming | Story/Drill-down/Current-Target 근거 |
| 17 Traceability | Story/Drill-down/Current-Target 근거 |
| 18 Integrated Baseline | Story/Drill-down/Current-Target 근거 |

---

# 19. 다음 장으로 넘어가는 이유

## FIG-13-19. 13장 → HG90

```text
제13장
표준화와 10년 지속 가능성
      ↓
"Critical GAP와 Runtime Evidence를 닫고 공식 Baseline으로 Release할 수 있는가?"
      ↓
Architecture Baseline
HG90 Evidence-backed Architecture Baseline
```

---

# 20. 제13장 최종 결론

## FIG-13-20. Final Story

```text
표준화와 10년 지속 가능성
   ↓
Responsibility
   ↓
Boundary
   ↓
Runtime
   ↓
Evidence
   ↓
CONDITIONAL PASS
```

제13장의 결론은 **Naming·DevOps·Observability·Traceability로 Architecture를 유지**라는 한 문장으로 정리된다.



---
