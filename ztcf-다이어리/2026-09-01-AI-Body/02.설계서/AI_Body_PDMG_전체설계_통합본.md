

<!-- FILE: 00_전체설계_마스터_인덱스.md -->


# AI 기반 개인 신체상태·운동·식단 코칭 플랫폼
# PDMG 기반 개발 설계 마스터 인덱스

- 문서 버전: v0.1
- 기준일: 2026-09-01
- 상태: BASELINE
- Backend 기준: `pdmg-service`
- UI 기준: `pdmg-ui`
- Java 업무 Root: `nhnis.mg.by`
- 핵심 자산: `Personal Body Model + Body Timeline + Coaching Intelligence`

---

## 1. 문서 목적

본 문서 세트는 지금까지 확정한 AI Body Coaching Platform의 요구사항과 PDMG 구현 기준을
개발 가능한 수준으로 분리 정리한 Baseline이다.

본 문서 세트의 기준 우선순위는 다음과 같다.

```text
1. 플랫폼 요구사항 정의서
   ↓
2. 플랫폼 개발 설계서의 Domain / AI / Data 원칙
   ↓
3. PDMG 실제 소스 및 실행구조
   ↓
4. PDMG Java 네이밍 및 코딩 표준서
   ↓
5. 현재 대화에서 확정한 BY 업무코드 / Package / UI 적용 결정
```

일반적인 Spring/React/JPA 설계와 PDMG 구현 방식이 충돌하는 경우
**PDMG 실제 소스와 PDMG 표준을 우선한다.**

---

## 2. 현재 확정된 핵심 결정

| 구분 | 결정 |
|---|---|
| Architecture | Modular Monolith First |
| Backend | 기존 `pdmg-service` 확장 |
| Backend Root | `nhnis.mg.by` |
| Framework Flow | TCF → Handler → Facade → Service → Rule → DAO → Mapper |
| UI | 기존 `pdmg-ui` 확장 |
| 업무 화면 | `static/{ProgramId}/index.html` |
| UI 공통 기능 | `static/_shared/*` 재사용 |
| Protocol | ServiceId + `hdr_nhnis + dto` |
| Persistence | PDMG DAO + MyBatis Mapper |
| Naming | PDMG AS-IS 업무 클래스 소문자 시작 |
| Data Principle | Longitudinal / Append-oriented |
| AI Principle | Algorithm + Rule + AI Hybrid |
| AI Safety | Fitness 영역, 의료진단/처방 금지 |
| Human in Loop | Coach Review 지원 |

---

## 3. 문서 구성

| 문서 | 내용 |
|---|---|
| `01_요구사항_기준선.md` | Vision, 사용자, 기능, MVP, 안전 요구사항 |
| `02_PDMG_적용_전체아키텍처.md` | 플랫폼 전체 구조와 PDMG 변환 |
| `03_BY_업무코드_및_네이밍_표준.md` | BY 업무코드, Program/Service ID, Naming |
| `04_Backend_패키지_및_거래구조.md` | `nhnis.mg.by.*` 패키지와 호출계층 |
| `05_pdmg-ui_화면_아키텍처.md` | HTML 중심 pdmg-ui 화면 구현 |
| `06_Program_Service_Registry_초안.md` | Program ID / Service ID Registry 초안 |
| `07_데이터_아키텍처.md` | 핵심 Entity, Body Timeline, 데이터 원칙 |
| `08_Rule_Algorithm_설계.md` | 정량 계산과 Rule Engine 경계 |
| `09_AI_Coaching_아키텍처.md` | AI Context, Insight, Safety, Fallback |
| `10_보안_비기능_운영_설계.md` | 보안, 성능, 확장성, 관측성 |
| `11_개발로드맵_및_결정대기목록.md` | Sprint 순서와 미확정 항목 |

---

## 4. 전체 개발 흐름

```text
Requirement
    ↓
BY Domain Code
    ↓
Program ID
    ↓
Service ID
    ↓
pdmg-ui/{ProgramId}/index.html
    ↓
Handler
    ↓
Facade
    ↓
Service
    ↓
Rule / Algorithm
    ↓
DAO
    ↓
MyBatis Mapper
    ↓
DB
    ↓
Test
```

AI가 필요한 거래는 Service/Rule 결과 위에서 다음 파이프라인을 추가한다.

```text
Domain Data
    ↓
Algorithm / Rule
    ↓
Personal Body Model
    ↓
Context Builder
    ↓
AI
    ↓
Safety Check
    ↓
Coaching Result
    ↓
AIInsight / Timeline
```

---

## 5. 개발 착수 전 핵심 체크

- [x] Java Root `nhnis.mg.by`
- [x] 세부업무 코드 14개 정의
- [x] PDMG Naming 적용 원칙
- [x] pdmg-ui HTML 화면 구현 원칙
- [x] Rule/AI 역할 분리 원칙
- [ ] Program 번호 Registry 최종 승인
- [ ] Service ID 전체 목록 최종 승인
- [ ] DB 물리 모델 확정
- [ ] 화면별 DTO/Service 매핑 확정
- [ ] Algorithm 공식/Threshold 확정
- [ ] AI Provider 및 운영 정책 확정


<!-- FILE: 01_요구사항_기준선.md -->


# AI Body Coaching Platform 요구사항 기준선

- 상태: BASELINE
- 기준: 플랫폼 요구사항 정의서
- 구현 플랫폼: PDMG

---

## 1. Vision

> 개인의 신체상태와 생활 데이터를 기반으로 사용자의 몸이 어떻게 변화하는지를 지속적으로 이해하고,
> 운동·영양·회복을 통합하여 개인화된 코칭을 제공하는 AI Body Coaching Platform

```text
Physical Condition
+ Training
+ Nutrition
+ Recovery
+ Lifestyle
+ Historical Data
        ↓
Personal Body Model
        ↓
AI Coaching
```

플랫폼의 경쟁력은 단순 AI Chat이 아니라 다음 조합에 있다.

```text
개인 데이터
× 장기간 History
× Body Model
× 운동 Algorithm
× 영양 Algorithm
× AI Reasoning
× Coach Expertise
```

최종 핵심 자산:

```text
Personal Body Digital Twin
+
Body Timeline
+
Coaching Intelligence
```

---

## 2. 사용자 유형

| 역할 | 책임 |
|---|---|
| Member | 자신의 신체·운동·식단·회복 데이터를 기록하고 코칭을 실행 |
| Coach | 담당 회원 상태 확인, 직접 피드백, AI 추천 승인/수정/거절 |
| Administrator | 플랫폼 운영·정책·기준정보 관리 |
| AI Coach | 분석·추천·설명·주간 Review 지원 |

향후 Nutrition Coach, Bodybuilding Coach, Gym Manager, Content Creator 확장이 가능하다.

