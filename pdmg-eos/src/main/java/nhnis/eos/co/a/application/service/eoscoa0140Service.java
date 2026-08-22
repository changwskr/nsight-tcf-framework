package nhnis.eos.co.a.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.eos.co.a.domain.EosStatusEngine;
import nhnis.eos.co.a.dto.eoscoa0140S0DTOin;
import nhnis.eos.co.a.dto.eoscoa0140S0DTOout;
import nhnis.eos.co.a.dto.eoscoa0140U0DTOin;
import nhnis.eos.co.a.dto.eoscoa0140U0DTOout;
import nhnis.eos.co.a.persistence.dao.eoscoa0140DAO;
import nhnis.eos.co.a.support.EosDtm;
import nhnis.eos.co.a.support.EosIdGenerator;

@Service
public class eoscoa0140Service {

    private final eoscoa0140DAO dao;
    private final EosStatusEngine statusEngine;
    private final EosIdGenerator idGenerator;

    public eoscoa0140Service(eoscoa0140DAO dao, EosStatusEngine statusEngine, EosIdGenerator idGenerator) {
        this.dao = dao;
        this.statusEngine = statusEngine;
        this.idGenerator = idGenerator;
    }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public eoscoa0140S0DTOout eoscoa0140S0(eoscoa0140S0DTOin input) {
        Map<String, Object> param = new HashMap<>();
        if (input != null) {
            put(param, "productId", input.getProductId());
            put(param, "versionId", input.getVersionId());
            put(param, "resourceTypeCd", input.getResourceTypeCd());
        }
        int pageNo = input == null || input.getPageNo() == null || input.getPageNo() <= 0 ? 1 : input.getPageNo();
        int pageSize = input == null || input.getPageSize() == null || input.getPageSize() <= 0 ? 20 : input.getPageSize();
        if (pageSize > 100) pageSize = 100;
        param.put("offset", (pageNo - 1) * pageSize);
        param.put("pageSize", pageSize);

        int total = dao.eoscoa0140S0_S0_count(param);
        List<Map<String, Object>> rows = dao.eoscoa0140S0_S0(param);
        List<Map<String, Object>> list = new ArrayList<>();
        if (rows != null) {
            for (Map<String, Object> row : rows) {
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("productId", str(row, "PRODUCT_ID"));
                item.put("productName", str(row, "PRODUCT_NAME"));
                item.put("resourceTypeCd", str(row, "RESOURCE_TYPE_CD"));
                item.put("versionId", str(row, "VERSION_ID"));
                item.put("versionNo", str(row, "VERSION_NO"));
                item.put("lfcId", str(row, "LFC_ID"));
                item.put("gaYmd", str(row, "GA_YMD"));
                item.put("eosYmd", str(row, "EOS_YMD"));
                item.put("eolYmd", str(row, "EOL_YMD"));
                item.put("effectiveFromDtm", str(row, "EFFECTIVE_FROM_DTM"));
                item.put("evidenceId", str(row, "EVIDENCE_ID"));
                list.add(item);
            }
        }
        eoscoa0140S0DTOout out = new eoscoa0140S0DTOout();
        out.setList(list);
        out.setSize(list.size());
        out.setPageNo(pageNo);
        out.setPageSize(pageSize);
        out.setTotalCount(total);
        out.setRSLT_CD("0000");
        out.setRSLT_MSG("OK");
        return out;
    }

