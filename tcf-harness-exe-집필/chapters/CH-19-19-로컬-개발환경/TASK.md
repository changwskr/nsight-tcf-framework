# TASK: CH-19-19-로컬-개발환경

## 이 작업은 책을 쓰는 일이다

산출물의 중심은 `IN/`·`OUT/` 메모가 아니라 **원고 본문** `../ztcfbook/제06편/19-로컬-개발환경.md` 이다.  
독자가 이 장만 읽어도 개념·흐름·코드·실수·검증까지 이해할 수 있게 **자세하고 풍부하게** 쓴다.

| 항목 | 값 |
| --- | --- |
| 목차 ID | `CH-19-19-로컬-개발환경` |
| 편 | 제6편 · 환경·빌드·배포 (`제06편`) |
| 번호 | 19 |
| 제목 | 로컬 개발환경 |
| 원고 | `../ztcfbook/제06편/19-로컬-개발환경.md` |
| 에디션 | `ztcfbook` (변경 시 승인) |

## 집필 목표 (풍부함)

1. **왜** 이 장이 필요한지 서문으로 연다 (문제·맥락·독자).
2. 절마다 **설명 + 표/흐름 + 실제 코드·경로 발췌 + 주의(실수) + 확인 방법**을 둔다.
3. 코드는 저장소 **실파일**을 인용한다. 가상 예시면 `(예시)`를 붙인다.
4. 설계서와 코드가 다르면 Gap을 숨기지 말고 본문에 쓴다.
5. 분량: 실습·핵심 장은 **얇은 요약서가 아니라 가이드 챕터** 수준 (여러 소절·풍부한 서술).
6. 장 말미: 장 요약 · 이전/다음 · 출처 색인.

`analysis-summary.md` 같은 핸드오프 파일을 만들지 마라. 검증 메모가 필요하면 본문 「디버깅·검증」 절에 녹인다. 상태는 `toc.json`만 갱신한다.

## 절 구성

- **19.1** JDK·Gradle·DB·IDE 구성
  - sources: `znsight-man/06-로컬-개발환경-구성.md`
- **19.2** application.yml·Profile
  - sources: `znsight-man/11-application-yml-기준.md`, `docs/architecture/20-env-spring.md`, `docs/architecture/25-env-profile.md`, `znsight-man/부록G-application-yml-템플릿.md`
- **19.3** Spring/Tomcat/Apache 환경
  - sources: `docs/architecture/20-env-spring.md`, `docs/architecture/21-env-tomcat.md`, `docs/architecture/23-env-apache.md`, `docs/architecture/24-env-spring-detail.md`, `zman/20-Spring환경설정.md`
- **19.4** 로컬 빌드·bootRun
  - sources: `znsight-man/63-로컬-빌드-방법.md`, `zguide/tcf-scripts-개발가이드.md`
- **19.5** ztomcat 8080 통합 검증
  - sources: `ztomcat/README.md`, `znsight-man/10-bootRun-Tomcat-WAR-차이.md`

필요하면 절을 **세분**해도 된다 (예: 22.2.1 Handler, 22.2.2 Facade…). 목차 대절 ID는 유지하되 소절을 풍부히 한다.

## 읽을 출처

- `znsight-man/06-로컬-개발환경-구성.md`
- `znsight-man/11-application-yml-기준.md`
- `docs/architecture/20-env-spring.md`
- `docs/architecture/25-env-profile.md`
- `znsight-man/부록G-application-yml-템플릿.md`
- `docs/architecture/21-env-tomcat.md`
- `docs/architecture/23-env-apache.md`
- `docs/architecture/24-env-spring-detail.md`
- `zman/20-Spring환경설정.md`
- `znsight-man/63-로컬-빌드-방법.md`
- `zguide/tcf-scripts-개발가이드.md`
- `ztomcat/README.md`
- `znsight-man/10-bootRun-Tomcat-WAR-차이.md`

코드 SoT 후보: `sv-service`, `tcf-core`, `tcf-web`, `*-service` 등 해당 장 주제 모듈.

## 규칙

1. CRITICAL: 출처·코드에 없는 ServiceId·포트·SQL·패키지 창작 금지.
2. CRITICAL: `node _gen-book-*.cjs` 무단 실행 금지.
3. CRITICAL: 이 항목의 `target`만 수정. 다른 장 동시 개편 금지.
4. CRITICAL: `IN/`·`OUT/` 디렉터리를 새로 만들지 마라.
5. 문체: `docs/UI_GUIDE.md` (풍부한 서술 가이드).

## 완료 조건

- `../ztcfbook/제06편/19-로컬-개발환경.md` 이 위 「집필 목표」를 충족
- 출처 색인·네비 유지
- `toc.json` 해당 항목 `status=completed` 후 `node scripts/sync_toc_chapters.cjs`
