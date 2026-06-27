package com.nh.nsight.gateway.web;

import com.nh.nsight.gateway.service.BusinessRouteService;
import com.nh.nsight.gateway.service.RouteResult;
import com.nh.nsight.gateway.support.GatewayProxyTrace;
import com.nh.nsight.gateway.web.support.ProxyResponseSupport;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class AbstractBusinessProxyController {
    private final BusinessRouteService routeService;
    private final String businessCode;

    protected AbstractBusinessProxyController(BusinessRouteService routeService, String businessCode) {
        this.routeService = routeService;
        this.businessCode = businessCode;
    }

    protected String businessCode() {
        return businessCode;
    }

    @PostMapping("/online")
    public ResponseEntity<String> proxyOnline(
            @RequestBody String requestBody,
            @AuthenticationPrincipal Jwt jwt,
            HttpServletRequest request,
            @RequestParam(value = "deploymentMode", required = false) String deploymentMode,
            @RequestParam(value = "bootrunHost", required = false) String bootrunHost,
            @RequestParam(value = "tomcatGatewayUrl", required = false) String tomcatGatewayUrl) {
        String phase = "Gateway." + businessCode + ".proxyOnline";
        GatewayProxyTrace.start(phase);
        GatewayProxyTrace.log(phase, "deploymentMode=" + deploymentMode
                + " bootrunHost=" + bootrunHost
                + " tomcatGatewayUrl=" + tomcatGatewayUrl);
        GatewayProxyTrace.log(phase, "GRF.forwardOnline");
        RouteResult result = routeService.forwardOnline(
                businessCode,
                requestBody,
                jwt,
                request.getHeader("Cookie"),
                deploymentMode,
                bootrunHost,
                tomcatGatewayUrl
        );
        GatewayProxyTrace.log(phase, "targetUrl=" + result.targetUrl()
                + " httpStatus=" + result.httpStatus()
                + " elapsedMs=" + result.elapsedMs());
        GatewayProxyTrace.log(phase, "ProxyResponseSupport.toResponse");
        ResponseEntity<String> response = ProxyResponseSupport.toResponse(result);
        GatewayProxyTrace.end(phase);
        return response;
    }
}
