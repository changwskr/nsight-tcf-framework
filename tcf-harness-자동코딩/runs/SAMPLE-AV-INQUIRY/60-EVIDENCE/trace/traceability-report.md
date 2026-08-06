# Traceability Report — SAMPLE-AV-INQUIRY

| From | To | Relation |
| --- | --- | --- |
| REQ-AV-0001 | AV.AssetValuation.selectList | SATISFIES |
| REQ-AV-0002 | pageNo/pageSize/totalCount | SATISFIES |
| REQ-AV-0003 | AV.AssetValuation.selectList | SATISFIES |
| REQ-AV-0004 | AvAssetValuationFacade @Transactional(readOnly) | SATISFIES |
| REQ-AV-0005 | no ops DDL / no autopush in harness | SATISFIES |
| AV.AssetValuation.selectList | AvAssetValuationHandler | IMPLEMENTS |
| AvAssetValuationHandler | AvAssetValuationFacade | USES |
| AvAssetValuationService | AvAssetValuationDao | USES |
| AvAssetValuationMapper | AV_ASSET_VALUATION | USES |
| REQ-AV-0001 | AvAssetValuationRuleTest | TESTS |

## Rates
- requirementServiceTraceRate: 100
- serviceProgramTraceRate: 100
- sqlDbObjectTraceRate: 100
- serviceOmCatalogConsistencyRate: 100 (draft present)
- openCriticalDriftCount: 0
