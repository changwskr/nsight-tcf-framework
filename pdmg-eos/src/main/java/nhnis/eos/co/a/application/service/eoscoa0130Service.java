package nhnis.eos.co.a.application.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.eos.co.a.domain.EosStatusEngine;
import nhnis.eos.co.a.dto.eoscoa0130C0DTOin;
import nhnis.eos.co.a.dto.eoscoa0130C0DTOout;
import nhnis.eos.co.a.dto.eoscoa0130D0DTOin;
import nhnis.eos.co.a.dto.eoscoa0130D0DTOout;
import nhnis.eos.co.a.dto.eoscoa0130S0DTOin;
import nhnis.eos.co.a.dto.eoscoa0130S0DTOout;
import nhnis.eos.co.a.dto.eoscoa0130U0DTOin;
import nhnis.eos.co.a.dto.eoscoa0130U0DTOout;
import nhnis.eos.co.a.persistence.dao.eoscoa0130DAO;
import nhnis.eos.co.a.support.EosDtm;
import nhnis.eos.co.a.support.EosIdGenerator;

@Service
public class eoscoa0130Service {

    private final eoscoa0130DAO dao;
    private final EosStatusEngine statusEngine;
    private final EosIdGenerator idGenerator;

    public eoscoa0130Service(eoscoa0130DAO dao, EosStatusEngine statusEngine, EosIdGenerator idGenerator) {
        this.dao = dao;
        this.statusEngine = statusEngine;
        this.idGenerator = idGenerator;
    }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public eoscoa0130S0DTOout eoscoa0130S0(eoscoa0130S0DTOin input) {
        eoscoa0130S0DTOout out = new eoscoa0130S0DTOout();
        String resourceId = trim(input == null ? null : input.getResourceId());
        if (resourceId == null) {
            out.setRSLT_CD("EOS-E0001");
            out.setRSLT_MSG("REQUIRED: resourceId");
            return out;
        }
        Map<String, Object> param = Map.of("resourceId", resourceId);
        Map<String, Object> row = dao.eoscoa0130S0_S0(param);
        if (row == null || row.isEmpty()) {
            out.setRSLT_CD("EOS-E0004");
            out.setRSLT_MSG("NOT_FOUND");
            return out;
        }
        Map<String, Object> detail = new LinkedHashMap<>();
        putCamel(detail, row, "RESOURCE_ID", "resourceId");
        putCamel(detail, row, "RESOURCE_NAME", "resourceName");
        putCamel(detail, row, "VERSION_ID", "versionId");
        putCamel(detail, row, "PRODUCT_ID", "productId");
        putCamel(detail, row, "PRODUCT_NAME", "productName");
        putCamel(detail, row, "RESOURCE_TYPE_CD", "resourceTypeCd");
        putCamel(detail, row, "VERSION_NO", "versionNo");
        putCamel(detail, row, "ENV_CD", "envCd");
        putCamel(detail, row, "CENTER_CD", "centerCd");
        putCamel(detail, row, "HOST_NAME", "hostName");
        putCamel(detail, row, "IP_ADDR", "ipAddr");
        putCamel(detail, row, "ORG_CD", "orgCd");
        putCamel(detail, row, "OWNER_USER_ID", "ownerUserId");
        putCamel(detail, row, "EOS_STATUS_CD", "eosStatusCd");
        putCamel(detail, row, "RISK_GRADE_CD", "riskGradeCd");
        putCamel(detail, row, "EXCEPTION_ACTIVE_YN", "exceptionActiveYn");
        putCamel(detail, row, "EOS_YMD", "eosYmd");
        putCamel(detail, row, "EOL_YMD", "eolYmd");
        putCamel(detail, row, "REMARK", "remark");
        String eosYmd = str(row, "EOS_YMD");
        detail.put("remainDays", statusEngine.remainDays(eosYmd));
        detail.put("riskHistCnt", dao.countRisk(param));
        detail.put("actionCnt", dao.countAction(param));
        detail.put("excCnt", dao.countException(param));
        out.setDetail(detail);
        out.setRSLT_CD("0000");
        out.setRSLT_MSG("OK");
        return out;
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public eoscoa0130C0DTOout eoscoa0130C0(eoscoa0130C0DTOin input) {
        eoscoa0130C0DTOout out = new eoscoa0130C0DTOout();
        String resourceName = trim(input == null ? null : input.getResourceName());
        String versionId = trim(input == null ? null : input.getVersionId());
        String envCd = trim(input == null ? null : input.getEnvCd());
        if (resourceName == null || versionId == null || envCd == null) {
            out.setRSLT_CD("EOS-E0001");
            out.setRSLT_MSG("REQUIRED: resourceName, versionId, envCd");
            return out;
        }
        if (dao.existsVersion(Map.of("versionId", versionId)) <= 0) {
            out.setRSLT_CD("EOS-E0004");
            out.setRSLT_MSG("VERSION_NOT_FOUND");
            return out;
        }
        Map<String, Object> lfc = dao.selectCurrentLfc(Map.of("versionId", versionId));
        String eosYmd = lfc == null ? null : str(lfc, "EOS_YMD");
        String eolYmd = lfc == null ? null : str(lfc, "EOL_YMD");
        int approaching = policyInt("EOS_THRESHOLD", "APPROACHING_DAYS", 365);
        int due = policyInt("EOS_THRESHOLD", "DUE_DAYS", 90);
        String status = statusEngine.resolveStatus(eosYmd, eolYmd, approaching, due);
        String resourceId = idGenerator.resourceId();
        String dtm = EosDtm.now();
        Map<String, Object> param = new HashMap<>();
        param.put("resourceId", resourceId);
        param.put("resourceName", resourceName);
        param.put("versionId", versionId);
        param.put("envCd", envCd);
        param.put("centerCd", empty(input.getCenterCd()));
        param.put("hostName", empty(input.getHostName()));
        param.put("ipAddr", empty(input.getIpAddr()));
        param.put("nsightAreaCd", empty(input.getNsightAreaCd()));
        param.put("orgCd", empty(input.getOrgCd()));
        param.put("ownerUserId", empty(input.getOwnerUserId()));
        param.put("eosStatusCd", status);
        param.put("remark", empty(input.getRemark()));
        param.put("regUserId", "LOCAL");
        param.put("regDtm", dtm);
        dao.eoscoa0130C0_C0(param);
        out.setResourceId(resourceId);
        out.setEosStatusCd(status);
        out.setRemainDays(statusEngine.remainDays(eosYmd));
        out.setRSLT_CD("0000");
        out.setRSLT_MSG("OK");
        return out;
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public eoscoa0130U0DTOout eoscoa0130U0(eoscoa0130U0DTOin input) {
        eoscoa0130U0DTOout out = new eoscoa0130U0DTOout();
        String resourceId = trim(input == null ? null : input.getResourceId());
        if (resourceId == null) {
            out.setRSLT_CD("EOS-E0001");
            out.setRSLT_MSG("REQUIRED: resourceId");
            return out;
        }
        Map<String, Object> existing = dao.eoscoa0130S0_S0(Map.of("resourceId", resourceId));
        if (existing == null || existing.isEmpty()) {
            out.setRSLT_CD("EOS-E0004");
            out.setRSLT_MSG("NOT_FOUND");
            return out;
        }
        if ("Y".equalsIgnoreCase(str(existing, "DISPOSE_YN"))) {
            out.setRSLT_CD("EOS-E0001");
            out.setRSLT_MSG("DISPOSED");
            return out;
        }
        String versionId = firstNonBlank(trim(input.getVersionId()), str(existing, "VERSION_ID"));
        if (dao.existsVersion(Map.of("versionId", versionId)) <= 0) {
            out.setRSLT_CD("EOS-E0004");
            out.setRSLT_MSG("VERSION_NOT_FOUND");
            return out;
        }
        Map<String, Object> lfc = dao.selectCurrentLfc(Map.of("versionId", versionId));
        String eosYmd = lfc == null ? null : str(lfc, "EOS_YMD");
        String eolYmd = lfc == null ? null : str(lfc, "EOL_YMD");
        String status = statusEngine.resolveStatus(eosYmd, eolYmd,
                policyInt("EOS_THRESHOLD", "APPROACHING_DAYS", 365),
                policyInt("EOS_THRESHOLD", "DUE_DAYS", 90));
        Map<String, Object> param = new HashMap<>();
        param.put("resourceId", resourceId);
        param.put("resourceName", firstNonBlank(trim(input.getResourceName()), str(existing, "RESOURCE_NAME")));
        param.put("versionId", versionId);
        param.put("envCd", firstNonBlank(trim(input.getEnvCd()), str(existing, "ENV_CD")));
        param.put("centerCd", coalesce(trim(input.getCenterCd()), str(existing, "CENTER_CD")));
        param.put("hostName", coalesce(trim(input.getHostName()), str(existing, "HOST_NAME")));
        param.put("ipAddr", coalesce(trim(input.getIpAddr()), str(existing, "IP_ADDR")));
        param.put("nsightAreaCd", coalesce(trim(input.getNsightAreaCd()), str(existing, "NSIGHT_AREA_CD")));
        param.put("orgCd", coalesce(trim(input.getOrgCd()), str(existing, "ORG_CD")));
        param.put("ownerUserId", coalesce(trim(input.getOwnerUserId()), str(existing, "OWNER_USER_ID")));
        param.put("eosStatusCd", status);
        param.put("remark", coalesce(trim(input.getRemark()), str(existing, "REMARK")));
        param.put("chgUserId", "LOCAL");
        param.put("chgDtm", EosDtm.now());
        int n = dao.eoscoa0130U0_U0(param);
        if (n <= 0) {
            out.setRSLT_CD("EOS-E0004");
            out.setRSLT_MSG("UPDATE_FAILED");
            return out;
        }
        out.setResourceId(resourceId);
        out.setEosStatusCd(status);
        out.setRemainDays(statusEngine.remainDays(eosYmd));
        out.setRSLT_CD("0000");
        out.setRSLT_MSG("OK");
        return out;
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public eoscoa0130D0DTOout eoscoa0130D0(eoscoa0130D0DTOin input) {
        eoscoa0130D0DTOout out = new eoscoa0130D0DTOout();
        String resourceId = trim(input == null ? null : input.getResourceId());
        String reason = trim(input == null ? null : input.getDisposeReason());
        if (resourceId == null || reason == null) {
            out.setRSLT_CD("EOS-E0001");
            out.setRSLT_MSG("REQUIRED: resourceId, disposeReason");
            return out;
        }
        Map<String, Object> param = Map.of("resourceId", resourceId);
        if (dao.eoscoa0130S0_S0(param) == null) {
            out.setRSLT_CD("EOS-E0004");
            out.setRSLT_MSG("NOT_FOUND");
            return out;
        }
        if (dao.countOpenAction(param) > 0 || dao.countActiveException(param) > 0) {
            out.setRSLT_CD("EOS-E0001");
            out.setRSLT_MSG("BLOCKED: open action or active exception");
            return out;
        }
        Map<String, Object> upd = new HashMap<>();
        upd.put("resourceId", resourceId);
        upd.put("remark", reason);
        upd.put("chgUserId", "LOCAL");
        upd.put("chgDtm", EosDtm.now());
        int n = dao.eoscoa0130D0_D0(upd);
        if (n <= 0) {
            out.setRSLT_CD("EOS-E0004");
            out.setRSLT_MSG("ALREADY_DISPOSED");
            return out;
        }
        out.setResourceId(resourceId);
        out.setRSLT_CD("0000");
        out.setRSLT_MSG("OK");
        return out;
    }

    private int policyInt(String type, String key, int def) {
        String v = dao.selectPolicyVal(Map.of("policyTypeCd", type, "policyKey", key));
        if (v == null || v.isBlank()) return def;
        try { return Integer.parseInt(v.trim()); } catch (NumberFormatException e) { return def; }
    }

    private static String trim(String v) { return v == null || v.isBlank() ? null : v.trim(); }
    private static String empty(String v) { return v == null ? "" : v.trim(); }
    private static String coalesce(String a, String b) { return a != null ? a : (b == null ? "" : b); }
    private static String firstNonBlank(String a, String b) { return a != null ? a : b; }

    private static String str(Map<String, Object> row, String key) {
        if (row == null) return null;
        Object v = row.get(key);
        if (v == null) {
            for (Map.Entry<String, Object> e : row.entrySet()) {
                if (e.getKey() != null && e.getKey().equalsIgnoreCase(key)) {
                    v = e.getValue();
                    break;
                }
            }
        }
        return v == null ? null : String.valueOf(v);
    }

    private static void putCamel(Map<String, Object> target, Map<String, Object> row, String col, String camel) {
        target.put(camel, str(row, col));
    }
}
