# NSIGHT — 표준프레임워크 아키텍처 구성 분석

## 1. 핵심 결론

이 자료는 NEXT 정보계 차세대 애플리케이션의 **표준 실행 프레임워크와 배치 실행 프레임워크를 하나의 기준선으로 제시하는 아키텍처 자료**다.

원본 3개 장표가 직접 제시하는 구조를 통합하면 다음과 같다.

```text
NEXT 정보계 차세대 애플리케이션 표준
        │
        ├─ 온라인 / 요청-응답 표준
        │    │
        │    └─ NH Cloud Framework
        │         ├─ Controller
        │         ├─ FWK LIB
        │         ├─ AOP
        │         ├─ Service
        │         ├─ DTO
        │         ├─ DAO
        │         ├─ O-R Mapper
        │         ├─ Config
        │         └─ Library
        │
        ├─ 업무유형별 Controller 표준
        │    ├─ NhinsController
        │    ├─ NhFileController
        │    ├─ NhRDController
        │    ├─ NhInboundController
        │    ├─ NhSsoController
        │    └─ NhController
        │
        └─ 배치 표준
             ├─ 작업 자동화 관리
             ├─ 작업 스케줄러
             ├─ Control-M Agent
             ├─ 배치 Shell
             └─ Spring Batch
                  ├─ Job Launcher
                  ├─ Job
                  ├─ Step
                  ├─ ItemReader / ItemProcessor / ItemWriter
                  ├─ Tasklet
                  └─ Spring Job Repository
```

이 자료의 핵심은 단순히 Spring 계층을 나열하는 것이 아니다.

```text
어떤 요청이 어떤 Controller로 들어오는가?
        ↓
시스템 공통 처리는 어디에서 수행되는가?
        ↓
업무 공통 처리는 어디에서 수행되는가?
        ↓
업무 Service와 데이터 접근은 어떻게 분리되는가?
        ↓
Config와 Library는 어떻게 표준화되는가?
        ↓
개발도구·CI/CD·NEXUS·Master Solution은 어떻게 연결되는가?
        ↓
온라인과 배치는 각각 어떤 실행모델을 사용하는가?
```

를 하나의 **Application Framework Baseline**으로 고정하는 데 있다.

또한 온라인과 배치를 함께 보면 전체 표준의 방향은 다음처럼 정리된다.

```text
온라인 거래
Client → Controller → System Pre/Post → Business Pre/Post
       → Service → DAO/O-R Mapper → External Resource

배치 거래
관리/스케줄 → Control-M Agent → Batch Shell → Spring Batch
           → Job → Step → Reader/Processor/Writer 또는 Tasklet
           → Job Repository / 원장 Database
```

즉 이 자료는 이후 개발표준, 패키지구조, 예외처리, 로깅, 트랜잭션, 데이터접근, 배포, 운영통제, 배치개발 가이드의 **상위 참조 프레임워크** 역할을 한다.

---

# 2. 원본 장표 메타정보

## 2.1 원본 자료 구성

원본 PowerPoint는 총 3개 장표로 구성된다.

| 장표 | 제목/주제 | 핵심 내용 |
|---:|---|---|
| 1 | `NH Cloud Framework 표준 아키텍처 구성` | 온라인 애플리케이션 프레임워크 전체 구조 |
| 2 | Controller 유형 정의 | 6개 표준 Controller의 책임과 사용 목적 |
| 3 | `배치 표준 아키텍처 구성` | Control-M + Shell + Spring Batch 기반 배치 실행 구조 |

---

## 2.2 장표 1 제목

```text
NH Cloud Framework 표준 아키텍처 구성
```

상단 설명:

```text
NEXT 정보계 차세대 어플리케이션 프레임워크의 아키텍처 구성도이다.
```

---

## 2.3 장표 2 상단 설명

원본에서 확인되는 문장:

> 정보계 프로젝트에서 사용하는 Controller, Service는 1:N 형태로 제공되며 각 업무 요건에 따라 NhinsController, NhFileController, NhRDController, NhInboundController, NhSsoController, NhController로 구분되어 사용함

이 문장은 **하나의 Service 계층에 대해 요청 성격별 Controller 진입유형을 분리해서 사용할 수 있음**을 직접 제시한다.

다만 `1:N`의 정확한 객체 수명주기나 Spring Bean cardinality를 뜻하는지는 장표만으로 확정할 수 없다.

따라서 본 문서에서는 다음처럼 해석한다.

```text
[FACT]
업무 요건에 따라 복수 Controller 유형을 구분하여 사용

[ANALYSIS]
Controller는 채널/요청형태별 Inbound Adapter 역할로 분리되고,
Service는 그 뒤의 업무기능을 담당하는 구조로 이해 가능
```

---

## 2.4 장표 3 제목

```text
배치 표준 아키텍처 구성
```

상단 설명은 장표 1과 동일하게 다음 문장을 사용한다.

```text
NEXT 정보계 차세대 어플리케이션 프레임워크의 아키텍처 구성도이다.
```

---

# 3. 원본 장표 1 전체 구조 — 상세 텍스트 재현

> 아래 그림은 원본 장표의 영역, 박스, 주요 연결방향, 컴포넌트명을 최대한 보존하여 텍스트로 재구성한 것이다.

```text
┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                NH Cloud Framework 표준 아키텍처 구성                                         │
│                                                                                                              │
│                  NEXT 정보계 차세대 어플리케이션 프레임워크의 아키텍처 구성도                                │
└──────────────────────────────────────────────────────────────────────────────────────────────────────────────┘

┌──────────────────┐
│      CLIENT      │
├──────────────────┤
│ 범용 단말        │
│                  │
│ 전용 단말        │
│                  │
│ CtoC 연계        │
└────────┬─────────┘
         │ request
         ▼
┌───────────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                      NH Cloud Framework                                                │
│                                                                                                       │
│ ┌────────────────┐   ┌────────────────┐   ┌────────────────┐   ┌────────────────┐   ┌───────────────┐ │
│ │   Controller   │   │    FWK LIB     │   │      AOP       │   │    SERVICE     │   │      DAO      │ │
│ ├────────────────┤   ├────────────────┤   ├────────────────┤   ├────────────────┤   ├───────────────┤ │
│ │ Nhins (화면)   │→  │ 시스템 선 처리 │→  │ 업무 선 처리   │→  │ Service (Biz)  │→  │ Data 변환     │ │
│ │ NhFile (파일)  │   │ 시스템 후 처리 │   │ 업무 후 처리   │   └───────┬────────┘   │ Mapper Call   │ │
│ │ NhRD (레포트)  │   └────────────────┘   └────────────────┘           │            └───────────────┘ │
│ │ NhInbound      │                                                   │                    │           │
│ │ NhSSO          │                                                   ▼                    │           │
│ │ Nh (Default)   │                                            ┌────────────────┐          │           │
│ └────────────────┘                                            │      DTO       │          │           │
│                                                               ├────────────────┤          │           │
│                                                               │ getter         │          ▼           │
│                                                               │ setter         │   ┌───────────────┐ │
│                                                               └────────────────┘   │  O-R Mapper   │ │
│                                                                                     ├───────────────┤ │
│                                                                                     │ Query Mapping │ │
│                                                                                     │ Query Execute │ │
│                                                                                     └───────────────┘ │
│                                                                                                       │
│ ┌─────────────────────────────────────────────┐   ┌──────────────────────────────────────────────────┐ │
│ │                    Config                   │   │                     Library                      │ │
│ ├─────────────────────────────────────────────┤   ├──────────────────────────────────────────────────┤ │
│ │ application.yml  │ manifest.yml │ gradle build│ │ NH 공통 │ MIDAS │ [해칭 영역/텍스트 미식별]      │ │
│ │ log4j2.xml       │ 업무config.yml│ 배포.sh     │ │         │ Spring Boot │ File Handle                │ │
│ │ 메시지yml                                      │ │ Log4j2  │ Utility     │ SSO                        │ │
│ │                                                │ │ 모니터링│ 암호화      │ coverage                   │ │
│ ├─────────────────────────────────────────────┤   └──────────────────────────────────────────────────┘ │
│ │             동적 Config Handler            │                                                       │
│ └─────────────────────────────────────────────┘                                                       │
└───────────────────────────────────────────────────────────────────────────────────────────────────────┘
         │ response
         ▼
    CLIENT / CtoC

외부/운영 연계 영역
────────────────────────────────────────────────────────────────────────────────────────────────────────

Framework 우측 연계 대상
    ├─ Database
    ├─ FOS
    ├─ API G/W (Cruz APIM)
    └─ Service Registry

동적 Config 관리
    ┌──────────────────────────┐
    │      Master Solution     │
    ├──────────────────────────┤
    │ Admin (관리 UI)          │
    │ Master (서버)            │
    │ DB                       │
    └─────────────┬────────────┘
                  │ 동적 Config 관리
                  ▼
         동적 Config Handler

개발 / 배포
    ┌──────────────────────────────────────────────────────────┐
    │ 개발툴 (STS IDE)                                        │
    ├──────────────────────────────────────────────────────────┤
    │ NH Cloud Framework                                      │
    │ Controller / AOP / Service / DAO / DTO / Mapper / O-R  │
    │ Config / Library                                        │
    │                                                          │
    │ Plug-In                                                  │
    │ DTO Plug-In / GIT Plug-In / BOOT Dashboard              │
    └────────────────────┬─────────────────────────────────────┘
                         │ 배포
                         ▼
              ┌──────────────────────┐
              │ CI/CD                │
              │ GitLabRunner         │
              │ GitLab               │
              │ NEXUS                │
              └──────────────────────┘
```

---

# 4. 장표 1 핵심 구조를 계층적으로 재작성

