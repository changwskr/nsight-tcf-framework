package com.nh.nsight.gateway.application.service;

import com.nh.nsight.gateway.application.rule.GatewayAuthExemptions;
import com.nh.nsight.gateway.application.rule.GatewaySessionValidator;
import com.nh.nsight.gateway.config.GatewayProperties;
import com.nh.nsight.gateway.support.GatewayProxyTrace;
import com.nh.nsight.gateway.support.GatewayRequestUserReader;
import com.nh.nsight.gateway.support.GatewaySessionContext;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
public class GatewayAuthenticationService {
    private static final String PHASE = "GatewayAuthenticationService.authenticate";

    private final GatewayProperties properties;
    private final GatewaySessionValidator sessionValidator;
    private final ObjectProvider<GatewayJwtValidator> jwtValidatorProvider;
    private final GatewayRequestUserReader requestUserReader;

    public GatewayAuthenticationService(GatewayProperties properties,
            GatewaySessionValidator sessionValidator,
            ObjectProvider<GatewayJwtValidator> jwtValidatorProvider,
            GatewayRequestUserReader requestUserReader) {
        this.properties = properties;
        this.sessionValidator = sessionValidator;
        this.jwtValidatorProvider = jwtValidatorProvider;
        this.requestUserReader = requestUserReader;
    }

    public GatewaySessionContext authenticate(String businessCode,
            String cookieHeader,
            String authorizationHeader,
            String requestBody) {
        GatewayProxyTrace.start(PHASE);
        try {
            if (!properties.getAuth().isLoginRequired()) {
                GatewayProxyTrace.log(PHASE, "login check skipped");
                return null;
            }
            String serviceId = requestUserReader.serviceId(requestBody).orElse(null);
            if (GatewayAuthExemptions.isLoginExempt(serviceId)) {
                GatewayProxyTrace.log(PHASE, "login exempt serviceId=" + serviceId);
                return null;
            }
            GatewayJwtValidator jwtValidator = jwtValidatorProvider.getIfAvailable();
            if (properties.getAuth().getJwt().isEnabled()
                    && jwtValidator != null
                    && jwtValidator.hasBearerToken(authorizationHeader)) {
                GatewayProxyTrace.log(PHASE, "jwt validation");
                return jwtValidator.validate(authorizationHeader, requestBody);
            }
            GatewayProxyTrace.log(PHASE, "session validation");
            return sessionValidator.validate(businessCode, cookieHeader, requestBody);
        } finally {
            GatewayProxyTrace.end(PHASE);
        }
    }
}
