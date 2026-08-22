# EOS-RULES.md

> 농협상호금융 정보계 EOS 자원관리 시스템  
> Requirements · Design · Implementation · Verification 공통 불변 규칙

---

# 0. 문서 목적

`EOS-RULES.md`는 EOS 자원관리 시스템의
**요구사항 분석, 화면설계, 데이터베이스 설계, 서비스 설계, 구현, 테스트, 운영** 전 단계에서
반드시 적용해야 하는 공통 규칙을 정의한다.

이 문서는 `AGENT.md`의 실행 절차와 달리,
작업 단계가 달라져도 변하지 않아야 하는 **설계·개발 불변원칙**을 고정한다.

적용 범위:

```text
EOS 원본자료 분석
→ 요구사항
→ 화면
→ 데이터
→ 서비스
→ 소스코드
→ 테스트
→ 배포
→ 운영
→ 변경관리
```

---

# 1. 최상위 원칙

## RULE-001. Source First

모든 판단은 다음 순서로 한다.

```text
실제 Source
→ 승인된 최신 설계서
→ EOS 원본 Excel
→ 프로젝트 표준
→ 추가 설계 제안
```

추측으로 다음을 확정하지 않는다.

```text
Package
Class
ServiceId
Endpoint
Table
Column
Code
상태값
권한
Transaction
Timeout
Workflow
```

---

## RULE-002. 사실과 설계를 구분한다

모든 결과에는 다음 구분을 사용한다.

```text
[원본확인]
원본 Excel/문서에서 확인

[현행소스]
Repository에서 실제 확인

[설계반영]
확정 요구사항을 구조화

[구현변경]
이번 구현에서 변경

[추가제안]
아키텍처 관점 제안

[확인필요]
정책/현업 의사결정 필요
```

---

## RULE-003. 원본 오류를 임의 수정하지 않는다

다음과 같은 값을 발견하더라도 조용히 수정하지 않는다.

```text
예외기간 역전
잘못된 날짜서식
Dashboard 집계 불일치
위험점수/등급 불일치
Lifecycle 날짜 불일치
중복 자원ID
```

처리:

```text
Issue 등록
→ 영향분석
→ Validation 요구사항
→ 확인/보정 절차
```

---

# 2. EOS 업무 모델 규칙

## RULE-010. EOS는 단순 자산대장이 아니다

EOS 시스템은 다음 업무를 하나의 Lifecycle로 관리한다.

```text
Resource
→ Lifecycle
→ Risk
→ Action
→ Exception
→ Verification
→ Audit
```

---

## RULE-011. Product / Version / Resource를 분리한다

다음 세 개는 서로 다른 개념이다.

```text
Product
예: Apache Tomcat

Product Version
예: 8.5.x

Resource
예: 특정 서버/논리자원에 설치된 Tomcat
```

한 Entity로 합치지 않는다.

---

## RULE-012. Lifecycle 날짜를 분리한다

다음을 한 날짜로 통합하지 않는다.

```text
EOS
EOL
계약종료일
조치목표일
실제완료일
예외시작일
예외종료일
최종전환목표일
```

---

## RULE-013. 상태를 하나로 합치지 않는다

다음 상태축은 별도로 관리한다.

```text
EOS 현재상태
위험등급
조치상태
예외상태
승인상태
수집상태
Drift 상태
```

`STATUS` 하나로 통합하지 않는다.

---

# 3. EOS 상태 규칙

## RULE-020. EOS 상태는 서버가 계산한다

기본 입력:

```text
기준일
EOS Date
예외 유효여부
상태정책
```

기본 출력:

```text
잔여일수
EOS 현재상태
```

Client가 `remainingDays`, `currentStatus`를 최종 결정하지 않는다.

---

## RULE-021. EOS 상태 경계값은 정책화한다

샘플 기준:

```text
정상 : 12개월 이상
주의 : 6~12개월
경고 : 3~6개월
위험 : 3개월 미만 또는 EOS 도래
예외 : 승인된 한시적 상태
```

