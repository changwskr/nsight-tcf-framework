# AI 기반 개인 신체상태·운동·식단 코칭 플랫폼 요구사항 정의서

## 1. 목적

본 플랫폼은 사용자의 신체상태, 운동능력, 식습관, 생활패턴 및 회복상태를 지속적으로 수집·분석하고, 이를 기반으로 개인에게 적합한 운동계획과 식단계획을 제공하는 것을 목적으로 한다.

플랫폼은 단순 운동 프로그램 생성 서비스가 아니라 다음 순환구조를 제공해야 한다.

```text
사용자 신체상태
        ↓
신체·생활 데이터 수집
        ↓
현재 상태 분석
        ↓
개인 목표 설정
        ↓
운동 계획 + 식단 계획
        ↓
실행
        ↓
운동·식사·수면·체중 기록
        ↓
AI 분석
        ↓
변화 및 위험요소 감지
        ↓
운동·식단 계획 재조정
        ↓
장기 Body Timeline 축적
```

---

# 2. 플랫폼 Vision

플랫폼의 핵심 Vision은 다음과 같다.

> **개인의 신체상태와 생활 데이터를 기반으로 사용자의 몸이 어떻게 변화하는지를 지속적으로 이해하고, 운동·영양·회복을 통합하여 개인화된 코칭을 제공하는 AI Body Coaching Platform**

플랫폼 핵심 공식은 다음과 같다.

```text
Physical Condition
        +
Training
        +
Nutrition
        +
Recovery
        +
Lifestyle
        +
Historical Data
        ↓
Personal Body Model
        ↓
AI Coaching
```

---

# 3. 목표 사용자

## 3.1 1차 대상

### 일반 헬스 및 체형개선 사용자

주요 목적은 다음과 같다.

- 체지방 감소
- 근육량 증가
- 체형 개선
- 건강한 운동습관 형성
- 체중 관리
- 운동 수행능력 향상

---

## 3.2 2차 대상

### 중급 이상 헬스 사용자

- 근비대
- Strength 향상
- 운동 Volume 관리
- 운동 정체기 분석
- 체중 증량 및 감량
- 운동·식단 최적화

---

## 3.3 3차 대상

### 보디빌딩 선수 및 대회 준비자

- Contest Prep
- Peak Week 관리
- 체중·체지방 변화관리
- 포징 및 컨디셔닝
- Competition History
- 시즌/비시즌 관리

---

# 4. 사용자 역할

플랫폼은 최소 다음 사용자 유형을 제공해야 한다.

| 역할 | 주요 기능 |
|---|---|
| Member | 개인 신체·운동·식단 관리 |
| Coach | 회원 상태 확인 및 코칭 |
| Administrator | 플랫폼 운영 및 관리 |
| AI Coach | 데이터 분석 및 추천 |

향후 다음 역할을 추가할 수 있다.

| 역할 | 설명 |
|---|---|
| Nutrition Coach | 식단 전문 코칭 |
| Bodybuilding Coach | 대회 준비 전문 |
| Gym Manager | 소속 회원 관리 |
| Content Creator | 교육 콘텐츠 제공 |

---

# 5. 핵심 기능 영역

플랫폼은 다음 핵심 영역으로 구성한다.

```text
01. User/Profile
02. Body Assessment
03. Body Timeline
04. Workout
05. Nutrition
06. Recovery
07. AI Coaching
08. Progress
09. Coach Management
10. Knowledge
11. Notification
12. Administration
```

---

# 6. 사용자 및 프로필 요구사항

## FR-USR-001 회원가입

사용자는 다음 방법을 이용하여 가입할 수 있어야 한다.

- 이메일
- 휴대전화
- 소셜 로그인

---

## FR-USR-002 기본 프로필

다음 정보를 관리할 수 있어야 한다.

- 이름 또는 닉네임
- 연령
- 성별
- 키
- 체중
- 운동경력
- 직업
- 일상 활동 수준

---

## FR-USR-003 목표 설정

사용자는 다음 목표를 선택할 수 있어야 한다.

- 체중 감량
- 체지방 감소
- 근육 증가
- 체중 증가
- 근력 증가
- 체형 개선
- 운동습관 형성
- 대회 준비

복수 목표 설정을 지원할 수 있어야 한다.

---

