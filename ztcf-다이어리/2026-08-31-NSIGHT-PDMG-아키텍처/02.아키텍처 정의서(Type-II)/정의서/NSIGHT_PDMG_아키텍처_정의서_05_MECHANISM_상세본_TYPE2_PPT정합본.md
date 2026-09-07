# NSIGHT / PDMG 아키텍처 정의서
# 05. MECHANISM — 인터페이스 / 아키텍처 표준화 / 프레임워크 / 업무솔루션 상세본

> 프로젝트: **NH 농협 상호금융 차세대 정보계 NSIGHT**  
> 문서 ID: `NSIGHT-ARCH-TYPE2-MECHANISM-05-DETAIL`  
> Architecture Level: **MECHANISM**  
> PPT 공식 범위:  
> **6. 인터페이스 아키텍처**  
> **8. 아키텍처 표준화**  
> **9. 아키텍처 구성 요소**  
> **10. 업무 솔루션 아키텍처**  
> 문서 상태: **Draft / Evidence-First / PPT-Aligned / Detailed Baseline**  
> 작성 기준일: **2026-08-31**  
> 선행 문서: `04. PHYSICAL — 물리 인프라 / 데이터베이스 / 시스템 표준 상세본`  
> 후속 문서: `06. RUNTIME — Runtime / Operations / Evidence 상세본`  
> 핵심 질문: **논리·물리 구조를 어떤 표준 Interface·거래골격·전문·GUID·Framework·Batch·Solution 계약으로 일관되게 실행할 것인가?**

---

# 0. 문서 사용법

MECHANISM은 “어느 서버에 배치되는가”를 설명하는 장이 아니다.

본 장은 다음을 고정한다.

```text
Physical / Logical Structure
        ↓
Interface Type / Standard Route
        ↓
Application Entry
        ↓
Transaction Skeleton
        ↓
Message / GUID / Charset
        ↓
Framework Responsibility
        ↓
Common Mechanism
        ↓
Batch / Solution Contract
        ↓
Runtime Sequence
```

즉:

```text
WHERE
→ PHYSICAL

HOW
→ MECHANISM

WHEN / IN WHAT ORDER
→ RUNTIME
```

---

# 0.1 Evidence 태그

| 태그 | 의미 |
|---|---|
| `[PPT-TOC]` | PPT 공식 목차 |
| `[PPT-BODY]` | PPT 본문에서 직접 확인 |
| `[PPT-CONTENT-GAP]` | PPT 상세 부족 |
| `[DUPLICATE-BLOCK]` | PPT 반복 블록 |
| `[FACT]` | 공식 정의서/Source에서 직접 확인 |
| `[WORKING BASELINE]` | 프로젝트 기준으로 반복 사용 |
| `[AS-IS]` | PDMG 현재 구현 |
| `[TO-BE]` | 목표 |
| `[SUPPLEMENTAL-DEFINITION]` | PPT Gap을 보강하는 기존 정의서 |
| `[GAP]` | 목표/자료/구현 차이 |
| `[OPEN]` | 결정 필요 |
| `[RISK]` | 위험 |
| `[ADR]` | Architecture Decision 필요 |
| `[EXCEPTION]` | 승인·관리되는 표준 예외 |

---

# 0.2 Evidence Register

| ID | 근거 | 사용 목적 | 상태 |
|---|---|---|---|
| EV-MECH-01 | TYPE2/PPT 정합 프롬프트 | 6/8/9/10 공식 목차, 8장 XX·Duplicate, 10장 Gap | `[WORKING BASELINE]` |
| EV-MECH-02 | `06_인터페이스아키텍처_정의서.md` | 온라인/파일/데이터 표준표, 계약, Direct 예외, FOS, CDC/ETL | `[FACT/WORKING BASELINE]` |
| EV-MECH-03 | `08_아키텍처표준화_정의서.md` | 패키지/ServiceId, 거래 8단계, 전문, GUID R1~R5, Charset, 호출 규칙 | `[FACT/WORKING BASELINE]` |
| EV-MECH-04 | `09_아키텍처구성요소_정의서.md` | `/ins` `/nb`, Framework vs 업무, Upload/RD/Inbound/SSO/예외/로그, Batch 4계층 | `[FACT/WORKING BASELINE]` |
| EV-MECH-05 | `10_업무솔루션아키텍처_정의서.md` | Solution 필수 Header, BI/MP/RD·AD/DG/IM 소속, Runtime #, Direct 예외 | `[SUPPLEMENTAL-DEFINITION]` |
| EV-MECH-06 | PDMG Module/Application/Runtime 자료 | ServiceId, Handler/Facade/Service/DAO, TCF AS-IS 정합 | `[AS-IS REFERENCE]` |
| EV-MECH-07 | `04 PHYSICAL 상세본` | Host/DB/Port/Physical Handoff | `[CURRENT BASELINE DRAFT]` |

---

# 1. PPT 공식 구조

[PPT-TOC]

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

---

# 1.1 PPT 정합 특수사항

## 8장

[PPT-BODY]에는 다음 확장 주제가 존재한다.

```text
정보 단말
온라인 프레임워크
거래 처리 구조
선/후 처리 범위
호출 구조 정의
도메인 정의
거래 처리 경로
미니 싱글뷰 거래 처리 경로
마케팅 플랫폼 거래 처리 경로
BI 포탈 거래 처리 경로
업무 솔루션 거래 처리 경로
전문 표준화
GUID 관리 체계
캐릭터 셋
```

그러나:

```text
8.5~8.9 공식 제목 = XX
```

따라서 새 공식 이름을 발명하지 않는다.

---

# 1.2 8장 Duplicate Block

```text
전행본 110~123
후행본 125~138
```

은:

```text
[DUPLICATE-BLOCK]
```

으로 관리한다.

원칙:

```text
동일 주제 정의 = 1벌 SSOT
```

반복 PPT를 서로 다른 표준으로 해석하지 않는다.

---

# 1.3 10장 Content Gap

PPT:

```text
10.1 SELF-BI
10.2 OLAP
10.3 EBM
10.4 데이터 흐름
```

은 공식 목차에 존재하지만 상세 본문이 충분하지 않다.

따라서:

```text
[PPT-CONTENT-GAP]
+
[SUPPLEMENTAL-DEFINITION]
```

으로 작성한다.

PPT에 없는 상세를 `[PPT-BODY]`로 표시하지 않는다.

---

# 2. 핵심 결론

MECHANISM의 핵심은 다음이다.

> **모든 거래·연계·배치·솔루션을 동일한 Architecture Boundary 안에 두고, 목적에 맞는 표준 매체를 선택하며, 동일 거래골격·전문·GUID·호출규칙을 재사용하고, Framework가 제공하는 공통 기능을 업무가 재구현하지 않도록 한다.**

구조:

```text
Client / Producer
      ↓
Standard Entry / Interface
      ↓
Framework
      ├─ ServiceId Routing
      ├─ Transaction Skeleton
      ├─ System Pre/Post
      ├─ Error / Logging
      └─ Context / Trace
      ↓
Business
      ├─ DTO
      ├─ Business Pre/Post
      ├─ Service
      └─ DAO / Mapper
      ↓
Data / External
```

---

# 3. PHYSICAL Handoff 수신

