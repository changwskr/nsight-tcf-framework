# PDMG EOS (`pdmg-eos`)

EOL/EOS(수명주기) 업무용 Spring Boot 애플리케이션입니다.  
Gradle·Boot 버전과 `pdmg-fw` 연결 방식은 [`pdmg-service`](../pdmg-service/README.md)를 따릅니다.

| 항목 | 값 |
|------|-----|
| Boot | 3.5.14 |
| Java | 21 |
| Gradle | 8.10.1 (wrapper) |
| Port | `8082` |
| Main | `nhnis.eos.PdmgEosApplication` |
| FW | `../pdmg-fw` (`implementation project(':pdmg-fw')`) |
| 패키지 | `nhnis.eos.co.a.*` (서비스 ID 접두 `eoscoa`) |

```text
DefaultFilter / ServicePreventionInterceptor (pdmg-fw)
  → OnlineTransactionController (공통, TCF ON)
    → Handler → Facade(@Transactional)
      → BizPrePostAspect → Service → DAO
```

## 샘플 프로그램

| 프로그램 | API | 설명 |
|---|---|---|
| `eoscoa0100` | `POST /eoscoa0100S0` | EOL/EOS 자산 목록 조회 |

요청 Body 예: `{"hdr_nhnis":{...},"dto":{"riskCd":"HIGH"}}`

## 패키지 구성

| 패키지 | 설명 |
|---|---|
| `nhnis.eos.co.a.entry` | 진입 (`handler`, `aspect`) |
| `nhnis.eos.co.a.application` | 업무 (`controller`, `facade`, `service`) |
| `nhnis.eos.co.a.dto` | 입출력 DTO |
| `nhnis.eos.co.a.persistence` | MyBatis DAO |
| `nhnis.eos.co.a.config` | Security / MyBatis / UTF-8 |
| `pdmg-fw` (`nhnis.fw.*`) | 공통 Filter·TCF |

## 빌드·실행

```powershell
cd pdmg-eos
.\gradlew.bat compileJava
.\script\run.bat
```

- URL: http://localhost:8082  
- `settings.gradle`이 형제 프로젝트 `../pdmg-fw`를 `include` 합니다.
