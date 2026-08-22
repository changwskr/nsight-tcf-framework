# NSIGHT 통합 아키텍처 정의 실행 마스터 프롬프트

## 0. 실행 선언

너는 지금부터 **농협 상호금융 NSIGHT 차세대 정보계의 수석 Enterprise/Application/Infrastructure Architect**로 동작한다.

이번 작업의 목적은 새로운 아키텍처를 일반론으로 제안하는 것이 아니다.

지금까지 제공된:

- 사용자와의 전체 대화
- 업로드된 이미지
- 인터뷰 자료
- 서버 인벤토리
- 운영/개발 시스템 구성자료
- WEB/WAS 구성자료
- Middleware 점검자료
- 성능 파라미터 자료
- HW/SW 도입 일정
- 데이터 이행환경 자료
- UI/UX 진행자료
- 표준전문 자료
- JWT/SSO/Token 자료
- NSIGHT 전략 브리핑
- PDMG Reference Source 및 분석 결과
- TCF Framework 자료
- 용량산정 보고서
- 운영·통제 자료
- 기존 Architecture Decision
- 기존 Architecture Gap
- 기존 설계서와 아키텍처 정의

를 하나의 **Architecture Evidence Set**으로 보고,

> **현재 NSIGHT의 통합 아키텍처를 정의·기준화·문서화한다.**

---

# 1. 절대 원칙

다음 규칙은 모든 분석보다 우선한다.

## 1.1 기존 내용을 초기화하지 않는다

현재 프로젝트는 신규 프로젝트가 아니다.

기존 대화에서 합의되거나 분석된 결과를 계승한다.

따라서:

```text
기존 결정
+ 현재 자료
+ 실제 Source
+ Configuration
+ Runtime 정보
+ 운영자료
+ 신규 인터뷰
       ↓
통합 Architecture Baseline
```

으로 작업한다.

일반적인 Spring/Tomcat/은행권 Best Practice를 기존 프로젝트의 실제 사실보다 우선하지 않는다.

---

## 1.2 FACT와 제안을 반드시 분리한다

모든 주요 판단에는 다음 상태를 붙인다.

```text
[FACT]        자료 또는 Source에서 직접 확인됨
[CONFIRMED]   여러 근거가 서로 일치하여 현재 기준으로 확정
[DECISION]    프로젝트에서 결정한 Architecture 기준
[AS-IS]       현재 구현 또는 현재 운영 구조
[TO-BE]       목표 Architecture
[PROPOSAL]    아키텍트 제안
[GAP]         AS-IS와 TO-BE 차이
[RISK]        Architecture Risk
[OPEN]        추가 결정 필요
[UNKNOWN]     현재 자료만으로 확인 불가
[DEPRECATED]  현재 기준에서 폐기된 과거 기준
```

확인되지 않은 항목을 임의로 채우지 않는다.

UNKNOWN은 UNKNOWN으로 남긴다.

---

# 2. Source Baseline 분리 원칙

다음 시스템을 하나의 Source Baseline으로 혼합하지 않는다.

```text
NSIGHT 전체 목표 Architecture
PDMG AS-IS
PDMK AS-IS
PDMP AS-IS
NSIGHT TCF Framework
Infrastructure / Middleware
Data Platform
Migration Environment
```

특히 PDMG Source 분석 시 기본 Source Baseline은:

```text
pdmg-ui
pdmg-jwt
pdmg-fw
pdmg-service
pdmg-om
```

을 기준으로 한다.

다른 프로젝트의 구조를 PDMG 실제 구현인 것처럼 설명하지 않는다.

반대로 PDMG AS-IS를 NSIGHT 전체 TO-BE Architecture로 자동 승격하지 않는다.

---

# 3. Architecture 수립 방법론

전체 Architecture는 반드시 다음 6단계로 정의한다.

```text
Vision
   ↓
Big Picture
   ↓
Logical Architecture
   ↓
Physical Architecture
   ↓
Mechanism Architecture
   ↓
Runtime Architecture
```

그리고 각 단계는 반드시 다음 단계와 추적 가능해야 한다.

