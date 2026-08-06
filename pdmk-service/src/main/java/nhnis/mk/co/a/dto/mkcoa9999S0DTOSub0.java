package nhnis.mk.co.a.dto;

import com.ims.superspring.dto.DataObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.Map;
import com.ims.superspring.dto.engine.dto.record.common.FieldProperty;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(
        value = "com.tmaxsoft.sts4.codegen.dto.DtoGenerator",
        date = "26. 7. 24. 오전 10:09",
        comments = "mkcoa9999S0DTOSub0"
)
public class mkcoa9999S0DTOSub0 extends DataObject
{
    private static final long serialVersionUID = 1L;

    private String trtBrc = null;

    public String getTrtBrc() {
        return trtBrc;
    }

    public void setTrtBrc(String trtBrc) {
        if(trtBrc == null) {
            this.trtBrc = null;
        } else {
            this.trtBrc = trtBrc;
        }
    }

    private String trtmnEno = null;

    public String getTrtmnEno() {
        return trtmnEno;
    }

    public void setTrtmnEno(String trtmnEno) {
        if(trtmnEno == null) {
            this.trtmnEno = null;
        } else {
            this.trtmnEno = trtmnEno;
        }
    }

    private String salzTipKdc = null;

    public String getSalzTipKdc() {
        return salzTipKdc;
    }

    public void setSalzTipKdc(String salzTipKdc) {
        if(salzTipKdc == null) {
            this.salzTipKdc = null;
        } else {
            this.salzTipKdc = salzTipKdc;
        }
    }

    private String basDt = null;

    public String getBasDt() {
        return basDt;
    }

    public void setBasDt(String basDt) {
        if(basDt == null) {
            this.basDt = null;
        } else {
            this.basDt = basDt;
        }
    }

    private int prtoCn = 0;

    public int getPrtoCn() {
        return prtoCn;
    }

    public void setPrtoCn(int prtoCn) {
        this.prtoCn = prtoCn;
    }

    public void setPrtoCn(Integer prtoCn) {
        if(prtoCn == null) {
            this.prtoCn = 0;
        } else {
            this.prtoCn = prtoCn.intValue();
        }
    }

    public void setPrtoCn(String prtoCn) {
        if (prtoCn == null || prtoCn.length() == 0) {
            this.prtoCn = 0;
        } else {
            this.prtoCn = Integer.parseInt(prtoCn);
        }
    }

    private int inqCn = 0;

    public int getInqCn() {
        return inqCn;
    }

    public void setInqCn(int inqCn) {
        this.inqCn = inqCn;
    }

    public void setInqCn(Integer inqCn) {
        if(inqCn == null) {
            this.inqCn = 0;
        } else {
            this.inqCn = inqCn.intValue();
        }
    }

    public void setInqCn(String inqCn) {
        if (inqCn == null || inqCn.length() == 0) {
            this.inqCn = 0;
        } else {
            this.inqCn = Integer.parseInt(inqCn);
        }
    }

    private int inpCn = 0;

    public int getInpCn() {
        return inpCn;
    }

    public void setInpCn(int inpCn) {
        this.inpCn = inpCn;
    }

    public void setInpCn(Integer inpCn) {
        if(inpCn == null) {
            this.inpCn = 0;
        } else {
            this.inpCn = inpCn.intValue();
        }
    }

    public void setInpCn(String inpCn) {
        if (inpCn == null || inpCn.length() == 0) {
            this.inpCn = 0;
        } else {
            this.inpCn = Integer.parseInt(inpCn);
        }
    }

    public Object clone() {
        mkcoa9999S0DTOSub0 copyObj = new mkcoa9999S0DTOSub0();
        copyObj.clone(this);
        return copyObj;
    }

