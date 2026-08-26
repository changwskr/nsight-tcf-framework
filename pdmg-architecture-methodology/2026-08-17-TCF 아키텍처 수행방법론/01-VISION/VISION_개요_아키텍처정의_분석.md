# VISION — 개요 / 아키텍처 정의 분석

> **문서 성격:** 원본 장표(`1. 아키텍처 정의 / 1.1 개요`)를 기준으로 한 분석본이다.  
> **목차(§1~§31)는 유지**하고, 설명 보강·부족한 항목 보완은 각 절 내부와 ANALYSIS 표기로 추가했다.  
> FACT(원본에 있는 것)와 ANALYSIS(해석·보완)를 섞어 읽지 않도록 구분한다.

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

### 이 문서가 답하는 것과 답하지 않는 것

| 답하는 것 (개요 수준) | 답하지 않는 것 (후속 문서) |
|---|---|
| 아키텍처를 왜 정의하는가 | 서버 사양·제품 BOM 상세 |
| 무엇을 Technical / Application / Data로 나눌 것인가 | Layer·패키지·API 명세 |
| 누가 어떤 목적으로 이 정의를 쓰는가 | 개별 업무 프로그램 설계서 |
| 구성·표준·동작원리를 **함께** 정의해야 한다는 원칙 | Timeout·TX·보안의 구현 코드 |
| 상세는 영역별 가이드로 분리한다는 경계 | 운영 Runbook·장애 대응 SOP |

한 문장으로 말하면, 이 장표는 **“무엇을 어떤 범위로 아키텍처라고 부를지”를 고정**하고,  
**“어떻게 구현·운영할지”는 영역별 가이드와 상세 장표에 위임**한다.

### 읽는 순서 권장

```text
§1 핵심 결론 → §3 원본 구조 → §4 계층 재작성
        │
        ├─ 역할이 분명하면 §6 → §20
        ├─ 정의 축이 궁금하면 §7~§12
        ├─ 영역별 범위가 궁금하면 §13~§16
        └─ 후속 작업 착수면 §19 · §26 · §27 · §28
```

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

상단 문장은 **정의 대상(무엇을)** · **독자(누가)** · **활용 목적(왜)** 을 한 문장에 압축한 것이다.  
뒤에 나오는 `목적 / 정의 / 적용 범위` 3단 구조는 이 문장을 풀어쓴 본문에 해당한다.

| 상단 문장의 조각 | 본문에서의 전개 |
|---|---|
| 어플리케이션 정의 | Application Architecture · 구성/분류/동작원리/표준 |
| 기술 아키텍처 설계 | Technical Architecture · 논리/물리/센터/백업/가용성 |
| 데이터 주제 영역 정의 | Data Architecture · Subject Area |
| 인프라·개발·유관 담당자 | §6 사용자 관점 · §20 독자별 활용 흐름 |
| 빠르게 파악하고 업무에 적용 | 공통 참조 문서 · Governance의 이해 단계 |

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

위 계층은 원본 장표의 **목차형 골격**이다.  
후속 문서를 작성·검토할 때는 새 내용이 이 트리의 **어느 가지에 속하는가**를 먼저 묻고,  
가지에 넣기 어려운 내용(예: 보안·인터페이스·운영)은 §26 GAP과 같이 **별도 Domain 여부**를 의사결정한다.

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

### 5.3 “업무에 활용”의 구체적 의미

원본의 “업무에 활용”은 추상적이므로, 역할별로 최소 활용 결과를 다음처럼 해석한다. (**ANALYSIS**)

| 역할 | 활용의 최소 결과 |
|---|---|
| 인프라 | 논리↔물리 매핑·Inventory·센터/가용성 기준을 배포·운영 결정에 사용 |
| 개발자 | Application 분류·표준·동작원리에 맞춰 설계/코딩/리뷰 기준을 맞춤 |
| 업무 개발 | 해당 업무영역 Runtime Flow를 프로그램 설계의 전제로 둠 |
| 데이터 | Subject Area를 모델·소유권·연계의 상위 기준으로 둠 |
| 유관/의사결정 | “이 시스템이 무엇을 포함하고 무엇을 포함하지 않는지”를 공통 언어로 합의 |

