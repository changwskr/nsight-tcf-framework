# Architecture Orchestration Rules

## 1. Orchestration과 Closed Loop의 관계

```text
[사람이 보는 방식]

Mission
→ Team Plan
→ Progress
→ Decision
→ Result

[내부 실행 방식]

Document
→ Model
→ Code
→ Test
→ Runtime Evidence
→ Drift
→ GAP/ADR
→ Baseline
```

두 구조는 분리하지 않는다. Orchestration은 Closed Loop를 실행하는 상위 제어계층이다.

## 2. 실행 단위

실행 단위는 `Run`이다.

Run은 다음으로 식별한다.

```text
runId
missionId
systemScope
architectureBaselineId
sourceBaselineId
```

## 3. 팀 구성 원칙

- 단순 규칙 점검: 1~3 Agent
- ServiceId Vertical Slice: 5~8 Agent
- Architecture Decision 검증: 6~10 Agent
- Full Release Gate: 전체 핵심 Agent

Agent 수를 목표로 삼지 않는다. 필요한 책임만 선택한다.

## 4. Gate 원칙

```text
Artifact
  ↓
Evidence
  ↓
Evaluator
  ↓
Gate Result
```

Gate 상태:

`PASS / CONDITIONAL PASS / HOLD / REJECT`

`PASS`를 사람이 문자열로 직접 입력해 완료 처리하는 방식은 허용하지 않는다.

## 5. Runtime 원칙

Logging은 Runtime Evidence가 아니다.

Runtime Evidence는 아래 Chain과 연결되어야 한다.

```text
architectureBaselineId
→ architectureModelVersion
→ sourceCommit
→ buildId
→ artifactHash
→ deploymentId
→ serviceId
→ traceId
→ evidenceId
```

## 6. Human Approval 원칙

아래 영역은 사람 승인이 필요한 통제 대상이다.

- Transaction Boundary
- Session/JWT/Security
- 개인정보/마스킹/암호화
- 데이터 소유권
- 장애 전파/격리
- 대안 선택
- 예외
- Baseline 승격
