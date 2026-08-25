# HG90 Final Human Architecture Gate — Candidate Review

> Candidate ID: `NSIGHT-ARCH-CANDIDATE-2026-08-19`  
> Gate Date: 2026-08-19  
> Recommendation: **HOLD**

---

## 1. Gate Purpose

HG90은 문서 작성 완료 여부를 승인하는 Gate가 아니다.

```text
Requirement
↔ Architecture
↔ ADR
↔ Model
↔ Source
↔ Configuration
↔ Test
↔ Deployment
↔ Runtime Evidence
```

이 연결이 Critical Scope에서 닫혔는지 사람이 최종 판단하는 Gate다.

---

## 2. Human Gate Decision

# **HOLD**

G80이 HOLD이므로 HG90은 PASS 또는 CONDITIONAL PASS로 승격하지 않는다.

현재 Candidate는 **Architecture Review Baseline**으로 사용 가능하나 **Production Runtime Approved Baseline**은 아니다.

---

## 3. Gate Scorecard

| 평가영역 | 상태 | 근거 | Human Gate 판단 |
|---|---|---|---|
| Strategy / Vision / NFR | PASS | G10 | 승인 가능 |
| Big Picture / Logical | CONDITIONAL | G20 | Catalog 조건부 |
| Physical | CONDITIONAL | G30 | Runtime mapping 조건부 |
| Application / TCF | CONDITIONAL | G40 | TX/Timeout runtime 조건부 |
| Security | HOLD 요소 포함 | G50/G80 | JWT Key P0 종료 필요 |
| Data / Integration | CONDITIONAL | G50 | Ownership/Auth/Deadline 필요 |
| Capacity | HOLD 요소 포함 | G60/G80 | Runtime 승인값 필요 |
| HA/DR | HOLD 요소 포함 | G70/G80 | Failover/RTO/RPO evidence 필요 |
| Deployment | HOLD 요소 포함 | G70/G80 | Rollback/compatibility evidence 필요 |
| Model / Conformance | PARTIAL | G80 | Schema/traceability 보강 필요 |
| Runtime Evidence | **OPEN** | 12 Mandatory Runs | 최종 승인 불가 |
| Drift/GAP/ADR Governance | ACTIVE | Register 존재 | P0 closure 필요 |

---

## 4. P0 Human Decision Queue

| 순서 | ADR / Decision | 현재상태 | 권고 |
|---:|---|---|---|
| 1 | ADR-SEC-001 JWT Signing Key SoT | OPEN | KMS/HSM 운영키를 SoT로 확정 |
| 2 | ADR-SEC-002 kid/Rotation/JWKS Grace | OPEN | Versioned key lifecycle 승인 |
| 3 | ADR-TX-001 Transaction Owner | OPEN | Facade 기본 Owner + 예외명시 |
| 4 | ADR-TMO-001 Timeout Cancellation Model | OPEN | Worker deadline + DB/TX semantics 승인 |
| 5 | ADR-SES-001 Session Strategy | OPEN | DeltaManager/JDBC/기타 중 운영방식 확정 |
| 6 | ADR-HA-001 AP HA Topology | OPEN | 2+2/3+3/8Core 중 NFR 기반 결정 |
| 7 | ADR-DR-001 Center Failure Continuity | OPEN | Session/Data continuity 정책 승인 |
| 8 | ADR-DR-002 RTO/RPO Class | OPEN | 서비스등급별 승인값 확정 |
| 9 | ADR-PERF-001 VM Capacity | OPEN | Runtime Evidence로 500/855 승격값 결정 |
| 10 | ADR-GOV-001 Architecture Model SoT | OPEN | JSON Schema + Manifest를 SoT로 승인 |
| 11 | ADR-DEP-001 Production Pipeline | OPEN | Git→Build→Artifact→eCAMS→Rollback 확정 |
| 12 | ADR-OPS-001 OM Control Plane | OPEN | 운영 Catalog/Policy/Metric 책임 확정 |

---

## 5. Approval Classes

### A. 지금 승인 가능한 Architecture Principles

- Vision → Big Picture → Logical → Physical → Mechanism → Runtime 방법론
- 5대 NFR
- ServiceId 중심 거래 추적성
- Domain Boundary 및 타 Domain DAO/Mapper/Table 직접접근 금지
- WEB/Apache, WAS/Tomcat 실행단위 구분
- WAS Server ≠ Tomcat JVM ≠ Application/WAR
- RDW / ADW 역할분리
- GUID + ServiceId Observability 원칙
- Document → Model → Code → Test → Runtime Evidence Closed Loop 원칙

### B. Working Baseline으로만 승인 가능한 항목

- 16Core 855 TPS Working Capacity
- Session 90m Working Candidate
- Tomcat 800~1,000 검증범위
- Hikari 120~150 / SingleView 150~180 검증범위
- DeltaManager + Sticky 후보
- 2+2/3+3/8Core HA 후보
- Facade Transaction Owner 방향

### C. 현재 승인 금지 항목

- Runtime Approved TPS
- Production-ready HA/DR
- JWT Key 운영 안정성
- Timeout safe cancellation
- Full ServiceId E2E Runtime Trace
- Deployment rollback safety
- Migration cutover readiness
- Complete Architecture Model SoT

---

## 6. Exception Policy

P0 항목을 해소하지 못하고 오픈해야 하는 경우 일반적인 `CONDITIONAL PASS`로 처리하지 않는다.

필수 항목:

```text
Exception ID
Risk Owner
Business Owner
Technical Owner
Reason
Impact
Compensating Control
Expiration Date
Rollback / Contingency
Evidence
Formal Approval
```

Security Key, Transaction Consistency, Data Integrity와 같이 Critical 영향이 있는 항목은 단순 Risk Acceptance로 우회하지 않고 별도 승인체계를 사용한다.

---

## 7. Re-Gate Procedure

```text
P0 Action 완료
  ↓
Evidence 등록
  ↓
Rule / Conformance 재실행
  ↓
Runtime Run 재실행
  ↓
Drift/GAP 상태 갱신
  ↓
ADR 승인
  ↓
G80 Re-evaluate
  ↓
HG90 Re-submit
```

---

## 8. Gate Sign-off Template

| Role | Name | Decision | Date | Condition / Comment |
|---|---|---|---|---|
| Chief Architect |  | HOLD / PASS / REJECT |  |  |
| Application Architect |  |  |  |  |
| Infra Architect |  |  |  |  |
| Data Architect |  |  |  |  |
| Security Architect |  |  |  |  |
| Operations |  |  |  |  |
| Performance |  |  |  |  |
| PMO / Project |  |  |  |  |

현재 문서는 Sign-off 요청서가 아니라 **HOLD 사유와 재심 조건을 명확히 하는 Candidate Review 문서**다.


---

## 9. P0 Closure Wave 1 Update

정적 Closure로 `Architecture Model JSON Schema`와 `Model Validator`는 해소됐다. Timeout/Query Timeout/Context cleanup 구현경로도 Source에서 확인했다.

그러나 JWT Key/Rotation, Service TX 중복, Runtime Mandatory Runs, 71 Server Runtime Mapping, HA/Session/Deployment/Migration Evidence는 미종료이므로 **HG90 = HOLD 유지**다.

다음 재심은 `94-P0-RUNTIME-EXECUTION-PLAN.md`의 Mandatory Run과 `95-P0-ADR-DECISION-PACK.md`의 Human Approval 이후 수행한다.
