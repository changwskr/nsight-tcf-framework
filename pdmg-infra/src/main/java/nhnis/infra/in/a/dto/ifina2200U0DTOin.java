package nhnis.infra.in.a.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.ims.superspring.dto.DataObject;
import com.ims.superspring.dto.engine.dto.record.common.FieldProperty;

public class ifina2200U0DTOin extends DataObject {
    private static final long serialVersionUID = 1L;
    private String orgAppId, appId, appName, systemId, appTypeCd, langCd, statusCd, remark, chgUserId;
    private List<String> appTypeList = new ArrayList<>();

    public String getOrgAppId() { return orgAppId; }
    public void setOrgAppId(String v) { orgAppId = v; }
    public String getAppId() { return appId; }
    public void setAppId(String v) { appId = v; }
    public String getAppName() { return appName; }
    public void setAppName(String v) { appName = v; }
    public String getSystemId() { return systemId; }
    public void setSystemId(String v) { systemId = v; }
    public String getAppTypeCd() { return appTypeCd; }
    public void setAppTypeCd(String v) { appTypeCd = v; }
    public List<String> getAppTypeList() { return appTypeList; }
    public void setAppTypeList(List<String> v) { appTypeList = v != null ? v : new ArrayList<>(); }
    public String getLangCd() { return langCd; }
    public void setLangCd(String v) { langCd = v; }
    public String getStatusCd() { return statusCd; }
    public void setStatusCd(String v) { statusCd = v; }
    public String getRemark() { return remark; }
    public void setRemark(String v) { remark = v; }
    public String getChgUserId() { return chgUserId; }
    public void setChgUserId(String v) { chgUserId = v; }

    @Override
    public Object clone() {
        ifina2200U0DTOin c = new ifina2200U0DTOin();
        c.clone(this);
        return c;
    }

    public void clone(DataObject src) {
        if (this == src) return;
        ifina2200U0DTOin in = (ifina2200U0DTOin) src;
        orgAppId = in.orgAppId;
        appId = in.appId;
        appName = in.appName;
        systemId = in.systemId;
        appTypeCd = in.appTypeCd;
        appTypeList = in.appTypeList == null ? new ArrayList<>() : new ArrayList<>(in.appTypeList);
        langCd = in.langCd;
        statusCd = in.statusCd;
        remark = in.remark;
        chgUserId = in.chgUserId;
    }

    private static final Map<String, FieldProperty> fieldPropertyMap = new LinkedHashMap<>();
    static {
        for (String n : new String[] {"orgAppId", "appId", "appName", "systemId", "appTypeCd", "langCd", "statusCd", "remark", "chgUserId"}) {
            fieldPropertyMap.put(n, FieldProperty.builder().setPhysicalName(n).setLogicalName(n)
                    .setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(true).setIsEncrypt(false).build());
        }
        fieldPropertyMap.put("appTypeList", FieldProperty.builder().setPhysicalName("appTypeList").setLogicalName("appTypeList")
                .setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(true).setIsEncrypt(false).build());
    }

    public Map<String, FieldProperty> getFieldPropertyMap() {
        return Collections.unmodifiableMap(fieldPropertyMap);
    }
}
