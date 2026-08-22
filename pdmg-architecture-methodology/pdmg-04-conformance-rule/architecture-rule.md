찾았습니다. 핵심부터 말하면 **`30-CONFORMANCE/reference-rules.json`은 `20-MODEL`에서 만든 아키텍처 지도를 보고 “이 구조가 반드시 지켜야 할 규칙”을 저장하는 파일**입니다.

현재 업로드된 `nsight-tcf-framework (2).zip` 안에는 완성된 `reference-rules.json` 파일 자체는 없었습니다. 이 파일은 `scan-reference`를 실행할 때 Run별로 생성하도록 설계되어 있습니다.

쉽게 표현하면:

```text
20-MODEL/reference-baseline-draft.json
=
"PDMG는 이렇게 생겼다"


30-CONFORMANCE/reference-rules.json
=
"PDMG라면 이렇게 만들어져 있어야 한다"
```

## 1. 현재 설계에서 확인된 Reference Rule은 4종류입니다

PDMG Reference 설계문서에 정확히 다음 4개 그룹이 정의되어 있습니다.

| Rule 그룹                  | 의미               | 대표 검증                                 |
| -------------------------- | ------------------ | ----------------------------------------- |
| **R1 Structural**          | 프로그램 구조 규칙 | 계층, 패키지, ServiceId, DTO, Mapper      |
| **R2 Framework**           | TCF 실행구조 규칙  | Controller, STF, Timeout, Dispatcher, ETF |
| **R3 Security**            | JWT/보안 규칙      | Private Key, JWT, JWKS, Refresh           |
| **R4 Runtime/Operational** | 운영·실행 규칙     | TX, Timeout, SQL, Thread, Hikari, 로그    |

전체적으로는:

```text
reference-rules.json
│
├─ R1 STRUCTURAL
├─ R2 FRAMEWORK
├─ R3 SECURITY
└─ R4 RUNTIME / OPERATION
```

입니다.

---

# 2. R1 — Structural Rule

가장 먼저 검사할 규칙입니다.

```text
PDMG 업무 프로그램

ServiceId
   ↓
Handler
   ↓
Facade
   ↓
Service
   ↓
DAO
   ↓
Mapper
   ↓
SQL
   ↓
Table
```

PDMG Reference의 Business Application Model 자체가 이 경로를 기준 구조로 정의합니다.

따라서 `reference-rules.json`에는 최소 다음 규칙이 필요합니다.

| 규칙                  | 의미                                     |
| --------------------- | ---------------------------------------- |
| ServiceId Unique      | 같은 ServiceId가 두 개 존재하면 안 됨    |
| ServiceId → Handler   | 모든 ServiceId는 Handler에 연결되어야 함 |
| Handler → Facade      | Handler는 Facade를 통해 업무를 호출      |
| Handler → DAO 금지    | Handler가 DAO 직접 호출 금지             |
| Controller → DAO 금지 | Controller가 DAO 직접 호출 금지          |
| Facade → Service      | Facade에서 Service 호출                  |
| Service → DAO         | DB 접근은 Service → DAO                  |
| Package Naming        | `nhnis.mg.co.a...` 구조 준수             |
| DTO Naming            | ServiceId와 DTO 네이밍 연결              |
| Mapper Namespace      | DAO ↔ Mapper namespace 정합              |
| SQL ID Unique         | Mapper SQL ID 중복 금지                  |

Closed Loop의 우선 구현 목록에서도 `ServiceId Unique`, `ServiceId → Handler Mapping`, `Handler → DAO Direct Call 금지`, `Controller → DAO Direct Call 금지`, `Package Naming`, `Mapper SQL ID Unique`가 P0 규칙으로 명시돼 있습니다.

예를 들어:

```text
정상

mgcoa9001S0
      ↓
mgcoa9001Handler
      ↓
mgcoa9001Facade
      ↓
mgcoa9001Service
      ↓
mgcoa9001DAO
```

반면:

```text
mgcoa9001Handler
       │
       └────────→ mgcoa9001DAO
```

이면:

```text
R1 위반

Handler → DAO Direct Call
        ↓
FAIL
```

입니다.

---

# 3. R2 — Framework Rule

이번에는 `pdmg-fw`의 규칙입니다.

Reference Framework Model은:

