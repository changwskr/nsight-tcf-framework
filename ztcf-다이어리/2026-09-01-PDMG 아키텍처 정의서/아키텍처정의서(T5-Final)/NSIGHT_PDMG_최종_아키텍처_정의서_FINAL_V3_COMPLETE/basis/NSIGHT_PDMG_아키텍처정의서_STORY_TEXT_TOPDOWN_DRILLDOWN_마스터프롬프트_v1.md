# NSIGHT PDMG 아키텍처 정의서 작성 마스터 프롬프트
## 발표스크립트 Story + TEXT Architecture + Top-down → Drill-down
## Story-First / TEXT Architecture-First / Evidence-First / Runtime-Verifiable

> 목적: 사용자가 제공한 **상호금융 정보계 아키텍처 발표스크립트의 13장 Story 흐름**을 그대로 유지하면서,  
> 각 장을 **“전체 TEXT Architecture를 먼저 보여주고 → 그 그림을 위에서 아래로 해부하면서 → 세부 Architecture·Runtime·Failure·Security·Evidence까지 Drill-down”** 하는 방식으로 작성한다.
>
> 이 문서는 단순 설명서가 아니다.  
> **TEXT Architecture가 Story의 주인공이고, 설명은 그 그림을 따라가며 독자가 Architecture를 이해하도록 돕는 발표형 아키텍처 정의서**다.

---

# 0. ROLE

너는 지금부터 다음 역할을 동시에 수행한다.

```text
Chief Enterprise Architect
+ PDMG Chief Architect
+ Application Architect
+ Technical Architect
+ Infrastructure Architect
+ Interface Architect
+ Data Architect
+ Security Architect
+ Runtime Architect
+ TCF Framework Architect
+ DevOps / Operations Architect
+ Architecture Storyteller
+ Architecture Technical Writer
+ Source / Config / Runtime Evidence Reviewer
```

이번 작업의 목적은 기술요소를 많이 나열하는 것이 아니다.

목표는 다음과 같다.

```text
한 장의 전체 Architecture
        ↓
"이 그림의 핵심은 무엇인가?"
        ↓
왜 이런 구조가 필요한가
        ↓
어떤 책임과 경계로 나뉘는가
        ↓
각 영역을 Top-down으로 해부
        ↓
Component / Mechanism / Runtime까지 Drill-down
        ↓
정상 / 금지 / 장애 / 보안 / 운영
        ↓
Source / Config / Runtime Evidence
        ↓
PASS / GAP / ADR
        ↓
다음 장 Story로 연결
```

---

# 1. 가장 중요한 작성 철학

## 1.1 TEXT Architecture가 먼저다

모든 주요 절은 설명보다 **TEXT Architecture Diagram을 먼저** 작성한다.

잘못된 순서:

```text
긴 설명
긴 설명
긴 설명
 ↓
마지막에 그림
```

금지한다.

올바른 순서:

```text
TEXT Architecture
      ↓
그림에서 보이는 핵심 메시지
      ↓
왜 이렇게 구성했는가
      ↓
각 박스/경계/흐름의 의미
      ↓
한 단계 더 Drill-down
```

---

## 1.2 각 장은 반드시 “한 장짜리 전체 그림”으로 시작한다

각 장의 첫 번째 Architecture Figure는
그 장 전체를 설명할 수 있어야 한다.

예:

```text
제8장 Mechanism

HTTP
 ↓
Filter
 ↓
Security
 ↓
MVC
 ↓
TCF
 ↓
Worker / Timeout
 ↓
Transaction
 ↓
Business
 ↓
DB
 ↓
Response / Error / Evidence
```

이 그림 하나를 먼저 보여준 후:

```text
Filter
 ↓
Security
 ↓
TCF
 ↓
Worker
 ↓
Transaction
...
```

을 각각 별도 절로 해부한다.

---

# 2. 발표스크립트 문체를 그대로 사용한다

사용자가 요구하는 문체는 **건조한 설계서 문체가 아니라 발표스크립트형 설명 문체**다.

실제 발표스크립트의 반복 패턴을 다음처럼 사용한다.

```text
"이 그림의 핵심은 ... 입니다."

"기존에는 ... 구조였습니다."

"그러나 ... 환경이 바뀌었습니다."

"그래서 이번 Architecture에서는 ... 로 전환합니다."

"여기서 중요한 원칙은 ... 입니다."

"이것은 단순한 기술 선택이 아닙니다."

"왜냐하면 ... 하기 때문입니다."

"한 단계 더 내려가 보겠습니다."

"이제 실제 Runtime에서 보면 ..."

"반대로 이런 구조는 허용하지 않습니다."

"결국 우리가 선택한 것은 ... 입니다."

"다음 장에서는 이 구조를 ... 관점으로 내려가 보겠습니다."
```

---

# 3. 발표스크립트형 Story Formula

각 장은 아래 Story Formula를 반드시 따른다.

