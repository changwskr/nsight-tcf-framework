# 농협상호금융 정보계 EOS 자원관리 시스템
# 데이터베이스 설계서 작성 마스터 프롬프트

## 0. 프롬프트 사용 목적

이 프롬프트는 농협상호금융 정보계 **EOS 자원관리 시스템**의 요구사항 정의서와 화면설계서를 입력으로 받아,
실제 개발·검토·DDL 구현에 사용할 수 있는 **데이터베이스 설계서**를 작성하기 위한 실행 프롬프트다.

기본 입력자료는 다음을 우선 사용한다.

```text
1. NH_상호금융정보계_EOS_자원관리_샘플양식.xlsx
2. EOS_자원관리_분석_요구사항_도출_마스터프롬프트.md
3. EOS 요구사항 정의서
4. EOS_자원관리_화면설계서_작성_마스터프롬프트.md
5. EOS 화면설계서
6. 추가 제공되는 코드표, 데이터 표준, DB 표준, 보안정책, 인터페이스 정의
```

이 작업의 목적은 Excel의 한 행을 그대로 하나의 테이블로 옮기는 것이 아니다.

EOS 자원관리 업무를 다음 데이터 생명주기로 재구성해야 한다.

```text
Product
  ↓
Product Version
  ↓
Product Lifecycle
  ↓
Resource / Installation
  ↓
Risk Assessment
  ↓
Action Plan
  ↓
Exception / Approval
  ↓
Monthly Check
  ↓
Collection / Drift
  ↓
Evidence / History / Audit
```

최종 산출물은 단순 테이블 목록이 아니라 다음을 모두 포함하는
**실제 구현 가능한 데이터베이스 설계서**여야 한다.

```text
논리 데이터 모델
+ 물리 데이터 모델
+ Entity 정의
+ Table 정의
+ Column 정의
+ PK / FK / UK
+ 코드·기준정보
+ 상태·Workflow
+ 이력·감사
+ Snapshot
+ 첨부/증빙
+ 인덱스
+ 데이터 정합성 Rule
+ 보안·개인정보
+ 보관·폐기
+ 초기 데이터
+ DDL
+ 화면·요구사항·API 추적성
+ 테스트 데이터/검증 SQL
```

---

# 1. 역할

너는 다음 역할을 동시에 수행한다.

- 농협상호금융 정보계 수석 데이터 아키텍트
- EOS/EOL Lifecycle 관리 전문가
- CMDB/IT 자원관리 데이터 모델러
- Oracle 데이터베이스 설계 전문가
- 요구사항 분석가
- 애플리케이션 아키텍트
- Java/Spring/MyBatis 연계 설계자
- 보안·개인정보·감사 데이터 설계자
- 운영·PMO·Architecture Gate 검토자
- 데이터 품질 및 변경관리 전문가

너의 임무는 입력자료에서 확인된 업무 개념과 관계를 먼저 정확히 분석하고,
이를 정규화된 데이터모델로 구조화한 뒤,
원본에 없는 추가 설계는 반드시 **추가제안**으로 구분하는 것이다.

---

# 2. 절대 원칙

## 2.1 Source First

반드시 제공된 Excel, 요구사항 정의서, 화면설계서를 먼저 분석한다.

확인되지 않은 Table, Column, 코드값, Cardinality, 상태값을
실제 확정사항처럼 임의 생성하지 않는다.

모든 판단은 다음 네 가지로 구분한다.

```text
[원본확인]
Excel·요구사항·화면설계에서 직접 확인된 내용

[설계반영]
확정된 업무 요구사항을 DB 모델로 구현한 내용

[추가제안]
정규화·성능·감사·운영성 관점에서 추가하는 설계

[확인필요]
업무정책 또는 아키텍처 의사결정이 필요한 내용
```

---

## 2.2 Excel 1행 = DB 1행으로 단순 변환 금지

다음 방식은 금지한다.

```text
01_EOS관리대장 26개 컬럼
→ EOS_RESOURCE 하나의 테이블에 26개 컬럼 그대로 생성
```

반드시 다음 개념 분리를 먼저 검토한다.

```text
제품
제품 버전
제품 Lifecycle
실제 설치자원
담당조직
위험평가
조치계획
예외신청
예외승인
월간점검
자동수집결과
증빙
변경이력
코드
```

---

## 2.3 Product / Version / Resource를 혼합하지 않는다

EOS 시스템에서 가장 중요한 데이터모델 판단은 다음 세 개를 구분하는 것이다.

```text
PRODUCT
예: Apache Tomcat

PRODUCT_VERSION
예: Tomcat 8.5.x

RESOURCE / RESOURCE_INSTALLATION
예: sv-ap-prd-01에 설치된 Tomcat 8.5.x
```

다음 속성은 어느 Entity의 책임인지 명확히 분석한다.

| 속성 | 책임 후보 |
|---|---|
| 제품명 | Product |
| 벤더 | Product |
| Version | Product Version |
| EOS/EOL | Product Version 또는 Lifecycle |
| 공식근거 | Product Lifecycle |
| 설치위치 | Resource |
| 환경 | Resource |
| 운영센터 | Resource |
| 담당조직 | Resource |
| 현재버전 | Resource → Product Version FK |
| 목표표준버전 | 정책 또는 Action Plan |
| 위험평가 | Resource |
| 조치계획 | Resource |
| 예외 | Resource/Action |

업무정책에 따라 달라질 수 있는 항목은 `[확인필요]`로 표시한다.

---

## 2.4 현재값과 이력을 분리한다

다음 값은 현재 상태만 덮어쓰면 안 되는지 반드시 검토한다.

```text
현재버전
목표표준버전
EOS/EOL
계약종료일
위험점수
위험등급
조치상태
예외상태
담당조직
점검결과
```

필요한 경우 다음 패턴을 사용한다.

```text
MASTER
  +
HISTORY
```

예:

```text
EOS_RESOURCE
EOS_RESOURCE_HIST

EOS_RISK_ASSESSMENT
EOS_RISK_ASSESSMENT_HIST
```

단, 무조건 History 테이블을 남발하지 말고
업무 추적성·감사·재현 요구에 따라 설계한다.

---

## 2.5 상태와 이벤트를 혼합하지 않는다

예:

```text
현재상태 = 위험
위험등급 = Critical
조치상태 = 진행중
예외상태 = 승인
```

위 네 값은 서로 다른 상태축이다.

하나의 STATUS 컬럼으로 합치지 않는다.

---

## 2.6 코드값을 자유문자열로 만들지 않는다

다음 항목은 코드화 가능성을 검토한다.

```text
자원구분
NSIGHT영역
환경
운영센터
현재상태
위험등급
조치유형
조치상태
승인상태
수집상태
Drift상태
증빙유형
알림유형
```

공통코드인지 독립 기준테이블인지 업무 중요도에 따라 판단한다.

---

# 3. 데이터베이스 설계 입력자료 분석

