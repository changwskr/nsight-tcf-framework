# NSIGHT / PDMG 아키텍처 정의서 — IV. PDMG Online Runtime & TCF Flow

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 대상: PDMG Online Runtime / TCF AS-IS Reference  
> 문서 상태: **Draft / Evidence-First**  
> 작성일: 2026-08-31  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_III_PDMG_Module_Application_Architecture.md`

---

# 0. Evidence Register

| ID | 근거 자료 | 본 장 사용 목적 | 상태 |
|---|---|---|---|
| EV-IV-01 | `2장. 전체 온라인 거래 빅픽처` | HTTP 요청 한 건의 전체 AS-IS Runtime | `[AS-IS EVIDENCE]` |
| EV-IV-02 | `12장. TCF 온라인 거래 프레임워크` | Controller/TcfFacade/Dispatcher/Handler/STF/ETF 실제 연결 | `[AS-IS EVIDENCE]` |
| EV-IV-03 | `09장.Filter와 Spring MVC` | Servlet Filter → MVC → Controller 생명주기 | `[AS-IS EVIDENCE]` |
| EV-IV-04 | `10장.Interceptor와 시스템 선처리` | Interceptor pre/after, ImageLog, 시스템 선후처리 | `[AS-IS EVIDENCE]` |
| EV-IV-05 | `11장.ServiceContext와 GUID` | Context 생성·보강·ThreadLocal·정리 | `[AS-IS EVIDENCE]` |
| EV-IV-06 | `14장.Handler와 Controller` | TransactionHandler 계약, Handler Registry, Facade 위임 | `[AS-IS EVIDENCE]` |
| EV-IV-07 | `11.예외처리-1.md` | 성공/실패 Envelope, GlobalExceptionHandler, Advice 중첩 Risk | `[AS-IS EVIDENCE]` |
| EV-IV-08 | `13장.TCF OFF 호환 구조` | TCF OFF Bean/Runtime/Controller 경로 | `[AS-IS EVIDENCE]` |
| EV-IV-09 | `00.BigPicture Tx 처리-1.md` | Worker/TransactionTemplate/설정값 Current Snapshot | `[AS-IS EVIDENCE]` |
| EV-IV-10 | `11.Http CORS적용-1.md` | DefaultFilter와 Security/CORS Chain 순서 | `[AS-IS EVIDENCE]` |
| EV-IV-11 | `NSIGHT_PDMG_아키텍처_정의서_III_PDMG_Module_Application_Architecture.md` | Module/Process/Package/TCF ON-OFF Handoff | `[CURRENT BASELINE DRAFT]` |
| EV-IV-12 | `NSIGHT_PDMG_아키텍처_인포그래픽_이미지화_마스터_프롬프트.md` | IV장 필수 Figure/금지 규칙 | `[WORKING BASELINE]` |

> **본 장의 Evidence 우선순위**
>
> ```text
> Current Source / Current application.yml
>      >
> 2026-08 Source 분석문서
>      >
> 통합 과거 문서 / 과거 그림
>      >
> 설계 의도 / 일반론
> ```
>
> 이전 문서와 현재 Source가 다르면 현재 Source를 `[AS-IS]`로 사용하고 차이를 `[DRIFT]` 또는 `[GAP]`로 기록한다.

---

# 1. 현재 Snapshot 전제

본 장의 주 Runtime은 다음 설정 조합을 기준으로 한다.

```yaml
nhnis:
  fw:
    tcf:
      enabled: true
    timeout:
      enabled: true
      milliseconds: 5000
      pool-size: 20
      queue-capacity: 100
    commons:
      filter:
        enabled: true
      legacy-web:
        enabled: true
```

### Snapshot 의미

| 설정 | 현재 장에서 활성화되는 핵심 |
|---|---|
| `tcf.enabled=true` | `OnlineTransactionController → TcfFacade → Dispatcher → Handler` |
| `timeout.enabled=true` | Worker Thread + `TransactionTemplate` |
| `filter.enabled=true` | `DefaultFilter`, Header/JWT/ServiceContext/MDC |
| `legacy-web.enabled=true` | Interceptor/Resolver/BizPrePostAspect |

> 숫자 `5000 / 20 / 100`은 **현재 분석 Snapshot의 AS-IS 값**이다. NSIGHT 전체 운영 표준값이나 최종 NFR로 자동 승격하지 않는다.

---

# 2. Figure Plan

| FIG | 제목 | Level | 목적 | 필수 |
|---|---|---:|---|---|
| FIG-IV-01 | 전체 Online Runtime Journey | L0~L3 | 요청→DB→응답 전체 시간순 흐름 | Y |
| FIG-IV-02 | Servlet / Security / MVC / TCF Boundary | L1~L3 | 실행 프레임 경계 분리 | Y |
| FIG-IV-03 | DefaultFilter 상세 | L2~L3 | Body/JWT/Header/Context/MDC | Y |
| FIG-IV-04 | ServiceContext Lifecycle | L2~L4 | 생성→보강→Worker→응답→정리 | Y |
| FIG-IV-05 | Interceptor / System Pre-Post | L2~L3 | 시스템 선후/ImageLog | Y |
| FIG-IV-06 | OnlineTransactionController 역할 | L2~L3 | ServiceId 결정·dto 추출 | Y |
| FIG-IV-07 | TransactionContext / TcfFacade | L2~L3 | TCF 실행정보와 Executor 연결 | Y |
| FIG-IV-08 | ServiceId → Dispatcher → Handler Registry | L2~L3 | 확장점/누락/중복 처리 | Y |
| FIG-IV-09 | Handler → Facade → Service → DAO | L2~L3 | Business Runtime 책임 | Y |
| FIG-IV-10 | 시스템/업무 선후처리 4구간 | L2~L4 | Pre/Post 책임 분리 | Y |
| FIG-IV-11 | 정상 Response Assembly | L3 | 성공 Envelope 형성 | Y |
| FIG-IV-12 | 오류 Response Assembly | L3~L4 | Filter/TCF/Legacy 오류 분기 | Y |
| FIG-IV-13 | TCF ON vs OFF Runtime | L2~L4 | Adapter/Timeout/Error 차이 | Y |
| FIG-IV-14 | STF/ETF Actual vs Intended | L3~L5 | Bean 존재와 실행연결 구분 | Y |
| FIG-IV-15 | Failure / Risk Map | L4 | 현재 Runtime 취약점 | Y |
| FIG-IV-16 | V장 Handoff | L5 | Thread/TX/Timeout 상세 연결 | Y |

---

# 3. 핵심 결론

IV장은 III장의 정적 구조를 **시간순 Runtime**으로 바꾼다.

현재 PDMG TCF ON + Timeout ON의 핵심 실행은 다음이다.

```text
HTTP Request
  ↓
DefaultFilter
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
  │
  ├─ Request Thread : Future.get(timeout)
  │
  └─ Worker Thread
       ↓
     TransactionTemplate
       ↓
     TransactionDispatcher
       ↓
     TransactionHandler
       ↓
     Business Facade
       ↓
     BizPrePostAspect.before
       ↓
     Service
       ↓
     DAO
       ↓
     Mapper XML
       ↓
     DB
       ↓
     BizPrePostAspect.afterReturning
       ↓
     Deadline Check
       ↓
     COMMIT / ROLLBACK
  │
  ▼
Response / Exception
  ↓
GlobalExceptionHandler 또는 Legacy Advice
  ↓
ResponseBodyArgumentResolver
  ↓
ServicePreventionInterceptor.afterCompletion
  ↓
DefaultFilter.finally
  ↓
