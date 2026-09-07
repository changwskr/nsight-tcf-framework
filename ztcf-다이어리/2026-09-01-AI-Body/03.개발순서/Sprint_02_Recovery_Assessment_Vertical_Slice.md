# Sprint 02 개발계획서
# Recovery / Assessment Vertical Slice

- 문서 버전: v0.1
- 기준일: 2026-09-01
- 상태: DEVELOPMENT BASELINE
- Sprint ID: `SPRINT-02`
- 선행 Sprint: `SPRINT-01`
- 핵심 원칙: Algorithm / Rule 테스트를 AI보다 먼저 작성

---

# 1. Sprint 목표

매일 최소 입력으로 사용자의 수면·피로·스트레스·근육통 등 회복 상태를 수집하고,
결정론적 Algorithm/Rule을 통해 Readiness와 Body Status를 산출한다.

```text
Daily Check-in
      ↓
Recovery
      ↓
Readiness
      ↓
Body Status
```

Readiness Score는 의료지표가 아니라 Fitness Coaching Indicator로 취급한다.

---

# 2. Sprint 범위

## Recovery

| Program ID | 기능 | Service |
|---|---|---|
| `mgbyr1000` | Daily Check-in | `S0`, `C0` |
| `mgbyr1100` | Sleep / Recovery | `S0`, `C0` |
| `mgbyr1200` | Readiness Trend | `S0`, `R0` |

## Assessment

| Program ID | 기능 | Service |
|---|---|---|
| `mgbya1000` | Initial Assessment | `S0`, `A0` |
| `mgbya1100` | Body Status | `S0`, `A0` |
| `mgbya1200` | Readiness Assessment | `S0`, `A0` |

Program 번호는 Registry 초안 기준이며 최종 승인 전까지 PROPOSED이다.

---

# 3. 핵심 요구사항

Daily Check-in 최소 입력:

```text
Weight
Sleep Duration
Sleep Satisfaction
Fatigue
Stress
Soreness
Condition
Note
```

목표 UX:

```text
30초 ~ 1분
```

---

# 4. 데이터 모델

## DailyCheckIn

```text
checkInId
userId
checkInDate
sleepDuration
sleepSatisfaction
fatigue
stress
soreness
condition
note
createdAt
```

## Recovery

```text
recoveryId
userId
date
sleepMinutes
sleepQuality
fatigueScore
stressScore
moodScore
sorenessScore
restingHeartRate
hrv
source
createdAt
```

## BodyStatus

```text
bodyStatusId
userId
baseDate
bodyCompositionScore
trainingScore
nutritionScore
recoveryScore
sleepScore
readinessScore
overallScore
algorithmVersion
createdAt
```

정확한 물리 Table/Column은 TBD이다.

---

# 5. Algorithm / Rule 경계

AI가 Readiness 점수를 임의 생성하지 않는다.

```text
Raw Data
  ↓
Calculator
  ↓
Normalized Metric
  ↓
Rule
  ↓
Readiness Score
  ↓
Recovery State
  ↓
Training Caution Level
```

입력 후보:

```text
Sleep Duration
Sleep Satisfaction
Fatigue
Soreness
Stress
Recent Training Load
Recent Performance
```

출력 후보:

```text
Readiness Score
Recovery State
Training Caution Level
```

정확한 가중치와 Threshold는 별도 Algorithm Specification에서 확정한다.

---

# 6. Rule / Algorithm Component

PascalCase 공통/업무 Rule 예:

```text
ReadinessCalculator
RecoveryStateRule
TrainingCautionRule
BodyStatusCalculator
```

권장 패키지:

```text
nhnis.mg.by.r.application.rule
nhnis.mg.by.a.application.rule
```

Algorithm은 독립 단위 테스트 가능해야 한다.

---

# 7. Backend 구조

예: Daily Check-in

```text
nhnis.mg.by.r
│
├─ entry/handler
│  └─ mgbyr1000Handler
├─ application/facade
│  └─ mgbyr1000Facade
├─ application/service
│  └─ mgbyr1000Service
├─ dto
│  └─ mgbyr1000*DTO*
└─ persistence/dao
   └─ mgbyr1000DAO
```

Assessment:

```text
nhnis.mg.by.a
│
├─ entry/handler
├─ application/facade
├─ application/service
├─ application/rule
├─ dto
└─ persistence/dao
```

---

# 8. UI

```text
static/
├─ mgbyr1000/   Daily Check-in
├─ mgbyr1100/   Recovery
├─ mgbyr1200/   Readiness Trend
├─ mgbya1000/   Initial Assessment
├─ mgbya1100/   Body Status
└─ mgbya1200/   Readiness Assessment
```

핵심 화면 우선순위:

```text
1. Daily Check-in
2. Today Recovery
3. Readiness
4. Body Status
```

---

# 9. End-to-End 흐름

```text
Daily Check-in
mgbyr1000C0
    ↓
Handler
    ↓
Facade
    ↓
Service
    ↓
DAO
    ↓
DB
    ↓
ReadinessCalculator
    ↓
RecoveryStateRule
    ↓
BodyStatus
    ↓
Body Timeline
```

---

# 10. 개발 Task

## 설계

- [ ] Recovery DTO 확정
- [ ] Check-in 입력값 범위 확정
- [ ] Score Scale 확정
- [ ] Readiness 공식 확정
- [ ] Weight/Threshold 확정
- [ ] Algorithm Version 정책
- [ ] BodyStatus Snapshot 정책

## Backend

- [ ] `mgbyr1000`
- [ ] `mgbyr1100`
- [ ] `mgbyr1200`
- [ ] `mgbya1000`
- [ ] `mgbya1100`
- [ ] `mgbya1200`
- [ ] Calculator 구현
- [ ] Rule 구현
- [ ] DAO/Mapper 작성

## UI

- [ ] 30초~1분 Check-in UX
- [ ] Recovery 상태 표시
- [ ] Readiness 표시
- [ ] 위험/주의 Level 표시
- [ ] 의료적 진단 표현 금지

---

# 11. 테스트 우선순위

Algorithm Test를 먼저 작성한다.

```text
Given
Known Inputs

When
ReadinessCalculator 실행

Then
Deterministic Result
```

필수 Test:

- 정상 수면/피로
- 수면 부족
- 높은 피로
- 높은 근육통
- Stress 증가
- Null/누락 데이터
- 경계값
- Version별 재현성

AI Evaluation Test는 Sprint 02 범위에 포함하지 않는다.

---

# 12. Safety

Readiness는 다음과 같이 표현한다.

```text
Fitness Coaching Indicator
```

금지:

```text
질병 진단
의학적 위험 확정
치료 지시
의약품 관련 지시
```

Fitness Scope를 벗어나면 전문가 상담 안내를 사용한다.

---

# 13. 완료 기준

```text
Check-in
→ Recovery
→ Algorithm
→ Rule
→ Readiness
→ Body Status
→ Timeline
```

- [ ] Algorithm Unit Test 통과
- [ ] Handler→DB Integration 통과
- [ ] Body Status 재현 가능
- [ ] Algorithm Version 기록 가능
- [ ] Member 권한 검증
- [ ] 의료적 표현 미사용
- [ ] Traceability Registry 반영

---

# 14. 다음 Sprint 연계

Sprint 02의 Readiness/Recovery 결과는 Sprint 03 Workout Adjustment의 입력이 된다.

```text
Sprint 01
Body / Goal
    +
Sprint 02
Recovery / Readiness
    ↓
Sprint 03
Workout Program / Log / Adjustment
```