```text
Filter
 ↓
Interceptor
 ↓
OnlineTransactionController
 ↓
STF
 ↓
Timeout Executor
 ↓
Dispatcher
 ↓
Handler
 ↓
ETF
```

로 정의되어 있습니다.

따라서 검증 규칙은 다음과 같습니다.

| 규칙                | 검사 내용                        |
| ------------------- | -------------------------------- |
| TCF Entry Rule      | TCF ON일 때 공통 Controller 사용 |
| STF Lifecycle       | 업무 실행 전에 STF 수행          |
| Dispatcher Routing  | ServiceId로 Handler 선택         |
| Handler Mapping     | 등록되지 않은 Handler 금지       |
| Timeout Executor    | Timeout 정책에 따라 실행         |
| Transaction Context | 거래 실행 Context 유지           |
| Transaction Owner   | TX 시작 주체 확인                |
| ETF Lifecycle       | 거래 종료 시 ETF 수행            |

현재 PDMG Timeout ON 구조에서는 특히:

```text
OnlineTimeoutExecutor
      ↓
Worker Thread
      ↓
TransactionTemplate
      ↓
TX BEGIN
      ↓
Dispatcher
```

이므로 Transaction 관련 규칙도 중요합니다. 실제 PDMG 구조에서는 Dispatcher 이전에 `TransactionTemplate`이 Physical Transaction을 시작합니다.

즉 이런 검사도 가능합니다.

```text
RULE

Timeout ON
    ↓
TransactionTemplate 존재?
    ↓
Dispatcher보다 바깥인가?
    ↓
YES → PASS
NO  → FAIL
```

---

# 4. R3 — Security Rule

`pdmg-jwt`를 대상으로 하는 규칙입니다.

Reference Security Model은:

```text
Login / SSO ServiceId
       ↓
Token Issuer
       ↓
Private Key
       ↓
JWT
       ↓
JWKS
       ↓
Public Key Validation
       ↓
Refresh / Revoke
```

입니다.

검증 대상은:

| 규칙                      | 의미                            |
| ------------------------- | ------------------------------- |
| JWT Issuer Boundary       | Token 발급 책임 분리            |
| Private Key Boundary      | 개인키 사용 위치 제한           |
| Private Key Exposure 금지 | 업무 모듈 등에 개인키 노출 금지 |
| JWKS Exposure             | 공개키만 JWKS로 제공            |
| JWT Expiration            | 만료시간 존재                   |
| Refresh Policy            | Refresh Token 정책              |
| Revoke Policy             | 토큰 폐기 지원                  |
| Internal Validation       | 내부 호출도 검증 정책 적용      |

특히 Closed Loop 기준에는:

```text
R-JWT-PRIVATE-KEY

Private Key는
Token Issuer 영역 외에 존재하면 안 된다.
```

라는 규칙이 명시돼 있습니다.

따라서:

```text
pdmg-jwt
  JwtTokenIssuer
       │
       └── Private Key     OK


pdmg-service
  CustomerService
       │
       └── Private Key     X
```

처럼 검사할 수 있습니다.

---

# 5. R4 — Runtime / Operational Rule

이 규칙은 **소스 구조만 보는 것이 아니라 실제 실행 결과까지 검사**합니다.

Reference 설계에서 다음 항목이 명시돼 있습니다.

```text
Runtime Evidence Chain
Transaction Begin / Commit / Rollback Evidence
Timeout Evidence
SQL Elapsed
Thread / Hikari / JVM Evidence
Sensitive Logging 금지
```

예를 들면:

```text
mgcoa9001S0 실행

ServiceId
   ↓
GUID
   ↓
Thread ID
   ↓
TX BEGIN
   ↓
SQL ID
   ↓
SQL elapsed
   ↓
TX COMMIT
   ↓
Response
```

이 증적이 연결되는지를 검사합니다.

다시 말해서:

```text
"Transaction 구조가 설계되어 있다"
```

만으로 PASS가 아닙니다.

```text
TX BEGIN 확인
     ↓
SQL 실행 확인
     ↓
COMMIT / ROLLBACK 확인
     ↓
동일 ServiceId / GUID로 추적
```

되어야 합니다.

---

# 6. 추가 우선순위 규칙도 이미 정의돼 있습니다

Closed Loop 마스터 프롬프트에는 규칙을 P0/P1/P2로 나누어 놓았습니다.

