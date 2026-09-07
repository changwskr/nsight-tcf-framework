# NSIGHT / PDMG 아키텍처 인포그래픽 이미지화 마스터 프롬프트 — TYPE2

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 목적: 기존 NSIGHT/PDMG 아키텍처 정의서와 PDMG Source/Config/Runtime Evidence를 읽고, **VISION → BIG PICTURE → LOGICAL → PHYSICAL → MECHANISM → RUNTIME**의 6단계 Top-down 순서로 재구성한 Architecture Definition을 여행계획표형 인포그래픽으로 시각화하기 위한 재사용 마스터 프롬프트  
> 문서 성격: Architecture Definition Visualization Master Prompt / TYPE2  
> 기본 철학: **상위 Architecture 의도와 책임을 먼저 고정하고, 하위 구현과 Runtime Evidence를 뒤에서 검증한다.**  
> 산출 형식: 6개 대장(Part) + 대장별 상세 장표/보조 이미지 + Inventory/Mapping/Traceability 카드

---

# 0. TYPE2의 핵심 변경점

TYPE2는 기존의 기술 주제별 나열을 버리는 것이 아니다. 기존 산출물을 **Architecture Level** 기준으로 다시 배치한다.

```text
TYPE1
Vision / Big Picture / PDMG Module / TCF Runtime / Timeout / Message
/ Security / Infrastructure / DevOps / Traceability

                    ↓ 재배치

TYPE2
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
Evidence / GAP / ADR / Closed Loop
```

TYPE2에서 가장 중요한 질문은 다음 하나다.

```text
이 내용은
"왜 / 무엇을 지향하는가?"        → VISION
"전체 공간과 책임은 무엇인가?"    → BIG PICTURE
"어디에 어떤 논리 역할을 둘 것인가?" → LOGICAL
"어느 물리 자원에 배치할 것인가?"  → PHYSICAL
"어떤 표준 원리로 동작시킬 것인가?" → MECHANISM
"실제로 시간순으로 어떻게 실행되는가?" → RUNTIME
```

하나의 주제가 둘 이상의 레벨을 횡단하면 **주 소속 + Cross Reference** 방식으로 작성한다.

예:

```text
Interface
  ├─ LOGICAL   : 어떤 경계와 경로를 허용하는가
  ├─ MECHANISM : API/Event/CDC/ETL/File 중 무엇을 선택하고 어떤 계약을 쓰는가
  └─ RUNTIME   : 실제 요청·응답·재시도·오류 시퀀스는 어떻게 흐르는가

HA / DR
  ├─ PHYSICAL  : 센터·서버·복제·배치 구조
  └─ RUNTIME   : 장애 탐지·전환·복구·Drill 시퀀스와 Evidence
```

---

# 1. ROLE

너는 지금부터 다음 역할을 동시에 수행한다.

```text
Chief Enterprise Architect
+ Application Architect
+ Data Architect
+ Technical / Infrastructure Architect
+ Interface Architect
+ Security Architect
+ TCF / PDMG Framework Architect
+ Transaction / Timeout Architect
+ Operations / Observability Architect
+ Architecture Governance Architect
+ Architecture Visual Designer
```

이번 작업은 일반적인 금융권 Best Practice를 예쁘게 그리는 작업이 아니다.

반드시 다음 자료를 우선한다.

```text
1. NSIGHT 공식 전략 / 정의 / 기준 자료
2. NSIGHT Architecture Definition 및 분석 Markdown
3. 승인된 ADR / Baseline / Inventory
4. PDMG Current Source / Config / Runtime Evidence
5. 과거 문서 / 참고 그림
6. 일반론 또는 추론
```

일반론은 자료 공백을 메우는 용도로 자동 사용하지 않는다.

---

# 2. NSIGHT와 PDMG의 위치

NSIGHT와 PDMG를 같은 레벨로 취급하지 않는다.

```text
NSIGHT
= Target Architecture / Strategy / Enterprise Baseline

PDMG
= Reference Implementation / AS-IS / Source Evidence
```

PDMG는 NSIGHT 전체 Architecture의 하위 Reference다.

```text
NSIGHT Target
   ↓
Enterprise / System / Data Responsibility
   ↓
Logical / Physical / Mechanism / Runtime
   ↓
PDMG Reference
   ↓
Source / Config / Runtime Evidence
```

금지:

```text
PDMG Handler 구조를 NSIGHT Big Picture 전체로 그리기
pdmg-fw 별도 모듈을 독립 원격 서버라고 단정하기
PDMG 구현을 NSIGHT 전사 표준으로 자동 승격하기
Source에 없는 OM/JWT/TCF 기능을 일반론으로 채우기
```

---

# 3. Evidence-First 규칙

모든 핵심 내용은 상태를 구분한다.

| 상태 | 의미 |
|---|---|
| `[FACT]` | 공식 문서·Source·Config·Runtime에서 직접 확인 |
| `[CONFIRMED]` | 복수 Evidence가 일치 |
| `[BASELINE-YYYY-MM-DD]` | 특정 시점의 기준선 |
| `[AS-IS]` | 현재 구현 또는 현행 구조 |
| `[TO-BE]` | 승인된 목표 구조 |
| `[DECISION]` | 승인된 의사결정 |
| `[PROPOSED]` | 제안·후보 |
| `[GAP]` | 목표와 구현/자료의 차이 |
| `[CONFLICT]` | 근거 간 충돌 |
| `[RISK]` | 장애·보안·성능·운영 위험 |
| `[OPEN]` | 결정 필요 |
| `[UNKNOWN]` | 현재 근거로 확인 불가 |
| `[DEPRECATED]` | 폐기된 과거 기준 |

## 3.1 사실 우선순위

```text
Current Source / Current Config
        >
Runtime Evidence
        >
Approved Architecture Baseline / ADR
        >
공식 설계/전략 문서
        >
Working Baseline / 분석 Markdown
        >
과거 문서 / 그림
        >
일반론 / 추론
```

단, **Source는 AS-IS를 증명하는 것이지 TO-BE의 우선순위를 자동으로 뒤집지 않는다.**

```text
Source ≠ Target
Source = AS-IS Evidence
Architecture = TO-BE / Decision
Difference = GAP
```

## 3.2 숫자 사용 규칙

다음 값은 근거·시점 없이 생성하지 않는다.

```text
TPS / TPMC
p95 / SLA
Timeout
Thread / Queue
Hikari Pool
JVM Heap
CPU / Memory
Server Count
RTO / RPO
Port
Session Time
CDC 지연
Batch 완료시각
```

과거 산정값은 반드시 예처럼 표시한다.

```text
[BASELINE-2026-06-03]
현재 운영값 아님
```

---

# 4. TYPE2 전체 Architecture Route

모든 장은 다음 한 줄을 기준으로 연결한다.

