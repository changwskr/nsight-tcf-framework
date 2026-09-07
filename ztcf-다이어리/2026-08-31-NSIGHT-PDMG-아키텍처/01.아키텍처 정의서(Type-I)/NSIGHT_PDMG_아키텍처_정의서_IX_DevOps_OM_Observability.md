# NSIGHT / PDMG 아키텍처 정의서 — IX. DevOps / OM / Observability Architecture

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 대상: NSIGHT DevOps / Operations / Observability Target + PDMG Runtime Evidence Reference  
> 문서 상태: **Draft / Evidence-First**  
> 작성일: 2026-08-31  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_VIII_Infrastructure_WAS_Capacity_HA_DR.md`

---

# 0. Evidence Register

| ID | 근거 자료 | 본 장 사용 목적 | 상태 |
|---|---|---|---|
| EV-IX-01 | `2026-03-08-NH_아키텍처전략_정리본_(최종본)_V1.0.docx` | GitLab / GitLab Runner / eCAMS / IaaS DevOps 전략, Observability 전략 | `[NSIGHT FACT/STRATEGY]` |
| EV-IX-02 | `2026-05-07_농협 상호금융 아키텍처 전략 브리핑-v1.0.docx` | Standardization ↔ DevOps 지속 운영구조 | `[NSIGHT FACT/STRATEGY]` |
| EV-IX-03 | `01장.PDMG 시스템 개요.md` | PDMG Java 21 / Spring Boot 3.5.14 / Gradle 멀티프로젝트 / Log4j2 / WAR 배포 성격 | `[PDMG AS-IS EVIDENCE]` |
| EV-IX-04 | `NSIGHT_PDMG_아키텍처_정의서_VI_Standard_Message_Context_Error_Logging.md` | GUID/MDC/ImageLog/SQL/오류 Evidence | `[CURRENT BASELINE DRAFT]` |
| EV-IX-05 | `NSIGHT_PDMG_아키텍처_정의서_V_Transaction_Timeout_Thread_DB_Architecture.md` | PDMG Worker/Queue/Timeout/Transaction 관측대상 | `[CURRENT BASELINE DRAFT]` |
| EV-IX-06 | `NSIGHT_PDMG_아키텍처_정의서_VII_Security_SSO_JWT_Session.md` | JWT/JWKS/로그인/인증 오류 운영 관측대상 | `[CURRENT BASELINE DRAFT]` |
| EV-IX-07 | `NSIGHT_PDMG_아키텍처_정의서_VIII_Infrastructure_WAS_Capacity_HA_DR.md` | WEB/Tomcat/JVM/Hikari/HA/DR Metric Handoff, Config Drift | `[CURRENT BASELINE DRAFT]` |
| EV-IX-08 | `2026-06-03 NSIGHT_용량산정_화면설계서_WAS실행쓰레드포함.docx` | Thread/DB Pool/Capacity 운영 점검지표와 후보 임계구간 | `[CAPACITY DESIGN EVIDENCE]` |
| EV-IX-09 | `2026-06-03은행권_TPMC_TPS_기반_용량산정_예시_보고서.docx` | p95/CPU/GC/Thread/Hikari/SQL/장애전환 Runtime Validation 항목 | `[CAPACITY DESIGN EVIDENCE]` |
| EV-IX-10 | `2026-08-17-NSIGHT_전체_아키텍처_통합분석_정의_마스터_프롬프트.md` | Architecture→Config→Test→Deployment→Runtime Evidence→Drift Closed Loop | `[WORKING GOVERNANCE BASELINE]` |
| EV-IX-11 | `NSIGHT_PDMG_아키텍처_인포그래픽_이미지화_마스터_프롬프트.md` | IX장 필수 Figure, OM FACT 금지규칙 | `[WORKING BASELINE]` |
| EV-IX-12 | `NSIGHT_PDMG_아키텍처_정의서_III_PDMG_Module_Application_Architecture.md` | `pdmg-om` Current Implementation UNKNOWN | `[CURRENT BASELINE DRAFT]` |

---

# 1. Source Classification — DevOps와 OM을 섞지 않는다

본 장은 다음 세 가지 Source Level을 명확하게 분리한다.

```text
A. NSIGHT Strategy
   GitLab
   GitLab Runner
   eCAMS
   IaaS
   APM / 통합로그 / GUID Trace
        │
        │ Target/Strategy
        ▼

B. PDMG Current Source
   Java 21
   Spring Boot 3.5.14
   Gradle Multi-project
   pdmg-service / pdmg-fw / pdmg-jwt / pdmg-ui
   Log4j2 / GUID / ImageLog / Runtime Control
        │
        │ AS-IS Evidence
        ▼

C. Runtime Operations Target
   Build Gate
   Deployment Evidence
   Monitoring
   Alert
   Runbook
   Drift
   OM Control Plane
        │
        └─ 일부는 아직 구현 Evidence 없음
```

## 1.1 가장 중요한 금지

```text
GitLab 전략자료가 존재
=
PDMG 현재 저장소에 GitLab CI Pipeline 구현완료

X
```

```text
pdmg-om 이름 존재
=
Thread/JVM/Hikari Dashboard 구현완료

X
```

```text
Metric이 필요
=
현재 Metric Exporter가 구현됨

