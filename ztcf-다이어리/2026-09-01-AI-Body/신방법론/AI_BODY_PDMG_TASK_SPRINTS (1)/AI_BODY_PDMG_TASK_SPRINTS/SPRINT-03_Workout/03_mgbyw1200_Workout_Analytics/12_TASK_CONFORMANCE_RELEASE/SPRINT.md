# 표준 적합성·완료검증·Baseline
# mgbyw1200 Workout Analytics

- 실행 순서: 12 / 12
- 상태: READY
- 원칙: 이 문서를 한 번에 하나씩 실행한다.

## 대상 Program

| 항목 | 값 |
|---|---|
| Program ID | `mgbyw1200` |
| 업무 | Workout |
| 기능 | Workout Analytics |
| Java Package Root | `nhnis.mg.by.w` |
| UI | `static/mgbyw1200/index.html` |
| 선행 Program | mgbyw1100, mgbya1200 |

### 대상 Service ID

- `mgbyw1200S0` : Workout Analytics 조회
- `mgbyw1200R0` : Workout Analytics 리포트

### 핵심 논리 Entity

- `WorkoutSession`
- `WorkoutSet`

### Rule / Algorithm 후보

- `WorkoutVolumeCalculator`
- `WorkoutComplianceCalculator`
- `WorkoutPerformanceCalculator`
- `WorkoutTrendCalculator`


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

Program을 “코드가 존재함”이 아니라 “PDMG 서비스로 완료됨” 상태로 승격한다.

## 최종 Conformance

- [ ] Requirement ↔ Program ↔ Service ↔ UI ↔ DTO ↔ Java ↔ SQL ↔ DB ↔ Test 추적 가능
- [ ] Program ID / Service ID 규칙 준수
- [ ] 업무 클래스 AS-IS 소문자 시작 준수
- [ ] Handler는 Facade만 호출
- [ ] Facade가 Transaction Boundary
- [ ] Service가 Rule/DAO를 호출
- [ ] DAO/Mapper 계약 일치
- [ ] UI가 `_shared`와 Service ID 계약 사용
- [ ] 보안/권한/로그 점검 완료
- [ ] 테스트 통과
- [ ] 미결사항은 명시적으로 TBD 등록

## Definition of Done

```text
Requirement
→ Program/Service Registry
→ Architecture
→ Data Model
→ DTO Contract
→ Backend Design
→ UI Design
→ Backend Code
→ UI Code
→ Test
→ Security/NFR
→ Conformance
→ BASELINE
```

## 산출물

- `artifacts/12_FINAL_TRACEABILITY.md`
- `artifacts/12_RELEASE_CHECKLIST.md`
- `artifacts/12_DECISION_TBD.md`
- Program 상태를 `BASELINE` 또는 승인된 완료상태로 변경


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

Program 완료 / 상위 Sprint 다음 Program
