# G50 Security / Data / Integration 실행 결과

## 1. Gate 판정

**G50 = CONDITIONAL PASS**

Security, Data, Integration의 목표 방향은 충분히 명확하고, JWT/JWKS/Refresh/Domain HTTP Integration 등 일부는 실제 Source까지 확인되었다. 그러나 운영 승인을 위해 Key Management, Authorization, Service-to-Service Authentication, Deadline Propagation, Data Ownership Catalog, Migration Control을 반드시 닫아야 한다.

---

## 2. 완료 항목

### Security

- SSO/JWT/Session 역할 분리
- `tcf-jwt` Access/Refresh/Revoke Source 확인
- Refresh Token Hash 저장 확인
- Refresh Rotation 기본 활성화 확인
- Denylist/Revoke 모델 확인
- JWKS Endpoint 확인
- Gateway JWKS/Issuer/Audience 검증 확인
- JWT + Session Hybrid Gateway Mode 확인
- Header ↔ JWT Claim 정합성 확인

### Data

- RDW/ADW 물리·책임 분리 기준화
- FAST/DEEP 데이터 경로 기준화
- CDC/Kafka/ETL 역할 분리
- No P2P / DB Link 금지 정책 연결
- Data Ownership Metadata 정의

### Integration

- MG↔MK Public ServiceId 경계 기준화
- `tcf-eai` HTTP/JSON Client Source 확인
- Standard Message/Context Propagation 확인
- Connect/Read Timeout 설정 확인
- Cross-Domain Local TX 비공유 원칙 확인
- Retry/CB/Bulkhead 현재 미구현 상태 명시

### Migration

- Source→Stage→Target 구조 기준화
- 반복 이행/차수별 환경 정의
- Reconciliation/Validation 모델 정의
- Cut-over/Go-NoGo/Rollback 요구 정의

---

## 3. G50의 가장 중요한 Source Drift

### G50-D01 — KMS 설계 vs Runtime Ephemeral Key

```text
DESIGN
KMS → Private/Public Key → JWT Sign

SOURCE
RSAKeyGenerator(2048) → Process-local Key Pair
```

**Severity: P0 / HOLD CONDITION**

운영에서는 Runtime 생성 Key를 사용하지 않도록 Key SoT ADR이 필요하다.

---

## 4. Critical G50 GAP / Risk

| ID | 내용 | 우선순위 | 후속 Gate |
|---|---|---:|---|
| G50-C01 | KMS/HSM 기반 JWT Signing Key SoT 확정 | **P0** | G50/G70 |
| G50-C02 | `kid` Version + Key Rotation/JWKS Grace Period | **P0** | G50/G70 |
| G50-C03 | SSO Assertion/Authorization Code Verification Owner | **P0** | G50 |
| G50-C04 | JWT Denylist Enforcement를 모든 보호 진입점에서 검증 | **P0** | G50/G70 |
| G50-C05 | Session vs JWT Hybrid 운영모드 ADR | **P0** | G50/G70 |
| G50-C06 | ServiceId/Menu/Data Authorization Model | **P0** | G50/G80 |
| G50-C07 | Domain/Table/View Owner Catalog | **P0** | G50/G80 |
| G50-C08 | RDW/ADW Read/Write Matrix | **P0** | G50/G60 |
| G50-C09 | Integration Remaining Deadline 전파 | **P0** | G60 |
| G50-C10 | Service-to-Service Authentication | **P0** | G50/G70 |
| G50-C11 | Retry/CB/Bulkhead/Idempotency 정책/구현 | P1 | G60/G70 |
| G50-C12 | Enterprise Gateway(CruzAPIM 등) ↔ tcf-eai Route Registry | **P0** | G70 |
| G50-C13 | Source→Target Migration Mapping Registry | **P0** | G70 |
| G50-C14 | Migration Go/No-Go + Rollback Runbook | **P0** | G70 |
| G50-C15 | Standard Message Header 생성주체/신뢰주체 Mapping | **P0** | G50/G80 |

---

## 5. Architecture Decision 후보

1. ADR-SEC-001 JWT Key Management — KMS Load vs KMS Sign/HSM
2. ADR-SEC-002 Authentication Mode — JWT-only vs Hybrid JWT+Session
3. ADR-SEC-003 Key Rotation & JWKS Versioning
4. ADR-AUTH-001 ServiceId/Data Authorization
5. ADR-INT-001 Cross-Domain Integration Gateway Policy
6. ADR-INT-002 Remaining Deadline Propagation
7. ADR-DATA-001 RDW/ADW Read-Write Boundary
8. ADR-MIG-001 Migration Validation/Go-NoGo Standard

---

## 6. 다음 단계

```text
G50 CONDITIONAL PASS
       ↓
G60 Capacity / Runtime
       ↓
TPS / Thread / Hikari / JVM
Transaction / Query / Integration Timeout
Load / Stress / N-1
Runtime Evidence
```

G60에서는 기존의 여러 Capacity 숫자를 Versioned Baseline으로 정리하고 실제 Runtime/Load Test로 승격 가능한 값을 결정한다.
