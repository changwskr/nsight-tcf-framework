# NSIGHT PDMG 아키텍처 정의서 — FINAL INTEGRATED
## 발표스크립트 Story + TEXT Architecture + Top-down → Drill-down

> 상태: `[WORKING INTEGRATED BASELINE-2026-09-01]`

```text
왜 다시 짓는가
 ↓
패러다임 전환
 ↓
6단계 방법론
 ↓
Big Picture
 ↓
Logical
 ↓
Physical
 ↓
DR
 ↓
Mechanism
 ↓
Runtime
 ↓
Data Platform
 ↓
Marketing Platform
 ↓
BI Portal
 ↓
Standardization / Evidence
 ↓
HG90
```

---

<!-- SOURCE CHAPTER: 01_왜_다시_짓는가.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제1장. 왜 다시 짓는가
## Story: “이미 시스템은 있지만, Architecture는 하나로 보이지 않는다”
## 발표스크립트 Story + TEXT Architecture + Top-down → Drill-down

> 작성 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_마스터프롬프트_v1.md`  
> 목차 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_상세목차_v1.md`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 주인공: **PDMG Current Architecture** / 상위 정합: **NSIGHT Target Reference**

---

# 0. 이 장의 Story

## FIG-01-00. Story Opening

```text
이전 장의 질문
 ↓
이미 시스템은 있지만, Architecture는 하나로 보이지 않는다
 ↓
전체 그림
 ↓
Top-down 해부
 ↓
Runtime / Failure / Security
 ↓
Evidence / PASS / GAP
```

# 1. 장 전체 대표 Architecture

## FIG-01-01. 왜 다시 짓는가 — Whole Architecture

```text
기존
Module / Source / Server / Data
각각 존재
        ↓
Architecture 연결 부족
        ↓
Runtime / Failure / Evidence 불명확

             ↓ 재정의

Business
 ↓
Application
 ↓
Logical
 ↓
Physical
 ↓
Mechanism
 ↓
Runtime
 ↓
Evidence
```

이 장의 핵심은 **이미 시스템은 있지만, Architecture는 하나로 보이지 않는다**라는 메시지를 하나의 Architecture 그림으로 먼저 이해하는 것입니다.

기존 문서에서는 왜 다시 짓는가과 관련된 기술요소가 개별 표나 구성요소로 나뉘어 보이는 경우가 많았습니다. 그러나 요소가 존재한다는 것만으로는 책임, 경계, 장애영향, 실행순서를 설명할 수 없습니다.

그래서 이번 정의서에서는 먼저 전체 그림을 보여주고, 독자가 그 그림의 방향과 경계를 이해한 뒤 L0에서 L5까지 내려가도록 구성합니다. 위에서 아래로 내려가는 이유는 세부기술이 상위 Architecture Intent를 잃지 않게 하기 위해서입니다.

여기서 중요한 원칙은 **PDMG Current와 NSIGHT Target을 분리하는 것**입니다. Source·Config·Runtime으로 확인되는 구조는 Current로 설명하고, 향후 목표나 전략은 Target 또는 Reference로 별도 표시합니다.

이것은 단순히 문서의 표현방식을 바꾸는 문제가 아닙니다. 같은 구성요소라도 Current인지 Target인지에 따라 Architecture Decision, 구현 책임, 테스트 기준이 달라지기 때문입니다.

운영 관점에서는 이 구분이 더 중요합니다. 실제 장애가 발생했을 때 어느 Component, Thread, JVM, DataSource, Interface가 영향을 받는지 추적하려면 책임과 Runtime이 연결되어 있어야 합니다.

반대로 왜 다시 짓는가을 제품명이나 박스 목록만으로 설명하면 그림은 단순해 보이지만 실제 변경과 장애를 설명하기 어렵습니다. 따라서 이 장에서는 정상경로뿐 아니라 금지패턴, Failure, Security, Evidence까지 함께 다룹니다.

이제 전체 그림을 기준으로 하나씩 해부해 보겠습니다. 먼저 L0에서는 이 장의 메시지를 고정하고, L1~L3에서는 책임과 Component를 나누며, L4에서는 실제 Runtime과 Failure를 보고, 마지막 L5에서는 Source·Config·Deployment·Runtime Evidence로 다시 확인합니다.

## FIG-01-02. L0 → L5 Drill-down Route

```text
L0 STORY / LANDSCAPE
"이미 시스템은 있지만, Architecture는 하나로 보이지 않는다"
 ↓
L1 RESPONSIBILITY / BOUNDARY
 ↓
L2 APPLICATION / LOGICAL / PLATFORM
 ↓
L3 COMPONENT / CONTRACT
 ↓
L4 RUNTIME / FAILURE / SECURITY
 ↓
L5 SOURCE / CONFIG / DEPLOYMENT / EVIDENCE
```

이제부터 이 대표 그림을 위에서 아래로 해부합니다. 상위 그림에서 보이는 연결을 바로 제품이나 서버로 해석하지 않고, 먼저 책임과 경계를 확인한 다음 Component와 Runtime으로 내려갑니다.

---

# 1.1 장 전체 대표 그림 — 왜 Architecture를 다시 정의하는가

## FIG-01-03. 장 전체 대표 그림 — 왜 Architecture를 다시 정의하는가

```text
┌──────────────────────────── CHANNEL / USER ────────────────────────────┐
│                                                                       │
│                        User / Browser                                 │
│                                                                       │
└──────────────────────────────┬────────────────────────────────────────┘
                               │ HTTP
             ┌─────────────────┼─────────────────┐
             │                 │                 │
             ▼                 ▼                 ▼
┌──────────────────┐ ┌──────────────────┐ ┌────────────────────────────┐
│ pdmg-ui          │ │ pdmg-jwt         │ │ pdmg-service               │
│ [AS-IS]          │ │ [AS-IS]          │ │ [AS-IS]                   │
│                  │ │                  │ │                            │
│ UI / Static      │ │ Login / SSO      │ │ Online Business Runtime    │
│ Request Build    │ │ Access / Refresh │ │                            │
│ ServiceId Call   │ │ JWKS             │ │ ┌────────────────────────┐ │
└────────┬─────────┘ └────────┬─────────┘ │ │ pdmg-fw [AS-IS]       │ │
         │ HTTP               │ Token     │ │ Framework Mechanism    │ │
         └──────────────┬─────┘           │ │ Filter / Context / TCF │ │
                        │                 │ │ Timeout / Error / Log  │ │
                        ▼                 │ └───────────┬────────────┘ │
                     Bearer               │             ▼              │
                                          │ Handler                    │
                                          │    ↓                       │
                                          │ Facade                     │
                                          │    ↓                       │
                                          │ Service                    │
                                          │    ↓                       │
                                          │ DAO                        │
                                          │    ↓                       │
                                          │ Mapper / SQL               │
                                          └───────────┬────────────────┘
                                                      │ MyBatis/JDBC
                                                      ▼
                                          ┌────────────────────────────┐
                                          │ RDW / DB                   │
                                          │ [DATA BOUNDARY]            │
                                          └────────────────────────────┘


                                          ┌────────────────────────────┐
                                          │ pdmg-om                    │
                                          │ [CURRENT DETAIL UNKNOWN]   │
                                          └────────────────────────────┘
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 장 전체 대표 그림 — 왜 Architecture를 다시 정의하는가을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“기존 PDMG는 어떻게 보이는가에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 1.2 기존 PDMG는 어떻게 보이는가

## FIG-01-04. 기존 PDMG는 어떻게 보이는가

```text
PDMG는 무엇인가?
   ↓
어떤 Process / Module / JVM / Spring Context를 가지는가?
   ↓
Framework와 Business는 어디에서 나뉘는가?
   ↓
ServiceId는 어떻게 Business Component를 선택하는가?
   ↓
거래 한 건은 어떤 Thread / Transaction에서 실행되는가?
   ↓
DB / Security / Message / Error / Logging은 어디에 연결되는가?
   ↓
실제 구현은 Architecture와 얼마나 일치하는가?
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 기존 PDMG는 어떻게 보이는가을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Inventory와 Architecture의 차이에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 1.3 Inventory와 Architecture의 차이

## FIG-01-05. Inventory와 Architecture의 차이

```text
Inventory
─────────────────────────────
pdmg-ui
pdmg-jwt
pdmg-fw
pdmg-service
Oracle
Tomcat
MyBatis


Architecture
─────────────────────────────
User
 ↓
UI
 ↓
Authentication
 ↓
Application Runtime
 ↓
Framework Control
 ↓
Business Execution
 ↓
Data Access
 ↓
DB
 ↓
Response / Evidence
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Inventory와 Architecture의 차이을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `01_NSIGHT_PDMG_아키텍처정의서_왜_다시_짓는가_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Module → Responsibility에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 1.4 Module → Responsibility

## FIG-01-06. Module → Responsibility

```text
Build Module
      ↓
Responsibility
      ↓
Runtime Relationship
      ↓
Architecture
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Module → Responsibility을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `01_NSIGHT_PDMG_아키텍처정의서_왜_다시_짓는가_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Source Structure → Runtime Structure에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 1.5 Source Structure → Runtime Structure

## FIG-01-07. Source Structure → Runtime Structure

```text
Git Source
   ↓
Gradle Multi-project
   ↓
Compile / Test
   ↓
WAR / Artifact
   ↓
Artifact Hash
   ↓
Deployment
   ↓
DeploymentId
   ↓
Host / JVM / WAR
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Source Structure → Runtime Structure을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Request Thread / Worker Thread에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 1.6 Request Thread / Worker Thread

## FIG-01-08. Request Thread / Worker Thread

```text
════════════ Request Thread ════════════

HTTP
 ↓
Filter
 ↓
Security
 ↓
MVC
 ↓
Controller
 ↓
TcfFacade
 ↓
Future.get(timeout)
        │
        │ submit
        ▼

════════════ Worker Thread ═════════════

Context Install
 ↓
TransactionTemplate BEGIN
 ↓
Dispatcher
 ↓
Handler
 ↓
Facade
 ↓
Service
 ↓
DAO
 ↓
DB
 ↓
Deadline
 ↓
COMMIT / ROLLBACK
```

이 그림의 핵심은 **Thread·Transaction·Deadline 경계**입니다. Request Thread / Worker Thread을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Request Thread의 HTTP 수명과 Worker/DB Transaction의 수명은 동일하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. HTTP 504, Future cancel, JDBC cancel, DB rollback을 동일한 사건으로 해석하지 않습니다.

Current Evidence 관점에서는 `01_NSIGHT_PDMG_아키텍처정의서_왜_다시_짓는가_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Timeout / Transaction 경계에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 1.7 Timeout / Transaction 경계

## FIG-01-09. Timeout / Transaction 경계

```text
Request Thread
 ↓ submit
Worker Pool
 ↓
TransactionTemplate BEGIN
 ↓
Handler / Facade / Service
 ↓
DAO / Hikari / JDBC
 ↓
DB
 ↓
Deadline
 ├─ commit
 └─ rollback
```

이 그림의 핵심은 **Thread·Transaction·Deadline 경계**입니다. Timeout / Transaction 경계을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Request Thread의 HTTP 수명과 Worker/DB Transaction의 수명은 동일하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. HTTP 504, Future cancel, JDBC cancel, DB rollback을 동일한 사건으로 해석하지 않습니다.

Current Evidence 관점에서는 `10_PDMG_TRANSACTION_TIMEOUT_THREAD_DB_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Authentication / Authorization / JWT GAP에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 1.8 Authentication / Authorization / JWT GAP

## FIG-01-10. Authentication / Authorization / JWT GAP

```text
pdmg-jwt
   │
   │ RS256 Issue
   ▼
JWT
   │
   ▼
pdmg-fw
   │
   │ HMAC Secret Verify Path
   ▼
Business Runtime

        [CRITICAL GAP]
```

이 그림의 핵심은 **Trust Boundary와 Identity 통제**입니다. Authentication / Authorization / JWT GAP을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Security는 Business Runtime 앞에서 통과해야 하는 Architecture Boundary입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. JWT Issuer/Verifier 정합성과 Principal→Business User Binding은 Critical GAP로 유지합니다.

Current Evidence 관점에서는 `01_NSIGHT_PDMG_아키텍처정의서_왜_다시_짓는가_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“ServiceId Backbone에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 1.9 ServiceId Backbone

## FIG-01-11. ServiceId Backbone

```text
Business Classification
MG / CO / A
       ↓
Program ID
mgcoa9001
       ↓
ServiceId
mgcoa9001S0
       ↓
Handler Registry
       ↓
TransactionHandler
       ↓
Facade
       ↓
Service
       ↓
DAO
       ↓
Mapper / SqlId
       ↓
SQL / Table
```

이 그림의 핵심은 **식별과 Traceability의 기준축**입니다. ServiceId Backbone을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. ServiceId/Program/Mapper 식별은 Source와 Runtime Evidence, 변경영향을 연결합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. 서로 다른 의미의 ID를 하나로 합치거나 자동 정규화하지 않습니다.

Current Evidence 관점에서는 `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Application → Logical → Physical에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 1.10 Application → Logical → Physical

## FIG-01-12. Application → Logical → Physical

```text
Business / Application Responsibility
                ↓
        Technical Capability
                ↓
      Logical Technical Node
                ↓
         Runtime Type
                ↓
       Physical Resource
                ↓
        Runtime Evidence
```

이 그림의 핵심은 **책임을 기술 역할과 장애경계로 변환하는 단계**입니다. Application → Logical → Physical을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Logical Architecture는 제품을 고르는 단계가 아니라 책임·Scale·Failure를 결정하는 단계입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Application, Build Module, Logical Node, Physical Host를 같은 것으로 취급하지 않습니다.

Current Evidence 관점에서는 `01_NSIGHT_PDMG_아키텍처정의서_왜_다시_짓는가_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Capacity / Failure Chain에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 1.11 Capacity / Failure Chain

## FIG-01-13. Capacity / Failure Chain

```text
Authentication Down
  ↓
New Login/Token Issue Failure
  ↓
Business Entry Impact
```

이 그림의 핵심은 **운영과 증적을 Architecture에 연결하는 단계**입니다. Capacity / Failure Chain을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 로그가 존재하는 것과 Architecture Rule의 Runtime 준수 증적은 다릅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. SourceCommit→ArtifactHash→DeploymentId→ServiceId/GUID를 연결합니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“PDMG Current ↔ NSIGHT Target에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 1.12 PDMG Current ↔ NSIGHT Target

## FIG-01-14. PDMG Current ↔ NSIGHT Target

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

이 그림의 핵심은 **현재 구현을 있는 그대로 확인하는 영역**입니다. PDMG Current ↔ NSIGHT Target을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 목표 구조를 섞지 않고 Source·Config·Runtime에서 확인되는 사실만 Current로 둡니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. 확인되지 않은 항목은 `[OPEN]` 또는 `[UNKNOWN]`으로 남겨 두는 것이 정확한 Architecture 관리입니다.

Current Evidence 관점에서는 `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Interface Contract에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 1.13 Interface Contract

## FIG-01-15. Interface Contract

```text
Business Interaction Need
        ↓
Interface Type
        ↓
Source / Target
        ↓
Contract
        ↓
Protocol / Schema
        ↓
Timeout / Retry
        ↓
Security
        ↓
Operations / Evidence
```

이 그림의 핵심은 **경계 간 연결을 Contract로 통제하는 단계**입니다. Interface Contract을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Source/Target, Contract, Security, Timeout, Retry, Operations까지 함께 정의해야 합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. P2P Direct DB 연결은 예외 ADR 없이 정상경로로 허용하지 않습니다.

Current Evidence 관점에서는 `01_NSIGHT_PDMG_아키텍처정의서_왜_다시_짓는가_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Operations / Runtime Evidence에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 1.14 Operations / Runtime Evidence

## FIG-01-16. Operations / Runtime Evidence

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

이 그림의 핵심은 **운영과 증적을 Architecture에 연결하는 단계**입니다. Operations / Runtime Evidence을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 로그가 존재하는 것과 Architecture Rule의 Runtime 준수 증적은 다릅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. SourceCommit→ArtifactHash→DeploymentId→ServiceId/GUID를 연결합니다.

Current Evidence 관점에서는 `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Architecture Closed Loop에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 1.15 Architecture Closed Loop

## FIG-01-17. Architecture Closed Loop

```text
Document
   ↓
Model
   ↓
Code
   ↓
Test
   ↓
Runtime Evidence
   ↓
Drift
   ↓
GAP
   ↓
ADR
   ↓
Architecture Update
   ↓
New Baseline
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Architecture Closed Loop을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `01_NSIGHT_PDMG_아키텍처정의서_왜_다시_짓는가_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“정상 / 금지패턴에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 1.16 정상 / 금지패턴

## FIG-01-18. 정상 / 금지패턴

```text
정상

System A
   ↓
Approved API / Data Contract
   ↓
System B
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 정상 / 금지패턴을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `01_NSIGHT_PDMG_아키텍처정의서_왜_다시_짓는가_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“주안 / 대안에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 1.17 주안 / 대안

## FIG-01-19. 주안 / 대안

```text
[주안]

Evidence-backed Architecture Baseline

Architecture
 ↓
Source / Config
 ↓
Deployment
 ↓
Runtime Evidence
 ↓
PASS / GAP
 ↓
ADR / Baseline


[대안]

Document-centered Baseline

Architecture Document
 ↓
Review
 ↓
Approval
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 주안 / 대안을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `01_NSIGHT_PDMG_아키텍처정의서_왜_다시_짓는가_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Current GAP에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 1.18 Current GAP

## FIG-01-20. Current GAP

```text
pdmg-jwt
   │
   │ RS256 Issue
   ▼
JWT
   │
   ▼
pdmg-fw
   │
   │ HMAC Secret Verify Path
   ▼
Business Runtime

        [CRITICAL GAP]
```

이 그림의 핵심은 **현재 구현을 있는 그대로 확인하는 영역**입니다. Current GAP을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 목표 구조를 섞지 않고 Source·Config·Runtime에서 확인되는 사실만 Current로 둡니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. 확인되지 않은 항목은 `[OPEN]` 또는 `[UNKNOWN]`으로 남겨 두는 것이 정확한 Architecture 관리입니다.

Current Evidence 관점에서는 `01_NSIGHT_PDMG_아키텍처정의서_왜_다시_짓는가_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“PASS에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 1.19 PASS

## FIG-01-21. PASS

```text
Architecture Definition
        ↓
[CONDITIONAL PASS]

Current PDMG
        ↓
[PARTIAL / GAP]

이 두 값은 동일하지 않다.
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. PASS을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“제2장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 1.20 제2장 Handoff

## FIG-01-22. 제2장 Handoff

```text
Logical Node
   ↓
Environment
   ↓
Physical Node
   ↓
Software / Runtime
   ↓
Port / Datasource
   ↓
Artifact
   ↓
Monitoring
   ↓
Evidence
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. 제2장 Handoff을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“장 결론 / 다음 장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# Architecture Cross-check — Failure / Security / Evidence

## FIG-01-23. Failure Propagation

```text
왜 다시 짓는가 하위 Component Failure
 ↓
Dependency / Pool / Interface 영향
 ↓
Runtime 지연 또는 오류
 ↓
Upstream Service 영향
 ↓
Alert / Recovery / Evidence
```

이 그림에서 중요한 것은 장애가 한 Component에서 끝나지 않는다는 점입니다. Architecture는 정상경로뿐 아니라 어떻게 느려지고, 어떻게 실패하고, 어디에서 차단해야 하는지까지 설명해야 합니다.

## FIG-01-24. Security / Trust Boundary

```text
Untrusted / External
 ↓
Authentication / Validation
 ↓
Trusted Principal / Contract
 ↓
왜 다시 짓는가 Runtime
 ↓
Authorization / Data Access
 ↓
Audit / Evidence
```

Security 관점에서는 '접속이 가능하다'와 '신뢰된 주체가 허가된 업무를 수행한다'를 구분합니다. PDMG의 JWT Issuer/Verifier 및 Identity Binding GAP는 해당 장에서 Critical GAP로 유지합니다.

---

# 정상패턴 / 금지패턴

## FIG-01-25. Normal Pattern

```text
Current Fact
 ↓
Responsibility
 ↓
Boundary
 ↓
Runtime
 ↓
Evidence
 ↓
PASS / GAP
```

정상패턴에서는 각 영역이 자신의 책임을 수행하고 다음 영역과는 명확한 Contract 또는 Runtime Boundary를 통해 연결됩니다. 이렇게 해야 변경 영향과 장애영향을 제한할 수 있습니다.

## FIG-01-26. Forbidden Pattern

```text
문서에 있음 → Fact        X
Module → Server 자동변환   X
Target → Current 자동승격  X
Log 존재 → Evidence PASS  X
```

반대로 금지패턴은 구현 자체가 불가능해서가 아니라 Architecture Boundary를 무너뜨리고 Source·Runtime·운영증적의 정합성을 떨어뜨리기 때문에 금지합니다. 예외가 필요하면 ADR와 Evidence로 관리합니다.

---

# Architecture Decision — 주안 / 대안 / Trade-off

## FIG-01-27. Primary vs Alternative

```text
[주안]
Evidence-backed Architecture Baseline

        VS

[대안]
Document-centered Baseline
```

이번 장의 주안은 **Evidence-backed Architecture Baseline**입니다. 이 선택은 현재 PDMG Source/Runtime과 NSIGHT Target Alignment를 함께 고려했을 때 책임과 Evidence를 가장 일관되게 유지할 수 있는 방향입니다.

대안인 **Document-centered Baseline**도 기술적으로 가능한 경우가 있습니다. 다만 대안을 선택하려면 더 나은 가용성·성능·운영성 또는 비용효과가 Runtime Test로 입증되어야 하며, 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| Architecture 정합성 | 높음 — 책임/경계 중심 | 별도 보완설계 필요 |
| Current PDMG 정합 | 현재 Evidence와 우선 정렬 | 변경범위 증가 가능 |
| 운영/장애분석 | 경계와 Trace가 명확 | 구조에 따라 복잡도 증가 |
| Evidence 조건 | 기본 Gate로 검증 | 추가 PoC/Test/ADR 필요 |

---

# Current Evidence / PASS / GAP

## FIG-01-28. Current GAP Map

```text
Current / Reference
│
├─ JWT RS256 Issuer ↔ HMAC Verifier
├─ Trusted Principal ↔ Business User Binding
├─ Artifact/Deployment → Host/JVM/WAR
├─ Runtime Evidence 자동화
└─ pdmg-om Current Scope
```

- `[GAP/OPEN]` JWT RS256 Issuer ↔ HMAC Verifier
- `[GAP/OPEN]` Trusted Principal ↔ Business User Binding
- `[GAP/OPEN]` Artifact/Deployment → Host/JVM/WAR
- `[GAP/OPEN]` Runtime Evidence 자동화
- `[GAP/OPEN]` pdmg-om Current Scope

## FIG-01-29. Architecture Assessment

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

Architecture PASS
 ≠
Implementation PASS
```

여기서 반드시 구분해야 할 것은 Architecture Definition PASS와 Current Implementation PASS가 동일하지 않다는 점입니다. 구조와 원칙이 합리적으로 정의되어 있어도 Current Source·Deployment·Runtime이 이를 따르지 않으면 Current Conformance는 GAP 또는 PARTIAL일 수 있습니다.

## FIG-01-30. Evidence Traceability

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

최종적으로 이 장의 Architecture는 문서 안에서 끝나지 않습니다. Source와 Config, 배포 Artifact, Runtime의 ServiceId/GUID, Metric/Trace/Test를 연결해 실제로 설계가 지켜졌음을 증명해야 합니다.

---

# 이 장의 결론

## FIG-01-31. Chapter Conclusion

```text
왜 다시 짓는가
 ↓
전체 TEXT Architecture
 ↓
L0 → L5 Drill-down
 ↓
Runtime / Failure / Security
 ↓
Normal / Forbidden
 ↓
Decision / Evidence
 ↓
CONDITIONAL PASS
```

이 장에서 확인한 것은 **이미 시스템은 있지만, Architecture는 하나로 보이지 않는다**입니다. 중요한 것은 구성요소를 많이 나열한 것이 아니라, 전체 그림에서 시작해 책임과 경계를 나누고 Runtime과 Evidence까지 연결했다는 점입니다.

여기까지 정리하면 다음 질문이 자연스럽게 생깁니다.

> **“저장소와 Application 중심에서 살아 움직이는 Platform Architecture로”**

그 질문이 바로 다음 단계, **정보계 패러다임의 전환**의 주제입니다.


---

<!-- SOURCE CHAPTER: 02_정보계_패러다임의_전환.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제2장. 정보계 패러다임의 전환
## Story: “저장소와 Application 중심에서 살아 움직이는 Platform Architecture로”
## 발표스크립트 Story + TEXT Architecture + Top-down → Drill-down

> 작성 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_마스터프롬프트_v1.md`  
> 목차 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_상세목차_v1.md`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 주인공: **PDMG Current Architecture** / 상위 정합: **NSIGHT Target Reference**

---

# 0. 이 장의 Story

## FIG-02-00. Story Opening

```text
이전 장의 질문
 ↓
저장소와 Application 중심에서 살아 움직이는 Platform Architecture로
 ↓
전체 그림
 ↓
Top-down 해부
 ↓
Runtime / Failure / Security
 ↓
Evidence / PASS / GAP
```

# 1. 장 전체 대표 Architecture

## FIG-02-01. 정보계 패러다임의 전환 — Whole Architecture

```text
Application-Centric
User → Application → DB

          ↓

Platform / Runtime-Centric
User
 ↓
UI / Auth
 ↓
Application Runtime
 ↓
Framework Control
 ↓
Data
 ↓
Operations / Evidence
```

이 장의 핵심은 **저장소와 Application 중심에서 살아 움직이는 Platform Architecture로**라는 메시지를 하나의 Architecture 그림으로 먼저 이해하는 것입니다.

기존 문서에서는 정보계 패러다임의 전환과 관련된 기술요소가 개별 표나 구성요소로 나뉘어 보이는 경우가 많았습니다. 그러나 요소가 존재한다는 것만으로는 책임, 경계, 장애영향, 실행순서를 설명할 수 없습니다.

그래서 이번 정의서에서는 먼저 전체 그림을 보여주고, 독자가 그 그림의 방향과 경계를 이해한 뒤 L0에서 L5까지 내려가도록 구성합니다. 위에서 아래로 내려가는 이유는 세부기술이 상위 Architecture Intent를 잃지 않게 하기 위해서입니다.

여기서 중요한 원칙은 **PDMG Current와 NSIGHT Target을 분리하는 것**입니다. Source·Config·Runtime으로 확인되는 구조는 Current로 설명하고, 향후 목표나 전략은 Target 또는 Reference로 별도 표시합니다.

이것은 단순히 문서의 표현방식을 바꾸는 문제가 아닙니다. 같은 구성요소라도 Current인지 Target인지에 따라 Architecture Decision, 구현 책임, 테스트 기준이 달라지기 때문입니다.

운영 관점에서는 이 구분이 더 중요합니다. 실제 장애가 발생했을 때 어느 Component, Thread, JVM, DataSource, Interface가 영향을 받는지 추적하려면 책임과 Runtime이 연결되어 있어야 합니다.

반대로 정보계 패러다임의 전환을 제품명이나 박스 목록만으로 설명하면 그림은 단순해 보이지만 실제 변경과 장애를 설명하기 어렵습니다. 따라서 이 장에서는 정상경로뿐 아니라 금지패턴, Failure, Security, Evidence까지 함께 다룹니다.

이제 전체 그림을 기준으로 하나씩 해부해 보겠습니다. 먼저 L0에서는 이 장의 메시지를 고정하고, L1~L3에서는 책임과 Component를 나누며, L4에서는 실제 Runtime과 Failure를 보고, 마지막 L5에서는 Source·Config·Deployment·Runtime Evidence로 다시 확인합니다.

## FIG-02-02. L0 → L5 Drill-down Route

```text
L0 STORY / LANDSCAPE
"저장소와 Application 중심에서 살아 움직이는 Platform Architecture로"
 ↓
L1 RESPONSIBILITY / BOUNDARY
 ↓
L2 APPLICATION / LOGICAL / PLATFORM
 ↓
L3 COMPONENT / CONTRACT
 ↓
L4 RUNTIME / FAILURE / SECURITY
 ↓
L5 SOURCE / CONFIG / DEPLOYMENT / EVIDENCE
```

이제부터 이 대표 그림을 위에서 아래로 해부합니다. 상위 그림에서 보이는 연결을 바로 제품이나 서버로 해석하지 않고, 먼저 책임과 경계를 확인한 다음 Component와 Runtime으로 내려갑니다.

---

# 2.1 장 전체 전환 그림

## FIG-02-03. 장 전체 전환 그림

```text
┌──────────────────────────── CHANNEL / USER ────────────────────────────┐
│                                                                       │
│                        User / Browser                                 │
│                                                                       │
└──────────────────────────────┬────────────────────────────────────────┘
                               │ HTTP
             ┌─────────────────┼─────────────────┐
             │                 │                 │
             ▼                 ▼                 ▼
┌──────────────────┐ ┌──────────────────┐ ┌────────────────────────────┐
│ pdmg-ui          │ │ pdmg-jwt         │ │ pdmg-service               │
│ [AS-IS]          │ │ [AS-IS]          │ │ [AS-IS]                   │
│                  │ │                  │ │                            │
│ UI / Static      │ │ Login / SSO      │ │ Online Business Runtime    │
│ Request Build    │ │ Access / Refresh │ │                            │
│ ServiceId Call   │ │ JWKS             │ │ ┌────────────────────────┐ │
└────────┬─────────┘ └────────┬─────────┘ │ │ pdmg-fw [AS-IS]       │ │
         │ HTTP               │ Token     │ │ Framework Mechanism    │ │
         └──────────────┬─────┘           │ │ Filter / Context / TCF │ │
                        │                 │ │ Timeout / Error / Log  │ │
                        ▼                 │ └───────────┬────────────┘ │
                     Bearer               │             ▼              │
                                          │ Handler                    │
                                          │    ↓                       │
                                          │ Facade                     │
                                          │    ↓                       │
                                          │ Service                    │
                                          │    ↓                       │
                                          │ DAO                        │
                                          │    ↓                       │
                                          │ Mapper / SQL               │
                                          └───────────┬────────────────┘
                                                      │ MyBatis/JDBC
                                                      ▼
                                          ┌────────────────────────────┐
                                          │ RDW / DB                   │
                                          │ [DATA BOUNDARY]            │
                                          └────────────────────────────┘


                                          ┌────────────────────────────┐
                                          │ pdmg-om                    │
                                          │ [CURRENT DETAIL UNKNOWN]   │
                                          └────────────────────────────┘
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 장 전체 전환 그림을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“기존 Application-centric 정보계에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 2.2 기존 Application-centric 정보계

## FIG-02-04. 기존 Application-centric 정보계

```text
PDMG Application
      ↓
Business Service
      ↓
DAO
      ↓
Mapper
      ↓
JDBC
      ↓
┌────────────────── DATA BOUNDARY ──────────────────┐
│                                                  │
│ RDW / DB                                         │
│ Table / View / SQL                               │
│                                                  │
└──────────────────────────────────────────────────┘
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 기존 Application-centric 정보계을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Responsibility-centric 전환에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 2.3 Responsibility-centric 전환

## FIG-02-05. Responsibility-centric 전환

```text
APPLICATION ARCHITECTURE

pdmg-service
= Business Responsibility
      │
      │ requires
      ▼
LOGICAL TECHNICAL ARCHITECTURE

