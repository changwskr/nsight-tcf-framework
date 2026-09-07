# NSIGHT / PDMG 아키텍처 정의서 — III. PDMG Module / Application Architecture

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 대상: PDMG Reference / AS-IS + NSIGHT TO-BE Alignment  
> 문서 상태: **Draft / Evidence-First**  
> 작성일: 2026-08-31  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_II_BigPicture_SystemBoundary.md`

---

# 0. Evidence Register

| ID | 근거 자료 | 본 장 사용 목적 | 상태 |
|---|---|---|---|
| EV-III-01 | `01장.PDMG_시스템_개요_ASCII_확장본.md` | PDMG 주요 모듈, Process/Module/Data Boundary | `[AS-IS EVIDENCE]` |
| EV-III-02 | `제3장. 레이어드 아키텍처와 컴포넌트` | Layer/Component, Spring Context, Framework vs Business | `[AS-IS EVIDENCE]` |
| EV-III-03 | `06장.패키지와_프로젝트_구조_ASCII_확장본.md` | Module/Package Map, UI/JWT 패키지, AS-IS vs TO-BE | `[AS-IS EVIDENCE]` |
| EV-III-04 | `04.패키지구조-1.md` | Package Root, ServiceId/Java/Mapper 축 | `[AS-IS EVIDENCE]` |
| EV-III-05 | `14.Spring MVC-1.md` / 시스템 선후처리 분석 | `PdmgApplication`, `scanBasePackages="nhnis"`, pdmg-fw Bean 동시 등록 | `[AS-IS EVIDENCE]` |
| EV-III-06 | `13장.TCF OFF 호환 구조.md` | TCF ON/OFF Bean/Entry 차이 | `[AS-IS EVIDENCE]` |
| EV-III-07 | `02.어플리케이션 컴포넌트 구조-1.md` | Handler→Facade→Service→DAO AS-IS와 TO-BE 후보 | `[AS-IS + PROPOSED]` |
| EV-III-08 | `02장.전체_온라인_거래_빅픽처_ASCII_확장본.md` | pdmg-ui→pdmg-service HTTP, ServiceId 기반 호출 | `[AS-IS EVIDENCE]` |
| EV-III-09 | `07장.도메인_정의와_호출_방식_ASCII_확장본.md` | MG/CO/A 업무축, Framework/Business 책임 | `[AS-IS EVIDENCE]` |
| EV-III-10 | `NSIGHT_PDMG_아키텍처_정의서_II_BigPicture_SystemBoundary.md` | PDMG가 NSIGHT Big Picture에서 차지하는 위치 | `[CURRENT BASELINE DRAFT]` |
| EV-III-11 | `NSIGHT_PDMG_아키텍처_인포그래픽_이미지화_마스터_프롬프트.md` | 5모듈 Baseline 및 III장 필수 View | `[WORKING BASELINE]` |

> **Evidence Rule**
>
> - `pdmg-ui`, `pdmg-service`, `pdmg-fw`, `pdmg-jwt`는 현재 PDMG 분석자료에서 Source/Runtime 구조가 확인된다.
> - `pdmg-om`은 NSIGHT/PDMG 기준 Baseline에는 존재하지만, 본 장에서 사용한 현재 Source Evidence에는 구현 범위가 충분히 확인되지 않았다.
> - 따라서 `pdmg-om`은 **[EXPECTED BASELINE] + [UNKNOWN CURRENT IMPLEMENTATION]** 으로 표시한다.
> - `application.rule`, `entry.facade`는 저장소 권장 TO-BE 구조로 제시된 적이 있으나 현재 일반 AS-IS 구현이라고 쓰지 않는다.

---

# 1. Figure Plan

| FIG | 제목 | Level | 목적 | 필수 |
|---|---|---:|---|---|
| FIG-III-01 | PDMG 5-Module Reference Map | L0~L1 | PDMG 전체 모듈 책임 | Y |
| FIG-III-02 | Module Change Reason Map | L1 | 모듈 분리 이유 | Y |
| FIG-III-03 | Process / Module / Data Boundary | L1~L2 | 경계 종류 구분 | Y |
| FIG-III-04 | Build Dependency vs Runtime Relation | L2 | 별도 모듈≠별도 서버 | Y |
| FIG-III-05 | Spring ApplicationContext — pdmg-service + pdmg-fw | L2 | 동일 JVM/Context 확인 | Y |
| FIG-III-06 | Framework vs Business Responsibility | L2 | 변경 이유와 의존방향 | Y |
| FIG-III-07 | pdmg-service AS-IS Package Map | L2 | 업무 Layer/Package 실제 구조 | Y |
| FIG-III-08 | Module / Base Package Map | L2 | UI/JWT/FW/Service Package 분리 | Y |
| FIG-III-09 | ServiceId → Package → Handler/Facade/Service/DAO/Mapper | L2~L3 | Naming/Traceability | Y |
| FIG-III-10 | TCF ON vs TCF OFF Application Structure | L2~L3 | 진입 Adapter 차이 | Y |
| FIG-III-11 | Module Boundary ≠ Process Boundary ≠ Spring Context Boundary | L2~L3 | 장애/운영 해석 | Y |
| FIG-III-12 | AS-IS vs TO-BE Responsibility Alignment | L3~L4 | PDMG를 NSIGHT 표준으로 승격할 범위 | Y |
| FIG-III-13 | Forbidden Dependency / Risk Map | L4 | 계층 우회·책임혼합 위험 | Y |
| FIG-III-14 | GAP / ADR / IV장 Handoff | L5 | Runtime 상세로 연결 | Y |

---

# 2. 핵심 결론

III장은 II장의 `Information Application / PDMG Reference` 박스를 Source 수준으로 확대한다.

가장 중요한 결론은 다음과 같다.

1. **PDMG의 모듈 경계와 Runtime Process 경계는 동일하지 않다.**
2. `pdmg-fw`는 별도 모듈이지만 `pdmg-service`가 HTTP로 호출하는 독립 서버가 아니다.
3. 현재 `pdmg-service`는 `@SpringBootApplication(scanBasePackages="nhnis")`로 `nhnis.mg.*`와 `nhnis.fw.*`를 같은 Spring ApplicationContext에 올릴 수 있는 구조다.
4. 현재 업무 Source Root는 `nhnis.mg.co.a.*`이며, 과거의 축 없는 `nhnis.mg.application.*` 구조는 현재 AS-IS 기준에서 제거된 것으로 본다.
5. 현재 주요 업무 호출 방향은 **Handler → Facade → Service → DAO/Mapper**이다.
6. 현재 일반 `Rule` 계층은 AS-IS Source에서 확인되지 않는다.
7. TCF ON과 OFF는 **다른 Entry Adapter를 사용**한다. ON에서는 공통 Controller/Dispatcher/Handler를 사용하고, OFF에서는 업무별 Spring MVC Controller로 직접 진입한다.
8. OFF AS-IS 일부 Controller는 Facade가 아니라 Service를 직접 호출하여 ON과 업무 경계/Transaction 경계가 달라질 수 있다.
9. `pdmg-ui`, `pdmg-service`, `pdmg-jwt` 사이에는 HTTP Process 경계가 존재할 수 있고, `pdmg-service`와 `pdmg-fw` 사이에는 Module/JVM 내부 경계가 존재한다.
10. `pdmg-om`은 본 장에서 기능을 일반론으로 채우지 않고 **Source Evidence 확보 전 [UNKNOWN]**으로 유지한다.

이 장의 목적은 PDMG를 “Handler/Service/DAO가 있는 Spring 프로그램” 수준으로 설명하는 것이 아니라:

```text
Repository / Module
        ↓
Build Dependency
        ↓
Process / JVM Boundary
        ↓