```text
VISION
왜 바꾸는가 / 무엇을 지향하는가
   ↓
BIG PICTURE
어떤 업무·시스템·데이터 공간으로 나누는가
   ↓
LOGICAL
어디에 어떤 논리 역할·경계·레이어를 둘 것인가
   ↓
PHYSICAL
논리 역할을 어떤 센터·서버·SW·DB에 배치할 것인가
   ↓
MECHANISM
거래·연계·인증·추적·예외를 어떤 표준 원리로 동작시킬 것인가
   ↓
RUNTIME
요청·이벤트·배치·장애가 시간순으로 실제 어떻게 실행되는가
   ↓
Evidence / Validation / GAP / ADR / Baseline Update
```

## 4.1 레벨별 산출물 정의

| Level | 반드시 답할 질문 | 대표 산출물 |
|---|---|---|
| VISION | 왜 개편하며 성공 기준은 무엇인가 | 기본방향, 목표, 원칙, NFR |
| BIG PICTURE | 전체 책임·업무·데이터 공간은 무엇인가 | 개념도, 분류체계, 그룹, 주제영역, 전체 구조 |
| LOGICAL | 어떤 논리 경계·시스템·레이어가 필요한가 | Zone, Logical Node, Layer, Logical IF |
| PHYSICAL | 실제 어디에 얼마나 배치하는가 | Center, Host, SW, DB, Capacity, HA/DR |
| MECHANISM | 어떤 표준 방식으로 안전하게 동작시키는가 | 8단계, 전문, GUID, IF Pattern, SSO, Error |
| RUNTIME | 실제 시간순 실행과 실패·복구는 어떻게 되는가 | Sequence, Thread/TX, 12 Runtime Types, Evidence |

---

# 5. 기존 정의서 → TYPE2 재배치 기준

| 기존 정의서 | TYPE2 주 소속 | Cross Reference |
|---|---|---|
| 01 아키텍처 정의 | VISION / BIG PICTURE | 전체 |
| 02 논리기술아키텍처 | LOGICAL | BIG PICTURE / PHYSICAL |
| 03 물리인프라아키텍처 | PHYSICAL | LOGICAL / RUNTIME |
| 04 데이터베이스아키텍처 | LOGICAL + PHYSICAL | MECHANISM / RUNTIME |
| 05 시스템표준 | PHYSICAL | MECHANISM / Governance |
| 06 인터페이스아키텍처 | LOGICAL + MECHANISM | RUNTIME |
| 07 런타임아키텍처 | RUNTIME | MECHANISM |
| 08 아키텍처표준화 | MECHANISM | RUNTIME |
| 09 아키텍처구성요소 | MECHANISM | LOGICAL / RUNTIME |
| 10 업무솔루션아키텍처 | BIG PICTURE + LOGICAL | MECHANISM / RUNTIME |
| 11 모니터링·가용성·DR·백업 | PHYSICAL + RUNTIME | VISION NFR |
| PDMG Module/Application | LOGICAL / MECHANISM | RUNTIME |
| PDMG TCF Flow | RUNTIME | MECHANISM |
| Transaction/Timeout/Thread/DB | RUNTIME | MECHANISM / PHYSICAL |
| DevOps/OM/Observability | RUNTIME / Governance | PHYSICAL |
| Naming/ServiceId/Traceability | MECHANISM + Closed Loop | 전체 |

---

# 6. 이미지 디자인 스타일 — 여행계획표형 Architecture Guide

모든 장은 **프리미엄 여행 일정표 / 도시 가이드 / 로드맵**의 친근한 시각 언어를 사용하되, Enterprise Architecture 문서의 정확성을 우선한다.

## 6.1 기본 스타일

```text
배경      : Cream / Ivory
주색      : Teal / Mint / Sky Blue / Deep Navy
보조색    : Orange / Amber / Purple / Red
카드      : Rounded Card
흐름      : Route Line / Milestone / Number Badge
아이콘    : Server / DB / Shield / Link / Gear / Eye / Compass
텍스트    : 한글 중심, 짧고 정확한 Label
Canvas    : Portrait A4 비율, 약 1055×1491 또는 1000:1414
```

## 6.2 레벨별 대표 색상 권장

```text
VISION       : Deep Navy + Gold
BIG PICTURE  : Teal + Sky
LOGICAL      : Blue + Mint
PHYSICAL     : Slate + Cyan
MECHANISM    : Purple + Teal
RUNTIME      : Orange + Deep Navy
GAP/RISK     : Red
OPEN/UNKNOWN : Gray
```

색상은 의미를 보조하는 수단이며 Architecture 책임보다 강하게 보이면 안 된다.

---

# 7. 모든 장의 공통 레이아웃

각 세로형 이미지는 기본적으로 다음 구조를 따른다.

```text
┌─────────────────────────────────────────┐
│ Chapter / Level / Scope Badge           │
├─────────────────────────────────────────┤
│ 1. 한눈에 보는 Route                    │
│    이 장에서 다음 장까지의 Journey      │
├─────────────────────────────────────────┤
│ 2. Architecture Main View               │
│    해당 레벨의 가장 큰 구조             │
├─────────────────────────────────────────┤
│ 3. Detail Cards                         │
│    3~8개 핵심 정의 / 인벤토리 / 매핑    │
├─────────────────────────────────────────┤
│ 4. Cross Reference                      │
│    상위/하위 레벨 연결                  │
├─────────────────────────────────────────┤
│ 5. Guardrail / Forbidden / Risk         │
├─────────────────────────────────────────┤
│ 6. FACT / GAP / ADR / Next Route        │
└─────────────────────────────────────────┘
```

## 7.1 절대 규칙

- 구조보다 클래스명이 먼저 보이지 않게 한다.
- 한 그림에 20개 이상의 작은 박스를 억지로 넣지 않는다.
- 긴 Inventory는 **요약 그림 + 별도 Inventory 카드/보조 이미지**로 분리한다.
- `UNKNOWN`은 삭제하지 않고 `? / TBD / Unknown Boundary`로 표시한다.
- AS-IS와 TO-BE를 같은 박스 안에 섞지 않는다.
- Source Class/Config는 `AS-IS Evidence` 카드에 별도로 둔다.
- 표는 구조를 보완하는 용도로만 사용한다.

---

# 8. 공통 Inventory 규칙

TYPE2는 예쁜 그림만 만드는 작업이 아니다. 분류·시스템·데이터·서버를 **Inventory**로 닫는다.

## 8.1 Inventory 공통 필드

가능한 경우 다음 공통 컬럼을 사용한다.

```text
ID
Name
Domain / Group
Role / Responsibility
Owner
AS-IS / TO-BE
Environment
Logical Node
Physical Mapping
Interface
Data Ownership
Runtime Type
Evidence
Status
GAP / ADR
```

## 8.2 Inventory 없는 그림 금지 대상

다음 주제는 그림만 만들고 끝내지 않는다.

```text
Application Classification
System Group
Application ↔ System Mapping
Data Subject Area
Logical Node
Physical Server
Interface
Runtime Type
ServiceId / Component / SQL Traceability
```

---

# 9. PART I — VISION

## 9.1 목적

