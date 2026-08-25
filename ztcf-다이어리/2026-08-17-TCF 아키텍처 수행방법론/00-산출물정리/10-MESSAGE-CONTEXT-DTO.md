# 10. Standard Message / Context / DTO Architecture — G40

> Gate: **G40 — Mechanism / Source Conformance**  
> 기준: `nsight-tcf-framework (2).zip`의 실제 Source Snapshot과 기존 NSIGHT Architecture Baseline을 대조한다.  
> 원칙: **PDMG AS-IS와 NSIGHT TCF TO-BE를 동일 구현으로 취급하지 않는다.**


## 1. 결론

PDMG AS-IS와 NSIGHT TCF TO-BE 모두 **공통 Header와 업무 Payload를 분리**한다는 원칙은 동일하다. 다만 Wire Contract의 필드명과 Envelope가 동일하지 않으므로 직접 호환으로 간주하면 안 된다.

## 2. PDMG AS-IS Message

```text
Request
├─ hdr_nhnis
│   └─ sys_comm
└─ dto

Success Response
├─ hdr_nhnis
└─ dto

Error Response
├─ hdr_nhnis
└─ result
```

`RequestBodyArgumentResolver`는 Root JSON에서 `dto` 노드만 추출하여 업무 DTO로 변환한다.

근거: `pdmg-fw/.../RequestBodyArgumentResolver.java:62-73`

`ResponseBodyArgumentResolver`는 업무 응답 `dto`와 오류 `result`를 분리한다.

근거: `ResponseBodyArgumentResolver.java:60-68`

## 3. PDMG Context

### ServiceContext

HTTP Request 생명주기 공통정보:

- Header
- GUID
- ServiceId
- User/IP 등
- Raw Request Body

`DefaultFilter`가 생성/설정하고 Request 종료 시 제거한다.

### TransactionContext

TCF 거래 실행구간 정보:

- ServiceContext 참조
- ServiceId
- 시작시각
- GUID/Header 접근

## 4. NSIGHT TCF Standard Message

```text
StandardRequest
├─ header : StandardHeader
└─ body

StandardResponse
├─ header : StandardHeader
├─ result : Result
└─ body
```

`StandardHeader` 주요 필드:

```text
systemId
businessCode
serviceId
serviceName
transactionCode
processingType
guid
traceId
channelId
userId
branchId
centerId
requestTime
clientIp
idempotencyKey
```

근거: `tcf-core/.../StandardHeader.java:9-26`

## 5. Client Header 보존

NSIGHT TCF는 STF 처리용 Header와 Client Echo용 Header를 분리한다.

- `TransactionContext.header` = normalize/보완된 내부 처리 Header
- `TransactionContext.clientHeader` = 응답 Echo용 원본 Header
- 서버가 생성한 `guid/traceId`만 clientHeader에 보완

이는 Framework가 내부 Normalize를 수행하더라도 응답 계약을 불필요하게 변형하지 않는 구조다.

근거: `TransactionContext.java`, `StandardHeader.copyOf/applyGeneratedCorrelationIdsFrom`

## 6. Contract Mapping Gap

| 의미 | PDMG | NSIGHT TCF | 상태 |
|---|---|---|---|
| Common Header | `hdr_nhnis.sys_comm` | `header` | Mapping 필요 |
| ServiceId | `rms_svc_c` | `serviceId` | Mapping 필요 |
| GUID | `std_gbl_id` | `guid` | Mapping 필요 |
| Business Payload | `dto` | `body` | Mapping 필요 |
| Error | `result` | `result` | 상세코드 Mapping 필요 |
| TraceId | 명시성 낮음/별도 | `traceId` | TO-BE 확장 |
| Idempotency Key | 별도/미명시 | `idempotencyKey` | TO-BE 확장 |

따라서 기존 화면/대외연계와 NSIGHT TCF 사이에는 **Message Adapter 또는 공식 Contract Migration**이 필요하다.

## 7. Architecture Rules

- Header는 Framework가 소유한다.
- 업무 Service는 전체 Wire Envelope를 직접 파싱하지 않는다.
- 업무 DTO/Body만 Handler/Facade/Service에 전달한다.
- DAO/Mapper까지 Header 전체를 전달하지 않는다.
- Correlation ID는 Framework가 생성/보완하되 Client 계약을 임의 변경하지 않는다.
- Error Response는 업무 코드가 직접 JSON 조립하지 않는다.

## 8. Open Issues

1. PDMG `hdr_nhnis` 전체 필드 ↔ StandardHeader 필드 매핑표.
2. 계정계/상호 표준전문 Header와 NSIGHT StandardHeader Mapping.
3. Null/Empty/Unknown field 처리 규칙.
4. Error Code/Result Code 변환 규칙.
5. Versioning/Backward Compatibility 정책.

## 9. Gate 판정

**CONDITIONAL PASS** — 메시지 책임 분리는 명확하지만 Contract Mapping/Versioning이 아직 미완성이다.
