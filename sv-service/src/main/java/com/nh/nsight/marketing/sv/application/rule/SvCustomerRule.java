package com.nh.nsight.marketing.sv.application.rule;

import com.nh.nsight.tcf.core.error.BusinessException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SvCustomerRule {
    private static final int MAX_CUSTOMER_NO_LENGTH = 20;

    public Map<String, Object> buildSummaryCriteria(Map<String, Object> body) {
        if (body == null) {
            throw new BusinessException("E-SV-VAL-0001", "고객번호는 필수입니다.");
        }
        String customerNo = stringValue(body.get("customerNo"));
        if (!StringUtils.hasText(customerNo)) {
            throw new BusinessException("E-SV-VAL-0001", "고객번호는 필수입니다.");
        }
        if (customerNo.length() > MAX_CUSTOMER_NO_LENGTH) {
            throw new BusinessException("E-SV-VAL-0002", "고객번호 길이가 올바르지 않습니다.");
        }

        Map<String, Object> criteria = new HashMap<>();
        criteria.put("customerNo", customerNo);
        String baseDate = stringValue(body.get("baseDate"));
        if (StringUtils.hasText(baseDate)) {
            criteria.put("baseDate", baseDate);
        }
        return criteria;
    }

    public void validateSummaryResult(Map<String, Object> customer) {
        if (customer == null || customer.isEmpty()) {
            throw new BusinessException("E-SV-BIZ-0001", "조회된 고객 정보가 없습니다.");
        }
    }

    private String stringValue(Object value) {
        return value == null ? null : String.valueOf(value).trim();
    }
}
