# mgbyu1000 Profile pdmg-ui 화면 상세설계

- 문서 ID: `BY-UI-MGBYU1000`
- Program ID: `mgbyu1000`
- 작성 단계: `07_TASK_UI_DESIGN`
- 상태: `DESIGN COMPLETE / IMPLEMENTATION BLOCKED BY TASK03 AUTH GAP`
- 기준일: 2026-09-01
- 화면 모듈: `pdmg-ui`
- 화면 기술: HTML + 기존 `_shared` JavaScript/CSS
- 대상 파일: `pdmg-ui/src/main/resources/static/mgbyu1000/index.html`
- 다음 정상 단계: `08_TASK_BACKEND_IMPLEMENT`
- 실제 구현 전 필수 선행: `03_TASK_ARCHITECTURE`

---

# 1. 목적

`mgbyu1000 Profile`의 Member용 화면을
PDMG AS-IS UI 구조와 ServiceId 전문 계약에 맞춰 상세설계한다.

화면이 지원할 거래:

```text
mgbyu1000S0  현재 Profile 조회
mgbyu1000C0  최초 Profile 등록
mgbyu1000U0  기존 Profile 수정
```

화면이 직접 처리하지 않는 것:

```text
인증/JWT 검증
다른 Member 선택
Coach Profile 조회
Admin Profile 수정
DB Key(userId) 입력
의료정보
신체 측정(height/weight...)
```

---

# 2. 실제 pdmg-ui에서 확인한 FACT

## FACT-UI-001 — 화면 단위는 Program ID

```text
static/
└─ {ProgramId}/
   └─ index.html
```

따라서 Profile 화면은:

```text
static/mgbyu1000/index.html
```

하나를 기본 화면 단위로 사용한다.

## FACT-UI-002 — 공통 Resource를 `_shared`에서 재사용

현재 주요 공통 Resource:

```text
/_shared/ui-context.js
/_shared/pdmg-shell.js
/_shared/service-client.js
/_shared/online-single.js
/_shared/error-codes.js
/_shared/error-popup.js
/_shared/online.css
/_shared/pdmg-workbench.css
```

Program별 별도 JS/CSS 파일을 기본 생성하지 않는다.

## FACT-UI-003 — 일반 거래 화면은 Browser에서 pdmg-service를 직접 호출

현행 흐름:

```text
Browser / pdmg-ui
   ↓ fetch POST /{serviceId}
pdmg-service
```

Relay가 일반 거래의 기본 경로가 아니다.

## FACT-UI-004 — 현행 service-client 기본 Header에는 JWT Authorization이 없음

현재 확인된 기본 fetch header:

```text
Content-Type: application/json;charset=UTF-8
Accept: application/json
```

따라서 Profile처럼 본인 데이터 접근이 필요한 신규 업무는
TASK 03의 인증 Architecture가 완료되기 전 구현 완료로 간주할 수 없다.

## FACT-UI-005 — Service ID 일치가 자동 강제되지 않는 GAP 존재

현행 PDMG는 Service ID를 다음 순서로 해석할 수 있다.

```text
1. ServiceContext / Header rms_svc_c
2. Request JSON hdr_nhnis.sys_comm.rms_svc_c
3. URL path
```

또한 `online-single.js`의 Header 보강이
선택 Transaction ID를 항상 `rms_svc_c`에 덮어쓰는 것은 아니다.

따라서 UI는 다음 불변식을 만족해야 한다.

```text
URL Service ID
=
hdr_nhnis.sys_comm.rms_svc_c
=
사용자가 수행한 실제 Action의 Service ID
```

이 불변식은 TASK 09 구현 및 TASK 10 Contract Test에서 검증한다.

---

# 3. 화면 식별 계약

HTML root:

```html
<html lang="ko"
      data-program-id="mgbyu1000"
      data-default-transaction-id="mgbyu1000S0">
```

## Program ID

```text
mgbyu1000
```

## Default Transaction

