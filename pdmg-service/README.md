# PDMG Service (`pdmg-service`)

PDMG 업무 샘플 애플리케이션입니다. **공통 FW는 [`pdmg-fw`](../pdmg-fw/README.md)에 의존**합니다.

네이밍은 [`docs/MG-NAMING_CONVENTION.md`](docs/MG-NAMING_CONVENTION.md) 를 따른다.

```text
DefaultFilter / ServicePreventionInterceptor (pdmg-fw)
  → OnlineTransactionController (공통, TCF ON)
    → Handler → Facade(@Transactional)
      → BizPrePostAspect → Service → DAO
```

### 문서

| 문서 | 내용 |
|------|------|
| [docs/01.트랜잭션처리 변경.md](docs/01.트랜잭션처리%20변경.md) | Service Pointcut · TX 범위 |
| [docs/pdmg-service 트랜잭션흐름.md](docs/pdmg-service%20트랜잭션흐름.md) | TCF ON 전체 흐름 |
| [docs/트랜잭션처리.md](docs/트랜잭션처리.md) | 요청흐름 vs DB TX |
| [docs/MG-NAMING_CONVENTION.md](docs/MG-NAMING_CONVENTION.md) | 네이밍 |

## 샘플 프로그램

| 프로그램 | API | 설명 |
|---|---|---|
| `mgcoa8888` | `POST /mgcoa8888S0`, `POST /mgcoa8888D0` | 이미지로그 조회/삭제 |
| `mgcoa5530` | `POST /mgcoa5530S0` | 마케팅희망고객(안내항목) 목록 |
| `mgcoa9999` | `POST /mgcoa9999S0` | 영업팁 실적 목록 |

요청 Body 예: `{"hdr_nhnis":{...},"dto":{...}}` 또는 local에서 `{"dto":{...}}`.

## 패키지 구성

| 패키지 / 모듈 | 설명 |
|---|---|
| `nhnis.mg.entry` | 진입 (`handler`, `facade`, `aspect`; TCF OFF 시 `controller`) |
| `nhnis.mg.application` | 업무 처리 (`service`, `dto`) |
| `nhnis.mg.persistence` | MyBatis DAO |
| `nhnis.mg.config` | Security / MyBatis / CORS 등 |
| `nhnis.mg.client` | 외부 연동 클라이언트 (필요 시) |
| `nhnis.mg.support` | 유틸 (`MappingUtil`) |
| `pdmg-fw` (`nhnis.fw.*`) | `ServicePreventionInterceptor`, commons |

## 빌드

```powershell
cd pdmg-service
.\gradlew.bat compileJava
.\gradlew.bat bootWar
```

`settings.gradle`이 형제 프로젝트 `../pdmg-fw`를 `include` 합니다.
