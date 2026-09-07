# NSIGHT / PDMG 아키텍처 정의서 — X. Naming / ServiceId / Traceability / Architecture Closed Loop

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 대상: PDMG Naming / ServiceId / Source-Data-Runtime Traceability + NSIGHT Architecture Closed Loop  
> 문서 상태: **Draft / Evidence-First / Final Integration Chapter**  
> 작성일: 2026-08-31  
> 선행 장: `NSIGHT_PDMG_아키텍처_정의서_IX_DevOps_OM_Observability.md`

---

# 0. Evidence Register

| ID | 근거 자료 | 본 장 사용 목적 | 상태 |
|---|---|---|---|
| EV-X-01 | `04장. 애플리케이션 분류와 Service ID.md` | ServiceId 11자 구성, MG/CO/A/Program/거래코드, Handler Registry 13개, UI Catalog Drift | `[PDMG AS-IS EVIDENCE]` |
| EV-X-02 | `NSIGHT_PDMG_아키텍처_정의서_III_PDMG_Module_Application_Architecture.md` | `nhnis.mg.co.a`, `rdw.mg.co.a`, Handler→Facade→Service→DAO/Mapper | `[CURRENT BASELINE DRAFT]` |
| EV-X-03 | `NSIGHT_PDMG_아키텍처_정의서_IV_PDMG_Online_Runtime_TCF_Flow.md` | ServiceId→Dispatcher→Handler Runtime / TCF ON-OFF | `[CURRENT BASELINE DRAFT]` |
| EV-X-04 | `NSIGHT_PDMG_아키텍처_정의서_VI_Standard_Message_Context_Error_Logging.md` | Header `rms_svc_c`, GUID, Response/Error/Log Trace | `[CURRENT BASELINE DRAFT]` |
| EV-X-05 | `NSIGHT_PDMG_아키텍처_정의서_VIII_Infrastructure_WAS_Capacity_HA_DR.md` | ServiceId→WAR→JVM→Host Trace Target | `[CURRENT BASELINE DRAFT]` |
| EV-X-06 | `NSIGHT_PDMG_아키텍처_정의서_IX_DevOps_OM_Observability.md` | Source Commit/Artifact/Config/Runtime Evidence/Drift Handoff | `[CURRENT BASELINE DRAFT]` |
| EV-X-07 | `NSIGHT / PDMG 아키텍처 인포그래픽 이미지화 마스터 프롬프트` | X장 필수 12 Figure, Naming→Closed Loop 구조 | `[WORKING BASELINE]` |
| EV-X-08 | `NSIGHT Architecture Closed Loop 실행 마스터 프롬프트` | G00~G70, 20-MODEL, 30-CODE, 40-TEST, 50-RUNTIME, Drift, GAP/ADR, HG90 | `[NSIGHT CLOSED-LOOP BASELINE]` |
| EV-X-09 | `2026-08-17-NSIGHT 전체 아키텍처 통합분석 정의 마스터 프롬프트` | ServiceId 중심 정/역방향 Traceability 및 Architecture Model | `[WORKING GOVERNANCE BASELINE]` |
| EV-X-10 | `ONTOLOGY-ANALYSIS.md` | System/Business/Function/Program/ServiceId/Component/Mapper/SqlId/Table Entity와 Relation | `[MODEL ANALYSIS EVIDENCE]` |
| EV-X-11 | `NSIGHT_PDMG_아키텍처_정의서_00_목차_및_작성기준.md` | I~X 전체 Closed Loop 최종 목표 | `[CURRENT BASELINE DRAFT]` |

---

# 1. Evidence Rule

X장은 “예쁜 Naming 표준”을 만드는 장이 아니다.

최우선 질문은 다음이다.

```text
이 Requirement가
어느 화면을 만들었고
어느 ServiceId를 호출하고
어느 Handler / Facade / Service / DAO / SQL을 실행하며
어느 Table / External Interface를 사용하고
어느 WAR / JVM / Host에 배포되고
실제 Runtime에서 어떤 GUID / Error / Metric으로 증명되는가?
```

이 질문에 양방향으로 답할 수 있어야 한다.

## 1.1 사실 우선순위

```text
Current Source
    >
Current Config
    >
Runtime Evidence
    >
Approved Architecture Baseline
    >
Approved ADR
    >
Detailed Design
    >
Guide / Wiki
    >
Past Document
```

Source가 목표와 다르면:

```text
Source = AS-IS

Architecture = TO-BE

Difference = GAP
```

로 처리한다.

---

# 2. Figure Plan

| FIG | 제목 | Level | 목적 | 필수 |
|---|---|---:|---|---|
| FIG-X-01 | NSIGHT Business Taxonomy Tree | L0~L2 | 업무분류→Program→ServiceId | Y |
| FIG-X-02 | ServiceId 11-Character Anatomy | L2 | 2+2+1+4+1+1 구조 | Y |
| FIG-X-03 | Program ID vs ServiceId | L2 | Program과 Transaction 구분 | Y |
| FIG-X-04 | Naming Projection Map | L2~L3 | Package/Component/Mapper 일관성 | Y |
| FIG-X-05 | Current Handler Registry — 13 Services | L2~L3 | 현재 라우팅 카탈로그 | Y |
| FIG-X-06 | ServiceId → Handler → Facade → Service → DAO | L2~L3 | 구현 정방향 | Y |
| FIG-X-07 | DAO → Mapper Namespace → SqlId → SQL → Table | L2~L4 | Data Trace | Y |
| FIG-X-08 | Screen / UI Catalog → ServiceId Drift | L2~L4 | UI↔Backend Drift | Y |
| FIG-X-09 | Forward Traceability | L1~L5 | Requirement→Runtime | Y |
| FIG-X-10 | Reverse Traceability | L1~L5 | Table/Error→Requirement | Y |
| FIG-X-11 | Architecture Model Entity / Relation | L2~L5 | 기계판독 모델 | Y |
| FIG-X-12 | Source Map / Index Architecture | L3~L5 | service-source-map / mapper-sql-index | Y |
| FIG-X-13 | Naming / Layer Conformance Rules | L3~L5 | 자동검증 | Y |
| FIG-X-14 | Source → Config → Test → Runtime Evidence | L3~L5 | Evidence Chain | Y |
| FIG-X-15 | Document → Model → Code → Test → Runtime Loop | L0~L5 | 전체 Closed Loop | Y |
| FIG-X-16 | GAP → ADR → Rule → Test | L4~L5 | 결정의 실행화 | Y |
| FIG-X-17 | Architecture Gate G00→HG90 Roadmap | L4~L5 | Gate 전체 | Y |
| FIG-X-18 | Drift Detection Matrix | L4~L5 | 문서/모델/코드/런타임 차이 | Y |
| FIG-X-19 | Impact Analysis Graph | L3~L5 | 변경영향 양방향 추적 | Y |
| FIG-X-20 | Evidence Manifest Chain | L4~L5 | Baseline→Runtime 증적 | Y |
| FIG-X-21 | Baseline Release / Change Management | L4~L5 | 승인·버전·이력 | Y |
| FIG-X-22 | I~X Architecture Integration Map | L0~L5 | 정의서 전체 통합 | Y |
| FIG-X-23 | Current GAP / Priority Map | L5 | 닫히지 않은 핵심영역 | Y |
| FIG-X-24 | Architecture Closed Loop Completion Gate | L5 | 최종 완료조건 | Y |

---

# 3. 핵심 결론

X장의 핵심 결론은 다음과 같다.

1. **NSIGHT/PDMG 추적성의 최우선 공통키는 ServiceId다.**
2. PDMG ServiceId는 현재 11자로 구성되며 `대구분(2)+업무구분(2)+세부업무(1)+프로그램번호(4)+거래구분(1)+거래순번(1)`이다.
3. 예를 들어 `mgcoa9001S0`는 `mg / co / a / 9001 / S / 0`으로 해석한다.
4. `MG/CO/A` 업무분류는 Java Package `nhnis.mg.co.a`, Mapper Root `rdw.mg.co.a`, Program/ServiceId `mgcoa...`에 동일 축으로 투영된다.
5. 현재 PDMG Handler Source에서 확인되는 등록 ServiceId는 **13개**다.
6. ServiceId는 TCF ON에서 Handler Registry의 런타임 라우팅 키이고, TCF OFF에서는 업무 Controller의 URL Mapping 문자열로 사용되는 경로가 존재한다.
7. Handler의 `serviceIds()` 등록과 `handle()` 내부 분기는 **둘 다** 같은 ServiceId를 처리해야 한다. 한쪽만 추가하면 Runtime Drift가 발생한다.
8. 현재 UI `TransactionCatalog`는 5530/8888/9999/9000 계열 8개를 포함하지만 Backend Registry에는 9001/9100을 포함한 13개 거래가 있어 **UI Catalog와 Backend Registry가 SSOT가 아니다.**
9. 현재 `TransactionDispatcher`는 ServiceId 형식 자체를 공통 검증하지 않으며 Handler가 같은 문자열을 등록하면 표준형식이 아니어도 Runtime Key가 될 수 있다. **실행 가능과 표준 적합은 다르다.**
10. ServiceId 형식 검증은 정규식만으로 충분하지 않다. `문법 → 승인 분류표 → Program ID 유일성 → ServiceId 유일성 → Handler 등록 → Handler 분기`의 다층 검증이 필요하다.
11. Handler/Facade/Service/DAO의 이름 stem이 같다는 관례는 추적에 유용하지만 모든 ServiceId가 1:1 클래스 stem을 반드시 가진다고 Source 없이 강제하면 안 된다.
12. DAO와 Mapper XML/SQL ID의 관계는 Source Scanner로 실제 Interface Method와 Namespace/SqlId를 추출해야 한다.
13. SQL이 접근하는 Table/View는 Mapper XML SQL을 Parse하여 Evidence로 연결해야 하며, 추정 테이블명을 문서에 채우지 않는다.
14. 화면→ServiceId 연결은 UI Source/TransactionCatalog/Static Page/호출코드에서 추출해야 하고, UI Catalog만 SSOT로 사용하면 현재 9001/9100 Drift를 놓친다.
15. Traceability는 정방향뿐 아니라 역방향을 지원해야 한다. 장애 SQL/Table에서 어떤 ServiceId·화면·Requirement가 영향을 받는지 역추적 가능해야 한다.
16. Architecture Model은 `System/Business/Function/Program/ServiceId/Component/Mapper/SqlId/Table/RuntimePolicy/Deployment/Evidence`를 기계판독 Entity로 만든다.
17. Design Graph와 Runtime Graph를 분리한다. 정적 `CALLS`와 Runtime `DISPATCHES_TO/RUNS_ON_THREAD/STARTS_TRANSACTION`을 한 관계로 섞지 않는다.
18. Architecture Rule은 문서 문장이 아니라 Scanner/ArchUnit/Config Linter/Contract Test 등 실행 가능한 검증으로 변환해야 한다.
19. Runtime Evidence에는 최소 `architectureBaselineId → modelVersion → sourceCommit → build → artifactHash → deploymentId → serviceId → traceId/GUID → evidence` Chain이 있어야 한다.
20. Drift는 `Document↔Model`, `Model↔Code`, `Model↔Config`, `Code↔Runtime`, `Config↔Runtime`, `Test↔Runtime`을 각각 분리한다.
21. GAP는 자동으로 TO-BE가 되지 않는다. 중요한 GAP은 ADR 승인 후 Model/Rule/Test/Source에 반영해야 한다.
22. Closed Loop Gate는 Source Baseline(G00), Document(G10), Model(G20), Code(G30), Test(G40), Runtime Evidence(G50), Drift(G60), GAP/ADR(G70), Model/Baseline Update, 최종 HG90 순으로 닫힌다.
23. 최종 HG90은 Runtime Evidence, Artifact Hash, Deployment ID, Critical Drift/GAP 해소, Human Approval이 없으면 PASS할 수 없다.
24. **좋은 Architecture의 완료상태는 “문서가 있다”가 아니라 “문서→모델→코드→테스트→Runtime→Drift→ADR→새 Baseline이 반복 가능하다”는 것이다.**

