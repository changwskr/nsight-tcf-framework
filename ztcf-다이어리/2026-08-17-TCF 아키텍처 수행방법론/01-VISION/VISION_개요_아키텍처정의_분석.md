# VISION — 개요 / 아키텍처 정의 분석

## 1. 핵심 결론

이 장표는 차세대 정보계 아키텍처 문서의 **최상위 개요이자 적용 기준선(Baseline Entry Point)** 을 정의한다.

장표가 직접 제시하는 핵심은 다음과 같다.

```text
차세대 정보계 아키텍처 정의
        │
        ├─ Application Architecture
        ├─ Technical Architecture
        └─ Data Architecture
```

그리고 이 정의의 목적은 단순히 아키텍처를 문서화하는 것이 아니라,

```text
인프라 담당자
개발자
업무 프로그램 개발 담당자
유관 담당자
        │
        ▼
차세대 정보계의 아키텍처를 빠르게 파악
        │
        ▼
업무/설계/개발/운영에 적용
```

할 수 있도록 **공통 이해 체계와 적용 기준을 제공하는 것**이다.

이 장표가 제시하는 아키텍처 정의 범위는 다음 세 축으로 정리할 수 있다.

| 아키텍처 영역 | 장표의 정의 초점 | 핵심 대상 |
|---|---|---|
| Technical Architecture | 논리/물리 구성, 기술요소, 소프트웨어, 센터 구성, 백업, 가용성 | 인프라 / 기술 플랫폼 |
| Application Architecture | 애플리케이션 구성·분류·동작 원리·표준화 | 업무 애플리케이션 / 개발 |
| Data Architecture | 데이터 주제영역 정의 | 데이터 구조 / 분류 |

즉 이 장표는 이후 모든 아키텍처 상세 설계의 출발점으로,

```text
무엇을 정의할 것인가?
누가 이해해야 하는가?
어느 수준까지 적용할 것인가?
어떤 아키텍처 영역을 문서화할 것인가?
```

를 고정하는 **Architecture Definition Vision / Overview** 역할을 한다.

---

# 2. 원본 장표 메타정보

## 2.1 문서 위치

원본 우측 상단 표기:

```text
1. 아키텍처 정의
1.1 개요
```

원본 페이지 하단:

```text
- 7 -
```

따라서 이 장표는 아키텍처 문서의 첫 번째 본문 장에 해당하며, 이후 상세 아키텍처 정의의 전제와 적용범위를 고정하는 페이지다.

---

## 2.2 장표 제목

```text
개요
```

---

## 2.3 상단 핵심 설명

원본에서 확인되는 문장:

> 차세대 정보계 시스템의 어플리케이션 정의, 기술 아키텍처 설계, 데이터 주제 영역의 정의를 기술하고 인프라 담당자, 개발자 및 유관 담당자가 아키텍처를 빠르게 파악하고 업무에 적용함

이를 구조화하면 다음과 같다.

```text
차세대 정보계 시스템
   │
   ├─ 어플리케이션 정의
   ├─ 기술 아키텍처 설계
   └─ 데이터 주제 영역 정의
          │
          ▼
   문서화 / 기준화
          │
          ▼
인프라 담당자 / 개발자 / 유관 담당자
          │
          ▼
아키텍처 빠른 파악
          │
          ▼
업무 적용
```

---

# 3. 원본 장표 전체 구조 — 상세 텍스트 재현

> 아래 텍스트 그림은 원본의 `목적 / 정의 / 적용 범위` 3개 영역과 그 내부 내용을 최대한 보존하여 재구성한 것이다.

