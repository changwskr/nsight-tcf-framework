# 농협상호금융 정보계 EOS 자원관리 시스템
# 서비스 설계서 작성 마스터 프롬프트

## 0. 프롬프트 사용 목적

이 프롬프트는 농협상호금융 정보계 **EOS 자원관리 시스템**의
요구사항 정의서, 화면설계서, 데이터베이스 설계서를 입력으로 받아
실제 개발·검토·테스트에 사용할 수 있는 **서비스 설계서**를 작성하기 위한 실행 프롬프트다.

기본 입력자료는 다음을 우선 사용한다.

```text
1. NH_상호금융정보계_EOS_자원관리_샘플양식.xlsx
2. EOS_자원관리_분석_요구사항_도출_마스터프롬프트.md
3. EOS 요구사항 정의서
4. EOS_자원관리_화면설계서_작성_마스터프롬프트.md
5. EOS 화면설계서
6. EOS_자원관리_데이터베이스_설계서_작성_마스터프롬프트.md
7. EOS 데이터베이스 설계서
8. 추가 제공되는 API 표준, ServiceId 표준, 인증/권한 정책, 개발 프레임워크 기준
```

이 작업의 목적은 화면마다 단순 CRUD API를 하나씩 만드는 것이 아니다.

EOS 업무 흐름을 다음 **서비스 책임 단위**로 분해해야 한다.

```text
자원 등록/조회
→ Product / Version / Lifecycle 관리
→ EOS 상태 자동판정
→ 위험도 평가
→ 조치계획
→ 조치 상태전이
→ 완료검증
→ 예외 신청
→ 예외 승인
→ 예외 월간점검
→ 월간 Snapshot/보고
→ 점검/자동수집
→ Version Drift
→ 코드/정책
→ 알림
→ 변경이력/감사
```

최종 산출물은 단순 API 목록이 아니라 다음을 모두 포함하는
**실제 구현 가능한 서비스 설계서**여야 한다.

```text
업무 서비스 식별
+ Service/API 계약
+ 요청/응답
+ 계층별 책임
+ 처리순서
+ Validation
+ 업무규칙
+ 상태전이
+ Transaction
+ DB CRUD
+ 동시성
+ 권한
+ 감사로그
+ 알림
+ 오류/Timeout
+ Batch/비동기
+ 외부연계
+ 테스트
+ 화면·요구사항·DB 추적성
```

---

# 1. 역할

너는 다음 역할을 동시에 수행한다.

- 농협상호금융 정보계 수석 애플리케이션 아키텍트
- EOS/EOL Lifecycle 업무 설계자
- Service/API 아키텍트
- Java/Spring 기반 백엔드 설계 전문가
- NSIGHT/PDMG/TCF 거래구조 설계자
- 데이터베이스·MyBatis 연계 설계자
- 보안·권한·감사 설계자
- 운영·배치·알림 설계자
- 요구사항 추적성 검토자
- PMO·Architecture Gate 검토자

너의 핵심 임무는 다음이다.

```text
화면 버튼 수만큼 API를 만드는 것이 아니라,
업무 책임과 Transaction 경계를 기준으로 서비스를 식별하고,
각 서비스가 어떤 데이터를 읽고 변경하며,
어떤 업무규칙과 권한을 적용하고,
정상·오류·Timeout 상황에서 어떻게 종료되는지 정의한다.
```

---

# 2. 절대 원칙

## 2.1 Source First

반드시 요구사항 정의서, 화면설계서, DB설계서를 먼저 분석한다.

확인되지 않은 ServiceId, Endpoint, 클래스명, Table, 상태코드, 오류코드를
확정사항처럼 임의 생성하지 않는다.

모든 설계 내용은 다음 네 가지로 구분한다.

```text
[원본확인]
입력자료에서 직접 확인된 사실

[설계반영]
확정 요구사항을 서비스 구조로 구현한 내용

[추가제안]
아키텍처·운영·보안·성능 관점에서 추가하는 내용

[확인필요]
업무 또는 아키텍처 의사결정이 필요한 내용
```

---

## 2.2 Screen Event ≠ Service 1:1

다음 방식은 금지한다.

```text
화면 버튼 하나
→ Service 하나

Grid 조회
→ Service 하나
Row 클릭
→ Service 하나
Tab 이동
→ Service 하나
```

화면 Event와 서비스 관계는 다음처럼 다양할 수 있다.

```text
화면 Event 1개
→ Service 1개

화면 Event 1개
→ Service 여러 개 조합

여러 화면 Event
→ 같은 조회 Service 재사용
```

서비스는 **업무 책임과 Use Case**를 기준으로 식별한다.

---

## 2.3 Controller를 업무 책임의 중심으로 두지 않는다

대상 시스템이 NSIGHT/PDMG/TCF 구조를 사용하는 경우
업무별 Controller를 중심으로 설계하지 않는다.

기본 실행구조는 입력자료의 실제 프레임워크 기준을 사용한다.

TCF 기반이라면 다음 구조를 우선 검토한다.

```text
화면
  ↓
공통 Online Endpoint
  ↓
ServiceId
  ↓
TCF / Dispatcher
  ↓
Handler
  ↓
Facade
  ↓
Service
  ├─ Rule
  └─ DAO / Mapper
```

일반 REST 구조라면 다음과 같이 적용할 수 있다.

```text
REST Controller
  ↓
Application Service / Facade
  ↓
Domain/Business Service
  ↓
Repository / DAO / Mapper
```

**어느 구조를 적용할지 입력자료에서 먼저 확인한다.**

확인되지 않으면 `[확인필요]`로 명시하고
두 구조의 차이를 비교한다.

---

## 2.4 서비스는 Transaction 책임을 명확히 해야 한다

모든 변경 서비스는 다음을 반드시 정의한다.

```text
Transaction 시작 위치
읽기/쓰기 범위
Commit 조건
Rollback 조건
Timeout
외부연계 포함 여부
비동기 처리 여부
재시도 여부
Idempotency 필요 여부
```

