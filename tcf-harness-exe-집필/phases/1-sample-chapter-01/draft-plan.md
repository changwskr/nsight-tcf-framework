# draft-plan.md — 1-sample-chapter-01

## Approved outline

사용자 요청 「실행해줘」(2026-08-05)로 **샘플 phase 전체 실행**을 승인으로 간주한다.  
범위: 제1장 최소 갱신(링크 수정 + Gap/별칭 명시). `_gen-book-*.cjs` 실행 **비승인**.

| 절 | 제목 | 핵심 메시지 | 조치 |
| --- | --- | --- | --- |
| 1.1 | TCF의 목적과 핵심 원칙 | 10원칙·거래 생명주기 통제 | 유지 |
| 1.2 | Handler·파이프라인·WAR | Handler 중심 + STF→Disp→ETF | Handler `serviceIds()` Gap 1문단 추가 |
| 1.3 | Online Endpoint | POST /{bc}/online + serviceId | JSON에 user/branch JsonAlias 주석 |
| 1.4 | bootRun vs ztomcat | 이중 배포 | 유지 |
| 1.5 | RACI | 역할·승인 | 유지 |
| 메타 | 출처 색인 | architecture 경로 | `zdocs-1/...`로 수정 |

## Section-to-source map

- 1.1 → `znsight-man/03-TCF-개발원칙.md`, `zman/05-TCF처리구조.md`
- 1.2 → `zdocs-1/architecture/architecture.md`, `zman/00-설계서-코드베이스-대조표.md`
- 1.3 → `znsight-man/22-Online-Endpoint-기준.md`, `zman/06-표준전문구조.md`, `tcf-core/.../StandardHeader.java`
- 1.4 → `znsight-man/10-bootRun-Tomcat-WAR-차이.md`, `ztomcat/README.md`
- 1.5 → `znsight-man/05-개발자-역할과-책임.md`

## Write paths

- `../ztcfbook/제01편/01-NSIGHT-TCF란-무엇인가.md`
- `phases/1-sample-chapter-01/*.md` 핸드오프

## Validation commands

```powershell
Test-Path -LiteralPath 'ztcfbook\제01편\01-NSIGHT-TCF란-무엇인가.md'
Test-Path -LiteralPath 'zdocs-1\architecture\architecture.md'
Select-String -LiteralPath 'ztcfbook\제01편\01-NSIGHT-TCF란-무엇인가.md' -Pattern 'zdocs-1/architecture/architecture.md'
Select-String -LiteralPath 'ztcfbook\제01편\01-NSIGHT-TCF란-무엇인가.md' -Pattern '출처 색인'
```

## Rollback

```powershell
git checkout -- 'ztcfbook/제01편/01-NSIGHT-TCF란-무엇인가.md'
```

## Fact-check

식별자·깨진 링크·Handler Gap·Header 별칭 대조 후 `factcheck-report.md`.

## Quality

TOC·네비·문체·`-m`/`-h` 후속 메모 → `qa-report.md`.
