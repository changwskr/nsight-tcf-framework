# factcheck-report.md — 1-sample-chapter-01

## Cited sources checked

| Claim / path | Evidence | Result |
| --- | --- | --- |
| 10 개발 원칙 | `znsight-man/03-TCF-개발원칙.md` §3.2 | OK |
| STF→Dispatcher→Handler→ETF | `zman/05-TCF처리구조.md` | OK (TimeoutExecutor는 상세장) |
| POST `/{businessCode}/online` | `zman/00`, `znsight-man/03` | OK |
| Online Endpoint vs REST | `znsight-man/22`, `zman/06` | OK |
| bootRun vs WAR | `znsight-man/10`, `ztomcat/README` | OK |
| RACI 역할 구분 | `znsight-man/05` | OK (표 수치는 매뉴얼 요약과 정합) |
| architecture 출처 링크 | `zdocs-1/architecture/architecture.md` exists | OK (수정 후) |
| Header `userId`/`branchId` | `tcf-core/.../StandardHeader.java` | OK |
| Alias `user`/`branch` | `pdmp-service/.../StandardHeaderDto.java` `@JsonAlias` | OK |
| Handler `serviceIds()` | `zman/00-설계서-코드베이스-대조표.md` | OK |

## Identifier audit

| Identifier | Status |
| --- | --- |
| `SV.Customer.selectSummary` | 예시(매뉴얼 동일) — 예시로 표기됨 |
| `SV-INQ-0001` | 예시 |
| `OM.Auth.login` | 서술 예시 — 본 phase에서 코드 전수 검증 안 함 → **defer** (제5편/실습장) |
| 포트 8086/8097/8100/8080 | `zman/00`·기존 장과 일치 범위 — 모듈 전수 재측정 안 함 → **defer** (제2장·부록) |

## Code-vs-doc gaps

| Gap | Disposition |
| --- | --- |
| Handler 1:1 vs `serviceIds()` | **fix** — 본문에 명시 |
| `docs/architecture` missing | **fix** — 출처를 `zdocs-1`로 교체 |
| man `user`/`branch` vs code fields | **fix** — JsonAlias 안내 |

## Findings

1. (High, fixed) 깨진 architecture 링크
2. (Medium, fixed) Handler 단위 Gap 미언급
3. (Low, fixed) Header 별칭 혼란 가능
4. (Info, defer) TOC/gen 스크립트의 구 경로 문자열

## Decision

**PASS** — High/Medium 처분 완료. Defer 항목은 후속 phase.
