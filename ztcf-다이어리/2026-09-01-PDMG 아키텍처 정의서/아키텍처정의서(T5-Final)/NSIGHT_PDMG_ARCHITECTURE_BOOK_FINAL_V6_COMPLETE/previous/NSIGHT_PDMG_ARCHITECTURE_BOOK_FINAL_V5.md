# NSIGHT / PDMG ARCHITECTURE BOOK
# FINAL V5 — FINAL ARCHITECTURE DEFINITION EDITION

> 기준일: `2026-09-01`  
> 문서 Edition: **FINAL V5**  
> Architecture Definition: **CONDITIONAL PASS**  
> Current PDMG Conformance: **PARTIAL / GAP**  
> Runtime Evidence Coverage: **MEDIUM**  
> HG90: **OPEN — Critical Evidence Closure 후 Release**

---

# 0. Executive Message

이 정의서는 PDMG를 Source Code 목록이나 서버 구성표로만 설명하지 않는다. **왜 이런 구조가 필요한가 → 어떤 책임과 경계로 분리되는가 → 실제 거래가 어떻게 움직이는가 → 실패하면 어디에서 끊기는가 → 무엇으로 그 구조를 증명하는가**를 하나의 Architecture Story로 연결한다.

```text
WHY
 ↓
PARADIGM
 ↓
METHOD
 ↓
BIG PICTURE
 ↓
LOGICAL
 ↓
PHYSICAL
 ↓
HA / DR
 ↓
MECHANISM
 ↓
RUNTIME
 ↓
DATA
 ↓
MARKETING
 ↓
BI
 ↓
STANDARDIZATION / EVIDENCE
 ↓
HG90 ARCHITECTURE BASELINE
```

# 0.1 각 장의 완전한 단위

```text
Story / Question
      ↓
Whole Architecture
      ↓
Top-down Drill-down
      ↓
Runtime / Failure / Security
      ↓
Normal / Forbidden Pattern
      ↓
Architecture Decision
      ↓
Source / Config / Runtime Evidence
      ↓
PASS / GAP / ADR
      ↓
Next Chapter
```

Evidence는 책 뒤의 별도 Architecture가 아니다. **각 장의 Architecture가 실제 Source·Config·Runtime에서 존재하는지 바로 그 장에서 검증한다.** Appendix에는 전수 Registry와 Inventory처럼 본문을 방해하는 Raw Reference만 둔다.

# 0.2 Master Architecture

```text
USER / CHANNEL
      ↓
UI DELIVERY
pdmg-ui
      ↓
AUTHENTICATION
pdmg-jwt
      ↓
APPLICATION RUNTIME
pdmg-service
      │
      ├─ pdmg-fw
      │   ├─ Filter / Context
      │   ├─ Security / TCF
      │   ├─ Worker / Timeout
      │   ├─ Transaction
      │   └─ Error / Logging
      │
      └─ Business
          Handler / Controller
                ↓
              Facade
                ↓
              Service
                ↓
               DAO
                ↓
              Mapper
                ↓
             RDW / DB

NSIGHT TARGET EXTENSION
RDW → CDC / ETL → ADW → BI
Marketing → Event / Kafka / Real-time Decision

PHYSICAL
GSLB → L4 → Apache → Tomcat/JVM → WAR → DB

EVIDENCE
SourceCommit → BuildId → ArtifactHash → DeploymentId
→ ServiceId → GUID/Trace → Runtime Evidence
→ Drift → GAP/ADR → Baseline
```

# 0.3 Final Architecture Principles

| Rule | 원칙 |
|---|---|
| R-01 | PDMG Current와 NSIGHT Target을 분리한다. |
| R-02 | Build Module, Process, JVM, Logical Node, Physical Server를 동일시하지 않는다. |
| R-03 | Framework는 HOW safely execute, Business는 WHAT to execute를 책임진다. |
| R-04 | ServiceId를 Business→Source→Runtime Trace의 핵심 식별자로 사용한다. |
| R-05 | TCF ON/OFF의 Business Use Case Boundary는 Common Facade로 정렬한다. |
| R-06 | Cross-system Direct DB DML/DB-Link를 기본 금지한다. |
| R-07 | DB Query Timeout < Worker/Transaction Deadline < Downstream/Server < Client Timeout 계층을 지향한다. |
| R-08 | RDW Operational Workload와 ADW Analytical Workload를 분리한다. |
| R-09 | Authentication 결과와 Business User Context를 명시적으로 Bind한다. |
| R-10 | Architecture는 Source/Deployment/Runtime Evidence로 닫고 Drift는 ADR로 갱신한다. |

# 0.4 Current / Target

```text
PDMG
= Current / Source / Config / Runtime Evidence

NSIGHT
= Target / Strategy / Alignment Reference

PDMG AS-IS
        ≠
NSIGHT TO-BE

Mapping / Promotion
= Registry + ADR + Evidence
```

# 0.5 Final Status

```text
Document Edition
      ↓
FINAL V5

Architecture Definition
      ↓
CONDITIONAL PASS

Current PDMG
      ↓
PARTIAL / GAP

Runtime Evidence
      ↓
MEDIUM

HG90
      ↓
OPEN
```

`FINAL V5`는 **문서 편집 Edition의 완료**를 의미한다. Current Implementation 또는 HG90가 자동 PASS되었다는 의미는 아니다.


---

# NSIGHT PDMG 아키텍처 정의서
# 제1장. 왜 다시 짓는가
## Story: “이미 시스템은 있지만, Architecture는 하나로 보이지 않는다”


---

# 0. Opening Script

이 장에서는 새로운 기술을 고르지 않습니다. 먼저 왜 다시 Architecture를 정의해야 하는지부터 봅니다.

PDMG에는 이미 Source가 있고, Module이 있고, DB가 있고, 실제로 거래도 수행됩니다. 그런데 장애가 발생했을 때 “이 거래는 어느 Thread에서 실행됐는가”, “이 ServiceId는 어느 WAR와 JVM에서 실행됐는가”, “504가 발생한 뒤 DB 작업은 실제로 끝났는가”를 하나의 구조로 설명하기는 어렵습니다.

그래서 이번 Architecture 정의의 출발점은 시스템을 새로 상상하는 것이 아니라, **이미 존재하는 Source·Runtime·Infrastructure·Security·Data·Operations를 하나의 책임과 Evidence 체계로 연결하는 것**입니다.

## FIG-01-01. 장 전체 Architecture

```text
현재 보이는 것
Module / Source / Server / Data
각각 존재
        ↓
하지만 연결이 약함
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

이 그림은 이번 정의서의 출발점을 보여줍니다. 현재 PDMG에는 Module·Source·DB·Runtime이 각각 존재하지만, 그것들이 하나의 Architecture Chain으로 연결되어야 장애와 변경의 영향까지 설명할 수 있습니다. 따라서 1장에서는 새로운 제품을 정하지 않고, **Current Fact를 Responsibility와 Runtime, Evidence로 다시 연결하는 이유**를 먼저 고정합니다.


---

# 1. Inventory와 Architecture의 차이

## FIG-01-02. Inventory와 Architecture의 차이

```text
Inventory
pdmg-ui / pdmg-jwt / pdmg-fw / pdmg-service
Tomcat / MyBatis / DB

        VS

Architecture
User
 ↓
UI / Auth
 ↓
Runtime Control
 ↓
Business
 ↓
Data
 ↓
Evidence
```

이 그림에서 먼저 봐야 할 것은 **목록과 구조의 차이**입니다. Inventory는 “무엇이 있는가”를 알려주지만, Architecture는 “누가 무엇을 책임지고, 어떤 순서로 실행되며, 실패하면 어디까지 영향을 주는가”를 설명해야 합니다.

예를 들어 `pdmg-fw`가 존재한다는 사실만으로는 이것이 별도 서버인지, `pdmg-service`와 같은 Spring Context에 들어가는 Framework Module인지 알 수 없습니다. 그래서 Architecture는 이름이 아니라 **책임과 Runtime 관계**로 다시 읽어야 합니다.

이 차이를 고정해야 뒤에서 Module, Logical Node, Physical Node를 혼동하지 않게 됩니다.


---

# 2. Module을 Responsibility로 다시 읽기

## FIG-01-03. Module을 Responsibility로 다시 읽기

```text
pdmg-ui      → UI Delivery
pdmg-jwt     → Authentication / Token
pdmg-service → Business Runtime
pdmg-fw      → Framework Runtime Control
pdmg-om      → Operations [UNKNOWN CURRENT]
```

여기서부터 PDMG를 단순 Module 집합이 아니라 **책임의 집합**으로 다시 읽습니다.

`pdmg-ui`는 화면과 요청 조립, `pdmg-jwt`는 인증과 토큰, `pdmg-service`는 업무 실행, `pdmg-fw`는 Filter·Context·TCF·Timeout·Transaction 같은 공통 Runtime Control을 담당합니다. `pdmg-om`은 기준에는 존재하지만 현재 Source/Runtime 범위가 충분히 확인되지 않았으므로 `[UNKNOWN]`을 유지합니다.

이렇게 책임을 먼저 고정하면 Module을 Physical Server로 바로 내려보내는 오류를 피할 수 있습니다.


---

# 3. Source와 Runtime은 다른 그림이다

## FIG-01-04. Source와 Runtime은 다른 그림이다

```text
SOURCE VIEW
Controller / Handler
 ↓
Facade
 ↓
Service
 ↓
DAO
 ↓
Mapper

        VS

RUNTIME VIEW
Filter → Security → MVC → TCF
→ Worker → TX → Handler
→ Facade → Service → DAO → DB
```

Source Layer Diagram은 코드 구조를 설명하고, Runtime Diagram은 **거래 한 건이 실제로 지나가는 시간순서**를 설명합니다. 둘은 같은 그림이 아닙니다.

Source만 보면 Filter와 Security, Worker Pool, TransactionTemplate, Context 전파가 보이지 않습니다. 반대로 Runtime만 보면 Program·Package·Mapper의 정적 구조가 보이지 않습니다.

따라서 이번 정의서는 두 그림을 분리해서 설명한 뒤, 마지막에 ServiceId와 GUID를 축으로 다시 연결합니다.


---

# 4. Timeout과 Transaction의 경계

## FIG-01-05. Timeout과 Transaction의 경계

```text
Request Thread
 ↓
Future.get(5000ms)
 ├─ complete
 └─ timeout → 504
        │
        │ worker may continue
        ▼
Worker Thread
 ↓
TransactionTemplate
 ↓
JDBC / DB
```

이 그림이 중요한 이유는 **HTTP 응답의 수명과 DB Transaction의 수명이 같지 않기 때문**입니다.

현재 Snapshot에서 Request Thread는 `Future.get(5000ms)`로 Worker 완료를 기다릴 수 있지만, 5초가 지나 504를 반환했다고 해서 Worker가 같은 시점에 끝났다는 보장은 없습니다. `cancel(true)`도 JDBC Statement Cancel이나 DB Session Kill을 자동으로 의미하지 않습니다.

그래서 이후 Runtime 장에서는 `HTTP 504 ≠ Worker 종료 ≠ JDBC cancel ≠ Rollback 완료`를 핵심 원칙으로 사용합니다.


---

# 5. Security GAP는 구조 문제다

## FIG-01-06. Security GAP는 구조 문제다

```text
pdmg-jwt
  RS256 Issue
      ↓
     JWT
      ↓
pdmg-fw
  HMAC Verify Path
      ↓
Business Runtime

[CRITICAL GAP]
```

JWT의 발급과 검증 방식이 맞지 않는 문제는 단순 구현 버그로 끝나지 않습니다. Issuer, Verifier, Key Source, JWKS, Rotation, Multi-instance, DR Trust까지 연결되는 **Architecture Boundary 문제**입니다.

특히 인증 성공과 업무 인가는 다릅니다. Token이 검증되더라도 `sub/ssoId`가 실제 Business User Context와 어떻게 묶이는지 확인되어야 합니다.

그래서 Security는 별도 장으로 분리되지만, 1장부터 Critical GAP로 전면에 둡니다.


---

# 6. ServiceId를 Architecture Backbone으로 본다

## FIG-01-07. ServiceId를 Architecture Backbone으로 본다

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
Mapper / SqlId
 ↓
Table
```

PDMG에서 가장 중요한 연결축 중 하나는 `ServiceId`입니다. 이것은 단순한 URL 코드가 아니라 Business와 Source, SQL, Runtime을 연결하는 식별자입니다.

예를 들어 `mgcoa9001S0`은 Handler Registry의 Key가 되고, Handler에서 Facade/Service를 거쳐 Mapper/SqlId와 연결될 수 있습니다. 운영 시에는 이 ServiceId가 GUID, elapsed, ErrorCode와 결합되어야 합니다.

이 축이 안정되면 “이 업무가 어떤 코드와 SQL을 타는가”뿐 아니라 “장애 시 어느 Deployment에서 실행됐는가”까지 확장할 수 있습니다.


---

# 7. Logical에서 Physical까지 연결되어야 한다

## FIG-01-08. Logical에서 Physical까지 연결되어야 한다

```text
Application
 ↓
Logical Technical Node
 ↓
Environment
 ↓
Center
 ↓
Host / VM
 ↓
JVM
 ↓
WAR
 ↓
DeploymentId
 ↓
Evidence
```

Application 이름과 Server 이름 사이에는 여러 단계가 있습니다. 이 단계를 생략하면 `Server = JVM = WAR` 같은 잘못된 모델이 생깁니다.

이번 정의서에서는 Application Responsibility를 Logical Node로 변환한 뒤, Physical 장에서 Center·Host·VM·JVM·WAR로 내려갑니다. 그 다음 DevOps 장에서 Artifact/DeploymentId와 연결합니다.

현재 가장 큰 Physical GAP는 이 전수 Mapping이 아직 완전히 닫히지 않았다는 점입니다.


---

# 8. Architecture Closed Loop

## FIG-01-09. Architecture Closed Loop

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
GAP / ADR
 ↓
New Baseline
```

Architecture는 한 번 작성하고 끝나는 문서가 아닙니다. Source와 Config, Deployment는 계속 바뀌기 때문에 문서가 자동으로 낡습니다.

그래서 마지막 장에서는 Architecture Rule을 CI와 Runtime Evidence에 연결하고, Drift가 발생하면 GAP나 ADR로 닫은 뒤 Baseline을 갱신하는 Closed Loop를 정의합니다.

1장의 결론은 명확합니다. **우리가 다시 짓는 것은 시스템이 아니라, 시스템을 설명하고 검증하는 Architecture 체계입니다.**

---

# 정상패턴과 금지패턴

## FIG-01-10. Normal Pattern

```text
Current Fact → Responsibility → Runtime → Evidence
```

정상패턴은 Current Fact를 먼저 고정한 뒤 책임·Runtime·Evidence를 연결하는 것입니다. 문서의 문장을 Source보다 우선하지 않고, Target을 Current로 승격하지 않습니다.

## FIG-01-11. Forbidden Pattern

```text
문서=Fact / Target=Current / Log=Evidence
```

금지해야 할 것은 문서에 있는 Target 구조를 Current Fact처럼 쓰거나, 로그가 존재한다는 이유만으로 Runtime Evidence가 확보됐다고 판단하는 것입니다.

---

# Architecture Decision

## FIG-01-12. 주안과 대안

```text
[주안]
Evidence-backed Architecture Baseline

        VS

[대안]
Document-centered Baseline
```

Evidence-backed Baseline을 주안으로 고정합니다. 문서 중심 방식보다 구현·배포·Runtime과의 Drift를 발견할 수 있고, 이후 Architecture를 운영체계로 전환할 수 있기 때문입니다.

Document-centered Baseline은 초기 정리에는 빠르지만 Source/Runtime 변경을 자동으로 감지할 수 없습니다. 따라서 설명자료로는 사용할 수 있어도 최종 Baseline의 주안으로는 채택하지 않습니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---


# Evidence / Conformance — 이 장의 Architecture를 무엇으로 증명하는가

## Evidence Architecture

```text
왜 다시 짓는가 Architecture Rule
        ↓
Source Evidence
        ↓
Config Evidence
        ↓
Runtime / Deployment Evidence
        ↓
Conformance Test
        ↓
PASS / GAP / ADR
```

### Source Evidence

- `[SOURCE]` PDMG 기준 Module: pdmg-ui / pdmg-jwt / pdmg-fw / pdmg-service / pdmg-om
- `[SOURCE]` Business Package: nhnis.mg.co.a.*
- `[SOURCE]` Framework Package: nhnis.fw.*, com.ims.superspring.*
- `[SOURCE]` Mapper Resource: rdw.mg.co.a/

### Config Evidence

- `[CONFIG]` TCF enabled=true
- `[CONFIG]` Timeout enabled=true / 5000ms
- `[CONFIG]` Worker pool=20 / queue=100

### Runtime / Deployment Evidence

- `[RUNTIME]` Filter→Security→MVC→TCF→Worker→Transaction→Handler→Facade→Service→DAO→DB
- `[RUNTIME]` ServiceId/GUID 기반 추적 기반 존재

### Architecture Decision

- `[DECISION]` Evidence-backed Architecture Baseline
- `[DECISION]` PDMG Current ≠ NSIGHT Target

### Related ADR

- `ADR-001 Architecture Baseline/SSOT`
- `ADR-003 PDMG↔NSIGHT Mapping`
- `ADR-040 Runtime Evidence Gate`

### Current GAP / OPEN

- `[GAP/OPEN]` JWT 발급/검증 정합
- `[GAP/OPEN]` Identity Binding
- `[GAP/OPEN]` Artifact→JVM/Host
- `[GAP/OPEN]` pdmg-om Scope
- `[GAP/OPEN]` Runtime Evidence 자동화

## 이 장의 판정

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

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `JWT 발급/검증/Key 정합`
- `Identity Binding`
- `Artifact→JVM/Host Trace`
- `pdmg-om Scope`
- `Runtime Evidence 자동화`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-01-16. 다음 장 Handoff

```text
왜 다시 짓는가
 ↓
