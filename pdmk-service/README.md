# PDMK Service (`pdmk-service`)

PDMK 업무 샘플 애플리케이션입니다. **공통 FW는 [`pdmk-fw`](../pdmk-fw/README.md)에 의존**합니다.

운영 트랜잭션 로그(예: `mkpca5530`)와 동일한 계층을 따릅니다.

```text
ServicePreventionInterceptor (pdmk-fw)
  → BizPrePostAspect (nhnis.mk.co.common)
    → Controller (nhnis.mk.co.a.controller.*)
      → Service (nhnis.mk.co.a.service.*)
```

## 샘플 프로그램

| 프로그램 | API | 설명 |
|---|---|---|
| `mkpca5530` | `POST /api/mk/co/a/5530/list` | 운영 로그 대응 안내항목 목록 (`mkpca5530S0`) |
| `mkpca9999` | `POST /api/mk/co/a/9999/list` | 영업팁 실적 조회 |
| `mkpca8888` | `POST /api/mk/co/a/8888/**` | 영업팁 실적 CRUD (JWT) |

`mkpca5530` 요청 Body 예: `{"dto":{"basDt":"20260801"}}` (로컬 H2 시드 3건 → `Total: 3`).

## 패키지 구성

| 패키지 / 모듈 | 설명 |
|---|---|
| `nhnis.mk.*` | Boot 앱, 업무 CRUD, Security/MyBatis |
| `nhnis.mk.co.common` | `BizPrePostAspect` 업무 공통 선·후처리 |
| `pdmk-fw` (`nhnis.fw.*`) | `ServicePreventionInterceptor`, TCF/commons |

## 빌드

```powershell
cd pdmk-service
.\gradlew.bat compileJava
.\gradlew.bat bootWar
```

`settings.gradle`이 형제 프로젝트 `../pdmk-fw`를 `include` 합니다.