PHYSICAL Output:

```text
Host
JVM
Artifact
DB
Port
Account
Filesystem
HA/DR Pair
```

MECHANISM은 이 위에 다음 계약을 얹는다.

```text
Interface ID
ServiceId
Standard Entry
Message
GUID
Charset
Error
Timeout / Retry
Framework
Batch
SSO
Solution Header
```

---

# 4. MECHANISM 전체 Text Architecture

```text
┌────────────────────── Client / Producer ──────────────────────┐
│ Terminal · Integrated Work · Web · Package UI · System       │
└────────────────────────────┬──────────────────────────────────┘
                             │
            ┌────────────────┼────────────────┐
            │                │                │
            ▼                ▼                ▼
        Online IF          File IF          Data IF
     MCA/MCI/API/Direct      FOS         CDC / ETL
            │                │                │
            └────────────────┼────────────────┘
                             ▼
┌────────────────────── Standard Mechanism ─────────────────────┐
│                                                              │
│ ServiceId / Interface ID                                     │
│ Standard Message                                             │
│ GUID / Trace                                                 │
│ Charset                                                      │
│ Authentication / Authorization                               │
│ Error / Timeout / Retry / Idempotency                        │
│                                                              │
├────────────────── Transaction 8 Steps ────────────────────────┤
│ [1] System Pre                                               │
│ [2] Common Pre                                               │
│ [3] Business Pre                                             │
│ [4] Controller                                               │
│ [5] Business Service                                         │
│ [6] Business Post                                            │
│ [7] Common Post                                              │
│ [8] System Post                                              │
└────────────────────────────┬──────────────────────────────────┘
                             ▼
                    DAO / Data / External
```

---

# 5. 6.1 인터페이스 표준 정의

인터페이스는 세 기본 유형으로 분류한다.

```text
1. 온라인 IF
2. 파일 IF
3. 데이터 IF
```

Event는 Runtime/Mechanism 확장 관점에서 별도 책임으로 관리하지만, PPT 6장의 공식 3유형 표를 임의 변경하지 않는다.

---

# 6. Interface Architecture 목적

PPT/정의서 핵심:

```text
시스템 간 자원 경합 최소화
+
플랫폼 독립성 유지
+
표준 매체·계약 고정
```

---

# 7. 정보계 구축 4대 원칙

must:

```text
1. 온라인 AP / 배치 AP 자원 분리
2. ETL 서버 독립
3. 이벤트 서버 분리
4. CDC 중계 제공
```

---

# 8. 온라인 인터페이스 표준표

Baseline:

| No. | Source | IF | Target | 성격 |
|---:|---|---|---|---|
| 1 | 통합업무 | 영업점 MCA | 정보계 | 대면 전사 표준 |
| 2 | 비대면 | MCI | 정보계 | 비대면 전사 표준 |
| 3 | 정보계 단말 | Direct | 정보계 | 앱이 직접 검증 |
| 4 | Package UI | Direct | 정보계 솔루션 | 예외 |
| 5 | 정보계 | Cruz APIM | 대내 | 송신 |
| 6 | 대내 | Cruz APIM | 정보계 | 수신 |
| 7 | 정보계 | API G/W | 대외 | 송신 |
| 8 | 대외 | API G/W | 정보계 | 수신 |
| 9 | 타 법인 | GSE | 정보계 | 법인 간 |

---

# 9. 온라인 경로 Text Architecture

```text
통합업무
   ↓ 영업점 MCA
정보계

비대면
   ↓ MCI
정보계

정보계 단말
   ↓ Direct
정보계

Package UI
   ↓ Direct [EXCEPTION]
Solution

정보계
   ↔ Cruz APIM
대내 시스템

정보계
   ↔ API G/W
대외 시스템

타 법인
   ↓ GSE
정보계
```

---

# 10. MCA / MCI 규범

shall:

```text
채널 표준전문
세션 정책
논리 서비스
```

금지:

```text
채널
→ 정보계 Hostname 직접 호출
```

---

# 11. Direct — 정보단말

Direct 의미:

```text
중계 IF System 없음
```

이지:

```text
검증 없음
```

이 아니다.

Application이 직접 책임:

```text
Authentication
Authorization
Input Validation
Rate Limit
Audit
Error
GUID
```

---

# 12. Direct — Package UI

No.4만 표준 예외.

must:

```text
[EXCEPTION]
표준전문 예외
Direct 예외
Reason
Owner
```

금지:

```text
Package 예외를 일반 API Direct로 확대
```

---

# 13. Cruz APIM

원칙:

```text
등록된 논리 API
```

만 호출한다.

금지:

```text
APIM 뒤
IP:PORT 하드코딩
```

---

# 14. 대외 API Gateway

대외 연계는 Gateway 통과만으로 완료되지 않는다.

별도 정의:

```text
DMZ
Certificate
Encryption
Timeout
Retry
Reconciliation
External SLA
```

---

# 15. GSE

법인 간 표준.

별도 계약:

```text
법인 Code
Authorization
Message Version
Error Ownership
```

일반 내부 API와 동일 취급하지 않는다.

---

# 16. 파일 인터페이스 표준표

| No. | Source | IF System | Target | 방향 |
|---:|---|---|---|---|
| 1 | 정보계 단말 | FOS | 정보계 | Inbound |
| 2 | Package UI | FOS | 정보계 솔루션 | Inbound |
| 3 | 정보계 | FOS | 대내 시스템 | 송신 |
| 4 | 대내 시스템 | FOS | 정보계 | 수신 |
| 5 | 정보계 | FOS + 대외MCA | 대외기관 | 송신 |
| 6 | 대외기관 | FOS + 대외MCA | 정보계 | 수신 |

원칙:

```text
내부 / 대내
= FOS 일원화

대외
= FOS + 대외MCA
```

---

# 17. File Runtime 상태모델

```text
ready
  ↓
processing
  ↓
done

processing
  ↓
error
```

핵심:

```text
Completion Signal 이전
→ 업무 소비 금지
```

---

# 18. File Contract

must:

```text
Interface ID
Layout
Charset
Hash
Completion Signal
Retry
Duplicate Prevention
Retention
SLA
RACI
```

---

# 19. File Naming

Baseline:

```text
<interface-id>_<business-date>_<sequence>_<schema-version>.<extension>
```

예시 형식:

```text
FILEINFO001_YYYYMMDD_0001_v1.dat
```

Manifest:

```text
...dat.manifest
```

---

# 20. File Naming 금지

```text
공백
Control Character
Path Separator
OS 종속 특수문자
고객명
계좌번호
개인정보
동일 파일명 덮어쓰기
```

작성 중:

```text
.part / .tmp
```

후 원자적 확정.

---

# 21. 데이터 인터페이스

기본 경로:

```text
Core DB
→ CDC
→ Information DW / RDW

Core BCV
→ ETL
→ RDW

RDW
→ ETL
→ ADW

DW
→ ETL
→ Other System

Other System
→ ETL
→ DW
```

---

# 22. CDC vs ETL

| 항목 | CDC | ETL |
|---|---|---|
| 목적 | Commit Change 저지연 전파 | 대량·초기·마감·모델링 |
| 처리 단위 | Change | Dataset |
| 시간성 | Real/Near RT | Batch |
| 복구 | Checkpoint | Restart |
| 주요 지표 | Lag | Window/Count |