Spring ApplicationContext
        ↓
Package / Layer
        ↓
Component
        ↓
ServiceId / Mapper / SQL
        ↓
Runtime Entry Mode
        ↓
AS-IS / TO-BE GAP
```

까지 연결하는 것이다.

---

# 3. 목적 / 범위 / 전제

## 3.1 목적

이 장은 다음 질문을 해결한다.

1. PDMG 주요 모듈은 어떤 변경 이유로 분리되어 있는가?
2. 모듈이 나뉘었다고 해서 서버 프로세스도 분리되는가?
3. `pdmg-fw`와 `pdmg-service`는 Runtime에서 어떤 관계인가?
4. `pdmg-service`의 실제 업무 패키지는 어디에 있는가?
5. Handler/Facade/Service/DAO는 Layer인가 Component인가?
6. `ServiceId`는 Package/Handler/Mapper와 어떻게 연결되는가?
7. TCF ON/OFF는 Application Architecture를 어떻게 다르게 만든다?
8. 현재 AS-IS의 어떤 구조를 NSIGHT 표준으로 승격할 수 있고, 무엇은 GAP인가?
9. IV장에서 Runtime Sequence를 분석할 때 어떤 경계를 기준으로 보아야 하는가?

## 3.2 포함 범위

```text
pdmg-ui
pdmg-jwt
pdmg-fw
pdmg-service
pdmg-om (Baseline/Unknown)

Module Responsibility
Process Boundary
Build Dependency
JVM / Spring ApplicationContext
Layer / Component
Package Structure
ServiceId / Naming Projection
TCF ON / OFF
AS-IS / TO-BE Alignment
Dependency / Forbidden Pattern
GAP / ADR
```

## 3.3 제외 범위

```text
DefaultFilter 상세             → IV
Dispatcher Runtime Sequence     → IV
Worker Thread / Timeout         → V
TransactionTemplate             → V
Standard Message / Context      → VI
JWT 발급/검증 상세              → VII
Server/JVM/WAR/Capacity         → VIII
OM Dashboard/Metric             → IX
전체 ServiceId Closed Loop      → X
```

---

# 4. FIG-III-01 — PDMG 5-Module Reference Map

```text
┌──────────────────────────── PDMG Reference ──────────────────────────────┐
│                                                                         │
│  [pdmg-ui]                                                              │
│  화면 / Browser Client                                                   │
│  ServiceId 기반 요청                                                     │
│       │ HTTP                                                             │
│       ├────────────────────────────────────┐                              │
│       ▼                                    ▼                              │
│  [pdmg-service]                       [pdmg-jwt]                          │
│  업무 Application                     인증 / JWT / Token                 │
│  Handler / Facade                     MG/JW/A 업무축                      │
│  Service / DAO                        별도 Process 경계 가능              │
│       │                                                                 │
│       │ same JVM / classpath                                             │
│       ▼                                                                 │
│  [pdmg-fw]                                                              │
│  Filter / Context / TCF                                                 │
│  Timeout / Error / Logging                                              │
│  공통 Web / Runtime                                                     │
│                                                                         │
│  [pdmg-om]                                                              │
│  운영관리 / 상태 / 관리 기능                                            │
│  [EXPECTED BASELINE]                                                    │
│  [CURRENT SOURCE IMPLEMENTATION = UNKNOWN]                              │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

## 4.1 핵심 해설

- `pdmg-ui`, `pdmg-service`, `pdmg-jwt`는 **사용자가 실제로 접속하거나 HTTP 경계가 존재하는 모듈/프로세스**로 분석자료에 나타난다.
- `pdmg-fw`는 독립 포트 서버가 아니라 `pdmg-service`/`pdmg-jwt`의 classpath에 포함되는 공통 Framework 성격이다.
- `pdmg-om`은 현재 정의서 Baseline 상 존재하지만, 실제 현재 Source Snapshot의 범위·패키지·프로세스는 본 장에서 확정하지 않는다.

## 4.2 Module Status

| 모듈 | Current Evidence | 기본 책임 | 판정 |
|---|---|---|---|
| `pdmg-ui` | 패키지/Static/HTTP 경로 확인 | 화면, 요청 작성, ServiceId 호출 | `[AS-IS]` |
| `pdmg-service` | Boot/업무 패키지/Controller/Facade/Service/DAO 확인 | 업무 거래 실행 | `[AS-IS]` |
| `pdmg-fw` | `nhnis.fw.*`, Filter/TCF/Timeout/Error 등 확인 | 공통 실행 Framework | `[AS-IS]` |
| `pdmg-jwt` | `nhnis.mg.jw.a.*` 패키지 축 확인 | 인증/JWT/Token | `[AS-IS]` |
| `pdmg-om` | 본 장 Source 상세 미확보 | 운영관리 후보 | `[UNKNOWN]` |

---

# 5. FIG-III-02 — Module Change Reason Map

모듈은 기능 카탈로그가 아니라 **변경 이유**로 구분한다.

```text
사용자 경험 / 화면 변화
        │
        ▼
     pdmg-ui
────────────────────────────────────

업무 Program / DTO / SQL 변화
        │
        ▼
   pdmg-service
────────────────────────────────────

공통 거래 실행정책 변화
Filter / TCF / Timeout / Error
        │
        ▼
     pdmg-fw
────────────────────────────────────

인증 / Token / Key 정책 변화
        │
        ▼
     pdmg-jwt
────────────────────────────────────

운영 / 상태 / 관리 기능 변화
        │
        ▼
     pdmg-om
     [현재 구현 Evidence TBD]
```

## 5.1 변경 이유와 책임

| 변경 이슈 | 기본 변경 대상 | 이유 |
|---|---|---|
| 화면 배치/버튼/조회 UX | `pdmg-ui` | 사용자 접점 |
| 신규 ServiceId 업무 로직 | `pdmg-service` | 업무 Use Case |
| 전체 거래 Timeout 정책 | `pdmg-fw` | 공통 실행 통제 |
| 표준 Error 조립 | `pdmg-fw` | 공통 계약 |
| 로그인/Access Token | `pdmg-jwt` | 인증·토큰 정책 |
| 업무 SQL | `pdmg-service` | 데이터 접근 |
| 거래 상태 Dashboard | `pdmg-om` 후보 | Source 검증 전 확정 금지 |

## 5.2 금지되는 복제

```text
pdmg-fw 공통 Filter를
pdmg-service에 복사
          X

pdmg-service 업무 Rule을
pdmg-fw에 구현
          X

JWT Key/Token Logic을
업무 Service에 복제
          X
```

---

# 6. FIG-III-03 — Process / Module / Data Boundary

PDMG를 이해할 때 가장 먼저 세 경계를 분리한다.

```text
[1. Process Boundary]

Browser
   │ HTTP
   ├──────────────► pdmg-ui
   │
   ├──────────────► pdmg-service
   │
   └──────────────► pdmg-jwt

장애:
Network / Port / CORS / Authorization / URL


[2. Module / JVM Boundary]

pdmg-service Runtime
   │
   ├─ pdmg-service Classes / Beans
   └─ pdmg-fw Classes / Beans

장애:
Classpath / Bean / AutoConfig / Scan / Version


[3. Data Boundary]

pdmg-service
   │ MyBatis / JDBC
   ▼
RDW / DB

장애:
DataSource / Transaction / SQL / Lock
```

## 6.1 핵심 원칙

```text
HTTP 실패
≠
Bean 실패
≠
SQL 실패
```

같은 “거래 실패”라도 어느 경계인지에 따라 확인대상이 달라진다.

## 6.2 운영 해석

