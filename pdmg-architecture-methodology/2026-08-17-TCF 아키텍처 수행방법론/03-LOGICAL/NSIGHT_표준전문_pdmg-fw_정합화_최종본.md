# NSIGHT 표준전문 ↔ pdmg-fw 전문 정합화 최종본

## 1. 목적

본 문서는 은행/상호 계정계 표준전문 Header의 의미를 **현재 pdmg-fw 실제 전문 구조**에 맞추어 정합화한다.

정합화의 기준은 다음과 같다.

- 외부 전문: `hdr_nhnis + dto`
- 공통 Header: `hdr_nhnis.sys_comm`
- 업무 입력/출력: `dto`
- 오류 응답: `result`
- HTTP 요청 공통 실행문맥: `ServiceContext`
- TCF 거래 실행문맥: `TransactionContext`
- 라우팅 기준: `rms_svc_c` = ServiceId

즉 계정계 800-byte 고정길이 Header를 그대로 복제하는 것이 아니라,
계정계 Header의 의미를 pdmg-fw가 현재 사용하는 필드와 실행 책임에 맞추어 변환한다.

---

# 2. pdmg-fw 기준 표준전문

## 2.1 요청

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "std_gbl_id": "GUID-20260818-000001",
      "rms_svc_c": "mgcoa9000S0",
      "tr_sysid": "PDMG",
      "scid": "mgcoa9000",
      "tr_brc": "10001",
      "tr_trm_ipadr": "10.10.10.10",
      "optr_eno": "E0000001"
    }
  },
  "dto": {
    "pageNo": 1,
    "pageSize": 20
  }
}
```

## 2.2 정상 응답

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "std_gbl_id": "GUID-20260818-000001",
      "rms_svc_c": "mgcoa9000S0"
    }
  },
  "dto": {
    "data": {}
  }
}
```

## 2.3 오류 응답

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "std_gbl_id": "GUID-20260818-000001",
      "rms_svc_c": "mgcoa9000S0"
    }
  },
  "result": {
    "code": "ERROR_CODE",
    "message": "ERROR_MESSAGE"
  }
}
```

따라서 pdmg-fw의 전문 규칙은 다음으로 고정한다.

```text
요청       = hdr_nhnis + dto
정상 응답  = hdr_nhnis + dto
오류 응답  = hdr_nhnis + result
```

---

# 3. 계정계 Header ↔ pdmg-fw sys_comm 핵심 매핑

| 계정계 Header 의미 | pdmg-fw 필드 | 의미 | 처리 책임 |
|---|---|---|---|
| 금융메시지ID / Global ID | `std_gbl_id` | End-to-End 거래 추적 GUID | `DefaultFilter` / Interceptor |
| 업무/서비스 식별 | `rms_svc_c` | ServiceId | UI → FW 검증 → Dispatcher |
| 거래 시스템 ID | `tr_sysid` | 호출/거래 시스템 | Framework |
| 화면/프로그램 ID | `scid` | Screen/Program 식별 | UI 전달, FW 보강 |
| 거래점/사무소 코드 | `tr_brc` | 점코드/조직정보 | Header/JWT/User Context |
| 단말 IP | `tr_trm_ipadr` | Client IP | Filter/Interceptor 보강 |
| 조작자 사번 | `optr_eno` | 사용자 식별 | JWT 검증 후 보강 |

중요: 기존 문서에서 사용했던 `globalId`, `serviceId`, `systemId`, `screenId`,
`branchCode`, `clientIp`, `userId` 같은 신규 이름을 별도의 외부 표준으로 만들지 않는다.
**현재 pdmg-fw와 맞출 때는 위의 실제 `sys_comm` 물리명을 기준으로 한다.**

---

# 4. 계정계 Header 전체를 pdmg-fw에 맞추는 분류 기준

계정계 Header 필드는 다음 5종류로 판정한다.

| 판정 | 의미 | pdmg-fw 적용 |
|---|---|---|
| `DIRECT` | pdmg-fw에 동일 의미 필드 존재 | `sys_comm` 직접 매핑 |
| `DERIVED` | JWT/HTTP/Framework에서 생성·보강 가능 | `sys_comm` 보강 |
| `CONTEXT` | 외부전문보다 내부 실행문맥이 적합 | `ServiceContext` |
| `DTO` | 업무별 데이터 | `dto` |
| `LEGACY/OPTIONAL` | MCA/PINPAD/BPR 등 특정 Legacy 채널 전용 | 필요성 검토 후 별도 확장/제거 |

---

# 5. 계정계 공통 Header의 pdmg-fw 적용

## 5.1 거래 식별

```text
계정계 금융메시지ID
       │
       ▼