---

## 2.5 파생값은 사용자가 임의로 변경하지 못하게 한다

다음 값은 서비스가 정책에 따라 산정하는지 확인한다.

```text
EOS 잔여일수
현재상태
위험 총점
위험등급
예외 만료상태
Version Drift
월간 KPI
```

화면에서 값을 받아 그대로 DB에 저장하는 방식은 금지한다.

---

## 2.6 상태전이는 서비스에서 통제한다

다음 상태는 직접 UPDATE SQL로 변경하지 않는다.

```text
조치상태
예외상태
승인상태
점검상태
Drift 처리상태
```

각 상태변경은 서비스의 업무규칙을 통해 수행한다.

---

# 3. 서비스 설계 입력자료 분석

먼저 다음 입력자료별 역할을 분석한다.

| 입력자료 | 서비스 설계 확인사항 |
|---|---|
| 요구사항 | 기능·업무규칙·Workflow·권한 |
| 화면설계 | Event·입력·조회·Button·Validation |
| DB설계 | Entity·Table·Transaction 대상 |
| 코드기준표 | 상태·위험·조치·승인 코드 |
| 인증정책 | 사용자·조직·Role |
| 연계정의 | 조직·알림·수집 Agent·파일 |
| 프레임워크 기준 | ServiceId·Handler·Transaction·Timeout |

---

# 4. 1단계 — 업무 Use Case Inventory

먼저 전체 Use Case를 목록화한다.

기본 후보:

```text
EOS-UC-001 EOS 통합현황 조회
EOS-UC-002 EOS 자원 목록조회
EOS-UC-003 EOS 자원 상세조회
EOS-UC-004 EOS 자원 등록
EOS-UC-005 EOS 자원 수정
EOS-UC-006 EOS 자원 폐기

EOS-UC-010 Product 조회
EOS-UC-011 Product 등록/수정
EOS-UC-012 Product Version 관리
EOS-UC-013 Lifecycle 등록/변경
EOS-UC-014 Lifecycle 변경 영향조회

EOS-UC-020 위험평가 조회
EOS-UC-021 위험평가 임시저장
EOS-UC-022 위험평가 완료
EOS-UC-023 위험 재평가

EOS-UC-030 조치계획 생성
EOS-UC-031 조치계획 수정
EOS-UC-032 조치상태 변경
EOS-UC-033 조치 완료검증

EOS-UC-040 예외 신청
EOS-UC-041 예외 신청조회
EOS-UC-042 예외 승인
EOS-UC-043 예외 반려
EOS-UC-044 예외 연장
EOS-UC-045 예외 종료
EOS-UC-046 예외 월간점검

EOS-UC-050 월간 Snapshot 생성
EOS-UC-051 월간보고 조회
EOS-UC-052 우선조치 대상 조회

EOS-UC-060 점검규칙 조회
EOS-UC-061 수집실행 요청
EOS-UC-062 수집결과 조회
EOS-UC-063 Version Drift 조회
EOS-UC-064 Drift 확인/해소

EOS-UC-070 코드/정책 조회
EOS-UC-071 코드/정책 변경

EOS-UC-080 변경이력 조회
EOS-UC-081 감사이력 조회
```

실제 입력자료와 비교하여
필요/통합/분리/삭제/추가 여부를 판단한다.

---

# 5. 2단계 — 서비스 식별 원칙

Use Case를 다음 기준으로 Service로 묶는다.

```text
업무 책임이 같은가?
Transaction 경계가 같은가?
권한이 같은가?
데이터 변경단위가 같은가?
실패 시 함께 Rollback되어야 하는가?
독립적으로 재사용할 필요가 있는가?
```

예:

```text
자원 상세조회
+ Lifecycle 조회
+ 최근 위험등급
+ 진행중 조치
+ 유효 예외
```

이것을 화면이 한 번에 필요로 한다면
다음 두 대안을 비교한다.

```text
A. 화면이 5개 서비스를 각각 호출
B. Resource Detail Query Service가 조합하여 반환
```

N+1 API 호출, 권한, 응답시간, 서비스 재사용성을 비교하여 결정한다.

---

# 6. 3단계 — 서비스 ID/명명체계

프로젝트의 기존 ServiceId 표준이 제공되면 그 기준을 우선한다.

확인되지 않은 경우 임의 확정하지 않는다.

서비스 설계서에서는 최소 다음 식별자를 관리한다.

```text
Service Design ID
Use Case ID
ServiceId 또는 API ID
업무영역
처리유형
서비스명
```

예시 형식은 `[추가제안]`으로만 사용한다.

```text
EOS.Resource.selectList
EOS.Resource.selectDetail
EOS.Resource.create
EOS.Risk.evaluate
EOS.Action.createPlan
EOS.Exception.request
EOS.Exception.approve
```

실제 NSIGHT/PDMG ServiceId의 물리 형식이 따로 있으면
그 형식을 적용한다.

---

# 7. 4단계 — 전체 서비스 목록

다음 표로 전체 서비스 목록을 작성한다.

| 서비스설계ID | ServiceId/API | 서비스명 | 유형 | 주요화면 | Transaction | 권한 | 주요 Table | 우선순위 |
|---|---|---|---|---|---|---|---|---|

서비스 유형:

```text
QUERY
COMMAND
WORKFLOW
BATCH
INTEGRATION
ADMIN
```

---

# 8. 5단계 — 조회 서비스 설계 원칙

조회 서비스는 다음을 정의한다.

```text
조회조건
기본값
Paging
Sorting
Filter
권한 범위
조회 Column
Code Name 조합
계산값
성능조건
Empty Result
Export와의 관계
```

다음 조회는 별도로 검토한다.

```text
Dashboard
자원 통합검색
자원 상세
Lifecycle 영향조회
위험평가 이력
조치계획 목록
예외 목록
월간보고
Drift 목록
감사이력
```

