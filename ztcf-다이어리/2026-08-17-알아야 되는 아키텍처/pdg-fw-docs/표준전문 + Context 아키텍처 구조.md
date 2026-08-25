# 7. 표준전문 + Context 아키텍처 구조

현재 **PDMG 실제 구조**에서 `표준전문 + Context`는 단순히 JSON 형식을 정하는 기능이 아닙니다.

핵심은 다음처럼 이해하는 것이 가장 정확합니다.

```text
표준전문
= 시스템 밖에서 거래정보를 전달하는 표준 Envelope

ServiceContext
= HTTP 요청 전체에서 공통정보를 공유하는 실행 문맥

TransactionContext
= TCF 실행 구간에서 사용하는 거래 실행 문맥

업무 DTO
= 실제 업무 데이터
```

즉 전체 원칙은:

```text
         외부 표준전문
              │
      ┌───────┴────────┐
      │                │
      ▼                ▼
 hdr_nhnis            dto
 시스템 공통정보       업무정보
      │                │
      ▼                ▼
ServiceContext      Business DTO
      │                │
      │                ▼
      │             Handler
      │                ↓
      │             Facade
      │                ↓
      └─────────────► Service
```

입니다. 실제 PDMG 요청 전문도 `hdr_nhnis.sys_comm + dto`의 두 영역으로 구성되고, Controller 이후 업무 계층에는 전체 전문이 아니라 `dto`가 전달됩니다. 공통 헤더는 `ServiceContext`를 통해 접근합니다.

---

## 1. 전체 아키텍처

```text
┌──────────────────────────────────────────────────────────────┐
│                       pdmg-ui / Client                       │
│                                                              │
│  HTTP POST                                                   │
│                                                              │
│  ┌────────────────────────────────────────────────────────┐  │
│  │ 표준 요청 전문                                         │  │
│  │                                                        │  │
│  │ hdr_nhnis                                              │  │
│  │   └─ sys_comm                                          │  │
│  │       ├─ std_gbl_id     GUID                           │  │
│  │       ├─ rms_svc_c      ServiceId                      │  │
│  │       ├─ scid           화면/프로그램                  │  │
│  │       ├─ tr_sysid       시스템                         │  │
│  │       ├─ tr_brc         점코드                         │  │
│  │       ├─ tr_trm_ipadr   단말 IP                        │  │
│  │       └─ optr_eno       사용자                         │  │
│  │                                                        │  │
│  │ dto                                                    │  │
│  │   └─ 업무별 입력정보                                   │  │
│  └────────────────────────────────────────────────────────┘  │
└──────────────────────────────┬───────────────────────────────┘
                               │
                               ▼
┌──────────────────────────────────────────────────────────────┐
│                    DefaultFilter / pdmg-fw                   │
│                                                              │
│  JSON Body Cache                                             │
│       │                                                      │
│       ├──── hdr_nhnis ─────────────────────────┐             │
│       │                                        │             │
│       │                                        ▼             │
│       │                               ServiceContext 생성    │
│       │                                        │             │
│       │                               ServiceContextHolder   │
│       │                                  ThreadLocal         │
│       │                                                      │
│       └──── dto ───────────────────────────────────────┐     │
└──────────────────────────────┬─────────────────────────┼─────┘
                               │                         │
                               ▼                         │
                    Interceptor                          │
             GUID/Header/User/IP 보강                   │
             MDC / 전문로그 / ImageLog                  │
                               │                         │
                               ▼                         │
                 OnlineTransactionController            │
                               │                         │
                               ├─ ServiceId 결정         │
                               │                         │
                               └─ dto 추출 ◀─────────────┘
                               │
                               ▼
                       TcfFacade.process
                               │
                               ▼
                    TransactionContext 생성
                               │
                               ├─ ServiceId
                               ├─ 시작시간
                               └─ ServiceContext 참조
                               │
                               ▼
                             STF
                               │
                               ▼
                       Timeout Executor
                               │
                       Thread 전환 가능
                               ▼
                          Dispatcher
                               │
                        ServiceId Lookup
                               ▼
                            Handler
                               │
                               ▼
                            Facade
                               │
                               ▼
                            Service
                               │
                     ┌─────────┴─────────┐
                     ▼                   ▼
                    Rule                DAO
                                         │
                                         ▼
                                      Mapper
                                         │
                                         ▼
                                         DB
                               │
                               ▼
                       ResponseBodyAdvice
                               │
                 ┌─────────────┴──────────────┐
                 │                            │
               정상                         오류
                 │                            │
                 ▼                            ▼
       hdr_nhnis + dto              hdr_nhnis + result
                 │                            │
                 └─────────────┬──────────────┘
                               ▼
                         pdmg-ui / Client
```