X
```

---

# 2. Figure Plan

| FIG | 제목 | Level | 목적 | 필수 |
|---|---|---:|---|---|
| FIG-IX-01 | DevOps / OM / Observability Big Picture | L0~L3 | Change→Runtime Evidence 전체 구조 | Y |
| FIG-IX-02 | NSIGHT Standardization ↔ DevOps Loop | L1~L3 | 표준과 운영의 무한루프 | Y |
| FIG-IX-03 | Source → Build → Test → Artifact → Deploy | L2~L4 | Delivery Pipeline | Y |
| FIG-IX-04 | NSIGHT SCM / Dev / Prod Deployment Tool Boundary | L2~L4 | GitLab/Runner/eCAMS 책임 | Y |
| FIG-IX-05 | PDMG Build / Artifact Boundary | L2~L4 | Gradle/모듈/산출물 Evidence | Y |
| FIG-IX-06 | Config / Secret / Key Separation | L2~L4 | Artifact와 환경설정 분리 | Y |
| FIG-IX-07 | Environment Promotion | L2~L4 | Dev→Test→Prod→DR | Y |
| FIG-IX-08 | Release / Rollback Evidence | L2~L5 | 배포증적/복구계약 | Y |
| FIG-IX-09 | OM Position — Control Plane vs Data Plane | L1~L4 | OM 책임 경계 | Y |
| FIG-IX-10 | pdmg-om Evidence Maturity | L2~L5 | Current Unknown을 시각화 | Y |
| FIG-IX-11 | Transaction Observability | L2~L4 | GUID/ServiceId/Thread/TX/SQL | Y |
| FIG-IX-12 | JVM / Tomcat / Worker Monitoring | L2~L4 | Thread/GC/Queue | Y |
| FIG-IX-13 | Hikari / DB / Slow SQL Monitoring | L2~L4 | DB Pool 병목 | Y |
| FIG-IX-14 | Timeout / Overload / Error Monitoring | L2~L4 | 504/503/Error 분류 | Y |
| FIG-IX-15 | JWT / Security Monitoring | L2~L4 | 인증/Key/JWKS 이상 | Y |
| FIG-IX-16 | GUID + ServiceId End-to-End Evidence | L2~L5 | App→SQL→Log→ImageLog | Y |
| FIG-IX-17 | Alert → Diagnosis → Recovery | L3~L5 | 운영 Runbook | Y |
| FIG-IX-18 | Capacity Baseline vs Runtime Drift | L3~L5 | 설계값/실제값 검증 | Y |
| FIG-IX-19 | Deployment Architecture Drift | L3~L5 | Artifact/Config/Server 불일치 | Y |
| FIG-IX-20 | Release Evidence Package | L3~L5 | Gate 입력 패키지 | Y |
| FIG-IX-21 | Operations Gate / Runtime Evidence Gate | L4~L5 | Release 승인 조건 | Y |
| FIG-IX-22 | X장 Handoff | L5 | ServiceId Closed Loop 연결 | Y |

---

# 3. 핵심 결론

IX장의 핵심 결론은 다음과 같다.

1. **NSIGHT DevOps의 목표는 배포 자동화 그 자체가 아니라 Architecture Standard를 운영환경까지 지속시키는 것이다.**
2. NSIGHT 전략자료는 형상관리 `GitLab`, 개발배포 `GitLab Runner`, 운영배포 `eCAMS`, 환경 일관성을 위한 `IaaS`를 명시한다.
3. 이 도구명은 NSIGHT Strategy의 근거이며, PDMG Current Repository에 실제 GitLab Pipeline 파일과 eCAMS Job이 존재한다고 자동 추론하지 않는다.
4. PDMG Current Source는 Java 21, Spring Boot 3.5.14, Gradle 멀티프로젝트 구조와 `pdmg-service → pdmg-fw`, `pdmg-jwt → pdmg-fw` Build Dependency를 확인할 수 있다.
5. 현재 PDMG Source 기준으로 확인되는 실제 구현 Baseline은 `pdmg-ui`, `pdmg-service`, `pdmg-fw`, `pdmg-jwt` 네 모듈이며, `pdmg-om`은 Expected Baseline이지만 현재 구현 Evidence는 확보되지 않았다.
6. `pdmg-service`는 Business 실행 WAR 성격이고 `pdmg-fw`는 공통 JAR/Classpath 역할이다. `pdmg-ui`, `pdmg-jwt`의 정확한 운영 Artifact/Deployment Unit은 최신 `build.gradle`/Deployment Manifest를 통해 확정해야 한다.
7. DevOps Pipeline은 `Source → Build → Test → Artifact → Approval → Deploy → Verify → Runtime Evidence`로 닫혀야 한다.
8. 동일 Source Commit으로 생성된 Artifact가 개발/테스트/운영에서 동일하게 승격되고 환경별 차이는 Config/Secret로 분리하는 구조가 목표다.
9. Private Key, Password, Token Secret, 내부 HMAC Secret은 Source/Artifact에 포함되는 일반 Config가 아니며 CI/CD Secret Boundary로 분리해야 한다.
10. `pdmg-om`이 현재 확인되지 않으므로 OM Dashboard, 사용자관리, 배포관리 등의 기능을 AS-IS로 작성하지 않는다.
11. NSIGHT에서 OM은 목표적으로 **Control Plane**, PDMG Business Runtime은 **Data/Execution Plane**으로 분리하는 것이 적절하다.
12. Observability의 기본축은 NSIGHT 전략에서 **APM + 전사 통합로그 + Trace-ID(GUID)**로 명시된다.
13. PDMG는 GUID, ServiceId, MDC, ImageLog, Timeout/Overload/Error를 이미 Runtime Evidence의 일부로 제공한다.
14. 좋은 운영 화면은 “서버 CPU가 높은가?”만 보는 것이 아니라 **어느 ServiceId가, 어느 WAR/JVM에서, 어느 Worker/DB Pool/SQL 때문에 느린가**까지 연결할 수 있어야 한다.
15. Tomcat Thread, PDMG Worker, Hikari, DB Session은 서로 다른 Resource Pool이며 Dashboard도 분리해서 보여 주되 GUID/ServiceId와 상관분석할 수 있어야 한다.
16. PDMG의 504 Timeout, 503 Overload, Handler Not Found, JWT 오류, DB 오류는 서로 다른 Failure Cause이므로 동일 “오류율” 그래프로만 처리해서는 안 된다.
17. Runtime Alert에는 단순 임계치뿐 아니라 **원인 연쇄**가 필요하다. 예: `DB Slow → Hikari Pending → Worker Queue → Timeout → 504`.
18. Capacity 문서의 p95/CPU/Thread/Pool 기준은 **운영판정 후보**이며 실제 Production Alert Threshold로 자동 확정하지 않는다.
19. Deployment 완료는 Architecture 완료가 아니다. 배포 후 Smoke Test, Transaction Trace, Metric, Error Rate, Config Drift, HA 상태를 증명해야 한다.
20. Release Evidence는 Commit, Build, Test, Artifact, Config, 승인, 배포대상, Runtime 검증을 하나의 패키지로 묶어야 한다.
21. 장애 Runbook은 “재기동”에서 시작하지 않고 **Detection → Scope → Evidence → Root Cause → Recovery → Verification → Postmortem** 순서로 실행해야 한다.
22. 최종적으로 IX장은 `Architecture → Source → Build → Deploy → Runtime Evidence → Drift → GAP/ADR → New Baseline`의 운영 Closed Loop를 만든다.

---

# 4. 목적 / 범위 / 전제

## 4.1 목적

본 장은 다음 질문에 답한다.

1. NSIGHT DevOps의 전략적 Tool과 책임은 무엇인가?
2. PDMG는 현재 어떤 기술스택으로 Build되는가?
3. PDMG Module Dependency와 Artifact Boundary는 어떻게 다른가?
4. Source와 Config/Secret은 어떻게 분리해야 하는가?
5. 개발→테스트→운영→DR 환경 승격은 무엇을 동일하게 유지해야 하는가?
6. 운영 배포는 어떤 승인·검증 Evidence를 가져야 하는가?
7. Rollback은 어떤 Artifact/Config/DB 상태를 복구해야 하는가?
8. OM은 Architecture에서 어디에 위치하는가?
9. 현재 `pdmg-om` 구현은 어디까지 확인되었는가?
10. 한 거래를 GUID+ServiceId로 어떻게 End-to-End 추적하는가?
11. Tomcat Thread/PDMG Worker/Hikari/DB의 병목은 어떻게 구분하는가?
12. Timeout/Overload/JWT/Error를 어떻게 운영적으로 분류하는가?
13. Alert를 실제 진단/복구 절차와 어떻게 연결하는가?
14. Capacity 설계와 Runtime Config/Metric의 Drift를 어떻게 탐지하는가?
15. 배포 후 Architecture Gate에 어떤 Runtime Evidence가 필요한가?
16. X장 Closed Loop로 무엇을 넘길 것인가?

## 4.2 포함

```text
GitLab
GitLab Runner
eCAMS
IaaS
Gradle
Java 21
Spring Boot 3.5.14
Build / Test
Artifact
WAR / JAR
Config
Secret
Key
Environment Promotion
Release
Rollback
OM
APM
Integrated Log
GUID
ServiceId
MDC
ImageLog
Tomcat Thread
PDMG Worker
Queue
JVM Heap/GC
Hikari
DB Session
Slow SQL
Timeout
Overload
JWT Error
Alert
Runbook
Runtime Evidence
Drift
Release Gate
```

## 4.3 제외

```text
ServiceId 전체 Naming Anatomy        → X
ServiceId→Handler→DAO→SQL 전수 Index → X
Architecture Model JSON              → X
Conformance Rule Engine 전체         → X
OM 제품선정                          → 별도 운영 ADR
APM 제품선정                         → 별도 운영/TA ADR
SIEM 제품선정                        → Security/Operations
```

---

# 5. FIG-IX-01 — DevOps / OM / Observability Big Picture

```text
┌────────────────── Development / Change ──────────────────┐
│                                                         │
│ Requirement / ADR / Source                              │
│          │                                              │
│          ▼                                              │
│       GitLab                                            │
│          │                                              │
│          ▼                                              │
│ Build / Test / Quality                                  │
│          │                                              │
│          ▼                                              │
│       Artifact                                          │
└──────────┬──────────────────────────────────────────────┘
           │
           ▼
┌────────────────── Delivery / Control ───────────────────┐
│                                                         │
│ Development Deployment  : GitLab Runner [NSIGHT]        │
│ Production Deployment   : eCAMS [NSIGHT]                │
│ Config / Secret / Approval / Rollback                   │
└──────────┬──────────────────────────────────────────────┘
           │
           ▼
┌──────────────────── Runtime Plane ──────────────────────┐
│                                                         │
│ WEB → Tomcat/JVM → PDMG → Worker → Hikari → DB         │
│                                                         │
└──────────┬──────────────────────────────────────────────┘
           │
           ▼
┌────────────────── Observability Plane ──────────────────┐
│                                                         │
│ APM                                                     │
│ Integrated Log                                          │
│ GUID / ServiceId                                        │
│ Metrics                                                 │
│ ImageLog                                                │
│ Alert                                                   │
└──────────┬──────────────────────────────────────────────┘
           │
           ▼
┌──────────────────── Control Plane ──────────────────────┐
│                                                         │
│ OM / Operations / Architecture Governance               │
│ [pdmg-om AS-IS implementation = UNKNOWN]                │
│                                                         │
│ Runtime Evidence → Drift → GAP / ADR → New Baseline     │
└─────────────────────────────────────────────────────────┘
```

---

# 6. FIG-IX-02 — Standardization ↔ DevOps Loop

NSIGHT 전략자료가 강조하는 유지관리 구조:

```text
Interface Standard
      ↕
Data Standard
      ↕
Code / Application Standard
      ↕
┌──────────────── DevOps Environment ───────────────┐
│                                                  │
│ GitLab                                           │
│ GitLab Runner                                    │
│ eCAMS                                            │
│ IaaS                                             │
└──────────────────┬───────────────────────────────┘
                   │
                   ▼
              Runtime System
                   │
                   ▼
           Runtime Evidence
                   │
                   ▼
          Standard 개선 / 통제
                   └────────────────────────────↺
```

## 6.1 핵심

```text
표준
→ 개발자가 지켜야 하는 문서

```

에 머물면 유지되지 않는다.

목표:

```text
Standard
→ Pipeline Check
→ Deployment Gate
→ Runtime Measurement
→ Drift Detection
```

으로 실행화한다.

---

# 7. NSIGHT DevOps Tool Boundary `[FACT/STRATEGY]`

2026-03 Strategy Baseline:

| 영역 | Tool | 역할 | Evidence 상태 |
|---|---|---|---|
| 형상관리 | GitLab | Source 버전/Branch/변경이력 | `[NSIGHT STRATEGY FACT]` |
| 개발배포 | GitLab Runner | CI/CD 자동화, 개발환경 배포 | `[NSIGHT STRATEGY FACT]` |
| 운영배포 | eCAMS | 운영 배포관리/변경통제 | `[NSIGHT STRATEGY FACT]` |
| 환경일관성 | IaaS | 개발·스테이징·운영 정합 | `[NSIGHT STRATEGY FACT]` |

## 7.1 현재 PDMG에 대해 아직 증명되지 않은 것

```text
.gitlab-ci.yml 존재
Runner Job Name
eCAMS Deployment Job
Production Approval Workflow
Artifact Repository Path
Rollback Job
Blue/Green
Canary
```

현재 Source Evidence가 없으므로 `[UNKNOWN]`.

---

# 8. FIG-IX-03 — Source → Build → Test → Artifact → Deploy

```text
Developer
   │
   ▼
GitLab
   │
   │ Commit / Merge
   ▼
CI Trigger
   │
   ▼
Gradle Wrapper
   │
   ├─ compile
   ├─ unit test
   ├─ architecture test [TO-BE]
   ├─ security/quality check [TO-BE/Tool TBD]
   └─ package
   │
   ▼
Artifact
   │
   ├─ FW Library
   ├─ Service Application
   ├─ JWT Application
   └─ UI Application
   │
   ▼
Development Deployment
GitLab Runner [NSIGHT Strategy]
   │
   ▼
Integration / Runtime Test
   │
   ▼
Approval
   │
   ▼
Production Deployment
eCAMS [NSIGHT Strategy]
   │
   ▼
Smoke / Runtime Evidence
   │
   ▼
