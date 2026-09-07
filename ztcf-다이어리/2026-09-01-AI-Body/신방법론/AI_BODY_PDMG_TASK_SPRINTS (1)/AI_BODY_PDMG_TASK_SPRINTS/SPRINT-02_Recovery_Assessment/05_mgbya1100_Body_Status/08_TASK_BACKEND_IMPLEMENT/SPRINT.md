# Backend 구현
# mgbya1100 Body Status

- 실행 순서: 08 / 12
- 상태: READY
- 원칙: 이 문서를 한 번에 하나씩 실행한다.

## 대상 Program

| 항목 | 값 |
|---|---|
| Program ID | `mgbya1100` |
| 업무 | Assessment |
| 기능 | Body Status |
| Java Package Root | `nhnis.mg.by.a` |
| UI | `static/mgbya1100/index.html` |
| 선행 Program | mgbya1000, mgbya1200 |

### 대상 Service ID

- `mgbya1100S0` : Body Status 조회
- `mgbya1100A0` : Body Status 재계산

### 핵심 논리 Entity

- `BodyStatus`

### Rule / Algorithm 후보

- `BodyStatusCalculator`


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

설계된 PDMG Backend를 실제 소스에 구현한다.

## 구현 순서

```text
DTO
→ DAO
→ Mapper XML
→ Rule/Algorithm
→ Service
→ Facade
→ Handler
→ 필요한 경우 Controller(TCF OFF 호환)
```

## 수행 TASK

1. 실제 PDMG 유사 Program 소스의 import/annotation/생성자/예외 패턴을 먼저 확인한다.
2. DTO를 구현한다.
3. DAO Interface를 구현한다.
4. Mapper XML을 구현한다.
5. Rule/Algorithm을 구현한다.
6. Service를 구현한다.
7. Facade에 Transaction을 적용한다.
8. Handler에 Service ID를 등록하고 라우팅한다.
9. JavaDoc을 작성한다.
10. 컴파일 오류를 제거한다.

## 필수 계약

```text
Program Type = 소문자 ProgramId + 역할
Facade TX = rdwTransactionManager
DAO FQCN = Mapper namespace
DAO Method = Mapper Statement ID
```

## 산출물

실제 `pdmg-service` 소스 변경 + `artifacts/08_BACKEND_IMPLEMENTATION_LOG.md`


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

`09_TASK_UI_IMPLEMENT/SPRINT.md`