pdmg-fw
std_gbl_id
       │
       ├─ ServiceContext.guid
       ├─ MDC
       ├─ 전문 Logging
       ├─ ImageLog
       └─ Runtime Trace
```

`std_gbl_id`를 거래 추적의 기준키로 사용한다.

---

## 5.2 서비스 식별

```text
계정계 업무/거래 식별
        │
        ▼
hdr_nhnis.sys_comm.rms_svc_c
        │
        ▼
OnlineTransactionController
        │
        ▼
TcfFacade
        │
        ▼
TransactionDispatcher
        │
        ▼
Handler
```

따라서:

> `rms_svc_c` = pdmg-fw의 ServiceId = 전문과 실행 프로그램을 연결하는 논리적 거래주소

로 정의한다.

---

## 5.3 시스템 식별

```text
계정계 시스템 관련 Header
        │
        ▼
tr_sysid
        │
        ▼
ServiceContext
        │
        ├─ Logging
        ├─ Runtime Evidence
        └─ 연계 시스템 식별
```

---

## 5.4 화면/프로그램 식별

```text
화면/프로그램 정보
      │
      ▼
scid
      │
      ├─ 화면 추적
      ├─ 전문로그
      └─ ImageLog
```

---

## 5.5 점/조직 정보

```text
거래사무소 / 발생점
       │
       ▼
tr_brc
       │
       ├─ Header 값
       └─ JWT/User Context와 정합성 검증
```

Client가 임의 전달한 조직정보를 무조건 신뢰하지 않고 인증된 사용자 Context와 검증한다.

---

## 5.6 단말 IP

```text
계정계 단말IP
     │
     ▼
tr_trm_ipadr
     │
     ▼
DefaultFilter / Interceptor
     │
     └─ 실제 HTTP Client IP 기준 보강
```

---

## 5.7 조작자

```text
계정계 조작자사번
      │
      ▼
optr_eno
      │
      ▼
ServicePreventionInterceptor
      │
      ▼
JwtProvider
      │
      └─ 인증 사용자 기준 보강
```

---

# 6. 계정계 Transaction Header 처리 원칙

계정계 Transaction Header에 존재하는 모든 필드를 `sys_comm`에 넣지 않는다.

| 계정계 Transaction Header 유형 | pdmg-fw 적용 |
|---|---|
| 거래 추적/라우팅 공통정보 | `sys_comm` |
| 사용자/점/단말 공통정보 | `sys_comm` + Security Context |
| HTTP 실행정보 | `ServiceContext` |
| TCF 실행시간/Deadline/상태 | `TransactionContext` |
| 업무별 거래조건 | `dto` |
| MCA/PINPAD/매체 전용 | 필요 시 별도 확장 Header 또는 DTO |
| BPR Image ID 등 업무성 정보 | 해당 업무 DTO 우선 |
| 내부 시스템 실행상태 | 외부 Header 금지, Context 내부 관리 |

---

# 7. ServiceContext 정합화

pdmg-fw에서는 `hdr_nhnis`를 업무 계층까지 계속 전달하지 않는다.

```text
HTTP Request
{
  hdr_nhnis,
  dto
}
       │
       ▼
DefaultFilter
       │
       ├─ Request Body Cache
       ├─ hdr_nhnis Parsing
       ├─ GUID 준비
       ├─ MDC
       └─ ServiceContext 생성
               │
               ▼
        ServiceContextHolder
