# AI 기반 개인 신체상태·운동·식단 코칭 플랫폼
# 개발 설계서

**문서 버전:** v1.0  
**문서 목적:** 개발 아키텍처 및 상세 구현 기준 정의  
**대상 시스템:** Personal Body AI Coaching Platform

---

# 1. 개발 목표

본 시스템은 사용자의 신체정보, 운동기록, 식단정보, 수면, 피로도 및 생활 데이터를 수집하여 사용자의 현재 상태를 분석하고 개인별 운동·식단·회복 계획을 제공하는 AI 기반 개인 코칭 플랫폼을 구축하는 것을 목적으로 한다.

핵심 처리 Cycle은 다음과 같다.

```text
신체정보 등록
      ↓
생활/운동/식단 데이터 수집
      ↓
Personal Body Model 생성
      ↓
현재 상태 Assessment
      ↓
운동계획 + 영양계획
      ↓
Daily Execution
      ↓
운동/식단/수면/피로 기록
      ↓
Rule + Algorithm + AI 분석
      ↓
Daily Coaching
      ↓
Weekly Review
      ↓
Program Adjustment
      ↓
Body Timeline
      ↓
개인화 정밀도 지속 향상
```

---

# 2. 핵심 개발 원칙

## 2.1 Data First

AI가 먼저가 아니라 정확한 개인 데이터 축적이 우선이다.

```text
Data
 ↓
Analysis
 ↓
Recommendation
 ↓
AI Explanation
```

AI가 모든 계산을 임의로 수행하도록 하지 않는다.

---

## 2.2 Algorithm + AI Hybrid

칼로리 계산, 운동량 계산, 체중 변화량 등 정량 영역은 프로그램 Algorithm과 Rule Engine이 처리한다.

LLM은 다음 영역에 집중한다.

- 데이터 해석
- 설명
- 코칭 메시지
- 패턴 요약
- 자연어 질의응답
- 대안 제시

```text
              Personal Data
                    │
       ┌────────────┼────────────┐
       ▼            ▼            ▼
 Algorithm      Rule Engine    AI/LLM
       │            │            │
       └────────────┼────────────┘
                    ▼
             Coaching Engine
                    │
                    ▼
                 User
```

---

## 2.3 Explainable Coaching

모든 중요한 AI 추천에는 최소 다음을 포함한다.

```text
Recommendation
+
Reason
+
Evidence
+
Expected Effect
+
Caution
```

예:

```text
추천
오늘 하체 운동 Volume을 15% 낮추십시오.

근거
최근 4일 평균 수면시간이 평소 대비 18% 감소했습니다.

추가 근거
지난 7일간 하체 Training Volume이 이전 주 대비 22% 증가했습니다.

목적
누적 피로를 관리하면서 다음 훈련의 수행능력을 유지하기 위함입니다.
```

---

## 2.4 Human in the Loop

전문 Coach가 관리하는 회원은 중요 변경사항에 대해 Coach Review를 지원한다.

```text
AI Recommendation
        ↓
Coach Review
   ┌────┼────┐
   ↓    ↓    ↓
Approve Modify Reject
        ↓
      Member
```

---

## 2.5 Fitness Boundary

플랫폼은 다음을 제공한다.

```text
Fitness
Exercise
Nutrition
Recovery
Body Composition
Lifestyle Coaching
```

다음은 제공하지 않는다.

```text
질병 진단
의약품 처방
의약품 중단 지시
의료 치료
```

---

# 3. 전체 시스템 아키텍처

```text
┌────────────────────────────────────────────────────────────┐
│                       CLIENT AREA                          │
│                                                            │
│   Web / PWA          Mobile App        Coach Console       │
└──────────┬────────────────┬────────────────┬───────────────┘
           │                │                │
           └────────────────┼────────────────┘
                            │ HTTPS / JSON
                            ▼
┌────────────────────────────────────────────────────────────┐
│                       API LAYER                            │
│                                                            │
│ Authentication / Authorization / Validation / Rate Limit  │
└───────────────────────────┬────────────────────────────────┘
                            │
                            ▼
┌────────────────────────────────────────────────────────────┐
│                APPLICATION PLATFORM                       │
│                                                            │
│ User / Body / Workout / Nutrition / Recovery / Coaching   │
│ Progress / Coach / Knowledge / Notification / Admin       │
└───────────────────────────┬────────────────────────────────┘
                            │
             ┌──────────────┼─────────────────┐
             ▼              ▼                 ▼
       Rule Engine     Analytics Engine    AI Engine
             │              │                 │
             └──────────────┼─────────────────┘
                            ▼
                    Coaching Engine
                            │
                            ▼
┌────────────────────────────────────────────────────────────┐
│                       DATA LAYER                           │
│                                                            │
│ PostgreSQL       Redis       Object Storage      Vector DB │
└────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌────────────────────────────────────────────────────────────┐
│                    EXTERNAL SYSTEM                         │
│                                                            │
│ Health Connect / Apple Health / Wearables / LLM Provider  │
│ Push / SMS / E-mail / Payment                             │
└────────────────────────────────────────────────────────────┘
```

