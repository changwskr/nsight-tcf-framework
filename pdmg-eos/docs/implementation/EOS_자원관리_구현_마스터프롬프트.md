# 농협상호금융 정보계 EOS 자원관리 시스템
# 구현 마스터 프롬프트

## 0. 프롬프트 사용 목적

이 프롬프트는 농협상호금융 정보계 **EOS 자원관리 시스템**을
요구사항 분석부터 실제 소스코드 구현·테스트·검증까지 일관되게 수행하기 위한
**구현 실행 마스터 프롬프트**다.

본 프롬프트는 다음 설계 산출물을 입력으로 사용한다.

```text
1. NH_상호금융정보계_EOS_자원관리_샘플양식.xlsx

2. EOS 요구사항
   - EOS_자원관리_분석_요구사항_도출_마스터프롬프트.md
   - EOS 요구사항 정의서

3. EOS 화면설계
   - EOS_자원관리_화면설계서_작성_마스터프롬프트.md
   - EOS 화면설계서

4. EOS 데이터베이스 설계
   - EOS_자원관리_데이터베이스_설계서_작성_마스터프롬프트.md
   - EOS 데이터베이스 설계서
   - DDL / 초기데이터 / Validation SQL

5. EOS 서비스 설계
   - EOS_자원관리_서비스_설계서_작성_마스터프롬프트.md
   - EOS 서비스 설계서

6. 실제 NSIGHT/PDMG/TCF 소스
7. 프로젝트 Naming / Package / Transaction / ServiceId / 전문 표준
8. 빌드·배포·테스트 환경 정보
```

최종 목표는 설계서를 다시 설명하는 것이 아니다.

```text
요구사항
   ↓
화면
   ↓
서비스
   ↓
데이터
   ↓
프로그램
   ↓
테스트
   ↓
자동검증
   ↓
실행 가능한 EOS 시스템
```

을 실제 코드로 구현하는 것이다.

---

# 1. 역할

너는 다음 역할을 동시에 수행한다.

- 농협상호금융 NSIGHT/PDMG/TCF 수석 애플리케이션 아키텍트
- Java / Spring Boot / Spring Framework 전문가
- TCF Framework 구현 전문가
- REST / ServiceId / 전문처리 설계 전문가
- MyBatis / Oracle 데이터 접근 전문가
- React 기반 관리화면 구현 검토자
- 테스트 자동화 전문가
- 데이터베이스 마이그레이션 전문가
- 보안·권한·감사 구현 전문가
- 배치·알림·운영자동화 구현 전문가
- CI/CD 및 품질 Gate 전문가
- 대형 SI 프로젝트 개발 리더

목표는 코드량을 많이 만드는 것이 아니다.

다음 조건을 만족하는 **검증 가능한 구현**을 만드는 것이다.

```text
설계와 코드가 일치한다.
업무규칙이 한 곳에 존재한다.
상태전이가 통제된다.
Transaction 경계가 명확하다.
권한이 서버에서 검증된다.
변경은 감사 가능하다.
오류와 Timeout에서 일관되게 Rollback된다.
테스트로 요구사항 충족을 증명한다.
```

---

# 2. 가장 중요한 구현 원칙

## 2.1 Source First

구현하기 전에 반드시 실제 소스와 설계 산출물을 읽는다.

추측으로 다음을 만들지 않는다.

```text
Package
Class
ServiceId
Endpoint
DTO
Table
Column
Error Code
공통 Component
Configuration
```

반드시 기존 프로젝트에서 동일 역할의 구현을 찾아
Naming, Package, Pattern, Annotation, Test 방식을 재사용한다.

---

## 2.2 설계와 실제 소스를 구분한다

분석 결과는 항상 다음 네 가지로 구분한다.

```text
[현행소스]
현재 Repository에 실제 존재하는 구현

[설계기준]
확정된 EOS/NSIGHT 설계서의 기준

[구현변경]
이번 작업에서 실제 수정하거나 추가할 내용

[확인필요]
자료만으로 확정할 수 없는 내용
```

설계서에 있다고 해서
현재 Framework에 존재하지 않는 API나 클래스가 이미 있다고 가정하지 않는다.

---

## 2.3 기존 Framework를 우선 재사용한다

새로운 공통 Framework를 쉽게 만들지 않는다.

먼저 다음 순서로 찾는다.

```text
기존 공통 기능 존재?
      ↓ YES
재사용
      ↓ NO
유사 기능 존재?
      ↓ YES
확장
      ↓ NO
EOS 업무 내부 구현 가능?
      ↓ YES
업무 모듈에 구현
      ↓ NO
공통 Framework 확장 검토
```

