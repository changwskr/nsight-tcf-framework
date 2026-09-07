# AI Body PDMG 개발 착수 작업 01
# PDMG 샘플 거래 End-to-End 분석 및 BY 적용 패턴

- 문서 버전: v0.1
- 기준일: 2026-09-01
- 상태: BASELINE WORKING
- 목적: Sprint 0의 "샘플 PDMG 거래 1건 End-to-End 분석" 완료 및 BY 첫 프로그램 적용 패턴 확정
- 적용 Backend: `pdmg-service`
- 적용 UI: `pdmg-ui`
- BY Java Root: `nhnis.mg.by`

---

## 1. 기준 우선순위

본 작업은 다음 우선순위를 적용한다.

1. AI Body 플랫폼 요구사항/설계 Baseline
2. PDMG 실제 소스 및 실행구조
3. PDMG Java 네이밍 및 코딩 표준
4. BY 업무코드/Program/Service Registry 초안
5. 일반 Java/Spring 관례

충돌 시 PDMG 실제 구조와 AS-IS 계약을 우선한다.

---

## 2. 상태 표기

| 표기 | 의미 |
|---|---|
| `FACT` | PDMG 실제 소스/정본 표준에서 확인 |
| `BASELINE` | 현재 프로젝트에서 적용하기로 확정 |
| `PROPOSED` | 구현 전 승인 필요 |
| `TBD` | 아직 결정되지 않음 |

---

## 3. PDMG 대표 샘플 거래

PDMG의 대표 End-to-End 추적 거래로 `mgcoa9000S0`을 사용한다.

```text
Program ID   : mgcoa9000
Service ID   : mgcoa9000S0
Package Root : nhnis.mg.co.a

TCF ON
Browser / Client
    ↓
DefaultFilter
    ↓
OnlineTransactionController
    ↓
TransactionHandler Registry
    ↓
mgcoa9000Handler
    ↓
mgcoa9000Facade.mgcoa9000S0()
    ↓
mgcoa9000Service.mgcoa9000S0()
    ↓
mgcoa9000DAO.mgcoa9000S0_S0()
    ↓
mgcoa9000-ORA.xml
    ↓
Mapper statement id="mgcoa9000S0_S0"
    ↓
DB
```

### 3.1 샘플 거래 Naming Contract

| 구분 | PDMG 실제 패턴 |
|---|---|
| Program ID | `mgcoa9000` |
| Service ID | `mgcoa9000S0` |
| Handler | `mgcoa9000Handler` |
| Facade | `mgcoa9000Facade` |
| Service | `mgcoa9000Service` |
| DAO | `mgcoa9000DAO` |
| DTO In | `mgcoa9000S0DTOin` |
| DTO Out | `mgcoa9000S0DTOout` |
| DTO Sub | `mgcoa9000S0DTOSub0` |
| Mapper File | `mgcoa9000-ORA.xml` |
| DAO/Mapper Statement | `mgcoa9000S0_S0` |
| Mapper namespace | DAO FQCN과 완전 일치 |
| UI/URL Transaction Key | Service ID 전체 문자열 |

---

## 4. BY 프로젝트로 변환하는 규칙

PDMG 공통관리 샘플의 `mg/co/a/9000` 패턴을 BY 사용자 Profile 프로그램에 그대로 투영한다.

```text
PDMG Sample
mg + co + a + 9000
      ↓
mgcoa9000

BY Profile
mg + by + u + 1000
      ↓
mgbyu1000
```

### 4.1 BY Profile Program

| 항목 | 값 | 상태 |
|---|---|---|
| 대구분 | `mg` | BASELINE |
| 업무 | `by` | BASELINE |
| 세부업무 | `u` | BASELINE |
| 프로그램번호 | `1000` | PROPOSED |
| Program ID | `mgbyu1000` | PROPOSED |
| 기능 | Profile | BASELINE |
| UI Path | `static/mgbyu1000/index.html` | PROPOSED |
| Package Root | `nhnis.mg.by.u` | BASELINE |

---

## 5. 첫 Program의 Service ID

기존 Registry 초안에 따라 Profile은 조회/등록/수정 3개 거래를 둔다.

| Service ID | 거래 | 용도 | 상태 |
|---|---|---|---|
| `mgbyu1000S0` | 조회 | 내 Profile 조회 | PROPOSED |
| `mgbyu1000C0` | 등록 | 최초 Profile 등록 | PROPOSED |
| `mgbyu1000U0` | 수정 | Profile 변경 | PROPOSED |

Program 단위 클래스에는 `S0/C0/U0`를 붙이지 않는다.

```text
mgbyu1000Handler
mgbyu1000Facade
mgbyu1000Service
mgbyu1000DAO
mgbyu1000Controller   # TCF OFF 호환이 실제로 필요할 때만
mgbyu1000-ORA.xml
```

---

## 6. 패키지 구조

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

Mapper:

```text
src/main/resources/
└─ mapper/
   └─ rdw/
      └─ mg/
         └─ by/
            └─ u/
               └─ mgbyu1000-ORA.xml
```

---

## 7. End-to-End Traceability

### 7.1 Profile 조회

```text
pdmg-ui
static/mgbyu1000/index.html
    │
    │ data-program-id="mgbyu1000"
    │ data-default-transaction-id="mgbyu1000S0"
    ▼
POST /mgbyu1000S0
hdr_nhnis + dto
    ▼
mgbyu1000Handler
    │ serviceIds(): mgbyu1000S0, C0, U0
    ▼
mgbyu1000Facade.mgbyu1000S0()
    ▼
mgbyu1000Service.mgbyu1000S0()
    ▼
mgbyu1000DAO.mgbyu1000S0_S0()
    ▼
namespace = nhnis.mg.by.u.persistence.dao.mgbyu1000DAO
id = mgbyu1000S0_S0
    ▼
USER_PROFILE (논리명)
```

