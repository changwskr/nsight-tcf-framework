package nhnis.eos.co.a.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.eos.co.a.domain.EosStatusEngine;
import nhnis.eos.co.a.persistence.dao.eoscoa0110DAO;
import nhnis.eos.co.a.support.EosResults;

@Service
public class eoscoa0110Service {

    private final eoscoa0110DAO dao;
    private final EosStatusEngine statusEngine;

    public eoscoa0110Service(eoscoa0110DAO dao, EosStatusEngine statusEngine) {
        this.dao = dao;
        this.statusEngine = statusEngine;
    }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public Map<String, Object> eoscoa0110S0(Map<String, Object> in) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("totalCnt", dao.countTotal());
        data.put("criticalCnt", dao.countByRisk(Map.of("riskGradeCd", "CRITICAL")));
        data.put("highCnt", dao.countByRisk(Map.of("riskGradeCd", "HIGH")));
        data.put("mediumCnt", dao.countByRisk(Map.of("riskGradeCd", "MEDIUM")));
        data.put("lowCnt", dao.countByRisk(Map.of("riskGradeCd", "LOW")));
        // ADR-001 A: RISK_STATUS = APPROACHING,DUE,OVERDUE,EOL
        Map<String, Object> st = new HashMap<>();
        st.put("statuses", List.of("APPROACHING", "DUE", "OVERDUE", "EOL"));
        data.put("riskStatusCnt", dao.countByStatusIn(st));
        data.put("exceptionNeedCnt", dao.countExceptionNeed());
        data.put("actionInProgressCnt", dao.countActionInProgress());
        data.put("kpiPolicyId", "POL-KPI-001");
        data.put("kpiFormula", dao.selectKpiFormula());

        List<Map<String, Object>> top = dao.topPriority(Map.of("limit", 5));
        List<Map<String, Object>> topList = new ArrayList<>();
        if (top != null) {
            for (Map<String, Object> row : top) {
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("resourceId", EosResults.str(row, "RESOURCE_ID"));
                item.put("name", EosResults.str(row, "RESOURCE_NAME"));
                item.put("grade", EosResults.str(row, "RISK_GRADE_CD"));
                item.put("status", EosResults.str(row, "EOS_STATUS_CD"));
                item.put("remainDays", statusEngine.remainDays(EosResults.str(row, "EOS_YMD")));
                topList.add(item);
            }
        }
        data.put("topPriorityList", topList);
        return EosResults.ok(data);
    }
}
