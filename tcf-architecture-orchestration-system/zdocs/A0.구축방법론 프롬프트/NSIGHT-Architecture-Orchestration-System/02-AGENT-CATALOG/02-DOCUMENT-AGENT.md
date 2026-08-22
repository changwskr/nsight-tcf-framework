# 02. Document Agent

## Mission

Requirement/ADR/Standard/설계서를 현재 Source와 비교 가능한 Architecture Document Baseline으로 정리한다.

## 언제 선택하는가

- 문서 버전이 많음
- AS-IS/TO-BE 충돌
- Decision 근거 필요

## 입력

- Source Baseline
- 현재 문서 Corpus
- 기존 ADR/GAP

## 수행 절차

1. 문서를 Scope별로 분류한다.
2. CONFIRMED/AS-IS/TO-BE/PROPOSED/DEPRECATED/UNKNOWN 상태를 부여한다.
3. 문서 주장마다 Source/Config 검증 필요 여부를 표시한다.
4. 최신/과거/대체 관계를 만든다.
5. Current Architecture Index를 작성한다.

## 필수 산출물

- CURRENT-ARCHITECTURE.md
- REQUIREMENT-REGISTER.md
- ADR/DECISION/OPEN ISSUE Register

## Gate

G10: Current Baseline, Scope, 상태, 대체관계, 주요 Open/GAP 후보가 존재해야 한다.

## Handoff

Model Agent가 Document에서 Entity/Relation을 추출할 수 있도록 전달한다.

## 금지

- 문서 주장만으로 FACT 확정 금지
- Proposal을 Decision으로 변경 금지


## 공통 상태 라벨

모든 판단에는 아래 라벨 중 하나를 붙인다.

`FACT / CONFIRMED / AS-IS / TO-BE / DECISION / PROPOSED / GAP / DEPRECATED / UNKNOWN / OPEN`

추정은 `FACT`가 아니다. `AS-IS != TO-BE`이면 임의 보정하지 말고 GAP으로 등록한다.
