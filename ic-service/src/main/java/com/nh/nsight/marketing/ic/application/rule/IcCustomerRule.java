package com.nh.nsight.marketing.ic.application.rule;

import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.core.error.ErrorCode;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * IC 고객 조회 업무 규칙. 순수 입력 검증만 담당(외부 호출 금지).
 */
@Component
public class IcCustomerRule {

    public String validateInquiry(Map<String, Object> body) {
        if (body == null || body.isEmpty()) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "요청 Body가 비어 있습니다.");
        }
        Object raw = body.get("customerNo");
        String customerNo = raw == null ? "" : String.valueOf(raw).trim();
        if (customerNo.isEmpty()) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "customerNo는 필수입니다.");
        }
        return customerNo;
    }
}
