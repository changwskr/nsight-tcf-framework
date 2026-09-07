# NSIGHT / PDMG 아키텍처 정의서 — VI. Standard Message / Context / Error / Logging Architecture

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 대상: PDMG Standard Message / Context / Error / Logging AS-IS Reference + TO-BE Contract  
> 문서 상태: **Draft / Evidence-First**  
> 작성일: 2026-08-31  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_V_Transaction_Timeout_Thread_DB_Architecture.md`

---

# 0. Evidence Register

| ID | 근거 자료 | 본 장 사용 목적 | 상태 |
|---|---|---|---|
| EV-VI-01 | `08장.HTTP 요청과 표준 전문.md` | `hdr_nhnis + dto`, Header 생명주기, local/non-local 전문 규칙 | `[PDMG AS-IS EVIDENCE]` |
| EV-VI-02 | `10.전문-1.md` | PDMG 실제 성공=`dto`, 실패=`result`, Header 보강, Filter 오류 | `[PDMG AS-IS EVIDENCE]` |
| EV-VI-03 | `11장.ServiceContext와 GUID.md` | ServiceContext 필드/수명/GUID/MDC/Worker 전파 | `[PDMG AS-IS EVIDENCE]` |
| EV-VI-04 | `16.Service Context.md` | Context 주요 소비처, TransactionContext와 차이 | `[PDMG AS-IS EVIDENCE]` |
| EV-VI-05 | `26장.예외처리와 표준 오류.md` | TCF ON 예외, `NH_NIS_ERR_DTO`, `result`, Filter 예외 Gap, HTTP Status | `[PDMG AS-IS EVIDENCE]` |
| EV-VI-06 | `27장.이미지로그와 GUID 추적.md` | `TB_FW_IMAGE_LOG`, PRE/POST/EX, GUID 추적, Fail-open Risk | `[PDMG AS-IS EVIDENCE]` |
| EV-VI-07 | `15.Interceptor-ServicePrevention.preHandle-1.md` | 시스템 선처리 로그·ImageLog 시작 위치 | `[PDMG AS-IS EVIDENCE]` |
| EV-VI-08 | `14.Spring MVC-1.md` | Request Resolver / ResponseBodyAdvice 역할·충돌 가능성 | `[PDMG AS-IS EVIDENCE]` |
| EV-VI-09 | `NSIGHT_PDMG_아키텍처_정의서_IV_PDMG_Online_Runtime_TCF_Flow.md` | Runtime/Response/Error Handoff | `[CURRENT BASELINE DRAFT]` |
| EV-VI-10 | `NSIGHT_PDMG_아키텍처_정의서_V_Transaction_Timeout_Thread_DB_Architecture.md` | Commit/Rollback/Timeout 결과를 Error/Log와 연결 | `[CURRENT BASELINE DRAFT]` |
| EV-VI-11 | `NSIGHT_PDMG_아키텍처_인포그래픽_이미지화_마스터_프롬프트.md` | VI장 필수 View / 메시지·Context·Error·Log 계약 | `[WORKING BASELINE]` |
| EV-VI-12 | PDMK 통합 정의서 계열 | 유사 commons 구조 비교용. PDMG current contract로 자동 승격 금지 | `[COMPARATIVE ONLY]` |

> **중요:** PDMK 계열 통합문서에는 Legacy commons 오류가 `{ hdr_nhnis, dto: NH_NIS_ERR_DTO }`로 표현되는 구간이 있다.  
> PDMG 현재 TCF ON Source 분석은 **성공 `{hdr_nhnis,dto}`, 알려진 실패 `{hdr_nhnis,result}`**이다.  
> 본 장에서는 PDMG Current Source를 우선한다.

---

# 1. Figure Plan

| FIG | 제목 | Level | 목적 | 필수 |
|---|---|---:|---|---|
| FIG-VI-01 | Standard Message / Context / Evidence Big Picture | L0~L3 | 전문·Context·Error·Log 전체 구조 | Y |
| FIG-VI-02 | Standard Request Envelope | L2 | `hdr_nhnis + dto` 책임 분리 | Y |
| FIG-VI-03 | `sys_comm` Core Field Map | L2 | GUID/Service/User/Screen/IP | Y |
| FIG-VI-04 | Header Lifecycle | L2~L3 | Client→Filter→Interceptor→Controller→Response | Y |
| FIG-VI-05 | DTO Binding / Business Boundary | L2~L3 | 전체 전문과 업무 DTO 분리 | Y |
| FIG-VI-06 | ServiceContext Structure | L2 | Context 필드와 책임 | Y |
| FIG-VI-07 | ServiceContext / TransactionContext / MDC 비교 | L2~L3 | 혼동 제거 | Y |
| FIG-VI-08 | GUID End-to-End Trace | L2~L4 | 전문·MDC·Worker·ImageLog·SQL 연결 | Y |
| FIG-VI-09 | Validation / Reject Layers | L2~L4 | Filter/MVC/Business/DB Validation | Y |
| FIG-VI-10 | Exception Taxonomy | L2~L4 | Filter/TCF/Biz/System/DB 분류 | Y |
| FIG-VI-11 | Error Mapping & Message Source | L2~L4 | Code→Message→DTO→HTTP | Y |
| FIG-VI-12 | Success Response Envelope | L3 | `hdr_nhnis + dto` | Y |
| FIG-VI-13 | Known Error Response Envelope | L3 | `hdr_nhnis + result` | Y |
| FIG-VI-14 | Filter/Security Early Error Gap | L3~L4 | Envelope 우회 | Y |
| FIG-VI-15 | Logging Channel Map | L2~L4 | MDC/TxLog/ImageLog/SQL/UI Relay | Y |
| FIG-VI-16 | ImageLog PRE/POST/EX Lifecycle | L3~L4 | 감사 DB 증적 | Y |
| FIG-VI-17 | Business TX vs ImageLog Evidence | L3~L4 | commit과 log 분리 | Y |
| FIG-VI-18 | Sensitive Data / Log Boundary | L3~L5 | 마스킹/Token/전문 원문 | Y |
| FIG-VI-19 | Error/Log Failure & Risk Map | L4 | 응답·감사·추적 Gap | Y |
| FIG-VI-20 | VII장 Handoff | L5 | JWT/SSO/Security 연결 | Y |

---

# 2. 핵심 결론

VI장의 핵심은 **PDMG 온라인 거래의 “실행 결과”를 표준 계약과 운영 증적으로 바꾸는 것**이다.

현재 Source 기준 핵심 결론은 다음과 같다.

1. PDMG 온라인 Request Envelope는 **`hdr_nhnis + dto`** 두 영역으로 구성된다.
2. `hdr_nhnis.sys_comm`은 Framework가 소유하는 시스템 공통정보이고, `dto`는 ServiceId별 Business Input/Output이다.
3. Handler 이후 Business 계층은 전체 전문을 전달받기보다 `dto`를 중심으로 처리하며 공통 Header는 필요할 때 `ServiceContext`를 통해 접근한다.
4. Header는 단순 요청 Echo가 아니다. Filter·Interceptor·Controller가 GUID, ServiceId, IP 등 일부 값을 보강할 수 있고 Response는 **처리 후 ServiceContext Header**를 사용한다.
5. 현재 핵심 추적축은 `std_gbl_id` GUID이며 `ServiceContext`, MDC, TransactionContext, Worker Context, ImageLog를 연결한다.
6. `ServiceContext`는 HTTP Request 범위의 Framework 공유 Context이며 DB Transaction 객체가 아니다.
7. `TransactionContext`는 TCF 실행구간의 ServiceId + ServiceContext 참조 + 경과정보 객체이며 DB Transaction이 아니다.
8. 성공 응답은 **`{ hdr_nhnis, dto }`** 구조다.
9. 현재 TCF ON에서 알려진 Framework/Business 실패는 **`{ hdr_nhnis, result }`** 구조의 `NH_NIS_ERR_DTO`로 조립된다.
10. Filter/Security 단계에서 `sendError(400/401)`로 조기 종료되는 실패는 MVC Advice를 거치지 않아 표준 Error Envelope가 보장되지 않는다.
11. 현재 예외·메시지 체계는 TCF `BizException`/Global Handler 경로, Legacy `NhBaseException`/Resolver 경로, Filter `sendError` 경로가 공존하므로 **하나의 중앙 Error Contract로 완전히 닫혀 있지 않다.**
12. `exceptionCode.yml`이 존재한다고 모든 오류가 자동으로 그 Message를 사용하는 것은 아니다. Throw Type → Handler → Message Resolver가 실제 연결되어야 한다.
13. PDMG ImageLog는 `TB_FW_IMAGE_LOG`에 GUID 기준 PRE/POST/EX 증적을 남기며 업무 DB Transaction과 분리될 수 있다.
14. ImageLog 저장 실패는 본거래를 보호하기 위해 Fail-open 성격이 있으나, 이는 감사 공백 Risk를 만든다.
15. 로그 채널은 MDC/애플리케이션 거래로그/ImageLog/SQL 로그/선택적 UI Relay 로그로 역할이 다르며 하나의 로그로 통합해 설명하면 안 된다.
16. 요청·응답 전문 원문, 사용자정보, Token, SQL Parameter에는 민감정보가 포함될 수 있으므로 **“추적성 확보”와 “민감정보 최소화”를 함께 설계해야 한다.**
17. GUID는 추적키이지 인증정보나 업무 PK가 아니다.
18. VI장의 최종 목표는 한 거래에 대해 다음을 연결하는 것이다.

```text
GUID
  ↓
ServiceId
  ↓