먼저 입력자료별 역할을 다음과 같이 정리한다.

| 입력자료 | DB 설계에서 확인할 내용 |
|---|---|
| EOS Excel | 실제 관리항목, 코드, 데이터 예시, 관계 Key |
| 요구사항 정의서 | 기능, Workflow, Validation, 이력, 보안 |
| 화면설계서 | 입력/조회 항목, Grid, 상태전이, Event |
| 코드기준표 | 코드 도메인 |
| 점검명령어 | 수집결과 모델 |
| 조직/사용자 기준 | 담당자·승인자 FK 여부 |
| 외부 CMDB/API | 외부 식별자와 동기화 Key |

입력자료가 없으면 임의로 확정하지 않는다.

---

# 4. 1단계 — 데이터 요구사항 Inventory

먼저 데이터 요구사항을 전체 목록화한다.

다음 형식을 사용한다.

| Data ID | 업무영역 | 데이터 개념 | 출처 | 생성주체 | 조회주체 | 변경주체 | 이력필요 | 보관기간 | 비고 |
|---|---|---|---|---|---|---|---|---|---|

최소 다음 업무영역을 분석한다.

```text
자원
Product
Product Version
Lifecycle
위험
조치
예외
승인
월간점검
월간 Snapshot
점검/수집
Drift
코드
첨부/증빙
알림
변경이력
감사
```

---

# 5. 2단계 — 핵심 Business Key 분석

각 Entity의 Business Key를 정의한다.

예:

```text
Resource ID
Product Code
Product Version ID
Lifecycle ID
Risk Assessment ID
Action Plan ID
Exception Request No
Approval ID
Monthly Snapshot YYYYMM
Collection Run ID
```

다음 질문을 반드시 검토한다.

1. `자원ID`는 전사 유일한가?
2. 자원ID는 의미코드인가 Surrogate Key와 별도로 필요한가?
3. Product + Version 조합은 Unique한가?
4. 동일 Product Version에 Lifecycle이 여러 건 존재할 수 있는가?
5. 동일 Resource에 여러 Risk Assessment가 존재할 수 있는가?
6. 동일 Resource에 여러 Action Plan이 존재할 수 있는가?
7. 예외승인은 Action Plan과 연결되는가 Resource와 직접 연결되는가?
8. 월간보고 Snapshot Key는 무엇인가?
9. 자동수집 한 번의 실행단위는 무엇인가?

확정되지 않으면 `[확인필요]`로 표시한다.

---

# 6. 3단계 — 논리 Entity 후보

다음 Entity를 기본 후보로 검토한다.

```text
EOS_RESOURCE
EOS_PRODUCT
EOS_PRODUCT_VERSION
EOS_PRODUCT_LIFECYCLE
EOS_RESOURCE_INSTALLATION
EOS_RISK_ASSESSMENT
EOS_RISK_SCORE
EOS_ACTION_PLAN
EOS_ACTION_HISTORY
EOS_EXCEPTION_REQUEST
EOS_EXCEPTION_APPROVAL
EOS_MONTHLY_CHECK
EOS_MONTHLY_SNAPSHOT
EOS_EVIDENCE
EOS_COLLECTION_RULE
EOS_COLLECTION_RUN
EOS_COLLECTION_RESULT
EOS_DRIFT_RESULT
EOS_CODE_GROUP
EOS_CODE
EOS_NOTIFICATION
EOS_CHANGE_HISTORY
```

모든 Entity를 반드시 생성하라는 뜻이 아니다.

각 Entity마다 다음을 판정한다.

```text
필수
통합가능
분리권장
2차확장
불필요
확인필요
```

---

# 7. 4단계 — 논리 데이터 모델

최소 다음 관계를 분석한다.

```text
PRODUCT
   1
   │
   N
PRODUCT_VERSION
   1
   │
   N
PRODUCT_LIFECYCLE

PRODUCT_VERSION
   1
   │
   N
RESOURCE_INSTALLATION
   N
   │
   1
RESOURCE

RESOURCE
   ├─ N RISK_ASSESSMENT
   ├─ N ACTION_PLAN
   ├─ N EXCEPTION_REQUEST
   ├─ N MONTHLY_CHECK
   ├─ N COLLECTION_RESULT
   ├─ N EVIDENCE
   └─ N CHANGE_HISTORY
```

실제 요구사항에 따라 Cardinality를 확정한다.

논리 ERD를 Mermaid 또는 ASCII로 작성한다.

예:

```text
EOS_PRODUCT
    │ 1
    │
    │ N
EOS_PRODUCT_VERSION
    │ 1
    │
    ├──────────── N EOS_PRODUCT_LIFECYCLE
    │
    └──────────── N EOS_RESOURCE_INSTALLATION
                         │ N
                         │
                         │ 1
                    EOS_RESOURCE
                         │
              ┌──────────┼───────────┐
              │          │           │
              N          N           N
             Risk      Action     Exception
```

---

# 8. 5단계 — EOS_RESOURCE 설계

EOS Resource는 실제 관리대상 자원을 식별하는 핵심 Entity다.

다음 속성을 검토한다.

```text
RESOURCE_ID
RESOURCE_CODE
NSIGHT_AREA_CODE
SYSTEM_SERVICE_NAME
BUSINESS_RUNTIME_NAME
RESOURCE_TYPE_CODE
INSTALL_LOCATION
ENV_CODE
CENTER_CODE
OWNER_ORG_ID
OWNER_USER_ID
VENDOR_ID 또는 Product 경유
RESOURCE_STATUS
USE_YN
REMARK
CREATED_AT
CREATED_BY
UPDATED_AT
UPDATED_BY
VERSION_NO
```

원본 Excel의 `제품명`, `현재버전`, `EOS일자` 등을
Resource에 직접 저장할지 FK로 분리할지 반드시 분석한다.

---

# 9. 6단계 — Product / Product Version 설계

## EOS_PRODUCT

검토 속성:

```text
PRODUCT_ID
PRODUCT_CODE
PRODUCT_NAME
VENDOR_ID
RESOURCE_TYPE_CODE
DESCRIPTION
USE_YN
```

## EOS_PRODUCT_VERSION

검토 속성:

```text
PRODUCT_VERSION_ID
PRODUCT_ID
VERSION_NAME
VERSION_SORT_KEY
RELEASE_DATE
STANDARD_YN
SUPPORT_STATUS
USE_YN
```

다음 이슈를 분석한다.

```text
8.5.x처럼 범위 Version을 저장할 것인가?
8.5.99처럼 실제 설치 Version과 제품 Lifecycle Version 범위는 어떻게 매핑하는가?
```

이 문제가 존재한다면 Version Match Rule 또는 Pattern 개념을 `[추가제안]`으로 검토한다.

---

# 10. 7단계 — Product Lifecycle 설계

Lifecycle은 다음을 분리 관리하는지 검토한다.

```text
EOS_DATE
EOL_DATE
CONTRACT_END_DATE
```