---

## 3. 핵심 기능 영역

1. User / Profile
2. Body
3. Assessment
4. Workout
5. Nutrition
6. Recovery
7. Coaching
8. AI
9. Progress
10. Human Coach
11. Knowledge
12. Notification
13. External Integration
14. Management / Admin

---

## 4. 핵심 User Journey

```text
회원가입
   ↓
Profile
   ↓
Goal
   ↓
Body Measurement
   ↓
Initial Assessment
   ↓
Workout Program
   ↓
Nutrition Plan
   ↓
Daily Coaching
   ↓
Workout / Meal / Sleep / Fatigue 기록
   ↓
Daily Analysis
   ↓
Weekly Review
   ↓
Workout / Nutrition Adjustment
   ↓
Progress
   ↓
Body Timeline
```

---

## 5. 주요 기능 요구사항

### 5.1 User

- 회원가입
- 개인 Profile
- 운동경력 / 활동수준
- 복수 Goal
- 개인정보/동의

### 5.2 Body

- 체중
- 체지방률
- 골격근량
- BMI
- 신체둘레
- 변화 사진
- Body Timeline

### 5.3 Assessment

- 초기 상태 평가
- Body Composition Score
- Training Score
- Nutrition Score
- Recovery Score
- Sleep Score
- Readiness Score

### 5.4 Workout

- 개인별 Workout Program
- Exercise / Set / Reps / Weight / Rest / RPE / RIR
- 실제 수행 기록
- Compliance
- Volume / Frequency / Intensity / PR
- Plateau 감지
- Adjustment

### 5.5 Nutrition

- 목표 Calories
- Protein / Carbohydrate / Fat
- 음식 기록
- Nutrition Compliance
- 체중 변화 및 수행상태 기반 Adjustment

### 5.6 Recovery

- 취침/기상
- 총 수면시간
- 수면 만족도
- Fatigue
- Soreness
- Stress
- Readiness

### 5.7 AI Coaching

- Personal Body Model
- Daily Analysis
- Daily Coaching
- Weekly Review
- Workout Adjustment
- Nutrition Adjustment
- Explainable Recommendation

중요 추천에는 다음 정보를 포함한다.

```text
Recommendation
+ Reason
+ Evidence
+ Expected Effect
+ Caution
```

### 5.8 Coach

- 담당 회원 Dashboard
- 위험/관심 회원 식별
- Coach Feedback
- AI Recommendation Review
  - Approve
  - Modify
  - Reject

### 5.9 Progress

- Goal Progress
- Body Trend
- Training Trend
- Transformation Record

### 5.10 Notification

- Workout Reminder
- Nutrition Reminder
- Check-in Reminder

### 5.11 External Integration

향후 연계 후보:

- Apple Health
- Health Connect
- Samsung Health
- Garmin
- Fitbit
- Smart Scale

연계 데이터 후보:

- Steps
- Heart Rate
- Sleep
- Calories
- Exercise
- Weight

---

## 6. MVP

### MUST

1. 회원가입
2. Profile
3. Goal
4. Body Measurement
5. Daily Check-in
6. Workout Program
7. Workout Log
8. Nutrition Goal
9. Nutrition Log
10. Sleep / Fatigue
11. AI Daily Analysis
12. Weekly Review
13. Workout Adjustment
14. Nutrition Adjustment
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

## 7. 안전 경계

### 금지

- 의료진단
- 질병 확정 표현
- 의약품 처방
- 의약품 중단/변경 지시
- 비정상적 과도 운동 자동추천

### Escalation

일반 Fitness Coaching 범위를 벗어나는 경우
전문 의료기관 또는 전문가 상담을 안내한다.

---

## 8. 비기능 요구사항

| 항목 | 기준 |
|---|---|
| 일반 화면 p95 | `≤ 3초` |
| 초기 User | 100~1,000 |
| 중기 | 10,000+ |
| 장기 | 100,000+ |
| UX | Mobile First |
| Integration | API First |
| Data | 안정적 장기 보존 |
| AI | 별도 응답시간/Fallback 정책 가능 |


<!-- FILE: 02_PDMG_적용_전체아키텍처.md -->


# AI Body Coaching Platform - PDMG 적용 전체 아키텍처

- 상태: BASELINE
- Architecture Style: Modular Monolith First
- Backend Runtime: PDMG
- UI Runtime: pdmg-ui

---

## 1. 핵심 원칙

플랫폼 개발 설계서의 Domain/AI/Data 원칙은 유지하되,
물리 구현은 PDMG 실제 구조로 변환한다.

| 일반 설계 개념 | PDMG 구현 |
|---|---|
| React/Next.js Frontend | `pdmg-ui` Static HTML |
| `/api/v1/...` REST | PDMG Service ID |
| Generic Controller | TCF Handler 중심 |
| Generic Transaction Service | Facade `@Transactional` |
| JPA | DAO + MyBatis Mapper |
| Generic API Error | PDMG Global Error |
| Generic Trace ID | PDMG GUID/MDC/ServiceId |

---

## 2. 전체 구조

```text
┌───────────────────────────┐
│          Browser          │
└─────────────┬─────────────┘
              │
              ▼
┌───────────────────────────┐
│          pdmg-ui          │
│                           │
│ static/index.html         │
│ static/{ProgramId}/       │
│        index.html         │
│ static/_shared/*          │
└─────────────┬─────────────┘
              │
              │ ServiceId
              │ hdr_nhnis + dto
              ▼
┌───────────────────────────┐
│       pdmg-service        │
│       nhnis.mg.by.*       │
└─────────────┬─────────────┘
              │
              ▼
       pdmg-fw / TCF
              │
              ▼
          Handler
              │
              ▼
          Facade
              │
              ▼
          Service
              │
      ┌───────┴────────┐
      ▼                ▼
 Rule/Algorithm      AI Adapter
      │                │
      └───────┬────────┘
              ▼
             DAO
              │
              ▼
       MyBatis Mapper
              │
              ▼
              DB
```

---

## 3. Domain 구조

```text
BY Platform
│
├─ u  User
├─ b  Body
├─ a  Assessment
├─ w  Workout
├─ n  Nutrition
├─ r  Recovery
├─ c  Coaching
├─ i  AI Intelligence
├─ p  Progress
├─ h  Human Coach
├─ k  Knowledge
├─ t  Notification
├─ e  External Integration
└─ m  Management / Admin
```

---

## 4. 핵심 Body Intelligence Pipeline

```text
Body Data
Workout Data
Nutrition Data
Recovery Data
Lifestyle Data
Historical Data
       │
       ▼
Domain Calculation
       │
       ▼
Assessment
       │
       ▼
Personal Body Model
       │
       ▼
Body Timeline
       │
       ▼
Rule / Adjustment Candidates
       │
       ▼
Context Builder
       │
       ▼
AI Analysis / Explain
       │
       ▼
Safety Check
       │
       ▼
Daily Coaching / Weekly Review
       │
       ▼
Execution / Tracking
       │
       └───────────────> Body Timeline
```