---

# 4. 목적 / 범위 / 전제

## 4.1 목적

본 장은 다음 질문에 답한다.

1. NSIGHT 업무분류는 PDMG Naming에 어떻게 투영되는가?
2. ServiceId 11자는 정확히 어떻게 해석하는가?
3. Program ID와 ServiceId는 무엇이 다른가?
4. ServiceId는 Package/Handler/Facade/Service/DAO와 어떻게 연결되는가?
5. DAO Method는 어떤 Mapper XML/SqlId와 연결되는가?
6. SQL은 어떤 Table/View를 사용하는지 어떻게 검증하는가?
7. UI/Screen/Event는 어떤 ServiceId를 호출하는가?
8. UI Catalog와 Backend Registry의 Drift를 어떻게 탐지하는가?
9. Requirement→Runtime의 정방향 추적을 어떻게 만드는가?
10. SQL/Table/Error→Requirement의 역방향 추적을 어떻게 만드는가?
11. Architecture Model에는 어떤 Entity/Relation이 필요한가?
12. Source Scanner는 무엇을 Index해야 하는가?
13. Architecture Rule을 어떻게 자동 Test로 바꾸는가?
14. Runtime Evidence는 어떤 ID Chain을 가져야 하는가?
15. Document/Model/Source/Config/Runtime Drift를 어떻게 관리하는가?
16. GAP/ADR/Rule/Test가 어떻게 하나의 Decision Lifecycle이 되는가?
17. G00~HG90 Architecture Gate는 무엇을 검증하는가?
18. I~IX장의 모든 결과를 어떻게 하나의 Baseline으로 묶는가?

## 4.2 포함

```text
Business Code
MG / CO / A
Program ID
ServiceId
Transaction Type
Sequence
Package
Handler
Facade
Service
DAO
Mapper
SqlId
SQL
Table / View
Screen
UI Catalog
Runtime Registry
Requirement
ADR
Architecture Rule
Model
Source Scanner
Config
Test
Build
Artifact
Deployment
GUID / TraceId
Runtime Evidence
Drift
GAP
Gate
Baseline
```

## 4.3 제외

```text
새로운 업무코드 실제 발급 승인
전체 DB Table 목록 수동작성
전체 Source Scanner 구현코드
실제 Gate Evaluator 제품선정
실제 GitLab/eCAMS Pipeline 구현
```

이 장은 **구조와 실행계약**을 정의한다.

---

# 5. FIG-X-01 — NSIGHT Business Taxonomy Tree

PDMG Current Source의 대표 업무축:

```text
NSIGHT / PDMG
     │
     ▼
Application Group
     │
     └─ MG
         │
         ▼
Business
     │
     └─ CO
         │
         ▼
Sub Business / Function
     │
     └─ A
         │
         ▼
Program Number
     │
     ├─ 5530
     ├─ 8888
     ├─ 9000
     ├─ 9001
     ├─ 9100
     └─ 9999
         │
         ▼
Transaction Type + Sequence
     │
     ├─ S0
     ├─ C0
     ├─ U0
     └─ D0
         │
         ▼
ServiceId
```

## 5.1 현재 Source의 대표 Program

| Program | Current Purpose | Current Service Example |
|---|---|---|
| `mgcoa5530` | 마케팅희망고객 조회 | `mgcoa5530S0` |
| `mgcoa8888` | 이미지로그 조회/삭제 | `mgcoa8888S0`, `mgcoa8888D0` |
| `mgcoa9000` | 거래 파라미터 관리 | `S0/C0/U0/D0` |
| `mgcoa9001` | 거래통제 관리 | `S0/C0/U0/D0` |
| `mgcoa9100` | 런타임 진단 | `S0` |
| `mgcoa9999` | 영업팁 실적 조회 | `S0` |

## 5.2 주의

현재 샘플이 모두 `MG/CO/A`라고 해서:

```text
모든 신규 PDMG 업무
=
MG / CO / A
```

라고 규정하지 않는다.

신규 업무는 승인된 Application Code Classification에서 소유 업무축을 선택해야 한다.

---

# 6. FIG-X-02 — ServiceId 11-Character Anatomy

현재 표준 구성:

```text
ServiceId
= 대구분(2)
+ 업무구분(2)
+ 세부업무(1)
+ 프로그램번호(4)
+ 거래구분(1)
+ 거래순번(1)

= 총 11자
```

대표:

```text
m g c o a 9 0 0 1 S 0
│ │ │ │ │ └───────┘ │ │
│ │ │ │ │  Program  │ │
│ │ │ │ │    9001   │ │
│ │ │ │ │           │ └─ Sequence = 0
│ │ │ │ │           └─── Transaction = S
│ │ │ │ └─────────────── Sub Business = A
│ │ └─┴───────────────── Business = CO
└─┴───────────────────── Group = MG
```

좀 더 단순히:

```text
mg | co | a | 9001 | S | 0
```

## 6.1 길이

| 구분 | 길이 | 예 |
|---|---:|---|
| Application Group | 2 | `mg` |
| Business | 2 | `co` |
| Sub Business | 1 | `a` |
| Program Number | 4 | `9001` |
| Transaction Type | 1 | `S` |
| Sequence | 1 | `0` |

---

# 7. 거래구분 코드

현재 PDMG Naming 자료에서 정의되는 코드:

| Code | Meaning | Current Handler Example |
|---|---|---|
| `S` | Search / Select | 있음 |
| `C` | Create | 있음 |
| `U` | Update | 있음 |
| `D` | Delete | 있음 |
| `A` | Action | 현재 Handler 예 없음 |
| `R` | Report | 현재 Handler 예 없음 |

## 7.1 중요한 규칙

거래코드는 HTTP Method가 아니라 업무 의미다.

```text
HTTP POST
≠
C(Create)
```

PDMG 온라인 거래는 POST를 사용하더라도 조회는 `S`일 수 있다.

## 7.2 Sequence

```text
S0
S1
...
S9
SA
...
```

처럼 확장 가능하다는 표준 설명이 있으나 실제 사용은 업무계약과 분류관리로 통제한다.

Sequence는 API Version 번호로 사용하지 않는다.

---

# 8. ServiceId 표준 정규식

문서상 일반 검증식:

```regex
^[a-z]{2}[a-z]{2}[a-z][0-9]{4}[SCUDAR][0-9A-Z]$
```

MG 그룹만 좁히면:

```regex
^mg[a-z]{2}[a-z][0-9]{4}[SCUDAR][0-9A-Z]$
```

## 8.1 정규식만으로 부족

```text
Regex PASS
   ↓
Approved Business Classification?
   ↓
Program Number Unique?
   ↓
ServiceId Unique?
   ↓
Handler Registered?
   ↓
Handler Branch Exists?
```

예:

```text
mgzza1234S0
```

은 형식상 가능할 수 있지만 `zz/a`가 승인분류가 아니면 유효한 ServiceId가 아니다.

---

# 9. FIG-X-03 — Program ID vs ServiceId

```text
Program ID
mgcoa9001
     │
     ├─ S0 → mgcoa9001S0
     ├─ C0 → mgcoa9001C0
     ├─ U0 → mgcoa9001U0
     └─ D0 → mgcoa9001D0
```

## 9.1 책임 차이

```text
Program ID
= 하나의 프로그램 / 업무기능 영역

ServiceId
= 그 프로그램 안의 실제 온라인 거래
```

## 9.2 변경 비용

ServiceId는 다음과 연결될 수 있다.

```text
URL
Header rms_svc_c
UI
Handler Registry
Transaction Control
Timeout
Log
Metric
ImageLog
Test
OM Catalog
```

따라서 운영 후 ID 변경은 단순 Rename이 아니다.

---

# 10. FIG-X-04 — Naming Projection Map

`MG / CO / A` 축은 현재 Source에서 다음처럼 투영된다.

```text
                    MG / CO / A
                        │
          ┌─────────────┼───────────────┐
          │             │               │
          ▼             ▼               ▼
      Java Root      Mapper Root       ID Prefix

  nhnis.mg.co.a     rdw.mg.co.a       mgcoa
          │             │               │
          │             │               ▼
          │             │          mgcoa9001S0
          │             │
          ▼             ▼
   Component Stem    XML Directory

mgcoa9001Handler    rdw/mg/co/a/
mgcoa9001Facade          │
mgcoa9001Service         ▼
mgcoa9001DAO       mgcoa9001-ORA.xml
```

