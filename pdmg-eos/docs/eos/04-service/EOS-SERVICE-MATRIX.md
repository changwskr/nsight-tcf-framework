# EOS Service Matrix (M10)

> Agent Step: **M10** — Use Case / ServiceId 식별  
> 화면: `EOS-SCREEN-DESIGN.md` · 데이터: `EOS-PHYSICAL-ERD.md`  
> 상세: [EOS-SERVICE-DESIGN.md](./EOS-SERVICE-DESIGN.md)  
> 패키지: `nhnis.eos.co.a` · Handler → Facade → Service → DAO

---

## 1. 도메인 × 유형

| Domain | QUERY | COMMAND | WORKFLOW | BATCH | ADMIN |
|--------|-------|---------|----------|-------|-------|
| dashboard | 0110S0 | | | | |
| resource | 0120S0, 0130S0 | 0130C0/U0/D0 | | 상태재산정 | |
| product/lifecycle | 0140S0 | 0140C0/U0 | 0140U1 일괄반영 | | |
| risk | 0150S0 | 0150C0 | | | |
| action | 0160S0 | 0160C0/U0 | 0160U1/U2, 0165U0 | | |
| exception | 0170S0, 0180S0 | 0170C0 | 0180U0/U1 | 만료 | |
| monthly-check | 0190S0 | 0190C0 | | 미수행알림 | |
| policy/code | 0141S0 | 0141C0/U0 | | | ADMIN |
| audit | 0151S0 | | | | |
| report | (P1 10xx) | | | Snapshot | |

---

## 2. P0 Service Catalog

| ServiceId | Type | Use Case | Primary Tables | Tx | 권한 |
|-----------|------|----------|----------------|-----|------|
| `eoscoa0110S0` | Q | Dashboard KPI 집계 | RESOURCE (+집계) | R | Viewer+ |
| `eoscoa0120S0` | Q | 자원 목록(슬림) | RESOURCE, VER, PRODUCT | R | Viewer+ |
| `eoscoa0120E0` | Q | 목록 Export | 동상 | R | Operator+ |
| `eoscoa0130S0` | Q | 자원 상세 | RESOURCE + 조인 | R | Viewer+ |
| `eoscoa0130C0` | C | 자원 등록 | RESOURCE | RW+Audit | Operator+ |
| `eoscoa0130U0` | C | 자원 수정 | RESOURCE | RW+Audit | Operator+ |
| `eoscoa0130D0` | C | 자원 폐기 | RESOURCE.DISPOSE | RW+Audit | Operator+ |
| `eoscoa0140S0` | Q | Lifecycle 목록/상세 | PRODUCT, VER, LFC | R | Viewer+ |
| `eoscoa0140C0` | C | Lifecycle 등록 | LFC | RW+Hist | Admin |
| `eoscoa0140U0` | C | Lifecycle 변경(이력) | LFC close+insert | RW+Hist | Admin |
| `eoscoa0140U1` | W | LFC변경 + Instance 상태 재계산 | LFC, RESOURCE | RW+Audit | Admin |
| `eoscoa0150S0` | Q | 위험평가 최신+이력 | RISK_ASSESS, SCORE | R | Viewer+ |
| `eoscoa0150C0` | C | 재평가 등록 | ASSESS+SCORE, RSC 캐시 | RW+Hist | Operator+ |
| `eoscoa0160S0` | Q | 조치계획 목록/상세 | ACTION_PLAN | R | Viewer+ |
| `eoscoa0160C0` | C | 조치 신규 | ACTION_PLAN + ST_HIST | RW | Operator+ |
| `eoscoa0160U0` | C | 조치 내용수정 | ACTION_PLAN | RW | Operator+ |
| `eoscoa0160U1` | W | 상태전이 | PLAN + ST_HIST | RW | Operator+ |
| `eoscoa0160U2` | W | 완료요청 | PLAN + EVID | RW | Operator+ |
| `eoscoa0165U0` | W | 완료검증 승인 | PLAN + EVID | RW+SoD | Approver+ |
| `eoscoa0170S0` | Q | 예외신청 조회 | EXC_REQ | R | Operator+ |
| `eoscoa0170C0` | C | 예외신청 | EXC_REQ | RW | Operator+ |
| `eoscoa0180S0` | Q | 승인 Inbox/상세 | EXC_REQ, APPR | R | Approver+ |
| `eoscoa0180U0` | W | 승인/조건부/반려 | APPR, REQ, RSC 캐시 | RW+SoD | Approver+ |
| `eoscoa0180U1` | W | 예외연장 | APPR chain | RW+SoD | Approver+ |
| `eoscoa0190S0` | Q | 월간점검 대상 | MONTHLY_CHK, EXC | R | Viewer+ |
| `eoscoa0190C0` | C | 점검 등록 | MONTHLY_CHK | RW | Operator+ |
| `eoscoa0141S0` | Q | 코드·정책 조회 | CODE, POLICY | R | Admin |
| `eoscoa0141C0` | C | 코드·정책 등록 | CODE/POLICY | RW+Audit | Admin |
| `eoscoa0141U0` | C | 코드·정책 변경 | CODE/POLICY | RW+Audit | Admin |
| `eoscoa0151S0` | Q | 감사이력 | CHG_HIST | R | Viewer*/Admin |
| `eoscoa0100S0` | Q | legacy | ASSET_EOL | R | deprecate |

---

## 3. Batch (논리 Service, 구현 P0/P1)

| BatchId | 주기 | 내용 | 갱신 |
|---------|------|------|------|
| `eos.batch.statusRecalc` | Daily | EOS_STATUS 재산정 | RESOURCE |
| `eos.batch.exceptionExpire` | Daily | 예외 만료 → EXCEPTION_ACTIVE_YN=N | RSC, EXC |
| `eos.batch.actionOverdue` | Daily | 목표일 초과 표시/알림 | (알림 P1) |
| `eos.batch.monthlyCheckMiss` | Monthly | 미점검 알림 | (알림 P1) |

---

## 4. 서버 전용 계산 (Client 무시)

| 값 | 산출 서비스 |
|----|-------------|
| remainingDays / EOS_STATUS_CD | status 엔진 (조회·0140U1·Batch) |
| TOTAL_SCORE / RISK_GRADE_CD | `eoscoa0150C0` |
| EXCEPTION_ACTIVE_YN | `eoscoa0180U0/U1`, 만료 Batch |
| APPROVER / APPROVE_DTM | `eoscoa0180*` |
| Audit USER/DTM | 전 COMMAND/WORKFLOW |

---

## 5. 화면 → Service 매핑

| Screen | Services |
|--------|----------|
| EOS-010 | 0110S0 |
| EOS-020 | 0120S0, 0120E0 |
| EOS-030 | 0130*, Tab→0150/0160/0170 |
| EOS-040 | 0140* |
| EOS-050 | 0150S0/C0 |
| EOS-060 | 0160*, 0165U0 |
| EOS-070 | 0170S0/C0 |
| EOS-080 | 0180* |
| EOS-090 | 0190S0/C0 |
| EOS-140 | 0141* |
| EOS-150 | 0151S0 |

---

## 6. 다음

상세 Use Case·상태전이·오류코드: [EOS-SERVICE-DESIGN.md](./EOS-SERVICE-DESIGN.md)  
이후 **M11** 보강 → **GATE-S**.