HTTP Response
```

본 장에서 반드시 고정하는 핵심 사실은 다음과 같다.

1. **HTTP Request 생명주기와 DB Transaction 생명주기는 동일하지 않다.**
2. `DefaultFilter`부터 `TcfFacade`까지와 응답 후처리는 Request Thread에서 실행된다.
3. Timeout이 활성화되면 Dispatcher 이하 업무 실행은 `pdmg-online-*` Worker Thread에서 실행된다.
4. `ServiceContext`와 MDC는 ThreadLocal이므로 Worker에 자동 전달되지 않으며 Framework가 명시적으로 설치·정리한다.
5. `OnlineTransactionController`는 업무 로직을 실행하지 않고 **ServiceId와 dto를 TCF 계약으로 변환**한다.
6. `TransactionDispatcher`는 ServiceId로 `TransactionHandler`를 선택한다.
7. Handler는 업무 Facade를 호출하며 DAO/SQL을 직접 실행하지 않는다.
8. 현재 `STF`와 `ETF` Bean은 존재할 수 있으나 **현행 `TcfFacade` 호출선에는 연결되어 있지 않다.**
9. 성공 응답은 `{ hdr_nhnis, dto }`, 알려진 실패는 `{ hdr_nhnis, result }` 구조가 현재 구현과 일치한다.
10. Filter 단계 `sendError` 오류는 Controller Advice에 도달하지 않으므로 표준 `result` Envelope가 보장되지 않는다.
11. TCF OFF는 Framework 전체 OFF가 아니라 TCF Core/Handler Registry를 우회하는 다른 Entry Adapter 구조다.
12. TCF OFF AS-IS 일부 Controller는 Facade를 우회하고 Service를 직접 호출하여 ON/OFF의 Transaction/Use Case 경계가 달라질 수 있다.

---

# 4. 목적 / 범위 / 전제

## 4.1 목적

본 장은 다음 질문을 해결한다.

1. Browser에서 요청한 거래는 어떤 순서로 DB까지 이동하는가?
2. Servlet Filter, Spring Security, MVC, TCF, Business Layer는 어느 순서로 실행되는가?
3. 어느 구간이 Request Thread이고 어디서 Worker Thread가 시작되는가?
4. `ServiceContext`는 언제 만들어지고 보강되고 제거되는가?
5. ServiceId는 어느 위치에서 결정되고 어떤 우선순위를 가지는가?
6. Dispatcher는 어떻게 Handler를 선택하는가?
7. 시스템 선처리와 업무 선처리는 어떻게 다른가?
8. 시스템 후처리와 업무 후처리는 어디에서 실행되는가?
9. 정상 응답과 오류 응답은 어디서 조립되는가?
10. TCF ON/OFF에서 무엇이 같고 무엇이 달라지는가?
11. STF/ETF가 “존재한다”는 것과 “실제 실행된다”는 것은 어떻게 다른가?
12. V장 Thread/Timeout/Transaction 분석으로 무엇을 넘겨야 하는가?

## 4.2 포함

```text
Browser / pdmg-ui
HTTP Request
CORS
DefaultFilter
SecurityFilterChain
DispatcherServlet
ServicePreventionInterceptor
OnlineTransactionController
TransactionContext
TcfFacade
OnlineTimeoutExecutor의 Runtime 위치
TransactionDispatcher
TransactionHandler
Business Facade
BizPrePostAspect
Service
DAO / Mapper / DB
ResponseBodyArgumentResolver
GlobalExceptionHandler
ImageLog
Context Clear
TCF ON/OFF
STF / ETF 연결 상태
```

## 4.3 제외

```text
Worker Pool 내부 세부                 → V
TransactionTemplate 상세              → V
Spring TX Propagation                 → V
JDBC Cancel / Interrupt               → V
표준전문 필드 전체                     → VI
예외 코드 정책 상세                    → VI
JWT 발급/JWKS/SSO                     → VII
Server/Tomcat/Hikari Capacity         → VIII
OM/APM/Metric                         → IX
ServiceId 전체 자동 Trace            → X
```

---

# 5. FIG-IV-01 — 전체 Online Runtime Journey

## 5.1 TCF ON + Timeout ON AS-IS

```text
Browser
  │
  │ 사용자 거래 실행
  ▼
pdmg-ui
  │
  │ POST /{serviceId}
  │ { hdr_nhnis, dto }
  ▼
════════════════════════ HTTP Request Thread ════════════════════════

[1] DefaultFilter
  ├─ OPTIONS 제외
  ├─ Body Cache
  ├─ Header / GUID
  ├─ 비-local JWT
  ├─ ServiceContext
  └─ MDC
  │
  ▼
[2] Spring SecurityFilterChain
  │
  ▼
[3] DispatcherServlet
  │
  ├─ HandlerMapping
  └─ HandlerExecutionChain
  │
  ▼
[4] ServicePreventionInterceptor.preHandle
  ├─ Header/sys_comm 보강
  ├─ GUID 보완
  ├─ ServiceId/IP/User 보완
  ├─ Request Log
  └─ Pre ImageLog
  │
  ▼
[5] OnlineTransactionController
  ├─ ServiceId 결정
  ├─ dto 추출
  └─ TcfFacade.process()
  │
  ▼
[6] TcfFacade
  ├─ TransactionContext.fromCurrent(serviceId)
  └─ OnlineTimeoutExecutor.execute(...)
  │
  ▼
[7] OnlineTimeoutExecutor
  ├─ Worker Context capture
  ├─ Task submit
  └─ Request Thread : Future.get(5000ms)
  │
  ├─────────────────────────────────────────────────────────────┐
  │                                                             │
  │                 ═════════ Worker Thread ═════════            │
  │                                                             ▼
  │              [8] Worker Context install                     │
  │                  ServiceContext / MDC                       │
  │                       │                                     │
  │                       ▼                                     │
  │              [9] TransactionTemplate BEGIN                  │
  │                       │                                     │
  │                       ▼                                     │
  │             [10] TransactionDispatcher                      │
  │                       │ serviceId                            │
  │                       ▼                                     │
  │             [11] TransactionHandler                         │
  │                       │                                     │
  │                       ▼                                     │
  │             [12] Business Facade                            │
  │                       │                                     │
  │                       ▼                                     │
  │             [13] BizPrePostAspect.before                    │
  │                       │                                     │
  │                       ▼                                     │
  │             [14] Service                                    │
  │                       │                                     │
  │                       ▼                                     │
  │             [15] DAO / Mapper / DB                          │
  │                       │                                     │
  │                       ▼                                     │
  │             [16] BizPrePostAspect.afterReturning            │
  │                       │                                     │
  │                       ▼                                     │
  │             [17] Deadline Check                             │
  │                       │                                     │
  │                       ▼                                     │
  │             [18] COMMIT / ROLLBACK                          │
  │                       │                                     │
  │                       ▼                                     │
  │             [19] Worker Context Clear                       │
  │                                                             │
  └────────────── 결과 / 예외 ◄──────────────────────────────────┘
  │
  ▼
[20] Controller 반환 또는 Exception Handler
  │
  ▼
[21] ResponseBodyArgumentResolver
  ├─ 성공 → hdr_nhnis + dto
  └─ 오류 → hdr_nhnis + result
  │
  ▼
[22] ServicePreventionInterceptor.afterCompletion
  ├─ 정상 → Post ImageLog
  └─ 오류 → Exception/Post ImageLog
  │
  ▼
[23] DefaultFilter.finally
  ├─ ServiceContextHolder.remove
  └─ MDC clear
════════════════════════ HTTP Response ═══════════════════════════════
  │
  ▼
pdmg-ui
  │
  ▼
Browser
```

## 5.2 Runtime을 이해하는 네 개의 시간축

```text
HTTP 생명주기
Filter ─────────────────────────────────────────────── finally

MVC 생명주기
DispatcherServlet → Interceptor → Controller → Advice → afterCompletion

TCF 생명주기
OnlineController → TcfFacade → Executor → Dispatcher → Handler

DB Transaction
                 Worker TransactionTemplate BEGIN ───── COMMIT/ROLLBACK
```

즉:

```text
HTTP
≠ MVC
≠ TCF
≠ DB Transaction
```

---

# 6. Browser / pdmg-ui → pdmg-service

## 6.1 일반 직접 호출 `[AS-IS]`

```text
Browser
   │
   │ fetch / POST
   ▼
pdmg-service :8080
```

PDMG UI 분석자료의 대표 예:

```text
POST /mgcoa9000S0
```

## 6.2 Relay 호환경로

```text
Browser
   ▼
pdmg-ui
   │ /api/relay/{serviceId}
   ▼
TransactionRelayService
   │ HTTP
   ▼
pdmg-service
```

Relay를 사용해도 Service 내부 TCF 실행은 동일하지만:

```text
Browser → UI Server → Service
```

라는 네트워크 경계가 하나 더 생긴다.

## 6.3 CORS

직접 호출 구조에서는 Browser Origin과 Service Origin이 다르면 CORS가 선행된다.

대표 Local 분석:

```text
pdmg-ui      :8090
pdmg-service :8080
```

CORS 실패는 Business Layer 장애가 아니다.

```text
Browser CORS Failure
   ↓