---

# 9. 6단계 — Command 서비스 설계 원칙

변경 서비스는 다음을 반드시 정의한다.

```text
입력
Validation
권한
현재상태 확인
동시성 확인
Transaction
DB 변경
History
Audit
Notification
응답
```

예:

```text
자원 수정 요청
  ↓
권한 검증
  ↓
Resource 존재 확인
  ↓
Version No 동시성 확인
  ↓
업무 Validation
  ↓
Resource Update
  ↓
History 기록
  ↓
Audit 기록
  ↓
Commit
```

---

# 10. 7단계 — Dashboard 서비스 설계

Dashboard는 UI에서 여러 SQL을 직접 호출하지 않는다.

최소 다음을 설계한다.

```text
기준일
NSIGHT영역
자원구분
환경
담당조직
```

응답 후보:

```text
summary
riskDistribution
areaSummary
priorityActions
upcomingDeadlines
alerts
```

KPI별 산정 정의가 확정되어야 한다.

특히 다음을 확인한다.

```text
"위험 상태"의 정확한 정의
Critical/High와의 관계
금월 목표조치 산정식
예외필요 산정식
```

정의가 없으면 `[확인필요]`.

---

# 11. 8단계 — EOS 자원 조회 서비스

## 목록조회

입력 후보:

```text
resourceId
nsightArea
systemService
resourceType
productName
environment
center
ownerOrg
eosFrom
eosTo
currentStatus
riskLevel
actionStatus
exceptionYn
pageNo
pageSize
sort
```

출력 핵심:

```text
resourceId
systemService
product
currentVersion
eosDate
remainingDays
currentStatus
riskLevel
actionStatus
exceptionStatus
ownerOrg
```

Paging은 DB 서버 페이징을 우선 검토한다.

---

# 12. 9단계 — 자원 상세조회 서비스

자원 상세화면의 여러 Tab을 한 번에 조회할지
Tab별 지연조회할지 비교한다.

대안:

```text
A. selectResourceDetail
   ├─ basic
   ├─ lifecycle
   ├─ risk
   ├─ action
   ├─ exception
   └─ collection

B. Tab별 개별 Query Service
```

초기 화면속도, 데이터량, API 복잡도, 권한을 비교한다.

---

# 13. 10단계 — 자원 등록 서비스

최소 처리흐름:

```text
Request
  ↓
필수값 검증
  ↓
자원ID 중복 확인
  ↓
Product/Version 존재 확인
  ↓
환경/조직/코드 검증
  ↓
PROD 담당조직 필수 확인
  ↓
Resource 생성
  ↓
Installation/Version 관계 생성
  ↓
초기 상태 산정
  ↓
변경/감사로그
  ↓
Commit
```

초기 위험평가 자동생성 여부는 확인한다.

---

# 14. 11단계 — 자원 수정 서비스

다음 변경을 구분한다.

```text
일반 속성 변경
현재 Version 변경
Product 변경
Lifecycle 연결 변경
담당조직 변경
폐기
```

Version 변경 시 다음을 검토한다.

```text
기존 Installation 종료
신규 Installation 생성
상태 재산정
위험 재평가 필요 플래그
Drift 해소
History
```

단순 `UPDATE CURRENT_VERSION`로 끝내지 않는다.

---

# 15. 12단계 — Product/Lifecycle 서비스

서비스 후보:

```text
Product 목록/상세
Product 등록/수정
Version 등록/수정
Lifecycle 등록
Lifecycle 변경
Lifecycle 변경 영향조회
```

Lifecycle 변경 시 중요한 처리:

```text
Product Version Lifecycle 변경
  ↓
해당 Version 사용 Resource 조회
  ↓
잔여일수/상태 재산정
  ↓
위험평가 영향 여부 판단
  ↓
Critical/High 신규 발생 탐지
  ↓
담당자 알림 후보
```

일괄 상태 재산정을 동기 Transaction으로 할지
Batch/Event로 분리할지 비교한다.

---

# 16. 13단계 — EOS 상태판정 서비스

상태판정은 공통 업무 Rule로 분리할 수 있는지 검토한다.

입력:

```text
baseDate
eosDate
exceptionValid
statusPolicy
```

출력:

```text
remainingDays
currentStatus
```

예:

```text
calculateRemainingDays()
determineEosStatus()
```

단, 실제 클래스/메서드명은 프레임워크 표준이 확인된 경우에만 확정한다.

---

# 17. 14단계 — 위험평가 조회 서비스

다음 데이터를 반환한다.

```text
Resource 요약
현재 Lifecycle
7개 평가요소
현재 점수
총점
위험등급
평가의견
평가자
평가일
과거평가
```

평가정책도 함께 제공할지 검토한다.

---

# 18. 15단계 — 위험평가 저장/완료 서비스

`임시저장`과 `평가완료`를 분리할지 검토한다.

## 평가 완료 처리

```text
Resource 존재
  ↓
평가권한
  ↓
7개 항목 존재
  ↓
각 Score 1~5 검증
  ↓
총점 산정
  ↓
위험등급 정책 적용
  ↓
Assessment 저장
  ↓
Detail Score 저장
  ↓
Critical/High 후속 Action 검사
  ↓
History/Audit
  ↓
Commit
```

사용자가 `totalScore`, `riskLevel`을 전달하더라도
서버에서 다시 계산하는 것을 원칙으로 검토한다.

---

# 19. 16단계 — 조치계획 생성 서비스

입력:

```text
resourceId
currentVersion
targetVersion
actionType
actionDescription
impactScope
preTask
testPlan
transitionType
rollbackPlan
ownerOrg
ownerUser
plannedStartDate
targetCompleteDate
```

Validation:

```text
Critical/High 대상 여부
목표일
목표 Version
Rollback
담당자
```

업무정책상 `Exception`도 조치유형에 포함될 경우
예외신청 Workflow와 중복 책임을 분석한다.

---

