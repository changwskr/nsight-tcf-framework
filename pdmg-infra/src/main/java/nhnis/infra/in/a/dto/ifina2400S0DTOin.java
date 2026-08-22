package nhnis.infra.in.a.dto;

import java.util.*;
import com.ims.superspring.dto.DataObject;
import com.ims.superspring.dto.engine.dto.record.common.FieldProperty;

public class ifina2400S0DTOin extends DataObject {
    private static final long serialVersionUID = 1L;
    private String systemId, domainCd, bizCd, detailCd, envCd, appId;
    private String appKeyword, techRoleCd, groupKeyword;
    public String getSystemId(){return systemId;} public void setSystemId(String v){systemId=v;}
    public String getDomainCd(){return domainCd;} public void setDomainCd(String v){domainCd=v;}
    public String getBizCd(){return bizCd;} public void setBizCd(String v){bizCd=v;}
    public String getDetailCd(){return detailCd;} public void setDetailCd(String v){detailCd=v;}
    public String getEnvCd(){return envCd;} public void setEnvCd(String v){envCd=v;}
    public String getAppId(){return appId;} public void setAppId(String v){appId=v;}
    public String getAppKeyword(){return appKeyword;} public void setAppKeyword(String v){appKeyword=v;}
    public String getTechRoleCd(){return techRoleCd;} public void setTechRoleCd(String v){techRoleCd=v;}
    public String getGroupKeyword(){return groupKeyword;} public void setGroupKeyword(String v){groupKeyword=v;}
    @Override public Object clone(){ ifina2400S0DTOin c=new ifina2400S0DTOin(); c.clone(this); return c; }
    public void clone(DataObject src){ if(this==src)return; ifina2400S0DTOin in=(ifina2400S0DTOin)src;
      systemId=in.systemId; domainCd=in.domainCd; bizCd=in.bizCd; detailCd=in.detailCd; envCd=in.envCd;
      appId=in.appId; appKeyword=in.appKeyword; techRoleCd=in.techRoleCd; groupKeyword=in.groupKeyword; }
    private static final Map<String, FieldProperty> fieldPropertyMap = new LinkedHashMap<>();
    static { fieldPropertyMap.put("systemId", FieldProperty.builder().setPhysicalName("systemId").setLogicalName("systemId").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(true).setIsEncrypt(false).build()); }
    public Map<String, FieldProperty> getFieldPropertyMap(){ return Collections.unmodifiableMap(fieldPropertyMap); }
}
