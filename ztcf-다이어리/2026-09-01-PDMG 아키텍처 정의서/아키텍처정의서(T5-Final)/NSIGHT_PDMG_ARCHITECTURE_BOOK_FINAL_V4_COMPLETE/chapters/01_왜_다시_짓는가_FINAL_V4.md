# NSIGHT PDMG 아키텍처 정의서
# 제1장. 왜 다시 짓는가
## Story: “이미 시스템은 있지만, Architecture는 하나로 보이지 않는다”
## STORY-FIRST / TEXT-ARCHITECTURE-FIRST / TOP-DOWN → DRILL-DOWN / EVIDENCE-FIRST

> 최종 문서 Edition: `FINAL V4 — Story + Architecture + Drill-down + Evidence in Chapter`  
> 기준일: `2026-09-01`  
> PDMG = Current / Source / Config / Runtime  
> NSIGHT = Target / Alignment / Strategy Reference  
> 문서 상태: **FINAL V4 WORKING BASELINE** / Evidence는 본 장 내부에서 Architecture와 함께 판정

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

이제 이 전체 그림을 위에서 아래로 해부하겠습니다. 이번 버전에서는 그림의 박스와 화살표를 직접 설명하고, 반복적인 형식문장은 최소화합니다. 각 Drill-down은 상위 그림의 어느 부분을 확대하는지 명확하게 연결합니다.

## FIG-01-02. Drill-down Route

```text
L0 전체 Story
 ↓
L1 책임 / Boundary
 ↓
L2 Logical / Application / Platform
 ↓
L3 Component / Contract
 ↓
L4 Runtime / Failure / Security
 ↓
L5 Source / Config / Deployment / Evidence
```

---

# 1. Inventory와 Architecture의 차이

## FIG-01-03. Inventory와 Architecture의 차이

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

여기까지가 `Inventory와 Architecture의 차이`의 역할입니다. 이제 이 구조를 더 내려가 **Module을 Responsibility로 다시 읽기**에서 다음 경계와 실행책임을 보겠습니다.

---

# 2. Module을 Responsibility로 다시 읽기

## FIG-01-04. Module을 Responsibility로 다시 읽기

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

여기까지가 `Module을 Responsibility로 다시 읽기`의 역할입니다. 이제 이 구조를 더 내려가 **Source와 Runtime은 다른 그림이다**에서 다음 경계와 실행책임을 보겠습니다.

---

# 3. Source와 Runtime은 다른 그림이다

## FIG-01-05. Source와 Runtime은 다른 그림이다

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

여기까지가 `Source와 Runtime은 다른 그림이다`의 역할입니다. 이제 이 구조를 더 내려가 **Timeout과 Transaction의 경계**에서 다음 경계와 실행책임을 보겠습니다.

---

# 4. Timeout과 Transaction의 경계

## FIG-01-06. Timeout과 Transaction의 경계

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

여기까지가 `Timeout과 Transaction의 경계`의 역할입니다. 이제 이 구조를 더 내려가 **Security GAP는 구조 문제다**에서 다음 경계와 실행책임을 보겠습니다.

---

# 5. Security GAP는 구조 문제다

## FIG-01-07. Security GAP는 구조 문제다

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

여기까지가 `Security GAP는 구조 문제다`의 역할입니다. 이제 이 구조를 더 내려가 **ServiceId를 Architecture Backbone으로 본다**에서 다음 경계와 실행책임을 보겠습니다.

---

# 6. ServiceId를 Architecture Backbone으로 본다

## FIG-01-08. ServiceId를 Architecture Backbone으로 본다

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

여기까지가 `ServiceId를 Architecture Backbone으로 본다`의 역할입니다. 이제 이 구조를 더 내려가 **Logical에서 Physical까지 연결되어야 한다**에서 다음 경계와 실행책임을 보겠습니다.

---

# 7. Logical에서 Physical까지 연결되어야 한다

## FIG-01-09. Logical에서 Physical까지 연결되어야 한다

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

여기까지가 `Logical에서 Physical까지 연결되어야 한다`의 역할입니다. 이제 이 구조를 더 내려가 **Architecture Closed Loop**에서 다음 경계와 실행책임을 보겠습니다.

---

# 8. Architecture Closed Loop