NSIGHT를 구현기술이 아니라 **개편 이유·구축방향·목표·Architecture Principle·NFR**에서 시작한다.

```text
현재 문제 / Business Need
        ↓
개편 기본 방향
        ↓
구축방향 및 목표
        ↓
Architecture Principle
        ↓
NFR / 성공 기준
        ↓
BIG PICTURE로 전달
```

## 9.2 목차

```text
I. VISION
1. 개요
   1.1 아키텍처 정의 목적
   1.2 개편 기본 방향
   1.3 적용 범위 / 이해관계자
2. 구축방향 및 목표
   2.1 고객 중심 서비스 강화
   2.2 데이터 기반 의사결정 강화
   2.3 통합 정보 활용 기반 강화
   2.4 유연하고 안정적인 운영체계
3. Architecture Principle
4. NFR / SLA / 성공 기준
5. NSIGHT Target과 PDMG Reference 관계
```

## 9.3 필수 이미지

```text
V-01 NSIGHT Architecture Vision Journey
V-02 개편 기본 방향 4축
V-03 Business Need → Architecture Goal
V-04 Technical / Application / Data 3축
V-05 NFR 5축 지도
     Performance / Availability / Scalability / Security / Observability
V-06 Architecture Principle Guardrail
V-07 NSIGHT Target vs PDMG Reference
V-08 VISION → BIG PICTURE Handoff
```

## 9.4 반드시 반영할 방향

```text
① 고객 중심 서비스 강화
② 데이터 기반 의사결정 강화
③ 통합 정보 활용 기반
④ 유연·안정 운영체계
```

NFR은 선언어가 아니라 가능한 경우 다음으로 연결한다.

```text
NFR
 → 지표
 → 측정 구간
 → Owner
 → Validation
```

## 9.5 금지

```text
Vision 장에서 Hostname/Port/SQL을 메인으로 설명
TCF Flow를 Vision의 대표 그림으로 사용
NFR을 “빠르게 / 안정적으로” 같은 형용사만으로 작성
PDMG AS-IS 수치를 NSIGHT Target으로 자동 승격
```

---

# 10. PART II — BIG PICTURE

## 10.1 목적

NSIGHT의 **업무·애플리케이션·시스템·데이터·외부 연계 공간과 책임**을 한눈에 보여준다.

```text
VISION
  ↓
Concept Architecture
  ↓
Application Classification
  ↓
System Group / Business Responsibility
  ↓
Data Subject Area
  ↓
Whole System Architecture
  ↓
Target System / Server Candidate Identification
  ↓
LOGICAL
```

## 10.2 목차

```text
II. BIG PICTURE
1. 개념 아키텍처
2. 애플리케이션 분류체계
   2.1 애플리케이션 도메인 구성
   2.2 애플리케이션 분류 체계 인벤토리
3. 시스템 그룹 업무 구분
   3.1 시스템 그룹별 책임
   3.2 애플리케이션 ↔ 시스템 간 매핑 인벤토리
4. 데이터 주제영역 정의
   4.1 데이터 주제영역 구조
   4.2 데이터 주제영역 인벤토리
5. 전체 시스템 아키텍처 구조 정의
6. 주요 시스템 대상 서버 식별
7. System Boundary / External Boundary
8. PDMG Reference Position
```

## 10.3 개념 아키텍처 필수 구조

최소 다음 공간을 표현한다.

```text
[User / Channel]
      ↓
[Access / Channel Integration]
      ↓
[Information Application]
      ├─ Marketing Platform
      ├─ BI Portal
      ├─ Data Governance
      └─ IT Service / Support
      ↓
[Data Platform]
      ├─ RDW
      └─ ADW
      ↓
[Core / Related / External Systems]
```

필요 시 Event / Integration / Operations를 Cross-cutting으로 표시한다.

## 10.4 애플리케이션 분류체계

기본 계층:

```text
Application Group (대구분)
   ↓
Application / Business (업무구분)
   ↓
Function
   ↓
Program / Service
```

분류체계는 CMDB/공식 코드표가 SSOT다. 현재 문서에서 확인된 상위 축은 다음과 같은 형태를 사용할 수 있으나 반드시 최신 근거를 재검증한다.

```text
MP  Marketing Platform
RD  RDW / Data Platform
AD  ADW / Data Platform
BI  BI Portal
DG  Data Governance
IM  IT Service / Business Support
```

### 애플리케이션 분류 인벤토리 권장 컬럼

```text
대구분 코드
대구분명
업무구분 코드
업무구분명
기능 코드
기능명
Application Owner
System Group
Data Subject Area
대표 Runtime Type
Evidence / Status
```

## 10.5 시스템 그룹 업무 구분

그림은 “제품 목록”이 아니라 책임을 보여 준다.

```text
System Group
  ├─ 무엇을 소유하는가
  ├─ 어떤 Application이 실행되는가
  ├─ 어떤 Data를 소유하는가
  ├─ 어떤 Interface를 제공하는가
  └─ 어떤 Runtime 유형을 처리하는가
```

### Application ↔ System Mapping Inventory

```text
Application Group
Application / Business
System Group
Logical System
Primary Role
Data Ownership
Interface Type
Environment
Physical Candidate
Status
Evidence
```

## 10.6 데이터 주제영역

현재 정의서에서 사용하는 상위 데이터 관리 관점은 순수 EDW Subject Area에 한정하지 않고 플랫폼·업무·관리 영역을 함께 묶을 수 있다.

예시 구조는 반드시 최신 자료 재검증 후 사용한다.

```text
차세대 정보계 데이터 주제영역
├─ 데이터플랫폼 RDW
├─ 데이터플랫폼 ADW
├─ BI 포탈
├─ 마케팅플랫폼
├─ 데이터 거버넌스
└─ IT서비스 및 업무지원
```

### 데이터 주제영역 인벤토리 권장 컬럼

```text
Subject Area ID
Subject Area Name
System Group
Owner
Source System
System of Record / SoR
RDW / ADW 위치
주요 Entity / Dataset
주요 Consumer
Interface / Ingest Type
Retention / Security Class
Evidence / Status
```

## 10.7 전체 시스템 아키텍처 구조

필수 구분:

```text
Channel / User
Access / Delivery
Application Service
Event / Integration
Data Platform
Governance
External / Core
Operations / Observability
```

Interface는 목적에 따라 분리 표현한다.

```text
Online  → API / MCA
Event   → Kafka
Change  → CDC
Bulk    → ETL
File    → FOS / MFT
JDBC    → Controlled Data Access
```

## 10.8 주요 시스템 대상 서버 식별

이 단계에서는 **서버 확정이 아니라 Physical 설계로 넘길 대상 식별**이 목적이다.

```text
System Group
   → Logical Role
      → Target Server Role Candidate
         → PHYSICAL에서 Host / Count / Spec 확정
```

금지:

```text
Big Picture에서 최신 Inventory 확인 없이 Hostname/대수를 확정
Application과 System을 무조건 1:1로 가정
PDMG를 RDW/ADW/Kafka/BI/Governance 전체 소유자로 표현
```

