# NSIGHT Wave 5 Evidence Inbox

이 폴더는 **실제 승인/운영 증적**을 투입하기 위한 Inbox이다. Template/Reference/Synthetic 결과는 최종 승인으로 승격하지 않는다.

## 디렉터리

```text
98BQ-evidence-inbox/
├─ adr-approvals/       # ADR 승인 기록 *.json
├─ runtime/             # RUN-ID별 실제 runtime bundle
├─ production-config/   # Host별 evidence-manifest.json
├─ closure/             # P0 Closure 승인 기록 *.json
└─ examples/            # 예시 파일(.json.example) - 자동 Intake 제외
```

## ADR Approval 최소 필드

`adr_id`, `decision`, `approver`, `decision_date`, `evidence_ref`

- `decision`: `APPROVE | REJECT | DEFER`
- APPROVE만 ADR을 `APPROVED` 상태로 승격한다.

## Runtime 승인 계약

Runtime Result가 아래 3개를 모두 만족해야 승인된다.

```text
status = PASS
evidence_class = PRODUCTION_RUNTIME
runtime_approved = true
```

## P0 Closure 최소 필드

`item_id`, `status`, `approver`, `decision_date`, `evidence_ref`

허용 종료 상태는 `CLOSED_RUNTIME`, `CLOSED_APPROVED`이다.
