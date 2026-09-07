# TASK 05 실행 순서 GAP

현재 상태:

```text
01 Requirements       COMPLETE
02 Program/Service    COMPLETE
03 Architecture       NOT EXECUTED
04 Data Model         COMPLETE
05 DTO/Message        COMPLETE
06 Backend Design     HOLD
```

사용자가 TASK 05 SPRINT를 직접 피딩했으므로 DTO/전문 계약은 작성했다.

그러나 Backend 상세설계 전에 다음 Architecture 항목이 반드시 확정되어야 한다.

1. 검증된 `authenticatedUserId`의 실제 취득 위치
2. TCF ON S0/C0/U0 Runtime Flow
3. Profile 없음/중복 오류 흐름
4. Coach/Admin 접근 경계
5. Timeout ON TransactionTemplate과 Facade TX 관계
6. MDC/Trace/Security Context 전달
7. TCF OFF 호환을 만들지 여부

따라서 다음 실제 실행 파일은:

```text
03_TASK_ARCHITECTURE/SPRINT.md
```

Architecture 완료 후 `05_DTO_MESSAGE_SPEC.md`와 충돌 여부를 점검하고
`06_TASK_BACKEND_DESIGN/SPRINT.md`로 이동한다.
