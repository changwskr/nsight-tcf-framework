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