Request
  ↓
Thread / Transaction
  ↓
Business / SQL
  ↓
Commit / Rollback
  ↓
Response / Error
  ↓
ImageLog / Application Log / SQL Log / Audit
```

---

# 3. 목적 / 범위 / 전제

## 3.1 목적

본 장은 다음 질문에 답한다.

1. PDMG 표준 Request/Response의 실제 JSON 계약은 무엇인가?
2. `hdr_nhnis`와 `dto`는 어떤 책임을 가진가?
3. `sys_comm`에서 아키텍처적으로 중요한 필드는 무엇인가?
4. Header는 요청부터 응답까지 어떻게 변하는가?
5. ServiceContext는 어떤 데이터를 담으며 누구가 소비하는가?
6. TransactionContext, ServiceContext, MDC는 어떻게 다른가?
7. GUID는 어느 저장소와 Runtime 경계를 연결하는가?
8. Validation은 Filter/MVC/Business/DB 어디에서 나뉘는가?
9. PDMG Error Type은 어떤 계층에서 발생하는가?
10. Error Code와 Message는 어디에서 해석되는가?
11. 성공과 실패 Envelope는 정확히 어떻게 다른가?
12. Filter/Security 오류가 표준 Error Envelope를 우회하는 문제를 어떻게 볼 것인가?
13. ImageLog는 어떤 시점에 Insert/Update하는가?
14. Business Transaction과 ImageLog Transaction은 어떤 관계인가?
15. SQL Log와 Application Log와 ImageLog는 무엇이 다른가?
16. 개인정보/민감정보를 어디까지 기록할 것인가?
17. VII장의 JWT/SSO/Security로 무엇을 넘길 것인가?

## 3.2 포함

```text
hdr_nhnis
sys_comm
dto
result
NH_NIS_ERR_DTO
DefaultFilter
CachedBody
RequestBodyArgumentResolver
ResponseBodyArgumentResolver
ServiceContext
ServiceContextHolder
TransactionContext
MDC / ThreadContext
GUID / std_gbl_id
ServiceId / rms_svc_c
scid
optr_eno
tr_trm_ipadr
Validation
BizException
NhBaseException
OnlineTimeoutException
OnlineOverloadException
ServiceHandlerNotFound
GlobalExceptionHandler
Message Source
exceptionCode.yml
MessageCache [연결 확인 범위]
PDMG Application Log
ImageLogHandler
TB_FW_IMAGE_LOG
MyBatis/SQL Log
Audit / Masking Boundary
```

## 3.3 제외

```text
JWT 발급/검증/Refresh 상세          → VII
SSO/IdP                            → VII
Masking 알고리즘/암호키            → VII
Tomcat/Log Disk/Retention Capacity → VIII/IX
OM Dashboard                       → IX
ServiceId→SQL/Table Closed Loop     → X
```

---

# 4. FIG-VI-01 — Standard Message / Context / Evidence Big Picture

```text
┌──────────────────────────── Client ────────────────────────────┐
│                                                               │
│  Request                                                      │
│  {                                                            │
│    hdr_nhnis : { sys_comm : {...} },                           │
│    dto       : { 업무 입력 }                                   │
│  }                                                            │
└─────────────────────────────┬─────────────────────────────────┘
                              │
                              ▼
┌──────────────────────── Framework Boundary ───────────────────┐
│                                                               │
│ DefaultFilter                                                 │
│  ├─ hdr_nhnis parse / 보강                                    │
│  ├─ GUID / ServiceContext                                     │
│  └─ MDC                                                       │
│       │                                                       │
│       ▼                                                       │
│ Interceptor                                                   │
│  ├─ GUID/Service/User/IP 보강                                  │
│  └─ Pre ImageLog                                              │
│       │                                                       │
│       ▼                                                       │
│ Controller / TCF                                              │
│  ├─ ServiceId 결정                                            │
│  └─ dto 추출                                                  │
└─────────────────────────────┬─────────────────────────────────┘
                              │
                              ▼
┌──────────────────────── Business Boundary ────────────────────┐
│                                                               │
│ Handler → Facade → Service → DAO → SQL                        │
│                                                               │
│ 입력 중심: dto                                                │
│ 공통정보: ServiceContext / TransactionContext                 │
└─────────────────────────────┬─────────────────────────────────┘
                              │
                 ┌────────────┴─────────────┐
                 │                          │
                 ▼                          ▼
            [Success]                   [Known Error]
                 │                          │
                 ▼                          ▼
       hdr_nhnis + dto            hdr_nhnis + result
                 │                          │
                 └────────────┬─────────────┘
                              ▼
                    ServiceContext.responseBody
                              │
                              ▼
                    Post / Exception ImageLog
                              │
                              ▼
        App Log + MDC + ImageLog + SQL Log + Audit
```

---

# 5. FIG-VI-02 — Standard Request Envelope

## 5.1 PDMG Current Contract

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "rms_svc_c": "mgcoa8888S0",
      "std_gbl_id": "...",
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

## 5.2 책임 분리

```text
┌──────────────────── hdr_nhnis ─────────────────────┐
│                                                    │
│ Framework / System Context                         │
│                                                    │
│ GUID                                               │
│ ServiceId                                          │
│ Screen                                             │
│ Client IP                                          │
│ User / Branch                                      │
│ Channel / Transaction Metadata                     │
│                                                    │
└────────────────────────────────────────────────────┘

┌──────────────────────── dto ───────────────────────┐
│                                                    │
│ Business Contract                                  │
│                                                    │
│ 조회조건                                           │
│ 등록/변경 데이터                                   │
│ 페이징                                             │
│ 업무 List                                          │
│ 업무 결과                                          │
│                                                    │
└────────────────────────────────────────────────────┘
```

## 5.3 소유

| Node | Owner | 주 소비자 |
|---|---|---|
| `hdr_nhnis` | Framework | Filter/Context/Interceptor/TCF/Response |
| `sys_comm` | Framework | Trace/Auth Context/Service Routing |
| Request `dto` | Business | Handler/Facade/Service |
| Response `dto` | Business | UI/Client |
| `result` | Framework Error Contract | Client/Error Log |

## 5.4 금지

```text
업무 DTO에 GUID/User/IP 복제
            X

공통 Header에 업무 List 저장
            X

DAO에 전체 전문 전달
            X
```

---

# 6. Local vs Non-local Request Contract

## 6.1 Local

개발 편의를 위해 `dto`만 들어온 경우 Filter가 synthetic Header를 만들 수 있다.

```json
{
  "dto": {
    "pageNo": 1
  }
}
```

가능:

```text
LOCAL ONLY
```

## 6.2 Non-local

통합/운영 성격 환경에서는:

```text
hdr_nhnis.sys_comm
+
JWT
```

가 정상 계약이다.

Header가 없으면 `400`, Bearer가 없거나 무효하면 `401` 경로로 종료될 수 있다.

## 6.3 Architecture Rule

```text
Local 성공
≠
운영 전문 검증 완료
```

Local synthetic Header에 의존하는 Test만으로 표준전문 적합성을 판정하지 않는다.

---

# 7. FIG-VI-03 — `sys_comm` Core Field Map

현재 Source/분석에서 반복적으로 확인되는 핵심 필드:

```text
hdr_nhnis
└─ sys_comm
   │
   ├─ std_gbl_id     → GUID
   │
   ├─ rms_svc_c      → Service ID
   │
   ├─ scid           → Screen / Program ID
   │
   ├─ tr_trm_ipadr   → Client / Terminal IP
   │
   ├─ optr_eno       → Operator / User
   │
   ├─ tr_brc         → Branch
   │
   ├─ tr_sysid       → System
   │
   ├─ tr_dtm         → Transaction DateTime
   │
   ├─ sync_dsc       → Sync Classification
   │
   ├─ ttl_ug_ync     → Header Flag [Spring TX와 동일개념 아님]
   │
   └─ 기타 전문 필드
```

## 7.1 핵심 5축

본 장에서 End-to-End 추적에 가장 중요한 값:

```text
std_gbl_id
rms_svc_c
scid
tr_trm_ipadr
optr_eno
```

## 7.2 보안 주의

```text
Header에 optr_eno가 있다
≠
해당 사용자가 인증되었다
```

클라이언트가 입력할 수 있는 Header 값은 단독 Authorization 근거로 사용하지 않는다.

검증된 JWT/SSO Trust와 연계는 VII장에서 닫는다.

---

# 8. FIG-VI-04 — Header Lifecycle

```text
Client JSON
   │
   ▼
DefaultFilter
   ├─ hdr_nhnis parse
   │
   ├─ local이면 합성 가능
   ├─ GUID 확보
   ├─ request header/IP 일부
   └─ ServiceContext.header
   │
   ▼
ServicePreventionInterceptor
   ├─ sys_comm 보장
   ├─ GUID 보완
   ├─ ServiceId 보완 후보
   ├─ IP/User 보완
   └─ MDC 동기화
   │
   ▼
OnlineTransactionController
   ├─ ServiceId 확정
   └─ Client IP 등 보완
   │
   ▼
Business Runtime
   │
   └─ Header 원문보다 Context typed Header 사용
   │
   ▼
ResponseBodyArgumentResolver
   │
   └─ 현재 ServiceContext.header 직렬화
   ▼
