package com.nh.nsight.marketing.av.application.dto.assetvaluation;

/** REQ-AV-001 search criteria (branchId from auth context only). */
public class AssetValuationSearchCriteria {

    private int pageNo;
    private int pageSize;
    private int offset;
    private String branchId;
    private String baseDate;
    private String customerNo;
    private String assetTypeCode;
    private String valuationStatusCode;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(String baseDate) {
        this.baseDate = baseDate;
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

    public String getValuationStatusCode() {
        return valuationStatusCode;
    }

    public void setValuationStatusCode(String valuationStatusCode) {
        this.valuationStatusCode = valuationStatusCode;
    }
}