추가 후보:

```text
LIFECYCLE_SOURCE_TYPE
SOURCE_URL
SOURCE_DOCUMENT_ID
CONFIRMED_DATE
CONFIRMED_BY
VALID_FROM
VALID_TO
CURRENT_YN
```

한 Product Version의 Lifecycle 변경이 발생할 때
기존 값을 덮어쓰는지 이력형으로 관리하는지 설계한다.

예:

```text
EOS_PRODUCT_LIFECYCLE
--------------------------------
LIFECYCLE_ID
PRODUCT_VERSION_ID
EOS_DATE
EOL_DATE
SOURCE_TYPE
SOURCE_REFERENCE
CONFIRMED_AT
CONFIRMED_BY
VALID_FROM
VALID_TO
CURRENT_YN
```

계약종료일이 Resource별 계약인지 Product 공통인지 반드시 확인한다.

---

# 11. 8단계 — Resource Installation / Version 배치관계 설계

Resource와 Product Version의 관계를 별도 Entity로 둘 필요를 검토한다.

예:

```text
EOS_RESOURCE_INSTALLATION
--------------------------------
INSTALLATION_ID
RESOURCE_ID
PRODUCT_VERSION_ID
INSTALLED_VERSION_TEXT
INSTALL_PATH
INSTANCE_NAME
DISCOVERED_VERSION
DISCOVERED_AT
SOURCE_TYPE
CURRENT_YN
```

다음 상황을 처리할 수 있어야 한다.

```text
하나의 서버에 여러 Product 설치
하나의 Product가 여러 서버에 설치
실제 발견 Version과 관리 Version 불일치
Version 변경이력
```

단순 시스템 범위에서는 Resource 자체가 하나의 Product 설치단위라면
통합 가능성을 비교한다.

---

# 12. 9단계 — 상태 자동판정 데이터 설계

Excel의 상태 산정기준을 데이터로 관리할 수 있는지 검토한다.

예:

```text
정상 : 12개월 이상
주의 : 6~12개월
경고 : 3~6개월
위험 : 3개월 미만 또는 경과
예외 : 승인 유효
```

다음 두 방식을 비교한다.

```text
A안. 프로그램 Configuration
B안. EOS_STATUS_POLICY 테이블
```

변경 가능성이 높은 업무정책이면 테이블 관리가 유리할 수 있다.

정책 테이블 후보:

```text
EOS_STATUS_POLICY
--------------------------------
POLICY_ID
STATUS_CODE
MIN_DAYS
MAX_DAYS
PRIORITY
USE_YN
VALID_FROM
VALID_TO
```

현재상태를 DB에 물리 저장할지
조회 시 계산할지도 비교한다.

---

# 13. 10단계 — 위험평가 모델 설계

샘플의 위험평가 요소는 다음 7개다.

```text
업무중요도
운영환경
외부노출
보안취약점
장애영향도
대체난이도
EOS상태
```

다음 두 모델을 비교한다.

## A안 — Fixed Column

```text
BUSINESS_CRITICALITY_SCORE
PRODUCTION_SCORE
EXPOSURE_SCORE
VULNERABILITY_SCORE
IMPACT_SCORE
REPLACEMENT_SCORE
EOS_STATUS_SCORE
```

## B안 — Assessment + Score Detail

```text
EOS_RISK_ASSESSMENT
EOS_RISK_SCORE
EOS_RISK_FACTOR
```

평가항목이 향후 추가·변경될 가능성이 높으면 B안을 우선 검토한다.

예:

```text
EOS_RISK_ASSESSMENT
--------------------------------
ASSESSMENT_ID
RESOURCE_ID
ASSESSMENT_DATE
TOTAL_SCORE
RISK_LEVEL_CODE
ASSESSMENT_COMMENT
ASSESSOR_ID
STATUS
```

```text
EOS_RISK_SCORE
--------------------------------
ASSESSMENT_ID
FACTOR_CODE
SCORE
COMMENT
```

점수와 등급은 가능하면 시스템 산정값으로 관리하고
사용자 임의수정 여부를 제한한다.

---

# 14. 11단계 — 위험등급 정책 설계

샘플 기준:

```text
Critical : 32 이상
High     : 26~31
Medium   : 20~25
Low      : 19 이하
```

이를 프로그램 하드코딩할지 정책테이블로 관리할지 판단한다.

후보:

```text
EOS_RISK_LEVEL_POLICY
--------------------------------
RISK_LEVEL_CODE
MIN_SCORE
MAX_SCORE
PRIORITY
ACTION_REQUIRED_YN
USE_YN
VALID_FROM
VALID_TO
```

평가기준 변경 시 과거 평가결과를 재산정할 것인지 반드시 확인한다.

---

# 15. 12단계 — 조치계획 모델 설계

`03_조치계획`을 분석하여 최소 다음 속성을 검토한다.

```text
ACTION_PLAN_ID
RESOURCE_ID
CURRENT_PRODUCT_VERSION_ID
TARGET_PRODUCT_VERSION_ID
ACTION_TYPE_CODE
ACTION_DESCRIPTION
IMPACT_SCOPE
PRE_TASK
TEST_PLAN
TRANSITION_TYPE_CODE
ROLLBACK_PLAN
OWNER_ORG_ID
OWNER_USER_ID
PLANNED_START_DATE
TARGET_COMPLETE_DATE
ACTUAL_COMPLETE_DATE
ACTION_STATUS_CODE
ISSUE_DESCRIPTION
COMPLETION_COMMENT
```

다음 관계를 분석한다.

```text
한 Resource : 여러 Action Plan 가능?
동시에 Active Action Plan 여러 개 가능?
하나의 Action Plan : 여러 Resource 가능?
```

여러 자원을 묶은 공통 Upgrade Project가 필요하면
`ACTION_PLAN`과 `ACTION_PLAN_RESOURCE` 분리를 추가제안으로 검토한다.

---

# 16. 13단계 — 조치상태 이력 설계

상태전이 예:

```text
미착수
→ 계획수립
→ 진행중
→ 테스트중
→ 완료

보류
예외관리
```

단순히 `ACTION_STATUS_CODE`를 덮어쓰는 것 외에
이력 테이블 필요성을 검토한다.

후보:

```text
EOS_ACTION_STATUS_HIST
--------------------------------
ACTION_STATUS_HIST_ID
ACTION_PLAN_ID
FROM_STATUS
TO_STATUS
CHANGED_AT
CHANGED_BY
CHANGE_REASON
```

---

# 17. 14단계 — 전환 및 Rollback 데이터 설계

다음 값을 자유 텍스트로 둘지 코드화할지 분석한다.

```text
Rolling
Blue-Green
DR 사전검증
서비스중단
Rollback
```

후보:

```text
TRANSITION_TYPE_CODE
DOWNTIME_YN
DR_PRECHECK_YN
ROLLBACK_REQUIRED_YN
ROLLBACK_PLAN
ROLLBACK_RESULT
```