## 10.1 Traceability 가치

로그에서:

```text
mgcoa9001U0
```

를 보면:

```text
nhnis.mg.co.a
mgcoa9001*
rdw.mg.co.a
```

를 우선 검색할 수 있다.

---

# 11. Naming Consistency Matrix

| Axis | Current Example | Expected Relationship |
|---|---|---|
| Business | `MG/CO/A` | 승인 분류 |
| Java | `nhnis.mg.co.a` | Business 축 동일 |
| Mapper | `rdw.mg.co.a` | Business 축 동일 |
| Program | `mgcoa9001` | `mgcoa + number` |
| ServiceId | `mgcoa9001S0` | Program + Tx |
| Handler | `mgcoa9001Handler` | Program Stem |
| Facade | `mgcoa9001Facade` | Program Stem |
| Service | `mgcoa9001Service` | Program Stem |
| DAO | `mgcoa9001DAO` | Program Stem 후보 |
| Mapper XML | `mgcoa9001-ORA.xml` | Program Stem 후보 |

모든 서비스가 동일 Stem 1:1인지 전수 Source Scan으로 확인한다.

---

# 12. Naming 정상 / 금지

## 정상

```text
ServiceId
mgcoa9001S0

Package
nhnis.mg.co.a

Handler
mgcoa9001Handler

Mapper Root
rdw.mg.co.a
```

## 금지

```text
ServiceId
mgcoa9001S0

Package
nhnis.mg.ic.a

Mapper
rdw.mg.co.b

→ 업무분류 Trace Drift
```

---

# 13. FIG-X-05 — Current Handler Registry: 13 Services

현재 Handler Source에서 확인되는 등록 거래:

```text
TransactionHandler Beans
│
├─ mgcoa5530Handler
│    └─ mgcoa5530S0
│
├─ mgcoa8888Handler
│    ├─ mgcoa8888S0
│    └─ mgcoa8888D0
│
├─ mgcoa9000Handler
│    ├─ mgcoa9000S0
│    ├─ mgcoa9000C0
│    ├─ mgcoa9000U0
│    └─ mgcoa9000D0
│
├─ mgcoa9001Handler
│    ├─ mgcoa9001S0
│    ├─ mgcoa9001C0
│    ├─ mgcoa9001U0
│    └─ mgcoa9001D0
│
├─ mgcoa9100Handler
│    └─ mgcoa9100S0
│
└─ mgcoa9999Handler
     └─ mgcoa9999S0

TOTAL = 13
```

## 13.1 과거 문서 Drift

과거 일부 자료에는 등록 거래를 8개로 설명한 시점이 있다.

현재 Handler Source 분석은 13개를 우선한다.

```text
Past Document = 8
Current Handler Source = 13
→ DRIFT / SUPERSEDED candidate
```

---

# 14. Handler Registry의 두 단계 일관성

신규 ServiceId가 실행되려면:

```text
1. handler.serviceIds()
       │
       ▼
   Registry 등록

AND

2. handler.handle()
       │
       ▼
   해당 ID 분기
```

가 모두 필요하다.

## 14.1 실패 A

```text
serviceIds() 등록 O
handle() branch X
       ↓
Dispatcher lookup 성공
       ↓
Handler 내부 실패
```

## 14.2 실패 B

```text
serviceIds() 등록 X
handle() branch O
       ↓
Registry lookup 실패
       ↓
분기 도달 불가
```

---

# 15. ServiceId 중복

현재 분석은 두 Handler가 동일 ServiceId를 등록하면 Application Startup에서 중복 예외를 발생시키는 구조를 설명한다.

```text
Handler A
   └─ mgcoa9001S0

Handler B
   └─ mgcoa9001S0
          │
          ▼
Duplicate Registration
          │
          ▼
Startup Failure
```

따라서 `R-SERVICEID-UNIQUE`는 Source/Startup Contract와 정합적인 Critical Rule이다.

---

# 16. Runtime 문자열은 자동 정규화되지 않는다

Registry는:

```text
mgcoa9001S0
```

과:

```text
MGCOA9001S0
mgcoa9001s0
"mgcoa9001S0 "
```

을 자동 같은 값으로 보정한다고 가정하지 않는다.

Naming Rule은 Client/Header/Path/Registry 모두에 동일한 Canonical Value를 강제해야 한다.

---

# 17. FIG-X-06 — ServiceId → Handler → Business Components

대표 흐름:

```text
ServiceId
mgcoa9000C0
     │
     ▼
TransactionDispatcher
     │
     ▼
mgcoa9000Handler
     │
     ▼
mgcoa9000Facade
     │
     ▼
mgcoa9000Service
     │
     ▼
mgcoa9000DAO
     │
     ▼
Mapper XML
     │
     ▼
SQL
```

## 17.1 책임 관계

```text
ServiceId
HANDLED_BY
Handler

Handler
CALLS
Facade

Facade
CALLS
Service

Service
USES
DAO
```

---

# 18. Component Rule

## Handler

```text
ServiceId 수신
거래 분기
Facade 위임
```

금지:

```text
Handler → DAO
Handler → Mapper
Handler → SQL
```

## Facade

```text
Use Case Boundary
Service 조합
Transaction 참여
```

## Service

```text
Business Procedure / Decision
DAO 호출
```

## DAO

```text
Persistence Adapter
Mapper/SQL 연결
```

---

# 19. Rule Layer

현재 PDMG 일반 AS-IS에는 독립 `application.rule` 계층이 확인되지 않는다.

따라서 Model:

```text
Service
  ↓
Rule
```

Relation은:

```text
[OPTIONAL / TO-BE]
```

로 관리한다.

현재 없는 Rule을 고아 Entity처럼 자동 생성하지 않는다.

---

# 20. FIG-X-07 — DAO → Mapper → SQL → Table

Target Trace:

```text
Service
   │
   ▼
DAO Interface
   │
   │ method
   ▼
Mapper Namespace
   │
   ▼
SqlId
   │
   ▼
SQL Text / Hash
   │
   ├─ SELECT
   ├─ INSERT
   ├─ UPDATE
   └─ DELETE
   │
   ▼
Table / View
```

## 20.1 Source Scanner가 해야 하는 일

```text
Java DAO Interface
   ↓ parse
FQCN / Method

Mapper XML
   ↓ parse
namespace
select/insert/update/delete id

SQL
   ↓ parse
FROM
JOIN
INSERT INTO
UPDATE
DELETE FROM
```

## 20.2 추정 금지

현재 Evidence에 Table명이 없으면:

```text
Table = [UNKNOWN]
```

으로 둔다.

클래스명에서 테이블명을 추정하지 않는다.

---

# 21. Mapper Identity

유일키 후보:

```text
Mapper Namespace
+
SqlId
```

Architecture Rule:

```text
R-MAPPER-SQLID-UNIQUE
```

## 21.1 DAO ↔ Mapper 검증

```text
DAO Method 존재
→ Mapper SqlId 존재?

Mapper SqlId 존재
→ 호출 DAO/Service 존재?

고아 SQL?
고아 DAO Method?
```

를 검사한다.

---

# 22. SQL → Table 관계

Relation:

```text
SqlId
  ── ACCESSES ──► Table / View
```

쓰기 SQL은 추가 속성이 필요하다.

```text
accessType:
SELECT | INSERT | UPDATE | DELETE
```

이 Relation은 변경영향 분석에 중요하다.

---

# 23. FIG-X-08 — Screen / UI Catalog → ServiceId Drift

현재 UI `TransactionCatalog`:

```text
UI TransactionCatalog
│
├─ 5530 계열
├─ 8888 계열
├─ 9999 계열
└─ 9000 계열

등록수 = 8
```

Backend:

```text
Handler Registry
│
├─ 5530
├─ 8888
├─ 9000
├─ 9001
├─ 9100
└─ 9999

등록 ServiceId = 13
```

현재 차이:

```text
UI Catalog
   X
9001 / 9100 전체 반영 안 됨

BUT
Static UI / 호출코드가 별도 존재할 수 있음
```

## 23.1 결론

```text
TransactionCatalog
≠
ServiceId SSOT
```

현재는 UI Catalog와 Backend Registry를 별도 Scanner로 비교해야 한다.

---

# 24. UI Trace Source

UI→ServiceId를 찾을 때 최소:

```text
TransactionCatalog
Static HTML/JS
service-client
fetch / axios
sample request
URL
Header rms_svc_c
```

를 함께 본다.

Catalog만 보지 않는다.

---

# 25. Screen / Event Model

Target:

```text
Screen
   │
   ├─ Event: Search
   │      └─ ServiceId S0
   │
   ├─ Event: Create
   │      └─ ServiceId C0
   │
   └─ Event: Delete
          └─ ServiceId D0
```

Relation:

```text
Screen
HAS_EVENT
ScreenEvent

ScreenEvent
TRIGGERS
ServiceId
```

실제 Screen ID는 UI Source에서 추출한다.

---

# 26. FIG-X-09 — Forward Traceability

NSIGHT Target 정방향:

```text
Requirement
   ↓
Architecture Principle
   ↓
ADR / Decision
   ↓
Business Domain
   ↓
Function
   ↓
Screen
   ↓
Screen Event
   ↓
Program ID
   ↓
ServiceId
   ↓
Endpoint
   ↓
Dispatcher
   ↓
Handler
   ↓
Facade
   ↓
Service
   ↓
Rule [optional]
   ↓
DAO
   ↓
Mapper / SqlId
   ↓
Table / View
   ↓
External Interface [if any]
   ↓
Build Artifact
   ↓
WAR / JVM / Host
   ↓
Test
   ↓
GUID / Runtime Trace
   ↓
Evidence
```

한 연결이 없으면 추적성 Coverage가 낮아진다.

---

# 27. FIG-X-10 — Reverse Traceability

장애 또는 변경에서 시작한다.

```text
Table / View
    ↑
SQL / SqlId
    ↑
Mapper
    ↑
DAO
    ↑
Service
    ↑
Facade
    ↑
Handler
    ↑
ServiceId
    ↑
Program
    ↑
Screen / Event
    ↑
Requirement / ADR
```