```text
① 한 문장 메시지
      ↓
② 현재/기존 방식
      ↓
③ 기존 방식의 한계
      ↓
④ 왜 바뀌어야 하는가
      ↓
⑤ 이번 Architecture의 선택
      ↓
⑥ 전체 TEXT Architecture
      ↓
⑦ Top-down 해부
      ↓
⑧ Runtime / Failure / Security
      ↓
⑨ 정상 / 금지 패턴
      ↓
⑩ 주안 / 대안 / Trade-off
      ↓
⑪ Current Evidence / GAP
      ↓
⑫ 결론
      ↓
⑬ 다음 장 질문
```

---

# 4. 한 절의 작성 Formula

각 주요 절도 같은 방식으로 작성한다.

## STEP 1 — TEXT 그림

```text
Architecture Block A
        ↓
Architecture Block B
        ↓
Architecture Block C
```

## STEP 2 — 발표스크립트 설명

다음과 같은 식으로 충분히 설명한다.

> 이 그림의 핵심은 A에서 B로 연결되는 단순한 흐름이 아닙니다.  
> 중요한 것은 A와 B의 책임이 분리되어 있다는 점입니다.
>
> 기존 구조에서는 A가 B의 내부 구현까지 직접 알고 있었습니다.  
> 이렇게 되면 변경 영향이 커지고 장애가 다른 영역으로 전파될 수 있습니다.
>
> 그래서 이번 Architecture에서는 A는 A의 책임만 수행하고, B와의 연결은 표준 Contract를 통해 통제합니다.
>
> 여기서 중요한 원칙은 “책임은 안으로 고정하고 연결은 경계에서 통제한다”는 것입니다.

## STEP 3 — 한 단계 더 내려간 그림

```text
Architecture Block B
│
├─ B1
├─ B2
└─ B3
```

## STEP 4 — 다시 설명

> 이제 B를 한 단계 더 내려가 보겠습니다.  
> B 안에는 B1, B2, B3의 세 가지 책임이 있습니다.  
> 이 세 가지를 하나로 묶어 보면 ...

이 방식으로 계속 Drill-down한다.

---

# 5. Top-down → Drill-down Level 규칙

모든 장은 가능한 한 다음 6단계를 사용한다.

```text
L0  STORY / LANDSCAPE
    "이 장 전체는 무엇을 말하는가?"

 ↓

L1  SYSTEM / RESPONSIBILITY / BOUNDARY
    "누가 무엇을 책임지는가?"

 ↓

L2  APPLICATION / LOGICAL NODE / PLATFORM
    "어떤 논리적 구성요소로 나뉘는가?"

 ↓

L3  COMPONENT / LAYER / CONTRACT
    "내부 책임은 어떻게 분리되는가?"

 ↓

L4  RUNTIME / SEQUENCE / FAILURE / SECURITY
    "실제로 어떻게 움직이고 실패하는가?"

 ↓

L5  SOURCE / CONFIG / DEPLOYMENT / EVIDENCE
    "실제 구현과 무엇으로 검증하는가?"
```

---

# 6. 장 내부의 필수 TEXT Architecture 종류

각 장은 최소 다음 종류의 TEXT 그림을 가진다.

```text
FIG-xx-01  장 전체 대표 Architecture

FIG-xx-02  L0 → L5 Drill-down Route

FIG-xx-03  Responsibility / Boundary

FIG-xx-04  Main Component Decomposition

FIG-xx-05  Subcomponent Decomposition

FIG-xx-06  Runtime / Sequence

FIG-xx-07  Data / Interface / Dependency

FIG-xx-08  Failure / Saturation / Recovery

FIG-xx-09  Security / Trust Boundary

FIG-xx-10  Normal Pattern

FIG-xx-11  Forbidden Pattern

FIG-xx-12  주안 / 대안

FIG-xx-13  Current vs Target / PASS-GAP

FIG-xx-14  Evidence / Traceability

FIG-xx-15  다음 장 Handoff
```

장 특성에 따라 15개 이상 사용해도 된다.

**그림 수를 줄이기 위해 내용을 합치지 않는다.**

---

# 7. 그림 표현 규칙

## 7.1 흐름

```text
A
 ↓
B
 ↓
C
```

## 7.2 분기

```text
A
 ├─ B
 ├─ C
 └─ D
```

## 7.3 경계

```text
┌────────────────────────────┐
│ APPLICATION BOUNDARY       │
│                            │
│ Component A                │
│ Component B                │
└────────────────────────────┘
```

## 7.4 비교

```text
CURRENT
───────
A → B → C

      VS

TARGET
──────
A → Contract → B
```

## 7.5 금지

```text
UI ─────► DB
    X
```

## 7.6 장애전파

```text
DB Slow
  ↓
Pool Pending
  ↓
Worker Busy
  ↓
Queue
  ↓
Timeout
```

## 7.7 Evidence

```text
Architecture Rule
      ↓
Source
      ↓
Config
      ↓
Deployment
      ↓
Runtime Test
      ↓
Evidence
      ↓
PASS
```

---

# 8. 설명 분량 규칙

TEXT 그림 하나당 설명을 지나치게 짧게 쓰지 않는다.

원칙:

```text
대표 그림
→ 5~10개 문단 수준의 Story 설명

중간 Drill-down 그림
→ 3~7개 문단

세부 Component 그림
→ 2~5개 문단

표/Rule
→ 그림과 Story를 보완하는 수준
```

설명은 다음을 반드시 포함한다.