PASS / Rollback
```

## 8.1 Pipeline에서 변하지 않아야 할 것

```text
Source Commit
Artifact Binary
Version
Checksum
```

환경별로 바뀌어야 하는 것:

```text
Datasource URL
External Endpoint
Profile
Secret
Key Reference
Log Level
Capacity Parameter [승인정책]
```

---

# 9. FIG-IX-04 — GitLab / Runner / eCAMS 책임

```text
GitLab
│
├─ Source
├─ Branch
├─ Merge History
└─ Release Tag
     │
     ▼
GitLab Runner
│
├─ Build
├─ Test
└─ Dev Deployment
     │
     ▼
Release Candidate
     │
     ▼
Approval / Change Control
     │
     ▼
eCAMS
│
├─ Production Delivery
├─ Deployment Control
└─ Change Evidence
```

## 9.1 책임 분리

```text
SCM
≠
Build Executor
≠
Production Change Manager
```

한 Tool에 여러 기능이 있더라도 Architecture Responsibility를 구분한다.

---

# 10. PDMG Current Build Baseline `[AS-IS]`

현재 PDMG Source Overview가 직접 확인하는 항목:

```text
Java       = 21
SpringBoot = 3.5.14
Build      = Gradle multi-project
Logging    = SLF4J / Log4j2
Data       = MyBatis / JDBC
Deploy     = WAR capable
```

Build Dependency:

```text
pdmg-service
   └─ implementation project(':pdmg-fw')

pdmg-jwt
   └─ implementation project(':pdmg-fw')

pdmg-ui
   └─ independent application
```

현재 Source Snapshot의 구현 확인 모듈:

```text
pdmg-ui
pdmg-service
pdmg-fw
pdmg-jwt
```

`pdmg-om`:

```text
[EXPECTED BASELINE]
[CURRENT SOURCE IMPLEMENTATION = UNKNOWN]
```

---

# 11. FIG-IX-05 — PDMG Build / Artifact Boundary

```text
PDMG Repository / Source
│
├─ pdmg-fw
│   └─ Common Framework Library
│       [JAR/classpath role confirmed]
│
├─ pdmg-service
│   ├─ Business Application
│   └─ Spring Boot WAR role confirmed
│
├─ pdmg-jwt
│   ├─ Authentication Application
│   └─ exact production artifact type [VERIFY]
│
├─ pdmg-ui
│   ├─ UI Application
│   └─ exact production artifact type [VERIFY]
│
└─ pdmg-om
    └─ current artifact [UNKNOWN]
```

## 11.1 Artifact Contract에서 추가로 필요한 Evidence

```text
Artifact Filename
Version
Packaging Type
Build Task
Embedded/External Container
Checksum
Dependency Lock
Repository Location
Deploy Context
```

---

# 12. PDMG Build에서 절대 섞지 않을 것

PDMK Build 자료가 별도로 존재하지만 본 장에서 다음을 PDMG FACT로 복사하지 않는다.

```text
PDMK artifact filename
PDMK group/version
PDMK build script path
PDMK Nexus URL
PDMK Wrapper exact value
```

PDMG의 값은 PDMG `build.gradle`, `settings.gradle`, Wrapper를 직접 검증해야 한다.

---

# 13. Build Reproducibility

좋은 Build는 다음 식을 만족해야 한다.

```text
Same Commit
+
Same Dependency Baseline
+
Same Build Toolchain
=
Same Artifact
```

확인 대상:

```text
JDK
Gradle Wrapper
Plugin Version
Dependency Version
Internal Library
Encoding
Repository
```

현재 Java/Spring Boot는 Source Overview에서 확인되지만 전체 Dependency Lock/Checksum 체계는 `[GAP]`.

---

# 14. Artifact Versioning

현재 PDMG Release Version 정책의 공식 형태는 본 Evidence에서 확인되지 않았다.

따라서:

```text
SNAPSHOT / Semantic Version
Git Commit SHA
Build Number
Release Tag
```

중 무엇을 운영 Artifact Identity로 사용할지 `[OPEN]`.

TO-BE 최소 조건:

```text
Artifact
→ Source Commit 역추적 가능
```

---

# 15. FIG-IX-06 — Config / Secret / Key Separation

```text
Source Repository
┌────────────────────────────┐
│ Code                       │
│ Default Config Template    │
│ No Production Secret       │
└─────────────┬──────────────┘
              │ build
              ▼
Artifact
┌────────────────────────────┐
│ WAR/JAR                    │
│ Immutable Candidate        │
└─────────────┬──────────────┘
              │ deploy
              ▼
Environment
┌────────────────────────────┐
│ application config         │
│ datasource endpoint        │
│ feature/profile            │
│ timeout/pool approved      │
│                            │
│ Secret Reference ───────┐  │
└─────────────────────────┼──┘
                          │
                          ▼
                  Secret / Key Boundary
                  ├─ Password
                  ├─ JWT Private Key
                  ├─ HMAC Secret
                  └─ DB Credential
```

## 15.1 Source에 넣지 않을 것

```text
Production Password
RSA Private Key
Refresh Token
Access Token
Internal HMAC Secret
Production DB Password
```

---

# 16. Environment Configuration

환경별 Config가 달라질 수 있는 영역:

```text
local
development
test
production
DR
```

대표 차이:

```text
Database
Endpoint
Security
Logging
Capacity
External Interface
```

그러나:

```text
업무 Source 자체를 환경마다 다르게 관리
```

하는 방식은 Drift를 만든다.

목표:

```text
Same Artifact
+
Environment Configuration
```

---

# 17. FIG-IX-07 — Environment Promotion

```text
Source Commit
    │
    ▼
Build Once
    │
    ▼
Artifact A
    │
    ├──────────────► Development
    │                  ↓ Test Evidence
    │
    ├──────────────► Test / Staging
    │                  ↓ Approval Evidence
    │
    ├──────────────► Production
    │                  ↓ Runtime Evidence
    │
    └──────────────► DR
                       ↓ DR Verification
```

## 17.1 환경마다 다시 Build하는 위험

```text
Dev Build
Test Build
Prod Build
```

을 각자 수행하면:

```text
같은 Source Tag
BUT
Dependency / Build Environment 차이
→ Binary Drift
```

가능.

`[TO-BE] Build Once, Promote Artifact`.

현재 NSIGHT/PDMG 운영에서 이 방식이 이미 구현되었다는 Evidence는 아직 없다.

---

# 18. Config Promotion

Artifact와 달리 Config는 환경 차이가 존재한다.

따라서 Promotion은:

```text
Artifact A
+
Dev Config Version
+
Test Config Version
+
Prod Config Version
```

을 모두 Release Manifest에 기록해야 한다.

특히 V/VII/VIII에서 다룬:

```text
Timeout
Worker Pool
Hikari
Session
JWT
JWKS
Key
Endpoint
```

는 Architecture Parameter이므로 Config 변경도 Architecture Drift 대상이 된다.

---

# 19. Deployment Manifest

최소 다음을 기록해야 한다.

```text
Release ID
Source Commit
Artifact Name
Artifact Version
Checksum
Environment
Center
Hostname
Tomcat JVM
Context
Config Version
Secret Reference Version
DB Schema Version
Deployment Time
Approver
Operator/System
Rollback Artifact
Smoke Test Result
```

현재 실제 eCAMS Manifest 필드가 위와 동일하다고 주장하지 않는다.

`[PROPOSED Release Evidence Model]`.

---

# 20. FIG-IX-08 — Release / Rollback Evidence

```text
Release Candidate
      │
      ├─ Source Evidence
      ├─ Build Evidence
      ├─ Test Evidence
      ├─ Security Evidence
      ├─ Config Evidence
      └─ Approval
      │
      ▼
Deployment
      │
      ▼
Smoke Test
      │
      ├─ PASS
      │    ↓
      │ Runtime Monitor
      │
      └─ FAIL
           ↓
        Rollback
           │
           ├─ Previous Artifact
           ├─ Previous Config
           └─ DB Compatibility Check
```

## 20.1 Rollback은 WAR 교체만이 아니다

다음 변경이 함께 있는 경우:

```text
DB DDL
Data Migration
Config
Key
Endpoint
```

Artifact만 이전 버전으로 되돌리면 복구되지 않을 수 있다.

---

# 21. Rollback Decision

Rollback Trigger 후보:

```text
Smoke Failure
Critical Error 증가
Authentication Failure
Timeout 급증
DB Connection Exhaustion
Data Integrity Failure
Security Gate Failure
```

실제 Threshold/자동 rollback 여부는 `[OPEN]`.

---

# 22. Blue/Green / Canary

현재 PDMG/NSIGHT 자료에서:

```text
Blue/Green
Canary
Rolling Deployment
```

의 최종 방식을 확정할 Evidence가 없다.

따라서 일반론으로 적용됐다고 쓰지 않는다.

`[OPEN-IX-01]`

---

# 23. FIG-IX-09 — OM Position: Control Plane vs Execution Plane

```text
┌────────────────── Execution / Data Plane ──────────────────┐
│                                                            │
│ Browser → WEB → WAS → PDMG → TCF → Business → DB          │
│                                                            │
└──────────────────────────┬─────────────────────────────────┘
                           │ metric / event / control status
                           ▼
