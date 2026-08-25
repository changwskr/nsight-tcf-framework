# Logging Architecture 구조

현재 **PDMG 실제 구조**를 기준으로 보면 Logging은 단순한 `log.info()` 집합이 아니라, **하나의 온라인 거래를 `GUID + ServiceId`로 연결하여 시스템 진입 → TCF → 업무 → DB → 오류 → 응답 → 운영증적까지 추적하는 공통 아키텍처**로 보는 것이 정확합니다.

현재 `pdmg-fw`의 `commons`에는 실제로 `log`, `imagelog`, `runtime`, `context` 영역이 존재하며, 시스템 공통 선·후처리에서 MDC·전문로그·ImageLog를 담당합니다.

## 1. Logging Architecture Big Picture

```text
                         PDMG LOGGING ARCHITECTURE

┌──────────────────────────────────────────────────────────────────────┐
│                         Client / pdmg-ui                             │
│                                                                      │
│ Authorization                                                       │
│ hdr_nhnis + dto                                                     │
└───────────────────────────────┬──────────────────────────────────────┘
                                │
                                ▼
════════════════════════════════════════════════════════════════════════
 ① SYSTEM LOGGING                                      pdmg-fw.commons
════════════════════════════════════════════════════════════════════════

                         DefaultFilter
                              │
                              ├─ Request 수신
                              ├─ GUID 준비
                              ├─ ServiceContext 생성
                              └─ MDC 구성
                              │
                              ▼
                ServicePreventionInterceptor
                              │
                              ├─ JWT 결과
                              ├─ User / IP
                              ├─ ServiceId
                              ├─ Request 전문 Logging
                              └─ PRE ImageLog
                              │
                              ▼

════════════════════════════════════════════════════════════════════════
 ② TCF / TRANSACTION LOGGING                              pdmg-fw.tcf
════════════════════════════════════════════════════════════════════════

                  OnlineTransactionController
                              │
                              ▼
                         TcfFacade
                              │
                              ├─ TransactionContext
                              ├─ Active TX Begin
                              │
                              ▼
                            STF
                              │
                              ├─ 거래통제
                              ├─ ServiceId
                              └─ 실행정책
                              │
                              ▼
                     Timeout Executor
                              │
                       Thread 전환
                              │
                              ▼
                  OnlineTimeoutWorkerContext
                              │
                     Context / MDC Restore
                              │
                              ▼
                    TransactionTemplate
                              │
                           TX BEGIN
                              │
                              ▼
                         Dispatcher
                              │
                         ServiceId
                              ▼

════════════════════════════════════════════════════════════════════════
 ③ BUSINESS LOGGING                                     pdmg-service
════════════════════════════════════════════════════════════════════════

                           Handler
                              │
                              ▼
                           Facade
                              │
                              ▼
                    PdmgBizTxFlowAspect
                              │
                     계층 실행흐름 추적
                              │
                              ▼
                     BizPrePostAspect
                              │
                  ┌───────────┴───────────┐
                  │                       │
               BEFORE                   AFTER
                  │                       │
          Service START              Service END
                  │                       │
                  ▼                       │
               Service                    │
                  │                       │
          ┌───────┼──────────┐            │
          ▼       ▼          ▼            │
        Rule     DAO     Integration       │
                  │                       │
                  ▼                       │
               Mapper                      │
                  │                       │
                  ▼                       │
                 DB                        │
                  │                       │
                  └───────────────┬───────┘
                                  ▼

════════════════════════════════════════════════════════════════════════
 ④ ERROR / RESPONSE LOGGING
════════════════════════════════════════════════════════════════════════

                       정상 / Exception
                              │
               ┌──────────────┴──────────────┐
               │                             │
             정상                           오류
               │                             │
               ▼                             ▼
       Response Logging           GlobalExceptionHandler
               │                             │
               │                    Error Code / Type
               │                             │
               └──────────────┬──────────────┘
                              ▼
                    POST / EXCEPTION
                       ImageLog
                              │
                              ▼
                      Active TX End
                              │
                              ▼
                 Context / MDC Clear
                              │
                              ▼
                         Response
```

시스템 선·후처리 기준에서도 `Request Logging → PRE ImageLog → 업무/TCF → Response Logging → POST/EXCEPTION ImageLog → Context/MDC Clear` 순서를 표준 흐름으로 정리하고 있습니다.

---

# 2. Logging을 6종류로 나누는 것이 좋습니다