---

## 5. 동기/비동기 구분

### 동기 우선

- Profile 조회/수정
- Body Measurement 등록/조회
- Daily Check-in
- Workout 기록
- Nutrition 기록
- Assessment 기본 계산
- Dashboard 조회

### 비동기 후보

- AI Insight 생성
- Weekly Review
- Notification 발송
- Body Photo Processing
- Knowledge Embedding
- Health Data Sync
- 장기 Analytics Aggregation

초기 구현은 PDMG 구조를 유지하면서 Scheduler/Worker 또는 DB 기반 Job으로 시작할 수 있다.
Queue 도입은 실제 부하와 운영 요구가 확인된 후 결정한다.

---

## 6. 장애 격리

AI 장애가 전체 업무 장애가 되어서는 안 된다.

```text
AI 정상
  → Rule + AI Coaching

AI 장애
  → Rule Based Coaching
  → AI 상태/장애 로그 기록
  → 사용자 핵심 기록 업무는 계속 제공
```

---

## 7. Architecture Decision

### 확정

- Data First
- Modular Monolith First
- PDMG Runtime 우선
- HTML 기반 pdmg-ui
- MyBatis/PDMG Persistence
- Algorithm + Rule + AI Hybrid
- Body Timeline Core
- Human in the Loop
- Fitness Boundary

### 아직 미확정

- 실제 DBMS/Schema 상세
- AI Provider
- Vector Store 도입 시점
- Object Storage 상세
- Notification Provider
- Wearable 연동 순서


<!-- FILE: 03_BY_업무코드_및_네이밍_표준.md -->


# BY 업무코드 및 PDMG 네이밍 표준

- 상태: BASELINE
- 대구분: `mg`
- 업무구분: `by`
- Java Root: `nhnis.mg.by`

---

## 1. 세부업무 코드 사전

| 코드 | 업무 | 영문 의미 |
|---|---|---|
| `u` | 사용자 | User |
| `b` | 신체 | Body |
| `a` | 신체평가 | Assessment |
| `w` | 운동 | Workout |
| `n` | 영양 | Nutrition |
| `r` | 회복 | Recovery |
| `c` | 코칭 | Coaching |
| `i` | AI | AI Intelligence |
| `p` | 성과/변화 | Progress |
| `h` | 사람 코치 | Human Coach |
| `k` | 지식 | Knowledge |
| `t` | 알림 | Notification |
| `e` | 외부연계 | External Integration |
| `m` | 관리자/운영 | Management / Admin |

예약 코드:

```text
d f g j l o q s v x y z
```

향후 신규 업무 추가 전 코드 사전 충돌 여부를 먼저 확인한다.

---

## 2. Program ID

PDMG Program ID는 9자리다.

```text
[대구분2][업무2][세부1][프로그램번호4]

mg + by + w + 1000
        ↓
mgbyw1000
```

예:

```text
mgbyu1000  User
mgbyb1000  Body
mgbya1000  Assessment
mgbyw1000  Workout
mgbyn1000  Nutrition
mgbyr1000  Recovery
mgbyc1000  Coaching
mgbyi1000  AI
mgbyp1000  Progress
mgbyh1000  Human Coach
mgbyk1000  Knowledge
mgbyt1000  Notification
mgbye1000  External Integration
mgbym1000  Management
```

---

## 3. Service ID

Service ID는 11자리다.

```text
[Program ID 9][거래구분1][순번1]
```

거래구분:

| 코드 | 의미 |
|---|---|
| `S` | 조회 |
| `C` | 등록 |
| `U` | 수정 |
| `D` | 삭제 |
| `A` | 혼합 |
| `R` | 리포트 |

예:

```text
mgbyw1000S0
mgbyw1000C0
mgbyw1000U0
mgbyw1000D0
```

---

## 4. Program 단위 Type

거래구분을 클래스명에 붙이지 않는다.

```text
mgbyw1000Handler
mgbyw1000Facade
mgbyw1000Service
mgbyw1000DAO
mgbyw1000Controller
mgbyw1000-ORA.xml
```

금지:

```text
mgbyw1000S0Handler
mgbyw1000C0Service
mgbyw1000U0DAO
```

---

## 5. AS-IS Class Case

PDMG 업무 프로그램 Type은 Program ID를 그대로 접두로 사용한다.

```text
mgbyw1000Handler
mgbyw1000Facade
mgbyw1000Service
mgbyw1000DAO
```

즉 소문자로 시작한다.

공통 컴포넌트는 PascalCase를 사용한다.

```text
WorkoutVolumeCalculator
AiSafetyValidator
SecurityConfig
BodyTrendRule
```

---

## 6. DTO

거래 DTO는 Service ID 전체를 사용한다.

```text
mgbyw1000S0DTOin
mgbyw1000S0DTOout
mgbyw1000C0DTOin
mgbyw1000C0DTOout
mgbyw1000S0DTOSub0
```

---

## 7. SQL ID

예:

```text
mgbyw1000S0_S0
mgbyw1000S0_S1
mgbyw1000C0_C0
mgbyw1000U0_U0
mgbyw1000D0_D0
```

강제 계약:

```text
DAO FQCN = Mapper namespace
DAO Method = Mapper Statement ID
```

---

## 8. End-to-End Traceability

```text
Service ID
mgbyw1000C0
       │
       ├─ UI data-default-transaction-id
       ├─ URL /mgbyw1000C0
       ├─ Handler.serviceIds()
       ├─ mgbyw1000C0DTOin/out
       ├─ Facade.mgbyw1000C0()
       ├─ Service.mgbyw1000C0()
       ├─ DAO.mgbyw1000C0_C0()
       ├─ Mapper id="mgbyw1000C0_C0"
       └─ Log / MDC
```

Naming은 Style이 아니라 Runtime Contract로 취급한다.


<!-- FILE: 04_Backend_패키지_및_거래구조.md -->


# Backend 패키지 및 PDMG 거래구조

- 상태: BASELINE
- 대상 모듈: `pdmg-service`
- Root Package: `nhnis.mg.by`

---

## 1. Package Tree

```text
nhnis.mg.by
│
├─ u
├─ b
├─ a
├─ w
├─ n
├─ r
├─ c
├─ i
├─ p
├─ h
├─ k
├─ t
├─ e
└─ m
```

일반 업무 도메인은 다음 패턴을 사용한다.

