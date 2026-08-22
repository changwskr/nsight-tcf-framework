# NSIGHT Architecture Orchestration System Design Spec

## 목표

기존 Architecture Closed Loop를 사용자가 이해하기 쉬운 **Mission → Orchestrator → Agent Team → Execution Board → Decision → Baseline** 운영모델과 결합한다.

## 설계 원칙

1. Orchestrator는 실행 제어만 담당하고 전문 분석은 Agent에게 위임한다.
2. Agent는 Run Scope와 Baseline을 변경할 수 없다.
3. 모든 결과는 Artifact + Evidence + Gate로 통제한다.
4. ServiceId를 핵심 E2E Trace Key로 사용한다.
5. 첫 적용은 Vertical Slice Pilot으로 시작한다.
6. Runtime Evidence 없는 Final PASS를 금지한다.
7. NSIGHT_TCF/PDMG/PDMK/PDMP Scope를 분리한다.

## 구성요소

- Master Prompt
- Orchestrator
- Agent Catalog
- Stage Workspace
- Stage Prompt
- Architecture Gate
- Templates
- Use Cases
- Governance
- User View Specification

## 사용자 경험

```text
Mission 입력
→ 팀 구성 확인
→ 실행 진행 확인
→ Gate/Drift 확인
→ 필요한 의사결정 승인
→ Baseline Release 확인
```

## 내부 데이터 흐름

```text
Run Manifest
→ Source Baseline
→ Document Baseline
→ Architecture Model
→ Source Map
→ Architecture Test
→ Runtime Evidence
→ Drift
→ GAP/ADR
→ Approval
→ Baseline Release
```

## 성공 기준

- 하나의 실제 ServiceId가 Requirement/Code/SQL/Test/Runtime까지 연결된다.
- 선택된 Agent와 선택 이유가 표시된다.
- 모든 Gate 결과는 Evidence를 가진다.
- Critical Drift가 열린 상태에서 HG90 PASS가 불가능하다.
