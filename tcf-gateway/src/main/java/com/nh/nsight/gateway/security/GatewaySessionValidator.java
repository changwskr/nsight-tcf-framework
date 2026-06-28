package com.nh.nsight.gateway.security;

import com.nh.nsight.gateway.session.model.GatewaySessionContext;
import com.nh.nsight.gateway.session.service.GatewaySessionValidationService;
import org.springframework.stereotype.Component;

@Component
public class GatewaySessionValidator {
    private final GatewaySessionValidationService validationService;

    public GatewaySessionValidator(GatewaySessionValidationService validationService) {
        this.validationService = validationService;
    }

    public GatewaySessionContext validate(String businessCode, String cookieHeader, String requestBody) {
        return validationService.validate(businessCode, cookieHeader, requestBody);
    }
}
