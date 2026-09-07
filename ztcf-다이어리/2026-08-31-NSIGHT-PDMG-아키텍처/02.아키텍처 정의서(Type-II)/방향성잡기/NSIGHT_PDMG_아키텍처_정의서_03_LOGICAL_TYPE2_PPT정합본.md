# NSIGHT / PDMG 아키텍처 정의서 — 03. LOGICAL

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 문서 ID: NSIGHT-ARCH-TYPE2-LOGICAL-03  
> Architecture Level: **LOGICAL**  
> PPT 공식 범위: **2. 논리 기술 아키텍처** + **6장 Interface Logical Boundary Cross Reference**  
> PPT 공식 절: **2.1 전사 IT Zone 기반 구성 기준 / 2.2 시스템 노드 정의 및 식별 / 2.3 기술 컴포넌트 정의 / 2.4 논리 기술 아키텍처 정의**  
> 문서 상태: Draft / Evidence-First / PPT-Aligned  
> 작성일: 2026-08-31  
> 선행: `02_BIG_PICTURE`  
> 후속: `04_PHYSICAL`  
> Evidence Level: PPT Target + Existing Definition Baseline + PDMG AS-IS Reference

---

# 0. Evidence Register

| ID | 근거 | 본 장 사용 목적 | 상태 |
|---|---|---|---|
| EV-LG-01 | `NSIGHT_PDMG_아키텍처_정의서_00_목차_및_작성기준_TYPE2_PPT정합본` | LOGICAL의 공식 범위·작성 규칙 | `[PPT-TOC]` / `[WORKING BASELINE]` |
| EV-LG-02 | `02_논리기술아키텍처_정의서.md` | 6 Zone·환경별 구축 범위·레이어드·허용/금지 기준 | `[WORKING BASELINE]` |
| EV-LG-03 | `02_BIG_PICTURE_TYPE2_PPT정합본` | 5대 Domain·System Group·Logical Server Candidate | `[CURRENT BASELINE DRAFT]` |
| EV-LG-04 | `06_인터페이스아키텍처_정의서.md` | Zone 경계와 Interface Logical Boundary 교차검증 | `[WORKING BASELINE]` |
| EV-LG-05 | PDMG Module/Application Architecture 자료 | Application/Framework Runtime Reference 위치 | `[AS-IS REFERENCE]` |

---

# 1. 핵심 결론

LOGICAL 아키텍처는 **물리 서버를 정하기 전에 “어느 논리 영역에 어떤 시스템·노드·컴포넌트를 둘 것인가”를 고정하는 단계**다.

```text
VISION
  ↓
BIG PICTURE
  ├─ Domain
  ├─ Application
  ├─ System Group
  └─ Data Subject
  ↓
LOGICAL
  ├─ Zone
  ├─ Logical System
  ├─ Logical Node
  ├─ Technology Component
  ├─ Layer
  └─ Allowed / Forbidden Connection
  ↓
PHYSICAL
  ├─ Center
  ├─ Host
  ├─ HW / SW
  └─ DB / Capacity
```

[WORKING BASELINE] 논리구조의 핵심 기준은 다음과 같다.

```text
6 Zone
+
표준 요청 경로
+
5대 논리 시스템
+
환경별 구축 범위 5 / 5 / 3 / 3
+
Client → Service → I/F → Data Layer
+
Zone·System·Data Boundary
```

한 문장으로 정의하면:

> **LOGICAL은 “어느 Zone·어느 논리 시스템·어느 레이어에서 책임질 것인가”를 고정하고, Hostname·대수·센터·제품 상세는 PHYSICAL로 위임한다.**

---

# 2. 목적 / 범위 / 전제

## 2.1 목적

본 장은 다음 질문에 답한다.

1. 전사 IT Zone을 어떤 책임 경계로 볼 것인가?
2. NSIGHT 5대 Domain/System을 어느 Zone에 둘 것인가?
3. 운영·개발·DR·선도 환경별로 어떤 논리 시스템을 구축할 것인가?
4. WEB/WAS/AP/DB/Event/ETL 등의 논리 노드는 어떤 책임을 가지는가?
5. 기술 컴포넌트는 어느 논리 노드에 배치되는가?
6. 어떤 연결을 허용하고 어떤 연결을 금지하는가?
7. RDW/ADW와 Application의 논리 소유 경계는 무엇인가?
8. Interface의 “논리 경계”와 Mechanism의 “매체/계약”을 어떻게 분리하는가?
9. PDMG는 LOGICAL 전체 중 어느 부분을 실제 구현 Reference로 증명하는가?

