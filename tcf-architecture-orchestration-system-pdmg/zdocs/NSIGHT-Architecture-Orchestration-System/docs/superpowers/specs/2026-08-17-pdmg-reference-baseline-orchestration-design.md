# PDMG Reference Baseline 기반 Architecture Orchestration System 재구성 설계

- 작성일: 2026-08-17
- 상태: DESIGN REVIEW
- 대상 시스템: NSIGHT Architecture Orchestration System
- Reference Source: `pdmg-ui`, `pdmg-fw`, `pdmg-service`, `pdmg-jwt`
- 설계 원칙: **Golden Reference Baseline + PDMG 내부 사전검증**

---

## 1. 목적

기존 Architecture Orchestration System은 `NSIGHT_TCF`, `BUSINESS_SERVICE`, `PDMG`, `PDMK`, `PDMP` 등 여러 System Scope를 동등하게 다루는 범용 구조였다.

이번 재구성에서는 다음 4개 프로젝트를 **기준 프로젝트군(Reference Project Set)** 으로 승격한다.

```text
pdmg-ui
pdmg-fw
pdmg-service
pdmg-jwt
```

이 4개 프로젝트에서 실제 Source / Configuration / Test / Runtime 구조를 검증하여 **PDMG Golden Source Baseline**과 **PDMG Reference Architecture**를 추출한다.

이후 다른 프로젝트나 신규 업무 모듈은 이 Reference Architecture와 비교하여 Architecture Conformance를 검증한다.

핵심 목표는 다음과 같다.

```text
4개 기준 프로젝트
      ↓
Source Baseline 검증
      ↓
Reference Architecture 추출
      ↓
Architecture Rule 생성
      ↓
Policy as Code / Test
      ↓
대상 프로젝트 Conformance 검사
      ↓
Drift / GAP / ADR
      ↓
Human Approval
      ↓
새 Baseline Release
```

---

## 2. 확인된 Reference Project 구조

### 2.1 `pdmg-ui`

역할은 **PDMG UI / 전문 테스트 / Architecture Design Shell**이다.

현재 Source에서 확인된 주요 특성:

- 독립 Gradle Root
- Java 21
- Spring Boot 3.5.14
- Boot JAR (`pdmg-ui.jar`)
- 패키지 Root: `nhnis.mg.ui`
- `pdmg-service` 전문 호출용 Relay 구조
- `tcf-ontology-service` Architecture Design 화면 연계

Reference 책임:

```text
Screen / UI Route
→ Transaction Catalog
→ ServiceId
→ pdmg-service 호출
```

### 2.2 `pdmg-fw`

역할은 **PDMG 공통 Framework / TCF Library**이다.

현재 Source에서 확인된 주요 특성:

- 독립 Gradle Root
- `java-library`
- Java 21
- Spring Boot 3.5.14 dependency baseline
- Boot 실행 앱이 아닌 Library JAR
- 패키지 Root: `nhnis.fw.*`
- TCF / STF / ETF / Dispatcher / Timeout / 공통 Filter / JWT / Transaction / Runtime Monitoring 포함

대표 Framework Runtime 구조:

```text
DefaultFilter
→ ServicePreventionInterceptor
→ OnlineTransactionController
→ TCF / STF
→ OnlineTimeoutExecutor
→ TransactionDispatcher
→ TransactionHandler
→ Business Layer
→ ETF
```

Reference 책임:

- TCF Runtime
- 공통 요청/응답
- 거래통제
- Timeout
- Transaction Infrastructure
- Framework JWT
- Runtime Monitoring
- 공통 Exception / Message / Utility

### 2.3 `pdmg-service`

역할은 **업무 서비스 Reference Application**이다.

현재 Source에서 확인된 주요 특성:

- 독립 Gradle Root
- `pdmg-fw`를 sibling project로 include
- Java 21
- Spring Boot 3.5.14
- 외부 Tomcat 배포용 WAR
- 패키지 Root: `nhnis.mg.co.a`
- Handler / Facade / Service / DAO / DTO 구조
- MyBatis / RDW DataSource
- 실제 ServiceId 샘플 다수