---

## 2. 표준전문은 `Header + DTO` 구조입니다

현재 실제 요청 전문은 개념적으로 다음 형태입니다.

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "rms_svc_c": "mgcoa8888S0",
      "std_gbl_id": "GUID-...",
      "tr_sysid": "PDMG",
      "tr_trm_ipadr": "127.0.0.1",
      "tr_brc": "10001",
      "scid": "mgcoa8888",
      "optr_eno": "E0000001"
    }
  },
  "dto": {
    "pageNo": 1,
    "pageSize": 20
  }
}
```

책임은 명확하게 나뉩니다.

| 영역        | 소유           | 역할                         |
| ----------- | -------------- | ---------------------------- |
| `hdr_nhnis` | `pdmg-fw` 중심 | 시스템 공통 헤더             |
| `sys_comm`  | `pdmg-fw` 중심 | 거래·사용자·점·단말·추적정보 |
| `dto`       | `pdmg-service` | 서비스별 업무 입력/출력      |
| `result`    | `pdmg-fw`      | 오류 표준정보                |

따라서 **업무 개발자가 표준전문 전체를 직접 들고 다니는 구조가 아닙니다.**

---

# 3. 왜 전문을 두 갈래로 분리하는가

예를 들어 고객조회 요청이라면 다음 두 정보는 성격이 완전히 다릅니다.

```text
[공통 정보]

GUID
ServiceId
사용자
점코드
단말 IP
시스템 ID
화면 ID

        ≠

[업무 정보]

고객번호
조회기간
조회조건
pageNo
pageSize
```

그래서 다음 구조를 만듭니다.

```text
                 Request

                   │
          ┌────────┴────────┐
          │                 │
          ▼                 ▼

     hdr_nhnis             dto
          │                 │
          ▼                 ▼
   ServiceContext       *DTOin
          │                 │
          │                 ▼
          │              Facade
          │                 ▼
          │              Service
          │
          └─ Framework 공통 처리
```

이 분리가 매우 중요합니다.

---

# 4. `ServiceContext`란 무엇인가

`ServiceContext`는 **HTTP 요청 한 건의 공통 실행정보 저장소**입니다.

현재 문서 기준으로 다음 정보를 관리합니다.

```text
ServiceContext

├─ hdr_nhnis
│   └─ sys_comm
│
├─ GUID
│
├─ HTTP Request
├─ HTTP Response
│
├─ Request Body 원문
├─ Response Body 원문
│
├─ Application / Profile
│
└─ 사용자 확장 Context
```

그리고:

```text
ServiceContextHolder
        │
        ▼
ThreadLocal
```

방식으로 현재 Request Thread에서 접근합니다.

쉽게 표현하면:

> **“현재 이 HTTP 요청이 누구의 어떤 거래인지 Framework 어디에서든 확인할 수 있도록 만든 요청 단위 가방”**

입니다.

---

# 5. ServiceContext가 해서는 안 되는 것

이 구분은 매우 중요합니다.

`ServiceContext`는 다음을 관리하지 않습니다.

```text
ServiceContext

DB Transaction              X
JDBC Connection             X
SqlSession                  X
업무 DTO 저장소             X
업무 상태 저장              X
업무 Rule                   X
```

즉:

```text
ServiceContext ≠ TransactionManager

ServiceContextHolder ≠ DB Transaction

Context ≠ 업무 DTO
```

입니다.

---

# 6. `TransactionContext`는 또 무엇인가

이름 때문에 상당히 헷갈리기 쉬운데 `TransactionContext`도 **DB Transaction 자체가 아닙니다.**

현재 구조는 다음과 같습니다.

```text
TcfFacade.process(serviceId)
        │
        ▼
TransactionContext
        │
        ├─ serviceId
        ├─ startedAtNanos
        └─ ServiceContext 참조
```

개념적인 생성은:

```java
new TransactionContext(
    serviceId,
    ServiceContextHolder.getInstance()
);
```

과 같은 형태입니다.

따라서:

```text
TransactionContext
        ≠
