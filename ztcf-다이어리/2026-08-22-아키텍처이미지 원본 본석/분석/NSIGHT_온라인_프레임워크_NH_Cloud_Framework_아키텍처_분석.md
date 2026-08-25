# NH Cloud Framework 온라인 프레임워크 아키텍처 분석

> 분석 기준: 사용자가 제공한 「온라인 프레임워크」 구성도 이미지  
> 원본 표기: `9.2 온라인 프레임워크`  
> 분석 원칙: **이미지에서 직접 확인되는 내용(FACT)** 과 **아키텍처 해석/개선 제안(INFERENCE/PROPOSAL)** 을 구분한다.

---

## 1. 문서 목적

본 문서는 제공된 「온라인 프레임워크」 구성도를 기준으로 NH Cloud Framework의 온라인 애플리케이션 실행구조를 분석하고, 다음 내용을 재사용 가능한 아키텍처 문서 형태로 정리한다.

- 온라인 요청/응답 처리 구조
- Controller, Framework Library, AOP, Service, DTO, DAO, O-R Mapper의 역할
- 시스템 공통 선·후처리와 업무 선·후처리 구조
- Config 및 동적 Config 관리구조
- 공통 Library 구성
- Database / FOS / API Gateway 연계 구조
- Master Solution 기반 운영관리 구조
- STS IDE 기반 개발환경
- Jenkins / GitLab / Nexus 기반 CI/CD 구조
- 구조적 장점
- 설계상 확인이 필요한 GAP
- 향후 NSIGHT 아키텍처 관점에서의 적용/검증 포인트

---

# 2. 원본 구성도에서 확인되는 전체 구조

이미지의 최상위 구조는 다음과 같이 해석할 수 있다.

```text
┌──────────────────────┐
│        CLIENT        │
│      전용 단말       │
└──────────┬───────────┘
           │ request
           ▼
┌────────────────────────────────────────────────────────────────────────────┐
│                         NH Cloud Framework                                 │
│                                                                            │
│  ┌────────────┐   ┌────────────┐   ┌────────────┐   ┌────────────┐        │
│  │ Controller │ → │  FWK LIB   │ → │    AOP     │ → │  SERVICE   │        │
│  │            │   │ 시스템선처리│   │ 업무선처리 │   │ Service(Biz)│        │
│  │ Nhins      │   │ 시스템후처리│   │ 업무후처리 │   └──────┬─────┘        │
│  │ NhFile     │   └────────────┘   └────────────┘          │              │
│  │ NhRD       │                                             ▼              │
│  │ NhInbound  │                                      ┌────────────┐        │
│  │ NhSSO      │                                      │    DTO     │        │
│  │ Nh(Default)│                                      │ getter     │        │
│  └────────────┘                                      │ setter     │        │
│                                                      └──────┬─────┘        │
│                                                             │              │
│                                                    ┌────────▼──────┐       │
│                                                    │      DAO      │       │
│                                                    │ Data 변환     │       │
│                                                    │ Mapper Call   │       │
│                                                    └────────┬──────┘       │
│                                                             │              │
│                                                    ┌────────▼──────┐       │
│                                                    │  O-R Mapper   │       │
│                                                    │ Query Mapping │       │
│                                                    │ Query Execute │       │
│                                                    └────────┬──────┘       │
│                                                             │              │
│  ┌──────────────────────────────────────────────────────────┼────────────┐ │
│  │ Config                                                   │            │ │
│  │ application.yml / manifest.yml / gradle build           │            │ │
│  │ log4j2.xml / 업무config.yml / 배포.sh / 메시지.yml      │            │ │
│  └──────────────────────────────────────────────────────────┼────────────┘ │
│  ┌──────────────────────────────────────────────────────────┼────────────┐ │
│  │ 동적 Config Handler                                      │            │ │
│  └──────────────────────────────────────────────────────────┼────────────┘ │
│                                                                            │
│  ┌──────────────────────────────────────────────────────────────────────┐  │
│  │ Library                                                              │  │
│  │ NH 공통 / MIDAS / DTO / XDataSet / Spring Boot / File Handle        │  │
│  │ Log4j2 / Utility / SSO / 모니터링 / 암호화 / coverage               │  │
│  └──────────────────────────────────────────────────────────────────────┘  │
└───────────────────────────────────────┬────────────────────────────────────┘
                                        │
             ┌──────────────────────────┼──────────────────────────┐
             ▼                          ▼                          ▼
       ┌──────────┐                ┌──────────┐             ┌──────────────┐
       │ Database │                │   FOS    │             │ API G/W      │
       └──────────┘                └──────────┘             │ (Cruz APIM)  │
                                                           └──────────────┘
```

하단에는 운영·개발·배포 영역이 별도로 존재한다.

```text
┌───────────────────────────────┐
│          관리자               │
└───────────────┬───────────────┘
                ▼
┌─────────────────────────────────────────────────────────┐
│                   Master Solution                       │
│                                                         │
│  Admin(관리 UI)  ─  Master(서버)  ─  DB                │
└───────────────────┬─────────────────────────────────────┘
                    │ 동적 Config 관리
                    ▼
              동적 Config Handler


┌──────────────────────────────────────────────────────────────────────┐
│ 개발툴 (STS IDE)                                                     │
│                                                                      │
│  NH Cloud Framework                                                  │
│  Controller / AOP / Service / DAO / DTO / O-R Mapper / Config / Lib │
│                                                                      │
│  Plug-In                                                             │
│  DTO Plug-In / GIT Plug-In / BOOT Dashboard                         │
└──────────────────────────────────────────────────────────────────────┘
                    │
                    │ 배포
                    ▼
┌─────────────────────────┐
│          CI/CD          │
│ Jenkins / GitLab / Nexus│
└─────────────────────────┘
```

