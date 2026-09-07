# NSIGHT / PDMG 아키텍처 정의서

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 문서 성격: Architecture Definition Baseline Draft  
> 작성 기준: Evidence-First / Top-down / Text Architecture / PDMG Source Reference  
> 작성 시작일: 2026-08-31

---

## 1. 문서의 목적

이 문서는 NSIGHT의 목표 아키텍처를 **전략 → Big Picture → 논리 → 물리 → 메커니즘 → Runtime** 순서로 설명하고, PDMG를 실제 구현 Reference/AS-IS로 사용하여 설계가 코드·설정·실행 증거와 연결되는지를 검증하기 위한 아키텍처 정의서다.

```text
NSIGHT
= Target Architecture / Strategy / Baseline

PDMG
= Reference Implementation / AS-IS / Source Evidence
```

PDMG 구현을 NSIGHT 전체 표준으로 자동 승격하지 않으며, NSIGHT 목표 구조가 PDMG에 이미 구현되어 있다고도 단정하지 않는다.

---

## 2. Evidence 상태 표기

| 태그 | 의미 |
|---|---|
| `[FACT]` | 공식 자료·Source·Config·Runtime에서 직접 확인 |
| `[CONFIRMED]` | 복수 근거가 일치 |
| `[BASELINE-YYYY-MM-DD]` | 특정 시점 기준 수치/설계 |
| `[AS-IS]` | 현재 PDMG 또는 현행 구현 |
| `[TO-BE]` | 승인된 목표 구조 |
| `[PROPOSED]` | 제안 또는 설계전략 문서에 명시되었으나 승인상태 미확인 |
| `[DECISION]` | 승인된 의사결정 |
| `[GAP]` | 현재와 목표의 차이 또는 근거 누락 |
| `[CONFLICT]` | 복수 자료의 값·정책이 충돌 |
| `[RISK]` | 장애·성능·보안·운영 위험 |
| `[OPEN]` | 결정 필요 |
| `[UNKNOWN]` | 현재 자료로 확인 불가 |
| `[DEPRECATED]` | 폐기된 과거 기준 |

---

## 3. 전체 목차

| 장 | 제목 | 핵심 질문 | 상태 |
|---|---|---|---|
| I | NSIGHT Architecture Vision & Strategy | 왜 바꾸며 어떤 원칙으로 갈 것인가 | 작성 시작 |
| II | NSIGHT Big Picture & System Boundary | 책임·경계와 전체 시스템 공간은 어떻게 나뉘는가 | 예정 |
| III | PDMG Module / Application Architecture | PDMG 5개 모듈과 업무/Framework 경계는 무엇인가 | 예정 |
| IV | PDMG Online Runtime & TCF Flow | HTTP 요청 1건은 실제로 어떻게 실행되는가 | 예정 |
| V | Transaction / Timeout / Thread / DB Architecture | Thread·Timeout·DB TX 경계는 어떻게 동작하는가 | 예정 |
| VI | Standard Message / Context / Error / Logging | 전문·Context·오류·로그는 어떻게 추적되는가 | 예정 |
| VII | Security / SSO / JWT / Session | 인증·토큰·인가·세션 경계는 무엇인가 | 예정 |
| VIII | Infrastructure / WAS / Capacity / HA / DR | 논리 구조가 물리 자원에 어떻게 배치되는가 | 예정 |
| IX | DevOps / OM / Observability | 변경·배포·운영·관측은 어떻게 닫히는가 | 예정 |
| X | Naming / ServiceId / Traceability / Closed Loop | 요구→코드→Runtime→Baseline 추적을 어떻게 보장하는가 | 예정 |

---

## 4. Top-down 작성 원칙

```text
L0  Business / Architecture Context
   ↓
L1  Domain / System / Responsibility
   ↓
L2  Application / Module / Node / Contract
   ↓
L3  Runtime / Sequence / Data Flow
   ↓
L4  Failure / Security / Operations / HA-DR
   ↓
L5  GAP / ADR / Verification / Runtime Evidence
```

- 구조를 먼저 설명하고 클래스명을 나중에 설명한다.
- 표가 그림을 대체하지 않는다.
- 자료가 없으면 박스를 없애지 말고 `[UNKNOWN]` / `[OPEN]`으로 남긴다.
- 숫자는 반드시 시점과 근거를 붙인다.
- AS-IS와 TO-BE는 같은 상자 안에서 섞지 않는다.

---

## 5. 문서의 중심 Closed Loop

```text
Requirement
   ↓
Architecture Principle
   ↓
Decision / ADR
   ↓
Logical / Physical Architecture
   ↓
Source / Config
   ↓
Test
   ↓
Deployment
   ↓
Runtime Evidence
   ↓
Drift / GAP
   ↓
New Baseline
   └────────────────────────────↺
```

최종 목표는 문서를 많이 만드는 것이 아니라 다음 상태를 만드는 것이다.

```text
NSIGHT Architecture
= 설명 가능
+ 추적 가능
+ 구현 가능
+ 테스트 가능
+ 운영 가능
+ 자동 검증 가능
+ Drift 탐지 가능
```
