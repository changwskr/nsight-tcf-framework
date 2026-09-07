# NSIGHT PDMG 아키텍처 정의서
# 제13장. 표준화와 10년 지속 가능성
## Naming·DevOps·Observability·Traceability로 Architecture를 유지
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **12장까지 정의한 Application/Data/Runtime 구조를 시간이 지나도 Source와 Deployment에 정합하게 유지하는 운영체계를 정의한다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제12장에서 넘어온 질문
     ↓
12장까지 정의한 Application/Data/Runtime 구조를 시간이 지나도 Source와 Deployment에 정합하게 유지하는 운영체계를 정의한다.
     ↓
이 장이 답해야 할 질문
     ↓
Naming·DevOps·Observability·Traceability로 Architecture를 유지
```

이 장의 핵심 원칙은 다음과 같다.

- 표준은 CI/Runtime에서 검증 가능한 Rule이어야 한다.
- SourceCommit→ArtifactHash→DeploymentId→ServiceId→GUID를 연결한다.
- Architecture PASS와 Implementation PASS를 분리한다.
- Critical Drift는 GAP 또는 ADR로 반드시 닫는다.

---

# 1. Architecture는 시간이 지나면 자동으로 낡는다

## FIG-13-01. Architecture는 시간이 지나면 자동으로 낡는다

```text
Architecture Document
      ↓ time
Source Changes
Config Changes
Deployment Changes
      ↓
Drift
```

---

# 2. 지속 가능성의 시작은 Naming이다

## FIG-13-02. 지속 가능성의 시작은 Naming이다

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

---

# 3. ServiceId는 개발표준과 운영증적을 연결한다

## FIG-13-03. ServiceId는 개발표준과 운영증적을 연결한다

```text
ServiceId
 ↓
Handler
 ↓
Facade / Service
 ↓
DAO / SqlId
 ↓
GUID / Log
 ↓
Metric / Evidence
```

---

# 4. 표준은 문서가 아니라 Rule이어야 한다

## FIG-13-04. 표준은 문서가 아니라 Rule이어야 한다

```text
Standard Document
 ↓
Machine-readable Rule
 ↓
Scanner
 ↓
CI Gate
 ↓
PASS / FAIL
```

---

# 5. Build Once / Promote Artifact

## FIG-13-05. Build Once / Promote Artifact

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

same binary
```

---

# 6. Config와 Secret을 Artifact에서 분리한다

## FIG-13-06. Config와 Secret을 Artifact에서 분리한다

```text
Artifact
= code/binary

Environment Config
= externalized

Secret / Key
= protected store
```

---

# 7. Deployment Trace를 만든다

## FIG-13-07. Deployment Trace를 만든다

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

---

# 8. Observability를 Runtime Evidence로 확장한다

## FIG-13-08. Observability를 Runtime Evidence로 확장한다

```text
Metric
+ Log
+ Trace
 ↓
ServiceId / GUID
 ↓
Deployment / Host
 ↓
Rule Evidence
```

---

# 9. Drift를 자동 탐지한다

## FIG-13-09. Drift를 자동 탐지한다

```text
Architecture Baseline
 ↓ compare
Source / Config / Deployment / Runtime
 ↓
Drift
 ↓
GAP / ADR / Fix
```

---

# 10. Architecture Decision을 Baseline에 반영한다

## FIG-13-10. Architecture Decision을 Baseline에 반영한다

```text
Decision Task
 ↓
주안 / 대안
 ↓
Evidence
 ↓
ADR
 ↓
Rule / Model / Standard
 ↓
New Baseline
```

---

# 11. G00부터 HG90까지

## FIG-13-11. G00부터 HG90까지

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
G70 GAP / ADR
 ↓
G80 Approval
 ↓
HG90
```

---

# 12. 10년 지속 가능한 Architecture의 의미

## FIG-13-12. 10년 지속 가능한 Architecture의 의미

```text
좋은 Architecture
= 처음 잘 그린 그림
        X

