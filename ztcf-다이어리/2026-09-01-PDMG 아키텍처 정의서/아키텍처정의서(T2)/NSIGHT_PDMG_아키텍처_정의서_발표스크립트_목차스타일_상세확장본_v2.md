# NSIGHT PDMG 아키텍처 정의서
# 발표스크립트 목차 스타일 — 상세 확장본 v2
## Visual-First / Top-down → Drill-down / Runtime / PASS-GAP-ADR

> 기준 목차: **상호금융 정보계 아키텍처 발표스크립트 13장 Story**  
> 직접 기준문서: `NSIGHT_PDMG_아키텍처_정의서_발표스크립트_목차스타일_v1`  
> 상세 원천: **PDMG Architecture Definition 00~18**  
> 상태: `[WORKING DETAILED BASELINE-2026-09-01]`  

---

# 0. 작업 지시 반영 기준

```text
13장 Story 목차 유지
        ↓
각 장 대표 Architecture
        ↓
Top-down Drill-down 그림
        ↓
세부 Component / Boundary
        ↓
Runtime / Flow
        ↓
정상패턴 / 금지패턴
        ↓
의사결정: 주안 + 대안 + 장단점
        ↓
PASS / GAP / ADR
        ↓
기존 00~18 Source Trace
```

이번 v2는 13장 요약본을 다시 요약하는 문서가 아니라, **13장 Story를 유지한 상태에서 기존 00~18 상세 Architecture의 TEXT 그림과 판정정보를 다시 끌어올린 상세 정의서**다.

# 0.1 전체 목차
1. **왜 다시 짓는가** — PDMG Architecture Definition의 목적과 재정의 이유
2. **정보계 패러다임의 전환** — PDMG를 Application이 아니라 Runtime Platform 관점까지 확장
3. **아키텍처 6 단계 수립 방법론** — VISION → BIG PICTURE → LOGICAL → PHYSICAL → MECHANISM → RUNTIME
4. **Big Picture** — PDMG 전체 System Context와 책임 경계
5. **논리 아키텍처** — Application Responsibility를 Logical Technical Node로 변환
6. **물리 아키텍처** — Logical Node를 Center·WEB·WAS·JVM·DB로 Mapping
7. **DR 센터 활용 전략** — AP 가용성과 DB 정합성을 분리하는 HA/DR 전략
8. **메커니즘** — Architecture를 실제로 움직이게 하는 공통 실행 규칙
9. **런타임 서비스** — 거래 한 건의 End-to-End 실행과 FAST/DEEP 경계
10. **데이터플랫폼** — RDW·ADW 역할분리와 PDMG Data Access
11. **마케팅플랫폼** — PDMG를 Marketing Platform 실행 Reference로 정의
12. **BI 포탈** — PDMG 경계 밖의 분석 소비계층과 Data Contract
13. **표준화와 10년 지속 가능성** — Naming·DevOps·Observability·Traceability로 Architecture를 유지

---

# 제 1장 왜 다시 짓는가
## PDMG Architecture Definition의 목적과 재정의 이유

## 1. 장의 목적과 핵심 정의

### FIG-01-01. 대표 Architecture

```text
기존 시스템 설명서
   ↓
Module / Server / Source 단편 정보
   ↓
"실제로 어떻게 동작하는가?" 설명 한계
   ↓
PDMG Architecture 재정의
   ↓
Business / Application / Technical / Runtime / Evidence 통합
   ↓
운영 가능한 Architecture Baseline
```

- PDMG는 단순 모듈 목록이 아니라 실행 가능한 Architecture System으로 정의한다.
- 현재 구현(PDMG AS-IS)과 목표(NSIGHT TO-BE)를 분리한다.
- Source·Config·Runtime Evidence가 없는 내용은 Current Fact로 승격하지 않는다.

### 핵심 Architecture 질문

- PDMG를 단순 구현체가 아니라 Architecture Baseline으로 정의했는가?
- Current와 Target을 분리했는가?
- Runtime Evidence로 PASS를 판단할 수 있는가?
- --

## 2. Top-down → Drill-down Architecture

### FIG-01-02. Drill-down Route

```text
L0  왜 다시 짓는가 전체관점
 ↓
L1  Responsibility / Boundary
 ↓
L2  Module / Logical Node / Platform
 ↓
L3  Component / Contract
 ↓
L4  Runtime / Failure / Security
 ↓
L5  Source / Config / Evidence
```

### FIG-01-03. Drill-down — FIG-18-04. L0 — PDMG Master Architecture Baseline

> 원천: `18_PDMG_INTEGRATED_ARCHITECTURE_BASELINE_상세정의서_v1.md`

```text
User / Browser
  ↓
UI Delivery
  ↓
Authentication / JWT
  ↓
Application Runtime
  ├─ Framework / TCF
  ├─ Worker / Transaction
  └─ Business
       ↓
   DAO / Mapper
       ↓
   RDW / DB

External Integration [contract-based]
Operations / Observability [cross-cutting]
Physical / HA / DR [mapping]
Traceability / Evidence [closed loop]
```

### FIG-01-04. Drill-down — FIG-01-24. Runtime Evidence Architecture

> 원천: `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`

```text
Runtime
  ↓
┌────────────────────────────────┐
│ Metric                         │
│ Log                            │
│ Trace                          │
│ ImageLog                       │
└───────────────┬────────────────┘
                ↓
GUID / ServiceId
Host / JVM
SqlId / ErrorCode
DeploymentId
                ↓
Dashboard / Alert
                ↓
Runbook
                ↓
Runtime Evidence
                ↓
Drift / GAP / ADR
```

### FIG-01-05. Drill-down — FIG-17-02. Evidence Flow

> 원천: `17_PDMG_TRACEABILITY_PASS_GAP_ADR_상세정의서_v1.md`

```text
Source / Config
   ↓
Runtime / Deployment
   ↓
PDMG Current Analysis
   ↓
Architecture Decision
   ↓
NSIGHT Target / Working Baseline
   ↓
PASS / GAP / OPEN
```

### FIG-01-06. Drill-down — TEXT ARCHITECTURE — PDMG를 한 문장으로 읽는 방식

> 원천: `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`

```text
PDMG
  ↓
복수 Build Module로 구성되지만
Module = Server가 아니며
  ↓
pdmg-service Runtime 안에서
pdmg-fw Framework Mechanism과
Business Component가 협력하고
  ↓
HTTP 요청은
Filter → Security → MVC → TCF를 지나
  ↓
Worker Thread의 Transaction 안에서
Handler → Facade → Service → DAO → Mapper → DB
경로를 수행하고
  ↓
hdr_nhnis / GUID / ServiceContext / Error / Logging으로
거래를 추적하며
  ↓
pdmg-jwt의 인증/Token 구조와 결합되고
  ↓
Source / Artifact / Deployment / Runtime Evidence까지
연결되어야
완전한 PDMG Architecture가 된다.
```

### FIG-01-07. Drill-down — FIG-18-17. Baseline Release Gate

> 원천: `18_PDMG_INTEGRATED_ARCHITECTURE_BASELINE_상세정의서_v1.md`

```text
Architecture Definition
 ↓
Critical ADR Closed
 ↓
Source / Config Conformance
 ↓
Security Integration PASS
 ↓
Performance / Failure PASS
 ↓
Deployment Trace PASS
 ↓
Runtime Evidence PASS
 ↓
DR / Restore PASS
 ↓
G80 Approval
 ↓
HG90 PDMG Baseline
```

### FIG-01-08. Drill-down — TEXT ARCHITECTURE — Conclusion

> 원천: `17_PDMG_TRACEABILITY_PASS_GAP_ADR_상세정의서_v1.md`

```text
PDMG TRACEABILITY / CONFORMANCE / PASS / GAP / ADR ARCHITECTURE
=
Current Fact
+
Architecture Rule
+
Runtime / Failure / Security
+
Evidence / PASS / GAP
```

## 3. Runtime / Flow 관점

### FIG-01-09. Runtime Interpretation

```text
Architecture Definition → Source/Config → Runtime/Deployment → Evidence → PASS/GAP → ADR
```

## 4. 정상패턴 / 금지패턴

### FIG-01-10. 정상패턴

```text
정상
Architecture Definition
  ↓
Source / Config
  ↓
Runtime Evidence
  ↓
PASS / GAP
  ↓
ADR / Baseline
```

### FIG-01-11. 금지패턴

```text
금지
문서에 있음
   ↓
곧 Current Fact로 간주
   X

PDMG AS-IS
   ↓
자동 NSIGHT TO-BE 승격
   X
```

## 5. Architecture 의사결정 — 주안 / 대안

### 의사결정 주제: **Architecture Baseline 운영방식**

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | Evidence-backed Baseline | 문서 중심 Baseline |
| 장점 | • Source/Runtime 정합성 높음<br>• PASS/GAP 객관화<br>• 변경영향 추적 가능 | • 초기 문서 작성은 단순<br>• 도입속도 빠름 |
| 단점 | • Evidence 수집 자동화 필요<br>• 초기 모델링 비용 증가 | • 실제 구현 Drift를 놓치기 쉬움<br>• 운영증적과 분리 |
| 권고 | **주안 채택** — Source/Target/NFR Evidence와 정합 | 대안은 별도 ADR와 Runtime Evidence가 있을 때만 채택 |

## 6. PASS / GAP / ADR

### 현재 판정: `CONDITIONAL PASS`

### 주요 GAP

- `[GAP/OPEN]` pdmg-om Current Scope
- `[GAP/OPEN]` Runtime Evidence 자동화
- `[GAP/OPEN]` HG90 Release 조건 미완료

### 연계 ADR / Decision

- `ADR-TASK-001 Architecture SSOT`
- `ADR-TASK-040 Runtime Evidence Gate`

### FIG-01-12. PASS 전환구조

```text
Architecture Definition
   ↓
Source / Config Conformance
   ↓
Security / Runtime / Failure Test
   ↓
Deployment / Runtime Evidence
   ↓
Critical GAP / ADR Closure
   ↓
PASS / HG90
```

## 7. 기존 00~18 정의서 Trace

**v1 상세원천:** 기존 01 Executive / 17 Traceability / 18 Integrated Baseline

| 기존 장 | 원천 파일 | 본 장에서 사용하는 관점 |
|---|---|---|
| 01 | 01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 17 | 17_PDMG_TRACEABILITY_PASS_GAP_ADR_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 18 | 18_PDMG_INTEGRATED_ARCHITECTURE_BASELINE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |

## 8. 장 결론

```text
왜 다시 짓는가
=
대표 Architecture
+ Drill-down
+ Runtime
+ Normal / Forbidden
+ Decision
+ PASS / GAP / ADR
```

---

# 제 2장 정보계 패러다임의 전환
## PDMG를 Application이 아니라 Runtime Platform 관점까지 확장

## 1. 장의 목적과 핵심 정의

### FIG-02-01. 대표 Architecture

```text
과거 관점
Application
= 화면 + 업무 Java + DB

        ↓ 전환

PDMG 관점
Application Responsibility
+
Framework Runtime
+
Transaction / Timeout / Thread
+
Security / JWT
+
Data Access
+
Deployment / Observability
+
Evidence
```

- Module Boundary ≠ Process Boundary ≠ Spring Context Boundary ≠ Physical Server.
- PDMG Current는 온라인/거래 Runtime Reference로 보고, Event/CDC/ETL/BI 전체를 Current로 과장하지 않는다.
- NSIGHT는 Scalable / Resilient / Data-Centric 목표와 Alignment 기준을 제공한다.

### 핵심 Architecture 질문

- Module/Process/Context/Server를 혼동하지 않는가?
- PDMG 범위를 NSIGHT 전체와 혼동하지 않는가?
- 실행/운영/보안까지 Architecture에 포함했는가?
- --

## 2. Top-down → Drill-down Architecture

### FIG-02-02. Drill-down Route

```text
L0  정보계 패러다임의 전환 전체관점
 ↓
L1  Responsibility / Boundary
 ↓
L2  Module / Logical Node / Platform
 ↓
L3  Component / Contract
 ↓
L4  Runtime / Failure / Security
 ↓
L5  Source / Config / Evidence
```

### FIG-02-03. Drill-down — FIG-04-18. Integration Logical Boundary

> 원천: `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`

```text
Application Runtime
        ↓
Approved Integration Contract
        ↓
┌─────────────────────────────┐
│ Integration Capability      │
│                             │
│ API / Service Adapter       │
│ Event Adapter               │
│ File Adapter                │
│ Data Movement Adapter       │
└──────────────┬──────────────┘
               ↓
Target System / Platform
```

### FIG-02-04. Drill-down — FIG-04-07. Module ≠ Logical Node

> 원천: `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`

```text
Build Modules
│
├─ pdmg-service ───────┐
│                      │
├─ pdmg-fw ────────────┼────► Application Runtime Logical Node
│                      │
├─ pdmg-ui ────────────┼────► UI Delivery Logical Node
│                      │
├─ pdmg-jwt ───────────┼────► Authentication Logical Node
│                      │
└─ pdmg-om ────────────┴────► Operations Node [UNKNOWN]
```

### FIG-02-05. Drill-down — FIG-01-10. TCF ON + Timeout ON Current Runtime

> 원천: `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`