### P0 — 가장 먼저 자동화

```text
ServiceId Unique
ServiceId → Handler Mapping
Handler → DAO Direct Call 금지
Controller → DAO Direct Call 금지
Package Naming
Mapper SQL ID Unique
JWT Private Key Exposure 금지
Runtime Evidence Mandatory
Artifact Hash Mandatory
Approval Mandatory
```

### P1 — 실행정책

```text
Transaction Owner
Timeout Policy
Session Policy
WAR Dependency
Sensitive Log Masking
EAI Timeout
DB Query Timeout
OM Catalog Match
```

### P2 — 운영 품질

```text
Capacity Threshold
Thread Threshold
Hikari Threshold
GC Threshold
Naming 확장규칙
Documentation Link Integrity
```

이 목록이 사실상 `reference-rules.json`의 초기 Rule Catalog가 됩니다.

---

# 7. 전문(Message)에 대한 규칙도 찾았습니다

전문 아키텍처에는 별도의 구체적인 Rule이 이미 정의돼 있습니다.

예를 들어:

```text
MSG-001  Header + Business Payload 분리

MSG-002  ServiceId는 공통 Header에서 관리

MSG-003  GUID 모든 거래 유지

MSG-004  업무 계층에 전체 전문 전달 금지

MSG-005  업무에는 필요한 DTO만 전달

MSG-006  Framework 공통정보는 Context 사용

MSG-007  업무 프로그램이 응답 JSON 직접 조립 금지

MSG-008  오류는 Exception으로 전달

MSG-009  정상 dto / 오류 result

MSG-010  GUID + ServiceId 추적성 유지

MSG-011  민감정보 로그 마스킹

MSG-012  전문 버전 하위호환
```

이것도 `reference-rules.json`의 `MESSAGE` 영역으로 포함시키는 것이 맞습니다.

---

# 8. 도메인 간 호출 규칙도 존재합니다

MG ↔ MK 같은 도메인 연계에서는 이미 매우 구체적인 규칙이 정의돼 있습니다.

대표적으로:

```text
다른 도메인 DAO 직접 호출 금지
다른 도메인 Mapper 직접 호출 금지
다른 도메인 Table 직접 갱신 금지

도메인 간 호출
       ↓
공개 ServiceId
       ↓
HTTP + 표준전문

WAR 간 Java Dependency 금지
순환 동기 호출 금지
```

입니다.

즉 이것도 향후:

```text
R1 / DOMAIN
```

규칙에 포함해야 합니다.

---

# 9. 결국 `reference-rules.json`을 아주 쉽게 보면

`20-MODEL`이 다음과 같다면:

```text
우리 PDMG는

ServiceId
 → Handler
 → Facade
 → Service
 → DAO
 → Mapper
 → DB

구조다.
```

`30-CONFORMANCE`는 이렇게 말합니다.

```text
그렇다면 검사한다.

① ServiceId 중복 없나?
② Handler 없는 ServiceId 없나?
③ Handler가 DAO 바로 부르지 않나?
④ Controller가 DAO 부르지 않나?
⑤ 패키지 규칙 맞나?
⑥ Mapper SQL ID 중복 없나?
⑦ Timeout 구조 맞나?
⑧ Transaction 시작 위치 맞나?
⑨ JWT Private Key가 잘못 노출되지 않았나?
⑩ Runtime에서 실제로 그렇게 움직이나?
```

바로 이것이 **Conformance**입니다.

---

## 최종 관계

```text
00-SOURCE
"실제 뭐가 있지?"
       ↓

20-MODEL
"우리 구조는 이렇게 생겼다."
       ↓

30-CONFORMANCE
"그 구조가 지켜야 할 규칙은 이것이다."
       ↓

40-TEST
"실제로 검사해보자."
       ↓

PASS / FAIL
```

그래서 `reference-rules.json`은 쉽게 이름을 바꾸어 이해하면:

> **`PDMG_아키텍처_준수규칙.json`**

입니다.

그리고 지금 자료를 기준으로 보면 **R1 Structural + R2 Framework + R3 Security + R4 Runtime/Operational을 기본 뼈대로 하고, P0/P1/P2의 실제 검사 규칙을 그 아래 배치하는 구조가 현재 PDMG Reference와 가장 정확하게 맞습니다.**