---

# 3. 구성요소별 분석

## 3.1 CLIENT

### 이미지에서 확인되는 내용

- Client는 `전용 단말`로 표현되어 있다.
- Client에서 NH Cloud Framework로 `request`가 전달된다.
- 처리 결과는 Client로 `response`가 반환된다.

### 아키텍처 의미

Client는 업무 애플리케이션의 외부 호출 주체이며, NH Cloud Framework는 Client와 업무 로직 사이에서 공통 실행환경을 제공하는 구조로 보인다.

```text
전용 단말
   │
   │ request
   ▼
NH Cloud Framework
   │
   │ response
   ▼
전용 단말
```

---

# 4. Controller 구조

## 4.1 이미지에서 확인되는 Controller 종류

| Controller | 이미지 표기 | 추정 역할 |
|---|---|---|
| `Nhins` | 화면 | 일반 화면 요청 진입 |
| `NhFile` | 파일 | 파일 처리 요청 |
| `NhRD` | 리포트 | 리포트/Report 처리 |
| `NhInbound` | Inbound | 외부 또는 대내 Inbound 처리 |
| `NhSSO` | SSO | SSO 관련 진입 |
| `Nh` | Default | 기본 온라인 요청 |

> `추정 역할`은 이름과 이미지의 괄호 표기를 근거로 한 아키텍처 해석이다. 세부 API/URL/메서드 계약은 이미지에서 확인할 수 없다.

## 4.2 구조적 의미

Controller를 요청 유형별로 분리한 구조다.

```text
Request
   │
   ├─ 화면 ─────→ Nhins
   ├─ 파일 ─────→ NhFile
   ├─ 리포트 ───→ NhRD
   ├─ Inbound ──→ NhInbound
   ├─ SSO ──────→ NhSSO
   └─ Default ──→ Nh
```

### 장점

- 요청 유형별 책임 구분이 명확하다.
- 파일/리포트/SSO와 일반 온라인 거래를 동일 Controller에 혼합하지 않는다.
- 공통 Controller 정책을 적용하기 쉽다.

### 확인 필요

- Controller가 업무 로직을 직접 수행하는지 여부
- Controller별 URL 규칙
- 공통 Request/Response Envelope 존재 여부
- 인증/인가 적용 시점
- 입력 Validation 책임
- Controller와 Service 간 직접 호출 여부

---

# 5. FWK LIB — 시스템 공통 선·후처리

이미지에는 `FWK LIB` 안에 다음 두 기능이 명확하게 표시되어 있다.

```text
FWK LIB
├─ 시스템 선 처리
└─ 시스템 후 처리
```

이는 Framework가 업무 로직 실행 전후에 **시스템 공통 처리를 수행하는 구조**임을 의미한다.

## 5.1 시스템 선처리 후보

이미지에는 상세 기능이 기재되어 있지 않으므로 아래는 일반적인 아키텍처 해석이며 실제 구현 확인이 필요하다.

```text
Request
   │
   ▼
시스템 선처리
   ├─ 요청 기본정보 확인
   ├─ 공통 Header 처리
   ├─ 인증/세션 정보 준비
   ├─ Trace/Logging Context 준비
   ├─ 공통 Validation
   └─ 시스템 공통 정책 적용
```

## 5.2 시스템 후처리 후보

```text
업무 처리 완료
   │
   ▼
시스템 후처리
   ├─ 공통 응답 생성
   ├─ 공통 오류 변환
   ├─ Response Header 처리
   ├─ Logging
   └─ Context 정리
```

## 5.3 핵심 평가

시스템 공통 선·후처리를 업무 AOP와 분리한 점은 구조적으로 적절하다.

```text
SYSTEM Concern
      │
      ▼
FWK LIB
시스템 선/후처리

BUSINESS Concern
      │
      ▼
AOP
업무 선/후처리
```

즉 **시스템 공통 관심사와 업무 공통 관심사를 서로 다른 실행 경계로 분리**한 구조다.

---

# 6. AOP — 업무 공통 선·후처리

이미지의 AOP 영역은 다음과 같다.

```text
AOP
├─ 업무 선 처리
└─ 업무 후 처리
```

## 6.1 의미

AOP는 비즈니스 Service 실행 전후에 업무 공통 정책을 적용하기 위한 계층으로 해석할 수 있다.

```text
FWK 시스템 선처리
        │
        ▼
AOP 업무 선처리
        │
        ▼
Service(Biz)
        │
        ▼
AOP 업무 후처리
        │
        ▼
FWK 시스템 후처리
```

## 6.2 장점

- Service에 반복되는 공통 코드 감소
- 업무 공통 정책의 중앙화 가능
- 시스템 공통처리와 업무 공통처리 분리 가능

## 6.3 확인 필요

다음 기능 중 실제 어느 항목이 AOP에 포함되는지는 이미지에서 확인되지 않는다.

- 업무 권한
- 업무 로그
- 업무 Validation
- Transaction
- Timeout
- 거래 통제
- 감사로그
- 성능 측정
- 업무 오류 변환

특히 **Transaction/Timeout을 AOP가 담당하는지 여부는 반드시 소스 또는 상세 설계서로 확인해야 한다.**

---

# 7. SERVICE — 비즈니스 처리

