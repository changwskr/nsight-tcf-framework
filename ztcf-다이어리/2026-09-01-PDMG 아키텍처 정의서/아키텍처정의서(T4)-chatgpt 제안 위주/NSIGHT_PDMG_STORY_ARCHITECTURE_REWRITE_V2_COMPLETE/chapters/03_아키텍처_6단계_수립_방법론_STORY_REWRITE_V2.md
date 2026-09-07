# NSIGHT PDMG 아키텍처 정의서
# 제3장. 아키텍처 6단계 수립 방법론
## Story: “비전에서 시작해 실제 Runtime 검증까지 내려간다”
## STORY-FIRST / TEXT-ARCHITECTURE-FIRST / TOP-DOWN → DRILL-DOWN / EVIDENCE-FIRST

> 작성 버전: `REWRITE V2 — Story Quality / Figure-to-Explanation Consistency 보완`  
> 기준일: `2026-09-01`  
> PDMG = Current / Source / Config / Runtime  
> NSIGHT = Target / Alignment / Strategy Reference

---

# 0. Opening Script

전환 방향을 정했으면 이제 설계 순서가 필요합니다. 복잡한 정보계를 한 번에 그리면 Application, Server, Product, Runtime이 같은 레벨에 섞입니다.

그래서 NSIGHT Architecture는 **VISION → BIG PICTURE → LOGICAL → PHYSICAL → MECHANISM → RUNTIME**의 여섯 단계로 내려갑니다. 그리고 마지막에는 Evidence로 다시 위로 올라옵니다.

이 순서는 문서 목차가 아니라, 의사결정의 순서입니다.

## FIG-03-01. 장 전체 Architecture

```text
VISION
 ↓
BIG PICTURE
 ↓
LOGICAL
 ↓
PHYSICAL
 ↓
MECHANISM
 ↓
RUNTIME
 ↓
EVIDENCE / PASS
```

이제 이 전체 그림을 위에서 아래로 해부하겠습니다. 이번 버전에서는 그림의 박스와 화살표를 직접 설명하고, 반복적인 형식문장은 최소화합니다. 각 Drill-down은 상위 그림의 어느 부분을 확대하는지 명확하게 연결합니다.

## FIG-03-02. Drill-down Route

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

# 1. VISION — 방향을 고정한다

## FIG-03-03. VISION — 방향을 고정한다

```text
Scalable
Resilient
Data-Centric
 ↓
Architecture Decision Criteria
```

VISION은 제품을 정하는 단계가 아닙니다. 이후 모든 의사결정을 판단할 기준을 고정하는 단계입니다.

예를 들어 Scalable을 말하면서 하나의 거대한 Failure Domain을 만들 수는 없습니다. Resilient를 말하면서 Restore Drill 없이 DR PASS를 선언할 수도 없습니다.

VISION은 뒤의 Logical·Physical 장을 구속하는 설계계약입니다.

여기까지가 `VISION — 방향을 고정한다`의 역할입니다. 이제 이 구조를 더 내려가 **BIG PICTURE — 책임과 경계를 고정한다**에서 다음 경계와 실행책임을 보겠습니다.

---

# 2. BIG PICTURE — 책임과 경계를 고정한다

## FIG-03-04. BIG PICTURE — 책임과 경계를 고정한다

```text
User / Channel
 ↓
Application Boundary
 ↓
Data / Integration
 ↓
Operations
```

Big Picture에서는 세부 Class나 Product를 보지 않습니다. 누가 어떤 책임을 가지고 어떤 경계를 넘는지만 봅니다.

이 단계가 약하면 뒤에서 UI가 DB를 직접 보거나, External System이 내부 Table을 직접 변경하는 구조가 생길 수 있습니다.

따라서 Big Picture는 적은 박스로 전체 책임을 고정하는 단계입니다.

여기까지가 `BIG PICTURE — 책임과 경계를 고정한다`의 역할입니다. 이제 이 구조를 더 내려가 **LOGICAL — 기술 역할을 정의한다**에서 다음 경계와 실행책임을 보겠습니다.

