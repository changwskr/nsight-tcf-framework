package nhnis.eos.co.a.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ims.superspring.dto.DataObject;
import com.ims.superspring.dto.engine.dto.record.common.FieldProperty;

public class eoscoa0130D0DTOout extends DataObject {
    private static final long serialVersionUID = 1L;
    private String resourceId;
    private String RSLT_CD;
    private String RSLT_MSG;
    public String getResourceId() { return resourceId; } public void setResourceId(String v) { resourceId = v; }
    public String getRSLT_CD() { return RSLT_CD; } public void setRSLT_CD(String v) { RSLT_CD = v; }
    public String getRSLT_MSG() { return RSLT_MSG; } public void setRSLT_MSG(String v) { RSLT_MSG = v; }
    @Override public Object clone() { eoscoa0130D0DTOout c = new eoscoa0130D0DTOout(); c.clone(this); return c; }
    public void clone(DataObject src) {
        if (this == src) return;
        eoscoa0130D0DTOout in = (eoscoa0130D0DTOout) src;
        resourceId = in.resourceId; RSLT_CD = in.RSLT_CD; RSLT_MSG = in.RSLT_MSG;
    }
    private static final Map<String, FieldProperty> fieldPropertyMap = new LinkedHashMap<>();
    static {
        fieldPropertyMap.put("RSLT_CD", FieldProperty.builder().setPhysicalName("RSLT_CD").setLogicalName("RSLT_CD").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(true).setIsEncrypt(false).build());
    }
    public Map<String, FieldProperty> getFieldPropertyMap() { return Collections.unmodifiableMap(fieldPropertyMap); }
}
