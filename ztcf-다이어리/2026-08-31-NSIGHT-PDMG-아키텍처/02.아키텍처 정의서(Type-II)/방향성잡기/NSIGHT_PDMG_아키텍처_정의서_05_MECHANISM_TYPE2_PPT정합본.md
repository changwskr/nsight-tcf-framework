# NSIGHT / PDMG 아키텍처 정의서 — 05. MECHANISM

> 프로젝트: NH 농협 상호금융 차세대 정보계 NSIGHT  
> 문서 ID: NSIGHT-ARCH-TYPE2-MECHANISM-05  
> Architecture Level: **MECHANISM**  
> PPT 공식 범위: **6. 인터페이스 아키텍처 / 8. 아키텍처 표준화 / 9. 아키텍처 구성 요소 / 10. 업무 솔루션 아키텍처**  
> 문서 상태: Draft / Evidence-First / PPT-Aligned  
> 작성일: 2026-08-31  
> 선행: `04_PHYSICAL`  
> 후속: `06_RUNTIME`  
> Evidence Level: PPT Target + Interface/Standardization/Component/Solution Definition + PDMG AS-IS Reference

---

# 0. Evidence Register

| ID | 근거 | 사용 목적 | 상태 |
|---|---|---|---|
| EV-MECH-01 | `06_인터페이스아키텍처_정의서.md` | 온라인/파일/데이터 표준·계약·금지 | `[WORKING BASELINE]` |
| EV-MECH-02 | `08_아키텍처표준화_정의서.md` | 8단계·전문·GUID·캐릭터셋·호출 규칙 | `[WORKING BASELINE]` |
| EV-MECH-03 | `09_아키텍처구성요소_정의서.md` | 단말/온라인/배치 Framework·진입점·공통 메커니즘 | `[WORKING BASELINE]` |
| EV-MECH-04 | `10_업무솔루션아키텍처_정의서.md` | SELF-BI/OLAP/EBM/Package의 아키텍처 편입 기준 | `[SUPPLEMENTAL-DEFINITION]` |
| EV-MECH-05 | PDMG Module/Online Runtime 자료 | Framework/Business AS-IS Reference | `[AS-IS REFERENCE]` |

---

# 1. 핵심 결론

MECHANISM은 논리·물리 구조가 **어떤 공통 규칙과 표준 부품으로 동작하는가**를 정의한다.

```text
LOGICAL / PHYSICAL
       ↓
MECHANISM
  ├─ Interface
  ├─ Application Layer
  ├─ Transaction 8 Steps
  ├─ Standard Message
  ├─ GUID / Trace
  ├─ Charset
  ├─ Framework Entry
  ├─ SSO / Exception / Log
  ├─ Batch Framework
  └─ Business Solution Contract
       ↓
RUNTIME
  실제 시간순 Sequence / Failure / Evidence
```

핵심 원칙:

```text
연계 목적별 Mechanism 분리
+
온라인 거래 동일 뼈대
+
전문 / GUID / 호출규칙 표준화
+
Framework 책임 재사용
+
업무 솔루션도 Architecture Boundary 안에 포함
```

한 문장:

> **MECHANISM은 “어떻게 안전하고 일관되게 연결·실행할 것인가”를 표준화하고, 실제 실행 시간축은 RUNTIME으로 위임한다.**

---

# 2. 목적 / 범위

본 장은 다음 질문에 답한다.

1. Online/File/Data/Event 연계에 어떤 표준 Mechanism을 사용하는가?
2. Interface Contract에 무엇을 필수로 넣는가?
3. 온라인 거래의 공통 처리 단계는 무엇인가?
4. 표준전문과 GUID는 어디서 어떻게 적용되는가?
5. Application Layer와 업무간 호출 경계는 무엇인가?
6. 단말/온라인/배치 Framework는 무엇을 책임지는가?
7. File Upload/Download, RD, Inbound, SSO, Exception, 거래로그는 어디서 제공하는가?
8. SELF-BI/OLAP/EBM 등 솔루션을 어떤 아키텍처 계약으로 편입하는가?
9. PDMG Source가 실제로 증명하는 Mechanism과 PPT Target의 차이는 무엇인가?