이미지에는 `SERVICE` 영역에 다음이 표시되어 있다.

```text
SERVICE
└─ Service (Biz)
```

## 7.1 역할

Service는 실제 비즈니스 로직 실행의 중심 계층으로 해석된다.

```text
Controller
   ↓
System Pre
   ↓
Business Pre
   ↓
Service(Biz)
   ↓
DAO
```

### 설계 원칙

Service가 담당해야 할 후보 책임은 다음과 같다.

- 업무 흐름
- 업무 판단
- 업무 규칙 실행
- DAO 호출
- 외부 연계 호출 조합
- 결과 구성

다만 이미지에서는 Service 내부 책임을 더 세분화하지 않는다.

---

# 8. DTO 구조

이미지에서 DTO는 다음과 같이 표현된다.

```text
DTO
├─ getter
└─ setter
```

## 8.1 의미

DTO는 계층 간 데이터를 전달하는 객체로 보인다.

```text
Controller
   │
   ▼
DTO
   │
   ▼
Service
   │
   ▼
DAO
```

## 8.2 확인 필요

이미지에는 다음이 나타나지 않는다.

- Request DTO / Response DTO 분리 여부
- DTO Validation 정책
- Domain Model과 DTO 분리 여부
- Entity 직접 노출 금지 여부
- Naming 규칙
- 표준전문 Header와 업무 DTO 분리 여부

따라서 DTO는 단순 Java Bean 수준으로만 표시되어 있으며, 계약 중심 DTO 설계는 별도 확인이 필요하다.

---

# 9. DAO 구조

이미지의 DAO는 다음 두 기능을 가진다.

```text
DAO
├─ Data 변환
└─ Mapper Call
```

## 9.1 역할

DAO는 Service와 O-R Mapper 사이에서 데이터 접근 경계를 담당하는 구조다.

```text
Service
   │
   ▼
DAO
   ├─ Data 변환
   └─ Mapper Call
           │
           ▼
      O-R Mapper
```

## 9.2 설계상 주의점

`Data 변환`과 `Mapper Call`을 하나의 DAO가 모두 담당할 경우 DAO가 과도한 책임을 가질 가능성이 있다.

확인이 필요한 질문은 다음과 같다.

1. Data 변환이 단순 DTO ↔ Mapper Parameter 변환인가?
2. 업무 규칙까지 DAO에 포함되는가?
3. SQL 호출만 담당하는가?
4. Result Mapping은 Mapper가 수행하는가 DAO가 수행하는가?

### 권장 책임 경계

```text
Service
  │
  ├─ 업무 로직
  │
  ▼
DAO
  │
  ├─ DB 접근 계약
  └─ Mapper 호출
       │
       ▼
Mapper
  │
  ├─ Query Mapping
  └─ Query Execute
```

업무 판단이 DAO 안으로 내려가는 구조는 피하는 것이 좋다.

---

# 10. O-R Mapper 구조

이미지에는 다음 기능이 표시되어 있다.

```text
O-R Mapper
├─ Query Mapping
└─ Query Execute
```

## 10.1 역할

O-R Mapper는 DAO의 호출을 실제 Query 실행으로 변환한다.

```text
DAO
   │
   ▼
O-R Mapper
   ├─ Query Mapping
   └─ Query Execute
           │
           ▼
        Database
```

## 10.2 확인 필요

이미지에는 실제 O-R Mapper 제품/기술이 명시되어 있지 않다.

따라서 다음을 임의로 확정해서는 안 된다.

- MyBatis
- Hibernate
- JPA
- iBatis
- 사내 Mapper Framework

실제 기술은 소스 또는 상세 기술문서 확인이 필요하다.

---

# 11. Database / FOS / API Gateway 연계

프레임워크 오른쪽에는 세 개의 외부 시스템이 표시되어 있다.

```text
Database
FOS
API G/W (Cruz APIM)
```

## 11.1 Database

DAO / O-R Mapper를 통해 접근하는 데이터 저장소로 표현되어 있다.

```text
Service
   ↓
DAO
   ↓
O-R Mapper
   ↓
Database
```

## 11.2 FOS

FOS는 별도 외부 시스템/솔루션 경계로 존재한다.

다만 이미지에는 다음 정보가 없다.

- 연계 프로토콜
- 호출 주체
- 동기/비동기 여부
- Timeout
- Retry
- 장애 처리
- 인증 방식

## 11.3 API G/W — Cruz APIM

API Gateway는 `Cruz APIM`으로 명시되어 있다.

```text
NH Cloud Framework
        │
        ▼
API G/W
(Cruz APIM)
        │
        ▼
외부/대내 API
```

### 확인 필요

- Gateway가 Inbound 전용인지 Outbound 연계에도 사용되는지
- 인증/인가 위치
- Rate Limit
- Timeout
- Circuit Breaker
- Retry
- API Version 정책
- API Logging
- Gateway 우회 호출 차단 정책

---

# 12. Config Architecture

이미지의 Config 영역에는 다음 파일이 확인된다.

| Config | 용도 해석 |
|---|---|
| `application.yml` | Spring/Application 설정 |
| `manifest.yml` | 실행/배포 메타정보 |
| `gradle build` | Gradle Build 정의 |
| `log4j2.xml` | Logging 설정 |
| `업무config.yml` | 업무별 설정 |
| `배포.sh` | 배포 Script |
| `메시지.yml` | Message/Code 설정 |

## 12.1 구조

