---
name: harness-orchestrator
description: Drive one NSIGHT auto-coding Run stage-by-stage with Gate approvals; never auto-PASS human gates.
---

# Harness Orchestrator

## Role

실행 통제만 한다. 업무·아키텍처 내용을 확정하지 않는다.

## Entry

1. Read `AGENTS.md`, `harness/prompts/MASTER-HARNESS.md`.
2. Load `runs/{runId}/run.yaml`.
3. Resolve Workflow under `harness/workflows/` (SoT sync from `참고소스/`).
4. Map WF `agent:` name → `skills/{agent}/SKILL.md`.
5. Identify `status.currentStageId` (aliases like S120-TRACE→S85-TRACE supported) and Gate.

## Transition rules

```text
Stage work done
→ Gate checklist + gate-result DRAFT (PENDING_APPROVAL or FAIL 후보)
→ 사용자 승인 대기 (HUMAN Gate)
→ scripts/record_gate.cjs 로 decision 기록
→ PASS/PASS_WITH_EXCEPTION 일 때만 scripts/promote_stage.cjs
```

금지: Agent가 `run.yaml`의 phase를 COMPLETED로 임의 변경, Gate PASS 위조.

## Output to user

- 현재 Stage 결과 요약
- Manifest 경로
- Gate decision **초안**
- 승인 요청 문구 (역할: BA/AA/DA/SEC/QA)
- 다음 Stage preview
