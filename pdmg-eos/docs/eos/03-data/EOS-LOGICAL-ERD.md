# EOS Logical Data Model (M07)

> Agent Step: **M07**  
> 근거: `EOS-RULES.md` RULE-090~095, `AGENT.md` §20–21  
> 화면 연계: `eos/02-screen/EOS-SCREEN-DESIGN.md`  
> 물리 DDL: M08 (`EOS-PHYSICAL-ERD.md`) — 본 문서는 **논리 Entity·관계**만 정의  
> 판정일: 2026-08-16

---

## 1. 설계 원칙

| 원칙 | 내용 |
|------|------|
| 분리 | PRODUCT → VERSION → RESOURCE(Instance) 3단. Risk/Action/Exception을 RESOURCE에 몰아넣지 않음 (`RULE-091`) |
| 이력 | Lifecycle·Risk·예외승인·조치상태·정책 변경은 **덮어쓰기 금지**, 이력/신규행 |
| 코드 | 상태·등급·유형 등은 CODE 참조 (`RULE-094`). 자유텍스트 코드 금지 |
| 날짜 | 논리 타입은 Date/DateTime (`RULE-092`). (물리 H2/Oracle 매핑은 M08) |
| Version | 문자열 (`RULE-093`) |
| 현행 GAP | `TB_EOS_ASSET_EOL` 단일 테이블 → 본 모델로 **대체** (`DATA-GAP-001`) |

물리 테이블명 후보 접두: `TB_EOS_*` (M08에서 확정).

---

## 2. 핵심 ER (개념)

```text
EOS_PRODUCT 1──* EOS_PRODUCT_VERSION 1──0..1 EOS_PRODUCT_LIFECYCLE
                         │
                         │ 1
                         ▼
              EOS_RESOURCE (Instance)
                         │
         ┌───────────────┼───────────────┬────────────────┐
         ▼               ▼               ▼                ▼
 EOS_RISK_ASSESSMENT  EOS_ACTION_PLAN  EOS_EXCEPTION_REQUEST  EOS_EVIDENCE
         │               │               │
         ▼               ▼               ▼
   (점수항목/이력)  STATUS_HIST    EXCEPTION_APPROVAL
                                       │
                                       ▼
                                 MONTHLY_CHECK

EOS_CODE_GROUP 1──* EOS_CODE
EOS_POLICY (임계값·위험등급구간·KPI·알림)
EOS_CHANGE_HISTORY / EOS_AUDIT_LOG  (횡단)
```

P1(수집·Drift·월간 Snapshot) Entity는 §6에 stub만 두고 P0 FK는 열어둠.

---

## 3. Entity 정의 (P0)

### 3.1 EOS_PRODUCT

| 속성 | 논리타입 | NN | 설명 |
|------|----------|----|------|
| PRODUCT_ID | ID | Y | PK |
| PRODUCT_NAME | String | Y | 예: Apache Tomcat |
| VENDOR_CD | Code | | → CODE(벤더) |
| RESOURCE_TYPE_CD | Code | Y | 자원구분 CHANNEL/NET/… |
| USE_YN | Yn | Y | |
| REMARK | Text | | |
| REG_* / CHG_* | Audit | Y | |

**BK:** (RESOURCE_TYPE_CD, PRODUCT_NAME) 또는 VENDOR+NAME `[확인필요 CONF-003 계열]`

---

### 3.2 EOS_PRODUCT_VERSION

| 속성 | 논리타입 | NN | 설명 |
|------|----------|----|------|
| VERSION_ID | ID | Y | PK |
| PRODUCT_ID | FK | Y | → PRODUCT |
| VERSION_NO | String | Y | `8.5.x` 등 |
| USE_YN | Yn | Y | |
| REMARK | Text | | |
| REG_* / CHG_* | Audit | Y | |

**UK:** (PRODUCT_ID, VERSION_NO)

---

### 3.3 EOS_PRODUCT_LIFECYCLE

Product Version의 **공식** EOS/EOL 일정. Instance에 날짜를 복제 저장하지 않는 것을 원칙으로 하되, 조회 편의상 파생 컬럼은 뷰/조인.

| 속성 | 논리타입 | NN | 설명 |
|------|----------|----|------|
| LIFECYCLE_ID | ID | Y | PK |
| VERSION_ID | FK | Y | → VERSION (1:1 권장) |
| GA_DATE | Date | | |
| EOS_DATE | Date | | End of Support |
| EOL_DATE | Date | | End of Life |
| EVIDENCE_ID | FK | | 근거 첨부 |
| SOURCE_DESC | Text | | 근거 설명 |
| EFFECTIVE_FROM | DateTime | Y | |
| EFFECTIVE_TO | DateTime | | 이력 종료 |
| CHANGE_REASON | Text | Y | 변경 시 |
| REG_* | Audit | Y | |