```text
HTTP Request
   │
   ▼
DefaultFilter
   ├─ Request Body Cache
   ├─ Header / GUID
   ├─ ServiceContext
   └─ MDC
   │
   ▼
Spring SecurityFilterChain
   │
   ▼
DispatcherServlet
   │
   ▼
ServicePreventionInterceptor.preHandle
   │
   ▼
OnlineTransactionController
   │
   ▼
TcfFacade
   │
   ▼
OnlineTimeoutExecutor
   │ submit
   ▼
════════════ Worker Thread ════════════
TransactionTemplate BEGIN
   │
   ▼
TransactionDispatcher
   │
   ▼
TransactionHandler
   │
   ▼
Business Facade
   │
   ▼
BizPrePostAspect
   │
   ▼
Service
   │
   ▼
DAO
   │
   ▼
Mapper XML / JDBC
   │
   ▼
DB
   │
   ▼
Deadline Check
   ├─ OK       → COMMIT
   └─ EXCEEDED → ROLLBACK
═══════════════════════════════════════
   │
   ▼
Response / Exception
   │
   ▼
ResponseBodyAdvice
   │
   ▼
Interceptor.afterCompletion
   │
   ▼
DefaultFilter.finally / Context Clear
```

### FIG-02-06. Drill-down — FIG-01-25. Current PDMG ↔ NSIGHT Target

> 원천: `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`

```text
NSIGHT Target Context
│
├─ Scalable / Resilient / Data-Centric
├─ RDW / ADW Workload Separation
├─ CDC Near Real-time
├─ Event-driven Integration
├─ Standard API / ETL / File
├─ WEB/WAS Scale-out
├─ HA / DR
└─ Observability / Evidence
        │
        │ compare
        ▼
PDMG Current
│
├─ UI / JWT
├─ Framework / TCF
├─ Worker / Transaction
├─ Business Layer
├─ DB Access
└─ Logging / Trace Clues
```

### FIG-02-07. Drill-down — TEXT ARCHITECTURE — Boundary Rule Set

> 원천: `02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md`

```text
R-BND-01
Module ≠ Process

R-BND-02
pdmg-fw ≠ Remote Business Server

R-BND-03
UI → DB Direct = Forbidden

R-BND-04
Cross-system Direct DB DML = Forbidden

R-BND-05
External Call → Approved Contract

R-BND-06
Authentication ≠ Authorization

R-BND-07
Client Header ≠ Trusted Principal

R-BND-08
PDMG Current ≠ NSIGHT Entire Target

R-BND-09
Unknown Physical Detail → OPEN

R-BND-10
Every Boundary Crossing → Traceable
```

### FIG-02-08. Drill-down — FIG-04-44. Logical Technical Rules

> 원천: `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`

```text
R-LT-01
Application ≠ Logical Technical Node

R-LT-02
Module ≠ Logical Technical Node

R-LT-03
Logical Node ≠ Physical Host

R-LT-04
Technology Component ≠ Product Version

R-LT-05
pdmg-fw ≠ mandatory independent Runtime Node

R-LT-06
Client → Data Direct = Forbidden

R-LT-07
External → PDMG DB Direct DML = Forbidden

R-LT-08
Application Runtime → Data through Data Access Capability

R-LT-09
Every Node defines State / Scale / Failure / Security

R-LT-10
Observability crosses every Runtime Node

R-LT-11
Unknown Host/Product/Port remains OPEN

R-LT-12
PDMG Current ≠ NSIGHT broader platform by default
```

## 3. Runtime / Flow 관점

### FIG-02-09. Runtime Interpretation

```text
User → Application → Framework/Runtime → Data → Operations/Evidence
```

## 4. 정상패턴 / 금지패턴

### FIG-02-10. 정상패턴

```text
정상
Business Responsibility
 ↓
Application
 ↓
Technical Capability
 ↓
Runtime / Physical
 ↓
Operations / Evidence
```

### FIG-02-11. 금지패턴

```text
금지
Module = Server
Process = Application
Spring Context = Logical Node
   X
```

## 5. Architecture 의사결정 — 주안 / 대안

### 의사결정 주제: **PDMG Architecture 범위**

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | Runtime/Operations까지 포함 | Application Source 중심 |
| 장점 | • 실제 운영구조 설명 가능<br>• 보안/성능/장애까지 연결 | • 개발자 이해가 빠름<br>• 문서량 감소 |
| 단점 | • 문서 범위가 넓어짐<br>• 책임분류 필요 | • Physical/Runtime 문제 설명 한계<br>• 운영/보안 단절 |
| 권고 | **주안 채택** — Source/Target/NFR Evidence와 정합 | 대안은 별도 ADR와 Runtime Evidence가 있을 때만 채택 |

## 6. PASS / GAP / ADR

### 현재 판정: `PASS / Current PARTIAL`

### 주요 GAP

- `[GAP/OPEN]` Module→Runtime/Physical 전수 Mapping
- `[GAP/OPEN]` Event/CDC/ETL Current 범위
- `[GAP/OPEN]` 운영/보안 Evidence

### 연계 ADR / Decision

- `ADR-TASK-003 PDMG↔NSIGHT Mapping`
- `ADR-TASK-036 OM Control Plane`

### FIG-02-12. PASS 전환구조

```text
Architecture Definition
   ↓
Source / Config Conformance
   ↓
Security / Runtime / Failure Test
   ↓
Deployment / Runtime Evidence
   ↓
Critical GAP / ADR Closure
   ↓
PASS / HG90
```

## 7. 기존 00~18 정의서 Trace

**v1 상세원천:** 기존 02 System Boundary / 03 Application / 04 Logical

| 기존 장 | 원천 파일 | 본 장에서 사용하는 관점 |
|---|---|---|
| 01 | 01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 02 | 02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 03 | 03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 04 | 04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |

## 8. 장 결론

```text
정보계 패러다임의 전환
=
대표 Architecture
+ Drill-down
+ Runtime
+ Normal / Forbidden
+ Decision
+ PASS / GAP / ADR
```

---

# 제 3장 아키텍처 6 단계 수립 방법론
## VISION → BIG PICTURE → LOGICAL → PHYSICAL → MECHANISM → RUNTIME

## 1. 장의 목적과 핵심 정의

### FIG-03-01. 대표 Architecture

```text
VISION
"무엇을 지향하는가?"
 ↓
BIG PICTURE
"누가 무엇을 책임지는가?"
 ↓
LOGICAL
"어떤 기술 역할/경계가 필요한가?"
 ↓
PHYSICAL
"어디에 어떻게 배치하는가?"
 ↓
MECHANISM
"어떤 실행규칙으로 통제하는가?"
 ↓
RUNTIME
"거래가 실제로 어떻게 동작하는가?"
 ↓
EVIDENCE / PASS
```

- 6단계는 문서 분류가 아니라 Architecture Drill-down 순서다.
- 하위 Runtime Evidence가 상위 Architecture 정의와 일치해야 한다.
- 마지막 단계는 그림 완성이 아니라 Source/Runtime 검증이다.

### 핵심 Architecture 질문

- Top-down 6단계가 실제 하위 Evidence와 연결되는가?
- 각 단계의 산출물이 다음 단계 Input이 되는가?
- Runtime 검증이 최종 Gate로 포함되는가?
- --

## 2. Top-down → Drill-down Architecture

### FIG-03-02. Drill-down Route

```text
L0  아키텍처 6 단계 수립 방법론 전체관점
 ↓
L1  Responsibility / Boundary
 ↓
L2  Module / Logical Node / Platform
 ↓
L3  Component / Contract
 ↓
L4  Runtime / Failure / Security
 ↓
L5  Source / Config / Evidence
```

### FIG-03-03. Drill-down — FIG-04-03. Logical Technical Drill-down

> 원천: `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`

```text
L0  PDMG Logical Technical Landscape
 ↓
L1  Zone / Trust / Workload
 ↓
L1  Technical Capability
 ↓
L2  Logical Technical Nodes
 ↓
L2  Application-to-Node Mapping
 ↓
L3  Runtime Characteristics
 ↓
L3  Allowed / Forbidden Connections
 ↓
L4  Request / Failure / Security Crossing
 ↓
L5  Current Implementation Projection / Evidence
 ↓
05  Physical Handoff
```

### FIG-03-04. Drill-down — FIG-05-04. L0 — PDMG Physical Master Architecture

> 원천: `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`

```text
User / Browser
   ↓
GSLB [Working Baseline]
   ↓
L4 [Working Baseline]
   ↓
WEB / Apache
   ↓
WAS / Tomcat JVM
   ↓
PDMG WAR / Runtime
   ├─ pdmg-service
   └─ pdmg-fw
   ↓
Hikari / MyBatis / JDBC
   ↓
RDW / DB

Authentication Runtime
pdmg-jwt
   ↓
Token / JWKS
   ↓
PDMG Business Runtime

Operations / Monitoring / Backup
= Physical Mapping OPEN / Target Reference
```

### FIG-03-05. Drill-down — FIG-01-22. Logical → Physical Working Path

> 원천: `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`

```text
User / Channel
      ↓
GSLB
      ↓
L4
      ↓
Apache WEB
      ↓
Tomcat JVM
      ↓
Business WAR
      ↓
Spring Runtime
      ├─ pdmg-fw
      └─ pdmg-service
      ↓
Hikari / MyBatis / JDBC
      ↓
RDW / ADW / DB
```

### FIG-03-06. Drill-down — FIG-00-04. L0 → L5

> 원천: `00_PDMG_ARCHITECTURE_MASTER_INDEX.md`

```text
L0
PDMG Architecture Landscape
"전체가 무엇인가?"
          ↓
L1
System Context / Boundary
"누가 호출하고 무엇을 호출하는가?"
          ↓
L2
Module / Logical Node / Container
"큰 책임단위는 무엇인가?"
          ↓
L3
Component / Layer
"Handler/Facade/Service/DAO/Framework는?"
          ↓
L4
Runtime / Sequence / Data Flow
"거래 한 건은 어떻게 실행되는가?"
          ↓
L5
Source / Config / Failure / Evidence
"어떤 코드와 설정으로 증명하는가?"
```

### FIG-03-07. Drill-down — FIG-05-05. Logical Node → Physical Resource Mapping

> 원천: `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`

```text
LTN-PD-01 UI Delivery
   ↓
WEB/UI Runtime Resource

LTN-PD-02 Authentication
   ↓
JWT Runtime Resource

LTN-PD-03 Application Runtime
   ↓
WAS VM / JVM / WAR

LTN-PD-04 Data Service
   ↓
DB Service / DB Cluster

LTN-PD-05 Integration
   ↓
Integration Platform [CONDITIONAL]

LTN-PD-06 Operations
   ↓
Monitoring / Control [OPEN]
```

### FIG-03-08. Drill-down — FIG-18-05. L0~L5 Full Drill-down

> 원천: `18_PDMG_INTEGRATED_ARCHITECTURE_BASELINE_상세정의서_v1.md`

```text
L0 PDMG Landscape
 ↓
L1 System / Trust / Data Boundary
 ↓
L2 Module / Logical Node
 ↓
L3 Layer / Component
 ↓
L4 Runtime / Failure / Security
 ↓
L5 Source / Config / Evidence
```

## 3. Runtime / Flow 관점

### FIG-03-09. Runtime Interpretation

```text
VISION → BIG PICTURE → LOGICAL → PHYSICAL → MECHANISM → RUNTIME → EVIDENCE
```

## 4. 정상패턴 / 금지패턴

### FIG-03-10. 정상패턴

```text
정상
VISION → BIG PICTURE → LOGICAL
→ PHYSICAL → MECHANISM → RUNTIME
→ EVIDENCE
```

### FIG-03-11. 금지패턴

```text
금지
제품부터 선정
 ↓
서버 그림
 ↓
사후에 Architecture 의미 부여
 X
```

## 5. Architecture 의사결정 — 주안 / 대안

### 의사결정 주제: **Architecture 수립순서**

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | 6단계 Top-down | 제품/Physical 선결정 |
| 장점 | • 책임→기술→배치 논리 유지<br>• Target/Current 분리 용이 | • 구체적인 그림을 빨리 만듦 |
| 단점 | • 초기 사고/정의시간 필요 | • 제품 종속/사후합리화 위험 |
| 권고 | **주안 채택** — Source/Target/NFR Evidence와 정합 | 대안은 별도 ADR와 Runtime Evidence가 있을 때만 채택 |

## 6. PASS / GAP / ADR

### 현재 판정: `PASS`

### 주요 GAP

- `[GAP/OPEN]` 6단계 산출물 간 자동 Trace
- `[GAP/OPEN]` Runtime Gate 자동화
- `[GAP/OPEN]` Architecture Model SSOT

### 연계 ADR / Decision

- `G00~HG90 Gate`
- `Architecture Model/Rule SSOT`

### FIG-03-12. PASS 전환구조

```text
Architecture Definition
   ↓
Source / Config Conformance
   ↓
Security / Runtime / Failure Test
   ↓
Deployment / Runtime Evidence
   ↓
Critical GAP / ADR Closure
   ↓
PASS / HG90
```

## 7. 기존 00~18 정의서 Trace

**v1 상세원천:** 기존 00 Guide / 01~18 전체 작성체계

| 기존 장 | 원천 파일 | 본 장에서 사용하는 관점 |
|---|---|---|
| 00 | 00_PDMG_ARCHITECTURE_MASTER_INDEX.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 01 | 01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 02 | 02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 03 | 03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 04 | 04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 05 | 05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 08 | 08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 09 | 09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 17 | 17_PDMG_TRACEABILITY_PASS_GAP_ADR_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 18 | 18_PDMG_INTEGRATED_ARCHITECTURE_BASELINE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |

## 8. 장 결론

```text
아키텍처 6 단계 수립 방법론
=
대표 Architecture
+ Drill-down
+ Runtime
+ Normal / Forbidden
+ Decision
+ PASS / GAP / ADR
```

---

# 제 4장 Big Picture
## PDMG 전체 System Context와 책임 경계

## 1. 장의 목적과 핵심 정의

### FIG-04-01. 대표 Architecture

```text
User / Browser
                          │
          ┌───────────────┼───────────────┐
          ▼               ▼               ▼
       pdmg-ui         pdmg-jwt       pdmg-service
       UI              Auth           Business
                                           │
                                 ┌─────────┴─────────┐
                                 │ pdmg-fw           │
                                 │ Runtime Control   │
                                 └─────────┬─────────┘
                                           ↓
                                  Handler → Facade
                                           ↓
                                         Service
                                           ↓
                                      DAO / Mapper
                                           ↓
                                         RDW / DB

pdmg-om = [UNKNOWN]

External Integration
= Approved Contract / Inventory dependent
```

- 책임은 Application/Technical Boundary 안에 고정하고 연결은 경계에서 통제한다.
- pdmg-fw는 별도 Remote Business Server가 아니라 Framework Module/Capability다.
- PDMG Current ≠ NSIGHT 전체 Target Architecture.

### 핵심 Architecture 질문

- 누가 PDMG를 호출하고 어떤 경계가 있는가?
- 정상/금지 연결이 명확한가?
- Security/Observability가 Cross-cutting으로 정의되는가?
- --

## 2. Top-down → Drill-down Architecture

### FIG-04-02. Drill-down Route

```text
L0  Big Picture 전체관점
 ↓
L1  Responsibility / Boundary
 ↓
L2  Module / Logical Node / Platform
 ↓
L3  Component / Contract
 ↓
L4  Runtime / Failure / Security
 ↓
L5  Source / Config / Evidence
```

### FIG-04-03. Drill-down — FIG-02-05. PDMG System Context

> 원천: `02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md`

```text
┌─────────────────────┐
                         │ User / Browser      │
                         └───────┬─────────────┘
                                 │
              ┌──────────────────┼──────────────────┐
              │                  │                  │
              ▼                  ▼                  ▼
       ┌──────────────┐   ┌──────────────┐   ┌─────────────────┐
       │ pdmg-ui      │   │ pdmg-jwt     │   │ pdmg-service    │
       │ UI Boundary  │   │ Auth Boundary│   │ Biz Boundary    │
       └──────┬───────┘   └──────┬───────┘   └────────┬────────┘
              │                  │ Bearer              │
              └──────────────────┼─────────────────────┘
                                 ▼
                        ┌─────────────────┐
                        │ pdmg-fw        │
                        │ Runtime Control │
                        │ same runtime    │
                        └────────┬────────┘
                                 │
                                 ▼
                        ┌─────────────────┐
                        │ Business Core   │
                        │ Handler→DAO     │
                        └────────┬────────┘
                                 │
                                 ▼
                        ┌─────────────────┐
                        │ RDW / DB        │
                        └─────────────────┘

External API / Event / CDC / ETL / File
        = Context / Target / Inventory dependent
        = PDMG Current direct ownership not assumed
```

### FIG-04-04. Drill-down — TEXT ARCHITECTURE — Final System Boundary

> 원천: `02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md`

```text
┌──────── USER / CHANNEL ────────┐
                   │ Browser / Information User     │
                   └──────────────┬─────────────────┘
                                  │
                                  ▼
                   ┌──────── ACCESS BOUNDARY ───────┐
                   │ GSLB / L4 / WEB [Reference]   │
                   └──────────────┬─────────────────┘
                                  │
             ┌────────────────────┼────────────────────┐
             ▼                    ▼                    ▼
     ┌──────────────┐     ┌──────────────┐     ┌──────────────────┐
     │ pdmg-ui      │     │ pdmg-jwt     │     │ pdmg-service     │
     │ UI Boundary  │     │ Auth Boundary│     │ Business Runtime │
     └──────┬───────┘     └──────┬───────┘     └────────┬─────────┘
            │                    │ Token                 │
            └────────────────────┼───────────────────────┘
                                 ▼
                     ┌──────────────────────────┐
                     │ Spring Runtime Boundary  │
                     │ pdmg-service + pdmg-fw   │
                     └────────────┬─────────────┘
                                  │
                                  ▼
                     ┌──────────────────────────┐
                     │ Business Component       │
                     │ Handler / Facade         │
                     │ Service / DAO / Mapper   │
                     └────────────┬─────────────┘
                                  │ JDBC
                                  ▼
                     ┌──────────────────────────┐
                     │ DATA BOUNDARY            │
                     │ RDW / DB                 │
                     └──────────────────────────┘

      External API / Event / CDC / ETL / File
                    │
                    └─ Approved Contract / Reference / OPEN

      Security
      = Cross-boundary Trust Control

      Observability
      = Cross-boundary GUID / ServiceId / Runtime Evidence

      OM
      = Operations Boundary [CURRENT UNKNOWN]
```

### FIG-04-05. Drill-down — FIG-18-04. L0 — PDMG Master Architecture Baseline

> 원천: `18_PDMG_INTEGRATED_ARCHITECTURE_BASELINE_상세정의서_v1.md`

```text
User / Browser
  ↓
UI Delivery
  ↓
Authentication / JWT
  ↓
Application Runtime
  ├─ Framework / TCF
  ├─ Worker / Transaction
  └─ Business
       ↓
   DAO / Mapper
       ↓
   RDW / DB

External Integration [contract-based]
Operations / Observability [cross-cutting]
Physical / HA / DR [mapping]
Traceability / Evidence [closed loop]
```

### FIG-04-06. Drill-down — FIG-02-11. pdmg-ui Boundary

> 원천: `02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md`

```text
Browser
  ↓
pdmg-ui
  ├─ Static / UI Resource
  ├─ Menu / Screen
  ├─ Transaction Catalog
  ├─ ServiceId Request
  └─ Bearer Token 전달
  ↓
HTTP
  ↓
pdmg-service
```

### FIG-04-07. Drill-down — FIG-02-12. pdmg-jwt Boundary

> 원천: `02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md`

```text
User / SSO Caller
       ↓
┌────────────────────────┐
│ pdmg-jwt               │
│                        │
│ Login                  │
│ SSO Validation         │
│ Access Token           │
│ Refresh Token          │
│ JWKS                   │
└───────────┬────────────┘
            │ Bearer Token
            ▼
┌────────────────────────┐
│ pdmg-service / pdmg-fw │
│ Token Verification     │
└────────────────────────┘
```

### FIG-04-08. Drill-down — FIG-02-28. Boundary GAP Map

> 원천: `02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md`

```text
SYSTEM CONTEXT
│
├─ Inbound
│   └─ Actual Domain / Port
│      [OPEN → 05]
│
├─ Outbound
│   └─ External API Inventory
│      [OPEN → 06]
│
├─ Data
│   └─ RDW vs ADW actual datasource usage
│      [OPEN → 07]
│
├─ Security
│   ├─ RS256/HMAC
│   │  [CRITICAL GAP]
│   └─ Identity Binding
│      [GAP]
│
├─ Process
│   └─ Actual Host/JVM/WAR mapping
│      [GAP → 05]
│
├─ Integration
│   └─ Event/CDC/ETL/File PDMG ownership
│      [UNKNOWN/REFERENCE]
│
├─ Operations
│   └─ pdmg-om
│      [UNKNOWN]
│
└─ Observability
    └─ Deployment/Host correlation
       [GAP]
```

## 3. Runtime / Flow 관점

### FIG-04-09. Runtime Interpretation

```text
User/Browser → UI/Auth → Application Runtime → Data/External Contract → Operations
```

## 4. 정상패턴 / 금지패턴

### FIG-04-10. 정상패턴

```text
정상
User → UI/Auth → Application Runtime
→ Data / Approved Interface

Security / Observability
= Cross-cutting
```

### FIG-04-11. 금지패턴

```text
금지
Browser → DB
UI → DAO
External → PDMG DB DML
Target Component = Current Component
 X
```

## 5. Architecture 의사결정 — 주안 / 대안

### 의사결정 주제: **System Boundary**

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | 책임 고정 + Contract 연결 | P2P/Direct 연결 허용 확대 |
| 장점 | • 변경격리<br>• 보안/운영 통제<br>• Traceability | • 초기 개발 간단 |
| 단점 | • Interface governance 필요 | • 강결합<br>• 장애전파<br>• 영향분석 어려움 |
| 권고 | **주안 채택** — Source/Target/NFR Evidence와 정합 | 대안은 별도 ADR와 Runtime Evidence가 있을 때만 채택 |

## 6. PASS / GAP / ADR

### 현재 판정: `PASS / Current CONDITIONAL`

### 주요 GAP

- `[GAP/OPEN]` JWT/Identity Critical GAP
- `[GAP/OPEN]` External Interface Inventory
- `[GAP/OPEN]` Physical Deployment Mapping

### 연계 ADR / Decision

- `ADR-TASK-014 Interface Type`
- `ADR-TASK-015 Direct DB`
- `ADR-TASK-035 Observability`

### FIG-04-12. PASS 전환구조

```text
Architecture Definition
   ↓
Source / Config Conformance
   ↓
Security / Runtime / Failure Test
   ↓
Deployment / Runtime Evidence
   ↓
Critical GAP / ADR Closure
   ↓
PASS / HG90
```

## 7. 기존 00~18 정의서 Trace

**v1 상세원천:** 기존 01 Executive / 02 System Context / 18 Integrated

| 기존 장 | 원천 파일 | 본 장에서 사용하는 관점 |
|---|---|---|
| 01 | 01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 02 | 02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 18 | 18_PDMG_INTEGRATED_ARCHITECTURE_BASELINE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |

## 8. 장 결론

```text
Big Picture
=
대표 Architecture
+ Drill-down
+ Runtime
+ Normal / Forbidden
+ Decision
+ PASS / GAP / ADR
```

---

# 제 5장 논리 아키텍처
## Application Responsibility를 Logical Technical Node로 변환

## 1. 장의 목적과 핵심 정의

### FIG-05-01. 대표 Architecture

```text
Application
pdmg-ui
pdmg-jwt
pdmg-service + pdmg-fw
pdmg-om?
      ↓
Technical Capability
      ↓
LTN-PD-01 UI Delivery
LTN-PD-02 Authentication
LTN-PD-03 Application Runtime
LTN-PD-04 Data Service
LTN-PD-05 Integration [CONDITIONAL]
LTN-PD-06 Operations [OPEN]
```

- Application ≠ Logical Technical Node.
- Build Module ≠ Logical Technical Node.
- Logical Node ≠ Physical Host.
- 각 Logical Node는 Runtime Type, State, Scale Unit, Failure Domain, Security Boundary를 정의한다.

### 핵심 Architecture 질문

- Application과 Technical Node를 구분했는가?
- Logical Node별 State/Scale/Failure/Security가 있는가?
- Integration/OM Unknown을 Current로 과장하지 않았는가?
- --

## 2. Top-down → Drill-down Architecture

### FIG-05-02. Drill-down Route

```text
L0  논리 아키텍처 전체관점
 ↓
L1  Responsibility / Boundary
 ↓
L2  Module / Logical Node / Platform
 ↓
L3  Component / Contract
 ↓
L4  Runtime / Failure / Security
 ↓
L5  Source / Config / Evidence
```

### FIG-05-03. Drill-down — FIG-04-42. Logical Node Record

> 원천: `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`

```text
Logical Node
│
├─ Node ID
├─ Node Name
├─ Technical Role
├─ Application Mapping
├─ Runtime Type
├─ Inbound
├─ Outbound
├─ State
├─ Scale Unit
├─ Failure Domain
├─ Security Boundary
├─ HA Requirement
├─ Monitoring
├─ Owner
└─ Evidence Status
```

### FIG-05-04. Drill-down — FIG-04-07. Module ≠ Logical Node

> 원천: `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`

```text
Build Modules
│
├─ pdmg-service ───────┐
│                      │
├─ pdmg-fw ────────────┼────► Application Runtime Logical Node
│                      │
├─ pdmg-ui ────────────┼────► UI Delivery Logical Node
│                      │
├─ pdmg-jwt ───────────┼────► Authentication Logical Node
│                      │
└─ pdmg-om ────────────┴────► Operations Node [UNKNOWN]
```

### FIG-05-05. Drill-down — FIG-04-47. Logical Technical Decisions

> 원천: `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`

```text
Logical Technical Decisions
│
├─ Application ↔ Technical Node Mapping
├─ Framework Runtime Placement
├─ Authentication State / Key Runtime
├─ Integration Capability Scope
├─ FAST / DEEP Isolation
├─ Scale Unit
├─ Failure Domain
├─ Observability Coverage
└─ Logical → Physical Mapping
```

### FIG-05-06. Drill-down — 왜 Framework를 별도 Logical Node로 고정하지 않는가

> 원천: `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`

```text
Framework Runtime
= 별도 Technical Capability

BUT

Framework Runtime Node
= 반드시 독립 Node 아님
```

