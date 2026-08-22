package nhnis.eos.co.a.application.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.eos.co.a.persistence.dao.eoscoa0141DAO;
import nhnis.eos.co.a.support.EosAuditWriter;
import nhnis.eos.co.a.support.EosDtm;
import nhnis.eos.co.a.support.EosIdGenerator;
import nhnis.eos.co.a.support.EosResults;

@Service
public class eoscoa0141Service {

    private final eoscoa0141DAO dao;
    private final EosIdGenerator ids;
    private final EosAuditWriter audit;

    public eoscoa0141Service(eoscoa0141DAO dao, EosIdGenerator ids, EosAuditWriter audit) {
        this.dao = dao; this.ids = ids; this.audit = audit;
    }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public Map<String, Object> eoscoa0141S0(Map<String, Object> in) {
        String mode = EosResults.str(in, "mode");
        if ("POLICY".equalsIgnoreCase(mode)) {
            return EosResults.ok(Map.of("list", dao.listPolicies(in)));
        }
        if (EosResults.str(in, "grpId") != null) {
            return EosResults.ok(Map.of("list", dao.listCodes(in)));
        }
        return EosResults.ok(Map.of("groups", dao.listGroups(), "codes", dao.listCodes(Map.of())));
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0141C0(Map<String, Object> in) {
        String dtm = EosDtm.now();
        if ("POLICY".equalsIgnoreCase(EosResults.str(in, "mode"))) {
            String type = EosResults.str(in, "policyTypeCd");
            String key = EosResults.str(in, "policyKey");
            String val = EosResults.str(in, "policyVal");
            if (type == null || key == null || val == null) return EosResults.fail("EOS-E0001", "REQUIRED policy");
            dao.closePolicy(Map.of("policyTypeCd", type, "policyKey", key, "effectiveToDtm", dtm));
            String policyId = ids.next("POL");
            Map<String, Object> p = new HashMap<>();
            p.put("policyId", policyId);
            p.put("policyTypeCd", type);
            p.put("policyKey", key);
            p.put("policyVal", val);
            p.put("effectiveFromDtm", dtm);
            p.put("changeReason", nvl(EosResults.str(in, "changeReason"), "create"));
            p.put("regUserId", "LOCAL");
            p.put("regDtm", dtm);
            dao.insertPolicy(p);
            audit.write("eoscoa0141C0", "POLICY", policyId, "CREATE", null, val, "OK", null);
            return EosResults.ok(Map.of("policyId", policyId));
        }
        String grpId = EosResults.str(in, "grpId");
        String code = EosResults.str(in, "code");
        String name = EosResults.str(in, "codeName");
        if (grpId == null || code == null || name == null) return EosResults.fail("EOS-E0001", "REQUIRED code");
        Map<String, Object> p = new HashMap<>();
        p.put("grpId", grpId);
        p.put("code", code);
        p.put("codeName", name);
        p.put("sortOrd", EosResults.intVal(in, "sortOrd", 0));
        p.put("useYn", nvl(EosResults.str(in, "useYn"), "Y"));
        p.put("regUserId", "LOCAL");
        p.put("regDtm", dtm);
        try {
            dao.insertCode(p);
        } catch (Exception e) {
            return EosResults.fail("EOS-E0005", "DUPLICATE");
        }
        return EosResults.ok(Map.of("grpId", grpId, "code", code));
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0141U0(Map<String, Object> in) {
        if ("POLICY".equalsIgnoreCase(EosResults.str(in, "mode"))) {
            return eoscoa0141C0(in); // versioned replace
        }
        String grpId = EosResults.str(in, "grpId");
        String code = EosResults.str(in, "code");
        if (grpId == null || code == null) return EosResults.fail("EOS-E0001", "REQUIRED");
        Map<String, Object> p = new HashMap<>();
        p.put("grpId", grpId);
        p.put("code", code);
        p.put("codeName", nvl(EosResults.str(in, "codeName"), code));
        p.put("sortOrd", EosResults.intVal(in, "sortOrd", 0));
        p.put("useYn", nvl(EosResults.str(in, "useYn"), "Y"));
        p.put("chgUserId", "LOCAL");
        p.put("chgDtm", EosDtm.now());
        dao.updateCode(p);
        audit.write("eoscoa0141U0", "CODE", grpId + ":" + code, "UPDATE", null, p.get("useYn").toString(), "OK", null);
        return EosResults.ok(Map.of("grpId", grpId, "code", code));
    }

    private static String nvl(String a, String b) { return a != null ? a : b; }
}