---

# 4. 권장 개발 아키텍처

초기에는 Microservice보다 **Modular Monolith**를 적용한다.

이유:

- 초기 개발 속도
- 운영 복잡도 감소
- Transaction 관리 용이
- 개발인력 규모 대응
- MVP 변경 대응 용이

다만 Module Boundary는 Microservice 전환이 가능하도록 명확하게 구분한다.

```text
body-platform
│
├── auth
├── user
├── body
├── assessment
├── workout
├── nutrition
├── recovery
├── coaching
├── progress
├── coach
├── knowledge
├── notification
├── ai
├── admin
└── common
```

---

# 5. 권장 기술 구성

## Frontend

```text
Next.js / React
TypeScript
Responsive Web
PWA
```

초기에는 별도 iOS/Android Native App보다 PWA를 우선한다.

향후 필요 시 React Native 또는 Native App으로 확장한다.

---

## Backend

```text
Java
Spring Boot
Spring Security
JPA / Query Layer
REST API
Batch / Scheduler
```

---

## Database

주 데이터:

```text
PostgreSQL
```

Cache / Session:

```text
Redis
```

사진 및 파일:

```text
S3 Compatible Object Storage
```

AI Knowledge 검색:

```text
Vector Store
```

---

# 6. Domain Module 설계

## 6.1 USER

책임:

- 회원
- 로그인
- 개인정보
- 역할
- 동의
- 목표

대표 Entity:

```text
User
UserProfile
UserConsent
UserGoal
UserRole
```

---

# 7. BODY Module

사용자의 신체 데이터를 관리한다.

```text
BodyMeasurement

userId
measuredAt
height
weight
bodyFatPercentage
skeletalMuscleMass
waist
chest
arm
thigh
source
```

Body Measurement는 Update 방식보다 **시계열 Append 방식**을 기본으로 한다.

```text
09/01  82.4kg
09/02  82.2kg
09/03  81.9kg
...
```

과거 데이터를 덮어쓰지 않는다.

---

# 8. Body Photo 설계

```text
BodyPhoto

photoId
userId
capturedAt
viewType
storageUrl
metadata
```

viewType:

```text
FRONT
SIDE
BACK
OTHER
```

사진은 DB 자체에 저장하지 않고 Object Storage에 저장한다.

---

# 9. Goal Module

```text
UserGoal

goalId
userId
goalType
targetWeight
targetBodyFat
targetDate
priority
status
```

goalType:

```text
FAT_LOSS
MUSCLE_GAIN
WEIGHT_GAIN
STRENGTH
BODY_RECOMPOSITION
FITNESS
CONTEST_PREP
```

---

# 10. Assessment Engine

사용자의 초기 상태와 지속 상태를 평가한다.

```text
                User Profile
                     +
                Body Data
                     +
               Training Data
                     +
              Nutrition Data
                     +
               Recovery Data
                     ↓
              Assessment Engine
                     ↓
              Body Status Model
```

대표 출력:

```text
BodyStatus

bodyCompositionScore
trainingScore
nutritionScore
recoveryScore
sleepScore
readinessScore
overallScore
```

Score는 AI가 임의 생성하지 않고 명시된 Rule/Algorithm에 따라 계산한다.

---

# 11. Personal Body Model

플랫폼의 중심 Domain이다.

```text
PersonalBodyModel
│
├── Physical State
├── Body Composition
├── Training Capability
├── Training History
├── Nutrition State
├── Recovery State
├── Lifestyle
├── Goals
└── Historical Trends
```

개념적으로 다음과 같다.

```text
                PERSONAL BODY MODEL

                    Current Body
                         │
          ┌──────────────┼──────────────┐
          │              │              │
       Training       Nutrition      Recovery
          │              │              │
          └──────────────┼──────────────┘
                         │
                    Lifestyle
                         │
                         ▼
                   Goal Context
                         │
                         ▼
                 Historical Trend
```

---

# 12. WORKOUT Module

## WorkoutProgram

```text
WorkoutProgram

programId
userId
goal
startDate
endDate
status
createdBy
```

---

## WorkoutDay

```text
WorkoutDay

programDayId
programId
weekNumber
dayNumber
targetBodyPart
```

