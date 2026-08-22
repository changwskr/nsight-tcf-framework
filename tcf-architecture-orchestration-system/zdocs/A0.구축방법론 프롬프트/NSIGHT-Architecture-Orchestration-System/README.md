# NSIGHT Architecture Orchestration System

농협 상호금융 NSIGHT 정보계의 아키텍처 업무를 **목표 기반 Orchestration + Architecture Closed Loop**로 수행하기 위한 작업공간이다.

## 무엇을 만드는가

사용자는 복잡한 Harness 내부 구조 대신 **목표를 입력하고, 선택된 Agent 팀과 진행상태, Gate, Drift, 최종 Baseline을 확인**한다.

내부에서는 다음 Closed Loop가 실행된다.

```text
USER MISSION
    ↓
ORCHESTRATOR
    ↓
AGENT TEAM
    ↓
Document
    ↓
Model
    ↓
Code
    ↓
Test
    ↓
Runtime Evidence
    ↓
Drift
    ↓
GAP / ADR
    ↓
Human Approval
    ↓
New Architecture Baseline
    ↺
```

## 핵심 철학

Architecture의 완료 기준은 문서 작성이 아니다.

```text
설명 가능
+ 기계 판독 가능
+ 실제 Source 연결 가능
+ 자동 검증 가능
+ Runtime에서 증명 가능
+ Drift 탐지 가능
+ 의사결정/변경 이력 추적 가능
```

## 처음 시작하는 순서

1. `START-HERE.md`
2. `00-MASTER-PROMPT.md`
3. `01-ORCHESTRATOR/ORCHESTRATOR-PROMPT.md`
4. 사용자 Mission 입력
5. `RUN-MANIFEST` 생성
6. Pilot Scope 선정
7. Agent Team 차출
8. Gate를 따라 Closed Loop 실행

## 첫 Pilot 원칙

전체 Repository를 한 번에 모델링하지 않는다.

```text
NSIGHT_TCF
+ 대표 Business WAR 1개
+ 실제 Source에서 확인된 ServiceId 1~3개
+ 관련 Mapper / SQL / Config / OM
+ 가능한 Runtime Evidence
```

를 하나의 Vertical Slice로 끝까지 관통한 후 범위를 확장한다.

## 현재 프로젝트에서 이미 확인된 주의사항

- 문서/생성물/과거자료가 같은 저장소에 혼재되어 있다.
- `build/`, `bin/`, `.gradle/`, `logs/` 등은 Source of Truth에서 제외해야 한다.
- NSIGHT_TCF와 PDMG/PDMK/PDMP의 AS-IS를 같은 것으로 간주하면 안 된다.
- Root Gradle Baseline, Module 수, OM Handler 수, Transaction Boundary, Session 정책 등은 Drift/GAP 후보가 존재한다.
- Runtime Evidence가 없으면 최종 HG90은 PASS할 수 없다.

## 디렉터리 역할

| 디렉터리 | 역할 |
|---|---|
| `01-ORCHESTRATOR` | 목표 해석, 팀 구성, 실행계획 |
| `02-AGENT-CATALOG` | 전문 Agent 프롬프트 |
| `03-WORKSPACE` | 실행 Run의 단계별 작업영역 |
| `04-STAGE-PROMPTS` | Closed Loop 단계별 실행 프롬프트 |
| `05-GATE` | 단계별 진입/종료 Gate |
| `06-TEMPLATES` | 표준 산출물 템플릿 |
| `07-USE-CASES` | 대표 실행 시나리오 |
| `08-GOVERNANCE` | 기준선/증적/승인/예외 통제 |
| `09-USER-VIEWS` | 사용자 화면·업무 흐름 정의 |
| `99-REFERENCE` | 기존 연속성/Closed Loop 기준자료 |
