# HG-30 승인 요청서 (AA / DA / SEC)

Run: `SAMPLE-AV-INQUIRY`  
Gate: `HG-30` Design Gate  
상태: **PENDING_APPROVAL**

## 설계 요약
- ServiceId: `AV.AssetValuation.selectList` (BA 확정)
- 패턴: `AV.CustomerContact.selectList` 동형 6계층
- 화면 UI: 제외
- DB: `AV_ASSET_VALUATION` **초안** (소유권 미확정)

## 승인 체크

| 역할 | 항목 | 결정 |
| --- | --- | --- |
| AA | ADR-001 / Handler~Mapper 구조 |  |
| DA | 테이블·컬럼·소유 도메인 확정 (GAP-AV-0001) |  |
| SEC | 조회·개인정보·감사 설계 |  |

승인 후:

```bash
node scripts/record_gate.cjs --id SAMPLE-AV-INQUIRY --gate HG-30 --decision PASS --summary "AA/DA/SEC approved"
node scripts/promote_stage.cjs --id SAMPLE-AV-INQUIRY
```