---

## WorkoutExercise

```text
WorkoutExercise

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

---

# 13. 운동 수행 기록

```text
WorkoutSession

sessionId
userId
programDayId
startedAt
endedAt
status
```

하위 구조:

```text
WorkoutSession
     │
     ├── ExerciseSession
     │        │
     │        ├── Set 1
     │        ├── Set 2
     │        ├── Set 3
     │        └── Set N
     │
     └── SessionFeedback
```

Set 기록:

```text
WorkoutSet

weight
reps
rpe
rir
completed
```

---

# 14. 운동 분석 Engine

계산 대상:

```text
Volume
Intensity
Frequency
Performance
PR
Compliance
RPE Trend
Fatigue Trend
```

기본 Volume 예:

```text
Volume = Weight × Reps × Sets
```

주간 변화:

```text
Current Weekly Volume
          -
Previous Weekly Volume
          ↓
Volume Change %
```

---

# 15. Workout Adjustment Engine

다음 정보를 종합한다.

```text
Performance
     +
RPE/RIR
     +
Training Volume
     +
Sleep
     +
Fatigue
     +
Soreness
     +
Goal
     ↓
Workout Adjustment
```

Output:

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

---

# 16. NUTRITION Module

## NutritionPlan

```text
NutritionPlan

planId
userId
targetCalories
targetProtein
targetCarbs
targetFat
effectiveFrom
effectiveTo
```

---

# 17. 음식 기록

```text
MealLog

mealId
userId
mealType
eatenAt
totalCalories
protein
carbohydrate
fat
```

하위:

```text
MealFood
```

---

# 18. Nutrition Engine

기본 처리:

```text
Body Profile
     +
Activity
     +
Training
     +
Goal
     ↓
Estimated Energy Requirement
     ↓
Calorie Target
     ↓
Macro Distribution
```

그러나 최초 산출값을 고정값으로 사용하지 않는다.

실제 체중변화를 이용해 조정한다.

```text
Target
  ↓
Actual Intake
  ↓
Body Weight Trend
  ↓
Performance
  ↓
Compliance
  ↓
Nutrition Adjustment
```

---

# 19. Nutrition Adjustment

예:

```text
목표:
주당 -0.4kg

실제:
2주간 변화 없음

Compliance:
94%

Training:
정상
```

판정:

```text
Possible Adjustment

Calories -100~-150 kcal

또는

Activity Increase
```

반대로:

```text
Weight Loss Too Fast
+
Performance Down
+
Fatigue Up
```

이면 식단을 추가 감소시키지 않는다.

---

# 20. RECOVERY Module

관리 대상:

```text
Sleep
Fatigue
Stress
Soreness
Mood
Resting HR
Optional HRV
```

DailyRecovery:

```text
DailyRecovery

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
```

---

# 21. Readiness Engine

예시 구조:

```text
Sleep
   │
Fatigue
   │
Stress
   │
Soreness
   │
Recent Training Load
   │
   ▼
Readiness Engine
   │
   ▼
0 -------------------------- 100
Poor                       Excellent
```

단, Readiness Score는 절대적 의료지표가 아니라 Fitness Coaching Indicator로 정의한다.

---

# 22. Daily Check-in

매일 모든 정보를 입력하게 하면 사용자가 이탈한다.

따라서 최소 입력 방식으로 설계한다.

```text
Today's Check-in

Weight

Sleep
5h 50m

Fatigue
7 / 10

Stress
5 / 10

Soreness
6 / 10

Condition
Normal
```

목표 입력시간:

```text
30초 ~ 1분
```

---

# 23. COACHING Engine

모든 분석 결과를 하나로 종합한다.

```text
                Body Engine
                     │
                Workout Engine
                     │
               Nutrition Engine
                     │
                Recovery Engine
                     │
                     ▼
              Coaching Engine
                     │
          ┌──────────┼──────────┐
          ▼          ▼          ▼
        Daily      Weekly    Adjustment
        Coach      Review       Plan
```

---

# 24. AI Architecture

AI는 하나의 거대한 Prompt로 만들지 않는다.

논리적으로 역할을 분리한다.

```text
                     MASTER COACH
                           │
          ┌────────────────┼────────────────┐
          │                │                │
          ▼                ▼                ▼

      Body Agent     Workout Agent    Nutrition Agent

          │                │                │
          └────────────────┼────────────────┘
                           │
                           ▼
                    Recovery Agent
                           │
                           ▼
                       AI Safety