## 2.2 포함

```text
IT Zone
Logical System
Logical Node
Technology Component
Environment Scope
Layered Architecture
Application / Data Ownership
Allowed / Forbidden Connection
Interface Logical Boundary
PDMG Logical Reference Position
```

## 2.3 제외

```text
센터 / Hostname / 대수 / HW       → 04 PHYSICAL
RAC / OGG 물리 구성               → 04 PHYSICAL
Interface 표준매체 / 계약          → 05 MECHANISM
전문 / GUID / Framework            → 05 MECHANISM
실행 Sequence / Thread / Timeout    → 06 RUNTIME
```

---

# 3. PPT 공식 구조 / 장표 Trace

[PPT-TOC]

```text
2. 논리 기술 아키텍처
   2.1 전사 IT Zone 기반 구성 기준
   2.2 시스템 노드 정의 및 식별
   2.3 기술 컴포넌트 정의
   2.4 논리 기술 아키텍처 정의
```

## 3.1 Numbering Drift

[PPT-TOC]

```text
2.2 시스템 노드 정의 및 식별
```

[PPT-BODY]

```text
2.2 시스템 영역 및 구성요소 정의
```

따라서 본 정의서에서는 두 명칭을 병기한다.

```text
2.2 시스템 노드 정의 및 식별
    (PPT 본문: 시스템 영역 및 구성요소 정의)
    [NUMBERING-DRIFT]
```

둘 중 하나를 임의 삭제하지 않는다.

## 3.2 PPT Content 상태

| 절 | 상태 | 작성 원칙 |
|---|---|---|
| 2.1 전사 IT Zone 기반 구성 기준 | `[PPT-TOC]` + 보강 | 기존 논리 정의서의 6 Zone 기준 사용 |
| 2.2 시스템 노드 정의 및 식별 | `[PPT-BODY]` | 운영/DR/개발/선도 환경 구조 유지 |
| 2.3 기술 컴포넌트 정의 | `[PPT-BODY]` | 공통·도메인별 컴포넌트 순서 보존 |
| 2.4 논리 기술 아키텍처 정의 | `[PPT-BODY]` | 마케팅·데이터·BI·거버넌스 중심 |

---

# 4. Main Text Architecture

```text
┌──────────────────────────── Channel Zones ─────────────────────────────┐
│                                                                       │
│ [대내 채널]        [대고객 채널]        [대외 채널]                   │
│     │                   │                   │                         │
└─────┼───────────────────┼───────────────────┼─────────────────────────┘
      └───────────────────┼───────────────────┘
                          ▼
                 ┌─────────────────┐
                 │   채널 통합     │
                 │ MCA / MCI 등    │
                 └────────┬────────┘
                          │ 계약된 요청
                          ▼
┌──────────────────────── 서비스 제공 Zone ─────────────────────────────┐
│                                                                       │
│ [마케팅플랫폼] [데이터플랫폼] [BI 포탈] [데이터거버넌스] [IT지원]   │
│      │              │           │            │             │         │
│      └──────────────┬───────────┴────────────┴─────────────┘         │
│                     │                                                │
│        Client → Service → I/F → Data                                 │
│                     │                                                │
└─────────────────────┼────────────────────────────────────────────────┘
                      │ 필요 시
                      ▼
                ┌───────────────┐
                │   대내 통합   │
                │ API/EAI/FOS…  │
                └───────┬───────┘
                        │
                        ▼
             [내부 / 계정계 / 유관 시스템]
```

핵심:

```text
Zone = 논리 책임 경계
Zone ≠ VLAN/Subnet 이름
```

```text
Logical Node = 역할 단위
Physical Host = 구현 단위
```

---

# 5. 2.1 전사 IT Zone 기반 구성 기준

## 5.1 6 Zone 정의

