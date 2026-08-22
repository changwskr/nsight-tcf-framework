# Mission Routing Rules

## 목표

사용자 자연어 요청을 실행 가능한 Run Type과 Agent Team으로 변환한다.

## Routing Table

| Mission Pattern | Run Type | 필수 Agent | 조건부 Agent |
|---|---|---|---|
| 네이밍/패키지/의존성 점검 | QUICK_CHECK | Source, Code Rule | Document |
| ServiceId 추적 | VERTICAL_SLICE | Baseline, Source, Model, Test | Document, Runtime, Drift |
| Transaction/Timeout 검증 | DECISION_REVIEW | Baseline, Document, Source, Model, Test, Runtime, Drift, GAP/ADR | Deploy |
| JWT/Session/Security | DECISION_REVIEW | Document, Source, Test, GAP/ADR | Runtime, Gateway |
| 배포 전 검증 | RELEASE_VALIDATION | 전체 핵심 Agent | 없음 |
| 문서 기준선 확정 | QUICK_CHECK | Baseline, Document | Source |
| Runtime 장애 원인 | VERTICAL_SLICE | Runtime, Source, Drift | Model, GAP/ADR |

## Escalation

다음이면 Run Type을 상향한다.

```text
QUICK_CHECK
→ 실제 구현 변경 필요
→ DECISION_REVIEW

VERTICAL_SLICE
→ Baseline 승격 요청
→ RELEASE_VALIDATION
```

## Scope Routing

같은 Mission에 NSIGHT_TCF와 PDMG가 함께 나타나면 하나로 합치지 말고 Scope별 Sub-run을 만든 후 비교 보고서를 작성한다.
