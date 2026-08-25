# P0 ADR Decision Pack — Human Approval Draft

> 상태: **DRAFT / HUMAN APPROVAL REQUIRED**

## ADR-SEC-001 — JWT Signing Key Source of Truth

**권고안:** Production은 KMS/HSM 또는 승인된 중앙 Key Store의 Private Key를 Signing Key SoT로 사용한다. Process-local Key 생성은 DEV 전용으로 제한한다.

- 이유: 다중 Issuer, 재기동, Key 감사/폐기/회전 요구
- 금지: Production `RSAKeyGenerator` 즉석 생성
- 검증: `RUN-JWT-ROTATE`

## ADR-SEC-002 — kid / Rotation / JWKS Grace

**권고안:** `kid=<key-family>-<version>`을 사용하고 JWKS는 Active + Previous Grace Key를 일정기간 동시 제공한다.

- Rotation 시 신규 Token은 Active Key로만 발급
- 이전 Token 검증을 위해 Previous Public Key는 Access Token 최대수명+운영여유 동안 유지
- Private Previous Key의 Sign 사용은 금지

## ADR-TX-001 — Transaction Owner

**권고안:** 표준 온라인 업무의 기본 TX Owner는 Facade/Use Case Boundary로 한다.

```text
Handler
 ↓
Facade @Transactional   ← 기본 Owner
 ↓
Service                 ← 기본적으로 TX 선언 없음
 ↓
DAO/Mapper
```

Service TX는 `REQUIRES_NEW`, 독립 업무단위 등 명시적 예외만 ADR/Rule Exception으로 허용한다. 현재 `EbUserService.create`, `EpUserEventService.receive`는 중복선언 후보로 정리한다.

## ADR-TMO-001 — Timeout Cancellation Model

**권고안:** Online Timeout은 Deadline/응답통제 Owner이며 DB Transaction rollback의 유일한 보장수단으로 `Thread.interrupt`를 간주하지 않는다.

필수 방어:

```text
DB Query Timeout < TX Timeout < Online Timeout < Client Timeout
```

그리고 Timeout 이후 TX rollback/Connection return을 Runtime Evidence로 증명한다.

## ADR-SES-001 — Session Strategy

**권고안 후보:** 센터 내부는 Sticky + DeltaManager 또는 외부 Session Store 중 하나를 선택하고, 센터간 Session continuity 여부는 별도 정책으로 결정한다. 현재 자료만으로 최종 선택 금지.

결정기준: Session 용량, Serialization, 장애영향, 센터 전환, 운영복잡도, DB 부하, 재로그인 허용정책.

## ADR-HA-001 — AP HA Topology

**보류:** `2+2`, `3+3`, `8Core Scale-Out`은 Runtime Approved Capacity와 N-1/센터장애 시험 전에는 최종 결정하지 않는다.

## ADR-PERF-001 — VM Runtime Capacity

**보류:** 500/855 TPS를 Runtime Evidence 없이 확정하지 않는다. 855는 Working, 500은 Legacy/Conservative로 유지한다.

## ADR-GOV-001 — Architecture Model SoT

**권고안:** JSON Model + JSON Schema + Validator + Manifest를 Machine-readable Architecture SoT로 사용하고 Markdown은 Human-readable View로 관리한다.

이번 Wave에서 Schema/Validator는 PASS했다. 단 Requirement/Screen/Table/Server/Runtime Evidence 관계가 추가되어야 완전 SoT로 승격한다.