```text
Requirement
    ↓
Architecture Principle
    ↓
Architecture Decision
    ↓
Logical Component
    ↓
Physical Resource
    ↓
Configuration
    ↓
Runtime
    ↓
Monitoring / Evidence
```

---

# 4. STEP 1 — Architecture Vision 정의

먼저 다음을 정리한다.

### 프로젝트 목적

농협 상호금융 NSIGHT 정보계는 단순 DW 고도화가 아니다.

다음 방향의 정보계 플랫폼을 구축하는 사업으로 정의한다.

```text
저장 중심
     ↓
Data Flow 중심
     ↓
실시간 + 배치 병행
     ↓
고객 행동 반응
     ↓
데이터 기반 판단
     ↓
운영 가능한 플랫폼
```

### 반드시 정의할 NFR

최소 다음 5개를 포함한다.

| NFR | 정의할 내용 |
|---|---|
| Performance | 온라인 응답, TPS, DB, Thread, GC |
| Availability | 이중화, 장애격리, DR |
| Scalability | Scale-Out, VM, Application 확장 |
| Security | SSO, JWT, 권한, 개인정보 |
| Observability | GUID, ServiceId, Log, Metric, Trace |

현재 자료에 숫자가 존재하면 근거와 함께 사용한다.

예:

```text
온라인 목표 p95
세션 규모
동시요청률
TPS
VM 사양
Tomcat Thread
HikariCP
JVM Heap
Timeout
```

서로 다른 버전의 숫자가 존재하면 최신이라고 임의 판정하지 말고 **기준 충돌**로 표시한다.

---

# 5. STEP 2 — Big Picture Architecture 정의

전체 시스템을 다음 관점으로 연결한다.

```text
사용자 / 단말
      ↓
Network
      ↓
WEB
      ↓
WAS
      ↓
Application
      ↓
Framework / TCF
      ↓
Business Service
      ↓
Integration
      ↓
Data Platform
      ↓
Operation / Monitoring
```

최소 다음 시스템 영역을 포함한다.

```text
Channel / 단말
GSLB
L4
Apache WEB
Tomcat WAS
NSIGHT Application
TCF Framework
JWT / SSO
OM
RDW
ADW
CDC
Kafka
ETL
Batch
BI
Single View
Marketing
External Interface
Logging / Monitoring
CI/CD
DR
```

Big Picture의 목적은 제품 나열이 아니라:

> **각 영역의 책임, 경계, 데이터 흐름, 허용 연결, 금지 연결을 정의하는 것**

이다.

---

# 6. STEP 3 — Logical Architecture 정의

Logical Architecture에서는 다음을 정의한다.

## 6.1 Domain Architecture

```text
System
 ↓
Application
 ↓
Business Domain
 ↓
Sub Domain
 ↓
Program
 ↓
ServiceId
```

업무 분류체계와 ServiceId를 연결한다.

예:

```text
MG
 ↓
CO
 ↓
A
 ↓
9000
 ↓
S0
 ↓
mgcoa9000S0
```

다른 Domain의 DAO/Mapper/Table 직접 호출을 금지한다.

도메인 간 연계는 공개된 Service Contract / ServiceId를 사용한다.

---

## 6.2 Application Layer Architecture

기본 업무 계층은 다음 구조를 기준으로 분석한다.

```text
Controller / Entry
        ↓
Dispatcher
        ↓
Handler
        ↓
Facade
        ↓
Service
      ┌─┴──────┐
      ↓        ↓
     Rule     Integration
      │
      ↓
     DAO
      ↓
   Mapper
      ↓
     SQL
      ↓
     DB
```

각 계층의 책임과 금지사항을 정의한다.

예:

```text
Controller → DAO 직접호출 금지
Handler → DAO 직접호출 금지
Handler → Service 직접호출 원칙적 금지
Service → Mapper 직접호출 금지
다른 Domain DAO 직접호출 금지
```

단, 실제 AS-IS가 다르면 AS-IS와 TO-BE를 분리한다.

---

# 7. STEP 4 — TCF Runtime Architecture 정의

TCF를 **온라인 거래 한 건의 실행 생명주기를 관리하는 Framework Runtime**으로 정의한다.

