# NSIGHT Master Architecture — Candidate Baseline

> Document ID: `99-MASTER-ARCHITECTURE`  
> Candidate Baseline: `NSIGHT-ARCH-CANDIDATE-2026-08-19`  
> Status: **CANDIDATE / HG90 HOLD**  
> 이 문서는 01~38 상세 문서를 단순 병합한 것이 아니라 현재 NSIGHT Architecture의 핵심 Source of Truth를 한 번에 이해하기 위한 통합본이다.

---

# Part 1. Executive Architecture

## 1.1 Architecture Vision

NSIGHT는 단순 DW 고도화가 아니라 **데이터가 흐르고, 고객행동에 반응하고, 경영 판단을 지원하는 차세대 정보계 플랫폼**을 지향한다.

```text
기존
Batch 중심 / 익일 데이터 / 저장소
        ↓
NSIGHT
Real-time + Batch 병행
RDW + ADW 책임분리
Event / CDC / Kafka / ETL
Marketing / Single View / BI
관측·통제 가능한 Runtime
```

## 1.2 Architecture Method

```text
Vision
  ↓
Big Picture
  ↓
Logical
  ↓
Physical
  ↓
Mechanism
  ↓
Runtime
```

Runtime 검증 결과가 NFR을 만족하지 못하면 Logical/Physical/Mechanism으로 되돌아간다.

## 1.3 5 NFR

| NFR | Architecture Focus |
|---|---|
| Performance | TPS, p95/p99, Thread, Pool, SQL, Timeout |
| Availability | HA, 장애격리, Failover/Failback, DR |
| Scalability | Scale-Out, JVM/Application 확장 |
| Security | SSO/JWT/Session/Key/Authorization |
| Observability | GUID/ServiceId/Log/Metric/Trace/OM |

---

# Part 2. Big Picture

```text
[USER / CHANNEL]
 Browser / WebTopSuite / React
            │
            ▼
[ACCESS]
 GSLB → L4 → Apache / Gateway
            │
            ▼
[SECURITY]
 SSO / JWT / Session / Authorization
            │
            ▼
[APPLICATION]
 Marketing / SingleView / BI / Credit / OM / Business Service
            │
            ▼
[FRAMEWORK]
 TCF / STF / ETF / Dispatcher / Timeout / Transaction / Context
            │
     ┌──────┴────────┐
     ▼               ▼
[INTEGRATION]      [DATA]
 HTTP/JSON/EAI     RDW / ADW
 Kafka/CDC         DB / Cache
 External          ETL / Batch
     │               │
     └──────┬────────┘
            ▼
[OPERATIONS]
 OM / Logging / Control / Audit / Runtime Evidence
            │
            ▼
[DELIVERY / INFRA]
 Git/Build/Deploy + WEB/WAS/AP/DB/DR
```

Architecture는 제품 목록이 아니라 **책임·경계·허용 연결·금지 연결·장애영향**으로 관리한다.

---

# Part 3. System / Domain Boundaries

## 3.1 Top-level System Groups

```text
NSIGHT
├─ MP  Marketing Platform
├─ RD  Real-time Data Warehouse
├─ AD  Analytical Data Warehouse
├─ BL  Business Analysis Layer
├─ DG  Data Governance
└─ IM  Information Management
```

## 3.2 Domain Rule

정상:

```text
MG Service
  ↓ Published Contract / ServiceId
HTTP / Standard Message
  ↓
MK Service
  ↓
MK Owned DAO / Mapper / Table
```

금지:

```text
MG → MK DAO 직접호출
MG → MK Mapper 직접호출
MG → MK 전용 Table 직접갱신
Domain A → Domain B 내부 구현클래스 직접의존
```

Cross-Domain 변경거래는 하나의 Local Spring Transaction으로 묶였다고 가정하지 않는다. Idempotency/Compensation/Reconciliation을 명시한다.

---

# Part 4. ServiceId Traceability

ServiceId는 단순 URL 코드가 아니라 Architecture Traceability의 중심키다.

```text
Requirement
 → Menu
 → Screen
 → Program
 → ServiceId
 → Endpoint
 → Dispatcher
 → Handler
 → Facade
 → Service
 → Rule / DAO
 → Mapper / SQL
 → Table / View
 → Integration
 → Application/WAR
 → Tomcat JVM
 → Server
 → Policy
 → Test
 → Log/Metric/Trace
```

