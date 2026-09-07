# NSIGHT PDMG 아키텍처 정의서
# 제13장. 표준화와 10년 지속 가능성
## Story: “좋은 Architecture는 처음 잘 그린 그림이 아니라 계속 정합되는 체계다”


---

# 0. Opening Script

마지막 장의 질문은 단순합니다. 지금 잘 만든 구조가 3년, 5년, 10년 뒤에도 같은 원칙을 유지할 수 있는가입니다.

Architecture는 시간이 지나면 자동으로 낡습니다. Source가 바뀌고 Config가 바뀌고 Deployment가 바뀌기 때문입니다.

그래서 최종 Architecture는 **Naming → CI Rule → Immutable Artifact → Deployment Trace → Runtime Evidence → Drift → ADR → New Baseline**이라는 Closed Loop로 끝나야 합니다.

## FIG-13-01. 장 전체 Architecture

```text
Architecture
 ↓
Naming / Rule
 ↓
Source
 ↓
CI Test
 ↓
Artifact
 ↓
Deployment
 ↓
Runtime Evidence
 ↓
Drift
 ↓
GAP / ADR
 ↓
New Baseline
 ↓
HG90
```

마지막 그림은 Architecture가 유지되는 방식을 보여줍니다. Naming과 Rule이 Source/CI로 내려가고, 동일 Artifact가 Deployment된 뒤 Runtime Evidence로 검증됩니다. Drift가 생기면 GAP/ADR로 다시 Baseline을 갱신합니다. **지속가능성은 변하지 않는 Architecture가 아니라, 변화해도 다시 정합되는 Architecture**입니다.


---

# 1. Naming Backbone

## FIG-13-02. Naming Backbone

```text
Business
 ↓
Program
 ↓
ServiceId
 ↓
Package / Class
 ↓
Mapper / SqlId
 ↓
Artifact / Deployment
```

지속가능성의 시작은 식별입니다.

Business와 Source, Runtime이 서로 다른 이름체계를 쓰면 자동 Trace가 불가능합니다. ServiceId와 Program, Package/Mapper 축이 정합해야 합니다.

Naming은 규칙이 아니라 Trace Backbone입니다.


---

# 2. Machine-readable Rule

## FIG-13-03. Machine-readable Rule

```text
Architecture Standard
 ↓
Rule
 ↓
Scanner
 ↓
CI Gate
 ↓
PASS / FAIL
```

표준문서만으로는 10년을 유지할 수 없습니다.

규칙을 Source Scanner와 CI Gate로 옮겨야 새 코드가 Architecture를 어기는 순간 바로 알 수 있습니다.

예를 들어 Duplicate ServiceId, Handler→DAO 같은 금지 Dependency는 기계검증 대상입니다.


---

# 3. Build Once / Promote

## FIG-13-04. Build Once / Promote

```text
Source Commit
 ↓
Build
 ↓
Artifact Hash
 ↓
DEV
 ↓
TEST
 ↓
PROD
 ↓
DR
```

환경마다 다시 Build하면 같은 Source라도 Binary가 달라질 수 있습니다.

Build Once 후 동일 Artifact를 승격하면 테스트한 Binary와 운영 Binary가 동일하다는 증명이 쉬워집니다.

ArtifactHash가 Evidence Chain의 핵심이 됩니다.


---

# 4. Config / Secret 분리

## FIG-13-05. Config / Secret 분리

```text
Artifact
= code/binary

Config
= environment

Secret / Key
= protected store
```

Artifact 안에 Environment Config와 Secret을 섞으면 Promotion과 Key Rotation이 어려워집니다.

특히 JWT Key는 Source나 일반 Config 파일과 분리해 Managed Store에서 다루는 방향이 필요합니다.

DR에서도 같은 Trust Chain을 복구할 수 있어야 합니다.


---

# 5. Deployment Trace

## FIG-13-06. Deployment Trace

```text
sourceCommit
 ↓
buildId
 ↓
artifactHash
 ↓
deploymentId
 ↓
WAR / JVM / Host
 ↓
ServiceId / GUID
```

Source와 Runtime을 연결하는 핵심 Chain입니다.

장애가 났을 때 로그만 보고 끝나는 것이 아니라 어떤 Commit/Artifact가 어느 Host에서 실행됐는지 알아야 합니다.

현재 이 Mapping 자동화가 주요 GAP입니다.


---

# 6. Observability → Evidence

## FIG-13-07. Observability → Evidence

```text
Metric
+ Log
+ Trace
 ↓
ServiceId / GUID
 ↓
Deployment
 ↓
Architecture Rule
 ↓
Evidence
```

Observability의 목적을 Dashboard에서 끝내지 않습니다.

Metric/Log/Trace를 Architecture Rule과 연결해야 Runtime Evidence가 됩니다.

예를 들어 Timeout Rule을 실제 p95와 Timeout/DB Wait Evidence로 검증할 수 있어야 합니다.


---

# 7. Drift / ADR

## FIG-13-08. Drift / ADR

```text
Baseline
 ↓ compare
Source / Config / Runtime
 ↓
Drift
 ↓
GAP
 ↓
ADR / Fix
 ↓
New Baseline
```

