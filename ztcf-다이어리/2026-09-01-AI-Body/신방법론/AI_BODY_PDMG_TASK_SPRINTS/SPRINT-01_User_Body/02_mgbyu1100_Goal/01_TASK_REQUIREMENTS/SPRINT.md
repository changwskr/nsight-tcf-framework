# 요구사항 정제 및 범위 확정
# mgbyu1100 Goal

- 실행 순서: 01 / 12
- 상태: READY
- 원칙: 이 문서를 한 번에 하나씩 실행한다.

## 대상 Program

| 항목 | 값 |
|---|---|
| Program ID | `mgbyu1100` |
| 업무 | User |
| 기능 | Goal |
| Java Package Root | `nhnis.mg.by.u` |
| UI | `static/mgbyu1100/index.html` |
| 선행 Program | mgbyu1000 |

### 대상 Service ID

- `mgbyu1100S0` : Goal 조회
- `mgbyu1100C0` : Goal 등록
- `mgbyu1100U0` : Goal 수정
- `mgbyu1100D0` : Goal 삭제/종료

### 핵심 논리 Entity

- `UserGoal`

### Rule / Algorithm 후보

- `GoalValidationRule (필요 시)`


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

해당 Program의 요구사항을 구현 가능한 수준으로 고정한다.
기능을 추가 설계하기 전에 **누가, 무엇을, 왜, 어떤 조건으로 수행하는지**를 확정한다.

## 수행 TASK

1. 상위 요구사항에서 이 Program에 해당하는 기능만 추출한다.
2. 사용자 역할(Member/Coach/Admin/AI)별 접근 범위를 정의한다.
3. 정상 시나리오를 작성한다.
4. 예외/오류/빈 데이터 시나리오를 작성한다.
5. 입력값/출력값 후보를 정의한다.
6. 개인정보·민감정보 포함 여부를 표시한다.
7. MUST/SHOULD/LATER 범위를 확인한다.
8. 선행 Program 의존성을 확인한다.
9. 요구사항마다 Working Requirement ID를 부여한다.
10. 요구사항을 Service 거래로 분해할 준비가 되었는지 확인한다.

## 산출물

Program 디렉토리의 `artifacts/01_REQUIREMENTS.md`

필수 표:

```text
Requirement ID
Actor
Requirement
Precondition
Input
Output
Business Rule
Error Case
Security Scope
MVP
Status
```

## 금지

- DB 컬럼부터 먼저 만들지 않는다.
- 화면 모양부터 결정하지 않는다.
- AI가 필요하지 않은 계산을 AI 요구사항으로 만들지 않는다.
- 의료 진단/처방 요구사항을 넣지 않는다.


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

`02_TASK_PROGRAM_SERVICE/SPRINT.md`
