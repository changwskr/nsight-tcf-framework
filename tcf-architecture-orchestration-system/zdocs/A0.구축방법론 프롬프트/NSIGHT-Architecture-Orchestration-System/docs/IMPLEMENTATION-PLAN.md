# NSIGHT Architecture Orchestration System Implementation Plan

> **For agentic workers:** 각 작업은 독립 검토 가능한 산출물을 만든다.

**Goal:** Orchestrator 기반 Agent Team 운영모델과 Architecture Closed Loop를 하나의 재사용 가능한 Markdown 작업공간으로 구성한다.

**Architecture:** 상위 Orchestrator가 Mission을 정규화하고 전문 Agent를 차출한다. 각 Agent는 단계별 Workspace에 Artifact/Evidence를 생성하며 Gate가 다음 Stage 진입을 통제한다.

**Tech Stack:** Markdown Prompt, YAML/JSON 개념모델, Java/Spring/Gradle/MyBatis/Runtime Evidence 연계 기준.

## Tasks

- [x] Task 1: Master/Quick Start/Rules 구성
- [x] Task 2: Orchestrator Prompt와 Mission Routing 구성
- [x] Task 3: 전문 Agent Catalog 구성
- [x] Task 4: Stage Workspace 정의
- [x] Task 5: Closed Loop 단계별 Prompt 구성
- [x] Task 6: Gate 규칙 구성
- [x] Task 7: 표준 산출물 Template 구성
- [x] Task 8: 대표 Use Case 구성
- [x] Task 9: Governance 규칙 구성
- [x] Task 10: 7개 사용자 View 구성
- [x] Task 11: 현재 NSIGHT Source Map/GAP Seed 반영
- [x] Task 12: Markdown 구조 검증 및 ZIP 패키징

## Verification

- 모든 Markdown 파일 UTF-8
- 빈 문서 없음
- 코드 Fence 짝수 검증
- 상대 Markdown 링크 무결성 검사
- 동일 파일명 충돌 없음
- ZIP 재열기 검사