현재 Source에서 `ServiceId → Handler → Facade → Service` 관계는 상당 부분 자동 추출되지만 Requirement/Screen/Table/Server/Runtime Evidence 연결은 아직 Partial이다.

---

# Part 5. Application & TCF Runtime

## 5.1 Online Transaction Lifecycle

```text
HTTP Request
  ↓
System Filter
  ↓
Interceptor
  ↓
Message / Context Resolver
  ↓
Online Transaction Entry
  ↓
TCF
  ↓
STF.preProcess
  ↓
Timeout / Deadline
  ↓
Transaction Boundary
  ↓
ServiceId Dispatcher
  ↓
Handler
  ↓
Facade
  ↓
Service
  ├─ Rule
  ├─ DAO → Mapper → DB
  └─ Integration Client
  ↓
ETF.postProcess
  ↓
Standard Response / Error
```

## 5.2 Three Pre/Post Layers

| Layer | Responsibility |
|---|---|
| System | Filter/Interceptor/Context/JWT/Message Log |
| TCF | STF/ETF/Control/Timeout/Transaction/Dispatcher |
| Business | Validation/Rule/Business AOP/Business Logging |

## 5.3 PDMG Reference vs NSIGHT Target

PDMG AS-IS는 중요한 Reference이지만 Target 자체가 아니다.

대표 차이:

| 영역 | PDMG AS-IS | NSIGHT Target Direction |
|---|---|---|
| Transaction | Timeout worker 안 Outer TransactionTemplate 가능 | Facade TX Owner 방향 |
| Message | `hdr_nhnis + dto/result` | StandardRequest/Response `header + body/result` |
| Rule | Service 내부 Rule 성격 로직 존재 | 독립 Rule 정책 후보 |
| OM | 기능 분산/스냅샷 차이 | Control Plane 통합 방향 |

---

# Part 6. Transaction / Timeout

## 6.1 Owner Model

```text
Request Thread
   ↓
TCF / STF
   ↓
Timeout Worker / Deadline Owner
   ↓
Dispatcher / Handler
   ↓
Facade               ← 기본 DB Transaction Owner 방향
   ↓
Service / DAO / DB
```

현재 Facade 50개는 모두 `@Transactional` 패턴이 확인되었고 Service에는 4건의 Transaction 예외 후보가 있다.

## 6.2 Timeout Hierarchy

```text
DB Query Timeout
   <
Transaction Timeout
   <
Server/Integration Read Timeout
   <
Client Timeout
```

숫자뿐 아니라 다음을 검증해야 한다.

- Worker cancel
- DB statement cancel
- rollback
- connection return
- late commit
- ThreadLocal/MDC cleanup
- child call remaining deadline

`RUN-TIMEOUT` 없이는 안전성을 승인하지 않는다.

---

# Part 7. Standard Message / Context / Error

```text
Standard Request
├─ Header
│   ├─ GUID
│   ├─ ServiceId
│   ├─ System/Screen/Program
│   ├─ User/Branch/Channel/IP
│   └─ Transaction metadata
└─ Business Body
```

책임:

```text
Header → Framework / ServiceContext
Body   → Business DTO
```

업무 Service가 공통 Header/오류 JSON을 임의 조립하지 않는 방향을 기본으로 한다.

Error:

```text
Exception
 ↓
Rollback
 ↓
ETF / finally
 ↓
Global Error Mapping
 ↓
Standard Error Result
 ↓
Error / Image / Audit Evidence
```

---

# Part 8. Security / SSO / JWT / Session

## 8.1 Target Flow

```text
User
 ↓
SSO / IdP
 ↓
Token Issuer
 ↓ KMS/HSM Private Key
JWT Access / Refresh
 ↓
Client
 ↓ Bearer
Gateway / Application
 ↓ JWKS / Public Key
Authentication / Authorization
 ↓
ServiceId / Business
```

## 8.2 Current Critical Drift

현재 Source Snapshot의 JWT Signing Key가 프로세스 시작 시 생성되는 구조라면 다중노드/재기동/Rotation 안정성을 승인할 수 없다.

필수:

- KMS/HSM Key SoT
- versioned `kid`
- JWKS grace period
- rotation run
- old/new token validation

## 8.3 JWT + Session

현재 Hybrid 경로가 존재하므로 최종 운영모드를 ADR로 확정한다.

후보:

- JWT 중심 + 최소 Session
- DeltaManager + Sticky
- Spring Session JDBC 등 외부 Store