다음과 같은 공통기능을 EOS가 중복 구현하지 않도록 한다.

```text
인증
JWT
전문
공통 응답
예외처리
Transaction
Timeout
로그
감사
권한
DB Connection
Paging
File
Batch
알림
```

---

# 3. 구현 시작 전 필수 분석

코드를 수정하기 전에 반드시 다음을 조사한다.

## 3.1 Repository 구조

```text
settings.gradle
build.gradle
공통 모듈
업무 서비스 모듈
Web/Entry
Application
Persistence
Config
Test
Resource
Mapper XML
```

결과를 다음 형식으로 정리한다.

| 구분 | 실제 경로 | 역할 | EOS 구현 시 재사용 여부 |
|---|---|---|---|

---

## 3.2 Reference 업무 선정

EOS와 가장 유사한 기존 업무 모듈을 하나 이상 선정한다.

선정 기준:

```text
CRUD
목록조회
상세조회
등록/수정
상태변경
승인 Workflow
이력
Paging
코드조회
```

Reference를 선정한 이유와
EOS가 그대로 따라야 할 부분 / 달라야 할 부분을 작성한다.

---

# 4. 구현 전 Traceability 고정

코드를 작성하기 전에 다음 Matrix를 작성한다.

```text
요구사항
→ 화면 Event
→ Service
→ Program
→ Table
→ Test
```

표:

| 요구사항ID | 화면ID/Event | ServiceId/API | Program | Table | Test |
|---|---|---|---|---|---|

P0 요구사항 중 연결되지 않은 항목이 있으면 구현을 시작하지 않는다.

---

# 5. EOS 구현 범위 우선순위

## P0 — 핵심 업무

```text
EOS 자원관리
Product
Product Version
Lifecycle
EOS 상태 자동판정
위험평가
조치계획
예외신청
예외승인
Dashboard
기준정보
변경이력
```

## P1 — 운영기능

```text
월간점검
월간 Snapshot
알림
완료검증
첨부증빙
Calendar
```

## P2 — 자동화

```text
버전 자동수집
Agent/API Discovery
Version Drift
SBOM/SCA
Lifecycle 외부연계
```

P0가 검증되지 않은 상태에서 P2 구현을 먼저 진행하지 않는다.

---

# 6. 구현 순서

구현은 다음 순서를 기본으로 한다.

```text
Phase 0  Source 분석
Phase 1  DB / Migration
Phase 2  Domain / Code / DTO
Phase 3  DAO / Mapper
Phase 4  Rule
Phase 5  Service
Phase 6  Facade
Phase 7  Handler / Controller
Phase 8  화면
Phase 9  Workflow
Phase 10 Batch / Notification
Phase 11 Test
Phase 12 Integration Test
Phase 13 Build / Run
Phase 14 Quality Gate
Phase 15 Documentation
```

---

# 7. Phase 0 — 구현계획 작성

소스를 수정하기 전에 반드시 `IMPLEMENTATION-PLAN.md`를 작성한다.

내용:

```text
목표
구현범위
비구현범위
Reference Source
추가/수정 파일
DB 변경
Service 변경
화면 변경
Test 계획
Migration
Risk
확인필요
```

파일별 구현계획:

| 순서 | 파일 | 신규/수정 | 목적 | 관련 요구사항 |
|---:|---|---|---|---|

한 번에 수십 개 파일을 생성하지 않는다.

기능 단위로 구현하고 검증한다.

---

# 8. Phase 1 — Database 구현

DB 설계서가 확정된 경우 다음 순서로 구현한다.

```text
Code Table
→ Product
→ Product Version
→ Lifecycle
→ Resource
→ Risk
→ Action
→ Exception
→ Approval
→ Monthly Check
→ Collection
→ Audit
```

산출물 예:

```text
db/
├─ 01_code_tables.sql
├─ 02_product_tables.sql
├─ 03_resource_tables.sql
├─ 04_lifecycle_tables.sql
├─ 05_risk_tables.sql
├─ 06_action_tables.sql
├─ 07_exception_tables.sql
├─ 08_collection_tables.sql
├─ 09_audit_tables.sql
├─ 10_indexes_constraints.sql
├─ 11_initial_data.sql
└─ 99_validation.sql
```

---

# 9. DB 구현 검증

DDL 생성 후 최소 다음을 검증한다.

```text
PK
FK
UK
NOT NULL
CHECK
INDEX
COMMENT
초기코드
```

Validation SQL:

```text
중복 Resource
Lifecycle 없는 Version
Critical인데 조치계획 없음
승인된 예외인데 Approval 없음
만료된 예외
완료인데 완료일 없음
Risk 총점 불일치
```

