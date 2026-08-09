package nhnis.mg.co.a.application.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nhnis.fw.exception.BizException;
import nhnis.mg.co.a.dto.mgcoa9001C0DTOin;
import nhnis.mg.co.a.dto.mgcoa9001C0DTOout;
import nhnis.mg.co.a.dto.mgcoa9001D0DTOin;
import nhnis.mg.co.a.dto.mgcoa9001D0DTOout;
import nhnis.mg.co.a.dto.mgcoa9001S0DTOSub0;
import nhnis.mg.co.a.dto.mgcoa9001S0DTOin;
import nhnis.mg.co.a.dto.mgcoa9001S0DTOout;
import nhnis.mg.co.a.dto.mgcoa9001U0DTOin;
import nhnis.mg.co.a.dto.mgcoa9001U0DTOout;
import nhnis.mg.co.a.persistence.dao.mgcoa9001DAO;

/**
 * 거래통제(serviceId별) Service.
 */
@Service
public class mgcoa9001Service {

    private static final Logger log = LoggerFactory.getLogger(mgcoa9001Service.class);
    private static final Pattern YN = Pattern.compile("^[YN]$");
    private static final Pattern HHMMSS = Pattern.compile("^\\d{6}$");

    @Autowired
    private mgcoa9001DAO mgcoa9001DAO;

