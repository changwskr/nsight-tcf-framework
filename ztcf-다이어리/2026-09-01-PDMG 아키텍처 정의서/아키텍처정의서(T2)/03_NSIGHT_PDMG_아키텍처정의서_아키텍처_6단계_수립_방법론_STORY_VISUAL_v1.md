# NSIGHT PDMG 아키텍처 정의서
# 제3장. 아키텍처 6단계 수립 방법론
## VISION → BIG PICTURE → LOGICAL → PHYSICAL → MECHANISM → RUNTIME
## Story-First / TEXT Architecture-First / Top-down → Drill-down / Evidence-First

> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 상위 목차: **13장 발표스크립트 Story 구조**  
> 본 장의 역할: **2장에서 정립한 새 관점을 순서 있는 설계 방법론으로 변환한다.**  
> 원칙: **TEXT Architecture가 Story를 먼저 만들고, 설명은 그림의 의미를 해석한다.**

---

# 0. 이 장의 이야기

```text
제2장에서 넘어온 질문
     ↓
2장에서 정립한 새 관점을 순서 있는 설계 방법론으로 변환한다.
     ↓
이 장이 답해야 할 질문
     ↓
VISION → BIG PICTURE → LOGICAL → PHYSICAL → MECHANISM → RUNTIME
```

이 장의 핵심 원칙은 다음과 같다.

- 6단계는 문서 분류가 아니라 Drill-down 순서다.
- 각 단계의 산출물은 다음 단계의 입력이 된다.
- Top-down 설계와 Bottom-up Evidence가 마지막에 만나야 한다.
- 제품/서버를 먼저 결정하고 Architecture를 사후 설명하지 않는다.

---

# 1. 방법론이 필요한 이유

## FIG-03-01. 방법론이 필요한 이유

```text
Architecture 요소가 많다
Application
Logical
Physical
Security
Data
Runtime
Operations
     ↓
한 번에 그리면
경계가 섞인다
     ↓
단계가 필요하다
```

---

# 2. 6단계 전체 Journey

## FIG-03-02. 6단계 전체 Journey

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
EVIDENCE
```

---

# 3. VISION — 무엇을 지향하는가

## FIG-03-03. VISION — 무엇을 지향하는가

```text
VISION

Scalable
+
Resilient
+
Data-Centric
     ↓
Architecture 판단기준
```

---

# 4. BIG PICTURE — 누가 무엇을 책임지는가

## FIG-03-04. BIG PICTURE — 누가 무엇을 책임지는가

```text
User / Channel
 ↓
Application Boundary
 ↓
Data / Integration Boundary
 ↓
Operations

책임은 공간에 고정
연결은 경계에서 통제
```

---

# 5. LOGICAL — 제품보다 기술 역할을 먼저 정한다

## FIG-03-05. LOGICAL — 제품보다 기술 역할을 먼저 정한다

```text
Application
 ↓
Technical Capability
 ↓
Logical Node
 ↓
State / Scale / Failure / Security
```

---

# 6. PHYSICAL — Logical Node를 실제 자원으로 내린다

## FIG-03-06. PHYSICAL — Logical Node를 실제 자원으로 내린다

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
Network / DB / Storage
```

---

# 7. MECHANISM — 실행규칙을 정의한다

## FIG-03-07. MECHANISM — 실행규칙을 정의한다

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

---

# 8. RUNTIME — 거래 한 건을 시간축으로 본다

## FIG-03-08. RUNTIME — 거래 한 건을 시간축으로 본다

```text
HTTP
 ↓
Request Thread
 ↓ submit
Worker Thread
 ↓
TX
 ↓
Business
 ↓
DB
 ↓
Response / Evidence
```

---

# 9. 각 단계는 다음 단계의 입력이다

## FIG-03-09. 각 단계는 다음 단계의 입력이다

```text
VISION
  "Scalable"
     ↓
LOGICAL
  Scale Unit
     ↓
PHYSICAL
  N+1 Node
     ↓
RUNTIME
  Failure Test
     ↓
EVIDENCE
  PASS
```

---

# 10. Top-down과 Bottom-up을 닫는다

## FIG-03-10. Top-down과 Bottom-up을 닫는다

```text
Top-down
Architecture Intent
      ↓
Design
      ↓
Implementation

Bottom-up
Source / Config
      ↑
Runtime Evidence
      ↑
Actual Behavior
```

---

