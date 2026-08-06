package nhnis.mk.co.a.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 마케팅희망고객 조회 출력 (mkpca5530S0).
 */
public class mkpca5530S0DTOout {

    private List<Map<String, Object>> records = new ArrayList<>();

    public List<Map<String, Object>> getRecords() {
        return records;
    }

    public void setRecords(List<Map<String, Object>> records) {
        this.records = records != null ? records : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "records : " + records;
    }
}