기준 업무 구조:

```text
OnlineTransactionController (pdmg-fw)
        ↓
TransactionDispatcher
        ↓
Handler
        ↓
Facade
        ↓
BizPrePostAspect
        ↓
Service
        ↓
DAO
        ↓
Mapper / SQL
        ↓
DB
```

중요: Transaction/Timeout 실제 AS-IS는 문서 문장만으로 확정하지 않고 `pdmg-fw` Timeout Executor와 `pdmg-service` Facade/Service Transaction 소스를 함께 검증하여 Reference Rule로 승격한다.

### 2.4 `pdmg-jwt`

역할은 **JWT 발급 / 관리 Reference Service**이다.

현재 Source에서 확인된 주요 특성:

- 독립 Gradle Root
- `pdmg-fw` sibling dependency
- Java 21
- Spring Boot 3.5.14
- 외부 Tomcat WAR
- 패키지 Root: `nhnis.mg.jw.a`
- RS256/JWKS 지원 컴포넌트
- ServiceId 기반 Handler/Facade/Service/DAO 구조
- JWKS Endpoint 제공

Reference 책임:

```text
Authentication / Token Issue
→ Private Key Boundary
→ JWT
→ JWKS / Public Key
→ Token Store / Refresh / Revoke
→ Security Policy
```

---

## 3. 기준선 계층

새 시스템에서는 Baseline을 3계층으로 나눈다.

```text
L0 — RAW SOURCE BASELINE
     4개 프로젝트의 실제 Source/Config/Test

L1 — PDMG VERIFIED BASELINE
     4개 프로젝트 내부 불일치 제거 또는 GAP 등록 후 검증된 기준

L2 — PDMG REFERENCE ARCHITECTURE
     다른 프로젝트에 적용할 Architecture Standard / Rule / Model
```

### L0 RAW SOURCE

소스가 존재한다는 사실만 기록한다.

```text
[AS-IS]
실제 현재 코드
```

L0의 모든 구현이 올바른 표준이라고 가정하지 않는다.

### L1 VERIFIED BASELINE

4개 프로젝트끼리 다음을 상호 검증한다.

- Java/Spring/Gradle Version
- Package Ownership
- TCF Runtime Flow
- ServiceId Pattern
- Handler/Facade/Service/DAO 계층
- Transaction Boundary
- Timeout Policy
- JWT Algorithm / Key Boundary
- Request/Response Message
- Build / WAR / JAR
- Test Pattern
- Logging / Runtime Monitoring

서로 충돌하면 Reference Standard로 승격하지 않고 `PDMG-REFERENCE-GAP`을 만든다.

### L2 REFERENCE ARCHITECTURE

L1에서 검증된 항목만 다른 프로젝트의 기준으로 사용한다.

```text
REFERENCE_RULE
REFERENCE_MODEL
REFERENCE_PATTERN
REFERENCE_POLICY
```

---

## 4. Source of Truth 우선순위 변경

기존 범용 우선순위를 아래처럼 변경한다.

```text
1. PDMG Verified Runtime Evidence
2. PDMG Verified Source
3. PDMG Applied Configuration
4. PDMG Reference Architecture Model
5. Approved PDMG ADR / Decision
6. PDMG Reference Standard
7. PDMG Guide / README / Design Document
8. Legacy NSIGHT/PDMK/PDMP 자료
9. 일반 기술 이론
```

단, L0 Source와 L2 Reference Standard가 충돌하면:

```text
L0 AS-IS
≠
L2 REFERENCE

→ PDMG INTERNAL GAP
```

으로 관리하며 Source를 무조건 표준으로 승격하지 않는다.

---

## 5. System Scope 재정의

기존 Scope를 폐기하지 않고 역할을 재정의한다.

### 기준 Scope

```text
PDMG_REFERENCE
├─ PDMG_UI
├─ PDMG_FW
├─ PDMG_SERVICE
└─ PDMG_JWT
```