이미 시스템은 있지만, Architecture는 하나로 보이지 않는다
 ↓
남은 질문
"저장소와 Application 중심에서 살아 움직이는 Platform Architecture로"
 ↓
정보계 패러다임의 전환
```

1장의 결론은 시스템을 새로 상상하는 것이 아니라 **이미 존재하는 PDMG를 설명 가능한 Architecture로 다시 연결해야 한다**는 것입니다. 다음 장에서는 왜 기존 Application 중심 관점만으로는 이 연결을 설명하기 어려운지, 정보계 패러다임 변화부터 정리합니다.

---

# NSIGHT PDMG 아키텍처 정의서
# 제2장. 정보계 패러다임의 전환
## Story: “저장소와 Application 중심에서 살아 움직이는 Platform Architecture로”


---

# 0. Opening Script

1장에서 왜 다시 정의해야 하는지 확인했다면, 2장에서는 **무엇이 달라져야 하는가**를 봅니다.

기존 정보계는 화면·업무 Java·DB를 중심으로 설명해도 어느 정도 이해할 수 있었습니다. 그러나 차세대 정보계는 실시간 거래, 인증, 데이터 이동, 대량 분석, 장애격리, 운영증적이 하나의 플랫폼 안에서 동시에 움직입니다.

따라서 설명의 중심도 Application 자체에서 **Responsibility, Runtime, Data Flow, Failure Domain, Evidence**로 이동해야 합니다.

## FIG-02-01. 장 전체 Architecture

```text
과거
User → Application → DB

        ↓ 전환

현재/목표
User
 ↓
UI / Authentication
 ↓
Application Runtime
 ↓
Framework Control
 ↓
Data / Integration
 ↓
Operations / Evidence
```

이 그림에서 변화의 중심은 Application 개수가 아닙니다. `User → Application → DB`만으로 설명하던 관점에서 벗어나, UI·인증·Runtime Control·Data Flow·Operations까지 Architecture 범위로 확장합니다. 앞으로의 설계는 제품이 아니라 **책임과 Capability, Runtime 특성**을 기준으로 내려갑니다.


---

# 1. Application-centric의 한계

## FIG-02-02. Application-centric의 한계

```text
Screen
 ↓
Business Java
 ↓
SQL
 ↓
DB

보이지 않는 것
Thread / Security / Timeout / Deployment / Evidence
```

기존 방식이 틀렸다는 의미는 아닙니다. 기능 중심 개발에는 충분했습니다. 문제는 시스템 규모와 운영복잡도가 커졌는데도 같은 관점으로 설명하려 할 때 생깁니다.

Thread와 Connection Pool, Token Verification, Interface Contract, Deployment Artifact를 설명하지 못하면 장애 원인을 Application 내부에서만 찾게 됩니다.

그래서 Architecture의 범위를 Source Code 밖으로 확장합니다.


---

# 2. Responsibility-centric 전환

## FIG-02-03. Responsibility-centric 전환

```text
UI Delivery
Authentication
Application Runtime
Framework Control
Data Service
Integration
Operations

각 책임은 분리
연결은 Contract
```

이번 전환의 첫 번째 핵심은 Application 이름 대신 **책임을 먼저 보는 것**입니다.

UI가 인증을 소유하지 않고, 인증이 Business Logic을 소유하지 않으며, Framework가 특정 업무 SQL을 소유하지 않도록 경계를 둡니다. 책임은 안으로 고정하고 연결은 경계에서 통제하는 방식입니다.

이렇게 해야 한 영역의 변경이 다른 영역으로 번지는 범위를 줄일 수 있습니다.


---

# 3. Module에서 Runtime Boundary로

## FIG-02-04. Module에서 Runtime Boundary로

```text
Build Module
 ↓
Runtime Relationship
 ↓
Process / JVM
 ↓
Spring Context

Module ≠ Process ≠ JVM
```

두 번째 전환은 Module과 Runtime을 구분하는 것입니다. `pdmg-service`와 `pdmg-fw`가 각각 Build Module이라고 해서 반드시 서로 다른 Process라는 뜻은 아닙니다.

현재 분석에서는 `scanBasePackages="nhnis"`에 의해 Framework Bean과 Business Bean이 같은 ApplicationContext에서 협력할 수 있습니다.

따라서 Build 구조는 Source Management 관점, Runtime Boundary는 실행/장애 관점으로 분리해서 봐야 합니다.


---

# 4. Product에서 Capability로

## FIG-02-05. Product에서 Capability로

```text
Tomcat
 ↓
Application Runtime Capability

HikariCP
 ↓
Connection Pool Capability

MyBatis
 ↓
SQL Mapping Capability
```

제품은 Architecture가 아닙니다. 제품은 Architecture가 요구하는 Capability를 구현하는 수단입니다.

예를 들어 Tomcat을 쓴다는 사실보다 중요한 것은 Request Thread, Connector, JVM Failure Domain을 어떻게 가져갈지입니다. HikariCP 역시 제품명보다 Connection Pool의 Capacity와 Timeout 관계가 중요합니다.

그래서 Logical 장에서는 제품보다 Capability를 먼저 정의합니다.


---

# 5. Online에서 FAST/DEEP Workload로

## FIG-02-06. Online에서 FAST/DEEP Workload로

```text
FAST
HTTP / Event / CDC
 ↓
bounded latency

DEEP
ETL / ADW / BI
 ↓
heavy workload
```

차세대 정보계는 모든 처리를 같은 Runtime으로 묶지 않습니다. 즉시 응답이 필요한 FAST와 장시간·대량 연산이 가능한 DEEP Workload를 분리합니다.

PDMG Current는 HTTP/Transaction 중심의 FAST Runtime Evidence가 강합니다. Event/CDC/ETL/BI는 NSIGHT Target Reference로 별도 관리합니다.

이 구분이 나중에 RDW/ADW 분리와 Capacity Isolation의 근거가 됩니다.


---

# 6. Server에서 Logical→Physical로

## FIG-02-07. Server에서 Logical→Physical로

```text
Responsibility
 ↓
Logical Node
 ↓
Scale / State / Failure
 ↓
VM / JVM / WAR
```

세 번째 전환은 Application을 곧바로 Server에 배치하지 않는 것입니다.

Logical Node 단계에서 Stateless인지, 어떤 Scale Unit을 가지는지, 어떤 Failure Domain을 가져야 하는지 결정한 뒤 Physical Resource로 내려갑니다.

이렇게 해야 VM 크기나 Server 수량이 Architecture Intent와 연결됩니다.


---

# 7. Logging에서 Evidence로

## FIG-02-08. Logging에서 Evidence로

```text
Log
= 사건 기록

Evidence
= Rule 준수 증명

Rule
 ↓
Runtime Metric/Test
 ↓
Evidence
 ↓
PASS
```

로그는 중요하지만 로그가 있다고 Architecture가 지켜졌다고 말할 수는 없습니다.

예를 들어 ServiceId가 로그에 찍힌다는 것과 “모든 거래가 올바른 Handler로 라우팅됐다”는 것은 다른 주장입니다. 후자는 Registry/Runtime Test가 필요합니다.

따라서 마지막에는 Log를 Metric/Trace/Test와 묶어 Evidence로 승격합니다.


---

# 8. Document에서 Closed Loop로

## FIG-02-09. Document에서 Closed Loop로

```text
Document
 ↓
Rule
 ↓
Source / CI
 ↓
Deployment
 ↓
Runtime
 ↓
Drift / ADR
```

패러다임 전환의 마지막은 문서 운영 방식입니다.

문서가 Source를 따라가는 것이 아니라, Architecture Rule이 Source와 Deployment를 검사하고 Runtime Evidence가 이를 다시 확인하는 구조로 전환합니다.

이제 Architecture는 산출물이 아니라 **변경을 통제하는 운영체계**가 됩니다.

---

# 정상패턴과 금지패턴

## FIG-02-10. Normal Pattern

```text
Responsibility → Capability → Runtime → Evidence
```

정상패턴은 Application 이름을 곧바로 Server나 Product로 매핑하지 않고 Responsibility→Capability→Runtime→Evidence 순서로 해석하는 것입니다.

## FIG-02-11. Forbidden Pattern

```text
Module=Server / Product=Node / Current=Target
```

Module=Server, Product=Logical Node, Current=Target 같은 단축 해석은 이후 Capacity·Security·DR 판단을 모두 왜곡하므로 허용하지 않습니다.

---

# Architecture Decision

## FIG-02-12. 주안과 대안

```text
[주안]
Runtime/Platform까지 Architecture Scope 확장

        VS

[대안]
Application Source 중심 Scope
```

Architecture Scope를 Source 내부에 한정하지 않고 Runtime/Platform/Data/Operations까지 확장합니다. 이것이 차세대 정보계의 실제 장애와 변경영향을 설명할 수 있는 최소 범위입니다.

Application Source 중심 Scope는 개발 관점에서는 단순하지만 Thread·Security·Deployment·Data Flow를 별도 문서로 분산시키므로 전체 Architecture 기준으로는 부족합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---


# Evidence / Conformance — 이 장의 Architecture를 무엇으로 증명하는가

## Evidence Architecture

```text
정보계 패러다임의 전환 Architecture Rule
        ↓
Source Evidence
        ↓
Config Evidence
        ↓
Runtime / Deployment Evidence
        ↓
Conformance Test
        ↓
PASS / GAP / ADR
```

### Source Evidence

- `[SOURCE]` Module 책임과 Runtime Boundary 분석
- `[SOURCE]` pdmg-service + pdmg-fw 동일 Spring Runtime 가능

### Config Evidence

- `[CONFIG]` Module build dependency와 Runtime config 분리

### Runtime / Deployment Evidence

- `[RUNTIME]` Application-centric가 아닌 Runtime/Platform 관점 필요

### Architecture Decision

- `[DECISION]` Application 중심 → Responsibility/Runtime 중심
- `[DECISION]` Product → Capability

### Related ADR

- `ADR-003 Mapping Registry`
- `ADR-035 Observability`
- `ADR-040 Runtime Evidence`

### Current GAP / OPEN

- `[GAP/OPEN]` Module→Runtime/Physical Mapping
- `[GAP/OPEN]` Current↔Target Mapping Registry
- `[GAP/OPEN]` Operations Current Scope

## 이 장의 판정

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

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `Module→Runtime/Physical Mapping`
- `Current↔Target Mapping Registry`
- `Operations Scope`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-02-16. 다음 장 Handoff

```text
정보계 패러다임의 전환
 ↓
저장소와 Application 중심에서 살아 움직이는 Platform Architecture로
 ↓
남은 질문
"비전에서 시작해 실제 Runtime 검증까지 내려간다"
 ↓
아키텍처 6단계 수립 방법론
```

이제 Architecture의 시선이 Application 내부에서 Runtime·Data·Operations까지 확장되었습니다. 다음 장에서는 이 넓어진 범위를 한 번에 설계하지 않고, 어떤 순서로 결정해야 하는지 6단계 방법론으로 고정합니다.

---

# NSIGHT PDMG 아키텍처 정의서
# 제3장. 아키텍처 6단계 수립 방법론
## Story: “비전에서 시작해 실제 Runtime 검증까지 내려간다”


---

# 0. Opening Script

전환 방향을 정했으면 이제 설계 순서가 필요합니다. 복잡한 정보계를 한 번에 그리면 Application, Server, Product, Runtime이 같은 레벨에 섞입니다.

그래서 NSIGHT Architecture는 **VISION → BIG PICTURE → LOGICAL → PHYSICAL → MECHANISM → RUNTIME**의 여섯 단계로 내려갑니다. 그리고 마지막에는 Evidence로 다시 위로 올라옵니다.

이 순서는 문서 목차가 아니라, 의사결정의 순서입니다.

## FIG-03-01. 장 전체 Architecture

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
EVIDENCE / PASS
```

이 여섯 단계는 목차가 아니라 의사결정 순서입니다. 상위 단계에서 방향과 경계를 정하지 않은 채 Physical이나 제품부터 고르면 뒤에서 설계를 합리화하게 됩니다. 그래서 VISION에서 시작해 RUNTIME까지 내려간 후, 마지막에는 Source와 Runtime Evidence로 다시 위의 의도를 검증합니다.


---

# 1. VISION — 방향을 고정한다

## FIG-03-02. VISION — 방향을 고정한다

```text
Scalable
Resilient
Data-Centric
 ↓
Architecture Decision Criteria
```

VISION은 제품을 정하는 단계가 아닙니다. 이후 모든 의사결정을 판단할 기준을 고정하는 단계입니다.

예를 들어 Scalable을 말하면서 하나의 거대한 Failure Domain을 만들 수는 없습니다. Resilient를 말하면서 Restore Drill 없이 DR PASS를 선언할 수도 없습니다.

VISION은 뒤의 Logical·Physical 장을 구속하는 설계계약입니다.


---

# 2. BIG PICTURE — 책임과 경계를 고정한다

## FIG-03-03. BIG PICTURE — 책임과 경계를 고정한다

```text
User / Channel
 ↓
Application Boundary
 ↓
Data / Integration
 ↓
Operations
```

Big Picture에서는 세부 Class나 Product를 보지 않습니다. 누가 어떤 책임을 가지고 어떤 경계를 넘는지만 봅니다.

이 단계가 약하면 뒤에서 UI가 DB를 직접 보거나, External System이 내부 Table을 직접 변경하는 구조가 생길 수 있습니다.

따라서 Big Picture는 적은 박스로 전체 책임을 고정하는 단계입니다.


---

# 3. LOGICAL — 기술 역할을 정의한다

## FIG-03-04. LOGICAL — 기술 역할을 정의한다

```text
Application Responsibility
 ↓
Technical Capability
 ↓
Logical Node
 ↓
State / Scale / Failure / Security
```

Logical Architecture는 제품을 사기 전에 필요한 기술 역할을 정의합니다.

Application Runtime, Authentication, Data Service, Integration, Operations 같은 Node를 만들고 각각의 State와 Scale, Failure Domain을 정의합니다.

이 단계가 있어야 Physical 설계가 제품과 서버 수량의 나열로 떨어지지 않습니다.


---

# 4. PHYSICAL — 실제 자원으로 내린다

## FIG-03-05. PHYSICAL — 실제 자원으로 내린다

```text
Logical Node
 ↓
Environment
 ↓
Center
 ↓
Host / VM
 ↓
JVM / WAR
 ↓
Network / DB
```

Physical 장에서는 Logical Node가 실제 어디에서 실행되는지 확정합니다.

Server, VM, JVM, WAR를 구분하고 Network, DB, Storage, Monitoring까지 함께 봅니다.

여기서 Candidate Capacity와 실제 Production Fact를 구분하는 것이 중요합니다.


---

# 5. MECHANISM — 실행규칙을 정한다

## FIG-03-06. MECHANISM — 실행규칙을 정한다

```text
Filter
 ↓
Security
 ↓
TCF
 ↓
Timeout
 ↓
Transaction
 ↓
Error / Logging
```

Mechanism은 같은 Infrastructure 위에서도 거래가 동일한 방식으로 움직이게 만드는 표준입니다.

Filter가 Context를 만들고 Security가 Trust를 확인하며 TCF가 ServiceId를 라우팅하고 Worker/Transaction이 실행경계를 만듭니다.

시스템은 서버만으로 움직이지 않고 이런 실행규칙으로 움직입니다.


---

# 6. RUNTIME — 시간축으로 검증한다

## FIG-03-07. RUNTIME — 시간축으로 검증한다

```text
T0 Request
T1 Filter
T2 Security
T3 Controller
T4 Worker
T5 Transaction
T6 DB
T7 Response
```

Runtime 장에서는 정적인 구조를 실제 시간순서로 펼칩니다.

여기서 Request Thread와 Worker Thread, HTTP Timeout과 DB Transaction 수명 같은 차이가 드러납니다.

Runtime을 봐야 설계가 실제 동작과 맞는지 확인할 수 있습니다.


---

# 7. Top-down과 Bottom-up을 닫는다

## FIG-03-08. Top-down과 Bottom-up을 닫는다

```text
Top-down
Intent → Design → Rule
          ↓
Bottom-up
Source ← Test ← Runtime Evidence
```

Architecture는 위에서 아래로만 내려가면 문서가 되고, 아래에서 위로만 보면 Source 분석이 됩니다.