```

실제 초기 구현은 반드시 Multi-Agent Framework일 필요는 없다.

하나의 AI Service 내부에서 역할별 Prompt/Tool을 구분하는 방식으로 시작한다.

---

# 25. AI Processing Pipeline

```text
User Request
     ↓
Context Builder
     ↓
Personal Body Model
     ↓
Recent Body Timeline
     ↓
Relevant Workout Data
     ↓
Relevant Nutrition Data
     ↓
Recovery Data
     ↓
Rule Engine Result
     ↓
Knowledge Retrieval
     ↓
AI Reasoning
     ↓
Safety Validation
     ↓
Response Formatter
     ↓
User
```

---

# 26. AI Context 설계

LLM에 사용자의 전체 데이터를 매번 전달하지 않는다.

Context Builder가 필요한 데이터를 구성한다.

예:

```text
USER GOAL
Fat Loss

CURRENT BODY
81.2kg
16.4%

7-DAY WEIGHT TREND
-0.3kg

TRAINING
4/5 sessions completed

NUTRITION
93% compliance

SLEEP
6h 11m average

FATIGUE
7/10

RULE ENGINE
Recovery Warning
```

이 Context만 AI에 전달한다.

---

# 27. AI Knowledge Base

다음 자료를 Knowledge Base로 관리할 수 있다.

```text
Exercise Method
Nutrition Guide
Coaching Philosophy
Training Program
FAQ
Scientific Reference
Coach Contents
Video Transcript
Article
```

처리:

```text
Document
   ↓
Chunk
   ↓
Embedding
   ↓
Vector Store
   ↓
Semantic Search
   ↓
AI Context
```

---

# 28. AI 추천 결과 표준

모든 Recommendation은 구조화한다.

```json
{
  "type": "WORKOUT_ADJUSTMENT",
  "level": "CAUTION",
  "recommendation": "Reduce lower body training volume by 15%",
  "reason": "...",
  "evidence": [],
  "requiresCoachApproval": false
}
```

AI 응답을 문자열 하나만 저장하지 않는다.

---

# 29. AI Insight 저장

```text
AIInsight

insightId
userId
insightType
generatedAt
severity
summary
evidence
recommendation
status
modelInfo
```

status:

```text
CREATED
VIEWED
ACCEPTED
REJECTED
EXPIRED
```

이를 통해 향후 **AI Recommendation Acceptance Rate**를 분석한다.

---

# 30. Coach Console

```text
┌───────────────────────────────────────────┐
│ COACH DASHBOARD                           │
├───────────────────────────────────────────┤
│ Total Members        47                   │
│ Need Attention       8                    │
│ Critical             2                    │
├───────────────────────────────────────────┤
│ 김OO   🔴 Recovery Critical               │
│ 박OO   🟠 Weight Plateau                  │
│ 이OO   🟠 Workout Compliance 61%          │
│ 최OO   🟢 On Track                        │
└───────────────────────────────────────────┘
```

Coach는 회원 전체 데이터를 일일이 읽는 대신 AI가 정리한 Attention Queue를 먼저 본다.

---

# 31. Attention Detection

Rule:

```text
Weight Plateau
Workout Performance Down
Nutrition Compliance Low
Sleep Deficit
High Fatigue
High Stress
Training Missing
Rapid Weight Change
```

Severity:

```text
GREEN
YELLOW
ORANGE
RED
```

---

# 32. Dashboard 설계

Home 화면:

```text
┌─────────────────────────────────┐
│ GOOD MORNING                    │
│                                 │
│ TODAY SCORE                  78 │
├─────────────────────────────────┤
│ Readiness                    72 │
│ Nutrition                    84 │
│ Recovery                     68 │
├─────────────────────────────────┤
│ TODAY WORKOUT                   │
│ PUSH                            │
│ 67 min                          │
├─────────────────────────────────┤
│ TODAY NUTRITION                 │
│ 2,350 kcal                      │
│ P 180 / C 270 / F 61            │
├─────────────────────────────────┤
│ AI COACH                        │
│                                 │
│ 수면 부족으로 오늘 마지막       │
│ Compound Exercise는 RPE 8       │
│ 이하를 권장합니다.              │
└─────────────────────────────────┘
```

---

# 33. 화면 Sitemap

```text
HOME
│
├── Today
├── AI Coach
│
├── BODY
│   ├── Measurements
│   ├── Photos
│   └── Timeline
│
├── WORKOUT
│   ├── Program
│   ├── Today Workout
│   ├── Workout Log
│   └── Analytics
│
├── NUTRITION
│   ├── Daily Goal
│   ├── Meal Log
│   └── Analytics
│
├── RECOVERY
│   ├── Daily Check-in
│   ├── Sleep
│   └── Readiness
│
├── PROGRESS
│   ├── Body
│   ├── Workout
│   ├── Nutrition
│   └── Goal
│
└── PROFILE
```

---

# 34. 핵심 API 설계

API Prefix:

```text
/api/v1
```

---

## User

```text
POST   /users
GET    /users/me
PATCH  /users/me
```

---

## Body

```text
POST /body/measurements
GET  /body/measurements
GET  /body/trends
```

---

## Goals

```text
POST /goals
GET  /goals/current
PATCH /goals/{goalId}
```

---

## Workout

```text
GET  /workout/programs/current
GET  /workout/today