# 7. 신체정보 관리 요구사항

## FR-BODY-001 신체 측정값

다음 정보를 기록할 수 있어야 한다.

- 체중
- 체지방률
- 골격근량
- BMI
- 허리둘레
- 가슴둘레
- 팔둘레
- 허벅지둘레

---

## FR-BODY-002 변화 사진

사용자는 신체 변화 사진을 등록할 수 있어야 한다.

예:

```text
Front
Side
Back
```

사진별 촬영일을 관리해야 한다.

---

## FR-BODY-003 신체 변화 Timeline

신체정보는 시간순으로 저장되어야 한다.

```text
2026-09-01
82.3kg / 18.2%

↓

2026-09-15
81.1kg / 17.1%

↓

2026-10-01
79.8kg / 15.8%
```

---

# 8. Body Assessment 요구사항

## FR-ASMT-001 최초 평가

회원가입 후 사용자의 현재 상태를 평가해야 한다.

평가 항목:

- 신체상태
- 운동경력
- 운동빈도
- 운동 가능시간
- 생활 활동량
- 수면시간
- 식습관
- 스트레스
- 목표

---

## FR-ASMT-002 Body Status 생성

플랫폼은 수집된 데이터를 기반으로 현재 상태를 구조화해야 한다.

예:

```text
Body Composition       74
Training Level         65
Nutrition              72
Recovery               55
Sleep                  48
Training Readiness     61
```

---

# 9. 운동관리 요구사항

## FR-WRK-001 운동 프로그램

AI 또는 Coach가 개인별 프로그램을 생성할 수 있어야 한다.

프로그램에는 다음 정보가 포함되어야 한다.

- 운동부위
- 운동종목
- 세트
- 반복횟수
- 중량
- 휴식시간
- RPE 또는 RIR
- 운동 순서

---

## FR-WRK-002 운동기록

사용자는 실제 수행 결과를 기록할 수 있어야 한다.

```text
Bench Press

Set 1
100kg × 10
RPE 7

Set 2
105kg × 8
RPE 8
```

---

## FR-WRK-003 운동수행률

계획 대비 실제 수행률을 계산해야 한다.

예:

```text
계획 운동 20개
수행 운동 18개

Workout Compliance
90%
```

---

## FR-WRK-004 운동 Volume 분석

다음 정보를 분석할 수 있어야 한다.

- 주간 Volume
- 부위별 Volume
- 운동빈도
- Intensity
- PR
- 운동수행 추세

---

## FR-WRK-005 운동 정체 감지

AI는 운동기록을 분석하여 정체 가능성을 감지해야 한다.

예:

```text
Bench Press

8주간
중량 변화 없음

Volume 증가
Performance 정체
```

---

# 10. 식단관리 요구사항

## FR-NUT-001 목표 칼로리

사용자의 다음 정보를 기반으로 일일 목표 Calories를 계산할 수 있어야 한다.

- 신체정보
- 활동량
- 운동량
- 목표
- 최근 체중변화

---

## FR-NUT-002 Macronutrient 목표

다음 목표를 제공해야 한다.

- Protein
- Carbohydrate
- Fat

예:

```text
Calories
2,400 kcal

Protein
180g

Carbohydrate
285g

Fat
60g
```

---

## FR-NUT-003 음식기록

사용자는 음식 섭취 내역을 기록할 수 있어야 한다.

입력방법:

- 직접 입력
- 음식 검색
- 자주 먹는 음식
- 식단 Template

향후 음식 사진 분석을 지원할 수 있다.

---

## FR-NUT-004 식단 준수율

계획 대비 실제 섭취 상태를 계산해야 한다.

```text
Calories Compliance
94%

Protein Compliance
88%
```

---

## FR-NUT-005 식단 조정

AI 또는 Coach는 체중 변화와 운동수행 상태를 기반으로 식단 변경을 추천할 수 있어야 한다.

---

# 11. Recovery 요구사항

## FR-REC-001 수면

다음 정보를 기록해야 한다.

- 취침시간
- 기상시간
- 총 수면시간
- 수면 만족도

---

## FR-REC-002 피로도

사용자는 일일 피로도를 입력할 수 있어야 한다.

예:

```text
1 ~ 10
```

---

## FR-REC-003 근육통