둘을 닫아야 Baseline이 됩니다. 상위 Intent가 Rule로 내려가고, Runtime Evidence가 다시 그 Rule을 검증합니다.

이 Closed Loop가 HG90의 전제가 됩니다.


---

# 8. Gate로 끝낸다

## FIG-03-09. Gate로 끝낸다

```text
G00 Source
 ↓
G20 Model
 ↓
G40 Test
 ↓
G50 Evidence
 ↓
G70 GAP/ADR
 ↓
HG90
```

6단계 그림을 완성했다고 끝나는 것이 아닙니다.

Source/Config Conformance, Runtime Test, GAP/ADR가 닫혀야 공식 Baseline으로 Release할 수 있습니다.

따라서 방법론의 마지막 단계는 항상 Evidence와 Gate입니다.

---

# 정상패턴과 금지패턴

## FIG-03-10. Normal Pattern

```text
Vision→BigPicture→Logical→Physical→Mechanism→Runtime→Evidence
```

정상패턴은 상위 의도가 하위 설계를 구속하고, 하위 Source/Runtime이 다시 상위 의도를 검증하는 양방향 구조입니다.

## FIG-03-11. Forbidden Pattern

```text
제품→서버→사후합리화
```

제품이나 서버를 먼저 정한 뒤 Vision과 Logical Architecture를 사후에 맞추는 방식은 이 방법론의 역순이므로 기본패턴으로 사용하지 않습니다.

---

# Architecture Decision

## FIG-03-12. 주안과 대안

```text
[주안]
6단계 Top-down + Bottom-up Evidence

        VS

[대안]
제품/Physical 선결정
```

6단계 Top-down과 Bottom-up Evidence를 하나의 방법론으로 채택합니다. 상위 Intent와 실제 Source가 서로 검증되지 않으면 문서와 시스템은 다시 분리되기 때문입니다.

제품/Physical 선결정 방식은 단기간 실행은 빠르지만 책임·경계·Failure Domain을 사후에 맞춰야 하므로 Architecture Decision의 추적성이 약해집니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---


# Evidence / Conformance — 이 장의 Architecture를 무엇으로 증명하는가

## Evidence Architecture

```text
아키텍처 6단계 수립 방법론 Architecture Rule
        ↓
Source Evidence
        ↓
Config Evidence
        ↓
Runtime / Deployment Evidence
        ↓
Conformance Test
        ↓
PASS / GAP / ADR
```

### Source Evidence

- `[SOURCE]` Architecture 00~18 정의서 및 Gate 체계
- `[SOURCE]` PDMG Source/Runtime 상세분석

### Config Evidence

- `[CONFIG]` Architecture Rule / Manifest / Conformance 구조

### Runtime / Deployment Evidence

- `[RUNTIME]` Top-down 설계와 Bottom-up Evidence 연결

### Architecture Decision

- `[DECISION]` VISION→BIG PICTURE→LOGICAL→PHYSICAL→MECHANISM→RUNTIME→EVIDENCE

### Related ADR

- `ADR-001 Baseline`
- `ADR-040 Runtime Evidence`
- `HG90 Gate`

### Current GAP / OPEN

- `[GAP/OPEN]` Architecture Model SSOT
- `[GAP/OPEN]` G50 Runtime Evidence 자동화
- `[GAP/OPEN]` HG90 자동 Gate

## 이 장의 판정

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

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `Architecture Model SSOT`
- `G50 Runtime Evidence 자동화`
- `HG90 Gate 운영`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-03-16. 다음 장 Handoff

```text
아키텍처 6단계 수립 방법론
 ↓
비전에서 시작해 실제 Runtime 검증까지 내려간다
 ↓
남은 질문
"화려한 박스가 아니라 책임과 경계를 먼저 본다"
 ↓
Big Picture
```

방법론이 정해졌으므로 이제 실제 설계가 시작됩니다. 다음 장 Big Picture에서는 세부기술을 잠시 내려놓고 사용자·인증·업무·데이터·외부연계의 책임과 경계를 한 장으로 고정합니다.

---

# NSIGHT PDMG 아키텍처 정의서
# 제4장. Big Picture
## Story: “화려한 박스가 아니라 책임과 경계를 먼저 본다”


---

# 0. Opening Script

이제 전체 시스템을 한 장으로 봅니다. 여기서 박스를 많이 넣는 것이 목적이 아닙니다.

Big Picture의 역할은 **사용자가 어디에서 들어오고, 인증은 어디에서 끝나며, 업무 Runtime과 Data는 어디에서 분리되고, External 연결은 어떤 경계를 통과하는가**를 한눈에 보여주는 것입니다.

이 장이 이후 Logical·Physical·Security·Interface 장의 공간적 기준이 됩니다.

## FIG-04-01. 장 전체 Architecture

```text
User / Browser
      ↓
UI Delivery
      ↓
Authentication
      ↓
Application Runtime
      ↓
Data

External → Approved Contract
Security / Observability = Cross-cutting
```

Big Picture에서는 세부 Class나 서버 이름을 최대한 지웁니다. 대신 사용자가 어디에서 들어오고, 인증은 어디에서 신뢰를 만들며, 업무 Runtime과 Data는 어디에서 갈라지고, External 연결은 어떤 Contract를 통과해야 하는지를 한 장에 고정합니다. 이후 모든 장은 이 경계를 확대하는 작업입니다.


---

# 1. User와 Channel Boundary

## FIG-04-02. User와 Channel Boundary

```text
User
 ↓
Browser / Channel
 ↓
Access Boundary
 ↓
pdmg-ui / pdmg-jwt / pdmg-service
```

첫 번째 경계는 사용자와 시스템 사이입니다. Browser는 업무를 시작하지만 Business Logic을 소유하지 않습니다.

UI는 화면과 요청조립을 담당하고, Authentication은 신뢰를 만들며, Business Runtime은 거래를 실행합니다. 세 역할을 같은 Application처럼 보이게 하면 변경영향이 커집니다.

그래서 Big Picture에서부터 UI/Auth/Business를 분리합니다.


---

# 2. UI Delivery Boundary

## FIG-04-03. UI Delivery Boundary

```text
Browser
 ↓
pdmg-ui
 ├─ Screen
 ├─ Transaction Catalog
 └─ Request Assembly
 ↓
Business Call
```

`pdmg-ui`는 Presentation 영역입니다. 화면, 정적자원, Transaction Catalog, Request Assembly를 담당합니다.

여기서 중요한 금지사항은 UI가 DAO나 DB를 직접 보는 것입니다. UI가 Business/Data Boundary를 넘으면 Layering과 Security가 동시에 무너집니다.

따라서 UI의 마지막 책임은 표준 Request를 만들어 Application Runtime으로 전달하는 데 있습니다.


---

# 3. Authentication Boundary

## FIG-04-04. Authentication Boundary

```text
Login / SSO
 ↓
pdmg-jwt
 ↓
Access / Refresh Token
 ↓
Verification
 ↓
Trusted Principal
```

Authentication은 별도의 Trust Boundary입니다. 로그인이나 SSO를 통과한 결과는 Token이지만, 업무 Runtime에서 다시 검증되어야 합니다.

현재 RS256 Issue와 HMAC Verify Path의 불일치는 이 Boundary가 완전히 닫히지 않았음을 의미합니다.

또한 Authentication과 Authorization은 다르므로 Trusted Principal이 Business User Context로 어떻게 연결되는지도 별도로 검증해야 합니다.


---

# 4. Application Runtime Boundary

## FIG-04-05. Application Runtime Boundary

```text
pdmg-service
┌──────────────────────────┐
│ pdmg-fw Runtime Control  │
│                          │
│ Handler / Facade         │
│ Service / DAO            │
└────────────┬─────────────┘
             ↓
           Data
```

PDMG의 중심 실행영역은 `pdmg-service`입니다. 이 안에서 `pdmg-fw` Framework Bean과 Business Bean이 협력할 수 있습니다.

따라서 `pdmg-fw`를 Remote Server로 그리는 것은 현재 Evidence와 맞지 않습니다. Build Module과 Runtime Boundary는 다릅니다.

이 Boundary 안에서 Framework는 실행정책을, Business는 Use Case를 담당합니다.


---

# 5. Data Boundary

## FIG-04-06. Data Boundary

```text
Business
 ↓
DAO / Mapper
 ↓
JDBC
 ↓
RDW / DB

External Data
 ↓
Approved Contract
```

Data Boundary에서는 내부 Data Access와 외부 시스템 Data를 분리합니다.

PDMG Current에서 DAO→Mapper→JDBC→RDW/DB는 비교적 강한 Evidence가 있습니다. 반면 ADW 사용이나 Cross-system Data Access는 Inventory를 통해 확인해야 합니다.

External System의 DB에 직접 DML하는 구조는 기본 정상패턴으로 보지 않습니다.


---

# 6. External Integration Boundary

## FIG-04-07. External Integration Boundary

```text
PDMG
 ↓
Interface Contract
 ├─ API
 ├─ Event
 ├─ File
 └─ Data Movement
 ↓
External System
```

외부 연결은 선 하나가 아니라 Contract입니다. Source/Target, Schema, Security, Timeout, Retry, Version, Operations가 함께 있어야 합니다.

PDMG Current에서 어떤 Interface가 실제 존재하는지는 전수 Inventory가 필요합니다. Kafka/CDC/ETL이 NSIGHT Target에 있다는 이유로 PDMG Current Component로 넣지 않습니다.

이 구분이 6장 Interface Architecture와 10장 Data Platform으로 이어집니다.


---

# 7. Security와 Observability는 Cross-cutting이다

## FIG-04-08. Security와 Observability는 Cross-cutting이다

```text
Security
 ↓ all boundaries

GUID / ServiceId
 ↓
Runtime
 ↓
Metric / Log / Trace
 ↓
Operations
```

Security와 Observability는 한쪽 구석의 박스가 아닙니다. 모든 Boundary를 가로지릅니다.

Security는 요청이 이동할 때마다 Trust를 유지해야 하고, Observability는 ServiceId/GUID를 기준으로 같은 거래를 끝까지 추적해야 합니다.

그래서 Big Picture에서부터 Cross-cutting으로 표시합니다.


---

# 8. Big Picture에서 보이는 GAP

## FIG-04-09. Big Picture에서 보이는 GAP

```text
CRITICAL
JWT / Identity

HIGH
Interface Inventory
Deployment Mapping

OPEN
pdmg-om
ADW current mapping
```

Big Picture는 세부내용을 숨기지만 GAP는 숨기지 않습니다.

현재 가장 중요한 것은 Security, External Interface Inventory, Deployment Mapping, pdmg-om Scope입니다. 이 항목들이 뒤 장에서 구체화됩니다.

Big Picture의 역할은 문제가 어디에 있는지 공간적으로 먼저 보여주는 것입니다.

---

# 정상패턴과 금지패턴

## FIG-04-10. Normal Pattern

```text
User→UI/Auth→Runtime→Data / External→Contract
```

정상패턴은 UI·Authentication·Business·Data의 책임을 분리하고 External 연결을 Contract를 통해 통제하는 것입니다.

## FIG-04-11. Forbidden Pattern

```text
Browser→DB / External→Direct DML
```

Browser→DB, UI→DAO, External→PDMG DB DML처럼 책임경계를 뛰어넘는 연결은 Architecture 기본패턴으로 허용하지 않습니다.

---

# Architecture Decision

## FIG-04-12. 주안과 대안

```text
[주안]
책임 고정 + Contract 연결

        VS

[대안]
P2P/Direct 연결 확대
```

Big Picture의 주안은 책임을 공간적으로 고정하고 경계 연결을 Contract로 통제하는 것입니다. 이 구조가 Logical·Security·Interface 설계의 공통 기준이 됩니다.

P2P와 Direct 접근을 확대하면 초기 연결은 쉬우나 변경영향과 장애전파가 빠르게 증가하므로 예외 승인 외에는 기본구조로 사용하지 않습니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---


# Evidence / Conformance — 이 장의 Architecture를 무엇으로 증명하는가

## Evidence Architecture

```text
Big Picture Architecture Rule
        ↓
Source Evidence
        ↓
Config Evidence
        ↓
Runtime / Deployment Evidence
        ↓
Conformance Test
        ↓
PASS / GAP / ADR
```

### Source Evidence

- `[SOURCE]` pdmg-ui / pdmg-jwt / pdmg-service / pdmg-fw 경계 분석
- `[SOURCE]` RDW/DB Current Data Boundary

### Config Evidence

- `[CONFIG]` Security Filter / HTTP / Application Runtime 설정

### Runtime / Deployment Evidence

- `[RUNTIME]` User→UI→Auth→Application Runtime→Data
- `[RUNTIME]` External은 Approved Contract로만 연결

### Architecture Decision

- `[DECISION]` 책임을 공간에 고정하고 연결을 경계에서 통제

### Related ADR

- `ADR-013 Identity Binding`
- `ADR-014 Interface Selection`
- `ADR-036 OM Control Plane`

### Current GAP / OPEN

- `[GAP/OPEN]` JWT/Identity
- `[GAP/OPEN]` External Interface Inventory
- `[GAP/OPEN]` pdmg-om
- `[GAP/OPEN]` Deployment Mapping

## 이 장의 판정

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

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `External Interface Inventory`
- `Identity Boundary Closure`
- `Deployment Mapping`
- `pdmg-om Scope`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-04-16. 다음 장 Handoff

```text
Big Picture
 ↓
화려한 박스가 아니라 책임과 경계를 먼저 본다
 ↓
남은 질문
"기술을 고르기 전에 무엇을 분리하고 허용할지 정한다"
 ↓
논리 아키텍처
```

Big Picture에서 공간적 경계를 고정했습니다. 다음 장에서는 이 책임들을 제품이 아니라 Technical Capability와 Logical Node로 변환해 State·Scale·Failure 관점으로 내려갑니다.

---

# NSIGHT PDMG 아키텍처 정의서
# 제5장. 논리 아키텍처
## Story: “기술을 고르기 전에 무엇을 분리하고 허용할지 정한다”


---

# 0. Opening Script

Big Picture에서 책임을 나눴다면 이제 그 책임을 기술적으로 실행할 구조가 필요합니다.

논리 아키텍처는 Tomcat, Kafka, Oracle 같은 제품을 고르는 단계가 아닙니다. **어떤 Technical Capability가 필요하고, 어떤 Logical Node로 분리하며, 각 Node가 어떤 State·Scale·Failure Domain을 가져야 하는지**를 정하는 단계입니다.

## FIG-05-01. 장 전체 Architecture

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

논리 아키텍처는 제품선정 전에 필요한 기술역할을 결정합니다. UI Delivery, Authentication, Application Runtime, Data Service 같은 Node를 만들고 State·Scale·Failure·Security 속성을 부여합니다. 이 속성이 Physical 설계의 입력이 됩니다.


---

# 1. Application에서 Capability로

## FIG-05-02. Application에서 Capability로

```text
pdmg-ui      → UI Delivery
pdmg-jwt     → Authentication
pdmg-service → Business Runtime
pdmg-fw      → Runtime Control
DB           → Data Service
```

Application 이름을 바로 Physical Server로 내리지 않고 먼저 Capability로 번역합니다.

이렇게 하면 현재 제품을 바꾸더라도 Architecture의 책임은 유지할 수 있습니다.

Capability가 Logical Node를 만드는 재료가 됩니다.


---

# 2. Logical Node Catalog

## FIG-05-03. Logical Node Catalog

```text
LTN-PD-01 UI Delivery
LTN-PD-02 Authentication
LTN-PD-03 Application Runtime
LTN-PD-04 Data Service
LTN-PD-05 Integration [CONDITIONAL]
LTN-PD-06 Operations [OPEN]
```

Logical Node는 실행책임과 Failure Domain을 표현하는 논리 단위입니다.

PDMG Current Evidence가 강한 UI/Auth/App/Data Node와 달리 Integration/Operations는 Current 범위가 완전히 확인되지 않았으므로 조건부/OPEN으로 둡니다.

Unknown을 Target으로 채우지 않는 것이 중요합니다.


---

# 3. Application Runtime Node 내부

## FIG-05-04. Application Runtime Node 내부

```text
Application Runtime
├─ Framework Runtime
│  ├─ Filter / Context
│  ├─ Security
│  ├─ TCF
│  ├─ Worker / Timeout
│  └─ Transaction / Error
└─ Business Runtime
   ├─ Handler / Controller
   ├─ Facade
   ├─ Service
   └─ DAO
```

Application Runtime Node는 다시 Framework와 Business로 나뉩니다.

Framework는 실행방법을, Business는 업무내용을 책임집니다. 이 분리를 지키면 TCF ON/OFF가 바뀌어도 Business Core를 동일하게 유지할 수 있습니다.

이 구조가 8장 Mechanism의 논리적 기반입니다.


---

# 4. State Model

## FIG-05-05. State Model

```text
UI Delivery       mostly stateless
Authentication      key / refresh state
Application Runtime request/context/TX state
Data Service        persistent state
```

Scale 전략을 정하려면 먼저 State를 알아야 합니다.