## FIG-01-10. Architecture Closed Loop

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

## FIG-01-11. Normal Pattern

```text
Current Fact → Responsibility → Runtime → Evidence
```

정상패턴은 각 영역이 자신의 책임을 유지하면서 명확한 Contract와 Runtime Boundary를 통해 연결되는 구조입니다. 변경·장애·보안·운영 책임이 이 경계를 따라 추적될 수 있어야 합니다.

## FIG-01-12. Forbidden Pattern

```text
문서=Fact / Target=Current / Log=Evidence
```

금지패턴은 기술적으로 불가능해서가 아니라 Architecture의 책임과 Evidence Chain을 무너뜨리기 때문에 제한합니다. 예외가 필요하면 묵시적으로 허용하지 않고 ADR와 Test Evidence로 승인합니다.

---

# Architecture Decision

## FIG-01-13. 주안과 대안

```text
[주안]
Evidence-backed Architecture Baseline

        VS

[대안]
Document-centered Baseline
```

이번 장의 주안은 **Evidence-backed Architecture Baseline**입니다. 이 방향은 현재 확인된 PDMG 구조와 NSIGHT Target을 연결하면서 책임·운영·Evidence를 가장 일관되게 유지할 수 있는 선택입니다.

대안인 **Document-centered Baseline**도 특정 조건에서는 사용할 수 있습니다. 다만 대안을 선택하려면 주안보다 나은 성능·가용성·비용 또는 운영효과가 PoC/Runtime Test로 확인되어야 하고, 그 결과를 ADR로 남겨야 합니다.

| 평가축 | 주안 | 대안 |
|---|---|---|
| 책임/경계 | 명확 | 추가 보완 필요 |
| Current PDMG 정합 | 높음 | 변경범위 가능 |
| 운영/장애분석 | Trace 용이 | 복잡도 증가 가능 |
| 승인조건 | 기본 Rule/Test | 별도 ADR + Evidence |

---

# Current GAP / PASS

## FIG-01-14. Current GAP

```text
Current
│
├─ JWT issuer/verifier
├─ Identity binding
├─ Artifact→Host/JVM/WAR
├─ Runtime Evidence automation
└─ pdmg-om scope
```

- `[GAP/OPEN]` JWT issuer/verifier
- `[GAP/OPEN]` Identity binding
- `[GAP/OPEN]` Artifact→Host/JVM/WAR
- `[GAP/OPEN]` Runtime Evidence automation
- `[GAP/OPEN]` pdmg-om scope

## FIG-01-15. Architecture Assessment

```text
Architecture Definition
 ↓
CONDITIONAL PASS

Current PDMG Conformance
 ↓
PARTIAL / GAP

Runtime Evidence
 ↓
MEDIUM

Architecture PASS
 ≠
Implementation PASS
```

Architecture가 잘 정의되었다는 것과 현재 구현이 그 정의를 지킨다는 것은 별도 판단입니다. 이 문서는 둘을 분리해 평가하며, `[OPEN]`과 `[UNKNOWN]`을 임의로 채우지 않습니다.

---

# Evidence Card

## FIG-01-16. Evidence Chain

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

본문에서는 Story와 Architecture 설명을 우선하고, Evidence는 이 카드에서 정리합니다. 앞으로 자동화 단계에서는 이 Chain을 Manifest/Registry로 기계적으로 생성하는 것이 목표입니다.

---


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

이 장의 정의가 완료되었다고 해서 현재 구현이 자동으로 PASS가 되는 것은 아니다. Source·Config·Runtime Evidence가 실제 Architecture Rule과 정합할 때 Current Conformance를 PASS로 승격한다.

---
# Chapter Closing Script

## FIG-01-17. 다음 장 Handoff

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

여기까지가 **왜 다시 짓는가**입니다. 이 장에서 중요한 것은 개별 기술을 많이 보여준 것이 아니라, 전체 그림을 시작점으로 책임과 Runtime을 하나씩 내려가며 설명했다는 점입니다.

이제 자연스럽게 다음 질문이 생깁니다. **저장소와 Application 중심에서 살아 움직이는 Platform Architecture로**. 그 질문이 다음 단계인 **정보계 패러다임의 전환**의 출발점입니다.