---

# 3. LOGICAL — 기술 역할을 정의한다

## FIG-03-05. LOGICAL — 기술 역할을 정의한다

```text
Application Responsibility
 ↓
Technical Capability
 ↓
Logical Node
 ↓
State / Scale / Failure / Security
```

Logical Architecture는 제품을 사기 전에 필요한 기술 역할을 정의합니다.

Application Runtime, Authentication, Data Service, Integration, Operations 같은 Node를 만들고 각각의 State와 Scale, Failure Domain을 정의합니다.

이 단계가 있어야 Physical 설계가 제품과 서버 수량의 나열로 떨어지지 않습니다.

여기까지가 `LOGICAL — 기술 역할을 정의한다`의 역할입니다. 이제 이 구조를 더 내려가 **PHYSICAL — 실제 자원으로 내린다**에서 다음 경계와 실행책임을 보겠습니다.

---

# 4. PHYSICAL — 실제 자원으로 내린다

## FIG-03-06. PHYSICAL — 실제 자원으로 내린다

```text
Logical Node
 ↓
Environment
 ↓
Center
 ↓
Host / VM
 ↓
JVM / WAR
 ↓
Network / DB
```

Physical 장에서는 Logical Node가 실제 어디에서 실행되는지 확정합니다.

Server, VM, JVM, WAR를 구분하고 Network, DB, Storage, Monitoring까지 함께 봅니다.

여기서 Candidate Capacity와 실제 Production Fact를 구분하는 것이 중요합니다.

여기까지가 `PHYSICAL — 실제 자원으로 내린다`의 역할입니다. 이제 이 구조를 더 내려가 **MECHANISM — 실행규칙을 정한다**에서 다음 경계와 실행책임을 보겠습니다.

---

# 5. MECHANISM — 실행규칙을 정한다

## FIG-03-07. MECHANISM — 실행규칙을 정한다

```text
Filter
 ↓
Security
 ↓
TCF
 ↓
Timeout
 ↓
Transaction
 ↓
Error / Logging
```

Mechanism은 같은 Infrastructure 위에서도 거래가 동일한 방식으로 움직이게 만드는 표준입니다.

Filter가 Context를 만들고 Security가 Trust를 확인하며 TCF가 ServiceId를 라우팅하고 Worker/Transaction이 실행경계를 만듭니다.

시스템은 서버만으로 움직이지 않고 이런 실행규칙으로 움직입니다.

여기까지가 `MECHANISM — 실행규칙을 정한다`의 역할입니다. 이제 이 구조를 더 내려가 **RUNTIME — 시간축으로 검증한다**에서 다음 경계와 실행책임을 보겠습니다.

---

# 6. RUNTIME — 시간축으로 검증한다

## FIG-03-08. RUNTIME — 시간축으로 검증한다

```text
T0 Request
T1 Filter
T2 Security
T3 Controller
T4 Worker
T5 Transaction
T6 DB
T7 Response
```

Runtime 장에서는 정적인 구조를 실제 시간순서로 펼칩니다.

여기서 Request Thread와 Worker Thread, HTTP Timeout과 DB Transaction 수명 같은 차이가 드러납니다.

Runtime을 봐야 설계가 실제 동작과 맞는지 확인할 수 있습니다.

여기까지가 `RUNTIME — 시간축으로 검증한다`의 역할입니다. 이제 이 구조를 더 내려가 **Top-down과 Bottom-up을 닫는다**에서 다음 경계와 실행책임을 보겠습니다.

---

# 7. Top-down과 Bottom-up을 닫는다

## FIG-03-09. Top-down과 Bottom-up을 닫는다

```text
Top-down
Intent → Design → Rule
          ↓
Bottom-up
Source ← Test ← Runtime Evidence
```

Architecture는 위에서 아래로만 내려가면 문서가 되고, 아래에서 위로만 보면 Source 분석이 됩니다.

