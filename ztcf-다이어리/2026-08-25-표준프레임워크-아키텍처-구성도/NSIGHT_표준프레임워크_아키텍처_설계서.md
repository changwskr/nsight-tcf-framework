# NSIGHT 표준 프레임워크 아키텍처 설계서

> 문서 성격: Application Framework Architecture Design / Working Baseline  
> 대상: NH 농협 상호금융 NSIGHT 차세대 정보계  
> 기준 자료: `NSIGHT_표준프레임워크_아키텍처구성_추가반영_통합본.pptx`, `NSIGHT_표준프레임워크_아키텍처구성_분석.md`  
> 작성 원칙: 원본에서 확인된 사실과 설계 규칙, 미확정 항목을 구분한다.

---

# 0. 문서 통제

## 0.1 문서 목적

본 설계서는 NSIGHT 차세대 정보계의 **온라인 애플리케이션 프레임워크와 배치 프레임워크의 표준 구조, 책임, 경계, 실행 흐름, 연계 규칙, 개발·운영 통제 기준**을 정의한다.

본 문서의 핵심 목적은 다음과 같다.

```text
업무 개발자
인프라 담당자
프레임워크 담당자
운영 담당자
CI/CD 담당자
아키텍처 담당자
        │
        ▼
동일한 Framework Architecture 이해
        │
        ▼
동일한 계층 / 동일한 실행경계 / 동일한 표준경로 적용
        │
        ▼
개발·배포·운영·검증의 일관성 확보
```

본 문서는 단순 구성요소 설명서가 아니다.

```text
Architecture Diagram
        ↓
Architecture Design
        ↓
Architecture Rule
        ↓
Development Standard
        ↓
Runtime Verification
```

으로 연결하기 위한 **프레임워크 설계 기준선**이다.

---

## 0.2 적용 범위

본 설계서의 적용 범위는 다음과 같다.

| 구분 | 적용 범위 |
|---|---|
| 온라인 | NH Cloud Framework 기반 요청/응답 처리 |
| Controller | 6종 표준 Controller |
| 공통처리 | FWK LIB 기반 시스템 선·후처리 |
| 업무공통 | AOP 기반 업무 선·후처리 |
| 업무계층 | Service(Biz), DTO, DAO, O-R Mapper |
| 설정 | Config / Dynamic Config Handler |
| 공통기능 | Library |
| 외부연계 | Database, FOS, API G/W(Cruz APIM), Service Registry |
| 개발환경 | STS IDE, Framework Plug-In |
| 형상·배포 | GitLabRunner, GitLab, NEXUS |
| 중앙관리 | Master Solution Admin / Master / DB |
| 배치 | Control-M Agent, Batch Shell, Spring Batch |
| 배치 실행 | JobLauncher, Job, Step, Reader/Processor/Writer, Tasklet |
| 배치 메타 | Spring Job Repository |
| 검증 | 구조, Traceability, Runtime Evidence 후보 |

---

## 0.3 비적용 / 별도 상세설계 대상

원본 장표만으로 확정할 수 없는 다음 항목은 본 문서에서 임의 확정하지 않는다.

```text
Transaction Boundary
Timeout Policy
공통 Exception 상세 구조
JWT / Session / Authorization 세부구조
API Gateway 상세 Routing Policy
Service Registry 등록/조회/Health 정책
FOS와 ObjectStorage의 정확한 관계
CI/CD Pipeline 단계/승인/롤백 규칙
Batch Restart / Retry / Skip 정책
Batch Shell Parameter / Exit Code 표준
Job Repository Retention / Purge 정책
Control-M ↔ Spring Batch Trace Key
```

이 항목은 `TBD / GAP`으로 관리하며 후속 상세설계에서 확정한다.

---

## 0.4 설계 상태 표기

본 설계서는 다음 상태를 사용한다.

| 상태 | 의미 |
|---|---|
| `CONFIRMED` | 원본 장표에서 직접 확인된 구조 |
| `DESIGN` | 원본 구조를 설계 규칙으로 구체화한 내용 |
| `CANDIDATE` | 향후 표준으로 검증·승인할 후보 |
| `TBD` | 원본만으로 확정할 수 없어 추가 확인 필요 |
| `PROHIBITED` | 아키텍처 표준 관점에서 금지하는 패턴 |

---

# 1. 설계 목표와 핵심 원칙

## 1.1 설계 목표

NSIGHT 표준 프레임워크는 다음 목표를 가진다.

1. 요청 유형별 표준 진입점을 제공한다.
2. 시스템 공통처리와 업무 공통처리를 분리한다.
3. 업무 로직의 위치와 데이터 접근 경계를 고정한다.
4. 설정과 공통 라이브러리를 분리한다.
5. 외부 연계를 표준 경계로 통제한다.
6. 개발도구·형상·배포·Artifact 관리와 추적성을 확보한다.
7. 온라인과 배치를 동일한 표준화 원칙으로 관리한다.
8. 배치 스케줄·실행·메타데이터를 분리한다.
9. 구조를 자동검증 가능한 규칙으로 발전시킨다.
10. Runtime에서 설계 준수 여부를 확인할 수 있어야 한다.

---

## 1.2 최상위 설계 원칙

```text
표준 진입점
    ↓
공통 실행경계
    ↓
업무 실행경계
    ↓
데이터/외부 연계경계
    ↓
운영·배포·관측 경계
```

핵심 원칙은 다음과 같다.

| 원칙 ID | 원칙 | 상태 |
|---|---|---|
| P-FWK-001 | 요청 유형에 맞는 Controller를 사용한다. | DESIGN |
| P-FWK-002 | 시스템 공통과 업무 공통을 분리한다. | DESIGN |
| P-FWK-003 | 핵심 업무 로직은 Service(Biz)에 둔다. | DESIGN |
| P-FWK-004 | 데이터 접근은 DAO → O-R Mapper 경계로 관리한다. | DESIGN |
| P-FWK-005 | 설정은 Config 영역으로 외부화한다. | DESIGN |
| P-FWK-006 | 공통 실행기능은 Library로 재사용한다. | DESIGN |
| P-FWK-007 | 동적 설정은 Master Solution 관리경계를 통해 반영한다. | DESIGN |
| P-BAT-001 | 배치 실행은 Control-M Agent → Shell → Spring Batch 경로로 관리한다. | DESIGN |
| P-BAT-002 | Step은 Reader/Processor/Writer 또는 Tasklet 구조를 사용한다. | CONFIRMED |
| P-BAT-003 | Job/Step 실행 메타는 Job Repository와 연결한다. | DESIGN |

---

# 2. 전체 프레임워크 Big Picture

## 2.1 통합 아키텍처

