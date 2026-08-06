# TASK: CH-04-04-애플리케이션-6계층

## 이 작업은 책을 쓰는 일이다

산출물의 중심은 `IN/`·`OUT/` 메모가 아니라 **원고 본문** `../ztcfbook/제01편/04-애플리케이션-6계층.md` 이다.  
독자가 이 장만 읽어도 개념·흐름·코드·실수·검증까지 이해할 수 있게 **자세하고 풍부하게** 쓴다.

| 항목 | 값 |
| --- | --- |
| 목차 ID | `CH-04-04-애플리케이션-6계층` |
| 편 | 제1편 · TCF Framework 이해하기 (`제01편`) |
| 번호 | 4 |
| 제목 | 애플리케이션 6계층 |
| 원고 | `../ztcfbook/제01편/04-애플리케이션-6계층.md` |
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

- **4.1** Handler → Facade → Service → Rule → DAO → Mapper
  - sources: `docs/architecture/01-application-layer.md`, `zarchitecture/03-애플리케이션-6계층-아키텍처.md`
- **4.2** 계층별 책임·금지 사항
  - sources: `znsight-man/12-애플리케이션-계층구조.md`
- **4.3** Controller를 만들지 않는 이유
  - sources: `zguide/README.md`, `zman/08-업무Handler개발.md`
- **4.4** Facade 계층 설계
  - sources: `docs/architecture/29-facade.md`, `znsight-man/24-Facade-개발.md`
- **4.5** 트랜잭션·예외·로그 계층 기준
  - sources: `docs/architecture/03-transaction.md`, `docs/architecture/05-exception.md`, `znsight-man/32-예외처리-기준.md`, `znsight-man/36-트랜잭션-기준.md`

필요하면 절을 **세분**해도 된다 (예: 22.2.1 Handler, 22.2.2 Facade…). 목차 대절 ID는 유지하되 소절을 풍부히 한다.

## 읽을 출처

- `docs/architecture/01-application-layer.md`
- `zarchitecture/03-애플리케이션-6계층-아키텍처.md`
- `znsight-man/12-애플리케이션-계층구조.md`
- `zguide/README.md`
- `zman/08-업무Handler개발.md`
- `docs/architecture/29-facade.md`
- `znsight-man/24-Facade-개발.md`
- `docs/architecture/03-transaction.md`
- `docs/architecture/05-exception.md`
- `znsight-man/32-예외처리-기준.md`
- `znsight-man/36-트랜잭션-기준.md`

코드 SoT 후보: `sv-service`, `tcf-core`, `tcf-web`, `*-service` 등 해당 장 주제 모듈.

## 규칙

1. CRITICAL: 출처·코드에 없는 ServiceId·포트·SQL·패키지 창작 금지.
2. CRITICAL: `node _gen-book-*.cjs` 무단 실행 금지.
3. CRITICAL: 이 항목의 `target`만 수정. 다른 장 동시 개편 금지.
4. CRITICAL: `IN/`·`OUT/` 디렉터리를 새로 만들지 마라.
5. 문체: `docs/UI_GUIDE.md` (풍부한 서술 가이드).

## 완료 조건

- `../ztcfbook/제01편/04-애플리케이션-6계층.md` 이 위 「집필 목표」를 충족
- 출처 색인·네비 유지
- `toc.json` 해당 항목 `status=completed` 후 `node scripts/sync_toc_chapters.cjs`
