# TASK 06 실행 순서 및 Security GAP

## 현재 실행 상태

```text
01 Requirements       COMPLETE
02 Program/Service    COMPLETE
03 Architecture       NOT EXECUTED
04 Data Model         COMPLETE
05 DTO/Message        COMPLETE
06 Backend Design     COMPLETE
07 UI Design          NOT EXECUTED
08 Backend Implement  BLOCKED
```

## 발견된 Critical GAP

실제 PDMG Source 분석상 JWT 검증 후 `sub/ssoId`는
request attribute에 저장되지만 업무 신원으로 ServiceContext에 연결되는
구현은 확인되지 않았다.

반면:

```text
hdr_nhnis.sys_comm.optr_eno
→ MDC userId
```

는 Client Header 기반일 수 있다.

따라서 `mgbyu1000` 본인 Profile의 DB `USER_ID`에
`optr_eno`를 그대로 사용하는 것은 금지한다.

## 다음 실제 실행

```text
03_TASK_ARCHITECTURE/SPRINT.md
```

에서 아래를 해결한다.

1. Trusted Principal 정의
2. verified JWT subject와 Profile userId 연결
3. Worker Thread Context 전달
4. Header와 Principal 불일치 정책
5. Handler/Facade/Service 중 Context 전달 책임
6. TCF ON/OFF 일관성

그 후 04/05/06 산출물에 영향이 있는 부분을 보정하고
07 UI Design으로 이동한다.