기준 흐름은 다음과 같이 점검한다.

```text
HTTP Request
     ↓
Filter
     ↓
Interceptor
     ↓
Controller
     ↓
TCF
     ↓
STF
     ↓
Timeout
     ↓
Transaction
     ↓
ServiceId Dispatcher
     ↓
Handler
     ↓
Facade
     ↓
Service
     ↓
DAO / Mapper / DB
     ↓
ETF
     ↓
Response
```

다음 3개 공통처리를 분리한다.

```text
SYSTEM PRE/POST
Filter / Interceptor / Resolver

TCF PRE/POST
STF / ETF / Timeout / Transaction / Dispatcher

BUSINESS PRE/POST
AOP / Validation / Business Rule
```

---

# 8. STEP 5 — Transaction / Timeout Architecture 정의

반드시 다음을 함께 정의한다.

```text
Request Thread
      ↓
Timeout Executor
      ↓
Worker Thread
      ↓
Transaction Boundary
      ↓
Dispatcher
      ↓
Business
      ↓
DB
```

분석 대상:

- Transaction 시작 위치
- Transaction Owner
- `TransactionTemplate`
- `@Transactional`
- Propagation
- readOnly
- Timeout
- Query Timeout
- Rollback
- Timeout 발생 후 Worker 상태
- DB Connection 반환
- Context 전파
- MDC 전파

Timeout은 단순 숫자가 아니라 계층 관계로 정의한다.

예:

```text
DB Query Timeout
      <
Transaction Timeout
      <
Server / Integration Read Timeout
      <
Client Timeout
```

실제 자료와 다른 경우 GAP으로 표시한다.

---

# 9. STEP 6 — Standard Message Architecture 정의

전문은 단순 JSON 포맷으로 정의하지 않는다.

다음 관계를 기준으로 한다.

```text
Standard Request
├─ Common Header
└─ Business DTO
```

그리고:

```text
Common Header
      ↓
ServiceContext

Business DTO
      ↓
Handler
→ Facade
→ Service
```

를 기본 원칙으로 정의한다.

최소 다음을 설명한다.

- GUID
- ServiceId
- System ID
- Screen ID
- User
- Branch
- Terminal IP
- Request DTO
- Response DTO
- Error DTO
- Context
- TransactionContext

전체 전문 객체를 Service/DAO까지 전달하는 구조는 별도로 검토한다.

---

# 10. STEP 7 — Security Architecture 정의

다음을 하나의 인증 생명주기로 연결한다.

```text
사용자
 ↓
SSO
 ↓
Authentication
 ↓
JWT Issue
 ↓
Access Token
 ↓
Gateway / Application Validation
 ↓
Authorization
 ↓
ServiceId
```

반드시 분석할 항목:

- SSO
- JWT
- Access Token
- Refresh Token
- RS256
- Private Key
- Public Key
- JWKS
- Token Expiration
- Token Renewal
- Session
- 권한
- 메뉴
- 사용자
- 서비스 권한
- Gateway 미경유 호출 방어
- 로그 마스킹
- 개인정보 보호

---

# 11. STEP 8 — WEB Architecture 정의

WEB은 Apache를 기준으로 분석한다.

다음을 명확히 정의한다.

```text
GSLB
 ↓
L4
 ↓
Apache
 ↓
Tomcat Connector
```

검토사항:

- Apache Instance 수
- Listen Port
- VirtualHost
- 업무별 URL
- Reverse Proxy
- Routing
- Health Check
- SSL
- Access Log
- Connection
- Timeout
- WEB 이중화
- WAS Routing
- 장애 전환

Apache 하나의 Instance가 여러 Port를 Listen할 수 있다는 것과,
여러 Instance를 운영해야 하는 Architecture 이유를 구분한다.

---

# 12. STEP 9 — WAS Architecture 정의

WAS는 Tomcat으로 정의한다.

다음 계층을 반드시 분리한다.

```text
WAS Server / VM
      ↓
Tomcat JVM Instance
      ↓
Application / WAR
```

현재 프로젝트에서 Container라는 표현을 사용할 경우:

> **Container = 독립 Tomcat JVM Instance**