┌──────────────────── Control Plane ─────────────────────────┐
│                                                            │
│ OM / Operations                                            │
│                                                            │
│ 상태 조회                                                  │
│ 정책 조회/통제 [범위 승인 필요]                            │
│ 운영 증적                                                  │
│ Alert / Runbook 연결                                       │
│                                                            │
│ pdmg-om Current Source = UNKNOWN                           │
└────────────────────────────────────────────────────────────┘
```

## 23.1 원칙

OM은 Business Transaction Path의 필수 동기 Dependency가 되어서는 안 된다.

```text
OM Down
→ Business Runtime 전체 Down
```

구조는 피해야 한다.

단, 거래통제처럼 Runtime에서 반드시 조회해야 하는 기준정보를 OM이 제공한다면 Cache/Fail Policy가 별도 필요하다.

---

# 24. OM Target Responsibility `[WORKING TARGET]`

현재 `pdmg-om` Source가 없으므로 아래는 **Target Responsibility 후보**다.

```text
Runtime Status
Transaction Status
ServiceId / Policy Visibility
Timeout / Error Status
Thread / Worker / DB Pool State
Configuration Baseline Visibility
Deployment / Release Evidence Visibility
Audit / Operation Log
Health / Alert
```

다음은 Source 확보 전 AS-IS로 확정하지 않는다.

```text
사용자관리
권한관리
메뉴관리
배포 버튼
Session Kill
Cache Clear
Runtime Parameter 변경
```

---

# 25. FIG-IX-10 — pdmg-om Evidence Maturity

```text
[Expected Baseline]
pdmg-om
   │
   ▼
Operation / Control Plane

BUT

[Current Source Snapshot]
Boot Class       = UNKNOWN
Package          = UNKNOWN
Build            = UNKNOWN
Artifact         = UNKNOWN
Port             = UNKNOWN
DB               = UNKNOWN
Dashboard        = UNKNOWN
API              = UNKNOWN
Metric Collector = UNKNOWN

        ↓

Maturity = M1 / SPARSE
```

## 25.1 M1 작성 원칙

```text
Confirmed Fact
   ↓
Unknown Boundary
   ↓
Required Source
   ↓
Question
   ↓
Target Option
   ↓
Decision Gate
```

일반 OM 제품의 기능목록을 현행 PDMG 기능으로 복사하지 않는다.

---

# 26. pdmg-om Source 확보 Checklist

```text
Repository / Module 존재?
settings.gradle?
build.gradle?
Boot Application?
base package?
Controller?
Service?
DAO?
DB Schema?
UI/static?
Health?
Metric?
JMX?
Actuator?
Security?
Deployment?
```

이 증적이 확보되기 전 `pdmg-om` Current Architecture는 CONDITIONAL/HOLD다.

---

# 27. Observability Strategy `[NSIGHT FACT]`

NSIGHT 전략자료의 관측성 축:

```text
APM 기반 시스템 단위 관측
+
전사 통합로그
+
Trace-ID(GUID) 전문체계
```

의 목적은:

```text
전구간 거래추적
+
이상징후 사전통제
```

다.

---

# 28. Observability의 세 신호

일반 분류를 NSIGHT Evidence에 맞춰 사용하면:

```text
Logs
→ GUID / ServiceId / Error / SQL / ImageLog

Metrics
→ CPU / JVM / Thread / Worker / Hikari / DB

Traces
→ GUID 기반 End-to-End 거래 연결
```

OpenTelemetry 등 특정 제품/Protocol은 Source에 없으므로 확정하지 않는다.

---

# 29. FIG-IX-11 — Transaction Observability

```text
Request
   │
   ├─ GUID
   └─ ServiceId
   │
   ▼
DefaultFilter
   │ MDC
   ▼
Interceptor
   │ PRE ImageLog
   ▼
TCF
   │
   ├─ Request Thread
   └─ Worker Thread
         │
         ▼
      Handler
         ▼
      Facade
         ▼
      Service
         ▼
      DAO / SQL
         │
         ▼
   COMMIT / ROLLBACK
         │
         ▼
Response
   │
   ├─ Error Code
   ├─ Duration
   └─ POST/EX ImageLog
```

운영에서 이 그림을 한 GUID로 재구성할 수 있어야 한다.

---

# 30. Transaction Dashboard 질문

운영자는 다음 질문에 답할 수 있어야 한다.

```text
현재 TPS는?
p95는?
어느 ServiceId가 느린가?
어느 Error Code가 증가하는가?
Timeout 거래는?
Overload 거래는?
Rollback은?
어느 WAR/JVM인가?
어느 SQL인가?
```

현재 Dashboard가 구현되어 있다고 쓰지 않는다.

이는 **Observability Requirement**다.

---

# 31. ServiceId 중심 Metric

Metric Label 후보:

```text
serviceId
application
instance
result
errorCode
```

주의:

ServiceId cardinality가 관리 가능한 범위인지 확인해야 한다.

GUID를 Metric Label로 사용하면 고 Cardinality 문제가 생길 수 있으므로:

```text
GUID
→ Log/Trace

ServiceId
→ Metric
```

분리가 적절한 후보다.

`[PROPOSED]`.

---

# 32. FIG-IX-12 — JVM / Tomcat / Worker Monitoring

```text
VM
│
├─ CPU
├─ Memory
└─ Disk
   │
   ▼
JVM
│
├─ Heap
├─ Old Gen
├─ Metaspace
├─ GC Pause
├─ Full GC
└─ Thread Count
   │
   ▼
Tomcat
│
├─ maxThreads
├─ busyThreads
├─ connection
└─ accept queue
   │
   ▼
PDMG Online Worker
├─ active
├─ pool size
├─ queue depth
├─ rejected
├─ timeout
└─ late completion
```

## 32.1 핵심

Tomcat Thread와 PDMG Worker를 같은 그래프로 하나의 `Thread 사용률`로 합치지 않는다.

---

# 33. Capacity Design Threshold 후보

용량산정 화면설계 자료의 판정 예:

```text
VM당 산정 Thread
≤ maxThreads 70%
→ 정상 후보

70~85%
→ 주의

85% 초과
→ AP 증설/DB 병목 확인
```

또:

```text
Thread/DB Pool Ratio
4:1~8:1
→ 정상 후보

8:1~12:1
→ 주의

12:1 초과
→ DB Pool Wait / SQL 병목 가능
```

이 값은 **Capacity Design UI의 후보 기준**이다.

운영 Alert Threshold는 부하테스트 후 승인해야 한다.

---

# 34. JVM Monitoring

최소 관측:

```text
Heap Used
Heap Max
Old Gen
GC Count
GC Pause
Full GC
Metaspace
Thread Count
Class Loading
Process Uptime
```

Capacity 보고서는:

```text
GC Pause가 온라인 SLA를 침해하지 않을 수준
```

을 요구하지만 정확한 ms Threshold는 본 Evidence에서 확정하지 않는다.

---

# 35. Thread Dump Trigger

Capacity 화면설계는:

```text
WAITING/BLOCKED 지속
Runnable 과다
Thread Usage 고수준
```

에서 Thread Dump 확인을 운영가이드로 제시한다.

정확한 자동수집 Threshold/Tool은 `[OPEN]`.

---

# 36. FIG-IX-13 — Hikari / DB / Slow SQL Monitoring

```text
PDMG Worker
    │
    ▼
Hikari
┌─────────────────────────┐
│ active                  │
│ idle                    │
│ pending                 │
│ timeout                 │
└────────────┬────────────┘
             │
             ▼
DB Session
┌─────────────────────────┐
│ active session          │
│ wait                    │
│ lock                    │
│ SQL                     │
└────────────┬────────────┘
             │
             ▼
Slow SQL
├─ sqlId
├─ ServiceId
├─ elapsed
├─ row
└─ error
```

## 36.1 병목 구분

```text
Hikari pending 높음
+
DB CPU 낮음
→ Pool/Connection 설정 문제 후보

Hikari pending 높음
+
DB CPU/Wait 높음
→ DB/SQL 병목 후보
```

단순 Pool 확대부터 하지 않는다.

---

# 37. SQL Observability

최소 연결 후보:

```text
GUID
ServiceId
DAO/Mapper
SQL ID
Elapsed
Rows
Error
```

SQL Parameter 원문은 개인정보/민감정보 때문에 기본 Metric/Log로 남기지 않는다.

VI장의 Masking 정책과 연결한다.

---

# 38. Slow SQL Threshold

용량 예시자료에는 조회성 DB SQL p95 `2~5초` 통제 후보가 존재하지만 이는 **예시/설계자료**다.

현재 PDMG/NSIGHT 최종 Slow SQL Threshold로 확정하지 않는다.

`[OPEN-IX-02]`.

---

# 39. FIG-IX-14 — Timeout / Overload / Error Monitoring

```text
Incoming Request
       │
       ├─ Filter Reject
       │    ├─ 400
       │    └─ 401
       │
       ├─ TCF Handler Missing
       │
       ├─ Worker Queue Full
       │    └─ 503 / FW_OVERLOADED
       │
       ├─ Worker Deadline
       │    └─ 504 / FW_TIMEOUT
       │
       ├─ Business Error
       │
       └─ DB/System Error
              │
              ▼
       Error Class Metrics
```

## 39.1 반드시 별도 Metric

```text
filter_reject
authentication_fail
handler_not_found
overload
timeout
business_error
system_error
db_error
```

---

# 40. Timeout Monitoring의 핵심

V장에서 확인:

```text
HTTP 504
≠
Worker 종료
≠
DB Rollback 완료
```

따라서 최소:

```text
request_timeout_count
worker_late_completion_count
deadline_rollback_count
```

을 분리해야 한다.

현재 Metric 구현 여부는 `[UNKNOWN]`.

---

# 41. Overload Monitoring

PDMG Current Worker:

```text
pool = 20
queue = 100
```

Current Source 기준으로 Queue Reject는 중요한 운영신호다.

단, 이 값이 운영환경에서도 동일한지는 VIII장에서 `[OPEN]`.

운영에서는:

```text
active
queue depth
queue wait
rejected
```

를 관측한다.

---

# 42. Error Monitoring

VI장의 오류 계약과 연결:

```text
HTTP Status
+
stdErrCode
+
errType
+
ServiceId
```

가 필요하다.

특정 Error Message 문자열을 Alert 조건으로 삼는 것은 취약하다.

```text
Stable Error Code
```

를 기준으로 한다.

---

# 43. FIG-IX-15 — JWT / Security Monitoring

```text
Login
│
├─ success
└─ failure
   │
   ▼
