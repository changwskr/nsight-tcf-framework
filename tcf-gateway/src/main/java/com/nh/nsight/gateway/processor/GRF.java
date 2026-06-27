package com.nh.nsight.gateway.processor;

import com.nh.nsight.gateway.security.GatewayAuthException;
import com.nh.nsight.gateway.service.RouteResult;
import com.nh.nsight.gateway.support.GatewayProxyTrace;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

@Component
public class GRF {
    private static final String PHASE = "GRF.forwardOnline";

    private final GSF gsf;
    private final GatewayRouteDispatcher dispatcher;
    private final GEF gef;

    public GRF(GSF gsf, GatewayRouteDispatcher dispatcher, GEF gef) {
        this.gsf = gsf;
        this.dispatcher = dispatcher;
        this.gef = gef;
    }

    public RouteResult forwardOnline(String businessCode,
                                     String requestBody,
                                     Jwt jwt,
                                     String cookieHeader,
                                     String deploymentMode,
                                     String bootrunHost,
                                     String tomcatGatewayUrl) {
        GatewayProxyTrace.start(PHASE);
        RouteContext context = null;
        try {
            GatewayProxyTrace.log(PHASE, "GSF.preProcess START");
            context = gsf.preProcess(
                    businessCode, requestBody, jwt, cookieHeader, deploymentMode, bootrunHost, tomcatGatewayUrl);
            GatewayProxyTrace.log(PHASE, "GSF.preProcess END");

            GatewayProxyTrace.log(PHASE, "GatewayRouteDispatcher.dispatch START");
            GatewayForwardResponse response = dispatcher.dispatch(context, cookieHeader);
            GatewayProxyTrace.log(PHASE, "GatewayRouteDispatcher.dispatch END");

            GatewayProxyTrace.log(PHASE, "GEF.success START");
            RouteResult result = gef.success(context, response);
            GatewayProxyTrace.log(PHASE, "GEF.success END");
            return result;
        } catch (GatewayAuthException e) {
            GatewayProxyTrace.log(PHASE, "GEF.authFail START");
            RouteResult result = gef.authFail(businessCode, context, e);
            GatewayProxyTrace.log(PHASE, "GEF.authFail END");
            return result;
        } catch (RestClientResponseException e) {
            if (context == null) {
                throw e;
            }
            GatewayProxyTrace.log(PHASE, "GEF.httpError START");
            RouteResult result = gef.httpError(context, e);
            GatewayProxyTrace.log(PHASE, "GEF.httpError END");
            return result;
        } catch (Exception e) {
            if (context == null) {
                throw e;
            }
            GatewayProxyTrace.log(PHASE, "GEF.connectionError START");
            RouteResult result = gef.connectionError(context, e);
            GatewayProxyTrace.log(PHASE, "GEF.connectionError END");
            return result;
        } finally {
            GatewayProxyTrace.end(PHASE);
        }
    }
}