```text
NH Cloud Framework
│
├─ Inbound / Client
│   ├─ 범용 단말
│   ├─ 전용 단말
│   └─ CtoC 연계
│
├─ Controller
│   ├─ Nhins
│   ├─ NhFile
│   ├─ NhRD
│   ├─ NhInbound
│   ├─ NhSSO
│   └─ Nh(Default)
│
├─ Framework Common
│   └─ FWK LIB
│       ├─ 시스템 선 처리
│       └─ 시스템 후 처리
│
├─ Business Common
│   └─ AOP
│       ├─ 업무 선 처리
│       └─ 업무 후 처리
│
├─ Business
│   └─ Service(Biz)
│
├─ Data Contract
│   └─ DTO
│       ├─ getter
│       └─ setter
│
├─ Data Access
│   ├─ DAO
│   │   ├─ Data 변환
│   │   └─ Mapper Call
│   └─ O-R Mapper
│       ├─ Query Mapping
│       └─ Query Execute
│
├─ Configuration
│   ├─ application.yml
│   ├─ manifest.yml
│   ├─ gradle build
│   ├─ log4j2.xml
│   ├─ 업무config.yml
│   ├─ 배포.sh
│   ├─ 메시지yml
│   └─ 동적 Config Handler
│
├─ Library
│   ├─ NH 공통
│   ├─ MIDAS
│   ├─ Spring Boot
│   ├─ File Handle
│   ├─ Log4j2
│   ├─ Utility
│   ├─ SSO
│   ├─ 모니터링
│   ├─ 암호화
│   └─ coverage
│
├─ External / Platform Integration
│   ├─ Database
│   ├─ FOS
│   ├─ API G/W (Cruz APIM)
│   └─ Service Registry
│
├─ Runtime Configuration Management
│   └─ Master Solution
│       ├─ Admin(관리 UI)
│       ├─ Master(서버)
│       └─ DB
│
└─ Development / Delivery
    ├─ STS IDE
    ├─ DTO Plug-In
    ├─ GIT Plug-In
    ├─ BOOT Dashboard
    ├─ GitLabRunner
    ├─ GitLab
    └─ NEXUS
```

---

# 5. CLIENT 영역 상세 분석

## 5.1 원본 FACT

원본은 Client 유형을 다음 3개로 제시한다.

```text
CLIENT
├─ 범용 단말
├─ 전용 단말
└─ CtoC 연계
```

Client와 Framework 사이에는 `request`, `response` 방향이 표시되어 있다.

---

## 5.2 설계 의미

이 구성은 Framework가 단일 UI 채널만을 대상으로 하지 않고, **사용자 단말과 시스템 간 연계 모두를 수용하는 공통 진입 기반**임을 보여준다.

```text
사용자 채널
   ├─ 범용 단말
   └─ 전용 단말
        │
        ├──────────────┐
        │              │
        ▼              ▼
   화면형 요청      특수 목적 요청

시스템 채널
   └─ CtoC 연계
        │
        ▼
   표준 요청/응답
```

> `표준 요청/응답`이라는 표현은 장표 2의 NhController 설명에서 직접 확인된다. CtoC가 항상 같은 프로토콜을 사용한다는 추가 해석은 하지 않는다.

---

# 6. Controller 영역 상세 분석

## 6.1 원본 FACT

장표 1은 다음 Controller 유형을 제시한다.

```text
Controller
├─ Nhins (화면)
├─ NhFile (파일)
├─ NhRD (레포트)
├─ NhInbound
├─ NhSSO
└─ Nh (Default)
```

장표 2는 이를 클래스 수준 명칭으로 다음과 같이 구체화한다.

```text
NhinsController
NhFileController
NhRDController
NhInboundController
NhSsoController
NhController
```

---

## 6.2 Controller의 아키텍처 역할

[ANALYSIS]

원본 구조에서 Controller는 Client와 FWK LIB 사이에 배치되어 있으므로 다음 역할의 **Inbound Boundary**로 해석할 수 있다.

```text
외부 요청
   ↓
요청 유형 식별
   ↓
적합한 Controller 선택
   ↓
Framework 공통 처리 진입
```

이때 핵심은 **업무 요건에 따라 Controller를 분리**한다는 점이다.

즉 다음과 같은 분리다.

```text
화면 요청      → NhinsController
파일 요청      → NhFileController
리포트 요청    → NhRDController
Inbound 요청   → NhInboundController
SSO 요청       → NhSsoController
C2C 요청       → NhController
```

---

# 7. 장표 2 — 표준 Controller 6종 원문 정리

## 7.1 NhinsController

### 원본 FACT

> Nhins Controller는 UI 프레임워크(xFrame)과 연계하여 업무 서비스 호출 시 사용하는 Controller 입니다.

구조화:

```text
UI Framework (xFrame)
       │
       ▼
NhinsController
       │
       ▼
업무 Service 호출
```

### 분석

화면 중심 업무요청의 표준 진입점으로 해석된다.

---

## 7.2 NhFileController

### 원본 FACT

> NhFile Controller는 UI(xFrame)에서 파일 Up/Download 요청시 사용하는 Controller 입니다.

> PaaS 환경에서 DB, ObjectStorage 간 파일 송수신을 할 수 있도록 제공합니다.

구조화:

```text
UI(xFrame)
   │
   │ File Upload / Download
   ▼
NhFileController
   │
   ├─ DB
   └─ ObjectStorage
```

### 분석

일반 업무 Controller와 파일 I/O 책임을 분리한다는 점이 중요하다.

장표 1의 외부 `FOS`와 장표 2의 `ObjectStorage`가 동일한 대상인지 여부는 자료만으로 확정할 수 없다.

```text
[확인 필요]
FOS = ObjectStorage 인지 여부
```

---

## 7.3 NhRDController

### 원본 FACT

> NhRD Controller는 UI(xFrame)에서 RD(Report Designer) 연계시 DB 처리 수행간 사용하는 Controller 입니다.

구조화:

```text
UI(xFrame)
   │
   ▼
RD(Report Designer)
   │
   ▼
NhRDController
   │
   ▼
DB 처리
```

### 분석

리포트/출력 계열의 데이터처리를 일반 화면 Controller와 분리하는 목적이 있다.

---

## 7.4 NhInboundController

### 원본 FACT

> NhInbound Controller는 Inbound 거래(EAI, 외부 JSON 전문)시 로직 처리를 수행할 때 사용하는 Controller 입니다.

구조화:

```text
EAI
또는
외부 JSON 전문
      │
      ▼
NhInboundController
      │
      ▼
Inbound 로직 처리
```

### 분석

일반 사용자 화면과 외부 시스템 연계를 별도 Inbound 경계로 분리한다.

---

## 7.5 NhSsoController

### 원본 FACT

> NhSso Controller는 NH 통합로그인 SSO 처리를 수행하는 Controller 입니다.

구조화:

```text
NH 통합로그인
      │
      ▼
NhSsoController
      │
      ▼
SSO 처리
```

### 분석

인증/로그인 성격의 요청을 일반 업무요청과 별도 경계로 관리하는 구조다.

다만 토큰방식, 세션방식, IdP 프로토콜 등은 이 자료에서 확인되지 않는다.

---

## 7.6 NhController

### 원본 FACT

> Nh Controller는 C2C(Container to Container) 통신시 사용하는 Controller로 표준전문을 기반으로 요청 / 응답을 수행합니다.

구조화:

```text
Container A
    │
    │ 표준전문
    ▼
NhController
    │
    │ 요청 / 응답
    ▼
Container B
```

### 분석

C2C 연계의 핵심은 직접 Java 호출이 아니라 **표준전문 기반 요청/응답 경계**가 있다는 점이다.

---

# 8. Controller 분류체계 요약

| Controller | 원본 용도 | 주요 진입 성격 | 직접 확인되는 외부 연계 |
|---|---|---|---|
| NhinsController | 업무 서비스 호출 | 화면/UI | xFrame |
| NhFileController | 파일 Up/Download | 파일 | DB, ObjectStorage |
| NhRDController | RD 연계 DB 처리 | Report | xFrame, RD, DB |
| NhInboundController | Inbound 거래 처리 | 시스템 연계 | EAI, 외부 JSON 전문 |
| NhSsoController | 통합로그인 | 인증 | NH 통합로그인 SSO |
| NhController | C2C 요청/응답 | Container 간 연계 | 표준전문 |

---

# 9. Controller 선택 규칙 — 아키텍처 관점

[ANALYSIS]

원본의 6개 Controller 유형을 선택규칙으로 바꾸면 다음처럼 이해할 수 있다.

```text
요청이 화면 업무 호출인가?
  └─ YES → NhinsController

요청이 파일 Up/Download 인가?
  └─ YES → NhFileController

요청이 RD/Report Designer 연계인가?
  └─ YES → NhRDController

요청이 EAI 또는 외부 JSON 전문인가?
  └─ YES → NhInboundController

요청이 NH 통합로그인 SSO 인가?
  └─ YES → NhSsoController

요청이 C2C 표준전문인가?
  └─ YES → NhController
```

이 규칙은 **Controller가 업무기능 자체가 아니라 요청유형에 따른 진입 표준**이라는 해석을 강화한다.

---

# 10. FWK LIB — 시스템 선·후처리

## 10.1 원본 FACT

장표 1은 Controller 다음에 `FWK LIB`를 두고 다음 두 기능을 표시한다.

```text
FWK LIB
├─ 시스템 선 처리
└─ 시스템 후 처리
```

---

## 10.2 아키텍처 의미

[ANALYSIS]

`시스템 선 처리 / 시스템 후 처리`라는 명칭은 업무별 로직 이전·이후에 공통 시스템 처리를 수행하는 **Framework Common Boundary**로 해석할 수 있다.

```text
Controller
    │
    ▼
시스템 선 처리
    │
    ▼
업무 처리
    │
    ▼
시스템 후 처리
```

다만 다음 세부 항목은 원본 장표에 직접 나열되어 있지 않다.

```text
인증
GUID
MDC
공통 Header
전문 Logging
공통 예외
```

따라서 이를 이 자료의 FACT로 단정하면 안 된다.

---

# 11. AOP — 업무 선·후처리

## 11.1 원본 FACT

```text
AOP
├─ 업무 선 처리
└─ 업무 후 처리
```

AOP 영역은 FWK LIB 뒤, SERVICE 앞에 위치한다.

---

## 11.2 시스템 선후처리와 업무 선후처리의 분리

[ANALYSIS]

원본 배치만으로도 최소한 다음 책임 분리는 확인할 수 있다.

```text
시스템 공통
FWK LIB
   │
   ├─ 시스템 선 처리
   └─ 시스템 후 처리

업무 공통
AOP
   │
   ├─ 업무 선 처리
   └─ 업무 후 처리
```