Response hdr_nhnis
```

## 8.1 중요한 정정

응답 Header는:

```text
"요청 Header 원문 그대로 Echo"
```

가 아니다.

정확한 설명은:

```text
요청에서 생성된 Header를
Framework가 보강한 뒤
현재 ServiceContext Header를 응답에 사용
```

이다.

---

# 9. ServiceId 정합성 재강조

IV장에서 확인한 Current 우선순위:

```text
1. ServiceContext Header
2. Request JSON Header
3. URL Path
```

따라서:

```text
URL        = mgcoa8888S0
Header     = mgcoa8888D0
실행 가능  = mgcoa8888D0
```

Risk가 존재할 수 있다.

전문 Architecture 관점에서:

```text
Request Contract
=
URL ServiceId
+
Header ServiceId
```

둘 다 존재하는 경우 일치시키는 것이 목표 후보이다.

---

# 10. FIG-VI-05 — DTO Binding / Business Boundary

## 10.1 TCF ON

```text
전체 JSON
{
  hdr_nhnis,
  dto
}
      │
      ▼
OnlineTransactionController
      │
      ├─ serviceId
      └─ request["dto"]
               │
               ▼
             TCF
               │
               ▼
            Handler
               │
               ▼
          Business Facade
```

Handler 이후 Business의 주 입력:

```text
dto
```

Header가 필요한 경우:

```text
ServiceContext
또는
TransactionContext.getHeader()
```

를 통해 접근한다.

## 10.2 전체 전문을 DAO까지 끌고 가지 않는다

```text
HTTP Request Map
      ↓
Handler
      ↓
Facade
      ↓
Service
      ↓
DAO

X
```

권장:

```text
System Context → Context
Business Data  → typed DTO / parameter
Persistence    → SQL parameter
```

---

# 11. RequestBodyArgumentResolver의 경계

PDMG에는 `RequestBodyArgumentResolver`가 존재하며 `dto` 노드만 typed parameter로 변환하는 의도가 있다.

개념:

```text
JSON root
   ↓
root["dto"]
   ↓
{serviceId}DTOin
```

하지만 TCF 공통 `OnlineTransactionController`는 전체 JSON `Map<String,Object>`가 필요하다.

따라서 Resolver 순서/지원 범위에 따라:

```text
typed Business Controller
vs
TCF Common Controller
```

의 요구가 충돌할 가능성이 분석되어 있다.

`[GAP-VI-01]`

TO-BE 후보:

```text
전용 Annotation
예: @PdmgRequestBody
```

또는 TCF Controller에서 전용 Standard Request 타입을 사용하여 Spring 표준 `@RequestBody`와 커스텀 Resolver의 의도를 분리한다.

---

# 12. FIG-VI-06 — ServiceContext Structure

현재 ServiceContext 주요 필드:

```text
ServiceContext
├─ applicationName
├─ guid
├─ active
├─ requestHeaders
├─ httpServletRequest
├─ httpServletResponse
├─ header : hdr_nhnis
├─ userContext : Map<String,Object>
├─ requestBody
└─ responseBody
```

## 12.1 역할별 그룹

```text
[Environment]
applicationName
active

[Trace]
guid

[HTTP]
requestHeaders
httpServletRequest
httpServletResponse

[Standard Contract]
header

[Extension]
userContext

[Audit Snapshot]
requestBody
responseBody
```

## 12.2 ServiceContext가 관리하지 않는 것

```text
Spring DB Transaction
JDBC Connection
SqlSession
업무 DTO 전체 생명주기
인증정책 자체
Thread 자동전파
```

---

# 13. ServiceContext는 숨은 Business Input이 되면 안 된다

좋은 사용:

```text
GUID
인증 후 사용자
점코드
채널 정보
```

주의할 사용:

```text
업무 조회조건
대량 List
업무 계산 중간값
DAO용 Query Parameter
```

Context 접근이 많을수록 Method Signature에 입력이 드러나지 않는다.

따라서:

```text
Business Data
→ 명시적 Method Argument

Cross-cutting Request Data
→ ServiceContext
```

로 나눈다.

---

# 14. `userContext` Risk

현재 `userContext`는 mutable `Map<String,Object>` 성격이다.

장점:

```text
확장 쉬움
```

위험:

```text
Key 문자열 Drift
branchCode vs brc
타입 Drift
Runtime Cast Error
Owner 불명확
```

TO-BE 후보:

```text
AuthenticatedUserContext
├─ userId
├─ branchCode
├─ roles
├─ channel
└─ ...
```

같은 명시적 immutable 타입으로 표준화.

단, 구체 필드는 VII장의 인증/인가 근거와 함께 결정한다.

---

# 15. FIG-VI-07 — Context Object 비교

| 구분 | ServiceContext | TransactionContext | MDC |
|---|---|---|---|
| 목적 | HTTP Request 공통 Context | TCF 실행 최소 Context | Log Correlation |
| 수명 | Filter→응답 종료 | TcfFacade→Handler | Thread |
| 보관 | ThreadLocal Holder | Method Argument | ThreadLocal |
| GUID | 있음 | ServiceContext 통해 있음 | Key |
| ServiceId | Header/보강 | 직접 필드 | Key |
| Header | 있음 | 참조 가능 | 없음 |
| Request/Response | 참조 있음 | 직접소유 아님 | 없음 |
| DB TX | 아님 | 아님 | 아님 |
| Worker 자동전파 | 안 됨 | 인자로 전달 | 안 됨 |

## 15.1 혼동 금지

```text
TransactionContext
= DB Transaction

X
```

```text
MDC
= 업무 Context 저장소

X
```

---

# 16. FIG-VI-08 — GUID End-to-End Trace

```text
Client / Filter
std_gbl_id
    │
    ▼
ServiceContext.guid
    │
    ├────────────► Header.sys_comm.std_gbl_id
    │
    ├────────────► MDC["guid"]
    │
    ├────────────► TransactionContext.getGuid()
    │
    ├────────────► Worker MDC
    │
    ├────────────► Application Log
    │
    ├────────────► ImageLog.GUID
    │
    └────────────► Error/Response Trace 후보
```

## 16.1 정상 Trace

```text
Request GUID = G123
    ↓
Pre ImageLog GUID = G123
    ↓
Worker Log GUID = G123
    ↓
Business Log GUID = G123
    ↓
SQL Log GUID = G123 [MDC 연결 시]
    ↓
Post ImageLog GUID = G123
```

## 16.2 GUID 불일치 영향

```text
Header GUID
≠
Context GUID
≠
MDC GUID
```

이면:

```text
ImageLog UPDATE 0건
로그 검색 단절
Timeout Worker 추적 단절
```

이 발생할 수 있다.

---

# 17. GUID가 의미하지 않는 것

GUID는:

```text
Trace Correlation ID
```

다.

GUID가 아닌 것:

```text
사용자 인증 ID
업무 데이터 PK
Idempotency Key [별도 결정 필요]
DB Transaction ID
JWT JTI
```

같은 GUID를 Client가 재사용할 가능성을 완전히 배제할 Source Evidence는 본 장에 없다.

따라서 GUID PK 정책과 “거래 시도 ID” 의미는 별도 검토가 필요하다.

`[OPEN-VI-01]`

---

# 18. FIG-VI-09 — Validation / Reject Layers

Validation은 하나의 위치가 아니다.

```text
[Layer 1 - Transport / Filter]
Body 존재?
JSON 문법?
Header 존재?
JWT 존재/유효?
        │
        ├─ 실패 → 400 / 401
        └─ 성공
             ↓

[Layer 2 - MVC / Binding]
Path?
DTO 변환?
Type?
        │
        ├─ 실패 → MVC Error
        └─ 성공
             ↓

[Layer 3 - TCF Contract]
ServiceId 존재?
Handler 등록?
Path/Header 일치?
        │
        ├─ 실패 → Framework Error
        └─ 성공
             ↓

[Layer 4 - Business Validation]
필수업무조건?
대상 존재?
중복?
권한?
        │
        ├─ 실패 → Business Exception/Result
        └─ 성공
             ↓

[Layer 5 - Persistence]
DB Constraint?
Lock?
SQL?
        │
        └─ 실패 → System/Data Error
```

---

# 19. Validation과 Business Reject를 구분한다

예:

```text
JSON 깨짐
= Transport Error

customerId 형식 오류
= Input Validation

대상고객 없음
= Business Reject 또는 Not Found

중복 등록
= Business Conflict

DB Unique Constraint
= Persistence Error
```

모든 것을 `E9999`로 처리하면 운영·Client 계약이 무너진다.

TO-BE Error Taxonomy는 계층별 의미를 보존해야 한다.

---

# 20. FIG-VI-10 — Exception Taxonomy

```text
                    [Failure]
                       │
     ┌─────────────────┼────────────────────┐
     ▼                 ▼                    ▼
 Transport          Framework             Business
 / Security            │                    │
     │                 │                    │
 Filter              TCF                  Biz Rule
 400/401             Handler Missing      Validation
                     Timeout              Conflict
                     Overload             Reject
                       │                    │
     └─────────────────┼────────────────────┘
                       ▼
                  System / Data
                       │
                   Runtime
                   MyBatis
                   JDBC
                   DB