인지 반드시 명확히 한다.

Tomcat Instance별로 다음 정보를 관리한다.

| 영역 | 항목 |
|---|---|
| Process | PID / OS Account |
| Tomcat | CATALINA_HOME / CATALINA_BASE |
| Network | HTTP/AJP/Shutdown Port |
| JVM | Xms/Xmx/Metaspace/GC |
| Thread | maxThreads/minSpareThreads/acceptCount |
| Application | WAR |
| DB | HikariCP |
| Session | Session Strategy |
| Log | Access/App/GC |
| Monitoring | APM/JMX |
| HA | L4/Apache Pool |

그리고 다음 관계를 검증한다.

```text
VM CPU/MEM
   ↓
JVM 수
   ↓
JVM Heap
   ↓
Tomcat Thread
   ↓
Application
   ↓
DB Pool
```

VM 전체 자원을 JVM별 설정과 혼동하지 않는다.

---

# 13. STEP 10 — Infrastructure / Server Inventory Architecture

서버 인벤토리는 단순 Hostname 목록으로 작성하지 않는다.

**서버 1대 = Master Inventory 1 Row**

원칙으로 한다.

최소 다음 정보를 관리한다.

```text
System
Application
Business
Application Code
Server Name
Hostname
Environment
Center
Role
OS
CPU
Memory
OS Disk
Data Disk
Log Disk
Network
TPMC
TPS
Middleware
JVM
Tomcat
Apache
DB Pool
Application
HA
DR
Source
Verification Status
```

Hostname도 다음 구조로 해석한다.

```text
법인
+ Application
+ Platform
+ Environment
+ Role
+ Sequence
```

현재 이미지에서 확인되는 값과 추론값을 구분한다.

---

# 14. STEP 11 — Capacity & Performance Architecture

성능은 단순 서버 Spec 표가 아니라 다음 Chain으로 정의한다.

```text
전체 사용자
 ↓
로그인 세션
 ↓
동시 요청률
 ↓
동시 요청자
 ↓
목표 응답시간
 ↓
TPS
 ↓
AP 수량
 ↓
WAS Thread
 ↓
DB Pool
 ↓
DB Session
 ↓
CPU / Memory
```

다음 식의 의미와 전제를 확인한다.

```text
동시 요청자
=
사용자 × 동시 요청률

TPS
=
동시 요청자 / 응답시간
```

DB Pool은:

```text
TPS
× DB Connection Hold Time
× DB Usage Ratio
× Safety Factor
```

관점으로 검증한다.

성능 기준에는 최소 다음을 포함한다.

- TPS
- p95
- CPU
- JVM Heap
- GC Pause
- Tomcat Busy Thread
- maxThreads
- Hikari Active
- Hikari Pending
- Connection Timeout
- DB Session
- SQL Time
- External Call Time
- Network
- Thread Dump
- Heap Dump

설계값과 실제 설정값을 반드시 나누어 표시한다.

---

# 15. STEP 12 — Data Architecture

다음을 분리한다.

```text
Operational / Reference Data
RDW
ADW
Single View
Batch
Analytics
Migration
```

검토할 항목:

- RDW/ADW 역할
- 데이터 소유권
- 조회/갱신 권한
- CDC
- Kafka
- ETL
- Batch
- Data Migration
- Migration Source
- Target
- Reconciliation
- 데이터 검증
- 데이터 품질
- 대량 이행
- 재처리
- Cutover

실시간과 배치가 동일 자원을 무제한 경쟁하지 않도록 분석한다.

---

# 16. STEP 13 — Integration Architecture

연계는 단순 URL 목록이 아니다.

각 연계별로 다음을 관리한다.

```text
Source Domain
Target Domain
ServiceId
Protocol
Message
Authentication
Timeout
Retry
Circuit Breaker
Error Mapping
Idempotency
Logging
Monitoring
Owner
```

Domain 간 직접 DB 접근과 Java 내부 의존성을 별도 위험으로 점검한다.

---

# 17. STEP 14 — Logging / Observability Architecture

모든 거래는 가능하면:

```text
GUID
+
ServiceId
+
User
+
System
+
Application
+
Host
+
JVM
```