### FIG-05-07. Drill-down — FIG-04-04. Logical Technical Master

> 원천: `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`

```text
┌──────────────────────── CLIENT / CHANNEL ────────────────────────┐
│                                                                  │
│ User / Browser                                                   │
│                                                                  │
└─────────────────────────────┬────────────────────────────────────┘
                              │ HTTP
                              ▼
┌──────────────────────── UI DELIVERY ──────────────────────────────┐
│                                                                  │
│ UI Delivery Logical Node                                         │
│ - Static / Presentation                                          │
│ - Request Assembly                                               │
│ - ServiceId Call                                                 │
│                                                                  │
└─────────────────────────────┬────────────────────────────────────┘
                              │
              ┌───────────────┼────────────────┐
              │               │                │
              ▼               ▼                ▼
┌──────────────────┐ ┌──────────────────┐ ┌────────────────────────┐
│ AUTHENTICATION   │ │ APPLICATION      │ │ OPERATIONS             │
│ LOGICAL NODE     │ │ RUNTIME NODE     │ │ LOGICAL NODE           │
│                  │ │                  │ │                        │
│ Login / Token    │ │ Business Runtime │ │ [CURRENT DETAIL       │
│ JWKS / Identity  │ │                  │ │  UNKNOWN]             │
└─────────┬────────┘ └──────────┬───────┘ └────────────────────────┘
          │ Token               │
          └──────────────┬──────┘
                         ▼
              ┌───────────────────────────────┐
              │ APPLICATION RUNTIME NODE      │
              │                               │
              │ Framework Runtime Capability  │
              │ ├─ Filter / Context           │
              │ ├─ TCF / Dispatcher           │
              │ ├─ Timeout / Worker           │
              │ ├─ Transaction Control        │
              │ ├─ Error / Logging            │
              │ └─ Security Integration       │
              │                               │
              │ Business Runtime Capability   │
              │ ├─ Handler / Controller       │
              │ ├─ Facade                     │
              │ ├─ Service                    │
              │ └─ DAO                        │
              └───────────────┬───────────────┘
                              │ JDBC / Data Access
                              ▼
              ┌───────────────────────────────┐
              │ DATA SERVICE LOGICAL NODE     │
              │ RDW / DB                     │
              └───────────────────────────────┘

External Integration
= Approved API / Event / File / Data Mechanism
= Current PDMG Inventory dependent

Observability
= Cross-cutting Capability across all nodes
```

### FIG-05-08. Drill-down — FIG-04-44. Logical Technical Rules

> 원천: `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`

```text
R-LT-01
Application ≠ Logical Technical Node

R-LT-02
Module ≠ Logical Technical Node

R-LT-03
Logical Node ≠ Physical Host

R-LT-04
Technology Component ≠ Product Version

R-LT-05
pdmg-fw ≠ mandatory independent Runtime Node

R-LT-06
Client → Data Direct = Forbidden

R-LT-07
External → PDMG DB Direct DML = Forbidden

R-LT-08
Application Runtime → Data through Data Access Capability

R-LT-09
Every Node defines State / Scale / Failure / Security

R-LT-10
Observability crosses every Runtime Node

R-LT-11
Unknown Host/Product/Port remains OPEN

R-LT-12
PDMG Current ≠ NSIGHT broader platform by default
```

## 3. Runtime / Flow 관점

### FIG-05-09. Runtime Interpretation

```text
Application → Capability → Logical Node → Runtime Type → State/Scale/Failure → Physical Mapping
```

## 4. 정상패턴 / 금지패턴

### FIG-05-10. 정상패턴

```text
정상
Application
 ↓
Technical Capability
 ↓
Logical Node
 ↓
State / Scale / Failure / Security
```

### FIG-05-11. 금지패턴

```text
금지
pdmg-fw = 독립 Remote Server
Logical Node = Tomcat Version
Application Code = Hostname
 X
```

## 5. Architecture 의사결정 — 주안 / 대안

### 의사결정 주제: **Framework Runtime Placement**

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | pdmg-service와 in-process | pdmg-fw Remote Runtime |
| 장점 | • Current Source 정합<br>• Network hop 없음<br>• TX/Context 연계 단순 | • 독립 Scale 가능<br>• Process 격리 |
| 단점 | • 동일 Process failure domain | • Current 구조와 불일치<br>• RPC/계약/지연 증가 |
| 권고 | **주안 채택** — Source/Target/NFR Evidence와 정합 | 대안은 별도 ADR와 Runtime Evidence가 있을 때만 채택 |

## 6. PASS / GAP / ADR

### 현재 판정: `PASS / Current CONDITIONAL`

### 주요 GAP

- `[GAP/OPEN]` LTN-PD-05 Integration Current 범위
- `[GAP/OPEN]` LTN-PD-06 Operations/pdmg-om
- `[GAP/OPEN]` State/Scale/HA 실증

### 연계 ADR / Decision

- `ADR-LT-02 Framework Runtime Placement`
- `ADR-LT-06 Scale Unit`
- `ADR-LT-09 Logical→Physical Gate`

### FIG-05-12. PASS 전환구조

```text
Architecture Definition
   ↓
Source / Config Conformance
   ↓
Security / Runtime / Failure Test
   ↓
Deployment / Runtime Evidence
   ↓
Critical GAP / ADR Closure
   ↓
PASS / HG90
```

## 7. 기존 00~18 정의서 Trace

**v1 상세원천:** 기존 03 Application / 04 Logical / 06 Interface / 07 Data

| 기존 장 | 원천 파일 | 본 장에서 사용하는 관점 |
|---|---|---|
| 03 | 03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 04 | 04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 06 | 06_PDMG_INTERFACE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 07 | 07_PDMG_DATA_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |

## 8. 장 결론

```text
논리 아키텍처
=
대표 Architecture
+ Drill-down
+ Runtime
+ Normal / Forbidden
+ Decision
+ PASS / GAP / ADR
```

---

# 제 6장 물리 아키텍처
## Logical Node를 Center·WEB·WAS·JVM·DB로 Mapping

## 1. 장의 목적과 핵심 정의

### FIG-06-01. 대표 Architecture

```text
User
 ↓
GSLB
 ↓
L4
 ↓
WEB / Apache
 ↓
WAS / Tomcat JVM
 ↓
PDMG WAR
 ↓
Hikari / MyBatis / JDBC
 ↓
RDW / DB

Logical Node
 ↓
Environment
 ↓
Center
 ↓
Host / VM
 ↓
JVM / Process
 ↓
Artifact
 ↓
Port / DB / Storage
 ↓
Monitoring / Backup
```

- Server ≠ VM ≠ JVM ≠ WAR.
- 의왕 Main / 안성 DR은 Working Physical Reference로 사용하되 실제 PDMG Host Mapping은 Evidence로 확정한다.
- 정확한 Hostname·Port·Version·Server Count는 Evidence 없이는 OPEN이다.

### 핵심 Architecture 질문

- Logical Node가 실제 Host/VM/JVM/WAR로 Mapping되는가?
- Port/Firewall/LB/Config가 정합하는가?
- Capacity Candidate를 Production Fact로 표현하지 않는가?
- --

## 2. Top-down → Drill-down Architecture

### FIG-06-02. Drill-down Route

```text
L0  물리 아키텍처 전체관점
 ↓
L1  Responsibility / Boundary
 ↓
L2  Module / Logical Node / Platform
 ↓
L3  Component / Contract
 ↓
L4  Runtime / Failure / Security
 ↓
L5  Source / Config / Evidence
```

### FIG-06-03. Drill-down — FIG-05-06. Server / VM / JVM / WAR Boundary

> 원천: `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`

```text
Physical Server / Hypervisor
   ↓
VM
   ↓
OS
   ↓
Runtime Process
   ↓
JVM
   ↓
WAR / Application Artifact

Server ≠ VM ≠ JVM ≠ WAR
```

### FIG-06-04. Drill-down — FIG-16-14. JVM / WAR Isolation

> 원천: `16_PDMG_CAPACITY_PERFORMANCE_HA_DR_상세정의서_v1.md`

```text
Business Group A
 → JVM A

Business Group B
 → JVM B

Shared VM possible
but JVM failure domains separated
```

### FIG-06-05. Drill-down — FIG-05-09. WAS / JVM / WAR Architecture

> 원천: `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`

```text
WAS VM
 ├─ JVM Group A
 │   ├─ WAR ...
 │   └─ WAR ...
 └─ JVM Group B
     ├─ WAR ...
     └─ WAR ...

JVM Group / WAR placement
= [OPEN / Candidate]
```

### FIG-06-06. Drill-down — FIG-05-18. Physical Traceability

> 원천: `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`

```text
Application
 ↓
Artifact
 ↓
DeploymentId
 ↓
WAR
 ↓
JVM
 ↓
VM
 ↓
Host
 ↓
Center
 ↓
Metric / Evidence
```

### FIG-06-07. Drill-down — FIG-05-04. L0 — PDMG Physical Master Architecture

> 원천: `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`

```text
User / Browser
   ↓
GSLB [Working Baseline]
   ↓
L4 [Working Baseline]
   ↓
WEB / Apache
   ↓
WAS / Tomcat JVM
   ↓
PDMG WAR / Runtime
   ├─ pdmg-service
   └─ pdmg-fw
   ↓
Hikari / MyBatis / JDBC
   ↓
RDW / DB

Authentication Runtime
pdmg-jwt
   ↓
Token / JWKS
   ↓
PDMG Business Runtime

Operations / Monitoring / Backup
= Physical Mapping OPEN / Target Reference
```

### FIG-06-08. Drill-down — FIG-05-05. Logical Node → Physical Resource Mapping

> 원천: `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`

```text
LTN-PD-01 UI Delivery
   ↓
WEB/UI Runtime Resource

LTN-PD-02 Authentication
   ↓
JWT Runtime Resource

LTN-PD-03 Application Runtime
   ↓
WAS VM / JVM / WAR

LTN-PD-04 Data Service
   ↓
DB Service / DB Cluster

LTN-PD-05 Integration
   ↓
Integration Platform [CONDITIONAL]

LTN-PD-06 Operations
   ↓
Monitoring / Control [OPEN]
```

## 3. Runtime / Flow 관점

### FIG-06-09. Runtime Interpretation

```text
Logical Node → Center → Host/VM → JVM/WAR → Port/DB/Storage → Monitoring/Backup
```

## 4. 정상패턴 / 금지패턴

### FIG-06-10. 정상패턴

```text
정상
Logical Node
 ↓
Environment
 ↓
Center
 ↓
Host/VM
 ↓
JVM/WAR
 ↓
Port/DB/Storage
 ↓
Evidence
```

### FIG-06-11. 금지패턴

```text
금지
Server = JVM = WAR
Candidate Capacity = Production Fact
Port/Hostname 추정 기입
 X
```

## 5. Architecture 의사결정 — 주안 / 대안

### 의사결정 주제: **WAS 배치전략**

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | 중형 VM Scale-out + JVM/WAR 격리 | 대형 VM Scale-up |
| 장점 | • N+1/장애격리 유리<br>• 업무그룹 분리<br>• 수평확장 | • 구성 단순<br>• 노드 수 감소 |
| 단점 | • 운영 인스턴스 증가 | • Failure domain 큼<br>• GC/자원독점 영향 |
| 권고 | **주안 채택** — Source/Target/NFR Evidence와 정합 | 대안은 별도 ADR와 Runtime Evidence가 있을 때만 채택 |

## 6. PASS / GAP / ADR

### 현재 판정: `PASS / Current PARTIAL`

### 주요 GAP

- `[GAP/OPEN]` Artifact→Host/JVM/WAR
- `[GAP/OPEN]` 실제 Port/Firewall/Version
- `[GAP/OPEN]` RTO/RPO/Restore Evidence

### 연계 ADR / Decision

- `ADR-TASK-026 WAS Sizing`
- `ADR-TASK-027 WEB/WAS Topology`
- `ADR-TASK-028 JVM/WAR Isolation`

### FIG-06-12. PASS 전환구조

```text
Architecture Definition
   ↓
Source / Config Conformance
   ↓
Security / Runtime / Failure Test
   ↓
Deployment / Runtime Evidence
   ↓
Critical GAP / ADR Closure
   ↓
PASS / HG90
```

## 7. 기존 00~18 정의서 Trace

**v1 상세원천:** 기존 05 Physical / 16 Capacity

| 기존 장 | 원천 파일 | 본 장에서 사용하는 관점 |
|---|---|---|
| 05 | 05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 16 | 16_PDMG_CAPACITY_PERFORMANCE_HA_DR_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |

## 8. 장 결론

```text
물리 아키텍처
=
대표 Architecture
+ Drill-down
+ Runtime
+ Normal / Forbidden
+ Decision
+ PASS / GAP / ADR
```

---

# 제 7장 DR 센터 활용 전략
## AP 가용성과 DB 정합성을 분리하는 HA/DR 전략

## 1. 장의 목적과 핵심 정의

### FIG-07-01. 대표 Architecture

```text
Main Center
 ├─ WEB
 ├─ WAS
 └─ DB Local HA
      │
      │ replication / recovery
      ▼
DR Center
 ├─ WEB
 ├─ WAS
 └─ DR DB

Failure
 ↓
Detection
 ↓
Isolation
 ↓
Traffic Reroute
 ↓
Application / Config / Key
 ↓
Data Recovery
 ↓
Business Validation
```

