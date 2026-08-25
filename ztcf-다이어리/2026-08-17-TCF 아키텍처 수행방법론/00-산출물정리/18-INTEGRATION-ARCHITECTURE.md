# 18. NSIGHT Integration Architecture

## 1. 목적

연계를 단순 URL 목록이 아니라 **도메인 경계, 계약, 인증, Timeout, 오류, 멱등성, 장애전파, 데이터 소유권**의 집합으로 정의한다.

---

## 2. Integration Principle

```text
Domain A
  ↓
Application Service
  ↓
Outbound Client / Port
  ↓
HTTP/JSON Standard Message
  ↓
Target Public ServiceId
  ↓
Domain B TCF
  ↓
Domain B Business
  ↓
Domain B Owned Data
```

금지:

```text
Domain A → Domain B DAO
Domain A → Domain B Mapper
Domain A → Domain B Table Direct Update
WAR 간 내부 Java Class 직접 Dependency
순환 동기호출 A→B→A
```

---

## 3. [FACT] MG ↔ MK Reference Rule

기존 MG/MK 분석에서는 다음이 기준으로 정리되어 있다.

```text
MG Service
  ↓
MgToMkClient
  ↓ HTTP + Standard Message
MK Public ServiceId
  ↓
MK Service
  ↓
MK DAO / Mapper
  ↓
MK Owned Data
```

원 요청 ServiceId와 Target ServiceId는 분리한다.

```text
callerServiceId = mg...
targetServiceId = mk...
trace/guid       = 연계 유지
```

즉:

> Trace Identity는 이어가고 Business Transaction Identity는 분리한다.

---

## 4. [FACT] NSIGHT `tcf-eai` Source

현재 Source Snapshot에는 `tcf-eai` 모듈이 있으며 주요 구현은 다음과 같다.

- `TcfServiceClient`
- `DefaultTcfServiceClient`
- `TcfIntegrationProperties`
- `HeaderPropagationHelper`
- `StandardRequestBuilder`
- `ResponseResultValidator`
- `IntegrationTimeoutException`
- `IntegrationBusinessException`

호출은 Spring `RestClient` 기반 HTTP/JSON이다.

---

## 5. Endpoint / Timeout Configuration

`nsight.integration` 설정 모델:

```yaml
nsight:
  integration:
    default-timeout-ms: 3000
    services:
      SV:
        base-url: http://...
        context-path: /sv
        online-path: /online
        connect-timeout-ms: 1000
        read-timeout-ms: 3000
```

현재 Source 기본값:

- Connect Timeout 미지정: 1000ms
- Read Timeout 미지정: `defaultTimeoutMs`
- Default Read Timeout: 3000ms

이 값은 실제 Service Deadline보다 작거나 같아야 한다.

---

## 6. Standard Message Propagation

`tcf-eai`는 caller `TransactionContext`에서 Header Propagation 정보를 추출하고 Standard Request를 구성한다.

Integration Contract는 최소 다음을 전달해야 한다.

```text
guid / traceId
callerServiceId
targetServiceId
user/context as allowed
channel/system
business body
```

다른 Domain을 호출할 때 `serviceId`는 Target ServiceId로 변경되어야 한다.

---

## 7. Transaction Boundary

HTTP 호출은 Spring Local Transaction을 공유하지 않는다.

```text
Domain A TX
  │
  ├─ Local DB
  │
  ├──── HTTP ────► Domain B TX
  │                 │
  │                 └─ Commit/Rollback independently
  │
  └─ Domain A Commit/Rollback
```

따라서 변경성 Cross-Domain 거래는 다음이 필수다.

- Idempotency Key
- Status Query
- Retry Policy
- Compensation
- Reconciliation
- Manual Recovery Path

---

## 8. Timeout Budget

금지:

```text
Parent Deadline 5s
  └─ Child HTTP Timeout 30s
```

권장:

```text
remainingDeadline = parentDeadline - elapsed
childReadTimeout <= remainingDeadline
```

현재 `tcf-eai`는 고정 endpoint timeout을 사용하며, **TransactionContext의 Remaining Deadline을 동적으로 하위 호출 timeout에 적용하는 구현은 현재 확인되지 않았다.**

판정:

```text
[GAP P0]
Deadline Propagation to Integration Client
```

---

## 9. Retry / Circuit Breaker / Bulkhead

