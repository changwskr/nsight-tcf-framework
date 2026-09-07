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