| 로그 유형                | 주요 위치            | 목적                    | 핵심 Key                |
| ------------------------ | -------------------- | ----------------------- | ----------------------- |
| **System Log**           | Filter / Interceptor | HTTP 요청 생명주기      | GUID, ServiceId         |
| **Transaction Log**      | TCF / STF / ETF      | 거래 시작·종료·정책     | GUID, ServiceId         |
| **Business Log**         | Aspect / Service     | 업무 실행흐름           | GUID, ServiceId, Method |
| **ImageLog**             | `ImageLogHandler`    | 요청·응답·오류 증적     | GUID, ServiceId         |
| **Error Log**            | Exception Handler    | 장애 원인 추적          | ErrorCode, Exception    |
| **Runtime Log/Evidence** | Runtime Monitor      | Thread/TX/JVM/Pool 관측 | ServiceId, Thread       |

중요한 것은 이 여섯 가지를 별개 시스템으로 만들지 않는 것입니다.

```text
                       GUID
                         │
                         │
                     ServiceId
                         │
       ┌─────────────────┼─────────────────┐
       │                 │                 │
       ▼                 ▼                 ▼

 System Log        Business Log       Error Log
       │                 │                 │
       └─────────────────┼─────────────────┘
                         │
                         ▼
                     ImageLog
                         │
                         ▼
                 Runtime Evidence
```

즉 **GUID가 거래 인스턴스 Identity이고, ServiceId가 거래 종류 Identity**가 됩니다.

---

# 3. System Logging

현재 시스템 공통 로깅의 중심은:

```text
DefaultFilter
       ↓
ServicePreventionInterceptor
```

입니다.

주요 정보는 다음과 같이 구성됩니다.

```text
GUID
ServiceId
UserId
Branch
Client IP
ScreenId
Request Time
URI
HTTP Method
```

`DefaultFilter`에서는 `ServiceContext`와 MDC를 준비하고, Interceptor에서는 JWT/User/IP/ServiceId를 보정한 뒤 요청 전문 Logging과 PRE ImageLog를 수행합니다.

개념적으로:

```text
Request
   ↓
GUID = A001
ServiceId = mgcoa9001S0
User = E001

   ↓

MDC

guid      = A001
serviceId = mgcoa9001S0
userId    = E001
```

이후 코드에서는 단순히:

```java
log.info("business processing start");
```

를 남겨도 실제 로그에서는 개념적으로:

```text
GUID=A001
ServiceId=mgcoa9001S0
User=E001
business processing start
```

처럼 동일 거래를 연결할 수 있게 만드는 것이 MDC의 역할입니다.

---

# 4. 가장 중요한 문제 — Timeout Worker에서도 MDC가 이어져야 한다

PDMG에서는 Timeout ON이면 Thread가 바뀝니다.

```text
Tomcat Request Thread
        │
        ▼
Timeout Executor
        │
        ▼
pdmg-online-* Worker Thread
```

그런데 `ThreadLocal`, MDC 등은 기본적으로 다른 Thread에 자동 전달되지 않습니다.

현재 이를 보완하기 위해:

```text
nhnis.fw.tcf.timeout.OnlineTimeoutWorkerContext
```

가 존재합니다.

구조는 다음과 같습니다.

```text
Request Thread

ServiceContext
MDC
SecurityContext
      │
      │ capture
      ▼
OnlineTimeoutWorkerContext
      │
      │ restore
      ▼
Worker Thread

ServiceContext
MDC
SecurityContext
```

따라서 Logging Architecture에서 이것은 매우 중요한 구조입니다.

잘못 구현하면:

```text
Request Thread

GUID=A001
ServiceId=mgcoa9001S0

        ↓ Thread 전환

Worker Thread

GUID=null
ServiceId=null        ← 장애 추적 불가능
```

이 됩니다.

정상 구조는:

```text
Request Thread
GUID=A001
ServiceId=mgcoa9001S0

        ↓

Worker Thread
GUID=A001
ServiceId=mgcoa9001S0

        ↓

Service
DAO
Error
ImageLog

모두 동일 GUID
```

입니다.

---

# 5. Business Logging

현재 실제 PDMG에는:

```text
BizPrePostAspect
PdmgBizTxFlowAspect
```

가 존재합니다.

두 역할을 구분해야 합니다.

```text
PdmgBizTxFlowAspect
        │
        └─ Handler / Facade / Service / DAO
           계층 실행흐름 관찰


BizPrePostAspect
        │
        └─ Business Service
           START / END 관찰
```

현재 `BizPrePostAspect`의 정상적인 흐름은 다음입니다.

```text
Facade
   ↓
Business Service Proxy
   ↓
Business BEFORE
   │
   ├─ GUID
   ├─ ServiceId
   ├─ Service Class
   ├─ Method
   └─ START
   ↓
Service
   ↓
DAO
   ↓
DB
   ↓
정상 Return
   ↓
Business AFTER
   │
   ├─ END
   └─ Response DTO Logging
```

여기서 중요한 것은:

> **Business END 로그 = DB COMMIT 완료 로그가 아닙니다.**

