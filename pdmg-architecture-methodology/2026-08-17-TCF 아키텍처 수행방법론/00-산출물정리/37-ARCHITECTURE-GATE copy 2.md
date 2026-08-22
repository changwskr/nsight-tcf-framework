# NSIGHT Architecture Gate Status — HG90 Candidate Review

| Gate | 상태 | 핵심 결과 | 다음 조건 |
|---|---|---|---|
| G00 Source Baseline | **CONDITIONAL PASS** | Scope/Canonical 기준화 | 운영 Build/Config Evidence 보강 |
| G10 Vision/NFR | **PASS** | Vision/5대 NFR 확정 | Runtime NFR 달성은 G60/G80 |
| G20 Big Picture/Logical | **CONDITIONAL PASS** | Domain/ServiceId 원칙 기준화 | Catalog/Ownership 전수화 |
| G30 Physical | **CONDITIONAL PASS** | 71 Server 및 WEB/WAS 모델 | Server→JVM→WAR/Route evidence |
| G40 Mechanism | **CONDITIONAL PASS** | TCF/PDMG Source conformance | TX/Timeout runtime evidence |
| G50 Security/Data/Integration | **CONDITIONAL PASS** | JWT/Data/Integration 기준화 | Key SoT/Auth/Ownership/Deadline |
| G60 Capacity/Runtime | **CONDITIONAL PASS** | Versioned Capacity baseline | Runtime approved values |
| G70 Operations/HA-DR/Deployment | **CONDITIONAL PASS** | Ops/HA/Deploy model | Failover/Session/Rollback evidence |
| G80 Closed Loop/Drift | **HOLD** | Rule/Model/Static Scan/Register 완료 | Mandatory Runtime/P0 closure |
| HG90 Final Human Gate | **HOLD** | Candidate Review Package 작성 | G80 재평가 후 Human Sign-off |

현재 승인상태:

```text
Architecture Review Baseline : AVAILABLE
Production Runtime Baseline  : NOT APPROVED
G80                         : HOLD
HG90                        : HOLD
```

---

## G20 — Big Picture / Logical Architecture

**판정: CONDITIONAL PASS**

### Conditional Pass 조건

| Condition ID | 조건 | 우선순위 | 다음 Gate |
|---|---|---:|---|
| G20-C01 | 전체 Domain Catalog/Owner 확정 | P0 | G30/G40 |
| G20-C02 | Domain별 Owned Table/View Catalog | P0 | G50 |
| G20-C03 | Public ServiceId/Integration Contract Registry | P0 | G40/G50 |
| G20-C04 | 71대 서버 ↔ JVM ↔ Application/WAR 실제 매핑 | P0 | G30/G40 |
| G20-C05 | Apache→Tomcat 실제 Routing Config 증적 | P1 | G30/G70 |
| G20-C06 | ServiceId Deadline/Timeout Metadata 연결 | P0 | G40/G60 |
| G20-C07 | OM/Runtime Evidence 책임 경계 확정 | P0 | G70/G80 |

---

## G30 — Physical Architecture

**판정: CONDITIONAL PASS**

### 완료

- Physical Server Baseline 71대 기준화
- System Group / Role / Hostname Physical Model
- WEB Server / Apache Instance 분리
- WAS Server / Tomcat JVM / Application 분리
- Container=Tomcat JVM Instance 표준화
- Apache Multi-Listen / Connector Port Routing 모델
- 운영 Cross Routing / Application Peer JVM HA 모델
- 개발 Consolidation / 운영 Isolation+HA 원칙
- 운영↔DR 대표 Pair Mapping
- Minimum/Allocated/Capacity Specification 분리
- Physical Inventory Data Model 정의

### 조건

| Condition ID | 조건 | 우선순위 | 후속 Gate |
|---|---|---:|---|
| G30-C01 | 71대 Server↔JVM↔Application/WAR 전수 Mapping | P0 | G40 |
| G30-C02 | Apache 실제 Routing Config Evidence | P0 | G70 |
| G30-C03 | Tomcat CATALINA_BASE/server.xml/setenv.sh Evidence | P0 | G60 |
| G30-C04 | Application HA Peer JVM Catalog | P0 | G60/G70 |
| G30-C05 | 운영↔DR 전수 Mapping + RTO/RPO | P0 | G70 |
| G30-C06 | 삭제/Review/Appliance Resource 정규화 | P1 | G60 |

### 현재 진행 위치

```text
G00  CONDITIONAL PASS
  ↓
G10  PASS
  ↓
G20  CONDITIONAL PASS
  ↓
G30  CONDITIONAL PASS
  ↓
G40  CONDITIONAL PASS
  ↓
G50  NEXT
```


---

## G40 — Mechanism / Source Conformance

**판정: CONDITIONAL PASS**

