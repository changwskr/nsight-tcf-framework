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