---

# 23. CDC 금지

```text
CDC
→ 대량 Batch 책임 혼재
```

---

# 24. ETL 금지

```text
ETL
→ “실시간”이라고 이름만 바꿈
```

실제 SLA/실행특성이 다르다.

---

# 25. Interface Type Selection

```text
즉시 응답이 필요?
 ├─ YES → Online
 └─ NO
     ├─ DB Change인가? → CDC
     ├─ 대량/변환인가? → ETL
     ├─ File 계약인가? → File
     └─ Event인가? → Event Mechanism
```

---

# 26. Interface Contract — 공통

모든 Interface:

```text
Type
+
Standard Table No.
+
Source / Target
+
Interface ID
+
Protocol / Message
+
Timeout
+
Retry
+
Idempotency
+
SLA
+
RACI
+
Trace Context
```

---

# 27. Interface ID

정의:

```text
연계 계약의 유일 식별키
```

Interface ID는 다음과 연결한다.

```text
Source
Target
Runtime Type
Message
Monitoring
Owner
```

---

# 28. SYNC / ASYNC 원칙

SYNC:

```text
현재 Transaction 완료에
Target 결과가 반드시 필요
```

일 때만 사용.

ASYNC:

```text
장시간
다수 Consumer
Failure Isolation
Replay
```

에 적합.

---

# 29. SYNC / ASYNC 비교

| 특성 | SYNC | ASYNC |
|---|---|---|
| 즉시 응답 | 적합 | 부적합 |
| 장시간 | 부적합 | 적합 |
| 장애 격리 | 낮음 | 높음 |
| Replay | 어려움 | 용이 |
| 대량 | 부적합 | 적합 |

---

# 30. Timeout Budget

계층:

```text
Client
  ↓
Channel / Gateway
  ↓
Application
  ↓
External / DB
```

원칙:

```text
하위 작업이 종료될 여유 없이
상위 Timeout이 먼저 끝나는
역전구조를 피한다.
```

정확한 초 값은 Runtime/Config Evidence로 확정한다.

---

# 31. Retry

```text
Transient Failure
   ↓
Retry Candidate
   ↓
Backoff
   ↓
Max Retry
   ↓
DLQ / Manual Recovery
```

---

# 32. Retry 금지

무작정 Retry 금지:

```text
Financial DML
Duplicate-risk Transaction
Validation Error
Authorization Error
Business Reject
```

---

# 33. Idempotency

Write Retry:

```text
Retry
+
Idempotency Key
+
Duplicate Detection
```

없이 수행하지 않는다.

---

# 34. 6.2 Interface 구성도

PPT 구간의 상세 중심:

```text
Marketing Platform
Data Platform
BI Portal
```

Data Governance / IT Support 상세가 약하면:

```text
[GAP]
```

으로 표시한다.

---

# 35. Interface Diagram 필수 Annotation

모든 화살표에:

```text
Source
Direction
Mechanism
SYNC/ASYNC
Target
Owner
```

을 표시한다.

---

# 36. 8. Architecture Standardization

핵심:

```text
동일 8단계
+
동일 논리전문
+
동일 GUID
+
동일 호출규칙
```

목표:

```text
End-to-End Trace
+
Business Boundary
```

---

# 37. 8.1 Application 계층 구조

구조:

```text
Application Group
  ↓
Application
  ↓
Function
  ↓
Controller / Service / DAO / Resource
```

---

# 38. ServiceId ↔ Controller

PPT/표준 Baseline:

```text
ServiceId
↔
Controller
1 : 1
```

단 PDMG AS-IS는 Handler Registry를 사용하므로:

```text
[PPT TARGET]
ServiceId ↔ Controller

[PDMG AS-IS]
ServiceId → Dispatcher → Handler
```

차이는 Alignment 대상으로 관리한다.

---

# 39. Transaction 8 Steps

```text
[1] 시스템 선처리   필수
[2] 공통 선처리     선택
[3] 업무 선처리     선택
[4] Controller      필수
[5] Business Service 통상 존재
[6] 업무 후처리     선택
[7] 공통 후처리     선택
[8] 시스템 후처리   필수
```

---

# 40. 8단계 전체 Text Architecture

```text
Request
  ↓
[1 System Pre]
  ↓
[2 Common Pre]
  ↓
[3 Business Pre]
  ↓
[4 Controller]
  ↓
[5 Business Service]
  ↓
DAO / External
  ↓
[6 Business Post]
  ↓
[7 Common Post]
  ↓
[8 System Post]
  ↓
Response
```

---

# 41. 거래 완료 정의

잘못:

```text
Controller 반환
= 거래 완료
```

정상:

```text
Business 반환
→ 사용한 Post 단계
→ System Post
→ Commit/Rollback
→ Output / Log
→ Cleanup
= 완료
```

---

# 42. Step 1 — System Pre

Framework 책임:

```text
Resource / Control
Input Log
ServiceId Mapping
Context
```

금지:

```text
업무 조건문으로 System Boundary 대체
```

---

# 43. Step 2 — Common Pre

Application Common Policy.

금지:

```text
ServiceId별 거대 switch/if
```

업무 고유 사전조건은 Step 3.

---

# 44. Step 3 — Business Pre

업무 사전 조건.

금지:

```text
Transaction Commit
Framework System Control
```

---

# 45. Step 4 — Controller

책임:

```text
Input
Flow Control
Service Call
```

금지:

```text
SQL
DAO Direct
Business Heavy Logic
```

---

# 46. Step 5 — Business Service

책임:

```text
Business Rule
Use Case
```

화면 전용 조립만 Service에 방치하지 않는다.

---

# 47. Step 6 — Business Post

업무 후처리.

Transaction 최종 제어를 가로채지 않는다.

---

# 48. Step 7 — Common Post

Application Common 후처리.

---

# 49. Step 8 — System Post

Framework 필수.

```text
Commit / Rollback
Output
System Log
Resource Cleanup
```

예외 시:

```text
Primary Failure
vs
Cleanup Failure
```

을 구분한다.

---

# 50. 호출 구조

기본:

```text
Controller
  ↓
Service
  ↓
DAO
  ↓
Mapper / SQL
  ↓
DB
```

PDMG AS-IS:

```text
Handler
  ↓
Facade
  ↓
Service
  ↓
DAO
```

---

# 51. Cross-Application Call

같은 기능:

```text
Controller → Service → DAO
```

타 기능:

```text
상대 Service Contract
```

타 Application:

```text
Public Controller/API
```

타 Group/External:

```text
6장 Standard IF
```

---

# 52. 호출 금지

```text
Controller → DAO
Application A → Application B DAO
Application A → Application B Table Direct
Remote Call을 Local TX로 묶는 가정
```

---

# 53. 8.2 표준 전문

논리 표준:

```text
Header
+
Body
+
Result / Error
```

FLAT/JSON은 **같은 논리전문의 구간별 표현**이다.

---

# 54. 표준 전문 적용

```text
Integrated Work / MCA
→ FLAT → JSON/HTTP

Information Terminal
→ JSON/HTTP

Marketing / BI AP
→ JSON/HTTP

APIM
→ JSON/HTTP

GSE
→ FLAT 중심

Package UI
→ [EXCEPTION]
```