| # | Zone | 논리 책임 | 대표 대상 |
|---:|---|---|---|
| 1 | 대내 채널 | 내부 사용자 업무 접근 | 통합업무·정보포털 등 |
| 2 | 대고객 채널 | 고객 채널 요청 유입 | Web/Mobile 등 |
| 3 | 대외 채널 | 외부기관/법인 연계 유입 | 공공·금융·계열 |
| 4 | 채널 통합 | 접속·인증·프로토콜·중계 | MCA/MCI/대외 중계 |
| 5 | 서비스 제공 | 업무·데이터 처리·정보 제공 | MP/DP/BI/DG/IM |
| 6 | 대내 통합 | 내부 연계·라우팅·파일 등 | API/EAI/FOS/MFT 등 |

## 5.2 표준 요청 경로

```text
대내/대고객/대외 채널
        ↓
채널 통합
        ↓
서비스 제공
        ↓ 필요 시
대내 통합
        ↓
내부 / 계정계 / 유관시스템
```

Logical 수준에서 중요한 것은 **제품명보다 경계 통과의 책임**이다.

## 5.3 Zone Boundary 규칙

경계 통과 시 논리적으로 확인해야 할 항목:

```text
발신 System
수신 System
Service / Interface ID
인증 / 인가
Schema / Contract
GUID / Trace Key
Error Boundary
Owner
```

실제 Header/Schema/Timeout은 MECHANISM에서 확정한다.

---

# 6. 2.2 시스템 노드 정의 및 식별

## 6.1 논리 시스템 계층

```text
Service 제공 Zone
│
├─ 마케팅플랫폼 (MP)
├─ 데이터플랫폼 (RD / AD 책임 분리)
├─ BI 포탈 (BI)
├─ 데이터거버넌스 (DG)
└─ IT서비스 및 업무지원 (IM)
```

[WORKING BASELINE] 데이터플랫폼은 시스템 공간 안에서 RD/AD 역할을 구분한다.

```text
Data Platform
  ├─ RDW / RD 역할
  └─ ADW / AD 역할
```

이를 하나의 “DW” 역할로 뭉개지 않는다.

## 6.2 환경별 구축 범위

기존 논리 정의서의 Working Baseline:

```text
운영 ── 5체계
개발 ── 5체계
DR   ── 즉시기동 3체계
선도 ── 3체계
```

구체화:

| 환경 | 논리 범위 | 상태 |
|---|---|---|
| 운영 | 5대 시스템 상시 + 이행용 임시 구성 가능 | `[WORKING BASELINE]` |
| 개발 | 운영 5대 체계 대응, 계정·데이터·대외 격리 | `[WORKING BASELINE]` |
| DR | 마케팅·데이터·IT지원 등 즉시 기동 대상 3체계 | `[WORKING BASELINE]` |
| 선도 | 마케팅·데이터·IT지원 등 3체계 중심 | `[WORKING BASELINE]` |

> DR/선도에서 정확히 어떤 세부 Application까지 즉시 기동하는지는 PHYSICAL/DR Runbook에서 재확정한다.

## 6.3 Logical Node 기본 유형

```text
WEB
WAS / Business AP
Integration
Event
CDC
ETL / Batch
RDW
ADW
BI / Analytics
Governance
OM / Support
```

각 Node는 반드시 하나 이상의 명시적 책임을 가진다.

---

# 7. Logical Node Inventory

| 필드 | 설명 |
|---|---|
| Logical Node ID | 논리 식별자 |
| Domain | MP / DP / BI / DG / IM |
| System Group | BIG PICTURE의 시스템 그룹 |
| Zone | 6 Zone 중 소속 |
| Environment | 운영/개발/DR/선도 |
| Node Type | WEB/WAS/AP/DB/Event/ETL 등 |
| Responsibility | 논리 책임 |
| Technology Component | 주요 기술 컴포넌트 |
| Data Ownership | RDW/ADW/메타 등 |
| Interface Boundary | 주요 Source/Target |
| Physical Mapping | 04 PHYSICAL에서 확정 |
| Runtime Type | 06 RUNTIME에서 확정 |
| Evidence | PPT/정의서/Source |
| Status | FACT/TO-BE/GAP/OPEN |

---

# 8. 2.3 기술 컴포넌트 정의

PPT Body의 전개 순서를 최대한 유지한다.

```text
공통 기술 컴포넌트
마케팅플랫폼 (1/3~3/3)
데이터플랫폼
BI 포탈 (1/4~4/4)
데이터거버넌스
IT서비스 및 업무지원 (1/3~3/3)
기타 기술 컴포넌트
```