```

## 20.1 Current 주요 Type

```text
ServiceHandlerNotFound
BizException
OnlineTimeoutException
OnlineOverloadException
NhBaseException [Legacy commons 경로]
RuntimeException / DataAccess Exception 계열
Filter sendError
```

---

# 21. Current PDMG TCF Error Mapping

현재 IV장에서 확인한 Known Mapping:

| Exception | Current Code | HTTP | Error Type |
|---|---|---:|---|
| `ServiceHandlerNotFound` | `E9999` | 500 | SERVICE 계열 |
| `BizException` | 업무/전달 code | 500 | BIZ/SERVICE 후보 |
| `OnlineTimeoutException` | `FW_TIMEOUT` | 504 | Framework |
| `OnlineOverloadException` | `FW_OVERLOADED` | 503 | Framework |
| Filter Body/JSON/Header | Servlet Error | 400 | Envelope 없음 |
| Filter JWT | Servlet Error | 401 | Envelope 없음 |

## 21.1 문제

Business Error와 System Error의 HTTP Status 중앙정책이 완전히 닫혀 있지 않다.

예:

```text
대상 없음
입력 오류
중복
권한 없음
```

을 모두 500으로 보낼지는 별도 정책이 필요하다.

---

# 22. Legacy `NhBaseException` 경로

PDMG Framework에는 Legacy commons 예외/Resolver 구조도 존재한다.

개념:

```text
NhBaseException
  ├─ stdErrCode
  ├─ TYPE
  ├─ messageValue[]
  └─ optional completed message
        │
        ▼
Legacy Error Processor
        │
        ▼
NH_NIS_ERR_DTO
```

TCF ON의 `BizException` Global Handler와 **동일한 예외 모델이라고 단정하지 않는다.**

현재 Source에는 복수 Error Path가 존재한다.

---

# 23. FIG-VI-11 — Error Mapping & Message Source

```text
Exception / Reject
      │
      ▼
Error Code
      │
      ├─────────────┐
      │             │
      ▼             ▼
exceptionCode.yml   MessageCache
FW/Common/Auth      Service/Biz Legacy
      │             │
      └──────┬──────┘
             ▼
       Message Resolve
             │
             ▼
      NH_NIS_ERR_DTO
             │
             ▼
       HTTP Status
             │
             ▼
      Response Envelope
```

## 23.1 중요한 Current Gap

PDMG `26장.예외처리와 표준 오류` 분석은:

```text
exceptionCode.yml 존재
```

와:

```text
Global Handler가 실제 그 Properties를 사용
```

가 동일하지 않다고 지적한다.

즉:

```text
YAML에 코드 등록
≠
모든 Error Response에 그 메시지 자동 적용
```

이다.

반드시:

```text
Throw
→ Handler
→ Resolver
→ Message Source
→ Response
```

전체 연결을 Test해야 한다.

---

# 24. `MessageCache` 구분

`MessageCache`는 `exceptionCode.yml`과 동일 저장소가 아니다.

```text
exceptionCode.yml
= Framework/Common/Auth 성격의 정적 설정 후보

MessageCache
= DB 등에서 기동 시 적재하는 업무 Message Bundle 후보
```

PDMG 분석에서는 Legacy `NhBaseException` SERVICE/BIZ 유형이 MessageCache를 사용할 수 있으나, 새로운 TCF `BizException` Handler가 동일 Cache를 사용한다고 단정할 수 없다.

따라서 하나의 흐름으로 합치지 않는다.

---

# 25. FIG-VI-12 — Success Response Envelope

현재 PDMG 성공 응답:

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "...": "..."
    }
  },
  "dto": {
    "...": "business result"
  }
}
```

Runtime:

```text
Business DTOout
      │
      ▼
Controller Return
      │
      ▼
ResponseBodyArgumentResolver
      │
      ├─ ServiceContext.header
      ├─ Business body
      └─ responseBody 저장
      │
      ▼
hdr_nhnis + dto
```

---

# 26. FIG-VI-13 — Known Error Response Envelope

Current PDMG TCF ON:

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "rms_svc_c": "mgcoa8888S0"
    }
  },
  "result": {
    "stdErrCode": "E9999",
    "stdErrMsgCntn": "등록되지 않은 serviceId입니다.",
    "errType": "SERVICE"
  }
}
```

## 26.1 계약

```text
dto
= 업무 성공 결과

result
= 표준 오류 결과
```

두 키를 혼합하지 않는다.

## 26.2 Legacy 문서와 차이

PDMK/Legacy 일부 문서는:

```text
hdr_nhnis + dto(NH_NIS_ERR_DTO)
```

로 표현한다.

본 PDMG 정의서에서는 현재 Source의:

```text
hdr_nhnis + result(NH_NIS_ERR_DTO)
```

를 사용한다.

---

# 27. `NH_NIS_ERR_DTO` 정보

현재 Source 분석에서 확인되는 주요 의미:

```text
stdErrCode
stdErrMsgCntn
addMsgContents
errType
```

Legacy 변환 코드에는 추가로:

```text
errClassName
errFileName
errMethodName
errLineNo
stackTrace
```

등 기술 상세를 포함할 수 있는 코드가 분석되어 있다.

## 27.1 Security Risk

외부/사용자 Response에:

```text
Class
File
Method
Line
StackTrace
```

를 노출하는 것은 Information Disclosure 위험이다.

`[RISK-VI-01]`

TO-BE:

```text
Client
→ 안정된 Error Code + 안전한 Message + Correlation ID

Internal Log
→ 상세 Stack / Root Cause
```

로 분리하는 것이 바람직하다.

---

# 28. FIG-VI-14 — Filter / Security Early Error Gap

```text
HTTP Request
   │
   ▼
DefaultFilter
   │
   ├─ Body 없음
   ├─ JSON 오류
   ├─ Header 없음
   ├─ JWT 없음
   └─ JWT 무효
        │
        ▼
   response.sendError
   400 / 401
        │
        ▼
MVC 미진입
Controller 미진입
GlobalExceptionHandler 미진입
ResponseBodyAdvice 미진입
        │
        ▼
표준 hdr_nhnis/result 미보장
```

## 28.1 Architecture Gap

Client는 현재 두 오류계약을 이해해야 할 수 있다.

```text
A. 표준 JSON Error
B. Servlet sendError
```

이는 Interface Contract를 복잡하게 한다.

`[GAP-VI-02]`

---

# 29. TO-BE Error Writer 후보

Filter/MVC/TCF 어디서 오류가 나더라도 최소 동일한 JSON Error Envelope를 만들기 위한 공통 Writer 후보:

```text
StandardErrorWriter
  │
  ├─ correlation / guid
  ├─ header available?
  ├─ code
  ├─ safe message
  ├─ error type
  └─ HTTP status
```

경로:

```text
Filter
  └─ StandardErrorWriter

Security EntryPoint
  └─ StandardErrorWriter

ControllerAdvice
  └─ StandardErrorWriter
```

`[PROPOSED]`이며 현재 Source에 이미 존재한다고 쓰지 않는다.

---

# 30. Error Code와 HTTP Status의 이중 계약

Error는 두 차원의 의미가 있다.

```text
HTTP Status
= 프로토콜 의미

stdErrCode
= NSIGHT/PDMG 업무·Framework 의미
```

예:

```text
HTTP 504
+
FW_TIMEOUT

HTTP 503
+
FW_OVERLOADED
```

TO-BE에서는:

```text
400 Invalid Request
401 Unauthenticated
403 Forbidden
404 Not Found
409 Conflict
500 Unexpected System
503 Overload
504 Timeout
```

등 의미를 정의할 수 있지만, 기존 Client 호환성과 현재 업무코드 정책 검증이 필요하다.

---

# 31. 일반 Runtime Exception Gap

현재 PDMG 분석은 포괄적인:

```java
@ExceptionHandler(Exception.class)
```

이 모든 경로에 존재한다고 확인하지 못했다.

따라서:

```text
NPE
MyBatis Exception
DataSource Error
Serialization Error
```

가 Spring Boot 기본 Error 구조로 갈 가능성이 있다.

`[GAP-VI-03]`

TO-BE에서는 예상하지 못한 System Error도:

```text
안전한 FW9999류 Code
+
GUID
+
Internal Root Cause Log
```

로 정규화하는 정책이 필요하다.

---

# 32. Validation Error vs Business Result

모든 업무 “실패”가 Exception이어야 하는 것은 아니다.

예:

```text
검색결과 0건
```

은 정상 업무결과일 수 있다.

반면:

```text
필수 입력 누락
중복 등록 불가
권한 없음
업무상 금지상태
```

는 Error/Reject 계약이 필요하다.

따라서:

```text
No Data
≠
Business Error
≠
System Error
```

를 명확히 한다.

---

# 33. Logging Architecture 개요

PDMG 온라인 거래의 Evidence는 한 채널이 아니다.

```text
1. MDC / ThreadContext
2. Application Transaction Log
3. ImageLog DB
4. SQL Log
5. UI Relay Log [선택 경로]
6. Security/Audit Log [VII/IX에서 확장]
```

각 채널은 목적·수명·저장소가 다르다.

---

# 34. FIG-VI-15 — Logging Channel Map

```text
Browser / UI
   │
   ├─ [optional Relay elapsed/status]
   │
   ▼
PDMG Service
┌──────────────────────────────────────────────────────┐
│                                                      │
│ ① MDC / ThreadContext                                │
│    guid / serviceId / user / ip                      │
│    + Worker 전파                                     │
│                                                      │
│ ② Application Transaction Log                        │
│    Interceptor / Aspect / Controller / Service       │
│                                                      │
│ ③ ImageLog DB                                        │
│    PRE / POST / EX                                   │
│    TB_FW_IMAGE_LOG                                   │
│                                                      │
│ ④ SQL Log                                            │
│    sqlId / params / elapsed 후보                     │
│                                                      │
└──────────────────────────────────────────────────────┘
```

## 34.1 목적 구분

| Channel | 목적 |
|---|---|
| MDC | 로그 상관관계 |
| App Log | 시간순 실행/진단 |
| ImageLog | 요청·응답/오류 감사 Snapshot |
| SQL Log | SQL ID/파라미터/소요 |
| Relay | UI 중계구간 HTTP 진단 |
| Security Audit | 로그인/권한/Token 이벤트 |

---

# 35. MDC / ThreadContext

현재 DefaultFilter/Interceptor가 다음 Key들을 주로 다룬다.

```text
guid
ip
userId
serviceId
```

SQL Logging에서:

```text
sqlId
```

를 추가할 수 있는 분석자료가 있다.

## 35.1 Worker 전파

V장에서 확인:

```text
Request MDC
   ↓ capture