### 완료

- PDMG System/TCF/Business 선후처리 Source 검증
- PDMG/NSIGHT ServiceId Dispatcher 및 중복 Fail-Fast 검증
- PDMG Worker + TransactionTemplate AS-IS 검증
- NSIGHT TCF Timeout Worker와 Facade Transaction Boundary 분리 확인
- STF/ETF Responsibility Expansion 확인
- Standard Message/Context/Error Ownership 비교
- Source-level Conformance GAP 등록

### 조건

| Condition ID | 조건 | 우선순위 | 후속 Gate |
|---|---|---:|---|
| G40-C01 | Facade Transaction Owner 원칙과 Service TX 예외 정리 | P0 | G60 |
| G40-C02 | Timeout 시 Late Commit/Connection 반환 Runtime Test | P0 | G60 |
| G40-C03 | DB Query Timeout 전수 검증 | P0 | G60 |
| G40-C04 | Worker RequestAttributes 전파 정책 ADR | P0 | G50/G60 |
| G40-C05 | `om-service`/`tcf-om` Runtime Scope 확정 | P0 | G00/G40 |
| G40-C06 | PDMG↔NSIGHT ServiceId Mapping | P0 | G50/G80 |
| G40-C07 | Standard Message Contract Mapping | P0 | G50 |
| G40-C08 | HTTP 200 Error Status 정책 | P1 | G50/G70 |
| G40-C09 | Console Trace 운영 제거/제어 | P1 | G70 |
| G40-C10 | Context/ThreadLocal Leak Test | P0 | G60 |


---

## G50 — Security / Data / Integration

**판정: CONDITIONAL PASS**

### 완료

- SSO/JWT/Session 책임분리 및 Source 검증
- Access/Refresh/Rotation/Revoke/JWKS 구조 검증
- Gateway JWT+Session Hybrid 인증경로 검증
- RDW/ADW 및 FAST/DEEP 데이터 경계 기준화
- MG↔MK Domain Contract 및 `tcf-eai` HTTP/JSON Source 검증
- Data Migration Source→Stage→Target→Validation 기준화

### 조건

| Condition ID | 조건 | 우선순위 | 후속 Gate |
|---|---|---:|---|
| G50-C01 | KMS/HSM 기반 JWT Signing Key SoT 확정 | P0 | G50/G70 |
| G50-C02 | kid Version/Key Rotation/JWKS Grace Period | P0 | G50/G70 |
| G50-C03 | SSO Assertion Verification Owner | P0 | G50 |
| G50-C04 | Denylist Enforcement 전수 검증 | P0 | G50/G70 |
| G50-C05 | JWT vs Session 운영모드 ADR | P0 | G50/G70 |
| G50-C06 | ServiceId/Menu/Data Authorization | P0 | G50/G80 |
| G50-C07 | Domain/Table/View Owner Catalog | P0 | G50/G80 |
| G50-C08 | RDW/ADW Read/Write Matrix | P0 | G50/G60 |
| G50-C09 | Integration Remaining Deadline 전파 | P0 | G60 |
| G50-C10 | Service-to-Service Authentication | P0 | G50/G70 |
| G50-C11 | Retry/CB/Bulkhead/Idempotency 정책 | P1 | G60/G70 |
| G50-C12 | Enterprise Gateway ↔ tcf-eai Route Registry | P0 | G70 |
| G50-C13 | Migration Source→Target Registry | P0 | G70 |
| G50-C14 | Migration Go/No-Go / Rollback Runbook | P0 | G70 |
| G50-C15 | Header 생성/신뢰주체 Mapping | P0 | G50/G80 |

### 현재 진행 위치

```text
G00  CONDITIONAL PASS
  ↓
G10  PASS
  ↓
G20  CONDITIONAL PASS
  ↓
G30  CONDITIONAL PASS
  ↓
G40  CONDITIONAL PASS
  ↓
G50  CONDITIONAL PASS
  ↓
G60  CONDITIONAL PASS
  ↓
G70  NEXT
```


---

## G60 — Capacity / Runtime

**판정: CONDITIONAL PASS**

### 완료

- User→Session→Concurrent Request→TPS→VM→Thread→Pool→DB Capacity Chain 기준화
- Peak 1,200 / Stress 1,800 TPS 목표 기준화
- 500 TPS Legacy와 855 TPS Working Baseline 분리
- 684 TPS/VM @80% Operational Working Capacity 정의
- Tomcat/JVM/Hikari/Timeout Versioned Baseline 작성
- N-1/Center Failure Capacity 분석
- Runtime Test Matrix 및 Evidence Schema 작성

### 조건