    @Transactional(transactionManager = "rdwTransactionManager", readOnly = true)
    public mgcoa9001S0DTOout mgcoa9001S0(mgcoa9001S0DTOin input) throws Exception {
        log.info("▶▶▶▶▶▶▶▶ mgcoa9001S0 Service Start!");

        Map<String, Object> param = new HashMap<>();
        if (input != null) {
            putIfHasText(param, "keyword", input.getKeyword());
            putIfHasText(param, "serviceId", input.getServiceId());
            putIfHasText(param, "useYn", normalizeYnOptional(input.getUseYn()));
            putIfHasText(param, "allowYn", normalizeYnOptional(input.getAllowYn()));
        }

        int pageNo = input == null || input.getPageNo() == null || input.getPageNo() <= 0
                ? 1 : input.getPageNo();
        int pageSize = input == null || input.getPageSize() == null || input.getPageSize() <= 0
                ? 20 : input.getPageSize();
        if (pageSize > 100) {
            pageSize = 100;
        }
        int offset = (pageNo - 1) * pageSize;
        param.put("pageNo", pageNo);
        param.put("pageSize", pageSize);
        param.put("offset", offset);

        int totalCount = mgcoa9001DAO.mgcoa9001S0_S0_count(param);
        List<Map<String, Object>> rows = mgcoa9001DAO.mgcoa9001S0_S0(param);

        mgcoa9001S0DTOout output = new mgcoa9001S0DTOout();
        if (rows != null) {
            for (Map<String, Object> row : rows) {
                mgcoa9001S0DTOSub0 sub = new mgcoa9001S0DTOSub0();
                sub.setServiceId(asString(row, "SERVICE_ID", "serviceId"));
                sub.setAllowYn(asString(row, "ALLOW_YN", "allowYn"));
                sub.setUseYn(asString(row, "USE_YN", "useYn"));
                sub.setAllowStartTm(asString(row, "ALLOW_START_TM", "allowStartTm"));
                sub.setAllowEndTm(asString(row, "ALLOW_END_TM", "allowEndTm"));
                sub.setAllowSysIds(asString(row, "ALLOW_SYS_IDS", "allowSysIds"));
                sub.setAllowBrcs(asString(row, "ALLOW_BRCS", "allowBrcs"));
                sub.setRegUserId(asString(row, "REG_USER_ID", "regUserId"));
                sub.setRegDtm(asString(row, "REG_DTM", "regDtm"));
                sub.setChgUserId(asString(row, "CHG_USER_ID", "chgUserId"));
                sub.setChgDtm(asString(row, "CHG_DTM", "chgDtm"));
                output.addRow(sub);
            }
        }
        output.setSize(output.getRows() == null ? 0 : output.getRows().size());
        output.setPageNo(pageNo);
        output.setPageSize(pageSize);
        output.setTotalCount(totalCount);
        output.setTotalPages(pageSize <= 0 ? 0 : (int) ((totalCount + pageSize - 1L) / pageSize));

        log.info("▶▶▶▶▶▶▶▶ mgcoa9001S0 Service End! - Total: " + totalCount);
        return output;
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public mgcoa9001C0DTOout mgcoa9001C0(mgcoa9001C0DTOin input) throws Exception {
        log.info("▶▶▶▶▶▶▶▶ mgcoa9001C0 Service Start!");

        String serviceId = requireText(input == null ? null : input.getServiceId(), "serviceId");
        String allowYn = requireYn(input == null ? null : input.getAllowYn(), "allowYn");
        String useYn = requireYn(input == null ? null : input.getUseYn(), "useYn");
        String startTm = optionalHHmmss(input == null ? null : input.getAllowStartTm(), "allowStartTm");
        String endTm = optionalHHmmss(input == null ? null : input.getAllowEndTm(), "allowEndTm");

        Map<String, Object> existsParam = new HashMap<>();
        existsParam.put("serviceId", serviceId);
        if (mgcoa9001DAO.mgcoa9001S0_S0_exists(existsParam) > 0) {
            throw new BizException("MP0409");
        }

        Map<String, Object> param = new HashMap<>();
        param.put("serviceId", serviceId);
        param.put("allowYn", allowYn);
        param.put("useYn", useYn);
        param.put("allowStartTm", startTm);
        param.put("allowEndTm", endTm);
        param.put("allowSysIds", trimToEmpty(input.getAllowSysIds()));
        param.put("allowBrcs", trimToEmpty(input.getAllowBrcs()));
        param.put("regUserId", firstNonBlank(input.getRegUserId(), "LOCAL"));
        param.put("regDtm", nowDtm());

        int cnt = mgcoa9001DAO.mgcoa9001C0_C0(param);
        mgcoa9001C0DTOout output = new mgcoa9001C0DTOout();
        output.setProcCnt(cnt);
        log.info("▶▶▶▶▶▶▶▶ mgcoa9001C0 Service End! - Total: " + cnt);
        return output;
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public mgcoa9001U0DTOout mgcoa9001U0(mgcoa9001U0DTOin input) throws Exception {
        log.info("▶▶▶▶▶▶▶▶ mgcoa9001U0 Service Start!");

        String serviceId = requireText(input == null ? null : input.getServiceId(), "serviceId");
        String allowYn = requireYn(input == null ? null : input.getAllowYn(), "allowYn");
        String useYn = requireYn(input == null ? null : input.getUseYn(), "useYn");
        String startTm = optionalHHmmss(input == null ? null : input.getAllowStartTm(), "allowStartTm");
        String endTm = optionalHHmmss(input == null ? null : input.getAllowEndTm(), "allowEndTm");

        Map<String, Object> existsParam = new HashMap<>();
        existsParam.put("serviceId", serviceId);
        if (mgcoa9001DAO.mgcoa9001S0_S0_exists(existsParam) <= 0) {
            throw new BizException("MP0404");
        }

        Map<String, Object> param = new HashMap<>();
        param.put("serviceId", serviceId);
        param.put("allowYn", allowYn);
        param.put("useYn", useYn);
        param.put("allowStartTm", startTm);
        param.put("allowEndTm", endTm);
        param.put("allowSysIds", trimToEmpty(input.getAllowSysIds()));
        param.put("allowBrcs", trimToEmpty(input.getAllowBrcs()));
        param.put("chgUserId", firstNonBlank(input.getChgUserId(), "LOCAL"));
        param.put("chgDtm", nowDtm());

        int cnt = mgcoa9001DAO.mgcoa9001U0_U0(param);
        if (cnt <= 0) {
            throw new BizException("MP0404");
        }
        mgcoa9001U0DTOout output = new mgcoa9001U0DTOout();
        output.setProcCnt(cnt);
        log.info("▶▶▶▶▶▶▶▶ mgcoa9001U0 Service End! - Total: " + cnt);
        return output;
    }

    @Transactional(transactionManager = "rdwTransactionManager", rollbackFor = Exception.class)
    public mgcoa9001D0DTOout mgcoa9001D0(mgcoa9001D0DTOin input) throws Exception {
        log.info("▶▶▶▶▶▶▶▶ mgcoa9001D0 Service Start!");

        if (input == null || input.getServiceIdList() == null || input.getServiceIdList().isEmpty()) {
            throw new BizException("MP0401");
        }
        List<String> ids = input.getServiceIdList().stream()
                .filter(v -> v != null && !v.isBlank())
                .map(String::trim)
                .distinct()
                .toList();
        if (ids.isEmpty()) {
            throw new BizException("MP0401");
        }

        Map<String, Object> param = new HashMap<>();
        param.put("serviceIdList", ids);
        int cnt = mgcoa9001DAO.mgcoa9001D0_D0(param);
        mgcoa9001D0DTOout output = new mgcoa9001D0DTOout();
        output.setProcCnt(cnt);
        log.info("▶▶▶▶▶▶▶▶ mgcoa9001D0 Service End! - Total: " + cnt);
        return output;
    }

    private String requireText(String value, String field) {
        String trimmed = trimToNull(value);
        if (trimmed == null) {
            throw new BizException("MP0401");
        }
        return trimmed;
    }

    private String requireYn(String value, String field) {
        String trimmed = trimToNull(value);
        if (trimmed == null) {
            throw new BizException("MP0401");
        }
        String upper = trimmed.toUpperCase(Locale.ROOT);
        if (!YN.matcher(upper).matches()) {
            throw new BizException("MP0403");
        }
        return upper;
    }

    private String normalizeYnOptional(String value) {
        String trimmed = trimToNull(value);
        if (trimmed == null) {
            return null;
        }
        String upper = trimmed.toUpperCase(Locale.ROOT);
        if (!YN.matcher(upper).matches()) {
            throw new BizException("MP0403");
        }
        return upper;
    }

    private String optionalHHmmss(String value, String field) {
        String trimmed = trimToNull(value);
        if (trimmed == null) {
            return null;
        }
        if (!HHMMSS.matcher(trimmed).matches()) {
            throw new BizException("MP0403");
        }
        return trimmed;
    }

    private String nowDtm() {
        return new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(new Date());
    }

    private String firstNonBlank(String primary, String fallback) {
        String value = trimToNull(primary);
        return value != null ? value : fallback;
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String trimToEmpty(String value) {
        return value == null ? "" : value.trim();
    }

    private void putIfHasText(Map<String, Object> param, String key, String value) {
        if (value != null && !value.isBlank()) {
            param.put(key, value.trim());
        }
    }

    private String asString(Map<String, Object> row, String upperKey, String camelKey) {
        if (row == null) {
            return null;
        }
        Object value = row.get(upperKey);
        if (value == null) {
            value = row.get(camelKey);
        }
        if (value == null) {
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                if (entry.getKey() != null
                        && (entry.getKey().equalsIgnoreCase(upperKey)
                        || entry.getKey().equalsIgnoreCase(camelKey))
                        && entry.getValue() != null) {
                    value = entry.getValue();
                    break;
                }
            }
        }
        return value == null ? null : String.valueOf(value);
    }
}