Application Runtime Node
├─ HTTP Runtime
├─ Framework Runtime
├─ Worker / Transaction
├─ Data Access
└─ Security Integration
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Responsibility-centric 전환을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Module → Runtime Boundary에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 2.4 Module → Runtime Boundary

## FIG-02-06. Module → Runtime Boundary

```text
Repository
│
├─ pdmg-ui
├─ pdmg-jwt
├─ pdmg-fw
├─ pdmg-service
└─ pdmg-om
      │
      │ build/module relationship
      ▼
Runtime
│
├─ pdmg-ui Process
├─ pdmg-jwt Process
└─ pdmg-service Process
      ├─ Business Classes
      └─ pdmg-fw Framework Classes
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Module → Runtime Boundary을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Product → Technical Capability에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 2.5 Product → Technical Capability

## FIG-02-07. Product → Technical Capability

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

이 그림의 핵심은 **책임을 기술 역할과 장애경계로 변환하는 단계**입니다. Product → Technical Capability을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Logical Architecture는 제품을 고르는 단계가 아니라 책임·Scale·Failure를 결정하는 단계입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Application, Build Module, Logical Node, Physical Host를 같은 것으로 취급하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Online → FAST / DEEP Workload에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 2.6 Online → FAST / DEEP Workload

## FIG-02-08. Online → FAST / DEEP Workload

```text
PDMG Current
ONLINE / FAST
 HTTP
 Worker
 TX
 RDW

NSIGHT Broader
FAST
 Event / CDC

DEEP
 ETL / ADW / BI
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Online → FAST / DEEP Workload을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `02_NSIGHT_PDMG_아키텍처정의서_정보계_패러다임의_전환_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Server → Logical → Physical에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 2.7 Server → Logical → Physical

## FIG-02-09. Server → Logical → Physical

```text
Application
 ↓
Logical Technical Node
 ↓
Runtime Type
 ↓
Environment
 ↓
VM / JVM / WAR
 ↓
Host / Center
```

이 그림의 핵심은 **책임을 기술 역할과 장애경계로 변환하는 단계**입니다. Server → Logical → Physical을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Logical Architecture는 제품을 고르는 단계가 아니라 책임·Scale·Failure를 결정하는 단계입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Application, Build Module, Logical Node, Physical Host를 같은 것으로 취급하지 않습니다.

Current Evidence 관점에서는 `02_NSIGHT_PDMG_아키텍처정의서_정보계_패러다임의_전환_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Logging → Runtime Evidence에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 2.8 Logging → Runtime Evidence

## FIG-02-10. Logging → Runtime Evidence

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

이 그림의 핵심은 **운영과 증적을 Architecture에 연결하는 단계**입니다. Logging → Runtime Evidence을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 로그가 존재하는 것과 Architecture Rule의 Runtime 준수 증적은 다릅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. SourceCommit→ArtifactHash→DeploymentId→ServiceId/GUID를 연결합니다.

Current Evidence 관점에서는 `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Document → Closed Loop에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 2.9 Document → Closed Loop

## FIG-02-11. Document → Closed Loop

```text
Document
 ↓
Model
 ↓
Code
 ↓
Test
 ↓
Runtime Evidence
 ↓
Drift
 ↓
GAP
 ↓
ADR
 ↓
New Baseline
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Document → Closed Loop을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `17_PDMG_TRACEABILITY_PASS_GAP_ADR_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“PDMG Current ↔ NSIGHT Target에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 2.10 PDMG Current ↔ NSIGHT Target

## FIG-02-12. PDMG Current ↔ NSIGHT Target

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

이 그림의 핵심은 **현재 구현을 있는 그대로 확인하는 영역**입니다. PDMG Current ↔ NSIGHT Target을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 목표 구조를 섞지 않고 Source·Config·Runtime에서 확인되는 사실만 Current로 둡니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. 확인되지 않은 항목은 `[OPEN]` 또는 `[UNKNOWN]`으로 남겨 두는 것이 정확한 Architecture 관리입니다.

Current Evidence 관점에서는 `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“정상 / 금지패턴에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 2.11 정상 / 금지패턴

## FIG-02-13. 정상 / 금지패턴

```text
Handler
  ↓
Facade
  ↓
Service
  ↓
DAO
  ↓
Mapper
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 정상 / 금지패턴을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“주안 / 대안에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 2.12 주안 / 대안

## FIG-02-14. 주안 / 대안

```text
[주안]
Application + Runtime + Operations + Evidence

        VS

[대안]
Application Source 중심
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 주안 / 대안을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `02_NSIGHT_PDMG_아키텍처정의서_정보계_패러다임의_전환_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“PASS / GAP에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 2.13 PASS / GAP

## FIG-02-15. PASS / GAP

```text
Architecture Definition
        ↓
[CONDITIONAL PASS]

Current PDMG
        ↓
[PARTIAL / GAP]

이 두 값은 동일하지 않다.
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. PASS / GAP을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“제3장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 2.14 제3장 Handoff

## FIG-02-16. 제3장 Handoff

```text
Logical Node
   ↓
Environment
   ↓
Physical Node
   ↓
Software / Runtime
   ↓
Port / Datasource
   ↓
Artifact
   ↓
Monitoring
   ↓
Evidence
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. 제3장 Handoff을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“장 결론 / 다음 장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# Architecture Cross-check — Failure / Security / Evidence

## FIG-02-17. Failure Propagation

```text
정보계 패러다임의 전환 하위 Component Failure
 ↓
Dependency / Pool / Interface 영향
 ↓
Runtime 지연 또는 오류
 ↓
Upstream Service 영향
 ↓
Alert / Recovery / Evidence
```

이 그림에서 중요한 것은 장애가 한 Component에서 끝나지 않는다는 점입니다. Architecture는 정상경로뿐 아니라 어떻게 느려지고, 어떻게 실패하고, 어디에서 차단해야 하는지까지 설명해야 합니다.

## FIG-02-18. Security / Trust Boundary

```text
Untrusted / External
 ↓
Authentication / Validation
 ↓
Trusted Principal / Contract
 ↓
정보계 패러다임의 전환 Runtime
 ↓
Authorization / Data Access
 ↓
Audit / Evidence
```

Security 관점에서는 '접속이 가능하다'와 '신뢰된 주체가 허가된 업무를 수행한다'를 구분합니다. PDMG의 JWT Issuer/Verifier 및 Identity Binding GAP는 해당 장에서 Critical GAP로 유지합니다.

---

# 정상패턴 / 금지패턴

## FIG-02-19. Normal Pattern

```text
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

정상패턴에서는 각 영역이 자신의 책임을 수행하고 다음 영역과는 명확한 Contract 또는 Runtime Boundary를 통해 연결됩니다. 이렇게 해야 변경 영향과 장애영향을 제한할 수 있습니다.

## FIG-02-20. Forbidden Pattern

```text
Module = Process = Server   X
Product = Architecture Node X
PDMG Current = NSIGHT Target X
```

반대로 금지패턴은 구현 자체가 불가능해서가 아니라 Architecture Boundary를 무너뜨리고 Source·Runtime·운영증적의 정합성을 떨어뜨리기 때문에 금지합니다. 예외가 필요하면 ADR와 Evidence로 관리합니다.

---

# Architecture Decision — 주안 / 대안 / Trade-off

## FIG-02-21. Primary vs Alternative

```text
[주안]
Runtime/Platform까지 Architecture Scope 확장

        VS

[대안]
Application Source 중심 Scope
```

이번 장의 주안은 **Runtime/Platform까지 Architecture Scope 확장**입니다. 이 선택은 현재 PDMG Source/Runtime과 NSIGHT Target Alignment를 함께 고려했을 때 책임과 Evidence를 가장 일관되게 유지할 수 있는 방향입니다.

대안인 **Application Source 중심 Scope**도 기술적으로 가능한 경우가 있습니다. 다만 대안을 선택하려면 더 나은 가용성·성능·운영성 또는 비용효과가 Runtime Test로 입증되어야 하며, 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| Architecture 정합성 | 높음 — 책임/경계 중심 | 별도 보완설계 필요 |
| Current PDMG 정합 | 현재 Evidence와 우선 정렬 | 변경범위 증가 가능 |
| 운영/장애분석 | 경계와 Trace가 명확 | 구조에 따라 복잡도 증가 |
| Evidence 조건 | 기본 Gate로 검증 | 추가 PoC/Test/ADR 필요 |

---

# Current Evidence / PASS / GAP

## FIG-02-22. Current GAP Map

```text
Current / Reference
│
├─ Module→Runtime/Physical 전수 Mapping
├─ Operations Current Scope
├─ Current↔Target Mapping Registry
└─ Runtime Evidence 자동화
```

- `[GAP/OPEN]` Module→Runtime/Physical 전수 Mapping
- `[GAP/OPEN]` Operations Current Scope
- `[GAP/OPEN]` Current↔Target Mapping Registry
- `[GAP/OPEN]` Runtime Evidence 자동화

## FIG-02-23. Architecture Assessment

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

Architecture PASS
 ≠
Implementation PASS
```

여기서 반드시 구분해야 할 것은 Architecture Definition PASS와 Current Implementation PASS가 동일하지 않다는 점입니다. 구조와 원칙이 합리적으로 정의되어 있어도 Current Source·Deployment·Runtime이 이를 따르지 않으면 Current Conformance는 GAP 또는 PARTIAL일 수 있습니다.

## FIG-02-24. Evidence Traceability

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

최종적으로 이 장의 Architecture는 문서 안에서 끝나지 않습니다. Source와 Config, 배포 Artifact, Runtime의 ServiceId/GUID, Metric/Trace/Test를 연결해 실제로 설계가 지켜졌음을 증명해야 합니다.

---

# 이 장의 결론

## FIG-02-25. Chapter Conclusion

```text
정보계 패러다임의 전환
 ↓
전체 TEXT Architecture
 ↓
L0 → L5 Drill-down
 ↓
Runtime / Failure / Security
 ↓
Normal / Forbidden
 ↓
Decision / Evidence
 ↓
PASS
```

이 장에서 확인한 것은 **저장소와 Application 중심에서 살아 움직이는 Platform Architecture로**입니다. 중요한 것은 구성요소를 많이 나열한 것이 아니라, 전체 그림에서 시작해 책임과 경계를 나누고 Runtime과 Evidence까지 연결했다는 점입니다.

여기까지 정리하면 다음 질문이 자연스럽게 생깁니다.

> **“비전에서 시작해 실제 Runtime 검증까지 내려간다”**

그 질문이 바로 다음 단계, **아키텍처 6단계 수립 방법론**의 주제입니다.


---

<!-- SOURCE CHAPTER: 03_아키텍처_6단계_수립_방법론.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제3장. 아키텍처 6단계 수립 방법론
## Story: “비전에서 시작해 실제 Runtime 검증까지 내려간다”
## 발표스크립트 Story + TEXT Architecture + Top-down → Drill-down

> 작성 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_마스터프롬프트_v1.md`  
> 목차 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_상세목차_v1.md`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 주인공: **PDMG Current Architecture** / 상위 정합: **NSIGHT Target Reference**

---

# 0. 이 장의 Story

## FIG-03-00. Story Opening

```text
이전 장의 질문
 ↓
비전에서 시작해 실제 Runtime 검증까지 내려간다
 ↓
전체 그림
 ↓
Top-down 해부
 ↓
Runtime / Failure / Security
 ↓
Evidence / PASS / GAP
```

# 1. 장 전체 대표 Architecture

## FIG-03-01. 아키텍처 6단계 수립 방법론 — Whole Architecture

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

이 장의 핵심은 **비전에서 시작해 실제 Runtime 검증까지 내려간다**라는 메시지를 하나의 Architecture 그림으로 먼저 이해하는 것입니다.

기존 문서에서는 아키텍처 6단계 수립 방법론과 관련된 기술요소가 개별 표나 구성요소로 나뉘어 보이는 경우가 많았습니다. 그러나 요소가 존재한다는 것만으로는 책임, 경계, 장애영향, 실행순서를 설명할 수 없습니다.

그래서 이번 정의서에서는 먼저 전체 그림을 보여주고, 독자가 그 그림의 방향과 경계를 이해한 뒤 L0에서 L5까지 내려가도록 구성합니다. 위에서 아래로 내려가는 이유는 세부기술이 상위 Architecture Intent를 잃지 않게 하기 위해서입니다.

여기서 중요한 원칙은 **PDMG Current와 NSIGHT Target을 분리하는 것**입니다. Source·Config·Runtime으로 확인되는 구조는 Current로 설명하고, 향후 목표나 전략은 Target 또는 Reference로 별도 표시합니다.

이것은 단순히 문서의 표현방식을 바꾸는 문제가 아닙니다. 같은 구성요소라도 Current인지 Target인지에 따라 Architecture Decision, 구현 책임, 테스트 기준이 달라지기 때문입니다.

운영 관점에서는 이 구분이 더 중요합니다. 실제 장애가 발생했을 때 어느 Component, Thread, JVM, DataSource, Interface가 영향을 받는지 추적하려면 책임과 Runtime이 연결되어 있어야 합니다.

반대로 아키텍처 6단계 수립 방법론을 제품명이나 박스 목록만으로 설명하면 그림은 단순해 보이지만 실제 변경과 장애를 설명하기 어렵습니다. 따라서 이 장에서는 정상경로뿐 아니라 금지패턴, Failure, Security, Evidence까지 함께 다룹니다.

이제 전체 그림을 기준으로 하나씩 해부해 보겠습니다. 먼저 L0에서는 이 장의 메시지를 고정하고, L1~L3에서는 책임과 Component를 나누며, L4에서는 실제 Runtime과 Failure를 보고, 마지막 L5에서는 Source·Config·Deployment·Runtime Evidence로 다시 확인합니다.

## FIG-03-02. L0 → L5 Drill-down Route

```text
L0 STORY / LANDSCAPE
"비전에서 시작해 실제 Runtime 검증까지 내려간다"
 ↓
L1 RESPONSIBILITY / BOUNDARY
 ↓
L2 APPLICATION / LOGICAL / PLATFORM
 ↓
L3 COMPONENT / CONTRACT
 ↓
L4 RUNTIME / FAILURE / SECURITY
 ↓
L5 SOURCE / CONFIG / DEPLOYMENT / EVIDENCE
```

이제부터 이 대표 그림을 위에서 아래로 해부합니다. 상위 그림에서 보이는 연결을 바로 제품이나 서버로 해석하지 않고, 먼저 책임과 경계를 확인한 다음 Component와 Runtime으로 내려갑니다.

---

# 3.1 6단계 전체 Journey

## FIG-03-03. 6단계 전체 Journey

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

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 6단계 전체 Journey을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `03_NSIGHT_PDMG_아키텍처정의서_아키텍처_6단계_수립_방법론_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“VISION에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 3.2 VISION

## FIG-03-04. VISION

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

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. VISION을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `03_NSIGHT_PDMG_아키텍처정의서_아키텍처_6단계_수립_방법론_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“BIG PICTURE에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 3.3 BIG PICTURE

## FIG-03-05. BIG PICTURE

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

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. BIG PICTURE을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `03_NSIGHT_PDMG_아키텍처정의서_아키텍처_6단계_수립_방법론_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“LOGICAL에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 3.4 LOGICAL

## FIG-03-06. LOGICAL

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

이 그림의 핵심은 **책임을 기술 역할과 장애경계로 변환하는 단계**입니다. LOGICAL을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Logical Architecture는 제품을 고르는 단계가 아니라 책임·Scale·Failure를 결정하는 단계입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Application, Build Module, Logical Node, Physical Host를 같은 것으로 취급하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“PHYSICAL에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 3.5 PHYSICAL

## FIG-03-07. PHYSICAL

```text
System / Application Context
User
 ↓
PDMG
 ↓
DB

        translates to

Physical Working Path
User
 ↓
GSLB
 ↓
L4
 ↓
Apache WEB
 ↓
Tomcat JVM
 ↓
PDMG WAR / Runtime
 ↓
DB Service
```

이 그림의 핵심은 **논리적 책임을 실제 실행자원으로 내리는 단계**입니다. PHYSICAL을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Server, VM, JVM, WAR를 구분하고 장애영향과 Scale Unit을 함께 봅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Hostname·Port·Version은 Inventory Evidence가 없으면 창작하지 않습니다.

Current Evidence 관점에서는 `02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“MECHANISM에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 3.6 MECHANISM

## FIG-03-08. MECHANISM

```text
NSIGHT Integration Context
│
├─ Online Immediate Result
│      → API / Service
│
├─ Business Event
│      → Event Broker
│
├─ DB Change
│      → CDC
│
├─ Bulk Data
│      → ETL
│
└─ File
       → MFT / FOS
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. MECHANISM을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“RUNTIME에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 3.7 RUNTIME

## FIG-03-09. RUNTIME

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

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. RUNTIME을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Evidence / Gate에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 3.8 Evidence / Gate

## FIG-03-10. Evidence / Gate

```text
Rule
 ↓
Runtime Metric / Trace / Failure Test
 ↓
Evidence
 ↓
Gate Result
```

이 그림의 핵심은 **운영과 증적을 Architecture에 연결하는 단계**입니다. Evidence / Gate을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 로그가 존재하는 것과 Architecture Rule의 Runtime 준수 증적은 다릅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. SourceCommit→ArtifactHash→DeploymentId→ServiceId/GUID를 연결합니다.

Current Evidence 관점에서는 `17_PDMG_TRACEABILITY_PASS_GAP_ADR_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“단계간 Input / Output에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 3.9 단계간 Input / Output

## FIG-03-11. 단계간 Input / Output

```text
Source Module
  ↓
Gradle Build
  ↓
Module Output
  ↓
WAR / JAR / Static Artifact
  ↓
Deployment
  ↓
Runtime Process
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 단계간 Input / Output을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Top-down + Bottom-up에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 3.10 Top-down + Bottom-up

## FIG-03-12. Top-down + Bottom-up

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

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Top-down + Bottom-up을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `03_NSIGHT_PDMG_아키텍처정의서_아키텍처_6단계_수립_방법론_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“정상 / 금지패턴에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 3.11 정상 / 금지패턴

## FIG-03-13. 정상 / 금지패턴

```text
Handler
  ↓
Facade
  ↓
Service
  ↓
DAO
  ↓
Mapper
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 정상 / 금지패턴을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Architecture Gate에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 3.12 Architecture Gate

## FIG-03-14. Architecture Gate

```text
Architecture Definition
       ↓
CONDITIONAL PASS

PDMG Current Conformance
       ↓
PARTIAL / GAP

Runtime Evidence Coverage
       ↓
MEDIUM

Highest Criticality
       ↓
JWT Issuer / Verifier
```

이 그림의 핵심은 **운영과 증적을 Architecture에 연결하는 단계**입니다. Architecture Gate을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 로그가 존재하는 것과 Architecture Rule의 Runtime 준수 증적은 다릅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. SourceCommit→ArtifactHash→DeploymentId→ServiceId/GUID를 연결합니다.

Current Evidence 관점에서는 `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“PASS / GAP에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 3.13 PASS / GAP

## FIG-03-15. PASS / GAP

```text
Architecture Definition
          ↓
PASS / CONDITIONAL / OPEN / FAIL

          ≠

PDMG Current Implementation
          ↓
PASS / PARTIAL / GAP / CONFLICT / UNKNOWN
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. PASS / GAP을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `00_PDMG_ARCHITECTURE_MASTER_INDEX.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“제4장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 3.14 제4장 Handoff

## FIG-03-16. 제4장 Handoff

```text
Logical Node
   ↓
Environment
   ↓
Physical Node
   ↓
Software / Runtime
   ↓
Port / Datasource
   ↓
Artifact
   ↓
Monitoring
   ↓
Evidence
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. 제4장 Handoff을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“장 결론 / 다음 장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# Architecture Cross-check — Failure / Security / Evidence

## FIG-03-17. Failure Propagation

```text
아키텍처 6단계 수립 방법론 하위 Component Failure
 ↓
Dependency / Pool / Interface 영향
 ↓
Runtime 지연 또는 오류
 ↓
Upstream Service 영향
 ↓
Alert / Recovery / Evidence
```

이 그림에서 중요한 것은 장애가 한 Component에서 끝나지 않는다는 점입니다. Architecture는 정상경로뿐 아니라 어떻게 느려지고, 어떻게 실패하고, 어디에서 차단해야 하는지까지 설명해야 합니다.

## FIG-03-18. Security / Trust Boundary

```text
Untrusted / External
 ↓
Authentication / Validation
 ↓
Trusted Principal / Contract
 ↓
아키텍처 6단계 수립 방법론 Runtime
 ↓
Authorization / Data Access
 ↓
Audit / Evidence
```

Security 관점에서는 '접속이 가능하다'와 '신뢰된 주체가 허가된 업무를 수행한다'를 구분합니다. PDMG의 JWT Issuer/Verifier 및 Identity Binding GAP는 해당 장에서 Critical GAP로 유지합니다.

---

# 정상패턴 / 금지패턴

## FIG-03-19. Normal Pattern

```text
VISION → BIG PICTURE → LOGICAL
→ PHYSICAL → MECHANISM → RUNTIME
→ EVIDENCE
```

정상패턴에서는 각 영역이 자신의 책임을 수행하고 다음 영역과는 명확한 Contract 또는 Runtime Boundary를 통해 연결됩니다. 이렇게 해야 변경 영향과 장애영향을 제한할 수 있습니다.

## FIG-03-20. Forbidden Pattern

```text
제품 선정
 ↓
서버 배치
 ↓
사후 Architecture 설명
 X
```

반대로 금지패턴은 구현 자체가 불가능해서가 아니라 Architecture Boundary를 무너뜨리고 Source·Runtime·운영증적의 정합성을 떨어뜨리기 때문에 금지합니다. 예외가 필요하면 ADR와 Evidence로 관리합니다.

---

# Architecture Decision — 주안 / 대안 / Trade-off

## FIG-03-21. Primary vs Alternative

```text
[주안]
6단계 Top-down + Bottom-up Evidence

        VS

[대안]
제품/Physical 선결정
```

이번 장의 주안은 **6단계 Top-down + Bottom-up Evidence**입니다. 이 선택은 현재 PDMG Source/Runtime과 NSIGHT Target Alignment를 함께 고려했을 때 책임과 Evidence를 가장 일관되게 유지할 수 있는 방향입니다.

대안인 **제품/Physical 선결정**도 기술적으로 가능한 경우가 있습니다. 다만 대안을 선택하려면 더 나은 가용성·성능·운영성 또는 비용효과가 Runtime Test로 입증되어야 하며, 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| Architecture 정합성 | 높음 — 책임/경계 중심 | 별도 보완설계 필요 |
| Current PDMG 정합 | 현재 Evidence와 우선 정렬 | 변경범위 증가 가능 |
| 운영/장애분석 | 경계와 Trace가 명확 | 구조에 따라 복잡도 증가 |
| Evidence 조건 | 기본 Gate로 검증 | 추가 PoC/Test/ADR 필요 |

---

# Current Evidence / PASS / GAP

## FIG-03-22. Current GAP Map

```text
Current / Reference
│
├─ 6단계 산출물 자동 Trace
├─ Architecture Model SSOT
├─ G50 Runtime Evidence 자동화
└─ HG90 Gate 자동화
```

- `[GAP/OPEN]` 6단계 산출물 자동 Trace
- `[GAP/OPEN]` Architecture Model SSOT
- `[GAP/OPEN]` G50 Runtime Evidence 자동화
- `[GAP/OPEN]` HG90 Gate 자동화

## FIG-03-23. Architecture Assessment

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

Architecture PASS
 ≠
Implementation PASS
```

여기서 반드시 구분해야 할 것은 Architecture Definition PASS와 Current Implementation PASS가 동일하지 않다는 점입니다. 구조와 원칙이 합리적으로 정의되어 있어도 Current Source·Deployment·Runtime이 이를 따르지 않으면 Current Conformance는 GAP 또는 PARTIAL일 수 있습니다.

## FIG-03-24. Evidence Traceability

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

최종적으로 이 장의 Architecture는 문서 안에서 끝나지 않습니다. Source와 Config, 배포 Artifact, Runtime의 ServiceId/GUID, Metric/Trace/Test를 연결해 실제로 설계가 지켜졌음을 증명해야 합니다.

---

# 이 장의 결론

## FIG-03-25. Chapter Conclusion

```text
아키텍처 6단계 수립 방법론
 ↓
전체 TEXT Architecture
 ↓
L0 → L5 Drill-down
 ↓
Runtime / Failure / Security
 ↓
Normal / Forbidden
 ↓
Decision / Evidence
 ↓
PASS
```

이 장에서 확인한 것은 **비전에서 시작해 실제 Runtime 검증까지 내려간다**입니다. 중요한 것은 구성요소를 많이 나열한 것이 아니라, 전체 그림에서 시작해 책임과 경계를 나누고 Runtime과 Evidence까지 연결했다는 점입니다.

여기까지 정리하면 다음 질문이 자연스럽게 생깁니다.

> **“화려한 박스가 아니라 책임과 경계를 먼저 본다”**

그 질문이 바로 다음 단계, **Big Picture**의 주제입니다.


---

<!-- SOURCE CHAPTER: 04_Big_Picture.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제4장. Big Picture
## Story: “화려한 박스가 아니라 책임과 경계를 먼저 본다”
## 발표스크립트 Story + TEXT Architecture + Top-down → Drill-down

> 작성 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_마스터프롬프트_v1.md`  
> 목차 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_상세목차_v1.md`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 주인공: **PDMG Current Architecture** / 상위 정합: **NSIGHT Target Reference**

---

# 0. 이 장의 Story

## FIG-04-00. Story Opening

```text
이전 장의 질문
 ↓
화려한 박스가 아니라 책임과 경계를 먼저 본다
 ↓
전체 그림
 ↓
Top-down 해부
 ↓
Runtime / Failure / Security
 ↓
Evidence / PASS / GAP
```

# 1. 장 전체 대표 Architecture

## FIG-04-01. Big Picture — Whole Architecture

```text
User / Browser
      ↓
UI
      ↓
Authentication
      ↓
Application Runtime
      ↓
Data

External
→ Approved Interface

Cross-cutting
Security / Observability / Operations
```

이 장의 핵심은 **화려한 박스가 아니라 책임과 경계를 먼저 본다**라는 메시지를 하나의 Architecture 그림으로 먼저 이해하는 것입니다.

기존 문서에서는 Big Picture과 관련된 기술요소가 개별 표나 구성요소로 나뉘어 보이는 경우가 많았습니다. 그러나 요소가 존재한다는 것만으로는 책임, 경계, 장애영향, 실행순서를 설명할 수 없습니다.

그래서 이번 정의서에서는 먼저 전체 그림을 보여주고, 독자가 그 그림의 방향과 경계를 이해한 뒤 L0에서 L5까지 내려가도록 구성합니다. 위에서 아래로 내려가는 이유는 세부기술이 상위 Architecture Intent를 잃지 않게 하기 위해서입니다.

여기서 중요한 원칙은 **PDMG Current와 NSIGHT Target을 분리하는 것**입니다. Source·Config·Runtime으로 확인되는 구조는 Current로 설명하고, 향후 목표나 전략은 Target 또는 Reference로 별도 표시합니다.

이것은 단순히 문서의 표현방식을 바꾸는 문제가 아닙니다. 같은 구성요소라도 Current인지 Target인지에 따라 Architecture Decision, 구현 책임, 테스트 기준이 달라지기 때문입니다.

운영 관점에서는 이 구분이 더 중요합니다. 실제 장애가 발생했을 때 어느 Component, Thread, JVM, DataSource, Interface가 영향을 받는지 추적하려면 책임과 Runtime이 연결되어 있어야 합니다.

반대로 Big Picture을 제품명이나 박스 목록만으로 설명하면 그림은 단순해 보이지만 실제 변경과 장애를 설명하기 어렵습니다. 따라서 이 장에서는 정상경로뿐 아니라 금지패턴, Failure, Security, Evidence까지 함께 다룹니다.

이제 전체 그림을 기준으로 하나씩 해부해 보겠습니다. 먼저 L0에서는 이 장의 메시지를 고정하고, L1~L3에서는 책임과 Component를 나누며, L4에서는 실제 Runtime과 Failure를 보고, 마지막 L5에서는 Source·Config·Deployment·Runtime Evidence로 다시 확인합니다.

## FIG-04-02. L0 → L5 Drill-down Route

```text
L0 STORY / LANDSCAPE
"화려한 박스가 아니라 책임과 경계를 먼저 본다"
 ↓
L1 RESPONSIBILITY / BOUNDARY
 ↓
L2 APPLICATION / LOGICAL / PLATFORM
 ↓
L3 COMPONENT / CONTRACT
 ↓
L4 RUNTIME / FAILURE / SECURITY
 ↓
L5 SOURCE / CONFIG / DEPLOYMENT / EVIDENCE
```

이제부터 이 대표 그림을 위에서 아래로 해부합니다. 상위 그림에서 보이는 연결을 바로 제품이나 서버로 해석하지 않고, 먼저 책임과 경계를 확인한 다음 Component와 Runtime으로 내려갑니다.

---

# 4.1 PDMG 전체 System Context

## FIG-04-03. PDMG 전체 System Context

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

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. PDMG 전체 System Context을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“User / Channel Boundary에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 4.2 User / Channel Boundary

## FIG-04-04. User / Channel Boundary

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

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. User / Channel Boundary을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“UI Delivery Boundary에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 4.3 UI Delivery Boundary

## FIG-04-05. UI Delivery Boundary

```text
User / Browser
      ↓
┌─────────────────────────────┐
│ LTN-01 UI Delivery          │
│                             │
│ Presentation                │
│ Static Resource             │
│ Transaction Catalog         │
│ Request Assembly            │
│ Bearer Token Propagation    │
└─────────────┬───────────────┘
              │ HTTP
              ▼
Authentication / Application
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. UI Delivery Boundary을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Authentication Boundary에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 4.4 Authentication Boundary

## FIG-04-06. Authentication Boundary

```text
Login / SSO
 ↓
pdmg-jwt
 ↓
Access / Refresh
 ↓
JWKS
 ↓
Business Runtime
```

이 그림의 핵심은 **Trust Boundary와 Identity 통제**입니다. Authentication Boundary을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Security는 Business Runtime 앞에서 통과해야 하는 Architecture Boundary입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. JWT Issuer/Verifier 정합성과 Principal→Business User Binding은 Critical GAP로 유지합니다.

Current Evidence 관점에서는 `04_NSIGHT_PDMG_아키텍처정의서_BIG_PICTURE_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Application Runtime Boundary에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 4.5 Application Runtime Boundary

## FIG-04-07. Application Runtime Boundary

```text
pdmg-service
┌──────────────────────────┐
│ pdmg-fw                  │
│ Runtime Control          │
│                          │
│ Business Component       │
│ Handler / Facade /       │
│ Service / DAO            │
└────────────┬─────────────┘
             ↓
           Data
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Application Runtime Boundary을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `04_NSIGHT_PDMG_아키텍처정의서_BIG_PICTURE_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Framework / Business Boundary에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 4.6 Framework / Business Boundary

## FIG-04-08. Framework / Business Boundary

```text
Framework
TransactionDispatcher
       ↓
TransactionHandler Interface
       ↓
Business Handler
       ↓
Business Facade
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Framework / Business Boundary을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Data Boundary에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 4.7 Data Boundary

## FIG-04-09. Data Boundary

```text
PDMG Application
      ↓
Business Service
      ↓
DAO
      ↓
Mapper
      ↓
JDBC
      ↓
┌────────────────── DATA BOUNDARY ──────────────────┐
│                                                  │
│ RDW / DB                                         │
│ Table / View / SQL                               │
│                                                  │
└──────────────────────────────────────────────────┘
```