이것은 매우 중요한 설계 포인트다.

```text
System Concern ≠ Business Concern
```

즉 시스템 공통 책임을 업무 AOP에 섞거나, 업무 공통정책을 FWK LIB에 무분별하게 넣지 않는 구조로 해석할 수 있다.

---

# 12. SERVICE 영역 상세 분석

## 12.1 원본 FACT

```text
SERVICE
└─ Service (Biz)
```

서비스 영역은 AOP 다음에 배치된다.

---

## 12.2 설계 의미

[ANALYSIS]

`Service (Biz)`라는 명칭은 이 계층이 **업무 로직의 중심**임을 명확히 한다.

```text
Controller
   ↓
Framework Common
   ↓
Business Common AOP
   ↓
Service(Biz)
   ↓
Data Access
```

즉 화면/파일/인바운드/SSO/C2C 등의 진입 방식이 달라도 업무로직은 Service 계층에서 처리하도록 정규화하는 방향으로 이해할 수 있다.

---

# 13. DTO 영역 상세 분석

## 13.1 원본 FACT

```text
DTO
├─ getter
└─ setter
```

DTO는 Service 아래쪽에 별도 구성요소로 배치된다.

---

## 13.2 설계 의미

[ANALYSIS]

DTO는 계층 간 데이터 전달에 사용하는 객체로 해석할 수 있으나, 이 장표에서는 Request/Response DTO 분리나 Validation 정책까지 제시하지 않는다.

확인 가능한 수준:

```text
Service
  │
  └─ DTO
       ├─ getter
       └─ setter
```

확인할 수 없는 수준:

```text
DTO Naming Rule
Input/Output 분리
Immutable 여부
Bean Validation 여부
Serialization 정책
```

---

# 14. DAO 영역 상세 분석

## 14.1 원본 FACT

```text
DAO
├─ Data 변환
└─ Mapper Call
```

---

## 14.2 설계 의미

DAO의 책임은 장표에서 비교적 명확하다.

```text
업무 Service
    │
    ▼
DAO
    ├─ Data 변환
    └─ Mapper Call
```

즉 DAO는 단순 Query 문자열 보관소가 아니라 **업무 데이터와 Mapper 호출 사이의 데이터 접근 경계**다.

---

# 15. O-R Mapper 영역 상세 분석

## 15.1 원본 FACT

```text
O-R Mapper
├─ Query Mapping
└─ Query Execute
```

---

## 15.2 DAO와 O-R Mapper의 관계

원본 구성요소를 그대로 연결하면 다음과 같다.

```text
Service(Biz)
     │
     ▼
DAO
 ├─ Data 변환
 └─ Mapper Call
     │
     ▼
O-R Mapper
 ├─ Query Mapping
 └─ Query Execute
     │
     ▼
Database
```

마지막 `Database` 연결은 전체 장표의 외부 DB 배치를 고려한 **구조적 해석**이며, 선의 정확한 연결종점은 상세 설계서에서 재확인해야 한다.

---

# 16. Config 영역 상세 분석

## 16.1 원본 FACT

원본 Config 영역은 다음 항목을 포함한다.

```text
Config
├─ application.yml
├─ manifest.yml
├─ gradle build
├─ log4j2.xml
├─ 업무config.yml
├─ 배포.sh
├─ 메시지yml
└─ 동적 Config Handler
```

---

## 16.2 Config의 의미

이 구성은 Framework의 실행이 Java 코드만으로 구성되는 것이 아니라 다음을 함께 표준화한다는 점을 보여준다.

```text
Runtime Config
+ Build Config
+ Logging Config
+ Business Config
+ Message Config
+ Deployment Script
+ Dynamic Configuration
```

---

## 16.3 Config 유형별 역할

| 항목 | 원본에서 확인되는 이름 | 문서상 해석 |
|---|---|---|
| application.yml | Spring 계열 설정 파일명 | Runtime 설정 후보 |
| manifest.yml | manifest | 배포/환경 메타정보 후보 |
| gradle build | Gradle Build | Build 구성 |
| log4j2.xml | Log4j2 설정 | Logging 구성 |
| 업무config.yml | 업무 Config | 업무별 설정 |
| 배포.sh | 배포 Shell | 배포 실행 |
| 메시지yml | Message YML | 메시지 외부화 |
| 동적 Config Handler | Dynamic Handler | 동적 설정 반영 |

> `후보`라고 표현한 항목은 원본에 상세 필드/처리방식이 없기 때문이다.

---

# 17. 동적 Config Handler와 Master Solution

## 17.1 원본 FACT

장표 하단에는 별도의 `Master Solution`이 존재한다.

```text
Master Solution
├─ Admin (관리 UI)
├─ Master (서버)
└─ DB
```

그리고 Master Solution에서 Framework 내부 `동적 Config Handler` 방향으로 `동적 Config 관리` 연결이 표시된다.

---

## 17.2 전체 동적 설정 흐름

```text
관리자
   │
   ▼
Admin (관리 UI)
   │
   ▼
Master (서버)
   │
   ├─ DB
   │
   └─ 동적 Config 관리
          │
          ▼
    동적 Config Handler
          │
          ▼
    Application Runtime
```

---

## 17.3 아키텍처 의미

[ANALYSIS]

정적 파일만으로 설정을 관리하는 구조와 별도로 **중앙 관리형 동적 설정 구조**를 고려하고 있음을 보여준다.

다만 다음은 원본에서 확인되지 않는다.

```text
Polling / Push 방식
Refresh Scope
Config Version
Rollback 방식
권한모델
감사로그
암호화방식
```

따라서 상세 운영설계에서 반드시 보강해야 한다.

---

# 18. Library 영역 상세 분석

## 18.1 원본 FACT

원본에서 확인되는 Library는 다음과 같다.

```text
Library
├─ NH 공통
├─ MIDAS
├─ [해칭 영역 / 텍스트 미식별]
├─ Spring Boot
├─ File Handle
├─ Log4j2
├─ Utility
├─ SSO
├─ 모니터링
├─ 암호화
└─ coverage
```

---

## 18.2 Library 분류

[ANALYSIS]

기능 관점으로 재분류하면 다음과 같이 볼 수 있다.

| 분류 | 구성요소 |
|---|---|
| 사내/공통 | NH 공통, MIDAS |
| Framework Runtime | Spring Boot |
| File | File Handle |
| Logging | Log4j2 |
| Utility | Utility |
| Security/Auth | SSO, 암호화 |
| Operations | 모니터링 |
| Quality | coverage |
| 확인필요 | 해칭 영역의 미식별 항목 |

---

# 19. 외부 연계 대상 상세 분석

## 19.1 원본 FACT

Framework 우측에는 다음 대상이 표시되어 있다.

```text
Database
FOS
API G/W (Cruz APIM)
Service Registry
```

또한 하단에는 다음 개발/배포 대상이 표시된다.

```text
CI/CD
├─ GitLabRunner
├─ GitLab
└─ NEXUS
```

---

## 19.2 외부 연계 역할 모델

```text
NH Cloud Framework
    │
    ├─ Data Resource
    │    └─ Database
    │
    ├─ File / Storage Resource
    │    └─ FOS
    │
    ├─ API Integration
    │    └─ API G/W (Cruz APIM)
    │
    ├─ Service Discovery / Registry
    │    └─ Service Registry
    │
    └─ Delivery
         ├─ GitLabRunner
         ├─ GitLab
         └─ NEXUS
```

> 위의 `Data Resource`, `File / Storage Resource`, `Service Discovery` 같은 카테고리명은 이해를 위한 ANALYSIS다. 원본에는 제품/대상명만 표시되어 있다.

---

# 20. 개발툴(STS IDE) 영역 상세 분석

## 20.1 원본 FACT

하단 개발영역은 다음과 같다.

```text
개발툴 (STS IDE)
│
├─ NH Cloud Framework
│   ├─ Controller
│   ├─ AOP
│   ├─ Service
│   ├─ DAO
│   ├─ DTO
│   ├─ Mapper
│   ├─ O-R
│   ├─ Config
│   └─ Library
│
└─ Plug-In
    ├─ DTO Plug-In
    ├─ GIT Plug-In
    └─ BOOT Dashboard
```

---

## 20.2 설계 의미

이 구조는 **Runtime Framework와 Developer Experience를 분리하지 않고 하나의 표준체계로 제공**하려는 의도를 보여준다.

```text
아키텍처 표준
    ↓
개발툴 제공
    ↓
Framework 구조 사용
    ↓
Plug-In으로 반복작업 보조
    ↓
Git / Build / Deploy
```

---

# 21. CI/CD 및 Artifact 관리 구조

## 21.1 원본 FACT

개발툴/Library 영역에서 우측 CI/CD 영역으로 `배포` 선이 연결된다.

```text
개발툴 / Library
      │
      │ 배포
      ▼
CI/CD
├─ GitLabRunner
├─ GitLab
└─ NEXUS
```

---

## 21.2 분석

이 장표만으로 다음은 확정할 수 없다.

```text
GitLab과 GitLabRunner의 세부 Pipeline 순서
NEXUS가 Maven/Gradle Artifact용인지 Container Image용인지
배포 승인 절차
운영환경 배포방식
Rollback 방식
```

따라서 CI/CD는 **연계 존재만 FACT**, 상세 파이프라인은 별도 설계대상이다.

---

# 22. 온라인 전체 요청-응답 흐름

원본 구조를 End-to-End로 재작성하면 다음과 같다.

```text
[범용 단말 / 전용 단말 / CtoC]
            │
            │ request
            ▼
        Controller
            │
            ▼
          FWK LIB
            │
      시스템 선 처리
            │
            ▼
           AOP
            │
       업무 선 처리
            │
            ▼
      Service (Biz)
            │
       ┌────┴──────────┐
       │               │
       ▼               ▼
      DTO             DAO
                       │
                       ├─ Data 변환
                       └─ Mapper Call
                              │
                              ▼
                         O-R Mapper
                              │
                         Query Mapping
                              │
                         Query Execute
                              │
                              ▼
                      External Resource

정상 처리 후
            │
            ▼
       업무 후 처리
            │
            ▼
      시스템 후 처리
            │
            ▼
         response
```