# 20. 17단계 — 조치상태 변경 서비스

상태전이를 반드시 서버에서 검증한다.

후보 상태:

```text
미착수
계획수립
진행중
테스트중
완료
보류
예외관리
```

서비스 처리:

```text
현재 상태 조회
→ 요청 상태
→ State Transition Rule
→ 필요 증빙 검증
→ 변경
→ 상태이력
→ Audit
```

금지 예:

```text
미착수 → 완료
```

허용 여부는 실제 정책을 확인한다.

---

# 21. 18단계 — 조치 완료검증 서비스

완료는 단순 상태 UPDATE가 아니다.

다음을 검증한다.

```text
실제완료일
목표 Version 적용
테스트 결과
운영 반영 확인
완료 증빙
점검 결과
검증자
```

완료 후 다음 연계처리를 검토한다.

```text
Resource 현재 Version 갱신
Lifecycle 재연결
EOS 상태 재산정
Risk 재평가 필요
Drift 해소
Action 완료
Exception 종료 가능성
```

한 Transaction으로 할지 단계별로 분리할지 판단한다.

---

# 22. 19단계 — 예외 신청 서비스

처리흐름:

```text
신청권한
  ↓
Resource 조회
  ↓
EOS/Risk 상태 확인
  ↓
기존 유효 예외 확인
  ↓
예외 시작/종료일 검증
  ↓
보완대책 필수
  ↓
최종전환계획 필수
  ↓
종료기준 필수
  ↓
Exception 생성
  ↓
Workflow 상태=신청
  ↓
감사
  ↓
승인대상 알림
```

`종료일 >= 시작일`은 반드시 서버에서 재검증한다.

---

# 23. 20단계 — 예외 승인/반려 서비스

승인자는 신청내용을 임의변경하지 않는다.

승인 서비스 입력:

```text
exceptionId
approvalStatus
approvalCondition
approvalComment
```

서버에서 결정:

```text
approverId
approvalDate
currentStatus
```

처리:

```text
승인권한
→ 신청자와 승인자 SoD
→ 현재상태=신청/검토중 확인
→ 예외기간 재검증
→ Approval 생성
→ Exception 상태변경
→ 감사
→ 신청자 통보
```

동시승인 방지를 위해 Optimistic Lock 또는 현재상태 재검증을 적용한다.

---

# 24. 21단계 — 예외 연장 서비스

연장은 날짜만 수정하는 기능이 아니다.

다음 정책을 검토한다.

```text
연장 = 신규 승인 필요
기존 승인 유지 금지
연장 사유 필수
보완대책 재검토
최종전환계획 재검토
위험도 재평가 여부
```

연장 이력과 승인 이력을 보존한다.

---

# 25. 22단계 — 예외 종료 서비스

종료조건:

```text
조치 완료
대체 완료
자원 폐기
예외기간 만료
기타 승인종료
```

자동 만료와 수동 종료를 구분한다.

---

# 26. 23단계 — 예외 월간점검 서비스

입력:

```text
exceptionId
checkYearMonth
securityStatus
incidentYn
compensatingControlOkYn
transitionProgressRate
newRisk
checkComment
evidence
```

Unique:

```text
Exception + YearMonth
```

이미 해당 월 점검이 있으면 신규등록/수정 정책을 정의한다.

---

# 27. 24단계 — 월간 Snapshot 생성 서비스

Snapshot은 재현 가능해야 한다.

처리:

```text
대상월 확인
→ 이미 확정 Snapshot 존재 여부
→ 기준일 결정
→ KPI 집계
→ 위험등급 집계
→ 예외 집계
→ 조치 집계
→ 우선조치 Top N
→ Snapshot 저장
→ 생성결과 검증
```

재생성 정책:

```text
DRAFT 재생성 가능
CONFIRMED 재생성 제한
```

실제 정책이 없으면 `[추가제안]`.

---

# 28. 25단계 — 월간보고 조회 서비스

입력:

```text
yearMonth
area
resourceType
```

응답:

```text
summary
previousMonth
change
priorityItems
issues
nextActions
```

실시간 원장 조회와 Snapshot 조회를 혼합하지 않는다.

---

# 29. 26단계 — 점검규칙 조회 서비스

`07_점검명령어`의 관리기준을 서비스화한다.

조회:

```text
resourceType
product
checkMethod
autoYn
```

운영명령 자체를 일반 사용자가 볼 수 있는지 권한을 확인한다.

---

# 30. 27단계 — 수집실행 서비스

운영서버 원격명령 실행은 고위험 기능이다.

직접 실행 구조를 무조건 설계하지 않는다.

다음 대안을 비교한다.

```text
A. EOS 서버가 원격 Shell 실행
B. 설치 Agent가 수집
C. 기존 운영도구/API 연계
D. 수동 수집결과 Upload
```

보안정책이 없으면 직접 원격명령은 `[확인필요]`.

서비스 설계 시 다음을 정의한다.

```text
실행권한
대상 Resource
수집 Rule
Timeout
실패처리
Credential 관리
Raw Result 보안
감사
```

---

# 31. 28단계 — 수집결과 처리 서비스

수집 결과에서 다음을 처리한다.

```text
발견 Product
발견 Version
수집일
결과상태
오류
```

관리 Version과 비교:

```text
MATCH
MISMATCH
COLLECT_FAILED
NOT_COLLECTED
REVIEW_REQUIRED
```

Drift 생성/갱신 정책을 정의한다.

---

# 32. 29단계 — Drift 해소 서비스

해소 방식 후보:

```text
관리원장 수정
실제 환경 수정
오탐 승인
수집 재실행
```

해소 시 다음을 기록한다.

```text
resolutionType
comment
resolvedBy
resolvedAt
evidence
```

---

# 33. 30단계 — 코드/정책 관리 서비스

다음 변경은 일반 CRUD보다 강한 검증이 필요하다.

```text
위험등급 점수구간
EOS 상태 임계값
진행상태
승인상태
```

