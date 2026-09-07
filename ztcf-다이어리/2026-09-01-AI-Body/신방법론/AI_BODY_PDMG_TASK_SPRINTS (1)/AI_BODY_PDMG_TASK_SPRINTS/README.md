# AI Body PDMG TASK Sprint Runbook

이 디렉토리는 **요구사항 → 설계 → 개발 → 테스트 → 완료검증**을 PDMG 아키텍처로 반복 실행하기 위한 개발 Runbook이다.

핵심 사용법은 단순하다.

```text
Sprint 선택
  ↓
Program 선택
  ↓
01_TASK_REQUIREMENTS/SPRINT.md 피딩
  ↓
산출물 생성/저장
  ↓
02_TASK_PROGRAM_SERVICE/SPRINT.md 피딩
  ↓
...
  ↓
12_TASK_CONFORMANCE_RELEASE/SPRINT.md
  ↓
Program 완료
```

## 개발 순서

### Sprint 01 — User / Body

```text
mgbyu1000 Profile
→ mgbyu1100 Goal
→ mgbyb1000 Body Measurement
→ mgbyb1100 Body Timeline
```

### Sprint 02 — Recovery / Assessment

```text
mgbyr1000 Daily Check-in
→ mgbyr1100 Sleep/Recovery
→ mgbya1000 Initial Assessment
→ mgbya1200 Readiness Assessment
→ mgbya1100 Body Status
→ mgbyr1200 Readiness Trend
```

### Sprint 03 — Workout

```text
mgbyw1000 Workout Program
→ mgbyw1100 Workout Log
→ mgbyw1200 Workout Analytics
→ mgbyw1300 Workout Adjustment
```

## 첫 시작점

가장 먼저 아래 파일을 피딩한다.

```text
SPRINT-01_User_Body/
└─ 01_mgbyu1000_Profile/
   └─ 01_TASK_REQUIREMENTS/
      └─ SPRINT.md
```

## Program당 고정 TASK

```text
01 Requirements
02 Program / Service ID
03 Architecture
04 Data Model
05 DTO / Message
06 Backend Design
07 UI Design
08 Backend Implement
09 UI Implement
10 Test
11 Security / NFR
12 Conformance / Release
```

## 중요한 운영 규칙

- 한 번에 하나의 `SPRINT.md`만 실행한다.
- TASK 완료 산출물은 Program의 `artifacts/`에 저장한다.
- 다음 TASK는 이전 TASK 산출물을 입력으로 사용한다.
- 실제 PDMG 소스와 AS-IS 표준이 일반 Spring 관례보다 우선한다.
- 미확정 DB/Algorithm/AI 정책은 임의 결정하지 않고 `TBD`로 유지한다.
- 마지막 TASK가 통과해야 하나의 Program이 완료된다.
