# Framework Source Map

Repository root from this harness: `../` (= NSIGHT-TCF-FRAMEWORK).

## Write targets

| Edition | Path | Audience |
| --- | --- | --- |
| Base (default) | `../ztcfbook` | 업무·운영 개발자 |
| Beginner | `../ztcfbook-m` | 초보 |
| Master | `../ztcfbook-h` | 아키텍트·시니어 |

Harness TOC SoT: `../../TOC.md` · `../../toc.json` · workspaces `../../chapters/{id}/`  
Canonical reader TOC: `../ztcfbook/00-목차.md`  
Chapter scaffold map: `../ztcfbook/_gen-book-chapters.cjs`

## Read priority (SoT)

| Priority | Area | Paths |
| --- | --- | --- |
| 1 | Live code | `../tcf-core`, `../tcf-web`, `../tcf-*`, `../pdmp-service`, `../sv-service`, other `*-service` |
| 2 | Manuals | `../znsight-man`, `../zman`, `../zguide` |
| 3 | Architecture | `../zarchitecture`, `../docs/architecture` or `../zdocs-1/architecture` |
| 4 | Ops / capacity | `../ztcf-book-capacity-md`, `../znsight-config-*` |
| 5 | Existing books | `../ztcfbook`, `../ztcfbook-m`, `../ztcfbook-h` |
| 6 | Research only | `../ztcf-다이어리` — never sole SoT for identifiers |

## Gap handling

When design docs disagree with code, open:

- `../zman/00-설계서-코드베이스-대조표.md`

Record both sides in `analysis-summary.md` and state which side the chapter will teach.

## Typical chapter layout under ztcfbook

```text
ztcfbook/
  00-목차.md
  서문/
  제01편/ … 제10편/
  부록/
```
