# 논리/물리 데이터 설계
# mgbya1200 Readiness Assessment

- 실행 순서: 04 / 12
- 상태: READY
- 원칙: 이 문서를 한 번에 하나씩 실행한다.

## 대상 Program

| 항목 | 값 |
|---|---|
| Program ID | `mgbya1200` |
| 업무 | Assessment |
| 기능 | Readiness Assessment |
| Java Package Root | `nhnis.mg.by.a` |
| UI | `static/mgbya1200/index.html` |
| 선행 Program | mgbyr1000, mgbyr1100, mgbya1000 |

### 대상 Service ID

- `mgbya1200S0` : Readiness Assessment 조회
- `mgbya1200A0` : Readiness 계산/저장

### 핵심 논리 Entity

- `BodyStatus`
- `Recovery`

### Rule / Algorithm 후보

- `ReadinessCalculator`
- `RecoveryStateRule`
- `TrainingCautionRule`


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

요구사항과 거래를 만족하는 논리 데이터 모델을 확정하고,
PDMG 실제 DB 표준을 확인하여 물리 모델을 설계한다.

## 수행 TASK

1. 논리 Entity와 관계를 정의한다.
2. PK 후보를 정의하되 실제 PDMG PK/Sequence 정책을 먼저 확인한다.
3. Raw / Derived 데이터를 구분한다.
4. 시간축 데이터는 Append-oriented 여부를 판정한다.
5. 생성/수정/기준일/Source 등 감사성 필드를 검토한다.
6. 개인정보/민감정보 컬럼을 표시한다.
7. 조회 조건과 인덱스 후보를 도출한다.
8. 실제 PDMG DB Naming/Oracle 관례를 확인한 후 Table/Column을 PROPOSED 또는 확정한다.
9. Mapper에서 필요한 CRUD SQL 목록을 정의한다.

## 산출물

- `artifacts/04_LOGICAL_DATA_MODEL.md`
- `artifacts/04_PHYSICAL_DATA_MODEL.md`
- `artifacts/04_SQL_LIST.md`

## 주의

DBMS/Schema/PK/Sequence 정책이 기준자료에 없으면 임의 확정하지 말고 `TBD`로 남긴다.


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

`05_TASK_DTO_MESSAGE/SPRINT.md`