둘을 닫아야 Baseline이 됩니다. 상위 Intent가 Rule로 내려가고, Runtime Evidence가 다시 그 Rule을 검증합니다.

이 Closed Loop가 HG90의 전제가 됩니다.

여기까지가 `Top-down과 Bottom-up을 닫는다`의 역할입니다. 이제 이 구조를 더 내려가 **Gate로 끝낸다**에서 다음 경계와 실행책임을 보겠습니다.

---

# 8. Gate로 끝낸다

## FIG-03-10. Gate로 끝낸다

```text
G00 Source
 ↓
G20 Model
 ↓
G40 Test
 ↓
G50 Evidence
 ↓
G70 GAP/ADR
 ↓
HG90
```

6단계 그림을 완성했다고 끝나는 것이 아닙니다.

Source/Config Conformance, Runtime Test, GAP/ADR가 닫혀야 공식 Baseline으로 Release할 수 있습니다.

따라서 방법론의 마지막 단계는 항상 Evidence와 Gate입니다.

---

# 정상패턴과 금지패턴

## FIG-03-11. Normal Pattern

```text
Vision→BigPicture→Logical→Physical→Mechanism→Runtime→Evidence
```

정상패턴은 각 영역이 자신의 책임을 유지하면서 명확한 Contract와 Runtime Boundary를 통해 연결되는 구조입니다. 변경·장애·보안·운영 책임이 이 경계를 따라 추적될 수 있어야 합니다.

## FIG-03-12. Forbidden Pattern

```text
제품→서버→사후합리화
```

금지패턴은 기술적으로 불가능해서가 아니라 Architecture의 책임과 Evidence Chain을 무너뜨리기 때문에 제한합니다. 예외가 필요하면 묵시적으로 허용하지 않고 ADR와 Test Evidence로 승인합니다.

---

# Architecture Decision

## FIG-03-13. 주안과 대안

```text
[주안]
6단계 Top-down + Bottom-up Evidence

        VS

[대안]
제품/Physical 선결정
```

이번 장의 주안은 **6단계 Top-down + Bottom-up Evidence**입니다. 이 방향은 현재 확인된 PDMG 구조와 NSIGHT Target을 연결하면서 책임·운영·Evidence를 가장 일관되게 유지할 수 있는 선택입니다.

대안인 **제품/Physical 선결정**도 특정 조건에서는 사용할 수 있습니다. 다만 대안을 선택하려면 주안보다 나은 성능·가용성·비용 또는 운영효과가 PoC/Runtime Test로 확인되어야 하고, 그 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---

# Current GAP / PASS

## FIG-03-14. Current GAP

```text
Current
│
├─ automated trace
├─ model SSOT
├─ runtime gate automation
└─ HG90 process
```

- `[GAP/OPEN]` automated trace
- `[GAP/OPEN]` model SSOT
- `[GAP/OPEN]` runtime gate automation
- `[GAP/OPEN]` HG90 process

## FIG-03-15. Architecture Assessment

```text
Architecture Definition
 ↓
PASS

Current PDMG Conformance
 ↓
PARTIAL

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

## FIG-03-16. Evidence Chain

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

# Chapter Closing Script

## FIG-03-17. 다음 장 Handoff

```text
아키텍처 6단계 수립 방법론
 ↓
비전에서 시작해 실제 Runtime 검증까지 내려간다
 ↓
남은 질문
"화려한 박스가 아니라 책임과 경계를 먼저 본다"
 ↓
Big Picture
```

여기까지가 **아키텍처 6단계 수립 방법론**입니다. 이 장에서 중요한 것은 개별 기술을 많이 보여준 것이 아니라, 전체 그림을 시작점으로 책임과 Runtime을 하나씩 내려가며 설명했다는 점입니다.

이제 자연스럽게 다음 질문이 생깁니다. **화려한 박스가 아니라 책임과 경계를 먼저 본다**. 그 질문이 다음 단계인 **Big Picture**의 출발점입니다.
