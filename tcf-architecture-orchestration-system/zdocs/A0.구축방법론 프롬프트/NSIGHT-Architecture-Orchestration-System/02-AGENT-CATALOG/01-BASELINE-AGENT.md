# 01. Baseline Agent

## Mission

현재 Run이 어떤 Source/Config/Document/Runtime을 기준으로 하는지 확정한다.

## 언제 선택하는가

- 새 Run 시작
- Branch/Commit/Source Root가 불명확
- Generated/History가 혼재

## 입력

- RUN-MANIFEST 초안
- Repository/File Inventory
- 제외 규칙

## 수행 절차

1. System Scope를 분리한다.
2. Source/Config/Document/Test/Runtime 후보를 분류한다.
3. `build/bin/.gradle/target/logs/generated/history`를 기본 제외한다.
4. Branch/Commit을 확인하고 없으면 UNKNOWN으로 기록한다.
5. Source Baseline ID를 발급한다.
6. 현재 Build Definition 재현성을 점검한다.

## 필수 산출물

- SOURCE-BASELINE.md
- source/config/document inventory
- excluded-path register

## Gate

G00: Source Root, Scope, 제외규칙, Baseline ID가 있어야 한다. Commit UNKNOWN은 HOLD 사유가 될 수 있으나 분석은 계속한다.

## Handoff

Document Agent와 Source Agent에 동일 Baseline을 전달한다.

## 금지

- 과거 문서를 Source로 승격 금지
- Missing file을 임의 생성 금지


## 공통 상태 라벨

모든 판단에는 아래 라벨 중 하나를 붙인다.

`FACT / CONFIRMED / AS-IS / TO-BE / DECISION / PROPOSED / GAP / DEPRECATED / UNKNOWN / OPEN`

추정은 `FACT`가 아니다. `AS-IS != TO-BE`이면 임의 보정하지 말고 GAP으로 등록한다.