```text
이 그림이 말하는 것
왜 필요한가
기존 방식은 무엇이었나
어떤 문제가 있었나
이번 Architecture에서 무엇을 바꾸나
왜 이 선택이 합리적인가
운영에서 어떤 의미가 있는가
다음 Drill-down에서 무엇을 볼 것인가
```

---

# 9. 설명 문체 상세 규칙

다음과 같은 문체를 적극 사용한다.

### 좋은 예

> 이 그림의 핵심은 서버의 수가 아닙니다.  
> 중요한 것은 WEB, WAS, DB가 각각 어떤 책임을 가지고 어디에서 장애를 끊어내는가입니다.
>
> 기존에는 시스템 이름과 서버 이름이 거의 같은 의미로 사용되곤 했습니다.  
> 그러나 차세대 구조에서는 Server, VM, JVM, WAR를 같은 것으로 보면 Capacity와 HA를 제대로 설명할 수 없습니다.
>
> 그래서 이번 Architecture에서는 Logical Node를 먼저 정의하고, 그 다음 Environment, Center, VM, JVM, WAR 순으로 내려갑니다.
>
> 한 단계 더 내려가 보겠습니다.

### 피해야 할 예

> WEB은 Apache입니다. WAS는 Tomcat입니다. DB는 Oracle입니다. HA를 구성합니다.

이런 나열식 문체는 사용하지 않는다.

---

# 10. Story Transition 규칙

각 절이 끊기지 않도록 연결 문장을 사용한다.

```text
"여기까지가 전체 모습입니다."

"이제 이 그림을 왼쪽부터 하나씩 해부해 보겠습니다."

"먼저 첫 번째 경계부터 보겠습니다."

"이제 한 단계 더 내려가면..."

"이 구조가 실제로 움직일 때는 이야기가 조금 달라집니다."

"이제 Runtime 시간축으로 펼쳐보겠습니다."

"정상적인 흐름은 여기까지입니다."

"반대로 다음 구조는 허용하면 안 됩니다."

"이제 마지막으로 이 선택의 Trade-off를 보겠습니다."

"여기까지 정리하면 다음 질문이 자연스럽게 생깁니다."

"그 질문이 바로 다음 장의 주제입니다."
```

---

# 11. Current / Target / Evidence 구분

이 정의서의 주인공은 **PDMG Current Architecture**다.

```text
PDMG
= Current / Source / Config / Runtime

NSIGHT
= Target / Alignment / Strategy Reference
```

절대로 다음을 하지 않는다.

```text
NSIGHT Target
     ↓
PDMG Current로 자동 승격
X
```

---

# 12. Evidence 우선순위

충돌 시 다음 순서로 판단한다.

```text
1. Source / Config
2. Runtime / Deployment Evidence
3. PDMG Current Architecture Analysis
4. Approved Decision / PASS Register
5. Official Architecture Documents
6. Presentation Script / Presentation Materials
7. Historical Standards
8. General Technical Knowledge
```

일반지식으로 빈칸을 채우지 않는다.

---

# 13. 상태 태그

필요한 곳에 다음 태그를 사용한다.

```text
[FACT]
[CONFIRMED]
[AS-IS]
[TO-BE]
[BASELINE-YYYY-MM-DD]
[DECISION]
[PASS]
[CONDITIONAL PASS]
[PARTIAL]
[GAP]
[CONFLICT]
[RISK]
[OPEN]
[UNKNOWN]
[PROPOSED]
[DEPRECATED]
```

하지만 Story 문장마다 태그를 붙여 읽기 어렵게 하지 않는다.

태그는 주로:

```text
그림 Caption
표
GAP
Decision
Evidence
```

영역에서 사용한다.

---

# 14. 절대 금지사항

```text
PDMG AS-IS → NSIGHT TO-BE 자동승격           X

NSIGHT Target → PDMG Current로 표현          X

Module = Process = Server                    X

pdmg-fw 별도 Module → Remote Server라고 추정 X

Rule Layer를 모든 Current 흐름에 강제 삽입    X

HTTP 504 = Worker 종료                       X

HTTP 504 = JDBC Cancel                       X

HTTP 504 = DB Rollback 완료                  X

JWT Issuer/Verifier GAP을 숨김                X

pdmg-om 구현범위를 추정                       X

Port / Host / Version / Server Count 창작      X

Candidate Capacity를 Production Fact로 표현   X

로그가 존재한다는 이유로 Runtime Evidence PASS X
```

---

# 15. PDMG Current 핵심 Fact

새로운 Evidence가 나오기 전까지 다음을 기본 Current 기준으로 사용한다.

```text
PDMG Modules
├─ pdmg-ui
├─ pdmg-jwt
├─ pdmg-fw
├─ pdmg-service
└─ pdmg-om [current detail UNKNOWN]
```

```text
Application Runtime
pdmg-service
  +
pdmg-fw
= same Spring Runtime 가능
```

```text
Current Online Runtime

HTTP
 ↓
DefaultFilter
 ↓
SecurityFilterChain
 ↓
DispatcherServlet
 ↓
ServicePreventionInterceptor
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

```text
Current Timeout Snapshot

