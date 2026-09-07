# NSIGHT / PDMG 아키텍처 정의서 — 02. BIG PICTURE

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 문서 ID: NSIGHT-ARCH-TYPE2-BIGPICTURE-02  
> Architecture Level: **BIG PICTURE**  
> PPT 공식 범위: **1.2 어플리케이션 분류 체계 / 1.3 데이터 주제영역 정의 / 1.4 시스템 아키텍처 구성**  
> 문서 상태: Draft / Evidence-First / PPT-Aligned  
> 작성일: 2026-08-31  
> 선행: `01_VISION`  
> 후속: `03_LOGICAL`

---

# 0. Evidence Register

| ID | 근거 | 본 장 사용 목적 | 상태 |
|---|---|---|---|
| EV-BP-01 | TYPE2/PPT 정합 프롬프트 | 1.2~1.4 공식 구조 | `[PPT-TOC]` / `[PPT-BODY]` |
| EV-BP-02 | 기존 Big Picture & System Boundary 정의서 | 5대 도메인·Boundary·Integration | `[WORKING BASELINE]` |
| EV-BP-03 | 기존 TYPE2 Big Picture 정의 | Inventory/Mapping 구조 | `[WORKING BASELINE]` |
| EV-BP-04 | PDMG Architecture 자료 | PDMG Reference Position | `[AS-IS REFERENCE]` |

---

# 1. 핵심 결론

BIG PICTURE의 목적은 제품 목록을 나열하는 것이 아니라 **NSIGHT의 업무·Application·System·Data 책임을 공간에 고정하는 것**이다.

[PPT-BODY] 기준 상위 Application Domain은 다음 다섯 개다.

```text
마케팅플랫폼
데이터플랫폼
BI 포탈
데이터거버넌스
IT서비스 및 인프라/업무지원
```

이를 전체 구조로 보면:

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

핵심 통제는 다음 세 가지다.

```text
규격 표준
인터페이스 통제
논리/물리 자원 분리
```

---

# 2. 목적 / 범위

본 장은 다음 질문에 답한다.

1. 어떤 Application Domain을 구축하는가?
2. Application을 어떤 분류체계로 관리하는가?
3. Application과 System Group은 어떻게 매핑되는가?
4. Data Subject Area는 어떻게 나뉘는가?
5. 전체 System Architecture의 책임 경계는 어디인가?
6. 어떤 주요 서버/논리 노드가 후속 LOGICAL 설계 대상인가?
7. PDMG는 전체 Big Picture의 어디에 위치하는가?

---

# 3. PPT 공식 구조

```text
1.2 어플리케이션 분류 체계
   ├─ 차세대 정보계 개념 아키텍처
   ├─ 어플리케이션 도메인 구성 정의
   ├─ 어플리케이션 분류 체계 (1/5~5/5)
   └─ 시스템 그룹 업무 구분 (1/6~6/6)

1.3 데이터 주제영역 정의
   └─ 데이터 주제영역 구성 정의 (1/2~2/2)

1.4 시스템 아키텍처 구성
   ├─ 전체 시스템 아키텍처 구조 정의
   └─ 주요 시스템 대상 서버 식별
```

---

# 4. 개념 아키텍처

```text
┌────────────────────────────────────────────────────┐
│ User / Channel                                     │
│ 내부 사용자 · 고객 · 외부 접점                    │
└───────────────────────┬────────────────────────────┘
                        │
                        ▼
┌────────────────────────────────────────────────────┐
│ Access / Channel Integration                       │
│ 인증 · 접속 · 표준 연계                           │
└───────────────────────┬────────────────────────────┘
                        │
                        ▼
┌──────────────── Information Application ───────────┐
│ Marketing │ BI Portal │ Data Governance │ IT지원  │
└───────────────────────┬────────────────────────────┘
                        │
                        ▼
┌────────────────── Data Platform ───────────────────┐
│ RDW : 실시간/준실시간 운영·제공                   │
│ ADW : 분석·집계·마트                               │
└───────────────────────┬────────────────────────────┘
                        │
                        ▼
┌────────────────────────────────────────────────────┐
│ Core / Related / External                          │
└────────────────────────────────────────────────────┘
```

Cross-cutting:

```text
Interface
Security
Observability
Operations
```

---

# 5. 어플리케이션 도메인 구성

| Domain | 핵심 책임 | 대표 Runtime/연계 |
|---|---|---|
| 마케팅플랫폼 | 고객/마케팅 업무·오퍼·접점 | Online / Event |
| 데이터플랫폼 | RDW/ADW·통합·제공·분석 기반 | CDC / ETL / Query |
| BI 포탈 | 조회·분석·리포팅·Self-BI | Query / BI |
| 데이터거버넌스 | 메타·품질·흐름·통제 | Metadata / Quality |
| IT서비스 및 인프라/업무지원 | 공통/운영/지원 | Shared / Ops |

