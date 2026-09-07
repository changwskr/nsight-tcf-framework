# Sprint 03 개발계획서
# Workout Vertical Slice

- 문서 버전: v0.1
- 기준일: 2026-09-01
- 상태: DEVELOPMENT BASELINE
- Sprint ID: `SPRINT-03`
- 선행 Sprint: `SPRINT-01`, `SPRINT-02`
- 핵심 원칙: 운동 정량 계산은 Algorithm, 정책판단은 Rule, 설명은 이후 AI

---

# 1. Sprint 목표

사용자 Goal과 Body/Recovery 데이터를 기반으로 Workout Program을 관리하고,
실제 운동 수행기록을 Set 단위로 축적하며,
Volume·Compliance·Performance Trend를 계산해
Workout Adjustment Candidate를 생성한다.

```text
Workout Program
      ↓
Workout Session / Log
      ↓
Volume
      ↓
Compliance
      ↓
Performance Trend
      ↓
Adjustment Candidate
```

---

# 2. Sprint 범위

| Program ID | 기능 | Service |
|---|---|---|
| `mgbyw1000` | Workout Program | `S0`, `C0`, `U0`, `D0` |
| `mgbyw1100` | Workout Session / Log | `S0`, `C0`, `U0` |
| `mgbyw1200` | Workout Analytics | `S0`, `R0` |
| `mgbyw1300` | Workout Adjustment | `S0`, `A0` |

Program 번호는 Registry 초안 기준이며 최종 승인 전까지 PROPOSED이다.

---

# 3. 데이터 모델

## WorkoutProgram

```text
programId
userId
goal
startDate
endDate
status
createdBy
createdAt
updatedAt
```

## WorkoutDay

```text
programDayId
programId
weekNumber
dayNumber
targetBodyPart
```

## WorkoutExercise

```text
exerciseId
programDayId
exerciseMasterId
sequence
targetSets
targetReps
targetWeight
targetRpe
restSeconds
```

## WorkoutSession

```text
sessionId
userId
programDayId
startedAt
endedAt
status
```

## WorkoutSet

```text
setId
sessionId
exerciseId
sequence
weight
reps
rpe
rir
completed
performedAt
```

---

# 4. Workout Algorithm

## Set Volume

```text
Set Volume = Weight × Reps
```

## Session Volume

```text
Session Volume = Σ Set Volume
```

## Weekly Volume

```text
Weekly Volume = Σ Session Volume
```

추가 분석 후보:

```text
Frequency
Intensity
PR
Compliance
RPE Trend
RIR Trend
Performance Trend
```

---

# 5. Workout Adjustment Rule

입력 후보:

```text
Performance
RPE/RIR
Volume Change
Sleep
Fatigue
Soreness
Compliance
Goal
```

출력 후보:

```text
KEEP
INCREASE_LOAD
DECREASE_LOAD
INCREASE_VOLUME
DECREASE_VOLUME
DELOAD
REST
EXERCISE_CHANGE
```

정확한 Threshold는 Algorithm/Rule Specification 승인 후 확정한다.

---

# 6. Rule / Algorithm Components

```text
WorkoutVolumeCalculator
WorkoutComplianceCalculator
WorkoutPerformanceCalculator
WorkoutTrendCalculator
WorkoutAdjustmentRule
PlateauDetectionRule
```

패키지:

```text
nhnis.mg.by.w.application.rule
```

공통/Rule Type은 PascalCase를 사용한다.

---

# 7. Backend Package

```text
nhnis.mg.by.w
│
├─ entry
│  └─ handler
│     ├─ mgbyw1000Handler
│     ├─ mgbyw1100Handler
│     ├─ mgbyw1200Handler
│     └─ mgbyw1300Handler
│
├─ application
│  ├─ facade
│  │  ├─ mgbyw1000Facade
│  │  ├─ mgbyw1100Facade
│  │  ├─ mgbyw1200Facade
│  │  └─ mgbyw1300Facade
│  ├─ service
│  │  ├─ mgbyw1000Service
│  │  ├─ mgbyw1100Service
│  │  ├─ mgbyw1200Service
│  │  └─ mgbyw1300Service
│  └─ rule
│     ├─ WorkoutVolumeCalculator
│     ├─ WorkoutComplianceCalculator
│     ├─ WorkoutPerformanceCalculator
│     ├─ WorkoutTrendCalculator
│     ├─ WorkoutAdjustmentRule
│     └─ PlateauDetectionRule
│
├─ dto
└─ persistence
   └─ dao
      ├─ mgbyw1000DAO
      ├─ mgbyw1100DAO
      ├─ mgbyw1200DAO
      └─ mgbyw1300DAO
```

---

# 8. Mapper

```text
mapper/rdw/mg/by/w/
├─ mgbyw1000-ORA.xml
├─ mgbyw1100-ORA.xml
├─ mgbyw1200-ORA.xml
└─ mgbyw1300-ORA.xml
```