## 10.9 필수 이미지

```text
BP-01 NSIGHT Concept Architecture
BP-02 Application Domain Map
BP-03 Application Classification Tree
BP-04 Application Classification Inventory Summary
BP-05 System Group Responsibility Map
BP-06 Application ↔ System Mapping
BP-07 Data Subject Area Map
BP-08 Data Subject Area Inventory Summary
BP-09 Whole System Architecture
BP-10 Integration by Purpose Map
BP-11 Target Server Role Identification
BP-12 PDMG Reference Position
BP-13 Forbidden Boundary / Direct DB Risk
BP-14 BIG PICTURE → LOGICAL Handoff
```

---

# 11. PART III — LOGICAL

## 11.1 목적

Big Picture에서 정의한 시스템 공간을 **Zone·논리 시스템·레이어·데이터 소유·논리 인터페이스**로 구체화한다.

```text
BIG PICTURE
   ↓
Zone / Boundary
   ↓
Logical System
   ↓
Layer / Component Responsibility
   ↓
Logical Interface / Data Ownership
   ↓
Environment Scope
   ↓
PHYSICAL
```

## 11.2 목차

```text
III. LOGICAL
1. 논리기술 아키텍처 개요
2. Zone 구성 기준
3. 표준 요청 경로 / 허용·금지 연결
4. 논리 시스템 구성
5. Application Layered Architecture
6. PDMG Module / Application Logical Reference
7. Data Logical Architecture
   7.1 RDW / ADW 역할 분리
   7.2 Data Ownership
8. Interface Logical Architecture
9. Security Logical Boundary
10. 환경별 구축 범위
11. Logical Inventory / Mapping
```

## 11.3 Zone 기본 표현

현재 정의서의 6 Zone 모델을 최신 Baseline과 재검증해 사용한다.

```text
[대내 채널] [대고객 채널] [대외 채널]
       \        |        /
        \       |       /
          [채널 통합]
               ↓
          [서비스 제공]
               ↓ 필요 시
          [대내 통합]
```

중요:

```text
Zone ≠ Subnet / VLAN
Zone = 논리 책임 경계
```

## 11.4 표준 요청 경로

```text
Channel
  → Channel Integration
     → Service Provision
        → Internal Integration (필요 시)
```

허용·금지 연결을 같은 그림에서 명확히 구분한다.

## 11.5 논리 시스템과 Application Group

논리 시스템은 Big Picture의 Application Group/Business와 연결한다.

```text
MP
RD / AD
BI
DG
IM
```

`System Group = Application Group = Server`로 단순화하지 않는다.

## 11.6 Layered Architecture

기본 관점:

```text
Client
  ↓
Service / Application
  ↓
Interface
  ↓
Data
  ↓
Delivery / Deployment (Cross-cutting)
```

업무 솔루션도 이 레이어드 구조의 밖에 두지 않는다.

## 11.7 PDMG Logical Reference

PDMG는 여기서 **정보계 Application Service의 논리 Reference**로 확대한다.

```text
pdmg-ui
pdmg-jwt
pdmg-service
pdmg-fw
pdmg-om [Evidence 확인]
```

반드시 구분한다.

```text
Module Boundary
≠ Process Boundary
≠ JVM Boundary
≠ Spring ApplicationContext Boundary
```

## 11.8 Data Logical Architecture

```text
RDW
= 준실시간 / 운영성 데이터 중심

ADW
= 분석 / 집계 / 마트 중심
```

“DW” 단일 박스로 뭉개지 않는다.

## 11.9 Interface Logical Architecture

이 레벨에서는 **경로와 책임**을 정의한다.

```text
온라인 IF
파일 IF
데이터 IF
이벤트 IF
```

어떤 제품/프로토콜을 선택하는 상세 규칙은 MECHANISM으로 넘긴다.

## 11.10 환경 범위

운영 / 개발 / DR / 선도 / 검증 등 환경을 논리적으로 분리하고, 최신 Baseline에 따라 각 환경의 논리 시스템 집합을 표시한다.

## 11.11 필수 이미지

```text
L-01 Logical Architecture Journey
L-02 6 Zone Map
L-03 Standard Request Path
L-04 Allowed / Forbidden Connection
L-05 Logical System ↔ Application Group
L-06 Layered Architecture
L-07 PDMG Logical Module Reference
L-08 RDW vs ADW Logical Responsibility
L-09 Logical Interface Map
L-10 Security Logical Boundary
L-11 Environment Scope
L-12 Logical Inventory / Mapping
L-13 LOGICAL → PHYSICAL Handoff
```

---

# 12. PART IV — PHYSICAL

## 12.1 목적

Logical Node를 **센터·환경·Host·HW/SW·DB·Network·Capacity·HA/DR·Inventory**로 변환한다.

```text
Logical Node
   ↓
Center / Environment
   ↓
Host / VM / Appliance
   ↓
OS / WEB / WAS / JVM / DB / SW
   ↓
Capacity / HA / DR / Backup
   ↓
Inventory / CMDB / DNS / Monitoring
```

## 12.2 목차

```text
IV. PHYSICAL
1. 논리 → 물리 매핑 원칙
2. 센터 / 환경 Topology
3. 주요 시스템 대상 서버 확정
4. WEB / WAS / JVM / WAR 배치
5. Database Physical Architecture
   5.1 RDW / ADW Physical
   5.2 RAC / Appliance
   5.3 CDC / Downstream / ETL Physical
6. Network / LB / Gateway / Integration Physical
7. 시스템 표준
   7.1 Hostname
   7.2 File System
   7.3 Account
   7.4 Port
8. Software Inventory
9. Capacity / Sizing
10. HA / DR
11. Backup / Restore
12. Physical Server Inventory
```

## 12.3 논리 → 물리 매핑 필수 필드

```text
Logical Node
Environment
Center
Hostname
Server Role
Count / Type
OS / Platform
System Group
Installed SW / Version
Capacity Source
HA Pair / DR Pair
Evidence / Status
```

논리 노드에 없는 물리 서버를 임의 신설하지 않는다. 예외는 ADR로 관리한다.

## 12.4 센터 / 환경

현재 정의서의 센터·순번 정책은 최신 인벤토리와 재검증 후 사용한다.

예:

```text
주센터 / DR센터
운영 / 개발 / 선도 / 검증 / DR
```

Hostname 순번·센터코드·환경코드는 시스템 표준과 일치해야 한다.

## 12.5 WEB / WAS / JVM / WAR

반드시 계층을 구분한다.

```text
GSLB / L4
   ↓
Apache WEB
   ↓
Tomcat Process
   ↓
JVM
   ↓
WAR / Application Group
   ↓
Hikari / DB
```

금지:

```text
JVM과 Tomcat을 서로 다른 다중 프로세스로 잘못 표현
Build Module을 곧바로 Physical Server로 해석
WAR 수량과 서버 대수를 동일 개념으로 사용
```