```text
                                      NSIGHT APPLICATION FRAMEWORK

┌────────────────────────────────────────────────────────────────────────────────────────────┐
│                                            CLIENT                                          │
│                                                                                            │
│   범용 단말                전용 단말                 C2C / 외부 / 시스템 연계              │
└──────────────┬───────────────────┬──────────────────────────────┬───────────────────────────┘
               │ request           │                              │
               ▼                   ▼                              ▼
┌────────────────────────────────────────────────────────────────────────────────────────────┐
│                                      CONTROLLER BOUNDARY                                   │
│                                                                                            │
│  Nhins        NhFile        NhRD        NhInbound        NhSso        Nh(Default)           │
│  화면         파일          Report      EAI/JSON         SSO          C2C 표준전문          │
└──────────────────────────────────────────────┬─────────────────────────────────────────────┘
                                               │
                                               ▼
┌────────────────────────────────────────────────────────────────────────────────────────────┐
│                                        FWK LIB                                             │
│                                                                                            │
│                        시스템 선 처리 / 시스템 후 처리                                     │
└──────────────────────────────────────────────┬─────────────────────────────────────────────┘
                                               │
                                               ▼
┌────────────────────────────────────────────────────────────────────────────────────────────┐
│                                           AOP                                              │
│                                                                                            │
│                          업무 선 처리 / 업무 후 처리                                      │
└──────────────────────────────────────────────┬─────────────────────────────────────────────┘
                                               │
                                               ▼
┌────────────────────────────────────────────────────────────────────────────────────────────┐
│                                      BUSINESS LAYER                                        │
│                                                                                            │
│      Service(Biz)                 DTO                         DAO                           │
│                                  getter/setter               Data 변환 / Mapper Call        │
└──────────────────────────────────────────────┬─────────────────────────────────────────────┘
                                               │
                                               ▼
┌────────────────────────────────────────────────────────────────────────────────────────────┐
│                                     O-R MAPPER                                             │
│                                                                                            │
│                              Query Mapping / Query Execute                                  │
└───────────────────────────────┬────────────────────────────────────────────────────────────┘
                                │
               ┌────────────────┼──────────────────────┬────────────────────────────┐
               ▼                ▼                      ▼                            ▼
          Database             FOS            API G/W(Cruz APIM)            Service Registry


Cross-cutting / Support
─────────────────────────────────────────────────────────────────────────────────────────────
Config                    Library                    Master Solution                 Dev / CI-CD
application.yml           NH 공통                    Admin UI                        STS IDE
manifest.yml              MIDAS                      Master Server                   Plug-In
log4j2.xml                Spring Boot                DB                              GitLabRunner
업무 config.yml           File Handle                Dynamic Config 관리             GitLab
메시지 yml                Log4j2 / Utility                                           NEXUS
배포 .sh                  SSO / 모니터링
Dynamic Config Handler    암호화 / coverage
```

---

## 2.2 설계 계층

```text
L0 Client / External
      ↓
L1 Controller
      ↓
L2 FWK LIB System Common
      ↓
L3 AOP Business Common
      ↓
L4 Service(Biz)
      ↓
L5 DTO / DAO
      ↓
L6 O-R Mapper
      ↓
L7 DB / FOS / API G/W / Registry

Cross-cutting
Config / Library / Master Solution / IDE / CI-CD
```

설계의 핵심은 **각 계층의 책임을 고정하고 계층 우회를 최소화하는 것**이다.

---

# 3. 온라인 요청 처리 아키텍처

## 3.1 표준 요청 흐름

```text
Client
  │
  │ request
  ▼
Controller
  │
  ▼
FWK LIB
  │ System Pre
  ▼
AOP
  │ Business Pre
  ▼
Service(Biz)
  │
  ├──────── DTO
  │
  ▼
DAO
  │ Data 변환 / Mapper Call
  ▼
O-R Mapper
  │ Query Mapping / Execute
  ▼
Database / External Resource
  │
  ▲
  │ result
  │
AOP
  │ Business Post
  ▼
FWK LIB
  │ System Post
  ▼
Controller
  │ response
  ▼
Client
```

`CONFIRMED`인 것은 Controller, 시스템 선/후처리, 업무 선/후처리, Service, DTO, DAO, O-R Mapper 구성이다. 정확한 내부 호출 순서와 예외 흐름은 실제 구현 검증이 필요하다.

---

# 4. Controller 아키텍처 설계

## 4.1 Controller 책임

Controller는 **요청 유형별 Inbound Boundary**다.

Controller에서 담당해야 하는 책임:

- 요청 유형 식별
- 표준 프레임워크 진입
- 요청/응답 경계 제공
- 업무 Service 호출을 위한 표준 통로 제공

Controller에 두지 않아야 하는 책임:

- 핵심 업무 규칙
- 임의 SQL 실행
- O-R Mapper 직접 호출
- 공통 시스템 로직의 중복 구현
- 요청 유형과 무관한 범용 처리 남용

---

## 4.2 표준 Controller 6종

| Controller | 원본 용도 | 표준 사용 시나리오 | 상태 |
|---|---|---|---|
| `NhinsController` | UI Framework(xFrame) 연계 업무 서비스 호출 | 화면 업무 요청 | CONFIRMED |
| `NhFileController` | UI 파일 Upload/Download | 파일 송수신 | CONFIRMED |
| `NhRDController` | RD(Report Designer) 연계 DB 처리 | Report/출력 | CONFIRMED |
| `NhInboundController` | EAI, 외부 JSON 전문 Inbound | 외부/시스템 Inbound | CONFIRMED |
| `NhSsoController` | NH 통합로그인 SSO | 로그인/SSO | CONFIRMED |
| `NhController` | C2C 통신, 표준전문 요청/응답 | Container 간 표준전문 | CONFIRMED |

---

## 4.3 Controller 선택 결정트리

```text
요청이 화면 업무인가?
  └─ YES → NhinsController

요청이 파일 Upload/Download인가?
  └─ YES → NhFileController

요청이 RD/Report 처리인가?
  └─ YES → NhRDController

요청이 EAI 또는 외부 JSON Inbound인가?
  └─ YES → NhInboundController

요청이 NH 통합로그인 SSO인가?
  └─ YES → NhSsoController

요청이 Container-to-Container 표준전문인가?
  └─ YES → NhController
```

---

## 4.4 Controller별 상세 설계

### 4.4.1 NhinsController

```text
UI Framework (xFrame)
       │
       ▼
NhinsController
       │
       ▼
Business Service
```

설계 규칙:

- 화면 업무 요청의 표준 진입점으로 사용한다.
- 화면 기술과 업무 Service의 결합을 최소화한다.
- Service에서 UI Framework 객체를 직접 사용하지 않는 구조를 지향한다.

### 4.4.2 NhFileController

```text
UI(xFrame)
   │ File Upload / Download
   ▼
NhFileController
   │
   ├─ DB
   └─ ObjectStorage
```

설계 규칙:

- 파일 I/O를 일반 업무 Controller와 분리한다.
- DB/ObjectStorage 연계 상세 방식은 파일 아키텍처에서 별도 정의한다.
- `FOS = ObjectStorage` 여부는 현재 자료만으로 확정하지 않는다.