---

# 55. MCA 변환 책임

```text
FLAT
↔
JSON
```

변환 및:

```text
Error Mapping
```

책임.

---

# 56. Direct도 표준 책임 유지

Direct 단말도:

```text
Authentication
GUID
Error
Transaction Log
```

규칙을 동일 적용한다.

---

# 57. 논리 전문 Header 최소개념

Header:

```text
Message Version
Transaction GUID
Progress Sequence
Channel / Institution Code
Service Code
Request Timestamp
User / Terminal
Length / Page / Continuation
```

상세 Field 명은 전문 필드정의서 SSOT를 따른다.

---

# 58. Body

```text
Service-specific Request / Response Data
```

---

# 59. Result / Error

```text
Success Flag
Standard Response Code
Business Error Code
User Message
Trace Information
```

---

# 60. 8.3 GUID 관리

5대 규칙:

```text
R1 Standard Message의 GUID = Node간 식별자
R2 Channel이 최초 거래 시 생성
R3 원거래글로벌ID로 E2E 조회
R4 Node 경유 시 진행번호 +1
R5 Response까지 키 유지
```

Package UI는 예외 가능하나 문서화한다.

---

# 61. GUID 왕복 진행번호

개념:

| Step | 구간 | 진행번호 |
|---:|---|---:|
| 1 | Channel 출발 | 1 |
| 2 | Channel Integration | 2 |
| 3 | Information AP | 3 |
| 4 | Internal Integration | 4 |
| 5 | Business 복귀 | 5 |
| 6 | Channel Integration 복귀 | 6 |
| 7 | 최초 Channel 응답 | 7 |

토폴로지에 따라 단계 수는 달라질 수 있다.

핵심:

```text
Progress Sequence
= 단조 증가
```

---

# 62. GUID 값과 진행번호 구분

```text
GUID Value
→ 변경 금지

Progress Sequence
→ Node 경유 시 증가
```

동일 System 내부:

```text
Controller → Service → DAO
```

는 진행번호가 아니라 Span/Log로 추적한다.

---

# 63. GUID Gap

PDMG/TCF Source에서:

```text
진행번호 +1 자동 API
```

가 확인되지 않을 수 있다.

따라서:

```text
[GAP]
누가 +1 하는가?
MCA/MCI?
APIM/GSE Adapter?
Application Framework?
```

를 확정한다.

---

# 64. GUID 금지

```text
AP가 뒤늦게 새 GUID 생성
중간 Node GUID 교체
Package 예외 미기록
원거래 ID 손실
```

---

# 65. 8.4 캐릭터 셋

Baseline:

```text
Channel / Business AP
→ UTF-8

RDW / ADW
→ MS949

API G/W / GSE
→ 전사 Interface 기준
```

실제 DB NLS는 Runtime Evidence로 검증한다.

---

# 66. Charset Responsibility

| Flow | Source → Target | 변환 |
|---|---|---|
| Channel↔Business | UTF-8→UTF-8 | 없음 |
| Business→RDW/ADW | UTF-8→MS949 | DB Access Boundary |
| RDW/ADW→Business/BI | MS949→UTF-8 | DB/Solution Boundary |
| NSIGHT→API G/W/GSE | UTF-8→전사기준 | Integration Boundary |
| File/Batch | 계약별 | File/Batch Boundary |

---

# 67. Charset 검증 대상

```text
JVM Encoding
HTTP Content-Type
File Encoding
JDBC
DB NLS
ETL
FOS
```

설계와 Config가 다르면:

```text
[DRIFT]
```

---

# 68. 8.5~8.9

공식:

```text
XX
```

이므로 번호별 이름을 만들지 않는다.

다음은:

```text
[8.X 확장주제 후보]
```

로만 관리한다.

```text
거래 처리 구조
선/후 처리
호출 구조
도메인
거래 처리 경로
```

---

# 69. Domain / URL / 거래처리 경로

PPT Body에 있는:

```text
미니 싱글뷰
Marketing Platform
BI Portal
Business Solution
```

경로는 실제 값과 Source를 대조한다.

PPT URL:

```text
설계 기준
```

Source/운영 URL:

```text
AS-IS Evidence
```

로 구분한다.

---

# 70. 9. 아키텍처 구성 요소

핵심:

> **Framework가 제공하는 Routing/TX/Logging/공통 Mechanism을 업무 코드에 재구현하지 않는다.**

---

# 71. 9.1 단말 Framework

Client:

```text
xFrame / Information Terminal
```

표준 진입:

```text
/ins/{ServiceId}
```

Wire:

```text
xDataSet → Map
```

Context:

```text
ServiceContext
```

---

# 72. xDataSet 구조

Baseline 개념:

```text
header
  ├─ version
  └─ screen number

datasets[]
```

상세 스키마는 단말 표준을 따른다.

---

# 73. JSON 진입

```text
/nb/{ServiceId}
```

Wire:

```text
JSON → DTO
```

Context:

```text
DTO + hdr
```

---

# 74. 동일 ServiceId 합류

shall:

```text
/ins/{ServiceId}
        \
         → 동일 Biz Service
        /
/nb/{ServiceId}
```

단말용과 JSON용 Business Logic을 복제하지 않는다.

---

# 75. Standard Entry 금지

```text
임의 Servlet
비표준 URL
채널별 업무 복제
```

---

# 76. Framework vs Business Responsibility

Framework:

```text
ServiceId Routing
8-step Skeleton
System Pre/Post
Transaction Boundary Hook
Common Exception
Logging Hook
Context
```

Business:

```text
Domain Rule
DTO
Business Pre/Post
Business Service
DAO / Mapper
Business Message
```

---

# 77. Framework 재구현 금지

업무에서 금지:

```text
자체 ServiceId Router
자체 GUID Framework
자체 공통 TX Wrapper
자체 공통 Error Envelope
자체 Common Filter
```

---

# 78. PDMG AS-IS Reference

PDMG:

```text
pdmg-fw
  ├─ Filter
  ├─ Context
  ├─ TCF
  ├─ Timeout
  ├─ Transaction
  ├─ Error
  └─ Logging

pdmg-service
  ├─ Handler
  ├─ Facade
  ├─ Service
  └─ DAO
```

주의:

```text
pdmg-fw
≠ 독립 원격 Server
```

---

# 79. TCF ON / OFF Alignment

ON:

```text
Common Controller
→ TCF
→ Dispatcher
→ Handler
→ Business
```

OFF:

```text
Business MVC Controller
→ Business
```

ON/OFF가 Target Standard의 어떤 범위에 해당하는지는 ADR/GAP로 관리한다.

---

# 80. File Upload / Download Mechanism

Framework/Common Mechanism으로 재사용한다.

기본:

```text
Upload
→ FOS
→ Meta/Completion
→ Business Consume
```

완료 전 소비 금지.

---

# 81. RD Reporting

전용 Entry:

```text
/rd/{serviceId}
```

Contract:

```text
Input  = Map
Output = String
```

일반 JSON DTO 거래와 구분한다.

---

# 82. RD Runtime

```text
xFrame
 ↓ Map
/rd/{ServiceId}
 ↓
RD Controller
 ↓
Service / DAO
 ↓ Query
DB
 ↓
String
 ↓
xFrame
```

