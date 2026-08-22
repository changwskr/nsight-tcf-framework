# EOS Physical Data Model (M08)

> Agent Step: **M08**  
> 논리: [EOS-LOGICAL-ERD.md](./EOS-LOGICAL-ERD.md)  
> DDL 참고: [ddl/TB_EOS_P0_H2.sql](./ddl/TB_EOS_P0_H2.sql)  
> 판정일: 2026-08-16

---

## 1. 물리 명명

| 구분 | 규칙 | 예 |
|------|------|-----|
| 테이블 | `TB_EOS_<ENTITY>` | `TB_EOS_PRODUCT` |
| PK | `TB_EOS_<ENTITY>_PK` | |
| UK | `TB_EOS_<ENTITY>_UK n` | |
| FK | `TB_EOS_<ENTITY>_FK_<REF>` | |
| Index | `IX_EOS_<ENTITY>_<COLS>` | |
| ID | `VARCHAR(40)` | 채번 CONF-003 |
| Code | `VARCHAR(20)`~`VARCHAR(40)` | CODE 테이블 값 |
| Yn | `CHAR(1)` `Y`/`N` | CHECK 권장 |
| 업무일 | `VARCHAR(8)` `YYYYMMDD` | H2·Oracle 공통 (논리 DATE) |
| 일시 | `VARCHAR(17)` `yyyyMMddHHmmssSSS` | FW `REG_DTM` 관례 |
| 장문 | `VARCHAR(2000)` / `CLOB` | 조치·예외 본문 |

> 타깃 DB가 Oracle이면 업무일을 `DATE`, 일시를 `TIMESTAMP`로 승격 가능. 컬럼 의미는 동일.

---

## 2. 테이블 목록 (P0)

| # | 테이블 | 논리 Entity | 화면 |
|---|--------|-------------|------|
| 1 | `TB_EOS_PRODUCT` | PRODUCT | 040 |
| 2 | `TB_EOS_PRODUCT_VER` | PRODUCT_VERSION | 040/030 |
| 3 | `TB_EOS_PRODUCT_LFC` | PRODUCT_LIFECYCLE | 040 |
| 4 | `TB_EOS_RESOURCE` | RESOURCE | 010~030 |
| 5 | `TB_EOS_RISK_ASSESS` | RISK_ASSESSMENT | 050 |
| 6 | `TB_EOS_RISK_SCORE` | RISK_SCORE | 050 |
| 7 | `TB_EOS_ACTION_PLAN` | ACTION_PLAN | 060 |
| 8 | `TB_EOS_ACTION_ST_HIST` | ACTION_STATUS_HIST | 060 |
| 9 | `TB_EOS_ACTION_EVID` | ACTION↔EVIDENCE | 060 |
| 10 | `TB_EOS_EXC_REQ` | EXCEPTION_REQUEST | 070 |
| 11 | `TB_EOS_EXC_APPR` | EXCEPTION_APPROVAL | 080 |
| 12 | `TB_EOS_MONTHLY_CHK` | MONTHLY_CHECK | 090 |
| 13 | `TB_EOS_EVIDENCE` | EVIDENCE | 040/060/090 |
| 14 | `TB_EOS_CODE_GRP` | CODE_GROUP | 140 |
| 15 | `TB_EOS_CODE` | CODE | 140 |
| 16 | `TB_EOS_POLICY` | POLICY | 140 |
| 17 | `TB_EOS_CHG_HIST` | CHANGE_HISTORY/AUDIT | 150 |

P1 stub (DDL 미포함): `TB_EOS_MONTHLY_SNAP`, `TB_EOS_COLLECT_*`, `TB_EOS_DRIFT_*`.

`TB_EOS_RESOURCE_INSTALL` — P0 제외 (DM-001).

---

## 3. 컬럼·제약 (요약)

### 3.1 `TB_EOS_PRODUCT`

| Column | Type | NN | 비고 |
|--------|------|----|------|
| PRODUCT_ID | VARCHAR(40) | Y | PK |
| PRODUCT_NAME | VARCHAR(120) | Y | |
| VENDOR_CD | VARCHAR(40) | | |
| RESOURCE_TYPE_CD | VARCHAR(40) | Y | |
| USE_YN | CHAR(1) | Y | DEFAULT 'Y' |
| REMARK | VARCHAR(500) | | |
| REG_USER_ID / REG_DTM | VARCHAR(50)/17 | Y | |
| CHG_USER_ID / CHG_DTM | VARCHAR(50)/17 | | |

**UK1:** `(RESOURCE_TYPE_CD, PRODUCT_NAME)`