```text
Config
├─ application.yml
├─ manifest.yml
├─ gradle build
├─ log4j2.xml
├─ 업무config.yml
├─ 배포.sh
└─ 메시지.yml
```

## 12.2 평가

설정, 로깅, 업무 설정, 메시지, 빌드, 배포가 명시적으로 분리되어 있다는 점은 장점이다.

다만 다음 구분이 필요하다.

```text
정적 Config
├─ Build Time
├─ Deploy Time
└─ Boot Time

동적 Config
└─ Runtime
```

정적 Config와 Runtime 동적 Config의 우선순위 및 충돌 정책이 반드시 정의되어야 한다.

---

# 13. 동적 Config Handler

이미지 하단에는 `동적 Config Handler`가 독립적으로 표현되어 있고 Master Solution의 `동적 Config 관리`와 연결된다.

## 13.1 전체 구조

```text
관리자
   │
   ▼
Master Solution
   │
   │ 동적 Config 관리
   ▼
동적 Config Handler
   │
   ▼
NH Cloud Framework Runtime
```

## 13.2 아키텍처 의미

이는 Config가 애플리케이션 파일에 고정되는 것만이 아니라, 운영 중 중앙 관리될 수 있음을 의미한다.

### 장점

- 환경별 설정 중앙 관리
- 운영변경의 신속성
- 재배포 없는 일부 설정 변경 가능성
- 표준 정책 강제 가능

### 주요 위험

동적 Config는 강력한 기능인 만큼 아래 통제가 필요하다.

1. 변경권한
2. 승인절차
3. 변경이력
4. 적용대상
5. 배포/반영 시점
6. Rollback
7. 버전관리
8. Config Validation
9. 잘못된 설정 확산 방지
10. Master 장애 시 동작정책

---

# 14. Master Solution

이미지의 Master Solution은 다음 구조다.

```text
Master Solution
├─ Admin (관리 UI)
├─ Master (서버)
└─ DB
```

관리자와 연결되고, 동적 Config 관리 기능을 제공한다.

## 14.1 Control Plane 관점

Master Solution은 온라인 요청을 직접 처리하는 Data Plane이라기보다 **운영 Control Plane**에 가깝다.

```text
┌──────────────────────────────────┐
│ CONTROL PLANE                    │
│                                  │
│ Admin UI                         │
│ Master Server                    │
│ Config DB                        │
└────────────────┬─────────────────┘
                 │
                 │ Policy / Config
                 ▼
┌──────────────────────────────────┐
│ DATA / RUNTIME PLANE             │
│                                  │
│ NH Cloud Framework               │
│ Online Transaction               │
└──────────────────────────────────┘
```

## 14.2 중요 검증항목

- Master Server HA
- Master DB HA
- 관리자 인증/인가
- Config 변경 감사로그
- 변경 승인 Workflow
- Config Version
- Runtime 전파 실패 처리
- Master 장애 시 기존 설정 유지 여부
- 환경별 DEV/TEST/PROD 분리
- 운영자 권한분리

---

# 15. Library Architecture

이미지에는 다음 공통 Library가 보인다.

| Library | 기능 해석 |
|---|---|
| NH 공통 | 농협 공통 기능 |
| MIDAS | 사내/솔루션 공통 기능 |
| DTO | DTO 공통 지원 |
| XDataSet | 데이터셋 처리 |
| Spring Boot | 애플리케이션 Runtime |
| File Handle | 파일 처리 |
| Log4j2 | Logging |
| Utility | 공통 Utility |
| SSO | Single Sign-On |
| 모니터링 | Runtime Monitoring |
| 암호화 | 암복호화 |
| coverage | 테스트 Coverage |

## 15.1 구조적 의미

Framework가 개별 기능을 직접 모두 구현하기보다 공통 Library 집합을 제공한다.

```text
NH Cloud Framework
        │
        ├─ Common
        ├─ DTO
        ├─ XDataSet
        ├─ Logging
        ├─ Security
        ├─ Monitoring
        ├─ File
        └─ Utility
```

## 15.2 관리상 주의점

공통 Library는 다음 정책이 필요하다.

- Library Owner
- Version
- Compatibility
- Dependency Rule
- 사용 허용/금지
- Deprecated 정책
- CVE 관리
- 배포 Repository
- 공통 Library 변경 영향도
- 애플리케이션별 버전 Drift 탐지

---

# 16. 개발도구 — STS IDE

이미지의 개발도구는 `STS IDE`로 표현된다.

## 16.1 포함 Framework 구성

```text
NH Cloud Framework
├─ Controller
├─ AOP
├─ Service
├─ DAO
├─ DTO
├─ O-R Mapper
├─ Config
└─ Library
```

즉 Runtime Framework의 구성과 개발 IDE의 구조가 대응된다.

## 16.2 Plug-In

이미지에는 다음 Plugin이 표시된다.

```text
Plug-In
├─ DTO Plug-In
├─ GIT Plug-In
└─ BOOT Dashboard
```

## 16.3 아키텍처 의미

개발자가 표준 구조를 IDE에서 바로 사용할 수 있도록 개발 생산성 도구를 함께 제공하는 방식이다.

### 장점

- 표준 코드 생성 가능
- DTO 표준화 가능
- Git Workflow 통합
- Spring Boot 실행/상태 확인
- 신규 개발자 진입장벽 완화

### 확인 필요

- 프로젝트 생성 Template
- Service/DAO/Mapper Code Generator
- 표준 검사 기능
- Naming Rule 자동검증
- Dependency Rule 자동검증
- CI Gate 연계
- Plugin 버전관리