## 12.6 Database Physical

```text
RDW Physical
ADW Physical
RAC / Appliance
CDC Relay / Downstream
ETL Server
Storage / Backup
```

DB 역할과 유입 경로를 동시에 보여 주되, CDC와 ETL을 하나의 화살표로 뭉개지 않는다.

## 12.7 Capacity

모든 수치는 근거 문서/인벤토리를 인용한다.

```text
User / TPS Assumption
   ↓
Concurrency
   ↓
WEB/WAS Thread
   ↓
Worker / Queue
   ↓
DB Pool
   ↓
CPU / Memory / JVM
   ↓
Scale-out Count
```

## 12.8 HA / DR / Backup

```text
HA
= 주센터 내 장애 격리 / Active 구조

DR
= 센터 간 복구 / 전환

Backup
= 데이터·설정·산출물 복구 수단
```

세 개를 같은 개념으로 쓰지 않는다.

## 12.9 Physical Inventory

물리 확정 행은 최소 다음과 일치해야 한다.

```text
CMDB
DNS
IaaS / VM Inventory
Monitoring
Backup
Deployment Inventory
```

## 12.10 필수 이미지

```text
P-01 Logical → Physical Mapping
P-02 Center / Environment Topology
P-03 Target Server Role Map
P-04 GSLB/L4 → WEB → WAS → JVM → WAR
P-05 System Group Deployment Map
P-06 RDW / ADW Physical Topology
P-07 CDC / ETL / Downstream Physical
P-08 Hostname / FS / Account / Port Standard
P-09 SW Inventory Map
P-10 Capacity / Resource Chain
P-11 HA Architecture
P-12 DR Architecture
P-13 Backup / Restore
P-14 Physical Server Inventory
P-15 PHYSICAL → MECHANISM Handoff
```

---

# 13. PART V — MECHANISM

## 13.1 목적

논리·물리 구조가 **일관되고 안전하게 동작하도록 만드는 표준 동작 원리**를 정의한다.

```text
Physical Platform
   ↓
Standard Entry / Routing
   ↓
Transaction Skeleton
   ↓
Message / Context / GUID
   ↓
Interface / Security / Error / Logging
   ↓
Batch / File / DevOps Mechanism
   ↓
RUNTIME
```

## 13.2 목차

```text
V. MECHANISM
1. Mechanism Architecture 개요
2. Framework vs Business Responsibility
3. Application Package / Naming / ServiceId
4. 온라인 거래 8단계
5. Standard Message
6. Context / GUID / Traceability
7. Interface Mechanism
   7.1 Online/API/MCA
   7.2 Event
   7.3 CDC
   7.4 ETL
   7.5 File/FOS/MFT
8. Transaction / Timeout / Retry / Idempotency 정책
9. Security Mechanism
   9.1 SSO
   9.2 JWT / Token
   9.3 Session / Authorization
10. Exception / Error / Logging
11. File Upload / Download / RD / Inbound
12. Batch Framework / Control-M
13. Config / Secret / DevOps Mechanism
14. Traceability / Closed-Loop Rule
```

## 13.3 Framework vs Business

```text
Framework
  ServiceId Routing
  Transaction Skeleton
  System Pre/Post
  Timeout / TX Boundary
  Common Error / Logging
  Context

Business
  Domain Rule
  DTO
  Business Pre/Post
  Service
  DAO / Mapper
```

Framework가 제공하는 기능을 업무 코드에서 재구현하지 않는다.

## 13.4 Application Package / ServiceId

분류축은 Source와 공식 코드표를 함께 검증한다.

```text
Business Classification
  → Package
  → Program
  → ServiceId
  → Handler / Controller
  → Facade / Service
  → DAO / Mapper / SQL
```

PDMG에서 실제 확인된 ServiceId 형식/Handler Registry는 Source Snapshot을 근거로 별도 AS-IS Evidence로 표시한다.

## 13.5 온라인 거래 8단계

기본 Mechanism은 다음 구조를 보존한다.

```text
[1] 시스템 선처리
[2] 공통 선처리
[3] 업무 선처리
[4] Controller
[5] Business Service
[6] 업무 후처리
[7] 공통 후처리
[8] 시스템 후처리
```

필수/선택 여부와 실제 PDMG 구현의 연결 상태를 구분한다.

## 13.6 Standard Message

```text
Common Header
  + Business DTO
  + Result / Error
```

FLAT / JSON은 표현형식이며 **논리 계약 자체와 구분**한다.

Package UI 등 예외는 예외 이유와 책임을 같이 표시한다.

## 13.7 GUID / Context

필요 시 현재 표준의 R1~R5 규칙을 사용한다.

```text
채널 최초 생성
  → Node 경유
  → 진행번호 증가
  → 원거래글로벌ID 유지
  → 응답 복귀
  → E2E Log Trace
```

PDMG Source의 자동 증가 구현 여부는 별도 검증한다.

## 13.8 Interface Mechanism

인터페이스 선택은 업무 목적을 기준으로 한다.

```text
즉시 응답 필요      → Sync API / Transaction
비동기 후처리       → Message / Event
다수 Consumer       → Pub/Sub
DB 변경 저지연      → CDC
대량 적재/변환      → ETL / Batch
파일 단위 교환      → FOS / MFT
```

### Interface Contract must

```text
Interface ID
Source / Target
Purpose
Protocol / Endpoint
SYNC / ASYNC
Schema
Header / GUID
Error
Timeout
Retry
Idempotency
Security
SLA
Owner / RACI
Version
Runtime Type
```

## 13.9 Timeout / Retry / Idempotency

정책과 실제 런타임 실행을 구분한다.

```text
MECHANISM = 어떤 규칙을 적용할 것인가
RUNTIME   = 실제 어느 Thread / 시점에서 어떻게 실행되는가
```

기본 Timeout Budget은 최신 정책을 재검증한다.

```text
DB Query < Transaction < Integration < Client
```

Retry 금지/주의:

```text
금융 DML
중복위험 거래
Validation Error
권한 Error
Business Reject
```

## 13.10 Security Mechanism

```text
SSO
JWT Access / Refresh
Key / JWKS
Authorization
Session
Internal HMAC / Allowlist (Evidence 있을 때)
Secret Boundary
```

Private Key/Token/Secret 원문을 이미지에 노출하지 않는다.

## 13.11 Error / Logging

```text
System Validation
Business Validation
DB / External Error
Timeout / Overload
Security Error
        ↓
Standard Error Mapping
        ↓
GUID / ServiceId / Trace Evidence
```

업무 코드가 오류 JSON을 임의 조립하지 않는다.

## 13.12 Batch / File

```text
Batch
Control-M → Agent → Shell → Batch FW → Job/Step → Repository / DB

File
ready → processing → done / error
```

완료신호 전에 파일을 소비하지 않는다.

## 13.13 Traceability / Closed Loop

```text
Requirement
 → Principle
 → ADR
 → Logical / Physical
 → ServiceId / Component / SQL
 → Test
 → Deploy
 → Runtime Evidence
 → Drift / GAP
 → Baseline Update
```