부위별 근육통을 기록할 수 있어야 한다.

---

## FR-REC-004 스트레스

주관적 스트레스 수준을 관리해야 한다.

---

## FR-REC-005 Readiness Score

신체·수면·피로·최근 운동량을 기반으로 운동 준비상태를 계산해야 한다.

```text
Training Readiness

82 / 100
```

---

# 12. AI Coaching 요구사항

## FR-AI-001 Personal Body Model

AI는 각 사용자별 개인 상태 모델을 유지해야 한다.

```text
Personal Body Model

Body
Training
Nutrition
Recovery
Lifestyle
Goal
Historical Trend
```

---

## FR-AI-002 Daily Analysis

AI는 일일 데이터를 분석해야 한다.

예:

```text
오늘 수면시간
5h 40m

최근 평균
7h 05m

Fatigue
높음
```

---

## FR-AI-003 Daily Coaching

AI는 다음 내용을 통합하여 일일 가이드를 생성해야 한다.

- 운동
- 식단
- 회복

---

## FR-AI-004 Weekly Review

AI는 매주 다음 내용을 분석해야 한다.

```text
Body Progress

Training Progress

Nutrition Compliance

Recovery

Goal Progress
```

---

## FR-AI-005 운동 프로그램 Adjustment

다음 상황을 감지하여 프로그램 변경을 추천해야 한다.

- 운동 수행능력 저하
- 피로 누적
- Volume 과다
- 운동 정체
- 회복 부족
- 운동 결손

---

## FR-AI-006 식단 Adjustment

다음 정보를 기반으로 식단 변경을 추천해야 한다.

```text
체중변화
+
Body Composition
+
식단 준수율
+
Training Performance
+
활동량
```

---

## FR-AI-007 AI 설명가능성

AI는 추천 이유를 설명해야 한다.

금지:

```text
오늘 운동량을 줄이세요.
```

권장:

```text
최근 5일 평균 수면시간이
평소보다 17% 감소하였고,

최근 하체 Training Volume이
23% 증가했습니다.

따라서 오늘 하체 운동 Volume을
약 10~15% 낮추는 것을 권장합니다.
```

---

# 13. Coach 기능 요구사항

## FR-COA-001 회원 Dashboard

Coach는 자신이 관리하는 회원을 조회할 수 있어야 한다.

---

## FR-COA-002 위험·관심 회원 식별

AI는 Coach가 확인해야 할 회원을 구분해야 한다.

예:

```text
🔴 확인 필요

김OO
체중 14일 정체

이OO
수면 부족 5일 지속

박OO
운동 수행률 54%
```

---

## FR-COA-003 Coach Feedback

Coach는 회원에게 직접 Feedback을 제공할 수 있어야 한다.

---

## FR-COA-004 AI Recommendation 승인

AI가 운동 또는 식단을 변경하는 경우,

Coach 관리 회원에 대해서는 다음 구조를 지원해야 한다.

```text
AI Recommendation
       ↓
Coach Review
       ↓
Approve / Modify / Reject
       ↓
Member
```

---

# 14. Progress 요구사항

## FR-PRG-001 목표진행률

사용자의 목표 진행상태를 표시해야 한다.

---

## FR-PRG-002 Body Trend

다음 데이터를 그래프로 제공해야 한다.

- 체중
- 체지방
- 근육량
- 신체둘레

---

## FR-PRG-003 Training Trend

다음 정보를 제공해야 한다.

- Strength
- Volume
- Frequency
- PR

---

## FR-PRG-004 Transformation

사용자의 변화과정을 하나의 Transformation Record로 관리해야 한다.

```text
Before
↓
Program
↓
Training
↓
Nutrition
↓
Coaching
↓
After
```

---

# 15. Dashboard 요구사항

메인 Dashboard에서는 최소 다음 정보를 제공해야 한다.

```text
TODAY

Body Status

Training Readiness

Today's Workout

Today's Nutrition

Sleep

Recovery

Goal Progress

AI Coach Recommendation
```

---

# 16. 알림 요구사항

## FR-NTF-001 운동 알림

운동 예정시간에 알림을 제공해야 한다.

## FR-NTF-002 식단 알림

식사 또는 영양목표 관련 알림을 제공할 수 있어야 한다.

## FR-NTF-003 Check-in 알림