Worker MDC
   ↓ business/sql log
finally clear
```

## 35.2 로그 패턴 가치

한 Log Event가 최소:

```text
timestamp
level
thread
guid
serviceId
logger
message
```

를 가지면 거래 추적성이 높아진다.

정확한 Log4j2 Pattern/파일명/보존기간은 IX장에서 Source/Config로 확정한다.

---

# 36. Application Transaction Log

현재 로그는 시스템 선후와 업무 선후 등 여러 계층에서 발생한다.

개념적 시간순서:

```text
SystemPre Start
GUID
Business Pre
Controller / TCF Start
Service Start
SQL
Service End
Business Post
System Post
```

TCF ON에서는 업무별 Controller 로그 대신 Handler/TCF 로그가 중심이 될 수 있으므로 과거 PDMK 로그 문구를 PDMG Current 표준 메시지라고 자동 승격하지 않는다.

핵심은 **메시지 문자열이 아니라 GUID/ServiceId 기반 단계 추적**이다.

---

# 37. SQL Logging

SQL Log의 목표:

```text
GUID
ServiceId
SQL ID
elapsed
row count
error
```

를 연결하는 것이다.

## 37.1 민감정보 주의

SQL Parameter에:

```text
주민번호
계좌번호
고객번호
전화번호
Token
```

등이 포함될 수 있다.

따라서:

```text
모든 SQL Param Full Dump
```

를 표준으로 삼지 않는다.

Parameter Masking/Allowlist 정책이 필요하다.

---

# 38. FIG-VI-16 — ImageLog PRE / POST / EX Lifecycle

## 38.1 PRE

```text
DefaultFilter
  ↓ Context/Header/Request
Interceptor.preHandle
  ↓
ImageLogHandler.preImagelog
  ↓
TB_FW_IMAGE_LOG
  ├─ GUID
  ├─ SERVICE_ID
  ├─ SCREEN_ID
  ├─ OPTR_ENO
  ├─ CLIENT_IP
  ├─ REQUEST_TIME
  └─ REQUEST_MSG [컬럼 지원 시]
```

## 38.2 POST

```text
Business 정상
  ↓
ResponseBodyAdvice
  ↓
ServiceContext.responseBody
  ↓
afterCompletion(ex=null)
  ↓
postImagelog
  ↓
UPDATE by GUID
  ├─ RESPONSE_TIME
  └─ RESPONSE_MSG
```

## 38.3 EX

```text
Known Error Response / Exception
  ↓
afterCompletion
  ↓
exceptionImagelog
  ↓
UPDATE / 필요 시 INSERT
  ├─ EXCEPTION_TYPE
  ├─ EXCEPTION_CODE
  └─ EXCEPTION_MSG
```

---

# 39. `TB_FW_IMAGE_LOG` 핵심 의미

PDMG 분석에서 확인되는 주요 컬럼군:

```text
GUID
SERVICE_ID
SCREEN_ID
OPTR_ENO
CLIENT_IP
REQUEST_TIME
RESPONSE_TIME
EXCEPTION_TYPE
EXCEPTION_CODE
EXCEPTION_MSG
REQUEST_MSG
RESPONSE_MSG [환경/스키마 지원 시]
```

## 39.1 목적

ImageLog는:

```text
업무 원장
```

이 아니다.

정확한 목적:

```text
한 거래가
무엇을 요청했고
어느 ServiceId였고
누가/어디서 호출했고
무엇으로 끝났는지
추적하는 운영/감사 Evidence
```

---

# 40. ImageLog Row와 GUID

분석자료 Local/H2 기준 GUID가 PK로 설정된 사례가 있다.

그러나 Architecture적으로:

```text
GUID = PK
```

를 전 운영환경 절대계약으로 자동 승격하기 전 다음을 검토해야 한다.

```text
Client GUID 재사용 가능성
Retry
동일 업무 재시도
하위 Call
Child Correlation
```

`[OPEN-VI-02]`

---

# 41. ImageLog Runtime DDL Risk

PDMG 분석에서는 ImageLogHandler가 `REQUEST_MSG`, `RESPONSE_MSG` 컬럼 존재를 확인하고 없으면 추가하려는 코드가 언급된다.

운영환경에서 Framework Application이 런타임 DDL을 수행한다면:

```text
Schema Change Control
DB 권한
배포 승인
Rollback
감사
```

와 충돌할 수 있다.

`[RISK-VI-02]`

TO-BE 후보:

```text
DDL은 Release Migration
Runtime은 Schema Validation만
```

---

# 42. ImageLog Fail-open

ImageLog 저장 실패가 업무요청을 실패시키지 않도록 예외를 삼키는 설계가 분석되어 있다.

장점:

```text
Audit DB 문제
   ↓
본거래 장애 전파 방지
```

위험:

```text
Audit DB 문제
   ↓
거래 성공
   ↓
ImageLog 없음
```

따라서 Fail-open이면 반드시:

```text
ImageLog failure metric
Alert
Retry/Recovery
Daily reconciliation
```

중 적절한 운영통제가 필요하다.

현재 구현의 실제 재처리 정책은 `[GAP]`이다.

---

# 43. FIG-VI-17 — Business TX vs ImageLog Evidence

```text
Request Thread
   ↓
ImageLog PRE
   │
   │ 업무 TX 밖 / 독립 가능
   ▼

Worker Thread
┌────────── Business Transaction ─────────┐
│                                        │
│ Handler                                │
│ Facade                                 │
│ Service                                │
│ DAO / SQL                              │
│                                        │
│ COMMIT or ROLLBACK                     │
└───────────────────┬────────────────────┘
                    │
                    ▼
Request Thread
   ↓
Response / Error
   ↓
ImageLog POST / EX
```

## 43.1 가능한 조합

| Business | ImageLog | 의미 |
|---|---|---|
| Commit | Success | 정상 |
| Rollback | EX Success | 정상 실패추적 |
| Commit | Log Fail | **감사 공백** |
| Rollback | Log Fail | **업무실패+감사공백** |

따라서:

```text
Business Outcome
와
Evidence Outcome
```

을 별도 Metric으로 관리해야 한다.

---

# 44. ImageLog의 비대칭 복구

PDMG 분석에 따르면:

```text
정상 POST UPDATE 0건
→ WARN

EX UPDATE 0건
→ INSERT fallback 가능
```

처럼 정상/예외의 Recovery가 비대칭일 수 있다.

이 구조는:

```text
PRE 실패
+
정상 업무
=
최종 정상 ImageLog가 없을 가능성
```

을 만든다.

`[RISK-VI-03]`

TO-BE에서는 정상 Post도 필요한 경우 Upsert/Recovery 정책을 검토한다.

---

# 45. Request / Response 원문 저장 Risk

ServiceContext와 ImageLog가 요청/응답 원문을 저장한다.

장점:

```text
장애 재현
전문 비교
고객문의 분석
```

위험:

```text
Memory 증가
DB CLOB 증가
개인정보 저장
Token 노출
중복 저장
Retention 비용
```

따라서 전문 원문 저장은 반드시:

```text
Max Size
Masking
Field Exclusion
Retention
Access Control
Encryption
```

정책과 함께 설계해야 한다.

현재 전체 정책은 미확정이다.

---

# 46. FIG-VI-18 — Sensitive Data / Log Boundary

```text
Request
  │
  ├─ Authorization: Bearer <token>      [절대 원문 로그 금지]
  │
  ├─ hdr_nhnis
  │    ├─ userId
  │    ├─ branch
  │    └─ IP
  │
  └─ dto
       ├─ customer data
       ├─ account data
       └─ business sensitive fields
            │
            ▼
      Logging Boundary
            │
   ┌────────┼───────────┐
   ▼        ▼           ▼
MDC      App Log     ImageLog
최소키    필요한 값    전문 Snapshot
   │        │           │
   └────────┴─────┬─────┘
                  ▼
              Mask / Drop
```

## 46.1 반드시 로그 금지 또는 강한 통제 대상

```text
JWT Access Token 원문
Refresh Token 원문
Private Key / Secret
비밀번호
전체 Authorization Header
민감 개인정보 원문
```

정확한 금융/개인정보 필드 마스킹 규칙은 VII/보안표준과 연계한다.

---

# 47. Error Response와 내부 Log를 분리한다

Client에게 필요한 것:

```text
Error Code
Safe Message
Error Type
GUID/Correlation
```

내부 운영에 필요한 것:

```text
Exception Class
Stack Trace
SQL Error
Source Line
Root Cause
Thread
```

따라서:

```text
Internal Detail
→ Log / Trace