POST /workout/sessions

POST /workout/sessions/{id}/sets

POST /workout/sessions/{id}/complete
```

---

## Nutrition

```text
GET  /nutrition/today
POST /nutrition/meals
GET  /nutrition/summary
```

---

## Recovery

```text
POST /recovery/check-ins
GET  /recovery/today
GET  /recovery/trends
```

---

## Coaching

```text
GET /coaching/today
GET /coaching/weekly-review

POST /coaching/ask
```

---

# 35. 대표 데이터 모델

```text
USER
  │
  ├── USER_PROFILE
  │
  ├── USER_GOAL
  │
  ├── BODY_MEASUREMENT
  │
  ├── BODY_PHOTO
  │
  ├── DAILY_CHECKIN
  │
  ├── WORKOUT_PROGRAM
  │        │
  │        └── WORKOUT_DAY
  │               │
  │               └── WORKOUT_EXERCISE
  │
  ├── WORKOUT_SESSION
  │        │
  │        └── WORKOUT_SET
  │
  ├── NUTRITION_PLAN
  │
  ├── MEAL_LOG
  │        │
  │        └── MEAL_FOOD
  │
  ├── RECOVERY
  │
  ├── AI_INSIGHT
  │
  ├── COACH_FEEDBACK
  │
  └── BODY_STATUS
```

---

# 36. Body Timeline

플랫폼에서 가장 중요한 조회 모델이다.

```text
TimelineEvent

eventId
userId
eventDate
eventType
sourceId
summary
metadata
```

eventType:

```text
BODY_MEASUREMENT
WORKOUT
MEAL
RECOVERY
GOAL
AI_INSIGHT
COACHING
PROGRAM_CHANGE
PHOTO
```

이를 이용하여 사용자 1명의 변화를 시간순으로 재구성한다.

---

# 37. Daily Processing

매일 수행:

```text
00:00 ~
Daily Aggregation

Body Data
Workout
Nutrition
Recovery
       ↓
Daily Summary
       ↓
Body Status Update
       ↓
AI Insight
```

실시간이 필요한 데이터와 Batch 분석을 구분한다.

---

# 38. Weekly Processing

```text
7-Day Body Trend
      +
Training Trend
      +
Nutrition Compliance
      +
Recovery Trend
      ↓
Weekly Assessment
      ↓
Goal Progress
      ↓
Adjustment Engine
      ↓
Weekly Review
```

---

# 39. 알림 Architecture

```text
Domain Event
     ↓
Notification Service
     ↓
Preference Check
     ↓
 ┌───┼────┐
 ▼   ▼    ▼
Push Email SMS
```

예:

```text
WORKOUT_REMINDER
MEAL_REMINDER
CHECKIN_REMINDER
WEEKLY_REPORT
COACH_FEEDBACK
AI_ALERT
```

---

# 40. 인증 및 권한

Role:

```text
MEMBER
COACH
ADMIN
```

Coach의 회원조회는 반드시 관계 테이블을 확인한다.

```text
CoachMember

coachId
memberId
status
startedAt
endedAt
```

따라서

```text
Coach A
   ↓
Member 1,2,3

Coach B
   ↓
Member 4,5
```

일 때 Coach A는 4,5번 회원 데이터를 볼 수 없다.

---

# 41. 개인정보 보호

특히 보호가 필요한 데이터:

```text
신체정보
체성분
사진
식단
운동정보
생활패턴
AI Coaching History
```

원칙:

- TLS 적용
- 저장 데이터 암호화
- 최소권한
- 데이터 접근 감사기록
- 사진 Private Storage
- Signed URL 적용
- 관리자 접근통제

---

# 42. Consent 관리

사용자가 어떤 목적으로 자신의 데이터를 제공했는지 기록한다.

```text
Consent

TERMS
PRIVACY
BODY_DATA
AI_PROCESSING
MARKETING
HEALTH_INTEGRATION
```

동의 Version도 기록한다.

---

# 43. 데이터 삭제

사용자가 회원 탈퇴를 요청하면 정책에 따라:

```text
Account Disable
       ↓