- Local HA와 Center DR은 다르다.
- AP Active-Active/N+1은 주안 후보이나 DB 양방향 Active-Active는 정합성 리스크 때문에 별도 판단한다.
- RTO/RPO는 Architecture가 임의로 숫자를 만들지 않고 Business Criticality로 확정한다.

### 핵심 Architecture 질문

- Local HA와 DR을 구분했는가?
- DB 정합성보다 보여주기식 Active-Active를 우선하지 않는가?
- RTO/RPO/Restore/Failback Evidence가 있는가?
- --

## 2. Top-down → Drill-down Architecture

### FIG-07-02. Drill-down Route

```text
L0  DR 센터 활용 전략 전체관점
 ↓
L1  Responsibility / Boundary
 ↓
L2  Module / Logical Node / Platform
 ↓
L3  Component / Contract
 ↓
L4  Runtime / Failure / Security
 ↓
L5  Source / Config / Evidence
```

### FIG-07-03. Drill-down — FIG-16-19. RTO / RPO

> 원천: `16_PDMG_CAPACITY_PERFORMANCE_HA_DR_상세정의서_v1.md`

```text
Business Criticality
 ↓
RTO / RPO
 ↓
DR Tier
 ↓
Infrastructure / Data / Key / App
 ↓
Drill Evidence

Exact values
= [OPEN]
```

### FIG-07-04. Drill-down — FIG-05-16. DR — Center Failure

> 원천: `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`

```text
Main Center
  ↓ detect
Traffic Reroute
  ↓
DR Access
  ↓
DR WEB/WAS
  ↓
DR DB / Replication
  ↓
Config / Key / Artifact
  ↓
Business Validation
```

### FIG-07-05. Drill-down — FIG-05-15. HA — Main Center

> 원천: `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`

```text
GSLB / L4
 ↓
WEB N+1 / Pair
 ↓
WAS Active-Active / N+1 candidate
 ↓
DB Local HA
 ↓
Residual Capacity Validation
```

### FIG-07-06. Drill-down — FIG-05-07. Environment / Center Axis

> 원천: `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`

```text
Development
Test
Production
DR
   │
   └─ Deployment Environment

Main Center [의왕 Working Reference]
DR Center   [안성 Working Reference]

Center ≠ Environment
```

### FIG-07-07. Drill-down — FIG-05-19. Rule Set

> 원천: `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`

```text
R-PHY-01
Server ≠ VM ≠ JVM ≠ WAR

R-PHY-02
Logical Node는 실제 Physical Mapping을 가져야 한다.

R-PHY-03
미확정 Host/Port/Version은 OPEN으로 유지한다.

R-PHY-04
WEB와 WAS 책임을 분리한다.

R-PHY-05
WAS Scale-out 시 N+1 잔존용량을 검증한다.

R-PHY-06
DB Local HA와 Center DR을 구분한다.

R-PHY-07
Backup 성공 ≠ Restore/Business Recovery 성공.

R-PHY-08
Key/Secret은 Artifact 일반 Config와 분리한다.

R-PHY-09
Port/Firewall/LB/Config는 상호 정합해야 한다.

R-PHY-10
Capacity Candidate를 Production Fact로 표기하지 않는다.
```

### FIG-07-08. Drill-down — FIG-16-22. Rule Set

> 원천: `16_PDMG_CAPACITY_PERFORMANCE_HA_DR_상세정의서_v1.md`

```text
R-CAP-01
Assumption→Calculation→Candidate→Test→Measured→Approved 단계를 구분한다.

R-CAP-02
PDMG current worker values를 target capacity로 사용하지 않는다.

R-CAP-03
Tomcat/Worker/Hikari/DB pool을 end-to-end로 산정한다.

R-CAP-04
N+1 잔존용량을 검증한다.

R-CAP-05
Scale-out unit과 failure domain을 함께 결정한다.

R-CAP-06
Session 정책과 HA 전략을 일치시킨다.

R-CAP-07
Local HA와 DR을 구분한다.

R-CAP-08
RTO/RPO는 business criticality로 결정한다.

R-CAP-09
DR에는 app/config/key/interface/monitoring까지 포함한다.

R-CAP-10
Performance 결과를 runtime monitoring baseline으로 전환한다.
```

## 3. Runtime / Flow 관점

### FIG-07-09. Runtime Interpretation

```text
Failure Detect → Isolate → Traffic Switch → App/Config/Key/Data Recover → Validate → Failback
```

## 4. 정상패턴 / 금지패턴

### FIG-07-10. 정상패턴

```text
정상
Detect → Isolate → Reroute
→ Recover App/Config/Key/Data
→ Validate Business
→ Failback
```

### FIG-07-11. 금지패턴

```text
금지
Backup 성공 = DR 성공
DB 양방향 Active-Active를
정합성 검증 없이 채택
 X
```

## 5. Architecture 의사결정 — 주안 / 대안

### 의사결정 주제: **DR 전략**

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | AP Active-Active/N+1 + DB 정합성 우선 DR | DB 양방향 Active-Active |
| 장점 | • 정합성 위험 낮음<br>• 운영복잡도 통제 | • RTO 단축 가능성 |
| 단점 | • 전환절차/복구 자동화 필요 | • Split-brain/충돌 위험<br>• 운영복잡도/검증비용 큼 |
| 권고 | **주안 채택** — Source/Target/NFR Evidence와 정합 | 대안은 별도 ADR와 Runtime Evidence가 있을 때만 채택 |

## 6. PASS / GAP / ADR

### 현재 판정: `PASS / Current OPEN`

### 주요 GAP

- `[GAP/OPEN]` RTO/RPO 확정
- `[GAP/OPEN]` DR Key/Artifact/Config 동기화
- `[GAP/OPEN]` Failback/Business Validation

### 연계 ADR / Decision

- `ADR-TASK-030 HA`
- `ADR-TASK-031 DR`
- `ADR-TASK-032 DB HA/DR`

### FIG-07-12. PASS 전환구조

```text
Architecture Definition
   ↓
Source / Config Conformance
   ↓
Security / Runtime / Failure Test
   ↓
Deployment / Runtime Evidence
   ↓
Critical GAP / ADR Closure
   ↓
PASS / HG90
```

## 7. 기존 00~18 정의서 Trace

**v1 상세원천:** 기존 05 Physical / 16 Capacity·HA·DR

| 기존 장 | 원천 파일 | 본 장에서 사용하는 관점 |
|---|---|---|
| 05 | 05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 16 | 16_PDMG_CAPACITY_PERFORMANCE_HA_DR_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |

## 8. 장 결론

```text
DR 센터 활용 전략
=
대표 Architecture
+ Drill-down
+ Runtime
+ Normal / Forbidden
+ Decision
+ PASS / GAP / ADR
```

---

# 제 8장 메커니즘
## Architecture를 실제로 움직이게 하는 공통 실행 규칙

## 1. 장의 목적과 핵심 정의

### FIG-08-01. 대표 Architecture

```text
HTTP
 ↓
DefaultFilter
 ↓
SecurityFilterChain
 ↓
DispatcherServlet
 ↓
Interceptor
 ↓
OnlineTransactionController
 ↓
TcfFacade
 ↓
OnlineTimeoutExecutor
 ↓
TransactionDispatcher
 ↓
Handler
 ↓
Facade / Service / DAO
 ↓
DB
 ↓
Response / Error / Log
```

- Framework는 '어떻게 안전하게 실행할 것인가'를 소유한다.
- Business는 '무슨 업무를 수행할 것인가'를 소유한다.
- TCF ON/OFF가 달라도 Common Business Core는 Facade로 정렬하는 것이 주안이다.

### 핵심 Architecture 질문

- Framework와 Business 책임이 분리되는가?
- TCF ON/OFF의 Business Core가 정합하는가?
- Timeout/Error/Context/JWT Mechanism이 서로 연결되는가?
- --

## 2. Top-down → Drill-down Architecture

### FIG-08-02. Drill-down Route

```text
L0  메커니즘 전체관점
 ↓
L1  Responsibility / Boundary
 ↓
L2  Module / Logical Node / Platform
 ↓
L3  Component / Contract
 ↓
L4  Runtime / Failure / Security
 ↓
L5  Source / Config / Evidence
```

### FIG-08-03. Drill-down — FIG-08-04. L0 — Framework Mechanism Master

> 원천: `08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md`

```text
HTTP
 ↓
DefaultFilter
 ↓
SecurityFilterChain
 ↓
DispatcherServlet
 ↓
Interceptor
 ↓
OnlineTransactionController
 ↓
TcfFacade
 ↓
OnlineTimeoutExecutor
 ↓
TransactionDispatcher
 ↓
Handler
 ↓
Business
 ↓
Response / Error / Log
```

### FIG-08-04. Drill-down — FIG-12-12. TransactionContext

> 원천: `12_PDMG_MESSAGE_CONTEXT_ERROR_LOGGING_상세정의서_v1.md`

```text
TransactionContext
= ServiceId
+ ServiceContext reference
+ startedAt / elapsed

≠ DB Transaction
```

### FIG-08-05. Drill-down — FIG-08-09. TCF Facade

> 원천: `08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md`

```text
OnlineTransactionController
 ↓
TcfFacade
 ├─ TimeoutExecutor
 └─ Dispatcher

STF / ETF
= classes may exist
≠ current executed path unless evidence
```

### FIG-08-06. Drill-down — FIG-08-24. PASS Model

> 원천: `08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md`

```text
Architecture Definition
 ↓
PASS

Current Framework Conformance
 ↓
PARTIAL / GAP

Strong: Filter/TCF/Worker/TX
Gap: context, error, OFF parity
```

### FIG-08-07. Drill-down — FIG-12-19. Rule Set

> 원천: `12_PDMG_MESSAGE_CONTEXT_ERROR_LOGGING_상세정의서_v1.md`

```text
R-MSG-01
hdr_nhnis는 Framework 공통정보, dto는 Business Payload다.

R-MSG-02
GUID는 trace key이며 auth/business PK/idempotency key가 아니다.

R-MSG-03
Response Header는 처리 후 ServiceContext를 사용한다.

R-MSG-04
ServiceId source mismatch를 통제한다.

R-MSG-05
ThreadLocal은 request/worker lifecycle마다 clear한다.

R-MSG-06
Worker Context는 immutable snapshot을 지향한다.

R-MSG-07
Known error는 stable code/envelope를 사용한다.

R-MSG-08
Filter/Security error도 표준 contract를 지향한다.

R-MSG-09
Sensitive data/token/secret logging을 금지한다.

R-MSG-10
ImageLog는 business transaction과 독립될 수 있음을 명시한다.
```

### FIG-08-08. Drill-down — FIG-10-10. Physical Transaction Boundary

> 원천: `10_PDMG_TRANSACTION_TIMEOUT_THREAD_DB_상세정의서_v1.md`

```text
Worker
 ↓
TransactionTemplate BEGIN
 ├─ Dispatcher
 ├─ Handler
 ├─ Facade REQUIRED joins
 ├─ Service
 ├─ DAO / SQL
 └─ Deadline
 ↓
COMMIT / ROLLBACK
```

## 3. Runtime / Flow 관점

### FIG-08-09. Runtime Interpretation

```text
Filter → Security → MVC → TCF → Worker/TX → Handler → Facade → Service → DAO → DB
```

## 4. 정상패턴 / 금지패턴

### FIG-08-10. 정상패턴

```text
정상
Framework Control
 ↓
Handler/Controller Adapter
 ↓
Common Facade
 ↓
Business Service
 ↓
DAO
```

### FIG-08-11. 금지패턴

```text
금지
Handler → DAO
Controller → Mapper
Framework → 특정 업무 SQL
TCF OFF가 공통정책을 우회
 X
```

## 5. Architecture 의사결정 — 주안 / 대안

### 의사결정 주제: **TCF ON/OFF Business Core**

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | Handler/Controller 모두 Facade | OFF Controller→Service 직접 |
| 장점 | • Use Case/Tx/Validation 일관<br>• TCF 전환 영향 축소 | • 현재 일부 Source 변경 최소 |
| 단점 | • Facade 정비 필요 | • 정책 Drift<br>• 선후처리/Tx 차이 |
| 권고 | **주안 채택** — Source/Target/NFR Evidence와 정합 | 대안은 별도 ADR와 Runtime Evidence가 있을 때만 채택 |

## 6. PASS / GAP / ADR

### 현재 판정: `PASS / Current PARTIAL-GAP`

### 주요 GAP

- `[GAP/OPEN]` Mutable Worker Context
- `[GAP/OPEN]` TCF OFF Business Core Drift
- `[GAP/OPEN]` Early Error Envelope
- `[GAP/OPEN]` ServiceId mismatch defense

### 연계 ADR / Decision

- `ADR-TASK-004 Common Business Core`
- `ADR-TASK-005 TCF Policy`
- `ADR-TASK-009 Worker Context`

### FIG-08-12. PASS 전환구조

```text
Architecture Definition
   ↓
Source / Config Conformance
   ↓
Security / Runtime / Failure Test
   ↓
Deployment / Runtime Evidence
   ↓
Critical GAP / ADR Closure
   ↓
PASS / HG90
```

## 7. 기존 00~18 정의서 Trace

**v1 상세원천:** 기존 08 Framework / 10 Transaction / 11 Security / 12 Message