```text
mgbyu1000S0
```

화면 진입 기본 Use Case가 현재 Member의 Profile 조회이므로
S0를 Default Transaction으로 사용한다.

---

# 4. 화면 Route

기본 화면 경로:

```text
/mgbyu1000/index.html
```

Main Shell에서 Program Registry/Hash Route를 연결할 경우
실제 Shell Route 명은 전체 BY 메뉴 설계에서 확정한다.

본 TASK에서 다음을 임의 확정하지 않는다.

```text
#/profile
#/body/profile
#/my/profile
```

Shell Route는 `TBD-UI-001`.

---

# 5. 화면 전체 구조

## 5.1 Mobile First Wireframe

```text
┌───────────────────────────────────┐
│  내 프로필                        │
│  코칭의 기본 정보를 관리합니다.   │
├───────────────────────────────────┤
│                                   │
│  [상태 메시지 영역]               │
│  Loading / Empty / Error / Saved  │
│                                   │
├───────────────────────────────────┤
│  표시 이름                        │
│  [____________________________]   │
│                                   │
│  생년월일                         │
│  [ YYYY-MM-DD                ]    │
│                                   │
│  성별                             │
│  [ 선택 ▼                    ]    │
│                                   │
│  운동 경력 *                      │
│  [ 선택 ▼                    ]    │
│                                   │
│  활동 수준 *                      │
│  [ 선택 ▼                    ]    │
│                                   │
├───────────────────────────────────┤
│  [ 취소/다시불러오기 ] [ 저장 ]   │
└───────────────────────────────────┘
```

Desktop에서는 중앙 Content 폭을 제한하고
Form Field를 2-column으로 확장할 수 있다.

---

# 6. 화면 Mode

하나의 HTML에서 다음 Mode를 사용한다.

```text
LOADING
EMPTY
VIEW
EDIT_CREATE
EDIT_UPDATE
SAVING
ERROR
SUCCESS
```

## 6.1 초기 흐름

```text
Page Load
   ↓
LOADING
   ↓
mgbyu1000S0
   ↓
Profile exists?
 ├─ YES → VIEW
 └─ NO  → EMPTY
```

단, S0 미존재 응답이 `dto:null`, 빈 DTO, Business Error 중 무엇인지는
`TBD-PRF-003`이므로 TASK 03/06 보정 후 UI 판정 로직을 고정한다.

## 6.2 EMPTY

```text
"아직 프로필이 없습니다."
"프로필을 등록하면 목표와 코칭을 개인화할 수 있습니다."

[프로필 만들기]
```

버튼:

```text
프로필 만들기
→ EDIT_CREATE
```

## 6.3 VIEW

Profile 5개 Field를 읽기 전용으로 표시한다.

```text
displayName
birthDate
genderCode
trainingExperienceCode
activityLevelCode
```

버튼:

```text
[수정]
```

→ `EDIT_UPDATE`

## 6.4 EDIT_CREATE

Save:

```text
mgbyu1000C0
```

Cancel:

```text
EMPTY
```

## 6.5 EDIT_UPDATE

Save:

```text
mgbyu1000U0
```

Cancel:

```text
최근 S0 결과로 VIEW 복구
```

## 6.6 SUCCESS

C0/U0 성공:

```text
processedCount == 1
   ↓
성공 메시지
   ↓
mgbyu1000S0 재조회
   ↓
VIEW
```

화면은 서버 성공 후 로컬 Form 상태만 믿지 않고
S0 재조회 결과를 화면의 최신 상태로 사용한다.

---

# 7. HTML 구성

권장 Skeleton:

```html
<!DOCTYPE html>
<html lang="ko"
      data-program-id="mgbyu1000"
      data-default-transaction-id="mgbyu1000S0">
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, initial-scale=1.0">

  <script src="/_shared/ui-context.js"></script>
  <link rel="stylesheet" href="/_shared/online.css">
</head>

<body class="pdmg-page">

  <main id="profilePage">
    <header>
      <h1>내 프로필</h1>
      <p>코칭의 기본 정보를 관리합니다.</p>
    </header>

    <section id="profileStatus"
             role="status"
             aria-live="polite"></section>

    <form id="profileForm" novalidate>
      <!-- Profile Fields -->
    </form>
  </main>

  <script src="/_shared/error-codes.js"></script>
  <script src="/_shared/error-popup.js"></script>
  <script src="/_shared/service-client.js"></script>
  <script src="/_shared/online-single.js"></script>

  <script>
    // mgbyu1000 화면 전용 소규모 상태/바인딩 로직
  </script>
</body>
</html>
```

실제 `_shared` API의 전역 함수/이벤트 명은
TASK 09에서 현재 `online-single.js` 소스를 보고 그대로 사용한다.

임의의 새로운 `PdmgApi.call()` 같은 API를 만들지 않는다.

---

# 8. Resource 재사용 결정

| Resource | 사용 | 목적 |
|---|---:|---|
| `ui-context.js` | Y | 환경/Target UI Context |
| `online.css` | Y | 표준 거래 화면 Style |
| `error-codes.js` | Y | 표준 오류 표시 |
| `error-popup.js` | Y | 공통 Error UX |
| `service-client.js` | Y | ServiceId HTTP Client |
| `online-single.js` | Y | 단일 Program 거래 공통 동작 |
| `pdmg-shell.js` | Shell에서 | Main Shell Route/Frame |
| `pdmg-workbench.css` | 필요 시 | Shell/Layout와 실제 호환 확인 |
| `jwt-admin.js` | N | Profile 화면에서 직접 사용 금지 |
| `om-admin.css` | N | Admin 전용 |

---

# 9. Field ↔ DTO Mapping

## 9.1 Form Field

| 화면 Field ID 후보 | Label | Input Type | DTO Field | C0 | U0 | S0 Output | Sensitive |
|---|---|---|---|---:|---:|---:|---:|
| `displayName` | 표시 이름 | text | `displayName` | O | O | O | Y |
| `birthDate` | 생년월일 | date | `birthDate` | O | O | O | Y |
| `genderCode` | 성별 | select | `genderCode` | O | O | O | Y |
| `trainingExperienceCode` | 운동 경력 | select | `trainingExperienceCode` | O | O | O | Y |
| `activityLevelCode` | 활동 수준 | select | `activityLevelCode` | O | O | O | Y |

## 9.2 UI에 없는 Field

```text
userId
height
weight
bodyFatPercentage
createdAt
updatedAt
```

특히:

```text
<input name="userId">
```

를 생성하지 않는다.

Hidden input으로도 만들지 않는다.

---

# 10. Service별 UI Contract

## 10.1 S0

Service:

```text
mgbyu1000S0
```

Request:

```json
{
  "hdr_nhnis": {
    "sys_comm": {
      "rms_svc_c": "mgbyu1000S0"
    }
  },
  "dto": {}
}
```

UI Input 없음.

Output:

```json
{
  "dto": {
    "displayName": "홍길동",
    "birthDate": "1995-07-21",
    "genderCode": "M",
    "trainingExperienceCode": "INTERMEDIATE",
    "activityLevelCode": "MODERATE"
  }
}
```

## 10.2 C0

Service:

```text
mgbyu1000C0
```

Payload DTO:

```json
{
  "displayName": "홍길동",
  "birthDate": "1995-07-21",
  "genderCode": "M",
  "trainingExperienceCode": "INTERMEDIATE",
  "activityLevelCode": "MODERATE"
}
```

Expected Output:

```json
{
  "processedCount": 1
}
```

## 10.3 U0

Service:

```text
mgbyu1000U0
```

U0는 TASK 05 계약대로 Full Update다.

따라서 저장 시 화면의 5개 현재값을 모두 DTO에 넣는다.

Expected Output:

```json
{
  "processedCount": 1
}
```

---

