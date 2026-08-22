package nhnis.infra.in.a.dto;



import java.util.*;

import com.ims.superspring.dto.DataObject;

import com.ims.superspring.dto.engine.dto.record.common.FieldProperty;



public class ifina2400D0DTOin extends DataObject {

    private static final long serialVersionUID = 1L;

    private String unlinkType, systemId, bizCd, detailCd, appId, envCd;

    private List<String> mapIdList = new ArrayList<>();

    public String getUnlinkType(){return unlinkType;} public void setUnlinkType(String v){unlinkType=v;}

    public String getSystemId(){return systemId;} public void setSystemId(String v){systemId=v;}

    public String getBizCd(){return bizCd;} public void setBizCd(String v){bizCd=v;}

    public String getDetailCd(){return detailCd;} public void setDetailCd(String v){detailCd=v;}

    public String getAppId(){return appId;} public void setAppId(String v){appId=v;}

    public String getEnvCd(){return envCd;} public void setEnvCd(String v){envCd=v;}

    public List<String> getMapIdList(){return mapIdList;} public void setMapIdList(List<String> v){mapIdList=v!=null?v:new ArrayList<>();}

    @Override public Object clone(){ ifina2400D0DTOin c=new ifina2400D0DTOin(); c.clone(this); return c; }

    public void clone(DataObject src){ if(this==src)return; ifina2400D0DTOin in=(ifina2400D0DTOin)src;

      unlinkType=in.unlinkType; systemId=in.systemId; bizCd=in.bizCd; detailCd=in.detailCd; appId=in.appId; envCd=in.envCd;

      mapIdList=in.mapIdList==null?new ArrayList<>():new ArrayList<>(in.mapIdList); }

    private static final Map<String, FieldProperty> fieldPropertyMap = new LinkedHashMap<>();

    static { fieldPropertyMap.put("mapIdList", FieldProperty.builder().setPhysicalName("mapIdList").setLogicalName("mapIdList").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(true).setIsEncrypt(false).build()); }

    public Map<String, FieldProperty> getFieldPropertyMap(){ return Collections.unmodifiableMap(fieldPropertyMap); }

}