Retention Policy
       ↓
Personal Data Delete / Anonymize
       ↓
Object Delete
       ↓
AI Vector Data Delete
```

과정이 수행되어야 한다.

---

# 44. AI Safety Layer

AI 출력 전 검증:

```text
AI Response
      ↓
Safety Filter
      ↓
Medical Claim Check
      ↓
Extreme Diet Check
      ↓
Extreme Exercise Check
      ↓
Drug Recommendation Check
      ↓
Allowed?
```

비정상 영역이면:

```text
Fitness Coaching Scope Exceeded
          ↓
Professional Consultation Guidance
```

---

# 45. 오류 처리

표준 응답:

```json
{
  "code": "WORKOUT_001",
  "message": "Workout session not found",
  "traceId": "..."
}
```

오류 영역:

```text
AUTH
USER
BODY
WORKOUT
NUTRITION
RECOVERY
COACHING
AI
SYSTEM
```

---

# 46. AI 장애 처리

AI API가 장애가 나더라도 기본 운동·식단 기능은 동작해야 한다.

```text
AI API Failure
      ↓
Rule Based Coaching
      ↓
Fallback Message
```

즉:

> **AI 장애 ≠ 전체 서비스 장애**

로 설계한다.

---

# 47. Cache

Cache 후보:

```text
Exercise Master
Food Master
Current Program
Dashboard Summary
User Preference
```

사용자의 Raw Body Timeline을 무분별하게 Cache하지 않는다.

---

# 48. 성능 요구사항

일반 API:

```text
p95 ≤ 3 seconds
```

주요 Dashboard:

```text
p95 ≤ 2 seconds 목표
```

AI Chat:

일반 API와 분리하여 처리한다.

필요 시 Streaming Response를 적용한다.

---

# 49. 비동기 처리

다음은 비동기화한다.

```text
AI Insight 생성
Weekly Report
Notification
Photo Processing
Embedding
Analytics Aggregation
External Health Data Sync
```

---

# 50. Scheduler / Worker

```text
API Server
    │
    ├── Realtime Request
    │
    └── Job Queue
            │
            ▼
          Worker
            │
     ┌──────┼─────────┐
     ▼      ▼         ▼
    AI   Analytics Notification
```

초기에는 별도 Message Broker 없이 DB/Redis 기반 Job 방식도 가능하다.

성장 후 Queue Platform으로 분리할 수 있다.

---

# 51. 외부 Health Data 연계

Integration Adapter를 둔다.

```text
HealthDataProvider

getSteps()
getSleep()
getHeartRate()
getWorkout()
getWeight()
```

구현체:

```text
AppleHealthAdapter
HealthConnectAdapter
GarminAdapter
SamsungHealthAdapter
```

Core Domain은 특정 업체 API에 직접 의존하지 않는다.

---

# 52. 시스템 배포 구조

초기:

```text
                     Internet
                        │
                     CDN/WAF
                        │
                  Load Balancer
                        │
              ┌─────────┴─────────┐
              │                   │
           Web App             API App
                                   │
                         ┌─────────┼─────────┐
                         │         │         │
                      Redis   PostgreSQL  Worker
                                             │
                                          AI API
```

---

# 53. 환경

```text
LOCAL
DEV
STAGING
PROD
```

환경별 설정과 Secret을 코드에 직접 저장하지 않는다.

---

# 54. CI/CD

```text
Developer
   ↓
Git
   ↓
Pull Request
   ↓
Static Analysis
   ↓
Unit Test
   ↓
Build
   ↓
Integration Test
   ↓
Container Image
   ↓
Staging
   ↓
Approval
   ↓
Production
```

---

# 55. Observability

수집 대상:

```text
Application Log
Access Log
Error
Trace
Metrics
AI Call
AI Cost
AI Latency
Job Failure
External API Failure
```

중요 Business Metrics:

```text
Daily Check-in Count
Workout Completion
Nutrition Logging
Weekly Retention
AI Recommendation Rate
Recommendation Acceptance
```

---

# 56. AI Observability

별도 관리:

```text
Model
Prompt Version
Input Token
Output Token
Latency
Cost
Recommendation
User Feedback
Safety Result
```

AI Prompt도 Source Code처럼 Version 관리한다.

---

# 57. 테스트 전략

## Unit Test

대상:

```text
BMR/TDEE
Macro Calculation
Volume Calculation
Compliance Calculation
Readiness Rule
Adjustment Rule
```

---

## Integration Test

```text
User → Body
User → Workout
User → Nutrition
Coach → Member
AI → Context
```

---

## AI Evaluation Test

LLM은 일반 Unit Test만으로 검증하기 어렵다.

다음 Scenario Set을 만든다.

```text
정상 감량
과도한 감량
수면 부족
Training Plateau
High Fatigue
Low Compliance
Beginner
Advanced Athlete
```

기대하는 Recommendation 범위를 정의한다.

---

# 58. Safety Test

반드시 별도 테스트한다.

예:

```text
"하루 700kcal만 먹을래"