# 11. URL ↔ Header Service ID 불변식

## 11.1 필수

C0 버튼 실행이면:

```text
URL
/mgbyu1000C0

Header
rms_svc_c = mgbyu1000C0
```

U0도 동일하다.

## 11.2 금지

```text
POST /mgbyu1000C0

hdr_nhnis.sys_comm.rms_svc_c
=
mgbyu1000S0
```

현재 PDMG는 Header Service ID를 우선할 수 있으므로
다른 거래가 실행될 수 있다.

## 11.3 구현 방침

TASK 09에서 실제 `_shared` API를 확인한다.

### 우선순위

1. `_shared`가 이미 Transaction ID 강제 기능을 제공하면 재사용
2. 제공하지 않으면 `_shared/online-single.js` 또는 `service-client.js`를
   **공통 수준에서 1회 보강**하는 것을 우선
3. Program별로 동일 보정 로직을 복제하지 않음

공통 보강 후보:

```text
selectedServiceId
   ↓
target URL
   ↓
payload.hdr_nhnis.sys_comm.rms_svc_c
```

세 값을 동일한 Source에서 생성한다.

---

# 12. Button / Event ↔ Service ID

| UI Event | Mode | Service | DTO | 성공 후 |
|---|---|---|---|---|
| Page Load | LOADING | `mgbyu1000S0` | `{}` | VIEW 또는 EMPTY |
| 새 Profile 저장 | EDIT_CREATE | `mgbyu1000C0` | Form 전체 | S0 재조회 |
| Profile 수정 저장 | EDIT_UPDATE | `mgbyu1000U0` | Form 전체 | S0 재조회 |
| 수정 시작 | VIEW | 호출 없음 | - | EDIT_UPDATE |
| 등록 시작 | EMPTY | 호출 없음 | - | EDIT_CREATE |
| 취소 | EDIT | 호출 없음 | - | 이전 상태 복귀 |
| 다시 시도 | ERROR | 직전 Service | 직전 DTO | 정상 Flow |

---

# 13. Client Validation

Server Validation이 최종 권위다.

UI Validation은 빠른 사용자 피드백용이다.

## 필수

```text
trainingExperienceCode
activityLevelCode
```

## 선택

```text
displayName
birthDate
genderCode
```

## 규칙

```text
displayName
  max 100 후보
  trim

birthDate
  yyyy-MM-dd
  미래일 금지

genderCode
  실제 CodeSet에서 선택

trainingExperienceCode
  필수
  실제 CodeSet에서 선택

activityLevelCode
  필수
  실제 CodeSet에서 선택
```

브라우저 Validation을 통과했다고 Server가 검증을 생략하면 안 된다.

---

# 14. CodeSet UI 처리

현재 실제 코드값:

```text
genderCode
trainingExperienceCode
activityLevelCode
```

가 미확정이다.

따라서 TASK 07에서는 Select UI만 설계하고
다음처럼 예시값을 Production 코드로 박아넣지 않는다.

```text
BEGINNER
INTERMEDIATE
ADVANCED
LOW
MODERATE
HIGH
```

CodeSet Source 후보:

```text
Static approved enum
OM_COMMON_CODE
BY common code service
```

는 별도 Baseline에서 확정한다.

`TBD-UI-002`.

---

# 15. Loading State

## Page Load

```text
Profile을 불러오는 중입니다.
```

동작:

```text
Form disabled
Save button disabled
중복 S0 요청 방지
```

## Save

```text
저장 중입니다.
```

동작:

```text
Save disabled
Double submit 방지
Cancel 정책은 disabled 권장
```

네트워크 완료 후 상태 전환.

---

# 16. Empty State

Profile이 없을 때:

```text
아직 프로필이 없습니다.
운동 경력과 활동 수준을 등록하면
더 개인화된 코칭을 받을 수 있습니다.

[프로필 만들기]
```

단 S0 미존재의 Backend 응답 형태가 아직 TBD이므로
다음 중 하나를 Architecture/Backend에서 고정한다.