**규칙:** 변경 시 기존 행 EFFECTIVE_TO 종료 + 신규 행 INSERT (`RULE-061`). 현재 유효 1건만 EFFECTIVE_TO IS NULL.

---

### 3.4 EOS_RESOURCE (Instance)

| 속성 | 논리타입 | NN | 설명 |
|------|----------|----|------|
| RESOURCE_ID | ID | Y | PK (채번 CONF-003) |
| RESOURCE_NAME | String | Y | |
| VERSION_ID | FK | Y | 관리 Version |
| ENV_CD | Code | Y | 환경 |
| CENTER_CD | Code | | 센터 |
| HOST_NAME | String | | |
| IP_ADDR | String | | |
| NSIGHT_AREA_CD | Code | | |
| ORG_CD | Code | | 담당조직 |
| OWNER_USER_ID | String | | 담당자 |
| EOS_STATUS_CD | Code | Y | 서버 재산정 상태 |
| RISK_GRADE_CD | Code | | 최신 평가 등급(파생 캐시) |
| EXCEPTION_ACTIVE_YN | Yn | Y | 유효예외 Badge용 캐시 |
| USE_YN / DISPOSE_YN | Yn | Y | 폐기 |
| REMARK | Text | | |
| REG_* / CHG_* | Audit | Y | |

**금지:** RISK 점수 7항목, 조치계획 본문, 예외사유를 본 테이블에 저장.

잔여일·임박 여부는 Lifecycle.EOS_DATE + Batch/조회 시 계산 (`RULE-200`).

---

### 3.5 EOS_RESOURCE_INSTALLATION `[선택]`

동일 Host에 다수 설치가 필요하면 분리. P0에서는 RESOURCE에 Host 속성을 두고 Installation은 **P1/확장**.

| 속성 | 설명 |
|------|------|
| INSTALL_ID | PK |
| RESOURCE_ID | FK |
| INSTALL_PATH / LISTEN_PORT | |

---

### 3.6 EOS_RISK_ASSESSMENT

| 속성 | 논리타입 | NN | 설명 |
|------|----------|----|------|
| ASSESS_ID | ID | Y | PK |
| RESOURCE_ID | FK | Y | |
| TOTAL_SCORE | Int | Y | **서버 계산** |
| RISK_GRADE_CD | Code | Y | **서버 계산** |
| COMMENT_TXT | Text | | |
| ASSESSOR_ID | String | Y | |
| ASSESS_DTM | DateTime | Y | |
| POLICY_VER | String | | 적용 등급정책 버전 |

**규칙:** 재평가 = INSERT only (`RULE-033`). RESOURCE.RISK_GRADE_CD는 최신 ASSESS 반영.

#### EOS_RISK_SCORE (항목)

| 속성 | NN | 설명 |
|------|----|------|
| ASSESS_ID | Y | FK |
| ITEM_CD | Y | BIZ/ENV/EXP/SEC/IMP/ALT/EOS |
| SCORE | Y | 1~5 |

**PK:** (ASSESS_ID, ITEM_CD)

---

### 3.7 EOS_ACTION_PLAN

| 속성 | 논리타입 | NN | 설명 |
|------|----------|----|------|
| ACTION_ID | ID | Y | PK |
| RESOURCE_ID | FK | Y | |
| ACTION_TYPE_CD | Code | Y | Upgrade/… |
| CUR_VERSION_ID | FK | | |
| TGT_VERSION_ID | FK | | |
| DETAIL_TXT | Text | Y | |
| IMPACT_TXT | Text | Y | |
| PREREQ_TXT | Text | | |
| TEST_PLAN_TXT | Text | Y | |
| CUTOVER_TYPE_CD | Code | Y | Rolling/… |
| OUTAGE_YN | Yn | Y | |
| OFFHOURS_YN | Yn | | |
| DR_VERIFY_YN | Yn | | |
| ROLLBACK_COND_TXT | Text | Y | |
| ROLLBACK_PROC_TXT | Text | Y | |
| ROLLBACK_TARGET_TXT | Text | Y | |
| ORG_CD / OWNER_USER_ID | | Y | |
| PLAN_START_DT / PLAN_END_DT | Date | Y | |
| ACTUAL_END_DT | Date | | |
| STATUS_CD | Code | Y | 미착수…완료 |
| ISSUE_TXT | Text | | |
| REG_* / CHG_* | Audit | Y | |