| 증상 | 먼저 볼 경계 |
|---|---|
| Browser CORS 오류 | Process/HTTP |
| `/online` 404 | MVC/Bean/TCF mode |
| Application 기동 실패 | Module/Spring Context |
| Handler Bean 중복 | Component Scan/Registry |
| SQL은 실행됐는데 Commit 안 됨 | Data/Transaction |
| JWT 발급 서버 연결 실패 | Process/HTTP |
| Filter Bean 없음 | Module/Context |

---

# 7. FIG-III-04 — Build Dependency vs Runtime Relation

## 7.1 Build 관점

현재 분석자료는 `pdmg-service`와 `pdmg-jwt`가 공통 `pdmg-fw`를 사용하는 모듈 구조로 설명한다.

```text
[Build / Repository]

pdmg-ui
    │
    │ HTTP Client / Static
    ▼
pdmg-service  ───── dependency / classpath ─────► pdmg-fw

pdmg-jwt      ───── dependency / classpath ─────► pdmg-fw

pdmg-om
    └─ [Dependency / Build 구조 UNKNOWN]
```

> Gradle 선언문 자체의 정확한 `implementation project(...)` 형태는 본 장 Evidence에서 재확인하지 않았으므로 문자열을 창작하지 않는다.

## 7.2 Runtime 관점

```text
Browser
   │
   ├── HTTP ──► pdmg-service Process
   │               │
   │               ├─ pdmg-service Bean
   │               └─ pdmg-fw Bean
   │
   └── HTTP ──► pdmg-jwt Process
                   │
                   ├─ JWT Bean
                   └─ pdmg-fw 공통 Bean 일부/의존 가능
```

## 7.3 핵심 결론

```text
Module Boundary
    ≠
Remote Call Boundary
```

`pdmg-service → pdmg-fw`를 다음처럼 그리면 잘못이다.

```text
pdmg-service
   │ HTTP
   ▼
pdmg-fw Server

X
```

현재 Source 해석은 다음이 맞다.

```text
┌──────────── pdmg-service JVM ────────────┐
│                                          │
│  Business Bean     Framework Bean        │
│                                          │
└──────────────────────────────────────────┘
```

---

# 8. FIG-III-05 — Runtime Spring ApplicationContext

## 8.1 Boot 시작점 `[AS-IS]`

`PdmgApplication` 분석자료:

```java
@SpringBootApplication(scanBasePackages = "nhnis")
@ConfigurationPropertiesScan("nhnis")
public class PdmgApplication {
}
```

이 설정 때문에 `pdmg-service` 실행 시 다음 패키지가 같은 Scan 영역에 들어간다.

```text
nhnis.mg.*
nhnis.fw.*
```

## 8.2 ApplicationContext 그림

```text
PdmgApplication
      │
      │ scanBasePackages = "nhnis"
      ▼
┌──────────────────── Spring ApplicationContext ─────────────────────┐
│                                                                    │
│ [pdmg-fw]                       [pdmg-service]                      │
│                                                                    │
│ DefaultFilter                   mgcoa...Handler                    │
│ Interceptor                     mgcoa...Facade                     │
│ OnlineTransactionController     mgcoa...Service                    │
│ TcfFacade                       mgcoa...DAO                        │
│ TransactionDispatcher           BizPrePostAspect                   │
│ OnlineTimeoutExecutor           Business Config                    │
│ ServiceContext                                                     │
│ Exception / Advice                                                 │
│                                                                    │
└───────────────────────────────┬────────────────────────────────────┘
                                │
                                ▼
                         MyBatis / JDBC / DB
```

## 8.3 Architecture 의미

- Framework와 Business는 **책임은 분리**되어 있지만 Runtime Bean은 같은 ApplicationContext에 공존할 수 있다.
- 따라서 `pdmg-fw` 장애가 `pdmg-service`와 무관한 별도 서버 장애로 나타나는 것이 아니라:
  - Bean Creation
  - Filter Registration
  - Auto Configuration
  - Conditional Bean
  - AOP
  - Context
  - Classpath
  형태로 나타날 수 있다.

## 8.4 `[RISK]`

`scanBasePackages="nhnis"`는 범위가 넓다.

```text
새 업무축 추가
   ↓
동일 Bean Name / 동일 Config Name
   ↓
충돌 가능

AOP Pointcut
   ↓
새 패키지 미포함
   ↓
공통 처리 누락 가능
```

따라서 새 업무축을 추가할 때 **패키지 추가만 하고 끝내면 안 된다.**

---

# 9. FIG-III-06 — Framework vs Business Responsibility

## 9.1 큰 경계

```text
┌────────────────── Framework Domain ──────────────────┐
│                                                       │
│ Filter                                                │
│ Context                                               │
│ TCF                                                   │
│ Timeout                                               │
│ Transaction Control                                   │
│ Error                                                 │
│ Logging                                               │
│ Common Message                                        │
│                                                       │
│ "어떻게 안전하고 일관되게 실행할까?"                 │
└────────────────────────┬──────────────────────────────┘
                         │
                         ▼
┌────────────────── Business Domain ───────────────────┐
│                                                       │
│ Handler                                               │
│ Facade                                                │
│ Service                                               │
│ DAO / Mapper                                          │
│ Business DTO                                          │
│ Business Rule [AS-IS 일반계층 없음]                  │
│                                                       │
│ "무슨 업무를 수행할까?"                              │
└───────────────────────────────────────────────────────┘
```

## 9.2 책임 Matrix

| 요소 | Framework 책임 | Business 책임 |
|---|---:|---:|
| Header/GUID/Context | ● | 사용 |
| ServiceId Routing Infrastructure | ● | Handler 등록 |
| Timeout | ● | SLA 요구 입력 |
| 최외곽 TX 제어(TCF+Timeout) | ● | 참여 |
| Use Case |  | ● |
| 업무 판단 |  | ● |
| SQL |  | ● |
| 표준 Error Envelope | ● | Error 발생/업무코드 |
| 업무 로그 내용 | 공통 뼈대 | 업무정보 |
| JWT 검증 기반 | 공통 | 권한 사용 |

## 9.3 Framework가 소유하면 안 되는 것

```text
고객등급 계산
캠페인 자격 판단
업무 SQL
특정 화면 정책
업무별 할인/수수료/점수 계산
```

## 9.4 Business가 소유하면 안 되는 것

```text
ThreadPool 직접 관리
공통 Timeout Executor 생성
전체 Error JSON 직접 조립
공통 Context 자체 구현
JWT 검증 정책 복제
공통 Filter 복제
```

---

# 10. Layer와 Component를 구분한다

PDMG에서 `Handler`, `Facade`, `Service`, `DAO`를 모두 독립 Layer라고 부르면 설계책임을 오해할 수 있다.

## 10.1 Layer

```text
Entry
Application
Persistence
Framework Infrastructure
```

## 10.2 Component

```text
Handler
Controller
Facade
Service
DAO
Aspect
Mapper XML
```

## 10.3 구조

```text
┌──────────────── Entry Layer ─────────────────┐
│ Handler                                      │
│ Aspect                                       │
└───────────────────┬──────────────────────────┘
                    ▼
┌────────────── Application Layer ─────────────┐
│ Controller [TCF OFF]                         │
│ Facade                                       │
│ Service                                      │
└───────────────────┬──────────────────────────┘
                    ▼
┌────────────── Persistence Layer ─────────────┐
│ DAO                                          │
│ Mapper XML                                   │
└───────────────────┬──────────────────────────┘
                    ▼
                   DB
```

Layer는 **왜 바뀌는가/누구를 의존하는가**의 경계이고, Component는 **구체적인 실행 역할**이다.

---

# 11. FIG-III-07 — pdmg-service AS-IS Package Map

## 11.1 현재 업무 Root `[AS-IS]`

