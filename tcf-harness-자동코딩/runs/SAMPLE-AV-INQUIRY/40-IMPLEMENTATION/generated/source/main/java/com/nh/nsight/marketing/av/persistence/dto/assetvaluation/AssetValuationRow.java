package com.nh.nsight.marketing.av.persistence.dto.assetvaluation;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class AssetValuationRow {

    private String valuationId;
    private String evalDate;
    private String productCode;
    private BigDecimal valuationAmt;
    private String currencyCode;
    private String useYn;
    private String regDtm;
    private String updDtm;

    public String getValuationId() {
        return valuationId;
    }

    public void setValuationId(String valuationId) {
        this.valuationId = valuationId;
    }

    public String getEvalDate() {
        return evalDate;
    }

    public void setEvalDate(String evalDate) {
        this.evalDate = evalDate;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public BigDecimal getValuationAmt() {
        return valuationAmt;
    }

    public void setValuationAmt(BigDecimal valuationAmt) {
        this.valuationAmt = valuationAmt;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getRegDtm() {
        return regDtm;
    }

    public void setRegDtm(String regDtm) {
        this.regDtm = regDtm;
    }

    public String getUpdDtm() {
        return updDtm;
    }

    public void setUpdDtm(String updDtm) {
        this.updDtm = updDtm;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("valuationId", valuationId);
        map.put("evalDate", evalDate);
        map.put("productCode", productCode);
        map.put("valuationAmt", valuationAmt);
        map.put("currencyCode", currencyCode);
        map.put("useYn", useYn);
        map.put("regDtm", regDtm);
        map.put("updDtm", updDtm);
        return map;
    }
}
