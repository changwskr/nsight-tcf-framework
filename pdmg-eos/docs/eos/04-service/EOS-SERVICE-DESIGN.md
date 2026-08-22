# EOS Service Design (M10/M11 초안)

> 매트릭스: [EOS-SERVICE-MATRIX.md](./EOS-SERVICE-MATRIX.md)  
> 규칙: `EOS-RULES.md` RULE-031~053, 040~043, 서버계산 §26  
> 공통 레이어: Handler → Facade → Service → DAO (`pdmg-service` 동일)

---

## 1. 공통 규약

| 항목 | 규약 |
|------|------|
| API | Online TX `ServiceId` (FW OnlineTransactionController) |
| DTO | `eoscoaNNNNxDTOin/out` |
| 오류 | `exceptionCode.yml` 코드 (EOS-E****) |
| Audit | COMMAND/WORKFLOW 성공 시 `TB_EOS_CHG_HIST` INSERT |
| Idempotency | C0: BK UK 충돌 시 업무오류 / U1 상태전이: 동일상태 no-op or 거부 |
| Timeout | 목록·Dashboard 조회 SLA ~3s (요건) |
| 권한 | 서버 최종 검증 |

### 공통 오류 (초안)

| Code | 의미 |
|------|------|
| EOS-E0001 | Validation |
| EOS-E0002 | 권한/SoD |
| EOS-E0003 | 상태전이 불가 |
| EOS-E0004 | 대상 없음 |
| EOS-E0005 | 중복/UK |
| EOS-E0006 | 정책/기준정보 오류 |
| EOS-E0007 | 완료증빙 부족 |
| EOS-E0008 | Lifecycle 영향 미확인 |

---

## 2. UC — Dashboard (`eoscoa0110S0`)

**In:** 조직·환경 필터(옵션)  
**Out:** KPI (총자원, Critical, High, 예외필요, 조치중, …) + drill filter token  
**Rule:** KPI 산정식 = `TB_EOS_POLICY` KPI_FORMULA (CONF-001). 원장 실시간 집계 (`RULE-190`).  
**Tx:** Read-only.

---

## 3. UC — Resource

### 3.1 목록 `0120S0`

슬림 컬럼만. 필터: 상태·등급·조직·제품·예외YN. 페이징.

### 3.2 등록 `0130C0`

VERSION_ID 필수. EOS_STATUS는 **서버 재산정** 후 저장. RESOURCE_ID 채번(CONF-003).

### 3.3 폐기 `0130D0`

DISPOSE_YN=Y. 진행중 조치/유효예외 있으면 경고 또는 거부 `[확인필요]`.

---

## 4. UC — Lifecycle

### 4.1 변경 `0140U0`

1. 현재 LFC `EFFECTIVE_TO_DTM` 세팅  
2. 신규 LFC INSERT  
3. Audit  

### 4.2 일괄반영 `0140U1`

`0140U0` + 해당 VERSION_ID의 전 RESOURCE에 대해 status 엔진 재실행.  
영향건수 응답. 대량 시 비동기 `[추가제안 P1]`.

---

## 5. UC — Risk `0150C0`

**In:** RESOURCE_ID + 7×SCORE(1~5) + comment  
**Process:**
1. 점수 범위 Validation  
2. TOTAL = sum (서버)  
3. GRADE = POLICY RISK_GRADE_BAND (서버)  
4. ASSESS + SCORE INSERT (UPDATE 금지)  
5. RESOURCE.RISK_GRADE_CD 갱신  
6. Critical/High → 응답에 follow-up CTA 플래그  

Client `totalScore`/`grade` 무시.

---

## 6. UC — Action

### 6.1 신규 `0160C0`

Rollback 3필드 필수. 초기 STATUS=미착수 + ST_HIST.

### 6.2 상태전이 `0160U1`

허용 그래프 (`RULE-042`):

```text
미착수 → 계획수립 → 진행중 → 테스트중 → 완료
         ↘ 보류 / 예외관리 (정책 허용 시)
금지: 미착수 → 완료
```

완료는 `0160U2`→`0165U0`만.

### 6.3 완료요청 `0160U2` / 검증 `0165U0`

증빙·실제완료일 필수. 검증자 ≠ 요청자 (SoD).

---

## 7. UC — Exception

### 7.1 신청 `0170C0`

END_YMD ≥ START_YMD. 필수 텍스트 필드. STATUS=승인대기. 신청번호 채번.

### 7.2 승인 `0180U0`

| Decision | 처리 |
|----------|------|
| 승인 | APPR INSERT, REQ 상태, RSC.EXCEPTION_ACTIVE_YN=Y |
| 조건부 | CONDITION_TXT 필수 |
| 반려 | REJECT_REASON 필수, Active 유지N |