```text
nhnis.mg
├─ PdmgApplication
├─ ServletInitializer
│
└─ co
   └─ a
      └─ 업무 코드
```

현재 기준:

```text
nhnis.mg
= Boot / Application Root

nhnis.mg.co.a
= 현재 업무 Root
```

## 11.2 전체 Source 구조

```text
pdmg-service
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ nhnis
   │  │     └─ mg
   │  │        ├─ PdmgApplication.java
   │  │        ├─ ServletInitializer.java
   │  │        │
   │  │        └─ co
   │  │           └─ a
   │  │              ├─ entry
   │  │              │  ├─ handler
   │  │              │  └─ aspect
   │  │              │
   │  │              ├─ application
   │  │              │  ├─ controller
   │  │              │  ├─ facade
   │  │              │  └─ service
   │  │              │
   │  │              ├─ dto
   │  │              ├─ persistence
   │  │              │  └─ dao
   │  │              ├─ client
   │  │              ├─ config
   │  │              └─ support
   │  │
   │  └─ resources
   │     ├─ application.yml
   │     ├─ exceptionCode.yml
   │     ├─ rdw.mg.co.a/
   │     └─ db/h2/
   │
   └─ test
      └─ java/nhnis/mg/co/a/...
```

## 11.3 현재 존재하지 않는 것으로 봐야 하는 구조

```text
nhnis.mg.co.a.application.rule
→ 일반 AS-IS 계층으로 확인되지 않음

nhnis.mg.co.a.persistence.file
→ 실제 Java 구현 패키지로 확인되지 않음

과거:
nhnis.mg.application.*
nhnis.mg.entry.*
→ 현재 AS-IS 업무축에서 제거됨
```

## 11.4 Package 경로 규칙

```text
nhnis.mg.[업무코드].[세부업무코드].[책임]
```

예:

```text
nhnis.mg.co.a.entry.handler
nhnis.mg.co.a.application.service
nhnis.mg.co.a.persistence.dao
```

---

# 12. FIG-III-08 — Module / Base Package Map

```text
pdmg-fw
  ├─ nhnis.fw.commons.*
  ├─ nhnis.fw.tcf.*
  ├─ nhnis.fw.exception.*
  └─ com.ims.superspring.*

pdmg-service
  ├─ nhnis.mg
  └─ nhnis.mg.co.a.*

pdmg-ui
  └─ nhnis.mg.ui.*

pdmg-jwt
  └─ nhnis.mg.jw.a.*

pdmg-om
  └─ [CURRENT PACKAGE UNKNOWN]
```

## 12.1 `pdmg-fw` Package 책임

| Root | 역할 |
|---|---|
| `nhnis.fw.commons.*` | Filter, Context, ImageLog, 전문, 공통 유틸 |
| `nhnis.fw.tcf.*` | Controller, Dispatcher, Handler SPI, Timeout |
| `nhnis.fw.exception.*` | 예외 및 Error Response |
| `com.ims.superspring.*` | 레거시 전문 DTO 호환 |

## 12.2 `pdmg-ui`

```text
nhnis.mg.ui
├─ PdmgUiApplication
├─ entry/web
├─ application/service
├─ client
├─ config
└─ support

resources/static
├─ _shared
├─ mgcoa5530
├─ mgcoa8888
├─ mgcoa9000
├─ mgcoa9001
├─ mgcoa9100
└─ mgcoa9999 ...
```

화면의 Static Folder와 서버 Java Package는 같은 기술계층이 아니다.

## 12.3 `pdmg-jwt`

```text
nhnis.mg.jw.a
├─ entry
│  ├─ handler
│  └─ web
├─ application
│  ├─ facade
│  └─ service
├─ dto
├─ persistence
│  └─ dao
├─ config
└─ support
```

JWT는 `MG/JW/A`라는 별도 업무축을 가진다. 이를 `nhnis.mg.co.a` 아래에 넣지 않는다.

---

# 13. ServiceId와 업무축

## 13.1 현재 업무축

```text
MG / CO / A
```

투영:

```text
업무분류
MG / CO / A
    │
    ├────────────┬─────────────┐
    ▼            ▼             ▼
Java           Mapper       Service Prefix
nhnis.mg.co.a  rdw.mg.co.a  mgcoa
```

## 13.2 ServiceId 예

```text
mgcoa9000S0
│ │ │
│ │ └─ a
│ └── co
└──── mg
```

ServiceId와 Package/Mapper가 다른 업무축을 가리키면 Traceability가 깨진다.

---

# 14. FIG-III-09 — ServiceId → Component → Mapper

```text
Service ID
mgcoa9000S0
     │
     ├─────────────────────────────┐
     │                             │
     ▼                             ▼
업무 Root                       Mapper Root
nhnis.mg.co.a                  rdw.mg.co.a/
     │
     ▼
Handler
mgcoa9000Handler
     │
     ▼
Facade
mgcoa9000Facade
     │
     ▼
Service
mgcoa9000Service
     │
     ▼
DAO
mgcoa9000DAO
     │
     ▼
Mapper XML
mgcoa9000-ORA.xml
     │
     ▼
SQL / Table
```

> 실제 모든 ServiceId가 정확히 동일 클래스 stem으로 1:1 구성되는지는 X장의 자동 인덱스/Source Scan으로 검증한다. III장에서는 현재 대표 규칙을 Architecture Map으로 사용한다.

## 14.1 UI와 연결

```text
static/mgcoa9000/
      │
      ▼
Service ID
mgcoa9000S0
      │
      ▼
pdmg-service
```

화면 Folder → ServiceId → Java → Mapper가 하나의 업무축으로 추적 가능해야 한다.

---

# 15. 현재 Application Component 책임

## 15.1 Handler `[AS-IS]`

```text
ServiceId
   ↓
Handler Registry / Dispatcher
   ↓
TransactionHandler
   ↓
Business Facade
```

핵심 책임:

- ServiceId 등록/수신
- 거래 분기
- 업무 Facade 연결

정상 책임이 아닌 것:

- SQL
- 핵심 Business Rule
- DB 직접 접근
- 자체 Transaction 전략

## 15.2 Facade `[AS-IS]`

역할:

- 유스케이스 경계
- 입력 변환/조정
- 여러 Service 호출 조정
- 기본 업무 Transaction 선언 위치

단, **TCF ON + Timeout ON의 최외곽 Transaction은 V장에서 설명할 Worker `TransactionTemplate`이 된다.**

따라서:

```text
Facade = 항상 최외곽 TX
```

라고 단정하지 않는다.

## 15.3 Service `[AS-IS]`

- 업무 절차
- 업무 판단
- DAO 호출
- 업무 선후 Aspect의 Join Point

현재 `BizPrePostAspect` Pointcut은 `nhnis.mg.co.a.application.service..*`를 기준으로 분석된다.

## 15.4 DAO / Mapper `[AS-IS]`

DAO는 현재 MyBatis Mapper Interface 역할을 수행한다.

```text
Service
   ↓
DAO Interface
   ↓
Mapper XML
   ↓
SQL
```

`DAO`와 `Mapper`를 동일 클래스라고 혼동하지 않는다.

---

# 16. Rule 계층 — AS-IS와 TO-BE를 분리한다

## 16.1 현재

```text
AS-IS

Handler
  ↓
Facade
  ↓
Service
  ↓
DAO
```

현재 일반 `application.rule` 계층은 확인되지 않는다.

## 16.2 저장소 권장 TO-BE 후보

```text
TO-BE Candidate

entry.handler
  ↓
entry.facade
  ↓
application.service
  ↓
application.rule
  ↓
persistence.dao / mapper
```

## 16.3 핵심 판단

Rule 계층은 폴더를 추가하는 것이 목적이 아니다.