External Contract
→ Safe Error DTO
```

로 분리한다.

---

# 48. StackTrace 외부 노출 Risk

Legacy Error DTO 코드에서 Stack Trace 상위 일부를 Response에 실을 수 있는 구조가 분석된다.

이는 다음 정보를 노출할 수 있다.

```text
Package
Class
Method
File
Line
Library
Internal Structure
```

`[RISK-VI-04]`

Production Target에서는 Error Response에서 제거하고 GUID로 내부 로그를 조회하는 것이 적절한 후보이다.

---

# 49. Message Internationalization / Locale

`sys_comm`에는 locale 관련 필드가 있을 수 있고 Error Message는 Message Source/Cache에서 해석될 수 있다.

그러나 현재 PDMG Source Evidence만으로:

```text
전사 다국어 Message 정책
KO/EN fallback
Channel별 locale
```

를 확정하지 않는다.

`[OPEN-VI-03]`

---

# 50. Error Type / Code Namespace

현재 관찰되는 예:

```text
FW_TIMEOUT
FW_OVERLOADED
E9999
업무 BizException Code
```

TO-BE에서는 최소:

```text
FW / SECURITY / SERVICE / BIZ / DATA / EXTERNAL
```

처럼 Owner를 식별할 Namespace 정책을 검토할 수 있다.

하지만 현재 공식 전체 Code Prefix 표준은 본 장 Evidence에 완전히 확보되지 않았다.

`[OPEN-VI-04]`

---

# 51. Error Code Stability Rule

Error Code는 Client와 운영도구가 의존할 계약이다.

금지:

```text
동일한 업무조건
→ 배포마다 다른 코드

동일 코드
→ 서비스마다 다른 의미
```

권장:

```text
Code
→ Stable Meaning
→ Owner
→ HTTP Mapping
→ Message Source
→ Retryability
→ Severity
```

이 Metadata를 Error Catalog로 관리하는 방향을 제안한다.

---

# 52. Retryability와 Error Contract

Error Contract에는 향후 다음 속성이 필요할 수 있다.

```text
retryable
idempotent_required
severity
alert_required
client_action
```

현재 `NH_NIS_ERR_DTO`에 이 필드가 존재한다고 단정하지 않는다.

이는 `[PROPOSED]` Error Catalog Metadata다.

---

# 53. Transaction Outcome과 Error의 연결

V장의 결과를 VI에서 다음처럼 해석한다.

```text
COMMIT
  ↓
Success Response

ROLLBACK + Known Business Exception
  ↓
Known Error Response

ROLLBACK + Timeout
  ↓
FW_TIMEOUT / 504

TX 미시작 + Overload
  ↓
FW_OVERLOADED / 503

TX 미시작 + Filter reject
  ↓
400/401 Early Error

Unknown Runtime
  ↓
현재 표준화 Gap
```

---

# 54. Error Response가 DB Outcome을 증명하는가

주의:

```text
HTTP Error
≠
DB Rollback 증거
```

V장에서 Timeout의 경우 HTTP 504와 Worker Rollback 시점이 다를 수 있음을 확인했다.

따라서 운영 분석은:

```text
HTTP Result
+
Worker Transaction Outcome
+
ImageLog
+
SQL/DB Evidence
```

를 함께 봐야 한다.

---

# 55. 한 거래의 End-to-End Evidence Timeline

```text
T0
Client Request
GUID=G1
ServiceId=S1
   ↓
T1
DefaultFilter
MDC(G1,S1)
   ↓
T2
Pre ImageLog
GUID=G1
   ↓
T3
TCF / Worker
MDC(G1,S1)
   ↓
T4
Business / SQL
sqlId=Q1
   ↓
T5
Commit or Rollback
   ↓
T6
Response
Success/Error
   ↓
T7
Post/EX ImageLog
GUID=G1
```

이 Timeline을 자동 재구성할 수 있어야 Observability가 닫힌다.

---

# 56. Application Log와 ImageLog의 차이

| 항목 | Application Log | ImageLog |
|---|---|---|
| 저장 | 파일/로그 플랫폼 | DB |
| 목적 | 기술 실행/진단 | 거래 전문/감사 |
| 구조 | Event Line | 거래 Row |
| 검색키 | GUID, ServiceId, Thread | GUID, ServiceId |
| 요청원문 | 선택 | 저장 가능 |
| 응답원문 | 선택 | 저장 가능 |
| Stack | 가능 | 예외컬럼 |
| Business TX | 별도 | 별도 가능 |
| Retention | 운영정책 | 감사정책 |

둘 중 하나가 다른 하나를 완전히 대체하지 않는다.

---

# 57. SQL Log와 Business Log의 차이

Business Log:

```text
왜 이 유스케이스가 실행됐는가
어떤 단계인가
업무 결과가 무엇인가
```

SQL Log:

```text
어떤 Mapper/SQL이
얼마나 걸렸고
어떤 결과/오류였는가
```

SQL Text/Parameter 전체를 Application Log에 무분별하게 중복하지 않는다.

---

# 58. ImageLog와 Audit Log의 차이

현재 `TB_FW_IMAGE_LOG`는 감사 Evidence 역할을 하지만:

```text
Security Audit
Authorization Change
Login/Logout
Token Issue/Refresh
Admin Action
Config Change
```

전체를 포함하는 전사 Audit Log와 동일하다고 단정하지 않는다.

VII/IX에서 Security/Administrative Audit를 별도 확장한다.

---

# 59. Log Level

현재 Framework/Service는 다양한 로그 레벨을 사용할 수 있으나 본 장 Evidence만으로 운영 동적 Log Level 정책 전체가 확정되지 않는다.

TO-BE에서는:

```text
ERROR
WARN
INFO
DEBUG
TRACE
```

에 대해:

```text
Production 기본레벨
동적 변경
자동복구
변경 감사
민감정보
성능영향
```

정책이 필요하다.

`[OPEN-VI-05]`

---

# 60. Error / Log Governance

각 Error/Log 자산은 Owner를 가져야 한다.

| 자산 | Owner 후보 |
|---|---|
| Standard Envelope | Framework Architecture |
| `sys_comm` | Framework + Interface Standard |
| Business DTO | Application Team |
| Error Code Catalog | Framework + Business Governance |
| Framework Message | Framework |
| Business Message | Business |
| ImageLog Schema | Framework + DBA/Operations |
| Log Pattern | Framework/Operations |
| SQL Log | Application/DBA |
| Security Audit | Security |

Owner는 현재 공식 RACI로 확정된 것이 아니라 TO-BE Governance 후보다.

---

# 61. FIG-VI-19 — Failure / Risk Map

```text
[Message]
URL ServiceId != Header ServiceId
   → 다른 거래 실행 Risk

[Context]
GUID/Header/MDC 불일치
   → Trace 단절

[Resolver]
TCF Map Controller vs dto Resolver 충돌
   → Binding 불명확

[Error]
Filter sendError
   → Standard Envelope 우회

[Error]
Unknown Runtime Exception
   → Spring 기본 /error 가능

[Advice]
복수 Advice
   → Handler 우선순위 Drift

[Message]
YAML 등록
   → 실제 Handler 미연결
   → 기대 Message 미출력

[Security]
StackTrace 외부 노출
   → Information Disclosure

[ImageLog]
Fail-open
   → 감사 공백

[ImageLog]
Runtime DDL
   → Schema Change Risk

[Privacy]
Request/Response Full Dump
   → 개인정보/Token 노출

[Trace]
GUID 재사용
   → 거래 시도 혼동