Stateless에 가까운 Node는 수평확장이 쉽지만, Session·Key·Transaction State를 가진 Node는 동기화와 Failover 전략이 필요합니다.

Logical 장에서 State를 먼저 정의해야 Physical HA가 설계됩니다.


---

# 5. Scale Unit

## FIG-05-06. Scale Unit

```text
UI  → delivery instance
Auth → auth instance
App  → JVM/runtime instance
DB   → DB service/node
```

모든 Node가 같은 단위로 Scale하는 것은 아닙니다.

Application Runtime은 JVM Instance가 Scale Unit이 될 수 있고, DB는 DB Service/Node 단위로 봐야 합니다.

이 차이를 무시하면 Thread 수와 Server 수를 같은 Capacity 문제로 보게 됩니다.


---

# 6. Failure Domain

## FIG-05-07. Failure Domain

```text
UI Failure   → access impact
Auth Failure → login/token impact
App Failure  → transaction impact
DB Failure   → data impact
```

Failure Domain은 장애가 어디까지 번지는지를 정의합니다.

Node를 나누는 이유는 단순한 모듈화가 아니라 장애격리입니다. Authentication 장애와 Business Runtime 장애가 동일한 Failure Domain일 필요는 없습니다.

Physical 장에서 이 Domain을 실제 JVM/VM에 매핑합니다.


---

# 7. Security Boundary

## FIG-05-08. Security Boundary

```text
Untrusted
 ↓
Authentication
 ↓
Application Runtime
 ↓
Business Authorization
 ↓
Data
```

Logical Security는 방화벽보다 먼저 Trust 흐름을 정의합니다.

사용자 입력은 Untrusted에서 시작하고, Authentication을 통해 Principal을 만들고, Business Authorization과 Data Authorization을 별도로 적용해야 합니다.

이 논리경계를 실제 Security Filter/JWT/DB 권한으로 구현하는 것은 이후 Mechanism/Runtime 장의 책임입니다.


---

# 8. Logical→Physical Handoff

## FIG-05-09. Logical→Physical Handoff

```text
Logical Node
 ↓
State / Scale / Failure
 ↓
Availability Requirement
 ↓
VM / JVM / WAR Mapping
```

논리 아키텍처의 마지막 질문은 '이 Node를 실제 어디에 배치할 것인가'입니다.

Physical 장은 Logical Node의 속성을 받아 실제 VM/JVM/WAR를 결정합니다.

즉 Logical이 없다면 Physical은 서버 배치표가 되고, Logical이 있으면 Physical은 설계의 구현이 됩니다.

---

# 정상패턴과 금지패턴

## FIG-05-10. Normal Pattern

```text
Application→Capability→Logical Node→Physical
```

정상패턴은 Application 책임을 Logical Node로 변환한 뒤 State·Scale·Failure 특성을 정의하고 Physical 설계로 넘기는 것입니다.

## FIG-05-11. Forbidden Pattern

```text
Logical Node=Product / Module=Server
```

Logical Node를 제품명이나 Build Module과 동일시하면 State와 Failure Domain을 표현할 수 없으므로 금지합니다.

---

# Architecture Decision

## FIG-05-12. 주안과 대안

```text
[주안]
Logical Node 먼저 정의

        VS

[대안]
제품/서버부터 정의
```

Logical Node를 제품보다 먼저 정의합니다. State·Scale·Failure·Security 속성을 기술한 뒤 제품과 Physical Resource를 선택해야 기술변경에도 Architecture Intent가 유지됩니다.

제품/서버부터 정의하면 현재 기술에 설계가 고정되고 Capability와 Failure Domain이 제품명 뒤에 숨게 됩니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---


# Evidence / Conformance — 이 장의 Architecture를 무엇으로 증명하는가

## Evidence Architecture

```text
논리 아키텍처 Architecture Rule
        ↓
Source Evidence
        ↓
Config Evidence
        ↓
Runtime / Deployment Evidence
        ↓
Conformance Test
        ↓
PASS / GAP / ADR
```

### Source Evidence

- `[SOURCE]` Application/Module 상세정의
- `[SOURCE]` Technical Architecture Definition
- `[SOURCE]` PDMG Logical 상세정의

### Config Evidence

- `[CONFIG]` Runtime State/Scale/Failure 속성은 Config/Deployment와 연결 필요

### Runtime / Deployment Evidence

- `[RUNTIME]` UI/Auth/Application/Data Logical Node 분리

### Architecture Decision

- `[DECISION]` Logical Node를 Product보다 먼저 정의

### Related ADR

- `ADR-004 Common Facade`
- `ADR-028 JVM Isolation`
- `ADR-029 Capacity Budget`

### Current GAP / OPEN

- `[GAP/OPEN]` Integration Node Current Scope
- `[GAP/OPEN]` Operations Node Current Scope
- `[GAP/OPEN]` State/Scale Evidence
- `[GAP/OPEN]` Logical→Physical Mapping

## 이 장의 판정

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

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `Logical Node→Physical Mapping`
- `Integration/Operations Node Evidence`
- `State/Scale 검증`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-05-16. 다음 장 Handoff

```text
논리 아키텍처
 ↓
기술을 고르기 전에 무엇을 분리하고 허용할지 정한다
 ↓
남은 질문
"속도와 안정성을 실제 Center·VM·JVM·DB 구조로 구현한다"
 ↓
물리 아키텍처
```

논리적으로 무엇을 분리할지 정했으므로 다음 질문은 실제 어디에서 실행할 것인가입니다. 6장에서는 Logical Node를 Center·VM·JVM·WAR·DB로 투영합니다.

---

# NSIGHT PDMG 아키텍처 정의서
# 제6장. 물리 아키텍처
## Story: “속도와 안정성을 실제 Center·VM·JVM·DB 구조로 구현한다”


---

# 0. Opening Script

논리적으로 무엇을 분리할지 정했다면 이제 실제 어디에서 실행할지 결정합니다.

물리 아키텍처의 핵심은 서버 수가 아닙니다. **Logical Node를 어떤 Center, VM, JVM, WAR에 배치하고, Network와 DB를 어디서 끊어 장애를 격리할 것인가**가 핵심입니다.

## FIG-06-01. 장 전체 Architecture

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
PDMG WAR
 ↓
Hikari / MyBatis / JDBC
 ↓
RDW / DB
```

물리 아키텍처에서는 Logical Node를 실제 Center·VM·JVM·WAR·DB로 내립니다. 여기서 가장 중요한 원칙은 **Server, VM, JVM, WAR가 서로 다른 단위**라는 점입니다. 이 구분이 명확해야 배포, 용량, 장애격리, DR을 같은 모델에서 설명할 수 있습니다.


---

# 1. Logical→Physical Mapping

## FIG-06-02. Logical→Physical Mapping

```text
Logical Node
 ↓
Environment
 ↓
Center
 ↓
Host / VM
 ↓
JVM
 ↓
WAR
 ↓
Port / DB / Storage
```

물리 설계는 Logical Node의 구현입니다. Environment와 Center를 구분하고 그 아래 Compute/Runtime/Artifact를 배치합니다.

Server, VM, JVM, WAR는 서로 다른 Failure/Scale 단위입니다.

이 구분이 있어야 Capacity와 HA를 정확히 설계할 수 있습니다.


---

# 2. GSLB/L4/WEB/WAS 경로

## FIG-06-03. GSLB/L4/WEB/WAS 경로

```text
Client
 ↓
GSLB
 ↓
L4
 ↓
Apache WEB
 ↓
Tomcat WAS
 ↓
Application
```

사용자 요청은 여러 계층을 통과합니다. 각 계층은 다른 책임을 가집니다.

GSLB는 광역/도메인 수준, L4는 서비스 분산, WEB은 Reverse Proxy/정적처리, WAS는 Application Runtime을 담당합니다.

제품명보다 이 책임분리가 중요합니다.


---

# 3. VM/JVM/WAR 분리

## FIG-06-04. VM/JVM/WAR 분리

```text
Physical Server
 ↓
VM
 ↓
OS
 ↓
JVM
 ↓
WAR
```

이 그림은 물리 장에서 가장 중요한 구분입니다.

WAS Server라는 한 단어 안에 VM, JVM, WAR가 섞이면 장애영향과 배포단위를 설명하기 어렵습니다.

JVM 장애는 그 JVM 안의 WAR에 영향을 주지만 같은 VM의 다른 JVM까지 반드시 죽는 것은 아닙니다.


---

# 4. JVM/WAR Isolation

## FIG-06-05. JVM/WAR Isolation

```text
WAS VM
├─ JVM Group A
│  └─ WAR A...
└─ JVM Group B
   └─ WAR B...
```

업무그룹을 JVM으로 분리하면 Resource Contention과 Failure Domain을 줄일 수 있습니다.

다만 JVM 수가 늘면 운영복잡도와 Memory Overhead가 증가합니다.

따라서 Isolation은 Candidate가 아니라 부하/장애시험으로 확정해야 합니다.


---

# 5. Data Physical Path

## FIG-06-06. Data Physical Path

```text
JVM
 ↓
Hikari
 ↓
JDBC
 ↓
DB Service
 ↓
DB Node / Storage
```

Application에서 DB까지도 여러 물리경계가 있습니다.

Connection Pool은 Application Resource이고, DB Session은 Database Resource입니다. 둘을 같은 Capacity 수치로 보면 안 됩니다.

DB HA/Storage 구조는 별도 Evidence가 필요합니다.


---

# 6. Network/Firewall/Port

## FIG-06-07. Network/Firewall/Port

```text
GSLB/L4
 ↓
WEB Port
 ↓
WAS Connector
 ↓
DB Service
 ↓
Management

Firewall Rule ↔ Config ↔ Inventory
```

Physical Architecture는 Network Diagram과 Config가 일치해야 합니다.

문서에 Port를 적는 것만으로 끝나는 것이 아니라 LB, Firewall, Connector, Application Config가 같은 값을 가져야 합니다.

정확한 Port/Hostname은 실제 Inventory 없이 창작하지 않습니다.


---

# 7. Capacity Candidate

## FIG-06-08. Capacity Candidate

```text
A 32C/256G ×4
B 16C/128G ×8
C 16C/128G ×4 ×2 groups

[CANDIDATE]
```

현재 자료에는 여러 Capacity Candidate가 있습니다. 이것을 Production Fact로 쓰면 안 됩니다.

Candidate는 부하모델과 비용, N+1 잔존용량, GC/DB 영향까지 시험한 뒤 승인되어야 합니다.

따라서 문서에는 Candidate 상태를 명시합니다.


---

# 8. Physical Traceability

## FIG-06-09. Physical Traceability

```text
sourceCommit
 ↓
artifactHash
 ↓
deploymentId
 ↓
WAR
 ↓
JVM
 ↓
VM
 ↓
Host / Center
```

물리 아키텍처를 운영과 연결하려면 Deployment Trace가 필요합니다.

'어느 서버에 배포됐다'가 아니라 SourceCommit과 ArtifactHash까지 이어져야 정확한 장애분석과 Rollback이 가능합니다.

현재 이 전수 Mapping이 주요 GAP입니다.

---

# 정상패턴과 금지패턴

## FIG-06-10. Normal Pattern

```text
Logical→VM/JVM/WAR→DB→Evidence
```

정상패턴은 Logical Node를 Center→VM→JVM→WAR로 단계적으로 투영하고 배포 Artifact와 실제 Runtime을 추적하는 것입니다.

## FIG-06-11. Forbidden Pattern

```text
Server=JVM=WAR / Candidate=Fact
```

Server=VM=JVM=WAR처럼 물리단위를 한 단계로 축약하거나 Candidate Capacity를 Production Fact로 표기하는 것을 금지합니다.

---

# Architecture Decision

## FIG-06-12. 주안과 대안

```text
[주안]
중형 VM Scale-out + JVM/WAR Isolation

        VS

[대안]
대형 VM Scale-up 중심
```

Physical은 Working Baseline인 L4→Apache→Tomcat 흐름을 중심으로 하되 VM/JVM/WAR를 분리해 Scale-out과 Isolation을 설계합니다.

대형 VM Scale-up은 운영단순성이 장점이지만 Failure Domain과 GC/Resource Contention이 커질 수 있어 부하시험 없이 기본안으로 확정하지 않습니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---


# Evidence / Conformance — 이 장의 Architecture를 무엇으로 증명하는가

## Evidence Architecture

```text
물리 아키텍처 Architecture Rule
        ↓
Source Evidence
        ↓
Config Evidence
        ↓
Runtime / Deployment Evidence
        ↓
Conformance Test
        ↓
PASS / GAP / ADR
```

### Source Evidence

- `[SOURCE]` Physical/Infrastructure 상세정의
- `[SOURCE]` Capacity/HA/DR 상세정의

### Config Evidence

- `[CONFIG]` GSLB/L4/Apache/Tomcat/JDBC working baseline
- `[CONFIG]` Host/Port/Version은 Inventory Evidence 필요

### Runtime / Deployment Evidence

- `[RUNTIME]` GSLB→L4→Apache→Tomcat/JVM→WAR→DB

### Architecture Decision

- `[DECISION]` Server ≠ VM ≠ JVM ≠ WAR
- `[DECISION]` Scale-out + JVM/WAR Isolation 후보

### Related ADR

- `ADR-026 VM Scale-out Candidate`
- `ADR-027 L4→Apache→Tomcat`
- `ADR-028 JVM Isolation`
- `ADR-034 Immutable Artifact`

### Current GAP / OPEN

- `[GAP/OPEN]` Artifact→Host/JVM/WAR
- `[GAP/OPEN]` 실제 Host/Port/Version
- `[GAP/OPEN]` Placement 승인
- `[GAP/OPEN]` Restore Evidence

## 이 장의 판정

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

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `CMDB/Host/JVM/WAR Inventory`
- `Artifact Deployment Trace`
- `Capacity/Failure Test`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-06-16. 다음 장 Handoff

```text
물리 아키텍처
 ↓
속도와 안정성을 실제 Center·VM·JVM·DB 구조로 구현한다
 ↓
남은 질문
"완벽해 보이는 구조보다 운영 가능한 복구구조를 선택한다"
 ↓
DR 센터 활용 전략
```

Physical 배치가 끝나면 장애가 발생했을 때 어떤 자원이 함께 실패하고 어떤 경로로 우회할지가 보입니다. 다음 장에서는 이 Failure Domain을 Local HA와 Center DR 전략으로 연결합니다.

---

# NSIGHT PDMG 아키텍처 정의서
# 제7장. DR 센터 활용 전략
## Story: “완벽해 보이는 구조보다 운영 가능한 복구구조를 선택한다”


---

# 0. Opening Script

Physical 구조를 만들었으면 다음 질문은 장애와 재해입니다.

DR의 목표는 '센터가 하나 더 있다'가 아닙니다. **Main이 사용할 수 없을 때 Application, Config, Key, Data, Interface를 DR에서 다시 연결하고 Business Transaction이 정상적으로 수행되는 것**이 목표입니다.

## FIG-07-01. 장 전체 Architecture

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

DR 그림은 센터가 하나 더 있다는 사실을 보여주기 위한 것이 아닙니다. 장애를 감지하고 격리한 뒤 Traffic, Application, Config, Key, Data를 DR에서 다시 연결하고 실제 업무를 검증한 후 Failback하는 전체 복구 Chain을 보여줍니다.


---

# 1. HA와 DR 구분

## FIG-07-02. HA와 DR 구분

```text
Local HA
= Node/Process Failure

Center DR
= Site Disaster
```

Local HA와 DR은 같은 문제가 아닙니다.

Node Failure를 N+1로 견디는 것과 Center 전체를 전환하는 것은 범위와 운영절차가 다릅니다.

두 문제를 분리해야 Test Scenario도 분리됩니다.


---

# 2. Main Local HA

## FIG-07-03. Main Local HA

```text
GSLB/L4
 ↓
WEB N+1
 ↓
WAS Active-Active/N+1
 ↓
DB Local HA
```

정상시에는 Local HA가 우선 방어선입니다.

노드 하나가 죽어도 잔존 노드가 부하를 감당해야 하므로 단순 Active-Active가 아니라 N+1 Capacity를 검증해야 합니다.

이 결과가 DR 규모 산정에도 영향을 줍니다.


---

# 3. 센터 장애 전환

## FIG-07-04. 센터 장애 전환

```text
Main Failure
 ↓
Detect
 ↓
Isolate
 ↓
Route Switch
 ↓
DR Runtime
 ↓
Business Check
```

DR에서 가장 중요한 것은 자동전환 여부보다 **전환조건과 검증**입니다.

잘못된 감지로 전환되면 더 큰 장애가 될 수 있으므로 Detect/Isolate가 먼저입니다.

그 다음 Traffic과 Application/Data가 같은 시점에 일관되게 전환되어야 합니다.


---

# 4. Application/Config/Key 복구

## FIG-07-05. Application/Config/Key 복구

```text
Artifact
Config
Secret / JWT Key
Interface Config
Scheduler
Monitoring
 ↓
