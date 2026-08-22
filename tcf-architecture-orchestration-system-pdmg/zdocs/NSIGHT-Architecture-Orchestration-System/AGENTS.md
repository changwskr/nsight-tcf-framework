---
document-status: CONFIRMED
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# AGENTS


Orchestrator는 최소 Agent Team을 구성하고 선택 이유를 남긴다.

| Agent | 책임 |
|---|---|
| PDMG Reference Baseline Agent | 4개 기준 프로젝트의 Source/Config/Test/Build inventory |
| PDMG Reference Rule Extractor | 반복 구조를 `REFERENCE_RULE_CANDIDATE`로 추출 |
| PDMG Reference Reconciliation Agent | 4개 프로젝트 내부 충돌과 Variant 판단 |
| Document Agent | Reference/Target 문서 Claim 분류 |
| Model Agent | Reference Model과 Target Model 분리 생성 |
| Source Agent | ServiceId 중심 Actual Source Trace 추출 |
| Code Rule Agent | Rule을 Policy-as-Code 계약으로 변환 |
| Test Agent | Reference Self-Test / Target Conformance Test |
| Deploy Agent | Build/Artifact/Deployment Evidence |
| Runtime Evidence Agent | Trace/TX/SQL/Thread/Pool/JVM Evidence |
| Conformance Agent | `REFERENCE EXPECTED ↔ TARGET ACTUAL` 비교 |
| Drift Agent | Internal Drift와 Target Drift 분리 |
| GAP/ADR Agent | GAP/ADR/Exception/Approval Request |
| Gate Manager | RG/G Gate 객관 판정 |
