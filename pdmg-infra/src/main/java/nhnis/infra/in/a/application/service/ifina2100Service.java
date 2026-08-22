package nhnis.infra.in.a.application.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import nhnis.infra.in.a.application.support.AuthGuard;
import nhnis.infra.in.a.dto.ifina2100C0DTOin;
import nhnis.infra.in.a.dto.ifina2100C0DTOout;
import nhnis.infra.in.a.dto.ifina2100D0DTOin;
import nhnis.infra.in.a.dto.ifina2100D0DTOout;
import nhnis.infra.in.a.dto.ifina2100S0DTOSub0;
import nhnis.infra.in.a.dto.ifina2100S0DTOin;
import nhnis.infra.in.a.dto.ifina2100S0DTOout;
import nhnis.infra.in.a.dto.ifina2100U0DTOin;
import nhnis.infra.in.a.dto.ifina2100U0DTOout;
import nhnis.infra.in.a.persistence.dao.ifina2100DAO;

@Service
public class ifina2100Service {

    private static final Logger log = LoggerFactory.getLogger(ifina2100Service.class);

    private final ifina2100DAO ifina2100DAO;
    private final AuthGuard authGuard;

    public ifina2100Service(ifina2100DAO ifina2100DAO, AuthGuard authGuard) {
        this.ifina2100DAO = ifina2100DAO;
        this.authGuard = authGuard;
    }

    public ifina2100S0DTOout ifina2100S0(ifina2100S0DTOin input) throws Exception {
        Map<String, Object> param = new HashMap<>();
        if (input != null) {
            put(param, "keyword", input.getKeyword());
            put(param, "systemId", input.getSystemId());
            put(param, "systemName", input.getSystemName());
            put(param, "ownerOrg", input.getOwnerOrg());
            put(param, "statusCd", input.getStatusCd());
        }
        int pageNo = pageNo(input == null ? null : input.getPageNo());
        int pageSize = pageSize(input == null ? null : input.getPageSize());
        param.put("pageNo", pageNo);
        param.put("pageSize", pageSize);
        param.put("offset", (pageNo - 1) * pageSize);

        int total = ifina2100DAO.ifina2100S0_S0_count(param);
        List<Map<String, Object>> rows = ifina2100DAO.ifina2100S0_S0(param);
        ifina2100S0DTOout out = new ifina2100S0DTOout();
        if (rows != null) {
            for (Map<String, Object> row : rows) {
                ifina2100S0DTOSub0 sub = new ifina2100S0DTOSub0();
                sub.setSystemId(as(row, "SYSTEM_ID", "systemId"));
                sub.setSystemName(as(row, "SYSTEM_NAME", "systemName"));
                sub.setOwnerOrg(as(row, "OWNER_ORG", "ownerOrg"));
                sub.setStatusCd(as(row, "STATUS_CD", "statusCd"));
                sub.setRemark(as(row, "REMARK", "remark"));
                sub.setRegUserId(as(row, "REG_USER_ID", "regUserId"));
                sub.setRegDtm(as(row, "REG_DTM", "regDtm"));
                sub.setChgUserId(as(row, "CHG_USER_ID", "chgUserId"));
                sub.setChgDtm(as(row, "CHG_DTM", "chgDtm"));
                out.addifina2100S0DTOSub0(sub);
            }
        }
        out.setSize(out.sizeifina2100S0DTOSub0());
        out.setPageNo(pageNo);
        out.setPageSize(pageSize);
        out.setTotalCount(total);
        out.setTotalPages(pageSize <= 0 ? 0 : (int) ((total + pageSize - 1L) / pageSize));
        return out;
    }