상태 변경은 `EOS_ACTION_STATUS_HIST`에 기록하고, 허용 전이만 서비스에서 통제 (`RULE-042`).

#### EOS_ACTION_STATUS_HIST

| 속성 | NN |
|------|----|
| HIST_ID | Y |
| ACTION_ID | Y |
| FROM_STATUS_CD / TO_STATUS_CD | Y |
| CHG_USER_ID / CHG_DTM / REASON | Y |

#### EOS_ACTION_EVIDENCE (완료증빙)

| 속성 | NN |
|------|----|
| ACTION_ID + EVIDENCE_ID | Y |
| VERIFY_USER_ID / VERIFY_DTM / VERIFY_RESULT_CD | 완료검증 시 |

---

### 3.8 EOS_EXCEPTION_REQUEST

| 속성 | 논리타입 | NN | 설명 |
|------|----------|----|------|
| EXC_REQ_ID | ID | Y | 신청번호 PK |
| RESOURCE_ID | FK | Y | |
| REQ_ORG_CD / REQ_USER_ID | | Y | |
| REQ_DTM | DateTime | Y | |
| START_DT / END_DT | Date | Y | END≥START |
| REASON_TXT | Text | Y | |
| BLOCKER_TXT | Text | Y | 즉시조치 불가 |
| MITIGATION_TXT | Text | Y | 임시대책 |
| FINAL_PLAN_TXT | Text | Y | |
| FINAL_TARGET_DT | Date | Y | |
| MONTHLY_CHECK_YN | Yn | Y | |
| EXIT_CRITERIA_TXT | Text | Y | |
| STATUS_CD | Code | Y | 임시저장/승인대기/… |

#### EOS_EXCEPTION_APPROVAL

| 속성 | NN | 설명 |
|------|----|------|
| APPROVAL_ID | Y | PK |
| EXC_REQ_ID | Y | FK |
| DECISION_CD | Y | 승인/조건부/반려 |
| CONDITION_TXT | | 조건부 시 |
| REJECT_REASON_TXT | | 반려 시 |
| APPROVER_ID | Y | **≠ REQ_USER_ID** |
| APPROVE_DTM | Y | |
| EXTEND_OF_APPROVAL_ID | | 연장 시 원승인 FK (`RULE-053`) |

승인 유효 시 RESOURCE.EXCEPTION_ACTIVE_YN=Y. **위험등급은 내리지 않음** (`RULE-022`).

---

### 3.9 EOS_MONTHLY_CHECK

| 속성 | NN | 설명 |
|------|----|------|
| CHECK_ID | Y | PK |
| EXC_REQ_ID 또는 APPROVAL_ID | Y | 유효 예외 참조 |
| CHECK_YM | Y | YYYYMM |
| MITIGATION_OK_YN | Y | |
| RESIDUAL_RISK_TXT | | |
| PLAN_PROGRESS_TXT | | |
| ISSUE_TXT | | |
| NEXT_CHECK_DT | | |
| CHECKER_ID / CHECK_DTM | Y | |

**UK 후보:** (EXC_REQ_ID, CHECK_YM) — 재등록 정책 V-090-02.

---

### 3.10 EOS_EVIDENCE

| 속성 | NN | 설명 |
|------|----|------|
| EVIDENCE_ID | Y | PK |
| FILE_NAME / CONTENT_TYPE / STORAGE_URI | Y | |
| ACL_SCOPE | Y | |
| REG_USER_ID / REG_DTM | Y | |

Lifecycle 근거, 조치 완료증빙, 점검 첨부에서 참조.

---

### 3.11 EOS_CODE_GROUP / EOS_CODE

| CODE_GROUP | CODE | 속성 |
|------------|------|------|
| GROUP_ID, GROUP_NAME | CODE, NAME, SORT_ORD, USE_YN, VALID_FROM/TO | 물리삭제 금지 |

그룹 예: RESOURCE_TYPE, ENV, CENTER, EOS_STATUS, RISK_GRADE, ACTION_TYPE, ACTION_STATUS, APPROVAL_STATUS, CUTOVER_TYPE, NSIGHT_AREA, VENDOR, RISK_ITEM.

---

### 3.12 EOS_POLICY

정책 종류별 행 또는 JSON/키-값. 최소 분리:

