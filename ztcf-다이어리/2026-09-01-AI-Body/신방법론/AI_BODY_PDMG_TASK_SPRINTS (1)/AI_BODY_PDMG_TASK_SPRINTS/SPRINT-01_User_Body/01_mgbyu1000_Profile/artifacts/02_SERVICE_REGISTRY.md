# mgbyu1000 Profile Service Registry

- 문서 ID: `BY-REG-MGBYU1000`
- Program ID: `mgbyu1000`
- 업무영역: User / Profile
- 작성 단계: `02_TASK_PROGRAM_SERVICE`
- 상태: `BASELINE`
- 기준일: 2026-09-01
- 입력 산출물: `artifacts/01_REQUIREMENTS.md`
- 다음 단계: `03_TASK_ARCHITECTURE`

---

# 1. 목적

TASK 01에서 확정한 Profile 요구사항을
PDMG Runtime Contract인 Program ID / Service ID와
DTO / Java Type / Mapper / SQL ID / UI Transaction Key에 연결한다.

이 문서가 확정되면 후속 TASK에서는 아래 식별자를 임의 변경하지 않는다.

```text
Requirement
    ↓
Program ID
    ↓
Service ID
    ↓
UI Transaction Key
    ↓
DTO
    ↓
Handler
    ↓
Facade
    ↓
Service
    ↓
DAO
    ↓
Mapper
    ↓
SQL ID
```

---

# 2. Program ID 계약

## 2.1 확정값

```text
대구분       mg
업무구분     by
세부업무     u
프로그램번호 1000
             │
             ▼
Program ID   mgbyu1000
```

| 항목 | 값 | 검증 | 상태 |
|---|---|---|---|
| 대구분 | `mg` | 2자리 소문자 | BASELINE |
| 업무구분 | `by` | 2자리 소문자 | BASELINE |
| 세부업무 | `u` | User 코드 | BASELINE |
| 프로그램번호 | `1000` | 4자리 숫자 | BASELINE |
| Program ID | `mgbyu1000` | 9자리 | BASELINE |

**결정:** `TBD-PRF-001`은 본 개발 Baseline 범위에서 종료한다.

> 조직 차원의 별도 Application Registry 승인 프로세스가 존재하는 경우
> 그것은 배포/거버넌스 Gate에서 추가 확인한다.
> 후속 개발 설계에서는 `mgbyu1000`을 고정값으로 사용한다.

---

# 3. Service ID 계약

PDMG Service ID:

```text
[Program ID 9][거래구분 1][순번 1]
```

Profile은 조회/최초등록/수정 3개 거래를 사용한다.

| Service ID | 거래구분 | 순번 | 업무명 | 상태 |
|---|---|---:|---|---|
| `mgbyu1000S0` | `S` 조회 | `0` | 내 Profile 조회 | BASELINE |
| `mgbyu1000C0` | `C` 등록 | `0` | 최초 Profile 등록 | BASELINE |
| `mgbyu1000U0` | `U` 수정 | `0` | Profile 수정 | BASELINE |

**결정:** `TBD-PRF-002`는 본 개발 Baseline 범위에서 종료한다.

현재 Profile 삭제 거래 `D0`는 만들지 않는다.
삭제/탈퇴는 개인정보 Retention 정책과 함께 별도 결정한다.

---

# 4. Program 단위 Type 계약

3개의 Service ID를 사용하지만 Java 업무 Type은 Program 단위로 하나씩 둔다.

```text
mgbyu1000S0 ┐
mgbyu1000C0 ├──> mgbyu1000Handler
mgbyu1000U0 ┘    mgbyu1000Facade
                  mgbyu1000Service
                  mgbyu1000DAO
                  mgbyu1000-ORA.xml
```

## 확정 Type

| 역할 | Type / File | 상태 |
|---|---|---|
| Handler | `mgbyu1000Handler` | BASELINE |
| Facade | `mgbyu1000Facade` | BASELINE |
| Service | `mgbyu1000Service` | BASELINE |
| DAO | `mgbyu1000DAO` | BASELINE |
| Mapper | `mgbyu1000-ORA.xml` | BASELINE |
| Controller | `mgbyu1000Controller` | TCF OFF 호환이 실제 필요한 경우만 |
| Rule | `ProfileValidationRule` | 필요성은 상세설계에서 확정 |

금지:

```text
mgbyu1000S0Handler
mgbyu1000C0Facade
mgbyu1000U0Service
mgbyu1000S0DAO
```

---