---

# 23. 온라인 구조의 핵심 책임 분리

```text
Controller
= 요청유형별 진입 경계

FWK LIB
= 시스템 공통 선·후처리

AOP
= 업무 공통 선·후처리

Service
= 업무 로직

DTO
= 데이터 전달 객체

DAO
= 데이터 변환 + Mapper 호출

O-R Mapper
= Query Mapping + Query Execute

Config
= 실행/업무/로그/배포/메시지 설정

Library
= 공통 실행기능
```

이 책임 분리는 이후 상세 개발표준을 만들 때 가장 중요한 기준이 된다.

---

# 24. 온라인 아키텍처의 금지 패턴 — ANALYSIS

원본의 책임 분리를 유지하려면 다음 패턴은 아키텍처적으로 경계해야 한다.

> 아래 항목은 원본에 `금지`라고 직접 쓰인 내용이 아니라, 원본 구조를 일관되게 적용하기 위한 ANALYSIS다.

```text
Controller에 업무로직 집중

FWK LIB에 업무별 로직 혼입

AOP에 핵심 업무처리 구현

Service가 Mapper를 무조건 직접 호출하여 DAO 책임 우회

DAO에 화면/채널 로직 혼입

O-R Mapper에 업무판단 로직 혼입

개별 프로젝트가 공통 Library를 임의 복제

동적 Config와 정적 Config의 우선순위 미정의
```

---

# 25. 장표 3 — 배치 표준 아키텍처 전체 구조

> 아래 텍스트 그림은 원본 배치 장표의 영역과 연결방향을 최대한 보존하여 재구성한 것이다.

```text
┌───────────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                      배치 표준 아키텍처 구성                                           │
└───────────────────────────────────────────────────────────────────────────────────────────────────────┘

        관리 담당자
            │
            ▼
┌──────────────────────────┐
│     작업 자동화 관리     │
├──────────────────────────┤
│ 배치작업등록             │
│ 배치작업조회             │
│ 배치모니터링             │
│ ...                      │
└─────────────┬────────────┘
              │
              │
              ▼
       ┌──────────────┐
       │  Control-M   │
       │    Agent     │
       └──────┬───────┘
              │
              ▼
       ┌──────────────┐
       │   배치 Shell │
       └──────┬───────┘
              │
              ▼
┌──────────────────────────────────────────────────────────────────────────────────────────────┐
│                                      배치 서버                                               │
│                                                                                              │
│  ┌────────────────────────────────────────────────────────────────────────────────────────┐ │
│  │                                    Spring Batch                                        │ │
│  └────────────────────────────────────────────────────────────────────────────────────────┘ │
│                                                                                              │
│  ┌────────────────────────────────────────────────────────────────────────────────────────┐ │
│  │                                    Job Execution                                       │ │
│  │                                                                                        │ │
│  │  ┌──────────────┐                                                                      │ │
│  │  │ Job Launcher │                                                                      │ │
│  │  └──────┬───────┘                                                                      │ │
│  │         │ execute()                                                                     │ │
│  │         ▼                                                                               │ │
│  │      ┌───────┐   execute()   ┌───────┐                                                  │ │
│  │      │  Job  │──────────────►│ Step  │                                                  │ │
│  │      └───┬───┘               └───┬───┘                                                  │ │
│  │          │                       │                                                      │ │
│  │          │              ┌────────┴──────────────────────────────────────────────────┐   │ │
│  │          │              │               Step Execution #1                          │   │ │
│  │          │              │                                                           │   │ │
│  │          │              │ read()    ─────────────► ItemReader                       │   │ │
│  │          │              │ process() ─────────────► ItemProcessor                    │   │ │
│  │          │              │ write()   ─────────────► ItemWriter                       │   │ │
│  │          │              │                         reads / writes                     │   │ │
│  │          │              └───────────────────────────────────────────────────────────┘   │ │
│  │          │                                                                             │ │
│  │          │              ┌───────────────────────────────────────────────────────────┐   │ │
│  │          └─────────────►│               Step Execution #2                          │   │ │
│  │                         │                                                           │   │ │
│  │                         │ Step ───────────────────────────────────────► Tasklet      │   │ │
│  │                         └───────────────────────────────────────────────────────────┘   │ │
│  └────────────────────────────────────────────────────────────────────────────────────────┘ │
└──────────────────────────────────────────────────────────────────────────────────────────────┘
              │
              ▼
┌────────────────────────────────────────────────────────┐
│                 Spring Job Repository                  │
├────────────────────────────────────────────────────────┤
│ BATCH_STEP_EXECUTION_CONTEXT                           │
│ BATCH_JOB_CONTEXT                                      │
│ BATCH_STEP_EXECUTION                                   │
│ BATCH_JOB_INSTANCE                                     │
│ BATCH_JOB_EXECUTION_PARAMS                             │
└────────────────────────────────────────────────────────┘

Job/Step의 Reader/Writer 처리
              │
              ▼
┌────────────────────────────────┐
│          원장 DataBase         │
└────────────────────────────────┘

별도 작업 스케줄러 영역
──────────────────────────────────────────────────────────────────────────────────────────────

┌──────────────────────────┐
│       작업 스케줄러      │
├──────────────────────────┤
│ 배치작업 기능            │
│ (정기, 수시)             │
│                          │
│ 작업실행정보             │
└─────────────┬────────────┘
              │
              └─────────────► Control-M Agent
```

---

# 26. 배치 관리/스케줄 영역 상세 분석

## 26.1 원본 FACT

좌측은 크게 두 영역으로 구분된다.

```text
작업 자동화 관리
├─ 배치작업등록
├─ 배치작업조회
├─ 배치모니터링
└─ ...

작업 스케줄러
├─ 배치작업 기능 (정기, 수시)
└─ 작업실행정보
```

---

## 26.2 설계 의미

배치 실행 자체와 운영관리 화면/스케줄링을 분리한다.

```text
Control Plane
   ├─ 등록
   ├─ 조회
   ├─ 모니터링
   └─ 스케줄
          │
          ▼
Execution Plane
   ├─ Control-M Agent
   ├─ Batch Shell
   └─ Spring Batch
```

> `Control Plane / Execution Plane` 용어는 ANALYSIS다.

---

# 27. Control-M Agent 상세 분석

## 27.1 원본 FACT

```text
작업 자동화 관리 / 작업 스케줄러
              │
              ▼
        Control-M Agent
              │
              ▼
          배치 Shell
```

---

## 27.2 설계 의미

원본은 **외부 스케줄/운영통제와 Spring Batch 내부 실행을 직접 결합하지 않고 Agent + Shell 경계**를 둔다.

```text
Schedule
   ↓
Control-M Agent
   ↓
Batch Shell
   ↓
Spring Batch Job
```

이 구조는 운영 스케줄과 애플리케이션 실행을 분리하는 기준으로 볼 수 있다.

---

# 28. Batch Shell 상세 분석

## 28.1 원본 FACT

`배치 Shell`은 Control-M Agent와 배치 서버 사이에 배치되어 있다.

```text
Control-M Agent
      │
      ▼
배치 Shell
      │
      ▼
Spring Batch
```

---

## 28.2 확인 필요

원본만으로는 다음을 알 수 없다.

```text
Shell 명명규칙
Job Parameter 전달방식
Exit Code 기준
Environment Profile 처리
Log Directory
Retry 방식
중복실행 방지
```

이들은 배치 개발/운영 표준에서 별도로 정의해야 한다.

---

# 29. Spring Batch 실행 구조

## 29.1 원본 FACT

```text
Spring Batch
    │
    ▼
Job Execution
    │
    ├─ Job Launcher
    │    │ execute()
    │    ▼
    │   Job
    │    │ execute()
    │    ▼
    │   Step
    │
    ├─ Step Execution #1
    │    ├─ ItemReader
    │    ├─ ItemProcessor
    │    └─ ItemWriter
    │
    └─ Step Execution #2
         └─ Tasklet
```

---

## 29.2 실행 계층

```text
JobLauncher
    ↓
Job
    ↓
Step
    ├─ Chunk-oriented Step
    │    ├─ read()
    │    ├─ process()
    │    └─ write()
    │
    └─ Tasklet-oriented Step
         └─ Tasklet
```

`Chunk-oriented`라는 용어는 Spring Batch 개념을 설명하기 위한 ANALYSIS이며, 원본에는 `Step Execution #1`과 Reader/Processor/Writer 구조가 직접 표시되어 있다.

---

# 30. Step Execution #1 — Reader / Processor / Writer

## 30.1 원본 FACT

```text
Step Execution #1
   │
   ├─ read()    → ItemReader
   ├─ process() → ItemProcessor
   └─ write()   → ItemWriter
```

그리고 `reads / writes`라는 설명이 표시된다.

---

## 30.2 데이터 처리 흐름

```text
Source
  │
  ▼
ItemReader
  │
  ▼
ItemProcessor
  │
  ▼
ItemWriter
  │
  ▼
Target / 원장 Database
```

원본에는 구체 Source 종류가 나와 있지 않으므로 파일/DB/API 등을 임의로 특정하지 않는다.

---

# 31. Step Execution #2 — Tasklet

## 31.1 원본 FACT

```text
Step Execution #2
     │
     ├─ Step
     │
     └────────────► Tasklet
```

---

## 31.2 설계 의미

[ANALYSIS]

Reader/Processor/Writer 방식 외에 단일 작업단위형 Step도 표준 구조에 포함하고 있음을 보여준다.

즉 배치 구현 패턴이 하나로 고정되어 있지 않다.

```text
Step 구현방식
   ├─ Reader / Processor / Writer
   └─ Tasklet
```

---

# 32. Spring Job Repository 상세 분석

## 32.1 원본 FACT

원본은 다음 Repository 테이블/영역을 표시한다.

```text
Spring Job Repository
├─ BATCH_STEP_EXECUTION_CONTEXT
├─ BATCH_JOB_CONTEXT
├─ BATCH_STEP_EXECUTION
├─ BATCH_JOB_INSTANCE
└─ BATCH_JOB_EXECUTION_PARAMS
```

---

## 32.2 설계 의미