```text
nhnis.mg.by.[세부]
│
├─ entry
│  ├─ handler
│  └─ aspect                # 필요 시
│
├─ application
│  ├─ controller            # TCF OFF/호환 필요 시
│  ├─ facade
│  ├─ service
│  └─ rule                  # Rule이 필요한 업무
│
├─ dto
│
├─ persistence
│  └─ dao
│
├─ config                   # 필요 시
├─ client                   # 외부 호출이 있는 경우
└─ support                  # 도메인 지원 컴포넌트
```

---

## 2. 예: Workout

```text
nhnis.mg.by.w
│
├─ entry
│  └─ handler
│     └─ mgbyw1000Handler
│
├─ application
│  ├─ facade
│  │  └─ mgbyw1000Facade
│  ├─ service
│  │  └─ mgbyw1000Service
│  └─ rule
│     ├─ WorkoutComplianceCalculator
│     ├─ WorkoutVolumeCalculator
│     └─ WorkoutAdjustmentRule
│
├─ dto
│  ├─ mgbyw1000S0DTOin
│  ├─ mgbyw1000S0DTOout
│  ├─ mgbyw1000C0DTOin
│  └─ mgbyw1000C0DTOout
│
└─ persistence
   └─ dao
      └─ mgbyw1000DAO
```

Mapper:

```text
src/main/resources/
└─ mapper/
   └─ rdw/
      └─ mg/
         └─ by/
            └─ w/
               └─ mgbyw1000-ORA.xml
```

---

## 3. PDMG TCF ON Flow

```text
Browser / pdmg-ui
      │
      ▼
pdmg-fw DefaultFilter
      │
      ▼
OnlineTransactionController
      │
      ▼
TransactionHandler Registry
      │
      ▼
mgbyxNNNNHandler
      │
      ▼
mgbyxNNNNFacade
      │
      ▼
mgbyxNNNNService
      │
      ├─ Rule / Algorithm
      └─ Domain Logic
      │
      ▼
mgbyxNNNNDAO
      │
      ▼
MyBatis Mapper
      │
      ▼
DB
```

---

## 4. 계층 책임

### Handler

책임:

- Service ID 등록
- Service ID별 Use Case 선택
- DTO 전달
- Facade 호출

금지:

- DAO 직접 호출
- SQL
- 핵심 Business Rule
- 자체 Transaction
- 대규모 JSON 변환

### Facade

책임:

- Program 단위 거래 진입
- Service Method 호출
- Transaction Boundary
- 여러 Service 조합이 필요한 경우 Application Orchestration

Transaction Manager는 PDMG 실제 RDW 설정을 따른다.

### Service

책임:

- Business Rule
- Domain Logic
- Rule/Algorithm 호출
- DAO 호출
- AI 연계 전 Domain Context 준비

### Rule / Algorithm

책임:

- 반복 가능한 정량 계산
- Threshold/Policy 판단
- Adjustment Candidate 생성

AI에 넘겨서는 안 되는 계산을 담당한다.

### DAO

책임:

- Mapper Interface
- SQL Statement ID 계약

### Mapper

책임:

- SQL
- Result Mapping
- DAO FQCN Namespace 계약

---

## 5. 거래 예

```text
POST /mgbyb1000C0
       │
       ▼
mgbyb1000Handler
       │
       ▼
mgbyb1000Facade.mgbyb1000C0()
       │
       ▼
mgbyb1000Service.mgbyb1000C0()
       │
       ▼
BodyMeasurementValidationRule
       │
       ▼
mgbyb1000DAO.mgbyb1000C0_C0()
       │
       ▼
mgbyb1000-ORA.xml
       │
       ▼
BODY_MEASUREMENT
```

---

## 6. AI Package 특성

AI 영역은 일반 CRUD Program뿐 아니라 공통 AI Adapter가 존재할 수 있다.

```text
nhnis.mg.by.i
│
├─ entry/handler
├─ application/facade
├─ application/service
├─ dto
├─ client
├─ safety
└─ support
```

공통 Type 예:

```text
AiContextBuilder
AiCoachingClient
AiSafetyValidator
AiResponseParser
RuleFallbackCoach
```

이 공통 Type은 PascalCase를 적용한다.


<!-- FILE: 05_pdmg-ui_화면_아키텍처.md -->


# pdmg-ui 기반 Body AI 화면 아키텍처

- 상태: BASELINE
- 대상 모듈: `pdmg-ui`
- 화면 기술: HTML 중심
- 화면 단위: Program ID

---

## 1. 실제 pdmg-ui 구조

```text
pdmg-ui
└─ src/main
   │
   ├─ java
   │  └─ nhnis/mg/ui
   │     ├─ PdmgUiApplication.java
   │     ├─ application/service
   │     ├─ client
   │     ├─ config
   │     ├─ entry/web
   │     └─ support
   │
   └─ resources
      │
      ├─ application.yml
      ├─ sample-requests
      └─ static
         ├─ index.html
         ├─ _shared/
         └─ {ProgramId}/index.html
```

---

## 2. 업무 화면 원칙

업무 프로그램 화면은 다음 패턴을 사용한다.

```text
static/
└─ mgbyw1000/
   └─ index.html
```

화면별 별도 JS/CSS 프로젝트 구조를 만들지 않는다.

금지 예:

```text
mgbyw1000/
├─ index.html
├─ workout.js
├─ workout.css
└─ components.js
```

공통 동작/스타일은 기존 `_shared` 리소스를 사용한다.

---

## 3. 기존 공통 Resource

현재 pdmg-ui 소스 기준 주요 공통 파일:

```text
_shared/
├─ ui-context.js
├─ pdmg-shell.js
├─ service-client.js
├─ online-single.js
├─ error-codes.js
├─ error-popup.js
├─ jwt-admin.js
├─ online.css
├─ pdmg-workbench.css
├─ jwt-admin.css
└─ om-admin.css
```

Body AI 화면은 기존 공통 기능을 우선 재사용한다.

---

## 4. Program HTML 표준

예:

```html
<!DOCTYPE html>
<html lang="ko"
      data-program-id="mgbyw1000"
      data-default-transaction-id="mgbyw1000S0">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <script src="/_shared/ui-context.js"></script>
  <link rel="stylesheet" href="/_shared/online.css">
</head>
<body class="pdmg-page">

  <!-- 업무 화면 -->

  <script src="/_shared/error-codes.js"></script>
  <script src="/_shared/error-popup.js"></script>
  <script src="/_shared/service-client.js"></script>
  <script src="/_shared/online-single.js"></script>
</body>
</html>
```

화면에만 필요한 소규모 동작은 HTML 내부 `<script>`로 둘 수 있다.
공통화가 필요해질 경우에만 `_shared` 확장을 검토한다.

---

## 5. Body AI 화면 Directory 초안