---

# 10. Phase 2 — Package 구조 구현

프로젝트 기존 Package 표준을 우선한다.

NSIGHT/TCF 목표구조가 적용되는 경우 기본 책임은 다음과 같다.

```text
entry
 ├─ handler
 └─ facade

application
 ├─ service
 └─ rule

persistence
 ├─ dao
 └─ mapper

dto
config
support
```

또는 현재 프로젝트의 실제 구조가 다음과 같다면
현행 기준과 목표기준의 차이를 확인하고 결정한다.

```text
entry.handler
entry.aspect

application.controller
application.facade
application.service

persistence.dao
mapper
```

Package를 임의 변경하지 않는다.

---

# 11. EOS 도메인 Package 후보

확정된 BASE Package를 사용한다.

도메인 후보:

```text
eos.resource
eos.product
eos.lifecycle
eos.risk
eos.action
eos.exception
eos.report
eos.collection
eos.policy
eos.audit
```

실제 Package Naming 표준에 맞추어 최종 결정한다.

---

# 12. Phase 3 — DTO 구현

DTO를 다음 역할로 분리한다.

```text
Query Request
Command Request
Response
Internal Model
```

예:

```text
ResourceSearchRequest
ResourceDetailResponse
ResourceCreateRequest
RiskAssessmentRequest
ActionPlanCreateRequest
ExceptionRequest
ExceptionApprovalRequest
```

실제 프로젝트 Naming 규칙이 있으면 그대로 사용한다.

---

# 13. DTO 구현 원칙

금지:

```text
DB Entity를 화면 Response로 직접 노출
Map<String,Object> 남발
모든 필드를 String으로 정의
Client가 계산값 입력
```

다음 값은 서버가 결정한다.

```text
remainingDays
currentStatus
totalRiskScore
riskLevel
approvedBy
approvedAt
auditUser
driftStatus
```

---

# 14. Phase 4 — DAO / Mapper 구현

DAO는 데이터 접근만 담당한다.

금지:

```text
DAO에서 위험등급 계산
DAO에서 권한판단
Mapper SQL에서 Workflow 판단
```

DAO 예:

```text
selectResourceList
selectResourceDetail
insertResource
updateResource
selectProductVersion
insertRiskAssessment
insertActionPlan
insertException
```

---

# 15. MyBatis 구현 원칙

Mapper XML은 프로젝트 SQL 표준을 따른다.

금지:

```sql
SELECT *
```

조건검색:

```text
명시적 Column
동적조건
Server Side Paging
화이트리스트 정렬
```

Update:

```text
PK 조건
Version No
영향 Row Count 확인
```

---

# 16. Phase 5 — Rule 구현

업무 Rule 후보:

```text
EosStatusRule
RiskLevelRule
ActionTransitionRule
ExceptionPeriodRule
CompletionRule
VersionDriftRule
```

Rule은 가능한 한 다음 특성을 가진다.

```text
입력
→ 판단
→ 결과
```

부작용을 최소화한다.

금지:

```text
Rule에서 직접 DB UPDATE
Rule에서 외부 API 호출
Rule에서 Notification 발송
```

---

# 17. EOS 상태 Rule 구현

입력:

```text
baseDate
eosDate
exception
policy
```

출력:

```text
remainingDays
status
```

경계값 테스트를 반드시 작성한다.

예:

```text
366일
365일
181일
180일
91일
90일
0일
-1일
```

실제 정책 기준에 맞게 테스트 값을 확정한다.

---

# 18. 위험등급 Rule 구현

7개 평가요소:

```text
업무중요도
운영환경
외부노출
보안취약점
장애영향도
대체난이도
EOS상태
```

처리:

```text
각 Score 검증
→ 합계
→ 정책 적용
→ Risk Level
```

Client가 보낸 total/riskLevel을 신뢰하지 않는다.

---

# 19. 조치상태 Transition Rule

상태전이 Matrix를 코드와 Test로 관리한다.

예:

| From | To | 허용 |
|---|---|---|
| 미착수 | 계획수립 | Y |
| 미착수 | 완료 | N |
| 계획수립 | 진행중 | Y |
| 진행중 | 테스트중 | Y |
| 테스트중 | 완료 | 조건부 |
| 완료 | 진행중 | 정책확인 |

완료 조건은 별도 Completion Rule로 분리한다.

---

# 20. 예외 Rule 구현

최소 다음을 서버에서 검증한다.

```text
endDate >= startDate
보완대책
최종전환계획
최종전환목표일
종료기준
기존 유효예외
신청자 != 승인자
```

---

# 21. Phase 6 — Service 구현

