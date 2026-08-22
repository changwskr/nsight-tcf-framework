# EOS GATE-R — Requirements Quality Gate

> 기준: `AGENT.md` §15 M03  
> 근거: `analysys/분석및요건정의.md`, `analysys/00.분석및요건_종합정리.md`  
> 판정일: 2026-08-16

---

## 체크리스트

| # | 항목 | 결과 | 비고 |
|---|------|------|------|
| 1 | Excel 핵심항목 ↔ 요구사항 연결 | **PASS** | 8시트 → RSC/LFC/RSK/ACT/EXC/RPT/COL 등 |
| 2 | Product / Version / Resource 구분 | **PASS (설계반영)** | 요건 §25·종합정리 §3.2 — 구현 전 모델 확정 필요 |
| 3 | EOS / EOL / 계약종료 구분 | **PASS** | `EOS-LFC-001~003` |
| 4 | 위험평가 기준 | **PASS** | 7항목·등급표 `EOS-RSK-*` |
| 5 | 조치 Workflow | **PASS** | `EOS-ACT-*` + `EOS-TRN-*` |
| 6 | 예외 Workflow | **PASS** | `EOS-EXC-*` + SoD |
| 7 | 권한 정의 | **PASS (개요)** | 역할 목록 있음 — RACI 상세는 화면·서비스 설계에서 보강 |
| 8 | 감사 정의 | **PASS** | §18 이력 필드·대상 |
| 9 | 데이터 품질 Rule | **PASS** | §20 Gate + §21 샘플 Issue |
| 10 | 테스트 가능 Acceptance | **PARTIAL** | ID별 서술형 — 전용 AC 표는 Traceability에서 보강 |

---

## [확인필요] (구현 차단이 아닌 정책 보류)

| ID | 내용 | 임시 처리 |
|----|------|-----------|
| CONF-001 | Dashboard `위험 상태` KPI 산정식 (27 vs 등급합 36) | 화면설계에 KPI 정의 슬롯 두고 코드정책으로 관리 |
| CONF-002 | EOL·계약종료를 상태판정에 포함할지 | P0는 EOS 잔여일만 사용 `[추가제안]` |
| CONF-003 | 월간보고를 P0 필수 vs P1 화면 | P0에 Snapshot API 최소, 화면은 P1 허용 |

---

## Gate 판정

```text
GATE-R = CONDITIONAL PASS
```

- P0 업무 요구사항은 화면·데이터·서비스 설계로 진행 가능.
- AC 표·RACI 상세·KPI 산정식은 후속 산출물에서 보강.
- **다음 단계: M04 화면 정보구조 (`SCREEN_DESIGN`)**