| 기존 장 | 원천 파일 | 본 장에서 사용하는 관점 |
|---|---|---|
| 08 | 08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 10 | 10_PDMG_TRANSACTION_TIMEOUT_THREAD_DB_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 11 | 11_PDMG_SECURITY_SSO_JWT_SESSION_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 12 | 12_PDMG_MESSAGE_CONTEXT_ERROR_LOGGING_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 13 | 13_PDMG_EVENT_CDC_ETL_BATCH_FILE_CACHE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |

## 8. 장 결론

```text
메커니즘
=
대표 Architecture
+ Drill-down
+ Runtime
+ Normal / Forbidden
+ Decision
+ PASS / GAP / ADR
```

---

# 제 9장 런타임 서비스
## 거래 한 건의 End-to-End 실행과 FAST/DEEP 경계

## 1. 장의 목적과 핵심 정의

### FIG-09-01. 대표 Architecture

```text
════════ Request Thread ════════
Filter
Security
MVC
Controller
TcfFacade
Future.get(timeout)
        │
        │ submit
        ▼
════════ Worker Thread ═════════
Context Install
TransactionTemplate BEGIN
Dispatcher
Handler
Facade
Service
DAO / Mapper
DB
Deadline
COMMIT / ROLLBACK
Context Clear
        │
        ▼
Response / Error / Evidence
```

- Current PDMG 핵심 Runtime은 Online/FAST Transaction 성격이다.
- Request Thread와 Worker Thread, HTTP Timeout과 DB Transaction Lifetime을 분리한다.
- NSIGHT의 DEEP 분석 흐름은 PDMG 온라인 Runtime과 자원/책임을 분리한다.

### 핵심 Architecture 질문

- Request Thread와 Worker Thread가 분리되는가?
- HTTP 504와 DB Rollback 완료를 동일시하지 않는가?
- FAST와 DEEP Workload를 분리하는가?
- --

## 2. Top-down → Drill-down Architecture

### FIG-09-02. Drill-down Route

```text
L0  런타임 서비스 전체관점
 ↓
L1  Responsibility / Boundary
 ↓
L2  Module / Logical Node / Platform
 ↓
L3  Component / Contract
 ↓
L4  Runtime / Failure / Security
 ↓
L5  Source / Config / Evidence
```

### FIG-09-03. Drill-down — FIG-09-11. Timeout Runtime

> 원천: `09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md`

```text
Future.get(5000ms)
 ├─ complete → response
 └─ timeout
      ↓
   cancel(true)
      ↓
   504

Worker may continue
```

### FIG-09-04. Drill-down — FIG-09-16. DB Runtime

> 원천: `09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md`

```text
Worker
 ↓
Transaction
 ↓
Hikari Connection
 ↓
JDBC Statement
 ↓
DB Session
 ↓
SQL wait / result
```

### FIG-09-05. Drill-down — FIG-09-04. L0 — End-to-End Online Runtime

> 원천: `09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md`

```text
Browser
 ↓
HTTP
 ↓
DefaultFilter
 ↓
SecurityFilterChain
 ↓
DispatcherServlet
 ↓
Interceptor
 ↓
OnlineTransactionController
 ↓
TcfFacade
 ↓
OnlineTimeoutExecutor
 ↓
Worker / TransactionTemplate
 ↓
Dispatcher / Handler
 ↓
Facade / Service / DAO / Mapper
 ↓
DB
 ↓
Response Advice / afterCompletion
 ↓
Filter finally
 ↓
HTTP Response
```

### FIG-09-06. Drill-down — FIG-09-09. Response Runtime

> 원천: `09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md`

```text
Business Result
 ↓
Controller return
 ↓
ResponseBodyAdvice
 ↓
hdr_nhnis + dto
 ↓
afterCompletion
 ↓
Filter finally
```

### FIG-09-07. Drill-down — FIG-09-14. Context Runtime

> 원천: `09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md`

```text
Request ThreadLocal
 ↓
Worker capture/install
 ↓
Business
 ↓
ResponseBody stored
 ↓
afterCompletion
 ↓
remove
```

### FIG-09-08. Drill-down — FIG-09-18. Runtime Failure Matrix

> 원천: `09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md`

```text
Filter fail
Security fail
Routing fail
Worker reject
Timeout
Business reject
DB fail
Response fail
 ↓
Different owner / evidence
```

## 3. Runtime / Flow 관점

### FIG-09-09. Runtime Interpretation

```text
Request Thread → submit → Worker Thread → TX → Business → DB → Response/Error/Evidence
```

## 4. 정상패턴 / 금지패턴

### FIG-09-10. 정상패턴

```text
정상
Request Thread
 ↓ submit
Worker Thread
 ↓ TX
Business
 ↓ DB
 ↓ Response/Evidence
```

### FIG-09-11. 금지패턴

```text
금지
HTTP 504 = Worker 종료
HTTP 504 = DB rollback 완료
Worker count = DB session count
 X
```

## 5. Architecture 의사결정 — 주안 / 대안

### 의사결정 주제: **Timeout 실행모델**

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | Request/Worker 분리 + 계층형 timeout | Request Thread 단일 실행 |
| 장점 | • 격리/Deadline 통제<br>• Overload 제어 | • 구조 단순 |
| 단점 | • Thread/Context 관리 복잡 | • 장기 DB 호출이 Request Thread 점유<br>• 격리 약함 |
| 권고 | **주안 채택** — Source/Target/NFR Evidence와 정합 | 대안은 별도 ADR와 Runtime Evidence가 있을 때만 채택 |

## 6. PASS / GAP / ADR

### 현재 판정: `PASS / Current CONDITIONAL`

### 주요 GAP

- `[GAP/OPEN]` JDBC Query Timeout/Cancel Evidence
- `[GAP/OPEN]` Late Worker
- `[GAP/OPEN]` Identity Binding
- `[GAP/OPEN]` Runtime→Deployment Correlation

### 연계 ADR / Decision

- `ADR-TASK-017 Timeout`
- `ADR-TASK-029 Pool Capacity`

### FIG-09-12. PASS 전환구조

```text
Architecture Definition
   ↓
Source / Config Conformance
   ↓
Security / Runtime / Failure Test
   ↓
Deployment / Runtime Evidence
   ↓
Critical GAP / ADR Closure
   ↓
PASS / HG90
```

## 7. 기존 00~18 정의서 Trace

**v1 상세원천:** 기존 09 Online Runtime / 10 TX / 13 Non-online

| 기존 장 | 원천 파일 | 본 장에서 사용하는 관점 |
|---|---|---|
| 09 | 09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 10 | 10_PDMG_TRANSACTION_TIMEOUT_THREAD_DB_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 13 | 13_PDMG_EVENT_CDC_ETL_BATCH_FILE_CACHE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |

## 8. 장 결론

```text
런타임 서비스
=
대표 Architecture
+ Drill-down
+ Runtime
+ Normal / Forbidden
+ Decision
+ PASS / GAP / ADR
```

---

# 제 10장 데이터플랫폼
## RDW·ADW 역할분리와 PDMG Data Access

## 1. 장의 목적과 핵심 정의

### FIG-10-01. 대표 Architecture

```text
PDMG
 ↓
Service
 ↓
DAO
 ↓
Mapper
 ↓
SqlId
 ↓
SQL
 ↓
RDW / DB

NSIGHT Data Platform
RDW
= Operational / Near-real-time

ADW
= Analytical / Mart / Heavy Query
```

- PDMG Current의 강한 Evidence는 MyBatis/JDBC 기반 RDW/DB 접근이다.
- PDMG가 ADW를 직접 사용하는지는 Datasource/Mapper Inventory로 확인해야 한다.
- Cross-system Direct DML/DB-Link를 정상패턴으로 두지 않는다.

### 핵심 Architecture 질문

- ServiceId→SQL→Table Lineage가 가능한가?
- RDW와 ADW의 역할을 분리하는가?
- Cross-system Direct DB DML을 금지하는가?
- --

## 2. Top-down → Drill-down Architecture

### FIG-10-02. Drill-down Route

```text
L0  데이터플랫폼 전체관점
 ↓
L1  Responsibility / Boundary
 ↓
L2  Module / Logical Node / Platform
 ↓
L3  Component / Contract
 ↓
L4  Runtime / Failure / Security
 ↓
L5  Source / Config / Evidence
```

### FIG-10-03. Drill-down — FIG-07-12. Data Lineage

> 원천: `07_PDMG_DATA_상세정의서_v1.md`

```text
Source
 ↓
Transform / SQL / ETL
 ↓
Target
 ↓
Consumer

Reverse:
Table → SqlId → DAO → ServiceId → Application
```

### FIG-10-04. Drill-down — FIG-07-04. L0 — PDMG Data Master

> 원천: `07_PDMG_DATA_상세정의서_v1.md`

```text
Business Meaning
   ↓
ServiceId
   ↓
Service / DAO
   ↓
Mapper / SqlId
   ↓
SQL
   ↓
Table / View
   ↓
RDW / DB
   ↓
Lineage / Evidence
```

### FIG-10-05. Drill-down — FIG-07-13. CDC / ETL Relation

> 원천: `07_PDMG_DATA_상세정의서_v1.md`

```text
Source Data
 ├─ Change stream → CDC → RDW
 └─ Bulk extract  → ETL → ADW

PDMG Current direct ownership
= [REFERENCE / OPEN]
```

### FIG-10-06. Drill-down — FIG-07-19. Rule Set

> 원천: `07_PDMG_DATA_상세정의서_v1.md`

```text
R-DA-01
Business concept→Logical Entity→Physical Object로 추적한다.

R-DA-02
Data Owner/Steward/SOR를 명시한다.

R-DA-03
Cross-system direct DML을 금지한다.

R-DA-04
ServiceId→DAO→Mapper→SqlId→Table 추적을 유지한다.

R-DA-05
RDW와 ADW의 Workload 목적을 분리한다.

R-DA-06
TX Manager와 DataSource를 정렬한다.

R-DA-07
Metadata/Lineage를 변경영향 분석에 사용한다.

R-DA-08
Critical Data에 Quality Rule을 둔다.

R-DA-09
Security classification과 lifecycle을 정의한다.

R-DA-10
PDMG Current에서 미확인 Data Flow를 창작하지 않는다.
```

### FIG-10-07. Drill-down — FIG-07-10. Mapper / SqlId Trace

> 원천: `07_PDMG_DATA_상세정의서_v1.md`

```text
ServiceId
 ↓
DAO
 ↓
Mapper Namespace
 ↓
SqlId
 ↓
SQL
 ↓
Table / View
```

### FIG-10-08. Drill-down — FIG-07-08. RDW / ADW Role

> 원천: `07_PDMG_DATA_상세정의서_v1.md`

```text
RDW
= Operational / Near-real-time / Information Service

ADW
= Analytical / Aggregation / Mart / Heavy Query

PDMG Current → RDW stronger evidence
PDMG Current → ADW [OPEN]
```

## 3. Runtime / Flow 관점

### FIG-10-09. Runtime Interpretation

```text
ServiceId → DAO → Mapper → SqlId → SQL/Table → RDW/ADW → Lineage/Evidence
```

## 4. 정상패턴 / 금지패턴

### FIG-10-10. 정상패턴

```text
정상
ServiceId → DAO → Mapper → SqlId
→ Table/View → Lineage

Online → RDW
Heavy Analysis → ADW
```

### FIG-10-11. 금지패턴

```text
금지
Cross-system Direct DML
RDW에 무제한 Heavy Query
Table Ownership 없는 변경
 X
```

## 5. Architecture 의사결정 — 주안 / 대안

### 의사결정 주제: **RDW/ADW 사용원칙**

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | Online/Analytical workload 분리 | RDW 중심 통합사용 |
| 장점 | • Online SLA 보호<br>• 자원격리<br>• 용도 명확 | • 구조 단순<br>• 데이터 복제 감소 |
| 단점 | • 데이터 동기화/운영 복잡도 | • Heavy query 간섭<br>• 확장성 한계 |
| 권고 | **주안 채택** — Source/Target/NFR Evidence와 정합 | 대안은 별도 ADR와 Runtime Evidence가 있을 때만 채택 |

## 6. PASS / GAP / ADR

### 현재 판정: `PASS / Current PARTIAL`

### 주요 GAP

- `[GAP/OPEN]` RDW/ADW Datasource Mapping
- `[GAP/OPEN]` Table/View Ownership
- `[GAP/OPEN]` Lineage/DQ 자동화
- `[GAP/OPEN]` CDC SLA Conflict

### 연계 ADR / Decision

- `ADR-TASK-021 RDW/ADW`
- `ADR-TASK-022 CDC SLA`
- `ADR-TASK-024 Metadata/Lineage`

### FIG-10-12. PASS 전환구조

```text
Architecture Definition
   ↓
Source / Config Conformance
   ↓
Security / Runtime / Failure Test
   ↓
Deployment / Runtime Evidence
   ↓
Critical GAP / ADR Closure
   ↓
PASS / HG90
```

## 7. 기존 00~18 정의서 Trace

**v1 상세원천:** 기존 07 Data / 06 Interface / 13 CDC·ETL

| 기존 장 | 원천 파일 | 본 장에서 사용하는 관점 |
|---|---|---|
| 07 | 07_PDMG_DATA_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 06 | 06_PDMG_INTERFACE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 13 | 13_PDMG_EVENT_CDC_ETL_BATCH_FILE_CACHE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |

## 8. 장 결론

