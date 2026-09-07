# Sprint 01 개발계획서
# User / Body Vertical Slice

- 문서 버전: v0.1
- 기준일: 2026-09-01
- 상태: DEVELOPMENT BASELINE
- Sprint ID: `SPRINT-01`
- Backend: `pdmg-service`
- UI: `pdmg-ui`
- Java Root: `nhnis.mg.by`
- 개발 원칙: 작은 Vertical Slice부터 End-to-End 검증

---

# 1. Sprint 목표

사용자의 기본 프로필과 목표를 등록하고,
신체 측정값을 시계열로 축적하여 Body Timeline으로 조회할 수 있는
최초 End-to-End Vertical Slice를 완성한다.

```text
Profile
   ↓
Goal
   ↓
Body Measurement
   ↓
Body Timeline
```

Sprint 01은 이후 Recovery, Workout, Nutrition, AI Coaching이 사용할
가장 기본적인 개인 데이터 기반을 만든다.

---

# 2. Sprint 범위

## 2.1 대상 Program

| Program ID | 업무 | 기능 | 상태 |
|---|---|---|---|
| `mgbyu1000` | User | Profile | PROPOSED |
| `mgbyu1100` | User | Goal | PROPOSED |
| `mgbyb1000` | Body | Body Measurement | PROPOSED |
| `mgbyb1100` | Body | Body Timeline | PROPOSED |

## 2.2 대상 Service ID

### Profile

```text
mgbyu1000S0  Profile 조회
mgbyu1000C0  Profile 등록
mgbyu1000U0  Profile 수정
```

### Goal

```text
mgbyu1100S0  Goal 조회
mgbyu1100C0  Goal 등록
mgbyu1100U0  Goal 수정
mgbyu1100D0  Goal 삭제/종료
```

### Body Measurement

```text
mgbyb1000S0  Body Measurement 조회
mgbyb1000C0  Body Measurement 등록
```

Body Measurement는 Append-oriented History 원칙을 적용한다.
일반 Update 거래는 기본 범위에 포함하지 않는다.

### Body Timeline

```text
mgbyb1100S0  Body Timeline 조회
```

---

# 3. 요구사항 Traceability

| Req ID | 요구사항 | Program | Service |
|---|---|---|---|
| `BY-USR-PRF-001` | 개인 Profile 조회 | `mgbyu1000` | `mgbyu1000S0` |
| `BY-USR-PRF-002` | 개인 Profile 등록 | `mgbyu1000` | `mgbyu1000C0` |
| `BY-USR-PRF-003` | 개인 Profile 수정 | `mgbyu1000` | `mgbyu1000U0` |
| `BY-USR-GOL-001` | Goal 조회 | `mgbyu1100` | `mgbyu1100S0` |
| `BY-USR-GOL-002` | Goal 등록 | `mgbyu1100` | `mgbyu1100C0` |
| `BY-USR-GOL-003` | Goal 수정 | `mgbyu1100` | `mgbyu1100U0` |
| `BY-USR-GOL-004` | Goal 삭제/종료 | `mgbyu1100` | `mgbyu1100D0` |
| `BY-BOD-MSR-001` | 신체 측정 이력 조회 | `mgbyb1000` | `mgbyb1000S0` |
| `BY-BOD-MSR-002` | 신체 측정 등록 | `mgbyb1000` | `mgbyb1000C0` |
| `BY-BOD-TML-001` | Body Timeline 조회 | `mgbyb1100` | `mgbyb1100S0` |

Requirement ID는 Working ID이며 정식 요구관리체계가 확정되면 매핑한다.

---

# 4. 데이터 범위

## 4.1 UserProfile

논리 필드 후보:

```text
userId
displayName
birthDate
gender
height
trainingExperience
activityLevel
createdAt
updatedAt
```

## 4.2 UserGoal

논리 필드 후보:

```text
goalId
userId
goalType
targetWeight
targetBodyFat
targetDate
priority
status
createdAt
updatedAt
```

Goal Type 후보:

```text
FAT_LOSS
MUSCLE_GAIN
WEIGHT_GAIN
STRENGTH
BODY_RECOMPOSITION
FITNESS
CONTEST_PREP
```

## 4.3 BodyMeasurement

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

Source 후보:

```text
MANUAL
DEVICE
IMPORT
```

## 4.4 Body Timeline

```text
TimelineEvent
├─ eventId
├─ userId
├─ eventDate
├─ eventType
├─ sourceId
├─ summary
└─ metadata
```

Sprint 01에서는 최소한 다음 Event를 조회 가능하게 한다.

```text
GOAL
BODY_MEASUREMENT
```

TimelineEvent 물리 Materialization 여부는 TBD이다.

---

# 5. Backend Package

## 5.1 User / Profile

```text
nhnis.mg.by.u
│
├─ entry
│  └─ handler
│     └─ mgbyu1000Handler
│
├─ application
│  ├─ facade
│  │  └─ mgbyu1000Facade
│  └─ service
│     └─ mgbyu1000Service
│
├─ dto
│  ├─ mgbyu1000S0DTOin
│  ├─ mgbyu1000S0DTOout
│  ├─ mgbyu1000C0DTOin
│  ├─ mgbyu1000C0DTOout
│  ├─ mgbyu1000U0DTOin
│  └─ mgbyu1000U0DTOout
│
└─ persistence
   └─ dao
      └─ mgbyu1000DAO
```

## 5.2 User / Goal

```text
nhnis.mg.by.u
│
├─ entry/handler/mgbyu1100Handler
├─ application/facade/mgbyu1100Facade
├─ application/service/mgbyu1100Service
├─ dto/mgbyu1100*DTO*
└─ persistence/dao/mgbyu1100DAO
```

## 5.3 Body Measurement