---

# 83. RD 금지

```text
/rd
→ /ins 일반 DTO 거래와 동일 취급
```

금지.

---

# 84. Inbound Mechanism

시스템 Inbound는 표준 진입과 Context/Error/Trace를 적용한다.

임의 Listener/Servlet를 업무마다 생성하지 않는다.

---

# 85. SSO Mechanism

9장 구성요소 기준:

```text
SSO
= 공통 Mechanism
```

설계서에서:

```text
사용 / 미사용 / 예외
```

중 하나를 반드시 명시한다.

---

# 86. SSO 계약

기존 구성요소 체크 기준에서:

```text
nonce
ticket
ssoId
```

계약을 관리한다.

상세 JWT/JWKS는 Security Architecture Evidence를 따른다.

---

# 87. SSO 금지

```text
우회 로그인 상시화
```

예외가 있다면:

```text
기간
승인
감사
Owner
```

필수.

---

# 88. Exception Mechanism

Framework 표준 예외구조를 사용한다.

기존 구성요소 기준:

```text
NhBaseException
ErrorProcess
```

등 표준 경로와 정합한다.

정확한 현재 PDMG Class는 Source Snapshot으로 재검증한다.

---

# 89. Error Contract

구분:

```text
System Error
Business Error
Validation Error
Authorization Error
Timeout
Overload
```

모든 실패를 하나의 “Error”로 뭉개지 않는다.

---

# 90. Transaction Log

필수 상관키:

```text
GUID
Original Transaction Global ID
ServiceId
Result
Timing
```

상세 Column은 Log 표준을 따른다.

---

# 91. 9.3 Batch Framework

4계층:

```text
1. Orchestration
2. Execution
3. State
4. Business Data
```

---

# 92. Batch 전체 Text Architecture

```text
Administrator
   ↓
Automation / Scheduler
   ↓
Control-M
   ↓
Agent
   ↓
Shell
   ↓
JobLauncher
   ↓
Job
   ↓
Step
   ├─ Chunk
   └─ Tasklet
   ↓
JobRepository
   +
Business DB
   ↓
Exit Code / Monitoring
```

---

# 93. Batch Orchestration

책임:

```text
Schedule
Dependency
Start
Monitoring
Rerun Control
```

Baseline:

```text
Control-M
```

운영 Batch가 Application 내부 Scheduler만으로 동작하는 구조는 금지.

---

# 94. Batch Execution

책임:

```text
Job
Step
Chunk
Tasklet
Commit
Restart
```

---

# 95. JobRepository

정의:

```text
Batch Execution State Store
```

원칙:

```text
JobRepository
≠ Business Ledger DB
```

---

# 96. Batch Data

Business DB는 업무 원장/처리 Data.

Batch 실행상태와 혼합하지 않는다.

---

# 97. Batch 금지

```text
Online WAS 장기 Batch
App 내부 Scheduler만으로 운영
중복 적재 가능한 재실행
초대형 단일 TX
업무일자=System Date 하드코딩
Checkpoint 없음
Control-M 없는 운영 Batch
```

---

# 98. Batch Restart

필수:

```text
Checkpoint
Idempotency
Restart Point
Exit Code
Owner
```

---

# 99. Delivery / Framework Boundary

구성요소 자료에서는:

```text
GitLab
Runner
NEXUS
```

등 통합개발환경 개념이 존재한다.

원칙:

```text
표준 Pipeline 산출물만 운영 투입
```

실제 PDMG CI/CD 구현 완료 여부는 별도 Evidence로 검증한다.

---

# 100. 수동 배포 금지

```text
Pipeline 없는 수동 운영배포
```

는 표준 미충족.

운영 Tool의 실제 적용상태는 DevOps Evidence를 따른다.

---

# 101. 상용 / Master Solution 경계

Framework가 자체 구현할 기능과 상용 Solution이 담당할 기능을 중복 구현하지 않는다.

필수:

```text
Responsibility Delegation Matrix
```

---

# 102. 10. 업무 솔루션 아키텍처

[PPT-CONTENT-GAP]

공식:

```text
10.1 SELF-BI
10.2 OLAP
10.3 EBM
10.4 데이터 흐름
```

본 장에서는 기존 정의서를:

```text
[SUPPLEMENTAL-DEFINITION]
```

으로 사용한다.

---

# 103. 업무솔루션 핵심 원칙

> **“솔루션이므로 Architecture 표준 제외”를 금지한다.**

모든 Solution은:

```text
소속
IF
Runtime
Data Ownership
Authentication
Standard Message Exception
```

을 정의한다.

---

# 104. Solution 소속 트리

```text
Business Solution
│
├─ BI
│   ├─ OLAP
│   ├─ Self-BI
│   ├─ Credit Performance
│   └─ Portal
│
├─ MP
│   ├─ Campaign
│   ├─ Offer
│   └─ EBM
│
├─ RD/AD
│   ├─ Mart
│   └─ Load / CDC/ETL result
│
├─ DG
│   ├─ Metadata
│   ├─ Quality
│   └─ Data Flow
│
└─ IM
    └─ IT Support Tools
```

---

# 105. Solution 전용 Zone 금지

소속 불명확 시:

```text
01 Classification
+
03 Logical
```

에 재매핑한다.

임의:

```text
Solution Zone
```

신설 금지.

---

# 106. Solution 필수 Header

must:

```text
Solution Name
System Group
UI Type
IF Type / Medium / Standard Table No.
Runtime Primary / Secondary #
Data Ownership
Authentication / SSO
Standard Message / Direct Exception
Layered Architecture Reference
PPT Gap Flag
```

---

# 107. Solution Header Template

```text
솔루션명:
소속 시스템 그룹: BI | MP | RD/AD | DG | IM
UI 유형: Package UI | Portal | Terminal | None
IF 유형/매체/표준표 No.:
런타임 주/보조 #:
데이터 소유:
인증: SSO 사용 | 미사용
표준전문/Direct 예외:
Layered MD:
PPT Gap:
```

공란/“추후”만 있으면 Review 미통과.

---

# 108. SELF-BI

소속:

```text
BI
```

주 Runtime:

```text
#10 Analysis / Provision
```

상황에 따라:

```text
#9 ETL
```

보조.

Package Direct이면 예외를 명시한다.

---

# 109. OLAP

소속:

```text
BI
```

Data:

```text
RDW / ADW
```

접근은 DB Access Matrix/BI Contract 범위.

---

# 110. EBM

소속:

```text
MP
```

주 Runtime:

```text
#7 Offering / Customer Notification
```

Event 수집:

```text
#6
```

과 분리.

---

# 111. EBM #6 / #7 분리

```text
#6
Customer/Event Collection

#7
Offer / UMS / Action
```

“모두 Event”로 한 Sequence에 합치지 않는다.

---

# 112. Business Solution Data Flow

상위:

```text
Source
 ↓
RDW / ADW
 ↓
Solution / Analysis
 ↓
Offer / Report / Dashboard
 ↓
Feedback
```

정확한 10.4 상세는 PPT/솔루션별 설계자료 추가 확보 필요.

---

# 113. Package UI

정의:

```text
상용/패키지 화면
```

