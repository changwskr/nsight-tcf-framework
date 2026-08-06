# HG-20 승인 요청서 (BA)

Run: `SAMPLE-AV-INQUIRY`  
Gate: `HG-20` Analysis Gate  
상태: **PENDING_APPROVAL**

## 요약

자산평가 목록 조회 요구를 REQ 5건으로 구조화했습니다.  
구현으로 넘기기 전에 아래를 확정해 주세요.

## 승인 항목

| ID | 내용 | 제안 | 결정(작성) |
| --- | --- | --- | --- |
| REQ-AV-0001~0005 | 요구·수용기준 | DRAFT → CONFIRMED |  |
| ASM-AV-0001 | 평가일자 단일일 검색 | 수용 |  |
| ASM-AV-0002 | ServiceId = `AV.AssetValuation.selectList` | 수용 |  |
| GAP-AV-0003 | 화면 UI 1차 제외(서버 거래만) | 제외 |  |
| GAP-AV-0001 | DB 테이블 소유권 | HG-30에서 DA 확인 | (인지) |
| GAP-AV-0002 | ServiceId 최종 확정 | ASM-AV-0002와 연동 |  |

## 산출물

- `20-ANALYSIS/requirements/requirement-register.yaml`
- `20-ANALYSIS/assumptions/assumption-register.yaml`
- `20-ANALYSIS/gaps/gap-register.yaml`
- `20-ANALYSIS/evidence/source-evidence.yaml`
- `70-REVIEW/gates/HG-20.json`

## 승인 후 명령

```bash
node scripts/record_gate.cjs --id SAMPLE-AV-INQUIRY --gate HG-20 --decision PASS --summary "BA approved ..."
node scripts/promote_stage.cjs --id SAMPLE-AV-INQUIRY
```