이 그림의 핵심은 **데이터 역할·소유권·흐름을 정의하는 단계**입니다. Data Boundary을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Operational/Analytical Workload, Ownership, Lineage, Security를 함께 정의합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Cross-system Direct DML은 정상패턴으로 두지 않습니다.

Current Evidence 관점에서는 `02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“External Integration Boundary에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 4.8 External Integration Boundary

## FIG-04-10. External Integration Boundary

```text
PDMG
 ↓
Approved Interface Contract
 ├─ API
 ├─ Event
 ├─ File
 └─ Data Movement
 ↓
External System

Current inventory
= OPEN / PARTIAL
```

이 그림의 핵심은 **경계 간 연결을 Contract로 통제하는 단계**입니다. External Integration Boundary을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Source/Target, Contract, Security, Timeout, Retry, Operations까지 함께 정의해야 합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. P2P Direct DB 연결은 예외 ADR 없이 정상경로로 허용하지 않습니다.

Current Evidence 관점에서는 `04_NSIGHT_PDMG_아키텍처정의서_BIG_PICTURE_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Security Cross-cutting에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 4.9 Security Cross-cutting

## FIG-04-11. Security Cross-cutting

```text
User
 ↓
Auth
 ↓
Token
 ↓
Verification
 ↓
Principal
 ↓
Authorization
 ↓
Data
```

이 그림의 핵심은 **Trust Boundary와 Identity 통제**입니다. Security Cross-cutting을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Security는 Business Runtime 앞에서 통과해야 하는 Architecture Boundary입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. JWT Issuer/Verifier 정합성과 Principal→Business User Binding은 Critical GAP로 유지합니다.

Current Evidence 관점에서는 `04_NSIGHT_PDMG_아키텍처정의서_BIG_PICTURE_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Observability Cross-cutting에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 4.10 Observability Cross-cutting

## FIG-04-12. Observability Cross-cutting

```text
UI
 │
 │ GUID / ServiceId
 ▼
Authentication
 │
 ▼
Application Runtime
 │
 ├─ Handler / Service
 │
 ├─ ErrorCode
 │
 └─ SqlId
 │
 ▼
Data
 │
 ▼
Metric / Log / Trace
 │
 ▼
Operations
```

이 그림의 핵심은 **운영과 증적을 Architecture에 연결하는 단계**입니다. Observability Cross-cutting을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 로그가 존재하는 것과 Architecture Rule의 Runtime 준수 증적은 다릅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. SourceCommit→ArtifactHash→DeploymentId→ServiceId/GUID를 연결합니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Allowed Path에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 4.11 Allowed Path

## FIG-04-13. Allowed Path

```text
Client
  ↓
UI Delivery
  ↓
Authentication / Token
  ↓
Application Runtime
  ↓
Framework Runtime Control
  ↓
Business Execution
  ↓
Data Access
  ↓
Data Service
```

이 그림의 핵심은 **경계 간 연결을 Contract로 통제하는 단계**입니다. Allowed Path을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Source/Target, Contract, Security, Timeout, Retry, Operations까지 함께 정의해야 합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. P2P Direct DB 연결은 예외 ADR 없이 정상경로로 허용하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Forbidden Path에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 4.12 Forbidden Path

## FIG-04-14. Forbidden Path

```text
Client ─────────────► Data Service
       X

UI Delivery ────────► DAO / DB
            X

External ───────────► Internal DB DML
         X

Framework ──────────► Specific Business DB Logic
          X

Analytics ──────────► Online RDW Heavy Query
          X / controlled

Module name
────────► Physical Server name
자동변환
          X
```

이 그림의 핵심은 **경계 간 연결을 Contract로 통제하는 단계**입니다. Forbidden Path을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Source/Target, Contract, Security, Timeout, Retry, Operations까지 함께 정의해야 합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. P2P Direct DB 연결은 예외 ADR 없이 정상경로로 허용하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Current GAP에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 4.13 Current GAP

## FIG-04-15. Current GAP

```text
Client User Header
≠ Trusted Identity

Trusted Identity
= Verified Principal 기반
```

이 그림의 핵심은 **현재 구현을 있는 그대로 확인하는 영역**입니다. Current GAP을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 목표 구조를 섞지 않고 Source·Config·Runtime에서 확인되는 사실만 Current로 둡니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. 확인되지 않은 항목은 `[OPEN]` 또는 `[UNKNOWN]`으로 남겨 두는 것이 정확한 Architecture 관리입니다.

Current Evidence 관점에서는 `02_PDMG_SYSTEM_CONTEXT_BOUNDARY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“주안 / 대안에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 4.14 주안 / 대안

## FIG-04-16. 주안 / 대안

```text
[주안]
책임 고정 + Contract 연결

        VS

[대안]
P2P/Direct 연결 확대
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 주안 / 대안을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `04_NSIGHT_PDMG_아키텍처정의서_BIG_PICTURE_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“PASS에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 4.15 PASS

## FIG-04-17. PASS

```text
Architecture Definition
        ↓
[CONDITIONAL PASS]

Current PDMG
        ↓
[PARTIAL / GAP]

이 두 값은 동일하지 않다.
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. PASS을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `01_PDMG_EXECUTIVE_ARCHITECTURE_상세정의서_v2.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“제5장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 4.16 제5장 Handoff

## FIG-04-18. 제5장 Handoff

```text
Logical Node
   ↓
Environment
   ↓
Physical Node
   ↓
Software / Runtime
   ↓
Port / Datasource
   ↓
Artifact
   ↓
Monitoring
   ↓
Evidence
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. 제5장 Handoff을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“장 결론 / 다음 장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# Architecture Cross-check — Failure / Security / Evidence

## FIG-04-19. Failure Propagation

```text
Big Picture 하위 Component Failure
 ↓
Dependency / Pool / Interface 영향
 ↓
Runtime 지연 또는 오류
 ↓
Upstream Service 영향
 ↓
Alert / Recovery / Evidence
```

이 그림에서 중요한 것은 장애가 한 Component에서 끝나지 않는다는 점입니다. Architecture는 정상경로뿐 아니라 어떻게 느려지고, 어떻게 실패하고, 어디에서 차단해야 하는지까지 설명해야 합니다.

## FIG-04-20. Security / Trust Boundary

```text
Untrusted / External
 ↓
Authentication / Validation
 ↓
Trusted Principal / Contract
 ↓
Big Picture Runtime
 ↓
Authorization / Data Access
 ↓
Audit / Evidence
```

Security 관점에서는 '접속이 가능하다'와 '신뢰된 주체가 허가된 업무를 수행한다'를 구분합니다. PDMG의 JWT Issuer/Verifier 및 Identity Binding GAP는 해당 장에서 Critical GAP로 유지합니다.

---

# 정상패턴 / 금지패턴

## FIG-04-21. Normal Pattern

```text
User → UI/Auth → App Runtime → Data
External → Approved Contract
Security/Observability = Cross-cutting
```

정상패턴에서는 각 영역이 자신의 책임을 수행하고 다음 영역과는 명확한 Contract 또는 Runtime Boundary를 통해 연결됩니다. 이렇게 해야 변경 영향과 장애영향을 제한할 수 있습니다.

## FIG-04-22. Forbidden Pattern

```text
Browser → DB               X
UI → DAO/Mapper             X
External → PDMG DB DML      X
```

반대로 금지패턴은 구현 자체가 불가능해서가 아니라 Architecture Boundary를 무너뜨리고 Source·Runtime·운영증적의 정합성을 떨어뜨리기 때문에 금지합니다. 예외가 필요하면 ADR와 Evidence로 관리합니다.

---

# Architecture Decision — 주안 / 대안 / Trade-off

## FIG-04-23. Primary vs Alternative

```text
[주안]
책임 고정 + Contract 연결

        VS

[대안]
P2P/Direct 연결 확대
```

이번 장의 주안은 **책임 고정 + Contract 연결**입니다. 이 선택은 현재 PDMG Source/Runtime과 NSIGHT Target Alignment를 함께 고려했을 때 책임과 Evidence를 가장 일관되게 유지할 수 있는 방향입니다.

대안인 **P2P/Direct 연결 확대**도 기술적으로 가능한 경우가 있습니다. 다만 대안을 선택하려면 더 나은 가용성·성능·운영성 또는 비용효과가 Runtime Test로 입증되어야 하며, 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| Architecture 정합성 | 높음 — 책임/경계 중심 | 별도 보완설계 필요 |
| Current PDMG 정합 | 현재 Evidence와 우선 정렬 | 변경범위 증가 가능 |
| 운영/장애분석 | 경계와 Trace가 명확 | 구조에 따라 복잡도 증가 |
| Evidence 조건 | 기본 Gate로 검증 | 추가 PoC/Test/ADR 필요 |

---

# Current Evidence / PASS / GAP

## FIG-04-24. Current GAP Map

```text
Current / Reference
│
├─ JWT/Identity Critical GAP
├─ External Interface Inventory
├─ pdmg-om Current Detail
└─ Deployment Mapping
```

- `[GAP/OPEN]` JWT/Identity Critical GAP
- `[GAP/OPEN]` External Interface Inventory
- `[GAP/OPEN]` pdmg-om Current Detail
- `[GAP/OPEN]` Deployment Mapping

## FIG-04-25. Architecture Assessment

```text
Architecture Definition
 ↓
PASS

Current PDMG Conformance
 ↓
CONDITIONAL / PARTIAL

Runtime Evidence Coverage
 ↓
MEDIUM

Architecture PASS
 ≠
Implementation PASS
```

여기서 반드시 구분해야 할 것은 Architecture Definition PASS와 Current Implementation PASS가 동일하지 않다는 점입니다. 구조와 원칙이 합리적으로 정의되어 있어도 Current Source·Deployment·Runtime이 이를 따르지 않으면 Current Conformance는 GAP 또는 PARTIAL일 수 있습니다.

## FIG-04-26. Evidence Traceability

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

최종적으로 이 장의 Architecture는 문서 안에서 끝나지 않습니다. Source와 Config, 배포 Artifact, Runtime의 ServiceId/GUID, Metric/Trace/Test를 연결해 실제로 설계가 지켜졌음을 증명해야 합니다.

---

# 이 장의 결론

## FIG-04-27. Chapter Conclusion

```text
Big Picture
 ↓
전체 TEXT Architecture
 ↓
L0 → L5 Drill-down
 ↓
Runtime / Failure / Security
 ↓
Normal / Forbidden
 ↓
Decision / Evidence
 ↓
PASS
```

이 장에서 확인한 것은 **화려한 박스가 아니라 책임과 경계를 먼저 본다**입니다. 중요한 것은 구성요소를 많이 나열한 것이 아니라, 전체 그림에서 시작해 책임과 경계를 나누고 Runtime과 Evidence까지 연결했다는 점입니다.

여기까지 정리하면 다음 질문이 자연스럽게 생깁니다.

> **“기술을 고르기 전에 무엇을 분리하고 허용할지 정한다”**

그 질문이 바로 다음 단계, **논리 아키텍처**의 주제입니다.


---

<!-- SOURCE CHAPTER: 05_논리_아키텍처.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제5장. 논리 아키텍처
## Story: “기술을 고르기 전에 무엇을 분리하고 허용할지 정한다”
## 발표스크립트 Story + TEXT Architecture + Top-down → Drill-down

> 작성 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_마스터프롬프트_v1.md`  
> 목차 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_상세목차_v1.md`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 주인공: **PDMG Current Architecture** / 상위 정합: **NSIGHT Target Reference**

---

# 0. 이 장의 Story

## FIG-05-00. Story Opening

```text
이전 장의 질문
 ↓
기술을 고르기 전에 무엇을 분리하고 허용할지 정한다
 ↓
전체 그림
 ↓
Top-down 해부
 ↓
Runtime / Failure / Security
 ↓
Evidence / PASS / GAP
```

# 1. 장 전체 대표 Architecture

## FIG-05-01. 논리 아키텍처 — Whole Architecture

```text
Application Responsibility
       ↓
Technical Capability
       ↓
Logical Technical Node
       ↓
Runtime Type
       ↓
State / Scale / Failure / Security
```

이 장의 핵심은 **기술을 고르기 전에 무엇을 분리하고 허용할지 정한다**라는 메시지를 하나의 Architecture 그림으로 먼저 이해하는 것입니다.

기존 문서에서는 논리 아키텍처과 관련된 기술요소가 개별 표나 구성요소로 나뉘어 보이는 경우가 많았습니다. 그러나 요소가 존재한다는 것만으로는 책임, 경계, 장애영향, 실행순서를 설명할 수 없습니다.

그래서 이번 정의서에서는 먼저 전체 그림을 보여주고, 독자가 그 그림의 방향과 경계를 이해한 뒤 L0에서 L5까지 내려가도록 구성합니다. 위에서 아래로 내려가는 이유는 세부기술이 상위 Architecture Intent를 잃지 않게 하기 위해서입니다.

여기서 중요한 원칙은 **PDMG Current와 NSIGHT Target을 분리하는 것**입니다. Source·Config·Runtime으로 확인되는 구조는 Current로 설명하고, 향후 목표나 전략은 Target 또는 Reference로 별도 표시합니다.

이것은 단순히 문서의 표현방식을 바꾸는 문제가 아닙니다. 같은 구성요소라도 Current인지 Target인지에 따라 Architecture Decision, 구현 책임, 테스트 기준이 달라지기 때문입니다.

운영 관점에서는 이 구분이 더 중요합니다. 실제 장애가 발생했을 때 어느 Component, Thread, JVM, DataSource, Interface가 영향을 받는지 추적하려면 책임과 Runtime이 연결되어 있어야 합니다.

반대로 논리 아키텍처을 제품명이나 박스 목록만으로 설명하면 그림은 단순해 보이지만 실제 변경과 장애를 설명하기 어렵습니다. 따라서 이 장에서는 정상경로뿐 아니라 금지패턴, Failure, Security, Evidence까지 함께 다룹니다.

이제 전체 그림을 기준으로 하나씩 해부해 보겠습니다. 먼저 L0에서는 이 장의 메시지를 고정하고, L1~L3에서는 책임과 Component를 나누며, L4에서는 실제 Runtime과 Failure를 보고, 마지막 L5에서는 Source·Config·Deployment·Runtime Evidence로 다시 확인합니다.

## FIG-05-02. L0 → L5 Drill-down Route

```text
L0 STORY / LANDSCAPE
"기술을 고르기 전에 무엇을 분리하고 허용할지 정한다"
 ↓
L1 RESPONSIBILITY / BOUNDARY
 ↓
L2 APPLICATION / LOGICAL / PLATFORM
 ↓
L3 COMPONENT / CONTRACT
 ↓
L4 RUNTIME / FAILURE / SECURITY
 ↓
L5 SOURCE / CONFIG / DEPLOYMENT / EVIDENCE
```

이제부터 이 대표 그림을 위에서 아래로 해부합니다. 상위 그림에서 보이는 연결을 바로 제품이나 서버로 해석하지 않고, 먼저 책임과 경계를 확인한 다음 Component와 Runtime으로 내려갑니다.

---

# 5.1 장 전체 Logical Architecture

## FIG-05-03. 장 전체 Logical Architecture

```text
Application?
 ↓
Capability?
 ↓
Logical Node?
 ↓
Runtime?
 ↓
State?
 ↓
Scale?
 ↓
Failure?
 ↓
Security?
 ↓
Allowed Path?
 ↓
Physical Handoff?
```

이 그림의 핵심은 **책임을 기술 역할과 장애경계로 변환하는 단계**입니다. 장 전체 Logical Architecture을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Logical Architecture는 제품을 고르는 단계가 아니라 책임·Scale·Failure를 결정하는 단계입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Application, Build Module, Logical Node, Physical Host를 같은 것으로 취급하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Application → Technical Capability에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 5.2 Application → Technical Capability

## FIG-05-04. Application → Technical Capability

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

이 그림의 핵심은 **책임을 기술 역할과 장애경계로 변환하는 단계**입니다. Application → Technical Capability을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Logical Architecture는 제품을 고르는 단계가 아니라 책임·Scale·Failure를 결정하는 단계입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Application, Build Module, Logical Node, Physical Host를 같은 것으로 취급하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Logical Node Catalog에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 5.3 Logical Node Catalog

## FIG-05-05. Logical Node Catalog

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

이 그림의 핵심은 **책임을 기술 역할과 장애경계로 변환하는 단계**입니다. Logical Node Catalog을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Logical Architecture는 제품을 고르는 단계가 아니라 책임·Scale·Failure를 결정하는 단계입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Application, Build Module, Logical Node, Physical Host를 같은 것으로 취급하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“UI Delivery Node에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 5.4 UI Delivery Node

## FIG-05-06. UI Delivery Node

```text
User / Browser
      ↓
┌─────────────────────────────┐
│ LTN-01 UI Delivery          │
│                             │
│ Presentation                │
│ Static Resource             │
│ Transaction Catalog         │
│ Request Assembly            │
│ Bearer Token Propagation    │
└─────────────┬───────────────┘
              │ HTTP
              ▼
Authentication / Application
```

이 그림의 핵심은 **책임을 기술 역할과 장애경계로 변환하는 단계**입니다. UI Delivery Node을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Logical Architecture는 제품을 고르는 단계가 아니라 책임·Scale·Failure를 결정하는 단계입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Application, Build Module, Logical Node, Physical Host를 같은 것으로 취급하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Authentication Node에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 5.5 Authentication Node

## FIG-05-07. Authentication Node

```text
User / SSO Caller
        ↓
┌─────────────────────────────┐
│ LTN-02 Authentication      │
│                             │
│ Login                       │
│ SSO Validation              │
│ Token Issue                 │
│ Refresh State               │
│ JWKS                        │
└─────────────┬───────────────┘
              │ Token / Public Key
              ▼
Application Runtime
```

이 그림의 핵심은 **Trust Boundary와 Identity 통제**입니다. Authentication Node을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Security는 Business Runtime 앞에서 통과해야 하는 Architecture Boundary입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. JWT Issuer/Verifier 정합성과 Principal→Business User Binding은 Critical GAP로 유지합니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Application Runtime Node에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 5.6 Application Runtime Node

## FIG-05-08. Application Runtime Node

```text
┌──────────────────────────────────────────────┐
│ LTN-03 APPLICATION RUNTIME                  │
│                                              │
│ HTTP / MVC Runtime                           │
│        ↓                                     │
│ Framework Runtime Control                    │
│        ↓                                     │
│ TCF / Dispatcher / Handler                   │
│        ↓                                     │
│ Facade / Service                             │
│        ↓                                     │
│ DAO / Data Access                            │
│                                              │
└───────────────────┬──────────────────────────┘
                    │
                    ▼
                 Data Node
```

이 그림의 핵심은 **책임을 기술 역할과 장애경계로 변환하는 단계**입니다. Application Runtime Node을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Logical Architecture는 제품을 고르는 단계가 아니라 책임·Scale·Failure를 결정하는 단계입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Application, Build Module, Logical Node, Physical Host를 같은 것으로 취급하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Framework Runtime Capability에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 5.7 Framework Runtime Capability

## FIG-05-09. Framework Runtime Capability

```text
Application Runtime Node
│
├─ Framework Entry
│   ├─ Filter
│   ├─ Context
│   └─ Security Integration
│
├─ Execution Control
│   ├─ TCF
│   ├─ Dispatcher
│   ├─ Worker
│   ├─ Timeout
│   └─ Transaction
│
└─ Cross-cutting
    ├─ Error
    ├─ Logging
    └─ Response
```

이 그림의 핵심은 **책임을 기술 역할과 장애경계로 변환하는 단계**입니다. Framework Runtime Capability을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Logical Architecture는 제품을 고르는 단계가 아니라 책임·Scale·Failure를 결정하는 단계입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Application, Build Module, Logical Node, Physical Host를 같은 것으로 취급하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Data Access Capability에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 5.8 Data Access Capability

## FIG-05-10. Data Access Capability

```text
Application Runtime
       ↓
DAO
       ↓
┌────────────────────────────┐
│ Data Access Capability     │
│                            │
│ Connection Pool            │
│ SQL Mapping                │
│ JDBC Connectivity          │
└────────────┬───────────────┘
             │
             ▼
        Data Service
```

이 그림의 핵심은 **책임을 기술 역할과 장애경계로 변환하는 단계**입니다. Data Access Capability을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Logical Architecture는 제품을 고르는 단계가 아니라 책임·Scale·Failure를 결정하는 단계입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Application, Build Module, Logical Node, Physical Host를 같은 것으로 취급하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Data Service Node에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 5.9 Data Service Node

## FIG-05-11. Data Service Node

```text
┌──────────────────────────────┐
│ LTN-04 DATA SERVICE          │
│                              │
│ Database Service             │
│ Transactional Data           │
│ Query / DML                  │
│ Session / Lock / SQL Runtime │
└──────────────────────────────┘
```

이 그림의 핵심은 **책임을 기술 역할과 장애경계로 변환하는 단계**입니다. Data Service Node을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Logical Architecture는 제품을 고르는 단계가 아니라 책임·Scale·Failure를 결정하는 단계입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Application, Build Module, Logical Node, Physical Host를 같은 것으로 취급하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Integration Node에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 5.10 Integration Node

## FIG-05-12. Integration Node

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

이 그림의 핵심은 **책임을 기술 역할과 장애경계로 변환하는 단계**입니다. Integration Node을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Logical Architecture는 제품을 고르는 단계가 아니라 책임·Scale·Failure를 결정하는 단계입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Application, Build Module, Logical Node, Physical Host를 같은 것으로 취급하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Operations Node에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 5.11 Operations Node

## FIG-05-13. Operations Node

```text
Runtime Nodes
   │
   ├─ Metric
   ├─ Log
   ├─ Trace
   ├─ Health
   └─ Control
   │
   ▼
┌───────────────────────────────┐
│ LTN-06 OPERATIONS / MGMT      │
│                               │
│ Monitoring                    │
│ Logging                       │
│ Deployment Control            │
│ Inventory                     │
│ Runbook / Alert               │
│ OM                            │
└───────────────────────────────┘
```

이 그림의 핵심은 **책임을 기술 역할과 장애경계로 변환하는 단계**입니다. Operations Node을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Logical Architecture는 제품을 고르는 단계가 아니라 책임·Scale·Failure를 결정하는 단계입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Application, Build Module, Logical Node, Physical Host를 같은 것으로 취급하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“State Model에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 5.12 State Model

## FIG-05-14. State Model

```text
UI Delivery
→ mostly stateless delivery
  client state may exist

Authentication
→ token/key/refresh state may exist

Application Runtime
→ request/context/transaction state
  long-lived business state should not reside in JVM memory by default

Data Service
→ persistent state

Operations
→ monitoring/inventory/control state
```

이 그림의 핵심은 **책임을 기술 역할과 장애경계로 변환하는 단계**입니다. State Model을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Logical Architecture는 제품을 고르는 단계가 아니라 책임·Scale·Failure를 결정하는 단계입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Application, Build Module, Logical Node, Physical Host를 같은 것으로 취급하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Scale Unit에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 5.13 Scale Unit

## FIG-05-15. Scale Unit

```text
UI Delivery
   ↓
Delivery Instance

Authentication
   ↓
Auth Instance

Application Runtime
   ↓
JVM / Runtime Instance candidate

Data
   ↓
DB Node / Service according to DB architecture

Operations
   ↓
Collector / Control Instance
```

이 그림의 핵심은 **책임을 기술 역할과 장애경계로 변환하는 단계**입니다. Scale Unit을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Logical Architecture는 제품을 고르는 단계가 아니라 책임·Scale·Failure를 결정하는 단계입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Application, Build Module, Logical Node, Physical Host를 같은 것으로 취급하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Failure Domain에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 5.14 Failure Domain

## FIG-05-16. Failure Domain

```text
UI Delivery Failure
      ↓
UI Access Impact

Authentication Failure
      ↓
Login / Token Impact

Application Runtime Failure
      ↓
Business Transaction Impact

Data Service Failure
      ↓
Query / DML Impact

Operations Failure
      ↓
Monitoring / Control Impact
```

이 그림의 핵심은 **책임을 기술 역할과 장애경계로 변환하는 단계**입니다. Failure Domain을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Logical Architecture는 제품을 고르는 단계가 아니라 책임·Scale·Failure를 결정하는 단계입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Application, Build Module, Logical Node, Physical Host를 같은 것으로 취급하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Security Boundary에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 5.15 Security Boundary

## FIG-05-17. Security Boundary

```text
Untrusted Client
 ↓
Auth Boundary
 ↓
Application Boundary
 ↓
Business Authorization
 ↓
Data Boundary
```

이 그림의 핵심은 **Trust Boundary와 Identity 통제**입니다. Security Boundary을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Security는 Business Runtime 앞에서 통과해야 하는 Architecture Boundary입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. JWT Issuer/Verifier 정합성과 Principal→Business User Binding은 Critical GAP로 유지합니다.

Current Evidence 관점에서는 `05_NSIGHT_PDMG_아키텍처정의서_논리_아키텍처_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Allowed / Forbidden Path에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 5.16 Allowed / Forbidden Path

## FIG-05-18. Allowed / Forbidden Path

```text
Allowed
UI → App Runtime → Data Access → DB

Forbidden
Client → DB
UI → DB
External → DB DML
```

이 그림의 핵심은 **경계 간 연결을 Contract로 통제하는 단계**입니다. Allowed / Forbidden Path을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Source/Target, Contract, Security, Timeout, Retry, Operations까지 함께 정의해야 합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. P2P Direct DB 연결은 예외 ADR 없이 정상경로로 허용하지 않습니다.

Current Evidence 관점에서는 `05_NSIGHT_PDMG_아키텍처정의서_논리_아키텍처_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Logical → Physical Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 5.17 Logical → Physical Handoff

## FIG-05-19. Logical → Physical Handoff

```text
Logical Technical Node
= 역할 / Runtime 특성 / 실패경계 / Scale 단위

             ↓ mapping

Physical Resource
= Center / Host / VM / Appliance
```

이 그림의 핵심은 **책임을 기술 역할과 장애경계로 변환하는 단계**입니다. Logical → Physical Handoff을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Logical Architecture는 제품을 고르는 단계가 아니라 책임·Scale·Failure를 결정하는 단계입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Application, Build Module, Logical Node, Physical Host를 같은 것으로 취급하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“주안 / 대안에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 5.18 주안 / 대안

## FIG-05-20. 주안 / 대안

```text
[주안]
pdmg-service와 in-process

        VS

[대안]
pdmg-fw Remote Runtime
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 주안 / 대안을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `05_NSIGHT_PDMG_아키텍처정의서_논리_아키텍처_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“PASS / GAP에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 5.19 PASS / GAP

## FIG-05-21. PASS / GAP

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

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. PASS / GAP을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“제6장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 5.20 제6장 Handoff

## FIG-05-22. 제6장 Handoff

```text
Logical Node
   ↓
Environment
   ↓
Physical Node
   ↓
Software / Runtime
   ↓
Port / Datasource
   ↓
Artifact
   ↓
Monitoring
   ↓
Evidence
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. 제6장 Handoff을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“장 결론 / 다음 장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# Architecture Cross-check — Failure / Security / Evidence

## FIG-05-23. Failure Propagation

```text
논리 아키텍처 하위 Component Failure
 ↓
Dependency / Pool / Interface 영향
 ↓
Runtime 지연 또는 오류
 ↓
Upstream Service 영향
 ↓
Alert / Recovery / Evidence
```

이 그림에서 중요한 것은 장애가 한 Component에서 끝나지 않는다는 점입니다. Architecture는 정상경로뿐 아니라 어떻게 느려지고, 어떻게 실패하고, 어디에서 차단해야 하는지까지 설명해야 합니다.

## FIG-05-24. Security / Trust Boundary

```text
Untrusted / External
 ↓
Authentication / Validation
 ↓
Trusted Principal / Contract
 ↓
논리 아키텍처 Runtime
 ↓
Authorization / Data Access
 ↓
Audit / Evidence
```

Security 관점에서는 '접속이 가능하다'와 '신뢰된 주체가 허가된 업무를 수행한다'를 구분합니다. PDMG의 JWT Issuer/Verifier 및 Identity Binding GAP는 해당 장에서 Critical GAP로 유지합니다.

---

# 정상패턴 / 금지패턴

## FIG-05-25. Normal Pattern

```text
Application
 ↓
Capability
 ↓
Logical Node
 ↓
State / Scale / Failure / Security
 ↓
Physical Mapping
```

정상패턴에서는 각 영역이 자신의 책임을 수행하고 다음 영역과는 명확한 Contract 또는 Runtime Boundary를 통해 연결됩니다. 이렇게 해야 변경 영향과 장애영향을 제한할 수 있습니다.

## FIG-05-26. Forbidden Pattern

```text
pdmg-fw = Remote Server   X
Logical Node = Product Ver X
Application Code = Hostname X
```

반대로 금지패턴은 구현 자체가 불가능해서가 아니라 Architecture Boundary를 무너뜨리고 Source·Runtime·운영증적의 정합성을 떨어뜨리기 때문에 금지합니다. 예외가 필요하면 ADR와 Evidence로 관리합니다.

---

# Architecture Decision — 주안 / 대안 / Trade-off

## FIG-05-27. Primary vs Alternative

```text
[주안]
Logical Node 먼저 정의

        VS

[대안]
제품/서버부터 정의
```

이번 장의 주안은 **Logical Node 먼저 정의**입니다. 이 선택은 현재 PDMG Source/Runtime과 NSIGHT Target Alignment를 함께 고려했을 때 책임과 Evidence를 가장 일관되게 유지할 수 있는 방향입니다.

대안인 **제품/서버부터 정의**도 기술적으로 가능한 경우가 있습니다. 다만 대안을 선택하려면 더 나은 가용성·성능·운영성 또는 비용효과가 Runtime Test로 입증되어야 하며, 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| Architecture 정합성 | 높음 — 책임/경계 중심 | 별도 보완설계 필요 |
| Current PDMG 정합 | 현재 Evidence와 우선 정렬 | 변경범위 증가 가능 |
| 운영/장애분석 | 경계와 Trace가 명확 | 구조에 따라 복잡도 증가 |
| Evidence 조건 | 기본 Gate로 검증 | 추가 PoC/Test/ADR 필요 |

---

# Current Evidence / PASS / GAP

## FIG-05-28. Current GAP Map

```text
Current / Reference
│
├─ Integration Node Current 범위
├─ Operations Node Current 범위
├─ State/Scale/Failure 실증
└─ Logical→Physical Mapping
```

- `[GAP/OPEN]` Integration Node Current 범위
- `[GAP/OPEN]` Operations Node Current 범위
- `[GAP/OPEN]` State/Scale/Failure 실증
- `[GAP/OPEN]` Logical→Physical Mapping

## FIG-05-29. Architecture Assessment

```text
Architecture Definition
 ↓
PASS

Current PDMG Conformance
 ↓
CONDITIONAL / PARTIAL

Runtime Evidence Coverage
 ↓
MEDIUM

Architecture PASS
 ≠
Implementation PASS
```

여기서 반드시 구분해야 할 것은 Architecture Definition PASS와 Current Implementation PASS가 동일하지 않다는 점입니다. 구조와 원칙이 합리적으로 정의되어 있어도 Current Source·Deployment·Runtime이 이를 따르지 않으면 Current Conformance는 GAP 또는 PARTIAL일 수 있습니다.

## FIG-05-30. Evidence Traceability

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