```text
┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                              1. 아키텍처 정의                                                            │
│                                                 1.1 개요                                                                 │
│                                                                                                                          │
│  차세대 정보계 시스템의 어플리케이션 정의, 기술 아키텍처 설계, 데이터 주제 영역의 정의를 기술하고                       │
│  인프라 담당자, 개발자 및 유관 담당자가 아키텍처를 빠르게 파악하고 업무에 적용함                                        │
│                                                                                                                          │
├──────────────┬───────────────────────────────────────────────────────────────────────────────────────────────────────────┤
│              │                                                                                                           │
│     목적     │ ■ 본 문서는 차세대 정보계 시스템의 아키텍처를 정의함으로써,                                              │
│              │   인프라 및 업무 프로그램 개발 담당자 또는 아키텍처의 이해를 필요로 하는 유관 담당자가                  │
│              │   시스템의 아키텍처를 이해하고 업무에 활용할 수 있도록 정보를 제공하는 것을 목적으로 함                │
│              │                                                                                                           │
├──────────────┼───────────────────────────────────────────────────────────────────────────────────────────────────────────┤
│              │                                                                                                           │
│     정의     │ ■ 어플리케이션의 구성과 분류 체계를 식별하고                                                            │
│              │   어플리케이션이 동작하는 시스템의 구성을 확인할 수 있도록 정의한다.                                    │
│              │                                                                                                           │
│              │ ■ 운영 환경 별 논리적 시스템 구성 단위와                                                                │
│              │   시스템을 구성하는 기술요소 및 소프트웨어의 구성을 확인할 수 있도록 정의한다.                         │
│              │                                                                                                           │
│              │ ■ 운영 환경 별 물리적 시스템 구성 단위와 구성 Inventory를 확인할 수 있도록 정의한다.                  │
│              │                                                                                                           │
│              │ ■ 아키텍처 표준 및 아키텍처 구성 요소의 동작 원리를 확인할 수 있도록 정의한다.                         │
│              │                                                                                                           │
│              │ ■ 업무 영역별 어플리케이션 동작 원리를 확인 할 수 있도록 정의한다.                                     │
│              │                                                                                                           │
├──────────────┼───────────────────────────────────────────────────────────────────────────────────────────────────────────┤
│              │                                                                                                           │
│   적용 범위  │ 차세대 정보계 구축 대상 업무 영역의 Technical, Application, Data 아키텍처 정의는                        │
│              │ 본 문서의 정의 내용을 적용한다.                                                                         │
│              │                                                                                                           │
│              │ ■ Technical Architecture                                                                                 │
│              │    - 운영 환경 별 논리, 물리 아키텍처의 구성과 시스템, 소프트웨어의 적용                                │
│              │    - 센터 별 시스템 구성 구분과 백업 및 시스템 가용성 처리 방식 적용                                   │
│              │                                                                                                           │
│              │ ■ Application Architecture                                                                               │
│              │    - 어플리케이션의 구성과 분류 및 동작 원리 확인 및 적용                                               │
│              │    - 아키텍처 표준화 정의 확인                                                                           │
│              │                                                                                                           │
│              │ ■ Data Architecture                                                                                      │
│              │    - 데이터 주제 영역 확인                                                                               │
│              │                                                                                                           │
│              │ ※ 상기 영역 외 상세 구성 방안 및 가이드는 각 영역별 가이드 참조                                        │
│              │                                                                                                           │
└──────────────┴───────────────────────────────────────────────────────────────────────────────────────────────────────────┘
```

---

# 4. 장표 핵심 구조를 계층적으로 재작성

```text
Architecture Definition
│
├─ 목적
│   └─ 관계자가 아키텍처를 이해하고 업무에 활용할 수 있도록 정보 제공
│
├─ 정의
│   ├─ Application 구성/분류 체계
│   ├─ 논리적 시스템 구성
│   ├─ 기술요소 / 소프트웨어 구성
│   ├─ 물리적 시스템 구성
│   ├─ 구성 Inventory
│   ├─ 아키텍처 표준
│   ├─ 구성요소 동작 원리
│   └─ 업무영역별 Application 동작 원리
│
└─ 적용 범위
    ├─ Technical Architecture
    │   ├─ 논리 아키텍처
    │   ├─ 물리 아키텍처
    │   ├─ 시스템/소프트웨어 적용
    │   ├─ 센터별 시스템 구성
    │   ├─ 백업
    │   └─ 가용성
    │
    ├─ Application Architecture
    │   ├─ 구성
    │   ├─ 분류
    │   ├─ 동작 원리
    │   └─ 아키텍처 표준화
    │
    └─ Data Architecture
        └─ 데이터 주제 영역
```

