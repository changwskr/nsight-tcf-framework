package com.nh.nsight.gateway.security;

import com.nh.nsight.gateway.config.GatewayProperties;
import com.nh.nsight.gateway.support.GatewayProxyTrace;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GatewaySessionValidator {
    private static final String PHASE = "GatewaySessionValidator.validate";

    private final GatewayProperties properties;

    public GatewaySessionValidator(GatewayProperties properties) {
        this.properties = properties;
    }

    /** JSESSIONID 쿠키로 로그인 여부 확인 */
    public void validate(String businessCode, String cookieHeader) {
        GatewayProxyTrace.start(PHASE);
        GatewayProxyTrace.log(PHASE, "businessCode=" + businessCode
                + " loginRequired=" + properties.getAuth().isLoginRequired());
        try {
            if (!properties.getAuth().isLoginRequired()) {
                GatewayProxyTrace.log(PHASE, "login check skipped");
                return;
            }
            GatewayProxyTrace.log(PHASE, "validateSessionCookie");
            if (!hasSessionCookie(cookieHeader)) {
                throw new GatewayAuthException(401, "로그인이 필요합니다.");
            }
            GatewayProxyTrace.log(PHASE, "JSESSIONID present");
        } finally {
            GatewayProxyTrace.end(PHASE);
        }
    }

    private boolean hasSessionCookie(String cookieHeader) {
        if (!StringUtils.hasText(cookieHeader)) {
            return false;
        }
        for (String part : cookieHeader.split(";")) {
            String trimmed = part.trim();
            if (trimmed.startsWith("JSESSIONID=") && trimmed.length() > "JSESSIONID=".length()) {
                return true;
            }
        }
        return false;
    }
}