최종적으로 이 장의 Architecture는 문서 안에서 끝나지 않습니다. Source와 Config, 배포 Artifact, Runtime의 ServiceId/GUID, Metric/Trace/Test를 연결해 실제로 설계가 지켜졌음을 증명해야 합니다.

---

# 이 장의 결론

## FIG-05-31. Chapter Conclusion

```text
논리 아키텍처
 ↓
전체 TEXT Architecture
 ↓
L0 → L5 Drill-down
 ↓
Runtime / Failure / Security
 ↓
Normal / Forbidden
 ↓
Decision / Evidence
 ↓
PASS
```

이 장에서 확인한 것은 **기술을 고르기 전에 무엇을 분리하고 허용할지 정한다**입니다. 중요한 것은 구성요소를 많이 나열한 것이 아니라, 전체 그림에서 시작해 책임과 경계를 나누고 Runtime과 Evidence까지 연결했다는 점입니다.

여기까지 정리하면 다음 질문이 자연스럽게 생깁니다.

> **“속도와 안정성을 실제 Center·VM·JVM·DB 구조로 구현한다”**

그 질문이 바로 다음 단계, **물리 아키텍처**의 주제입니다.


---

<!-- SOURCE CHAPTER: 06_물리_아키텍처.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제6장. 물리 아키텍처
## Story: “속도와 안정성을 실제 Center·VM·JVM·DB 구조로 구현한다”
## 발표스크립트 Story + TEXT Architecture + Top-down → Drill-down

> 작성 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_마스터프롬프트_v1.md`  
> 목차 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_상세목차_v1.md`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 주인공: **PDMG Current Architecture** / 상위 정합: **NSIGHT Target Reference**

---

# 0. 이 장의 Story

## FIG-06-00. Story Opening

```text
이전 장의 질문
 ↓
속도와 안정성을 실제 Center·VM·JVM·DB 구조로 구현한다
 ↓
전체 그림
 ↓
Top-down 해부
 ↓
Runtime / Failure / Security
 ↓
Evidence / PASS / GAP
```

# 1. 장 전체 대표 Architecture

## FIG-06-01. 물리 아키텍처 — Whole Architecture

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
WAR
 ↓
Hikari / MyBatis / JDBC
 ↓
RDW / DB
```

이 장의 핵심은 **속도와 안정성을 실제 Center·VM·JVM·DB 구조로 구현한다**라는 메시지를 하나의 Architecture 그림으로 먼저 이해하는 것입니다.

기존 문서에서는 물리 아키텍처과 관련된 기술요소가 개별 표나 구성요소로 나뉘어 보이는 경우가 많았습니다. 그러나 요소가 존재한다는 것만으로는 책임, 경계, 장애영향, 실행순서를 설명할 수 없습니다.

그래서 이번 정의서에서는 먼저 전체 그림을 보여주고, 독자가 그 그림의 방향과 경계를 이해한 뒤 L0에서 L5까지 내려가도록 구성합니다. 위에서 아래로 내려가는 이유는 세부기술이 상위 Architecture Intent를 잃지 않게 하기 위해서입니다.

여기서 중요한 원칙은 **PDMG Current와 NSIGHT Target을 분리하는 것**입니다. Source·Config·Runtime으로 확인되는 구조는 Current로 설명하고, 향후 목표나 전략은 Target 또는 Reference로 별도 표시합니다.

이것은 단순히 문서의 표현방식을 바꾸는 문제가 아닙니다. 같은 구성요소라도 Current인지 Target인지에 따라 Architecture Decision, 구현 책임, 테스트 기준이 달라지기 때문입니다.

운영 관점에서는 이 구분이 더 중요합니다. 실제 장애가 발생했을 때 어느 Component, Thread, JVM, DataSource, Interface가 영향을 받는지 추적하려면 책임과 Runtime이 연결되어 있어야 합니다.

반대로 물리 아키텍처을 제품명이나 박스 목록만으로 설명하면 그림은 단순해 보이지만 실제 변경과 장애를 설명하기 어렵습니다. 따라서 이 장에서는 정상경로뿐 아니라 금지패턴, Failure, Security, Evidence까지 함께 다룹니다.

이제 전체 그림을 기준으로 하나씩 해부해 보겠습니다. 먼저 L0에서는 이 장의 메시지를 고정하고, L1~L3에서는 책임과 Component를 나누며, L4에서는 실제 Runtime과 Failure를 보고, 마지막 L5에서는 Source·Config·Deployment·Runtime Evidence로 다시 확인합니다.

## FIG-06-02. L0 → L5 Drill-down Route

```text
L0 STORY / LANDSCAPE
"속도와 안정성을 실제 Center·VM·JVM·DB 구조로 구현한다"
 ↓
L1 RESPONSIBILITY / BOUNDARY
 ↓
L2 APPLICATION / LOGICAL / PLATFORM
 ↓
L3 COMPONENT / CONTRACT
 ↓
L4 RUNTIME / FAILURE / SECURITY
 ↓
L5 SOURCE / CONFIG / DEPLOYMENT / EVIDENCE
```

이제부터 이 대표 그림을 위에서 아래로 해부합니다. 상위 그림에서 보이는 연결을 바로 제품이나 서버로 해석하지 않고, 먼저 책임과 경계를 확인한 다음 Component와 Runtime으로 내려갑니다.

---

# 6.1 장 전체 Physical Architecture

## FIG-06-03. 장 전체 Physical Architecture

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

이 그림의 핵심은 **논리적 책임을 실제 실행자원으로 내리는 단계**입니다. 장 전체 Physical Architecture을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Server, VM, JVM, WAR를 구분하고 장애영향과 Scale Unit을 함께 봅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Hostname·Port·Version은 Inventory Evidence가 없으면 창작하지 않습니다.

Current Evidence 관점에서는 `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Logical → Physical Mapping에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 6.2 Logical → Physical Mapping

## FIG-06-04. Logical → Physical Mapping

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

이 그림의 핵심은 **책임을 기술 역할과 장애경계로 변환하는 단계**입니다. Logical → Physical Mapping을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Logical Architecture는 제품을 고르는 단계가 아니라 책임·Scale·Failure를 결정하는 단계입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Application, Build Module, Logical Node, Physical Host를 같은 것으로 취급하지 않습니다.

Current Evidence 관점에서는 `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Environment / Center에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 6.3 Environment / Center

## FIG-06-05. Environment / Center

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

이 그림의 핵심은 **논리적 책임을 실제 실행자원으로 내리는 단계**입니다. Environment / Center을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Server, VM, JVM, WAR를 구분하고 장애영향과 Scale Unit을 함께 봅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Hostname·Port·Version은 Inventory Evidence가 없으면 창작하지 않습니다.

Current Evidence 관점에서는 `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“GSLB / L4에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 6.4 GSLB / L4

## FIG-06-06. GSLB / L4

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

이 그림의 핵심은 **논리적 책임을 실제 실행자원으로 내리는 단계**입니다. GSLB / L4을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Server, VM, JVM, WAR를 구분하고 장애영향과 Scale Unit을 함께 봅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Hostname·Port·Version은 Inventory Evidence가 없으면 창작하지 않습니다.

Current Evidence 관점에서는 `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“WEB / Apache에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 6.5 WEB / Apache

## FIG-06-07. WEB / Apache

```text
GSLB
 ↓
L4
 ↓
WEB VM
 ↓
Apache Instance
 ↓
Reverse Proxy / Routing
 ↓
Tomcat Connector

Apache Instance ≠ WEB VM
```

이 그림의 핵심은 **논리적 책임을 실제 실행자원으로 내리는 단계**입니다. WEB / Apache을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Server, VM, JVM, WAR를 구분하고 장애영향과 Scale Unit을 함께 봅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Hostname·Port·Version은 Inventory Evidence가 없으면 창작하지 않습니다.

Current Evidence 관점에서는 `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“WAS / Tomcat에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 6.6 WAS / Tomcat

## FIG-06-08. WAS / Tomcat

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

이 그림의 핵심은 **논리적 책임을 실제 실행자원으로 내리는 단계**입니다. WAS / Tomcat을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Server, VM, JVM, WAR를 구분하고 장애영향과 Scale Unit을 함께 봅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Hostname·Port·Version은 Inventory Evidence가 없으면 창작하지 않습니다.

Current Evidence 관점에서는 `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Server / VM / JVM / WAR에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 6.7 Server / VM / JVM / WAR

## FIG-06-09. Server / VM / JVM / WAR

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

이 그림의 핵심은 **논리적 책임을 실제 실행자원으로 내리는 단계**입니다. Server / VM / JVM / WAR을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Server, VM, JVM, WAR를 구분하고 장애영향과 Scale Unit을 함께 봅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Hostname·Port·Version은 Inventory Evidence가 없으면 창작하지 않습니다.

Current Evidence 관점에서는 `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“JVM Group / WAR Isolation에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 6.8 JVM Group / WAR Isolation

## FIG-06-10. JVM Group / WAR Isolation

```text
Business Group A
 → JVM A

Business Group B
 → JVM B

Shared VM possible
but JVM failure domains separated
```

이 그림의 핵심은 **논리적 책임을 실제 실행자원으로 내리는 단계**입니다. JVM Group / WAR Isolation을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Server, VM, JVM, WAR를 구분하고 장애영향과 Scale Unit을 함께 봅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Hostname·Port·Version은 Inventory Evidence가 없으면 창작하지 않습니다.

Current Evidence 관점에서는 `16_PDMG_CAPACITY_PERFORMANCE_HA_DR_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Data / DB에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 6.9 Data / DB

## FIG-06-11. Data / DB

```text
PDMG JVM
  ↓ Datasource
Hikari Pool
  ↓ JDBC
DB Service
  ↓
DB Cluster / Node
  ↓
Storage

RDW = Operational / Near-real-time
ADW = Analytical / Mart [Target Reference]
```

이 그림의 핵심은 **데이터 역할·소유권·흐름을 정의하는 단계**입니다. Data / DB을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Operational/Analytical Workload, Ownership, Lineage, Security를 함께 정의합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Cross-system Direct DML은 정상패턴으로 두지 않습니다.

Current Evidence 관점에서는 `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Network / Port / Firewall에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 6.10 Network / Port / Firewall

## FIG-06-12. Network / Port / Firewall

```text
Client
 ↓
DNS/GSLB
 ↓
L4 VIP
 ↓
WEB Port
 ↓
WAS Connector Port
 ↓
DB Service Port
 ↓
Management / Backup Network

Port Inventory ↔ Firewall ↔ LB ↔ Config
```

이 그림의 핵심은 **논리적 책임을 실제 실행자원으로 내리는 단계**입니다. Network / Port / Firewall을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Server, VM, JVM, WAR를 구분하고 장애영향과 Scale Unit을 함께 봅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Hostname·Port·Version은 Inventory Evidence가 없으면 창작하지 않습니다.

Current Evidence 관점에서는 `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Storage / Filesystem에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 6.11 Storage / Filesystem

## FIG-06-13. Storage / Filesystem

```text
OS Filesystem
 ├─ App / Runtime
 ├─ Log
 ├─ Temp
 ├─ Artifact
 └─ Config

Data Storage
 ├─ DB Data
 ├─ Archive
 └─ Backup
```

이 그림의 핵심은 **논리적 책임을 실제 실행자원으로 내리는 단계**입니다. Storage / Filesystem을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Server, VM, JVM, WAR를 구분하고 장애영향과 Scale Unit을 함께 봅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Hostname·Port·Version은 Inventory Evidence가 없으면 창작하지 않습니다.

Current Evidence 관점에서는 `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Monitoring / Backup에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 6.12 Monitoring / Backup

## FIG-06-14. Monitoring / Backup

```text
WEB / WAS / JWT / DB
   ↓
Agent / Exporter / Log
   ↓
Monitoring / Logging
   ↓
Alert / Dashboard

DB / Config / File
   ↓
Backup
   ↓
Restore Test
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. Monitoring / Backup을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Capacity Candidate에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 6.13 Capacity Candidate

## FIG-06-15. Capacity Candidate

```text
Candidate A
32C/256G ×4

Candidate B
16C/128G ×8

Candidate C
16C/128G ×4 ×2 groups

≠ Production Fact
```

이 그림의 핵심은 **운영과 증적을 Architecture에 연결하는 단계**입니다. Capacity Candidate을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 로그가 존재하는 것과 Architecture Rule의 Runtime 준수 증적은 다릅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. SourceCommit→ArtifactHash→DeploymentId→ServiceId/GUID를 연결합니다.

Current Evidence 관점에서는 `06_NSIGHT_PDMG_아키텍처정의서_물리_아키텍처_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Physical Traceability에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 6.14 Physical Traceability

## FIG-06-16. Physical Traceability

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

이 그림의 핵심은 **논리적 책임을 실제 실행자원으로 내리는 단계**입니다. Physical Traceability을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Server, VM, JVM, WAR를 구분하고 장애영향과 Scale Unit을 함께 봅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Hostname·Port·Version은 Inventory Evidence가 없으면 창작하지 않습니다.

Current Evidence 관점에서는 `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“HA Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 6.15 HA Handoff

## FIG-06-17. HA Handoff

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

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. HA Handoff을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“주안 / 대안에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 6.16 주안 / 대안

## FIG-06-18. 주안 / 대안

```text
[주안]
중형 VM Scale-out + JVM/WAR 격리

        VS

[대안]
대형 VM Scale-up
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 주안 / 대안을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `06_NSIGHT_PDMG_아키텍처정의서_물리_아키텍처_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“PASS / GAP에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 6.17 PASS / GAP

## FIG-06-19. PASS / GAP

```text
PDMG Logical Technical
│
├─ ACCESS
│   └─ Traffic/Web exact logical/physical ownership
│      [OPEN]
│
├─ AUTH
│   ├─ RS256 issuer vs HMAC verifier
│   │  [CRITICAL GAP]
│   └─ Key/State HA characteristic
│      [GAP]
│
├─ APPLICATION
│   └─ TCF OFF Common Facade drift
│      [APPLICATION GAP]
│
├─ DATA
│   └─ RDW/ADW actual datasource mapping
│      [OPEN]
│
├─ INTEGRATION
│   └─ PDMG current API/Event/File inventory
│      [OPEN]
│
├─ OPERATIONS
│   └─ pdmg-om current technical role
│      [UNKNOWN]
│
├─ OBSERVABILITY
│   └─ DeploymentId/Host/JVM correlation
│      [GAP]
│
└─ PHYSICAL MAPPING
    └─ Node → Host/VM/JVM/WAR
       [GAP]
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. PASS / GAP을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“제7장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 6.18 제7장 Handoff

## FIG-06-20. 제7장 Handoff

```text
Logical Node
   ↓
Environment
   ↓
Physical Node
   ↓
Software / Runtime
   ↓
Port / Datasource
   ↓
Artifact
   ↓
Monitoring
   ↓
Evidence
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. 제7장 Handoff을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `04_PDMG_LOGICAL_TECHNICAL_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“장 결론 / 다음 장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# Architecture Cross-check — Failure / Security / Evidence

## FIG-06-21. Failure Propagation

```text
물리 아키텍처 하위 Component Failure
 ↓
Dependency / Pool / Interface 영향
 ↓
Runtime 지연 또는 오류
 ↓
Upstream Service 영향
 ↓
Alert / Recovery / Evidence
```

이 그림에서 중요한 것은 장애가 한 Component에서 끝나지 않는다는 점입니다. Architecture는 정상경로뿐 아니라 어떻게 느려지고, 어떻게 실패하고, 어디에서 차단해야 하는지까지 설명해야 합니다.

## FIG-06-22. Security / Trust Boundary

```text
Untrusted / External
 ↓
Authentication / Validation
 ↓
Trusted Principal / Contract
 ↓
물리 아키텍처 Runtime
 ↓
Authorization / Data Access
 ↓
Audit / Evidence
```

Security 관점에서는 '접속이 가능하다'와 '신뢰된 주체가 허가된 업무를 수행한다'를 구분합니다. PDMG의 JWT Issuer/Verifier 및 Identity Binding GAP는 해당 장에서 Critical GAP로 유지합니다.

---

# 정상패턴 / 금지패턴

## FIG-06-23. Normal Pattern

```text
Logical Node
 ↓
Environment / Center
 ↓
Host / VM
 ↓
JVM / WAR
 ↓
Network / DB / Storage
 ↓
Evidence
```

정상패턴에서는 각 영역이 자신의 책임을 수행하고 다음 영역과는 명확한 Contract 또는 Runtime Boundary를 통해 연결됩니다. 이렇게 해야 변경 영향과 장애영향을 제한할 수 있습니다.

## FIG-06-24. Forbidden Pattern

```text
Server = JVM = WAR          X
Candidate = Production Fact X
Port / Host / Version 추정  X
```

반대로 금지패턴은 구현 자체가 불가능해서가 아니라 Architecture Boundary를 무너뜨리고 Source·Runtime·운영증적의 정합성을 떨어뜨리기 때문에 금지합니다. 예외가 필요하면 ADR와 Evidence로 관리합니다.

---

# Architecture Decision — 주안 / 대안 / Trade-off

## FIG-06-25. Primary vs Alternative

```text
[주안]
중형 VM Scale-out + JVM/WAR Isolation

        VS

[대안]
대형 VM Scale-up 중심
```

이번 장의 주안은 **중형 VM Scale-out + JVM/WAR Isolation**입니다. 이 선택은 현재 PDMG Source/Runtime과 NSIGHT Target Alignment를 함께 고려했을 때 책임과 Evidence를 가장 일관되게 유지할 수 있는 방향입니다.

대안인 **대형 VM Scale-up 중심**도 기술적으로 가능한 경우가 있습니다. 다만 대안을 선택하려면 더 나은 가용성·성능·운영성 또는 비용효과가 Runtime Test로 입증되어야 하며, 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| Architecture 정합성 | 높음 — 책임/경계 중심 | 별도 보완설계 필요 |
| Current PDMG 정합 | 현재 Evidence와 우선 정렬 | 변경범위 증가 가능 |
| 운영/장애분석 | 경계와 Trace가 명확 | 구조에 따라 복잡도 증가 |
| Evidence 조건 | 기본 Gate로 검증 | 추가 PoC/Test/ADR 필요 |

---

# Current Evidence / PASS / GAP

## FIG-06-26. Current GAP Map

```text
Current / Reference
│
├─ Artifact→Host/JVM/WAR
├─ 실제 Host/Port/Version Inventory
├─ JVM/WAR 배치 승인
└─ RTO/RPO/Restore Evidence
```

- `[GAP/OPEN]` Artifact→Host/JVM/WAR
- `[GAP/OPEN]` 실제 Host/Port/Version Inventory
- `[GAP/OPEN]` JVM/WAR 배치 승인
- `[GAP/OPEN]` RTO/RPO/Restore Evidence

## FIG-06-27. Architecture Assessment

```text
Architecture Definition
 ↓
PASS

Current PDMG Conformance
 ↓
PARTIAL / OPEN

Runtime Evidence Coverage
 ↓
MEDIUM

Architecture PASS
 ≠
Implementation PASS
```

여기서 반드시 구분해야 할 것은 Architecture Definition PASS와 Current Implementation PASS가 동일하지 않다는 점입니다. 구조와 원칙이 합리적으로 정의되어 있어도 Current Source·Deployment·Runtime이 이를 따르지 않으면 Current Conformance는 GAP 또는 PARTIAL일 수 있습니다.

## FIG-06-28. Evidence Traceability

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

최종적으로 이 장의 Architecture는 문서 안에서 끝나지 않습니다. Source와 Config, 배포 Artifact, Runtime의 ServiceId/GUID, Metric/Trace/Test를 연결해 실제로 설계가 지켜졌음을 증명해야 합니다.

---

# 이 장의 결론

## FIG-06-29. Chapter Conclusion

```text
물리 아키텍처
 ↓
전체 TEXT Architecture
 ↓
L0 → L5 Drill-down
 ↓
Runtime / Failure / Security
 ↓
Normal / Forbidden
 ↓
Decision / Evidence
 ↓
PASS
```

이 장에서 확인한 것은 **속도와 안정성을 실제 Center·VM·JVM·DB 구조로 구현한다**입니다. 중요한 것은 구성요소를 많이 나열한 것이 아니라, 전체 그림에서 시작해 책임과 경계를 나누고 Runtime과 Evidence까지 연결했다는 점입니다.

여기까지 정리하면 다음 질문이 자연스럽게 생깁니다.

> **“완벽해 보이는 구조보다 운영 가능한 복구구조를 선택한다”**

그 질문이 바로 다음 단계, **DR 센터 활용 전략**의 주제입니다.


---

<!-- SOURCE CHAPTER: 07_DR_센터_활용_전략.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제7장. DR 센터 활용 전략
## Story: “완벽해 보이는 구조보다 운영 가능한 복구구조를 선택한다”
## 발표스크립트 Story + TEXT Architecture + Top-down → Drill-down

> 작성 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_마스터프롬프트_v1.md`  
> 목차 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_상세목차_v1.md`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 주인공: **PDMG Current Architecture** / 상위 정합: **NSIGHT Target Reference**

---

# 0. 이 장의 Story

## FIG-07-00. Story Opening

```text
이전 장의 질문
 ↓
완벽해 보이는 구조보다 운영 가능한 복구구조를 선택한다
 ↓
전체 그림
 ↓
Top-down 해부
 ↓
Runtime / Failure / Security
 ↓
Evidence / PASS / GAP
```

# 1. 장 전체 대표 Architecture

## FIG-07-01. DR 센터 활용 전략 — Whole Architecture

```text
Main Center
 ↓ failure
Detect
 ↓
Isolate
 ↓
Traffic Reroute
 ↓
DR WEB/WAS
 ↓
DR DB
 ↓
Business Validation
 ↓
Failback
```

이 장의 핵심은 **완벽해 보이는 구조보다 운영 가능한 복구구조를 선택한다**라는 메시지를 하나의 Architecture 그림으로 먼저 이해하는 것입니다.

기존 문서에서는 DR 센터 활용 전략과 관련된 기술요소가 개별 표나 구성요소로 나뉘어 보이는 경우가 많았습니다. 그러나 요소가 존재한다는 것만으로는 책임, 경계, 장애영향, 실행순서를 설명할 수 없습니다.

그래서 이번 정의서에서는 먼저 전체 그림을 보여주고, 독자가 그 그림의 방향과 경계를 이해한 뒤 L0에서 L5까지 내려가도록 구성합니다. 위에서 아래로 내려가는 이유는 세부기술이 상위 Architecture Intent를 잃지 않게 하기 위해서입니다.

여기서 중요한 원칙은 **PDMG Current와 NSIGHT Target을 분리하는 것**입니다. Source·Config·Runtime으로 확인되는 구조는 Current로 설명하고, 향후 목표나 전략은 Target 또는 Reference로 별도 표시합니다.

이것은 단순히 문서의 표현방식을 바꾸는 문제가 아닙니다. 같은 구성요소라도 Current인지 Target인지에 따라 Architecture Decision, 구현 책임, 테스트 기준이 달라지기 때문입니다.

운영 관점에서는 이 구분이 더 중요합니다. 실제 장애가 발생했을 때 어느 Component, Thread, JVM, DataSource, Interface가 영향을 받는지 추적하려면 책임과 Runtime이 연결되어 있어야 합니다.

반대로 DR 센터 활용 전략을 제품명이나 박스 목록만으로 설명하면 그림은 단순해 보이지만 실제 변경과 장애를 설명하기 어렵습니다. 따라서 이 장에서는 정상경로뿐 아니라 금지패턴, Failure, Security, Evidence까지 함께 다룹니다.

이제 전체 그림을 기준으로 하나씩 해부해 보겠습니다. 먼저 L0에서는 이 장의 메시지를 고정하고, L1~L3에서는 책임과 Component를 나누며, L4에서는 실제 Runtime과 Failure를 보고, 마지막 L5에서는 Source·Config·Deployment·Runtime Evidence로 다시 확인합니다.

## FIG-07-02. L0 → L5 Drill-down Route

```text
L0 STORY / LANDSCAPE
"완벽해 보이는 구조보다 운영 가능한 복구구조를 선택한다"
 ↓
L1 RESPONSIBILITY / BOUNDARY
 ↓
L2 APPLICATION / LOGICAL / PLATFORM
 ↓
L3 COMPONENT / CONTRACT
 ↓
L4 RUNTIME / FAILURE / SECURITY
 ↓
L5 SOURCE / CONFIG / DEPLOYMENT / EVIDENCE
```

이제부터 이 대표 그림을 위에서 아래로 해부합니다. 상위 그림에서 보이는 연결을 바로 제품이나 서버로 해석하지 않고, 먼저 책임과 경계를 확인한 다음 Component와 Runtime으로 내려갑니다.

---

# 7.1 장 전체 DR Architecture

## FIG-07-03. 장 전체 DR Architecture

```text
Network
 ↓
WEB/WAS
 ↓
Artifact/Config/Key
 ↓
DB
 ↓
Interface
 ↓
Business Transaction
 ↓
Evidence
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. 장 전체 DR Architecture을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `07_NSIGHT_PDMG_아키텍처정의서_DR_센터_활용_전략_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“HA와 DR의 차이에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 7.2 HA와 DR의 차이

## FIG-07-04. HA와 DR의 차이

```text
Local HA
= Node / Process Failure 대응

DR
= Center / Site Disaster 대응
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. HA와 DR의 차이을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `07_NSIGHT_PDMG_아키텍처정의서_DR_센터_활용_전략_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Main Center Local HA에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 7.3 Main Center Local HA

## FIG-07-05. Main Center Local HA

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

이 그림의 핵심은 **논리적 책임을 실제 실행자원으로 내리는 단계**입니다. Main Center Local HA을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Server, VM, JVM, WAR를 구분하고 장애영향과 Scale Unit을 함께 봅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Hostname·Port·Version은 Inventory Evidence가 없으면 창작하지 않습니다.

Current Evidence 관점에서는 `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Center Failure에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 7.4 Center Failure

## FIG-07-06. Center Failure

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

이 그림의 핵심은 **논리적 책임을 실제 실행자원으로 내리는 단계**입니다. Center Failure을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Server, VM, JVM, WAR를 구분하고 장애영향과 Scale Unit을 함께 봅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Hostname·Port·Version은 Inventory Evidence가 없으면 창작하지 않습니다.

Current Evidence 관점에서는 `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Detection / Isolation에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 7.5 Detection / Isolation

## FIG-07-07. Detection / Isolation

```text
Architecture / Config Baseline
 ↓ compare
Actual Source / Deployment / Runtime
 ↓
Drift
 ↓
GAP / ADR / Fix
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Detection / Isolation을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Traffic Reroute에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 7.6 Traffic Reroute

## FIG-07-08. Traffic Reroute

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

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Traffic Reroute을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“DR WEB / WAS에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 7.7 DR WEB / WAS

## FIG-07-09. DR WEB / WAS

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

이 그림의 핵심은 **논리적 책임을 실제 실행자원으로 내리는 단계**입니다. DR WEB / WAS을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Server, VM, JVM, WAR를 구분하고 장애영향과 Scale Unit을 함께 봅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Hostname·Port·Version은 Inventory Evidence가 없으면 창작하지 않습니다.

Current Evidence 관점에서는 `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Artifact / Config / Key에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 7.8 Artifact / Config / Key

## FIG-07-10. Artifact / Config / Key

```text
Artifact
= code/binary

Environment Config
= external

Secret / Key
= protected store

Never bundle generic secret
```

이 그림의 핵심은 **Trust Boundary와 Identity 통제**입니다. Artifact / Config / Key을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Security는 Business Runtime 앞에서 통과해야 하는 Architecture Boundary입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. JWT Issuer/Verifier 정합성과 Principal→Business User Binding은 Critical GAP로 유지합니다.

Current Evidence 관점에서는 `14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“DB Consistency에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 7.9 DB Consistency

## FIG-07-11. DB Consistency

```text
PDMG JVM
  ↓ Datasource
Hikari Pool
  ↓ JDBC
DB Service
  ↓
DB Cluster / Node
  ↓
Storage

RDW = Operational / Near-real-time
ADW = Analytical / Mart [Target Reference]
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. DB Consistency을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“AP Active-Active vs DB Active-Active에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 7.10 AP Active-Active vs DB Active-Active

## FIG-07-12. AP Active-Active vs DB Active-Active

```text
AP Active-Active
= request availability

DB Active-Active
= data write consistency problem

둘은 같은 결정이 아니다
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. AP Active-Active vs DB Active-Active을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `07_NSIGHT_PDMG_아키텍처정의서_DR_센터_활용_전략_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“RTO / RPO에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 7.11 RTO / RPO

## FIG-07-13. RTO / RPO

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

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. RTO / RPO을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `16_PDMG_CAPACITY_PERFORMANCE_HA_DR_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Backup / Restore에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 7.12 Backup / Restore

## FIG-07-14. Backup / Restore

```text
Backup Job
 ↓
Retention
 ↓
Restore Drill
 ↓
Data Consistency
 ↓
Application Validation
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. Backup / Restore을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Failover에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 7.13 Failover

## FIG-07-15. Failover

```text
Main → DR
 Failover
    ↓
Business on DR
    ↓
Main Recovery
    ↓
Data/Config Re-sync
    ↓
Failback
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. Failover을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `07_NSIGHT_PDMG_아키텍처정의서_DR_센터_활용_전략_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Failback에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 7.14 Failback

## FIG-07-16. Failback

```text
Main → DR
 Failover
    ↓
Business on DR
    ↓
Main Recovery
    ↓
Data/Config Re-sync
    ↓
Failback
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. Failback을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `07_NSIGHT_PDMG_아키텍처정의서_DR_센터_활용_전략_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Business Validation에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 7.15 Business Validation

## FIG-07-17. Business Validation

```text
6,000 branches
 × 6 users
 = 36,000 users

Concurrency assumption
= 10% candidate

p95 target
= 3s working target
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Business Validation을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `16_PDMG_CAPACITY_PERFORMANCE_HA_DR_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“DR Test에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 7.16 DR Test

## FIG-07-18. DR Test

```text
Network
 ↓
WEB/WAS
 ↓
Artifact/Config/Key
 ↓
DB
 ↓
Interface
 ↓
Business Transaction
 ↓
Evidence
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. DR Test을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `07_NSIGHT_PDMG_아키텍처정의서_DR_센터_활용_전략_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“주안 / 대안에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 7.17 주안 / 대안

## FIG-07-19. 주안 / 대안

```text
[주안]
AP Active-Active/N+1 + DB 정합성 우선 DR

        VS

[대안]
DB 양방향 Active-Active
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 주안 / 대안을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `07_NSIGHT_PDMG_아키텍처정의서_DR_센터_활용_전략_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“PASS / GAP에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 7.18 PASS / GAP

## FIG-07-20. PASS / GAP

```text
Architecture Definition
 ↓
PASS

Current Security Conformance
 ↓
GAP / CRITICAL

Target is clear
Current issuer/verifier and key lifecycle not aligned
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. PASS / GAP을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `11_PDMG_SECURITY_SSO_JWT_SESSION_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“제8장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 7.19 제8장 Handoff

## FIG-07-21. 제8장 Handoff

```text
WEB / WAS / JWT / DB
   ↓
Agent / Exporter / Log
   ↓
Monitoring / Logging
   ↓
Alert / Dashboard

DB / Config / File
   ↓
Backup
   ↓
Restore Test
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. 제8장 Handoff을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `05_PDMG_PHYSICAL_INFRASTRUCTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“장 결론 / 다음 장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# Architecture Cross-check — Failure / Security / Evidence

## FIG-07-22. Failure Propagation

