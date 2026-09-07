# NSIGHT PDMG 아키텍처 정의서
# 제13장. 표준화와 10년 지속 가능성
## Story: “좋은 Architecture는 처음 잘 그린 그림이 아니라 계속 정합되는 체계다”
## STORY-FIRST / TEXT-ARCHITECTURE-FIRST / TOP-DOWN → DRILL-DOWN / EVIDENCE-FIRST

> 최종 문서 Edition: `FINAL V4 — Story + Architecture + Drill-down + Evidence in Chapter`  
> 기준일: `2026-09-01`  
> PDMG = Current / Source / Config / Runtime  
> NSIGHT = Target / Alignment / Strategy Reference  
> 문서 상태: **FINAL V4 WORKING BASELINE** / Evidence는 본 장 내부에서 Architecture와 함께 판정

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

이제 이 전체 그림을 위에서 아래로 해부하겠습니다. 이번 버전에서는 그림의 박스와 화살표를 직접 설명하고, 반복적인 형식문장은 최소화합니다. 각 Drill-down은 상위 그림의 어느 부분을 확대하는지 명확하게 연결합니다.

## FIG-13-02. Drill-down Route

```text
L0 전체 Story
 ↓
L1 책임 / Boundary
 ↓
L2 Logical / Application / Platform
 ↓
L3 Component / Contract
 ↓
L4 Runtime / Failure / Security
 ↓
L5 Source / Config / Deployment / Evidence
```

---

# 1. Naming Backbone

## FIG-13-03. Naming Backbone

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

여기까지가 `Naming Backbone`의 역할입니다. 이제 이 구조를 더 내려가 **Machine-readable Rule**에서 다음 경계와 실행책임을 보겠습니다.

---

# 2. Machine-readable Rule

## FIG-13-04. Machine-readable Rule

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

여기까지가 `Machine-readable Rule`의 역할입니다. 이제 이 구조를 더 내려가 **Build Once / Promote**에서 다음 경계와 실행책임을 보겠습니다.

---

# 3. Build Once / Promote

## FIG-13-05. Build Once / Promote

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

여기까지가 `Build Once / Promote`의 역할입니다. 이제 이 구조를 더 내려가 **Config / Secret 분리**에서 다음 경계와 실행책임을 보겠습니다.

---

# 4. Config / Secret 분리

## FIG-13-06. Config / Secret 분리

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

여기까지가 `Config / Secret 분리`의 역할입니다. 이제 이 구조를 더 내려가 **Deployment Trace**에서 다음 경계와 실행책임을 보겠습니다.

---

# 5. Deployment Trace

## FIG-13-07. Deployment Trace

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

여기까지가 `Deployment Trace`의 역할입니다. 이제 이 구조를 더 내려가 **Observability → Evidence**에서 다음 경계와 실행책임을 보겠습니다.

---

# 6. Observability → Evidence

## FIG-13-08. Observability → Evidence

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

여기까지가 `Observability → Evidence`의 역할입니다. 이제 이 구조를 더 내려가 **Drift / ADR**에서 다음 경계와 실행책임을 보겠습니다.

---

# 7. Drift / ADR

## FIG-13-09. Drift / ADR

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

여기까지가 `Drift / ADR`의 역할입니다. 이제 이 구조를 더 내려가 **G00→HG90**에서 다음 경계와 실행책임을 보겠습니다.

---

# 8. G00→HG90

## FIG-13-10. G00→HG90

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

## FIG-13-11. Normal Pattern

```text
Rule→CI→Artifact→Deploy→Evidence→Drift→ADR
```

정상패턴은 각 영역이 자신의 책임을 유지하면서 명확한 Contract와 Runtime Boundary를 통해 연결되는 구조입니다. 변경·장애·보안·운영 책임이 이 경계를 따라 추적될 수 있어야 합니다.

## FIG-13-12. Forbidden Pattern

```text
문서만 표준 / 재빌드 / Evidence 없이 PASS
```

금지패턴은 기술적으로 불가능해서가 아니라 Architecture의 책임과 Evidence Chain을 무너뜨리기 때문에 제한합니다. 예외가 필요하면 묵시적으로 허용하지 않고 ADR와 Test Evidence로 승인합니다.

---

# Architecture Decision

## FIG-13-13. 주안과 대안

```text
[주안]
CI + Runtime Evidence Gate

        VS

[대안]
문서 Review/수동점검
```

이번 장의 주안은 **CI + Runtime Evidence Gate**입니다. 이 방향은 현재 확인된 PDMG 구조와 NSIGHT Target을 연결하면서 책임·운영·Evidence를 가장 일관되게 유지할 수 있는 선택입니다.

대안인 **문서 Review/수동점검**도 특정 조건에서는 사용할 수 있습니다. 다만 대안을 선택하려면 주안보다 나은 성능·가용성·비용 또는 운영효과가 PoC/Runtime Test로 확인되어야 하고, 그 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---

# Current GAP / PASS

## FIG-13-14. Current GAP

```text
Current
│
├─ naming scanner
├─ artifact/deployment identity
├─ runtime evidence collector
├─ om control plane
└─ critical adr closure
```

- `[GAP/OPEN]` naming scanner
- `[GAP/OPEN]` artifact/deployment identity
- `[GAP/OPEN]` runtime evidence collector
- `[GAP/OPEN]` om control plane
- `[GAP/OPEN]` critical adr closure

## FIG-13-15. Architecture Assessment

```text
Architecture Definition
 ↓
CONDITIONAL PASS

Current PDMG Conformance
 ↓
PARTIAL / GAP

Runtime Evidence
 ↓
MEDIUM

Architecture PASS
 ≠
Implementation PASS
```

Architecture가 잘 정의되었다는 것과 현재 구현이 그 정의를 지킨다는 것은 별도 판단입니다. 이 문서는 둘을 분리해 평가하며, `[OPEN]`과 `[UNKNOWN]`을 임의로 채우지 않습니다.

---

# Evidence Card

## FIG-13-16. Evidence Chain

```text
Architecture Rule
 ↓
Source / Config
 ↓
Build / Artifact
 ↓
Deployment
 ↓
ServiceId / GUID
 ↓
Metric / Log / Trace / Test
 ↓
Runtime Evidence
 ↓
PASS / GAP / ADR
```

본문에서는 Story와 Architecture 설명을 우선하고, Evidence는 이 카드에서 정리합니다. 앞으로 자동화 단계에서는 이 Chain을 Manifest/Registry로 기계적으로 생성하는 것이 목표입니다.

---


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

이 장의 정의가 완료되었다고 해서 현재 구현이 자동으로 PASS가 되는 것은 아니다. Source·Config·Runtime Evidence가 실제 Architecture Rule과 정합할 때 Current Conformance를 PASS로 승격한다.

---
# Chapter Closing Script

## FIG-13-17. 다음 장 Handoff

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

여기까지가 **표준화와 10년 지속 가능성**입니다. 이 장에서 중요한 것은 개별 기술을 많이 보여준 것이 아니라, 전체 그림을 시작점으로 책임과 Runtime을 하나씩 내려가며 설명했다는 점입니다.

이제 자연스럽게 다음 질문이 생깁니다. **모든 Critical GAP와 Evidence를 닫고 공식 Architecture Baseline으로 Release한다**. 그 질문이 다음 단계인 **HG90 Evidence-backed Baseline**의 출발점입니다.
