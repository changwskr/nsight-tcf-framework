# Workspace 운영 규칙

1. 원본 입력자료는 수정하지 않고 단계별 `IN/`에 복사한다.
2. 분석에 사용한 근거는 `evidence/`에 저장하거나 위치를 기록한다.
3. 단계 산출물은 반드시 `OUT/`에 작성한다.
4. 단계 완료 시 `90-STATE/architecture-state.yaml`을 갱신한다.
5. Architecture Decision은 `06-A05-ADR-PRINCIPLES/OUT/ADR/`에 저장한다.
6. 미해결 Gap은 다음 단계로 숨기지 않고 Handoff한다.
7. `FACT`와 `PROPOSED`를 같은 문단에서 혼동하지 않는다.
8. 실제 Source와 Target Architecture가 다르면 Source를 자동으로 표준으로 승격하지 않는다.
9. 단계 Gate 실패 시 다음 Baseline 확정을 금지한다.
10. 모든 As-Built 문서는 Source/Configuration/Runtime Evidence와 대조한다.