# 11. 6단계가 끝나도 최종 단계는 Evidence다

## FIG-03-11. 6단계가 끝나도 최종 단계는 Evidence다

```text
그림 완성
   ≠
Architecture 완료

Architecture
 ↓
Source / Config
 ↓
Test
 ↓
Runtime Evidence
 ↓
PASS
```

---

# 12. 정상패턴 — 이 장에서 지켜야 할 구조

## FIG-03-12. Normal Pattern

```text
VISION → BIG PICTURE → LOGICAL
→ PHYSICAL → MECHANISM → RUNTIME
→ EVIDENCE / PASS
```

정상패턴의 핵심은 **책임·경계·실행·증적이 한 방향으로 이어지는 것**이다.

---

# 13. 금지패턴 — 구조를 다시 흐리게 만드는 방식

## FIG-03-13. Forbidden Pattern

```text
제품선정 → 서버배치 → 사후 Architecture 설명   X
Logical 없이 Physical 확정                    X
Runtime Evidence 없이 최종 PASS               X
```

금지패턴은 구현이 가능하더라도 Architecture Boundary와 Runtime Evidence를 무너뜨리는 경우를 의미한다.

---

# 14. Architecture Decision — Architecture 수립순서

## FIG-03-14. 주안과 대안

```text
[주안]
6단계 Top-down + Evidence

        VS

[대안]
제품/Physical 선결정
```

| 구분 | 주안 | 대안 |
|---|---|---|
| 안 | 6단계 Top-down + Evidence | 제품/Physical 선결정 |
| 장점 | • 책임→기술→배치 논리 유지<br>• Target/Current 분리<br>• 변경영향 추적 | • 빠르게 구체화 가능 |
| 단점 | • 초기 정의시간 필요<br>• 모델 관리 필요 | • 제품 종속<br>• 사후합리화<br>• 경계 혼재 |
| 권고 | **주안 채택** | 대안은 별도 ADR와 Evidence가 있을 때 채택 |

---

# 15. Current PDMG GAP

## FIG-03-15. GAP Map

```text
Current PDMG
│
├─ 6단계 산출물 간 자동 Trace 미완료
├─ Architecture Model SSOT 자동화 미완료
└─ Runtime Gate 자동화 미완료
```

- `[GAP/OPEN]` 6단계 산출물 간 자동 Trace 미완료
- `[GAP/OPEN]` Architecture Model SSOT 자동화 미완료
- `[GAP/OPEN]` Runtime Gate 자동화 미완료

---

# 16. 제3장 Architecture 판정

## FIG-03-16. Assessment

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
```

| 평가축 | 판정 | 설명 |
|---|---|---|
| 6단계 정의 | PASS | Story/산출물 구조 명확 |
| 단계간 Handoff | PASS | Logical→Physical→Runtime 연결 |
| Bottom-up Evidence | PARTIAL | 자동수집 미완료 |
| Gate | PARTIAL | HG90 자동화 OPEN |

---

# 17. 원천 정의서 Trace

## FIG-03-17. Source Trace

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
| 00 Master Index | Story/Drill-down/Current-Target 근거 |
| 01 Executive | Story/Drill-down/Current-Target 근거 |
| 04 Logical | Story/Drill-down/Current-Target 근거 |
| 05 Physical | Story/Drill-down/Current-Target 근거 |
| 08 Mechanism | Story/Drill-down/Current-Target 근거 |
| 09 Runtime | Story/Drill-down/Current-Target 근거 |
| 17 Traceability | Story/Drill-down/Current-Target 근거 |
| 18 Integrated | Story/Drill-down/Current-Target 근거 |

---

# 18. 다음 장으로 넘어가는 이유

## FIG-03-18. 3장 → 4

```text
제3장
아키텍처 6단계 수립 방법론
      ↓
"이제 전체 시스템에서 누가 무엇을 책임지는지 한눈에 어떻게 볼 것인가?"
      ↓
제4장
Big Picture
```

---

# 19. 제3장 최종 결론

## FIG-03-19. Final Story

```text
아키텍처 6단계 수립 방법론
   ↓
Responsibility
   ↓
Boundary
   ↓
Runtime
   ↓
Evidence
   ↓
PASS
```

제3장의 결론은 **VISION → BIG PICTURE → LOGICAL → PHYSICAL → MECHANISM → RUNTIME**라는 한 문장으로 정리된다.