| Condition ID | 조건 | 우선순위 | 후속 Gate |
|---|---|---:|---|
| G60-C01 | 500 vs 855 VM Runtime 승인 TPS | P0 | G60/G70 |
| G60-C02 | Session 60m vs 90m ADR | P0 | G70 |
| G60-C03 | Tomcat 800~1,000 Runtime 승인 | P0 | G60 |
| G60-C04 | Hikari Pool DB Hold-Time/Session 검증 | P0 | G60 |
| G60-C05 | JVM Heap 업무유형별 승인 | P0 | G60 |
| G60-C06 | ServiceId별 Timeout Chain 검증 | P0 | G60/G80 |
| G60-C07 | Timeout Late Commit/Connection Return Test | P0 | G60/G80 |
| G60-C08 | 2+2 vs 3+3 HA Capacity ADR | P0 | G70 |
| G60-C09 | Stress 1,800 TPS Evidence | P0 | G60 |
| G60-C10 | Session Failover/DeltaManager Test | P0 | G70 |
| G60-C11 | 실제 Runtime Config Snapshot | P0 | G70 |
| G60-C12 | L4 Idle/Sticky/KeepAlive 정합성 | P1 | G70 |
| G60-C13 | Error/Timeout 합격 임계치 | P1 | G70/G80 |
| G60-C14 | AP Pool×DB Session 상한 검증 | P0 | G60/G70 |

### 현재 진행 위치

```text
G00  CONDITIONAL PASS
  ↓
G10  PASS
  ↓
G20  CONDITIONAL PASS
  ↓
G30  CONDITIONAL PASS
  ↓
G40  CONDITIONAL PASS
  ↓
G50  CONDITIONAL PASS
  ↓
G60  CONDITIONAL PASS
  ↓
G70  NEXT
```


---

## G70 — Operations / HA-DR / Deployment

**판정: CONDITIONAL PASS**

### 완료

- Control Plane / Runtime Plane 운영책임 기준화
- GUID + ServiceId Logging/Observability 체계 기준화
- ImageLog PRE/POST/EXCEPTION 운영증적 모델 정리
- 다중 Host/JVM/WAR OM 통합모델 정의
- WEB/WAS/JVM/Application/DB/Center Failure Domain 분리
- 운영↔DR Pair 및 Failover/Failback 표준 흐름 정의
- DeltaManager 센터내 Session HA Working Candidate 정리
- 2+2 vs 3+3/8Core Scale-Out ADR 후보화
- GitLab/GitLab Runner/Nexus/eCAMS Deployment 역할 기준화
- Rolling Deploy Residual Capacity Rule 정의
- HW/SW 도입과 DR/성능/배포 Gate Dependency 연결

### 조건

| Condition ID | 조건 | 우선순위 | 후속 Gate |
|---|---|---:|---|
| G70-C01 | 전체 운영↔DR Pair Catalog | P0 | G70/G80 |
| G70-C02 | 시스템별 RTO/RPO 승인 | P0 | G70/G80 |
| G70-C03 | 2+2 vs 3+3/8Core HA Topology ADR | P0 | G70/G80 |
| G70-C04 | Session Strategy ADR + Failover Test | P0 | G70/G80 |
| G70-C05 | 실제 GSLB/L4/Apache Routing Config Evidence | P0 | G70/G80 |
| G70-C06 | N-1/Center Failover/Failback Runtime Evidence | P0 | G80 |
| G70-C07 | OM 전체 Runtime Catalog | P0 | G80 |
| G70-C08 | GUID+ServiceId E2E Trace Evidence | P0 | G80 |
| G70-C09 | Critical Change Approval/Audit/Expiration | P0 | G80 |
| G70-C10 | GitLab→Runner→Nexus/eCAMS Pipeline Evidence | P0 | G80 |
| G70-C11 | Rolling Deploy Residual Capacity Test | P0 | G80 |
| G70-C12 | Rollback + DB/Config Compatibility Test | P0 | G80 |
| G70-C13 | Alert→Runbook→Evidence 폐쇄루프 | P0 | G80 |
| G70-C14 | Migration Go/No-Go/Rollback Runbook | P0 | G80 |
| G70-C15 | JWT Key/Session/Route Critical 운영변경 통제 | P0 | G80 |

### 현재 진행 위치

```text
G00  CONDITIONAL PASS
  ↓
G10  PASS
  ↓
G20  CONDITIONAL PASS
  ↓
G30  CONDITIONAL PASS
  ↓
G40  CONDITIONAL PASS
  ↓
G50  CONDITIONAL PASS
  ↓
G60  CONDITIONAL PASS
  ↓
G70  CONDITIONAL PASS
  ↓
G80  NEXT
```

---

## G80 — Closed Loop / Drift

**판정: HOLD**

### 완료

