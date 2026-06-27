package com.nh.nsight.gateway.processor;

import com.nh.nsight.gateway.catalog.GatewayBusinessModules;
import com.nh.nsight.gateway.catalog.GatewayBusinessModules.Module;
import com.nh.nsight.gateway.security.GatewaySessionValidator;
import com.nh.nsight.gateway.support.GatewayProxyTrace;
import com.nh.nsight.gateway.support.GatewayRequestEnricher;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GSF {
    private static final String PHASE = "GSF.preProcess";

    private final GatewaySessionValidator sessionValidator;
    private final GatewayRequestEnricher enricher;

    public GSF(GatewaySessionValidator sessionValidator, GatewayRequestEnricher enricher) {
        this.sessionValidator = sessionValidator;
        this.enricher = enricher;
    }

    public RouteContext preProcess(String businessCode,
                                   String requestBody,
                                   Jwt jwt,
                                   String cookieHeader,
                                   String deploymentMode,
                                   String bootrunHost,
                                   String tomcatGatewayUrl) {
        GatewayProxyTrace.start(PHASE);
        try {
            GatewayProxyTrace.log(PHASE, "businessCode=" + businessCode);
            GatewayProxyTrace.log(PHASE, "GatewayBusinessModules.require");
            Module module = GatewayBusinessModules.require(businessCode);

            GatewayProxyTrace.log(PHASE, "sessionValidator.validate");
            sessionValidator.validate(businessCode, cookieHeader);

            GatewayProxyTrace.log(PHASE, "resolveOnlineUrl");
            String targetUrl = resolveOnlineUrl(module, deploymentMode, bootrunHost, tomcatGatewayUrl);
            GatewayProxyTrace.log(PHASE, "targetUrl=" + targetUrl);
            GatewayProxyTrace.log(PHASE, "enricher.enrich");
            String enrichedBody = enricher.enrich(requestBody, jwt);
            GatewayProxyTrace.log(PHASE, "RouteContext");
            return new RouteContext(module, targetUrl, enrichedBody, System.currentTimeMillis());
        } finally {
            GatewayProxyTrace.end(PHASE);
        }
    }

    private String resolveOnlineUrl(Module module, String deploymentMode, String bootrunHost, String tomcatGatewayUrl) {
        if ("JWT".equals(module.code())) {
            if ("tomcat".equalsIgnoreCase(deploymentMode)) {
                String base = StringUtils.hasText(tomcatGatewayUrl) ? tomcatGatewayUrl : "http://localhost:8080";
                return trimTrailingSlash(base) + "/jwt/online";
            }
            if (StringUtils.hasText(bootrunHost)) {
                return trimTrailingSlash(bootrunHost) + ":8100/online";
            }
            return "http://127.0.0.1:8100/online";
        }
        if ("tomcat".equalsIgnoreCase(deploymentMode)) {
            String base = StringUtils.hasText(tomcatGatewayUrl) ? tomcatGatewayUrl : "http://localhost:8080";
            return trimTrailingSlash(base) + module.tomcatOnlinePath();
        }
        if (StringUtils.hasText(bootrunHost)) {
            return trimTrailingSlash(bootrunHost) + ":" + module.bootrunPort() + module.tomcatOnlinePath();
        }
        return module.defaultBootrunOnlineUrl();
    }

    private String trimTrailingSlash(String value) {
        if (!StringUtils.hasText(value)) {
            return "http://127.0.0.1";
        }
        return value.endsWith("/") ? value.substring(0, value.length() - 1) : value;
    }
}