```text
static/
│
├─ mgbyu1000/   # Profile
├─ mgbyu1100/   # Goal
├─ mgbyb1000/   # Body Measurement
├─ mgbyb1100/   # Body Timeline
├─ mgbya1000/   # Assessment
├─ mgbyw1000/   # Workout Program
├─ mgbyw1100/   # Workout Log
├─ mgbyn1000/   # Nutrition Plan
├─ mgbyn1100/   # Nutrition Log
├─ mgbyr1000/   # Daily Check-in / Recovery
├─ mgbyc1000/   # Daily Coaching
├─ mgbyc1100/   # Weekly Review
├─ mgbyp1000/   # Progress Dashboard
├─ mgbyh1000/   # Coach Dashboard
└─ mgbyi1000/   # AI Insight
```

위 Program 번호는 Registry 확정 전까지 `PROPOSED`다.

---

## 6. 화면 호출 구조

```text
{ProgramId}/index.html
       │
       ▼
_shared/online-single.js
       │
       ▼
_shared/service-client.js
       │
       │ POST /{ServiceId}
       │ hdr_nhnis + dto
       ▼
pdmg-service
       │
       ▼
Handler → Facade → Service → DAO
```

---

## 7. Main Shell

현재 `static/index.html`은 PDMG Workbench Shell 역할을 한다.

Body AI 전용 메뉴를 추가할 경우에도 우선 이 Shell 구조와 Hash Routing 방식을 유지한다.

예:

```text
#/body
#/workout
#/nutrition
#/recovery
#/coach
```

다만 실제 route 명과 메뉴 구성은 화면 Registry 확정 후 적용한다.

---

## 8. UX 원칙

기술 구조는 기존 pdmg-ui를 유지하되 서비스 UX는 다음을 지향한다.

- Mobile First
- 빠른 Daily Check-in
- Today 중심 Dashboard
- Readiness / Workout / Nutrition / Recovery 상태 가시화
- AI Recommendation은 근거와 함께 표시
- Coach 관리 회원은 Review 상태 표시


<!-- FILE: 06_Program_Service_Registry_초안.md -->


# BY Program / Service Registry 초안

- 상태: PROPOSED
- 목적: 요구사항 → 화면 → ServiceId → Java → SQL 추적성 기준
- 주의: 프로그램 번호와 세부 Service 목록은 구현 전 최종 승인 필요

---

## 1. 번호 원칙

현재 초안은 도메인 내부에서 다음 간격을 사용한다.

```text
1000  핵심 기본 기능
1100  두 번째 주요 기능
1200  분석/보조 기능
1300+ 확장
```

이는 관리 편의성을 위한 초안이며 PDMG Runtime 규칙 자체는 아니다.

---

## 2. User

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyu1000` | Profile | `S0`, `C0`, `U0` |
| `mgbyu1100` | Goal | `S0`, `C0`, `U0`, `D0` |
| `mgbyu1200` | Consent | `S0`, `C0`, `U0` |

예:

```text
mgbyu1000S0 Profile 조회
mgbyu1000U0 Profile 수정
mgbyu1100S0 Goal 조회
mgbyu1100C0 Goal 등록
```

---

## 3. Body

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyb1000` | Body Measurement | `S0`, `C0` |
| `mgbyb1100` | Body Timeline | `S0` |
| `mgbyb1200` | Body Photo | `S0`, `C0`, `D0` |

Body Measurement는 원칙적으로 Append 중심이므로 일반 수정 거래는 최소화한다.

---

## 4. Assessment

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbya1000` | Initial Assessment | `S0`, `A0` |
| `mgbya1100` | Body Status | `S0`, `A0` |
| `mgbya1200` | Readiness Assessment | `S0`, `A0` |

---

## 5. Workout

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyw1000` | Workout Program | `S0`, `C0`, `U0`, `D0` |
| `mgbyw1100` | Workout Session/Log | `S0`, `C0`, `U0` |
| `mgbyw1200` | Workout Analytics | `S0`, `R0` |
| `mgbyw1300` | Workout Adjustment | `S0`, `A0` |

---

## 6. Nutrition

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyn1000` | Nutrition Plan | `S0`, `C0`, `U0` |
| `mgbyn1100` | Meal/Nutrition Log | `S0`, `C0`, `U0`, `D0` |
| `mgbyn1200` | Nutrition Analytics | `S0`, `R0` |
| `mgbyn1300` | Nutrition Adjustment | `S0`, `A0` |

---

## 7. Recovery

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyr1000` | Daily Check-in | `S0`, `C0` |
| `mgbyr1100` | Sleep/Recovery | `S0`, `C0` |
| `mgbyr1200` | Readiness Trend | `S0`, `R0` |

---

## 8. Coaching

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyc1000` | Daily Coaching | `S0`, `A0` |
| `mgbyc1100` | Weekly Review | `S0`, `A0`, `R0` |
| `mgbyc1200` | Coaching Adjustment | `S0`, `A0` |

---

## 9. AI

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyi1000` | AI Insight | `S0`, `A0` |
| `mgbyi1100` | AI Context / Explain | `S0`, `A0` |
| `mgbyi1200` | AI Evaluation / Feedback | `S0`, `C0` |

---

## 10. Progress

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyp1000` | Progress Dashboard | `S0` |
| `mgbyp1100` | Body Trend | `S0`, `R0` |
| `mgbyp1200` | Transformation | `S0`, `R0` |

---

## 11. Human Coach

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyh1000` | Coach Member Dashboard | `S0` |
| `mgbyh1100` | Attention Queue | `S0` |
| `mgbyh1200` | Coach Feedback | `S0`, `C0` |
| `mgbyh1300` | AI Review | `S0`, `A0` |

---

## 12. Knowledge

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyk1000` | Knowledge Search | `S0` |
| `mgbyk1100` | Knowledge Management | `S0`, `C0`, `U0`, `D0` |

---

## 13. Notification

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbyt1000` | Notification Setting | `S0`, `U0` |
| `mgbyt1100` | Notification History | `S0` |
| `mgbyt1200` | Notification Dispatch | `A0` |

---