또는 Runtime:

```text
Error / GUID
     ↑
ServiceId
     ↑
Deployment
     ↑
Artifact
     ↑
Source Commit
     ↑
Architecture Baseline
```

---

# 28. 역방향 추적이 필요한 실제 상황

## Table Column 변경

```text
Column 변경
   ↓
어떤 SqlId?
   ↓
어떤 DAO?
   ↓
어떤 ServiceId?
   ↓
어떤 화면?
   ↓
어떤 Test?
```

## ServiceId 변경

```text
ServiceId 변경
   ↓
UI
Header
Handler
Policy
Log
Metric
ImageLog
Test
```

## Error 증가

```text
FW_TIMEOUT 증가
   ↓
어느 ServiceId?
   ↓
어느 SQL?
   ↓
어느 Host/JVM?
```

---

# 29. FIG-X-11 — Architecture Model Entity / Relation

최소 Entity:

```text
System
Business
Function
Program
ServiceId
Screen
ScreenEvent

Component
├─ Handler
├─ Facade
├─ Service
├─ Rule
└─ DAO

Mapper
SqlId
Table
View

Message
Header
DTO

TimeoutPolicy
TransactionPolicy
SecurityPolicy

BuildArtifact
Deployment
RuntimeTrace
Evidence

ADR
GAP
Drift
```

## 29.1 Design Relation

```text
System
  HAS_BUSINESS
Business
  HAS_FUNCTION
Function
  HAS_PROGRAM
Program
  PROVIDES_SERVICE
ServiceId
  HANDLED_BY
Handler
  CALLS
Facade
  CALLS
Service
  USES
DAO
  EXECUTES
Mapper / SqlId
  ACCESSES
Table
```

## 29.2 Runtime Relation

```text
Dispatcher
  DISPATCHES_TO
Handler

RuntimeStep
  RUNS_ON_THREAD
Worker

TimeoutExecutor
  STARTS_TRANSACTION
UnitOfWork

Facade
  PARTICIPATES_IN_TRANSACTION
UnitOfWork

Service
  CALLS_SQL
SqlId

ServiceId
  LOGGED_BY
ImageLog

ServiceId
  MEASURED_BY
Metric
```

---

# 30. Design Graph와 Runtime Graph를 분리

잘못된 모델:

```text
Handler CALLS WorkerThread
```

WorkerThread는 정적 Component Call이 아니다.

올바른 구분:

```text
[Design Graph]

Handler CALLS Facade


[Runtime Graph]

Dispatcher DISPATCHES_TO Handler
Handler RUNS_ON_THREAD Worker
```

이 구분은 “소스 구조”와 “실행 구조”를 혼합하지 않기 위해 중요하다.

---

# 31. Entity Identifier

Closed Loop 공통 식별자:

```text
architectureBaselineId
architectureModelVersion
requirementId
adrId
systemId
businessCode
serviceId
componentId
sourceCommit
buildId
artifactId
artifactHash
deploymentId
environment
testRunId
gateRunId
traceId
guid
evidenceId
evidenceHash
gapId
driftId
```

최우선 업무 추적키:

```text
serviceId
```

Runtime 거래 추적키:

```text
guid / traceId
```

Release 추적키:

```text
sourceCommit / artifactHash / deploymentId
```

---

# 32. FIG-X-12 — Source Map / Index Architecture

```text
Source Scanner
│
├─ Java Scanner
│    ├─ ServiceId
│    ├─ Handler
│    ├─ Facade
│    ├─ Service
│    └─ DAO
│
├─ Mapper Scanner
│    ├─ Namespace
│    ├─ SqlId
│    ├─ SQL
│    └─ Table/View
│
├─ UI Scanner
│    ├─ Screen
│    ├─ Event
│    └─ ServiceId
│
├─ Config Scanner
│    ├─ Timeout
│    ├─ Transaction
│    ├─ JWT
│    └─ Datasource
│
└─ Deploy Scanner
     ├─ Artifact
     ├─ WAR/JVM
     └─ Host
```

산출물 후보:

```text
serviceid-index
service-source-map
mapper-sql-index
table-access-index
screen-service-index
config-map
deployment-map
```

---

# 33. Source Map Row 후보

```yaml
serviceId: mgcoa9001S0
business:
  group: mg
  domain: co
  function: a
programId: mgcoa9001
handler:
facade:
service:
dao:
mapper:
sqlIds:
tables:
screenEvents:
timeoutPolicy:
transactionPolicy:
artifact:
deployment:
tests:
runtimeEvidence:
status:
```

실제 빈 값은 `[UNKNOWN]`으로 유지한다.

---

# 34. Table Access Index

예시 구조만 정의한다.

```yaml
table: <TABLE_OR_VIEW>
accessedBy:
  - serviceId:
    mapper:
    sqlId:
    accessType:
```

Table명을 Source Scanner 없이 생성하지 않는다.

---

# 35. FIG-X-13 — Naming / Layer Conformance Rules

```text
R-SERVICEID-FORMAT
11자 / 승인 거래코드

R-SERVICEID-CLASSIFICATION
ServiceId 업무축 = 승인분류표

R-SERVICEID-UNIQUE
전체 Scope ServiceId 유일

R-HANDLER-REGISTRATION
ServiceId → Handler 존재

R-HANDLER-BRANCH
등록된 ID를 handle()이 처리

R-HANDLER-NO-DAO
Handler → DAO 직접호출 금지

R-CONTROLLER-NO-DAO
Controller → DAO 금지

R-SERVICE-DAO
Service Persistence 호출 표준

R-MAPPER-SQLID-UNIQUE
Namespace + SqlId 유일

R-MAPPER-TABLE-TRACE
SqlId → Table Evidence 존재

R-BUSINESS-PACKAGE-ALIGN
ServiceId 업무축 = Java/Mapper 축

R-UI-SERVICE-COVERAGE
UI 호출 ServiceId ↔ Backend Registry 정합
```

---

# 36. Rule 속성

모든 Architecture Rule은 최소:

```text
Rule ID
Category
Title
Description
Source Evidence
Scope
Severity
Expected Pattern
Forbidden Pattern
Validation Method
Automation Candidate
Exception Policy
Owner
Status
```

를 가진다.

---

# 37. Rule Severity 후보

```text
CRITICAL
- ServiceId duplicate
- Private Key boundary
- Direct cross-domain DML
- Runtime Evidence 없는 release

HIGH
- Handler→DAO
- UI/Backend ServiceId Drift
- Timeout policy mismatch

MEDIUM
- Naming stem mismatch

LOW
- Documentation label/style
```

실제 Severity는 Rule Catalog에서 승인한다.

---

# 38. Rule Exception

예외 허용 시:

```text
Exception ID
Rule ID
Reason
Scope
Owner
Approved By
Expiry
Compensating Control
Evidence
```

만료일 없는 영구 예외를 기본으로 허용하지 않는다.

---

# 39. Architecture Test 유형

```text
Architecture Test
  - Package
  - Dependency
  - Naming
  - Mapping

Contract Test
  - Request/Response
  - Header
  - JWT
  - Error

Integration Test
  - Controller→TCF
  - Dispatcher→Handler
  - Handler→Facade
  - Service→DAO
  - DAO→Mapper

Security Test
  - JWT
  - Private Key
  - Logging

Runtime Policy Test
  - Timeout
  - Transaction
  - Query
```

---

# 40. FIG-X-14 — Source → Config → Test → Runtime Evidence

```text
Source
ServiceId / Component
        │
        ▼
Config
Timeout / JWT / DB / Deploy
        │
        ▼
Test
Architecture / Contract / Integration
        │
        ▼
Build
Commit / Artifact Hash
        │
        ▼
Deployment
Host / JVM / WAR
        │
        ▼
Runtime
GUID / ServiceId / Thread / TX / SQL
        │
        ▼
Evidence
Result / Metric / Log / Audit
```

Source만 있어도 완료가 아니고 Runtime Log만 있어도 완료가 아니다.

---

# 41. Runtime Evidence Chain

필수 Chain:

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
traceId / guid
      ↓
runtime evidence
```

하나라도 끊기면:

```text
"이 Runtime Evidence가 어느 Architecture/Source를 증명하는가?"
```

에 답하기 어렵다.

---

# 42. FIG-X-15 — Document → Model → Code → Test → Runtime Closed Loop

```text
┌────────────────────────────────────────┐
│ Architecture Document                  │
│ Requirement / Principle / ADR          │
└──────────────────┬─────────────────────┘
                   │ parse
                   ▼
┌────────────────────────────────────────┐
│ Architecture Model                     │
│ Entity / Relation / Policy             │
└──────────────────┬─────────────────────┘
                   │ validate
                   ▼
┌────────────────────────────────────────┐
│ Architecture as Code                   │
│ Source / Config / Rule                 │
└──────────────────┬─────────────────────┘
                   │ execute
                   ▼
┌────────────────────────────────────────┐
│ Architecture as Test                   │
│ Arch / Contract / Security / Runtime   │
└──────────────────┬─────────────────────┘
                   │ deploy
                   ▼
┌────────────────────────────────────────┐
│ Runtime Evidence                       │
│ ServiceId / GUID / SQL / Thread / TX   │
└──────────────────┬─────────────────────┘
                   │ compare
                   ▼
┌────────────────────────────────────────┐
│ Drift Detection                        │
└──────────────────┬─────────────────────┘
                   │
          ┌────────┴────────┐
          ▼                 ▼
       GAP / ADR       Model Update
          │                 │
          └────────┬────────┘
                   ▼
          New Document Baseline
                   │
                   └─────────────↺
```

---

# 43. “Architecture as Document”만으로 부족한 이유

문서:

```text
Handler는 DAO를 직접 호출하지 않는다.
```

만 있으면 사람이 놓칠 수 있다.

Closed Loop:

```text
Rule
   ↓
Source Scanner / ArchUnit
   ↓
Build
   ↓
FAIL
```

로 만들어야 한다.

---

# 44. FIG-X-16 — GAP → ADR → Rule → Test

```text
Runtime / Source Difference
       │
       ▼
