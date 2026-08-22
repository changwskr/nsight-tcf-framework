# START HERE — Architecture Orchestration System 시작 방법

## 1. 사용자가 하는 일

사용자는 먼저 **목표(Mission)**만 명확하게 입력한다.

예:

```text
TCF Transaction Boundary를 실제 Source와 Runtime 기준으로 검증해줘.
```

또는:

```text
ServiceId 하나를 화면부터 SQL, Runtime Evidence까지 추적해줘.
```

## 2. Orchestrator가 하는 일

Orchestrator는 즉시 설계안을 만들지 않는다.

```text
Mission
  ↓
System Scope 판별
  ↓
Source/Runtime 가용성 확인
  ↓
복잡도 판단
  ↓
필요 Agent 선택
  ↓
실행 순서
  ↓
Gate
  ↓
Expected Deliverables
```

을 먼저 만든다.

## 3. Run 격리

모든 작업은 `runId`로 분리한다.

```text
ACL-RUN-YYYYMMDD-NNN
```

예:

```text
ACL-RUN-20260817-001
```

각 Run은 Source, Model, Test, Evidence, Gate 결과를 독립적으로 보관한다.

## 4. 첫 Run

첫 Run은 반드시 Vertical Slice다.

```text
대표 ServiceId
    ↓
Handler
    ↓
Facade
    ↓
Service
    ↓
DAO / Mapper / SQL
    ↓
Config / OM Policy
    ↓
Test
    ↓
Runtime
```

Runtime 환경이 없으면 G50은 `HOLD`로 기록하되 G00~G40 및 정적 Drift 분석까지 진행한다.

## 5. 결과

한 Run이 정상 완료되면 다음 질문에 답할 수 있어야 한다.

- 이 Architecture는 무엇을 약속했는가?
- 그 약속은 Model에 어떻게 표현됐는가?
- 실제 Code는 그 Model을 따르는가?
- Test는 이를 자동 검증하는가?
- Runtime은 실제로 그렇게 동작했는가?
- 차이가 있다면 어떤 Drift/GAP/ADR인가?
- 어떤 Baseline이 승인되었는가?
