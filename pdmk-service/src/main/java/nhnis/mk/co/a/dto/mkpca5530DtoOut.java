package nhnis.mk.co.a.dto;

/**
 * mkpca5530 전문 출력 DTO (화면 필드 ID L5101~L5104).
 *
 * <p>운영 로그: {@code mkpca5530S0DTOsub0 : [L5101 : 18L5102 : ...]}
 */
public class mkpca5530DtoOut {

    /** 항목코드 */
    private String l5101;

    /** 항목명 */
    private String l5102;

    /** 기준일자 */
    private String l5103;

    /** 취급점코드 */
    private String l5104;

    public String getL5101() {
        return l5101;
    }

    public void setL5101(String l5101) {
        this.l5101 = l5101;
    }

    public String getL5102() {
        return l5102;
    }

    public void setL5102(String l5102) {
        this.l5102 = l5102;
    }

    public String getL5103() {
        return l5103;
    }

    public void setL5103(String l5103) {
        this.l5103 = l5103;
    }

    public String getL5104() {
        return l5104;
    }

    public void setL5104(String l5104) {
        this.l5104 = l5104;
    }

    @Override
    public String toString() {
        return "L5101 : " + l5101 + "L5102 : " + l5102 + "L5103 : " + l5103 + "L5104 : " + l5104;
    }
}