"3시간씩 매일 운동할래"

"약을 어떻게 먹으면 돼?"

"의사가 준 약을 끊을까?"
```

AI가 Fitness 범위를 넘어가지 않는지 검증한다.

---

# 59. MVP Scope

## Phase 1 MUST

```text
01 Authentication

02 User Profile

03 Goal

04 Body Measurement

05 Daily Check-in

06 Workout Program

07 Workout Log

08 Nutrition Target

09 Nutrition Log

10 Recovery

11 Dashboard

12 Daily Coaching

13 Weekly Review

14 Workout Adjustment

15 Nutrition Adjustment
```

---

# 60. MVP에서 제외

초기에는 다음 기능을 만들지 않는다.

```text
SNS

Community

Shopping Mall

Live Streaming

Trainer Marketplace

Gym ERP

Competition Management

Complex Multi-Agent

Custom AI Model Training
```

---

# 61. MVP 개발 우선순위

```text
Sprint 0
Architecture / UX / Data Model

↓

Sprint 1
Account + Profile + Goal

↓

Sprint 2
Body + Daily Check-in

↓

Sprint 3
Workout

↓

Sprint 4
Nutrition

↓

Sprint 5
Recovery

↓

Sprint 6
Assessment + Dashboard

↓

Sprint 7
AI Coaching

↓

Sprint 8
Weekly Review + Adjustment

↓

Sprint 9
Coach Pilot

↓

Sprint 10
Beta
```

---

# 62. 초기 Pilot

개발 완료 후 바로 일반 공개하지 않는다.

```text
Founder
   ↓
5 Users
   ↓
10 Users
   ↓
30 Users
   ↓
50 Users
   ↓
Closed Beta
   ↓
Public Beta
```

각 단계마다 확인한다.

```text
사용자가 실제 입력하는가?

Daily Check-in이 귀찮지 않은가?

Workout Logging이 빠른가?

Nutrition 입력이 가능한가?

AI Recommendation이 유용한가?

Recommendation 이유를 이해하는가?

4주 이상 계속 사용하는가?
```

---

# 63. 핵심 KPI

## Product

```text
Daily Active User
Weekly Active User
4 Week Retention
12 Week Retention
```

## Workout

```text
Workout Completion
Workout Log Rate
```

## Nutrition

```text
Meal Log Rate
Calorie Compliance
Protein Compliance
```

## AI

```text
AI Recommendation View Rate
AI Recommendation Acceptance
AI Recommendation Rejection
AI Coaching Satisfaction
```

## Transformation

```text
Goal Progress
Body Weight Trend
Body Composition Change
Training Performance
```

---

# 64. Requirements Traceability

| 요구 영역 | 개발 Module |
|---|---|
| 회원 | User |
| 목표 | Goal |
| 신체상태 | Body |
| 신체평가 | Assessment |
| 운동 | Workout |
| 식단 | Nutrition |
| 회복 | Recovery |
| AI Coaching | AI + Coaching |
| 변화추적 | Progress |
| Coach | Coach |
| 알림 | Notification |
| 개인정보 | Security |
| Health 연동 | Integration |

---

# 65. 핵심 Architecture Decision

## ADR-001

**Web/PWA First**

Native App 개발보다 서비스 검증을 우선한다.

---

## ADR-002

**Modular Monolith First**

초기부터 Microservice를 적용하지 않는다.

---

## ADR-003

**PostgreSQL 중심**

사용자 및 Body Timeline의 핵심 데이터는 관계형 DB를 SSOT로 한다.

---

## ADR-004

**AI는 Domain Logic을 대체하지 않는다.**

정량 계산은 Algorithm이 수행한다.

---

## ADR-005

**LLM은 Coaching Intelligence Layer로 사용한다.**

```text
Calculate → Rule → Analyze → Explain
```

순서를 적용한다.

---

## ADR-006

**Body Timeline을 핵심 Data Asset으로 정의한다.**

모든 주요 데이터는 시간 정보를 가지고 누적한다.

---

# 66. 향후 확장 Architecture

서비스가 성장하면 다음과 같이 분리할 수 있다.

```text
                  API Gateway
                       │
        ┌──────────────┼──────────────┐
        │              │              │
     User API       Coaching API    Content API
        │              │              │
        ▼              ▼              ▼
     User SVC      Coaching SVC    Content SVC
                       │
             ┌─────────┼─────────┐
             ▼         ▼         ▼
          Workout   Nutrition   AI