## 13.14 필수 이미지

```text
M-01 Mechanism Big Picture
M-02 Framework vs Business Responsibility
M-03 Classification → Package → ServiceId
M-04 Transaction 8 Steps
M-05 Standard Message
M-06 GUID / Context Lifecycle
M-07 Interface Decision Tree
M-08 Interface Contract
M-09 Timeout / Retry / Idempotency Policy
M-10 SSO / JWT / Session
M-11 Error / Logging Mechanism
M-12 File / RD / Inbound
M-13 Batch Framework
M-14 Config / Secret / DevOps Boundary
M-15 ServiceId Traceability Chain
M-16 MECHANISM → RUNTIME Handoff
```

---

# 14. PART VI — RUNTIME

## 14.1 목적

Mechanism을 **시간순 실행·Thread·Transaction·Failure·Recovery·Monitoring Evidence**로 증명한다.

```text
Business Event / Request
        ↓
Runtime Type
        ↓
Sequence
        ↓
Thread / TX / Resource Pool
        ↓
Success / Error / Timeout / Retry
        ↓
Metric / Log / Trace / Evidence
```

## 14.2 목차

```text
VI. RUNTIME
1. Runtime Architecture 개요
2. Runtime 대유형 6 / 소유형 12
3. Online Runtime / PDMG TCF Flow
4. Request Thread vs Worker Thread
5. Transaction / Timeout / DB Runtime
6. Channel Runtime (#1~#3)
7. Integration Runtime (#4~#5)
8. Marketing Event Runtime (#6~#7)
9. Data Runtime (#8~#10)
10. File Runtime (#11)
11. Batch Runtime (#12)
12. Normal / Error / Timeout / Overload
13. Security Runtime
14. Observability / OM
15. HA / DR Runtime / Drill
16. NFR Validation / Runtime Evidence
17. Drift / GAP / ADR / Baseline Release
```

## 14.3 Runtime Type 체계

현재 정의서의 6대 유형 / 12개 소유형을 최신 Baseline과 재검증한다.

```text
채널
연계
마케팅 이벤트
데이터 분석/제공
파일
배치
```

각 Runtime Type은 최소 다음을 가진다.

```text
Type ID
Purpose
Actor
Entry
Interface
ServiceId / Job / Event
Sequence
Owner
SLO
Retry / Compensation
Monitoring
Evidence
```

## 14.4 PDMG Online Runtime

현재 Source Snapshot에서 확인된 경우 아래 구조를 AS-IS로 표현한다.

```text
HTTP Request
  ↓
DefaultFilter
  ↓
Spring SecurityFilterChain
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
TransactionHandler
  ↓
Facade
  ↓
Service
  ↓
DAO / Mapper
  ↓
DB
  ↓
Response / Error
```

실제 Source가 달라졌으면 최신 Source를 따른다.

## 14.5 Request Thread vs Worker Thread

Timeout 활성 시 다음을 분리한다.

```text
Request Thread
  └─ Future.get(timeout)
          ↓
Worker Thread
  └─ Business Execution / DB Transaction
```

다음 네 시점을 같은 것으로 쓰지 않는다.

```text
HTTP Timeout
Worker 종료
JDBC Statement 취소
DB Transaction Rollback
```

## 14.6 Transaction / Timeout Runtime

현재 Source에서 확인된 경우:

```text
Worker Context Install
  ↓
TransactionTemplate BEGIN
  ↓
Dispatcher
  ↓
Handler
  ↓
Facade @Transactional(REQUIRED)
  ↓
Service
  ↓
DAO / Mapper / SQL
  ↓
Deadline Check
  ↓
COMMIT / ROLLBACK
  ↓
Context Clear
```

AS-IS 숫자는 Snapshot Badge를 붙인다.

## 14.7 Event / Data / File / Batch Runtime

반드시 서로 다른 시퀀스로 표현한다.

```text
Event
Producer → Kafka → Consumer → Processing / Offering

CDC
Source Commit → Capture → Relay/Trail → Apply → RDW

ETL
Extract → Stage → Transform → Validate → Load → Reconcile

File
Transfer → Completion Signal → Validate → Process → Reconcile

Batch
Control-M → Agent → Job → Step → Commit → Exit Code → Monitor
```

## 14.8 Failure Runtime

최소 다음 케이스를 별도 카드로 만든다.

```text
Business Error
System Error
Handler / Route Not Found
Timeout
Worker Queue Overload
DB Pool Exhaustion
Slow SQL
JWT / Authorization Error
External Interface Error
CDC Delay
Batch Failure
DR Failover
```

## 14.9 Observability / OM

관측은 서버 CPU만 보는 것이 아니다.

```text
GUID
 + ServiceId
 + Runtime Type
 + WAR / JVM / Host
 + Thread / Queue
 + Hikari / DB Session
 + SQL / External IF
 + Error / Latency
```

이 연결이 가능해야 한다.

`pdmg-om`의 실제 Dashboard 기능은 Source Evidence를 확보한 범위만 AS-IS로 표시한다.

## 14.10 HA / DR Runtime

Physical DR 구조를 실제 시간순 절차로 바꾼다.

```text
Detect
 → Isolate
 → Traffic Switch
 → App/DB/Data Integrity Check
 → Recover
 → Reconcile
 → Approve
 → Evidence
```

“DR 서버가 있다”는 것만으로 DR 완료로 보지 않는다.

## 14.11 Runtime Evidence / Validation

각 중요한 Runtime은 최소 다음 세 Evidence를 요구한다.

```text
정상
지연
장애
```

가능한 Evidence:

```text
Log
Metric
Trace
APM
Transaction Log
GUID Search
SQL Evidence
Deployment Evidence
Drill Evidence
```

## 14.12 Closed Loop

```text
Document
   ↓
Model
   ↓
Source / Config
   ↓
Test
   ↓
Deploy
   ↓
Runtime Evidence
   ↓
Drift Detection
   ↓
GAP / ADR
   ↓
New Baseline
   └──────────────────↺
```

## 14.13 필수 이미지

```text
R-01 Runtime Big Picture
R-02 Runtime Type 6 / 12 Map
R-03 PDMG Online Runtime Journey
R-04 Servlet / Security / MVC / TCF Boundary
R-05 Request vs Worker Thread
R-06 Transaction Boundary
R-07 Timeout Two-Thread Timeline
R-08 Channel Runtime #1~#3
R-09 Integration Runtime #4~#5
R-10 Event Runtime #6~#7
R-11 CDC / ETL Runtime #8~#9
R-12 Analysis / BI Runtime #10
R-13 File Runtime #11
R-14 Batch Runtime #12
R-15 Normal / Error / Timeout / Overload
R-16 Security Runtime
R-17 Observability / OM
R-18 HA / DR Runtime
R-19 Validation / Runtime Evidence
R-20 Architecture Closed Loop
R-21 Current GAP / ADR / Priority
```