## 8.1 Technology Component의 정의

Technology Component는 제품 목록이 아니라:

```text
Logical Responsibility
        ↓
Technology Capability
        ↓
Deployment Candidate
```

로 본다.

## 8.2 Component Inventory

| 필드 | 설명 |
|---|---|
| Component ID | 식별자 |
| Domain/System | 소속 |
| Logical Node | 배치 논리 노드 |
| Layer | Client/Service/I-F/Data/Delivery |
| Role | Web/Business/Event/ETL/DB 등 |
| Product/SW | PPT에 근거가 있을 때만 |
| Source | 주요 Input |
| Target | 주요 Output |
| Physical Mapping | PHYSICAL |
| Runtime Type | RUNTIME |
| Evidence | 근거 |
| Status | 상태 |

제품/버전이 PPT에 없으면 `[UNKNOWN]`으로 남긴다.

---

# 9. Layered Architecture

기본 구조:

```text
┌──────────────────────────────┐
│ Client Layer                 │
│ 단말 / Web / Package UI      │
└──────────────┬───────────────┘
               ▼
┌──────────────────────────────┐
│ Service Layer                │
│ 업무 Application / Service   │
└──────────────┬───────────────┘
               ▼
┌──────────────────────────────┐
│ Interface Layer              │
│ API / MCA / Event / File     │
└──────────────┬───────────────┘
               ▼
┌──────────────────────────────┐
│ Data Layer                   │
│ RDW / ADW / Metadata         │
└──────────────────────────────┘
               │
               ▼
       Delivery / Ops Cross-cut
```

Layer는 호출 방향과 책임을 고정한다.

```text
Client → Service → Interface / Data
```

역행/우회는 원칙적으로 제한한다.

---

# 10. 2.4 도메인별 논리 기술 아키텍처

## 10.1 마케팅플랫폼

```text
Channel
  ↓
Web / Service Entry
  ↓
Marketing Application
  ├─ Online Business
  ├─ Customer / Offer
  └─ Event Processing
  ↓
RDW / Integration
```

핵심:

- Online과 Event Processing 책임을 논리적으로 구분한다.
- 대량 ETL을 Online Application에 혼재하지 않는다.
- PDMG는 이 중 Online Application 실행의 AS-IS Reference다.

## 10.2 데이터플랫폼

```text
Core / Source
  ├─ CDC/OGG → RDW
  └─ ETL      → RDW
                 ↓ ETL
                ADW
```

논리 책임:

```text
RDW = 준실시간/운영정보
ADW = 분석/집계/마트
```

RAC/OGG 장비·노드 수는 PHYSICAL에서 정의한다.

## 10.3 BI 포탈

```text
User
 ↓
BI Portal / Package UI
 ↓
BI / Analytics Service
 ↓
RDW / ADW
```

Package/Direct 예외는 MECHANISM에서 별도 표기한다.

## 10.4 데이터거버넌스

```text
Metadata / Quality / Lineage
        ↓
Governance Service
        ↓
Data Platform / Application
```

업무 Transaction DB의 소유자가 되는 것이 아니라 **메타·품질·흐름의 관리 책임**을 가진다.

## 10.5 IT서비스 및 업무지원

PPT에 독립 논리기술 상세가 충분하지 않으면:

```text
[PPT-CONTENT-GAP]
```

으로 유지한다.

확인 가능한 공통/운영/지원 Component만 기록하며 일반론으로 채우지 않는다.

---

# 11. Application / Data Ownership

Logical 설계에서 최소 다음 관계를 닫는다.

```text
Application
   ├─ owns → Business Responsibility
   ├─ uses → Interface
   └─ accesses → Approved Data

Data Platform
   ├─ RDW → Operational / Near-real-time
   └─ ADW → Analytical / Aggregated
```

금지:

```text
Application A → Application B DB 직접 DML
Application A → Application B DAO 직접 호출
External → Internal DB 직접 접근
Channel → Core DB 직접 접근
```

---

# 12. 허용 / 금지 연결

## 12.1 허용

```text
채널 → 채널통합
채널통합 → 서비스제공
서비스제공 ↔ 대내통합
서비스제공 → 자기 소유 Data
Data Platform → 승인된 ETL/CDC 경로
```

