package com.nh.nsight.marketing.ep.application.rule;

import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.core.error.ErrorCode;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EpUserEventRule {
    public void validateReceive(Map<String, Object> body) {
        if (body == null) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "요청 Body가 없습니다.");
        }
        require(body, "eventId");
        require(body, "userId");
        require(body, "eventType");
    }

    private void require(Map<String, Object> body, String key) {
        Object value = body.get(key);
        if (!(value instanceof String text) || !StringUtils.hasText(text)) {
            throw new BusinessException(ErrorCode.BUSINESS_ERROR, "필수 필드 누락: " + key);
        }
    }
}