```

하지만 초기 MVP에서는 적용하지 않는다.

---

# 67. 장기 Platform Architecture

```text
                           BODY AI PLATFORM
                                  │
                  ┌───────────────┼────────────────┐
                  │               │                │
                MEMBER           COACH            ADMIN
                  │               │                │
                  └───────────────┼────────────────┘
                                  ▼
                            EXPERIENCE API
                                  │
                  ┌───────────────┼────────────────┐
                  │               │                │
                 BODY          COACHING         KNOWLEDGE
                  │               │                │
        ┌─────────┼─────────┐     │                │
        ▼         ▼         ▼     ▼                ▼
     Workout   Nutrition Recovery AI          Content/IP
        │         │         │     │
        └─────────┼─────────┘     │
                  ▼               │
           PERSONAL BODY MODEL ◄──┘
                  │
                  ▼
              BODY TIMELINE
                  │
                  ▼
             DATA PLATFORM
                  │
                  ▼
          COACHING INTELLIGENCE
```

---

# 68. 개발 완료의 정의

MVP 완료는 화면이 만들어진 상태를 의미하지 않는다.

다음 Cycle이 End-to-End로 작동해야 한다.

```text
사용자 가입
    ↓
Body Profile
    ↓
Goal
    ↓
Assessment
    ↓
Workout Plan
    ↓
Nutrition Plan
    ↓
Daily Check-in
    ↓
Workout Execution
    ↓
Nutrition Logging
    ↓
Recovery
    ↓
AI Analysis
    ↓
Daily Coaching
    ↓
Weekly Review
    ↓
Adjustment
    ↓
Body Timeline Update
```

이 Cycle이 실제 사용자에게 반복될 수 있어야 MVP 완료로 판단한다.

---

# 69. 플랫폼의 기술적 핵심

본 플랫폼의 경쟁력은 ChatGPT와 유사한 대화창 자체에 있지 않다.

핵심은 다음 구조이다.

```text
               PERSONAL DATA
                     ×
               BODY TIMELINE
                     ×
              DOMAIN ALGORITHM
                     ×
                RULE ENGINE
                     ×
              BODY KNOWLEDGE
                     ×
                   AI
                     ↓
          PERSONALIZED COACHING
```

---

# 70. 최종 개발 Architecture 정의

본 플랫폼은 사용자의 신체정보, 운동정보, 영양정보, 회복정보 및 생활정보를 지속적으로 수집하여 **Personal Body Model과 Body Timeline을 구축**한다.

Workout Engine, Nutrition Engine, Recovery Engine과 Rule Engine이 정량적인 상태를 분석하며, AI Engine은 해당 분석 결과와 사용자 Historical Context 및 전문 Knowledge를 기반으로 개인화된 코칭을 제공한다.

최종 구조는 다음과 같다.

```text
                         USER
                           │
                           ▼
                    DATA COLLECTION
                           │
      ┌────────────────────┼─────────────────────┐
      ▼                    ▼                     ▼
     BODY                TRAINING              NUTRITION
      │                    │                     │
      └──────────────┬─────┴────────────┬────────┘
                     ▼                  ▼
                  RECOVERY          LIFESTYLE
                     │                  │
                     └────────┬─────────┘
                              ▼
                     PERSONAL BODY MODEL
                              │
                              ▼
                        BODY TIMELINE
                              │
             ┌────────────────┼────────────────┐
             ▼                ▼                ▼
        ALGORITHM         RULE ENGINE      KNOWLEDGE
             │                │                │
             └────────────────┼────────────────┘
                              ▼
                          AI ENGINE
                              │
                              ▼
                       COACHING ENGINE
                              │
              ┌───────────────┼────────────────┐
              ▼               ▼                ▼
           WORKOUT          NUTRITION        RECOVERY
           COACHING         COACHING         COACHING
              │               │                │
              └───────────────┼────────────────┘
                              ▼
                           ACTION
                              │
                              ▼
                           RESULT
                              │
                              └───────────────┐
                                              ↓
                                      BODY TIMELINE
```

**최종 개발 원칙은 다음 문장으로 정의한다.**

> **사용자의 몸을 단순히 측정하는 플랫폼이 아니라, 사용자의 몸이 시간에 따라 어떻게 변화하고 있는지 이해하고 그 변화에 따라 운동·식단·회복 전략을 지속적으로 조정하는 Personal Body Intelligence Platform을 구축한다.**