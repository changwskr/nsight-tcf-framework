# Implementation Plan — SAMPLE-AV-INQUIRY

## Create
- AvAssetValuationHandler/Facade/Service/Rule/Dao/Mapper + DTOs
- `AvAssetValuationMapper.xml`
- `schema.sql`에 `AV_ASSET_VALUATION` + 샘플 MERGE (DA 승인 후)
- Unit/Integration test (selectList 페이징)
- OM catalog draft under `40-IMPLEMENTATION/generated/om/`

## Modify
- (필요 시) 컴포넌트 스캔 범위 — 동일 패키지면 변경 없음

## Test plan
- Rule: pageNo/pageSize/evalDate 검증
- Service/Dao: 페이징 totalCount
- Handler: ServiceId 라우팅
- ArchUnit: Service→Mapper 직접호출 없음

## Out of scope
- 화면 UI
- 운영 DDL 실행 / Push
