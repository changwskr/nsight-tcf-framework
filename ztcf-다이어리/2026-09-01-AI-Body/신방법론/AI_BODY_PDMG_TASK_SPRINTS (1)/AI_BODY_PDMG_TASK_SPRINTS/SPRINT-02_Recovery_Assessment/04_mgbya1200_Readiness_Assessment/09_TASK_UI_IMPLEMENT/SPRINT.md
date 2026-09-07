# UI 구현
# mgbya1200 Readiness Assessment

- 실행 순서: 09 / 12
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

Backend Service ID와 연결되는 `pdmg-ui` 화면을 구현한다.

## 수행 TASK

1. `static/{ProgramId}/index.html` 생성/수정
2. `_shared/ui-context.js` 등 공통 리소스 재사용
3. Service ID 호출 연결
4. `hdr_nhnis + dto` 요청 생성
5. 결과 바인딩
6. 오류 Popup/표준 오류 처리 연결
7. 입력 Validation
8. Mobile viewport/사용성 확인
9. 민감정보 노출 점검

## 금지

- 일반 React/Next 프로젝트를 새로 만들지 않는다.
- 화면별 공통 기능을 중복 JS로 복제하지 않는다.
- 임의 REST URL을 새로 만들지 않는다.

## 산출물

실제 `pdmg-ui` 소스 변경 + `artifacts/09_UI_IMPLEMENTATION_LOG.md`


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

`10_TASK_TEST/SPRINT.md`