DR Ready
```

DR은 WAR 파일만 복제한다고 준비되는 것이 아닙니다.

Environment Config, Secret, JWT Key, Interface Endpoint, Monitoring까지 같은 Baseline을 가져야 합니다.

특히 Key 불일치는 인증장애로 직결되므로 DR 데이터로 취급해야 합니다.


---

# 5. DB Consistency

## FIG-07-06. DB Consistency

```text
Application Active-Active
        ≠
DB Write Active-Active

DB
 ↓
Replication / Recovery
 ↓
Consistency Check
```

Application 가용성과 DB 쓰기 정합성은 다른 결정입니다.

DB 양방향 Active-Active는 충돌/순서/정합성 문제가 있으므로 단순히 RTO를 줄인다는 이유로 채택할 수 없습니다.

금융계에서는 검증되지 않은 Write Active-Active보다 정합성을 우선합니다.


---

# 6. RTO/RPO

## FIG-07-07. RTO/RPO

```text
Business Criticality
 ↓
RTO / RPO
 ↓
DR Tier
 ↓
Technology / Procedure
 ↓
Test Evidence
```

RTO/RPO는 Architecture가 임의로 정하는 숫자가 아닙니다.

업무 중요도와 허용손실을 기준으로 결정하고, 그 값을 만족시키기 위해 Technology와 Operation Procedure를 선택해야 합니다.

현재 정확한 값은 OPEN으로 유지합니다.


---

# 7. Failover와 Failback

## FIG-07-08. Failover와 Failback

```text
Main → DR
 Failover
    ↓
Operate on DR
    ↓
Main Recover
    ↓
Re-sync
    ↓
Failback
```

많은 DR 설계가 Failover까지만 생각합니다. 하지만 실제 운영에서는 원센터 복구 후 Failback이 더 어렵습니다.

Data/Config가 다시 동기화되고, Business Cutover 시점이 명확해야 합니다.

Failback까지 Test해야 DR 설계가 닫힙니다.


---

# 8. DR PASS의 의미

## FIG-07-09. DR PASS의 의미

```text
Backup Success
   ≠
DR PASS

DR PASS
= Network
+ App/Config/Key
+ Data
+ Interface
+ Monitoring
+ Business Validation
```

DR PASS는 Backup Job 성공이 아닙니다.

사용자가 실제 업무를 수행하고 결과가 정합하게 저장되는지까지 확인해야 합니다.

따라서 최종 Evidence는 Business Validation입니다.

---

# 정상패턴과 금지패턴

## FIG-07-10. Normal Pattern

```text
Detect→Isolate→Reroute→Recover→Validate→Failback
```

정상패턴은 Local HA로 일반 장애를 흡수하고, Center 장애 시 Detect→Isolate→Reroute→Recover→Validate→Failback을 수행하는 것입니다.

## FIG-07-11. Forbidden Pattern

```text
Backup=DR / Failback 미검증
```

Backup 성공을 DR PASS로 간주하거나 Failback을 검증하지 않은 채 DR 완료로 판단하는 것을 금지합니다.

---

# Architecture Decision

## FIG-07-12. 주안과 대안

```text
[주안]
AP Active-Active/N+1 + DB 정합성 우선 DR

        VS

[대안]
DB 양방향 Active-Active
```

Application은 Stateless Active-Active/N+1을 지향하되 DB는 정합성을 우선한 별도 DR Decision으로 관리합니다.

DB 양방향 Write Active-Active는 빠른 전환의 장점보다 충돌·순서·정합성 운영위험이 크므로 명시적 PoC와 승인 없이는 채택하지 않습니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---


# Evidence / Conformance — 이 장의 Architecture를 무엇으로 증명하는가

## Evidence Architecture

```text
DR 센터 활용 전략 Architecture Rule
        ↓
Source Evidence
        ↓
Config Evidence
        ↓
Runtime / Deployment Evidence
        ↓
Conformance Test
        ↓
PASS / GAP / ADR
```

### Source Evidence

- `[SOURCE]` Physical/HA/DR 정의
- `[SOURCE]` Security Key/Config 동기화 요구

### Config Evidence

- `[CONFIG]` DR Config/Secret/Key/Endpoint 동기화 필요

### Runtime / Deployment Evidence

- `[RUNTIME]` Detect→Isolate→Reroute→Recover→Business Validate→Failback

### Architecture Decision

- `[DECISION]` Application Active-Active와 DB Write Active-Active 분리
- `[DECISION]` DB 정합성 우선

### Related ADR

- `ADR-030 Stateless Active-Active+N+1`
- `ADR-031 Warm/Hot DR`
- `ADR-032 DB HA/DR`
- `ADR-038 Restore Drill`

### Current GAP / OPEN

- `[GAP/OPEN]` RTO/RPO
- `[GAP/OPEN]` DB DR 상세
- `[GAP/OPEN]` Config/Key Sync Evidence
- `[GAP/OPEN]` Failback Evidence

## 이 장의 판정

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

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `RTO/RPO 승인`
- `Restore Test`
- `Failover/Failback Business Validation`
- `Key/Config DR Sync`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-07-16. 다음 장 Handoff

```text
DR 센터 활용 전략
 ↓
완벽해 보이는 구조보다 운영 가능한 복구구조를 선택한다
 ↓
남은 질문
"시스템은 서버가 아니라 표준과 실행규칙으로 움직인다"
 ↓
메커니즘
```

DR까지 정리하면 공간과 복구전략은 준비됩니다. 이제 정상상태에서 거래가 매번 같은 방식으로 움직이도록 만드는 공통 실행규칙이 필요합니다. 그 규칙이 8장의 Mechanism입니다.

---

# NSIGHT PDMG 아키텍처 정의서
# 제8장. 메커니즘
## Story: “시스템은 서버가 아니라 표준과 실행규칙으로 움직인다”


---

# 0. Opening Script

이 장은 PDMG 기술 아키텍처의 중심입니다.

앞 장까지는 공간과 배치를 봤습니다. 이제 같은 서버 위에서 거래가 **어떤 표준 규칙으로 시작되고, 신뢰를 확인하고, ServiceId를 해석하고, Worker와 Transaction을 만들고, Error와 Log를 남기는지**를 봅니다.

이 그림의 핵심은 Component 수가 아니라 **실행정책의 소유권**입니다.

## FIG-08-01. 장 전체 Architecture

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
OnlineTransactionController
 ↓
TcfFacade
 ↓
OnlineTimeoutExecutor
 ↓
TransactionDispatcher
 ↓
Handler
 ↓
Facade / Service
 ↓
DAO / DB
 ↓
Response / Error / Evidence
```

Mechanism은 PDMG 기술 Architecture의 실행규칙입니다. 요청은 Controller보다 먼저 Filter와 Security를 지나고, ServiceId를 해석한 뒤 Worker와 Transaction 안에서 Business를 실행합니다. 따라서 이 장의 핵심은 Component 수가 아니라 **실행정책을 누가 소유하는가**입니다.


---

# 1. Framework와 Business의 역할

## FIG-08-02. Framework와 Business의 역할

```text
Framework
= HOW safely execute

Business
= WHAT to execute

Framework Contract
 ↓
Handler / Facade / Service
```

먼저 책임을 나눕니다. Framework는 Context, Security, Timeout, Transaction, Error, Logging 같은 실행정책을 소유합니다.

Business는 어떤 업무를 수행할지 결정합니다. Framework가 특정 업무 SQL을 알기 시작하면 공통정책과 업무코드가 강결합됩니다.

이 분리가 Mechanism 장의 가장 중요한 원칙입니다.


---

# 2. DefaultFilter — 거래 Context 시작

## FIG-08-03. DefaultFilter — 거래 Context 시작

```text
HTTP
 ↓
DefaultFilter
 ├─ Body Cache
 ├─ GUID/Header
 ├─ ServiceContext
 └─ MDC
 ↓
FilterChain
 ↓
finally clear
```

거래는 Controller에서 시작되는 것이 아닙니다. Filter에서 이미 Context가 만들어지고 GUID/MDC가 준비됩니다.

이 지점이 중요한 이유는 Filter가 Security와 MVC보다 먼저 실행되기 때문입니다. 여기서 만든 Context가 이후 Runtime 전체로 전달됩니다.

마지막 `finally`에서 ThreadLocal을 반드시 제거해야 Cross-request contamination을 막을 수 있습니다.


---

# 3. SecurityFilterChain — Trust Gate

## FIG-08-04. SecurityFilterChain — Trust Gate

```text
DefaultFilter
 ↓
SecurityFilterChain
 ↓
JWT Verify
 ↓
Trusted Principal
 ↓
DispatcherServlet
```

Security는 MVC 이전의 Trust Gate입니다.

여기서 Token이 검증되어야 이후 Controller가 신뢰된 Principal을 사용할 수 있습니다. 다만 현재 `pdmg-jwt`의 RS256 Issue와 Framework HMAC Verify Path는 정합성 GAP가 있습니다.

또한 Principal이 `hdr_nhnis`의 사용자정보와 자동으로 동일하다고 가정하지 않습니다.


---

# 4. ServiceId Resolution과 TCF

## FIG-08-05. ServiceId Resolution과 TCF

```text
Context Header
 ↓
Request Header
 ↓
Path Variable
 ↓
ServiceId
 ↓
TcfFacade
 ↓
TransactionDispatcher
```

PDMG의 Runtime Routing Key는 ServiceId입니다.

현재 Controller는 여러 Source에서 ServiceId를 얻을 수 있으므로, 값이 다를 때 어떻게 처리할지 명시적인 방어가 필요합니다. 단순 precedence만 있고 mismatch rejection이 없다면 잘못된 거래로 라우팅될 위험이 있습니다.

따라서 ServiceId 일치검증은 PROPOSED Defense로 관리합니다.


---

# 5. Handler Registry

## FIG-08-06. Handler Registry

```text
Spring Handlers
 ↓ startup
Registry
 ServiceId → Handler
 ↓
Duplicate?
 ├─ YES → startup fail
 └─ NO  → runtime route
```

Handler Registry는 기동 시 만들어지는 Runtime Routing Table입니다.

Duplicate ServiceId는 기동오류로 처리하는 것이 안전합니다. 또한 `serviceIds()`에 등록된 ID와 `handle()` 내부 branch가 서로 일치해야 합니다.

현재 Registry 13개와 UI Transaction Catalog 차이는 Drift 후보입니다.


---

# 6. Worker / Timeout

## FIG-08-07. Worker / Timeout

```text
Request Thread
 ↓ submit
pdmg-online-N
 ↓
pool-size 20
queue 100
deadline 5000ms
 ↓
Business
```

Timeout Mechanism은 Request Thread와 Worker Thread를 분리합니다.

여기서 20/100/5000은 Current Snapshot이지 Target SLA가 아닙니다. 이 수치를 Capacity 목표로 그대로 승격하면 안 됩니다.

Worker Pool은 Tomcat Thread Pool과도, Hikari Pool과도 다른 Resource입니다.


---

# 7. TransactionTemplate

## FIG-08-08. TransactionTemplate

```text
Worker
 ↓
TransactionTemplate BEGIN
 ↓
Dispatcher / Handler
 ↓
Facade @Transactional(REQUIRED)
 ↓
Service / DAO
 ↓
Deadline Check
 ↓
COMMIT / ROLLBACK
```

실제 DB Transaction은 Worker 안의 `TransactionTemplate`에서 시작됩니다.

Facade의 `@Transactional(REQUIRED)`는 새로운 Transaction을 만드는 것보다 외부 Transaction에 참여할 수 있습니다.

따라서 TCF ON/OFF에서 Facade를 공통 Business Boundary로 맞추는 것이 Transaction 일관성 측면에서도 중요합니다.


---

# 8. Error/Response/Logging

## FIG-08-09. Error/Response/Logging

```text
Exception
 ↓
GlobalExceptionHandler
 ↓
ErrorCode / HTTP
 ↓
hdr_nhnis + result
 ↓
Response Advice
 ↓
ImageLog / MDC / Evidence
```

실패도 표준 흐름을 가져야 합니다.

Known Error는 Taxonomy와 Envelope로 변환되지만 Filter/Security 단계의 Early Error는 MVC Advice를 우회할 수 있습니다. 이 때문에 Error Contract가 완전히 일관되지는 않습니다.

Logging은 GUID/ServiceId로 연결하되 Token/Secret/민감 DTO를 기록하지 않는 원칙이 필요합니다.

---

# 정상패턴과 금지패턴

## FIG-08-10. Normal Pattern

```text
Filter→Security→TCF→Worker/TX→Facade→Service→DAO
```

정상패턴은 Framework가 Context/Security/Timeout/TX를 소유하고 Handler와 Controller가 공통 Facade를 통해 같은 Business Core를 실행하는 것입니다.

## FIG-08-11. Forbidden Pattern

```text
Handler→DAO / TCF OFF Common Core 우회
```

Handler→DAO 직접호출, TCF OFF에서 Business Core 우회, Client Header를 Trusted Identity로 간주하는 구조는 금지/개선 대상으로 둡니다.

---

# Architecture Decision

## FIG-08-12. 주안과 대안

```text
[주안]
Handler/Controller 모두 Common Facade

        VS

[대안]
TCF OFF Controller→Service Direct
```

TCF ON Handler와 TCF OFF Controller가 모두 Common Facade를 호출하도록 Business Use Case Boundary를 통일합니다.

OFF 경로에서 Service를 직접 호출하면 Transaction/AOP/정책 적용점이 달라져 두 Runtime의 Business 의미가 Drift할 수 있습니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---


# Evidence / Conformance — 이 장의 Architecture를 무엇으로 증명하는가

## Evidence Architecture

```text
메커니즘 Architecture Rule
        ↓
Source Evidence
        ↓
Config Evidence
        ↓
Runtime / Deployment Evidence
        ↓
Conformance Test
        ↓
PASS / GAP / ADR
```

### Source Evidence

- `[SOURCE]` DefaultFilter / ServiceContext / TCF / Dispatcher / Handler / GlobalExceptionHandler 분석
- `[SOURCE]` TCF ON/OFF Source 분석

### Config Evidence

- `[CONFIG]` tcf.enabled=true
- `[CONFIG]` timeout.enabled=true
- `[CONFIG]` timeout=5000ms
- `[CONFIG]` pool-size=20
- `[CONFIG]` queue-capacity=100
- `[CONFIG]` legacy-web/filter enabled

### Runtime / Deployment Evidence

- `[RUNTIME]` DefaultFilter→SecurityFilterChain→DispatcherServlet→Interceptor→Controller→TcfFacade→TimeoutExecutor→Worker TX→Dispatcher→Handler→Facade→Service→DAO→DB

### Architecture Decision

- `[DECISION]` Framework=HOW / Business=WHAT
- `[DECISION]` TCF ON/OFF 공통 Facade Business Core

### Related ADR

- `ADR-004 Common Facade`
- `ADR-005 TCF Policy`
- `ADR-009 Immutable Worker Context`
- `ADR-017 Timeout Budget`

### Current GAP / OPEN

- `[GAP/OPEN]` Mutable Worker Context
- `[GAP/OPEN]` ServiceId mismatch defense
- `[GAP/OPEN]` TCF OFF Controller→Service Drift
- `[GAP/OPEN]` Early Error Envelope
- `[GAP/OPEN]` Generic Error Coverage

## 이 장의 판정

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

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `JWT Verifier 정합`
- `Immutable Worker Context`
- `TCF OFF→Facade 정렬`
- `ServiceId mismatch 방어`
- `Error Envelope 정합`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-08-16. 다음 장 Handoff

```text
메커니즘
 ↓
시스템은 서버가 아니라 표준과 실행규칙으로 움직인다
 ↓
남은 질문
"정적인 Architecture를 거래 한 건의 시간축으로 펼쳐본다"
 ↓
런타임 서비스
```

Mechanism은 실행규칙을 정의했습니다. 다음 장에서는 이 규칙을 거래 한 건의 시간축으로 펼쳐 Request Thread, Worker Thread, Transaction, DB, Timeout이 실제 어떻게 상호작용하는지 확인합니다.

---

# NSIGHT PDMG 아키텍처 정의서
# 제9장. 런타임 서비스
## Story: “정적인 Architecture를 거래 한 건의 시간축으로 펼쳐본다”


---

# 0. Opening Script

8장에서 실행규칙을 봤다면, 9장에서는 그 규칙이 실제 거래 한 건에서 어떤 순서로 움직이는지 봅니다.

이 장에서는 박스의 위치보다 **시간**이 중요합니다. Request Thread가 언제 Worker에게 일을 넘기고, Transaction이 언제 시작되고, DB가 늦어졌을 때 어떤 현상이 위로 전파되는지 시간축으로 펼칩니다.

## FIG-09-01. 장 전체 Architecture

```text
════════ Request Thread ════════
HTTP
 ↓
Filter
 ↓
Security
 ↓
MVC / Controller
 ↓
Future.get(timeout)
        │ submit
        ▼
════════ Worker Thread ═════════
Context Install
 ↓
Transaction BEGIN
 ↓
Dispatcher / Handler
 ↓
Facade / Service / DAO
 ↓
DB
 ↓
Deadline
 ↓
Commit / Rollback
        │
        ▼
Response / Evidence
```