### 검증 대상 Scope

```text
TARGET_PROJECT
├─ NSIGHT_TCF
├─ BUSINESS_SERVICE
├─ PDMK
├─ PDMP
├─ EOS
└─ NEW_PROJECT
```

핵심 차이는 다음과 같다.

```text
PDMG_REFERENCE = 기준
TARGET_PROJECT = 기준 준수 여부를 검사받는 대상
```

---

## 6. Orchestrator Mission 유형 재정의

기존 4개 Run Type을 유지하되 PDMG Reference 전용 Run Type을 추가한다.

| Run Type | 목적 |
|---|---|
| `REFERENCE_BOOTSTRAP` | 4개 PDMG 프로젝트 Source Baseline 생성 |
| `REFERENCE_RECONCILIATION` | 4개 프로젝트 내부 Architecture 충돌 검증 |
| `REFERENCE_RELEASE` | 검증된 PDMG Reference Architecture 승격 |
| `QUICK_CHECK` | 단순 Architecture Rule 검사 |
| `VERTICAL_SLICE` | ServiceId 중심 E2E 검증 |
| `DECISION_REVIEW` | TX/JWT/Session 등 의사결정 |
| `CONFORMANCE_REVIEW` | Target Project가 PDMG Reference를 따르는지 검증 |
| `RELEASE_VALIDATION` | Target Project Baseline 최종 검증 |

최초 실행 순서는 반드시 다음과 같다.

```text
REFERENCE_BOOTSTRAP
        ↓
REFERENCE_RECONCILIATION
        ↓
REFERENCE_RELEASE
        ↓
CONFORMANCE_REVIEW / VERTICAL_SLICE / ...
```

---

## 7. Agent Catalog 재구성

기존 Agent를 유지하되 Reference Architecture 관점으로 책임을 변경하고 4개 전용 Agent를 추가한다.

### Core Orchestration Agents

| Agent | 변경 후 책임 |
|---|---|
| Orchestrator | Reference vs Target 구분, Run Type, Team 구성 |
| Baseline Agent | 4개 Reference Source Baseline + Target Baseline |
| Document Agent | Reference 문서와 Target 문서 분리 |
| Model Agent | Reference Model과 Target Model을 별도로 생성 |
| Source Agent | Source Trace 추출 |
| Code Rule Agent | Reference Rule → Policy-as-Code |
| Test Agent | Reference Rule / Target Conformance Test |
| Deploy Agent | Build/Artifact/Deploy 증적 |
| Runtime Evidence Agent | Runtime Evidence Chain |
| Drift Agent | Internal Drift + Conformance Drift 분리 |
| GAP/ADR Agent | Reference GAP / Target GAP 분리 |
| Gate Manager | Reference Gate / Target Gate 분리 |

### 신규 Reference Agents

#### `PDMG-REFERENCE-BASELINE-AGENT`

4개 프로젝트를 하나의 Reference Project Set으로 인벤토리화한다.

#### `PDMG-REFERENCE-RULE-EXTRACTOR`

실제 구현에서 반복되는 구조를 후보 Rule로 추출한다.

예:

```text
pdmg-service와 pdmg-jwt 모두
Handler → Facade → Service → DAO
```

을 발견했다고 해서 즉시 표준 확정하지 않고 `REFERENCE_RULE_CANDIDATE`로 만든다.

#### `PDMG-REFERENCE-RECONCILIATION-AGENT`

4개 프로젝트의 상충 구현을 탐지한다.

예:

```text
pdmg-service DTO Pattern
≠
pdmg-jwt DTO Pattern
```

이 경우 업무 특성 차이인지 표준 충돌인지 분류한다.

#### `CONFORMANCE-AGENT`

Target Project와 PDMG Reference Architecture를 비교한다.

```text
REFERENCE EXPECTED
          ↕
TARGET ACTUAL
          ↓
MATCH / ALLOWED_VARIANT / GAP / EXCEPTION
```

---

## 8. Architecture Model 재구성