```text
nhnis.mg.by.b
│
├─ entry/handler/mgbyb1000Handler
├─ application/facade/mgbyb1000Facade
├─ application/service/mgbyb1000Service
├─ application/rule/BodyMeasurementValidationRule
├─ dto/mgbyb1000*DTO*
└─ persistence/dao/mgbyb1000DAO
```

## 5.4 Body Timeline

```text
nhnis.mg.by.b
│
├─ entry/handler/mgbyb1100Handler
├─ application/facade/mgbyb1100Facade
├─ application/service/mgbyb1100Service
├─ dto/mgbyb1100S0DTOin
├─ dto/mgbyb1100S0DTOout
└─ persistence/dao/mgbyb1100DAO
```

---

# 6. Mapper 구조

```text
src/main/resources/mapper/rdw/mg/by/
│
├─ u/
│  ├─ mgbyu1000-ORA.xml
│  └─ mgbyu1100-ORA.xml
│
└─ b/
   ├─ mgbyb1000-ORA.xml
   └─ mgbyb1100-ORA.xml
```

강제 계약:

```text
DAO FQCN = Mapper namespace
DAO Method = Mapper Statement ID
```

예:

```text
nhnis.mg.by.b.persistence.dao.mgbyb1000DAO
        =
<mapper namespace="nhnis.mg.by.b.persistence.dao.mgbyb1000DAO">
```

---

# 7. UI 구조

```text
pdmg-ui/src/main/resources/static/
│
├─ mgbyu1000/
│  └─ index.html
├─ mgbyu1100/
│  └─ index.html
├─ mgbyb1000/
│  └─ index.html
└─ mgbyb1100/
   └─ index.html
```

공통 Resource:

```text
/_shared/ui-context.js
/_shared/online.css
/_shared/error-codes.js
/_shared/error-popup.js
/_shared/service-client.js
/_shared/online-single.js
```

화면 호출:

```text
index.html
   ↓
Service ID
   ↓
POST /{ServiceId}
   ↓
hdr_nhnis + dto
   ↓
pdmg-service
```

---

# 8. 핵심 End-to-End 흐름

## 8.1 Body Measurement 등록

```text
pdmg-ui/mgbyb1000/index.html
        │
        │ mgbyb1000C0
        ▼
OnlineTransactionController
        ▼
mgbyb1000Handler
        ▼
mgbyb1000Facade.mgbyb1000C0()
        ▼
mgbyb1000Service.mgbyb1000C0()
        ▼
BodyMeasurementValidationRule
        ▼
mgbyb1000DAO.mgbyb1000C0_C0()
        ▼
mgbyb1000-ORA.xml
        ▼
BODY_MEASUREMENT 논리 Entity
```

## 8.2 Body Timeline 조회

```text
mgbyb1100S0
   ↓
mgbyb1100Handler
   ↓
mgbyb1100Facade
   ↓
mgbyb1100Service
   ↓
Profile / Goal / BodyMeasurement
   ↓
Timeline 조합
   ↓
mgbyb1100S0DTOout
```

---

# 9. 개발 Task

## 9.1 설계

- [ ] Program 번호 최종 승인
- [ ] Service ID 목록 승인
- [ ] DTO 필드 정의
- [ ] Logical ERD 작성
- [ ] Physical DB Naming 확정
- [ ] PK/Sequence 정책 확정
- [ ] Profile/Goal 개인정보 최소수집 검토
- [ ] Timeline Materialization 정책 결정

## 9.2 Backend

- [ ] `mgbyu1000`
- [ ] `mgbyu1100`
- [ ] `mgbyb1000`
- [ ] `mgbyb1100`
- [ ] Handler 구현
- [ ] Facade Transaction 적용
- [ ] Service 구현
- [ ] Body Measurement Validation Rule
- [ ] DAO 작성
- [ ] Mapper XML 작성

## 9.3 UI

- [ ] Profile 화면
- [ ] Goal 화면
- [ ] Body Measurement 화면
- [ ] Body Timeline 화면
- [ ] `_shared` 기반 Service 호출
- [ ] 오류 Popup 연계
- [ ] Mobile First 확인

---

# 10. 테스트

## Unit

- Profile Validator
- Goal Validation
- Body Measurement Validation
- Timeline 조합 로직

## Integration

```text
Handler → Facade → Service
DAO ↔ Mapper
DB
pdmg-ui ↔ pdmg-service
```

## Security

- 본인 데이터만 조회 가능
- 타 사용자 userId 조작 차단
- 로그 개인정보 노출 점검

## Contract

- Program ID 9자리
- Service ID 11자리
- Handler `serviceIds()` 일치
- DTO 이름 = Service ID + DTOin/out
- DAO FQCN = Mapper namespace
- DAO method = XML statement id

---

# 11. 완료 기준 Definition of Done

Sprint 01 완료는 아래 조건을 모두 충족해야 한다.

```text
Profile
Goal
Body Measurement
Body Timeline
      │
      ▼
HTML
→ ServiceId
→ Handler
→ Facade
→ Service
→ Rule
→ DAO
→ Mapper
→ DB
→ Test
```

- [ ] 전체 E2E 동작
- [ ] 오류 처리 검증
- [ ] 권한 검증
- [ ] SQL/Mapper 계약 검증
- [ ] 테스트 통과
- [ ] Traceability Registry 갱신
- [ ] Body Measurement History가 Append 방식으로 보존됨

---

# 12. 다음 Sprint 연계

Sprint 01의 User/Profile/Goal/BodyMeasurement 데이터는
Sprint 02의 Recovery / Assessment 입력이 된다.

```text
Sprint 01
Profile + Goal + Body
       ↓
Sprint 02
Check-in + Recovery + Readiness + Body Status
```