---

# 5. 목적 영역 상세 분석

## 5.1 원본 FACT

원본 목적 문장은 다음 내용을 명시한다.

```text
본 문서는 차세대 정보계 시스템의 아키텍처를 정의
        ↓
인프라 및 업무 프로그램 개발 담당자
또는 아키텍처의 이해를 필요로 하는 유관 담당자
        ↓
시스템의 아키텍처 이해
        ↓
업무 활용
```

---

## 5.2 아키텍처 문서의 성격

이 문서의 목적은 단순 설계기록이 아니라 **공통 참조 문서**다.

즉 다음 성격을 가진다.

```text
Architecture Reference
      +
Development Reference
      +
Infrastructure Reference
      +
Governance Reference
```

### 의미

- 인프라 담당자는 물리/논리 구성과 기술요소를 확인
- 개발자는 애플리케이션 구조와 표준을 확인
- 데이터 담당자는 데이터 주제영역을 확인
- 유관 담당자는 시스템 구조를 이해하고 업무 의사결정에 활용

---

# 6. 문서 사용자 관점 분석

장표에서 직접 언급되는 사용자:

```text
인프라 담당자
개발자
업무 프로그램 개발 담당자
유관 담당자
아키텍처 이해가 필요한 담당자
```

이를 역할 관점으로 정리하면 다음과 같다.

| 역할 | 이 문서에서 확인해야 할 정보 |
|---|---|
| 인프라 담당자 | 논리/물리 구성, 시스템, 소프트웨어, 센터, 백업, 가용성 |
| 개발자 | Application 구성, 분류, 동작원리, 표준 |
| 업무 개발 담당자 | 업무영역별 Application 동작원리 |
| 데이터 담당자 | 데이터 주제영역 |
| 아키텍트 | 전체 Architecture Baseline |
| 유관 담당자 | 시스템 구조 및 업무 적용 기준 |

> `데이터 담당자`, `아키텍트`라는 명칭 자체는 목적 문장에 직접 열거되지 않지만, Data Architecture 및 아키텍처 정의 범위를 고려한 **ANALYSIS**다.

---

# 7. “정의” 영역의 5개 핵심 정의 축

장표의 `정의` 영역은 사실상 아키텍처 정의서가 답해야 할 핵심 질문을 5개로 제시한다.

```text
Q1. Application은 어떻게 구성되고 분류되는가?
Q2. 논리 시스템은 어떻게 구성되는가?
Q3. 물리 시스템은 어떻게 구성되는가?
Q4. Architecture Standard와 구성요소는 어떻게 동작하는가?
Q5. 업무영역별 Application은 어떻게 동작하는가?
```

이 다섯 질문을 해결하면 다음 전체 구조가 완성된다.

```text
Business Domain
     ↓
Application
     ↓
Logical System
     ↓
Technical Component
     ↓
Physical System / Inventory
     ↓
Runtime Principle
```

---

# 8. 정의 ① — Application 구성과 분류체계

## 8.1 원본 FACT

> 어플리케이션의 구성과 분류 체계를 식별하고 어플리케이션이 동작하는 시스템의 구성을 확인할 수 있도록 정의한다.

이를 그림으로 풀면:

```text
Application Portfolio
      │
      ├─ 업무영역
      ├─ 시스템
      ├─ Application
      ├─ Module
      └─ Component
             │
             ▼
     Runtime System
```

---

## 8.2 설계 의미

이 정의는 단순 애플리케이션 목록이 아니라 다음 매핑이 필요함을 의미한다.

```text
업무
 ↓
Application
 ↓
System
 ↓
Runtime
```

즉 애플리케이션 분류체계는 **업무 구조와 실행환경을 연결하는 기준**이다.

---

# 9. 정의 ② — 논리적 시스템 구성

## 9.1 원본 FACT

> 운영 환경 별 논리적 시스템 구성 단위와 시스템을 구성하는 기술요소 및 소프트웨어의 구성을 확인할 수 있도록 정의한다.

이를 구조화하면:

```text
운영 환경
   │
   ▼
Logical System
   │
   ├─ Application
   ├─ Middleware
   ├─ Database
   ├─ Interface
   ├─ Framework
   └─ Software
```

### 핵심 포인트

- “운영 환경 별”이라는 표현이 중요
- 하나의 논리시스템이 아니라 환경별 차이를 보여줘야 함
- 기술요소와 소프트웨어 구성도 함께 정의

---

# 10. 정의 ③ — 물리적 시스템 구성과 Inventory

## 10.1 원본 FACT

> 운영 환경 별 물리적 시스템 구성 단위와 구성 Inventory를 확인할 수 있도록 정의한다.

텍스트 그림:

```text
Logical System
     │
     ▼
Physical System
     │
     ├─ Server
     ├─ VM
     ├─ OS
     ├─ CPU
     ├─ Memory
     ├─ Disk
     ├─ Network
     └─ Installed Software
```

> `Server/VM/OS/CPU...` 세부 항목은 장표에 직접 나열되지 않고 `물리적 시스템 구성 단위 / Inventory`를 설명하기 위한 **ANALYSIS 예시**다.

---

## 10.2 논리와 물리의 관계

```text
Logical Architecture
       │
       │ Deployment Mapping
       ▼
Physical Architecture
       │
       ▼
Inventory
```

이 관계가 고정되어야 다음 질문에 답할 수 있다.

- 이 Application은 어디에서 실행되는가?
- 어떤 서버/VM에 배치되는가?
- 어떤 소프트웨어가 필요한가?
- 장애 시 어떤 물리 구성요소의 영향을 받는가?

---

# 11. 정의 ④ — Architecture Standard 및 구성요소 동작원리

## 11.1 원본 FACT

> 아키텍처 표준 및 아키텍처 구성 요소의 동작 원리를 확인할 수 있도록 정의한다.

이는 문서가 정적 구성도만 제공하지 않고 **Runtime Behavior**까지 정의해야 한다는 의미다.

```text
Architecture Component
       │
       ├─ 역할
       ├─ 책임
       ├─ 호출 관계
       ├─ 정상 흐름
       ├─ 오류 흐름
       └─ 운영 원칙
```

---

## 11.2 중요성

같은 구성요소라도 동작 규칙이 없으면 실제 개발·운영에서 해석이 달라질 수 있다.

따라서 다음 수준까지 내려가야 한다.

```text
Component
   ↓
Rule
   ↓
Runtime Flow
   ↓
Standard
```

이 원칙은 이후 Controller/Service/DAO/TCF/API Gateway/CDC/ETL 등 상세 아키텍처 정의와 연결될 수 있다.

> 구체 컴포넌트 예시는 **ANALYSIS**이며 현재 장표에 직접 표기된 것은 아니다.

---

# 12. 정의 ⑤ — 업무영역별 Application 동작원리

## 12.1 원본 FACT

> 업무 영역별 어플리케이션 동작 원리를 확인 할 수 있도록 정의한다.

이를 구조화하면:

```text
Business Domain A
   ↓
Application A
   ↓
Runtime Flow A

Business Domain B
   ↓
Application B
   ↓
Runtime Flow B
```

### 의미

전사 공통 아키텍처만 정의하는 것이 아니라 업무별 특성을 반영해야 한다.

즉:

```text
Common Architecture
       +
Domain Specific Behavior
```

구조가 필요하다.

---

# 13. Technical Architecture 적용 범위 상세 분석

원본은 Technical Architecture 적용 범위를 두 가지로 정의한다.

## 13.1 운영환경별 논리·물리 아키텍처 및 SW 적용

원본:

> 운영 환경 별 논리, 물리 아키텍처의 구성과 시스템, 소프트웨어의 적용

구조화:

```text
Environment
   │
   ├─ Logical Architecture
   │     ├─ System
   │     └─ Software
   │
   └─ Physical Architecture
         ├─ Node
         └─ Deployment
```

---

## 13.2 센터별 구성 / 백업 / 가용성

원본:

> 센터 별 시스템 구성 구분과 백업 및 시스템 가용성 처리 방식 적용