```

ServiceContext의 역할:

```text
ServiceContext
│
├─ applicationName
├─ guid
├─ active profile
├─ requestHeaders
├─ HttpServletRequest
├─ HttpServletResponse
├─ hdr_nhnis
│   └─ sys_comm
├─ userContext
├─ requestBody
└─ responseBody
```

다음은 넣지 않는다.

```text
DB Transaction       X
JDBC Connection      X
SqlSession           X
업무 DTO 저장소      X
업무 Rule            X
```

---

# 8. TransactionContext 정합화

TCF 진입 후에는 `TransactionContext`가 별도로 생성된다.

```text
ServiceContext
       │
       ▼
OnlineTransactionController
       │
       ▼
TcfFacade
       │
       ▼
TransactionContext
       │
       ├─ ServiceId
       ├─ startedAt
       └─ ServiceContext 참조
       │
       ▼
STF → Timeout → Dispatcher → Handler → ETF
```

따라서:

```text
ServiceContext
= HTTP Request 전체 문맥

TransactionContext
= TCF 거래 실행 문맥

Spring Transaction
= DB Transaction 경계
```

세 개를 구분한다.

---

# 9. pdmg-fw 실제 처리 흐름과 전문

```text
pdmg-ui / Client
      │
      │ Authorization: Bearer JWT
      │ hdr_nhnis + dto
      ▼
┌──────────────────────────────────────────────┐
│ pdmg-fw.commons                              │
│                                              │
│ DefaultFilter                                │
│  ├─ Body Cache                               │
│  ├─ hdr_nhnis Parsing                       │
│  ├─ ServiceContext                          │
│  └─ GUID/MDC                                 │
│       ↓                                      │
│ ServicePreventionInterceptor                 │
│  ├─ JWT Validation                          │
│  ├─ User/IP/GUID/Header 보강                │
│  ├─ Request Logging                         │
│  └─ PRE ImageLog                            │
│       ↓                                      │
│ RequestBodyArgumentResolver                  │
│  └─ dto → 업무 DTO                          │
└───────────────────┬──────────────────────────┘
                    ▼
┌──────────────────────────────────────────────┐
│ pdmg-fw.tcf                                  │
│                                              │
│ OnlineTransactionController                  │
│       ↓                                      │
│ TcfFacade                                    │
│       ↓                                      │
│ TransactionContext                           │
│       ↓                                      │
│ STF                                          │
│       ↓                                      │
│ Timeout Executor                             │
│       ↓                                      │
│ TransactionDispatcher                        │
│       │ rms_svc_c / ServiceId                │
│       ▼                                      │
│ Handler                                      │
└───────────────────┬──────────────────────────┘
                    ▼
pdmg-service
Handler → Facade → Service → DAO → Mapper
                    │
           ┌────────┴────────┐
           ▼                 ▼
          DTO             Exception
           │                 │
           └────────┬────────┘
                    ▼
pdmg-fw ResponseBodyArgumentResolver
           │
      ┌────┴─────┐
      ▼          ▼
   정상          오류
hdr_nhnis     hdr_nhnis
 + dto         + result
```

---

# 10. RequestBodyArgumentResolver 기준

업무 Controller/Handler에 표준전문 전체를 넘기지 않는다.

```text
{
  hdr_nhnis : {...},
  dto       : {...}
}
       │
       ▼
RequestBodyArgumentResolver
       │
       └─ dto만 업무 DTO Class로 변환
```

즉:

```text
hdr_nhnis → ServiceContext → Framework

dto       → DTOin → Handler/Facade/Service
```

로 분리한다.

---

# 11. ResponseBodyArgumentResolver 기준

## 정상

```text
업무 DTO
   │
   ▼
ResponseBodyArgumentResolver
   │
   ▼
{
  "hdr_nhnis": {...},
  "dto": {...}
}
```

## 오류

```text
Exception
   │
   ▼