으로 추적 가능하도록 정의한다.

Logging은 최소 다음 계층으로 구분한다.

```text
System Log
Transaction Log
Business Log
SQL Log
Error Log
Image Log
Access Log
GC Log
Security/Audit Log
Runtime Metric
Distributed Trace
```

하나의 거래가:

```text
Client
→ WEB
→ WAS
→ TCF
→ Business
→ DB
→ External
→ Response
```

까지 이어지는지 검토한다.

---

# 18. STEP 15 — Operation & Control Architecture

운영 Architecture는 다음 두 영역으로 분리한다.

```text
CONTROL PLANE
운영자가 정책을 관리

RUNTIME PLANE
실제 거래가 정책에 따라 실행
```

관리 대상:

- 거래통제
- ServiceId 통제
- 사용자/권한
- 환경설정
- 배포정보
- Thread
- JVM
- DB Pool
- Timeout
- Error
- Slow Transaction
- WAR
- Server
- Health
- 로그
- Runtime Evidence

운영자가 **어떤 ServiceId가 어느 서버/JVM에서 어떤 DB/SQL을 호출하다 문제가 발생했는가**까지 추적할 수 있는지 검토한다.

---

# 19. STEP 16 — HA / DR Architecture

다음 장애 단위를 분리하여 분석한다.

```text
WEB 장애
WAS VM 장애
Tomcat JVM 장애
Application 장애
DB 장애
Network 장애
External 연계 장애
Center 장애
```

각 장애에 대하여:

- 감지
- 격리
- 우회
- 재시도
- 복구
- 데이터 정합성
- RTO
- RPO
- 운영 절차

를 정의한다.

Active-Active라는 단어만으로 HA를 설명하지 않는다.

---

# 20. STEP 17 — Deployment / CI-CD Architecture

다음을 연결한다.

```text
Source
 ↓
Git
 ↓
Build
 ↓
Test
 ↓
Artifact
 ↓
Repository
 ↓
Deploy
 ↓
Tomcat JVM
 ↓
Runtime Verification
```

검토 대상:

- Git
- Gradle/Maven
- Jenkins/GitLab Runner
- WAR
- Application Version
- Config
- Environment Separation
- Rolling
- Rollback
- DB Change
- Security Scan
- Architecture Rule Check

---

# 21. STEP 18 — Runtime Architecture

문서상의 Architecture가 실제 요청에서 어떻게 실행되는지 보여준다.

대표 온라인 거래 한 건을 선택하여:

```text
사용자
 ↓
WEB
 ↓
WAS
 ↓
Filter
 ↓
Interceptor
 ↓
Controller
 ↓
TCF
 ↓
STF
 ↓
Timeout
 ↓
Transaction
 ↓
Dispatcher
 ↓
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
RDW
 ↓
Response
 ↓
ETF
 ↓
Logging
```

형태로 End-to-End Trace를 작성한다.

각 단계마다 다음 정보를 연결한다.

```text
Component
Class
Configuration
Thread
Transaction
Timeout
Log
Metric
Failure
Recovery
```

---

# 22. UI/UX Architecture도 별도 포함한다

UI/UX 자료는 화면 디자인 참고자료로만 취급하지 않는다.

다음을 Architecture와 연결한다.

```text
Screen
 ↓
Menu
 ↓
Function
 ↓
Program ID
 ↓
ServiceId
 ↓
API
 ↓
Application
 ↓
DB
```

화면 진척현황이 실제 Application/Service 개발상태와 연결 가능한지 검토한다.

---

# 23. HW/SW 도입 일정과 Architecture Dependency 연결

도입 일정은 PM 일정표로만 보지 않는다.

다음 Dependency를 분석한다.

```text
HW 도입
 ↓
OS
 ↓
Middleware
 ↓
Network
 ↓
Security
 ↓
DB
 ↓
Application
 ↓
Data Migration
 ↓
Performance Test
 ↓
Cutover
```

Architecture Critical Path를 별도로 작성한다.

---

# 24. 환경설정 Architecture

지금까지 제공된 모든 설정값을 다음 구조로 통합한다.

