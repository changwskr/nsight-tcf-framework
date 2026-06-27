package com.nh.nsight.gateway.processor;

import com.nh.nsight.gateway.catalog.GatewayBusinessModules;
import com.nh.nsight.gateway.catalog.GatewayBusinessModules.Module;
import com.nh.nsight.gateway.config.GatewayProperties;
import com.nh.nsight.gateway.security.GatewaySessionValidator;
import com.nh.nsight.gateway.support.GatewayProxyTrace;
import com.nh.nsight.gateway.support.GatewayRequestEnricher;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GSF {
    private static final String PHASE = "GSF.preProcess";

    private final GatewayProperties properties;
    private final GatewaySessionValidator sessionValidator;
    private final GatewayRequestEnricher enricher;

    public GSF(GatewayProperties properties, GatewaySessionValidator sessionValidator,
               GatewayRequestEnricher enricher) {
        this.properties = properties;
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
            sessionValidator.validate(businessCode, cookieHeader, requestBody);

            GatewayProxyTrace.log(PHASE, "resolveOnlineUrl");
            String mode = effectiveDeploymentMode(deploymentMode);
            String host = effectiveBootrunHost(bootrunHost);
            String tomcatBase = effectiveTomcatBaseUrl(tomcatGatewayUrl);
            GatewayProxyTrace.log(PHASE, "routing deploymentMode=" + mode
                    + " bootrunHost=" + host
                    + " tomcatBaseUrl=" + tomcatBase
                    + " ignoreRequestParams=" + properties.getRouting().isIgnoreRequestParams());
            String targetUrl = resolveOnlineUrl(module, mode, host, tomcatBase);
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
        if ("tomcat".equalsIgnoreCase(deploymentMode)) {
            String base = StringUtils.hasText(tomcatGatewayUrl) ? tomcatGatewayUrl : "http://localhost:8080";
            if ("JWT".equals(module.code())) {
                return trimTrailingSlash(base) + "/jwt/online";
            }
            return trimTrailingSlash(base) + module.tomcatOnlinePath();
        }
        if (StringUtils.hasText(bootrunHost)) {
            return trimTrailingSlash(bootrunHost) + ":" + module.bootrunPort() + module.tomcatOnlinePath();
        }
        return module.defaultBootrunOnlineUrl();
    }

    private String effectiveDeploymentMode(String deploymentMode) {
        if (!properties.getRouting().isIgnoreRequestParams() && StringUtils.hasText(deploymentMode)) {
            return deploymentMode;
        }
        return properties.getRouting().getDeploymentMode().name();
    }

    private String effectiveBootrunHost(String bootrunHost) {
        if (!properties.getRouting().isIgnoreRequestParams() && StringUtils.hasText(bootrunHost)) {
            return bootrunHost;
        }
        return properties.getRouting().getBootrunHost();
    }

    private String effectiveTomcatBaseUrl(String tomcatGatewayUrl) {
        if (!properties.getRouting().isIgnoreRequestParams() && StringUtils.hasText(tomcatGatewayUrl)) {
            return tomcatGatewayUrl;
        }
        return properties.getRouting().getTomcatBaseUrl();
    }

    private String trimTrailingSlash(String value) {
        if (!StringUtils.hasText(value)) {
            return "http://127.0.0.1";
        }
        return value.endsWith("/") ? value.substring(0, value.length() - 1) : value;
    }
}