### 4.4.3 NhRDController

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

설계 규칙:

- Report/RD 성격의 요청을 일반 화면 업무와 분리한다.
- 대용량 Report 처리의 별도 성능/Timeout 정책은 후속 설계한다.

### 4.4.4 NhInboundController

```text
EAI / 외부 JSON 전문
          │
          ▼
NhInboundController
          │
          ▼
Inbound Logic
```

설계 규칙:

- 시스템 연계 Inbound 요청을 사용자 화면 요청과 분리한다.
- 외부 전문 Validation/보안/추적키 규칙은 Interface 상세설계에서 확정한다.

### 4.4.5 NhSsoController

```text
NH 통합로그인
     │
     ▼
NhSsoController
     │
     ▼
SSO 처리
```

설계 규칙:

- 통합 로그인 처리를 일반 업무 Controller와 분리한다.
- Session/JWT/IdP 프로토콜은 본 장표만으로 확정하지 않는다.

### 4.4.6 NhController

```text
Container A
    │
    │ 표준전문
    ▼
NhController
    │
    │ request / response
    ▼
Container B
```

설계 규칙:

- C2C 통신은 표준전문 기반 경계로 관리한다.
- 직접 내부 구현 호출보다 표준전문 기반 서비스 계약을 우선한다.

---

# 5. FWK LIB — 시스템 공통처리 설계

## 5.1 역할

원본은 FWK LIB에 다음을 명시한다.

```text
시스템 선 처리
시스템 후 처리
```

따라서 FWK LIB는 **업무와 무관한 시스템 공통 실행경계**로 정의한다.

---

## 5.2 책임 경계

```text
┌───────────────────────────────┐
│ FWK LIB                       │
│                               │
│ System Pre Processing         │
│ System Post Processing        │
└───────────────┬───────────────┘
                │
                ▼
             AOP / Biz
```

본 자료는 시스템 선/후처리의 세부 기능을 나열하지 않는다.

따라서 다음은 `TBD`다.

- 공통 Header 처리
- Trace/GUID 생성
- 공통 Validation
- Authentication/Authorization
- Transaction 시작 여부
- Timeout 적용 위치
- 공통 Error 변환
- 공통 Logging 범위

후속 Framework Runtime 설계에서 실제 소스와 대조하여 확정한다.

---

# 6. AOP — 업무 공통처리 설계

## 6.1 역할

원본은 AOP에 다음을 명시한다.

```text
업무 선 처리
업무 후 처리
```

따라서 AOP는 **업무 공통 관심사의 실행경계**다.

---

## 6.2 시스템 공통과 업무 공통의 분리

```text
SYSTEM COMMON
FWK LIB
   │
   │ 시스템 선/후처리
   ▼
BUSINESS COMMON
AOP
   │
   │ 업무 선/후처리
   ▼
BUSINESS
Service(Biz)
```

설계 규칙:

- 시스템 전역 처리를 업무 AOP에 중복 구현하지 않는다.
- 업무 공통 처리를 개별 Service에 반복 구현하지 않는다.
- AOP 적용 대상, 순서, 예외 시 후처리 방식은 상세설계에서 확정한다.

---

# 7. Service(Biz) 아키텍처 설계

## 7.1 역할

`Service(Biz)`는 **업무 로직의 중심 계층**으로 정의한다.

```text
Controller
   ↓
FWK LIB
   ↓
AOP
   ↓
Service(Biz)
   ↓
DAO / External
```

---

## 7.2 설계 규칙

Service는 다음을 담당한다.

- 업무 처리
- 업무 판단
- 업무 처리 흐름 조합
- DTO 기반 데이터 처리
- DAO 호출

Service에 두지 않는 것을 권장하는 항목:

- UI Framework 종속 처리
- 직접 SQL Mapping
- 배포 스크립트/환경설정 처리
- 공통 인프라 기능 중복 구현

---

# 8. DTO 아키텍처 설계

## 8.1 원본 정의

원본은 DTO를 다음 형태로 표현한다.

```text
DTO
├─ getter
└─ setter
```

따라서 DTO는 계층 간 데이터 전달 계약을 담당하는 객체로 설계한다.

---

## 8.2 설계 규칙

- Controller/Service/DAO 사이의 데이터 전달 구조를 명시적으로 정의한다.
- UI Framework 전용 객체가 Service까지 침투하지 않도록 한다.
- Request/Response DTO의 상세 네이밍과 Validation은 개발표준에서 확정한다.

---

# 9. DAO / O-R Mapper 아키텍처 설계

## 9.1 DAO 책임

원본은 DAO에 다음을 명시한다.

```text
DAO
├─ Data 변환
└─ Mapper Call
```

DAO는 Service와 O-R Mapper 사이의 **데이터 접근 경계**다.

---

## 9.2 O-R Mapper 책임

원본은 O-R Mapper에 다음을 명시한다.

```text
O-R Mapper
├─ Query Mapping
└─ Query Execute
```

전체 구조:

```text
Service(Biz)
    │
    ▼
DAO
    │ Data 변환
    │ Mapper Call
    ▼
O-R Mapper
    │ Query Mapping
    │ Query Execute
    ▼
Database
```

---

## 9.3 금지 패턴

```text
PROHIBITED
Controller → O-R Mapper 직접 호출

PROHIBITED
Controller → DB 직접 접근

PROHIBITED
Service → UI Framework 직접 의존
```

실제 소스에서 계층 우회가 존재하는지는 Architecture Conformance Test로 확인한다.

---

# 10. Config 아키텍처 설계

## 10.1 원본 Config 항목

원본 장표에는 다음 항목이 표시된다.

```text
application.yml
manifest.yml
gradle build
log4j2.xml
업무 config.yml
배포 .sh
메시지 yml
동적 Config Handler
```

---

## 10.2 Config 설계 원칙

```text
Config
= 변경 가능한 실행/업무/로그/메시지/배포 정의
```

| Config 유형 | 설계 목적 | 상태 |
|---|---|---|
| `application.yml` | Application 실행설정 | CONFIRMED |
| `manifest.yml` | 배포/환경 관련 구성 | CONFIRMED |
| Gradle Build | 빌드 구성 | CONFIRMED |
| `log4j2.xml` | Logging 구성 | CONFIRMED |
| 업무 `config.yml` | 업무 설정 | CONFIRMED |
| 배포 `.sh` | 배포/기동 관련 스크립트 | CONFIRMED |
| 메시지 `yml` | 메시지 설정 | CONFIRMED |
| Dynamic Config Handler | 런타임 동적 설정 반영 경계 | CONFIRMED |

세부 파일명, Profile, Secret 분리, 환경별 Override 규칙은 후속 Config 표준에서 확정한다.

---

# 11. Dynamic Config / Master Solution 설계

## 11.1 전체 구조

원본은 중앙 Master Solution과 Dynamic Config Handler를 제시한다.

