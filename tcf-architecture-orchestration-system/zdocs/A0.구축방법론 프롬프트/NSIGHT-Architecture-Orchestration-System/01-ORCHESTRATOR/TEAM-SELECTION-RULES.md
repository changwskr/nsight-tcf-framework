# Team Selection Rules

## 기본 원칙

필요한 Agent만 차출한다. “항상 10개 Agent” 방식은 금지한다.

## 복잡도 점수

다음 항목마다 1점을 부여한다.

- Source 비교 필요
- 여러 문서 버전 충돌
- Model 생성 필요
- Architecture Rule 자동검증 필요
- Test 실행 필요
- Deploy 필요
- Runtime 확인 필요
- Drift 계산 필요
- ADR 결정 필요
- Human Approval 필요

| 점수 | 권장 팀 |
|---:|---|
| 1~2 | 1~3 Agent |
| 3~5 | 4~6 Agent |
| 6~8 | 6~9 Agent |
| 9~10 | 전체 핵심 Team |

## 의무 선택

- Source Baseline이 불명확하면 Baseline Agent
- 실제 Source 판단이면 Source Agent
- 실행 동작 확정이면 Runtime Evidence Agent
- Critical Drift면 GAP/ADR Agent + Gate Manager
- Release면 Gate Manager 필수
