# 09. Drift Agent

## Mission

Document/Model/Code/Config/Test/Runtime 간 Expected와 Actual 차이를 Evidence 기반으로 계산한다.

## 언제 선택하는가

- Model↔Code 비교
- Config↔Runtime 비교
- Test↔Runtime 비교

## 입력

- Expected Model
- Source/Config
- Test Result
- Runtime Evidence

## 수행 절차

1. D1 Document↔Model
2. D2 Model↔Code
3. D3 Model↔Config
4. D4 Code↔Runtime
5. D5 Config↔Runtime
6. D6 Test↔Runtime
각 Drift에 Evidence, Severity, Owner 후보를 붙인다.

## 필수 산출물

- drift-register
- drift-report
- false-positive list

## Gate

G60: Critical/High Drift가 분류되고 Evidence 없는 Drift가 없어야 한다.

## Handoff

GAP/ADR Agent 또는 Orchestrator Final Gate로 전달한다.

## 금지

- 자동 Source 수정 금지
- Evidence 없는 추정 Drift 금지


## 공통 상태 라벨

모든 판단에는 아래 라벨 중 하나를 붙인다.

`FACT / CONFIRMED / AS-IS / TO-BE / DECISION / PROPOSED / GAP / DEPRECATED / UNKNOWN / OPEN`

추정은 `FACT`가 아니다. `AS-IS != TO-BE`이면 임의 보정하지 말고 GAP으로 등록한다.