센터장애 시 재로그인 허용 여부도 별도 Decision이다.

---

# Part 9. WEB / WAS / Physical Architecture

## 9.1 Execution Units

```text
WEB Server / VM
  ↓
Apache Instance
  ↓ Listen / VirtualHost / Proxy
Tomcat Connector
  ↓
WAS Server / VM
  ↓
Tomcat JVM Instance
  ↓
Application / WAR
```

핵심:

```text
WAS Server ≠ Tomcat JVM ≠ Application/WAR
```

구성도상의 Container는 독립 Tomcat JVM Instance 의미로 기준화한 자료가 있다.

## 9.2 Apache

하나의 Apache Instance에서 여러 Port를 Listen할 수 있다.

```text
Apache
├─ 9000 → JVM :19000
├─ 9001 → JVM :19001
├─ 9010 → JVM :19010
└─ 9011 → JVM :19011
```

실제 운영 승인에는 `httpd.conf`, L4/GSLB, health/routing evidence가 필요하다.

## 9.3 Server Inventory

현재 71대 Physical Working Baseline이 존재한다.

최종 Runtime Inventory:

```text
Application / WAR
  ↓
Tomcat JVM
  ↓
WAS Server
  ↓
CPU / Memory
  ↓
Thread / Hikari
  ↓
DB
  ↓
HA Peer
  ↓
DR Pair
```

이 전수 연결은 HG90 Blocker다.

---

# Part 10. Capacity / Performance

## 10.1 Capacity Chain

```text
Total User
 ↓
Login Session
 ↓
Concurrent Request User
 ↓
TPS
 ↓
AP/VM
 ↓
Tomcat Thread
 ↓
Hikari Pool
 ↓
DB Session
 ↓
CPU/Memory/GC
 ↓
N-1 / Center Failure
```

## 10.2 Versioned Baseline

| Metric | Reference / Legacy | Current Working | Runtime Approved |
|---|---:|---:|---:|
| 16Core Capacity | 500 TPS | 855 TPS 산정 | UNKNOWN |
| 80% Working Capacity | - | 684 TPS/VM | UNKNOWN |
| Session Idle | 60m | 90m Candidate | UNKNOWN |
| Tomcat maxThreads | 800~1,000 | 800 initial / 1,000 upper | UNKNOWN |
| General Hikari | 80~100 | 120~150 validation | UNKNOWN |
| SingleView Hikari | 100~120 | 150~180 validation | UNKNOWN |

숫자충돌을 삭제하지 않고 Versioned Baseline으로 유지한다.

## 10.3 Runtime Approval

필수 Run:

- 600 TPS
- 1,200 TPS
- 1,800 TPS Stress
- N-1
- Hikari pressure
- Slow SQL
- Timeout fault

Runtime 결과가 나오기 전 Working 값을 운영 확정값으로 표현하지 않는다.

---

# Part 11. Data Architecture

```text
Source
 ├─ CDC ───────→ RDW ─────→ Near Real-time / SingleView
 ├─ Event ─────→ Kafka ───→ Real-time Marketing
 └─ Batch/ETL ─→ ADW ─────→ BI / OLAP / Analytics
```

원칙:

- RDW/ADW 역할 및 부하 분리
- 실시간/배치 자원경합 통제
- Data owner 명확화
- Domain 간 직접 DB 결합 금지
- Read/Write Matrix 관리

현재 Domain/Table/View Owner Catalog는 HG90 전에 보강 필요하다.

---

# Part 12. Integration Architecture

```text
Source Domain Service
  ↓
Integration Client
  ↓
HTTP / JSON + Standard Message
  ↓
Target ServiceId
  ↓
Target TCF / Service
```

Contract 필수항목:

- Source/Target Owner
- ServiceId/API
- Message
- Authentication/Authorization
- Connect/Read Timeout
- Remaining Deadline
- Retry/CB/Bulkhead
- Idempotency
- Error Mapping
- Observability

다른 Domain DAO/Mapper/Table 직접호출은 금지한다.

---

# Part 13. Data Migration

```text
AS-IS
 ↓ Extract
STAGE
 ↓ Clean / Transform / Validate
TARGET
 ↓ Reconcile
Pre-Migration
 ↓
Main Cutover
```

Cutover 승인 증적:

- Count
- PK/Duplicate
- Sum/Aggregate
- Null/Code
- Hash
- FK/Integrity
- Business Validation
- Go/No-Go
- Rollback rehearsal