```text
DR 센터 활용 전략 하위 Component Failure
 ↓
Dependency / Pool / Interface 영향
 ↓
Runtime 지연 또는 오류
 ↓
Upstream Service 영향
 ↓
Alert / Recovery / Evidence
```

이 그림에서 중요한 것은 장애가 한 Component에서 끝나지 않는다는 점입니다. Architecture는 정상경로뿐 아니라 어떻게 느려지고, 어떻게 실패하고, 어디에서 차단해야 하는지까지 설명해야 합니다.

## FIG-07-23. Security / Trust Boundary

```text
Untrusted / External
 ↓
Authentication / Validation
 ↓
Trusted Principal / Contract
 ↓
DR 센터 활용 전략 Runtime
 ↓
Authorization / Data Access
 ↓
Audit / Evidence
```

Security 관점에서는 '접속이 가능하다'와 '신뢰된 주체가 허가된 업무를 수행한다'를 구분합니다. PDMG의 JWT Issuer/Verifier 및 Identity Binding GAP는 해당 장에서 Critical GAP로 유지합니다.

---

# 정상패턴 / 금지패턴

## FIG-07-24. Normal Pattern

```text
Detect → Isolate → Reroute
→ Recover App/Config/Key/Data
→ Business Validate
→ Failback
```

정상패턴에서는 각 영역이 자신의 책임을 수행하고 다음 영역과는 명확한 Contract 또는 Runtime Boundary를 통해 연결됩니다. 이렇게 해야 변경 영향과 장애영향을 제한할 수 있습니다.

## FIG-07-25. Forbidden Pattern

```text
Backup Success = DR PASS X
DB AA 무검증              X
Failback 미검증           X
```

반대로 금지패턴은 구현 자체가 불가능해서가 아니라 Architecture Boundary를 무너뜨리고 Source·Runtime·운영증적의 정합성을 떨어뜨리기 때문에 금지합니다. 예외가 필요하면 ADR와 Evidence로 관리합니다.

---

# Architecture Decision — 주안 / 대안 / Trade-off

## FIG-07-26. Primary vs Alternative

```text
[주안]
AP Active-Active/N+1 + DB 정합성 우선 DR

        VS

[대안]
DB 양방향 Active-Active
```

이번 장의 주안은 **AP Active-Active/N+1 + DB 정합성 우선 DR**입니다. 이 선택은 현재 PDMG Source/Runtime과 NSIGHT Target Alignment를 함께 고려했을 때 책임과 Evidence를 가장 일관되게 유지할 수 있는 방향입니다.

대안인 **DB 양방향 Active-Active**도 기술적으로 가능한 경우가 있습니다. 다만 대안을 선택하려면 더 나은 가용성·성능·운영성 또는 비용효과가 Runtime Test로 입증되어야 하며, 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| Architecture 정합성 | 높음 — 책임/경계 중심 | 별도 보완설계 필요 |
| Current PDMG 정합 | 현재 Evidence와 우선 정렬 | 변경범위 증가 가능 |
| 운영/장애분석 | 경계와 Trace가 명확 | 구조에 따라 복잡도 증가 |
| Evidence 조건 | 기본 Gate로 검증 | 추가 PoC/Test/ADR 필요 |

---

# Current Evidence / PASS / GAP

## FIG-07-27. Current GAP Map

```text
Current / Reference
│
├─ RTO/RPO 최종값
├─ DR Artifact/Config/Key 동기화
├─ DB HA/DR 상세
└─ Failover/Failback Business Evidence
```

- `[GAP/OPEN]` RTO/RPO 최종값
- `[GAP/OPEN]` DR Artifact/Config/Key 동기화
- `[GAP/OPEN]` DB HA/DR 상세
- `[GAP/OPEN]` Failover/Failback Business Evidence

## FIG-07-28. Architecture Assessment

```text
Architecture Definition
 ↓
PASS

Current PDMG Conformance
 ↓
OPEN / CONDITIONAL

Runtime Evidence Coverage
 ↓
LOW-MEDIUM

Architecture PASS
 ≠
Implementation PASS
```

여기서 반드시 구분해야 할 것은 Architecture Definition PASS와 Current Implementation PASS가 동일하지 않다는 점입니다. 구조와 원칙이 합리적으로 정의되어 있어도 Current Source·Deployment·Runtime이 이를 따르지 않으면 Current Conformance는 GAP 또는 PARTIAL일 수 있습니다.

## FIG-07-29. Evidence Traceability

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

최종적으로 이 장의 Architecture는 문서 안에서 끝나지 않습니다. Source와 Config, 배포 Artifact, Runtime의 ServiceId/GUID, Metric/Trace/Test를 연결해 실제로 설계가 지켜졌음을 증명해야 합니다.

---

# 이 장의 결론

## FIG-07-30. Chapter Conclusion

```text
DR 센터 활용 전략
 ↓
전체 TEXT Architecture
 ↓
L0 → L5 Drill-down
 ↓
Runtime / Failure / Security
 ↓
Normal / Forbidden
 ↓
Decision / Evidence
 ↓
PASS
```

이 장에서 확인한 것은 **완벽해 보이는 구조보다 운영 가능한 복구구조를 선택한다**입니다. 중요한 것은 구성요소를 많이 나열한 것이 아니라, 전체 그림에서 시작해 책임과 경계를 나누고 Runtime과 Evidence까지 연결했다는 점입니다.

여기까지 정리하면 다음 질문이 자연스럽게 생깁니다.

> **“시스템은 서버가 아니라 표준과 실행규칙으로 움직인다”**

그 질문이 바로 다음 단계, **메커니즘**의 주제입니다.


---

<!-- SOURCE CHAPTER: 08_메커니즘.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제8장. 메커니즘
## Story: “시스템은 서버가 아니라 표준과 실행규칙으로 움직인다”
## 발표스크립트 Story + TEXT Architecture + Top-down → Drill-down

> 작성 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_마스터프롬프트_v1.md`  
> 목차 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_상세목차_v1.md`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 주인공: **PDMG Current Architecture** / 상위 정합: **NSIGHT Target Reference**

---

# 0. 이 장의 Story

## FIG-08-00. Story Opening

```text
이전 장의 질문
 ↓
시스템은 서버가 아니라 표준과 실행규칙으로 움직인다
 ↓
전체 그림
 ↓
Top-down 해부
 ↓
Runtime / Failure / Security
 ↓
Evidence / PASS / GAP
```

# 1. 장 전체 대표 Architecture

## FIG-08-01. 메커니즘 — Whole Architecture

```text
HTTP
 ↓
Filter
 ↓
Security
 ↓
MVC
 ↓
TCF
 ↓
Worker / Timeout
 ↓
Transaction
 ↓
Handler
 ↓
Facade / Service
 ↓
DAO / DB
 ↓
Response / Error / Evidence
```

이 장의 핵심은 **시스템은 서버가 아니라 표준과 실행규칙으로 움직인다**라는 메시지를 하나의 Architecture 그림으로 먼저 이해하는 것입니다.

기존 문서에서는 메커니즘과 관련된 기술요소가 개별 표나 구성요소로 나뉘어 보이는 경우가 많았습니다. 그러나 요소가 존재한다는 것만으로는 책임, 경계, 장애영향, 실행순서를 설명할 수 없습니다.

그래서 이번 정의서에서는 먼저 전체 그림을 보여주고, 독자가 그 그림의 방향과 경계를 이해한 뒤 L0에서 L5까지 내려가도록 구성합니다. 위에서 아래로 내려가는 이유는 세부기술이 상위 Architecture Intent를 잃지 않게 하기 위해서입니다.

여기서 중요한 원칙은 **PDMG Current와 NSIGHT Target을 분리하는 것**입니다. Source·Config·Runtime으로 확인되는 구조는 Current로 설명하고, 향후 목표나 전략은 Target 또는 Reference로 별도 표시합니다.

이것은 단순히 문서의 표현방식을 바꾸는 문제가 아닙니다. 같은 구성요소라도 Current인지 Target인지에 따라 Architecture Decision, 구현 책임, 테스트 기준이 달라지기 때문입니다.

운영 관점에서는 이 구분이 더 중요합니다. 실제 장애가 발생했을 때 어느 Component, Thread, JVM, DataSource, Interface가 영향을 받는지 추적하려면 책임과 Runtime이 연결되어 있어야 합니다.

반대로 메커니즘을 제품명이나 박스 목록만으로 설명하면 그림은 단순해 보이지만 실제 변경과 장애를 설명하기 어렵습니다. 따라서 이 장에서는 정상경로뿐 아니라 금지패턴, Failure, Security, Evidence까지 함께 다룹니다.

이제 전체 그림을 기준으로 하나씩 해부해 보겠습니다. 먼저 L0에서는 이 장의 메시지를 고정하고, L1~L3에서는 책임과 Component를 나누며, L4에서는 실제 Runtime과 Failure를 보고, 마지막 L5에서는 Source·Config·Deployment·Runtime Evidence로 다시 확인합니다.

## FIG-08-02. L0 → L5 Drill-down Route

```text
L0 STORY / LANDSCAPE
"시스템은 서버가 아니라 표준과 실행규칙으로 움직인다"
 ↓
L1 RESPONSIBILITY / BOUNDARY
 ↓
L2 APPLICATION / LOGICAL / PLATFORM
 ↓
L3 COMPONENT / CONTRACT
 ↓
L4 RUNTIME / FAILURE / SECURITY
 ↓
L5 SOURCE / CONFIG / DEPLOYMENT / EVIDENCE
```

이제부터 이 대표 그림을 위에서 아래로 해부합니다. 상위 그림에서 보이는 연결을 바로 제품이나 서버로 해석하지 않고, 먼저 책임과 경계를 확인한 다음 Component와 Runtime으로 내려갑니다.

---

# 8.1 장 전체 Mechanism Architecture

## FIG-08-03. 장 전체 Mechanism Architecture

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
Controller
 ↓
TcfFacade
 ↓
TimeoutExecutor
 ↓
Dispatcher
 ↓
Handler
 ↓
Business
 ↓
Response/Error/Log
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. 장 전체 Mechanism Architecture을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `08_NSIGHT_PDMG_아키텍처정의서_메커니즘_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Framework vs Business에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.2 Framework vs Business

## FIG-08-04. Framework vs Business

```text
Framework
= How to execute safely

Business
= What business work to execute

Framework → Handler Contract → Business
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Framework vs Business을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“DefaultFilter에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.3 DefaultFilter

## FIG-08-05. DefaultFilter

```text
Request
 ↓
Body Cache
 ↓
Header / GUID
 ↓
ServiceContext
 ↓
MDC
 ↓
FilterChain
 ↓
finally / clear
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. DefaultFilter을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `manual/current-baseline`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Header / GUID에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.4 Header / GUID

## FIG-08-06. Header / GUID

```text
Request Header
 ↓
DefaultFilter
 GUID / source context
 ↓
Interceptor
 enrichment
 ↓
Controller
 ServiceId finalization
 ↓
ServiceContext
 ↓
Response Header
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Header / GUID을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `12_PDMG_MESSAGE_CONTEXT_ERROR_LOGGING_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“ServiceContext에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.5 ServiceContext

## FIG-08-07. ServiceContext

```text
Request Thread
 ↓ create
ServiceContext
 ↓ enrich
Worker capture/install
 ↓ Business
 ↓ responseBody
 ↓ afterCompletion
 ↓ clear/remove
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. ServiceContext을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `manual/current-baseline`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“SecurityFilterChain에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.6 SecurityFilterChain

## FIG-08-08. SecurityFilterChain

```text
DefaultFilter
 ↓
SecurityFilterChain
 ↓
JWT Verification
 ↓
Trusted Principal?
 ↓
DispatcherServlet
```

이 그림의 핵심은 **Trust Boundary와 Identity 통제**입니다. SecurityFilterChain을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Security는 Business Runtime 앞에서 통과해야 하는 Architecture Boundary입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. JWT Issuer/Verifier 정합성과 Principal→Business User Binding은 Critical GAP로 유지합니다.

Current Evidence 관점에서는 `manual/current-baseline`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“DispatcherServlet / Interceptor에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.7 DispatcherServlet / Interceptor

## FIG-08-09. DispatcherServlet / Interceptor

```text
DispatcherServlet
 ↓
HandlerMapping
 ↓
ServicePreventionInterceptor.preHandle
 ↓
Controller
 ↓
afterCompletion
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. DispatcherServlet / Interceptor을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“ServiceId Resolution에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.8 ServiceId Resolution

## FIG-08-10. ServiceId Resolution

```text
ServiceContext Header
 ↓ precedence
Request Header
 ↓
Path Variable
 ↓
ServiceId / null

Mismatch Reject
= [GAP / PROPOSED]
```

이 그림의 핵심은 **식별과 Traceability의 기준축**입니다. ServiceId Resolution을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. ServiceId/Program/Mapper 식별은 Source와 Runtime Evidence, 변경영향을 연결합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. 서로 다른 의미의 ID를 하나로 합치거나 자동 정규화하지 않습니다.

Current Evidence 관점에서는 `manual/current-baseline`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“TCF에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.9 TCF

## FIG-08-11. TCF

```text
HTTP
 ↓
Filter / Security / MVC
 ↓
Business Controller
 ↓
Facade / Service
 ↓
DAO

TCF / TimeoutExecutor
= not applied by OFF automatically
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. TCF을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“TransactionDispatcher에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.10 TransactionDispatcher

## FIG-08-12. TransactionDispatcher

```text
HTTP
 ↓
OnlineTransactionController
 ↓
TcfFacade
 ↓
TransactionDispatcher
 ↓ serviceId lookup
TransactionHandler
 ↓ serviceId branch
Business Facade
 ↓
Service
 ↓
DAO
```

이 그림의 핵심은 **Thread·Transaction·Deadline 경계**입니다. TransactionDispatcher을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Request Thread의 HTTP 수명과 Worker/DB Transaction의 수명은 동일하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. HTTP 504, Future cancel, JDBC cancel, DB rollback을 동일한 사건으로 해석하지 않습니다.

Current Evidence 관점에서는 `03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Handler Registry에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.11 Handler Registry

## FIG-08-13. Handler Registry

```text
Spring Beans
TransactionHandler[]
 ↓
Registry
serviceId → Handler
 ↓
Dispatch
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. Handler Registry을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Worker / Timeout에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.12 Worker / Timeout

## FIG-08-14. Worker / Timeout

```text
Request Thread
 ↓ submit
Worker Pool
 ↓
pool-size = 20
queue-capacity = 100
deadline = 5000ms
 ↓
Business Transaction

[AS-IS SNAPSHOT]
```

이 그림의 핵심은 **Thread·Transaction·Deadline 경계**입니다. Worker / Timeout을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Request Thread의 HTTP 수명과 Worker/DB Transaction의 수명은 동일하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. HTTP 504, Future cancel, JDBC cancel, DB rollback을 동일한 사건으로 해석하지 않습니다.

Current Evidence 관점에서는 `manual/current-baseline`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“TransactionTemplate에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.13 TransactionTemplate

## FIG-08-15. TransactionTemplate

```text
Worker
 ↓
TransactionTemplate BEGIN
 ↓
TransactionDispatcher
 ↓
Handler
 ↓
Facade REQUIRED
 ↓
Service / DAO
 ↓
Deadline Check
 ↓
COMMIT / ROLLBACK
```

이 그림의 핵심은 **Thread·Transaction·Deadline 경계**입니다. TransactionTemplate을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Request Thread의 HTTP 수명과 Worker/DB Transaction의 수명은 동일하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. HTTP 504, Future cancel, JDBC cancel, DB rollback을 동일한 사건으로 해석하지 않습니다.

Current Evidence 관점에서는 `manual/current-baseline`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Context Propagation에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.14 Context Propagation

## FIG-08-16. Context Propagation

```text
Request ServiceContext
 ↓ capture
Worker
 ↓ install
Business
 ↓ clear
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Context Propagation을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Error Taxonomy에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.15 Error Taxonomy

## FIG-08-17. Error Taxonomy

```text
ServiceHandlerNotFound
→ E9999 / SERVICE / 500

BizException
→ business code / BIZ / 500

OnlineTimeoutException
→ FW_TIMEOUT / COMMON / 504

OnlineOverloadException
→ FW_OVERLOADED / COMMON / 503
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Error Taxonomy을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `12_PDMG_MESSAGE_CONTEXT_ERROR_LOGGING_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Response Envelope에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.16 Response Envelope

## FIG-08-18. Response Envelope

```text
Known Exception
 ↓
GlobalExceptionHandler
 ↓
Standard Error

Filter/Security early error
 ↓
MVC bypass possible
 [GAP]
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Response Envelope을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Logging / ImageLog에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.17 Logging / ImageLog

## FIG-08-19. Logging / ImageLog

```text
GUID / ServiceId
 ↓
MDC
 ↓
ImageLog PRE/POST/EX
 ↓
Application Log
 ↓
Evidence
```

이 그림의 핵심은 **운영과 증적을 Architecture에 연결하는 단계**입니다. Logging / ImageLog을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 로그가 존재하는 것과 Architecture Rule의 Runtime 준수 증적은 다릅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. SourceCommit→ArtifactHash→DeploymentId→ServiceId/GUID를 연결합니다.

Current Evidence 관점에서는 `08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“TCF OFF에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.18 TCF OFF

## FIG-08-20. TCF OFF

```text
TCF ON
HTTP → TCF → Handler → Facade → Service

TCF OFF
HTTP → Business Controller → Facade → Service
                ↑
       [Target alignment]

Current 일부
Controller → Service Direct
        [GAP]
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. TCF OFF을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `manual/current-baseline`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Normal / Forbidden에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.19 Normal / Forbidden

## FIG-08-21. Normal / Forbidden

```text
Filter
 ↓
Security
 ↓
MVC
 ↓
TCF / Adapter
 ↓
Common Facade
 ↓
Service
 ↓
DAO
 ↓
DB
```

이 그림의 핵심은 **경계 간 연결을 Contract로 통제하는 단계**입니다. Normal / Forbidden을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Source/Target, Contract, Security, Timeout, Retry, Operations까지 함께 정의해야 합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. P2P Direct DB 연결은 예외 ADR 없이 정상경로로 허용하지 않습니다.

Current Evidence 관점에서는 `08_NSIGHT_PDMG_아키텍처정의서_메커니즘_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“주안 / 대안에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.20 주안 / 대안

## FIG-08-22. 주안 / 대안

```text
[주안]
Handler/Controller 모두 Common Facade

        VS

[대안]
OFF Controller→Service 직접
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 주안 / 대안을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `08_NSIGHT_PDMG_아키텍처정의서_메커니즘_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“PASS / GAP에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.21 PASS / GAP

## FIG-08-23. PASS / GAP

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

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. PASS / GAP을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“제9장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 8.22 제9장 Handoff

## FIG-08-24. 제9장 Handoff

```text
제8장
메커니즘
      ↓
"이 Mechanism들이 거래 한 건에서 실제로 어떤 시간순서로 실행되는가?"
      ↓
제9장
런타임 서비스
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. 제9장 Handoff을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `08_NSIGHT_PDMG_아키텍처정의서_메커니즘_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“장 결론 / 다음 장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# Architecture Cross-check — Failure / Security / Evidence

## FIG-08-25. Failure Propagation

```text
메커니즘 하위 Component Failure
 ↓
Dependency / Pool / Interface 영향
 ↓
Runtime 지연 또는 오류
 ↓
Upstream Service 영향
 ↓
Alert / Recovery / Evidence
```

이 그림에서 중요한 것은 장애가 한 Component에서 끝나지 않는다는 점입니다. Architecture는 정상경로뿐 아니라 어떻게 느려지고, 어떻게 실패하고, 어디에서 차단해야 하는지까지 설명해야 합니다.

## FIG-08-26. Security / Trust Boundary

```text
Untrusted / External
 ↓
Authentication / Validation
 ↓
Trusted Principal / Contract
 ↓
메커니즘 Runtime
 ↓
Authorization / Data Access
 ↓
Audit / Evidence
```

Security 관점에서는 '접속이 가능하다'와 '신뢰된 주체가 허가된 업무를 수행한다'를 구분합니다. PDMG의 JWT Issuer/Verifier 및 Identity Binding GAP는 해당 장에서 Critical GAP로 유지합니다.

---

# 정상패턴 / 금지패턴

## FIG-08-27. Normal Pattern

```text
Filter → Security → MVC → TCF
→ Worker/TX → Common Facade
→ Service → DAO → DB
```

정상패턴에서는 각 영역이 자신의 책임을 수행하고 다음 영역과는 명확한 Contract 또는 Runtime Boundary를 통해 연결됩니다. 이렇게 해야 변경 영향과 장애영향을 제한할 수 있습니다.

## FIG-08-28. Forbidden Pattern

```text
Handler → DAO              X
Controller → Mapper          X
Framework → Business SQL     X
TCF OFF → Common Core 우회   X
```

반대로 금지패턴은 구현 자체가 불가능해서가 아니라 Architecture Boundary를 무너뜨리고 Source·Runtime·운영증적의 정합성을 떨어뜨리기 때문에 금지합니다. 예외가 필요하면 ADR와 Evidence로 관리합니다.

---

# Architecture Decision — 주안 / 대안 / Trade-off

## FIG-08-29. Primary vs Alternative

```text
[주안]
Handler/Controller 모두 Common Facade

        VS

[대안]
TCF OFF Controller→Service Direct
```

이번 장의 주안은 **Handler/Controller 모두 Common Facade**입니다. 이 선택은 현재 PDMG Source/Runtime과 NSIGHT Target Alignment를 함께 고려했을 때 책임과 Evidence를 가장 일관되게 유지할 수 있는 방향입니다.

대안인 **TCF OFF Controller→Service Direct**도 기술적으로 가능한 경우가 있습니다. 다만 대안을 선택하려면 더 나은 가용성·성능·운영성 또는 비용효과가 Runtime Test로 입증되어야 하며, 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| Architecture 정합성 | 높음 — 책임/경계 중심 | 별도 보완설계 필요 |
| Current PDMG 정합 | 현재 Evidence와 우선 정렬 | 변경범위 증가 가능 |
| 운영/장애분석 | 경계와 Trace가 명확 | 구조에 따라 복잡도 증가 |
| Evidence 조건 | 기본 Gate로 검증 | 추가 PoC/Test/ADR 필요 |

---

# Current Evidence / PASS / GAP

## FIG-08-30. Current GAP Map

```text
Current / Reference
│
├─ Mutable Worker ServiceContext
├─ TCF OFF Business Core Drift
├─ Early Error Envelope
├─ ServiceId mismatch 방어
└─ Generic Error Coverage
```

- `[GAP/OPEN]` Mutable Worker ServiceContext
- `[GAP/OPEN]` TCF OFF Business Core Drift
- `[GAP/OPEN]` Early Error Envelope
- `[GAP/OPEN]` ServiceId mismatch 방어
- `[GAP/OPEN]` Generic Error Coverage

## FIG-08-31. Architecture Assessment

```text
Architecture Definition
 ↓
PASS

Current PDMG Conformance
 ↓
PARTIAL / GAP

Runtime Evidence Coverage
 ↓
HIGH-MEDIUM

Architecture PASS
 ≠
Implementation PASS
```

여기서 반드시 구분해야 할 것은 Architecture Definition PASS와 Current Implementation PASS가 동일하지 않다는 점입니다. 구조와 원칙이 합리적으로 정의되어 있어도 Current Source·Deployment·Runtime이 이를 따르지 않으면 Current Conformance는 GAP 또는 PARTIAL일 수 있습니다.

## FIG-08-32. Evidence Traceability

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

최종적으로 이 장의 Architecture는 문서 안에서 끝나지 않습니다. Source와 Config, 배포 Artifact, Runtime의 ServiceId/GUID, Metric/Trace/Test를 연결해 실제로 설계가 지켜졌음을 증명해야 합니다.

---

# 이 장의 결론

## FIG-08-33. Chapter Conclusion

```text
메커니즘
 ↓
전체 TEXT Architecture
 ↓
L0 → L5 Drill-down
 ↓
Runtime / Failure / Security
 ↓
Normal / Forbidden
 ↓
Decision / Evidence
 ↓
PASS
```

이 장에서 확인한 것은 **시스템은 서버가 아니라 표준과 실행규칙으로 움직인다**입니다. 중요한 것은 구성요소를 많이 나열한 것이 아니라, 전체 그림에서 시작해 책임과 경계를 나누고 Runtime과 Evidence까지 연결했다는 점입니다.

여기까지 정리하면 다음 질문이 자연스럽게 생깁니다.

> **“정적인 Architecture를 거래 한 건의 시간축으로 펼쳐본다”**

그 질문이 바로 다음 단계, **런타임 서비스**의 주제입니다.


---

<!-- SOURCE CHAPTER: 09_런타임_서비스.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제9장. 런타임 서비스
## Story: “정적인 Architecture를 거래 한 건의 시간축으로 펼쳐본다”
## 발표스크립트 Story + TEXT Architecture + Top-down → Drill-down

> 작성 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_마스터프롬프트_v1.md`  
> 목차 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_상세목차_v1.md`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 주인공: **PDMG Current Architecture** / 상위 정합: **NSIGHT Target Reference**

---

# 0. 이 장의 Story

## FIG-09-00. Story Opening

```text
이전 장의 질문
 ↓
정적인 Architecture를 거래 한 건의 시간축으로 펼쳐본다
 ↓
전체 그림
 ↓
Top-down 해부
 ↓
Runtime / Failure / Security
 ↓
Evidence / PASS / GAP
```

# 1. 장 전체 대표 Architecture

## FIG-09-01. 런타임 서비스 — Whole Architecture

```text
Request Thread
Filter
Security
MVC
Controller
Future.get
     │
     │ submit
     ▼
Worker Thread
Context
TX
Dispatcher
Handler
Facade
Service
DAO
DB
     ↓
Response / Evidence
```

이 장의 핵심은 **정적인 Architecture를 거래 한 건의 시간축으로 펼쳐본다**라는 메시지를 하나의 Architecture 그림으로 먼저 이해하는 것입니다.

기존 문서에서는 런타임 서비스과 관련된 기술요소가 개별 표나 구성요소로 나뉘어 보이는 경우가 많았습니다. 그러나 요소가 존재한다는 것만으로는 책임, 경계, 장애영향, 실행순서를 설명할 수 없습니다.

그래서 이번 정의서에서는 먼저 전체 그림을 보여주고, 독자가 그 그림의 방향과 경계를 이해한 뒤 L0에서 L5까지 내려가도록 구성합니다. 위에서 아래로 내려가는 이유는 세부기술이 상위 Architecture Intent를 잃지 않게 하기 위해서입니다.

여기서 중요한 원칙은 **PDMG Current와 NSIGHT Target을 분리하는 것**입니다. Source·Config·Runtime으로 확인되는 구조는 Current로 설명하고, 향후 목표나 전략은 Target 또는 Reference로 별도 표시합니다.

이것은 단순히 문서의 표현방식을 바꾸는 문제가 아닙니다. 같은 구성요소라도 Current인지 Target인지에 따라 Architecture Decision, 구현 책임, 테스트 기준이 달라지기 때문입니다.

운영 관점에서는 이 구분이 더 중요합니다. 실제 장애가 발생했을 때 어느 Component, Thread, JVM, DataSource, Interface가 영향을 받는지 추적하려면 책임과 Runtime이 연결되어 있어야 합니다.

반대로 런타임 서비스을 제품명이나 박스 목록만으로 설명하면 그림은 단순해 보이지만 실제 변경과 장애를 설명하기 어렵습니다. 따라서 이 장에서는 정상경로뿐 아니라 금지패턴, Failure, Security, Evidence까지 함께 다룹니다.

이제 전체 그림을 기준으로 하나씩 해부해 보겠습니다. 먼저 L0에서는 이 장의 메시지를 고정하고, L1~L3에서는 책임과 Component를 나누며, L4에서는 실제 Runtime과 Failure를 보고, 마지막 L5에서는 Source·Config·Deployment·Runtime Evidence로 다시 확인합니다.

## FIG-09-02. L0 → L5 Drill-down Route

```text
L0 STORY / LANDSCAPE
"정적인 Architecture를 거래 한 건의 시간축으로 펼쳐본다"
 ↓
L1 RESPONSIBILITY / BOUNDARY
 ↓
L2 APPLICATION / LOGICAL / PLATFORM
 ↓
L3 COMPONENT / CONTRACT
 ↓
L4 RUNTIME / FAILURE / SECURITY
 ↓
L5 SOURCE / CONFIG / DEPLOYMENT / EVIDENCE
```

이제부터 이 대표 그림을 위에서 아래로 해부합니다. 상위 그림에서 보이는 연결을 바로 제품이나 서버로 해석하지 않고, 먼저 책임과 경계를 확인한 다음 Component와 Runtime으로 내려갑니다.

---

# 9.1 End-to-End Runtime

## FIG-09-03. End-to-End Runtime

```text
Request Thread
Filter
Security
MVC
Controller
Future.get
     │
     │ submit
     ▼
Worker Thread
Context
TX
Dispatcher
Handler
Facade
Service
DAO
DB
     ↓
Response / Evidence
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. End-to-End Runtime을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `manual/current-baseline`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Request Thread에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.2 Request Thread

## FIG-09-04. Request Thread

```text
T0 Request
 ↓ Filter
 ↓ Security
 ↓ MVC
 ↓ Controller
 ↓ submit Worker
 ↓ Future.get(timeout)
 ↓ Response / Exception
 ↓ Cleanup
```

이 그림의 핵심은 **Thread·Transaction·Deadline 경계**입니다. Request Thread을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Request Thread의 HTTP 수명과 Worker/DB Transaction의 수명은 동일하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. HTTP 504, Future cancel, JDBC cancel, DB rollback을 동일한 사건으로 해석하지 않습니다.

Current Evidence 관점에서는 `09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Worker Thread에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.3 Worker Thread

## FIG-09-05. Worker Thread

```text
submit
 ↓
pdmg-online-N
 ↓
Context Install
 ↓
TX BEGIN
 ↓
Dispatch
 ↓
Business
 ↓
DB
 ↓
Deadline
 ↓
Commit/Rollback
 ↓
Context Clear
```

이 그림의 핵심은 **Thread·Transaction·Deadline 경계**입니다. Worker Thread을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Request Thread의 HTTP 수명과 Worker/DB Transaction의 수명은 동일하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. HTTP 504, Future cancel, JDBC cancel, DB rollback을 동일한 사건으로 해석하지 않습니다.

Current Evidence 관점에서는 `09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Context Install에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.4 Context Install

## FIG-09-06. Context Install

```text
Request Context
 ↓ capture
Worker install
 ↓ business
Worker clear

Current same mutable reference risk
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Context Install을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `10_PDMG_TRANSACTION_TIMEOUT_THREAD_DB_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Transaction BEGIN에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.5 Transaction BEGIN

## FIG-09-07. Transaction BEGIN

```text
Worker
 ↓
TransactionTemplate BEGIN
 ↓
Handler
 ↓
Facade REQUIRED
 ↓
Service / DAO
 ↓
Commit / Rollback
```

이 그림의 핵심은 **Thread·Transaction·Deadline 경계**입니다. Transaction BEGIN을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Request Thread의 HTTP 수명과 Worker/DB Transaction의 수명은 동일하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. HTTP 504, Future cancel, JDBC cancel, DB rollback을 동일한 사건으로 해석하지 않습니다.

Current Evidence 관점에서는 `08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“ServiceId Routing에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.6 ServiceId Routing

## FIG-09-08. ServiceId Routing

```text
Header / Context / Path
 ↓