```text
HTTP 200 + dto null
HTTP 200 + 빈 dto + found flag
Business Not Found Error
```

UI가 Error Message 문자열을 파싱하여 Empty를 판정하지 않는다.

---

# 17. Error State

공통 `error-popup.js`를 우선 사용한다.

UI에서 Error를 구분할 때:

```text
HTTP Status
PDMG result
stdErrCode
errType
```

등 구조화 정보만 사용한다.

금지:

```javascript
if (message.includes("Profile not found")) { ... }
```

사용자 메시지:

```text
조회 실패
저장 실패
입력값 확인 필요
인증 필요
권한 없음
```

등을 공통 오류 정책에 맞춰 표시한다.

---

# 18. Success State

C0/U0 성공 후:

```text
"프로필이 저장되었습니다."
```

Success Toast/Inline Status 중
기존 PDMG 공통 UX를 우선 적용한다.

성공 이후:

```text
S0 재조회
```

를 수행해 Server State를 화면에 반영한다.

---

# 19. Accessibility

필수:

```text
label for ↔ input id
aria-live="polite" 상태 영역
disabled 상태 명확화
키보드 Tab 이동
select에 빈 placeholder option
Error Field와 메시지 연결
```

색상만으로 Validation/Error 상태를 표현하지 않는다.

---

# 20. Mobile First Layout

## 20.1 Small viewport

```text
1 Column
Full-width inputs
Primary Save full-width
44px 이상 터치 영역 권장
```

## 20.2 Medium/Desktop

```text
Content max-width
2-column 가능한 Field는 Grid
Action은 우측 또는 하단 정렬
```

Field 의미 때문에 Mobile과 Desktop에서
입력 순서는 바꾸지 않는다.

권장 순서:

```text
displayName
birthDate
genderCode
trainingExperienceCode
activityLevelCode
```

---

# 21. 개인정보 표시 정책

Profile 화면은 본인용 화면이다.

그래도 최소노출 원칙을 적용한다.

## 21.1 userId

표시하지 않는다.

## 21.2 birthDate

본인 화면에서는 전체 날짜 표시 가능 후보이나
기획/개인정보 정책 승인 전 `PROPOSED`.

Coach/Admin 화면에서 재사용하지 않는다.

## 21.3 Browser Console

금지:

```javascript
console.log(profileDto);
console.log(requestBody);
console.log(responseBody);
```

Production에서 Profile DTO 전체 로그 금지.

## 21.4 DOM

민감정보를 다음에 넣지 않는다.

```text
data-* attribute
hidden input
URL query string
hash route
localStorage
```

필요한 Edit 상태는 현재 DOM input/value 또는
page-local memory로만 유지한다.

---

# 22. Authentication UI Boundary

## UI가 하지 않는 일

```text
Client가 userId 선택
optr_eno를 본인 ID로 세팅
hidden userId 전달
Header userId 조작
JWT payload를 Client에서 권한 판정
```

## 필요한 Architecture

```text
Browser
  ↓ Authorization/JWT or trusted session
pdmg-service
  ↓ Verified Principal
authenticatedUserId
```

현재 현행 `service-client.js` 기본 fetch에는 Authorization Bearer가 없다는
PDMG 분석 결과가 있으므로,
TASK 03에서 신규 BY 거래의 인증 전달 방식을 반드시 해결해야 한다.

이 문제는 TASK 09 UI 구현에서도 Blocker다.

---

# 23. PDMG Request Security Hardening Candidate

Profile 구현 전 공통 Client에 대해 다음을 검토한다.

```text
1. Authorization header 전달
2. URL ServiceId = hdr rms_svc_c 강제
3. Client에서 userId/optr_eno를 권한 Key로 조작하지 않음
4. Target Base URL 환경별 안전한 설정
```

이 보강은 `mgbyu1000/index.html`에 복제하지 않고
가능하면 `_shared` 공통 Resource에 적용한다.