```text
관리자
  │
  ▼
Master Solution Admin UI
  │
  ▼
Master Server
  │
  ▼
Master DB
  │
  │ 동적 Config 관리
  ▼
Dynamic Config Handler
  │
  ▼
Application Runtime
```

---

## 11.2 운영 통제 원칙

Dynamic Config는 운영 영향을 직접 줄 수 있으므로 다음 항목을 후속 상세설계의 필수 통제로 둔다.

| 통제 항목 | 상태 |
|---|---|
| 변경 요청자/승인자 | TBD |
| 변경 버전 | TBD |
| 적용 시각 | TBD |
| 대상 Application/Instance | TBD |
| 변경 전/후 값 | TBD |
| Rollback | TBD |
| Audit Trail | TBD |
| 동기화 실패 처리 | TBD |

원본에는 중앙 관리구조가 확인되지만 위 세부 통제 절차는 제시되지 않는다.

---

# 12. Library 아키텍처 설계

## 12.1 원본 Library 항목

원본 장표에서 확인되는 항목은 다음과 같다.

```text
NH 공통
MIDAS
Spring Boot
File Handle
Log4j2
Utility
SSO
모니터링
암호화
coverage
```

일부 원본 해칭/세부 명칭은 실제 Artifact 목록과 대조가 필요하다.

---

## 12.2 Config와 Library 분리

```text
Config
= 바뀌는 값 / 실행 정의

Library
= 코드로 제공되는 공통 기능
```

설계 규칙:

- 변경값을 Library 코드에 하드코딩하지 않는다.
- 공통 기능을 업무 프로젝트별로 복제하지 않는다.
- Library 버전 정책과 호환성 규칙은 별도 Release Governance로 관리한다.

---

# 13. 외부 연계 아키텍처

## 13.1 원본 외부 연계 대상

```text
Database
FOS
API G/W (Cruz APIM)
Service Registry
```

---

## 13.2 표준 경계

```text
Business Framework
       │
       ├─ Data Access  → Database
       ├─ File         → FOS
       ├─ API          → Cruz APIM
       └─ Service      → Service Registry
```

본 자료는 각 연계의 상세 프로토콜·라우팅·인증·Health 정책까지는 제공하지 않는다.

---

## 13.3 API Gateway 설계 보완 항목

`TBD`:

- 어떤 호출이 반드시 Cruz APIM을 경유하는가
- 내부/외부 API 구분
- 인증/인가
- Timeout
- Retry
- Rate Limit
- Error Mapping
- Trace Header

---

## 13.4 Service Registry 설계 보완 항목

`TBD`:

- 등록 주체
- 조회 방식
- 서비스 식별자
- Health Check
- Instance 제거 정책
- Cache
- 장애 시 Fallback

---

## 13.5 File / FOS 설계 보완 항목

`TBD`:

- FOS와 ObjectStorage 관계
- 업로드/다운로드 표준
- 파일 크기 제한
- 저장위치
- 보안/암호화
- 정합성 검증
- 정리/Retention

---

# 14. 개발환경 / IDE 아키텍처

## 14.1 원본 구성

```text
개발툴 (STS IDE)
   │
   └─ NH Cloud Framework
       ├─ Controller
       ├─ AOP
       ├─ Service
       ├─ DAO
       ├─ DTO
       ├─ Mapper
       ├─ O-R
       ├─ Config
       └─ Library

Plug-In
├─ DTO Plug-In
├─ GIT Plug-In
└─ BOOT Dashboard
```

---

## 14.2 설계 목표

IDE/Plug-In은 개발자가 아키텍처 표준을 **쉽게 지키도록 만드는 Developer Experience 계층**이다.

```text
Architecture Standard
       ↓
IDE Template / Plug-In
       ↓
표준 코드 생성/구성
       ↓
Git
       ↓
CI/CD
       ↓
Runtime
```

후속 설계에서 템플릿 버전, Plug-In 배포, 업데이트 정책을 확정한다.

---

# 15. CI/CD 및 Artifact 아키텍처

## 15.1 원본 구성

```text
GitLabRunner
GitLab
NEXUS
배포
```

원본의 다른 유사 장표에는 Jenkins 표기도 존재하므로, 현재 실제 Baseline의 Runner/Jenkins 역할은 별도 확인한다.

---

## 15.2 설계 구조

```text
Developer / STS
      │
      ▼
GitLab
      │
      ▼
CI Runner / Pipeline
      │
      ├─ Build
      ├─ Test
      └─ Package
      │
      ▼
NEXUS
      │
      ▼
Deploy
      │
      ▼
Runtime
```

Pipeline 단계, 승인, Rollback, Artifact Promotion 규칙은 `TBD`다.

---

# 16. 배치 프레임워크 아키텍처

## 16.1 전체 구조

```text
                         BATCH CONTROL PLANE

┌──────────────────────────┐
│ 작업 자동화 관리         │
│                          │
│ - 배치작업등록           │
│ - 배치작업조회           │
│ - 배치모니터링           │
└─────────────┬────────────┘
              │
              ▼
┌──────────────────────────┐
│ 작업 스케줄러            │
│                          │
│ - 정기                   │
│ - 수시                   │
│ - 작업실행정보           │
└─────────────┬────────────┘
              │
              ▼
        Control-M Agent
              │
              ▼
          Batch Shell
              │
              ▼

                         BATCH EXECUTION PLANE

┌──────────────────────────────────────────────────────────────┐
│                     Spring Batch                             │
│                                                              │
│ JobLauncher                                                  │
│    │ execute()                                               │
│    ▼                                                         │
│   Job                                                        │
│    │ execute()                                               │
│    ▼                                                         │
│   Step                                                       │
│    ├─ Reader → Processor → Writer                            │
│    └─ Tasklet                                                │
└───────────────┬──────────────────────────────────────────────┘
                │
       ┌────────┴──────────┐
       ▼                   ▼
Spring Job Repository    원장 DataBase
Execution Metadata       Business Data
```

---

# 17. 배치 운영관리 / 스케줄 설계

## 17.1 작업 자동화 관리

원본에서 확인되는 기능:

```text
배치작업등록
배치작업조회
배치모니터링
...
```

이는 운영 관리 영역이다.

---

## 17.2 작업 스케줄러

원본에서 확인되는 기능:

```text
배치작업 기능
- 정기
- 수시

작업실행정보
```

설계상 운영/스케줄 관리정보와 Spring Batch 내부 실행 메타를 분리한다.

---

# 18. Control-M Agent / Batch Shell 설계

## 18.1 실행 경계

```text
Schedule
   ↓
Control-M Agent
   ↓
Batch Shell
   ↓
Spring Batch
```

이 구조는 외부 스케줄러와 애플리케이션 실행을 직접 결합하지 않는 표준 경계를 제공한다.

---

## 18.2 Batch Shell 표준에서 추가 정의할 항목

다음은 원본에서 확인되지 않는다.

```text
Shell Naming
Job Parameter 전달방식
Exit Code 표준
Environment Profile
Log Directory
Duplicate Execution 방지
Retry
Timeout
```