구조화:

```text
Center A
  ├─ System
  ├─ Backup
  └─ Availability

Center B
  ├─ System
  ├─ Backup
  └─ Availability
```

### 핵심 의미

Technical Architecture는 단순 서버 배치도가 아니라 다음을 포함한다.

- 센터별 구성 차이
- 백업
- 시스템 가용성
- 장애 대응을 고려한 구성

---

# 14. Technical Architecture 텍스트 모델

```text
┌──────────────────────────── Technical Architecture ────────────────────────────┐
│                                                                                │
│  Environment                                                                   │
│    │                                                                           │
│    ├─ Logical Architecture                                                     │
│    │   ├─ Logical System                                                       │
│    │   ├─ Technical Component                                                  │
│    │   └─ Software                                                             │
│    │                                                                           │
│    └─ Physical Architecture                                                    │
│        ├─ Physical System                                                      │
│        ├─ Inventory                                                            │
│        ├─ Center                                                               │
│        ├─ Backup                                                               │
│        └─ Availability                                                         │
│                                                                                │
└────────────────────────────────────────────────────────────────────────────────┘
```

---

# 15. Application Architecture 적용 범위 상세 분석

원본:

- **어플리케이션의 구성과 분류 및 동작 원리 확인 및 적용**
- **아키텍처 표준화 정의 확인**

이를 다음처럼 모델링할 수 있다.

```text
Application Architecture
   │
   ├─ Composition
   ├─ Classification
   ├─ Runtime Behavior
   └─ Standardization
```

---

## 15.1 Application 구성

```text
System
  ↓
Application
  ↓
Module
  ↓
Component
```

---

## 15.2 Application 분류

```text
Business
Channel
Common
Integration
Batch
Data
Operation
```

> 분류 예시는 현재 장표에 직접 나오지 않으며 **ANALYSIS 예시**다.

---

## 15.3 동작원리

```text
Input
  ↓
Application
  ↓
Business Logic
  ↓
Data / Interface
  ↓
Output
```

---

## 15.4 표준화

표준화는 다음 항목이 일관되도록 만드는 역할을 한다.

```text
Naming
Layer
Package
Interface
Error
Logging
Transaction
Security
```

> 위 항목들은 현재 장표의 `아키텍처 표준화`를 구체화한 **ANALYSIS**이며, 원본에는 항목별 상세가 없다.

---

# 16. Data Architecture 적용 범위 상세 분석

원본:

> 데이터 주제 영역 확인

즉 이 개요 단계에서 Data Architecture의 핵심은 **물리 테이블 설계보다 상위 개념의 주제영역 정의**다.

```text
Enterprise Data
      │
      ├─ Subject Area A
      ├─ Subject Area B
      ├─ Subject Area C
      └─ Subject Area N
```

### 의미

- 데이터 분류 기준 고정
- 업무영역과 데이터영역의 대응
- 후속 Logical/Physical Data Model의 기준점

---

# 17. 세 아키텍처 영역의 관계

```text
                         ┌────────────────────┐
                         │ Business / 업무영역│
                         └─────────┬──────────┘
                                   │
                  ┌────────────────┼────────────────┐
                  │                │                │
                  ▼                ▼                ▼
      Application Architecture  Data Architecture  Technical Architecture
                  │                │                │
                  │                │                │
           Application        Subject Area     Logical/Physical
           Composition        Data Domain      System/Software
           Classification                    Center/Availability
                  │                │                │
                  └────────────────┼────────────────┘
                                   ▼
                          Runtime Architecture
```

이 그림은 현재 장표의 내용을 구조적으로 해석한 **ANALYSIS**다.

핵심은 세 영역이 서로 독립 문서가 아니라 **하나의 실행 가능한 시스템 아키텍처를 설명하기 위해 연결되어야 한다**는 점이다.

---

# 18. Architecture Traceability 모델

개요 장표에서 요구하는 내용을 실제 관리모델로 바꾸면 다음 Traceability가 필요하다.

```text
업무영역
   ↓
Application
   ↓
Logical System
   ↓
Technical Component / Software
   ↓
Physical System / Inventory
```