다음 입력 누락 시 알림을 제공할 수 있어야 한다.

- 체중
- 수면
- 운동
- 식단
- 컨디션

---

# 17. 외부 데이터 연계 요구사항

향후 다음 서비스와 연계 가능해야 한다.

```text
Apple Health
Google Health Connect
Garmin
Samsung Health
Fitbit
Smart Scale
Wearable Devices
```

연계 데이터 예:

- Steps
- Heart Rate
- Sleep
- Calories
- Exercise
- Weight

---

# 18. 데이터 요구사항

플랫폼의 주요 핵심 Entity는 다음과 같다.

```text
User
│
├── Profile
├── Goal
├── BodyMeasurement
├── BodyPhoto
├── WorkoutProgram
├── WorkoutSession
├── Exercise
├── WorkoutSet
├── NutritionPlan
├── Meal
├── NutritionLog
├── Sleep
├── Recovery
├── CheckIn
├── CoachFeedback
├── AIInsight
└── Competition
```

---

# 19. Body Timeline 요구사항

플랫폼의 가장 중요한 데이터 구조로 사용자의 장기간 변화정보를 관리해야 한다.

```text
USER

2026.09
│
├─ Body
├─ Workout
├─ Nutrition
├─ Sleep
└─ Recovery

2026.10
│
├─ Body
├─ Workout
├─ Nutrition
├─ Sleep
└─ Recovery

2026.11
...
```

이를 통해 AI는 단일 시점이 아닌 변화 추세를 분석해야 한다.

---

# 20. AI 안전 요구사항

## SAFE-001 의료진단 금지

플랫폼 AI는 의료진단을 수행해서는 안 된다.

예:

```text
당뇨병입니다.
심장질환입니다.
```

등과 같은 표현을 제공해서는 안 된다.

---

## SAFE-002 의약품 처방 금지

AI는 의약품 사용·중단·변경을 지시해서는 안 된다.

---

## SAFE-003 위험상황 Escalation

사용자가 입력한 정보가 일반 운동·영양 코칭 범위를 벗어나는 경우 전문 의료기관 또는 전문가 상담을 안내해야 한다.

---

## SAFE-004 과도한 운동 제한

AI는 사용자의 현재 상태와 운동경력을 벗어나는 비정상적인 운동량을 자동 제안해서는 안 된다.

---

# 21. 개인정보 및 보안 요구사항

## SEC-001 개인정보 보호

신체정보와 건강 관련 데이터는 민감정보로 취급해야 한다.

---

## SEC-002 데이터 암호화

다음 데이터는 전송 및 저장 과정에서 보호해야 한다.

- 개인정보
- 신체정보
- 사진
- 코칭정보

---

## SEC-003 접근통제

Coach는 자신이 관리하도록 승인된 회원 데이터만 열람할 수 있어야 한다.

---

## SEC-004 데이터 삭제

사용자는 자신의 계정과 관련 데이터를 삭제 요청할 수 있어야 한다.

---

# 22. 비기능 요구사항

## NFR-001 성능

일반 화면 조회 응답시간 목표:

```text
p95 ≤ 3초
```

AI 분석은 별도의 응답시간 정책을 적용할 수 있다.

---

## NFR-002 확장성

초기:

```text
User 100~1,000
```

중기:

```text
10,000+
```

장기:

```text
100,000+
```

확장이 가능해야 한다.

---

## NFR-003 가용성

회원 운동기록과 주요 데이터를 안정적으로 보존해야 한다.

---

## NFR-004 Mobile First

주요 사용자 기능은 모바일 환경을 우선하여 설계해야 한다.

---

## NFR-005 API First

향후 앱·웨어러블·외부서비스 연계를 고려하여 주요 기능을 API화해야 한다.

---

# 23. MVP 요구사항

첫 번째 버전에서는 다음 기능만 우선 구현한다.

### MUST

1. 회원가입
2. 개인 Profile
3. 목표 설정
4. Body Measurement
5. Daily Check-in
6. Workout Program
7. Workout Log
8. Nutrition Goal
9. Nutrition Log
10. Sleep / Fatigue
11. AI Daily Analysis
12. Weekly Review
13. 운동 Adjustment
14. 식단 Adjustment
15. Progress Dashboard

### SHOULD