배치 실행 상태와 메타정보를 애플리케이션 메모리에만 두지 않고 Repository에 관리하는 구조다.

```text
Job 실행
   │
   ├─ Job Instance
   ├─ Execution Parameter
   ├─ Step Execution
   └─ Execution Context
        │
        ▼
Spring Job Repository
```

---

# 33. 작업실행정보와 Job Repository의 구분

원본에는 좌측 작업 스케줄러 영역의 `작업실행정보`와 배치 서버 하단의 `Spring Job Repository`가 각각 존재한다.

따라서 최소한 다음 두 관리영역은 구분된다.

```text
운영/스케줄 관점
작업실행정보

Spring Batch 내부 실행 관점
Spring Job Repository
```

두 데이터가 동일 DB인지, 동기화되는지, 어떤 키로 연결되는지는 원본만으로 확인할 수 없다.

```text
[확인 필요]
작업실행정보 ↔ Spring Job Repository Trace Key
```

---

# 34. 원장 DataBase 연계

## 34.1 원본 FACT

장표 우측 하단에 다음이 표시된다.

```text
원장 DataBase
```

Step Execution #1의 Reader/Writer 흐름과 연결선이 보인다.

---

## 34.2 분석

배치 비즈니스 데이터와 Spring Job Repository의 실행 메타데이터를 구분하는 구조로 이해할 수 있다.

```text
업무 데이터
    → 원장 DataBase

배치 실행 메타데이터
    → Spring Job Repository
```

---

# 35. 배치 End-to-End 실행 흐름

```text
[관리 담당자]
      │
      ▼
작업 자동화 관리
      │
      ├─ 등록
      ├─ 조회
      └─ 모니터링
      │
      ▼
작업 스케줄러
      │
      │ 정기 / 수시
      ▼
Control-M Agent
      │
      ▼
배치 Shell
      │
      ▼
Spring Batch
      │
      ▼
Job Launcher
      │ execute()
      ▼
Job
      │ execute()
      ▼
Step
      │
      ├───────────────┐
      │               │
      ▼               ▼
Step #1            Step #2
      │               │
      ▼               ▼
Reader             Tasklet
  ↓
Processor
  ↓
Writer
      │
      ▼
원장 DataBase

동시에
Job / Step 실행상태
      │
      ▼
Spring Job Repository
```

---

# 36. 온라인과 배치 표준의 통합 비교

| 관점 | 온라인 NH Cloud Framework | 배치 Standard |
|---|---|---|
| 진입 | Client Request | 스케줄/운영 요청 |
| 외부 통제 | Controller 유형 | 작업 자동화 / Control-M |
| 실행 시작 | Controller | Batch Shell / JobLauncher |
| 공통 처리 | FWK LIB / AOP | Spring Batch Runtime |
| 핵심 업무 | Service(Biz) | Job / Step |
| 데이터 처리 | DAO / O-R Mapper | Reader / Processor / Writer / Tasklet |
| 실행 메타 | 원본에 별도 명시 없음 | Spring Job Repository |
| 외부 DB | Database | 원장 DataBase |
| 설정 | Config / 동적 Config | 상세 설정은 장표 미제시 |
| 운영지원 | Master Solution / 모니터링 Library | 작업등록/조회/모니터링 |
| 배포 | GitLabRunner/GitLab/NEXUS | 별도 배치 배포흐름은 장표 미제시 |

---

# 37. 전체 Application Framework Big Picture

```text
                                      NEXT INFORMATION SYSTEM

┌────────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                      APPLICATION STANDARD                                          │
├────────────────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                                    │
│  ONLINE                                                                                            │
│  ──────                                                                                            │
│  범용/전용/C2C                                                                                    │
│       │                                                                                            │
│       ▼                                                                                            │
│  Controller Type                                                                                   │
│       │                                                                                            │
│       ▼                                                                                            │
│  FWK LIB(System Pre/Post)                                                                          │
│       │                                                                                            │
│       ▼                                                                                            │
│  AOP(Business Pre/Post)                                                                             │
│       │                                                                                            │
│       ▼                                                                                            │
│  Service(Biz)                                                                                      │
│       │                                                                                            │
│       ├─ DTO                                                                                       │
│       └─ DAO → O-R Mapper → Database/FOS/API G/W                                                   │
│                                                                                                    │
│  Supporting                                                                                        │
│  Config / Library / Master Solution / Service Registry / CI-CD / NEXUS / STS IDE                   │
│                                                                                                    │
├────────────────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                                    │
│  BATCH                                                                                             │
│  ─────                                                                                             │
│  관리담당자                                                                                        │
│       │                                                                                            │
│       ▼                                                                                            │
│  작업자동화 / 스케줄                                                                              │
│       │                                                                                            │
│       ▼                                                                                            │
│  Control-M Agent → Batch Shell                                                                     │
│       │                                                                                            │
│       ▼                                                                                            │
│  Spring Batch                                                                                      │
│       │                                                                                            │
│       ├─ JobLauncher → Job → Step → Reader/Processor/Writer → 원장 DB                              │
│       │                                                                                            │
│       ├─ Job → Step → Tasklet                                                                      │
│       │                                                                                            │
│       └─ Spring Job Repository                                                                     │
│                                                                                                    │
└────────────────────────────────────────────────────────────────────────────────────────────────────┘
```

---

# 38. Architecture Responsibility Matrix

| 영역 | 주요 책임 | 원본 근거 |
|---|---|---|
| Client | 요청 발생/응답 수신 | 장표 1 |
| Controller | 요청 유형별 진입 | 장표 1, 2 |
| FWK LIB | 시스템 선·후처리 | 장표 1 |
| AOP | 업무 선·후처리 | 장표 1 |
| Service | 업무 처리 | 장표 1 |
| DTO | 데이터 객체 | 장표 1 |
| DAO | Data 변환, Mapper Call | 장표 1 |
| O-R Mapper | Query Mapping/Execute | 장표 1 |
| Config | 실행/업무/로그/배포/메시지 설정 | 장표 1 |
| Dynamic Config Handler | 동적 Config 반영 | 장표 1 |
| Master Solution | 동적 Config 관리 | 장표 1 |
| Library | 공통 기능 제공 | 장표 1 |
| STS IDE/Plug-In | 개발 지원 | 장표 1 |
| CI/CD/GitLab/NEXUS | 배포/형상·Artifact 연계 | 장표 1 |
| 작업 자동화 관리 | 배치등록/조회/모니터링 | 장표 3 |
| 작업 스케줄러 | 정기/수시 실행 | 장표 3 |
| Control-M Agent | 스케줄 실행 전달 | 장표 3 |
| Batch Shell | Batch 실행 경계 | 장표 3 |
| JobLauncher | Job 실행 시작 | 장표 3 |
| Job | 배치 작업 단위 | 장표 3 |
| Step | 배치 실행 단계 | 장표 3 |
| ItemReader | 읽기 | 장표 3 |
| ItemProcessor | 처리 | 장표 3 |
| ItemWriter | 쓰기 | 장표 3 |
| Tasklet | 단일 Step 처리 | 장표 3 |
| Job Repository | Batch 실행 메타 관리 | 장표 3 |

---

# 39. Architecture Traceability 모델

이 자료를 개발/운영 표준으로 연결하려면 다음 Traceability가 필요하다.

```text
요청 유형
   ↓
Controller Type
   ↓
System Pre/Post
   ↓
Business Pre/Post
   ↓
Service
   ↓
DTO / DAO
   ↓
Mapper / External Resource
   ↓
Config / Library
   ↓
Build / Deploy
   ↓
Runtime / Monitoring
```

배치는 다음과 같다.

```text
Batch Job ID
   ↓
Scheduler Definition
   ↓
Control-M Job
   ↓
Shell
   ↓
Spring Batch Job
   ↓
Step
   ↓
Reader/Processor/Writer 또는 Tasklet
   ↓
Job Repository
   ↓
Business Data
```

---

# 40. 아키텍처 표준 관점에서 파생되는 핵심 규칙

> 이 절은 원본 구조를 표준규칙으로 전환한 ANALYSIS다.

## 40.1 Inbound 분리 규칙

```text
R-FWK-001
요청유형에 적합한 표준 Controller를 사용한다.
```

## 40.2 시스템/업무 선후처리 분리

```text
R-FWK-002
시스템 공통 처리는 FWK LIB,
업무 공통 처리는 AOP 책임으로 분리한다.
```

## 40.3 업무로직 위치

```text
R-FWK-003
핵심 업무로직은 Service(Biz) 계층에 위치한다.
```

## 40.4 데이터 접근 규칙

```text
R-FWK-004
데이터 접근은 DAO → O-R Mapper 구조를 기본 경계로 관리한다.
```

## 40.5 설정 외부화

```text
R-FWK-005
공통/업무/로그/메시지/배포 설정은 Config 영역으로 관리한다.
```

## 40.6 공통 Library 규칙

```text
R-FWK-006
공통 기능은 Library를 통해 재사용한다.
```

## 40.7 동적 Config 관리

```text
R-FWK-007
동적 설정은 Master Solution과 동적 Config Handler의 관리경계를 통해 반영한다.
```

## 40.8 Batch 실행경계

```text
R-BATCH-001
배치 스케줄 실행은 Control-M Agent → Batch Shell → Spring Batch 흐름으로 관리한다.
```

## 40.9 Batch Step 구현

```text
R-BATCH-002
Step은 Reader/Processor/Writer 또는 Tasklet 방식으로 구성할 수 있다.
```

## 40.10 Batch Metadata

```text
R-BATCH-003
Job/Step 실행상태는 Spring Job Repository와 연결해 관리한다.
```

---

# 41. 시스템 선후처리와 업무 선후처리의 표준 경계

```text
┌────────────────────────────────────────────┐
│ SYSTEM COMMON                             │
│ FWK LIB                                   │
│                                            │
│ 시스템 선 처리                            │
│ 시스템 후 처리                            │
└───────────────────┬────────────────────────┘
                    │
                    ▼
┌────────────────────────────────────────────┐
│ BUSINESS COMMON                           │
│ AOP                                       │
│                                            │
│ 업무 선 처리                              │
│ 업무 후 처리                              │
└───────────────────┬────────────────────────┘
                    │
                    ▼
┌────────────────────────────────────────────┐
│ BUSINESS                                  │
│ Service(Biz)                              │
└────────────────────────────────────────────┘
```

