package nhnis.eos.co.a.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ims.superspring.dto.DataObject;
import com.ims.superspring.dto.engine.dto.record.common.FieldProperty;

public class eoscoa0140U0DTOin extends DataObject {
    private static final long serialVersionUID = 1L;
    private String versionId;
    private String gaYmd;
    private String eosYmd;
    private String eolYmd;
    private String evidenceId;
    private String sourceDesc;
    private String changeReason;
    private String recalcInstanceYn;
    public String getVersionId() { return versionId; } public void setVersionId(String v) { versionId = v; }
    public String getGaYmd() { return gaYmd; } public void setGaYmd(String v) { gaYmd = v; }
    public String getEosYmd() { return eosYmd; } public void setEosYmd(String v) { eosYmd = v; }
    public String getEolYmd() { return eolYmd; } public void setEolYmd(String v) { eolYmd = v; }
    public String getEvidenceId() { return evidenceId; } public void setEvidenceId(String v) { evidenceId = v; }
    public String getSourceDesc() { return sourceDesc; } public void setSourceDesc(String v) { sourceDesc = v; }
    public String getChangeReason() { return changeReason; } public void setChangeReason(String v) { changeReason = v; }
    public String getRecalcInstanceYn() { return recalcInstanceYn; } public void setRecalcInstanceYn(String v) { recalcInstanceYn = v; }
    @Override public Object clone() { eoscoa0140U0DTOin c = new eoscoa0140U0DTOin(); c.clone(this); return c; }
    public void clone(DataObject src) {
        if (this == src) return;
        eoscoa0140U0DTOin in = (eoscoa0140U0DTOin) src;
        versionId = in.versionId; gaYmd = in.gaYmd; eosYmd = in.eosYmd; eolYmd = in.eolYmd;
        evidenceId = in.evidenceId; sourceDesc = in.sourceDesc; changeReason = in.changeReason;
        recalcInstanceYn = in.recalcInstanceYn;
    }
    private static final Map<String, FieldProperty> fieldPropertyMap = new LinkedHashMap<>();
    static {
        fieldPropertyMap.put("versionId", FieldProperty.builder().setPhysicalName("versionId").setLogicalName("versionId").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(false).setIsEncrypt(false).build());
        fieldPropertyMap.put("changeReason", FieldProperty.builder().setPhysicalName("changeReason").setLogicalName("changeReason").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(false).setIsEncrypt(false).build());
    }
    public Map<String, FieldProperty> getFieldPropertyMap() { return Collections.unmodifiableMap(fieldPropertyMap); }
}