기존 단일 Architecture Model을 `Reference Model`과 `Target Model`로 분리한다.

```text
20-MODEL/
├─ reference/
│  ├─ pdmg-reference-model.yaml
│  ├─ framework-model.yaml
│  ├─ application-model.yaml
│  ├─ jwt-security-model.yaml
│  ├─ ui-integration-model.yaml
│  └─ reference-rules.yaml
│
├─ target/
│  └─ <target>-architecture-model.yaml
│
└─ conformance/
   ├─ reference-target-matrix.yaml
   └─ allowed-variants.yaml
```

Reference Entity에는 다음 속성을 추가한다.

```yaml
referenceStatus: VERIFIED | CANDIDATE | VARIANT | DEPRECATED
referenceSource:
  - PDMG_FW
  - PDMG_SERVICE
referenceEvidenceIds: []
```

---

## 9. Reference Architecture 핵심 모델

PDMG Reference에서는 최소 다음 Model을 별도 관리한다.

### 9.1 Framework Model

```text
Filter
→ Interceptor
→ OnlineTransactionController
→ STF
→ Timeout Executor
→ Dispatcher
→ Handler
→ ETF
```

### 9.2 Business Application Model

```text
ServiceId
→ Handler
→ Facade
→ Service
→ DAO
→ Mapper
→ SQL
→ Table
```

### 9.3 UI Integration Model

```text
UI Route
→ Transaction Catalog
→ ServiceId
→ HTTP Relay
→ pdmg-service
```

### 9.4 JWT Security Model

```text
Login/SSO ServiceId
→ Token Issuer
→ Private Key
→ JWT
→ JWKS
→ Public Key Validation
→ Refresh/Revoke
```

### 9.5 Runtime Policy Model

```text
TransactionPolicy
TimeoutPolicy
SecurityPolicy
LoggingPolicy
RuntimeEvidencePolicy
```

---

## 10. Reference Rule 유형

Rule은 4개 레벨로 분리한다.

### R1 — Structural Rule

- package naming
- Handler / Facade / Service / DAO dependency
- ServiceId uniqueness
- DTO naming
- Mapper namespace / SQL ID

### R2 — Framework Rule

- TCF ON/OFF 진입점
- Dispatcher routing
- Timeout Executor
- Transaction Context
- STF/ETF lifecycle

### R3 — Security Rule

- JWT issuer boundary
- Private Key storage/use
- JWKS/public key exposure
- token expiry/refresh/revoke
- internal call validation

### R4 — Runtime/Operational Rule

- Runtime Evidence Chain
- Transaction Begin/Commit/Rollback evidence
- Timeout evidence
- SQL elapsed
- Thread/Hikari/JVM evidence
- sensitive logging prohibition

---

## 11. Drift 분류 재구성

기존 Drift 외에 Reference 관점 Drift를 추가한다.

| Drift Type | 의미 |
|---|---|
| `REFERENCE_INTERNAL_DRIFT` | 4개 PDMG 기준 프로젝트끼리 충돌 |
| `REFERENCE_DOCUMENT_DRIFT` | Reference 문서와 Reference Source 불일치 |
| `REFERENCE_RUNTIME_DRIFT` | Reference Source/Config와 실제 Runtime 불일치 |
| `TARGET_CONFORMANCE_DRIFT` | Target Project가 Reference Rule과 불일치 |
| `ALLOWED_VARIANT` | 의도적으로 허용된 구현 변형 |
| `TARGET_EXCEPTION` | 승인 필요 예외 |

예:

```text
PDMG Reference
Handler → Facade → Service → DAO

Target
Handler → DAO

→ TARGET_CONFORMANCE_DRIFT
```

---

## 12. Gate 재구성

G00~HG90 번호는 유지하되 두 종류의 Gate Profile을 둔다.

### 12.1 Reference Gate Profile

```text
RG00 Reference Source Baseline
RG10 Reference Document
RG20 Reference Model
RG30 Reference Source/Rule
RG40 Reference Test
RG50 Reference Runtime Evidence
RG60 Reference Internal Drift
RG70 Reference GAP/ADR
RG80 Reference Approval
RHG90 Reference Baseline Release
```