Spring Transaction

TransactionContext
        ≠
TransactionTemplate
```

입니다.

---

## 7. 두 Context를 비교하면

| 구분            | `ServiceContext`         | `TransactionContext`       |
| --------------- | ------------------------ | -------------------------- |
| 범위            | HTTP 요청 전체           | TCF 실행 구간              |
| 생성            | Filter                   | TcfFacade                  |
| 전달            | ThreadLocal              | 메서드 인자                |
| 핵심정보        | Header, GUID, HTTP, 전문 | ServiceId, 시작시간        |
| 표준전문 Header | 직접 보유                | ServiceContext를 통해 접근 |
| 업무 DTO        | 보유하지 않는 것이 원칙  | 보유하지 않음              |
| DB TX           | 관리 안 함               | 관리 안 함                 |
| Timeout 계산    | 정보 제공                | 시작시간 활용 가능         |

이 차이는 실제 Context 분석자료에도 명확하게 구분되어 있습니다.

---

# 8. 표준전문이 ServiceContext로 변하는 과정

실제 처리 순서는 다음과 같습니다.

```text
Client JSON

{
  hdr_nhnis,
  dto
}
       │
       ▼
DefaultFilter
       │
       ├─ Request Body 캐싱
       │
       ├─ hdr_nhnis Parsing
       │
       ├─ Header DTO 생성
       │
       ├─ GUID 준비
       │
       └─ ServiceContext 생성
              │
              ▼
      ServiceContextHolder
              │
              ▼
          ThreadLocal
```

그리고 Interceptor가 Context를 다시 보강합니다.

```text
ServiceContext
     │
     ▼
Interceptor

├─ GUID 보장
├─ rms_svc_c 보강
├─ scid 보강
├─ IP 보강
├─ 사용자 보강
├─ MDC 설정
├─ 전문로그
└─ ImageLog
```

현재 Interceptor는 `sys_comm`의 GUID, ServiceId, 화면/프로그램 ID, IP, 조작자 정보 등을 보강하고 전문 로그/ImageLog에도 사용합니다.

---

# 9. Controller에서는 Header와 DTO가 갈라집니다

이 지점이 아키텍처적으로 매우 중요합니다.

Controller에서 실제로는:

```java
Object dtoBody =
    request == null
        ? null
        : request.get("dto");

return tcfFacade.process(
    serviceId,
    dtoBody
);
```

형태로 `dto`만 업무 실행 쪽으로 내려보냅니다.

즉:

```text
전체 JSON
   │
   ├──────── hdr_nhnis
   │               │
   │               ▼
   │          ServiceContext
   │
   └──────── dto
                   │
                   ▼
                Handler
                   ↓
                Facade
                   ↓
                Service
```

입니다.

**Header와 업무 데이터의 생명주기를 분리한 구조**라고 볼 수 있습니다.

---

# 10. ServiceId는 Header와 Context를 관통합니다

특히 `rms_svc_c`가 중요합니다.

```text
hdr_nhnis
   │
   └─ sys_comm
        │
        └─ rms_svc_c
              │
              ▼
          ServiceContext
              │
              ▼
      OnlineTransactionController
              │
              ▼
       TransactionContext
              │
              ▼
          Dispatcher
              │
              ▼
           Handler
```

즉 표준전문의 ServiceId가 최종적으로 **업무 실행 경로를 결정하는 Architecture Key**가 됩니다.

---

# 11. GUID는 Context 전체를 연결합니다

GUID도 같은 방식입니다.

```text
Client
  │
  │ std_gbl_id
  ▼
hdr_nhnis
  │
  ▼
ServiceContext.guid
  │
  ├─ MDC.guid
  ├─ Request Log
  ├─ ImageLog
  ├─ Error Log
  ├─ Response Header
  └─ 운영 추적
```

현재 기준에서는 다음 세 값이 동일해야 정상적인 거래 추적이 가능합니다.

```text
ServiceContext.guid

        =

hdr_nhnis.sys_comm.std_gbl_id

        =

MDC["guid"]
```

GUID가 Context/Header/MDC에서 일치해야 요청 로그와 ImageLog를 같은 거래로 연결할 수 있습니다.

---

# 12. Timeout ON이면 Context에 Thread 문제가 생깁니다

여기에서 중요한 문제가 하나 발생합니다.

`ServiceContextHolder`는 `ThreadLocal`입니다.

따라서 기본 Java 동작은:

```text
Request Thread