현재 이행 구조는 정의되어 있으나 실 Cutover Evidence는 HG90 blocker다.

---

# Part 14. Logging / Observability / OM

## 14.1 Correlation Key

```text
GUID + ServiceId
```

추가 Dimension:

- User
- Screen / Program
- Application
- Hostname
- Tomcat JVM
- Thread
- SQL/External

## 14.2 Evidence Types

- Apache Access
- Application/System
- Transaction
- Business
- SQL
- External
- Error
- ImageLog PRE/POST/EXCEPTION
- Security/Audit
- GC
- Deployment
- Metric/Trace

## 14.3 Control Plane

```text
OM / Operator
  ↓ Policy / Catalog / Config
Runtime TCF / STF
  ↓
ALLOW / BLOCK / Timeout / Control
  ↓
Runtime Evidence
  └────────────→ OM
```

운영 완성도는 `Alert → Runbook → Action → Evidence`가 닫힐 때 판단한다.

---

# Part 15. HA / DR / Session

장애 Scope:

- Apache
- WAS VM
- Tomcat JVM
- Application
- Thread exhaustion
- Hikari exhaustion
- DB
- External
- Center

후보 topology:

| Candidate | 특징 |
|---|---|
| 2+2 16Core | 센터 장애 수용 산술 후보 |
| 3+3 16Core | 센터장애 후 추가노드 여유 |
| 8Core Scale-Out | 작은 장애단위와 Rolling 유리 |

최종 결정은 Runtime Capacity + HA Test + 운영복잡도로 ADR 승인한다.

Session은 별도 ADR이며 Failover/Center Failure에서 생존/재로그인 정책을 명시한다.

---

# Part 16. CI/CD / Deployment

```text
Git
 ↓
Build
 ↓
Unit / Architecture / Security Test
 ↓
Artifact
 ↓
Repository
 ↓
DEV / Verification
 ↓ Approval
Production Deployment
 ↓
Health / ServiceId Smoke
 ↓
Runtime Evidence
```

Rolling:

```text
Pool Remove
 ↓ Drain
Deploy
 ↓ Health
Smoke
 ↓ Pool Return
Next Node
```

중요: 노드 제외 후 잔여 Capacity가 Peak를 감당할 수 있어야 Rolling을 무중단으로 승인한다.

---

# Part 17. Requirements / UIUX Traceability

```text
Requirement
 ↓
Menu
 ↓
Screen ID
 ↓
Event
 ↓
Program ID
 ↓
ServiceId
 ↓
Code / SQL / Data
 ↓
Runtime Resource
 ↓
Test / Evidence
```

현재 초안은 존재하나 전수 연결은 아직 Partial이다.

---

# Part 18. Architecture Rules / Model / Conformance

## 18.1 Rule Categories

```text
R1 Structural
R2 Framework
R3 Security
R4 Runtime/Operational
MSG Standard Message
DOMAIN Domain Boundary
PERF Capacity/Performance
INFRA WEB/WAS/Server
DATA Data/Integration
GOV Governance
```

총 61개 Rule Registry가 작성되어 있다.

## 18.2 Partial Model

현재 Source extracted model:

```text
ServiceId
  → Handler
  → Facade
  → Service
  → DAO / Integration
  → Mapper
```

정적 분석에서 Handler의 DAO/Mapper 직접 import와 module scope ServiceId duplicate는 0건으로 확인되었다.

## 18.3 Missing Edges

- Requirement→Screen→ServiceId
- Mapper/SQL→Table Owner
- WAR→JVM→Server
- ServiceId→Policy
- ServiceId→Runtime Evidence

Model Schema/Validator와 함께 보강한다.

---

# Part 19. Runtime Evidence / Drift

## 19.1 Mandatory Runtime Registry

```text
RUN-P600
RUN-P1200
RUN-S1800
RUN-N1
RUN-CF
RUN-HIKARI
RUN-SLOWSQL
RUN-TIMEOUT
RUN-SESSION
RUN-ROLLING
RUN-TRACE
RUN-JWT-ROTATE
```

각 Run은 다음 Identity를 가진다.

```text
RunId + Timestamp + Environment + Build/Commit + Config Version
+ ServiceId + GUID + Hostname + Tomcat JVM
```

## 19.2 Current Closed Loop State

```text
Document        Strong
Model           Partial
Code            Static evidence available
Config          Partial
Test            Registry / partial
Runtime         OPEN
Drift           Active
GAP/ADR         Active
Baseline        Candidate only
```