### 12.2 Target Conformance Gate Profile

```text
G00 Target Source Baseline
G10 Target Document
G20 Target Model
G30 Reference ↔ Target Code
G40 Conformance Test
G50 Target Runtime Evidence
G60 Target Drift
G70 Target GAP/ADR
G80 Human Approval
HG90 Target Baseline Release
```

Reference Baseline이 `RHG90 PASS` 되기 전에는 Target Project의 최종 HG90 PASS를 허용하지 않는다.

---

## 13. G00 Source Baseline 변경

기존 G00은 Repository 전체를 대상으로 했지만 새 `RG00`은 정확히 4개 프로젝트를 기준으로 한다.

```text
pdmg-ui
pdmg-fw
pdmg-service
pdmg-jwt
```

반드시 생성할 인벤토리:

```text
reference-project-inventory.yaml
reference-source-inventory.yaml
reference-config-inventory.yaml
reference-test-inventory.yaml
reference-build-inventory.yaml
reference-runtime-evidence-inventory.yaml
```

Branch/Commit을 ZIP에서 확인할 수 없으면 `UNKNOWN`으로 유지한다.

---

## 14. G10 Document Baseline 변경

Reference 문서는 다음 두 범주로 나눈다.

```text
REFERENCE-SUPPORTING-DOCUMENT
REFERENCE-CLAIM-CANDIDATE
```

README/설계서가 실제 Source와 일치해야 `CONFIRMED REFERENCE CLAIM`으로 승격한다.

문서보다 Source를 우선하되, Source가 잘못된 구현일 가능성이 있으므로 Reference Rule 확정은 G70/RG80 의사결정을 거친다.

---

## 15. G20 Reference Model 변경

Model의 핵심 Root를 다음처럼 고정한다.

```yaml
referenceArchitecture:
  id: PDMG-REFERENCE
  projects:
    - pdmg-ui
    - pdmg-fw
    - pdmg-service
    - pdmg-jwt
```

ServiceId Trace는 PDMG 서비스와 JWT를 우선 Vertical Slice 대상으로 한다.

```text
mgcoaXXXXYY
mgjwaXXXXYY
```

UI는 해당 ServiceId를 소비하는 Consumer Relation으로 연결한다.

---

## 16. G30 Reference Rule Extraction 변경

G30에서는 단순 Model↔Source 비교 외에 다음을 수행한다.

```text
4개 프로젝트 Source
      ↓
반복 구조 탐지
      ↓
REFERENCE_RULE_CANDIDATE
      ↓
상충 구조 탐지
      ↓
VARIANT / GAP / ADR
      ↓
검증 Rule
```

Rule Source Provenance를 반드시 기록한다.

```yaml
ruleId: RR-APP-001
observedIn:
  - pdmg-service
  - pdmg-jwt
confidence: HIGH
status: CANDIDATE
```

---

## 17. G40 Conformance Test 변경

Test는 두 그룹이다.

### Reference Self-Test

4개 Reference Project가 Reference Rule을 실제로 만족하는지 검증한다.

### Target Conformance Test

Target Project가 Reference Rule을 만족하는지 검증한다.

Evaluator 구조는 동일하다.

```text
Evaluator
→ Measured Value
→ Threshold
→ Result
```

수동 PASS 금지 원칙을 유지한다.

---

## 18. G50 Runtime Evidence 변경

Reference Architecture를 공식화하려면 최소 `pdmg-service`와 `pdmg-jwt`의 대표 ServiceId Runtime Evidence가 필요하다.

권장 Pilot:

```text
pdmg-service
mgcoa5530S0 또는 실제 확인 ServiceId 1건

pdmg-jwt
mgjwa1000C0 또는 실제 확인 ServiceId 1건
```

Runtime 환경이 없으면 `RG50=HOLD`, `RHG90=HOLD`다.

