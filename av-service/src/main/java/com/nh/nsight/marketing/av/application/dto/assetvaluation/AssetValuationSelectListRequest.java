package com.nh.nsight.marketing.av.application.dto.assetvaluation;

import java.util.Map;

/** REQ-AV-001 selectList request body. */
public class AssetValuationSelectListRequest {

    private final Integer pageNo;
    private final Integer pageSize;
    private final String baseDate;
    private final String customerNo;
    private final String assetTypeCode;
    private final String valuationStatusCode;

    public AssetValuationSelectListRequest(
            Integer pageNo,
            Integer pageSize,
            String baseDate,
            String customerNo,
            String assetTypeCode,
            String valuationStatusCode) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.baseDate = baseDate;
        this.customerNo = customerNo;
        this.assetTypeCode = assetTypeCode;
        this.valuationStatusCode = valuationStatusCode;
    }

    public static AssetValuationSelectListRequest fromMap(Map<String, Object> body) {
        Map<String, Object> safeBody = body != null ? body : Map.of();
        return new AssetValuationSelectListRequest(
                toInteger(safeBody.get("pageNo")),
                toInteger(safeBody.get("pageSize")),
                trimToNull(safeBody.get("baseDate")),
                trimToNull(safeBody.get("customerNo")),
                trimToNull(safeBody.get("assetTypeCode")),
                trimToNull(safeBody.get("valuationStatusCode")));
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public String getBaseDate() {
        return baseDate;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public String getAssetTypeCode() {
        return assetTypeCode;
    }

    public String getValuationStatusCode() {
        return valuationStatusCode;
    }

    private static Integer toInteger(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number number) {
            return number.intValue();
        }
        String text = String.valueOf(value).trim();
        if (text.isEmpty()) {
            return null;
        }
        return Integer.valueOf(text);
    }

    private static String trimToNull(Object value) {
        if (value == null) {
            return null;
        }
        String text = String.valueOf(value).trim();
        return text.isEmpty() ? null : text;
    }
}