목적이 달성되지 않은 상태의 전형적 증상은 다음과 같다.

- 같은 용어(시스템·모듈·서비스)를 조직마다 다르게 씀
- 논리 구성도와 실제 서버 Inventory가 불일치
- 표준은 있으나 Runtime(정상/오류/타임아웃) 설명이 없어 구현이 제각각
- Data Subject Area 없이 테이블부터 설계가 시작됨

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

### 사용자별 “이 장표만으로 충분한가?”

| 역할 | 이 장표만으로 | 반드시 이어서 볼 것 |
|---|---|---|
| 유관 담당자 | 범위·목적 이해에 **충분** | 필요 시 영역별 한 장 요약 |
| 아키텍트 | Baseline 고정에 **충분** | Technical/Application/Data 상세 + Governance |
| 인프라 | 범위 확인용 | 논리/물리도, Inventory, 센터/백업/가용성 가이드 |
| 개발자 | 원칙 확인용 | Application 표준, Runtime, 패키지/Layer 가이드 |
| 데이터 | Subject Area 존재 확인용 | 주제영역 목록·업무 매핑·LDM/PDM 가이드 |

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

### 질문 ↔ 산출물 대응 (ANALYSIS)

| 질문 | 최소 산출물 | 품질 기준(초안) |
|---|---|---|
| Q1 Application 구성·분류 | Application Portfolio / 분류체계 | 업무영역·시스템·앱이 중복 없이 매핑됨 |
| Q2 논리 시스템 | 환경별 Logical Architecture | DEV/TEST/PROD(또는 프로젝트 확정 환경)별 차이 명시 |
| Q3 물리 시스템·Inventory | Physical Architecture + Inventory | 논리 단위 ↔ 물리 단위 추적 가능 |
| Q4 표준·동작원리 | Architecture Standard + Runtime 설명 | 정상/오류/예외 흐름이 구성요소 단위로 설명됨 |
| Q5 업무영역별 동작 | Domain Runtime Flow | 업무마다 “입력→처리→데이터/연계→출력”이 구분됨 |

다섯 질문 중 하나라도 비어 있으면, 개요상 “정의했다”고 말할 수 없다.  
특히 Q4·Q5가 빠지면 **구성도는 있으나 개발·운영 해석이 갈라지는** 전형적 실패로 이어진다.

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

### 8.3 후속 문서에서 채워야 할 최소 항목

원본은 “식별하고 확인할 수 있도록 정의”까지만 말하므로, 상세에서는 최소한 다음을 채운다. (**ANALYSIS**)

- Application ID / 명칭 / 소유 업무영역
- 상위 시스템·하위 Module/Component 경계
- 온라인·배치·연계 등 실행 유형
- 의존 Application / 공유 Common 여부
- 배치되는 Logical System(후속 §9와 연결)

분류가 잘못되면 Traceability(§18) 전체가 흔들리므로, **이름 붙이기 전에 경계부터 합의**하는 것이 안전하다.

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

### 9.2 “운영 환경 별”이 비어 있을 때의 위험

원본은 환경 목록을 적지 않는다. 후속에서 확정하지 않으면 다음 혼선이 생긴다. (**ANALYSIS**)

```text
같은 Logical System 이름
   ├─ DEV 에서는 단일 인스턴스
   ├─ TEST 에서는 이중화 흉내
   └─ PROD 에서는 Active-Active / DR 포함
```

권장 보완(후속 Technical 가이드):

| 항목 | 설명 |
|---|---|
| 환경 목록 | 예: Local / DEV / TEST / STG / PROD (프로젝트 확정명 사용) |
| 환경별 차이표 | 논리 구성·SW 버전·연계 대상·데이터 범위 |
| 공통 vs 차이 | “전 환경 동일”과 “환경만 다름”을 명시적으로 분리 |

### 9.3 기술요소·소프트웨어에 넣을 최소 범주

