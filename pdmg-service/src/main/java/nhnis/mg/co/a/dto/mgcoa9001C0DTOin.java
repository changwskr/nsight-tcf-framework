package nhnis.mg.co.a.dto;

/** 거래통제 등록 입력 (mgcoa9001C0). */
public class mgcoa9001C0DTOin {

    private String serviceId;
    private String allowYn;
    private String useYn;
    private String allowStartTm;
    private String allowEndTm;
    private String allowSysIds;
    private String allowBrcs;
    private String regUserId;

    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }
    public String getAllowYn() { return allowYn; }
    public void setAllowYn(String allowYn) { this.allowYn = allowYn; }
    public String getUseYn() { return useYn; }
    public void setUseYn(String useYn) { this.useYn = useYn; }
    public String getAllowStartTm() { return allowStartTm; }
    public void setAllowStartTm(String allowStartTm) { this.allowStartTm = allowStartTm; }
    public String getAllowEndTm() { return allowEndTm; }
    public void setAllowEndTm(String allowEndTm) { this.allowEndTm = allowEndTm; }
    public String getAllowSysIds() { return allowSysIds; }
    public void setAllowSysIds(String allowSysIds) { this.allowSysIds = allowSysIds; }
    public String getAllowBrcs() { return allowBrcs; }
    public void setAllowBrcs(String allowBrcs) { this.allowBrcs = allowBrcs; }
    public String getRegUserId() { return regUserId; }
    public void setRegUserId(String regUserId) { this.regUserId = regUserId; }
}
