package nhnis.infra.in.a.dto;



import java.util.*;

import com.ims.superspring.dto.DataObject;

import com.ims.superspring.dto.engine.dto.record.common.FieldProperty;



public class ifina2400U0DTOin extends DataObject {

    private static final long serialVersionUID = 1L;

    private String systemId, bizCd, detailCd, appId, envCd, action, chgUserId, remark;

    public String getSystemId(){return systemId;} public void setSystemId(String v){systemId=v;}

    public String getBizCd(){return bizCd;} public void setBizCd(String v){bizCd=v;}

    public String getDetailCd(){return detailCd;} public void setDetailCd(String v){detailCd=v;}

    public String getAppId(){return appId;} public void setAppId(String v){appId=v;}

    public String getEnvCd(){return envCd;} public void setEnvCd(String v){envCd=v;}

    public String getAction(){return action;} public void setAction(String v){action=v;}

    public String getChgUserId(){return chgUserId;} public void setChgUserId(String v){chgUserId=v;}

    public String getRemark(){return remark;} public void setRemark(String v){remark=v;}

    @Override public Object clone(){ ifina2400U0DTOin c=new ifina2400U0DTOin(); c.clone(this); return c; }

    public void clone(DataObject src){ if(this==src)return; ifina2400U0DTOin in=(ifina2400U0DTOin)src;

      systemId=in.systemId; bizCd=in.bizCd; detailCd=in.detailCd; appId=in.appId; envCd=in.envCd;

      action=in.action; chgUserId=in.chgUserId; remark=in.remark; }

    private static final Map<String, FieldProperty> fieldPropertyMap = new LinkedHashMap<>();

    static { fieldPropertyMap.put("action", FieldProperty.builder().setPhysicalName("action").setLogicalName("action").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(true).setIsEncrypt(false).build()); }

    public Map<String, FieldProperty> getFieldPropertyMap(){ return Collections.unmodifiableMap(fieldPropertyMap); }

}