ServiceContext = 존재
      │
      │ Worker submit
      ▼
Worker Thread

ServiceContext = 없음
```

입니다.

ThreadLocal은 다른 Thread로 자동 전달되지 않기 때문입니다.

---

# 13. 그래서 `OnlineTimeoutWorkerContext`가 존재합니다

현재 PDMG는 Worker 실행 전에 Context를 캡처합니다.

```text
Request Thread

ServiceContext
MDC
GUID
ServiceId
      │
      ▼
OnlineTimeoutWorkerContext.capture()
      │
      │ submit
      ▼
Worker Thread
      │
      ▼
workerContext.install()
      │
      ├─ ServiceContextHolder.setInstance()
      └─ MDC 복원
```

그래서 Worker 쪽의:

```text
Handler
Facade
BizPrePostAspect
Service
```

에서도 GUID/ServiceId 등을 조회할 수 있습니다.

---

# 14. Worker 종료 후 Context를 반드시 지워야 합니다

Thread Pool은 Thread를 재사용합니다.

따라서 다음과 같이 해야 합니다.

```text
Worker #1

사용자 A Context
     │
     ▼
업무 완료
     │
     ▼
clear()
     │
     ▼
Thread 재사용
     │
     ▼
사용자 B Context
```

정리가 안 되면:

```text
사용자 A Context
      │
      X 제거 안 됨
      │
      ▼
같은 Worker Thread
      │
      ▼
사용자 B 거래에서
사용자 A 정보 조회 가능
```

이라는 심각한 문제가 생깁니다.

따라서 현재 구현에서도 `finally`에서 Context와 MDC를 제거하는 흐름을 둡니다.

---

# 15. 전체 Context 생명주기

한 장으로 정리하면 다음과 같습니다.

```text
                     HTTP Request Thread

Client
  │
  ▼
DefaultFilter
  │
  ├─ JSON 읽기
  ├─ hdr_nhnis 추출
  ├─ GUID
  ├─ Request Body
  │
  ▼
ServiceContext 생성
  │
  ▼
ServiceContextHolder.set()
  │
  ▼
Interceptor
  │
  ├─ Header 보강
  ├─ GUID 동기화
  ├─ MDC
  └─ ImageLog PRE
  │
  ▼
Controller
  │
  ├─ ServiceId
  └─ dto 분리
  │
  ▼
TcfFacade
  │
  ▼
TransactionContext 생성
  │
  ├─ ServiceId
  ├─ Started Time
  └─ ServiceContext
  │
  ▼
STF
  │
  ▼
TimeoutExecutor
  │
  ├──── Context Capture
  │
  ▼
──────────────── THREAD BOUNDARY ────────────────
  │
  ▼
                     Worker Thread

OnlineTimeoutWorkerContext.install()
  │
  ▼
TransactionTemplate
  │
  ▼
Dispatcher
  │
  ▼
Handler
  │
  ▼
Facade
  │
  ▼
Service
  │
  ▼
DAO / Mapper / DB
  │
  ▼
WorkerContext.clear()

──────────────── THREAD BOUNDARY ────────────────
  │
  ▼
                     Request Thread

ETF
  │
  ▼
ResponseBodyAdvice
  │
  ├─ Header
  └─ DTO / Result
  │
  ▼
ServiceContext.responseBody
  │
  ▼
ImageLog POST
  │
  ▼
ServiceContextHolder.remove()
  │
  ▼
Client
```

이 전체 생명주기는 현재 ServiceContext 자료에서도 `Filter → Interceptor → Controller/TCF → Worker → Advice → afterCompletion → Filter finally`로 정리되어 있습니다.

---

# 16. 정상 응답은 `hdr_nhnis + dto`

업무 Service는 결과 DTO만 반환합니다.

```text
Service
   │
   ▼
*DTOout
   │
   ▼
ResponseBodyAdvice
   │
   ├─ ServiceContext.header
   └─ 업무 DTO
   │
   ▼
```

결과:

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "std_gbl_id": "...",
      "rms_svc_c": "mgcoa8888S0"
    }
  },
  "dto": {
    "data": "..."
  }
}
```

현재 구현에서 응답 Header는 단순 요청 원문의 복사가 아니라 **ServiceContext에서 처리 도중 보강된 Header 객체**를 사용합니다.

