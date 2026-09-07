# AI Body PDMG 개발 착수 작업 02
# Sprint 1 초기 Application Registry v0.1

- 기준일: 2026-09-01
- 상태: PROPOSED
- 범위: User / Body 첫 Vertical Slice
- 목적: Requirement → Program → Service → UI → Java → SQL → DB → Test 추적성 확보

---

## 1. Sprint 1 범위

```text
Profile
  ↓
Goal
  ↓
Body Measurement
  ↓
Body Timeline
```

대상 Program:

```text
mgbyu1000
mgbyu1100
mgbyb1000
mgbyb1100
```

---

## 2. 통합 Registry

| Req ID | Domain | Program ID | Service ID | 거래 | 기능 | UI | DTO In | DTO Out | Handler | Facade | Service | DAO | SQL ID | Logical Entity | MVP | 상태 |
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| BY-USR-PRF-001 | User | `mgbyu1000` | `mgbyu1000S0` | S | Profile 조회 | `static/mgbyu1000/index.html` | `mgbyu1000S0DTOin` | `mgbyu1000S0DTOout` | `mgbyu1000Handler` | `mgbyu1000Facade` | `mgbyu1000Service` | `mgbyu1000DAO` | `mgbyu1000S0_S0` | UserProfile | Y | PROPOSED |
| BY-USR-PRF-002 | User | `mgbyu1000` | `mgbyu1000C0` | C | Profile 등록 | `static/mgbyu1000/index.html` | `mgbyu1000C0DTOin` | `mgbyu1000C0DTOout` | `mgbyu1000Handler` | `mgbyu1000Facade` | `mgbyu1000Service` | `mgbyu1000DAO` | `mgbyu1000C0_C0` | UserProfile | Y | PROPOSED |
| BY-USR-PRF-003 | User | `mgbyu1000` | `mgbyu1000U0` | U | Profile 수정 | `static/mgbyu1000/index.html` | `mgbyu1000U0DTOin` | `mgbyu1000U0DTOout` | `mgbyu1000Handler` | `mgbyu1000Facade` | `mgbyu1000Service` | `mgbyu1000DAO` | `mgbyu1000U0_U0` | UserProfile | Y | PROPOSED |
| BY-USR-GOL-001 | User | `mgbyu1100` | `mgbyu1100S0` | S | Goal 조회 | `static/mgbyu1100/index.html` | `mgbyu1100S0DTOin` | `mgbyu1100S0DTOout` | `mgbyu1100Handler` | `mgbyu1100Facade` | `mgbyu1100Service` | `mgbyu1100DAO` | `mgbyu1100S0_S0` | UserGoal | Y | PROPOSED |
| BY-USR-GOL-002 | User | `mgbyu1100` | `mgbyu1100C0` | C | Goal 등록 | `static/mgbyu1100/index.html` | `mgbyu1100C0DTOin` | `mgbyu1100C0DTOout` | `mgbyu1100Handler` | `mgbyu1100Facade` | `mgbyu1100Service` | `mgbyu1100DAO` | `mgbyu1100C0_C0` | UserGoal | Y | PROPOSED |
| BY-USR-GOL-003 | User | `mgbyu1100` | `mgbyu1100U0` | U | Goal 수정 | `static/mgbyu1100/index.html` | `mgbyu1100U0DTOin` | `mgbyu1100U0DTOout` | `mgbyu1100Handler` | `mgbyu1100Facade` | `mgbyu1100Service` | `mgbyu1100DAO` | `mgbyu1100U0_U0` | UserGoal | Y | PROPOSED |
| BY-USR-GOL-004 | User | `mgbyu1100` | `mgbyu1100D0` | D | Goal 삭제/종료 | `static/mgbyu1100/index.html` | `mgbyu1100D0DTOin` | `mgbyu1100D0DTOout` | `mgbyu1100Handler` | `mgbyu1100Facade` | `mgbyu1100Service` | `mgbyu1100DAO` | `mgbyu1100D0_D0` | UserGoal | Y | PROPOSED |
| BY-BOD-MSR-001 | Body | `mgbyb1000` | `mgbyb1000S0` | S | Body Measurement 조회 | `static/mgbyb1000/index.html` | `mgbyb1000S0DTOin` | `mgbyb1000S0DTOout` | `mgbyb1000Handler` | `mgbyb1000Facade` | `mgbyb1000Service` | `mgbyb1000DAO` | `mgbyb1000S0_S0` | BodyMeasurement | Y | PROPOSED |
| BY-BOD-MSR-002 | Body | `mgbyb1000` | `mgbyb1000C0` | C | Body Measurement 등록 | `static/mgbyb1000/index.html` | `mgbyb1000C0DTOin` | `mgbyb1000C0DTOout` | `mgbyb1000Handler` | `mgbyb1000Facade` | `mgbyb1000Service` | `mgbyb1000DAO` | `mgbyb1000C0_C0` | BodyMeasurement | Y | PROPOSED |
| BY-BOD-TML-001 | Body | `mgbyb1100` | `mgbyb1100S0` | S | Body Timeline 조회 | `static/mgbyb1100/index.html` | `mgbyb1100S0DTOin` | `mgbyb1100S0DTOout` | `mgbyb1100Handler` | `mgbyb1100Facade` | `mgbyb1100Service` | `mgbyb1100DAO` | `mgbyb1100S0_S0` | TimelineEvent / Aggregated View | Y | PROPOSED |

---

## 3. Mapper 파일

```text
rdw/mg/by/u/mgbyu1000-ORA.xml
rdw/mg/by/u/mgbyu1100-ORA.xml
rdw/mg/by/b/mgbyb1000-ORA.xml
rdw/mg/by/b/mgbyb1100-ORA.xml
```

---

## 4. Requirement ID 정책

위 `BY-*` Requirement ID는 추적성을 시작하기 위한 Working ID이며 정식 요구사항 관리도구/프로젝트 표준 ID가 정해지면 매핑한다.

```text
BY-USR-PRF-xxx  User / Profile
BY-USR-GOL-xxx  User / Goal
BY-BOD-MSR-xxx  Body / Measurement
BY-BOD-TML-xxx  Body / Timeline
```

---

## 5. 물리 DB 결정 전 금지

다음 항목은 현재 Registry에서 임의 확정하지 않는다.

- 실제 Table Name
- PK/Sequence
- Schema
- DBMS
- Column 물리명
- Encryption Column
- Timeline Materialization 방식

Logical Entity까지만 연결한다.

---

## 6. Sprint 1 첫 구현 순서

```text
1. mgbyu1000 Profile
2. mgbyu1100 Goal
3. mgbyb1000 Body Measurement
4. mgbyb1100 Body Timeline
```

각 Program은 아래 Gate를 통과해야 다음 Program으로 이동한다.

```text
UI
→ Service ID
→ Handler
→ Facade
→ Service
→ DAO
→ Mapper
→ DB
→ Integration Test
```

---

## 7. 승인 전 체크

- [ ] Program 번호 1000/1100 승인
- [ ] Service ID 거래 목록 승인
- [ ] Requirement ID 정책 승인/매핑
- [ ] Profile 물리 테이블/컬럼 승인
- [ ] Goal 물리 테이블/컬럼 승인
- [ ] BodyMeasurement 물리 테이블/컬럼 승인
- [ ] Timeline 구현 방식 승인
- [ ] DTO 필드 승인
- [ ] Member 본인 데이터 접근 정책 반영
