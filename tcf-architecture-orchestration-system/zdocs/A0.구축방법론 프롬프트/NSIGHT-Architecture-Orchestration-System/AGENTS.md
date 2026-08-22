# AGENTS.md — NSIGHT Architecture Orchestration Agent Operating Rules

## 1. 최상위 원칙

모든 Agent는 Orchestrator의 `RUN-MANIFEST`와 `EXECUTION-PLAN`을 입력으로 사용한다.

Agent가 독자적으로 System Scope나 Baseline을 바꾸면 안 된다.


## 공통 상태 라벨

모든 판단에는 아래 라벨 중 하나를 붙인다.

`FACT / CONFIRMED / AS-IS / TO-BE / DECISION / PROPOSED / GAP / DEPRECATED / UNKNOWN / OPEN`

추정은 `FACT`가 아니다. `AS-IS != TO-BE`이면 임의 보정하지 말고 GAP으로 등록한다.



## 사실 판단 우선순위

1. 현재 실제 실행 Source
2. 현재 적용 Configuration
3. 현재 Runtime Evidence
4. 승인된 Architecture Baseline
5. 승인된 ADR
6. 최신 상세설계
7. 개발/운영 Guide
8. Book/Wiki
9. 과거 문서/대화
10. 일반 기술 이론


## 2. Agent 공통 입력

- `runId`
- `mission`
- `systemScope`
- `sourceBaselineId`
- 선행 Agent 산출물
- 허용된 Source 경로
- 제외 경로
- 해당 Stage Gate 규칙

## 3. Agent 공통 출력

모든 Agent는 다음을 남긴다.

```text
[STAGE]
[STATUS]
[SYSTEM SCOPE]
[FACT]
[AS-IS]
[TO-BE]
[GAP]
[UNKNOWN]
[EVIDENCE]
[ARTIFACTS]
[GATE CANDIDATE]
[NEXT HANDOFF]
```

## 4. Agent 간 직접 통신 규칙

Agent A가 Agent B를 직접 임의 호출하지 않는다.

```text
Agent
  ↓ 결과
Orchestrator
  ↓ Gate
Next Agent
```

예외적으로 동일 Stage 내 병렬 분석은 Orchestrator가 실행계획에 명시한 경우만 허용한다.

## 5. 금지

- Source에 없는 Class/ServiceId/Table을 추정 생성
- 과거 문서를 Current Baseline으로 자동 승격
- Runtime Evidence 없이 Runtime 완료 선언
- Evaluator 결과 없이 Gate PASS
- Critical Drift를 숨기고 다음 단계 진행
- Approval 없이 ADR/Baseline 확정
