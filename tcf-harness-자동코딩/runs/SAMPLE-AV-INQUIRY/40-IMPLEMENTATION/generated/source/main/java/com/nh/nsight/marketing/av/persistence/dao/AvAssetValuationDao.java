package com.nh.nsight.marketing.av.persistence.dao;

import com.nh.nsight.marketing.av.application.dto.assetvaluation.AssetValuationSearchCriteria;
import com.nh.nsight.marketing.av.persistence.dto.assetvaluation.AssetValuationRow;
import com.nh.nsight.marketing.av.persistence.mapper.AvAssetValuationMapper;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class AvAssetValuationDao {
    private final AvAssetValuationMapper mapper;

    public AvAssetValuationDao(AvAssetValuationMapper mapper) {
        this.mapper = mapper;
    }

    public List<AssetValuationRow> searchValuations(AssetValuationSearchCriteria criteria) {
        return mapper.searchValuations(criteria);
    }

    public int countValuations(AssetValuationSearchCriteria criteria) {
        return mapper.countValuations(criteria);
    }
}