완료검증 Entity를 별도로 둘 필요도 검토한다.

---

# 18. 15단계 — 예외신청 모델 설계

`04_예외승인`의 핵심 속성을 모두 반영한다.

```text
EXCEPTION_ID
REQUEST_NO
RESOURCE_ID
REQUEST_DATE
REQUEST_ORG_ID
REQUESTER_ID
RISK_LEVEL_CODE
EXCEPTION_START_DATE
EXCEPTION_END_DATE
REQUEST_REASON
IMMEDIATE_ACTION_IMPOSSIBLE_REASON
COMPENSATING_CONTROL
FINAL_TRANSITION_PLAN
FINAL_TRANSITION_TARGET_DATE
MONTHLY_CHECK_REQUIRED_YN
TERMINATION_CRITERIA
EXCEPTION_STATUS_CODE
```

다음 Validation을 DB/Application 양쪽에서 어떻게 보장할지 정의한다.

```text
EXCEPTION_END_DATE >= EXCEPTION_START_DATE
COMPENSATING_CONTROL NOT NULL
FINAL_TRANSITION_PLAN NOT NULL
FINAL_TRANSITION_TARGET_DATE NOT NULL
```

---

# 19. 16단계 — 예외승인 모델 설계

신청정보와 승인정보를 분리한다.

후보:

```text
EOS_EXCEPTION_APPROVAL
--------------------------------
APPROVAL_ID
EXCEPTION_ID
APPROVAL_STEP
APPROVER_ID
APPROVAL_STATUS_CODE
APPROVAL_CONDITION
APPROVAL_COMMENT
APPROVED_AT
```

Workflow가 다단계 승인인지 단일 승인인지 입력자료에서 확인한다.

확인되지 않으면 임의로 다단계 Approval을 확정하지 않는다.

신청자와 승인자 SoD 검증방법을 정의한다.

---

# 20. 17단계 — 예외 월간점검 모델 설계

승인된 예외의 월간점검을 별도 Entity로 관리할지 분석한다.

후보:

```text
EOS_MONTHLY_CHECK
--------------------------------
CHECK_ID
EXCEPTION_ID
CHECK_YEARMONTH
CHECK_DATE
SECURITY_STATUS
INCIDENT_YN
COMPENSATING_CONTROL_OK_YN
TRANSITION_PROGRESS_RATE
NEW_RISK_DESCRIPTION
CHECK_COMMENT
CHECKER_ID
EVIDENCE_ID
```

Unique 후보:

```text
EXCEPTION_ID + CHECK_YEARMONTH
```

---

# 21. 18단계 — 월간 Snapshot 모델 설계

실시간 Dashboard와 월간보고를 분리한다.

```text
실시간
= 현재 원장 집계

월간보고
= 확정 월말 Snapshot
```

다음 두 방식을 비교한다.

```text
A. 월간보고 시점에 SQL로 다시 계산
B. 월별 Snapshot 저장
```

재현성 및 과거 보고 보존이 중요하면 B안을 우선 검토한다.

후보:

```text
EOS_MONTHLY_SNAPSHOT
--------------------------------
SNAPSHOT_ID
BASE_YEARMONTH
BASE_DATE
TOTAL_COUNT
RISK_COUNT
CRITICAL_COUNT
HIGH_COUNT
MEDIUM_COUNT
LOW_COUNT
EXCEPTION_REQUIRED_COUNT
ACTION_IN_PROGRESS_COUNT
CREATED_AT
CONFIRMED_AT
CONFIRMED_BY
```

세부 행 Snapshot이 필요한지도 검토한다.

---

# 22. 19단계 — Dashboard 집계 데이터 설계

Dashboard KPI가 원장에서 실시간 계산 가능한지,
Materialized View/집계테이블이 필요한지 비교한다.

초기 36건만 기준으로 성능설계를 확정하지 않는다.

예상 자원 규모와 조회빈도를 기반으로 판단한다.

각 KPI에 대해 다음을 작성한다.

| KPI | Source Entity | 조건 | 산정 SQL 개념 | Snapshot 여부 |
|---|---|---|---|---|

특히 `위험 상태`와 `Critical + High`의 업무정의가 다를 수 있으므로
KPI 정의를 명확히 확인한다.

---

# 23. 20단계 — 점검/자동수집 모델 설계

`07_점검명령어`의 구조를 시스템화한다.

## 수집규칙

후보:

```text
EOS_COLLECTION_RULE
--------------------------------
RULE_ID
RESOURCE_TYPE_CODE
PRODUCT_ID
CHECK_METHOD_TYPE
COMMAND_TEMPLATE
COLLECT_FIELD
CAUTION_TEXT
OUTPUT_TYPE
AUTO_YN
USE_YN
```

운영서버 명령을 DB에 평문으로 저장하는 것이 적절한지
보안 관점에서 검토한다.

## 수집실행

```text
EOS_COLLECTION_RUN
--------------------------------
RUN_ID
STARTED_AT
ENDED_AT
RUN_STATUS
TRIGGER_TYPE
EXECUTOR
```

## 수집결과

```text
EOS_COLLECTION_RESULT
--------------------------------
RESULT_ID
RUN_ID
RESOURCE_ID
RULE_ID
DISCOVERED_PRODUCT
DISCOVERED_VERSION
RAW_RESULT_REFERENCE
COLLECTED_AT
RESULT_STATUS
ERROR_MESSAGE
```

---

# 24. 21단계 — Drift 모델 설계

관리 원장 Version과 실제 수집 Version을 비교한다.

상태 후보:

```text
MATCH
MISMATCH
COLLECT_FAILED
NOT_COLLECTED
REVIEW_REQUIRED
```

후보:

```text
EOS_DRIFT_RESULT
--------------------------------
DRIFT_ID
RESOURCE_ID
MANAGED_VERSION
DISCOVERED_VERSION
DRIFT_STATUS_CODE
DETECTED_AT
RESOLVED_AT
RESOLUTION_TYPE
RESOLVED_BY
```

Drift를 별도 테이블로 둘지 Collection Result에서 계산할지 비교한다.

---

# 25. 22단계 — 증빙/첨부 모델 설계

다음 업무에서 증빙이 발생한다.

```text
Lifecycle 공식근거
유지보수 계약
위험평가 근거
조치 테스트
전환 결과
Rollback 결과
예외승인
월간점검
자동수집
완료검증
```

DB에 Binary를 직접 저장할지
파일 저장소의 Metadata만 관리할지 비교한다.

권장 후보:

```text
EOS_EVIDENCE
--------------------------------
EVIDENCE_ID
OWNER_TYPE
OWNER_ID
DOCUMENT_TYPE_CODE
FILE_NAME
STORAGE_REFERENCE
FILE_SIZE
MIME_TYPE
SECURITY_LEVEL
DESCRIPTION
UPLOADED_AT
UPLOADED_BY
```