다음 질문이 먼저다.

```text
업무 판단이 Service에 과밀한가?
업무 Rule이 여러 Use Case에서 재사용되는가?
Rule Test를 Service Flow와 분리할 가치가 있는가?
도메인 규칙의 변경 이유가 Workflow와 다른가?
```

답이 없다면 Rule Package를 형식적으로 추가하지 않는다.

---

# 17. FIG-III-10 — TCF ON vs TCF OFF

## 17.1 TCF ON `[AS-IS]`

```text
HTTP
  ↓
Filter / Interceptor
  ↓
OnlineTransactionController
  ↓
TcfFacade
  ↓
TimeoutExecutor
  ↓
TransactionDispatcher
  ↓
Handler
  ↓
Business Facade
  ↓
Service
  ↓
DAO
```

## 17.2 TCF OFF `[AS-IS]`

```text
HTTP
  ↓
Filter / Interceptor
  ↓
Business Controller
  │
  ├─ 대부분 → Service → DAO
  │
  └─ 일부   → Facade → Service → DAO
  ↓
Response Advice / afterCompletion
```

## 17.3 ON/OFF 공통 목표 후보

```text
ON
OnlineController → Handler ─┐
                            ├─► Business Facade
OFF                         │       ↓
Business Controller ────────┘     Service
                                  ↓
                                  DAO
```

## 17.4 ON/OFF의 본질

```text
TCF OFF
≠
Framework 전체 OFF

TCF OFF
=
공통 TCF Entry / Dispatcher / Handler Registry를 우회
+
업무별 Spring MVC Controller 사용
```

Filter, Interceptor, Response Advice, DataSource, TransactionManager는 별도 조건에 따라 남을 수 있다.

## 17.5 OFF에서 비활성화되는 것으로 분석된 구성

```text
OnlineTransactionController
TcfFacade
TransactionDispatcher
업무 TransactionHandler
STF / ETF
TCF 조건 GlobalExceptionHandler
```

단, STF/ETF의 **AS-IS Runtime 실연결 여부**는 별도 문제다. Bean 존재/비존재와 실제 호출 여부를 구분한다.

---

# 18. TCF OFF의 Architecture GAP

현행 OFF Controller가 Facade 경계를 일관되게 공유하지 않는다.

예시 분석:

```text
mgcoa5530  Controller → Service
mgcoa8888  Controller → Service
mgcoa9000  Controller → Service
mgcoa9001  Controller → Service
mgcoa9999  Controller → Service
mgcoa9100  Controller → Facade
```

이 구조의 문제는 **ON과 OFF가 같은 Business Core를 공유한다는 보장이 약해진다**는 점이다.

## 18.1 Risk

```text
TCF ON
Handler → Facade → Service

TCF OFF
Controller → Service

          ↓

Facade 책임 우회
DTO 변환 차이
Transaction 차이
Use Case 조정 차이
ON/OFF 결과 Drift
```

## 18.2 TO-BE 후보

```text
Entry Adapter만 다르게
업무 Core는 동일하게

ON  → Handler     ─┐
                    ├→ Facade → Service → DAO
OFF → Controller  ─┘
```

---

# 19. FIG-III-11 — Module Boundary ≠ Process Boundary ≠ Spring Context Boundary

```text
┌───────────────────────────────┐
│ Repository Module Boundary    │
│                               │
│ pdmg-service | pdmg-fw        │
└──────────────┬────────────────┘
               │ Build
               ▼
┌───────────────────────────────┐
│ Runtime Process / JVM         │
│                               │
│ pdmg-service Application      │
│   + pdmg-fw classes           │
└──────────────┬────────────────┘
               │ Spring Boot
               ▼
┌───────────────────────────────┐
│ Spring ApplicationContext     │
│                               │
│ FW Beans + Business Beans     │
└───────────────────────────────┘
```

반대로:

```text
pdmg-ui Process
      │ HTTP
      ▼
pdmg-service Process

pdmg-jwt Process
      ▲
      │ HTTP
Browser / UI
```

## 19.1 세 경계 비교

| 경계 | 무엇을 분리 | 대표 연결 | 장애 유형 |
|---|---|---|---|
| Module | Source/Build | Dependency | Compile/JAR/Version |
| Process | Network/Runtime | HTTP | Port/CORS/Network |
| Spring Context | Bean/Configuration | Bean Injection/AOP | Bean/Conditional/Scan |
| Data | Persistence | JDBC/MyBatis | SQL/TX/Lock |

---

# 20. pdmg-ui Application Architecture

## 20.1 역할

```text
User
  ↓
Screen
  ↓
Service ID 선택
  ↓
Request Envelope
  ↓
HTTP
  ↓
pdmg-service
```

## 20.2 직접 호출과 Relay `[AS-IS]`

```text
[일반 화면 주 경로]

Browser
   │ CORS / HTTP
   ▼
pdmg-service


[호환 Relay]

Browser
   ▼
pdmg-ui
   │ /api/relay/{serviceId}
   ▼
TransactionRelayService
   ▼
pdmg-service
```

Relay를 사용한다고 업무 실행 구조가 바뀌는 것은 아니다.

다만 장애 관찰 지점은 늘어난다.

```text
Browser → UI Server → Service
```

## 20.3 UI와 Business Package를 동일시하지 않는다

```text
static/mgcoa9000
≠
nhnis.mg.co.a.application.service
```

UI는 Server Business Layer를 복제하지 않고 **ServiceId 계약을 통해 연결**한다.

---

# 21. pdmg-jwt Application Architecture

## 21.1 현재 확인된 구조

```text
nhnis.mg.jw.a
├─ entry.handler
├─ entry.web
├─ application.facade
├─ application.service
├─ dto
├─ persistence.dao
├─ config
└─ support
```

## 21.2 책임

```text
Authentication
Token Issue
Token Refresh
Token Persistence
Security Policy
```

상세:

- RS256
- Refresh Token
- SSO/HMAC
- Key/JWKS
- Session/Revocation

등은 VII장에서 Source Evidence를 재검증해 확정한다.

## 21.3 금지

```text
JWT 업무축을
nhnis.mg.co.a 업무축 안으로 이동
                 X

Marketing Service가
Private Key / Refresh Token Logic 직접 보유
                 X
```

---

# 22. pdmg-fw Application Architecture

## 22.1 책임

현재 분석자료에 반복 확인되는 핵심:

```text
HTTP Entry Common
- DefaultFilter

MVC Common
- ServicePreventionInterceptor
- Response Advice / Resolver

Context
- ServiceContext

TCF
- OnlineTransactionController
- TcfFacade
- TransactionDispatcher

Execution Control
- OnlineTimeoutExecutor

Exception / Error
Logging / ImageLog
```

## 22.2 Framework는 업무보다 바깥쪽이다

```text
┌──────────────────── pdmg-fw ────────────────────┐
│                                                 │
│ Request                                         │
│   ↓                                             │
│ Filter → Context → Interceptor                  │
│   ↓                                             │
│ TCF / Timeout / Dispatcher                      │
│   ↓                                             │
│ ┌────────── Business Application ────────────┐  │
│ │ Handler → Facade → Service → DAO          │  │
│ └────────────────────────────────────────────┘  │
│   ↓                                             │
│ Error / Response / Log                          │
│                                                 │
└─────────────────────────────────────────────────┘
```

Framework는 개별 업무를 수행하지 않고 **업무가 실행되는 조건과 계약을 제공**한다.

---

# 23. pdmg-service Application Architecture

## 23.1 현재 핵심 경로

```text
Handler
   ↓
Facade
   ↓
Service
   ↓
DAO
   ↓
Mapper XML
   ↓
DB
```

## 23.2 Side Package