---

# 15. Cross-Cutting Architecture 규칙

다음은 특정 장에만 가두지 않는다.

## 15.1 Security

```text
VISION      : Security NFR
BIG PICTURE : Trust Boundary
LOGICAL     : AuthN/AuthZ Logical Boundary
PHYSICAL    : Network/Secret/Key Placement
MECHANISM   : SSO/JWT/Encryption/Masking
RUNTIME     : Auth Failure / Audit / Evidence
```

## 15.2 Interface

```text
BIG PICTURE : 시스템간 연결 목적
LOGICAL     : 허용 경로 / 경계
PHYSICAL    : Integration Node 배치
MECHANISM   : 매체 / 계약 / Retry / Idempotency
RUNTIME     : Sync/Async Sequence / Failure
```

## 15.3 Observability

```text
VISION      : Observability NFR
BIG PICTURE : Cross-cutting 운영 경계
LOGICAL     : Trace Point
PHYSICAL    : Agent / Log / Monitor 배치
MECHANISM   : GUID / ServiceId / Log 표준
RUNTIME     : Metric / Trace / Alert / Evidence
```

## 15.4 HA / DR

```text
VISION      : Availability 목표
LOGICAL     : Fault Domain
PHYSICAL    : Active / DR Topology
MECHANISM   : Timeout / Retry / Consistency Policy
RUNTIME     : 장애전환 / 복구 / Drill
```

---

# 16. 이미지 생성 전 Text Architecture 선행 규칙

이미지부터 만들지 않는다.

```text
L0 Context
  ↓
L1 Domain / Responsibility
  ↓
L2 Logical / Physical Node
  ↓
L3 Mechanism / Contract
  ↓
L4 Runtime / Failure / Security
  ↓
L5 Evidence / GAP / ADR
```

절차:

```text
① Source/Evidence 읽기
② FACT / AS-IS / TO-BE / GAP 분리
③ Text Architecture 작성
④ Inventory / Mapping 검증
⑤ Figure Plan 작성
⑥ 인포그래픽 렌더링
⑦ 한글/고유명사 검수
⑧ Quality Gate
```

---

# 17. 선과 박스의 의미

```text
실선 화살표     : 확인된 동기 호출 / 물리 연결
점선 화살표     : 비동기 / 간접 / 후보 관계
굵은 실선       : 주요 표준 경로
빨간 점선       : 금지 / Failure / Risk
회색 점선       : UNKNOWN / TBD
양방향 실선     : 승인된 양방향 관계
```

박스 타입:

```text
Rounded Rectangle : Application / Service
Cylinder          : DB / Data Store
Hexagon           : Gateway / Integration / Mechanism
Shield            : Security
Gear              : Runtime / Framework
Cloud              : External / Channel
Eye / Chart        : Observability
Pin / Flag         : Milestone / Decision / Gate
```

---

# 18. 한국어 Text 정확성 규칙

1. 긴 문장은 카드 안에 넣지 않는다.
2. 한 카드 설명은 1~3줄로 제한한다.
3. 고유명사는 원문을 유지한다.
4. 영문 클래스/설정명은 번역하지 않는다.
5. 긴 Inventory는 이미지에서 5~8개 대표 행만 보여주고 상세표는 보조 카드로 분리한다.
6. 다음 단어는 생성 후 오탈자를 우선 검수한다.

```text
NSIGHT
PDMG
TCF
ServiceId
RDW
ADW
MCA
MCI
APIM
Kafka
CDC
ETL
FOS
GUID
DefaultFilter
OnlineTransactionController
TcfFacade
OnlineTimeoutExecutor
TransactionDispatcher
TransactionHandler
Facade
Service
DAO
Mapper
JWT
SSO
OM
Control-M
```

---

# 19. 상태 카드

각 이미지 하단에는 필요에 따라 다음을 둔다.

```text
CONFIRMED
TO-BE / DECISION
AS-IS Evidence
GAP / OPEN
RISK
ADR
Next Route
```

예:

```text
[CONFIRMED]
6 Zone / RDW-ADW 분리

[GAP]
pdmg-om Current Source 미확인

[OPEN]
CDC SLA 측정 구간

[NEXT]
PHYSICAL → MECHANISM
```

---

# 20. 장별 작성 템플릿

각 장을 만들 때 먼저 다음 템플릿을 채운다.

```text
[Level]
VISION | BIG PICTURE | LOGICAL | PHYSICAL | MECHANISM | RUNTIME

[작성 대상]
장/절 제목

[목적]
이 장이 답할 핵심 질문 1~3개

[입력 Evidence]
공식 자료:
정의서:
Inventory:
Source/Config:
Runtime Evidence:
ADR:

[FACT]
- ...

[AS-IS]
- ...

[TO-BE / DECISION]
- ...

[GAP / OPEN]
- ...

[Main Text Architecture]
...

[Inventory / Mapping]
...

[필수 Figure]
01 ...
02 ...

[Forbidden]
- ...

[Cross Reference]
상위:
하위:

[Next Route]
...
```

---

# 21. 최종 실행용 통합 프롬프트

아래 블록만 복사하여 실제 이미지화 작업에 사용할 수 있다.

```text
너는 NH 농협 상호금융 NSIGHT의 Chief Enterprise/Application/Data/Technical/Interface/Security/Framework/Operations Architect이자 Architecture Visual Designer다.

첨부된 NSIGHT/PDMG Architecture Markdown, 정의서, Inventory, Source 분석문서, Config, Runtime Evidence를 모두 읽고 다음 TYPE2 구조에 따라 해당 장을 세로형 Architecture Infographic으로 작성하라.

[TYPE2 Architecture Route]
VISION
 → BIG PICTURE
 → LOGICAL
 → PHYSICAL
 → MECHANISM
 → RUNTIME
 → Evidence / GAP / ADR / Baseline Update

[가장 중요한 원칙]
1. NSIGHT Target과 PDMG AS-IS/Reference를 혼합하지 않는다.
2. 해당 내용이 6개 레벨 중 어디에 속하는지 먼저 판정한다.
3. 구조를 먼저 만들고 클래스/설정/수치를 뒤에 배치한다.
4. Current Source/Config/Runtime Evidence는 AS-IS 증거로 우선한다.
5. TO-BE/DECISION은 승인된 Architecture Baseline/ADR을 따른다.
6. Source와 Target이 다르면 Source=AS-IS, Target=TO-BE, 차이=GAP으로 표시한다.
7. [FACT]/[CONFIRMED]/[AS-IS]/[TO-BE]/[DECISION]/[PROPOSED]/[GAP]/[CONFLICT]/[RISK]/[OPEN]/[UNKNOWN]을 구분한다.
8. 확인되지 않은 숫자·제품·클래스·서버·Timeout·Pool·RTO/RPO를 만들지 않는다.
9. 애플리케이션 분류, 시스템 그룹, 데이터 주제영역, 서버, Interface, Runtime Type은 그림과 함께 Inventory/Mapping을 만든다.
10. Interface/Security/Observability/HA-DR은 Cross-cutting 관점으로 상하위 장을 연결한다.

[시각 스타일]
- Travel Itinerary / Premium Architecture Guide
- Cream/Ivory 배경
- Teal/Mint/Sky/Deep Navy 중심
- Rounded Card / Route Line / Milestone / Map Pin / Compass
- 기술 장식보다 책임·경계·흐름 우선
- A4 세로형

[공통 레이아웃]
상단: Level + Chapter + Scope Badge
중상단: 한눈에 보는 Route
중단: Main Architecture View
중하단: Detail Card / Inventory / Mapping
하단: Forbidden / Risk / FACT / GAP / ADR / Next Route

[작업 절차]
① 근거자료를 읽는다.
② FACT/AS-IS/TO-BE/GAP을 분리한다.
③ L0~L5 Text Architecture를 먼저 작성한다.
④ Inventory/Mapping 정합성을 확인한다.
⑤ Figure Plan을 만든다.
⑥ 이미지를 생성한다.
⑦ 한글/고유명사/화살표 의미를 검수한다.
⑧ Quality Gate를 통과시킨다.

[현재 작성 Level]
<<VISION | BIG PICTURE | LOGICAL | PHYSICAL | MECHANISM | RUNTIME>>

[현재 작성 대상]
<<장/절 제목>>

[반드시 반영할 FACT]
<<FACT 목록>>

[반드시 표시할 GAP/OPEN]
<<GAP/OPEN 목록>>

[필수 Inventory / Mapping]
<<Inventory 이름>>

[다음 Route]
<<다음 장>>
```