`OWNER_TYPE + OWNER_ID` 다형 관계가 DB FK 정합성을 약화시킬 수 있으므로
대안도 비교한다.

---

# 26. 23단계 — 조직/담당자 모델

담당조직과 담당자가 기존 사내 조직/사용자 시스템에서 제공되는지 확인한다.

다음 대안을 비교한다.

```text
A. EOS 내부 조직/사용자 테이블
B. 외부 조직/사용자 기준정보 FK/Code Reference
C. Cache/동기화 테이블
```

사용자 개인정보를 불필요하게 복제하지 않는다.

---

# 27. 24단계 — 코드/정책 테이블 설계

다음 공통코드 구조를 검토한다.

```text
EOS_CODE_GROUP
EOS_CODE
```

후보:

```text
EOS_CODE_GROUP
--------------------------------
CODE_GROUP
CODE_GROUP_NAME
DESCRIPTION
USE_YN
```

```text
EOS_CODE
--------------------------------
CODE_GROUP
CODE
CODE_NAME
DESCRIPTION
SORT_ORDER
USE_YN
VALID_FROM
VALID_TO
```

하지만 위험평가요소, 위험등급정책처럼
속성이 많은 업무기준은 별도 Table이 더 적합할 수 있다.

무조건 모든 기준정보를 `EOS_CODE` 하나에 넣지 않는다.

---

# 28. 25단계 — 알림 모델 설계

알림 요구가 있는 경우 다음 구조를 검토한다.

```text
EOS_NOTIFICATION_RULE
EOS_NOTIFICATION
```

Rule 후보:

```text
EVENT_TYPE
BEFORE_DAYS
REPEAT_CYCLE
ESCALATION_LEVEL
CHANNEL
USE_YN
```

실제 발송 이력:

```text
NOTIFICATION_ID
EVENT_TYPE
RESOURCE_ID
RECIPIENT_ID
CHANNEL
MESSAGE
SEND_STATUS
SENT_AT
ERROR_MESSAGE
```

---

# 29. 26단계 — 변경이력 및 감사 모델 설계

업무 이력과 보안 감사로그를 구분한다.

## 업무이력

사용자가 화면에서 보고 업무적으로 추적해야 하는 변경.

예:

```text
버전 변경
EOS/EOL 변경
담당조직 변경
목표일 변경
위험등급 변경
```

## 감사로그

누가 언제 어떤 데이터를 변경했는지 보안/감사 목적.

후보:

```text
EOS_CHANGE_HISTORY
--------------------------------
HISTORY_ID
ENTITY_TYPE
ENTITY_ID
FIELD_NAME
BEFORE_VALUE
AFTER_VALUE
CHANGE_REASON
CHANGED_AT
CHANGED_BY
```

대량/민감 데이터 저장 시 별도 Audit 방식도 비교한다.

---

# 30. 27단계 — 물리명명 규칙

프로젝트 DB 명명표준이 제공되면 해당 기준을 최우선으로 사용한다.

별도 표준이 없을 경우 다음을 **설계 후보**로 사용하고
`[추가제안]`으로 표시한다.

```text
대문자
단어구분 _
영역 Prefix EOS_
PK 컬럼 *_ID
코드 컬럼 *_CODE
여부 컬럼 *_YN
날짜 *_DATE
일시 *_AT
설명 *_DESC 또는 *_DESCRIPTION
순번 *_SEQ
```

예:

```text
EOS_RESOURCE
EOS_PRODUCT
EOS_PRODUCT_VERSION
EOS_ACTION_PLAN
```

Index:

```text
IDX_{TABLE}_{순번}
```

Unique:

```text
UK_{TABLE}_{순번}
```

PK/FK Constraint:

```text
PK_{TABLE}
FK_{TABLE}_{순번}
```

실제 조직 DB 표준이 있으면 이름을 변경한다.

---

# 31. 28단계 — 데이터타입 설계

Oracle 기준 후보를 사용하되,
프로젝트 표준을 우선한다.

예:

```text
ID             NUMBER 또는 VARCHAR2
CODE           VARCHAR2
NAME           VARCHAR2
DATE           DATE
TIMESTAMP       TIMESTAMP
YN             CHAR(1)
SCORE           NUMBER
DESCRIPTION     VARCHAR2 또는 CLOB
```

다음 항목에 주의한다.

```text
날짜를 VARCHAR2로 저장하지 않는다.
YN을 Y/N 외 자유문자열로 만들지 않는다.
점수를 문자열로 저장하지 않는다.
파일 Binary를 무조건 DB에 넣지 않는다.
Version 문자열은 숫자로만 가정하지 않는다.
```

Version은 다음과 같은 값이 가능하다.

```text
8.5.x
2.4.x
17.0.10
RHEL 7.9
Spring Boot 3.3.x
```

따라서 문자열 기반 Version 필드를 고려한다.

---

# 32. 29단계 — PK / FK / UK 설계

모든 테이블마다 다음을 작성한다.

```text
Primary Key
Business Key
Unique Key
Foreign Key
Delete Rule
Update Rule
```

삭제정책은 특히 중요하다.

다음 Entity는 물리삭제 제한을 검토한다.

```text
Resource
Lifecycle
Risk Assessment
Action Plan
Exception
Approval
Monthly Snapshot
Audit
```

Code가 사용중인 경우 물리삭제 대신 비활성화를 우선한다.

---

# 33. 30단계 — 참조 무결성 설계

다음 관계의 FK 또는 논리 참조를 정의한다.

```text
Product Version → Product
Lifecycle → Product Version
Installation → Resource
Installation → Product Version
Risk → Resource
Action → Resource
Exception → Resource
Approval → Exception
Monthly Check → Exception
Evidence → 업무 Entity
Collection Result → Resource
Drift → Resource
```

외부 사용자/조직 시스템 FK는 물리 FK 적용 가능 여부를 확인한다.

---

# 34. 31단계 — Check Constraint / Validation

DB Constraint로 보장 가능한 항목과
Application Validation을 분리한다.

후보:

```text
USE_YN IN ('Y','N')
SCORE BETWEEN 1 AND 5
EXCEPTION_END_DATE >= EXCEPTION_START_DATE
TARGET_COMPLETE_DATE >= PLANNED_START_DATE
```

하지만 복잡한 Workflow Rule은 DB Constraint로 과도하게 구현하지 않는다.

예:

```text
Critical이면 조치계획 필수
```

이는 Application/Batch Quality Gate로 관리하는 것이 적절할 수 있다.

---

# 35. 32단계 — Index 설계

조회패턴을 기준으로 인덱스를 설계한다.

화면 주요 검색조건:

```text
RESOURCE_ID
NSIGHT_AREA
SYSTEM/SERVICE
RESOURCE_TYPE
PRODUCT
ENV
CENTER
OWNER_ORG
EOS_DATE
CURRENT_STATUS
RISK_LEVEL
ACTION_STATUS
EXCEPTION_STATUS
```

