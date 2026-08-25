# Image Logging 아키텍처 구조

현재 PDMG/NSIGHT 맥락에서 말씀하신 **“Imaging Logging”은 `Image Logging(ImageLog)` 아키텍처**로 보는 것이 맞습니다.

ImageLog는 일반 `log.info()` 수준의 애플리케이션 로그가 아니라, **온라인 거래의 요청·응답·오류 시점의 핵심 전문과 실행 문맥을 거래 증적 형태로 남기는 구조**입니다. 현재 PDMG Logging Architecture에서도 ImageLog를 `PRE / POST / EXCEPTION`으로 구분하고, `GUID + ServiceId`를 중심으로 일반 로그·오류로그·Runtime Evidence와 연결하는 구조로 정의하고 있습니다.

---

## 1. Image Logging 전체 아키텍처

```text
                     PDMG IMAGE LOGGING ARCHITECTURE

┌───────────────────────────────────────────────────────────────┐
│                     Client / pdmg-ui                          │
│                                                               │
│ Authorization : Bearer JWT                                   │
│                                                               │
│ hdr_nhnis                                                    │
│ + dto                                                        │
└──────────────────────────────┬────────────────────────────────┘
                               │
                               ▼
                    HTTP Request 수신
                               │
                               ▼
┌───────────────────────────────────────────────────────────────┐
│                   SYSTEM PRE PROCESSING                       │
│                       pdmg-fw                                 │
│                                                               │
│ DefaultFilter                                                 │
│    │                                                          │
│    ├─ Request Body Cache                                      │
│    ├─ hdr_nhnis Parsing                                      │
│    ├─ ServiceContext 생성                                    │
│    └─ MDC 구성                                                │
│          │                                                    │
│          ▼                                                    │
│ ServicePreventionInterceptor.preHandle()                      │
│    │                                                          │
│    ├─ JWT Validation                                          │
│    ├─ GUID 보정                                               │
│    ├─ ServiceId 보정                                          │
│    ├─ User / IP / Screen 정보                                 │
│    │                                                          │
│    └─ ★ PRE IMAGE LOG                                        │
│          │                                                    │
│          │ 요청 시점의 거래 Image 저장                       │
└──────────┼────────────────────────────────────────────────────┘
           │
           ▼
┌───────────────────────────────────────────────────────────────┐
│                         TCF                                   │
│                                                               │
│ OnlineTransactionController                                   │
│       ↓                                                       │
│ TcfFacade                                                     │
│       ↓                                                       │
│ STF                                                           │
│       ↓                                                       │
│ Timeout Executor                                              │
│       ↓                                                       │
│ TransactionDispatcher                                        │
│       ↓                                                       │
│ Handler                                                       │
└──────────┬────────────────────────────────────────────────────┘
           ▼
┌───────────────────────────────────────────────────────────────┐
│                    BUSINESS APPLICATION                       │
│                                                               │
│ Facade                                                        │
│    ↓                                                          │
│ Service                                                       │
│    ↓                                                          │
│ DAO                                                           │
│    ↓                                                          │
│ Mapper / SQL                                                  │
│    ↓                                                          │
│ DB                                                            │
└──────────┬────────────────────────────────────────────────────┘
           │
     ┌─────┴─────┐
     │           │
     ▼           ▼
   정상        Exception
     │           │
     │           ▼
     │     GlobalExceptionHandler
     │           │
     ▼           ▼
 Response DTO   Error Result
     │           │
     └─────┬─────┘
           ▼
┌───────────────────────────────────────────────────────────────┐
│                  SYSTEM POST PROCESSING                       │
│                                                               │
│ ServicePreventionInterceptor / Response 처리                  │
│                                                               │
│     정상                           오류                        │
│      │                              │                         │
│      ▼                              ▼                         │
│ ★ POST IMAGE LOG             ★ EXCEPTION IMAGE LOG            │
│      │                              │                         │
│      └──────────────┬───────────────┘                         │
│                     ▼                                         │
│          ServiceContext / MDC Clear                           │
└─────────────────────┬─────────────────────────────────────────┘
                      ▼
                  HTTP Response
```

PDMG 시스템 선·후처리에서도 요청 시 `PRE ImageLog`, 정상 종료 시 `POST ImageLog`, 예외 시 `EXCEPTION ImageLog`가 시스템 공통 처리에 위치하는 구조로 정리되어 있습니다.

---

# 2. ImageLog의 핵심은 세 종류입니다