주의: 실제 세부 Application 명칭과 코드값은 PPT의 1/5~5/5 및 공식 코드표를 SSOT로 한다.

---

# 6. 어플리케이션 분류 체계

기본 계층:

```text
Application Group / Domain
        ↓
Application / Business
        ↓
Function
        ↓
Program / Service
```

분류의 목적:

```text
업무 책임
  ↕
System Group
  ↕
Service / Runtime
  ↕
Data Subject
```

## 6.1 Application Inventory 구조

| 필드 | 설명 |
|---|---|
| 대구분 코드 | Domain/Application Group |
| 대구분명 | Domain Name |
| 업무구분 코드 | Application/Business Code |
| 업무구분명 | Application Name |
| 기능 | Function |
| System Group | 배치 대상 System Group |
| Data Subject Area | 주요 데이터 책임 |
| Runtime Type | 대표 실행 유형 |
| Owner | 책임자/조직 |
| Evidence | PPT/CMDB/Source |
| Status | FACT/OPEN/GAP |

현재 문서에서 세부 코드/업무명을 확정할 근거가 부족한 행은 `[UNKNOWN]`으로 유지한다.

---

# 7. 시스템 그룹 업무 구분

PPT 기준 6개 그룹:

```text
1. 마케팅플랫폼 시스템 그룹
2. 데이터플랫폼 시스템 그룹
3. BI 포탈 시스템 그룹
4. 데이터거버넌스 시스템 그룹
5. IT서비스 및 인프라 지원 시스템 그룹
6. 인프라 임시 시스템 그룹
```

System Group은 제품이 아니라 **운영·배치·자원·책임 경계**다.

## 7.1 Mapping 원칙

```text
Application
   ↓ belongs-to
System Group
   ↓ deployed-to
Logical Node
   ↓ mapped-to
Physical Resource
```

## 7.2 Application ↔ System Mapping Inventory

| Application | Domain | System Group | Logical Node | Data | Runtime | Evidence | Status |
|---|---|---|---|---|---|---|---|
| 마케팅 관련 Application | 마케팅플랫폼 | 마케팅플랫폼 그룹 | 후속 LOGICAL | RDW 등 | Online/Event | PPT | `[TO-BE]` |
| BI 관련 Application | BI 포탈 | BI 포탈 그룹 | 후속 LOGICAL | ADW/RDW | BI/Query | PPT | `[TO-BE]` |
| Data Processing | 데이터플랫폼 | 데이터플랫폼 그룹 | 후속 LOGICAL | RDW/ADW | CDC/ETL | PPT | `[TO-BE]` |
| Governance | 데이터거버넌스 | 데이터거버넌스 그룹 | 후속 LOGICAL | Metadata | Governance | PPT | `[TO-BE]` |
| IT Support | IT지원 | IT서비스 그룹 | 후속 LOGICAL | 공통 | Support | PPT | `[TO-BE]` |

세부 Application 명칭은 PPT 세부 분류표에서 별도 Inventory로 추출해야 한다.

---

# 8. 데이터 주제영역 정의

Data Subject Area는 테이블 목록보다 상위의 **데이터 책임 분류**다.

```text
Business / Subject
        ↓
Data Ownership
        ↓
RDW / ADW
        ↓
Application Consumption
        ↓
Interface / Runtime
```

## 8.1 RDW / ADW 역할

```text
RDW
= 실시간/준실시간 운영·정보제공 중심

ADW
= 분석·집계·마트·전략분석 중심
```

두 영역을 “DW 하나”로 통합 설명하지 않는다.

## 8.2 Data Subject Inventory

| 필드 | 설명 |
|---|---|
| Subject ID | 공식 ID |
| Subject Name | 주제영역명 |
| Description | 정의 |
| SoR | 원천/기준 여부 |
| RDW/ADW | 위치 |
| Source | 원천 |
| Consumer | 소비 Application |
| Refresh | CDC/ETL 등 |
| Owner | 데이터 책임 |
| Evidence | 근거 |
| Status | 상태 |

현재 PPT의 주제영역 상세 이름/ID는 원본 장표에서 추출하여 이 표를 확장한다.

---

# 9. 전체 시스템 아키텍처 구조 정의

```text
[내부/고객/외부 Channel]
          │
          ▼
[Channel / Access / Integration Boundary]
          │
          ▼
[Service / Information Application]
  ├─ Marketing
  ├─ BI
  ├─ Governance
  └─ IT Support
          │
          ├──────── Event / API / File
          │
          ▼
[Data Platform]
  ├─ RDW
  └─ ADW
          │
          ├──────── CDC / ETL
          │
          ▼
[Core / Related / External]
```

