# UC05 — Runtime Drift Detection

## Mission
Model의 Timeout/TX/Pool/Thread 정책과 실제 Runtime 값을 비교한다.

## Team
Model → Deploy → Runtime → Drift → GAP/ADR

## 예
Model Timeout 3s vs Runtime 5s.
Config Hikari max 120 vs Runtime max 80.

Evidence 없는 차이는 Drift로 확정하지 않는다.