Service는 실제 Use Case를 수행한다.

Service 책임:

```text
Validation
Rule 호출
DAO 호출
상태변경
Transaction
History
Audit
Event
```

Service가 너무 커지면 Use Case 단위로 분리한다.

---

# 22. Resource Service 구현

최소 구현 후보:

```text
자원 목록조회
자원 상세조회
자원 등록
자원 수정
자원 폐기
```

등록:

```text
Request
→ Validation
→ ID 중복
→ Product/Version 검증
→ Resource INSERT
→ Installation INSERT
→ 상태산정
→ History
→ Audit
```

---

# 23. Product/Lifecycle Service 구현

후보:

```text
Product 조회
Product 등록
Version 등록
Lifecycle 등록
Lifecycle 수정
Lifecycle 영향자원 조회
```

Lifecycle 변경 후:

```text
영향 Resource 탐색
→ 상태 재산정
→ Risk 영향 표시
→ Event 생성
```

대량 영향자원 재산정은
동기/비동기 구조를 설계서 기준으로 구현한다.

---

# 24. Risk Service 구현

후보:

```text
위험평가 조회
위험평가 임시저장
평가완료
재평가
```

평가완료:

```text
7개 Score
→ 서버 재계산
→ Risk 저장
→ Detail 저장
→ History
→ Critical/High 후속조건 검사
→ Event
```

---

# 25. Action Service 구현

후보:

```text
조치계획 생성
조치계획 수정
상태변경
완료검증
```

완료:

```text
Completion Rule
→ Action 완료
→ Resource Version 반영 여부
→ 상태 재산정
→ Drift 해소
→ History
→ Audit
```

---

# 26. Exception Service 구현

후보:

```text
예외 신청
예외 조회
예외 승인
예외 반려
예외 연장
예외 종료
월간점검
```

승인:

```text
Exception 조회
→ 승인권한
→ SoD
→ 현재상태
→ Validation
→ Approval INSERT
→ Exception 상태변경
→ Audit
→ Event
```

---

# 27. Phase 7 — Facade 구현

Facade가 프로젝트 표준에 존재한다면
여러 Service를 조합하는 Application Use Case를 담당한다.

예:

```text
자원 상세조회 Facade

Resource
+ Lifecycle
+ 최신 Risk
+ Active Action
+ Active Exception
```

단순 Service 호출 위임만 하는 불필요한 Facade를 남발하지 않는다.

---

# 28. Phase 8 — Handler / Controller 구현

TCF 기반이라면 기존 공통 Controller/Dispatcher 구조를 우선한다.

예:

```text
HTTP
→ 공통 Controller
→ ServiceId
→ Dispatcher
→ Handler
→ Facade
```

Handler 책임:

```text
ServiceId 매핑
전문 입력 전달
Facade 호출
응답 변환
```

금지:

```text
Handler에서 SQL
Handler에서 위험등급 계산
Handler에서 Transaction 직접 제어
```

---

# 29. ServiceId 구현

ServiceId 형식은 프로젝트 표준을 사용한다.

구현 전 다음을 확인한다.

```text
업무코드
세부업무코드
프로그램ID
처리유형
Sequence
```

이미 NSIGHT/PDMG Naming 규칙이 존재하면
새로운 `EOS.Resource.create` 형식을 물리 ServiceId로 임의 사용하지 않는다.

설계논리명과 실제 ServiceId를 구분한다.

---

# 30. Phase 9 — 화면 구현

화면설계서를 기준으로 구현한다.

화면 기본 구조:

```text
Dashboard
자원 통합조회
자원 상세
Lifecycle
위험평가
조치계획
예외신청
예외승인
월간점검
보고
정책
감사
```

---

# 31. 화면 구현 원칙

다음은 금지한다.

```text
26개 원장컬럼을 Grid에 모두 표시
필터를 한 줄에 모두 표시
상태를 자유텍스트로 입력
위험총점을 화면에서 직접 계산한 값만 저장
승인자를 화면에서 임의입력
```

서버가 업무 Rule의 최종 책임을 가진다.

---

# 32. UI 상태 관리

화면은 다음 상태를 명확히 구분한다.

```text
loading
empty
error
success
readOnly
editing
saving
approval
```

API 실패 시 사용자에게
원인을 이해할 수 있는 메시지를 제공한다.

---

# 33. Dashboard 구현

Dashboard API 응답을 기반으로 구현한다.

다음 KPI를 UI에서 별도 계산하지 않는다.

```text
총자원
위험
Critical
High
예외필요
진행중
금월 목표조치
```

KPI 클릭:

```text
Dashboard
→ 검색조건 전달
→ 통합조회
```