정적 Reference Model/Rule 생성은 계속 가능하지만 `VERIFIED REFERENCE`로 최종 승격하지 않는다.

---

## 19. G60~G80 변경

### G60

4개 Reference Project 내부 Drift와 Target Conformance Drift를 분리한다.

### G70

Reference 내부 충돌은 다음으로 분류한다.

```text
DOCUMENT_ERROR
SOURCE_DEFECT
INTENTIONAL_VARIANT
REFERENCE_GAP
ADR_REQUIRED
EXCEPTION
```

### G80

다음은 Human Approval 필수다.

- Reference Transaction Boundary
- Timeout 기본 정책
- TCF ON/OFF 책임
- JWT Private Key / JWKS 경계
- Package / Layer Standard의 Breaking Change
- Reference Exception
- PDMG Reference Baseline 승격

---

## 20. Baseline ID 정책

### Source Baseline

```text
PDMG-SRC-YYYYMMDD-NNN
```

### Reference Architecture Baseline

```text
PDMG-REF-YYYYMMDD-NNN
```

### Reference Model Version

```text
PDMG-MODEL-MAJOR.MINOR.PATCH
```

### Target Run

```text
ACL-RUN-YYYYMMDD-NNN
```

### Target Baseline

기존 시스템별 Baseline 규칙을 유지하되 `referenceBaselineId`를 필수로 연결한다.

```yaml
referenceBaselineId: PDMG-REF-20260817-001
```

---

## 21. Workspace 재구성

기존 `03-WORKSPACE`를 Reference와 Target으로 분리한다.

```text
03-WORKSPACE/
├─ REFERENCE/
│  ├─ 00-IN/
│  ├─ 10-DOCUMENT/
│  ├─ 20-MODEL/
│  ├─ 30-CODE/
│  ├─ 40-TEST/
│  ├─ 50-RUNTIME-EVIDENCE/
│  ├─ 60-DRIFT/
│  ├─ 70-GAP-ADR/
│  ├─ 80-GATE/
│  └─ 90-OUT/
│
├─ TARGET-TEMPLATE/
│  ├─ 00-IN/
│  ├─ 10-DOCUMENT/
│  ├─ 20-MODEL/
│  ├─ 30-CODE/
│  ├─ 40-TEST/
│  ├─ 50-RUNTIME-EVIDENCE/
│  ├─ 60-DRIFT/
│  ├─ 70-GAP-ADR/
│  ├─ 80-GATE/
│  └─ 90-OUT/
│
└─ RUNS/
```

---

## 22. 새 최상위 디렉터리 구조