따라서 G80 = HOLD다.

---

# Part 20. GAP / Risk / ADR

## 20.1 P0 GAP Summary

- Runtime Evidence
- JWT Key SoT
- Transaction/Timeout semantics
- Full Traceability
- Server/JVM/WAR mapping
- HA/DR/RTO/RPO
- Session
- Capacity approval
- Data ownership
- Integration Auth/Deadline
- OM Runtime Catalog
- Deployment evidence
- Migration evidence
- Model SoT

## 20.2 Critical Risks

- JWT key 재기동/다중노드 검증 실패
- Timeout 이후 Late Commit
- 센터장애 후 Capacity 부족
- Hikari 과대 → DB session pressure
- Session 전략 불명확
- Runtime Inventory 불완전
- Runtime Evidence 없는 잘못된 Gate PASS

## 20.3 ADR

Critical ADR은 `35-ADR-REGISTER.md`에서 관리하며 HG90 이전에 승인되어야 한다.

---

# Part 21. Architecture Gate

```text
G00  CONDITIONAL PASS
G10  PASS
G20  CONDITIONAL PASS
G30  CONDITIONAL PASS
G40  CONDITIONAL PASS
G50  CONDITIONAL PASS
G60  CONDITIONAL PASS
G70  CONDITIONAL PASS
G80  HOLD
HG90 HOLD
```

HG90 HOLD는 실패 선언이 아니라 **Runtime Evidence 없는 최종 승인 방지**를 위한 Governance 결과다.

---

# Part 22. Baseline Release Criteria

다음 조건이 충족된 경우 Candidate를 Approved Baseline으로 승격한다.

```text
P0 FAIL_TARGET = 0
P0 OPEN_RUNTIME = 0 or approved exception
Critical ADR approved
Model Schema validation PASS
Mandatory Runtime Runs complete
E2E trace complete
Server/JVM/WAR mapping complete
HA/DR/Session evidence complete
Deployment rollback evidence complete
Migration gate evidence complete
```

승격 시:

```text
Status: CANDIDATE
    ↓
HG90 PASS
    ↓
Status: APPROVED BASELINE
    ↓
Baseline Manifest freeze
    ↓
Continuous Drift Monitoring
```

---

# Part 23. Canonical Detailed Documents

| 영역 | 문서 |
|---|---|
| Evidence/Current | 01, 02 |
| Vision/Big Picture | 03, 04 |
| Logical/Physical | 05, 06 |
| Application/TCF | 07, 08, 09, 10, 11 |
| Security | 12 |
| WEB/WAS/Inventory | 13, 14, 15 |
| Capacity | 16, 26, 31 |
| Data/Integration/Migration | 17, 18, 19 |
| Observability/Ops/HA | 20, 21, 22 |
| Delivery/UI/Dependency | 23, 24, 25 |
| Traceability/Rules/Model/Test | 27, 28, 29, 30 |
| Drift/Governance | 32, 33, 34, 35, 36, 37, 38 |

---

# Part 24. Final Statement

현재 NSIGHT Architecture는 **설계 가능한 수준을 넘어 상당한 Source Conformance까지 확보한 Candidate Architecture Baseline**이다.

그러나 NSIGHT가 스스로 정의한 완료기준은 "문서가 완성됨"이 아니다.

```text
설명 가능
+ 추적 가능
+ 구현 가능
+ 테스트 가능
+ 운영 가능
+ 자동검증 가능
+ Drift 탐지 가능
```

따라서 Runtime Evidence와 Critical ADR이 닫힐 때까지 이 문서의 상태는 **CANDIDATE / HG90 HOLD**로 유지한다.


---

# P0 Closure Wave 1 Update

HG90 Candidate 이후 첫 Closure Wave에서 Machine-readable Model Schema와 Validator를 추가하고 정적 P0를 재검증했다.

```text
Closed Static
- Architecture Model Schema
- Model Validator PASS
- Policy TX Timeout path
- DB Query Timeout path
- Worker Context cleanup path

Still Blocking
- JWT KMS/HSM + kid rotation
- Standard business Service TX duplicate 2 cases
- Timeout late commit / connection return runtime proof
- Runtime approved capacity
- Session / HA / DR
- 71 Server → JVM → WAR → Route
- E2E Trace
- Rolling / Rollback
- Migration Cutover
- Human ADR approval
```

따라서 Master 상태는 계속 **CANDIDATE / HG90 HOLD**다.