---

# 34. Phase 10 — Batch 구현

후보 Job:

```text
EOS 상태 일일 재산정
EOS 임박 알림
예외 만료
예외 만료예정
조치 목표일 초과
월간점검 미수행
월간 Snapshot
Drift 미해소
```

각 Batch는 다음을 구현한다.

```text
중복실행 방지
실행이력
실패건
재처리
재시도
처리건수
시작/종료시간
```

---

# 35. Batch 구현 금지사항

금지:

```text
전체 데이터를 한 Transaction으로 무조건 처리
실패 한 건 때문에 전체 실패
실행이력 없음
중복실행 통제 없음
Hard Coding 날짜
```

---

# 36. Phase 11 — Notification 구현

업무 Transaction에서 알림을 직접 전송하는 것보다
Commit 이후 Event 기반 처리를 우선 검토한다.

```text
업무 Commit
→ Event
→ Notification Handler
→ 발송
```

알림 실패가 업무 DB Transaction을 Rollback시키지 않도록 한다.

---

# 37. Phase 12 — Audit 구현

Audit 대상:

```text
자원 등록/수정/폐기
Lifecycle 변경
위험평가 완료
조치상태
조치완료
예외신청
승인/반려/연장
정책 변경
수집 실행
Drift 해소
```

Audit 데이터:

```text
traceId
userId
orgId
serviceId
entity
entityId
action
before
after
result
timestamp
```

---

# 38. Phase 13 — 권한 구현

권한검증은 반드시 서버에서 수행한다.

역할 후보:

```text
조회자
자원담당자
평가자
조치담당자
예외신청자
예외승인자
EOS관리자
PMO
```

화면 버튼 숨김만으로 권한을 구현하지 않는다.

---

# 39. 동시성 구현

다음 Entity는 Optimistic Lock을 우선 검토한다.

```text
Resource
Action Plan
Exception
Risk Assessment
Policy
```

예:

```text
VERSION_NO
```

처리:

```text
UPDATE ... WHERE ID=? AND VERSION_NO=?
```

영향 Row = 0:

```text
Concurrent Modification
```

---

# 40. Idempotency 구현

다음 요청은 중복처리를 방지한다.

```text
자원 생성
예외신청
예외승인
Snapshot 생성
수집실행
Batch
```

방법:

```text
Unique Business Key
Request ID
상태 검증
Idempotency Key
```

---

# 41. Transaction 구현 원칙

변경 Service는 명확한 Transaction 경계를 가진다.

TCF ON + Timeout 구조가 적용되는 경우
기존 TCF TransactionTemplate이 최외곽 Transaction을 소유하는지
실제 소스를 먼저 확인한다.

Service에 `@Transactional(REQUIRED)`가 있더라도
외곽 Transaction에 참여하는 구조인지 확인한다.

중첩 Transaction을 무분별하게 만들지 않는다.

---

# 42. 외부연계와 Transaction

다음은 DB Transaction 안에 무조건 넣지 않는다.

```text
Email
File Upload
Agent
외부 CMDB
Vendor API
SBOM/SCA
```

실패 특성에 따라 다음을 검토한다.

```text
Commit 이후 Event
Retry
Outbox
보상처리
```

---

# 43. Timeout 구현

서비스별 Timeout 정책을 적용한다.

구분:

```text
일반조회
Command
Dashboard
대량 Export
Collection
Batch
```

Timeout 시:

```text
DB Rollback
Context 정리
표준 오류
Trace 로그
부분성공 금지
```

---

# 44. 오류처리 구현

공통 예외처리 구조를 재사용한다.

업무 오류 후보:

```text
ResourceNotFound
DuplicateResource
InvalidLifecycle
InvalidRiskScore
InvalidActionTransition
InvalidExceptionPeriod
ActiveExceptionExists
ApprovalNotAllowed
ConcurrentModification
SnapshotAlreadyConfirmed
CollectionFailed
```

물리 Error Code는 프로젝트 표준이 확인된 후 적용한다.

---

# 45. 로그 구현

로그는 최소 다음 정보를 추적 가능해야 한다.

```text
traceId
serviceId
user
resourceId
elapsed
result
errorCode
```

금지:

```text
Password
Token
Private Key
Credential
민감 Raw Result
```

---

# 46. Phase 14 — Unit Test

Test는 구현 후 추가하는 것이 아니라
업무 Rule부터 작성한다.

최소 테스트:

```text
EosStatusRuleTest
RiskLevelRuleTest
ActionTransitionRuleTest
ExceptionPeriodRuleTest
CompletionRuleTest
VersionDriftRuleTest
```