```text
NSIGHT-Architecture-Orchestration-System/
│
├─ START-HERE.md
├─ 00-MASTER-PROMPT.md
├─ AGENTS.md
├─ ARCHITECTURE-ORCHESTRATION-RULES.md
│
├─ 00-REFERENCE-BASELINE/
│  ├─ REFERENCE-PROJECTS.md
│  ├─ PDMG-SOURCE-BASELINE.md
│  ├─ PDMG-REFERENCE-ARCHITECTURE.md
│  ├─ PDMG-REFERENCE-RULES.md
│  ├─ PDMG-ALLOWED-VARIANTS.md
│  └─ PDMG-REFERENCE-GAPS.md
│
├─ 01-ORCHESTRATOR/
│  ├─ ORCHESTRATOR-PROMPT.md
│  ├─ REFERENCE-MISSION-ROUTING-RULES.md
│  ├─ TARGET-MISSION-ROUTING-RULES.md
│  ├─ TEAM-SELECTION-RULES.md
│  └─ ...
│
├─ 02-AGENT-CATALOG/
│  ├─ 01-PDMG-REFERENCE-BASELINE-AGENT.md
│  ├─ 02-PDMG-REFERENCE-RULE-EXTRACTOR.md
│  ├─ 03-PDMG-REFERENCE-RECONCILIATION-AGENT.md
│  ├─ 04-DOCUMENT-AGENT.md
│  ├─ 05-MODEL-AGENT.md
│  ├─ 06-SOURCE-AGENT.md
│  ├─ 07-CODE-RULE-AGENT.md
│  ├─ 08-TEST-AGENT.md
│  ├─ 09-DEPLOY-AGENT.md
│  ├─ 10-RUNTIME-EVIDENCE-AGENT.md
│  ├─ 11-CONFORMANCE-AGENT.md
│  ├─ 12-DRIFT-AGENT.md
│  ├─ 13-GAP-ADR-AGENT.md
│  └─ 14-GATE-MANAGER-AGENT.md
│
├─ 03-WORKSPACE/
│  ├─ REFERENCE/
│  ├─ TARGET-TEMPLATE/
│  └─ RUNS/
│
├─ 04-STAGE-PROMPTS/
│  ├─ REFERENCE/
│  │  ├─ STEP-R00-BOOTSTRAP.md
│  │  ├─ STEP-R10-DOCUMENT.md
│  │  ├─ STEP-R20-MODEL.md
│  │  ├─ STEP-R30-RULE-EXTRACTION.md
│  │  ├─ STEP-R40-SELF-TEST.md
│  │  ├─ STEP-R50-RUNTIME.md
│  │  ├─ STEP-R60-INTERNAL-DRIFT.md
│  │  ├─ STEP-R70-GAP-ADR.md
│  │  ├─ STEP-R80-APPROVAL.md
│  │  └─ STEP-R90-REFERENCE-RELEASE.md
│  └─ TARGET/
│     └─ STEP-00 ... STEP-90
│
├─ 05-GATE/
│  ├─ REFERENCE/
│  │  └─ RG00 ... RHG90
│  └─ TARGET/
│     └─ G00 ... HG90
│
├─ 06-TEMPLATES/
├─ 07-USE-CASES/
├─ 08-GOVERNANCE/
├─ 09-USER-VIEWS/
└─ 99-REFERENCE/
```

---

## 23. 최초 구축 Run

재구성된 시스템의 첫 실행은 Target Project 검증이 아니라 **PDMG Reference Baseline 생성**이어야 한다.

```text
RUN 1
REFERENCE_BOOTSTRAP
      ↓
4개 Project Inventory
      ↓
RG00

RUN 2
REFERENCE_RECONCILIATION
      ↓
Document / Model / Code
      ↓
Internal Drift
      ↓
GAP / ADR

RUN 3
REFERENCE_RELEASE
      ↓
Test
      ↓
Runtime Evidence
      ↓
Human Approval
      ↓
RHG90
      ↓
PDMG-REF-...
```

첫 Reference Baseline이 완료된 후에만:

```text
TARGET_PROJECT
      ↓
CONFORMANCE_REVIEW
```

를 수행한다.

---

## 24. 최초 Vertical Slice 권장 대상

### 업무 Reference

`pdmg-service`에서 실제 ServiceId 1개를 선택한다.

예시 후보:

```text
mgcoa5530S0
```

검증:

```text
ServiceId
→ Handler
→ Facade
→ Service
→ DAO
→ Mapper/SQL
→ Table
→ Test
→ Runtime
```

### JWT Reference

`pdmg-jwt`에서 실제 ServiceId 1개를 선택한다.

예시 후보:

```text
mgjwa1000C0
```

검증:

```text
ServiceId
→ Handler
→ Facade
→ Service
→ Token Issuer
→ Key/JWKS
→ Token Store
→ Test
→ Runtime
```

### UI Reference

`pdmg-ui`에서 위 ServiceId 호출 Route와 Relay를 연결한다.

### Framework Reference

`pdmg-fw`에서 해당 거래의 공통 Runtime을 연결한다.

---

## 25. 성공 기준

재구성 완료 후 다음 질문에 답할 수 있어야 한다.