따라서 `GAP-BAT-001` 상세설계 대상으로 관리한다.

---

# 19. Spring Batch 실행 설계

## 19.1 Job 실행 계층

```text
JobLauncher
    ↓
Job
    ↓
Step
```

원본에는 `execute()` 흐름이 명시된다.

---

## 19.2 Step 구현 유형

### 유형 A — Reader / Processor / Writer

```text
Source
  │
  ▼
ItemReader
  │ read()
  ▼
ItemProcessor
  │ process()
  ▼
ItemWriter
  │ write()
  ▼
Target / 원장 DB
```

### 유형 B — Tasklet

```text
Step
  │
  ▼
Tasklet
```

설계 규칙:

- 반복적인 데이터 처리에는 Reader/Processor/Writer 구조를 사용할 수 있다.
- 단일 처리단위형 작업에는 Tasklet 구조를 사용할 수 있다.
- 어떤 유형을 선택할지는 배치 개발표준에서 정의한다.

---

# 20. Spring Job Repository 설계

## 20.1 원본 표시 영역

원본에 표시된 Repository 영역은 다음과 같다.

```text
Spring Job Repository
├─ BATCH_STEP_EXECUTION_CONTEXT
├─ BATCH_JOB_CONTEXT
├─ BATCH_STEP_EXECUTION
├─ BATCH_JOB_INSTANCE
└─ BATCH_JOB_EXECUTION_PARAMS
```

> 주의: 위 명칭은 원본 장표 표기를 보존한 것이다. 실제 Spring Batch 버전의 전체 Schema와 정확히 동일한지는 DB Schema 검증이 필요하다.

---

## 20.2 메타데이터 책임

```text
Job Instance
Job Parameter
Job Execution
Step Execution
Execution Context
        │
        ▼
Spring Job Repository
```

Job Repository는 업무 원장 데이터와 분리된 **배치 실행 메타데이터 저장영역**으로 설계한다.

---

# 21. 작업실행정보 ↔ Job Repository Traceability

원본에는 두 영역이 각각 존재한다.

```text
운영/스케줄 관점
작업실행정보

Spring Batch 관점
Spring Job Repository
```

후속 설계에서 다음 추적키를 확정해야 한다.

```text
Control-M Job ID
   ↔ Shell / Batch Job ID
   ↔ Spring Job Name
   ↔ Job Instance
   ↔ Job Execution
   ↔ Step Execution
```

이는 운영 장애분석을 위해 높은 우선순위를 가진다.

---

# 22. 온라인 ↔ 배치 통합 책임 모델

| 관점 | 온라인 | 배치 |
|---|---|---|
| 진입 | 표준 Controller | Scheduler / Control-M |
| 시스템 공통 | FWK LIB | Shell/공통 Batch Runtime 후보 |
| 업무 공통 | AOP | Job/Step 공통 Listener 후보(TBD) |
| 업무 실행 | Service(Biz) | Job / Step |
| 데이터 접근 | DAO / O-R Mapper | Reader/Writer / DB |
| 실행 메타 | Runtime Log 등(TBD) | Job Repository |
| 운영관리 | Master/Monitoring | 작업 자동화 관리/모니터링 |
| 배포 | GitLabRunner/GitLab/NEXUS | 동일 체계 연계 후보 |

온라인과 배치 모두 **진입 → 공통 → 업무 → 데이터 → 운영증적**의 공통 원칙으로 관리한다.

---

# 23. Framework Responsibility Matrix

| 구성요소 | 주요 책임 | 금지/제한 |
|---|---|---|
| Controller | Inbound 요청 유형별 진입 | 핵심 Biz/SQL 직접 수행 |
| FWK LIB | 시스템 공통 선/후처리 | 업무별 로직 수용 |
| AOP | 업무 공통 선/후처리 | 시스템 공통기능 중복 |
| Service(Biz) | 핵심 업무 로직 | UI 기술 종속 |
| DTO | 계층 간 데이터 계약 | DB 접근 |
| DAO | 데이터 변환/Mapper 호출 | 화면 처리 |
| O-R Mapper | Query Mapping/Execute | 업무 흐름 조합 |
| Config | 변경 가능한 실행정의 | 공통 코드 구현 |
| Library | 공통 기능 재사용 | 환경별 설정 하드코딩 |
| Master Solution | 동적 Config 중앙관리 | 무통제 직접 변경 |
| STS Plug-In | 표준 개발 지원 | Baseline 미관리 |
| GitLab/Runner/NEXUS | 형상/빌드/Artifact | 수동 우회배포 |
| Control-M | 배치 스케줄 통제 | Spring 내부 로직 직접 구현 |
| Batch Shell | 외부 Scheduler와 Job 경계 | 업무 규칙 과다 수용 |
| Spring Batch | Job/Step 실행 | 운영 스케줄 자체 소유 |
| Job Repository | 배치 실행 메타 | 업무 원장 데이터 저장 |

---

# 24. Framework Dependency Rule

## 24.1 정상 의존

```text
Controller
  ↓
FWK LIB / AOP
  ↓
Service
  ↓
DAO
  ↓
O-R Mapper
  ↓
Database
```

---

## 24.2 금지 의존 후보

```text
PROHIBITED
Controller → Mapper

PROHIBITED
Controller → Database

PROHIBITED
UI Framework → DAO

PROHIBITED
Service → UI Framework

PROHIBITED
업무코드 → Master DB 직접 변경

PROHIBITED
Control-M → Spring 내부 Step 직접 호출
```

금지 규칙은 실제 소스 구조와 프로젝트 표준 승인 후 Baseline으로 확정한다.

---

# 25. 표준 Architecture Rule

## 25.1 Online Framework Rules

```text
R-FWK-001
요청유형에 적합한 표준 Controller를 사용한다.

R-FWK-002
시스템 공통 처리는 FWK LIB,
업무 공통 처리는 AOP 책임으로 분리한다.

R-FWK-003
핵심 업무로직은 Service(Biz)에 위치한다.

R-FWK-004
데이터 접근은 DAO → O-R Mapper 구조를 기본 경계로 한다.

R-FWK-005
공통/업무/로그/메시지/배포 설정은 Config 영역으로 관리한다.

R-FWK-006
공통 기능은 Library를 통해 재사용한다.

R-FWK-007
동적 설정은 Master Solution과 Dynamic Config Handler의 관리경계를 통해 반영한다.
```

---

## 25.2 Batch Framework Rules

```text
R-BATCH-001
배치 스케줄 실행은
Control-M Agent → Batch Shell → Spring Batch 흐름으로 관리한다.

R-BATCH-002
Step은 Reader/Processor/Writer 또는 Tasklet 방식으로 구성할 수 있다.

R-BATCH-003
Job/Step 실행상태는 Spring Job Repository와 연결해 관리한다.
```

---

# 26. Naming / Traceability 설계

## 26.1 온라인 추적 모델

