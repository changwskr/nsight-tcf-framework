package nhnis.eos.co.a.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ims.superspring.dto.DataObject;
import com.ims.superspring.dto.engine.dto.record.common.FieldProperty;

public class eoscoa0140S0DTOin extends DataObject {
    private static final long serialVersionUID = 1L;
    private String productId;
    private String versionId;
    private String resourceTypeCd;
    private Integer pageNo;
    private Integer pageSize;
    public String getProductId() { return productId; } public void setProductId(String v) { productId = v; }
    public String getVersionId() { return versionId; } public void setVersionId(String v) { versionId = v; }
    public String getResourceTypeCd() { return resourceTypeCd; } public void setResourceTypeCd(String v) { resourceTypeCd = v; }
    public Integer getPageNo() { return pageNo; } public void setPageNo(Integer v) { pageNo = v; }
    public Integer getPageSize() { return pageSize; } public void setPageSize(Integer v) { pageSize = v; }
    @Override public Object clone() { eoscoa0140S0DTOin c = new eoscoa0140S0DTOin(); c.clone(this); return c; }
    public void clone(DataObject src) {
        if (this == src) return;
        eoscoa0140S0DTOin in = (eoscoa0140S0DTOin) src;
        productId = in.productId; versionId = in.versionId; resourceTypeCd = in.resourceTypeCd;
        pageNo = in.pageNo; pageSize = in.pageSize;
    }
    private static final Map<String, FieldProperty> fieldPropertyMap = new LinkedHashMap<>();
    static {
        fieldPropertyMap.put("pageNo", FieldProperty.builder().setPhysicalName("pageNo").setLogicalName("pageNo").setType(FieldProperty.TYPE_OBJECT_INT).setDecimal(-1).setIsNullable(true).setIsEncrypt(false).build());
    }
    public Map<String, FieldProperty> getFieldPropertyMap() { return Collections.unmodifiableMap(fieldPropertyMap); }
}
