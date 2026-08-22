# EOS GATE-U — Screen Design Gate

> Agent Step: **M06**  
> 근거: `AGENT.md` GATE-U, `EOS-RULES.md` RULE-070~075  
> 산출: `EOS-NAVIGATION.md`, `EOS-SCREEN-DESIGN.md`  
> 판정일: 2026-08-16

---

## 1. 판정 요약

| 항목 | 결과 |
|------|------|
| **GATE-U** | **CONDITIONAL PASS** |
| 근거 | P0 화면(010~090, 140, 150) 상세·ServiceId·Event 매핑 완료 |
| 잔여 | `[확인필요]` 정책·UX 항목 미결정 (구현 차단은 아님, 설계 ADR/정책 확정 필요) |
| 다음 | **M07** 논리 데이터 모델 (`eos/03-data/`) |

구현(풀 백엔드)은 GATE-U 이후 **서비스·DB 설계 게이트**까지 보류 (`AGENT` 구현 선점 금지).

---

## 2. 체크리스트

| # | 기준 | 결과 | 비고 |
|---|------|------|------|
| U-01 | IA/Nav에 P0 화면 정의 | ✅ | `EOS-NAVIGATION.md` |
| U-02 | Dashboard → Drill-down | ✅ | EOS-010 → 020/030 |
| U-03 | 목록 → 상세 → Tab(위험/조치/예외) | ✅ | RULE-072/073 |
| U-04 | Product Lifecycle ≠ Instance 혼동 방지 | ✅ | EOS-040 / 030 |
| U-05 | 위험점수·등급 서버 계산 UX | ✅ | EOS-050 |
| U-06 | 조치계획 Rollback·상태전이 | ✅ | EOS-060 |
| U-07 | 예외 ≠ 단순보류, SoD | ✅ | EOS-070/080 |
| U-08 | 예외 월간점검 화면 | ✅ | EOS-090 (보고는 P1) |
| U-09 | 코드·정책 하드코딩 금지 UI | ✅ | EOS-140 |
| U-10 | 감사이력 조회 | ✅ | EOS-150 |
| U-11 | Event–ServiceId 매핑 | ✅ | 화면별 표 |
| U-12 | Validation / Empty·권한 | ✅ | 화면별 |
| U-13 | P1 화면(100/110/120…) 상세 | ⏭ | GATE-U 범위 외 (후속) |
| U-14 | UI 기술 스택 확정 | ⚠ | UX-003 `[확인필요]` |

---

## 3. 화면–ServiceId 요약 (P0)

| Screen | ServiceId |
|--------|-----------|
| EOS-010 | `eoscoa0110S0` (+E0 P1) |
| EOS-020 | `eoscoa0120S0` (+E0) |
| EOS-030 | `eoscoa0130S0/C0/U0/D0` |
| EOS-040 | `eoscoa0140S0/C0/U0/U1` |
| EOS-050 | `eoscoa0150S0/C0` |
| EOS-060 | `eoscoa0160S0/C0/U0/U1/U2` (+0165U0) |
| EOS-070 | `eoscoa0170S0/C0` |
| EOS-080 | `eoscoa0180S0/U0/U1` |
| EOS-090 | `eoscoa0190S0/C0` |
| EOS-140 | `eoscoa0141S0/C0/U0` |
| EOS-150 | `eoscoa0151S0` |

---

## 4. Open `[확인필요]` (설계 이월)

| ID | 내용 | 권고 해소 시점 |
|----|------|----------------|
| CONF-001 | Dashboard「위험 상태」KPI 산정식 | EOS-140 KPI정책 / ADR |
| CONF-002 | Critical+무계획 hard block 여부 | RULE-040 정책 ADR |
| CONF-003 | 자원 ID 채번 규칙 | M07 데이터모델 |
| CONF-004 | EOL을 EOS 상태집합에 포함? | Lifecycle 정책 |
| UX-001 | 완료검증 독립화면(EOS-065) vs 탭 | 서비스설계 |
| UX-002 | = CONF-001 | |
| UX-003 | static UI vs `pdmg-ui` | 구현 착수 전 |
| V-090-02 | 월간점검 동일월 재등록 정책 | EOS-090 ADR |
| AUD-SCOPE | EOS-150 조직 범위 | 권한설계 |

---

## 5. Source GAP (참고)

현행 `eoscoa0100S0` + `TB_EOS_ASSET_EOL` 단일 테이블은 본 GATE 화면 집합을 충족하지 않음 → M07에서 재구성 (`DATA-GAP-001`).

---

## 6. 서명

| Role | 결과 |
|------|------|
| Agent | **CONDITIONAL PASS** — P0 화면설계 게이트 충족, Open 이슈는 정책/ADR로 이월 |
| Human | (검토 후 PASS / HOLD 확정) |