```text
Request Type
   ↓
Controller Type
   ↓
Application / Service
   ↓
DTO
   ↓
DAO
   ↓
Mapper / Query
   ↓
Database / External
   ↓
Runtime Log / Monitoring
```

필수 Traceability Key의 실제 명칭은 후속 표준에서 확정한다.

---

## 26.2 배치 추적 모델

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
Reader/Processor/Writer or Tasklet
   ↓
Job Repository
   ↓
Business Data
```

---

# 27. Security 설계 관점

원본에서 직접 확인되는 보안 관련 요소:

```text
NhSsoController
SSO Library
암호화 Library
API G/W
```

그러나 다음 세부사항은 미확정이다.

| 항목 | 상태 |
|---|---|
| 인증 방식 | TBD |
| 인가 방식 | TBD |
| Session/JWT | TBD |
| Secret 관리 | TBD |
| API G/W 인증정책 | TBD |
| 파일 보안 | TBD |
| Dynamic Config 권한 | TBD |

따라서 본 설계서는 보안 요소가 Framework에 포함됨을 확인하되, 상세 보안 정책은 별도 Security Architecture로 연결한다.

---

# 28. Transaction / Timeout / Error 설계 관점

원본 장표에는 Transaction, Timeout, 공통 Exception 처리 구조가 명시되지 않는다.

따라서 다음 항목은 최우선 후속 설계 대상이다.

```text
GAP-FWK-TRANSACTION
- Transaction 시작 위치
- Commit / Rollback 경계
- Propagation

GAP-FWK-TIMEOUT
- Web Request Timeout
- Transaction Timeout
- Query Timeout
- External Call Timeout

GAP-FWK-ERROR
- Biz Error
- System Error
- External Error
- Error Code
- Response Mapping
```

이 값은 실제 Framework 소스와 운영설정을 분석해 확정한다.

---

# 29. Logging / Observability 설계

원본에서 확인되는 구성:

```text
log4j2.xml
Log4j2 Library
모니터링 Library
Master Solution
Batch Monitoring
Job Repository
```

설계 목표:

```text
Request / Job
    ↓
Execution
    ↓
Log / Monitoring / Repository
    ↓
Trace
    ↓
장애 원인 식별
```

후속 설계 항목:

- 공통 Trace ID
- Service/Program ID
- Controller Type
- Batch Job ID
- 실행시간
- Error Code
- 대상 외부시스템
- Query/DB 지표
- Dynamic Config 버전

---

# 30. Availability / Recovery 설계

원본 장표는 프레임워크 구성요소를 제시하지만 HA/DR 상세동작을 직접 설명하지 않는다.

Framework 관점에서 최소 확인해야 할 항목:

```text
Stateless 여부
동적 Config 다중 Instance 동기화
Service Registry 장애
API Gateway 장애
FOS 장애
DB 장애
Batch Scheduler 장애
Job Restart
Artifact Rollback
```

이는 Technical/HA-DR Architecture와 연계하여 검증한다.

---

# 31. Performance 설계 관점

성능과 관련해 원본에서 직접 구체 수치는 제시되지 않는다.

Framework 설계 검증에서는 최소한 다음 지표를 확인한다.

| 영역 | 지표 후보 |
|---|---|
| Controller | 요청량, 응답시간, 오류율 |
| FWK LIB | 공통처리 오버헤드 |
| AOP | 업무 공통처리 오버헤드 |
| Service | 처리시간 |
| DAO/Mapper | DB 시간, Query 횟수 |
| External | API/FOS 지연 |
| Dynamic Config | 반영시간/실패율 |
| Batch | Job/Step 처리시간, 처리건수 |
| Job Repository | Metadata 증가량 |

구체 임계치는 Performance Architecture에서 별도 정의한다.

---

# 32. 개발 표준 산출물 연결

본 프레임워크 설계를 실제 개발표준으로 전환하려면 최소 다음 산출물이 필요하다.

| 산출물 | 내용 |
|---|---|
| Controller 개발가이드 | 6종 선택기준, URL/Method, Request/Response |
| FWK LIB 가이드 | 시스템 선/후처리 상세 |
| AOP 가이드 | 업무 선/후처리 Pointcut/순서 |
| Service 가이드 | 업무로직 책임 |
| DTO 가이드 | Request/Response/Validation |
| DAO/Mapper 가이드 | Data Access 규칙 |
| Config 가이드 | 파일/환경/Profile/Secret |
| Library 가이드 | 공통 Library 목록/버전 |
| Dynamic Config 가이드 | 승인/배포/롤백 |
| CI/CD 가이드 | Build/Test/Artifact/Deploy |
| Batch 가이드 | Job/Step/Shell/Parameter/Exit Code |
| Runtime 가이드 | Log/Monitoring/Evidence |

---

# 33. Architecture Conformance 자동검증 후보

다음 규칙은 `CANDIDATE`다.

```text
RULE-CONTROLLER-001
표준 Controller 유형 외 임의 Controller 생성 여부 검사

RULE-LAYER-001
Controller가 O-R Mapper를 직접 참조하지 않는지 검사

RULE-LAYER-002
Service가 UI Framework 클래스에 직접 의존하지 않는지 검사

RULE-CONFIG-001
필수 Config 파일 존재 여부 검사

RULE-LOG-001
log4j2.xml 존재 여부 검사

RULE-BATCH-001
Batch Job이 Job Repository와 연결되는지 검사

RULE-BATCH-002
Control-M Job ↔ Shell ↔ Spring Job Traceability 검사
```

---

# 34. 테스트 전략

## 34.1 Controller Test

```text
각 요청유형
  ↓
표준 Controller
  ↓
정상 Routing
  ↓
