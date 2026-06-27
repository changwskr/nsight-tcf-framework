package com.nh.nsight.gateway.security;

import com.nh.nsight.gateway.config.GatewayProperties;
import com.nh.nsight.gateway.support.GatewayLoginSessionSupport;
import com.nh.nsight.gateway.support.GatewayProxyTrace;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GatewaySessionValidator {
    private static final String PHASE = "GatewaySessionValidator.validate";

    private final GatewayProperties properties;
    private final GatewayLoginSessionSupport loginSessionSupport;

    public GatewaySessionValidator(GatewayProperties properties, GatewayLoginSessionSupport loginSessionSupport) {
        this.properties = properties;
        this.loginSessionSupport = loginSessionSupport;
    }

    /** JSESSIONID 또는 gateway 로그인 세션으로 인증 확인 */
    public void validate(String businessCode, String cookieHeader, String requestBody) {
        GatewayProxyTrace.start(PHASE);
        GatewayProxyTrace.log(PHASE, "businessCode=" + businessCode
                + " loginRequired=" + properties.getAuth().isLoginRequired());
        try {
            if (!properties.getAuth().isLoginRequired()) {
                GatewayProxyTrace.log(PHASE, "login check skipped");
                return;
            }
            String serviceId = loginSessionSupport.extractServiceId(requestBody);
            if (GatewayAuthExemptions.isLoginExempt(serviceId)) {
                GatewayProxyTrace.log(PHASE, "login exempt serviceId=" + serviceId);
                return;
            }
            GatewayProxyTrace.log(PHASE, "validateSession");
            if (hasSessionCookie(cookieHeader)) {
                GatewayProxyTrace.log(PHASE, "JSESSIONID present");
                return;
            }
            if (loginSessionSupport.currentUser().isPresent()) {
                GatewayProxyTrace.log(PHASE, "gateway session present userId="
                        + loginSessionSupport.currentUser().get().userId());
                return;
            }
            throw new GatewayAuthException(401, "로그인이 필요합니다.");
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
