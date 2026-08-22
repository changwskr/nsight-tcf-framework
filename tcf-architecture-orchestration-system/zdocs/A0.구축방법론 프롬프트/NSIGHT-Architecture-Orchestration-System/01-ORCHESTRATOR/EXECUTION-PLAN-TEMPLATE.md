# Execution Plan Template

## Mission

- missionId:
- title:
- successCriteria:

## Run

- runId:
- runType:
- systemScope:
- sourceBaselineId:
- architectureBaselineId:
- runtimeAvailable:

## Selected Team

| Order | Agent | Selection Reason | Depends On | Output | Gate |
|---:|---|---|---|---|---|

## Parallel Groups

병렬 실행 가능한 Agent와 합류 지점을 명시한다.

## Expected Evidence

| Evidence | Collector | Mandatory |
|---|---|---|

## Stop Conditions

- REJECT 발생
- Source Scope 잘못 지정
- Critical Security Evidence 훼손
- 사용자가 실행 중단 요청

HOLD는 분석 중단과 동일하지 않다. 가능한 정적 분석을 계속한다.