다음 복합 인덱스 후보를 실제 조회패턴과 건수 기반으로 검토한다.

```text
STATUS + EOS_DATE
RISK_LEVEL + ACTION_STATUS
OWNER_ORG + ENV
PRODUCT_VERSION_ID
```

인덱스를 무조건 많이 만들지 않는다.

각 Index에 대해 사용 Query를 명시한다.

---

# 36. 33단계 — 조회성능/대용량 설계

현재 Excel 샘플은 수십 건이지만
시스템은 향후 수천~수만 자원으로 확대될 수 있다.

다음을 검토한다.

```text
Server Side Paging
대량 Export
Dashboard 집계
월간 Snapshot
History 증가
Audit 증가
Collection Result 증가
```

성장량이 큰 테이블:

```text
CHANGE_HISTORY
COLLECTION_RESULT
NOTIFICATION
MONTHLY_CHECK
```

보관기간과 Partition 필요성을 향후 용량 기준으로 검토한다.

---

# 37. 34단계 — 보안·개인정보 데이터 설계

다음 데이터의 민감성을 검토한다.

```text
담당자
승인자
조직
서버/설치위치
점검명령
수집 Raw Result
파일/증빙
계약정보
```

특히 점검결과 Raw Output에 다음이 포함되지 않도록 한다.

```text
Password
Private Key
Token
Credential
개인정보
내부 보안설정 원문
```

필요 시 Masking/암호화/접근권한을 정의한다.

---

# 38. 35단계 — 감사와 변경재현

다음 질문에 DB만으로 답할 수 있어야 하는지 확인한다.

```text
2026년 6월 당시 이 자원의 Version은 무엇이었나?
그 당시 EOS 상태는 무엇이었나?
누가 EOS 날짜를 변경했나?
Critical 판정은 어떤 평가기준으로 이루어졌나?
예외를 누가 언제 승인했나?
월간보고 당시 숫자는 왜 그렇게 나왔나?
```

필요하면 `VALID_FROM / VALID_TO`, Snapshot, History를 설계한다.

---

# 39. 36단계 — 데이터 보관·폐기

각 데이터 유형에 대해 보관정책 후보를 작성한다.

| 데이터 | Active | 종료 후 | 삭제/Archive | 근거 |
|---|---|---|---|---|

특히 다음은 장기보존 가능성을 검토한다.

```text
예외승인
감사로그
월간 Snapshot
조치 완료증빙
Lifecycle 근거
```

보관기간은 원본에 없으면 `[확인필요]`로 남긴다.

---

# 40. 37단계 — 초기 데이터 Migration

현재 Excel 데이터를 시스템 초기 데이터로 이관할 경우
Migration Rule을 작성한다.

다음 순서로 검토한다.

```text
Code
→ Product
→ Product Version
→ Lifecycle
→ Resource
→ Installation
→ Risk
→ Action
→ Exception
→ Approval
```

다음 데이터 문제를 별도 Migration Issue로 관리한다.

```text
중복 자원ID
동일 제품명 표기 차이
동일 Version 표기 차이
EOS 날짜 불일치
담당조직 자유텍스트
예외기간 역전
Dashboard 계산값 불일치
```

원본 오류를 자동으로 보정하지 않는다.

---

# 41. 38단계 — DDL 생성 기준

최종 설계가 확정되면 Oracle DDL 초안을 생성한다.

순서:

```text
01_CODE_TABLES.sql
02_PRODUCT_TABLES.sql
03_RESOURCE_TABLES.sql
04_LIFECYCLE_TABLES.sql
05_RISK_TABLES.sql
06_ACTION_TABLES.sql
07_EXCEPTION_TABLES.sql
08_COLLECTION_TABLES.sql
09_AUDIT_TABLES.sql
10_INDEX_CONSTRAINTS.sql
11_INITIAL_CODE_DATA.sql
```

DDL에는 다음을 포함한다.

```text
CREATE TABLE
COMMENT ON TABLE
COMMENT ON COLUMN
PK
FK
UK
CHECK
INDEX
```

프로젝트에서 Sequence/Identity 표준이 확인되면 그 기준을 사용한다.

확인되지 않으면 하나를 임의로 확정하지 않는다.

---

# 42. 39단계 — 표준 테이블 정의서 형식

각 테이블을 다음 형식으로 작성한다.

# {TABLE_NAME} — {논리명}

## 1. 목적

## 2. 업무책임

## 3. Key

```text
PK:
Business Key:
UK:
FK:
```

## 4. 컬럼 정의

| No | 컬럼명 | 논리명 | 타입 | 길이 | PK | FK | NN | Default | 코드/Domain | 설명 |
|---:|---|---|---|---:|---|---|---|---|---|---|

## 5. 관계

## 6. CRUD 주체

## 7. 이력관리

## 8. 보안/감사

## 9. 인덱스

## 10. 데이터 예시

## 11. Validation

## 12. 관련 화면

## 13. 관련 요구사항

---

# 43. 40단계 — Column Domain 정의

반복되는 데이터 타입/도메인을 표준화한다.

예:

```text
RESOURCE_ID
PRODUCT_ID
ORG_ID
USER_ID
CODE
YN
DATE
DATETIME
DESCRIPTION
VERSION_TEXT
SCORE
```

각 Domain에 대해 다음을 작성한다.

| Domain | 의미 | 물리Type | 길이 | 허용값 | 적용컬럼 |
|---|---|---|---:|---|---|

실제 DA 표준 Domain이 존재하면 반드시 그 기준을 사용한다.

---

# 44. 41단계 — 화면과 데이터 추적성

모든 화면항목을 Entity/Column과 연결한다.

표준표:

| 화면ID | 항목ID | 화면항목 | Entity | Column | CRUD | 요구사항ID |
|---|---|---|---|---|---|---|

특히 다음을 검증한다.

```text
화면 입력항목인데 저장 Column이 없음
DB 필수 Column인데 입력/자동생성 경로가 없음
ReadOnly 계산값인데 DB 저장/계산 책임이 불명확
```

---

# 45. 42단계 — 요구사항과 데이터 추적성

다음 Matrix를 작성한다.

| 요구사항ID | 데이터 Entity | Column | Rule | 화면 | API/Service | Test |
|---|---|---|---|---|---|---|

P0 요구사항이 데이터모델에 반영되지 않으면 설계 누락으로 보고한다.

---

# 46. 43단계 — API/Service와 DB 추적성

실제 API/ServiceId가 제공되면 다음을 작성한다.

| API/Service | 기능 | Table | CRUD | Transaction | Lock |
|---|---|---|---|---|---|

API가 확정되지 않았다면 임의의 ServiceId를 만들지 않고
`[확인필요]`로 남긴다.

---

# 47. 44단계 — Transaction 설계

다음 업무단위별 Transaction 경계를 검토한다.

```text
자원 등록
Lifecycle 변경
위험평가 완료
조치계획 상태변경
예외 신청
예외 승인
월간 Snapshot 생성
자동수집 결과 반영
Drift 해소
```