GlobalExceptionHandler / Error DTO
   │
   ▼
ResponseBodyArgumentResolver
   │
   ▼
{
  "hdr_nhnis": {...},
  "result": {...}
}
```

따라서 업무 Service가 직접 표준 오류 JSON을 조립하지 않는다.

---

# 12. 계정계 Header → pdmg-fw 매핑 Matrix

| 계정계 의미 | pdmg-fw 대상 | 상태 | 비고 |
|---|---|---|---|
| 금융메시지ID | `std_gbl_id` | DIRECT | GUID/E2E Trace |
| 업무/서비스코드 | `rms_svc_c` | DIRECT | ServiceId |
| 거래 시스템 | `tr_sysid` | DIRECT | 시스템 식별 |
| 화면/프로그램 | `scid` | DIRECT | 화면/프로그램 추적 |
| 거래사무소/점 | `tr_brc` | DIRECT/DERIVED | 인증 Context와 검증 |
| 단말 IP | `tr_trm_ipadr` | DERIVED | HTTP 정보로 보강 |
| 조작자사번 | `optr_eno` | DERIVED | JWT 사용자로 보강 |
| 거래일시 | ServiceContext/로그 | CONTEXT | 현재 실제 물리필드 추가 여부는 소스 확인 필요 |
| 응답구분/결과 | `result` | DIRECT(응답) | 오류 응답 표준 |
| 거래 실행모드 | TCF 정책/Context | CONTEXT | `sys_comm` 신규 필드로 단정하지 않음 |
| 원거래ID | 미확정 | GAP | 실제 pdmg-fw 물리필드 확인 필요 |
| MCA 정보 | 미확정 | LEGACY/OPTIONAL | NSIGHT 필요성 검토 |
| PINPAD/매체 | 미확정 | LEGACY/OPTIONAL | 업무/채널 요구 시 |
| DB PIN | 미확정 | LEGACY/OPTIONAL | 보안 검토 필수 |
| PGW 정보 | 미확정 | LEGACY/OPTIONAL | Gateway 구조와 비교 |
| BPR Image ID | 업무 DTO 후보 | DTO | 업무 의미일 경우 |
| 자동화기기 마감 | 업무/채널 DTO 후보 | DTO/OPTIONAL | NSIGHT 적용성 검토 |

`미확정` 항목은 실제 pdmg-fw 소스에서 물리필드가 확인되기 전까지 임의 추가하지 않는다.

---

# 13. 기존 NSIGHT Header 초안에서 수정할 항목

기존 초안의 다음 명칭:

```text
globalId
serviceId
systemId
channelId
screenId
userId
branchCode
clientIp
requestDateTime
transactionType
```

을 pdmg-fw 외부 전문의 새로운 표준 필드로 확정하지 않는다.

현재 확인된 pdmg-fw 기준은:

```text
globalId    → std_gbl_id
serviceId   → rms_svc_c
systemId    → tr_sysid
screenId    → scid
branchCode  → tr_brc
clientIp    → tr_trm_ipadr
userId      → optr_eno
```

이다.

`channelId`, `requestDateTime`, `transactionType`, `originalGlobalId` 등은
계정계 Header에는 의미가 존재하더라도 **현재 pdmg-fw의 실제 `sys_comm` 물리필드로 확인되지 않은 상태라면 GAP로 관리**한다.

---

# 14. Architecture Rule

| Rule ID | 규칙 | 판정 |
|---|---|---|
| `MSG-PDMG-001` | 요청 전문은 `hdr_nhnis + dto`를 사용 | 필수 |
| `MSG-PDMG-002` | 정상 응답은 `hdr_nhnis + dto` | 필수 |
| `MSG-PDMG-003` | 오류 응답은 `hdr_nhnis + result` | 필수 |
| `MSG-PDMG-004` | 시스템 공통정보는 `hdr_nhnis.sys_comm`에 둔다 | 필수 |
| `MSG-PDMG-005` | 업무정보는 `dto`에 둔다 | 필수 |
| `MSG-PDMG-006` | `std_gbl_id`를 E2E 추적키로 사용 | 필수 |
| `MSG-PDMG-007` | `rms_svc_c`를 ServiceId/Dispatcher Key로 사용 | 필수 |
| `MSG-PDMG-008` | `hdr_nhnis`는 Framework에서 Parsing/Context화 | 필수 |
| `MSG-PDMG-009` | 업무 계층에 전체 전문을 전달하지 않는다 | 필수 |
| `MSG-PDMG-010` | `dto`만 업무 DTO로 Binding한다 | 필수 |
| `MSG-PDMG-011` | JWT 검증 후 사용자 정보를 Context/Header에 보강 | 필수 |
| `MSG-PDMG-012` | Client IP는 서버측 HTTP 정보와 검증/보강 | 필수 |
| `MSG-PDMG-013` | 계정계 Legacy Header를 무조건 `sys_comm`에 복제하지 않는다 | 금지 |
| `MSG-PDMG-014` | 미확인 필드는 실제 소스 확인 전 신규 표준으로 확정하지 않는다 | 필수 |
| `MSG-PDMG-015` | `ServiceContext`, `TransactionContext`, DB Transaction을 분리한다 | 필수 |

---

# 15. 최종 정합화 모델

```text
[계정계 표준전문 Header]
        │
        │ 의미/생성주체 분석
        ▼