예를 들어:

```text
Service Return
      ↓
BizPost END
      ↓
Facade Return
      ↓
TransactionTemplate
      ↓
COMMIT
```

일 수 있기 때문입니다.

따라서 로그 이름도:

```text
BUSINESS_END
```

와:

```text
TX_COMMIT
```

을 의미적으로 분리하는 것이 좋습니다.

---

# 6. ImageLog Architecture

현재 가장 중요한 영속성 로그 중 하나가:

```text
nhnis.fw.commons.imagelog.ImageLogHandler
```

입니다.

실제 구조에는:

```text
preImagelog()
postImagelog()
exceptionImagelog()
```

가 존재합니다.

```text
                    Request
                       │
                       ▼
                 PRE ImageLog
                       │
                       ▼
                  업무 실행
                       │
             ┌─────────┴─────────┐
             │                   │
           정상                Exception
             │                   │
             ▼                   ▼
       POST ImageLog      EXCEPTION ImageLog
```

현재 자료에서 저장 대상으로 정의된 핵심 정보는:

```text
GUID
ServiceId
ScreenId
User
Client IP

Request Time
Response Time

Request Message
Response Message

Exception Type
Exception Code
Exception Message
```

입니다. 운영·통제 구조에서도 ImageLog를 동일 거래의 요청·응답·오류를 연결하는 Runtime Evidence로 사용하고 있습니다.

현재 테이블은:

```text
TB_FW_IMAGE_LOG
```

로 정리되어 있습니다.

따라서 중요한 책임 분리는:

```text
Business Service
      │
      └── ImageLog INSERT       X


pdmg-fw
      │
      └── ImageLogHandler       O
```

입니다.

**ImageLog는 업무 DAO의 책임이 아니라 Framework 책임**입니다.

---

# 7. Error Logging

에러가 발생하면 다음 세 가지가 함께 연결되어야 합니다.

```text
Exception
   │
   ├─ Application Error Log
   │
   ├─ Standard Error Response
   │
   └─ EXCEPTION ImageLog
```

현재 에러처리 구조는:

```text
BizException
OnlineTimeoutException
OnlineOverloadException
NhBaseException
기타 Exception
```

등을 구분해 처리하고, ImageLog에는:

```text
GUID
ServiceId
ExceptionType
ExceptionCode
ExceptionMessage
```

를 기록하는 구조입니다.

예를 들어:

```text
GUID       = A001
ServiceId  = mgcoa9001S0
ErrorCode  = MP0404
ErrorType  = BIZ

Exception
   ↓
ROLLBACK
   ↓
GlobalExceptionHandler
   ↓
result
   ↓
EXCEPTION ImageLog
```

이렇게 되어야 합니다.

---

# 8. Runtime Logging / Runtime Evidence

PDMG는 일반 로그만으로 운영진단하려는 구조가 아닙니다.

현재:

```text
MgRuntimeMonitor
MgActiveTransactionRegistry
```

같은 Runtime 관측 기반도 존재합니다.

Active Transaction에는 개념적으로:

```text
GUID
ServiceId
BusinessCode
ThreadId
ThreadName
StartedAt
CurrentStep
Elapsed
```

가 존재합니다.

따라서 운영자는 다음처럼 볼 수 있습니다.

```text
GUID      ServiceId       Thread       Elapsed
------------------------------------------------
A001      mgcoa5530S0     pdmg-01       310 ms
A002      mgcoa8888S0     pdmg-02      2210 ms
A003      mgcoa5530S0     pdmg-03      4810 ms
```

그리고 이것을:

```text
ServiceId
   ↓
Active TX
   ↓
Thread
   ↓
DB Pool
   ↓
SQL
```

로 좁혀갈 수 있어야 합니다.

즉 궁극적으로 Logging Architecture는:

```text
"에러 로그가 있다"
```

에서 끝나는 것이 아니라:

```text
어느 사용자
   ↓
어느 GUID
   ↓
어느 ServiceId
   ↓
어느 Worker Thread
   ↓
어느 Service
   ↓
어느 DAO / SQL
   ↓
얼마나 수행
   ↓
왜 실패
```

까지 추적 가능해야 합니다.

---

# 9. 권장 로그 Correlation Key

현재 PDMG 기준에서 가장 중요한 Key는 다음과 같이 가져가는 것이 좋습니다.

| Key            | 의미                 | 중요도 |
| -------------- | -------------------- | -----: |
| `guid`         | 요청 1건의 Global ID |  ★★★★★ |
| `serviceId`    | 거래 종류            |  ★★★★★ |
| `programId`    | 프로그램             |   ★★★★ |
| `businessCode` | 업무                 |   ★★★★ |
| `userId`       | 사용자               |   ★★★★ |
| `screenId`     | 호출 화면            |    ★★★ |
| `threadName`   | 실행 Thread          |   ★★★★ |
| `txId`         | DB TX 추적           |   ★★★★ |
| `sqlId`        | Mapper SQL           |   ★★★★ |
| `elapsedMs`    | 수행시간             |  ★★★★★ |
| `errorCode`    | 표준 오류            |  ★★★★★ |