장표는 범주만 요구한다. 상세 Inventory 전 단계에서라도 다음 범주를 비우지 않는 것이 좋다.

- Application Runtime (WAS/Boot 등)
- Framework / Middleware
- Database / Data Platform
- Messaging / Interface
- Security / Auth 구성요소
- Monitoring / Logging (Observability)

> 위 범주는 **ANALYSIS**이며, Security·Interface·Observability를 Technical에 포함할지 별도 Domain으로 둘지는 §26에서 열린 질문이다.

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

### 10.3 Inventory가 충족해야 할 최소 속성

“Inventory를 확인”하려면 목록 나열만으로는 부족하다. 후속 템플릿에 넣을 최소 속성 예시는 다음과 같다. (**ANALYSIS**)

| 속성 | 목적 |
|---|---|
| 논리 시스템 ID | 논리↔물리 매핑 |
| 호스트/VM/컨테이너 ID | 배치 위치 |
| 센터 / Zone | HA·DR·백업 범위 |
| 환경 | DEV/TEST/PROD 등 |
| SW 스택·버전 | 패치·취약점·호환 |
| 소유 팀 / 운영 책임 | 장애 시 에스컬레이션 |
| 백업·모니터링 대상 여부 | 운영 누락 방지 |

Inventory가 없으면 가용성·백업(§13.2) 논의가 **그림만 있는 선언**으로 남는다.

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

### 11.3 동작원리 설명의 최소 골격

“동작 원리”를 문장 하나로 끝내지 않으려면, 구성요소마다 아래 골격을 채우는 것이 안전하다. (**ANALYSIS**)

```text
1) 역할·책임 (무엇을 하는가 / 하지 않는가)
2) 호출 관계 (누가 호출하고 무엇을 호출하는가)
3) 정상 흐름
4) 오류·예외 흐름
5) 시간·자원 제약 (Timeout, 재시도, 부하 제한 등 — 해당 시)
6) 관찰 포인트 (로그·메트릭·추적 ID)
7) 관련 표준 (네이밍, 트랜잭션, 보안 등)
```

이 골격이 없으면 §25.1의 “구성 + 동작” 원칙이 문서상으로만 남고, 실제 구현은 팀마다 달라진다.

### 11.4 Architecture Standard에 포함할 후보 목록

원본은 표준의 항목을 열거하지 않는다. 후속 표준 목록을 만들 때 후보로 쓸 수 있는 축이다. (**ANALYSIS**)

- 네이밍 / 패키지 / Layer
- 온라인·배치 분리
- 트랜잭션·Timeout·멱등
- 예외·오류코드·사용자 메시지
- 로깅·감사·이미지로그
- 인증·인가·개인정보
- 인터페이스·전문·채널
- 데이터 접근(DAO/SQL) 규칙

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

### 12.2 업무영역별 Runtime 템플릿 (ANALYSIS)

업무마다 장황한 서사 대신, 동일 템플릿으로 채우면 문서 일관성이 유지된다.

| 항목 | 기입 내용 |
|---|---|
| 업무영역 / Application | 명칭·ID |
| 진입점 | 채널·API·배치 Job 등 |
| 정상 처리 단계 | 입력 → 검증 → 업무규칙 → 데이터/연계 → 출력 |
| 주요 데이터 Subject Area | §16과 매핑 |
| 주요 외부 연계 | 동기/비동기 |
| 실패·재처리 원칙 | 재시도 가능 여부, 보상 필요 여부 |
| 비기능 제약 | 응답시간·가용성·보안 (NFR과 연결) |

공통 Architecture만 있고 Domain Runtime이 없으면,  
**“표준은 지켰는데 업무 동작이 설명되지 않는”** 문서 상태가 된다.

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

### 13.3 백업·가용성을 개요에서 다루는 이유

Technical에 백업·가용성을 넣은 것은, 물리 배치만으로는 **생존 설계(NFR Availability)** 를 말할 수 없기 때문이다. (**ANALYSIS**)

후속 Technical 상세에서 최소로 답해야 할 질문:

- 센터(또는 가용 구역) 간 구성은 Active-Active / Active-Standby / DR-only 중 무엇인가?
- 백업 대상은 무엇인가(DB·파일·설정·로그)?
- RPO/RTO 수준의 목표가 어디에 정의되는가? (개요에는 수치 없음 → SLA/NFR 문서와 연결)
- Application 이중화와 Data 이중화의 책임 경계는 어디인가?

개요 장표는 이 질문의 **존재**를 고정하고, 수치·구성안은 영역별 가이드·SLA에 맡긴다.

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

이 모델은 Technical 문서를 쓸 때 **목차 골격**으로 재사용할 수 있다.  
Environment → Logical → Physical → Center/Backup/Availability 순으로 내려가면,  
개요(§13)와 상세 가이드의 절 구조가 어긋나지 않는다.

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

위 흐름은 최소 골격이다. 온라인 업무 Application을 설명할 때는 다음을 보강하는 것이 좋다. (**ANALYSIS**)

```text
채널/API 진입
  ↓
공통 전처리 (인증·전문·컨텍스트)
  ↓
업무 Facade / Service
  ↓
트랜잭션·Timeout 경계
  ↓
DAO / 외부 연계
  ↓
공통 후처리·응답
```

배치·연계 Application은 진입점과 트랜잭션 경계가 달라지므로,  
**분류(§15.2)별로 Runtime 다이어그램을 따로** 두는 편이 안전하다.

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

표준화 문서가 “목록만 있고 준수 검증이 없으면” §21 Governance가 끊긴다.  
따라서 표준 항목마다 **정의서 / 예제 / 검사 방법(리뷰·테스트·정적분석)** 중 최소 하나를 후속에서 연결하는 것이 바람직하다.

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

### 개요 단계 Data의 한계와 보완 방향

이 장표의 Data Architecture는 **Subject Area 확인**으로 범위가 좁다.  
부족한 부분을 후속에서 메울 때 혼동하지 않도록 계층을 명시한다. (**ANALYSIS**)

```text
개요 (본 장표)
  └─ Data Subject Area
        ↓
Data Guide / 상세
  ├─ 업무영역 ↔ Subject Area 매핑
  ├─ 소유권·품질 책임
  ├─ Logical Data Model
  ├─ Physical Data Model
  └─ 연계·복제·적재 경로 (CDC/ETL 등 — Technical과 경계 합의)
```

Subject Area 없이 테이블부터 설계하면 Application Traceability(§18)의 데이터 축이 끊긴다.  
반대로 Subject Area만 있고 매핑·소유권이 없으면 **분류표만 있는 Data Architecture**가 된다.

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

### 연결이 깨질 때의 증상

| 깨진 연결 | 증상 |
|---|---|
| Application ↔ Technical | 앱은 정의됐는데 어느 논리/물리 시스템에서 도는지 모름 |
| Application ↔ Data | 화면/서비스는 있는데 다루는 주제영역·소유 데이터가 불명 |
| Data ↔ Technical | 주제영역은 있는데 저장·적재·백업 위치가 Inventory에 없음 |
| 세 영역 ↔ Runtime | 구성도는 있으나 정상/오류 시 실제 흐름을 설명 못함 |

개요 문서를 읽을 때는 세 상자만 보지 말고, 아래 Runtime으로 모이는 **화살표가 후속 문서에 존재하는가**를 점검 질문으로 삼는다.

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

### Traceability 점검 질문

후속 산출물을 받을 때 개요 기준으로 바로 물을 수 있는 질문이다.

1. 이 업무는 어느 Application에 속하는가?
2. 그 Application은 어느 Logical System에서 실행되는가?
3. 그 Logical System은 어느 Physical / Inventory 항목에 매핑되는가?
4. 다루는 데이터는 어느 Subject Area인가?
5. 장애·백업·가용성 단위는 Technical의 어느 센터/구성에 속하는가?

한 질문에라도 “문서에 없다”면 Traceability가 끊긴 것이다.

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

### 산출물 “완료”의 최소 기준 (ANALYSIS)