표준전문 예외 가능.

예외 가능:

```text
≠ Architecture 제외
```

---

# 114. Package UI Direct

필수:

```text
Direct Standard Row
Exception Reason
Authentication
GUID/Trace
Error
Runtime Type
Owner
```

---

# 115. 정보단말 Package 구조

기존 Supplement 기준:

```text
webapp/
├─ _wpack_
├─ cm/
├─ websquare/
└─ webTop/
    ├─ cm/
    ├─ layout/
    └─ screen/{대구분}/{업무}/{분류}/{화면ID}.xml
```

`_wpack_` 빌드 산출물 직접 수정 금지.

---

# 116. 화면 명명

개념:

```text
[대구분][업무][분류][일련][유형].xml
```

정확한 코드/경로는 화면표준 SSOT 사용.

---

# 117. 신용실적 Package

Supplemental Baseline:

```text
소속 = BI
Runtime = #10 (+ #9)
Direct Exception = 가능/명시
```

기존 전용 Interface 분석을 재사용한다.

---

# 118. RD Solution Contract

RD:

```text
/rd/{serviceId}
Map → String
```

일반 Transaction 진입과 혼용 금지.

---

# 119. 비즈메타 Solution

소속:

```text
DG
```

책임:

```text
Metadata
```

업무 Data 원장을 직접 소유하지 않는다.

---

# 120. Data Ownership — Solution

모든 Solution은:

```text
Own
Read
Write
Feedback
```

을 구분한다.

---

# 121. Solution Security

필수:

```text
SSO
Role
Masking
Data Access
Audit
```

SSO 우회 상시화 금지.

---

# 122. Solution Direct 금지

Direct 예외를:

```text
일반 API
일반 Channel
```

에 확장하지 않는다.

---

# 123. Interface ↔ Runtime Mapping

| Mechanism | Runtime # |
|---|---|
| MCA/MCI/Direct | #1~#3 |
| APIM Internal | #4 |
| External/GSE | #5 |
| Event Collection | #6 |
| Offer/UMS | #7 |
| CDC | #8 |
| ETL | #9 |
| Analysis | #10 |
| File/FOS | #11 |
| Batch | #12 |

---

# 124. Message ↔ Runtime Mapping

```text
Online
→ Standard Message + GUID

File
→ File Contract + Charset + Completion Signal

CDC/ETL
→ Data Contract / Checkpoint

Batch
→ Job/Step/Status
```

---

# 125. Traceability Key

Mechanism에서 관리해야 할 주요 키:

```text
ServiceId
Interface ID
GUID
Original Transaction Global ID
File ID
Job ID
Event ID
```

Runtime별로 하나 이상의 대표 Correlation Key를 가져야 한다.

---

# 126. ServiceId Trace

```text
Business Classification
  ↓
Function
  ↓
ServiceId
  ↓
Controller / Handler
  ↓
Facade / Service
  ↓
DAO / Mapper / SQL
```

PDMG AS-IS에서는 Handler Registry를 포함한다.

---

# 127. Standard Message Trace

```text
Channel
  ↓
Header/GUID
  ↓
MCA/MCI/API
  ↓
Application
  ↓
Result/Error
  ↓
Response
```

---

# 128. File Trace

```text
Interface ID
  ↓
Filename
  ↓
Manifest
  ↓
Hash
  ↓
Completion Signal
  ↓
Consumer
```

---

# 129. Batch Trace

```text
Schedule ID
  ↓
Control-M
  ↓
Job
  ↓
JobInstance
  ↓
Step
  ↓
Business Data
  ↓
Exit Code
```

---

# 130. Security Mechanism

Cross-cutting:

```text
Authentication
Authorization
SSO
JWT / Session
Encryption
Masking
Audit
```

본 장에서는 **Mechanism 책임**까지만 정의하고 실제 Runtime Failure는 06으로 넘긴다.

---

# 131. Trust Boundary 재검증

Interface Boundary 통과 시:

```text
Identity
Authorization
Message
Trace
```

검증.

---

# 132. Timeout / Retry / Security 관계

Retry 시:

```text
Token Expired
Authorization Failure
Business Reject
```

를 Transient Error처럼 재시도하지 않는다.

---

# 133. Exception Taxonomy

최소:

```text
Validation
Business
Authentication
Authorization
Technical
Timeout
Overload
External
Data
```

로 구분한다.

---

# 134. Common Error Response

표준 전문의 Result/Error 블록과 정합한다.

PDMG 현재 Error Envelope와 Target 전문의 차이는 별도 Alignment로 기록한다.

---

# 135. Logging Mechanism

필수:

```text
GUID
ServiceId
Result
Duration
Error Code
```

민감정보 Log 금지.

---

# 136. Framework Responsibility Matrix

| 영역 | Framework | Business |
|---|---:|---:|
| ServiceId Routing | ● | 등록/사용 |
| 8단계 Skeleton | ● | 해당 Hook 구현 |
| System Pre/Post | ● |  |
| Business Pre/Post | Hook | ● |
| TX Control Hook | ● | 참여 |
| Error Envelope | ● | Business Error 발생 |
| Logging | Skeleton | Business Context |
| Domain Rule |  | ● |
| SQL |  | ● |

---

# 137. Interface Responsibility Matrix

| Mechanism | Source | IF Owner | Target | Key Contract |
|---|---|---|---|---|
| MCA | Channel | Channel | App | Message/GUID |
| APIM | App | Integration | App/System | API Contract |
| FOS | Sender | File | Receiver | Completion/Hash |
| CDC | Source DB | Data | RDW | Change/Checkpoint |
| ETL | Source | Data/Batch | Target | Dataset/Restart |

Owner 조직명은 RACI에서 확정한다.

---

# 138. Batch Responsibility Matrix

| Layer | Responsibility |
|---|---|
| Control-M | Schedule/Dependency/Monitor |
| Agent/Shell | OS Entry |
| Batch FW | Job/Step Execution |
| JobRepository | State |
| Business DB | Business Data |

---

# 139. Solution Responsibility Matrix

| Solution Type | System Group | Primary Concern |
|---|---|---|
| Self-BI | BI | Analysis |
| OLAP | BI | Multidimensional Analysis |
| EBM | MP | Offer/Event |
| Metadata Tool | DG | Metadata |
| Data Load | RD/AD | Data |

---

# 140. Part B — 인터페이스 준수 규범

shall:

```text
표준표 행 선택
```

표준표에 없는 상시 Route:

```text
ADR / 표준개정
```

전 사용 금지.

---

# 141. Interface Contract must

모든 신규/변경 IF:

```text
Interface ID
Source
Target
Type
Standard No.
Runtime #
Message/Schema
Timeout
Retry
Idempotency
SLA
RACI
Trace
Security
```

---

# 142. Direct must

Direct는:

```text
Exception
+
책임 강화
```

다.

---

# 143. File must

```text
Completion Signal
Hash
Duplicate Prevention
Retention
RACI
```

---

# 144. CDC must

```text
Checkpoint
Lag
Recovery
```

Runtime에서 Evidence 검증.

---

# 145. ETL must

```text
Window
Restart
Count/Reconciliation
Owner
```

---

# 146. 8단계 준수

필수:

```text
1
4
8
```

선택:

```text
2
3
6
7
```