Runtime에서는 8장의 정적인 Mechanism을 시간축으로 펼칩니다. 특히 `submit`을 기준으로 Request Thread와 Worker Thread가 갈라지고, DB Transaction은 Worker 쪽에서 시작됩니다. 이 차이를 이해해야 504, Overload, DB 지연을 정확히 해석할 수 있습니다.


---

# 1. Request Thread 역할

## FIG-09-02. Request Thread 역할

```text
Request Thread
├─ Filter/Security/MVC
├─ Controller
├─ Worker submit
├─ Future wait
└─ HTTP Response
```

Request Thread는 HTTP와 MVC 수명을 담당합니다.

Business Transaction 전체를 직접 수행하지 않고 Worker에게 위임하고 완료를 기다립니다. 이 분리가 Timeout의 의미를 복잡하게 만드는 동시에 Request Thread 보호장치가 됩니다.

그래서 Request Thread의 종료와 Business 작업 종료를 같은 것으로 보면 안 됩니다.


---

# 2. Worker Thread 역할

## FIG-09-03. Worker Thread 역할

```text
pdmg-online-N
├─ Context Install
├─ Transaction BEGIN
├─ Dispatch
├─ Business
├─ DB
├─ Deadline
└─ Commit/Rollback
```

Worker는 Business Execution의 실제 주체입니다.

Request Thread에서 캡처한 Context/MDC를 설치하고 Transaction을 시작한 뒤, Handler/Facade/Service/DAO를 실행합니다.

작업이 끝나면 반드시 Worker Context를 clear해야 합니다.


---

# 3. ServiceId Runtime Routing

## FIG-09-04. ServiceId Runtime Routing

```text
Request
 ↓
ServiceId
 ↓
Registry
 ↓
Handler
 ↓
Facade Method
 ↓
Business Use Case
```

Runtime에서 ServiceId는 단순 Header가 아니라 Routing Key입니다.

잘못된 ServiceId는 잘못된 Handler로 연결되므로 Registry uniqueness와 Context/Header/Path 일치검증이 중요합니다.

이 흐름은 Naming/Traceability 장과 연결됩니다.


---

# 4. DB Runtime

## FIG-09-05. DB Runtime

```text
Worker
 ↓
Transaction
 ↓
Hikari Connection
 ↓
JDBC Statement
 ↓
DB Session
 ↓
SQL / Wait / Result
```

DB에 도달하기까지도 여러 Resource Boundary가 있습니다.

Worker가 있다고 Connection이 있는 것은 아니며, Connection이 있다고 DB가 즉시 응답하는 것도 아닙니다.

성능문제는 이 체인을 따라 역으로 전파됩니다.


---

# 5. Timeout 504

## FIG-09-06. Timeout 504

```text
Future.get(5000ms)
 ├─ complete → success
 └─ timeout
      ↓
   cancel(true)
      ↓
   HTTP 504

Worker may continue
```

504는 Client에게 보이는 결과일 뿐 내부작업의 종료증명이 아닙니다.

`cancel(true)`가 Interrupt를 전달해도 JDBC Driver나 DB가 즉시 취소되는지는 별도 Integration Test가 필요합니다.

그래서 Query Timeout과 Transaction Deadline을 계층적으로 설계해야 합니다.


---

# 6. Overload 503

## FIG-09-07. Overload 503

```text
Worker 20
 ↓
Queue 100
 ↓
Full
 ↓
Reject
 ↓
OnlineOverloadException
 ↓
HTTP 503
```

Overload는 느린 응답과 다르게 **수용할 수 없는 부하를 빠르게 거절하는 정책**입니다.

Queue를 무한히 키우면 Timeout만 늘어납니다. 제한된 Worker/Queue와 503은 Backpressure의 일부입니다.

Target Capacity는 부하시험으로 재산정해야 합니다.


---

# 7. Saturation Cascade

## FIG-09-08. Saturation Cascade

```text
DB Slow
 ↓
Hikari Pending
 ↓
Worker Occupied
 ↓
Queue Increase
 ↓
Request Wait
 ↓
504 / 503
```

이 그림이 Runtime 장의 핵심 Failure Story입니다.

DB가 느리면 DB만 느린 것이 아니라 Connection, Worker, Queue, Request Thread로 영향이 올라옵니다.

따라서 Capacity는 Tomcat `maxThreads` 하나로 결정할 수 없습니다.


---

# 8. Runtime Evidence

## FIG-09-09. Runtime Evidence

```text
GUID
+ ServiceId
+ Thread
+ SqlId
+ ErrorCode
+ elapsed
+ deploymentId
+ host/jvm
 ↓
Evidence
```

Runtime을 설계했다면 마지막에는 증적이 남아야 합니다.

현재 GUID/ServiceId/MDC/ImageLog는 강한 기반입니다. 여기에 DeploymentId, Host/JVM, SqlId를 연결하면 Source부터 Runtime까지 추적할 수 있습니다.

이 연결이 자동화되어야 HG90 Gate가 실제로 동작합니다.

---

# 정상패턴과 금지패턴

## FIG-09-10. Normal Pattern

```text
Request→Worker→TX→DB→Response/Evidence
```

정상패턴은 Request와 Worker의 수명을 구분하고, Resource Chain 전체에 Backpressure와 계층형 Timeout을 적용하는 것입니다.

## FIG-09-11. Forbidden Pattern

```text
504=Worker 종료 / Worker=DB Session
```

HTTP 504를 Worker 종료나 DB Rollback 완료로 해석하거나 Queue를 무한 확장해 부하를 숨기는 방식을 금지합니다.

---

# Architecture Decision

## FIG-09-12. 주안과 대안

```text
[주안]
Request/Worker 분리 + 계층형 Timeout

        VS

[대안]
Request Thread 단일 실행
```

Request/Worker 분리 구조를 유지하면서 DB Query Timeout부터 Client Timeout까지 계층형 Budget을 정의하는 것을 주안으로 합니다.

단일 Request Thread에서 모든 작업을 실행하면 구조는 단순하지만 Slow DB가 HTTP Thread를 직접 점유해 Saturation 전파가 빨라질 수 있습니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---


# Evidence / Conformance — 이 장의 Architecture를 무엇으로 증명하는가

## Evidence Architecture

```text
런타임 서비스 Architecture Rule
        ↓
Source Evidence
        ↓
Config Evidence
        ↓
Runtime / Deployment Evidence
        ↓
Conformance Test
        ↓
PASS / GAP / ADR
```

### Source Evidence

- `[SOURCE]` OnlineTimeoutExecutor / TransactionTemplate / TransactionDispatcher / Handler Source 분석
- `[SOURCE]` GlobalExceptionHandler 및 Runtime 흐름 분석

### Config Evidence

- `[CONFIG]` timeout 5000ms / worker 20 / queue 100
- `[CONFIG]` Query Timeout/Hikari exact target 값 미확정

### Runtime / Deployment Evidence

- `[RUNTIME]` Request Thread와 Worker Thread 분리
- `[RUNTIME]` HTTP 504 ≠ Worker 종료 ≠ JDBC Cancel ≠ Rollback 완료
- `[RUNTIME]` 503 Overload Backpressure

### Architecture Decision

- `[DECISION]` DB Query Timeout < Worker/Transaction Deadline < Downstream/Server < Client Timeout

### Related ADR

- `ADR-017 Timeout Budget`
- `ADR-018 Retry/Idempotency`
- `ADR-029 Capacity Budget`
- `ADR-040 Runtime Evidence`

### Current GAP / OPEN

- `[GAP/OPEN]` JDBC Query Timeout
- `[GAP/OPEN]` JDBC Cancel Evidence
- `[GAP/OPEN]` Late Worker
- `[GAP/OPEN]` Deployment/Host Correlation
- `[GAP/OPEN]` Identity Binding

## 이 장의 판정

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

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `DB Query Timeout`
- `JDBC Cancel/Late Worker Test`
- `Overload/Capacity Test`
- `Deployment Correlation`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-09-16. 다음 장 Handoff

```text
런타임 서비스
 ↓
정적인 Architecture를 거래 한 건의 시간축으로 펼쳐본다
 ↓
남은 질문
"RDW는 실시간을 지키고 ADW는 분석을 극대화한다"
 ↓
데이터플랫폼
```

Runtime을 시간축으로 보면 성능과 장애가 어디서 전파되는지 설명할 수 있습니다. 다음 장에서는 그 Runtime의 최종 대상인 Data를 Operational과 Analytical Workload로 분리합니다.

---

# NSIGHT PDMG 아키텍처 정의서
# 제10장. 데이터플랫폼
## Story: “RDW는 실시간을 지키고 ADW는 분석을 극대화한다”


---

# 0. Opening Script

Runtime의 끝에는 항상 Data가 있습니다. 하지만 Data Architecture를 DB 목록으로 끝내면 안 됩니다.

이 장에서는 **어떤 Data가 누구의 소유인지, Online Workload와 Analytical Workload를 어디에서 분리할지, ServiceId에서 Table까지 어떻게 Trace할지**를 봅니다.

## FIG-10-01. 장 전체 Architecture

```text
PDMG Online
 ↓
Service
 ↓
DAO / Mapper / SqlId
 ↓
RDW
 ↓
Operational

NSIGHT Analytics
Source / RDW
 ↓
ETL / Data Movement
 ↓
ADW
 ↓
BI / Heavy Analysis
```

Data Platform은 DB 목록이 아니라 Workload와 Ownership을 설계하는 장입니다. PDMG Current의 RDW 경로와 NSIGHT Target의 ADW/CDC/ETL을 구분하고, ServiceId에서 SqlId와 Table까지 추적 가능한 Data Lineage를 목표로 합니다.


---

# 1. Data Architecture vs DB

## FIG-10-02. Data Architecture vs DB

```text
Data Architecture
Ownership / Flow / Quality / Security / Lifecycle
        ↓
DB Architecture
Schema / Instance / HA / Storage
```

Data Architecture는 Database Architecture보다 상위개념입니다.

어디에 저장하는지보다 누가 소유하고 어떤 업무가 사용하며 어떤 품질/보안정책을 적용할지가 먼저입니다.

DB 제품과 Instance는 그 정책을 구현하는 수단입니다.


---

# 2. PDMG Current Data Access

## FIG-10-03. PDMG Current Data Access

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
 ↓
JDBC
 ↓
RDW / DB
```

PDMG Current에서 가장 강하게 확인되는 Data Access는 MyBatis/JDBC를 통한 RDW/DB 경로입니다.

이 구조를 AS-IS Backbone으로 두고 ADW나 External Data는 별도 Inventory로 확인합니다.

확인되지 않은 ADW 직접접근을 Current로 그리지 않습니다.


---

# 3. RDW 역할

## FIG-10-04. RDW 역할

```text
Online Service
 ↓
RDW
Operational / Near-real-time
 ↓
bounded latency
```

RDW는 Online/Operational Workload를 지키는 영역으로 봅니다.

여기에는 즉시 조회·거래에 필요한 데이터가 집중되고, Heavy Analysis가 자원을 독점하지 않도록 해야 합니다.

실제 Table/Schema Ownership은 추가 Registry가 필요합니다.


---

# 4. ADW 역할

## FIG-10-05. ADW 역할

```text
RDW / Source
 ↓
ETL / Data Movement
 ↓
ADW
 ↓
Mart / Aggregate
 ↓
BI / Analytics
```

ADW는 분석과 집계, Mart, Heavy Query를 담당하는 Target 역할입니다.

PDMG Current가 ADW를 직접 사용하는지는 아직 Datasource/Mapper Inventory가 필요합니다.

따라서 ADW는 Target Reference와 Current Evidence를 구분해 설명합니다.


---

# 5. ServiceId→Table Trace

## FIG-10-06. ServiceId→Table Trace

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

Data Lineage를 Application과 연결하는 핵심축입니다.

어떤 ServiceId가 어떤 SqlId와 Table을 사용하는지 추적할 수 있어야 변경영향과 성능분석이 가능합니다.

이 Trace는 수동 문서가 아니라 자동 Index가 목표입니다.


---

# 6. CDC와 ETL 분리

## FIG-10-07. CDC와 ETL 분리

```text
Change Data
Source DB → CDC → RDW/Consumer

Bulk Data
Source → ETL → ADW
```

CDC와 ETL은 목적이 다릅니다.

CDC는 Change를 빠르게 전달하고, ETL은 대량 변환/적재에 적합합니다. 하나를 다른 목적에 무리하게 사용하면 지연이나 운영복잡도가 커집니다.

CDC Freshness 3s와 30s 충돌은 SLA Tier로 정리해야 합니다.


---

# 7. Data Ownership / Direct DML

## FIG-10-08. Data Ownership / Direct DML

```text
Own Data
 Read / Write

Other System Data
 ↓
Approved Contract

Direct DML
 X
```

Data Ownership이 불명확하면 시스템 경계도 무너집니다.

타 시스템 Table을 직접 DML하면 변경 영향, Lock, 보안, 운영책임이 강하게 결합됩니다.

따라서 Cross-system Direct DML은 기본 금지로 보고 예외는 ADR로 관리합니다.


---

# 8. Data Evidence

## FIG-10-09. Data Evidence

```text
ServiceId
 ↓
SqlId
 ↓
Table
 ↓
elapsed / rows / error
 ↓
GUID
 ↓
Evidence
```

Data Architecture도 Runtime Evidence로 닫아야 합니다.

어떤 거래가 어떤 SQL을 실행했고 얼마나 걸렸는지 연결되면 Performance와 Data Lineage를 함께 볼 수 있습니다.

이 Evidence가 13장 Closed Loop의 입력이 됩니다.

---

# 정상패턴과 금지패턴

## FIG-10-10. Normal Pattern

```text
ServiceId→SqlId→Table / Online→RDW / Analysis→ADW
```

정상패턴은 Online Operational Workload를 RDW에서 보호하고 Heavy Analysis는 ADW로 분리하며 Data Access를 ServiceId→SqlId→Table까지 추적하는 것입니다.

## FIG-10-11. Forbidden Pattern

```text
Cross-system DML / Heavy Query→Online
```

Cross-system Direct DML, Heavy BI Query의 Online RDW 직접 수행, 근거 없는 ADW Current 표시를 금지합니다.

---

# Architecture Decision

## FIG-10-12. 주안과 대안

```text
[주안]
RDW/ADW Workload 분리

        VS

[대안]
RDW 중심 통합사용
```

RDW는 Operational SLA를 보호하고 ADW는 Analytical Workload를 담당하도록 역할을 분리합니다.

RDW 중심 통합사용은 Data Copy를 줄일 수 있지만 Heavy Analysis가 Online Workload와 Resource를 경쟁하게 되므로 기본안으로 두지 않습니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---


# Evidence / Conformance — 이 장의 Architecture를 무엇으로 증명하는가

## Evidence Architecture

```text
데이터플랫폼 Architecture Rule
        ↓
Source Evidence
        ↓
Config Evidence
        ↓
Runtime / Deployment Evidence
        ↓
Conformance Test
        ↓
PASS / GAP / ADR
```

### Source Evidence

- `[SOURCE]` DAO / Mapper / SqlId / RDW Current 경로
- `[SOURCE]` Data Architecture / Interface / Event-CDC-ETL 분석

### Config Evidence

- `[CONFIG]` Datasource/Mapper Inventory 필요
- `[CONFIG]` CDC SLA baseline 충돌 3s vs 30s

### Runtime / Deployment Evidence

- `[RUNTIME]` ServiceId→DAO→Mapper→SqlId→Table Trace
- `[RUNTIME]` RDW Operational / ADW Analytical Target

### Architecture Decision

- `[DECISION]` RDW/ADW Workload Separation
- `[DECISION]` Cross-system Direct DML 금지

### Related ADR

- `ADR-015 Direct DB 제한`
- `ADR-021 RDW/ADW 분리`
- `ADR-022 CDC SLA`

### Current GAP / OPEN

- `[GAP/OPEN]` RDW/ADW 실제 Mapping
- `[GAP/OPEN]` Ownership
- `[GAP/OPEN]` Lineage/DQ 자동화
- `[GAP/OPEN]` CDC SLA Conflict

## 이 장의 판정

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

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `RDW/ADW Datasource Mapping`
- `Table Ownership/Lineage`
- `CDC SLA ADR`
- `Data Runtime Evidence`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-10-16. 다음 장 Handoff

```text
데이터플랫폼
 ↓
RDW는 실시간을 지키고 ADW는 분석을 극대화한다
 ↓
남은 질문
"배치형 캠페인에서 고객 행동에 반응하는 플랫폼으로"
 ↓
마케팅플랫폼
```

Data Platform에서 신뢰할 수 있는 Operational/Analytical 경계를 만들었습니다. 다음 장에서는 이 Data와 PDMG Runtime이 Marketing Platform의 실행구조에서 어떻게 사용되는지 연결합니다.

---

# NSIGHT PDMG 아키텍처 정의서
# 제11장. 마케팅플랫폼
## Story: “배치형 캠페인에서 고객 행동에 반응하는 플랫폼으로”


---

# 0. Opening Script

데이터가 준비되면 그 데이터를 실제 고객 접점에서 사용하는 실행플랫폼이 필요합니다.

이 장에서 PDMG는 Marketing Platform 전체를 의미하지 않습니다. PDMG는 **UI·JWT·Framework·ServiceId 기반 Business Runtime을 보여주는 Current Reference**입니다.

NSIGHT의 Event/Kafka/Real-time Target은 별도 Target으로 분리해 설명합니다.

## FIG-11-01. 장 전체 Architecture

```text
NSIGHT Marketing Platform
        ↓