## 14. External Integration

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbye1000` | Integration Account | `S0`, `C0`, `D0` |
| `mgbye1100` | Health Sync | `S0`, `A0` |
| `mgbye1200` | Device Data | `S0`, `A0` |

---

## 15. Management / Admin

| Program ID | 기능 | Service 초안 |
|---|---|---|
| `mgbym1000` | User/Admin Management | `S0`, `U0` |
| `mgbym1100` | Policy/Code Management | `S0`, `C0`, `U0`, `D0` |
| `mgbym1200` | AI/Service Operation | `S0`, `U0` |

---

## 16. 최종 Registry에 추가해야 할 Column

```text
Requirement ID
Domain Code
Program ID
Program Name
Service ID
Transaction Type
Service Name
Package
Handler
Facade
Service
Rule/Algorithm
DAO
Mapper
SQL ID
UI Path
Input DTO
Output DTO
DB Table
MVP
Status
Owner
Test ID
```


<!-- FILE: 07_데이터_아키텍처.md -->


# AI Body Coaching Platform 데이터 아키텍처

- 상태: LOGICAL BASELINE / PHYSICAL PROPOSED
- 핵심 원칙: Longitudinal Data
- 핵심 자산: Body Timeline

---

## 1. 핵심 데이터 원칙

### 1.1 Data before AI

AI 결과보다 먼저 정확하고 재현 가능한 원천 데이터를 저장한다.

### 1.2 Append-oriented History

신체 변화, 운동 수행, 식단, 수면, 피로 등 시간축 데이터는
현재값 하나로 덮어쓰지 않고 History를 보존한다.

```text
잘못된 방식
User.currentWeight = 80.2

권장 방식
2026-09-01 82.3
2026-09-15 81.1
2026-10-01 79.8
```

### 1.3 Raw / Derived 분리

```text
Raw Data
  ├─ 실제 체중
  ├─ 운동 Set
  ├─ 섭취량
  ├─ 수면
  └─ 피로
       ↓
Derived Data
  ├─ Trend
  ├─ Compliance
  ├─ Readiness
  ├─ Assessment
  └─ Adjustment Candidate
```

---

## 2. 주요 Entity

```text
User
├─ Profile
├─ Goal
├─ Consent
├─ BodyMeasurement
├─ BodyPhoto
├─ WorkoutProgram
├─ WorkoutDay
├─ WorkoutExercise
├─ WorkoutSession
├─ WorkoutSet
├─ NutritionPlan
├─ MealLog
├─ MealFood
├─ Sleep
├─ Recovery
├─ CheckIn
├─ BodyStatus
├─ AIInsight
├─ CoachFeedback
├─ Notification
└─ TimelineEvent
```

---

## 3. Body Measurement

논리 항목:

```text
measurementId
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
createdAt
```

원칙:

- 과거 측정값 삭제/수정 최소화
- 보정이 필요한 경우 변경 이력 또는 정정 정책 필요
- `measuredAt` 기준 정렬
- Source 구분: MANUAL / DEVICE / IMPORT 등

---

## 4. Workout

```text
WorkoutProgram
  ↓
WorkoutDay
  ↓
WorkoutExercise
  ↓
WorkoutSession
  ↓
ExerciseSession
  ↓
WorkoutSet
```

WorkoutSet 핵심:

```text
weight
reps
rpe
rir
completed
sequence
performedAt
```

---

## 5. Nutrition

```text
NutritionPlan
  ├─ targetCalories
  ├─ targetProtein
  ├─ targetCarbs
  └─ targetFat

MealLog
  ↓
MealFood
```

목표와 실제를 분리하여 Compliance 계산이 가능해야 한다.

---

## 6. Recovery / Check-in

```text
DailyCheckIn
├─ sleepDuration
├─ sleepSatisfaction
├─ fatigue
├─ stress
├─ soreness
├─ condition
└─ note
```

Readiness Score 자체는 Derived Data로 관리한다.

---

## 7. Personal Body Model

Personal Body Model은 하나의 단순 Table이라기보다
사용자 상태를 계산/조회하는 Domain Model로 본다.

```text
PersonalBodyModel
├─ Current Body
├─ Body Composition
├─ Training Capability
├─ Training History
├─ Nutrition State
├─ Recovery State
├─ Lifestyle
├─ Goal Context
└─ Historical Trend
```

Snapshot을 저장할 경우 계산 Version과 기준일을 함께 관리한다.

---

## 8. Body Timeline

Body Timeline은 모든 시간축 Event를 사용자 중심으로 조합한다.

```text
USER
│
├─ 2026-09-01 Body Measurement
├─ 2026-09-01 Workout Session
├─ 2026-09-01 Nutrition Log
├─ 2026-09-01 Check-in
├─ 2026-09-01 AI Insight
│
├─ 2026-09-02 ...
│
└─ ...
```

TimelineEvent를 별도 Materialized Entity로 둘지,
기존 도메인 데이터를 통합 조회할지는 성능/조회패턴 확정 후 결정한다.

---

## 9. AIInsight

논리 항목 후보:

```text
insightId
userId
insightType
modelName
modelVersion
severity
recommendation
reason
evidence
expectedEffect
caution
status
createdAt
expiredAt
```

Status 후보:

```text
CREATED
VIEWED
ACCEPTED
REJECTED
EXPIRED
```

---

## 10. 물리 DB 결정 대기

다음은 아직 확정이 필요하다.

- 실제 DBMS
- Schema Naming
- PK 전략
- Sequence/UUID 정책
- JSON Column 사용 여부
- 사진 Object Storage
- Timeline Materialization 여부
- AI Context Snapshot 저장 범위
- Partitioning 기준
- 개인정보 암호화 대상 Column


<!-- FILE: 08_Rule_Algorithm_설계.md -->


# Rule / Algorithm 설계

- 상태: BASELINE PRINCIPLE / FORMULA PROPOSED
- 원칙: 정량 계산은 AI가 임의 수행하지 않는다.

---

## 1. 역할 분리

```text
Program Algorithm
       │
       ├─ 계산
       ├─ 정규화
       ├─ Trend
       └─ Score
       │
       ▼
Rule Engine
       │
       ├─ Threshold
       ├─ 정책
       ├─ 위험감지
       └─ Adjustment Candidate
       │
       ▼
AI
       ├─ 해석
       ├─ 설명
       ├─ 코칭 문장
       ├─ 패턴 요약
       └─ 대안 제시
