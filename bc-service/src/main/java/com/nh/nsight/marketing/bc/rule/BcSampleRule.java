package com.nh.nsight.marketing.bc.rule;

import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.core.error.ErrorCode;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class BcSampleRule {
    public void validateInquiry(Map<String, Object> body) {
        System.out.println("\n ==============================================[BcSampleRule.validateInquiry] start");
        System.out.println(" ==============================================[BcSampleRule.validateInquiry] body=" + body);
        System.out.println(" ==============================================[BcSampleRule.validateInquiry] sampleKey="
                + (body != null ? body.get("sampleKey") : null));

        if (body == null || body.isEmpty()) {
            System.out.println(" ==============================================[BcSampleRule.validateInquiry] validation=FAIL (empty body)");
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "요청 Body가 비어 있습니다.");
        }

        System.out.println(" ==============================================[BcSampleRule.validateInquiry] validation=OK");
        System.out.println(" ==============================================[BcSampleRule.validateInquiry] end");
    }
}