┌─────────────────────────────────────┐
│ pdmg-fw Standard Message            │
│                                     │
│ hdr_nhnis                           │
│   └─ sys_comm                       │
│       ├─ std_gbl_id                 │
│       ├─ rms_svc_c                  │
│       ├─ tr_sysid                   │
│       ├─ scid                       │
│       ├─ tr_brc                     │
│       ├─ tr_trm_ipadr               │
│       └─ optr_eno                   │
│                                     │
│ dto                                 │
└────────────────┬────────────────────┘
                 ▼
          DefaultFilter
                 │
                 ├─ Header Parsing
                 ├─ ServiceContext
                 └─ MDC
                 ▼
 ServicePreventionInterceptor
                 │
                 ├─ JWT
                 ├─ User/IP/GUID 보강
                 ├─ Logging
                 └─ ImageLog
                 ▼
      OnlineTransactionController
                 │
                 ▼
             TcfFacade
                 │
                 ▼
        TransactionContext
                 │
                 ▼
     STF → Timeout → Dispatcher
                 │
            rms_svc_c
                 ▼
              Handler
                 ▼
       Facade → Service → DAO
                 │
          ┌──────┴──────┐
          ▼             ▼
         DTO         Exception
          │             │
          └──────┬──────┘
                 ▼
    ResponseBodyArgumentResolver
          │             │
          ▼             ▼
 hdr_nhnis+dto   hdr_nhnis+result
```

---

# 16. 최종 결론

계정계 전문과 pdmg-fw 전문을 맞출 때 기준은 **계정계의 필드명을 새 JSON 이름으로 재작성하는 것**이 아니다.

정확한 기준은 다음이다.

```text
계정계 Header 의미
      ↓
현재 pdmg-fw 실제 sys_comm 필드와 매핑
      ↓
DIRECT / DERIVED / CONTEXT / DTO / LEGACY 판정
      ↓
DefaultFilter + ServiceContext
      ↓
Interceptor + JWT + Logging
      ↓
TCF + TransactionContext
      ↓
rms_svc_c(ServiceId) Dispatcher
      ↓
업무 DTO
      ↓
표준 Response
```

따라서 현재 정합화된 핵심 물리필드는:

```text
std_gbl_id
rms_svc_c
tr_sysid
scid
tr_brc
tr_trm_ipadr
optr_eno
```

이며, 이것을 기준으로 계정계 Header 전 필드를 하나씩 매핑하고,
현재 pdmg-fw에 존재하지 않는 필드는 **즉시 추가하지 말고 GAP로 관리한 뒤 필요성과 생성주체를 검토**하는 것이 최종 원칙이다.
