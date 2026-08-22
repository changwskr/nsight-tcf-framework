---
document-status: AS-IS
system-scope: PDMG_REFERENCE
reference-project-set: [pdmg-ui, pdmg-fw, pdmg-service, pdmg-jwt]
last-verified: 2026-08-17
---

# REFERENCE PROJECTS


Reference Project Set은 다음 4개로 고정한다.

| Project | Role | Java | Spring | Gradle | Packaging | Package Root | Main Java | Test Java | Dependency |
|---|---|---|---|---|---|---|---:|---:|---|
| pdmg-ui | UI / Transaction Catalog / Relay | Java 21 | Spring Boot 3.5.14 | Gradle 8.10.1 | Boot JAR `pdmg-ui.jar` | `nhnis.mg.ui` | 8 | 0 | 독립 Root |
| pdmg-fw | Framework / TCF Library | Java 21 | Spring Boot 3.5.14 | Gradle 8.10.1 | Library JAR (`bootJar=false`, `jar=true`) | `nhnis.fw.*` + 일부 `com.ims.*` | 91 | 2 | 독립 Root |
| pdmg-service | Business Reference Application | Java 21 | Spring Boot 3.5.14 | Gradle 8.10.1 | External Tomcat WAR (`bootWar=false`) | `nhnis.mg.co.a.*` | 75 | 4 | `:pdmg-fw` sibling include |
| pdmg-jwt | JWT Reference Service | Java 21 | Spring Boot 3.5.14 | Gradle 8.10.1 | External Tomcat WAR (`bootWar=false`) | `nhnis.mg.jw.a.*` | 70 | 0 | `:pdmg-fw` sibling include |

Source counts는 제공된 2026-08-17 추출본에서 `build/`를 제외하고 계산한 값이다. Git metadata가 신뢰 가능한 형태로 포함되지 않아 Branch/Commit은 `UNKNOWN`이다.
