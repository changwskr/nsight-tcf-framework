package com.nh.nsight.marketing.av.application.dto.assetvaluation;

import java.util.Map;

public class AssetValuationSelectListRequest {

    private final Integer pageNo;
    private final Integer pageSize;
    private final String evalDate;
    private final String productCode;

    public AssetValuationSelectListRequest(
            Integer pageNo, Integer pageSize, String evalDate, String productCode) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.evalDate = evalDate;
        this.productCode = productCode;
    }

    public static AssetValuationSelectListRequest fromMap(Map<String, Object> body) {
        Map<String, Object> safeBody = body != null ? body : Map.of();
        return new AssetValuationSelectListRequest(
                toInteger(safeBody.get("pageNo")),
                toInteger(safeBody.get("pageSize")),
                trimToNull(safeBody.get("evalDate")),
                trimToNull(safeBody.get("productCode")));
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public String getEvalDate() {
        return evalDate;
    }

    public String getProductCode() {
        return productCode;
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