SoD: APPROVER_ID ≠ REQ_USER_ID → EOS-E0002.  
위험등급 **미변경** (`RULE-022`).

### 7.3 연장 `0180U1`

신규 APPR + EXTEND_OF_APPR_ID. 기간 Validation. 재승인.

---

## 8. UC — Monthly Check `0190C0`

유효 승인 예외만. UK (EXC_REQ_ID, CHECK_YM) 충돌 → EOS-E0005.

---

## 9. UC — Policy/Code `0141*`

Admin only. CODE 물리삭제 없음(USE_YN). RISK_GRADE_BAND 구간 겹침 검증.  
정책 변경 즉시 이후 평가/집계에 반영(캐시 TTL은 구현).

---

## 10. UC — Audit `0151S0`

필터·페이징. UPDATE/DELETE 서비스 없음.

---

## 11. Status Engine (공유 모듈)

입력: RESOURCE + 현재 LFC(EOS_YMD/EOL_YMD) + POLICY EOS_THRESHOLD + 오늘  
출력: EOS_STATUS_CD (+ remainingDays는 조회 DTO만)

호출: `0130C0/U0`, `0140U1`, `eos.batch.statusRecalc`, 상세조회 시 옵션 재계산.

---

## 12. 트랜잭션 경계

| Service | 단일 TX에 포함 |
|---------|----------------|
| 0150C0 | ASSESS+SCORE+RSC 캐시+CHG_HIST |
| 0140U1 | LFC 이력 + N×RSC 상태 (+CHG_HIST) |
| 0180U0 | APPR+REQ+RSC 캐시+CHG_HIST |
| 0160U1 | PLAN+ST_HIST+CHG_HIST |

부분 실패 시 전체 롤백.

---

## 13. GATE-S 준비 체크 (중간)

| 항목 | 상태 |
|------|------|
| Use Case–Service 매핑 | ✅ Matrix |
| 서버 계산 명시 | ✅ |
| 상태전이 서비스화 | ✅ 0160U1 |
| SoD | ✅ 0180/0165 |
| 오류코드 초안 | ✅ |
| API 필드 스펙(DTO) | ⏭ M11 보강 |
| Sequence Diagram | ⏭ 복잡 UC만 |

**GATE-S:** M11에서 DTO 필드·시퀀스 보강 후 판정 권장.

---

## 14. Sequence (핵심 UC)

### 14.1 위험 재평가 `0150C0`

```text
Client → Handler → Facade → RiskService
  → validate scores(1~5)
  → PolicyDao.loadRiskBands()
  → totalScore/grade = server calc
  → RiskDao.insertAssess + insertScores
  → ResourceDao.updateRiskGradeCache
  → AuditDao.insert
  ← assessId, totalScore, grade, followUp flags
```

### 14.2 Lifecycle 일괄반영 `0140U1`

```text
Client → … → LifecycleService
  → close current LFC + insert new LFC
  → list Resources by versionId
  → for each: StatusEngine.recalc → update EOS_STATUS_CD
  → Audit
  ← affectedCnt, statusChangedCnt
```

### 14.3 예외 승인 `0180U0`

```text
Client → … → ExceptionService
  → load EXC_REQ
  → if approverId == reqUserId → EOS-E0002
  → validate decision fields
  → insert EXC_APPR, update REQ status
  → if APPROVE/CONDITIONAL: RESOURCE.EXCEPTION_ACTIVE_YN=Y
  → Audit (risk grade unchanged)
  ← apprId, exceptionActiveYn
```

### 14.4 조치 상태전이 `0160U1`

```text
Client → … → ActionService
  → load ACTION_PLAN
  → StateMachine.assertAllowed(from, to)
  → update STATUS_CD + insert ST_HIST
  → Audit
  ← statusCd
```

---

## 15. DTO 상세

필드 목록: [EOS-SERVICE-DTO.md](./EOS-SERVICE-DTO.md)

---

## 16. 동시성

| UC | 전략 |
|----|------|
| 0150C0 | INSERT only — 충돌 없음 |
| 0140U0/U1 | 현재 LFC 행 낙관적: EFFECTIVE_TO IS NULL 재확인 |
| 0160U1 | STATUS_CD 조건 UPDATE (from 일치) 실패 시 EOS-E0003 |
| 0180U0 | REQ STATUS=승인대기 조건 UPDATE |

---

## 17. Test Case ID (서비스)

| ID | UC | 기대 |
|----|-----|------|
| ST-0150-01 | 조작 totalScore | 서버값 적용 |
| ST-0160-01 | 미착수→완료 | E0003 |
| ST-0180-01 | 본인승인 | E0002 |
| ST-0140-01 | U1 | Instance 상태 갱신 |
| ST-0190-01 | 동일 CHECK_YM | E0005 |
