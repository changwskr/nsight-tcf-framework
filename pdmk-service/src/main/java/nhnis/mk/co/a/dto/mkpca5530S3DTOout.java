package nhnis.mk.co.a.dto;

/**
 * 마케팅희망고객 삭제 결과 (mkpca5530S3).
 */
public class mkpca5530S3DTOout {

    private Integer PROC_CNT;
    private String RSLT_CD;
    private String RSLT_MSG;

    public Integer getPROC_CNT() {
        return PROC_CNT;
    }

    public void setPROC_CNT(Integer PROC_CNT) {
        this.PROC_CNT = PROC_CNT;
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
        return "PROC_CNT : " + PROC_CNT + " RSLT_CD : " + RSLT_CD + " RSLT_MSG : " + RSLT_MSG;
    }
}
