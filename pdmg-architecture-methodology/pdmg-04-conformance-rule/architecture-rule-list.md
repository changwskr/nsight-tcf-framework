네. 지금까지 **PDMG Reference Architecture와 `30-CONFORMANCE/reference-rules.json` 후보로 찾은 규칙을 중복을 제거해서 한 표 체계로 정리**하면 아래와 같습니다.

중요한 전제는, 소스에서 발견했다고 바로 표준으로 확정하지 않고 `VERIFIED | CANDIDATE | VARIANT | DEPRECATED` 상태를 붙여 관리하는 것입니다. Reference 설계 자체도 이 방식을 요구합니다.

## 1. 전체 Architecture Rule 분류

| 구분       | Rule 영역              | 핵심 질문                                | 주요 대상                  |
| ---------- | ---------------------- | ---------------------------------------- | -------------------------- |
| **R1**     | Structural             | 프로그램 구조가 표준 계층을 지키는가?    | `pdmg-service`, `pdmg-jwt` |
| **R2**     | Framework              | TCF 거래 생명주기가 표준대로 동작하는가? | `pdmg-fw`                  |
| **R3**     | Security               | JWT/Key/Auth 경계가 안전한가?            | `pdmg-jwt`, `pdmg-fw`      |
| **R4**     | Runtime / Operational  | 실제 실행도 설계와 같은가?               | `pdmg-fw`, 운영영역        |
| **MSG**    | Standard Message       | 전문/Header/DTO/Context 규칙을 지키는가? | UI/FW/Service              |
| **DOMAIN** | Domain Boundary        | 업무 도메인 경계를 침범하지 않는가?      | MG/MK 등                   |
| **GOV**    | Closed Loop Governance | 테스트·증적·승인이 갖춰졌는가?           | 전체                       |

PDMG Reference 설계에서는 공식적으로 R1~R4를 Structural, Framework, Security, Runtime/Operational로 구분합니다.

---

# 2. R1 — Structural Architecture Rule

| 우선순위 | Rule                                  | 검사 내용                        | 정상                       | 위반 예                         |
| -------- | ------------------------------------- | -------------------------------- | -------------------------- | ------------------------------- |
| **P0**   | **ServiceId Unique**                  | ServiceId 중복 여부              | `mgcoa9001S0` 1건          | 동일 ServiceId 2개 Handler 등록 |
| **P0**   | **ServiceId → Handler Mapping**       | 모든 ServiceId에 Handler 존재    | ServiceId → Handler        | Handler 없는 ServiceId          |
| P0       | Handler → Facade                      | Handler는 Facade를 호출          | Handler→Facade             | Handler→Service 직접            |
| **P0**   | **Handler → DAO Direct Call 금지**    | Handler의 DAO 직접 의존 검사     | Handler→Facade→Service→DAO | Handler→DAO                     |
| **P0**   | **Controller → DAO Direct Call 금지** | Controller의 DAO 직접 의존 검사  | Controller→TCF/Facade      | Controller→DAO                  |
| P1       | Facade → Service                      | Facade가 Use Case 경계 담당      | Facade→Service             | Facade→Mapper                   |
| P1       | Service → DAO                         | DB 접근은 DAO 경계 사용          | Service→DAO                | Service→Mapper XML 직접         |
| **P0**   | **Package Naming**                    | 업무 패키지 분류 규칙            | `nhnis.mg.co.a...`         | 업무코드와 패키지 불일치        |
| P1       | ServiceId ↔ Package                   | ServiceId 업무코드와 패키지 일치 | `mgcoa...` ↔ `mg.co.a`     | `mgcoa`인데 `mg.ic.a`           |
| P1       | DTO Naming                            | ServiceId별 DTO 계약             | `mgcoa9001S0DTOin/out`     | 거래와 무관한 DTO               |
| P1       | DAO ↔ Mapper Namespace                | DAO FQCN과 Mapper namespace 일치 | DAO ↔ XML                  | 잘못된 namespace                |
| **P0**   | **Mapper SQL ID Unique**              | SQL ID 중복 여부                 | SQL ID 1개                 | 동일 namespace 내 중복          |
| P1       | DAO Method ↔ SQL ID                   | DAO 메서드와 Mapper SQL 연결     | method ↔ id                | Mapper SQL 없음                 |
| P1       | ServiceId → Table Traceability        | 거래에서 Table까지 추적 가능     | ServiceId→...→Table        | 중간 관계 단절                  |