    /** Lifecycle 변경(이력) + 선택적 Instance 일괄 재계산 (0140U0 / U1 통합) */
    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public eoscoa0140U0DTOout eoscoa0140U0(eoscoa0140U0DTOin input) {
        eoscoa0140U0DTOout out = new eoscoa0140U0DTOout();
        String versionId = trim(input == null ? null : input.getVersionId());
        String reason = trim(input == null ? null : input.getChangeReason());
        if (versionId == null || reason == null) {
            out.setRSLT_CD("EOS-E0001");
            out.setRSLT_MSG("REQUIRED: versionId, changeReason");
            return out;
        }
        String eosYmd = trim(input.getEosYmd());
        String eolYmd = trim(input.getEolYmd());
        String gaYmd = trim(input.getGaYmd());
        if (eosYmd == null && eolYmd == null && gaYmd == null) {
            out.setRSLT_CD("EOS-E0001");
            out.setRSLT_MSG("REQUIRED: at least one of gaYmd/eosYmd/eolYmd");
            return out;
        }

        String dtm = EosDtm.now();
        String openId = dao.selectOpenLfcId(Map.of("versionId", versionId));
        if (openId != null) {
            Map<String, Object> close = new HashMap<>();
            close.put("lfcId", openId);
            close.put("effectiveToDtm", dtm);
            dao.closeLfc(close);
        }
        String lfcId = idGenerator.lifecycleId();
        Map<String, Object> ins = new HashMap<>();
        ins.put("lfcId", lfcId);
        ins.put("versionId", versionId);
        ins.put("gaYmd", gaYmd);
        ins.put("eosYmd", eosYmd);
        ins.put("eolYmd", eolYmd);
        ins.put("evidenceId", trim(input.getEvidenceId()));
        ins.put("sourceDesc", empty(input.getSourceDesc()));
        ins.put("effectiveFromDtm", dtm);
        ins.put("changeReason", reason);
        ins.put("regUserId", "LOCAL");
        ins.put("regDtm", dtm);
        dao.insertLfc(ins);

        int affected = 0;
        int changed = 0;
        boolean recalc = !"N".equalsIgnoreCase(trim(input.getRecalcInstanceYn()) == null ? "Y" : input.getRecalcInstanceYn());
        if (recalc) {
            int approaching = policyInt("APPROACHING_DAYS", 365);
            int due = policyInt("DUE_DAYS", 90);
            String newStatus = statusEngine.resolveStatus(eosYmd, eolYmd, approaching, due);
            List<Map<String, Object>> resources = dao.listResourcesByVersion(Map.of("versionId", versionId));
            if (resources != null) {
                for (Map<String, Object> r : resources) {
                    affected++;
                    String old = str(r, "EOS_STATUS_CD");
                    if (newStatus != null && !newStatus.equals(old)) {
                        Map<String, Object> upd = new HashMap<>();
                        upd.put("resourceId", str(r, "RESOURCE_ID"));
                        upd.put("eosStatusCd", newStatus);
                        upd.put("chgUserId", "LOCAL");
                        upd.put("chgDtm", dtm);
                        dao.updateResourceStatus(upd);
                        changed++;
                    }
                }
            }
        }

        out.setLfcId(lfcId);
        out.setAffectedResourceCnt(affected);
        out.setStatusChangedCnt(changed);
        out.setRSLT_CD("0000");
        out.setRSLT_MSG("OK");
        return out;
    }

    private int policyInt(String key, int def) {
        String v = dao.selectPolicyVal(Map.of("policyTypeCd", "EOS_THRESHOLD", "policyKey", key));
        if (v == null || v.isBlank()) return def;
        try { return Integer.parseInt(v.trim()); } catch (NumberFormatException e) { return def; }
    }

    private static void put(Map<String, Object> p, String k, String v) {
        if (v != null && !v.isBlank()) p.put(k, v.trim());
    }
    private static String trim(String v) { return v == null || v.isBlank() ? null : v.trim(); }
    private static String empty(String v) { return v == null ? "" : v.trim(); }
    private static String str(Map<String, Object> row, String key) {
        if (row == null) return null;
        Object v = row.get(key);
        if (v == null) {
            for (Map.Entry<String, Object> e : row.entrySet()) {
                if (e.getKey() != null && e.getKey().equalsIgnoreCase(key)) { v = e.getValue(); break; }
            }
        }
        return v == null ? null : String.valueOf(v);
    }
}