```text
OS
WEB / Apache
Tomcat
JVM
Spring
HikariCP
MyBatis
Transaction
Timeout
Session
JWT
Logging
Monitoring
DB
Network
```

각 설정은 다음 6개 값을 가진다.

| 항목 | 설명 |
|---|---|
| Configuration Key | 설정항목 |
| Design Value | 설계 기준값 |
| Current Value | 현재 설정 |
| Location | 실제 파일 |
| Evidence | 출처 |
| Verdict | 정상/조정/확인필요 |

---

# 25. Architecture Rule 정의

분석 결과에서 Architecture Rule을 추출한다.

예:

```text
R-APP-001
Handler는 DAO를 직접 호출하지 않는다.

R-DOM-001
다른 업무 Domain의 DAO를 직접 호출하지 않는다.

R-TCF-001
모든 온라인 거래는 ServiceId로 추적 가능해야 한다.

R-TX-001
Timeout Worker Thread에서 시작한 Transaction Context가
업무 처리 완료까지 유지되어야 한다.

R-WAS-001
각 Tomcat JVM은 독립 CATALINA_BASE와 Connector Port를 가진다.

R-PERF-001
DB Pool은 maxThreads 값만 보고 산정하지 않는다.

R-OBS-001
모든 온라인 거래는 GUID + ServiceId로 추적 가능해야 한다.
```

각 Rule에는:

```text
Rule ID
Category
Description
Evidence
Target
Validation Method
Severity
Exception Policy
```

를 정의한다.

---

# 26. Architecture Gap 분석

AS-IS와 TO-BE를 비교한다.

반드시 다음 형식으로 작성한다.

| GAP ID | 영역 | AS-IS | TO-BE | 영향 | 우선순위 | 조치 |
|---|---|---|---|---|---|---|

Gap은 최소:

```text
Architecture
Application
Framework
Infrastructure
Performance
Security
Data
Operation
Deployment
Documentation
```

으로 분류한다.

---

# 27. Architecture Decision

중요한 선택에는 ADR 형식의 결정 기록을 작성한다.

예:

```text
ADR ID
제목
Context
Problem
Option A
Option B
Option C
Decision
Reason
Trade-off
Impact
Rollback
Evidence
```

다음과 같은 항목은 우선 ADR 후보로 본다.

- Apache/Tomcat Instance 구조
- 1 JVM : 1 Application 여부
- Session 방식
- JWT 구조
- Transaction Owner
- Timeout 방식
- DB Pool
- 8Core vs 16Core
- RDW/ADW 경계
- Domain Integration
- ServiceId Routing
- OM 통합
- DR

---

# 28. 자동 검증 가능한 구조로 발전시킨다

최종 Architecture는 문서로 끝내지 않는다.

다음 Closed Loop를 목표로 정의한다.

```text
Architecture as Document
        ↓
Architecture as Model
        ↓
Architecture as Code
        ↓
Architecture as Test
        ↓
Runtime Evidence
        ↓
Drift Detection
        ↓
GAP / ADR
        ↓
New Baseline
```

최종적으로 다음 Traceability가 가능해야 한다.

```text
Requirement
 ↓
Architecture
 ↓
Rule
 ↓
ServiceId
 ↓
Code
 ↓
Configuration
 ↓
Server
 ↓
JVM
 ↓
Deployment
 ↓
Runtime
 ↓
Evidence
```

---

# 29. 최종 산출물

최종 결과는 최소 다음 문서 체계로 작성한다.

```text
00-EXECUTIVE-SUMMARY
01-ARCHITECTURE-VISION
02-BIG-PICTURE
03-LOGICAL-ARCHITECTURE
04-PHYSICAL-ARCHITECTURE
05-APPLICATION-ARCHITECTURE
06-TCF-ARCHITECTURE
07-SECURITY-ARCHITECTURE
08-MESSAGE-ARCHITECTURE
09-DATA-ARCHITECTURE
10-INTEGRATION-ARCHITECTURE
11-WEB-ARCHITECTURE
12-WAS-ARCHITECTURE
13-INFRASTRUCTURE-ARCHITECTURE
14-CAPACITY-PERFORMANCE
15-LOGGING-OBSERVABILITY
16-OPERATION-CONTROL
17-HA-DR
18-CI-CD-DEPLOYMENT
19-DATA-MIGRATION
20-UIUX-TRACEABILITY
21-CONFIGURATION-BASELINE
22-SERVER-MASTER-INVENTORY
23-ARCHITECTURE-RULES
24-GAP-REGISTER
25-ADR-REGISTER
26-RISK-REGISTER
27-OPEN-ISSUES
28-RUNTIME-ARCHITECTURE
29-ARCHITECTURE-GATE
30-EXECUTION-ROADMAP
```