Approved Mapping
        ↓
PDMG Reference
UI / JWT / Framework / ServiceId
        ↓
Business Runtime
        ↓
RDW / Interface

Target Extension
Event / Kafka / Real-time Decision
```

Marketing Platform에서는 PDMG를 전체 마케팅 플랫폼과 동일시하지 않습니다. PDMG는 ServiceId 기반의 실행 Runtime Reference이며, Event/Kafka 기반 Real-time Marketing은 NSIGHT Target으로 분리합니다. 두 세계는 명시적인 Mapping Registry와 Interface Contract로 연결합니다.


---

# 1. PDMG Runtime Reference

## FIG-11-02. PDMG Runtime Reference

```text
pdmg-ui
pdmg-jwt
pdmg-service
 └─ pdmg-fw
pdmg-om [UNKNOWN]
```

PDMG는 Marketing Application의 실행패턴을 보여주는 중요한 Reference입니다.

다만 `pdmg-om`은 Current 구현범위가 충분히 확인되지 않았고, Marketing Platform 전체 기능을 PDMG로 대표할 수는 없습니다.

그래서 Current Reference라는 표현을 유지합니다.


---

# 2. Program과 ServiceId

## FIG-11-03. Program과 ServiceId

```text
mg | co | a | 9001
 ↓
mgcoa9001
 ↓
mgcoa9001S0
 ↓
Handler / Use Case
```

Marketing Runtime의 Source Trace는 Program과 ServiceId에서 시작합니다.

Program ID는 업무코드 축을 반영하고 ServiceId는 거래유형까지 포함합니다.

이 ID는 Registry, Logging, SQL Trace까지 연결됩니다.


---

# 3. Business Runtime

## FIG-11-04. Business Runtime

```text
UI
 ↓
ServiceId
 ↓
Handler
 ↓
Facade
 ↓
Service
 ↓
DAO / RDW
```

현재 PDMG의 강점은 ServiceId에서 Business Layer와 Data Access까지 이어지는 실행축입니다.

TCF ON에서는 Handler가 Facade를 향하고, Target에서는 TCF OFF Controller도 같은 Facade를 향하도록 정렬하는 것이 주안입니다.

이렇게 해야 Marketing Use Case의 경계가 Entry Mechanism에 따라 달라지지 않습니다.


---

# 4. Customer Context / Identity

## FIG-11-05. Customer Context / Identity

```text
JWT Principal
 ↓
Business User Context
 ↓
Customer / Session Context
 ↓
ServiceId Execution
```

마케팅은 고객맥락을 많이 사용하기 때문에 Identity Binding이 중요합니다.

JWT의 `sub/ssoId`와 Header의 사용자정보를 신뢰 없이 합치면 잘못된 고객문맥이 업무에 들어갈 수 있습니다.

Security GAP를 Marketing Runtime의 핵심조건으로 봅니다.


---

# 5. External Interface

## FIG-11-06. External Interface

```text
Marketing Service
 ↓
Interface Contract
 ↓
API / Event / File
 ↓
External Platform
```

Marketing은 여러 외부/내부 시스템과 연결될 가능성이 높습니다.

하지만 연결선이 많아질수록 P2P 강결합 위험이 커집니다. 그래서 Interface Contract를 통해 호출/이벤트/파일을 분리합니다.

Current Inventory가 없으면 특정 연계기술을 AS-IS로 단정하지 않습니다.


---

# 6. Event/Kafka Target

## FIG-11-07. Event/Kafka Target

```text
Customer Action
 ↓
Event
 ↓
Kafka / Event Platform
 ↓
Consumer / Decision
 ↓
Response / Offering

[NSIGHT TARGET]
```

실시간 마케팅 Target은 고객행동을 Event로 처리하는 방향을 가질 수 있습니다.

하지만 이 구조가 PDMG Current Source에 구현되어 있다고 자동으로 말하지 않습니다.

Current HTTP/TCF Runtime과 Target Event Runtime을 명확히 분리합니다.


---

# 7. MP ↔ mg Mapping

## FIG-11-08. MP ↔ mg Mapping

```text
NSIGHT Target
MP
 │
 │ Mapping Registry / ADR
 ▼
PDMG AS-IS
mg

MP ≠ mg automatically
```

이것은 Naming이 아니라 Architecture Governance 문제입니다.

NSIGHT의 `MP`와 PDMG Source의 `mg`는 이름이 비슷해도 동일한 식별자라고 자동 가정하지 않습니다.

공식 Mapping Registry와 ADR가 필요합니다.


---

# 8. Marketing Runtime Evidence

## FIG-11-09. Marketing Runtime Evidence

```text
Customer Action
 ↓
ServiceId
 ↓
GUID
 ↓
Business / Data / Interface
 ↓
Result
 ↓
Evidence
```

마케팅 실행도 결국 Evidence로 닫아야 합니다.

어떤 고객행동이 어떤 ServiceId와 Data/Interface를 거쳐 어떤 결과를 만들었는지 추적할 수 있어야 합니다.

이 축이 캠페인/추천 품질분석에도 연결될 수 있습니다.

---

# 정상패턴과 금지패턴

## FIG-11-10. Normal Pattern

```text
MP→Mapping→PDMG ServiceId→Runtime
```

정상패턴은 PDMG Current Runtime과 NSIGHT Marketing Target을 분리하고 공식 Mapping/Interface Contract로만 연결하는 것입니다.

## FIG-11-11. Forbidden Pattern

```text
MP=mg / Target Event=Current
```

`mg`를 `MP`로 자동 치환하거나 NSIGHT Event Target을 PDMG Current 구현으로 표시하지 않습니다.

---

# Architecture Decision

## FIG-11-12. 주안과 대안

```text
[주안]
MP↔PDMG Mapping Registry + ADR

        VS

[대안]
mg를 MP로 일괄 치환
```

NSIGHT `MP`와 PDMG `mg`는 Mapping Registry/ADR로 명시적으로 연결합니다. 이름 유사성보다 책임과 Application Code SSOT가 기준입니다.

일괄 치환은 빠르지만 RD/AD/BI 등 다른 Group과의 Canonical Key 정합을 훼손할 수 있으므로 사용하지 않습니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---


# Evidence / Conformance — 이 장의 Architecture를 무엇으로 증명하는가

## Evidence Architecture

```text
마케팅플랫폼 Architecture Rule
        ↓
Source Evidence
        ↓
Config Evidence
        ↓
Runtime / Deployment Evidence
        ↓
Conformance Test
        ↓
PASS / GAP / ADR
```

### Source Evidence

- `[SOURCE]` PDMG Application/ServiceId Source
- `[SOURCE]` Application Code Definition: MP/RD/AD/BI/DG/IM
- `[SOURCE]` PDMG mg Source Naming

### Config Evidence

- `[CONFIG]` Program/ServiceId Registry
- `[CONFIG]` JWT/Identity Runtime Config

### Runtime / Deployment Evidence

- `[RUNTIME]` Program→ServiceId→Handler→Facade→Service→DAO/RDW
- `[RUNTIME]` Event/Kafka는 Target Reference

### Architecture Decision

- `[DECISION]` NSIGHT MP ↔ PDMG mg는 Mapping Registry/ADR로 연결
- `[DECISION]` 자동 치환 금지

### Related ADR

- `ADR-003 Mapping Registry`
- `ADR-013 Identity Binding`
- `ADR-014 Interface Selection`

### Current GAP / OPEN

- `[GAP/OPEN]` MP↔mg Mapping
- `[GAP/OPEN]` JWT/Identity
- `[GAP/OPEN]` External Interface Inventory
- `[GAP/OPEN]` Event Current Ownership
- `[GAP/OPEN]` pdmg-om

## 이 장의 판정

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

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `MP↔mg Mapping Registry`
- `Identity Binding`
- `Interface Inventory`
- `Event Target Ownership`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-11-16. 다음 장 Handoff

```text
마케팅플랫폼
 ↓
배치형 캠페인에서 고객 행동에 반응하는 플랫폼으로
 ↓
남은 질문
"데이터 플랫폼이 신뢰를 만들고 BI는 판단 속도를 높인다"
 ↓
BI 포탈
```

Marketing은 실행의 속도를 높이는 영역입니다. 다음 장 BI Portal에서는 같은 Data Platform을 기반으로 실행이 아니라 판단의 속도를 높이는 Analytical Consumer 구조를 봅니다.

---

# NSIGHT PDMG 아키텍처 정의서
# 제12장. BI 포탈
## Story: “데이터 플랫폼이 신뢰를 만들고 BI는 판단 속도를 높인다”


---

# 0. Opening Script

Marketing Platform이 실행을 담당한다면 BI는 판단을 지원합니다.

중요한 것은 BI가 PDMG 내부 DAO나 Online DB를 직접 끌어다 쓰는 구조가 아니라, **Data Platform이 제공하는 신뢰된 Dataset과 Contract를 소비하는 별도 Analytical Boundary**라는 점입니다.

이 장은 PDMG Current보다는 NSIGHT Target Reference 성격이 강합니다.

## FIG-12-01. 장 전체 Architecture

```text
Operational Data
 ↓
RDW
 ↓
ADW
 ↓
Data Contract
 ↓
BI Portal
 ↓
Report / Self-BI / Analysis
```

BI Portal은 Online Runtime의 연장이 아니라 별도의 Analytical Boundary입니다. RDW의 운영 데이터를 ADW와 신뢰된 Dataset으로 가공해 BI가 소비하도록 하고, Heavy Query가 Online Transaction 자원을 침범하지 않도록 분리합니다.


---

# 1. BI Boundary

## FIG-12-02. BI Boundary

```text
PDMG Operational Runtime
        ≠
BI Analytical Runtime
```

BI는 PDMG 내부 Module이 아닙니다.

두 Runtime은 Workload, Scale, Data Access Pattern이 다르기 때문에 별도 Boundary로 봅니다.

이 분리가 Online SLA 보호의 시작입니다.


---

# 2. RDW→ADW

## FIG-12-03. RDW→ADW

```text
RDW
Operational
 ↓
ETL / Data Movement
 ↓
ADW
Analytical
```

BI가 Online RDW를 직접 Heavy Query하면 운영거래에 영향을 줄 수 있습니다.

따라서 분석 Workload는 ADW나 Analytical Platform으로 이동시키는 방향이 기본입니다.

실제 Refresh/Freshness SLA는 별도 Evidence가 필요합니다.


---

# 3. Data Contract

## FIG-12-04. Data Contract

```text
Dataset
├─ Owner
├─ Schema
├─ Version
├─ Freshness
├─ Security
└─ SLA
```

BI와 Data Platform 사이의 Contract는 API보다 Dataset Contract가 중심이 될 수 있습니다.

이 Contract가 없으면 Report마다 같은 지표를 다르게 계산하고 데이터 품질 책임이 불명확해집니다.

따라서 Dataset Owner와 Metric Definition이 중요합니다.


---

# 4. Report / Self-BI

## FIG-12-05. Report / Self-BI

```text
ADW Dataset
 ↓
BI Semantic Layer
 ↓
Report
 ↓
Self-BI
 ↓
Decision
```

BI Portal은 단순 Report Viewer가 아니라 사용자가 신뢰된 Dataset을 재사용하는 소비계층입니다.

Semantic Layer와 공통 Metric Definition이 있어야 Self-BI가 데이터 혼란으로 이어지지 않습니다.

이 영역은 Target 상세설계가 필요합니다.


---

# 5. BI Security

## FIG-12-06. BI Security

```text
User
 ↓
Authentication
 ↓
BI Role
 ↓
Dataset Permission
 ↓
Row / Column Access
 ↓
Audit
```

BI는 데이터를 넓게 보여주기 때문에 Application 로그인만으로 충분하지 않습니다.

Dataset 단위, Row/Column 단위 권한과 Audit이 필요할 수 있습니다.

정확한 권한모델은 BI Platform 선택과 함께 확정해야 합니다.


---

# 6. Freshness SLA

## FIG-12-07. Freshness SLA

```text
Source Change
 ↓
CDC / ETL
 ↓
ADW Refresh
 ↓
Dataset
 ↓
Report Freshness
```

BI에서 '실시간'은 모호한 표현입니다.

Source Change부터 Report에 보이기까지의 전체 Freshness를 측정해야 합니다.

CDC 3초/30초와 같은 SLA 충돌도 최종 사용자 관점의 Freshness로 재정의해야 합니다.


---

# 7. Performance Isolation

## FIG-12-08. Performance Isolation

```text
Heavy BI Query
 ↓
ADW / BI Runtime

not

PDMG WAS / RDW Online
```

분석부하를 Online Runtime과 분리하는 이유는 Performance Isolation입니다.

BI Query가 느리더라도 고객/직원 Online Transaction SLA에 영향을 주지 않아야 합니다.

따라서 Compute/Data Resource도 별도 Scale Unit을 가져야 합니다.


---

# 8. BI Evidence

## FIG-12-09. BI Evidence

```text
Dataset Version
 ↓
Report / Query
 ↓
User / Role
 ↓
Refresh Time
 ↓
Audit / Performance Evidence
```

BI도 운영 가능하려면 Evidence가 필요합니다.

어떤 Dataset Version을 어떤 사용자가 조회했고 얼마나 걸렸는지 추적되어야 Governance와 Performance를 함께 볼 수 있습니다.

현재는 Target Reference 수준이므로 실제 구현 Evidence는 OPEN입니다.

---

# 정상패턴과 금지패턴

## FIG-12-10. Normal Pattern

```text
RDW→ADW→Data Contract→BI
```

정상패턴은 BI가 Data Platform의 신뢰된 Dataset을 소비하고 Online DAO/DB와 직접 결합하지 않는 것입니다.

## FIG-12-11. Forbidden Pattern

```text
BI→PDMG DAO / Heavy Query→Online
```

BI가 PDMG DAO/Mapper나 Online DB에 직접 결합하여 분석부하를 운영거래에 전파하는 구조를 금지합니다.

---

# Architecture Decision

## FIG-12-12. 주안과 대안

```text
[주안]
ADW/Data Contract 기반 BI 연결

        VS

[대안]
PDMG 내부 DAO/DB 직접 연결
```

BI는 ADW/Data Contract 기반으로 연결합니다. 운영 Application 내부에 분석 기능을 붙이는 것이 아니라 신뢰된 Dataset을 독립 소비하는 구조가 목표입니다.

PDMG 내부 DAO/DB 직접 연결은 구현은 빠르지만 Online SLA와 Data Ownership을 동시에 침범하므로 금지대안입니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---


# Evidence / Conformance — 이 장의 Architecture를 무엇으로 증명하는가

## Evidence Architecture

```text
BI 포탈 Architecture Rule
        ↓
Source Evidence
        ↓
Config Evidence
        ↓
Runtime / Deployment Evidence
        ↓
Conformance Test
        ↓
PASS / GAP / ADR
```

### Source Evidence

- `[SOURCE]` Data/Interface Architecture와 NSIGHT BI Target 자료
- `[SOURCE]` PDMG Current에는 BI 구현 직접 Evidence 제한적

### Config Evidence

- `[CONFIG]` Dataset/Freshness/Security Contract는 Target 상세설계 필요

### Runtime / Deployment Evidence

- `[RUNTIME]` RDW→ADW→Dataset→BI Portal
- `[RUNTIME]` Heavy BI Query는 Online Runtime과 분리

### Architecture Decision

- `[DECISION]` ADW/Data Contract 기반 BI
- `[DECISION]` PDMG DAO/DB Direct 접근 금지

### Related ADR

- `ADR-021 RDW/ADW 분리`
- `ADR-035 Observability`

### Current GAP / OPEN

- `[GAP/OPEN]` BI Current Evidence
- `[GAP/OPEN]` Data Contract Inventory
- `[GAP/OPEN]` ADW Freshness
- `[GAP/OPEN]` Dataset Ownership/Security

## 이 장의 판정

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

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `Dataset Contract/Ownership`
- `ADW Freshness SLA`
- `BI Security Model`
- `Target Runtime Evidence`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-12-16. 다음 장 Handoff

```text
BI 포탈
 ↓
데이터 플랫폼이 신뢰를 만들고 BI는 판단 속도를 높인다
 ↓
남은 질문
"좋은 Architecture는 처음 잘 그린 그림이 아니라 계속 정합되는 체계다"
 ↓