실제 `06_코드기준표`가 다르면 원본 기준을 우선한다.

---

## RULE-022. 예외는 위험 자체를 제거하지 않는다

예외승인으로 인해 EOS 위험이 사라지는 것으로 처리하지 않는다.

논리적으로 다음을 분리한다.

```text
위험상태
+
예외승인상태
```

---

# 4. 위험평가 규칙

## RULE-030. 위험평가 기본 요소

원본 샘플 기준:

```text
업무중요도
운영환경
외부노출
보안취약점
장애영향도
대체난이도
EOS상태
```

각 요소 점수:

```text
1 ~ 5
```

---

## RULE-031. 위험총점은 서버가 계산한다

금지:

```text
Client가 totalScore 전송
→ 그대로 저장
```

필수:

```text
Detail Score
→ 서버 합산
→ 정책 적용
→ Risk Level
```

---

## RULE-032. 위험등급도 서버가 계산한다

샘플 기준:

```text
32 이상 → Critical
26~31   → High
20~25   → Medium
19 이하 → Low
```

실제 코드기준표가 다르면 해당 기준을 따른다.

---

## RULE-033. 위험평가 이력을 보존한다

재평가 시 기존 평가를 덮어쓰지 않는다.

최소 보존:

```text
평가일
평가자
항목별 점수
총점
등급
평가의견
적용정책
```

---

# 5. 조치계획 규칙

## RULE-040. High/Critical은 후속조치가 있어야 한다

다음 상태를 방치하지 않는다.

```text
Critical
High
```

최소 하나가 필요하다.

```text
조치계획
또는
유효한 예외승인
```

---

## RULE-041. 조치계획은 일정만 관리하지 않는다

필수 검토항목:

```text
현재 Version
목표 Version
조치유형
영향범위
선행작업
테스트계획
전환방식
Rollback
담당조직
착수일
목표일
실제완료일
진행상태
```

---

## RULE-042. 조치상태는 상태전이 규칙을 따른다

기본 후보:

```text
미착수
→ 계획수립
→ 진행중
→ 테스트중
→ 완료
```

금지 예:

```text
미착수 → 완료
```

정책이 허용하지 않는 전이를 SQL로 직접 변경하지 않는다.

---

## RULE-043. 완료는 검증 후 처리한다

완료 시 최소 확인:

```text
실제완료일
목표 Version
테스트 결과
운영반영
증빙
검증자
```

---

# 6. 예외관리 규칙

## RULE-050. 예외는 단순 보류가 아니다

예외에는 최소 다음이 있어야 한다.

```text
예외기간
예외사유
즉시조치 불가사유
임시보완대책
최종전환계획
최종전환목표일
종료기준
승인
월간점검
```

---

## RULE-051. 예외기간 Validation

필수:

```text
예외 종료일 >= 예외 시작일
```

---

## RULE-052. 예외 신청자와 승인자를 분리한다

최소 SoD:

```text
REQUESTER != APPROVER
```

화면 버튼 숨김만으로 처리하지 않는다.

서버에서 검증한다.

---

## RULE-053. 예외 연장은 재승인이다

금지:

```text
기존 승인건의 종료일만 변경
```

원칙:

```text
연장사유
→ 재검토
→ 재승인
→ 이력보존
```

---

## RULE-054. 예외 만료는 자동 식별한다

예외 종료일이 지나면 다음 중 정책에 따라 처리한다.

```text
만료
위험복귀
재승인 필요
알림
```

---

# 7. Product Lifecycle 규칙

## RULE-060. Lifecycle은 공식근거를 가진다

EOS/EOL에는 가능한 다음 정보를 연결한다.

```text
Vendor
Source Type
Source URL/문서
확인일
확인자
증빙
```

---

## RULE-061. Lifecycle 변경이력을 보존한다

금지:

```text
EOS Date 단순 덮어쓰기
```

필요 시 다음 구조를 사용한다.

```text
VALID_FROM
VALID_TO
CURRENT_YN
```

또는 별도 History.

---

## RULE-062. Lifecycle 변경 영향도를 확인한다

Product Version의 Lifecycle 변경 시
해당 Version을 사용하는 Resource를 확인한다.

```text
Product Version
→ Resource 목록
→ 상태 재산정
→ Risk 영향
→ 조치/알림 영향
```

---

# 8. 화면 설계 규칙

## RULE-070. Excel Sheet를 화면과 1:1 변환하지 않는다

금지:

```text
00_Dashboard → 화면1
01_EOS관리대장 → 화면2
...
```

업무 흐름 중심으로 구성한다.

---

## RULE-071. 기본 화면 흐름

```text
Dashboard
→ 자원 통합조회
→ 자원 상세
→ Risk
→ Action
→ Exception
→ Verification
```

---

## RULE-072. 자원 목록에 모든 컬럼을 노출하지 않는다

Excel 원장 26개 항목을 Grid에 모두 표시하지 않는다.

Grid 핵심 후보:

```text
자원ID
시스템/서비스
자원구분
제품
현재Version
EOS Date
D-Day
현재상태
위험등급
조치상태
예외상태
담당조직
```

---

## RULE-073. 상세화면은 Tab 구조를 우선한다

```text
기본정보
Lifecycle
Risk
Action
Exception
Collection
History
```

---

## RULE-074. Dashboard는 판단을 지원해야 한다

최소 다음을 즉시 파악할 수 있어야 한다.

```text
전체자원
위험자원
Critical
High
예외필요
진행중
금월조치
우선조치 Top N
```

---

## RULE-075. KPI는 Drill-down 가능해야 한다

예:

```text
Critical 클릭
→ 자원조회
→ riskLevel=Critical
```

---

# 9. 화면 이벤트 규칙

## RULE-080. Event는 서비스와 추적 가능해야 한다

추적:

```text
Screen
→ Event
→ Service
→ Program
→ Table
→ Test
```

---

## RULE-081. 화면 권한과 서버 권한을 분리한다

화면:

```text
버튼 표시/비표시
활성/비활성
```

서버:

```text
최종 권한검증
```

---

# 10. 데이터베이스 설계 규칙

## RULE-090. 핵심 Entity 분리

기본 후보:

```text
PRODUCT
PRODUCT_VERSION
PRODUCT_LIFECYCLE
RESOURCE
RESOURCE_INSTALLATION
RISK_ASSESSMENT
RISK_SCORE
ACTION_PLAN
EXCEPTION_REQUEST
EXCEPTION_APPROVAL
MONTHLY_CHECK
MONTHLY_SNAPSHOT
COLLECTION_RESULT
DRIFT_RESULT
EVIDENCE
CHANGE_HISTORY
CODE
```

---

## RULE-091. 한 테이블에 모든 업무를 넣지 않는다

금지:

```text
EOS_RESOURCE
+ Risk
+ Action
+ Exception
+ Approval
+ Audit
```

를 한 테이블로 구성.

---

## RULE-092. 날짜는 Date/Datetime 타입

금지:

```text
VARCHAR2('2026-08-16')
```

날짜형 컬럼을 사용한다.

---

## RULE-093. Version은 문자열 기반을 고려한다

Version 예:

```text
8.5.x
2.4.x
17.0.10
RHEL 7.9
3.3.x
```

숫자형만으로 설계하지 않는다.

---

## RULE-094. 코드값 자유입력 금지

다음은 코드화 검토:

```text
자원구분
환경
센터
상태
위험등급
조치유형
승인상태
Drift 상태
```

---

## RULE-095. 사용중 기준정보 물리삭제 금지

우선:

```text
USE_YN
VALID_FROM
VALID_TO
```

등을 사용한다.

---

# 11. DB 무결성 규칙