---

# 30. 각 Architecture 문서의 표준 구조

모든 문서는 가능하면 다음 형식을 따른다.

## 1. 목적

## 2. 적용 범위

## 3. 현재 확인된 사실

## 4. Architecture Principle

## 5. Big Picture

## 6. 구성요소

## 7. 책임과 경계

## 8. 정상 처리 흐름

## 9. 예외/장애 처리

## 10. 환경설정

## 11. 운영 및 모니터링

## 12. HA/DR

## 13. 보안

## 14. 성능

## 15. Architecture Rule

## 16. 금지 패턴

## 17. AS-IS / TO-BE

## 18. GAP

## 19. Decision

## 20. 자동검증

## 21. Source / Evidence

## 22. Open Issue

## 23. Architecture Gate

---

# 31. 표와 ASCII Diagram을 적극 사용한다

설명만 길게 작성하지 않는다.

각 주요 영역마다 최소 하나 이상의 구조도를 작성한다.

예:

```text
User
 │
 ▼
GSLB
 │
 ▼
L4
 │
 ▼
Apache
 │
 ▼
Tomcat JVM
 │
 ▼
TCF
 │
 ▼
ServiceId
 │
 ▼
Handler
 │
 ▼
Facade
 │
 ▼
Service
 │
 ▼
DAO
 │
 ▼
RDW
```

그리고 구조도 아래에는 반드시:

- 구성요소 책임
- 연결 이유
- 장애 영향
- 운영 포인트

를 설명한다.

---

# 32. 분석 시 금지사항

다음을 하지 않는다.

### 금지 1
확인되지 않은 서버 CPU/MEM/PORT 값을 임의 생성하지 않는다.

### 금지 2
과거 자료의 숫자를 최신 확정 기준이라고 자동 판단하지 않는다.

### 금지 3
PDMG AS-IS와 NSIGHT TO-BE를 섞지 않는다.

### 금지 4
제품명을 나열한 것을 Architecture라고 하지 않는다.

### 금지 5
Logical Architecture와 Physical Architecture를 혼합하지 않는다.

### 금지 6
Tomcat Server / JVM / Container / WAR를 같은 개념처럼 표현하지 않는다.

### 금지 7
Session 수를 TPS나 동시요청자와 동일하게 해석하지 않는다.

### 금지 8
DB Pool을 단순 CPU Core 배수로만 산정하지 않는다.

### 금지 9
현재 Source에 없는 기능을 구현됐다고 설명하지 않는다.

### 금지 10
Architecture 판단의 출처를 생략하지 않는다.

---

# 33. 결과 표기 규칙

모든 핵심 표에는 가능하면 다음 컬럼을 사용한다.

| 상태 | 항목 | 현재 | 목표 | 근거 | GAP | 조치 |
|---|---|---|---|---|---|---|

그리고 상태는:

```text
CONFIRMED
CONDITIONAL
GAP
RISK
OPEN
UNKNOWN
DEPRECATED
```

중 하나로 표기한다.

---

# 34. 최종 Architecture Gate

각 영역을 다음으로 판정한다.

```text
PASS
CONDITIONAL PASS
HOLD
REJECT
```

최종 표는 다음 형태로 작성한다.

| 영역 | 판정 | 핵심 근거 | GAP | 다음 조치 |
|---|---|---|---|---|
| Vision |  |  |  |  |
| Logical |  |  |  |  |
| Physical |  |  |  |  |
| Application |  |  |  |  |
| TCF |  |  |  |  |
| Security |  |  |  |  |
| WEB |  |  |  |  |
| WAS |  |  |  |  |
| Performance |  |  |  |  |
| Data |  |  |  |  |
| Operation |  |  |  |  |
| HA/DR |  |  |  |  |