```

---

# 62. 현재 GAP

| ID | GAP | 영향 |
|---|---|---|
| GAP-VI-01 | `RequestBodyArgumentResolver`와 TCF 공통 Map Controller의 역할 충돌 가능성 | DTO Binding |
| GAP-VI-02 | Filter/Security Early Error가 표준 Envelope를 우회 | Client Contract |
| GAP-VI-03 | 포괄 System Exception 표준화 미완성 | 오류 일관성 |
| GAP-VI-04 | BizException/Legacy NhBaseException/Filter Error가 서로 다른 Error Path | Error Governance |
| GAP-VI-05 | exceptionCode.yml과 실제 TCF Handler Message 연결이 완전히 통일되지 않음 | Message 일관성 |
| GAP-VI-06 | MessageCache와 TCF BizException 연결정책 불명확 | 업무 Message |
| GAP-VI-07 | Error Code↔HTTP Status 중앙정책 미확정 | Client/Monitoring |
| GAP-VI-08 | StackTrace/내부소스정보 Response 노출 제거정책 미확정 | Security |
| GAP-VI-09 | Request/Response 전문 Masking/Size/Retention 정책 미확정 | Privacy/Capacity |
| GAP-VI-10 | ImageLog fail-open에 대한 Alert/Recovery/Reconciliation 미확정 | Audit |
| GAP-VI-11 | ImageLog 정상 POST 0-row 복구정책 비대칭 | Audit Completeness |
| GAP-VI-12 | Runtime DDL의 운영 허용여부 미확정 | DB Governance |
| GAP-VI-13 | GUID 재사용/Uniqueness 정책 미확정 | Trace |
| GAP-VI-14 | SQL Parameter Masking/Logging 정책 미확정 | Privacy |
| GAP-VI-15 | 동적 Log Level 운영정책 미확정 | Operations |
| GAP-VI-16 | Security Audit와 ImageLog의 책임경계 미확정 | VII/IX |

---

# 63. Current RISK

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-VI-01 | Error DTO에 Stack/Class/File/Line 노출 | High |
| RISK-VI-02 | Framework Runtime DDL | High |
| RISK-VI-03 | 정상 ImageLog 복구 비대칭 | Medium/High |
| RISK-VI-04 | Filter 오류 Envelope 없음 | High |
| RISK-VI-05 | Advice 중첩/우선순위 Drift | High |
| RISK-VI-06 | Unknown Runtime Exception 비표준 응답 | High |
| RISK-VI-07 | Header 사용자정보를 인증정보로 신뢰 | Critical |
| RISK-VI-08 | 전문 Full Dump 개인정보/Token 노출 | Critical |
| RISK-VI-09 | GUID/Header/MDC Drift | High |
| RISK-VI-10 | Message Source 미연결 | Medium |
| RISK-VI-11 | ImageLog 실패 미탐지 | High |
| RISK-VI-12 | ServiceId URL/Header 불일치 | High |
| RISK-VI-13 | SQL Parameter 원문로그 | High |
| RISK-VI-14 | ServiceContext를 Business Data Bag으로 사용 | Medium/High |

---

# 64. OPEN Issue

| ID | 질문 |
|---|---|
| OPEN-VI-01 | GUID는 Client 생성 허용인가 Server 생성 중심인가, 재사용을 허용하는가 |
| OPEN-VI-02 | ImageLog GUID PK 정책을 운영에서도 유지할 것인가 거래시도 ID를 별도로 둘 것인가 |
| OPEN-VI-03 | Error Message Locale 정책은 무엇인가 |
| OPEN-VI-04 | Error Code Namespace와 Owner는 어떻게 정할 것인가 |
| OPEN-VI-05 | 동적 Log Level은 어떤 Tool/권한/감사를 사용할 것인가 |
| OPEN-VI-06 | Filter/Security 오류를 표준 Error Writer로 통합할 것인가 |
| OPEN-VI-07 | Legacy `NhBaseException`을 유지할 것인가 `BizException` 체계로 통합할 것인가 |
| OPEN-VI-08 | MessageCache를 신규 업무 Error에도 사용할 것인가 |
| OPEN-VI-09 | Request/Response ImageLog에 저장 가능한 최대 Payload는 얼마인가 |
| OPEN-VI-10 | 민감정보 Field Masking을 DTO Annotation 기반으로 할 것인가 중앙 Policy로 할 것인가 |
| OPEN-VI-11 | SQL Parameter Logging을 운영에서 허용할 것인가 |
| OPEN-VI-12 | ImageLog Runtime DDL을 제거할 것인가 |
| OPEN-VI-13 | ImageLog fail-open 시 Recovery SLA는 무엇인가 |
| OPEN-VI-14 | Standard Error DTO에 GUID/Correlation ID를 명시적으로 넣을 것인가 |

---

# 65. ADR 후보

| ADR | 결정 주제 |
|---|---|
| ADR-VI-01 | PDMG 표준 Error Envelope 전 구간 통일 |
| ADR-VI-02 | URL/Header ServiceId Single Source of Truth |
| ADR-VI-03 | `BizException` vs `NhBaseException` Error Model 통합 |
| ADR-VI-04 | Error Code ↔ HTTP Status 중앙정책 |
| ADR-VI-05 | `exceptionCode.yml` / MessageCache 역할분리 |
| ADR-VI-06 | Error DTO StackTrace 외부 제거 |
| ADR-VI-07 | StandardErrorWriter 도입 |
| ADR-VI-08 | ImageLog Runtime DDL 제거 |
| ADR-VI-09 | ImageLog Fail-open Alert/Recovery |
| ADR-VI-10 | Request/Response 전문 Masking/Retention |
| ADR-VI-11 | GUID 생성/유일성/Retry 정책 |
| ADR-VI-12 | ServiceContext typed UserContext |
| ADR-VI-13 | RequestBody Resolver 전용 Annotation |
| ADR-VI-14 | SQL Parameter Log Policy |
| ADR-VI-15 | Error Response에 Correlation ID 노출 |

---

# 66. TO-BE Standard Message Contract 후보

```text
Request
{
  hdr_nhnis : StandardHeader,
  dto       : BusinessRequest
}

Success
{
  hdr_nhnis : StandardHeader,
  dto       : BusinessResponse
}

Failure
{
  hdr_nhnis : StandardHeader,
  result    : StandardError
}
```

## 66.1 StandardError 후보 Metadata

현재 필드의 안정화 + 필요 시 별도 Catalog:

```text
code
message
type
correlationId
```

다음과 같은 내부 상세는 Response에서 제외 후보:

```text
stackTrace
className
fileName
methodName
lineNo
```

---

# 67. TO-BE Error Pipeline 후보

```text
Transport / Security / MVC / TCF / Business / Data
                  │
                  ▼
           Exception Classifier
                  │
                  ▼
             Error Catalog
      code / type / HTTP / retryability
                  │
                  ▼
            Message Resolver
                  │
                  ▼
          StandardErrorWriter
                  │
                  ▼
       hdr_nhnis + result + GUID
                  │
                  ├─ Client Response
                  ├─ Application Log
                  └─ ImageLog / Audit
```

`[PROPOSED]`이며 현재 AS-IS 구조가 아니다.

---

# 68. TO-BE Trace Contract 후보

한 거래의 최소 Trace Set:

```text
GUID
ServiceId
Application
Environment
Thread
User/Channel [안전한 식별]
SQL ID
External Interface ID
Error Code
Duration
Transaction Outcome
```

모든 값이 하나의 Log Event에 있을 필요는 없다.

GUID/ServiceId로 Join 가능하면 된다.

---

# 69. Privacy / Security Logging Rule 후보

## Must Not Log

```text
Authorization Bearer 원문
Access Token 원문
Refresh Token 원문
Password
Private Key
Secret
Full 주민/개인 식별정보
```

## Mask / Hash / Partial

```text
계좌번호
전화번호
고객식별자
이메일
IP [정책에 따라]
업무 민감필드
```

정확한 마스킹 규칙은 은행보안 기준과 VII장 결정을 따른다.

---

# 70. Runtime Test Scenario

## 70.1 Normal Request

```text
Given
  hdr_nhnis + dto
Expect
  Context header 생성
  GUID 일치
  Business dto 처리
  hdr_nhnis + dto response
  PRE/POST ImageLog
```

## 70.2 Non-local Missing Header

```text
Given
  dto only
Expect
  400
  Business 미진입
  현재는 표준 result 미보장
```

## 70.3 Invalid JWT

```text
Expect
  401
  Controller 미진입
  Token 원문 Log 없음
```

## 70.4 Unknown ServiceId

```text
Expect
  Error Code
  hdr_nhnis + result
  GUID 유지
  ImageLog EX
```

## 70.5 BizException

```text
Expect
  rollback
  safe message
  error type
  result
```

## 70.6 Runtime Exception

```text
Expect TO-BE
  Standard system error
  no stack external
  internal stack with GUID
```

현재 Source가 이 계약을 완전히 보장하는지는 GAP.

## 70.7 Timeout

```text
Expect
  HTTP 504
  FW_TIMEOUT
  GUID same
  Worker rollback evidence
  ImageLog final error
```

## 70.8 ImageLog Failure

```text
Business success
ImageLog DB failure
Expect
  Business success 유지 [현행 fail-open]
  Alert/Metric 필수 [TO-BE]
```

## 70.9 Context Leak

```text
Thread reuse
Expect
  이전 GUID/User 없음
```

## 70.10 Sensitive Field

```text
Request contains sensitive data
Expect
  Application/ImageLog/SQL Log masking policy 적용
```

---

# 71. Architecture Conformance Rule 후보

```text
RULE-VI-01
Online request root must contain dto

RULE-VI-02
Non-local request must contain hdr_nhnis.sys_comm

RULE-VI-03
Path ServiceId and Header ServiceId must match when both exist

RULE-VI-04
Business DAO must not accept full Standard Request envelope

RULE-VI-05
Response success must use dto

RULE-VI-06
Known error must use result

RULE-VI-07
Production response must not expose stackTrace/class/file/line

RULE-VI-08
Authorization header/token must never be logged

RULE-VI-09
ServiceContext must be cleared at request completion

RULE-VI-10
Worker MDC must be cleared

RULE-VI-11
GUID must be present in Application/ImageLog

RULE-VI-12
Every Error Code must exist in Error Catalog

RULE-VI-13
Every Error Code must have HTTP mapping

RULE-VI-14
ImageLog failure must generate monitoring signal

RULE-VI-15
Runtime Schema DDL from application is prohibited unless explicitly approved
```

---

# 72. Logging / Error Operation Metrics 후보

IX장으로 넘길 최소 Metric:

```text
standard_error_count{code,type,http}
filter_reject_count{reason}
security_reject_count
unknown_exception_count
error_envelope_bypass_count
image_log_pre_fail_count
image_log_post_fail_count
image_log_ex_fail_count
image_log_update_zero_count
guid_missing_count
guid_mismatch_count
service_id_mismatch_count
sensitive_log_violation_count
sql_slow_count
```

현재 `pdmg-om`에 이미 구현되어 있다고 단정하지 않는다.

---

# 73. Traceability

## 73.1 Runtime → Contract

```text
IV Runtime
DefaultFilter
      ↓
VI Header / Context

IV Controller
      ↓
VI ServiceId / dto

IV Response Advice
      ↓
VI Success/Error Envelope

IV ImageLog
      ↓
VI Audit Evidence
```

## 73.2 Transaction → Error

```text
V COMMIT
   ↓
VI dto success

V ROLLBACK
   ↓
VI result error

V Timeout
   ↓
VI FW_TIMEOUT / 504

V Overload
   ↓
VI FW_OVERLOADED / 503
```

## 73.3 VI → VII

```text
Header user information
         ↓
실제 인증근거인가?
         ↓
JWT / SSO

Filter JWT reject
         ↓
Access/Refresh/JWKS

Log Masking
         ↓
Token / User / Secret Protection
```

---

# 74. FIG-VI-20 — VII장 Handoff

```text
VI. Standard Message / Context / Error / Logging
        │
        ├─ hdr_nhnis / sys_comm
        ├─ dto / result
        ├─ ServiceContext
        ├─ GUID / MDC
        ├─ Validation
        ├─ Exception / Error Code
        ├─ Message Source
        ├─ ImageLog
        ├─ SQL Log
        └─ Sensitive Log Boundary
                 │
                 ▼
