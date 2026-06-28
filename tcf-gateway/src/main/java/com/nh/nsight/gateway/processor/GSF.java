package com.nh.nsight.gateway.processor;

import com.nh.nsight.gateway.catalog.GatewayBusinessModules;
import com.nh.nsight.gateway.catalog.GatewayBusinessModules.Module;
import com.nh.nsight.gateway.config.GatewayProperties;
import com.nh.nsight.gateway.route.GatewayRouteNotFoundException;
import com.nh.nsight.gateway.route.model.GatewayRoute;
import com.nh.nsight.gateway.route.service.GatewayRouteResolver;
import com.nh.nsight.gateway.security.GatewaySessionValidator;
import com.nh.nsight.gateway.session.model.GatewaySessionContext;
import com.nh.nsight.gateway.session.support.GatewaySessionRequestEnricher;
import com.nh.nsight.gateway.support.GatewayProxyTrace;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component
public class GSF {
    private static final String PHASE = "GSF.preProcess";

    private final GatewayProperties properties;
    private final GatewaySessionValidator sessionValidator;
    private final GatewaySessionRequestEnricher sessionRequestEnricher;
    private final GatewayRouteResolver routeResolver;

    public GSF(GatewayProperties properties, GatewaySessionValidator sessionValidator,
               GatewaySessionRequestEnricher sessionRequestEnricher,
               GatewayRouteResolver routeResolver) {
        this.properties = properties;
        this.sessionValidator = sessionValidator;
        this.sessionRequestEnricher = sessionRequestEnricher;
        this.routeResolver = routeResolver;
    }

    public RouteContext preProcess(String businessCode,
                                   String requestBody,
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
                    + " targetUrl=" + targetUrl
                    + " connectTimeoutMs=" + route.connectTimeoutMs()
                    + " readTimeoutMs=" + route.readTimeoutMs());

            GatewayProxyTrace.log(PHASE, "sessionValidator.validate");
            GatewaySessionContext sessionContext = sessionValidator.validate(businessCode, cookieHeader, requestBody);

            GatewayProxyTrace.log(PHASE, "targetUrl=" + targetUrl);
            GatewayProxyTrace.log(PHASE, "sessionRequestEnricher.enrich");
            String enrichedBody = sessionRequestEnricher.enrich(requestBody, sessionContext);
            GatewayProxyTrace.log(PHASE, "RouteContext");
            return new RouteContext(
                    module,
                    targetUrl,
                    enrichedBody,
                    System.currentTimeMillis(),
                    route.connectTimeoutMs(),
                    route.readTimeoutMs());
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