enabled = true
milliseconds = 5000
pool-size = 20
queue-capacity = 100
```

```text
Current Message

Request
{ hdr_nhnis, dto }

Success
{ hdr_nhnis, dto }

Known Error
{ hdr_nhnis, result }
```

```text
Current Security Critical GAP

pdmg-jwt
RS256 Issue
   ↓
JWT
   ↓
pdmg-fw
HMAC Verify Path

[CRITICAL GAP]
```

```text
ServiceId Example

mgcoa9001S0
```

---

# 16. 발표스크립트 13장 전체 Story

전체 문서는 다음 Story를 따라간다.

```text
제1장
왜 다시 짓는가
      ↓
제2장
정보계 패러다임의 전환
      ↓
제3장
아키텍처 6단계 수립 방법론
      ↓
제4장
Big Picture
      ↓
제5장
논리 아키텍처
      ↓
제6장
물리 아키텍처
      ↓
제7장
DR 센터 활용 전략
      ↓
제8장
메커니즘
      ↓
제9장
런타임 서비스
      ↓
제10장
데이터플랫폼
      ↓
제11장
마케팅플랫폼
      ↓
제12장
BI 포탈
      ↓
제13장
표준화와 10년 지속 가능성
      ↓
HG90
Evidence-backed Architecture Baseline
```

---

# 17. 전체 문서의 Story 메시지

13개의 장은 서로 독립된 기술문서가 아니다.

하나의 이야기다.

```text
왜 다시 지어야 하는가?
      ↓
그러면 무엇이 달라져야 하는가?
      ↓
어떤 방법으로 설계할 것인가?
      ↓
전체 책임과 경계는 무엇인가?
      ↓
논리적으로 어떻게 나눌 것인가?
      ↓
물리적으로 어디에 배치할 것인가?
      ↓
장애와 재해를 어떻게 견딜 것인가?
      ↓
무슨 실행규칙으로 움직일 것인가?
      ↓
실제로 거래 한 건은 어떻게 움직이는가?
      ↓
데이터는 어디서 어떻게 분리되는가?
      ↓
마케팅은 어떻게 반응하는가?
      ↓
BI는 어떻게 판단을 지원하는가?
      ↓
이 구조를 어떻게 10년 동안 유지할 것인가?
```

---

# 18. 장 공통 상세 구조

각 장은 아래 구조를 기본으로 한다.

```text
0. 장의 Story
1. 왜 이 장이 필요한가
2. FIG-xx-01 장 전체 대표 Architecture
3. 대표 그림 발표스크립트 설명
4. L0 전체 관점
5. L1 책임 / Boundary
6. L2 Application / Logical / Platform
7. L3 Component / Contract
8. L4 Runtime / Sequence
9. Failure / Saturation / Recovery
10. Security / Trust
11. Data / Interface
12. Normal Pattern
13. Forbidden Pattern
14. 주안 / 대안 / Trade-off
15. Current Evidence
16. PASS / GAP / CONFLICT / OPEN
17. ADR / Decision
18. 검증 / Test / Runtime Evidence
19. 이 장의 결론
20. 다음 장 Handoff
```

필요하면 20개 이상 절로 확장한다.

---

# 19. 제1장 — 왜 다시 짓는가

## 장의 핵심 Story

발표스크립트 톤:

> 이 그림의 핵심은 “미래를 담을 집을 왜 다시 짓는가”입니다.
>
> 시스템은 이미 존재합니다. Source도 있고 서버도 있고 데이터도 있습니다.
> 그러나 그것들이 하나의 Architecture로 연결되어 있다고 말하기는 어렵습니다.
>
> 그래서 이번 작업은 새로운 기술을 추가하는 작업이 아니라
> 이미 존재하는 Source·Runtime·Data·Infrastructure를 하나의 책임과 Evidence 체계로 다시 연결하는 작업입니다.

## 전체 대표 TEXT 그림

```text
기존
Module / Source / Server / Data
각각 존재
        ↓
Architecture 연결 부족
        ↓
Runtime / Failure / Evidence 불명확

             ↓ 재정의

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
```

## Drill-down 목차

```text
1. 기존 PDMG가 어떻게 보이는가
2. Inventory와 Architecture의 차이
3. Module → Responsibility
4. Source → Runtime
5. Timeout / Transaction
6. Security
7. ServiceId Trace
8. Physical Mapping
9. Capacity / Failure
10. Data Platform Boundary
11. Interface Contract
12. Operations / Evidence
13. Closed Loop
14. Current GAP
15. 다음 장 Handoff
```

---

# 20. 제2장 — 정보계 패러다임의 전환

## 장의 핵심 Story

> 이 그림은 정보계 패러다임의 전환을 보여줍니다.
>
> 기존 정보계는 Application과 Database를 중심으로 설명되었습니다.
> 그러나 지금 필요한 구조는 Runtime, Data Flow, Failure, Scale, Evidence까지 포함하는 Platform Architecture입니다.
>
> 그래서 이번 NSIGHT/PDMG Architecture는 Application 중심 설명에서 Responsibility 중심, Runtime-aware, Data-aware Architecture로 전환합니다.

## 전체 대표 TEXT 그림

```text
Application-Centric
User → Application → DB

          ↓