이 3단 분리는 향후 Framework 상세설계에서 반드시 유지해야 할 핵심 기준이다.

---

# 42. Config와 Library의 표준 경계

```text
Config
= 바뀔 수 있는 실행/업무/배포 정의

Library
= 코드로 제공되는 공통 실행기능
```

원본을 기반으로 구분하면:

```text
Config
├─ application.yml
├─ manifest.yml
├─ 업무config.yml
├─ log4j2.xml
├─ 메시지yml
├─ gradle build
└─ 배포.sh

Library
├─ NH 공통
├─ MIDAS
├─ Spring Boot
├─ File Handle
├─ Log4j2
├─ Utility
├─ SSO
├─ 모니터링
├─ 암호화
└─ coverage
```

[ANALYSIS]

설정값과 공통코드를 명확히 분리하지 않으면 변경관리와 배포관리의 책임이 뒤섞일 수 있다.

---

# 43. 동적 Config 운영통제 관점

동적 Config는 편리하지만 운영리스크가 크다.

원본에 중앙 `Master Solution`이 있기 때문에 다음 통제항목을 상세설계에서 반드시 보완해야 한다.

| 통제 항목 | 원본 상태 | 후속 필요 |
|---|---|---|
| 변경권한 | 미표시 | 역할/권한 정의 |
| 승인 | 미표시 | 승인 Workflow |
| Version | 미표시 | Config Versioning |
| Rollback | 미표시 | 이전값 복구 |
| Audit | 미표시 | 변경자/시간/변경값 |
| 암호화 | Library에 암호화 존재 | Secret 별도 기준 필요 |
| Refresh | Handler 존재 | 적용방식/적용시점 정의 |
| 장애시 동작 | 미표시 | Master 장애 시 정책 |

---

# 44. Security 관점 분석

> 본 절은 원본에서 직접 확인되는 `SSO`, `암호화`, `API G/W`, `Service Registry` 등을 기반으로 한 ANALYSIS다.

원본에서 보안 관련으로 직접 확인되는 요소:

```text
NhSsoController
SSO Library
암호화 Library
API G/W (Cruz APIM)
```

따라서 보안 책임은 최소 다음 경계와 연결된다.

```text
인증 진입
NhSsoController

공통 인증 Library
SSO

데이터 보호
암호화

외부 API 경계
API G/W
```

하지만 다음은 원본에 없다.

```text
JWT 여부
Session 정책
Key 관리
TLS 정책
API 인증 방식
권한/인가 정책
개인정보 마스킹
```

따라서 본 장표만으로 보안아키텍처를 확정할 수 없다.

---

# 45. Availability 관점 분석

원본 장표는 Framework 논리구조를 설명하며 다음 가용성 항목을 직접 제시하지 않는다.

```text
Active-Active
Cluster
Failover
Retry
Circuit Breaker
DR
```

다만 다음 외부 구성요소가 장애경계가 될 수 있다.

```text
Database
FOS
API G/W
Service Registry
Master Solution
GitLab/NEXUS
Control-M
Job Repository
```

따라서 상세 Physical/Runtime Architecture에서는 각 컴포넌트별 장애영향도를 별도 정의해야 한다.

---

# 46. Observability 관점 분석

원본에서 직접 확인되는 관측/운영 요소:

```text
Library
└─ 모니터링

Config
└─ log4j2.xml

Library
└─ Log4j2

배치
└─ 배치모니터링
```

이를 연결하면 다음 운영관측 모델이 필요하다.

```text
온라인
Log4j2 + 모니터링

배치
배치모니터링 + Job Repository
```

그러나 Log Format, Correlation ID, Metric, Alert Threshold, APM 제품 등은 장표에서 확인되지 않는다.

---

# 47. Performance 관점 분석

원본은 성능수치나 Thread/Pool 값을 제시하지 않는다.

따라서 다음을 이 장표의 표준값으로 해석하면 안 된다.

```text
TPS
Thread
Connection Pool
Timeout
Heap
Batch Chunk Size
Commit Interval
Parallel Step
```

다만 구조상 성능 검증 포인트는 식별할 수 있다.

```text
Controller
FWK LIB
AOP
Service
DAO
O-R Mapper
Database
API G/W
FOS

Batch
Control-M
Shell
Job
Step
Reader
Processor
Writer
Repository
DB
```

---

# 48. Transaction 관점 분석

온라인 장표에는 `Transaction`이라는 명시적 컴포넌트가 없다.

배치 장표도 Commit/Retry/Skip 정책을 직접 제시하지 않는다.

따라서 다음은 모두 별도 설계가 필요하다.

```text
온라인 Transaction Boundary
Transaction Timeout
ReadOnly Transaction
Rollback Rule

배치 Chunk Commit
Retry / Skip
Restartability
Idempotency
```

이 점은 중요한 GAP다.

---

# 49. Error Handling 관점 분석

원본에는 Exception Handler, Error Code, Error Response 구조가 직접 나타나지 않는다.

따라서 Framework 표준을 완성하려면 최소 다음을 추가 정의해야 한다.

```text
Controller Error Boundary
System Pre/Post Error
AOP Error
Service Business Error
DAO/Mapper Error
External API Error
File Error
Batch Job Error
Step Error
Shell Exit Code
Control-M 상태 매핑
```

---

# 50. Logging 관점 분석

원본에서 직접 확인되는 것은:

```text
log4j2.xml
Log4j2
모니터링
```

따라서 최소한 Framework에서 로깅을 공통기능으로 제공하려는 방향은 확인할 수 있다.

하지만 다음은 확인되지 않는다.

```text
로그 레벨 기준
거래로그
전문로그
감사로그
민감정보 마스킹
MDC
GUID/TraceId
로그 보관기간
```

---

# 51. 개발표준 산출물로 변환해야 할 항목

이 장표를 실제 개발표준으로 사용할 경우 다음 문서로 분해하는 것이 적절하다.

```text
01. Framework Overview
02. Controller Standard
03. System Pre/Post Standard
04. Business AOP Standard
05. Service Standard
06. DTO Standard
07. DAO Standard
08. O-R Mapper Standard
09. Configuration Standard
10. Dynamic Configuration Standard
11. Common Library Standard
12. External Integration Standard
13. SSO Standard
14. File Standard
15. Report/RD Standard
16. C2C Standard Message Standard
17. CI/CD Standard
18. Developer Tool / Plug-In Guide
19. Batch Development Standard
20. Batch Operation Standard
```

---

# 52. 배치 상세 표준으로 확장해야 할 항목

장표 3을 실제 Batch Guide로 만들려면 다음 내용을 추가해야 한다.

```text
Job Naming
Step Naming
Shell Naming
Control-M Job Naming
Job Parameter
Job Instance Key
Execution Context
Restart
Retry
Skip
Duplicate Execution Prevention
Chunk Size
Commit Interval
Reader Type
Writer Type
Tasklet 기준
Exit Code
Monitoring
SLA
Failure Notification
Job Repository Retention
Purge
```

이 항목들은 원본에 직접 없으므로 **후속 상세 정의 대상**이다.

---

# 53. Controller 상세 표준으로 확장해야 할 항목

장표 2는 Controller의 목적만 정의한다.

실제 개발표준에는 다음이 추가되어야 한다.

| 항목 | 필요 여부 |
|---|---|
| URL Mapping | 필요 |
| HTTP Method | 필요 |
| Request/Response Type | 필요 |
| Header | 필요 |
| Validation | 필요 |
| Error Handling | 필요 |
| Authentication | 필요 |
| Authorization | 필요 |
| Logging | 필요 |
| Timeout | 필요 |
| Example Code | 필요 |
| Unit Test | 필요 |

---

# 54. Framework Layer 의존성 방향

[ANALYSIS]

원본 구조를 의존성 방향으로 단순화하면 다음과 같다.

```text
Client
  ↓
Controller
  ↓
FWK LIB
  ↓
AOP
  ↓
Service
  ↓
DAO
  ↓
O-R Mapper
  ↓
External Data Resource
```

DTO와 Config/Library는 횡단 지원요소로 본다.

```text
            DTO
             │
Controller → Service → DAO

Config / Library
      └──── 전체 Runtime 지원
```

---

# 55. Framework와 개발도구의 추적성

장표 하단의 STS IDE 영역은 Runtime Framework의 주요 구성요소를 그대로 개발툴에 노출한다.

```text
Runtime Component        Development Tool
─────────────────────────────────────────
Controller          ↔    Controller
AOP                 ↔    AOP
Service             ↔    Service
DAO                 ↔    DAO
DTO                 ↔    DTO
O-R Mapper          ↔    Mapper / O-R
Config              ↔    Config
Library             ↔    Library
```

이 구조는 개발자가 아키텍처를 문서로만 보는 것이 아니라 IDE에서 동일한 구조로 개발하도록 유도하는 체계다.

---

# 56. Developer Experience 관점 분석

원본 Plug-In:

```text
DTO Plug-In
GIT Plug-In
BOOT Dashboard
```

[ANALYSIS]

이는 반복작업을 도구화하려는 방향을 보여준다.

```text
Architecture Rule
     ↓
IDE / Plug-In
     ↓
개발자 반복작업 자동화
     ↓
표준편차 감소
```

다만 코드생성 범위, Template 버전, 표준위반 검출기능 등은 확인되지 않는다.

---

# 57. Framework Governance 관점 분석

이 자료를 운영 가능한 표준으로 만들기 위해 필요한 Governance 구조는 다음과 같다.

```text
표준 정의
   ↓
Framework / Library 배포
   ↓
IDE / Plug-In 제공
   ↓
Project 적용
   ↓
GitLab Build
   ↓
NEXUS Artifact
   ↓
CI/CD Deploy
   ↓
Runtime Monitoring
   ↓
표준 개선
```

원본에서 `Framework → 개발툴 → CI/CD` 연결은 확인되지만, 승인 Gate나 Version 정책은 별도 정의가 필요하다.

---

# 58. Version / Release 관리 GAP

원본에는 다음 요소가 존재한다.

```text
NH Cloud Framework
Library
NEXUS
GitLab
GitLabRunner
```

