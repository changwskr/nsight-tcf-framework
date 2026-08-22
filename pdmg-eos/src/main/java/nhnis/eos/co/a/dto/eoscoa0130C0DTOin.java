package nhnis.eos.co.a.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ims.superspring.dto.DataObject;
import com.ims.superspring.dto.engine.dto.record.common.FieldProperty;

public class eoscoa0130C0DTOin extends DataObject {
    private static final long serialVersionUID = 1L;
    private String resourceName;
    private String versionId;
    private String envCd;
    private String centerCd;
    private String hostName;
    private String ipAddr;
    private String nsightAreaCd;
    private String orgCd;
    private String ownerUserId;
    private String remark;
    public String getResourceName() { return resourceName; } public void setResourceName(String v) { resourceName = v; }
    public String getVersionId() { return versionId; } public void setVersionId(String v) { versionId = v; }
    public String getEnvCd() { return envCd; } public void setEnvCd(String v) { envCd = v; }
    public String getCenterCd() { return centerCd; } public void setCenterCd(String v) { centerCd = v; }
    public String getHostName() { return hostName; } public void setHostName(String v) { hostName = v; }
    public String getIpAddr() { return ipAddr; } public void setIpAddr(String v) { ipAddr = v; }
    public String getNsightAreaCd() { return nsightAreaCd; } public void setNsightAreaCd(String v) { nsightAreaCd = v; }
    public String getOrgCd() { return orgCd; } public void setOrgCd(String v) { orgCd = v; }
    public String getOwnerUserId() { return ownerUserId; } public void setOwnerUserId(String v) { ownerUserId = v; }
    public String getRemark() { return remark; } public void setRemark(String v) { remark = v; }
    @Override public Object clone() { eoscoa0130C0DTOin c = new eoscoa0130C0DTOin(); c.clone(this); return c; }
    public void clone(DataObject src) {
        if (this == src) return;
        eoscoa0130C0DTOin in = (eoscoa0130C0DTOin) src;
        resourceName = in.resourceName; versionId = in.versionId; envCd = in.envCd; centerCd = in.centerCd;
        hostName = in.hostName; ipAddr = in.ipAddr; nsightAreaCd = in.nsightAreaCd; orgCd = in.orgCd;
        ownerUserId = in.ownerUserId; remark = in.remark;
    }
    private static final Map<String, FieldProperty> fieldPropertyMap = new LinkedHashMap<>();
    static {
        fieldPropertyMap.put("resourceName", FieldProperty.builder().setPhysicalName("resourceName").setLogicalName("resourceName").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(false).setIsEncrypt(false).build());
        fieldPropertyMap.put("versionId", FieldProperty.builder().setPhysicalName("versionId").setLogicalName("versionId").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(false).setIsEncrypt(false).build());
        fieldPropertyMap.put("envCd", FieldProperty.builder().setPhysicalName("envCd").setLogicalName("envCd").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(false).setIsEncrypt(false).build());
    }
    public Map<String, FieldProperty> getFieldPropertyMap() { return Collections.unmodifiableMap(fieldPropertyMap); }
}
