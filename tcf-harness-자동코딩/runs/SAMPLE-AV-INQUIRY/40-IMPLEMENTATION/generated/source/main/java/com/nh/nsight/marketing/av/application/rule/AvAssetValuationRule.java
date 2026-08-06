package com.nh.nsight.marketing.av.application.rule;

import com.nh.nsight.marketing.av.application.dto.assetvaluation.AssetValuationSearchCriteria;
import com.nh.nsight.marketing.av.application.dto.assetvaluation.AssetValuationSelectListRequest;
import com.nh.nsight.marketing.av.persistence.dto.assetvaluation.AssetValuationRow;
import com.nh.nsight.tcf.core.support.error.BusinessException;
import com.nh.nsight.tcf.core.support.error.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AvAssetValuationRule {
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int MAX_PAGE_SIZE = 100;

    public void validateSelectList(AssetValuationSelectListRequest request) {
        AssetValuationSelectListRequest safe =
                request != null ? request : AssetValuationSelectListRequest.fromMap(Map.of());
        if (safe.getEvalDate() == null) {
            throw new BusinessException(
                    ErrorCode.BUSINESS_ERROR, "AV-AVL-V001: evalDate는 필수입니다.");
        }
        if (!safe.getEvalDate().matches("\\d{8}")) {
            throw new BusinessException(
                    ErrorCode.BUSINESS_ERROR, "AV-AVL-V002: evalDate는 yyyyMMdd 형식이어야 합니다.");
        }
        int pageSize = safe.getPageSize() != null ? safe.getPageSize() : DEFAULT_PAGE_SIZE;
        if (pageSize > MAX_PAGE_SIZE) {
            throw new BusinessException(
                    ErrorCode.BUSINESS_ERROR, "pageSize는 최대 " + MAX_PAGE_SIZE + " 입니다.");
        }
        int pageNo = safe.getPageNo() != null ? safe.getPageNo() : 1;
        if (pageNo < 1) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "pageNo는 1 이상이어야 합니다.");
        }
    }

    public AssetValuationSearchCriteria buildSearchCriteria(AssetValuationSelectListRequest request) {
        AssetValuationSelectListRequest safe =
                request != null ? request : AssetValuationSelectListRequest.fromMap(Map.of());
        int pageNo = safe.getPageNo() != null ? safe.getPageNo() : 1;
        int pageSize = safe.getPageSize() != null ? safe.getPageSize() : DEFAULT_PAGE_SIZE;
        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        AssetValuationSearchCriteria criteria = new AssetValuationSearchCriteria();
        criteria.setPageNo(pageNo);
        criteria.setPageSize(pageSize);
        criteria.setOffset((pageNo - 1) * pageSize);
        criteria.setEvalDate(safe.getEvalDate());
        criteria.setProductCode(safe.getProductCode());
        return criteria;
    }

    public List<Map<String, Object>> toRows(List<AssetValuationRow> rows) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (rows == null) {
            return result;
        }
        for (AssetValuationRow row : rows) {
            result.add(row.toMap());
        }
        return result;
    }
}