# 5. Package 계약

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
│  ├─ service
│  │  └─ mgbyu1000Service
│  └─ rule
│     └─ ProfileValidationRule        # 필요 시
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

# 6. DTO 이름 예약

TASK 02에서는 DTO **이름만 계약**한다.
실제 필드/타입/Validation은 TASK 05에서 확정한다.

| Service ID | Input DTO | Output DTO | 상태 |
|---|---|---|---|
| `mgbyu1000S0` | `mgbyu1000S0DTOin` | `mgbyu1000S0DTOout` | BASELINE |
| `mgbyu1000C0` | `mgbyu1000C0DTOin` | `mgbyu1000C0DTOout` | BASELINE |
| `mgbyu1000U0` | `mgbyu1000U0DTOin` | `mgbyu1000U0DTOout` | BASELINE |

목록 Sub DTO가 필요해질 경우:

```text
mgbyu1000S0DTOSub0
```

Profile 단건 조회가 기본이므로 현재 Sub DTO 생성은 `TBD/Not Required Yet`이다.

---

# 7. DAO / Mapper / SQL ID 계약

## 7.1 DAO FQCN

```text
nhnis.mg.by.u.persistence.dao.mgbyu1000DAO
```

## 7.2 Mapper namespace

```xml
<mapper namespace="nhnis.mg.by.u.persistence.dao.mgbyu1000DAO">
```

강제 계약:

```text
DAO FQCN
=
Mapper namespace
```

## 7.3 SQL Statement ID 예약

| Service ID | DAO Method | Mapper Statement ID | DML 역할 | 상태 |
|---|---|---|---|---|
| `mgbyu1000S0` | `mgbyu1000S0_S0` | `mgbyu1000S0_S0` | SELECT | BASELINE |
| `mgbyu1000C0` | `mgbyu1000C0_C0` | `mgbyu1000C0_C0` | INSERT | BASELINE |
| `mgbyu1000U0` | `mgbyu1000U0_U0` | `mgbyu1000U0_U0` | UPDATE | BASELINE |

강제 계약:

```text
DAO Method
=
Mapper Statement ID
```

물리 Table/Column은 TASK 04 이전에 임의 확정하지 않는다.

---

# 8. UI Transaction 계약

## 8.1 UI Path

```text
pdmg-ui/src/main/resources/static/mgbyu1000/index.html
```

## 8.2 Program Key

```html
data-program-id="mgbyu1000"
```

## 8.3 Default Transaction

Profile 화면 진입 시 본인 Profile 조회가 기본 Use Case이므로:

```html
data-default-transaction-id="mgbyu1000S0"
```

**결정:** UI 기본 거래는 `mgbyu1000S0`.

## 8.4 거래 호출

```text
POST /mgbyu1000S0
POST /mgbyu1000C0
POST /mgbyu1000U0
```

PDMG 표준 전문:

```text
hdr_nhnis + dto
```

---

# 9. Requirement ↔ Service Traceability

## 9.1 Primary Mapping

| Requirement ID | Service ID | 관계 | 설명 | 상태 |
|---|---|---|---|---|
| `BY-USR-PRF-001` | `mgbyu1000S0` | PRIMARY | 본인 Profile 조회 | BASELINE |
| `BY-USR-PRF-002` | `mgbyu1000C0` | PRIMARY | 최초 Profile 등록 | BASELINE |
| `BY-USR-PRF-003` | `mgbyu1000U0` | PRIMARY | Profile 수정 | BASELINE |
| `BY-USR-PRF-004` | `mgbyu1000C0/U0` | SUPPORT | 운동경력 등록/수정 | BASELINE |
| `BY-USR-PRF-005` | `mgbyu1000C0/U0` | SUPPORT | 활동수준 등록/수정 | BASELINE |
| `BY-USR-PRF-006` | `mgbyu1000S0` | SUPPORT | 후속 Domain용 Profile 조회 Context | BASELINE |

## 9.2 Cross-Cutting Mapping

| Requirement ID | 적용 Service | 구분 | 상태 |
|---|---|---|---|
| `BY-USR-PRF-007` | `S0/C0/U0` | 본인/권한 Scope | BASELINE |
| `BY-USR-PRF-008` | 직접 Service 매핑 없음 | AI Context 최소화 원칙 | BASELINE |
| `BY-USR-PRF-009` | `S0/C0/U0` | 민감정보 Log 최소화 | BASELINE |
| `BY-USR-PRF-010` | 직접 Service 매핑 없음 | 삭제/Retention 정책 | TBD |

---

