이 프로젝트는 **책을 쓰는** TOC 구동 하네스다.  
제품은 `ztcfbook` 본문이다. `IN/`·`OUT/` 작업장 패턴을 쓰지 않는다.

1. `TOC.md`에서 항목 선택 → `chapters/{id}/TASK.md` 읽기  
2. 출처·실코드 조사  
3. `docs/UI_GUIDE.md` 풍부함 기준으로 **자세하고 풍부한 장**을 `target`에 집필  
4. `toc.json` 완료 표시 → `node scripts/sync_toc_chapters.cjs`

채팅 예: `TOC의 CH-22를 자세하고 풍부하게 집필해줘`