Reference 구조 자체가 `ServiceId → Handler → Facade → Service → DAO → Mapper → SQL → Table`을 Business Application Model로 정의합니다. 또한 P0 자동검사 대상으로 ServiceId Unique, Handler Mapping, Handler→DAO 금지, Controller→DAO 금지, Package Naming, Mapper SQL ID Unique가 명시돼 있습니다.

---

# 3. R2 — Framework / TCF Rule

| 우선순위  | Rule                   | 검사 내용                       | 기대 구조                             |
| --------- | ---------------------- | ------------------------------- | ------------------------------------- |
| P1        | TCF Entry Rule         | TCF ON/OFF에 맞는 진입점 사용   | ON → `OnlineTransactionController`    |
| P1        | System Pre Processing  | Filter/Interceptor 실행순서     | Filter → Interceptor                  |
| P1        | STF Lifecycle          | 업무 실행 전 STF 수행           | Controller→TCF→STF                    |
| **P0/P1** | Dispatcher Routing     | ServiceId 기반 Handler Routing  | `handlerMap[serviceId]`               |
| P1        | Handler Registration   | Dispatcher에 Handler 등록 여부  | ServiceId→Handler                     |
| **P1**    | Timeout Executor       | Timeout 정책에 따른 실행        | STF→Timeout Executor                  |
| **P1**    | Transaction Owner      | Physical TX 시작 주체 확인      | Worker→TransactionTemplate            |
| P1        | Transaction Context    | 거래 Context 유지               | 요청→Worker Context 전달              |
| P1        | Worker Context Restore | Thread 변경 시 Context/MDC 복구 | Request→Worker                        |
| P1        | ETF Lifecycle          | 업무 종료 후 ETF 수행           | Business→ETF                          |
| P1        | TCF Ordering           | 전체 실행순서 준수              | STF→Timeout/TX→Dispatcher→Handler→ETF |

현재 PDMG Reference Framework Model은 `Filter → Interceptor → OnlineTransactionController → STF → Timeout Executor → Dispatcher → Handler → ETF`입니다.

Timeout ON인 PDMG에서는 특히 다음 구조가 중요한 검사 대상입니다.

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

실제 분석 자료에서도 Dispatcher 이전에 `TransactionTemplate`이 Physical Transaction을 시작하는 구조로 확인됐습니다.

---

# 4. R3 — JWT / Security Rule

| 우선순위 | Rule                          | 검사 내용                 | 정상                   | 금지                         |
| -------- | ----------------------------- | ------------------------- | ---------------------- | ---------------------------- |
| P1       | JWT Issuer Boundary           | Token 발급 책임 분리      | Token Issuer           | 업무 Service가 임의 발급     |
| **P0**   | **Private Key Exposure 금지** | Private Key 위치 검사     | `pdmg-jwt` Issuer 영역 | `pdmg-service`, UI 등에 존재 |
| P1       | Private Key Use Boundary      | 서명 주체만 개인키 사용   | Issuer만 Sign          | Validator가 Private Key 보유 |
| P1       | Public Key Distribution       | 검증자는 Public Key 사용  | JWKS/Public Key        | Private Key 배포             |
| P1       | JWKS Exposure                 | JWKS에는 공개키만 제공    | Public Key             | Private Key 포함             |
| P1       | JWT Expiration                | Access Token 만료 존재    | `exp` 정책 존재        | 무기한 Token                 |
| P1       | Refresh Policy                | Refresh Token 정책 관리   | 저장/교체/만료         | 무제한 재사용                |
| P1       | Revoke Policy                 | 폐기 가능 여부            | Logout/Revoke          | 폐기 불가능                  |
| P1       | Internal Call Validation      | 내부 호출 인증 검증       | JWT 검증 적용          | 내부라는 이유로 무검증       |
| P0       | Browser Private Key 금지      | Client의 개인키 보유 검사 | 없음                   | Browser Private Key          |
| P1       | JWT URL 전달 금지             | Token 전송 위치           | Authorization Header   | Query String                 |

Reference Security Model은 `Login/SSO → Token Issuer → Private Key → JWT → JWKS → Public Key Validation → Refresh/Revoke`로 정의됩니다. 프로젝트 인증 원칙도 Private Key는 발급 주체만 사용하고 브라우저 보유 및 JWT URL 전달을 금지합니다.

---

# 5. R4 — Runtime / Operational Rule