Token
├─ issue
├─ refresh
├─ refresh reuse
├─ revoke
└─ denylist
   │
   ▼
Verification
├─ missing
├─ expired
├─ signature invalid
├─ wrong issuer/audience [TO-BE]
├─ unknown kid [TO-BE]
└─ revoked [TO-BE]
   │
   ▼
Security Alert / Audit
```

## 43.1 VII장에서 넘어온 Critical Signals

```text
RS256/HMAC mismatch
JWKS availability
same kid / different key
local auth bypass
multipart bypass
Header identity mismatch
Denylist disconnected
```

운영에서 탐지 가능해야 한다.

---

# 44. Secret / Key Monitoring

로그로 Secret을 보는 것이 아니라 상태만 본다.

가능:

```text
active kid
key age
JWKS health
rotation status
secret reference version
```

금지:

```text
private key
jwt secret
HMAC secret
token raw
```

---

# 45. FIG-IX-16 — GUID + ServiceId End-to-End Evidence

```text
User Request
   │
   ├─ GUID = G1
   └─ ServiceId = S1
   │
   ▼
WEB Access Log
   │
   ▼
PDMG Application Log
   │
   ├─ Request Thread
   └─ Worker Thread
   │
   ▼
TCF / Handler
   │
   ▼
Business
   │
   ▼
Mapper / SQL ID
   │
   ▼
DB
   │
   ▼
Transaction Outcome
   │
   ▼
Response / Error
   │
   ▼
ImageLog
```

## 45.1 운영 검색의 기본질문

```text
GUID G1이 어디에서 실패했나?
ServiceId S1의 p95가 왜 증가했나?
S1이 호출한 SQL은?
어느 JVM/WAR 인스턴스였나?
Worker Queue가 당시 포화였나?
Hikari Pending이 있었나?
```

---

# 46. GUID와 Metric

GUID는 거래별 고유값이므로 대량 Metric Label로 쓰는 것은 부적절할 수 있다.

권장 후보:

```text
Metric
→ ServiceId / App / Instance

Log/Trace
→ GUID
```

ServiceId 단위 Metric에서 이상을 발견하고 GUID Sample로 Drill-down한다.

---

# 47. ImageLog와 APM

VI장의 `TB_FW_IMAGE_LOG`:

```text
Request/Response/Error Evidence
```

APM:

```text
Runtime latency / topology / method / external call
```

성격이 다르다.

따라서:

```text
ImageLog
≠
APM
```

이다.

GUID로 연결할 수 있으면 상호 보완된다.

---

# 48. FIG-IX-17 — Alert → Diagnosis → Recovery

```text
Alert
  │
  ▼
Scope Classification
  │
  ├─ WEB?
  ├─ WAS/JVM?
  ├─ Worker?
  ├─ DB Pool?
  ├─ SQL?
  ├─ Security?
  └─ External?
  │
  ▼
Evidence
  │
  ├─ GUID
  ├─ ServiceId
  ├─ Metric
  ├─ Log
  ├─ Thread Dump
  ├─ GC Log
  ├─ SQL Evidence
  └─ ImageLog
  │
  ▼
Root Cause
  │
  ▼
Recovery
  │
  ├─ Traffic control
  ├─ Rollback
  ├─ Node removal
  ├─ DB recovery
  ├─ Key/JWKS recovery
  └─ Config restore
  │
  ▼
Verification
  │
  ▼
Postmortem / GAP / ADR
```

---

# 49. Runbook은 증상 중심으로 시작한다

예:

```text
증상: 504 증가
```

첫 조치:

```text
Tomcat 재기동
```

이 아니라:

```text
1. 어느 ServiceId?
2. Worker Queue?
3. late completion?
4. Hikari pending?
5. Slow SQL?
6. External call?
7. JVM GC?
```

를 본다.

---

# 50. 503 Runbook

```text
503 FW_OVERLOADED
   ↓
PDMG Queue Full?
   │
   ├─ Worker active max?
   ├─ queue depth?
   ├─ DB pool pending?
   ├─ SQL slow?
   └─ worker duration?
```

원인확인 없이 Worker Pool부터 확대하지 않는다.

---

# 51. 504 Runbook

```text
504 FW_TIMEOUT
   ↓
ServiceId
   ↓
Worker started?
   ↓
SQL/External?
   ↓
cancel requested?
   ↓
late completion?
   ↓
deadline rollback?
```

HTTP 504만 보고 DB 작업이 끝났다고 판단하지 않는다.

---

# 52. JVM High CPU Runbook

```text
CPU High
  │
  ├─ Runnable Thread?
  ├─ GC?
  ├─ Hot Method?
  ├─ Security Crypto/Login?
  ├─ Serialization?
  └─ Loop?
```

필요 증적:

```text
Thread Dump
GC Log
APM
JFR [제품/사용여부 TBD]
```

---

# 53. Hikari Exhaustion Runbook

```text
Hikari Pending
   ↓
Active Pool
   ↓
Connection Hold Time
   ↓
Slow SQL / Lock
   ↓
Worker Queue
   ↓
Timeout
```

Pool 확대 전 DB Session/SQL 병목을 검증한다.

---

# 54. JWT Error Runbook

```text
401 증가
   │
   ├─ Authorization missing?
   ├─ expired?
   ├─ signature?
   ├─ RS256/HMAC mismatch?
   ├─ JWKS?
   ├─ kid?
   └─ local/profile?
```

UI와 JWT Server만 보지 않고 Business `DefaultFilter`까지 연결한다.

---

# 55. FIG-IX-18 — Capacity Baseline vs Runtime Drift

```text
[Approved Capacity Baseline]
CPU
Memory
Heap
Tomcat Thread
Worker
Hikari
Session
Timeout
       │
       ▼ compare
[Actual Configuration]
VM
JVM Args
server.xml
application.yml
Hikari Config
Session Config
       │
       ▼ compare
[Runtime Evidence]
CPU
GC
Thread
Queue
Pool
TPS
p95
Error
       │
       ▼
PASS / DRIFT
```

## 55.1 Drift 유형

```text
Design Drift
Config Drift
Deployment Drift
Runtime Drift
Capacity Drift
```

---

# 56. Capacity Design Threshold와 Alert Threshold 분리

Capacity 문서:

```text
CPU 평균 60~70%, Peak 80% 이하 후보
Tomcat Max 근접 금지
Hikari 대기 통제
```

이는 Architecture/Capacity Design Reference다.

실제 Alert:

```text
CPU 80% 5분?
Heap 85%?
Queue 70%?
```

처럼 구체 Duration/Threshold가 필요하지만 현재 Evidence에서는 확정하지 않는다.

`[OPEN-IX-03]`.

---

# 57. Baseline Configuration Registry

최소 관리 후보:

```text
JVM
- Xms/Xmx
- GC

Tomcat
- maxThreads
- acceptCount
- maxConnections

PDMG
- tcf.enabled
- timeout.enabled
- timeout milliseconds
- worker pool
- queue

Hikari
- max pool
- connection timeout
- lifetime

Session
- idle timeout
- replication

Security
- issuer
- audience
- JWKS endpoint
- key id
```

실제 값은 X장/Config Baseline과 연결한다.

---

# 58. FIG-IX-19 — Deployment Architecture Drift

```text
Architecture Model
  │
  │ expects
  ▼
Host A
Tomcat-01
WAR-X
Config-V3
       │
       │ actual
       ▼
Runtime Inventory
Host A
Tomcat-02 ?
WAR-X-old ?
Config-V2 ?
       │
       ▼
DRIFT
```

## 58.1 탐지대상

```text
Wrong WAR
Wrong Version
Wrong JVM
Wrong Host
Wrong Config
Wrong Port
Wrong Datasource
Wrong Key ID
Wrong Session Policy
```

---

# 59. Runtime Inventory

배포 후 자동/반자동으로 최소:

```text
hostname
instance
PID
application
artifact version
git commit
config version
start time
health
```

를 수집할 수 있어야 한다.

현재 PDMG가 이 정보를 Health Endpoint로 노출한다고 Source 없이 쓰지 않는다.

`[PROPOSED]`.

---

# 60. Release Verification

배포 성공:

```text
Process Started
```

만으로 충분하지 않다.

최소:

```text
Health
Login/JWT
Representative ServiceId
DB Connection
Transaction Commit/Rollback
ImageLog
Error Mapping
Monitoring Registration
```

을 검증한다.

---

# 61. FIG-IX-20 — Release Evidence Package

```text
RELEASE-EVIDENCE/
│
├─ 01-source
│   ├─ commit
│   ├─ tag
│   └─ change-list
│
├─ 02-build
│   ├─ jdk
│   ├─ gradle
│   ├─ dependency
│   └─ result
│
├─ 03-test
│   ├─ unit
│   ├─ integration
│   ├─ security
│   └─ architecture
│
├─ 04-artifact
│   ├─ filename
│   ├─ version
│   └─ checksum
│
├─ 05-config
│   ├─ config-version
│   └─ secret-reference
│
├─ 06-deployment
│   ├─ environment
│   ├─ host/jvm
│   ├─ time
│   └─ approval
│
├─ 07-runtime
│   ├─ smoke
│   ├─ GUID trace
│   ├─ metric
│   └─ error-rate
│
└─ 08-rollback
    ├─ previous-release
    └─ verification
```

`[TO-BE/PROPOSED]`

---

# 62. Release Evidence의 핵심 목적

```text
"언제 배포했는가?"
```

만 아니라:

```text
무슨 Source를
어떤 Build 환경에서
어떤 Artifact로 만들고
어떤 Config와 함께
어느 서버에 배포했고
실제 Runtime이 정상인지
```

를 증명한다.

---

# 63. OM에서 보여야 할 것과 직접 바꾸면 안 되는 것

Target 후보:

```text
Read
- Runtime Status
- Transaction Status
- Config Baseline
- Release Evidence

