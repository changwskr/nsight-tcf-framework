package nhnis.mk.co.a.dto;

/**
 * 마케팅희망고객 등록 입력 (mkpca5530S1).
 */
public class mkpca5530S1DTOin {

    private String BSNNM;
    private String PMCUS_RG_HDNG_CNM;
    private Integer OBJ_CUSCN;
    private String PMCUS_SCF_EXPL;
    private String REL_FLNM;
    private String RG_BRC;
    private String RG_EMP_ENO;
    private String RG_DT;
    private String ETC1_RMK_CNTN;
    private String ETC2_RMK_CNTN;
    private String ETC3_RMK_CNTN;

    public String getBSNNM() {
        return BSNNM;
    }

    public void setBSNNM(String BSNNM) {
        this.BSNNM = BSNNM;
    }

    public String getPMCUS_RG_HDNG_CNM() {
        return PMCUS_RG_HDNG_CNM;
    }

    public void setPMCUS_RG_HDNG_CNM(String PMCUS_RG_HDNG_CNM) {
        this.PMCUS_RG_HDNG_CNM = PMCUS_RG_HDNG_CNM;
    }

    public Integer getOBJ_CUSCN() {
        return OBJ_CUSCN;
    }

    public void setOBJ_CUSCN(Integer OBJ_CUSCN) {
        this.OBJ_CUSCN = OBJ_CUSCN;
    }

    public String getPMCUS_SCF_EXPL() {
        return PMCUS_SCF_EXPL;
    }

    public void setPMCUS_SCF_EXPL(String PMCUS_SCF_EXPL) {
        this.PMCUS_SCF_EXPL = PMCUS_SCF_EXPL;
    }

    public String getREL_FLNM() {
        return REL_FLNM;
    }

    public void setREL_FLNM(String REL_FLNM) {
        this.REL_FLNM = REL_FLNM;
    }

    public String getRG_BRC() {
        return RG_BRC;
    }

    public void setRG_BRC(String RG_BRC) {
        this.RG_BRC = RG_BRC;
    }

    public String getRG_EMP_ENO() {
        return RG_EMP_ENO;
    }

    public void setRG_EMP_ENO(String RG_EMP_ENO) {
        this.RG_EMP_ENO = RG_EMP_ENO;
    }

    public String getRG_DT() {
        return RG_DT;
    }

    public void setRG_DT(String RG_DT) {
        this.RG_DT = RG_DT;
    }

    public String getETC1_RMK_CNTN() {
        return ETC1_RMK_CNTN;
    }

    public void setETC1_RMK_CNTN(String ETC1_RMK_CNTN) {
        this.ETC1_RMK_CNTN = ETC1_RMK_CNTN;
    }

    public String getETC2_RMK_CNTN() {
        return ETC2_RMK_CNTN;
    }

    public void setETC2_RMK_CNTN(String ETC2_RMK_CNTN) {
        this.ETC2_RMK_CNTN = ETC2_RMK_CNTN;
    }

    public String getETC3_RMK_CNTN() {
        return ETC3_RMK_CNTN;
    }

    public void setETC3_RMK_CNTN(String ETC3_RMK_CNTN) {
        this.ETC3_RMK_CNTN = ETC3_RMK_CNTN;
    }

    @Override
    public String toString() {
        return "BSNNM : " + BSNNM + "\n"
                + "RG_BRC : " + RG_BRC + "\n"
                + "RG_EMP_ENO : " + RG_EMP_ENO + "\n"
                + "RG_DT : " + RG_DT + "\n";
    }
}
