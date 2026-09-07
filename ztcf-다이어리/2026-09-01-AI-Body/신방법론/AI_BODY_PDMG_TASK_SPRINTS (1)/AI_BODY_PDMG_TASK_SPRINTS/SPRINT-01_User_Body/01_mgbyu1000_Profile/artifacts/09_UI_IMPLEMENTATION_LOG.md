# TASK 09 — mgbyu1000 UI Implementation Log

- Program: `mgbyu1000 Profile`
- TASK: `09_TASK_UI_IMPLEMENT`
- 상태: `PATCH_READY`
- SSOT 적용: `NO`
- Browser E2E: `NOT RUN`
- 기준일: 2026-09-01

---

# 1. 실행 결과

TASK 09 요구에 따라 다음 적용용 화면을 생성했다.

```text
pdmg-ui/src/main/resources/static/mgbyu1000/index.html
```

별도 React/Next 프로젝트나 Program별 JS/CSS 파일은 생성하지 않았다.

---

# 2. 구현된 화면 계약

```html
data-program-id="mgbyu1000"
data-default-transaction-id="mgbyu1000S0"
```

지원 Service:

```text
Page Load  → mgbyu1000S0
Create     → mgbyu1000C0
Update     → mgbyu1000U0
```

---

# 3. 구현 Field

```text
displayName
birthDate
genderCode
trainingExperienceCode
activityLevelCode
```

금지 Field:

```text
userId
height
weight
bodyFatPercentage
```

`userId`는 hidden input으로도 만들지 않았다.

---

# 4. 상태 머신

```text
LOADING
 → S0
 → VIEW / EMPTY

EMPTY
 → EDIT_CREATE
 → C0
 → S0 reload

VIEW
 → EDIT_UPDATE
 → U0
 → S0 reload

Any failure
 → ERROR
```

Double submit 방지를 적용했다.

---

# 5. Service ID 안전계약

한 Service Action에서 다음 세 값을 동일한 `serviceId` 변수에서 생성한다.

```text
Action Service ID
=
POST URL Service ID
=
hdr_nhnis.sys_comm.rms_svc_c
```

따라서 C0 저장이 Header S0로 잘못 실행되는 식의
현행 PDMG Header/Path mismatch 위험을 화면에서 방지한다.

---

# 6. 개인정보

다음은 하지 않는다.

```text
Profile DTO localStorage 저장
Profile DTO console.log
userId URL/query/hash 저장
userId hidden input
```

---

# 7. CodeSet 처리

실제 승인 CodeSet이 없으므로
다음 값을 Production Option으로 임의 하드코딩하지 않았다.

```text
BEGINNER
INTERMEDIATE
ADVANCED
LOW
MODERATE
HIGH
```

현재 Patch는 임시 text code input과 안내문을 사용한다.

CodeSet 확정 후 Select/Lookup UI로 교체해야 한다.

---

# 8. JWT / Authorization

현행 `service-client.js` 기본 fetch에 Bearer Authorization이 없다는
AS-IS를 확인했다.

Patch Bundle에는:

```text
shared-patches/service-client-authorization.proposed.diff
```

를 포함했으나 자동 적용하지 않았다.

이유:

```text
Token 저장 Key
JWT Algorithm
Issuer/Audience/Claim
pdmg-jwt 발급 계약
pdmg-service 검증 계약
```

이 아직 완전히 정합되지 않았다.

---

# 9. 검증 결과

```text
HTML Parser               PASS
Node --check inline JS    PASS
Privacy Static Check      PASS
Conformance Test          PASS
```

Privacy Check:

```text
userId input      없음
height input      없음
localStorage      없음
console.log       없음
```

---

# 10. 미결사항 / Blocker

## P0

1. Browser 인증 전달 방식
2. JWT 검증 ↔ Backend authenticatedUserId
3. 실제 Browser CORS/Auth E2E
4. S0 미존재 최종 응답 계약

## P1

1. CodeSet Source
2. 공통 Error Popup 실제 버전 API 확인
3. Main Shell Route/Menu

---

# 11. 완료 판정

```text
UI Source Patch 생성      PASS
PDMG Program HTML 계약    PASS
JS Syntax                 PASS
Privacy Static            PASS
SSOT 반영                 PENDING
Browser E2E               PENDING
JWT Integration           PENDING
```

**TASK 09 상태: PATCH_READY**

실제 저장소 적용과 Browser/Service E2E 전에는 `COMPLETE`로 승격하지 않는다.
