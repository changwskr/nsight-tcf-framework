# 실행 순서 GAP — TASK 03 Architecture 미실행

현재 Program 산출물 확인 결과:

```text
01_REQUIREMENTS.md        존재 / COMPLETE
02_SERVICE_REGISTRY.md    존재 / COMPLETE
03_ARCHITECTURE.md        없음
04_DATA_MODEL             이번 실행에서 작성
```

Runbook 원칙은 TASK 01 → 02 → 03 → 04 순서다.
사용자가 TASK 04 SPRINT를 직접 피딩했으므로 데이터 설계를 수행했지만,
**TASK 05로 넘어가기 전에 TASK 03 Architecture를 반드시 실행해야 한다.**

TASK 03에서 확인/보정할 항목:

- userId 인증 Context 취득 위치
- S0/C0/U0 Runtime Flow
- Handler/Facade/Service 책임
- Transaction Boundary
- Profile 없음/중복 Error Flow
- Coach/Admin 접근 경계
- MDC/Trace
- AI 직접 접근 금지 경계

Architecture 결과가 데이터 경계와 충돌하면 TASK 04 산출물을 수정한다.