그러나 다음 정책은 보이지 않는다.

```text
Framework Version
Library Version
Compatibility Matrix
Deprecated Version
Upgrade Policy
Release Note
Rollback Version
```

따라서 Framework를 장기간 운영하려면 별도 **Framework Release Governance**가 필요하다.

---

# 59. Service Registry GAP

원본에는 `Service Registry`가 표시되어 있지만 다음은 확인되지 않는다.

```text
등록 주체
등록 항목
조회 방식
Health Check
Service ID
Endpoint Version
Dynamic Discovery 여부
```

따라서 현재 장표에서 확정 가능한 것은:

```text
[FACT]
Service Registry가 Framework 주변 외부 구성요소로 존재
```

뿐이다.

---

# 60. API G/W GAP

원본:

```text
API G/W
(Cruz APIM)
```

직접 확인되는 것은 Gateway 제품/역할 이름뿐이다.

추가 확인 필요:

```text
North-South API만 처리하는가?
C2C도 Gateway를 경유하는가?
Authentication/Authorization 수행 위치는?
Rate Limit은?
Timeout은?
API Versioning은?
```

---

# 61. FOS / File Architecture GAP

장표 1에는 `FOS`, 장표 2에는 `ObjectStorage`가 나타난다.

```text
장표 1
FOS

장표 2
PaaS 환경의 DB, ObjectStorage 간 파일 송수신
```

두 대상의 관계가 문서상 명확하지 않다.

```text
[확인 필요]
FOS의 정확한 명칭/역할
FOS ↔ ObjectStorage 동일성
File Metadata 저장위치
대용량 File 처리방식
Streaming 여부
```

---

# 62. Database Access GAP

원본은 DAO와 O-R Mapper를 정의하지만 다음을 특정하지 않는다.

```text
MyBatis/JPA 등 구체 기술
Connection Pool
SQL Timeout
Transaction Manager
Read/Write 분리
DB 종류
Paging 표준
```

따라서 Data Access 상세 설계가 별도로 필요하다.

---

# 63. 배치 Job Repository GAP

원본 테이블명은 다음 5개가 보인다.

```text
BATCH_STEP_EXECUTION_CONTEXT
BATCH_JOB_CONTEXT
BATCH_STEP_EXECUTION
BATCH_JOB_INSTANCE
BATCH_JOB_EXECUTION_PARAMS
```

장표만으로는 다음을 판단하기 어렵다.

```text
전체 Spring Batch Metadata Table Set인지 일부만 표현한 것인지
Schema Version
DB 종류
Retention
Purge
Index
HA
```

따라서 실제 구축시 DB DDL과 Framework 버전을 확인해야 한다.

---

# 64. Naming Architecture로 연결할 항목

이 자료는 다음 Naming 대상의 존재를 명확히 한다.

```text
Controller
Service
DTO
DAO
Mapper
Config
Library
Batch Job
Step
Shell
```

후속 Naming Standard에서는 최소 다음 매핑이 필요하다.

```text
업무코드
  ↓
Program
  ↓
Controller
  ↓
Service
  ↓
DTO
  ↓
DAO / Mapper

Batch
  ↓
Job ID
  ↓
Shell
  ↓
Spring Job
  ↓
Step
```

---

# 65. Runtime Architecture로 연결할 항목

온라인 Runtime 검증 시 최소 다음 흐름을 관측해야 한다.

```text
Request
 ↓
Controller
 ↓
System Pre
 ↓
Business Pre
 ↓
Service
 ↓
DAO
 ↓
Mapper
 ↓
DB/API/File
 ↓
Business Post
 ↓
System Post
 ↓
Response
```

배치는:

```text
Schedule
 ↓
Control-M Agent
 ↓
Shell
 ↓
JobLauncher
 ↓
Job
 ↓
Step
 ↓
Reader/Processor/Writer or Tasklet
 ↓
Repository / DB
```

---

# 66. Runtime Evidence로 확인해야 할 항목

> ANALYSIS

| 영역 | Runtime Evidence |
|---|---|
| Controller | 요청유형, 응답코드, 처리시간 |
| FWK LIB | 시스템 선후처리 시간/오류 |
| AOP | 업무 선후처리 적용여부 |
| Service | 업무 처리시간 |
| DAO | DB 호출수/시간 |
| Mapper | SQL 실행시간/오류 |
| API G/W | 외부 호출시간/상태 |
| File | 송수신 성공/실패/크기 |
| Dynamic Config | 적용 버전/시각 |
| Batch Job | 시작/종료/상태 |
| Batch Step | 처리건수/상태 |
| Job Repository | Instance/Execution 상태 |

---

# 67. 자동검증 가능한 Architecture Rule 후보

> 아래는 원본을 Architecture-as-Test로 확장하기 위한 후보 규칙이다.

```text
RULE-CONTROLLER-001
표준 Controller 유형 외 직접 임의 Controller 생성 여부 검사

RULE-LAYER-001
Controller가 O-R Mapper를 직접 참조하지 않는지 검사

RULE-LAYER-002
Service가 UI Framework 클래스에 직접 의존하지 않는지 검사

RULE-CONFIG-001
필수 Config 파일 존재 여부 검사

RULE-LOG-001
log4j2.xml 존재 여부 검사

RULE-BATCH-001
배치 Job이 Job Repository를 사용하는지 검사

RULE-BATCH-002
Control-M Job과 Batch Shell, Spring Job의 Traceability 검사
```

이 규칙은 원본에 직접 정의된 것이 아니므로 `CANDIDATE`로 관리해야 한다.

---

# 68. Architecture Risk / GAP 종합

| ID | 영역 | GAP / 위험 | 근거 | 우선순위 |
|---|---|---|---|---|
| GAP-FWK-001 | Controller | URL/Method/전문 규칙 없음 | 장표 2는 용도만 정의 | 높음 |
| GAP-FWK-002 | FWK LIB | 시스템 선후처리 세부기능 미정 | 장표 1 | 높음 |
| GAP-FWK-003 | AOP | 업무 선후처리 대상/순서 미정 | 장표 1 | 높음 |
| GAP-FWK-004 | Transaction | Transaction 경계 미표시 | 장표 1 | 높음 |
| GAP-FWK-005 | Error | 공통 오류처리 미표시 | 전체 | 높음 |
| GAP-FWK-006 | Security | 인증/인가 세부규칙 미표시 | SSO/암호화만 존재 | 높음 |
| GAP-FWK-007 | Dynamic Config | 승인/버전/롤백 미표시 | Master Solution | 높음 |
| GAP-FWK-008 | Service Registry | 등록/조회/Health 규칙 미표시 | 장표 1 | 중간 |
| GAP-FWK-009 | API Gateway | 경유범위/정책 미표시 | 장표 1 | 중간 |
| GAP-FWK-010 | File | FOS/ObjectStorage 관계 미확정 | 장표 1/2 | 중간 |
| GAP-FWK-011 | CI/CD | 파이프라인/승인/롤백 미표시 | 장표 1 | 중간 |
| GAP-BAT-001 | Shell | 표준 Parameter/Exit Code 없음 | 장표 3 | 높음 |
| GAP-BAT-002 | Restart | Restart/Retry/Skip 미표시 | 장표 3 | 높음 |
| GAP-BAT-003 | Job Repo | 전체 Metadata/Retention 미표시 | 장표 3 | 중간 |
| GAP-BAT-004 | Traceability | Control-M ↔ Spring Job 키 미표시 | 장표 3 | 높음 |
| GAP-BAT-005 | Monitoring | SLA/Alert 기준 미표시 | 장표 3 | 중간 |

---

# 69. 후속 상세 설계에서 반드시 확인할 항목

## Online Framework

```text
Controller Mapping
Standard Message
Request/Response DTO
Validation
Exception
Error Code
Logging
Transaction
Timeout
Session
Authentication
Authorization
DAO/Mapper
Paging
File
Report
C2C
Inbound
SSO
```

## Config / Library

```text
Configuration Precedence
Dynamic Refresh
Secret
Library Version
Dependency Management
Compatibility
Deprecation
```

## CI/CD

```text
Branch Strategy
Build
Unit Test
Static Analysis
Artifact Publish
Deploy
Approval
Rollback
Environment Promotion
```

## Batch

```text
Scheduler
Control-M Definition
Shell
Job Parameter
Job/Step Naming
Restart
Retry
Skip
Commit
Chunk
Tasklet
Job Repository
Monitoring
SLA
Failure Handling
```

---

# 70. 검증 체크리스트 — 온라인

| 검증 항목 | 확인 |
|---|:---:|
| 요청 유형별 표준 Controller가 정의되어 있는가 | □ |
| 화면 Controller와 C2C Controller가 구분되는가 | □ |
| File 요청은 NhFileController 경계를 사용하는가 | □ |
| RD 연계는 NhRDController 경계를 사용하는가 | □ |
| EAI/외부 JSON은 NhInboundController 경계를 사용하는가 | □ |
| SSO는 NhSsoController 경계를 사용하는가 | □ |
| 시스템 선후처리와 업무 선후처리가 분리되어 있는가 | □ |
| Service(Biz) 책임이 명확한가 | □ |
| DAO와 O-R Mapper 책임이 명확한가 | □ |
| DTO 사용규칙이 정의되어 있는가 | □ |
| Config 파일별 책임이 정의되어 있는가 | □ |
| 동적 Config 승인/버전/롤백이 정의되어 있는가 | □ |
| 공통 Library의 버전이 관리되는가 | □ |
| Database/FOS/API G/W/Registry 연결규칙이 정의되어 있는가 | □ |
| CI/CD와 NEXUS 배포흐름이 정의되어 있는가 | □ |

---

# 71. 검증 체크리스트 — 배치

