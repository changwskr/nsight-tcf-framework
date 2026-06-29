package com.nh.nsight.marketing.eb.application.rule;

import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.core.error.ErrorCode;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EbUserRule {
    private static final int MAX_PAGE_SIZE = 100;

    public void validateInquiry(Map<String, Object> body) {
        if (body == null) {
            return;
        }
        int pageSize = parsePositiveInt(body.get("pageSize"), 20);
        if (pageSize > MAX_PAGE_SIZE) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "pageSize는 최대 " + MAX_PAGE_SIZE + " 입니다.");
        }
        int pageNo = parsePositiveInt(body.get("pageNo"), 1);
        if (pageNo < 1) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "pageNo는 1 이상이어야 합니다.");
        }
    }

    public void validateCreate(Map<String, Object> body) {
        if (body == null) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "요청 Body가 없습니다.");
        }
        require(body, "userId");
        require(body, "userName");
    }

    private void require(Map<String, Object> body, String key) {
        Object value = body.get(key);
        if (!(value instanceof String text) || !StringUtils.hasText(text)) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "필수 필드 누락: " + key);
        }
    }

    private int parsePositiveInt(Object value, int defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number number) {
            return number.intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(value).trim());
        } catch (NumberFormatException ex) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "숫자 형식이 올바르지 않습니다: " + value);
        }
    }
}