정책 변경 시 기존 데이터에 미치는 영향을 분석한다.

예:

```text
Critical 기준 32 → 30
```

다음을 결정해야 한다.

```text
기존 평가 재산정?
신규 평가부터 적용?
유효기간 정책?
```

---

# 34. 31단계 — 변경이력/감사 조회 서비스

검색조건:

```text
entityType
entityId
fieldName
changedBy
changedFrom
changedTo
```

응답:

```text
before
after
reason
user
datetime
approval
```

민감정보가 History에 평문 저장되지 않도록 한다.

---

# 35. 32단계 — 알림 서비스/이벤트 설계

알림 Trigger:

```text
EOS 12개월 전
EOS 6개월 전
EOS 3개월 전
EOS 도래
Critical 발생
High 발생
조치 목표일 임박
조치 목표일 초과
예외 만료 임박
예외 만료
월간점검 미수행
Version Drift
```

동기 서비스 처리 중 직접 발송할지
이벤트/비동기로 분리할지 비교한다.

권장 검토:

```text
업무 Transaction Commit
→ Domain/Application Event
→ Notification Processor
→ 발송
```

알림 실패가 원 업무 Transaction을 Rollback시키지 않도록 검토한다.

---

# 36. 33단계 — Batch 서비스 설계

다음 Batch 후보를 검토한다.

```text
EOS 상태 일일 재산정
EOS 임박 알림
예외 만료처리
예외 만료 알림
조치 목표일 초과 탐지
월간점검 미수행 탐지
월간 Snapshot 생성
Lifecycle 재검증 대상 추출
Drift 미해소 알림
```

Batch마다 다음을 정의한다.

```text
Job ID
Schedule
입력
처리범위
Chunk
재시도
중복실행 방지
실패복구
실행이력
```

---

# 37. 34단계 — 권한 서비스 규칙

최소 역할:

```text
조회자
자원담당자
위험평가자
조치담당자
예외신청자
예외승인자
EOS 관리자
PMO/관리자
```

각 서비스별 권한 Matrix:

| 서비스 | 조회자 | 자원담당 | 평가자 | 조치담당 | 신청자 | 승인자 | 관리자 |
|---|---|---|---|---|---|---|---|

서버에서 권한검증을 반드시 수행한다.

화면 버튼 숨김만으로 보안을 구현하지 않는다.

---

# 38. 35단계 — 감사로그 서비스 규칙

다음 서비스는 감사대상으로 검토한다.

```text
자원 등록/수정/폐기
Product Version 변경
Lifecycle 변경
위험평가 완료
조치상태 변경
조치 완료
예외 신청
예외 승인/반려/연장/종료
정책 변경
수집 실행
Drift 해소
```

감사정보:

```text
userId
orgId
serviceId/API
resourceId
action
before/after
result
timestamp
traceId
```

---

# 39. 36단계 — 요청/응답 표준

프로젝트의 표준 전문이 제공되면 반드시 그 구조를 사용한다.

NSIGHT/PDMG 전문이 적용되는 경우 입력자료의 실제 구조를 기준으로 한다.

예시 개념:

```text
Header
  ├─ ServiceId
  ├─ User
  ├─ Organization
  ├─ Trace
  └─ Channel

DTO
  └─ 업무 입력
```

응답:

```text
공통 결과
+ 업무 DTO
```

실제 전문 구조가 없으면
일반적인 JSON 구조를 임의 확정하지 않는다.

---

# 40. 37단계 — 서비스별 Request 설계

각 Request Field에 대해 작성한다.

| 필드 | 타입 | 필수 | 최대길이 | 코드 | Validation | Source | 설명 |
|---|---|---|---:|---|---|---|---|

다음 값은 Client 입력을 신뢰하지 않는다.

```text
현재상태
위험총점
위험등급
승인자
승인일
감사사용자
잔여일수
Drift 상태
```

서버에서 계산/확정한다.

---

# 41. 38단계 — 서비스별 Response 설계

Response는 화면에 필요한 데이터만 반환한다.

대량 내부데이터를 그대로 반환하지 않는다.

각 Field:

| 필드 | 타입 | Nullable | Source | Masking | 설명 |
|---|---|---|---|---|---|

---

# 42. 39단계 — Validation 설계

Validation을 네 단계로 구분한다.

```text
1. 형식 Validation
2. 참조 Validation
3. 업무 Validation
4. 상태/권한 Validation
```

예:

```text
예외신청

형식:
startDate/endDate

참조:
Resource 존재

업무:
endDate >= startDate
보완대책 필수

상태:
기존 승인 예외 존재 여부

권한:
신청권한
```

---

# 43. 40단계 — 업무 Rule 분리

다음 Rule은 여러 서비스에서 재사용 가능하다.

```text
EOS 상태 산정
위험등급 산정
상태전이 검증
예외기간 검증
조치 완료 검증
Version Drift 판정
```

TCF의 Rule 계층을 사용하는 경우
부작용 없는 Rule로 분리하는 것을 검토한다.

Rule에서 직접 DB Update나 외부연계를 하지 않는다.

---

# 44. 41단계 — Transaction 설계

각 Command 서비스마다 표를 작성한다.

| 서비스 | TX | Read Tables | Write Tables | 외부연계 | Timeout | Rollback |
|---|---|---|---|---|---|---|

원칙:

```text
DB 변경은 가능한 하나의 업무 Transaction으로 처리

알림 등 부가처리는 Transaction Commit 이후 비동기 가능

외부 시스템 긴 호출을 DB TX 안에 넣는 것은 신중하게 판단

Timeout 발생 시 DB Rollback 여부 명확화
```

---

# 45. 42단계 — 동시성 설계

다음 대상에 Optimistic Lock을 검토한다.

```text
Resource
Action Plan
Exception Request
Risk Assessment
Policy
```

요청에 `versionNo`를 포함할지 검토한다.

충돌 응답:

```text
다른 사용자가 이미 변경했습니다.
최신 정보를 다시 조회한 후 처리해 주세요.
```

---

# 46. 43단계 — Idempotency 설계

다음 서비스는 중복호출 위험이 있다.

```text
자원 생성
예외 신청
예외 승인
월간 Snapshot 생성
Batch
수집 실행
알림
```

다음 방식 검토:

```text
Business Unique Key
Request ID
Idempotency Key
상태 재검증
```

---

# 47. 44단계 — 오류코드 설계

프로젝트 공통 오류코드 표준이 제공되면 해당 표준을 사용한다.

업무 오류 범주 후보:

```text
RESOURCE_NOT_FOUND
RESOURCE_DUPLICATE
INVALID_EOS_DATE
INVALID_RISK_SCORE
INVALID_ACTION_TRANSITION
EXCEPTION_PERIOD_INVALID
EXCEPTION_ALREADY_ACTIVE
APPROVAL_NOT_ALLOWED
CONCURRENT_MODIFICATION
SNAPSHOT_ALREADY_CONFIRMED
COLLECTION_FAILED
DRIFT_NOT_FOUND
```

실제 물리 오류코드 형식은 임의 확정하지 않는다.

각 오류마다 다음을 정의한다.

```text
오류구분
HTTP/공통 결과
업무코드
메시지
로그레벨
Rollback
사용자 조치
```

---

# 48. 45단계 — Timeout 설계

서비스 유형별 Timeout을 검토한다.

```text
일반조회
Dashboard
대량 Export
수집 실행
월간 Snapshot
Batch
```

대량 Export/수집을 일반 온라인 Timeout 안에서 처리하지 않는 구조를 검토한다.

Timeout 시:

```text
부분 Commit 금지
실행상태 정리
재시도 가능 여부
운영로그
```

---

# 49. 46단계 — 외부연계 설계

후보 외부 시스템:

```text
사용자/조직
메일/알림
CMDB
서버/Agent
SBOM/SCA
Vendor Lifecycle
파일 저장소
```

각 연계마다:

```text
연계ID
방향
Protocol
인증
Timeout
Retry
Circuit Breaker
데이터
오류처리
감사
```

확정된 연계만 최종 설계에 포함한다.

---

# 50. 47단계 — DAO/Mapper 설계

서비스별 DB 접근을 추적한다.

| 서비스 | DAO/Repository | Mapper | SQL ID | Table | CRUD |
|---|---|---|---|---|---|

NSIGHT/PDMG 구조라면
업무 Service가 직접 Mapper를 호출하지 않고
프로젝트 표준 계층을 따른다.

---

# 51. 48단계 — SQL 설계 기준

조회:

```text
조건검색
서버 Paging
정렬 화이트리스트
필요 Column만 조회
```

변경:

```text
PK 조건
Version No 동시성
영향 Row 검증
```

금지:

```text
SELECT *
무제한 전체조회
문자열 직접 ORDER BY 조립
상태코드 임의 Update
```

---

# 52. 49단계 — 서비스 처리흐름 ASCII

모든 주요 Command 서비스는 처리흐름을 ASCII로 작성한다.

예:

```text
[예외 승인 요청]
       ↓
[인증/권한]
       ↓
[Exception 조회]
       ↓
[현재상태 검증]
       ↓
[신청자≠승인자]
       ↓
[기간/보완대책 재검증]
       ↓
[Approval INSERT]
       ↓
[Exception 상태 UPDATE]
       ↓
[Audit INSERT]
       ↓
[TX COMMIT]
       ↓
[알림 Event]
```

---

# 53. 50단계 — 정상·오류·Timeout 흐름

각 핵심 서비스에 다음 세 가지를 작성한다.

## 정상

```text
요청
→ 검증
→ 업무처리
→ DB
→ Commit
→ 감사
→ 응답
```

## 업무오류

```text
요청
→ 업무 Rule 실패
→ DB 변경 없음
→ 업무 오류 응답
```

## 시스템오류/Timeout

```text
요청
→ 처리중 예외/Timeout
→ Rollback
→ 시스템 오류로그
→ 표준 오류 응답
```

---

# 54. 51단계 — 서비스별 상세설계 표준 형식

각 서비스는 반드시 다음 형식으로 작성한다.

# {서비스설계ID}. {서비스명}

## 1. 서비스 개요

| 항목 | 내용 |
|---|---|
| 서비스설계ID | |
| ServiceId/API | |
| 서비스명 | |
| 유형 | QUERY/COMMAND/... |
| 관련 화면 | |
| 관련 Event | |
| 관련 요구사항 | |
| 호출권한 | |
| Transaction | |
| Timeout | |
| Idempotency | |

## 2. 목적

## 3. 선행조건

## 4. 입력 전문/Request

## 5. 출력 전문/Response

## 6. 처리흐름

ASCII Flow를 작성한다.

## 7. Validation

| Rule ID | 구분 | 대상 | Rule | 오류 |
|---|---|---|---|---|

## 8. 업무규칙

## 9. 상태전이

## 10. Transaction

## 11. DB CRUD

| 순서 | Table | CRUD | 조건 | 목적 |
|---:|---|---|---|---|

## 12. DAO/Mapper/SQL

## 13. 권한

## 14. 감사로그

## 15. 알림/Event

## 16. 동시성

## 17. 오류처리

## 18. Timeout

## 19. 정상 예시

## 20. 금지 예시

## 21. 테스트 시나리오

## 22. 추적성

---

# 55. 서비스 테스트 시나리오

최소 다음을 작성한다.

```text
자원 목록 정상조회
조회조건 없음
Paging
자원 중복등록
PROD 담당조직 누락
Lifecycle 변경
Lifecycle 변경 영향자원 다수
EOS 상태 경계값
위험점수 1
위험점수 5
위험점수 범위초과
Critical 자동판정
조치계획 생성
잘못된 상태전이
완료증빙 없는 완료
예외 정상신청
예외기간 역전
기존 유효 예외 중복
신청자 본인승인
예외 조건부승인
동시승인
예외 만료
월간점검 중복
Snapshot 중복생성
수집 성공
수집 Timeout
Version Drift
동시 Resource 수정
권한 없는 사용자
DB 오류
Timeout
```

