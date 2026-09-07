# mgbyb1100 Body Timeline

이 디렉토리는 **하나의 PDMG Program을 처음부터 끝까지 완료하기 위한 실행 Runbook**이다.

## 실행 순서

01. `01_TASK_REQUIREMENTS/SPRINT.md` - 요구사항 정제 및 범위 확정
02. `02_TASK_PROGRAM_SERVICE/SPRINT.md` - Program / Service ID 계약 확정
03. `03_TASK_ARCHITECTURE/SPRINT.md` - PDMG End-to-End 아키텍처 설계
04. `04_TASK_DATA_MODEL/SPRINT.md` - 논리/물리 데이터 설계
05. `05_TASK_DTO_MESSAGE/SPRINT.md` - DTO / 전문 / 인터페이스 계약 설계
06. `06_TASK_BACKEND_DESIGN/SPRINT.md` - Backend 상세설계
07. `07_TASK_UI_DESIGN/SPRINT.md` - pdmg-ui 화면 상세설계
08. `08_TASK_BACKEND_IMPLEMENT/SPRINT.md` - Backend 구현
09. `09_TASK_UI_IMPLEMENT/SPRINT.md` - UI 구현
10. `10_TASK_TEST/SPRINT.md` - 단위/통합/E2E 테스트
11. `11_TASK_SECURITY_NFR/SPRINT.md` - 보안·비기능·운영 점검
12. `12_TASK_CONFORMANCE_RELEASE/SPRINT.md` - 표준 적합성·완료검증·Baseline

## 사용 방법

1. 반드시 `01_TASK_REQUIREMENTS/SPRINT.md`부터 시작한다.
2. 한 TASK가 완료되기 전 다음 TASK로 이동하지 않는다.
3. 각 TASK 산출물은 `artifacts/`에 지정된 이름으로 저장한다.
4. 실제 코드는 `pdmg-service` / `pdmg-ui`의 정식 위치에 반영한다.
5. `PROPOSED/TBD`를 임의로 `FACT`로 바꾸지 않는다.
6. 마지막 `12_TASK_CONFORMANCE_RELEASE`를 통과하면 이 Program을 완료로 본다.

## 대상 Program

| 항목 | 값 |
|---|---|
| Program ID | `mgbyb1100` |
| 업무 | Body |
| 기능 | Body Timeline |
| Java Package Root | `nhnis.mg.by.b` |
| UI | `static/mgbyb1100/index.html` |
| 선행 Program | mgbyu1000, mgbyu1100, mgbyb1000 |

### 대상 Service ID

- `mgbyb1100S0` : Body Timeline 조회

### 핵심 논리 Entity

- `TimelineEvent (논리)`
- `UserGoal`
- `BodyMeasurement`

### Rule / Algorithm 후보

- `TimelineCompositionRule (필요 시)`

