# SOURCE_DIFF — SAMPLE-AV-INQUIRY

## Added (av-service)
- entry/handler/AvAssetValuationHandler.java
- entry/facade/AvAssetValuationFacade.java
- application/service/AvAssetValuationService.java
- application/rule/AvAssetValuationRule.java
- application/dto/assetvaluation/* (3)
- persistence/dao/AvAssetValuationDao.java
- persistence/mapper/AvAssetValuationMapper.java
- persistence/dto/assetvaluation/AssetValuationRow.java
- resources/mapper/av/AvAssetValuationMapper.xml
- test/.../AvAssetValuationRuleTest.java

## Modified
- resources/schema.sql (AV_ASSET_VALUATION + seed)

## ServiceId
`AV.AssetValuation.selectList`
