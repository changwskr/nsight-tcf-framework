# Program / Service ID 계약 확정
# mgbyr1100 Sleep Recovery

- 실행 순서: 02 / 12
- 상태: READY
- 원칙: 이 문서를 한 번에 하나씩 실행한다.

## 대상 Program

| 항목 | 값 |
|---|---|
| Program ID | `mgbyr1100` |
| 업무 | Recovery |
| 기능 | Sleep Recovery |
| Java Package Root | `nhnis.mg.by.r` |
| UI | `static/mgbyr1100/index.html` |
| 선행 Program | mgbyr1000 |

### 대상 Service ID

- `mgbyr1100S0` : Sleep/Recovery 조회
- `mgbyr1100C0` : Sleep/Recovery 등록

### 핵심 논리 Entity

- `Recovery`

### Rule / Algorithm 후보

- `RecoveryValidationRule`


## 기준 자료

이 TASK는 다음 기준을 우선 적용한다.

1. `00_전체설계_마스터_인덱스.md`
2. `01_요구사항_기준선.md`
3. `02_PDMG_적용_전체아키텍처.md`
4. `03_BY_업무코드_및_네이밍_표준.md`
5. `04_Backend_패키지_및_거래구조.md`
6. `05_pdmg-ui_화면_아키텍처.md`
7. `06_Program_Service_Registry_초안.md`
8. `07_데이터_아키텍처.md`
9. `08_Rule_Algorithm_설계.md`
10. `09_AI_Coaching_아키텍처.md`
11. `10_보안_비기능_운영_설계.md`
12. `Java_네이밍_및_코딩_표준서.md`
13. 실제 PDMG 소스 (`pdmg-fw`, `pdmg-service`, `pdmg-ui`, `pdmg-jwt`, `pdmg-om`)

충돌 시 **실제 PDMG 소스와 승인된 PDMG AS-IS 표준을 우선**한다.


## 고정 PDMG 구현 규칙

```text
pdmg-ui
  → ServiceId + hdr_nhnis + dto
  → pdmg-fw / TCF
  → Handler
  → Facade
  → Service
  → Rule / Algorithm
  → DAO
  → MyBatis Mapper
  → DB
```

- Java 업무 Root: `nhnis.mg.by`
- 업무 Program Type: Program ID를 그대로 접두로 사용하며 **소문자 시작**
- Facade package: `application.facade`
- Transaction Boundary: Facade
- Transaction Manager: `rdwTransactionManager`
- DAO: PDMG MyBatis Mapper Interface 패턴
- Mapper: `[ProgramId]-ORA.xml`
- `DAO FQCN = Mapper namespace`
- `DAO Method = Mapper Statement ID`
- UI: `static/{ProgramId}/index.html`
- 공통 UI: `static/_shared/*` 우선 재사용
- Protocol: `ServiceId + hdr_nhnis + dto`
- 일반적인 REST/JPA/React 관례를 임의로 도입하지 않는다.


## 목표

요구사항을 PDMG Runtime Contract인 Program ID / Service ID로 고정한다.

## 수행 TASK

1. Program ID가 9자리 규칙에 맞는지 검증한다.
2. 각 거래를 S/C/U/D/A/R로 분류한다.
3. Service ID 11자리를 확정한다.
4. 한 Program에 여러 거래가 있어도 Handler/Facade/Service/DAO는 Program 단위임을 확인한다.
5. DTO 이름을 Service ID 기준으로 예약한다.
6. SQL ID prefix를 예약한다.
7. UI default transaction 후보를 정한다.
8. Requirement ↔ Service ID Traceability를 작성한다.
9. Service ID 중복을 검사한다.

## 산출물

`artifacts/02_SERVICE_REGISTRY.md`

필수 컬럼:

```text
Requirement ID
Program ID
Service ID
Transaction Type
Service Name
UI
DTO In
DTO Out
Handler
Facade
Service
DAO
Mapper
SQL ID
Status
```

## 자동 점검 기준

- Program ID = 9자리
- Service ID = 11자리
- 거래구분 = S/C/U/D/A/R
- Program Type에 Service 거래코드를 붙이지 않음


## 완료 시 반드시 남길 것

- 결정사항
- 미결사항(TBD)
- 변경된 Registry
- 생성/수정한 파일 목록
- 테스트 결과
- 다음 TASK가 알아야 할 입력값

## 완료 게이트

- [ ] 이 TASK의 산출물이 파일 또는 코드로 존재한다.
- [ ] FACT / BASELINE / PROPOSED / TBD가 구분되어 있다.
- [ ] PDMG 표준과 충돌하는 임의 설계를 하지 않았다.
- [ ] 다음 TASK가 추가 질문 없이 착수할 정도로 입력이 정리되었다.

## 다음 실행

완료 후 다음 파일을 피딩한다.

`03_TASK_ARCHITECTURE/SPRINT.md`
