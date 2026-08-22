package nhnis.eos.co.a.application.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.eos.co.a.persistence.dao.eoscoa0170DAO;
import nhnis.eos.co.a.support.EosAuditWriter;
import nhnis.eos.co.a.support.EosDtm;
import nhnis.eos.co.a.support.EosIdGenerator;
import nhnis.eos.co.a.support.EosResults;

@Service
public class eoscoa0170Service {

    private final eoscoa0170DAO dao;
    private final EosIdGenerator ids;
    private final EosAuditWriter audit;

    public eoscoa0170Service(eoscoa0170DAO dao, EosIdGenerator ids, EosAuditWriter audit) {
        this.dao = dao; this.ids = ids; this.audit = audit;
    }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public Map<String, Object> eoscoa0170S0(Map<String, Object> in) {
        String id = EosResults.str(in, "excReqId");
        if (id != null) {
            Map<String, Object> one = dao.selectOne(Map.of("excReqId", id));
            if (one == null) return EosResults.fail("EOS-E0004", "NOT_FOUND");
            return EosResults.ok(Map.of("detail", one));
        }
        List<Map<String, Object>> list = dao.list(in);
        return EosResults.ok(Map.of("list", list == null ? List.of() : list));
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0170C0(Map<String, Object> in) {
        String resourceId = EosResults.str(in, "resourceId");
        String start = EosResults.str(in, "startYmd");
        String end = EosResults.str(in, "endYmd");
        String reason = EosResults.str(in, "reasonTxt");
        String blocker = EosResults.str(in, "blockerTxt");
        String mitigation = EosResults.str(in, "mitigationTxt");
        String finalPlan = EosResults.str(in, "finalPlanTxt");
        String finalTarget = EosResults.str(in, "finalTargetYmd");
        String exit = EosResults.str(in, "exitCriteriaTxt");
        if (resourceId == null || start == null || end == null || reason == null || blocker == null
                || mitigation == null || finalPlan == null || finalTarget == null || exit == null) {
            return EosResults.fail("EOS-E0001", "REQUIRED exception fields");
        }
        if (end.compareTo(start) < 0) return EosResults.fail("EOS-E0001", "END < START");
        String reqUser = nvl(EosResults.str(in, "reqUserId"), "LOCAL");
        String excReqId = ids.next("EXC");
        String dtm = EosDtm.now();
        Map<String, Object> p = new HashMap<>();
        p.put("excReqId", excReqId);
        p.put("resourceId", resourceId);
        p.put("reqOrgCd", nvl(EosResults.str(in, "reqOrgCd"), "ORG-IT"));
        p.put("reqUserId", reqUser);
        p.put("reqDtm", dtm);
        p.put("startYmd", start);
        p.put("endYmd", end);
        p.put("reasonTxt", reason);
        p.put("blockerTxt", blocker);
        p.put("mitigationTxt", mitigation);
        p.put("finalPlanTxt", finalPlan);
        p.put("finalTargetYmd", finalTarget);
        p.put("monthlyCheckYn", nvl(EosResults.str(in, "monthlyCheckYn"), "Y"));
        p.put("exitCriteriaTxt", exit);
        p.put("statusCd", "PENDING");
        p.put("regUserId", reqUser);
        p.put("regDtm", dtm);
        dao.insert(p);
        audit.write("eoscoa0170C0", "EXCEPTION", excReqId, "CREATE", null, "PENDING", "OK", null);
        return EosResults.ok(Map.of("excReqId", excReqId, "statusCd", "PENDING"));
    }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public Map<String, Object> eoscoa0180S0(Map<String, Object> in) {
        String id = EosResults.str(in, "excReqId");
        if (id != null) {
            Map<String, Object> one = dao.selectOne(Map.of("excReqId", id));
            if (one == null) return EosResults.fail("EOS-E0004", "NOT_FOUND");
            return EosResults.ok(Map.of("detail", one));
        }
        List<Map<String, Object>> list = dao.inbox(Map.of());
        return EosResults.ok(Map.of("list", list == null ? List.of() : list));
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0180U0(Map<String, Object> in) {
        String excReqId = EosResults.str(in, "excReqId");
        String decision = EosResults.str(in, "decisionCd");
        String approver = nvl(EosResults.str(in, "approverId"), "APPROVER");
        if (excReqId == null || decision == null) return EosResults.fail("EOS-E0001", "REQUIRED: excReqId, decisionCd");
        Map<String, Object> req = dao.selectOne(Map.of("excReqId", excReqId));
        if (req == null) return EosResults.fail("EOS-E0004", "NOT_FOUND");
        if (!"PENDING".equals(EosResults.str(req, "STATUS_CD"))) return EosResults.fail("EOS-E0003", "NOT_PENDING");
        if (approver.equalsIgnoreCase(EosResults.str(req, "REQ_USER_ID"))) {
            return EosResults.fail("EOS-E0002", "SOD: approver != requester");
        }
        if ("CONDITIONAL".equals(decision) && EosResults.str(in, "conditionTxt") == null) {
            return EosResults.fail("EOS-E0001", "REQUIRED: conditionTxt");
        }
        if ("REJECT".equals(decision) && EosResults.str(in, "rejectReasonTxt") == null) {
            return EosResults.fail("EOS-E0001", "REQUIRED: rejectReasonTxt");
        }
        String dtm = EosDtm.now();
        String apprId = ids.next("APR");
        Map<String, Object> ap = new HashMap<>();
        ap.put("apprId", apprId);
        ap.put("excReqId", excReqId);
        ap.put("decisionCd", decision);
        ap.put("conditionTxt", EosResults.str(in, "conditionTxt"));
        ap.put("rejectReasonTxt", EosResults.str(in, "rejectReasonTxt"));
        ap.put("approverId", approver);
        ap.put("approveDtm", dtm);
        ap.put("extendOfApprId", null);
        ap.put("regUserId", approver);
        ap.put("regDtm", dtm);
        dao.insertAppr(ap);
        String newStatus = switch (decision) {
            case "APPROVE" -> "APPROVED";
            case "CONDITIONAL" -> "CONDITIONAL";
            default -> "REJECTED";
        };
        Map<String, Object> st = new HashMap<>();
        st.put("excReqId", excReqId);
        st.put("statusCd", newStatus);
        st.put("chgUserId", approver);
        st.put("chgDtm", dtm);
        dao.updateStatus(st);
        String active = ("APPROVE".equals(decision) || "CONDITIONAL".equals(decision)) ? "Y" : "N";
        Map<String, Object> rsc = new HashMap<>();
        rsc.put("resourceId", EosResults.str(req, "RESOURCE_ID"));
        rsc.put("exceptionActiveYn", active);
        rsc.put("chgUserId", approver);
        rsc.put("chgDtm", dtm);
        dao.updateResourceExceptionYn(rsc);
        audit.write("eoscoa0180U0", "EXCEPTION", excReqId, decision, "PENDING", newStatus, "OK", null);
        return EosResults.ok(Map.of("apprId", apprId, "statusCd", newStatus, "exceptionActiveYn", active));
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0180U1(Map<String, Object> in) {
        // extend: new approval on same request with new dates stored in condition/reason; re-approve
        in.putIfAbsent("decisionCd", "APPROVE");
        return eoscoa0180U0(in);
    }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public Map<String, Object> eoscoa0190S0(Map<String, Object> in) {
        List<Map<String, Object>> list = dao.listMonthly(in);
        return EosResults.ok(Map.of("list", list == null ? List.of() : list));
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0190C0(Map<String, Object> in) {
        String excReqId = EosResults.str(in, "excReqId");
        String checkYm = EosResults.str(in, "checkYm");
        String okYn = EosResults.str(in, "mitigationOkYn");
        if (excReqId == null || checkYm == null || okYn == null) {
            return EosResults.fail("EOS-E0001", "REQUIRED: excReqId, checkYm, mitigationOkYn");
        }
        String checkId = ids.next("MCK");
        String dtm = EosDtm.now();
        Map<String, Object> p = new HashMap<>();
        p.put("checkId", checkId);
        p.put("excReqId", excReqId);
        p.put("checkYm", checkYm);
        p.put("mitigationOkYn", okYn);
        p.put("residualRiskTxt", EosResults.str(in, "residualRiskTxt"));
        p.put("planProgressTxt", EosResults.str(in, "planProgressTxt"));
        p.put("issueTxt", EosResults.str(in, "issueTxt"));
        p.put("nextCheckYmd", EosResults.str(in, "nextCheckYmd"));
        p.put("checkerId", nvl(EosResults.str(in, "checkerId"), "LOCAL"));
        p.put("checkDtm", dtm);
        p.put("regUserId", "LOCAL");
        p.put("regDtm", dtm);
        try {
            dao.insertMonthly(p);
        } catch (Exception e) {
            return EosResults.fail("EOS-E0005", "DUPLICATE_CHECK_YM");
        }
        return EosResults.ok(Map.of("checkId", checkId));
    }

    private static String nvl(String a, String b) { return a != null ? a : b; }
}