ServiceId
 ↓
Dispatcher Registry
 ↓
Handler
 ↓
handle branch
 ↓
Facade method
```

이 그림의 핵심은 **식별과 Traceability의 기준축**입니다. ServiceId Routing을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. ServiceId/Program/Mapper 식별은 Source와 Runtime Evidence, 변경영향을 연결합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. 서로 다른 의미의 ID를 하나로 합치거나 자동 정규화하지 않습니다.

Current Evidence 관점에서는 `09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Handler / Facade / Service에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.7 Handler / Facade / Service

## FIG-09-09. Handler / Facade / Service

```text
Spring Beans
TransactionHandler[]
 ↓
Registry
serviceId → Handler
 ↓
Dispatch
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. Handler / Facade / Service을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“DAO / Mapper / JDBC에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.8 DAO / Mapper / JDBC

## FIG-09-10. DAO / Mapper / JDBC

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

이 그림의 핵심은 **식별과 Traceability의 기준축**입니다. DAO / Mapper / JDBC을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. ServiceId/Program/Mapper 식별은 Source와 Runtime Evidence, 변경영향을 연결합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. 서로 다른 의미의 ID를 하나로 합치거나 자동 정규화하지 않습니다.

Current Evidence 관점에서는 `09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“DB Session에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.9 DB Session

## FIG-09-11. DB Session

```text
Worker Thread
 ↓ borrow
Hikari Connection
 ↓
JDBC
 ↓
DB Session
 ↓
SQL

Thread count ≠ Pool size ≠ DB session count
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. DB Session을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `10_PDMG_TRANSACTION_TIMEOUT_THREAD_DB_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Success Response에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.10 Success Response

## FIG-09-12. Success Response

```text
Known Exception
 ↓
GlobalExceptionHandler
 ↓
Standard Error

Filter/Security early error
 ↓
MVC bypass possible
 [GAP]
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Success Response을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Known Error에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.11 Known Error

## FIG-09-13. Known Error

```text
Exception
 ↓
GlobalExceptionHandler
 ↓
ErrorCode / Type
 ↓
hdr_nhnis + result
 ↓
HTTP status
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Known Error을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Timeout에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.12 Timeout

## FIG-09-14. Timeout

```text
Future.get(5000ms)
 ├─ complete → Response
 └─ timeout
      ↓
   cancel(true)
      ↓
   HTTP 504

504 ≠ Worker 종료
504 ≠ JDBC cancel
504 ≠ rollback 완료
```

이 그림의 핵심은 **Thread·Transaction·Deadline 경계**입니다. Timeout을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Request Thread의 HTTP 수명과 Worker/DB Transaction의 수명은 동일하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. HTTP 504, Future cancel, JDBC cancel, DB rollback을 동일한 사건으로 해석하지 않습니다.

Current Evidence 관점에서는 `manual/current-baseline`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Overload에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.13 Overload

## FIG-09-15. Overload

```text
Worker Active 20
 ↓
Queue 100
 ↓
Saturation
 ↓
Reject
 ↓
OnlineOverloadException
 ↓
HTTP 503
```

이 그림의 핵심은 **Thread·Transaction·Deadline 경계**입니다. Overload을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Request Thread의 HTTP 수명과 Worker/DB Transaction의 수명은 동일하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. HTTP 504, Future cancel, JDBC cancel, DB rollback을 동일한 사건으로 해석하지 않습니다.

Current Evidence 관점에서는 `manual/current-baseline`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Late Worker에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.14 Late Worker

## FIG-09-16. Late Worker

```text
Request Thread
 └─ timeout / 504
        │
        │ worker may continue
        ▼
Worker Thread
 ↓
JDBC / DB
 ↓
late finish / rollback?
 ↓
Runtime Evidence required
```

이 그림의 핵심은 **Thread·Transaction·Deadline 경계**입니다. Late Worker을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Request Thread의 HTTP 수명과 Worker/DB Transaction의 수명은 동일하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. HTTP 504, Future cancel, JDBC cancel, DB rollback을 동일한 사건으로 해석하지 않습니다.

Current Evidence 관점에서는 `manual/current-baseline`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“TCF OFF Runtime에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.15 TCF OFF Runtime

## FIG-09-17. TCF OFF Runtime

```text
HTTP
 ↓
Filter
 ↓
Security
 ↓
MVC
 ↓
Business Controller
 ↓
Facade / Service
 ↓
DAO
 ↓
DB
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. TCF OFF Runtime을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“FAST / DEEP에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.16 FAST / DEEP

## FIG-09-18. FAST / DEEP

```text
제8장에서 넘어온 질문
     ↓
8장의 Mechanism을 시간축으로 펼쳐 Request/Worker/Transaction/DB/Response가 실제로 어떻게 움직이는지 보여준다.
     ↓
이 장이 답해야 할 질문
     ↓
거래 한 건의 End-to-End 실행과 FAST/DEEP 경계
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. FAST / DEEP을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `09_NSIGHT_PDMG_아키텍처정의서_런타임_서비스_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Runtime Evidence에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.17 Runtime Evidence

## FIG-09-19. Runtime Evidence

```text
Architecture Rule
 ↓
Runtime Metric / Trace / Test
 ↓
Evidence ID
 ↓
Gate
 ↓
Baseline PASS
```

이 그림의 핵심은 **운영과 증적을 Architecture에 연결하는 단계**입니다. Runtime Evidence을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 로그가 존재하는 것과 Architecture Rule의 Runtime 준수 증적은 다릅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. SourceCommit→ArtifactHash→DeploymentId→ServiceId/GUID를 연결합니다.

Current Evidence 관점에서는 `14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Failure Matrix에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.18 Failure Matrix

## FIG-09-20. Failure Matrix

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

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Failure Matrix을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `09_PDMG_ONLINE_RUNTIME_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Normal / Forbidden에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.19 Normal / Forbidden

## FIG-09-21. Normal / Forbidden

```text
Request Thread
 ↓ submit
Worker Thread
 ↓
Transaction
 ↓
Business
 ↓
DB
 ↓
Response / Evidence
```

이 그림의 핵심은 **경계 간 연결을 Contract로 통제하는 단계**입니다. Normal / Forbidden을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Source/Target, Contract, Security, Timeout, Retry, Operations까지 함께 정의해야 합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. P2P Direct DB 연결은 예외 ADR 없이 정상경로로 허용하지 않습니다.

Current Evidence 관점에서는 `09_NSIGHT_PDMG_아키텍처정의서_런타임_서비스_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“주안 / 대안에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.20 주안 / 대안

## FIG-09-22. 주안 / 대안

```text
[주안]
Request/Worker 분리 + 계층형 Timeout

        VS

[대안]
Request Thread 단일 실행
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 주안 / 대안을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `09_NSIGHT_PDMG_아키텍처정의서_런타임_서비스_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“PASS / GAP에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.21 PASS / GAP

## FIG-09-23. PASS / GAP

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

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. PASS / GAP을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `08_PDMG_FRAMEWORK_MECHANISM_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“제10장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 9.22 제10장 Handoff

## FIG-09-24. 제10장 Handoff

```text
PDMG Business
 ↓
Approved Contract
 ↓
Event / CDC / ETL / Batch / File
 ↓
Platform

PDMG business code
≠ platform implementation ownership by default
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. 제10장 Handoff을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `13_PDMG_EVENT_CDC_ETL_BATCH_FILE_CACHE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“장 결론 / 다음 장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# Architecture Cross-check — Failure / Security / Evidence

## FIG-09-25. Failure Propagation

```text
런타임 서비스 하위 Component Failure
 ↓
Dependency / Pool / Interface 영향
 ↓
Runtime 지연 또는 오류
 ↓
Upstream Service 영향
 ↓
Alert / Recovery / Evidence
```

이 그림에서 중요한 것은 장애가 한 Component에서 끝나지 않는다는 점입니다. Architecture는 정상경로뿐 아니라 어떻게 느려지고, 어떻게 실패하고, 어디에서 차단해야 하는지까지 설명해야 합니다.

## FIG-09-26. Security / Trust Boundary

```text
Untrusted / External
 ↓
Authentication / Validation
 ↓
Trusted Principal / Contract
 ↓
런타임 서비스 Runtime
 ↓
Authorization / Data Access
 ↓
Audit / Evidence
```

Security 관점에서는 '접속이 가능하다'와 '신뢰된 주체가 허가된 업무를 수행한다'를 구분합니다. PDMG의 JWT Issuer/Verifier 및 Identity Binding GAP는 해당 장에서 Critical GAP로 유지합니다.

---

# 정상패턴 / 금지패턴

## FIG-09-27. Normal Pattern

```text
Request Thread
 ↓ submit
Worker Thread
 ↓ TX
Business
 ↓ DB
 ↓ Response / Evidence
```

정상패턴에서는 각 영역이 자신의 책임을 수행하고 다음 영역과는 명확한 Contract 또는 Runtime Boundary를 통해 연결됩니다. 이렇게 해야 변경 영향과 장애영향을 제한할 수 있습니다.

## FIG-09-28. Forbidden Pattern

```text
HTTP 504 = Worker 종료  X
HTTP 504 = DB Rollback  X
Worker = DB Session     X
```

반대로 금지패턴은 구현 자체가 불가능해서가 아니라 Architecture Boundary를 무너뜨리고 Source·Runtime·운영증적의 정합성을 떨어뜨리기 때문에 금지합니다. 예외가 필요하면 ADR와 Evidence로 관리합니다.

---

# Architecture Decision — 주안 / 대안 / Trade-off

## FIG-09-29. Primary vs Alternative

```text
[주안]
Request/Worker 분리 + 계층형 Timeout

        VS

[대안]
Request Thread 단일 실행
```

이번 장의 주안은 **Request/Worker 분리 + 계층형 Timeout**입니다. 이 선택은 현재 PDMG Source/Runtime과 NSIGHT Target Alignment를 함께 고려했을 때 책임과 Evidence를 가장 일관되게 유지할 수 있는 방향입니다.

대안인 **Request Thread 단일 실행**도 기술적으로 가능한 경우가 있습니다. 다만 대안을 선택하려면 더 나은 가용성·성능·운영성 또는 비용효과가 Runtime Test로 입증되어야 하며, 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| Architecture 정합성 | 높음 — 책임/경계 중심 | 별도 보완설계 필요 |
| Current PDMG 정합 | 현재 Evidence와 우선 정렬 | 변경범위 증가 가능 |
| 운영/장애분석 | 경계와 Trace가 명확 | 구조에 따라 복잡도 증가 |
| Evidence 조건 | 기본 Gate로 검증 | 추가 PoC/Test/ADR 필요 |

---

# Current Evidence / PASS / GAP

## FIG-09-30. Current GAP Map

```text
Current / Reference
│
├─ JDBC Query Timeout/Cancel Evidence
├─ Late Worker
├─ Identity Binding
├─ Deployment/Host Correlation
└─ TCF OFF Runtime Parity
```

- `[GAP/OPEN]` JDBC Query Timeout/Cancel Evidence
- `[GAP/OPEN]` Late Worker
- `[GAP/OPEN]` Identity Binding
- `[GAP/OPEN]` Deployment/Host Correlation
- `[GAP/OPEN]` TCF OFF Runtime Parity

## FIG-09-31. Architecture Assessment

```text
Architecture Definition
 ↓
PASS

Current PDMG Conformance
 ↓
PARTIAL / CONDITIONAL

Runtime Evidence Coverage
 ↓
HIGH-MEDIUM

Architecture PASS
 ≠
Implementation PASS
```

여기서 반드시 구분해야 할 것은 Architecture Definition PASS와 Current Implementation PASS가 동일하지 않다는 점입니다. 구조와 원칙이 합리적으로 정의되어 있어도 Current Source·Deployment·Runtime이 이를 따르지 않으면 Current Conformance는 GAP 또는 PARTIAL일 수 있습니다.

## FIG-09-32. Evidence Traceability

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

최종적으로 이 장의 Architecture는 문서 안에서 끝나지 않습니다. Source와 Config, 배포 Artifact, Runtime의 ServiceId/GUID, Metric/Trace/Test를 연결해 실제로 설계가 지켜졌음을 증명해야 합니다.

---

# 이 장의 결론

## FIG-09-33. Chapter Conclusion

```text
런타임 서비스
 ↓
전체 TEXT Architecture
 ↓
L0 → L5 Drill-down
 ↓
Runtime / Failure / Security
 ↓
Normal / Forbidden
 ↓
Decision / Evidence
 ↓
PASS
```

이 장에서 확인한 것은 **정적인 Architecture를 거래 한 건의 시간축으로 펼쳐본다**입니다. 중요한 것은 구성요소를 많이 나열한 것이 아니라, 전체 그림에서 시작해 책임과 경계를 나누고 Runtime과 Evidence까지 연결했다는 점입니다.

여기까지 정리하면 다음 질문이 자연스럽게 생깁니다.

> **“RDW는 실시간을 지키고 ADW는 분석을 극대화한다”**

그 질문이 바로 다음 단계, **데이터플랫폼**의 주제입니다.


---

<!-- SOURCE CHAPTER: 10_데이터플랫폼.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제10장. 데이터플랫폼
## Story: “RDW는 실시간을 지키고 ADW는 분석을 극대화한다”
## 발표스크립트 Story + TEXT Architecture + Top-down → Drill-down

> 작성 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_마스터프롬프트_v1.md`  
> 목차 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_상세목차_v1.md`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 주인공: **PDMG Current Architecture** / 상위 정합: **NSIGHT Target Reference**

---

# 0. 이 장의 Story

## FIG-10-00. Story Opening

```text
이전 장의 질문
 ↓
RDW는 실시간을 지키고 ADW는 분석을 극대화한다
 ↓
전체 그림
 ↓
Top-down 해부
 ↓
Runtime / Failure / Security
 ↓
Evidence / PASS / GAP
```

# 1. 장 전체 대표 Architecture

## FIG-10-01. 데이터플랫폼 — Whole Architecture

```text
PDMG
 ↓
Service
 ↓
DAO
 ↓
Mapper / SqlId
 ↓
RDW

Operational
        VS
Analytical

RDW         ADW
 ↓           ↓
Online      BI / Heavy Analysis
```

이 장의 핵심은 **RDW는 실시간을 지키고 ADW는 분석을 극대화한다**라는 메시지를 하나의 Architecture 그림으로 먼저 이해하는 것입니다.

기존 문서에서는 데이터플랫폼과 관련된 기술요소가 개별 표나 구성요소로 나뉘어 보이는 경우가 많았습니다. 그러나 요소가 존재한다는 것만으로는 책임, 경계, 장애영향, 실행순서를 설명할 수 없습니다.

그래서 이번 정의서에서는 먼저 전체 그림을 보여주고, 독자가 그 그림의 방향과 경계를 이해한 뒤 L0에서 L5까지 내려가도록 구성합니다. 위에서 아래로 내려가는 이유는 세부기술이 상위 Architecture Intent를 잃지 않게 하기 위해서입니다.

여기서 중요한 원칙은 **PDMG Current와 NSIGHT Target을 분리하는 것**입니다. Source·Config·Runtime으로 확인되는 구조는 Current로 설명하고, 향후 목표나 전략은 Target 또는 Reference로 별도 표시합니다.

이것은 단순히 문서의 표현방식을 바꾸는 문제가 아닙니다. 같은 구성요소라도 Current인지 Target인지에 따라 Architecture Decision, 구현 책임, 테스트 기준이 달라지기 때문입니다.

운영 관점에서는 이 구분이 더 중요합니다. 실제 장애가 발생했을 때 어느 Component, Thread, JVM, DataSource, Interface가 영향을 받는지 추적하려면 책임과 Runtime이 연결되어 있어야 합니다.

반대로 데이터플랫폼을 제품명이나 박스 목록만으로 설명하면 그림은 단순해 보이지만 실제 변경과 장애를 설명하기 어렵습니다. 따라서 이 장에서는 정상경로뿐 아니라 금지패턴, Failure, Security, Evidence까지 함께 다룹니다.

이제 전체 그림을 기준으로 하나씩 해부해 보겠습니다. 먼저 L0에서는 이 장의 메시지를 고정하고, L1~L3에서는 책임과 Component를 나누며, L4에서는 실제 Runtime과 Failure를 보고, 마지막 L5에서는 Source·Config·Deployment·Runtime Evidence로 다시 확인합니다.

## FIG-10-02. L0 → L5 Drill-down Route

```text
L0 STORY / LANDSCAPE
"RDW는 실시간을 지키고 ADW는 분석을 극대화한다"
 ↓
L1 RESPONSIBILITY / BOUNDARY
 ↓
L2 APPLICATION / LOGICAL / PLATFORM
 ↓
L3 COMPONENT / CONTRACT
 ↓
L4 RUNTIME / FAILURE / SECURITY
 ↓
L5 SOURCE / CONFIG / DEPLOYMENT / EVIDENCE
```

이제부터 이 대표 그림을 위에서 아래로 해부합니다. 상위 그림에서 보이는 연결을 바로 제품이나 서버로 해석하지 않고, 먼저 책임과 경계를 확인한 다음 Component와 Runtime으로 내려갑니다.

---

# 10.1 장 전체 Data Platform

## FIG-10-03. 장 전체 Data Platform

```text
Data Architecture
├─ Domain / Subject
├─ Ownership
├─ Model
├─ Flow
├─ Quality
├─ Security
├─ Lifecycle
└─ Evidence

Database Architecture
= Data Architecture의 하위 구현영역
```

이 그림의 핵심은 **데이터 역할·소유권·흐름을 정의하는 단계**입니다. 장 전체 Data Platform을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Operational/Analytical Workload, Ownership, Lineage, Security를 함께 정의합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Cross-system Direct DML은 정상패턴으로 두지 않습니다.

Current Evidence 관점에서는 `07_PDMG_DATA_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Data Architecture vs Database에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.2 Data Architecture vs Database

## FIG-10-04. Data Architecture vs Database

```text
Data Architecture
├─ Domain / Subject
├─ Ownership
├─ Model
├─ Flow
├─ Quality
├─ Security
├─ Lifecycle
└─ Evidence

Database Architecture
= Data Architecture의 하위 구현영역
```

이 그림의 핵심은 **데이터 역할·소유권·흐름을 정의하는 단계**입니다. Data Architecture vs Database을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Operational/Analytical Workload, Ownership, Lineage, Security를 함께 정의합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Cross-system Direct DML은 정상패턴으로 두지 않습니다.

Current Evidence 관점에서는 `07_PDMG_DATA_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“PDMG Current Data Access에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.3 PDMG Current Data Access

## FIG-10-05. PDMG Current Data Access

```text
Handler
 ↓
Facade
 ↓
Service
 ↓
DAO
 ↓
MyBatis Mapper
 ↓
JDBC
 ↓
RDW / DB
```

이 그림의 핵심은 **현재 구현을 있는 그대로 확인하는 영역**입니다. PDMG Current Data Access을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 목표 구조를 섞지 않고 Source·Config·Runtime에서 확인되는 사실만 Current로 둡니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. 확인되지 않은 항목은 `[OPEN]` 또는 `[UNKNOWN]`으로 남겨 두는 것이 정확한 Architecture 관리입니다.

Current Evidence 관점에서는 `manual/current-baseline`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Data Ownership에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.4 Data Ownership

## FIG-10-06. Data Ownership

```text
Business Data
 ↓
Owner / Steward
 ↓
SOR / Master
 ↓
Approved Consumer
 ↓
PDMG Access
```

이 그림의 핵심은 **데이터 역할·소유권·흐름을 정의하는 단계**입니다. Data Ownership을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Operational/Analytical Workload, Ownership, Lineage, Security를 함께 정의합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Cross-system Direct DML은 정상패턴으로 두지 않습니다.

Current Evidence 관점에서는 `07_PDMG_DATA_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“RDW에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.5 RDW

## FIG-10-07. RDW

```text
PDMG Online
 ↓
DAO / Mapper
 ↓
RDW
 ↓
Operational / Near-real-time Service
```

이 그림의 핵심은 **데이터 역할·소유권·흐름을 정의하는 단계**입니다. RDW을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Operational/Analytical Workload, Ownership, Lineage, Security를 함께 정의합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Cross-system Direct DML은 정상패턴으로 두지 않습니다.

Current Evidence 관점에서는 `manual/current-baseline`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“ADW에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.6 ADW

## FIG-10-08. ADW

```text
NSIGHT Analytical Target
Source / RDW
 ↓
ETL / Data Movement
 ↓
ADW
 ↓
Mart / Heavy Analysis / BI

PDMG Current direct use
= [OPEN]
```

이 그림의 핵심은 **NSIGHT Target 또는 확장 관점**입니다. ADW을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. PDMG Current에 자동으로 존재한다고 가정하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Target 필요성과 Current 구현 사이에는 Mapping Registry, Interface Contract 또는 ADR가 필요합니다.

Evidence 관점에서 이 절의 일부는 NSIGHT Target Reference입니다. PDMG Source에서 직접 확인되지 않은 기능은 Current로 표현하지 않습니다. 그림은 `manual/current-baseline`와 상위 Target 원칙을 구분하여 사용합니다.

한 단계 더 내려가면 다음 질문은 **“Datasource / Transaction에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.7 Datasource / Transaction

## FIG-10-09. Datasource / Transaction

```text
TransactionManager
   ↓
DataSource
   ↓
Hikari
   ↓
DAO / Mapper
   ↓
DB

TX Manager ↔ DataSource
must align
```

이 그림의 핵심은 **Thread·Transaction·Deadline 경계**입니다. Datasource / Transaction을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Request Thread의 HTTP 수명과 Worker/DB Transaction의 수명은 동일하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. HTTP 504, Future cancel, JDBC cancel, DB rollback을 동일한 사건으로 해석하지 않습니다.

Current Evidence 관점에서는 `07_PDMG_DATA_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“DAO / Mapper / SqlId에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.8 DAO / Mapper / SqlId

## FIG-10-10. DAO / Mapper / SqlId

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

이 그림의 핵심은 **식별과 Traceability의 기준축**입니다. DAO / Mapper / SqlId을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. ServiceId/Program/Mapper 식별은 Source와 Runtime Evidence, 변경영향을 연결합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. 서로 다른 의미의 ID를 하나로 합치거나 자동 정규화하지 않습니다.

Current Evidence 관점에서는 `07_PDMG_DATA_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Table / View에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.9 Table / View

## FIG-10-11. Table / View

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

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Table / View을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `07_PDMG_DATA_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Read / Write Boundary에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.10 Read / Write Boundary

## FIG-10-12. Read / Write Boundary

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

이 그림의 핵심은 **경계 간 연결을 Contract로 통제하는 단계**입니다. Read / Write Boundary을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Source/Target, Contract, Security, Timeout, Retry, Operations까지 함께 정의해야 합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. P2P Direct DB 연결은 예외 ADR 없이 정상경로로 허용하지 않습니다.

Current Evidence 관점에서는 `07_PDMG_DATA_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“CDC에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.11 CDC

## FIG-10-13. CDC

```text
Source DB
 ↓ Change Capture
Trail / Queue
 ↓
Relay / Apply
 ↓
RDW / Consumer

Freshness
3s vs 30s
[CONFLICT]
```

이 그림의 핵심은 **데이터 역할·소유권·흐름을 정의하는 단계**입니다. CDC을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Operational/Analytical Workload, Ownership, Lineage, Security를 함께 정의합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Cross-system Direct DML은 정상패턴으로 두지 않습니다.

Current Evidence 관점에서는 `manual/current-baseline`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“ETL에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.12 ETL

## FIG-10-14. ETL

```text
Source Data
 ├─ Change stream → CDC → RDW
 └─ Bulk extract  → ETL → ADW

PDMG Current direct ownership
= [REFERENCE / OPEN]
```

이 그림의 핵심은 **데이터 역할·소유권·흐름을 정의하는 단계**입니다. ETL을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Operational/Analytical Workload, Ownership, Lineage, Security를 함께 정의합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Cross-system Direct DML은 정상패턴으로 두지 않습니다.

Current Evidence 관점에서는 `07_PDMG_DATA_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Metadata에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.13 Metadata

## FIG-10-15. Metadata

```text
Business Metadata
+ Technical Metadata
+ Operational Metadata
   ↓
Catalog / Lineage
```

이 그림의 핵심은 **데이터 역할·소유권·흐름을 정의하는 단계**입니다. Metadata을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Operational/Analytical Workload, Ownership, Lineage, Security를 함께 정의합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Cross-system Direct DML은 정상패턴으로 두지 않습니다.

Current Evidence 관점에서는 `07_PDMG_DATA_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Lineage에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.14 Lineage

## FIG-10-16. Lineage

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

이 그림의 핵심은 **데이터 역할·소유권·흐름을 정의하는 단계**입니다. Lineage을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Operational/Analytical Workload, Ownership, Lineage, Security를 함께 정의합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Cross-system Direct DML은 정상패턴으로 두지 않습니다.

Current Evidence 관점에서는 `07_PDMG_DATA_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Data Quality에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.15 Data Quality

## FIG-10-17. Data Quality

```text
Definition
 ↓
Validation
 ↓
Load / Update
 ↓
Monitor
 ↓
Issue
 ↓
Remediation
```

이 그림의 핵심은 **데이터 역할·소유권·흐름을 정의하는 단계**입니다. Data Quality을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Operational/Analytical Workload, Ownership, Lineage, Security를 함께 정의합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Cross-system Direct DML은 정상패턴으로 두지 않습니다.

Current Evidence 관점에서는 `07_PDMG_DATA_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Data Security에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.16 Data Security

## FIG-10-18. Data Security

```text
Classification
 ↓
Access Control
 ↓
Masking / Encryption
 ↓
Audit
 ↓
Retention / Disposal
```

이 그림의 핵심은 **Trust Boundary와 Identity 통제**입니다. Data Security을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Security는 Business Runtime 앞에서 통과해야 하는 Architecture Boundary입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. JWT Issuer/Verifier 정합성과 Principal→Business User Binding은 Critical GAP로 유지합니다.

Current Evidence 관점에서는 `07_PDMG_DATA_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Workload Isolation에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.17 Workload Isolation

## FIG-10-19. Workload Isolation

```text
Online Query
 ↓
RDW

Heavy Analytics
 ↓
ADW

Cross impact
= minimize / isolate
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Workload Isolation을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `07_PDMG_DATA_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Runtime Evidence에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.18 Runtime Evidence

## FIG-10-20. Runtime Evidence

```text
ServiceId
 ↓
SqlId
 ↓
Table
 ↓
Rows / Elapsed / Error
 ↓
GUID
 ↓
Evidence
```

이 그림의 핵심은 **운영과 증적을 Architecture에 연결하는 단계**입니다. Runtime Evidence을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 로그가 존재하는 것과 Architecture Rule의 Runtime 준수 증적은 다릅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. SourceCommit→ArtifactHash→DeploymentId→ServiceId/GUID를 연결합니다.

Current Evidence 관점에서는 `10_NSIGHT_PDMG_아키텍처정의서_데이터플랫폼_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“주안 / 대안에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.19 주안 / 대안

## FIG-10-21. 주안 / 대안

```text
[주안]
Online/Analytical Workload 분리

        VS

[대안]
RDW 중심 통합사용
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 주안 / 대안을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `10_NSIGHT_PDMG_아키텍처정의서_데이터플랫폼_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“PASS / GAP에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.20 PASS / GAP

## FIG-10-22. PASS / GAP

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. PASS / GAP을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `06_PDMG_INTERFACE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“제11장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 10.21 제11장 Handoff

## FIG-10-23. 제11장 Handoff

```text
PDMG Business
 ↓
Approved Contract
 ↓
Event / CDC / ETL / Batch / File
 ↓
Platform

PDMG business code
≠ platform implementation ownership by default
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. 제11장 Handoff을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `13_PDMG_EVENT_CDC_ETL_BATCH_FILE_CACHE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“장 결론 / 다음 장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# Architecture Cross-check — Failure / Security / Evidence

## FIG-10-24. Failure Propagation

```text
데이터플랫폼 하위 Component Failure
 ↓
Dependency / Pool / Interface 영향
 ↓
Runtime 지연 또는 오류
 ↓
Upstream Service 영향
 ↓
Alert / Recovery / Evidence
```

이 그림에서 중요한 것은 장애가 한 Component에서 끝나지 않는다는 점입니다. Architecture는 정상경로뿐 아니라 어떻게 느려지고, 어떻게 실패하고, 어디에서 차단해야 하는지까지 설명해야 합니다.

## FIG-10-25. Security / Trust Boundary

```text
Untrusted / External
 ↓
Authentication / Validation
 ↓
Trusted Principal / Contract
 ↓
데이터플랫폼 Runtime
 ↓
Authorization / Data Access
 ↓
Audit / Evidence
```

Security 관점에서는 '접속이 가능하다'와 '신뢰된 주체가 허가된 업무를 수행한다'를 구분합니다. PDMG의 JWT Issuer/Verifier 및 Identity Binding GAP는 해당 장에서 Critical GAP로 유지합니다.

---

# 정상패턴 / 금지패턴

## FIG-10-26. Normal Pattern

```text
ServiceId → DAO → Mapper → SqlId → Table
Online → RDW
Heavy Analysis → ADW
```

정상패턴에서는 각 영역이 자신의 책임을 수행하고 다음 영역과는 명확한 Contract 또는 Runtime Boundary를 통해 연결됩니다. 이렇게 해야 변경 영향과 장애영향을 제한할 수 있습니다.

## FIG-10-27. Forbidden Pattern

```text
Cross-system Direct DML X
RDW Heavy Query 무제한  X
Ownership 없는 Write    X
```

반대로 금지패턴은 구현 자체가 불가능해서가 아니라 Architecture Boundary를 무너뜨리고 Source·Runtime·운영증적의 정합성을 떨어뜨리기 때문에 금지합니다. 예외가 필요하면 ADR와 Evidence로 관리합니다.

---

# Architecture Decision — 주안 / 대안 / Trade-off

## FIG-10-28. Primary vs Alternative

```text
[주안]
RDW/ADW Workload 분리

        VS

[대안]
RDW 중심 통합사용
```

이번 장의 주안은 **RDW/ADW Workload 분리**입니다. 이 선택은 현재 PDMG Source/Runtime과 NSIGHT Target Alignment를 함께 고려했을 때 책임과 Evidence를 가장 일관되게 유지할 수 있는 방향입니다.

대안인 **RDW 중심 통합사용**도 기술적으로 가능한 경우가 있습니다. 다만 대안을 선택하려면 더 나은 가용성·성능·운영성 또는 비용효과가 Runtime Test로 입증되어야 하며, 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| Architecture 정합성 | 높음 — 책임/경계 중심 | 별도 보완설계 필요 |
| Current PDMG 정합 | 현재 Evidence와 우선 정렬 | 변경범위 증가 가능 |
| 운영/장애분석 | 경계와 Trace가 명확 | 구조에 따라 복잡도 증가 |
| Evidence 조건 | 기본 Gate로 검증 | 추가 PoC/Test/ADR 필요 |

---

# Current Evidence / PASS / GAP

## FIG-10-29. Current GAP Map

```text
Current / Reference
│
├─ RDW/ADW Datasource Mapping
├─ Table/View Ownership
├─ Lineage/DQ 자동화
└─ CDC SLA 3s vs 30s Conflict
```

- `[GAP/OPEN]` RDW/ADW Datasource Mapping
- `[GAP/OPEN]` Table/View Ownership
- `[GAP/OPEN]` Lineage/DQ 자동화
- `[GAP/OPEN]` CDC SLA 3s vs 30s Conflict

## FIG-10-30. Architecture Assessment

```text
Architecture Definition
 ↓