# 10. 최종 Service Registry

| Requirement ID | Program ID | Service ID | Transaction Type | Service Name | UI | DTO In | DTO Out | Handler | Facade | Service | DAO | Mapper | SQL ID | Status |
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| `BY-USR-PRF-001,006` | `mgbyu1000` | `mgbyu1000S0` | `S` 조회 | 내 Profile 조회 | `static/mgbyu1000/index.html` | `mgbyu1000S0DTOin` | `mgbyu1000S0DTOout` | `mgbyu1000Handler` | `mgbyu1000Facade` | `mgbyu1000Service` | `mgbyu1000DAO` | `mgbyu1000-ORA.xml` | `mgbyu1000S0_S0` | BASELINE |
| `BY-USR-PRF-002,004,005` | `mgbyu1000` | `mgbyu1000C0` | `C` 등록 | 최초 Profile 등록 | `static/mgbyu1000/index.html` | `mgbyu1000C0DTOin` | `mgbyu1000C0DTOout` | `mgbyu1000Handler` | `mgbyu1000Facade` | `mgbyu1000Service` | `mgbyu1000DAO` | `mgbyu1000-ORA.xml` | `mgbyu1000C0_C0` | BASELINE |
| `BY-USR-PRF-003,004,005` | `mgbyu1000` | `mgbyu1000U0` | `U` 수정 | Profile 수정 | `static/mgbyu1000/index.html` | `mgbyu1000U0DTOin` | `mgbyu1000U0DTOout` | `mgbyu1000Handler` | `mgbyu1000Facade` | `mgbyu1000Service` | `mgbyu1000DAO` | `mgbyu1000-ORA.xml` | `mgbyu1000U0_U0` | BASELINE |

`BY-USR-PRF-007`과 `BY-USR-PRF-009`는 세 거래 전체에 적용되는 Cross-Cutting Requirement다.

---

# 11. End-to-End Architecture Key

## S0

```text
mgbyu1000S0
 ├─ UI data-default-transaction-id
 ├─ POST /mgbyu1000S0
 ├─ Handler.serviceIds()
 ├─ mgbyu1000S0DTOin
 ├─ mgbyu1000S0DTOout
 ├─ mgbyu1000Facade.mgbyu1000S0()
 ├─ mgbyu1000Service.mgbyu1000S0()
 ├─ mgbyu1000DAO.mgbyu1000S0_S0()
 ├─ Mapper id="mgbyu1000S0_S0"
 └─ MDC serviceId
```

## C0

```text
mgbyu1000C0
 ├─ POST /mgbyu1000C0
 ├─ Handler.serviceIds()
 ├─ mgbyu1000C0DTOin/out
 ├─ mgbyu1000Facade.mgbyu1000C0()
 ├─ mgbyu1000Service.mgbyu1000C0()
 ├─ mgbyu1000DAO.mgbyu1000C0_C0()
 └─ Mapper id="mgbyu1000C0_C0"
```

## U0

```text
mgbyu1000U0
 ├─ POST /mgbyu1000U0
 ├─ Handler.serviceIds()
 ├─ mgbyu1000U0DTOin/out
 ├─ mgbyu1000Facade.mgbyu1000U0()
 ├─ mgbyu1000Service.mgbyu1000U0()
 ├─ mgbyu1000DAO.mgbyu1000U0_U0()
 └─ Mapper id="mgbyu1000U0_U0"
```

---

# 12. 자동 검증 결과

| 검증 항목 | 결과 |
|---|---|
| Program ID `mgbyu1000` 길이 9 | PASS |
| Program ID 업무축 `mg/by/u` | PASS |
| `mgbyu1000S0` 길이 11 | PASS |
| `mgbyu1000C0` 길이 11 | PASS |
| `mgbyu1000U0` 길이 11 | PASS |
| 거래구분 `S/C/U` 허용코드 | PASS |
| Program Type에 거래코드 미포함 | PASS |
| DTO에 Service ID 전체 포함 | PASS |
| DAO FQCN / Mapper namespace 계약 예약 | PASS |
| DAO Method / Mapper ID 계약 예약 | PASS |
| Registry 초안 내 Service ID 중복 | PASS — 현재 Registry의 98개 Service ID가 모두 Unique |
| 대상 `S0/C0/U0` Registry 존재성 | PASS — 각각 1건 |

---

# 13. 결정사항

TASK 02에서 다음을 고정한다.