| 구분          | 시점              | 목적                              |
| ------------- | ----------------- | --------------------------------- |
| **PRE**       | 업무 실행 전      | 실제 어떤 요청이 들어왔는지 보존  |
| **POST**      | 정상 업무 종료 후 | 어떤 결과가 반환됐는지 보존       |
| **EXCEPTION** | 오류 발생 시      | 실패 당시 요청·오류·실행정보 보존 |

쉽게 보면:

```text
                     하나의 거래

Request
   │
   ▼
[PRE]
 요청 증적
   │
   ▼
업무 실행
   │
   ├──────── 정상 ────────→ [POST]
   │                       응답 증적
   │
   └──────── 오류 ────────→ [EXCEPTION]
                           장애 증적
```

ImageLog는 시스템 공통 선·후처리 책임에 포함되어 있으며, 현재 `pdmg-fw commons`에도 `imagelog` 영역이 별도로 존재합니다.

---

# 3. PRE ImageLog

PRE ImageLog는 **업무를 실행하기 직전의 거래 상태를 보존**합니다.

```text
HTTP Request
     ↓
DefaultFilter
     ↓
ServiceContext
     ↓
Interceptor
     │
     ├─ JWT 검증
     ├─ GUID 확정
     ├─ ServiceId 확정
     ├─ User 확정
     ├─ Client IP 확정
     │
     ▼
┌────────────────────────────┐
│       PRE IMAGE LOG        │
│                            │
│ GUID                       │
│ ServiceId                  │
│ User                       │
│ Branch                     │
│ Client IP                  │
│ ScreenId                   │
│ Request Header             │
│ Request DTO                │
│ Request Time               │
└────────────────────────────┘
     │
     ▼
TCF 실행
```

중요한 이유는 **업무 Service가 실행되기도 전에 원본 거래 정보를 남긴다**는 것입니다.

예를 들어 이후 DB 장애가 발생하더라도:

```text
무슨 ServiceId였나?
무슨 사용자가 요청했나?
어느 화면에서 호출했나?
무슨 DTO가 들어왔나?
GUID가 무엇인가?
```

를 확인할 수 있습니다.

---

# 4. POST ImageLog

정상 처리되면 POST ImageLog가 남습니다.

```text
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
COMMIT
  ↓
Response DTO
  ↓
┌────────────────────────────┐
│       POST IMAGE LOG       │
│                            │
│ GUID                       │
│ ServiceId                  │
│ 정상 처리 결과             │
│ Response DTO               │
│ 종료 시각                  │
│ 전체 처리시간              │
│ 사용자 / 화면 / IP         │
└────────────────────────────┘
```

그래서 하나의 정상 거래는 개념적으로:

```text
GUID = G202608170001
ServiceId = mgcoa9001S0

PRE
 ↓
업무 수행
 ↓
POST
```

라는 한 쌍을 갖습니다.

---

# 5. EXCEPTION ImageLog

업무 중 예외가 발생하면 POST가 아니라 **오류 당시 상태를 보존하는 EXCEPTION ImageLog**가 중요합니다.

```text
Service
   ↓
DAO
   ↓
Mapper
   ↓
DB
   │
   X Exception
   │
   ▼
ROLLBACK
   │
   ▼
GlobalExceptionHandler
   │
   ├─ ErrorCode
   ├─ ErrorType
   ├─ Exception Class
   ├─ Method
   ├─ File
   └─ Line
   │
   ▼
┌──────────────────────────────┐
│     EXCEPTION IMAGE LOG      │
│                              │
│ GUID                         │
│ ServiceId                    │
│ Request                      │
│ ErrorCode                    │
│ ErrorMessage                 │
│ ExceptionClass               │
│ Class / Method               │
│ Error 발생시각               │
│ User / IP                    │
└──────────────────────────────┘
```

PDMG 에러 처리 역시 업무 프로그램이 오류 JSON을 직접 만드는 방식이 아니라 예외를 전달하고 Framework의 `GlobalExceptionHandler`가 표준 오류정보를 생성합니다. 따라서 ImageLog도 이 오류 처리 구조와 연결해서 보는 것이 중요합니다.

---

# 6. ImageLog에서 가장 중요한 두 Key

PDMG Logging 전체에서 핵심은 다음 두 값입니다.

```text
GUID
+
ServiceId
```

역할은 서로 다릅니다.

| Key         | 의미                     | 예                    |
| ----------- | ------------------------ | --------------------- |
| `GUID`      | **실제 거래 한 건의 ID** | `20260817-ABC-000001` |
| `ServiceId` | **거래 종류의 ID**       | `mgcoa9001S0`         |