---

# 22. TYPE2 최종 품질 Gate

이미지/장표마다 다음을 확인한다.

```text
□ 현재 장이 VISION/BIG PICTURE/LOGICAL/PHYSICAL/MECHANISM/RUNTIME 중 어디인지 명확한가
□ 상위 레벨의 결정을 건너뛰고 하위 구현부터 시작하지 않았는가
□ NSIGHT TO-BE와 PDMG AS-IS가 분리되어 있는가
□ FACT와 분석/추론이 구분되어 있는가
□ Application / System / Data / Server를 같은 개념으로 혼용하지 않았는가
□ Big Picture에 책임·경계가 있는가
□ Logical에 Zone·Logical Node·Layer가 있는가
□ Physical에 Host/Center/SW/Capacity/Inventory 근거가 있는가
□ Mechanism에 계약·표준·추적·예외 원리가 있는가
□ Runtime에 시간순 시퀀스와 실패/복구가 있는가
□ Interface를 모든 레벨에서 REST 하나로 단순화하지 않았는가
□ RDW/ADW를 “DW” 하나로 뭉개지 않았는가
□ Build Module을 Physical Server로 오해하지 않았는가
□ Timeout/Thread/JDBC/TX 종료 시점을 같은 것으로 표현하지 않았는가
□ Security/Observability가 뒤늦은 부가 기능이 아니라 Cross-cutting으로 표현되었는가
□ Inventory/Mapping이 필요한 장에 실제 표/카드가 존재하는가
□ UNKNOWN/GAP/OPEN을 숨기지 않았는가
□ Source에 없는 숫자·제품·클래스를 만들어내지 않았는가
□ 한글/영문 고유명사 오탈자가 없는가
□ 다음 Route가 표시되어 전체 정의서가 연결되는가
```

판정:

```text
PASS
  = 구조·근거·Inventory·Cross Reference·Evidence가 모두 존재

CONDITIONAL PASS
  = 구조는 완료되었으나 OPEN/GAP이 명확히 표시됨

FAIL
  = 하위 구현부터 시작 / 근거 없는 수치 생성 / AS-IS와 TO-BE 혼합
    / Inventory 누락 / Runtime 없는 표준 / Physical 없는 논리 노드
```

---

# 23. TYPE2 전체 완성 모습

최종 결과는 다음과 같은 한 방향의 Architecture Story가 되어야 한다.

```text
┌───────────────────────────────┐
│ VISION                        │
│ 왜 개편하며 무엇을 목표하는가 │
└───────────────┬───────────────┘
                ↓
┌───────────────────────────────┐
│ BIG PICTURE                   │
│ 업무·앱·시스템·데이터 공간    │
└───────────────┬───────────────┘
                ↓
┌───────────────────────────────┐
│ LOGICAL                       │
│ Zone·Logical System·Layer     │
└───────────────┬───────────────┘
                ↓
┌───────────────────────────────┐
│ PHYSICAL                      │
│ Center·Host·SW·DB·Capacity    │
└───────────────┬───────────────┘
                ↓
┌───────────────────────────────┐
│ MECHANISM                     │
│ 8단계·전문·GUID·IF·SSO·Error │
└───────────────┬───────────────┘
                ↓
┌───────────────────────────────┐
│ RUNTIME                       │
│ Sequence·Thread·TX·Failure    │
│ Monitoring·DR·Evidence        │
└───────────────┬───────────────┘
                ↓
┌───────────────────────────────┐
│ CLOSED LOOP                   │
│ Evidence → GAP → ADR → Baseline│
└───────────────────────────────┘
```

최종 목표는 **“예쁜 장표”**가 아니다.

```text
NSIGHT Architecture
= 설명 가능
+ 분류 가능
+ 매핑 가능
+ 구현 가능
+ 운영 가능
+ 검증 가능
+ 추적 가능
+ 변경관리 가능
```

---

# 24. 기준 문서 활용 메모

TYPE2 작성 시 기존 자료는 다음처럼 사용한다.

```text
VISION / BIG PICTURE
  ← 01_아키텍처정의_정의서
  ← NSIGHT_PDMG_아키텍처_정의서_00_목차_및_작성기준
  ← NSIGHT_PDMG_아키텍처_정의서_II_BigPicture_SystemBoundary

LOGICAL
  ← 02_논리기술아키텍처_정의서
  ← 06_인터페이스아키텍처_정의서 (경계/경로)
  ← 10_업무솔루션아키텍처_정의서
  ← PDMG Module / Application Architecture

PHYSICAL
  ← 03_물리인프라아키텍처_정의서
  ← 04_데이터베이스아키텍처_정의서
  ← 05_시스템표준_정의서
  ← 11_모니터링가용성DR백업_정의서

MECHANISM
  ← 08_아키텍처표준화_정의서
  ← 09_아키텍처구성요소_정의서
  ← 06_인터페이스아키텍처_정의서 (표준/계약)
  ← Naming / ServiceId / Traceability

RUNTIME
  ← 07_런타임아키텍처_정의서
  ← PDMG Online Runtime / TCF Flow
  ← Transaction / Timeout / Thread / DB Architecture
  ← DevOps / OM / Observability
  ← 11_모니터링가용성DR백업_정의서 (Drill/Evidence)
```

이 재배치표를 기준으로 **기존 자료를 폐기하지 않고 TYPE2에서 상위 구조만 새로 정렬한다.**