좋은 Architecture
= 변화할 때마다
  Source / Runtime과
  다시 정합되는 체계
        O
```

---

# 13. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-13-13. Normal Pattern

```text
Architecture
 ↓
Rule / Naming
 ↓
CI Test
 ↓
Immutable Artifact
 ↓
Deployment
 ↓
Runtime Evidence
 ↓
Drift
 ↓
ADR
 ↓
New Baseline / HG90
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 14. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-13-14. Forbidden Pattern

```text
표준문서만 작성, 자동검증 없음 X
Environment별 재빌드             X
Secret을 Artifact에 포함          X
Runtime Evidence 없이 PASS        X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 15. Architecture Decision — Architecture Governance

## FIG-13-15. 주안과 대안

```text
[주안]
CI + Runtime Evidence Gate

        VS

[대안]
문서 Review / 수동점검
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | CI + Runtime Evidence Gate | 문서 Review / 수동점검 |
| 장점 | • 지속적 Conformance<br>• Drift 조기탐지<br>• HG90 신뢰성 | • 초기 도입 단순 |
| 단점 | • 자동화 투자 필요 | • 사람 의존<br>• 누락/노후화<br>• Runtime 불일치 |
| 권고 | **주안 채택** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 16. Current PDMG GAP

## FIG-13-16. GAP Map

```text
Current PDMG
│
├─ Naming Scanner CI Enforcement 미완료
├─ Artifact/Deployment Identity 표준 OPEN
├─ Runtime Evidence Collector 미완료
├─ pdmg-om/OM Control Plane Current 상세 OPEN
└─ Critical ADR 일부 미종결
```

- `[GAP/OPEN]` Naming Scanner CI Enforcement 미완료
- `[GAP/OPEN]` Artifact/Deployment Identity 표준 OPEN
- `[GAP/OPEN]` Runtime Evidence Collector 미완료
- `[GAP/OPEN]` pdmg-om/OM Control Plane Current 상세 OPEN
- `[GAP/OPEN]` Critical ADR 일부 미종결

---

# 17. 제13장 Architecture 판정

## FIG-13-17. Assessment

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
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| Naming/ServiceId | PASS/PARTIAL | Current 규칙 강함, 일부 Enterprise naming OPEN |
| CI Rule Gate | PARTIAL | 자동화 미완료 |
| Artifact Promotion | PASS architecture | 실제 pipeline evidence 필요 |
| Observability | PARTIAL | GUID/Log 강함, full trace 미완료 |
| HG90 | OPEN | Critical Gate/Evidence 필요 |

---

# 18. 원천 정의서 Trace

## FIG-13-18. Source Trace

```text
Story Chapter
   ↓
PDMG 00~18 Source
   ↓
Current Fact / Target Reference
   ↓
Architecture Rule / GAP
```

| 원천 | 용도 |
|---|---|
| 14 DevOps/OM/Observability | Story/Drill-down/Current-Target 근거 |
| 15 Naming | Story/Drill-down/Current-Target 근거 |
| 17 Traceability | Story/Drill-down/Current-Target 근거 |
| 18 Integrated Baseline | Story/Drill-down/Current-Target 근거 |

---

# 19. 다음 장으로 넘어가는 이유

## FIG-13-19. 13장 → HG90

```text
제13장
표준화와 10년 지속 가능성
      ↓
"Critical GAP와 Runtime Evidence를 닫고 공식 Baseline으로 Release할 수 있는가?"
      ↓
Architecture Baseline
HG90 Evidence-backed Architecture Baseline
```

---

# 20. 제13장 최종 결론

## FIG-13-20. Final Story

```text
표준화와 10년 지속 가능성
   ↓
Responsibility
   ↓
Boundary
   ↓
Runtime
   ↓
Evidence
   ↓
CONDITIONAL PASS
```

제13장의 결론은 **Naming·DevOps·Observability·Traceability로 Architecture를 유지**라는 한 문장으로 정리된다.