## RULE-100. Key 정의 필수

모든 Entity:

```text
PK
Business Key
UK
FK
```

를 검토한다.

---

## RULE-101. Check Constraint 후보

```text
YN IN ('Y','N')
Score BETWEEN 1 AND 5
Exception End >= Start
```

복잡한 Workflow는 Application Rule로 관리한다.

---

## RULE-102. 낙관적 Lock을 검토한다

대상 후보:

```text
Resource
Risk
Action
Exception
Policy
```

기본 후보:

```text
VERSION_NO
```

---

# 12. 서비스 설계 규칙

## RULE-110. Screen Button과 Service를 1:1로 만들지 않는다

서비스는 다음 기준으로 식별한다.

```text
Use Case
Transaction
업무책임
권한
재사용성
```

---

## RULE-111. Query / Command를 구분한다

```text
QUERY
COMMAND
WORKFLOW
BATCH
INTEGRATION
ADMIN
```

---

## RULE-112. 상태 변경은 Command Service로 통제한다

금지:

```text
UI → updateStatus SQL
```

---

## RULE-113. 서비스는 Validation을 수행한다

구분:

```text
Format Validation
Reference Validation
Business Validation
State Validation
Permission Validation
```

---

# 13. NSIGHT/TCF 구현 규칙

## RULE-120. 실제 Repository 계층을 우선한다

기본 목표 책임:

```text
UI
→ Handler/Entry
→ Facade
→ Service
→ Rule
→ DAO
→ Mapper
→ DB
```

실제 프로젝트 구조가 다르면 현행소스를 분석 후 적용한다.

---

## RULE-121. Handler에 업무로직 금지

Handler:

```text
ServiceId Mapping
Request 전달
Facade 호출
Response 변환
```

---

## RULE-122. Facade는 Use Case 조합

여러 Service를 조합해야 하는 경우 사용한다.

단순 위임 Facade 남발 금지.

---

## RULE-123. Service는 업무 처리와 TX 책임

```text
Validation
Rule
DAO
상태변경
History
Audit
Event
```

---

## RULE-124. Rule은 판단 책임

금지:

```text
Rule → DB UPDATE
Rule → 외부 API
Rule → Notification
```

---

## RULE-125. DAO는 데이터 접근 책임

금지:

```text
DAO에서 Risk 등급 결정
DAO에서 권한 판단
DAO에서 Workflow 판단
```

---

## RULE-126. Mapper는 SQL 책임

금지:

```text
SELECT *
권한 Rule
업무 Rule
```

---

# 14. Transaction 규칙

## RULE-130. Command는 TX 경계를 정의한다

반드시 명시:

```text
Start
Read
Write
Commit
Rollback
Timeout
```

---

## RULE-131. TCF 최외곽 Transaction을 확인한다

TCF ON + Timeout 구조에서 Worker Thread의 `TransactionTemplate`이
최외곽 DB Transaction을 소유하는 경우
Service Transaction은 기존 TX 참여를 우선한다.

실제 Source 확인 없이 새로운 외곽 TX를 만들지 않는다.

---

## RULE-132. 외부 부가처리를 핵심 DB TX에 무조건 넣지 않는다

대상:

```text
Email
Notification
File
Agent
CMDB
Vendor API
SBOM/SCA
```

필요 시:

```text
DB Commit
→ Event
→ Async
```

---

# 15. Timeout 규칙

## RULE-140. 서비스 유형별 Timeout을 구분한다

```text
일반조회
Command
Dashboard
Export
Collection
Batch
```

---

## RULE-141. Timeout에서 부분 Commit 금지

```text
Timeout
→ Rollback
→ Context 정리
→ 오류로그
→ 표준 오류응답
```

---

# 16. 오류처리 규칙

## RULE-150. 업무오류와 시스템오류를 구분한다

업무오류 예:

```text
Resource 없음
중복 Resource
잘못된 Risk Score
잘못된 상태전이
예외기간 오류
승인권한 없음
동시성 충돌
```

