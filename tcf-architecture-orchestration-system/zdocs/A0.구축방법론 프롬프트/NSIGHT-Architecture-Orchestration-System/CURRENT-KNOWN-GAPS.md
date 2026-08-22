# Current Known GAP / Drift Seed Register

이 파일은 Orchestrator 첫 Run에서 사실 검증할 초기 후보 목록이다. 검증 전에는 확정 GAP가 아니라 `DRIFT-CANDIDATE`다.

| ID | 후보 | 심각도 | 검증 Agent |
|---|---|---|---|
| DRIFT-CANDIDATE-001 | OM Handler 문서 수와 실제 Source 수 불일치 | MEDIUM | Document/Source |
| DRIFT-CANDIDATE-002 | 22/24 Module 기준 불일치 | HIGH | Baseline/Source |
| DRIFT-CANDIDATE-003 | Root Gradle Baseline 불명확 | CRITICAL | Baseline |
| DRIFT-CANDIDATE-004 | TransactionTemplate와 `@Transactional` 역할 차이 | HIGH | Source/Runtime |
| DRIFT-CANDIDATE-005 | DeltaManager와 Spring Session JDBC 기준 혼재 | HIGH | Document/ADR |
| DRIFT-CANDIDATE-006 | Gate Rule 선언과 Evaluator 구현 차이 | CRITICAL | Test/Gate |
| DRIFT-CANDIDATE-007 | Runtime Evidence 없이 HG90 통과 가능성 | CRITICAL | Gate |
| DRIFT-CANDIDATE-008 | JWT/Gateway/OM/EAI 등 테스트 부족 | HIGH | Test |
| DRIFT-CANDIDATE-009 | Sensitive Request/Response Logging | CRITICAL | Source/Security |
| DRIFT-CANDIDATE-010 | Generated 문서를 SoT로 오인할 위험 | HIGH | Baseline/Document |

판정 결과는 `FALSE_POSITIVE / CONFIRMED_GAP / ACCEPTED_EXCEPTION / UNKNOWN` 중 하나로 기록한다.