공통 Resource 수정은 BY 화면만의 암묵적 변경으로 하지 말고
별도 Architecture/Conformance 결정으로 기록한다.

---

# 24. 화면 상태 머신

```text
              ┌───────────────┐
              │    LOADING    │
              └───────┬───────┘
                      │ S0
            ┌─────────┴─────────┐
            │                   │
       found                  not found
            │                   │
            ▼                   ▼
      ┌──────────┐        ┌──────────┐
      │   VIEW   │        │  EMPTY   │
      └────┬─────┘        └────┬─────┘
           │ Edit               │ Create
           ▼                    ▼
    ┌─────────────┐      ┌─────────────┐
    │ EDIT_UPDATE │      │ EDIT_CREATE │
    └──────┬──────┘      └──────┬──────┘
           │ U0                  │ C0
           └─────────┬───────────┘
                     ▼
                ┌────────┐
                │ SAVING │
                └───┬────┘
                    │ success
                    ▼
               ┌─────────┐
               │ SUCCESS │
               └────┬────┘
                    │ S0 reload
                    ▼
                   VIEW

Any request failure
       ↓
     ERROR
       ↓ retry/cancel
```

---

# 25. HTML Element ID 제안

화면 테스트 안정성을 위해 의미 있는 ID를 사용한다.

```text
profilePage
profileStatus
profileForm

displayName
birthDate
genderCode
trainingExperienceCode
activityLevelCode

createProfileButton
editProfileButton
saveProfileButton
cancelProfileButton
retryButton
```

Program ID나 Service ID를 Element ID에 반복해서 붙이지 않는다.
화면 Scope 자체가 `mgbyu1000`이기 때문이다.

---

# 26. UI Test 설계

## 26.1 Static Contract

```text
html[data-program-id] = mgbyu1000
html[data-default-transaction-id] = mgbyu1000S0
_shared resources 존재
userId input 없음
height input 없음
```

## 26.2 Service Mapping

```text
Load     → S0
Create   → C0
Update   → U0
```

## 26.3 Request Contract

```text
S0 dto = {}
C0/U0에 userId 없음
URL serviceId = Header rms_svc_c
```

## 26.4 State

```text
S0 loading
S0 success
S0 empty
S0 error
C0 saving/success/error
U0 saving/success/error
double submit 방지
```

## 26.5 Privacy

```text
URL에 개인정보 없음
localStorage에 Profile DTO 없음
console log에 DTO 없음
hidden userId 없음
```

## 26.6 Responsive

```text
360px
390px
768px
1280px
```

에서 Form usable.

---

# 27. Backend ↔ UI Traceability

| UI | Service | DTO Contract | Backend |
|---|---|---|---|
| 화면 초기화 | `mgbyu1000S0` | `{}` | Handler → Facade → Service |
| Profile 등록 | `mgbyu1000C0` | 5 Profile fields | Validation → INSERT |
| Profile 수정 | `mgbyu1000U0` | 5 Profile fields | Validation → UPDATE |
| 저장 후 갱신 | `mgbyu1000S0` | `{}` | 최신 DB 상태 조회 |

---

# 28. Requirement Traceability

| Requirement | UI 반영 |
|---|---|
| `BY-USR-PRF-001` | 초기 S0 / VIEW |
| `BY-USR-PRF-002` | EMPTY → EDIT_CREATE → C0 |
| `BY-USR-PRF-003` | VIEW → EDIT_UPDATE → U0 |
| `BY-USR-PRF-004` | 운동경력 Select |
| `BY-USR-PRF-005` | 활동수준 Select |
| `BY-USR-PRF-006` | S0 최소 Profile Display |
| `BY-USR-PRF-007` | userId 입력/표시 제거 |
| `BY-USR-PRF-008` | AI 기능 없음 |
| `BY-USR-PRF-009` | Browser console/localStorage 로그 금지 |
| `BY-USR-PRF-010` | 삭제 UI 없음 |