GAP
       │
       ├─ trivial fix
       │     ↓
       │   Source Fix
       │
       └─ architecture decision required
             ↓
            ADR
             ↓
          Decision
             ↓
       Architecture Rule
             ↓
       Rule Implementation
             ↓
       Conformance Test
             ↓
       Runtime Verification
             ↓
       Baseline Update
```

## 44.1 ADR이 필요한 대표 항목

```text
Transaction Boundary
Timeout
Retry
Session
JWT Verification
Gateway
WAR Split
Cache
DB Pool
Masking
Encryption
ServiceId
Package
Logging
Audit
Failure Isolation
```

---

# 45. GAP 문서 구조

```yaml
gapId:
systemScope:
title:
asIs:
toBe:
difference:
impact:
severity:
sourceEvidence:
runtimeEvidence:
owner:
targetDate:
status:
```

GAP는 “TODO 문장”이 아니라 관리 Entity다.

---

# 46. ADR 구조

```text
1. Problem
2. Context
3. Requirement
4. Constraints
5. Alternatives
6. Comparison
7. Decision
8. Rationale
9. Impact
10. Risk
11. Implementation Location
12. Test Method
13. Runtime Evidence Method
14. Rollback / Migration
15. Deprecation Condition
```

---

# 47. FIG-X-17 — Architecture Gate G00 → HG90

NSIGHT Closed Loop Source를 기준으로 정리한다.

```text
G00
Source Baseline
   │
   ▼
G10
Architecture Document
   │
   ▼
G20
Architecture Model
   │
   ▼
G30
Model ↔ Code / Architecture as Code
   │
   ▼
G40
Architecture / Contract / Integration / Security Test
   │
   ▼
G50
Deployment / Runtime Evidence
   │
   ▼
G60
Drift Detection
   │
   ▼
G70
GAP / ADR Decision Gate
   │
   ▼
80
Model / Document Baseline Update
   │
   ▼
HG90
Final Architecture Baseline Release
```

> X장 이미지화 프롬프트는 `G00~G90`이라고 표현하지만 Closed Loop 실행 마스터의 최종 Gate 명칭은 **HG90**이다. 본 장은 실제 Closed Loop Source 명칭을 우선 사용한다.

---

# 48. Gate G00 — Source Baseline

PASS 조건:

```text
Source Root 확인
System Scope 확인
Generated Artifact 분리
Source / Document / Config 분리
Branch / Commit 확인
또는 UNKNOWN 명시
```

산출:

```text
source-inventory
document-inventory
config-inventory
SOURCE-BASELINE
```

---

# 49. Gate G10 — Document Baseline

PASS:

```text
Current Architecture Baseline 존재
Decision 상태 확인
AS-IS/TO-BE 혼합 없음
System Scope 존재
과거/현재 구분
주요 GAP 등록
```

---

# 50. Gate G20 — Architecture Model

PASS:

```text
Model Schema PASS
중복 ServiceId 없음
고아 Entity 없음
Traceability Coverage 기준 충족
UNKNOWN Relation 목록
Model Version
```

## 50.1 중요한 주의

Traceability Coverage의 실제 Threshold 값은 프로젝트 Gate Rule에서 승인해야 한다.

예시 `0.98`을 현재 확정 기준으로 복사하지 않는다.

---

# 51. Gate G30 — Architecture as Code

PASS:

```text
Build Definition 존재
Source Map Coverage
Model ↔ Code Mapping
Architecture Rule 실행기
Critical Rule 미구현 0 또는 승인예외
Build Reproducibility
```

산출:

```text
code-inventory
service-source-map
config-map
om-service-catalog
architecture-rules
rule-implementation-map
```

---

# 52. Gate G40 — Architecture as Test

필수 Evidence:

```text
Architecture Test
Unit / Integration Test
Security Test
Traceability Report
Evaluator 계산 결과
Test Run ID
```

사람이 수동으로 `PASS` 텍스트를 넣는 것으로 Gate를 통과하지 않는다.

---

# 53. Gate G50 — Runtime Evidence

PASS:

```text
Deployment ID
Artifact Hash
Runtime Scenario
Runtime Evidence
ServiceId ↔ TraceId
Evidence Manifest
Evidence Hash
```

## 53.1 Logging ≠ Runtime Evidence

단순 로그 파일 존재로 G50을 PASS하지 않는다.

반드시 Source/Artifact/Deployment와 연결되어야 한다.

---

# 54. Gate G60 — Drift Detection

PASS 조건:

```text
Critical Drift 모두 분류
High Drift 담당자 지정
Evidence 없는 Drift 금지
UNKNOWN 별도등록
자동수정 금지
```

Drift는 발견 즉시 문서를 자동 덮어쓰지 않는다.

---

# 55. Gate G70 — GAP / ADR

PASS 조건:

```text
Critical GAP 미결정 상태로 승격 금지
ADR 필요 항목은 승인 전 TO-BE 확정 금지
예외에는 만료일 필수
```

즉:

```text
GAP
→ 사람이 결정해야 할 문제
```

를 Agent가 자동 정답으로 바꾸지 않는다.

---

# 56. Stage 80 — Model / Document Baseline Update

Closed Loop 실행 Source는 G70 이후:

```text
Resolved GAP
   ↓
Approved ADR
   ↓
Architecture Model Update
   ↓
Schema Validation
   ↓
Source/Test Verification
   ↓
Document Baseline Update
   ↓
New Baseline ID
```

순서를 정의한다.

본 장에서는 이를 `Stage 80`으로 부른다.

실제 프로젝트에서 별도 `G80` 승인 Gate를 둘지는 Governance Decision으로 관리한다.

---

# 57. HG90 — Final Architecture Gate

최종 순서:

```text
Document Baseline Valid
        ↓
Model Schema Valid
        ↓
Model ↔ Source Match
        ↓
Architecture Rules PASS
        ↓
Build PASS
        ↓
Unit / Integration PASS
        ↓
Security PASS
        ↓
Deployment PASS
        ↓
Runtime Scenario PASS
        ↓
Runtime Evidence Captured
        ↓
Model ↔ Runtime Drift Check
        ↓
Critical Drift = 0
        ↓
Human Approval
        ↓
HG90 PASS
```

## 57.1 HG90 PASS 금지

```text
Runtime Evidence 없음
Critical GAP OPEN
Critical Drift OPEN
Source Commit UNKNOWN
Artifact Hash 없음
Deployment ID 없음
Rule Evaluator 미실행
Human Approval 없음
```

---

# 58. Human Approval

자동화로 대체하지 않을 결정:

```text
Architecture Baseline 승인
Critical Exception 승인
Security Risk Acceptance
DR/RPO/RTO 승인
서비스 중요도
대규모 Capacity Decision
Deprecation
Final HG90
```

자동 Test가 PASS해도 승인행위는 별도 Evidence다.

---

# 59. FIG-X-18 — Drift Detection Matrix

```text
Document
   │ D1
   ▼
Model
   │ D2
   ▼
Code
   │ D3
   ▼
Config
   │ D4
   ▼
Runtime
   │
   ▼
Evidence
```

실제 Drift 유형:

```text
D1 Document vs Model
D2 Model vs Code
D3 Model vs Config
D4 Code vs Runtime
D5 Config vs Runtime
D6 Test vs Runtime
```

---

# 60. Drift 예

## D1

```text
Document
ServiceId = 8개

Model
ServiceId = 13개
```

## D2

```text
Model Handler
mgcoa9001Handler

Source
없음
```

## D3

```text
Model timeout = approved X

Config timeout = Y
```

## D4

```text
Source Transaction Boundary 예상
≠
Runtime TX Owner
```

## D5

```text
Config Hikari Max = X

Runtime Max = Y
```

## D6

```text
Integration Test Timeout PASS

Production
late SQL commit/worker behavior 다름
```

---

# 61. Current Real Drift Example — UI Catalog

현재 확인가능한 실제 후보:

```text
Backend Handler Registry
13 ServiceIds

vs

UI TransactionCatalog
8 ServiceIds
```

이 차이가 무조건 Defect라는 뜻은 아니다.

정확한 판정:

```text
UI Catalog가 전체 Backend Service Catalog를 대표해야 하는 계약인가?
```

를 확인한 뒤:

```text
TRUE DRIFT
EXPECTED PARTIAL CATALOG
```

로 분류한다.

---

# 62. Drift Record

```yaml
driftId:
type:
systemScope:
serviceId:
expected:
actual:
evidence:
severity:
detectedAt:
status:
```

Status:

```text
OPEN
ACCEPTED
FIXED
DEFERRED
```

---

# 63. FIG-X-19 — Impact Analysis Graph

예: ServiceId 변경

```text
ServiceId
   │
   ├─ Header rms_svc_c
   ├─ Path / Endpoint
   ├─ Handler Registry
   ├─ Handler Branch
   ├─ Facade Method
   ├─ UI Call
   ├─ Transaction Catalog
   ├─ Timeout Policy
   ├─ Transaction Control
   ├─ ImageLog
   ├─ Metric
   ├─ Test
   └─ Documentation
```

예: Table 변경

```text
Table
  ↑
SqlId
  ↑
Mapper
  ↑
DAO
  ↑
Service
  ↑
ServiceId
  ↑
Screen
  ↑
Requirement
```

---

# 64. 변경영향 필수 대상

다음 변경은 Trace Graph Impact를 수행한다.

```text
ServiceId
Header
DTO
DB Column
Table
JWT Claim
Timeout
Transaction
Package
WAR
Mapper
Gateway Route
Error Code
Session
```

---

# 65. FIG-X-20 — Evidence Manifest Chain

```text
Architecture Baseline
ARCH-...
      │
      ▼
Model Version
MODEL-...
      │
      ▼
Source Commit
<sha>
      │
      ▼
Build ID
      │
      ▼
Artifact
hash
      │
      ▼
Deployment ID
      │
      ▼
Environment / Host / JVM
      │
      ▼
ServiceId
      │
      ▼
GUID / TraceId
      │
      ▼
Runtime Evidence
      │
      ▼
