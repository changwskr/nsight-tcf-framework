# NSIGHT Architecture Executive Summary — HG90 Candidate Review

> Baseline Candidate ID: `NSIGHT-ARCH-CANDIDATE-2026-08-19`  
> Review Date: 2026-08-19 00:39 +0900  
> Current Human Gate Recommendation: **HOLD**  
> 사용 목적: 임원/PMO/수석 아키텍트가 현재 NSIGHT 아키텍처의 확정영역, 미확정영역, 오픈 전 필수조치를 빠르게 판단하기 위한 최상위 요약본.

---

## 1. 결론

NSIGHT의 **전략·논리·물리·애플리케이션·TCF·데이터·보안·운영 아키텍처의 뼈대는 기준화 가능한 수준**이다.

현재 확인된 핵심 구조는 다음과 같다.

```text
Vision / NFR
   ↓
Big Picture / Domain Boundary
   ↓
Logical Architecture
   ↓
Physical Architecture
   ↓
TCF / Transaction / Timeout / Security / Data Mechanism
   ↓
Capacity / HA-DR / Operations / Deployment
   ↓
Architecture Rule / Model / Static Conformance
```

그러나 프로젝트가 정의한 완료조건은 여기서 끝나지 않는다.

```text
Document
  ↓
Model
  ↓
Code
  ↓
Configuration
  ↓
Test
  ↓
Runtime Evidence
  ↓
Drift / GAP / ADR
  ↓
Approved Baseline
```

현재는 **Document / Model / Code Static Conformance까지 상당 부분 도달했지만 Runtime Evidence와 Critical ADR이 닫히지 않았다.**

따라서 HG90 최종 승인 권고는 다음과 같다.

| 구분 | 판정 |
|---|---|
| Architecture Design Baseline | **REVIEWABLE** |
| Source Static Conformance | **PARTIAL PASS** |
| Runtime Approved Baseline | **NOT YET** |
| Production Readiness | **NOT YET** |
| G80 Closed Loop | **HOLD** |
| HG90 Final Human Gate | **HOLD** |

---

## 2. 현재 가장 강하게 기준화된 Architecture

### 2.1 전략 및 방법론

```text
Vision
 → Big Picture
 → Logical
 → Physical
 → Mechanism
 → Runtime
```

5대 NFR:

- Performance
- Availability
- Scalability
- Security
- Observability

### 2.2 Application / TCF Runtime

```text
Client
 ↓
Filter / Interceptor
 ↓
Standard Message / Context
 ↓
TCF
 ↓
STF
 ↓
Timeout / Transaction Boundary
 ↓
ServiceId Dispatcher
 ↓
Handler
 ↓
Facade
 ↓
Service
 ├─ Rule
 ├─ DAO → Mapper → SQL → DB
 └─ Integration Client
 ↓
ETF
 ↓
Standard Response / Error / Runtime Evidence
```

핵심 원칙:

- ServiceId를 거래 식별·Routing·Traceability 중심키로 사용
- Handler의 DAO/Mapper 직접 의존 금지
- Facade를 Use Case / Transaction Boundary의 기본 Owner로 사용
- 시스템 선후처리 / TCF 선후처리 / 업무 선후처리 책임 분리
- PDMG AS-IS와 NSIGHT TCF TO-BE를 별도 Baseline으로 관리

### 2.3 WEB / WAS / Runtime Unit

```text
WAS Server / VM
      ≠
Tomcat JVM Instance
      ≠
Application / WAR
```

WEB = Apache, WAS = Tomcat을 기본으로 하며 Apache Multi-Listen과 Tomcat JVM별 Connector 구조를 사용한다.

### 2.4 Data

```text
FAST
Event / Kafka / Near Real-time / RDW

DEEP
CDC / ETL / ADW / BI / OLAP
```

RDW와 ADW는 역할·부하·책임을 분리하고 Domain 간 직접 DAO/Mapper/Table 접근을 금지하는 방향으로 기준화한다.

### 2.5 Operations / Observability

```text
GUID + ServiceId
  ↓
Host / JVM / Thread
  ↓
TCF / Business
  ↓
SQL / External
  ↓
Error / ImageLog / Metric / Trace
  ↓
OM / Runtime Evidence
```

---

## 3. Current Gate Snapshot

| Gate | 상태 | 해석 |
|---|---|---|
| G00 Source Baseline | CONDITIONAL PASS | Scope/Canonical 기준은 있으나 운영 Config/Commit 일부 UNKNOWN |
| G10 Vision/NFR | **PASS** | 전략과 5대 NFR 기준화 |
| G20 Big Picture/Logical | CONDITIONAL PASS | Domain/Data/Contract 전수 Catalog 필요 |
| G30 Physical | CONDITIONAL PASS | Server→JVM→WAR 실제 매핑 필요 |
| G40 Mechanism | CONDITIONAL PASS | TX/Timeout/Context 예외와 Runtime 검증 필요 |
| G50 Security/Data/Integration | CONDITIONAL PASS | Key SoT/Auth/Data Ownership/Deadline 조건 필요 |
| G60 Capacity/Runtime | CONDITIONAL PASS | Runtime 승인 성능값 필요 |
| G70 Operations/HA-DR/Deployment | CONDITIONAL PASS | Failover/Session/Deploy Evidence 필요 |
| G80 Closed Loop/Drift | **HOLD** | Runtime Evidence와 P0 Conformance 미종료 |
| HG90 Final Human Gate | **HOLD** | 최종 Release Baseline 승인 불가 |