- Coach Dashboard
- Body Photo
- Notification
- Wearable 연동

### LATER

- Community
- SNS
- 쇼핑몰
- 영상강의
- Certification
- Gym Management
- B2B SaaS

---

# 24. MVP 핵심 User Journey

```text
회원가입
   ↓
신체정보 입력
   ↓
목표 설정
   ↓
초기 Body Assessment
   ↓
운동 프로그램 생성
   ↓
영양 목표 생성
   ↓
Daily Coaching
   ↓
운동 수행
   ↓
식단 기록
   ↓
수면 / 피로 입력
   ↓
AI 분석
   ↓
Weekly Review
   ↓
프로그램 / 식단 조정
   ↓
Body Progress
```

---

# 25. 성공 KPI

플랫폼 성공 여부는 단순 가입자 수보다 다음 지표를 기준으로 판단한다.

| 구분 | KPI |
|---|---|
| Engagement | 주간 활성 사용자 |
| Workout | 운동 기록률 |
| Nutrition | 식단 기록률 |
| Compliance | 프로그램 수행률 |
| Retention | 4주/12주 유지율 |
| Transformation | 목표 달성률 |
| Coaching | Coach 관리 시간 절감 |
| AI | AI 추천 수용률 |
| Business | 유료 전환율 |

---

# 26. 플랫폼 핵심 설계원칙

### Principle 01

**Data before AI**

AI보다 먼저 데이터를 정확하게 수집한다.

### Principle 02

**Longitudinal Data**

단일 상태보다 장기간 변화 추세를 중요하게 본다.

### Principle 03

**Explainable Coaching**

AI는 무엇을 해야 하는지만 아니라 왜 해야 하는지를 설명한다.

### Principle 04

**Human in the Loop**

중요한 판단은 Coach가 검토할 수 있어야 한다.

### Principle 05

**Personalization**

모든 사용자가 동일한 프로그램을 받는 구조를 지양한다.

### Principle 06

**Continuous Adjustment**

프로그램은 고정된 계획이 아니라 지속적으로 변화해야 한다.

### Principle 07

**Fitness, Not Diagnosis**

초기 서비스는 Fitness Coaching에 집중하고 의료진단 영역을 명확히 구분한다.

---

# 27. 플랫폼 전체 요구사항 Big Picture

```text
                         USER
                           │
                           ▼
                  ┌─────────────────┐
                  │ Personal Profile│
                  └────────┬────────┘
                           │
                           ▼
                  ┌─────────────────┐
                  │ Body Assessment │
                  └────────┬────────┘
                           │
                           ▼
                  ┌─────────────────┐
                  │Personal Body Model
                  └────────┬────────┘
                           │
          ┌────────────────┼────────────────┐
          │                │                │
          ▼                ▼                ▼
       Workout          Nutrition        Recovery
          │                │                │
          └────────────────┼────────────────┘
                           │
                           ▼
                     AI Coach
                           │
           ┌───────────────┼───────────────┐
           │               │               │
        Analyze         Recommend        Explain
           │               │               │
           └───────────────┼───────────────┘
                           ▼
                       Execution
                           │
                           ▼
                        Tracking
                           │
                           ▼
                        Progress
                           │
                           ▼
                      Adjustment
                           │
                           ▼
                     Body Timeline
                           │
                           └──────────┐
                                      ↓
                              AI Re-Learning
```

---

# 28. 최종 요구사항 정의

본 플랫폼에서 가장 중요한 요구사항은 다음 한 문장으로 정의한다.

> **사용자의 신체상태, 운동, 영양, 수면, 회복 및 행동 데이터를 장기간 축적하여 개인의 Body Timeline을 구축하고, AI가 해당 변화 패턴을 분석하여 개인에게 적합한 운동·영양·회복 코칭을 지속적으로 조정·제공할 수 있어야 한다.**

플랫폼의 핵심 경쟁력은 단순한 `AI Chat`이 아니라 다음에 있다.

```text
개인 데이터
     ×
장기간 History
     ×
Body Model
     ×
운동 알고리즘
     ×
영양 알고리즘
     ×
AI Reasoning
     ×
Coach Expertise
```

즉 최종적으로 플랫폼의 핵심 자산은 **Personal Body Digital Twin + Body Timeline + Coaching Intelligence**이다.