---

# 17. 오류 응답은 `hdr_nhnis + result`

Controller/TCF 이후의 표준 오류는 다음 구조입니다.

```text
Exception
    │
    ▼
GlobalExceptionHandler
    │
    ▼
NH_NIS_ERR_DTO
    │
    ▼
ResponseBodyAdvice
    │
    ▼
```

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "std_gbl_id": "...",
      "rms_svc_c": "mgcoa9001S0"
    }
  },
  "result": {
    "stdErrCode": "MP0404",
    "stdErrMsgCntn": "요청한 데이터를 찾을 수 없습니다.",
    "errType": "BIZ"
  }
}
```

따라서 현재 표준은:

```text
정상
hdr_nhnis + dto

오류
hdr_nhnis + result
```

로 이해하는 것이 정확합니다.

---

# 18. 계층별 Context 사용 원칙

제가 이 구조에서 가장 중요하게 보는 개발 원칙은 다음입니다.

| 계층        |     ServiceContext |        업무 DTO |
| ----------- | -----------------: | --------------: |
| Filter      |          적극 사용 |     원문만 처리 |
| Interceptor |          적극 사용 |  업무 처리 금지 |
| TCF         |               사용 |       전달 가능 |
| STF         |               사용 |       필요 최소 |
| Dispatcher  |     ServiceId 중심 |            전달 |
| Handler     |       Context 가능 |            사용 |
| Facade      |          최소 사용 |     **주 입력** |
| Service     |        가급적 최소 |     **주 입력** |
| Rule        |          사용 지양 |     **주 입력** |
| DAO         | **사용 금지 권장** | 데이터 파라미터 |
| Mapper      |          사용 금지 |    SQL 파라미터 |

특히 다음 구조는 피하는 것이 좋습니다.

```java
public Customer selectCustomer() {
    ServiceContext ctx =
        ServiceContextHolder.getInstance();

    String customerNo =
        ctx.getUserContext().get("customerNo");
}
```

업무 입력을 Context에서 몰래 읽으면:

```text
메서드 Parameter만 봐서는
무엇이 필요한지 알 수 없음

        ↓

Hidden Dependency
        ↓
테스트 어려움
재사용 어려움
비동기 처리 어려움
```

이 됩니다.

좋은 형태는:

```java
selectCustomer(CustomerSearchDTO input)
```

입니다.

---

# 19. 현재 구조에서 꼭 점검해야 할 GAP ①

## URL ServiceId와 Header ServiceId

현재 분석자료에서는 ServiceId 결정 우선순위가 다음과 같이 확인됩니다.

```text
1. ServiceContext.rms_svc_c
2. 요청 JSON rms_svc_c
3. URL Path
```

따라서:

```text
POST /mgcoa8888S0

Header:
rms_svc_c = mgcoa8888D0
```

가 들어오면 Header 값이 우선되어 다른 Use Case로 라우팅될 가능성이 있습니다.

즉:

```text
URL ServiceId
      ≠
Header ServiceId

현재 일치 강제 검증 필요
```

입니다.

**보안과 거래 무결성 관점에서도 반드시 명시적인 일치 검증을 두는 것이 좋습니다.**

---

# 20. GAP ② Worker에 `ServiceContext` 객체 전체 전달

현재 `OnlineTimeoutWorkerContext`는 별도의 안전한 Snapshot을 만드는 것이 아니라 기존 `ServiceContext` 참조를 전달하는 형태로 분석되어 있습니다.

그런데 `ServiceContext`에는 Servlet Request/Response 같은 HTTP 객체도 포함됩니다.

개념적으로:

```text
Request Thread

ServiceContext
 ├─ Header
 ├─ GUID
 ├─ RequestBody
 ├─ HttpServletRequest   ←
 └─ HttpServletResponse  ←
        │
        ▼
Worker Thread로 객체 참조 전달
```

이 부분은 Thread 안전성 관점에서 개선 후보입니다.

### 권장 TO-BE

```text
Request Thread
      │
      ▼
WorkerContextSnapshot

├─ GUID
├─ ServiceId
├─ UserId
├─ BranchId
├─ Header Snapshot
├─ MDC
└─ Deadline