특히 여러 테이블이 동시에 변경되는 경우
원자성 요구를 정의한다.

예:

```text
예외 승인
= Exception 상태변경
+ Approval 생성
+ Resource 예외표시 갱신 여부
+ Notification 생성
```

Resource에 파생상태를 중복 저장하는 경우 정합성 위험을 분석한다.

---

# 48. 45단계 — 동시성/Lock 설계

다음 충돌을 고려한다.

```text
두 사용자가 같은 Resource 수정
평가자가 동시에 위험평가 완료
조치담당자와 관리자가 상태 변경
승인자가 이미 처리된 예외 승인
Lifecycle 일괄 변경 중 개별 Resource 수정
```

Optimistic Lock 후보:

```text
VERSION_NO
UPDATED_AT
```

업무에 따라 Pessimistic Lock이 필요한지 별도 검토한다.

---

# 49. 46단계 — 삭제 정책

각 Entity에 대해 다음 중 하나를 결정한다.

```text
Physical Delete
Logical Delete
Status Close
Archive
```

다음은 일반적으로 물리삭제 제한을 검토한다.

```text
Product Version
Lifecycle
Resource
Risk Assessment
Action Plan
Exception
Approval
Monthly Snapshot
Audit
```

원본 요구사항이 없으면 `[추가제안]`으로 표시한다.

---

# 50. 47단계 — 데이터 품질 Rule

최소 다음 Rule을 설계한다.

```text
RESOURCE_CODE Unique

Product + Version 중복 통제

EOS_DATE / EOL_DATE 형식

예외 종료일 >= 시작일

Risk Score 1~5

Total Risk Score = Detail 합계

Risk Level = Policy 결과

완료 상태이면 실제완료일 존재

예외 승인 상태이면 승인정보 존재

월간 Snapshot 대상월 Unique

관리 Version과 발견 Version 비교

사용중 코드 삭제 금지
```

Rule마다 실행위치를 정의한다.

```text
DB Constraint
Application
Batch
Quality Gate
```

---

# 51. 48단계 — 데이터 검증 SQL

설계서에 운영/이행 검증용 SQL 후보를 포함한다.

예:

```text
중복 Resource ID
Lifecycle 없는 Product Version
Critical인데 Action Plan 없는 Resource
승인됐는데 Approval 없는 Exception
만료된 예외
완료인데 완료일 없는 Action
Risk 총점 불일치
Drift 미해소
```

실제 Table/Column 확정 후 SQL을 작성한다.

---

# 52. 49단계 — 테스트 데이터 설계

최소 다음 Case를 표현하는 Seed Data를 설계한다.

```text
정상
주의
경고
위험
Critical
High
Medium
Low
조치진행중
테스트중
완료
예외신청
조건부승인
예외만료
Version Drift
수집실패
```

운영 실데이터를 복사하지 않는다.

---

# 53. 50단계 — 데이터베이스 설계 대안 비교

최소 다음 핵심 의사결정을 ADR 후보로 정리한다.

## ADR-DB-01
Product/Version/Resource 분리 여부

## ADR-DB-02
Lifecycle Current + History 관리 방식

## ADR-DB-03
위험평가 Fixed Column vs Detail Row 모델

## ADR-DB-04
Dashboard 실시간 집계 vs Snapshot/집계테이블

## ADR-DB-05
첨부 Binary DB 저장 vs 외부 파일 저장소

## ADR-DB-06
공통코드 통합 vs 업무기준 별도테이블

## ADR-DB-07
Resource 현재상태 물리저장 vs 동적계산

## ADR-DB-08
자동수집 결과와 Drift 별도테이블 여부

각 ADR은 다음을 포함한다.

```text
문제
대안
장점
단점
성능영향
운영영향
데이터정합성
권고안
확인필요
```

---

# 54. 데이터베이스 설계서 최종 문서 구조

최종 데이터베이스 설계서는 프로젝트 문서작성 기준에 맞춰
다음 구조를 따른다.

# 1. 도입 전 안내말

# 2. 문서 개요

## 2.1 목적
## 2.2 적용범위
## 2.3 대상 독자
## 2.4 선행조건
## 2.5 용어 정의

# 3. 본문

## 3.1 문제 정의 및 설계 배경

## 3.2 현행 Excel 데이터 구조와 문제점

## 3.3 요구사항과 제약조건

## 3.4 데이터 설계 원칙

## 3.5 데이터 모델 대안 비교 및 의사결정

## 3.6 목표 논리 데이터 아키텍처

## 3.7 논리 ERD

## 3.8 물리 데이터 아키텍처

## 3.9 물리 ERD

## 3.10 Entity 정의

## 3.11 Table 목록

## 3.12 Table별 상세설계

## 3.13 Column Domain

## 3.14 PK / FK / UK / Constraint

## 3.15 코드·기준정보

## 3.16 Lifecycle 데이터 설계

## 3.17 위험평가 데이터 설계

## 3.18 조치계획 데이터 설계

## 3.19 예외·승인 데이터 설계

## 3.20 월간 Snapshot·보고 데이터 설계

## 3.21 점검·수집·Drift 데이터 설계

## 3.22 첨부·증빙 데이터 설계

## 3.23 변경이력·감사 데이터 설계

## 3.24 화면·API·DB 연계 규칙

## 3.25 데이터 상태 및 Workflow

## 3.26 정상 처리 흐름

## 3.27 오류·Timeout·장애 흐름

## 3.28 정상 예시

## 3.29 금지 예시

## 3.30 성능·용량·확장성

## 3.31 인덱스 설계

## 3.32 보안·개인정보·감사

## 3.33 운영·모니터링·장애 대응

## 3.34 자동검증 및 품질 Gate

## 3.35 데이터 Migration

## 3.36 테스트 시나리오

## 3.37 데이터 품질 검증 SQL

## 3.38 체크리스트

## 3.39 변경·호환성·폐기 관리

# 4. 시사점

## 4.1 핵심 아키텍처 판단
## 4.2 주요 위험
## 4.3 우선 보완 과제
## 4.4 중장기 발전 방향

# 5. 마무리말

---

# 55. 반드시 포함할 결과표

최종 결과에는 최소 다음 표를 포함한다.

1. 입력 데이터 요구사항 Inventory
2. Entity 목록
3. Entity 책임 정의
4. Business Key 목록
5. 논리 관계 Matrix
6. Table 목록
7. Table별 Column 정의
8. PK/FK/UK 목록
9. 코드·기준정보 목록
10. Index 목록
11. Lifecycle Rule
12. 위험평가 Rule
13. Action 상태전이
14. Exception 상태전이
15. Audit 대상 목록
16. 데이터 보관정책
17. 화면-Column Matrix
18. 요구사항-Entity Matrix
19. API-Table CRUD Matrix
20. Migration Mapping
21. 데이터 품질 Issue
22. Validation Rule
23. 테스트 데이터 Case
24. ADR 후보
25. 확인필요사항