Evidence Hash
```

## 65.1 Evidence Manifest

```yaml
evidenceId:
evidenceType:
architectureBaselineId:
architectureModelVersion:
sourceCommit:
buildId:
artifactId:
artifactHash:
deploymentId:
environment:
serviceId:
traceId:
capturedAt:
collector:
hash:
```

---

# 66. Runtime Evidence Category

## Transaction

```text
ServiceId
GUID
Start/End
Result
Elapsed
Thread
TX Begin/Commit/Rollback
```

## SQL

```text
Mapper
SqlId
SQL Hash
Elapsed
Rows
Query Timeout
DB Pool
```

## Thread / Pool

```text
Tomcat Thread
PDMG Worker
Queue
Hikari
```

## JVM

```text
Heap
Metaspace
GC
CPU
Thread Count
```

## Security

```text
JWT Subject
Auth Result
Authorization
Audit
```

---

# 67. Evidence를 저장할 때의 원칙

Evidence 자체도 무결성이 필요하다.

```text
Evidence ID
Captured Time
Collector
Hash
Source/Deployment Link
```

특히 운영 Log를 복사한 텍스트만 보고:

```text
"이것이 Release A의 Evidence다"
```

라고 주장하지 않는다.

---

# 68. FIG-X-21 — Baseline Release / Change Management

```text
Current Baseline
ARCH-V1
    │
    ├─ GAP
    ├─ Drift
    └─ ADR
        │
        ▼
Approved Change
        │
        ▼
Model V2
        │
        ▼
Source / Test / Runtime Verify
        │
        ▼
Human Approval
        │
        ▼
ARCH-V2
        │
        ├─ CURRENT
        │
        └─ ARCH-V1
             └─ SUPERSEDED
```

이전 Baseline을 삭제하지 않는다.

---

# 69. Baseline 상태

```text
CURRENT
SUPERSEDED
DEPRECATED
```

Document에는:

```text
supersedes
superseded-by
last-verified
source-baseline
```

Metadata를 유지한다.

---

# 70. Baseline Version은 Evidence와 연결한다

예시 형태:

```text
Architecture Baseline
ARCH-...

Model
MODEL-...

Source Commit
<sha>

Build
BUILD-...

Deployment
DEPLOY-...
```

실제 Version Format은 프로젝트 기준으로 결정한다.

예시값을 현행 ID라고 사용하지 않는다.

---

# 71. FIG-X-22 — I~X Architecture Integration Map

```text
I. Vision / Strategy
      │
      ▼
II. Big Picture / Boundary
      │
      ▼
III. Module / Application
      │
      ▼
IV. Online Runtime
      │
      ▼
V. Thread / Timeout / Transaction
      │
      ▼
VI. Message / Context / Error / Log
      │
      ▼
VII. Security / JWT / SSO
      │
      ▼
VIII. Infrastructure / Capacity / HA / DR
      │
      ▼
IX. DevOps / OM / Observability
      │
      ▼
X. ServiceId / Traceability / Closed Loop
      │
      └───────────────────────────────┐
                                      │
                                      ▼
                                New Baseline
                                      │
                                      └──────→ I
```

X장은 별도 기술영역 하나가 아니라 I~IX를 **검증 가능한 한 시스템**으로 묶는다.

---

# 72. I~IX에서 X로 들어오는 Key

| Chapter | X에 넘기는 Key |
|---|---|
| I | Principle / NFR / Decision |
| II | System / Domain / Interface Boundary |
| III | Module / Package / Component |
| IV | Runtime Step / ServiceId / Handler |
| V | Thread / TX / Timeout Policy |
| VI | Message / GUID / Error / Log |
| VII | Security Policy / Principal / JWT |
| VIII | WAR / JVM / Host / Capacity / DR |
| IX | Commit / Artifact / Deploy / Metric / Evidence |
| X | 통합 Model / Rule / Drift / Baseline |

---

# 73. Traceability Coverage

Coverage는 Relation별로 본다.

예:

```text
ServiceId → Handler Coverage
Handler → Facade Coverage
DAO → Mapper Coverage
Mapper → Table Coverage
ServiceId → Test Coverage
ServiceId → Runtime Evidence Coverage
```

전체 평균 하나만 보면 Critical Link 누락이 숨을 수 있다.

---

# 74. Coverage 예시 구조

```text
ServiceId 13
Handler mapped 13
= 100%

Facade mapped ?
Service mapped ?
DAO mapped ?
SQL mapped ?
Table mapped ?
Runtime evidenced ?
```

현재 본 장에서는 Handler Registry 13개는 Source 근거가 있지만 나머지를 Source Scan 없이 100%라고 쓰지 않는다.

---

# 75. Orphan Entity

Model에서 찾아야 하는 고아:

```text
ServiceId without Handler
Handler without ServiceId
DAO without Mapper
Mapper without caller
SQL without table evidence
Screen without ServiceId
ServiceId without Test
Deployment without Artifact
Runtime Evidence without Deployment
```

고아가 모두 Defect는 아니다.

예외/공통기능이면 Classification이 필요하다.

---

# 76. FIG-X-23 — Current GAP / Priority Map

```text
P0 / Critical Trace
├─ ServiceId Full Source Index
├─ UI ↔ Backend Service Catalog
├─ DAO ↔ Mapper ↔ SQL ↔ Table
├─ Runtime Evidence
└─ Deployment Mapping

P1 / Architecture Rule
├─ Naming / Package Alignment
├─ Handler Dependency
├─ Transaction / Timeout
├─ JWT Boundary
└─ Log / GUID

