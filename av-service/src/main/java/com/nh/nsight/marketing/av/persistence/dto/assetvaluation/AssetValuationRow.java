package com.nh.nsight.marketing.av.persistence.dto.assetvaluation;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/** REQ-AV-001 row from AV_ASSET_VALUATION (+ code names). */
public class AssetValuationRow {

    private String assetId;
    private String customerNo;
    private String assetTypeCode;
    private String assetTypeName;
    private BigDecimal valuationAmount;
    private String valuationDate;
    private String valuationStatusCode;
    private String valuationStatusName;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getAssetTypeCode() {
        return assetTypeCode;
    }

    public void setAssetTypeCode(String assetTypeCode) {
        this.assetTypeCode = assetTypeCode;
    }

    public String getAssetTypeName() {
        return assetTypeName;
    }

    public void setAssetTypeName(String assetTypeName) {
        this.assetTypeName = assetTypeName;
    }

    public BigDecimal getValuationAmount() {
        return valuationAmount;
    }

    public void setValuationAmount(BigDecimal valuationAmount) {
        this.valuationAmount = valuationAmount;
    }

    public String getValuationDate() {
        return valuationDate;
    }

    public void setValuationDate(String valuationDate) {
        this.valuationDate = valuationDate;
    }

    public String getValuationStatusCode() {
        return valuationStatusCode;
    }

    public void setValuationStatusCode(String valuationStatusCode) {
        this.valuationStatusCode = valuationStatusCode;
    }

    public String getValuationStatusName() {
        return valuationStatusName;
    }

    public void setValuationStatusName(String valuationStatusName) {
        this.valuationStatusName = valuationStatusName;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("assetId", assetId);
        map.put("customerNo", customerNo);
        map.put("assetTypeCode", assetTypeCode);
        map.put("assetTypeName", assetTypeName);
        map.put("valuationAmount", valuationAmount);
        map.put("valuationDate", valuationDate);
        map.put("valuationStatusCode", valuationStatusCode);
        map.put("valuationStatusName", valuationStatusName);
        return map;
    }
}