즉:

```text
ServiceId = mgcoa9001S0

오늘 실행
 ├─ GUID-A
 ├─ GUID-B
 ├─ GUID-C
 └─ GUID-D
```

입니다.

그래서 장애가 나면:

```text
ServiceId
   ↓
어떤 프로그램인가?

GUID
   ↓
그 프로그램의 어느 요청인가?
```

를 찾게 됩니다.

Logging Architecture에서도 GUID를 거래 인스턴스 Identity, ServiceId를 거래 종류 Identity로 연결하는 구조를 사용합니다.

---

# 7. ImageLog와 일반 Logging은 다릅니다

이 둘을 혼동하면 안 됩니다.

### 일반 로그

```java
log.info("customer search start");
log.debug("request={}", request);
log.error("customer search error", e);
```

주 목적은:

```text
개발
+
장애 분석
+
실행 흐름 확인
```

입니다.

### ImageLog

```text
거래 요청/응답 자체의 증적
```

입니다.

따라서:

| 항목           | 일반 Log | ImageLog |
| -------------- | -------- | -------- |
| 실행 흐름      | O        | △        |
| Debug          | O        | X        |
| 요청 원문      | 제한적   | O        |
| 응답 원문      | 제한적   | O        |
| 거래 증적      | △        | **O**    |
| PRE/POST 대응  | 보통 X   | **O**    |
| 장애 당시 전문 | 제한적   | **O**    |
| 조회/운영 분석 | O        | **O**    |

ImageLog는 **로그 파일 한 줄보다 거래 Snapshot에 더 가깝습니다.**

---

# 8. ImageLog 데이터 모델은 이렇게 가져가는 것이 좋습니다

현재 구조를 운영 가능한 Architecture로 확장하면 논리적으로 다음 정도가 적절합니다.

```text
IMAGE_LOG
│
├─ Identity
│   ├─ imageLogId
│   ├─ guid
│   ├─ traceId
│   └─ serviceId
│
├─ Classification
│   ├─ PRE
│   ├─ POST
│   └─ EXCEPTION
│
├─ Request Context
│   ├─ systemId
│   ├─ screenId
│   ├─ userId
│   ├─ branchCode
│   ├─ clientIp
│   └─ channel
│
├─ Message
│   ├─ requestHeader
│   ├─ requestBody
│   ├─ responseHeader
│   └─ responseBody
│
├─ Error
│   ├─ errorCode
│   ├─ errorType
│   ├─ errorMessage
│   └─ exceptionClass
│
└─ Runtime
    ├─ startTime
    ├─ endTime
    ├─ elapsedMs
    ├─ threadName
    └─ serverInstance
```

---

# 9. 다만 전문 전체를 무조건 저장하면 안 됩니다

ImageLog에서 특히 중요한 아키텍처 원칙입니다.

예를 들어 요청에:

```text
주민등록번호
계좌번호
전화번호
고객명
Access Token
Refresh Token
Password
인증정보
```

가 존재할 수 있습니다.

따라서 구조는 반드시:

```text
Request / Response
       │
       ▼
Sensitive Field Detection
       │
       ▼
Masking / Exclusion
       │
       ▼
ImageLog
```

여야 합니다.

특히:

```text
Authorization Header       저장 금지
JWT Access Token           저장 금지
Refresh Token              저장 금지
Password                   저장 금지

주민번호                    Masking
계좌번호                    Masking
전화번호                    Masking
고객식별정보                정책에 따른 Masking
```

처럼 **ImageLog 저장 정책 자체가 보안 아키텍처의 일부**여야 합니다.

---

# 10. Timeout / Transaction과 연결해서 보면

ImageLog는 Transaction과도 관계가 있습니다.

```text
PRE ImageLog
    ↓
TCF
    ↓
Timeout Executor
    ↓
TransactionTemplate
    ↓
TX BEGIN
    ↓
Business
    │
    ├─ 성공
    │    ↓
    │  COMMIT
    │    ↓
    │  POST ImageLog
    │
    └─ 실패
         ↓
       ROLLBACK
         ↓
       EXCEPTION ImageLog
```

여기서 중요한 설계 포인트는 **업무 DB Transaction과 ImageLog 저장 Transaction을 어떻게 분리할 것인가**입니다.

예를 들어 업무 Transaction과 ImageLog가 완전히 같은 TX라면:

```text
Business TX
 ├─ 업무 DB UPDATE
 └─ Exception ImageLog INSERT

       ↓

ROLLBACK
```

시 오류 증적까지 Rollback될 수 있습니다.

따라서 운영 증적 성격의 ImageLog는 일반적으로 다음처럼 별도 저장 경계를 검토할 가치가 있습니다.

```text
Business Transaction
        │
        ├─ COMMIT / ROLLBACK
        │
        ▼
Image Logging Boundary
        │
        └─ 별도 저장
```

다만 **현재 제공된 자료만으로 ImageLog 저장 DB Transaction이 실제로 `REQUIRES_NEW`, 비동기 Queue, 별도 DB 중 어느 방식인지까지는 확정할 수 없습니다.** 이 부분은 소스를 추가 확인해야 `AS-IS`로 단정할 수 있습니다.

---

# 11. Worker Thread 전환 시에도 Context가 이어져야 합니다

PDMG Timeout 구조에서는 Request Thread에서 Worker Thread로 전환될 수 있습니다.

```text
Request Thread
   │
   │ GUID = A001
   │ ServiceId = mgcoa9001S0
   ▼
Timeout Executor
   │
   ▼
Worker Thread
```

그런데 ThreadLocal/MDC를 그대로 사용하면 새 Thread에는 정보가 없을 수 있습니다.

따라서 현재 Logging Architecture에서 중요한 역할이:

```text
OnlineTimeoutWorkerContext
         ↓
Context / MDC Restore
```

입니다. 즉 Worker에서도 같은 GUID와 ServiceId로 로그를 남겨야 한 거래가 끊기지 않습니다.

정상 구조는:

```text
Request Thread
GUID=A001
      │
      ▼
Worker Thread
GUID=A001
      │
      ▼
DAO / SQL
GUID=A001
      │
      ▼
Response
GUID=A001
```

입니다.

---

# 12. 최종적으로 ImageLog는 Runtime Evidence까지 연결되어야 합니다

향후 NSIGHT 관점에서는 ImageLog를 단독 기능으로 끝내기보다:

```text
                GUID + ServiceId
                       │
       ┌───────────────┼────────────────┐
       │               │                │
       ▼               ▼                ▼
    PRE Image       Business Log     Runtime
       │               │                │
       │               │            Thread/TX
       ▼               │            Hikari/JVM
     POST              │                │
       │               │                │
       └───────────────┼────────────────┘
                       ▼
                 Runtime Evidence
```

로 연결하는 것이 좋습니다.

그러면 운영자가 `mgcoa9001S0` 한 건을 조회했을 때:

```text
ServiceId      mgcoa9001S0
GUID           G20260817-001
User           E0001234

PRE Image      O
POST Image     O

Business
  Service      mgcoa9001Service
  Method       mgcoa9001S0

DB
  Mapper       mgcoa9001-ORA.xml
  SQL ID       mgcoa9001S0_S0
  Elapsed      132ms

Runtime
  Thread       pdmg-online-17
  TX           COMMIT
  Total        287ms
```

까지 한 번에 연결할 수 있습니다.

---

## 핵심 정리

Image Logging 아키텍처를 한 장으로 압축하면 다음입니다.

```text
                         HTTP REQUEST
                              │
                              ▼
                       DefaultFilter
                              │
                       Context / MDC
                              │
                              ▼
                         Interceptor
                              │
                       ★ PRE IMAGE
                              │
                              ▼
                             TCF
                              │
                 STF → Timeout → Dispatcher
                              │
                              ▼
                     Handler → Facade
                              │
                              ▼
                       Service → DAO
                              │
                              ▼
                             DB
                              │
                  ┌───────────┴──────────┐
                  │                      │
               SUCCESS                 ERROR
                  │                      │
               COMMIT                ROLLBACK
                  │                      │
                  ▼                      ▼
           ★ POST IMAGE        ★ EXCEPTION IMAGE
                  │                      │
                  └──────────┬───────────┘
                             ▼
                   GUID + ServiceId
                             │
                             ▼
                Logging / Monitoring / OM
                             │
                             ▼
                    Runtime Evidence
```

따라서 **Image Logging은 “전문을 많이 찍는 기능”이 아니라 `PRE → 실행 → POST/EXCEPTION`으로 거래의 상태를 보존하고, `GUID + ServiceId`를 기준으로 장애분석·감사·운영 추적까지 연결하는 거래 증적 아키텍처**로 정의하는 것이 가장 적절합니다.
