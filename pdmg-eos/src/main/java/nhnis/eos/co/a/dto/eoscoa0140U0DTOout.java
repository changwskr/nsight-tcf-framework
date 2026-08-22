package nhnis.eos.co.a.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ims.superspring.dto.DataObject;
import com.ims.superspring.dto.engine.dto.record.common.FieldProperty;

public class eoscoa0140U0DTOout extends DataObject {
    private static final long serialVersionUID = 1L;
    private String lfcId;
    private Integer affectedResourceCnt;
    private Integer statusChangedCnt;
    private String RSLT_CD;
    private String RSLT_MSG;
    public String getLfcId() { return lfcId; } public void setLfcId(String v) { lfcId = v; }
    public Integer getAffectedResourceCnt() { return affectedResourceCnt; } public void setAffectedResourceCnt(Integer v) { affectedResourceCnt = v; }
    public Integer getStatusChangedCnt() { return statusChangedCnt; } public void setStatusChangedCnt(Integer v) { statusChangedCnt = v; }
    public String getRSLT_CD() { return RSLT_CD; } public void setRSLT_CD(String v) { RSLT_CD = v; }
    public String getRSLT_MSG() { return RSLT_MSG; } public void setRSLT_MSG(String v) { RSLT_MSG = v; }
    @Override public Object clone() { eoscoa0140U0DTOout c = new eoscoa0140U0DTOout(); c.clone(this); return c; }
    public void clone(DataObject src) {
        if (this == src) return;
        eoscoa0140U0DTOout in = (eoscoa0140U0DTOout) src;
        lfcId = in.lfcId; affectedResourceCnt = in.affectedResourceCnt; statusChangedCnt = in.statusChangedCnt;
        RSLT_CD = in.RSLT_CD; RSLT_MSG = in.RSLT_MSG;
    }
    private static final Map<String, FieldProperty> fieldPropertyMap = new LinkedHashMap<>();
    static {
        fieldPropertyMap.put("RSLT_CD", FieldProperty.builder().setPhysicalName("RSLT_CD").setLogicalName("RSLT_CD").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(true).setIsEncrypt(false).build());
    }
    public Map<String, FieldProperty> getFieldPropertyMap() { return Collections.unmodifiableMap(fieldPropertyMap); }
}