---

## 4. Static Architecture Conformance Snapshot

현재 정적 Source 분석에서 확인된 내용:

| 항목 | 결과 |
|---|---:|
| Source Scan Handler | 59 |
| ServiceId Mapping | 121 |
| ServiceId 보유 Module | 15 |
| Module Scope Duplicate ServiceId | 0 |
| Handler → DAO 직접 import | 0 |
| Handler → Mapper 직접 import | 0 |
| Handler Cross-Domain import 후보 | 0 |
| Facade | 50 |
| `@Transactional` Facade | 50 / 50 |
| Service `@Transactional` 예외 후보 | 4 |
| Architecture Model Node | 380 |
| Architecture Model Edge | 380 |

이 결과는 구조적 방향이 상당히 코드에 반영되어 있음을 보여주지만, Runtime 승인과 동일한 의미는 아니다.

---

## 5. HG90 Hard Blocker

다음은 최종 Baseline Release 전에 해소 또는 명시적 Risk Acceptance가 필요한 P0 항목이다.

| ID | Blocker | 현재 | 승인조건 |
|---|---|---|---|
| HB-01 | JWT Signing Key SoT | Process-local RSA / fixed kid | KMS/HSM + versioned kid + rotation evidence |
| HB-02 | Transaction / Timeout Fault Evidence | 설계/Static만 존재 | Late Commit/rollback/connection return test |
| HB-03 | Capacity Runtime Approval | 500/855 등 Versioned 값 | P600/P1200/S1800/N-1 실행결과 |
| HB-04 | Center/Session HA | 후보정책 | Failover/Failback + Session test |
| HB-05 | Physical Runtime Mapping | 71 Server baseline | Server→JVM→WAR→Route 전수 mapping |
| HB-06 | E2E Observability | 설계 존재 | GUID+ServiceId 실제 Trace evidence |
| HB-07 | Architecture Model SoT | Partial model | JSON Schema + validation + missing edges |
| HB-08 | Deployment Safety | 역할 정의 | Rolling/Rollback/DB+Config compatibility test |
| HB-09 | Migration Gate | 구조 정의 | Reconciliation/Go-NoGo/Rollback rehearsal |
| HB-10 | Critical ADR | 16 ADR 중 P0 다수 OPEN | Human approval 또는 명시적 conditional acceptance |

---

## 6. Mandatory Runtime Runs

```text
RUN-P600       General Peak 600 TPS
RUN-P1200      Design Peak 1,200 TPS
RUN-S1800      Stress 1,800 TPS
RUN-N1         AP N-1
RUN-CF         Center Failure / Failback
RUN-HIKARI     DB Pool Pressure
RUN-SLOWSQL    Slow SQL / Timeout Hierarchy
RUN-TIMEOUT    Cancel / Rollback / Connection Return
RUN-SESSION    Session Failover
RUN-ROLLING    Rolling Deployment
RUN-TRACE      GUID + ServiceId End-to-End Trace
RUN-JWT-ROTATE JWT Key Rotation / Grace Period
```

이 Run 결과가 Build/Commit/Config/ServiceId/GUID/Hostname/JVM과 연결되어야 Runtime Approved Baseline으로 승격할 수 있다.

---

## 7. Human Gate 권고

### 현재 권고

# **HG90 = HOLD**

### 허용 가능한 활동

- Architecture Review
- 설계/개발 계속 진행
- P0 Evidence 확보
- ADR 심의
- 성능/HA/보안/배포/이행 시험
- Candidate Baseline을 기준으로 Drift 추적

### 현재 허용하지 않는 표현

- "최종 운영 Baseline 확정"
- "성능 승인 완료"
- "HA/DR 검증 완료"
- "JWT 운영 Key 체계 완료"
- "Closed Loop 완료"
- "Production Ready"

---

## 8. HG90 재심 조건

```text
P0 FAIL_TARGET = 0
P0 OPEN_RUNTIME = 0 또는 승인된 Exception
Critical ADR = APPROVED
Model Schema Validation = PASS
Runtime Mandatory Run = PASS/Approved Exception
Source/Config/Test/Runtime Evidence Manifest = Complete
P0 Drift/GAP = Closed 또는 Approved Risk Acceptance
```

---

## 9. 문서 사용 순서

경영/PMO:

```text
00-EXECUTIVE-SUMMARY.md
→ 37-ARCHITECTURE-GATE.md
→ 90-HG90-HUMAN-GATE.md
→ 38-EXECUTION-ROADMAP.md
```

아키텍트/개발/운영:

```text
99-MASTER-ARCHITECTURE.md
→ 각 03~27 상세 Architecture
→ 28 Rules / 29 Model / 30 Conformance
→ 31 Runtime Evidence
→ 32~36 Drift/GAP/Risk/ADR/Open
```