HttpServletRequest       X
HttpServletResponse      X
```

처럼 **Worker에 필요한 값만 복사한 불변 Snapshot**으로 만드는 것이 더 안전합니다.

---

# 21. GAP ③ Filter 오류는 아직 같은 표준전문이 아닐 수 있음

Controller 이후 오류는:

```text
hdr_nhnis + result
```

로 표준화되어 있습니다.

하지만 Filter에서:

```text
JWT 없음
Header 없음
JSON 파싱 실패
Body 없음
```

등이 발생하면 `sendError()` 경로를 사용하므로 표준 오류 Envelope를 거치지 않을 수 있습니다.

현재 구조:

```text
                    오류 발생
                       │
        ┌──────────────┴──────────────┐
        ▼                             ▼
     Filter                        Controller 이후
        │                             │
   sendError()                 GlobalExceptionHandler
        │                             │
 Servlet Error                    result
        │                             │
        ▼                             ▼
 비표준 가능                 hdr_nhnis + result
```

TO-BE는:

```text
모든 오류
   ↓
Common Error Response Writer
   ↓
hdr_nhnis + result
```

로 통일하는 것이 좋습니다.

---

# 22. 권장 최종 구조

현재 구조의 장점을 유지하면서 발전시키면 다음 형태가 가장 안정적입니다.

```text
                     STANDARD MESSAGE
                            │
              ┌─────────────┴─────────────┐
              ▼                           ▼
           HEADER                        DTO
              │                           │
              ▼                           ▼
       ServiceContext                 *DTOin
              │                           │
              │                           ▼
              │                       Business
              │
              ▼
       TransactionContext
              │
              ├─ ServiceId
              ├─ Start Time
              ├─ Deadline
              └─ Runtime Metadata
              │
              ▼
        WorkerContextSnapshot
              │
              ├─ GUID
              ├─ ServiceId
              ├─ User
              ├─ Branch
              ├─ MDC
              └─ Deadline
              │
              ▼
           Dispatcher
              │
              ▼
            Handler
              │
              ▼
            Facade
              │
              ▼
            Service  ◀──────── Business DTO
              │
         ┌────┴────┐
         ▼         ▼
        Rule       DAO
                   │
                   ▼
                 Mapper
                   │
                   ▼
                   DB
```

즉 **업무 DTO와 Runtime Context가 병렬로 흐르되 서로의 책임을 침범하지 않는 구조**가 좋습니다.

---

# 23. 초보자 관점에서는 이렇게 기억하면 됩니다

택배에 비유하면 이해하기 쉽습니다.

```text
표준전문
= 택배 상자

hdr_nhnis
= 송장

dto
= 상자 안의 물건

ServiceContext
= 배송 중 계속 참고하는 배송정보

TransactionContext
= 이번 배송작업의 실행정보

ServiceId
= 배송 목적지 코드

GUID
= 운송장 번호
```

그래서:

```text
              택배 상자
                 │
        ┌────────┴────────┐
        │                 │
       송장              내용물
        │                 │
        ▼                 ▼
ServiceContext         Business DTO
        │                 │
        └───────┬─────────┘
                ▼
             업무처리
```

라고 생각하면 됩니다.

---

## 핵심 정리

PDMG의 **표준전문 + Context 아키텍처**를 한 그림만 기억한다면 이것입니다.

```text
Client
  │
  │ { hdr_nhnis, dto }
  ▼
DefaultFilter
  │
  ├──── Header ───────────→ ServiceContext
  │                           │
  │                           ├─ GUID
  │                           ├─ ServiceId
  │                           ├─ User
  │                           ├─ Branch
  │                           └─ Request/Response
  │
  └──── DTO
          │
          ▼
Controller
  │
  ├─ ServiceContext
  │
  ▼
TransactionContext
  │
  ▼
STF / Timeout
  │
  ▼
Dispatcher
  │
  ▼
Handler
  │
  ▼
Facade
  │
  ▼
Service  ◀──────── DTO
  │
  ▼
DAO → Mapper → DB
  │
  ▼
ResponseBodyAdvice
  │
  ├─ 정상 → hdr_nhnis + dto
  └─ 오류 → hdr_nhnis + result
```

가장 중요한 아키텍처 원칙은 **`Header는 Context로, 업무정보는 DTO로 분리한다`**는 것입니다. Context는 GUID·ServiceId·사용자·점·추적정보를 공통 계층에 공급하지만, **업무 DTO나 DB Transaction 자체를 대신해서는 안 됩니다.**