그리고 데이터 측면:

```text
업무영역
   ↓
Application
   ↓
Data Subject Area
```

최종적으로:

```text
Business
   ├─ Application
   ├─ Data
   └─ Technical
```

세 축이 연결되어야 한다.

---

# 19. 이 장표가 요구하는 산출물 구조

현재 장표에서 직접적으로 또는 구조적으로 도출되는 산출물은 다음과 같다.

| 영역 | 필수 산출물 성격 |
|---|---|
| Application | Application 구성/분류체계 |
| Application | 업무영역별 동작원리 |
| Technical | 논리 시스템 구성도 |
| Technical | 물리 시스템 구성도 |
| Technical | 기술요소/SW 구성 |
| Technical | 시스템 Inventory |
| Technical | 센터별 구성 |
| Technical | 백업/가용성 방식 |
| Data | 데이터 주제영역 정의 |
| Common | 아키텍처 표준 |
| Common | 구성요소 동작원리 |

---

# 20. 독자별 활용 흐름

```text
Infrastructure Engineer
        ↓
Technical Architecture
        ↓
System / SW / Physical / HA / Backup

Developer
        ↓
Application Architecture
        ↓
Composition / Classification / Standard / Runtime

Data Architect / Data Engineer
        ↓
Data Architecture
        ↓
Subject Area

Related Stakeholder
        ↓
Architecture Overview
        ↓
System Understanding / 업무 활용
```

---

# 21. Architecture Governance 관점 분석

이 장표는 “정의”와 “적용 범위”를 제시하므로 자연스럽게 다음 Governance 구조를 요구한다.

```text
Architecture Definition
        ↓
Architecture Standard
        ↓
Project Design
        ↓
Implementation
        ↓
Validation
```

### 왜 중요한가?

표준을 정의만 하고 적용여부를 검증하지 않으면 실제 구축과 문서가 달라질 수 있다.

따라서 후속 단계에서는 다음이 필요하다.

- 설계 검토
- 표준 준수 확인
- 구성 Inventory 검증
- 논리 ↔ 물리 매핑 확인
- Application 분류 준수
- Data Subject Area 준수

---

# 22. Architecture Baseline 관점

이 장표는 최상위 정의 장표이기 때문에 이후 모든 상세 문서는 이 개요와 정합해야 한다.

```text
1.1 개요
   ↓
Architecture Definition Baseline
   ↓
Technical / Application / Data
   ↓
Detailed Architecture
```

후속 문서가 이 개요에서 벗어나면 다음 질문이 필요하다.

- 새로운 아키텍처 영역이 추가된 것인가?
- Scope가 변경된 것인가?
- 기존 정의가 폐기된 것인가?
- ADR/승인이 필요한가?

---

# 23. 아키텍처 문서 계층 모델

```text
Level 0
Architecture Vision / Overview
        │
        ▼
Level 1
Technical / Application / Data Architecture
        │
        ▼
Level 2
Logical / Physical / Runtime / Standard
        │
        ▼
Level 3
Detailed Design / Guide
        │
        ▼
Level 4
Configuration / Source / Inventory
```

현재 장표는 사실상 **Level 0 ~ Level 1 경계**에 해당한다.

> 이 계층명 자체는 원본에 직접 표시되지 않은 **ANALYSIS**다.

---

# 24. 상세 가이드와의 관계

원본 하단 주석:

> ※ 상기 영역 외 상세 구성 방안 및 가이드는 각 영역별 가이드 참조

이는 이 문서가 모든 구현 세부를 담는 것이 아니라 **Architecture Definition과 Guideline을 분리**한다는 뜻이다.

```text
Architecture Definition
   │
   ├─ 무엇인가?
   ├─ 왜 필요한가?
   ├─ 어디에 적용하는가?
   └─ 어떤 원칙인가?
           │
           ▼
Detailed Guide
   ├─ 어떻게 구성하는가?
   ├─ 어떻게 설정하는가?
   ├─ 어떻게 개발하는가?
   └─ 어떻게 운영하는가?
```

---

# 25. 문서 구조상 중요한 설계 원칙

