package com.nh.nsight.gateway.processor;

import com.nh.nsight.gateway.catalog.GatewayBusinessModules.Module;
import com.nh.nsight.gateway.support.GatewayProxyTrace;

public record RouteContext(
        Module module,
        String targetUrl,
        String enrichedBody,
        long startedAtMillis,
        int connectTimeoutMs,
        int readTimeoutMs
) {
    private static final String PHASE = "RouteContext";

    public RouteContext {
        GatewayProxyTrace.start(PHASE);
        GatewayProxyTrace.log(PHASE, "module=" + module.code()
                + " targetUrl=" + targetUrl
                + " connectTimeoutMs=" + connectTimeoutMs
                + " readTimeoutMs=" + readTimeoutMs
                + " startedAtMillis=" + startedAtMillis);
        GatewayProxyTrace.end(PHASE);
    }
}
