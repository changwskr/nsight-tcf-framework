# NSIGHT PDMG 아키텍처 정의서
# 제8장. 메커니즘
## Story: “시스템은 서버가 아니라 표준과 실행규칙으로 움직인다”
## STORY-FIRST / TEXT-ARCHITECTURE-FIRST / TOP-DOWN → DRILL-DOWN / EVIDENCE-FIRST

> 최종 문서 Edition: `FINAL V4 — Story + Architecture + Drill-down + Evidence in Chapter`  
> 기준일: `2026-09-01`  
> PDMG = Current / Source / Config / Runtime  
> NSIGHT = Target / Alignment / Strategy Reference  
> 문서 상태: **FINAL V4 WORKING BASELINE** / Evidence는 본 장 내부에서 Architecture와 함께 판정

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

이제 이 전체 그림을 위에서 아래로 해부하겠습니다. 이번 버전에서는 그림의 박스와 화살표를 직접 설명하고, 반복적인 형식문장은 최소화합니다. 각 Drill-down은 상위 그림의 어느 부분을 확대하는지 명확하게 연결합니다.

## FIG-08-02. Drill-down Route

```text
L0 전체 Story
 ↓
L1 책임 / Boundary
 ↓
L2 Logical / Application / Platform
 ↓
L3 Component / Contract
 ↓
L4 Runtime / Failure / Security
 ↓
L5 Source / Config / Deployment / Evidence
```

---

# 1. Framework와 Business의 역할

## FIG-08-03. Framework와 Business의 역할

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

여기까지가 `Framework와 Business의 역할`의 역할입니다. 이제 이 구조를 더 내려가 **DefaultFilter — 거래 Context 시작**에서 다음 경계와 실행책임을 보겠습니다.

---

# 2. DefaultFilter — 거래 Context 시작

## FIG-08-04. DefaultFilter — 거래 Context 시작

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

여기까지가 `DefaultFilter — 거래 Context 시작`의 역할입니다. 이제 이 구조를 더 내려가 **SecurityFilterChain — Trust Gate**에서 다음 경계와 실행책임을 보겠습니다.

---

# 3. SecurityFilterChain — Trust Gate

## FIG-08-05. SecurityFilterChain — Trust Gate

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

여기까지가 `SecurityFilterChain — Trust Gate`의 역할입니다. 이제 이 구조를 더 내려가 **ServiceId Resolution과 TCF**에서 다음 경계와 실행책임을 보겠습니다.

---

# 4. ServiceId Resolution과 TCF

## FIG-08-06. ServiceId Resolution과 TCF

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

여기까지가 `ServiceId Resolution과 TCF`의 역할입니다. 이제 이 구조를 더 내려가 **Handler Registry**에서 다음 경계와 실행책임을 보겠습니다.

---

# 5. Handler Registry

## FIG-08-07. Handler Registry

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

여기까지가 `Handler Registry`의 역할입니다. 이제 이 구조를 더 내려가 **Worker / Timeout**에서 다음 경계와 실행책임을 보겠습니다.

---

# 6. Worker / Timeout

## FIG-08-08. Worker / Timeout

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

여기까지가 `Worker / Timeout`의 역할입니다. 이제 이 구조를 더 내려가 **TransactionTemplate**에서 다음 경계와 실행책임을 보겠습니다.

---

# 7. TransactionTemplate

## FIG-08-09. TransactionTemplate

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

여기까지가 `TransactionTemplate`의 역할입니다. 이제 이 구조를 더 내려가 **Error/Response/Logging**에서 다음 경계와 실행책임을 보겠습니다.

---

# 8. Error/Response/Logging

## FIG-08-10. Error/Response/Logging

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

## FIG-08-11. Normal Pattern

```text
Filter→Security→TCF→Worker/TX→Facade→Service→DAO
```

정상패턴은 각 영역이 자신의 책임을 유지하면서 명확한 Contract와 Runtime Boundary를 통해 연결되는 구조입니다. 변경·장애·보안·운영 책임이 이 경계를 따라 추적될 수 있어야 합니다.

## FIG-08-12. Forbidden Pattern

```text
Handler→DAO / TCF OFF Common Core 우회
```

금지패턴은 기술적으로 불가능해서가 아니라 Architecture의 책임과 Evidence Chain을 무너뜨리기 때문에 제한합니다. 예외가 필요하면 묵시적으로 허용하지 않고 ADR와 Test Evidence로 승인합니다.

---

# Architecture Decision

## FIG-08-13. 주안과 대안

```text
[주안]
Handler/Controller 모두 Common Facade

        VS

[대안]
TCF OFF Controller→Service Direct
```

이번 장의 주안은 **Handler/Controller 모두 Common Facade**입니다. 이 방향은 현재 확인된 PDMG 구조와 NSIGHT Target을 연결하면서 책임·운영·Evidence를 가장 일관되게 유지할 수 있는 선택입니다.

대안인 **TCF OFF Controller→Service Direct**도 특정 조건에서는 사용할 수 있습니다. 다만 대안을 선택하려면 주안보다 나은 성능·가용성·비용 또는 운영효과가 PoC/Runtime Test로 확인되어야 하고, 그 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---

# Current GAP / PASS

## FIG-08-14. Current GAP

```text
Current
│
├─ mutable context
├─ tcf off drift
├─ early error envelope
├─ serviceId mismatch
└─ generic error coverage
```

- `[GAP/OPEN]` mutable context
- `[GAP/OPEN]` tcf off drift
- `[GAP/OPEN]` early error envelope
- `[GAP/OPEN]` serviceId mismatch
- `[GAP/OPEN]` generic error coverage

## FIG-08-15. Architecture Assessment

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

Architecture PASS
 ≠
Implementation PASS
```

Architecture가 잘 정의되었다는 것과 현재 구현이 그 정의를 지킨다는 것은 별도 판단입니다. 이 문서는 둘을 분리해 평가하며, `[OPEN]`과 `[UNKNOWN]`을 임의로 채우지 않습니다.

---

# Evidence Card

## FIG-08-16. Evidence Chain

```text
Architecture Rule
 ↓
Source / Config
 ↓
Build / Artifact
 ↓
Deployment
 ↓
ServiceId / GUID
 ↓
Metric / Log / Trace / Test
 ↓
Runtime Evidence
 ↓
PASS / GAP / ADR
```

본문에서는 Story와 Architecture 설명을 우선하고, Evidence는 이 카드에서 정리합니다. 앞으로 자동화 단계에서는 이 Chain을 Manifest/Registry로 기계적으로 생성하는 것이 목표입니다.

---


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

이 장의 정의가 완료되었다고 해서 현재 구현이 자동으로 PASS가 되는 것은 아니다. Source·Config·Runtime Evidence가 실제 Architecture Rule과 정합할 때 Current Conformance를 PASS로 승격한다.

---
# Chapter Closing Script

## FIG-08-17. 다음 장 Handoff

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

여기까지가 **메커니즘**입니다. 이 장에서 중요한 것은 개별 기술을 많이 보여준 것이 아니라, 전체 그림을 시작점으로 책임과 Runtime을 하나씩 내려가며 설명했다는 점입니다.

이제 자연스럽게 다음 질문이 생깁니다. **정적인 Architecture를 거래 한 건의 시간축으로 펼쳐본다**. 그 질문이 다음 단계인 **런타임 서비스**의 출발점입니다.
