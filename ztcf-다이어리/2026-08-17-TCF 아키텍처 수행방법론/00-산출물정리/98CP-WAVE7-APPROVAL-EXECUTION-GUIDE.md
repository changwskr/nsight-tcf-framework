# Wave 7 — Approval Execution Guide

## 목적

Architecture Board가 11개 승인 가능 ADR을 실제로 의결하고, 그 결과를 Wave 5 Evidence Intake가 읽을 수 있는 JSON으로 전환하는 절차를 정의한다.

## 절차

1. `98CL-ARCHITECTURE-BOARD-ONE-PAGE.md`로 11건을 일괄 검토한다.
2. 각 건을 `APPROVE / REJECT / DEFER`로 결정한다.
3. `approval-records/<ADR>.json`에 실제 `decision`, `approver`, `decision_date`, `condition`, `evidence_ref`를 기록한다.
4. 승인 완료 JSON만 `98BQ-evidence-inbox/adr-approvals/`로 복사한다.
5. Wave 5 Intake Tool을 재실행한다.
6. Runtime/P0 Closure가 남아 있으면 G80/HG90은 HOLD를 유지한다.

## 금지

- `recommended_decision=APPROVE`를 실제 승인으로 해석하지 않는다.
- 승인자/일자 없는 APPROVE를 Intake하지 않는다.
- Runtime 결과 없이 Session/HA/Capacity ADR을 억지로 최종화하지 않는다.
