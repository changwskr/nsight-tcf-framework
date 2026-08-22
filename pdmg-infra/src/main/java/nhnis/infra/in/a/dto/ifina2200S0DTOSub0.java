package nhnis.infra.in.a.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.ims.superspring.dto.DataObject;
import com.ims.superspring.dto.engine.dto.record.common.FieldProperty;

public class ifina2200S0DTOSub0 extends DataObject {
    private static final long serialVersionUID = 1L;
    private String appId, appName, systemId, detailCd, appTypeCd, langCd, statusCd, remark, regUserId, regDtm, chgUserId, chgDtm;
    private List<String> appTypeList = new ArrayList<>();

    public String getAppId(){return appId;} public void setAppId(String v){appId=v;}
    public String getAppName(){return appName;} public void setAppName(String v){appName=v;}
    public String getSystemId(){return systemId;} public void setSystemId(String v){systemId=v;}
    public String getDetailCd(){return detailCd;} public void setDetailCd(String v){detailCd=v;}
    public String getAppTypeCd(){return appTypeCd;} public void setAppTypeCd(String v){appTypeCd=v;}
    public List<String> getAppTypeList(){return appTypeList;} public void setAppTypeList(List<String> v){appTypeList=v!=null?v:new ArrayList<>();}
    public String getLangCd(){return langCd;} public void setLangCd(String v){langCd=v;}
    public String getStatusCd(){return statusCd;} public void setStatusCd(String v){statusCd=v;}
    public String getRemark(){return remark;} public void setRemark(String v){remark=v;}
    public String getRegUserId(){return regUserId;} public void setRegUserId(String v){regUserId=v;}
    public String getRegDtm(){return regDtm;} public void setRegDtm(String v){regDtm=v;}
    public String getChgUserId(){return chgUserId;} public void setChgUserId(String v){chgUserId=v;}
    public String getChgDtm(){return chgDtm;} public void setChgDtm(String v){chgDtm=v;}

    @Override public Object clone(){ ifina2200S0DTOSub0 c=new ifina2200S0DTOSub0(); c.clone(this); return c; }
    public void clone(DataObject src){ if(this==src)return; ifina2200S0DTOSub0 in=(ifina2200S0DTOSub0)src;
      appId=in.appId; appName=in.appName; systemId=in.systemId; detailCd=in.detailCd; appTypeCd=in.appTypeCd;
      appTypeList=in.appTypeList==null?new ArrayList<>():new ArrayList<>(in.appTypeList);
      langCd=in.langCd; statusCd=in.statusCd; remark=in.remark;
      regUserId=in.regUserId; regDtm=in.regDtm; chgUserId=in.chgUserId; chgDtm=in.chgDtm; }

    private static final Map<String, FieldProperty> fieldPropertyMap = new LinkedHashMap<>();
    static {
      for(String n: new String[]{"appId","appName","systemId","detailCd","appTypeCd","langCd","statusCd","remark","regUserId","regDtm","chgUserId","chgDtm"})
        fieldPropertyMap.put(n, FieldProperty.builder().setPhysicalName(n).setLogicalName(n).setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(true).setIsEncrypt(false).build());
      fieldPropertyMap.put("appTypeList", FieldProperty.builder().setPhysicalName("appTypeList").setLogicalName("appTypeList").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(true).setIsEncrypt(false).build());
    }
    public Map<String, FieldProperty> getFieldPropertyMap(){ return Collections.unmodifiableMap(fieldPropertyMap); }
}
