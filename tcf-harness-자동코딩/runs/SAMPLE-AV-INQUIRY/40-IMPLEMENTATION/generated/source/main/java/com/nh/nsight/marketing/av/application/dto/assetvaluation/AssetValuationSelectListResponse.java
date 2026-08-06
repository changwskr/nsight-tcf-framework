package com.nh.nsight.marketing.av.application.dto.assetvaluation;

import com.nh.nsight.tcf.core.support.context.TransactionContext;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AssetValuationSelectListResponse {

    private final String businessCode;
    private final String serviceId;
    private final String guid;
    private final List<Map<String, Object>> items;
    private final int totalCount;
    private final int pageNo;
    private final int pageSize;

    public AssetValuationSelectListResponse(
            String businessCode,
            String serviceId,
            String guid,
            List<Map<String, Object>> items,
            int totalCount,
            int pageNo,
            int pageSize) {
        this.businessCode = businessCode;
        this.serviceId = serviceId;
        this.guid = guid;
        this.items = items;
        this.totalCount = totalCount;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public static AssetValuationSelectListResponse of(
            TransactionContext context,
            AssetValuationSearchCriteria criteria,
            List<Map<String, Object>> items,
            int totalCount) {
        return new AssetValuationSelectListResponse(
                "AV",
                context.getHeader().getServiceId(),
                context.getHeader().getGuid(),
                items,
                totalCount,
                criteria.getPageNo(),
                criteria.getPageSize());
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("businessCode", businessCode);
        result.put("serviceId", serviceId);
        result.put("guid", guid);
        result.put("items", items);
        result.put("totalCount", totalCount);
        result.put("pageNo", pageNo);
        result.put("pageSize", pageSize);
        return result;
    }
}
