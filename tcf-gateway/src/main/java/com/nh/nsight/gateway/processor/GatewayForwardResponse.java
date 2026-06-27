package com.nh.nsight.gateway.processor;

import com.nh.nsight.gateway.support.GatewayProxyTrace;
import java.util.List;

public record GatewayForwardResponse(
        int httpStatus,
        long elapsedMs,
        String responseBody,
        List<String> setCookies
) {
    private static final String PHASE = "GatewayForwardResponse";

    public GatewayForwardResponse {
        GatewayProxyTrace.start(PHASE);
        GatewayProxyTrace.log(PHASE, "httpStatus=" + httpStatus
                + " elapsedMs=" + elapsedMs
                + " setCookies=" + (setCookies == null ? 0 : setCookies.size()));
        GatewayProxyTrace.end(PHASE);
    }
}