예:

```text
mgbyw1100C0_C0
mgbyw1100U0_U0
mgbyw1200S0_S0
mgbyw1200R0_S0
mgbyw1300A0_S0
```

실제 Statement 구성은 구현 시 확정하며
DAO Method와 XML ID는 완전 일치해야 한다.

---

# 9. UI

```text
static/
├─ mgbyw1000/   Workout Program
├─ mgbyw1100/   Workout Log
├─ mgbyw1200/   Workout Analytics
└─ mgbyw1300/   Workout Adjustment
```

사용자 Journey:

```text
Today Workout
    ↓
Exercise
    ↓
Set 입력
    ↓
Weight / Reps / RPE / RIR
    ↓
Session Complete
    ↓
Analytics
    ↓
Adjustment Candidate
```

---

# 10. End-to-End 흐름

## 운동 Session 등록

```text
mgbyw1100C0
    ↓
mgbyw1100Handler
    ↓
mgbyw1100Facade
    ↓
mgbyw1100Service
    ↓
Workout Set Validation
    ↓
mgbyw1100DAO
    ↓
mgbyw1100-ORA.xml
    ↓
WorkoutSession / WorkoutSet
```

## Analytics

```text
Workout Raw Data
    ↓
WorkoutVolumeCalculator
    ↓
WorkoutComplianceCalculator
    ↓
WorkoutPerformanceCalculator
    ↓
WorkoutTrendCalculator
    ↓
Workout Analytics
```

## Adjustment

```text
Workout Analytics
   +
Sprint 02 Readiness
   +
Goal
   ↓
WorkoutAdjustmentRule
   ↓
Adjustment Candidate
```

---

# 11. Plateau Detection

정체 판단은 단일 Session이 아니라 Window 기반으로 한다.

후보 신호:

```text
Load 증가 없음
Reps 개선 없음
Volume 증가 대비 Performance 정체
높은 RPE 지속
충분한 Compliance 상태에서 성과 정체
```

정체 기간과 최소 표본 수는 TBD이다.

---

# 12. Explainable Evidence

Sprint 03에서는 AI 호출 전 구조화 Evidence까지 만든다.

예:

```json
{
  "type": "RECOVERY_RISK",
  "signals": [
    {"metric": "sleepAvg5d", "changePct": -17},
    {"metric": "lowerBodyVolume7d", "changePct": 23}
  ],
  "candidateAction": "DECREASE_VOLUME",
  "suggestedRangePct": "10-15"
}
```

숫자는 Algorithm/Rule이 생성한다.
AI가 나중에 설명만 수행한다.

---

# 13. 개발 Task

## 설계

- [ ] Exercise Master 범위
- [ ] Workout Program 구조
- [ ] Set/Session 상태값
- [ ] RPE/RIR Validation
- [ ] Volume 공식 승인
- [ ] Compliance 공식 승인
- [ ] Performance Trend Window
- [ ] Plateau Window
- [ ] Adjustment Threshold

## Backend

- [ ] `mgbyw1000`
- [ ] `mgbyw1100`
- [ ] `mgbyw1200`
- [ ] `mgbyw1300`
- [ ] Calculator 구현
- [ ] Adjustment Rule 구현
- [ ] Plateau Detection Rule 구현
- [ ] DAO/Mapper 구현

## UI

- [ ] Workout Program 조회
- [ ] Today Workout
- [ ] Set 빠른 입력
- [ ] Session 완료
- [ ] Volume / Compliance 표시
- [ ] Adjustment Candidate 표시

---

# 14. 테스트

## Unit

- Volume 계산
- Session/Weekly 집계
- Compliance
- RPE/RIR 경계값
- Performance Trend
- Adjustment Rule
- Plateau Detection

## Integration

```text
Workout UI
→ Service ID
→ Handler
→ Facade
→ Service
→ Rule
→ DAO
→ Mapper
→ DB
```

## Regression

Sprint 01 / 02의 다음 기능이 깨지지 않아야 한다.

```text
Profile
Goal
Body Measurement
Body Timeline
Daily Check-in
Recovery
Readiness
Body Status
```

---

# 15. 완료 기준

```text
Workout Program
→ Workout Log
→ Volume
→ Compliance
→ Performance Trend
→ Adjustment Candidate
```

- [ ] Set 단위 Raw Data 보존
- [ ] Algorithm 재현 가능
- [ ] Rule 독립 테스트 통과
- [ ] Readiness 연계
- [ ] Goal 연계
- [ ] E2E Integration 통과
- [ ] Traceability Registry 갱신

---

# 16. 다음 Sprint 연계

Sprint 03의 Workout Raw/Derived Data는 이후 Nutrition 및 AI Coaching의 핵심 Context가 된다.

```text
Body
+
Goal
+
Recovery
+
Workout
    ↓
Nutrition
    ↓
Coaching / AI
```
