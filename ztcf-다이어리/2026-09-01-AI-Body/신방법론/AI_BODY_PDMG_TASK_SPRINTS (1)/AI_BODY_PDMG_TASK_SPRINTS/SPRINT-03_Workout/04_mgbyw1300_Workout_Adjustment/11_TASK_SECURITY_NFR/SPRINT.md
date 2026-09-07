# 보안·비기능·운영 점검
# mgbyw1300 Workout Adjustment

- 실행 순서: 11 / 12
- 상태: READY
- 원칙: 이 문서를 한 번에 하나씩 실행한다.

## 대상 Program

| 항목 | 값 |
|---|---|
| Program ID | `mgbyw1300` |
| 업무 | Workout |
| 기능 | Workout Adjustment |
| Java Package Root | `nhnis.mg.by.w` |
| UI | `static/mgbyw1300/index.html` |
| 선행 Program | mgbyw1200, mgbya1200, mgbyu1100 |

### 대상 Service ID

- `mgbyw1300S0` : Workout Adjustment 조회
- `mgbyw1300A0` : Workout Adjustment 계산/생성

### 핵심 논리 Entity

- `WorkoutSession`
- `WorkoutSet`
- `BodyStatus`

### Rule / Algorithm 후보

- `WorkoutAdjustmentRule`
- `PlateauDetectionRule`


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

기능 완료 후가 아니라 Release 전에 반드시 보안·성능·운영 요구를 검증한다.

## 수행 TASK

1. Member는 본인 데이터만 접근 가능한지 확인한다.
2. Coach/Admin 범위가 필요한 Program이면 Scope를 검증한다.
3. 로그에 민감정보가 과도하게 남지 않는지 확인한다.
4. 입력값 검증/SQL Injection 방지 여부 확인
5. p95 3초 목표 관점의 느린 SQL/불필요 호출을 점검한다.
6. Timeout 계층을 침범하는 외부 호출이 있는지 확인한다.
7. AI가 없는 Program은 AI 장애와 무관하게 동작해야 한다.
8. MDC에 `guid/traceId/userId/serviceId/sqlId/errCode` 추적 가능성을 확인한다.
9. 삭제/보존 정책 관련 데이터면 별도 표시한다.
10. 운영 모니터링 Metric 후보를 정리한다.

## 산출물

`artifacts/11_SECURITY_NFR_REVIEW.md`


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

`12_TASK_CONFORMANCE_RELEASE/SPRINT.md`