가장 중요한 관계는:

```text
GUID
 │
 ├─ ServiceId
 │    ├─ Handler
 │    ├─ Service
 │    ├─ DAO
 │    ├─ SqlId
 │    └─ ErrorCode
 │
 └─ Elapsed
```

입니다.

---

# 10. [TO-BE] 구조화 Logging 표준

현재 구조를 발전시킨다면 문자열 중심 로그보다는 **구조화 로그**를 권장합니다.

예:

```json
{
  "timestamp": "2026-08-17T21:18:30.123+09:00",
  "level": "INFO",
  "eventType": "BUSINESS_END",

  "guid": "A001",
  "serviceId": "mgcoa9001S0",
  "programId": "mgcoa9001",
  "businessCode": "CO",

  "userId": "E000001",
  "screenId": "mgcoa9001",

  "thread": "pdmg-online-3",

  "class": "mgcoa9001Service",
  "method": "mgcoa9001S0",

  "elapsedMs": 137,
  "result": "SUCCESS"
}
```

이렇게 하면 OM/ELK/OpenSearch/Splunk 등의 로그 플랫폼과 연결하기가 훨씬 쉬워집니다.

---

# 11. 전문 Logging에는 반드시 별도 통제가 필요합니다

이 부분은 **TO-BE 운영 표준으로 강제하는 것을 권장**합니다.

Request/Response 전체를 무조건 기록하면 안 됩니다.

```text
Standard Message
      │
      ▼
Logging Filter
      │
      ├─ 일반 필드      → Logging
      │
      ├─ 주민번호       → Masking
      │
      ├─ 계좌번호       → Masking
      │
      ├─ Password       → 제거
      │
      ├─ JWT            → 제거
      │
      └─ Private Key    → 절대 기록 금지
```

즉:

```text
전문 Logging
      ≠
전문 전체 Dump
```

입니다.

---

# 12. Logging Architecture Rule

최종적으로 다음을 Architecture Rule로 고정하는 것이 좋습니다.

```text
RULE-LOG-001
모든 온라인 거래는 GUID를 가져야 한다.

RULE-LOG-002
모든 업무 로그에는 ServiceId를 연결한다.

RULE-LOG-003
Request → Worker Thread 전환 시
ServiceContext와 MDC를 전파한다.

RULE-LOG-004
업무 프로그램이 ImageLog를 직접 저장하지 않는다.

RULE-LOG-005
업무 프로그램에서 System 로그 형식을 개별 정의하지 않는다.

RULE-LOG-006
Business END와 Transaction COMMIT을 구분한다.

RULE-LOG-007
Exception은 ErrorCode + ExceptionType + GUID + ServiceId를 남긴다.

RULE-LOG-008
모든 Timeout 로그에는
ServiceId + Timeout Policy + Elapsed를 기록한다.

RULE-LOG-009
전문 로그의 개인정보/인증정보를 Masking한다.

RULE-LOG-010
Request 종료 시 반드시 MDC와 ThreadLocal Context를 제거한다.
```

특히 마지막 규칙은 Thread Pool 환경에서는 중요합니다. 현재 시스템 선·후처리 구조도 요청 종료 후 `ServiceContext`와 MDC를 제거하도록 정의하고 있습니다.

---

# 13. 최종 권장 Logging 구조

```text
                       PDMG LOGGING

                         GUID
                          +
                       ServiceId
                          │
           ┌──────────────┼──────────────┐
           │              │              │
           ▼              ▼              ▼

      SYSTEM LOG       TX LOG       BUSINESS LOG
           │              │              │
           │              │              │
           └──────────────┼──────────────┘
                          │
                          ▼
                       ERROR LOG
                          │
                          ▼
                       IMAGE LOG
                          │
                          ▼
                    RUNTIME EVIDENCE
                          │
                          ▼
                         OM
                          │
                          ▼
                    장애/성능 분석
```

따라서 PDMG Logging Architecture의 핵심은 **“로그를 많이 남기는 것”이 아니라 `GUID + ServiceId` 하나로 전체 거래를 끝까지 재구성할 수 있도록 하는 것**입니다.

```text
Request
 → GUID
 → ServiceId
 → Thread
 → TCF
 → Handler
 → Service
 → DAO
 → SQL
 → TX
 → Error
 → Response
 → Runtime Evidence
```

이 흐름이 한 번에 추적되면 Logging Architecture가 완성됩니다.
