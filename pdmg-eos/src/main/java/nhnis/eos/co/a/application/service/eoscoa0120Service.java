package nhnis.eos.co.a.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.eos.co.a.domain.EosStatusEngine;
import nhnis.eos.co.a.dto.eoscoa0120S0DTOin;
import nhnis.eos.co.a.dto.eoscoa0120S0DTOout;
import nhnis.eos.co.a.persistence.dao.eoscoa0120DAO;

@Service
public class eoscoa0120Service {

    private final eoscoa0120DAO dao;
    private final EosStatusEngine statusEngine;

    public eoscoa0120Service(eoscoa0120DAO dao, EosStatusEngine statusEngine) {
        this.dao = dao;
        this.statusEngine = statusEngine;
    }

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public eoscoa0120S0DTOout eoscoa0120S0(eoscoa0120S0DTOin input) {
        Map<String, Object> param = new HashMap<>();
        if (input != null) {
            put(param, "resourceName", input.getResourceName());
            put(param, "resourceTypeCd", input.getResourceTypeCd());
            put(param, "envCd", input.getEnvCd());
            put(param, "orgCd", input.getOrgCd());
            put(param, "eosStatusCd", input.getEosStatusCd());
            put(param, "riskGradeCd", input.getRiskGradeCd());
            put(param, "exceptionActiveYn", input.getExceptionActiveYn());
            put(param, "productId", input.getProductId());
            put(param, "versionId", input.getVersionId());
        }

        int pageNo = input == null || input.getPageNo() == null || input.getPageNo() <= 0 ? 1 : input.getPageNo();
        int pageSize = input == null || input.getPageSize() == null || input.getPageSize() <= 0 ? 20 : input.getPageSize();
        if (pageSize > 100) {
            pageSize = 100;
        }
        int offset = (pageNo - 1) * pageSize;
        param.put("pageNo", pageNo);
        param.put("pageSize", pageSize);
        param.put("offset", offset);

        int totalCount = dao.eoscoa0120S0_S0_count(param);
        List<Map<String, Object>> rows = dao.eoscoa0120S0_S0(param);

        List<Map<String, Object>> list = new ArrayList<>();
        if (rows != null) {
            for (Map<String, Object> row : rows) {
                Map<String, Object> item = new LinkedHashMap<>();
                item.put("resourceId", str(row, "RESOURCE_ID", "resourceId"));
                item.put("resourceName", str(row, "RESOURCE_NAME", "resourceName"));
                item.put("productName", str(row, "PRODUCT_NAME", "productName"));
                item.put("resourceTypeCd", str(row, "RESOURCE_TYPE_CD", "resourceTypeCd"));
                item.put("versionNo", str(row, "VERSION_NO", "versionNo"));
                item.put("envCd", str(row, "ENV_CD", "envCd"));
                item.put("orgCd", str(row, "ORG_CD", "orgCd"));
                item.put("eosStatusCd", str(row, "EOS_STATUS_CD", "eosStatusCd"));
                item.put("riskGradeCd", str(row, "RISK_GRADE_CD", "riskGradeCd"));
                item.put("exceptionActiveYn", str(row, "EXCEPTION_ACTIVE_YN", "exceptionActiveYn"));
                item.put("ownerUserId", str(row, "OWNER_USER_ID", "ownerUserId"));
                String eosYmd = str(row, "EOS_YMD", "eosYmd");
                item.put("remainDays", statusEngine.remainDays(eosYmd));
                list.add(item);
            }
        }

        eoscoa0120S0DTOout out = new eoscoa0120S0DTOout();
        out.setList(list);
        out.setSize(list.size());
        out.setPageNo(pageNo);
        out.setPageSize(pageSize);
        out.setTotalCount(totalCount);
        out.setTotalPages(pageSize <= 0 ? 0 : (int) ((totalCount + pageSize - 1L) / pageSize));
        out.setRSLT_CD("0000");
        out.setRSLT_MSG("OK");
        return out;
    }

    private static void put(Map<String, Object> param, String key, String value) {
        if (value != null && !value.isBlank()) {
            param.put(key, value.trim());
        }
    }

    private static String str(Map<String, Object> row, String... keys) {
        for (String key : keys) {
            Object v = row.get(key);
            if (v == null) {
                for (Map.Entry<String, Object> e : row.entrySet()) {
                    if (e.getKey() != null && e.getKey().equalsIgnoreCase(key)) {
                        v = e.getValue();
                        break;
                    }
                }
            }
            if (v != null) {
                return String.valueOf(v);
            }
        }
        return null;
    }
}