```

---

## 2. Body Algorithm 후보

- BMI
- Weight Change
- Rolling Weight Average
- Body Fat Change
- Muscle Mass Change
- Circumference Trend
- Goal Progress %

예:

```text
Weight Change %
= (Current Weight - Baseline Weight) / Baseline Weight × 100
```

공식과 Window는 최종 Algorithm Specification에서 확정한다.

---

## 3. Energy / Nutrition Algorithm 후보

- BMR
- TDEE
- Activity Factor
- Target Calories
- Protein Target
- Carbohydrate Target
- Fat Target
- Calorie Compliance
- Protein Compliance
- Weight Trend Adjustment

중요:

개발 설계서의 핵심은 최초 계산값을 고정하지 않고
실제 체중/수행/준수율을 이용해 재조정하는 것이다.

---

## 4. Workout Algorithm 후보

### 기본 Volume

```text
Volume = Weight × Reps
```

세션/주간 집계:

```text
Session Volume = Σ Set Volume
Weekly Volume  = Σ Session Volume
```

필요 분석:

- Volume
- Frequency
- Intensity
- PR
- Compliance
- RPE Trend
- RIR Trend
- Performance Trend

---

## 5. Workout Adjustment Candidate

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

입력 후보:

```text
Performance
+ RPE/RIR
+ Volume Change
+ Sleep
+ Fatigue
+ Soreness
+ Compliance
+ Goal
```

---

## 6. Recovery / Readiness

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

출력:

```text
Readiness Score
Recovery State
Training Caution Level
```

정확한 가중치와 Threshold는 별도 Specification이 필요하다.

---

## 7. Plateau Detection

정체 판단은 단일 기록이 아니라 Window 기반으로 한다.

후보 신호:

- 일정 기간 Load 증가 없음
- Reps 개선 없음
- Volume은 증가하지만 Performance 정체
- 높은 RPE 지속
- Compliance가 충분한 상태에서 성과 정체

정체 기간과 최소 표본 수는 확정 필요.

---

## 8. Explainable Evidence 생성

AI에 전달할 Evidence는 Rule/Algorithm 결과에서 구조화한다.

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

AI는 이 Evidence를 설명하되 숫자를 새로 창작하지 않는다.

---

## 9. 테스트 원칙

Algorithm은 반드시 독립 단위 테스트가 가능해야 한다.

```text
Given
Known Inputs

When
Calculator / Rule 실행

Then
Deterministic Result
```

AI Evaluation Test와 Algorithm Unit Test를 분리한다.


<!-- FILE: 09_AI_Coaching_아키텍처.md -->


# AI Coaching Architecture

- 상태: BASELINE
- 핵심 원칙: AI는 Domain Logic을 대체하지 않는다.
- 처리 순서: Calculate → Rule → Analyze → Explain

---

## 1. AI 책임

AI가 담당한다.

- 데이터 해석
- 사용자 친화적 설명
- Daily Coaching
- Weekly Summary
- Pattern Summary
- 자연어 Q&A
- 대안 제시
- Coach 보조

AI가 담당하지 않는다.

- BMR/TDEE 확정 계산의 유일한 출처
- 운동 Volume 계산의 유일한 출처
- Readiness 숫자 임의 생성
- 의료 진단
- 의약품 처방
- 규칙을 무시한 운동/식단 강제 변경

---

## 2. AI Pipeline

```text
User / Scheduled Trigger
        ↓
Context Builder
        ↓
Personal Body Model
        ↓
Recent Body Timeline
        ↓
Relevant Workout/Nutrition/Recovery
        ↓
Rule / Algorithm Results
        ↓
Knowledge Retrieval
        ↓
AI Reasoning
        ↓
Safety Validator
        ↓
Response Formatter
        ↓
AIInsight
```

---

## 3. Context Builder

모든 사용자 데이터를 매 요청마다 LLM에 보내지 않는다.

Context Builder가 목적에 따라 최소 Context를 선택한다.

### Daily Coaching 예

```text
Today Check-in
Recent 7d Sleep
Recent Workout Load
Today Workout Plan
Today Nutrition Target
Current Goal
Recent Adjustment
```

### Weekly Review 예

```text
7d Body Trend
7d Workout
7d Nutrition
7d Recovery
Goal Progress
Compliance
Previous Recommendation
```

---

## 4. 구조화 Output

AI 응답은 문자열만 저장하지 않는다.

```json
{
  "recommendation": "...",
  "reason": "...",
  "evidence": [],
  "expectedEffect": "...",
  "caution": "...",
  "severity": "YELLOW",
  "actions": []
}
```

---

## 5. Severity

Coach Attention과 AI Insight에 활용할 수준 후보:

```text
GREEN
YELLOW
ORANGE
RED
```

정확한 기준은 Safety/Rule Specification에서 정의한다.

---

## 6. Human in the Loop

Coach 관리 회원:

```text
AI Recommendation
       ↓
Coach Attention Queue
       ↓
Coach Review
  ┌────┼────┐
  ▼    ▼    ▼
Approve Modify Reject
       ↓
Member
```

Coach 승인 대상의 범위는 정책으로 결정한다.

---

## 7. AI Failure Fallback

```text
AI 호출 성공
   → AI Coaching

AI Timeout / Error
   → Rule Based Coaching
   → AI 장애 상태 기록
   → 사용자 기록/조회는 정상 제공
```

---

## 8. Safety

### 반드시 차단/완화해야 하는 영역

- 의료진단
- 의약품 복용/중단 지시
- 위험한 극단적 식이
- 사용자 경험수준을 벗어난 과도한 운동
- 심각한 이상 상태를 Fitness 문제로 단정

### Escalation

```text
Fitness Scope
   → Coaching

Out of Scope / Risk
   → Safety Message
   → 전문 의료/전문가 상담 안내
```

---

## 9. AI Observability

기록 후보:

```text
provider
model
promptVersion
contextVersion
latencyMs
tokenUsage
resultStatus
fallbackUsed
safetyResult
userFeedback
coachDecision
```

개인 원문 데이터를 운영로그에 과도하게 남기지 않는다.

---

## 10. 결정 대기

- LLM Provider
- Model
- Prompt Versioning 저장방식
- Knowledge Retrieval 구현
- Vector Store 여부
- AI Timeout
- Retry
- 비용한도
- 개인정보 Masking 정책
- AI Evaluation Dataset


<!-- FILE: 10_보안_비기능_운영_설계.md -->


# 보안 · 비기능 · 운영 설계

- 상태: BASELINE / 상세 정책 일부 PROPOSED

---

## 1. 개인정보 / 민감정보

민감정보로 취급:

- 개인정보
- 신체정보
- Body Photo
- 운동/식단 기록
- 수면/피로/스트레스
- Coaching 정보

원칙:

- 전송 구간 보호
- 저장 보호
- 최소권한
- 로그 최소화
- 삭제 요청 지원
- 외부 AI 전송 데이터 최소화

---

## 2. 접근통제

### Member

본인 데이터만 접근.

### Coach

승인된 담당 회원만 접근.

### Administrator

운영 책임 범위에 따라 최소권한 부여.

### AI

필요한 Context만 제공.
전체 사용자 데이터를 무제한으로 전달하지 않는다.

---

## 3. 인증 / 세션

PDMG의 JWT/SSO 구조를 기준으로 연계한다.

구체적인 Body AI 로그인 Program과 Role Mapping은
pdmg-jwt 및 실제 운영 인증정책을 확인하여 확정한다.

---

## 4. 데이터 삭제

사용자가 계정/관련 데이터 삭제를 요청할 수 있어야 한다.

삭제 설계 시 다음을 구분해야 한다.

- 즉시 삭제 대상
- 법/운영 정책상 보존 대상
- AI 파생 데이터
- 사진/Object Storage
- Audit Log
- Backup 데이터

상세 보존기간은 별도 정책 필요.

---

## 5. 성능

일반 화면 목표:

```text
p95 ≤ 3초
```

AI 호출은 별도 SLA를 정의한다.

UI Timeout / FW Timeout / DB Timeout은 계층별로 구분한다.

```text
Browser/UI Timeout
      >
