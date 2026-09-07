# TASK 07 실행 순서 / UI Security GAP

## 현재 상태

```text
01 Requirements        COMPLETE
02 Program/Service     COMPLETE
03 Architecture        NOT EXECUTED
04 Data Model          COMPLETE
05 DTO/Message         COMPLETE
06 Backend Design      COMPLETE
07 UI Design           COMPLETE
08 Backend Implement   BLOCKED
09 UI Implement        BLOCKED
```

## 이번 UI 설계에서 추가 확인된 PDMG GAP

### 1. Browser Direct Call

현재 일반 pdmg-ui 거래는 Browser가
`pdmg-service /{serviceId}`를 직접 호출한다.

### 2. Authorization Header

확인된 현행 `service-client.js` 기본 fetch에는
`Authorization: Bearer ...`가 없다.

본인 Profile과 같은 신규 개인정보 거래는
신뢰된 인증 Context 설계 없이 구현 완료 처리할 수 없다.

### 3. Service ID Mismatch

현재 PDMG는 Header `rms_svc_c`를 URL path보다 우선할 수 있고,
`online-single.js`가 선택 거래 ID와 Header Service ID를
항상 동일하게 강제하지 않는다.

따라서 다음 계약을 공통적으로 보강해야 한다.

```text
Selected Transaction ID
=
URL Service ID
=
hdr_nhnis.sys_comm.rms_svc_c
```

## 다음 실제 실행

```text
03_TASK_ARCHITECTURE/SPRINT.md
```

TASK 03에서 최소한 다음을 고정한다.

1. Browser 인증 전달 방식
2. JWT verified subject → authenticatedUserId
3. Timeout Worker Context 전달
4. optr_eno 신뢰금지/불일치 정책
5. Service ID URL/Header 일치 Enforcement 위치
6. TCF ON/OFF 인증 일관성

완료 후 TASK 04~07을 보정하고 TASK 08로 진행한다.
