package com.nh.nsight.marketing.av.persistence.mapper;

import com.nh.nsight.marketing.av.application.dto.assetvaluation.AssetValuationSearchCriteria;
import com.nh.nsight.marketing.av.persistence.dto.assetvaluation.AssetValuationRow;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AvAssetValuationMapper {
    List<AssetValuationRow> searchValuations(AssetValuationSearchCriteria criteria);

    int countValuations(AssetValuationSearchCriteria criteria);
}