---

# 17. CI/CD Architecture

이미지 오른쪽 하단에는 다음 도구가 표시되어 있다.

```text
CI/CD
├─ Jenkins
├─ GitLab
└─ Nexus
```

## 17.1 역할 해석

| 도구 | 주요 역할 |
|---|---|
| GitLab | Source / Branch / Merge 관리 |
| Jenkins | Build / Test / Deploy Pipeline |
| Nexus | Artifact Repository |

## 17.2 예상 흐름

```text
Developer / STS
      │
      ▼
    GitLab
      │
      ▼
   Jenkins
      │
      ├─ Build
      ├─ Test
      ├─ Package
      └─ Deploy
      │
      ▼
     Nexus
      │
      ▼
NH Cloud Framework Runtime
```

> 위 실행순서는 도구 역할을 바탕으로 한 전형적 흐름이며, 이미지 자체가 정확한 Pipeline 순서를 정의하지는 않는다.

## 17.3 추가 확인 필요

이미지에는 다음 Quality/Security Gate가 나타나지 않는다.

- Unit Test Gate
- Integration Test
- Code Quality
- SAST
- OSS/CVE Scan
- Secret Scan
- Artifact Signing
- Deployment Approval
- Rollback
- Blue/Green / Rolling
- 운영 배포 승인체계

---

# 18. 온라인 거래 E2E 흐름 재구성

이미지에 표시된 연결관계를 종합하면 온라인 요청 처리 흐름은 다음처럼 정리할 수 있다.

```text
[Client / 전용 단말]
        │
        │ Request
        ▼
[Controller]
        │
        ├─ Nhins
        ├─ NhFile
        ├─ NhRD
        ├─ NhInbound
        ├─ NhSSO
        └─ Nh(Default)
        │
        ▼
[FWK LIB]
시스템 선처리
        │
        ▼
[AOP]
업무 선처리
        │
        ▼
[SERVICE]
Service(Biz)
        │
        ▼
[DTO]
        │
        ▼
[DAO]
Data 변환
Mapper Call
        │
        ▼
[O-R Mapper]
Query Mapping
Query Execute
        │
        ├──────────→ Database
        │
        ├──────────→ FOS
        │
        └──────────→ API G/W (Cruz APIM)
        │
        ▼
[AOP]
업무 후처리
        │
        ▼
[FWK LIB]
시스템 후처리
        │
        ▼
[Controller]
        │
        │ Response
        ▼
[Client]
```

### 주의

위 흐름 중 `Service → DTO → DAO`의 정확한 호출 순서와 FOS/API Gateway의 실제 호출 위치는 원본 그림의 선만으로 완전히 확정하기 어렵다. 따라서 E2E 흐름은 **구성요소 관계를 이해하기 위한 논리 재구성**으로 사용하고, 실제 Source/Sequence Diagram으로 검증해야 한다.

---

# 19. 선·후처리 아키텍처의 핵심

이 구성도의 가장 중요한 특징 중 하나는 선·후처리를 두 계층으로 분리한 것이다.

```text
┌──────────────────────────────┐
│ 시스템 공통 선·후처리       │
│ FWK LIB                      │
└─────────────┬────────────────┘
              │
              ▼
┌──────────────────────────────┐
│ 업무 공통 선·후처리         │
│ AOP                          │
└─────────────┬────────────────┘
              │
              ▼
┌──────────────────────────────┐
│ 실제 업무 처리              │
│ Service(Biz)                 │
└──────────────────────────────┘
```

## 19.1 장점

- 시스템 Concern과 Business Concern 분리
- 공통 코드 중앙화
- 업무 코드 단순화
- 정책 일관성 확보
- 표준화에 유리
- Framework 변경과 업무 변경의 영향도 분리 가능

## 19.2 반드시 확인해야 할 경계

| 기능 | 권장 책임 |
|---|---|
| 인증/세션/GUID | 시스템 공통 |
| 공통 Request/Response | 시스템 공통 |
| 공통 Logging | 시스템 공통 |
| 업무 전제조건 | 업무 선처리 또는 Service |
| 업무 감사 | 업무 공통 |
| 업무 Validation | Service/Validation |
| Transaction | 명확한 거래 경계 필요 |
| Timeout | Transaction과 연계된 실행경계 필요 |
| DB Access | DAO/Mapper |
| Error Mapping | Framework 공통과 업무 오류 구분 |

---

# 20. 전체 아키텍처를 Control Plane / Runtime Plane으로 재분류

원본 그림을 현대적인 관점으로 다시 분류하면 다음과 같이 볼 수 있다.

```text
┌───────────────────────────────────────────────┐
│              CONTROL PLANE                    │
│                                               │
│ Master Solution                               │
│ ├─ Admin UI                                   │
│ ├─ Master Server                              │
│ └─ Config DB                                  │
│                                               │
│ CI/CD                                         │
│ ├─ GitLab                                     │
│ ├─ Jenkins                                    │
│ └─ Nexus                                      │
└─────────────────────┬─────────────────────────┘
                      │ Config / Build / Deploy
                      ▼
┌───────────────────────────────────────────────┐
│              RUNTIME PLANE                    │
│                                               │
│ Controller                                    │
│ FWK LIB                                       │
│ AOP                                           │
│ Service                                       │
│ DTO                                           │
│ DAO                                           │
│ O-R Mapper                                    │
│ Config / Library                              │
└─────────────────────┬─────────────────────────┘
                      │
                      ▼
             DB / FOS / API Gateway
```

