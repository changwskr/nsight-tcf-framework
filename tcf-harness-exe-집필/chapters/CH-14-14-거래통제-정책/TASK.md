# TASK: CH-14-14-거래통제-정책

## 이 작업은 책을 쓰는 일이다

산출물의 중심은 `IN/`·`OUT/` 메모가 아니라 **원고 본문** `../ztcfbook/제04편/14-거래통제-정책.md` 이다.  
독자가 이 장만 읽어도 개념·흐름·코드·실수·검증까지 이해할 수 있게 **자세하고 풍부하게** 쓴다.

| 항목 | 값 |
| --- | --- |
| 목차 ID | `CH-14-14-거래통제-정책` |
| 편 | 제4편 · 보안·인증·통제 (`제04편`) |
| 번호 | 14 |
| 제목 | 거래통제·정책 |
| 원고 | `../ztcfbook/제04편/14-거래통제-정책.md` |
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

- **14.1** Header 7항 Allow-List
  - sources: `docs/architecture/40-header-7-transaction-control.md`, `zman/13-거래통제.md`
- **14.2** businessCode · URL · Prefix 정합성
  - sources: `znsight-man/명명규칙-21-Header-항목.md`
- **14.3** OM 거래통제 등록 절차
  - sources: `znsight-man/48-거래통제-등록-절차.md`
- **14.4** Timeout·ServiceId Catalog OM 등록
  - sources: `znsight-man/47-ServiceId-등록-절차.md`, `znsight-man/49-Timeout-정책-등록.md`
- **14.5** 공통코드·오류코드 OM 등록
  - sources: `znsight-man/50-공통코드-사용-절차.md`, `znsight-man/51-오류코드-등록-절차.md`

필요하면 절을 **세분**해도 된다 (예: 22.2.1 Handler, 22.2.2 Facade…). 목차 대절 ID는 유지하되 소절을 풍부히 한다.

## 읽을 출처

- `docs/architecture/40-header-7-transaction-control.md`
- `zman/13-거래통제.md`
- `znsight-man/명명규칙-21-Header-항목.md`
- `znsight-man/48-거래통제-등록-절차.md`
- `znsight-man/47-ServiceId-등록-절차.md`
- `znsight-man/49-Timeout-정책-등록.md`
- `znsight-man/50-공통코드-사용-절차.md`
- `znsight-man/51-오류코드-등록-절차.md`

코드 SoT 후보: `sv-service`, `tcf-core`, `tcf-web`, `*-service` 등 해당 장 주제 모듈.

## 규칙

1. CRITICAL: 출처·코드에 없는 ServiceId·포트·SQL·패키지 창작 금지.
2. CRITICAL: `node _gen-book-*.cjs` 무단 실행 금지.
3. CRITICAL: 이 항목의 `target`만 수정. 다른 장 동시 개편 금지.
4. CRITICAL: `IN/`·`OUT/` 디렉터리를 새로 만들지 마라.
5. 문체: `docs/UI_GUIDE.md` (풍부한 서술 가이드).

## 완료 조건

- `../ztcfbook/제04편/14-거래통제-정책.md` 이 위 「집필 목표」를 충족
- 출처 색인·네비 유지
- `toc.json` 해당 항목 `status=completed` 후 `node scripts/sync_toc_chapters.cjs`