P2 / Governance
├─ Model Schema
├─ Gate Evaluator
├─ Drift Automation
├─ Evidence Package
└─ Human Approval
```

---

# 77. Current GAP Register

| ID | GAP | Impact |
|---|---|---|
| GAP-X-01 | PDMG 전체 ServiceId 최신 기계 인덱스 미생성 | Trace |
| GAP-X-02 | UI TransactionCatalog와 Backend Registry SSOT 미통합 | UI/Backend Drift |
| GAP-X-03 | Program ID 중앙 발급/유일성 Registry 미확인 | Naming |
| GAP-X-04 | 분류표와 ServiceId 자동 검증 미구현 | Governance |
| GAP-X-05 | Handler `serviceIds()` ↔ `handle()` branch 자동 검증 미확인 | Runtime |
| GAP-X-06 | Handler→Facade→Service→DAO 전수 CALL Graph 미완료 | Source Trace |
| GAP-X-07 | DAO↔Mapper Namespace/SqlId 전수 Trace 미완료 | Data Trace |
| GAP-X-08 | SqlId→Table/View 전수 Trace 미완료 | Data Impact |
| GAP-X-09 | Screen/Event→ServiceId 전수 Index 미완료 | UI Trace |
| GAP-X-10 | ServiceId→Timeout/TX/Security Policy Index 미완료 | Runtime Policy |
| GAP-X-11 | ServiceId→WAR/JVM/Host 실제 Mapping 미완료 | Deployment |
| GAP-X-12 | ServiceId→Test Case Coverage 미완료 | Quality |
| GAP-X-13 | ServiceId→GUID/Runtime Evidence 자동연결 미완료 | Runtime |
| GAP-X-14 | Architecture Model 전체 Schema/Relation 완성도 미확정 | Model |
| GAP-X-15 | Rule Engine/Conformance Automation 적용범위 미확정 | Automation |
| GAP-X-16 | Gate Evaluator 실제 구현/운영 Evidence 미확정 | Gate |
| GAP-X-17 | Critical Drift/GAP Human Approval Workflow 미확정 | Governance |
| GAP-X-18 | Baseline Version/Model/Source/Artifact ID 통합 SSOT 미확정 | Release |
| GAP-X-19 | OM Catalog 현재 Source 미확정 | Operation Trace |
| GAP-X-20 | HG90 실제 운영 승인절차/승인자 미확정 | Final Gate |

---

# 78. Current RISK

| ID | Risk | 중요도 후보 |
|---|---|---|
| RISK-X-01 | 잘못된 ServiceId로 다른 Handler/정책 실행 | Critical |
| RISK-X-02 | UI Catalog와 Backend Registry 불일치 | High |
| RISK-X-03 | 병렬 개발 Program Number 충돌 | High |
| RISK-X-04 | 비표준 ServiceId도 Registry에 등록되면 실행 가능 | High |
| RISK-X-05 | Handler 등록/branch 한쪽만 수정 | High |
| RISK-X-06 | Package/Mapper 업무축 불일치 | High |
| RISK-X-07 | DAO↔SQL 추적 불가 | High |
| RISK-X-08 | Table 변경 Impact 누락 | Critical |
| RISK-X-09 | Runtime Evidence가 Source Release와 연결 안 됨 | Critical |
| RISK-X-10 | Document가 Source보다 오래됨 | High |
| RISK-X-11 | 자동 Gate 없이 수동 PASS | Critical |
| RISK-X-12 | Runtime Evidence 없는 HG90 | Critical |
| RISK-X-13 | Agent가 GAP을 승인된 TO-BE로 자동변환 | Critical |
| RISK-X-14 | Model과 Runtime Relation 혼합 | Medium/High |
| RISK-X-15 | Generated 문서를 Source of Truth로 오인 | High |
| RISK-X-16 | Critical Exception 만료관리 없음 | High |

---

# 79. OPEN Issue

| ID | 질문 |
|---|---|
| OPEN-X-01 | ServiceId/Program ID 공식 발급 Owner는 누구인가 |
| OPEN-X-02 | TransactionCatalog를 전체 UI Service Catalog로 승격할 것인가 |
| OPEN-X-03 | ServiceId Registry SSOT를 Code에서 생성할 것인가 별도 Catalog를 둘 것인가 |
| OPEN-X-04 | ServiceId 형식 검증을 Build/Runtime 어느 위치에서 강제할 것인가 |
| OPEN-X-05 | Business Classification Table의 기계판독 원본은 무엇인가 |
| OPEN-X-06 | DAO→Mapper 관계를 Namespace 규칙으로 검증할 것인가 실제 Method Call로 검증할 것인가 |
| OPEN-X-07 | SQL Parser가 Dynamic MyBatis SQL/Table을 어디까지 해석할 것인가 |
| OPEN-X-08 | View/Synonym/Procedure 내부 Table까지 Trace할 것인가 |
| OPEN-X-09 | Screen/Event ID SSOT는 무엇인가 |
| OPEN-X-10 | ServiceId Traceability Coverage 목표치는 얼마인가 |
| OPEN-X-11 | ServiceId별 Runtime Evidence 필수 Scenario는 무엇인가 |
| OPEN-X-12 | Model Storage를 YAML/JSON/Graph 중 무엇으로 운영할 것인가 |
| OPEN-X-13 | Gate Evaluator 실행주기는 PR/Build/Release 중 어디인가 |
| OPEN-X-14 | Critical Rule Exception 승인권자는 누구인가 |
| OPEN-X-15 | Human Approval Evidence를 어느 시스템에 저장할 것인가 |
| OPEN-X-16 | HG90을 실제 배포 Release Gate와 어떻게 연동할 것인가 |
| OPEN-X-17 | Baseline ID/Model Version/Release ID Naming은 무엇인가 |
| OPEN-X-18 | Drift 자동탐지 후 자동수정은 어디까지 금지할 것인가 |

---

# 80. ADR 후보

| ADR | 결정 주제 |
|---|---|
| ADR-X-01 | ServiceId / Program ID SSOT |
| ADR-X-02 | ServiceId Format/Classification Validation |
| ADR-X-03 | UI Catalog ↔ Backend Registry Generation Strategy |
| ADR-X-04 | Handler Registration/Branch Conformance |
| ADR-X-05 | Naming ↔ Package ↔ Mapper Alignment |
| ADR-X-06 | DAO / Mapper / SqlId Trace Strategy |
| ADR-X-07 | SQL → Table Static Analysis Scope |
| ADR-X-08 | Screen/Event → ServiceId Catalog |
| ADR-X-09 | Architecture Model Storage / Schema |
| ADR-X-10 | Design Graph vs Runtime Graph |
| ADR-X-11 | Traceability Coverage Threshold |
| ADR-X-12 | Architecture Rule Engine |
| ADR-X-13 | Gate Evaluator / Human Approval |
| ADR-X-14 | Runtime Evidence Manifest |
| ADR-X-15 | Drift Management |
| ADR-X-16 | Baseline Version / Release Identity |
| ADR-X-17 | Architecture Exception Expiry |
| ADR-X-18 | HG90 Final Approval Process |

---

# 81. Architecture Rule Catalog 후보

## Naming

```text
R-SERVICEID-FORMAT
R-SERVICEID-CLASSIFICATION
R-PROGRAMID-UNIQUE
R-SERVICEID-UNIQUE
R-PACKAGE-BUSINESS-ALIGN
R-MAPPER-BUSINESS-ALIGN
```

## Component

```text
R-HANDLER-REGISTRATION
R-HANDLER-BRANCH
R-HANDLER-NO-DAO
R-CONTROLLER-NO-DAO
R-SERVICE-DAO
R-DOMAIN-DEPENDENCY
```

## Data

```text
R-DAO-MAPPER-MAP
R-MAPPER-SQLID-UNIQUE
R-SQL-TABLE-TRACE
R-CROSS-DOMAIN-DML
```

## Runtime

```text
R-TX-OWNER
R-TIMEOUT-POLICY
R-GUID-SERVICEID-LOG
R-RUNTIME-EVIDENCE
```

## Security

```text
R-JWT-PRIVATE-KEY
R-JWT-VERIFY-POLICY
R-SENSITIVE-LOG
```

## Deployment

```text
R-WAR-DEPENDENCY
R-DEPLOYMENT-MAP
R-ARTIFACT-HASH
```

---

# 82. Conformance Rule → Validation Method

| Rule | Validation |
|---|---|
| ServiceId Format | Regex + Classification Lookup |
| ServiceId Unique | Handler Registry Scan |
| Handler Branch | AST/Source Scan + Test |
| Handler→DAO 금지 | ArchUnit/AST |
| Package Alignment | FQCN vs Parsed ServiceId |
| DAO→Mapper | Java/XML Parser |
| SqlId Unique | XML Index |
| SQL→Table | SQL Parser |
| Timeout | Config Scanner |
| TX Owner | Source + Integration Runtime |
| JWT Private Key | Repository/Config Secret Scan |
| Runtime Evidence | Manifest/Gate Evaluator |

---

# 83. Architecture as Code Workspace

권장:

```text
00-IN
10-DOCUMENT
20-MODEL
30-CODE
40-TEST
50-RUNTIME-EVIDENCE
60-DRIFT
70-GAP-ADR
80-GATE
90-OUT
```

각 단계가 다음 단계의 Evidence를 생성한다.

---

# 84. Required Model Files

```text
20-MODEL/system/system-model.yaml
20-MODEL/business/business-model.yaml
20-MODEL/service/service-model.yaml
20-MODEL/component/component-model.yaml
20-MODEL/data/data-model.yaml
20-MODEL/integration/integration-model.yaml
20-MODEL/runtime-policy/runtime-policy-model.yaml
20-MODEL/traceability/traceability-matrix.yaml
```

Schema:

```text
system-model.schema.json
service-model.schema.json
component-model.schema.json
runtime-policy.schema.json
traceability.schema.json
```

---

# 85. Required Source Map Files

```text
30-CODE/inventory/code-inventory.csv

30-CODE/source-map/
├─ service-source-map.yaml
├─ serviceid-index.csv
├─ mapper-sql-index.csv
├─ table-access-index.csv
└─ screen-service-index.csv

30-CODE/config-map/
└─ config-map.yaml

30-CODE/policy-as-code/
├─ architecture-rules.yaml
└─ rule-implementation-map.yaml
```

이 파일명은 Closed Loop Workspace의 권장 산출물이며 현재 실제 파일 생성완료를 의미하지 않는다.

---

# 86. Required Test Reports

```text
architecture-report
contract-report
unit-report
integration-report
security-report
traceability-report
runtime-policy-report
```

Gate는 Report 존재 여부뿐 아니라 결과와 Evidence ID를 확인한다.

---

# 87. FIG-X-24 — Architecture Closed Loop Completion Gate

```text
Is Current Document Valid?
          │
          ▼ YES
Is Architecture Model Valid?
          │
          ▼ YES
Does Model Match Source?
          │
          ▼ YES
Do Architecture Rules Pass?
          │
          ▼ YES
Do Build / Tests Pass?
          │
          ▼ YES
Is Exact Artifact Deployed?
          │
          ▼ YES
Is Runtime Scenario Executed?
          │
          ▼ YES
Is Runtime Evidence Captured?
          │
          ▼ YES
Is Critical Drift = 0?
          │
          ▼ YES
Is Critical GAP Closed/Approved?
          │
          ▼ YES
Human Approval?
          │
          ▼ YES
        HG90 PASS
```

하나라도 `NO`이면:

```text
CONDITIONAL / FAIL / HOLD
```

이다.

---

# 88. X장 정상 예시

```text
Requirement
REQ-001
   ↓
Screen
MG-9001
   ↓
ServiceId
mgcoa9001S0
   ↓
Handler
mgcoa9001Handler
   ↓
Facade
mgcoa9001Facade
   ↓
Service
mgcoa9001Service
   ↓
DAO
mgcoa9001DAO
   ↓
Mapper
mgcoa9001-ORA.xml
   ↓
SqlId / Table
[Source Scan]
   ↓
Test
[Traceable]
   ↓
Deployment
[Artifact/Host]
   ↓
Runtime
GUID + ServiceId
```

`REQ-001`, `MG-9001`은 형식 설명용 예시이며 현재 공식 Requirement/Screen ID가 아니다.

---

# 89. X장 금지 예시

```text
Document
"ServiceId 8개"
      ↓
현재 Source 확인 없이 Baseline 유지
      X
```

```text
Handler
  ↓
DAO
  X
```

```text
SQL 파일 이름만 보고
Table명 추정
      X
```

```text
Runtime Log 존재
      ↓
Source Commit/Artifact/Deploy 연결 없이
"Architecture Evidence"
      X
```

```text
Critical GAP
      ↓
LLM이 자동으로 TO-BE 결정
      X
```

---

# 90. Impact Analysis Checklist

ServiceId 변경 시:

```text
[ ] UI
[ ] Header
[ ] Path
[ ] Handler Registry
[ ] Handler branch
[ ] Facade
[ ] Timeout policy
[ ] Transaction control
[ ] JWT/authorization
[ ] Log
[ ] ImageLog
[ ] Metric
[ ] Test
[ ] OM
[ ] Document
```

Table 변경 시:

```text
[ ] SQL
[ ] Mapper
[ ] DAO
[ ] Service
[ ] ServiceId
[ ] Screen
[ ] Data authorization
[ ] Performance
[ ] Batch/External
[ ] Test
```

---

# 91. Traceability Matrix 최소 컬럼

```text
Requirement ID
Business
Screen
Program ID
ServiceId
Handler
Facade
Service
DAO
Mapper
SqlId
Table/View
External Interface
Timeout Policy
Transaction Policy
Security Policy
Test Case
Artifact
Deployment
Host/JVM
Runtime Evidence
GAP/ADR
Status
```

값 없는 컬럼은 삭제하지 않고 `[UNKNOWN]`으로 남긴다.

---

# 92. Evidence Status

각 Relation:

```text
FACT
CONFIRMED
AS-IS
TO-BE
PROPOSED
GAP
UNKNOWN
DEPRECATED
```

을 가진다.

예:

```text
ServiceId → Handler
[FACT]

ServiceId → Table
[UNKNOWN until SQL scan]

ServiceId → Target Timeout
[PROPOSED or DECISION]
```

---

# 93. Current Confirmed / AS-IS

현재 본 장에서 확정할 수 있는 핵심:

```text
ServiceId = 11자
MG/CO/A 업무축
Java Root = nhnis.mg.co.a
Mapper Root = rdw.mg.co.a
Current Handler Registered Service = 13
Handler Registry = Runtime Routing Key
UI TransactionCatalog = 8
UI Catalog와 Backend Registry는 단일 SSOT가 아님
ServiceId 문자열 자동 case/space normalization 없음
Handler serviceIds + handle branch 둘 다 필요
Handler→Facade→Service→DAO/Mapper 구조
```

---

# 94. Current UNKNOWN

```text
전체 ServiceId 최신 Repository Index
전체 Screen/Event Mapping
전체 DAO→Mapper
전체 SqlId→Table
전체 External Interface
전체 ServiceId→Timeout Policy
전체 ServiceId→Security Policy
전체 Test Coverage
전체 WAR/JVM/Host Mapping
전체 Runtime Evidence Coverage
pdmg-om Catalog
Architecture Gate Evaluator Runtime
HG90 실제 승인 Workflow
```

---

# 95. Definition of Done — X장 관점

Service 하나가 완성됐다고 판단하려면 최소:

```text
ServiceId valid
Handler mapped
Business path mapped
DAO/Mapper mapped
Policy mapped
Test mapped
Artifact/Deployment mapped
Runtime evidence captured
No critical drift
```

가 필요하다.

---

# 96. 프로젝트 최종 Architecture 완료 정의

NSIGHT/PDMG Architecture 전체 완료는 다음 질문이 모두 YES인 상태다.

```text
문서가 현재 기준인가?
        YES