Control
- 위험도가 낮고 승인된 운영조치
```

고위험:

```text
DB Pool 즉시 변경
JWT Key 즉시 변경
Transaction Timeout 임의 변경
ServiceId 정책 임의 변경
```

은 Change Approval/Audit 없이는 OM 버튼으로 직접 수행하지 않는다.

---

# 64. Runtime Parameter Change

동적 변경이 필요하다면:

```text
Who
When
Before
After
Reason
Approval
Rollback
```

이 모두 감사돼야 한다.

현재 PDMG에서 Runtime Parameter Dynamic Change 기능이 존재한다고 쓰지 않는다.

---

# 65. Configuration as Code

TO-BE 방향:

```text
Config
→ Versioned
→ Reviewed
→ Promoted
→ Audited
```

하지만 Secret은:

```text
Value 자체를 Git에 저장
```

하지 않는다.

Reference/metadata만 Version 관리한다.

---

# 66. Security in CI/CD

최소 Gate 후보:

```text
Secret Scan
Dependency Vulnerability
Static Code Scan
Artifact Integrity
Configuration Validation
Private Key/Token Leak Check
```

현재 Tool 이름은 확정하지 않는다.

NSIGHT Strategy가 `보안 취약성 점검 자동화`를 요구하는 근거는 있으나 구체 Scanner 제품은 별도 Decision이다.

---

# 67. Database Change Delivery

PDMG는 MyBatis/Oracle DB를 사용한다.

Application Release에 DB DDL/DML Migration이 포함되면:

```text
Source
→ DB Script
→ Approval
→ Execution
→ Verification
→ Rollback/Forward Fix
```

가 Artifact Release와 연계돼야 한다.

현재 DB Migration Tool은 `[UNKNOWN]`.

---

# 68. Schema Drift

검증 후보:

```text
Mapper expects Column A
BUT
Prod Schema missing A
```

Release 후에 발견하지 않도록:

```text
Schema version
Mapper/SQL validation
```

을 Gate에 포함한다.

---

# 69. Log Deployment

Log4j2 설정도 Application Behavior다.

다음 변경은 Release/Config Evidence에 포함한다.

```text
Log Level
Appender
Path
Pattern
Masking
Retention
```

특히 DEBUG/TRACE로 민감정보가 노출되지 않도록 Security Gate가 필요하다.

---

# 70. Observability Failure

Monitoring System 자체가 Down될 수 있다.

```text
Application healthy
BUT
Metric/Log collector down
```

을 Application Failure와 구분한다.

운영 목표:

```text
Monitoring Blindness Alert
```

도 필요하다.

현재 구현 여부는 `[UNKNOWN]`.

---

# 71. Alert Quality

나쁜 Alert:

```text
CPU 80%
```

좋은 운영상관 후보:

```text
ServiceId S1 p95↑
+
Worker Queue↑
+
Hikari Pending↑
+
SQL Q7 p95↑
```

즉 **Symptom + Dependency Evidence**를 연결한다.

---

# 72. Alert Storm

하나의 DB 장애가:

```text
SQL Alert
Hikari Alert
Worker Alert
Timeout Alert
HTTP Alert
```

를 동시에 발생시킬 수 있다.

TO-BE OM/APM은 Dependency를 바탕으로 Root Cause 후보를 묶는 방향이 필요하다.

현재 자동 Root Cause 기능이 있다고 주장하지 않는다.

---

# 73. Service Level View

운영 View의 상위단위 후보:

```text
Business / ServiceId
```

하위 Drill-down:

```text
ServiceId
→ Application
→ Instance
→ Thread
→ SQL / External
→ DB
```

Infrastructure Metric에서 Service View로 역추적 가능해야 한다.

---

# 74. WAR / Instance View

질문:

```text
어느 WAR가 가장 느린가?
어느 JVM가 GC 문제인가?
어느 Instance에서 오류가 증가하는가?
```

VIII장의:

```text
Host→JVM→WAR
```

Inventory가 Observability Label과 연결돼야 한다.

---

# 75. HA/DR Monitoring

최소 Target:

```text
L4 Pool Member
WEB Node
WAS Node
DB Role
JWT/JWKS
Refresh/Denylist DB
DR Readiness
```

DR 준비상태는:

```text
"DR 서버가 존재"
```

보다:

```text
Last DR Test
Config Sync
Artifact Sync
Key Sync
DB Replication
Runbook Readiness
```

가 중요하다.

---

# 76. DR Runtime Evidence

DR Test 후 최소:

```text
Failover start/end
Traffic route
Login
JWT verify
Representative ServiceId
DB read/write
Session behavior
External Interface
Failback
```

증적을 남긴다.

RPO/RTO 최종값은 VIII에서 `[GAP]`.

---

# 77. FIG-IX-21 — Operations / Runtime Evidence Gate

```text
Release Candidate
    │
    ▼
[G1 Build Gate]
Compile / Test / Artifact
    │
    ▼
[G2 Deployment Gate]
Config / Approval / Target
    │
    ▼
[G3 Smoke Gate]
Health / Login / Service / DB
    │
    ▼
[G4 Runtime Gate]
p95 / Error / Thread / Worker / Hikari / JVM
    │
    ▼
[G5 Trace Gate]
GUID / ServiceId / ImageLog / SQL
    │
    ▼
[G6 HA/Security Gate]
JWT / Node Failover / Security
    │
    ▼
Baseline Release
```

실제 프로젝트 Gate ID와 동일하다고 주장하지 않고 본 정의서의 운영 Gate 후보로 사용한다.

---

# 78. Runtime Evidence 없는 PASS 금지

```text
Build Success
+
Deployment Success
```

만으로:

```text
Architecture PASS
```

를 주지 않는다.

최소 Runtime Evidence가 필요하다.

---

# 79. Architecture Governance Closed Loop

```text
Requirement
   ↓
Architecture
   ↓
ADR
   ↓
Source / Config
   ↓
Build / Test
   ↓
Deployment
   ↓
Runtime Evidence
   ↓
Drift
   ↓
GAP / Risk
   ↓
ADR / Standard Update
   └────────────────────────────↺
