package nhnis.mk.co.a.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 마케팅희망고객 삭제 입력 (mkpca5530S3).
 */
public class mkpca5530S3DTOin {

    private List<Integer> SQNO_LIST = new ArrayList<>();

    public List<Integer> getSQNO_LIST() {
        return SQNO_LIST;
    }

    public void setSQNO_LIST(List<Integer> SQNO_LIST) {
        this.SQNO_LIST = SQNO_LIST != null ? SQNO_LIST : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "SQNO_LIST : " + SQNO_LIST + "\n";
    }
}
