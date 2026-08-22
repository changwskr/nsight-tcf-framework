package nhnis.eos.co.a.application.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.eos.co.a.persistence.dao.eoscoa0151DAO;
import nhnis.eos.co.a.support.EosResults;

@Service
public class eoscoa0151Service {
    private final eoscoa0151DAO dao;
    public eoscoa0151Service(eoscoa0151DAO dao) { this.dao = dao; }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public Map<String, Object> eoscoa0151S0(Map<String, Object> in) {
        String histId = EosResults.str(in, "histId");
        if (histId != null) {
            Map<String, Object> d = dao.detail(Map.of("histId", histId));
            if (d == null) return EosResults.fail("EOS-E0004", "NOT_FOUND");
            return EosResults.ok(Map.of("detail", d));
        }
        int pageNo = EosResults.intVal(in, "pageNo", 1);
        int pageSize = EosResults.intVal(in, "pageSize", 20);
        if (pageSize > 100) pageSize = 100;
        Map<String, Object> param = new HashMap<>(in);
        param.put("offset", (pageNo - 1) * pageSize);
        param.put("pageSize", pageSize);
        int total = dao.count(param);
        List<Map<String, Object>> list = dao.search(param);
        return EosResults.ok(Map.of(
                "list", list == null ? List.of() : list,
                "pageNo", pageNo,
                "pageSize", pageSize,
                "totalCount", total
        ));
    }
}