| 우선순위 | Rule                           | 검사 내용              | 필요한 Evidence        |
| -------- | ------------------------------ | ---------------------- | ---------------------- |
| **P0**   | **Runtime Evidence Mandatory** | 실제 실행 증적 존재    | Evidence ID            |
| P1       | Transaction Begin Evidence     | TX 시작 확인           | TX BEGIN               |
| P1       | Transaction Commit Evidence    | 정상 종료 COMMIT       | COMMIT                 |
| P1       | Transaction Rollback Evidence  | 오류 시 ROLLBACK       | ROLLBACK               |
| **P1**   | Timeout Policy                 | 거래 Timeout 정책      | timeout 값/결과        |
| P1       | Timeout Evidence               | 실제 Timeout 발생/처리 | Timeout Trace          |
| **P1**   | DB Query Timeout               | SQL 실행시간 통제      | Query Timeout          |
| P1       | SQL Elapsed                    | SQL 수행시간 측정      | SqlId + elapsed        |
| **P1**   | Sensitive Log Masking          | 개인정보/Token 마스킹  | Log Evidence           |
| P2       | Thread Threshold               | Thread 임계치          | active/max threads     |
| P2       | Hikari Threshold               | DB Pool 임계치         | active/idle/max        |
| P2       | JVM/GC Threshold               | Heap/GC 이상 여부      | JVM/GC metrics         |
| P2       | Capacity Threshold             | Capacity 기준 검사     | TPS/p95/자원           |
| P1       | Runtime Traceability           | 실행정보 연결          | ServiceId/GUID/TraceId |

R4는 단순히 “코드에 Transaction이 있다”가 아니라 `TX BEGIN → SQL → COMMIT/ROLLBACK`을 같은 ServiceId/GUID로 실제 증명하도록 요구합니다.

---

# 6. MSG — 표준전문 Rule

이 영역은 기존 자료에 실제 Rule ID가 있습니다.

| Rule ID   | 규칙                                     | 판정 기준           |
| --------- | ---------------------------------------- | ------------------- |
| `MSG-001` | Header + Business Payload 분리           | `hdr_nhnis + dto`   |
| `MSG-002` | ServiceId는 공통 Header에서 관리         | `rms_svc_c`         |
| `MSG-003` | GUID 모든 거래 유지                      | 요청~응답 동일 GUID |
| `MSG-004` | 업무 계층에 전체 전문 전달 금지          | 업무에는 DTO만      |
| `MSG-005` | 업무에는 필요한 DTO만 전달               | Handler/Service DTO |
| `MSG-006` | Framework 공통정보는 Context 사용        | `ServiceContext`    |
| `MSG-007` | 업무 프로그램의 응답 JSON 직접 조립 금지 | Resolver 사용       |
| `MSG-008` | 오류는 Exception으로 전달                | `BizException` 등   |
| `MSG-009` | 정상 `dto` / 오류 `result`               | 응답 Envelope       |
| `MSG-010` | GUID + ServiceId 추적성 유지             | 로그/Context 연계   |
| `MSG-011` | 민감정보 로그 마스킹                     | Token/개인정보 보호 |
| `MSG-012` | 전문 버전 하위호환                       | Contract 호환성     |

이 규칙은 PDMG 전문 구조인 `hdr_nhnis + dto`, 오류 시 `hdr_nhnis + result`와 연결됩니다.

---

# 7. DOMAIN — 업무 도메인 경계 Rule

MG↔MK 도메인 연계 자료에는 12개의 Rule ID가 이미 정의되어 있습니다.

| Rule ID        | 규칙                                           | 성격     |
| -------------- | ---------------------------------------------- | -------- |
| `R-DOMAIN-001` | MG와 MK를 독립 Business Domain으로 관리        | 필수     |
| `R-DOMAIN-002` | 타 도메인 DAO 직접 호출 금지                   | **금지** |
| `R-DOMAIN-003` | 타 도메인 Mapper 직접 호출 금지                | **금지** |
| `R-DOMAIN-004` | 타 도메인 전용 Table 직접 갱신 금지            | **금지** |
| `R-DOMAIN-005` | 역방향 호출에도 동일 규칙 적용                 | 필수     |
| `R-DOMAIN-006` | 도메인 간 호출은 공개 ServiceId 사용           | 필수     |
| `R-DOMAIN-007` | 별도 WAR이면 HTTP + 표준전문 사용              | 필수     |
| `R-DOMAIN-008` | WAR 간 Java Project Dependency 업무호출 금지   | **금지** |
| `R-DOMAIN-009` | MG→MK→MG 순환 동기 호출 금지                   | **금지** |
| `R-DOMAIN-010` | 호출자는 상대 내부 Facade/Service를 알지 않음  | 필수     |
| `R-DOMAIN-011` | 데이터 변경 책임은 데이터 소유 도메인          | 필수     |
| `R-DOMAIN-012` | ServiceId·Timeout·오류·로그·권한을 계약에 포함 | 필수     |

