package nhnis.eos.co.a.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ims.superspring.dto.DataObject;
import com.ims.superspring.dto.engine.dto.record.common.FieldProperty;

public class eoscoa0100S0DTOin extends DataObject {
    private static final long serialVersionUID = 1L;
    private String riskCd;
    private String statusCd;
    public String getRiskCd(){return riskCd;} public void setRiskCd(String v){riskCd=v;}
    public String getStatusCd(){return statusCd;} public void setStatusCd(String v){statusCd=v;}
    @Override public Object clone(){ eoscoa0100S0DTOin c=new eoscoa0100S0DTOin(); c.clone(this); return c; }
    public void clone(DataObject src){ if(this==src)return; eoscoa0100S0DTOin in=(eoscoa0100S0DTOin)src; riskCd=in.riskCd; statusCd=in.statusCd; }
    private static final Map<String, FieldProperty> fieldPropertyMap = new LinkedHashMap<>();
    static {
        fieldPropertyMap.put("riskCd", FieldProperty.builder().setPhysicalName("riskCd").setLogicalName("riskCd").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(true).setIsEncrypt(false).build());
        fieldPropertyMap.put("statusCd", FieldProperty.builder().setPhysicalName("statusCd").setLogicalName("statusCd").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(true).setIsEncrypt(false).build());
    }
    public Map<String, FieldProperty> getFieldPropertyMap(){ return Collections.unmodifiableMap(fieldPropertyMap); }
}
