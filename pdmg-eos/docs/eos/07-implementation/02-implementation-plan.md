# EOS Implementation Plan (M16)

> Agent Step: **M16**  
> Reference: mgcoa9000 · GAP: `01-gap-analysis.md` · ADR-001 Defaults  
> 일자: 2026-08-16  
> **코드 작성은 본 계획 Human 확인 후** (AGENT 구현 선점 금지). 다만 사용자가 「진행해」로 구현 착수를 지시하면 Wave1부터 실행.

---

## 1. Wave 개요

| Wave | 목표 | ServiceId | DB |
|------|------|-----------|-----|
| **W0** | 스키마 교체·코드시드·0100 유지/가드 | — | P0 DDL 적용, ASSET_EOL drop |
| **W1** | 원장 CRUD + Lifecycle | 0120, 0130*, 0140* | PRODUCT~RESOURCE, LFC |
| **W2** | Dashboard + Risk | 0110, 0150* | RISK_*, POLICY KPI |
| **W3** | Action | 0160*, 0165 | ACTION_* |
| **W4** | Exception + Monthly | 0170*, 0180*, 0190* | EXC_*, MONTHLY_CHK |
| **W5** | Admin + Audit | 0141*, 0151 | CODE, POLICY, CHG_HIST |
| **W6** | Batch stub + 0100 제거 + 테스트 | batch ids | — |

각 Wave 종료 시: compile + 핵심 API 수동/자동 스모크.

---

## 2. W0 상세

1. `schema.sql` ← `docs/eos/03-data/ddl/TB_EOS_P0_H2.sql` (+ TX_CONTROL 유지)  
2. `data.sql` 시드: CODE_GRP/CODE, RISK_GRADE_BAND, EOS_THRESHOLD, KPI(A), 샘플 PRODUCT/VER/LFC/RESOURCE 2~3건  
3. `eoscoa0100`는 W0~W1 동안 **깨지지 않게** 임시 뷰/매핑 또는 비활성 — 권장: W1에서 0120 완료 후 0100 삭제  

---

## 3. W1 상세 (우선 구현)

클래스 (9000 미러):

```text
eoscoa0120Handler/Facade/Service/DAO + S0 DTO + XML
eoscoa0130* (S0/C0/U0/D0)
eoscoa0140* (S0/C0/U0/U1)
domain/EosStatusEngine
support/EosIdGenerator (ADR CONF-003 A)
```

---

## 4. 공통 산출 (전 Wave)

- `exceptionCode.yml` EOS-E0001~0008  
- Audit helper → `TB_EOS_CHG_HIST`  
- Mapper namespace `rdw.eos.co.a`

---

## 5. 검증

| Wave | 검증 |
|------|------|
| W0 | 앱 기동, 테이블 존재 |
| W1 | 자원 등록→목록→상세, LFC 변경 후 상태 변화 |
| W2 | KPI 숫자·위험 재평가 이력 |
| W3 | 금지전이 거부, Rollback 필수 |
| W4 | SoD 403/E0002, 승인 후 Badge |
| W5 | 코드 비활성, 감사 조회 |
| W6 | legacy 제거, ST-* 테스트 |

---

## 6. 비범위 (본 계획)

- UI (`pdmg-ui`)  
- P1 월간보고 Snapshot·수집·Drift  
- Oracle 전용 DDL 최적화  

---

## 7. 착수 조건

- [x] GATE-R/U/D/S CONDITIONAL PASS  
- [x] M13~M15 문서  
- [x] ADR-001 Default  
- [ ] Human: 「Wave0/1 구현 진행」명시 (또는 연속 「진행해」= 착수 승인으로 해석 가능)