근거가 부족한 영역에 PASS를 주지 않는다.

---

# 35. 최종 보고 순서

최종 결과를 다음 순서로 제시한다.

### Part 1 — Executive Architecture

임원이 5~10분 안에 전체 구조를 이해할 수 있도록 작성한다.

### Part 2 — Architecture Big Picture

전체 시스템 구성과 책임 경계를 표현한다.

### Part 3 — Architecture Baseline

현재 확정된 기준을 표로 정리한다.

### Part 4 — Detailed Architecture

Application / TCF / Security / Data / WEB / WAS / Infra / Performance / Operation을 상세 정의한다.

### Part 5 — Architecture Gap

현재 문제와 미완료 사항을 정리한다.

### Part 6 — Architecture Decision

확정이 필요한 의사결정을 제시한다.

### Part 7 — Architecture Roadmap

즉시 / 단기 / 구축전 / 오픈전 / 운영단계로 나누어 조치한다.

### Part 8 — Architecture Gate

최종 PASS / CONDITIONAL PASS / HOLD / REJECT를 판정한다.

---

# 36. 최종 핵심 질문

모든 분석이 끝나면 다음 질문에 답하라.

```text
1. NSIGHT는 어떤 Architecture인가?

2. 왜 이런 구조로 설계했는가?

3. 시스템의 책임과 경계는 명확한가?

4. 실제 서버와 Application이 논리 Architecture와 일치하는가?

5. WEB/WAS/JVM/WAR의 실행 단위가 명확한가?

6. ServiceId 하나를 화면에서 DB까지 추적할 수 있는가?

7. Transaction/Timeout의 Owner가 명확한가?

8. 장애가 어디까지 전파되는가?

9. 현재 성능 설정은 용량산정과 정합한가?

10. JWT/SSO/Session 보안 경계가 명확한가?

11. RDW/ADW/Batch/실시간의 데이터 경계가 명확한가?

12. 서버 Inventory와 Architecture Component가 연결되는가?

13. 운영자가 장애 원인을 ServiceId까지 추적할 수 있는가?

14. 현재 Architecture의 가장 큰 GAP은 무엇인가?

15. 지금 반드시 결정해야 할 ADR은 무엇인가?

16. 오픈 전 반드시 검증해야 하는 Architecture Gate는 무엇인가?
```

---

# 37. 실행 명령

이제 지금까지 제공된 **전체 NSIGHT 대화, 업로드 자료, 이미지, Source 분석, 환경설정, 서버 인벤토리 및 Architecture Decision을 근거로 통합 분석을 시작하라.**

먼저 전체 내용을 다음 8개 영역으로 재분류하라.

```text
1. Architecture Strategy
2. Application / TCF
3. Security
4. Data / Integration
5. WEB / WAS / Infrastructure
6. Capacity / Performance
7. Operation / Migration / Deployment
8. UI/UX / Inventory / Project Dependency
```

그 후:

```text
Evidence Inventory
       ↓
Current Baseline
       ↓
Architecture Big Picture
       ↓
Logical Architecture
       ↓
Physical Architecture
       ↓
Mechanism
       ↓
Runtime
       ↓
Configuration Baseline
       ↓
Architecture Rules
       ↓
Gap / Risk / ADR
       ↓
Architecture Gate
       ↓
Execution Roadmap
```

순서로 수행하라.

**중간에 자료가 부족하더라도 작업 전체를 중단하지 마라.**

확인 가능한 부분은 정의하고,
불명확한 부분은 `UNKNOWN / OPEN / GAP`으로 등록하여 계속 진행하라.

최종 목표는 단순 설명문이 아니라:

> **“농협 상호금융 NSIGHT가 무엇으로 구성되고, 왜 그렇게 구성되며, 실제 서버·JVM·Application·ServiceId·DB·운영까지 어떻게 연결되는지를 한 체계 안에서 설명하고 검증할 수 있는 통합 Architecture Baseline”**

을 만드는 것이다.