이 분리는 운영 통제와 실제 거래 처리를 구별하는 데 유용하다.

---

# 21. 아키텍처 장점

## 21.1 계층 분리

Controller → Framework → AOP → Service → DAO → Mapper 구조로 책임을 나누고 있다.

## 21.2 공통 선·후처리 분리

시스템 공통과 업무 공통을 별도 계층으로 분리한다.

## 21.3 설정 외부화

`application.yml`, 업무 config, 메시지 config 등이 애플리케이션 코드와 분리되어 있다.

## 21.4 동적 Config 관리

Master Solution을 통해 Runtime 설정을 중앙 통제할 수 있는 구조다.

## 21.5 개발표준 도구화

STS IDE와 Plugin을 통해 개발자가 Framework 구조를 직접 사용할 수 있다.

## 21.6 CI/CD 도구 체계

GitLab + Jenkins + Nexus를 통해 소스→빌드→Artifact→배포를 연결할 기반이 있다.

## 21.7 외부 연계 경계

Database, FOS, API Gateway를 프레임워크 외부 경계로 표현한다.

## 21.8 공통 Library 제공

Logging, SSO, 암호화, 모니터링, File, Utility 등을 공통화한다.

---

# 22. 설계상 주요 GAP 및 확인 필요사항

아래 항목은 **원본 그림만으로 확인되지 않는 영역**이며, 실제 운영 표준으로 사용하려면 반드시 상세 설계 또는 소스로 검증해야 한다.

| GAP ID | 영역 | 확인 필요사항 | 중요도 |
|---|---|---|---|
| GAP-01 | Transaction | Transaction 시작/종료 위치 | 매우 높음 |
| GAP-02 | Timeout | 거래 Timeout 계층 및 취소 방식 | 매우 높음 |
| GAP-03 | Error | 공통 오류/업무 오류 처리 체계 | 매우 높음 |
| GAP-04 | Security | 인증·인가 상세 Flow | 매우 높음 |
| GAP-05 | Trace | GUID/TraceId/MDC 기준 | 높음 |
| GAP-06 | Observability | Metrics/Trace/Log 연계 | 높음 |
| GAP-07 | API | API Gateway Timeout/Retry/Circuit Breaker | 높음 |
| GAP-08 | FOS | FOS 장애/Timeout 처리 | 높음 |
| GAP-09 | Config | 동적 Config 승인/이력/Rollback | 매우 높음 |
| GAP-10 | HA | Master Solution 이중화 | 높음 |
| GAP-11 | DAO | Data 변환 책임 범위 | 중간 |
| GAP-12 | DTO | Request/Response/Validation 규칙 | 중간 |
| GAP-13 | Controller | URL/Service 식별 규칙 | 높음 |
| GAP-14 | Mapper | 실제 O-R Mapper 기술/SQL Timeout | 높음 |
| GAP-15 | CI/CD | Quality/Security Gate | 높음 |
| GAP-16 | Test | Unit/Integration/Runtime Test 체계 | 높음 |
| GAP-17 | Config | 정적/동적 Config 우선순위 | 높음 |
| GAP-18 | Secrets | Password/Key/Secret 관리 위치 | 매우 높음 |
| GAP-19 | Dependency | 공통 Library Version 정책 | 높음 |
| GAP-20 | Runtime | 장애·복구·Failover 시나리오 | 매우 높음 |

---

# 23. 금지 패턴 후보

다음은 원본 구성도를 실제 개발표준으로 발전시킬 때 명확히 금지할 필요가 있는 패턴이다.

```text
[금지] Controller에 업무 로직 직접 구현

[금지] Controller → Mapper 직접 호출

[금지] Service → SQL 직접 실행

[금지] 업무 Service에서 시스템 공통 Header/세션을 직접 조작

[금지] DAO에 업무 정책 구현

[금지] 운영 Config를 Source Code에 Hard Coding

[금지] Master Config를 승인 없이 운영 반영

[금지] API 연계에 Timeout 없이 무한 대기

[금지] Retry를 업무 멱등성 검토 없이 적용

[금지] Secret/Private Key를 일반 yml에 평문 저장

[금지] 공통 Library를 애플리케이션별 임의 버전으로 Drift

[금지] 운영 배포 Artifact를 개발자 PC에서 직접 생성

[금지] Git 이력 없이 운영 서버 파일 직접 수정
```

---

# 24. 권장 정상 패턴

```text
Client
  ↓
Controller
  ↓
System Pre Processing
  ↓
Business Pre Processing
  ↓
Service / Use Case
  ↓
DAO
  ↓
Mapper
  ↓
DB
  ↓
Business Post Processing
  ↓
System Post Processing
  ↓
Response
```

운영 통제는 별도로 다음과 같이 둔다.

```text
Admin
  ↓
Master Solution
  ↓
Versioned Config
  ↓
Approval
  ↓
Dynamic Config Handler
  ↓
Runtime Apply
  ↓
Audit / Evidence
```

개발/배포는 다음과 같이 관리한다.

```text
STS IDE
   ↓
GitLab
   ↓
CI Build / Test
   ↓
Nexus Artifact
   ↓
Deploy Approval
   ↓
Runtime
```

---

# 25. NSIGHT 아키텍처 관점의 대응 해석

> 이 절은 원본 이미지에 직접 쓰인 명칭이 아니라, 현재 NSIGHT에서 사용하는 아키텍처 개념과의 **비교 해석**이다. 1:1 동일 구현이라고 확정해서는 안 된다.