### 7.2 Profile 등록

```text
mgbyu1000C0
    ↓
mgbyu1000Handler
    ↓
mgbyu1000Facade.mgbyu1000C0()
    ↓
mgbyu1000Service.mgbyu1000C0()
    ↓
Profile Validation
    ↓
mgbyu1000DAO.mgbyu1000C0_C0()
    ↓
mgbyu1000-ORA.xml / mgbyu1000C0_C0
    ↓
USER_PROFILE (논리명)
```

### 7.3 Profile 수정

```text
mgbyu1000U0
    ↓
mgbyu1000Handler
    ↓
mgbyu1000Facade.mgbyu1000U0()
    ↓
mgbyu1000Service.mgbyu1000U0()
    ↓
Profile Validation
    ↓
mgbyu1000DAO.mgbyu1000U0_U0()
    ↓
mgbyu1000-ORA.xml / mgbyu1000U0_U0
    ↓
USER_PROFILE (논리명)
```

---

## 8. DAO / Mapper 계약

다음은 반드시 자동검증 대상으로 둔다.

```text
DAO FQCN
=
Mapper namespace
```

```text
DAO Method
=
Mapper Statement ID
```

예:

```text
DAO
nhnis.mg.by.u.persistence.dao.mgbyu1000DAO

Mapper
<mapper namespace="nhnis.mg.by.u.persistence.dao.mgbyu1000DAO">
```

```text
DAO method            Mapper id
mgbyu1000S0_S0   =    mgbyu1000S0_S0
mgbyu1000C0_C0   =    mgbyu1000C0_C0
mgbyu1000U0_U0   =    mgbyu1000U0_U0
```

---

## 9. Facade Transaction Boundary

PDMG AS-IS 원칙에 따라 Transaction Boundary는 Facade에서 관리한다.

```text
조회 S0
@Transactional(
    transactionManager = "rdwTransactionManager",
    readOnly = true
)

등록/수정
@Transactional(
    transactionManager = "rdwTransactionManager",
    rollbackFor = Exception.class
)
```

실제 Transaction Manager 및 Timeout 값은 PDMG 운영 설정을 확인한 뒤 고정한다.

---

## 10. DTO 초안

물리 필드는 아직 확정하지 않고 요구사항 기반 논리 필드만 정의한다.

### mgbyu1000S0DTOin

```text
userId
```

### mgbyu1000S0DTOout

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

### mgbyu1000C0DTOin

```text
displayName
birthDate
gender
height
trainingExperience
activityLevel
```

### mgbyu1000U0DTOin

```text
displayName
height
trainingExperience
activityLevel
```

주의:
- 필드 최종 확정은 개인정보 최소수집 정책과 DB 물리모델 승인 후 진행한다.
- `userId`를 클라이언트가 임의 지정하는 구조인지 인증 Context에서 취득하는지는 인증 설계 확인 후 확정한다.

---

## 11. DB 경계

현재 확정 가능한 것은 논리 Entity `UserProfile`까지다.

```text
Logical Entity : UserProfile
Physical Table : TBD
PK Strategy    : TBD
Schema         : TBD
DBMS           : TBD
Encryption     : TBD
```

따라서 첫 코드 생성 전 DB Physical Naming Gate가 필요하다.

---

## 12. UI 구현 패턴

```html
<html lang="ko"
      data-program-id="mgbyu1000"
      data-default-transaction-id="mgbyu1000S0">
```

공통 자원:

```text
/_shared/ui-context.js
/_shared/online.css
/_shared/error-codes.js
/_shared/error-popup.js
/_shared/service-client.js
/_shared/online-single.js
```

화면 전용 로직은 우선 `index.html` 내부의 소규모 script로 두고, 재사용 필요가 생길 때만 `_shared`로 승격한다.

---

## 13. 테스트 기준

### Naming Contract Test

- Program ID = 9자리
- Service ID = 11자리
- Service ID 중복 금지
- Program Prefix와 Package 업무축 일치
- Handler 등록 Service ID와 Registry 일치
- DTO 이름에 Service ID 전체 포함
- DAO FQCN = Mapper namespace
- DAO Method = Mapper Statement ID

### Integration Test

```text
UI
→ ServiceId
→ Handler
→ Facade
→ Service
→ DAO
→ Mapper
→ DB
```

### Security Test

- 본인 Profile만 조회/수정
- 다른 Member의 userId로 접근 불가
- 민감 필드 로그 노출 금지

---

## 14. 이번 작업에서 확정한 구현 패턴

```text
[BASELINE]
BY 업무 Root = nhnis.mg.by
업무 타입 = 소문자 Program ID + 역할 접미사
Facade 위치 = application.facade
Service ID = 11자리
DTO = Service ID + DTOin/DTOout
DAO = Program 단위
Mapper = Program 단위 -ORA.xml
DAO FQCN = Mapper namespace
DAO Method = Mapper statement id
UI = static/{ProgramId}/index.html
Protocol = hdr_nhnis + dto
Transaction = Facade Boundary
```

---

## 15. 다음 Gate

다음 작업은 `mgbyu1000` 코드를 바로 생성하는 것이 아니라 아래 4개를 먼저 고정한다.

1. `mgbyu1000 / mgbyu1100 / mgbyb1000 / mgbyb1100` Program 번호 승인
2. 각 Program의 Service ID 승인
3. User/Profile/Goal/BodyMeasurement의 DB 물리 Naming
4. Requirement → Program → Service → UI → DTO → Table 통합 Registry

이 Gate 완료 후 첫 실제 코드 Vertical Slice를 생성한다.