```text
데이터플랫폼
=
대표 Architecture
+ Drill-down
+ Runtime
+ Normal / Forbidden
+ Decision
+ PASS / GAP / ADR
```

---

# 제 11장 마케팅플랫폼
## PDMG를 Marketing Platform 실행 Reference로 정의

## 1. 장의 목적과 핵심 정의

### FIG-11-01. 대표 Architecture

```text
Marketing Platform Responsibility
         ↓
PDMG Reference
│
├─ pdmg-ui
├─ pdmg-jwt
├─ pdmg-service
│   └─ pdmg-fw
└─ pdmg-om? [UNKNOWN]
         ↓
Program / ServiceId
         ↓
Business Runtime
         ↓
RDW / External Contract
```

- PDMG는 Marketing Platform의 Application/Runtime Reference로 사용할 수 있다.
- PDMG AS-IS의 `mg` Source Prefix와 NSIGHT Application Group `MP`는 동일하다고 자동 간주하지 않고 Mapping Registry/ADR로 연결한다.
- 실시간 Event/Kafka 기반 마케팅 전체 플랫폼 기능을 PDMG Current로 자동 승격하지 않는다.

### 핵심 Architecture 질문

- PDMG를 Marketing Platform Reference로 어떻게 Mapping하는가?
- `mg`와 `MP`의 Mapping을 승인 없이 동일시하지 않는가?
- Event/Kafka Target을 Current로 과장하지 않는가?
- --

## 2. Top-down → Drill-down Architecture

### FIG-11-02. Drill-down Route

```text
L0  마케팅플랫폼 전체관점
 ↓
L1  Responsibility / Boundary
 ↓
L2  Module / Logical Node / Platform
 ↓
L3  Component / Contract
 ↓
L4  Runtime / Failure / Security
 ↓
L5  Source / Config / Evidence
```

### FIG-11-03. Drill-down — FIG-03-43. Application GAP

> 원천: `03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md`

```text
PDMG Application
│
├─ pdmg-om
│   └─ Current implementation
│      [UNKNOWN]
│
├─ TCF OFF
│   └─ Controller → Service Direct
│      [GAP]
│
├─ Rule Layer
│   └─ Universal AS-IS 아님
│      [OPEN]
│
├─ ServiceId
│   └─ UI Catalog ↔ Backend Registry
│      [GAP]
│
├─ Handler Contract
│   └─ Dispatcher serviceId vs Context serviceId
│      mismatch defense
│      [PROPOSED]
│
├─ Naming
│   └─ Full 1:1 Class Stem
│      [VERIFY]
│
├─ Data
│   └─ RDW/ADW Mapper usage full inventory
│      [OPEN]
│
└─ Deployment
    └─ Module/Artifact/Process mapping
       [OPEN]
```

### FIG-11-04. Drill-down — FIG-03-03. Application Drill-down

> 원천: `03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md`

```text
L0  PDMG Application Landscape
 ↓
L1  Five Modules
 ↓
L2  Build / Process / Context
 ↓
L2  Package / Layer
 ↓
L3  Handler / Controller / Facade / Service / DAO
 ↓
L4  TCF ON / OFF Entry
 ↓
L5  Program / ServiceId / Mapper / Rule / Test
```

### FIG-11-05. Drill-down — FIG-03-25. ServiceId

> 원천: `03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md`

```text
mg | co | a | 9001 | S | 0
│    │    │    │      │   │
│    │    │    │      │   └─ Sequence
│    │    │    │      └──── Transaction Type
│    │    │    └─────────── Program Number
│    │    └──────────────── Function
│    └───────────────────── Business
└────────────────────────── Application/Major
```

### FIG-11-06. Drill-down — TEXT ARCHITECTURE — Application Checklist

> 원천: `03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md`

```text
Module?
 ↓
Process?
 ↓
Spring Context?
 ↓
Package?
 ↓
Layer?
 ↓
Component?
 ↓
ServiceId?
 ↓
Dependency?
 ↓
TCF ON/OFF?
 ↓
PASS / GAP
```

### FIG-11-07. Drill-down — FIG-03-04. PDMG 5-Module Application Map

> 원천: `03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md`

```text
┌────────────────────────── PDMG APPLICATION ────────────────────────────┐
│                                                                       │
│  ┌────────────────┐     ┌────────────────┐                            │
│  │ pdmg-ui        │     │ pdmg-jwt       │                            │
│  │ [AS-IS]        │     │ [AS-IS]        │                            │
│  │ UI / Request   │     │ Auth / Token   │                            │
│  └───────┬────────┘     └───────┬────────┘                            │
│          │ HTTP                 │ Bearer / JWKS                        │
│          └───────────────┬──────┘                                     │
│                          ▼                                            │
│  ┌─────────────────────────────────────────────────────────────────┐  │
│  │ pdmg-service [AS-IS]                                            │  │
│  │ Business Application Runtime                                    │  │
│  │                                                                 │  │
│  │  ┌───────────────────────────────────────────────────────────┐  │  │
│  │  │ pdmg-fw [AS-IS]                                          │  │  │
│  │  │ Framework Module / Runtime Mechanism                      │  │  │
│  │  │ Filter / Context / TCF / Timeout / Error / Logging       │  │  │
│  │  └────────────────────────┬──────────────────────────────────┘  │  │
│  │                           ▼                                     │  │
│  │ Handler → Facade → Service → DAO → Mapper                     │  │
│  └───────────────────────────┬─────────────────────────────────────┘  │
│                              │ JDBC                                  │
│                              ▼                                       │
│                          RDW / DB                                    │
│                                                                       │
│  ┌────────────────┐                                                   │
│  │ pdmg-om        │                                                   │
│  │ [UNKNOWN]      │                                                   │
│  └────────────────┘                                                   │
└───────────────────────────────────────────────────────────────────────┘
```

### FIG-11-08. Drill-down — FIG-03-44. Application Decisions

> 원천: `03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md`

```text
ADR-TASK-003
PDMG ↔ NSIGHT Mapping
      ↓

ADR-TASK-004
Common Business Core
      ↓

ADR-TASK-005
TCF ON/OFF Policy
      ↓

ADR-TASK-006
Rule Layer
      ↓

ADR-TASK-007
Standard Message
      ↓

ADR-TASK-008
Error Standard
```

## 3. Runtime / Flow 관점

### FIG-11-09. Runtime Interpretation

```text
MP Target → Mapping Registry → PDMG Program/ServiceId → Runtime → Data/Interface
```

## 4. 정상패턴 / 금지패턴

### FIG-11-10. 정상패턴

```text
정상
NSIGHT MP
 ↓ approved mapping
PDMG mg/co/a
 ↓
Program / ServiceId
 ↓
Business Runtime
```

### FIG-11-11. 금지패턴

```text
금지
MP = mg 자동 동일시
Kafka/Event Target = PDMG Current
Marketing Platform 전체 = pdmg-service
 X
```

## 5. Architecture 의사결정 — 주안 / 대안

### 의사결정 주제: **MP↔PDMG 코드 Mapping**

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | Mapping Registry + ADR | `mg`를 `MP`로 일괄 치환 |
| 장점 | • AS-IS 보존<br>• Traceability<br>• 단계적 전환 | • 표면상 단순/통일 |
| 단점 | • Registry 관리 필요 | • Source/운영 ID 영향<br>• 충돌/역추적 문제 |
| 권고 | **주안 채택** — Source/Target/NFR Evidence와 정합 | 대안은 별도 ADR와 Runtime Evidence가 있을 때만 채택 |

## 6. PASS / GAP / ADR

### 현재 판정: `CONDITIONAL PASS`

### 주요 GAP

- `[GAP/OPEN]` MP↔mg Mapping
- `[GAP/OPEN]` JWT Security
- `[GAP/OPEN]` Marketing Event/Interface Current Inventory

### 연계 ADR / Decision

- `ADR-TASK-003 PDMG↔NSIGHT Mapping`
- `ADR-TASK-002 Code Registry SSOT`

### FIG-11-12. PASS 전환구조

```text
Architecture Definition
   ↓
Source / Config Conformance
   ↓
Security / Runtime / Failure Test
   ↓
Deployment / Runtime Evidence
   ↓
Critical GAP / ADR Closure
   ↓
PASS / HG90
```

## 7. 기존 00~18 정의서 Trace

**v1 상세원천:** 기존 03 Application / 06 Interface / 11 Security / 15 Naming

| 기존 장 | 원천 파일 | 본 장에서 사용하는 관점 |
|---|---|---|
| 03 | 03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 06 | 06_PDMG_INTERFACE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 11 | 11_PDMG_SECURITY_SSO_JWT_SESSION_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 15 | 15_PDMG_NAMING_CODE_DEVELOPMENT_STANDARD_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |

## 8. 장 결론

```text
마케팅플랫폼
=
대표 Architecture
+ Drill-down
+ Runtime
+ Normal / Forbidden
+ Decision
+ PASS / GAP / ADR
```

---

# 제 12장 BI 포탈
## PDMG 경계 밖의 분석 소비계층과 Data Contract

## 1. 장의 목적과 핵심 정의

### FIG-12-01. 대표 Architecture

```text
PDMG / Operational Service
       ↓
RDW / Data Platform
       ↓
ADW / Analytical Platform
       ↓
BI Portal
       ↓
Self-BI / Report / Analysis

PDMG
≠ BI Portal Current Implementation
```

- BI Portal은 PDMG Current 내부 Module로 정의하지 않는다.
- BI는 Data Platform의 신뢰된 데이터와 표준 Interface/Data Contract를 소비하는 상위 분석 Application이다.
- PDMG와 BI의 연결점은 Application 직접 의존이 아니라 Data/Interface Contract다.

### 핵심 Architecture 질문

- BI가 PDMG 내부 Module이 아니라 Data Consumer임을 유지하는가?
- Data/Interface Contract를 통해 연결되는가?
- Operational Runtime과 Analytical Runtime을 분리하는가?
- --

## 2. Top-down → Drill-down Architecture

### FIG-12-02. Drill-down Route

```text
L0  BI 포탈 전체관점
 ↓
L1  Responsibility / Boundary
 ↓
L2  Module / Logical Node / Platform
 ↓
L3  Component / Contract
 ↓
L4  Runtime / Failure / Security
 ↓
L5  Source / Config / Evidence
```

### FIG-12-03. Drill-down — FIG-18-10. Data / Interface Overlay

> 원천: `18_PDMG_INTEGRATED_ARCHITECTURE_BASELINE_상세정의서_v1.md`

```text
ServiceId
 ↓
Business
 ↓
DAO / Mapper / SQL
 ↓
RDW

External Need
 ↓
InterfaceId
 ↓
API / Event / CDC / ETL / File
```

### FIG-12-04. Drill-down — FIG-06-09. Interface Contract

> 원천: `06_PDMG_INTERFACE_상세정의서_v1.md`

```text
InterfaceId
Source / Target
Purpose
Type
Protocol / Endpoint
Sync/Async
Schema / Header
GUID / Correlation
Error
Timeout / Retry
Idempotency
Security
SLA
Owner / Version
```

### FIG-12-05. Drill-down — FIG-06-05. Interface Architecture Principles

> 원천: `06_PDMG_INTERFACE_상세정의서_v1.md`

```text
IF-01 Purpose-driven
IF-02 P2P minimize
IF-03 Service-to-Service
IF-04 Sync/Async by business need
IF-05 Online/Bulk separation
IF-06 Contract-first
IF-07 Failure isolation
IF-08 Traceability
IF-09 Versioning
IF-10 Exception via Architecture Review
```

### FIG-12-06. Drill-down — FIG-07-11. Read / Write Boundary

> 원천: `07_PDMG_DATA_상세정의서_v1.md`

```text
Own / Approved Data
  ├─ READ
  └─ WRITE according to ownership

Other System Data
  ↓
Approved Interface / Data Contract

Cross-system direct DML
= Forbidden
```

### FIG-12-07. Drill-down — FIG-18-14. Target Alignment

> 원천: `18_PDMG_INTEGRATED_ARCHITECTURE_BASELINE_상세정의서_v1.md`

```text
NSIGHT Target
├─ Standard Interface
├─ RDW/ADW separation
├─ Event/CDC/ETL/File
├─ WEB/WAS scale-out
├─ HA/DR
├─ Security key/JWKS
├─ Observability
└─ Evidence closed loop
       ↓ compare
PDMG Current
       ↓
PASS / GAP / ADR
```

### FIG-12-08. Drill-down — FIG-06-04. L0 — PDMG Interface Master

> 원천: `06_PDMG_INTERFACE_상세정의서_v1.md`

```text
Business Interaction Need
   ↓
Interface Classification
   ↓
Source / Target Boundary
   ↓
Interface Contract
   ↓
Runtime Mechanism
   ↓
Failure / Recovery
   ↓
Operations / Evidence
```

## 3. Runtime / Flow 관점

### FIG-12-09. Runtime Interpretation

```text
RDW/ADW → Data Contract → BI Portal → Report/Self-BI → Governance
```

## 4. 정상패턴 / 금지패턴

### FIG-12-10. 정상패턴

```text
정상
Operational Data
 ↓
RDW / ADW
 ↓
Approved Data Contract
 ↓
BI Portal / Self-BI
```

### FIG-12-11. 금지패턴

```text
금지
BI → PDMG DAO
BI → PDMG 내부 Table DML
BI Runtime을 PDMG JVM에 혼재
 X
```