| 산출물 | 미완성으로 보는 상태 | 완료로 보는 최소 상태 |
|---|---|---|
| Application 분류체계 | 이름 목록만 존재 | 업무·시스템·실행유형 매핑 완료 |
| 논리 구성도 | 단일 환경 그림만 존재 | 운영환경별 차이 설명 포함 |
| 물리 구성·Inventory | 서버 나열만 | 논리 ID·센터·환경·SW·소유 연결 |
| 동작원리 | 정상 경로 문구만 | 오류·예외(필요 시 Timeout)까지 기술 |
| Subject Area | 영역명만 | 업무 매핑·후속 모델 연계 명시 |
| Architecture Standard | 목차만 | 항목 정의 + 적용/검증 방법 연결 |

이 표는 원본에 없는 **품질 기준 보완**이며, §28 검증 체크리스트와 함께 쓴다.

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

### Governance에서 빠진 채 진행될 때의 결과

```text
정의만 있고 Validation이 없으면
   → 프로젝트마다 “우리 해석”이 갈라짐
   → Inventory·표준·Runtime이 문서와 코드에서 따로 진화
   → 장애·감사 시 “어느 기준이 정식인가?”를 다시 논쟁
```

따라서 개요 장표를 Baseline으로 삼는다면, 후속 계획에 **검토 게이트(설계 리뷰 / 표준 준수 / Runtime 검증)** 를 명시적으로 넣는 것이 이 절의 실천이다.

검증의 유형도 구분해 두면 좋다. (**ANALYSIS**)

| 검증 유형 | 예시 |
|---|---|
| 문서 정합 | 논리↔물리 매핑, 분류체계 중복 여부 |
| 설계 리뷰 | Standard 항목 누락, Domain Runtime 템플릿 충족 |
| Runtime 시험 | 정상·오류·Timeout·가용성 시나리오 |
| Inventory 실사 | 문서 Inventory ↔ 실제 호스트/SW |

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

### 경계가 지켜지지 않을 때

- 개요에 서버 사양·제품명·포트·SQL이 쌓이면 **Vision이 상세설계처럼** 변질된다.
- 반대로 상세 가이드에 “왜 이 영역이 아키텍처 범위인가”만 반복되면 **정의 문서의 역할을 중복**한다.

실무 규칙은 단순하다.