Server 업무 Timeout
      >
DB/외부 호출 세부 Timeout
```

실제 값은 PDMG 운영 정책에 맞춰 확정한다.

---

## 6. 확장성

```text
초기    100 ~ 1,000 User
중기    10,000+
장기    100,000+
```

초기는 Modular Monolith로 시작하되 다음 영역은 향후 분리 가능하도록 경계를 유지한다.

- AI
- Notification
- Integration Sync
- Image Processing
- Analytics Aggregation

---

## 7. 가용성 / 장애 격리

핵심 기록은 AI 장애와 독립적이어야 한다.

```text
Body Measurement   정상
Workout Log        정상
Nutrition Log      정상
Check-in           정상
AI                  장애 가능
```

AI 장애 시 Rule Based Fallback을 제공한다.

---

## 8. Observability

기본 MDC/Trace 후보:

```text
guid
traceId
userId
serviceId
ip
sqlId
ifId
errCode
```

업무 Metric 후보:

```text
API latency
error rate
DB latency
AI latency
AI fallback rate
daily check-in completion
workout compliance
nutrition compliance
notification success
```

---

## 9. 테스트

### Unit

- Calculator
- Rule
- Validator
- Service

### Integration

- Handler → Facade → Service
- DAO ↔ Mapper
- DB
- pdmg-ui ↔ pdmg-service

### AI Evaluation

- Grounding
- Explainability
- Safety
- Hallucination
- Recommendation consistency
- Fallback

### Security

- Member/Coach Scope
- Unauthorized access
- Data deletion
- Sensitive log exposure

---

## 10. 운영상 반드시 결정할 사항

- 환경별 URL/설정
- DB Pool
- Transaction Timeout
- AI Timeout/Retry
- Scheduler 정책
- Batch Window
- Data Retention
- Backup/Restore
- Monitoring Dashboard
- Alert Severity
- 개인정보 Masking
- 운영자 권한


<!-- FILE: 11_개발로드맵_및_결정대기목록.md -->


# 개발 로드맵 및 결정 대기 목록

- 상태: BASELINE ROADMAP
- 구현 원칙: 작은 Vertical Slice부터 End-to-End 검증

---

## 1. Sprint 0 - Baseline

목표:

PDMG 기반 Body AI 개발을 안전하게 시작할 수 있는 Registry와 공통 기준 확정.

작업:

- [ ] BY Program Registry 최종 확정
- [ ] Service ID 최종 확정
- [ ] Requirement Traceability
- [ ] DB Logical Model
- [ ] DB Physical Naming
- [ ] pdmg-ui Menu/Route
- [ ] 공통 DTO/Header 검증
- [ ] 샘플 PDMG 거래 1건 End-to-End 분석
- [ ] 테스트 Template
- [ ] AI Adapter Interface

산출물:

```text
BY-APPLICATION-REGISTRY
DB-DESIGN
SERVICE-ID-REGISTRY
UI-MAPPING
ALGORITHM-SPEC
```

---

## 2. Sprint 1 - User / Body Vertical Slice

권장 범위:

```text
Profile
  ↓
Goal
  ↓
Body Measurement
  ↓
Body Timeline
```

개발:

- `mgbyu1000`
- `mgbyu1100`
- `mgbyb1000`
- `mgbyb1100`

완료기준:

```text
HTML
→ ServiceId
→ Handler
→ Facade
→ Service
→ DAO
→ Mapper
→ DB
→ Test
```

전체 연결 검증.

---

## 3. Sprint 2 - Recovery / Assessment

```text
Daily Check-in
    ↓
Recovery
    ↓
Readiness
    ↓
Body Status
```

Algorithm/Rule 테스트를 먼저 작성한다.

---

## 4. Sprint 3 - Workout

```text
Workout Program
Workout Log
Volume
Compliance
Performance Trend
Adjustment Candidate
```

---

## 5. Sprint 4 - Nutrition

```text
Nutrition Plan
Meal Log
Calories/Macro
Compliance
Weight Trend
Adjustment Candidate
```

---

## 6. Sprint 5 - Coaching / AI

```text
Rule Results
   ↓
Context Builder
   ↓
Daily Coaching
   ↓
Safety
   ↓
AIInsight
   ↓
Fallback
```

---

## 7. Sprint 6 - Weekly Review / Progress

```text
Body Trend
Workout Trend
Nutrition Compliance
Recovery Trend
Goal Progress
        ↓
Weekly Review
```

---

## 8. Sprint 7 - Coach

- Coach Dashboard
- Attention Queue
- Feedback
- AI Approve/Modify/Reject

---

## 9. Sprint 8+ - 확장

- Notification
- Knowledge
- Wearable / Health Sync
- Body Photo
- Admin
- AI Evaluation
- Advanced Bodybuilding / Contest Prep

---

# 10. 현재 결정 완료

- [x] `pdmg-service` Backend
- [x] `nhnis.mg.by` Root
- [x] 14개 세부업무 코드
- [x] PDMG Naming
- [x] Program Type AS-IS 소문자 시작
- [x] PDMG Handler → Facade → Service → DAO
- [x] MyBatis Mapper
- [x] `pdmg-ui` 사용
- [x] 화면 = `{ProgramId}/index.html`
- [x] `_shared` 재사용
- [x] Algorithm + AI Hybrid
- [x] Body Timeline Core
- [x] Fitness Safety Boundary

---

# 11. 아직 최종 결정이 필요한 사항

## Critical - 개발 전

1. Program ID 번호 최종 승인
2. Service ID Transaction 목록
3. DBMS/Schema 및 Table Naming
4. PK/Sequence 정책
5. 화면별 DTO
6. Requirement ↔ Service Traceability
7. Initial Algorithm 공식/Threshold

## Important - AI 구현 전

8. AI Provider
9. Model
10. Context Size/Selection
11. Prompt Versioning
12. Safety Policy
13. Timeout/Retry/Fallback
14. AI Evaluation Dataset

## Important - 운영 전

15. Role/Permission
16. 개인정보/암호화
17. Retention/Delete
18. Monitoring
19. Backup/Restore
20. Notification Provider
21. External Health Integration 정책

---

# 12. 다음 권장 작업

다음 개발 산출물은 하나의 통합 Registry로 만든다.

```text
Requirement ID
Domain
Domain Code
Program ID
Service ID
Transaction
UI
DTO In/Out
Handler
Facade
Service
Rule
DAO
Mapper
Table
Test
MVP
Status
```

이 Registry 승인 후 실제 코드를 생성한다.