핵심은:

```text
잘못된 구조
MG Service
   ↓
MK DAO
   ↓
MK Table
```

가 아니라,

```text
정상 구조

MG Service
   ↓
MG→MK Client
   ↓
HTTP + 표준전문
   ↓
MK 공개 ServiceId
   ↓
MK Service
   ↓
MK DAO
   ↓
MK Table
```

입니다.

---

# 8. Closed Loop / Governance Rule

`reference-rules.json`에서 애플리케이션 구조와 별도로 관리해야 할 규칙입니다.

| 우선순위 | Rule                         | 목적                           |
| -------- | ---------------------------- | ------------------------------ |
| **P0**   | Runtime Evidence Mandatory   | 실행 증적 없는 PASS 방지       |
| **P0**   | Artifact Hash Mandatory      | 검증한 WAR/JAR 식별            |
| **P0**   | Approval Mandatory           | 사람 승인 우회 방지            |
| P1       | OM Catalog Match             | 실제 Service와 OM Catalog 정합 |
| P1       | WAR Dependency               | 애플리케이션 경계 검증         |
| P1       | Session Policy               | 세션 정책 검증                 |
| P1       | EAI Timeout                  | 외부 연계 Timeout 검증         |
| P2       | Documentation Link Integrity | 문서 링크 유효성               |
| P2       | Naming 확장규칙              | 추가 네이밍 표준               |
| P2       | Capacity Threshold           | 운영 Capacity Gate             |

Closed Loop 마스터 프롬프트에서 P0/P1/P2 우선순위를 이와 같이 명시하고 있습니다.

## 9. 우선순위만 다시 보면

| Priority | 의미                     | 대표 Rule                                                                                                                         |
| -------- | ------------------------ | --------------------------------------------------------------------------------------------------------------------------------- |
| **P0**   | 반드시 먼저 자동검사     | ServiceId Unique, Handler Mapping, Layer 금지규칙, SQL ID Unique, Private Key Exposure, Runtime Evidence, Artifact Hash, Approval |
| **P1**   | 실행 정책/보안/운영 계약 | Transaction Owner, Timeout, Session, WAR Dependency, Masking, EAI/DB Timeout, OM Catalog                                          |
| **P2**   | 운영 품질·지속관리       | Capacity, Thread, Hikari, GC, Naming 확장, Document Link                                                                          |

---

## 10. 최종적으로 `reference-rules.json`은 이런 구조가 됩니다

```text
30-CONFORMANCE/
└─ reference-rules.json
      │
      ├─ R1 STRUCTURAL
      │    ├─ ServiceId Unique
      │    ├─ Handler Mapping
      │    ├─ Layer Dependency
      │    ├─ Package
      │    └─ Mapper/SQL
      │
      ├─ R2 FRAMEWORK
      │    ├─ TCF Entry
      │    ├─ STF/ETF
      │    ├─ Dispatcher
      │    ├─ Timeout
      │    └─ Transaction
      │
      ├─ R3 SECURITY
      │    ├─ JWT
      │    ├─ Private Key
      │    ├─ JWKS
      │    └─ Refresh/Revoke
      │
      ├─ R4 RUNTIME
      │    ├─ TX Evidence
      │    ├─ Timeout Evidence
      │    ├─ SQL Elapsed
      │    ├─ Thread/Hikari/JVM
      │    └─ Logging
      │
      ├─ MESSAGE
      │    └─ MSG-001 ~ MSG-012
      │
      ├─ DOMAIN
      │    └─ R-DOMAIN-001 ~ 012
      │
      └─ GOVERNANCE
           ├─ Evidence
           ├─ Artifact Hash
           └─ Approval
```

따라서 현재까지 찾은 것을 기준으로 하면 **약 50개 수준의 세부 Rule 후보**가 있습니다. 다만 이것들을 곧바로 모두 `VERIFIED`로 선언해서는 안 되고, 실제 `pdmg-ui / pdmg-fw / pdmg-jwt / pdmg-service` 소스와 대조해 `VERIFIED`, `CANDIDATE`, `VARIANT`, `DEPRECATED`를 붙인 뒤 `reference-rules.json`의 공식 Baseline으로 승격시키는 것이 맞습니다.