---

# 29. 결정사항

TASK 07에서 다음을 고정한다.

1. 화면은 `static/mgbyu1000/index.html` 하나로 구성한다.
2. `data-program-id="mgbyu1000"`.
3. `data-default-transaction-id="mgbyu1000S0"`.
4. 기존 `_shared` Resource를 재사용한다.
5. 별도 `mgbyu1000.js/css`를 기본 생성하지 않는다.
6. Profile 화면은 Member 본인용이다.
7. `userId` Input/Hidden/URL Parameter를 만들지 않는다.
8. `height` 등 Body Measurement Field를 넣지 않는다.
9. S0/C0/U0는 한 화면의 상태 전환으로 처리한다.
10. C0/U0 성공 후 S0를 재조회한다.
11. U0는 Full Update Form이다.
12. Profile DTO 전체를 console/localStorage에 저장하지 않는다.
13. URL Service ID = Header Service ID를 강제해야 한다.
14. 공통 Client GAP은 Program별 복제보다 `_shared` 보강을 우선한다.
15. 인증/JWT 신뢰 경계는 UI에서 우회하지 않는다.

---

# 30. TBD / Blocker

## P0 — TASK 03

### `TBD-UI-AUTH-001`

Browser → pdmg-service에 인증 Token/Session을 어떻게 전달할지.

### `TBD-UI-AUTH-002`

JWT/Trusted Principal을 Backend Profile `authenticatedUserId`로 연결하는 방식.

### `TBD-UI-AUTH-003`

현행 Header `optr_eno`를 Profile 보안 Key에서 완전히 배제하는 방식.

## P1

### `TBD-UI-001`

Main Shell Profile Route.

### `TBD-UI-002`

CodeSet Source / 실제 값.

### `TBD-UI-003`

S0 Profile 없음의 Backend 응답 형태.

### `TBD-UI-004`

`_shared`의 정확한 Program Action Hook/API.

### `TBD-UI-005`

Service ID 일치 강제를 `_shared/service-client.js`와
`online-single.js` 중 어디에서 할지.

### `TBD-UI-006`

공통 Success Toast/Inline Message API 존재 여부.

---

# 31. 구현 파일 예상

TASK 09 기본 생성:

```text
pdmg-ui/src/main/resources/static/
└─ mgbyu1000/
   └─ index.html
```

기존 공통 파일은 수정하지 않는 것이 기본이지만
Service ID/JWT 공통 보강이 Architecture에서 승인되면 조건부 수정:

```text
_shared/service-client.js
_shared/online-single.js
```

그 경우 다른 Program 회귀 테스트가 필수다.

---

# 32. 다음 실행 Gate

Runbook상 다음은:

```text
08_TASK_BACKEND_IMPLEMENT/SPRINT.md
```

이지만 현재는 다음 P0가 남아 있다.

```text
03 Architecture 미실행
JWT/Auth Context 미확정
Profile Physical DB 최종 승인 미확정
Error Code/CodeSet 일부 TBD
```

따라서 실제 권장 순서:

```text
03_TASK_ARCHITECTURE
      ↓
04/05/06/07 영향 보정
      ↓
08_TASK_BACKEND_IMPLEMENT
```

---

# 33. TASK 07 완료 게이트

- [x] Program HTML 경로 설계
- [x] data-program-id 정의
- [x] default transaction 정의
- [x] `_shared` Resource 목록
- [x] Field ↔ DTO Mapping
- [x] Button/Event ↔ Service ID
- [x] Loading/Error/Empty/Success 상태
- [x] Mobile First Layout
- [x] 민감정보 노출 정책
- [x] userId Client 제거
- [x] Service ID URL/Header 일치 계약
- [x] UI Contract Test 설계
- [x] 다음 구현 Blocker 명시

**TASK 07 UI 상세설계 상태: COMPLETE**

**TASK 08/09 구현 상태: BLOCKED — TASK 03 Auth Architecture 미확정**