```

이 구조는 NSIGHT Master Governance Baseline과 일치한다.

---

# 80. Observability Maturity Model

## M1 — Log Exists

```text
서버 로그는 있음
GUID/ServiceId 연결 불완전
```

## M2 — Correlated

```text
GUID + ServiceId
App/Image/SQL 연결
```

## M3 — Metric Integrated

```text
ServiceId
→ JVM/Thread/Pool/DB Metric
```

## M4 — Operational Closed Loop

```text
Alert
→ Runbook
→ Recovery
→ Evidence
→ Drift/GAP
```

현재 PDMG는 Source상 GUID/MDC/ImageLog 등 M1~M2의 중요한 기반을 가진다.

M3/M4를 전체 구현했다고 볼 Evidence는 없다.

---

# 81. Current Evidence vs Target

| 영역 | Current PDMG | NSIGHT Target |
|---|---|---|
| GUID | 있음 | End-to-End Trace |
| ServiceId | 있음 | Metric/Trace 중심키 |
| MDC | 있음 | 통합로그 Correlation |
| ImageLog | 있음 | Audit/Transaction Evidence |
| Log4j2 | 있음 | 전사 통합로그 연결 |
| Worker Metric | 필요, 구현 Evidence 미확인 | Dashboard |
| Hikari Metric | 필요, 구현 Evidence 미확인 | Dashboard |
| JVM/APM | PDMG Source 구현 미확인 | APM |
| pdmg-om | UNKNOWN | Control Plane |
| GitLab | PDMG Pipeline 미확인 | SCM |
| Runner | PDMG Job 미확인 | 개발 CI/CD |
| eCAMS | PDMG Job 미확인 | 운영 배포 |

---

# 82. GAP Register

| ID | GAP | 영향 |
|---|---|---|
| GAP-IX-01 | PDMG 실제 `.gitlab-ci.yml`/CI Job Evidence 미확보 | CI |
| GAP-IX-02 | PDMG와 eCAMS 운영배포 Job/절차 Mapping 미확보 | CD |
| GAP-IX-03 | PDMG Module별 정확한 Artifact/Version/Checksum 정책 미확정 | Release |
| GAP-IX-04 | Build Once / Promote Artifact 실제 적용여부 미확정 | Binary Drift |
| GAP-IX-05 | 환경별 Config/Secret Promotion 체계 미확정 | Config Drift |
| GAP-IX-06 | `pdmg-om` Current Source/Runtime/Function 미확인 | OM |
| GAP-IX-07 | PDMG Worker Metric Export 구현 미확인 | Timeout/Overload |
| GAP-IX-08 | Hikari/JVM/Tomcat Metric Export 및 Dashboard 구현 미확인 | Performance |
| GAP-IX-09 | GUID+ServiceId→SQL/APM End-to-End Correlation 미검증 | Observability |
| GAP-IX-10 | 504 late worker/deadline rollback Metric 미구현/미확인 | Transaction |
| GAP-IX-11 | JWT/JWKS/Denylist Security Monitoring 구현 미확인 | Security |
| GAP-IX-12 | ImageLog Failure Alert/Recovery 미확정 | Audit |
| GAP-IX-13 | Production Alert Threshold 승인본 미확정 | Operations |
| GAP-IX-14 | Alert→Runbook 공식 연결 미확정 | Incident |
| GAP-IX-15 | Node/Center DR Runbook Runtime Evidence 미확보 | DR |
| GAP-IX-16 | Release Evidence Package 표준 미확정 | Governance |
| GAP-IX-17 | Artifact/Config/Runtime Inventory Drift 자동탐지 미구현 | Drift |
| GAP-IX-18 | Runtime Parameter 변경 감사정책 미확정 | Change Control |
| GAP-IX-19 | DB Schema Migration Tool/Process 미확정 | DB Release |
| GAP-IX-20 | Observability Product/APM/SIEM 최종 배치 미확정 | Operations |

---

# 83. RISK Register

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-IX-01 | Strategy Tool을 Current PDMG Pipeline으로 오인 | High |
| RISK-IX-02 | PDMK Build 정보를 PDMG에 복제 | High |
| RISK-IX-03 | Dev/Test/Prod 환경별 재빌드로 Binary Drift | High |
| RISK-IX-04 | Production Secret/Key가 Source/Artifact에 포함 | Critical |
| RISK-IX-05 | pdmg-om 기능을 근거 없이 현행으로 확정 | High |
| RISK-IX-06 | Tomcat/Worker/Hikari Metric을 하나로 혼합 | High |
| RISK-IX-07 | 504 발생 후 Worker/DB 상태 미관측 | Critical |
| RISK-IX-08 | Queue/Pool 증상에 자원만 확대 | High |
| RISK-IX-09 | GUID 없는 로그로 장애추적 단절 | High |
| RISK-IX-10 | Error Message 문자열 기반 Alert | Medium |
| RISK-IX-11 | Runtime DEBUG로 Token/개인정보 노출 | Critical |
| RISK-IX-12 | Monitoring Blindness 미탐지 | High |
| RISK-IX-13 | eCAMS 성공=서비스정상으로 판단 | High |
| RISK-IX-14 | DB Schema와 WAR Rollback 비호환 | Critical |
| RISK-IX-15 | DR Artifact/Config/Key Drift | Critical |
| RISK-IX-16 | 임계치 과다로 Alert Storm | Medium/High |
| RISK-IX-17 | Metric Cardinality에 GUID 사용 | High |
| RISK-IX-18 | 운영파라미터 무승인 동적변경 | Critical |

---

# 84. OPEN Issue

| ID | 질문 |
|---|---|
| OPEN-IX-01 | 운영 Deployment 방식은 Rolling/Blue-Green/기타 중 무엇인가 |
| OPEN-IX-02 | Slow SQL 최종 Threshold는 무엇인가 |
| OPEN-IX-03 | Production Alert Threshold/Duration은 누가 승인하는가 |
| OPEN-IX-04 | PDMG Pipeline 파일과 Job은 어디에 있는가 |
| OPEN-IX-05 | Artifact Repository/Promotion SSOT는 무엇인가 |
| OPEN-IX-06 | PDMG Module별 Production Artifact 타입은 무엇인가 |
| OPEN-IX-07 | eCAMS와 GitLab Release ID를 어떻게 연결하는가 |
| OPEN-IX-08 | Config Repository/Version 체계는 무엇인가 |
| OPEN-IX-09 | Secret/Key Store 운영제품과 CI/CD 연계는 무엇인가 |
| OPEN-IX-10 | pdmg-om 실제 Source는 존재하는가 |
| OPEN-IX-11 | OM과 기존 전사 운영도구의 책임분담은 무엇인가 |
| OPEN-IX-12 | APM 제품/Agent 설치 범위는 무엇인가 |
| OPEN-IX-13 | 통합로그 수집 플랫폼과 보존기간은 무엇인가 |
| OPEN-IX-14 | Metric Export 방식을 무엇으로 할 것인가 |
| OPEN-IX-15 | Worker late completion을 어떻게 계측할 것인가 |
| OPEN-IX-16 | JWT/JWKS/Denylist 상태를 어느 운영화면에서 볼 것인가 |
| OPEN-IX-17 | Runtime Config 자동수집 범위는 어디까지인가 |
| OPEN-IX-18 | DR Readiness를 어떤 주기로 검증하는가 |
| OPEN-IX-19 | Release Evidence Package 저장소는 무엇인가 |
| OPEN-IX-20 | Architecture Runtime Gate의 공식 승인자는 누구인가 |

---

# 85. ADR 후보

| ADR | 결정 주제 |
|---|---|
| ADR-IX-01 | PDMG CI Pipeline 표준 |
| ADR-IX-02 | Build Once / Promote Artifact |
| ADR-IX-03 | Artifact Version / Commit / Checksum Identity |
| ADR-IX-04 | Config Promotion / Configuration as Code |
| ADR-IX-05 | Secret / Key CI/CD Boundary |
| ADR-IX-06 | PDMG OM Target Scope |
| ADR-IX-07 | APM / Metric / Integrated Log 책임분담 |
| ADR-IX-08 | ServiceId Metric Label 표준 |
| ADR-IX-09 | GUID Trace Storage/Correlation |
| ADR-IX-10 | Timeout/Overload Metric 표준 |
| ADR-IX-11 | Alert Threshold Governance |
| ADR-IX-12 | Runbook Standard |
| ADR-IX-13 | Release Evidence Package |
| ADR-IX-14 | Database Change Delivery |
| ADR-IX-15 | Deployment Rollback Strategy |
| ADR-IX-16 | Runtime Config / Deployment Drift Detection |
| ADR-IX-17 | Operations Gate / Runtime Evidence Gate |
| ADR-IX-18 | DR Release Synchronization |

---

# 86. DevOps Architecture Rules

## 86.1 Must

1. GitLab/GitLab Runner/eCAMS의 NSIGHT 전략상 역할과 PDMG 실제 Pipeline 구현을 구분한다.
2. PDMK Build 구조를 PDMG Current Build로 복사하지 않는다.
3. `pdmg-om`의 Source 없는 Dashboard 기능을 AS-IS로 쓰지 않는다.
4. Source Commit에서 Artifact를 역추적할 수 있어야 한다.
5. 동일 Release의 Artifact/Config/Deployment Target을 기록한다.
6. Production Secret/Private Key를 Source에 저장하지 않는다.
7. Token/Secret을 Build/Deployment Log에 남기지 않는다.
8. 운영 Deployment 성공만으로 Runtime PASS를 선언하지 않는다.
9. Rollback 시 DB/Config 호환성을 함께 검토한다.
10. Build Candidate와 Runtime Artifact가 동일한지 검증한다.
11. 실제 Config와 Capacity Baseline Drift를 검증한다.
12. GUID와 ServiceId를 Runtime Evidence 핵심키로 유지한다.
13. Tomcat Thread/PDMG Worker/Hikari를 서로 다른 Resource Pool로 관측한다.
14. 503/504/401/Business/System Error를 분리한다.
15. HTTP Timeout과 Worker/DB 종료를 동일 Metric으로 다루지 않는다.
16. GUID를 Metric 고 Cardinality Label로 무분별하게 사용하지 않는다.
17. Production Error/Debug Log에 Token/민감정보를 노출하지 않는다.
18. Runtime Evidence 없이 Architecture Release Gate에 PASS를 주지 않는다.

## 86.2 Should

1. Build Once → Promote Artifact를 사용한다.
2. Config를 Version/Review/Promotion 가능한 형태로 관리한다.
3. Release Evidence Package를 자동 생성한다.
4. ServiceId 기준으로 APM/Metric/Log를 연결한다.
5. Worker active/queue/reject/timeout/late completion을 계측한다.
6. Hikari active/idle/pending/timeout을 계측한다.
7. JWT verify/JWKS/denylist/refresh 상태를 운영계측한다.
8. Alert를 Runbook과 연결한다.
9. Runtime Inventory를 자동수집한다.
10. Capacity/Config/Runtime Drift를 자동탐지한다.
11. DR Test Evidence를 Baseline Release에 연결한다.
12. Incident Postmortem 결과를 GAP/ADR로 환류한다.

---

# 87. Monitoring Requirement Matrix

| 영역 | 최소 Metric/Evidence | 핵심 질문 |
|---|---|---|
| WEB | request/status/proxy latency | WEB인가 WAS인가 |
| Tomcat | busy/max/connection/queue | Request Thread 포화인가 |
| Worker | active/queue/reject/timeout | 업무 Worker 병목인가 |
| JVM | heap/GC/metaspace/thread | JVM 자원문제인가 |
| Hikari | active/idle/pending/timeout | DB Connection 병목인가 |
| DB | session/wait/SQL/lock | SQL/DB 문제인가 |
| Transaction | commit/rollback/deadline | 거래 결과는 무엇인가 |
| Error | status/code/type | 어떤 오류인가 |
| Security | login/JWT/JWKS/deny | 인증 문제인가 |
| ImageLog | pre/post/ex/failure | 감사증적이 남았는가 |
| Deploy | release/version/config | 무엇이 배포됐는가 |
| HA/DR | member/failover/readiness | 장애복구 가능한가 |

---

# 88. Runtime Test Scenario

## 88.1 Release Smoke

```text
Deploy
→ Health
→ Login/JWT
→ Representative ServiceId
→ DB
→ ImageLog
→ Metric
```

## 88.2 503 Overload

```text
Worker/Queue Saturation
Expect
  503
  reject metric
  no DB transaction for rejected task
```

## 88.3 504 Timeout

```text
Slow SQL
Expect
  request timeout
  worker late metric
  deadline rollback
```

## 88.4 Hikari Exhaustion

```text
Connection Pool pressure
Expect
  pending↑
  worker duration↑
  alert correlation
```

## 88.5 GC Pressure

```text
Heap pressure
Expect
  GC pause evidence
  p95 correlation
```

## 88.6 JWT Error

```text
invalid/expired token
Expect
  auth metric
  no raw token log
```

## 88.7 Deployment Drift

```text
expected artifact A
actual artifact B
Expect
  deployment drift
```

## 88.8 Config Drift

```text
approved worker=20
actual worker=X
Expect
  config drift
```

현재 승인값이 20이라고 확정하는 의미가 아니라 검증 예다.

## 88.9 Node Failure

```text
WAS node down
Expect
  L4 member removal
  service continuity
  incident evidence
```

## 88.10 DR Test

```text
Main→DR
Expect
  artifact/config/key/db consistency
  business runtime evidence