## 5. Architecture 의사결정 — 주안 / 대안

### 의사결정 주제: **BI 연결방식**

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | ADW/Data Contract 기반 | PDMG 내부 DB/DAO 직접접근 |
| 장점 | • 결합도 낮음<br>• 분석 workload 격리<br>• Data governance | • 즉시 개발 쉬움 |
| 단점 | • Data pipeline 필요 | • 강결합<br>• Online 영향<br>• Ownership 붕괴 |
| 권고 | **주안 채택** — Source/Target/NFR Evidence와 정합 | 대안은 별도 ADR와 Runtime Evidence가 있을 때만 채택 |

## 6. PASS / GAP / ADR

### 현재 판정: `TARGET REFERENCE / PDMG N-A`

### 주요 GAP

- `[GAP/OPEN]` BI Target 상세 Current Evidence 없음
- `[GAP/OPEN]` Data Contract/Interface Inventory
- `[GAP/OPEN]` ADW SLA/Ownership

### 연계 ADR / Decision

- `Interface/Data Contract ADR`
- `BI Target Architecture Decision`

### FIG-12-12. PASS 전환구조

```text
Architecture Definition
   ↓
Source / Config Conformance
   ↓
Security / Runtime / Failure Test
   ↓
Deployment / Runtime Evidence
   ↓
Critical GAP / ADR Closure
   ↓
PASS / HG90
```

## 7. 기존 00~18 정의서 Trace

**v1 상세원천:** 기존 06 Interface / 07 Data + NSIGHT BI Target

| 기존 장 | 원천 파일 | 본 장에서 사용하는 관점 |
|---|---|---|
| 06 | 06_PDMG_INTERFACE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 07 | 07_PDMG_DATA_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 18 | 18_PDMG_INTEGRATED_ARCHITECTURE_BASELINE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |

## 8. 장 결론

```text
BI 포탈
=
대표 Architecture
+ Drill-down
+ Runtime
+ Normal / Forbidden
+ Decision
+ PASS / GAP / ADR
```

---

# 제 13장 표준화와 10년 지속 가능성
## Naming·DevOps·Observability·Traceability로 Architecture를 유지

## 1. 장의 목적과 핵심 정의

### FIG-13-01. 대표 Architecture

```text
Architecture
 ↓
Naming / Code Standard
 ↓
Source
 ↓
CI / Rule Test
 ↓
Artifact
 ↓
Deployment
 ↓
Runtime Evidence
 ↓
Drift / GAP
 ↓
ADR
 ↓
New Baseline
```

- 표준은 문서가 아니라 CI와 Runtime에서 검증 가능한 Rule이어야 한다.
- Program→ServiceId→Package→Mapper→Artifact→Deployment→GUID를 하나의 Trace Chain으로 연결한다.
- Architecture Definition PASS와 Current Implementation PASS를 분리한다.

### 핵심 Architecture 질문

- Naming/Rule이 CI에서 자동검증되는가?
- Artifact/Deployment/Runtime Evidence가 Trace되는가?
- Critical GAP가 닫혀야 HG90가 되는가?
- --

## 2. Top-down → Drill-down Architecture

### FIG-13-02. Drill-down Route

```text
L0  표준화와 10년 지속 가능성 전체관점
 ↓
L1  Responsibility / Boundary
 ↓
L2  Module / Logical Node / Platform
 ↓
L3  Component / Contract
 ↓
L4  Runtime / Failure / Security
 ↓
L5  Source / Config / Evidence
```

### FIG-13-03. Drill-down — FIG-14-07. CI Gate

> 원천: `14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md`

```text
Commit
 ↓
Build
 ↓
Unit
 ↓
Naming / Dependency
 ↓
Contract / Security
 ↓
Architecture Rule
 ↓
Artifact
```

### FIG-13-04. Drill-down — FIG-17-20. Rule Set

> 원천: `17_PDMG_TRACEABILITY_PASS_GAP_ADR_상세정의서_v1.md`

```text
R-TR-01
모든 Critical Architecture Object는 machine-readable ID를 가진다.

R-TR-02
ServiceId를 Source/Data/Runtime 공통 추적축으로 사용한다.

R-TR-03
Architecture PASS와 Implementation PASS를 분리한다.

R-TR-04
Logging만으로 Runtime Evidence PASS를 선언하지 않는다.

R-TR-05
SourceCommit→ArtifactHash→DeploymentId를 유지한다.

R-TR-06
Critical Rule은 CI/Runtime Gate에서 검증한다.

R-TR-07
Drift는 GAP 또는 ADR로 닫는다.

R-TR-08
UNKNOWN/OPEN을 숨기지 않는다.

R-TR-09
승인된 ADR은 Model/Rule/Standard에 반영한다.

R-TR-10
HG90는 Evidence-backed Baseline Release다.
```

### FIG-13-05. Drill-down — FIG-14-20. Rule Set

> 원천: `14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md`

```text
R-OPS-01
Build once, immutable artifact를 promotion한다.

R-OPS-02
SourceCommit→ArtifactHash→DeploymentId를 추적한다.

R-OPS-03
Config와 Secret을 Artifact에서 분리한다.

R-OPS-04
OM Control Plane과 Business Runtime Plane을 분리한다.

R-OPS-05
GitLab/Runner/eCAMS 전략을 PDMG current implementation으로 자동표시하지 않는다.

R-OPS-06
Metric/Log/Trace를 ServiceId/GUID와 연결한다.

R-OPS-07
Thread/Worker/Hikari/DB pool을 분리 관측한다.

R-OPS-08
Backup 성공 외 Restore/Business Validation을 수행한다.

R-OPS-09
Deployment 후 runtime evidence를 수집한다.

R-OPS-10
Critical drift는 release gate에서 차단한다.
```

### FIG-13-06. Drill-down — FIG-14-11. Deployment Trace

> 원천: `14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md`

```text
sourceCommit
 ↓
buildId
 ↓
artifactHash
 ↓
deploymentId
 ↓
WAR/JVM/Host
 ↓
ServiceId/GUID
```

### FIG-13-07. Drill-down — FIG-18-19. Rule Set

> 원천: `18_PDMG_INTEGRATED_ARCHITECTURE_BASELINE_상세정의서_v1.md`

```text
R-BL-01
PDMG Current와 NSIGHT Target을 분리한다.

R-BL-02
모든 주요 Architecture Domain은 TEXT diagram과 evidence를 가진다.

R-BL-03
Critical GAP는 ADR 또는 구현/Evidence로 닫는다.

R-BL-04
Architecture PASS와 Implementation PASS를 분리한다.

R-BL-05
Unknown/Open 값을 숨기거나 추정하지 않는다.

R-BL-06
ServiceId를 Source/Data/Runtime trace의 핵심축으로 사용한다.

R-BL-07
Artifact/Deployment/Runtime evidence를 Baseline에 연결한다.

R-BL-08
Security/Performance/DR는 runtime test evidence가 없으면 최종 PASS가 아니다.

R-BL-09
Baseline 변경은 ADR/Version으로 관리한다.

R-BL-10
HG90만 공식 Release Baseline으로 본다.
```

### FIG-13-08. Drill-down — FIG-15-15. Artifact / Deployment Naming

> 원천: `15_PDMG_NAMING_CODE_DEVELOPMENT_STANDARD_상세정의서_v1.md`

```text
SourceCommit
 ↓
BuildId
 ↓
ArtifactHash
 ↓
DeploymentId

Exact enterprise syntax
= [OPEN]
```

## 3. Runtime / Flow 관점

### FIG-13-09. Runtime Interpretation

```text
Architecture → Rule/CI → Artifact → Deploy → Metric/Log/Trace → Drift → ADR → New Baseline
```

## 4. 정상패턴 / 금지패턴

### FIG-13-10. 정상패턴

```text
정상
Architecture
 ↓
Rule / Naming
 ↓
CI Test
 ↓
Artifact
 ↓
Deployment
 ↓
Runtime Evidence
 ↓
Drift / ADR
```

### FIG-13-11. 금지패턴

```text
금지
표준 문서만 작성
CI 검증 없음
Runtime Evidence 없음
그래도 PASS 선언
 X
```

## 5. Architecture 의사결정 — 주안 / 대안

### 의사결정 주제: **Architecture Governance**

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | CI + Runtime Evidence Gate | 문서 리뷰/수동점검 |
| 장점 | • 지속적 Conformance<br>• Drift 조기탐지<br>• HG90 신뢰성 | • 초기 도입 간단 |
| 단점 | • 자동화 투자 필요 | • 사람 의존<br>• 누락/노후화 |
| 권고 | **주안 채택** — Source/Target/NFR Evidence와 정합 | 대안은 별도 ADR와 Runtime Evidence가 있을 때만 채택 |

## 6. PASS / GAP / ADR

### 현재 판정: `CONDITIONAL PASS`

### 주요 GAP

- `[GAP/OPEN]` Naming Scanner CI
- `[GAP/OPEN]` Artifact/Deployment Identity
- `[GAP/OPEN]` Runtime Evidence Collector
- `[GAP/OPEN]` Critical ADR Closure

### 연계 ADR / Decision

- `ADR-TASK-033 CI`
- `ADR-TASK-034 Artifact Promotion`
- `ADR-TASK-035 Observability`
- `ADR-TASK-040 Runtime Evidence`

### FIG-13-12. PASS 전환구조

```text
Architecture Definition
   ↓
Source / Config Conformance
   ↓
Security / Runtime / Failure Test
   ↓
Deployment / Runtime Evidence
   ↓
Critical GAP / ADR Closure
   ↓
PASS / HG90
```

## 7. 기존 00~18 정의서 Trace

**v1 상세원천:** 기존 14 DevOps / 15 Naming / 17 Traceability / 18 Baseline

| 기존 장 | 원천 파일 | 본 장에서 사용하는 관점 |
|---|---|---|
| 14 | 14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 15 | 15_PDMG_NAMING_CODE_DEVELOPMENT_STANDARD_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 17 | 17_PDMG_TRACEABILITY_PASS_GAP_ADR_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |
| 18 | 18_PDMG_INTEGRATED_ARCHITECTURE_BASELINE_상세정의서_v1.md | 대표/Drill-down/Runtime/PASS-GAP Source |

## 8. 장 결론

```text
표준화와 10년 지속 가능성
=
대표 Architecture
+ Drill-down
+ Runtime
+ Normal / Forbidden
+ Decision
+ PASS / GAP / ADR
```

---

# 마무리 — 발표 Story 기반 PDMG 통합 Architecture Baseline

## FIG-99-01. 전체 Story와 Architecture 연결

```text
왜 다시 짓는가
 ↓
패러다임을 어떻게 전환하는가
 ↓
6단계로 어떻게 설계하는가
 ↓
Big Picture에서 책임을 어떻게 나누는가
 ↓
Logical Node로 어떻게 구조화하는가
 ↓
Physical Resource에 어떻게 배치하는가
 ↓
HA/DR로 어떻게 견디는가
 ↓
Mechanism으로 어떻게 통제하는가
 ↓
Runtime에서 실제 어떻게 실행되는가
 ↓
Data Platform을 어떻게 분리하는가
 ↓
Marketing Platform에 어떻게 Mapping하는가
 ↓
BI가 어떻게 데이터를 소비하는가
 ↓
Naming / DevOps / Evidence로 어떻게 10년 유지하는가
 ↓
HG90 Evidence-backed Architecture Baseline
```

## FIG-99-02. PDMG Master Runtime

```text
User / Browser
      ↓
pdmg-ui
      ↓
pdmg-jwt
      ↓
pdmg-service + pdmg-fw
      │
      ├─ Filter / Context / Security
      ├─ TCF / Dispatcher / Handler
      ├─ Worker / Timeout / Transaction
      ├─ Facade / Service
      └─ DAO / Mapper
              ↓
            RDW / DB

Physical
GSLB → L4 → Apache → Tomcat/JVM → WAR → DB

Cross-cutting
Interface / Security / Observability / DevOps / HA-DR / Traceability
```

## 최종 종합판정

| 평가축 | 현재 판정 | 최종 PASS 조건 |
|---|---|---|
| Architecture Definition | **CONDITIONAL PASS** | 13장 Story + 00~18 상세 정의 완료 |
| Current PDMG Conformance | **PARTIAL / GAP** | Critical Source/Runtime GAP Close |
| Security | **CRITICAL GAP** | RS256/JWKS/Key/Identity Binding |
| Physical / Deployment | **GAP** | Artifact→Host/JVM/WAR Evidence |
| Performance / HA / DR | **CONDITIONAL** | Load/Failure/RTO/RPO/Restore Evidence |
| Runtime Evidence | **MEDIUM / GAP** | DeploymentId/GUID/Metric/Trace 자동연계 |
| Final HG90 | **OPEN** | G80 승인 + Critical Gate PASS |

## Final Critical GAP

```text
JWT RS256 Issuer ↔ HMAC Verifier
JWT Key Lifecycle / Multi-instance
Trusted Principal ↔ Business User Binding
TCF OFF Business Core Drift
Mutable Worker ServiceContext
JDBC Query Timeout / Cancel Evidence
External Interface Inventory
RDW/ADW Data Ownership / Lineage
pdmg-om Current Scope
Artifact / Deployment → Host/JVM/WAR
Capacity / RTO / RPO / DR Evidence
Runtime Evidence Automation
```