# Run Manifest Template

```yaml
runId: ACL-RUN-YYYYMMDD-NNN
missionId: MISSION-YYYYMMDD-NNN
runType: VERTICAL_SLICE

mission:
  title: "TCF Transaction Boundary 검증"
  successCriteria:
    - "Model과 실제 Source의 Transaction Boundary 차이를 식별한다"
    - "Runtime Evidence가 가능하면 실제 TX Owner를 증명한다"

systemScope:
  - NSIGHT_TCF
  - BUSINESS_SERVICE

pilot:
  strategy: SERVICEID_VERTICAL_SLICE
  selectionRule: "실제 Source에서 확인된 ServiceId 중 대표 1~3건"
  maxServiceIds: 3

source:
  baselineId: ARCH-SOURCE-YYYYMMDD-NNN
  root: "repository-root"
  branch: "UNKNOWN이면 UNKNOWN 유지"
  commit: "UNKNOWN이면 UNKNOWN 유지"

runtime:
  available: false
  environment: LOCAL

architecture:
  baselineId: ARCH-YYYYMMDD-NNN
  modelVersion: MODEL-0.1.0

startedAt: YYYY-MM-DD
```