| POLICY_TYPE | 내용 |
|-------------|------|
| EOS_THRESHOLD | 임박/도래 일수 |
| RISK_GRADE_BAND | 점수→등급 구간 |
| KPI_FORMULA | Dashboard KPI (CONF-001) |
| ALERT_RULE | 알림 시점·대상 |

변경 시 버전/유효기간 + CHANGE_HISTORY.

---

### 3.13 EOS_CHANGE_HISTORY / EOS_AUDIT_LOG

`RULE-171` 필드. Entity 단위 업무이력과 시스템 Audit를 하나로 둘지 분리는 M08 선택.

| 속성 | NN |
|------|----|
| HIST_ID / TRACE_ID | Y |
| USER_ID / ORG_CD / SERVICE_ID | Y |
| ENTITY_TYPE / ENTITY_ID / ACTION_CD | Y |
| BEFORE_JSON / AFTER_JSON | |
| RESULT_CD / REASON_TXT | |
| EVENT_DTM | Y |

---

## 4. 관계 요약

| From | To | Cardinality | 비고 |
|------|-----|-------------|------|
| PRODUCT | VERSION | 1:N | |
| VERSION | LIFECYCLE | 1:N (유효 1) | 이력형 |
| VERSION | RESOURCE | 1:N | Instance |
| RESOURCE | RISK_ASSESSMENT | 1:N | 최신=MAX(DTM) |
| ASSESSMENT | RISK_SCORE | 1:7 | |
| RESOURCE | ACTION_PLAN | 1:N | |
| ACTION | STATUS_HIST | 1:N | |
| RESOURCE | EXCEPTION_REQUEST | 1:N | |
| REQUEST | APPROVAL | 1:N | 연장 포함 |
| APPROVAL/REQUEST | MONTHLY_CHECK | 1:N | |
| * | EVIDENCE | N:1 | |
| CODE_GROUP | CODE | 1:N | |

---

## 5. 화면 Traceability (P0)

| Screen | 주요 Entity |
|--------|-------------|
| EOS-010 | RESOURCE + 집계(상태/등급/예외/조치) |
| EOS-020/030 | RESOURCE, VERSION, PRODUCT, LIFECYCLE |
| EOS-040 | PRODUCT, VERSION, LIFECYCLE, EVIDENCE |
| EOS-050 | RISK_ASSESSMENT, RISK_SCORE, POLICY |
| EOS-060 | ACTION_PLAN, STATUS_HIST, EVIDENCE |
| EOS-070/080 | EXCEPTION_REQUEST, APPROVAL |
| EOS-090 | MONTHLY_CHECK |
| EOS-140 | CODE_*, POLICY |
| EOS-150 | CHANGE_HISTORY / AUDIT |

---

## 6. P1 Stub (FK만 예약)

| Entity | 용도 |
|--------|------|
| EOS_MONTHLY_SNAPSHOT | 월간보고 EOS-100 (`RULE-191`) |
| EOS_COLLECTION_* / DRIFT_* | 자동수집·불일치 |
| EOS_ALERT_OUTBOX | 알림 발송 |

---

## 7. 현행 소스 매핑 (폐기 예정)

| 현행 | 이관 |
|------|------|
| `TB_EOS_ASSET_EOL.ASSET_*` | → RESOURCE (+ NAME) |
| `PRODUCT_NAME` + `VERSION_NO` | → PRODUCT / VERSION |
| `EOL_DATE` / `EOS_DATE` | → LIFECYCLE |
| `RISK_CD` / `STATUS_CD` | → 파생 캐시 또는 평가/상태 코드 |
| 단일 테이블 나머지 | Risk/Action/Exception **미존재** → 신규 |

---

## 8. Open Issues → M08/ADR

| ID | 내용 |
|----|------|
| CONF-003 | RESOURCE_ID / PRODUCT_ID 채번 |
| CONF-004 | EOL을 EOS_STATUS 코드에 포함 여부 |
| DM-001 | INSTALLATION P0 포함 여부 (기본: 제외) |
| DM-002 | CHANGE_HISTORY vs AUDIT_LOG 통합 |
| DM-003 | RISK_GRADE·EXCEPTION_ACTIVE 캐시 동기화 트랜잭션 |

---

## 9. 다음

1. ~~M08 물리~~ → [EOS-PHYSICAL-ERD.md](./EOS-PHYSICAL-ERD.md), [ddl/TB_EOS_P0_H2.sql](./ddl/TB_EOS_P0_H2.sql)  
2. ~~M09 GATE-D~~ → [EOS-GATE-D.md](./EOS-GATE-D.md) **CONDITIONAL PASS**  
3. **M10** 서비스 설계 (`eos/04-service/`)  
