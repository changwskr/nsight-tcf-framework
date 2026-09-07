# NSIGHT PDMG 아키텍처 정의서
# 제3장. 아키텍처 6단계 수립 방법론
## Story: “비전에서 시작해 실제 Runtime 검증까지 내려간다”


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

이 여섯 단계는 목차가 아니라 의사결정 순서입니다. 상위 단계에서 방향과 경계를 정하지 않은 채 Physical이나 제품부터 고르면 뒤에서 설계를 합리화하게 됩니다. 그래서 VISION에서 시작해 RUNTIME까지 내려간 후, 마지막에는 Source와 Runtime Evidence로 다시 위의 의도를 검증합니다.


---

# 1. VISION — 방향을 고정한다

## FIG-03-02. VISION — 방향을 고정한다

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


---

# 2. BIG PICTURE — 책임과 경계를 고정한다

## FIG-03-03. BIG PICTURE — 책임과 경계를 고정한다

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


---

# 3. LOGICAL — 기술 역할을 정의한다

## FIG-03-04. LOGICAL — 기술 역할을 정의한다

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


---

# 4. PHYSICAL — 실제 자원으로 내린다

## FIG-03-05. PHYSICAL — 실제 자원으로 내린다

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


---

# 5. MECHANISM — 실행규칙을 정한다

## FIG-03-06. MECHANISM — 실행규칙을 정한다

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


---

# 6. RUNTIME — 시간축으로 검증한다

## FIG-03-07. RUNTIME — 시간축으로 검증한다

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


---

# 7. Top-down과 Bottom-up을 닫는다

## FIG-03-08. Top-down과 Bottom-up을 닫는다

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


---

# 8. Gate로 끝낸다

## FIG-03-09. Gate로 끝낸다

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

## FIG-03-10. Normal Pattern

```text
Vision→BigPicture→Logical→Physical→Mechanism→Runtime→Evidence
```

정상패턴은 상위 의도가 하위 설계를 구속하고, 하위 Source/Runtime이 다시 상위 의도를 검증하는 양방향 구조입니다.

## FIG-03-11. Forbidden Pattern

```text
제품→서버→사후합리화
```

제품이나 서버를 먼저 정한 뒤 Vision과 Logical Architecture를 사후에 맞추는 방식은 이 방법론의 역순이므로 기본패턴으로 사용하지 않습니다.

---

# Architecture Decision

## FIG-03-12. 주안과 대안

```text
[주안]
6단계 Top-down + Bottom-up Evidence

        VS

[대안]
제품/Physical 선결정
```

6단계 Top-down과 Bottom-up Evidence를 하나의 방법론으로 채택합니다. 상위 Intent와 실제 Source가 서로 검증되지 않으면 문서와 시스템은 다시 분리되기 때문입니다.

제품/Physical 선결정 방식은 단기간 실행은 빠르지만 책임·경계·Failure Domain을 사후에 맞춰야 하므로 Architecture Decision의 추적성이 약해집니다.

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
아키텍처 6단계 수립 방법론 Architecture Rule
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

- `[SOURCE]` Architecture 00~18 정의서 및 Gate 체계
- `[SOURCE]` PDMG Source/Runtime 상세분석

### Config Evidence

- `[CONFIG]` Architecture Rule / Manifest / Conformance 구조

### Runtime / Deployment Evidence

- `[RUNTIME]` Top-down 설계와 Bottom-up Evidence 연결

### Architecture Decision

- `[DECISION]` VISION→BIG PICTURE→LOGICAL→PHYSICAL→MECHANISM→RUNTIME→EVIDENCE

### Related ADR

- `ADR-001 Baseline`
- `ADR-040 Runtime Evidence`
- `HG90 Gate`

### Current GAP / OPEN

- `[GAP/OPEN]` Architecture Model SSOT
- `[GAP/OPEN]` G50 Runtime Evidence 자동화
- `[GAP/OPEN]` HG90 자동 Gate

## 이 장의 판정

```text
Architecture Definition
        ↓
PASS

Current PDMG Conformance
        ↓
PARTIAL

Runtime Evidence Coverage
        ↓
MEDIUM

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `Architecture Model SSOT`
- `G50 Runtime Evidence 자동화`
- `HG90 Gate 운영`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-03-16. 다음 장 Handoff

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

방법론이 정해졌으므로 이제 실제 설계가 시작됩니다. 다음 장 Big Picture에서는 세부기술을 잠시 내려놓고 사용자·인증·업무·데이터·외부연계의 책임과 경계를 한 장으로 고정합니다.