업무가 1/8 Framework 경계를 대체하지 않는다.

---

# 147. ServiceId 준수

신규 Online Service:

```text
/ins
or
/nb
+
ServiceId
```

표준 Entry 사용.

---

# 148. 동일 Biz 합류

단말/JSON:

```text
같은 ServiceId
→ 같은 Business Service
```

---

# 149. GUID 준수

must:

```text
채널 최초 생성
Node +1
원거래 ID 유지
Response까지 유지
```

---

# 150. Charset 준수

```text
채널/AP UTF-8
RDW/ADW MS949
```

설계 기준과 Runtime NLS를 대조한다.

---

# 151. Framework 준수

Framework 제공 기능을 업무에서 재구현 금지.

---

# 152. Batch 준수

운영 Batch:

```text
Control-M 중앙 통제
```

---

# 153. Solution 준수

솔루션:

```text
필수 Header 전항목 기입
```

공란/추후만 있으면 Review 미통과.

---

# 154. 표준 예외

예외는:

```text
Reason
Owner
Approval
Expiry
Monitoring
Fallback
ADR
```

을 가진다.

---

# 155. 금지 패턴 전체

```text
모든 연계 REST
Direct 일반화
Channel→DB
Application→타 DAO
업무 자체 Service Router
업무 자체 Common TX Wrapper
업무 자체 GUID Framework
Filter에서 업무 Rule
완료 전 File 소비
CDC에 Batch 혼재
ETL을 실시간처럼 사용
Controller→DAO
Control-M 없는 운영 Batch
JobRepository=원장 DB
수동 운영배포
Solution=Architecture 제외
SSO 우회 상시화
PPT 8.5~8.9 임의 공식명 부여
10장 Gap 숨김
```

---

# 156. Mechanism Inventory

관리 대상:

```text
Interface Inventory
ServiceId Inventory
Standard Message Version
GUID Rule
Charset Rule
Framework Feature Inventory
Common Mechanism Inventory
Batch Job Inventory
Solution Architecture Header
Exception Register
```

---

# 157. Interface Inventory Template

| IF ID | Type | No. | Source | Mechanism | Target | Runtime | Owner | Status |
|---|---|---:|---|---|---|---|---|---|
| TBD | Online | 1 | 통합업무 | MCA | 정보계 | #2 | TBD | Open |
| TBD | File | 3 | 정보계 | FOS | 대내 | #11 | TBD | Open |
| TBD | Data | TBD | Core | CDC | RDW | #8 | Data | Baseline |

---

# 158. ServiceId Inventory Template

| ServiceId | App | Function | Entry | Controller/Handler | Service | Data | Runtime | Status |
|---|---|---|---|---|---|---|---|---|
| TBD | MP | TBD | /ins,/nb | TBD | TBD | RDW | #1/#2 | Open |

---

# 159. Standard Message Inventory

| Version | Applies To | Format | Header Schema | Result Schema | Owner | Status |
|---|---|---|---|---|---|---|
| TBD | Online | FLAT/JSON | TBD | TBD | Architecture/FW | Open |

---

# 160. GUID Inventory

| Rule | Owner | Boundary | Implementation | Evidence | Status |
|---|---|---|---|---|---|
| R1 | Architecture | Node | TBD | Standard | Baseline |
| R2 | Channel | Entry | TBD | Standard | Baseline |
| R4 | TBD | Node | GAP | Source check | Gap |

---

# 161. Common Mechanism Inventory

| Mechanism | FW Provided | Business Usage | Exception | Evidence |
|---|---:|---|---|---|
| Upload | Y | Use | TBD | 09 |
| Download | Y | Use | TBD | 09 |
| RD | Y | Use | Dedicated Entry | 09/10 |
| Inbound | Y | Use | TBD | 09 |
| SSO | Y/Architecture | Use | Approved only | 09 |
| Exception | Y | Throw/Map | No reimplementation | 09 |
| Tx Log | Y | Context | No duplicate | 09 |

---

# 162. Batch Inventory Template

| Job ID | Schedule | Control-M | Job | Step | JobRepository | Data | Restart | Owner |
|---|---|---|---|---|---|---|---|---|
| TBD | TBD | Y | TBD | TBD | TBD | TBD | Y | TBD |

---

# 163. Solution Header Inventory

| Solution | Group | UI | IF | Runtime | Data | SSO | Direct Exception | Gap |
|---|---|---|---|---|---|---|---|---|
| Self-BI | BI | Package/Portal | TBD | #10 | RD/AD | TBD | TBD | 10장 |
| OLAP | BI | TBD | TBD | #10 | AD | TBD | TBD | 10장 |
| EBM | MP | None/Portal | Event | #7/#6 | MP/RD | TBD | N/A | 10장 |

---

# 164. NFR → MECHANISM Mapping

| NFR | Mechanism |
|---|---|
| Performance | 목적별 IF, SYNC/ASYNC, Timeout |
| Availability | Retry/Replay/Completion/Batch Restart |
| Scalability | Async/Event/Batch 분리 |
| Security | Auth/SSO/Contract/Charset/Data Access |
| Observability | GUID/ServiceId/IF ID/Log |
| Integrity | Idempotency/Hash/Reconciliation |

---

# 165. Failure Design Handoff

MECHANISM에서 정의:

```text
어떤 Failure를
어떤 Error Category로
어떤 Retry/Compensation 정책으로
처리할 것인가
```

RUNTIME에서 검증:

```text
실제 발생 순서
Thread/TX 상태
Metric
Recovery
```

---

# 166. AS-IS vs TO-BE

예:

```text
[PPT TARGET]
ServiceId ↔ Controller 1:1

[PDMG AS-IS]
ServiceId → Dispatcher → Handler
```

이 차이를 숨기지 않는다.

---

# 167. PDMG Alignment 대상

```text
Entry
ServiceId
Framework Routing
Transaction Skeleton
Message
GUID
Error
Logging
SSO
```

---

# 168. PDMG를 그대로 Target으로 승격하지 않는 영역

```text
현재 Timeout 값
Worker Pool
현재 Class 구조
현재 ON/OFF Mode
현재 URL
```

이들은 AS-IS Evidence다.

---

# 169. GAP Register

| ID | GAP | 영향 | 조치 |
|---|---|---|---|
| GAP-MC-01 | 8.5~8.9 공식 제목 XX | PPT 정합 | 공식 개정 전 유지 |
| GAP-MC-02 | 8장 Duplicate | SSOT | 1벌 Baseline |
| GAP-MC-03 | GUID +1 Owner | E2E Trace | FW/Adapter 결정 |
| GAP-MC-04 | Charset Runtime NLS | 데이터 오류 | Config 검증 |
| GAP-MC-05 | Interface 전수 ID/RACI | 운영 | Inventory |
| GAP-MC-06 | 10장 PPT 상세 부족 | Solution 설계 | Supplemental 유지 |
| GAP-MC-07 | PDMG Controller/Handler Alignment | 표준화 | ADR |
| GAP-MC-08 | ON/OFF 동일 TX/Use Case 경계 | Runtime | 06 검증 |
| GAP-MC-09 | SSO/JWT 전사 적용범위 | Security | ADR |
| GAP-MC-10 | Standard Message 상세 Field SSOT | 개발 | Field 정의서 |