| 원본 온라인 Framework | NSIGHT 관점의 대응 개념 |
|---|---|
| Controller | Inbound Controller / 공통 진입점 |
| FWK LIB 시스템 선·후처리 | System Pre/Post Processing |
| AOP 업무 선·후처리 | Business Pre/Post Processing |
| Service(Biz) | 업무 Service / Use Case |
| DTO | Business Request/Response DTO |
| DAO | Data Access Boundary |
| O-R Mapper | Mapper / Query Layer |
| 동적 Config Handler | Runtime Config Control |
| Master Solution | Operation/Control Plane |
| 모니터링 Library | Observability |
| SSO Library | Authentication Integration |
| 암호화 Library | Security Common |
| Jenkins/GitLab/Nexus | CI/CD Toolchain |

## 25.1 가장 큰 구조적 연속성

원본 그림이 이미 다음 세 가지 분리를 갖고 있다는 점이 중요하다.

```text
SYSTEM PRE/POST
      ≠
BUSINESS PRE/POST
      ≠
BUSINESS SERVICE
```

이는 향후 TCF 구조를 설계할 때도 매우 유효한 기준이 된다.

---

# 26. 권장 TO-BE 상세 구조

원본 구조를 유지하면서 Runtime 통제를 강화한다면 다음과 같이 확장할 수 있다.

```text
CLIENT
  │
  ▼
API / WEB ENTRY
  │
  ▼
Controller
  │
  ▼
────────────────────────────────────────────
SYSTEM COMMON
────────────────────────────────────────────
Request Context
Authentication
Authorization
GUID / TraceId
Message Validation
System Logging
  │
  ▼
────────────────────────────────────────────
TRANSACTION / FRAMEWORK
────────────────────────────────────────────
Transaction Context
Transaction Control
Timeout
Idempotency
Dispatcher
  │
  ▼
────────────────────────────────────────────
BUSINESS
────────────────────────────────────────────
Business Pre
Handler / Use Case
Service
Rule
DAO
Mapper
Business Post
  │
  ▼
────────────────────────────────────────────
RESOURCE
────────────────────────────────────────────
DB
FOS
API Gateway
External Service
  │
  ▼
Response / Error / Evidence
```

이는 원본의 구조적 장점을 유지하면서 Transaction, Timeout, Dispatcher, Runtime Evidence 같은 운영 핵심 경계를 더 명확히 만드는 방향이다.

---

# 27. Source / Interpretation / Open Issue 구분

## 27.1 CONFIRMED — 이미지에서 직접 확인

- NH Cloud Framework 명칭
- Controller 6종
- FWK LIB 시스템 선/후처리
- AOP 업무 선/후처리
- Service(Biz)
- DTO getter/setter
- DAO Data 변환/Mapper Call
- O-R Mapper Query Mapping/Query Execute
- Config 파일 목록
- 동적 Config Handler
- Library 목록
- Database
- FOS
- API G/W(Cruz APIM)
- Master Solution
- Admin/Master/DB
- 동적 Config 관리
- STS IDE
- DTO/GIT/BOOT Dashboard Plugin
- Jenkins
- GitLab
- Nexus
- Client request/response

## 27.2 INFERENCE — 구조를 바탕으로 해석

- Controller별 정확한 책임
- 시스템 선/후처리 상세 기능
- 업무 선/후처리 상세 기능
- Service의 Use Case 역할
- CI/CD 단계 순서
- Control Plane / Runtime Plane 분리 개념
- API Gateway의 Outbound 연계 여부
- DTO의 Request/Response 역할

## 27.3 UNKNOWN — 원본 이미지로 확인 불가

- Framework 버전
- Java 버전
- Spring Boot 버전
- O-R Mapper 실제 제품
- Transaction 경계
- Timeout 구현
- Error Handling
- Retry/Circuit Breaker
- JWT 여부
- SSO 상세 Flow
- Session 구조
- 권한 모델
- GUID/TraceId 정책
- Logging Format
- Metrics/Trace 수집 방식
- Master HA
- Config Rollback
- 배포전략
- DB Pool
- Thread Pool
- 성능 기준
- DR 구조

---

# 28. Architecture Review 체크리스트

## 28.1 Controller

- [ ] Controller별 URL 규칙이 정의되어 있는가?
- [ ] Controller에 업무 로직이 없는가?
- [ ] 공통 Request/Response 계약이 있는가?
- [ ] Validation 책임이 명확한가?

## 28.2 Framework Common

- [ ] 시스템 선처리 항목이 문서화되어 있는가?
- [ ] 시스템 후처리 항목이 문서화되어 있는가?
- [ ] 공통 Context가 정의되어 있는가?
- [ ] ThreadLocal 사용 시 Clear가 보장되는가?

## 28.3 AOP

- [ ] 업무 선처리 Pointcut이 명확한가?
- [ ] 업무 후처리 Pointcut이 명확한가?
- [ ] AOP 순서가 명확한가?
- [ ] 예외 시 후처리 동작이 정의되어 있는가?

## 28.4 Transaction / Timeout

- [ ] Transaction 시작 위치가 명확한가?
- [ ] Transaction 종료 위치가 명확한가?
- [ ] Timeout과 Transaction이 정합적인가?
- [ ] Timeout 시 DB 작업 취소 정책이 있는가?

## 28.5 Service

- [ ] Service가 업무 책임만 가지는가?
- [ ] DB 구현 세부사항에 직접 의존하지 않는가?
- [ ] 외부 연계 Timeout 정책이 있는가?

## 28.6 DAO / Mapper

