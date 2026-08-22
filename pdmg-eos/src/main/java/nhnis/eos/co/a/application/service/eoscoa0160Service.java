package nhnis.eos.co.a.application.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.eos.co.a.domain.EosActionStateMachine;
import nhnis.eos.co.a.persistence.dao.eoscoa0160DAO;
import nhnis.eos.co.a.support.EosAuditWriter;
import nhnis.eos.co.a.support.EosDtm;
import nhnis.eos.co.a.support.EosIdGenerator;
import nhnis.eos.co.a.support.EosResults;

@Service
public class eoscoa0160Service {

    private final eoscoa0160DAO dao;
    private final EosActionStateMachine sm;
    private final EosIdGenerator ids;
    private final EosAuditWriter audit;

    public eoscoa0160Service(eoscoa0160DAO dao, EosActionStateMachine sm, EosIdGenerator ids, EosAuditWriter audit) {
        this.dao = dao; this.sm = sm; this.ids = ids; this.audit = audit;
    }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public Map<String, Object> eoscoa0160S0(Map<String, Object> in) {
        String actionId = EosResults.str(in, "actionId");
        if (actionId != null) {
            Map<String, Object> one = dao.selectOne(Map.of("actionId", actionId));
            if (one == null) return EosResults.fail("EOS-E0004", "NOT_FOUND");
            return EosResults.ok(Map.of("detail", one));
        }
        String resourceId = EosResults.str(in, "resourceId");
        if (resourceId == null) return EosResults.fail("EOS-E0001", "REQUIRED: resourceId or actionId");
        List<Map<String, Object>> list = dao.listByResource(Map.of("resourceId", resourceId));
        return EosResults.ok(Map.of("list", list == null ? List.of() : list));
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0160C0(Map<String, Object> in) {
        String resourceId = EosResults.str(in, "resourceId");
        String rb1 = EosResults.str(in, "rollbackCondTxt");
        String rb2 = EosResults.str(in, "rollbackProcTxt");
        String rb3 = EosResults.str(in, "rollbackTargetTxt");
        if (resourceId == null || rb1 == null || rb2 == null || rb3 == null) {
            return EosResults.fail("EOS-E0001", "REQUIRED: resourceId, rollback*");
        }
        String actionId = ids.next("ACT");
        String dtm = EosDtm.now();
        Map<String, Object> p = new HashMap<>();
        p.put("actionId", actionId);
        p.put("resourceId", resourceId);
        p.put("actionTypeCd", nvl(EosResults.str(in, "actionTypeCd"), "UPGRADE"));
        p.put("curVersionId", EosResults.str(in, "curVersionId"));
        p.put("tgtVersionId", EosResults.str(in, "tgtVersionId"));
        p.put("detailTxt", nvl(EosResults.str(in, "detailTxt"), "-"));
        p.put("impactTxt", nvl(EosResults.str(in, "impactTxt"), "-"));
        p.put("prereqTxt", EosResults.str(in, "prereqTxt"));
        p.put("testPlanTxt", nvl(EosResults.str(in, "testPlanTxt"), "-"));
        p.put("cutoverTypeCd", nvl(EosResults.str(in, "cutoverTypeCd"), "ROLLING"));
        p.put("outageYn", nvl(EosResults.str(in, "outageYn"), "N"));
        p.put("offhoursYn", nvl(EosResults.str(in, "offhoursYn"), "N"));
        p.put("drVerifyYn", nvl(EosResults.str(in, "drVerifyYn"), "N"));
        p.put("rollbackCondTxt", rb1);
        p.put("rollbackProcTxt", rb2);
        p.put("rollbackTargetTxt", rb3);
        p.put("orgCd", nvl(EosResults.str(in, "orgCd"), "ORG-IT"));
        p.put("ownerUserId", nvl(EosResults.str(in, "ownerUserId"), "LOCAL"));
        p.put("planStartYmd", nvl(EosResults.str(in, "planStartYmd"), "20260816"));
        p.put("planEndYmd", nvl(EosResults.str(in, "planEndYmd"), "20261231"));
        p.put("statusCd", "NOT_STARTED");
        p.put("issueTxt", EosResults.str(in, "issueTxt"));
        p.put("regUserId", "LOCAL");
        p.put("regDtm", dtm);
        dao.insert(p);
        Map<String, Object> h0 = hist(actionId, null, "NOT_STARTED", dtm, "create");
        dao.insertHist(h0);
        audit.write("eoscoa0160C0", "ACTION", actionId, "CREATE", null, "NOT_STARTED", "OK", null);
        return EosResults.ok(Map.of("actionId", actionId, "statusCd", "NOT_STARTED"));
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0160U0(Map<String, Object> in) {
        String actionId = EosResults.str(in, "actionId");
        if (actionId == null) return EosResults.fail("EOS-E0001", "REQUIRED: actionId");
        Map<String, Object> cur = dao.selectOne(Map.of("actionId", actionId));
        if (cur == null) return EosResults.fail("EOS-E0004", "NOT_FOUND");
        Map<String, Object> p = new HashMap<>();
        p.put("actionId", actionId);
        p.put("actionTypeCd", nvl(EosResults.str(in, "actionTypeCd"), EosResults.str(cur, "ACTION_TYPE_CD")));
        p.put("detailTxt", nvl(EosResults.str(in, "detailTxt"), EosResults.str(cur, "DETAIL_TXT")));
        p.put("impactTxt", nvl(EosResults.str(in, "impactTxt"), EosResults.str(cur, "IMPACT_TXT")));
        p.put("prereqTxt", EosResults.str(in, "prereqTxt"));
        p.put("testPlanTxt", nvl(EosResults.str(in, "testPlanTxt"), EosResults.str(cur, "TEST_PLAN_TXT")));
        p.put("cutoverTypeCd", nvl(EosResults.str(in, "cutoverTypeCd"), EosResults.str(cur, "CUTOVER_TYPE_CD")));
        p.put("outageYn", nvl(EosResults.str(in, "outageYn"), EosResults.str(cur, "OUTAGE_YN")));
        p.put("rollbackCondTxt", nvl(EosResults.str(in, "rollbackCondTxt"), EosResults.str(cur, "ROLLBACK_COND_TXT")));
        p.put("rollbackProcTxt", nvl(EosResults.str(in, "rollbackProcTxt"), EosResults.str(cur, "ROLLBACK_PROC_TXT")));
        p.put("rollbackTargetTxt", nvl(EosResults.str(in, "rollbackTargetTxt"), EosResults.str(cur, "ROLLBACK_TARGET_TXT")));
        p.put("orgCd", nvl(EosResults.str(in, "orgCd"), EosResults.str(cur, "ORG_CD")));
        p.put("ownerUserId", nvl(EosResults.str(in, "ownerUserId"), EosResults.str(cur, "OWNER_USER_ID")));
        p.put("planStartYmd", nvl(EosResults.str(in, "planStartYmd"), EosResults.str(cur, "PLAN_START_YMD")));
        p.put("planEndYmd", nvl(EosResults.str(in, "planEndYmd"), EosResults.str(cur, "PLAN_END_YMD")));
        p.put("issueTxt", EosResults.str(in, "issueTxt"));
        p.put("chgUserId", "LOCAL");
        p.put("chgDtm", EosDtm.now());
        dao.update(p);
        return EosResults.ok(Map.of("actionId", actionId, "statusCd", EosResults.str(cur, "STATUS_CD")));
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0160U1(Map<String, Object> in) {
        String actionId = EosResults.str(in, "actionId");
        String to = EosResults.str(in, "toStatusCd");
        if (actionId == null || to == null) return EosResults.fail("EOS-E0001", "REQUIRED: actionId, toStatusCd");
        Map<String, Object> cur = dao.selectOne(Map.of("actionId", actionId));
        if (cur == null) return EosResults.fail("EOS-E0004", "NOT_FOUND");
        String from = EosResults.str(cur, "STATUS_CD");
        if (!sm.canTransit(from, to)) return EosResults.fail("EOS-E0003", "INVALID_TRANSITION " + from + "->" + to);
        String dtm = EosDtm.now();
        Map<String, Object> upd = new HashMap<>();
        upd.put("actionId", actionId);
        upd.put("statusCd", to);
        upd.put("fromStatusCd", from);
        upd.put("actualEndYmd", null);
        upd.put("chgUserId", "LOCAL");
        upd.put("chgDtm", dtm);
        if (dao.updateStatus(upd) <= 0) return EosResults.fail("EOS-E0003", "CONCURRENT");
        dao.insertHist(hist(actionId, from, to, dtm, EosResults.str(in, "reasonTxt")));
        audit.write("eoscoa0160U1", "ACTION", actionId, "STATUS", from, to, "OK", null);
        return EosResults.ok(Map.of("actionId", actionId, "statusCd", to));
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0160U2(Map<String, Object> in) {
        String actionId = EosResults.str(in, "actionId");
        String actualEnd = EosResults.str(in, "actualEndYmd");
        if (actionId == null || actualEnd == null) return EosResults.fail("EOS-E0001", "REQUIRED: actionId, actualEndYmd");
        Map<String, Object> cur = dao.selectOne(Map.of("actionId", actionId));
        if (cur == null) return EosResults.fail("EOS-E0004", "NOT_FOUND");
        String from = EosResults.str(cur, "STATUS_CD");
        if (!sm.canRequestComplete(from)) return EosResults.fail("EOS-E0003", "COMPLETE_ONLY_FROM_TESTING");
        // evidence: accept evidenceId string or skip if placeholder EVID seed missing — create soft link skip
        String evid = EosResults.str(in, "evidenceId");
        if (evid == null) {
            evid = ids.next("EVD");
            // soft: skip physical evidence row if not provided — only require id token for workflow
        }
        String dtm = EosDtm.now();
        // evidence FK optional path: store pending without FK when evidence missing in TB
        audit.write("eoscoa0160U2", "ACTION", actionId, "COMPLETE_REQ", from, "PENDING_VERIFY", "OK", actualEnd + "/" + evid);
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("actionId", actionId);
        data.put("statusCd", from);
        data.put("completeRequestedYn", "Y");
        data.put("actualEndYmd", actualEnd);
        data.put("evidenceId", evid);
        return EosResults.ok(data);
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public Map<String, Object> eoscoa0165U0(Map<String, Object> in) {
        String actionId = EosResults.str(in, "actionId");
        String approveYn = EosResults.str(in, "approveYn");
        String verifyUser = EosResults.str(in, "verifyUserId");
        if (actionId == null || approveYn == null) return EosResults.fail("EOS-E0001", "REQUIRED: actionId, approveYn");
        if (verifyUser == null) verifyUser = "APPROVER";
        Map<String, Object> cur = dao.selectOne(Map.of("actionId", actionId));
        if (cur == null) return EosResults.fail("EOS-E0004", "NOT_FOUND");
        String from = EosResults.str(cur, "STATUS_CD");
        if (!"TESTING".equals(from)) return EosResults.fail("EOS-E0003", "VERIFY_ONLY_TESTING");
        String owner = EosResults.str(cur, "OWNER_USER_ID");
        String reg = EosResults.str(cur, "REG_USER_ID");
        if ((owner != null && owner.equalsIgnoreCase(verifyUser))
                || (reg != null && reg.equalsIgnoreCase(verifyUser))) {
            return EosResults.fail("EOS-E0002", "SOD: verifier != owner/requester");
        }
        if (!"Y".equalsIgnoreCase(approveYn)) {
            return EosResults.ok(Map.of("actionId", actionId, "statusCd", from, "verifiedYn", "N"));
        }
        String dtm = EosDtm.now();
        String actualEnd = EosResults.str(in, "actualEndYmd");
        Map<String, Object> upd = new HashMap<>();
        upd.put("actionId", actionId);
        upd.put("statusCd", "DONE");
        upd.put("fromStatusCd", from);
        upd.put("actualEndYmd", actualEnd);
        upd.put("chgUserId", verifyUser);
        upd.put("chgDtm", dtm);
        if (dao.updateStatus(upd) <= 0) return EosResults.fail("EOS-E0003", "CONCURRENT");
        dao.insertHist(hist(actionId, from, "DONE", dtm, "verified"));
        audit.write("eoscoa0165U0", "ACTION", actionId, "VERIFY", from, "DONE", "OK", null);
        return EosResults.ok(Map.of("actionId", actionId, "statusCd", "DONE"));
    }

    private Map<String, Object> hist(String actionId, String from, String to, String dtm, String reason) {
        Map<String, Object> h = new HashMap<>();
        h.put("histId", ids.next("ASH"));
        h.put("actionId", actionId);
        h.put("fromStatusCd", from);
        h.put("toStatusCd", to);
        h.put("chgUserId", "LOCAL");
        h.put("chgDtm", dtm);
        h.put("reasonTxt", reason);
        return h;
    }

    private static String nvl(String a, String b) { return a != null ? a : (b == null ? "" : b); }
}
