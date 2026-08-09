package nhnis.mg.co.a.dto;

import java.util.ArrayList;
import java.util.List;

/** 거래통제 목록 조회 입력 (mgcoa9001S0). */
public class mgcoa9001S0DTOin {

    private String keyword;
    private String serviceId;
    private String useYn;
    private String allowYn;
    private Integer pageNo;
    private Integer pageSize;

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }
    public String getUseYn() { return useYn; }
    public void setUseYn(String useYn) { this.useYn = useYn; }
    public String getAllowYn() { return allowYn; }
    public void setAllowYn(String allowYn) { this.allowYn = allowYn; }
    public Integer getPageNo() { return pageNo; }
    public void setPageNo(Integer pageNo) { this.pageNo = pageNo; }
    public Integer getPageSize() { return pageSize; }
    public void setPageSize(Integer pageSize) { this.pageSize = pageSize; }
}