```text
개요: Why / What / Scope / Principle
가이드: How / Config / Build / Operate
둘의 중복이 생기면 → 개요는 원칙만 남기고 상세는 가이드로 이동
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

| 위험/GAP | 영향 | 후속 확인 | 보완 방향 (ANALYSIS) |
|---|---|---|---|
| Scope는 3개 영역이나 Interface/Security/Operation이 별도 명시되지 않음 | 책임 경계 불명확 가능 | 다른 장/가이드에서 분리 여부 확인 | Technical·Application에 포함할지, 별도 Domain으로 둘지 ADR로 결정 |
| “운영 환경 별” 환경 목록 미기재 | DEV/TEST/PROD 등 기준 불명확 | 환경 정의서 확인 | 환경 목록·차이표를 Technical Baseline에 고정 |
| Physical Inventory 항목 상세 미정 | 인벤토리 편차 | 인벤토리 템플릿 필요 | §10.3 최소 속성으로 템플릿 착수 |
| Architecture Standard 범위 미정 | 개발표준과 중복/누락 | 표준 목록 확인 | §11.4 후보 목록으로 표준 Inventory 작성 |
| Data Architecture 범위가 Subject Area로만 기술 | 상세 데이터 설계 범위 불명확 | Data Guide 확인 | Subject Area → LDM/PDM 계층을 Data Guide 서두에 명시 |
| 업무영역별 동작원리 표현 방식 미정 | 문서 일관성 저하 | Runtime Template 필요 | §12.2 템플릿을 Domain 문서 공통 양식으로 채택 |
| 센터별 구성 대상/센터명 미기재 | HA/DR 기준 불명확 | Technical 상세문서 확인 | 센터(또는 가용구역) 명칭·역할표를 먼저 합의 |
| Observability(로그·메트릭·추적) 미언급 | 운영·장애분석 기준 공백 | NFR/운영 가이드 확인 | Application Standard 또는 Operation에 관찰 포인트 포함 |
| NFR·SLA와 개요의 연결 미기재 | 성능·가용성 목표가 아키텍처와 단절 | NFR/SLA 문서 확인 | Technical 가용성·Application Runtime에 NFR 참조를 명시 |
| Validation 절차 미기재 | 정의만 하고 준수 여부를 모름 | Governance 계획 확인 | §21 검증 유형을 프로젝트 게이트에 편입 |

### GAP을 방치하면 생기는 전형적 실패 패턴

```text
1) 그림은 화려하나 Inventory와 불일치
2) 표준 문서는 있으나 Runtime(오류·Timeout)이 팀마다 다름
3) Data Subject Area와 Application 분류가 서로 다른 언어 사용
4) Security/Interface가 “누구 문서에도 정식 주인이 없음”
5) 센터/DR은 발표 자료에만 있고 Technical Baseline에 없음
```

GAP 표의 “후속 확인” 열이 비어 있는 채로 상세설계를 시작하면,  
개요 Baseline(§22)이 **형식상 존재하고 실질상 무력화**된다.

---

# 27. 후속 상세 설계에서 반드시 확인할 항목

## Technical

- [ ] 운영환경 목록
- [ ] 환경별 논리 구성 차이표
- [ ] 논리 시스템 구분
- [ ] 물리 시스템 구분
- [ ] 시스템 Inventory 기준
- [ ] SW Inventory
- [ ] 논리↔물리 매핑
- [ ] 센터(또는 가용 구역) 구성·명칭
- [ ] 백업 대상·방식
- [ ] 가용성 / HA·DR 방식
- [ ] 장애/복구 단위와 책임
- [ ] NFR Availability·Performance와의 참조 관계

## Application

- [ ] Application 분류체계
- [ ] Layer
- [ ] 업무영역
- [ ] 구성요소
- [ ] Runtime (정상 흐름)
- [ ] Runtime (오류·예외·Timeout 등 비정상 흐름)
- [ ] 표준화 항목 목록
- [ ] 표준 항목별 적용·검증 방법
- [ ] 동작 원리 (공통)
- [ ] 동작 원리 (업무영역별 템플릿 적용)
- [ ] 온라인/배치/연계 유형별 Runtime 구분

## Data

- [ ] Subject Area
- [ ] 업무영역 매핑
- [ ] 데이터 소유권
- [ ] 품질·권한 책임
- [ ] 후속 Logical/Physical Model 연계
- [ ] Application↔Subject Area Traceability

## 개요에 직접 없으나 후속에서 경계 합의가 필요한 항목 (ANALYSIS)

원본 Scope 3축 밖에 자주 등장하므로, **어디에 소속시킬지**를 먼저 정한다.

### Interface / Integration

- [ ] 채널·전문·API·이벤트 연계의 아키텍처 소속 (Application vs 별도 Interface)
- [ ] 동기/비동기 패턴과 Timeout 계층 정합

### Security

- [ ] 인증·인가·개인정보·전송보호의 문서 소속
- [ ] Application Standard와의 중복/누락 점검

### Operation / Observability

- [ ] 로그·감사·모니터링·추적 ID 기준
- [ ] Inventory·가용성과의 운영 연계

---

# 28. 검증 체크리스트

### 개요·범위

- [ ] 본 문서의 아키텍처 목적이 이해관계자에게 명확한가?
- [ ] Technical / Application / Data 세 축의 적용 범위가 합의되었는가?
- [ ] 상세 가이드와 Architecture Definition 간 중복/충돌이 없는가?
- [ ] Interface / Security / Operation을 세 축에 포함할지 별도 Domain으로 둘지 결정되었는가?

### Technical

- [ ] 운영환경별 논리 시스템 구성이 정의되어 있는가?
- [ ] 기술요소 및 소프트웨어 구성이 문서화되어 있는가?
- [ ] 운영환경별 물리 시스템 구성이 정의되어 있는가?
- [ ] Inventory와 실제 인프라가 일치하는가?
- [ ] Technical Architecture에서 센터별 구성이 정의되는가?
- [ ] 백업 및 가용성 방식이 기술되어 있는가?
- [ ] 논리↔물리 Traceability가 샘플 업무 기준으로 증명되는가?

### Application

- [ ] Application 구성과 분류체계가 실제 시스템과 일치하는가?
- [ ] Architecture Standard 목록이 별도로 관리되는가?
- [ ] Application Architecture 표준화 기준이 정의되는가?
- [ ] 구성요소의 동작원리가 Runtime 관점으로 설명되는가?
- [ ] 업무영역별 Application 동작원리가 정의되는가?
- [ ] 정상 흐름뿐 아니라 오류·예외 흐름이 포함되는가?

### Data

- [ ] Data Subject Area가 확정되어 있는가?
- [ ] 업무영역·Application과 Subject Area 매핑이 있는가?
- [ ] 후속 LDM/PDM 가이드와의 경계가 명확한가?

### Governance

- [ ] 설계 검토·표준 준수·Runtime 검증 게이트가 계획에 있는가?
- [ ] Baseline 변경 시 ADR/승인 절차가 있는가? (§22)

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
| ANALYSIS | 동작원리는 정상뿐 아니라 오류·예외·Timeout 등 비정상 흐름을 포함해야 함 |
| ANALYSIS | Inventory는 목록이 아니라 논리 매핑·센터·소유·SW를 담는 관리 대상 |
| ANALYSIS | Interface/Security/Operation/Observability는 Scope 확장 또는 소속 합의가 필요 |
| ANALYSIS | 산출물 완료 기준(§19)과 검증 유형(§21)을 개요 Governance에 연결해야 함 |
| 확인 필요 | 운영환경의 실제 종류 |
| 확인 필요 | 센터별 실제 구성과 명칭 |
| 확인 필요 | Architecture Standard 상세 항목 |
| 확인 필요 | Data Subject Area 실제 분류 |
| 확인 필요 | Interface/Security/Operation이 별도 Architecture Domain인지 여부 |
| 확인 필요 | NFR/SLA 수치와 Technical·Application Runtime 문서의 공식 연결 지점 |
| 확인 필요 | Observability(로그·메트릭·추적)의 정식 문서 위치 |

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
- NFR 수치(응답시간·RPO/RTO 등)
- 보안 통제의 구현 상세

이러한 내용은 장표 하단에서 직접 지시한 것처럼 **각 영역별 상세 가이드 및 후속 장표**에서 확인해야 한다.

### 해석 시 추가 주의

- 원본에 없는 환경명·센터명·표준 항목을 이 개요에 **사실처럼 고정하지 말 것** (ANALYSIS/예시와 FACT를 섞지 말 것)
- “동작 원리”를 정상 경로 한 줄로 축소 해석하지 말 것
- 세 축 밖에 있는 Security/Interface를 “없으므로 불필요”로 단정하지 말 것 — **소속 미정이 정확한 상태**다

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
8. **정의 이후 Validation(설계 리뷰·표준 준수·Runtime 검증)을 Governance에 포함한다.** (ANALYSIS 보완)
9. **Interface / Security / Operation / Observability의 문서 소속을 열린 과제로 관리한다.** (ANALYSIS 보완)

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
Implementation / Operation / Validation
```

### 이 개요 문서의 한계 (명확히)

- 환경·센터·Subject Area·표준 항목의 **실명 목록은 없다**
- NFR/SLA **수치를 제공하지 않는다**
- Runtime의 **구현 방식(프레임워크·코드)을 규정하지 않는다**
- 다만 후속이 채워야 할 **질문·산출물·검증 항목의 골격은 제공한다**

결론적으로 이 장표는 **차세대 정보계 아키텍처를 누가, 무엇을, 어떤 범위에서 이해하고 적용해야 하는지를 선언하는 최상위 Architecture Definition Vision**이다.
