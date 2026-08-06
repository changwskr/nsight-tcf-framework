# Program Design — AssetValuation selectList

## 생성 대상 (av-service)

```text
entry/handler/AvAssetValuationHandler.java
entry/facade/AvAssetValuationFacade.java
application/service/AvAssetValuationService.java
application/rule/AvAssetValuationRule.java
application/dto/assetvaluation/
  AssetValuationSelectListRequest.java
  AssetValuationSelectListResponse.java
  AssetValuationSearchCriteria.java
  AssetValuationItem.java
persistence/dao/AvAssetValuationDao.java
persistence/mapper/AvAssetValuationMapper.java
persistence/dto/assetvaluation/AssetValuationRow.java
resources/mapper/av/AvAssetValuationMapper.xml
```

## 계층 규칙
- Handler → Facade만 호출
- Service → Rule + DAO (Mapper 직접 호출 금지)
- DAO → Mapper
- Facade: `@Transactional(readOnly = true, timeout = 5)`

## Handler 스케치

```java
private static final String SELECT_LIST = "AV.AssetValuation.selectList";
// serviceIds() = List.of(SELECT_LIST)
// doHandle → facade.selectList(...)
```

## 기존 코드와의 관계
- 신규 도메인 Handler 추가 (Sample/CustomerContact와 병행)
- `settings.gradle` / 모듈 구조 변경 없음