| 검증 항목 | 확인 |
|---|:---:|
| 작업등록/조회/모니터링 관리기능이 있는가 | □ |
| 정기/수시 스케줄 기준이 정의되어 있는가 | □ |
| Control-M Agent 경유방식이 표준화되어 있는가 | □ |
| Batch Shell Naming/Parameter가 표준화되어 있는가 | □ |
| JobLauncher/Job/Step 관계가 정의되어 있는가 | □ |
| Reader/Processor/Writer 사용기준이 있는가 | □ |
| Tasklet 사용기준이 있는가 | □ |
| Job Repository Schema가 확정되어 있는가 | □ |
| Job Restart 정책이 있는가 | □ |
| Retry/Skip 정책이 있는가 | □ |
| 중복실행 방지 정책이 있는가 | □ |
| Shell Exit Code와 Control-M 상태가 매핑되는가 | □ |
| 원장 DB 처리와 Repository가 분리되어 있는가 | □ |
| Batch SLA/Alert가 정의되어 있는가 | □ |

---

# 72. 테스트 관점

## 72.1 Controller Test

```text
UI → NhinsController
File → NhFileController
RD → NhRDController
Inbound → NhInboundController
SSO → NhSsoController
C2C → NhController
```

각 유형별 정상/오류 테스트가 필요하다.

---

## 72.2 Layer Test

```text
Controller
  ↓
FWK LIB
  ↓
AOP
  ↓
Service
  ↓
DAO
  ↓
Mapper
```

계층을 우회하는 호출이 없는지 검사한다.

---

## 72.3 Dynamic Config Test

```text
변경 등록
  ↓
Master Solution
  ↓
Dynamic Config Handler
  ↓
Runtime 반영
  ↓
검증
  ↓
Rollback
```

---

## 72.4 Batch Test

```text
Schedule Trigger
  ↓
Control-M
  ↓
Shell
  ↓
JobLauncher
  ↓
Job
  ↓
Step
  ↓
Repository
  ↓
Business DB
```

정상/실패/재시작을 구분해 검증해야 한다.

---

# 73. FACT / ANALYSIS / 확인 필요 구분

## 73.1 FACT

원본 장표에서 직접 확인되는 내용:

```text
NH Cloud Framework 구성요소
6개 Controller 유형
시스템 선/후처리
업무 선/후처리
Service(Biz)
DTO getter/setter
DAO Data 변환 / Mapper Call
O-R Mapper Query Mapping / Query Execute
Config 파일명
동적 Config Handler
Master Solution
Library 항목
Database / FOS / API G/W / Service Registry
STS IDE / Plug-In
GitLabRunner / GitLab / NEXUS
Control-M Agent
Batch Shell
Spring Batch
JobLauncher / Job / Step
ItemReader / ItemProcessor / ItemWriter
Tasklet
Spring Job Repository
원장 DataBase
```

---

## 73.2 ANALYSIS

본 문서에서 구조를 이해하기 위해 분석한 내용:

```text
Controller = Inbound Boundary
FWK LIB = System Common Boundary
AOP = Business Common Boundary
Service = Business Logic
DAO/O-R Mapper = Data Access Boundary
Config vs Library 분리
Control Plane vs Execution Plane
Architecture Rule 후보
Runtime Evidence 후보
금지패턴 후보
```

---

## 73.3 확인 필요

```text
해칭 Library 항목의 실제 명칭
FOS의 정확한 역할
FOS와 ObjectStorage 관계
Service Registry 세부동작
API G/W 적용범위
Transaction Boundary
Error Handling
Timeout
Security 세부정책
Dynamic Config 변경통제
CI/CD Pipeline
Framework Version 정책
Batch Restart/Retry/Skip
Batch Job Repository 전체 Schema
Control-M ↔ Spring Job Trace Key
```

---

# 74. 장표 해석 시 유의사항

## 74.1 논리 구성도와 구현 소스는 동일하지 않을 수 있다

이 장표는 표준 아키텍처 구성도다.

따라서 실제 소스에서 클래스명/패키지/호출순서가 동일한지 별도 검증해야 한다.

```text
Architecture Diagram
      ≠
Source Evidence
```

---

## 74.2 Framework 명칭만으로 세부 기능을 추정하면 안 된다

예:

```text
FWK LIB
시스템 선 처리
```

라고 되어 있다고 해서 JWT, GUID, MDC, Logging이 반드시 포함된다고 단정할 수 없다.

---

## 74.3 연결선의 정확한 프로토콜은 별도 확인 필요

Database, FOS, API G/W, Service Registry는 외부 연계대상으로 보이지만 JDBC/HTTP/REST 등 프로토콜이 명시되어 있지 않다.

---

## 74.4 배치 장표는 운영/실행 구조 중심이다

Batch의 Transaction, Retry, Skip, Partition, Parallelism 등은 이 장표의 범위를 넘어선다.

---

# 75. Architecture Baseline 관점

이 자료는 다음 Baseline의 출발점으로 사용할 수 있다.

```text
Application Framework Baseline
│
├─ Online Entry Baseline
│   └─ 6 Controller Type
│
├─ Common Processing Baseline
│   ├─ System Pre/Post
│   └─ Business Pre/Post
│
├─ Business Layer Baseline
│   └─ Service(Biz)
│
├─ Data Access Baseline
│   ├─ DTO
│   ├─ DAO
│   └─ O-R Mapper
│
├─ Configuration Baseline
│   ├─ Static Config
│   └─ Dynamic Config
│
├─ Library Baseline
│
├─ Development Tool Baseline
│
├─ Delivery Baseline
│   ├─ GitLabRunner
│   ├─ GitLab
│   └─ NEXUS
│
└─ Batch Baseline
    ├─ Control-M
    ├─ Shell
    ├─ Spring Batch
    └─ Job Repository
```

---

# 76. 문서 계층 모델

이 장표는 전체 개발표준 문서계층에서 다음 위치가 적절하다.

```text
Application Architecture
        │
        ▼
Standard Framework Architecture       ← 현재 자료
        │
        ├─ Online Framework Standard
        │    ├─ Controller Guide
        │    ├─ Pre/Post Guide
        │    ├─ Service Guide
        │    ├─ DTO Guide
        │    ├─ DAO/Mapper Guide
        │    ├─ Config Guide
        │    └─ Library Guide
        │
        ├─ Integration Guide
        │    ├─ File
        │    ├─ RD
        │    ├─ Inbound
        │    ├─ SSO
        │    ├─ C2C
        │    └─ API Gateway
        │
        ├─ DevOps Guide
        │    ├─ IDE
        │    ├─ Plug-In
        │    ├─ GitLab
        │    └─ NEXUS
        │
        └─ Batch Guide
             ├─ Control-M
             ├─ Shell
             ├─ Job
             ├─ Step
             └─ Repository
```

---

# 77. 원본 3개 장표의 관계

```text
장표 1
Framework 전체 Big Picture
      │
      ├───────────────┐
      │               │
      ▼               ▼
장표 2              장표 3
Controller 상세      Batch 상세
```

장표 1이 온라인 Framework의 전체 공간배치라면,
장표 2는 그중 Controller 분류를 상세화하고,
장표 3은 별도의 Batch Runtime을 상세화한다.

---

# 78. 아키텍처 핵심 메시지 10개

```text
1. 요청 유형에 따라 Controller를 분리한다.
2. 시스템 선후처리와 업무 선후처리를 분리한다.
3. 업무 로직은 Service(Biz)로 모은다.
4. 데이터 접근은 DAO와 O-R Mapper로 분리한다.
5. Config는 정적/동적 관리체계를 가진다.
6. 공통 기능은 Library로 제공한다.
7. 개발툴에서 Framework 구조를 직접 지원한다.
8. GitLab/NEXUS와 배포 연계를 갖는다.
9. Batch는 Control-M → Shell → Spring Batch로 실행한다.
10. Batch 실행 메타데이터는 Job Repository로 관리한다.
```

이 10개가 현재 원본 자료에서 가장 강하게 읽히는 표준 아키텍처 메시지다.

---

# 79. 최종 평가

이 자료는 NEXT 정보계 차세대 애플리케이션의 **표준 Framework Runtime과 Batch Runtime의 상위 구조를 정의하는 핵심 장표**다.

특히 다음 점에서 중요하다.

```text
Channel/요청 유형 분리
        ↓
표준 Controller
        ↓
System Common / Business Common 분리
        ↓
Service 중심 업무처리
        ↓
DAO / O-R Mapper 데이터접근
        ↓
Config / Library 표준화
        ↓
동적 Config 중앙관리
        ↓
IDE / Plug-In 개발지원
        ↓
GitLab / NEXUS 배포연계
```

그리고 Batch는 별도의 표준 실행축으로:

```text
운영관리 / 스케줄
        ↓
Control-M Agent
        ↓
Batch Shell
        ↓
Spring Batch
        ↓
Job / Step
        ↓
Reader-Processor-Writer 또는 Tasklet
        ↓
Job Repository / 원장 Database
```

를 제시한다.

따라서 이 자료를 한 문장으로 정의하면 다음과 같다.

> **“NEXT 정보계 차세대 애플리케이션의 온라인 요청 진입부터 공통 선후처리, 업무서비스, 데이터접근, 설정·라이브러리, 개발·배포, 배치 실행까지를 하나의 표준 실행모델로 고정하는 Application Framework Architecture Baseline”**

이다.

다만 현재 장표는 **구성요소와 책임의 상위 그림**이며, 실제 운영 가능한 표준으로 확정하려면 다음이 반드시 후속 정의되어야 한다.

```text
Transaction
Timeout
Validation
Error / Exception
Logging / Trace
Security / Authorization
Service Registry Policy
API Gateway Policy
File/FOS Policy
Dynamic Config Governance
CI/CD Release Governance
Batch Retry / Restart / Skip
Batch Traceability
Runtime Evidence
```

따라서 최종 판정은 다음과 같다.

```text
Architecture Direction        : 명확
Layer Responsibility          : 상당히 명확
Controller Classification     : 명확
Config / Library Structure    : 명확
Developer Tool Integration    : 확인됨
CI/CD Integration             : 상위 수준 확인
Batch Runtime                 : 명확
Operational Detail            : 추가 정의 필요
Security Detail               : 추가 정의 필요
Transaction/Error/Timeout     : 추가 정의 필요
Runtime Evidence              : 추가 정의 필요
```

이 장표는 **표준프레임워크 상세 정의의 출발점으로는 충분히 가치가 높고**, 이후 개발표준·런타임·운영표준과 연결해 **Architecture as Document → Model → Code → Test → Runtime Evidence**로 확장해야 완전한 기준선이 된다.