---

# 3. PPT 공식 구조 / Content 상태

```text
6. 인터페이스 아키텍처
   6.1 인터페이스 표준 정의
   6.2 인터페이스 구성도

8. 아키텍처 표준화
   8.1 어플리케이션 계층 구조
   8.2 전문 표준화 정의
   8.3 GUID 관리 체계 정의
   8.4 캐릭터 셋 정의
   8.5 XX
   8.6 XX
   8.7 XX
   8.8 XX
   8.9 XX

9. 아키텍처 구성 요소
   9.1 단말 프레임워크
   9.2 온라인 프레임워크
   9.3 배치 프레임워크

10. 업무 솔루션 아키텍처
   10.1 SELF-BI
   10.2 OLAP
   10.3 EBM
   10.4 데이터 흐름
```

PPT 상태:

```text
6장  = 상세 존재
8장  = 상세 존재 + 반복 블록 [DUPLICATE-BLOCK]
9장  = 상세 존재
10장 = [PPT-CONTENT-GAP] → Supplemental Definition 사용
```

8.5~8.9는 **공식 제목 `XX`를 유지**하고 임의 번호명칭을 확정하지 않는다.

---

# 4. Main Mechanism Architecture

```text
[Channel / Producer]
        │
        ├──────── Online ── MCA / MCI / API / Direct
        ├──────── Event  ── Event Broker
        ├──────── File   ── FOS / MFT
        └──────── Data   ── CDC / ETL
        │
        ▼
┌──────────────────── Standard Entry / Framework ────────────────────┐
│ ServiceId / Interface ID / Authentication / Context / GUID         │
│ Standard Message / Error / Logging                                 │
│                                                                    │
│ Transaction Pipeline                                               │
│ [1] System Pre                                                     │
│ [2] Common Pre                                                     │
│ [3] Business Pre                                                   │
│ [4] Controller                                                     │
│ [5] Business Service                                               │
│ [6] Business Post                                                  │
│ [7] Common Post                                                    │
│ [8] System Post                                                    │
└──────────────────────────┬─────────────────────────────────────────┘
                           ▼
                     DAO / Data / External
```

---

# 5. 6.1 인터페이스 표준 정의

## 5.1 인터페이스 3대 기본 유형

[PPT/기존 정의서 기준]

```text
온라인 Interface
파일 Interface
데이터 Interface
```

TYPE2 확장 관점에서는 Event를 별도 Runtime/Mechanism으로 명시한다.

```text
Online
Event
File
CDC
ETL
External
```

## 5.2 구축 원칙

```text
온라인 AP / 배치 AP 자원 분리
ETL 서버/실행영역 독립
Event 처리 자원 분리
CDC 중계 제공
```

## 5.3 Online 표준 경로

기존 정의서 기준 대표 표준:

| Source / Context | Mechanism | Target / 의미 |
|---|---|---|
| 통합업무 | 영업점 MCA | 정보계 |
| 비대면 채널 | MCI | 정보계 |
| 정보계 단말 | Direct | 정보계 |
| Package UI | Direct | Solution, 예외 |
| 정보계 ↔ 대내 | Cruz APIM | 양방향 |
| 정보계 ↔ 대외 | API Gateway/APIM | 양방향 |
| 타 법인 | GSE | 정보계 |

제품명/경로는 PPT Baseline이며 실제 운영 제품/URL은 별도 Evidence로 검증한다.

## 5.4 File 표준

```text
내부 / 단말 / Package
        ↓
       FOS
        ↓
     정보계
```

대외 File은:

```text
FOS + 대외 연계 Mechanism
```

Completion Signal:

```text
ready
  ↓
processing
  ↓
done
  ↘
  error
```

`done` 이전 업무 반영 금지.

## 5.5 Data 표준

