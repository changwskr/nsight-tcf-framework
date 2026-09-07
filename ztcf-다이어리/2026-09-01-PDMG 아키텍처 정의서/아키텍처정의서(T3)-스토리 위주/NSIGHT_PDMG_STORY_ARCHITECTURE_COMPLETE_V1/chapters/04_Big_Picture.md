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