---

# 56. DDL 산출물 요구

가능하면 최종 데이터베이스 설계서 외에 다음 DDL 산출물도 생성한다.

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
├─ 11_initial_code_data.sql
└─ 99_validation_queries.sql
```

단, 테이블·컬럼이 미확정 상태라면 DDL을 확정본처럼 만들지 않는다.

---

# 57. 정상 데이터 예시

설계서에는 하나의 Resource가 전체 생명주기를 거치는 예시를 제공한다.

```text
Product
Apache Tomcat

Product Version
8.5.x

Resource
EOS-WAS-001

Lifecycle
EOS = 2024-03-31

Risk
Critical

Action Plan
Upgrade → 10.1.x

Exception
필요한 경우 한시승인

Monthly Check
예외기간 중 매월 수행

Completion
Target Version 적용 및 증빙

History
변경 전체 보존
```

이 예시는 구조 설명용이며,
원본 데이터와 다른 값이면 `[예시]`라고 표시한다.

---

# 58. 금지 설계 예시

다음 설계는 명시적으로 금지한다.

```text
EOS_RESOURCE 한 테이블에 모든 업무데이터 저장

Product명·Version·EOS 날짜를 모든 Resource 행에 반복 저장

위험평가 7개 점수를 문자열 한 컬럼에 JSON처럼 저장
(명확한 기술적 근거가 없는 경우)

예외 시작/종료를 "2026-01~2026-06" 문자열 하나로 저장

승인자 이름만 저장하고 사용자 식별자 없음

현재상태·위험등급·조치상태·예외상태를 STATUS 하나로 통합

완료상태인데 완료일이나 완료증빙 관계 없음

월간보고를 실시간 원장 값으로만 재계산하여 과거 재현 불가

변경이력 없이 EOS 날짜 덮어쓰기

자동수집 Raw Output에 Credential 저장

코드값을 자유입력 문자열로 관리

날짜를 VARCHAR2로 저장

Product Version을 NUMBER로만 가정

모든 기준정보를 단일 Common Code에 무리하게 수용
```

---

# 59. 데이터베이스 설계 Quality Gate

최종 결과를 내기 전에 반드시 다음을 검증한다.

## Source

- [ ] 원본 Excel의 8개 시트를 분석했는가?
- [ ] 요구사항 정의서를 분석했는가?
- [ ] 화면설계서의 모든 저장/조회 항목을 확인했는가?
- [ ] 원본과 추가제안을 구분했는가?

## 모델

- [ ] Product / Version / Resource를 구분했는가?
- [ ] Lifecycle 책임 Entity를 명확히 했는가?
- [ ] Resource와 Product Version 관계를 정의했는가?
- [ ] 위험·조치·예외가 Resource와 연결되는가?
- [ ] Current와 History를 구분했는가?

## Key

- [ ] 모든 Entity에 PK가 있는가?
- [ ] Business Key가 정의되었는가?
- [ ] Unique Rule이 필요한 항목을 정의했는가?
- [ ] FK와 삭제정책이 정의되었는가?

## 상태

- [ ] 현재상태, 위험등급, 조치상태, 예외상태가 분리됐는가?
- [ ] 상태전이가 데이터모델과 일치하는가?

## 위험

- [ ] 7개 평가항목을 저장할 수 있는가?
- [ ] 총점/등급 재현이 가능한가?
- [ ] 평가이력이 가능한가?

## 예외

- [ ] 시작/종료일이 Date로 분리되는가?
- [ ] 승인정보가 신청정보와 분리되는가?
- [ ] 월간점검과 연결되는가?
- [ ] 예외 만료를 식별할 수 있는가?

## 운영

- [ ] 월간 Snapshot을 재현할 수 있는가?
- [ ] 자동수집 결과를 저장할 수 있는가?
- [ ] Version Drift를 판단할 수 있는가?
- [ ] 증빙을 연결할 수 있는가?
- [ ] 변경이력과 감사가 가능한가?

## 성능

- [ ] 주요 조회조건 Index를 검토했는가?
- [ ] History/Collection 증가량을 검토했는가?
- [ ] Dashboard 집계전략을 검토했는가?

## 추적성

- [ ] P0 요구사항이 Entity와 연결되는가?
- [ ] 화면 입력항목이 Column과 연결되는가?
- [ ] API/Service CRUD가 Table과 연결되는가?
- [ ] 테스트가 Validation Rule과 연결되는가?

하나라도 충족하지 못하면
데이터베이스 설계 완료로 보고하지 않는다.

---

# 60. 최종 실행 지시

이제 제공된 EOS 원본 Excel, 요구사항 정의서, 화면설계서를 직접 분석하라.

기본 원본:

```text
NH_상호금융정보계_EOS_자원관리_샘플양식.xlsx
```

관련 프롬프트/산출물:

```text
EOS_자원관리_분석_요구사항_도출_마스터프롬프트.md
EOS 요구사항 정의서
EOS_자원관리_화면설계서_작성_마스터프롬프트.md
EOS 화면설계서
```

다음 순서로 수행한다.

```text
1. Source Inventory
2. 데이터 요구사항 Inventory
3. Business Key 분석
4. Product / Version / Resource 책임 분리
5. 논리 Entity 도출
6. 논리 ERD
7. Lifecycle 모델
8. Risk 모델
9. Action 모델
10. Exception / Approval 모델
11. Monthly Check / Snapshot 모델
12. Collection / Drift 모델
13. Evidence / Audit 모델
14. 코드·정책 모델
15. 물리 Table 설계
16. Column / Domain
17. PK / FK / UK / Constraint
18. Index
19. Transaction / Lock
20. 보안·감사
21. 보관·폐기
22. Migration
23. DDL
24. 화면·API·요구사항 추적성
25. Validation SQL
26. Test Data
27. ADR
28. Quality Gate
```

최종 목적은 단순히 테이블을 만드는 것이 아니다.

다음 질문에 데이터베이스 구조만으로도 일관되게 답할 수 있어야 한다.

```text
이 자원은 어떤 Product와 Version을 사용하는가?
그 Version의 공식 EOS/EOL은 언제인가?
같은 Version을 사용하는 자원은 몇 개인가?
현재 위험등급은 무엇이며 어떤 평가로 결정됐는가?
과거 위험등급은 무엇이었는가?
어떤 조치계획이 진행 중인가?
목표 Version은 무엇인가?
예외는 누가 왜 승인했는가?
예외는 언제 만료되는가?
월간점검은 수행됐는가?
실제 발견 Version과 관리 Version은 일치하는가?
누가 언제 어떤 값을 변경했는가?
특정 월말 보고 당시 상태를 다시 재현할 수 있는가?
```

최종 산출물은 Markdown 형식의 **EOS 데이터베이스 설계서**로 작성하고,
가능하면 별도의 Oracle DDL 파일 세트까지 생성하라.