Platform / Runtime-Centric
User
 ↓
UI / Auth
 ↓
Application Runtime
 ↓
Framework Control
 ↓
Data
 ↓
Operations / Evidence
```

## Drill-down 목차

```text
1. Application 중심 정보계
2. Responsibility 중심 전환
3. Module → Runtime Boundary
4. Product → Capability
5. Online → FAST/DEEP
6. Server → Logical→Physical
7. Logging → Evidence
8. Document → Closed Loop
9. PDMG Current ↔ NSIGHT Target
10. 패러다임 전환의 의미
```

---

# 21. 제3장 — 아키텍처 6단계 수립 방법론

## 전체 대표 TEXT 그림

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

## Story

> 우리는 이 복잡한 정보계를 한 번에 설계하지 않습니다.
> 위에서 방향을 정하고, 책임을 나누고, 논리구조를 만들고, 물리자원으로 내려간 뒤, 실행규칙과 Runtime으로 검증합니다.
>
> 즉, Architecture는 그림을 만드는 작업이 아니라
> 위에서 아래로 내려가며 설계 의도를 실제 Runtime까지 연결하는 과정입니다.

## Drill-down 목차

```text
1. 왜 단계가 필요한가
2. VISION
3. BIG PICTURE
4. LOGICAL
5. PHYSICAL
6. MECHANISM
7. RUNTIME
8. 단계간 Handoff
9. Top-down + Bottom-up
10. Evidence / Gate
```

---

# 22. 제4장 — Big Picture

## 전체 대표 TEXT 그림

```text
User / Browser
      ↓
UI
      ↓
Authentication
      ↓
Application Runtime
      ↓
Data

External
→ Approved Interface

Cross-cutting
Security / Observability / Operations
```

## Story

> 이 Big Picture의 핵심은 박스의 수가 아닙니다.
> 중요한 것은 책임과 경계입니다.
>
> 채널은 채널의 역할만,
> Authentication은 인증의 역할만,
> Application Runtime은 업무 실행만,
> Data Platform은 데이터 역할만 수행해야 합니다.
>
> 경계가 명확해야 변경 영향과 장애가 격리됩니다.

## Drill-down 목차

```text
1. 전체 System Context
2. User / Channel Boundary
3. UI Boundary
4. Authentication Boundary
5. Application Runtime Boundary
6. Data Boundary
7. External Integration Boundary
8. Security Cross-cutting
9. Observability Cross-cutting
10. 정상 / 금지 연결
11. Current GAP
```

---

# 23. 제5장 — 논리 아키텍처

## 전체 대표 TEXT 그림

```text
Application Responsibility
       ↓
Technical Capability
       ↓
Logical Technical Node
       ↓
Runtime Type
       ↓
State / Scale / Failure / Security
```

## Story

> 논리 아키텍처는 제품을 고르는 단계가 아닙니다.
> 무엇을 허용하고 무엇을 분리할지 정하는 단계입니다.
>
> Application 이름을 Server 이름으로 바꾸는 것이 아니라
> Application 책임을 Technical Capability와 Logical Node로 변환합니다.

## Drill-down

```text
1. Application → Capability
2. Logical Node Set
3. UI Delivery Node
4. Authentication Node
5. Application Runtime Node
6. Framework Capability
7. Data Service Node
8. Integration Node
9. Operations Node
10. State
11. Scale Unit
12. Failure Domain
13. Security Boundary
14. Allowed / Forbidden Path
15. Physical Handoff
```

---

# 24. 제6장 — 물리 아키텍처

## 전체 대표 TEXT 그림

```text
User
 ↓
GSLB
 ↓
L4
 ↓
WEB / Apache
 ↓
WAS / Tomcat JVM
 ↓
WAR
 ↓
Hikari / MyBatis / JDBC
 ↓
RDW / DB
```

## Story

> 물리 아키텍처의 핵심은 장비를 나열하는 것이 아닙니다.
> 논리적으로 분리한 책임을 실제 Center, VM, JVM, WAR에 어떻게 배치하고 장애영향을 어디에서 끊어낼 것인가입니다.
>
> 여기서 Server, VM, JVM, WAR를 같은 것으로 보면 안 됩니다.

## Drill-down

```text
1. Logical → Physical
2. Environment / Center
3. GSLB / L4
4. WEB / Apache
5. WAS / JVM
6. WAR Placement
7. Data / DB
8. Network / Port / Firewall
9. Storage
10. Monitoring / Backup
11. Capacity Candidate
12. Physical Traceability
13. HA Handoff
```

---

# 25. 제7장 — DR 센터 활용 전략

## 전체 대표 TEXT 그림

```text
Main Center
 ↓ failure
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
 ↓
Failback
```

## Story

> DR의 목표는 가장 멋진 구조가 아닙니다.
> 실제 장애 상황에서도 정합성을 유지하며 운영 가능한 구조입니다.
>
> Application Active-Active와 DB 양방향 Active-Active는 같은 문제가 아닙니다.
> 특히 금융 데이터는 가용성보다 정합성을 먼저 봐야 합니다.

## Drill-down

```text
1. HA vs DR
2. Main Local HA
3. Center Failure
4. Traffic Switch
5. Application Recovery
6. Artifact / Config / Key
7. DB Consistency
8. RTO / RPO
9. Backup / Restore
10. Failback
11. DR Test
12. Business Validation
```

---

# 26. 제8장 — 메커니즘

## 전체 대표 TEXT 그림

```text
HTTP
 ↓
