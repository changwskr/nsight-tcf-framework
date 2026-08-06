package com.nh.nsight.marketing.av.application.service;

import com.nh.nsight.marketing.av.application.dto.assetvaluation.AssetValuationSearchCriteria;
import com.nh.nsight.marketing.av.application.dto.assetvaluation.AssetValuationSelectListRequest;
import com.nh.nsight.marketing.av.application.dto.assetvaluation.AssetValuationSelectListResponse;
import com.nh.nsight.marketing.av.application.rule.AvAssetValuationRule;
import com.nh.nsight.marketing.av.persistence.dao.AvAssetValuationDao;
import com.nh.nsight.marketing.av.persistence.dto.assetvaluation.AssetValuationRow;
import com.nh.nsight.tcf.core.support.context.TransactionContext;
import java.util.List;
import org.springframework.stereotype.Service;

/** REQ-AV-001 AssetValuation selectList use case. */
@Service
public class AvAssetValuationService {
    private final AvAssetValuationRule rule;
    private final AvAssetValuationDao dao;

    public AvAssetValuationService(AvAssetValuationRule rule, AvAssetValuationDao dao) {
        this.rule = rule;
        this.dao = dao;
    }

    public AssetValuationSelectListResponse selectList(
            AssetValuationSelectListRequest request, TransactionContext context) {
        rule.validateSelectList(request, context);
        AssetValuationSearchCriteria criteria = rule.buildSearchCriteria(request, context);
        List<AssetValuationRow> rows = dao.searchValuations(criteria);
        int totalCount = dao.countValuations(criteria);
        return AssetValuationSelectListResponse.of(
                context, criteria, rule.toMaskedRows(rows), totalCount);
    }
}
