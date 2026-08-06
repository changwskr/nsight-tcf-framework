# NSIGHT 자동 하네스 작업공간 상세기준

## 1. 도입 전 안내말

작업공간은 단순한 파일 저장 폴더가 아니라 원본 보존, 단계별 산출물 격리, Gate 승격, Evidence 보존과 Run 재현성을 강제하는 물리적 통제 경계다.

```text
00-IN → 10-BASELINE → 20-ANALYSIS → 30-DESIGN
→ 40-IMPLEMENTATION → 50-TEST → 60-EVIDENCE
→ 70-REVIEW → 80-STAGING → 90-OUT → 99-ARCHIVE
```

## 2. 디렉터리 구조

```text
{runId}/
├─ 00-IN/
│  ├─ requirements/
│  ├─ source/
│  ├─ database/
│  ├─ reference/
│  ├─ constraints/
│  ├─ quarantine/
│  └─ input-manifest.json
├─ 10-BASELINE/
│  ├─ baseline.yaml
│  ├─ source-inventory.json
│  ├─ document-inventory.json
│  ├─ terminology.yaml
│  ├─ technology-baseline.yaml
│  └─ baseline-manifest.json
├─ 20-ANALYSIS/
│  ├─ requirements/
│  ├─ domain/
│  ├─ assumptions/
│  ├─ gaps/
│  ├─ evidence/
│  ├─ trace/
│  └─ analysis-manifest.json
├─ 30-DESIGN/
│  ├─ architecture/
│  ├─ screen/
│  ├─ transaction/
│  ├─ program/
│  ├─ data/
│  ├─ interface/
│  ├─ security/
│  ├─ operation/
│  ├─ adr/
│  ├─ plan/
│  └─ design-manifest.json
├─ 40-IMPLEMENTATION/
│  ├─ worktree/
│  ├─ generated/{source,resources,mapper,sql,config,om}/
│  ├─ patches/
│  ├─ diff/
│  ├─ rejected/
│  └─ implementation-manifest.json
├─ 50-TEST/
│  ├─ unit/
│  ├─ integration/
│  ├─ contract/
│  ├─ architecture/
│  ├─ security/
│  ├─ performance/
│  ├─ fixtures/
│  └─ test-manifest.json
├─ 60-EVIDENCE/
│  ├─ build/
│  ├─ test/
│  ├─ quality/
│  ├─ security/
│  ├─ trace/
│  ├─ drift/
│  ├─ runtime/
│  ├─ tool-invocations/
│  └─ evidence-manifest.json
├─ 70-REVIEW/
│  ├─ gates/
│  ├─ approvals/
│  ├─ exceptions/
│  ├─ issues/
│  ├─ comments/
│  └─ review-manifest.json
├─ 80-STAGING/{documents,source,sql,configuration,evidence}/
├─ 90-OUT/{documents,source-package,database-package,om-package,test-package,evidence-package,final-report}/
├─ 95-CHECKPOINT/{state,manifests,recovery}/
├─ 99-ARCHIVE/{audit,logs}/
└─ run.yaml
```

## 3. 접근·변경 정책

| 구역 | 변경 정책 | 주 작성자 | 승격 조건 |
|---|---|---|---|
| `00-IN` | 불변·격리 | Intake Agent | HG-00 |
| `10-BASELINE` | 확정 후 불변 | Baseline Agent | HG-10 |
| `20-ANALYSIS` | 분석 Gate 전까지 | Requirement/Domain Agent | HG-20 |
| `30-DESIGN` | 설계 Gate 전까지 | Design/ADR Agent | HG-30 |
| `40-IMPLEMENTATION` | Worktree에서만 변경 | Code/SQL Agent | HG-40·50 |
| `50-TEST` | 테스트 자산만 변경 | Test Agent | HG-60 |
| `60-EVIDENCE` | Append Only | Tool/Quality/Security Agent | Gate 입력 |
| `70-REVIEW` | Gate·승인자만 변경 | Gate Engine/Approver | 승인 결과 |
| `80-STAGING` | Promotion Manager 전용 | Orchestrator | HG-90 전 검토 |
| `90-OUT` | 생성 후 불변 | Packaging Agent | HG-90 PASS |
| `95-CHECKPOINT` | Orchestrator 전용 | Orchestrator | 복구용 |
| `99-ARCHIVE` | Object Lock 권장 | Archive Manager | Run 종료 |

## 4. 파일 작성 규칙

1. 모든 파일은 임시파일 작성 → Schema/Hash 검증 → Atomic Rename 순으로 생성한다.
2. 원본 파일은 덮어쓰지 않고 새 `ArtifactVersion`을 생성한다.
3. 모든 Artifact에는 SHA-256, mediaType, size, producerAgent, contractVersion을 기록한다.
4. Manifest에 등록되지 않은 파일은 정식 산출물로 인정하지 않는다.
5. `90-OUT`에는 승인된 Manifest가 참조하는 파일만 복사한다.
6. Evidence 파일은 Append Only로 관리하고 수정 시 새 Evidence를 생성한다.
7. Git 소스 변경은 Run 전용 Worktree에서만 수행한다.

## 5. Promotion 처리

```text
Gate PASS
→ Artifact 상태 APPROVED
→ Approved Manifest 생성
→ 다음 Stage Input Manifest에 등록
→ 최종 패키징 시에만 80-STAGING으로 복사
```

`PASS_WITH_EXCEPTION`은 예외 ID, 만료일, 보완통제와 함께 승격한다. `FAIL`, `BLOCKED`, `PENDING_APPROVAL`은 승격을 금지한다.

## 6. 운영·복구 기준

- Run마다 Workspace Quota를 설정한다.
- Checkpoint 시점에 상태 DB 버전과 Manifest Hash를 함께 저장한다.
- Orchestrator 재기동 시 마지막 일관된 Checkpoint부터 복구한다.
- 상태 DB 미등록 파일은 `ORPHAN`으로 탐지해 격리한다.
- Archive는 입력·기준선·승인·Evidence·최종 Manifest를 포함해야 한다.
