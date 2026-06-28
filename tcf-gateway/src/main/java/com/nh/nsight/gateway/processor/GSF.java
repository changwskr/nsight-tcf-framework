package com.nh.nsight.gateway.processor;

import com.nh.nsight.gateway.catalog.GatewayBusinessModules;
import com.nh.nsight.gateway.catalog.GatewayBusinessModules.Module;
import com.nh.nsight.gateway.config.GatewayProperties;
import com.nh.nsight.gateway.route.GatewayRouteNotFoundException;
import com.nh.nsight.gateway.route.model.GatewayRoute;
import com.nh.nsight.gateway.route.service.GatewayRouteResolver;
import com.nh.nsight.gateway.security.GatewaySessionValidator;
import com.nh.nsight.gateway.support.GatewayProxyTrace;
import com.nh.nsight.gateway.support.GatewayRequestEnricher;
import java.util.Locale;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class GSF {
    private static final String PHASE = "GSF.preProcess";

    private final GatewayProperties properties;
    private final GatewaySessionValidator sessionValidator;
    private final GatewayRequestEnricher enricher;
    private final GatewayRouteResolver routeResolver;

    public GSF(GatewayProperties properties, GatewaySessionValidator sessionValidator,
               GatewayRequestEnricher enricher, GatewayRouteResolver routeResolver) {
        this.properties = properties;
        this.sessionValidator = sessionValidator;
        this.enricher = enricher;
        this.routeResolver = routeResolver;
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
            GatewayProxyTrace.log(PHASE, "routeResolver.resolve envCode=" + properties.getEnvCode());
            GatewayRoute route = routeResolver.resolve(businessCode)
                    .orElseThrow(() -> new GatewayRouteNotFoundException(
                            properties.getEnvCode(),
                            businessCode.toUpperCase(Locale.ROOT)));

            Module module = moduleFromRoute(route);
            String targetUrl = route.targetUrl();
            GatewayProxyTrace.log(PHASE, "routeTableHit routeId=" + route.routeId()
                    + " targetUrl=" + targetUrl);

            GatewayProxyTrace.log(PHASE, "sessionValidator.validate");
            sessionValidator.validate(businessCode, cookieHeader, requestBody);

            GatewayProxyTrace.log(PHASE, "targetUrl=" + targetUrl);
            GatewayProxyTrace.log(PHASE, "enricher.enrich");
            String enrichedBody = enricher.enrich(requestBody, jwt);
            GatewayProxyTrace.log(PHASE, "RouteContext");
            return new RouteContext(module, targetUrl, enrichedBody, System.currentTimeMillis());
        } finally {
            GatewayProxyTrace.end(PHASE);
        }
    }

    private Module moduleFromRoute(GatewayRoute route) {
        return GatewayBusinessModules.find(route.businessCode())
                .orElseGet(() -> new Module(
                        route.businessCode().toUpperCase(Locale.ROOT),
                        0,
                        route.contextPath() + route.onlinePath()));
    }
}