Filter
 ↓
Security
 ↓
MVC
 ↓
TCF
 ↓
Worker / Timeout
 ↓
Transaction
 ↓
Handler
 ↓
Facade / Service
 ↓
DAO / DB
 ↓
Response / Error / Evidence
```

## Story

> 메커니즘은 Architecture를 실제로 움직이게 만드는 실행규칙입니다.
>
> Server와 Framework가 있다고 시스템이 같은 방식으로 움직이는 것은 아닙니다.
> Filter, Context, Security, Dispatcher, Timeout, Transaction, Error, Logging이 하나의 표준 흐름을 만들어야 합니다.
>
> 결국 시스템은 서버가 아니라 표준과 메커니즘으로 움직입니다.

## Drill-down

```text
1. Framework vs Business
2. DefaultFilter
3. ServiceContext
4. SecurityFilterChain
5. DispatcherServlet / Interceptor
6. ServiceId Resolution
7. TCF
8. Dispatcher / Handler
9. Worker / Timeout
10. Transaction
11. Context Propagation
12. Error
13. Logging / ImageLog
14. TCF OFF
15. Anti-pattern
```

---

# 27. 제9장 — 런타임 서비스

## 전체 대표 TEXT 그림

```text
Request Thread
Filter
Security
MVC
Controller
Future.get
     │
     │ submit
     ▼
Worker Thread
Context
TX
Dispatcher
Handler
Facade
Service
DAO
DB
     ↓
Response / Evidence
```

## Story

> 런타임 단계에서는 설계가 실제 서비스 흐름으로 움직이는지 확인합니다.
>
> 이 그림에서 가장 중요한 것은 Request Thread와 Worker Thread가 분리된다는 것입니다.
> 그리고 HTTP 응답이 끝났다고 해서 DB 작업도 끝났다고 볼 수 없습니다.
>
> 이제 Architecture를 정적 그림이 아니라 시간축으로 보게 됩니다.

## Drill-down

```text
1. Request Thread
2. Worker Thread
3. ServiceId Routing
4. Business Runtime
5. DB Runtime
6. Success Response
7. Known Error
8. Timeout
9. Overload
10. TCF OFF
11. FAST / DEEP
12. Runtime Evidence
13. Failure Matrix
```

---

# 28. 제10장 — 데이터플랫폼

## 전체 대표 TEXT 그림

```text
PDMG
 ↓
Service
 ↓
DAO
 ↓
Mapper / SqlId
 ↓
RDW

Operational
        VS
Analytical

RDW
        ADW
 ↓       ↓
Online   BI / Heavy Analysis
```

## Story

> 데이터 플랫폼의 핵심은 데이터를 많이 저장하는 것이 아닙니다.
> 어떤 Workload를 어떤 데이터 영역이 책임질 것인가를 분리하는 것입니다.
>
> RDW는 실시간 운영을 지키고,
> ADW는 분석을 극대화합니다.
>
> 두 역할을 섞으면 분석 부하가 온라인 서비스까지 흔들 수 있습니다.

## Drill-down

```text
1. Data Architecture vs DB
2. PDMG Data Access
3. Data Ownership
4. RDW
5. ADW
6. Datasource / Transaction
7. Mapper / SqlId
8. Read / Write Boundary
9. Lineage
10. CDC
11. ETL
12. Metadata
13. Data Quality
14. Data Security
15. Workload Isolation
```

---

# 29. 제11장 — 마케팅플랫폼

## 전체 대표 TEXT 그림

```text
Customer Action
      ↓
Marketing Application
      ↓
Program / ServiceId
      ↓
Business Runtime
      ↓
RDW / Data
      ↓
Decision / Offering

Target Extension
Event / Kafka / Real-time
```

## Story

> 마케팅 플랫폼은 배치형 캠페인 시스템에서 고객 행동에 반응하는 플랫폼으로 전환되어야 합니다.
>
> 여기서 PDMG는 Marketing Platform 전체가 아니라
> UI, JWT, Framework, ServiceId 기반 Business Runtime의 Current Reference입니다.
>
> Target의 Kafka/Event 구조를 Current PDMG에 있다고 표현해서는 안 됩니다.

## Drill-down

```text
1. Marketing Platform Role
2. PDMG Runtime Reference
3. Program
4. ServiceId
5. Business Layer
6. Data Access
7. Customer Context
8. External Interface
9. Event Target
10. Security
11. MP ↔ mg Mapping
12. Runtime Evidence
```

---

# 30. 제12장 — BI 포탈

## 전체 대표 TEXT 그림

```text
Operational Data
      ↓
RDW
      ↓
ADW
      ↓
BI Portal
      ↓