---

# 170. ADR 후보

| ADR | 주제 |
|---|---|
| ADR-MC-01 | ServiceId Target Entry Model |
| ADR-MC-02 | GUID Progress Owner |
| ADR-MC-03 | Direct Exception Policy |
| ADR-MC-04 | Standard Message Versioning |
| ADR-MC-05 | Retry/Idempotency |
| ADR-MC-06 | Package UI Exception |
| ADR-MC-07 | Batch Orchestration |
| ADR-MC-08 | Solution SSO |
| ADR-MC-09 | PDMG ON/OFF Alignment |
| ADR-MC-10 | Charset Conversion Owner |

---

# 171. Verification Checklist — 6장

```text
[ ] 온라인 No.1~9가 기준선과 일치하는가
[ ] 파일 No.1~6이 기준선과 일치하는가
[ ] FOS Completion Signal이 있는가
[ ] CDC와 ETL을 구분했는가
[ ] Direct는 예외로 관리되는가
[ ] Interface Contract가 완비되는가
[ ] Runtime #와 매핑되는가
```

---

# 172. Verification Checklist — 8장

```text
[ ] 8.5~8.9를 임의 명명하지 않았는가
[ ] Duplicate를 한 SSOT로 관리하는가
[ ] 거래 8단계 필수 1/4/8을 유지하는가
[ ] ServiceId/계층 호출 규칙이 있는가
[ ] FLAT/JSON을 동일 논리전문 표현으로 보는가
[ ] GUID R1~R5가 있는가
[ ] Charset UTF-8/MS949 기준이 있는가
```

---

# 173. Verification Checklist — 9장

```text
[ ] /ins/{ServiceId}가 있는가
[ ] /nb/{ServiceId}가 있는가
[ ] 동일 ServiceId가 동일 Business로 합류하는가
[ ] Framework 책임 재구현이 없는가
[ ] Upload/RD/Inbound/SSO/Error/Log 사용여부가 명시되는가
[ ] /rd/{ServiceId}를 일반 거래와 분리했는가
[ ] Control-M Batch 4계층이 정의되는가
[ ] JobRepository와 Business DB를 구분하는가
```

---

# 174. Verification Checklist — 10장

```text
[ ] PPT Content Gap을 명시했는가
[ ] Solution 필수 Header가 존재하는가
[ ] BI/MP/RD·AD/DG/IM 소속을 지정했는가
[ ] Runtime 주/보조 #가 있는가
[ ] Data Ownership이 있는가
[ ] SSO/Direct 예외가 있는가
[ ] Solution 전용 Zone을 만들지 않았는가
```

---

# 175. Mechanism Completion Gate

```text
G-MC-01 Interface Standard
G-MC-02 Interface Contract
G-MC-03 Transaction 8 Steps
G-MC-04 Application Call Rule
G-MC-05 Standard Message
G-MC-06 GUID
G-MC-07 Charset
G-MC-08 Standard Entry
G-MC-09 Framework Responsibility
G-MC-10 Common Mechanism
G-MC-11 Batch
G-MC-12 Solution Header
G-MC-13 Exception Register
G-MC-14 GAP / ADR
```

---

# 176. RUNTIME Handoff

MECHANISM Output:

```text
Interface Type
Standard Route
Interface ID
ServiceId
Message
GUID
Charset
Transaction Skeleton
Framework Entry
Common Mechanism
Batch Contract
Solution Header
Retry / Idempotency Policy
```

RUNTIME에서 확정:

```text
Actual Sequence
Thread
Transaction
Timeout Timing
Error Timing
Retry Execution
Event Lag
CDC Lag
File Completion
Batch Restart
Monitoring
HA/DR
Evidence
```

---

# 177. MECHANISM → RUNTIME 연결

```text
[HOW]
MCA / API / FOS / CDC / ETL / Framework / Batch
        ↓
[WHEN & ORDER]
실제 Request / Event / File / Job Sequence
        ↓
[PROVE]
Metric / Log / Trace / Recovery
```

---

# 178. 최종 평가

MECHANISM Architecture의 완료상태는 다음과 같다.

```text
하나의 신규 거래/연계를 보았을 때

어떤 Interface 표준행을 쓰는가?
어떤 ServiceId/Interface ID인가?
어떤 Entry로 들어오는가?
어떤 8단계를 거치는가?
어떤 Message를 쓰는가?
GUID는 어디서 생성/증가하는가?
Charset 변환은 누가 하는가?
Framework와 Business 책임은 무엇인가?
Retry/Idempotency는 무엇인가?
Batch라면 누가 Scheduling하는가?
Solution이면 어느 Group/Runtime/Data/SSO인가?

를 설계서 한 벌로 설명할 수 있어야 한다.
```

최종 핵심 선언:

> **Mechanism Architecture는 개별 기술 사용법이 아니라 모든 거래·연계·배치·솔루션이 지켜야 할 실행 계약이며, 그 계약은 Interface 표준표·8단계·전문·GUID·Framework·Batch·Solution Header로 구체화되고 다음 Runtime 장에서 실제 실행증거로 검증된다.**

---

# Appendix A. Online IF Summary

```text
1 통합업무 → MCA → 정보계
2 비대면 → MCI → 정보계
3 정보단말 → Direct → 정보계
4 Package UI → Direct → Solution [EXCEPTION]
5~6 정보계 ↔ Cruz APIM ↔ 대내
7~8 정보계 ↔ API G/W ↔ 대외
9 타법인 → GSE → 정보계
```

---

# Appendix B. File IF Summary

```text
1 정보계 단말 → FOS → 정보계
2 Package UI → FOS → Solution
3 정보계 → FOS → 대내
4 대내 → FOS → 정보계
5 정보계 → FOS+대외MCA → 대외
6 대외 → FOS+대외MCA → 정보계
```

---

# Appendix C. Transaction 8 Steps

```text
1 System Pre       MUST
2 Common Pre
3 Business Pre
4 Controller       MUST
5 Business Service
6 Business Post
7 Common Post
8 System Post      MUST
```

---

# Appendix D. GUID Rules

```text
R1 Node ID in Standard Message
R2 Channel creates
R3 E2E by Original Global ID
R4 Progress +1 each Node
R5 Preserve through Response
```

---

# Appendix E. Charset

```text
Channel/AP = UTF-8
RDW/ADW    = MS949
API G/W/GSE = Enterprise IF Standard
```

---

# Appendix F. Entry

```text
/ins/{ServiceId}
xDataSet → Map
ServiceContext

/nb/{ServiceId}
JSON → DTO
DTO + hdr

/rd/{ServiceId}
Map → String
```

---

# Appendix G. Batch

```text
Control-M
→ Agent
→ Shell
→ JobLauncher
→ Job
→ Step
→ JobRepository
+ Business DB
```

---

# Appendix H. 본 장에서 임의 확정하지 않는 것

```text
실제 모든 ServiceId
실제 모든 Interface ID
실제 Standard Message 상세 Field
GUID 진행번호 +1 구현 Owner
모든 Timeout 초
모든 Retry 횟수
모든 Solution 제품명
모든 SSO IdP 상세
PPT 8.5~8.9 공식 명칭
PPT 10장 미작성 상세
```
