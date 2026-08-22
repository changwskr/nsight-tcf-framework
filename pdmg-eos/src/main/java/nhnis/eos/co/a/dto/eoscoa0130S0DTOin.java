package nhnis.eos.co.a.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ims.superspring.dto.DataObject;
import com.ims.superspring.dto.engine.dto.record.common.FieldProperty;

public class eoscoa0130S0DTOin extends DataObject {
    private static final long serialVersionUID = 1L;
    private String resourceId;
    public String getResourceId() { return resourceId; }
    public void setResourceId(String v) { resourceId = v; }
    @Override public Object clone() { eoscoa0130S0DTOin c = new eoscoa0130S0DTOin(); c.clone(this); return c; }
    public void clone(DataObject src) { if (this == src) return; resourceId = ((eoscoa0130S0DTOin) src).resourceId; }
    private static final Map<String, FieldProperty> fieldPropertyMap = new LinkedHashMap<>();
    static {
        fieldPropertyMap.put("resourceId", FieldProperty.builder().setPhysicalName("resourceId").setLogicalName("resourceId").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(false).setIsEncrypt(false).build());
    }
    public Map<String, FieldProperty> getFieldPropertyMap() { return Collections.unmodifiableMap(fieldPropertyMap); }
}
