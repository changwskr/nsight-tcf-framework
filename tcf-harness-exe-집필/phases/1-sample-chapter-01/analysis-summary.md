# analysis-summary.md — 1-sample-chapter-01

## Scope

- 대상 장: 제1장 「NSIGHT TCF란 무엇인가」
- 경로: `../ztcfbook/제01편/01-NSIGHT-TCF란-무엇인가.md`
- 작업 유형: 하네스 샘플 실행 — 출처 대조 후 **최소 수정**(깨진 링크·코드 Gap 명시). 전면 재집필 아님.

## Edition target

- `ztcfbook` (base). `-m`/`-h` 미러는 본 phase 범위 밖.

## Source inventory

| 절 | 출처 | 존재 |
| --- | --- | --- |
| 1.1 | `znsight-man/03-TCF-개발원칙.md` | OK |
| 1.1 | `zman/05-TCF처리구조.md` | OK |
| 1.2 | `docs/architecture/architecture.md` | **MISS** → 실제 SoT `zdocs-1/architecture/architecture.md` |
| 1.3 | `znsight-man/22-Online-Endpoint-기준.md` | OK |
| 1.3 | `zman/06-표준전문구조.md` | OK |
| 1.4 | `znsight-man/10-bootRun-Tomcat-WAR-차이.md` | OK |
| 1.4 | `ztomcat/README.md` | OK |
| 1.5 | `znsight-man/05-개발자-역할과-책임.md` | OK |
| Gap | `zman/00-설계서-코드베이스-대조표.md` | OK |
| Code | `tcf-core/.../StandardHeader.java` (`userId`, `branchId`) | OK |
| Code | `pdmp-service/.../StandardHeaderDto.java` (`@JsonAlias("user"|"branch")`) | OK |

이전·다음 링크: `../서문/00-서문.md`, `./02-전체-시스템-구조.md` — OK  
목차: `../00-목차.md` — OK

## Conflicts

1. **architecture 경로**: `_gen-book-chapters.cjs`·장 출처 색인이 `docs/architecture/architecture.md`를 가리키나 저장소에는 `zdocs-1/architecture/architecture.md`만 존재.
2. **Header 필드명**: 매뉴얼 예시는 `user`/`branch`, 코드·본문 예시는 `userId`/`branchId`. 코드 SoT + JsonAlias로 양립. 본문은 코드 필드 유지, 별칭 한 줄 명시.
3. **Handler 단위**: 설계서는 serviceId당 1 Handler, 코드는 도메인 Handler + `serviceIds()` (`zman/00`). 본문에 Gap 명시 필요.

## Open questions

- 없음 (샘플 phase 범위에서 처분 가능). `docs/architecture` 심볼릭/이동은 별도 저장소 정리 과제.

## Acceptance evidence

```text
Test-Path chapter/sources → 위 inventory
Select-String chapter 'docs/architecture' → broken link confirmed
```

User trigger for execution: chat request 「실행해줘」(2026-08-05).