모든 패키지가 수직 Layer는 아니다.

```text
DTO
  = 경계 데이터 계약

Config
  = Bean/DataSource/MyBatis 조립

Client
  = Outbound Integration 경계

Support
  = 제한적 보조 책임

Aspect
  = 횡단관심
```

## 23.3 잘못된 해석

```text
Handler → DTO → Config → Client → Service → Support → DAO
```

처럼 디렉터리 목록을 실행 순서로 보면 안 된다.

---

# 24. pdmg-om — Evidence Gap

PDMG Baseline에는 `pdmg-om`이 포함되어 있다.

하지만 현재 본 장이 참조한 Source 분석자료에서 다음을 확정할 충분한 Evidence가 없다.

```text
Boot Class
Base Package
HTTP Port
Repository Dependency
DB
Dashboard
Monitoring Scope
Control API
Deployment Unit
```

따라서 III장에서는 다음처럼만 표시한다.

```text
pdmg-om
  │
  ├─ Expected Role:
  │   운영관리 / 상태 / 관리 기능
  │
  └─ Current Implementation:
      [UNKNOWN]
```

## 24.1 금지

```text
Thread Dashboard
JVM Dashboard
DB Pool Dashboard
배포관리
사용자/권한
로그관리
```

같은 일반적인 OM 기능을 Source 확인 없이 AS-IS로 그리지 않는다.

IX장에서 Source Evidence를 확보한 뒤 구체화한다.

---

# 25. FIG-III-12 — AS-IS vs TO-BE Responsibility Alignment

## 25.1 AS-IS

```text
nhnis.mg.co.a
├─ entry
│  ├─ handler
│  └─ aspect
├─ application
│  ├─ controller
│  ├─ facade
│  └─ service
├─ dto
├─ persistence
│  └─ dao
├─ client
├─ config
└─ support
```

## 25.2 TO-BE 후보

```text
nhnis.mg.[업무].[세부]
├─ entry
│  ├─ web
│  ├─ handler
│  └─ facade
├─ application
│  ├─ service
│  └─ rule
├─ dto / contract
├─ persistence
│  ├─ dao
│  └─ mapper
├─ client
├─ config
└─ support
```

## 25.3 핵심 차이

| 주제 | AS-IS | TO-BE 후보 | 판정 |
|---|---|---|---|
| Controller 위치 | `application.controller` | `entry.web` | `[PROPOSED]` |
| Facade 위치 | `application.facade` | `entry.facade` | `[PROPOSED]` |
| Rule | 일반계층 없음 | `application.rule` | `[PROPOSED]` |
| DAO | `persistence.dao` | 유지 | 정합 |
| Mapper | XML Resource | `persistence.mapper` 표현 후보 | 설계정의 필요 |
| 업무축 | `co.a` 단일 | `[업무].[세부]` 확장 | 확장 필요 |
| TCF OFF | Service 직접 사례 | Facade 공통 | 개선 필요 |

## 25.4 TO-BE의 목적

폴더 이름을 바꾸는 것이 아니라:

```text
Entry 책임 명확화
      ↓
Use Case 경계 통일
      ↓
ON/OFF 공통 Business Core
      ↓
Business Rule 선택적 분리
      ↓
Persistence 책임 명확화
      ↓
Architecture Test 가능
```

로 이어지는 것이 목적이다.

---

# 26. NSIGHT 표준으로 승격 가능한 PDMG 요소

PDMG AS-IS 전체를 NSIGHT TO-BE로 자동 승격하지 않는다.

다음 기준으로 평가한다.

## 26.1 승격 후보가 강한 요소

```text
ServiceId 중심 Transaction Routing
Framework / Business 책임 분리
Handler → Facade → Service → DAO 의존방향
Context / Error / Logging 공통화
Timeout / Transaction 공통통제
Package와 업무분류 연결
Mapper/SQL Traceability
TCF ON/OFF를 통한 Entry Adapter 분리 개념
```

## 26.2 재설계/검증이 필요한 요소

```text
TCF OFF Controller → Service 직접
Facade 위치/책임 일관성
Rule 계층 부재
Gateway/인증 경계
JWT/SSO와 Business 연계
OM 범위
모든 업무축 확장규칙
Timeout/Transaction 상세
STF/ETF 실제 연결
```

---

# 27. FIG-III-13 — Forbidden Dependency / Risk Map

```text
[금지 1]
Handler
   └────────────► DAO
           X

[금지 2]
Controller
   └────────────► DAO
           X

[위험 3]
TCF OFF Controller
   └────────────► Service
        Facade 우회
        [AS-IS 일부 존재]

[금지 4]
Business Service
   └────────────► Framework 내부 구현 DAO
           X

[금지 5]
pdmg-fw
   └────────────► 특정 마케팅 업무 Rule
           X

[금지 6]
pdmg-service
   └────────────► JWT Private Key 직접 관리
           X

[위험 7]
새 업무축 추가
   └─ Package만 복사
      AOP/MapperScan/Config/Test 미수정
           X

[위험 8]
pdmg-om
   └─ 일반론 기능을 AS-IS로 확정
           X
```

---

# 28. 신규 업무축 추가 시 Architecture Checklist

현재 업무축:

```text
MG / CO / A
```

새 업무축을 추가한다고 가정하면 단순히 다음만 만들면 안 된다.

```text
nhnis.mg.xx.a
```

반드시 함께 검토한다.

```text
ServiceId Prefix
Java Package
Handler Registry
Mapper Resource Path
@MapperScan
Mapper namespace
AOP Pointcut
Config Bean
DTO
Test Package
Static/UI ServiceId
Log Category
Architecture Rule
OM/Monitoring
```

## 28.1 신규 축 추가 그림

```text
Business Taxonomy
      ↓
ServiceId
      ↓
Java Package
      ↓
Spring Bean
      ↓
Handler Registry
      ↓
AOP
      ↓
DAO / Mapper
      ↓
SQL
      ↓
Test
      ↓
Runtime Trace
```

---

# 29. Dependency Rule

## 29.1 AS-IS 정상 방향

```text
UI
  ↓ HTTP
Framework Entry
  ↓
Handler
  ↓
Facade
  ↓
Service
  ↓
DAO
  ↓
Mapper / DB
```

## 29.2 권장 의존 방향

```text
Entry
  ↓
Application
  ↓
Persistence

Framework
  └─ Entry/Application을 감싸고 공통 정책 제공
```

업무코드가 Framework 내부 구현에 역방향 의존하지 않도록 한다.

## 29.3 Side Boundary

```text
Application
  ├─ DTO / Contract
  ├─ Client → External
  ├─ Support
  └─ Config (조립)
```

---

# 30. Runtime Structure Preview

IV장 상세 전 PDMG Application 구조를 한 번에 보면:

```text
Browser / pdmg-ui
      │
      │ HTTP + ServiceId
      ▼
┌──────────────────────── pdmg-service JVM ────────────────────────┐
│                                                                  │
│  pdmg-fw                                                         │
│   Filter / Context / Interceptor                                 │
│        │                                                         │
│        ▼                                                         │
│   TCF Controller / Facade / Dispatcher                           │
│        │                                                         │
│        ▼                                                         │
│  pdmg-service                                                    │
│   Handler                                                        │
│        ▼                                                         │
│   Facade                                                         │
│        ▼                                                         │
│   Service                                                        │
│        ▼                                                         │
│   DAO                                                            │
│                                                                  │
└───────────────────────────┬──────────────────────────────────────┘
                            │
                            ▼
                      Mapper XML / DB
```

이 그림은 구조를 설명하며 **Thread/Transaction 시간을 표현하지 않는다.**

시간순 실행은 IV/V장에서 별도 Sequence로 그린다.

