# Gate Result Template

```json
{
  "gateResultId": "GTR-HGXX-0001",
  "runId": "RUN-...",
  "stageId": "S...",
  "gateId": "HG-XX",
  "gateVersion": "1.0.0",
  "decision": "PENDING_APPROVAL",
  "qualityScore": 0,
  "hardFailureCount": 0,
  "requiredFailureCount": 0,
  "evaluatedAt": "2026-08-06T00:00:00+09:00",
  "summary": "",
  "ruleResults": [],
  "approvalIds": [],
  "exceptionIds": []
}
```

`decision`은 Agent가 제안만 한다. 최종 PASS/APPROVED는 사용자 승인 + `scripts/record_gate.cjs` / `promote_stage.cjs`로만 확정한다.