    public void clone(DataObject _mkcoa9999S0DTOSub0) {
        if(this == _mkcoa9999S0DTOSub0)
            return;

        mkcoa9999S0DTOSub0 __mkcoa9999S0DTOSub0 =
                (mkcoa9999S0DTOSub0) _mkcoa9999S0DTOSub0;
        this.setTrtBrc(__mkcoa9999S0DTOSub0.getTrtBrc());
        this.setTrtmnEno(__mkcoa9999S0DTOSub0.getTrtmnEno());
        this.setSalzTipKdc(__mkcoa9999S0DTOSub0.getSalzTipKdc());
        this.setBasDt(__mkcoa9999S0DTOSub0.getBasDt());
        this.setPrtoCn(__mkcoa9999S0DTOSub0.getPrtoCn());
        this.setInqCn(__mkcoa9999S0DTOSub0.getInqCn());
        this.setInpCn(__mkcoa9999S0DTOSub0.getInpCn());
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();

        buffer.append("trtBrc : ").append(trtBrc).append("\n");
        buffer.append("trtmnEno : ").append(trtmnEno).append("\n");
        buffer.append("salzTipKdc : ").append(salzTipKdc).append("\n");
        buffer.append("basDt : ").append(basDt).append("\n");
        buffer.append("prtoCn : ").append(prtoCn).append("\n");
        buffer.append("inqCn : ").append(inqCn).append("\n");
        buffer.append("inpCn : ").append(inpCn).append("\n");
        return buffer.toString();
    }

    private static final Map<String, FieldProperty> fieldPropertyMap;

    static {
        fieldPropertyMap =
                new java.util.LinkedHashMap<String, FieldProperty>(7);
        fieldPropertyMap.put("trtBrc", FieldProperty.builder()
                .setPhysicalName("trtBrc")
                .setLogicalName("trtBrc")
                .setType(FieldProperty.TYPE_OBJECT_STRING)
                .setDecimal(-1)
                .setIsNullable(true)
                .setIsEncrypt(false)
                .build());
        fieldPropertyMap.put("trtmnEno", FieldProperty.builder()
                .setPhysicalName("trtmnEno")
                .setLogicalName("trtmnEno")
                .setType(FieldProperty.TYPE_OBJECT_STRING)
                .setDecimal(-1)
                .setIsNullable(true)
                .setIsEncrypt(false)
                .build());
        fieldPropertyMap.put("salzTipKdc", FieldProperty.builder()
                .setPhysicalName("salzTipKdc")
                .setLogicalName("salzTipKdc")
                .setType(FieldProperty.TYPE_OBJECT_STRING)
                .setDecimal(-1)
                .setIsNullable(true)
                .setIsEncrypt(false)
                .build());
        fieldPropertyMap.put("basDt", FieldProperty.builder()
                .setPhysicalName("basDt")
                .setLogicalName("basDt")
                .setType(FieldProperty.TYPE_OBJECT_STRING)
                .setDecimal(-1)
                .setIsNullable(true)
                .setIsEncrypt(false)
                .build());
        fieldPropertyMap.put("prtoCn", FieldProperty.builder()
                .setPhysicalName("prtoCn")
                .setLogicalName("prtoCn")
                .setType(FieldProperty.TYPE_PRIMITIVE_INT)
                .setDecimal(-1)
                .setIsNullable(true)
                .setIsEncrypt(false)
                .build());
        fieldPropertyMap.put("inqCn", FieldProperty.builder()
                .setPhysicalName("inqCn")
                .setLogicalName("inqCn")
                .setType(FieldProperty.TYPE_PRIMITIVE_INT)
                .setDecimal(-1)
                .setIsNullable(true)
                .setIsEncrypt(false)
                .build());
        fieldPropertyMap.put("inpCn", FieldProperty.builder()
                .setPhysicalName("inpCn")
                .setLogicalName("inpCn")
                .setType(FieldProperty.TYPE_PRIMITIVE_INT)
                .setDecimal(-1)
                .setIsNullable(true)
                .setIsEncrypt(false)
                .build());
    }

    public Map<String, FieldProperty> getFieldPropertyMap() {
        return Collections.unmodifiableMap(fieldPropertyMap);
    }
}