- [ ] DAO와 Mapper 책임이 분리되어 있는가?
- [ ] Mapper Query Timeout이 있는가?
- [ ] SQL ID/Naming 규칙이 있는가?
- [ ] DB Paging이 표준화되어 있는가?

## 28.7 Config

- [ ] 정적 Config와 동적 Config가 분리되어 있는가?
- [ ] Config 우선순위가 정의되어 있는가?
- [ ] Config 변경 이력이 남는가?
- [ ] Rollback이 가능한가?
- [ ] Secret은 별도 관리되는가?

## 28.8 Master Solution

- [ ] Admin 권한이 분리되어 있는가?
- [ ] Master가 HA인가?
- [ ] Config DB가 HA인가?
- [ ] 운영 변경 승인절차가 있는가?
- [ ] 모든 변경에 Audit Trail이 남는가?

## 28.9 Security

- [ ] SSO 인증 흐름이 정의되어 있는가?
- [ ] Authorization이 별도 정의되어 있는가?
- [ ] 암호화 Key 관리가 정의되어 있는가?
- [ ] Secret이 Config에 평문 저장되지 않는가?

## 28.10 Observability

- [ ] 거래 식별자가 존재하는가?
- [ ] Log/Metric/Trace가 상호 연결되는가?
- [ ] 장애 발생 Service/Query를 추적할 수 있는가?
- [ ] Runtime Evidence가 보존되는가?

## 28.11 CI/CD

- [ ] Git Merge 정책이 있는가?
- [ ] Jenkins Pipeline이 코드화되어 있는가?
- [ ] Nexus Artifact가 Immutable한가?
- [ ] Unit/Integration Test Gate가 있는가?
- [ ] SAST/OSS/CVE 검사가 있는가?
- [ ] 운영 배포 승인절차가 있는가?
- [ ] Rollback이 가능한가?

---

# 29. 최종 평가

이 온라인 프레임워크 구성도는 단순 Spring 애플리케이션 계층도가 아니라 다음 **5개 축을 하나로 묶은 통합 Framework Architecture**로 볼 수 있다.

```text
1. Runtime Framework
   Controller
   FWK LIB
   AOP
   Service
   DAO
   Mapper

2. Common Platform
   Config
   Library
   SSO
   Logging
   Monitoring
   Encryption

3. Integration
   Database
   FOS
   API Gateway

4. Operation / Control
   Master Solution
   Dynamic Config

5. Development / Delivery
   STS IDE
   Plug-In
   GitLab
   Jenkins
   Nexus
```

가장 큰 장점은 **애플리케이션 Runtime, 공통기능, 동적설정, 개발도구, CI/CD를 하나의 Framework Ecosystem으로 설계했다는 점**이다.

반면 구성도만으로는 다음이 보이지 않는다.

```text
Transaction
Timeout
Error Handling
Trace
Security Detail
Runtime Evidence
HA/DR
Config Governance
Quality Gate
```

따라서 이 그림을 NSIGHT의 최종 온라인 Framework Baseline으로 사용하려면 위 항목을 상세 아키텍처와 실제 소스로 검증하여 보완해야 한다.

---

# 30. 한 문장 정의

> **NH Cloud Framework 온라인 프레임워크는 요청 유형별 Controller를 진입점으로 하여 시스템 공통 선·후처리와 업무 공통 선·후처리를 분리하고, Service–DAO–O/R Mapper 계층으로 업무와 데이터를 처리하며, 중앙 Config 관리·공통 Library·개발 IDE·CI/CD를 함께 제공하는 통합 온라인 애플리케이션 실행 및 개발 플랫폼이다.**

---

# 31. 권장 후속 산출물

이 구성도를 실제 Architecture Baseline으로 발전시키기 위해 다음 문서를 후속으로 작성하는 것이 적절하다.

1. `온라인_거래_E2E_시퀀스.md`
2. `시스템_선후처리_상세설계.md`
3. `업무_선후처리_AOP_상세설계.md`
4. `Transaction_Timeout_아키텍처.md`
5. `Error_Handling_아키텍처.md`
6. `Dynamic_Config_운영통제_아키텍처.md`
7. `Logging_Observability_아키텍처.md`
8. `SSO_인증인가_아키텍처.md`
9. `API_Gateway_연계_표준.md`
10. `DAO_Mapper_SQL_개발표준.md`
11. `CI_CD_배포_표준.md`
12. `Architecture_Conformance_Rules.md`

---

## Appendix A. 요약 텍스트 구조

```text
전용 단말
   │
   ▼
Controller
   │
   ▼
FWK LIB
시스템 선처리
   │
   ▼
AOP
업무 선처리
   │
   ▼
Service(Biz)
   │
   ▼
DTO
   │
   ▼
DAO
Data 변환 / Mapper Call
   │
   ▼
O-R Mapper
Query Mapping / Query Execute
   │
   ├── Database
   ├── FOS
   └── API G/W(Cruz APIM)
   │
   ▼
AOP
업무 후처리
   │
   ▼
FWK LIB
시스템 후처리
   │
   ▼
Response
```

## Appendix B. 운영/개발 지원구조

```text
관리자
   │
   ▼
Master Solution
Admin UI / Master / DB
   │
   ▼
동적 Config Handler
   │
   ▼
NH Cloud Framework


개발자
   │
   ▼
STS IDE
Framework + Plug-In
   │
   ▼
GitLab
   │
   ▼
Jenkins
   │
   ▼
Nexus
   │
   ▼
배포
```