현재 `tcf-eai` Source에서 다음 구현은 확인되지 않았다.

- Retry
- Circuit Breaker
- Bulkhead
- Idempotency orchestration

따라서 이것들을 구현됐다고 가정하지 않는다.

정책 원칙:

| 항목 | 기본정책 |
|---|---|
| Read-only Retry | 제한적, Deadline 내 |
| Change Retry | 기본 금지, 멱등성 확보 시만 |
| Circuit Breaker | 외부/불안정 연계 후보 |
| Bulkhead | 연계 장애가 전체 Thread/Pool로 전파되는 경우 적용 |
| Infinite Retry | 금지 |

---

## 10. Error Mapping

현재 Source는 다음을 구분한다.

```text
Timeout
→ IntegrationTimeoutException

Connection/System
→ IntegrationException(SYSTEM)

Target Business Error
→ ResponseResultValidator / Business Error Mapping
```

원인정보는 보존해야 한다.

```text
callerServiceId
targetBusinessCode
targetServiceId
targetErrorCode
elapsedMs
guid
```

---

## 11. Integration Security

현재 일반 `tcf-eai` HTTP Client에서 Authorization Bearer 전달/서비스간 mTLS/API Key 처리는 명시적으로 확인되지 않았다.

따라서 서비스 간 인증은 G50의 핵심 Open Issue다.

검토 대상:

- Gateway 경유 여부
- Service-to-Service JWT
- mTLS
- Internal API Key / Signed Header
- Source IP/Network Trust

금지:

```text
내부망이므로 인증 생략
```

---

## 12. No P2P 정책과 Enterprise Integration

전략자료의 Integration 통제는 다음 방향이다.

- No P2P
- DB Link 금지
- 시스템 간 API Gateway(CruzAPIM) 경로
- 파일연계 표준 경로
- 대외 연계 표준 경로
- 계정계 실시간 연계 표준 경로

`tcf-eai`는 Application 수준 HTTP Client Mechanism이고, Enterprise Gateway/EAI 제품 경계와 동일 개념이 아니다.

따라서 최종 Physical Integration은 다음을 구분해야 한다.

```text
Application Client
  ↓
Internal Gateway / CruzAPIM / EAI Boundary
  ↓
Target System
```

---

## 13. Integration Contract Registry

최소 컬럼:

| Field | 설명 |
|---|---|
| Interface ID | 고유 ID |
| Source Domain | 호출자 |
| Caller ServiceId | 호출 거래 |
| Target Domain | 대상 |
| Target ServiceId/API | 공개 계약 |
| Protocol | HTTP/JSON/File/Kafka 등 |
| Gateway | 경유지 |
| Auth | 인증 방식 |
| Connect/Read Timeout | 시간제한 |
| Retry | 재시도 |
| Idempotency | 중복방지 |
| Error Mapping | 오류변환 |
| Data Owner | 데이터 소유권 |
| Owner | 운영책임 |
| SLA | 응답/처리 SLA |
| Evidence | 근거 |

현재 전수 Contract Registry는 미완성이다.

---

## 14. Integration Architecture Rules

| Rule ID | Rule | 상태 |
|---|---|---|
| INT-001 | Cross-Domain은 Public Contract만 사용 | 확정 |
| INT-002 | 다른 Domain DAO/Mapper/Table 직접 접근 금지 | 확정 |
| INT-003 | Caller/Target ServiceId 분리 | 확정 |
| INT-004 | TraceId/GUID는 연계 추적 가능하게 전달 | 확정 |
| INT-005 | Local TX를 원격 TX로 확장했다고 간주 금지 | 확정 |
| INT-006 | Child Timeout <= Remaining Deadline | **GAP** |
| INT-007 | 무제한 Retry 금지 | 정책 |
| INT-008 | 변경 Retry는 Idempotency 필수 | 정책 |
| INT-009 | Service-to-Service Authentication 명시 | **OPEN** |
| INT-010 | Enterprise Gateway 경유정책과 App Client를 구분 | Open |

---

## 15. G50 Integration 판정

**CONDITIONAL PASS**

`tcf-eai`로 HTTP/JSON + Standard Message + Timeout + Error Mapping Mechanism은 존재하지만, Deadline Propagation, Retry/CB/Bulkhead, Service-to-Service Authentication, Enterprise Route Registry를 닫아야 한다.
