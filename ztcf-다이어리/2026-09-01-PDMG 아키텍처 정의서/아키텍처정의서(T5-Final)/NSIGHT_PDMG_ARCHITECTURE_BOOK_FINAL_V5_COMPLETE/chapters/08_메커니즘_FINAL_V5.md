# NSIGHT PDMG 아키텍처 정의서
# 제8장. 메커니즘
## Story: “시스템은 서버가 아니라 표준과 실행규칙으로 움직인다”


---

# 0. Opening Script

이 장은 PDMG 기술 아키텍처의 중심입니다.

앞 장까지는 공간과 배치를 봤습니다. 이제 같은 서버 위에서 거래가 **어떤 표준 규칙으로 시작되고, 신뢰를 확인하고, ServiceId를 해석하고, Worker와 Transaction을 만들고, Error와 Log를 남기는지**를 봅니다.

이 그림의 핵심은 Component 수가 아니라 **실행정책의 소유권**입니다.

## FIG-08-01. 장 전체 Architecture

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
OnlineTransactionController
 ↓
TcfFacade
 ↓
OnlineTimeoutExecutor
 ↓
TransactionDispatcher
 ↓
Handler
 ↓
Facade / Service
 ↓
DAO / DB
 ↓
Response / Error / Evidence
```

Mechanism은 PDMG 기술 Architecture의 실행규칙입니다. 요청은 Controller보다 먼저 Filter와 Security를 지나고, ServiceId를 해석한 뒤 Worker와 Transaction 안에서 Business를 실행합니다. 따라서 이 장의 핵심은 Component 수가 아니라 **실행정책을 누가 소유하는가**입니다.


---

# 1. Framework와 Business의 역할

## FIG-08-02. Framework와 Business의 역할

```text
Framework
= HOW safely execute

Business
= WHAT to execute

Framework Contract
 ↓
Handler / Facade / Service
```

먼저 책임을 나눕니다. Framework는 Context, Security, Timeout, Transaction, Error, Logging 같은 실행정책을 소유합니다.

Business는 어떤 업무를 수행할지 결정합니다. Framework가 특정 업무 SQL을 알기 시작하면 공통정책과 업무코드가 강결합됩니다.

이 분리가 Mechanism 장의 가장 중요한 원칙입니다.


---

# 2. DefaultFilter — 거래 Context 시작

## FIG-08-03. DefaultFilter — 거래 Context 시작

```text
HTTP
 ↓
DefaultFilter
 ├─ Body Cache
 ├─ GUID/Header
 ├─ ServiceContext
 └─ MDC
 ↓
FilterChain
 ↓
finally clear
```

거래는 Controller에서 시작되는 것이 아닙니다. Filter에서 이미 Context가 만들어지고 GUID/MDC가 준비됩니다.

이 지점이 중요한 이유는 Filter가 Security와 MVC보다 먼저 실행되기 때문입니다. 여기서 만든 Context가 이후 Runtime 전체로 전달됩니다.

마지막 `finally`에서 ThreadLocal을 반드시 제거해야 Cross-request contamination을 막을 수 있습니다.


---

# 3. SecurityFilterChain — Trust Gate

## FIG-08-04. SecurityFilterChain — Trust Gate

```text
DefaultFilter
 ↓
SecurityFilterChain
 ↓
JWT Verify
 ↓
Trusted Principal
 ↓
DispatcherServlet
```

Security는 MVC 이전의 Trust Gate입니다.

여기서 Token이 검증되어야 이후 Controller가 신뢰된 Principal을 사용할 수 있습니다. 다만 현재 `pdmg-jwt`의 RS256 Issue와 Framework HMAC Verify Path는 정합성 GAP가 있습니다.

또한 Principal이 `hdr_nhnis`의 사용자정보와 자동으로 동일하다고 가정하지 않습니다.


---

# 4. ServiceId Resolution과 TCF

## FIG-08-05. ServiceId Resolution과 TCF

```text
Context Header
 ↓
Request Header
 ↓
Path Variable
 ↓
ServiceId
 ↓
TcfFacade
 ↓
TransactionDispatcher
```

PDMG의 Runtime Routing Key는 ServiceId입니다.

현재 Controller는 여러 Source에서 ServiceId를 얻을 수 있으므로, 값이 다를 때 어떻게 처리할지 명시적인 방어가 필요합니다. 단순 precedence만 있고 mismatch rejection이 없다면 잘못된 거래로 라우팅될 위험이 있습니다.

따라서 ServiceId 일치검증은 PROPOSED Defense로 관리합니다.


---

# 5. Handler Registry

## FIG-08-06. Handler Registry

```text
Spring Handlers
 ↓ startup
Registry
 ServiceId → Handler
 ↓
Duplicate?
 ├─ YES → startup fail
 └─ NO  → runtime route
```

Handler Registry는 기동 시 만들어지는 Runtime Routing Table입니다.

Duplicate ServiceId는 기동오류로 처리하는 것이 안전합니다. 또한 `serviceIds()`에 등록된 ID와 `handle()` 내부 branch가 서로 일치해야 합니다.

현재 Registry 13개와 UI Transaction Catalog 차이는 Drift 후보입니다.


---

# 6. Worker / Timeout

## FIG-08-07. Worker / Timeout

```text
Request Thread
 ↓ submit
pdmg-online-N
 ↓
pool-size 20
queue 100
deadline 5000ms
 ↓