PASS

Current PDMG Conformance
 ↓
PARTIAL / CONDITIONAL

Runtime Evidence Coverage
 ↓
MEDIUM

Architecture PASS
 ≠
Implementation PASS
```

여기서 반드시 구분해야 할 것은 Architecture Definition PASS와 Current Implementation PASS가 동일하지 않다는 점입니다. 구조와 원칙이 합리적으로 정의되어 있어도 Current Source·Deployment·Runtime이 이를 따르지 않으면 Current Conformance는 GAP 또는 PARTIAL일 수 있습니다.

## FIG-10-31. Evidence Traceability

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

최종적으로 이 장의 Architecture는 문서 안에서 끝나지 않습니다. Source와 Config, 배포 Artifact, Runtime의 ServiceId/GUID, Metric/Trace/Test를 연결해 실제로 설계가 지켜졌음을 증명해야 합니다.

---

# 이 장의 결론

## FIG-10-32. Chapter Conclusion

```text
데이터플랫폼
 ↓
전체 TEXT Architecture
 ↓
L0 → L5 Drill-down
 ↓
Runtime / Failure / Security
 ↓
Normal / Forbidden
 ↓
Decision / Evidence
 ↓
PASS
```

이 장에서 확인한 것은 **RDW는 실시간을 지키고 ADW는 분석을 극대화한다**입니다. 중요한 것은 구성요소를 많이 나열한 것이 아니라, 전체 그림에서 시작해 책임과 경계를 나누고 Runtime과 Evidence까지 연결했다는 점입니다.

여기까지 정리하면 다음 질문이 자연스럽게 생깁니다.

> **“배치형 캠페인에서 고객 행동에 반응하는 플랫폼으로”**

그 질문이 바로 다음 단계, **마케팅플랫폼**의 주제입니다.


---

<!-- SOURCE CHAPTER: 11_마케팅플랫폼.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제11장. 마케팅플랫폼
## Story: “배치형 캠페인에서 고객 행동에 반응하는 플랫폼으로”
## 발표스크립트 Story + TEXT Architecture + Top-down → Drill-down

> 작성 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_마스터프롬프트_v1.md`  
> 목차 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_상세목차_v1.md`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 주인공: **PDMG Current Architecture** / 상위 정합: **NSIGHT Target Reference**

---

# 0. 이 장의 Story

## FIG-11-00. Story Opening

```text
이전 장의 질문
 ↓
배치형 캠페인에서 고객 행동에 반응하는 플랫폼으로
 ↓
전체 그림
 ↓
Top-down 해부
 ↓
Runtime / Failure / Security
 ↓
Evidence / PASS / GAP
```

# 1. 장 전체 대표 Architecture

## FIG-11-01. 마케팅플랫폼 — Whole Architecture

```text
Customer Action
      ↓
Marketing Application
      ↓
Program / ServiceId
      ↓
Business Runtime
      ↓
RDW / Data
      ↓
Decision / Offering

Target Extension
Event / Kafka / Real-time
```

이 장의 핵심은 **배치형 캠페인에서 고객 행동에 반응하는 플랫폼으로**라는 메시지를 하나의 Architecture 그림으로 먼저 이해하는 것입니다.

기존 문서에서는 마케팅플랫폼과 관련된 기술요소가 개별 표나 구성요소로 나뉘어 보이는 경우가 많았습니다. 그러나 요소가 존재한다는 것만으로는 책임, 경계, 장애영향, 실행순서를 설명할 수 없습니다.

그래서 이번 정의서에서는 먼저 전체 그림을 보여주고, 독자가 그 그림의 방향과 경계를 이해한 뒤 L0에서 L5까지 내려가도록 구성합니다. 위에서 아래로 내려가는 이유는 세부기술이 상위 Architecture Intent를 잃지 않게 하기 위해서입니다.

여기서 중요한 원칙은 **PDMG Current와 NSIGHT Target을 분리하는 것**입니다. Source·Config·Runtime으로 확인되는 구조는 Current로 설명하고, 향후 목표나 전략은 Target 또는 Reference로 별도 표시합니다.

이것은 단순히 문서의 표현방식을 바꾸는 문제가 아닙니다. 같은 구성요소라도 Current인지 Target인지에 따라 Architecture Decision, 구현 책임, 테스트 기준이 달라지기 때문입니다.

운영 관점에서는 이 구분이 더 중요합니다. 실제 장애가 발생했을 때 어느 Component, Thread, JVM, DataSource, Interface가 영향을 받는지 추적하려면 책임과 Runtime이 연결되어 있어야 합니다.

반대로 마케팅플랫폼을 제품명이나 박스 목록만으로 설명하면 그림은 단순해 보이지만 실제 변경과 장애를 설명하기 어렵습니다. 따라서 이 장에서는 정상경로뿐 아니라 금지패턴, Failure, Security, Evidence까지 함께 다룹니다.

이제 전체 그림을 기준으로 하나씩 해부해 보겠습니다. 먼저 L0에서는 이 장의 메시지를 고정하고, L1~L3에서는 책임과 Component를 나누며, L4에서는 실제 Runtime과 Failure를 보고, 마지막 L5에서는 Source·Config·Deployment·Runtime Evidence로 다시 확인합니다.

## FIG-11-02. L0 → L5 Drill-down Route

```text
L0 STORY / LANDSCAPE
"배치형 캠페인에서 고객 행동에 반응하는 플랫폼으로"
 ↓
L1 RESPONSIBILITY / BOUNDARY
 ↓
L2 APPLICATION / LOGICAL / PLATFORM
 ↓
L3 COMPONENT / CONTRACT
 ↓
L4 RUNTIME / FAILURE / SECURITY
 ↓
L5 SOURCE / CONFIG / DEPLOYMENT / EVIDENCE
```

이제부터 이 대표 그림을 위에서 아래로 해부합니다. 상위 그림에서 보이는 연결을 바로 제품이나 서버로 해석하지 않고, 먼저 책임과 경계를 확인한 다음 Component와 Runtime으로 내려갑니다.

---

# 11.1 장 전체 Marketing Platform

## FIG-11-03. 장 전체 Marketing Platform

```text
NSIGHT Marketing Platform
        ↓
Business / Application Scope
        ↓
PDMG Reference
UI / JWT / Framework / Service Runtime
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 장 전체 Marketing Platform을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `11_NSIGHT_PDMG_아키텍처정의서_마케팅플랫폼_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“PDMG Runtime Reference에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 11.2 PDMG Runtime Reference

## FIG-11-04. PDMG Runtime Reference

```text
Browser / UI
  ↓ HTTP/JSON
PDMG Runtime
  ↓ JDBC
RDW / DB

External API / Event / File
= Inventory Required
= [OPEN / PARTIAL]
```

이 그림의 핵심은 **현재 구현을 있는 그대로 확인하는 영역**입니다. PDMG Runtime Reference을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 목표 구조를 섞지 않고 Source·Config·Runtime에서 확인되는 사실만 Current로 둡니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. 확인되지 않은 항목은 `[OPEN]` 또는 `[UNKNOWN]`으로 남겨 두는 것이 정확한 Architecture 관리입니다.

Current Evidence 관점에서는 `06_PDMG_INTERFACE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Application / Business Code에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 11.3 Application / Business Code

## FIG-11-05. Application / Business Code

```text
Security Infrastructure
→ Identity Verification

Application
→ Business Authorization
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Application / Business Code을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Program ID에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 11.4 Program ID

## FIG-11-06. Program ID

```text
mg | co | a | 9001
2  + 2  + 1 + 4
= 9 chars

Example
mgcoa9001
```

이 그림의 핵심은 **식별과 Traceability의 기준축**입니다. Program ID을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. ServiceId/Program/Mapper 식별은 Source와 Runtime Evidence, 변경영향을 연결합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. 서로 다른 의미의 ID를 하나로 합치거나 자동 정규화하지 않습니다.

Current Evidence 관점에서는 `manual/current-baseline`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“ServiceId에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 11.5 ServiceId

## FIG-11-07. ServiceId

```text
mg | co | a | 9001 | S | 0
2  + 2  + 1 + 4    +1 +1
= 11 chars

Example
mgcoa9001S0
```

이 그림의 핵심은 **식별과 Traceability의 기준축**입니다. ServiceId을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. ServiceId/Program/Mapper 식별은 Source와 Runtime Evidence, 변경영향을 연결합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. 서로 다른 의미의 ID를 하나로 합치거나 자동 정규화하지 않습니다.

Current Evidence 관점에서는 `manual/current-baseline`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Handler / Facade / Service에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 11.6 Handler / Facade / Service

## FIG-11-08. Handler / Facade / Service

```text
Inbound Adapter
Handler / Controller
        ↓
┌────────────────────────────┐
│ Business Facade            │
│                            │
│ Use Case Entry             │
│ DTO Conversion             │
│ Service Coordination       │
│ Transaction Annotation     │
└─────────────┬──────────────┘
              ↓
          Service
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. Handler / Facade / Service을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Customer Context에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 11.7 Customer Context

## FIG-11-09. Customer Context

```text
┌──────────────── pdmg-service JVM ────────────────┐
│                                                 │
│ PdmgApplication                                 │
│ @SpringBootApplication                          │
│ scanBasePackages = "nhnis"                      │
│                                                 │
│ ┌─────────────────────┐  ┌────────────────────┐ │
│ │ nhnis.mg.*          │  │ nhnis.fw.*        │ │
│ │ Business            │  │ Framework         │ │
│ │                     │  │                    │ │
│ │ Handler             │  │ Filter            │ │
│ │ Controller          │  │ TCF               │ │
│ │ Facade              │  │ Timeout           │ │
│ │ Service             │  │ Context           │ │
│ │ DAO                 │  │ Error             │ │
│ └─────────┬───────────┘  └──────────┬─────────┘ │
│           └──────── Spring DI ───────┘           │
└─────────────────────────────────────────────────┘
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Customer Context을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“RDW / Data Access에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 11.8 RDW / Data Access

## FIG-11-10. RDW / Data Access

```text
Handler
 ↓
Facade
 ↓
Service
 ↓
DAO
 ↓
MyBatis Mapper
 ↓
JDBC
 ↓
RDW / DB
```

이 그림의 핵심은 **데이터 역할·소유권·흐름을 정의하는 단계**입니다. RDW / Data Access을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Operational/Analytical Workload, Ownership, Lineage, Security를 함께 정의합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Cross-system Direct DML은 정상패턴으로 두지 않습니다.

Current Evidence 관점에서는 `07_PDMG_DATA_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“External Interface에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 11.9 External Interface

## FIG-11-11. External Interface

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

이 그림의 핵심은 **경계 간 연결을 Contract로 통제하는 단계**입니다. External Interface을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Source/Target, Contract, Security, Timeout, Retry, Operations까지 함께 정의해야 합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. P2P Direct DB 연결은 예외 ADR 없이 정상경로로 허용하지 않습니다.

Current Evidence 관점에서는 `06_PDMG_INTERFACE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Event / Kafka Target에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 11.10 Event / Kafka Target

## FIG-11-12. Event / Kafka Target

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

이 그림의 핵심은 **NSIGHT Target 또는 확장 관점**입니다. Event / Kafka Target을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. PDMG Current에 자동으로 존재한다고 가정하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Target 필요성과 Current 구현 사이에는 Mapping Registry, Interface Contract 또는 ADR가 필요합니다.

Evidence 관점에서 이 절의 일부는 NSIGHT Target Reference입니다. PDMG Source에서 직접 확인되지 않은 기능은 Current로 표현하지 않습니다. 그림은 `18_PDMG_INTEGRATED_ARCHITECTURE_BASELINE_상세정의서_v1.md`와 상위 Target 원칙을 구분하여 사용합니다.

한 단계 더 내려가면 다음 질문은 **“Real-time Decision Target에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 11.11 Real-time Decision Target

## FIG-11-13. Real-time Decision Target

```text
Decision Question
   ↓
주안 / 대안
   ↓
장점 / 단점
   ↓
Evidence / Test
   ↓
ADR
   ↓
Baseline
```

이 그림의 핵심은 **NSIGHT Target 또는 확장 관점**입니다. Real-time Decision Target을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. PDMG Current에 자동으로 존재한다고 가정하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Target 필요성과 Current 구현 사이에는 Mapping Registry, Interface Contract 또는 ADR가 필요합니다.

Evidence 관점에서 이 절의 일부는 NSIGHT Target Reference입니다. PDMG Source에서 직접 확인되지 않은 기능은 Current로 표현하지 않습니다. 그림은 `06_PDMG_INTERFACE_상세정의서_v1.md`와 상위 Target 원칙을 구분하여 사용합니다.

한 단계 더 내려가면 다음 질문은 **“Security / Authorization에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 11.12 Security / Authorization

## FIG-11-14. Security / Authorization

```text
Verified Principal
       ↓
Inbound Adapter
       ↓
Facade
       ↓
Service
       ↓
Business Authorization
       ↓
DAO
```

이 그림의 핵심은 **Trust Boundary와 Identity 통제**입니다. Security / Authorization을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Security는 Business Runtime 앞에서 통과해야 하는 Architecture Boundary입니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. JWT Issuer/Verifier 정합성과 Principal→Business User Binding은 Critical GAP로 유지합니다.

Current Evidence 관점에서는 `03_PDMG_APPLICATION_MODULE_ARCHITECTURE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“MP ↔ mg Mapping에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 11.13 MP ↔ mg Mapping

## FIG-11-15. MP ↔ mg Mapping

```text
NSIGHT Target
MP Marketing Platform
       │
       │ Mapping Registry / ADR
       ▼
PDMG AS-IS
mg

MP ≠ mg
unless approved
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. MP ↔ mg Mapping을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `manual/current-baseline`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Runtime Evidence에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 11.14 Runtime Evidence

## FIG-11-16. Runtime Evidence

```text
Campaign / User Action
 ↓
ServiceId
 ↓
GUID
 ↓
Business Runtime
 ↓
Data / External Call
 ↓
Evidence
```

이 그림의 핵심은 **운영과 증적을 Architecture에 연결하는 단계**입니다. Runtime Evidence을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 로그가 존재하는 것과 Architecture Rule의 Runtime 준수 증적은 다릅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. SourceCommit→ArtifactHash→DeploymentId→ServiceId/GUID를 연결합니다.

Current Evidence 관점에서는 `11_NSIGHT_PDMG_아키텍처정의서_마케팅플랫폼_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Normal / Forbidden에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 11.15 Normal / Forbidden

## FIG-11-17. Normal / Forbidden

```text
NSIGHT MP
 ↓
Approved Mapping
 ↓
PDMG Program / ServiceId
 ↓
Business Runtime
 ↓
RDW / Approved Interface
```

이 그림의 핵심은 **경계 간 연결을 Contract로 통제하는 단계**입니다. Normal / Forbidden을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Source/Target, Contract, Security, Timeout, Retry, Operations까지 함께 정의해야 합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. P2P Direct DB 연결은 예외 ADR 없이 정상경로로 허용하지 않습니다.

Current Evidence 관점에서는 `11_NSIGHT_PDMG_아키텍처정의서_마케팅플랫폼_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“주안 / 대안에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 11.16 주안 / 대안

## FIG-11-18. 주안 / 대안

```text
[주안]
Mapping Registry + ADR

        VS

[대안]
`mg`를 `MP`로 일괄 치환
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 주안 / 대안을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `11_NSIGHT_PDMG_아키텍처정의서_마케팅플랫폼_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“PASS / GAP에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 11.17 PASS / GAP

## FIG-11-19. PASS / GAP

```text
Architecture Definition
 ↓
PASS

Current Security Conformance
 ↓
GAP / CRITICAL

Target is clear
Current issuer/verifier and key lifecycle not aligned
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. PASS / GAP을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `11_PDMG_SECURITY_SSO_JWT_SESSION_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“제12장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 11.18 제12장 Handoff

## FIG-11-20. 제12장 Handoff

```text
PDMG Business
 ↓
Approved Contract
 ↓
Event / CDC / ETL / Batch / File
 ↓
Platform

PDMG business code
≠ platform implementation ownership by default
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. 제12장 Handoff을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `13_PDMG_EVENT_CDC_ETL_BATCH_FILE_CACHE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“장 결론 / 다음 장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# Architecture Cross-check — Failure / Security / Evidence

## FIG-11-21. Failure Propagation

```text
마케팅플랫폼 하위 Component Failure
 ↓
Dependency / Pool / Interface 영향
 ↓
Runtime 지연 또는 오류
 ↓
Upstream Service 영향
 ↓
Alert / Recovery / Evidence
```

이 그림에서 중요한 것은 장애가 한 Component에서 끝나지 않는다는 점입니다. Architecture는 정상경로뿐 아니라 어떻게 느려지고, 어떻게 실패하고, 어디에서 차단해야 하는지까지 설명해야 합니다.

## FIG-11-22. Security / Trust Boundary

```text
Untrusted / External
 ↓
Authentication / Validation
 ↓
Trusted Principal / Contract
 ↓
마케팅플랫폼 Runtime
 ↓
Authorization / Data Access
 ↓
Audit / Evidence
```

Security 관점에서는 '접속이 가능하다'와 '신뢰된 주체가 허가된 업무를 수행한다'를 구분합니다. PDMG의 JWT Issuer/Verifier 및 Identity Binding GAP는 해당 장에서 Critical GAP로 유지합니다.

---

# 정상패턴 / 금지패턴

## FIG-11-23. Normal Pattern

```text
NSIGHT MP
 ↓ Mapping Registry
PDMG Program / ServiceId
 ↓ Business Runtime
 ↓ Data / Interface
```

정상패턴에서는 각 영역이 자신의 책임을 수행하고 다음 영역과는 명확한 Contract 또는 Runtime Boundary를 통해 연결됩니다. 이렇게 해야 변경 영향과 장애영향을 제한할 수 있습니다.

## FIG-11-24. Forbidden Pattern

```text
MP = mg 자동치환            X
Kafka Target = Current PDMG X
Marketing 전체 = service    X
```

반대로 금지패턴은 구현 자체가 불가능해서가 아니라 Architecture Boundary를 무너뜨리고 Source·Runtime·운영증적의 정합성을 떨어뜨리기 때문에 금지합니다. 예외가 필요하면 ADR와 Evidence로 관리합니다.

---

# Architecture Decision — 주안 / 대안 / Trade-off

## FIG-11-25. Primary vs Alternative

```text
[주안]
MP↔PDMG Mapping Registry + ADR

        VS

[대안]
mg를 MP로 일괄 치환
```

이번 장의 주안은 **MP↔PDMG Mapping Registry + ADR**입니다. 이 선택은 현재 PDMG Source/Runtime과 NSIGHT Target Alignment를 함께 고려했을 때 책임과 Evidence를 가장 일관되게 유지할 수 있는 방향입니다.

대안인 **mg를 MP로 일괄 치환**도 기술적으로 가능한 경우가 있습니다. 다만 대안을 선택하려면 더 나은 가용성·성능·운영성 또는 비용효과가 Runtime Test로 입증되어야 하며, 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| Architecture 정합성 | 높음 — 책임/경계 중심 | 별도 보완설계 필요 |
| Current PDMG 정합 | 현재 Evidence와 우선 정렬 | 변경범위 증가 가능 |
| 운영/장애분석 | 경계와 Trace가 명확 | 구조에 따라 복잡도 증가 |
| Evidence 조건 | 기본 Gate로 검증 | 추가 PoC/Test/ADR 필요 |

---

# Current Evidence / PASS / GAP

## FIG-11-26. Current GAP Map

```text
Current / Reference
│
├─ MP↔mg Mapping
├─ JWT/Identity Security
├─ Marketing External Interface Inventory
├─ Event/Kafka Current Ownership
└─ pdmg-om Scope
```

- `[GAP/OPEN]` MP↔mg Mapping
- `[GAP/OPEN]` JWT/Identity Security
- `[GAP/OPEN]` Marketing External Interface Inventory
- `[GAP/OPEN]` Event/Kafka Current Ownership
- `[GAP/OPEN]` pdmg-om Scope

## FIG-11-27. Architecture Assessment

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

Architecture PASS
 ≠
Implementation PASS
```

여기서 반드시 구분해야 할 것은 Architecture Definition PASS와 Current Implementation PASS가 동일하지 않다는 점입니다. 구조와 원칙이 합리적으로 정의되어 있어도 Current Source·Deployment·Runtime이 이를 따르지 않으면 Current Conformance는 GAP 또는 PARTIAL일 수 있습니다.

## FIG-11-28. Evidence Traceability

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

최종적으로 이 장의 Architecture는 문서 안에서 끝나지 않습니다. Source와 Config, 배포 Artifact, Runtime의 ServiceId/GUID, Metric/Trace/Test를 연결해 실제로 설계가 지켜졌음을 증명해야 합니다.

---

# 이 장의 결론

## FIG-11-29. Chapter Conclusion

```text
마케팅플랫폼
 ↓
전체 TEXT Architecture
 ↓
L0 → L5 Drill-down
 ↓
Runtime / Failure / Security
 ↓
Normal / Forbidden
 ↓
Decision / Evidence
 ↓
CONDITIONAL PASS
```

이 장에서 확인한 것은 **배치형 캠페인에서 고객 행동에 반응하는 플랫폼으로**입니다. 중요한 것은 구성요소를 많이 나열한 것이 아니라, 전체 그림에서 시작해 책임과 경계를 나누고 Runtime과 Evidence까지 연결했다는 점입니다.

여기까지 정리하면 다음 질문이 자연스럽게 생깁니다.

> **“데이터 플랫폼이 신뢰를 만들고 BI는 판단 속도를 높인다”**

그 질문이 바로 다음 단계, **BI 포탈**의 주제입니다.


---

<!-- SOURCE CHAPTER: 12_BI_포탈.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제12장. BI 포탈
## Story: “데이터 플랫폼이 신뢰를 만들고 BI는 판단 속도를 높인다”
## 발표스크립트 Story + TEXT Architecture + Top-down → Drill-down

> 작성 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_마스터프롬프트_v1.md`  
> 목차 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_상세목차_v1.md`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 주인공: **PDMG Current Architecture** / 상위 정합: **NSIGHT Target Reference**

---

# 0. 이 장의 Story

## FIG-12-00. Story Opening

```text
이전 장의 질문
 ↓
데이터 플랫폼이 신뢰를 만들고 BI는 판단 속도를 높인다
 ↓
전체 그림
 ↓
Top-down 해부
 ↓
Runtime / Failure / Security
 ↓
Evidence / PASS / GAP
```

# 1. 장 전체 대표 Architecture

## FIG-12-01. BI 포탈 — Whole Architecture

```text
Operational Data
      ↓
RDW
      ↓
ADW
      ↓
BI Portal
      ↓
Report / Self-BI / Analysis
```

이 장의 핵심은 **데이터 플랫폼이 신뢰를 만들고 BI는 판단 속도를 높인다**라는 메시지를 하나의 Architecture 그림으로 먼저 이해하는 것입니다.

기존 문서에서는 BI 포탈과 관련된 기술요소가 개별 표나 구성요소로 나뉘어 보이는 경우가 많았습니다. 그러나 요소가 존재한다는 것만으로는 책임, 경계, 장애영향, 실행순서를 설명할 수 없습니다.

그래서 이번 정의서에서는 먼저 전체 그림을 보여주고, 독자가 그 그림의 방향과 경계를 이해한 뒤 L0에서 L5까지 내려가도록 구성합니다. 위에서 아래로 내려가는 이유는 세부기술이 상위 Architecture Intent를 잃지 않게 하기 위해서입니다.

여기서 중요한 원칙은 **PDMG Current와 NSIGHT Target을 분리하는 것**입니다. Source·Config·Runtime으로 확인되는 구조는 Current로 설명하고, 향후 목표나 전략은 Target 또는 Reference로 별도 표시합니다.

이것은 단순히 문서의 표현방식을 바꾸는 문제가 아닙니다. 같은 구성요소라도 Current인지 Target인지에 따라 Architecture Decision, 구현 책임, 테스트 기준이 달라지기 때문입니다.

운영 관점에서는 이 구분이 더 중요합니다. 실제 장애가 발생했을 때 어느 Component, Thread, JVM, DataSource, Interface가 영향을 받는지 추적하려면 책임과 Runtime이 연결되어 있어야 합니다.

반대로 BI 포탈을 제품명이나 박스 목록만으로 설명하면 그림은 단순해 보이지만 실제 변경과 장애를 설명하기 어렵습니다. 따라서 이 장에서는 정상경로뿐 아니라 금지패턴, Failure, Security, Evidence까지 함께 다룹니다.

이제 전체 그림을 기준으로 하나씩 해부해 보겠습니다. 먼저 L0에서는 이 장의 메시지를 고정하고, L1~L3에서는 책임과 Component를 나누며, L4에서는 실제 Runtime과 Failure를 보고, 마지막 L5에서는 Source·Config·Deployment·Runtime Evidence로 다시 확인합니다.

## FIG-12-02. L0 → L5 Drill-down Route

```text
L0 STORY / LANDSCAPE
"데이터 플랫폼이 신뢰를 만들고 BI는 판단 속도를 높인다"
 ↓
L1 RESPONSIBILITY / BOUNDARY
 ↓
L2 APPLICATION / LOGICAL / PLATFORM
 ↓
L3 COMPONENT / CONTRACT
 ↓
L4 RUNTIME / FAILURE / SECURITY
 ↓
L5 SOURCE / CONFIG / DEPLOYMENT / EVIDENCE
```

이제부터 이 대표 그림을 위에서 아래로 해부합니다. 상위 그림에서 보이는 연결을 바로 제품이나 서버로 해석하지 않고, 먼저 책임과 경계를 확인한 다음 Component와 Runtime으로 내려갑니다.

---

# 12.1 장 전체 BI Architecture

## FIG-12-03. 장 전체 BI Architecture

```text
Data Architecture
├─ Domain / Subject
├─ Ownership
├─ Model
├─ Flow
├─ Quality
├─ Security
├─ Lifecycle
└─ Evidence

Database Architecture
= Data Architecture의 하위 구현영역
```

이 그림의 핵심은 **NSIGHT Target 또는 확장 관점**입니다. 장 전체 BI Architecture을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. PDMG Current에 자동으로 존재한다고 가정하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Target 필요성과 Current 구현 사이에는 Mapping Registry, Interface Contract 또는 ADR가 필요합니다.

Evidence 관점에서 이 절의 일부는 NSIGHT Target Reference입니다. PDMG Source에서 직접 확인되지 않은 기능은 Current로 표현하지 않습니다. 그림은 `07_PDMG_DATA_상세정의서_v1.md`와 상위 Target 원칙을 구분하여 사용합니다.

한 단계 더 내려가면 다음 질문은 **“BI Application Boundary에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 12.2 BI Application Boundary

## FIG-12-04. BI Application Boundary

```text
User
 ↓
Authentication
 ↓
BI Role
 ↓
Dataset Permission
 ↓
Column/Row Access
 ↓
Audit
```

이 그림의 핵심은 **NSIGHT Target 또는 확장 관점**입니다. BI Application Boundary을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. PDMG Current에 자동으로 존재한다고 가정하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Target 필요성과 Current 구현 사이에는 Mapping Registry, Interface Contract 또는 ADR가 필요합니다.

Evidence 관점에서 이 절의 일부는 NSIGHT Target Reference입니다. PDMG Source에서 직접 확인되지 않은 기능은 Current로 표현하지 않습니다. 그림은 `12_NSIGHT_PDMG_아키텍처정의서_BI_포탈_STORY_VISUAL_v1.md`와 상위 Target 원칙을 구분하여 사용합니다.

한 단계 더 내려가면 다음 질문은 **“Operational vs Analytical에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 12.3 Operational vs Analytical

## FIG-12-05. Operational vs Analytical

```text
Data Architecture
├─ Domain / Subject
├─ Ownership
├─ Model
├─ Flow
├─ Quality
├─ Security
├─ Lifecycle
└─ Evidence

Database Architecture
= Data Architecture의 하위 구현영역
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Operational vs Analytical을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `07_PDMG_DATA_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“RDW → ADW에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 12.4 RDW → ADW

## FIG-12-06. RDW → ADW

```text
RDW
= Operational / Near-real-time / Information Service

ADW
= Analytical / Aggregation / Mart / Heavy Query

PDMG Current → RDW stronger evidence
PDMG Current → ADW [OPEN]
```

이 그림의 핵심은 **NSIGHT Target 또는 확장 관점**입니다. RDW → ADW을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. PDMG Current에 자동으로 존재한다고 가정하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Target 필요성과 Current 구현 사이에는 Mapping Registry, Interface Contract 또는 ADR가 필요합니다.

Evidence 관점에서 이 절의 일부는 NSIGHT Target Reference입니다. PDMG Source에서 직접 확인되지 않은 기능은 Current로 표현하지 않습니다. 그림은 `07_PDMG_DATA_상세정의서_v1.md`와 상위 Target 원칙을 구분하여 사용합니다.

한 단계 더 내려가면 다음 질문은 **“Data Contract에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 12.5 Data Contract

## FIG-12-07. Data Contract

```text
InterfaceId / Dataset
Source
Target
Schema
Freshness
Security
Owner
Version
SLA
```

이 그림의 핵심은 **데이터 역할·소유권·흐름을 정의하는 단계**입니다. Data Contract을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Operational/Analytical Workload, Ownership, Lineage, Security를 함께 정의합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Cross-system Direct DML은 정상패턴으로 두지 않습니다.

Current Evidence 관점에서는 `12_NSIGHT_PDMG_아키텍처정의서_BI_포탈_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Dataset에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 12.6 Dataset

## FIG-12-08. Dataset

```text
Business Term
 ↓
Data Subject
 ↓
Owner / Steward
 ↓
Metric Definition
 ↓
Dataset
 ↓
BI Report
```

이 그림의 핵심은 **데이터 역할·소유권·흐름을 정의하는 단계**입니다. Dataset을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Operational/Analytical Workload, Ownership, Lineage, Security를 함께 정의합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Cross-system Direct DML은 정상패턴으로 두지 않습니다.

Current Evidence 관점에서는 `12_NSIGHT_PDMG_아키텍처정의서_BI_포탈_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Report에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 12.7 Report

## FIG-12-09. Report

```text
Operational Source
 ↓
RDW
 ↓
ADW
 ↓
BI Portal
 ↓
Report / Self-BI / Analysis
```

이 그림의 핵심은 **논리적 책임을 실제 실행자원으로 내리는 단계**입니다. Report을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Server, VM, JVM, WAR를 구분하고 장애영향과 Scale Unit을 함께 봅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Hostname·Port·Version은 Inventory Evidence가 없으면 창작하지 않습니다.

Current Evidence 관점에서는 `12_NSIGHT_PDMG_아키텍처정의서_BI_포탈_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Self-BI에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 12.8 Self-BI

## FIG-12-10. Self-BI

```text
User
 ↓
Authentication
 ↓
BI Role
 ↓
Dataset Permission
 ↓
Column/Row Access
 ↓
Audit
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Self-BI을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `12_NSIGHT_PDMG_아키텍처정의서_BI_포탈_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“AI / Natural Language Analysis에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 12.9 AI / Natural Language Analysis

## FIG-12-11. AI / Natural Language Analysis

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

이 그림의 핵심은 **NSIGHT Target 또는 확장 관점**입니다. AI / Natural Language Analysis을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. PDMG Current에 자동으로 존재한다고 가정하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Target 필요성과 Current 구현 사이에는 Mapping Registry, Interface Contract 또는 ADR가 필요합니다.

Evidence 관점에서 이 절의 일부는 NSIGHT Target Reference입니다. PDMG Source에서 직접 확인되지 않은 기능은 Current로 표현하지 않습니다. 그림은 `06_PDMG_INTERFACE_상세정의서_v1.md`와 상위 Target 원칙을 구분하여 사용합니다.

한 단계 더 내려가면 다음 질문은 **“BI Security에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 12.10 BI Security

## FIG-12-12. BI Security

```text
User
 ↓
