# Runtime Evidence Intake → G80 Re-Gate Guide

## 1. 입력 순서

```text
Operator Runbook 실행
  ↓
Run Evidence Bundle
  ↓
98V Runtime Manifest Schema 검증
  ↓
PRODUCTION_RUNTIME Provenance 확인
  ↓
Run-specific Machine/Human Gate
  ↓
Runtime Registry 갱신
  ↓
ADR Closure Evidence 연결
  ↓
P0 Closure Matrix 갱신
  ↓
98BH Re-Gate Evaluator
```

## 2. Runtime PASS 최소계약

다음 세 조건이 동시에 만족되어야 Runtime 승인으로 계산한다.

```text
status = PASS
evidence_class = PRODUCTION_RUNTIME
runtime_approved = true
```

Synthetic, Reference, DRY_RUN, 문서상 기대값은 Runtime PASS로 승격하지 않는다.

## 3. Bundle Provenance 필수값

- Environment / Center / Host 또는 Test topology
- Run ID / Run timestamp
- Artifact version / config hash
- Operator / Change ticket 또는 승인번호
- Raw metric/log/db evidence path
- Evaluator result
- Human reviewer / approval timestamp

## 4. Re-Gate 시 갱신대상

- `31-RUNTIME-EVIDENCE.md`
- `35-ADR-REGISTER.md` 및 `98BI-ADR-APPROVAL-REGISTER.json`
- `32-DRIFT-REGISTER.md`
- `33-GAP-REGISTER.md`
- `34-RISK-REGISTER.md`
- `36-OPEN-ISSUES.md`
- `96-P0-CLOSURE-MATRIX.json`
- `37-ARCHITECTURE-GATE.md`

## 5. 금지

- 승인되지 않은 수치를 Runtime Approved Baseline으로 기록하지 않는다.
- Runbook 존재를 Runtime Evidence로 간주하지 않는다.
- ADR Proposal을 APPROVED로 간주하지 않는다.
