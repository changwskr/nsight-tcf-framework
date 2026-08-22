package nhnis.eos.co.a.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.eos.co.a.domain.EosRiskCalculator;
import nhnis.eos.co.a.persistence.dao.eoscoa0150DAO;
import nhnis.eos.co.a.support.EosAuditWriter;
import nhnis.eos.co.a.support.EosDtm;
import nhnis.eos.co.a.support.EosIdGenerator;
import nhnis.eos.co.a.support.EosResults;

@Service
public class eoscoa0150Service {

    private final eoscoa0150DAO dao;
    private final EosRiskCalculator calc;
    private final EosIdGenerator ids;
    private final EosAuditWriter audit;

    public eoscoa0150Service(eoscoa0150DAO dao, EosRiskCalculator calc, EosIdGenerator ids, EosAuditWriter audit) {
        this.dao = dao;
        this.calc = calc;
        this.ids = ids;
        this.audit = audit;
    }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public Map<String, Object> eoscoa0150S0(Map<String, Object> in) {
        String resourceId = EosResults.str(in, "resourceId");
        if (resourceId == null) return EosResults.fail("EOS-E0001", "REQUIRED: resourceId");
        Map<String, Object> latest = dao.selectLatest(Map.of("resourceId", resourceId));
        Map<String, Object> data = new LinkedHashMap<>();
        if (latest != null) {
            Map<String, Object> current = new LinkedHashMap<>();
            current.put("assessId", EosResults.str(latest, "ASSESS_ID"));
            current.put("totalScore", latest.get("TOTAL_SCORE") != null ? latest.get("TOTAL_SCORE") : latest.get("totalScore"));
            current.put("riskGradeCd", EosResults.str(latest, "RISK_GRADE_CD"));
            current.put("assessorId", EosResults.str(latest, "ASSESSOR_ID"));
            current.put("assessDtm", EosResults.str(latest, "ASSESS_DTM"));
            current.put("commentTxt", EosResults.str(latest, "COMMENT_TXT"));
            List<Map<String, Object>> scores = dao.selectScores(Map.of("assessId", EosResults.str(latest, "ASSESS_ID")));
            List<Map<String, Object>> scoreList = new ArrayList<>();
            if (scores != null) {
                for (Map<String, Object> s : scores) {
                    scoreList.add(Map.of("itemCd", EosResults.str(s, "ITEM_CD"), "score", s.get("SCORE") != null ? s.get("SCORE") : s.get("score")));
                }
            }
            current.put("scores", scoreList);
            data.put("current", current);
        }
        List<Map<String, Object>> hist = dao.selectHistory(Map.of("resourceId", resourceId));
        data.put("history", hist == null ? List.of() : hist);
        return EosResults.ok(data);
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0150C0(Map<String, Object> in) {
        String resourceId = EosResults.str(in, "resourceId");
        if (resourceId == null) return EosResults.fail("EOS-E0001", "REQUIRED: resourceId");
        if (dao.existsResource(Map.of("resourceId", resourceId)) <= 0) {
            return EosResults.fail("EOS-E0004", "NOT_FOUND");
        }
        int[] scores = {
                EosResults.intVal(in, "scoreBiz", 0),
                EosResults.intVal(in, "scoreEnv", 0),
                EosResults.intVal(in, "scoreExp", 0),
                EosResults.intVal(in, "scoreSec", 0),
                EosResults.intVal(in, "scoreImp", 0),
                EosResults.intVal(in, "scoreAlt", 0),
                EosResults.intVal(in, "scoreEos", 0)
        };
        String[] items = {"BIZ", "ENV", "EXP", "SEC", "IMP", "ALT", "EOS"};
        try {
            for (int s : scores) calc.validateScore(s);
        } catch (IllegalArgumentException e) {
            return EosResults.fail("EOS-E0001", e.getMessage());
        }
        int total = calc.total(scores[0], scores[1], scores[2], scores[3], scores[4], scores[5], scores[6]);
        int cMin = band("CRITICAL_MIN", 32);
        int hMin = band("HIGH_MIN", 26);
        int mMin = band("MEDIUM_MIN", 20);
        String grade = calc.grade(total, cMin, hMin, mMin);
        String assessId = ids.next("RSK");
        String dtm = EosDtm.now();
        Map<String, Object> assess = new HashMap<>();
        assess.put("assessId", assessId);
        assess.put("resourceId", resourceId);
        assess.put("totalScore", total);
        assess.put("riskGradeCd", grade);
        assess.put("commentTxt", EosResults.str(in, "commentTxt"));
        assess.put("assessorId", "LOCAL");
        assess.put("assessDtm", dtm);
        assess.put("policyId", "POL-RSK-001");
        assess.put("regUserId", "LOCAL");
        assess.put("regDtm", dtm);
        dao.insertAssess(assess);
        for (int i = 0; i < items.length; i++) {
            dao.insertScore(Map.of("assessId", assessId, "itemCd", items[i], "score", scores[i]));
        }
        Map<String, Object> upd = new HashMap<>();
        upd.put("resourceId", resourceId);
        upd.put("riskGradeCd", grade);
        upd.put("chgUserId", "LOCAL");
        upd.put("chgDtm", dtm);
        dao.updateResourceGrade(upd);
        audit.write("eoscoa0150C0", "RISK", assessId, "CREATE", null, grade, "OK", null);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("assessId", assessId);
        data.put("totalScore", total);
        data.put("riskGradeCd", grade);
        data.put("followUpActionYn", "CRITICAL".equals(grade) || "HIGH".equals(grade) ? "Y" : "N");
        data.put("followUpExceptionYn", "CRITICAL".equals(grade) ? "Y" : "N");
        return EosResults.ok(data);
    }

    private int band(String key, int def) {
        String v = dao.selectPolicyVal(Map.of("policyTypeCd", "RISK_GRADE_BAND", "policyKey", key));
        if (v == null) return def;
        try { return Integer.parseInt(v.trim()); } catch (Exception e) { return def; }
    }
}