Business
```

Timeout Mechanism은 Request Thread와 Worker Thread를 분리합니다.

여기서 20/100/5000은 Current Snapshot이지 Target SLA가 아닙니다. 이 수치를 Capacity 목표로 그대로 승격하면 안 됩니다.

Worker Pool은 Tomcat Thread Pool과도, Hikari Pool과도 다른 Resource입니다.


---

# 7. TransactionTemplate

## FIG-08-08. TransactionTemplate

```text
Worker
 ↓
TransactionTemplate BEGIN
 ↓
Dispatcher / Handler
 ↓
Facade @Transactional(REQUIRED)
 ↓
Service / DAO
 ↓
Deadline Check
 ↓
COMMIT / ROLLBACK
```

실제 DB Transaction은 Worker 안의 `TransactionTemplate`에서 시작됩니다.

Facade의 `@Transactional(REQUIRED)`는 새로운 Transaction을 만드는 것보다 외부 Transaction에 참여할 수 있습니다.

따라서 TCF ON/OFF에서 Facade를 공통 Business Boundary로 맞추는 것이 Transaction 일관성 측면에서도 중요합니다.


---

# 8. Error/Response/Logging

## FIG-08-09. Error/Response/Logging

```text
Exception
 ↓
GlobalExceptionHandler
 ↓
ErrorCode / HTTP
 ↓
hdr_nhnis + result
 ↓
Response Advice
 ↓
ImageLog / MDC / Evidence
```

실패도 표준 흐름을 가져야 합니다.

Known Error는 Taxonomy와 Envelope로 변환되지만 Filter/Security 단계의 Early Error는 MVC Advice를 우회할 수 있습니다. 이 때문에 Error Contract가 완전히 일관되지는 않습니다.

Logging은 GUID/ServiceId로 연결하되 Token/Secret/민감 DTO를 기록하지 않는 원칙이 필요합니다.

---

# 정상패턴과 금지패턴

## FIG-08-10. Normal Pattern

```text
Filter→Security→TCF→Worker/TX→Facade→Service→DAO
```

정상패턴은 Framework가 Context/Security/Timeout/TX를 소유하고 Handler와 Controller가 공통 Facade를 통해 같은 Business Core를 실행하는 것입니다.

## FIG-08-11. Forbidden Pattern

```text
Handler→DAO / TCF OFF Common Core 우회
```

Handler→DAO 직접호출, TCF OFF에서 Business Core 우회, Client Header를 Trusted Identity로 간주하는 구조는 금지/개선 대상으로 둡니다.

---

# Architecture Decision

## FIG-08-12. 주안과 대안

```text
[주안]
Handler/Controller 모두 Common Facade

        VS

[대안]
TCF OFF Controller→Service Direct
```

TCF ON Handler와 TCF OFF Controller가 모두 Common Facade를 호출하도록 Business Use Case Boundary를 통일합니다.

OFF 경로에서 Service를 직접 호출하면 Transaction/AOP/정책 적용점이 달라져 두 Runtime의 Business 의미가 Drift할 수 있습니다.

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
메커니즘 Architecture Rule
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

- `[SOURCE]` DefaultFilter / ServiceContext / TCF / Dispatcher / Handler / GlobalExceptionHandler 분석
- `[SOURCE]` TCF ON/OFF Source 분석

### Config Evidence

- `[CONFIG]` tcf.enabled=true
- `[CONFIG]` timeout.enabled=true
- `[CONFIG]` timeout=5000ms
- `[CONFIG]` pool-size=20
- `[CONFIG]` queue-capacity=100
- `[CONFIG]` legacy-web/filter enabled

### Runtime / Deployment Evidence

- `[RUNTIME]` DefaultFilter→SecurityFilterChain→DispatcherServlet→Interceptor→Controller→TcfFacade→TimeoutExecutor→Worker TX→Dispatcher→Handler→Facade→Service→DAO→DB

### Architecture Decision

- `[DECISION]` Framework=HOW / Business=WHAT
- `[DECISION]` TCF ON/OFF 공통 Facade Business Core

### Related ADR

- `ADR-004 Common Facade`
- `ADR-005 TCF Policy`
- `ADR-009 Immutable Worker Context`
- `ADR-017 Timeout Budget`

### Current GAP / OPEN

- `[GAP/OPEN]` Mutable Worker Context
- `[GAP/OPEN]` ServiceId mismatch defense
- `[GAP/OPEN]` TCF OFF Controller→Service Drift
- `[GAP/OPEN]` Early Error Envelope
- `[GAP/OPEN]` Generic Error Coverage

## 이 장의 판정

```text
Architecture Definition
        ↓
PASS

Current PDMG Conformance
        ↓
PARTIAL / GAP

Runtime Evidence Coverage
        ↓
HIGH-MEDIUM

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `JWT Verifier 정합`
- `Immutable Worker Context`
- `TCF OFF→Facade 정렬`
- `ServiceId mismatch 방어`
- `Error Envelope 정합`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-08-16. 다음 장 Handoff

```text
메커니즘
 ↓
시스템은 서버가 아니라 표준과 실행규칙으로 움직인다
 ↓
남은 질문
"정적인 Architecture를 거래 한 건의 시간축으로 펼쳐본다"
 ↓
런타임 서비스
```

Mechanism은 실행규칙을 정의했습니다. 다음 장에서는 이 규칙을 거래 한 건의 시간축으로 펼쳐 Request Thread, Worker Thread, Transaction, DB, Timeout이 실제 어떻게 상호작용하는지 확인합니다.