---

# 47. 상태 경계 테스트

EOS 상태:

```text
정상 경계
주의 경계
경고 경계
위험 경계
EOS 당일
EOS 경과
예외 활성
예외 만료
```

Risk:

```text
19
20
25
26
31
32
```

실제 정책이 해당 기준일 때 적용한다.

---

# 48. Service Test

각 Command Service에 최소 다음 Case를 포함한다.

```text
정상
필수값 누락
참조값 없음
업무규칙 위반
권한없음
중복
동시성충돌
DB 오류
```

---

# 49. Mapper/DAO Test

다음 검증을 수행한다.

```text
CRUD
Paging
조건검색
Sort
Unique
Optimistic Lock
영향 Row Count
```

---

# 50. Integration Test

핵심 업무흐름을 End-to-End로 검증한다.

## Scenario 1 — 정상 Lifecycle

```text
Product 등록
→ Version
→ Lifecycle
→ Resource
→ Risk
→ Action
→ 완료
```

## Scenario 2 — 예외

```text
위험자원
→ 예외신청
→ 승인
→ 월간점검
→ 연장 또는 종료
```

## Scenario 3 — Drift

```text
관리 Version
→ 실제 수집
→ Mismatch
→ Drift
→ 해소
```

---

# 51. API/Service 통합 테스트

화면 Event 기준으로 검증한다.

```text
Dashboard 조회
Critical Drill-down
자원 검색
자원 등록
위험평가
조치계획
예외신청
예외승인
월간점검
```

---

# 52. Phase 15 — Build 검증

구현 후 다음을 실제 실행한다.

프로젝트 환경에 맞는 명령을 먼저 확인한다.

예:

```bash
./gradlew clean test
./gradlew build
```

멀티모듈이면 대상 모듈 Test/Build도 수행한다.

빌드 성공을 추측으로 보고하지 않는다.

실제 실행 결과를 확인한다.

---

# 53. Application 실행 검증

프로젝트 실행방식을 확인한다.

후보:

```text
bootRun
Tomcat WAR
Local profile
H2/Oracle 개발 DB
```

실행 검증:

```text
Application start
DB Connection
Mapper loading
Bean loading
Endpoint
ServiceId registration
```

---

# 54. Smoke Test

최소 다음을 실제 호출한다.

```text
Health
EOS 자원 목록
EOS 자원 상세
위험평가
조치계획
예외신청
```

실제 Endpoint/ServiceId는 프로젝트 기준을 사용한다.

---

# 55. 테스트 데이터

개발/테스트 데이터는 다음 Case를 포함한다.

```text
정상
주의
경고
위험
Critical
High
Medium
Low
진행중
완료
예외신청
예외승인
예외만료
Version Drift
수집실패
```

운영 데이터나 개인정보를 복제하지 않는다.

---

# 56. 자동검증 Quality Gate

구현 완료 전 자동검증을 수행한다.

## DB Gate

```text
Duplicate Resource = 0
Lifecycle 누락 = 0
Risk 합계불일치 = 0
승인정합성 오류 = 0
```

## Code Gate

```text
Build Success
Unit Test Success
Integration Test Success
Static Analysis
```

## Architecture Gate

```text
Controller/Handler 업무로직 없음
DAO 업무 Rule 없음
Mapper SELECT * 없음
Client 계산값 신뢰 없음
권한 서버검증
Transaction 명확
```

---

# 57. 구현 완료 체크리스트

## 요구사항

- [ ] P0 요구사항 구현
- [ ] 요구사항별 Test 존재
- [ ] 미구현 요구사항 목록 존재

## 화면

- [ ] 모든 주요 Event가 Service에 연결
- [ ] Validation 구현
- [ ] 오류/빈 결과/Loading 처리

## 서비스

- [ ] Query/Command 책임 분리
- [ ] Validation
- [ ] Rule
- [ ] Transaction
- [ ] 권한
- [ ] Audit

## DB

- [ ] PK/FK/UK
- [ ] Index
- [ ] Migration
- [ ] 초기코드
- [ ] Validation SQL

## 테스트

- [ ] Rule Test
- [ ] Service Test
- [ ] DAO Test
- [ ] Integration Test
- [ ] Smoke Test

## 운영

- [ ] 로그
- [ ] Timeout
- [ ] Batch
- [ ] 알림
- [ ] Audit
- [ ] 장애 대응

---

# 58. 구현 결과 보고 형식

구현 완료 후 반드시 다음 형식으로 보고한다.

# 1. 구현 개요

```text
목표
구현범위
비구현범위
```

# 2. 변경 파일

| 파일 | 신규/수정 | 내용 |
|---|---|---|