시스템오류:

```text
DB 오류
Timeout
외부연계 장애
File 오류
```

---

## RULE-151. 사용자가 행동 가능한 메시지를 제공한다

나쁜 예:

```text
ERROR-500
```

좋은 예:

```text
예외 종료일은 시작일보다 빠를 수 없습니다.
종료일을 다시 입력해 주세요.
```

---

# 17. 보안 규칙

## RULE-160. 서버에서 최종 권한검증

화면 버튼 숨김만으로 권한을 구현하지 않는다.

---

## RULE-161. Client 전달 사용자 정보를 신뢰하지 않는다

다음은 서버 인증Context에서 취득한다.

```text
userId
orgId
role
approverId
auditUser
```

---

## RULE-162. 민감정보 로그 금지

금지:

```text
Password
Token
Private Key
Credential
민감 Raw Result
```

---

# 18. 감사 규칙

## RULE-170. 중요 변경은 감사 가능해야 한다

대상:

```text
Resource 생성/수정/폐기
Lifecycle 변경
Risk 완료
Action 상태
Action 완료
Exception
Approval
Extension
Policy
Collection 실행
Drift 해소
```

---

## RULE-171. Audit 기본정보

```text
Trace ID
User
Organization
Service
Entity
Entity ID
Action
Before
After
Result
Timestamp
```

---

# 19. 자동수집 규칙

## RULE-180. 원격 Shell 실행을 기본값으로 하지 않는다

대안 순서 검토:

```text
기존 관리도구/API
Agent
수동 Upload
원격 Shell
```

원격 Shell은 보안정책 승인 후 사용한다.

---

## RULE-181. 관리 Version과 발견 Version을 구분한다

```text
Managed Version
Discovered Version
```

비교 결과:

```text
MATCH
MISMATCH
COLLECT_FAILED
NOT_COLLECTED
REVIEW_REQUIRED
```

---

# 20. Dashboard/보고 규칙

## RULE-190. Dashboard는 현재상태

```text
Current / Near Real Time
```

---

## RULE-191. 월간보고는 Snapshot

```text
특정 시점 재현 가능
```

실시간 원장값만으로 과거 보고를 재생성하지 않는다.

---

## RULE-192. KPI 정의식을 관리한다

특히 다음은 명확한 산정식이 필요하다.

```text
위험상태
예외필요
진행중
금월목표조치
우선조치 Top N
```

---

# 21. Batch 규칙

## RULE-200. Batch 후보

```text
EOS 상태 재산정
EOS 임박
예외 만료
조치 초과
월간점검 미수행
Snapshot
Drift 미해소
```

---

## RULE-201. Batch 실행이력

최소:

```text
Job ID
시작
종료
처리건수
성공건수
실패건수
상태
재처리
```

---

# 22. Notification 규칙

## RULE-210. 업무 Commit 후 알림을 우선 검토한다

```text
Business TX Commit
→ Event
→ Notification
```

알림 실패로 핵심업무 Rollback을 유발하지 않는다.

---

# 23. MyBatis/SQL 규칙

## RULE-220. SELECT * 금지

명시적 Column만 조회한다.

---

## RULE-221. Server Side Paging

목록성 조회는 서버 Paging을 기본으로 한다.

---

## RULE-222. Sort White List

Client가 전달한 정렬 문자열을 SQL에 직접 연결하지 않는다.

---

## RULE-223. Update 영향건수 확인

Optimistic Lock 등에서:

```text
updatedRows == 0
```

이면 동시수정 또는 대상없음을 구분한다.

---

# 24. 테스트 규칙

## RULE-230. Rule Test 우선

최소:

```text
EosStatusRule
RiskLevelRule
ActionTransitionRule
ExceptionPeriodRule
CompletionRule
VersionDriftRule
```

---

## RULE-231. 경계값 Test

Risk 샘플 기준:

```text
19
20
25
26
31
32
```

EOS 상태도 경계일자를 테스트한다.

---

## RULE-232. Command Service Test

최소:

```text
정상
필수값 누락
참조값 없음
업무 Rule 위반
권한없음
중복
동시성충돌
DB 오류
```

---

## RULE-233. Integration Scenario

정상:

```text
Product
→ Version
→ Lifecycle
→ Resource
→ Risk
→ Action
→ Complete
```

예외:

```text
Risk
→ Exception Request
→ Approval
→ Monthly Check
→ Close/Extend
```

Drift:

```text
Managed Version
→ Collection
→ Mismatch
→ Resolution
```

---

# 25. Build/Run 규칙

## RULE-240. 실제 Build 실행 전 성공 보고 금지

예:

```bash
./gradlew clean test
./gradlew build
```

프로젝트 실제 명령을 사용한다.

---

## RULE-241. Runtime 검증

최소:

```text
Application Start
DB Connection
Mapper Load
Bean Load
ServiceId/API Registration
```

---

## RULE-242. Smoke Test

최소:

```text
Health
Dashboard
Resource List
Resource Detail
Risk
Action
Exception
```

---

# 26. Traceability 규칙

## RULE-250. 전 단계 추적성 필수

```text
Excel
→ Requirement
→ Screen/Event
→ Service
→ Program
→ DB
→ Test
```

표:

| Requirement | Screen/Event | Service | Program | Table | Test |
|---|---|---|---|---|---|

---

## RULE-251. P0 누락 금지

P0 요구사항은 반드시 다음 중 하나 상태를 가진다.

```text
IMPLEMENTED
PARTIAL
BLOCKED
NOT_STARTED
```

설명 없이 누락하지 않는다.

---

# 27. 문서 규칙

## RULE-260. 정식 설계문서 공통 목차

```text
1. 도입 전 안내말

2. 문서 개요
   - 목적
   - 적용범위
   - 대상 독자
   - 선행조건
   - 용어 정의

3. 본문
   - 문제 정의 및 설계 배경
   - 현행 구조와 문제점
   - 요구사항과 제약조건
   - 설계 원칙
   - 대안 비교 및 의사결정
   - 목표 아키텍처
   - 표준 형식
   - 구성요소 및 속성
   - 책임 경계와 RACI
   - 정상 처리 흐름
   - 오류·Timeout·장애 흐름
   - 정상 예시
   - 금지 예시
   - 연계 규칙
   - 데이터 및 상태관리
   - 성능·용량·확장성
   - 보안·개인정보·감사
   - 운영·모니터링·장애 대응
   - 자동검증 및 품질 Gate
   - 테스트 시나리오
   - 체크리스트
   - 변경·호환성·폐기 관리

4. 시사점
   - 핵심 아키텍처 판단
   - 주요 위험
   - 우선 보완 과제
   - 중장기 발전 방향

5. 마무리말
```

---

# 28. Naming 규칙

## RULE-270. 프로젝트 표준 우선

Naming Convention이 있으면 무조건 우선한다.

없을 경우에만 설계 후보를 사용한다.

---

## RULE-271. 임의 ServiceId 생성 금지

논리 서비스명:

```text
ResourceCreate
RiskEvaluate
ExceptionApprove
```

과 실제 물리 ServiceId를 구분한다.

---

# 29. Git 규칙

## RULE-280. 작업 전 상태 확인

```bash
git status
git branch
```

---

## RULE-281. 기존 변경 보호

사용자가 이미 수정한 파일을
정당한 이유 없이 덮어쓰거나 되돌리지 않는다.

---

## RULE-282. 관련 없는 변경 금지

```text
대량 Format
공통 Framework 전면수정
기존 업무 Source 수정
기존 Test 삭제
```

---

# 30. ADR 규칙

## RULE-290. 중요한 의사결정은 ADR로 기록한다

기본 후보:

```text
Product/Version/Resource 분리
Lifecycle History
Risk Detail 구조
EOS 상태 저장/계산
Dashboard 집계
Exception Workflow
Lifecycle 일괄 재산정
Collection 방식
Notification Event
Monthly Snapshot
```

---

# 31. 품질 Gate

## GATE-R — Requirements

```text
[ ] 원본추적
[ ] Acceptance Criteria
[ ] Risk
[ ] Action
[ ] Exception
[ ] Audit
```

## GATE-U — UI

```text
[ ] Navigation
[ ] Drill-down
[ ] Validation
[ ] Permission
[ ] Event-Service
```

## GATE-D — Data

```text
[ ] PK/FK/UK
[ ] History
[ ] Code
[ ] Index
[ ] Quality Rule
```

## GATE-S — Service

```text
[ ] Request/Response
[ ] Rule
[ ] State
[ ] TX
[ ] Timeout
[ ] Audit
```

## GATE-I — Implementation

```text
[ ] DB
[ ] DTO
[ ] DAO/Mapper
[ ] Rule
[ ] Service
[ ] Facade
[ ] Handler/API
[ ] UI
```

## GATE-T — Test

```text
[ ] Unit
[ ] Service
[ ] DAO
[ ] Integration
```

## GATE-B — Build

```text
[ ] Test Success
[ ] Build Success
```

## GATE-RUN — Runtime

```text
[ ] Start
[ ] DB
[ ] Endpoint
[ ] Smoke
```

## GATE-TR — Traceability

```text
[ ] Requirement
[ ] Screen
[ ] Service
[ ] Program
[ ] DB
[ ] Test
```

---

# 32. 완료 상태 규칙

## RULE-300. 완료 선언 기준

다음이 확인되어야 한다.

```text
Requirements
Screen
DB
Service
Implementation
Test
Build
Run
Smoke
Validation
Traceability
```

---

## RULE-301. 검증되지 않은 경우 DONE 금지

사용 가능한 상태:

```text
DONE
PARTIAL
UNVERIFIED
BLOCKED
```

`DONE`은 실제 검증 완료 시만 사용한다.

---

# 33. 금지사항 종합

절대 하지 않는다.

```text
Excel 시트를 그대로 화면화

Excel 한 행을 단일 DB Table로 단순 변환

Product/Version/Resource 혼합

EOS/EOL/계약종료 통합

모든 상태를 STATUS 하나로 관리

날짜 VARCHAR 저장

코드 자유입력

화면에서 Risk 최종판정

Client 승인자 신뢰

Handler/Controller 업무로직

DAO 업무 Rule

Mapper 권한/Workflow

상태 직접 UPDATE

예외 만료일 없는 승인

신청자=승인자 허용

Lifecycle 이력 없이 덮어쓰기

Audit 없는 중요변경

동시성 없는 승인

외부 Notification 실패로 업무 Rollback

SELECT *

무제한 조회

Credential 로그

Test 없이 완료

Build 없이 성공 보고

실행 없이 Smoke 성공 보고

확인하지 않은 내용을 사실로 문서화
```

---

# 34. EOS 핵심 추적구조

최종적으로 항상 다음 관계를 유지한다.

```text
원본 Excel
   ↓
Requirement ID
   ↓
Screen ID / Event ID
   ↓
ServiceId / API
   ↓
Handler
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
Table / Column
   ↓
Test
```

---

# 35. 최종 원칙

EOS 시스템의 성공 기준은 단순한 CRUD 완성이 아니다.

반드시 다음 Governance Loop가 동작해야 한다.

```text
발견
→ 평가
→ 계획
→ 조치
→ 예외
→ 점검
→ 검증
→ 감사
```

그리고 모든 단계는 다음 세 가지를 만족해야 한다.

```text
추적 가능
검증 가능
운영 가능
```

이 세 가지 중 하나라도 만족하지 못하면
EOS 구현은 완료된 것으로 판단하지 않는다.