Controller 미진입 또는 Browser가 Response 차단
```

---

# 7. FIG-IV-02 — Servlet / Security / MVC / TCF Boundary

```text
┌──────────────────────── Servlet Container ────────────────────────┐
│                                                                   │
│ HTTP Request                                                      │
│   │                                                               │
│   ▼                                                               │
│ DefaultFilter                                                     │
│   │                                                               │
│   ▼                                                               │
│ Spring SecurityFilterChain                                        │
│   │                                                               │
│   ▼                                                               │
│ DispatcherServlet                                                 │
│   │                                                               │
│   ├─ HandlerMapping                                               │
│   ├─ ArgumentResolver / MessageConverter                          │
│   └─ HandlerExecutionChain                                        │
│         │                                                         │
│         ▼                                                         │
│ ServicePreventionInterceptor.preHandle                            │
│         │                                                         │
│         ▼                                                         │
│ OnlineTransactionController                     [TCF Boundary]    │
│         │                                                         │
│         ▼                                                         │
│ TcfFacade → TimeoutExecutor → Dispatcher → Handler                │
│         │                                                         │
│         ▼                                                         │
│ Business Facade → Service → DAO                  [Business]       │
│         │                                                         │
│         ▼                                                         │
│ ResponseBodyAdvice / ExceptionHandler                             │
│         │                                                         │
│         ▼                                                         │
│ Interceptor.afterCompletion                                       │
│   │                                                               │
│   ▼                                                               │
│ DefaultFilter.finally                                             │
└───────────────────────────────────────────────────────────────────┘
```

## 7.1 경계별 책임

| 경계 | 주 책임 | 금지 책임 |
|---|---|---|
| Servlet Filter | 원시 HTTP 준비, Context/JWT | 업무 SQL |
| Security Filter Chain | Web Security | Business Rule |
| MVC | Mapping/Binding/Advice | DB Transaction 전체 |
| Interceptor | 시스템 선후/ImageLog | 업무 Rule |
| TCF | ServiceId 실행/라우팅/Timeout 연결 | 고객/상품 판단 |
| Handler | 거래 Adapter | DAO/SQL |
| Business | 유스케이스/업무 | Servlet 처리 |
| Persistence | SQL/Data | HTTP 계약 |

---

# 8. FIG-IV-03 — DefaultFilter 상세

## 8.1 등록/위치

Current Source 분석 기준:

```text
FilterConfiguration
  └─ FilterRegistrationBean<DefaultFilter>
       ├─ URL : /*
       └─ Order : Ordered.HIGHEST_PRECEDENCE + 20
```

과거 일부 정리문서에는 `order=1` 표현이 있으므로 최신 Source를 기준으로 한다.

`[DRIFT-IV-01]`

```text
과거 문서 order=1
vs
현재 Source 분석 HIGHEST_PRECEDENCE + 20
```

## 8.2 정상 JSON 처리

```text
DefaultFilter.doFilter
   │
   ├─ URI / Header 수집
   │
   ├─ OPTIONS ?
   │     └─ YES → Filter 제외
   │
   ├─ multipart ?
   │     └─ 별도 최소 Context
   │
   └─ 일반 JSON
         │
         ├─ CachedBodyHttpServletRequest
         ├─ 비-local JWT Bearer 검증
         ├─ Body Empty 검증
         ├─ JSON Parse
         ├─ hdr_nhnis 검증
         │    ├─ local → synthetic header 허용
         │    └─ non-local → 없으면 400
         ├─ sys_comm 보강
         ├─ GUID / IP / User / serviceId → MDC
         ├─ ServiceContext 생성
         ├─ ServiceContextHolder.set
         └─ filterChain.doFilter(wrapper)
                 │
                 ▼
             finally
                 ├─ ServiceContextHolder.remove
                 └─ ThreadContext.clearAll
```

## 8.3 Filter 책임

```text
Raw HTTP
   ↓
Reusable Request
   +
Common Header
   +
Request Context
   +
Trace Context
   +
JWT Gate
```

## 8.4 Filter에서 종료되는 오류

현재 분석에서 Filter는 다음 오류를 `sendError()`로 조기 종료한다.

| 조건 | HTTP |
|---|---:|
| Access Token 없음 | 401 |
| Token 검증 실패 | 401 |
| Access Token 아님 | 401 |
| Body 없음 | 400 |
| 잘못된 JSON | 400 |
| 공통 Header 없음(non-local) | 400 |
| Filter 내부 오류 | 400 |

이 경우:

```text
Controller 미진입
TCF 미진입
Business 미진입
GlobalExceptionHandler 미진입
```

할 수 있다.

따라서 Filter 오류는 현재 `hdr_nhnis + result` 표준 오류 Envelope를 항상 보장하지 않는다.

---

# 9. Spring SecurityFilterChain

## 9.1 현재 위치

```text
DefaultFilter
   ↓
Spring SecurityFilterChain
   ↓
DispatcherServlet
```

현재 분석자료상 `pdmg-service`의 App `SecurityFilterChain`이 사용되며, `pdmg-fw`의 legacy `commons.security.enabled` SecurityConfig는 기본 비활성이다.

## 9.2 현재 주요 해석

```text
DefaultFilter
= 비-local JWT 검증의 실제 Gate

App SecurityFilterChain
= CSRF/Session/CORS/인가 체인
```

샘플/현행 설정은 `permitAll` 성격이 분석되어 있으므로:

```text
Spring SecurityFilterChain 존재
≠
현재 Business Authorization 완전 적용
```

이다.

상세 인증/인가 구조는 VII장에 넘긴다.

## 9.3 이중 보안 체인 금지

```text
commons SecurityConfig ON
+
app SecurityConfig ON
           X

TCF JwtAuthenticationFilter
+
DefaultFilter JWT
           X
```

동일 요청에 중복 Security Filter를 병행하면 정책 충돌·이중검증·Bean 충돌 위험이 있다.

---

# 10. DispatcherServlet과 MVC

## 10.1 Spring MVC 경계

```text
filterChain.doFilter()
   ↓
DispatcherServlet
   ├─ HandlerMapping
   ├─ HandlerInterceptor
   ├─ ArgumentResolver
   ├─ HandlerAdapter
   ├─ Controller
   ├─ ReturnValue Handler
   └─ ResponseBodyAdvice
```

## 10.2 왜 Filter와 MVC를 분리하는가

Filter에서 알 수 있는 것:

```text
HTTP Method
URI
Header
Content-Type
Raw Body
```

MVC에서 알 수 있는 것:

```text
어떤 Controller인가
어떤 Mapping인가
어떤 Argument Type인가
어떤 Handler Method인가
```

업무 ServiceId별 Controller 정책을 Filter에 과도하게 넣지 않는다.

---

# 11. FIG-IV-04 — ServiceContext Lifecycle

```text
Request Thread
   │
   ▼
DefaultFilter
   ├─ ServiceContext 생성
   ├─ Header
   ├─ GUID
   ├─ Request Headers
   ├─ Servlet Request/Response 참조
   └─ requestBody
   │
   ▼
ServiceContextHolder.set
   │
   ▼
Interceptor
   ├─ Header/sys_comm 보완
   ├─ GUID 보완
   └─ MDC 동기화
   │
   ▼
OnlineTransactionController
   └─ ServiceId / IP 보완
   │
   ▼
TransactionContext.fromCurrent
   │
   └─ ServiceContext 참조 캡처
   │
   ▼
OnlineTimeoutWorkerContext.capture
   │ Thread 경계
   ▼
Worker
   ├─ Holder install
   ├─ MDC install
   └─ Handler / Business
   │
   ▼
Worker finally
   ├─ Holder clear
   └─ MDC clear
   │
   ▼
Request Thread
   │
   ▼
ResponseBodyArgumentResolver
   └─ responseBody 저장
   │
   ▼
afterCompletion
   └─ Post / Exception ImageLog
   │
   ▼
DefaultFilter.finally
   ├─ Holder.remove
   └─ MDC.clearAll
```

## 11.1 Context가 자동으로 해주는 것이 아닌 것

```text
DB Transaction
JWT 정책 자체
Business Authorization
Thread 자동전파
JDBC Connection
DTO 생명주기
```

## 11.2 중요 Risk

현재 Worker는 독립 immutable Snapshot이 아니라 동일 `ServiceContext` 참조를 공유하는 것으로 분석된다.

따라서 다음을 유의한다.

```text
Servlet Request/Response 참조
   +
Worker Thread
   +
Request 완료 시점
```

의 수명 관계를 안전하게 유지해야 한다.

상세 Thread 안전성은 V장에 넘긴다.

---

# 12. FIG-IV-05 — ServicePreventionInterceptor / 시스템 선후처리

## 12.1 preHandle

```text
DispatcherServlet
   ↓
ServicePreventionInterceptor.preHandle
   │
   ├─ multipart 여부
   ├─ ServiceContext 확인
   ├─ Header/sys_comm 보장
   ├─ GUID 보완
   ├─ MDC 보완
   ├─ Service ID 보완
   ├─ IP/User 정보 보완
   ├─ Request Log
   └─ preImagelog
   │
   └─ true
       ↓
     Controller
```

## 12.2 afterCompletion

```text
Controller / Advice 완료
   ↓
ServicePreventionInterceptor.afterCompletion
   │
   ├─ 정상
   │   └─ postImagelog
   │
   └─ 예외
       └─ exceptionImagelog
```

## 12.3 중요한 구분

```text
ServicePreventionInterceptor
= MVC 시스템 선후처리

STF / ETF
= TCF 시스템 선후 확장점
```

둘은 같은 것이 아니다.

현재 AS-IS에서는 Interceptor가 실제 Request 경로에 연결되어 있고, STF/ETF는 TcfFacade 실제 호출선에 연결되어 있지 않다.

---

# 13. 시스템 선처리 ≠ 거래통제 STF

현재 Source 분석에는 `stf` Bean과 거래통제 Service가 존재하지만:

```text
TcfFacade.process()
   ↓
TransactionContext 생성
   ↓
OnlineTimeoutExecutor
   ↓
Dispatcher
```

로 바로 진행하며:

```text
stf.preProcess(...)
```

호출이 현재 연결되어 있지 않다.

따라서:

```text
거래통제 코드 존재
≠
모든 TCF 거래에 거래통제 적용
```

이다.

이 차이는 Runtime Evidence에서 반드시 보이게 해야 한다.

---

# 14. FIG-IV-06 — OnlineTransactionController

## 14.1 TCF ON 공통 Controller

TCF ON에서 공통 POST Entry를 제공한다.

```text
POST /online
POST /{businessCode}/online
POST /{serviceId}
```

모든 경로는 내부 `handle()`로 수렴하는 구조로 분석된다.

## 14.2 ServiceId 결정 우선순위 `[AS-IS]`

```text
1. ServiceContext.header.sys_comm.rms_svc_c
        ↓ 없으면
2. Request JSON hdr_nhnis.sys_comm.rms_svc_c
        ↓ 없으면
3. Path Variable
        ↓ 없으면
4. null
```

## 14.3 Risk — Path/Header 불일치

예:

```text
Path
/mgcoa9000S0

Header
rms_svc_c = mgcoa9001D0
```

현재 Controller는 복수 위치 값의 정합성을 강제 검증하지 않는 것으로 분석된다.

그러면 Header 값이 선택될 수 있다.

`[RISK-IV-01]`

```text
URL ServiceId
≠
Header ServiceId
```

인데 요청이 거부되지 않을 수 있다.

### TO-BE 후보

```text
Path와 Header가 둘 다 존재하면
   │
   ├─ 동일 → 진행
   └─ 불일치 → 표준 오류
```

## 14.4 dto 추출

Controller는 전체 전문을 업무 Handler에 넘기지 않는다.

```text
Request
{
  hdr_nhnis : {...},
  dto       : {...}
}

      ↓

TCF 전달
dto only
+
TransactionContext
```

Header/GUID는 `ServiceContext/TransactionContext` 경계를 통해 접근한다.

---

# 15. FIG-IV-07 — TransactionContext / TcfFacade

## 15.1 TransactionContext

`TransactionContext`는 DB Transaction 객체가 아니다.

```text
TransactionContext
├─ serviceId
├─ ServiceContext 참조
└─ startedAtNanos
```

의미:

```text
ServiceId Routing
Header/GUID 접근
TCF 경과시간
```

의미하지 않는 것:

```text
JDBC Connection
Spring TransactionStatus
DB Transaction ID
Commit Time
```

## 15.2 TcfFacade

현재 TcfFacade의 핵심은 단순하다.

```text
serviceId
  ↓
TransactionContext.fromCurrent
  ↓
OnlineTimeoutExecutor.execute(
   dispatcher.dispatch(...)
)
  ↓
결과 반환
```

TcfFacade는:

```text
SQL 실행 X
업무 DTO 세부 판단 X
ImageLog 조립 X
HTTP Envelope 조립 X
업무 Facade 역할 X
```

## 15.3 Timeout ON/OFF 영향

```text
Timeout OFF
Request Thread
   ↓
Dispatcher
   ↓
Handler
   ↓
Business

Timeout ON
Request Thread
   ↓ submit
Worker Thread
   ↓
TransactionTemplate
   ↓
Dispatcher
   ↓
Handler
   ↓
Business
```

본 장에서는 위치만 고정하고 세부 Transaction/Deadline은 V장에서 상세화한다.

---

# 16. OnlineTimeoutExecutor — Runtime 위치

## 16.1 현재 설정값 `[AS-IS SNAPSHOT]`

```text
milliseconds    = 5000
pool-size       = 20
queue-capacity  = 100
```

의미:

```text
Worker 최대 20개 실행
       +
대기 Queue 100
       ↓
수용 초과
       ↓
OnlineOverloadException
       ↓
HTTP 503
```

Request Thread:

```text
Future.get(5000ms)
```

Worker:

```text
pdmg-online-N
```

## 16.2 이 장에서 다루지 않는 것

```text
TransactionTemplate 내부 Propagation
Deadline rollback
Future.cancel(true)
JDBC interrupt
late commit
```

은 V장에 위임한다.

---

# 17. FIG-IV-08 — ServiceId → Dispatcher → Handler Registry

## 17.1 Registry 개념

Framework는 구체 업무 Handler 클래스를 직접 import하지 않는다.

```text
pdmg-fw
TransactionHandler Interface
        ▲
        │ implements
        │
pdmg-service
mgcoa5530Handler
mgcoa8888Handler
mgcoa9000Handler
mgcoa9001Handler
mgcoa9100Handler
mgcoa9999Handler
```

Spring Context에서 Handler 구현체 목록을 수집하여 Registry를 구성한다.

## 17.2 ServiceId 등록 예

| Handler | ServiceId |
|---|---|
| `mgcoa5530Handler` | `S0` |
| `mgcoa8888Handler` | `S0`, `D0` |
| `mgcoa9000Handler` | `S0`, `C0`, `U0`, `D0` |
| `mgcoa9001Handler` | `S0`, `C0`, `U0`, `D0` |
| `mgcoa9100Handler` | `S0` |
| `mgcoa9999Handler` | `S0` |

## 17.3 Dispatcher 흐름

```text
serviceId
mgcoa9000C0
   │
   ▼
handlerMap[serviceId]
   │
   ├─ found
   │    ↓
   │  mgcoa9000Handler
   │
   └─ not found
        ↓
      ServiceHandlerNotFound
```

## 17.4 Handler Registry의 Architecture 가치

```text
신규 ServiceId
     ↓
Business Handler 추가
     ↓
serviceIds() 등록
     ↓
Spring Registry 자동 확장
```

Framework Dispatcher 소스를 신규 거래마다 수정하지 않는 구조다.

## 17.5 중복 ServiceId

중복 등록이 발생하면 Framework가 정상 기동 시점에 이를 감지/거부해야 한다.

정확한 현재 중복 처리 구현은 Source 단위로 X장에서 다시 자동검증한다.

`[OPEN-IV-01]`

---

# 18. FIG-IV-09 — Handler → Business Runtime

## 18.1 Handler 책임

```text
Dispatcher
   ↓
TransactionHandler.handle(dtoBody, context)
   │
   ├─ context.serviceId
   ├─ 거래 분기
   └─ Business Facade 호출
```

Handler는 Inbound Adapter다.

## 18.2 대표 예

```text
mgcoa9000S0
   ↓
mgcoa9000Handler
   ↓
facade.mgcoa9000S0(...)
```

```text
mgcoa9000C0
   ↓
mgcoa9000Handler
   ↓
facade.mgcoa9000C0(...)
```

## 18.3 정상 Business 경로

```text
TransactionHandler
   ↓
Business Facade
   ↓
BizPrePostAspect.before
   ↓
Service
   ↓
DAO
   ↓
Mapper XML
   ↓
DB
   ↓
Service Return
   ↓
BizPrePostAspect.afterReturning
   ↓
Facade Return
   ↓
Handler Return
```

## 18.4 Handler 금지 책임

```text
DAO 직접 호출              X
SQL 작성                    X
핵심 업무 계산             X
TransactionManager 직접조작 X
Servlet Request 재파싱      X
```

---

# 19. Business Facade / Service / DAO

## 19.1 Business Facade

현재 역할:

```text
Use Case Boundary
DTO 변환/조정
Service 조합
@Transactional 선언 위치(다수 업무)
```

하지만:

```text
TCF ON + Timeout ON
```

에서 실제 최외곽 DB Transaction은 Worker의 `TransactionTemplate`이므로:

```text
Facade Transaction
= 항상 최외곽 Transaction
```

이라고 쓰지 않는다.

## 19.2 BizPrePostAspect

현재 Pointcut은 업무 Service public Method를 기준으로 분석된다.

```text
Facade
   ↓
Service Proxy
   │
   ├─ BizPrePostAspect.before
   ▼
Service
   │
   └─ 정상 반환
       ↓
     BizPrePostAspect.afterReturning
```

예외가 발생하면 `afterReturning`은 실행되지 않는다.

## 19.3 Service

```text
업무 절차
업무 판단
DAO 호출
결과 DTO 작성
```

## 19.4 DAO / Mapper

```text
Service
   ↓
DAO Interface
   ↓
Mapper XML
   ↓
SQL
   ↓
DB
```

---

# 20. FIG-IV-10 — 시스템 선처리 / 업무 선후처리 / 시스템 후처리

PDMG의 “선후처리”는 하나가 아니다.

```text
[시스템 선처리 1]
DefaultFilter
- Header
- JWT
- Context
- MDC

       ↓

[시스템 선처리 2]
ServicePreventionInterceptor.preHandle
- GUID 보완
- Request Log
- Pre ImageLog

       ↓

[업무 선처리]
BizPrePostAspect.before
- 업무 Service 호출 전 공통 처리/로그

       ↓

[업무 실행]
Service / DAO

       ↓

[업무 후처리]
BizPrePostAspect.afterReturning
- 정상 반환 후 처리/로그

       ↓

[시스템 응답 후처리]
ResponseBodyAdvice
- 응답 Envelope
- responseBody Context

       ↓

[시스템 후처리]
ServicePreventionInterceptor.afterCompletion
- Post/Exception ImageLog

       ↓

[최종 정리]
DefaultFilter.finally
- Context/MDC clear
```

## 20.1 DB Transaction 포함 여부

아래는 **TCF ON + Timeout ON** 시 구조적 해석이다.

```text
Request Thread
DefaultFilter
Interceptor.pre
TcfFacade
     │
     └─ Worker TX BEGIN
           ├─ Dispatcher
           ├─ Handler
           ├─ Facade
           ├─ BizPrePostAspect.before
           ├─ Service
           ├─ DAO
           ├─ BizPrePostAspect.afterReturning
           └─ TX END
Request Thread
Response Advice
Interceptor.afterCompletion
Filter.finally
```

따라서 시스템 선후처리의 대부분은 업무 DB Transaction 바깥이다.

---

# 21. STF / ETF — 의도와 실제 연결을 분리

## 21.1 의도된 TCF 확장 흐름

```text
Controller
   ↓
TcfFacade
   ↓
STF.preProcess
   ↓
Dispatcher / Handler / Business
   ↓
ETF.postProcess
   ↓
Response
```

## 21.2 현재 AS-IS 실제 호출

```text
Controller
   ↓
TcfFacade
   ↓
OnlineTimeoutExecutor
   ↓
Dispatcher
   ↓
Handler
   ↓
Business
```

현재 `TcfFacade`는 Dispatcher와 TimeoutExecutor를 연결하며 STF/ETF 호출선이 확인되지 않는다.

## 21.3 FIG-IV-14 — Bean 존재 ≠ Runtime 실행

```text
Spring Context

STF Bean        ETF Bean
   │               │
   │ 존재          │ 존재
   │               │
   └────── X ──────┘
       현재 TcfFacade
       호출선 미연결


실제 Runtime
TcfFacade
   ↓
TimeoutExecutor
   ↓
Dispatcher
```

## 21.4 Architecture 의미

```text
Class 존재
Bean 존재
설계문서 존재
```

만으로 공통정책이 적용된다고 판단하면 안 된다.

반드시:

```text
Caller
  ↓
실제 Method Call
  ↓
Runtime Evidence
```

를 확인한다.

---

# 22. FIG-IV-11 — 정상 Response Assembly

## 22.1 업무 결과 반환

```text
DAO
  ↓
Service
  ↓
Facade
  ↓
Handler
  ↓
Dispatcher
  ↓
TcfFacade
  ↓
OnlineTransactionController return
```

## 22.2 ResponseBodyArgumentResolver

현재 성공 응답:

```json
{
  "hdr_nhnis": {...},
  "dto": {...}
}
```

구조:

```text
Controller Return Body
      │
      ▼
ResponseBodyArgumentResolver
      │
      ├─ ServiceContext.header
      ├─ body
      └─ responseBody 저장
      │
      ▼
{
  hdr_nhnis,
  dto
}
```

## 22.3 정상 후처리

```text
ResponseBodyAdvice
   ↓
ServiceContext.responseBody
   ↓
afterCompletion
   ↓
postImagelog
   ↓
Filter finally
```

---

# 23. FIG-IV-12 — 오류 Response Assembly

오류는 발생 위치에 따라 경로가 다르다.

## 23.1 TCF/Business 알려진 예외

```text
Dispatcher / Handler / Facade / Service
   ↓ exception
GlobalExceptionHandler
   ↓
NH_NIS_ERR_DTO
   ↓
ResponseBodyArgumentResolver
   ↓
{
  hdr_nhnis,
  result
}
```

현재 알려진 TCF Handler:

| 예외 | 코드 | HTTP |
|---|---|---:|
| `ServiceHandlerNotFound` | `E9999` | 500 |
| `BizException` | 업무 코드 | 500 |
| `OnlineTimeoutException` | `FW_TIMEOUT` | 504 |
| `OnlineOverloadException` | `FW_OVERLOADED` | 503 |

## 23.2 Legacy `NhBaseException`

```text
NhBaseException
  ↓
ResponseBodyArgumentResolver @ExceptionHandler
  ↓
NH_NIS_ERR_DTO
  ↓
result
```

## 23.3 Filter 오류

```text
DefaultFilter
  ↓
sendError(400/401)
  ↓
MVC 미진입
  ↓
GlobalExceptionHandler 미진입
```

따라서 표준 `result` Envelope가 보장되지 않는다.

## 23.4 일반 Runtime / DB 예외

현재 `GlobalExceptionHandler`에 포괄적인:

```java
@ExceptionHandler(Exception.class)
```

이 확인되지 않는 분석 결과가 있다.

따라서:

```text
NullPointerException
SQL Exception
MyBatis Exception
DataSource Error
```

가 Spring 기본 `/error` 구조로 갈 위험이 있다.

`[RISK-IV-02]`

---

# 24. Advice 중첩 Risk

현재 예외 Advice는 둘 이상이 후보가 될 수 있다.

```text
GlobalExceptionHandler
  └─ ServiceHandlerNotFound 전용

ResponseBodyArgumentResolver
  └─ NhBaseException 포괄
```

`ServiceHandlerNotFound`가 `NhBaseException` 계열이면 둘 다 처리 후보가 될 수 있다.

명시적 `@Order`가 없다는 분석이 있으므로:

```text
동일 예외
   ↓
어떤 Advice가 먼저 처리?
   ↓
응답 코드/메시지 Drift 가능
```

`[RISK-IV-03]`

장기적으로:

```text
Exception Mapping
와
ResponseBodyAdvice
```

책임을 분리하는 것이 더 명확하다.

---

# 25. Error Envelope의 현재 계약

## 성공

```text
hdr_nhnis
+
dto
```

## 실패

```text
hdr_nhnis
+
result
```

현재 `ResponseBodyArgumentResolver`는 `NH_NIS_ERR_DTO`인 경우 `result`에 넣는 구조로 분석된다.

이 계약은 VI장에서 상세 스키마와 Error Type/Message Source로 확장한다.

---

# 26. ImageLog와 Response의 연결

현재 오류 Response의:

```text
result.stdErrCode
result.stdErrMsgCntn
result.errType
```

를 `ImageLogHandler`가 읽어 오류 결과를 저장하는 구조가 분석된다.

```text
ResponseBodyAdvice
   │
   └─ result
       │
       ▼
ServiceContext.responseBody
       │
       ▼
afterCompletion
       │
       ▼
ImageLogHandler
       │
       ├─ EXCEPTION_CODE
       ├─ MESSAGE
       └─ TYPE
```

따라서 응답 Envelope와 ImageLog Format은 독립적으로 바꾸면 안 된다.

---

# 27. afterCompletion 예외 재던지기 Risk

현재 분석에는 `ServicePreventionInterceptor.afterCompletion()`에서:

```text
ex != null
  ↓
exceptionImagelog
  ↓
Context remove
  ↓
throw ex
```

패턴이 존재한다.

`afterCompletion`은 이미 MVC 처리 완료 뒤 정리 단계다.

여기서 예외를 다시 던지면:

```text
원래 예외
   +
후처리 중 재예외
   ↓
중복 로그
Container 재처리
원인 혼동
```

Risk가 있다.

`[RISK-IV-04]`

---

# 28. FIG-IV-13 — TCF ON vs TCF OFF Runtime

## 28.1 TCF ON `[AS-IS]`

```text
HTTP
 ↓
DefaultFilter
 ↓
Security
 ↓
Interceptor
 ↓
OnlineTransactionController
 ↓
TcfFacade
 ↓
TimeoutExecutor
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
Response Advice
 ↓
afterCompletion
```

## 28.2 TCF OFF `[AS-IS]`

```text
HTTP
 ↓
DefaultFilter
 ↓
Security
 ↓
Interceptor
 ↓
Business Controller
 ├─ 대부분 → Service → DAO
 └─ 일부   → Facade → Service → DAO
 ↓
Response Advice
 ↓
afterCompletion
```

## 28.3 OFF에서 사라지는 것

```text
OnlineTransactionController
TcfFacade
TransactionDispatcher
TransactionHandler
STF / ETF Bean
TCF 조건 GlobalExceptionHandler
```

## 28.4 OFF에서도 남을 수 있는 것

```text
DefaultFilter
ServiceContext
Interceptor
ResponseBodyAdvice
BizPrePostAspect
DataSource
TransactionManager
Service
DAO / Mapper
```

## 28.5 중요한 사실

```text
timeout.enabled=true
```

여도 OFF Controller는 TcfFacade를 거치지 않으므로:

```text
OnlineTimeoutExecutor
```

가 업무 요청을 감싸지 않는다.

즉:

```text
TCF OFF
→ Worker Pool Timeout 없음
→ TCF Overload/Timeout 예외 없음
```

일 수 있다.

---

# 29. ON/OFF 공통 TO-BE 후보

```text
TCF ON
OnlineController
     ↓
Handler ───────────────┐
                       │
                       ▼
                 Business Facade
                       │
TCF OFF                ▼
Business Controller ─► Service
                       │
                       ▼
                      DAO
```

더 엄밀히는:

```text
ON  : ServiceId Adapter
OFF : HTTP Typed Adapter

두 Adapter 모두
       ↓
동일 Facade
       ↓
동일 Service
       ↓
동일 DAO
```

를 지향한다.

이렇게 해야 ON/OFF Test가 **같은 업무 Core를 비교하는 Test**가 된다.

---

# 30. TCF OFF AS-IS의 경계 Drift

현재 OFF 분석:

```text
mgcoa5530  Controller → Service
mgcoa8888  Controller → Service
mgcoa9000  Controller → Service
mgcoa9001  Controller → Service
mgcoa9999  Controller → Service
mgcoa9100  Controller → Facade
```

결과:

```text
Facade DTO 변환
Facade Use Case Coordination
Facade @Transactional
```

가 거래별로 우회될 수 있다.

`[GAP-IV-01]`

---

# 31. Request Thread / Worker Thread Preview

본 장에서 Thread 경계만 표시한다.

```text
Tomcat Request Thread
   │
   ├─ DefaultFilter
   ├─ Security
   ├─ DispatcherServlet
   ├─ Interceptor
   ├─ Controller
   ├─ TcfFacade
   │
   └─ Future.get(timeout)
          │
          │ submit
          ▼
     pdmg-online-N
          │
          ├─ Context/MDC install
          ├─ TransactionTemplate
          ├─ Dispatcher
          ├─ Handler
          ├─ Business
          └─ clear
```

다음 질문은 V장으로 넘긴다.

```text
ThreadPool Queue가 가득 차면?
Future.cancel(true)는 무엇을 보장?
JDBC Query는 interrupt에 반응?
Deadline 후 Commit 가능?
TransactionTemplate와 @Transactional 관계?
Connection은 언제 획득?
```

---

# 32. Runtime 대표 시나리오 A — 정상 조회

`mgcoa9000S0` 예:

```text
Browser
   ↓
POST /mgcoa9000S0
   ↓
DefaultFilter
   ↓
Interceptor
   ↓
OnlineTransactionController
   │ serviceId = mgcoa9000S0
   │ dto 추출
   ↓
TcfFacade
   ↓
TimeoutExecutor
   ↓
Dispatcher
   ↓
mgcoa9000Handler
   ↓
mgcoa9000Facade.mgcoa9000S0
   ↓
mgcoa9000Service.mgcoa9000S0
   ↓
mgcoa9000DAO
   ↓
rdw.mg.co.a/mgcoa9000-ORA.xml
   ↓
DB
   ↓
DTOout
   ↓
ResponseBodyArgumentResolver
   ↓
{ hdr_nhnis, dto }
```

---

# 33. Runtime 대표 시나리오 B — Handler 미등록

```text
Client
   ↓
ServiceId = unknown
   ↓
Controller
   ↓
TcfFacade
   ↓
Dispatcher
   ↓
handlerMap miss
   ↓
ServiceHandlerNotFound
   ↓
GlobalExceptionHandler
   ↓
E9999 / SERVICE / HTTP 500
   ↓
result Envelope
```

단, Advice 중첩 우선순위 Risk는 별도 존재한다.

---

# 34. Runtime 대표 시나리오 C — Timeout

```text
Request Thread
   ↓
Future.get(5000ms)
   │
   └──── Worker
          ↓
        Business running

5초 초과
   ↓
OnlineTimeoutException
   ↓
GlobalExceptionHandler
   ↓
HTTP 504
   ↓
result.FW_TIMEOUT
```

그러나:

```text
HTTP 504
≠
Worker SQL 즉시 중단 보장
```

이다.

Worker 취소/late commit은 V장에서 상세화한다.

---

# 35. Runtime 대표 시나리오 D — Overload

```text
Worker 20개 실행
   +
Queue 100개 대기
   ↓
추가 요청
   ↓
Task Reject
   ↓
OnlineOverloadException
   ↓
HTTP 503
   ↓
FW_OVERLOADED
```

이 값은 현재 Snapshot의 구현값이며 Capacity Target이 아니다.

---

# 36. Runtime 대표 시나리오 E — Filter 인증 실패

```text
Browser
   ↓
DefaultFilter
   ↓
Authorization 없음 / Invalid
   ↓
sendError(401)
   ↓
Return
```

실행되지 않는 것:

```text
DispatcherServlet
Interceptor
Controller
TCF
Handler
Business
DB
```

따라서 Filter 오류는 Application Business 장애와 분리해 관측해야 한다.

---

# 37. Runtime 대표 시나리오 F — 일반 DB 예외

```text
DAO / Mapper
   ↓
SQL Exception
   ↓
Facade/Worker TX rollback
   ↓
Exception Propagation
   ↓
Global Handler?
   │
   ├─ 명시 Type이면 표준 result
   └─ 일반 Exception이면 Spring default 가능
```

현재 일반 `Exception.class` Handler 미확보는 표준 오류계약의 GAP다.

---

# 38. Runtime Responsibility Matrix

| 구성요소 | 입력 | 주요 책임 | 출력 | 금지 |
|---|---|---|---|---|
| DefaultFilter | Raw HTTP | Body/JWT/Header/Context | Wrapped Request | 업무 SQL |
| SecurityFilterChain | HTTP | Security/CORS | Security Context | 업무 규칙 |
| Interceptor | Context/MVC | GUID/ImageLog | Controller 진입 | 업무 계산 |
| OnlineController | Map/Path | ServiceId/dto | TCF 호출 | DAO |
| TransactionContext | ServiceId/Context | TCF 실행정보 | Handler Context | DB TX |
| TcfFacade | ID/dto | Context/Executor 연결 | 결과 | Business Facade 역할 |
| TimeoutExecutor | Callable | Thread/Timeout/TX 바깥 제어 | result/exception | ServiceId 분기 |
| Dispatcher | ID/dto/context | Handler lookup | Handler result | SQL |
| Handler | dto/context | 거래 분기/Facade 위임 | result | DAO |
| Business Facade | dto | Use Case/TX 참여 | result | Servlet |
| BizAspect | Service Join Point | 업무 선후 로그 | proceed | TX 자체 |
| Service | typed DTO | 업무 절차 | DTO/result | HTTP |
| DAO | params | SQL 호출 | row/result | 업무 UI |
| Resolver/Advice | return/exception | Envelope | HTTP body | DB |
| afterCompletion | response/ex | ImageLog | finalize | 원예외 재정의 |
| Filter finally | ThreadLocal | Context/MDC clear | request end | 업무 후속 |

---

# 39. Figure-IV-15 — Failure / Risk Map

```text
[HTTP / Filter]
JWT/JSON/Header 오류
   └─ sendError
      └─ 표준 Envelope 우회 Risk

[MVC]
Binding / Mapping 오류
   └─ Handler별 처리 차이

[TCF]
ServiceId 불일치
Handler 미등록
   └─ E9999

[Executor]
Queue Full
   └─ 503

[Timeout]
Future timeout
   └─ 504
      └─ Worker 계속 가능 Risk

[Business]
BizException
   └─ message dictionary 미연결 Risk

[DB]
Runtime/SQL Exception
   └─ 포괄 Handler 부재 Risk

[Advice]
Global vs Legacy Advice 중첩
   └─ 응답 Drift Risk

[Post]
afterCompletion rethrow
   └─ 중복 오류 Risk

[Context]
ThreadLocal clear 누락
   └─ 다음 요청 GUID/User 오염 Risk
```

---

# 40. 현재 Runtime GAP

| ID | GAP | 영향 |
|---|---|---|
| GAP-IV-01 | TCF OFF 일부 Controller가 Facade 우회 | ON/OFF 업무/TX Drift |
| GAP-IV-02 | STF/ETF Bean은 있으나 실제 TcfFacade 호출선 미연결 | 거래통제/후처리 정책 미적용 가능 |
| GAP-IV-03 | Filter 오류가 표준 `result` Envelope 미보장 | Client 오류 계약 불일치 |
| GAP-IV-04 | 일반 `Exception.class` Handler 미확인 | 시스템 오류 표준화 미보장 |
| GAP-IV-05 | Advice 우선순위 명시 부족 | 동일 예외 응답 Drift |
| GAP-IV-06 | Path/Header ServiceId 불일치 검증 없음 | 다른 거래 실행 Risk |
| GAP-IV-07 | BizException 메시지 사전 연결 미흡 | 사용자 오류메시지 품질 |
| GAP-IV-08 | TCF OFF에는 OnlineTimeoutExecutor 적용 안 됨 | ON/OFF SLA 차이 |
| GAP-IV-09 | Filter early error와 Security CORS Header 결합 Risk | Browser 오류 응답 노출 |
| GAP-IV-10 | STF 거래통제와 Interceptor 선처리 명칭 혼동 | 문서/운영 오해 |

---

# 41. Current Runtime RISK

| ID | Risk | 심각도 후보 |
|---|---|---|
| RISK-IV-01 | Path/Header ServiceId 불일치 | High |
| RISK-IV-02 | 일반 Runtime/DB 예외가 표준 result로 가지 않을 수 있음 | High |
| RISK-IV-03 | ControllerAdvice 중첩 | High |
| RISK-IV-04 | afterCompletion 예외 재던지기 | Medium/High |
| RISK-IV-05 | Filter sendError의 표준 전문/CORS 불일치 | High |
| RISK-IV-06 | Context/MDC clear 누락 시 Thread 재사용 오염 | Critical |
| RISK-IV-07 | Worker가 동일 ServiceContext 참조 사용 | Medium/High |
| RISK-IV-08 | TCF OFF Timeout 부재 | High |
| RISK-IV-09 | STF/ETF 미연결을 적용 중으로 오해 | High |
| RISK-IV-10 | 업무 예외 HTTP Status 정책 미확정 | Medium |
| RISK-IV-11 | 현재 5000ms/20/100을 운영 NFR로 오해 | Medium |

---

# 42. OPEN Issue

| ID | 질문 |
|---|---|
| OPEN-IV-01 | Dispatcher의 중복 ServiceId 등록은 현재 정확히 어떤 시점/예외로 차단되는가 |
| OPEN-IV-02 | Path/Header ServiceId 불일치를 반드시 거절할 것인가 |
| OPEN-IV-03 | Filter 오류도 표준 Envelope로 통일할 것인가 |
| OPEN-IV-04 | GlobalExceptionHandler와 Legacy Advice의 공식 우선순위는 무엇인가 |
| OPEN-IV-05 | STF/ETF를 Target Runtime에 실제 연결할 것인가 |
| OPEN-IV-06 | 거래통제를 Filter/Interceptor/STF 중 어디에 둘 것인가 |
| OPEN-IV-07 | TCF OFF를 운영에서 허용할 것인가 |
| OPEN-IV-08 | ON/OFF Timeout/Error 계약을 동일하게 만들 것인가 |
| OPEN-IV-09 | 업무 오류 HTTP Status 정책은 500인가 4xx/200 Business Result인가 |
| OPEN-IV-10 | SecurityFilterChain의 최종 Authorization 역할은 VII장에서 어떻게 정리할 것인가 |

---

# 43. ADR 후보

| ADR | 결정 주제 |
|---|---|
| ADR-IV-01 | ServiceId Single Source of Truth: Header vs Path |
| ADR-IV-02 | STF/ETF Runtime 연결 여부 |
| ADR-IV-03 | Filter Error Standard Writer 도입 |
| ADR-IV-04 | Exception Handler 단일화/우선순위 |
| ADR-IV-05 | TCF OFF 운영 허용 여부 |
| ADR-IV-06 | ON/OFF 공통 Facade 강제 |
| ADR-IV-07 | ON/OFF 공통 Timeout 정책 |
| ADR-IV-08 | Business Error HTTP Status 정책 |
| ADR-IV-09 | afterCompletion 예외 재던지기 제거 |
| ADR-IV-10 | Worker Context를 immutable snapshot으로 바꿀지 |

---

# 44. Runtime Architecture Rules

## 44.1 Must

1. Filter에서 업무 SQL을 실행하지 않는다.
2. Controller에서 업무 로직/DAO를 직접 실행하지 않는다.
3. Handler는 DAO를 직접 호출하지 않는다.
4. ServiceId Routing과 Business Rule을 혼합하지 않는다.
5. `ServiceContext`는 Request 완료 시 반드시 제거한다.
6. Worker Context/MDC는 Worker 종료 시 반드시 정리한다.
7. TCF ON/OFF를 같은 Runtime으로 설명하지 않는다.
8. STF/ETF를 Source 호출선 확인 없이 AS-IS 실행단계로 그리지 않는다.
9. 성공과 실패 Envelope를 현재 Source대로 구분한다.
10. Filter `sendError`를 표준 Business Error와 같은 경로라고 설명하지 않는다.
11. Timeout 값 5000ms를 NSIGHT Target SLA라고 쓰지 않는다.
12. `TransactionContext`를 DB Transaction이라고 설명하지 않는다.

## 44.2 Should

1. Path/Header ServiceId 일치 검증을 도입한다.
2. Filter 오류도 표준 JSON Envelope를 사용하도록 정렬한다.
3. Advice 책임을 Exception Mapping과 Response Wrapping으로 분리한다.
4. TCF OFF도 Facade 이하 Business Core를 공유한다.
5. 일반 시스템 예외에 대한 표준 Handler를 둔다.
6. Runtime Diagram과 Source Call Graph를 자동 대조한다.

---

# 45. TCF ON/OFF 비교 Matrix

| 항목 | ON | OFF |
|---|---|---|
| Filter | 유지 | 유지 가능 |
| Security | 유지 | 유지 |
| Interceptor | 유지 | 유지 가능 |
| 공통 Online Controller | 사용 | 미사용 |
| TcfFacade | 사용 | 미사용 |
| TimeoutExecutor | 요청 적용 | 요청 미적용 |
| Dispatcher | 사용 | 미사용 |
| Handler Registry | 사용 | 미사용 |
| Business Controller | 미사용 | 사용 |
| Facade | Handler 경유 | 일부 Controller만 |
| Service | 사용 | 사용 |
| DAO | 사용 | 사용 |
| STF/ETF Bean | 생성 가능, 현재 미연결 | 미생성 |
| TCF GlobalExceptionHandler | 사용 | 미생성 |
| ResponseBodyAdvice | 유지 | 유지 가능 |
| BizPrePostAspect | 유지 | 유지 가능 |
| Worker TX | Timeout ON 시 있음 | 없음 |
| TX 시작 | Worker 외곽 + Facade 참여 | Facade/Service Annotation에 따라 |
| Timeout 5000ms | 적용 | TCF Executor 기준 미적용 |

---

# 46. Logging / Trace Runtime 위치

```text
DefaultFilter
  ├─ MDC serviceId
  ├─ guid
  ├─ ip
  └─ userId
      │
      ▼
Interceptor
  ├─ GUID 보완
  └─ Pre ImageLog
      │
      ▼
Worker
  ├─ MDC install
  └─ Business Log
      │
      ▼
ResponseBodyAdvice
  └─ responseBody
      │
      ▼
afterCompletion
  └─ Post/Exception ImageLog
      │
      ▼
Filter finally
  └─ MDC clear
```

VI장에서 다음을 상세화한다.

```text
GUID
ServiceId
Request/Response Image
Business Log
Error Code
Audit
Masking
```

---

# 47. Security Runtime 위치 Preview

본 장에서 Security 위치만 고정한다.

```text
Browser
   ↓
DefaultFilter
   ├─ non-local Bearer JWT validate
   └─ ssoId request attribute
   ↓
SecurityFilterChain
   ↓
MVC/TCF
```

다음은 VII장으로 넘긴다.

```text
JWT 발급주체
Access/Refresh
RS256/JWKS
Gateway 검증
Header 사용자와 JWT Subject 연결
권한
Logout/Revocation
Session
```

---

# 48. Transaction Runtime 위치 Preview

본 장에서는 DB Transaction의 위치만 표시한다.

```text
Request Thread
   ↓
TcfFacade
   ↓
TimeoutExecutor
   │
   └─ Worker
       ↓
     TransactionTemplate BEGIN
       ↓
     Dispatcher
       ↓
     Handler
       ↓
     Facade @Transactional(REQUIRED)
       ↓
     Service
       ↓
     DAO
       ↓
     Deadline Check
       ↓
     COMMIT / ROLLBACK
```

V장에서 다음을 분해한다.

```text
TransactionTemplate와 Facade Proxy 관계
Propagation
Rollback
Deadline
Interrupt
Connection
Query Timeout
Late Commit
```

---

# 49. Traceability

## 49.1 Runtime → Source Role

| Runtime | Source/Package Role |
|---|---|
| DefaultFilter | `nhnis.fw.commons.filter` |
| ServiceContext | `nhnis.fw.commons.context` |
| Interceptor | `nhnis.fw.commons.interceptor` |
| OnlineTransactionController | `nhnis.fw.tcf` |
| TcfFacade | `nhnis.fw.tcf` |
| OnlineTimeoutExecutor | `nhnis.fw.tcf.timeout` |
| TransactionDispatcher | `nhnis.fw.tcf` |
| TransactionHandler | FW Interface + 업무 구현 |
| Business Facade | `nhnis.mg.co.a.application.facade` |
| BizPrePostAspect | 업무/공통 AOP |
| Service | `nhnis.mg.co.a.application.service` |
| DAO | `nhnis.mg.co.a.persistence.dao` |
| Mapper XML | `rdw.mg.co.a` |
| ResponseBodyArgumentResolver | `nhnis.fw.commons.resolver` |
| GlobalExceptionHandler | `nhnis.fw.exception` |
| ImageLogHandler | `nhnis.fw.commons.imagelog` |

## 49.2 III → IV → V

```text
III
누가 존재하는가
Module / Package / Component
      ↓
IV
누가 먼저 실행되는가
Runtime / Sequence
      ↓
V
어느 Thread와 Transaction인가
Timeout / TX / DB
```

---

# 50. 검증 체크리스트

## 50.1 전체 Runtime

- [x] Filter→Security→MVC→TCF→Business 순서가 표현되는가
- [x] Request Thread와 Worker Thread가 구분되는가
- [x] DB TX가 HTTP 전체를 감싸는 것으로 그리지 않았는가
- [x] Response 후처리가 Request Thread로 돌아오는가

## 50.2 Filter / Context

- [x] Body Cache가 존재하는가
- [x] local/non-local JWT 차이가 있는가
- [x] ServiceContext 생성·보강·정리가 있는가
- [x] MDC clear가 있는가
- [x] Filter 오류가 Advice를 우회함을 표시했는가

## 50.3 TCF

- [x] ServiceId 우선순위가 표시되는가
- [x] Controller가 dto만 넘기는가
- [x] TransactionContext가 DB TX가 아니라고 설명했는가
- [x] Dispatcher→Handler Registry 구조가 있는가
- [x] Handler→Facade가 유지되는가
- [x] STF/ETF 미연결이 표시되는가

## 50.4 Business

- [x] Facade→Service→DAO 구조가 유지되는가
- [x] BizPrePostAspect 위치가 있는가
- [x] Handler→DAO를 정상패턴으로 쓰지 않았는가

## 50.5 Response / Error

- [x] 성공=`hdr_nhnis+dto`인가
- [x] 실패=`hdr_nhnis+result`인가
- [x] Filter sendError가 별도 경로인가
- [x] Global/Legacy Advice 중첩 Risk가 있는가
- [x] 일반 Exception Handler GAP가 있는가

## 50.6 ON/OFF

- [x] OFF가 Framework 전체 OFF로 쓰이지 않았는가
- [x] OFF에서 TCF Core가 사라지는가
- [x] OFF에서 Filter/Interceptor는 남을 수 있는가
- [x] OFF에 TCF Timeout이 적용되지 않음을 표시했는가
- [x] OFF Facade 우회 GAP가 있는가

---

# 51. FIG-IV-16 — V장 Handoff

```text
IV. PDMG Online Runtime
      │
      ├─ Request Thread
      ├─ DefaultFilter
      ├─ Context/MDC
      ├─ TCF
      ├─ TimeoutExecutor
      ├─ Worker
      ├─ Dispatcher
      ├─ Handler
      ├─ Business
      ├─ Response
      └─ Error
              │
              ▼
V. Transaction / Timeout / Thread / DB Architecture
              │
              ├─ Request Thread vs Worker
              ├─ Executor Pool / Queue
              ├─ Context Copy
              ├─ TransactionTemplate
              ├─ @Transactional(REQUIRED)
              ├─ Commit / Rollback
              ├─ Timeout / Deadline
              ├─ Future.cancel(true)
              ├─ Interrupt
              ├─ JDBC / Query Timeout
              ├─ Hikari Connection
              └─ Late Commit Prevention
```

## V장에서 반드시 답할 질문

1. Worker TransactionTemplate는 정확히 언제 BEGIN 하는가?
2. Handler가 DB Transaction 안에 포함되는가?
3. Facade `@Transactional(REQUIRED)`는 Worker TX에 참여하는가?
4. Timeout OFF에서는 Transaction 시작점이 어디인가?
5. TCF OFF에서는 Transaction 시작점이 거래마다 왜 달라지는가?
6. ServiceContext/MDC는 Worker에 어떻게 전달되는가?
7. Future timeout 후 Worker는 언제 실제 종료되는가?
8. `cancel(true)`와 JDBC Query 취소는 같은가?
9. Deadline 초과 뒤 Commit을 어떻게 막는가?
10. Connection은 어느 Thread에서 획득되고 반환되는가?
11. DB Query Timeout / Transaction Timeout / Server Timeout의 예산은 어떻게 계층화되는가?
12. Overload 20+100 구조는 Capacity/NFR 관점에서 적절한가?

---

# 52. Completion Gate

```text
Figure Plan                    16
실제 Text Figure              16

전체 Runtime Journey          PASS
Servlet/Security/MVC/TCF      PASS
DefaultFilter                 PASS
ServiceContext Lifecycle      PASS
Interceptor Pre/Post          PASS
OnlineController              PASS
TcfFacade/TransactionContext  PASS
Dispatcher/Handler Registry   PASS
Business Runtime              PASS
Pre/Post 4구간                PASS
Success Response              PASS
Error Response                PASS
TCF ON/OFF                    PASS
STF/ETF Actual vs Intended    PASS
Failure/Risk                  PASS
V장 Handoff                   PASS

Source 없는 STF 실행          0건
Handler→DAO 정상패턴          0건
Controller 업무로직 정상화    0건
5000ms를 Target SLA로 승격    0건
```

**판정: CONDITIONAL PASS**

## PASS 전환 조건

```text
Condition-IV-01
현재 Source Snapshot 기준
Filter Order / Security Chain / CORS를 Runtime Test로 재확인

Condition-IV-02
ServiceId Header/Path 정합 정책 결정

Condition-IV-03
STF/ETF Target 연결 여부 결정

Condition-IV-04
GlobalExceptionHandler / Legacy Advice 우선순위 정리

Condition-IV-05
Filter 오류 표준 Envelope 정책 결정

Condition-IV-06
TCF OFF 운영 허용 여부 결정

Condition-IV-07
ON/OFF 공통 Facade 경계 확정

Condition-IV-08
V장에서 Timeout/Transaction/Interrupt Runtime 증적 완료
```

---

# 53. 장 최종 평가

IV장은 PDMG Source 구조를 실제 **온라인 요청 1건의 실행시간 순서**로 변환했다.

가장 중요한 결론은 다음이다.

> **PDMG 온라인 거래는 Filter → MVC → TCF → Business → Persistence → Response의 다중 경계를 가진다.**

> **TCF ON + Timeout ON에서 TcfFacade까지는 Request Thread이고, Dispatcher 이하 업무는 Worker Thread와 TransactionTemplate 안에서 실행된다.**

> **ServiceContext와 MDC는 ThreadLocal이므로 Worker에 명시적으로 전달·정리되어야 한다.**

> **OnlineTransactionController는 업무를 수행하는 Controller가 아니라 ServiceId와 dto를 TCF 계약으로 바꾸는 Inbound Adapter다.**

> **Dispatcher는 ServiceId Registry를 통해 Handler를 선택하고 Handler는 Business Facade에 위임한다.**

> **현재 STF/ETF는 구현 또는 Bean 존재와 실제 Runtime 연결이 일치하지 않으므로 “시스템 선후처리가 TCF에서 자동 적용된다”고 쓸 수 없다.**

> **정상 응답은 `hdr_nhnis + dto`, 알려진 실패는 `hdr_nhnis + result`이지만 Filter 오류와 일부 일반 시스템 예외는 이 계약을 우회할 수 있다.**

> **TCF OFF는 다른 Entry Adapter이며 일부 현행 Controller가 Facade를 우회하므로 ON/OFF의 업무·Transaction 계약이 아직 완전히 동일하지 않다.**

다음 V장에서는 이 Runtime을 다시 Thread와 DB Transaction 관점으로 확대한다.

```text
IV
HTTP / Runtime Sequence
        ↓
V
Thread / Timeout / Transaction / JDBC / Commit-Rollback
```

IV장에서 “누가 언제 실행되는가”를 고정했으므로, V장에서는 “어느 Thread와 Transaction 안에서 실행되는가”를 Source와 설정값으로 증명한다.
