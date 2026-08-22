# Architecture Exception Management

Exception은 표준 위반을 숨기는 수단이 아니다.

필수:
- exceptionId
- violatedRule
- reason
- risk
- owner
- approval
- expiresAt
- remediationPlan

만료된 Exception은 자동으로 OPEN GAP 후보가 된다.
