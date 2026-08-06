package nhnis.mk.co.a.dto;

/**
 * 마케팅희망고객 조회 입력 (mkpca5530S0).
 */
public class mkpca5530S0DTOin {

    /** 취급점 코드 */
    private String BRC;

    public String getBRC() {
        return BRC;
    }

    public void setBRC(String BRC) {
        this.BRC = BRC;
    }

    @Override
    public String toString() {
        return "BRC : " + BRC + "\n";
    }
}