1. Program ID = `mgbyu1000`
2. Service ID = `mgbyu1000S0`, `mgbyu1000C0`, `mgbyu1000U0`
3. Profile 삭제 `D0`는 현재 생성하지 않는다.
4. Program Type은 `mgbyu1000Handler/Facade/Service/DAO`
5. DTO 이름은 Service ID 전체 사용
6. Mapper 파일 = `mgbyu1000-ORA.xml`
7. DAO FQCN = Mapper namespace
8. SQL ID = `S0_S0`, `C0_C0`, `U0_U0` 계약
9. UI Path = `static/mgbyu1000/index.html`
10. UI default transaction = `mgbyu1000S0`
11. 다음 TASK부터 위 Architecture Key를 임의 변경하지 않는다.

---

# 14. 종료/이관된 TBD

| TBD ID | 처리 |
|---|---|
| `TBD-PRF-001` Program 번호 | `mgbyu1000`으로 BASELINE 확정 |
| `TBD-PRF-002` Service 목록 | `S0/C0/U0`으로 BASELINE 확정 |

---

# 15. 계속 유지하는 TBD

| TBD ID | 내용 | 다음 처리 |
|---|---|---|
| `TBD-PRF-003` | Profile 없음 조회 시 Empty vs 업무오류 | TASK 03/05 |
| `TBD-PRF-004` | 사용자 식별값 JWT/SSO/Header 취득 위치 | TASK 03/05 |
| `TBD-PRF-005` | userId Client DTO 포함 여부 | TASK 05 |
| `TBD-PRF-006` | Profile 필드 최종 목록 | TASK 04/05 |
| `TBD-PRF-007` | trainingExperience 코드값 | TASK 04/05 |
| `TBD-PRF-008` | activityLevel 코드값 | TASK 04/05 |
| `TBD-PRF-009` | Profile 중복 판단 기준 | TASK 04 |
| `TBD-PRF-010` | 탈퇴/삭제 Retention | TASK 11 |
| `TBD-PRF-011` | Coach 직접 S0 접근 여부 | TASK 03/11 |
| `TBD-PRF-012` | Admin 직접 수정 여부 | TASK 03/11 |
| `TBD-PRF-013` | 암호화/마스킹 필드 | TASK 04/11 |

---

# 16. 생성/수정 파일

## 생성

```text
artifacts/02_SERVICE_REGISTRY.md
```

## 수정

```text
02_TASK_PROGRAM_SERVICE/SPRINT.md
```

---

# 17. 테스트 결과

이번 단계는 Runtime Contract 정적 검증을 수행했다.

```text
Program ID Format              PASS
Service ID Format              PASS
Transaction Code               PASS
Service ID Duplicate           PASS
Program Type Naming            PASS
DTO Naming Reservation         PASS
Mapper Naming Reservation      PASS
DAO/Mapper Contract            PASS
Requirement Traceability       PASS
UI Default Transaction         PASS
```

실제 Java/DB 실행 테스트는 아직 수행 대상이 아니다.

---

# 18. 다음 TASK 입력

`03_TASK_ARCHITECTURE/SPRINT.md`는 아래를 고정 입력으로 사용한다.

```text
Program
mgbyu1000

Services
mgbyu1000S0
mgbyu1000C0
mgbyu1000U0

UI
static/mgbyu1000/index.html
default = mgbyu1000S0

Java
nhnis.mg.by.u.entry.handler.mgbyu1000Handler
nhnis.mg.by.u.application.facade.mgbyu1000Facade
nhnis.mg.by.u.application.service.mgbyu1000Service
nhnis.mg.by.u.persistence.dao.mgbyu1000DAO

Mapper
mapper/rdw/mg/by/u/mgbyu1000-ORA.xml

Namespace
nhnis.mg.by.u.persistence.dao.mgbyu1000DAO

SQL IDs
mgbyu1000S0_S0
mgbyu1000C0_C0
mgbyu1000U0_U0

Protocol
hdr_nhnis + dto

Security
Member SELF

Open TBD
TBD-PRF-003 ~ TBD-PRF-013
```

---

# 19. TASK 02 완료 게이트

- [x] Program ID 9자리 규칙 검증
- [x] Service ID 11자리 규칙 검증
- [x] 거래구분 S/C/U 검증
- [x] Service ID 중복 검증
- [x] DTO 이름 예약
- [x] SQL ID 예약
- [x] UI Default Transaction 확정
- [x] Requirement ↔ Service ID Traceability 작성
- [x] Program 단위 Type 계약 확인
- [x] 다음 Architecture TASK 입력 확정

**TASK 02 상태: COMPLETE**
