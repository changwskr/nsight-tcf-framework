# PILOT 01 — First Architecture Orchestration Run

## 목적
전체 저장소를 한 번에 처리하기 전에 Architecture Orchestration System이 실제로 Closed Loop를 완주할 수 있는지 검증한다.

## Scope
- `NSIGHT_TCF`
- 대표 Business Service 1개
- 실제 Source에서 확인된 ServiceId 1~3개

## Stage
1. Run Manifest
2. G00 Source Baseline
3. Document/Source 병렬 분석
4. ServiceId Architecture Model
5. Architecture Rule/Test
6. Runtime 가능한 경우 Deploy/Evidence
7. Drift
8. GAP/ADR
9. Human Approval
10. HG90 또는 명확한 HOLD

## Pilot 성공 기준
Runtime이 가능한 환경:
- ServiceId 1건 이상 E2E Trace
- Architecture Test Evidence
- Runtime Evidence Chain
- Drift 판정
- Gate 결과

Runtime이 없는 환경:
- G00~G40 완료
- Runtime Missing Evidence 명확
- G50/HG90 HOLD
- Runtime 재개 조건 명시
