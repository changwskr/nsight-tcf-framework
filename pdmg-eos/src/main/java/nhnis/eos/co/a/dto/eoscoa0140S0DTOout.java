package nhnis.eos.co.a.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ims.superspring.dto.DataObject;
import com.ims.superspring.dto.engine.dto.record.common.FieldProperty;

public class eoscoa0140S0DTOout extends DataObject {
    private static final long serialVersionUID = 1L;
    private List<Map<String, Object>> list = new ArrayList<>();
    private int size;
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalCount;
    private String RSLT_CD;
    private String RSLT_MSG;
    public List<Map<String, Object>> getList() { return list; }
    public void setList(List<Map<String, Object>> v) { list = v != null ? v : new ArrayList<>(); }
    public int getSize() { return size; } public void setSize(int v) { size = v; }
    public Integer getPageNo() { return pageNo; } public void setPageNo(Integer v) { pageNo = v; }
    public Integer getPageSize() { return pageSize; } public void setPageSize(Integer v) { pageSize = v; }
    public Integer getTotalCount() { return totalCount; } public void setTotalCount(Integer v) { totalCount = v; }
    public String getRSLT_CD() { return RSLT_CD; } public void setRSLT_CD(String v) { RSLT_CD = v; }
    public String getRSLT_MSG() { return RSLT_MSG; } public void setRSLT_MSG(String v) { RSLT_MSG = v; }
    @Override public Object clone() { eoscoa0140S0DTOout c = new eoscoa0140S0DTOout(); c.clone(this); return c; }
    public void clone(DataObject src) {
        if (this == src) return;
        eoscoa0140S0DTOout in = (eoscoa0140S0DTOout) src;
        list = in.list == null ? new ArrayList<>() : new ArrayList<>(in.list);
        size = in.size; pageNo = in.pageNo; pageSize = in.pageSize; totalCount = in.totalCount;
        RSLT_CD = in.RSLT_CD; RSLT_MSG = in.RSLT_MSG;
    }
    private static final Map<String, FieldProperty> fieldPropertyMap = new LinkedHashMap<>();
    static {
        fieldPropertyMap.put("RSLT_CD", FieldProperty.builder().setPhysicalName("RSLT_CD").setLogicalName("RSLT_CD").setType(FieldProperty.TYPE_OBJECT_STRING).setDecimal(-1).setIsNullable(true).setIsEncrypt(false).build());
    }
    public Map<String, FieldProperty> getFieldPropertyMap() { return Collections.unmodifiableMap(fieldPropertyMap); }
}