금지 패턴:

```text
Channel → 타 시스템 DB 직접 접근
External → 내부 DB 직접 DML
모든 Integration을 하나의 REST 방식으로 통일
대량 처리 → Online Thread에 혼재
Application A → Application B DAO 직접 호출
```

---

# 10. 주요 시스템 대상 서버 식별

BIG PICTURE 단계에서는 **물리 Hostname이 아니라 Logical Server Candidate**까지만 식별한다.

예:

```text
WEB
WAS / AP
Event Processing
CDC Relay
ETL / Batch
RDW DB
ADW DB
BI / Solution
Governance
Integration
OM / Monitoring
```

구체적인 Host/대수/센터/SW는 PHYSICAL에서 확정한다.

## 10.1 Candidate Inventory

| Candidate | Domain | Logical Role | Physical Detail | Status |
|---|---|---|---|---|
| WEB | 공통/도메인 | Web Entry | PHYSICAL에서 확정 | Candidate |
| WAS/AP | Marketing/BI 등 | Business Runtime | PHYSICAL | Candidate |
| Event | Marketing | Event Runtime | PHYSICAL | Candidate |
| CDC | Data | Change Capture/Relay | PHYSICAL | Candidate |
| ETL | Data | Bulk/Batch | PHYSICAL | Candidate |
| RDW | Data | Operational DW | PHYSICAL | Candidate |
| ADW | Data | Analytics DW | PHYSICAL | Candidate |

---

# 11. System / External Boundary

BIG PICTURE의 핵심은 **책임은 공간에 고정하고 연결은 경계에서 통제**하는 것이다.

```text
Channel Boundary
Application Boundary
Data Boundary
Integration Boundary
External Boundary
Security Boundary
Operations Boundary
```

경계가 바뀌면 후속 LOGICAL / MECHANISM에서 ADR 대상이다.

---

# 12. PDMG Reference Position

PDMG는 다음 위치의 Reference다.

```text
NSIGHT Big Picture
       │
       ▼
Information Application
       │
       ▼
Marketing / Business Application Runtime
       │
       ▼
PDMG
  pdmg-ui
  pdmg-jwt
  pdmg-fw
  pdmg-service
  pdmg-om [Evidence 확인 필요]
```

PDMG가 직접 소유하지 않는 전체 영역:

```text
RDW/ADW 전체
전사 Kafka/Event Platform
전사 CDC/ETL
BI 전체
Data Governance 전체
Enterprise DR 전체
```

---

# 13. BIG PICTURE 원칙

| ID | 원칙 |
|---|---|
| BP-01 | Application은 Domain/Business Responsibility로 분류 |
| BP-02 | System Group은 자원·운영·책임 경계 |
| BP-03 | Data Subject Area는 테이블보다 상위 책임 |
| BP-04 | RDW와 ADW 역할을 분리 |
| BP-05 | Integration은 목적별 Mechanism을 사용 |
| BP-06 | Logical Server 후보와 Physical Host를 구분 |
| BP-07 | PDMG는 Application Runtime Reference로 한정 |

---

# 14. GAP / OPEN

| 항목 | 상태 |
|---|---|
| Application 세부 코드/기능 Inventory 완전 추출 | `[OPEN]` |
| Data Subject Area 세부 ID/Name 완전 추출 | `[OPEN]` |
| Application ↔ System 세부 매핑 | `[OPEN]` |
| 주요 대상 서버의 Logical Node 상세 | LOGICAL로 Handoff |
| 5대 Domain 명칭의 PPT 내 띄어쓰기/용어 Drift | `[OPEN]` |

---

# 15. Verification Checklist

```text
[ ] 1.2~1.4 PPT 구조를 추적할 수 있는가
[ ] 5대 Domain이 분리되어 있는가
[ ] Application과 System Group을 혼동하지 않았는가
[ ] Data Subject와 Table을 혼동하지 않았는가
[ ] RDW/ADW 역할이 구분되는가
[ ] Logical Server Candidate와 Physical Host가 구분되는가
[ ] PDMG 위치가 과도하게 확대되지 않았는가
[ ] Interface/External Boundary가 보이는가
```

---

# 16. LOGICAL Handoff

BIG PICTURE Output:

```text
Domain
Application Classification
System Group
Data Subject Area
System Boundary
Logical Server Candidate
```

LOGICAL에서 확정할 것:

```text
IT Zone
Logical System
Logical Node
Technology Component
Environment Scope
Allowed / Forbidden Connection
Domain Logical Architecture
```