- Architecture Rule Registry 및 machine-readable JSON 생성
- Source static conformance scan 수행
- Partial Architecture Model 추출
- Conformance Test/Runtime Evidence Registry 작성
- Drift/GAP/Risk/ADR/Open Issue 통합
- Requirement/UI Traceability Draft 생성

### Blocking Conditions

| Condition ID | 조건 | 우선순위 | 후속 |
|---|---|---:|---|
| G80-C01 | JWT KMS/HSM Key SoT + kid Rotation | P0 | G80/HG90 |
| G80-C02 | Transaction/Timeout Fault Runtime Evidence | P0 | G80/HG90 |
| G80-C03 | P600/P1200/S1800/N-1 Runtime Evidence | P0 | G80/HG90 |
| G80-C04 | Center/Session Failover/Failback Evidence | P0 | G80/HG90 |
| G80-C05 | 71 Server→JVM→WAR + actual Routing Mapping | P0 | G80/HG90 |
| G80-C06 | GUID+ServiceId E2E Runtime Trace | P0 | G80/HG90 |
| G80-C07 | Architecture Model JSON Schema/Full Traceability | P0 | G80/HG90 |
| G80-C08 | CI/CD Rollback/DB/Config Compatibility Evidence | P0 | G80/HG90 |
| G80-C09 | Migration Reconciliation/Go-No-Go Evidence | P0 | G80/HG90 |
| G80-C10 | Critical ADR Approval | P0 | HG90 |

### 현재 진행 위치

```text
G00  CONDITIONAL PASS
 ↓
G10  PASS
 ↓
G20  CONDITIONAL PASS
 ↓
G30  CONDITIONAL PASS
 ↓
G40  CONDITIONAL PASS
 ↓
G50  CONDITIONAL PASS
 ↓
G60  CONDITIONAL PASS
 ↓
G70  CONDITIONAL PASS
 ↓
G80  HOLD
 ↓
HG90 WAIT / NOT APPROVABLE YET
```



---

## HG90 — Final Human Architecture Gate

**판정: HOLD**

### 사유

- G80 HOLD 유지
- JWT Key SoT/Rotation P0
- Mandatory Runtime Runs OPEN
- Timeout Late Commit/Connection Return evidence OPEN
- 71 Server→JVM→WAR/Route mapping OPEN
- Session/HA/RTO-RPO ADR + runtime evidence OPEN
- Architecture Model Schema/Full Traceability OPEN
- Deploy rollback 및 Migration Go/No-Go evidence OPEN

### 재심 조건

```text
P0 FAIL_TARGET = 0
P0 OPEN_RUNTIME = 0 or approved exception
Critical ADR approved
Model Schema PASS
Mandatory Runs complete
Evidence Manifest complete
```

자세한 Human Gate 기준은 `90-HG90-HUMAN-GATE.md`와 `91-HG90-APPROVAL-CHECKLIST.md`를 참조한다.


---

## P0 Closure Wave 1 — 2026-08-19

**Gate 변화: 없음 — G80 HOLD / HG90 HOLD 유지**

| 항목 | 결과 | Gate 영향 |
|---|---|---|
| Architecture Model JSON Schema | CLOSED_STATIC | Checklist 해소 |
| Model Validator | PASS (380 nodes / 380 edges / dangling 0) | Checklist 해소 |
| Policy TX Timeout | Static path confirmed | Runtime proof 대기 |
| DB Query Timeout | Static path confirmed | Runtime proof 대기 |
| Worker Context Cleanup | Static path confirmed | Leak test 대기 |
| JWT KMS/HSM SoT | FAIL_TARGET | HOLD 유지 |
| JWT kid/Rotation | FAIL_TARGET | HOLD 유지 |
| Standard business Service TX | 2 duplicate candidates | HOLD 유지 |
| Runtime mandatory runs | OPEN | HOLD 유지 |
| 71 Server→JVM→WAR | OPEN | HOLD 유지 |

세부: `92-P0-CLOSURE-MASTER.md`, `93-P0-STATIC-EVIDENCE.md`, `94-P0-RUNTIME-EXECUTION-PLAN.md`, `95-P0-ADR-DECISION-PACK.md`.


---

## P0 Closure Wave 2A — Change Specification

**판정: SPEC READY / G80 HOLD 유지 / HG90 HOLD 유지**

| Change | Spec | Gate 영향 |
|---|---|---|
| JWT Signing Key Provider | READY | 구현 + RUN-JWT-ROTATE 필요 |
| Versioned kid / JWKS Grace | READY | 구현 + RUN-JWT-ROTATE 필요 |
| Facade TX Owner Cleanup | READY | 구현 + rollback test 필요 |
| Timeout Safety Harness | READY | 구현 + RUN-TIMEOUT/RUN-SLOWSQL 필요 |

이번 Wave는 구현준비 완료 상태를 의미하며 Runtime 승인상태를 의미하지 않는다.