### 3.2 `TB_EOS_PRODUCT_VER`

| Column | Type | NN |
|--------|------|----|
| VERSION_ID | VARCHAR(40) | Y PK |
| PRODUCT_ID | VARCHAR(40) | Y FK→PRODUCT |
| VERSION_NO | VARCHAR(80) | Y |
| USE_YN | CHAR(1) | Y |
| REMARK | VARCHAR(500) | |
| REG_*/CHG_* | | |

**UK1:** `(PRODUCT_ID, VERSION_NO)`

### 3.3 `TB_EOS_PRODUCT_LFC`

| Column | Type | NN |
|--------|------|----|
| LFC_ID | VARCHAR(40) | Y PK |
| VERSION_ID | VARCHAR(40) | Y FK→VER |
| GA_YMD / EOS_YMD / EOL_YMD | VARCHAR(8) | |
| EVIDENCE_ID | VARCHAR(40) | FK→EVIDENCE |
| SOURCE_DESC | VARCHAR(500) | |
| EFFECTIVE_FROM_DTM | VARCHAR(17) | Y |
| EFFECTIVE_TO_DTM | VARCHAR(17) | | NULL=현재 |
| CHANGE_REASON | VARCHAR(500) | Y |
| REG_* | | |

**IX:** `(VERSION_ID, EFFECTIVE_TO_DTM)` — 현재 LFC 조회  
**규칙:** 동일 VERSION의 `EFFECTIVE_TO_DTM IS NULL` 행은 애플리케이션에서 1건 유지 (부분 UK는 H2 제약 한계 → 서비스 보장).

### 3.4 `TB_EOS_RESOURCE`

| Column | Type | NN |
|--------|------|----|
| RESOURCE_ID | VARCHAR(40) | Y PK |
| RESOURCE_NAME | VARCHAR(120) | Y |
| VERSION_ID | VARCHAR(40) | Y FK→VER |
| ENV_CD / CENTER_CD / NSIGHT_AREA_CD / ORG_CD | VARCHAR(40) | ENV Y |
| HOST_NAME | VARCHAR(120) | |
| IP_ADDR | VARCHAR(64) | |
| OWNER_USER_ID | VARCHAR(50) | |
| EOS_STATUS_CD | VARCHAR(40) | Y |
| RISK_GRADE_CD | VARCHAR(40) | | 캐시 |
| EXCEPTION_ACTIVE_YN | CHAR(1) | Y DEFAULT 'N' |
| USE_YN / DISPOSE_YN | CHAR(1) | Y |
| REMARK | VARCHAR(500) | |
| REG_*/CHG_* | | |

**IX:** `(EOS_STATUS_CD)`, `(RISK_GRADE_CD)`, `(ORG_CD)`, `(VERSION_ID)`  
**금지:** Risk 7항목·조치본문·예외사유 컬럼 없음.

### 3.5 `TB_EOS_RISK_ASSESS` / `TB_EOS_RISK_SCORE`

**ASSESS:** ASSESS_ID PK, RESOURCE_ID FK, TOTAL_SCORE INT, RISK_GRADE_CD, COMMENT_TXT, ASSESSOR_ID, ASSESS_DTM, POLICY_ID(or VER)

**SCORE:** PK `(ASSESS_ID, ITEM_CD)`, SCORE INT CHECK 1~5

**IX:** `(RESOURCE_ID, ASSESS_DTM DESC)`

### 3.6 `TB_EOS_ACTION_PLAN` / `ST_HIST` / `EVID`

조치 본문·Rollback·전환·일정 컬럼은 논리모델 §3.7과 동일 매핑.  
STATUS_CD만 현재값; 전이는 `TB_EOS_ACTION_ST_HIST`.  
완료증빙 N:M → `TB_EOS_ACTION_EVID (ACTION_ID, EVIDENCE_ID)`.

### 3.7 `TB_EOS_EXC_REQ` / `TB_EOS_EXC_APPR`

신청·승인 분리. APPR.APPROVER_ID ≠ REQ.REQ_USER_ID는 **DB CHECK 불가(상관)** → 서비스 SoD.  
연장: `EXTEND_OF_APPR_ID` self-FK.

### 3.8 `TB_EOS_MONTHLY_CHK`

UK `(EXC_REQ_ID, CHECK_YM)` — V-090-02를 UK로 확정(동일월 1건). 재점검은 UPDATE 또는 정책 변경 시 UK 완화.

### 3.9 `TB_EOS_CODE_GRP` / `TB_EOS_CODE`

