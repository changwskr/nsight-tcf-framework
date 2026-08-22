package nhnis.eos.co.a.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ims.superspring.dto.DataObject;
import com.ims.superspring.dto.engine.dto.record.common.FieldProperty;

public class eoscoa0130D0DTOin extends DataObject {
    private static final long serialVersionUID = 1L;
    private String resourceId;
    private String disposeReason;
    public String getResourceId() { return resourceId; } public void setResourceId(String v) { resourceId = v; }
    public String getDisposeReason() { return disposeReason; } public void setDisposeReason(String v) { disposeReason = v; }
    @Override public Object clone() { eoscoa0130D0DTOin c = new eoscoa0130D0DTOin(); c.clone(this); return c; }
    public void clone(DataObject src) {
        if (this == src) return;
        eoscoa0130D0DTOin in = (eoscoa0130D0DTOin) src;
        resourceId = in.resourceId; disposeReason = in.disposeReason;
    }
    private static final Map<String, FieldProperty> fieldPropertyMap = new LinkedHashMap<>();
    static {
        fieldPropertyMap.put("resourceId", FieldProperty.builder().setPhysicalName("resourceId").setLogicalName("resourceId").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(false).setIsEncrypt(false).build());
        fieldPropertyMap.put("disposeReason", FieldProperty.builder().setPhysicalName("disposeReason").setLogicalName("disposeReason").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(false).setIsEncrypt(false).build());
    }
    public Map<String, FieldProperty> getFieldPropertyMap() { return Collections.unmodifiableMap(fieldPropertyMap); }
}