Model이 존재하는가?
        YES
Schema가 유효한가?
        YES
ServiceId가 Source와 연결되는가?
        YES
SQL/Data까지 추적되는가?
        YES
Architecture Rule이 실행되는가?
        YES
Build/Test가 재현되는가?
        YES
정확한 Artifact가 배포됐는가?
        YES
Runtime Evidence가 존재하는가?
        YES
Model과 Runtime이 일치하는가?
        YES
Critical Drift가 없는가?
        YES
Critical GAP이 승인/해소됐는가?
        YES
Human Approval이 존재하는가?
        YES
```

이 상태에서:

```text
ARCHITECTURE CLOSED LOOP = PASS
```

로 판단한다.

---

# 97. 검증 체크리스트

## 97.1 Naming

- [x] ServiceId 11자 구조가 Source와 일치하는가
- [x] 거래코드 S/C/U/D/A/R을 Current/Standard로 구분했는가
- [x] `MG/CO/A`를 모든 신규업무 강제축으로 쓰지 않았는가
- [x] Program ID와 ServiceId를 구분했는가
- [x] Regex만으로 유효성을 끝내지 않았는가

## 97.2 Registry

- [x] Current Handler 13개 ServiceId를 반영했는가
- [x] UI Catalog 8개와의 차이를 반영했는가
- [x] serviceIds()/handle() 이중 정합성을 반영했는가
- [x] 중복 ServiceId Startup Failure를 반영했는가
- [x] 문자열 자동 정규화를 가정하지 않았는가

## 97.3 Source / Data Trace

- [x] Handler→Facade→Service→DAO 구조가 있는가
- [x] Rule Layer를 AS-IS로 창작하지 않았는가
- [x] DAO→Mapper→SqlId→Table Trace가 있는가
- [x] 실제 Table명을 근거 없이 만들지 않았는가
- [x] Screen→ServiceId Scanner를 별도 정의했는가

## 97.4 Model

- [x] Entity/Relation이 정의되는가
- [x] Design/Runtime Graph가 분리되는가
- [x] ServiceId가 최우선 추적키인가
- [x] GUID/TraceId와 ServiceId 역할을 구분했는가
- [x] Orphan Entity를 정의했는가

## 97.5 Closed Loop

- [x] Document→Model→Code→Test→Runtime Loop가 있는가
- [x] Source/Config/Test/Runtime Evidence Chain이 있는가
- [x] GAP→ADR→Rule→Test가 있는가
- [x] Drift Matrix가 있는가
- [x] Baseline Version/History가 있는가

## 97.6 Gate

- [x] G00 Source가 있는가
- [x] G10 Document가 있는가
- [x] G20 Model이 있는가
- [x] G30 Code가 있는가
- [x] G40 Test가 있는가
- [x] G50 Runtime Evidence가 있는가
- [x] G60 Drift가 있는가
- [x] G70 GAP/ADR이 있는가
- [x] Stage 80 Update가 있는가
- [x] HG90 Final Gate가 있는가
- [x] Runtime Evidence 없는 Final PASS를 금지했는가
- [x] Human Approval을 자동화하지 않았는가

---

# 98. Completion Gate

```text
Figure Plan                           24
실제 Text Figure                     24

Business Taxonomy                    PASS
ServiceId Anatomy                    PASS
Program vs Service                   PASS
Naming Projection                    PASS
Handler Registry 13                  PASS
Service→Business Mapping             PASS
DAO→Mapper→SQL→Table                 CONDITIONAL
UI Catalog Drift                     PASS
Forward Trace                        PASS
Reverse Trace                        PASS
Architecture Model                   PASS
Source Map                           PROPOSED
Conformance Rules                    PASS
Source→Runtime Evidence              PASS
Closed Loop                          PASS
GAP→ADR→Rule→Test                    PASS
G00→HG90                             PASS
Drift Detection                      PASS
Impact Analysis                      PASS
Evidence Manifest                    PASS
Baseline Management                  PASS
I~X Integration                      PASS
Current GAP Map                      PASS
Final Completion Gate                PASS

근거 없는 Table명 생성               0건
13 ServiceId를 8개로 오기재          0건
Rule Layer AS-IS 창작                0건
Runtime Evidence 없는 Final PASS      0건
GAP 자동승인                         0건
```

**판정: CONDITIONAL PASS**

## PASS 전환 조건

```text
Condition-X-01
Current Repository 전체 ServiceId Index 기계생성

Condition-X-02
UI Screen/Event/TransactionCatalog ↔ Backend Registry 전수 비교

Condition-X-03
Handler serviceIds() ↔ handle() branch 자동검증

Condition-X-04
Handler→Facade→Service→DAO 전수 Source Call Graph 생성

Condition-X-05
DAO↔Mapper Namespace/SqlId Index 생성

Condition-X-06
SqlId→Table/View Static Trace 생성 및 Dynamic SQL 예외 분류

Condition-X-07
ServiceId→Timeout/TX/Security Policy Map 생성

Condition-X-08
ServiceId→Test Coverage Report 생성

Condition-X-09
ServiceId→WAR/JVM/Host Deployment Map 생성

Condition-X-10
ServiceId→Runtime GUID/Evidence Manifest 연결

Condition-X-11
20-MODEL Schema/Relation 검증

Condition-X-12
Architecture Rule Evaluator 실제 실행

Condition-X-13
Critical Drift/GAP Approval Workflow 확정

Condition-X-14
Baseline/Model/Source/Artifact/Deploy ID SSOT 확정

Condition-X-15
HG90 Human Approval / Final Release 절차 검증
```

---

# 99. 최종 평가

X장은 NSIGHT/PDMG Architecture 정의서의 마지막 기술장이면서 동시에 **I~IX장을 다시 연결하는 통합 검증장**이다.

가장 중요한 결론은 다음과 같다.

> **PDMG의 ServiceId는 단순 URL이나 메서드명이 아니라 Business Classification, Runtime Routing, Policy, Logging, Test, Operations를 연결하는 최우선 Architecture Key다.**

> **현재 ServiceId는 11자이며 `mgcoa9001S0 = mg/co/a/9001/S/0`처럼 업무분류와 프로그램, 거래행위를 함께 표현한다.**

> **현재 Handler Source에는 13개 거래가 등록되어 있고, UI TransactionCatalog는 그보다 작은 8개 카탈로그이므로 Catalog와 Runtime Registry를 하나의 SSOT라고 볼 수 없다.**

> **올바른 Traceability는 `ServiceId → Handler → Facade → Service → DAO → Mapper/SqlId → Table`의 정방향뿐 아니라 `Table/Error → SQL → DAO → ServiceId → Screen → Requirement`의 역방향도 지원해야 한다.**

> **Source에 없는 Table/View, Screen, Deployment Relation은 이름에서 추정하지 않고 Scanner/Config/Runtime Evidence가 확보될 때까지 UNKNOWN으로 유지해야 한다.**

> **Architecture Model은 Design Relation과 Runtime Relation을 분리하고, Rule은 사람이 읽는 문장이 아니라 실행 가능한 Architecture Test로 변환해야 한다.**

> **Runtime Evidence에는 Architecture Baseline, Model Version, Source Commit, Artifact Hash, Deployment ID, ServiceId, GUID/TraceId가 연결되어야 하며 단순 로그 존재만으로는 Architecture Evidence가 되지 않는다.**

> **Drift는 문서와 Source 중 어느 하나를 자동 정답으로 덮어쓰는 문제가 아니라 Evidence를 기반으로 GAP/ADR을 발생시키는 Governance Mechanism이다.**

> **최종 HG90은 Document/Model/Code/Test/Deployment/Runtime Evidence/Drift/Human Approval이 모두 연결될 때만 PASS할 수 있다.**

> **따라서 NSIGHT Architecture의 최종 형태는 문서 묶음이 아니라 다음 폐쇄루프다.**

```text
Architecture
    ↓
Model
    ↓
Source / Config
    ↓
Test
    ↓
Build / Deploy
    ↓
Runtime Evidence
    ↓
Drift
    ↓
GAP / ADR
    ↓
New Baseline
    └──────────────────────────────↺
```

---

# 100. I~X 전체 정의서 최종 Route

```text
I
왜 바꾸는가?
Vision / Strategy
       ↓
II
어디까지가 우리 시스템인가?
Big Picture / Boundary
       ↓
III
무엇으로 구성되는가?
Module / Application
       ↓
IV
요청 한 건이 어떻게 흐르는가?
Online Runtime
       ↓
V
어느 Thread/TX에서 실행되는가?
Timeout / Transaction / DB
       ↓
VI
어떻게 계약하고 추적하는가?
Message / Context / Error / Log
       ↓
VII
누구를 신뢰하고 무엇을 허용하는가?
Security / JWT / SSO
       ↓
VIII
어디서 얼마나 버티며 장애를 견디는가?
Infrastructure / Capacity / HA / DR
       ↓
IX
어떻게 배포하고 운영에서 증명하는가?
DevOps / OM / Observability
       ↓
X
모든 것을 어떻게 추적·검증·갱신하는가?
ServiceId / Traceability / Closed Loop
       ↓
NEW BASELINE
       └───────────────────────────────────→ I
```

**NSIGHT/PDMG Architecture Definition의 최종 목표는 “한 번 완성된 그림”이 아니라 “변경되어도 다시 검증 가능한 Architecture”다.**