표준:

| Test ID | Service | Case | Input | Expected | Rollback | Audit |
|---|---|---|---|---|---|---|

---

# 56. 서비스-화면 추적성

| 화면ID | Event ID | Service | 요청 | 응답 | 오류 |
|---|---|---|---|---|---|

화면 Event가 서비스에 연결되지 않으면 누락으로 표시한다.

---

# 57. 서비스-DB 추적성

| Service | Table | CRUD | Transaction | Lock | SQL |
|---|---|---|---|---|---|

---

# 58. 서비스-요구사항 추적성

| 요구사항ID | Service | Rule | 화면 | Table | Test |
|---|---|---|---|---|---|

P0 요구사항이 서비스로 구현되지 않으면 설계 누락이다.

---

# 59. 서비스 프로그램 구조 추적성

TCF 기반 구조일 경우 다음 Matrix를 작성한다.

| ServiceId | Handler | Facade | Service | Rule | DAO | Mapper |
|---|---|---|---|---|---|---|

클래스명이 아직 확정되지 않았다면
역할과 생성 규칙만 제시하고 `[확인필요]`로 둔다.

---

# 60. 권장 EOS 서비스 도메인 분류

다음 도메인 후보를 검토한다.

```text
resource
product
lifecycle
risk
action
exception
report
collection
policy
audit
notification
```

패키지 구조가 도메인 우선 원칙이면 다음 개념을 적용할 수 있다.

```text
{base}.eos.resource.*
{base}.eos.risk.*
{base}.eos.action.*
{base}.eos.exception.*
```

실제 BASE Package는 프로젝트 기준을 확인한 후 적용한다.

---

# 61. 서비스 설계 대안 비교 ADR

최소 다음 ADR 후보를 작성한다.

## ADR-SVC-01
자원 상세 Aggregate Query vs Tab별 개별 Query

## ADR-SVC-02
Lifecycle 변경 시 동기 일괄 재산정 vs 비동기 Batch/Event

## ADR-SVC-03
위험평가 임시저장/완료 Service 분리 여부

## ADR-SVC-04
조치 완료 시 Resource Version 자동갱신 여부

## ADR-SVC-05
예외 승인 단일/다단계 Workflow

## ADR-SVC-06
월간 Snapshot 수동확정 vs 자동 Batch

## ADR-SVC-07
자원정보 수집 Agent/API/원격명령 방식

## ADR-SVC-08
알림 동기발송 vs 비동기 Event

## ADR-SVC-09
Dashboard 실시간 집계 vs Read Model/Cache

각 ADR:

```text
문제
대안
장점
단점
Transaction 영향
성능 영향
운영 영향
보안 영향
권고안
확인필요
```

---

# 62. 서비스 설계 금지 예시

다음 설계는 금지한다.

```text
화면 버튼마다 무조건 Service 1개 생성

Service가 화면 Layout에 종속

Controller가 직접 Mapper 호출

Handler에 업무 로직 작성

DAO에 업무규칙 작성

Client가 전달한 위험등급을 그대로 저장

Client가 전달한 승인자 ID 신뢰

상태값을 임의 UPDATE

예외 승인 시 신청자=승인자 허용

알림 실패 때문에 핵심 업무 Transaction Rollback

외부 API 장시간 호출을 DB TX 안에 무조건 포함

완료 처리에서 증빙/실제완료일 검증 없음

동시수정 검증 없음

조회 SQL SELECT *

대량 Export를 일반 온라인 응답으로 무제한 생성

수집 Shell 명령에 Credential 포함

Raw 수집결과를 무조건 로그에 출력

오류 발생 후 부분 Commit
```

---

# 63. 서비스 설계서 최종 문서 구조

최종 서비스 설계서는 프로젝트 문서작성 기준에 맞춰 다음 구조를 따른다.

# 1. 도입 전 안내말

# 2. 문서 개요
- 목적
- 적용범위
- 대상 독자
- 선행조건
- 용어 정의

# 3. 본문

## 3.1 문제 정의 및 설계 배경

## 3.2 현행 구조와 문제점

## 3.3 요구사항과 제약조건

## 3.4 서비스 설계 원칙

## 3.5 서비스 대안 비교 및 의사결정

## 3.6 목표 서비스 아키텍처

## 3.7 서비스 분류 및 표준 형식

## 3.8 전체 서비스 목록

## 3.9 서비스 구성요소 및 속성

## 3.10 책임 경계와 RACI

## 3.11 ServiceId/API 규칙

## 3.12 요청/응답 전문

## 3.13 정상 처리 흐름

## 3.14 오류·Timeout·장애 흐름

## 3.15 정상 예시

## 3.16 금지 예시

## 3.17 화면 연계 규칙

## 3.18 데이터/DB 연계 규칙

## 3.19 상태 및 Transaction 관리

## 3.20 권한·보안·개인정보·감사

## 3.21 알림·Batch·외부연계

## 3.22 성능·용량·확장성

## 3.23 운영·모니터링·장애 대응

## 3.24 자동검증 및 품질 Gate

## 3.25 서비스별 상세설계

### Resource
### Product/Lifecycle
### Risk
### Action
### Exception
### Report
### Collection
### Policy/Audit

## 3.26 테스트 시나리오

## 3.27 추적성 Matrix

## 3.28 체크리스트

## 3.29 변경·호환성·폐기 관리

# 4. 시사점
- 핵심 아키텍처 판단
- 주요 위험
- 우선 보완 과제
- 중장기 발전 방향

# 5. 마무리말

---

# 64. 반드시 포함할 결과표

최종 서비스 설계서에는 최소 다음 표를 포함한다.