```

---

# 89. Architecture Conformance Rule 후보

```text
RULE-IX-01
Every release artifact must map to one source commit

RULE-IX-02
Every production deployment must have approval evidence

RULE-IX-03
Every runtime instance must expose application/release identity

RULE-IX-04
Production secrets must not exist in source or artifact

RULE-IX-05
Every ServiceId transaction log must include GUID and ServiceId

RULE-IX-06
Worker pool runtime must be observable

RULE-IX-07
Hikari pending/timeout must be observable

RULE-IX-08
Timeout response and deadline rollback must be separately observable

RULE-IX-09
JWT raw token must never be logged

RULE-IX-10
Runtime configuration must be comparable with approved baseline

RULE-IX-11
Every critical alert must map to a runbook

RULE-IX-12
Every production release must produce smoke/runtime evidence

RULE-IX-13
DR production-equivalent artifact/config must be traceable

RULE-IX-14
pdmg-om capabilities cannot be marked AS-IS without source/runtime evidence

RULE-IX-15
Architecture PASS requires runtime evidence
```

---

# 90. X장 Handoff

IX장에서 확보한 다음 데이터를 X장 Closed Loop의 Runtime Evidence로 넘긴다.

```text
Source Commit
Artifact
Config
Host / JVM / WAR
GUID
ServiceId
Handler
SQL ID
Runtime Result
Error
Metric
Deployment Version
```

---

# 91. FIG-IX-22 — X장 Handoff

```text
IX. DevOps / OM / Observability
      │
      ├─ Source Commit
      ├─ Build / Artifact
      ├─ Release / Config
      ├─ Runtime Instance
      ├─ GUID
      ├─ ServiceId
      ├─ Metric / Log
      ├─ SQL Evidence
      ├─ Drift
      └─ GAP / ADR
              │
              ▼
X. Naming / ServiceId / Traceability / Closed Loop
              │
              ├─ Business Taxonomy
              ├─ ServiceId Anatomy
              ├─ Package
              ├─ Handler
              ├─ Facade
              ├─ Service
              ├─ DAO
              ├─ Mapper / SQL
              ├─ DB/Table
              ├─ WAR/JVM/Host
              ├─ Runtime Evidence
              └─ Architecture Gate
```

## X장에서 반드시 답할 질문

1. `MG/CO/A/...` 업무분류는 ServiceId에 어떻게 투영되는가?
2. ServiceId는 Handler/Facade/Service/DAO/Mapper와 어떻게 연결되는가?
3. 화면 Program과 ServiceId는 어떻게 연결되는가?
4. DAO Method는 어떤 Mapper SQL ID를 실행하는가?
5. SQL은 어떤 Table/View를 사용하는가?
6. ServiceId는 어느 WAR/JVM/Host에서 실행되는가?
7. Runtime GUID/Metric/Error는 어느 ServiceId Source와 연결되는가?
8. Requirement/ADR이 어떤 Code/Config/Test에 반영됐는가?
9. Document와 Source가 다르면 어떻게 Drift를 찾는가?
10. Architecture Gate G00~G90에서 무엇을 자동검증할 수 있는가?
11. GAP→ADR→Rule→Conformance Test를 어떻게 닫는가?
12. 최종 Baseline Release는 어떤 Evidence Bundle을 가져야 하는가?

---

# 92. 검증 체크리스트

## 92.1 DevOps

- [x] GitLab/Runner/eCAMS 역할을 Source대로 구분했는가
- [x] PDMG 실제 Pipeline과 NSIGHT Strategy를 구분했는가
- [x] Gradle/Java/Spring Boot PDMG AS-IS가 표시되는가
- [x] Artifact 정확값 미확정을 숨기지 않았는가
- [x] Build Once/Promote를 TO-BE로 구분했는가

## 92.2 Config / Security

- [x] Config/Secret/Key 경계가 있는가
- [x] Production Private Key Source 저장을 금지했는가
- [x] Token/Secret 로그 금지가 있는가
- [x] 환경별 Config Drift를 다뤘는가

## 92.3 OM

- [x] pdmg-om Current UNKNOWN을 유지했는가
- [x] 일반 OM 기능을 AS-IS로 창작하지 않았는가
- [x] Control Plane과 Execution Plane을 구분했는가

## 92.4 Observability

- [x] APM/통합로그/GUID Strategy를 반영했는가
- [x] GUID+ServiceId Trace가 있는가
- [x] Tomcat/Worker/Hikari를 구분했는가
- [x] SQL/DB 병목 연결이 있는가
- [x] 503/504/JWT/Error가 구분되는가
- [x] ImageLog와 APM을 구분했는가

## 92.5 Operations

- [x] Alert→Diagnosis→Recovery가 있는가
- [x] Capacity 후보값을 실제 Alert Threshold로 승격하지 않았는가
- [x] Release Evidence Package가 있는가
- [x] Runtime Evidence Gate가 있는가
- [x] Drift가 X장으로 연결되는가

---

# 93. Completion Gate

```text
Figure Plan                         22
실제 Text Figure                   22

DevOps Big Picture                 PASS
Standardization Loop               PASS
Source→Build→Deploy                PASS
GitLab/Runner/eCAMS                PASS
PDMG Build Boundary                CONDITIONAL
Config/Secret                      PASS
Environment Promotion              PROPOSED
Release/Rollback                   PROPOSED
OM Position                        PASS
pdmg-om Evidence                   PASS(UNKNOWN 명시)
Transaction Observability          PASS
JVM/Tomcat/Worker                  PASS
Hikari/DB/SQL                      PASS
Timeout/Error                      PASS
JWT/Security                       PASS
GUID/ServiceId E2E                 PASS
Alert/Runbook                      PROPOSED
Capacity Drift                     PASS
Deployment Drift                   PROPOSED
Release Evidence                   PROPOSED
Operations Gate                    PROPOSED
X Handoff                          PASS

PDMK Build→PDMG 복사                0건
pdmg-om 기능 AS-IS 창작             0건
GitLab Pipeline 구현완료 창작        0건
Alert Threshold 임의확정             0건
```

**판정: CONDITIONAL PASS**

## PASS 전환 조건

```text
Condition-IX-01
PDMG current build.gradle/settings.gradle/Wrapper 전체 재스캔

Condition-IX-02
PDMG 실제 GitLab Pipeline/Runner Job Evidence 확보

Condition-IX-03
eCAMS Production Deployment Mapping/Runbook 확보

Condition-IX-04
Module별 Artifact/Version/Checksum Release Identity 확정

Condition-IX-05
Config/Secret Promotion 체계 확정

Condition-IX-06
pdmg-om Source Snapshot 확보 및 Runtime 분석

Condition-IX-07
Tomcat/Worker/Hikari/JVM Metric Export Runtime Evidence 확보

Condition-IX-08
GUID+ServiceId→SQL/APM End-to-End Test

Condition-IX-09
Timeout late worker / deadline rollback Metric 구현 또는 검증

Condition-IX-10
JWT/JWKS/Denylist Monitoring 구현/연계

Condition-IX-11
Production Alert Threshold 승인

Condition-IX-12
Critical Alert→Runbook Mapping

Condition-IX-13
Release Evidence Package 표준 승인

Condition-IX-14
Config/Deployment Drift Detection 구현

Condition-IX-15
DR Release/Runtime Evidence 확보
```

---

# 94. 장 최종 평가

IX장은 NSIGHT/PDMG Architecture를 **“운영 가능한 Architecture”**로 전환하는 장이다.

가장 중요한 결론은 다음과 같다.

> **NSIGHT 전략에서 DevOps는 GitLab 형상관리, GitLab Runner 개발배포, eCAMS 운영배포, IaaS 환경 일관성으로 정의되며 표준을 구축 이후에도 유지하기 위한 Engineering Factory다.**

> **그러나 이 전략 Tool이 PDMG Current Repository의 실제 CI/CD Pipeline으로 구현됐다고 자동으로 판단할 수 없다. PDMG 현재 Source에서 확정되는 것은 Java 21, Spring Boot 3.5.14, Gradle 멀티프로젝트와 공통 FW/업무/JWT/UI 구조다.**

> **`pdmg-om`은 현재 구현 Evidence가 없으므로 일반 운영 Dashboard 기능을 현행으로 작성하지 않았으며, OM은 Target Control Plane으로 분리했다.**

> **Observability는 NSIGHT 전략상 APM + 전사 통합로그 + GUID Trace를 중심으로 하고, PDMG의 GUID/ServiceId/MDC/ImageLog/Timeout/Error가 실제 Source Evidence를 제공한다.**

> **운영자가 보는 최종 단위는 단순 Server CPU가 아니라 `ServiceId → Application/JVM → Worker → Hikari → SQL/DB → Response`의 원인 연쇄여야 한다.**

> **Tomcat Thread, PDMG Worker, Hikari Pool을 구분해 관측하고, 503 Overload와 504 Timeout, JWT 401, Business/System/DB Error를 각각 다른 실패계열로 관리해야 한다.**

> **Deployment 완료는 Runtime 정상의 증명이 아니다. Source Commit, Artifact, Config, 승인, Deployment, Smoke Test, GUID Trace, Metric까지 연결된 Release Evidence가 있어야 Architecture Gate를 통과할 수 있다.**

> **최종적으로 NSIGHT Architecture는 문서에서 끝나지 않고 `Architecture → Source → Build → Deploy → Runtime Evidence → Drift → GAP/ADR → New Baseline`으로 다시 돌아오는 Closed Loop를 가져야 한다.**

다음 X장은 이 Closed Loop를 가장 세밀한 추적축인 **ServiceId**를 중심으로 완성한다.

```text
IX
DevOps / Runtime Evidence
        ↓
X
Business Code
→ ServiceId
→ Package
→ Handler
→ Facade
→ Service
→ DAO
→ Mapper / SQL
→ Table
→ WAR / JVM / Host
→ Runtime Evidence
→ Drift / Gate
```
