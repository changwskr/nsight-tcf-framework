package com.nh.nsight.marketing.sv.rule;

import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.core.error.ErrorCode;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class SvSampleRule {
    public void validateInquiry(Map<String, Object> body) {
        if (body == null || body.isEmpty()) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "요청 Body가 비어 있습니다.");
        }
    }
}