---

# 31. TCF ON/OFF와 Architecture Test 관점

두 Mode를 모두 허용한다면 다음을 자동검증해야 한다.

## 31.1 구조 규칙

```text
ON Handler → Facade
OFF Controller → Facade
Facade → Service
Service → DAO
```

## 31.2 비교 Test

```text
같은 입력
   ├─ TCF ON
   └─ TCF OFF

비교:
Response DTO
Error Code
DB 변경
Transaction
Business Log
Authorization
Timeout
```

## 31.3 운영 정책 `[OPEN]`

TCF OFF를:

```text
A. Local 개발 전용
B. Migration 검증
C. 운영 비상 경로
D. 공식 병행 Runtime
```

중 무엇으로 둘 것인지 Decision이 필요하다.

---

# 32. Layer별 운영/장애 해석

| Layer/Boundary | 대표 장애 | 확인 대상 |
|---|---|---|
| UI | JS/ServiceId/URL | Browser/Static |
| HTTP Process | CORS/Port/Network | UI/Service/JWT |
| FW Filter | Context/JWT/Body | `pdmg-fw` |
| TCF | Handler 미등록/Dispatcher | `pdmg-fw` + 업무 Handler |
| Handler | ServiceId 분기 | Registry |
| Facade | Transaction/Use Case | Business |
| Service | Business 오류 | Business |
| DAO/Mapper | SQL/Binding | MyBatis |
| DB | Lock/Connection | DataSource/Oracle |
| OM | `[UNKNOWN]` | IX |

---

# 33. Traceability

## 33.1 Module → Package → Responsibility

| Module | Package | Responsibility |
|---|---|---|
| pdmg-ui | `nhnis.mg.ui.*` | UI/Browser 접점 |
| pdmg-service | `nhnis.mg.co.a.*` | 업무 거래 |
| pdmg-fw | `nhnis.fw.*` | 공통 실행 |
| pdmg-jwt | `nhnis.mg.jw.a.*` | 인증/Token |
| pdmg-om | TBD | 운영관리 `[UNKNOWN]` |

## 33.2 ServiceId → Source

```text
mgcoa...
  ↓
nhnis.mg.co.a
  ↓
Handler
  ↓
Facade
  ↓
Service
  ↓
DAO
  ↓
rdw.mg.co.a/*.xml
```

## 33.3 II장 → III장 연결

```text
II
Information Application
      ↓
PDMG Reference

III
Module / Process / Context
      ↓
Package / Component / ServiceId

IV
Runtime Sequence
```

---

# 34. 확정 / GAP / OPEN / RISK

## 34.1 `[CONFIRMED / AS-IS]`

- 현재 PDMG 주요 Source Evidence는 `pdmg-ui`, `pdmg-service`, `pdmg-fw`, `pdmg-jwt`에 대해 확인된다.
- `pdmg-service` 업무 Root는 `nhnis.mg.co.a.*`.
- `pdmg-fw`는 `nhnis.fw.*`와 `com.ims.superspring.*` 공통 영역을 가진다.
- `pdmg-ui`는 `nhnis.mg.ui.*`.
- `pdmg-jwt`는 `nhnis.mg.jw.a.*`.
- `PdmgApplication`은 `scanBasePackages="nhnis"`로 분석되어 `nhnis.mg.*`와 `nhnis.fw.*` Bean을 같은 Context에 올린다.
- 현재 일반 업무 계층에는 `application.rule`이 확인되지 않는다.
- 현재 Handler는 `entry.handler`.
- 현재 Controller/Facade/Service는 `application.*`.
- DAO는 `persistence.dao`.
- Mapper Resource는 `rdw.mg.co.a/`.
- TCF ON/OFF는 진입 Adapter가 다르다.
- OFF AS-IS 일부 Controller는 Facade를 우회해 Service를 직접 호출한다.

## 34.2 `[GAP]`

| ID | 내용 | 영향 |
|---|---|---|
| GAP-III-01 | `pdmg-om` Current Source/Package/Runtime 미확인 | IX |
| GAP-III-02 | Gradle Build Dependency의 최신 원문 재검증 필요 | Module Baseline |
| GAP-III-03 | PDMG 5모듈 중 `pdmg-om` 포함 여부를 Snapshot별 인벤토리로 확정 필요 | 00/III |
| GAP-III-04 | 신규 업무축 추가 시 AOP/MapperScan/Config 자동검증 기준 미완성 | X |
| GAP-III-05 | TCF OFF Controller의 Facade 공통화 미완료 | IV/V |
| GAP-III-06 | `application.rule`을 표준으로 둘지 Decision 없음 | 개발표준 |
| GAP-III-07 | `entry.facade`로 Facade 위치를 이동할지 Decision 없음 | Package 표준 |
| GAP-III-08 | UI Direct vs Relay를 운영 표준으로 무엇을 쓸지 결정 필요 | VII/VIII |
| GAP-III-09 | JWT와 Business Service의 최종 Trust Boundary가 III장 범위에서 미확정 | VII |
| GAP-III-10 | STF/ETF의 Target 책임과 AS-IS 실연결 관계 구분 필요 | IV |

## 34.3 `[OPEN]`

| ID | 질문 |
|---|---|
| OPEN-III-01 | `pdmg-om`은 독립 Process인가, Business Application에 포함되는가 |
| OPEN-III-02 | TCF OFF는 운영 허용인가 Local/Migration 전용인가 |
| OPEN-III-03 | Facade Package의 표준 위치는 `application.facade`인가 `entry.facade`인가 |
| OPEN-III-04 | Rule Layer는 어떤 업무 복잡도에서 필수인가 |
| OPEN-III-05 | 신규 업무축 추가 시 ServiceId Prefix/Package/Mapper를 누가 승인하는가 |
| OPEN-III-06 | UI Relay는 호환용인가 표준 Gateway 역할인가 |
| OPEN-III-07 | PDMG를 NSIGHT 표준 Reference로 승격할 정확한 범위는 무엇인가 |

## 34.4 `[RISK]`

| ID | Risk | 영향 |
|---|---|---|
| RISK-III-01 | TCF OFF Service 직접호출 | Facade/TX/Use Case 경계 Drift |
| RISK-III-02 | 넓은 `nhnis` Component Scan | Bean 충돌/원치 않는 자동등록 |
| RISK-III-03 | 새 업무축에 AOP/MapperScan 누락 | 공통처리/DAO 미적용 |
| RISK-III-04 | Module=Server로 오해 | 장애분석/배포설계 오류 |
| RISK-III-05 | `Rule` 일반론을 AS-IS로 문서화 | Source와 문서 Drift |
| RISK-III-06 | OM 기능 일반론 확정 | 운영 Architecture 허위 Baseline |
| RISK-III-07 | UI Folder와 Java Package를 동일 분류로 오해 | Traceability 혼선 |
| RISK-III-08 | Handler/Facade/Service를 각각 독립 Layer로 과도하게 정의 | 책임/의존 설계 왜곡 |

---

# 35. ADR 후보

| ADR | 질문 |
|---|---|
| ADR-III-01 | PDMG 5모듈 Baseline에서 `pdmg-om`의 공식 Deployment/Runtime 위치는 어디인가 |
| ADR-III-02 | TCF OFF를 운영 Profile에서 허용할 것인가 |
| ADR-III-03 | ON/OFF 공통 Business Entry를 Facade로 강제할 것인가 |
| ADR-III-04 | Facade를 `entry.facade`로 이동할 것인가 현재 `application.facade`를 유지할 것인가 |
| ADR-III-05 | `application.rule`을 표준 Layer로 도입할 것인가 선택형으로 둘 것인가 |
| ADR-III-06 | UI Direct Call과 Relay 중 운영 표준을 무엇으로 둘 것인가 |
| ADR-III-07 | `scanBasePackages="nhnis"` 광역 Scan을 유지할 것인가 Module별 Scan으로 줄일 것인가 |
| ADR-III-08 | 신규 업무축 Registration을 Code Convention으로 둘 것인가 자동 Conformance Rule로 강제할 것인가 |

