package nhnis.eos.co.a.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ims.superspring.dto.DataObject;
import com.ims.superspring.dto.engine.dto.record.common.FieldProperty;

public class eoscoa0120S0DTOin extends DataObject {
    private static final long serialVersionUID = 1L;
    private String resourceName;
    private String resourceTypeCd;
    private String envCd;
    private String orgCd;
    private String eosStatusCd;
    private String riskGradeCd;
    private String exceptionActiveYn;
    private String productId;
    private String versionId;
    private Integer pageNo;
    private Integer pageSize;

    public String getResourceName() { return resourceName; }
    public void setResourceName(String v) { resourceName = v; }
    public String getResourceTypeCd() { return resourceTypeCd; }
    public void setResourceTypeCd(String v) { resourceTypeCd = v; }
    public String getEnvCd() { return envCd; }
    public void setEnvCd(String v) { envCd = v; }
    public String getOrgCd() { return orgCd; }
    public void setOrgCd(String v) { orgCd = v; }
    public String getEosStatusCd() { return eosStatusCd; }
    public void setEosStatusCd(String v) { eosStatusCd = v; }
    public String getRiskGradeCd() { return riskGradeCd; }
    public void setRiskGradeCd(String v) { riskGradeCd = v; }
    public String getExceptionActiveYn() { return exceptionActiveYn; }
    public void setExceptionActiveYn(String v) { exceptionActiveYn = v; }
    public String getProductId() { return productId; }
    public void setProductId(String v) { productId = v; }
    public String getVersionId() { return versionId; }
    public void setVersionId(String v) { versionId = v; }
    public Integer getPageNo() { return pageNo; }
    public void setPageNo(Integer v) { pageNo = v; }
    public Integer getPageSize() { return pageSize; }
    public void setPageSize(Integer v) { pageSize = v; }

    @Override
    public Object clone() {
        eoscoa0120S0DTOin c = new eoscoa0120S0DTOin();
        c.clone(this);
        return c;
    }

    public void clone(DataObject src) {
        if (this == src) return;
        eoscoa0120S0DTOin in = (eoscoa0120S0DTOin) src;
        resourceName = in.resourceName;
        resourceTypeCd = in.resourceTypeCd;
        envCd = in.envCd;
        orgCd = in.orgCd;
        eosStatusCd = in.eosStatusCd;
        riskGradeCd = in.riskGradeCd;
        exceptionActiveYn = in.exceptionActiveYn;
        productId = in.productId;
        versionId = in.versionId;
        pageNo = in.pageNo;
        pageSize = in.pageSize;
    }

    private static final Map<String, FieldProperty> fieldPropertyMap = new LinkedHashMap<>();
    static {
        fieldPropertyMap.put("resourceName", FieldProperty.builder().setPhysicalName("resourceName").setLogicalName("resourceName").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(true).setIsEncrypt(false).build());
        fieldPropertyMap.put("pageNo", FieldProperty.builder().setPhysicalName("pageNo").setLogicalName("pageNo").setType(FieldProperty.TYPE_OBJECT_INT).setDecimal(-1).setIsNullable(true).setIsEncrypt(false).build());
        fieldPropertyMap.put("pageSize", FieldProperty.builder().setPhysicalName("pageSize").setLogicalName("pageSize").setType(FieldProperty.TYPE_OBJECT_INT).setDecimal(-1).setIsNullable(true).setIsEncrypt(false).build());
    }

    public Map<String, FieldProperty> getFieldPropertyMap() {
        return Collections.unmodifiableMap(fieldPropertyMap);
    }
}