Service 진입
```

검증:

- 화면 → Nhins
- 파일 → NhFile
- Report → NhRD
- EAI/JSON → NhInbound
- SSO → NhSso
- C2C → Nh

---

## 34.2 Layer Test

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

검증:

- 계층 우회 여부
- 역방향 의존 여부
- UI 기술의 하위계층 침투 여부

---

## 34.3 Dynamic Config Test

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

현재 Rollback 상세가 원본에 없으므로 실제 운영설계 확정 후 테스트한다.

---

## 34.4 Batch Test

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

검증 시나리오:

- 정상 완료
- Step 실패
- Job 실패
- 재시작
- 중복실행
- 잘못된 Parameter
- Repository 연결 실패
- 원장 DB 실패

Restart/Retry/Skip 세부 기준은 후속 설계 확정이 필요하다.

---

# 35. Runtime Evidence 모델

프레임워크 설계가 실제 실행과 일치하는지 확인하려면 다음 증적을 수집한다.

| 대상 | Runtime Evidence 후보 |
|---|---|
| Controller | Controller Type / URI / Request Count |
| FWK LIB | System Pre/Post 실행 로그 |
| AOP | Business Pre/Post 실행 로그 |
| Service | Service 호출/처리시간 |
| DAO/Mapper | Query/DB 실행 증적 |
| External | API/FOS 호출 결과 |
| Config | 적용 Profile/Version |
| Dynamic Config | 변경 ID/버전/시각 |
| CI/CD | Commit/Build/Artifact/Deploy ID |
| Batch | Control-M Job ID / Spring Job Execution ID |
| Step | 처리건수/상태 |
| Job Repository | Instance/Execution 상태 |

---

# 36. Architecture GAP / Risk Register

| ID | 영역 | GAP / 위험 | 우선순위 | 상태 |
|---|---|---|---|---|
| GAP-FWK-001 | Controller | URL/Method/전문 규칙 미정 | 높음 | TBD |
| GAP-FWK-002 | FWK LIB | 시스템 선후처리 세부기능 미정 | 높음 | TBD |
| GAP-FWK-003 | AOP | 업무 선후처리 대상/순서 미정 | 높음 | TBD |
| GAP-FWK-004 | Transaction | Transaction 경계 미표시 | 높음 | TBD |
| GAP-FWK-005 | Error | 공통 오류처리 미표시 | 높음 | TBD |
| GAP-FWK-006 | Security | 인증/인가 상세규칙 미표시 | 높음 | TBD |
| GAP-FWK-007 | Dynamic Config | 승인/버전/Rollback 미표시 | 높음 | TBD |
| GAP-FWK-008 | Service Registry | 등록/조회/Health 규칙 미표시 | 중간 | TBD |
| GAP-FWK-009 | API Gateway | 경유범위/정책 미표시 | 중간 | TBD |
| GAP-FWK-010 | File | FOS/ObjectStorage 관계 미확정 | 중간 | TBD |
| GAP-FWK-011 | CI/CD | Pipeline/승인/Rollback 미표시 | 중간 | TBD |
| GAP-BAT-001 | Shell | Parameter/Exit Code 표준 없음 | 높음 | TBD |
| GAP-BAT-002 | Restart | Restart/Retry/Skip 미표시 | 높음 | TBD |
| GAP-BAT-003 | Job Repository | Metadata/Retention 미표시 | 중간 | TBD |
| GAP-BAT-004 | Traceability | Control-M↔Spring Job 키 미표시 | 높음 | TBD |
| GAP-BAT-005 | Monitoring | SLA/Alert 기준 미표시 | 중간 | TBD |

---

# 37. 금지 패턴

## 37.1 온라인

```text
[금지]
요청 유형과 무관하게 하나의 범용 Controller에 모든 기능 집중

[금지]
Controller 내부에 핵심 업무 규칙 구현

[금지]
Controller → DB / Mapper 직접 접근

[금지]
Service에 UI Framework 객체 종속

[금지]
공통 기능을 업무 Application마다 복사 구현

[금지]
운영 설정을 Source Code에 하드코딩

[금지]
Master Solution을 우회한 무통제 Dynamic Config 변경
```

---

## 37.2 배치

```text
[금지]
운영 스케줄러가 Spring 내부 Step을 직접 호출

[금지]
Control-M Job과 Spring Job 간 식별 불가능한 구조

[금지]
Job Repository 없이 실행 상태를 메모리/로그에만 의존

[금지]
업무 원장 데이터와 Batch Metadata의 무분별한 혼재

[금지]
Shell에 대규모 업무로직 구현
```

---

# 38. 변경관리

## 38.1 변경 대상

다음은 Architecture Governance 대상이다.

```text
Controller 유형 추가/변경
FWK LIB 처리순서 변경
AOP Pointcut/순서 변경
Service Layer 구조 변경
DAO/Mapper 경계 변경
Config 파일 체계 변경
Library 버전 변경
Dynamic Config 방식 변경
CI/CD 도구/흐름 변경
Batch Shell 표준 변경
Spring Batch Version 변경
Job Repository Schema 변경
```

---

## 38.2 변경 절차

```text
변경요청
   ↓
영향분석
   ↓
Architecture Review
   ↓
Rule / Design 갱신
   ↓
Source / Config 반영
   ↓
Test
   ↓
Runtime Evidence
   ↓
Baseline 승인
```

---

# 39. Architecture Gate

| Gate | 검증내용 | 산출물 |
|---|---|---|
| G-FWK-00 | 대상 Framework 버전/Source 기준 확정 | Source Baseline |
| G-FWK-10 | Controller/Layer 구조 확인 | Architecture Model |
| G-FWK-20 | Rule 정의 | Conformance Rules |
| G-FWK-30 | Source/Config 정적 검사 | Conformance Result |
| G-FWK-40 | Unit/Integration Test | Test Evidence |
| G-FWK-50 | Runtime 실행 검증 | Runtime Evidence |
| G-FWK-60 | Diagram↔Source↔Runtime Drift | Drift Report |
| G-FWK-70 | GAP/ADR 처리 | Decision Record |
| HG-FWK-90 | Baseline 승인 | Released Baseline |

---

# 40. 개발자 체크리스트

## 40.1 온라인 개발

- [ ] 요청 유형에 맞는 표준 Controller를 선택했는가?
- [ ] 시스템 공통과 업무 공통 처리를 분리했는가?
- [ ] 핵심 업무 로직이 Service(Biz)에 있는가?
- [ ] DAO/O-R Mapper 경계를 준수하는가?
- [ ] UI Framework 타입이 Service 이하로 내려가지 않는가?
- [ ] Config를 코드와 분리했는가?
- [ ] 공통 기능은 표준 Library를 사용하는가?
- [ ] 외부 API/File/Service Registry 연계가 표준 경계를 거치는가?
- [ ] 표준 Logging/Monitoring을 적용했는가?
- [ ] Architecture Rule 검사를 통과했는가?

---

## 40.2 배치 개발

- [ ] Control-M → Shell → Spring Batch 실행경계를 지키는가?
- [ ] Job/Step 구조가 명확한가?
- [ ] Reader/Processor/Writer 또는 Tasklet 유형이 적합한가?
- [ ] Job Repository와 실행메타를 연결했는가?
- [ ] 작업실행정보와 Job Execution을 추적할 수 있는가?
- [ ] Parameter/Exit Code가 표준화됐는가? (`TBD` 확정 후)
- [ ] 실패/재시작 시나리오가 검증됐는가?
- [ ] Batch Monitoring에서 실행상태를 식별할 수 있는가?

---

# 41. 운영 체크리스트

- [ ] 배포된 Framework/Library Version을 확인할 수 있는가?
- [ ] Config 적용 버전을 확인할 수 있는가?
- [ ] Dynamic Config 변경 이력을 추적할 수 있는가?
- [ ] Controller별 요청량/오류를 확인할 수 있는가?
- [ ] Service/DAO/External 지연을 추적할 수 있는가?
- [ ] Batch Job/Step 상태를 확인할 수 있는가?
- [ ] Control-M Job과 Spring Execution을 연결할 수 있는가?
- [ ] Job Repository 증가량과 정리정책이 존재하는가?
- [ ] Artifact와 배포 버전을 역추적할 수 있는가?

---

# 42. 후속 상세설계 우선순위

## P1 — 즉시 확정 필요

1. FWK LIB 시스템 선/후처리 상세
2. AOP 업무 선/후처리 상세
3. Transaction Boundary
4. Timeout
5. Exception/Error Response
6. Security 인증/인가
7. Dynamic Config 승인/버전/Rollback
8. Batch Shell Parameter/Exit Code
9. Batch Restart/Retry/Skip
10. Control-M ↔ Spring Batch Trace Key

## P2 — 운영 Baseline 확정 전

1. Service Registry
2. API Gateway 경유 정책
3. FOS/ObjectStorage 관계
4. CI/CD Pipeline/Promotion
5. Job Repository Retention
6. SLA/Alert
7. Framework/Library Version Governance

---

# 43. Source / Evidence Mapping

| 설계 영역 | 주요 근거 |
|---|---|
| Framework Big Picture | 표준 아키텍처 구성 장표 |
| Controller 6종 | Controller 상세 장표 |
| System Pre/Post | FWK LIB 영역 |
| Business Pre/Post | AOP 영역 |
| Service/DTO/DAO/Mapper | 온라인 Framework 영역 |
| Config/Library | 온라인 Framework 영역 |
| Master Solution | 동적 Config 관리 영역 |
| IDE/Plug-In | 개발툴 영역 |
| GitLabRunner/GitLab/NEXUS | CI/CD 영역 |
| Batch Control | 배치 표준 장표 |
| JobLauncher/Job/Step | Spring Batch 장표 |
| Reader/Processor/Writer | Step Execution #1 |
| Tasklet | Step Execution #2 |
| Job Repository | 배치 Repository 영역 |

---

# 44. FACT / DESIGN / TBD 구분

## 44.1 CONFIRMED

```text
NH Cloud Framework
6개 Controller
FWK LIB 시스템 선/후처리
AOP 업무 선/후처리
Service(Biz)
DTO getter/setter
DAO Data 변환 / Mapper Call
O-R Mapper Query Mapping / Query Execute
Config 항목
Dynamic Config Handler
Library 항목
Database / FOS / API G/W / Service Registry
STS IDE / Plug-In
GitLabRunner / GitLab / NEXUS
Master Solution
작업 자동화 관리
작업 스케줄러
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