---

# 36. Architecture Rules

## 36.1 Must

1. `pdmg-fw`를 Remote HTTP Server로 그리지 않는다.
2. `pdmg-service` 업무코드는 `nhnis.mg.[업무].[세부]` 업무축을 가진다.
3. Handler는 업무 DAO를 직접 호출하지 않는다.
4. Controller는 DAO를 직접 호출하지 않는다.
5. ServiceId와 Java/Mapper 업무축의 일관성을 유지한다.
6. Source에 없는 `Rule`/`OM` 기능을 AS-IS로 작성하지 않는다.
7. TCF ON/OFF를 같은 Entry라고 설명하지 않는다.
8. Module Boundary와 Process Boundary를 문서에서 구분한다.
9. Framework 책임과 Business 책임을 섞지 않는다.
10. PDMG AS-IS를 NSIGHT 전체 TO-BE로 자동 승격하지 않는다.

## 36.2 Should

1. ON/OFF가 Facade 이하 Business Core를 공유하도록 정렬한다.
2. 새 업무축 추가 시 Package뿐 아니라 Mapper/AOP/Test/Trace를 동시에 갱신한다.
3. Business Rule이 복잡해지면 Rule Layer 도입을 ADR로 검토한다.
4. Architecture Test로 의존방향을 검증한다.
5. ServiceId→Handler→Facade→DAO→Mapper Trace Index를 자동 생성한다.

---

# 37. 검증 체크리스트

## 37.1 Module

- [x] 5모듈 Baseline이 표시되어 있는가
- [x] `pdmg-om` Unknown을 숨기지 않았는가
- [x] UI/JWT/Service/FW 역할이 구분되는가
- [x] Module을 Server로 오해하지 않았는가

## 37.2 Runtime Boundary

- [x] UI→Service/JWT HTTP Process 경계가 표현되는가
- [x] Service+FW 동일 Spring Context가 표현되는가
- [x] Data Boundary가 별도로 표현되는가

## 37.3 Package

- [x] 현재 업무 Root가 `nhnis.mg.co.a`인가
- [x] 과거 축 없는 패키지를 현재 AS-IS로 쓰지 않았는가
- [x] Rule Layer를 AS-IS로 쓰지 않았는가
- [x] JWT Package를 CO/A 아래에 넣지 않았는가
- [x] UI Static과 Server Package를 동일시하지 않았는가

## 37.4 Application Component

- [x] Handler→Facade→Service→DAO 의존방향이 표현되는가
- [x] DAO→Mapper XML이 표현되는가
- [x] Facade TX를 항상 최외곽으로 단정하지 않았는가
- [x] ON/OFF Entry 차이를 표시했는가

## 37.5 AS-IS / TO-BE

- [x] `entry.facade`, `application.rule`이 PROPOSED로 구분되는가
- [x] TCF OFF Service 직접호출 GAP가 표시되는가
- [x] PDMG Standard 승격범위를 별도 Decision으로 남겼는가

---

# 38. FIG-III-14 — IV장 Handoff

```text
II. Big Picture
      │
      ▼
III. PDMG Module / Application Architecture
      │
      ├─ Module Boundary
      ├─ Process Boundary
      ├─ Spring Context
      ├─ Package / Layer
      ├─ ServiceId / Component
      └─ TCF ON / OFF
              │
              ▼
IV. PDMG Online Runtime & TCF Flow
              │
              ├─ DefaultFilter
              ├─ SecurityFilterChain
              ├─ DispatcherServlet
              ├─ Interceptor
              ├─ OnlineTransactionController
              ├─ TcfFacade
              ├─ OnlineTimeoutExecutor
              ├─ Dispatcher
              ├─ Handler
              ├─ Facade
              ├─ Service
              ├─ DAO
              ├─ Response Advice
              └─ Context Clear
```

## 38.1 IV장에서 반드시 답할 질문

1. HTTP 요청은 정확히 어떤 순서로 각 Filter/Interceptor/Controller를 통과하는가?
2. Spring Security는 DefaultFilter와 MVC 사이에서 어떻게 배치되는가?
3. `ServiceContext`는 언제 생성되고 언제 제거되는가?
4. ServiceId는 어디에서 추출되고 Dispatcher가 어떻게 Handler를 선택하는가?
5. `OnlineTimeoutExecutor`는 어느 Thread에서 실행되는가?
6. `TransactionTemplate`은 어느 시점에 시작되는가?
7. Handler는 실제로 어떤 Facade Method를 호출하는가?
8. BizPrePostAspect는 어디에 개입하는가?
9. 정상/업무오류/시스템오류 Response는 어디에서 조립되는가?
10. TCF OFF에서 위 경로 중 무엇이 사라지고 무엇이 남는가?

---

# 39. Completion Gate

```text
Figure Plan                    14
실제 Text Figure              14

PDMG 5-Module Map             PASS
Module Change Reason          PASS
Process/Module/Data Boundary  PASS
Build vs Runtime              PASS
Spring Context                PASS
Framework vs Business         PASS
AS-IS Package                 PASS
Module/Base Package           PASS
ServiceId Trace               PASS
TCF ON/OFF                    PASS
Boundary Difference           PASS
AS-IS vs TO-BE                PASS
Forbidden/Risk                PASS
IV Handoff                    PASS

Source 없는 Rule AS-IS       0건
Source 없는 OM AS-IS         0건
Module=Remote Server 오해     0건
```

**판정: CONDITIONAL PASS**

## PASS 전환 조건

```text
Condition-III-01
현재 Repository Snapshot에서
pdmg-ui / pdmg-service / pdmg-fw / pdmg-jwt / pdmg-om
실제 포함 여부와 Gradle Dependency를 기계적으로 재스캔

Condition-III-02
pdmg-om Source / Package / Process / Function Evidence 확보

Condition-III-03
TCF OFF 운영정책 결정

Condition-III-04
Facade 위치 / Rule Layer 표준 ADR 확정

Condition-III-05
신규 업무축 Package/Mapper/AOP/ServiceId Conformance Rule 정의
```

---

# 40. 장 최종 평가

III장은 II장의 추상적 PDMG Reference를 실제 Source 구조로 내렸다.

핵심은 다음이다.

> **PDMG는 여러 모듈로 나뉘지만, 모듈 경계와 서버 프로세스 경계는 동일하지 않다.**

> **`pdmg-service`와 `pdmg-fw`는 책임은 분리되지만 동일 Spring ApplicationContext 안에서 함께 동작할 수 있다.**

> **현재 업무 Source는 `nhnis.mg.co.a.*` 단일 업무축이며 Handler → Facade → Service → DAO/Mapper가 AS-IS 핵심 의존방향이다.**

> **TCF ON과 OFF는 Entry Adapter가 다르고, OFF AS-IS 일부는 Facade를 우회하므로 동일 Business Core 보장이 현재 완전하지 않다.**

> **`application.rule`, `entry.facade`, `pdmg-om` 상세는 현재 Source와 TO-BE를 구분하여 관리해야 한다.**

따라서 IV장에서는 구조 그림을 시간순 실행으로 전환한다.

```text
Structure
Module / Context / Package
        ↓
Runtime
HTTP → Filter → MVC → TCF → Handler → Business → DB → Response
```

III장에서 정의한 경계가 IV장에서 실제 요청 1건의 Runtime Sequence로 검증되어야 한다.