Authentication
 ↓
BI Role
 ↓
Dataset Permission
 ↓
Column/Row Access
 ↓
Audit
```

이 그림의 핵심은 **NSIGHT Target 또는 확장 관점**입니다. BI Security을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. PDMG Current에 자동으로 존재한다고 가정하지 않습니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Target 필요성과 Current 구현 사이에는 Mapping Registry, Interface Contract 또는 ADR가 필요합니다.

Evidence 관점에서 이 절의 일부는 NSIGHT Target Reference입니다. PDMG Source에서 직접 확인되지 않은 기능은 Current로 표현하지 않습니다. 그림은 `12_NSIGHT_PDMG_아키텍처정의서_BI_포탈_STORY_VISUAL_v1.md`와 상위 Target 원칙을 구분하여 사용합니다.

한 단계 더 내려가면 다음 질문은 **“Row / Column Access에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 12.11 Row / Column Access

## FIG-12-13. Row / Column Access

```text
Handler
 ↓
Facade
 ↓
Service
 ↓
DAO
 ↓
MyBatis Mapper
 ↓
JDBC
 ↓
RDW / DB
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Row / Column Access을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `07_PDMG_DATA_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Freshness SLA에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 12.12 Freshness SLA

## FIG-12-14. Freshness SLA

```text
Source DB
  ↓ Capture
Trail / Queue
  ↓ Relay
Target Apply
  ↓
RDW / Consumer

Freshness SLA
= 30s vs 3s [CONFLICT]
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Freshness SLA을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `06_PDMG_INTERFACE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Performance Isolation에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 12.13 Performance Isolation

## FIG-12-15. Performance Isolation

```text
Online Query
 ↓
RDW

Heavy Analytics
 ↓
ADW

Cross impact
= minimize / isolate
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Performance Isolation을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `07_PDMG_DATA_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Data Governance에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 12.14 Data Governance

## FIG-12-16. Data Governance

```text
Business Term
 ↓
Data Subject
 ↓
Owner / Steward
 ↓
Metric Definition
 ↓
Dataset
 ↓
BI Report
```

이 그림의 핵심은 **데이터 역할·소유권·흐름을 정의하는 단계**입니다. Data Governance을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Operational/Analytical Workload, Ownership, Lineage, Security를 함께 정의합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Cross-system Direct DML은 정상패턴으로 두지 않습니다.

Current Evidence 관점에서는 `12_NSIGHT_PDMG_아키텍처정의서_BI_포탈_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Normal / Forbidden에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 12.15 Normal / Forbidden

## FIG-12-17. Normal / Forbidden

```text
Operational Data
 ↓
RDW
 ↓
ADW
 ↓
Approved Data Contract
 ↓
BI Portal / Self-BI
```

이 그림의 핵심은 **경계 간 연결을 Contract로 통제하는 단계**입니다. Normal / Forbidden을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Source/Target, Contract, Security, Timeout, Retry, Operations까지 함께 정의해야 합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. P2P Direct DB 연결은 예외 ADR 없이 정상경로로 허용하지 않습니다.

Current Evidence 관점에서는 `12_NSIGHT_PDMG_아키텍처정의서_BI_포탈_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“주안 / 대안에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 12.16 주안 / 대안

## FIG-12-18. 주안 / 대안

```text
[주안]
ADW / Data Contract 기반

        VS

[대안]
PDMG 내부 DB/DAO 직접 접근
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 주안 / 대안을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `12_NSIGHT_PDMG_아키텍처정의서_BI_포탈_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“PASS / GAP에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 12.17 PASS / GAP

## FIG-12-19. PASS / GAP

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. PASS / GAP을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `06_PDMG_INTERFACE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“제13장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 12.18 제13장 Handoff

## FIG-12-20. 제13장 Handoff

```text
PDMG Business
 ↓
Approved Contract
 ↓
Event / CDC / ETL / Batch / File
 ↓
Platform

PDMG business code
≠ platform implementation ownership by default
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. 제13장 Handoff을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `13_PDMG_EVENT_CDC_ETL_BATCH_FILE_CACHE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“장 결론 / 다음 장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# Architecture Cross-check — Failure / Security / Evidence

## FIG-12-21. Failure Propagation

```text
BI 포탈 하위 Component Failure
 ↓
Dependency / Pool / Interface 영향
 ↓
Runtime 지연 또는 오류
 ↓
Upstream Service 영향
 ↓
Alert / Recovery / Evidence
```

이 그림에서 중요한 것은 장애가 한 Component에서 끝나지 않는다는 점입니다. Architecture는 정상경로뿐 아니라 어떻게 느려지고, 어떻게 실패하고, 어디에서 차단해야 하는지까지 설명해야 합니다.

## FIG-12-22. Security / Trust Boundary

```text
Untrusted / External
 ↓
Authentication / Validation
 ↓
Trusted Principal / Contract
 ↓
BI 포탈 Runtime
 ↓
Authorization / Data Access
 ↓
Audit / Evidence
```

Security 관점에서는 '접속이 가능하다'와 '신뢰된 주체가 허가된 업무를 수행한다'를 구분합니다. PDMG의 JWT Issuer/Verifier 및 Identity Binding GAP는 해당 장에서 Critical GAP로 유지합니다.

---

# 정상패턴 / 금지패턴

## FIG-12-23. Normal Pattern

```text
Operational Data → RDW → ADW
→ Data Contract → BI Portal
```

정상패턴에서는 각 영역이 자신의 책임을 수행하고 다음 영역과는 명확한 Contract 또는 Runtime Boundary를 통해 연결됩니다. 이렇게 해야 변경 영향과 장애영향을 제한할 수 있습니다.

## FIG-12-24. Forbidden Pattern

```text
BI → PDMG DAO            X
BI → 내부 Table DML      X
Heavy Query → Online RDW X
```

반대로 금지패턴은 구현 자체가 불가능해서가 아니라 Architecture Boundary를 무너뜨리고 Source·Runtime·운영증적의 정합성을 떨어뜨리기 때문에 금지합니다. 예외가 필요하면 ADR와 Evidence로 관리합니다.

---

# Architecture Decision — 주안 / 대안 / Trade-off

## FIG-12-25. Primary vs Alternative

```text
[주안]
ADW/Data Contract 기반 BI 연결

        VS

[대안]
PDMG 내부 DAO/DB 직접 연결
```

이번 장의 주안은 **ADW/Data Contract 기반 BI 연결**입니다. 이 선택은 현재 PDMG Source/Runtime과 NSIGHT Target Alignment를 함께 고려했을 때 책임과 Evidence를 가장 일관되게 유지할 수 있는 방향입니다.

대안인 **PDMG 내부 DAO/DB 직접 연결**도 기술적으로 가능한 경우가 있습니다. 다만 대안을 선택하려면 더 나은 가용성·성능·운영성 또는 비용효과가 Runtime Test로 입증되어야 하며, 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| Architecture 정합성 | 높음 — 책임/경계 중심 | 별도 보완설계 필요 |
| Current PDMG 정합 | 현재 Evidence와 우선 정렬 | 변경범위 증가 가능 |
| 운영/장애분석 | 경계와 Trace가 명확 | 구조에 따라 복잡도 증가 |
| Evidence 조건 | 기본 Gate로 검증 | 추가 PoC/Test/ADR 필요 |

---

# Current Evidence / PASS / GAP

## FIG-12-26. Current GAP Map

```text
Current / Reference
│
├─ BI Current Implementation Evidence 없음
├─ Data Contract Inventory
├─ ADW Freshness/SLA
└─ Dataset Ownership/Security
```

- `[GAP/OPEN]` BI Current Implementation Evidence 없음
- `[GAP/OPEN]` Data Contract Inventory
- `[GAP/OPEN]` ADW Freshness/SLA
- `[GAP/OPEN]` Dataset Ownership/Security

## FIG-12-27. Architecture Assessment

```text
Architecture Definition
 ↓
TARGET REFERENCE / CONDITIONAL PASS

Current PDMG Conformance
 ↓
N-A / OPEN

Runtime Evidence Coverage
 ↓
LOW

Architecture PASS
 ≠
Implementation PASS
```

여기서 반드시 구분해야 할 것은 Architecture Definition PASS와 Current Implementation PASS가 동일하지 않다는 점입니다. 구조와 원칙이 합리적으로 정의되어 있어도 Current Source·Deployment·Runtime이 이를 따르지 않으면 Current Conformance는 GAP 또는 PARTIAL일 수 있습니다.

## FIG-12-28. Evidence Traceability

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

최종적으로 이 장의 Architecture는 문서 안에서 끝나지 않습니다. Source와 Config, 배포 Artifact, Runtime의 ServiceId/GUID, Metric/Trace/Test를 연결해 실제로 설계가 지켜졌음을 증명해야 합니다.

---

# 이 장의 결론

## FIG-12-29. Chapter Conclusion

```text
BI 포탈
 ↓
전체 TEXT Architecture
 ↓
L0 → L5 Drill-down
 ↓
Runtime / Failure / Security
 ↓
Normal / Forbidden
 ↓
Decision / Evidence
 ↓
TARGET REFERENCE / CONDITIONAL PASS
```

이 장에서 확인한 것은 **데이터 플랫폼이 신뢰를 만들고 BI는 판단 속도를 높인다**입니다. 중요한 것은 구성요소를 많이 나열한 것이 아니라, 전체 그림에서 시작해 책임과 경계를 나누고 Runtime과 Evidence까지 연결했다는 점입니다.

여기까지 정리하면 다음 질문이 자연스럽게 생깁니다.

> **“좋은 Architecture는 처음 잘 그린 그림이 아니라 계속 정합되는 체계다”**

그 질문이 바로 다음 단계, **표준화와 10년 지속 가능성**의 주제입니다.


---

<!-- SOURCE CHAPTER: 13_표준화와_10년_지속_가능성.md -->

# NSIGHT PDMG 아키텍처 정의서
# 제13장. 표준화와 10년 지속 가능성
## Story: “좋은 Architecture는 처음 잘 그린 그림이 아니라 계속 정합되는 체계다”
## 발표스크립트 Story + TEXT Architecture + Top-down → Drill-down

> 작성 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_마스터프롬프트_v1.md`  
> 목차 기준: `NSIGHT_PDMG_아키텍처정의서_STORY_TEXT_TOPDOWN_DRILLDOWN_상세목차_v1.md`  
> 문서 상태: `[WORKING BASELINE-2026-09-01]`  
> 주인공: **PDMG Current Architecture** / 상위 정합: **NSIGHT Target Reference**

---

# 0. 이 장의 Story

## FIG-13-00. Story Opening

```text
이전 장의 질문
 ↓
좋은 Architecture는 처음 잘 그린 그림이 아니라 계속 정합되는 체계다
 ↓
전체 그림
 ↓
Top-down 해부
 ↓
Runtime / Failure / Security
 ↓
Evidence / PASS / GAP
```

# 1. 장 전체 대표 Architecture

## FIG-13-01. 표준화와 10년 지속 가능성 — Whole Architecture

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

이 장의 핵심은 **좋은 Architecture는 처음 잘 그린 그림이 아니라 계속 정합되는 체계다**라는 메시지를 하나의 Architecture 그림으로 먼저 이해하는 것입니다.

기존 문서에서는 표준화와 10년 지속 가능성과 관련된 기술요소가 개별 표나 구성요소로 나뉘어 보이는 경우가 많았습니다. 그러나 요소가 존재한다는 것만으로는 책임, 경계, 장애영향, 실행순서를 설명할 수 없습니다.

그래서 이번 정의서에서는 먼저 전체 그림을 보여주고, 독자가 그 그림의 방향과 경계를 이해한 뒤 L0에서 L5까지 내려가도록 구성합니다. 위에서 아래로 내려가는 이유는 세부기술이 상위 Architecture Intent를 잃지 않게 하기 위해서입니다.

여기서 중요한 원칙은 **PDMG Current와 NSIGHT Target을 분리하는 것**입니다. Source·Config·Runtime으로 확인되는 구조는 Current로 설명하고, 향후 목표나 전략은 Target 또는 Reference로 별도 표시합니다.

이것은 단순히 문서의 표현방식을 바꾸는 문제가 아닙니다. 같은 구성요소라도 Current인지 Target인지에 따라 Architecture Decision, 구현 책임, 테스트 기준이 달라지기 때문입니다.

운영 관점에서는 이 구분이 더 중요합니다. 실제 장애가 발생했을 때 어느 Component, Thread, JVM, DataSource, Interface가 영향을 받는지 추적하려면 책임과 Runtime이 연결되어 있어야 합니다.

반대로 표준화와 10년 지속 가능성을 제품명이나 박스 목록만으로 설명하면 그림은 단순해 보이지만 실제 변경과 장애를 설명하기 어렵습니다. 따라서 이 장에서는 정상경로뿐 아니라 금지패턴, Failure, Security, Evidence까지 함께 다룹니다.

이제 전체 그림을 기준으로 하나씩 해부해 보겠습니다. 먼저 L0에서는 이 장의 메시지를 고정하고, L1~L3에서는 책임과 Component를 나누며, L4에서는 실제 Runtime과 Failure를 보고, 마지막 L5에서는 Source·Config·Deployment·Runtime Evidence로 다시 확인합니다.

## FIG-13-02. L0 → L5 Drill-down Route

```text
L0 STORY / LANDSCAPE
"좋은 Architecture는 처음 잘 그린 그림이 아니라 계속 정합되는 체계다"
 ↓
L1 RESPONSIBILITY / BOUNDARY
 ↓
L2 APPLICATION / LOGICAL / PLATFORM
 ↓
L3 COMPONENT / CONTRACT
 ↓
L4 RUNTIME / FAILURE / SECURITY
 ↓
L5 SOURCE / CONFIG / DEPLOYMENT / EVIDENCE
```

이제부터 이 대표 그림을 위에서 아래로 해부합니다. 상위 그림에서 보이는 연결을 바로 제품이나 서버로 해석하지 않고, 먼저 책임과 경계를 확인한 다음 Component와 Runtime으로 내려갑니다.

---

# 13.1 장 전체 Governance / Closed Loop

## FIG-13-03. 장 전체 Governance / Closed Loop

```text
Document
 ↓
Model
 ↓
Code
 ↓
Test
 ↓
Runtime Evidence
 ↓
Drift
 ↓
GAP
 ↓
ADR
 ↓
New Baseline
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 장 전체 Governance / Closed Loop을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `17_PDMG_TRACEABILITY_PASS_GAP_ADR_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Architecture Drift에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.2 Architecture Drift

## FIG-13-04. Architecture Drift

```text
Architecture / Config Baseline
 ↓ compare
Actual Source / Deployment / Runtime
 ↓
Drift
 ↓
GAP / ADR / Fix
```

이 그림의 핵심은 **운영과 증적을 Architecture에 연결하는 단계**입니다. Architecture Drift을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 로그가 존재하는 것과 Architecture Rule의 Runtime 준수 증적은 다릅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. SourceCommit→ArtifactHash→DeploymentId→ServiceId/GUID를 연결합니다.

Current Evidence 관점에서는 `14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Naming에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.3 Naming

## FIG-13-05. Naming

```text
Business Axis
MG / CO / A
    ↓
Program
mgcoa9001
    ↓
ServiceId
mgcoa9001S0
    ↓
Package
nhnis.mg.co.a
    ↓
Handler / Facade / Service / DAO
    ↓
Mapper
rdw.mg.co.a
    ↓
SqlId / SQL / Table
```

이 그림의 핵심은 **식별과 Traceability의 기준축**입니다. Naming을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. ServiceId/Program/Mapper 식별은 Source와 Runtime Evidence, 변경영향을 연결합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. 서로 다른 의미의 ID를 하나로 합치거나 자동 정규화하지 않습니다.

Current Evidence 관점에서는 `00_PDMG_ARCHITECTURE_MASTER_INDEX.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Program / ServiceId에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.4 Program / ServiceId

## FIG-13-06. Program / ServiceId

```text
Business
 ↓
Program
 ↓
ServiceId
 ↓
Handler
 ↓
Facade
 ↓
Service
 ↓
DAO
 ↓
Mapper
 ↓
SqlId
 ↓
Table
```

이 그림의 핵심은 **식별과 Traceability의 기준축**입니다. Program / ServiceId을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. ServiceId/Program/Mapper 식별은 Source와 Runtime Evidence, 변경영향을 연결합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. 서로 다른 의미의 ID를 하나로 합치거나 자동 정규화하지 않습니다.

Current Evidence 관점에서는 `17_PDMG_TRACEABILITY_PASS_GAP_ADR_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Package / Mapper에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.5 Package / Mapper

## FIG-13-07. Package / Mapper

```text
Business Axis
MG / CO / A
 ↓
Java
nhnis.mg.co.a

Mapper
rdw.mg.co.a
```

이 그림의 핵심은 **식별과 Traceability의 기준축**입니다. Package / Mapper을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. ServiceId/Program/Mapper 식별은 Source와 Runtime Evidence, 변경영향을 연결합니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. 서로 다른 의미의 ID를 하나로 합치거나 자동 정규화하지 않습니다.

Current Evidence 관점에서는 `15_PDMG_NAMING_CODE_DEVELOPMENT_STANDARD_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Development Rule에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.6 Development Rule

## FIG-13-08. Development Rule

```text
General Rule Layer
= Current Source에서 전수 확인되지 않음

→ [OPEN / PROPOSED]
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Development Rule을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `00_PDMG_ARCHITECTURE_MASTER_INDEX.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Static Scanner에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.7 Static Scanner

## FIG-13-09. Static Scanner

```text
Source
 ↓
Program parser
 ↓
Package parser
 ↓
Class parser
 ↓
ServiceId parser
 ↓
Mapper parser
 ↓
PASS / FAIL
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Static Scanner을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `15_PDMG_NAMING_CODE_DEVELOPMENT_STANDARD_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“CI Gate에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.8 CI Gate

## FIG-13-10. CI Gate

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

이 그림의 핵심은 **운영과 증적을 Architecture에 연결하는 단계**입니다. CI Gate을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 로그가 존재하는 것과 Architecture Rule의 Runtime 준수 증적은 다릅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. SourceCommit→ArtifactHash→DeploymentId→ServiceId/GUID를 연결합니다.

Current Evidence 관점에서는 `14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Build Once에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.9 Build Once

## FIG-13-11. Build Once

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

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Build Once을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `13_NSIGHT_PDMG_아키텍처정의서_표준화와_10년_지속가능성_STORY_VISUAL_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Immutable Artifact에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.10 Immutable Artifact

## FIG-13-12. Immutable Artifact

```text
Build once
 ↓
Immutable Artifact
 ↓
DEV
 ↓
TEST
 ↓
PROD
 ↓
DR

Hash identical
```

이 그림의 핵심은 **운영과 증적을 Architecture에 연결하는 단계**입니다. Immutable Artifact을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 로그가 존재하는 것과 Architecture Rule의 Runtime 준수 증적은 다릅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. SourceCommit→ArtifactHash→DeploymentId→ServiceId/GUID를 연결합니다.

Current Evidence 관점에서는 `14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Config / Secret에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.11 Config / Secret

## FIG-13-13. Config / Secret

```text
Artifact
= code/binary

Environment Config
= external

Secret / Key
= protected store

Never bundle generic secret
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Config / Secret을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“DeploymentId에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.12 DeploymentId

## FIG-13-14. DeploymentId

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
 ↓
Runtime Evidence
```

이 그림의 핵심은 **운영과 증적을 Architecture에 연결하는 단계**입니다. DeploymentId을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 로그가 존재하는 것과 Architecture Rule의 Runtime 준수 증적은 다릅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. SourceCommit→ArtifactHash→DeploymentId→ServiceId/GUID를 연결합니다.

Current Evidence 관점에서는 `manual/current-baseline`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Host / JVM Trace에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.13 Host / JVM Trace

## FIG-13-15. Host / JVM Trace

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

이 그림의 핵심은 **논리적 책임을 실제 실행자원으로 내리는 단계**입니다. Host / JVM Trace을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Server, VM, JVM, WAR를 구분하고 장애영향과 Scale Unit을 함께 봅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Hostname·Port·Version은 Inventory Evidence가 없으면 창작하지 않습니다.

Current Evidence 관점에서는 `14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Metric / Log / Trace에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.14 Metric / Log / Trace

## FIG-13-16. Metric / Log / Trace

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

이 그림의 핵심은 **운영과 증적을 Architecture에 연결하는 단계**입니다. Metric / Log / Trace을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 로그가 존재하는 것과 Architecture Rule의 Runtime 준수 증적은 다릅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. SourceCommit→ArtifactHash→DeploymentId→ServiceId/GUID를 연결합니다.

Current Evidence 관점에서는 `14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Runtime Evidence에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.15 Runtime Evidence

## FIG-13-17. Runtime Evidence

```text
Architecture Rule
 ↓
Runtime Metric / Trace / Test
 ↓
Evidence ID
 ↓
Gate
 ↓
Baseline PASS
```

이 그림의 핵심은 **운영과 증적을 Architecture에 연결하는 단계**입니다. Runtime Evidence을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 로그가 존재하는 것과 Architecture Rule의 Runtime 준수 증적은 다릅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. SourceCommit→ArtifactHash→DeploymentId→ServiceId/GUID를 연결합니다.

Current Evidence 관점에서는 `14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Drift Detection에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.16 Drift Detection

## FIG-13-18. Drift Detection

```text
Architecture / Config Baseline
 ↓ compare
Actual Source / Deployment / Runtime
 ↓
Drift
 ↓
GAP / ADR / Fix
```

이 그림의 핵심은 **운영과 증적을 Architecture에 연결하는 단계**입니다. Drift Detection을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 로그가 존재하는 것과 Architecture Rule의 Runtime 준수 증적은 다릅니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. SourceCommit→ArtifactHash→DeploymentId→ServiceId/GUID를 연결합니다.

Current Evidence 관점에서는 `14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“GAP / ADR에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.17 GAP / ADR

## FIG-13-19. GAP / ADR

```text
Expected Architecture
   ↓ compare
Current Evidence
   ↓
GAP / OPEN / CONFLICT
   ↓
Owner / Evidence / ADR
   ↓
Close
```

이 그림의 핵심은 **장애와 재해에서 서비스 복구를 보장하는 단계**입니다. GAP / ADR을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. Local HA, Center DR, Backup, Restore는 서로 다른 책임을 가집니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. RTO/RPO와 복구성은 Drill과 Business Validation Evidence로 확정합니다.

Current Evidence 관점에서는 `14_PDMG_DEVOPS_OM_OBSERVABILITY_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“G00 → HG90에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.18 G00 → HG90

## FIG-13-20. G00 → HG90

```text
G00 Source
 ↓
G10 Document
 ↓
G20 Model
 ↓
G30 Conformance
 ↓
G40 Rule Test
 ↓
G50 Runtime Evidence
 ↓
G60 Drift
 ↓
G70 GAP / ADR
 ↓
G80 Approval
 ↓
HG90 Baseline Release
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. G00 → HG90을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `manual/current-baseline`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Architecture Baseline Release에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.19 Architecture Baseline Release

## FIG-13-21. Architecture Baseline Release

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

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Architecture Baseline Release을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `18_PDMG_INTEGRATED_ARCHITECTURE_BASELINE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“Final Critical GAP에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.20 Final Critical GAP

## FIG-13-22. Final Critical GAP

```text
CRITICAL
├─ JWT issuer/verifier/key
├─ Identity binding
├─ Deployment→Host/JVM/WAR
└─ Runtime evidence automation

HIGH
├─ TCF OFF facade drift
├─ Worker mutable context
├─ Query timeout/cancel evidence
├─ Interface inventory
├─ RDW/ADW actual mapping
├─ pdmg-om scope
└─ Capacity/DR approval
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. Final Critical GAP을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `18_PDMG_INTEGRATED_ARCHITECTURE_BASELINE_상세정의서_v1.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“최종 PASS 조건에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# 13.21 최종 PASS 조건

## FIG-13-23. 최종 PASS 조건

```text
Architecture Definition
          ↓
PASS / CONDITIONAL / OPEN / FAIL

          ≠

PDMG Current Implementation
          ↓
PASS / PARTIAL / GAP / CONFLICT / UNKNOWN
```

이 그림의 핵심은 **상위 Architecture를 한 단계 더 구체화하는 영역**입니다. 최종 PASS 조건을 별도 절로 분리한 이유는 상위 Architecture에서 한 개의 박스로 보였던 영역 안에 실제로 여러 책임과 통제지점이 숨어 있기 때문입니다.

기존에는 이 영역을 구성요소 이름이나 제품 이름만으로 설명하기 쉬웠습니다. 그러나 그렇게 보면 연결은 보이지만 왜 이 경계가 필요한지, 장애가 어디까지 전파되는지, 누가 운영 책임을 가지는지가 보이지 않습니다. 상위 그림의 박스를 책임·경계·흐름으로 나누어 실제 설계대상으로 바꿉니다.

그래서 이번 Architecture에서는 이 영역을 `Responsibility → Boundary → Runtime → Evidence` 순서로 읽습니다. Current Fact와 Target Reference를 분리하고 Evidence가 없는 부분은 추정하지 않습니다.

Current Evidence 관점에서는 `00_PDMG_ARCHITECTURE_MASTER_INDEX.md`에서 확인되는 구조를 우선 사용합니다. Host, Port, Version, 운영값처럼 근거가 없는 값은 그림을 완성하기 위해 임의로 넣지 않습니다.

한 단계 더 내려가면 다음 질문은 **“장 결론 / 다음 장 Handoff에서는 이 책임이 어떻게 더 구체화되는가?”**입니다. 이제 다음 영역으로 내려가 보겠습니다.

---

# Architecture Cross-check — Failure / Security / Evidence

## FIG-13-24. Failure Propagation

```text
표준화와 10년 지속 가능성 하위 Component Failure
 ↓
Dependency / Pool / Interface 영향
 ↓
Runtime 지연 또는 오류
 ↓
Upstream Service 영향
 ↓
Alert / Recovery / Evidence
```

이 그림에서 중요한 것은 장애가 한 Component에서 끝나지 않는다는 점입니다. Architecture는 정상경로뿐 아니라 어떻게 느려지고, 어떻게 실패하고, 어디에서 차단해야 하는지까지 설명해야 합니다.

## FIG-13-25. Security / Trust Boundary

```text
Untrusted / External
 ↓
Authentication / Validation
 ↓
Trusted Principal / Contract
 ↓
표준화와 10년 지속 가능성 Runtime
 ↓
Authorization / Data Access
 ↓
Audit / Evidence
```

Security 관점에서는 '접속이 가능하다'와 '신뢰된 주체가 허가된 업무를 수행한다'를 구분합니다. PDMG의 JWT Issuer/Verifier 및 Identity Binding GAP는 해당 장에서 Critical GAP로 유지합니다.

---

# 정상패턴 / 금지패턴

## FIG-13-26. Normal Pattern

```text
Architecture → Rule/CI
→ Artifact → Deployment
→ Runtime Evidence → Drift
→ ADR → New Baseline
```

정상패턴에서는 각 영역이 자신의 책임을 수행하고 다음 영역과는 명확한 Contract 또는 Runtime Boundary를 통해 연결됩니다. 이렇게 해야 변경 영향과 장애영향을 제한할 수 있습니다.

## FIG-13-27. Forbidden Pattern

```text
표준문서만 존재        X
Environment 재빌드     X
Secret in Artifact     X
Evidence 없이 PASS     X
```

반대로 금지패턴은 구현 자체가 불가능해서가 아니라 Architecture Boundary를 무너뜨리고 Source·Runtime·운영증적의 정합성을 떨어뜨리기 때문에 금지합니다. 예외가 필요하면 ADR와 Evidence로 관리합니다.

---

# Architecture Decision — 주안 / 대안 / Trade-off

## FIG-13-28. Primary vs Alternative

```text
[주안]
CI + Runtime Evidence Gate

        VS

[대안]
문서 Review/수동점검
```

이번 장의 주안은 **CI + Runtime Evidence Gate**입니다. 이 선택은 현재 PDMG Source/Runtime과 NSIGHT Target Alignment를 함께 고려했을 때 책임과 Evidence를 가장 일관되게 유지할 수 있는 방향입니다.

대안인 **문서 Review/수동점검**도 기술적으로 가능한 경우가 있습니다. 다만 대안을 선택하려면 더 나은 가용성·성능·운영성 또는 비용효과가 Runtime Test로 입증되어야 하며, 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| Architecture 정합성 | 높음 — 책임/경계 중심 | 별도 보완설계 필요 |
| Current PDMG 정합 | 현재 Evidence와 우선 정렬 | 변경범위 증가 가능 |
| 운영/장애분석 | 경계와 Trace가 명확 | 구조에 따라 복잡도 증가 |
| Evidence 조건 | 기본 Gate로 검증 | 추가 PoC/Test/ADR 필요 |

---

# Current Evidence / PASS / GAP

## FIG-13-29. Current GAP Map

```text
Current / Reference
│
├─ Naming Scanner CI
├─ Artifact/Deployment Identity
├─ Runtime Evidence Collector
├─ pdmg-om/OM Control Plane
└─ Critical ADR Closure
```

- `[GAP/OPEN]` Naming Scanner CI
- `[GAP/OPEN]` Artifact/Deployment Identity
- `[GAP/OPEN]` Runtime Evidence Collector
- `[GAP/OPEN]` pdmg-om/OM Control Plane
- `[GAP/OPEN]` Critical ADR Closure

## FIG-13-30. Architecture Assessment

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

Architecture PASS
 ≠
Implementation PASS
```

여기서 반드시 구분해야 할 것은 Architecture Definition PASS와 Current Implementation PASS가 동일하지 않다는 점입니다. 구조와 원칙이 합리적으로 정의되어 있어도 Current Source·Deployment·Runtime이 이를 따르지 않으면 Current Conformance는 GAP 또는 PARTIAL일 수 있습니다.

## FIG-13-31. Evidence Traceability

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

최종적으로 이 장의 Architecture는 문서 안에서 끝나지 않습니다. Source와 Config, 배포 Artifact, Runtime의 ServiceId/GUID, Metric/Trace/Test를 연결해 실제로 설계가 지켜졌음을 증명해야 합니다.

---

# 이 장의 결론

## FIG-13-32. Chapter Conclusion

```text
표준화와 10년 지속 가능성
 ↓
전체 TEXT Architecture
 ↓
L0 → L5 Drill-down
 ↓
Runtime / Failure / Security
 ↓
Normal / Forbidden
 ↓
Decision / Evidence
 ↓
CONDITIONAL PASS
```

이 장에서 확인한 것은 **좋은 Architecture는 처음 잘 그린 그림이 아니라 계속 정합되는 체계다**입니다. 중요한 것은 구성요소를 많이 나열한 것이 아니라, 전체 그림에서 시작해 책임과 경계를 나누고 Runtime과 Evidence까지 연결했다는 점입니다.

여기까지 정리하면 다음 질문이 자연스럽게 생깁니다.

> **“13개 장의 Architecture를 Evidence로 검증하고 공식 Baseline으로 Release한다”**

그 질문이 바로 다음 단계, **HG90 Evidence-backed Architecture Baseline**의 주제입니다.


---
