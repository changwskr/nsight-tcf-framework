package nhnis.mk.co.a.dto;

/**
 * 마케팅희망고객 등록/수정 결과 (mkpca5530S1 / S2).
 */
public class mkpca5530S1DTOout {

    private Integer SQNO;
    private String RSLT_CD;
    private String RSLT_MSG;

    public Integer getSQNO() {
        return SQNO;
    }

    public void setSQNO(Integer SQNO) {
        this.SQNO = SQNO;
    }

    public String getRSLT_CD() {
        return RSLT_CD;
    }

    public void setRSLT_CD(String RSLT_CD) {
        this.RSLT_CD = RSLT_CD;
    }

    public String getRSLT_MSG() {
        return RSLT_MSG;
    }

    public void setRSLT_MSG(String RSLT_MSG) {
        this.RSLT_MSG = RSLT_MSG;
    }

    @Override
    public String toString() {
        return "SQNO : " + SQNO + " RSLT_CD : " + RSLT_CD + " RSLT_MSG : " + RSLT_MSG;
    }
}