표준화와 10년 지속 가능성
```

이제 데이터의 생성·실행·판단까지 연결했습니다. 마지막 장에서는 이 Architecture가 시간이 지나도 무너지지 않도록 Naming, CI, Artifact, Deployment, Evidence, ADR를 하나의 Closed Loop로 묶습니다.

---

# NSIGHT PDMG 아키텍처 정의서
# 제13장. 표준화와 10년 지속 가능성
## Story: “좋은 Architecture는 처음 잘 그린 그림이 아니라 계속 정합되는 체계다”


---

# 0. Opening Script

마지막 장의 질문은 단순합니다. 지금 잘 만든 구조가 3년, 5년, 10년 뒤에도 같은 원칙을 유지할 수 있는가입니다.

Architecture는 시간이 지나면 자동으로 낡습니다. Source가 바뀌고 Config가 바뀌고 Deployment가 바뀌기 때문입니다.

그래서 최종 Architecture는 **Naming → CI Rule → Immutable Artifact → Deployment Trace → Runtime Evidence → Drift → ADR → New Baseline**이라는 Closed Loop로 끝나야 합니다.

## FIG-13-01. 장 전체 Architecture

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

마지막 그림은 Architecture가 유지되는 방식을 보여줍니다. Naming과 Rule이 Source/CI로 내려가고, 동일 Artifact가 Deployment된 뒤 Runtime Evidence로 검증됩니다. Drift가 생기면 GAP/ADR로 다시 Baseline을 갱신합니다. **지속가능성은 변하지 않는 Architecture가 아니라, 변화해도 다시 정합되는 Architecture**입니다.


---

# 1. Naming Backbone

## FIG-13-02. Naming Backbone

```text
Business
 ↓
Program
 ↓
ServiceId
 ↓
Package / Class
 ↓
Mapper / SqlId
 ↓
Artifact / Deployment
```

지속가능성의 시작은 식별입니다.

Business와 Source, Runtime이 서로 다른 이름체계를 쓰면 자동 Trace가 불가능합니다. ServiceId와 Program, Package/Mapper 축이 정합해야 합니다.

Naming은 규칙이 아니라 Trace Backbone입니다.


---

# 2. Machine-readable Rule

## FIG-13-03. Machine-readable Rule

```text
Architecture Standard
 ↓
Rule
 ↓
Scanner
 ↓
CI Gate
 ↓
PASS / FAIL
```

표준문서만으로는 10년을 유지할 수 없습니다.

규칙을 Source Scanner와 CI Gate로 옮겨야 새 코드가 Architecture를 어기는 순간 바로 알 수 있습니다.

예를 들어 Duplicate ServiceId, Handler→DAO 같은 금지 Dependency는 기계검증 대상입니다.


---

# 3. Build Once / Promote

## FIG-13-04. Build Once / Promote

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
```

환경마다 다시 Build하면 같은 Source라도 Binary가 달라질 수 있습니다.

Build Once 후 동일 Artifact를 승격하면 테스트한 Binary와 운영 Binary가 동일하다는 증명이 쉬워집니다.

ArtifactHash가 Evidence Chain의 핵심이 됩니다.


---

# 4. Config / Secret 분리

## FIG-13-05. Config / Secret 분리

```text
Artifact
= code/binary

Config
= environment

Secret / Key
= protected store
```

Artifact 안에 Environment Config와 Secret을 섞으면 Promotion과 Key Rotation이 어려워집니다.

특히 JWT Key는 Source나 일반 Config 파일과 분리해 Managed Store에서 다루는 방향이 필요합니다.

DR에서도 같은 Trust Chain을 복구할 수 있어야 합니다.


---

# 5. Deployment Trace

## FIG-13-06. Deployment Trace

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
```

Source와 Runtime을 연결하는 핵심 Chain입니다.

장애가 났을 때 로그만 보고 끝나는 것이 아니라 어떤 Commit/Artifact가 어느 Host에서 실행됐는지 알아야 합니다.

현재 이 Mapping 자동화가 주요 GAP입니다.


---

# 6. Observability → Evidence

## FIG-13-07. Observability → Evidence

```text
Metric
+ Log
+ Trace
 ↓
ServiceId / GUID
 ↓
Deployment
 ↓
Architecture Rule
 ↓
Evidence
```

Observability의 목적을 Dashboard에서 끝내지 않습니다.

Metric/Log/Trace를 Architecture Rule과 연결해야 Runtime Evidence가 됩니다.

예를 들어 Timeout Rule을 실제 p95와 Timeout/DB Wait Evidence로 검증할 수 있어야 합니다.


---

# 7. Drift / ADR

## FIG-13-08. Drift / ADR

```text
Baseline
 ↓ compare
Source / Config / Runtime
 ↓
Drift
 ↓
GAP
 ↓
ADR / Fix
 ↓
New Baseline
```

Drift는 실패가 아니라 변경의 신호입니다.

중요한 것은 Drift를 숨기지 않고 GAP로 기록하고, Architecture 변경이 필요하면 ADR로 Baseline을 갱신하는 것입니다.

이 과정이 없으면 문서와 실제가 다시 분리됩니다.


---

# 8. G00→HG90

## FIG-13-09. G00→HG90

```text
G00 Source
 ↓
G10 Document
 ↓
G20 Model
 ↓
G30 Conformance
 ↓
G40 Test
 ↓
G50 Runtime Evidence
 ↓
G60 Drift
 ↓
G70 GAP/ADR
 ↓
G80 Approval
 ↓
HG90
```

최종 Baseline은 단순 문서 승인으로 Release하지 않습니다.

Source와 Model, Test, Runtime Evidence, GAP/ADR가 모두 Gate를 통과해야 HG90로 승격합니다.

따라서 10년 지속가능성의 핵심은 '변하지 않는 구조'가 아니라 **변화해도 다시 정합되는 절차**입니다.

---

# 정상패턴과 금지패턴

## FIG-13-10. Normal Pattern

```text
Rule→CI→Artifact→Deploy→Evidence→Drift→ADR
```

정상패턴은 Architecture Rule을 Machine-readable하게 만들고 Source→Artifact→Deployment→Runtime Evidence까지 동일 Identity Chain으로 연결하는 것입니다.

## FIG-13-11. Forbidden Pattern

```text
문서만 표준 / 재빌드 / Evidence 없이 PASS
```

문서 Review만으로 표준 준수를 선언하거나 환경마다 Artifact를 다시 Build하고 Runtime Evidence 없이 HG90를 PASS시키는 것을 금지합니다.

---

# Architecture Decision

## FIG-13-12. 주안과 대안

```text
[주안]
CI + Runtime Evidence Gate

        VS

[대안]
문서 Review/수동점검
```

Machine-readable Rule, Immutable Artifact Promotion, Runtime Evidence Gate를 최종 운영모델로 채택합니다.

수동 문서 Review와 환경별 재빌드는 사람이 기억해야 하는 규칙을 늘리고 동일 Binary 검증을 어렵게 하므로 장기 운영모델로 채택하지 않습니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---


# Evidence / Conformance — 이 장의 Architecture를 무엇으로 증명하는가

## Evidence Architecture

```text
표준화와 10년 지속 가능성 Architecture Rule
        ↓
Source Evidence
        ↓
Config Evidence
        ↓
Runtime / Deployment Evidence
        ↓
Conformance Test
        ↓
PASS / GAP / ADR
```

### Source Evidence

- `[SOURCE]` Naming/ServiceId Standard
- `[SOURCE]` DevOps/OM/Observability
- `[SOURCE]` Traceability/PASS/GAP/ADR
- `[SOURCE]` Integrated Baseline

### Config Evidence

- `[CONFIG]` Java 21 / Spring Boot 3.5.14 / Gradle multi-project
- `[CONFIG]` External Config + Secret Store Target

### Runtime / Deployment Evidence

- `[RUNTIME]` sourceCommit→buildId→artifactHash→deploymentId→ServiceId/GUID→Runtime Evidence

### Architecture Decision

- `[DECISION]` Build Once / Immutable Promotion
- `[DECISION]` CI + Runtime Evidence Gate
- `[DECISION]` Architecture Closed Loop

### Related ADR

- `ADR-002 ServiceId SSOT`
- `ADR-033 CI/CD Orchestrator OPEN`
- `ADR-034 Immutable Artifact`
- `ADR-035 Observability`
- `ADR-037 Config/Secret`
- `ADR-040 Runtime Evidence Gate`

### Current GAP / OPEN

- `[GAP/OPEN]` Naming Scanner
- `[GAP/OPEN]` Artifact/Deployment Identity
- `[GAP/OPEN]` Runtime Evidence Collector
- `[GAP/OPEN]` OM Control Plane
- `[GAP/OPEN]` Critical ADR Closure

## 이 장의 판정

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

Architecture Definition PASS
        ≠
Current Implementation PASS
```

### PASS 전환조건

- `CI Scanner`
- `Immutable Artifact Identity`
- `Deployment Trace`
- `Runtime Evidence Collector`
- `ADR-033/critical ADR closure`

위 조건은 문서 완성도가 아니라 **Current Implementation Conformance를 PASS로 전환하기 위한 Exit Criteria**다. 해당 Evidence가 확보되기 전에는 `[GAP]`, `[OPEN]`, `[UNKNOWN]` 상태를 유지한다.

---
# Chapter Closing Script

## FIG-13-16. 다음 장 Handoff

```text
표준화와 10년 지속 가능성
 ↓
좋은 Architecture는 처음 잘 그린 그림이 아니라 계속 정합되는 체계다
 ↓
남은 질문
"모든 Critical GAP와 Evidence를 닫고 공식 Architecture Baseline으로 Release한다"
 ↓
HG90 Evidence-backed Baseline
```

13장의 결론은 명확합니다. 최종 산출물은 고정된 그림이 아니라 **변경을 감지하고 Evidence로 다시 정합되는 Architecture 운영체계**입니다. Critical GAP가 Source/Runtime/DR Evidence로 닫히고 G80 승인을 통과할 때 HG90 Architecture Baseline으로 Release합니다.

---

# RAW REFERENCE APPENDICES


---

# APPENDIX A. ServiceId Registry

## Current Handler Registry — 13 ServiceIds

```text
mgcoa5530S0

mgcoa8888S0
mgcoa8888D0

mgcoa9000S0
mgcoa9000C0
mgcoa9000U0
mgcoa9000D0

mgcoa9001S0
mgcoa9001C0
mgcoa9001U0
mgcoa9001D0

mgcoa9100S0
mgcoa9999S0
```

## Naming Rule

```text
Program   = 2 + 2 + 1 + 4 = 9 chars
ServiceId = 2 + 2 + 1 + 4 + 1 + 1 = 11 chars
```

Regex:

```text
^[a-z]{2}[a-z]{2}[a-z][0-9]{4}[SCUDAR][0-9A-Z]$
```

Registry와 `handle()` branch는 일치해야 하며 Duplicate는 startup fail 원칙이다.

---

# APPENDIX B. Application Code Registry

## NSIGHT Application Groups

```text
MP  Marketing Platform
RD  RDW
AD  ADW
BI  BI Portal
DG  Data Governance
IM  IT Service / Business Support
```

Application Code는 `Group + App`을 Canonical Key로 관리한다.

```text
RD-SR ≠ AD-SR
```

PDMG Source의 `mg`와 NSIGHT Target의 `MP`는 자동 치환하지 않으며 Mapping Registry / ADR가 필요하다.

---

# APPENDIX C. Package / Class Index

```text
pdmg-service
└─ nhnis.mg.co.a.*
   ├─ entry.handler
   ├─ application.controller
   ├─ application.facade
   ├─ application.service
   ├─ application.dto
   └─ persistence.dao

pdmg-fw
├─ nhnis.fw.*
└─ com.ims.superspring.*

pdmg-ui
└─ nhnis.mg.ui.*

pdmg-jwt
└─ nhnis.mg.jw.a.*

Mapper Resource
└─ rdw.mg.co.a/
```

---

# APPENDIX D. Mapper / SqlId / Table Index

최종 자동 Index는 다음 Chain으로 생성한다.

```text
ServiceId
 ↓
Handler / Controller
 ↓
Facade / Service
 ↓
DAO Method
 ↓
Mapper Namespace
 ↓
SqlId
 ↓
SQL
 ↓
Table / View
```

현재 실제 전수 Table/View 목록은 Source Scanner 산출물로 관리한다.

---

# APPENDIX E. Physical Inventory

필수 Registry Fields:

| Layer | 필수 항목 |
|---|---|
| Center | Center ID / Role |
| Host | Hostname / IP / HW |
| VM | VM ID / CPU / Memory |
| OS | OS / Version |
| WEB | Apache Instance / Port |
| WAS | Tomcat/JVM / Connector |
| Artifact | WAR / Version / Hash |
| Network | L4 VIP / Firewall / Route |
| DB | Datasource / Service / Node |
| Storage | Mount / Capacity / Backup |
| Deployment | deploymentId / Time / Owner |

현재 Host/JVM/WAR 전수 Mapping은 `[GAP]`.

---

# APPENDIX F. Interface Inventory

필수 Interface Registry:

| 항목 | 설명 |
|---|---|
| Interface ID | Canonical ID |
| Source / Target | 시스템 경계 |
| Business Purpose | 업무 목적 |
| Type | API / Event / CDC / ETL / File |
| Sync/Async | 처리 방식 |
| Contract | Schema / Version |
| Security | AuthN/AuthZ/Encryption |
| Timeout | Budget |
| Retry | Retryability / Backoff |
| Idempotency | 중복 방지 |
| Operations | SLA / Owner / Alert |
| Evidence | Test / Runtime |

Cross-system Direct DB DML / DB-Link는 기본 금지.

---

# APPENDIX G. Configuration Inventory

Known PDMG Current Snapshot:

```yaml
nhnis:
  fw:
    tcf:
      enabled: true
    timeout:
      enabled: true
      milliseconds: 5000
      pool-size: 20
      queue-capacity: 100
    commons:
      legacy-web:
        enabled: true
      filter:
        enabled: true
```

이 값은 AS-IS Snapshot이며 Target SLA가 아니다.

---

# APPENDIX H. ADR Register Summary

핵심 ADR:

```text
001 Architecture Baseline / SSOT
002 ServiceId SSOT
003 PDMG↔NSIGHT Mapping
004 Common Facade
005 TCF Policy
009 Immutable Worker Context
010 RS256 + JWKS
011 Managed JWT Key
013 Principal→Business User
014 Interface Selection
015 Direct DB Restriction
017 Timeout Budget
018 Retry / Idempotency
021 RDW / ADW Separation
022 CDC SLA
026 Capacity Candidate
027 L4→Apache→Tomcat
028 JVM Isolation
029 Capacity / Backpressure
030 Active-Active + N+1
031 DR Tier
032 DB HA/DR
033 CI/CD Orchestrator [OPEN]
034 Immutable Artifact
035 Metrics + Logs + Traces
036 OM Control Plane
037 Config / Secret Store
038 Restore Drill
039 Controlled Promotion
040 Runtime Evidence Gate
```

---

# APPENDIX I. GAP Register

Critical:

```text
SECURITY
- RS256 Issuer ↔ HMAC Verifier
- JWT Key Lifecycle
- Principal ↔ Business User

RUNTIME
- JDBC Query Timeout
- JDBC Cancel / Late Worker
- TCF OFF Drift

PHYSICAL
- Artifact → Deployment → JVM → Host

DATA / INTERFACE
- RDW / ADW Mapping
- Ownership / Lineage
- External Interface Inventory

OPERATIONS
- pdmg-om Scope
- Runtime Evidence Automation

HA / DR
- RTO / RPO
- Restore
- Failover / Failback Evidence
```

---

# APPENDIX J. Evidence Register

Evidence Priority:

```text
1 Source / Config
2 Runtime / Deployment Evidence
3 PDMG Current Architecture Analysis
4 Approved ADR / PASS Register
5 Official Architecture Documents
6 Presentation / Explanatory Documents
7 Historical Standards
8 General Knowledge
```

Evidence Chain:

```text
architectureBaselineId
 ↓
architectureModelVersion
 ↓
sourceCommit
 ↓
buildId
 ↓
artifactHash
 ↓
deploymentId
 ↓
serviceId
 ↓
traceId / GUID
 ↓
runtime evidence
```

---

# FINAL RELEASE GATE

```text
Architecture Definition FINAL V5
        ↓
G00 Source Baseline
        ↓
G10 Document Classification
        ↓
G20 Architecture Model
        ↓
G30 Model ↔ Source Conformance
        ↓
G40 Rule / Conformance Test
        ↓
G50 Runtime Evidence
        ↓
G60 Drift
        ↓
G70 GAP / ADR Closure
        ↓
G80 Approval
        ↓
HG90 Architecture Baseline Release
```

## Final Critical Closure

- **Security** — RS256 Issuer↔Verifier, Key Lifecycle, Principal↔Business User Binding
- **Runtime** — JDBC Query Timeout/Cancel, Late Worker, TCF OFF Business Core Drift
- **Physical** — Artifact→Deployment→WAR→JVM→Host Mapping
- **Data / Interface** — RDW/ADW Mapping, Ownership/Lineage, External Interface Inventory
- **Operations** — pdmg-om Current Scope, Runtime Evidence Automation
- **HA / DR** — RTO/RPO, Restore, Failover/Failback Business Validation

문서 편집은 FINAL V5에서 종료한다. 이후 변경은 단순 문장 보완이 아니라 **Evidence Closure 또는 ADR에 의한 Architecture Baseline 변경**으로 관리한다.