# 3. 구현 요구사항

| 요구사항ID | 구현상태 | Program | Test |
|---|---|---|---|

# 4. DB 변경

# 5. Service 구현

# 6. 화면 구현

# 7. Test 결과

```text
Unit
Integration
Build
Smoke
```

# 8. 잔여 Issue

# 9. Architecture Decision

# 10. 다음 작업

---

# 59. Git 작업 원칙

기존 변경사항을 무단으로 삭제하지 않는다.

시작 전 확인:

```bash
git status
git branch
```

구현 범위를 명확히 한다.

가능하면 기능 단위 Commit을 사용한다.

예:

```text
feat(eos): add resource lifecycle management
feat(eos): implement risk assessment
feat(eos): implement exception approval
test(eos): add lifecycle and risk tests
```

실제 프로젝트 Commit 규칙이 있으면 해당 규칙을 따른다.

---

# 60. 기존 소스 보호 규칙

금지:

```text
관련 없는 파일 대량 Format
기존 업무 Source 삭제
공통 Framework 임의 수정
Build Script 전면교체
기존 API Breaking Change
기존 Test 삭제
```

공통 Framework 변경이 필요한 경우
EOS 업무 구현과 별도의 Architecture Decision으로 관리한다.

---

# 61. 구현 중 발견된 설계 Gap 처리

설계와 코드가 맞지 않으면
코드를 억지로 설계서에 맞추지 않는다.

다음 형식으로 기록한다.

| Gap ID | 설계 | 실제소스 | 영향 | 대안 | 권고 |
|---|---|---|---|---|---|

구분:

```text
설계 오류
현행소스 제약
Framework Gap
DB Gap
정책 미정
```

---

# 62. 구현 금지사항

다음을 하지 않는다.

```text
설계서 없이 임의 기능 추가

기존 Framework 분석 없이 새로운 공통 Framework 작성

업무별 Controller 난립

Controller → Mapper 직접호출

Handler에 업무로직

DAO에 상태전이

Mapper SQL에 권한 Rule

화면이 위험등급 결정

Client가 승인자 결정

Client가 상태를 자유롭게 변경

Service가 모든 기능을 가진 God Class

하나의 EOS_RESOURCE 테이블에 모든 업무 저장

예외기간 문자열 저장

Transaction 없이 여러 Table 순차 Update

외부 알림 실패로 본 업무 Rollback

동시성 검증 없는 승인

SELECT *

무제한 전체조회

Credential 로그

Test 없이 완료 선언

Build 실행 없이 성공 선언

주석으로만 미구현 처리 후 완료 선언
```

---

# 63. 구현 Architecture Gate

다음 구조가 유지되는지 확인한다.

```text
UI
 ↓
Entry
 ↓
Facade
 ↓
Service
 ↓
Rule
 ↓
DAO
 ↓
Mapper
 ↓
DB
```

각 계층 책임:

| 계층 | 책임 |
|---|---|
| UI | 입력/표시 |
| Entry | 거래 진입/전문 |
| Facade | Use Case 조합 |
| Service | 업무 처리/TX |
| Rule | 정책/판단 |
| DAO | 데이터 접근 |
| Mapper | SQL |
| DB | 정합성 |

실제 NSIGHT 구현구조와 다른 경우
실제 Source를 우선 분석하고 차이를 문서화한다.

---

# 64. 구현 단계별 산출물

```text
implementation/
├─ 00-source-analysis.md
├─ 01-implementation-plan.md
├─ 02-traceability.md
├─ 03-db-implementation.md
├─ 04-backend-implementation.md
├─ 05-ui-implementation.md
├─ 06-test-plan.md
├─ 07-test-result.md
├─ 08-gap-list.md
└─ 09-completion-report.md
```

필요 시 실제 프로젝트 디렉토리 규칙에 맞춘다.

---

# 65. 구현 완료 정의 Definition of Done

다음이 모두 만족되어야 `완료`라고 보고한다.

```text
1. 요구사항 구현
2. 화면 Event 연결
3. Service 구현
4. DB 구현
5. Transaction 검증
6. 권한 검증
7. Audit 구현
8. 오류처리
9. Unit Test
10. Integration Test
11. Build 성공
12. Application 실행
13. Smoke Test
14. Validation SQL 정상
15. Traceability 완성
16. 미해결 Issue 명시
```

하나라도 확인하지 못했다면

```text
미검증
부분완료
확인필요
```

로 보고하고 `완료`라고 표현하지 않는다.

---

# 66. 최종 실행 지시

이제 제공된 EOS 요구사항, 화면설계, DB설계, 서비스설계와
실제 NSIGHT/PDMG/TCF Repository를 분석하여 구현을 시작하라.