```text
Core DB ── CDC/OGG ──▶ RDW
Core BCV ── ETL ─────▶ RDW
RDW      ── ETL ─────▶ ADW
DW       ↔ ETL ↔ 타 시스템
```

---

# 6. Interface Selection Principle

업무 목적이 먼저다.

```text
즉시 응답 필수?
 ├─ YES → Online/API/MCA
 └─ NO
      ├─ Event 발생? → Event
      ├─ DB Change?  → CDC
      ├─ 대량 Data?  → ETL
      └─ File 단위?  → File/FOS/MFT
```

금지:

```text
모든 연계를 REST로 통일
대량 Data를 Online API로 전송
Event를 Online Request Thread에서 장시간 처리
타 시스템 DB Direct DML
```

---

# 7. Interface Contract

Interface는 단순 연결이 아니다.

```text
Interface
= Connection
+ Contract
+ Error
+ Timeout
+ Retry / Replay
+ Security
+ Trace
+ Operations
+ Versioning
```

필수 필드:

| 분류 | 필드 |
|---|---|
| Identity | Interface ID / Name |
| Relation | Source / Target |
| Business | Purpose / Owner |
| Type | API/Event/CDC/ETL/File |
| Runtime | SYNC/ASYNC |
| Contract | Protocol / Endpoint / Schema |
| Context | Header / GUID / ServiceId |
| Failure | Error / Timeout / Retry / Compensation |
| Integrity | Idempotency / Reconciliation |
| Security | AuthN/AuthZ/Encryption |
| Operations | SLA / Metric / Alert / Runbook |
| Change | Version / Deprecation / ADR |

---

# 8. SYNC / ASYNC

원칙:

> **현재 업무 Transaction 완료에 Target 결과가 반드시 필요한 경우만 SYNC를 사용한다.**

| 특성 | SYNC | ASYNC |
|---|---|---|
| 사용자 즉시 응답 | 적합 | 부적합 |
| 장시간 처리 | 부적합 | 적합 |
| 다수 Consumer | 부적합 | 적합 |
| 장애 격리 | 어려움 | 용이 |
| Replay | 복잡 | 용이 |
| 대량 처리 | 부적합 | 적합 |

---

# 9. Timeout / Retry / Idempotency Mechanism

Timeout은 계층적으로 관리한다.

```text
Client
  ↓
Gateway / Channel
  ↓
Application
  ↓
DB / External
```

상위 Timeout이 하위 처리가 끝날 수 없는 구조로 역전되지 않도록 Budget을 설계한다.

Retry 원칙:

```text
Transient Failure
  ↓
Retry Candidate
  ↓
Backoff
  ↓
Max Retry
  ↓
DLQ / Manual Recovery / Replay
```

무작정 Retry 금지:

```text
금융 DML
중복 위험 거래
Validation Error
Authorization Error
Business Reject
```

Idempotency가 없는 Write Retry는 원칙적으로 금지한다.

---

# 10. 6.2 인터페이스 구성도

PPT 상세이 존재하는 중심:

```text
마케팅플랫폼
데이터플랫폼
BI 포탈
```

다음 Domain의 독립 상세가 없으면 `[GAP]`으로 남긴다.

```text
데이터거버넌스
IT서비스 및 업무지원
```

Interface Diagram에는 반드시:

```text
Source
Mechanism
Direction
Target
SYNC/ASYNC
Owner
```

를 명시한다.

---

# 11. 8.1 어플리케이션 계층 구조

기본 계층:

```text
Application Group
  ↓
Application
  ↓
Function
  ↓
Controller / Service / DAO / Resource
```

기존 표준화 정의서 Baseline:

```text
ServiceId ↔ Controller
```

1:1 원칙은 PPT/표준 Baseline로 관리하되, PDMG AS-IS는 Handler Registry 구조를 함께 사용하므로 실제 Source와 차이를 `[GAP/ALIGNMENT]`로 분석한다.

---

# 12. 거래 처리 8단계

