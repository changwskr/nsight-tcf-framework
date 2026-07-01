package com.nh.nsight.marketing.ep.application.rule;

import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.core.error.ErrorCode;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EpUserEventRule {
    public static final int DEFAULT_PAGE_NO = 1;
    public static final int DEFAULT_PAGE_SIZE = 100;
    public static final int MAX_PAGE_SIZE_LOG = 1000;

    public void validateReceive(Map<String, Object> body) {
        if (body == null) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "요청 Body가 없습니다.");
        }
        require(body, "eventId");
        require(body, "userId");
        require(body, "eventType");
    }

    public void validateInquiry(Map<String, Object> body) {
        // 목록 조회 — body·검색조건 모두 선택 (pageNo/pageSize만으로도 조회 가능)
    }

    public Map<String, Object> buildSearchCriteria(Map<String, Object> body) {
        Map<String, Object> safeBody = body != null ? body : Map.of();
        Map<String, Object> criteria = new HashMap<>();
        normalizePaging(criteria, safeBody, MAX_PAGE_SIZE_LOG);
        putTrimmed(criteria, "eventId", safeBody.get("eventId"));
        putTrimmed(criteria, "userId", safeBody.get("userId"));
        putTrimmed(criteria, "eventType", safeBody.get("eventType"));
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

    private void require(Map<String, Object> body, String key) {
        Object value = body.get(key);
        if (!(value instanceof String text) || !StringUtils.hasText(text)) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "필수 필드 누락: " + key);
        }
    }
}