## 12.2 금지

```text
채널 ─X→ 서비스 DB 직접
채널 ─X→ 계정계 DB 직접
외부 ─X→ 내부 DB 직접
업무A ─X→ 업무B DAO/DB 공유
Gateway/EAI에 업무 Logic/업무 DB 소유
온라인 AP에 대량 ETL 혼재
```

예외는 ADR/승인 대상으로 관리한다.

---

# 13. Interface Logical Boundary Cross Reference

LOGICAL에서는 **어디를 건너는가**만 고정한다.

```text
Source Zone/System
   ↓
Logical Interface Boundary
   ↓
Target Zone/System
```

MECHANISM에서는 **어떤 방식으로 건너는가**를 고정한다.

```text
API / MCA / Event / CDC / ETL / File
Contract / Header / Timeout / Retry / Security
```

둘을 같은 레벨에서 섞지 않는다.

---

# 14. Security / NFR / Observability 연결

| NFR | LOGICAL 설계 반영 |
|---|---|
| Performance | Online/Event/ETL 역할 분리, 경유 최소화 |
| Availability | 논리 역할의 이중화·DR 대상 식별 |
| Scalability | Scale-out 가능한 AP/Event/Data 역할 분리 |
| Security | Zone/Trust Boundary, 재인증/재검증 위치 |
| Observability | Zone 경계마다 GUID/ServiceId 추적 가능 구조 |

Security Logical Rule:

```text
Trust Boundary 통과
  → 인증/인가 재확인
  → Contract 검증
  → Trace Context 유지
```

---

# 15. PDMG AS-IS Reference

PDMG는 LOGICAL 전체가 아니라 다음 영역의 Reference다.

```text
Information Application
  ↓
Marketing / Business Runtime
  ↓
PDMG
  ├─ pdmg-ui
  ├─ pdmg-jwt
  ├─ pdmg-service
  ├─ pdmg-fw
  └─ pdmg-om [구현 Evidence 확인 필요]
```

주의:

```text
pdmg-fw = Module/Classpath Framework
≠ 독립 원격 System/Server
```

PDMG의 실제 Process/JVM/Spring Context는 RUNTIME/Appendix에서 검증한다.

---

# 16. GAP / RISK / OPEN / ADR

| 항목 | 상태 | 조치 |
|---|---|---|
| 2.1 독립 PPT 상세 범위 | `[PPT-CONTENT-GAP]` 가능 | 6 Zone Baseline로 보강 |
| 2.2 목차/본문 명칭 차이 | `[NUMBERING-DRIFT]` | 병기 유지 |
| IT서비스 및 업무지원 2.4 상세 | `[OPEN]` | PPT/설계자료 추가 확인 |
| 논리 Node별 정확한 제품 | `[UNKNOWN]` | PHYSICAL/SW Inventory에서 확정 |
| DR 3체계 세부 Application | `[OPEN]` | DR 설계/Runbook와 정합 |
| Interface 경계와 매체 혼재 | `[RISK]` | LOGICAL/MECHANISM 분리 검토 |

---

# 17. Verification Checklist

```text
[ ] 2.1~2.4 PPT 공식 구조를 추적할 수 있는가
[ ] 2.2 Numbering Drift를 숨기지 않았는가
[ ] Zone과 VLAN/Subnet을 동일시하지 않았는가
[ ] 5대 Domain/System이 논리적으로 배치되었는가
[ ] 운영/개발/DR/선도 범위가 분리되었는가
[ ] Logical Node와 Physical Host가 구분되는가
[ ] RDW/ADW 논리 책임이 분리되는가
[ ] 허용/금지 연결이 명시되었는가
[ ] Interface Logical Boundary와 Mechanism을 분리했는가
[ ] PDMG를 전체 NSIGHT Logical 구조로 확대하지 않았는가
```

---

# 18. PHYSICAL Handoff

LOGICAL Output:

```text
Zone
Logical System
Logical Node
Technology Component
Environment Scope
Layer
Application/Data Ownership
Allowed / Forbidden Connection
```

PHYSICAL Input:

```text
Center
Host / Hostname
HW / SW
DB / RAC / OGG
Capacity
HA / DR Pair
Filesystem
Account
Port
Inventory
```
