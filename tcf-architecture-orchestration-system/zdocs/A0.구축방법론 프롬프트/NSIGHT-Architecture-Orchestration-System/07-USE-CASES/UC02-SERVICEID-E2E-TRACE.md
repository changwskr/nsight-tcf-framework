# UC02 — ServiceId E2E Trace

## Mission
실제 Source에서 확인된 대표 ServiceId 1건을 화면/프로그램/SQL/Test/Runtime까지 추적한다.

## Team
Baseline → Source → Model → Test → Runtime → Drift

## 결과
Traceability Matrix 한 행이 최소 Handler~Table까지 채워지고, Runtime 가능 시 deploymentId/traceId/evidenceId까지 연결된다.
