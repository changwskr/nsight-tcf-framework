package nhnis.infra.in.a.dto;

import java.util.*;
import com.ims.superspring.dto.DataObject;
import com.ims.superspring.dto.engine.dto.record.common.FieldProperty;

public class ifina2400C0DTOin extends DataObject {
    private static final long serialVersionUID = 1L;
    private String linkType, systemId, domainCd, bizCd, detailCd, envCd, appId;
    private String mapTypeCd, refId, roleCd, mapRoleCd, primaryYn, remark, regUserId;
    private List<String> refIdList = new ArrayList<>();
    public String getLinkType(){return linkType;} public void setLinkType(String v){linkType=v;}
    public String getSystemId(){return systemId;} public void setSystemId(String v){systemId=v;}
    public String getDomainCd(){return domainCd;} public void setDomainCd(String v){domainCd=v;}
    public String getBizCd(){return bizCd;} public void setBizCd(String v){bizCd=v;}
    public String getDetailCd(){return detailCd;} public void setDetailCd(String v){detailCd=v;}
    public String getEnvCd(){return envCd;} public void setEnvCd(String v){envCd=v;}
    public String getAppId(){return appId;} public void setAppId(String v){appId=v;}
    public String getMapTypeCd(){return mapTypeCd;} public void setMapTypeCd(String v){mapTypeCd=v;}
    public String getRefId(){return refId;} public void setRefId(String v){refId=v;}
    public String getRoleCd(){return roleCd;} public void setRoleCd(String v){roleCd=v;}
    public String getMapRoleCd(){return mapRoleCd;} public void setMapRoleCd(String v){mapRoleCd=v;}
    public String getPrimaryYn(){return primaryYn;} public void setPrimaryYn(String v){primaryYn=v;}
    public String getRemark(){return remark;} public void setRemark(String v){remark=v;}
    public String getRegUserId(){return regUserId;} public void setRegUserId(String v){regUserId=v;}
    public List<String> getRefIdList(){return refIdList;} public void setRefIdList(List<String> v){refIdList=v!=null?v:new ArrayList<>();}
    @Override public Object clone(){ ifina2400C0DTOin c=new ifina2400C0DTOin(); c.clone(this); return c; }
    public void clone(DataObject src){ if(this==src)return; ifina2400C0DTOin in=(ifina2400C0DTOin)src;
      linkType=in.linkType; systemId=in.systemId; domainCd=in.domainCd; bizCd=in.bizCd; detailCd=in.detailCd; envCd=in.envCd;
      appId=in.appId; mapTypeCd=in.mapTypeCd; refId=in.refId; roleCd=in.roleCd; mapRoleCd=in.mapRoleCd;
      primaryYn=in.primaryYn; remark=in.remark; regUserId=in.regUserId;
      refIdList=in.refIdList==null?new ArrayList<>():new ArrayList<>(in.refIdList); }
    private static final Map<String, FieldProperty> fieldPropertyMap = new LinkedHashMap<>();
    static { fieldPropertyMap.put("appId", FieldProperty.builder().setPhysicalName("appId").setLogicalName("appId").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(true).setIsEncrypt(false).build()); }
    public Map<String, FieldProperty> getFieldPropertyMap(){ return Collections.unmodifiableMap(fieldPropertyMap); }
}