```text
[1] 시스템 선처리   필수
[2] 공통 선처리     선택
[3] 업무 선처리     선택
[4] Controller      필수
[5] Biz Service     통상 존재
[6] 업무 후처리     선택
[7] 공통 후처리     선택
[8] 시스템 후처리   필수
```

정상 완료 개념:

```text
Controller/Service 반환
   ≠
거래 완료

사용한 Post 단계
  ↓
System Post
  ↓
Commit / Rollback / Output / Log / Cleanup
  =
거래 완료
```

단계별 책임을 업무코드가 임의 재구현하지 않는다.

---

# 13. 호출 구조 정의

정상 방향:

```text
Controller
  ↓
Service / Facade
  ↓
DAO
  ↓
Mapper / SQL
  ↓
DB
```

PDMG AS-IS Reference:

```text
Handler
  ↓
Facade
  ↓
Service
  ↓
DAO / Mapper
```

두 구조의 차이는 **PPT Target vs PDMG AS-IS**로 표시한다.

금지:

```text
Controller → DAO 직접
Handler → SQL 직접
Application A → Application B DAO 직접
Layer 역행
```

타 Application/Group 호출은 승인된 Interface를 사용한다.

---

# 14. 8.2 전문 표준화 정의

논리 전문은 채널부터 정보계까지 공통 Context를 전달한다.

```text
Channel
  ↔ Channel Integration
  ↔ Information Application
  ↔ Internal Integration
```

표현 형식:

```text
FLAT
JSON / HTTP
```

MCA 등 변환 노드는:

```text
FLAT ↔ JSON
Error Mapping
Charset Conversion
```

책임을 가진다.

Package UI는 예외가 있을 수 있으므로 명시적으로 `[EXCEPTION]` 처리한다.

---

# 15. 8.3 GUID 관리 체계

기본 규칙:

```text
Channel에서 최초 생성
       ↓
Node 경유
       ↓
Progress Sequence 증가
       ↓
Response 복귀까지 유지
       ↓
End-to-End Search
```

GUID 목적:

```text
Transaction Correlation
Error Trace
Log / APM Correlation
Interface Reconciliation
Runtime Evidence
```

PDMG Source에서 진행번호 자동 증가 책임이 확인되지 않으면:

```text
[GAP]
Adapter / Framework / Application 중 Owner 확정 필요
```

---

# 16. 8.4 캐릭터 셋 정의

[PPT BASELINE]

```text
Channel / Business System → UTF-8
RDW / ADW DB              → MS949
```

이는 **설계 기준**이다.

실제 다음은 별도 검증한다.

```text
JVM Encoding
HTTP Content-Type
File Encoding
DB NLS
JDBC Conversion
ETL / FOS Encoding
```

설계와 Runtime Config가 다르면 `[DRIFT]`.

---

# 17. 8.5~8.9

PPT 공식 제목:

```text
8.5 XX
8.6 XX
8.7 XX
8.8 XX
8.9 XX
```

따라서 공식 번호명을 임의로 변경하지 않는다.

PPT Body의 확장 주제 후보만 기록한다.

```text
[8.X Candidate]
거래 처리 구조
선/후 처리
호출 구조
도메인
거래 처리 경로
```

`[DUPLICATE-BLOCK]`인 반복 장표는 한 Baseline으로 정리하고 차이만 기록한다.

---

# 18. 9.1 단말 프레임워크

단말 Framework의 책임:

```text
Screen / Client
  ↓
Standard Entry
  ↓
Request Conversion
  ↓
ServiceId / Context
  ↓
Application Framework
```

프로젝트/Directory 등 세부 구조는 실제 PPT/Source 근거로만 작성한다.

---

# 19. 9.2 온라인 프레임워크

PPT Body 전개 주제:

```text
온라인 프레임워크
단말 - Application Framework 연계
File Upload
File Download
Report(RD) 연계
Inbound 거래
SSO
온라인 선/후처리
Exception Handling
거래로그
Master Solution
Cloud Framework 제공기능
상용 F/W 대비 개발/실행/운영 대응
업무 개발 Framework 사용 기준
```