장표에서 직접 도출되는 핵심 원칙은 다음과 같다.

## 25.1 구성과 동작을 함께 정의

```text
Static Architecture
      +
Runtime Behavior
```

구성도만 있어서는 안 되고 동작원리까지 있어야 한다.

---

## 25.2 논리와 물리를 함께 정의

```text
Logical
   ↕
Physical
```

둘 중 하나만 있으면 실제 배포 및 운영 구조를 완전히 설명할 수 없다.

---

## 25.3 업무와 기술을 연결

```text
Business Domain
   ↓
Application
   ↓
Technical System
```

---

## 25.4 Application과 Data를 분리하되 연결

```text
Application
   ↕
Data Subject Area
```

---

# 26. 주요 위험과 GAP

현재 장표만 기준으로 보았을 때 후속 문서에서 주의해야 할 GAP은 다음과 같다.

| 위험/GAP | 영향 | 후속 확인 |
|---|---|---|
| Scope는 3개 영역이나 Interface/Security/Operation이 별도 명시되지 않음 | 책임 경계 불명확 가능 | 다른 장/가이드에서 분리 여부 확인 |
| “운영 환경 별” 환경 목록 미기재 | DEV/TEST/PROD 등 기준 불명확 | 환경 정의서 확인 |
| Physical Inventory 항목 상세 미정 | 인벤토리 편차 | 인벤토리 템플릿 필요 |
| Architecture Standard 범위 미정 | 개발표준과 중복/누락 | 표준 목록 확인 |
| Data Architecture 범위가 Subject Area로만 기술 | 상세 데이터 설계 범위 불명확 | Data Guide 확인 |
| 업무영역별 동작원리 표현 방식 미정 | 문서 일관성 저하 | Runtime Template 필요 |
| 센터별 구성 대상/센터명 미기재 | HA/DR 기준 불명확 | Technical 상세문서 확인 |

---

# 27. 후속 상세 설계에서 반드시 확인할 항목

## Technical

- [ ] 운영환경 목록
- [ ] 논리 시스템 구분
- [ ] 물리 시스템 구분
- [ ] 시스템 Inventory 기준
- [ ] SW Inventory
- [ ] 센터 구성
- [ ] 백업
- [ ] 가용성
- [ ] 장애/복구

## Application

- [ ] Application 분류체계
- [ ] Layer
- [ ] 업무영역
- [ ] 구성요소
- [ ] Runtime
- [ ] 표준화 항목
- [ ] 동작 원리

## Data

- [ ] Subject Area
- [ ] 업무영역 매핑
- [ ] 데이터 소유권
- [ ] 후속 Logical/Physical Model 연계

---

# 28. 검증 체크리스트

- [ ] 본 문서의 아키텍처 목적이 이해관계자에게 명확한가?
- [ ] Application 구성과 분류체계가 실제 시스템과 일치하는가?
- [ ] 운영환경별 논리 시스템 구성이 정의되어 있는가?
- [ ] 기술요소 및 소프트웨어 구성이 문서화되어 있는가?
- [ ] 운영환경별 물리 시스템 구성이 정의되어 있는가?
- [ ] Inventory와 실제 인프라가 일치하는가?
- [ ] Architecture Standard 목록이 별도로 관리되는가?
- [ ] 구성요소의 동작원리가 Runtime 관점으로 설명되는가?
- [ ] 업무영역별 Application 동작원리가 정의되는가?
- [ ] Technical Architecture에서 센터별 구성이 정의되는가?
- [ ] 백업 및 가용성 방식이 기술되어 있는가?
- [ ] Application Architecture 표준화 기준이 정의되어 있는가?
- [ ] Data Subject Area가 확정되어 있는가?
- [ ] 상세 가이드와 Architecture Definition 간 중복/충돌이 없는가?

---

# 29. FACT / ANALYSIS / 확인 필요 구분