Drift는 실패가 아니라 변경의 신호입니다.

중요한 것은 Drift를 숨기지 않고 GAP로 기록하고, Architecture 변경이 필요하면 ADR로 Baseline을 갱신하는 것입니다.

이 과정이 없으면 문서와 실제가 다시 분리됩니다.


---

# 8. G00→HG90

## FIG-13-09. G00→HG90

```text
G00 Source
 ↓
G10 Document
 ↓
G20 Model
 ↓
G30 Conformance
 ↓
G40 Test
 ↓
G50 Runtime Evidence
 ↓
G60 Drift
 ↓
G70 GAP/ADR
 ↓
G80 Approval
 ↓
HG90
```

최종 Baseline은 단순 문서 승인으로 Release하지 않습니다.

Source와 Model, Test, Runtime Evidence, GAP/ADR가 모두 Gate를 통과해야 HG90로 승격합니다.

따라서 10년 지속가능성의 핵심은 '변하지 않는 구조'가 아니라 **변화해도 다시 정합되는 절차**입니다.

---

# 정상패턴과 금지패턴

## FIG-13-10. Normal Pattern

```text
Rule→CI→Artifact→Deploy→Evidence→Drift→ADR
```

정상패턴은 Architecture Rule을 Machine-readable하게 만들고 Source→Artifact→Deployment→Runtime Evidence까지 동일 Identity Chain으로 연결하는 것입니다.

## FIG-13-11. Forbidden Pattern

```text
문서만 표준 / 재빌드 / Evidence 없이 PASS
```

문서 Review만으로 표준 준수를 선언하거나 환경마다 Artifact를 다시 Build하고 Runtime Evidence 없이 HG90를 PASS시키는 것을 금지합니다.

---

# Architecture Decision

## FIG-13-12. 주안과 대안

```text
[주안]
CI + Runtime Evidence Gate

        VS

[대안]
문서 Review/수동점검
```

Machine-readable Rule, Immutable Artifact Promotion, Runtime Evidence Gate를 최종 운영모델로 채택합니다.

수동 문서 Review와 환경별 재빌드는 사람이 기억해야 하는 규칙을 늘리고 동일 Binary 검증을 어렵게 하므로 장기 운영모델로 채택하지 않습니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---


# Evidence / Conformance — 이 장의 Architecture를 무엇으로 증명하는가

## Evidence Architecture

```text
표준화와 10년 지속 가능성 Architecture Rule
        ↓
Source Evidence
        ↓
Config Evidence
        ↓
Runtime / Deployment Evidence
        ↓
Conformance Test
        ↓
PASS / GAP / ADR
```

### Source Evidence

- `[SOURCE]` Naming/ServiceId Standard
- `[SOURCE]` DevOps/OM/Observability
- `[SOURCE]` Traceability/PASS/GAP/ADR
- `[SOURCE]` Integrated Baseline

### Config Evidence

- `[CONFIG]` Java 21 / Spring Boot 3.5.14 / Gradle multi-project
- `[CONFIG]` External Config + Secret Store Target

### Runtime / Deployment Evidence

- `[RUNTIME]` sourceCommit→buildId→artifactHash→deploymentId→ServiceId/GUID→Runtime Evidence

### Architecture Decision

- `[DECISION]` Build Once / Immutable Promotion
- `[DECISION]` CI + Runtime Evidence Gate
- `[DECISION]` Architecture Closed Loop

### Related ADR

- `ADR-002 ServiceId SSOT`
- `ADR-033 CI/CD Orchestrator OPEN`
- `ADR-034 Immutable Artifact`
- `ADR-035 Observability`
- `ADR-037 Config/Secret`
- `ADR-040 Runtime Evidence Gate`

### Current GAP / OPEN

- `[GAP/OPEN]` Naming Scanner
- `[GAP/OPEN]` Artifact/Deployment Identity
- `[GAP/OPEN]` Runtime Evidence Collector
- `[GAP/OPEN]` OM Control Plane
- `[GAP/OPEN]` Critical ADR Closure

## 이 장의 판정

```text
Architecture Definition
        ↓
CONDITIONAL PASS

Current PDMG Conformance
        ↓
PARTIAL / GAP

Runtime Evidence Coverage
        ↓
MEDIUM

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `CI Scanner`
- `Immutable Artifact Identity`
- `Deployment Trace`
- `Runtime Evidence Collector`
- `ADR-033/critical ADR closure`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-13-16. 다음 장 Handoff

```text
표준화와 10년 지속 가능성
 ↓
좋은 Architecture는 처음 잘 그린 그림이 아니라 계속 정합되는 체계다
 ↓
남은 질문
"모든 Critical GAP와 Evidence를 닫고 공식 Architecture Baseline으로 Release한다"
 ↓
HG90 Evidence-backed Baseline
```

13장의 결론은 명확합니다. 최종 산출물은 고정된 그림이 아니라 **변경을 감지하고 Evidence로 다시 정합되는 Architecture 운영체계**입니다. Critical GAP가 Source/Runtime/DR Evidence로 닫히고 G80 승인을 통과할 때 HG90 Architecture Baseline으로 Release합니다.