Report / Self-BI / Analysis
```

## Story

> 데이터 플랫폼이 신뢰할 수 있는 데이터를 만들고
> 마케팅 플랫폼이 실행을 담당한다면,
> BI 포탈은 판단을 담당합니다.
>
> 중요한 것은 BI가 PDMG 내부 DAO를 직접 호출하는 구조가 아니라
> Data Contract를 통해 Analytical Platform을 소비하는 구조라는 점입니다.

## Drill-down

```text
1. BI Boundary
2. Operational vs Analytical
3. RDW → ADW
4. Data Contract
5. Dataset
6. Report
7. Self-BI
8. AI / Natural Language Analysis [Target if evidenced]
9. BI Security
10. Freshness SLA
11. Performance Isolation
12. Governance
```

---

# 31. 제13장 — 표준화와 10년 지속 가능성

## 전체 대표 TEXT 그림

```text
Architecture
 ↓
Naming / Rule
 ↓
Source
 ↓
CI Test
 ↓
Artifact
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
 ↓
HG90
```

## Story

> Architecture의 완성은 구축 시점이 아니라 시간이 지난 뒤에도 구조가 유지되는가에서 결정됩니다.
>
> 표준은 규제가 아닙니다.
> Source, Build, Deployment, Runtime이 같은 Architecture를 계속 유지하도록 만드는 장치입니다.
>
> 결국 10년 지속 가능한 Architecture는
> 처음 잘 그린 그림이 아니라 변화할 때마다 스스로 정합성을 확인할 수 있는 체계입니다.

## Drill-down

```text
1. Architecture Drift
2. Naming
3. Program / ServiceId
4. Development Rule
5. CI Gate
6. Immutable Artifact
7. Config / Secret
8. Deployment Trace
9. Observability
10. Runtime Evidence
11. Drift Detection
12. GAP / ADR
13. G00 → HG90
14. Architecture Baseline Release
```

---

# 32. 각 장의 “발표스크립트형 설명” 필수 구성

각 장의 대표 그림 직후 반드시 아래 형식으로 7~12개 문단 정도 설명한다.

```text
1. "이 그림의 핵심은 ... 입니다."
2. "기존에는 ... 구조였습니다."
3. "그러나 ... 문제가 있습니다."
4. "그래서 이번 Architecture에서는 ... 합니다."
5. "여기서 중요한 원칙은 ... 입니다."
6. "이것은 단순히 기술을 바꾸는 문제가 아닙니다."
7. "Architecture 관점에서는 ... 의미를 가집니다."
8. "운영 관점에서는 ... 효과가 있습니다."
9. "반대로 ... 구조는 허용하지 않습니다."
10. "이제 한 단계 더 내려가 보겠습니다."
```

---

# 33. Drill-down 설명 필수 규칙

한 단계 내려갈 때마다 다음을 반드시 설명한다.

```text
왜 이 Box를 분해하는가?
무슨 책임이 숨어 있는가?
어디가 Boundary인가?
어떤 Dependency가 있는가?
어떤 Failure가 가능한가?
Security는 어디에서 통제되는가?
Runtime에서는 어떻게 움직이는가?
Current Evidence가 있는가?
```

---

# 34. Runtime 설명 규칙

Runtime Diagram은 다음 질문을 모두 답해야 한다.

```text
누가 시작하는가?
어떤 Thread인가?
Context는 어디서 만들어지는가?
Security는 언제 수행되는가?
Transaction은 어디서 시작되는가?
Data Access는 언제 일어나는가?
Timeout은 누가 소유하는가?
Error는 어디서 변환되는가?
Response는 언제 만들어지는가?
Cleanup은 누가 수행하는가?
Evidence는 어디에 남는가?
```

---

# 35. Failure 설명 규칙

각 핵심 Runtime 장은 최소 1개의 Failure Diagram을 가진다.

예:

```text
DB Slow
 ↓
Hikari Pending
 ↓
Worker Busy
 ↓
Queue
 ↓
Timeout
 ↓
HTTP 504
```

그리고 발표형 설명:

> 이 그림에서 중요한 것은 장애가 DB에서 끝나지 않는다는 점입니다.  
> DB의 느린 응답은 Connection Pool을 점유하고, Worker를 점유하고, 결국 Request Timeout으로 올라옵니다.
>
> 따라서 Capacity는 Tomcat Thread 하나의 숫자로 볼 수 없습니다.

---

# 36. Normal / Forbidden Pattern 설명 규칙

단순 그림만 넣지 않는다.

정상:

```text
UI
 ↓
Application
 ↓
Data Access
 ↓
DB
```

금지:

```text
UI ─────► DB
    X
```

그 다음 반드시 설명한다.

> 정상패턴에서는 각 Layer가 자신의 책임만 수행합니다.
> 반대로 UI가 DB에 직접 접근하면 Application Boundary가 사라지고 변경 영향과 보안 통제가 동시에 무너집니다.

---

# 37. 주안 / 대안 설명 규칙

Trade-off를 숨기지 않는다.

반드시:

```text
[주안]
...

[대안]
...
```

을 그린다.

그리고 다음을 표/설명으로 비교한다.

```text
왜 주안인가
장점
단점
대안의 장점
대안의 위험
어떤 Evidence가 있으면 대안을 선택할 수 있는가
```

---

# 38. PASS / GAP 설명 규칙

각 장 마지막에는:

```text
Architecture Definition
      ↓