| 구분 | 내용 |
|---|---|
| FACT | 문서 위치 `1. 아키텍처 정의 / 1.1 개요` |
| FACT | Application 정의, Technical Architecture 설계, Data Subject Area 정의 |
| FACT | 인프라 담당자, 개발자 및 유관 담당자의 빠른 이해와 업무 적용 목적 |
| FACT | Application 구성/분류체계 정의 |
| FACT | 운영환경별 논리 시스템/기술요소/SW 구성 정의 |
| FACT | 운영환경별 물리 시스템/Inventory 정의 |
| FACT | Architecture Standard 및 구성요소 동작원리 정의 |
| FACT | 업무영역별 Application 동작원리 정의 |
| FACT | Technical / Application / Data Architecture 적용 |
| FACT | Technical에 센터별 구성, 백업, 가용성 포함 |
| FACT | Data Architecture에 데이터 주제영역 포함 |
| FACT | 상세 구성방안/가이드는 각 영역별 가이드 참조 |
| ANALYSIS | 이 장표를 Architecture Baseline Entry Point로 해석 |
| ANALYSIS | Business↔Application↔Technical↔Physical Traceability 필요 |
| ANALYSIS | Architecture Governance 및 표준 준수 검증 필요 |
| ANALYSIS | 상세가이드와 Architecture Definition의 역할 분리 |
| 확인 필요 | 운영환경의 실제 종류 |
| 확인 필요 | 센터별 실제 구성과 명칭 |
| 확인 필요 | Architecture Standard 상세 항목 |
| 확인 필요 | Data Subject Area 실제 분류 |
| 확인 필요 | Interface/Security/Operation이 별도 Architecture Domain인지 여부 |

---

# 30. 장표 해석 시 유의사항

이 장표는 **아키텍처 상세 설계 장표가 아니라 아키텍처 정의 문서 전체의 목적·정의·적용범위를 선언하는 개요 페이지**다.

따라서 다음과 같은 상세사항을 이 장표만으로 확정하면 안 된다.

- 서버 사양
- 구체 미들웨어 제품
- 네트워크 구성
- Application Layer 상세
- Package/Module 상세
- 데이터 Subject Area 목록
- HA/DR 실제 방식
- 백업 주기
- 운영환경 명칭
- Interface Architecture 상세

이러한 내용은 장표 하단에서 직접 지시한 것처럼 **각 영역별 상세 가이드 및 후속 장표**에서 확인해야 한다.

---

# 31. 최종 평가

이 장표는 차세대 정보계 아키텍처의 기술적 상세를 설명하지는 않지만, 전체 아키텍처 문서 체계에서 매우 중요한 역할을 한다.

핵심 구조는 다음과 같다.

```text
Architecture Definition
   │
   ├─ Application
   ├─ Technical
   └─ Data
          │
          ▼
Logical / Physical / Standard / Runtime / Subject Area
          │
          ▼
Infrastructure / Developer / Stakeholder
          │
          ▼
Architecture Understanding
          │
          ▼
Project Application
```

특히 중요한 원칙은 다음과 같다.

1. **Application / Technical / Data 세 축을 최상위 아키텍처 범위로 고정한다.**
2. **구성뿐 아니라 동작원리를 함께 정의한다.**
3. **논리와 물리 아키텍처를 함께 관리한다.**
4. **운영환경별 구성과 Inventory를 기준화한다.**
5. **센터별 백업 및 가용성을 Technical Architecture의 범위로 포함한다.**
6. **업무영역별 Application 동작원리를 정의한다.**
7. **상세 구현은 영역별 가이드로 분리한다.**

따라서 이 장표는 이후의 모든 아키텍처 상세문서가 정합성을 가져야 하는 **Architecture Definition Baseline / Vision Overview**로 평가할 수 있다.

후속 문서는 다음 계층으로 연결되는 것이 자연스럽다.

```text
VISION / 개요
   ↓
Technical Architecture
   ↓
Application Architecture
   ↓
Data Architecture
   ↓
Logical / Physical / Runtime
   ↓
Standard / Guideline
   ↓
Implementation / Operation
```

결론적으로 이 장표는 **차세대 정보계 아키텍처를 누가, 무엇을, 어떤 범위에서 이해하고 적용해야 하는지를 선언하는 최상위 Architecture Definition Vision**이다.