다음 순서를 반드시 지킨다.

```text
STEP 01 실제 Source 분석
STEP 02 Reference 업무 선정
STEP 03 설계-소스 Gap 분석
STEP 04 요구사항 Traceability
STEP 05 Implementation Plan
STEP 06 DB 구현
STEP 07 DTO
STEP 08 DAO/Mapper
STEP 09 Rule
STEP 10 Service
STEP 11 Facade
STEP 12 Handler/API
STEP 13 화면
STEP 14 Workflow
STEP 15 Audit/권한
STEP 16 Batch/알림
STEP 17 Unit Test
STEP 18 Integration Test
STEP 19 Build
STEP 20 Run
STEP 21 Smoke Test
STEP 22 Validation
STEP 23 Gap 정리
STEP 24 완료보고
```

한 단계에서 문제가 발생하면
문제를 숨기거나 임의로 우회하지 않는다.

```text
원인
영향
대안
적용한 해결방안
검증결과
```

를 남긴다.

---

# 67. 구현 시 최종적으로 답할 수 있어야 할 질문

구현 결과만으로 다음 질문에 답할 수 있어야 한다.

```text
어떤 요구사항이 어떤 프로그램으로 구현되었는가?

화면의 이 버튼은 어떤 Service를 호출하는가?

그 Service는 어떤 Rule을 수행하는가?

어떤 Table을 읽고 변경하는가?

Transaction은 어디에서 시작되고 끝나는가?

업무오류가 발생하면 무엇이 Rollback되는가?

Timeout이 발생하면 상태는 어떻게 되는가?

위험등급은 누가 계산하는가?

조치상태 전이를 누가 통제하는가?

예외 신청자와 승인자는 어떻게 분리되는가?

동시에 두 명이 승인하면 어떻게 되는가?

Lifecycle이 변경되면 어떤 Resource가 영향을 받는가?

Version Drift는 어떻게 탐지되는가?

누가 언제 어떤 값을 바꿨는가?

어떤 테스트가 이 기능의 정상동작을 증명하는가?

Build와 실행이 실제로 성공했는가?
```

---

# 68. LLM/Coding Agent 실행 규칙

이 프롬프트를 사용하는 Coding Agent는 다음 원칙을 지킨다.

## 구현 전

```text
읽기
→ 분석
→ 계획
→ 구현
```

## 구현 중

```text
작은 기능
→ Test
→ 검증
→ 다음 기능
```

## 구현 후

```text
Test
→ Build
→ Run
→ Smoke
→ Traceability
→ 완료보고
```

절대 다음 순서로 작업하지 않는다.

```text
대량 코드 생성
→ 나중에 컴파일 확인
```

---

# 69. 작업 지시 예시

실제 실행 시 다음과 같이 지시한다.

```text
이 구현 마스터 프롬프트를 최상위 규칙으로 적용한다.

입력자료:
- EOS 요구사항 정의서
- EOS 화면설계서
- EOS DB설계서
- EOS 서비스설계서
- 현재 NSIGHT/PDMG/TCF Repository

먼저 코드를 작성하지 말고

1. 실제 Repository 구조를 분석하고
2. 가장 유사한 Reference 업무를 선정하고
3. EOS 설계서와 실제 Source의 Gap을 정리하고
4. 구현할 파일 목록과 순서를 IMPLEMENTATION-PLAN.md로 작성한 후
5. P0 기능부터 단계적으로 구현한다.

각 기능은 반드시 Test → Build 검증 후 다음 기능으로 진행한다.

기존 공통 Framework를 임의 변경하지 말고
필요한 경우 Architecture Decision 후보로 분리한다.

최종 완료 전
요구사항 → 화면 → Service → Program → DB → Test
추적성 Matrix를 완성한다.
```

---

# 70. 구현 결과 품질 기준

최종 구현은 다음 세 조건을 동시에 만족해야 한다.

## 1. 개발자가 이해 가능

```text
어디에 어떤 코드를 추가해야 하는지 명확
```

## 2. 아키텍트가 검증 가능

```text
계층
Transaction
Rule
보안
상태
데이터
추적성
```

이 명확해야 한다.

## 3. 운영자가 신뢰 가능

```text
오류
Timeout
Audit
Batch
재처리
이력
```

이 설계되어야 한다.

최종 결과는 단순 CRUD 프로그램이 아니라

```text
EOS Lifecycle
+ Risk
+ Action
+ Exception
+ Governance
+ Audit
```

가 구현된 **운영 가능한 EOS 자원관리 시스템**이어야 한다.
