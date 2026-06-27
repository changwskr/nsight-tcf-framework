package com.nh.nsight.gateway.service;

import com.nh.nsight.gateway.processor.GRF;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class BusinessRouteService {
    private final GRF grf;

    public BusinessRouteService(GRF grf) {
        this.grf = grf;
    }

    public RouteResult forwardOnline(String businessCode,
                                     String requestBody,
                                     Jwt jwt,
                                     String cookieHeader,
                                     String deploymentMode,
                                     String bootrunHost,
                                     String tomcatGatewayUrl) {
        return grf.forwardOnline(
                businessCode,
                requestBody,
                jwt,
                cookieHeader,
                deploymentMode,
                bootrunHost,
                tomcatGatewayUrl
        );
    }
}