1. PDMG의 공식 Reference Source는 정확히 어느 4개 프로젝트인가?
2. 각 Reference Project의 역할과 책임 경계는 무엇인가?
3. 어떤 Rule이 실제 Source에서 검증된 Reference Rule인가?
4. Rule의 근거가 어느 프로젝트/파일/Runtime Evidence인가?
5. 4개 프로젝트 내부에서 서로 충돌하는 구현은 무엇인가?
6. Target Project가 Reference Architecture를 얼마나 준수하는가?
7. 허용된 Variant와 Architecture GAP을 구분할 수 있는가?
8. Reference Rule을 ArchUnit/Scanner/Test로 자동 검증할 수 있는가?
9. Runtime Evidence가 Reference Model과 연결되어 있는가?
10. 어떤 PDMG Reference Baseline을 기준으로 Target이 검증되었는가?

---

## 26. 비목표

이번 재구성에서 하지 않는 것은 다음과 같다.

- `pdmg-ui`, `pdmg-fw`, `pdmg-service`, `pdmg-jwt`의 모든 구현을 무조건 정답으로 선언
- 기존 NSIGHT_TCF Source를 삭제하거나 폐기
- PDMK/PDMP를 PDMG와 동일한 AS-IS로 강제
- Runtime Evidence 없이 PDMG Reference Baseline 최종 PASS
- Human Approval 없이 Transaction/JWT 등 핵심 Reference Rule 확정

---

## 27. 핵심 설계 결정

### DEC-01

`pdmg-ui`, `pdmg-fw`, `pdmg-service`, `pdmg-jwt`을 **Reference Project Set**으로 사용한다.

### DEC-02

Reference Source는 즉시 표준이 아니며, `RAW → VERIFIED → REFERENCE` 3단계 승격 절차를 거친다.

### DEC-03

Reference Baseline과 Target Baseline을 분리한다.

### DEC-04

4개 Reference Project의 내부 불일치는 `REFERENCE_INTERNAL_DRIFT`로 먼저 해결한다.

### DEC-05

Target Project는 PDMG Reference Architecture에 대한 `Conformance` 관점으로 검증한다.

### DEC-06

Reference Architecture의 최종 승격에는 Runtime Evidence와 Human Approval이 필수다.

---

## 28. 설계 자체 검토 결과

### Placeholder 검사

- 구현에 필요한 미정 Placeholder 없음.
- 실제 Git Branch/Commit은 Source Evidence 단계에서 `UNKNOWN` 가능하도록 설계됨.

### 내부 일관성

- Reference와 Target 역할이 분리되어 있음.
- 기존 G00~HG90 Closed Loop를 유지하면서 Reference 전용 RG00~RHG90을 추가하므로 기존 운영방식과 충돌하지 않음.

### Scope 검사

- 이번 작업은 Architecture Orchestration System의 구조 재편까지를 범위로 함.
- Reference Baseline 실제 산출과 Target 프로젝트 실제 Conformance 검사는 후속 실행 Run으로 분리함.

### 모호성 검사

- `Source = Standard`로 오해하지 않도록 `RAW / VERIFIED / REFERENCE` 승격단계를 명시함.
- `PDMG Reference`와 `Target Project`를 명시적으로 분리함.

---

# 최종 설계 요약

```text
              PDMG REFERENCE PROJECT SET

 pdmg-ui     pdmg-fw     pdmg-service     pdmg-jwt
    │           │             │               │
    └───────────┴──────┬──────┴───────────────┘
                       ▼
               RAW SOURCE BASELINE
                       ▼
               INTERNAL RECONCILIATION
                       ▼
                 VERIFIED BASELINE
                       ▼
              REFERENCE ARCHITECTURE
                       ▼
              REFERENCE RULE / MODEL
                       ▼
                  RHG90 RELEASE
                       ▼
                 PDMG-REF-XXXX
                       │
         ┌─────────────┴─────────────┐
         ▼                           ▼
   신규 업무서비스             기존 Target Project
         │                           │
         └─────────────┬─────────────┘
                       ▼
               CONFORMANCE REVIEW
                       ▼
            MATCH / VARIANT / GAP
                       ▼
             Test / Runtime Evidence
                       ▼
                Drift / GAP / ADR
                       ▼
                Human Approval
                       ▼
                      HG90
```

