# TASK: CH-10-10-TransactionHandler-개발

## 이 작업은 책을 쓰는 일이다

산출물의 중심은 `IN/`·`OUT/` 메모가 아니라 **원고 본문** `../ztcfbook/제03편/10-TransactionHandler-개발.md` 이다.  
독자가 이 장만 읽어도 개념·흐름·코드·실수·검증까지 이해할 수 있게 **자세하고 풍부하게** 쓴다.

| 항목 | 값 |
| --- | --- |
| 목차 ID | `CH-10-10-TransactionHandler-개발` |
| 편 | 제3편 · 거래 개발 실무 (`제03편`) |
| 번호 | 10 |
| 제목 | TransactionHandler 개발 |
| 원고 | `../ztcfbook/제03편/10-TransactionHandler-개발.md` |
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

- **10.1** Handler 등록·serviceIds() 패턴
  - sources: `znsight-man/23-TransactionHandler-개발.md`, `zman/08-업무Handler개발.md`
- **10.2** Online Endpoint 호출 규약
  - sources: `znsight-man/22-Online-Endpoint-기준.md`
- **10.3** Facade · Service · Rule 구현
  - sources: `znsight-man/24-Facade-개발.md`, `znsight-man/25-Service-개발.md`, `znsight-man/26-Rule-개발.md`
- **10.4** DAO · MyBatis Mapper
  - sources: `znsight-man/27-DAO-개발.md`, `znsight-man/28-MyBatis-Mapper-개발.md`, `docs/architecture/07-DAO.md`, `docs/architecture/26-mybatis.md`
- **10.5** SQL 작성·페이징
  - sources: `znsight-man/29-SQL-작성-기준.md`, `znsight-man/30-페이징-처리-기준.md`, `docs/architecture/27-paging.md`
- **10.6** 서비스 간 연동 (tcf-eai)
  - sources: `znsight-man/31-서비스간-연동-개발.md`, `docs/architecture/46-service-integration-contract.md`, `zguide/tcf-eai-개발가이드.md`

필요하면 절을 **세분**해도 된다 (예: 22.2.1 Handler, 22.2.2 Facade…). 목차 대절 ID는 유지하되 소절을 풍부히 한다.

## 읽을 출처

- `znsight-man/23-TransactionHandler-개발.md`
- `zman/08-업무Handler개발.md`
- `znsight-man/22-Online-Endpoint-기준.md`
- `znsight-man/24-Facade-개발.md`
- `znsight-man/25-Service-개발.md`
- `znsight-man/26-Rule-개발.md`
- `znsight-man/27-DAO-개발.md`
- `znsight-man/28-MyBatis-Mapper-개발.md`
- `docs/architecture/07-DAO.md`
- `docs/architecture/26-mybatis.md`
- `znsight-man/29-SQL-작성-기준.md`
- `znsight-man/30-페이징-처리-기준.md`
- `docs/architecture/27-paging.md`
- `znsight-man/31-서비스간-연동-개발.md`
- `docs/architecture/46-service-integration-contract.md`
- `zguide/tcf-eai-개발가이드.md`

코드 SoT 후보: `sv-service`, `tcf-core`, `tcf-web`, `*-service` 등 해당 장 주제 모듈.

## 규칙

1. CRITICAL: 출처·코드에 없는 ServiceId·포트·SQL·패키지 창작 금지.
2. CRITICAL: `node _gen-book-*.cjs` 무단 실행 금지.
3. CRITICAL: 이 항목의 `target`만 수정. 다른 장 동시 개편 금지.
4. CRITICAL: `IN/`·`OUT/` 디렉터리를 새로 만들지 마라.
5. 문체: `docs/UI_GUIDE.md` (풍부한 서술 가이드).

## 완료 조건

- `../ztcfbook/제03편/10-TransactionHandler-개발.md` 이 위 「집필 목표」를 충족
- 출처 색인·네비 유지
- `toc.json` 해당 항목 `status=completed` 후 `node scripts/sync_toc_chapters.cjs`
