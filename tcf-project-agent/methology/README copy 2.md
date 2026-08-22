# NSIGHT Architecture Agent Workspace

이 Workspace는 약 8개월간 진행된 농협 상호금융 NSIGHT 정보계 프로젝트를
**Evidence → Baseline → As-Is → Gap/Drift → ADR → Target → 상세 아키텍처 → 검증 → As-Built**
순서로 재정립하기 위한 Architecture Agent 실행공간이다.

## 1. 핵심 파일

- `00-GOVERNANCE/AGENTS.md`
  - Agent 역할, 책임, Gate, Handoff, 상태관리 규칙
- `00-GOVERNANCE/03.ARCHITECTURE-RULES.md`
  - TCF, ServiceId, 패키지, Transaction, Timeout, JWT, 데이터, 용량, 운영 등 아키텍처 불변규칙
- `00-GOVERNANCE/04.단계별 Agent 프롬프트.md`
  - A00~A16 전체 실행 프롬프트
- `00-GOVERNANCE/MASTER-AGENT-PROMPT.md`
  - 최상위 Architecture Master Agent 시작 프롬프트

## 2. 단계 구조

```text
A00 초기화
A01 Baseline
A02 As-Is
A03 Gap/Drift
A04 Requirements/NFR
A05 Principles/ADR
A06 Target Architecture
A07 TCF/Application
A08 Security
A09 Data/Integration
A10 Infra/Capacity/Performance
A11 Operations/Failure
A12 Development/Golden Path
A13 Automatic Validation
A14 Traceability
A15 As-Built
A16 Roadmap
```

## 3. 각 단계 작업영역

각 단계 디렉터리에는 다음이 존재한다.

```text
agent-프롬프트.md
IN/         입력자료
OUT/        단계 산출물
evidence/   근거자료
notes/      작업 메모
```

## 4. 실행 원칙

```text
Evidence before Opinion
As-Is before To-Be
Decision before Implementation
Gate before Baseline
Traceability before Completion
Runtime before Assumption
As-Built before Closure
```

## 5. 시작 방법

1. `00-GOVERNANCE/MASTER-AGENT-PROMPT.md`를 최상위 Agent에 전달한다.
2. A00부터 시작한다.
3. 각 단계의 `agent-프롬프트.md`를 실행한다.
4. 입력자료는 해당 단계 `IN/`에 둔다.
5. Evidence는 해당 단계 `evidence/`에 둔다.
6. 산출물은 `OUT/`에 저장한다.
7. Gate 결과와 상태는 `90-STATE/architecture-state.yaml`에 반영한다.