온라인 Framework 책임을 한 문장으로 정의하면:

> **업무가 “무엇을 처리할지”에 집중할 수 있도록 진입·Context·거래골격·Transaction·오류·로그·공통 메커니즘을 제공한다.**

---

# 20. PDMG Framework AS-IS Reference

PDMG에서 확인되는 주요 Reference:

```text
pdmg-fw
  ├─ Filter / Context
  ├─ TCF
  ├─ Timeout
  ├─ Transaction
  ├─ Error
  └─ Logging

pdmg-service
  └─ Handler / Facade / Service / DAO

pdmg-ui
pdmg-jwt
pdmg-om [Current Evidence 확인 필요]
```

중요:

```text
pdmg-fw 별도 Module
≠
pdmg-service가 HTTP로 호출하는 독립 서버
```

실제 Spring Context/JVM 관계는 Source Evidence를 따른다.

---

# 21. Online Entry

기존 구성요소 정의서 Baseline:

```text
/ins/{ServiceId}
  Wire: xDataSet → Map
  Context: ServiceContext

/nb/{ServiceId}
  Wire: JSON → DTO
  Context: DTO + hdr
```

두 경로는 동일 Business Service로 합류하도록 설계한다.

실제 PDMG Source Snapshot과 URL이 다르면 최신 Source를 AS-IS로 기록한다.

---

# 22. 공통 메커니즘

설계서에서 각 기능에 대해:

```text
사용
미사용
예외
```

중 하나를 명시한다.

대상:

```text
File Upload
File Download
RD / Report
Inbound
SSO
Exception
Transaction Log
```

업무가 Framework 제공 기능을 별도 재구현하는 것을 금지한다.

---

# 23. 9.3 배치 프레임워크

4계층으로 구분한다.

```text
1. Orchestration
   Control-M / Scheduler

2. Execution
   Batch Framework
   Job / Step

3. State
   JobRepository / Checkpoint

4. Business Data
   Business DB
```

흐름:

```text
Scheduler
  ↓
Agent / Shell
  ↓
JobLauncher
  ↓
Job
  ↓
Step
  ↓
JobRepository + Business DB
```

금지:

```text
온라인 WAS에서 장기 Batch
Application 내부 cron만으로 운영 Batch
재실행 시 중복 적재
초대형 단일 Transaction
업무일자 = System Date 하드코딩
```

---

# 24. 10. 업무 솔루션 아키텍처

[PPT-TOC]

```text
10.1 SELF-BI
10.2 OLAP
10.3 EBM
10.4 데이터 흐름
```

현재 PPT 독립 본문이 충분하지 않으므로:

```text
[PPT-CONTENT-GAP]
[SUPPLEMENTAL-DEFINITION]
```

으로 관리한다.

---

# 25. 업무 솔루션 필수 Architecture Header

모든 솔루션은 다음을 반드시 정의한다.

```text
Solution Name
System / Domain
UI Type
Interface Type / Standard No.
Runtime Type
Data Ownership
SSO / Authentication
Standard Message 적용/예외
Direct 예외
Layered Architecture Link
Owner
Evidence
GAP / ADR
```

솔루션이라는 이유로 Architecture 표준에서 제외하지 않는다.

---

# 26. Solution Mapping Baseline

| Solution 성격 | 기본 소속 | 주요 고려 |
|---|---|---|
| SELF-BI / OLAP | BI | Package UI, Query, ADW/RDW |
| EBM / Campaign | MP | Event/Offer Runtime |
| Data Mart / Load | DP | ETL/CDC, RDW/ADW |
| Metadata Tool | DG | Metadata/Quality/Lineage |
| IT Support Tool | IM | Shared/Support |

정확한 제품/구성은 PPT/솔루션 설계자료 근거로 확정한다.

---

# 27. Security Mechanism

