package com.nh.nsight.tcf.core.security;

import com.nh.nsight.tcf.core.config.TcfProperties;
import com.nh.nsight.tcf.core.error.BusinessException;
import com.nh.nsight.tcf.core.error.ErrorCode;
import com.nh.nsight.tcf.core.message.StandardHeader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SessionValidator {
    private final TcfProperties properties;

    public SessionValidator(TcfProperties properties) {
        this.properties = properties;
    }

    public void validate(StandardHeader header) {
        if (!properties.isSessionValidationEnabled()) {
            return;
        }
        if (!StringUtils.hasText(header.getUserId())) {
            throw new BusinessException(ErrorCode.SESSION_INVALID, "로그인 세션이 유효하지 않습니다.");
        }
    }
}