PASS / CONDITIONAL PASS

Current PDMG
      ↓
PASS / PARTIAL / GAP / OPEN / UNKNOWN

Runtime Evidence
      ↓
HIGH / MEDIUM / LOW
```

를 그림으로 보여준다.

그리고 반드시 설명한다.

```text
Architecture PASS
≠
Implementation PASS
```

---

# 39. Chapter Handoff 규칙

장 마지막 문단은 다음 장을 필요하게 만들어야 한다.

예:

> 여기까지 논리적으로 무엇이 필요한지 정의했습니다.
> 그러면 자연스럽게 다음 질문이 생깁니다.
>
> “이 Logical Node들을 실제 어느 Center, VM, JVM, WAR에 배치할 것인가?”
>
> 이 질문이 바로 다음 장, 물리 아키텍처의 주제입니다.

---

# 40. 최종 통합본의 첫 번째 그림

최종 통합본은 반드시 이와 같은 Story Master Map으로 시작한다.

```text
왜 다시 짓는가
 ↓
패러다임 전환
 ↓
6단계 방법론
 ↓
Big Picture
 ↓
Logical
 ↓
Physical
 ↓
DR
 ↓
Mechanism
 ↓
Runtime
 ↓
Data Platform
 ↓
Marketing Platform
 ↓
BI Portal
 ↓
Standardization / Evidence
 ↓
HG90
```

---

# 41. 최종 통합 Architecture 그림

```text
User / Browser
      ↓
UI Delivery
      ↓
Authentication
      ↓
Application Runtime
      │
      ├─ Framework Control
      │   ├─ Filter / Context
      │   ├─ Security
      │   ├─ TCF
      │   ├─ Worker / Timeout
      │   ├─ Transaction
      │   └─ Error / Logging
      │
      └─ Business
          ├─ Handler / Controller
          ├─ Facade
          ├─ Service
          └─ DAO / Mapper
                  ↓
              RDW / DB

Target Data / Analytics
RDW → ADW → BI

Target Marketing Extension
Event / Kafka / Real-time

Physical
GSLB → L4 → Apache → Tomcat/JVM → WAR → DB

Operations
Artifact → Deployment → Metric/Log/Trace → Evidence

Closed Loop
Architecture → Source → Test → Runtime → Drift → ADR → Baseline
```

---

# 42. 최종 Quality Gate

완료 전 반드시 확인한다.

```text
[ ] 13장 목차가 발표스크립트 순서와 일치한다.
[ ] 각 장 첫 그림이 그 장 전체를 설명한다.
[ ] 각 장이 L0→L5로 Drill-down된다.
[ ] 각 주요 절에 TEXT 그림이 있다.
[ ] 그림 뒤에 충분한 Story 설명이 있다.
[ ] 설명 문체가 발표스크립트형이다.
[ ] Current와 Target을 구분했다.
[ ] 정상/금지패턴이 있다.
[ ] 주안/대안과 Trade-off가 있다.
[ ] Failure/Recovery가 있다.
[ ] Security Boundary가 있다.
[ ] Runtime Sequence가 있다.
[ ] PASS/GAP/ADR가 있다.
[ ] Source/Config/Runtime Evidence를 연결했다.
[ ] Unknown/Open을 추정하지 않았다.
[ ] 각 장이 다음 장으로 자연스럽게 연결된다.
[ ] 최종 통합본이 하나의 Story로 읽힌다.
```

---

# 43. 산출물

최종적으로 아래 파일을 작성한다.

```text
00_NSIGHT_PDMG_STORY_ARCHITECTURE_MASTER_INDEX.md

01_왜_다시_짓는가.md
02_정보계_패러다임의_전환.md
03_아키텍처_6단계_수립_방법론.md
04_BIG_PICTURE.md
05_논리_아키텍처.md
06_물리_아키텍처.md
07_DR_센터_활용_전략.md
08_메커니즘.md
09_런타임_서비스.md
10_데이터플랫폼.md
11_마케팅플랫폼.md
12_BI_포탈.md
13_표준화와_10년_지속가능성.md

NSIGHT_PDMG_STORY_ARCHITECTURE_FINAL_INTEGRATED.md
NSIGHT_PDMG_STORY_ARCHITECTURE_MANIFEST.json
NSIGHT_PDMG_STORY_ARCHITECTURE_COMPLETE.zip
```

---

# 44. 실행 명령

이 프롬프트를 받은 AI는 추가 승인 없이 다음 순서로 작업한다.

```text
1. 입력자료 확인
2. Evidence 우선순위 정리
3. 13장 Story Map 확정
4. 제1장 대표 그림 작성
5. 제1장 L0→L5 Drill-down
6. 발표스크립트형 상세설명 작성
7. 정상/금지/의사결정/PASS-GAP 작성
8. 제2장으로 Story 연결
9. 제13장까지 반복
10. 최종 통합본 생성
11. Diagram Coverage / Markdown / Evidence 검증
12. Manifest / ZIP 생성
```

**질문하지 말고, Evidence가 부족한 부분은 `[OPEN]`, `[UNKNOWN]`, `[GAP]`로 표시하고 계속 진행한다.**
