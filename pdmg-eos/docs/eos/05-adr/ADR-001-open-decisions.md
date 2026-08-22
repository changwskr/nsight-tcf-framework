# ADR-001 — Open Design Decisions (EOS P0)

> 상태: Proposed  
> 일자: 2026-08-16  
> 관련: GATE-R/U/D/S Open Issues

구현 착수 전 Human 확정 권장. 미확정 시 **잠정(Default)** 으로 구현 가능.

---

## CONF-001 — Dashboard「위험 상태」KPI

| 안 | 내용 |
|----|------|
| A (Default) | `EOS_STATUS IN (임박, 도래, 초과)` 자원 수 (폐기 제외) |
| B | `RISK_GRADE IN (Critical, High)` |
| C | A∪B distinct |

**결정:** _(미정)_ → POLICY `KPI_FORMULA`로 저장, Default=A.

---

## CONF-003 — ID 채번

| 안 | 내용 |
|----|------|
| A (Default) | `접두3 + yyyyMMdd + 6digit seq` (앱 채번 테이블) |
| B | DB SEQUENCE / IDENTITY |
| C | UUID |

**결정:** _(미정)_ → Default=A.

---

## CONF-004 — EOL과 EOS_STATUS

| 안 | 내용 |
|----|------|
| A (Default) | 상태코드에 `EOL` 포함, EOS_YMD·EOL_YMD 임계로 판정 |
| B | EOL은 별도 플래그, 상태집합은 EOS만 |

**결정:** _(미정)_ → Default=A.

---

## SVC-001 — 자원 폐기 시

**Default:** 진행중 조치 또는 유효예외 있으면 **거부(E0001)**. 강제폐기=Admin + reason.

## SVC-002 — Lifecycle 일괄반영

**Default:** 동기 TX. 1회 500건 초과 시 오류 안내 후 배치 권고 (P1 비동기).

## UX-003 — UI 기술

**Decision:** `pdmg-ui` static (기존 workbench 패턴).

- 전문 테스트: `#/eoscoa` → `static/eoscoa/` · 기본 대상 `http://localhost:8082`
- 관리 화면: `#/eos` → `static/eos/` (EOS-010 KPI + EOS-020 목록 + 0130 상세)
- `pdmg-eos` CORS: `spring.mvc.cors` + `CorsConfigurationSource` (8090 허용)
- React 별도 앱은 P1 비범위