공통:

```text
Authentication
Authorization
SSO
Token / Session
Encryption
Masking
Audit
```

Interface/Framework/Solution 모두 Trust Boundary를 통과할 때 동일 정책을 적용한다.

PDMG JWT/SSO 상세는 AS-IS Reference로 사용하되 NSIGHT 전사 인증표준으로 자동 승격하지 않는다.

---

# 28. Observability Mechanism

표준 Context에 다음 연결을 보장한다.

```text
GUID
ServiceId
Interface ID
User / Channel Context
Error Code
Timing
```

이를:

```text
Application Log
Transaction Log
APM
SQL / DB
Interface Log
Runtime Evidence
```

로 연결한다.

---

# 29. MECHANISM 원칙 / 금지

| ID | 원칙 |
|---|---|
| MC-01 | 업무 목적에 따라 Interface Mechanism 선택 |
| MC-02 | Direct P2P / Direct DB 최소화 |
| MC-03 | Contract First |
| MC-04 | SYNC는 필요한 경우만 |
| MC-05 | Retry는 Idempotency와 함께 |
| MC-06 | 온라인 거래는 공통 8단계 |
| MC-07 | GUID/Trace Context 유지 |
| MC-08 | Framework 제공기능 재구현 금지 |
| MC-09 | Batch는 중앙 오케스트레이션 |
| MC-10 | Solution도 Architecture Boundary 안에 포함 |
| MC-11 | 예외는 ADR/승인 |

---

# 30. GAP / RISK / OPEN / ADR

| 항목 | 상태 | 조치 |
|---|---|---|
| 8.5~8.9 공식 제목 | `[OPEN]` | PPT 공식 개정 전 XX 유지 |
| 8장 반복 블록 | `[DUPLICATE-BLOCK]` | 후행본 비교 후 SSOT 1벌 |
| GUID 진행번호 증가 Owner | `[GAP]` 가능 | Source/Framework 검증 |
| Charset 실제 Runtime 설정 | `[OPEN/DRIFT]` 가능 | HTTP/JVM/DB/File 검증 |
| 10장 상세 | `[PPT-CONTENT-GAP]` | Supplemental Definition 유지 |
| PDMG ON/OFF 구조 차이 | `[AS-IS GAP]` | RUNTIME에서 검증 |
| Interface 전체 ID/Owner/SLA | `[OPEN]` | Inventory 작성 |
| SSO/JWT 전사 적용범위 | `[OPEN]` | Security ADR |

---

# 31. Verification Checklist

```text
[ ] 6·8·9·10 PPT 공식 구조가 추적되는가
[ ] Interface를 목적별로 분리했는가
[ ] Online/File/Data/Event가 혼재되지 않는가
[ ] Interface Contract가 정의되어 있는가
[ ] SYNC/ASYNC 기준이 있는가
[ ] Retry와 Idempotency가 연결되는가
[ ] 거래 8단계가 정의되는가
[ ] 표준전문 / GUID / Charset이 분리되어 있는가
[ ] Framework와 Business 책임이 구분되는가
[ ] PDMG AS-IS를 Target으로 자동 승격하지 않았는가
[ ] 8.5~8.9를 임의 명명하지 않았는가
[ ] 10장 Content Gap을 숨기지 않았는가
[ ] Batch 4계층이 구분되는가
[ ] 솔루션에 IF/Runtime/SSO/Data Owner가 정의되는가
```

---

# 32. RUNTIME Handoff

MECHANISM Output:

```text
Interface Type / Contract
SYNC / ASYNC
Application Layer
Transaction 8 Steps
Standard Message
GUID / Charset
Framework Entry / Responsibility
SSO / Error / Log
Batch Orchestration / Execution
Solution Contract
```

RUNTIME에서 검증할 것:

```text
Runtime Type
Actual Sequence
Thread
Transaction
Timeout
Retry / Compensation
Failure / Error
Metric / Trace
HA / DR / Recovery
Runtime Evidence
```