VII. Security / SSO / JWT / Session
                 │
                 ├─ Authentication
                 ├─ Access Token
                 ├─ Refresh Token
                 ├─ RS256 / JWKS
                 ├─ SSO
                 ├─ Authorization
                 ├─ Session
                 ├─ Logout / Revocation
                 ├─ HMAC Internal Call
                 ├─ Key / Secret
                 └─ Security Audit
```

## VII장에서 반드시 답할 질문

1. Header의 `optr_eno`와 JWT Subject/Claim은 어떻게 연결되는가?
2. Header 사용자값을 Client가 조작해도 권한이 오르지 않도록 어디에서 검증하는가?
3. `pdmg-jwt`는 Access/Refresh Token을 어떻게 발급하는가?
4. Access Token은 어떤 알고리즘/Key로 서명하는가?
5. 검증은 `pdmg-service`, Gateway, Filter 중 어디에서 하는가?
6. JWKS/Public Key는 어떻게 배포하는가?
7. Refresh Token은 평문인가 Hash인가, 어디에 저장하는가?
8. SSO 발급경로는 외부 IdP 검증을 어디에서 하는가?
9. 내부 호출의 HMAC/Timestamp/IP Allowlist는 무엇을 보호하는가?
10. Authentication과 Business Authorization은 어디에서 나뉘는가?
11. Logout/Token Revocation/Session 만료는 어떻게 연계되는가?
12. Token/Key/Secret이 Log/ImageLog에 남지 않도록 어떻게 통제하는가?

---

# 75. Architecture Rules

## 75.1 Must

1. PDMG 성공 응답은 `hdr_nhnis + dto`로 표현한다.
2. PDMG Current TCF known error는 `hdr_nhnis + result`로 표현한다.
3. PDMK Legacy 오류 `dto` 구조를 PDMG Current 계약으로 쓰지 않는다.
4. `hdr_nhnis`와 Business `dto` 책임을 분리한다.
5. DAO에 전체 전문을 전달하지 않는다.
6. Header를 단순 Echo라고 설명하지 않는다.
7. ServiceContext/TransactionContext/MDC를 DB Transaction으로 설명하지 않는다.
8. GUID를 Authentication ID나 업무 PK로 설명하지 않는다.
9. Filter `sendError`가 표준 result를 보장한다고 설명하지 않는다.
10. Source에 없는 Message 연결을 자동으로 가정하지 않는다.
11. Client Response에 StackTrace/내부 Source정보를 노출하지 않는 방향으로 설계한다.
12. Token/Secret/Authorization 원문을 Log하지 않는다.
13. ImageLog 성공과 Business Commit 성공을 동일시하지 않는다.
14. ImageLog 실패를 무조건 본거래 실패로 전파하지 않는다. 현재 Fail-open 정책과 TO-BE 운영보상을 구분한다.
15. 전문 원문 저장 시 Masking/Size/Retention을 함께 정의한다.

## 75.2 Should

1. Filter/Security/MVC/TCF Error를 단일 Standard Error Contract로 정렬한다.
2. Error Code/HTTP/Message/Retryability Catalog를 SSOT로 관리한다.
3. GUID를 Response Error에 명시적으로 제공해 운영 검색을 쉽게 한다.
4. ServiceContext userContext를 typed immutable 객체로 개선한다.
5. Request Body Resolver 의도를 전용 Annotation으로 명확히 한다.
6. ImageLog Runtime DDL을 배포 Migration으로 이동한다.
7. ImageLog Failure Metric/Alert/Reconciliation을 구현한다.
8. SQL Parameter Masking을 중앙정책화한다.
9. Production Error Response에서 내부 기술정보를 제거한다.
10. Application/MDC/ImageLog/SQL을 GUID+ServiceId로 Join 가능하게 한다.

---

# 76. 검증 체크리스트

## 76.1 Message

- [x] Request가 hdr_nhnis + dto인가
- [x] Header와 DTO Owner가 분리되는가
- [x] local synthetic Header를 운영표준으로 오해하지 않았는가
- [x] Header가 처리 중 보강됨을 표시했는가
- [x] PDMG 실패 result 구조를 사용했는가

## 76.2 Context

- [x] ServiceContext 필드가 표현되는가
- [x] TransactionContext와 차이를 설명했는가
- [x] MDC와 Context를 구분했는가
- [x] GUID가 Thread 전환 후 이어지는가
- [x] Context가 업무 데이터 저장소가 아님을 명시했는가

## 76.3 Error

- [x] Filter/MVC/TCF/Biz/System/Data 실패를 분리했는가
- [x] Timeout 504 / Overload 503을 연결했는가
- [x] Filter 400/401 Envelope Gap가 있는가
- [x] Unknown Exception Gap가 있는가
- [x] exceptionCode.yml 존재=자동적용으로 쓰지 않았는가
- [x] Legacy NhBaseException과 BizException을 구분했는가

## 76.4 Logging

- [x] MDC/App/ImageLog/SQL을 분리했는가
- [x] PRE/POST/EX가 표현되는가
- [x] Business TX와 ImageLog TX가 분리되는가
- [x] Fail-open Audit Gap가 있는가
- [x] 민감정보 Log Boundary가 있는가

## 76.5 Handoff

- [x] Header User→JWT Trust 질문을 VII장으로 넘겼는가
- [x] Token/Secret Logging을 VII장으로 넘겼는가
- [x] Security Audit를 VII/IX로 넘겼는가

---

# 77. Completion Gate

```text
Figure Plan                     20
실제 Text Figure               20

Standard Message Big Picture   PASS
Request Envelope               PASS
sys_comm Field Map             PASS
Header Lifecycle               PASS
DTO Binding Boundary           PASS
ServiceContext                 PASS
Context 비교                   PASS
GUID Trace                     PASS
Validation Layer               PASS
Exception Taxonomy             PASS
Message/Error Mapping          CONDITIONAL
Success Envelope               PASS
Error Envelope                 PASS
Early Error Gap                PASS
Logging Channels               PASS
ImageLog Lifecycle             PASS
Business vs Audit TX           PASS
Sensitive Log Boundary         PASS
Failure/Risk                   PASS
VII Handoff                    PASS

PDMK Legacy를 PDMG Current로 혼합   0건
Filter 오류를 표준 result로 오기재   0건
GUID를 인증ID로 오기재               0건
Source 없는 Message 적용 가정        0건
```

**판정: CONDITIONAL PASS**

## PASS 전환 조건

```text
Condition-VI-01
PDMG Current Source 기준
GlobalExceptionHandler / Legacy Advice 전체 Handler Matrix 기계적 재스캔

Condition-VI-02
exceptionCode.yml / MessageCache / BizException Message 연결 Runtime Test

Condition-VI-03
Filter/Security Error Envelope 정책 ADR 확정

Condition-VI-04
Error Code↔HTTP Status 정책 확정

Condition-VI-05
Production Error DTO StackTrace 제거 정책 확정

Condition-VI-06
ImageLog Runtime DDL 운영정책 확정

Condition-VI-07
ImageLog Fail-open Alert/Recovery 구현 증적

Condition-VI-08
Request/Response/SQL Logging Masking·Retention 정책 확정

Condition-VI-09
GUID 생성/유일성/Retry 정책 확정

Condition-VI-10
VII장에서 JWT Claim과 Header User Trust 연결 검증
```

---

# 78. 장 최종 평가

VI장은 PDMG의 실행결과를 **표준전문, Context, Error, Log, Audit Evidence**로 연결했다.

핵심은 다음과 같다.

> **PDMG의 Request 계약은 `hdr_nhnis + dto`이고, 시스템 공통정보와 업무정보를 분리한다.**

> **Header는 단순 Echo가 아니라 Filter/Interceptor/Controller에서 보강된 현재 ServiceContext의 Header가 Response에 사용된다.**

> **ServiceContext는 HTTP Request 범위의 Framework 공유 Context이고 TransactionContext는 TCF 실행정보이며, 둘 다 DB Transaction이 아니다.**

> **GUID(`std_gbl_id`)는 Header, ServiceContext, MDC, Worker, ImageLog를 연결하는 핵심 Correlation Key다.**

> **현재 성공은 `hdr_nhnis + dto`, 알려진 TCF 실패는 `hdr_nhnis + result`다. Filter/Security 조기 실패는 이 표준 Envelope를 우회할 수 있다.**

> **현재 Error Architecture는 BizException/Global Handler, Legacy NhBaseException, Filter sendError가 공존하고 Message Source 연결도 완전히 단일화되지 않았으므로 중앙 Error Contract가 아직 닫혀 있지 않다.**

> **ImageLog는 `TB_FW_IMAGE_LOG`에 PRE/POST/EX 증적을 남기며 업무 Transaction과 분리될 수 있다. 이는 본거래 가용성에는 유리하지만 Audit Gap을 운영적으로 보상해야 한다.**

> **추적성을 위해 전문과 SQL을 모두 기록하는 것이 목표가 아니라, GUID+ServiceId로 필요한 Evidence를 연결하면서 개인정보·Token·Secret을 최소화하는 것이 목표다.**

다음 VII장에서는 전문 Header에 있는 사용자정보와 실제 인증 근거를 연결하고, `pdmg-jwt`를 Source 수준으로 확대한다.

```text
VI
Message / Context / Error / Log
        ↓
VII
Authentication / SSO / JWT / Session / Authorization / Key
```