1. Use Case Inventory
2. 전체 Service 목록
3. Service 유형 분류
4. ServiceId/API 목록
5. 화면 Event-Service Matrix
6. Service-DB CRUD Matrix
7. Service-Program Matrix
8. Service-권한 Matrix
9. Service-Transaction Matrix
10. Service-Timeout Matrix
11. Service-오류코드 Matrix
12. 상태전이표
13. Validation Rule 목록
14. 업무 Rule 목록
15. Batch 목록
16. 알림 Event 목록
17. 외부연계 목록
18. Audit 대상 목록
19. 테스트 Scenario
20. 요구사항 추적성 Matrix
21. ADR 후보
22. 확인필요사항

---

# 65. 서비스 설계 Quality Gate

최종 결과를 내기 전에 반드시 점검한다.

## Source

- [ ] 요구사항 정의서를 분석했는가?
- [ ] 화면 Event 전체를 분석했는가?
- [ ] DB Table/Column을 분석했는가?
- [ ] 원본확인과 추가제안을 구분했는가?

## 서비스 책임

- [ ] 화면 버튼 수만큼 Service를 만들지 않았는가?
- [ ] Use Case 중심으로 Service를 식별했는가?
- [ ] Query와 Command 책임이 구분되는가?
- [ ] Transaction 경계가 명확한가?

## 계층

- [ ] Handler/Facade/Service/Rule/DAO 책임을 침범하지 않는가?
- [ ] Controller/Handler에 업무로직이 과도하게 들어가지 않는가?
- [ ] DAO/Mapper에 업무규칙이 없는가?

## 데이터

- [ ] 모든 Command Service의 DB 변경대상이 정의됐는가?
- [ ] 위험총점/등급을 서버가 계산하는가?
- [ ] 상태전이를 서버에서 검증하는가?
- [ ] History/Audit 처리가 있는가?

## 예외

- [ ] 예외기간을 서버에서 검증하는가?
- [ ] 신청/승인 SoD가 있는가?
- [ ] 중복 유효 예외를 검증하는가?
- [ ] 만료/연장/종료가 설계되어 있는가?

## 조치

- [ ] 잘못된 상태전이를 차단하는가?
- [ ] 완료조건이 있는가?
- [ ] Version 변경 후 연계처리가 정의됐는가?

## 운영

- [ ] Batch 대상이 정의됐는가?
- [ ] 알림 실패와 업무 TX가 분리됐는가?
- [ ] 자동수집 Timeout/실패처리가 있는가?
- [ ] Drift 해소 프로세스가 있는가?

## 보안

- [ ] 서버 권한검증이 있는가?
- [ ] Client가 전달한 사용자/승인정보를 그대로 신뢰하지 않는가?
- [ ] 감사대상이 정의됐는가?
- [ ] 민감 Raw Result 통제가 있는가?

## 성능

- [ ] Paging이 있는가?
- [ ] Dashboard 호출전략이 있는가?
- [ ] 대량 Export/수집이 온라인 Thread를 장기점유하지 않는가?

## 추적성

- [ ] 화면 Event가 Service에 연결되는가?
- [ ] Service가 DB Table에 연결되는가?
- [ ] P0 요구사항이 Service에 연결되는가?
- [ ] Service가 테스트에 연결되는가?

하나라도 충족하지 못하면
서비스 설계 완료로 보고하지 않는다.

---

# 66. 최종 실행 지시

이제 제공된 다음 산출물을 기준으로 EOS 서비스 설계서를 작성하라.

```text
NH_상호금융정보계_EOS_자원관리_샘플양식.xlsx

EOS 요구사항 정의서
EOS 화면설계서
EOS 데이터베이스 설계서
```

필요한 경우 다음 작성 프롬프트도 기준으로 사용한다.

```text
EOS_자원관리_분석_요구사항_도출_마스터프롬프트.md
EOS_자원관리_화면설계서_작성_마스터프롬프트.md
EOS_자원관리_데이터베이스_설계서_작성_마스터프롬프트.md
```

다음 순서로 수행한다.

```text
1. Source 분석
2. 화면 Event Inventory
3. Use Case Inventory
4. Service 책임 분해
5. Service 목록
6. ServiceId/API 결정
7. Query Service 설계
8. Command Service 설계
9. Product/Lifecycle Service
10. Risk Service
11. Action Service
12. Exception/Approval Service
13. Report/Snapshot Service
14. Collection/Drift Service
15. Policy/Audit Service
16. Request/Response
17. Validation/Rule
18. 상태전이
19. Transaction
20. 권한/감사
21. 오류/Timeout
22. Batch/알림
23. DB CRUD/Mapper
24. 테스트
25. 추적성
26. ADR
27. Quality Gate
```

최종 목적은 단순히 API 목록을 만드는 것이 아니다.

다음 질문에 서비스 설계서만으로 답할 수 있어야 한다.

```text
이 화면 Event는 어떤 Service를 호출하는가?
Service는 어떤 Use Case를 책임지는가?
누가 호출할 수 있는가?
어떤 Validation이 수행되는가?
어떤 업무 Rule이 적용되는가?
어느 상태에서 호출 가능한가?
어떤 Table을 읽고 변경하는가?
Transaction은 어디까지인가?
Timeout이 발생하면 무엇이 Rollback되는가?
동시에 두 사용자가 수정하면 어떻게 되는가?
어떤 Audit가 남는가?
어떤 알림이 발생하는가?
실패 시 어떤 오류가 반환되는가?
어떤 테스트로 완료를 증명하는가?
```

최종 산출물은 Markdown 형식의 **EOS 서비스 설계서**로 작성하라.

가능하면 별도 부록으로 다음 Matrix도 생성하라.

```text
화면 Event ↔ Service
Service ↔ Program
Service ↔ DB
Service ↔ 권한
Service ↔ 오류
Service ↔ Test
요구사항 ↔ Service ↔ DB ↔ Test
```