    public ifina2100C0DTOout ifina2100C0(ifina2100C0DTOin input) throws Exception {
        ifina2100C0DTOout out = new ifina2100C0DTOout();
        if (authGuard.denyIfHard(out, "ifina2100C0")) return out;
        String systemId = trim(input == null ? null : input.getSystemId());
        String systemName = trim(input == null ? null : input.getSystemName());
        if (systemId == null || systemName == null) {
            out.setPROC_CNT(0);
            out.setRSLT_CD("0001");
            out.setRSLT_MSG("REQUIRED: systemId, systemName");
            return out;
        }
        Map<String, Object> exists = Map.of("systemId", systemId);
        if (ifina2100DAO.ifina2100S0_S0_exists(exists) > 0) {
            out.setPROC_CNT(0);
            out.setRSLT_CD("0002");
            out.setRSLT_MSG("DUPLICATE_SYSTEM_ID");
            return out;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("systemId", systemId);
        param.put("systemName", systemName);
        param.put("ownerOrg", empty(input.getOwnerOrg()));
        param.put("statusCd", blank(input.getStatusCd(), "DISCOVERED"));
        param.put("remark", empty(input.getRemark()));
        param.put("regUserId", blank(input.getRegUserId(), "LOCAL"));
        param.put("regDtm", now());
        int cnt = ifina2100DAO.ifina2100C0_C0(param);
        out.setPROC_CNT(cnt);
        out.setRSLT_CD("0000");
        out.setRSLT_MSG("OK");
        return out;
    }

    public ifina2100U0DTOout ifina2100U0(ifina2100U0DTOin input) throws Exception {
        ifina2100U0DTOout out = new ifina2100U0DTOout();
        if (authGuard.denyIfHard(out, "ifina2100U0")) return out;
        String systemId = trim(input == null ? null : input.getSystemId());
        String orgSystemId = trim(input == null ? null : input.getOrgSystemId());
        if (orgSystemId == null) {
            orgSystemId = systemId;
        }
        String systemName = trim(input == null ? null : input.getSystemName());
        if (systemId == null || systemName == null) {
            out.setPROC_CNT(0);
            out.setRSLT_CD("0001");
            out.setRSLT_MSG("REQUIRED: systemId, systemName");
            return out;
        }
        if (ifina2100DAO.ifina2100S0_S0_exists(Map.of("systemId", orgSystemId)) <= 0) {
            out.setPROC_CNT(0);
            out.setRSLT_CD("0003");
            out.setRSLT_MSG("NOT_FOUND");
            return out;
        }

        boolean rename = !orgSystemId.equals(systemId);
        if (rename && ifina2100DAO.ifina2100S0_S0_exists(Map.of("systemId", systemId)) > 0) {
            out.setPROC_CNT(0);
            out.setRSLT_CD("0002");
            out.setRSLT_MSG("DUPLICATE_SYSTEM_ID");
            return out;
        }

        Map<String, Object> param = new HashMap<>();
        param.put("systemName", systemName);
        param.put("ownerOrg", empty(input.getOwnerOrg()));
        param.put("statusCd", empty(input.getStatusCd()));
        param.put("remark", empty(input.getRemark()));
        param.put("chgUserId", blank(input.getChgUserId(), "LOCAL"));
        param.put("chgDtm", now());

        int cnt;
        if (rename) {
            param.put("orgSystemId", orgSystemId);
            param.put("newSystemId", systemId);
            // 자식/참조 먼저 갱신 후 마스터 PK 변경
            ifina2100DAO.ifina2100U0_cascadeSystemId(param);
            ifina2100DAO.ifina2100U0_cascadeGroup(param);
            ifina2100DAO.ifina2100U0_cascadeAsset(param);
            ifina2100DAO.ifina2100U0_cascadeApp(param);
            ifina2100DAO.ifina2100U0_cascadeDb(param);
            ifina2100DAO.ifina2100U0_cascadeMapSession(param);
            ifina2100DAO.ifina2100U0_cascadeTargetChecklist(param);
            ifina2100DAO.ifina2100U0_cascadeTargetChangeLog(param);
            ifina2100DAO.ifina2100U0_cascadeTargetEvidence(param);
            ifina2100DAO.ifina2100U0_cascadeTargetGate(param);
            ifina2100DAO.ifina2100U0_cascadeTargetAvail(param);
            ifina2100DAO.ifina2100U0_cascadeTargetCapacity(param);
            ifina2100DAO.ifina2100U0_cascadeTargetSecurity(param);
            ifina2100DAO.ifina2100U0_cascadeTargetCost(param);
            ifina2100DAO.ifina2100U0_cascadeTargetMigration(param);
            cnt = ifina2100DAO.ifina2100U0_renameSystem(param);
            log.info("SYSTEM_ID renamed {} -> {} (cnt={})", orgSystemId, systemId, cnt);
        } else {
            param.put("systemId", systemId);
            cnt = ifina2100DAO.ifina2100U0_U0(param);
        }
        out.setPROC_CNT(cnt);
        out.setRSLT_CD("0000");
        out.setRSLT_MSG(rename ? "OK_RENAMED" : "OK");
        return out;
    }

    public ifina2100D0DTOout ifina2100D0(ifina2100D0DTOin input) throws Exception {
        ifina2100D0DTOout out = new ifina2100D0DTOout();
        if (authGuard.denyIfHard(out, "ifina2100D0")) return out;
        if (input == null || input.getSystemIdList() == null || input.getSystemIdList().isEmpty()) {
            out.setPROC_CNT(0);
            out.setRSLT_CD("0001");
            out.setRSLT_MSG("NO_DATA");
            return out;
        }
        List<String> ids = input.getSystemIdList().stream()
                .filter(v -> v != null && !v.isBlank()).map(String::trim).distinct().toList();
        if (ids.isEmpty()) {
            out.setPROC_CNT(0);
            out.setRSLT_CD("0001");
            out.setRSLT_MSG("NO_DATA");
            return out;
        }
        for (String systemId : ids) {
            Map<String, Object> p = Map.of("systemId", systemId);
            // App 맵 → App → 세션 → 업무대그룹 정리, 서버군/자산은 연결만 해제
            ifina2100DAO.ifina2100D0_appMaps(p);
            ifina2100DAO.ifina2100D0_apps(p);
            ifina2100DAO.ifina2100D0_mapSessions(p);
            ifina2100DAO.ifina2100D0_details(p);
            ifina2100DAO.ifina2100D0_units(p);
            ifina2100DAO.ifina2100D0_domains(p);
            ifina2100DAO.ifina2100D0_unlinkGroup(p);
            ifina2100DAO.ifina2100D0_unlinkAsset(p);
            ifina2100DAO.ifina2100D0_unlinkDb(p);
            ifina2100DAO.ifina2100D0_targetChecklist(p);
            ifina2100DAO.ifina2100D0_targetGate(p);
            ifina2100DAO.ifina2100D0_targetCost(p);
            ifina2100DAO.ifina2100D0_targetMigration(p);
            ifina2100DAO.ifina2100D0_targetAvail(p);
            ifina2100DAO.ifina2100D0_targetCapacity(p);
            ifina2100DAO.ifina2100D0_targetSecurity(p);
        }
        int cnt = ifina2100DAO.ifina2100D0_D0(Map.of("systemIdList", ids));
        out.setPROC_CNT(cnt);
        out.setRSLT_CD("0000");
        out.setRSLT_MSG("OK");
        return out;
    }

    private static int pageNo(Integer v) { return v == null || v <= 0 ? 1 : v; }
    private static int pageSize(Integer v) {
        int size = v == null || v <= 0 ? 10 : v;
        return Math.min(size, 100);
    }
    private static String now() {
        return new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date());
    }
    private static String trim(String v) {
        if (v == null) return null;
        String t = v.trim();
        return t.isEmpty() ? null : t;
    }
    private static String empty(String v) { return v == null ? "" : v.trim(); }
    private static String blank(String v, String d) {
        String t = trim(v);
        return t != null ? t : d;
    }
    private static void put(Map<String, Object> m, String k, String v) {
        if (v != null && !v.isBlank()) m.put(k, v.trim());
    }
    private static String as(Map<String, Object> row, String u, String c) {
        if (row == null) return null;
        Object v = row.get(u);
        if (v == null) v = row.get(c);
        if (v == null) {
            for (Map.Entry<String, Object> e : row.entrySet()) {
                if (e.getKey() != null && (e.getKey().equalsIgnoreCase(u) || e.getKey().equalsIgnoreCase(c))) {
                    v = e.getValue();
                    break;
                }
            }
        }
        return v == null ? null : String.valueOf(v);
    }
}
