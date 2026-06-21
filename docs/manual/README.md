# NSIGHT TCF — 운영·개발 매뉴얼

| 항목 | 내용 |
|------|------|
| 대상 | 로컬 개발·빌드·배포 담당자 |
| 관련 | [README.md](../../README.md), [docs/architecture/22-build-project.md](../architecture/22-build-project.md), [environment-variables.md](environment-variables.md), [artifacts.md](artifacts.md), [lib-module.md](lib-module.md), [docs/architecture/38-script.md](../architecture/38-script.md) |

---

## 문서 목록

| 문서 | 설명 |
|------|------|
| [gradle.md](gradle.md) | **Gradle 명령어** — bootRun, bootWar, 테스트, 일괄 빌드 |
| [environment-variables.md](environment-variables.md) | **환경변수·JVM 속성** — JAVA_HOME, NSIGHT_*, Spring 프로파일 |
| [artifacts.md](artifacts.md) | **빌드 산출물·기동 파일** — JAR/WAR 물리 경로, ztomcat 배치 |
| [lib-module.md](lib-module.md) | **라이브러리 모듈 참조** — 24 모듈 물리 경로, Gradle 캐시→WAR→Tomcat |

아키텍처·모듈 구조 상세: [22-build-project.md](../architecture/22-build-project.md)  
스크립트 래퍼(`tcf-scripts`, `ztomcat`): [38-script.md](../architecture/38-script.md)