CODE PK `(GRP_ID, CODE)`. USE_YN, VALID_FROM_YMD, VALID_TO_YMD. 물리 DELETE 금지.

### 3.10 `TB_EOS_POLICY`

| Column | 비고 |
|--------|------|
| POLICY_ID | PK |
| POLICY_TYPE_CD | EOS_THRESHOLD / RISK_GRADE_BAND / KPI_FORMULA / ALERT_RULE |
| POLICY_KEY | |
| POLICY_VAL | VARCHAR(2000) 또는 CLOB (JSON) |
| EFFECTIVE_FROM_DTM / TO | 이력형 |
| USE_YN | |

### 3.11 `TB_EOS_CHG_HIST`

RULE-171 필드. BEFORE_JSON / AFTER_JSON `CLOB`.  
**수정·삭제 API 없음** (앱 레벨). DB 권한은 조회·INSERT only 권장.

### 3.12 `TB_EOS_EVIDENCE`

STORAGE_URI, FILE_NAME, CONTENT_TYPE, ACL_SCOPE_CD, REG_*.

---

## 4. FK 관계

```text
PRODUCT ──< PRODUCT_VER ──< PRODUCT_LFC
                │
                └──< RESOURCE ──< RISK_ASSESS ──< RISK_SCORE
                         │
                         ├──< ACTION_PLAN ──< ACTION_ST_HIST
                         │         └──< ACTION_EVID >── EVIDENCE
                         │
                         └──< EXC_REQ ──< EXC_APPR
                                   └──< MONTHLY_CHK

CODE_GRP ──< CODE
EVIDENCE <── PRODUCT_LFC (optional)
```

---

## 5. 인덱스 전략

| 용도 | Index |
|------|--------|
| Dashboard 상태집계 | `IX_EOS_RSC_STATUS (EOS_STATUS_CD, DISPOSE_YN)` |
| 위험등급 KPI | `IX_EOS_RSC_RISK (RISK_GRADE_CD, DISPOSE_YN)` |
| 조직별 목록 | `IX_EOS_RSC_ORG (ORG_CD)` |
| Version→Instance 일괄 | `IX_EOS_RSC_VER (VERSION_ID)` |
| 예외 Inbox | `IX_EOS_EXC_REQ_ST (STATUS_CD, REQ_DTM)` |
| 감사 검색 | `IX_EOS_CHG_ENT (ENTITY_TYPE, ENTITY_ID, EVENT_DTM)` |
| 감사 Trace | `IX_EOS_CHG_TRACE (TRACE_ID)` |

---

## 6. 보관·이력

| 대상 | 정책 |
|------|------|
| PRODUCT_LFC | 종료행 보관, 물리삭제 금지 |
| RISK_ASSESS | 전 이력 보관 |
| ACTION_ST_HIST | 전 전이 보관 |
| EXC_APPR | 연장 체인 보관 |
| CHG_HIST | 장기 보관 (아카이브 Batch는 P1) |
| CODE | USE_YN=N만 |

---

## 7. 프로토타입 이행

| From `TB_EOS_ASSET_EOL` | To |
|-------------------------|-----|
| ASSET_ID / ASSET_NAME | RESOURCE |
| PRODUCT_NAME | PRODUCT (신규 채번) |
| VERSION_NO | PRODUCT_VER |
| EOS_DATE / EOL_DATE | PRODUCT_LFC |
| RISK_CD / STATUS_CD | RESOURCE 캐시 + (평가 없으면 코드만) |
| 테이블 자체 | **Deprecated** — H2 기동 시 DROP 후 신규 DDL 적용 (구현 단계) |

런타임 `schema.sql` 교체는 **GATE-D 이후·구현(M12+)** 에서 수행. 본 M08은 설계·참고 DDL만 제공.

---

## 8. ID 채번 (CONF-003 잠정)

| ID | 형식 (잠정) |
|----|-------------|
| PRODUCT_ID | `PRD` + yyyyMMdd + seq |
| VERSION_ID | `VER` + … |
| RESOURCE_ID | `RSC` + … |
| ASSESS_ID | `RSK` + … |
| ACTION_ID | `ACT` + … |
| EXC_REQ_ID | `EXC` + … |
| 기타 | 접두 3자 + 일시 + seq |

확정은 ADR. 시퀀스 테이블 또는 DB SEQUENCE는 M08 구현 시 선택.

---

## 9. 다음

1. **M09 GATE-D** — PK/UK/FK/Index/History/Audit/Traceability  
2. **M10** 서비스 설계 (`eos/04-service/`)  