## 44.2 DESIGN

```text
Controller = Inbound Boundary
FWK LIB = System Common Boundary
AOP = Business Common Boundary
Service = Business Logic Boundary
DAO/O-R Mapper = Data Access Boundary
Config vs Library 분리
온라인/배치 Architecture Rule
금지패턴
Traceability
Architecture Gate
Runtime Evidence 모델
```

---

## 44.3 TBD

```text
정확한 Framework 내부 호출순서
Transaction
Timeout
Exception/Error
Security 세부정책
Dynamic Config Change Control
Service Registry 상세
API Gateway 상세
FOS/ObjectStorage 관계
CI/CD Pipeline
Framework Version Policy
Batch Shell 상세표준
Restart/Retry/Skip
Job Repository 전체 Schema/Retention
Control-M ↔ Spring Job Trace Key
```

---

# 45. 최종 프레임워크 아키텍처 정의

NSIGHT 표준 프레임워크는 다음과 같이 정의한다.

> **NSIGHT 표준 프레임워크는 요청 유형별 표준 Controller를 진입점으로 하고, FWK LIB의 시스템 공통 선·후처리와 AOP의 업무 공통 선·후처리를 분리하며, Service(Biz) → DAO → O-R Mapper로 업무 및 데이터 접근 책임을 계층화하는 온라인 실행 프레임워크와, Control-M Agent → Batch Shell → Spring Batch → Job/Step → Job Repository로 구성되는 배치 실행 프레임워크를 하나의 개발·배포·운영 기준선으로 통합한 표준 Application Runtime Architecture이다.**

전체 핵심 구조는 다음과 같다.

```text
ONLINE

Client
  ↓
Controller Type
  ↓
System Common (FWK LIB)
  ↓
Business Common (AOP)
  ↓
Service(Biz)
  ↓
DTO / DAO
  ↓
O-R Mapper
  ↓
DB / FOS / API G/W / Registry

        +

Config / Library / Master Solution
STS / Plug-In / GitLab / Runner / NEXUS


BATCH

Automation / Scheduler
  ↓
Control-M Agent
  ↓
Batch Shell
  ↓
JobLauncher
  ↓
Job
  ↓
Step
  ├─ Reader → Processor → Writer
  └─ Tasklet
  ↓
Job Repository / Business DB
```

그리고 이 설계가 완성되기 위해서는 다음 Closed Loop가 필요하다.

```text
Framework Architecture
        ↓
Detailed Design
        ↓
Development Standard
        ↓
Source / Config
        ↓
Conformance Test
        ↓
Runtime Evidence
        ↓
Drift / GAP
        ↓
Architecture Baseline Update
```

따라서 본 문서를 **NSIGHT Application Framework Architecture Working Baseline**으로 사용하고, `TBD`로 표시된 항목을 실제 소스·설정·Runtime 증적으로 검증한 후 Release Baseline으로 승격한다.

---

# 46. 최종 승인 전 필수 확인 목록

- [ ] 실제 NH Cloud Framework 소스의 계층/호출구조와 장표가 일치하는가?
- [ ] 6개 Controller가 실제 구현에 모두 존재하는가?
- [ ] Controller별 URL/Method/전문 계약이 정의됐는가?
- [ ] 시스템 선/후처리 기능과 순서가 확정됐는가?
- [ ] 업무 선/후처리 AOP 기능과 순서가 확정됐는가?
- [ ] Transaction/Timeout/Error 경계가 확정됐는가?
- [ ] DAO/O-R Mapper 구현방식이 확정됐는가?
- [ ] Config/Profile/Secret 표준이 확정됐는가?
- [ ] Dynamic Config 승인/버전/Rollback이 정의됐는가?
- [ ] Library Artifact/Version 목록이 확정됐는가?
- [ ] API G/W/FOS/Service Registry 정책이 확정됐는가?
- [ ] CI/CD Pipeline과 NEXUS Promotion이 정의됐는가?
- [ ] Control-M/Shell/Spring Batch Parameter 규칙이 정의됐는가?
- [ ] Restart/Retry/Skip 정책이 정의됐는가?
- [ ] Control-M과 Job Repository 추적키가 확정됐는가?
- [ ] Runtime Evidence를 수집할 수 있는가?
- [ ] Architecture Rule을 자동 검사할 수 있는가?

---

# 47. 문서 상태

```text
Current Status : WORKING BASELINE

원본 장표 구조 : CONFIRMED
설계 규칙      : DESIGN
자동검증 규칙  : CANDIDATE
상세 실행정책  : 일부 TBD
Release Baseline : 미승인
```

본 문서는 후속 상세설계 및 소스 검증 결과에 따라 지속적으로 갱신한다.
