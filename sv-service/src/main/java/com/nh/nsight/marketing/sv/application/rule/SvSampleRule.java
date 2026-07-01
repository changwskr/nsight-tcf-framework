package com.nh.nsight.marketing.sv.application.rule;

import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.core.error.ErrorCode;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class SvSampleRule {
    public static final int DEFAULT_PAGE_NO = 1;
    public static final int DEFAULT_PAGE_SIZE = 100;
    public static final int MAX_PAGE_SIZE = 500;

    public void validateInquiry(Map<String, Object> body) {
        if (body == null) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "요청 Body가 없습니다.");
        }
    }

    public Map<String, Object> buildSearchCriteria(Map<String, Object> body) {
        Map<String, Object> criteria = new HashMap<>();
        normalizePaging(criteria, body, MAX_PAGE_SIZE);
        putTrimmed(criteria, "sampleKey", body.get("sampleKey"));
        return criteria;
    }

    private void normalizePaging(Map<String, Object> criteria, Map<String, Object> body, int maxPageSize) {
        int pageNo = Math.max(1, toInt(body.get("pageNo"), DEFAULT_PAGE_NO));
        int pageSize = toInt(body.get("pageSize"), DEFAULT_PAGE_SIZE);
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (pageSize > maxPageSize) {
            pageSize = maxPageSize;
        }
        criteria.put("pageNo", pageNo);
        criteria.put("pageSize", pageSize);
        criteria.put("offset", (pageNo - 1) * pageSize);
    }

    private void putTrimmed(Map<String, Object> target, String key, Object value) {
        if (value == null) {
            return;
        }
        String text = String.valueOf(value).trim();
        if (!text.isEmpty()) {
            target.put(key, text);
        }
    }

    private int toInt(Object value, int defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number number) {
            return number.intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(value).trim());
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }
}
