# 자동코딩 하네스 Golden Path

정본 Workflow: `참고소스/workflows/wf-online-inquiry-v1.yaml` (= sync 후 `harness/workflows/...`)

```text
S00-INPUT      HG-00  입력
S10-BASELINE   HG-10  Baseline
S20-ANALYSIS   HG-20  요구+도메인 (BA)
S30-DESIGN     HG-30  설계+ADR (AA/DA/SEC)
S40-PLAN       —     구현계획
S50-IMPLEMENT  HG-40  code+sql+test 생성
S60-BUILD      HG-50  Build Evidence
S70-TEST       HG-60  Test Evidence
S80-VERIFY     HG-70  품질·보안 (HUMAN)
S85-TRACE      HG-80  추적·Drift (AA)
S90-FINAL      HG-90  패키징 (AA+QA)
```

## 사람 승인

| Gate | 역할 |
| --- | --- |
| HG-20 | BA |
| HG-30 | AA, DA, SEC |
| HG-70 | (rule HUMAN 포함 시) |
| HG-80 | AA |
| HG-90 | AA, QA |

## 레거시 Stage 별칭

초기 하네스 draft ID는 `promote_stage.cjs`가 자동 정규화한다.  
예: `S120-TRACE` → `S85-TRACE`
