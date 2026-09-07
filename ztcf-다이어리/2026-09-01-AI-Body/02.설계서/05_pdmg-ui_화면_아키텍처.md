# pdmg-ui 기반 Body AI 화면 아키텍처

- 상태: BASELINE
- 대상 모듈: `pdmg-ui`
- 화면 기술: HTML 중심
- 화면 단위: Program ID

---

## 1. 실제 pdmg-ui 구조

```text
pdmg-ui
└─ src/main
   │
   ├─ java
   │  └─ nhnis/mg/ui
   │     ├─ PdmgUiApplication.java
   │     ├─ application/service
   │     ├─ client
   │     ├─ config
   │     ├─ entry/web
   │     └─ support
   │
   └─ resources
      │
      ├─ application.yml
      ├─ sample-requests
      └─ static
         ├─ index.html
         ├─ _shared/
         └─ {ProgramId}/index.html
```

---

## 2. 업무 화면 원칙

업무 프로그램 화면은 다음 패턴을 사용한다.

```text
static/
└─ mgbyw1000/
   └─ index.html
```

화면별 별도 JS/CSS 프로젝트 구조를 만들지 않는다.

금지 예:

```text
mgbyw1000/
├─ index.html
├─ workout.js
├─ workout.css
└─ components.js
```

공통 동작/스타일은 기존 `_shared` 리소스를 사용한다.

---

## 3. 기존 공통 Resource

현재 pdmg-ui 소스 기준 주요 공통 파일:

```text
_shared/
├─ ui-context.js
├─ pdmg-shell.js
├─ service-client.js
├─ online-single.js
├─ error-codes.js
├─ error-popup.js
├─ jwt-admin.js
├─ online.css
├─ pdmg-workbench.css
├─ jwt-admin.css
└─ om-admin.css
```

Body AI 화면은 기존 공통 기능을 우선 재사용한다.

---

## 4. Program HTML 표준

예:

```html
<!DOCTYPE html>
<html lang="ko"
      data-program-id="mgbyw1000"
      data-default-transaction-id="mgbyw1000S0">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <script src="/_shared/ui-context.js"></script>
  <link rel="stylesheet" href="/_shared/online.css">
</head>
<body class="pdmg-page">

  <!-- 업무 화면 -->

  <script src="/_shared/error-codes.js"></script>
  <script src="/_shared/error-popup.js"></script>
  <script src="/_shared/service-client.js"></script>
  <script src="/_shared/online-single.js"></script>
</body>
</html>
```

화면에만 필요한 소규모 동작은 HTML 내부 `<script>`로 둘 수 있다.
공통화가 필요해질 경우에만 `_shared` 확장을 검토한다.

---

## 5. Body AI 화면 Directory 초안

```text
static/
│
├─ mgbyu1000/   # Profile
├─ mgbyu1100/   # Goal
├─ mgbyb1000/   # Body Measurement
├─ mgbyb1100/   # Body Timeline
├─ mgbya1000/   # Assessment
├─ mgbyw1000/   # Workout Program
├─ mgbyw1100/   # Workout Log
├─ mgbyn1000/   # Nutrition Plan
├─ mgbyn1100/   # Nutrition Log
├─ mgbyr1000/   # Daily Check-in / Recovery
├─ mgbyc1000/   # Daily Coaching
├─ mgbyc1100/   # Weekly Review
├─ mgbyp1000/   # Progress Dashboard
├─ mgbyh1000/   # Coach Dashboard
└─ mgbyi1000/   # AI Insight
```

위 Program 번호는 Registry 확정 전까지 `PROPOSED`다.

---

## 6. 화면 호출 구조

```text
{ProgramId}/index.html
       │
       ▼
_shared/online-single.js
       │
       ▼
_shared/service-client.js
       │
       │ POST /{ServiceId}
       │ hdr_nhnis + dto
       ▼
pdmg-service
       │
       ▼
Handler → Facade → Service → DAO
```

---

## 7. Main Shell

현재 `static/index.html`은 PDMG Workbench Shell 역할을 한다.

Body AI 전용 메뉴를 추가할 경우에도 우선 이 Shell 구조와 Hash Routing 방식을 유지한다.

예:

```text
#/body
#/workout
#/nutrition
#/recovery
#/coach
```

다만 실제 route 명과 메뉴 구성은 화면 Registry 확정 후 적용한다.

---

## 8. UX 원칙

기술 구조는 기존 pdmg-ui를 유지하되 서비스 UX는 다음을 지향한다.

- Mobile First
- 빠른 Daily Check-in
- Today 중심 Dashboard
- Readiness / Workout / Nutrition / Recovery 상태 가시화
- AI Recommendation은 근거와 함께 표시
- Coach 관리 회원은 Review 상태 표시
